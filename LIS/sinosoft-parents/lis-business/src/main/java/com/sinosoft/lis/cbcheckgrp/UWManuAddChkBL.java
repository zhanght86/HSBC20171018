package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
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
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @modified by ZhangRong
 * @version 1.0
 */

public class UWManuAddChkBL {
private static Logger logger = Logger.getLogger(UWManuAddChkBL.class);

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
	private String mPolNo = "";
	private String mPolNo2 = "";
	private String mInsuredNo = "";
	private String mAddReason = "";
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流加费活动表任务0000000002 */
	/** 保单表 */
	private LCContSchema mLCContSchema = null;
	private LCPolSchema mLCPolSchema = null;
	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	/** 责任项表 */
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySet mNewLCDutySet = new LCDutySet();
	/** 保费表 */
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet mOldLCPremSet = new LCPremSet();
	private LCPremSet mNewLCPremSet = new LCPremSet();

	/** 保全批改补退费表 */
	// private LJSGetEndorseSet mOldLJSGetEndorseSet = new LJSGetEndorseSet();
	// private LJSGetEndorseSet mNewLJSGetEndorseSet = new LJSGetEndorseSet();

	public UWManuAddChkBL() {
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

		logger.debug("Start UWManuAddChkBL Submit...");
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("UWManuAddChkBL Submit OK!");

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
		// 校验合同单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = tLCContSet.get(1);
		mGrpContNo = mLCContSchema.getGrpContNo();

		// 校验保单信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo2);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		// 校验保全批单核保主表
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mPolNo);
		if (!tLCUWMasterDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "保全批单核保主表信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCUWMasterSchema.setSchema(tLCUWMasterDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PRnewUW);// 核保通知书
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL);// 保单号
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
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
		// mTransferData =
		// (TransferData)cInputData.getObjectByObjectName("TransferData",0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
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
			tError.moduleName = "UWManuAddChkBL";
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
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mPolNo = mLCPolSchema.getPolNo();
		mPolNo2 = mLCPolSchema.getPolNo();
		mContNo = mLCPolSchema.getContNo();
		if (mPolNo == null && !mPolNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PolNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/*
		 * if ( mPolNo2 == null ) { // @@错误处理 //this.mErrors.copyAllErrors(
		 * tLCPolDB.mErrors ); CError tError = new CError(); tError.moduleName =
		 * "UWManuAddChkBL"; tError.functionName = "getInputData"; tError.errorMessage =
		 * "前台传输业务数据中PolNo2失败!"; this.mErrors .addOneError(tError) ; return false; }
		 */// zhr
		// mAddReason = (String)mTransferData.getValueByName("AddReason");
		mAddReason = (String) cInputData.getObjectByObjectName("String", 0);

		mLCPremSet = (LCPremSet) cInputData.getObjectByObjectName("LCPremSet",
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
		tLMRiskDB.setRiskCode(mLCPolSchema.getRiskCode());
		if (!tLMRiskDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
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
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取代理人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 形成加费信息
		if (mLCPremSet.size() > 0) {
			// 取责任信息
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
			mLCDutySet = tLCDutyDB.query();

			// 计算除去本次加费项目,承保时的基本保费项后，该保单在该的加费项目数。以便计算本次加费的编码起始编码值.
			String tsql = "select count(*) from LCPrem where  polno = '"
					+ mLCPolSchema.getPolNo().trim()
					+ "'  and state in ('1','3')";
			String tReSult = new String();
			ExeSQL tExeSQL = new ExeSQL();
			tReSult = tExeSQL.getOneValue(tsql);
			if (tExeSQL.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWManuAddChkBL";
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
			if (mLCDutySet.size() > 0) {
				for (int m = 1; m <= mLCDutySet.size(); m++) {
					int maxno = 0;
					LCDutySchema tLCDutySchema = new LCDutySchema();
					tLCDutySchema = mLCDutySet.get(m);

					// 减去该责任的原本次加费金额
					String sql = "select * from LCPrem where payplancode  like '000000%' and polno = '"
							+ mLCPolSchema.getPolNo().trim()
							+ "' and dutycode = '"
							+ tLCDutySchema.getDutyCode().trim()
							+ "'and state = '1'";
					LCPremDB tLCPremDB = new LCPremDB();
					LCPremSet tLCPremSet = new LCPremSet();

					tLCPremSet = tLCPremDB.executeQuery(sql);

					if (tLCPremSet.size() > 0) {
						for (int j = 1; j <= tLCPremSet.size(); j++) {
							LCPremSchema tLCPremSchema;// = new LCPremSchema();
							tLCPremSchema = tLCPremSet.get(j);

							tLCDutySchema.setPrem(tLCDutySchema.getPrem()
									- tLCPremSchema.getPrem());
							mLCPolSchema.setPrem(mLCPolSchema.getPrem()
									- tLCPremSchema.getPrem());
							mLCContSchema.setPrem(mLCContSchema.getPrem()
									- tLCPremSchema.getPrem());
						}
					}

					// 为投保单表和责任表加上本次的加费.同时形成加费信息
					for (int i = 1; i <= mLCPremSet.size(); i++) {
						double tPrem;

						if (mLCPremSet.get(i).getDutyCode().equals(
								tLCDutySchema.getDutyCode())) {
							maxno = maxno + 1;
							// 形成加费编码
							String PayPlanCode = "";
							PayPlanCode = String.valueOf(maxno + tCount);
							for (int j = PayPlanCode.length(); j < 8; j++) {
								PayPlanCode = "0" + PayPlanCode;
							}

							// 保单总保费
							tPrem = mLCPolSchema.getPrem()
									+ mLCPremSet.get(i).getPrem();
							tSumStandPrem = tSumStandPrem
									+ mLCPremSet.get(i).getPrem();

							mLCPremSet.get(i).setGrpContNo(mGrpContNo);
							mLCPremSet.get(i).setContNo(mContNo);
							mLCPremSet.get(i).setPayPlanCode(PayPlanCode);
							mLCPremSet.get(i).setPayIntv(
									tLCDutySchema.getPayIntv());
							mLCPremSet.get(i).setStandPrem(
									mLCPremSet.get(i).getPrem());
							mLCPremSet.get(i).setSumPrem("0");
							mLCPremSet.get(i).setPaytoDate(
									mLCPremSet.get(i).getPayStartDate());
							mLCPremSet.get(i).setState("1");// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
							// 3：前几次不通批单下的加费：
							mLCPremSet.get(i).setUrgePayFlag("Y");// 加费相一定要催交，而不是去取该险种所描述的催交标志。
							mLCPremSet.get(i).setManageCom(
									mLCPolSchema.getManageCom());
							mLCPremSet.get(i).setAppntNo(
									mLCPolSchema.getAppntNo());
							mLCPremSet.get(i).setAppntType("1");// 个人投保
							mLCPremSet.get(i).setOperator(mOperater);
							mLCPremSet.get(i).setMakeDate(
									PubFun.getCurrentDate());
							mLCPremSet.get(i).setMakeTime(
									PubFun.getCurrentTime());
							mLCPremSet.get(i).setModifyDate(
									PubFun.getCurrentDate());
							mLCPremSet.get(i).setModifyTime(
									PubFun.getCurrentTime());
							mLCPremSet.get(i).setSecInsuAddPoint(
									mLCPremSet.get(i).getSecInsuAddPoint());
							mLCPremSet.get(i).setAddFeeDirect(
									mLCPremSet.get(i).getAddFeeDirect());
							mLCPremSet.get(i).setSuppRiskScore(
									mLCPremSet.get(i).getSuppRiskScore());

							// 更新保险责任
							tLCDutySchema.setPrem(tLCDutySchema.getPrem()
									+ mLCPremSet.get(i).getPrem());
							// 更新保单数据
							mLCPolSchema.setPrem(tPrem);
							// 更新合同单数据
							mLCContSchema.setPrem(mLCContSchema.getPrem()
									+ mLCPremSet.get(i).getPrem());
						}
					}
					mNewLCDutySet.add(tLCDutySchema);

				}
			}

		}

		// 准备删除上一次该项目的加费的数据
		String tSQL = "select * from lcprem where polno = '" + mPolNo2 + "'"
				+ " and substr(payplancode,1,6) = '000000'"
				+ " and state = '1'";// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
		// 3：前几次不通批单下的加费：
		LCPremDB tLCPremDB = new LCPremDB();
		mOldLCPremSet = tLCPremDB.executeQuery(tSQL);
		if (mOldLCPremSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "查询加费信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备添加该项目的批改补退费表中人工核保加费的数据
		if (tSumStandPrem > 0 && mLCPremSet != null && mLCPremSet.size() > 0) {
			for (int i = 1; i <= mLCPremSet.size(); i++) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutyDB.setDutyCode(mLCPremSet.get(i).getDutyCode());
				tLCDutyDB.setPolNo(mLCPremSet.get(i).getPolNo());
				LCDutySet tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "UWManuAddChkBL";
					tError.functionName = "prepareAdd";
					tError.errorMessage = "查询责任表信息失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCDutySchema = tLCDutySet.get(1);
				String tDutyPaytoDate = tLCDutySchema.getPaytoDate();
				String tPremPaytoDate = mLCPremSet.get(i).getPaytoDate();
			}
		}

		// 准备核保主表信息
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mPolNo);
		if (tLCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "无批单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCUWMasterSchema.setSchema(tLCUWMasterDB);
		if (mAddReason != null && !mAddReason.trim().equals("")) {
			mLCUWMasterSchema.setAddPremReason(mAddReason);
		} else {
			mLCUWMasterSchema.setAddPremReason("");
		}

		if (mLCPremSet != null && mLCPremSet.size() > 0) {
			mLCUWMasterSchema.setAddPremFlag("1");// 有加费标识
		} else {
			mLCUWMasterSchema.setAddPremFlag("0");// 无加费标识
		}

		mLCUWMasterSchema.setOperator(mOperater);
		mLCUWMasterSchema.setManageCom(mManageCom);
		mLCUWMasterSchema.setModifyDate(tCurrentDate);
		mLCUWMasterSchema.setModifyTime(tCurrentTime);

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		if (tLCCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "prepareAdd";
			tError.errorMessage = "无合同单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);
		if (mAddReason != null && !mAddReason.trim().equals("")) {
			mLCCUWMasterSchema.setAddPremReason(mAddReason);
		} else {
			mLCCUWMasterSchema.setAddPremReason("");
		}

		if (mLCPremSet != null && mLCPremSet.size() > 0) {
			mLCCUWMasterSchema.setAddPremFlag("1");// 有加费标识
		} else {
			mLCCUWMasterSchema.setAddPremFlag("0");// 无加费标识
		}

		mLCCUWMasterSchema.setOperator(mOperater);
		mLCCUWMasterSchema.setManageCom(mManageCom);
		mLCCUWMasterSchema.setModifyDate(tCurrentDate);
		mLCCUWMasterSchema.setModifyTime(tCurrentTime);

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 删除上一个加费数据
		if (mOldLCPremSet != null && mOldLCPremSet.size() > 0) {
			map.put(mOldLCPremSet, "DELETE");
		}

		// 添加本次加费数据
		if (mLCPremSet != null && mLCPremSet.size() > 0) {
			map.put(mLCPremSet, "INSERT");
		}

		// 修改本次加费后更新的保单责任数据
		if (mNewLCDutySet != null && mNewLCDutySet.size() > 0) {
			map.put(mNewLCDutySet, "UPDATE");
		}

		// 修改本次加费后更新的保单数据
		if (mLCPolSchema != null) {
			map.put(mLCPolSchema, "UPDATE");
		}

		// 修改本次加费后更新的合同单数据
		if (mLCContSchema != null) {
			map.put(mLCContSchema, "UPDATE");
		}

		// 更新批单核保主表
		map.put(mLCUWMasterSchema, "UPDATE");

		// 更新合同单核保主表
		map.put(mLCCUWMasterSchema, "UPDATE");

		mResult.add(map);
		return true;
	}

	/*
	 * public VData getResult() { return mResult; }
	 * 
	 * public TransferData getReturnTransferData() { return mTransferData; }
	 */// zhr
	public CErrors getErrors() {
		return mErrors;
	}
}
