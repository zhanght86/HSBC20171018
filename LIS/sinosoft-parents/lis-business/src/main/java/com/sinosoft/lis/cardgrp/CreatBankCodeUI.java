package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.cardgrp.CreatBankCodeBL;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CreatBankCodeUI
{
private static Logger logger = Logger.getLogger(CreatBankCodeUI.class);

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    public CreatBankCodeUI()
    {
    }

    /**
       传输数据的公共方法
     */
    public boolean submitData(VData tInputData)
    {
        CreatBankCodeBL tCreatBankCodeBL = new CreatBankCodeBL();

        if (!tCreatBankCodeBL.submitData(tInputData))
        {
            // @@错误处理
            mInputData.clear();
            return false;
        }
        else
        {
            mInputData = tCreatBankCodeBL.getResult();
        }

        return true;
    }

    public VData getResult()
    {
        return mInputData;
    }

}
