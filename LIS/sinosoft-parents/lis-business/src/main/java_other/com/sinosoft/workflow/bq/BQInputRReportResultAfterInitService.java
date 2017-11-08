package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
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
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPRReportSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class BQInputRReportResultAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(BQInputRReportResultAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mOperate;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mEdorNo;
	private String mCode;
	private String mNewPrtSeq;

	/** 保单表 */
	private LPContSchema mLPContSchema = new LPContSchema();
	private MMap mMap = new MMap();
	private LPRReportSchema mLPRReportSchema = new LPRReportSchema();
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();

	public BQInputRReportResultAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		//MMap map = new MMap();

		mMap.put(mLPRReportSchema, "UPDATE");
		if (mLPCUWMasterSchema != null) {
			mMap.put(mLPCUWMasterSchema, "UPDATE");
		}
		mResult.add(mMap);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		//查询工作流信息
		 LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		 LWMissionDB tLWMissionDB = new LWMissionDB();
		 tLWMissionDB.setMissionID(mMissionID);
		 tLWMissionDB.setSubMissionID(mSubMissionID);
		 tLWMissionDB.setActivityID(mOperate);
		 if (!tLWMissionDB.getInfo())
		 {
			 CError.buildErr(this, "工作流表查询失败!") ;
			 return false;
		 }
		
		 tLWMissionSchema.setSchema(tLWMissionDB);
		 mNewPrtSeq = tLWMissionSchema.getMissionProp3();
		
		// 校验保单信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setContNo(mContNo);
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size()!=1) {
			CError.buildErr(this, "保单" + mContNo + "信息查询失败!");
			return false;
		}
		mLPContSchema.setSchema(tLPContSet.get(1));

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            输入数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
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
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		mLPRReportSchema.setSchema((LPRReportSchema) cInputData.getObjectByObjectName("LPRReportSchema", 0));
		if (mLPRReportSchema == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LCRReport失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		mOperate = cOperate;
//		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据SubMissionID失败！");
			return false;
		}
		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}		
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中EdorNo失败!");
			return false;
		}
		mCode = (String) mTransferData.getValueByName("Code");
		if (mCode == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中Code失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (prepareReport() == false) {
			return false;
		}

		return true;
	}

	private boolean prepareReport() {
		// 取回复内容
		String tReplyContent = mLPRReportSchema.getReplyContente();
		// 取调查人
		String tInvOperator = mLPRReportSchema.getRemark();
		// 取生调费用
		double tRreportFee = mLPRReportSchema.getRReportFee();
		logger.debug(tReplyContent);
		// 取生调记录
		String tsql = "select * from LPRReport where ContNo = '"
				+ "?ContNo?" + "' and prtseq =  '"
				+"?prtseq?"+ "' and replyflag = '0'";

		logger.debug("sql:" + tsql);
		LPRReportDB tLPRReportDB = new LPRReportDB();
		LPRReportSet tLPRReportSet = new LPRReportSet();
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tsql);
		sqlbv1.put("ContNo",mLPRReportSchema.getContNo());
		sqlbv1.put("prtseq",mLPRReportSchema.getPrtSeq());
		tLPRReportSet = tLPRReportDB.executeQuery(sqlbv1);

		if (tLPRReportSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "此单已经回复!");
			return false;
		} else {
			mLPRReportSchema = tLPRReportSet.get(1);
		}

		mLPRReportSchema.setReplyContente(tReplyContent);
		mLPRReportSchema.setRReportFee(tRreportFee);
		mLPRReportSchema.setReplyFlag("1");
		mLPRReportSchema.setReplyOperator(mOperater);//回复人
		mLPRReportSchema.setRemark(tInvOperator);//生存调查人
		mLPRReportSchema.setReplyDate(PubFun.getCurrentDate());
		mLPRReportSchema.setReplyTime(PubFun.getCurrentTime());
		mLPRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mLPRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// 核保主表信息
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setContNo(mLPRReportSchema.getContNo());
		if (tLPCUWMasterDB.getInfo() == false) {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "UWRReportBL";
			// tError.functionName = "prepareReport";
			// tError.errorMessage = "无核保主表信息!";
			// this.mErrors.addOneError(tError);
			// return false;
			mLPCUWMasterSchema = null; // 为使保全核保能复用新契约核保下发生调通知书，更改查询新契约核保表的内容。
										// modified by zhangrong
		} else {
			mLPCUWMasterSchema = tLPCUWMasterDB.getSchema();
			mLPCUWMasterSchema.setReportFlag("2");
		}
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {
		
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("syscertifycode");
		tLDCodeDB.setCode(mCode);
		if (!tLDCodeDB.getInfo()) {
			return true;
		}
		//查询单证表
		LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
		LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
		tLZSysCertifyDB.setCertifyCode(tLDCodeDB.getCodeName());
		tLZSysCertifyDB.setCertifyNo(mNewPrtSeq);
		tLZSysCertifySet = tLZSysCertifyDB.query();
		if (tLZSysCertifySet == null || tLZSysCertifySet.size() < 0) {
			// @@错误处理
			CError.buildErr(this, "单证表LZSysCertify查询失败!");
			return false;
		}
		LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		tLZSysCertifySchema = tLZSysCertifySet.get(1);
		
		// 处于未打印状态的体检通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerDB.setCode(mCode); // 面见通知书
		tLOPRTManagerDB.setPrtSeq(mNewPrtSeq);
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStateFlag("1");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError.buildErr(this, "查询打印管理表信息出错!");
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "在打印队列中没有处于未打印状态的核保通知书!");
			return false;
		}        
		
		mTransferData.setNameAndValue("PrtSeq", mNewPrtSeq);
		mTransferData.setNameAndValue("PrtNo", mLPContSchema.getPrtNo());
		
		mTransferData.setNameAndValue("CertifyCode", tLZSysCertifySchema
				.getCertifyCode());
		mTransferData.setNameAndValue("SendOutCom", tLZSysCertifySchema
				.getSendOutCom());
		mTransferData.setNameAndValue("ReceiveCom", tLZSysCertifySchema
				.getReceiveCom());
		mTransferData.setNameAndValue("Handler", tLZSysCertifySchema
				.getHandler());
		mTransferData.setNameAndValue("HandleDate", tLZSysCertifySchema
				.getHandleDate());
		mTransferData.setNameAndValue("Operator", tLZSysCertifySchema
				.getOperator());
		mTransferData.setNameAndValue("MakeDate", tLZSysCertifySchema
				.getMakeDate());
		mTransferData
				.setNameAndValue("SendNo", tLZSysCertifySchema.getSendNo());
		mTransferData.setNameAndValue("TakeBackNo", tLZSysCertifySchema
				.getTakeBackNo());
