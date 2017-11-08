package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


import com.sinosoft.utility.*;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
public class GEdorCADetailBL
{
private static Logger logger = Logger.getLogger(GEdorCADetailBL.class);

    public GEdorCADetailBL()
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
    private LPInsureAccClassSet tempLPInsureAccClassSet = new
            LPInsureAccClassSet();
    private LPInsureAccClassSchema mLPInsureAccClassSchemaOut = new
            LPInsureAccClassSchema();
    private LPInsureAccClassSchema mLPInsureAccClassSchemaIn = new
            LPInsureAccClassSchema();
    private String TheMoney = "";
    private double Money = 0.0;



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

            if("INSERT||MONEY".equals(mOperate)){
            	TheMoney = (String) sugoInputData.getObjectByObjectName("String", 0);
            	Money = Double.parseDouble(TheMoney);
            	tempLPInsureAccClassSet = (LPInsureAccClassSet) sugoInputData.
            	getObjectByObjectName(
            			"LPInsureAccClassSet", 0);
            	mLPInsureAccClassSchemaOut = tempLPInsureAccClassSet.get(1);
            	mLPInsureAccClassSchemaIn = tempLPInsureAccClassSet.get(2);
            }
            LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
            tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
            if (!tLPGrpEdorItemDB.getInfo())
            {
            	CError tError = new CError();
            	tError.moduleName = "GEdorCADetailBL";
            	tError.functionName = "getInputData";
            	tError.errorMessage = "查询LPGrpEdorItem失败!!";
            	this.mErrors.addOneError(tError);
            	return false;
            }
            mLPGrpEdorItemSchema = tLPGrpEdorItemDB.getSchema();
            
