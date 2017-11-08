package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPIndUWMasterDB;
import com.sinosoft.lis.db.LPIndUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPIndUWMasterSchema;
import com.sinosoft.lis.schema.LPIndUWSubSchema;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPIndUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BQSpecBL {
private static Logger logger = Logger.getLogger(BQSpecBL.class);

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
	private String mEdorNo;
	private String mEdorAcceptNo;
	private String mEdorType;
	private String mPrtNo;
	private String mCustomerNo;
	private String mSpecReason;
	private String mRemark;
	private String mOperatetype;
	private String mProposalContNo;
	private String mSerialno;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	/** 特约表 */
	private LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();	

	/** 被保人核保主表 */
	private LPIndUWMasterSchema tLPIndUWMasterSchema = new LPIndUWMasterSchema();
	/** 被保人核保子表 */
	private LPIndUWSubSchema tLPIndUWSubSchema = new LPIndUWSubSchema();
	private LPContSchema tLPContSchema = new LPContSchema();	
	private LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();
	
	//private LBCSpecSet mLBCSpecSet = new LBCSpecSet();

	public BQSpecBL() {
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
		
		//查询合同信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		tLPContDB.setContNo(mContNo);
		if(!tLPContDB.getInfo())
		{
			CError.buildErr(this, "合同信息查询失败！");
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();
		
		if(!prepareIndUWMaster())
			return false;
		
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
		mLPCSpecSchema = (LPCSpecSchema) cInputData.getObjectByObjectName(
				"LPCSpecSchema", 0);

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
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		logger.debug("*****mEdorNo*****" + mEdorNo);
		if (mEdorNo == null || mEdorNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输数据EdorNo失败");
			return false;
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		logger.debug("*****mEdorType*****" + mEdorType);
		if (mEdorType == null || mEdorType.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输数据EdorType失败");
			return false;
		}
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		logger.debug("*****mEdorAcceptNo*****" + mEdorAcceptNo);
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输数据EdorAcceptNo失败");
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

		
//		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
//		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
//		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
//		tLPEdorItemDB.setEdorNo(mEdorNo);
//		tLPEdorItemDB.setContNo(mContNo);
//		tLPEdorItemSet = tLPEdorItemDB.query();
//		if(tLPEdorItemSet.size()<1){
//			CError.buildErr(this, "查询个险保全项目表出错！");
//			return false;
//		}
//		mEdorType  = tLPEdorItemSet.get(1).getEdorType();
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
		if(!prepareIndUWMaster())
			return false;		
		
        //准备核保主表数据
		String tSpecReason = mLPCSpecSchema.getSpecReason();	
		if (mOperatetype.equals("DELETE")) {	
				
		    LPCSpecDB tLPCSpecDB = new LPCSpecDB();
		    tLPCSpecDB.setEdorNo(mEdorNo);
		    tLPCSpecDB.setContNo(mContNo);
		    tLPCSpecDB.setCustomerNo(mCustomerNo);
		    tLPCSpecDB.setNeedPrint("Y");
		    LPCSpecSet tLPCSpecSet = new LPCSpecSet();
		    tLPCSpecSet = tLPCSpecDB.query();
		    if (tLPCSpecSet == null )
		    {
		    	CError.buildErr(this,"全部特约信息查询出错！");
				return false;
		    }
		    tLPIndUWMasterSchema.setSpecReason("删除一条特约"); // 特约原因
		    if(tLPCSpecSet.size()>1)
		    	tLPIndUWMasterSchema.setSpecFlag("1"); // 特约标识
		    else 
		    	tLPIndUWMasterSchema.setSpecFlag("0"); // 特约标识						
			
		}
		else {			
			if (tSpecReason != null && !tSpecReason.trim().equals("")) {
				tLPIndUWMasterSchema.setSpecReason(tSpecReason);
			} else {
				tLPIndUWMasterSchema.setSpecReason("");
			}

				tLPIndUWMasterSchema.setSpecFlag("1"); // 特约标识			
		}
		
		tLPIndUWMasterSchema.setOperator(mOperater);
		tLPIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		// 准备被保人核保子表数据
		Reflections mReflections = new Reflections();
		mReflections.transFields(tLPIndUWSubSchema, tLPIndUWMasterSchema);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select max(uwno) from lpinduwsub where edorno='"+"?mEdorNo?"+"' and contno='"+"?mContNo?"+"' and insuredno='"+"?mCustomerNo?"+"'";
		sqlbv.sql(sql);
		sqlbv.put("mEdorNo", mEdorNo);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("mCustomerNo", mCustomerNo);
		ExeSQL txExeSQL = new ExeSQL();
        int uwno = Integer.parseInt(txExeSQL.getOneValue(sqlbv));
        	
        tLPIndUWSubSchema.setUWNo(uwno+1);
		tLPIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		// 准备特约的数据
		if (mLPCSpecSchema != null) {
			//启用合同特约
			LPCSpecDB tLPCSpecDB = new LPCSpecDB();
			tLPCSpecDB.setSchema(this.mLPCSpecSchema);
			if(!this.mOperatetype.equals("INSERT")){
				if (mSerialno == null || mSerialno == "") {
					CError.buildErr(this,"前台传送Serialno出错");
					return false;
				}
				
				tLPCSpecDB.setContNo(this.mContNo);
				tLPCSpecDB.setEdorNo(mEdorNo);
				tLPCSpecDB.setEdorType(mEdorType);
				tLPCSpecDB.setSerialNo(this.mSerialno);
			    if (!tLPCSpecDB.getInfo()) {
					CError.buildErr(this, "查询特约出错！");
					return false;
				}
			    tLPCSpecSchema = tLPCSpecDB.getSchema();	    			    
			    
			}
			
			if (mOperatetype.equals("DELETE")) {
				
			} else if (mOperatetype.equals("UPDATE")) {
				if(!mLPCSpecSchema.getSpecContent().equals(""))
					tLPCSpecSchema.setSpecContent(mLPCSpecSchema.getSpecContent());//修改特约内容
				if(!mLPCSpecSchema.getNeedPrint().equals(""))
					tLPCSpecSchema.setNeedPrint(mLPCSpecSchema.getNeedPrint());//修改下发标记
				if(!mLPCSpecSchema.getSpecCode().equals(""))
					tLPCSpecSchema.setSpecCode(mLPCSpecSchema.getSpecCode());
				if(!mLPCSpecSchema.getSpecType().equals(""))
					tLPCSpecSchema.setSpecType(mLPCSpecSchema.getSpecType());	
//				if(!mLPCSpecSchema.getSpecReason().equals(""))
//					tLPCSpecSchema.setSpecReason(mLPCSpecSchema.getSpecReason());
				if(mLPCSpecSchema.getBackupType()!=null && !mLPCSpecSchema.getBackupType().equals(""))
					tLPCSpecSchema.setBackupType(mLPCSpecSchema.getBackupType());
				tLPCSpecSchema.setModifyDate(mCurrentDate);
				tLPCSpecSchema.setModifyTime(mCurrentTime);
			} else // INSERT
			{
				tLPCSpecSchema.setSerialNo(PubFun1.CreateMaxNo("SpecCode",
						PubFun.getNoLimit(mGlobalInput.ComCode)));
				tLPCSpecSchema.setGrpContNo(tLPContSchema.getGrpContNo());
				tLPCSpecSchema.setProposalContNo(tLPContSchema.getProposalContNo());
				tLPCSpecSchema.setContNo(mContNo);
				tLPCSpecSchema.setCustomerNo(mCustomerNo);
				tLPCSpecSchema.setBackupType(mLPCSpecSchema.getBackupType());
				tLPCSpecSchema.setSpecCode(mLPCSpecSchema.getSpecCode());
				tLPCSpecSchema.setSpecType(mLPCSpecSchema.getSpecType());
				tLPCSpecSchema.setSpecContent(mLPCSpecSchema.getSpecContent());
				tLPCSpecSchema.setSpecReason(mLPCSpecSchema.getSpecReason());
				tLPCSpecSchema.setNeedPrint(mLPCSpecSchema.getNeedPrint());
				tLPCSpecSchema.setOperator(mOperater);
				tLPCSpecSchema.setMakeDate(mCurrentDate);
				tLPCSpecSchema.setMakeTime(mCurrentTime);
				tLPCSpecSchema.setModifyDate(mCurrentDate);
				tLPCSpecSchema.setModifyTime(mCurrentTime);
				tLPCSpecSchema.setEdorType(mEdorType);
				tLPCSpecSchema.setEdorNo(mEdorNo);
			}
		}

		return true;
	}
	
	/**
	 * prepareIndUWMaster
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUWMaster() {
		LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
		tLPIndUWMasterDB.setEdorNo(mEdorNo);
		tLPIndUWMasterDB.setEdorType(mEdorType);
		tLPIndUWMasterDB.setContNo(mContNo);
		tLPIndUWMasterDB.setInsuredNo(mCustomerNo);
		if (tLPIndUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this,"查询被保人核保主表出错!");
			return false;			
		}
		else
		{
			tLPIndUWMasterSchema.setSchema(tLPIndUWMasterDB);
			int uwno = tLPIndUWMasterDB.getUWNo();
			tLPIndUWMasterSchema.setUWNo(uwno + 1);			
			tLPIndUWMasterSchema.setOperator(mOperater); // 操作员		
			tLPIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLPIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			
			LPIndUWSubDB tLPIndUWSubDB = new LPIndUWSubDB();
			tLPIndUWSubDB.setEdorNo(mEdorNo);
			tLPIndUWSubDB.setContNo(mContNo);
			tLPIndUWSubDB.setInsuredNo(mCustomerNo);
			LPIndUWSubSet tLPIndUWSubSet = new LPIndUWSubSet();
			tLPIndUWSubSet = tLPIndUWSubDB.query();
			if (tLPIndUWSubSet==null || tLPIndUWSubSet.size()<1) {
				// @@错误处理
				CError.buildErr(this,"查询保全被保人核保子表出错!");
				return false;
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
		if (tLPCSpecSchema != null) {
			map.put(tLPCSpecSchema, mOperatetype);
		}

		// 添加续保批单核保主表通知书打印管理表数据
		map.put(tLPIndUWMasterSchema, "UPDATE");
		map.put(tLPIndUWSubSchema, "INSERT");

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
