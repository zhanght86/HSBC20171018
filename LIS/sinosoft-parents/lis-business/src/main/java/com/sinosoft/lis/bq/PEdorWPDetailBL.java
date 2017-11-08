/**
 * AM --保全追加保费 Additional premiums
 * LIUZHAO 20080111
 */
package com.sinosoft.lis.bq;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.operfee.RnAccountDeal;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


public class PEdorWPDetailBL{

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private MMap map=new MMap();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCDutySchema mLCDutySchema =new LCDutySchema();
    private LCPremSchema mLCPremSchema =new LCPremSchema();
    private LJSPayPersonSet mLJSPayPersonSet =new LJSPayPersonSet();
    private LJSGetEndorseSet mLJSGetEndorseSet =new LJSGetEndorseSet();
    
	private RnAccountDeal mRnAccountDeal =new RnAccountDeal();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private Reflections ref = new Reflections();
	//总保费
	private double mSumPrem = 0.0;
	private double dGetMoney = 0.0;
	private double dChgPrem = 0.0;
	private String mPolNo;

	public PEdorWPDetailBL( ) {

	}


	public CErrors getErrors()
    {
        return mErrors;
    }


	/**
     * 数据输出方法，供外界获取数据处理结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }


    /**
	 * 外部调用接口
	 * @param tVData
	 * @param tOperator
	 * @return
	 */
	public boolean submitData(VData cInputData,String cOperate)
	{
        mInputData = (VData)cInputData.clone();
        mOperate = cOperate;

        if (!getInputData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        //准备往后台的数据
        if (!prepareData())
        {
            return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, ""))
        {
            mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError.buildErr(this,"数据提交失败!");
            return false;
        }
		return true;
	}

 
	
    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return boolean
     */
    private boolean dealData()
    {
    	if(mOperate.equals("EDORITEM|DELETE")){
    		
    		LPInsureAccTraceSet aLPInsureAccTraceSet = new LPInsureAccTraceSet();
    		LPInsureAccFeeTraceSet aLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
    		LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
    		LJSPayPersonSet aLJSPayPersonSet = new LJSPayPersonSet();
    		
    		LJSGetEndorseDB deLJSGetEndorseDB = new LJSGetEndorseDB();
            LJSPayPersonDB deLJSPayPersonDB = new LJSPayPersonDB();
            LPInsureAccTraceDB deLPInsureAccTraceDB = new LPInsureAccTraceDB();
            LPInsureAccFeeTraceDB deLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
            deLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
            deLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
            deLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema.getEdorType());
            deLJSGetEndorseDB.setPolNo(mLCPolSchema.getPolNo());
            try
            {
                aLJSGetEndorseSet = deLJSGetEndorseDB.query();
            }
            catch (Exception ex)
            {
                CError.buildErr(this, "查询批改补退费表出现异常！");
                return false;
            }
            deLJSPayPersonDB.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
            deLJSPayPersonDB.setPolNo(mLCPolSchema.getPolNo());
            // deLJSPayPersonDB.setPolNo(mLJSPayPersonSet.get(1).getPolNo());
            // deLJSPayPersonDB.setPayCount(mLJSPayPersonSet.get(1).getPayCount());
            // deLJSPayPersonDB.setPayAimClass(mLJSPayPersonSet.get(1).getPayAimClass());
            // deLJSPayPersonDB.setDutyCode(mLJSPayPersonSet.get(1).getDutyCode());
            // deLJSPayPersonDB.setPayPlanCode(mLJSPayPersonSet.get(1).getPayPlanCode());
            try
            {
                aLJSPayPersonSet = deLJSPayPersonDB.query();
            }
            catch (Exception ex)
            {
                CError.buildErr(this, "查询应收个人交费表出现异常！");
                return false;
            }
            try
            {
                deLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                deLPInsureAccTraceDB.setPolNo(mLCPolSchema.getPolNo());
                aLPInsureAccTraceSet = deLPInsureAccTraceDB.query();
            }
            catch (Exception ex)
            {
                CError.buildErr(this, "查询保全保险帐户表记价履历表出现异常！");
                return false;
            }
            try
            {
                deLPInsureAccFeeTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                deLPInsureAccFeeTraceDB.setPolNo(mLCPolSchema.getPolNo());
                aLPInsureAccFeeTraceSet = deLPInsureAccFeeTraceDB.query();
            }
            catch (Exception ex)
            {
                CError.buildErr(this, "查询应收个人交费表出现异常！");
                return false;
            }
            String sUpdateSQL = "update LPEdorItem  set getMoney = '0',edorstate = '3' where contno = '?contno?' and  EdorNo = '?EdorNo?' and EdorType = '?EdorType?'";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(sUpdateSQL);
            sqlbv.put("contno", mLPEdorItemSchema.getContNo());
            sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
            sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
            map.put(sqlbv, "UPDATE");
            map.put(aLJSGetEndorseSet, "DELETE");
            map.put(aLJSPayPersonSet, "DELETE");
            map.put(aLPInsureAccTraceSet, "DELETE");
            map.put(aLPInsureAccFeeTraceSet, "DELETE");
            return true;
    	}
        if (!delPData())
        {
            mErrors.addOneError("删除上次保存操作数据失败!");
            return false;   
        }
    	LPContSchema tLPContSchema =new LPContSchema();
    	LPPolSchema  tLPPolSchema = new LPPolSchema();
    	LPDutySchema tLPDutySchema =new LPDutySchema();
    	LPPremSchema tLPPremSchema =new LPPremSchema();    	
    	