            if (mLPGrpEdorItemSchema.getEdorState().trim().equals("2")) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GEdorDetailBL";
                tError.functionName = "Preparedata";
                tError.errorMessage = "该保全已经申请确认不能修改!";
                logger.debug("------" + tError);
                this.mErrors.addOneError(tError);
                return false;
              }
            return true;
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
            CError tError = new CError();
            tError.moduleName = "GEdorCADetailBL";
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
        		tError.moduleName = "GEdorCADetailBL";
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
        		tError.moduleName = "GEdorCADetailBL";
        		tError.functionName = "submitData";
        		tError.errorMessage = "数据提交失败!";
        		this.mErrors.addOneError(tError);
        		return false;
        	}
        }
        return true;
    }


    private boolean prepareDeleteData() {
    	LPAccMoveDB tLPAccMoveDB = new LPAccMoveDB();
    	String tMoveOutPolNo = (String)mTransferData.getValueByName("MoveOutPolNo");
    	String tMoveOutInsuAccNo = (String)mTransferData.getValueByName("MoveOutInsuAccNo");
    	String tMoveOutPayPlanCode = (String)mTransferData.getValueByName("MoveOutPayPlanCode");
    	String tMoveInPolNo = (String)mTransferData.getValueByName("MoveInPolNo");
    	String tMoveInInsuAccNo = (String)mTransferData.getValueByName("MoveInInsuAccNo");
    	String tMoveInPayPlanCode = (String)mTransferData.getValueByName("MoveInPayPlanCode");
    	
    	tLPAccMoveDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    	tLPAccMoveDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    	tLPAccMoveDB.setPolNo(tMoveOutPolNo);
    	tLPAccMoveDB.setInsuAccNo(tMoveOutInsuAccNo);
    	tLPAccMoveDB.setPayPlanCode(tMoveOutPayPlanCode);
    	tLPAccMoveDB.setOtherNo(tMoveInPolNo);
    	tLPAccMoveDB.setAccMoveNo(tMoveInInsuAccNo);
    	tLPAccMoveDB.setAccMoveCode(tMoveInPayPlanCode);
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
    	String tSerialNoIn = tLPAccMoveSchema.getSerialNoIn();
    	String tSQL = "delete from lpinsureacctrace where edorno ='"+mLPGrpEdorItemSchema.getEdorNo()+"' and " +
    			"edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and serialno in ('"+tSerialNoOut+"','"+tSerialNoIn+"')";
    	mMap.put(tSQL, "DELETE");
    	mResult.clear();
        mResult.add(mMap);
        return true;
	}

	private boolean dealData()
	{



		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		LCInsureAccClassSchema tLCInsureAccClassSchemaOut = new LCInsureAccClassSchema();
		LCInsureAccClassSchema tLCInsureAccClassSchemaIn = new LCInsureAccClassSchema();

		//处理转出帐户(转出帐户结息,转入不结息)

		tLCInsureAccClassDB.setGrpContNo(mLPInsureAccClassSchemaOut.getGrpContNo());
		tLCInsureAccClassDB.setPolNo(mLPInsureAccClassSchemaOut.getPolNo());
		tLCInsureAccClassDB.setInsuAccNo(mLPInsureAccClassSchemaOut.getInsuAccNo());
		tLCInsureAccClassDB.setPayPlanCode(mLPInsureAccClassSchemaOut.getPayPlanCode());
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() <= 0)
		{
			CError tError = new CError();
			tError.moduleName = "GEdorCADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询LCInsureAccClass失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCInsureAccClassSchemaOut = tLCInsureAccClassSet.get(1);
		int tIntv = PubFun.calInterval(tLCInsureAccClassSchemaOut.getBalaDate(), mLPGrpEdorItemSchema.getEdorValiDate(), "D");
	    if(tIntv<0){
	      	//当天结过结息的,将日期置为最晚
	    	CError.buildErr(this, "保全操作时间在转出账户上次结息日期之前!,不允许操作该账户!");
	    	return false;
	    	
	     }

		LPInsureAccClassDB tLPInsureAccClassDBOut=new LPInsureAccClassDB();
		tLPInsureAccClassDBOut.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPInsureAccClassDBOut.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPInsureAccClassDBOut.setPolNo(tLCInsureAccClassSchemaOut.getPolNo());
		tLPInsureAccClassDBOut.setInsuAccNo(tLCInsureAccClassSchemaOut.getInsuAccNo());
		tLPInsureAccClassDBOut.setPayPlanCode(tLCInsureAccClassSchemaOut.getPayPlanCode());
		tLPInsureAccClassDBOut.setAccAscription(tLCInsureAccClassSchemaOut.getAccAscription());
		LPInsureAccClassSet tLPInsureAccClassSetOut=tLPInsureAccClassDBOut.query();
		if(tLPInsureAccClassSetOut==null||tLPInsureAccClassSetOut.size()<=0)
		{
			mRef.transFields(mLPInsureAccClassSchemaOut, tLCInsureAccClassSchemaOut);
			mLPInsureAccClassSchemaOut.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			mLPInsureAccClassSchemaOut.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		}
		else
		{
			LPInsureAccClassSchema tLPInsureAccClassSchemaOut=tLPInsureAccClassSetOut.get(1);
			mLPInsureAccClassSchemaOut.setSchema(tLPInsureAccClassSchemaOut);
			logger.debug(mLPInsureAccClassSchemaOut.getInsuAccBala());
		}
		//-------------------------------处理转出帐户结束-----------------------



		//处理转入帐户
		tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setGrpContNo(mLPInsureAccClassSchemaIn.getGrpContNo());
		tLCInsureAccClassDB.setPolNo(mLPInsureAccClassSchemaIn.getPolNo());
		tLCInsureAccClassDB.setInsuAccNo(mLPInsureAccClassSchemaIn.getInsuAccNo());
		tLCInsureAccClassDB.setPayPlanCode(mLPInsureAccClassSchemaIn.getPayPlanCode());
		LCInsureAccClassSet tLCInsureAccClassInSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassInSet == null || tLCInsureAccClassInSet.size() <= 0)
		{
			CError tError = new CError();
			tError.moduleName = "GEdorCADetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询LCInsureAccClass失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCInsureAccClassSchemaIn = tLCInsureAccClassInSet.get(1);

		tIntv = PubFun.calInterval(tLCInsureAccClassSchemaIn.getBalaDate(), mLPGrpEdorItemSchema.getEdorValiDate(), "D");
	    if(tIntv<0){
	      	//当天结过结息的,将日期置为最晚
	    	CError.buildErr(this, "保全操作时间在转入账户上次结息日期之前!,不允许操作该账户!");
	    	return false;
	     }
		
		LPInsureAccClassDB tLPInsureAccClassDBIn=new LPInsureAccClassDB();
		tLPInsureAccClassDBIn.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPInsureAccClassDBIn.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPInsureAccClassDBIn.setPolNo(tLCInsureAccClassSchemaIn.getPolNo());
		tLPInsureAccClassDBIn.setInsuAccNo(tLCInsureAccClassSchemaIn.getInsuAccNo());
		tLPInsureAccClassDBIn.setPayPlanCode(tLCInsureAccClassSchemaIn.getPayPlanCode());
		tLPInsureAccClassDBIn.setOtherNo(tLCInsureAccClassSchemaIn.getOtherNo());
		tLPInsureAccClassDBIn.setAccAscription(tLCInsureAccClassSchemaIn.getAccAscription());
		LPInsureAccClassSet tLPInsureAccClassSetIn=tLPInsureAccClassDBIn.query();
		
		if(tLPInsureAccClassSetIn==null||tLPInsureAccClassSetIn.size()<=0)
		{
			mRef.transFields(mLPInsureAccClassSchemaIn, tLCInsureAccClassSchemaIn);
			mLPInsureAccClassSchemaIn.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			mLPInsureAccClassSchemaIn.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		}
		else
		{
			LPInsureAccClassSchema tLPInsureAccClassSchemaIn=tLPInsureAccClassSetIn.get(1);
			mLPInsureAccClassSchemaIn.setSchema(tLPInsureAccClassSchemaIn);
			logger.debug(mLPInsureAccClassSchemaIn.getInsuAccBala());
		}



		//处理保险帐户表记价履历表
		LPInsureAccTraceSchema tLPInsureAccTraceSchemaOut = new LPInsureAccTraceSchema();
		LPInsureAccTraceSchema tLPInsureAccTraceSchemaIn = new LPInsureAccTraceSchema();
		tLPInsureAccTraceSchemaOut = initLPInsureAccTrace(mLPInsureAccClassSchemaOut, -Money);
		tLPInsureAccTraceSchemaOut.setMoneyType("AO");
		tLPInsureAccTraceSchemaIn = initLPInsureAccTrace(mLPInsureAccClassSchemaIn, Money);
		tLPInsureAccTraceSchemaIn.setMoneyType("AI");
		mMap.put(tLPInsureAccTraceSchemaOut, "INSERT");
		mMap.put(tLPInsureAccTraceSchemaIn, "INSERT");

		//处理帐户转换表
		LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
		tLPAccMoveSchema.setEdorNo(mLPInsureAccClassSchemaOut.getEdorNo());
		tLPAccMoveSchema.setEdorType(mLPInsureAccClassSchemaOut.getEdorType());
		tLPAccMoveSchema.setPolNo(mLPInsureAccClassSchemaOut.getPolNo());
		tLPAccMoveSchema.setInsuAccNo(mLPInsureAccClassSchemaOut.getInsuAccNo());
		tLPAccMoveSchema.setPayPlanCode(mLPInsureAccClassSchemaOut.getPayPlanCode());
		tLPAccMoveSchema.setRiskCode(mLPInsureAccClassSchemaOut.getRiskCode());
		tLPAccMoveSchema.setAccType(mLPInsureAccClassSchemaOut.getAccType());
		tLPAccMoveSchema.setAccMoveType("C");
		tLPAccMoveSchema.setOtherNo(mLPInsureAccClassSchemaIn.getPolNo());
		tLPAccMoveSchema.setOtherType(mLPInsureAccClassSchemaOut.getOtherType());
		tLPAccMoveSchema.setAccAscription(mLPInsureAccClassSchemaOut.getAccAscription());
		tLPAccMoveSchema.setAccMoveNo(mLPInsureAccClassSchemaIn.getInsuAccNo());
		tLPAccMoveSchema.setAccMoveCode(mLPInsureAccClassSchemaIn.getPayPlanCode());
		tLPAccMoveSchema.setAccMoveBala(Money);
		tLPAccMoveSchema.setSerialNoOut(tLPInsureAccTraceSchemaOut.getSerialNo());
		tLPAccMoveSchema.setSerialNoIn(tLPInsureAccTraceSchemaIn.getSerialNo());
		tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
		tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
		tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
		tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());

		mMap.put(tLPAccMoveSchema, "DELETE&INSERT");

		if("INSERT||MONEY".equals(mOperate)){
			mLPGrpEdorItemSchema.setEdorState("1");
			mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
			mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		}
		return true;
	}

    private LPInsureAccTraceSchema initLPInsureAccTrace(LPInsureAccClassSchema
            aLPInsureAccClassSchema, double aAccBalance)
    {
      LPInsureAccTraceSchema aLPInsureAccTraceSchema = new
          LPInsureAccTraceSchema();
      Reflections ref = new Reflections();
      ref.transFields(aLPInsureAccTraceSchema, aLPInsureAccClassSchema);
      aLPInsureAccTraceSchema.setMakeDate(mLPGrpEdorItemSchema.getEdorValiDate());
      aLPInsureAccTraceSchema.setMakeTime("00:00:00");
      
      int tIntv = PubFun.calInterval(aLPInsureAccClassSchema.getBalaDate(), mLPGrpEdorItemSchema.getEdorValiDate(), "D");
      if(tIntv==0){
      	//当天结过结息的,将日期置为最晚
    	  aLPInsureAccTraceSchema.setMakeTime("23:59:59");
      }
      aLPInsureAccTraceSchema.setManageCom(mGlobalInput.ManageCom);
      aLPInsureAccTraceSchema.setModifyDate(mLPGrpEdorItemSchema.getEdorValiDate());
      aLPInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
      aLPInsureAccTraceSchema.setMoney(aAccBalance);
      aLPInsureAccTraceSchema.setMoneyType("TF");
      aLPInsureAccTraceSchema.setFeeCode("000000");
      aLPInsureAccTraceSchema.setPayDate(mLPGrpEdorItemSchema.getEdorValiDate());
      aLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
      aLPInsureAccTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
          PubFun.getNoLimit(mGlobalInput.ManageCom)));
      aLPInsureAccTraceSchema.setState("");
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
    	String tEdorType = "CA";
    	String tEdorNo = "86000020090469000376";
    	String tSql = "select polno,sum(accmovebala) from lpaccmove where edortype = '"+tEdorType+"' and edorno = '"+tEdorNo+"' group by polno";
		SSRS rSSRS = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(tSql);
		if (rSSRS == null || rSSRS.MaxRow == 0) {

		}
		
		int tArrLen = rSSRS.MaxRow;
		
		for (int k = 1; k <= tArrLen; k++) {
			logger.debug("保单号为:"+rSSRS.GetText(k, 1)+"转出金额总计为:"+rSSRS.GetText(k, 2));
		}
    }
}
