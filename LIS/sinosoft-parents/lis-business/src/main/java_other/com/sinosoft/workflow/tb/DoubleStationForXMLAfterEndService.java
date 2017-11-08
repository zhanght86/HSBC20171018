package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.LBPOAppntDB;
import com.sinosoft.lis.db.LBPOBnfDB;
import com.sinosoft.lis.db.LBPOContDB;
import com.sinosoft.lis.db.LBPOCustomerImpartDB;
import com.sinosoft.lis.db.LBPODataDictionaryDB;
import com.sinosoft.lis.db.LBPOInsuredDB;
import com.sinosoft.lis.db.LBPOPerInvestPlanDB;
import com.sinosoft.lis.db.LBPOPolDB;
import com.sinosoft.lis.db.LDImpartDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.ElementLis;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.BPOPolDataSchema;
import com.sinosoft.lis.schema.LBPOAppntSchema;
import com.sinosoft.lis.schema.LBPOBnfSchema;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOCustomerImpartSchema;
import com.sinosoft.lis.schema.LBPODataDictionarySchema;
import com.sinosoft.lis.schema.LBPOInsuredSchema;
import com.sinosoft.lis.schema.LBPOPerInvestPlanSchema;
import com.sinosoft.lis.schema.LBPOPolSchema;
import com.sinosoft.lis.schema.LDImpartSchema;
import com.sinosoft.lis.tb.DSPubCheckCol;
import com.sinosoft.lis.tb.WBChangeRiskField;
import com.sinosoft.lis.vschema.LBPOAppntSet;
import com.sinosoft.lis.vschema.LBPOBnfSet;
import com.sinosoft.lis.vschema.LBPOContSet;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.lis.vschema.LBPODataDictionarySet;
import com.sinosoft.lis.vschema.LBPOInsuredSet;
import com.sinosoft.lis.vschema.LBPOPerInvestPlanSet;
import com.sinosoft.lis.vschema.LBPOPolSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;
/**
 * 功能：按照格式生成XML文件并以BLOB的形式插入到数据库中
 * 		XML生成成功则工作流流转到0000001403内部外包待导入状态，否则返回错误信息
 * @param 
 *            
 * @return 
 */
