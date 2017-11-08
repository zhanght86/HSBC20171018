package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：理赔打印通用方法</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTPubFunBL
{
private static Logger logger = Logger.getLogger(LLPRTPubFunBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();


    private String mClmNo    = "";     //赔案号
    private String mCusNo    = "";     //客户号



    public LLPRTPubFunBL(){}


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－事故信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到事件信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLAccidentSchema getLLAccident(String pClmNo)
    {

        String tSql = "select LLAccident.* from LLAccident ,LLCaseRela where 1=1 "
            +" and LLAccident.AccNo = LLCaseRela.CaseRelaNo"
            +" and LLCaseRela.CaseNo = '"+pClmNo+"'";


        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
        LLAccidentDB tLLAccidentDB = new LLAccidentDB();
        LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(tSql);


        if(tLLAccidentSet == null || tLLAccidentSet.size() != 1 )
        {
            this.mErrors.copyAllErrors(tLLAccidentDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案信息发生错误!";
            this.mErrors.addOneError(tError);
        }
        else
        {
            tLLAccidentSchema = tLLAccidentSet.get(1);
            logger.debug("aaaaaa");
        }
        return tLLAccidentSchema;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－报案信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 得到报案附件的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLReportAffixSet getLLReportAffix(String pClmNo, String pCusNo)
    {
        LLReportAffixSchema tLLReportAffixSchema = new LLReportAffixSchema();
        LLReportAffixDB tLLReportAffixDB = new LLReportAffixDB();
        tLLReportAffixDB.setRptNo(pClmNo);
        tLLReportAffixDB.setCustomerNo(pCusNo);
        LLReportAffixSet tLLReportAffixSet = tLLReportAffixDB.query();

        if(tLLReportAffixSet == null || tLLReportAffixSet.size() == 0 )
        {
            this.mErrors.copyAllErrors(tLLReportAffixDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案下的理赔类型发生错误!";
            this.mErrors.addOneError(tError);
        }
        return tLLReportAffixSet;
    }



    /**
     * 得到报案附件的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public SSRS getSSRSLLReportAffix(String pClmNo,String pCusNo)
    {

        String tSql = "select * from LLReportAffix where 1 =1 "
            +" and RptNo='" + pClmNo + "'"
            +" and CustomerNo='" + pCusNo + "'";

        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "dealData";
            tError.errorMessage = "得到报案附件信息时发生错误!"+tSql;
            this.mErrors.addOneError(tError);
        }

        return tSSRS;
    }




    /**
     * 得到报案理赔类型的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLReportReasonSet getLLReportReason(String pClmNo, String pCusNo)
    {
        LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema();
        LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
        tLLReportReasonDB.setRpNo(pClmNo);
        tLLReportReasonDB.setCustomerNo(pCusNo);
        LLReportReasonSet tLLReportReasonSet = tLLReportReasonDB.query();

        if(tLLReportReasonSet == null || tLLReportReasonSet.size() == 0 )
        {
            this.mErrors.copyAllErrors(tLLReportReasonDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案下的理赔类型发生错误!";
            this.mErrors.addOneError(tError);
        }
        return tLLReportReasonSet;
    }


    /**
     * 得到报案理赔类型的字符串信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public String getSLLReportReason(String pClmNo, String pCusNo)
    {
        LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema();
        LLReportReasonSet tLLReportReasonSet = getLLReportReason(pClmNo,pCusNo);

        String tReturn = "";
        for (int i=1;i<=tLLReportReasonSet.size();i++)
        {
            tLLReportReasonSchema = tLLReportReasonSet.get(i);
            String tReasonCode = tLLReportReasonSchema.getReasonCode();
            String tReasonName = getLDCode("llclaimtype",tReasonCode);

            if (i==1)
            {
                tReturn = tReasonName;
            }
            else
            {
                tReturn = tReturn+"/"+tReasonName;
            }
        }
        return tReturn;
    }




    /**
     * 得到报案表中的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLReportSchema getLLReport(String pClmNo)
    {
        LLReportSchema tLLReportSchema = new LLReportSchema();
        LLReportDB tLLReportDB = new LLReportDB();
        tLLReportDB.setRptNo(pClmNo);
        LLReportSet tLLReportSet = tLLReportDB.query();

        if(tLLReportSet == null || tLLReportSet.size() != 1 )
        {
            this.mErrors.copyAllErrors(tLLReportDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案信息发生错误!";
            this.mErrors.addOneError(tError);
        }
        else
        {
            tLLReportSchema = tLLReportSet.get(1);
        }

        return tLLReportSchema;
    }


    /**
     * 得到报案分案表中的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLSubReportSchema getLLSubReport(String pClmNo, String pCusNo)
    {
        LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
        LLSubReportDB tLLSubReportDB = new LLSubReportDB();
        tLLSubReportDB.setSubRptNo(pClmNo);
        tLLSubReportDB.setCustomerNo(pCusNo);
        LLSubReportSet tLLSubReportSet = tLLSubReportDB.query();

        if(tLLSubReportSet == null || tLLSubReportSet.size() != 1 )
        {
            this.mErrors.copyAllErrors(tLLSubReportDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案下的理赔类型发生错误!";
            this.mErrors.addOneError(tError);
        }
        else
        {
            tLLSubReportSchema = tLLSubReportSet.get(1);
        }

        return tLLSubReportSchema;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－立案信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到立案附件的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLAffixSet getLLAffix(String pClmNo, String pCusNo)
    {
        LLAffixSchema tLLAffixSchema = new LLAffixSchema();
        LLAffixDB tLLAffixDB = new LLAffixDB();
        tLLAffixDB.setRgtNo(pClmNo);
        tLLAffixDB.setCustomerNo(pCusNo);
        LLAffixSet tLLAffixSet = tLLAffixDB.query();

        if(tLLAffixSet == null || tLLAffixSet.size() == 0 )
        {
            this.mErrors.copyAllErrors(tLLAffixDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案下的理赔类型发生错误!";
            this.mErrors.addOneError(tError);
        }
        return tLLAffixSet;
    }



    /**
     * 得到立案附件的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public SSRS getSSRSLLAffix(String pClmNo,String pCusNo)
    {

        String tSql = "select * from LLReportAffix where 1 =1 "
            +" and RptNo='" + pClmNo + "'"
            +" and CustomerNo='" + pCusNo + "'";

        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "dealData";
            tError.errorMessage = "得到立案附件信息时发生错误!"+tSql;
            this.mErrors.addOneError(tError);
        }
        return tSSRS;
    }


    /**
     * 得到立案理赔类型的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLAppClaimReasonSet getLLAppClaimReason(String pClmNo, String pCusNo)
    {
        LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
        LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
        tLLAppClaimReasonDB.setCaseNo(pClmNo);
        tLLAppClaimReasonDB.setCustomerNo(pCusNo);
        LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();

        if(tLLAppClaimReasonSet == null || tLLAppClaimReasonSet.size() == 0 )
        {
            this.mErrors.copyAllErrors(tLLAppClaimReasonDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案下的理赔类型发生错误!";
            this.mErrors.addOneError(tError);
        }
        else
        {
            tLLAppClaimReasonSchema = tLLAppClaimReasonSet.get(1);
        }

        return tLLAppClaimReasonSet;
    }



    /**
     * 得到立案理赔类型的字符串信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public String getSLLAppClaimReason(String pClmNo, String pCusNo)
    {
        LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
        LLAppClaimReasonSet tLLAppClaimReasonSet = getLLAppClaimReason(pClmNo,pCusNo);

        String tReturn = "";
        for (int i=1;i<=tLLAppClaimReasonSet.size();i++)
        {
            tLLAppClaimReasonSchema = tLLAppClaimReasonSet.get(i);
            String tReasonCode = tLLAppClaimReasonSchema.getReasonCode();
            String tReasonName = getLDCode("llclaimtype",tReasonCode);

            if (i==1)
            {
                tReturn = tReasonName;
            }
            else
            {
                tReturn = tReturn+"/"+tReasonName;
            }
        }
        return tReturn;
    }


    /**
     * 得到立案表中的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLRegisterSchema getLLRegister(String pClmNo)
    {
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        tLLRegisterDB.setRgtNo(pClmNo);
        LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();

        if(tLLRegisterSet == null || tLLRegisterSet.size()  != 1 )
        {
            this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案信息发生错误!";
            this.mErrors.addOneError(tError);
        }
        else
        {
            tLLRegisterSchema = tLLRegisterSet.get(1);
        }

        return tLLRegisterSchema;
    }



    /**
     * 得到立案分案表中的信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLCaseSchema getLLCase(String pClmNo, String pCusNo)
    {
        LLCaseSchema tLLCaseSchema = new LLCaseSchema();
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(pClmNo);
        tLLCaseDB.setCustomerNo(pCusNo);
        LLCaseSet tLLCaseSet = tLLCaseDB.query();

        if(tLLCaseSet == null || tLLCaseSet.size() != 1 )
        {
            this.mErrors.copyAllErrors(tLLCaseDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "获取报案下的理赔类型发生错误!";
            this.mErrors.addOneError(tError);
        }
        else
        {
            tLLCaseSchema = tLLCaseSet.get(1);
        }

        return tLLCaseSchema;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案保单信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 获取赔案涉及的合同信息
     */
    public LCContSet getClmLCCont(String pClmNo)
    {
        String tReturn = "";
        String tSql = "";

        tSql = "select * from LCCont where 1 =1 "
                +" and ContNo in (select distinct ContNo "
                    +" from LLClaimPolicy where 1=1 and ClmNo='" + pClmNo + "')";

        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(tSql);
        if(tLCContSet == null || tLCContSet.size() == 0 )
        {
            this.mErrors.copyAllErrors(tLCContDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getNewInsLCCont";
            tError.errorMessage = "获取赔案涉及的合同信息发生错误!";
            this.mErrors.addOneError(tError);
        }
        return tLCContSet;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－调查信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 获取调查过程信息
     */
    public LLInqApplySet getLLInqApply(String pClmNo,String pInqDept)
    {
        LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema();
        LLInqApplyDB tLLInqApplyDB = new LLInqApplyDB();
        tLLInqApplyDB.setClmNo(pClmNo);                      //赔案号


        if (!pInqDept.equals(""))
        {
            tLLInqApplyDB.setInqDept(pInqDept);              //调查机构信息
        }

        LLInqApplySet tLLInqApplySet = tLLInqApplyDB.query();
        return tLLInqApplySet;
    }


    /**
     * 获取调查过程单证信息
     */
    public LLInqCertificateSet getLLInqCertificate(String pClmNo)
    {
        LLInqCertificateSchema tLLInqCertificateSchema = new LLInqCertificateSchema();
        LLInqCertificateDB tLLInqCertificateDB = new LLInqCertificateDB();
        tLLInqCertificateDB.setClmNo(pClmNo);                      //赔案号
        LLInqCertificateSet tLLInqCertificateSet = tLLInqCertificateDB.query();
        return tLLInqCertificateSet;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案计算信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 获取赔案审核信息
     */
    public LLClaimUWMainSchema getLLClaimUWMain(String pClmNo)
    {
        String tReturn = "";
        LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
        LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
        tLLClaimUWMainDB.setClmNo(pClmNo);
        if (tLLClaimUWMainDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimUWMainDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getCusSex";
            tError.errorMessage = "查询赔案审核信息失败!";
            this.mErrors.addOneError(tError);
        }
        tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());
        return tLLClaimUWMainSchema;
    }


    /**
     * 获取赔案保项信息
     */
    public LLClaimDetailSet getLLClaimDetail(String pClmNo ,String pContNo,String pPolNo,
            String pGetDutyKind,String pGetDutyCode,String pGiveType)
    {
        String tReturn = "";
        LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        tLLClaimDetailDB.setClmNo(pClmNo);

        if (!pContNo.equals(""))
        {
            tLLClaimDetailDB.setContNo(pContNo);            //合同编号
        }

        if (!pPolNo.equals(""))
        {
            tLLClaimDetailDB.setPolNo(pPolNo);              //险种编号
        }

        if (!pGetDutyKind.equals(""))
        {
            tLLClaimDetailDB.setGetDutyKind(pGetDutyKind);  //理赔类型
        }


        if (!pGetDutyCode.equals(""))
        {
            tLLClaimDetailDB.setGetDutyCode(pGetDutyCode);  //责任编码
        }

        if (!pGiveType.equals(""))
        {
            tLLClaimDetailDB.setGiveType(pGiveType);        //0给付,1拒付
        }

        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
        return tLLClaimDetailSet;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案结算信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 获取合同终止信息
     */
    public LLContStateSet getLLContState(String pClmNo,String pContNo)
    {
        String tReturn = "";
        String tSql = "";
        tSql = "select * from LLContState where 1 =1 "
            +" and ClmNo ='" + pClmNo + "'"
            +" and ContNo='" + pContNo + "'";

        LLContStateDB tLLContStateDB = new LLContStateDB();
        LLContStateSet tLLContStateSet = tLLContStateDB.executeQuery(tSql);

        return tLLContStateSet;
    }



    /**
     * 获取赔案结算的合同信息
     */
    public LCContSet getLLBalCont(String pClmNo,String pBalType,String pSubBalType)
    {
        String tReturn = "";
        String tSql = "";
        tSql = "select * from LCCont where 1 =1 "
            +" and ContNo in (select distinct ContNo  from LLBalance "
                +" where 1=1 and ClmNo='" + pClmNo + "')"
                +" and FeeOperationType in ('"+pBalType+"')";

        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(tSql);


        return tLCContSet;
    }


    /**
     * 获取赔案结算信息
     */
    public LLBalanceSet getLLBalance(String pClmNo,String pContNo,String pPolNo,String pBalType)
    {


        LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
        LLBalanceDB tLLBalanceDB = new LLBalanceDB();
        tLLBalanceDB.setClmNo(pClmNo);                      //赔案号
        if (!pContNo.equals(""))
        {
            tLLBalanceDB.setContNo(pContNo);                //合同号
        }
        if (!pPolNo.equals(""))
        {
            tLLBalanceDB.setPolNo(pPolNo);                  //险种号
        }

        if (!pBalType.equals(""))
        {
            tLLBalanceDB.setFeeOperationType(pBalType);     //结算的业务类型
        }

        LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();

        return tLLBalanceSet;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案费用信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 获取赔案费用的明细信息
     */
    public LLClaimDutyFeeSet getLLClaimDutyFee(String pClmNo,String pContNo,String pPolNo)
    {

        LLClaimDutyFeeDB tLLClaimDutyFeeDB = new LLClaimDutyFeeDB();
        tLLClaimDutyFeeDB.setClmNo(pClmNo);

        if (!pContNo.equals(""))
        {
//            tLLClaimDutyFeeDB.setContNo(pContNo);           //合同编号
        }

        if (!pPolNo.equals(""))
        {
            tLLClaimDutyFeeDB.setPolNo(pPolNo);              //险种编号
        }

        LLClaimDutyFeeSet tLLClaimDutyFeeSet = tLLClaimDutyFeeDB.query();
        return tLLClaimDutyFeeSet;

    }





    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案豁免信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 获取赔案豁免的合同信息
     */
    public LCContSet getLLExemptCont(String pClmNo)
    {

        String tSql = "";
        tSql = "select * from LCCont where 1 =1 "
            +" and ContNo in (select distinct ContNo  from LLExempt "
                +" where 1=1 and ClmNo='" + pClmNo + "')";


        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(tSql);


        return tLCContSet;
    }


    /**
     * 获取赔案豁免的保项信息
     */
    public LLExemptSet getLLExemptDetail(String pClmNo,String pContNo)
    {
        String tReturn = "";
        String tSql = "";
        tSql = "select * from LLExempt where 1 =1 "
            +" and ClmNo ='" + pClmNo + "'"
            +" and ContNo='" + pContNo + "'";

        LLExemptDB tLLExemptDB = new LLExemptDB();
        LLExemptSet tLLExemptSet = tLLExemptDB.executeQuery(tSql);


        return tLLExemptSet;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案预付信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 获取赔案预付的合同信息
     */
    public LCContSet getLLPrepayCont(String pClmNo)
    {

        String tSql = "";
        tSql = "select * from LCCont where 1 =1 "
            +" and ContNo in (select distinct ContNo  from LLPrepayDetail "
                +" where 1=1 and ClmNo='" + pClmNo + "')";
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(tSql);

        return tLCContSet;
    }



    /**
     * 获取赔案预付的保项信息
     */
    public LLPrepayDetailSet getLLPrepayDetail(String pClmNo ,String pContNo,String pPolNo,
            String pGetDutyKind,String pGetDutyCode,String pGiveType)
    {

        LLPrepayDetailDB tLLPrepayDetailDB = new LLPrepayDetailDB();
        tLLPrepayDetailDB.setClmNo(pClmNo);

        if (!pContNo.equals(""))
        {
            tLLPrepayDetailDB.setContNo(pContNo);           //合同编号
        }

        if (!pPolNo.equals(""))
        {
            tLLPrepayDetailDB.setPolNo(pPolNo);              //险种编号
        }

        if (!pGetDutyKind.equals(""))
        {
            tLLPrepayDetailDB.setGetDutyKind(pGetDutyKind);  //理赔类型
        }


        if (!pGetDutyCode.equals(""))
        {
            tLLPrepayDetailDB.setGetDutyCode(pGetDutyCode);  //责任编码
        }



        LLPrepayDetailSet tLLPrepayDetailSet = tLLPrepayDetailDB.query();


        return tLLPrepayDetailSet;
    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－赔案财务信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 获取赔案受益人信息
     */
    public LLBnfSet getLLBnf(String pClmNo ,String pContNo,String pPolNo,String pBnfKind)
    {

        LLBnfDB tLLBnfDB = new LLBnfDB();
        tLLBnfDB.setClmNo(pClmNo);

        if (!pContNo.equals(""))
        {
            tLLBnfDB.setContNo(pContNo);           //合同编号
        }

        if (!pPolNo.equals(""))
        {
            tLLBnfDB.setPolNo(pPolNo);              //险种编号
        }

        if (!pBnfKind.equals(""))
        {
            tLLBnfDB.setBnfKind(pBnfKind);          //受益类型,A赔案,B预付
        }

        LLBnfSet tLLBnfSet = tLLBnfDB.query();
        return tLLBnfSet;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－既往赔案信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 得到立案表中的既往赔案信息
     * @param pClmNo:赔案号
     * @param pCusNo:客户号
     * @return
     */
    public LLRegisterSet getHisLLRegister(String pClmNo,String pCusNo)
    {


        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();


        String tSql = "select * from LLRegister where 1=1 "
            +" and RgtNo in (select distinct CaseNo from LLCase where 1=1 "
            +" CustomerNo = '"+pCusNo+"' and CaseNo not in ('"+pClmNo+"'))";

        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        LLRegisterSet tLLRegisterSet = tLLRegisterDB.executeQuery(tSql);

        return tLLRegisterSet;
    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－承保保单信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 得到客户的性别
     */
    public String getCustSex(String pCusNo)
    {
        String tReturn = "";
        LDPersonSchema tLDPersonSchema = new LDPersonSchema();
        LDPersonDB tLDPersonDB = new LDPersonDB();
        tLDPersonDB.setCustomerNo(pCusNo);


        if (tLDPersonDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLDPersonDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getCusSex";
            tError.errorMessage = "查询客户信息失败!";
            this.mErrors.addOneError(tError);
            return "";
        }
        tLDPersonSchema.setSchema(tLDPersonDB.getSchema());

        String tSex = StrTool.cTrim(tLDPersonSchema.getSex());
        if (tSex.equals("0"))
        {
            tReturn = "男";
        }
        else if (tSex.equals("1"))
        {
            tReturn = "女";
        }
        else
        {
            tReturn = "";
        }
        return tReturn;

    }


    /**
     * 最新合同生效对应日信息
     */
    public String getNewCValiDate(String pCusType , String pCusNo)
    {
        String tReturn = "";
        String tSql = "";


        if (pCusType.equals("00"))          //作为投保人
        {
            tSql = "select max(CValiDate) from LCCont where 1 =1 "
                +" and AppntNo ='" + pCusNo + "'";
        }
        else if (pCusType.equals("01"))     //作为被保人
        {
            tSql = "select max(CValiDate) from LCCont where 1 =1 "
                +" and InsuredNo ='" + pCusNo + "'";
        }
        ExeSQL tExeSQL = new ExeSQL();
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql)).substring(0,10);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getNewCValiDate";
            tError.errorMessage = "获取作为最新合同生效对应日信息发生错误!"+tSql;
            this.mErrors.addOneError(tError);
        }
        return tReturn;
    }


    /**
     * 获取最新合同生效对应日信息所对应的合同信息
     */
    public LCContSet getNewLCCont(String pCusType , String pCusNo)
    {
        String tReturn = "";
        String tSql = "";

        String tCValiDate = getNewCValiDate(pCusType,pCusNo);

        if (pCusType.equals("00"))          //作为投保人
        {
            tSql = "select * from LCCont where 1 =1 "
                +" and AppntNo ='" + pCusNo + "'"
                +" and CValiDate=to_date('"+tCValiDate+"','yyyy-mm-dd') ";
        }
        else if (pCusType.equals("01"))     //作为被保人
        {
            tSql = "select * from LCCont where 1 =1 "
                +" and InsuredNo ='" + pCusNo + "'"
                +" and CValiDate=to_date('"+tCValiDate+"','yyyy-mm-dd') ";
        }

        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(tSql);
        if(tLCContSet == null || tLCContSet.size() == 0 )
        {
            this.mErrors.copyAllErrors(tLCContDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getNewInsLCCont";
            tError.errorMessage = "获取最新合同生效对应日信息所对应的合同信息发生错误!";
            this.mErrors.addOneError(tError);
        }
        return tLCContSet;
    }



    /**
     * 得到代理人信息
     */
    public LAAgentSchema getLAAgent(String pCusType , String pCusNo)
    {
        String tLAAgentCode = getNewLCCont(pCusType,pCusNo).get(1).getAgentCode();

        LAAgentSchema tLAAgentSchema = new LAAgentSchema();
        LAAgentDB tLAAgentDB = new LAAgentDB();
        tLAAgentDB.setAgentCode(tLAAgentCode);

        if (tLAAgentDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLAAgent";
            tError.errorMessage = "查询代理人信息失败!";
            this.mErrors.addOneError(tError);

        }
        tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
        return tLAAgentSchema;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－产品定义息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到险种信息,M主险,S附险
     */
    private LMRiskAppSchema getLMRiskApp(String pRiskCode)
    {
        LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        tLMRiskAppDB.setRiskCode(pRiskCode);            //险种编号

        if (tLMRiskAppDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getCusSex";
            tError.errorMessage = "查询险种定义描述信息失败!";
            this.mErrors.addOneError(tError);
        }
        tLMRiskAppSchema.setSchema(tLMRiskAppDB.getSchema());


        return tLMRiskAppSchema;
    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－其他信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到医院信息
     */
    public LLCommendHospitalSet getLLCommendHospital()
    {
        LAAgentSchema tLAAgentSchema = new LAAgentSchema();
        String tSql = "";
        tSql = "select * from LLCommendHospital";
        LLCommendHospitalDB tLLCommendHospitalDB = new LLCommendHospitalDB();
        LLCommendHospitalSet tLLCommendHospitalSet = tLLCommendHospitalDB.executeQuery(tSql);
        return tLLCommendHospitalSet;
    }



   /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    *
    * －－－－－－－－－－－－－－－理赔操作人员所在的Schema－－－－－－－－－－－－－－－
    *
    * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    */

   /**
    * 理赔操作人员所在的信息
    */
   public LLClaimUserSchema getLLClaimUser(String pUserCode)
   {
       LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
       LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
       tLLClaimUserDB.setUserCode(pUserCode);

       if (tLLClaimUserDB.getInfo() == false)
       {
           // @@错误处理
           this.mErrors.copyAllErrors(tLLClaimUserDB.mErrors);
           CError tError = new CError();
           tError.moduleName = "LLPRTPubFunBL";
           tError.functionName = "getLAAgent";
           tError.errorMessage = "得到理赔用户信息失败!";
           this.mErrors.addOneError(tError);
       }

       tLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());
       return tLLClaimUserSchema;
   }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－代码信息－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到代码表的汉字的信息
     * @param pCodeType:代码类型
     * @param pCode:代码编号
     * @return
     */
    public String getLDCode(String pCodeType, String pCode)
    {

        LDCodeSchema tLDCodeSchema = new LDCodeSchema();
        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCodeType(pCodeType);
        tLDCodeDB.setCode(pCode);

        if (tLDCodeDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLDCode";
            tError.errorMessage = "查询代码表信息失败!";
            this.mErrors.addOneError(tError);
            return "";
        }
        tLDCodeSchema.setSchema(tLDCodeDB.getSchema());
        String tReturn = StrTool.cTrim(tLDCodeSchema.getCodeName());

        return tReturn;
    }


    /**
     * 得到给付责任的汉字信息
     * @param pCodeType:代码类型
     * @param pCode:代码编号
     * @return
     */
    public String getLMDutyGetClm(String pGetDutyKind, String pGetDutyCode)
    {
        LMDutyGetClmSchema tLMDutyGetClmSchema = new LMDutyGetClmSchema();
        LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
        tLMDutyGetClmDB.setGetDutyKind(pGetDutyKind);
        tLMDutyGetClmDB.setGetDutyCode(pGetDutyCode);

        if (tLMDutyGetClmDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLMDutyGetClmDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLDCode";
            tError.errorMessage = "查询给付责任信息失败!";
            this.mErrors.addOneError(tError);
            return "";
        }
        tLMDutyGetClmSchema.setSchema(tLMDutyGetClmDB.getSchema());
        String tReturn = StrTool.cTrim(tLMDutyGetClmSchema.getGetDutyName());

        return tReturn;
    }

    /**
     * 得到打印管理表的信息
     * @param pClmNo 赔案号
     * @return LOPRTManagerSchema
     */
    public LOPRTManagerSchema getLOPRTManager(String pPrtSeq)
    {

        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
        LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
        tLOPRTManagerDB.setPrtSeq(pPrtSeq);

        if (tLOPRTManagerDB.getInfo() == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPubFunBL";
            tError.functionName = "getLOPRTManager";
            tError.errorMessage = "查询代码表信息失败!";
            this.mErrors.addOneError(tError);
            return null;
        }
        tLOPRTManagerSchema.setSchema(tLOPRTManagerDB.getSchema());

        return tLOPRTManagerSchema;
    }

    /**
     * 得到打印管理表的信息
     * @param ClmNo 赔案号
     * @return true or false
     */
    public boolean isDeadClaim(String ClmNo)
    {
        LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
        tLLAppClaimReasonDB.setCaseNo(ClmNo);
        LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
        if (tLLAppClaimReasonSet.size() > 0)
        {
            for (int i = 0; i < tLLAppClaimReasonSet.size(); i++)
            {
                String tCode = tLLAppClaimReasonSet.get(i).getReasonCode().substring(1,3);
                if (tCode.equals("00"))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