    	LCContDB tLCContDB =new LCContDB();
    	LCPolDB  tLCPolDB = new LCPolDB();
    	LCDutyDB tLCDutyDB =new LCDutyDB();
    	LCPremDB tLCPremDB =new LCPremDB();
    	
    	LCContSet tLCContSet =new LCContSet();
    	LCPolSet tLCPolSet = new LCPolSet();
    	LCDutySet tLCDutySet =new LCDutySet();
    	LCPremSet tLCPremSet =new LCPremSet();
    	
    	LPContSet tLPContSet =new LPContSet();
    	LPPolSet tLPPolSet = new LPPolSet();
    	LPDutySet tLPDutySet =new LPDutySet();
    	LPPremSet tLPPremSet =new LPPremSet();    	
    	
    	mLPEdorItemSchema.setMakeDate(mCurrentDate);
    	mLPEdorItemSchema.setMakeTime(mCurrentTime);    	
    	mLPEdorItemSchema.setModifyDate(mCurrentDate);
    	mLPEdorItemSchema.setModifyTime(mCurrentTime);  
    	String[] tFieldName = {"SumPrem","EdorNo","EdorType","ModifyDate","ModifyTime"};
    	
    	//  万能险追加不需要改变 lcpol ,lcprem,lcduty,lcget,lccont 数据 没什么意义 先注释掉 modify by LH  2008-10-30
    	//保单表
//    	System.out.println(mLPEdorItemSchema.getContNo());    	
//    	tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
//    	tLCContSet =tLCContDB.query();
//    	if(tLCContDB.mErrors.needDealError())
//    	{
//            mErrors.copyAllErrors(tLCContDB.mErrors);
//            mErrors.addOneError("查找保单信息失败!");
//            return false;    		
//    	}
//    	ref.transFields(tLPContSchema, tLCContSet.get(1));
//    	mSumPrem = tLPContSchema.getSumPrem()+mLPEdorItemSchema.getGetMoney();    
//    	tLPContSet.add(tLPContSchema);
//    	ChangeSchemaField(tLPContSet,tFieldName);        	
//    	map.put(tLPContSet, "DELETE&INSERT");
    	//**************************************************************************
    	
