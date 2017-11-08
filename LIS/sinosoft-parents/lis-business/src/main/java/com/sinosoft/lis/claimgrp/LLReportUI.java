/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAccidentSubSchema;
import com.sinosoft.lis.schema.LLCaseRelaSchema;
import com.sinosoft.lis.schema.LLReportReasonSchema;
import com.sinosoft.lis.schema.LLReportRelaSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:提交理赔报案信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author
 * @version 1.0
 */
public class LLReportUI
{
private static Logger logger = Logger.getLogger(LLReportUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLReportUI()
    {
    }

    public static void main(String[] args)
    {

        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
        LLAccidentSubSchema tLLAccidentSubSchema = new LLAccidentSubSchema();//分事件表
        LLReportSchema tLLReportSchema = new LLReportSchema();//报案表
        LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();//分案表
        LLReportRelaSchema tLLReportRelaSchema = new LLReportRelaSchema();//报案分案关联表
        LLCaseRelaSchema tLLCaseRelaSchema = new LLCaseRelaSchema();//分案事件关联表
        LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema();//理赔类型表
        LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet();//理赔类型集合


        /** 全局变量 */
        GlobalInput mGlobalInput = new GlobalInput();
        mGlobalInput.Operator = "001";
        mGlobalInput.ComCode = "86";
        mGlobalInput.ManageCom = "86";

        //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
        String wFlag = "9999999999";

        tLLAccidentSchema.setAccNo("80000000930");//事件号(新事件时为空)
        tLLReportSchema.setRptNo("0000000001");//报案号=赔案号
        tLLSubReportSchema.setSubRptNo("0000000001");//分案号=报案号=赔案号

        tLLAccidentSchema.setAccDate("2005-01-01");//意外事故发生日期
        tLLAccidentSchema.setAccType("");//事故类型===出险原因

        tLLReportSchema.setAccidentReason("");//出险原因

        tLLSubReportSchema.setCustomerNo("1000000001");//出险人编码
        tLLSubReportSchema.setAccDate("2005-01-01");//出险日期
        tLLSubReportSchema.setAccidentDetail("");//出险细节

        for (int i = 0; i < 2; i++)
        {
            String tt = "0" + i;
            tLLReportReasonSchema = new LLReportReasonSchema();
            tLLReportReasonSchema.setRpNo("0000000001");//报案号=赔案号
            tLLReportReasonSchema.setCustomerNo("1000000001");//出险人编码
            tLLReportReasonSchema.setReasonCode(tt);//理赔类型
            tLLReportReasonSet.add(tLLReportReasonSchema);
        }

        //String使用TransferData打包后提交
        TransferData mTransferData = new TransferData();
        mTransferData.setNameAndValue("RptNo", "0000000001");
        mTransferData.setNameAndValue("RptorName", "zzzzz");
        mTransferData.setNameAndValue("CustomerNo", "1000000001");
        mTransferData.setNameAndValue("CustomerName", "zzzzzzz");
        mTransferData.setNameAndValue("CustomerSex", "1");
        mTransferData.setNameAndValue("AccDate", "2005-01-01");
        mTransferData.setNameAndValue("MissionID", "");
        mTransferData.setNameAndValue("SubMissionID", "");

        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(mGlobalInput);
        tVData.add(mTransferData);
        tVData.add(tLLAccidentSchema);
        tVData.add(tLLReportSchema);
        tVData.add(tLLSubReportSchema);
        tVData.add(tLLReportReasonSet);

        LLReportUI tLLReportUI = new LLReportUI();
        try
        {
            if (tLLReportUI.submitData(tVData, "UPDATE"))
            {
                VData tResult = new VData();
            }
            else
            {
                logger.debug(tLLReportUI.mErrors.getError(0).
                                   errorMessage);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        LLReportBLF tLLReportBLF = new LLReportBLF();

        logger.debug("----------BLF BEGIN----------");
        if (tLLReportBLF.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLReportBLF.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLReportUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLReportBLF.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
