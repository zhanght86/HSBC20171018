	/**
	 * AM --保全追加保费 Additional premiums
	 * LIUZHAO 20080111
	 */
	package com.sinosoft.lis.bq;
	
	import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
	
	public class PEdorWPConfirmBL  implements EdorConfirm{
	
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
	
	    private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); //分红业绩报告书打印记录
	    private LCPolSchema mLCPolSchema = new LCPolSchema();
	    private LCPolDB mLCPolDB = new LCPolDB();
	
	
	    
		private VData mOutDta = new VData();
		private ExeSQL mExeSQL = new ExeSQL();
	
		private String mCurrentDate = PubFun.getCurrentDate();
		private String mCurrentTime = PubFun.getCurrentTime();
		private String mEnterAccDate = "";
		
		private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema(); 
		private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
		private LCContSchema mLCContSchema =new LCContSchema();
	
		private Reflections ref = new Reflections();
		
    	//帐户相关
		private	LCInsureAccSet mLCInsureAccSet =new LCInsureAccSet();
		private	LCInsureAccClassSet mLCInsureAccClassSet =new LCInsureAccClassSet();
		private	LCInsureAccTraceSet mLCInsureAccTraceSet =new LCInsureAccTraceSet();    	

		private	LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
		private	LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		private	LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet =new LCInsureAccFeeTraceSet();		

		private	LPInsureAccSet mLPInsureAccSet =new LPInsureAccSet();
		private	LPInsureAccClassSet mLPInsureAccClassSet =new LPInsureAccClassSet();
		private	LPInsureAccTraceSet mLPInsureAccTraceSet =new LPInsureAccTraceSet();    	

		private	LPInsureAccFeeSet mLPInsureAccFeeSet = new LPInsureAccFeeSet();
		private	LPInsureAccClassFeeSet mLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		private LPInsureAccFeeTraceSet mLPInsureAccFeeTraceSet =new LPInsureAccFeeTraceSet();    	
    	
		public PEdorWPConfirmBL( ) {
	
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
	
//			if(!pubSubmitData())
//			{
//				return false;
//			}
	
			return true;
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
		 * 统一提交
		 * @return
		 */
//		public boolean pubSubmitData()
//		{
//	        PubSubmit tSubmit = new PubSubmit();
//	        if (!tSubmit.submitData(mOutDta, "")) //数据提交
//	        {
//	            // @@错误处理
//	            this.mErrors.copyAllErrors(tSubmit.mErrors);
//	            CError tError = new CError();
//	            tError.moduleName = "DispatchBonusBL";
//	            tError.functionName = "normalDispatch";
//	            tError.errorMessage = "数据提交失败!";
//	            this.mErrors.addOneError(tError);
//	            return false;
//	        }
//	        return true;
//		}
	
	    
	
	 
		
	    /**
	     * 根据前面的输入数据，进行逻辑处理
	     * @return boolean
	     */
	    private boolean dealData()
	    {
//	    	LCContSchema tLCContSchema =new LCContSchema();
//	    	LCPolSchema  tLCPolSchema = new LCPolSchema();
//	    	LCDutySchema tLCDutySchema =new LCDutySchema();
//	    	LCPremSchema tLCPremSchema =new LCPremSchema();
//	    	
//	    	
//	    	LPContSchema tLPContSchema =new LPContSchema();
//	    	LPPolSchema  tLPPolSchema = new LPPolSchema();
//	    	LPDutySchema tLPDutySchema =new LPDutySchema();
//	    	LPPremSchema tLPPremSchema =new LPPremSchema();  
//	    	
//	    	
//	    	//备份C表
//	    	LCContSchema bLCContSchema =new LCContSchema();
//	    	LCPolSchema  bLCPolSchema = new LCPolSchema();
//	    	LCDutySchema bLCDutySchema =new LCDutySchema();
//	    	LCPremSchema bLCPremSchema =new LCPremSchema();	
//	    	
//	    	LPContSchema bLPContSchema =new LPContSchema();
//	    	LPPolSchema  bLPPolSchema = new LPPolSchema();
//	    	LPDutySchema bLPDutySchema =new LPDutySchema();
//	    	LPPremSchema bLPPremSchema =new LPPremSchema();	 	    	
//
//	    	
//	    	LPContDB tLPContDB =new LPContDB();
//	    	LPPolDB  tLPPolDB = new LPPolDB();
//	    	LPDutyDB tLPDutyDB =new LPDutyDB();
//	    	LPPremDB tLPPremDB =new LPPremDB();
//	    	   	
//	    	
//	    	LCContDB tLCContDB =new LCContDB();
//	    	LCPolDB  tLCPolDB = new LCPolDB();
//	    	LCDutyDB tLCDutyDB =new LCDutyDB();
//	    	LCPremDB tLCPremDB =new LCPremDB();
//	    	
//	    	LCContDB bLCContDB =new LCContDB();
//	    	LCPolDB  bLCPolDB = new LCPolDB();
//	    	LCDutyDB bLCDutyDB =new LCDutyDB();
//	    	LCPremDB bLCPremDB =new LCPremDB();	    	
//	    	
//	    	LCContSet tLCContSet =new LCContSet();
//	    	LCPolSet tLCPolSet = new LCPolSet();
//	    	LCDutySet tLCDutySet =new LCDutySet();
//	    	LCPremSet tLCPremSet =new LCPremSet();
//	    	
//	    	LCContSet bLCContSet =new LCContSet();
//	    	LCPolSet bLCPolSet = new LCPolSet();
//	    	LCDutySet bLCDutySet =new LCDutySet();
//	    	LCPremSet bLCPremSet =new LCPremSet();	    	
//	    	
//	    	LPContSet tLPContSet =new LPContSet();
//	    	LPPolSet tLPPolSet = new LPPolSet();
//	    	LPDutySet tLPDutySet =new LPDutySet();
//	    	LPPremSet tLPPremSet =new LPPremSet(); 	    	
//	    	
//	    	LPContSet bLPContSet =new LPContSet();
//	    	LPPolSet bLPPolSet = new LPPolSet();
//	    	LPDutySet bLPDutySet =new LPDutySet();
//	    	LPPremSet bLPPremSet =new LPPremSet(); 
	    	
//	    	mLPEdorItemSchema.setMakeDate(mCurrentDate);
//	    	mLPEdorItemSchema.setMakeTime(mCurrentTime);    	
	    	mLPEdorItemSchema.setModifyDate(mCurrentDate);
	    	mLPEdorItemSchema.setModifyTime(mCurrentTime);  
//	    	String[] tFieldName = {"CurrentDate","CurrentTime","CurrentDate","CurrentTime"};
//	    	
//	    	//**********************LCCont*********************************
//	    	//保单表
//	    	System.out.println(mLPEdorItemSchema.getContNo());   
//	    	tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//	    	tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
//	    	tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	tLPContSet =tLPContDB.query();
//	    	if(tLPContDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLPContDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	tLPContSchema = tLPContSet.get(1);
//	    	ref.transFields(tLCContSchema, tLPContSchema);
//	    	mLCContSchema = tLCContSchema;
//	    	tLCContSet.add(tLCContSchema);
//	    	ChangeSchemaField(tLCContSet,tFieldName); //确认C表
//	    	
//	    	//备份保单C表
//	    	bLCContDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	bLCContSet =bLCContDB.query();
//	    	if(bLCContDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLCContDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	bLCContSchema = bLCContSet.get(1);
//	    	ref.transFields(bLPContSchema, tLPContSchema);	    	
//	    	ref.transFields(bLPContSchema, bLCContSchema);	
//	    	
//	    	
//	    	map.put(tLCContSet, "DELETE&INSERT");
//	    	map.put(bLPContSchema, "DELETE&INSERT"); //备份C表
	    	
	    	
	    	
	    	//**************LCPol*********************************
//	    	System.out.println(mLPEdorItemSchema.getContNo());   
//	    	tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//	    	tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
//	    	tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	tLPPolSet =tLPPolDB.query();
//	    	if(tLPPolDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLPPolDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	tLPPolSchema = tLPPolSet.get(1);
//	    	ref.transFields(tLCPolSchema, tLPPolSchema);
//	    	tLCPolSet.add(tLCPolSchema);
//	    	ChangeSchemaField(tLCPolSet,tFieldName); //确认C表
	    	
	    
//	    	bLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	bLCPolSet =bLCPolDB.query();
//	    	if(bLCPolDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLCPolDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	bLCPolSchema = bLCPolSet.get(1);
//	    	ref.transFields(bLPPolSchema, tLPPolSchema);	    	
//	    	ref.transFields(bLPPolSchema, bLCPolSchema);	
	    	
//	    	map.put(tLCPolSet, "DELETE&INSERT");
//	    	map.put(bLPPolSchema, "DELETE&INSERT"); //备份C表
//	    	
//	    	//******************LCPrem**************************************
//	    	System.out.println(mLPEdorItemSchema.getContNo());   
//	    	tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//	    	tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
//	    	tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	tLPPremSet =tLPPremDB.query();
//	    	if(tLPPremDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLPPremDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	tLPPremSchema = tLPPremSet.get(1);
//	    	ref.transFields(tLCPremSchema, tLPPremSchema);
//	    	tLCPremSet.add(tLCPremSchema);
//	    	ChangeSchemaField(tLCPremSet,tFieldName); //确认C表
	    	
	    	//备份保单C表
//	    	String tSql = "select * from lcprem a where contno = '"+mLPEdorItemSchema.getContNo()+"'"
//		     +" and exists(  select 1 from lmdutypay "
//		     		      +" where payplancode = a.payplancode"
//		                  +" and payaimclass = '1')"//追加保费项
//		                  ;
//	    	System.out.println("test:-tSql: "+tSql);
//	    	bLCPremSet = bLCPremDB.executeQuery(tSql);	    	
//	    	if(bLCPremDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLCPremDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	bLCPremSchema = bLCPremSet.get(1);
//	    	ref.transFields(bLPPremSchema, tLPPremSchema);	    	
//	    	ref.transFields(bLPPremSchema, bLCPremSchema);	
//	    	
//	    	
//	    	map.put(tLCPremSet, "DELETE&INSERT");
//	    	map.put(bLPPremSchema, "DELETE&INSERT"); //备份C表
	    	
	    	//*******************LCDuty***************************************
//	    	System.out.println(mLPEdorItemSchema.getContNo());   
//	    	tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//	    	tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
//	    	tLPDutyDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	tLPDutySet =tLPDutyDB.query();
//	    	if(tLPDutyDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLPDutyDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	tLPDutySchema = tLPDutySet.get(1);
//	    	ref.transFields(tLCDutySchema, tLPDutySchema);
//	    	tLCDutySet.add(tLCDutySchema);
//	    	ChangeSchemaField(tLCDutySet,tFieldName); //确认C表
//	    	
//	    	//备份保单C表
//	    	bLCDutyDB.setContNo(mLPEdorItemSchema.getContNo());
//	    	bLCDutySet =bLCDutyDB.query();
//	    	if(bLCDutyDB.mErrors.needDealError())
//	    	{
//	            mErrors.copyAllErrors(tLCDutyDB.mErrors);
//	            mErrors.addOneError("查找保单信息失败!");
//	            return false;    		
//	    	}
//	    	bLCDutySchema = bLCDutySet.get(1);
//	    	ref.transFields(bLPDutySchema, tLPDutySchema);	    	
//	    	ref.transFields(bLPDutySchema, bLCDutySchema);	
//	    	
//	    	map.put(tLCDutySet, "DELETE&INSERT");
//	    	map.put(bLPDutySchema, "DELETE&INSERT"); //备份C表
	    	
	    	String tSql = "";
	    	//***********修改paydate 置为业务到帐日期*******************************
	    	if(mLPEdorItemSchema.getEdorType().equals("AM")){//因为别的保全项目可能调用此/溢交选择
	    		tSql=" select * from ljapayperson where contno='?contno?' "+
	            " and payno=(Select payno From ljapay where incomeno='?incomeno?'"+
		            " and incometype='10') ";
	    		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	    		sqlbv.sql(tSql);
	    		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
	    		sqlbv.put("incomeno", mLPEdorItemSchema.getEdorAcceptNo());
				LJAPayPersonDB pLJAPayPersonDB=new LJAPayPersonDB();
				LJAPayPersonSet pLJAPayPersonSet=pLJAPayPersonDB.executeQuery(sqlbv);
				if(pLJAPayPersonSet.size()<1)
				{
				    return false;
				}
				mEnterAccDate=pLJAPayPersonSet.get(1).getEnterAccDate();	
	    	}
	    	
	    	//**********帐户处理**************************************************
	    	//帐户轨迹表
	    	LPInsureAccTraceSchema tLPInsureAccTraceSchema = null;
	    	LCInsureAccTraceSchema tLCInsureAccTraceSchema = null;
	    	LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
	    	LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
	    	LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
	    	
	    	tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	    	tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
	    	tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
	    	for(int m= 1; m <= tLPInsureAccTraceSet.size();m++)
	    	{
	    		tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
	    		tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
	    		
	    		tLPInsureAccTraceSchema = tLPInsureAccTraceSet.get(m);
		    	tLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
		    	tLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
		    	tLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
		    	tLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
		    	tLPInsureAccTraceSchema.setValueDate(mCurrentDate);
		    	//当溢缴转追加调用这一段处理时，mEnterAccDate 实际是为空值的  modify by LH 2009-03-24
		    	if (mEnterAccDate != null && !mEnterAccDate.equals(""))
		    	{
		    		tLPInsureAccTraceSchema.setPayDate(mEnterAccDate);
		    	}
		    	ref.transFields(tLCInsureAccTraceSchema,tLPInsureAccTraceSchema);
		    	tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
	    	}

	    	
	    	//管理费轨迹表
	    	LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = null;
	    	LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = null;
	    	LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
	    	LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
	    	LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
	    	
	    	tLPInsureAccFeeTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	    	tLPInsureAccFeeTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
	    	tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB.query();
	    	for(int n =1; n <= tLPInsureAccFeeTraceSet.size();n++)
	    	{
	    		tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
		    	tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
		    	
		    	tLPInsureAccFeeTraceSchema = tLPInsureAccFeeTraceSet.get(n);
		    	tLPInsureAccFeeTraceSchema.setMakeDate(mCurrentDate);
		    	tLPInsureAccFeeTraceSchema.setMakeTime(mCurrentTime);
		    	tLPInsureAccFeeTraceSchema.setModifyDate(mCurrentDate);
		    	tLPInsureAccFeeTraceSchema.setModifyTime(mCurrentTime);	   
		    	//当溢缴转追加调用这一段处理时，mEnterAccDate 实际是为空值的  modify by LH 2009-03-24
		    	if (mEnterAccDate != null && !mEnterAccDate.equals(""))
		    	{
		    		// update by Vicky 20091015 start
		    		//tLPInsureAccTraceSchema.setPayDate(mEnterAccDate);
		    		tLPInsureAccFeeTraceSchema.setPayDate(mEnterAccDate);
		    		// update by Vicky 20091015 end
		    	}
		    	ref.transFields(tLCInsureAccFeeTraceSchema,tLPInsureAccFeeTraceSchema);
		         //营改增 add zhangyingfeng 2016-07-14
		          //价税分离 计算器
		          TaxCalculator.calBySchema(tLCInsureAccFeeTraceSchema);
		          //end zhangyingfeng 2016-07-14
		    	tLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
	    	}

//	    	管理费分类表
	    	LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = null;
	    	LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = null;
	    	LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
	    	LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
	    	LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
	    	
	    	tLPInsureAccClassFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	    	tLPInsureAccClassFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
	    	tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();
	    	for(int n =1; n <= tLPInsureAccClassFeeSet.size();n++)
	    	{
	    		tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
		    	tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
		    	
		    	tLPInsureAccClassFeeSchema = tLPInsureAccClassFeeSet.get(n);
		    	tLPInsureAccClassFeeSchema.setMakeDate(mCurrentDate);
		    	tLPInsureAccClassFeeSchema.setMakeTime(mCurrentTime);
		    	tLPInsureAccClassFeeSchema.setModifyDate(mCurrentDate);
		    	tLPInsureAccClassFeeSchema.setModifyTime(mCurrentTime);	   
		    	tLPInsureAccClassFeeSchema.setBalaDate(mCurrentDate);
		    	tLPInsureAccClassFeeSchema.setBalaTime(mCurrentTime);
		    	ref.transFields(tLCInsureAccClassFeeSchema,tLPInsureAccClassFeeSchema);
		    	tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
	    	}
	    	//管理费表
	    	LPInsureAccFeeSchema tLPInsureAccFeeSchema = null;
	    	LCInsureAccFeeSchema tLCInsureAccFeeSchema = null;
	    	LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
	    	LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
	    	LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
	    	
	    	tLPInsureAccFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	    	tLPInsureAccFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
	    	tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
	    	for(int n =1; n <= tLPInsureAccFeeSet.size();n++)
	    	{
	    		tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
		    	tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
		    	
		    	tLPInsureAccFeeSchema = tLPInsureAccFeeSet.get(n);
		    	tLPInsureAccFeeSchema.setMakeDate(mCurrentDate);
		    	tLPInsureAccFeeSchema.setMakeTime(mCurrentTime);
		    	tLPInsureAccFeeSchema.setModifyDate(mCurrentDate);
		    	tLPInsureAccFeeSchema.setModifyTime(mCurrentTime);	   
		    	//tLPInsureAccFeeSchema.setBalaDate(mCurrentDate);
		    	tLPInsureAccFeeSchema.setBalaTime(mCurrentTime);
		    	ref.transFields(tLCInsureAccFeeSchema,tLPInsureAccFeeSchema);
		    	tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
	    	}
	    	if (mEnterAccDate != null && !mEnterAccDate.equals(""))
	    	{
	    		mLPEdorItemSchema.setEdorValiDate(mEnterAccDate);
	    	}
	    	map.put(mLPEdorItemSchema, "UPDATE");
	    	map.put(tLCInsureAccTraceSet, "DELETE&INSERT");
	    	map.put(tLCInsureAccFeeTraceSet, "DELETE&INSERT");
	    	map.put(tLCInsureAccClassFeeSet, "DELETE&INSERT");
	    	map.put(tLCInsureAccFeeSet, "DELETE&INSERT");
	    	
	    	//***********渠道应收处理**********************************************
	        //处理LASPayPerson数据
//	        String strSQL=" select getnoticeno from LJapay "
//	        	          +" where IncomeNo='"+mLPEdorItemSchema.getEdorAcceptNo()+"'"
//	        	          +" and IncomeType='10' and otherno='"+mLPEdorItemSchema.getEdorAcceptNo()+"'";
//			ExeSQL tes = new ExeSQL();
//			String sGetNoticeNo = tes.getOneValue(strSQL);
//			if (sGetNoticeNo == null || sGetNoticeNo.trim().equals("")) 
//			{
//			  CError.buildErr(this, "查询缴费通知书号码失败!");
//			  return false;
//			}
//			//删除主险或部分附加险（非NS产生）
//			map.put("delete from laspayperson where 1=1 " +
//			       " and lastpaytodate = (select min(lastpaytodate) from ljapayperson where getnoticeno = '" +
//			       sGetNoticeNo +
//			       "' and contno = '" + mLPEdorItemSchema.getContNo() +
//			       "' and paytype = 'AM') and contno = '" +
//			       mLPEdorItemSchema.getContNo() + "' and paytype = 'ZC'",
//			       "DELETE");
//	        LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
//	        tLJAPayPersonDB.setContNo(mLPEdorItemSchema.getContNo());
//	        tLJAPayPersonDB.setGetNoticeNo(sGetNoticeNo);
//	        mLJAPayPersonSet = tLJAPayPersonDB.query();
//	        if (mLJAPayPersonSet == null || mLJAPayPersonSet.size() < 1) {
//	            mErrors.addOneError("查询个人实收表信息失败!");
//	            return false;
//	        }
//	        if (!this.CreateLASPayPerson()) {
//	            return false;
//	        }

	    	
	        return true;
	    }
	
	    
	    
	    /**
	     * 生成LASPayPerson数据
	     * @return boolean
	     */
	    private boolean CreateLASPayPerson() {

	        LASPayPersonSet tLASPayPersonSet = new LASPayPersonSet();
	        LASPayPersonSchema tLASPayPersonSchema;
	        Reflections tRef = new Reflections();
	        String sPolType = "";
	        String sBranchSeries = "";
	        try {
	            ExeSQL tES = new ExeSQL();
	            String QueryPolType =
	                    "select poltype from lacommisiondetail where 1=1"
	                    + " and grpcontno = '?grpcontno?'"
	                    + " and agentcode = '?agentcode?'";
	            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	            sqlbv1.sql(QueryPolType);
	            sqlbv1.put("grpcontno", mLCContSchema.getContNo());
	            sqlbv1.put("agentcode", mLCContSchema.getAgentCode());
	            sPolType = tES.getOneValue(sqlbv1);
	            String QuerySeries =
	                    "select branchseries from labranchgroup where 1=1"
	                    + " and agentgroup = '?agentgroup?'";
	            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	            sqlbv2.sql(QuerySeries);
	            sqlbv2.put("agentgroup", mLCContSchema.getAgentGroup());
	            sBranchSeries = tES.getOneValue(sqlbv2);
	        } catch (Exception e) {
	            e.printStackTrace();
	            CError.buildErr(this, "查询代理信息异常!");
	            return false;
	        }
	        for (int i = 1; i <= mLJAPayPersonSet.size(); i++) {
	            tLASPayPersonSchema = new LASPayPersonSchema();
	            tRef.transFields(tLASPayPersonSchema, mLJAPayPersonSet.get(i));
	            tLASPayPersonSchema.setActuPayFlag("1");
	            tLASPayPersonSchema.setPolType(sPolType);
	            tLASPayPersonSchema.setBranchSeries(sBranchSeries);
	            tLASPayPersonSchema.setPayType("ZC");
	            tLASPayPersonSchema.setOperator(mGlobalInput.Operator);
	            tLASPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
	            tLASPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
	            tLASPayPersonSet.add(tLASPayPersonSchema);
	        }
	        map.put(tLASPayPersonSet, "DELETE&INSERT");
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
						obj[1] = mLPEdorItemSchema.getV(aFieldName[j]);
						
						Boolean iFlag = (Boolean) tMethod.invoke(tSchemaSet.getObj(i), obj);
						if(!iFlag.booleanValue()) return;
					}    			
	    		}
	    		
	    	}catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    	
	    }
	    
	    /**
	     * 改变Schema中的变量值
	     * @return
	     */
	    private SchemaSet ChangeSchemaField(String tTableName,String[] aFieldName)
	    {
	    	SchemaSet RSchemaSet = new SchemaSet();
	    	try
	    	{
	    		Class tClass =Class.forName("com.sinosoft.lis.bq." +tTableName + "Set");
	    		SchemaSet pSchemaSet = (SchemaSet)tClass.newInstance();
	    		for(int i = 0; i <= pSchemaSet.size();i++)
	    		{
	    			Class dClass =Class.forName("com.sinosoft.lis.bq." +tTableName + "Schema");
	    			Schema pSchema = (Schema)dClass.newInstance();
	    			for(int j = 0; j <= aFieldName.length;j++)
	    			{
		    			pSchema.setV(aFieldName[j], mLPEdorItemSchema.getV(aFieldName[j]));
	    			}
	    			pSchemaSet.add(pSchema);	    			
	    		}
				RSchemaSet = pSchemaSet;
	    		
	    	}catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    	
	    	return RSchemaSet;
	    }	    
	    
	    /**
	     * 处理帐户相关表
	     * @return
	     */
	    private boolean PrepareAccSchemaSet()
	    {
   		    	
	    	return true;
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
	            
	        }
	        catch (Exception e)
	        {
	            CError.buildErr(this, "接收传入数据失败!");
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
	        mResult.add(map);
	        return true;
	    }
	
	}
