/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


import com.sinosoft.lis.vschema.LLFeeMainSet;
import com.sinosoft.lis.db.LLFeeMainDB;
import com.sinosoft.lis.schema.LLFeeMainSchema;


import com.sinosoft.lis.vschema.LLCaseReceiptSet;
import com.sinosoft.lis.db.LLCaseReceiptDB;
import com.sinosoft.lis.schema.LLCaseReceiptSchema;

import com.sinosoft.lis.vschema.LLCommendHospitalSet;
import com.sinosoft.lis.db.LLCommendHospitalDB;
import com.sinosoft.lis.schema.LLCommendHospitalSchema;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:提交理赔医疗费用信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author:续涛
 * @version 1.0
 */

public class LLMedicalFeeInpBL
{
private static Logger logger = Logger.getLogger(LLMedicalFeeInpBL.class);



    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */


    private MMap map = new MMap();                  /** 往后面传输的数据库操作 */
    private MMap mapDel = new MMap();               /** 执行删除的数据库操作，放在最后 */


    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();

//    private LLFeeMainDB mLLFeeMainDB = new LLFeeMainDB();
//  private LLCaseReceiptDB mLLCaseReceiptDB = new LLCaseReceiptDB();

    private LLFeeMainSchema mLLFeeMainSchema = new LLFeeMainSchema();
    private LLCaseReceiptSchema mLLCaseReceiptSchema = new LLCaseReceiptSchema();


    public LLMedicalFeeInpBL(){}


    /**
    * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */

    public boolean submitData(VData cInputData,String cOperate)
    {
        logger.debug("----------LLMedicalFeeInpBL Begin----------");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }

        //检查数据合法性
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

        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInpBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;

