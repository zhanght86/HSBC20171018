package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.LJSGetEndorseTotalBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;
/**
 * 判断是否已经保存了客户意见，如果没有保存，不允许二核确认完毕
 * 如果已经保存，为下一节点准备数据
 * 
 * */
public class BQUWConfirmAfterInitService implements AfterInitService  {
private static Logger logger = Logger.getLogger(BQUWConfirmAfterInitService.class);

	
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	
	private String mEdorNo;
	private String mEdorType;
	private String mContNo;
	private String mOperater;
	private String mMissionID;
	private String mSubMissionID;
	/** 申请方式*/
	private String mApptype;
	/** 被保人姓名*/
	private String mAppntName;
	/** 申请人名称*/
	private String mEdorAppName;
	/** 保全受理号*/
	private String mEdorAcceptNo;
	
	// add by jiaqiangli 2009-07-16 保全财务金额汇总
	private boolean mSumFina = false;
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		//mResult.clear();
		return mResult;
	}

	public TransferData getReturnTransferData() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean submitData(VData cInputData, String cOperate) {
//		 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		//数据校验
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

	private boolean getInputData(VData cInputData, String cOperate) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}
		
		if (mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null || "".equals(mEdorNo.trim())) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据EdorNo失败!");
			return false;
		}
		
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType == null || "".equals(mEdorType.trim())) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}
		
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null || "".equals(mContNo.trim())) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ContNo失败!");
			return false;
		}
		
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || "".equals(mMissionID.trim())) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据MissionID失败!");
			return false;
		}
		
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null || "".equals(mSubMissionID.trim())) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据SubMissionID失败!");
			return false;
		}
		
		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否已经保存了客户意见
	 * 
	 * */
	private boolean checkData() {
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(mEdorNo);
		tLPCUWMasterDB.setEdorType(mEdorType);
		tLPCUWMasterDB.setContNo(mContNo);
		if(!tLPCUWMasterDB.getInfo()){
			CError.buildErr(this, "查询个人保全核保最近结果表失败");
			return false;
		}
		if(tLPCUWMasterDB.getCustomerIdea()==null||"".equals(tLPCUWMasterDB.getCustomerIdea())){
			CError.buildErr(this, "尚未保存客户意见，不允许二核结果确认完毕！");
			return false;
		}
		
		// add by jiaqiangli 2009-07-16 保全财务金额汇总
		// 客户意见只有同意的才允许汇总
		if ("0".equals(tLPCUWMasterDB.getCustomerIdea())) {
			mSumFina = true;
		}
		return true;
	}
	
	/**
	 * 为下一节点准备数据
	 * 
	 * */
	private boolean dealData(){
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		tLPEdorMainDB.setEdorNo(mEdorNo);
		tLPEdorMainDB.setContNo(mContNo);
		tLPEdorMainSet=tLPEdorMainDB.query();
		if(tLPEdorMainSet.size()<=0||tLPEdorMainSet.size()!=1){
			CError.buildErr(this, "查询个险保全批改表失败！");
			return false;
		}
		mEdorAppName = tLPEdorMainSet.get(1).getEdorAppName();
		mEdorAcceptNo = tLPEdorMainSet.get(1).getEdorAcceptNo();
		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setEdorNo(mEdorNo);
		tLPAppntDB.setEdorType(mEdorType);
		tLPAppntDB.setContNo(mContNo);
		//如果P表中没有再去查C表
		if(!tLPAppntDB.getInfo()){
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mContNo);
			if(!tLCAppntDB.getInfo()){
				CError.buildErr(this, "查询投保人信息失败！");
				return false;
			}
			mAppntName=tLCAppntDB.getAppntName();
		}else{
			mAppntName=tLPAppntDB.getAppntName();
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorAppDB.setOtherNo(mContNo);
		tLPEdorAppDB.setOtherNoType("3");
		if(!tLPEdorAppDB.getInfo()){
			CError.buildErr(this, "查询保全申请主表失败！");
			return false;
		}
		mApptype = tLPEdorAppDB.getAppType();
		
		//add by jiaqiangli 2009-07-16 保全财务金额汇总
		if (mSumFina == true) {
			LPEdorAppSchema tLPEdorAppSchema = tLPEdorAppDB.getSchema();
			VData tVData = new VData();
			tVData.add(tLPEdorAppSchema);
			tVData.add(mGlobalInput);
			LJSGetEndorseTotalBL tLJSGetEndorseTotalBL = new LJSGetEndorseTotalBL();
			if (!tLJSGetEndorseTotalBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
				mErrors.addOneError(new CError("生成财务应收、应付信息失败!"));
				return false;
			}
			if (tLJSGetEndorseTotalBL.getResult() == null) {
				mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
				mErrors.addOneError(new CError("获得财务应收、应付信息失败!"));
				return false;
			}
			mResult.clear();
			mResult = tLJSGetEndorseTotalBL.getResult();
		}
		//add by jiaqiangli 2009-07-16 保全财务金额汇总
		
		return true;
	}
	
	
	private boolean prepareTransferData(){
		mTransferData.setNameAndValue("EdorAcceptNo", mEdorAcceptNo);
		mTransferData.setNameAndValue("OtherNo", mContNo);
		mTransferData.setNameAndValue("OtherNoType", "3");
		mTransferData.setNameAndValue("EdorAppName", mEdorAppName);
		mTransferData.setNameAndValue("Apptype", mApptype);
		mTransferData.setNameAndValue("AppntName", mAppntName);
		return true;
	}
	
	
	private boolean prepareOutputData(){
		
		return true;
	}
}
