package com.sinosoft.lis.vat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LDVATGrpSet;
import com.sinosoft.lis.vschema.LDVATGrpSet;
import com.sinosoft.lis.workflowmanage.ProcessDefBL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class ConfigVATRiskGrpBL {
	private static Logger logger = Logger.getLogger(ProcessDefBL.class);

    private static Logger log = Logger.getLogger("com.sinosoft.lis.vat");
    public ConfigVATRiskGrpBL()
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
    private LDVATGrpSet mLDVATGrpSet = new LDVATGrpSet();

    /** 数据操作字符串 */
    private String mOperate;
    private String mID;
    private String mBusiTypeCode;
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
            tError.moduleName = "ConfigVATRiskGrpBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "数据处理失败ConfigVATRiskGrpBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (!checkData())
        {
            CError tError = new CError();
            tError.moduleName = "ConfigVATRiskGrpBL";
            tError.functionName = "checkData";
            tError.errorMessage = "数据处理失败ConfigVATRiskGrpBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //进行业务处理
        if (!dealData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ConfigVATRiskGrpBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据处理失败ConfigVATRiskGrpBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ConfigVATRiskGrpBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage =
                    "数据处理失败ConfigVATRiskGrpBL-->prepareOutputData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) { //数据提交
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "ConfigVATRiskGrpBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
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
            tError.moduleName = "LDVATGrpBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
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
	        	for(int i=1;i<=mLDVATGrpSet.size();i++)
	    		{
	        		mID=(String)mLDVATGrpSet.get(i).getID();
	    		}
	        	 							
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("delete from LDVATGrp where id='?mID?' ");
				sqlbv.put("mID", mID);
//				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
//				sqlbv2.sql("delete from LDVATGrp where BusiTypeCode='?mBusiTypeCode?'");
//				sqlbv2.put("mBusiTypeCode", mBusiTypeCode);
	  	                
	                map.put(sqlbv,"DELETE");
	                
//	                map.put(sqlbv2,"DELETE");
	                
	        }
	        else if (this.mOperate.equals("UPDATE||MAIN")) {
	        		        	
	        	String curID=(String)mLDVATGrpSet.get(1).getID();
	        	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("delete from LDVATGrp where id='?curID?' ");
				sqlbv3.put("curID", curID);
				map.put(sqlbv3,"DELETE");
								
				String currentDate = PubFun.getCurrentDate();
	        	String currentTime = PubFun.getCurrentTime();
	        	
	        	if(mLDVATGrpSet.size()>0)
	    		{
	        		mLDVATGrpSet.get(1).setID(curID);
	        		mLDVATGrpSet.get(1).setMakeDate(currentDate);
	        		mLDVATGrpSet.get(1).setMakeTime(currentTime);
	        		mLDVATGrpSet.get(1).setModifyDate(currentDate);
	        		mLDVATGrpSet.get(1).setModifyTime(currentTime);
	        		mLDVATGrpSet.get(1).setOperator(mGlobalInput.Operator);
	    			map.put(mLDVATGrpSet,"DELETE&INSERT");
	    		}
	    
	        }
	        else if (this.mOperate.equals("INSERT||MAIN")) {
	        	String currentDate = PubFun.getCurrentDate();
	        	String currentTime = PubFun.getCurrentTime();
	        	
	        	for(int i=1;i<=mLDVATGrpSet.size();i++)
	    		{
	        		String nextid = PubFun1.CreateMaxNo("VATRATECONFIG", 20);
	        		mLDVATGrpSet.get(i).setID(nextid);
	        		mLDVATGrpSet.get(i).setMakeDate(currentDate);
	        		mLDVATGrpSet.get(i).setMakeTime(currentTime);
	        		mLDVATGrpSet.get(i).setModifyDate(currentDate);
	        		mLDVATGrpSet.get(i).setModifyTime(currentTime);
	        		mLDVATGrpSet.get(i).setOperator(mGlobalInput.Operator);
	    		}
	        	
	        	if(mLDVATGrpSet.size()>0)
	    		{
	    			map.put(mLDVATGrpSet,"DELETE&INSERT");
	    		}
	    
	        }

	        return tReturn;
	}
	
	private boolean checkData() {
	
		return true;
	}
	private boolean getInputData(VData cInputData) {
		//全局变量
//		 mTransferData=(TransferData)cInputData.getObjectByObjectName("TransferData",0);
       mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
           "GlobalInput", 0));
       mLDVATGrpSet=(LDVATGrpSet) cInputData.getObjectByObjectName("LDVATGrpSet",0);
//       mID=(String)mTransferData.getValueByName("RecordID");
       
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

}
