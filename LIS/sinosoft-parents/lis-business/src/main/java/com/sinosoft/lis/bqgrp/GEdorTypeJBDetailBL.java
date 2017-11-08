package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;



import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.*;

//保险帐户表放到保全确认时处理 20051121
public class GEdorTypeJBDetailBL implements BusinessService
{
private static Logger logger = Logger.getLogger(GEdorTypeJBDetailBL.class);

	public GEdorTypeJBDetailBL()
	{}

	public CErrors mErrors = new CErrors();

	private VData mInputData = new VData();
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	private String mOperate;
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData;

	//   C表 to P表
	private Reflections mRef = new Reflections();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	private LPInsureAccClassSchema mJBLPInsureAccClassSchema = new
	LPInsureAccClassSchema();
	private String sJBMoney = "";
	private double dJBMoney = 0.0;



	private boolean getInputData(VData sugoInputData)
	{
		try
		{
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
			getObjectByObjectName(
					"LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			mJBLPInsureAccClassSchema = (LPInsureAccClassSchema)mInputData.getObjectByObjectName(
					"LPInsureAccClassSchema", 0);

			if("INSERT||MONEY".equals(mOperate)){
				sJBMoney = (String)mTransferData.getValueByName("JBMoney");
				dJBMoney = Double.parseDouble(sJBMoney);
			}
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
			if (!tLPGrpEdorItemDB.getInfo())
			{
				CError tError = new CError();
				tError.moduleName = "GEdorJBDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询LPGrpEdorItem失败!!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLPGrpEdorItemSchema = tLPGrpEdorItemDB.getSchema();
			return true;
		}
		catch (Exception ee)
		{
			ee.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GEdorJBDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public VData getResult()
	{
		return mResult;
	}


	public boolean submitData(VData cInputData, String cOperate)
	{
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		if (!getInputData(cInputData))
		{
			return false;
		}
		if("INSERT||MONEY".equals(mOperate)){

			if (!dealData())
			{
				return false;
			}

			if (!prepareData())
			{
				return false;
			}

			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, ""))
			{
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "GEdorJBDetailBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		if("DELETE||MONEY".equals(mOperate)){
			if(!prepareDeleteData()){
				return false;
			}
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, ""))
			{
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "GEdorJBDetailBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		if("CONFIRM||MONEY".equals(mOperate)){
			if(!prepareConfirmData()){
				return false;
			}
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, ""))
			{
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "GEdorJBDetailBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		
		mResult.clear();
		
		return true;
	}

	private boolean prepareConfirmData()
	{

		mLPGrpEdorItemSchema.setEdorState("1");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	private boolean prepareDeleteData() {
		LPAccMoveDB tLPAccMoveDB = new LPAccMoveDB();
		String tJBMovePolNo = (String)mTransferData.getValueByName("JBMovePolNo");
		String tJBMoveInsuAccNo = (String)mTransferData.getValueByName("JBMoveInsuAccNo");
		String tJBMovePayPlanCode = (String)mTransferData.getValueByName("JBMovePayPlanCode");
		String tJBMoveOtherNo = (String)mTransferData.getValueByName("JBMoveOtherNo");


		tLPAccMoveDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPAccMoveDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPAccMoveDB.setPolNo(tJBMovePolNo);
		tLPAccMoveDB.setInsuAccNo(tJBMoveInsuAccNo);
		tLPAccMoveDB.setPayPlanCode(tJBMovePayPlanCode);
		tLPAccMoveDB.setOtherNo(tJBMoveOtherNo);
		tLPAccMoveDB.setAccMoveNo("000000");
		tLPAccMoveDB.setAccMoveCode("000000");
		LPAccMoveSet tLPAccMoveSet = tLPAccMoveDB.query();
		if(tLPAccMoveSet==null||tLPAccMoveSet.size()<1)
		{
			CError tError = new CError();
			tError.moduleName = "GEdorCADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询LPAccMove失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		LPAccMoveSchema tLPAccMoveSchema = tLPAccMoveSet.get(1);
		mMap.put(tLPAccMoveSchema, "DELETE");
		String tSerialNoOut = tLPAccMoveSchema.getSerialNoOut();

		String tSQL = "delete from lpinsureacctrace where edorno ='"+mLPGrpEdorItemSchema.getEdorNo()+"' and "
		+ "edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and serialno = '"+tSerialNoOut+"'";
		mMap.put(tSQL, "DELETE");

		tSQL = "delete from LJSGetEndorse where GetNoticeNo ='"+mLPGrpEdorItemSchema.getEdorNo()+"' and EndorsementNo = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and "
		+ "FeeOperationType = '"+mLPGrpEdorItemSchema.getEdorType()+"' and PayPlanCode = '"+tJBMovePayPlanCode+"' and PolNo = '"+tJBMovePolNo+"'";
		mMap.put(tSQL, "DELETE");

		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	private boolean dealData()
	{
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		LCInsureAccClassSchema tJBLCInsureAccClassSchema = new LCInsureAccClassSchema();

		//处理减保帐户
		tLCInsureAccClassDB.setGrpContNo(mJBLPInsureAccClassSchema.getGrpContNo());
		tLCInsureAccClassDB.setPolNo(mJBLPInsureAccClassSchema.getPolNo());
		tLCInsureAccClassDB.setInsuAccNo(mJBLPInsureAccClassSchema.getInsuAccNo());
		tLCInsureAccClassDB.setPayPlanCode(mJBLPInsureAccClassSchema.getPayPlanCode());
		tLCInsureAccClassDB.setAccAscription(mJBLPInsureAccClassSchema.getAccAscription());
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() <= 0)
		{
			CError tError = new CError();
			tError.moduleName = "GEdorJBDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询LCInsureAccClass失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tJBLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);


		mRef.transFields(mJBLPInsureAccClassSchema, tJBLCInsureAccClassSchema);
		mJBLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		mJBLPInsureAccClassSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());


		//处理保险帐户表记价履历表
		LPInsureAccTraceSchema tJBLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
		tJBLPInsureAccTraceSchema = initLPInsureAccTrace(mJBLPInsureAccClassSchema, -dJBMoney);
		tJBLPInsureAccTraceSchema.setMoneyType("JB");

		mMap.put(tJBLPInsureAccTraceSchema, "INSERT");

		//生成补退费记录
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mJBLPInsureAccClassSchema.getPolNo());
		if(!tLCPolDB.getInfo())
		{
			CError.buildErr(this, "保单信息查询失败!");
			return false;
		}
		LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
		String sFeeType = BqCalBL.getFinType(mLPGrpEdorItemSchema.getEdorType(), "TF",tLCPolSchema.getPolNo());
		LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
		tLJSGetEndorseSchemaNew.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchemaNew.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchemaNew.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
		tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeType);
		tLJSGetEndorseSchemaNew.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLJSGetEndorseSchemaNew.setGrpPolNo(tLCPolSchema.getGrpPolNo());
		tLJSGetEndorseSchemaNew.setContNo(tLCPolSchema.getContNo());
		tLJSGetEndorseSchemaNew.setPolNo(tLCPolSchema.getPolNo());
		tLJSGetEndorseSchemaNew.setRiskCode(tLCPolSchema.getRiskCode());
		tLJSGetEndorseSchemaNew.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchemaNew.setOtherNoType("3");
		tLJSGetEndorseSchemaNew.setDutyCode("000000");
		tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
		tLJSGetEndorseSchemaNew.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
		tLJSGetEndorseSchemaNew.setGetMoney(dJBMoney);
		tLJSGetEndorseSchemaNew.setAgentCode(tLCPolSchema.getAgentCode());
		tLJSGetEndorseSchemaNew.setAgentCom(tLCPolSchema.getAgentCom());
		tLJSGetEndorseSchemaNew.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJSGetEndorseSchemaNew.setAgentType(tLCPolSchema.getAgentType());
		tLJSGetEndorseSchemaNew.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLJSGetEndorseSchemaNew.setKindCode(tLCPolSchema.getKindCode());
		tLJSGetEndorseSchemaNew.setAppntNo(tLCPolSchema.getAppntNo());
		tLJSGetEndorseSchemaNew.setRiskVersion(tLCPolSchema.getRiskVersion());
		tLJSGetEndorseSchemaNew.setHandler(tLCPolSchema.getHandler());
		tLJSGetEndorseSchemaNew.setApproveCode(tLCPolSchema.getApproveCode());
		tLJSGetEndorseSchemaNew.setApproveDate(tLCPolSchema.getApproveDate());
		tLJSGetEndorseSchemaNew.setApproveTime(tLCPolSchema.getApproveTime());
		tLJSGetEndorseSchemaNew.setManageCom(tLCPolSchema.getManageCom());
		tLJSGetEndorseSchemaNew.setPolType("1");
		tLJSGetEndorseSchemaNew.setGetFlag("1");
		tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
		tLJSGetEndorseSchemaNew.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchemaNew.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_Prem);

		mMap.put(tLJSGetEndorseSchemaNew, "DELETE&INSERT");

		//处理帐户转换表
		LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
		tLPAccMoveSchema.setEdorNo(mJBLPInsureAccClassSchema.getEdorNo());
		tLPAccMoveSchema.setEdorType(mJBLPInsureAccClassSchema.getEdorType());
		tLPAccMoveSchema.setPolNo(mJBLPInsureAccClassSchema.getPolNo());
		tLPAccMoveSchema.setInsuAccNo(mJBLPInsureAccClassSchema.getInsuAccNo());
		tLPAccMoveSchema.setPayPlanCode(mJBLPInsureAccClassSchema.getPayPlanCode());
		tLPAccMoveSchema.setRiskCode(mJBLPInsureAccClassSchema.getRiskCode());
		tLPAccMoveSchema.setAccType(mJBLPInsureAccClassSchema.getAccType());
		tLPAccMoveSchema.setAccMoveType("C");
		tLPAccMoveSchema.setOtherNo(mJBLPInsureAccClassSchema.getOtherNo());
		tLPAccMoveSchema.setOtherType(mJBLPInsureAccClassSchema.getOtherType());
		tLPAccMoveSchema.setAccAscription(mJBLPInsureAccClassSchema.getAccAscription());
		tLPAccMoveSchema.setAccMoveNo("000000");
		tLPAccMoveSchema.setAccMoveCode("000000");
		tLPAccMoveSchema.setAccMoveBala(dJBMoney);
		tLPAccMoveSchema.setSerialNoOut(tJBLPInsureAccTraceSchema.getSerialNo());
		tLPAccMoveSchema.setSerialNoIn("000000");
		tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
		tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
		tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
		tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());

