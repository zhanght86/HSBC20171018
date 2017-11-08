package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.EdorDetail;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.pubfun.GlobalInput;

/**
 * <p>Title: web业务系统</p>
 *
 * <p>Description: 团体保全-保险合同效力恢复 </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: sinosfot</p>
 *
 * @author zhangtao
 * @version 1.0
 */
public class GrpEdorREDetailBLF implements EdorDetail
{
private static Logger logger = Logger.getLogger(GrpEdorREDetailBLF.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 往后面传输的数据库操作 */

    /** 全局数据 */
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private GlobalInput mGlobalInput = new GlobalInput();
    private MMap mMap = new MMap();

    public GrpEdorREDetailBLF()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     * @param: cOperate 数据操作符
     * @return: boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        logger.debug("--==  Grp BD Detail BLF  ==--");
        //得到外部传入的数据
        if (!getInputData())
        {
            return false;
        }

        //数据操作业务处理
        if (!dealData())
        {
            return false;
        }

        //准备提交后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        //数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError.buildErr(this, "数据提交失败!");
            return false;
        }
        mInputData = null;

        return true;
    }

    /**
     * 得到外部传入的数据
     * @return: boolean
     */
    private boolean getInputData()
    {
        return true;
    }


    /**
     * 数据操作业务处理
     * @return: boolean
     */
    private boolean dealData()
    {
        GrpEdorREDetailBL tGrpEdorREDetailBL = new GrpEdorREDetailBL();
        if (!tGrpEdorREDetailBL.submitData(mInputData, mOperate))
        {
            mErrors.copyAllErrors(tGrpEdorREDetailBL.getErrors());
            return false;

        }
        mResult = tGrpEdorREDetailBL.getResult();
        mMap.add((MMap) mResult.getObjectByObjectName("MMap", 0));
        return true;
    }

    /**
     * 准备提交后台的数据
     * @return: boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(mMap);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错:" + ex.toString());
            return false;
        }
        return true;
    }

    /**
     * 返回处理结果
     * @return: VData
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 返回处理结果
     * @return: VData
     */
    public CErrors getErrors()
    {
        return mErrors;
    }

}
