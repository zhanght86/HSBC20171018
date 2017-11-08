/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
import java.sql.Connection;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContHangUpStateDB;

/**
 * <p>Title: 保单挂起</p>
 * <p>Description: 保单挂起逻辑处理类</p>
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * <p>Company: Sinosoft</p>
 * @author: yuejinwei
 * @version 1.0
 */

public class LLLcContSuspendBL
{
private static Logger logger = Logger.getLogger(LLLcContSuspendBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private MMap map = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private LCContHangUpStateSchema mLCContHangUpStateSchema= new LCContHangUpStateSchema();
    private LCContHangUpStateSet mLCContHangUpStateSet= new LCContHangUpStateSet();
    private GlobalInput mG = new GlobalInput();

    private String mClmNo    = "";     //赔案号

    private TransferData mTransferData = new TransferData();

    public LLLcContSuspendBL()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
        logger.debug("----------LLLcContSuspendBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        this.mOperate = cOperate;
        if (!getInputData())//得到外部传入的数据,将数据备份到本类中
        {
            return false;
        }
        logger.debug("----------after getInputData----------");
        if (!checkInputData()) //检查数据合法性
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");
        if (!dealData(cOperate))//进行业务处理
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
        if (!tPubSubmit.submitData(mInputData, cOperate))// @@错误处理
        {
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLLcContSuspendBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("--LLLcContSuspendBL.submitData--");
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
        logger.debug("--start getInputData()");

        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mLCContHangUpStateSet = (LCContHangUpStateSet) mInputData.getObjectByObjectName("LCContHangUpStateSet", 0);

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号


        if (mLCContHangUpStateSet == null)// @@错误处理
        {
            CError tError = new CError();
            tError.moduleName = "LLLcContSuspendBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
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
        logger.debug("----------begin checkInputData----------");
        try
        {
            //检测数据
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLLcContSuspendBL";
            tError.functionName = "checkInputData";
            tError.errorMessage = "在校验输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("------start dealData-----");

        //整理数据,如果数据已存在则补充
        for (int i = 1; i <= mLCContHangUpStateSet.size(); i++)
        {
            LCContHangUpStateSchema tLCContHangUpStateSchema= new LCContHangUpStateSchema();
            LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
            tLCContHangUpStateDB.setContNo(mLCContHangUpStateSet.get(i).getContNo());
            tLCContHangUpStateDB.setInsuredNo(mLCContHangUpStateSet.get(i).getInsuredNo());
            tLCContHangUpStateDB.setPolNo(mLCContHangUpStateSet.get(i).getPolNo());
            tLCContHangUpStateDB.getInfo();
            tLCContHangUpStateSchema.setSchema(tLCContHangUpStateDB.getSchema());

            if (tLCContHangUpStateSchema.getMakeDate() != null)
            {
                mLCContHangUpStateSet.get(i).setAgentFlag(tLCContHangUpStateSchema.getAgentFlag());
                mLCContHangUpStateSet.get(i).setClaimFlag(tLCContHangUpStateSchema.getClaimFlag());
                mLCContHangUpStateSet.get(i).setNBFlag(tLCContHangUpStateSchema.getNBFlag());
                mLCContHangUpStateSet.get(i).setMakeDate(tLCContHangUpStateSchema.getMakeDate());
                mLCContHangUpStateSet.get(i).setMakeTime(tLCContHangUpStateSchema.getMakeTime());
                mLCContHangUpStateSet.get(i).setOperator(tLCContHangUpStateSchema.getOperator());
                mLCContHangUpStateSet.get(i).setStandFlag1(tLCContHangUpStateSchema.getStandFlag1());
                mLCContHangUpStateSet.get(i).setStandFlag2(tLCContHangUpStateSchema.getStandFlag2());
                mLCContHangUpStateSet.get(i).setStandFlag3(mClmNo);
                mLCContHangUpStateSet.get(i).setStandFlag4(tLCContHangUpStateSchema.getStandFlag4());
                mLCContHangUpStateSet.get(i).setStandFlag5(tLCContHangUpStateSchema.getStandFlag5());
                mLCContHangUpStateSet.get(i).setRemark(tLCContHangUpStateSchema.getRemark());
            }
            else
            {
                mLCContHangUpStateSet.get(i).setAgentFlag("0");
                mLCContHangUpStateSet.get(i).setClaimFlag("0");
                mLCContHangUpStateSet.get(i).setNBFlag("0");
                mLCContHangUpStateSet.get(i).setStandFlag3(mClmNo);//存储赔案号
                mLCContHangUpStateSet.get(i).setMakeDate(CurrentDate);
                mLCContHangUpStateSet.get(i).setMakeTime(CurrentTime);
                mLCContHangUpStateSet.get(i).setOperator(mG.ManageCom);
            }
            mLCContHangUpStateSet.get(i).setModifyDate(CurrentDate);
            mLCContHangUpStateSet.get(i).setModifyTime(CurrentTime);
        }

        map.put(mLCContHangUpStateSet, "DELETE&INSERT");

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
            tError.moduleName = "LLLcContSuspendBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    private void jbInit() throws Exception
    {
        //
    }
}
