/*
 * @(#)PEdorAACancelBL.java	2005-12-15
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */


package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LPEdorItemSchema;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全- 加保撤销处理类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author：鲁哲
 * @version：1.0
 * @CreateDate：2007-08-29
 */
public class PEdorCSCancelBL implements EdorCancel
{
private static Logger logger = Logger.getLogger(PEdorCSCancelBL.class);

    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    private MMap map=new MMap();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    //需要撤销的保全项目
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    public PEdorCSCancelBL()
    {
    }

    /**
     * 数据提交的公共方法
     * @param cInputData 传入的数据对象
     * @param cOperate 数据操作字符串
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        if (!getInputData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        //准备往后台的数据
        if (!prepareData())
        {
            return false;
        }

        return true;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @return boolean
     */
    private boolean getInputData()
    {
        try
        {
            mGlobalInput =
                    (GlobalInput)
                    mInputData.getObjectByObjectName("GlobalInput", 0);
            mLPEdorItemSchema =  //需要撤销的保全项目
                    (LPEdorItemSchema)
                    mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            CError.buildErr(this, "接收传入数据失败!");
            return false;
        }
        if (mGlobalInput == null || mLPEdorItemSchema == null)
        {
            CError.buildErr(this, "传入数据有误!");
            return false;
        }

        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return boolean
     */
    private boolean dealData(){
    	logger.debug("=== 加保撤销处理类 ===");
    	String EdorAcceptNo = mLPEdorItemSchema.getEdorNo(); 
    	//投连保全项目AR需要删除的表

    	String delSql = "delete from  ES_DOC_PAGES where DocId = (select docid from ES_DOC_MAIN where DocCode = '?EdorAcceptNo?') and PageType='7'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(delSql);
        sqlbv.put("EdorAcceptNo", EdorAcceptNo);
         
    	map.put(sqlbv, "DELETE");

    	return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * @return boolean
     */
    private boolean prepareData()
    {
        mResult.clear();
        mResult.add(map);
        return true;
    }

    /**
     * 数据输出，供外层获取数据处理结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 错误返回，供外层获取处理错误信息
     * @return CErrors
     */
    public CErrors getErrors()
    {
        return null;
    }

}
