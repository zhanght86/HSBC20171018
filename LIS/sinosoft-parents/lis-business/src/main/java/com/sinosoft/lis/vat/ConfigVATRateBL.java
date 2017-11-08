package com.sinosoft.lis.vat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDVATTaxConfigSchema;
import com.sinosoft.lis.vschema.LDVATTaxConfigSet;
import com.sinosoft.lis.workflowmanage.ProcessDefBL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class ConfigVATRateBL {

	private static Logger logger = Logger.getLogger(ProcessDefBL.class);

    private static Logger log = Logger.getLogger("com.sinosoft.lis.vat");
    public ConfigVATRateBL()
    {
   
    }

	public CErrors mErrors;
	private VData mResult = new VData();

    private MMap map = new MMap();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 全局数据 */
//    private TransferData mTransferData = new TransferData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private LDVATTaxConfigSet mLDVATTaxConfigSet = new LDVATTaxConfigSet();

    /** 数据操作字符串 */
    private String mOperate;
    private String mID;
    private String RiskGrpCode;
    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
	public boolean submitData(VData cInputData, String cOperate) {
		//将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)) 
        {
            CError tError = new CError();
            tError.moduleName = "ConfigVATRateBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "数据处理失败ConfigVATRateBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (!checkData())
        {
            CError tError = new CError();
            tError.moduleName = "ConfigVATRateBL";
            tError.functionName = "checkData";
            tError.errorMessage = "数据处理失败ConfigVATRateBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //进行业务处理
        if (!dealData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ConfigVATRateBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据处理失败ConfigVATRateBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ConfigVATRateBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage =
                    "数据处理失败ConfigVATRateBL-->prepareOutputData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) { //数据提交
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "ConfigVATRateBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
	}
	
	private boolean checkData() {
		return true;
	}

	/**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */	
	private boolean dealData() {
		 boolean tReturn = true;
	        
	        if (this.mOperate.equals("DELETE||MAIN")) 
	        {
	        	for(int i=1;i<=mLDVATTaxConfigSet.size();i++)
	    		{
	        		mID=(String)mLDVATTaxConfigSet.get(i).getID();
	        		RiskGrpCode=(String)mLDVATTaxConfigSet.get(i).getRiskGrpCode();
	    		}
	        	 							
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("delete from LDVATTaxConfig where id='?mID?' ");
				sqlbv.put("mID", mID);
//				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
//				sqlbv2.sql("delete from LDVATTaxConfig where BusiTypeCode='?mBusiTypeCode?'");
//				sqlbv2.put("mBusiTypeCode", mBusiTypeCode);
	  	                
	                map.put(sqlbv,"DELETE");
	                
//	                map.put(sqlbv2,"DELETE");
	             
	        }
	        else if (this.mOperate.equals("UPDATE||MAIN")) {
	        		        	
	        	String curID=(String)mLDVATTaxConfigSet.get(1).getID();
	        	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("delete from LDVATTaxConfig where id='?curID?' ");
				sqlbv3.put("curID", curID);
				map.put(sqlbv3,"DELETE");
								
				String currentDate = PubFun.getCurrentDate();
	        	String currentTime = PubFun.getCurrentTime();
	        	
	        	if(mLDVATTaxConfigSet.size()>0)
	    		{
	        		mLDVATTaxConfigSet.get(1).setID(curID);
	        		mLDVATTaxConfigSet.get(1).setMakeDate(currentDate);
	        		mLDVATTaxConfigSet.get(1).setMakeTime(currentTime);
	        		mLDVATTaxConfigSet.get(1).setModifyDate(currentDate);
	        		mLDVATTaxConfigSet.get(1).setModifyTime(currentTime);
	        		mLDVATTaxConfigSet.get(1).setOperator(mGlobalInput.Operator);
	    			map.put(mLDVATTaxConfigSet,"DELETE&INSERT");
	    		}
	    
	        }
	        else if (this.mOperate.equals("INSERT||MAIN")) {
	        	String currentDate = PubFun.getCurrentDate();
	        	String currentTime = PubFun.getCurrentTime();
	        	
	        	for(int i=1;i<=mLDVATTaxConfigSet.size();i++)
	    		{
	        		String nextid = PubFun1.CreateMaxNo("VATRATECONFIG", 20);
	        		mLDVATTaxConfigSet.get(i).setID(nextid);
	        		mLDVATTaxConfigSet.get(i).setMakeDate(currentDate);
	        		mLDVATTaxConfigSet.get(i).setMakeTime(currentTime);
	        		mLDVATTaxConfigSet.get(i).setModifyDate(currentDate);
	        		mLDVATTaxConfigSet.get(i).setModifyTime(currentTime);
	        		mLDVATTaxConfigSet.get(i).setOperator(mGlobalInput.Operator);
	    		}
	        	
	        	if(mLDVATTaxConfigSet.size()>0)
	    		{
	    			map.put(mLDVATTaxConfigSet,"DELETE&INSERT");
	    		}
	    
	        }

	        return tReturn;
	}
	
	private boolean prepareOutputData() {

        try 
        {
            mInputData.clear();
            mInputData.add(map);
        } 
        catch (Exception ex) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDVATTaxConfigBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
	}

	private boolean getInputData(VData cInputData) {
		//全局变量
//		 mTransferData=(TransferData)cInputData.getObjectByObjectName("TransferData",0);
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
            "GlobalInput", 0));
        mLDVATTaxConfigSet=(LDVATTaxConfigSet) cInputData.getObjectByObjectName("LDVATTaxConfigSet",0);
//        mID=(String)mTransferData.getValueByName("RecordID");
        
        if (mGlobalInput == null) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ConfigVATRate";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的信息！";
            this.mErrors.addOneError(tError);
            return false;
        }
       
        return true;
	}

	public VData getResult() {
		 return this.mResult;
	}
	
	public LDVATTaxConfigSchema returnObject ( LDVATTaxConfigSchema mLDVATTaxConfigSchema,String aConfigID){
		String currentDate = PubFun.getCurrentDate();
    	String currentTime = PubFun.getCurrentTime();
    	
    	
    		String nextid = PubFun1.CreateMaxNo("VATRATECONFIG", 20);
    		mLDVATTaxConfigSchema.setID(nextid);
    		mLDVATTaxConfigSchema.setMakeDate(currentDate);
    		mLDVATTaxConfigSchema.setMakeTime(currentTime);
    		mLDVATTaxConfigSchema.setModifyDate(currentDate);
    		mLDVATTaxConfigSchema.setModifyTime(currentTime);
    		mLDVATTaxConfigSchema.setOperator(mGlobalInput.Operator);
    		mLDVATTaxConfigSchema.setConfigID(aConfigID);
		
    	
    	return mLDVATTaxConfigSchema;
	}
}