    	//保单险种表
    	String sQuerySQL = "select * from LCPol where ContNo = '?ContNo?' and PolNo = mainPolNo";
    	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    	sqlbv1.sql(sQuerySQL);
    	sqlbv1.put("ContNo", mLPEdorItemSchema.getContNo());
    	try
    	{
    		tLCPolSet =tLCPolDB.executeQuery(sqlbv1);
    	}catch (Exception ex)
    	{
    		CError.buildErr(this, "查找保单险种信息异常!");
    		return false;
    	}
    	if (tLCPolSet == null || tLCPolSet.size() <=0)
    	{
    		CError.buildErr(this, "查找保单险种信息失败!");
    		return false;
    	}
    	else
    	{
    		mLCPolSchema = tLCPolSet.get(1);
    	}
    	mPolNo = mLCPolSchema.getPolNo();
//    	ref.transFields(tLPPolSchema, mLCPolSchema);
//    	tLPPolSet.add(tLPPolSchema);
//    	ChangeSchemaField(tLPPolSet,tFieldName); 
//    	tLPPolSet.get(1).setSumPrem(mLPEdorItemSchema.getGetMoney()+mLCPolSchema.getSumPrem());
//    	map.put(tLPPolSet, "DELETE&INSERT");
    	//**************************************************************************
    	
    	//保费表
    	String tSql = "select * from lcprem a where contno = '?contno?'"
    			     +" and exists(  select 1 from lmdutypay "
    			     		      +" where payplancode = a.payplancode"
    			                  +" and payaimclass = '1')";//追加保费项
//    			                  +" and payplancode = 'UVA023'";
    	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    	sqlbv2.sql(tSql);
    	sqlbv2.put("contno", mLPEdorItemSchema.getContNo());
    	
    	tLCPremSet = tLCPremDB.executeQuery(sqlbv2);

    	if(tLCPremDB.mErrors.needDealError())
    	{
            mErrors.copyAllErrors(tLCPremDB.mErrors);
            mErrors.addOneError("查找保费表失败!");
            return false;    		
    	}  
    	mLCPremSchema = tLCPremSet.get(1);
//    	ref.transFields(tLPPremSchema, mLCPremSchema);
//    	tLPPremSet.add(tLPPremSchema);
//    	ChangeSchemaField(tLPPremSet,tFieldName);    
////    	tLPPremSet.get(1).setPrem(mLPEdorItemSchema.getGetMoney()+mLCPremSchema.getPrem());
//    	tLPPremSet.get(1).setSumPrem(mLPEdorItemSchema.getGetMoney()+mLCPremSchema.getSumPrem());
//    	map.put(tLPPremSet, "DELETE&INSERT");
    	//**************************************************************************    	
    	
    	//保单险种责任表
//    	tLCDutyDB.setContNo(mLPEdorItemSchema.getContNo());
//    	tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
    	
    	// add by LH ISA的两条责任都是不定期的，所以这里必须特殊处理一下  begin ----------------------
		ExeSQL tExeSQL = new ExeSQL();
		String sISASQL = "select 'X' from lmrisksort a where risksorttype = '00' and risksortvalue = 'ISA' "
			             + "and exists (select 'X' from LCPol where PolNo = MainPolNo and RiskCode = a.RiskCode And PolNo = '?mPolNo?' )";
		String sFlag = "";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sISASQL);
		sqlbv3.put("mPolNo", mPolNo);
		try
		{
			sFlag = tExeSQL.getOneValue(sqlbv3);
		}catch (Exception ex)
		{
			CError.buildErr(this, "查询主险是否为ISA险种出现异常！");
			return false;
		}
		//不能随便取一条，必须是追加的责任 modify by LH 2008-10-30
		if (sFlag != null && !sFlag.equals(""))
		{
			//如果是ISA 险种，申请日为生效日 
			 sQuerySQL = "select * from LCDuty where PolNo = '?PolNo?' and PayIntv = '-1' and DutyCode in ('ISA001','ISA201') ";
			 sqlbv1=new SQLwithBindVariables();
			 sqlbv1.sql(sQuerySQL); 
			 sqlbv1.put("PolNo", mLCPolSchema.getPolNo());
		}
		else
		{
	        sQuerySQL = "select * from LCDuty where PolNo = '?PolNo?' ";
	        sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(sQuerySQL);
	        sqlbv1.put("PolNo", mLCPolSchema.getPolNo());
		}
		// add by LH ISA的两条责任都是不定期的，所以这里必须特殊处理一下  end ----------------------------
		
    	
    	tLCDutySet=tLCDutyDB.executeQuery(sqlbv1);
    	if(tLCDutyDB.mErrors.needDealError())
    	{
            mErrors.copyAllErrors(tLCDutyDB.mErrors);
            mErrors.addOneError("查找保单险种责任表失败!");
            return false;    		
    	}
    	mLCDutySchema = tLCDutySet.get(1);
      
    	//准备核销需要的财务数据
        mLJSPayPersonSet  = CreatePayPersonSet();
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchemaSet(mLJSPayPersonSet);
        //end zhangyingfeng 2016-07-14
        //渠道应收
