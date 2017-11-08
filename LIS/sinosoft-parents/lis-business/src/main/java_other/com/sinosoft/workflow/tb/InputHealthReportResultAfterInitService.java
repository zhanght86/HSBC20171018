package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.DisDesbBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCPENoticeReplySet;
import com.sinosoft.lis.vschema.LCPENoticeResultSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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
 * @author HL modify by zhangxing 解决体检合并问题
 * @version 6.0
 */
public class InputHealthReportResultAfterInitService implements
		AfterInitService {
private static Logger logger = Logger.getLogger(InputHealthReportResultAfterInitService.class);

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
	private String mPrtSeq; // 问题件打印流水号
	/** mUWFlag=1 来自人工核保*/
	private String mUWFlag;
	/**人工核保新增体检结果 模拟生成体检表数据09-06-11*/
	private String mNewPrtSeq;
	/**第一次录入标志*/
	private String mNewFlag;
	private String mPEName;
	private String tCustomerno = "";
	private String tCustomerType = "";

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private MMap mMap;
	private LCPENoticeResultSet mLCPENoticeResultSet = new LCPENoticeResultSet();
	private LCPENoticeReplySet mLCPENoticeReplySet = new LCPENoticeReplySet();
	private LCPENoticeSet mLCPENoticeSet = new LCPENoticeSet();
	private LCPENoticeSet mLCPENoticeSet1 = new LCPENoticeSet();
	private LCPENoticeSchema mLCPENoticeSchema = new LCPENoticeSchema();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCPENoticeItemSchema mLCPENoticeItemSchema = new LCPENoticeItemSchema();
	public InputHealthReportResultAfterInitService() {
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

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

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

		if("1".equals(mUWFlag)){
			PubSubmit tPubSubmit = new PubSubmit();
			logger.debug("Start tPRnewManualDunBLS Submit...");
	
			if (!tPubSubmit.submitData(mResult, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
	
				CError.buildErr(this,"数据提交失败!");
				return false;
			}
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
		
		if (this.mLCPENoticeResultSet!=null && this.mLCPENoticeResultSet.size()>0)
			map.put(this.mLCPENoticeResultSet, "DELETE&INSERT");
		if (this.mLCPENoticeReplySet!=null && this.mLCPENoticeReplySet.size()>0)
			map.put(this.mLCPENoticeReplySet, "DELETE&INSERT");
		if (this.mLCPENoticeSet1!=null && this.mLCPENoticeSet1.size()>0){
			map.put(this.mLCPENoticeSet1, "UPDATE");
		}
		if("1".equals(mNewFlag)){
			map.put(mLCPENoticeSchema, "INSERT");
			map.put(mLOPRTManagerSchema, "INSERT");
			map.put(mLCPENoticeItemSchema, "INSERT");
		}
		//map.add(this.mMap);

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
		tLCContDB.setContNo(mLCPENoticeSet.get(1).getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "保单" + mLCPENoticeSet.get(1).getContNo() + "信息查询失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);
		mPrtSeq = mLCPENoticeSet.get(1).getPrtSeq();
		//第一次录入 生成prtseq
		if("1".equals(mUWFlag)&&(mPrtSeq==null||"".equals(mPrtSeq)||"null".equals(mPrtSeq))){
			mNewPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PE);
			mNewFlag = "1";
			logger.debug("第一次录入体检信息！");
			String customernoSql = "select insuredno,'I' from lcinsured where name='"+"?name?"+"' and grpcontno='00000000000000000000'"
			+ " and contno='"+"?contno1?"+"'"
			+ " union "
			+ " select appntno,'A' from lcappnt where appntname='"+"?name2?"+"' and grpcontno='00000000000000000000'"
			+ " and contno='"+"?contno2?"+"'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(customernoSql);
			sqlbv1.put("name", mPEName);
			sqlbv1.put("contno1",mLCPENoticeSet.get(1).getContNo());
			sqlbv1.put("name1", mPEName);
			sqlbv1.put("contno2",mLCPENoticeSet.get(1).getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			//String tCustomerno = tExeSQL.getOneValue(customernoSql);
			if(tSSRS.getMaxRow()>0){
				tCustomerno = tSSRS.GetText(1, 1);
				tCustomerType = tSSRS.GetText(1, 2);
			}else{
			CError.buildErr(this, "体检结果保存失败！请确认体检人姓名是否为本本单下的投保人或被保人！");
			return false;
			}
		}
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
		mInputData = cInputData;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mLCPENoticeResultSet = (LCPENoticeResultSet) cInputData
				.getObjectByObjectName("LCPENoticeResultSet", 0);
		if (mLCPENoticeResultSet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LCPENoticeResultSet失败!");
			return false;
		}

		mLCPENoticeReplySet = (LCPENoticeReplySet) cInputData
				.getObjectByObjectName("LCPENoticeReplySet", 0);
		if (mLCPENoticeReplySet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LCPENoticeItemSet失败!");
			return false;
		}

		mLCPENoticeSet = (LCPENoticeSet) cInputData.getObjectByObjectName(
				"LCPENoticeSet", 0);
		if (mLCPENoticeSet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LCPENoticeSet失败!");
			return false;
		}

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
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
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mPEName = (String) mTransferData.getValueByName("PEName");
		mPrtSeq = mLCPENoticeSet.get(1).getPrtSeq();

		// ********************业务处理相关Java类**************************************
		// mLCPENoticeResultSet = (LCPENoticeResultSet) mTransferData
		// .getValueByName("LCPENoticeResultSet");
		// if (mLCPENoticeResultSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据LCPENoticeResultSet失败!");
		// return false;
		// }
		//
		// mLCPENoticeItemSet = (LCPENoticeItemSet) mTransferData
		// .getValueByName("LCPENoticeItemSet");
		// if (mLCPENoticeItemSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据LCPENoticeItemSet失败!");
		// return false;
		// }
		//
		// mLCPENoticeSet = (LCPENoticeSet) mTransferData
		// .getValueByName("LCPENoticeSet");
		// if (mLCPENoticeSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据LCPENoticeSet失败!");
		// return false;
		// }

		// ////////////////////////////////////////////////////////////////////////////

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		try {
			logger.debug("come in  dealData()");
			mPrtSeq = mLCPENoticeSet.get(1).getPrtSeq();
			
			// 保存既往病史
			int tSerialNo = 1;
			for (int i = 1; i <= mLCPENoticeResultSet.size(); i++) {
				mLCPENoticeResultSet.get(i).setSerialNo("" + tSerialNo);
				if(mLCPENoticeResultSet.get(i).getDisDesb()== null||mLCPENoticeResultSet.get(i).getDisDesb().equals("")){
				mLCPENoticeResultSet.get(i).setDisDesb("" + tSerialNo);
				}
				mLCPENoticeResultSet.get(i).setICDCode("" + tSerialNo);
				mLCPENoticeResultSet.get(i).setOperator(mOperater);
				mLCPENoticeResultSet.get(i)
						.setMakeDate(PubFun.getCurrentDate());
				mLCPENoticeResultSet.get(i)
						.setMakeTime(PubFun.getCurrentTime());
				mLCPENoticeResultSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCPENoticeResultSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
				if("1".equals(mNewFlag)){
					mLCPENoticeResultSet.get(i).setPrtSeq(mNewPrtSeq);
					mLCPENoticeResultSet.get(i).setCustomerNo(tCustomerno);
				}
				
			}

			for (int i = 1; i <= mLCPENoticeReplySet.size(); i++) {
				mLCPENoticeReplySet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCPENoticeReplySet.get(i).setModifyTime(
						PubFun.getCurrentTime());
				if("1".equals(mNewFlag)){
					mLCPENoticeReplySet.get(i).setPrtSeq(mNewPrtSeq);
				}
				

			}
			if(!"1".equals(mNewFlag)){
				LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
				LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
				tLCPENoticeDB.setProposalContNo(mLCPENoticeSet.get(1).getContNo());
				tLCPENoticeDB.setPrtSeq(mLCPENoticeSet.get(1).getPrtSeq());
				tLCPENoticeDB.setCustomerNo(mLCPENoticeSet.get(1).getCustomerNo());
				
				if (!tLCPENoticeDB.getInfo()) {
					CError.buildErr(this, "LCPENotice表查询失败!");
					return false;
				}
				tLCPENoticeSchema.setSchema(tLCPENoticeDB);
				for (int i = 1; i <= mLCPENoticeSet.size(); i++) {
					tLCPENoticeSchema.setPEDoctor(mOperater);//用作体检结果录入人
					tLCPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
					tLCPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
					tLCPENoticeSchema.setPEResult(mLCPENoticeSet.get(i).getPEResult());
					tLCPENoticeSchema.setHospitCode(mLCPENoticeSet.get(i).getHospitCode());//2009-01-05 ln add 体检医院代码
					tLCPENoticeSchema.setPEAddress(mLCPENoticeSet.get(i).getPEAddress());
					tLCPENoticeSchema.setAgentName(mLCPENoticeSet.get(i).getAgentName());
					tLCPENoticeSchema.setREPEDate(mLCPENoticeSet.get(i).getREPEDate());
					mLCPENoticeSet1.add(tLCPENoticeSchema);
	
				}
			}else{
				mLCPENoticeSchema.setPrtSeq(mNewPrtSeq);
				mLCPENoticeSchema.setContNo(mLCPENoticeSet.get(1).getContNo());
				mLCPENoticeSchema.setPrtSeq(mNewPrtSeq);
				mLCPENoticeSchema.setPEDate(mLCPENoticeSet.get(1).getREPEDate());
//				mLCPENoticeSchema.setPEBeforeCond(tIfEmpty);
				mLCPENoticeSchema.setRemark("人工核保录入体检信息");
				mLCPENoticeSchema.setCustomerNo(tCustomerno);
				mLCPENoticeSchema.setPEReason("");  
				mLCPENoticeSchema.setCustomerType(tCustomerType);
				mLCPENoticeSchema.setGrpContNo(mLCContSchema.getGrpContNo());
				mLCPENoticeSchema.setProposalContNo(mLCContSchema.getProposalContNo());
				mLCPENoticeSchema.setName(mPEName);
				mLCPENoticeSchema.setPrintFlag("2");
				mLCPENoticeSchema.setAppName(mLCContSchema.getAppntName());
				mLCPENoticeSchema.setAgentCode(mLCContSchema.getAgentCode());
				mLCPENoticeSchema.setManageCom(mLCContSchema.getManageCom());
				mLCPENoticeSchema.setOperator(mOperater); // 操作员
				mLCPENoticeSchema.setMakeDate(PubFun.getCurrentDate());
				mLCPENoticeSchema.setMakeTime(PubFun.getCurrentTime());
				mLCPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
				mLCPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
				mLCPENoticeSchema.setPEResult(mLCPENoticeSet.get(1).getPEResult());
				mLCPENoticeSchema.setHospitCode(mLCPENoticeSet.get(1).getHospitCode());//2009-01-05 ln add 体检医院代码
				mLCPENoticeSchema.setPEAddress(mLCPENoticeSet.get(1).getPEAddress());
				mLCPENoticeSchema.setAgentName(mLCPENoticeSet.get(1).getAgentName());
				mLCPENoticeSchema.setREPEDate(mLCPENoticeSet.get(1).getREPEDate());
				
				
				logger.debug(mGlobalInput.ComCode);

				logger.debug("mNewPrtSeq=" + mNewPrtSeq);
				mLOPRTManagerSchema.setPrtSeq(mNewPrtSeq);
				mLOPRTManagerSchema.setOtherNo(mLCPENoticeSet.get(1).getContNo());

				mLOPRTManagerSchema.setStandbyFlag3(mLCContSchema.getGrpContNo()); // modify
																					// by
																					// zhangxing
				logger.debug("StandbyFlag3="
						+ mLOPRTManagerSchema.getStandbyFlag3());
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 新契约保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PE); // 体检
				mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
				mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
				mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

				mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				mLOPRTManagerSchema.setStateFlag("2");
				mLOPRTManagerSchema.setPatchFlag("0");
				mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

				mLOPRTManagerSchema.setForMakeDate(PubFun.getCurrentDate());

				mLOPRTManagerSchema.setStandbyFlag1(tCustomerno); // 被保险人编码
				mLOPRTManagerSchema.setOldPrtSeq(mNewPrtSeq);
				
				
				mLCPENoticeItemSchema.setProposalContNo(mLCContSchema.getProposalContNo());
				mLCPENoticeItemSchema.setContNo(mLCContSchema.getContNo());
				mLCPENoticeItemSchema.setPrtSeq(mNewPrtSeq);
				mLCPENoticeItemSchema.setPEItemCode("other"); // 核保规则编码
				mLCPENoticeItemSchema.setPEItemName("12"); // 核保出错信息
				mLCPENoticeItemSchema.setModifyDate(PubFun.getCurrentDate()); // 当前值
				mLCPENoticeItemSchema.setModifyTime(PubFun.getCurrentTime());
				
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;
		} finally {
		}
		return true;

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData1() {

		DisDesbBL tDisDesbBL = new DisDesbBL();

		logger.debug("--------DisDesb Start!---------");
		boolean tResult = tDisDesbBL.submitData(mInputData, mOperate);
		logger.debug("--------DisDesb End!---------");

		// 如果有需要处理的错误，则返回
		if (tDisDesbBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tDisDesbBL.mErrors);
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;
		mResult = tDisDesbBL.getResult();

		if (tResult) {
			mMap = (MMap) tDisDesbBL.getResult().getObjectByObjectName("MMap",
					0);
		} else {
			this.mErrors.copyAllErrors(tDisDesbBL.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		
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
