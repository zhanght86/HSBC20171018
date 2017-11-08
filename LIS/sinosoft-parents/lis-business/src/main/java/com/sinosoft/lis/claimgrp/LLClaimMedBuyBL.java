package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LLClaimMedBuyBL {
private static Logger logger = Logger.getLogger(LLClaimMedBuyBL.class);
    public CErrors mErrors = new CErrors();
    /** 全局数据 */
    private GlobalInput mG = new GlobalInput();
    public TransferData mTransferData = new TransferData();
    /** 往前面传输数据的容器 */
    private VData mInputData;
    private MMap map = new MMap();
    private VData mResult = new VData();
    private ExeSQL mExeSQL = new ExeSQL();
    private SSRS mSSRS = new SSRS();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private LCMedBuyMainSchema mLCMedBuyMainSchema = new LCMedBuyMainSchema();
    private LCMedBuyMainSchema tLCMedBuyMainSchema = new LCMedBuyMainSchema();
    private LCMedBuyMainSchema nLCMedBuyMainSchema = new LCMedBuyMainSchema();
    private LCMedBuyMainSet mLCMedBuyMainSet = new LCMedBuyMainSet();
    private LCMedBuyMainSet tLCMedBuyMainSet = new LCMedBuyMainSet();
    private LCMedBuyMainSet nLCMedBuyMainSet = new LCMedBuyMainSet();
    private LCMedBuyMainDB mLCMedBuyMainDB = new LCMedBuyMainDB();
    private LCMedBuyDetailSchema mLCMedBuyDetailSchema = new
            LCMedBuyDetailSchema();
    private LCMedBuyDetailSet mLCMedBuyDetailSet = new LCMedBuyDetailSet();
    private LCMedCardUserInfoSchema mLCMedCardUserInfoSchema = new
            LCMedCardUserInfoSchema();
    private LCMedCardUserInfoSet mLCMedCardUserInfoSet = new
            LCMedCardUserInfoSet();

    private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();
    private LLAccidentSet mLLAccidentSet = new LLAccidentSet();
    private LLAccidentSubSchema mLLAccidentSubSchema = new LLAccidentSubSchema();
    private LLAccidentSubSet mLLAccidentSubSet = new LLAccidentSubSet();
    private LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();
    private LLCaseRelaSet mLLCaseRelaSet = new LLCaseRelaSet();
    private LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();
    private LLReportRelaSet mLLReportRelaSet = new LLReportRelaSet();

    private LLReportSchema mLLReportSchema = new LLReportSchema();
    private LLReportSet mLLReportSet = new LLReportSet();
    private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();
    private LLSubReportSet mLLSubReportSet = new LLSubReportSet();
    private LLReportReasonSchema mLLReportReasonSchema = new
            LLReportReasonSchema();
    private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();

    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
    private LLRegisterSet mLLRegisterSet = new LLRegisterSet();
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();
    private LLCaseSet mLLCaseSet = new LLCaseSet();
    private LLAppClaimReasonSchema mLLAppClaimReasonSchema = new
            LLAppClaimReasonSchema();
    private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();

    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LLClaimSet mLLClaimSet = new LLClaimSet();
    private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
    private LLClaimUWMainSet mLLClaimUWMainSet = new LLClaimUWMainSet();
    private LLClaimPolicySchema mLLClaimPolicySchema = new LLClaimPolicySchema();
    private LLClaimPolicySet mLLClaimPolicySet = new LLClaimPolicySet();
    private LLClaimDetailSchema mLLClaimDetailSchema = new LLClaimDetailSchema();
    private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
    private LLCaseReceiptSchema mLLCaseReceiptSchema = new LLCaseReceiptSchema();
    private LLCaseReceiptSet mLLCaseReceiptSet = new LLCaseReceiptSet();
    private LCInsureAccTraceSchema mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
    private LLBalanceSchema mLLBalanceSchema = new LLBalanceSchema();
    private LLBalanceSet mLLBalanceSet = new LLBalanceSet();
    private LJAGetSchema mLJAGetSchema = new LJAGetSchema();
    private LJAGetSet mLJAGetSet = new LJAGetSet();
    private LJAGetClaimSchema mLJAGetClaimSchema = new LJAGetClaimSchema();
    private LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();

    private String GrpContNo = null;
    private String CardDate = null; //发票日期
    private String CheckFlag = null;
    private String ContNo = null;
    private String StartDate = null;
    private String EndDate = null;
    private double tMoney = 0.00; //累计的理赔金额
    private int tMainFeeNo = 0;
    private String RptNo = null;
    private String AccNo = null;
    private String tGrpPolNo = null;
    private String tPolNo = null;
    private String tContNo = null;
    private String tInsuredNo = null;
    private String tName = null;
    private String tGrpNo = null;
    private String tGrpName = null;
    private String tRiskCode = null;
    private String tManageCom = null;  //保单机构
    private String tCValiDate = null;
    private String tPolState = null;
    private String tDutyCode = null;
    private String tGetDutyCode = null;
    private String tAgentCode = null;
    private String tAgentGroup = null;
    private String tAgentType = null;

    public LLClaimMedBuyBL() {}

    public boolean submitData(VData cInputData, String cOperate) {
        mInputData = (VData) cInputData.clone();
        if (!getInputData()) {
            this.buildError("submitData", "无法获取输入信息");
            return false;
        }
        if (!checkData()) {
            return false;
        }
        if (!dealData(CheckFlag)) {
            this.buildError("submitData", "处理数据时失败");
            return false;
        }

        if (!prepareOutputData()) {
            this.buildError("submitData", "提交数据时失败");
            return false;
        }

        PubSubmit mmPubSubmit = new PubSubmit();
        if (!mmPubSubmit.submitData(mResult, cOperate)) {
            // @@错误处理
            this.buildError("dealData", "数据提交失败");
            return false;
        }

        return true;

    }

    public boolean getInputData() {
        mG = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mLCMedBuyMainSet = ((LCMedBuyMainSet) mInputData.getObjectByObjectName(
                "LCMedBuyMainSet", 0));

        if (mG == null || mTransferData == null) {
            buildError("getInputData", "传过来的数据不全");
            return false;
        }
        GrpContNo = (String) mTransferData.getValueByName("GrpContNo");
        ContNo = (String) mTransferData.getValueByName("ContNo");
        StartDate = (String) mTransferData.getValueByName("StartDate");
        EndDate = (String) mTransferData.getValueByName("EndDate");
        CardDate = (String) mTransferData.getValueByName("CardDate");
        CheckFlag = (String) mTransferData.getValueByName("CheckFlag");

        return true;
    }

    public boolean checkData() {

        return true;
    }

    public boolean dealData(String CheckFlag) {

        String userSql = null;
        String polSql = null;
        String dutySql = null;
        String getdutySql = null;
        String AllQuerySql = null;
        String moneySql = null;
        String clmSql = null;
        String clmflagSql = null;
        String clmSql1 = null;
        String clmSql2 = null;
        String clmflagSql2 = null;
        String clmSql3 = null;
        String clmSql4 = null;

        if (CheckFlag.equals("Yes")) { //按选择生成赔案，取set中记录生成数据

            for (int i = 1; i <= mLCMedBuyMainSet.size(); i++) {
                mLCMedBuyMainSchema = new LCMedBuyMainSchema();
                tLCMedBuyMainSchema = new LCMedBuyMainSchema();
                mLCMedBuyMainSchema = mLCMedBuyMainSet.get(i);
                clmflagSql2 = "update LCMedBuyMain set clmflag='1' where MedCardNo='"+mLCMedBuyMainSchema.getMedCardNo()+"' and BatchNo='"+mLCMedBuyMainSchema.getBatchNo()+"' and MedType1='"+mLCMedBuyMainSchema.getMedType1()+"' ";
                map.put(clmflagSql2,"UPDATE");

                tMoney = tMoney + mLCMedBuyMainSchema.getSumClmPrice(); //累加赔付金额

                if (i > 1) {
                    tLCMedBuyMainSchema = mLCMedBuyMainSet.get(i - 1); // 上一条数据
                    if (mLCMedBuyMainSchema.getMemo().equals(tLCMedBuyMainSchema.getMemo())) {
                            continue;   //如果是同一个人，则跳出循环只累加金额。同一个人只生成一个赔案。
                    }else
                    {
                        RptNo=PubFun1.CreateMaxNo("RPTONLYNO", mG.ManageCom); // 不是同一个人时，为上一个人生成赔案号;
                        tMoney = tMoney - mLCMedBuyMainSchema.getSumClmPrice(); //不是同一个人时，减去本次循环的金额,才为上个人真正的赔付金额
                        clmSql1 = "update LCMedBuyMain set clmno='"+RptNo+"' where MedCardNo='"+tLCMedBuyMainSchema.getMedCardNo()+"' and BatchNo='"+tLCMedBuyMainSchema.getBatchNo()+"' and MedType1='"+tLCMedBuyMainSchema.getMedType1()+"' ";
                        map.put(clmSql1,"UPDATE"); //回写赔案号
                        for (int n = 1; n <= mLCMedBuyMainSet.size(); n++) {
                            nLCMedBuyMainSchema = new LCMedBuyMainSchema();
                            nLCMedBuyMainSchema = mLCMedBuyMainSet.get(n);
                            if ((tLCMedBuyMainSchema.getMemo().equals(nLCMedBuyMainSchema.getMemo())) && ((!tLCMedBuyMainSchema.getBatchNo().equals(nLCMedBuyMainSchema.getBatchNo())) || (!tLCMedBuyMainSchema.getMedType1().equals(nLCMedBuyMainSchema.getMedType1())))) {
                                clmSql2 = "update LCMedBuyMain set clmno='"+RptNo+"' where MedCardNo='"+nLCMedBuyMainSchema.getMedCardNo()+"' and BatchNo='"+nLCMedBuyMainSchema.getBatchNo()+"' and MedType1='"+nLCMedBuyMainSchema.getMedType1()+"' ";
                                map.put(clmSql2,"UPDATE"); //如果是同一个人且不是同一批次和同一类型，则置上相同赔案号。同一个人只生成一个赔案。
                              }
                        }

                        AccNo = PubFun1.CreateMaxNo("ACCNO", 10); //生成事件号流水号
                        AccNo = "8" + AccNo;
                        userSql = "select contno,insuredno,name from LCMedCardUserInfo where medcardno='" +
                                         tLCMedBuyMainSchema.getMedCardNo() + "' ";
                        mSSRS = mExeSQL.execSQL(userSql);
                        if (mSSRS.getMaxRow() > 0) {
                            tContNo = mSSRS.GetText(1, 1);
                            tInsuredNo = mSSRS.GetText(1, 2);
                            tName = mSSRS.GetText(1, 3);
                        } else {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimMedBuyBL";
                            tError.functionName = "perpareMissionProp";
                            tError.errorMessage = "没有医疗卡" +
                                                  mLCMedBuyMainSchema.getMedCardNo() +
                                                  "的信息!";
                            this.mErrors.addOneError(tError);
                            return false;
                        }

                        polSql=" select riskcode,grppolno,polno,managecom,appntno,appntname,cvalidate,polstate,agentcode,agentgroup,agenttype from lcpol where contno='"+tContNo+"' ";
                        mSSRS = mExeSQL.execSQL(polSql);
                        if (mSSRS.getMaxRow() > 0) {
                            tRiskCode = mSSRS.GetText(1, 1);
                            tGrpPolNo = mSSRS.GetText(1, 2);
                            tPolNo = mSSRS.GetText(1, 3);
                            tManageCom = mSSRS.GetText(1, 4);
                            tGrpNo = mSSRS.GetText(1, 5);
                            tGrpName = mSSRS.GetText(1, 6);
                            tCValiDate = mSSRS.GetText(1, 7);
                            tPolState = mSSRS.GetText(1, 8);
                            tAgentCode = mSSRS.GetText(1, 9);
                            tAgentGroup = mSSRS.GetText(1, 10);
                            tAgentType = mSSRS.GetText(1, 11);
                            dutySql="select dutycode from lmriskduty where riskcode='"+tRiskCode+"' and specflag='0'";
                            tDutyCode = mExeSQL.getOneValue(dutySql);
                            getdutySql="select getdutycode from lmdutygetrela where dutycode='"+tDutyCode+"'";
                            tGetDutyCode = mExeSQL.getOneValue(getdutySql);

                        } else {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimMedBuyBL";
                            tError.functionName = "perpareMissionProp";
                            tError.errorMessage = "没有保单号" + tContNo + "的保单信息!";
                            this.mErrors.addOneError(tError);
                            return false;
                        }
                         //生成理赔数据
                        if (!DealClaim())
                        {
                            //errors
                            return false;
                        }
                        tMoney=mLCMedBuyMainSchema.getSumClmPrice(); //生成新赔案后清除上个赔案的赔付金额
                        tMainFeeNo = 0; //生成新赔案后清零MainFeeNo
                    }

                }

            }
            // 为最后一个人生成赔案号;
            RptNo=PubFun1.CreateMaxNo("RPTONLYNO", mG.ManageCom);
            clmSql3 = "update LCMedBuyMain set clmno='"+RptNo+"' where MedCardNo='"+mLCMedBuyMainSchema.getMedCardNo()+"' and BatchNo='"+mLCMedBuyMainSchema.getBatchNo()+"' and MedType1='"+mLCMedBuyMainSchema.getMedType1()+"' ";
            map.put(clmSql3,"UPDATE"); //回写赔案号
            for (int n = 1; n <= mLCMedBuyMainSet.size(); n++) {
                nLCMedBuyMainSchema = new LCMedBuyMainSchema();
                nLCMedBuyMainSchema = mLCMedBuyMainSet.get(n);
                if ((mLCMedBuyMainSchema.getMemo().equals(nLCMedBuyMainSchema.getMemo())) && ((!mLCMedBuyMainSchema.getBatchNo().equals(nLCMedBuyMainSchema.getBatchNo())) || (!mLCMedBuyMainSchema.getMedType1().equals(nLCMedBuyMainSchema.getMedType1())))) {
                    clmSql4 = "update LCMedBuyMain set clmno='"+RptNo+"' where MedCardNo='"+nLCMedBuyMainSchema.getMedCardNo()+"' and BatchNo='"+nLCMedBuyMainSchema.getBatchNo()+"' and MedType1='"+nLCMedBuyMainSchema.getMedType1()+"' ";
                     map.put(clmSql4,"UPDATE"); //如果是同一个人且不是同一批次和同一类型，则置上相同赔案号。同一个人只生成一个赔案。
                  }
            }
            AccNo = PubFun1.CreateMaxNo("ACCNO", 10); //生成事件号流水号
            AccNo = "8" + AccNo;
            userSql = "select contno,insuredno,name from LCMedCardUserInfo where medcardno='" +
                             mLCMedBuyMainSchema.getMedCardNo() + "' "; //最后一个人的卡号
            mSSRS = mExeSQL.execSQL(userSql);
            if (mSSRS.getMaxRow() > 0) {
                tContNo = mSSRS.GetText(1, 1);
                tInsuredNo = mSSRS.GetText(1, 2);
                tName = mSSRS.GetText(1, 3);
            } else {
                //@@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimMedBuyBL";
                tError.functionName = "perpareMissionProp";
                tError.errorMessage = "没有医疗卡" +
                                      mLCMedBuyMainSchema.getMedCardNo() +
                                      "的信息!";
                this.mErrors.addOneError(tError);
                return false;
            }

            polSql=" select riskcode,grppolno,polno,managecom,appntno,appntname,cvalidate,polstate,agentcode,agentgroup,agenttype from lcpol where contno='"+tContNo+"' ";
            mSSRS = mExeSQL.execSQL(polSql);
            if (mSSRS.getMaxRow() > 0) {
                tRiskCode = mSSRS.GetText(1, 1);
                tGrpPolNo = mSSRS.GetText(1, 2);
                tPolNo = mSSRS.GetText(1, 3);
                tManageCom = mSSRS.GetText(1, 4);
                tGrpNo = mSSRS.GetText(1, 5);
                tGrpName = mSSRS.GetText(1, 6);
                tCValiDate = mSSRS.GetText(1, 7);
                tPolState = mSSRS.GetText(1, 8);
                tAgentCode = mSSRS.GetText(1, 9);
                tAgentGroup = mSSRS.GetText(1, 10);
                tAgentType = mSSRS.GetText(1, 11);
                dutySql="select dutycode from lmriskduty where riskcode='"+tRiskCode+"' and specflag='0'";
                tDutyCode = mExeSQL.getOneValue(dutySql);
                getdutySql="select getdutycode from lmdutygetrela where dutycode='"+tDutyCode+"'";
                tGetDutyCode = mExeSQL.getOneValue(getdutySql);

            } else {
                //@@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimMedBuyBL";
                tError.functionName = "perpareMissionProp";
                tError.errorMessage = "没有保单号" + tContNo + "的保单信息!";
                this.mErrors.addOneError(tError);
                return false;
            }
            //生成理赔数据
           if (!DealClaim())
           {
               //errors
               return false;
            }
            tMoney=0.00; //生成新赔案后清除上个赔案的赔付金额
            tMainFeeNo = 0; //生成新赔案后清零MainFeeNo
//分支
      } else { //按查询条件生成赔案
            AllQuerySql = "select distinct contno from lcmedcarduserinfo a,lcmedbuymain b where 1=1 and a.MedCardNo = b.MedCardNo and ClmFlag='0' "
                         +" and b.makedate >= '"+StartDate+"' and b.makedate <= '"+EndDate+"' and  grpcontno = '"+GrpContNo+"' and managecom='"+mG.ManageCom+"' ";
            if (ContNo!=null && !ContNo.equals(""))
            {
               AllQuerySql=AllQuerySql+" and contno='"+ContNo+"' ";
            }
            mSSRS = mExeSQL.execSQL(AllQuerySql);
            for (int j=1;j<=mSSRS.getMaxCol();j++)
            {
                RptNo=PubFun1.CreateMaxNo("RPTONLYNO", mG.ManageCom); // 为最后一个人生成赔案号;
                AccNo = PubFun1.CreateMaxNo("ACCNO", 10); //生成事件号流水号
                AccNo = "8" + AccNo;

                tContNo = mSSRS.GetText(j,1);
                polSql=" select riskcode,grppolno,polno,managecom,appntno,appntname,cvalidate,polstate,agentcode,agentgroup,agenttype,insuredno from lcpol where contno='"+tContNo+"' ";
                SSRS tSSRS = mExeSQL.execSQL(polSql);
                if (tSSRS.getMaxRow() > 0) {
                    tRiskCode = tSSRS.GetText(1, 1);
                    tGrpPolNo = tSSRS.GetText(1, 2);
                    tPolNo = tSSRS.GetText(1, 3);
                    tManageCom = tSSRS.GetText(1, 4);
                    tGrpNo = tSSRS.GetText(1, 5);
                    tGrpName = tSSRS.GetText(1, 6);
                    tCValiDate = tSSRS.GetText(1, 7);
                    tPolState = tSSRS.GetText(1, 8);
                    tAgentCode = tSSRS.GetText(1, 9);
                    tAgentGroup = tSSRS.GetText(1, 10);
                    tAgentType = tSSRS.GetText(1, 11);
                    tInsuredNo = tSSRS.GetText(1, 12);
                    dutySql="select dutycode from lmriskduty where riskcode='"+tRiskCode+"' and specflag='0'";
                    tDutyCode = mExeSQL.getOneValue(dutySql);
                    getdutySql="select getdutycode from lmdutygetrela where dutycode='"+tDutyCode+"'";
                    tGetDutyCode = mExeSQL.getOneValue(getdutySql);
                    moneySql = "select sum(SumClmPrice) from lcmedcarduserinfo a,lcmedbuymain b where 1=1 and a.MedCardNo = b.MedCardNo and ClmFlag='0' "
                                             +" and b.makedate >= '"+StartDate+"' and b.makedate <= '"+EndDate+"' and  grpcontno = '"+GrpContNo+"' and managecom='"+mG.ManageCom+"' "
                                             +" and contno='"+tContNo+"' ";
                   tMoney = Double.parseDouble(mExeSQL.getOneValue(moneySql));
                } else {
                    //@@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LLClaimMedBuyBL";
                    tError.functionName = "perpareMissionProp";
                    tError.errorMessage = "没有保单号" + tContNo + "的保单信息!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
                //生成理赔数据
               if (!DealClaim())
               {
                   //errors
                   return false;
               }
                tMoney=0.00; //生成新赔案后清除上个赔案的赔付金额
                tMainFeeNo = 0; //生成新赔案后清零MainFeeNo

                clmflagSql = "select * from lcmedcarduserinfo a,lcmedbuymain b where 1=1 and a.MedCardNo = b.MedCardNo and ClmFlag='0' "
                         +" and b.makedate >= '"+StartDate+"' and b.makedate <= '"+EndDate+"' and  grpcontno = '"+GrpContNo+"' and managecom='"+mG.ManageCom+"' "
                         +" and contno='"+tContNo+"' ";
                mLCMedBuyMainSet=mLCMedBuyMainDB.executeQuery(clmflagSql);
                for (int k=1;k<=mLCMedBuyMainSet.size();k++)
                {
                    mLCMedBuyMainSchema = new LCMedBuyMainSchema();
                    mLCMedBuyMainSchema=mLCMedBuyMainSet.get(k);
                    clmSql = "update lcmedbuymain set clmflag='1',clmno='"+RptNo+"' where MedCardNo = '"+mLCMedBuyMainSchema.getMedCardNo()+"' "
                             +" and BatchNo='"+mLCMedBuyMainSchema.getBatchNo()+"' and MedType1='"+mLCMedBuyMainSchema.getMedType1()+"' ";

                     map.put(clmSql ,"UPDATE");
                }


            }

        }

//提交理赔数据
        map.put(mLLReportSet,"INSERT");
        map.put(mLLSubReportSet,"INSERT");
        map.put(mLLReportReasonSet,"INSERT");
        map.put(mLLAccidentSet,"INSERT");
        map.put(mLLAccidentSubSchema,"INSERT");
        map.put(mLLReportRelaSet,"INSERT");
        map.put(mLLCaseRelaSet,"INSERT");
        map.put(mLLRegisterSet,"INSERT");
        map.put(mLLCaseSet,"INSERT");
        map.put(mLLAppClaimReasonSet,"INSERT");
        map.put(mLLClaimSet,"INSERT");
        map.put(mLLClaimUWMainSet,"INSERT");
        map.put(mLLClaimPolicySet,"INSERT");
        map.put(mLLClaimDetailSet,"INSERT");
        map.put(mLLCaseReceiptSet,"INSERT");
        map.put(tLCInsureAccTraceSet,"INSERT");
        map.put(mLLBalanceSet,"INSERT");
        map.put(mLJAGetSet,"INSERT");
        map.put(mLJAGetClaimSet,"INSERT");

        return true;
    }

    public boolean DealClaim()
    {
        /**---------------------------------------------------------------------
         * No1 理赔相关表
         *----------------------------------------------------------------------*/
        //报案相关表
        if (!setReport())
        {
            //errors
            return false;
        }
        //立案相关表
        if (!setRegister())
        {
            //errors
            return false;
        }
        //险种层面费用信息llclaim
        if (!setLLClaim())
        {
            //errors
            return false;
        }
        //赔案层面llclaimpolicy
        if (!setLLClaimPolicy())
        {
            //errors
            return false;
        }
        //理赔类型层面llclaimdetail
        if (!setLLClaimDetail())
        {
            //errors
            return false;
        }
        //理赔结算表llcasereceipt
        if (!setLLCaseReceipt())
        {
            //errors
            return false;
        }
        //理赔结算表lcinsureacctrace
        if (!setLCInsureAccTrace())
        {
            //errors
            return false;
        }
        //理赔结算表llbalance
        if (!setLLBalance())
        {
            //errors
            return false;
        }
        //理赔结算表ljaget
        if (!setLJAGet())
        {
            //errors
            return false;
        }

        return true;
    }
    private boolean setReport() {
            //报案表
            mLLReportSchema = new LLReportSchema();
            mLLReportSchema.setRptNo(RptNo);
            mLLReportSchema.setRgtClass("2"); //个险1，团险2
            mLLReportSchema.setRgtObj("20"); //使用个人帐户
            mLLReportSchema.setRptorName(mG.Operator); //报案人姓名
            mLLReportSchema.setAccidentDate(CardDate);
            mLLReportSchema.setAccidentReason("2"); //1意外，2疾病
            mLLReportSchema.setRptDate(CurrentDate); //报案受理日期
            mLLReportSchema.setAvaiFlag("10"); //案件有效标志10表示保存未确认
            mLLReportSchema.setAvaliReason("02"); //帐户类
            mLLReportSchema.setOperator(mG.Operator);
            mLLReportSchema.setMngCom(mG.ManageCom);
            mLLReportSchema.setMakeDate(CurrentDate);
            mLLReportSchema.setMakeTime(CurrentTime);
            mLLReportSchema.setModifyDate(CurrentDate);
            mLLReportSchema.setModifyTime(CurrentTime);
            mLLReportSchema.setGrpContNo(GrpContNo);
            mLLReportSchema.setAppntNo(tGrpNo);
            mLLReportSchema.setGrpName(tGrpName);
            mLLReportSchema.setRiskCode(tRiskCode);

            mLLReportSet.add(mLLReportSchema);
            //分案表
            mLLSubReportSchema = new LLSubReportSchema();
            mLLSubReportSchema.setSubRptNo(RptNo);
            mLLSubReportSchema.setCustomerNo(tInsuredNo);
            mLLSubReportSchema.setCustomerName(tName);
            mLLSubReportSchema.setAccDate(CardDate);
            mLLSubReportSchema.setOperator(mG.Operator);
            mLLSubReportSchema.setMngCom(mG.ManageCom);
            mLLSubReportSchema.setMakeDate(CurrentDate);
            mLLSubReportSchema.setMakeTime(CurrentTime);
            mLLSubReportSchema.setModifyDate(CurrentDate);
            mLLSubReportSchema.setModifyTime(CurrentTime);

            mLLSubReportSet.add(mLLSubReportSchema);
            //理赔类型表(多条添加)
            mLLReportReasonSchema = new LLReportReasonSchema();
            mLLReportReasonSchema.setRpNo(RptNo); //分案号=赔案号
            mLLReportReasonSchema.setReasonCode("200");
            mLLReportReasonSchema.setCustomerNo(tInsuredNo);
            mLLReportReasonSchema.setOperator(mG.Operator);
            mLLReportReasonSchema.setMngCom(mG.ManageCom);
            mLLReportReasonSchema.setMakeDate(CurrentDate);
            mLLReportReasonSchema.setMakeTime(CurrentTime);
            mLLReportReasonSchema.setModifyDate(CurrentDate);
            mLLReportReasonSchema.setModifyTime(CurrentTime);

            mLLReportReasonSet.add(mLLReportReasonSchema);

            //事件表
            mLLAccidentSchema = new LLAccidentSchema();
            mLLAccidentSchema.setAccNo(AccNo); //事件号
            mLLAccidentSchema.setAccDate(CardDate);
            mLLAccidentSchema.setOperator(mG.Operator);
            mLLAccidentSchema.setMngCom(mG.ManageCom);
            mLLAccidentSchema.setMakeDate(CurrentDate);
            mLLAccidentSchema.setMakeTime(CurrentTime);
            mLLAccidentSchema.setModifyDate(CurrentDate);
            mLLAccidentSchema.setModifyTime(CurrentTime);

            mLLAccidentSet.add(mLLAccidentSchema);
            //分事件表
            mLLAccidentSubSchema = new LLAccidentSubSchema();
            mLLAccidentSubSchema.setAccNo(AccNo); //事件号
            mLLAccidentSubSchema.setCustomerNo(mLLSubReportSchema.getCustomerNo()); //出险人编码

            mLLAccidentSubSet.add(mLLAccidentSubSchema);
            //报案分案关联表
            mLLReportRelaSchema = new LLReportRelaSchema();
            mLLReportRelaSchema.setRptNo(RptNo); //分案号=赔案号
            mLLReportRelaSchema.setSubRptNo(RptNo); //分案号=赔案号

            mLLReportRelaSet.add(mLLReportRelaSchema);
            //分案事件关联表
            mLLCaseRelaSchema = new LLCaseRelaSchema();
            mLLCaseRelaSchema.setCaseRelaNo(AccNo); //事件号
            mLLCaseRelaSchema.setCaseNo(RptNo); //分案号=赔案号
            mLLCaseRelaSchema.setSubRptNo(RptNo); //分案号=赔案号

            mLLCaseRelaSet.add(mLLCaseRelaSchema);


         return true;
    }
    private boolean setRegister() {
            //立案表
            mLLRegisterSchema = new LLRegisterSchema();
            mLLRegisterSchema.setRgtNo(RptNo); //赔案号
            mLLRegisterSchema.setRgtState("02"); //*案件类型*，账户案件
            mLLRegisterSchema.setRgtObj("20"); //
            mLLRegisterSchema.setRgtObjNo("1"); //其他号码(?)
            mLLRegisterSchema.setRgtClass("2"); //申请类型,前台已输入
            mLLRegisterSchema.setCustomerNo("1"); //客户号(?)
            mLLRegisterSchema.setGrpName(tGrpName);
            mLLRegisterSchema.setAccidentReason("2"); //1意外，2疾病
            mLLRegisterSchema.setAccidentDate(CardDate);
            mLLRegisterSchema.setEndCaseFlag("1");
            mLLRegisterSchema.setEndCaseDate(CurrentDate);
            mLLRegisterSchema.setRgtConclusion("01"); //立案结论,立案通过
            mLLRegisterSchema.setClmState("60");
            mLLRegisterSchema.setRgtDate(CurrentDate);
            mLLRegisterSchema.setOperator(mG.Operator);
            mLLRegisterSchema.setMngCom(mG.ManageCom.substring(0,2)); //机构取2位
            mLLRegisterSchema.setMakeDate(CurrentDate);
            mLLRegisterSchema.setMakeTime(CurrentTime);
            mLLRegisterSchema.setModifyDate(CurrentDate);
            mLLRegisterSchema.setModifyTime(CurrentTime);
            mLLRegisterSchema.setGrpContNo(GrpContNo);
            mLLRegisterSchema.setAppntNo(tGrpNo);
            mLLRegisterSchema.setRiskCode(tRiskCode);

            mLLRegisterSet.add(mLLRegisterSchema);
            //立案分案表
            mLLCaseSchema = new LLCaseSchema();
            mLLCaseSchema.setCaseNo(RptNo); //赔案号
            mLLCaseSchema.setRgtNo(RptNo); //赔案号
            mLLCaseSchema.setRgtType("02"); //案件类型,账户案件
            mLLCaseSchema.setRgtState("60"); //案件状态
            mLLCaseSchema.setCustomerNo(tInsuredNo);
            mLLCaseSchema.setCustomerName(tName);
            mLLCaseSchema.setRgtDate(CurrentDate);
            mLLCaseSchema.setEndCaseFlag("1"); //重要信息修改标志
            mLLCaseSchema.setEndCaseDate(CurrentDate);
            mLLCaseSchema.setAffixConclusion("0"); //单证齐全标志
            mLLCaseSchema.setCondoleFlag("0"); //非导入标志
            mLLCaseSchema.setEditFlag("0"); //重要信息修改标志
            mLLCaseSchema.setAccDate(CardDate);
            mLLCaseSchema.setOperator(mG.Operator);
            mLLCaseSchema.setMngCom(mG.ManageCom.substring(0,2));
            mLLCaseSchema.setMakeDate(CurrentDate);
            mLLCaseSchema.setMakeTime(CurrentTime);
            mLLCaseSchema.setModifyDate(CurrentDate);
            mLLCaseSchema.setModifyTime(CurrentTime);

            mLLCaseSet.add(mLLCaseSchema);
            //理赔类型表(多条添加)
            mLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
            mLLAppClaimReasonSchema.setCaseNo(RptNo);
            mLLAppClaimReasonSchema.setRgtNo(RptNo);
            mLLAppClaimReasonSchema.setCustomerNo(tInsuredNo);
            mLLAppClaimReasonSchema.setReasonCode("200");
            mLLAppClaimReasonSchema.setOperator(mG.Operator);
            mLLAppClaimReasonSchema.setMngCom(mG.ManageCom.substring(0,2));
            mLLAppClaimReasonSchema.setMakeDate(CurrentDate);
            mLLAppClaimReasonSchema.setMakeTime(CurrentTime);
            mLLAppClaimReasonSchema.setModifyDate(CurrentDate);
            mLLAppClaimReasonSchema.setModifyTime(CurrentTime);

            mLLAppClaimReasonSet.add(mLLAppClaimReasonSchema);

         return true;
    }
    private boolean setLLClaim() {
        mLLClaimSchema = new LLClaimSchema();
        mLLClaimSchema.setClmNo(RptNo);
        mLLClaimSchema.setRgtNo(RptNo);
        mLLClaimSchema.setCaseNo(RptNo);
        mLLClaimSchema.setClmState("60");
        mLLClaimSchema.setStandPay(tMoney);
        mLLClaimSchema.setRealPay(tMoney);
        mLLClaimSchema.setGiveType("0");
        mLLClaimSchema.setClmUWer(mG.Operator);
        mLLClaimSchema.setEndCaseDate(CurrentDate);
        mLLClaimSchema.setMngCom(mG.ManageCom.substring(0,2));
        mLLClaimSchema.setOperator(mG.Operator);
        mLLClaimSchema.setMakeDate(CurrentDate);
        mLLClaimSchema.setMakeTime(CurrentTime);
        mLLClaimSchema.setModifyDate(CurrentDate);
        mLLClaimSchema.setModifyTime(CurrentTime);

        mLLClaimSet.add(mLLClaimSchema);

        mLLClaimUWMainSchema = new LLClaimUWMainSchema();
        mLLClaimUWMainSchema.setClmNo(RptNo);
        mLLClaimUWMainSchema.setRgtNo(RptNo);
        mLLClaimUWMainSchema.setCaseNo(RptNo);
        mLLClaimUWMainSchema.setClmUWer(mG.Operator);
        mLLClaimUWMainSchema.setAppClmUWer(mG.Operator);
        mLLClaimUWMainSchema.setOperator(mG.Operator);
        mLLClaimUWMainSchema.setMngCom(mG.ManageCom.substring(0,2));
        mLLClaimUWMainSchema.setMakeDate(CurrentDate);
        mLLClaimUWMainSchema.setMakeTime(CurrentTime);
        mLLClaimUWMainSchema.setModifyDate(CurrentDate);
        mLLClaimUWMainSchema.setModifyTime(CurrentTime);
        mLLClaimUWMainSchema.setAuditConclusion("0");
        mLLClaimUWMainSchema.setAuditPer(mG.Operator);
        mLLClaimUWMainSchema.setAuditDate(CurrentDate);
        mLLClaimUWMainSchema.setExamConclusion("0");

        mLLClaimUWMainSet.add(mLLClaimUWMainSchema);

        return true;
    }
    private boolean setLLClaimPolicy() {
        mLLClaimPolicySchema = new LLClaimPolicySchema();
        mLLClaimPolicySchema.setClmNo(RptNo);
        mLLClaimPolicySchema.setCaseNo(RptNo);
        mLLClaimPolicySchema.setRgtNo(RptNo);
        mLLClaimPolicySchema.setCaseRelaNo(AccNo);
        mLLClaimPolicySchema.setGrpContNo(GrpContNo);
        mLLClaimPolicySchema.setGrpPolNo(tGrpPolNo);
        mLLClaimPolicySchema.setContNo(tContNo);
        mLLClaimPolicySchema.setPolNo(tPolNo);
        mLLClaimPolicySchema.setGetDutyKind("200");
        mLLClaimPolicySchema.setKindCode("H");
        mLLClaimPolicySchema.setRiskCode(tRiskCode);
        mLLClaimPolicySchema.setPolMngCom(tManageCom);
        mLLClaimPolicySchema.setSaleChnl("2");
        mLLClaimPolicySchema.setAgentCode(tAgentCode);
        mLLClaimPolicySchema.setAgentGroup(tAgentGroup);
        mLLClaimPolicySchema.setInsuredNo(tInsuredNo);
        mLLClaimPolicySchema.setInsuredName(tName);
        mLLClaimPolicySchema.setAppntNo(tGrpNo);
        mLLClaimPolicySchema.setAppntName(tGrpName);
        mLLClaimPolicySchema.setCValiDate(tCValiDate);
        mLLClaimPolicySchema.setPolState(tPolState);
        mLLClaimPolicySchema.setStandPay(tMoney);
        mLLClaimPolicySchema.setRealPay(tMoney);
        mLLClaimPolicySchema.setGiveType("0");
        mLLClaimPolicySchema.setEndCaseDate(CurrentDate);
        mLLClaimPolicySchema.setMngCom(mG.ManageCom.substring(0,2));
        mLLClaimPolicySchema.setOperator(mG.Operator);
        mLLClaimPolicySchema.setMakeDate(CurrentDate);
        mLLClaimPolicySchema.setMakeTime(CurrentTime);
        mLLClaimPolicySchema.setModifyDate(CurrentDate);
        mLLClaimPolicySchema.setModifyTime(CurrentTime);

        mLLClaimPolicySet.add(mLLClaimPolicySchema);

       return true;
    }
    private boolean setLLClaimDetail() {
        mLLClaimDetailSchema = new LLClaimDetailSchema();
        mLLClaimDetailSchema.setClmNo(mLLClaimPolicySchema.getClmNo());
        mLLClaimDetailSchema.setCaseNo(mLLClaimPolicySchema.getCaseNo());
        mLLClaimDetailSchema.setRgtNo(mLLClaimPolicySchema.getRgtNo());
        mLLClaimDetailSchema.setPolNo(mLLClaimPolicySchema.getPolNo());
        mLLClaimDetailSchema.setPolSort("C");
        mLLClaimDetailSchema.setPolType("0");
        mLLClaimDetailSchema.setDutyCode(tDutyCode);
        mLLClaimDetailSchema.setGetDutyKind(mLLClaimPolicySchema.getGetDutyKind());
        mLLClaimDetailSchema.setGetDutyCode(tGetDutyCode);
        mLLClaimDetailSchema.setCaseRelaNo(mLLClaimPolicySchema.getCaseRelaNo());
        mLLClaimDetailSchema.setGrpContNo(mLLClaimPolicySchema.getGrpContNo());
        mLLClaimDetailSchema.setGrpPolNo(mLLClaimPolicySchema.getGrpPolNo());
        mLLClaimDetailSchema.setContNo(mLLClaimPolicySchema.getContNo());
        mLLClaimDetailSchema.setPolNo(mLLClaimPolicySchema.getPolNo());
        mLLClaimDetailSchema.setKindCode(mLLClaimPolicySchema.getKindCode());
        mLLClaimDetailSchema.setRiskCode(mLLClaimPolicySchema.getRiskCode());
        mLLClaimDetailSchema.setPolMngCom(mLLClaimPolicySchema.getPolMngCom());
        mLLClaimDetailSchema.setSaleChnl(mLLClaimPolicySchema.getSaleChnl());
        mLLClaimDetailSchema.setAgentCode(mLLClaimPolicySchema.getAgentCode());
        mLLClaimDetailSchema.setAgentGroup(mLLClaimPolicySchema.getAgentGroup());
        mLLClaimDetailSchema.setStandPay(mLLClaimPolicySchema.getStandPay());
        mLLClaimDetailSchema.setRealPay(mLLClaimPolicySchema.getRealPay());
        mLLClaimDetailSchema.setGiveType(mLLClaimPolicySchema.getGiveType());
        mLLClaimDetailSchema.setMngCom(mLLClaimPolicySchema.getMngCom());
        mLLClaimDetailSchema.setOperator(mLLClaimPolicySchema.getOperator());
        mLLClaimDetailSchema.setMakeDate(CurrentDate);
        mLLClaimDetailSchema.setMakeTime(CurrentTime);
        mLLClaimDetailSchema.setModifyDate(CurrentDate);
        mLLClaimDetailSchema.setModifyTime(CurrentTime);
        mLLClaimDetailSchema.setCustomerNo(mLLClaimPolicySchema.getInsuredNo());
        mLLClaimDetailSchema.setCValiDate(mLLClaimPolicySchema.getCValiDate());
        mLLClaimDetailSchema.setNBPolNo(mLLClaimPolicySchema.getPolNo());

        mLLClaimDetailSet.add(mLLClaimDetailSchema);

        return true;
    }
    private boolean setLLCaseReceipt() {
        mLLCaseReceiptSchema = new LLCaseReceiptSchema();
        mLLCaseReceiptSchema.setClmNo(mLLClaimPolicySchema.getClmNo());
        mLLCaseReceiptSchema.setCaseNo(mLLClaimPolicySchema.getCaseNo());
        mLLCaseReceiptSchema.setRgtNo(mLLClaimPolicySchema.getRgtNo());
        mLLCaseReceiptSchema.setMainFeeNo(Integer.toString(tMainFeeNo+1)); //生成MainFeeNo
        String tFeeDetailNo = PubFun1.CreateMaxNo("FeeDetailNo", 10);
        mLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo);
        mLLCaseReceiptSchema.setFeeItemType("A");
        mLLCaseReceiptSchema.setFeeItemCode("01");
        mLLCaseReceiptSchema.setFeeItemName("西药费");
        mLLCaseReceiptSchema.setFee(mLLClaimPolicySchema.getRealPay());
        mLLCaseReceiptSchema.setAdjSum(mLLClaimPolicySchema.getRealPay());
        mLLCaseReceiptSchema.setStartDate(CardDate);
        mLLCaseReceiptSchema.setMngCom(mLLClaimPolicySchema.getMngCom());
        mLLCaseReceiptSchema.setOperator(mLLClaimPolicySchema.getOperator());
        mLLCaseReceiptSchema.setMakeDate(CurrentDate);
        mLLCaseReceiptSchema.setMakeTime(CurrentTime);
        mLLCaseReceiptSchema.setModifyDate(CurrentDate);
        mLLCaseReceiptSchema.setModifyTime(CurrentTime);
        mLLCaseReceiptSchema.setCustomerNo(mLLClaimPolicySchema.getInsuredNo());

        mLLCaseReceiptSet.add(mLLCaseReceiptSchema);

         return true;
    }
    private boolean setLCInsureAccTrace() {
        mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
        String traceSql="select * from lcinsureacctrace where polno='"+tPolNo+"' and moneytype='BF' ";
        mLCInsureAccTraceSet = mLCInsureAccTraceDB.executeQuery(traceSql);
        if (mLCInsureAccTraceSet.size()>0)
        {
            String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", mG.ManageCom);  //生成98开头+机构的16位流水号
            mLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(1); //取出保单初始帐户信息
            mLCInsureAccTraceSchema.setSerialNo(tSerialNo);
            mLCInsureAccTraceSchema.setOtherNo(RptNo);
            mLCInsureAccTraceSchema.setOtherType("5");
            mLCInsureAccTraceSchema.setMoneyType("PG");
            mLCInsureAccTraceSchema.setMoney(-tMoney);
            mLCInsureAccTraceSchema.setPayDate(CardDate);
            mLCInsureAccTraceSchema.setOperator(mG.Operator);
            mLCInsureAccTraceSchema.setMakeDate(CurrentDate);
            mLCInsureAccTraceSchema.setMakeTime(CurrentTime);
            mLCInsureAccTraceSchema.setModifyDate(CurrentDate);
            mLCInsureAccTraceSchema.setModifyTime(CurrentTime);
            mLCInsureAccTraceSchema.setPayNo(RptNo);

            tLCInsureAccTraceSet.add(mLCInsureAccTraceSchema);
            return true;
        }else
        {
        //@@错误处理
              CError tError = new CError();
              tError.moduleName = "LLClaimMedBuyBL";
              tError.functionName = "perpareMissionProp";
              tError.errorMessage = "没有保单号" + tContNo + "的帐户信息!";
              this.mErrors.addOneError(tError);
              return false;
        }

    }
    private boolean setLLBalance() {
        mLLBalanceSchema = new LLBalanceSchema();
        mLLBalanceSchema.setClmNo(mLLClaimPolicySchema.getClmNo());
        mLLBalanceSchema.setFeeOperationType("A");
        mLLBalanceSchema.setSubFeeOperationType(mLLClaimDetailSchema.getGetDutyKind());
        mLLBalanceSchema.setFeeFinaType("PK");
        mLLBalanceSchema.setBatNo("0");
        mLLBalanceSchema.setOtherNo("0");
        mLLBalanceSchema.setOtherNoType("0");
        mLLBalanceSchema.setSaleChnl("2");
        mLLBalanceSchema.setGrpContNo(mLLClaimPolicySchema.getGrpContNo());
        mLLBalanceSchema.setGrpPolNo(mLLClaimPolicySchema.getGrpPolNo());
        mLLBalanceSchema.setContNo(mLLClaimPolicySchema.getContNo());
        mLLBalanceSchema.setPolNo(mLLClaimPolicySchema.getPolNo());
        mLLBalanceSchema.setDutyCode(mLLClaimDetailSchema.getDutyCode());
        mLLBalanceSchema.setGetDutyKind(mLLClaimDetailSchema.getGetDutyKind());
        mLLBalanceSchema.setGetDutyCode(mLLClaimDetailSchema.getGetDutyCode());
        mLLBalanceSchema.setKindCode(mLLClaimPolicySchema.getKindCode());
        mLLBalanceSchema.setRiskCode(mLLClaimPolicySchema.getRiskCode());
        mLLBalanceSchema.setAgentCode(mLLClaimPolicySchema.getAgentCode());
        mLLBalanceSchema.setAgentGroup(mLLClaimPolicySchema.getAgentGroup());
        mLLBalanceSchema.setGetDate(CardDate);
        mLLBalanceSchema.setPay(tMoney);
        mLLBalanceSchema.setManageCom(mG.ManageCom);
        mLLBalanceSchema.setAgentType(tAgentType);
        mLLBalanceSchema.setOperator(mG.Operator);
        mLLBalanceSchema.setMakeDate(CurrentDate);
        mLLBalanceSchema.setMakeTime(CurrentTime);
        mLLBalanceSchema.setModifyDate(CurrentDate);
        mLLBalanceSchema.setModifyTime(CurrentTime);

        mLLBalanceSet.add(mLLBalanceSchema);

        return true;
    }
    private boolean setLJAGet() {
        String tActuGetNo = PubFun1.CreateMaxNo("GETNO", mG.ManageCom); //产生实付号码 ActuGetNo;
        mLJAGetSchema = new LJAGetSchema();
        mLJAGetSchema.setActuGetNo(tActuGetNo);
        mLJAGetSchema.setOtherNo(mLLBalanceSchema.getClmNo());
        mLJAGetSchema.setOtherNoType("5");
        mLJAGetSchema.setSendBankCount("0");
        mLJAGetSchema.setAgentCode(mLLBalanceSchema.getAgentCode());
        mLJAGetSchema.setAgentGroup(mLLBalanceSchema.getAgentGroup());
        mLJAGetSchema.setAgentType(mLLBalanceSchema.getAgentType());
        mLJAGetSchema.setAccName(mLLClaimPolicySchema.getAppntName());
        mLJAGetSchema.setSumGetMoney(mLLBalanceSchema.getPay());
        mLJAGetSchema.setShouldDate(CurrentDate);
        String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", mG.ManageCom); //产生即付通知书号 GetNoticeNo
        mLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
        mLJAGetSchema.setManageCom(mG.ManageCom);
        mLJAGetSchema.setPolicyCom(mLLClaimPolicySchema.getPolMngCom());
        mLJAGetSchema.setOperator(mG.Operator);
        mLJAGetSchema.setMakeDate(CurrentDate);
        mLJAGetSchema.setMakeTime(CurrentTime);
        mLJAGetSchema.setModifyDate(CurrentDate);
        mLJAGetSchema.setModifyTime(CurrentTime);
        mLJAGetSchema.setOperState("0");

        mLJAGetSet.add(mLJAGetSchema);

        mLJAGetClaimSchema = new LJAGetClaimSchema();
        mLJAGetClaimSchema.setActuGetNo(tActuGetNo);
        mLJAGetClaimSchema.setFeeOperationType(mLLBalanceSchema.getFeeOperationType());
        mLJAGetClaimSchema.setSubFeeOperationType(mLLBalanceSchema.getSubFeeOperationType());
        mLJAGetClaimSchema.setFeeFinaType(mLLBalanceSchema.getFeeFinaType());
        mLJAGetClaimSchema.setOtherNo(mLLBalanceSchema.getClmNo());
        mLJAGetClaimSchema.setOtherNoType(mLJAGetSchema.getOtherNoType());
        mLJAGetClaimSchema.setDutyCode(mLLBalanceSchema.getDutyCode());
        mLJAGetClaimSchema.setGetDutyKind(mLLBalanceSchema.getGetDutyKind());
        mLJAGetClaimSchema.setGetDutyCode(mLLBalanceSchema.getGetDutyCode());
        mLJAGetClaimSchema.setGrpContNo(mLLBalanceSchema.getGrpContNo());
        mLJAGetClaimSchema.setGrpPolNo(mLLBalanceSchema.getGrpPolNo());
        mLJAGetClaimSchema.setContNo(mLLBalanceSchema.getContNo());
        mLJAGetClaimSchema.setPolNo(mLLBalanceSchema.getPolNo());
        mLJAGetClaimSchema.setKindCode(mLLBalanceSchema.getKindCode());
        mLJAGetClaimSchema.setRiskCode(mLLBalanceSchema.getRiskCode());
        mLJAGetClaimSchema.setAgentCode(mLLBalanceSchema.getAgentCode());
        mLJAGetClaimSchema.setAgentGroup(mLLBalanceSchema.getAgentGroup());
        mLJAGetClaimSchema.setPay(mLLBalanceSchema.getPay());
        mLJAGetClaimSchema.setManageCom(mG.ManageCom);
        mLJAGetClaimSchema.setAgentType(mLLBalanceSchema.getAgentType());
        mLJAGetClaimSchema.setGetNoticeNo(tGetNoticeNo);
        mLJAGetClaimSchema.setOPConfirmCode(mG.Operator);
        mLJAGetClaimSchema.setOPConfirmDate(CurrentDate);
        mLJAGetClaimSchema.setOPConfirmTime(CurrentTime);
        mLJAGetClaimSchema.setOperator(mG.Operator);
        mLJAGetClaimSchema.setMakeDate(CurrentDate);
        mLJAGetClaimSchema.setMakeTime(CurrentTime);
        mLJAGetClaimSchema.setModifyDate(CurrentDate);
        mLJAGetClaimSchema.setModifyTime(CurrentTime);
        mLJAGetClaimSchema.setOperState("0");
        mLJAGetClaimSchema.setAccPolNo(mLLBalanceSchema.getPolNo());

        mLJAGetClaimSet.add(mLJAGetClaimSchema);

        return true;
    }
    private boolean prepareOutputData() {
        try {
            mResult.clear();
            mResult.add(map);
            mResult.add(RptNo);
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LKPhoneCallBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "LLClaimMedBuyBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public MMap getMMap() {
        return map;
    }
    public VData getResult() {
        return mResult;
    }

}