        logger.debug("----------LLMedicalFeeInpBL End----------");
        return true;
    }



    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {

        mInputData = cInputData;
        this.mOperate = cOperate;

        logger.debug("LLMedicalFeeInpBL测试--"+this.mOperate);

        mGlobalInput.setSchema((GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0));

        //mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        //mLCUWSendTraceSchema = (LCUWSendTraceSchema) mTransferData.getValueByName("LCUWSendTraceSchema");

        mLLFeeMainSchema = (LLFeeMainSchema) mInputData.getObjectByObjectName("LLFeeMainSchema", 0);
        //或者使用this.mLLFeeMainSchema.setSchema((LLFeeMainSchema)mInputData.getObjectByObjectName("LLFeeMainSchema",0));


        mLLCaseReceiptSchema = (LLCaseReceiptSchema) mInputData.getObjectByObjectName("LLCaseReceiptSchema", 0);

        return true;
    }

    /**
     * 校验传入的信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData()
    {

        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInpBL";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }


        //获得操作员编码
        String tOperator = mGlobalInput.Operator;
        if (tOperator == null || tOperator.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInpBL";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mLLFeeMainSchema == null || mLLCaseReceiptSchema == null )
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInpBL";
            tError.functionName = "checkData";
            tError.errorMessage = "接受的Schema信息全部为空!";
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
        boolean tReturn = false;

        //  进行医疗费用的录入
        if (this.mOperate.equals("CLINIC||INSERT"))
        {
            if (!medicalInsert())
            {
                return false;
            }
        }

        //  进行医疗费用的删除
        if (this.mOperate.equals("CLINIC||DELETE"))
        {
            if (!medicalDelete())
            {
                return false;
            }
        }

        //  进行医疗费用的修改
        if (this.mOperate.equals("CLINIC||UPDATE"))
        {
            if (!medicalUpdate())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 进行医疗费用的录入
     * @return
     */
    private boolean medicalInsert()
    {
        //  进行账单信息主表的数据查询
        LLFeeMainDB tLLFeeMainDB = new LLFeeMainDB();

        tLLFeeMainDB.setClmNo(mLLFeeMainSchema.getClmNo());                     //赔案号
        tLLFeeMainDB.setCaseNo(mLLFeeMainSchema.getCaseNo());                   //分案号
        tLLFeeMainDB.setCustomerNo(mLLFeeMainSchema.getCustomerNo());           //客户号
        tLLFeeMainDB.setHospitalCode(mLLFeeMainSchema.getHospitalCode());       //医院编号

        LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
        tLLFeeMainSet = tLLFeeMainDB.query();
        //  或者是 tLLFeeMainSet.set(tLLFeeMainDB.query());

        if (tLLFeeMainDB.mErrors.needDealError() == true)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLFeeMainDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInpBL";
            tError.functionName = "medicalInsert";
            tError.errorMessage = "账单信息主表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //  如果账单信息主表无数据，则添加主表数据
        if (tLLFeeMainSet == null || tLLFeeMainSet.size() == 0)
        {
            LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();

            tLLFeeMainSchema.setClmNo(mLLFeeMainSchema.getClmNo());
            tLLFeeMainSchema.setCaseNo(mLLFeeMainSchema.getCaseNo());
            tLLFeeMainSchema.setCustomerNo(mLLFeeMainSchema.getCustomerNo());
            tLLFeeMainSchema.setHospitalCode(mLLFeeMainSchema.getHospitalCode());

            //查询医院名称和等级
            LLCommendHospitalDB tLLCommendHospitalDB = new LLCommendHospitalDB();
            tLLCommendHospitalDB.setHospitalCode(mLLFeeMainSchema.getHospitalCode());
            LLCommendHospitalSet tLLCommendHospitalSet = tLLCommendHospitalDB.query();
            LLCommendHospitalSchema tLLCommendHospitalSchema = tLLCommendHospitalSet.get(1);
            tLLFeeMainSchema.setHospitalName(tLLCommendHospitalSchema.getHospitalName());
            tLLFeeMainSchema.setHospitalGrade(tLLCommendHospitalSchema.getHosAtti());


            //  生成帐单号
            String strNoLimit = PubFun.getNoLimit(this.mGlobalInput.ComCode);
            String tMainFeeNo = PubFun1.CreateMaxNo("MainFeeNo",10);
            tLLFeeMainSchema.setMainFeeNo(tMainFeeNo);

            tLLFeeMainSchema.setOperator(this.mGlobalInput.Operator);
            tLLFeeMainSchema.setMngCom(this.mGlobalInput.ManageCom);
            tLLFeeMainSchema.setMakeDate(this.CurrentDate);
            tLLFeeMainSchema.setMakeTime(this.CurrentTime);
            tLLFeeMainSchema.setModifyDate(this.CurrentDate);
            tLLFeeMainSchema.setModifyTime(this.CurrentTime);

            map.put(tLLFeeMainSchema, "INSERT");

            //  如果账单信息主表无数据，则添加明细表数据
            LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
            tLLCaseReceiptSchema.setClmNo(mLLFeeMainSchema.getClmNo());
            tLLCaseReceiptSchema.setCaseNo(mLLFeeMainSchema.getCaseNo());
            tLLCaseReceiptSchema.setRgtNo(mLLFeeMainSchema.getCaseNo());
            tLLCaseReceiptSchema.setMainFeeNo(tMainFeeNo);

            //  生成账单费用明细流水号
            String tFeeDetailNo = PubFun1.CreateMaxNo("FeeDetailNo",10);
            tLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo);

            tLLCaseReceiptSchema.setFeeItemType("A");
            tLLCaseReceiptSchema.setFeeItemCode(mLLCaseReceiptSchema.getFeeItemCode());
            tLLCaseReceiptSchema.setFeeItemName(mLLCaseReceiptSchema.getFeeItemName());
            tLLCaseReceiptSchema.setFee(mLLCaseReceiptSchema.getFee());
            tLLCaseReceiptSchema.setAdjSum(mLLCaseReceiptSchema.getAdjSum());

            tLLCaseReceiptSchema.setStartDate(mLLCaseReceiptSchema.getStartDate());
            tLLCaseReceiptSchema.setEndDate(mLLCaseReceiptSchema.getEndDate());

            int tDay = PubFun.calInterval(mLLCaseReceiptSchema.getStartDate(),mLLCaseReceiptSchema.getEndDate(), "D");
            tLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));

            tLLCaseReceiptSchema.setDealFlag("01");

            tLLCaseReceiptSchema.setOperator(this.mGlobalInput.Operator);
            tLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
            tLLCaseReceiptSchema.setMakeDate(this.CurrentDate);
            tLLCaseReceiptSchema.setMakeTime(this.CurrentTime);
            tLLCaseReceiptSchema.setModifyDate(this.CurrentDate);
            tLLCaseReceiptSchema.setModifyTime(this.CurrentTime);

            map.put(tLLCaseReceiptSchema, "INSERT");

        }

        else if ( tLLFeeMainSet.size() == 1)
        {
            //  如果账单信息主表无数据，则添加明细表数据

            LLFeeMainSchema tLLFeeMainSchema = tLLFeeMainSet.get(1);


            LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
            tLLCaseReceiptSchema.setClmNo(tLLFeeMainSchema.getClmNo());
            tLLCaseReceiptSchema.setCaseNo(tLLFeeMainSchema.getCaseNo());
            tLLCaseReceiptSchema.setRgtNo(mLLFeeMainSchema.getCaseNo());

            tLLCaseReceiptSchema.setMainFeeNo(tLLFeeMainSchema.getMainFeeNo());

            //  生成账单费用明细流水号
            String tFeeDetailNo = PubFun1.CreateMaxNo("FeeDetailNo",10);
            tLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo);

            tLLCaseReceiptSchema.setFeeItemType(mLLCaseReceiptSchema.getFeeItemType());
            tLLCaseReceiptSchema.setFeeItemCode(mLLCaseReceiptSchema.getFeeItemCode());
            tLLCaseReceiptSchema.setFeeItemName(mLLCaseReceiptSchema.getFeeItemName());
            tLLCaseReceiptSchema.setFee(mLLCaseReceiptSchema.getFee());

            tLLCaseReceiptSchema.setStartDate(mLLCaseReceiptSchema.getStartDate());
            tLLCaseReceiptSchema.setEndDate(mLLCaseReceiptSchema.getEndDate());
            int tDay = PubFun.calInterval(mLLCaseReceiptSchema.getStartDate(),mLLCaseReceiptSchema.getEndDate(), "D");
            tLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));

            tLLCaseReceiptSchema.setDealFlag("01");

            tLLCaseReceiptSchema.setOperator(this.mGlobalInput.Operator);
            tLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
            tLLCaseReceiptSchema.setMakeDate(this.CurrentDate);
            tLLCaseReceiptSchema.setMakeTime(this.CurrentTime);
            tLLCaseReceiptSchema.setModifyDate(this.CurrentDate);
            tLLCaseReceiptSchema.setModifyTime(this.CurrentTime);

            map.put(tLLCaseReceiptSchema, "INSERT");

            //logger.debug("LLMedicalFeeInpBL测试--tDay"+tDay);
            //  logger.debug("LLMedicalFeeInpBL测试--"+tLLCaseReceiptSchema.getFeeItemName());
        }
        else
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInpBL";
            tError.functionName = "medicalInsert";
            tError.errorMessage = "账单信息主表数据大于1!";
            this.mErrors.addOneError(tError);
            return false;
        }



        return true;
    }

    /**
     * 进行医疗费用的删除
     * @return
     */
    private boolean medicalDelete()
    {
        LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
        tLLCaseReceiptSchema.setSchema(mLLCaseReceiptSchema);

        map.put(tLLCaseReceiptSchema, "DELETE");
        return true;
    }

    /**
     * 进行医疗费用的修改
     * @return
     */
    private boolean medicalUpdate()
    {

        LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
        tLLCaseReceiptSchema.setSchema(mLLCaseReceiptSchema);

        int tDay = PubFun.calInterval(mLLCaseReceiptSchema.getStartDate(),mLLCaseReceiptSchema.getEndDate(), "D");
        tLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));

        tLLCaseReceiptSchema.setOperator(this.mGlobalInput.Operator);
        tLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
        tLLCaseReceiptSchema.setMakeDate(this.CurrentDate);
        tLLCaseReceiptSchema.setMakeTime(this.CurrentTime);
        tLLCaseReceiptSchema.setModifyDate(this.CurrentDate);
        tLLCaseReceiptSchema.setModifyTime(this.CurrentTime);

        //logger.debug("LLMedicalFeeInpBL测试--tDay"+tDay);

        map.put(tLLCaseReceiptSchema, "UPDATE");
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
//            mResult.add(mLLAccidentSchema);//供前台界面使用
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLReportBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 提供返回前台界面的数据
     * @return
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 用于测试
     * @return
     */
    public static void main(String[] args)
    {
    }


}
