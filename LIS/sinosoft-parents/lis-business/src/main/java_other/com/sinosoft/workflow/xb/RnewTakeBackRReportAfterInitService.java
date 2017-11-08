package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.db.RnewRReportDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.RnewRReportSchema;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.lis.vschema.RnewRReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流节点任务:新契约回收生调通知书
 * </p>
 * <p>
 * Description:人工核保生调通知书回收AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RnewTakeBackRReportAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RnewTakeBackRReportAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mPrtSeq;
	private boolean mPatchFlag;
	private String mReplyContente;
	private String mOldPrtSeq; // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	private String mTakeBackOperator;
	/** 执行保全工作流特约活动表任务0000000013 */
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 保全核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	/** 单证发放表 */
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();

	/** 生存调查表 */
	private RnewRReportSet mRnewRReportSet = new RnewRReportSet();
	private RnewRReportSchema mRnewRReportSchema = new RnewRReportSchema();
	private LWMissionSet mLWMissionSet = new LWMissionSet();

	public RnewTakeBackRReportAfterInitService() {
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

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		if (!this.prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLWMissionSet, "UPDATE");
		// 添加核保通知书打印管理表数据
		if (mLOPRTManagerSet != null && mLOPRTManagerSet.size() > 0) {
			map.put(mLOPRTManagerSet, "UPDATE");
		}
		// 添加续保体检通知书自动发放表数据
		if (mLZSysCertifySet != null && mLZSysCertifySet.size() > 0) {
			map.put(mLZSysCertifySet, "UPDATE");
		}
		map.put(this.mLCUWMasterSet, "UPDATE");
		map.put(mRnewRReportSet, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 校验保全批单核保主表
		// 校验保单信息
		// LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		// tLCUWMasterDB.setContNo(mContNo);
		// if (!tLCUWMasterDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "保全批单核保主表信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mLCUWMasterSchema.setSchema(tLCUWMasterDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode("44"); //
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStateFlag("1");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中没有处于已打印待回收状态的生调通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLOPRTManagerSchema = tLOPRTManagerSet.get(1);
		if (mLOPRTManagerSchema.getPatchFlag() == null) {
			mPatchFlag = false;
		} else if (mLOPRTManagerSchema.getPatchFlag().equals("0")) {
			mPatchFlag = false;
		} else if (mLOPRTManagerSchema.getPatchFlag().equals("1")) {
			mPatchFlag = true;
		}

		// 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
		if (mPatchFlag == true) {
			LOPRTManagerDB tempLOPRTManagerDB = new LOPRTManagerDB();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();
			String tStr = "select * from LOPRTManager where (PrtSeq = '"
					+ "?mOldPrtSeq?" + "'" + "or OldPrtSeq = '" + "?mOldPrtSeq?" + "')";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tStr);
			sqlbv1.put("mOldPrtSeq", mOldPrtSeq);
			LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
					.executeQuery(sqlbv1);
			;
			if (tempLOPRTManagerSet.size() == 1) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
				tError.functionName = "preparePrint";
				tError.errorMessage = "查询在打印队列中没有该补打生调通知书的原通知书记录信息出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			for (int i = 1; i <= tempLOPRTManagerSet.size(); i++) {
				mLOPRTManagerSet.add(tempLOPRTManagerSet.get(i));
			}
		} else {
			LOPRTManagerDB tempLOPRTManagerDB = new LOPRTManagerDB();
			mOldPrtSeq = mLOPRTManagerSchema.getPrtSeq();
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tempLOPRTManagerDB.setOldPrtSeq(mOldPrtSeq);
				LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
						.query();
				if (tempLOPRTManagerSet != null
						&& tempLOPRTManagerSet.size() > 0) {
					for (int i = 1; i <= tempLOPRTManagerSet.size(); i++) {
						mLOPRTManagerSet.add(tempLOPRTManagerSet.get(i));
					}
				}
			}
		}

		// 查询系统单证回收队列表
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			if (mLOPRTManagerSet.get(i).getStateFlag() != null
					&& mLOPRTManagerSet.get(i).getStateFlag().trim()
							.equals("1")) {
				LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
				LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
				if (mLOPRTManagerSet.get(i).getCode().trim().equals("04")) {
					tLZSysCertifyDB.setCertifyCode("7012"); // 保全体检通知书标识
				}
				tLZSysCertifyDB.setCertifyNo(mLOPRTManagerSet.get(i)
						.getPrtSeq());
				tLZSysCertifySet = tLZSysCertifyDB.query();
				if (tLZSysCertifySet == null || tLZSysCertifySet.size() != 1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "UWPrintTakeBackAutoHealthAfterInitService";
					tError.functionName = "preparePrint";
					tError.errorMessage = "回收生调通知书时,LZSysCertifySchema表信息查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLZSysCertifySet.add(tLZSysCertifySet.get(1));
			}
		}
		// tongmeng 2007-12-18 add
		RnewRReportSet tRnewRReportSet = new RnewRReportSet();
		RnewRReportDB tRnewRReportDB = new RnewRReportDB();
		String tSQL_R = " select * from RnewRReport where contno='"
				+ "?contno?" + "' " + " and prtseq = '" + "?prtseq?"
				+ "' and replycontente is not null";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL_R);
		sqlbv2.put("contno", this.mContNo);
		sqlbv2.put("prtseq", this.mOldPrtSeq);
		// tRnewRReportDB.setContNo(this.mContNo);
		// tRnewRReportDB.setPrtSeq(this.mOldPrtSeq);
		//        
		tRnewRReportSet = tRnewRReportDB.executeQuery(sqlbv2);
		if (tRnewRReportSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "UWPrintTakeBackAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "请先回复生调通知书后再回收!";
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mTakeBackOperator = (String) mTransferData
				.getValueByName("TakeBackOperator");
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务处理数据
		mPrtSeq = (String) mTransferData.getValueByName("CertifyNo");
		if (mPrtSeq == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输单证号出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// //获得业务处理数据
		// mRnewRReportSchema = (RnewRReportSchema) mTransferData.getValueByName(
		// "RnewRReportSchema");
		// if (mRnewRReportSchema == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中LPRReportSchema失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mReplyContente = mRnewRReportSchema.getReplyContente();
		// mPrtSeq = mRnewRReportSchema.getPrtSeq();
		// if (mPrtSeq == null || mPrtSeq.trim().equals(""))
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PrintTakeBackRReportAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中LPRReportSchema中的PrtSeq失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 打印队列
		if (preparePrint() == false) {
			return false;
		}
		if (prepareAutoSysCertSendOut() == false) {
			return false;
		}
		if (prepareTransferData() == false) {
			return false;
		}
		if (prepareAutoReport() == false) {
			return false;
		}
        //处理工作流信息
//		if(!dealMission()){
//			return false;
//		}
		return true;
	}
	private boolean dealMission(){
		//给发送通知书任务置上操作员
		LWMissionSchema xLWMissionSchema = new LWMissionSchema();
		LWMissionDB xLWMissionDB = new LWMissionDB();
		LWMissionSet xLWMissionSet = new LWMissionSet();
		xLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		xLWMissionDB.setActivityID("0000007012");
		xLWMissionSet=xLWMissionDB.query();
		if(xLWMissionSet.size()!=1){
//			if(!tLWMissionDB.getInfo()){
			CError.buildErr(this, "查询LWMission失败！");
			return false;
		}
		xLWMissionSchema = xLWMissionSet.get(1);
		xLWMissionSchema.setDefaultOperator(this.mGlobalInput.Operator);
		xLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		xLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		mLWMissionSet.add(xLWMissionSchema);
		return true;
	}
	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */

	//
	private boolean prepareAutoReport() {
		RnewRReportDB tLCCReportDB = new RnewRReportDB();
		RnewRReportSet tLCCReportSet = new RnewRReportSet();
		tLCCReportDB.setPrtSeq(mOldPrtSeq);
		tLCCReportSet = tLCCReportDB.query();
		for (int i = 1; i <= tLCCReportSet.size(); i++) {
			mRnewRReportSchema.setSchema(tLCCReportSet.get(i));
			mRnewRReportSchema.setReplyFlag("2");
			mRnewRReportSet.add(mRnewRReportSchema);
		}

		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */

	private boolean preparePrint() {
		// 准备打印管理表数据
		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			mLOPRTManagerSet.get(i).setStateFlag("2");
		}
//		 核保表信息
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterDB.setContNo(this.mContNo);
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterSet.size()==0) 
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendNoticeAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for(int i=1;i<=tLCUWMasterSet.size();i++)
		{
		  mLCUWMasterSchema.setSchema(tLCUWMasterSet.get(i));
		  mLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		  mLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		  mLCUWMasterSchema.setReportFlag("3");// 回收生调通知书
		  mLCUWMasterSet.add(mLCUWMasterSchema);
		}
		return true;
	}

	private boolean prepareAutoSysCertSendOut() {
		// 准备单证回收管理表数据
		for (int i = 1; i <= mLZSysCertifySet.size(); i++) {
			mLZSysCertifySet.get(i)
					.setTakeBackMakeDate(PubFun.getCurrentDate());
			mLZSysCertifySet.get(i)
					.setTakeBackMakeTime(PubFun.getCurrentTime());
			mLZSysCertifySet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLZSysCertifySet.get(i).setModifyTime(PubFun.getCurrentTime());
			mLZSysCertifySet.get(i).setTakeBackOperator(mTakeBackOperator);
			mLZSysCertifySet.get(i).setStateFlag("1");
		}
		return true;
	}

	private boolean prepareTransferData() {
		logger.debug("mLOPRTManagerSchema.getOldPrtSeq():"
				+ mLOPRTManagerSchema.getOldPrtSeq());
		mTransferData
				.setNameAndValue("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		mTransferData.setNameAndValue("OldPrtSeq", mLOPRTManagerSchema
				.getOldPrtSeq());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());

		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());

		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("Operator", mLCContSchema.getOperator());
		mTransferData.setNameAndValue("MakeDate", mLCContSchema.getMakeDate());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());

		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
