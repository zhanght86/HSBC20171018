

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
 * Compansy: sinosoft
 * </p>
 *
 * @zhangbin
 * @version 1.0
 */
public class RISerialNo implements RICalMan {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private String mAccumulateDefNo = "";

	private String mBatchNo = "";

	private RIWFLogSchema mRIWFLogSchema;

	private RIInitSplitSeg mRIInitSplitSeg;

	// private String mEventType;
	private String[][] mSeg;

	/** 自动分保在保编号 */
	private String autoNo;

	/** 临时分保再保编号 */
	private String tempNo;

	public RISerialNo() {
	}

	private boolean init() {
		try {
			mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			mSeg = mRIInitSplitSeg.getValue();
		} catch (Exception ex) {
			buildError("initInfo", " 出错信息: " + ex.getMessage());
			return false;
		}
		return true;
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

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		try {
			mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
					"RIWFLogSchema", 0);
			if (mRIWFLogSchema == null) {
				buildError("getInputData", "得到日志信息失败！");
				return false;
			}
			mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
			mBatchNo = mRIWFLogSchema.getBatchNo();
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

	private boolean dealData() {
		try {
			this.autoNo = null;
			this.tempNo = null;
			ExeSQL exeSQL = new ExeSQL();
			// 查询自动再保编号和临分再保编号开头字母
			String tSql = "select codealias from ldcode where codetype='riautono' and code='"
					+ mAccumulateDefNo + "'";
			SSRS tSSRS = exeSQL.execSQL(tSql);
			if (tSSRS.MaxRow > 0) {
				autoNo = tSSRS.GetText(1, 1);
			}
			tSql = "select codealias from ldcode where codetype='ritempno' and code='"
					+ mAccumulateDefNo + "'";
			tSSRS = exeSQL.execSQL(tSql);
			if (tSSRS.MaxRow > 0) {
				tempNo = tSSRS.GetText(1, 1);
			}
			for (int i = 0; i < mSeg.length; i++) {
				if (autoNo != null) {// 更新新契约自动分保再保编号
					tSql = "update RIPolRecordbake a set StandbyString1=getserialno1('"+autoNo+"','"
							+ PubFun.getCurrentDate().substring(0, 4)
							+ "',a.managecom) where batchno='"
							+ mBatchNo
							+ "' and StandbyString1 is null and accumulatedefno='"
							+ mAccumulateDefNo
							+ "' and (ReinsreFlag is null or ReinsreFlag = '') and eventtype='01' and a.eventno between '"
							+ mSeg[i][0] + "' and '" + mSeg[i][1] + "'";
					if (!exeSQL.execUpdateSQL(tSql)) {
						buildError("dealData", " 生成新单再保编号失败 ");
						return false;
					}
				}
				if (tempNo != null) {// 更新新契约临时分保再保编号
					tSql = "update RIPolRecordbake a set StandbyString1=getserialno1('"+tempNo+"','"
							+ PubFun.getCurrentDate().substring(0, 4)
							+ "',a.managecom) where batchno='"
							+ mBatchNo
							+ "' and StandbyString1 is null and accumulatedefno='"
							+ mAccumulateDefNo
							+ "' and ReinsreFlag in ('00','03') and eventtype='01' and a.eventno between '"
							+ mSeg[i][0] + "' and '" + mSeg[i][1] + "'";
					if (!exeSQL.execUpdateSQL(tSql)) {
						buildError("dealData", " 生成新单再保编号失败 ");
						return false;
					}
				}
				// 更新非新契约业务接口数据的再保编号
				tSql = "update RIPolRecordbake a set StandbyString1=(select StandbyString1 from RIPolRecordbake where polno=a.polno and eventno in(select max(eventno)from RIPolRecordbake where StandbyString1 is not null and polno =a.polno)) where batchno='"
						+ mBatchNo
						+ "' and StandbyString1 is null and accumulatedefno='"
						+ mAccumulateDefNo
						+ "' and eventtype<>'01' and a.eventno between '"
						+ mSeg[i][0] + "' and '" + mSeg[i][1] + "'";
				if (!exeSQL.execUpdateSQL(tSql)) {
					buildError("dealData", " 生成再保编号失败 ");
					return false;
				}
			}
		} catch (Exception ex) {
			buildError("dealData", " 更新再保编号失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CalItemDeal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}
