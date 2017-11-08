package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */

import com.sinosoft.utility.*;

public class LDMsgInfoUI {
private static Logger logger = Logger.getLogger(LDMsgInfoUI.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    
//业务处理相关变量
    public LDMsgInfoUI()
    {
    }

    /**
     传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        this.mInputData = cInputData;

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
    
        LDMsgInfoBL tLDMsgInfoBL = new LDMsgInfoBL();
        logger.debug("Start LDMsgInfo UI Submit...");
        if(!tLDMsgInfoBL.submitData(mInputData, mOperate)){
        	this.mErrors = tLDMsgInfoBL.mErrors;
        	return false;
        }
        logger.debug("End LDMsgInfo UI Submit...");          
        return true;
    }

    public static void main(String[] args)
    {
    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }
	}

