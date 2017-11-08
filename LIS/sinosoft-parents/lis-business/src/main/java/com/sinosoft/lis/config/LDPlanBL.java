package com.sinosoft.lis.config;

import org.apache.log4j.Logger;

/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LDPlanDutyParamDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.db.LMRiskDutyFactorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDPlanRiskSchema;
import com.sinosoft.lis.schema.LDPlanSchema;
import com.sinosoft.lis.vschema.LDPlanDutyParamSet;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.lis.vschema.LDPlanSet;
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
public class LDPlanBL {
	private static Logger logger = Logger.getLogger(LDPlanBL.class);

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
	private LDPlanSchema mLDPlanSchema = new LDPlanSchema();
	private LDPlanSet mLDPlanSet = new LDPlanSet();
	private LDPlanRiskSchema mLDPlanRiskSchema = new LDPlanRiskSchema();
	private LDPlanRiskSet mLDPlanRiskSet = new LDPlanRiskSet();
	private LDPlanDutyParamSet mLDPlanDutyParamSet = new LDPlanDutyParamSet();
	private String mContPlanCode = ""; // 保险计划或默认值编码
	private String mPlanType = "";
	private String mPlanSql = "";
	private String mSql = "";
	private String mPeoples3 = "";
	private String mRemark = "";
	private String mPlanKind1 = "";
	private String mPlanKind2 = "";
	private String mPlanKind3 = "";
	private String mManageCom = "";

	public LDPlanBL() {
	}

