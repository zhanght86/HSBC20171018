package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LLUWSpecMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LLUWSpecMasterSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.lis.vschema.LLUWSpecMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLUWSpecBL {
private static Logger logger = Logger.getLogger(LLUWSpecBL.class);

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
	private ExeSQL mExeSql = new ExeSQL();
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
	private String mClmNo;
	private String mBatNo;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	/** 被保人核保主表 */
	private LCIndUWMasterSchema mLCIndUWMasterSchema = new LCIndUWMasterSchema();
	/** 被保人核保子表 */
	private LCIndUWSubSchema mLCIndUWSubSchema = new LCIndUWSubSchema();
	/** 特约表 */
	private LLUWSpecMasterSchema mLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
	private LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
	//private LBCSpecSet mLBCSpecSet = new LBCSpecSet();

	public LLUWSpecBL() {
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
		mLLUWSpecMasterSchema = (LLUWSpecMasterSchema) cInputData.getObjectByObjectName(
				"LLUWSpecMasterSchema", 0);
		mLCIndUWMasterSchema = (LCIndUWMasterSchema) cInputData
				.getObjectByObjectName("LCIndUWMasterSchema", 0);

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mOperatetype = (String) mTransferData.getValueByName("Operatetype");
		logger.debug("*****mOperatetype*****" + mOperatetype);

		mProposalContNo = (String) mTransferData.getValueByName("ProposalContNo");
		logger.debug("*****mProposalContNo*****" + mProposalContNo);

		mSerialno = (String) mTransferData.getValueByName("SerialNo");
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
			String tCurSql = "Select customerno from llcase where caseno= '?caseno?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tCurSql);
			sbv2.put("caseno", (String) mTransferData.getValueByName("ClmNo"));
			mCustomerNo = mExeSql.getOneValue(sbv2);
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
//			CError.buildErr(this,"前台传输数据CustomerNo失败");
//			return false;
		}
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败");
			return false;
		}
