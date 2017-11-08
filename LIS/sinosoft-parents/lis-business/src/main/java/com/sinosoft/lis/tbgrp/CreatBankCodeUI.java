package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.tbgrp.CreatBankCodeBL;

import com.sinosoft.service.BusinessService;
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
public class CreatBankCodeUI  implements BusinessService
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

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean submitData(VData data, String Operater) {
		// TODO Auto-generated method stub
		return submitData(data);
	}

}
