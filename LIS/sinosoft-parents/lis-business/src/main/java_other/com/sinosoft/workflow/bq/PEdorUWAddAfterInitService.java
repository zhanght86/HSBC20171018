package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMLoanDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

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
 * @author unascribed
 * @version 1.0
 */

public class PEdorUWAddAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PEdorUWAddAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mEdorNo;
	private String mEdorType;
	private String mPolNo;
	private String mPolNo2;
	private String mInsuredNo;
	private String mAddReason;
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流加费活动表任务0000000002 */
	/** 保单表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();

	/** 保全核保主表 */
	private LPUWMasterMainSchema mLPUWMasterMainSchema = new LPUWMasterMainSchema();
	/** 责任项表 */
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPDutySet mNewLPDutySet = new LPDutySet();
	/** 保费表 */
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPPremSet mOldLPPremSet = new LPPremSet();
	private LPPremSet mNewLPPremSet = new LPPremSet();
	/** 保全批改补退费表 */
	private LJSGetEndorseSet mOldLJSGetEndorseSet = new LJSGetEndorseSet();
	private LJSGetEndorseSet mNewLJSGetEndorseSet = new LJSGetEndorseSet();
	/** 保全批改表 */
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();

	public PEdorUWAddAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start SysUWNoticeBL Submit...");

		// mResult.clear();
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保特约信息
		if (prepareAdd() == false)
			return false;

		return true;

	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo2);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema.setSchema(tLCPolDB);

		// 校验保单信息
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setPolNo(mPolNo2);
		tLPPolDB.setEdorNo(mEdorNo);
		tLPPolDB.setEdorType(mEdorType);
		if (!tLPPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo2 + "的P表信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPPolSchema.setSchema(tLPPolDB);

		// 校验保全批单核保主表
		// 校验保单信息
		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		tLPUWMasterMainDB.setEdorNo(mEdorNo);
		// tLPUWMasterMainDB.setPolNo(mPolNo) ;
		if (!tLPUWMasterMainDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "保全批单核保主表信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPUWMasterMainSchema.setSchema(tLPUWMasterMainDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PEdorUW);// 核保通知书
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL);// 保单号
		tLOPRTManagerDB.setStandbyFlag2(mEdorNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的核保通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLPPremSet != null && mLPPremSet.size() > 0) {
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorNo(mEdorNo);
			// tLPEdorMainDB.setPolNo(mPolNo) ;
			// tLPEdorMainDB.setEdorType(mLPPremSet.get(1).getEdorType());
			LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
			mLPEdorMainSet = tLPEdorMainDB.query();
			if (mLPEdorMainSet == null || mLPEdorMainSet.size() != 1) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorUWAddAfterInitService";
				tError.functionName = "preparePrint";
				tError.errorMessage = "查询保全批改主表信息出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPEdorMainSchema = mLPEdorMainSet.get(1);
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中mEdorType失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPolNo = (String) mTransferData.getValueByName("PolNo");
		if (mPolNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PolNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPolNo2 = (String) mTransferData.getValueByName("PolNo2");
		if (mPolNo2 == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PolNo2失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mAddReason = (String) mTransferData.getValueByName("AddReason");
		// if ( mAddReason == null )
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PEdorUWAddAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中AddReason失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }//注销考虑到:加费后又撤消时,通常核保师不会再输入加费原因.

		// 获得业务体检通知数据
		mLPPremSet = (LPPremSet) mTransferData.getValueByName("LPPremSet");
		// if ( mLPPremSet == null )
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PEdorUWAddAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输获得业务加费数据失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAdd() {

		double tTotalMoney = 0;
		double tSumStandPrem = 0;
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		// 取险种名称
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(mLCPolSchema.getRiskCode());
		// tLMRiskDB.setRiskVer(mLCPolSchema.getRiskVersion());
		if (!tLMRiskDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取险种名称失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取代理人姓名
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCPolSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取代理人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 形成加费信息
		if (mLPPremSet.size() > 0) {
			// 取责任信息
			LPDutyDB tLPDutyDB = new LPDutyDB();
			tLPDutyDB.setPolNo(mLPPolSchema.getPolNo());
			tLPDutyDB.setEdorNo(mEdorNo);
			tLPDutyDB.setEdorType(mEdorType);
			mLPDutySet = tLPDutyDB.query();

			// 计算除去本次保全加费项目,承保时的基本保费项后，该保单在该保全项目下的加费项目数。以便计算本次保全加费的编码起始编码值.
			String tsql = "select count(*) from LPPrem where  polno = '"
					+ "?polno?" + "'  and edortype = '"
					+ "?mEdorType?" + "'  and edorno = '" + "?mEdorNo?"
					+ "' and state in ( '1','3')";
			String tReSult = new String();
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tsql);
			sqlbv1.put("polno",mLCPolSchema.getPolNo().trim());
			sqlbv1.put("mEdorType",mEdorType);
			sqlbv1.put("mEdorNo",mEdorNo);
			tReSult = tExeSQL.getOneValue(sqlbv1);
			if (tExeSQL.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorUWAddAfterInitService";
				tError.functionName = "prepareAdd";
				tError.errorMessage = "执行SQL语句：" + tsql + "失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tReSult == null || tReSult.equals("")) {
				return false;
			}

			int tCount = 0;
			tCount = Integer.parseInt(tReSult);// 已包括了本次节点及相关同步节点

			// 更新责任项
			if (mLPDutySet.size() > 0) {
				for (int m = 1; m <= mLPDutySet.size(); m++) {
					int maxno = 0;
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tLPDutySchema = mLPDutySet.get(m);

					// 减去该责任的原本次保全加费金额
					String sql = "select * from LPPrem where payplancode  like '000000%' and polno = '"
							+ "?polno?"
							+ "' and dutycode = '"
							+ "?dutycode?"
							+ "' and edortype = '"
							+ "?edortype?"
							+ "' and  state = '2'";
					LPPremDB tLPPremDB = new LPPremDB();
					LPPremSet tLPPremSet = new LPPremSet();

					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(sql);
					sqlbv2.put("polno",mLCPolSchema.getPolNo().trim());
					sqlbv2.put("dutycode",tLPDutySchema.getDutyCode().trim());
					sqlbv2.put("edortype",tLPDutySchema.getEdorType());
					tLPPremSet = tLPPremDB.executeQuery(sqlbv2);

					if (tLPPremSet.size() > 0) {
						for (int j = 1; j <= tLPPremSet.size(); j++) {
							LPPremSchema tLPPremSchema = new LPPremSchema();
							tLPPremSchema = tLPPremSet.get(j);

							tLPDutySchema.setPrem(tLPDutySchema.getPrem()
									- tLPPremSchema.getPrem());
							mLPPolSchema.setPrem(mLPPolSchema.getPrem()
									- tLPPremSchema.getPrem());
						}
					}

					// 为投保单表和责任表加上本次的加费.同时形成加费信息
					for (int i = 1; i <= mLPPremSet.size(); i++) {
						double tPrem;

						if (mLPPremSet.get(i).getDutyCode().equals(
								tLPDutySchema.getDutyCode())) {
							maxno = maxno + 1;
							// 形成加费编码
							String PayPlanCode = "";
							PayPlanCode = String.valueOf(maxno + tCount);
							for (int j = PayPlanCode.length(); j < 8; j++) {
								PayPlanCode = "0" + PayPlanCode;
							}

							// 保单总保费
							tPrem = mLPPolSchema.getPrem()
									+ mLPPremSet.get(i).getPrem();
							tSumStandPrem = tSumStandPrem
									+ mLPPremSet.get(i).getPrem();
							// mLPPremSet.get(i).setPolNo(mLCPolSchema.getPolNo());//以下注销处表明其信息是前台传入信息
							// mLPPremSet.get(i).setDutyCode();
							mLPPremSet.get(i).setPayPlanCode(PayPlanCode);
							// mLPPremSet.get(i).setGrpPolNo("00000000000000000000");
							// mLPPremSet.get(i).setPayPlanType();
							// mLPPremSet.get(i).setPayTimes();
							mLPPremSet.get(i).setPayIntv(
									tLPDutySchema.getPayIntv());
							// mLPPremSet.get(i).setMult(tLPDutySchema.getMult());
							mLPPremSet.get(i).setStandPrem(
									mLPPremSet.get(i).getPrem());
							// mLPPremSet.get(i).setPrem();
							mLPPremSet.get(i).setSumPrem("0");
							// mLPPremSet.get(i).setRate();
							// mLPPremSet.get(i).setPayStartDate();
							// mLPPremSet.get(i).setPayEndDate();
							mLPPremSet.get(i).setPaytoDate(
									mLPPremSet.get(i).getPayStartDate());
							mLPPremSet.get(i).setState("2");// 0:承保时的保费项。1:承保时的加费项；2：本次保全项目保全加费项
							// 3：前几次不通批单下的保全加费：
							mLPPremSet.get(i).setUrgePayFlag("Y");// 加费相一定要催交，而不是去取该险种所描述的催交标志。
							mLPPremSet.get(i).setManageCom(
									mLCPolSchema.getManageCom());
							mLPPremSet.get(i).setAppntNo(
									mLCPolSchema.getAppntNo());
							mLPPremSet.get(i).setAppntType("1");// 个人投保
							mLPPremSet.get(i).setModifyDate(
									PubFun.getCurrentDate());
							mLPPremSet.get(i).setModifyTime(
									PubFun.getCurrentTime());
							mLPPremSet.get(i).setSuppRiskScore(
									mLPPremSet.get(i).getSuppRiskScore());

							// 更新保险责任
							tLPDutySchema.setPrem(tLPDutySchema.getPrem()
									+ mLPPremSet.get(i).getPrem());
							// 更新保单数据
							mLPPolSchema.setPrem(tPrem);
						}
					}
					mNewLPDutySet.add(tLPDutySchema);

				}
			}

		}

		// 准备删除上一次该项目的加费的数据
		String tSQL = "select * from lpprem where polno = '" + "?mPolNo2?" + "'"
				+ " and EdorNo = '" + "?mEdorNo?" + "'" + " and EdorType = '"
				+ "?mEdorType?" + "'" + " and substr(payplancode,1,6) = '000000'"
				+ " and state = '2'";// 0:承保时的保费项。1:承保时的加费项；2：本次保全项目保全加费项
		// 3：前几次不通批单下的保全加费：
		LPPremDB tLPPremDB = new LPPremDB();
		
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL);
		sqlbv3.put("mPolNo2",mPolNo2);
		sqlbv3.put("mEdorNo",mEdorNo);
		sqlbv3.put("mEdorType",mEdorType);
		mOldLPPremSet = tLPPremDB.executeQuery(sqlbv3);
		if (mOldLPPremSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "查询保全加费信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备删除上一次该NS项目的保全批改补退费表中保全人工核保加费的数据
		tSQL = "select * from ljsgetendorse where getnoticeno = '" + "?mEdorNo?"
				+ "'" + " and endorsementno = '" + "?mEdorNo?" + "'"
				+ " and feeoperationtype = '" + "?feeoperationtype?"
				+ "'" + " and substr(payplancode,1,6) = '000000'"
				+ " and payplancode <> '00000000'" + " and polno = '" + "?mPolNo2?"
				+ "'";
		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL);
		sqlbv4.put("mEdorNo",mEdorNo);
		sqlbv4.put("feeoperationtype",mLPPremSet.get(1).getEdorType());
		sqlbv4.put("mPolNo2",mPolNo2);
		mOldLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(sqlbv4);
		if (mOldLJSGetEndorseSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "查询保全加费信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备添加该项目的保全批改补退费表中保全人工核保加费的数据
		if (tSumStandPrem > 0 && mLPPremSet != null && mLPPremSet.size() > 0) {

			for (int i = 1; i <= mLPPremSet.size(); i++) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutyDB.setDutyCode(mLPPremSet.get(i).getDutyCode());
				tLCDutyDB.setPolNo(mLPPremSet.get(i).getPolNo());
				LCDutySet tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorUWAddAfterInitService";
					tError.functionName = "prepareAdd";
					tError.errorMessage = "查询责任表信息失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCDutySchema = tLCDutySet.get(1);
				String tDutyPaytoDate = tLCDutySchema.getPaytoDate();
				String tPremPaytoDate = mLPPremSet.get(i).getPaytoDate();

				// 保全核保针对一责任加费的交至日期小于该责任的交费日期时要计算当前要加费的金额及其利息并往保全批改补退费表中提交一笔交费项
				if (!mEdorType.equals("NS") && tDutyPaytoDate != null
						&& tPremPaytoDate != null
						&& tDutyPaytoDate.compareTo(tPremPaytoDate) > 0) {

					// 生成利息交费项
					// 按照最新交费间隔计算交费期数
					int premNum = 0;
					if (mLCPolSchema.getPayIntv() == 0) {
						premNum = 1;
					} else {
						premNum = PubFun.calInterval(mLCPolSchema
								.getCValiDate(), mLCPolSchema.getPaytoDate(),
								"M")
								/ mLCPolSchema.getPayIntv();
					}

					double intervalMoney = mLPPremSet.get(i).getPrem(); // 保费差额
					double bfMoney = 0; // 补费本金
					double interestMoney = 0; // 利息

					// 获取利率描述
					LMLoanDB tLMLoanDB = new LMLoanDB();
					tLMLoanDB.setRiskCode(mLCPolSchema.getRiskCode());
					if (!tLMLoanDB.getInfo()) {
						// ?
					}
					AccountManage tAccountManage = new AccountManage();

					// 计算利息
					double interest = 0;
					for (int j = 0; j < premNum; j++) {
						if (j == 0) {
							interest = tAccountManage.getInterest(
									intervalMoney, mLCPolSchema.getCValiDate(),
									PubFun.getCurrentDate(), tLMLoanDB
											.getInterestRate(), tLMLoanDB
											.getInterestMode(), tLMLoanDB
											.getInterestType(), "D");
						} else {
							interest = tAccountManage.getInterest(
									intervalMoney, PubFun.calDate(mLCPolSchema
											.getCValiDate(), j
											* mLCPolSchema.getPayIntv(), "M",
											mLCPolSchema.getCValiDate()),
									PubFun.getCurrentDate(), tLMLoanDB
											.getInterestRate(), tLMLoanDB
											.getInterestMode(), tLMLoanDB
											.getInterestType(), "D");
						}
						bfMoney = bfMoney + intervalMoney; // 补费本金
						if (interest > 0)
							interestMoney = interestMoney + interest; // 利息
						bfMoney = Double.parseDouble(new DecimalFormat("0.00")
								.format(bfMoney));
						interestMoney = Double.parseDouble(new DecimalFormat(
								"0.00").format(interestMoney));
					}

					tTotalMoney = bfMoney + interestMoney;
					// 生成批改补退费表中的补费记录
					LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tLPPremSchema = mLPPremSet.get(i);
					tLJSGetEndorseSchema.setGetNoticeNo(mEdorNo); // 给付通知书号码
					tLJSGetEndorseSchema.setEndorsementNo(mEdorNo);
					tLJSGetEndorseSchema.setFeeOperationType(tLPPremSchema
							.getEdorType());

					mReflections
							.transFields(tLJSGetEndorseSchema, mLCPolSchema);

					tLJSGetEndorseSchema.setGetDate(mLPEdorMainSchema
							.getEdorValiDate());
					tLJSGetEndorseSchema.setGetMoney(bfMoney);// 本金额
					tLJSGetEndorseSchema.setFeeFinaType("BF"); // 加费
					tLJSGetEndorseSchema.setPayPlanCode(tLPPremSchema
							.getPayPlanCode()); // 无作用
					tLJSGetEndorseSchema.setDutyCode(tLPPremSchema
							.getDutyCode()); // 无作用，但一定要，转ljagetendorse时非空
					tLJSGetEndorseSchema.setOtherNo(mEdorNo); // 无作用
					tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
					tLJSGetEndorseSchema.setGetFlag("0");

					tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
					tLJSGetEndorseSchema.setMakeDate(tCurrentDate);
					tLJSGetEndorseSchema.setMakeTime(tCurrentTime);
					tLJSGetEndorseSchema.setModifyDate(tCurrentDate);
					tLJSGetEndorseSchema.setModifyTime(tCurrentTime);

					// 生成保全批改补退费表利息记录
					LJSGetEndorseSchema tLJSGetEndorseSchemaLX = new LJSGetEndorseSchema();
					tLJSGetEndorseSchemaLX.setGetNoticeNo(mEdorNo); // 给付通知书号码
					tLJSGetEndorseSchemaLX.setEndorsementNo(mEdorNo);
					tLJSGetEndorseSchemaLX.setFeeOperationType(tLPPremSchema
							.getEdorType());

					mReflections.transFields(tLJSGetEndorseSchemaLX,
							mLCPolSchema);

					tLJSGetEndorseSchemaLX.setGetDate(mLPEdorMainSchema
							.getEdorValiDate());
					tLJSGetEndorseSchemaLX.setGetMoney(interestMoney);
					tLJSGetEndorseSchemaLX.setFeeFinaType("LX"); // 利息
					tLJSGetEndorseSchemaLX.setPayPlanCode(tLPPremSchema
							.getPayPlanCode()); // 无作用
					tLJSGetEndorseSchemaLX.setDutyCode(tLPPremSchema
							.getDutyCode()); // 无作用，但一定要，转ljagetendorse时非空
					tLJSGetEndorseSchemaLX.setOtherNo(mEdorNo); // 无作用
					tLJSGetEndorseSchemaLX.setOtherNoType("3"); // 保全给付
					tLJSGetEndorseSchemaLX.setGetFlag("0");

					tLJSGetEndorseSchemaLX.setOperator(mLCPolSchema
							.getManageCom());
					tLJSGetEndorseSchemaLX.setMakeDate(tCurrentDate);
					tLJSGetEndorseSchemaLX.setMakeTime(tCurrentTime);
					tLJSGetEndorseSchemaLX.setModifyDate(tCurrentDate);
					tLJSGetEndorseSchemaLX.setModifyTime(tCurrentTime);

					mNewLJSGetEndorseSet.add(tLJSGetEndorseSchema);
					mNewLJSGetEndorseSet.add(tLJSGetEndorseSchemaLX);
				}

				String tPayStartDate = mLPPremSet.get(i).getPayStartDate();
				// 保全NS项目核保针对一责任加费的交至日期小于该责任的交费日期时要计算当前要加费的金额及其利息并往保全批改补退费表中提交一笔交费项
				if (mEdorType.equals("NS") && tPayStartDate != null) {
					double intervalMoney = mLPPremSet.get(i).getPrem(); // 保费差额
					double bfMoney = 0; // 补费本金
					double interestMoney = 0; // 利息

					// if(tCurrentDate.compareTo(tPayStartDate)>0)
					// {
					// 生成利息交费项
					// 获取利率描述
					// LMLoanDB tLMLoanDB = new LMLoanDB();
					// tLMLoanDB.setRiskCode(mLCPolSchema.getRiskCode());
					// if (!tLMLoanDB.getInfo())
					// {
					// //?
					// }
					// AccountManage tAccountManage = new AccountManage();

					// 计算利息
					// double interest = 0;
					// interest =
					// tAccountManage.getInterest(intervalMoney,tPayStartDate,PubFun.getCurrentDate(),
					// tLMLoanDB.getInterestRate(), tLMLoanDB.getInterestMode(),
					// tLMLoanDB.getInterestType(), "D");
					// bfMoney = bfMoney + intervalMoney; //补费本金
					// if (interest > 0) interestMoney = interestMoney + interest; //利息
					// bfMoney = Double.parseDouble(new
					// DecimalFormat("0.00").format(bfMoney));
					// interestMoney = Double.parseDouble(new
					// DecimalFormat("0.00").format(interestMoney));
					// }

					tTotalMoney = bfMoney + intervalMoney;
					// 生成批改补退费表中的补费记录
					LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tLPPremSchema = mLPPremSet.get(i);
					tLJSGetEndorseSchema.setGetNoticeNo(mEdorNo); // 给付通知书号码
					tLJSGetEndorseSchema.setEndorsementNo(mEdorNo);
					tLJSGetEndorseSchema.setFeeOperationType(tLPPremSchema
							.getEdorType());

					mReflections
							.transFields(tLJSGetEndorseSchema, mLCPolSchema);

					tLJSGetEndorseSchema.setGetDate(mLPEdorMainSchema
							.getEdorValiDate());
					tLJSGetEndorseSchema.setGetMoney(intervalMoney);// 本金额
					tLJSGetEndorseSchema.setFeeFinaType("BF"); // 加费
					tLJSGetEndorseSchema.setPayPlanCode(tLPPremSchema
							.getPayPlanCode()); // 无作用
					tLJSGetEndorseSchema.setDutyCode(tLPPremSchema
							.getDutyCode()); // 无作用，但一定要，转ljagetendorse时非空
					tLJSGetEndorseSchema.setOtherNo(mEdorNo); // 无作用
					tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
					tLJSGetEndorseSchema.setGetFlag("0");

					tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
					tLJSGetEndorseSchema.setMakeDate(tCurrentDate);
					tLJSGetEndorseSchema.setMakeTime(tCurrentTime);
					tLJSGetEndorseSchema.setModifyDate(tCurrentDate);
					tLJSGetEndorseSchema.setModifyTime(tCurrentTime);

					// 生成保全批改补退费表利息记录
					LJSGetEndorseSchema tLJSGetEndorseSchemaLX = new LJSGetEndorseSchema();
					tLJSGetEndorseSchemaLX.setGetNoticeNo(mEdorNo); // 给付通知书号码
					tLJSGetEndorseSchemaLX.setEndorsementNo(mEdorNo);
					tLJSGetEndorseSchemaLX.setFeeOperationType(tLPPremSchema
							.getEdorType());

					mReflections.transFields(tLJSGetEndorseSchemaLX,
							mLCPolSchema);

					tLJSGetEndorseSchemaLX.setGetDate(mLPEdorMainSchema
							.getEdorValiDate());
					tLJSGetEndorseSchemaLX.setGetMoney(interestMoney);
					tLJSGetEndorseSchemaLX.setFeeFinaType("LX"); // 利息
					tLJSGetEndorseSchemaLX.setPayPlanCode(tLPPremSchema
							.getPayPlanCode()); // 无作用
					tLJSGetEndorseSchemaLX.setDutyCode(tLPPremSchema
							.getDutyCode()); // 无作用，但一定要，转ljagetendorse时非空
					tLJSGetEndorseSchemaLX.setOtherNo(mEdorNo); // 无作用
					tLJSGetEndorseSchemaLX.setOtherNoType("3"); // 保全给付
					tLJSGetEndorseSchemaLX.setGetFlag("0");

					tLJSGetEndorseSchemaLX.setOperator(mLCPolSchema
							.getManageCom());
					tLJSGetEndorseSchemaLX.setMakeDate(tCurrentDate);
					tLJSGetEndorseSchemaLX.setMakeTime(tCurrentTime);
					tLJSGetEndorseSchemaLX.setModifyDate(tCurrentDate);
					tLJSGetEndorseSchemaLX.setModifyTime(tCurrentTime);

					mNewLJSGetEndorseSet.add(tLJSGetEndorseSchema);
					mNewLJSGetEndorseSet.add(tLJSGetEndorseSchemaLX);
				}
			}
		}

		// 准备保全核保主表信息
		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		// tLPUWMasterMainDB.setPolNo(mPolNo);
		tLPUWMasterMainDB.setEdorNo(mEdorNo);
		if (tLPUWMasterMainDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAddAfterInitService";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "无保全批单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPUWMasterMainSchema.setSchema(tLPUWMasterMainDB);
		if (mAddReason != null && !mAddReason.trim().equals("")) {
			mLPUWMasterMainSchema.setAddPremReason(mAddReason);
		} else {
			mLPUWMasterMainSchema.setAddPremReason("");
		}

		if (mLPPremSet != null && mLPPremSet.size() > 0) {
			mLPUWMasterMainSchema.setChangePolFlag("1");// 有加费标识
		} else {
			mLPUWMasterMainSchema.setChangePolFlag("0");// 无加费标识
		}

		mLPUWMasterMainSchema.setOperator(mOperater);
		mLPUWMasterMainSchema.setManageCom(mManageCom);
		mLPUWMasterMainSchema.setModifyDate(tCurrentDate);
		mLPUWMasterMainSchema.setModifyTime(tCurrentTime);

		// 准备保全批改主表信息(待核保确认时再修改)
		// double tGetMoney = tTotalMoney + mLPEdorMainSchema.getGetMoney();
		// mLPEdorMainSchema.setGetMoney(tGetMoney);
		// mLPEdorMainSchema.setOperator(mOperater) ;
		// mLPEdorMainSchema.setManageCom(mManageCom);
		// mLPEdorMainSchema.setModifyDate(tCurrentDate) ;
		// mLPEdorMainSchema.setModifyTime(tCurrentTime);

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 删除上一个加费数据
		if (mOldLPPremSet != null && mOldLPPremSet.size() > 0) {
			map.put(mOldLPPremSet, "DELETE");
		}

		// 添加本次保全加费数据
		if (mLPPremSet != null && mLPPremSet.size() > 0) {
			map.put(mLPPremSet, "INSERT");
		}

		// 修改本次保全加费后更新的保单责任数据
		if (mNewLPDutySet != null && mNewLPDutySet.size() > 0) {
			map.put(mNewLPDutySet, "UPDATE");
		}

		// 修改本次保全加费后更新的保单数据
		if (mLPPolSchema != null) {
			map.put(mLPPolSchema, "UPDATE");
		}

		// 准备删除上一次该NS项目的保全批改补退费表中保全人工核保加费的数据
		if (mOldLJSGetEndorseSet != null && mOldLJSGetEndorseSet.size() > 0) {
			map.put(mOldLJSGetEndorseSet, "DELETE");
		}

		// 准备添加这次该NS项目的保全批改补退费表中保全人工核保加费的数据
		if (mNewLJSGetEndorseSet != null && mNewLJSGetEndorseSet.size() > 0) {
			map.put(mNewLJSGetEndorseSet, "INSERT");

		}

		// 添加保全批单核保主表
		map.put(mLPUWMasterMainSchema, "UPDATE");

		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
