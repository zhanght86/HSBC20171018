package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPRReportDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPRReportSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPRReportSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class BQTakeBackRReportAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(BQTakeBackRReportAfterInitService.class);
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
	private String mEdorNo;
	private String mEdorType;
	private String mEdorAcceptNo;
	/** 执行保全工作流特约活动表任务0000000013 */
	/** 保单表 */
	private LPContSchema mLPContSchema = new LPContSchema();

	/** 保全核保主表 */
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	/** 单证发放表 */
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();

	/** 生存调查表 */
	private LPRReportSet mLPReportSet = new LPRReportSet();
	private LPRReportSchema mLPRReportSchema = new LPRReportSchema();

	public BQTakeBackRReportAfterInitService() {
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

		// 添加核保通知书打印管理表数据
		if (mLOPRTManagerSet != null && mLOPRTManagerSet.size() > 0) {
			map.put(mLOPRTManagerSet, "UPDATE");
		}
		// 添加续保体检通知书自动发放表数据
		if (mLZSysCertifySet != null && mLZSysCertifySet.size() > 0) {
			map.put(mLZSysCertifySet, "UPDATE");
		}
		map.put(this.mLPCUWMasterSchema, "UPDATE");
		map.put(mLPReportSet, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setActivityID("0000000314");
		String sqlstr="select * from lwmission where missionid='"+"?missionid?"+"' and activityid in(select activityid from lwactivity where functionid='10020314')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sqlstr);
		sqlbv1.put("missionid",this.mMissionID);
		tLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		ExeSQL tExeSQL = new ExeSQL();
		if(tLWMissionSet.size()<1){
			CError.buildErr(this, "查询工作流信息失败！");
			return false;
		}
		
//		tLWMissionSet = tLWMissionDB.query();
//		if(tLWMissionSet.size()<1){
//			CError.buildErr(this, "查询工作流信息失败！");
//			return false;
//		}
//		mEdorAcceptNo = tLWMissionSet.get(1).getMissionProp1();
//		//mEdorType = tLWMissionSet.get(1).getMissionProp9();
//		String EdorNoSql="select edorno from lpedormain where edoracceptno='"+mEdorAcceptNo+"'";
//		ExeSQL tExeSQL = new ExeSQL();
//		mEdorNo = tExeSQL.getOneValue(EdorNoSql);
		mEdorNo = tLWMissionSet.get(1).getMissionProp15();
		String EdorAcceptNoSql = "select edoracceptno from lpedormain where edorno='"+"?edorno?"+"'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(EdorAcceptNoSql);
		sqlbv2.put("edorno",tLWMissionSet.get(1).getMissionProp15());
		mEdorAcceptNo = tExeSQL.getOneValue(sqlbv2);
		// 校验保单信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mEdorNo);
		//tLPContDB.setEdorType(mEdorType);
		tLPContDB.setContNo(mContNo);
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContSet==null || tLPContSet.size()!=1) {
			CError.buildErr(this, "保单" + mContNo + "信息查询失败!") ;
			return false;
		}
		mLPContSchema.setSchema(tLPContSet.get(1));

		// 校验保全批单核保主表
		// 校验保单信息
		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setContNo(mContNo);
		// if (!tLCCUWMasterDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "保全批单核保主表信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);

		// 处于已打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode("24"); //保全生调通知书
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStateFlag("1");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError.buildErr(this, "查询打印管理表信息出错!") ;
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "在打印队列中没有处于已打印待回收状态的生调通知书!") ;
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
			
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tStr);
			sqlbv3.put("mOldPrtSeq",mOldPrtSeq);
			LOPRTManagerSet tempLOPRTManagerSet = tempLOPRTManagerDB
					.executeQuery(sqlbv3);
			;
			if (tempLOPRTManagerSet.size() == 1) {
				// @@错误处理
				CError.buildErr(this, "查询在打印队列中没有该补打生调通知书的原通知书记录信息出错!") ;
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
				if (mLOPRTManagerSet.get(i).getCode().trim().equals("24")) {
					tLZSysCertifyDB.setCertifyCode("2010"); // 保全生调通知书标识
				}
				tLZSysCertifyDB.setCertifyNo(mLOPRTManagerSet.get(i)
						.getPrtSeq());
				tLZSysCertifySet = tLZSysCertifyDB.query();
				if (tLZSysCertifySet == null || tLZSysCertifySet.size() != 1) {
					// @@错误处理
					CError.buildErr(this, "回收生调通知书时,LZSysCertifySchema表信息查询失败!") ;
					return false;
				}
				mLZSysCertifySet.add(tLZSysCertifySet.get(1));
			}
		}
		// tongmeng 2007-12-18 add
		LPRReportSet tLPRReportSet = new LPRReportSet();
		LPRReportDB tLPRReportDB = new LPRReportDB();
		String tSQL_R = " select * from LPRReport where contno='"
				+ "?contno?" + "' " + " and prtseq = '" + "?prtseq?"
				+ "' and replycontente is not null";
		// tLCRReportDB.setContNo(this.mContNo);
		// tLCRReportDB.setPrtSeq(this.mOldPrtSeq);
		//        
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL_R);
		sqlbv4.put("contno",this.mContNo);
		sqlbv4.put("prtseq",this.mOldPrtSeq);
		tLPRReportSet = tLPRReportDB.executeQuery(sqlbv4);
		if (tLPRReportSet.size() <= 0) {
			CError.buildErr(this, "请先回复生调通知书后再回收!") ;
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
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!") ;
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operate失败!") ;
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!") ;
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据失败!") ;
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!") ;
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中MissionID失败!") ;
			return false;
		}

		// 获得业务处理数据
		mPrtSeq = (String) mTransferData.getValueByName("CertifyNo");
		if (mPrtSeq == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输单证号出错!") ;
			return false;
		}

		// //获得业务处理数据
		// mLCRReportSchema = (LCRReportSchema) mTransferData.getValueByName(
		// "LCRReportSchema");
		// if (mLCRReportSchema == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLPContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PEdorPrintTakeBackRReportAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中LPRReportSchema失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mReplyContente = mLCRReportSchema.getReplyContente();
		// mPrtSeq = mLCRReportSchema.getPrtSeq();
		// if (mPrtSeq == null || mPrtSeq.trim().equals(""))
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLPContDB.mErrors );
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
		return true;
	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */

	//
	private boolean prepareAutoReport() {
		LPRReportDB tLPReportDB = new LPRReportDB();
		LPRReportSet tLPReportSet = new LPRReportSet();
		tLPReportDB.setPrtSeq(mOldPrtSeq);
		tLPReportSet = tLPReportDB.query();
		for (int i = 1; i <= tLPReportSet.size(); i++) {
			mLPRReportSchema.setSchema(tLPReportSet.get(i));
			mLPRReportSchema.setReplyFlag("2");
			mLPReportSet.add(mLPRReportSchema);
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
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		LPCUWMasterSet tLPCUWMasterSet = new LPCUWMasterSet();
		tLPCUWMasterDB.setContNo(mContNo);
		//tLPCUWMasterDB.setEdorType(mEdorType);
		tLPCUWMasterDB.setEdorNo(mEdorNo);
		tLPCUWMasterSet = tLPCUWMasterDB.query();
		if (tLPCUWMasterSet==null || tLPCUWMasterSet.size()<1) {
			// @@错误处理
			CError.buildErr(this, "LPCUWMaster表取数失败!");
			return false;
		}
		mLPCUWMasterSchema.setSchema(tLPCUWMasterDB);
		mLPCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLPCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		mLPCUWMasterSchema.setReportFlag("2");// 回收生调通知书

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
		mTransferData.setNameAndValue("PrtNo", mLPContSchema.getPrtNo());
		mTransferData.setNameAndValue("ContNo", mLPContSchema.getContNo());

		mTransferData
				.setNameAndValue("ManageCom", mLPContSchema.getManageCom());

		mTransferData.setNameAndValue("AppntNo", mLPContSchema.getAppntNo());
		mTransferData
				.setNameAndValue("AppntName", mLPContSchema.getAppntName());
		mTransferData.setNameAndValue("Operator", mLPContSchema.getOperator());
		mTransferData.setNameAndValue("MakeDate", mLPContSchema.getMakeDate());
		mTransferData
				.setNameAndValue("AgentCode", mLPContSchema.getAgentCode());
		mTransferData
				.setNameAndValue("ManageCom", mLPContSchema.getManageCom());

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