//		if (mLCIndUWMasterSchema == null) {
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
//			CError.buildErr(this,"前台传输全局公共数据失败");
//			return false;
//		}
		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据Operate失败");
			return false;
		}
		
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		if (mClmNo == null || mClmNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据ClmNo失败");
			return false;
		}
		
		mBatNo = (String) mTransferData.getValueByName("BatNo");
		if (mBatNo == null || mBatNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据BatNo失败");
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
//		LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
//		tLCIndUWMasterDB.setContNo(mContNo);
//		tLCIndUWMasterDB.setInsuredNo(mCustomerNo);
//		if (tLCIndUWMasterDB.getInfo() == false) {
//			// @@错误处理
//			CError.buildErr(this,"查询被保人核保主表出错!");
//			return false;
//		}
		
        //准备核保主表数据
//		mLCIndUWMasterSchema.setSchema(tLCIndUWMasterDB);
		String tSpecReason = mLLUWSpecMasterSchema.getSpecReason();	
		if (mOperatetype.equals("DELETE")) {	
			
//		    mLCIndUWMasterSchema.setSpecReason("");		
		    LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
		    tLLUWSpecMasterDB.setContNo(mContNo);
		    tLLUWSpecMasterDB.setCustomerNo(mCustomerNo);
		    tLLUWSpecMasterDB.setClmNo(mClmNo);
		    tLLUWSpecMasterDB.setBatNo(mBatNo);
		    tLLUWSpecMasterDB.setNeedPrint("Y");
		    LLUWSpecMasterSet tLLUWSpecMasterSet = new LLUWSpecMasterSet();
		    tLLUWSpecMasterSet = tLLUWSpecMasterDB.query();
		    if (tLLUWSpecMasterSet == null)
		    {
		    	CError.buildErr(this,"全部特约信息查询出错！");
				return false;
		    }
//		    mLCIndUWMasterSchema.setSpecReason("删除一条特约"); // 特约原因
//		    if(tLLUWSpecMasterSet.size()>1)
//		    	mLCIndUWMasterSchema.setSpecFlag("1"); // 特约标识
//		    else 
//		    	mLCIndUWMasterSchema.setSpecFlag("0"); // 特约标识						
			
		}
		else {			
//			if (tSpecReason != null && !tSpecReason.trim().equals("")) {
//				mLCIndUWMasterSchema.setSpecReason(tSpecReason);
//			} else {
//				mLCIndUWMasterSchema.setSpecReason("");
//			}
//
//				mLCIndUWMasterSchema.setSpecFlag("1"); // 特约标识			
		}
		
//		mLCIndUWMasterSchema.setOperator(mOperater);
//		mLCIndUWMasterSchema.setManageCom(mManageCom);
//		mLCIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
//		mLCIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		// 准备被保人核保子表数据
//		LCIndUWSubDB tLCIndUWSubDB = new LCIndUWSubDB();
//		String tSql = "select * from LCIndUWSub where contno='"+mContNo+"' and insuredno='"+mCustomerNo+"' order by uwno desc";
//		LCIndUWSubSet tLCIndUWSubSet = new LCIndUWSubSet();
//		tLCIndUWSubSet = tLCIndUWSubDB.executeQuery(tSql);
//		if(tLCIndUWSubSet==null)
//		{
//			CError.buildErr(this,"查询被保人核保子表出错!");
//			return false;
//		}
//		
//		mLCIndUWSubSchema = tLCIndUWSubSet.get(1);
//		mLCIndUWSubSchema.setProposalContNo(mLCIndUWMasterSchema.getProposalContNo());
//		mLCIndUWSubSchema.setUWNo(tLCIndUWSubSet.get(1).getUWNo() + 1);
//		mLCIndUWSubSchema.setSpecReason(mLCIndUWMasterSchema.getSpecReason());
//		mLCIndUWSubSchema.setSpecFlag(mLCIndUWMasterSchema.getSpecFlag());
//		mLCIndUWSubSchema.setOperator(mOperater); // 操作员	
//		mLCIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
//		mLCIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
//		mLCIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
//		mLCIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		// 准备特约的数据
		if (mLLUWSpecMasterSchema != null) {
			//启用合同特约
			LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
			tLLUWSpecMasterDB.setSchema(this.mLLUWSpecMasterSchema);
			if(!this.mOperatetype.equals("INSERT"))
			{
				if (mSerialno == null || mSerialno == "") {
					CError.buildErr(this,"前台传送Serialno出错");
					return false;
				}
				
				tLLUWSpecMasterDB.setProposalContNo(this.mProposalContNo);
				tLLUWSpecMasterDB.setSerialNo(this.mSerialno);
				tLLUWSpecMasterDB.setClmNo(mClmNo);
			    if (!tLLUWSpecMasterDB.getInfo()) {
					CError.buildErr(this, "查询特约出错！");
				}
			    tLLUWSpecMasterSchema = tLLUWSpecMasterDB.getSchema();	    			    
			    
			}
			
			if (mOperatetype.equals("DELETE")) {				
				
			} else if (mOperatetype.equals("UPDATE")) {
				if(!mLLUWSpecMasterSchema.getSpecContent().equals(""))
					tLLUWSpecMasterSchema.setSpecContent(mLLUWSpecMasterSchema.getSpecContent());//修改特约内容
				if(!mLLUWSpecMasterSchema.getNeedPrint().equals(""))
					tLLUWSpecMasterSchema.setNeedPrint(mLLUWSpecMasterSchema.getNeedPrint());//修改下发标记
				if(!mLLUWSpecMasterSchema.getSpecCode().equals(""))
					tLLUWSpecMasterSchema.setSpecCode(mLLUWSpecMasterSchema.getSpecCode());
				if(!mLLUWSpecMasterSchema.getSpecType().equals(""))
					tLLUWSpecMasterSchema.setSpecType(mLLUWSpecMasterSchema.getSpecType());	
				if(!mLLUWSpecMasterSchema.getSpecReason().equals(""))
					tLLUWSpecMasterSchema.setSpecReason(mLLUWSpecMasterSchema.getSpecReason());
				tLLUWSpecMasterSchema.setModifyDate(mCurrentDate);
				tLLUWSpecMasterSchema.setModifyTime(mCurrentTime);
			} else // INSERT
			{
				tLLUWSpecMasterSchema.setSerialNo(PubFun1.CreateMaxNo("SpecCode",
						PubFun.getNoLimit(mGlobalInput.ComCode)));
				tLLUWSpecMasterSchema.setGrpContNo("00000000000000000000");
				tLLUWSpecMasterSchema.setProposalContNo(mContNo);
				tLLUWSpecMasterSchema.setContNo(mContNo);
				tLLUWSpecMasterSchema.setClmNo(mClmNo);
				tLLUWSpecMasterSchema.setBatNo(mBatNo);
				tLLUWSpecMasterSchema.setCustomerNo(mCustomerNo);
				//mLCCSpecSchema.setPrtFlag("1");
				tLLUWSpecMasterSchema.setBackupType("");
				tLLUWSpecMasterSchema.setSpecCode(mLLUWSpecMasterSchema.getSpecCode());
				tLLUWSpecMasterSchema.setSpecType(mLLUWSpecMasterSchema.getSpecType());
				tLLUWSpecMasterSchema.setSpecContent(mLLUWSpecMasterSchema.getSpecContent());
				tLLUWSpecMasterSchema.setSpecReason(mLLUWSpecMasterSchema.getSpecReason());
				tLLUWSpecMasterSchema.setNeedPrint(mLLUWSpecMasterSchema.getNeedPrint());
				tLLUWSpecMasterSchema.setOperator(mOperater);
				tLLUWSpecMasterSchema.setMakeDate(mCurrentDate);
				tLLUWSpecMasterSchema.setMakeTime(mCurrentTime);
				tLLUWSpecMasterSchema.setModifyDate(mCurrentDate);
				tLLUWSpecMasterSchema.setModifyTime(mCurrentTime);
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
		if (tLLUWSpecMasterSchema != null) {
			map.put(tLLUWSpecMasterSchema, mOperatetype);
		}

		// 添加续保批单核保主表通知书打印管理表数据
//		map.put(mLCIndUWMasterSchema, "UPDATE");
//		map.put(mLCIndUWSubSchema, "INSERT");

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