public class DoubleStationForXMLAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(DoubleStationForXMLAfterEndService.class);

	public CErrors mErrors = new CErrors();

	private TransferData mTransferData = new TransferData();
	private TransferData tTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private DSPubCheckCol mDSPubCheckCol = new DSPubCheckCol();
	private BPOPolDataSchema mBPOPolDataSchema = new BPOPolDataSchema();
	private MMap map = new MMap();
	private VData mVData = new VData();

	private String mContNo = "";
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private String mOperater = "";
	private String mManageCom = "";
	private String mSucceFlag = "0";
	private String mInputNo = "";
	private String tReleaseFlag = "";//附加豁免险标记 0为未勾选 1为勾选，需新生成一条险种信息
	private String mInputTime ="";
	private boolean mIsApprove=true;

	
	COracleBlob tCOracleBlob = new COracleBlob();
	CMySQLBlob tCMySQLBlob = new CMySQLBlob();
	

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public VData getResult() {
		
		return mVData;
	}

	public TransferData getReturnTransferData() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 功能：按照格式生成XML文件并以BLOB的形式插入到数据库中
	 * 
	 * @param 
	 *            
	 * @return 
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData, cOperate))
			return false;

		//
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");
		if(mInputTime.equals("2")||mInputTime.equals("3")){
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
		}else{
			return true;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mOperater = (String) mTransferData.getValueByName("Operator");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mInputTime = (String) mTransferData.getValueByName("InputTime");
		return true;
	}
	
	/**
	 *  校验数据是否合法、是否有空记录，如果有空记录则remove掉
	 * @param 
	 *            
	 * @return 
	 * */
	private boolean checkData() {

		return true;
	}

	private boolean dealData() {
		try {
			//Connection conn = DBConnPool.getConnection();
			//conn.setAutoCommit(false);
			//生成XML文件
			Document ttdoc = this.vDataToXML(mContNo);
			if(ttdoc==null){
				CError.buildErr(this, "生成XML文件时出错！");
				return false;
			}
			String tLimit = PubFun.getNoLimit(mManageCom);
			String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			mBPOPolDataSchema.setBussNo(mContNo);
			mBPOPolDataSchema.setBussNoType("JM");
			mBPOPolDataSchema.setSerialNo(serNo);
			mBPOPolDataSchema.setOperator(mOperater);
			mBPOPolDataSchema.setMakeDate(theCurrentDate);
			mBPOPolDataSchema.setMakeTime(theCurrentTime);
			InputStream ins;
			XMLOutputter outputter = new XMLOutputter("  ", true, "GBK");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			outputter.output(ttdoc, baos);
			baos.close();
			ins = new ByteArrayInputStream(baos.toByteArray());

			mBPOPolDataSchema.setPolData(ins);
			map.put(mBPOPolDataSchema, "BLOBINSERT");
			// outputDocumentToFile("D:/test", ttdoc);
//			if (!InsertBlob(ttdoc, conn)) {
//				CError tError = new CError();
//	            tError.moduleName = "DoubleStationForXMLAfterEndService";
//	            tError.functionName = "dealData";
//	            tError.errorMessage = "生成MXL文件时出错!";
//				return false;
//			} else {
//				conn.commit();
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	/**
	 * 生成XML文件
	 * 
	 * */
	public Document vDataToXML(String tContNo) {
//		mInputTime="3";
		boolean isOnePerson = false;
		String tSql="select * from lbpodatadictionary where checktype='02'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		LBPODataDictionaryDB tLBPODataDictionaryDB = new LBPODataDictionaryDB();
		LBPODataDictionarySet tLBPODataDictionarySet = new LBPODataDictionarySet();
		TransferData ttTransferData = new TransferData();
		tLBPODataDictionarySet=tLBPODataDictionaryDB.executeQuery(sqlbv);
		ttTransferData.setNameAndValue(tLBPODataDictionarySet.get(1).getContrasCol(), tLBPODataDictionarySet.get(1).getErrorCode());
		mContNo = tContNo;
		String ContType=mContNo.substring(0, 4);
		Document doc = null;
		ElementLis OnePolData = new ElementLis("OnePolData");
		doc = new Document(OnePolData);
		/** 
		 * BaseInfo 节点
		 * */
		//合同信息
		ElementLis BaseInfo = new ElementLis("BaseInfo");
		OnePolData.addContent(BaseInfo);
		ElementLis SuccFlag = new ElementLis("SuccFlag");
		ElementLis Errors = new ElementLis("Errors");
		LBPOContDB ttLBPOContDB = new LBPOContDB();
		LBPOContSchema ttLBPOContSchema = new LBPOContSchema();
		LBPOContSet tLBPOContSet = new LBPOContSet();
		ttLBPOContDB.setContNo(mContNo);
		ttLBPOContDB.setInputNo(mInputTime);
		tLBPOContSet = ttLBPOContDB.query();
		ttLBPOContSchema = tLBPOContSet.get(1);
		tReleaseFlag=ttLBPOContSchema.getReleaseFlag();
		tTransferData=mDSPubCheckCol.verifyValue(ttLBPOContSchema, "02");
		Vector tV = tTransferData.getValueNames();
		for(int i=0;i<tV.size();i++){
			mSucceFlag="1";
			ElementLis Error = new ElementLis("Error");
			ElementLis ErrType = new ElementLis("ErrType");
			ElementLis ErrTag = new ElementLis("ErrTag");
			ElementLis ErrCode  = new ElementLis("ErrCode");
			ElementLis ErrMessage  = new ElementLis("ErrMessage");
			ErrType.setText("1");
			ErrTag.setText((String)tV.get(i));
			ErrCode.setText((String)ttTransferData.getValueByName(tV.get(i)));
			ErrMessage.setText((String)tTransferData.getValueByName((String)tV.get(i)));
			Errors.addContent(Error);
			Error.addContent(ErrType);
			Error.addContent(ErrTag);
			Error.addContent(ErrCode);
			Error.addContent(ErrMessage);
		}
//			logger.debug((String)tTransferData.getValueByName(tV.get(i)));
		/*
		LBPOPolDB tLBPOPolDB = new LBPOPolDB();
		LBPOPolSchema ttLBPOPolSchema = new LBPOPolSchema();
		LBPOPolSet tLBPOPolSet = new LBPOPolSet();
		tLBPOPolDB.setContNo(mContNo);
		tLBPOPolDB.setInputNo(mInputTime);
		tLBPOPolSet = tLBPOPolDB.query();
		if(tLBPOPolSet.size()<1){
			CError.buildErr(this, "传入的数据有误，tLBPOPolSet.size为0");
			return null;
		}
		ttLBPOPolSchema = tLBPOPolSet.get(1);
		tTransferData=mDSPubCheckCol.verifyValue(ttLBPOPolSchema, "02");
		Vector tV2 = tTransferData.getValueNames();
		for(int i=0;i<tV2.size();i++){
			
			mSucceFlag="1";
			ElementLis Error = new ElementLis("Error");
			ElementLis ErrType = new ElementLis("ErrType");
			ElementLis ErrTag = new ElementLis("ErrTag");
			ElementLis ErrCode  = new ElementLis("ErrCode");
			ElementLis ErrMessage  = new ElementLis("ErrMessage");
			ErrType.setText("1");
			ErrTag.setText((String)tV2.get(i));
			ErrCode.setText((String)ttTransferData.getValueByName(tV2.get(i)));
			ErrMessage.setText((String)tTransferData.getValueByName((String)tV2.get(i)));
			Errors.addContent(Error);
			Error.addContent(ErrType);
			Error.addContent(ErrTag);
			Error.addContent(ErrCode);
			Error.addContent(ErrMessage);
		}*/
		//投保人校验
		LBPOAppntDB tLBPOAppntDB = new LBPOAppntDB();
		LBPOAppntSchema ttLBPOAppntSchema = new LBPOAppntSchema();
		LBPOAppntSet tLBPOAppntSet = new LBPOAppntSet();
		tLBPOAppntDB.setContNo(mContNo);
		tLBPOAppntDB.setInputNo(mInputTime);
		tLBPOAppntSet = tLBPOAppntDB.query();
		ttLBPOAppntSchema = tLBPOAppntSet.get(1);
		tTransferData=mDSPubCheckCol.verifyValue(ttLBPOAppntSchema, "02");
		Vector tV3 = tTransferData.getValueNames();
		for(int i=0;i<tV3.size();i++){
			mSucceFlag="1";
			ElementLis Error = new ElementLis("Error");
			ElementLis ErrType = new ElementLis("ErrType");
			ElementLis ErrTag = new ElementLis("ErrTag");
			ElementLis ErrCode  = new ElementLis("ErrCode");
			ElementLis ErrMessage  = new ElementLis("ErrMessage");
			ErrType.setText("1");
			ErrTag.setText((String)tV3.get(i));
			ErrCode.setText((String)ttTransferData.getValueByName(tV3.get(i)));
			ErrMessage.setText((String)tTransferData.getValueByName((String)tV3.get(i)));
			Errors.addContent(Error);
			Error.addContent(ErrType);
			Error.addContent(ErrTag);
			Error.addContent(ErrCode);
			Error.addContent(ErrMessage);
		}
		//校验投保人签名
		/*if(ttLBPOContSchema.getAppSignName()==null
				||"".equals(ttLBPOContSchema.getAppSignName())
				||ttLBPOAppntSchema.getAppntName()==null
				||"".equals(ttLBPOAppntSchema.getAppntName())
				||(!ttLBPOAppntSchema.getAppntName().equals(ttLBPOContSchema.getAppSignName()))){
			ElementLis Error = new ElementLis("Error");
			ElementLis ErrType = new ElementLis("ErrType");
			ElementLis ErrTag = new ElementLis("ErrTag");
			ElementLis ErrCode  = new ElementLis("ErrCode");
			ElementLis ErrMessage  = new ElementLis("ErrMessage");
			ErrType.setText("1");
			ErrTag.setText("");
			ErrMessage.setText("投保人签名(为空或与投保人姓名不符)");
			Errors.addContent(Error);
			Error.addContent(ErrType);
			Error.addContent(ErrTag);
			Error.addContent(ErrCode);
			Error.addContent(ErrMessage);
		}*/
		

		LBPOInsuredDB tLBPOInsuredDB = new LBPOInsuredDB();
//		LBPOInsuredSchema ttLBPOInsuredSchema = new LBPOInsuredSchema();
		LBPOInsuredSet tLBPOInsuredSet = new LBPOInsuredSet();
		tLBPOInsuredDB.setContNo(mContNo);
		tLBPOInsuredDB.setInputNo(mInputTime);
		tLBPOInsuredDB.setAppntNo(ttLBPOAppntSchema.getAppntNo());
		tLBPOInsuredSet = tLBPOInsuredDB.query();
		//去掉所有必要信息都为空的schema
		int insuredSize=tLBPOInsuredSet.size();
		for(int i=1;i<=insuredSize;i++){
			LBPOInsuredSchema ttLBPOInsuredSchema = new LBPOInsuredSchema();
			ttLBPOInsuredSchema = tLBPOInsuredSet.get(i);
			if(mDSPubCheckCol.ifRemove(ttLBPOInsuredSchema, "03")){
				tLBPOInsuredSet.remove(ttLBPOInsuredSchema);
				insuredSize--;
				i--;
			}
		}
		boolean isOneInsured = true;
		if(tLBPOInsuredSet.size()>1){
			isOneInsured = false;
		}
		///////////////////////////////////////////////////////////////////////
		// LBPOCont 节点
		//合同部分信息
		ElementLis LCPol = new ElementLis("LCPol");
		OnePolData.addContent(LCPol);
		ElementLis PrtNo = new ElementLis("PrtNo");
		PrtNo.setText(ttLBPOContSchema.getPrtNo());
		ElementLis ManageCom = new ElementLis("ManageCom");
		
		//tongmeng 2009-02-08
		//
		String tSQL = "select managecom from es_doc_main where "
			        + " doccode='"+"?doccode?"+"' and busstype='TB' and subtype='UA001' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("doccode", ttLBPOContSchema.getContNo());
		
		ExeSQL tManageExeSQL = new ExeSQL();
		ManageCom.setText(tManageExeSQL.getOneValue(sqlbv1));
		ElementLis SaleChnl = new ElementLis("SaleChnl");
		SaleChnl.setText(ttLBPOContSchema.getSaleChnl());
		ElementLis AgentCode = new ElementLis("AgentCode");
		AgentCode.setText(ttLBPOContSchema.getAgentCode());
		ElementLis AgentName = new ElementLis("AgentName");
		//tongmeng 2009-02-12
		AgentName.setText(ttLBPOContSchema.getHandler());
		ElementLis AgentGroup = new ElementLis("AgentGroup");
		AgentGroup.setText(ttLBPOContSchema.getAgentGroup());
		ElementLis AgentCom = new ElementLis("AgentCom");
		AgentCom.setText(ttLBPOContSchema.getAgentCom());
		ElementLis Remark = new ElementLis("Remark");
		//Remark.setText(ttLBPOContSchema.getRemark());
		//tongmeng 2009-02-12 modify
		Remark.setText(ttLBPOContSchema.getImpartRemark());
		ElementLis PolApplyDate = new ElementLis("PolApplyDate");
		PolApplyDate.setText(ttLBPOContSchema.getPolApplyDate());
		ElementLis GetPolMode = new ElementLis("GetPolMode");
		GetPolMode.setText(ttLBPOContSchema.getGetPolMode());
		ElementLis BankCode = new ElementLis("BankCode");
		String tBankCode =ttLBPOContSchema.getBankCode();
		BankCode.setText(ttLBPOContSchema.getBankCode());
		ElementLis AccName = new ElementLis("AccName");
		ElementLis BankAccNo = new ElementLis("BankAccNo");
		//09-09-11  使用最新新保险法需求过滤银行账号
		String tBankAccNo =PubFun.getBankAccNo(tBankCode, ttLBPOContSchema.getBankAccNo());
		BankAccNo.setText(tBankAccNo);
		String tAccName =ttLBPOContSchema.getAccName();
		if(tAccName==null||"null".equals(tAccName)||"".equals(tAccName)){
			  if((tBankCode==null||"null".equals(tBankCode)||tBankCode.equals(""))
					  &&(tBankAccNo==null||"null".equals(tBankAccNo)||tBankAccNo.equals(""))){
			  }else{
				  tAccName=ttLBPOAppntSchema.getAppntName();
			  }
		  }
		AccName.setText(tAccName);
		ElementLis PayMode = new ElementLis("PayMode");
		ElementLis PayLocation = new ElementLis("PayLocation");
		
		ElementLis PayIntv = new ElementLis("PayIntv");
		String tPayIntv=ttLBPOContSchema.getPayIntv();
		if(tPayIntv==null||tPayIntv.equals("")||(!tPayIntv.equals("")&&tPayIntv.equals("null"))){
			tPayIntv ="99";
		}
		PayIntv.setText(tPayIntv);
		String tPayLocation =ttLBPOContSchema.getPayLocation();
		
		if(!mContNo.substring(0,4).equals("8611")&&!mContNo.substring(0,4).equals("8621"))
		{
		if(ttLBPOContSchema.getBankCode()!=null&&!ttLBPOContSchema.getBankCode().equals("")
				&&ttLBPOContSchema.getAccName()!=null&&!ttLBPOContSchema.getAccName().equals("")
				&&ttLBPOContSchema.getBankAccNo()!=null&&!ttLBPOContSchema.getBankAccNo().equals(""))
			
		{
			//PayMode.setText("8");
			PayLocation.setText("8");
		}
		else if((ttLBPOContSchema.getBankCode()==null||ttLBPOContSchema.getBankCode().equals(""))
				&&(ttLBPOContSchema.getAccName()==null||ttLBPOContSchema.getAccName().equals(""))
				&&(ttLBPOContSchema.getBankAccNo()==null||ttLBPOContSchema.getBankAccNo().equals(""))
				)
		{
			//PayMode.setText("5");
			PayLocation.setText("5");
		}
		else
		{
			//PayMode.setText("8");
			PayLocation.setText("8");
		}
		}
		else
		{
			if(tPayLocation==null||"".equals(tPayLocation)||"null".equals(tPayLocation)){
				tPayLocation="2";
			}
			PayLocation.setText(tPayLocation);
		}
		//如录入为“空”或判断为多选时，转换为代码“2-人工收取”导入系统
		String tPayMode =ttLBPOContSchema.getPayMode();
		if(tPayMode==null||"".equals(tPayMode)){
			tPayMode="2";
		}
		PayMode.setText(tPayMode);
		//ayLocation.setText(ttLBPOContSchema.getPayLocation());
		
		
		ElementLis OutPayFlag = new ElementLis("OutPayFlag");
		String tOutPayFlag = ttLBPOContSchema.getOutPayFlag();
		if(tOutPayFlag==null||tOutPayFlag.equals("")||(!tOutPayFlag.equals("")&&tOutPayFlag.equals("null"))){
			tOutPayFlag ="1";
		}else{
			if("0".equals(tPayIntv)&&"2".equals(tOutPayFlag)){
				tOutPayFlag = "1";
			}
		}
		OutPayFlag.setText(tOutPayFlag);
		ElementLis FloatRate = new ElementLis("FloatRate");
		//FloatRate.setText(String.valueOf(ttLBPOPolSchema.getFloatRate()));
		FloatRate.setText(String.valueOf(1));
		ElementLis AutoPayFlag = new ElementLis("AutoPayFlag");
		String tAutoPayFlag = ttLBPOContSchema.getAutoPayFlag();
		if(tAutoPayFlag==null||tAutoPayFlag.equals("")||(!tAutoPayFlag.equals("")&&tAutoPayFlag.equals("null"))){
			//PayIntv 是否为趸交 只会有无问题件
				tAutoPayFlag = "0";
		}else if("1".equals(tAutoPayFlag)){
			if("0".equals(tPayIntv)){
				tAutoPayFlag = "0";
			}
		}
		AutoPayFlag.setText(tAutoPayFlag);
		ElementLis RnewFlag = new ElementLis("RnewFlag");
		String tRnewFlag = ttLBPOContSchema.getRnewFlag();
		if(tRnewFlag==null||tRnewFlag.equals("")||(!tRnewFlag.equals("")&&tRnewFlag.equals("null"))){
			//PayIntv 是否为趸交 只会有无问题件
				if("0".equals(tPayIntv)){
					tRnewFlag="1";
				}
		}
		RnewFlag.setText(tRnewFlag);
		ElementLis Spec = new ElementLis("Spec");
		Spec.setText("");
		ElementLis SignName = new ElementLis("SignName");
		SignName.setText(ttLBPOContSchema.getFirstTrialOperator());
		LCPol.addContent(PrtNo);
		LCPol.addContent(ManageCom);
		LCPol.addContent(SaleChnl);
		LCPol.addContent(AgentCode);
		LCPol.addContent(AgentName);
		LCPol.addContent(AgentGroup);
		LCPol.addContent(AgentCom);
		LCPol.addContent(Remark);
		LCPol.addContent(PolApplyDate);
		LCPol.addContent(GetPolMode);
		LCPol.addContent(PayMode);
		LCPol.addContent(PayLocation);
		LCPol.addContent(BankCode);
		LCPol.addContent(AccName);
		LCPol.addContent(BankAccNo);
		LCPol.addContent(OutPayFlag);
		LCPol.addContent(FloatRate);
		LCPol.addContent(PayIntv);
		LCPol.addContent(AutoPayFlag);
		LCPol.addContent(RnewFlag);
		LCPol.addContent(Spec);
		LCPol.addContent(SignName);
/////////////////////////////////////////////////////////////////////
		// LBPOAppnt 节点
		//投保人节点
		ElementLis LCAppnt = new ElementLis("LCAppnt");
		OnePolData.addContent(LCAppnt);
		ElementLis AppntName = new ElementLis("AppntName");
		AppntName.setText(ttLBPOAppntSchema.getAppntName());
		ElementLis AppntSex = new ElementLis("AppntSex");
		String tAppntSex =ttLBPOAppntSchema.getAppntSex();
		//AppntSex.setText(ttLBPOAppntSchema.getAppntSex());
		ElementLis AppntBirthday = new ElementLis("AppntBirthday");
		String tAppntBirthday = ttLBPOAppntSchema.getAppntBirthday();
		//AppntBirthday.setText(ttLBPOAppntSchema.getAppntBirthday());
		ElementLis AppntRelationToInsured = new ElementLis(
				"AppntRelationToInsured");
		AppntRelationToInsured
				.setText(ttLBPOAppntSchema.getRelationToInsured());
		//判断投被保人是否为同一人 根据投保人的“与被保人关系”去判断
		if("00".equals(ttLBPOAppntSchema.getRelationToInsured())){
			isOnePerson = true;
		}
		ElementLis AppntIDType = new ElementLis("AppntIDType");
		String tAppntIDType = ttLBPOAppntSchema.getIDType();
		ElementLis AppntIDNo = new ElementLis("AppntIDNo");
		String tAppntIDNo = ttLBPOAppntSchema.getIDNo();
		//String ttAppntIDType =tAppntIDType;
		if("出生日期".equals(tAppntIDType)||(tAppntBirthday!=null&&tAppntBirthday.equals(tAppntIDType))
				||"无证件".equals(tAppntIDType)){
			tAppntIDType ="9";
		}
		if(tAppntIDType==null||"".equals(tAppntIDType)||"null".equals(tAppntIDType)){
			if(tAppntIDNo!=null&&(tAppntIDNo.length()==15||tAppntIDNo.length()==18)){
				tAppntIDType="0";
			}else{
				tAppntIDType="8";
			}
		}
		AppntIDType.setText(tAppntIDType);
//		通过身份证号计算性别
		if(tAppntSex==null||"".equals(tAppntSex)||"null".equals(tAppntSex)){
			if("0".equals(tAppntIDType)){
				tAppntSex=PubFun.getSexFromId(tAppntIDNo);
			}
			if(!"0".equals(tAppntSex)&&!"1".equals(tAppntSex)){
				tAppntSex ="2";
			}
		}
		AppntSex.setText(tAppntSex);
//		当证件类型为9-无证件时，将IDNo 赋值为出生日期 YYYY-MM-DD(前提是日期格式要合法)
		  if("9".equals(tAppntIDType)){
			  Date tDate = null;
			SimpleDateFormat df;
			  try {
					if (tAppntBirthday.indexOf("-") != -1) {
						df = new SimpleDateFormat("yyyy-M-d");
						tDate = df.parse(tAppntBirthday);
						String dateStr2=df.format(tDate);
						if(dateStr2.equals(tAppntBirthday)){
							//日期合法
						}
					} else {
						df = new SimpleDateFormat("yyyyMMdd");
						tDate = df.parse(tAppntBirthday);
                            //日期合法
					}
					ExeSQL tExeSQL = new ExeSQL();
					String tBirthSQL = "select to_char(to_date('"+"?date?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual";
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(tBirthSQL);
					sqlbv3.put("date", tAppntBirthday);
					String tBirth = tExeSQL.getOneValue(sqlbv3);
					tAppntIDNo=tBirth;
				} catch (Exception e) {
					// @@错误处理
					//tResult = "不为日期类型";
				}
		  }
		  if("0".equals(tAppntIDType)){
			  //通过证件类型计算出生日期
			  if(tAppntBirthday==null||"".equals(tAppntBirthday)||"null".equals(tAppntBirthday)){
				  tAppntBirthday =PubFun.getBirthdayFromId(tAppntIDNo);
			  }
		  }
		  //如录入日期格式不是“YYYY-MM-DD”时 补齐日期
		  if(tAppntBirthday!=null){
		  String Date[]=null;
		  Date =tAppntBirthday.split("-");
		  if(Date.length==2){
			  //logger.debug("dfghjkl");
			  tAppntBirthday=String.valueOf(tAppntBirthday+"-01");
			  //tAppntBirthday=tAppntBirthday+"-01";
		  }
	     }
		AppntBirthday.setText(tAppntBirthday);
		AppntIDNo.setText(tAppntIDNo);
		ElementLis AppntNativePlace = new ElementLis("AppntNativePlace");
		AppntNativePlace.setText(ttLBPOAppntSchema.getNativePlace());
		ElementLis AppntRgtAddress = new ElementLis("AppntRgtAddress");
		AppntRgtAddress.setText(ttLBPOAppntSchema.getRgtAddress());
		ElementLis AppntMarriage = new ElementLis("AppntMarriage");
		AppntMarriage.setText(ttLBPOAppntSchema.getMarriage());
		ElementLis AppntPostalAddress = new ElementLis("AppntPostalAddress");
		AppntPostalAddress.setText(ttLBPOAppntSchema.getPostalAddress());
		ElementLis AppntZipCode = new ElementLis("AppntZipCode");
		AppntZipCode.setText(ttLBPOAppntSchema.getZipCode());
		ElementLis AppntHomeAddress = new ElementLis("AppntHomeAddress");
		AppntHomeAddress.setText(ttLBPOAppntSchema.getHomeAddress());
		ElementLis AppntHomeZipCode = new ElementLis("AppntHomeZipCode");
		AppntHomeZipCode.setText(ttLBPOAppntSchema.getHomeZipCode());
		ElementLis AppntPhone = new ElementLis("AppntPhone");
		String tFirstChoosePhone =ttLBPOAppntSchema.getMobile();
		ElementLis AppntIDEndDate = new ElementLis("AppntIDEndDate");
		String tAppntIDEndDate =ttLBPOAppntSchema.getIDExpDate();
		if(tAppntIDEndDate!=null){
			String Date[]=null;
			Date =tAppntIDEndDate.split("-");
			if(Date.length==2){
				//logger.debug("dfghjkl");
				tAppntIDEndDate=String.valueOf(tAppntIDEndDate+"-01");
				//tAppntBirthday=tAppntBirthday+"-01";
			}
		}
		AppntIDEndDate.setText(tAppntIDEndDate);
		ElementLis AppntSocialInsuFlag = new ElementLis("AppntSocialInsuFlag");
		AppntSocialInsuFlag.setText(ttLBPOAppntSchema.getSocialInsuFlag());
		//首选电话转换
		String tFormatAF="";
		if(tFirstChoosePhone!=null){
			if(tFirstChoosePhone.indexOf("转")!=-1){
				tFirstChoosePhone=StrTool.replace(tFirstChoosePhone, "转", "-");
				String tFirstPhone[]=tFirstChoosePhone.split("-");
				tFormatAF =tFirstPhone[0].replaceAll("[^0-9]", "")+"-"+tFirstPhone[1].replaceAll("[^0-9]", "");
			}else{
				tFormatAF =tFirstChoosePhone.replaceAll("[^0-9]", "");
			}
		}else{
			tFormatAF=tFirstChoosePhone;
		}
		AppntPhone.setText(tFormatAF);
		ElementLis AppntPhone2 = new ElementLis("AppntPhone2");
		String tOtherChoosePhone =ttLBPOAppntSchema.getCompanyPhone();
//		首选电话转换
		String tFormatAO="";
		if(tOtherChoosePhone!=null){
			if(tOtherChoosePhone.indexOf("转")!=-1){
				tOtherChoosePhone=StrTool.replace(tOtherChoosePhone, "转", "-");
				String tOtherPhone[]=tOtherChoosePhone.split("-");
				tFormatAO =tOtherPhone[0].replaceAll("[^0-9]", "")+"-"+tOtherPhone[1].replaceAll("[^0-9]", "");
			}else{
				tFormatAO=tOtherChoosePhone.replaceAll("[^0-9]", "");
			}
		}else{
			tFormatAO=tOtherChoosePhone;
		}
		AppntPhone2.setText(tFormatAO);
		ElementLis AppntEMail = new ElementLis("AppntEMail");
		AppntEMail.setText(ttLBPOAppntSchema.getEMail());
		ElementLis AppntGrpName = new ElementLis("AppntGrpName");
		AppntGrpName.setText(ttLBPOAppntSchema.getCompanyAddress());
		ElementLis AppntWorkType = new ElementLis("AppntWorkType");
		AppntWorkType.setText(ttLBPOAppntSchema.getWorkType());
		ElementLis AppntPluralityType = new ElementLis("AppntPluralityType");
		AppntPluralityType.setText(ttLBPOAppntSchema.getPluralityType());
		ElementLis AppntOccupationCode = new ElementLis("AppntOccupationCode");
		AppntOccupationCode.setText(ttLBPOAppntSchema.getOccupationCode());
		//tongmeng 2009-04-01 modify
		//针对简易投保单特殊处理
		//ttLBPOContSchema.getContNo()
		String tempContNo = ttLBPOContSchema.getContNo();
		if(tempContNo==null)
		{
			tempContNo = "";
		}
		if(!tempContNo.equals("")&&tempContNo.substring(0,4).equals("8616"))
		{
			/*
			规则：注：导入系统时判断“投保人资料”中的“系被保险人的”关系，如为“00-本人”时，
			取“投保人资料”中录入的所有项目和“被保险人资料”中的“工作单位”、“职业”、“职业代码”，
			全部导入系统“投保人资料”中；如为非本人时，按实际录入项目分别
			导入“投保人资料”和“被保险人资料”中。
			 */
			String tRelation = ttLBPOAppntSchema.getRelationToInsured();
			if(tRelation!=null&&tRelation.equals("00"))
			{
				String tSQL_Insured = "select * from lbpoinsured where  "
								    + " contno='"+"?contno?"+"' and inputno='"+"?inputno?"+"' ";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(tSQL_Insured);
				sqlbv4.put("contno", mContNo);
				sqlbv4.put("inputno", mInputTime);
				//简易投保单只有唯一的一条被保人记录.
				LBPOInsuredSet tempLBPOInsuredSet = new LBPOInsuredSet();
				LBPOInsuredDB tempLBPOInsuredDB = new LBPOInsuredDB();
				LBPOInsuredSchema tempLBPOInsuredSchema = new LBPOInsuredSchema();
				tempLBPOInsuredSet = tempLBPOInsuredDB.executeQuery(sqlbv4);
				if(tempLBPOInsuredSet.size()>0)
				{
					tempLBPOInsuredSchema = tempLBPOInsuredSet.get(1);
					AppntGrpName.setText(tempLBPOInsuredSchema.getCompanyAddress());
					AppntWorkType.setText(tempLBPOInsuredSchema.getWorkType());
					AppntOccupationCode.setText(tempLBPOInsuredSchema.getOccupationCode());
					
				}
			}
		}
		
		
		LCAppnt.addContent(AppntName);
		LCAppnt.addContent(AppntSex);
		LCAppnt.addContent(AppntBirthday);
		LCAppnt.addContent(AppntRelationToInsured);
		LCAppnt.addContent(AppntIDType);
		LCAppnt.addContent(AppntIDNo);
		LCAppnt.addContent(AppntIDEndDate);
		LCAppnt.addContent(AppntNativePlace);
		LCAppnt.addContent(AppntRgtAddress);
		LCAppnt.addContent(AppntMarriage);
		LCAppnt.addContent(AppntPostalAddress);
		LCAppnt.addContent(AppntZipCode);
		LCAppnt.addContent(AppntHomeAddress);
		LCAppnt.addContent(AppntHomeZipCode);
		LCAppnt.addContent(AppntPhone);
		LCAppnt.addContent(AppntPhone2);
		LCAppnt.addContent(AppntEMail);
		LCAppnt.addContent(AppntGrpName);
		LCAppnt.addContent(AppntSocialInsuFlag);
		LCAppnt.addContent(AppntWorkType);
		LCAppnt.addContent(AppntPluralityType);
		LCAppnt.addContent(AppntOccupationCode);
////////////////////////////////////////////
		ElementLis LCCustomerImpartSet = new ElementLis("LCCustomerImpartSet");
		OnePolData.addContent(LCCustomerImpartSet);
		//tongmeng 2009-04-01 add
	    //身高体重等数据,不需要记录是否,并且需要拆分为2条.对这样的数据,单独处理
		String tSQL_Spec = "select * from lbpocustomerimpart where "
			        + " impartcode in ('A0501','A0534','A0101','A0120','D0101','D0119') "
			        + " and contno='"+"?contno?"+"' and inputno='"+"?inputno?"+"' ";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSQL_Spec);
		sqlbv5.put("contno", mContNo);
		sqlbv5.put("inputno", mInputTime);
		LBPOCustomerImpartDB tSpecLBPOCustomerImpartDB = new LBPOCustomerImpartDB();
		LBPOCustomerImpartSet tSpecLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
		tSpecLBPOCustomerImpartSet = tSpecLBPOCustomerImpartDB.executeQuery(sqlbv5);
		WBChangeRiskField tWBChangeRiskField = new WBChangeRiskField();
		for(int i=1;i<=tSpecLBPOCustomerImpartSet.size();i++)
		{
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema = tSpecLBPOCustomerImpartSet.get(i);
			TransferData transTransferData = new TransferData();
			tWBChangeRiskField.changeRiskField(tLBPOCustomerImpartSchema, transTransferData, "JM");
			tTransferData=mDSPubCheckCol.verifyValue(tLBPOCustomerImpartSchema, "02");
			Vector tV4 = tTransferData.getValueNames();
			for(int k=0;k<tV4.size();k++){
				mSucceFlag="1";
				ElementLis Error = new ElementLis("Error");
				ElementLis ErrType = new ElementLis("ErrType");
				ElementLis ErrTag = new ElementLis("ErrTag");
				ElementLis ErrCode  = new ElementLis("ErrCode");
				ElementLis ErrMessage  = new ElementLis("ErrMessage");
				ErrType.setText("1");
				ErrTag.setText((String)tV4.get(k));
				ErrCode.setText((String)ttTransferData.getValueByName(tV4.get(k)));
				ErrMessage.setText((String)tTransferData.getValueByName((String)tV4.get(k)));
				Errors.addContent(Error);
				Error.addContent(ErrType);
				Error.addContent(ErrTag);
				Error.addContent(ErrCode);
				Error.addContent(ErrMessage);
			}
			String tImpartCode = tLBPOCustomerImpartSchema.getImpartCode();
			int tLoopTimes = 0;
			String tCusomerType = "A";
			int tCopyStart = 0;
			int tCopyEnd = 0;
			if(tImpartCode.equals("A0101")||tImpartCode.equals("A0501"))
			{
				tLoopTimes = 2;
				tCusomerType = "I";
				//tCopyStart = 0;
				//tCopyEnd = 2;
			}
			else if(tImpartCode.equals("A0120")||tImpartCode.equals("A0534"))
			{
				tLoopTimes = 2;
				tCusomerType = "I";
			}
			else if(tImpartCode.equals("D0101"))
			{
				tLoopTimes = 3;
				tCusomerType = "I";
			}
			else if(tImpartCode.equals("D0119"))
			{
				tLoopTimes = 2;
				tCusomerType = "I";
			}else if(tImpartCode.equals("A0102")){
				tLoopTimes = 2;
				tCusomerType = "I";
			}
			
			//复制数据
			for(int n=1;n<=tLoopTimes;n++)
			{
				ElementLis LCCustomerImpart = new ElementLis("LCCustomerImpart");
				
				LCCustomerImpartSet.addContent(LCCustomerImpart);
				ElementLis ImpartFlag = new ElementLis("ImpartFlag");
				ImpartFlag.setText("1");
				ElementLis ImpartVer = new ElementLis("ImpartVer");
				ImpartVer.setText(tLBPOCustomerImpartSchema.getImpartVer());
				ElementLis ImpartCode = new ElementLis("ImpartCode");
				ImpartCode.setText(tLBPOCustomerImpartSchema.getImpartCode());
				ElementLis ImpartContent = new ElementLis("ImpartContent");
				ImpartContent.setText(tLBPOCustomerImpartSchema.getImpartParamModle());
				ElementLis CustomerNoType = new ElementLis("CustomerNoType");
				ElementLis CustomerToInsured = new ElementLis("CustomerToInsured");
				//CustomerNoType  1-被保人   0-投保人
				if(tLBPOCustomerImpartSchema.getImpartVer().indexOf("A")!=-1)
				{
					if(n==1)
					{
						CustomerNoType.setText("1");
					}
					else
					{
						CustomerNoType.setText("0");
					}
					
					if(tImpartCode.equals("A0101")||tImpartCode.equals("A0120")||tImpartCode.equals("A0501")||tImpartCode.equals("A0534"))
					{
						String tImpartContent =  tLBPOCustomerImpartSchema.getImpartParamModle();
						if(n==1)
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 1,"/")+"/"+PubFun.getStr(tImpartContent, 2,"/"));
						}
						if(isOnePerson)
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 1,"/")+"/"+PubFun.getStr(tImpartContent, 2,"/"));
						} else if((!isOnePerson)&&(n!=1)){
							ImpartContent.setText(PubFun.getStr(tImpartContent, 3,"/")+"/"+PubFun.getStr(tImpartContent, 4,"/"));
						}
					}
					//拆分投被保人吸烟量  05-18
					if(tImpartCode.equals("A0102")){
						String tImpartContent =  tLBPOCustomerImpartSchema.getImpartParamModle();
						String tempImpartContent="";
						if(n==1)
						{
							tempImpartContent = PubFun.getStr(tImpartContent, 1,"/")+"/"+PubFun.getStr(tImpartContent, 2,"/")+"/"+PubFun.getStr(tImpartContent, 3,"/");
							//非必录项的处理
							if(!"".equals(PubFun.getStr(tImpartContent, 6,"/"))){
								tempImpartContent = tempImpartContent + "/" + PubFun.getStr(tImpartContent, 6,"/");
							}
							ImpartContent.setText(tempImpartContent);
						}
						else
						{
							tempImpartContent = PubFun.getStr(tImpartContent, 1,"/")+"/"+PubFun.getStr(tImpartContent, 4,"/")+"/"+PubFun.getStr(tImpartContent, 5,"/");
							//非必录项的处理
							if(!"".equals(PubFun.getStr(tImpartContent, 6,"/"))){
								tempImpartContent = tempImpartContent + "/" + PubFun.getStr(tImpartContent, 6,"/");
							}
							ImpartContent.setText(tempImpartContent);
						}
					}
				}
				else if(tLBPOCustomerImpartSchema.getImpartVer().indexOf("D")!=-1)
				{
//					if(n==1)
//					{
//						CustomerNoType.setText("1");
//					}
//					else
					{
						CustomerNoType.setText("0");
					}
					//if(tLBPOCustomerImpartSchema.getImpartCode().equals("D0101"))
					{
						CustomerToInsured.setText(String.valueOf(n));
					}
					if(tImpartCode.equals("D0101"))
					{
						String tImpartContent =  tLBPOCustomerImpartSchema.getImpartParamModle();
						if(n==1)
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 1,"/")+"/"+PubFun.getStr(tImpartContent, 2,"/"));
						}
						else if(n==2)
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 3,"/")+"/"+PubFun.getStr(tImpartContent, 4,"/"));
						}
						else 
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 5,"/")+"/"+PubFun.getStr(tImpartContent, 6,"/"));
						}
					}
					if(tImpartCode.equals("D0119"))
					{
						String tImpartContent =  tLBPOCustomerImpartSchema.getImpartParamModle();
						if(n==1)
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 1,"/")+"/"+PubFun.getStr(tImpartContent, 2,"/"));
						}
						else
						{
							ImpartContent.setText(PubFun.getStr(tImpartContent, 3,"/")+"/"+PubFun.getStr(tImpartContent, 4,"/"));
						}
					}
				}

				LCCustomerImpart.addContent(ImpartFlag);
				LCCustomerImpart.addContent(ImpartVer);
				LCCustomerImpart.addContent(ImpartCode);
				LCCustomerImpart.addContent(ImpartContent);
				LCCustomerImpart.addContent(CustomerNoType);
				LCCustomerImpart.addContent(CustomerToInsured);
			}
				
		}
		/**
		 * 投保人告知信息
		 */
		
		LBPOCustomerImpartDB tLBPOCustomerImpartDB = new LBPOCustomerImpartDB();
		LBPOCustomerImpartSet tLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
		//tLBPOCustomerImpartDB.setContNo((mContNo));
		//tLBPOCustomerImpartDB.setCustomerNoType("0");
		//tLBPOCustomerImpartDB.setPrtFlag("0");
		//tLBPOCustomerImpartDB.setInputNo(mInputTime);
		//tongmeng 2009-04-01 modify
		//业务员告知也使用prtflag
		String appntImpartSql="select * from lbpocustomerimpart where impartver not in ('A03','D04','103') " 
							 + " and impartcode not in ('A0501','A0534','A0101','A0120','D0101','D0119') "
							 + " and prtflag='0' "
		//+"and impartparammodle is not null " +
		+"and contno='"+"?contno1?"+"' and inputno='"+"?inputno1?"+"'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(appntImpartSql);
		sqlbv6.put("contno1", mContNo);
		sqlbv6.put("inputno1", mInputTime);
		tLBPOCustomerImpartSet = tLBPOCustomerImpartDB.executeQuery(sqlbv6);
		for (int n = 1; n <= tLBPOCustomerImpartSet.size(); n++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema = tLBPOCustomerImpartSet.get(n);
			TransferData transTransferData = new TransferData();
			tWBChangeRiskField.changeRiskField(tLBPOCustomerImpartSchema, transTransferData, "JM");
			tTransferData=mDSPubCheckCol.verifyValue(tLBPOCustomerImpartSchema, "02");
			Vector tV4 = tTransferData.getValueNames();
			for(int i=0;i<tV4.size();i++){
				mSucceFlag="1";
				ElementLis Error = new ElementLis("Error");
				ElementLis ErrType = new ElementLis("ErrType");
				ElementLis ErrTag = new ElementLis("ErrTag");
				ElementLis ErrCode  = new ElementLis("ErrCode");
				ElementLis ErrMessage  = new ElementLis("ErrMessage");
				ErrType.setText("1");
				ErrTag.setText((String)tV4.get(i));
				ErrCode.setText((String)ttTransferData.getValueByName(tV4.get(i)));
				ErrMessage.setText((String)tTransferData.getValueByName((String)tV4.get(i)));
				Errors.addContent(Error);
				Error.addContent(ErrType);
				Error.addContent(ErrTag);
				Error.addContent(ErrCode);
				Error.addContent(ErrMessage);
			}
			LBPOInsuredDB ttLBPOInsuredDB = new LBPOInsuredDB();
//			LBPOInsuredSet ttLBPOInsuredSet = new LBPOInsuredSet();
			ttLBPOInsuredDB.setInsuredNo(tLBPOCustomerImpartSchema
					.getCustomerNo());
			ttLBPOInsuredDB.setInputNo(mInputTime);
//			ttLBPOInsuredSet = ttLBPOInsuredDB.query();
//			LBPOInsuredSchema mLBPOInsuredSchema = new LBPOInsuredSchema();
//			mLBPOInsuredSchema = ttLBPOInsuredSet.get(1);
			ElementLis LCCustomerImpart = new ElementLis("LCCustomerImpart");

			LCCustomerImpartSet.addContent(LCCustomerImpart);
			ElementLis ImpartFlag = new ElementLis("ImpartFlag");
			if (tLBPOCustomerImpartSchema.getImpartParamModle()==null)
			{
				tLBPOCustomerImpartSchema.setImpartParamModle("是");
				ImpartFlag.setText("1");
			}
			else
			{
				if("否".equals(tLBPOCustomerImpartSchema.getImpartParamModle()))
					ImpartFlag.setText("0");
				else
				{
					String impartparammodle = "";
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					tLDImpartDB.setImpartVer(tLBPOCustomerImpartSchema.getImpartVer());
					tLDImpartDB.setImpartCode(tLBPOCustomerImpartSchema.getImpartCode());
					if(!tLDImpartDB.getInfo())
					{
					}
					else
					{
						tLDImpartSchema= tLDImpartDB.getSchema();
					}
				    if(tLDImpartSchema!=null){
				    	impartparammodle = tLDImpartSchema.getImpartParamModle();
				    	if(impartparammodle!=null 
								&& tLBPOCustomerImpartSchema.getImpartParamModle().equals(impartparammodle))
						{
							ImpartFlag.setText("0");
						}
				    	else
				    		ImpartFlag.setText("1");
				    }					
					else {
						ImpartFlag.setText("1");
					}
				}					
			}
			ElementLis ImpartVer = new ElementLis("ImpartVer");
			ImpartVer.setText(tLBPOCustomerImpartSchema.getImpartVer());
			ElementLis ImpartCode = new ElementLis("ImpartCode");
			ImpartCode.setText(tLBPOCustomerImpartSchema.getImpartCode());
			ElementLis ImpartContent = new ElementLis("ImpartContent");
			ImpartContent.setText(tLBPOCustomerImpartSchema.getImpartParamModle());
			ElementLis CustomerNoType = new ElementLis("CustomerNoType");
			ElementLis CustomerToInsured = new ElementLis("CustomerToInsured");
			CustomerNoType.setText("0");
			LCCustomerImpart.addContent(ImpartFlag);
			LCCustomerImpart.addContent(ImpartVer);
			LCCustomerImpart.addContent(ImpartCode);
			LCCustomerImpart.addContent(ImpartContent);
			LCCustomerImpart.addContent(CustomerNoType);
			LCCustomerImpart.addContent(CustomerToInsured);
		}

		/**
		 * 被保人告知信息
		 */
		
		LBPOCustomerImpartDB insLBPOCustomerImpartDB = new LBPOCustomerImpartDB();
		String insuredImpartSql1 ="select * from lbpocustomerimpart where impartver not in ('A03','D04','103') " 
			 + " and impartcode not in ('A0501','A0534','A0101','A0120','D0101','D0119') "
			 + " and Insured1='0' " + 
			 " and contno='"+"?contno2?"+"' and inputno='"+"?inputno2?"+"'";	
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(insuredImpartSql1);
		sqlbv7.put("contno2", mContNo);
		sqlbv7.put("inputno2", mInputTime);