//        ref.transFields(mLASPayPersonSchema, mLJSPayPersonSet.get(1));
        //批改补退费数据
        mLJSGetEndorseSet = CreateEndorseSet();    
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchemaSet(mLJSGetEndorseSet);
        //end zhangyingfeng 2016-07-14
    	//帐户相关Schema
    	LCInsureAccTraceSet tLCInsureAccTraceSet =new LCInsureAccTraceSet();    	
    	LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet =new LCInsureAccFeeTraceSet();
    	LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
    	LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
    	LPInsureAccTraceSet tLPInsureAccTraceSet =new LPInsureAccTraceSet();    	
    	LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet =new LPInsureAccFeeTraceSet();     
    	LPInsureAccTraceSchema tLPInsureAccTraceSchema = null;
    	LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = null;   
    	LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = null;
    	LPInsureAccFeeSchema tLPInsureAccFeeSchema = null;
    	LPInsureAccClassFeeSet tLPInsureAccClassFeeSet =new LPInsureAccClassFeeSet(); 
    	LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
    	
    	//追加保费进帐户
    	VData tVData =new VData();
    	tVData.add(mGlobalInput);
    	
        LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
        LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
    	ref.transFields(tLJAPayPersonSchema, mLJSPayPersonSet.get(1));         
    	tLJAPayPersonSet.add(tLJAPayPersonSchema); 
        tVData.add(tLJAPayPersonSet);
        String feeTakePlace = "06";//保单初始管理费(追加续期保全)
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FeeTakePlace", feeTakePlace);
        tVData.add(tTransferData);
        if(!mRnAccountDeal.submitData(tVData, this.mOperate))
        {
            mErrors.copyAllErrors(mRnAccountDeal.mErrors);
            mErrors.addOneError("追加保费帐户处理失败!");
            return false; 
        }else
        {
        	mResult 				= mRnAccountDeal.getResult();
        	LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet)mResult.getObjectByObjectName("LCInsureAccSet", 0);
        	LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet)mResult.getObjectByObjectName("LCInsureAccClassSet", 0);
        	tLCInsureAccTraceSet    =(LCInsureAccTraceSet)mResult.getObjectByObjectName("LCInsureAccTraceSet", 0);
        	tLCInsureAccFeeTraceSet =(LCInsureAccFeeTraceSet)mResult.getObjectByObjectName("LCInsureAccFeeTraceSet", 0);
        	//由于批处理程序不处理管理费,因此在此用这里得到的管理费表数据
        	tLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet)mResult.getObjectByObjectName("LCInsureAccClassFeeSet", 0);
        	tLCInsureAccFeeSet = (LCInsureAccFeeSet)mResult.getObjectByObjectName("LCInsureAccFeeSet", 0);
        	//帐户项
        	LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
        	LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
        	LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
        	LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
        	for(int m= 1;m <= tLCInsureAccSet.size();m++)
        	{
        		ref.transFields(tLPInsureAccSchema,tLCInsureAccSet.get(m));
        		tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        		tLPInsureAccSchema.setEdorType("WP");
        		tLPInsureAccSet.add(tLPInsureAccSchema);
        	}
        	for(int m= 1;m <= tLCInsureAccClassSet.size();m++)
        	{
        		ref.transFields(tLPInsureAccClassSchema,tLCInsureAccClassSet.get(m));
        		tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        		tLPInsureAccClassSchema.setEdorType("WP");
        		tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
        	}
        	for(int m= 1;m <= tLCInsureAccTraceSet.size();m++)
        	{
        		tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
	        	ref.transFields(tLPInsureAccTraceSchema, tLCInsureAccTraceSet.get(m)); 
	        	tLPInsureAccTraceSchema.setBusyType("AM");
//	        	tLPInsureAccTraceSchema.setFeeCode("000000");
	        	tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
	        	tLPInsureAccTraceSchema.setValueDate(mCurrentDate);
	        	tLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo());
	        	tLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
	        	tLPInsureAccTraceSchema.setOtherType("3");
	        	tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
        	}
        	//管理费项 3个都要
        	for(int n= 1;n <= tLCInsureAccFeeTraceSet.size();n++)
        	{   
        		tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();   
	        	ref.transFields(tLPInsureAccFeeTraceSchema, tLCInsureAccFeeTraceSet.get(n)); 	
	        	tLPInsureAccFeeTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());//设为批单号方便回退处理
	        	tLPInsureAccFeeTraceSchema.setOtherType("3");
	        	tLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
        	}
        	for(int o = 1; o<=tLCInsureAccClassFeeSet.size();o++){
        		tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
        		ref.transFields(tLPInsureAccClassFeeSchema, tLCInsureAccClassFeeSet.get(o));
        		//tLPInsureAccClassFeeSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
        		tLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
        	}
        	for(int p = 1; p<=tLCInsureAccFeeSet.size();p++){
        		tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
        		ref.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeSet.get(p));
        		//tLPInsureAccFeeSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
        		tLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
        	}
        	//修改部分字段
        	ChangeSchemaField(tLPInsureAccTraceSet,tFieldName);
        	ChangeSchemaField(tLPInsureAccFeeTraceSet,tFieldName);
        	ChangeSchemaField(tLPInsureAccClassFeeSet,tFieldName);
        	ChangeSchemaField(tLPInsureAccFeeSet,tFieldName);
        	map.put(tLPInsureAccSet, "DELETE&INSERT");
        	map.put(tLPInsureAccClassSet, "DELETE&INSERT");
        	map.put(tLPInsureAccTraceSet, "DELETE&INSERT");
			//add zhangyingfeng 2016-08-03
			//营改增 价税分离计算器
			TaxCalculator.calBySchemaSet(tLPInsureAccFeeTraceSet);
			//end zhangyingfeng 2016-08-03
        	map.put(tLPInsureAccFeeTraceSet, "DELETE&INSERT");
        	map.put(tLPInsureAccClassFeeSet, "DELETE&INSERT");
        	map.put(tLPInsureAccFeeSet, "DELETE&INSERT");
  	    }
        LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();;
	    tLJSPayPersonSchema.setPolNo(mLPEdorItemSchema.getPolNo());
        LJAPayPersonDB lzLJAPayDB = new LJAPayPersonDB();
	    String SQL = "select * from LJAPayPerson where contno = '?contno?' order by paycount DESC";	    
	    SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	    sqlbv4.sql(SQL);
	    sqlbv4.put("contno", mLPEdorItemSchema.getContNo());
	    tLJSPayPersonSchema.setPayCount(lzLJAPayDB.executeQuery(sqlbv4).get(1).getPayCount()+1);//暂时,PK
	    tLJSPayPersonSchema.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
	    tLJSPayPersonSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
	    tLJSPayPersonSchema.setContNo(mLCPolSchema.getContNo());
	    tLJSPayPersonSchema.setManageCom(mLCPolSchema.getManageCom());
	    tLJSPayPersonSchema.setAgentCom(mLCPolSchema.getAgentCom());
	    tLJSPayPersonSchema.setAgentType(mLCPolSchema.getAgentType());
	    tLJSPayPersonSchema.setRiskCode(mLCPolSchema.getRiskCode());
	    tLJSPayPersonSchema.setAgentCode(mLCPolSchema.getAgentCode());
	    tLJSPayPersonSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
	    tLJSPayPersonSchema.setPayTypeFlag("2");//暂时
	    tLJSPayPersonSchema.setAppntNo(mLCPolSchema.getAppntNo());
	    tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
	    tLJSPayPersonSchema.setPayAimClass("1");//暂时,PK
	    tLJSPayPersonSchema.setPayType("WP");
	    tLJSPayPersonSchema.setDutyCode("LW0400");//暂时,PK
	    tLJSPayPersonSchema.setPayPlanCode("LW0400");//暂时,PK
	    tLJSPayPersonSchema.setSumDuePayMoney("1212");
		tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
		tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSPayPersonSchema.setBankAccNo("");
		tLJSPayPersonSchema.setBankCode("");
		tLJSPayPersonSchema.setPayIntv("-1");
		tLJSPayPersonSchema.setMainPolYear(PubFun.calInterval(mLCPolSchema.getCValiDate(), mLPEdorItemSchema.getEdorValiDate(), "Y")+1);
		tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setCurPayToDate(mLCPolSchema.getPaytoDate());
		mLJSPayPersonSet.add(tLJSPayPersonSchema);
		
		LJSGetEndorseSchema tLJSGetEndorseSchema = initLJSGetEndorse(tLJSPayPersonSchema,"BF");
		mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
    	mLPEdorItemSchema.setChgPrem(mLPEdorItemSchema.getGetMoney());
    	mLPEdorItemSchema.setGetMoney(mLPEdorItemSchema.getGetMoney());
    	//mLPEdorItemSchema.setEdorAppDate(mCurrentDate);
        mLPEdorItemSchema.setEdorState("1");
        mLPEdorItemSchema.setModifyDate(mCurrentDate);
        mLPEdorItemSchema.setModifyTime(mCurrentTime);
        mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
    	map.put(mLPEdorItemSchema,"UPDATE");
        map.put(mLJSPayPersonSet, "DELETE&INSERT");
