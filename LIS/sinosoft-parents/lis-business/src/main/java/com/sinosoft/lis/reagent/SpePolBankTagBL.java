package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

import com.f1j.ss.*;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.io.*;

import java.lang.*;

import java.text.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author GUOXIANG
 * @version 1.0
 */
import java.util.*;

public class SpePolBankTagBL
{
private static Logger logger = Logger.getLogger(SpePolBankTagBL.class);

    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mOperate = "";

//    //应收表数据 
    private LJSPaySet mLJSPaySet = new LJSPaySet();
    private LJSPaySchema mLJSPaySchema = new LJSPaySchema();  
    private LJSPaySet update_LJSPaySet = new LJSPaySet();   //准备传往后台修改的集合


    public SpePolBankTagBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 传输数据的公共方法
     * @param cInputData
     * @param cOperate
     * @return
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        if (getInputData(cInputData) == false)
        {
            return false;
        }

        if (dealData() == false)
        {
            return false;
        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        try
        {
            mLJSPaySet = (LJSPaySet) cInputData.get(0);
            mGlobalInput = (GlobalInput) cInputData.get(1);       
            mOperate = this.mGlobalInput.Operator;
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "ReAgentPlcAssignBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "前台准备的数据有误。";
            this.mErrors.addOneError(tError);
        }
        return true;
    }

    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
	        logger.debug("开始对应收主表记录进行处理~~~"+mLJSPaySet.size());
            
            //需要处理的应收记录个数
            int maincount = mLJSPaySet.size();
	        logger.debug("需要处理的应收记录个数："+maincount);
            for (int a = 1; a <= maincount; a++)
            {
            	LJSPayDB mLJSPayDB = new LJSPayDB();
                mLJSPaySchema = new LJSPaySchema();
                logger.debug("当前处理ljspay.otherno="+mLJSPaySet.get(a).getOtherNo());
                mLJSPayDB.setOtherNo(mLJSPaySet.get(a).getOtherNo());
                mLJSPaySchema=mLJSPayDB.query().get(1);
                
                mLJSPaySchema.setPayForm("1");
                mLJSPaySchema.setOperator(this.mOperate);
                mLJSPaySchema.setModifyDate(this.CurrentDate);
                mLJSPaySchema.setModifyTime(this.CurrentTime);
                update_LJSPaySet.add(mLJSPaySchema);
            }
            
           mInputData.add(update_LJSPaySet);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReAgentPlcAssignBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return this.mInputData;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "ReAgentPlcDown";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    /**
     * 准备所有要打印的数据
     * @return
     */
    private boolean dealData()
    {
        mInputData.clear();
        if (prepareOutputData() == false)
        {
            return false;
        }

        logger.debug("Start SpePolBankTagBL Submit...");
        SpePolBankTagBLS tSpePolBankTagBLS = new SpePolBankTagBLS();
        tSpePolBankTagBLS.submitData(mInputData, mOperate);
        logger.debug("End SpePolBankTagBL Submit...");
        if (tSpePolBankTagBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tSpePolBankTagBLS.mErrors);
            CError tError = new CError();
            tError.moduleName = "ReAgentPlcAssignBL";
            tError.functionName = "submitDat";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }
}