//		insLBPOCustomerImpartDB.setContNo((mContNo));
//		insLBPOCustomerImpartDB.setInsured1("0");
//		insLBPOCustomerImpartDB.setInputNo(mInputTime);
		tLBPOCustomerImpartSet.clear();
		tLBPOCustomerImpartSet = insLBPOCustomerImpartDB.executeQuery(sqlbv7);
		for (int n = 1; n <= tLBPOCustomerImpartSet.size(); n++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema = tLBPOCustomerImpartSet.get(n);
			ElementLis LCCustomerImpart = new ElementLis("LCCustomerImpart");

			tTransferData=mDSPubCheckCol.verifyValue(tLBPOCustomerImpartSchema, "02");
			Vector tV4 = tTransferData.getValueNames();
			for(int i=0;i<tV4.size();i++){
				mSucceFlag="1";
				ElementLis Error = new ElementLis("Error");
				ElementLis ErrType = new ElementLis("ErrType");
				ElementLis ErrTag = new ElementLis("ErrTag");
				ElementLis ErrCode  = new ElementLis("ErrCode");
				ElementLis ErrMessage  = new ElementLis("ErrMessage");
				ErrType.setText("1");
				ErrTag.setText((String)tV4.get(i));
				ErrCode.setText((String)ttTransferData.getValueByName(tV4.get(i)));
				ErrMessage.setText((String)tTransferData.getValueByName((String)tV4.get(i)));
				Errors.addContent(Error);
				Error.addContent(ErrType);
				Error.addContent(ErrTag);
				Error.addContent(ErrCode);
				Error.addContent(ErrMessage);
			}
			
			
			LCCustomerImpartSet.addContent(LCCustomerImpart);
			ElementLis ImpartFlag = new ElementLis("ImpartFlag");
			if (tLBPOCustomerImpartSchema.getImpartParamModle()==null)
			{
				tLBPOCustomerImpartSchema.setImpartParamModle("是");
				ImpartFlag.setText("1");
			}
			else
			{
				if("否".equals(tLBPOCustomerImpartSchema.getImpartParamModle()))
					ImpartFlag.setText("0");
				else
				{
					String impartparammodle = "";
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					tLDImpartDB.setImpartVer(tLBPOCustomerImpartSchema.getImpartVer());
					tLDImpartDB.setImpartCode(tLBPOCustomerImpartSchema.getImpartCode());
					if(!tLDImpartDB.getInfo())
					{
					}
					else
					{
						tLDImpartSchema= tLDImpartDB.getSchema();
					}
				    if(tLDImpartSchema!=null){
				    	impartparammodle = tLDImpartSchema.getImpartParamModle();
				    	if(impartparammodle!=null 
								&& tLBPOCustomerImpartSchema.getImpartParamModle().equals(impartparammodle))
						{
							ImpartFlag.setText("0");
						}
				    	else
				    		ImpartFlag.setText("1");
				    }					
					else {
						ImpartFlag.setText("1");
					}
				}					
			}
			ElementLis ImpartVer = new ElementLis("ImpartVer");
			ImpartVer.setText(tLBPOCustomerImpartSchema.getImpartVer());
			ElementLis ImpartCode = new ElementLis("ImpartCode");
			ImpartCode.setText(tLBPOCustomerImpartSchema.getImpartCode());
			ElementLis ImpartContent = new ElementLis("ImpartContent");
			ImpartContent.setText(tLBPOCustomerImpartSchema.getImpartParamModle());
			ElementLis CustomerNoType = new ElementLis("CustomerNoType");
			CustomerNoType.setText("1");
			ElementLis CustomerToInsured = new ElementLis("CustomerToInsured");
			if (isOneInsured) {
				CustomerToInsured.setText("");
			} else {
				CustomerToInsured.setText("1");
			}
			LCCustomerImpart.addContent(ImpartFlag);
			LCCustomerImpart.addContent(ImpartVer);
			LCCustomerImpart.addContent(ImpartCode);
			LCCustomerImpart.addContent(ImpartContent);
			LCCustomerImpart.addContent(CustomerNoType);
			LCCustomerImpart.addContent(CustomerToInsured);
		}
		
		//第二被保人告知信息
		LBPOCustomerImpartDB ins2LBPOCustomerImpartDB = new LBPOCustomerImpartDB();
		String insuredImpartSql2 ="select * from lbpocustomerimpart where impartver not in ('A03','D04','103') " 
			 + " and impartcode not in ('A0501','A0534','A0101','A0120','D0101','D0119') "
			 + " and Insured2='0' " + 
			 " and contno='"+"?contno3?"+"' and inputno='"+"?inputno3?"+"'";		
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(insuredImpartSql2);
		sqlbv8.put("contno3", mContNo);
		sqlbv8.put("inputno3", mInputTime);
