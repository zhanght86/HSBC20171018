/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.db.LLClaimDB;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 承保暂交费业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLClaimSimpleBL
{
private static Logger logger = Logger.getLogger(LLClaimSimpleBL.class);

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

    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();

    private GlobalInput mG = new GlobalInput();
    private String mRptNo = "";
    private String mRealPay = "";

    public LLClaimSimpleBL()
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
        logger.debug("----------LLClaimSimpleBL begin submitData----------");
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
        mRptNo = (String) mInputData.getObject(1);//赔案号
        mRealPay = (String) mInputData.getObject(2);//赔案金额
//        //获取需要更新的字段
//        mLLClaimSchema = (LLClaimSchema) mInputData.getObjectByObjectName(
//            "LLClaimSchema", 0);

        if (mLLClaimSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimSimpleBL";
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
//        logger.debug(mLLClaimSchema.getAccDate());
//        try
//        {
//            //非空字段检验
//            if (mLLClaimSchema.getAccDate() == null)//意外事故发生日期
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimSimpleBL";
//                tError.functionName = "checkInputData";
//                tError.errorMessage = "接受的意外事故发生日期为空!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//            if (mLLCaseSchema.getCustomerNo() == null)//出险人编码
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimSimpleBL";
//                tError.functionName = "checkInputData";
//                tError.errorMessage = "接受的出险人编码为空!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//        }
//        catch (Exception ex)
//        {
//            // @@错误处理
//            CError tError = new CError();
//            tError.moduleName = "LLClaimSimpleBL";
//            tError.functionName = "checkInputData";
//            tError.errorMessage = "在校验输入的数据时出错!";
//            this.mErrors.addOneError(tError);
//            return false;
//        }
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
        //添加纪录
        if (cOperate.equals("UPDATE||SIMPLECLAIM"))
        {
            //判断是否已经存在
            String strSql = "select ClmNo from LLClaim where "
                             + "ClmNo = " + mRptNo; //赔案号
            ExeSQL exesql = new ExeSQL();
            String tResult = exesql.getOneValue(strSql);
            logger.debug("-----查询调查结论表所得记录= " + tResult);
            if (tResult.length() == 0)
            {
                //插入
                mLLClaimSchema.setClmNo(mRptNo);
                mLLClaimSchema.setRgtNo(mRptNo);
                mLLClaimSchema.setCaseNo(mRptNo);
                mLLClaimSchema.setClmState("50");//结案
                mLLClaimSchema.setRealPay(mRealPay);
                mLLClaimSchema.setEndCaseDate(CurrentDate);//结案日期
                mLLClaimSchema.setOperator(mG.Operator);
                mLLClaimSchema.setMngCom(mG.ManageCom);
                mLLClaimSchema.setMakeDate(CurrentDate);
                mLLClaimSchema.setMakeTime(CurrentTime);
                mLLClaimSchema.setModifyDate(CurrentDate);
                mLLClaimSchema.setModifyTime(CurrentTime);
            }
            else
            {
                //更新
                LLClaimDB tLLClaimDB = new LLClaimDB();
                tLLClaimDB.setClmNo(mRptNo);
                tLLClaimDB.getInfo();
                mLLClaimSchema = tLLClaimDB.getSchema();
                mLLClaimSchema.setClmState("50"); //赔案状态置为已结案
                mLLClaimSchema.setRealPay(mRealPay);
                mLLClaimSchema.setEndCaseDate(CurrentDate);//结案日期
                mLLClaimSchema.setModifyDate(CurrentDate);
                mLLClaimSchema.setModifyTime(CurrentTime);
            }
            map.put(mLLClaimSchema, "DELETE&INSERT");

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
            mInputData.clear();
            mInputData.add(map);
            mResult.add(map);

        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimSimpleBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
