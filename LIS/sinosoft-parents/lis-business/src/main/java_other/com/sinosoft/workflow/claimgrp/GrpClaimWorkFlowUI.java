package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
/**
 * <p>Title:理赔工作流 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SinoSoft</p>
 * @author ZL
 * @version 1.0
 */

public class GrpClaimWorkFlowUI implements BusinessService
{
private static Logger logger = Logger.getLogger(GrpClaimWorkFlowUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    public GrpClaimWorkFlowUI() {}

    public static void main(String[] args)
    {
        /** 全局变量 */
        GlobalInput mGlobalInput = new GlobalInput();
        mGlobalInput.Operator = "001";
        mGlobalInput.ComCode = "86";
        mGlobalInput.ManageCom = "86";

//报案调试
        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
        LLAccidentSubSchema tLLAccidentSubSchema = new LLAccidentSubSchema();//分事件表
        LLReportSchema tLLReportSchema = new LLReportSchema();//报案表
        LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();//分案表
        LLReportRelaSchema tLLReportRelaSchema = new LLReportRelaSchema();//报案分案关联表
        LLCaseRelaSchema tLLCaseRelaSchema = new LLCaseRelaSchema();//分案事件关联表
        LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema();//理赔类型表
        LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet();//理赔类型集合
//        //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
        String wFlag = "9999999999";

        tLLAccidentSchema.setAccNo("80000002389");//事件号(新事件时为空)
        tLLReportSchema.setRptNo("90000002472");//报案号=赔案号
        tLLSubReportSchema.setSubRptNo("90000002472");//分案号=报案号=赔案号

        tLLAccidentSchema.setAccDate("2005-08-14");//意外事故发生日期
        tLLAccidentSchema.setAccType("");//事故类型===出险原因

        tLLReportSchema.setAccidentReason("");//出险原因

        tLLSubReportSchema.setCustomerNo("0000555600");//出险人编码
        tLLSubReportSchema.setAccDate("2005-08-14");//出险日期
        tLLSubReportSchema.setAccidentDetail("");//出险细节

        for (int i = 0; i < 2; i++)
        {
            String tt = "0" + i;
            tLLReportReasonSchema = new LLReportReasonSchema();
            tLLReportReasonSchema.setRpNo("90000002472");//报案号=赔案号
            tLLReportReasonSchema.setCustomerNo("0000555600");//出险人编码
            tLLReportReasonSchema.setReasonCode(tt);//理赔类型
            tLLReportReasonSet.add(tLLReportReasonSchema);
        }

        //String使用TransferData打包后提交
        TransferData mTransferData = new TransferData();
        mTransferData.setNameAndValue("RptNo", "90000002472");
        mTransferData.setNameAndValue("RptorName", "zzzzz");
        mTransferData.setNameAndValue("CustomerNo", "0000555600");
        mTransferData.setNameAndValue("CustomerName", "zzzzzzz");
        mTransferData.setNameAndValue("CustomerSex", "1");
        mTransferData.setNameAndValue("AccDate", "2005-01-01");
        mTransferData.setNameAndValue("MissionID", "00000000000000019444");
        mTransferData.setNameAndValue("SubMissionID", "1");
        mTransferData.setNameAndValue("OtherOperator", "");
        mTransferData.setNameAndValue("OtherFlag", "0");

        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(mGlobalInput);
        tVData.add(mTransferData);
        tVData.add(tLLAccidentSchema);
        tVData.add(tLLReportSchema);
        tVData.add(tLLSubReportSchema);
        tVData.add(tLLReportReasonSet);

//立案调试
//        TransferData mTransferData = new TransferData();
//        mTransferData.setNameAndValue("RptNo", "90000003544");
//        mTransferData.setNameAndValue("RptorState", "20");
//        mTransferData.setNameAndValue("CustomerNo", "0000586320");
//        mTransferData.setNameAndValue("CustomerName", "1");
//        mTransferData.setNameAndValue("CustomerSex", "1");
//        mTransferData.setNameAndValue("AccDate", "1");
//        mTransferData.setNameAndValue("RgtClass", "1");  //申请类型：个险为1,团险为2
//        mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
//        mTransferData.setNameAndValue("RgtObjNo", "1");  //其他号码
//        mTransferData.setNameAndValue("MngCom", mGlobalInput.ManageCom);  //机构
//        mTransferData.setNameAndValue("PrepayFlag", "0");  //预付标志
//        mTransferData.setNameAndValue("ComeWhere", "A");  //来自:A立案进入审核，B审批进入审核因为不通过，C审批进入审核因为赔案为负数，D为预付进入审核
//        //------------------------------------------------------------------------------BEG
//        //审核权限，在匹配计算完善后加入service类后去掉此行
//        //------------------------------------------------------------------------------
////    mTransferData.setNameAndValue("Popedom","A01");
//        //------------------------------------------------------------------------------END
//        //转移条件参数
//        mTransferData.setNameAndValue("SimpleFlag", "0");  //是否简易案件
//        //业务类需要数据
//        mTransferData.setNameAndValue("PopedomPhase","A"); //权限阶段标示A:审核B:审批
//        mTransferData.setNameAndValue("standpay","120000"); //预估金额
//        mTransferData.setNameAndValue("adjpay","120000"); //调整后金额
//
//        mTransferData.setNameAndValue("RgtConclusion","01");
//
//        mTransferData.setNameAndValue("MissionID","00000000000000023935");
//        mTransferData.setNameAndValue("SubMissionID","1");
////
//        //准备传输数据 VData
//        VData tVData = new VData();
//        tVData.add(mGlobalInput);
//        tVData.add(mTransferData);

//审核调试
//        TransferData mTransferData = new TransferData();
//        mTransferData.setNameAndValue("RptNo", "90000001728");
//        mTransferData.setNameAndValue("RptorState", "40");
//        mTransferData.setNameAndValue("CustomerNo", "0000533510");
//        mTransferData.setNameAndValue("CustomerName", "1");
//        mTransferData.setNameAndValue("CustomerSex", "0");
//        mTransferData.setNameAndValue("AccDate", "00");
//        mTransferData.setNameAndValue("RgtClass", "1");  //申请类型
//        mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
//        mTransferData.setNameAndValue("RgtObjNo", "1");  //其他号码
//        mTransferData.setNameAndValue("MngCom", mGlobalInput.ManageCom);  //机构
//        mTransferData.setNameAndValue("PrepayFlag", "0");
//        mTransferData.setNameAndValue("AuditPopedom", "A01");//审核权限
//        mTransferData.setNameAndValue("Auditer", "001");//审核人
//        //------------------------------------------------------------------------------BEG
//        //审批权限，在匹配计算完善后加入service类后去掉此行
//        //------------------------------------------------------------------------------
//        mTransferData.setNameAndValue("Popedom","B01");
//        //------------------------------------------------------------------------------END
//        //转移条件参数
//        mTransferData.setNameAndValue("BudgetFlag", "0");  //是否预付
//        mTransferData.setNameAndValue("IsRunBL", "1");  //是否运行BL
//        mTransferData.setNameAndValue("PopedomPhase","B"); //权限阶段标示A:审核B:审批
//
//        mTransferData.setNameAndValue("MissionID","00000000000000014807");
//        mTransferData.setNameAndValue("SubMissionID","1");
//
//        //准备传输数据 VData
//        VData tVData = new VData();
//        tVData.add(mGlobalInput);
//        tVData.add(mTransferData);

//审批调试
//        LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
//        tLLClaimUWMainSchema.setClmNo("90000002452");//赔案号
//        tLLClaimUWMainSchema.setExamConclusion("0");//审批结论
//        tLLClaimUWMainSchema.setExamIdea("12");//审批意见
//        tLLClaimUWMainSchema.setExamNoPassReason("");//不通过原因
//        tLLClaimUWMainSchema.setExamNoPassDesc("");//不通过依据
//
//        //String使用TransferData打包后提交
//        TransferData mTransferData = new TransferData();
//        mTransferData.setNameAndValue("RptNo", "90000002452");
//        mTransferData.setNameAndValue("RptorState", "50");
//        mTransferData.setNameAndValue("CustomerNo","0000546570");
//        mTransferData.setNameAndValue("CustomerName","1");
//        mTransferData.setNameAndValue("CustomerSex","1");
//        mTransferData.setNameAndValue("AccDate","1");
//        mTransferData.setNameAndValue("RgtClass", "1");  //申请类型
//        mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
//        mTransferData.setNameAndValue("RgtObjNo","1");  //其他号码
//        mTransferData.setNameAndValue("MngCom", mGlobalInput.ManageCom);  //机构
//
//        mTransferData.setNameAndValue("Popedom","A03"); //审核权限
//        mTransferData.setNameAndValue("PrepayFlag","0");//预付标志
//        mTransferData.setNameAndValue("Auditer",mGlobalInput.Operator);//审核人
//
//        //转移条件
//        mTransferData.setNameAndValue("Reject","0");  //审批是否通过
//
//        mTransferData.setNameAndValue("MissionID","00000000000000019369");
//        mTransferData.setNameAndValue("SubMissionID","1");
//
//        //准备传输数据 VData
//        VData tVData = new VData();
//        tVData.add(mGlobalInput);
//        tVData.add(mTransferData);
//        tVData.add(tLLClaimUWMainSchema);

//简易案件调试
//        TransferData mTransferData = new TransferData();
//        mTransferData.setNameAndValue("RptNo", "90000001149");
//        mTransferData.setNameAndValue("RptorState", "50");
//        mTransferData.setNameAndValue("CustomerNo", "0000522710");
//        mTransferData.setNameAndValue("CustomerName", "");
//        mTransferData.setNameAndValue("CustomerSex", "");
//        mTransferData.setNameAndValue("AccDate", "");
//        mTransferData.setNameAndValue("RgtClass", "1");  //申请类型
//        mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
//        mTransferData.setNameAndValue("RgtObjNo", "");  //其他号码
//        mTransferData.setNameAndValue("MngCom", mGlobalInput.ManageCom);  //机构
//        mTransferData.setNameAndValue("PrepayFlag", "0");  //预付标志
//        mTransferData.setNameAndValue("Auditer", "0");  //审核操作人,为审批不通过时返回个人工作队列用
//
//        mTransferData.setNameAndValue("PopedomPhase","A");//权限阶段:审核A;
//
//        //转移条件参数
//        mTransferData.setNameAndValue("AuditFlag", "1");  //不通过需要审核
//
//        mTransferData.setNameAndValue("MissionID","00000000000000013651");
//        mTransferData.setNameAndValue("SubMissionID","1");
//
//        //准备传输数据 VData
//        VData tVData = new VData();
//        tVData.add(mGlobalInput);
//        tVData.add(mTransferData);

        GrpClaimWorkFlowUI tGrpClaimWorkFlowUI = new GrpClaimWorkFlowUI();
        try
        {
            if (tGrpClaimWorkFlowUI.submitData(tVData, "0000005005"))
            {
                VData tResult = new VData();
            }
            else
            {
                logger.debug(tGrpClaimWorkFlowUI.mErrors.getError(0).
                                   errorMessage);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
       传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        GrpClaimWorkFlowBL tGrpClaimWorkFlowBL = new GrpClaimWorkFlowBL();

        logger.debug("---GrpClaimWorkFlowBL UI BEGIN---");
        if (!tGrpClaimWorkFlowBL.submitData(cInputData, mOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tGrpClaimWorkFlowBL.mErrors);
            mResult.clear();
            return false;
        }
        mResult = tGrpClaimWorkFlowBL.getResult();
        logger.debug("---UI mResult="+mResult);
        logger.debug("---GrpClaimWorkFlowBL UI END---");
        return true;
    }

    /**
        传输数据的公共方法
     */
    public VData getResult()
    {
        return mResult;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
