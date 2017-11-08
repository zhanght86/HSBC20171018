package com.sinosoft.lis.wfpic;
import org.apache.log4j.Logger;

/*
*@author:lk
*@date:20080904 
*/
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import org.jdom.Document;
import org.jdom.output.*;
import org.jdom.Element;
import org.jdom.input.*;

import java.io.*;

public class LWConditionUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(LWConditionUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
    	LWConditionBL tLWConditionBL = new LWConditionBL();
    	if(!tLWConditionBL.submitData(cInputData, cOperate))
    	{
    		this.mErrors = tLWConditionBL.mErrors;
    		this.mResult = tLWConditionBL.getResult();
    		return false;
    	}
    	else
    	{
    		this.mResult = tLWConditionBL.getResult();
    		return true;
    	}
    	
    }


    



//    public VData getResult()
//    {
//    	mResult.add(RebuildXML);
//        return this.mResult;
//    }
    public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

    
}
