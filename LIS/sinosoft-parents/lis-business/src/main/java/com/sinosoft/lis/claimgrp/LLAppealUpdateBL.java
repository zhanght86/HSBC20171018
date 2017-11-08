/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLAppealSchema;
import com.sinosoft.lis.db.LLAppealDB;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 申请申诉信息更新业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */

public class LLAppealUpdateBL
{
private static Logger logger = Logger.getLogger(LLAppealUpdateBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private MMap map = new MMap();
    private TransferData mTransferData = new TransferData();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private LLAppealSchema mLLAppealSchema = new LLAppealSchema();
    private Reflections ref= new Reflections();

    private GlobalInput mG = new GlobalInput();
    private String mClmNo = "";
    private String mRptNo = "";

    public LLAppealUpdateBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
    * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */
    public boolean submitData(VData cInputData,String cOperate)
    {
        logger.debug("----------LLAppealUpdateBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
            return false;
        logger.debug("----------after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        logger.debug("----------after dealData----------");

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------after prepareOutputData----------");

        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLAppealUpdateBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("---start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mLLAppealSchema = (LLAppealSchema) mInputData.getObjectByObjectName("LLAppealSchema", 0);

        if (mLLAppealSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAppealUpdateBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息为空!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        LLAppealSchema tLLAppealSchema = new LLAppealSchema();
        LLAppealDB tLLAppealDB = new LLAppealDB();
        tLLAppealDB.setAppealNo(mLLAppealSchema.getAppealNo());
        tLLAppealDB.getInfo();
        tLLAppealSchema = tLLAppealDB.getSchema();

        //更新理赔申诉错误表
        mLLAppealSchema.setCaseNo(tLLAppealSchema.getCaseNo());
        mLLAppealSchema.setAppealState(tLLAppealSchema.getAppealState()); //申诉/错误类型
        mLLAppealSchema.setAppealType(tLLAppealSchema.getAppealType()); //申诉状态
        mLLAppealSchema.setOperator(tLLAppealSchema.getOperator());
        mLLAppealSchema.setMakeDate(tLLAppealSchema.getMakeDate());
        mLLAppealSchema.setMakeTime(tLLAppealSchema.getMakeTime());
        mLLAppealSchema.setModifyDate(CurrentDate);
        mLLAppealSchema.setModifyTime(CurrentTime);
        mLLAppealSchema.setManageCom(tLLAppealSchema.getManageCom());

        map.put(mLLAppealSchema, "DELETE&INSERT");

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAppealUpdateBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
