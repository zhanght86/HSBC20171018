package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

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

public class CombinationConfigBL {
private static Logger logger = Logger.getLogger(CombinationConfigBL.class);
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
	private String mPortfolioflag = "";

	public CombinationConfigBL() {
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
			tError.moduleName = "CombinationConfigBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据处理失败CombinationConfigBL-->checkData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CombinationConfigBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败CombinationConfigBL-->dealData!";
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
			logger.debug("Start CombinationConfigBL Submit...");
			CombinationConfigBLS tCombinationConfigBLS = new CombinationConfigBLS();
			tCombinationConfigBLS.submitData(mInputData, cOperate);
			logger.debug("End CombinationConfigBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tCombinationConfigBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tCombinationConfigBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "CombinationConfigBL";
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

			//校验保额比例是否正确
			if(!checkAmntRate()){
				return false;
			}
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
						tError.moduleName = "CombinationConfigBL";
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
					mLDPlanRiskSchema.setPortfolioFlag("1");//产品组合   所以写死为1 
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
								sqlbv1.put("DutyCode",DutyCode);
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
									+ "' and ChooseFlag in ('3') and DutyCode = '"
									+ "?DutyCode?" + "'";
						} else {
							mSql = "select * from LMRiskDutyFactor where RiskCode = '"
									+ "?RiskCode?"
									+ "' and ChooseFlag in ('1','2') and DutyCode = '"
									+ "?DutyCode?" + "'";
						}
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(mSql);
						sqlbv2.put("RiskCode",RiskCode);
						sqlbv2.put("DutyCode",DutyCode);
						tLMRiskDutyFactorSet = tLMRiskDutyFactorDB
								.executeQuery(sqlbv2);
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
							tError.moduleName = "CombinationConfigBL";
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
								sqlbv3.put("DutyCode",DutyCode);
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
						sqlbv4.put("RiskCode",RiskCode);
						sqlbv4.put("DutyCode",DutyCode);
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
								sqlbv5.put("DutyCode",DutyCode);
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
				tError.moduleName = "CombinationConfigBL";
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
			mLDPlanSchema.setPortfolioFlag(mPortfolioflag);// 无扫描录入保障计划查询时会查询State=‘9’的
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
		this.mPortfolioflag = (String) cInputData.get(11);

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start CombinationConfigBLQuery Submit...");
		LDPlanDB tLDPlanDB = new LDPlanDB();
		tLDPlanDB.setSchema(this.mLDPlanSchema);
		this.mLDPlanSet = tLDPlanDB.query();
		this.mResult.add(this.mLDPlanSet);
		logger.debug("End CombinationConfigBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDPlanDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDPlanDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CombinationConfigBL";
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
			tError.moduleName = "CombinationConfigBL";
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
	
	/**
	 * 校验保额比例是否正确（总和是否为1）
	 * 与客户沟通确定小数只保留三位(49.5%)
	 * 超过三位调用PubFun.round方法进行四舍五入
	 * */
	private boolean checkAmntRate(){
		if(mPlanKind1==null||"".equals(mPlanKind1)){
			CError.buildErr(this, "必须录入保额比例！");
			return false;
		}
		double tdou2=0;
		try {
			String tstr2[] = mPlanKind1.split("/");
			double tdou = 0;
			tdou2 = 0;
			for(int i=0;i<tstr2.length;i++){
				logger.debug(tstr2[i]);
				tdou = PubFun.round(Double.parseDouble(tstr2[i]),3);
				tdou2 = tdou2+tdou;
			}
			if(!String.valueOf(tstr2.length).equals(mPeoples3)){
				CError.buildErr(this, "录入的保额分配比例与可保人数不符");
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			CError.buildErr(this, "请录入正确的保额比例格式!");
			return false;
		}
		if(1!=tdou2){
			CError.buildErr(this, "保额比例总和不为1！");
			return false;
		}else{
			logger.debug("保额比例总和为1，校验保额比例完毕");
		}
		return true;
	}
}