//		 mTransferData.setNameAndValue("ValidDate",tLZSysCertifySchema
//				 .getValidDate());

		boolean tPatchFlag = false;
		tLOPRTManagerSchema = tLOPRTManagerSet.get(1);
		if (tLOPRTManagerSchema.getPatchFlag() == null)
			tPatchFlag = false;
		else if (tLOPRTManagerSchema.getPatchFlag().equals("0"))
			tPatchFlag = false;
		else if (tLOPRTManagerSchema.getPatchFlag().equals("1"))
			tPatchFlag = true;
		if (tPatchFlag == true) {
			mTransferData.setNameAndValue("OldPrtSeq", tLOPRTManagerSchema
					.getOldPrtSeq());
		} else {
			mTransferData.setNameAndValue("OldPrtSeq", tLOPRTManagerSchema
					.getPrtSeq());
		}
		mTransferData.setNameAndValue("DoneDate", tLOPRTManagerSchema
				.getDoneDate());
		mTransferData.setNameAndValue("ManageCom", tLOPRTManagerSchema
				.getManageCom());
		mTransferData.setNameAndValue("ExeOperator", tLOPRTManagerSchema
				.getExeOperator());
		//将打印表状态置为打印单据已回复
		//tLOPRTManagerSchema.setStateFlag("2");
		mMap.put(tLOPRTManagerSchema, "UPDATE");
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
