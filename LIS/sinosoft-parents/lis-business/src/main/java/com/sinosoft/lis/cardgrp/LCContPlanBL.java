/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanDutyParamDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.db.LMRiskDutyFactorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
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
	private LCContPlanSchema mLCContPlanSchema = new LCContPlanSchema();
	private LCContPlanSet mLCContPlanSet = new LCContPlanSet();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCContPlanRiskSchema mLCContPlanRiskSchema = new LCContPlanRiskSchema();
	private LCContPlanRiskSet mLCContPlanRiskSet = new LCContPlanRiskSet();
	private LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
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
			// if (this.mLCContPlanSchema.getContPlanCode().compareTo("00") ==
			// 0) {
			// tSql =
			// "select count(1) from LCInsured a,LCPol b where a.PrtNo = b.PrtNo
			// " +
			// " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo " +
			// " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '" +
			// this.mLCContPlanSchema.getGrpContNo() + "'";
			// } else {
			String tSql = "select count(1) from LCInsured a,LCPol b where a.PrtNo = b.PrtNo "
					+ " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo "
					+ " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '"
					+ "?GrpContNo?"
					+ "' and a.ContPlanCode = '"
					+ "?ContPlanCode?" + "'";
			// }
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tSql);
			sqlbv12.put("GrpContNo", this.mLCContPlanSchema.getGrpContNo());
			sqlbv12.put("ContPlanCode", this.mLCContPlanSchema.getContPlanCode());
			SSRS ssrs = texeSQL.execSQL(sqlbv12);
			if (ssrs.GetText(1, 1).compareTo("0") > 0) {
				CError tError = new CError();
				tError.moduleName = "LCContPlanBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该团单"
						+ this.mLCContPlanDutyParamSet.get(1).getGrpContNo()
						+ "下，有被保人已经投保该保障计划，请先删除被保险人然后修改保障计划！";
				this.mErrors.addOneError(tError);
				return false;
			}
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
			// this.mLCContPlanDutyParamSet.get(1).
			// getGrpContNo() + "下，有被保人拥有该" +
			// this.mLCContPlanDutyParamSet.get(1).
			// getContPlanCode() + "保险计划！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
		} else {

			LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
			for (int m = 1; m <= mLCContPlanDutyParamSet.size(); m++) {
				// 改用这样的查询方式，可以把录入细化到险种信息一层
				tLCContPlanRiskDB = new LCContPlanRiskDB();
				tLCContPlanRiskDB.setProposalGrpContNo(mLCContPlanDutyParamSet
						.get(m).getProposalGrpContNo());
				tLCContPlanRiskDB.setMainRiskCode(mLCContPlanDutyParamSet
						.get(m).getMainRiskCode());
				tLCContPlanRiskDB.setRiskCode(mLCContPlanDutyParamSet.get(m)
						.getRiskCode());
				tLCContPlanRiskDB.setContPlanCode(mLCContPlanDutyParamSet
						.get(m).getContPlanCode());
				tLCContPlanRiskDB.setPlanType(this.mLCContPlanSchema
						.getPlanType());
				if (tLCContPlanRiskDB.getInfo()) {
					// 如果是新增的时候，则需要出错处理
					if (this.mOperate.compareTo("INSERT||MAIN") == 0) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LCContPlanBL";
						tError.functionName = "checkData";
						if (tLCContPlanRiskDB.getContPlanCode().compareTo("00") != 0) {
							tError.errorMessage = "该团单"
									+ tLCContPlanRiskDB.getGrpContNo() + "保险计划"
									+ tLCContPlanRiskDB.getContPlanCode()
									+ "中，已存在" + tLCContPlanRiskDB.getRiskCode()
									+ "险种信息，如果你要变动，请使用保险计划修改功能！";
						} else {
							tError.errorMessage = "该团单"
									+ tLCContPlanRiskDB.getGrpContNo() + "已存在"
									+ tLCContPlanRiskDB.getRiskCode()
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
			String plantype = mLCContPlanSchema.getPlanType();
			logger.debug("是不是保险套餐:" + plantype);
			if (plantype.equals("1")) {
				// 如果都具有，还要把GRPPOLNO保存到险种责任要素里mLCContPlanDutyParam。setgrppolno
				String plancode = mLCContPlanSchema.getRemark();
				LDPlanRiskDB mLDPlanRiskDB = new LDPlanRiskDB();
				LDPlanRiskSet mLDPlanRiskSet = new LDPlanRiskSet();
				String sql = "select  * FROM ldplanrisk where contplancode='"
						+ plancode + "'";
				logger.debug("+++++++++++" + sql);
				mLDPlanRiskSet = mLDPlanRiskDB.executeQuery(sql);
				if (mLDPlanRiskSet == null || mLDPlanRiskSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "LCContPlanBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保险套餐不存在或定义错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= mLDPlanRiskSet.size(); i++) {
					LCGrpPolDB mLCGrpPol = new LCGrpPolDB();
					LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
					String tsql = "select * From lcgrppol where riskcode='"
							+ mLDPlanRiskSet.get(i).getRiskCode()
							+ "' and grpcontno='"
							+ mLCContPlanSchema.getGrpContNo() + "'";
					logger.debug(tsql);
					mLCGrpPolSet = mLCGrpPol.executeQuery(tsql);
					if (mLCGrpPolSet == null || mLCGrpPolSet.size() == 0) {
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
								+ mLCContPlanDutyParamSet.size());
						for (int j = 1; j <= mLCContPlanDutyParamSet.size(); j++) {
							logger.debug("\\\\\\\\\\\\:"
									+ mLCContPlanDutyParamSet.get(j)
											.getRiskCode());
							logger.debug("\\\\\\\\\\\\:"
									+ mLCGrpPolSet.get(1).getRiskCode());
							if (mLCContPlanDutyParamSet.get(j).getRiskCode()
									.equals(mLCGrpPolSet.get(1).getRiskCode())) {
								mLCContPlanDutyParamSet.get(j).setGrpPolNo(
										mLCGrpPolSet.get(1).getGrpPolNo());
							}
						}
					}
				}
			}

			// 循环保险计划责任要素值集合，添加了主险校验
			for (int i = 1; i <= this.mLCContPlanDutyParamSet.size(); i++) {
				if (RiskCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
						.getRiskCode()) != 0
						|| MainRiskCode.compareTo(this.mLCContPlanDutyParamSet
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
					RiskCode = this.mLCContPlanDutyParamSet.get(i)
							.getRiskCode();
					MainRiskCode = this.mLCContPlanDutyParamSet.get(i)
							.getMainRiskCode();
					DutyCode = "";

					// 准备保单险种保险计划表数据，只与险种和计划挂钩，不关心责任、要素信息
					mLCContPlanRiskSchema = new LCContPlanRiskSchema();
					mLCContPlanRiskSchema.setPlanType(this.mLCContPlanSchema
							.getPlanType());
					mLCContPlanRiskSchema
							.setGrpContNo(this.mLCContPlanDutyParamSet.get(i)
									.getGrpContNo());
					mLCContPlanRiskSchema
							.setProposalGrpContNo(this.mLCContPlanDutyParamSet
									.get(i).getProposalGrpContNo());
					mLCContPlanRiskSchema
							.setRiskCode(this.mLCContPlanDutyParamSet.get(i)
									.getRiskCode());
					mLCContPlanRiskSchema
							.setRiskVersion(this.mLCContPlanDutyParamSet.get(i)
									.getRiskVersion());
					mLCContPlanRiskSchema
							.setContPlanCode(this.mLCContPlanDutyParamSet
									.get(i).getContPlanCode());
					mLCContPlanRiskSchema
							.setContPlanName(this.mLCContPlanDutyParamSet
									.get(i).getContPlanName());
					mLCContPlanRiskSchema
							.setMainRiskCode(this.mLCContPlanDutyParamSet
									.get(i).getMainRiskCode());
					mLCContPlanRiskSchema
							.setMainRiskVersion(this.mLCContPlanDutyParamSet
									.get(i).getMainRiskVersion());
					mLCContPlanRiskSchema
							.setOperator(this.mGlobalInput.Operator);
					mLCContPlanRiskSchema.setMakeDate(PubFun.getCurrentDate());
					mLCContPlanRiskSchema.setMakeTime(PubFun.getCurrentTime());
					mLCContPlanRiskSchema
							.setModifyDate(PubFun.getCurrentDate());
					mLCContPlanRiskSchema
							.setModifyTime(PubFun.getCurrentTime());
					mLCContPlanRiskSet.add(mLCContPlanRiskSchema);

					// 新险种的第一个责任项校验
					if (DutyCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 责任更换
						DutyCode = this.mLCContPlanDutyParamSet.get(i)
								.getDutyCode();
						// 如果要素信息是Deductible则，需要对要素信息做一定处理
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ DutyCode + "'";
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(lssql);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLCContPlanSchema.getPlanType().compareTo("0") == 0) {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ DutyCode + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ DutyCode + "'";
						}
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(mSql);
					}
					k = 1;
				} else {
					// 同一险种下的不同责任校验
					if (DutyCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
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
						DutyCode = this.mLCContPlanDutyParamSet.get(i)
								.getDutyCode();
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ DutyCode + "'";
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(lssql);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						k = 1;
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLCContPlanSchema.getPlanType().compareTo("0") == 0) {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ DutyCode + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ RiskCode
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ DutyCode + "'";
						}
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(mSql);
					} else {
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ DutyCode + "'";
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(lssql);
								mLCContPlanDutyParamSet.get(i)
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
			// mLCContPlanSchema = new LCContPlanSchema();
			// mLCContPlanSchema.setGrpContNo(this.mLCContPlanDutyParamSet.get(1).
			// getGrpContNo());
			// mLCContPlanSchema.setProposalGrpContNo(this.mLCContPlanDutyParamSet.
			// get(1).getProposalGrpContNo());
			// mLCContPlanSchema.setContPlanCode(this.mLCContPlanDutyParamSet.get(
			// 1).
			// getContPlanCode());
			// mLCContPlanSchema.setContPlanName(this.mLCContPlanDutyParamSet.get(
			// 1).
			// getContPlanName());
			// mLCContPlanSchema.setPlanType(this.mPlanType); //计划类别，此处全部为固定计划
			// mLCContPlanSchema.setPlanSql(this.mPlanSql); //分类说明
			// mLCContPlanSchema.setPeoples3(this.mPeoples3);//可保人数
			mLCContPlanSchema.setOperator(this.mGlobalInput.Operator);
			mLCContPlanSchema.setMakeDate(PubFun.getCurrentDate());
			mLCContPlanSchema.setMakeTime(PubFun.getCurrentTime());
			mLCContPlanSchema.setModifyDate(PubFun.getCurrentDate());
			mLCContPlanSchema.setModifyTime(PubFun.getCurrentTime());
			mLCContPlanSet.add(mLCContPlanSchema);
		}
		// 由于删除操作可能没有multine数据，因此需要一些特殊参数，并且根据参数查询要删除的数据
		if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
			// 准备保单保险计划的删除数据
			LCContPlanDB tLCContPlanDB = new LCContPlanDB();
			tLCContPlanDB.setGrpContNo(this.mLCContPlanSchema.getGrpContNo());
			tLCContPlanDB.setContPlanCode(this.mLCContPlanSchema
					.getContPlanCode());
			tLCContPlanDB.setPlanType(this.mLCContPlanSchema.getPlanType());
			tLCContPlanDB.setProposalGrpContNo(this.mLCContPlanSchema
					.getProposalGrpContNo());
			mLCContPlanSet = tLCContPlanDB.query();
			// 准备保单险种保险计划的删除数据
			LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
			tLCContPlanRiskDB.setGrpContNo(this.mLCContPlanSchema
					.getGrpContNo());
			tLCContPlanRiskDB.setContPlanCode(this.mLCContPlanSchema
					.getContPlanCode());
			tLCContPlanRiskDB.setPlanType(this.mLCContPlanSchema.getPlanType());
			tLCContPlanRiskDB.setProposalGrpContNo(this.mLCContPlanSchema
					.getProposalGrpContNo());
			mLCContPlanRiskSet = tLCContPlanRiskDB.query();
			if (mLCContPlanSet.size() > 0 && mLCContPlanRiskSet.size() > 0) {
				if (mLCContPlanSet.get(1).getPlanType().equals("1")) {
					for (int i = 1; i <= mLCContPlanRiskSet.size(); i++) {

						LCContPlanRiskDB ttLCContPlanRiskDB = new LCContPlanRiskDB();
						ttLCContPlanRiskDB.setGrpContNo(this.mLCContPlanSchema
								.getGrpContNo());
						ttLCContPlanRiskDB.setRiskCode(mLCContPlanRiskSet
								.get(i).getRiskCode());
						int planRiskCount = ttLCContPlanRiskDB.getCount();
						if (ttLCContPlanRiskDB.mErrors.needDealError()) {
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "GroupRiskBL";
							tError.functionName = "deleteData";
							tError.errorMessage = "查询保险计划时失败!";
							this.mErrors.addOneError(tError);
							return false;

						}
						if (planRiskCount == 1) {
							LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
							tLCGrpPolDB.setGrpContNo(this.mLCContPlanSchema
									.getGrpContNo());
							tLCGrpPolDB.setRiskCode(mLCContPlanRiskSet.get(i)
									.getRiskCode());
							LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
							if (tLCGrpPolSet == null
									|| tLCGrpPolSet.size() <= 0) {
								// @@错误处理
								CError tError = new CError();
								tError.moduleName = "GrpContBL";
								tError.functionName = "dealData";
								tError.errorMessage = "请您确认：要删除的集体险种代码传入错误!";
								this.mErrors.addOneError(tError);
								return false;
							}
							mLCGrpPolSet.add(tLCGrpPolSet.get(1));
						}
					}
				}
			}
			// 准备保险计划责任要素值的删除数据
			LCContPlanDutyParamDB tLCContPlanDutyParamDB = new LCContPlanDutyParamDB();
			tLCContPlanDutyParamDB.setGrpContNo(this.mLCContPlanSchema
					.getGrpContNo());
			tLCContPlanDutyParamDB.setContPlanCode(this.mLCContPlanSchema
					.getContPlanCode());
			tLCContPlanDutyParamDB.setPlanType(this.mLCContPlanSchema
					.getPlanType());
			tLCContPlanDutyParamDB.setProposalGrpContNo(this.mLCContPlanSchema
					.getProposalGrpContNo());
			mLCContPlanDutyParamSet = tLCContPlanDutyParamDB.query();
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
		this.mLCContPlanDutyParamSet.set((LCContPlanDutyParamSet) cInputData
				.getObjectByObjectName("LCContPlanDutyParamSet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mLCContPlanSchema.setSchema((LCContPlanSchema) cInputData
				.getObjectByObjectName("LCContPlanSchema", 0));
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
		LCContPlanDB tLCContPlanDB = new LCContPlanDB();
		tLCContPlanDB.setSchema(this.mLCContPlanSchema);
		this.mLCContPlanSet = tLCContPlanDB.query();
		this.mResult.add(this.mLCContPlanSet);
		logger.debug("End LCContPlanBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCContPlanDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContPlanDB.mErrors);
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
			this.mInputData.add(this.mLCContPlanSet);
			this.mInputData.add(this.mLCContPlanRiskSet);
			this.mInputData.add(this.mLCContPlanDutyParamSet);
			this.mInputData.add(this.mLCGrpPolSet);
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
