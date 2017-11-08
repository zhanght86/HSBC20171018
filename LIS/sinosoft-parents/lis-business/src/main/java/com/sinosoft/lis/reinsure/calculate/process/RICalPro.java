





package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.reinsure.calculate.calitem.RICalItemProc;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.reinsure.calculate.manage.RIFinishCalulate;
import com.sinosoft.lis.reinsure.calculate.param.RIInitParam;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public class RICalPro implements RICalMan {
	public CErrors mErrors = new CErrors();
	private String[] mExeOrder;
	private VData mInputData;
	private String mAccumulateDefNo = "";
	private RIInitData mRIInitData;
	private RIWFLogSchema mRIWFLogSchema;
	private RIAccumulateDefSchema mRIAccumulateDefSchema;

	public RICalPro() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!init()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		// 记录工作流程日志
		if (!recordLog()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		try {
			mInputData = cInputData;
			mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
					"RIWFLogSchema", 0);
			if (mRIWFLogSchema == null) {
				buildError("getInputData", "得到日志信息失败！");
				return false;
			}
			mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
			if (mRIWFLogSchema.getStartDate() == null
					|| mRIWFLogSchema.getEndDate() == null) {
				buildError("getInputData", "日志信息中日期有误，请进行核对！");
				return false;
			}
			return true;
		} catch (Exception e) {
			buildError("getInputData", "数据校验程序取得参数时失败，失败原因：" + e.getMessage());
			return false;
		}
	}

	private boolean init() {
		try {
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			mRIAccumulateDefSchema = this.mRIInitData
					.getRIAccumulateDefSchema();
		} catch (Exception ex) {
			buildError("initInfo", " 出错信息: " + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean ExeOrderConf() {
		if (mRIAccumulateDefSchema.getGetDutyType().equals("02")
				&& mRIAccumulateDefSchema.getDIntv().equals("D")) { // 事件驱动模式
			mExeOrder = new String[5];
			mExeOrder[0] = "01";
			mExeOrder[1] = "03";
			mExeOrder[2] = "02";
			mExeOrder[3] = "04";
			mExeOrder[4] = "05";

		} else if (mRIAccumulateDefSchema.getGetDutyType().equals("02")
				&& !mRIAccumulateDefSchema.getDIntv().equals("D")) { // 有效保单模式
			mExeOrder = new String[3];
			mExeOrder[0] = "01";
			mExeOrder[1] = "02";
			mExeOrder[2] = "04";

		} else if (mRIAccumulateDefSchema.getGetDutyType().equals("01")) { // 共保
			mExeOrder = new String[5];
			mExeOrder[0] = "01";
			mExeOrder[1] = "03";
			mExeOrder[2] = "02";
			mExeOrder[3] = "04";
			mExeOrder[4] = "11";

		}
		return true;
	}

	private boolean dealData() {
		if (!ExeOrderConf()) {
			buildError("dealData", "");
			return false;
		}
		for (int i = 0; i < mExeOrder.length; i++) {
			if (!mExeOrder[i].equals("05")) {
				RIRemoveRecord tRIRemoveRecord = new RIRemoveRecord();
				if (!tRIRemoveRecord.submitData(mInputData, mExeOrder[i])) {
					buildError("dealData",
							tRIRemoveRecord.mErrors.getFirstError());
					return false;
				}
				System.out.println(" 事件类型：  " + mExeOrder[i]);
				
				char flag = getRecordNum(mRIWFLogSchema.getTaskCode(), mRIWFLogSchema.getBatchNo(),mExeOrder[i]);
				if (flag == '3') { // 出错
					return false;
				} else if (flag == '1') { // 有数据
					RIInitParam tRIInitParam = new RIInitParam();
					if (!tRIInitParam.submitData(mInputData, mExeOrder[i])) {
						buildError("dealData", tRIInitParam.mErrors.getFirstError());
						return false;
					}
					RICalItemProc tRICalItemProc = new RICalItemProc();
					if (!tRICalItemProc.submitData(mInputData, mExeOrder[i])) {
						buildError("dealData",
								tRICalItemProc.mErrors.getFirstError());
						return false;
					}
				}
			}
			RIFinishCalulate tRIFinishCalulate = new RIFinishCalulate();
			if (!tRIFinishCalulate.submitData(mInputData, mExeOrder[i])) {
				buildError("dealData",
						tRIFinishCalulate.mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	private char getRecordNum(String tAccumulateDefNo, String tBatchNo, String tEventType) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" select 1 from dual where exists (select 1 from rirecordtrace a where a.accumulatedefno='");
		strBuffer.append(tAccumulateDefNo);
		strBuffer.append("' and a.batchno='");
		strBuffer.append(tBatchNo);
		strBuffer.append("' and a.eventtype='");
		strBuffer.append(tEventType);
		strBuffer.append("' and exists (select 1 from ripolrecord b WHERE a.eventno = b.eventno))");
		ExeSQL tExeSQL = new ExeSQL();
		String temp = tExeSQL.getOneValue(strBuffer.toString());
		System.out.println("initRISplitSeg: " + strBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("initRISplitSeg",
					"RISplitSeg程序查询再保接口表失败" + mErrors.getFirstError());
			return '3'; // 报错
		}
		if (temp==null || "".equals(temp)) {
			return '2'; // 无数据
		} else {
			return '1'; // 有数据
		}
	}
	
	private boolean recordLog() {

		return true;
	}

	public static void main(String[] args) {
		RICalPro tRICalPro = new RICalPro();
		VData tVData = new VData();
		RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
		tRIWFLogSchema.setTaskCode("L001000001");
		tRIWFLogSchema.setBatchNo("0000000031");
		tRIWFLogSchema.setNodeState("");
		tVData.add(tRIWFLogSchema);
		tRICalPro.submitData(tVData, "");
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RICalPro";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}


