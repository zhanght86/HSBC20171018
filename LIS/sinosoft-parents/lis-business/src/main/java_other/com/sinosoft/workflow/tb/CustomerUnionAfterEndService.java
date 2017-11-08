package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

public class CustomerUnionAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(CustomerUnionAfterEndService.class);

	public CErrors mErrors = new CErrors();
	
	private TransferData mTransferData = new TransferData();

	private String mAppntNo;
	
	private String mContNo;

	private String mInsuredNo;
	
	private String mPolNo;

	private VData mResult = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();
	
	// 判断问题件修改完毕是否该扭转到下一结点标记
	private String mOtherNoticeFlag = "";


	private String mRiskCode;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mCustomerType;
	/**被保人信息*/
	private LDPersonSchema iLDPersonSchema = new LDPersonSchema();
	/**投保人信息*/
	private LDPersonSchema aLDPersonSchema = new LDPersonSchema();
	
	private boolean isAutoUnion=false;//判断是否需要人工合并

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return null;
	}

	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData, cOperate))
			return false;
		
		if (!checkData())
			return false;
	
		logger.debug("Start  dealData...");
		// 进行业务处理
		if (!dealData())
			return false;
	
		logger.debug("dealData successful!");
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;
	
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mActivityID=cOperate;
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if(mTransferData==null){
			CError.buildErr(this, "外部传输数据出错！");
			return false;
		}
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if(mContNo==null||"".equals(mContNo)){
			CError.buildErr(this, "合同号码为空！");
			return false;
		}
		if(mMissionID==null||"".equals(mMissionID)){
			CError.buildErr(this, "传入MissionID失败！");
			return false;
		}
		if(mSubMissionID==null||"".equals(mSubMissionID)){
			CError.buildErr(this, "传入mSubMissionID失败！");
			return false;
		}
		if(mActivityID==null||"".equals(mActivityID)){
			CError.buildErr(this, "传入mActivityID失败！");
			return false;
		}
		
		//查询投被保人客户号
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
			return false;
		}
			
		mAppntNo = tLCContDB.getAppntNo();
		mInsuredNo = tLCContDB.getInsuredNo();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mContNo);
		tLCPolSet = tLCPolDB.query();
		if(tLCPolSet.size()>0){
			mPolNo = tLCPolSet.get(1).getPolNo();
		}else{
//			CError.buildErr(this, "查询险种信息失败！");
//			return false;
		}
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
	
		return true;
	}


	/** 为下一结点准备数据 */
	private boolean prepareTransferData() {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		tLCPolDB.getInfo();
		tLCPolSchema = tLCPolDB.getSchema();
		//tongmeng 2009-02-21 add
		//是否创建问题件修改岗工作流.
	
		this.mTransferData.setNameAndValue("OtherNoticeFlag", mOtherNoticeFlag);
		this.mTransferData.setNameAndValue("customerUnion", "0");
		return true;
	}
	
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();


		mResult.add(map);
		return true;
	}
	//tongmeng 2009-02-21 add
	//问题件修改岗工作流扭转的判断放在此处....
	/**
	 * 准备下一结点判断字段
	 * 
	 * @return
	 */
	
	public static void main(String args[]){
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
		//tGlobalInput.AgentCom = "86110000";
		tGlobalInput.ComCode = "86110000";
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";
		tTransferData.setNameAndValue("GlobalInput", "");
		tTransferData.setNameAndValue("AppntNo", "9000003634");
		tTransferData.setNameAndValue("InsuredNo", "9000003634");
		tTransferData.setNameAndValue("PolNo", "110410000000262");
		tTransferData.setNameAndValue("ContNo", "86112009020425");
		tTransferData.setNameAndValue("MissionID", "86512008111904");
		tTransferData.setNameAndValue("SubMissionID", "86512008111904");
		tTransferData.setNameAndValue("ActivityID", "86512008111904");
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		
		AutoCustomerUnionAfterEndService auto = new AutoCustomerUnionAfterEndService();
		auto.submitData(tVData, "12123");
		VData ttVData = new VData();
		ttVData=auto.getResult();
		TransferData ttTransferData = new TransferData();
		ttTransferData = (TransferData) ttVData.getObjectByObjectName("TransferData", 0);
		String customerUnion = (String) ttTransferData.getValueByName("customerUnion");
		logger.debug("customerUnion:"+customerUnion);
		/*
		String tIDNo = "tong123meng tese 21 3";
		logger.debug(tIDNo.replaceAll("[^0-9]", ""));
		*/
	}
}
