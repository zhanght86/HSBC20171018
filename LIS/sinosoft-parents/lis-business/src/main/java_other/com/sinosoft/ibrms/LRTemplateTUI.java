package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LRTemplateTSchema;
import com.sinosoft.lis.vschema.LRTemplateBSet;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.lis.vschema.LRTemplateTSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: AgentSystem</p>
 *
 * <p>Description: LIS - 培训-计划管理</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author duanxy
 * @version 6.0
 */
public class LRTemplateTUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LRTemplateTUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;   
    /** 计划管理中何种操作0-申请，1-修改，3-审核，4-审批*/
    //业务处理相关变量
    /** 全局数据 */ 
    private GlobalInput mGlobalInput = new GlobalInput();
    private LRTemplateTSet mLRTemplateTSet = new LRTemplateTSet();
    private LRTemplateSet mLRTemplateSet = new LRTemplateSet();
    private LRTemplateBSet mLRTemplateBSet = new LRTemplateBSet();
    
    LRTemplateTBL mLRTemplateTBL = new LRTemplateTBL();
    private VData mResult=new VData();
    public LRTemplateTUI()
    {
    }
    
    public static void main (String arg[])
    {
    	GlobalInput tG = new GlobalInput();
    	tG.ComCode="86";
    	tG.Operator="001";
    	
    	LRTemplateTSet tLRTemplateTSet=new LRTemplateTSet();
    	LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
//    	tLRTemplateTSchema.setApplyID("001");  
//		tLRTemplateTSchema.setBookID("1");  
//		tLRTemplateTSchema.setBookName("ad");
		tLRTemplateTSet.add(tLRTemplateTSchema);
		
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLRTemplateTSet);
		
		LRTemplateTUI tLRTemplateTUI  = new LRTemplateTUI();
		tLRTemplateTUI.submitData(tVData, "INSERT||MAIN");
		
		LRTemplateTBL tLRTemplateTBL  = new LRTemplateTBL();
		tLRTemplateTBL.submitData(tVData, "INSERT||MAIN");
		
	
    }

    /**
     传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("Begin LRTemplateTUI.submitData.........");
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("Start mLRTemplateTSet Submit...");
        mLRTemplateTBL.submitData(mInputData, mOperate);
        logger.debug("End LRTemplateTBL Submit...");
        //如果有需要处理的错误，则返回
        if (mLRTemplateTBL.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mLRTemplateTBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LAAgentUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        logger.debug("Begin LRTemplateTUI.getInputData.........");
        this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mLRTemplateTSet.set((LRTemplateTSet) cInputData.getObjectByObjectName("LRTemplateTSet", 0));
        this.mLRTemplateSet.set((LRTemplateSet) cInputData.getObjectByObjectName("LRTemplateSet", 0));
        this.mLRTemplateBSet.set((LRTemplateBSet) cInputData.getObjectByObjectName("LRTemplateBSet", 0));
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError.buildErr(this, "没有得到足够的信息！");
            return false;
        }
        return true;
    }

    /**
    * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {    
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
            logger.debug("Begin LRTemplateTUI.prepareOutputData.........");
            
            mInputData.add(this.mGlobalInput);
            mInputData.add(this.mLRTemplateTSet);  
            mInputData.add(this.mLRTemplateSet); 
            mInputData.add(this.mLRTemplateBSet); 
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LRTemplateTUI";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
    
    public CErrors getErrors() {
		// TODO Auto-generated method stub
		logger.debug("//////////////"+mErrors.getFirstError());
		return mErrors;
	}
    public VData getResult() {
		
		return this.mResult;
	}
}