//		ins2LBPOCustomerImpartDB.setContNo((mContNo));
//		ins2LBPOCustomerImpartDB.setInsured2("0");
//		ins2LBPOCustomerImpartDB.setInputNo(mInputTime);
		tLBPOCustomerImpartSet.clear();
		tLBPOCustomerImpartSet = ins2LBPOCustomerImpartDB.executeQuery(sqlbv8);
		for (int n = 1; n <= tLBPOCustomerImpartSet.size(); n++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema = tLBPOCustomerImpartSet.get(n);
			ElementLis LCCustomerImpart = new ElementLis("LCCustomerImpart");

			LCCustomerImpartSet.addContent(LCCustomerImpart);
			ElementLis ImpartFlag = new ElementLis("ImpartFlag");
			if (tLBPOCustomerImpartSchema.getImpartParamModle()==null)
			{
				tLBPOCustomerImpartSchema.setImpartParamModle("是");
				ImpartFlag.setText("1");
			}
			else
			{
				if("否".equals(tLBPOCustomerImpartSchema.getImpartParamModle()))
					ImpartFlag.setText("0");
				else
				{
					String impartparammodle = "";
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					tLDImpartDB.setImpartVer(tLBPOCustomerImpartSchema.getImpartVer());
					tLDImpartDB.setImpartCode(tLBPOCustomerImpartSchema.getImpartCode());
					if(!tLDImpartDB.getInfo())
					{
					}
					else
					{
						tLDImpartSchema= tLDImpartDB.getSchema();
					}
				    if(tLDImpartSchema!=null){
				    	impartparammodle = tLDImpartSchema.getImpartParamModle();
				    	if(impartparammodle!=null 
								&& tLBPOCustomerImpartSchema.getImpartParamModle().equals(impartparammodle))
						{
							ImpartFlag.setText("0");
						}
				    	else
				    		ImpartFlag.setText("1");
				    }					
					else {
						ImpartFlag.setText("1");
					}
				}					
			}
			ElementLis ImpartVer = new ElementLis("ImpartVer");
			ImpartVer.setText(tLBPOCustomerImpartSchema.getImpartVer());
			ElementLis ImpartCode = new ElementLis("ImpartCode");
			ImpartCode.setText(tLBPOCustomerImpartSchema.getImpartCode());
			ElementLis ImpartContent = new ElementLis("ImpartContent");
			ImpartContent.setText(tLBPOCustomerImpartSchema.getImpartParamModle());
			ElementLis CustomerNoType = new ElementLis("CustomerNoType");
			CustomerNoType.setText("1");
			ElementLis CustomerToInsured = new ElementLis("CustomerToInsured");
			CustomerToInsured.setText("2");
			LCCustomerImpart.addContent(ImpartFlag);
			LCCustomerImpart.addContent(ImpartVer);
			LCCustomerImpart.addContent(ImpartCode);
			LCCustomerImpart.addContent(ImpartContent);
			LCCustomerImpart.addContent(CustomerNoType);
			LCCustomerImpart.addContent(CustomerToInsured);
		}
		//第三被保人告知信息
		LBPOCustomerImpartDB ins3LBPOCustomerImpartDB = new LBPOCustomerImpartDB();
		String insuredImpartSql3 ="select * from lbpocustomerimpart where impartver not in ('A03','D04','103') " 
			 + " and impartcode not in ('A0501','A0534','A0101','A0120','D0101','D0119') "
			 + " and Insured3='0' " + 
			 " and contno='"+"?contno4?"+"' and inputno='"+"?inputno4?"+"'";		
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(insuredImpartSql3);
		sqlbv9.put("contno4", mContNo);
		sqlbv9.put("inputno4", mInputTime);
