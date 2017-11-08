package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.db.RnewIndUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.schema.RnewIndUWSubSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.RnewIndUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class RnewUWSpecBL {
private static Logger logger = Logger.getLogger(RnewUWSpecBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private VData mIputData = new VData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mContNo;
	private String mPrtNo;
	private String mCustomerNo;
	private String mSpecReason;
	private String mRemark;
	private String mOperatetype;
	private String mProposalContNo;
	private String mSerialno;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	/** 被保人核保主表 */
	private RnewIndUWMasterSchema mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
	/** 被保人核保子表 */
	private RnewIndUWSubSchema mRnewIndUWSubSchema = new RnewIndUWSubSchema();
	/** 特约表 */
	private LCCSpecSchema mLCCSpecSchema = new LCCSpecSchema();
	private LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
	//private LBCSpecSet mLBCSpecSet = new LBCSpecSet();

	public RnewUWSpecBL() {
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

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError.buildErr(this, "数据提交失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保特约信息
		if (prepareSpec() == false) {
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
		// 校验保单信息
//		LCPolDB tLCPolDB = new LCPolDB();
//		logger.debug("mPolNo check " + mPolNo);
//		tLCPolDB.setPolNo(mPolNo);
//
//		mLCPolSchema.setSchema(tLCPolDB);
//		mPrtNo = mLCPolSchema.getPrtNo();
/*
		// // 处于未打印状态的核保通知书在打印队列中只能有一个
		// // 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UW); // 核保通知书
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		tLOPRTManagerDB.setStandbyFlag2(mLCPolSchema.getPrtNo());
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRnewUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRnewUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的核保通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}
*/
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
		//tongmeng 2008-10-08 modify
		//启用合同特约
		mLCCSpecSchema = (LCCSpecSchema) cInputData.getObjectByObjectName(
				"LCCSpecSchema", 0);
		mRnewIndUWMasterSchema = (RnewIndUWMasterSchema) cInputData
				.getObjectByObjectName("RnewIndUWMasterSchema", 0);

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mOperatetype = (String) mTransferData.getValueByName("Operatetype");
		logger.debug("*****mOperatetype*****" + mOperatetype);

		mProposalContNo = (String) mTransferData.getValueByName("ProposalContNo");
		logger.debug("*****mProposalContNo*****" + mProposalContNo);

		mSerialno = (String) mTransferData.getValueByName("Serialno");
		logger.debug("*****mSerialno*****" + mSerialno);
		
		mContNo = (String) mTransferData.getValueByName("ContNo");
		logger.debug("*****mContNo*****" + mContNo);
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输数据ContNo失败");
			return false;
		}
		
		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		logger.debug("*****mCustomerNo*****" + mCustomerNo);
		if (mCustomerNo == null || mCustomerNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输数据CustomerNo失败");
			return false;
		}
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败");
			return false;
		}
		if (mRnewIndUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败");
			return false;
		}
		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据Operate失败");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据ManageCom失败");
			return false;
		}

		mOperate = cOperate;

		return true;

	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareSpec() {

		// 准备续保核保主表信息
		//取合同下的第一被保人的主险编码
		
//		String tSQL = "select polno from lcpol where contno='"+this.mLCCSpecSchema.getContNo()+"' and insuredno='"+this.mLCCSpecSchema.getCustomerNo()+"' "
//		            + " and (risksequence is null or risksequence='1') and polno=mainpolno "; 
//		String tPolNo = "";
//		ExeSQL tExeSQL = new ExeSQL();
//		tPolNo = tExeSQL.getOneValue(tSQL);
//		if(tPolNo==null||tPolNo.equals(""))
//		{
//			CError.buildErr(this,"查询合同下的第一主险保单号失败!");
//			return false;
//		}
		RnewIndUWMasterDB tRnewIndUWMasterDB = new RnewIndUWMasterDB();
		tRnewIndUWMasterDB.setContNo(mContNo);
		tRnewIndUWMasterDB.setInsuredNo(mCustomerNo);
		if (tRnewIndUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this,"查询被保人核保主表出错!");
			return false;
		}
		
        //准备核保主表数据
		mRnewIndUWMasterSchema.setSchema(tRnewIndUWMasterDB);
		String tSpecReason = mLCCSpecSchema.getSpecReason();	
		if (mOperatetype.equals("DELETE")) {	
			
		    mRnewIndUWMasterSchema.setSpecReason("");		
		    LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		    tLCCSpecDB.setContNo(mContNo);
		    tLCCSpecDB.setCustomerNo(mCustomerNo);
		    tLCCSpecDB.setNeedPrint("Y");
		    LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		    tLCCSpecSet = tLCCSpecDB.query();
		    if (tLCCSpecSet == null)
		    {
		    	CError.buildErr(this,"全部特约信息查询出错！");
				return false;
		    }
		    mRnewIndUWMasterSchema.setSpecReason("删除一条特约"); // 特约原因
		    if(tLCCSpecSet.size()>1)
		    	mRnewIndUWMasterSchema.setSpecFlag("1"); // 特约标识
		    else 
		    	mRnewIndUWMasterSchema.setSpecFlag("0"); // 特约标识						
			
		}
		else {			
			if (tSpecReason != null && !tSpecReason.trim().equals("")) {
				mRnewIndUWMasterSchema.setSpecReason(tSpecReason);
			} else {
				mRnewIndUWMasterSchema.setSpecReason("");
			}

				mRnewIndUWMasterSchema.setSpecFlag("1"); // 特约标识			
		}
		
		mRnewIndUWMasterSchema.setOperator(mOperater);
		mRnewIndUWMasterSchema.setManageCom(mManageCom);
		mRnewIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mRnewIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		// 准备被保人核保子表数据
		RnewIndUWSubDB tRnewIndUWSubDB = new RnewIndUWSubDB();
		String tSql = "select * from RnewIndUWSub where contno='?mContNo?' and insuredno='?mCustomerNo?' order by uwno desc";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("mCustomerNo", mCustomerNo);
		RnewIndUWSubSet tRnewIndUWSubSet = new RnewIndUWSubSet();
		tRnewIndUWSubSet = tRnewIndUWSubDB.executeQuery(sqlbv);
		if(tRnewIndUWSubSet==null)
		{
			CError.buildErr(this,"查询被保人核保子表出错!");
			return false;
		}
		
		mRnewIndUWSubSchema = tRnewIndUWSubSet.get(1);
		mRnewIndUWSubSchema.setProposalContNo(mRnewIndUWMasterSchema.getProposalContNo());
		mRnewIndUWSubSchema.setUWNo(tRnewIndUWSubSet.get(1).getUWNo() + 1);
		mRnewIndUWSubSchema.setSpecReason(mRnewIndUWMasterSchema.getSpecReason());
		mRnewIndUWSubSchema.setSpecFlag(mRnewIndUWMasterSchema.getSpecFlag());
		mRnewIndUWSubSchema.setOperator(mOperater); // 操作员	
		mRnewIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		mRnewIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		mRnewIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		mRnewIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		// 准备特约的数据
		if (mLCCSpecSchema != null) {
			//启用合同特约
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			tLCCSpecDB.setSchema(this.mLCCSpecSchema);
			if(!this.mOperatetype.equals("INSERT"))
			{
				if (mSerialno == null || mSerialno == "") {
					CError.buildErr(this,"前台传送Serialno出错");
					return false;
				}
				
				tLCCSpecDB.setProposalContNo(this.mProposalContNo);
				tLCCSpecDB.setSerialNo(this.mSerialno);
			    if (!tLCCSpecDB.getInfo()) {
					CError.buildErr(this, "查询特约出错！");
				}
			    tLCCSpecSchema = tLCCSpecDB.getSchema();	    			    
			    
			}
			
			if (mOperatetype.equals("DELETE")) {				
				
			} else if (mOperatetype.equals("UPDATE")) {
				if(!mLCCSpecSchema.getSpecContent().equals(""))
					tLCCSpecSchema.setSpecContent(mLCCSpecSchema.getSpecContent());//修改特约内容
				if(!mLCCSpecSchema.getNeedPrint().equals(""))
					tLCCSpecSchema.setNeedPrint(mLCCSpecSchema.getNeedPrint());//修改下发标记
				if(!mLCCSpecSchema.getSpecCode().equals(""))
					tLCCSpecSchema.setSpecCode(mLCCSpecSchema.getSpecCode());
				if(!mLCCSpecSchema.getSpecType().equals(""))
					tLCCSpecSchema.setSpecType(mLCCSpecSchema.getSpecType());	
				if(!mLCCSpecSchema.getSpecReason().equals(""))
					tLCCSpecSchema.setSpecReason(mLCCSpecSchema.getSpecReason());
				tLCCSpecSchema.setModifyDate(mCurrentDate);
				tLCCSpecSchema.setModifyTime(mCurrentTime);
			} else // INSERT
			{
				tLCCSpecSchema.setSerialNo(PubFun1.CreateMaxNo("SpecCode",
						PubFun.getNoLimit(mGlobalInput.ComCode)));
				tLCCSpecSchema.setGrpContNo(mRnewIndUWMasterSchema.getGrpContNo());
				tLCCSpecSchema.setProposalContNo(mRnewIndUWMasterSchema.getProposalContNo());
				tLCCSpecSchema.setContNo(mContNo);
				tLCCSpecSchema.setCustomerNo(mCustomerNo);
				//mLCCSpecSchema.setPrtFlag("1");
				tLCCSpecSchema.setBackupType("");
				tLCCSpecSchema.setSpecCode(mLCCSpecSchema.getSpecCode());
				tLCCSpecSchema.setSpecType(mLCCSpecSchema.getSpecType());
				tLCCSpecSchema.setSpecContent(mLCCSpecSchema.getSpecContent());
				tLCCSpecSchema.setSpecReason(mLCCSpecSchema.getSpecReason());
				tLCCSpecSchema.setNeedPrint(mLCCSpecSchema.getNeedPrint());
				tLCCSpecSchema.setOperator(mOperater);
				tLCCSpecSchema.setMakeDate(mCurrentDate);
				tLCCSpecSchema.setMakeTime(mCurrentTime);
				tLCCSpecSchema.setModifyDate(mCurrentDate);
				tLCCSpecSchema.setModifyTime(mCurrentTime);
			}
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加本次续保特约数据
		if (tLCCSpecSchema != null) {
			map.put(tLCCSpecSchema, mOperatetype);
		}

		// 添加续保批单核保主表通知书打印管理表数据
		map.put(mRnewIndUWMasterSchema, "UPDATE");
		map.put(mRnewIndUWSubSchema, "INSERT");

		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