	public static void main(String[] args) {
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
			tError.moduleName = "LDPlanBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据处理失败LDPlanBL-->checkData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LDPlanBL-->dealData!";
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
			logger.debug("Start LDPlanBL Submit...");
			LDPlanBLS tLDPlanBLS = new LDPlanBLS();
			tLDPlanBLS.submitData(mInputData, cOperate);
			logger.debug("End LDPlanBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tLDPlanBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDPlanBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDPlanBL";
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
		// 暂时不需要检验,wangyc
		// //如果是删除或修改操作，需要校验是否有被保人拥有该计划，如果有，则该计划不允许删除、修改
		// if (this.mOperate.compareTo("DELETE||MAIN") == 0 ||
		// this.mOperate.compareTo("UPDATE||MAIN") == 0)
		// {
		// String tSql = "";
		// ExeSQL texeSQL = new ExeSQL();
		// /*
		// 判定是计划还是默认值处理
		// 如果是默认值则，则只要保单下有被保人拥有险种信息后，就不可删除、修改
		// 如果是保险计划，则如果属于该计划的被保人拥有险种信息后，就不可删除、修改
		// */
		// if (this.mContPlanCode.compareTo("00") == 0)
		// {
		// tSql =
		// "select count(1) from LCInsured a,LCPol b where a.PrtNo = b.PrtNo " +
		// " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo " +
		// " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '" +
		// this.mGrpContNo + "'";
		// }
		// else
		// {
		// tSql =
		// "select count(1) from LCInsured a,LCPol b where a.PrtNo = b.PrtNo " +
		// " and a.GrpContNo = b.GrpContNo and a.ContNo = b.ContNo " +
		// " and a.InsuredNo = b.InsuredNo and a.GrpContNo = '" +
		// this.mGrpContNo + "' and a.ContPlanCode = '" +
		// this.mContPlanCode + "'";
		// }
		// SSRS ssrs = texeSQL.execSQL(tSql);
		// if (ssrs.GetText(1, 1).compareTo("0") > 0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "LDPlanBL";
		// tError.functionName = "checkData";
		// tError.errorMessage = "该团单" +
		// this.mLDPlanDutyParamSet.get(1).
		// getGrpContNo() + "下，有被保人拥有险种信息！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// // LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		// // LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		// // tLCInsuredDB.setContPlanCode(this.mContPlanCode);
		// // tLCInsuredDB.setGrpContNo(this.mGrpContNo);
		// // tLCInsuredSet = tLCInsuredDB.query();
		// // if (tLCInsuredSet.size() > 0)
		// // {
		// // CError tError = new CError();
		// // tError.moduleName = "LDPlanBL";
		// // tError.functionName = "checkData";
		// // tError.errorMessage = "该团单" +
		// // this.mLDPlanDutyParamSet.get(1).
		// // getGrpContNo() + "下，有被保人拥有该" +
		// // this.mLDPlanDutyParamSet.get(1).
		// // getContPlanCode() + "保险计划！";
		// // this.mErrors.addOneError(tError);
		// // return false;
		// // }
		// }
		// else
		// {
		// LDPlanRiskDB tLDPlanRiskDB = new LDPlanRiskDB();
		// for (int m = 1; m <= mLDPlanDutyParamSet.size(); m++)
		// {
		// //改用这样的查询方式，可以把录入细化到险种信息一层
		// tLDPlanRiskDB = new LDPlanRiskDB();
		// tLDPlanRiskDB.setProposalGrpContNo(mLDPlanDutyParamSet.
		// get(m).getProposalGrpContNo());
		// tLDPlanRiskDB.setMainRiskCode(mLDPlanDutyParamSet.get(m).
		// getMainRiskCode());
		// tLDPlanRiskDB.setRiskCode(mLDPlanDutyParamSet.get(m).
		// getRiskCode());
		// tLDPlanRiskDB.setContPlanCode(mLDPlanDutyParamSet.get(m).
		// getContPlanCode());
		// tLDPlanRiskDB.setPlanType(mPlanType);
		// if (tLDPlanRiskDB.getInfo())
		// {
		// //如果是新增的时候，则需要出错处理
		// if (this.mOperate.compareTo("INSERT||MAIN") == 0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LDPlanBL";
		// tError.functionName = "checkData";
		// if (tLDPlanRiskDB.getContPlanCode().compareTo("00") !=
		// 0)
		// {
		// tError.errorMessage = "该团单" +
		// tLDPlanRiskDB.
		// getGrpContNo() +
		// "保险计划" +
		// tLDPlanRiskDB.
		// getContPlanCode() + "中，已存在" +
		// tLDPlanRiskDB.getRiskCode() +
		// "险种信息，如果你要变动，请使用保险计划修改功能！";
		// }
		// else
		// {
		// tError.errorMessage = "该团单" +
		// tLDPlanRiskDB.
		// getGrpContNo() +
		// "已存在" +
		// tLDPlanRiskDB.getRiskCode() +
		// "险种默认值信息，如果您要变动，请使用险种信息更新功能！";
		// }
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		// }
		// }
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

			// 循环保险计划责任要素值集合，添加了主险校验
			for (int i = 1; i <= this.mLDPlanDutyParamSet.size(); i++) {
				if (RiskCode.compareTo(this.mLDPlanDutyParamSet.get(i)
						.getRiskCode()) != 0
						|| MainRiskCode.compareTo(this.mLDPlanDutyParamSet.get(
								i).getMainRiskCode()) != 0) {
					// 当险种更换后，校验要素个数，且第一次进入时不校验
					if (RiskCode.compareTo("") != 0
							&& k != tLMRiskDutyFactorSet.size()) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LDPlanBL";
						tError.functionName = "dealData";
						tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode
								+ "！";
						this.mErrors.addOneError(tError);
						return false;
					}
					// 险种更换，则责任编码初始化
					RiskCode = this.mLDPlanDutyParamSet.get(i).getRiskCode();
					MainRiskCode = this.mLDPlanDutyParamSet.get(i)
							.getMainRiskCode();
					DutyCode = "";

					// 准备保单险种保险计划表数据，只与险种和计划挂钩，不关心责任、要素信息
					mLDPlanRiskSchema = new LDPlanRiskSchema();
					mLDPlanRiskSchema.setPlanType(this.mPlanType);
					mLDPlanRiskSchema.setRiskCode(this.mLDPlanDutyParamSet.get(
							i).getRiskCode());
					mLDPlanRiskSchema.setRiskVersion(this.mLDPlanDutyParamSet
							.get(i).getRiskVersion());
					mLDPlanRiskSchema.setContPlanCode(this.mLDPlanDutyParamSet
							.get(i).getContPlanCode());
					mLDPlanRiskSchema.setContPlanName(this.mLDPlanDutyParamSet
							.get(i).getContPlanName());
					mLDPlanRiskSchema.setMainRiskCode(this.mLDPlanDutyParamSet
							.get(i).getMainRiskCode());
					mLDPlanRiskSchema
							.setMainRiskVersion(this.mLDPlanDutyParamSet.get(i)
									.getMainRiskVersion());
					mLDPlanRiskSchema.setOperator(this.mGlobalInput.Operator);
					mLDPlanRiskSchema.setMakeDate(PubFun.getCurrentDate());
					mLDPlanRiskSchema.setMakeTime(PubFun.getCurrentTime());
					mLDPlanRiskSchema.setModifyDate(PubFun.getCurrentDate());
					mLDPlanRiskSchema.setModifyTime(PubFun.getCurrentTime());
					mLDPlanRiskSet.add(mLDPlanRiskSchema);

					// 新险种的第一个责任项校验
					if (DutyCode.compareTo(this.mLDPlanDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 责任更换
						DutyCode = this.mLDPlanDutyParamSet.get(i)
								.getDutyCode();
						// 如果要素信息是Deductible则，需要对要素信息做一定处理
						if (this.mLDPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLDPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
								sqlbv1.sql(lssql);
								sqlbv1.put("DutyCode", DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv1);
								mLDPlanDutyParamSet.get(i).setCalFactorValue(
										ssrs.GetText(1, 1));
							}
						}
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mPlanType.compareTo("0") == 0) {
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
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(mSql);
						sqlbv2.put("RiskCode", RiskCode);
						sqlbv2.put("DutyCode", DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB.executeQuery(sqlbv2);
					}
					k = 1;
				} else {
					// 同一险种下的不同责任校验
					if (DutyCode.compareTo(this.mLDPlanDutyParamSet.get(i)
							.getDutyCode()) != 0) {
						// 同一险种下，责任更换时，校验要素个数
						if (k != tLMRiskDutyFactorSet.size()) {
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "LDPlanBL";
							tError.functionName = "dealData";
							tError.errorMessage = "1险种" + RiskCode + "下的"
									+ DutyCode + "责任要素信息有误！";
							this.mErrors.addOneError(tError);
							return false;
						}
						// 责任更换
						DutyCode = this.mLDPlanDutyParamSet.get(i)
								.getDutyCode();
						if (this.mLDPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLDPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
								sqlbv3.sql(lssql);
								sqlbv3.put("DutyCode", DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv3);
								mLDPlanDutyParamSet.get(i).setCalFactorValue(
										ssrs.GetText(1, 1));
							}
						}
						k = 1;
						// 查询当前险种、当前责任下的要素个数
						tLMRiskDutyFactorDB = new LMRiskDutyFactorDB();
						// 判定是险种信息录入，还是保障计划录入
						if (this.mPlanType.compareTo("0") == 0) {
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
						sqlbv4.put("RiskCode", RiskCode);
						sqlbv4.put("DutyCode", DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(sqlbv4);
					} else {
						if (this.mLDPlanDutyParamSet.get(i).getCalFactor()
								.compareTo("Deductible") == 0) {
							if (this.mLDPlanDutyParamSet.get(i)
									.getCalFactorValue().compareTo("") == 0) {
								String lssql = "select b.Deductible from LMDutyGetRela a,LMDutyGetClm b where a.GetDutyCode = b.GetDutyCode and a.DutyCode = '"
										+ "?DutyCode?" + "'";
								SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
								sqlbv5.sql(lssql);
								sqlbv5.put("DutyCode", DutyCode);
								ExeSQL exeSQL = new ExeSQL();
								SSRS ssrs = exeSQL.execSQL(sqlbv5);
								mLDPlanDutyParamSet.get(i).setCalFactorValue(
										ssrs.GetText(1, 1));
							}
						}
						k = k + 1;
					}
				}
			}
			if (k != tLMRiskDutyFactorSet.size()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDPlanBL";
				tError.functionName = "dealData";
				tError.errorMessage = "险种" + RiskCode + "下的" + DutyCode
						+ "责任要素信息有误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 准备保单保险计划表数据
			mLDPlanSchema = new LDPlanSchema();
			mLDPlanSchema.setContPlanCode(this.mLDPlanDutyParamSet.get(1)
					.getContPlanCode());
			mLDPlanSchema.setContPlanName(this.mLDPlanDutyParamSet.get(1)
					.getContPlanName());
			mLDPlanSchema.setPlanType(this.mPlanType); // 计划类别，此处全部为固定计划
			mLDPlanSchema.setPlanSql(this.mPlanSql); // 分类说明
			mLDPlanSchema.setPeoples3(this.mPeoples3); // 可保人数
			mLDPlanSchema.setRemark(this.mRemark);
			mLDPlanSchema.setOperator(this.mGlobalInput.Operator);
			mLDPlanSchema.setMakeDate(PubFun.getCurrentDate());
			mLDPlanSchema.setMakeTime(PubFun.getCurrentTime());
			mLDPlanSchema.setModifyDate(PubFun.getCurrentDate());
			mLDPlanSchema.setModifyTime(PubFun.getCurrentTime());
			mLDPlanSchema.setPlanKind1(this.mPlanKind1);
			mLDPlanSchema.setPlanKind2(this.mPlanKind2);
			mLDPlanSchema.setPlankind3(this.mPlanKind3);
			mLDPlanSchema.setManageCom(this.mManageCom);
			mLDPlanSchema.setState("9");// 无扫描录入保障计划查询时会查询State=‘9’的
			mLDPlanSet.add(mLDPlanSchema);
		}
		// 由于删除操作可能没有multine数据，因此需要一些特殊参数，并且根据参数查询要删除的数据
		if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
			// 准备保单保险计划的删除数据
			LDPlanDB tLDPlanDB = new LDPlanDB();
			tLDPlanDB.setContPlanCode(this.mContPlanCode);
			tLDPlanDB.setPlanType(this.mPlanType);
			mLDPlanSet = tLDPlanDB.query();

			// 准备保单险种保险计划的删除数据
			LDPlanRiskDB tLDPlanRiskDB = new LDPlanRiskDB();
			tLDPlanRiskDB.setContPlanCode(this.mContPlanCode);
			tLDPlanRiskDB.setPlanType(this.mPlanType);
			mLDPlanRiskSet = tLDPlanRiskDB.query();

			// 准备保险计划责任要素值的删除数据
			LDPlanDutyParamDB tLDPlanDutyParamDB = new LDPlanDutyParamDB();
			tLDPlanDutyParamDB.setContPlanCode(this.mContPlanCode);
			tLDPlanDutyParamDB.setPlanType(this.mPlanType);
			mLDPlanDutyParamSet = tLDPlanDutyParamDB.query();
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
		this.mLDPlanDutyParamSet.set((LDPlanDutyParamSet) cInputData
				.getObjectByObjectName("LDPlanDutyParamSet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mContPlanCode = (String) cInputData.get(2);
		this.mPlanType = (String) cInputData.get(3);
		this.mPlanSql = (String) cInputData.get(4);
		this.mPeoples3 = (String) cInputData.get(5);
		this.mRemark = (String) cInputData.get(6);
		this.mPlanKind1 = (String) cInputData.get(7);
		this.mPlanKind2 = (String) cInputData.get(8);
		this.mPlanKind3 = (String) cInputData.get(9);
		this.mManageCom = (String) cInputData.get(10);

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start LDPlanBLQuery Submit...");
		LDPlanDB tLDPlanDB = new LDPlanDB();
		tLDPlanDB.setSchema(this.mLDPlanSchema);
		this.mLDPlanSet = tLDPlanDB.query();
		this.mResult.add(this.mLDPlanSet);
		logger.debug("End LDPlanBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDPlanDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDPlanDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanBL";
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
			this.mInputData.add(this.mLDPlanSet);
			this.mInputData.add(this.mLDPlanRiskSet);
			this.mInputData.add(this.mLDPlanDutyParamSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanBL";
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
