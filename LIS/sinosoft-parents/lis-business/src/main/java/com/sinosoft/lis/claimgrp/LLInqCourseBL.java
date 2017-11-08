package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author zhangxing
 * @version 1.0
 */

public class LLInqCourseBL
{
private static Logger logger = Logger.getLogger(LLInqCourseBL.class);


    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /**传输数据的容器 */

    private MMap map = new MMap();

    private GlobalInput mGlobalInput = new GlobalInput();
    //调查过程
    private LLInqCourseSchema mLLInqCourseSchema = new LLInqCourseSchema();
    //调查过程单证信息
    private LLInqCertificateSet mLLInqCertificateSet = new LLInqCertificateSet();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    public LLInqCourseBL()

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
        {

            return false;
        }

        //校验是否有未打印的体检通知书
        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {

            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLSuveryDistributeBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
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
        // 核保特约信息
        if (prepareInqCourse() == false)
        {
            return false;
        }
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
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));

        mLLInqCourseSchema = (LLInqCourseSchema) cInputData.getObjectByObjectName(
                "LLInqCourseSchema", 0);
        mLLInqCertificateSet=(LLInqCertificateSet) cInputData.getObjectByObjectName(
                "LLInqCertificateSet", 0);

        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLSuveryDistributeBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLLInqCourseSchema == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLSuveryDistributeBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }



        return true;

    }

    /**
     * 准备特约资料信息
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareInqCourse()
    {
      //生成调查过程号  /** 过程序号---过程序号由系统生成 */
      String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
      String tNo = "";
      tNo = PubFun1.CreateMaxNo("CouNo", tLimit);
      //调查过程表
      LLInqCourseSchema tLLInqCourseSchema = new LLInqCourseSchema();
      tLLInqCourseSchema.setSchema(mLLInqCourseSchema);
      tLLInqCourseSchema.setCouNo(tNo);
      tLLInqCourseSchema.setOperator(mGlobalInput.Operator);
      tLLInqCourseSchema.setMakeDate(CurrentDate);
      tLLInqCourseSchema.setMakeTime(CurrentTime);
      tLLInqCourseSchema.setModifyDate(CurrentDate);
      tLLInqCourseSchema.setModifyTime(CurrentTime);
      map.put(tLLInqCourseSchema, "INSERT");

      LLInqCertificateSet tLLInqCertificateSet = new LLInqCertificateSet();
      tLLInqCertificateSet.set(mLLInqCertificateSet);
      for(int i=1;i<=tLLInqCertificateSet.size();i++)
      {

          /** 过程序号---过程序号由系统生成 */
          tLLInqCertificateSet.get(i).setCouNo(tLLInqCourseSchema.getCouNo());
          /** 单证序号---单证序号由系统生成*/
          String tCerNo = PubFun1.CreateMaxNo("AffixNo",10);
          tLLInqCertificateSet.get(i).setCerNo(tCerNo);
          tLLInqCertificateSet.get(i).setOperator(mGlobalInput.Operator);
          tLLInqCertificateSet.get(i).setMakeDate(CurrentDate);
          tLLInqCertificateSet.get(i).setMakeTime(CurrentTime);
          tLLInqCertificateSet.get(i).setModifyDate(CurrentDate);
          tLLInqCertificateSet.get(i).setModifyTime(CurrentTime);

      }
      map.put(tLLInqCertificateSet, "INSERT");

      return true;
    }


    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.clear();
        mResult.add(map);
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }


    public CErrors getErrors()
    {
        return mErrors;
    }
}
