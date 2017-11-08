/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLInqApplySchema;
import com.sinosoft.lis.schema.LLInqConclusionSchema;
import com.sinosoft.lis.db.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLGrpInqApplyBL
{
private static Logger logger = Logger.getLogger(LLGrpInqApplyBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private MMap map = new MMap();
    private boolean mIsReportExist;
    private TransferData mTransferData = new TransferData();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private LLInqApplySchema mLLInqApplySchema = new LLInqApplySchema();
    private LLInqConclusionSchema mLLInqConclusionSchema = new LLInqConclusionSchema();

    private GlobalInput mG = new GlobalInput();

    public LLGrpInqApplyBL()
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
        logger.debug("----------LLGrpInqApplyBL begin submitData----------");
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
        if (!dealData(cOperate))
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
            tError.moduleName = "LLGrpInqApplyBL";
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
        logger.debug("--start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mLLInqApplySchema = (LLInqApplySchema) mInputData.getObjectByObjectName(
            "LLInqApplySchema", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
        if (mLLInqApplySchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpInqApplyBL";
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
            //非空字段检验
            if (mLLInqApplySchema.getClmNo() == null)//赔案号
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLGrpInqApplyBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "接受的赔案号为空!";
                this.mErrors.addOneError(tError);
                return false;
            }
            if (mLLInqApplySchema.getBatNo() == null)//调查批次号
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLGrpInqApplyBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "接受的调查批次号为空!";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpInqApplyBL";
            tError.functionName = "checkInputData";
            tError.errorMessage = "在校验输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
    /**
     * 更新mTransferData中的值，为工作流准备数据
     * @return boolean
     */
    private boolean perpareMissionProp()
    {
        mTransferData.removeByName("InqNo");
        mTransferData.setNameAndValue("InqNo",mLLInqApplySchema.getInqNo());//
        return true;
    }
    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("------start dealData-----");
        boolean tReturn = false;
        //判断是否存在记录（同一赔案号 的同一个批次下 是否存在相同的机构）现在已删除

        //添加纪录
        if (cOperate.equals("INSERT"))
        {
            logger.debug("----- dealData---transact= "+cOperate);
                    //调查申请表
               String tInqNo = PubFun1.CreateMaxNo("InqNo", 10); //生成调查序号流水号
               logger.debug("-----生成调查序号= " + tInqNo);

               mLLInqApplySchema.setInqNo(tInqNo); //调查序号
               mLLInqApplySchema.setApplyPer(mG.Operator); //申请人
               mLLInqApplySchema.setApplyDate(CurrentDate); //申请日期
               mLLInqApplySchema.setInitDept(mG.ManageCom); //发起机构
               mLLInqApplySchema.setInqState("0"); //调查状态"0---申请"
              // mLLInqApplySchema.setInitPhase("01");//提起阶段(已在前台处理)
               mLLInqApplySchema.setOperator(mG.Operator);
               mLLInqApplySchema.setMakeDate(CurrentDate);
               mLLInqApplySchema.setMakeTime(CurrentTime);
               mLLInqApplySchema.setModifyDate(CurrentDate);
               mLLInqApplySchema.setModifyTime(CurrentTime);

               //******************************************************
                //判断调查结论表记录是否存在,条件(赔案\批次\机构)
                //******************************************************
                 String strSql = "select ClmNo from LLInqConclusion where "
                                 + "ClmNo = " + mLLInqApplySchema.getClmNo() //赔案
                                 + " and BatNo = " +
                                 mLLInqApplySchema.getBatNo() //批次
                                 + " and InqDept = " +
                                 mLLInqApplySchema.getInqDept(); //机构
               ExeSQL exesql = new ExeSQL();
               String tResult = exesql.getOneValue(strSql);
               logger.debug("-----查询调查结论表所得记录= " + tResult);
               if (tResult.length() == 0)
               {
                    //调查结论表
                   String tConNo = PubFun1.CreateMaxNo("ConNo", 10); //生成调查结论序号流水号
                   logger.debug("-----生成调查结论序号= " + tConNo);
                   mLLInqConclusionSchema.setClmNo(mLLInqApplySchema.getClmNo()); //赔案号
                   mLLInqConclusionSchema.setConNo(tConNo); //结论序号
                   mLLInqConclusionSchema.setBatNo(mLLInqApplySchema.getBatNo()); //调查批次
                   mLLInqConclusionSchema.setInitDept(mG.ManageCom); //发起机构
                   mLLInqConclusionSchema.setInqDept(mLLInqApplySchema.getInqDept()); //调查机构
                   mLLInqConclusionSchema.setFiniFlag("0"); //完成标志
                   mLLInqConclusionSchema.setColFlag("2");//汇总标志：2---机构结论
                   mLLInqConclusionSchema.setLocFlag(mLLInqApplySchema.getLocFlag()); //本地标志

                   mLLInqConclusionSchema.setOperator(mG.Operator);
                   mLLInqConclusionSchema.setMakeDate(CurrentDate);
                   mLLInqConclusionSchema.setMakeTime(CurrentTime);
                   mLLInqConclusionSchema.setModifyDate(CurrentDate);
                   mLLInqConclusionSchema.setModifyTime(CurrentTime);

                   map.put(mLLInqConclusionSchema, "INSERT");
                   tReturn = true;
               }
               map.put(mLLInqApplySchema, "INSERT");
               String updatesql="update LLSubReport set SurveyFlag='1' where SubRptNo='" +mLLInqApplySchema.getClmNo() +"' and CustomerNo='" +mLLInqApplySchema.getCustomerNo() +"'";
               map.put(updatesql, "UPDATE");
               //更新mTransferData中的值
               if (!perpareMissionProp())
               {
                   //@@错误处理
                   CError tError = new CError();
                   tError.moduleName = "LLGrpInqApplyBL";
                   tError.functionName = "perpareMissionProp";
                   tError.errorMessage = "为工作流准备数据失败!";
                   this.mErrors.addOneError(tError);
                   return false;
               }
                tReturn = true;
        }
        else
        {
            tReturn = false;
        }
        return tReturn;
    }


    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mResult.add(map);
            mResult.add(mTransferData);
            mResult.add(mG);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpInqApplyBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
