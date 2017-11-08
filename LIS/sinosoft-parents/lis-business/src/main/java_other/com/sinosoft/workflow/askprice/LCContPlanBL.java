/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.workflow.askprice;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LQBenefitDB;
import com.sinosoft.lis.db.LQBenefitDutyParamDB;
import com.sinosoft.lis.db.LQBenefitToRiskDB;
import com.sinosoft.lis.db.AskPriceRadioDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.db.LMRiskDutyFactorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LQBenefitDutyParamSchema;
import com.sinosoft.lis.schema.LQBenefitSchema;
import com.sinosoft.lis.schema.LQBenefitToRiskSchema;
import com.sinosoft.lis.vschema.LQBenefitDutyParamSet;
import com.sinosoft.lis.vschema.LQBenefitToRiskSet;
import com.sinosoft.lis.vschema.LQBenefitSet;
import com.sinosoft.lis.vschema.AskPriceRadioSet;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.lis.vschema.LMRiskDutyFactorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * 数据准备类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据操作类型不同，对数据进行校验、准备处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class LCContPlanBL {
private static Logger logger = Logger.getLogger(LCContPlanBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LQBenefitSchema mLQBenefitSchema = new LQBenefitSchema();
	private LQBenefitSet mLQBenefitSet = new LQBenefitSet();
	private AskPriceRadioSet mAskPriceRadioSet = new AskPriceRadioSet();
	private LQBenefitToRiskSchema mLQBenefitToRiskSchema = new LQBenefitToRiskSchema();
	private LQBenefitToRiskSet mLQBenefitToRiskSet = new LQBenefitToRiskSet();
	private LQBenefitDutyParamSet mLQBenefitDutyParamSet = new LQBenefitDutyParamSet();
	// private String mGrpContNo = ""; //团体合同号
	// private String mContPlanCode = ""; //保险计划或默认值编码
	// private String mProposalGrpContNo = "";
	// private String mPlanType = "";
	// private String mPlanSql = "";
	// private String mPeoples3 = "";
	private String mSql = "";

	public LCContPlanBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据处理失败LCContPlanBL-->checkData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LCContPlanBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start LCContPlanBL Submit...");
			LCContPlanBLS tLCContPlanBLS = new LCContPlanBLS();
			tLCContPlanBLS.submitData(mInputData, cOperate);
			logger.debug("End LCContPlanBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tLCContPlanBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCContPlanBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanBL";
				tError.functionName = "submitDat";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		mInputData = null;
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 如果是删除或修改操作，需要校验是否有被保人拥有该计划，如果有，则该计划不允许删除、修改
		if (this.mOperate.compareTo("DELETE||MAIN") == 0
				|| this.mOperate.compareTo("UPDATE||MAIN") == 0) {
			// 增加一个校验141险种只能在一个保障计划中存在 yaory

			// end
			// String tSql = "";
			ExeSQL texeSQL = new ExeSQL();
			/*
			 * 判定是计划还是默认值处理 如果是默认值则，则只要保单下有被保人拥有险种信息后，就不可删除、修改
			 * 如果是保险计划，则如果属于该计划的被保人拥有险种信息后，就不可删除、修改
			 */
			// if (this.mLQBenefitSchema.getContPlanCode().compareTo("00") ==
			// 0) {
			// tSql =
			// "select count(1) from LCInsured a,LCPol b where a.PrtNo = b.PrtNo
			// " +
			// " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo " +
			// " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '" +
			// this.mLQBenefitSchema.getGrpContNo() + "'";
			// } else {
		 /*	String tSql = "select count(1) from LCInsured a,LCPol b where a.PrtNo = b.PrtNo "
					+ " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo "
					+ " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '"
					+ this.mLQBenefitSchema.getGrpContNo()
					+ "' and a.ContPlanCode = '"
					+ this.mLQBenefitSchema.getContPlanCode() + "'";
			// }
			SSRS ssrs = texeSQL.execSQL(tSql);
			if (ssrs.GetText(1, 1).compareTo("0") > 0) {
				CError tError = new CError();
				tError.moduleName = "LCContPlanBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该团单"
						+ this.mLQBenefitDutyParamSet.get(1).getGrpContNo()
						+ "下，有被保人已经投保该保障计划，请先删除被保险人然后修改保障计划！";
				this.mErrors.addOneError(tError);
				return false;
			}
			 */
			// LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			// LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			// tLCInsuredDB.setContPlanCode(this.mContPlanCode);
			// tLCInsuredDB.setGrpContNo(this.mGrpContNo);
			// tLCInsuredSet = tLCInsuredDB.query();
			// if (tLCInsuredSet.size() > 0)
			// {
			// CError tError = new CError();
			// tError.moduleName = "LCContPlanBL";
			// tError.functionName = "checkData";
			// tError.errorMessage = "该团单" +
			// this.mLQBenefitDutyParamSet.get(1).
			// getGrpContNo() + "下，有被保人拥有该" +
			// this.mLQBenefitDutyParamSet.get(1).
			// getContPlanCode() + "保险计划！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
		} else {

			LQBenefitToRiskDB tLQBenefitToRiskDB = new LQBenefitToRiskDB();
			for (int m = 1; m <= mLQBenefitDutyParamSet.size(); m++) {
				// 改用这样的查询方式，可以把录入细化到险种信息一层
				tLQBenefitToRiskDB = new LQBenefitToRiskDB();
				tLQBenefitToRiskDB.setAskPriceNo(mLQBenefitDutyParamSet
						.get(m).getAskPriceNo());
				tLQBenefitToRiskDB.setAskNo(mLQBenefitDutyParamSet
						.get(m).getAskNo());
				tLQBenefitToRiskDB.setMainRiskCode(mLQBenefitDutyParamSet
						.get(m).getMainRiskCode());
				tLQBenefitToRiskDB.setRiskCode(mLQBenefitDutyParamSet.get(m)
						.getRiskCode());
				tLQBenefitToRiskDB.setContPlanCode(mLQBenefitDutyParamSet
						.get(m).getContPlanCode());
				tLQBenefitToRiskDB.setPlanType(this.mLQBenefitSchema
						.getPlanType());
				if (tLQBenefitToRiskDB.getInfo()) {
					// 如果是新增的时候，则需要出错处理
					if (this.mOperate.compareTo("INSERT||MAIN") == 0) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LCContPlanBL";
						tError.functionName = "checkData";
						if (tLQBenefitToRiskDB.getContPlanCode().compareTo("00") != 0) {
							tError.errorMessage = "该报价单"
									+ tLQBenefitToRiskDB.getAskPriceNo() + "，版本号："+tLQBenefitToRiskDB.getAskNo()+"的保险计划"
									+ tLQBenefitToRiskDB.getContPlanCode()
									+ "中，已存在" + tLQBenefitToRiskDB.getRiskCode()
									+ "险种信息，如果你要变动，请使用保险计划修改功能！";
						} else {
							tError.errorMessage = "该报价单"
								+ tLQBenefitToRiskDB.getAskPriceNo() + "，版本号:"+tLQBenefitToRiskDB.getAskNo()+ "已存在"
									+ tLQBenefitToRiskDB.getRiskCode()
									+ "险种默认值信息，如果您要变动，请使用险种信息更新功能！";
						}
						this.mErrors.addOneError(tError);
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		boolean tReturn = true;
		// 新增处理，修改处理
		if (this.mOperate.compareTo("DELETE||MAIN") != 0) {
			String RiskCode = "";
			String MainRiskCode = "";
			String DutyCode = "";
			int k = 0;

			LMRiskDutyFactorDB tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
			LMRiskDutyFactorSet tLMRiskDutyFactorSet = new LMRiskDutyFactorSet();
			// 首先校验是不是保险套餐，如果是判断本合同下有没有添加保险套餐的险种yaory
			String plantype = mLQBenefitSchema.getPlanType();
			logger.debug("是不是保险套餐:" + plantype);
			if (plantype.equals("1")) {
				// 如果都具有，还要把GRPPOLNO保存到险种责任要素里mLCContPlanDutyParam。setgrppolno
				String plancode = mLQBenefitSchema.getRemark();
				LDPlanRiskDB mLDPlanRiskDB = new LDPlanRiskDB();
				LDPlanRiskSet mLDPlanRiskSet = new LDPlanRiskSet();
				String sql = "select  * FROM ldplanrisk where contplancode='"
						+ "?plancode?" + "'";
				logger.debug("+++++++++++" + sql);
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("plancode",plancode);
				mLDPlanRiskSet = mLDPlanRiskDB.executeQuery(sqlbv1);
				if (mLDPlanRiskSet == null || mLDPlanRiskSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "LCContPlanBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保险套餐不存在或定义错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= mLDPlanRiskSet.size(); i++) {
					AskPriceRadioDB mLCGrpPol = new AskPriceRadioDB();
					AskPriceRadioSet mAskPriceRadioSet = new AskPriceRadioSet();
					String tsql = "select * From askpriceradio where riskcode='"
							+ "?riskcode?"
							+ "' and askpriceno='"
							+ "?askpriceno?" + "' and askno ='"+"?askno?"+"'";
					logger.debug(tsql);
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tsql);
					sqlbv2.put("riskcode",mLDPlanRiskSet.get(i).getRiskCode());
					sqlbv2.put("askpriceno",mLQBenefitSchema.getAskPriceNo());
					sqlbv2.put("askno",mLQBenefitSchema.getAskNo());
					mAskPriceRadioSet = mLCGrpPol.executeQuery(sqlbv2);
					if (mAskPriceRadioSet == null || mAskPriceRadioSet.size() == 0) {
						CError tError = new CError();
						tError.moduleName = "LCContPlanBL";
						tError.functionName = "dealData";
						tError.errorMessage = "请先添加保险套餐下的险种"
								+ mLDPlanRiskSet.get(i).getRiskCode() + "！";
						this.mErrors.addOneError(tError);
						return false;

					} else {
						// 把GRPPOLNO保存到险种责任要素里mLCContPlanDutyParam。setgrppolno
						logger.debug("\\\\\\\\\\\\:"
								+ mLQBenefitDutyParamSet.size());
						for (int j = 1; j <= mLQBenefitDutyParamSet.size(); j++) {
							logger.debug("\\\\\\\\\\\\:"
									+ mLQBenefitDutyParamSet.get(j)
											.getRiskCode());
							logger.debug("\\\\\\\\\\\\:"
									+ mAskPriceRadioSet.get(1).getRiskCode());
							if (mLQBenefitDutyParamSet.get(j).getRiskCode()
									.equals(mAskPriceRadioSet.get(1).getRiskCode())) {
								mLQBenefitDutyParamSet.get(j).setAskPriceNo(
										mAskPriceRadioSet.get(1).getAskPriceNo());
								mLQBenefitDutyParamSet.get(j).setAskNo(
										mAskPriceRadioSet.get(1).getAskNo());
							}
						}
					}
				}
			}

			// 循环保险计划责任要素值集合，添加了主险校验
			for (int i = 1; i <= this.mLQBenefitDutyParamSet.size(); i++) {
				if (RiskCode.compareTo(this.mLQBenefitDutyParamSet.get(i)
						.getRiskCode()) != 0
						|| MainRiskCode.compareTo(this.mLQBenefitDutyParamSet
								.get(i).getMainRiskCode()) != 0) {
					// 当险种更换后，校验要素个数，且第一次进入时不校验
					if (RiskCode.compareTo("") != 0
							&& k != tLMRiskDutyFactorSet.size()) {
						// @@错误处理
						logger.debug("失败1");
						CError tError = new CError();
						tError.moduleName = "LCContPlanBL";
						tError.functionName = "dealData";
						tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode
								+ "责任要素信息有误！";
						this.mErrors.addOneError(tError);
						return false;
					}
					// 险种更换，则责任编码初始化
					RiskCode = this.mLQBenefitDutyParamSet.get(i)
							.getRiskCode();
					MainRiskCode = this.mLQBenefitDutyParamSet.get(i)
							.getMainRiskCode();
					DutyCode = "";

					// 准备保单险种保险计划表数据，只与险种和计划挂钩，不关心责任、要素信息
					mLQBenefitToRiskSchema = new LQBenefitToRiskSchema();
					mLQBenefitToRiskSchema.setPlanType(this.mLQBenefitSchema
							.getPlanType());
					mLQBenefitToRiskSchema
							.setAskPriceNo(this.mLQBenefitDutyParamSet.get(i)
									.getAskPriceNo());
					mLQBenefitToRiskSchema
							.setAskNo(this.mLQBenefitDutyParamSet
									.get(i).getAskNo());
					mLQBenefitToRiskSchema
							.setRiskCode(this.mLQBenefitDutyParamSet.get(i)
									.getRiskCode());
					mLQBenefitToRiskSchema
							.setRiskVersion(this.mLQBenefitDutyParamSet.get(i)
									.getRiskVersion());
					mLQBenefitToRiskSchema
							.setContPlanCode(this.mLQBenefitDutyParamSet
									.get(i).getContPlanCode());
					mLQBenefitToRiskSchema
							.setContPlanName(this.mLQBenefitDutyParamSet
									.get(i).getContPlanName());
					mLQBenefitToRiskSchema
							.setMainRiskCode(this.mLQBenefitDutyParamSet
									.get(i).getMainRiskCode());
					mLQBenefitToRiskSchema
							.setMainRiskVersion(this.mLQBenefitDutyParamSet
									.get(i).getMainRiskVersion());
					mLQBenefitToRiskSchema
							.setOperator(this.mGlobalInput.Operator);
					mLQBenefitToRiskSchema.setMakeDate(PubFun.getCurrentDate());
					mLQBenefitToRiskSchema.setMakeTime(PubFun.getCurrentTime());
					mLQBenefitToRiskSchema
							.setModifyDate(PubFun.getCurrentDate());
					mLQBenefitToRiskSchema
							.setModifyTime(PubFun.getCurrentTime());
					mLQBenefitToRiskSet.add(mLQBenefitToRiskSchema);

					// 新险种的第一个责任项校验
					if (DutyCode.compareTo(this.mLQBenefitDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 责任更换
						DutyCode = this.mLQBenefitDutyParamSet.get(i)
								.getDutyCode();
						// 如果要素信息是Deductible则，需要对要素信息做一定处理
						if (this.mLQBenefitDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLQBenefitDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
								sqlbv3.sql(lssql);
								sqlbv3.put("DutyCode",DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv3);
								mLQBenefitDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLQBenefitSchema.getPlanType().compareTo("0") == 0) {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						}
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql(mSql);
						sqlbv4.put("RiskCode",RiskCode);
						sqlbv4.put("DutyCode",DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(sqlbv4);
					}
					k = 1;
				} else {
					// 同一险种下的不同责任校验
					if (DutyCode.compareTo(this.mLQBenefitDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 同一险种下，责任更换时，校验要素个数
						if (k != tLMRiskDutyFactorSet.size()) {
							// @@错误处理
							logger.debug("失败2");
							CError tError = new CError();
							tError.moduleName = "LCContPlanBL";
							tError.functionName = "dealData";
							tError.errorMessage = "险种" + RiskCode + "下的"
									+ DutyCode + "责任要素信息有误！";
							this.mErrors.addOneError(tError);
							return false;
						}
						// 责任更换
						DutyCode = this.mLQBenefitDutyParamSet.get(i)
								.getDutyCode();
						if (this.mLQBenefitDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLQBenefitDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
								sqlbv5.sql(lssql);
								sqlbv5.put("DutyCode",DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv5);
								mLQBenefitDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						k = 1;
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLQBenefitSchema.getPlanType().compareTo("0") == 0) {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						}
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						sqlbv6.sql(mSql);
						sqlbv6.put("RiskCode",RiskCode);
						sqlbv6.put("DutyCode",DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(sqlbv6);
					} else {
						if (this.mLQBenefitDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLQBenefitDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
								sqlbv7.sql(mSql);
								sqlbv7.put("DutyCode",DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv7);
								mLQBenefitDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						k += 1;
					}
				}
			}
			// if (k != tLMRiskDutyFactorSet.size()) {
			// // @@错误处理
			// logger.debug("失败3");
			// CError tError = new CError();
			// tError.moduleName = "LCContPlanBL";
			// tError.functionName = "dealData";
			// tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode +
			// "责任要素信息有误！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// 准备保单保险计划表数据
			// mLQBenefitSchema = new LQBenefitSchema();
			// mLQBenefitSchema.setGrpContNo(this.mLQBenefitDutyParamSet.get(1).
			// getGrpContNo());
			// mLQBenefitSchema.setProposalGrpContNo(this.mLQBenefitDutyParamSet.
			// get(1).getProposalGrpContNo());
			// mLQBenefitSchema.setContPlanCode(this.mLQBenefitDutyParamSet.get(
			// 1).
			// getContPlanCode());
			// mLQBenefitSchema.setContPlanName(this.mLQBenefitDutyParamSet.get(
			// 1).
			// getContPlanName());
			// mLQBenefitSchema.setPlanType(this.mPlanType); //计划类别，此处全部为固定计划
			// mLQBenefitSchema.setPlanSql(this.mPlanSql); //分类说明
			// mLQBenefitSchema.setPeoples3(this.mPeoples3);//可保人数
			mLQBenefitSchema.setOperator(this.mGlobalInput.Operator);
			mLQBenefitSchema.setMakeDate(PubFun.getCurrentDate());
			mLQBenefitSchema.setMakeTime(PubFun.getCurrentTime());
			mLQBenefitSchema.setModifyDate(PubFun.getCurrentDate());
			mLQBenefitSchema.setModifyTime(PubFun.getCurrentTime());
			mLQBenefitSet.add(mLQBenefitSchema);
		}
		// 由于删除操作可能没有multine数据，因此需要一些特殊参数，并且根据参数查询要删除的数据
		if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
			// 准备保单保险计划的删除数据
			LQBenefitDB tLQBenefitDB = new LQBenefitDB();
			tLQBenefitDB.setAskPriceNo(this.mLQBenefitSchema.getAskPriceNo());
			tLQBenefitDB.setContPlanCode(this.mLQBenefitSchema
					.getContPlanCode());
			tLQBenefitDB.setPlanType(this.mLQBenefitSchema.getPlanType());
			tLQBenefitDB.setAskNo(this.mLQBenefitSchema
					.getAskNo());
			mLQBenefitSet = tLQBenefitDB.query();
			// 准备保单险种保险计划的删除数据
			LQBenefitToRiskDB tLQBenefitToRiskDB = new LQBenefitToRiskDB();
			tLQBenefitToRiskDB.setAskPriceNo(this.mLQBenefitSchema
					.getAskPriceNo());
			tLQBenefitToRiskDB.setContPlanCode(this.mLQBenefitSchema
					.getContPlanCode());
			tLQBenefitToRiskDB.setPlanType(this.mLQBenefitSchema.getPlanType());
			tLQBenefitToRiskDB.setAskNo(this.mLQBenefitSchema
					.getAskNo());
			mLQBenefitToRiskSet = tLQBenefitToRiskDB.query();
			if (mLQBenefitSet.size() > 0 && mLQBenefitToRiskSet.size() > 0) {
				if (mLQBenefitSet.get(1).getPlanType().equals("1")) {
					for (int i = 1; i <= mLQBenefitToRiskSet.size(); i++) {

						LQBenefitToRiskDB ttLQBenefitToRiskDB = new LQBenefitToRiskDB();
						ttLQBenefitToRiskDB.setAskPriceNo(this.mLQBenefitSchema
								.getAskPriceNo());
						ttLQBenefitToRiskDB.setAskNo(this.mLQBenefitSchema
								.getAskNo());
						ttLQBenefitToRiskDB.setRiskCode(mLQBenefitToRiskSet
								.get(i).getRiskCode());
						int planRiskCount = ttLQBenefitToRiskDB.getCount();
						if (ttLQBenefitToRiskDB.mErrors.needDealError()) {
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "GroupRiskBL";
							tError.functionName = "deleteData";
							tError.errorMessage = "查询保险计划时失败!";
							this.mErrors.addOneError(tError);
							return false;

						}
						if (planRiskCount == 1) {
							AskPriceRadioDB tAskPriceRadioDB = new AskPriceRadioDB();
							tAskPriceRadioDB.setAskPriceNo(this.mLQBenefitSchema									
									.getAskPriceNo());
							tAskPriceRadioDB.setAskNo(this.mLQBenefitSchema
									.getAskNo());
							tAskPriceRadioDB.setRiskCode(mLQBenefitToRiskSet.get(i)
									.getRiskCode());
							AskPriceRadioSet tAskPriceRadioSet = tAskPriceRadioDB.query();
							if (tAskPriceRadioSet == null
									|| tAskPriceRadioSet.size() <= 0) {
								// @@错误处理
								CError tError = new CError();
								tError.moduleName = "GrpContBL";
								tError.functionName = "dealData";
								tError.errorMessage = "请您确认：要删除的集体险种代码传入错误!";
								this.mErrors.addOneError(tError);
								return false;
							}
							mAskPriceRadioSet.add(tAskPriceRadioSet.get(1));
						}
					}
				}
			}
			// 准备保险计划责任要素值的删除数据
			LQBenefitDutyParamDB tLQBenefitDutyParamDB = new LQBenefitDutyParamDB();
			tLQBenefitDutyParamDB.setAskPriceNo(this.mLQBenefitSchema
					.getAskPriceNo());
			tLQBenefitDutyParamDB.setContPlanCode(this.mLQBenefitSchema
					.getContPlanCode());
			tLQBenefitDutyParamDB.setPlanType(this.mLQBenefitSchema
					.getPlanType());
			tLQBenefitDutyParamDB.setAskNo(this.mLQBenefitSchema
					.getAskNo());
			mLQBenefitDutyParamSet = tLQBenefitDutyParamDB.query();
		}

		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		this.mLQBenefitDutyParamSet.set((LQBenefitDutyParamSet) cInputData
				.getObjectByObjectName("LQBenefitDutyParamSet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mLQBenefitSchema.setSchema((LQBenefitSchema) cInputData
				.getObjectByObjectName("LQBenefitSchema", 0));
		// this.mProposalGrpContNo = (String) cInputData.get(2);
		// this.mGrpContNo = (String) cInputData.get(3);
		// this.mContPlanCode = (String) cInputData.get(4);
		// this.mPlanType = (String) cInputData.get(5);
		// this.mPlanSql = (String) cInputData.get(6);
		// this.mPeoples3 = (String) cInputData.get(7);

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start LCContPlanBLQuery Submit...");
		LQBenefitDB tLQBenefitDB = new LQBenefitDB();
		tLQBenefitDB.setSchema(this.mLQBenefitSchema);
		this.mLQBenefitSet = tLQBenefitDB.query();
		this.mResult.add(this.mLQBenefitSet);
		logger.debug("End LCContPlanBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLQBenefitDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLQBenefitDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData = new VData();
			this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mLQBenefitSet);
			this.mInputData.add(this.mLQBenefitToRiskSet);
			this.mInputData.add(this.mLQBenefitDutyParamSet);
			this.mInputData.add(this.mAskPriceRadioSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
