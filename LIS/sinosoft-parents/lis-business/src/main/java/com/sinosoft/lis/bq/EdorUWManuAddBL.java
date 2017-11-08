package com.sinosoft.lis.bq;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author liurx
 * @version 1.0
 */

public class EdorUWManuAddBL {
private static Logger logger = Logger.getLogger(EdorUWManuAddBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private TransferData mTransferData = new TransferData();
	/** 工作流引擎 */
	// ActivityOperator mActivityOperator = new ActivityOperator();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mEdorNo = "";
	private String mEdorType = "";
	private String mPolNo = "";
	private String mPolNo2 = "";
	private String mInsuredNo = "";
	private String mAddReason = "";
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流加费活动表任务0000000002 */
	/** 保单表 */
	private LPContSchema mLPContSchema = null;
	private LPPolSchema mLPPolSchema = null;
	/** 核保主表 */
	private LPUWMasterSchema mLPUWMasterSchema = new LPUWMasterSchema();
	/** 责任项表 */
	private LPDutySet mLPDutySet = new LPDutySet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LPDutySet mNewLPDutySet = new LPDutySet();
	/** 保费表 */
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPPremSet mOldLPPremSet = new LPPremSet();
	private LPPremSet mNewLPPremSet = new LPPremSet();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();// add
																		// by
																		// lizhuo
																		// for
																		// NS/AA
																		// at
																		// 2005-10-10
	private BqCalBL mBqCalBL = new BqCalBL();

	/** 保全批改补退费表 */
	// private LJSGetEndorseSet mOldLJSGetEndorseSet = new LJSGetEndorseSet();
	// private LJSGetEndorseSet mNewLJSGetEndorseSet = new LJSGetEndorseSet();

	public EdorUWManuAddBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start EdorUWManuAddBL Submit...");
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("EdorUWManuAddBL Submit OK!");

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保加费信息
		if (prepareAdd() == false) {
			return false;
		}

		return true;

	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(mEdorNo);
		tLPEdorItemDB.setEdorType(mEdorType);
		tLPEdorItemDB.setContNo(mContNo);
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "未查询到保全项目信息！");
			return false;
		}
		if (tLPEdorItemSet.size() == 1) {
			mLPEdorItemSchema = tLPEdorItemSet.get(1);
		} else {
			for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
				if (tLPEdorItemSet.get(i).getPolNo().equals(mPolNo)) {
					mLPEdorItemSchema = tLPEdorItemSet.get(i);
					break;
				}
			}
		}
		// 校验合同单信息

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mContNo);
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "checkData";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPContSchema = tLPContSet.get(1);
		mGrpContNo = mLPContSchema.getGrpContNo();

		// 校验保单信息
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setPolNo(mPolNo2);
		tLPPolDB.setEdorNo(mEdorNo);
		tLPPolDB.setEdorType(mEdorType);
		if (!tLPPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPPolSchema = tLPPolDB.getSchema();

		// 校验保全批单核保主表
		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setPolNo(mPolNo);
		tLPUWMasterDB.setEdorNo(mEdorNo);
		tLPUWMasterDB.setEdorType(mEdorType);
		if (!tLPUWMasterDB.getInfo()) {
			// CError tError = new CError();
			// tError.moduleName = "EdorUWManuAddBL";
			// tError.functionName = "checkData";
			// tError.errorMessage = "保单" + mContNo + "保全批单核保主表信息查询失败!";
			// this.mErrors.addOneError(tError);
			// return false;
			CError.buildErr(this, "保单" + mContNo + "保全批单核保主表信息查询失败!");
		} else {
			mLPUWMasterSchema.setSchema(tLPUWMasterDB);
		}
		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PRnewUW); // 核保通知书
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的核保通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		mLPPolSchema = (LPPolSchema) cInputData.getObjectByObjectName(
				"LPPolSchema", 0);
		mPolNo = mLPPolSchema.getPolNo();
		mPolNo2 = mLPPolSchema.getPolNo();
		mContNo = mLPPolSchema.getContNo();
		mEdorNo = mLPPolSchema.getEdorNo();
		mEdorType = mLPPolSchema.getEdorType();
		if (mPolNo == null && !mPolNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PolNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/*
		 * if ( mPolNo2 == null ) { // @@错误处理 //this.mErrors.copyAllErrors(
		 * tLPPolDB.mErrors ); CError tError = new CError(); tError.moduleName =
		 * "EdorUWManuAddBL"; tError.functionName = "getInputData";
		 * tError.errorMessage = "前台传输业务数据中PolNo2失败!"; this.mErrors
		 * .addOneError(tError) ; return false; }
		 */
		// zhr
		mAddReason = (String) cInputData.getObjectByObjectName("String", 0);
		mLPPremSet = (LPPremSet) cInputData.getObjectByObjectName("LPPremSet",
				0);

		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAdd() {
		double tSumStandPrem = 0;
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		// 取险种名称
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(mLPPolSchema.getRiskCode());
		if (!tLMRiskDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "取险种名称失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取代理人姓名
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLPPolSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorUWManuAddBL";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "取代理人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取责任信息
		LPDutyDB tLPDutyDB = new LPDutyDB();
		tLPDutyDB.setPolNo(mLPPolSchema.getPolNo());
		tLPDutyDB.setEdorNo(mLPPolSchema.getEdorNo());
		tLPDutyDB.setEdorType(mLPPolSchema.getEdorType());
		mLPDutySet = tLPDutyDB.query();
		if (mLPDutySet == null || mLPDutySet.size() <= 0) {
			CError.buildErr(this, "未查询到责任项数据，加费失败！");
			return false;
		}
		double dOldSumAddPrem_Pol = 0.0;
		double dOldSumAddPrem_Duty = 0.0;
		double dNewSumAddPrem_Pol = 0.0;
		double dNewSumAddPrem_Duty = 0.0;
		// 形成加费信息
		if (mLPPremSet.size() > 0) {
			AccountManage tAccountManage = new AccountManage();
			tAccountManage.setPayEndYear(Integer.toString(mLPPolSchema
					.getPayEndYear()));
			tAccountManage.setPayIntv(Integer.toString(mLPPolSchema
					.getPayIntv()));
			FDate tD = new FDate();
			BqCode mBqCode = new BqCode();
			Date tPayDate = tD.getDate(mLPPolSchema.getCValiDate()); // 各期交费对应日，初始化为首期交费日
			String tIntervalType = "Y"; // 交费类型，按年、月或日。
			String tSubOperType = "";

			if (mLPPolSchema.getPayIntv() == 0) {
				tIntervalType = "Y";
			}
			if (mLPPolSchema.getPayIntv() == 1) {
				tIntervalType = "M";
			} else if (mLPPolSchema.getPayIntv() == 3)// add by zhangtao
														// 2006-06-30 支持季交保单
														// 计算方式同年交
			{
				tIntervalType = "Y";
			} else if (mLPPolSchema.getPayIntv() == 12) {
				tIntervalType = "Y";
			}

			LDCode1DB tLDCode1DB = new LDCode1DB();
			tLDCode1DB.setCodeType("CM");
			tLDCode1DB.setCode("BF");
			tLDCode1DB.setCode1("000000");
			if (!tLDCode1DB.getInfo()) // 获取补费会计科目
			{
				mErrors.copyAllErrors(tLDCode1DB.mErrors);
				mErrors.addOneError(new CError("获取财务代码失败！"));
				return false;
			}
			String BFSubject = tLDCode1DB.getCodeName();

			tLDCode1DB.setCode("LX");
			if (!tLDCode1DB.getInfo()) // 获取利息会计科目
			{
				mErrors.copyAllErrors(tLDCode1DB.mErrors);
				mErrors.addOneError(new CError("获取财务代码失败！"));
				return false;
			}
			String LXSubject = tLDCode1DB.getCodeName();

			tLDCode1DB.setCode("TF");
			if (!tLDCode1DB.getInfo()) // 获取退费会计科目
			{
				mErrors.copyAllErrors(tLDCode1DB.mErrors);
				mErrors.addOneError(new CError("获取财务代码失败！"));
				return false;
			}
			String TFSubject = tLDCode1DB.getCodeName();

			// 更新责任项
			if (mLPDutySet.size() > 0) {
				for (int m = 1; m <= mLPDutySet.size(); m++) {
					dNewSumAddPrem_Duty = 0.0;
					double chgMoney = 0;
					double interestMoney = 0; // 总利息变量
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tLPDutySchema = mLPDutySet.get(m);

					// 计算除去本次加费项目,承保时的基本保费项后，该保单在该的加费项目数。以便计算本次加费的编码起始编码值.
					String tsql = "select max(payplancode) from LCPrem where  polno = '"
							+ "?polno?"
							+ "' and DutyCode = '"
							+ "?DutyCode?" +
							// "' and edorno = '" + mEdorNo + "' and edortype =
							// '" + mEdorType +
							"' and trim(payplancode) like '000000__'";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(tsql);
					sqlbv.put("polno", mLPPolSchema.getPolNo().trim());
					sqlbv.put("DutyCode", tLPDutySchema.getDutyCode());
					ExeSQL tExeSQL = new ExeSQL();
					String sCount = tExeSQL.getOneValue(sqlbv);
					if (tExeSQL.mErrors.needDealError()) {
						CError.buildErr(this, "加费查询失败!");
						return false;
					}
					int tCount;
					if (sCount == null || sCount.equals("")) {
						tCount = 0;
					} else {
						tCount = Integer.parseInt(sCount.trim());
					}

					String PayPlanCode = "";
					Reflections tReflections = new Reflections();
					// 为保单表和责任表加上本次的加费.同时形成加费信息
					for (int i = 1; i <= mLPPremSet.size(); i++) {
						int premNum = 0;

						if (mLPPolSchema.getPayIntv() == 0) {
							premNum = PubFun.calInterval2(mLPPremSet.get(i)
									.getPayStartDate(), mLPPremSet.get(i)
									.getPaytoDate(), "M");
							if (premNum != 0) {
								premNum = 1;
							}
						} else {
							premNum = PubFun.calInterval2(mLPPremSet.get(i)
									.getPayStartDate(), mLPPremSet.get(i)
									.getPaytoDate(), "M")
									/ mLPPolSchema.getPayIntv();
						}

						if (mLPPremSet.get(i).getDutyCode().equals(
								tLPDutySchema.getDutyCode())) {
							dNewSumAddPrem_Duty += mLPPremSet.get(i).getPrem();
							dNewSumAddPrem_Pol += dNewSumAddPrem_Duty;
							LPPremSchema tLPPremSchema = mLPPremSet.get(i);
							// 形成加费编码
							PayPlanCode = String.valueOf(++tCount);
							if (tCount < 10) {
								PayPlanCode = "0" + PayPlanCode;
							}
							PayPlanCode = "000000" + PayPlanCode;
							// 减去该责任的原本次加费金额
							String sql = "";
							SQLwithBindVariables sbv=new SQLwithBindVariables();
							if (tLPPremSchema.getPayPlanType().equals("01")
									|| tLPPremSchema.getPayPlanType().equals(
											"03")) {
								sql = "select * from LCPrem where PayPlanCode like '000000%' and PayPlanType in ('01', '03')"
										+ " and PolNo = '"
										+ "?PolNo?"
										+ "' and DutyCode = '"
										+ "?DutyCode?"
										+ "'";
						
								sbv.put("PolNo", mLPPolSchema.getPolNo().trim());
								sbv.put("DutyCode", tLPDutySchema.getDutyCode().trim());
								if (tLPPremSchema.getAddFeeDirect() != null
										&& !tLPPremSchema.getAddFeeDirect()
												.trim().equals("")) { // add
																		// by
																		// zhangtao
																		// 2007-04-17
																		// 团体加费不分AddFeeDirect，该字段为空
									sql += " and AddFeeDirect = '"
											+ "?AddFeeDirect?"
											+ "'";
									sbv.put("AddFeeDirect", tLPPremSchema.getAddFeeDirect());
								}
								sql += "order by PayStartDate";
						
								sbv.sql(sql);
							} else if (tLPPremSchema.getPayPlanType().equals(
									"02")
									|| tLPPremSchema.getPayPlanType().equals(
											"04")) {
								sql = "select * from LCPrem where PayPlanCode like '000000%' and PayPlanType in ('02', '04')"
										+ " and PolNo = '"
										+ "?PolNo?"
										+ "' and DutyCode = '"
										+ "?DutyCode?"
										+ "'";
							
								sbv.put("PolNo", mLPPolSchema.getPolNo().trim());
								sbv.put("DutyCode", tLPDutySchema.getDutyCode().trim());
								if (tLPPremSchema.getAddFeeDirect() != null
										&& !tLPPremSchema.getAddFeeDirect()
												.trim().equals("")) { // add
																		// by
																		// zhangtao
																		// 2007-04-17
																		// 团体加费不分AddFeeDirect，该字段为空
									sql += " and AddFeeDirect = '"
											+ "?AddFeeDirect?"
											+ "'";
					
									sbv.put("AddFeeDirect", tLPPremSchema.getAddFeeDirect());
								}
								sql += "order by PayStartDate";
								sbv.sql(sql);
							} else {
								continue;
							}

							LCPremDB tLCPremDB = new LCPremDB();
							logger.debug(sql);
							LCPremSet tLCPremSet = tLCPremDB.executeQuery(sbv);
							if (tLCPremSet != null && tLCPremSet.size() > 0) // C表已有加费记录
							{
								// 复效加费如果新的加费标准比以前低，还是按照以前的标准来收取续期保费
								if (tLCPremSet.get(1).getPrem() > tLPPremSchema
										.getPrem()) {
									if (mEdorType != null
											&& mEdorType.equals("RE")) {
										tLPPremSchema.setPrem(tLCPremSet.get(1)
												.getPrem());
										tLPPremSchema.setStandPrem(tLCPremSet
												.get(1).getStandPrem());
										tLPPremSchema
												.setSuppRiskScore(tLCPremSet
														.get(1)
														.getSuppRiskScore());
									}
								}
								if (tLPPremSchema.getPayStartDate() != null && // 追溯加费
										tLPPremSchema.getPayStartDate().equals(
												mLPPolSchema.getCValiDate())) {
									if (tLPPremSchema.getPayStartDate()
											.equals(
													tLCPremSet.get(1)
															.getPayStartDate())) {
										// tLPDutySchema.setPrem(tLPDutySchema.getPrem()
										// - tLCPremSet.get(1).getPrem());
										// mLPPolSchema.setPrem(mLPPolSchema.getPrem()
										// - tLCPremSet.get(1).getPrem());
										// mLPContSchema.setPrem(mLPContSchema.getPrem()
										// - tLCPremSet.get(1).getPrem());
										chgMoney = tLPPremSchema.getPrem()
												- tLCPremSet.get(1).getPrem();
										tLCPremSet.get(1).setPrem(
												tLPPremSchema.getPrem());
										tLCPremSet.get(1).setStandPrem(
												tLPPremSchema.getPrem());
										tLCPremSet.get(1).setSumPrem(
												tLCPremSet.get(1).getSumPrem()
														- tLCPremSet.get(1)
																.getPrem()
														+ tLPPremSchema
																.getPrem());
										tLCPremSet.get(1).setSuppRiskScore(
												tLPPremSchema
														.getSuppRiskScore());
										tLCPremSet.get(1).setSecInsuAddPoint(
												tLPPremSchema
														.getSecInsuAddPoint());
										LPPremSchema ttLPPremSchema = new LPPremSchema();
										tReflections.transFields(
												ttLPPremSchema, tLCPremSet.get(
														1).getSchema());
										ttLPPremSchema.setEdorNo(tLPPremSchema
												.getEdorNo());
										ttLPPremSchema
												.setEdorType(tLPPremSchema
														.getEdorType());
										mNewLPPremSet.add(ttLPPremSchema);
										PayPlanCode = ttLPPremSchema
												.getPayPlanCode();
									} else {
										tLPPremSchema = createNewAddFee(
												tLPPremSchema, PayPlanCode,
												premNum);
										mNewLPPremSet.add(tLPPremSchema);
										chgMoney = tLPPremSchema.getPrem();
									}
								} else {
									tLPPremSchema = createNewAddFee(
											tLPPremSchema, PayPlanCode, premNum);
									chgMoney = 0;
									for (int j = 1; j <= tLCPremSet.size(); j++) {
										if (tLCPremSet
												.get(j)
												.getPayEndDate()
												.compareTo(
														tLPPremSchema
																.getPayStartDate()) <= 0) {
											mNewLPPremSet
													.add(tLCPremSet.get(j));
											continue;
										} else {
											LPPremSchema tempLPPremSchema = new LPPremSchema();
											tReflections.transFields(
													tempLPPremSchema,
													tLCPremSet.get(j)
															.getSchema());
											tempLPPremSchema
													.setEdorNo(tLPPremSchema
															.getEdorNo());
											tempLPPremSchema
													.setEdorType(tLPPremSchema
															.getEdorType());
											if (!tempLPPremSchema
													.getPayStartDate()
													.equals(
															tLPPremSchema
																	.getPayStartDate())) {
												tempLPPremSchema
														.setPayEndDate(tLPPremSchema
																.getPayStartDate());
												mNewLPPremSet
														.add(tempLPPremSchema);
											}
											mNewLPPremSet.add(tLPPremSchema);
											break;
										}
									}
								}
							} else {
								tLPPremSchema = createNewAddFee(tLPPremSchema,
										PayPlanCode, premNum);
								mNewLPPremSet.add(tLPPremSchema);
								chgMoney = tLPPremSchema.getPrem();
							}

							// //更新其他表的保费总额
							// tLPDutySchema.setPrem(tLPDutySchema.getPrem() +
							// tLPPremSchema.getPrem());
							// mLPPolSchema.setPrem(mLPPolSchema.getPrem() +
							// tLPPremSchema.getPrem());
							// mLPContSchema.setPrem(mLPContSchema.getPrem() +
							// tLPPremSchema.getPrem());

							// 如果是追溯加费，则以下计算加费补退费。
							// if (tLPPremSchema.getPayStartDate() != null &&
							// tLPPremSchema.getPayStartDate().equals(mLPPolSchema.getCValiDate()))
							// {
							if (premNum > 0) {
								double dSumBJPrem = 0.0;
								double dSumBJInterest = 0.0;
								for (int k = 0; k < premNum && chgMoney != 0; k++) {
									double dBJPrem = 0.0;
									double dBJInterest = 0.0;
									try {
										if (chgMoney > 0) // 实交加费少于应交加费，则补交加费及利息
										{
											String tRiskcode = "";
											if (mLPPolSchema
													.getPolNo()
													.equals(
															mLPPolSchema
																	.getMainPolNo())) {
												tRiskcode = mLPPolSchema
														.getRiskCode();
											} else {
												SQLwithBindVariables tsbv=new SQLwithBindVariables();
												tsbv.sql("select Riskcode from LCPol where PolNo = '"+ "?PolNo?"+ "'");
												tsbv.put("PolNo", mLPPolSchema.getMainPolNo());
												tRiskcode = tExeSQL
														.getOneValue(tsbv);
											}
											if (mEdorType != null
													&& !mEdorType.equals("NS")
													&& !mEdorType.equals("AA")) // 新增附加险不算利息。
											{
												if (tRiskcode != null
														&& !tAccountManage
																.calInterest(
																		"IC",
																		chgMoney,
																		tD
																				.getString(tPayDate),
																		mLPEdorItemSchema
																				.getEdorValiDate(),
																		tRiskcode,
																		tIntervalType)) // 计算利息,如果失败
												{
													mErrors
															.copyAllErrors(tAccountManage.mErrors);
													CError.buildErr(this,
															"利息计算失败！");
													return false;
												}
												interestMoney = tAccountManage
														.getCalResult(); // 获得计算后的利息结果
											}
											// chgMoney = Double.parseDouble(new
											// DecimalFormat("0.00").format(
											// chgMoney));
											// interestMoney =
											// Double.parseDouble(new
											// DecimalFormat("0.00").
											// format(interestMoney));
											dBJPrem = chgMoney;
											dBJInterest = interestMoney;

											dSumBJPrem += chgMoney;
											dSumBJInterest += interestMoney;

											if (tLPPremSchema.getPayPlanType() != null
													&& (tLPPremSchema
															.getPayPlanType()
															.equals("01") || tLPPremSchema
															.getPayPlanType()
															.equals("03"))) {
												if (dBJPrem > 0) {
													if (tLPPremSchema
															.getAddFeeDirect() != null
															&& tLPPremSchema
																	.getAddFeeDirect()
																	.equals(
																			"00")) {
														tSubOperType = mBqCode.Pay_AppntAddPremHealth
																+ Integer
																		.toString(k + 1);
													} else {
														tSubOperType = mBqCode.Pay_InsurAddPremHealth
																+ Integer
																		.toString(k + 1);
													}
												} else if (dBJPrem < 0) {
													tSubOperType = mBqCode.Get_AddPremHealth
															+ Integer
																	.toString(k + 1);
												}
											} else if (tLPPremSchema
													.getPayPlanType() != null
													&& (tLPPremSchema
															.getPayPlanType()
															.equals("02") || tLPPremSchema
															.getPayPlanType()
															.equals("04"))) {
												if (dBJPrem > 0) {
													if (tLPPremSchema
															.getAddFeeDirect() != null
															&& tLPPremSchema
																	.getAddFeeDirect()
																	.equals(
																			"00")) {
														tSubOperType = mBqCode.Pay_AppntAddPremOccupation
																+ Integer
																		.toString(k + 1);
													} else {
														tSubOperType = mBqCode.Pay_InsurAddPremOccupation
																+ Integer
																		.toString(k + 1);
													}

												} else {
													tSubOperType = mBqCode.Get_AddPremOccupation
															+ Integer
																	.toString(k + 1);
												}
											}

											if (dBJPrem > 0) {
												addLJSGetEndorse(
														mLPEdorItemSchema,
														mLPPolSchema,
														mLPPremSet.get(i)
																.getDutyCode(),
														PayPlanCode, BFSubject,
														tSubOperType, dBJPrem); // 在批改补退费表中增加单期补交保费记录
											} else if (dBJPrem < 0) {
												addLJSGetEndorse(
														mLPEdorItemSchema,
														mLPPolSchema,
														mLPPremSet.get(i)
																.getDutyCode(),
														PayPlanCode, TFSubject,
														tSubOperType, dBJPrem); // 在批改补退费表中增加单期退还保费记录
											}
											if (dBJInterest > 0) {
												addLJSGetEndorse(
														mLPEdorItemSchema,
														mLPPolSchema,
														mLPPremSet.get(i)
																.getDutyCode(),
														PayPlanCode, LXSubject,
														tSubOperType,
														dBJInterest); // 在批改补退费表中增加单期补交利息记录
											}
										}
									} catch (Exception ex) {
										CError.buildErr(this, "加费补退费计算异常！");
									}

									if (mLPPolSchema.getPayIntv() == 1) {
										tPayDate = PubFun.calDate(tPayDate,
												mLPPolSchema.getPayIntv(), "D",
												null);
									} else if (mLPPolSchema.getPayIntv() == 12) {
										tPayDate = PubFun.calDate(tPayDate,
												mLPPolSchema.getPayIntv(), "M",
												null);
									}
								}
								dSumBJPrem = Double
										.parseDouble(new DecimalFormat("0.00")
												.format(dSumBJPrem));
								dSumBJInterest = Double
										.parseDouble(new DecimalFormat("0.00")
												.format(dSumBJInterest));

							}
						}

					}
					// 更新责任项表中保费标准
					dOldSumAddPrem_Duty = getDutyAddPrem(tLPDutySchema);
					if (dOldSumAddPrem_Duty == -1) {
						return false;
					}
					dOldSumAddPrem_Pol += dOldSumAddPrem_Duty;
					tLPDutySchema.setPrem(tLPDutySchema.getPrem()
							- dOldSumAddPrem_Duty + dNewSumAddPrem_Duty);
				}
				// 更新保单和险种表中保费标准
				mLPPolSchema.setPrem(mLPPolSchema.getPrem()
						- dOldSumAddPrem_Pol + dNewSumAddPrem_Pol);
				mLPContSchema.setPrem(mLPContSchema.getPrem()
						- dOldSumAddPrem_Pol + dNewSumAddPrem_Pol);
			}

			if (mAddReason != null && !mAddReason.trim().equals("")) {
				mLPUWMasterSchema.setAddPremReason(mAddReason);
			} else {
				mLPUWMasterSchema.setAddPremReason("");
			}
			if (mLPPremSet != null && mLPPremSet.size() > 0) {
				mLPUWMasterSchema.setAddPremFlag("1"); // 有加费标识
			} else {
				mLPUWMasterSchema.setAddPremFlag("0"); // 无加费标识
			}
			mLPUWMasterSchema.setOperator(mOperater);
			mLPUWMasterSchema.setManageCom(mManageCom);
			mLPUWMasterSchema.setModifyDate(tCurrentDate);
			mLPUWMasterSchema.setModifyTime(tCurrentTime);
		} else {
			// LPPremDB tLPPremDB = new LPPremDB();
			// mLPPremSet = tLPPremDB.executeQuery("select * from LPPrem where
			// EdorNo = " + mEdorNo + "' and EdorType = '" + mEdorType + "' and
			// PayEndDate < '" +
			// mLPPolSchema.getPaytoDate() + "'");
			double tPrem_Pol = 0.0;
			for (int i = 1; i <= mLPDutySet.size(); i++) {
				// String sql = "select Sum(Prem) from LPPrem where PayPlanType
				// <> '0' and PayStartDate <= '" + mLPPolSchema.getPaytoDate() +
				// "' and PayEndDate >= '" + mLPPolSchema.getPaytoDate() + "'";
				// ExeSQL tExeSQL = new ExeSQL();
				// String tValue = tExeSQL.getOneValue(sql);
				// if (tValue == null || tValue.equals(""))
				// {
				// continue;
				// }
				double tPrem_Duty = getDutyAddPrem(mLPDutySet.get(i));
				tPrem_Pol += tPrem_Duty;
				mLPDutySet.get(i).setPrem(
						mLPDutySet.get(i).getPrem() - tPrem_Duty);
			}
			mLPContSchema.setPrem(mLPContSchema.getPrem() - tPrem_Pol);
			mLPPolSchema.setPrem(mLPPolSchema.getPrem() - tPrem_Pol);
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// //删除上一个加费数据
		// if (mOldLPPremSet != null && mOldLPPremSet.size() > 0)
		// {
		// map.put(mOldLPPremSet, "DELETE");
		// }
		String sEdorValidate = mLPEdorItemSchema.getEdorValiDate();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(" delete from LPPrem where PayPlanType <> '0' and PolNo = '"+ "?mPolNo?" + "'" + " and EdorNo = '" + "?mEdorNo?"+ "' and EdorType = '" + "?mEdorType?"+ "' and ((paystartdate <= '" + "?sEdorValidate?" + "' and payenddate >= '" + "?sEdorValidate?" + "') or (paystartdate >= '" + "?sEdorValidate?" + "'))");
		sbv.put("mPolNo", mPolNo);
		sbv.put("mEdorNo", mEdorNo);
		sbv.put("mEdorType", mEdorType);
		sbv.put("sEdorValidate", sEdorValidate);
		map.put(sbv, "DELETE"); // 删除原有的加费项
		// 添加本次加费数据
		if (mNewLPPremSet != null && mNewLPPremSet.size() > 0) {
			map.put(mNewLPPremSet, "DELETE&INSERT");
		}

		// 修改本次加费后更新的保单责任数据
		if (mLPDutySet != null && mLPDutySet.size() > 0) {
			map.put(mLPDutySet, "DELETE&INSERT");
		}

		// 修改本次加费后更新的保单数据
		if (mLPPolSchema != null) {
			map.put(mLPPolSchema, "UPDATE");
		}

		// 修改本次加费后更新的合同单数据
		if (mLPContSchema != null) {
			map.put(mLPContSchema, "UPDATE");
		}

		// 更新批单核保主表
		if (mLPUWMasterSchema.getContNo() != null
				&& !mLPUWMasterSchema.getContNo().equals("")) {
			map.put(mLPUWMasterSchema, "UPDATE");
		}

		// 更新本次核保过程中的个人应收加费 lizhuo 2006-3-10
		if ("AA".equals(mEdorType) || "NS".equals(mEdorType)
				|| "WA".equals(mEdorType) || "NI".equals(mEdorType)
				|| "WS".equals(mEdorType)) {
			String delPayPerson = "delete from ljspayperson where paytype = '"
					+ "?mEdorType?"
					+ "'"
					+ " and polno = '"
					+ "?mPolNo?"
					+ "'"
					+ " and payplancode like '000000__'"
					+ " and payplancode in (select payplancode from ljsgetendorse where endorsementno = '"
					+ "?mEdorNo?"
					+ "' and polno = '"
					+ "?mPolNo?"
					+ "' and trim(payplancode) like '000000__' and otherno = payplancode)";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(delPayPerson);
			sqlbv.put("mEdorType", mEdorType);
			sqlbv.put("mPolNo", mPolNo);
			sqlbv.put("mEdorNo", mEdorNo);
			logger.debug("delete ljspayperson:" + delPayPerson);
			map.put(sqlbv, "DELETE");
		}

		// 更新批改补退费中补交加费
		String delLJS = "delete from LJSGetEndorse where 1=1 "
				+ " and EndorsementNo = '"
				+ "?mEdorNo?"
				+ "'"
				+ " and feeoperationtype = '"
				+ "?mEdorType?"
				+ "'"
				+ " and polno = '"
				+ "?mPolNo?"
				+ "'"
				+ " and (othernotype = '6' or (trim(payplancode) like '000000__' and subfeeoperationtype like '%UW%')) ";
		// 为了保证对程序更新前后产生的数据兼容，暂时两个条件兼用
        SQLwithBindVariables sbvs=new SQLwithBindVariables();
        sbvs.sql(delLJS);
        sbvs.put("mEdorType", mEdorType);
        sbvs.put("mPolNo", mPolNo);
        sbvs.put("mEdorNo", mEdorNo);
		map.put(sbvs, "DELETE"); // 尚待修改 zhangtao 2005-09-13
		logger.debug(delLJS);
		map.put(mLJSGetEndorseSet, "DELETE&INSERT");
		// 更新保全项目补退费合计
		String sWhere = "EndorsementNo = '" + "?EndorsementNo?"
				+ "' and EdorType = '" + "?EdorType?" + "' ";
		String sWhere2 = "EdorNo = '" + "?EdorNo?"
				+ "' and EdorType = '" + "?EdorType?" + "' ";
		// 考虑险种级保全项目（会有重复）//尚待修改 zhangtao 2005-09-13
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(" update LPEdorItem set "
						+ " GetMoney = (select sum(GetMoney) from LJSGetendorse where "
						+ sWhere
						+ " ), "
						+ " GetInterest = (select sum(GetMoney) from LJSGetendorse where "
						+ sWhere + " and FeeFinaType = 'LX')"
						+ " where " + sWhere2);
		sbv1.put("EndorsementNo", mLPContSchema.getEdorNo());
		sbv1.put("EdorType", mLPContSchema.getEdorType());
		map
				.put(sbv1, "UPDATE");
		// 更新保全批单补退费合计
		
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(" update LPEdorMain set "
						+ " GetMoney = (select sum(GetMoney) from LJSGetendorse where EndorsementNo = '"
						+ "?edorno?"
						+ "'  ), "
						+ " GetInterest = (select sum(GetMoney) from LJSGetendorse where EndorsementNo = '"
						+ "?edorno?"
						+ "'and FeeFinaType = 'LX')"
						+ " where edorno = '"
						+ "?edorno?" + "'");
		sbv2.put("edorno", mLPContSchema.getEdorNo());
		map
				.put(sbv2, "UPDATE");

		// 更新保全申请主表
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(" update LPEdorApp set "
				+ " GetMoney = (select sum(GetMoney) from LJSGetendorse where EndorsementNo in (select edorno from lpedoritem where edoracceptno = '"
				+ "?edoracceptno?"
				+ "' ) ), "
				+ " GetInterest = (select sum(GetMoney) from LJSGetendorse where EndorsementNo in (select edorno from lpedoritem where edoracceptno = '"
				+ "?edoracceptno?"
				+ "' ) and FeeFinaType = 'LX')"
				+ " where Edoracceptno = '?edoracceptno?'");
		sbv3.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		map
				.put(sbv3,"UPDATE");
		// 对于AA/NS增加LJSPayPerson的处理 add by lizhuo at 2005-10-11
		if ((mEdorType.equals("AA") || mEdorType.equals("NS")
				|| mEdorType.equals("WA") || mEdorType.equals("NI") || mEdorType
				.equals("WS"))
				&& mLJSPayPersonSet != null && mLJSPayPersonSet.size() > 0) {
			map.put(mLJSPayPersonSet, "DELETE&INSERT");
		}
		mResult.add(map);
		return true;
	}

	private LPPremSchema createNewAddFee(LPPremSchema aLPPremSchema,
			String aPayPlanCode, int iPayTimes) {
		aLPPremSchema.setGrpContNo(mGrpContNo);
		aLPPremSchema.setContNo(mContNo);
		aLPPremSchema.setEdorNo(mEdorNo);
		aLPPremSchema.setEdorType(mEdorType);
		aLPPremSchema.setPayPlanCode(aPayPlanCode);
		aLPPremSchema.setPayIntv(mLPPolSchema.getPayIntv());
		aLPPremSchema.setStandPrem(aLPPremSchema.getPrem());
		aLPPremSchema.setSumPrem("0");
		aLPPremSchema.setPaytoDate(mLPPolSchema.getPaytoDate());
		aLPPremSchema.setState("1"); // 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
										// 3：前几次不通批单下的加费：
		aLPPremSchema.setUrgePayFlag("Y"); // 加费相一定要催交，而不是去取该险种所描述的催交标志。
		aLPPremSchema.setManageCom(mLPPolSchema.getManageCom());
		aLPPremSchema.setAppntNo(mLPPolSchema.getAppntNo());
		aLPPremSchema.setAppntType("1"); // 个人投保
		aLPPremSchema.setOperator(mOperater);
		aLPPremSchema.setMakeDate(PubFun.getCurrentDate());
		aLPPremSchema.setMakeTime(PubFun.getCurrentTime());
		aLPPremSchema.setModifyDate(PubFun.getCurrentDate());
		aLPPremSchema.setModifyTime(PubFun.getCurrentTime());
		aLPPremSchema.setPayTimes(iPayTimes);
		return aLPPremSchema;
	}

	/**
	 * 往批改补退费表（应收/应付）新增数据
	 * 
	 * @return
	 */
	private boolean addLJSGetEndorse(LPEdorItemSchema aLPEdorItemSchema,
			LPPolSchema aLPPolSchema, String aDutyCode, String aPayPlanCode,
			String feeType, String subType, double feeMoney) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = mBqCalBL.initLJSGetEndorse(
				aLPEdorItemSchema, aLPPolSchema, aDutyCode, aPayPlanCode,
				subType, feeType, feeMoney, mGlobalInput);

		if (tLJSGetEndorseSchema != null) {
			tLJSGetEndorseSchema.setOtherNo(aPayPlanCode); // 为了保证主键不冲突
															// 区分由于年龄等因素导致的加费追缴和加费评点调整导致的加费追缴
			tLJSGetEndorseSchema.setOtherNoType("6"); // 暂定类型 6-交费计划编码
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-14
			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}

		// AA/NS 往LJSPayPerson表中添加记录
		if ((StrTool.compareString(mLPEdorItemSchema.getEdorType(), "NS")
				|| StrTool.compareString(mLPEdorItemSchema.getEdorType(), "AA")
				|| StrTool.compareString(mLPEdorItemSchema.getEdorType(), "WA")
				|| // 团体整单加保
				StrTool.compareString(mLPEdorItemSchema.getEdorType(), "NI") || // 团体新增被保人
		StrTool.compareString(mLPEdorItemSchema.getEdorType(), "WS")) // 团体整单新增附加险
				&& feeMoney > 0 && feeType.equals("BF")) {
			dealLJSPayPerson(tLJSGetEndorseSchema, mLPPolSchema);
		}

		return true;
	}

	/**
	 * 已有加费
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	private double getDutyAddPrem(LPDutySchema pLPDutySchema) {
		String sEdorValidate = mLPEdorItemSchema.getEdorValiDate();
		double dNextPrem = 0.0;
		String sNextPrem = "";
		String sql = " select sum(prem) from lpprem " + " where polno = '"
				+ "?polno?" + "' and dutycode = '"
				+ "?dutycode?"
				+ "' and trim(payplantype) <> '0' "
				+ " and ((paystartdate <= '" + "?sEdorValidate?"
				+ "' and payenddate >= '" + "?sEdorValidate?"
				+ "') or (paystartdate >= '" + "?sEdorValidate?" + "'))";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPDutySchema.getPolNo());
		sqlbv.put("dutycode", pLPDutySchema.getDutyCode());
		sqlbv.put("sEdorValidate", sEdorValidate);
		ExeSQL tExeSQL = new ExeSQL();
		sNextPrem = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "现有加费查询失败!");
			return -1;
		}
		if (sNextPrem == null || sNextPrem.equals("")) {
			return 0;
		}
		try {
			dNextPrem = Double.parseDouble(sNextPrem);
		} catch (Exception e) {
			CError.buildErr(this, "现有加费查询结果错误!" + "错误结果：" + sNextPrem);
			return -1;
		}

		return dNextPrem;
	}

	private boolean dealLJSPayPerson(LJSGetEndorseSchema aLJSGetEndorseSchema,
			LPPolSchema aLPPolSchema) {
		LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLJSPayPersonSchema, aLJSGetEndorseSchema);
		tLJSPayPersonSchema.setSumActuPayMoney(aLJSGetEndorseSchema
				.getGetMoney());
		tLJSPayPersonSchema.setSumDuePayMoney(aLJSGetEndorseSchema
				.getGetMoney());
		tLJSPayPersonSchema.setGetNoticeNo(aLJSGetEndorseSchema
				.getEndorsementNo());
		tLJSPayPersonSchema.setPayCount(1);
		tLJSPayPersonSchema.setPayAimClass("1");
		tLJSPayPersonSchema.setPayType(aLJSGetEndorseSchema
				.getFeeOperationType());
		tLJSPayPersonSchema.setPayDate(aLJSGetEndorseSchema.getGetDate());
		tLJSPayPersonSchema.setLastPayToDate(tLJSPayPersonSchema.getPayDate());
		tLJSPayPersonSchema.setCurPayToDate(aLPPolSchema.getPaytoDate());
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchema(tLJSPayPersonSchema);
        //end zhangyingfeng 2016-07-14
		mLJSPayPersonSet.add(tLJSPayPersonSchema);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
