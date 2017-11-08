package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HL
 * @version 6.0
 */
public class InputRReportResultAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(InputRReportResultAfterInitService.class);
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

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private MMap mMap;
	private LCRReportSchema mLCRReportSchema = new LCRReportSchema();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	public InputRReportResultAfterInitService() {
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
		MMap map = new MMap();

		map.put(mLCRReportSchema, "UPDATE");
		if (mLCCUWMasterSchema != null) {
			map.put(mLCCUWMasterSchema, "UPDATE");
		}
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "保单" + mContNo + "信息查询失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

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

		mLCRReportSchema.setSchema((LCRReportSchema) cInputData.getObjectByObjectName("LCRReportSchema", 0));
		if (mLCRReportSchema == null) {
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
//		mMissionID = (String) mTransferData.getValueByName("MissionID");
//		if (mMissionID == null) {
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
//			CError tError = new CError();
//			tError.moduleName = "InputRReportResultAfterInitService";
//			tError.functionName = "getInputData";
//			tError.errorMessage = "前台传输业务数据中MissionID失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}

		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
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
		String tReplyContent = mLCRReportSchema.getReplyContente();
		// 取调查人
		String tInvOperator = mLCRReportSchema.getRemark();
		// 取生调费用
		double tRreportFee = mLCRReportSchema.getRReportFee();
		logger.debug(tReplyContent);
		// 取生调记录
		String tsql = "select * from LCRReport where ContNo = '"
				+ "?ContNo?" + "' and prtseq =  '"
				+ "?prtseq?" + "' and replyflag = '0'";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tsql);
        sqlbv1.put("ContNo", mLCRReportSchema.getContNo());
        sqlbv1.put("prtseq", mLCRReportSchema.getPrtSeq());
		logger.debug("sql:" + tsql);
		LCRReportDB tLCRReportDB = new LCRReportDB();
		LCRReportSet tLCRReportSet = new LCRReportSet();

		tLCRReportSet = tLCRReportDB.executeQuery(sqlbv1);

		if (tLCRReportSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "此单已经回复!");
			return false;
		} else {
			mLCRReportSchema = tLCRReportSet.get(1);
		}

		mLCRReportSchema.setReplyContente(tReplyContent);
		mLCRReportSchema.setRReportFee(tRreportFee);
		mLCRReportSchema.setReplyFlag("1");
		mLCRReportSchema.setReplyOperator(mOperater);//回复人
		mLCRReportSchema.setRemark(tInvOperator);//生存调查人
		mLCRReportSchema.setReplyDate(PubFun.getCurrentDate());
		mLCRReportSchema.setReplyTime(PubFun.getCurrentTime());
		mLCRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mLCRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// 核保主表信息
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLCRReportSchema.getContNo());

		if (tLCCUWMasterDB.getInfo() == false) {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "UWRReportBL";
			// tError.functionName = "prepareReport";
			// tError.errorMessage = "无核保主表信息!";
			// this.mErrors.addOneError(tError);
			// return false;
			mLCCUWMasterSchema = null; // 为使保全核保能复用新契约核保下发生调通知书，更改查询新契约核保表的内容。
										// modified by zhangrong
		} else {
			mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
			mLCCUWMasterSchema.setReportFlag("2");
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
		tLDCodeDB.setCode(PrintManagerBL.CODE_MEET);
		if (!tLDCodeDB.getInfo()) {
			return true;
		}
		//查询单证表
		LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
		LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
		tLZSysCertifyDB.setCertifyCode(tLDCodeDB.getCodeName());
		tLZSysCertifyDB.setCertifyNo(mLCRReportSchema.getPrtSeq());
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

		//tLOPRTManagerDB.setCode(PrintManagerBL.CODE_MEET); // 面见通知书
		//tLOPRTManagerDB.setPrtSeq(mLCRReportSchema.getPrtSeq());
		//tLOPRTManagerDB.setOtherNo(mContNo);
		//tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		//tLOPRTManagerDB.setStateFlag("1");
		String tSql = "select * from LOPRTManager where code='"+"?ContNo2?"+"' and otherno='"+"?prtseq2?"+"' "
		           + " and othernotype='"+"?othernotype2?"+"' and stateflag='1' order by prtseq desc ";
		   SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(tSql);
	        sqlbv2.put("ContNo2", PrintManagerBL.CODE_MEET);
	        sqlbv2.put("prtseq2", mContNo);
	        sqlbv2.put("othernotype2", PrintManagerBL.ONT_CONT);
		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(sqlbv2);
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError.buildErr(this, "查询打印管理表信息出错!");
			return false;
		}

		if (tLOPRTManagerSet.size() < 1) {
			// @@错误处理
			CError.buildErr(this, "在打印队列中没有处于未打印状态的核保通知书!");
			return false;
		}        
		tLOPRTManagerSchema.setSchema(tLOPRTManagerSet.get(1));
		mTransferData.setNameAndValue("PrtSeq", tLOPRTManagerSchema.getPrtSeq());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		
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
		 mTransferData.setNameAndValue("ValidDate",tLZSysCertifySchema
				 .getValidDate());

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
