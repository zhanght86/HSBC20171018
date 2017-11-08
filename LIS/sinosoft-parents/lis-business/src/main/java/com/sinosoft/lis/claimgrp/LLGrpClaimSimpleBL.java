/*
 * <p>ClassName: LLGrpClaimSimpleBL </p>
 * <p>Description: LLGrpClaimSimpleBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @Author：pd
 * @CreateDate：2005-11-09
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.service.BusinessService;

public class LLGrpClaimSimpleBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLGrpClaimSimpleBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    private MMap map = new MMap();
    /** 全局数据 */
    TransferData mTransferData = new TransferData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData tTransferData = new TransferData();

    //账户理赔存取轨迹
    LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private ExeSQL mExeSQL = new ExeSQL();
    private SSRS mSSRS = new SSRS();

    private LLBnfSet mLLBnfSet = new LLBnfSet();

    private String mOperater;
    private String mClmNo = "";
    private String mAuditFlag = "";
    private String mFlag = "";
    private String mMaxLevel = ""; //最高权限
    private String mStandpay = "";
    private String mAdjpay = "";

    public LLGrpClaimSimpleBL() {
    }

    public static void main(String[] args) {

        LLGrpClaimSimpleBL tt = new LLGrpClaimSimpleBL();
        String tOperator = "";
        String tClmNo = "580101000181";
        String tFlag = "2";
        String tAuditFlag = "2";
        GlobalInput tG = new GlobalInput();
        tG.ManageCom = "0101";
        tG.Operator = "tk";
        VData tVData = new VData();
        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("RptNo", tClmNo);
        tTransferData.setNameAndValue("AuditFlag", tAuditFlag);
        tTransferData.setNameAndValue("Flag", tFlag);
        tVData.add(tG);
        tVData.add(tTransferData);
        if (!tt.submitData(tVData, tOperator)) {
            logger.debug("测试失败！");
        } else {
            logger.debug("测试失败！");
        }

    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        logger.debug("begin getinputdata");
        if (!getInputData(cInputData, cOperate)) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpClaimSimpleBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLGrpClaimSimpleBL-->getInputData!";
            this.mErrors.addOneError(tError);
            return false;

        }
        logger.debug(
                "----------LLClaimRegisterDealBL after getInputData----------");
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpClaimSimpleBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLGrpClaimSimpleBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug(
                "----------LLGrpClaimSimpleBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpClaimSimpleBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLGrpClaimSimpleBL-->submitquery!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug(
                "----------LLGrpClaimSimpleBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLGrpClaimSimpleBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mInputData = null;
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        /**---------------------------------------------------------------------BEG
         * 功能：根据简易案件结论为结论分别提交处理
         * 处理：1 简易案件结论为不通过时,为审核节点增加"来自"属性,分为B简易案件结论为进入
         *        审核因为赔案金额为正数，C审批进入审核因为赔案金额为负数,D来自简易案件
         *      2 简易案件结论为通过时,处理财务数据，详细见同审批
         *      3 计算审核权限
         *--------------------------------------------------------------------*/

        //案件核赔表
        String trptSql="select operator,nvl(ReturnMode,0) from llreport where rptno='"+mClmNo+"' ";
        mSSRS=mExeSQL.execSQL(trptSql);
        String tRptOperator=null;
        String tReturnMode=null;
        if (mSSRS.getMaxRow()>0)
        {
             tRptOperator=mSSRS.GetText(1,1); //报案操作员
             tReturnMode=mSSRS.GetText(1,2);  //延迟立案回退次数
        }
        String trgtSql="select operator from llregister where rgtno='"+mClmNo+"' ";
        String tRgtOperator=mExeSQL.getOneValue(trgtSql);//立案操作员

        LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
        LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
        tLLClaimUWMainDB.setClmNo(mClmNo);
        tLLClaimUWMainDB.getInfo();
        tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());

        mLLClaimUWMainSchema.setClmNo(mClmNo);
        mLLClaimUWMainSchema.setRgtNo(mClmNo);
        mLLClaimUWMainSchema.setCaseNo(mClmNo);
        mLLClaimUWMainSchema.setAuditConclusion(mAuditFlag);
        mLLClaimUWMainSchema.setAppClmUWer(tRptOperator);
        mLLClaimUWMainSchema.setAppActionType(tReturnMode);
        mLLClaimUWMainSchema.setClmUWer(tRgtOperator);
        mLLClaimUWMainSchema.setAuditPer(mGlobalInput.Operator);
        mLLClaimUWMainSchema.setAuditDate(CurrentDate);
        //查询审批信息
        if (tLLClaimUWMainSchema.getExamPer() != null&&!tLLClaimUWMainSchema.getExamPer().equals("")) {
            mLLClaimUWMainSchema.setExamPer(tLLClaimUWMainSchema.getExamPer());  //取出审批操作员
            mLLClaimUWMainSchema.setExamDate(tLLClaimUWMainSchema.getExamDate());//取出审批日期
            mLLClaimUWMainSchema.setAppPhase(tLLClaimUWMainSchema.getAppPhase());//取出审批回退次数
        }
        //查询立案回退次数
        if (tLLClaimUWMainSchema.getClmDecision() != null&&!tLLClaimUWMainSchema.getClmDecision().equals("")) {
            mLLClaimUWMainSchema.setClmDecision(tLLClaimUWMainSchema.getClmDecision());  //取出立案回退次数
        }
        mLLClaimUWMainSchema.setOperator(mGlobalInput.Operator);
        mLLClaimUWMainSchema.setMngCom(mGlobalInput.ManageCom);
        mLLClaimUWMainSchema.setMakeDate(CurrentDate);
        mLLClaimUWMainSchema.setMakeTime(CurrentTime);
        mLLClaimUWMainSchema.setModifyDate(CurrentDate);
        mLLClaimUWMainSchema.setModifyTime(CurrentTime);
        mLLClaimUWMainSchema.setExamConclusion("0");

        logger.debug("mAuditFlag == " + mAuditFlag); //复核结论： 0-复核通过；1-案件回退或立案通过
        logger.debug("mFlag == " + mFlag); //立案与复核区分标志： FROMSIMALL-非理算案件立案；2-复核;
        if (mAuditFlag.equals("1") || mAuditFlag.equals("2")) {
            //简易案件结论为不通过,工作流流入审核,赔案状态已经为审核,无需更改
            //为公共传输数据集合中添加工作流下一节点属性字段数据
            //账户案件确认在此处理
            mTransferData.setNameAndValue("ComeWhere", "D");
            mTransferData.removeByName("RptorState");
            mTransferData.setNameAndValue("RptorState", "30");

            //No.1.1 准备权限计算所需参数
            String tSelectSql1 = ""; //理算金额
            tSelectSql1 = "select nvl(a.realpay,0) from llclaim a where "
                          + "a.clmno = '" + mClmNo + "'";
            mStandpay = mExeSQL.getOneValue(tSelectSql1);
            if (mStandpay == null) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询理算金额失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            String tSelectSql2 = ""; //调整后金额
            tSelectSql2 = "select nvl(b.beadjsum,0) from llregister b where "
                          + "b.rgtno = '" + mClmNo + "'";
            mAdjpay = mExeSQL.getOneValue(tSelectSql2);
            if (mAdjpay == null) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询调整后金额失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            mTransferData.setNameAndValue("standpay", mStandpay);
            mTransferData.setNameAndValue("adjpay", mAdjpay);

            //No.2.2 更改赔案状态
            if (mFlag.equals("2")) {
                String Fsql = "select rgtstate from llregister where RgtNo = '" +
                              mClmNo + "' ";
                String trgtstate = mExeSQL.getOneValue(Fsql);
                if (trgtstate.equals("03")) {
                    String sql1 =
                            " update LLRegister set ClmState = '25' where"
                            + " RgtNo = '" + mClmNo + "'";
                    map.put(sql1, "UPDATE");
                } else {
                    String sql1 =
                            " update LLRegister set ClmState = '20' where"
                            + " RgtNo = '" + mClmNo + "'";
                    map.put(sql1, "UPDATE");
                }

                String sql2 = " update llclaim set ClmState = '20' where"
                              + " clmno = '" + mClmNo + "'";
                map.put(sql2, "UPDATE");

                String sql4 = " update llcase set RgtState = '20' where"
                              + " caseno = '" + mClmNo + "'";
                map.put(sql4, "UPDATE");

                String sql6 = " update LLClaimUWMain set ClmDecision=nvl(ClmDecision,0)+1 "
                              + " where ClmNo = '" + mClmNo + "'";
                map.put(sql6, "UPDATE"); //ClmDecision存案件回退次数。

                map.put(mLLClaimUWMainSchema, "DELETE&INSERT");

            }
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             修改原因：将rgtstate状态改为‘03’来标识是来自[非理算理赔]案件
             修 改 人：万泽辉
             修改日期：2006-01-21
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            else if (mFlag.equals("FROMSIMALL")) {
                String sql1 =
                        " update LLRegister set ClmState = '40',rgtstate = '03' where"
                        + " RgtNo = '" + mClmNo + "'";
                map.put(sql1, "UPDATE");
                String sql2 = " update llclaim set ClmState = '40' where"
                              + " clmno = '" + mClmNo + "'";
                map.put(sql2, "UPDATE");

                String sql4 = " update llcase set RgtState = '40' where"
                              + " caseno = '" + mClmNo + "'";
                map.put(sql4, "UPDATE");

            } else {
                String sql1 = " update LLRegister set ClmState = '40' where"
                              + " RgtNo = '" + mClmNo + "'";
                map.put(sql1, "UPDATE");
                String sql2 = " update llclaim set ClmState = '40' where"
                              + " clmno = '" + mClmNo + "'";
                map.put(sql2, "UPDATE");

                String sql14 = " update llcase set RgtState = '40' where"
                               + " caseno = '" + mClmNo + "'";
                map.put(sql14, "UPDATE");

            }

            //账户理赔记录存取
            /*
                         if (mAuditFlag.equals("2"))
                         {
                LLClaimAccountSet tLLClaimAccountSet = new LLClaimAccountSet();
                LLClaimAccountDB tLLClaimAccountDB = new LLClaimAccountDB();
                String accountSQL =
                        "select * from llclaimaccount where clmno='" + mClmNo +
                        "'";
             tLLClaimAccountSet = tLLClaimAccountDB.executeQuery(accountSQL);
                if (tLLClaimAccountSet.size() != 0)
                {
                    for (int i = 1; i <= tLLClaimAccountSet.size(); i++)
                    {
                        LLClaimAccountSchema tLLClaimAccountSchema = new
                                LLClaimAccountSchema();
                        LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                                LCInsureAccTraceSchema();
                        tLLClaimAccountSchema = tLLClaimAccountSet.get(i);
                        if (tLLClaimAccountSchema.getOtherType().equals("S"))
                        {
                            tLCInsureAccTraceSchema = getAccountToTrace(
                                    tLLClaimAccountSchema);
                            mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
                        }

                    }
                    //去掉  N 团体账户的利息 PX  LCInsureAccTrace不需要 2006-02-16 P.
                    String PAccountSQL = "select distinct polno,insuaccno,payplancode,otherno,othertype,paydate from llclaimaccount where clmno='" +
                                         mClmNo + "' and othertype in('P')";
                    ExeSQL tExeSQL = new ExeSQL();
                    SSRS tSSRS = new SSRS();
                    tSSRS = tExeSQL.execSQL(PAccountSQL);
                    if (tSSRS != null && tSSRS.getMaxRow() > 0)
                    {
                        String rowdata[][] = tSSRS.getAllData();
                        for (int k = 0; k < tSSRS.getMaxRow(); k++)
                        {
             LCInsureAccTraceSchema pLCInsureAccTraceSchema = new
                                    LCInsureAccTraceSchema();
                            if (!rowdata[k][0].equals(""))
             pLCInsureAccTraceSchema.setPolNo(rowdata[k][0]);
                            if (!rowdata[k][1].equals(""))
             pLCInsureAccTraceSchema.setInsuAccNo(rowdata[k][
                                        1]);
                            if (!rowdata[k][2].equals(""))
                                pLCInsureAccTraceSchema.setPayPlanCode(rowdata[
                                        k][2]);
                            if (!rowdata[k][3].equals(""))
                                pLCInsureAccTraceSchema.setRiskCode(rowdata[k][
                                        3]);
                            pLCInsureAccTraceSchema.setPayDate(CurrentDate);
                            String PAccMoney =
             "select sum(AccPayMoney) from llclaimaccount where polno='" +
                                    pLCInsureAccTraceSchema.getPolNo() +
                                    "' and clmno='" +
                                    mClmNo +
                                    "' and insuaccno='" +
                                    pLCInsureAccTraceSchema.getInsuAccNo() +
                                    "' and otherno='" +
                                    pLCInsureAccTraceSchema.getRiskCode() +
                                    "' and payplancode='" +
                                    pLCInsureAccTraceSchema.getPayPlanCode() +
                                    "' and othertype='" + rowdata[k][4] + "'"+
             " and paydate = '"+rowdata[k][5]+"'";//modify by wanzh 2006-03-23
                            ExeSQL mExeSQL = new ExeSQL();
                            SSRS mSSRS = new SSRS();
                            mSSRS = mExeSQL.execSQL(PAccMoney);
                            double modifyMoney = 0;
                            if (mSSRS.getMaxRow() > 0 && mSSRS != null)
                            {
                                String rowData1[] = mSSRS.getRowData(1);
                                if (!rowData1[0].equals(""))
                                {
             modifyMoney = Double.parseDouble(rowData1[0]);
                                }
                            }
                            if (rowdata[k][4].equals("P"))
                            { //P 团体报销额
                                modifyMoney = modifyMoney * ( -1);
                                pLCInsureAccTraceSchema.setMoney(modifyMoney);
                                //统一改为PG 2006-02-16 P.D
                                pLCInsureAccTraceSchema.setMoneyType("PG");
                            }
                            if (rowdata[k][4].equals("N"))
                            { //N 团体账户的利息
                                pLCInsureAccTraceSchema.setMoney(modifyMoney);
                                pLCInsureAccTraceSchema.setMoneyType("PX");
                            }
                            String AccTraceSQL =
             "select * from lcinsureacctrace where polno='" +
                                    pLCInsureAccTraceSchema.getPolNo() +
                                    "' and insuaccno='" +
                                    pLCInsureAccTraceSchema.getInsuAccNo() +
                                    "' and riskcode='" +
                                    pLCInsureAccTraceSchema.getRiskCode() +
                                    "' and payplancode='" +
                                    pLCInsureAccTraceSchema.getPayPlanCode() +
                                    "' and moneytype='BF'";
                            LCInsureAccTraceDB trLCInsureAccTraceDB = new
                                    LCInsureAccTraceDB();
                            LCInsureAccTraceSet trLCInsureAccTraceSet = new
                                    LCInsureAccTraceSet();
                            trLCInsureAccTraceSet = trLCInsureAccTraceDB.
                                    executeQuery(AccTraceSQL);
                            if (trLCInsureAccTraceSet.size() > 0)
                            {
             LCInsureAccTraceSchema trLCInsureAccTraceSchema = new
                                        LCInsureAccTraceSchema();
                                trLCInsureAccTraceSchema =
                                        trLCInsureAccTraceSet.get(1);
                                String SerialNo = PubFun1.CreateMaxNo(
                                        "SERIALNO", mGlobalInput.ManageCom);
             logger.debug("-----生成流水线号--- " + SerialNo);
                                pLCInsureAccTraceSchema.setSerialNo(SerialNo);
                                pLCInsureAccTraceSchema.setGrpPolNo(
             trLCInsureAccTraceSchema.getGrpPolNo());
                                pLCInsureAccTraceSchema.setOtherType("5");
                                pLCInsureAccTraceSchema.setManageCom(
             trLCInsureAccTraceSchema.getManageCom());
                                pLCInsureAccTraceSchema.setGrpContNo(
             trLCInsureAccTraceSchema.getGrpContNo());
                                pLCInsureAccTraceSchema.setContNo(
                                        trLCInsureAccTraceSchema.getContNo());
                                pLCInsureAccTraceSchema.setOtherNo(mClmNo);
                                pLCInsureAccTraceSchema.setState("0");
                                pLCInsureAccTraceSchema.setAccAscription("1");
                                pLCInsureAccTraceSchema.setOperator(
                                        mGlobalInput.Operator);
             pLCInsureAccTraceSchema.setMakeDate(CurrentDate);
             pLCInsureAccTraceSchema.setMakeTime(CurrentTime);
                                pLCInsureAccTraceSchema.setModifyDate(
                                        CurrentDate);
                                pLCInsureAccTraceSchema.setModifyTime(
                                        CurrentTime);
                                mLCInsureAccTraceSet.add(
                                        pLCInsureAccTraceSchema);
                            }
                            //mLCInsureAccTraceSet.add(pLCInsureAccTraceSchema);
                            //jixf  把这一行提到上面去
                        }
                    }
                }
                if (mLCInsureAccTraceSet.size() != 0)
                {
                    String DeleteTraceSQL = "delete from lcinsureacctrace where trim(grpcontno) in (select distinct grpcontno from llclaimaccount where clmno='" +
                                            mClmNo +
             "') and moneytype in('PL','PX','SL','SX','PG') " +
                                            "and OtherNo='" + mClmNo + "'";
                    map.put(DeleteTraceSQL, "DELETE");
                    map.put(mLCInsureAccTraceSet, "INSERT");
                }
                         }
             */
        } else {
            /*加锁，准备挂起，防止中间点2次结案按钮*/
            LLClaimDB mLLClaimDB = new LLClaimDB();
            LLClaimSet mLLClaimSet = new LLClaimSet();
            LLClaimSchema mLLClaimSchema = new LLClaimSchema();
            mLLClaimDB.setClmNo(mClmNo);
            mLLClaimSet = mLLClaimDB.query();
            mLLClaimSchema = mLLClaimSet.get(1);
            String tGetDutyKind = mLLClaimSchema.getGetDutyKind();
            if (tGetDutyKind == null) {
                tGetDutyKind = "";
            }
            if (tGetDutyKind.equals("1")) {
                CError tError = new CError();
                tError.moduleName = "CardBL";
                tError.functionName = "getInputData";
                tError.errorMessage = "该赔案正在做复核结案，请稍等!";
                this.mErrors.addOneError(tError);
                return false;
            } else {
                map.put("update LLClaim set GetDutyKind='1' where clmno='" +
                        mLLClaimSchema.getClmNo() + "' ", "UPDATE");
                VData tInputData = new VData();
                tInputData.add(map);
                PubSubmit tPubSubmit = new PubSubmit();
                if (!tPubSubmit.submitData(tInputData, mOperate)) {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "LCNewCardChcekBL";
                    tError.functionName = "submitData";
                    tError.errorMessage = "数据提交失败!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
            }

            /**
             *受益人分配和写入LJSGET表
             */
            if (!getLLBnf()) {
                return false;
            }

            //tTransferData.setNameAndValue("ClmNo", mClmNo);
            //准备传输数据 VData
            VData tVData = new VData();
            tVData.add(mGlobalInput);
            tVData.add(tTransferData);
            tVData.add(mLLBnfSet);
            LLBnfBL tLLBnfBL = new LLBnfBL();
            if (!tLLBnfBL.submitData(tVData, "INSERT")) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLBnfBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                mResult.clear();
                mInputData = null;
                return false;
            } else {
                VData tVDate = new VData();
                tVDate = tLLBnfBL.getResult();
                logger.debug("-----start Service getData from BL");
                MMap tMap = new MMap();
                tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
                map.add(tMap);
            }

            //No.2.1 简易案件结论为通过,同审核处理
            LLClaimConfirmPassBL tLLClaimConfirmPassBL = new
                    LLClaimConfirmPassBL();
            if (!tLLClaimConfirmPassBL.submitData(mInputData, "")) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLClaimConfirmPassBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                mResult.clear();
                mInputData = null;
                return false;
            } else {
                VData tVDate = new VData();
                tVDate = tLLClaimConfirmPassBL.getResult();
                logger.debug("-----start Service getData from BL");
                MMap tMap = new MMap();
                tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
                map.add(tMap);
            }

            //解除保单挂起
            LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
            if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "解除保单挂起失败!";
                this.mErrors.addOneError(tError);
                return false;
            } else {
                VData tempVData = new VData();
                tempVData = tLLLcContReleaseBL.getResult();
                MMap tMap = new MMap();
                tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
                map.add(tMap);
            }

            //更改死亡标志
            if (!dealDeath()) {
                return false;
            }

            //No.2.2 更改赔案状态为结案
            String sql1 =
                    " update LLRegister set ClmState = '60' ,EndCaseFlag = '1' , EndCaseDate = '" +
                    PubFun.getCurrentDate() + "' where"
                    + " RgtNo = '" + mClmNo + "'";
            map.put(sql1, "UPDATE");

            String sql2 =
                    " update llclaim set ClmState = '60' , EndCaseDate = '" +
                    PubFun.getCurrentDate()
                    + "' , ClmUWer = '" + mOperater //理赔员
                    + "' where clmno = '" + mClmNo + "'";
            map.put(sql2, "UPDATE");

            String sql4 =
                    " update llcase set RgtState = '60' , EndCaseDate = '" +
                    PubFun.getCurrentDate()
                    + "' where caseno = '" + mClmNo + "'";
            map.put(sql4, "UPDATE");

            //如果录入了医疗明细，结案后删除mainfeeno='0000000000'的信息
            String sql7 = "select * from llcasereceipt where caseno = '" +
                          mClmNo + "' and mainfeeno<>'0000000000' ";

            mSSRS = mExeSQL.execSQL(sql7);
            if (mSSRS.getMaxRow() > 0) {
                String sql6 = " delete from llcasereceipt where caseno = '" +
                              mClmNo + "' and mainfeeno='0000000000' ";
                map.put(sql6, "DELETE");
            }

            map.put(mLLClaimUWMainSchema, "DELETE&INSERT");

            //写入销售渠道统计表 2006-02-17 P.D
            LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
            LLClaimSaleChnlSet tLLClaimSaleChnlSet = new LLClaimSaleChnlSet();
            tLLClaimSaleChnlSet = tLLClaimPubFunBL.insertLLClaimSaleChnl(mClmNo,
                    mOperater, mGlobalInput.ManageCom);
            if (tLLClaimSaleChnlSet.size() > 0) {
                map.put(tLLClaimSaleChnlSet, "DELETE&INSERT");
            } else {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLClaimPubFunBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "写入销售渠道统计表失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            //为公共传输数据集合中添加工作流下一节点属性字段数据,结案要回到立案机构
            String sql3 = " select MngCom from LLRegister where"
                          + " RgtNo = '" + mClmNo + "'";
            String tMngCom = mExeSQL.getOneValue(sql3);
            if (tMngCom != null) {
                mTransferData.removeByName("MngCom");
                mTransferData.setNameAndValue("MngCom", tMngCom);
            }

        }
        //----------------------------------------------------------------------END

        return true;

    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate) {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mClmNo = (String) mTransferData.getValueByName("RptNo");
        mAuditFlag = (String) mTransferData.getValueByName("AuditFlag");
        mFlag = (String) mTransferData.getValueByName("Flag");
        mInputData = cInputData;
        if (mGlobalInput == null) {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "AskUWSimpleAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得操作员编码
        mOperater = mGlobalInput.Operator;
        if (mOperater == null || mOperater.trim().equals("")) {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLClaimSimpleAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operate失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     *取到LLBNF表相关数据
     * @return boolean
     */
    private boolean getLLBnf() {
        LLCaseDB mLLCaseDB = new LLCaseDB();
        mLLCaseDB.setCaseNo(mClmNo);
        LLCaseSet tLLCaseSet = mLLCaseDB.query();
        if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "getLLBnf";
            tError.errorMessage = "查询分案信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        String strSql =
                " select polno,sum(pay),GrpContNo,GrpPolNo,ContNo from LLBalance where"
                + " ClmNo = '" + mClmNo +
                "' group by polno,GrpContNo,GrpPolNo,ContNo";

         mSSRS = mExeSQL.execSQL(strSql);
        if (mExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(mExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimAuditBL";
            tError.functionName = "getLLBnf";
            tError.errorMessage = "未查询到LLBNF表的信息!";
            logger.debug(
                    "------------------------------------------------------");
            logger.debug("--LLClaimCalAutoBL.dealData()--理赔计算时发生错误!" +
                               strSql);
            logger.debug(
                    "------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int a = 1; a <= mSSRS.getMaxRow(); a++) {
            String PolNo = mSSRS.GetText(a, 1);
            String pay = mSSRS.GetText(a, 2);
            String GrpContNo = mSSRS.GetText(a, 3);
            String GrpPolNo = mSSRS.GetText(a, 4);
            String ContNo = mSSRS.GetText(a, 5);

            LCInsuredSet mLCInsuredSet = new LCInsuredSet();
            LCInsuredDB mLCInsuredDB = new LCInsuredDB();
            mLCInsuredDB.setContNo(ContNo);
            mLCInsuredSet = mLCInsuredDB.query();

            for (int i = 1; i <= mLCInsuredSet.size(); i++) {
                LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
                mLCInsuredSchema = mLCInsuredSet.get(i);
                LLBnfSchema mLLBnfSchema = new LLBnfSchema();
                String tCNo = mLCInsuredSchema.getInsuredNo();
                logger.debug("=========cNO====" + tCNo);
                /*
                 LDPersonSchema mLDPersonSchema = new LDPersonSchema();
                                 LDPersonDB mLDPersonDB = new LDPersonDB();
                                 LLBnfSchema mLLBnfSchema = new LLBnfSchema();
                 String tCNo = tLLCaseSet.get(i).getCustomerNo();
                                 mLDPersonDB.setCustomerNo(tCNo);
                                 if (mLDPersonDB.getInfo())
                                 {
                    mLDPersonSchema = mLDPersonDB.getSchema();
                                 }
                 String strSql2 = " select * from lcpol where polno = '" + PolNo +
                                 "' and  insuredno = '" + tCNo + "'";
                                 SSRS tSSRS2 = tExeSQL.execSQL(strSql2);
                                 if (tSSRS2.getMaxRow() > 0)
                                 {
                 */
                mLLBnfSchema.setClmNo(mClmNo);
                mLLBnfSchema.setCaseNo(mClmNo);
                mLLBnfSchema.setBatNo("0");
                mLLBnfSchema.setGrpContNo(GrpContNo);
                mLLBnfSchema.setGrpPolNo(GrpPolNo);
                mLLBnfSchema.setContNo(ContNo);
                mLLBnfSchema.setBnfKind("A");
                mLLBnfSchema.setPolNo(PolNo);
                mLLBnfSchema.setInsuredNo(tCNo);
                mLLBnfSchema.setBnfNo("1");
                mLLBnfSchema.setCustomerNo(tCNo);
                mLLBnfSchema.setName(mLCInsuredSchema.getName());
                mLLBnfSchema.setPayeeNo(tCNo);
                mLLBnfSchema.setPayeeName(mLCInsuredSchema.getName());
                mLLBnfSchema.setBnfType("0");
                mLLBnfSchema.setBnfGrade("0");
                mLLBnfSchema.setRelationToInsured("00");
                mLLBnfSchema.setSex(mLCInsuredSchema.getSex());
                mLLBnfSchema.setBirthday(mLCInsuredSchema.getBirthday());
                mLLBnfSchema.setIDType(mLCInsuredSchema.getIDType());
                mLLBnfSchema.setIDNo(mLCInsuredSchema.getIDNo());
                mLLBnfSchema.setRelationToPayee("00");
                mLLBnfSchema.setPayeeSex(mLCInsuredSchema.getSex());
                mLLBnfSchema.setPayeeBirthday(mLCInsuredSchema.getBirthday());
                mLLBnfSchema.setPayeeIDType(mLCInsuredSchema.getIDType());
                mLLBnfSchema.setPayeeIDNo(mLCInsuredSchema.getIDNo());
                mLLBnfSchema.setGetMoney(pay);
                mLLBnfSchema.setBnfLot("100");
                mLLBnfSchema.setCasePayMode("");
                mLLBnfSchema.setCasePayFlag("0"); //保险金支付标志
                mLLBnfSchema.setBankCode("");
                mLLBnfSchema.setBankAccNo("");
                mLLBnfSchema.setAccName("");
                mLLBnfSet.add(mLLBnfSchema);
                //}
            }
            //String使用TransferData打包后提交
            tTransferData.setNameAndValue("ClmNo", mClmNo);
            tTransferData.setNameAndValue("PolNo", PolNo);
            tTransferData.setNameAndValue("BnfKind", "A");
        }
        return true;
    }

    /**
     * 针对死亡案件更改死亡日期和标志
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean dealDeath() {
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(mClmNo);
        LLCaseSet tLLCaseSet = tLLCaseDB.query();
        if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "dealData";
            tError.errorMessage = "查询分案信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int i = 1; i <= tLLCaseSet.size(); i++) {
            String tCNo = tLLCaseSet.get(i).getCustomerNo();
            LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
            tLLAppClaimReasonDB.setCaseNo(mClmNo);
            tLLAppClaimReasonDB.setRgtNo(mClmNo);
            tLLAppClaimReasonDB.setCustomerNo(tCNo);
            LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.
                    query();
            if (tLLAppClaimReasonSet == null && tLLAppClaimReasonSet.size() < 1) {
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询赔案理赔类型信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
                String tCode = tLLAppClaimReasonSet.get(j).getReasonCode().
                               substring(2, 3);
                if (tCode.equals("02")) { //死亡
                    //更改立案分案表
                    String sql3 = " update llcase set DieFlag = '1',"
                                  + " DeathDate = AccDate where"
                                  + " CaseNo = '" + mClmNo + "'"
                                  + " and CustomerNo = '" + tCNo + "'";
                    map.put(sql3, "UPDATE");
                    //更改报案分案表
                    String sql4 = " update LLSubReport set DieFlag = '1',"
                                  + " DieDate = AccDate where"
                                  + " CaseNo = '" + mClmNo + "'"
                                  + " and CustomerNo = '" + tCNo + "'";
                    map.put(sql4, "UPDATE");
                    //更改客户表
                    String sql5 = " update LDPerson set DeathDate = to_date('"
                                  + tLLCaseSet.get(i).getAccDate()
                                  + "','yyyy-mm-dd') where"
                                  + " CustomerNo = '" + tCNo + "'";
                    map.put(sql5, "UPDATE");

                    break;
                }
            }
        }
        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData() {
        try {
            this.mInputData.clear();
            this.mInputData.add(map);
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLBnfBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult() {
        return this.mResult;
    }

    private LCInsureAccTraceSchema getAccountToTrace(LLClaimAccountSchema
            mLLClaimAccountSchema) {
        LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
        LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        LCInsureAccTraceSchema rLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        String SAccTraceSQL = "select * from lcinsureacctrace where polno='" +
                              mLLClaimAccountSchema.getPolNo() +
                              "' and insuaccno='" +
                              mLLClaimAccountSchema.getInsuAccNo() +
                              "' and riskcode='" +
                              mLLClaimAccountSchema.getOtherNo() +
                              "' and payplancode='" +
                              mLLClaimAccountSchema.getPayPlanCode() +
                              "' and moneytype='BF'";
        logger.debug("轨迹：" + SAccTraceSQL);
        mLCInsureAccTraceSet = mLCInsureAccTraceDB.executeQuery(SAccTraceSQL);
        if (mLCInsureAccTraceSet.size() > 0) {
            mLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(1);
        }
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO",
                                              mGlobalInput.ManageCom);
        logger.debug("-----生成流水线号--- " + SerialNo);
        rLCInsureAccTraceSchema.setSchema(mLCInsureAccTraceSchema);
        rLCInsureAccTraceSchema.setSerialNo(SerialNo);
        rLCInsureAccTraceSchema.setGrpContNo(mLLClaimAccountSchema.getGrpContNo());
        rLCInsureAccTraceSchema.setPolNo(mLLClaimAccountSchema.getPolNo());
        rLCInsureAccTraceSchema.setInsuAccNo(mLLClaimAccountSchema.getInsuAccNo());
        rLCInsureAccTraceSchema.setPayPlanCode(mLLClaimAccountSchema.
                                               getPayPlanCode());
        rLCInsureAccTraceSchema.setOtherType("5");
        rLCInsureAccTraceSchema.setOtherNo(mClmNo);
        rLCInsureAccTraceSchema.setRiskCode(mLLClaimAccountSchema.getOtherNo());
        rLCInsureAccTraceSchema.setPayDate(mLLClaimAccountSchema.getPayDate());
        rLCInsureAccTraceSchema.setAccAscription("1");
        double ModifyMoney = 0;
        ModifyMoney = mLLClaimAccountSchema.getAccPayMoney();
        if (mLLClaimAccountSchema.getOtherType().equals("S")) { //个人账户报销额
            ModifyMoney = ModifyMoney * ( -1);
            rLCInsureAccTraceSchema.setMoney(ModifyMoney);
//            rLCInsureAccTraceSchema.setMoneyType("SL");//统一改为PG 2006-02-16 P.D
            rLCInsureAccTraceSchema.setMoneyType("PG");
        }
        if (mLLClaimAccountSchema.getOtherType().equals("M")) { //个人账户余额
            rLCInsureAccTraceSchema.setMoney(ModifyMoney);
            rLCInsureAccTraceSchema.setMoneyType("SX");
        }
        rLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
        rLCInsureAccTraceSchema.setMakeDate(CurrentDate);
        rLCInsureAccTraceSchema.setMakeTime(CurrentTime);
        rLCInsureAccTraceSchema.setModifyDate(CurrentDate);
        rLCInsureAccTraceSchema.setModifyTime(CurrentTime);
        return rLCInsureAccTraceSchema;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
