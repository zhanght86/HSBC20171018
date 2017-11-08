/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAppClaimReasonSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
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
public class LLGrpClaimRegisterUI
{
private static Logger logger = Logger.getLogger(LLGrpClaimRegisterUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLGrpClaimRegisterUI()
    {
    }

    public static void main(String[] args)
    {

        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();//报案表
        LLCaseSchema tLLCaseSchema = new LLCaseSchema();//分案表
        LLClaimSchema tLLClaimSchema = new LLClaimSchema();//赔案表
        LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
        LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet();//理赔类型集合

        GlobalInput tGI = new GlobalInput();
        LLGrpClaimRegisterUI tLLGrpClaimRegisterUI = new LLGrpClaimRegisterUI();

        String transact = "INSERT";//获取操作insert||update
        tGI.ManageCom="86";
        tGI.Operator="001";
        //获取报案页面信息
        tLLAccidentSchema.setAccNo("");//事件号(新事件时为空)
        tLLRegisterSchema.setRgtNo(" ");//报案号=赔案号
        tLLCaseSchema.setCaseNo(" ");//分案号=报案号=赔案号
//        tLLAccidentSchema.setAccDate("2005-05-01");//意外事故发生日期
        tLLRegisterSchema.setRgtantName("");//报案人姓名
        tLLRegisterSchema.setRgtantPhone("");//报案人电话
        tLLRegisterSchema.setRgtantAddress("");//报案人通讯地址
        tLLRegisterSchema.setRelation("");//报案人与事故人关系
        tLLRegisterSchema.setRgtType("");//报案方式
        tLLRegisterSchema.setAccidentSite("");//出险地点
        tLLRegisterSchema.setAccidentReason("1");//出险原因
        tLLCaseSchema.setCustomerNo("86110000000224");//出险人编码
        tLLCaseSchema.setAccidentDate("2005-05-02");//出险日期

        //String使用TransferData打包后提交
        TransferData mTransferData = new TransferData();
        mTransferData.setNameAndValue("Remark", "121212");
        mTransferData.setNameAndValue("hospital", "121242442");

        //工作流相关
        mTransferData.setNameAndValue("RptNo", "90000001270 ");
        mTransferData.setNameAndValue("RptorState", "20");
        mTransferData.setNameAndValue("CustomerNo", "");
        mTransferData.setNameAndValue("CustomerName", "");
        mTransferData.setNameAndValue("CustomerSex", "");
        mTransferData.setNameAndValue("AccDate", "");

        mTransferData.setNameAndValue("MissionID","88888888");
        mTransferData.setNameAndValue("SubMissionID","9999999");
        mTransferData.setNameAndValue("ActivityID","777777777");

        for (int i = 0; i < 2; i++)
        {
            String tt = "0" + i;
            tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
            tLLAppClaimReasonSchema.setRgtNo("0000000001");//报案号=赔案号
            tLLAppClaimReasonSchema.setCustomerNo("1000000001");//出险人编码
            tLLAppClaimReasonSchema.setReasonCode(tt);//理赔类型
            tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
        }

        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLAccidentSchema);
        tVData.add(tLLRegisterSchema);
        tVData.add(tLLCaseSchema);
        tVData.add(tLLAppClaimReasonSet);

        tLLGrpClaimRegisterUI.submitData(tVData,transact);
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

        LLGrpClaimRegisterBLF tLLGrpClaimRegisterBLF = new LLGrpClaimRegisterBLF();

        logger.debug("----------UI BEGIN----------");
        if (tLLGrpClaimRegisterBLF.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLGrpClaimRegisterBLF.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLGrpClaimRegisterBLF.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
