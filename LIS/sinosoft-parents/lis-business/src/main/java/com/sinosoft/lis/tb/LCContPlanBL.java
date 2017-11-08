/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanDutyParamDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LMRiskDutyFactorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
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
		if (this.mOperate.compareTo("DELETE||MAIN") != 0) {
			if (!checkSubRisk()) {
				return false;
			}
		}
		// 在保险计划增加前，进行验证判断该保险计划是不是已经存在
		if (this.mOperate.compareTo("INSERT||MAIN") == 0) {
			String tGrpContNo = mLCContPlanSchema.getGrpContNo();
			String tContPlanCode = mLCContPlanSchema.getContPlanCode();
			String tContPlanType = mLCContPlanSchema.getPlanType();
			if (!tContPlanType.equals("3")) {
				String checkSql = "select * from LCContPlan where GrpContNo='"
						+ "?GrpContNo?" + "' and ContPlanCode='" + "?ContPlanCode?"
						+ "' ";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(checkSql);
				sqlbv.put("GrpContNo", tGrpContNo);
				sqlbv.put("ContPlanCode", tContPlanCode);
				ExeSQL texeSQL = new ExeSQL();
				SSRS tsrs = new SSRS();
				tsrs = texeSQL.execSQL(sqlbv);
				if (tsrs.getMaxRow() > 0) {
					this.buildError("CheckData", "该险种计划已经存在");
					return false;
				}
			}
		}
		// 如果是删除或修改操作，需要校验是否有被保人拥有该计划，如果有，则该计划不允许删除、修改
		if (this.mOperate.compareTo("DELETE||MAIN") == 0
				|| this.mOperate.compareTo("UPDATE||MAIN") == 0) {
			ExeSQL texeSQL = new ExeSQL();
			String tSql = "";
			SSRS ssrs = new SSRS();
			String tMsg = "";
			/*
			 * 判定是计划还是默认值处理 如果是默认值则，则只要保单下有被保人拥有险种信息后，就不可删除、修改
			 * 如果是保险计划，则如果属于该计划的被保人拥有险种信息后，就不可删除、修改
			 */
			if (this.mLCContPlanSchema.getContPlanCode().compareTo("00") == 0) {
				tSql = "select count(1) from LCInsured a , LCPol b where a.PrtNo = b.PrtNo "
						+ " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo "
						+ " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '"
						+ "?GrpContNo?"
						+ "'"
						+ " and b.RiskCode='"
						+ "?RiskCode?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSql);
				sqlbv1.put("GrpContNo", mLCContPlanDutyParamSet.get(1).getGrpContNo());
				sqlbv1.put("RiskCode", mLCContPlanDutyParamSet.get(1).getRiskCode());
				tMsg = "该团单" + mLCContPlanDutyParamSet.get(1).getGrpContNo()
						+ "下，有被保人拥有该险种信息！";
				ssrs = texeSQL.execSQL(sqlbv1);
				if (ssrs.GetText(1, 1).compareTo("0") > 0) {
					this.buildError("CheckData", tMsg);
					return false;
				}

				// -------------------------------------Beg
				// Add by:chenrong
				// Add date:2006.6.14
				// 如果其它保险计划中存在此险种，则不能删除险种信息
				if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
					tSql = "select (" + tSql + ")+"
							+ "(select count(1) from LCContPlanRisk a "
							+ "where a.GrpContNo='"
							+ "?GrpContNo?"
							+ "'" + " and a.RiskCode='"
							+ "?RiskCode?"
							+ "' and contplancode not in ('00')) from dual";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSql);
					sqlbv2.put("GrpContNo", mLCContPlanDutyParamSet.get(1).getGrpContNo());
					sqlbv2.put("RiskCode", mLCContPlanDutyParamSet.get(1).getRiskCode());
					tMsg = "该团单"
							+ mLCContPlanDutyParamSet.get(1).getGrpContNo()
							+ "下，有被保人或险种计划拥有该险种信息！";
					ssrs = texeSQL.execSQL(sqlbv2);
					if (ssrs.GetText(1, 1).compareTo("0") > 0) {
						this.buildError("CheckData", tMsg);
						return false;
					}

				}
				// ------------------------------------End
			} else {
				tSql = "select count(1) from LCInsured a , LCPol b where a.PrtNo = b.PrtNo "
						+ " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo "
						+ " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '"
						+ "?GrpContNo?"
						+ "' and a.ContPlanCode = '"
						+ "?ContPlanCode?" + "'";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSql);
				sqlbv3.put("GrpContNo", this.mLCContPlanSchema.getGrpContNo());
				sqlbv3.put("ContPlanCode", this.mLCContPlanSchema.getContPlanCode());
				tMsg = "该团单" + mLCContPlanDutyParamSet.get(1).getGrpContNo()
						+ "下，有被保人拥有该险种计划！";
				ssrs = texeSQL.execSQL(sqlbv3);
				if (ssrs.GetText(1, 1).compareTo("0") > 0) {
					this.buildError("CheckData", tMsg);
					return false;
				}

				logger.debug("tSql=" + tSql);
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
						String tMsg = "";
						if (tLCContPlanRiskDB.getContPlanCode().compareTo("00") != 0) {
							tMsg = "该团单" + tLCContPlanRiskDB.getGrpContNo()
									+ "保险计划"
									+ tLCContPlanRiskDB.getContPlanCode()
									+ "中，已存在" + tLCContPlanRiskDB.getRiskCode()
									+ "险种信息，如果你要变动，请使用保险计划修改功能！";
						} else {
							tMsg = "该团单" + tLCContPlanRiskDB.getGrpContNo()
									+ "已存在" + tLCContPlanRiskDB.getRiskCode()
									+ "险种默认值信息，如果您要变动，请使用险种信息更新功能！";
						}
						this.buildError("CheckData", tMsg);
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 校验保险计划中，所录入的附加险是否有对应的主险
	 * 
	 * @param tLCContPlanDutyParamSet
	 *            LCContPlanDutyParamSet
	 * @return boolean
	 */
	private boolean checkSubRisk() {
		try {
			ExeSQL texeSQL = new ExeSQL();
			if (this.mLCContPlanSchema.getContPlanCode().compareTo("00") == 0) {
				if (mLCContPlanDutyParamSet != null
						&& mLCContPlanDutyParamSet.size() > 0) {
					String tMainRiskCode = mLCContPlanDutyParamSet.get(1)
							.getMainRiskCode();
					String tSubRiskCode = mLCContPlanDutyParamSet.get(1)
							.getRiskCode();
					// logger.debug("tMainRiskCode=" + tMainRiskCode);
					if (tMainRiskCode.equals(tSubRiskCode)) {
						return true;
					}
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql("select count(1) from lmriskrela"
							+ " where relariskcode='" + "?relariskcode?"
							+ "' and riskcode='" + "?riskcode?"
							+ "'");
					sqlbv4.put("relariskcode", tSubRiskCode);
					sqlbv4.put("riskcode", tMainRiskCode.trim());
					String tCount = texeSQL
							.getOneValue(sqlbv4);
					if (tCount.equals("0")) {
						this.buildError("checkSubRisk", "所录入附加险" + tSubRiskCode
								+ "和相应的主险" + tMainRiskCode + "不一致，请重新录入!");
						return false;
					}
				}
			} else {
				MMap tMainRiskMap = new MMap();
				MMap tSubRiskMap = new MMap();
				logger.debug("start checkSubRisk.........");
				for (int t = 1; t <= mLCContPlanDutyParamSet.size(); t++) {
					String tMainRiskCode = mLCContPlanDutyParamSet.get(t)
							.getMainRiskCode();
					String tSubRiskCode = mLCContPlanDutyParamSet.get(t)
							.getRiskCode();
					// logger.debug("tMainRiskCode=" + tMainRiskCode);
					if (tMainRiskCode.equals(tSubRiskCode)) {
						if (tMainRiskMap.get(tSubRiskCode) == null) {
							tMainRiskMap.put(tSubRiskCode, tSubRiskCode);
						}
					} else {
						if (tSubRiskMap.get(tSubRiskCode) == null) {
							tSubRiskMap.put(tSubRiskCode, tMainRiskCode);
						}
					}
				}
				if (tSubRiskMap != null) {
					logger.debug("tSubRiskMap.size="
							+ tSubRiskMap.keySet().size());
					for (int i = 0; i < tSubRiskMap.keySet().size(); i++) {
						Object key = tSubRiskMap.getKeyByOrder(String
								.valueOf(i + 1));
						String tMainRiskCode = (String) tMainRiskMap
								.get(tSubRiskMap.get(key).toString());
						// logger.debug("tMainRiskCode=" + tMainRiskCode);
						if (tMainRiskCode == null
								|| "".equals(tMainRiskCode.trim())) {
							this.buildError("CheckData", "保险计划中，附加险"
									+ key.toString() + "没有录入相应主险的责任要素!");
							return false;
						}
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql("select count(1) from lmriskrela"
								+ " where relariskcode='"
								+ "?relariskcode?"
								+ "' and riskcode='"
								+ "?riskcode?" + "'");
						sqlbv5.put("relariskcode", key.toString().trim());
						sqlbv5.put("riskcode", tMainRiskCode.trim());
						String tCount = texeSQL
								.getOneValue(sqlbv5);
						if (tCount.equals("0")) {
							this.buildError("checkSubRisk", "所录入附加险"
									+ key.toString() + "和相应的主险" + tMainRiskCode
									+ "不一致，请重新录入!");
							return false;
						}
					}
				}

			}

			return true;
		} catch (Exception ex) {
			this.buildError("checkSubRisk", ex.toString());
			return false;
		}
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
								String lssql = "select b.Deductible from LMDutyGetRela a , LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
								sqlbv6.sql(lssql);
								sqlbv6.put("DutyCode", DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv6);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mLCContPlanSchema.getPlanType().compareTo("0") == 0) {
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
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						sqlbv7.sql();
						sqlbv7.put("RiskCode", RiskCode);
						sqlbv7.put("DutyCode", DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(sqlbv7);
					}
					k = 1;
				} else {
					// 同一险种下的不同责任校验
					if (DutyCode.compareTo(this.mLCContPlanDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 同一险种下，责任更换时，校验要素个数
						if (k != tLMRiskDutyFactorSet.size()) {
							// @@错误处理
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
								String lssql = "select b.Deductible from LMDutyGetRela a , LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
								sqlbv8.sql(lssql);
								sqlbv8.put("DutyCode", DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv8);
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
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('0','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						}
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						sqlbv9.sql();
						sqlbv9.put("RiskCode", RiskCode);
						sqlbv9.put("DutyCode", DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(sqlbv9);
					} else {
						if (this.mLCContPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLCContPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a , LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
								sqlbv10.sql(lssql);
								sqlbv10.put("DutyCode", DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv10);
								mLCContPlanDutyParamSet.get(i)
										.setCalFactorValue(ssrs.GetText(1, 1));
							}
						}
						k += 1;
					}
				}
			}
			if (k != tLMRiskDutyFactorSet.size()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBL";
				tError.functionName = "dealData";
				tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode
						+ "责任要素信息有误！";
				this.mErrors.addOneError(tError);
				return false;
			}
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
			if (this.mLCContPlanSchema.getContPlanCode().equals("00")) {
				tLCContPlanRiskDB.setRiskCode(mLCContPlanDutyParamSet.get(1)
						.getRiskCode());
			}
			mLCContPlanRiskSet = tLCContPlanRiskDB.query();

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
			if (this.mLCContPlanSchema.getContPlanCode().equals("00")) {
				tLCContPlanDutyParamDB.setRiskCode(mLCContPlanDutyParamSet.get(
						1).getRiskCode());
			}
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
		// this.mProposalGrpContNo =(String) cInputData.get(2);
		// this.mGrpContNo = (String) cInputDaa.get(3);
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

	private void buildError(String mFunctionName, String mMsg) {
		CError tError = new CError();
		tError.moduleName = "LCContPlanBL";
		tError.functionName = mFunctionName;
		tError.errorMessage = mMsg;
		this.mErrors.addOneError(tError);
	}
}