		mMap.put(tLPAccMoveSchema, "DELETE&INSERT");


		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		return true;
	}

	private LPInsureAccTraceSchema initLPInsureAccTrace(LPInsureAccClassSchema
			aLPInsureAccClassSchema, double aAccBalance)
	{
		LPInsureAccTraceSchema aLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
		mRef.transFields(aLPInsureAccTraceSchema, aLPInsureAccClassSchema);
		aLPInsureAccTraceSchema.setMakeDate(mLPGrpEdorItemSchema.getEdorValiDate());
		aLPInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
		aLPInsureAccTraceSchema.setManageCom(mGlobalInput.ManageCom);
		aLPInsureAccTraceSchema.setModifyDate(mLPGrpEdorItemSchema.getEdorValiDate());
		aLPInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
		aLPInsureAccTraceSchema.setMoney(aAccBalance);
		aLPInsureAccTraceSchema.setMoneyType("JB");
		aLPInsureAccTraceSchema.setFeeCode("000000");
		aLPInsureAccTraceSchema.setPayDate(mLPGrpEdorItemSchema.getEdorValiDate());
		aLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		aLPInsureAccTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",PubFun.getNoLimit(mGlobalInput.ManageCom)));
		aLPInsureAccTraceSchema.setState("0");
		aLPInsureAccTraceSchema.setUnitCount(0);
		return aLPInsureAccTraceSchema;
	}

	private boolean prepareData()
	{
		mResult.clear();
		mResult.add(mMap);
		return true;
	}


	public static void main(String[] args)
	{
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
