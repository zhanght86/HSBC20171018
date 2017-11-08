package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;



/**
 * <p>Title: 提起二次核保</p>
 * <p>Description: 提起二次核保 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author zhangxing
 * @version 1.0
 */

public class SecondUWBL
{
private static Logger logger = Logger.getLogger(SecondUWBL.class);


    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();


    /** 往后面传输数据的容器 */
    private VData mInputData;


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mGlobalInput = new GlobalInput();


    //private VData mIputData = new VData();
    private TransferData mTransferData = new TransferData();
    private LLCUWBatchSet mLLCUWBatchSet = new LLCUWBatchSet();

    private LLCUWBatchSchema mLLCUWBatchSchema = new LLCUWBatchSchema();
    private LLCUWBatchSet mmLLCUWBatchSet = new LLCUWBatchSet();



    /** 数据操作字符串 */
    private String mOperator;
    private String mManageCom;
    private String mOperate;

    public SecondUWBL()
    {
    }


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
            return false;

        //校验是否有未打印的体检通知书
        if (!checkData())
            return false;

        logger.debug("Start  dealData...");

        //进行业务处理
        if (!dealData())
            return false;

        logger.debug("dealData successful!");

        //准备往后台的数据
        if (!prepareOutputData())
            return false;

        logger.debug("Start  Submit...");

        //提交数据

            PubSubmit tSubmit = new PubSubmit();
            if (!tSubmit.submitData(mResult, ""))
            {
              // @@错误处理
              this.mErrors.copyAllErrors(tSubmit.mErrors);
              CError tError = new CError();
              tError.moduleName = "LCInsuredUWBL";
              tError.functionName = "submitData";
              tError.errorMessage = "数据提交失败!";
              this.mErrors .addOneError(tError) ;
              return false;
            }

        return true;
    }


    private boolean prepareLLCUW()
    {
      logger.debug(mLLCUWBatchSet.size());
      String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
      String tNo = "";
      tNo = PubFun1.CreateMaxNo("CaseNo", tLimit);

      for(int i=1;i<=mLLCUWBatchSet.size();i++)
      {
        LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();
        tLLCUWBatchSchema = mLLCUWBatchSet.get(i).getSchema();
        logger.debug(tLLCUWBatchSchema.getContNo());
        LCContSchema tLCContSchema = new LCContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(tLLCUWBatchSchema.getContNo());
        if(!tLCContDB.getInfo())
        {
          CError tError = new CError();
          tError.moduleName = "LCContDB";
          tError.functionName = "prepareLLCUW";
          tError.errorMessage = "前台传输全局公共数据ContNo失败!";
          this.mErrors.addOneError(tError);
          return false;
        }
        tLCContSchema = tLCContDB.getSchema();

        mLLCUWBatchSchema.setGrpContNo(tLCContSchema.getGrpContNo());
        mLLCUWBatchSchema.setProposalContNo(tLCContSchema.getProposalContNo());
        mLLCUWBatchSchema.setBatNo(tNo);
        mLLCUWBatchSchema.setUWNo(1);

        mLLCUWBatchSchema.setCaseNo(tLLCUWBatchSchema.getCaseNo());
        mLLCUWBatchSchema.setContNo(tLLCUWBatchSchema.getContNo());

        mLLCUWBatchSchema.setAppntName(tLLCUWBatchSchema.getAppntName());
        mLLCUWBatchSchema.setInsuredNo(tLLCUWBatchSchema.getInsuredNo());
        mLLCUWBatchSchema.setClaimRelFlag("0");
        mLLCUWBatchSchema.setState("0");

        mLLCUWBatchSchema.setHealthImpartNo1(tLLCUWBatchSchema.getHealthImpartNo1());
        mLLCUWBatchSchema.setHealthImpartNo2(tLLCUWBatchSchema.getHealthImpartNo2());
        mLLCUWBatchSchema.setNoImpartDesc(tLLCUWBatchSchema.getNoImpartDesc());

        mLLCUWBatchSchema.setOperator(mGlobalInput.Operator);
        mLLCUWBatchSchema.setManageCom(mGlobalInput.ManageCom);
        mLLCUWBatchSchema.setMakeDate(PubFun.getCurrentDate());
        mLLCUWBatchSchema.setMakeTime(PubFun.getCurrentTime());
        mLLCUWBatchSchema.setModifyDate(PubFun.getCurrentDate());
        mLLCUWBatchSchema.setModifyTime(PubFun.getCurrentTime());

        LLCUWBatchSchema ttLLCUWBatchSchema = new LLCUWBatchSchema();
        ttLLCUWBatchSchema.setSchema(mLLCUWBatchSchema);

        mmLLCUWBatchSet.add(ttLLCUWBatchSchema);


          }

          return true;
      }



    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.clear();
        MMap map = new MMap();
        map.put(mmLLCUWBatchSet,"INSERT");
        mResult.add(map);
        return true;
    }


    /**
     * 校验业务数据
     * @return
     */
    private boolean checkData()
    {

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        logger.debug("jsdfjlmkafjdfkaishileqianjinqianjin");
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
       mLLCUWBatchSet = (LLCUWBatchSet) cInputData.
                               getObjectByObjectName("LLCUWBatchSet", 0);


        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mInputData = cInputData;
        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LCInsuredBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得操作员编码
        mOperator = mGlobalInput.Operator;
        if (mOperator == null || mOperator.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LCInsuredBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据mOperator失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得管理机构编码
        mManageCom = mGlobalInput.ManageCom;
        if (mManageCom == null || mManageCom.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LCInsuredBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据mOperator失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }


    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
      if(!prepareLLCUW())
        return false;


        return true;
    }

    /**
     * 返回结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 返回错误
     * @return VData
     */
    public CErrors getErrors()
    {
        return mErrors;
    }

}
