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
 * <p>Description: 解除保单挂起逻辑处理类</p>
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */

public class LLLcContReleaseBL
{
private static Logger logger = Logger.getLogger(LLLcContReleaseBL.class);

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

    public LLLcContReleaseBL()
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
        logger.debug("----------LLLcContReleaseBL begin submitData----------");
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
        if (!dealData())//进行业务处理
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
//        PubSubmit tPubSubmit = new PubSubmit();
//        if (!tPubSubmit.submitData(mInputData, cOperate))// @@错误处理
//        {
//            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLLcContReleaseBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors.addOneError(tError);
//            return false;
//        }
//        logger.debug("--LLLcContReleaseBL.submitData--");
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

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        mClmNo   = (String) mTransferData.getValueByName("RptNo");     //赔案号

        if (mClmNo == null)// @@错误处理
        {
            CError tError = new CError();
            tError.moduleName = "LLLcContReleaseBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的赔案号为空!";
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
            tError.moduleName = "LLLcContReleaseBL";
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
    private boolean dealData()
    {
        logger.debug("------start dealData-----");
        
		// 判断该合同是否被挂起
		LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, mClmNo, "4", "");
		if (!tLCContHangUpBL.deleteHungUpForKey()) {
			mErrors.copyAllErrors(tLCContHangUpBL.mErrors);
			return false;
		} else {
			VData tempVData = new VData();
			tempVData = tLCContHangUpBL.getResult();
			MMap tMap = new MMap();
			tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			map.add(tMap);
		}

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
            mResult.clear();
            mResult.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLLcContReleaseBL";
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