//        map.put(mLASPayPersonSchema, "DELETE&INSERT");
        map.put(mLJSGetEndorseSet, "DELETE&INSERT");
        return true;
    }
    private LJSGetEndorseSchema initLJSGetEndorse(LJSPayPersonSchema tLJSPayPersonSchema,String strfinType){
    	LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
    	tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
    	String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), strfinType, mLPEdorItemSchema.getPolNo());
    	ref.transFields(tLJSGetEndorseSchema, mLCPolSchema);
    	ref.transFields(tLJSGetEndorseSchema, tLJSPayPersonSchema);
    	tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
//    	if (finType==null || finType.equals("")){
//    		CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
//    		return null;
//    	}
    	tLJSGetEndorseSchema.setFeeFinaType("G001");
    	tLJSGetEndorseSchema.setCurrency(mLPEdorItemSchema.getCurrency());
    	tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
    	tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
    	tLJSGetEndorseSchema.setOtherNo(mLCPolSchema.getPolNo());
    	tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
    	tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
    	tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
    	tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
    	tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
    	tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
    	tLJSGetEndorseSchema.setGetMoney(tLJSPayPersonSchema.getSumDuePayMoney());
    	tLJSGetEndorseSchema.setGetFlag("0");
    	tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem + String.valueOf(mLJSPayPersonSet.get(1).getPayCount()));
    	return tLJSGetEndorseSchema;
    }
    /**
     * 删除上次保存过的数据
     * @return boolean
     */
    
    private boolean delPData()
    {

        //清除P表中上次保存过的数据
        String edorno = mLPEdorItemSchema.getEdorNo();
        String edortype = mLPEdorItemSchema.getEdorType();
        String sqlWhere = " contno = '?contno?' and edorno = '?edorno?' and edortype = '?edortype?'";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql("delete from LPInsureAccTrace where" + sqlWhere);
        sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
        sqlbv1.put("edorno", edorno);
        sqlbv1.put("edortype", edortype);
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql("delete from LPInsureAccFeeTrace where" + sqlWhere);
        sqlbv2.put("contno", mLPEdorItemSchema.getContNo());
        sqlbv2.put("edorno", edorno);
        sqlbv2.put("edortype", edortype);
        map.put(sqlbv1, "DELETE");
        map.put(sqlbv2, "DELETE");
        
        return true;
    }
    
    
    /**
     * 改变Schema中的变量值
     * @return
     */
    private void ChangeSchemaField(SchemaSet tSchemaSet,String[] aFieldName)
    {
    	try
    	{
    		Field tField;
    		Class[] parameterTypes = new Class[2];
    		parameterTypes[0] = String.class;
    		parameterTypes[1] = String.class;
    		
    		Object[] obj = new Object[2];
    		for(int i = 1; i <= tSchemaSet.size();i++)
    		{
				for (int j = 0; j < aFieldName.length; j++)
				{
					Class cClass = tSchemaSet.getObj(i).getClass();
					Method tMethod = cClass.getMethod("setV", parameterTypes);
					
					obj[0] = aFieldName[j];
					if(!aFieldName[j].equals("SumPrem"))
					{
						obj[1] = mLPEdorItemSchema.getV(aFieldName[j]);
					}else
					{
						obj[1] = String.valueOf(mSumPrem);
					}
					
					Boolean iFlag = (Boolean) tMethod.invoke(tSchemaSet.getObj(i), obj);
					if(!iFlag.booleanValue()) return;
				}    			
    		}
    		
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	
    }
    
    
    /*
     * 返回批改补退费信息
     */
    private LJSPayPersonSet CreatePayPersonSet()
    {
    	
    	LJSPayPersonSet tLJSPayPersonSet =new LJSPayPersonSet();
    	LJSPayPersonSchema tLJSPayPersonSchema =new LJSPayPersonSchema();
//    	LCPolDB  tLCPolDB = new LCPolDB();    
//    	LCPolSchema  tLCPolSchema = new LCPolSchema();
//
//    	tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
//    	tLCPolSchema = tLCPolDB.query().get(1);
//    	if(tLCPolDB.mErrors.needDealError())
//    	{
//            mErrors.copyAllErrors(tLCPolDB.mErrors);
//            mErrors.addOneError("查找保单险种信息失败!");
//            return null;        		
//    	}
    	
    	ref.transFields(tLJSPayPersonSchema, mLCPolSchema);
    	
    	tLJSPayPersonSchema.setSumDuePayMoney(mLPEdorItemSchema.getGetMoney());
    	tLJSPayPersonSchema.setSumActuPayMoney(mLPEdorItemSchema.getGetMoney());
    	tLJSPayPersonSchema.setPayIntv(-1);
    	tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
    	
    	
    	tLJSPayPersonSchema.setPayType("WP");

    	tLJSPayPersonSchema.setPayAimClass("1");
    	tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
    	tLJSPayPersonSchema.setPayPlanCode(mLCPremSchema.getPayPlanCode());
    	tLJSPayPersonSchema.setDutyCode(mLCDutySchema.getDutyCode());
    	tLJSPayPersonSchema.setCurPayToDate(PubFun.calDate(mLCPremSchema.getPaytoDate(), 1, "Y", null));
    	tLJSPayPersonSchema.setLastPayToDate(mLCPremSchema.getPaytoDate());
    	tLJSPayPersonSchema.setMakeDate(mCurrentDate);
    	tLJSPayPersonSchema.setMakeTime(mCurrentTime);
    	tLJSPayPersonSchema.setModifyDate(mCurrentDate);
    	tLJSPayPersonSchema.setModifyTime(mCurrentTime);
    	
    	String tSql = "select max(Mainpolyear) from ljapayperson a where contno = '?contno?'"
    				  + " and Exists(select 1 from lcpol b where polno = a.polno and b.polno=b.mainpolno)"
    	              ;
    	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    	sqlbv.sql(tSql);
    	sqlbv.put("contno", mLPEdorItemSchema.getContNo());
    	ExeSQL tExeSQL =new ExeSQL();
    	String tMainpolyear = tExeSQL.getOneValue(sqlbv);
    	
    	tLJSPayPersonSchema.setMainPolYear(tMainpolyear);
    	
//    	if(tMainpolyear.compareTo("6")<0)
//    	{
//    		tLJSPayPersonSchema.setPayCount("1");
//    	}else
//    	{
    	//追加保费记续期保费收入 paycount和期交保费走，但首期为2
    		tSql = "select max(paycount) from ljapayperson a where contno = '?contno?'"
    			   + " and Payplancode = 'LW0410'"
    		;
    		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    		sqlbv1.sql(tSql);
    		sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
     		String tPayCount = tExeSQL.getOneValue(sqlbv1);
    		if(tPayCount !=null&&tPayCount.compareTo("2") < 0)
    		{
    			tLJSPayPersonSchema.setPayCount(2);
    		}else
    		{
    			tLJSPayPersonSchema.setPayCount(tPayCount);
    		}
//    	}
    	
    	tLJSPayPersonSet.add(tLJSPayPersonSchema);
    	
    	return tLJSPayPersonSet;
    }
    
    
    /*
     * 返回批改补退费信息
     */
    private LJSGetEndorseSet CreateEndorseSet( )
    {
      	LJSGetEndorseSchema tLJSGetEndorseSchema =new LJSGetEndorseSchema();
    	LJSGetEndorseSet tLJSGetEndorseSet =new LJSGetEndorseSet();
    	

    	
    	ref.transFields(tLJSGetEndorseSchema, mLCPolSchema);
    	
    	tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setGetMoney(mLPEdorItemSchema.getGetMoney());
    	tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
    	tLJSGetEndorseSchema.setFeeFinaType("BF");    
    	tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem+String.valueOf(mLJSPayPersonSet.get(1).getPayCount()));
    	tLJSGetEndorseSchema.setPayPlanCode(mLCPremSchema.getPayPlanCode());
    	tLJSGetEndorseSchema.setDutyCode(mLCDutySchema.getDutyCode());

        tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); //其他号码置为保全批单号
        tLJSGetEndorseSchema.setOtherNoType("3"); //保全给付
        tLJSGetEndorseSchema.setGetFlag("0");
    	
    	
    	tLJSGetEndorseSchema.setMakeDate(mCurrentDate);
    	tLJSGetEndorseSchema.setMakeTime(mCurrentTime);
    	tLJSGetEndorseSchema.setModifyDate(mCurrentDate);
    	tLJSGetEndorseSchema.setModifyTime(mCurrentTime);
    	
    	tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
    	
    	return tLJSGetEndorseSet;
    }    
    
    /**
     * 将外部传入的数据分解到本类的属性中
     * @return boolean
     */
    private boolean getInputData()
    {
        try
        {
            mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
            
            if(mLPEdorItemSchema == null)
            {
                CError.buildErr(this, "没有保全项目信息数据!");
                return false;            	
            }
            
            String sEdorAcceptNo = mLPEdorItemSchema.getEdorAcceptNo();
            String sEdorNo = mLPEdorItemSchema.getEdorNo();
            String sContNo = mLPEdorItemSchema.getContNo();
            
            dGetMoney = mLPEdorItemSchema.getGetMoney();
        	dChgPrem = mLPEdorItemSchema.getChgPrem();            
            
            LPEdorItemDB tLPEdorItemDB =new LPEdorItemDB();
            LPEdorItemSchema tLPEdorItemSchema =new LPEdorItemSchema();
            tLPEdorItemDB.setEdorAcceptNo(sEdorAcceptNo);
            tLPEdorItemDB.setEdorNo(sEdorNo);
            tLPEdorItemDB.setContNo(sContNo);
            
            tLPEdorItemSchema = tLPEdorItemDB.query().get(1);
            tLPEdorItemSchema.setGetMoney(dGetMoney);
            tLPEdorItemSchema.setChgPrem(dChgPrem);

            if(tLPEdorItemDB.mErrors.needDealError())
            {
                CError.buildErr(this, "没有保全项目信息数据!");
                return false;                	
            }
            
            mLPEdorItemSchema.setSchema(tLPEdorItemSchema);
            
        }
        catch (Exception e)
        {
            CError.buildErr(this, "接收传入数据失败!");
            return false;
        }
        if (mLPEdorItemSchema == null)
        {
            CError.buildErr(this, "传入数据有误!");
            return false;
        }

        return true;
    }

    
	/**
     * 准备往后层输出所需要的数据
     * @return boolean
     */
    private boolean prepareData()
    {
    	mResult.clear();
        mResult.add(map);
        return true;
    }

}
