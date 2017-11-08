package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author 张星
 * @version 1.0
 */

public class LLSecondManuUWUI
{
private static Logger logger = Logger.getLogger(LLSecondManuUWUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    private VData mInputData;

    /** 数据操作字符串 */
    private String mOperate;

    public LLSecondManuUWUI()
    {
    }

    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        this.mOperate = cOperate;
        LLSecondManuUWBL tLLSecondManuUWBL = new LLSecondManuUWBL();
        //如果有需要处理的错误，则返回
        logger.debug("-------- Start!---------");
        if (tLLSecondManuUWBL.submitData(cInputData, mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLSecondManuUWBL.mErrors);
            return false;
        }
        logger.debug("-------- End!---------");
        return true;
    }

    /**
     * 返回结果方法
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }


    public static void main(String[] args)
    {
        VData tVData = new VData();
        GlobalInput mGlobalInput = new GlobalInput();

        /** 全局变量 */
        mGlobalInput.Operator = "001";
        mGlobalInput.ComCode = "86";
        mGlobalInput.ManageCom = "86";


        LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
        tLLCUWMasterSchema.setCaseNo("90000001185");
        tLLCUWMasterSchema.setBatNo("550000000000306");
        tLLCUWMasterSchema.setContNo("230110000003100");
        tLLCUWMasterSchema.setPassFlag("2");
        tLLCUWMasterSchema.setUWIdea("保单核保测试");
        tVData.add(mGlobalInput);
        tVData.add(tLLCUWMasterSchema);

        LLSecondManuUWUI tLLSecondManuUWUI = new LLSecondManuUWUI();
        try
        {
            if (tLLSecondManuUWUI.submitData(tVData, ""))
            {

            }
            else
            {
                logger.debug("error:" +
                                   tLLSecondManuUWUI.mErrors.getError(0).
                                   errorMessage);
            }
        }
        catch (Exception e)
        {
            logger.debug("error:" + e);
        }

    }

}
