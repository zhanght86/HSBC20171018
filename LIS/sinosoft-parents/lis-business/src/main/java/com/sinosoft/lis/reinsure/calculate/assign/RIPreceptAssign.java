

package com.sinosoft.lis.reinsure.calculate.assign;

import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.schema.RIBarGainInfoSchema;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.lis.schema.RIPreceptSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIBarGainInfoSet;
import com.sinosoft.lis.vschema.RIItemCalSet;
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
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public class RIPreceptAssign implements RICalMan {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private RIInitData mRIInitData;
	private RIInitSplitSeg mRIInitSplitSeg;
	private String mAccumulateDefNo = "";
	private RIWFLogSchema mRIWFLogSchema;
	private String[][] mSeg;

	public RIPreceptAssign() {
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
		mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
				"RIWFLogSchema", 0);
		if (mRIWFLogSchema == null) {
			buildError("getInputData", "得到日志信息失败！");
			return false;
		}

		if (mRIWFLogSchema.getStartDate() == null
				|| mRIWFLogSchema.getEndDate() == null) {
			buildError("getInputData", "日志信息中日期有误，请进行核对！");
			return false;
		}
		mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
		if (mAccumulateDefNo == null || mAccumulateDefNo.equals("")) {
			buildError("getInputData", "日志信息中累计风险，请进行核对！");
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
		if (!getAssignPrecept()) {
			return false;
		}
		return true;
	}

	/**
	 * 初始化数据
	 * 
	 * @return boolean
	 */
	private boolean init() {
		try {
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			mSeg = mRIInitSplitSeg.getValue();
		} catch (Exception ex) {
			buildError("initInfo", "分保方案分配初始化类失败: " + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 判断接口记录适用的再保方案
	 * 
	 * @param aRIPolRecordSchema
	 *            RIPolRecordSchema
	 * @return RIPreceptSet
	 * @throws Exception
	 */
	public boolean getAssignPrecept() {
		// 合同分保的保单
		for (int i = 1; i <= mRIInitData.getRIPreceptSet().size(); i++) {
			// 对该累计风险下所有再保方案循环
			RIPreceptSchema tRIPreceptSchema = mRIInitData.getRIPreceptSet()
					.get(i);
			if (!tRIPreceptSchema.getPreceptType().equals("02")) {
				// 判断为自动分保
				RIBarGainInfoSet tRIBarGainInfoSet = mRIInitData
						.getRIBarGainInfoSet(); // 再保合同
				RIBarGainInfoSchema tRIBarGainInfoSchema = new RIBarGainInfoSchema();
				for (int j = 1; j <= tRIBarGainInfoSet.size(); j++) {
					if (tRIBarGainInfoSet.get(j).getRIContNo()
							.equals(tRIPreceptSchema.getRIContNo())) {
						tRIBarGainInfoSchema = tRIBarGainInfoSet.get(j);
					}
				}
				if (tRIBarGainInfoSchema == null) {
					buildError("dealData",
							" 再保方案分配时" + tRIPreceptSchema.getRIPreceptNo()
									+ "没有得到相应再保合同 ");
					return false;
				}
				RIItemCalSet tRIItemCalSet = (RIItemCalSet) mRIInitData
						.getRIAssignCalTD().getValueByName(
								tRIPreceptSchema.getRIPreceptNo());
				for (int j = 1; j <= tRIItemCalSet.size(); j++) {
					RIItemCalSchema tRIItemCalSchema = tRIItemCalSet.get(j);
					if (tRIItemCalSchema.getItemCalType().equals("1")) {
						// 固定值
					}
					if (tRIItemCalSchema.getItemCalType().equals("2")) {
						// SQL
						StringBuffer strBuffer;
						for (int k = 0; k < mSeg.length; k++) {
							strBuffer = new StringBuffer();
							strBuffer
									.append("update RIPolRecord a set a.RIContNo = '");
							strBuffer.append(tRIPreceptSchema.getRIContNo());
							strBuffer.append("' ,a.RIPreceptNo = '");
							strBuffer.append(tRIPreceptSchema.getRIPreceptNo());
							strBuffer.append("' ,a.RIPreceptType = '");
							strBuffer.append(tRIPreceptSchema.getAttachFalg());
							strBuffer.append("',a.NodeState='04'");
							strBuffer.append(" where a.AccumulateDefNO='");
							strBuffer.append(mAccumulateDefNo);
							strBuffer.append("' and a.BatchNo='");
							strBuffer.append(mRIWFLogSchema.getBatchNo());
							strBuffer.append("' and a."
									+ tRIBarGainInfoSchema.getMatchMode()
									+ ">='");
							strBuffer.append(tRIBarGainInfoSchema
									.getCValiDate());
							if (tRIBarGainInfoSchema.getEndDate() != null
									&& !tRIBarGainInfoSchema.getEndDate()
											.equals("")) {
								strBuffer.append("' and a."
										+ tRIBarGainInfoSchema.getMatchMode()
										+ "<='");
								strBuffer.append(tRIBarGainInfoSchema
										.getEndDate());
							}
							strBuffer.append("' and a.eventno between '");
							strBuffer.append(mSeg[k][0]);
							strBuffer.append("' and '");
							strBuffer.append(mSeg[k][1]);

							strBuffer
									.append("' and a.riskcode in (select b.riskcode from RIArithmeticRiskRela b where b.Arithmeticdefid='");
							strBuffer
									.append(tRIPreceptSchema.getArithmeticID());

							strBuffer.append("') and ");
							strBuffer.append(tRIItemCalSchema.getCalSQL());
							strBuffer
									.append(" and (a.NodeState not in ('04','05','08','10','11') or a.Nodestate is null)");
							strBuffer
									.append(" and (a.reinsreflag<>'03' or a.reinsreflag is null)");
							System.out.println(" sql: " + strBuffer.toString());
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strBuffer.toString());
							if (tExeSQL.mErrors.needDealError()) {
								buildError("dealData", " 再保方案分配时"
										+ tRIItemCalSchema.getCalItemName()
										+ "算法执行出错 ");
								return false;
							}
							strBuffer = null;
						}
					}
					if (tRIItemCalSchema.getItemCalType().equals("3")) {
						// Class
						String calClass = tRIItemCalSchema.getCalClass();
						if (calClass == null || calClass.equals("")) {
							buildError(
									"dealData",
									"再保方案分配时"
											+ tRIItemCalSchema.getCalItemName()
											+ "算法没有定义CalSQL");
							return false;
						}
						try {
							Class tClass = Class.forName(calClass);
							RIAssign tRIAssign = (RIAssign) tClass
									.newInstance();
							if (!tRIAssign.dealData(tRIItemCalSchema,
									tRIPreceptSchema)) {
								this.mErrors.copyAllErrors(tRIAssign.mErrors);
								return false;
							}
						} catch (Exception ex) {
							buildError(
									"dealData",
									"再保方案分配时"
											+ tRIItemCalSchema.getCalItemName()
											+ "算法出错：" + ex.getMessage());
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean recordLog() {
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIPreceptAssign";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public static void main(String[] args) {
		RIPreceptAssign tRIPreceptAssign = new RIPreceptAssign();
		VData tVData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("200801");
		mRIWFLogSchema.setTaskCode("L001000001");
		tVData.add(mRIWFLogSchema);
		tRIPreceptAssign.submitData(tVData, "");
	}

}