//		ins3LBPOCustomerImpartDB.setContNo((mContNo));
//		ins3LBPOCustomerImpartDB.setinsured3("0");
//		ins3LBPOCustomerImpartDB.setInputNo(mInputTime);
		tLBPOCustomerImpartSet.clear();
		tLBPOCustomerImpartSet = ins3LBPOCustomerImpartDB.executeQuery(sqlbv9);
		for (int n = 1; n <= tLBPOCustomerImpartSet.size(); n++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema = tLBPOCustomerImpartSet.get(n);
			ElementLis LCCustomerImpart = new ElementLis("LCCustomerImpart");

			LCCustomerImpartSet.addContent(LCCustomerImpart);
			ElementLis ImpartFlag = new ElementLis("ImpartFlag");
			if (tLBPOCustomerImpartSchema.getImpartParamModle()==null)
			{
				tLBPOCustomerImpartSchema.setImpartParamModle("是");
				ImpartFlag.setText("1");
			}
			else
			{
				if("否".equals(tLBPOCustomerImpartSchema.getImpartParamModle()))
					ImpartFlag.setText("0");
				else
				{
					String impartparammodle = "";
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					tLDImpartDB.setImpartVer(tLBPOCustomerImpartSchema.getImpartVer());
					tLDImpartDB.setImpartCode(tLBPOCustomerImpartSchema.getImpartCode());
					if(!tLDImpartDB.getInfo())
					{
					}
					else
					{
						tLDImpartSchema= tLDImpartDB.getSchema();
					}
				    if(tLDImpartSchema!=null){
				    	impartparammodle = tLDImpartSchema.getImpartParamModle();
				    	if(impartparammodle!=null 
								&& tLBPOCustomerImpartSchema.getImpartParamModle().equals(impartparammodle))
						{
							ImpartFlag.setText("0");
						}
				    	else
				    		ImpartFlag.setText("1");
				    }					
					else {
						ImpartFlag.setText("1");
					}
				}					
			}
			ElementLis ImpartVer = new ElementLis("ImpartVer");
			ImpartVer.setText(tLBPOCustomerImpartSchema.getImpartVer());
			ElementLis ImpartCode = new ElementLis("ImpartCode");
			ImpartCode.setText(tLBPOCustomerImpartSchema.getImpartCode());
			ElementLis ImpartContent = new ElementLis("ImpartContent");
			ImpartContent.setText(tLBPOCustomerImpartSchema.getImpartParamModle());
			ElementLis CustomerNoType = new ElementLis("CustomerNoType");
			CustomerNoType.setText("1");
			ElementLis CustomerToInsured = new ElementLis("CustomerToInsured");
			CustomerToInsured.setText("3");
			LCCustomerImpart.addContent(ImpartFlag);
			LCCustomerImpart.addContent(ImpartVer);
			LCCustomerImpart.addContent(ImpartCode);
			LCCustomerImpart.addContent(ImpartContent);
			LCCustomerImpart.addContent(CustomerNoType);
			LCCustomerImpart.addContent(CustomerToInsured);
		}
		//业务员告知信息
		LBPOCustomerImpartDB agentLBPOCustomerImpartDB = new LBPOCustomerImpartDB();
		tLBPOCustomerImpartSet.clear();
		String agentImpartSql="select * from lbpocustomerimpart where impartver in ('A03','D04','103') " +
				//tongmeng 2009-04-01 add
				//业务员告知也按是否导入
		        " and (prtflag='0' or insured1='0' " + 
				"or impartparammodle is not null) and impartcode not in ('A0156','A0157','D0157','D0158') " +
				"and contno='"+"?contno5?"+"' and inputno='"+"?inputno5?"+"'";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(agentImpartSql);
		sqlbv10.put("contno5", mContNo);
		sqlbv10.put("inputno5", mInputTime);
		logger.debug(agentImpartSql);
		tLBPOCustomerImpartSet = agentLBPOCustomerImpartDB.executeQuery(sqlbv10);
		for (int n = 1; n <= tLBPOCustomerImpartSet.size(); n++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema = tLBPOCustomerImpartSet.get(n);
			ElementLis LCCustomerImpart = new ElementLis("LCCustomerImpart");

			LCCustomerImpartSet.addContent(LCCustomerImpart);
			ElementLis ImpartFlag = new ElementLis("ImpartFlag");
			if (tLBPOCustomerImpartSchema.getPrtFlag()==null && tLBPOCustomerImpartSchema.getInsured1()==null)
			{
				if (tLBPOCustomerImpartSchema.getImpartParamModle()==null)
					ImpartFlag.setText("0");
				else
				{
					if("否".equals(tLBPOCustomerImpartSchema.getImpartParamModle()))
						ImpartFlag.setText("0");
					else
					{
						String impartparammodle = "";
						LDImpartSchema tLDImpartSchema = new LDImpartSchema();
						LDImpartDB tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLBPOCustomerImpartSchema.getImpartVer());
						tLDImpartDB.setImpartCode(tLBPOCustomerImpartSchema.getImpartCode());
						if(!tLDImpartDB.getInfo())
						{
						}
						else
						{
							tLDImpartSchema= tLDImpartDB.getSchema();
						}
					    if(tLDImpartSchema!=null){
					    	impartparammodle = tLDImpartSchema.getImpartParamModle();
					    	if(impartparammodle!=null 
									&& tLBPOCustomerImpartSchema.getImpartParamModle().equals(impartparammodle))
							{
								ImpartFlag.setText("0");
							}
					    	else
					    		ImpartFlag.setText("1");
					    }					
						else {
							ImpartFlag.setText("1");
						}
					}					
				}
			}
			else if((tLBPOCustomerImpartSchema.getPrtFlag()!=null && tLBPOCustomerImpartSchema.getPrtFlag().equals("0"))
					|| (tLBPOCustomerImpartSchema.getInsured1()!=null && tLBPOCustomerImpartSchema.getInsured1().equals("0")))
			{
				tLBPOCustomerImpartSchema.setImpartParamModle("是");
				ImpartFlag.setText("1");
			}
			else
			{
				ImpartFlag.setText("0");
			}
			
			ElementLis ImpartVer = new ElementLis("ImpartVer");
			ImpartVer.setText(tLBPOCustomerImpartSchema.getImpartVer());
			ElementLis ImpartCode = new ElementLis("ImpartCode");
			ImpartCode.setText(tLBPOCustomerImpartSchema.getImpartCode());
			ElementLis ImpartContent = new ElementLis("ImpartContent");
			ImpartContent.setText(tLBPOCustomerImpartSchema.getImpartParamModle());
			ElementLis CustomerNoType = new ElementLis("CustomerNoType");
			CustomerNoType.setText("2");
			ElementLis CustomerToInsured = new ElementLis("CustomerToInsured");
			CustomerToInsured.setText("");
			LCCustomerImpart.addContent(ImpartFlag);
			LCCustomerImpart.addContent(ImpartVer);
			LCCustomerImpart.addContent(ImpartCode);
			LCCustomerImpart.addContent(ImpartContent);
			LCCustomerImpart.addContent(CustomerNoType);
			LCCustomerImpart.addContent(CustomerToInsured);
		}
		/**
		 * 被保人信息
		 * */
		ElementLis LCInsureds = new ElementLis("LCInsureds");
		OnePolData.addContent(LCInsureds);
		logger.debug("共"+tLBPOInsuredSet.size()+"个被保人");
		for (int i = 1; i <= tLBPOInsuredSet.size(); i++) {
			boolean isOnePol = true;
//			ExeSQL ttExeSQL = new ExeSQL();
			LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
			tLBPOInsuredSchema = tLBPOInsuredSet.get(i);
			tTransferData=mDSPubCheckCol.verifyValue(tLBPOInsuredSchema, "02");
			Vector tV5 = tTransferData.getValueNames();
			for(int j=0;j<tV5.size();j++){
				mSucceFlag="1";
				ElementLis Error = new ElementLis("Error");
				ElementLis ErrType = new ElementLis("ErrType");
				ElementLis ErrTag = new ElementLis("ErrTag");
				ElementLis ErrCode  = new ElementLis("ErrCode");
				ElementLis ErrMessage  = new ElementLis("ErrMessage");
				ErrType.setText("1");
				ErrTag.setText((String)tV5.get(j));
				ErrCode.setText((String)ttTransferData.getValueByName(tV5.get(j)));
				ErrMessage.setText((String)tTransferData.getValueByName((String)tV5.get(j)));
				Errors.addContent(Error);
				Error.addContent(ErrType);
				Error.addContent(ErrTag);
				Error.addContent(ErrCode);
				Error.addContent(ErrMessage);
			}
			
			ElementLis LCInsured = new ElementLis("LCInsured");
			LCInsureds.addContent(LCInsured);
			ElementLis InsuredNo = new ElementLis("InsuredNo");
			if (isOneInsured) {
				InsuredNo.setText("-1");
			} else {
				InsuredNo.setText(tLBPOInsuredSchema.getSequenceNo());
			}
			LCInsured.addContent(InsuredNo);
			ElementLis LBPOInsuredDes = new ElementLis("LCInsuredDes");
			LCInsured.addContent(LBPOInsuredDes);
			ElementLis RelationToMainInsured = new ElementLis(
					"RelationToMainInsured");
			RelationToMainInsured.setText(tLBPOInsuredSchema
					.getRelationToMainInsured());
			ElementLis Name = new ElementLis("Name");
			Name.setText(tLBPOInsuredSchema.getName());
			ElementLis Sex = new ElementLis("Sex");
			String tInsurdSex =tLBPOInsuredSchema.getSex();
			//Sex.setText(tLBPOInsuredSchema.getSex());
			ElementLis Birthday = new ElementLis("Birthday");
			String tInsuredBirthday = tLBPOInsuredSchema.getBirthday();
			//Birthday.setText(tLBPOInsuredSchema.getBirthday());
			ElementLis RelationToAppnt = new ElementLis("RelationToAppnt");
			RelationToAppnt.setText(tLBPOInsuredSchema.getRelationToAppnt());
			ElementLis IDType = new ElementLis("IDType");
			String tInsuredIDType =tLBPOInsuredSchema.getIDType() ;
			//IDType.setText(tLBPOInsuredSchema.getIDType());
			ElementLis IDNo = new ElementLis("IDNo");
			String tInsuredIDNo = tLBPOInsuredSchema.getIDNo();
			if("出生日期".equals(tInsuredIDType)||(tInsuredBirthday!=null&&tInsuredBirthday.equals(tInsuredIDType))
					||"无证件".equals(tInsuredIDType)){
				tInsuredIDType ="9";
			}
			if(tInsuredIDType==null||"".equals(tInsuredIDType)||"null".equals(tInsuredIDType)){
				if(tInsuredIDNo!=null&&(tInsuredIDNo.length()==15||tInsuredIDNo.length()==18)){
					tInsuredIDType="0";
				}else{
					tInsuredIDType="8";
				}
			}
			IDType.setText(tInsuredIDType);
//			通过身份证号计算性别
			if(tInsurdSex==null||"".equals(tInsurdSex)||"null".equals(tInsurdSex)){
				if("0".equals(tInsuredIDType)){
					tInsurdSex=PubFun.getSexFromId(tInsuredIDNo);
				}
				if(!"0".equals(tInsurdSex)&&!"1".equals(tInsurdSex)){
						tInsurdSex ="2";
					}
			}
			Sex.setText(tInsurdSex);
//			当证件类型为9-无证件时，将IDNo 赋值为出生日期 YYYY-MM-DD(前提是日期格式要合法)
			  if("9".equals(tInsuredIDType)){
				  Date tDate = null;
				SimpleDateFormat df;
				  try {
						if (tInsuredBirthday.indexOf("-") != -1) {
							df = new SimpleDateFormat("yyyy-M-d");
							tDate = df.parse(tInsuredBirthday);
							String dateStr2=df.format(tDate);
							if(dateStr2.equals(tInsuredBirthday)){
								//日期合法
							}
						} else {
							df = new SimpleDateFormat("yyyyMMdd");
							tDate = df.parse(tInsuredBirthday);
	                            //日期合法
						}
						ExeSQL tExeSQL = new ExeSQL();
						String tBirthSQL = "select to_char(to_date('"+"?contno6?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual";
						SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
						sqlbv11.sql(tBirthSQL);
						sqlbv11.put("contno6", tInsuredBirthday);
						String tBirth = tExeSQL.getOneValue(sqlbv11);
						tInsuredIDNo=tBirth;
					} catch (Exception e) {
						// @@错误处理
						//tResult = "不为日期类型";
					}
			  }
			  //生日赋值
			  if("0".equals(tInsuredIDType)){
				  //通过证件类型计算出生日期
				  if(tInsuredBirthday==null||"".equals(tInsuredBirthday)||"null".equals(tInsuredBirthday)){
					  tInsuredBirthday =PubFun.getBirthdayFromId(tInsuredIDNo);
				  }
			  }
			  //如录入日期格式不是“YYYY-MM-DD”时 补齐日期
			  if(tInsuredBirthday!=null){
			  String Date[]=null;
			  Date =tInsuredBirthday.split("-");
			  if(Date.length==2){
				  //logger.debug("dfghjkl");
				  tInsuredBirthday=String.valueOf(tInsuredBirthday+"-01");
				  //tAppntBirthday=tAppntBirthday+"-01";
			  }
		     }
			Birthday.setText(tInsuredBirthday);
			IDNo.setText(tInsuredIDNo);
			ElementLis NativePlace = new ElementLis("NativePlace");
			NativePlace.setText(tLBPOInsuredSchema.getNativePlace());
			ElementLis IDEndDate = new ElementLis("IDEndDate");
			String tIDEndDate = tLBPOInsuredSchema.getIDExpDate();
			if(tIDEndDate!=null){
				String Date[]=null;
				Date =tIDEndDate.split("-");
				if(Date.length==2){
					//logger.debug("dfghjkl");
					tIDEndDate=String.valueOf(tIDEndDate+"-01");
					//tAppntBirthday=tAppntBirthday+"-01";
				}
			}
			IDEndDate.setText(tIDEndDate);
			ElementLis SocialInsuFlag = new ElementLis("SocialInsuFlag");
			SocialInsuFlag.setText(tLBPOInsuredSchema.getSocialInsuFlag());
			ElementLis RgtAddress = new ElementLis("RgtAddress");
			RgtAddress.setText(tLBPOInsuredSchema.getRgtAddress());
			ElementLis Marriage = new ElementLis("Marriage");
			Marriage.setText(tLBPOInsuredSchema.getMarriage());
			ElementLis HomeAddress = new ElementLis("HomeAddress");
			HomeAddress.setText(tLBPOInsuredSchema.getHomeAddress());
			ElementLis HomeZipCode = new ElementLis("HomeZipCode");
			HomeZipCode.setText(tLBPOInsuredSchema.getHomeZipCode());
			ElementLis Phone = new ElementLis("Phone");
			String tFirstChooseInsuredPhone =tLBPOInsuredSchema.getMobile();
			String tFormatIF="";
			if(tFirstChooseInsuredPhone!=null){
				if(tFirstChooseInsuredPhone.indexOf("转")!=-1){
					tFirstChooseInsuredPhone=StrTool.replace(tFirstChooseInsuredPhone, "转", "-");
					String tFirstPhone[]=tFirstChooseInsuredPhone.split("-");
					tFormatIF =tFirstPhone[0].replaceAll("[^0-9]", "")+"-"+tFirstPhone[1].replaceAll("[^0-9]", "");
				}else{
					tFormatIF =tFirstChooseInsuredPhone.replaceAll("[^0-9]", "");
				}
			}else{
				tFormatIF=tFirstChooseInsuredPhone;
			}
			//Phone.setText(tLBPOInsuredSchema.getMobile());
			Phone.setText(tFormatIF);
			ElementLis Phone2 = new ElementLis("Phone2");
			String tOtherChooseInsuredPhone =tLBPOInsuredSchema.getCompanyPhone();
			String tFormatIO="";
			if(tOtherChooseInsuredPhone!=null){
				if(tOtherChooseInsuredPhone.indexOf("转")!=-1){
					tOtherChooseInsuredPhone=StrTool.replace(tOtherChooseInsuredPhone, "转", "-");
					String tFirstPhone[]=tOtherChooseInsuredPhone.split("-");
					tFormatIO =tFirstPhone[0].replaceAll("[^0-9]", "")+"-"+tFirstPhone[1].replaceAll("[^0-9]", "");
				}else{
					tFormatIO =tOtherChooseInsuredPhone.replaceAll("[^0-9]", "");
				}
			}else{
				tFormatIO=tOtherChooseInsuredPhone;
			}
			Phone2.setText(tFormatIO);
			ElementLis GrpName = new ElementLis("GrpName");
			GrpName.setText(tLBPOInsuredSchema.getCompanyAddress());
			ElementLis WorkType = new ElementLis("WorkType");
			WorkType.setText(tLBPOInsuredSchema.getWorkType());
			ElementLis PluralityType = new ElementLis("PluralityType");
			PluralityType.setText(tLBPOInsuredSchema.getPluralityType());
			ElementLis OccupationCode = new ElementLis("OccupationCode");
			OccupationCode.setText(tLBPOInsuredSchema.getOccupationCode());
			LBPOInsuredDes.addContent(RelationToMainInsured);
			LBPOInsuredDes.addContent(Name);
			LBPOInsuredDes.addContent(Sex);
			LBPOInsuredDes.addContent(Birthday);
			LBPOInsuredDes.addContent(RelationToAppnt);
			LBPOInsuredDes.addContent(IDType);
			LBPOInsuredDes.addContent(IDNo);
			LBPOInsuredDes.addContent(IDEndDate);
			LBPOInsuredDes.addContent(NativePlace);
			LBPOInsuredDes.addContent(RgtAddress);
			LBPOInsuredDes.addContent(Marriage);
			LBPOInsuredDes.addContent(HomeAddress);
			LBPOInsuredDes.addContent(HomeZipCode);
			LBPOInsuredDes.addContent(Phone);
			LBPOInsuredDes.addContent(Phone2);
			LBPOInsuredDes.addContent(GrpName);
			LBPOInsuredDes.addContent(SocialInsuFlag);
			LBPOInsuredDes.addContent(WorkType);
			LBPOInsuredDes.addContent(PluralityType);
			LBPOInsuredDes.addContent(OccupationCode);
			/**被保人险种信息*/
			ElementLis LCDealTypeSet = new ElementLis("LCDealTypeSet");
			LCInsured.addContent(LCDealTypeSet);
			LBPOPolDB ttLBPOPolDB = new LBPOPolDB();
			LBPOPolSet ttLBPOPolSet = new LBPOPolSet();
			LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
			//modify by ln 只查该被保人下主险信息
			String tMainPolAllSql = "select * from lbpopol"
				+" where contno ='"+"?contno7?"+"' and inputno='"+"?inputno7?"+"' and insuredno='"+"?insuredno?"+"' and polno=mainpolno";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(tMainPolAllSql);
			sqlbv12.put("contno7", mContNo);
			sqlbv12.put("inputno7", mInputTime);
			sqlbv12.put("insuredno", tLBPOInsuredSchema.getInsuredNo());
			ttLBPOPolSet = ttLBPOPolDB.executeQuery(sqlbv12);
			//去除掉所有必要信息为空的schema
			int PolSize=ttLBPOPolSet.size();
			for(int a=1;a<=PolSize;a++){
				LBPOPolSchema rLBPOPolSchema = new LBPOPolSchema();
				rLBPOPolSchema = ttLBPOPolSet.get(a);
				if(mDSPubCheckCol.ifRemove(rLBPOPolSchema, "03")){
					ttLBPOPolSet.remove(rLBPOPolSchema);
					PolSize--;
					a--;
				}
			}
			//注掉原来的逻辑   通过mainpolno去查询
			if(ttLBPOPolSet.size()>1){
				isOnePol=false;
			}
			else
				isOnePol=true;
//			String tMainPolNoSql = "select distinct mainpolno from lbpopol"
//				+" where contno ='"+mContNo+"' and inputno='"+mInputTime+"'";
//			SSRS tMainPolSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
//			tMainPolSSRS = tExeSQL.execSQL(tMainPolNoSql);
//			if(tMainPolSSRS.getMaxRow()>1){
//				isOnePol=false;
//			} else {
//				isOnePol=true;
//			}
			for (int j = 1; j <= ttLBPOPolSet.size(); j++) {
				tLBPOPolSchema = ttLBPOPolSet.get(j);
				//tongmeng 2009-02-23 modify
				//险种转换单独使用WBChangeRiskField类
				tWBChangeRiskField = new WBChangeRiskField();
				TransferData transTransferData = new TransferData();
				tWBChangeRiskField.changeRiskField(tLBPOPolSchema, transTransferData, "JM");
				if(!tLBPOPolSchema.getContNo().substring(0,4).equals("8616"))
				{
					tLBPOPolSchema.setPayEndYear(tLBPOPolSchema.getPayYears());
				}
				tTransferData=mDSPubCheckCol.verifyValue(tLBPOPolSchema, "02");
				
				Vector tV6 = tTransferData.getValueNames();
				for(int a=0;a<tV6.size();a++){
					mSucceFlag="1";
					ElementLis Error = new ElementLis("Error");
					ElementLis ErrType = new ElementLis("ErrType");
					ElementLis ErrTag = new ElementLis("ErrTag");
					ElementLis ErrCode  = new ElementLis("ErrCode");
					ElementLis ErrMessage  = new ElementLis("ErrMessage");
					ErrType.setText("1");
					ErrTag.setText((String)tV6.get(a));
					ErrCode.setText((String)ttTransferData.getValueByName(tV6.get(a)));
					ErrMessage.setText((String)tTransferData.getValueByName((String)tV6.get(a)));
					Errors.addContent(Error);
					Error.addContent(ErrType);
					Error.addContent(ErrTag);
					Error.addContent(ErrCode);
					Error.addContent(ErrMessage);
				}
				if(StrTool.cTrim(tLBPOPolSchema.getGetYearFlag()).equals("")
						&&StrTool.cTrim(tLBPOPolSchema.getGetYear()).equals("")
						&&StrTool.cTrim(tLBPOPolSchema.getGetLimit()).equals("")
						&&StrTool.cTrim(tLBPOPolSchema.getStandbyFlag3()).equals("")
						&&StrTool.cTrim(tLBPOPolSchema.getLiveGetMode()).equals("")
						&&StrTool.cTrim(tLBPOPolSchema.getBonusGetMode()).equals("")){
					continue;
				}
				ElementLis LCDealType = new ElementLis("LCDealType");
				LCDealTypeSet.addContent(LCDealType);
				ElementLis TypeToRisk = new ElementLis("TypeToRisk");
				if (isOnePol) {
					TypeToRisk.setText("-1");
				} else {
					TypeToRisk.setText(String.valueOf(j));
				}
				ElementLis GetYearFlag = new ElementLis("GetYearFlag");
				GetYearFlag.setText(tLBPOPolSchema.getGetYearFlag());
				ElementLis GetYear = new ElementLis("GetYear");
				GetYear.setText(String.valueOf(tLBPOPolSchema.getGetYear()));
				ElementLis GetYears = new ElementLis("GetYears");
				GetYears.setText(tLBPOPolSchema.getGetLimit());
				ElementLis GetDutyKind = new ElementLis("GetDutyKind");
				GetDutyKind.setText(tLBPOPolSchema.getStandbyFlag3());
				ElementLis LiveGetMode = new ElementLis("LiveGetMode");
				LiveGetMode.setText(tLBPOPolSchema.getLiveGetMode());
				ElementLis BonusGetMode = new ElementLis("BonusGetMode");
				BonusGetMode.setText(tLBPOPolSchema.getBonusGetMode());
				LCDealType.addContent(TypeToRisk);
				LCDealType.addContent(GetYearFlag);
				LCDealType.addContent(GetYear);
				LCDealType.addContent(GetYears);
				LCDealType.addContent(GetDutyKind);
				LCDealType.addContent(LiveGetMode);
				LCDealType.addContent(BonusGetMode);
			}
			/**个人投资计划*/
			ElementLis LCInvest = new ElementLis("LCInvest");
			LCInsured.addContent(LCInvest);
			LBPOPerInvestPlanDB tLBPOPerInvestPlanDB = new LBPOPerInvestPlanDB();
			LBPOPerInvestPlanSet tLBPOPerInvestPlanSet = new LBPOPerInvestPlanSet();
			tLBPOPerInvestPlanDB.setInputNo(mInputTime);
			tLBPOPerInvestPlanDB
					.setInsuredNo(tLBPOInsuredSchema.getInsuredNo());
			tLBPOPerInvestPlanSet = tLBPOPerInvestPlanDB.query();
			//去除掉所有必要信息都为空的schema
			int PerInvestSize = tLBPOPerInvestPlanSet.size();
			for(int a=1;a<=PerInvestSize;a++){
				LBPOPerInvestPlanSchema rLBPOPerInvestPlanSchema = new LBPOPerInvestPlanSchema();
				rLBPOPerInvestPlanSchema = tLBPOPerInvestPlanSet.get(a);
				if(mDSPubCheckCol.ifRemove(rLBPOPerInvestPlanSchema, "03")){
					tLBPOPerInvestPlanSet.remove(rLBPOPerInvestPlanSchema);
					PerInvestSize--;
					a--;
				}
			}
			if (tLBPOPerInvestPlanSet == null
					|| tLBPOPerInvestPlanSet.size() == 0) {
				ElementLis InvestFlag = new ElementLis("InvestFlag");
				InvestFlag.setText("0");
				LCInvest.addContent(InvestFlag);
			} else {
				ElementLis InvestFlag = new ElementLis("InvestFlag");
				InvestFlag.setText("1");
				LCInvest.addContent(InvestFlag);
				ElementLis InvestSet = new ElementLis("InvestSet");
				LCInvest.addContent(InvestSet);

				for (int k = 1; k <= tLBPOPerInvestPlanSet.size(); k++) {
//					LBPOPerInvestPlanSchema tLBPOPerInvestPlanSchema = new LBPOPerInvestPlanSchema();
//					tLBPOPerInvestPlanSchema = tLBPOPerInvestPlanSet.get(k);
					ElementLis EffTime = new ElementLis("EffTime");
					InvestSet.setText("1");
					LCInvest.addContent(EffTime);
					ElementLis Invest = new ElementLis("Invest");
					InvestSet.addContent(Invest);
					ElementLis AccToRisk = new ElementLis("AccToRisk");
					ElementLis AccNo = new ElementLis("AccNo");
					ElementLis AccLot = new ElementLis("AccLot");
					ElementLis AccMoney = new ElementLis("AccMoney");
					Invest.addContent(AccToRisk);
					Invest.addContent(AccNo);
					Invest.addContent(AccLot);
					Invest.addContent(AccMoney);
				}
			}

			ElementLis LCAddit = new ElementLis("LCAddit");
			LCInsured.addContent(LCAddit);
			if (tLBPOPerInvestPlanSet == null
					|| tLBPOPerInvestPlanSet.size() == 0) {
				ElementLis AdditFlag = new ElementLis("AdditFlag");
				AdditFlag.setText("0");
				LCAddit.addContent(AdditFlag);
			} else {
				ElementLis AdditFlag = new ElementLis("AdditFlag");
				AdditFlag.setText("1");
				LCAddit.addContent(AdditFlag);
				ElementLis AdditSet = new ElementLis("AdditSet");
				LCAddit.addContent(AdditSet);
				for (int m = 1; m <= tLBPOPerInvestPlanSet.size(); m++) {
					ElementLis Addit = new ElementLis("Addit");
					AdditSet.addContent(Addit);
					ElementLis AdditToRisk = new ElementLis("AdditToRisk");
					ElementLis AdditPrem = new ElementLis("AdditPrem");
					ElementLis AdditAmnt = new ElementLis("AdditAmnt");
					ElementLis AddAmnt = new ElementLis("AddAmnt");
					Addit.addContent(AdditToRisk);
					Addit.addContent(AdditPrem);
					Addit.addContent(AdditAmnt);
					Addit.addContent(AddAmnt);
				}
			}
			/**受益人信息*/
			ElementLis LCBnfs = new ElementLis("LCBnfs");
			LCInsured.addContent(LCBnfs);
			LBPOBnfDB tLBPOBnfDB = new LBPOBnfDB();
			LBPOBnfSet tLBPOBnfSet = new LBPOBnfSet();
			tLBPOBnfDB.setInsuredNo(tLBPOInsuredSet.get(i).getInsuredNo());
			tLBPOBnfDB.setInputNo(mInputTime);
			tLBPOBnfSet = tLBPOBnfDB.query();
			double cBnfLot=0;
			//去除掉所有必要信息都为空的shcema
			int bnfSize = tLBPOBnfSet.size();
			for(int a=1;a<=bnfSize;a++){
				LBPOBnfSchema rLBPOBnfSchema = new LBPOBnfSchema();
				rLBPOBnfSchema = tLBPOBnfSet.get(a);
				if(mDSPubCheckCol.ifRemove(rLBPOBnfSchema, "03")){
					tLBPOBnfSet.remove(rLBPOBnfSchema);
					bnfSize--;
					a--;
				}else{
					//如果有信息，看是否某些字段中为'法定' 如果是法定 则按空处理，移除
					for(int n=0;n<rLBPOBnfSchema.getFieldCount();n++){
//						logger.debug("字段名称："+rLBPOBnfSchema.getFieldName(n));
//						logger.debug("字段值："+rLBPOBnfSchema.getV(n));
						if("法定".equals(rLBPOBnfSchema.getV(n))){
							//如果某个字段为‘法定’视为空！
							tLBPOBnfSet.remove(rLBPOBnfSchema);
							bnfSize--;
							a--;
							break;
						}
					}
				}
			}
//			判断满期、身故的受益人数 比便下面判断是否平均分配收益比例
			//当有两条或两条以上的记录时才做此校验
			int tMQ=0;
			int tSG=0;
			for(int k=1;k<=tLBPOBnfSet.size();k++){
				//循环查看并判断赋值
				String tType =tLBPOBnfSet.get(k).getBnfType();
				if("0".equals(tType)){
					tMQ=tMQ+1;
				}
				if("1".equals(tType)){
					tSG=tSG+1;
				}
				
			}
			for (int x = 1; x <= tLBPOBnfSet.size(); x++) {
				LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
				tLBPOBnfSchema = tLBPOBnfSet.get(x);
				tTransferData=mDSPubCheckCol.verifyValue(tLBPOBnfSchema, "02");
				Vector tV7 = tTransferData.getValueNames();
				for(int a=0;a<tV7.size();a++){
					mSucceFlag="1";
					ElementLis Error = new ElementLis("Error");
					ElementLis ErrType = new ElementLis("ErrType");
					ElementLis ErrTag = new ElementLis("ErrTag");
					ElementLis ErrCode  = new ElementLis("ErrCode");
					ElementLis ErrMessage  = new ElementLis("ErrMessage");
					ErrType.setText("1");
					ErrTag.setText((String)tV7.get(a));
					ErrCode.setText((String)ttTransferData.getValueByName(tV7.get(a)));
					ErrMessage.setText((String)tTransferData.getValueByName((String)tV7.get(a)));
					Errors.addContent(Error);
					Error.addContent(ErrType);
					Error.addContent(ErrTag);
					Error.addContent(ErrCode);
					Error.addContent(ErrMessage);
				}
				ElementLis LCBnf = new ElementLis("LCBnf");
				LCBnfs.addContent(LCBnf);
				ElementLis BnfToRisk = new ElementLis("BnfToRisk");
				if (isOnePol) {
					BnfToRisk.setText("-1");
				} else {
					BnfToRisk.setText((String.valueOf(x)));
				}
				String tPolType =tLBPOBnfSchema.getContNo().substring(0, 4);
				ElementLis BnfType = new ElementLis("BnfType");
				BnfType.setText(tLBPOBnfSchema.getBnfType());
				ElementLis NAME = new ElementLis("Name");
				NAME.setText(tLBPOBnfSchema.getName());
//				NAME.setText("王金平");
				ElementLis IDTYPE = new ElementLis("IDType");
				String tBnfIDType =tLBPOBnfSchema.getIDType();
				String tBnfBirthday =tLBPOBnfSchema.getBirthday();
				String tBnfIDNo =tLBPOBnfSchema.getIDNo();
				if("出生日期".equals(tBnfIDType)||(tBnfBirthday!=null&&tBnfBirthday.equals(tBnfIDType))
						||"无证件".equals(tBnfIDType)){
					tBnfIDType ="9";
				}
				if(tBnfIDType==null||"null".equals(tBnfIDType)||"".equals(tBnfIDType)){
					if(tBnfIDNo!=null&&(tBnfIDNo.length()==15||tBnfIDNo.length()==18)){
						tBnfIDType="0";
					}else{
						tBnfIDType="8";
					}
				}
				//简易保单：证件号码”中有“-”，则“证件类型”按“9-无证件”导入
				if("8616".equals(tPolType)){
					if(tBnfIDNo!=null&&tBnfIDNo.indexOf("-")!=-1){
						tBnfIDType="9";
					}
				}
				//IDTYPE.setText(tLBPOBnfSchema.getIDType());
				IDTYPE.setText(tBnfIDType);
				//IDNo 如填写为20070503或2007年5月3日，则统一录入成“2007-05-03”的形式
				ElementLis IDNO = new ElementLis("IDNo");
//				ExeSQL tExeSQL =new ExeSQL();
				String ttBnfIDNo =tBnfIDNo;
				try{
					if(ttBnfIDNo!=null&&ttBnfIDNo.length()==8){
						SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
						sqlbv13.sql(" select to_char(to_date('"+"?date1?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual");
						sqlbv13.put("date1", tBnfIDNo);
						ttBnfIDNo =tExeSQL.getOneValue(sqlbv13);
						if(!"".equals(ttBnfIDNo)){
							tBnfIDNo=ttBnfIDNo;
						}
					}
					if(ttBnfIDNo.indexOf("年")!=-1&&ttBnfIDNo.indexOf("月")!=-1&&ttBnfIDNo.indexOf("日")!=-1){
						ttBnfIDNo=StrTool.replace(ttBnfIDNo, "年", "-");
						ttBnfIDNo=StrTool.replace(ttBnfIDNo, "月", "-");
						ttBnfIDNo=StrTool.replace(ttBnfIDNo, "日", "");
						SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
						sqlbv14.sql(" select to_char(to_date('"+"?date2?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual");
						sqlbv14.put("date2", ttBnfIDNo);
						ttBnfIDNo =tExeSQL.getOneValue(sqlbv14);
						if(!"".equals(ttBnfIDNo)){
							tBnfIDNo=ttBnfIDNo;
						}
					}
				}catch(Exception ex){}
//				IDNO.setText(tLBPOBnfSchema.getIDNo());
				IDNO.setText(tBnfIDNo);
				ElementLis RelationToInsured = new ElementLis(
						"RelationToInsured");
				RelationToInsured
						.setText(tLBPOBnfSchema.getRelationToInsured());
				ElementLis BnfLot = new ElementLis("BnfLot");
				String tBnfLot = tLBPOBnfSchema.getBnfLot();
				String tBnfGrade = tLBPOBnfSchema.getBnfGrade();
				String tBnfType = tLBPOBnfSchema.getBnfType();
				if("0".equals(tBnfType)){
					//生存
					if(tMQ==1&&(tBnfLot==null||"".equals(tBnfLot))){
						//受益人为一人 而且收益比例为空 按1导入
						tBnfLot="1";
					}
					if(tMQ==2&&(tBnfLot==null||"".equals(tBnfLot))
							&&(tBnfGrade==null||"".equals(tBnfGrade))){
						//如果受益人为两人 且收益顺序、收益比例都为空 每人50%
						tBnfLot="0.5";
					}
					if(tMQ==2&&(tBnfLot==null||"".equals(tBnfLot))){
						String tBnfGrade1=tLBPOBnfSet.get(1).getBnfGrade();
						if(tBnfGrade1==null) tBnfGrade1="";
						if(tBnfGrade1.equals(tLBPOBnfSet.get(2).getBnfGrade())){
							tBnfLot = "0.5";
						}
					}
				}
				
				
				if("1".equals(tBnfType)){
					if(tSG==1&&(tBnfLot==null||"".equals(tBnfLot))){
						//受益人为一人 而且收益比例为空 按1导入
						tBnfLot="1";
					}
					if(tSG==2&&(tBnfLot==null||"".equals(tBnfLot))
							&&(tBnfGrade==null||"".equals(tBnfGrade))){
						//如果受益人为两人 且收益顺序、收益比例都为空 每人50%
						tBnfLot="0.5";
					}
					if(tSG==2&&(tBnfLot==null||"".equals(tBnfLot))){
						String tBnfGrade1=tLBPOBnfSet.get(1).getBnfGrade();
						if(tBnfGrade1==null) tBnfGrade1="";
						if(tBnfGrade1.equals(tLBPOBnfSet.get(2).getBnfGrade())){
							tBnfLot = "0.5";
						}
					}
				}
				//将受益比例转换成小数
				if(tBnfLot.indexOf("%")!=-1){
					tBnfLot =String.valueOf(Double.parseDouble(tBnfLot.replaceAll("[^0-9]", ""))/100);
				}
				BnfLot.setText(tBnfLot);
				ElementLis BnfGrade = new ElementLis("BnfGrade");
				
				//tongmeng 2009-03-21 add
				//银代身故受益人,收益顺序直接转为1
				if(this.mContNo.substring(0,4).equals("8635")||this.mContNo.substring(0,4).equals("8625")
						||this.mContNo.substring(0,4).equals("3110"))
				{
					if(tLBPOBnfSchema.getBnfType()!=null&&tLBPOBnfSchema.getBnfType().equals("1"))
					{
						if(tLBPOBnfSchema.getBnfGrade()==null||tLBPOBnfSchema.getBnfGrade().equals(""))
						{
							BnfGrade.setText("1");
						}
					}
				}
				else
				{
					BnfGrade.setText(tLBPOBnfSchema.getBnfGrade());
				}
				ElementLis Address = new ElementLis("Address");
				//tongmeng 2009-02-12 add
				//记录受益人地址
				String tBnfAddress=tLBPOBnfSchema.getBnfAddress();
				/* 如录入为“空”，且受益人资料其它项有内容时，后台对生存受益人与投、被保险人（姓名、证件号码）进行校验。
				   * 如判断为投保人，则按序号“1”直接导入；如判断为被保人，则按序号“2”导入，无需给出问题件；
				   * 如判断为非投保人或非被保人时，按“空”导入，并给出问题件。如录入为“空”，且受益人资料其它项均为“空”，按“空”导入，
				   * 无需给出问题件
				   */
					if(tBnfAddress==null||"".equals(tBnfAddress)){
						//判断是否同投保人姓名证件相同
						if(tLBPOBnfSchema.getName()!=null&&tLBPOBnfSchema.getName().equals(tLBPOInsuredSet.get(i).getName())&&tBnfIDNo!=null&&tBnfIDNo.equals(tLBPOInsuredSet.get(i).getIDNo())){
							tBnfAddress="2";
						}
						if(tLBPOBnfSchema.getName()!=null&&tLBPOBnfSchema.getName().equals(ttLBPOAppntSchema.getAppntName())&&tBnfIDNo!=null&&tBnfIDNo.equals(ttLBPOAppntSchema.getIDNo())){
							tBnfAddress="1";
						}
				}
				Address.setText(tBnfAddress);
				LCBnf.addContent(BnfToRisk);
				LCBnf.addContent(BnfType);
				LCBnf.addContent(NAME);
				LCBnf.addContent(IDTYPE);
				LCBnf.addContent(IDNO);
				LCBnf.addContent(RelationToInsured);
				LCBnf.addContent(BnfLot);
				LCBnf.addContent(BnfGrade);
				LCBnf.addContent(Address);
			}
			logger.debug("lcrisks");
			ElementLis LCRisks = new ElementLis("LCRisks");
			LCInsured.addContent(LCRisks);
//			if(("1").equals(tLBPOInsuredSchema.getSequenceNo())&&("1").equals(tReleaseFlag)){
//				ElementLis Risks = new ElementLis("Risks");
//				LCRisks.addContent(Risks);
//				ElementLis Risk = new ElementLis("Risk");
//				Risks.addContent(Risk);
//				ElementLis RiskCode = new ElementLis("RiskCode");
//				ElementLis RiskName = new ElementLis("RiskName");
//				ElementLis Prem = new ElementLis("Prem");
//				ElementLis Mult = new ElementLis("Mult");
//				ElementLis Amnt = new ElementLis("Amnt");
//				ElementLis Level = new ElementLis("Level");
//				ElementLis PayEndYear = new ElementLis("PayEndYear");
//				ElementLis InsuYear = new ElementLis("InsuYear");
//				ElementLis REMARK = new ElementLis("Remark");
//				RiskCode.setText("121301");

//				Prem.setText(ttLBPOContSchema.getPrem());
//				Amnt.setText(ttLBPOContSchema.getAmnt());
//				
//				Risk.addContent(RiskCode);
//				Risk.addContent(RiskName);
//				Risk.addContent(Prem);
//				Risk.addContent(Mult);
//				Risk.addContent(Amnt);
//				Risk.addContent(Level);
//				Risk.addContent(PayEndYear);
//				Risk.addContent(InsuYear);
//				Risk.addContent(REMARK);
//			}
			
			for(int y = 1; y<=ttLBPOPolSet.size();y++){
				LBPOPolDB mLBPOPolDB = new LBPOPolDB();
				LBPOPolSet mLBPOPolSet = new LBPOPolSet();
				mLBPOPolDB.setInsuredNo(tLBPOInsuredSchema.getInsuredNo());
				mLBPOPolDB.setInputNo(mInputTime);
				mLBPOPolDB.setContNo(tLBPOInsuredSchema.getContNo());
				mLBPOPolDB.setMainPolNo(ttLBPOPolSet.get(y).getMainPolNo());
				mLBPOPolSet = mLBPOPolDB.query();
				//去除掉所有必要信息都为空的schema
				int mLBPOPolSetSize = mLBPOPolSet.size();
				ElementLis Risks = new ElementLis("Risks");
				LCRisks.addContent(Risks);
				ElementLis RiskNo = new ElementLis("RiskNo");
				if (isOnePol) {
					RiskNo.setText("-1");
				} else {
					RiskNo.setText(String.valueOf(y));
				}
				Risks.addContent(RiskNo);
				for(int a=1;a<=mLBPOPolSetSize;a++){
					LBPOPolSchema rLBPOPolSchema = new LBPOPolSchema();
					rLBPOPolSchema = mLBPOPolSet.get(a);
					if(mDSPubCheckCol.ifRemove(rLBPOPolSchema, "03")){
						mLBPOPolSet.remove(rLBPOPolSchema);
						mLBPOPolSetSize--;
						a--;
					}
				}
				LBPOPolSchema tReleaseLBPOPolSchema =new LBPOPolSchema();
				for(int l=1;l<=mLBPOPolSet.size();l++){
					if("121301".equals(mLBPOPolSet.get(l).getRiskCode())){
						tReleaseLBPOPolSchema =mLBPOPolSet.get(l);
						tReleaseFlag="1";
					}
				}
				for(int h=1;h<=mLBPOPolSet.size();h++){
					if(!"121301".equals(mLBPOPolSet.get(h).getRiskCode())){
						LBPOPolSchema mmLBPOPolSchema = new LBPOPolSchema();
						mmLBPOPolSchema = mLBPOPolSet.get(h);
						//tongmeng 2009-02-23 modify
						//险种转换单独使用WBChangeRiskField类
						tWBChangeRiskField = new WBChangeRiskField();
						TransferData transTransferData = new TransferData();
						tWBChangeRiskField.changeRiskField(mmLBPOPolSchema, transTransferData, "JM");
						LBPOPolDB mmLBPOPolDB = new LBPOPolDB();
						LBPOPolSet mmLBPOPolSet = new LBPOPolSet();
						
						//去除掉所有信息都为空的schema
						int mmPolSize = mmLBPOPolSet.size();
						for(int a=1;a<=mmPolSize;a++){
							LBPOPolSchema rLBPOPolSchema = new LBPOPolSchema();
							rLBPOPolSchema = mmLBPOPolSet.get(a);
							if(mDSPubCheckCol.ifRemove(rLBPOPolSchema, "03")){
								mmLBPOPolSet.remove(rLBPOPolSchema);
								mmPolSize--;
								a--;
							}
						}
						if(!mmLBPOPolSchema.getContNo().substring(0,4).equals("8616")) {
							mmLBPOPolSchema.setPayEndYear(mmLBPOPolSchema.getPayYears());
						}
							/*tTransferData=mDSPubCheckCol.verifyValue(mmLBPOPolSchema, "02");
							Vector tV7 = tTransferData.getValueNames();
							for(int a=0;a<tV7.size();a++){
								mSucceFlag="1";
								ElementLis Error = new ElementLis("Error");
								ElementLis ErrType = new ElementLis("ErrType");
								ElementLis ErrTag = new ElementLis("ErrTag");
								ElementLis ErrCode  = new ElementLis("ErrCode");
								ElementLis ErrMessage  = new ElementLis("ErrMessage");
								ErrType.setText("1");
								ErrTag.setText((String)tV7.get(a));
								ErrCode.setText((String)ttTransferData.getValueByName(tV7.get(a)));
								ErrMessage.setText((String)tTransferData.getValueByName((String)tV7.get(a)));
								Errors.addContent(Error);
								Error.addContent(ErrType);
								Error.addContent(ErrTag);
								Error.addContent(ErrCode);
								Error.addContent(ErrMessage);
							}*/
							//tongmeng 2009-02-23 modify
							//险种转换单独使用WBChangeRiskField类
						WBChangeRiskField ttWBChangeRiskField = new WBChangeRiskField();
						TransferData ttransTransferData = new TransferData();
						ttWBChangeRiskField.changeRiskField(mmLBPOPolSchema, ttransTransferData, "JM");
						LMRiskDB mLMRiskDB = new LMRiskDB();
						LMRiskSet mLMRiskSet = new LMRiskSet();
						if(!(mmLBPOPolSchema.getRiskCode()==null||"".equals(mmLBPOPolSchema.getRiskCode()))){
							mLMRiskDB.setRiskCode(mmLBPOPolSchema.getRiskCode());
							mLMRiskSet = mLMRiskDB.query();
						}
						ElementLis Risk = new ElementLis("Risk");
						Risks.addContent(Risk);
						ElementLis RiskCode = new ElementLis("RiskCode");
						ElementLis RiskName = new ElementLis("RiskName");
						ElementLis Prem = new ElementLis("Prem");
						ElementLis Mult = new ElementLis("Mult");
						ElementLis Amnt = new ElementLis("Amnt");
						ElementLis Level = new ElementLis("Level");
						ElementLis PayEndYear = new ElementLis("PayEndYear");
						ElementLis InsuYear = new ElementLis("InsuYear");
						ElementLis REMARK = new ElementLis("Remark");
						RiskCode.setText(mmLBPOPolSchema.getRiskCode());
						//tongmeng 2009-03-21 modify
						//如果险种编码为空,不设置险种名称
						if(mmLBPOPolSchema.getRiskCode()==null
								||mmLBPOPolSchema.getRiskCode().equals("")
								||mmLBPOPolSchema.getRiskCode().equals("null")) {
							RiskName.setText("");
						} else {
							for(int a=1;a<=mLMRiskSet.size();a++){
								RiskName.setText(mLMRiskSet.get(a).getRiskName());
							}
						}
						Prem.setText(String.valueOf(mmLBPOPolSchema.getPrem()));
						Mult.setText(String.valueOf(mmLBPOPolSchema.getMult()));
						Amnt.setText(String.valueOf(mmLBPOPolSchema.getAmnt()));
						Level.setText(mmLBPOPolSchema.getStandbyFlag1());
						//tongmeng 2009-02-23 modify
						//PayEndYear 在WBChangeRiskField 中转换
						String tPayEndYear = mmLBPOPolSchema.getPayEndYear();
						try{
							tPayEndYear=StrTool.replace(tPayEndYear, "A", "岁");
							tPayEndYear=StrTool.replace(tPayEndYear, "Y", "年");
						}catch(Exception ex){}
						PayEndYear.setText(tPayEndYear);
						InsuYear.setText(mmLBPOPolSchema.getInsuYear());
						REMARK.setText(mmLBPOPolSchema.getRemark());
						Risk.addContent(RiskCode);
						Risk.addContent(RiskName);
						Risk.addContent(Prem);
						Risk.addContent(Mult);
						Risk.addContent(Amnt);
						Risk.addContent(Level);
						Risk.addContent(PayEndYear);
						Risk.addContent(InsuYear);
						Risk.addContent(REMARK);
						if(("1").equals(tReleaseFlag)&&y==1){
							//LCRisks.addContent(Risks);
							ElementLis Riska = new ElementLis("Risk");
							Risks.addContent(Riska);
							ElementLis RiskCodea = new ElementLis("RiskCode");
							ElementLis RiskNamea = new ElementLis("RiskName");
							ElementLis Prema = new ElementLis("Prem");
							ElementLis Multa = new ElementLis("Mult");
							ElementLis Amnta = new ElementLis("Amnt");
							ElementLis Levela = new ElementLis("Level");
							ElementLis PayEndYeara = new ElementLis("PayEndYear");
							ElementLis InsuYeara = new ElementLis("InsuYear");
							ElementLis REMARKa = new ElementLis("Remark");
							RiskCodea.setText("121301");
							RiskNamea.setText("附加豁免保费定期寿险");
							Prema.setText(tReleaseLBPOPolSchema.getPrem());
							Amnta.setText(tReleaseLBPOPolSchema.getAmnt());
							Levela.setText(tReleaseLBPOPolSchema.getStandbyFlag1());
							PayEndYeara.setText(tReleaseLBPOPolSchema.getPayEndYear());
							InsuYeara.setText(tReleaseLBPOPolSchema.getInsuYear());
							REMARKa.setText(tReleaseLBPOPolSchema.getRemark());
							
							Riska.addContent(RiskCodea);
							Riska.addContent(RiskNamea);
							Riska.addContent(Prema);
							Riska.addContent(Multa);
							Riska.addContent(Amnta);
							Riska.addContent(Levela);
							Riska.addContent(PayEndYeara);
							Riska.addContent(InsuYeara);
							Riska.addContent(REMARKa);
						}
					}
				}
			}
		}
		SuccFlag.setText(mSucceFlag);
		BaseInfo.addContent(SuccFlag);
		BaseInfo.addContent(Errors);
		return doc;
	}

	public static void outputDocumentToFile(String pathfilename, Document doc) {
		// setup this like outputDocument
		FileWriter writer = null;
		XMLOutputter outputter = null;
		try {
			outputter = new XMLOutputter("  ", true, "GBK");
			// outputter.setIndent(true);
			// outputter.setNewlines(true);
			// output to a file
			String str = pathfilename + ".xml";
			writer = new java.io.FileWriter(str);
			outputter.output(doc, writer);

		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	private boolean InsertBlob(Document Doc, Connection conn) {
		// 产生流水号
		try {

			String tLimit = PubFun.getNoLimit(mManageCom);
			String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

			String szSQL = "INSERT INTO BPOPolData(BussNo, BussNoType, PolData,SerialNo, Operator, MakeDate,MakeTime) VALUES('"
					+ mContNo
					+ "', 'JM', "
					+ " empty_blob(),'"
					+ serNo
					+ "', '"
					+ mOperater
					+ "', '"
					+ theCurrentDate
					+ "','"
					+ theCurrentTime + "')";
//			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
//			sqlbv15.sql(szSQL);
//			sqlbv15.put("v1", mContNo);
//			sqlbv15.put("v2", serNo);
//			sqlbv15.put("v3", mOperater);
//			sqlbv15.put("v4", theCurrentDate);
//			sqlbv15.put("v5", theCurrentTime);
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn)) {
					conn.rollback();
					conn.close();
					// @@错误处理
					CError.buildErr(this, "外包投保数据表插入失败！" + szSQL);
					return false;
				}
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL, conn)) {
					conn.rollback();
					conn.close();
					// @@错误处理
					CError.buildErr(this, "外包投保数据表插入失败！" + szSQL);
					return false;
				}		
			}
			szSQL = " and BussNo = '" + mContNo + "' and BussNoType = 'JM'";
