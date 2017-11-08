

package com.sinosoft.lis.reinsure.calculate.check;

import com.sinosoft.lis.db.RICheckErrDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.schema.RICheckErrBakeSchema;
import com.sinosoft.lis.schema.RICheckErrSchema;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RICheckErrBakeSet;
import com.sinosoft.lis.vschema.RICheckErrSet;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
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
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */

public class RICheckData implements RICalMan {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private String mAccumulateDefNo = "";

	private RIWFLogSchema mRIWFLogSchema;
	private RIInitData mRIInitData;

	private Reflections mReflections = new Reflections();

	public RICheckData() {
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
		if (!recordLog()) {
			return false;
		}
		return true;
	}

	/**
	 * 得到业务数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		try {
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

	/**
	 * 初始化数据
	 * 
	 * @return boolean
	 */
	private boolean init() {
		try {
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
		} catch (Exception ex) {
			buildError("getInputData", " 数据校验程序初始化类时失败，失败原因：" + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 数据处理
	 * 
	 * @param aAccumulateDefNO
	 *            String
	 * @return boolean
	 */
	private boolean dealData() {
		RIItemCalSet tRIItemCalSet = mRIInitData.getRICheckCalSet();
		if (tRIItemCalSet.size() > 0) {
			for (int i = 1; i <= tRIItemCalSet.size(); i++) {
				RIItemCalSchema tItemCalSchema = tRIItemCalSet.get(i);
				if (tItemCalSchema.getItemCalType() != null
						&& !tItemCalSchema.getItemCalType().equals("")) {
					if (tItemCalSchema.getItemCalType().equals("1")) {
						// 固定数值
						continue;
					} else if (tItemCalSchema.getItemCalType().equals("2")) {
						PubCalculator tPubCalculator = new PubCalculator();
						tPubCalculator.addBasicFactor("BatchNO",
								mRIWFLogSchema.getBatchNo());
						tPubCalculator.addBasicFactor("AccumulateDefNO",
								mRIWFLogSchema.getTaskCode());
						tPubCalculator.addBasicFactor("StartDate",
								mRIWFLogSchema.getStartDate());
						tPubCalculator.addBasicFactor("EndDate",
								mRIWFLogSchema.getEndDate());
						String strSQL = tItemCalSchema.getCalSQL();
						if (strSQL == null && strSQL.equals("")) {
							buildError("distillData", "数据校验算法："
									+ tItemCalSchema.getArithmeticID() + "-"
									+ tItemCalSchema.getArithmeticName()
									+ "提取SQL未定义!");
							return false;
						}
						tPubCalculator.setCalSql(strSQL);
						strSQL = tPubCalculator.calculateEx();
						strSQL = "select count(1) from (" + strSQL + ")";
						System.out.println(" check sql : " + strSQL);
						ExeSQL tExeSQL = new ExeSQL();
						int countNum = Integer.parseInt(tExeSQL
								.getOneValue(strSQL));

						if (countNum > 0) {
							if (!writeErrorLog(tItemCalSchema)) {
								return false;
							}
						}
					} else if (tItemCalSchema.getItemCalType().equals("3")) {
						// 类处理
						return true;
					}
				}
			}
		}
		return true;
	}

	private boolean writeErrorLog(RIItemCalSchema riItemCalSchema) {
		try {
			RICheckErrDB tRICheckErrDB = new RICheckErrDB();
			RICheckErrBakeSet tRICheckErrBakeSet = new RICheckErrBakeSet();

			tRICheckErrDB.setBatchNo(mRIWFLogSchema.getBatchNo());
			tRICheckErrDB.setArithmeticID(riItemCalSchema.getArithmeticID());
			RICheckErrSet tRICheckErrDelSet = tRICheckErrDB.query();
			if (tRICheckErrDB.mErrors.needDealError()) {
				buildError("writeErrorLog", "查询数据校验信息表时出错");
				return false;
			}
			if (tRICheckErrDelSet.size() > 0) {
				RICheckErrBakeSchema tRICheckErrBakeSchema;
				for (int i = 1; i <= tRICheckErrDelSet.size(); i++) {
					tRICheckErrBakeSchema = new RICheckErrBakeSchema();
					mReflections.transFields(tRICheckErrBakeSchema,
							tRICheckErrDelSet.get(1));
					tRICheckErrBakeSet.add(tRICheckErrBakeSchema);
				}
			}
			RICheckErrSet tRICheckErrSet = new RICheckErrSet();
			RICheckErrSchema tRICheckErrSchema = new RICheckErrSchema();
			tRICheckErrSchema
					.setSerialNo(PubFun1.CreateMaxNo("RICHECKERR", 20));
			tRICheckErrSchema.setBatchNo(mRIWFLogSchema.getBatchNo());
			tRICheckErrSchema
					.setArithmeticID(riItemCalSchema.getArithmeticID());
			tRICheckErrSchema.setCalItemID(riItemCalSchema.getCalItemID());
			tRICheckErrSchema.setErrInfo("批次号为:" + mRIWFLogSchema.getBatchNo()
					+ ", 累计风险编码: " + mRIWFLogSchema.getTaskCode() + ", 数据校验算法:"
					+ riItemCalSchema.getArithmeticID() + "出现错误:"
					+ riItemCalSchema.getReMark() + "。");
			tRICheckErrSchema.setMakeDate(PubFun.getCurrentDate());
			tRICheckErrSchema.setMakeTime(PubFun.getCurrentTime());
			tRICheckErrSet.add(tRICheckErrSchema);

			MMap tMap = new MMap();
			tMap.put(tRICheckErrBakeSet, "INSERT");
			tMap.put(tRICheckErrDelSet, "DELETE");
			tMap.put(tRICheckErrSet, "INSERT");
			VData tVData = new VData();
			tVData.add(tMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, "")) {
				buildError("writeErrorLog", "保存数据校验信息时出错");
				return false;
			}
		} catch (Exception ex) {
			buildError("writeErrorLog", "保存数据校验信息时出错:" + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean recordLog() {
		return true;
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
