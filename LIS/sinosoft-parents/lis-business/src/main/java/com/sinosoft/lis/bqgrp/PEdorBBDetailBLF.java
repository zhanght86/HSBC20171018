package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.EdorDetail;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个单被保人基本信息变更</p>
 */
public class PEdorBBDetailBLF implements EdorDetail
{
private static Logger logger = Logger.getLogger(PEdorBBDetailBLF.class);

    public PEdorBBDetailBLF()
    {
    }

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    /** 全局数据 */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
    private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
    private LPContSchema mLPContSchema=new LPContSchema();
    private GlobalInput mGlobalInput = new GlobalInput();

    /**
     * 数据提交的公共方法
     * @param: cInputData 传入的数据
     * @param cOperate 数据操作字符串
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        mInputData = (VData) cInputData.clone();

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug("------end getInputData-------");

        //数据准备操作
        if (!dealData())
        {
            return false;
        }
        logger.debug("------end dealData-------");

        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "PEdorBBDetailBLF";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 返回处理错误
     * @return: CErrors
     */
      public CErrors getErrors()
      {
          return mErrors;
      }

    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData()
    {
        try
        {
            mLPInsuredSchema = (LPInsuredSchema) mInputData.getObjectByObjectName("LPInsuredSchema", 0);
            mLPAddressSchema = (LPAddressSchema) mInputData.getObjectByObjectName("LPAddressSchema", 0);
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
            mLPContSchema = (LPContSchema) mInputData.getObjectByObjectName("LPContSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName( "GlobalInput",0);
            if (mLPInsuredSchema == null || mLPAddressSchema == null || mLPEdorItemSchema == null || mGlobalInput == null)
            {
               logger.debug("---getInputData false----");
               return false;
            }
        }
        catch (Exception e)
        {
            CError.buildErr(this, "接收数据失败");
            return false;
        }
        return true;
    }

    /**
     * 准备需要保存的数据
     * 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
     */
    private boolean dealData()
    {
        PEdorBBDetailBL tPEdorBBDetailBL = new PEdorBBDetailBL();
        if (!tPEdorBBDetailBL.submitData(mInputData,mOperate)) {
            this.mErrors.copyAllErrors(tPEdorBBDetailBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "PEdorBBDetailBLF";
            tError.functionName = "dealData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mResult.clear();
        mResult = tPEdorBBDetailBL.getResult();
        return true;
    }
}
