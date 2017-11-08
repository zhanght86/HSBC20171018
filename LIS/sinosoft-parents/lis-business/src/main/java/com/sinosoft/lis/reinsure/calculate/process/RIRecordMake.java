



package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class RIRecordMake {
	public RIRecordMake() {
	}

	private RIInitData mRIInitData;
	public CErrors mErrors = new CErrors();
	private String mAccumulateDefNo = "";
	private VData mVData = new VData();
	private RIWFLogSchema mRIWFLogSchema;
	private RIAccumulateDefSchema mRIAccumulateDefSchema;

	public RIRecordMake(String accumulateDefNo) throws Exception {
		try {
			mAccumulateDefNo = accumulateDefNo;
			if (!init()) {
			}
		} catch (Exception ex) {
			throw ex;
		}

	}

	private boolean init() {
		try {
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			mRIAccumulateDefSchema = this.mRIInitData
					.getRIAccumulateDefSchema();

		} catch (Exception ex) {
			buildError("initInfo", " 生成分保记录时初始化参数失败: " + ex);
			return false;
		}
		return true;
	}

	public boolean deal(RIPolRecordSchema riPolRecordSchema) {
		if (riPolRecordSchema.getEventType().equals("01")) {
			if (!dealPolData(riPolRecordSchema)) {
				return false;
			}
		} else if (riPolRecordSchema.getEventType().equals("02")) {
			if (!dealContinueData(riPolRecordSchema)) {
				return false;
			}
		} else if (riPolRecordSchema.getEventType().equals("03")) {
			if (!dealEdorData(riPolRecordSchema)) {
				return false;
			}
		} else if (riPolRecordSchema.getEventType().equals("04")) {
			if (!dealClaimData(riPolRecordSchema)) {
				return false;
			}
		} else if (riPolRecordSchema.getEventType().equals("11")) {
			if (!dealBonusData(riPolRecordSchema)) {
				return false;
			}
		} else {
			buildError("deal", " 分保记录生成时，非法业务类型");
			return false;
		}
		return true;
	}

	/**
	 * 新单数据
	 * 
	 * @return boolean
	 */
	private boolean dealPolData(RIPolRecordSchema riPolRecordSchema) {
		mVData = new VData();
		try {
			RIProcessType[] tRIProcessType;
			tRIProcessType = (RIProcessType[]) mRIInitData
					.getRIProcessCalClassTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			System.out.println(riPolRecordSchema.getRIPreceptNo());
			RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet) mRIInitData
					.getRIRiskDivideTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet) mRIInitData
					.getRIIncomeCompanyTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet) mRIInitData
					.getRIDivisionLineDefTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			if (!tRIProcessType[0].dealData(riPolRecordSchema,
					tRIIncomeCompanySet, tRIDivisionLineDefSet,
					tRIRiskDivideSet)) {
				buildError("initInfo",
						" 生成累积风险为： "
								+ riPolRecordSchema.getAccumulateDefNO()
								+ " 事件号为： "
								+ riPolRecordSchema.getEventNo()
								+ " 的再保记录时出现错误： "
								+ tRIProcessType[0].getCErrors()
										.getFirstError());
				return false;
			}
			mVData = tRIProcessType[0].getValue();
		} catch (Exception ex) {
			buildError("initInfo",
					"新单生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()
							+ "  " + ex);
			return false;
		}
		return true;
	}

	/**
	 * 续期
	 * 
	 * @param tVData
	 *            VData
	 * @return boolean
	 */
	private boolean dealContinueData(RIPolRecordSchema riPolRecordSchema) {
		mVData = new VData();
		try {
			RIProcessType[] tRIProcessType;
			tRIProcessType = (RIProcessType[]) mRIInitData
					.getRIProcessCalClassTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());

			RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet) mRIInitData
					.getRIRiskDivideTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet) mRIInitData
					.getRIIncomeCompanyTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet) mRIInitData
					.getRIDivisionLineDefTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			if (!tRIProcessType[1].dealData(riPolRecordSchema,
					tRIIncomeCompanySet, tRIDivisionLineDefSet,
					tRIRiskDivideSet)) {
				buildError("initInfo",
						" 生成累积风险为： "
								+ riPolRecordSchema.getAccumulateDefNO()
								+ " 事件号为： "
								+ riPolRecordSchema.getEventNo()
								+ " 的再保记录时出现错误： "
								+ tRIProcessType[1].getCErrors()
										.getFirstError());
				return false;
			}
			mVData = tRIProcessType[1].getValue();
		} catch (Exception ex) {
			buildError("initInfo",
					"新单生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()
							+ "  " + ex);
			return false;
		}
		return true;
	}

	/**
	 * 保全
	 * 
	 * @return boolean
	 */
	private boolean dealEdorData(RIPolRecordSchema riPolRecordSchema) {
		mVData = new VData();
		try {
			RIProcessType[] tRIProcessType;
			tRIProcessType = (RIProcessType[]) mRIInitData
					.getRIProcessCalClassTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());

			RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet) mRIInitData
					.getRIRiskDivideTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet) mRIInitData
					.getRIIncomeCompanyTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet) mRIInitData
					.getRIDivisionLineDefTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			if (!tRIProcessType[2].dealData(riPolRecordSchema,
					tRIIncomeCompanySet, tRIDivisionLineDefSet,
					tRIRiskDivideSet)) {
				buildError("initInfo",
						" 生成累积风险为： "
								+ riPolRecordSchema.getAccumulateDefNO()
								+ " 事件号为： "
								+ riPolRecordSchema.getEventNo()
								+ " 的再保记录时出现错误： "
								+ tRIProcessType[2].getCErrors()
										.getFirstError());
				return false;
			}
			mVData = tRIProcessType[2].getValue();
		} catch (Exception ex) {
			buildError("initInfo",
					"新单生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()
							+ "  " + ex);
			return false;
		}
		return true;
	}

	/**
	 * 理赔
	 * 
	 * @return boolean
	 */
	private boolean dealClaimData(RIPolRecordSchema riPolRecordSchema) {
		mVData = new VData();
		try {
			RIProcessType[] tRIProcessType;
			tRIProcessType = (RIProcessType[]) mRIInitData
					.getRIProcessCalClassTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet) mRIInitData
					.getRIRiskDivideTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet) mRIInitData
					.getRIIncomeCompanyTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet) mRIInitData
					.getRIDivisionLineDefTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			if (mRIAccumulateDefSchema.getDIntv().equals("D")) {
				if (!tRIProcessType[3].dealData(riPolRecordSchema,
						tRIIncomeCompanySet, tRIDivisionLineDefSet,
						tRIRiskDivideSet)) {
					buildError(
							"initInfo",
							" 生成累积风险为： "
									+ riPolRecordSchema.getAccumulateDefNO()
									+ " 事件号为： "
									+ riPolRecordSchema.getEventNo()
									+ " 的再保记录时出现错误： "
									+ tRIProcessType[3].getCErrors()
											.getFirstError());
					return false;
				}
				mVData = tRIProcessType[3].getValue();
			} else {
				if (!tRIProcessType[2].dealData(riPolRecordSchema,
						tRIIncomeCompanySet, tRIDivisionLineDefSet,
						tRIRiskDivideSet)) {
					buildError(
							"initInfo",
							" 生成累积风险为： "
									+ riPolRecordSchema.getAccumulateDefNO()
									+ " 事件号为： "
									+ riPolRecordSchema.getEventNo()
									+ " 的再保记录时出现错误： "
									+ tRIProcessType[2].getCErrors()
											.getFirstError());
					return false;
				}
				mVData = tRIProcessType[2].getValue();
			}
		} catch (Exception ex) {
			buildError("initInfo",
					"新单生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()
							+ "  " + ex);
			return false;
		}
		return true;
	}

	/**
	 * 红利
	 * 
	 * @return boolean
	 */
	private boolean dealBonusData(RIPolRecordSchema riPolRecordSchema) {
		mVData = new VData();
		try {
			RIProcessType[] tRIProcessType;
			tRIProcessType = (RIProcessType[]) mRIInitData
					.getRIProcessCalClassTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());

			RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet) mRIInitData
					.getRIRiskDivideTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet) mRIInitData
					.getRIIncomeCompanyTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet) mRIInitData
					.getRIDivisionLineDefTD().getValueByName(
							riPolRecordSchema.getRIPreceptNo());
			if (!tRIProcessType[4].dealData(riPolRecordSchema,
					tRIIncomeCompanySet, tRIDivisionLineDefSet,
					tRIRiskDivideSet)) {
				buildError("initInfo",
						" 生成累积风险为： "
								+ riPolRecordSchema.getAccumulateDefNO()
								+ " 事件号为： "
								+ riPolRecordSchema.getEventNo()
								+ " 的再保记录时出现错误： "
								+ tRIProcessType[2].getCErrors()
										.getFirstError());
				return false;
			}
			mVData = tRIProcessType[4].getValue();
		} catch (Exception ex) {
			buildError("initInfo",
					"新单生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()
							+ "  " + ex);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mVData;
	}

	public static void main(String[] args) {
		RIDataMake tRIDataMake = new RIDataMake();
		VData tVData = new VData();
		RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
		tRIWFLogSchema.setTaskCode("L001000001");
		tRIWFLogSchema.setBatchNo("0000000031");
		tRIWFLogSchema.setNodeState("");
		tVData.add(tRIWFLogSchema);
		tRIDataMake.submitData(tVData, "");
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

