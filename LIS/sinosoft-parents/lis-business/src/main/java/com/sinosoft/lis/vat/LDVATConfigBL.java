package com.sinosoft.lis.vat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LDVATConfigSet;
import com.sinosoft.lis.vschema.LDVATTaxConfigSet;
import com.sinosoft.lis.workflowmanage.ProcessDefBL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LDVATConfigBL {
	private static Logger logger = Logger.getLogger(ProcessDefBL.class);

    private static Logger log = Logger.getLogger("com.sinosoft.lis.vat");
    public LDVATConfigBL()
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
    private LDVATConfigSet mLDVATConfigSet = new LDVATConfigSet();
    private LDVATTaxConfigSet mLDVATTaxConfigSet = new LDVATTaxConfigSet();

    /** 数据操作字符串 */
    private String mOperate;
    private String mID; //主表ID
    private String taxMid; //税率表Id
    private String FeeTypeCode;
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
            tError.moduleName = "LDVATConfigBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "数据处理失败LDVATConfigBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (!checkData())
        {
            CError tError = new CError();
            tError.moduleName = "LDVATConfigBL";
            tError.functionName = "checkData";
            tError.errorMessage = "数据处理失败LDVATConfigBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //进行业务处理
        if (!dealData(cInputData)) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDVATConfigBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据处理失败LDVATConfigBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDVATConfigBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage =
                    "数据处理失败LDVATConfigBL-->prepareOutputData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) { //数据提交
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDVATConfigBL";
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
	private boolean dealData(VData cInputData) {
		 boolean tReturn = true;
	        
	        if (this.mOperate.equals("DELETE||MAIN")) 
	        {
	        	for(int i=1;i<=mLDVATConfigSet.size();i++)
	    		{
	        		mID=(String)mLDVATConfigSet.get(i).getID();
	    		}
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("delete from LDVATConfig where id='?mID?'");
				sqlbv.put("mID", mID);
	            map.put(sqlbv,"DELETE");
	            
	        	
	        	
//	        	for(int i=1;i<=mLDVATTaxConfigSet.size();i++)
//	    		{
//	        		taxMid=(String)mLDVATTaxConfigSet.get(1).getID();
//	    		}
//	        	SQLwithBindVariables taxSqlbv=new SQLwithBindVariables();
//	        	taxSqlbv.sql("delete from LDVATTaxConfig where id='?curID?' ");
//	        	taxSqlbv.put("curID", taxMid);
//				map.put(taxSqlbv,"DELETE");
	             
	        }
	        else if (this.mOperate.equals("UPDATE||MAIN")) {
	        		        	
	        	String curID=(String)mLDVATConfigSet.get(1).getID();
	        	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("delete from LDVATConfig where id='?curID?' ");
				sqlbv3.put("curID", curID);
				map.put(sqlbv3,"DELETE");
				
			
								
				String currentDate = PubFun.getCurrentDate();
	        	String currentTime = PubFun.getCurrentTime();
	        	
	        	if(mLDVATConfigSet.size()>0)
	    		{
	        		mLDVATConfigSet.get(1).setID(curID);
	        		mLDVATConfigSet.get(1).setMakeDate(currentDate);
	        		mLDVATConfigSet.get(1).setMakeTime(currentTime);
	        		mLDVATConfigSet.get(1).setModifyDate(currentDate);
	        		mLDVATConfigSet.get(1).setModifyTime(currentTime);
	        		mLDVATConfigSet.get(1).setOperator(mGlobalInput.Operator);
	    			map.put(mLDVATConfigSet,"DELETE&INSERT");
	    		}
	        	
//	        	taxMid=(String)mLDVATTaxConfigSet.get(1).getID();
//	        	SQLwithBindVariables taxSqlbv3=new SQLwithBindVariables();
//	        	taxSqlbv3.sql("delete from LDVATTaxConfig where id='?curID?' ");
//	        	taxSqlbv3.put("curID", taxMid);
//				map.put(taxSqlbv3,"DELETE");
//	        	for(int i=1;i<=mLDVATTaxConfigSet.size();i++)
//	    		{
//	        		mLDVATTaxConfigSet.get(i).setID(taxMid);
//	        		mLDVATTaxConfigSet.get(i).setConfigID(curID);
//	        		mLDVATTaxConfigSet.get(i).setMakeDate(currentDate);
//	        		mLDVATTaxConfigSet.get(i).setMakeTime(currentTime);
//	        		mLDVATTaxConfigSet.get(i).setModifyDate(currentDate);
//	        		mLDVATTaxConfigSet.get(i).setModifyTime(currentTime);
//	        		mLDVATTaxConfigSet.get(i).setOperator(mGlobalInput.Operator);
//	        		map.put(mLDVATTaxConfigSet,"DELETE&INSERT");
//	    		}
	        	
	        
	    
	        }
	        else if (this.mOperate.equals("INSERT||MAIN")) {  //同时操作俩张表
	        	String currentDate = PubFun.getCurrentDate();
	        	String currentTime = PubFun.getCurrentTime();
	        	String nextid = ""; 
	        	for(int i=1;i<=mLDVATConfigSet.size();i++)
	    		{
	        		nextid = PubFun1.CreateMaxNo("VATCONFIG", 20);
	        		mLDVATConfigSet.get(i).setID(nextid);
	        		mLDVATConfigSet.get(i).setMakeDate(currentDate);
	        		mLDVATConfigSet.get(i).setMakeTime(currentTime);
	        		mLDVATConfigSet.get(i).setModifyDate(currentDate);
	        		mLDVATConfigSet.get(i).setModifyTime(currentTime);
	        		mLDVATConfigSet.get(i).setOperator(mGlobalInput.Operator);
	    		}
//	        	for(int i=1;i<=mLDVATTaxConfigSet.size();i++)
//	    		{
//	        		String taxNextid = PubFun1.CreateMaxNo("VATCONFIG", 20);
//	        		mLDVATTaxConfigSet.get(i).setID(taxNextid);
//	        		mLDVATTaxConfigSet.get(i).setMakeDate(currentDate);
//	        		mLDVATTaxConfigSet.get(i).setMakeTime(currentTime);
//	        		mLDVATTaxConfigSet.get(i).setModifyDate(currentDate);
//	        		mLDVATTaxConfigSet.get(i).setModifyTime(currentTime);
//	        		mLDVATTaxConfigSet.get(i).setOperator(mGlobalInput.Operator);
//	        		mLDVATTaxConfigSet.get(i).setConfigID(nextid);
//	        		
//	    		}
	        	
//	        	if(mLDVATTaxConfigSet.size()>0)
//	    		{
//	    			map.put(mLDVATTaxConfigSet,"DELETE&INSERT");
//	    		}
	        	if(mLDVATConfigSet.size()>0)
	    		{
	    			map.put(mLDVATConfigSet,"DELETE&INSERT");
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
        mLDVATConfigSet=(LDVATConfigSet) cInputData.getObjectByObjectName("LDVATConfigSet",0);
        mLDVATTaxConfigSet = (LDVATTaxConfigSet) cInputData.getObjectByObjectName("LDVATTaxConfigSet",0);
//        mID=(String)mTransferData.getValueByName("RecordID");
        
        if (mGlobalInput == null) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDVATConfig";
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

}
