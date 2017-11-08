package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.*;

/*
 * <p>ClassName:  </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database:
 * @CreateDate：
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.*;

public class ProcessDefBL 
{
private static Logger logger = Logger.getLogger(ProcessDefBL.class);

    private static Logger log = Logger.getLogger("com.sinosoft.lis.workflowmanage");
    public ProcessDefBL()
    {
   
    }

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    private MMap map = new MMap();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();

    /** 数据操作字符串 */
    private String mOperate;
    private String mProcessID;
    private String mVersion;

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
            tError.moduleName = "WorkDayDefBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "数据处理失败WorkDayDefBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (!checkData())
        {
            CError tError = new CError();
            tError.moduleName = "PWTransitionTimeBL";
            tError.functionName = "checkData";
            tError.errorMessage = "数据处理失败PDAccountManagertBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //进行业务处理
        if (!dealData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "WorkDayDefBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据处理失败WorkDayDefBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PWTransitionTimeBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage =
                    "数据处理失败PWTransitionTimeBL-->prepareOutputData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) { //数据提交
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "PWTransitionTimeBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    //校验
    private boolean checkData() 
    {
        return true;
    }
    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() 
    {
        boolean tReturn = true;
        
        if (this.mOperate.equals("DELETE||MAIN")) 
        {
			boolean isDelete=true;
			
			ExeSQL tExeSQL=new ExeSQL();
			
			//看有没有用过  正在用
			String sql="select count(*) from  lwmission where processid='?mProcessID?' and version='?mVersion?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("mProcessID", mProcessID);
			sqlbv.put("mVersion", mVersion);
			String count=tExeSQL.getOneValue(sqlbv);
			if(count!=null&&!count.equals("")&&!count.equals("0"))
			{
                CError tError = new CError();
                tError.moduleName = "LWFlowBL";
                tError.functionName = "dealData";
                tError.errorMessage = "该流程已经被使用，不能删除";
                this.mErrors.addOneError(tError);
                return false;
			}
			else
			{
				sql="select count(*) from lbmission where processid='?mProcessID?' and version='?mVersion?'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("mProcessID", mProcessID);
				sqlbv1.put("mVersion", mVersion);
				count=tExeSQL.getOneValue(sqlbv1);
				if(count!=null&&!count.equals("")&&!count.equals("0"))
				{
	                CError tError = new CError();
	                tError.moduleName = "LWFlowBL";
	                tError.functionName = "dealData";
	                tError.errorMessage = "该流程已经被使用，不能删除";
	                this.mErrors.addOneError(tError);
	                return false;
				}
			}
			
            if(isDelete)
            {
				LWProcessXMLSchema tLWProcessXMLSchema=new LWProcessXMLSchema();           			
    			tLWProcessXMLSchema.setProcessID(mProcessID);
    			tLWProcessXMLSchema.setVersion(mVersion);
    			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql("delete from LWProcess where  processid='?mProcessID?' and version='?mVersion?'");
				sqlbv2.put("mProcessID", mProcessID);
				sqlbv2.put("mVersion", mVersion);
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("delete from LWCondition where  processid='?mProcessID?' and version='?mVersion?'");
				sqlbv3.put("mProcessID", mProcessID);
				sqlbv3.put("mVersion", mVersion);
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql("delete from LWProcessTrans where  processid='?mProcessID?' and version='?mVersion?'");
				sqlbv4.put("mProcessID", mProcessID);
				sqlbv4.put("mVersion", mVersion);
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql("delete from LWTransTime where timeid =(select timeid from LWProcess where  processid='?mProcessID?' and version='?mVersion?')");
				sqlbv5.put("mProcessID", mProcessID);
				sqlbv5.put("mVersion", mVersion);
                map.put(tLWProcessXMLSchema, "BLOBDELETE");   
                
                map.put(sqlbv2,"DELETE");
                
                map.put(sqlbv3,"DELETE");
                
				map.put(sqlbv4,"DELETE"); 
				map.put(sqlbv5, "DELETE");
				//删除号，防止废号，从号上去判断，本身不太可靠，但也没有别的办法，主要是为了和原来的兼容，如果不兼容，完全不用牵涉到最大号
				//6，01，00000，15  为例
				
//	            String BuType = mProcessID.substring(0,3);
//	            String Ints = mProcessID.substring(4,10);
//	            int Sint = Integer.parseInt(Ints); 				
//	            map.put("delete from ldmaxno where notype='"+BuType+Sint+"' and nolimit='SN'", "DELETE");
            }
        }

        return tReturn;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
            "GlobalInput", 0));
        mTransferData=(TransferData)cInputData.getObjectByObjectName("TransferData",0);
        
        if (mGlobalInput == null) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "WorkDayDefBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的信息！";
            this.mErrors.addOneError(tError);
            return false;
        }
        mProcessID=(String)mTransferData.getValueByName("ProcessID");
        mVersion=(String)mTransferData.getValueByName("Version");
        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData() 
    {
        try 
        {
            mInputData.clear();
            mInputData.add(map);
        } 
        catch (Exception ex) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "WorkDayDefBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult() 
    {
        return this.mResult;
    }
}
