/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔延迟立案逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLGrpClaimCancelBL
{
private static Logger logger = Logger.getLogger(LLGrpClaimCancelBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    //全局变量
    private MMap map = new MMap();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private GlobalInput mG = new GlobalInput();
    TransferData mTransferData = new TransferData();

    //立案相关
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
    private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

    private String mCustomerNo = "";
    private String mRptNo = "";


    public LLGrpClaimCancelBL()
    {
    }


    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LLGrpClaimCancelBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimCancelBL after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimCancelBL after checkInputData----------");
        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimCancelBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimCancelBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLGrpClaimCancelBL";
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
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("---LLClaimRegisterDealBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);

        mRptNo = (String) mTransferData.getValueByName("RptNo");
        mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");//获得当前客户号码
        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * @return：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
//        ExeSQL tExeSQL = new ExeSQL();
//        String strSQL="select count(1) from LLInqApply where clmno='"+mRptNo+"' and InqState='0'";
//        String tReturn = tExeSQL.getOneValue(strSQL);
//        if (tReturn != null && tReturn.length() != 0) {
//            if(Integer.parseInt(tReturn)>0)
//            {
//                // @@错误处理
//                this.mErrors.copyAllErrors(tExeSQL.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "LLGrpClaimCancelBL";
//                tError.functionName = "checkInputData";
//                tError.errorMessage = "该赔案正在调查中，不能删除!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//         }else{
//             this.mErrors.copyAllErrors(tExeSQL.mErrors);
//             CError tError = new CError();
//             tError.moduleName = "LLGrpClaimCancelBL";
//             tError.functionName = "checkInputData";
//             tError.errorMessage = "在LLInqApply表查找相关信息时出错!";
//             this.mErrors.addOneError(tError);
//             return false;
//         }

        return true;
    }

    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        //删除赔案
        if(this.mOperate.equals("DELETECLAIM"))
        {
            delClaim();
        }
        //删除赔案中的一个出险人
        if(this.mOperate.equals("DELETEPERSON"))
        {
            delOnePerson();
        }
/*
        //解除保单挂起
        LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
        if (!tLLLcContReleaseBL.submitData(mInputData,""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterDealBL";
            tError.functionName = "submitData";
            tError.errorMessage = "解除保单挂起失败!";
            this.mErrors .addOneError(tError);
            return false;
        }
        else
        {
            VData tempVData = new VData();
            tempVData = tLLLcContReleaseBL.getResult();
            MMap tMap = new MMap();
            tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            map.add(tMap);
        }
*/
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
            mResult.add(mLLRegisterSchema);

        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterDealBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 删除赔案
     * @return boolean
     */
    private boolean delClaim()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 删除步骤一：删除报案信息及扫描信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delReportInfo();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 删除步骤二：删除立案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delClaimInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 删除步骤三：删除分案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delCaseInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 删除步骤四：删除调查信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delInqInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 删除步骤五：删除赔案信息（匹配并理算信息）
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delMatchInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 删除步骤六：删除帐户信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delAccInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No7.0 删除步骤七：删除核赔信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delClaimUWInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No8.0 删除步骤八：删除核赔信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delOtherTable();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No9.0 删除步骤九：删除费用信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delFeeTable();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No10.0 删除步骤十：删除任务信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        delMissionInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No11.0 更新步骤十一：更新lcget表SumMoney数据
         * SumMoney ＝ SumMoney － 结案后的理算金额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        updateSumMoney();
        return true;
    }

    /**
     * 删除一个出险人 未完成
     * @return boolean
     */
    private boolean delOnePerson()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 删除步骤一：删除立案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //delClaimInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 删除步骤二：删除分案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //delCaseInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 删除步骤三：删除赔案信息（匹配并理算信息）
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //delMatchInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 删除步骤四：删除任务信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //delMissionInfo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 删除步骤四：修改状态
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //delMissionInfo();


        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No1.0 删除步骤一：删除报案信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delReportInfo()
    {
        //删除LLReportReason报案理赔类型RpNo赔案号CustomerNo出险人客户号ReasonCode理赔类型
        String strLLReportReason = "delete from LLReportReason where RpNo='" +
                                   mRptNo + "'";
        map.put(strLLReportReason, "DELETE");
        //删除LLReportAffix报案附件表
        String strLLReportAffix = "delete from LLReportAffix where RptNo='" +
                                  mRptNo + "'";
        map.put(strLLReportAffix, "DELETE");
        //删除LLReport报案表RptNo报案号
        String strLLReport = "delete from LLReport where RptNo='" + mRptNo + "'";
        map.put(strLLReport, "DELETE");
        //删除LLSubReport分案表SubRptNo分报案号CustomerNo关联客户号码
        String strLLSubReport = "delete from LLSubReport where subrptno in(select subrptno from LLReportRela where rptno='" + mRptNo +"')";
        map.put(strLLSubReport, "DELETE");
        //删除LLReportRela报案事件关联表RptNo报案号SubRptNo分报案号(事件号)
        String strLLReportRela = "delete from LLReportRela where RptNo='" + mRptNo +"'";
        map.put(strLLReportRela, "DELETE");
        //删除LLAccident事件AccNo事件号
        String strLLAccident = "delete from LLAccident where Accno in (select caserelano from LLCaseRela where caseno='" + mRptNo + "')";
        map.put(strLLAccident, "DELETE");
        //删除LLAccidentSub分事件AccNo事件号CustomerNo出险人客户号
        String strLLAccidentSub = "delete from LLAccidentSub where Accno in (select caserelano from LLCaseRela where caseno='" + mRptNo + "')";
        map.put(strLLAccidentSub, "DELETE");
        //删除LLCaseRela分案事件关联CaseRelaNo事故号CaseNo赔案号SubRptNo分案号
        String strLLCaseRela = "delete from LLCaseRela where caseno='" + mRptNo + "'";
        map.put(strLLCaseRela, "DELETE");
        //删除扫描件信息
        map.put("delete From es_doc_main where doccode='" + mRptNo + "'","DELETE");
        map.put("delete from es_doc_pages where docid in (select docid from es_doc_relation where bussno='" +mRptNo + "')", "DELETE");
        map.put("delete From es_doc_relation where bussno='" + mRptNo +"'", "DELETE");

        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No2.0 删除步骤二：删除立案信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delClaimInfo()
    {
        //删除LLGrpRegister团体立案登记总表
        String strLLGrpRegister = "delete from LLGrpRegister where RgtNo='" +
                                  mRptNo + "'";
        map.put(strLLGrpRegister, "DELETE");
        //删除LLRegister立案/申请登记总表
        String strLLRegister = "delete from LLRegister where rgtno='" + mRptNo + "'";
        map.put(strLLRegister, "DELETE");
        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No3.0 删除步骤三：删除分案信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delCaseInfo()
    {
         //删除LLAppClaimReason立案理赔类型
         String strLLAppClaimReason = "delete from LLAppClaimReason where CaseNo='" +mRptNo + "'";
         map.put(strLLAppClaimReason, "DELETE");
         //删除LLAffix附件表
         String strLLAffix = "delete from LLAffix where RgtNo='" +mRptNo + "'";
         map.put(strLLAffix, "DELETE");
         //删除LLCaseInfo分案疾病伤残明细
         String strLLCaseInfo = "delete from LLCaseInfo where ClmNo='" +mRptNo + "'";
         map.put(strLLCaseInfo, "DELETE");
         //删除LLOperation手术信息表
         String strLLOperation = "delete from LLOperation where ClmNo='" +mRptNo + "'";
         map.put(strLLOperation, "DELETE");
         //删除LLOtherFactor其它录入要素
         String strLLOtherFactor = "delete from LLOtherFactor where ClmNo='" +mRptNo + "'";
         map.put(strLLOtherFactor, "DELETE");
         //删除LLCaseReceipt分案收据明细
         //String strLLCaseReceipt = "delete from LLCaseReceipt where MainFeeNo in (select MainFeeNo from LLFeeMain where ClmNo='" +mRptNo + "')";
        String strLLCaseReceipt = "delete from LLCaseReceipt where ClmNo = '"+mRptNo+"'";
         map.put(strLLCaseReceipt, "DELETE");
         //删除LLFeeOtherItem非医药账单明细
         //String strLLFeeOtherItem = "delete from LLFeeOtherItem where MainFeeNo in (select MainFeeNo from LLFeeMain where ClmNo='" +mRptNo + "')";
         String strLLFeeOtherItem = "delete from LLFeeOtherItem where CaseNo = '" +mRptNo + "'";
         map.put(strLLFeeOtherItem, "DELETE");
         //删除LLCaseDrug药品明细
         //String strLLCaseDrug = "delete from LLCaseDrug where MainFeeNo in (select MainFeeNo from LLFeeMain where ClmNo='" +mRptNo + "')";
         String strLLCaseDrug = "delete from LLCaseDrug where CaseNo = '" +mRptNo + "'";
         map.put(strLLCaseDrug, "DELETE");
         //删除LLFeeMain账单信息主表
         String strLLFeeMain= "delete from LLFeeMain where ClmNo='" +mRptNo + "'";
         map.put(strLLFeeMain, "DELETE");
         //删除LLClaimDesc备注描述信息
         String strLLClaimDesc= "delete from LLClaimDesc where ClmNo='" +mRptNo + "'";
         map.put(strLLClaimDesc, "DELETE");
         //删除LLCase立案分案
         String strLLCase= "delete from LLCase where CaseNo='" +mRptNo + "'";
         map.put(strLLCase, "DELETE");
         //删除llcasereceiptclass社保账单表
         String strLLCaseReceiptClass = "delete from llcasereceiptclass where clmno='" +mRptNo + "'";
         map.put(strLLCaseReceiptClass, "DELETE");
        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No4.0 删除步骤四：删除调查信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delInqInfo()
    {
         //删除LLInqApply调查申请ClmNo赔案号InqNo调查序号
         String strLLInqApply= "delete from LLInqApply where ClmNo='" +mRptNo + "'";
         map.put(strLLInqApply, "DELETE");
         //删除LLInqConclusion调查结论ClmNo赔案号ConNo结论序号
         String strLLInqConclusion= "delete from LLInqConclusion where ClmNo='" +mRptNo + "'";
         map.put(strLLInqConclusion, "DELETE");
         //删除LLInqCourse调查过程ClmNo赔案号InqNo调查序号CouNo过程序号
         String strLLInqCourse= "delete from LLInqCourse where ClmNo='" +mRptNo + "'";
         map.put(strLLInqCourse, "DELETE");
         //删除LLInqFee调查费用ClmNo赔案号InqNo调查序号InqDept调查机构FeeType费用类型
         String strLLInqFee= "delete from LLInqFee where ClmNo='" +mRptNo + "'";
         map.put(strLLInqFee, "DELETE");
         //删除LLInqCertificate调查过程单证信息ClmNo赔案号InqNo调查序号CouNo过程序号CerNo单
         String strLLInqCertificate= "delete from LLInqCertificate where ClmNo='" +mRptNo + "'";
         map.put(strLLInqCertificate, "DELETE");
         //删除LLCondole慰问信息
         String strLLCondole= "delete from LLCondole where ClmNo='" +mRptNo + "'";
         map.put(strLLCondole, "DELETE");
         //删除LLSubmitApply呈报申请
         String strLLSubmitApply= "delete from LLSubmitApply where ClmNo='" +mRptNo + "'";
         map.put(strLLSubmitApply, "DELETE");
        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No5.0 删除步骤五：删除赔案信息（匹配并理算信息）
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delMatchInfo()
    {
        //删除LLClaimPolicy赔案保单名细
        String strLLClaimPolicy= "delete from LLClaimPolicy where ClmNo='" +mRptNo + "'";
        map.put(strLLClaimPolicy, "DELETE");
        //删除LLClaimDetail赔付明细
        String strLLClaimDetail = "delete from LLClaimDetail where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimDetail, "DELETE");
        //删除LLClaimDutyFee责任费用统计
        String strLLClaimDutyFee = "delete from LLClaimDutyFee where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimDutyFee, "DELETE");
        //删除LLToClaimDuty待理算责任
        String strLLToClaimDuty = "delete from LLToClaimDuty where CaseNo='" + mRptNo + "'";
        map.put(strLLToClaimDuty, "DELETE");
        //删除LLToClaimDutyFee临时表责任费用统计
        String strLLToClaimDutyFee = "delete from LLToClaimDutyFee where ClmNo='" + mRptNo + "'";
        map.put(strLLToClaimDutyFee, "DELETE");
        //删除LLClaim赔案
        String strLLClaim = "delete from LLClaim where ClmNo='" + mRptNo + "'";
        map.put(strLLClaim, "DELETE");
        //删除LLClaimDutyKind赔案理赔类型
        String strLLClaimDutyKind = "delete from LLClaimDutyKind where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimDutyKind, "DELETE");
        //删除LLStandPayInfo预估金额信息表
        String strLLStandPayInfo = "delete from LLStandPayInfo where CaseNo='" + mRptNo + "'";
        map.put(strLLStandPayInfo, "DELETE");
        //删除LLBalance理赔结算表
        String strLLBalance = "delete from LLBalance where ClmNo='" + mRptNo + "'";
        map.put(strLLBalance, "DELETE");
        //删除LLBnf理赔受益人账户
        String strLLBnf = "delete from LLBnf where ClmNo='" + mRptNo + "'";
        map.put(strLLBnf, "DELETE");
        //删除LLClaimAccount赔付账户明细
        String strLLClaimAccount = "delete from LLClaimAccount where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimAccount, "DELETE");
        //删除LLExempt保费豁免记录表
        String strLLExempt = "delete from LLExempt where ClmNo='" + mRptNo + "'";
        map.put(strLLExempt, "DELETE");

        //目前系统可能没有用到，但删除不会出错
//        //删除LLPrepayDetail预付明细记录
//        String strLLPrepayDetail = "delete from LLPrepayDetail where ClmNo='" + mRptNo + "'";
//        map.put(strLLPrepayDetail, "DELETE");
//        //删除LLPrepayClaim预付赔案记录
//        String strLLPrepayClaim = "delete from LLPrepayClaim where ClmNo='" + mRptNo + "'";
//        map.put(strLLPrepayClaim, "DELETE");
        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No6.0 删除步骤六：删除帐户信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delAccInfo()
    {
        //删除LCInsureAccTrace保险帐户表记价履历表
        logger.debug("======mRptNo"+mRptNo);
        //注意这种更新或删除条件要慎重
        String strclass = " update lcinsureaccclass set baladate='1900-01-01',lastaccbala=0,insuaccbala=0 "
                          + " where polno in ( select distinct polno from lcinsureacctrace "
                          + " where otherno='"+mRptNo+"' and money<0 )";
        logger.debug("--------strclass--"+strclass);
        map.put(strclass,"UPDATE");

        String strLCInsureAccTrace = "delete from LCInsureAccTrace where OtherNo='" +
                        mRptNo + "'";
        logger.debug("--------strLCInsureAccTrace--"+strLCInsureAccTrace);
        map.put(strLCInsureAccTrace, "DELETE");

        //更新LCInsureAccClass

        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No7.0 删除步骤七：删除核赔信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delClaimUWInfo()
    {
        //删除LLClaimUWMain案件核赔表
        String strLLClaimUWMain = "delete from LLClaimUWMain where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimUWMain, "DELETE");
        //删除LLClaimUWMDetail案件核赔履历表
        String strLLClaimUWMDetail = "delete from LLClaimUWMDetail where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimUWMDetail, "DELETE");
        //删除LLClaimUnderwrite核赔
        String strLLClaimUnderwrite = "delete from LLClaimUnderwrite where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimUnderwrite, "DELETE");
        //删除LLClaimUWDetail核赔履历
        String strLLClaimUWDetail = "delete from LLClaimUWDetail where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimUWDetail, "DELETE");
        //删除LLClaimUWDutyFee核赔责任费用统计
        String strLLClaimUWDutyFee = "delete from LLClaimUWDutyFee where ClmNo='" + mRptNo + "'";
        map.put(strLLClaimUWDutyFee, "DELETE");
        //删除LLClaimSaleChnl理赔销售渠道信息表
        String strLLClaimSaleChnl = "delete from LLClaimSaleChnl where CaseNo='" + mRptNo + "'";
        map.put(strLLClaimSaleChnl, "DELETE");

        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No8.0 删除步骤八：删除其他表信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delOtherTable()
    {
        /*//删除LLContState理赔保单状态暂存表
         String strSQL = "delete from LLGrpRegister where rgtno='" + mRptNo + "'";
         map.put(strSQL, "DELETE");
         //删除LCContHangUpState保单挂起状态表
         String strSQL = "delete from LLGrpRegister where rgtno='" + mRptNo + "'";
         map.put(strSQL, "DELETE");*/
         //删除LOPRTManager打印管理表
         String strLOPRTManager = "delete from LOPRTManager where OtherNo='" + mRptNo + "' and OtherNoType='05'";
         map.put(strLOPRTManager, "DELETE");
         //删除LLPRTManager理赔打印管理表
         String strLLPRTManager = "delete from LLPRTManager where ClmNo='" + mRptNo + "'";
         map.put(strLLPRTManager, "DELETE");
         //删除LLCaseBack案件回退记录
         String strLLCaseBack = "delete from LLCaseBack where OldClmNo='" + mRptNo + "'";
         map.put(strLLCaseBack, "DELETE");
        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No9.0 删除步骤九：删除费用信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delFeeTable()
    {
        //删除LJSGet应付总表
        String strLJSGet = "delete from LJSGet where OtherNo='" + mRptNo + "' and OtherNoType='5'";
        map.put(strLJSGet, "DELETE");
        //删除LJSGetClaim应付总表
        String strLJSGetClaim = "delete from LJSGetClaim where OtherNo='" + mRptNo + "' and OtherNoType='5'";
        map.put(strLJSGetClaim, "DELETE");
        //删除LJAGet应付总表
        String strLJAGet = "delete from LJAGet where OtherNo='" + mRptNo + "' and OtherNoType='5' and operstate='0' ";
        map.put(strLJAGet, "DELETE");
        //删除LJAGetClaim应付总表
        String strLJAGetClaim = "delete from LJAGetClaim where OtherNo='" + mRptNo + "' and OtherNoType='5' and operstate='0' ";
        map.put(strLJAGetClaim, "DELETE");
        //删除LLJSPay理赔应收总表暂存表
        String strLLJSPay = "delete from LLJSPay where ClmNo='" + mRptNo + "'";
        map.put(strLLJSPay, "DELETE");
        //删除LLJSPayPerson理赔应收个人交费暂存表
        String strLLJSPayPerson = "delete from LLJSPayPerson where ClmNo='" + mRptNo + "'";
        map.put(strLLJSPayPerson, "DELETE");
        //删除LLJSGet理赔应付总表
        String strLLJSGet = "delete from LLJSGet where ClmNo='" + mRptNo + "'";
        map.put(strLLJSGet, "DELETE");
        //删除LLJSGetDraw理赔给付表(生存领取_应付)暂存表
        String strLLJSGetDraw = "delete from LLJSGetDraw where ClmNo='" + mRptNo + "'";
        map.put(strLLJSGetDraw, "DELETE");
        return true;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No10.0 删除步骤十：删除任务信息
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean delMissionInfo()
    {
        //删除LWMission理赔打印管理表
         String strLWMission = "delete from LWMission where processid='0000000005' and MissionProp1='" + mRptNo + "'";
         map.put(strLWMission, "DELETE");
         //删除LWNOTEPAD工作流记事本表
         String strLWNOTEPAD = "delete from LWNOTEPAD where MissionID in (select MissionID from LWMission where processid='0000000005' and MissionProp1='" + mRptNo + "')";
         map.put(strLWNOTEPAD, "DELETE");
         //删除LWLOCK工作流应用锁表
         String strLWLOCK = "delete from LWLOCK where MissionID in (select MissionID from LWMission where processid='0000000005' and MissionProp1='" + mRptNo + "')";
         map.put(strLWLOCK, "DELETE");
        return true;
    }
    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * No11.0 更新步骤十一：更新lcget表SumMoney数据为结案前的金额
     * SumMoney ＝ SumMoney － 结案后的理算金额
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean  updateSumMoney(){
        String tSql = " select a.polno,a.getdutycode,a.dutycode,a.realpay from llclaimdetail a,llclaim b"
                    + " where a.caseno = '"+mRptNo+"' and a.caseno = b.caseno and b.clmstate = '60'";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if(tSSRS!=null){
            for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
                String tPolNo       = tSSRS.GetText(i, 1); //保单号
                String tGetDutyCode = tSSRS.GetText(i, 2); //给付责任编码
                String tDutyCode    = tSSRS.GetText(i, 3); //责任编码
                String tRealPay     = tSSRS.GetText(i, 4); //核赔赔付金额
                String tSql2 = " select SumMoney from lcget where"
                             + " polno = '"+tPolNo+"'"
                             + " and getdutycode = '"+tGetDutyCode+"'"
                             + " and dutycode = '"+tDutyCode+"'";
                String tSumMoney = tExeSQL.getOneValue(tSql2);
                if(tSumMoney!=null&&!tSumMoney.equals("")){
                    double dSumMoney = Double.parseDouble(tSumMoney);
                    double dRealPay = Double.parseDouble(tRealPay);
                    dSumMoney = PubFun.setPrecision(dSumMoney, "0.00"); //已经赔付过的金额
                    dRealPay = PubFun.setPrecision(dRealPay, "0.00"); //核赔赔付金额
                    double tMoney = dSumMoney - dRealPay; //还原到删除案件前的金额
                    String tSql3 = " update lcget set summoney = '" + tMoney +
                                   "' where"
                                   + " polno = '" + tPolNo + "'"
                                   + " and getdutycode = '" + tGetDutyCode +
                                   "'"
                                   + " and dutycode = '" + tDutyCode + "'";
                    map.put(tSql3, "UPDATE");
                }
            }

        }

        return true;
    }

    public static void main(String[] args)
    {
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("RptNo","580101017245");

        VData tVData = new VData();
        tVData.addElement(tTransferData);

        LLGrpClaimCancelBL tLLGrpClaimCancelBL = new LLGrpClaimCancelBL();
        tLLGrpClaimCancelBL.submitData(tVData, "DELETECLAIM");
        logger.debug("DELETE OVER!");
    }

}



