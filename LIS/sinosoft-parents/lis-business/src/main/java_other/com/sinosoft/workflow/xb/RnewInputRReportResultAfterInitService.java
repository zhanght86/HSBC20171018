package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LDCodeDB;
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
import com.sinosoft.lis.schema.LZSysCertifySchema;
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
public class RnewInputRReportResultAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RnewInputRReportResultAfterInitService.class);
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

//	险种信息
	private String mRiskCode="";
	private String mProposalNo="";
	
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private MMap mMap;
	private RnewRReportSchema mRnewRReportSchema = new RnewRReportSchema();
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	public RnewInputRReportResultAfterInitService() {
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
		map.put(mLWMissionSet, "UPDATE");
		map.put(mRnewRReportSchema, "UPDATE");
		map.put(mLCUWMasterSet, "UPDATE");
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

		mRnewRReportSchema.setSchema((RnewRReportSchema) cInputData.getObjectByObjectName("RnewRReportSchema", 0));
		if (mRnewRReportSchema == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据RnewRReport失败!");
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
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

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
        //处理工作流信息
//		if(!dealMission())
//			return false;

		return true;
	}
	private boolean dealMission(){
		//给发送通知书任务置上操作员
		LWMissionSchema xLWMissionSchema = new LWMissionSchema();
		LWMissionDB xLWMissionDB = new LWMissionDB();
		LWMissionSet xLWMissionSet = new LWMissionSet();
		xLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		xLWMissionDB.setActivityID("0000007014");
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
	private boolean prepareReport() {
		// 取回复内容
		String tReplyContent = mRnewRReportSchema.getReplyContente();
		// 取调查人
		String tInvOperator = mRnewRReportSchema.getRemark();
		// 取生调费用
		double tRreportFee = mRnewRReportSchema.getRReportFee();
		logger.debug(tReplyContent);
		// 取生调记录
		String tsql = "select * from RnewRReport where ContNo = '"
				+ "?ContNo?" + "' and prtseq =  '"
				+ "?prtseq?" + "' and replyflag = '0'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("ContNo", mRnewRReportSchema.getContNo());
		sqlbv.put("prtseq", mRnewRReportSchema.getPrtSeq());
		logger.debug("sql:" + tsql);
		RnewRReportDB tRnewRReportDB = new RnewRReportDB();
		RnewRReportSet tRnewRReportSet = new RnewRReportSet();

		tRnewRReportSet = tRnewRReportDB.executeQuery(sqlbv);

		if (tRnewRReportSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "此单已经回复!");
			return false;
		} else {
			mRnewRReportSchema = tRnewRReportSet.get(1);
		}

		mRnewRReportSchema.setReplyContente(tReplyContent);
		mRnewRReportSchema.setRReportFee(tRreportFee);
		mRnewRReportSchema.setReplyFlag("1");
		mRnewRReportSchema.setReplyOperator(mOperater);//回复人
		mRnewRReportSchema.setRemark(tInvOperator);//生存调查人
		mRnewRReportSchema.setReplyDate(PubFun.getCurrentDate());
		mRnewRReportSchema.setReplyTime(PubFun.getCurrentTime());
		mRnewRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mRnewRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// 核保表信息
		//获取投保单号
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
		  mLCUWMasterSchema.setReportFlag("2");// 回收生调通知书
		  mLCUWMasterSet.add(mLCUWMasterSchema);
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
		tLDCodeDB.setCode("44");
		if (!tLDCodeDB.getInfo()) {
			return true;
		}
		//查询单证表
		LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
		LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
		tLZSysCertifyDB.setCertifyCode(tLDCodeDB.getCodeName());
		tLZSysCertifyDB.setCertifyNo(mRnewRReportSchema.getPrtSeq());
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

		tLOPRTManagerDB.setCode("44"); // 面见通知书
		tLOPRTManagerDB.setPrtSeq(mRnewRReportSchema.getPrtSeq());
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
		
		mTransferData.setNameAndValue("PrtSeq", mRnewRReportSchema.getPrtSeq());
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