//			SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
//			sqlbv33.sql(szSQL);
//			sqlbv33.put("f1", mContNo);
			XMLOutputter outputter = new XMLOutputter("  ", true, "GBK");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			outputter.output(Doc, baos);
			baos.close();
			InputStream ins = new ByteArrayInputStream(baos.toByteArray());
			
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.UpdateBlob(Doc, "BPOPolData", "PolData", szSQL,
						conn)) {
					conn.rollback();
					conn.close();
					CError.buildErr(this, "外包投保数据表修改数据失败！" + szSQL);
					return false;
				}			
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.UpdateBlob(Doc, "BPOPolData", "PolData", szSQL,
						conn)) {
					conn.rollback();
					conn.close();
					CError.buildErr(this, "外包投保数据表修改数据失败！" + szSQL);
					return false;
				}
							
			}
		} catch (Exception ex) {
			try {
				conn.rollback();
				conn.close();
			} catch (Exception ex1) {
			}
			ex.printStackTrace();
			CError.buildErr(this, "修改数据失败！" + ex.toString());
			return false;

		}
		return true;
	}

	private boolean prepareTransferData() {

		return true;
	}

	private boolean prepareOutputData() {
		mVData.clear();
		mVData.addElement(map);
		return true;
	}
	
	private Schema filtrateData(Schema tSchema){
		
		
		
		return tSchema;
	}
	
	private SchemaSet filtrateData(SchemaSet tSchemaSet){
		LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
		LBPODataDictionaryDB tLBPODataDictionaryDB = new LBPODataDictionaryDB();
		LBPODataDictionarySet tLBPODataDictionarySet = new LBPODataDictionarySet();
		
		String tClassName = tSchemaSet.getClass().getName();
		String tCheckName = tClassName.substring(tClassName.lastIndexOf(".") + 1);
		// 获得需要校验的数据表名
		tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Set"));
		String tSql = "select * from LBPODataDictionary where checktype='01' and tablename='"+"?tablename?"+"'";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("tablename", tCheckName);
		tLBPODataDictionarySet=tLBPODataDictionaryDB.executeQuery(sqlbv16);
		if(tLBPODataDictionarySet.size()>1){
			CError.buildErr(this, "查询内部外包双岗校验字典表失败");
			return null;
		}
		tLBPODataDictionarySchema = tLBPODataDictionarySet.get(1);
		Hashtable tHashtable = new Hashtable();
		tHashtable.put(tLBPODataDictionarySchema.getContrasCol(), tLBPODataDictionarySchema.getMSG());
		for(int i=1;i<=tSchemaSet.size();i++){
//			for(int k=0;k<tS)
		}
		return tSchemaSet;
	}

	public static void main(String args[]) {
		String a="1/2//4/";
		String b[]=a.split("/");
		
//		int tAge = PubFun.calInterval("19830203","", "Y");
//		logger.debug("tAge:"+tAge);
//		String a="年";
//		String x=StrTool.replace(a, "至", "");
//		String y="年 ";
//		
//		if(y.equals(a)){
//			logger.debug("adfafsd");
//		}
//		String tInsuYear ="1212ee转22拉拉";
//		tInsuYear.replaceAll("[^0-9]", "");
//		logger.debug("Result："+tInsuYear.replaceAll("[^0-9]", ""));
		String[] tAllData = {
				
				"86110200100301"
		} ;
		
		for(int i=0;i<tAllData.length;i++)
		{
		Connection conn = DBConnPool.getConnection();
		try {
			//
//			try{
//			Date tDate = null;
//			SimpleDateFormat df;
//			String tcheckDate = "20080213";
//			ExeSQL tExeSQL=new ExeSQL();
//			if (tcheckDate.indexOf("-") != -1) {
//				df = new SimpleDateFormat("yyyy-MM-dd");
//				tDate = df.parse(tcheckDate);
//				logger.debug("tDate:"+tDate);
//				String dateStr2=df.format(tDate);
//				String tSQL = "select to_char(to_date('"+tcheckDate+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual";
//				String tBirthday = tExeSQL.getOneValue(tSQL);
//				if(!dateStr2.equals(tBirthday)){
//					logger.debug("不行！");
//				}
//			}else{
//				df = new SimpleDateFormat("yyyy-MM-dd");
//				tDate = df.parse(tcheckDate);
//				String dateStr1 =df.format(tDate);
//				String tSQL="select to_char(to_date('"+tcheckDate+"','yyyymmdd'),'yyyymmdd') from dual";
//				String tBirthday = tExeSQL.getOneValue(tSQL);
//				if(!dateStr1.equals(tBirthday)){
//					logger.debug("不行！");
//				}
//				logger.debug("dateStr1:"+dateStr1);
//			}
//			}catch(Exception ex){}	
			//
			conn.setAutoCommit(false);
			DoubleStationForXMLAfterEndService tDoubleStationForXMLAfterEndService = new DoubleStationForXMLAfterEndService();
			tDoubleStationForXMLAfterEndService.mInputTime = "2";
			Document tdoc = tDoubleStationForXMLAfterEndService
					.vDataToXML(tAllData[i]);
			logger.debug("asdfasfsda");
			 tDoubleStationForXMLAfterEndService.outputDocumentToFile("H:/test",
			 tdoc);
			tDoubleStationForXMLAfterEndService.mOperater="001";
		//	tDoubleStationForXMLAfterEndService.InsertBlob(tdoc, conn);
			conn.commit();
			conn.close();
			logger.debug("生成XML完毕！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
