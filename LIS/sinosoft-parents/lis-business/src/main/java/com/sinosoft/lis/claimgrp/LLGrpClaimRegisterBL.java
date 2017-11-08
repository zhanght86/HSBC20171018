/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claim.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.service.BusinessService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLGrpClaimRegisterBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLGrpClaimRegisterBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    private VData tResult = new VData();
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
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();
    private LLCaseSet mLLCaseSet = new LLCaseSet();
    private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();
    private LLAffixSet mLLAffixSet = new LLAffixSet();

    //报案相关
    private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();
    private LLReportSchema mLLReportSchema = new LLReportSchema();
    private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();
    private LLReportReasonSchema mLLReportReasonSchema = new
            LLReportReasonSchema();
    private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();

    //账户相关
    private LLClaimAccountSet mLLClaimAccountSet = new LLClaimAccountSet(); //前台传入数据
    private LLClaimAccountSchema mLLClaimAccountSchema = new
            LLClaimAccountSchema();
    private LLClaimAccountSet sLLClaimAccountSet = new LLClaimAccountSet(); //后台处理数据集\
    private LLClaimAccountSet pLLClaimAccountSet = new LLClaimAccountSet(); //后台处理数据集
    private LLFeeMainSchema mLLFeeMainSchema = new LLFeeMainSchema();
    private LLCaseReceiptSchema mLLCaseReceiptSchema = new LLCaseReceiptSchema();

    //流水号类型,详细见SysMaxNo
    private String mRptNo = "";
    private String mAccNo = "";
    private boolean mIsRegisterExist;
    private boolean mIsReportExist;
    private boolean mIsAccExist;

    public LLGrpClaimRegisterBL() {
    }

    public static void main(String[] args) {

    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        logger.debug(
                "----------LLGrpClaimRegisterBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimRegisterBL after getInputData----------");

        //进行业务处理
        if (!dealData(cOperate)) {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimRegisterBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData()) {
            return false;
        }
        logger.debug(
                "----------LLGrpClaimRegisterBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (this.mOperate.equals("IMPORT||MAIN")) {
            return true;
        } else if (!tPubSubmit.submitData(mResult, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLGrpClaimRegisterBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult() {
        return tResult;
    }

    public VData getResult2() {
        return mResult;
    }

    public MMap getMMap() {
        return map;
    }

    /**
     * 从输入数据中得到所有对象
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData() {
        logger.debug("---LLClaimRegisterBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);

        mLLAccidentSchema = (LLAccidentSchema) mInputData.getObjectByObjectName(
                "LLAccidentSchema", 0);
        mLLRegisterSchema = (LLRegisterSchema) mInputData.getObjectByObjectName(
                "LLRegisterSchema", 0);
        mLLCaseSchema = (LLCaseSchema) mInputData.getObjectByObjectName(
                "LLCaseSchema", 0);
        mLLAppClaimReasonSet = (LLAppClaimReasonSet) mInputData.
                               getObjectByObjectName(
                                       "LLAppClaimReasonSet", 0);
        /******************************jinsh20070403点修改时将信息保存到报案的表里*******************************/
        if (this.mOperate.equals("UPDATE") || this.mOperate.equals("ACCUPDATE")) {
            mLLReportSchema = (LLReportSchema) mInputData.getObjectByObjectName(
                    "LLReportSchema", 0);
            mLLSubReportSchema = (LLSubReportSchema) mInputData.
                                 getObjectByObjectName("LLSubReportSchema", 0);
            mLLReportReasonSchema = (LLReportReasonSchema) mInputData.
                                    getObjectByObjectName(
                                            "LLReportReasonSchema", 0);
            mLLReportReasonSet = (LLReportReasonSet) mInputData.
                                 getObjectByObjectName("LLReportReasonSet", 0);
        }
        /******************************jinsh20070403点修改时将信息保存到报案的表里********************************/
        if (this.mOperate == "AccInsert" || this.mOperate == "ACCUPDATE" ||
            this.mOperate == "ForAccInsert") {
            logger.debug("账户理赔参数");
            mLLClaimAccountSet = (LLClaimAccountSet) mInputData.
                                 getObjectByObjectName("LLClaimAccountSet", 0);
            mLLFeeMainSchema = (LLFeeMainSchema) mInputData.
                               getObjectByObjectName("LLFeeMainSchema", 0);
            mLLCaseReceiptSchema = (LLCaseReceiptSchema) mInputData.
                                   getObjectByObjectName("LLCaseReceiptSchema",
                    0);
            if (mLLClaimAccountSet == null && mLLFeeMainSchema == null &&
                mLLCaseReceiptSchema == null) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimRegisterBL";
                tError.functionName = "getInputData";
                tError.errorMessage = "账户相关金额不存在，请查证！";
                this.mErrors.addOneError(tError);
                return false;
            }
        }

        if (mLLRegisterSchema == null && mLLCaseSchema == null) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate) {
        logger.debug("---LLGrpClaimRegisterBL start dealData()...");
        boolean tReturn = false;
        String deleteSql = "";
        String deleteSqlUp = "";
        //----------------------------------------------------------------------Beg
        //功能：判断事件、报案、立案信息是否存在
        //处理：1 事件号为空，则报案、立案都不存在；事件号不为空，则判断报案号。
        //     2 报案号为空，则立案不存在；报案号不为空，则查询立案是否存在。
        //----------------------------------------------------------------------
        IsExist();
        //----------------------------------------------------------------------End

        if (cOperate.equals("INSERT") || this.mOperate.equals("IMPORT||MAIN")
            || cOperate.equals("AccInsert") ||
            this.mOperate.equals("INSERT||TOSIMALL"))
        //----------------------------------------------------------------------
        //功能：添加纪录
        //处理：1 报案不存在则新建所有
        //     2 报案存在。则判断立案，立案不存在则同步所有报案信息
        //                          立案存在则只更新立案分案信息
        //----------------------------------------------------------------------
        {
            //报案不存在
            logger.debug("mIsReportExist == " + mIsReportExist);
            if (!mIsReportExist) {
                logger.debug("---报案不存在:");

                //处理报案相关表，处理完返回的数据打包到map

                if (!addReport()) {
                    //@@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LLClaimRegisterBL";
                    tError.functionName = "dealData";
                    tError.errorMessage = "为报案相关表准备数据失败!";
                    this.mErrors.addOneError(tError);
                    tReturn = false;
                    return false;
                }

                if (!addInsuAcc(cOperate)) {
                    //@@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LLClaimRegisterBL";
                    tError.functionName = "dealData";
                    tError.errorMessage = "账户相关表准备数据失败!";
                    this.mErrors.addOneError(tError);
                    tReturn = false;
                    return false;
                }
                //立案表
                mLLRegisterSchema.setRgtNo(mRptNo); //赔案号
                if (!cOperate.equals("AccInsert")) {
                    mLLRegisterSchema.setRgtObj("1"); //号码类型(?)
                }
                mLLRegisterSchema.setRgtObjNo("1"); //其他号码(?)
                mLLRegisterSchema.setRgtClass("2"); //申请类型,前台已输入
                mLLRegisterSchema.setCustomerNo("1"); //客户号(?)
                if (cOperate.equals("AccInsert")) {
                    mLLRegisterSchema.setRgtState("02"); //*案件类型*，账户案件
                } else {
                    mLLRegisterSchema.setRgtState("01"); //*案件类型*，简易案件
                }
                mLLRegisterSchema.setRgtConclusion("01"); //立案结论,立案通过
                mLLRegisterSchema.setClmState("20");
                mLLRegisterSchema.setRgtDate(CurrentDate);
                mLLRegisterSchema.setOperator(mG.Operator);
                mLLRegisterSchema.setMngCom(mG.ManageCom);
                mLLRegisterSchema.setMakeDate(CurrentDate);
                mLLRegisterSchema.setMakeTime(CurrentTime);
                mLLRegisterSchema.setModifyDate(CurrentDate);
                mLLRegisterSchema.setModifyTime(CurrentTime);

                //立案分案表
                mLLCaseSchema.setCaseNo(mRptNo); //赔案号
                mLLCaseSchema.setRgtNo(mRptNo); //赔案号
                if (cOperate.equals("AccInsert")) {
                    mLLCaseSchema.setRgtType("02"); //案件类型,账户案件
                } else {
                    mLLCaseSchema.setRgtType("01"); //案件类型,简易案件
                }
                mLLCaseSchema.setRgtState("20"); //案件状态
                mLLCaseSchema.setAffixConclusion("0"); //单证齐全标志
                mLLCaseSchema.setEditFlag("0"); //重要信息修改标志
                mLLCaseSchema.setRgtDate(CurrentDate);
                mLLCaseSchema.setOperator(mG.Operator);
                mLLCaseSchema.setMngCom(mG.ManageCom);
                mLLCaseSchema.setMakeDate(CurrentDate);
                mLLCaseSchema.setMakeTime(CurrentTime);
                mLLCaseSchema.setModifyDate(CurrentDate);
                mLLCaseSchema.setModifyTime(CurrentTime);

                //理赔类型表(多条添加)
                for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
                    mLLAppClaimReasonSet.get(i).setCaseNo(mRptNo);
                    mLLAppClaimReasonSet.get(i).setRgtNo(mRptNo);
                    mLLAppClaimReasonSet.get(i).setOperator(mG.Operator);
                    mLLAppClaimReasonSet.get(i).setMngCom(mG.ManageCom);
                    mLLAppClaimReasonSet.get(i).setMakeDate(CurrentDate);
                    mLLAppClaimReasonSet.get(i).setMakeTime(CurrentTime);
                    mLLAppClaimReasonSet.get(i).setModifyDate(CurrentDate);
                    mLLAppClaimReasonSet.get(i).setModifyTime(CurrentTime);
                }
                map.put(mLLAppClaimReasonSet, "INSERT");
                map.put(mLLRegisterSchema, "INSERT");
                map.put(mLLCaseSchema, "INSERT");

                tReturn = true;
                return true;
            }
            //报案已存在
            else {
                logger.debug("---报案已存在:");
                //立案存在,只增加出险人
                //add by wood 为了支持多次导入，不判断是否存在立案，统一按没有立案处理
                if (!this.mOperate.equals("INSERT||TOSIMALL")) { //针对非理算理赔的区分
                    mIsRegisterExist = false;
                    if (mIsRegisterExist) {
                        logger.debug("---立案存在:导入的案件!");
                        tReturn = true;
                        return true;
                        /*
                             //查询出险人是否存在于立案
                             String strSql = "select CaseNo from LLCase where "
                                             + "CustomerNo = '"
                                             + mLLCaseSchema.getCustomerNo()
                                             + "' and CaseNo = '"
                                             + mLLCaseSchema.getCaseNo() + "'";
                             ExeSQL exesql = new ExeSQL();
                             String tResult = exesql.getOneValue(strSql);
                             if (tResult.length() > 0)
                             {
                                 // @@错误处理
                                 CError tError = new CError();
                                 tError.moduleName = "LLClaimRegisterBL";
                                 tError.functionName = "dealData";
                                 tError.errorMessage = "出险人已存在于此立案中!";
                                 this.mErrors.addOneError(tError);
                                 return false;
                             }
                             else
                             {
                                 //处理报案相关表，处理完返回的数据打包到map
                                 if (!addReport())
                                 {
                                     //@@错误处理
                                     CError tError = new CError();
                                     tError.moduleName = "LLClaimRegisterBL";
                                     tError.functionName = "dealData";
                                     tError.errorMessage = "为报案相关表准备数据失败!";
                                     this.mErrors.addOneError(tError);
                                     tReturn = false;
                                        return false;
                                 }

                                 if (!addInsuAcc(cOperate))
                                 {
                                   //@@错误处理
                                   CError tError = new CError();
                                   tError.moduleName = "LLClaimRegisterBL";
                                   tError.functionName = "dealData";
                                   tError.errorMessage = "账户相关表准备数据失败!";
                                   this.mErrors.addOneError(tError);
                                   tReturn = false;
                                      return false;
                                  }

                                 //立案分案表
                         mLLCaseSchema.setRgtNo(mLLCaseSchema.getCaseNo()); //赔案号
                                 if(cOperate.equals("AccInsert"))
                                 {
                                   mLLCaseSchema.setRgtType("02"); //案件类型,账户案件
                                 }
                                 else
                                 {
                                   mLLCaseSchema.setRgtType("01"); //案件类型,简易案件
                                 }
                                 mLLCaseSchema.setRgtState("20"); //案件状态
                                 mLLCaseSchema.setAffixConclusion("0");//单证齐全标志
                                 mLLCaseSchema.setEditFlag("0");//重要信息修改标志
                                 mLLCaseSchema.setRgtDate(CurrentDate);
                                 mLLCaseSchema.setOperator(mG.Operator);
                                 mLLCaseSchema.setMngCom(mG.ManageCom);
                                 mLLCaseSchema.setMakeDate(CurrentDate);
                                 mLLCaseSchema.setMakeTime(CurrentTime);
                                 mLLCaseSchema.setModifyDate(CurrentDate);
                                 mLLCaseSchema.setModifyTime(CurrentTime);
                                 //立案理赔类型表(多条添加)
                         for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++)
                                 {
                         mLLAppClaimReasonSet.get(i).setOperator(mG.
                                             Operator);
                                     mLLAppClaimReasonSet.get(i).setMngCom(mG.
                                             ManageCom);
                                     mLLAppClaimReasonSet.get(i).setMakeDate(
                                             CurrentDate);
                                     mLLAppClaimReasonSet.get(i).setMakeTime(
                                             CurrentTime);
                                     mLLAppClaimReasonSet.get(i).setModifyDate(
                                             CurrentDate);
                                     mLLAppClaimReasonSet.get(i).setModifyTime(
                                             CurrentTime);
                                 }
                                 map.put(mLLAppClaimReasonSet, "INSERT");
                                 map.put(mLLCaseSchema, "INSERT");
                                 tReturn = true;
                                 return true;
                             }
                         */
                    }                 //立案不存在
                else {
                    logger.debug("---立案不存在,同步报案数据:");

                    //-------------------------------------------------------------
                    if (!addInsuAcc(cOperate)) {
                        //@@错误处理
                        CError tError = new CError();
                        tError.moduleName = "LLClaimRegisterBL";
                        tError.functionName = "dealData";
                        tError.errorMessage = "账户相关表准备数据失败!";
                        this.mErrors.addOneError(tError);
                        tReturn = false;
                        return false;
                    }

                    //同步所有报案信息到立案,同时更新立案标志为已立案20
                    //-------------------------------------------------------------
                    //报案到立案表------------------------------------------------Beg
                    LLReportDB tLLReportDB = new LLReportDB();
                    LLReportSchema tLLReportSchema = new LLReportSchema();
                    String sql = "select * from LLReport where"
                                 + " RptNo = '" + mRptNo + "'";
                    logger.debug("Start query LLReport:" + sql);
                    LLReportSet tLLReportSet = tLLReportDB.executeQuery(sql);

                    if (tLLReportSet == null && tLLReportSet.size() == 0) {
                        //@@错误处理
                        CError tError = new CError();
                        tError.moduleName = "LLClaimRegisterBL";
                        tError.functionName = "dealData";
                        tError.errorMessage = "查询报案表失败!";
                        this.mErrors.addOneError(tError);
                        tReturn = false;
                        return false;
                    } else {
                        tLLReportSchema = tLLReportSet.get(1);
                        //立案表
                        mLLRegisterSchema.setRgtNo(tLLReportSchema.getRptNo()); //报案号=赔案号
                        mLLRegisterSchema.setAccidentDate(tLLReportSchema.
                                getAccidentDate()); //意外事故发生日期
                        mLLRegisterSchema.setAccidentReason(tLLReportSchema.
                                getAccidentReason()); //出险原因
                        if (!cOperate.equals("AccInsert")) {
                            mLLRegisterSchema.setRgtObj("1"); //号码类型(?)
                        } else {
                            mLLRegisterSchema.setRgtObj(tLLReportSchema.
                                    getRgtObj()); //是否使用公共账户(?)
                        }
                        mLLRegisterSchema.setRgtObjNo("1"); //其他号码(?)
                        mLLRegisterSchema.setRgtClass("2"); //申请类型(?)
                        mLLRegisterSchema.setCustomerNo("1"); //客户号(?)
                        if(mLLRegisterSchema.getRgtState()==null||"".equals(mLLRegisterSchema.getRgtState())){
	                        if (cOperate.equals("AccInsert")) {
	                            mLLRegisterSchema.setRgtState("02"); //*案件类型*，账户案件
	                        } else {
	                            mLLRegisterSchema.setRgtState("01"); //*案件类型*，简易案件
	                        }
                        }
                        mLLRegisterSchema.setClmState("20");
                        mLLRegisterSchema.setRemark(tLLReportSchema.getRemark());
                        mLLRegisterSchema.setRgtDate(CurrentDate);
                        mLLRegisterSchema.setOperator(mG.Operator);
                        mLLRegisterSchema.setMngCom(mG.ManageCom);
                        mLLRegisterSchema.setMakeDate(CurrentDate);
                        mLLRegisterSchema.setMakeTime(CurrentTime);
                        mLLRegisterSchema.setModifyDate(CurrentDate);
                        mLLRegisterSchema.setModifyTime(CurrentTime);

                        //同步立案分案表(多条)-------------------------------------Beg
                        LLSubReportDB tLLSubReportDB = new LLSubReportDB();
                        String sql1 = "select * from LLSubReport where"
                                      + " SubRptNo = '" + mRptNo + "'";
                        logger.debug("Start query LLSubReport:" + sql1);
                        LLSubReportSet tLLSubReportSet = tLLSubReportDB.
                                executeQuery(sql1);

                        if (tLLSubReportSet == null &&
                            tLLSubReportSet.size() == 0) {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "dealData";
                            tError.errorMessage = "查询报案分案表失败!";
                            this.mErrors.addOneError(tError);
                            tReturn = false;
                            return false;
                        } else {

                            mLLCaseSet.clear();
                            for (int j = 1; j <= tLLSubReportSet.size(); j++) {
                                LLSubReportSchema tLLSubReportSchema = new
                                        LLSubReportSchema();
                                tLLSubReportSchema = tLLSubReportSet.get(j);
                                LLCaseSchema tLLCaseSchema = new LLCaseSchema();

                                tLLCaseSchema.setCaseNo(tLLSubReportSchema.
                                        getSubRptNo()); //赔案号
                                tLLCaseSchema.setRgtNo(tLLSubReportSchema.
                                        getSubRptNo()); //赔案号
                                tLLCaseSchema.setCustomerNo(tLLSubReportSchema.
                                        getCustomerNo()); //出险人编码
                                //tLLCaseSchema.setSecondUWFlag(String.valueOf(j));
                                tLLCaseSchema.setSecondUWFlag(tLLSubReportSchema.getCondoleFlag());
                                //取得用户补充信息
                                LDPersonDB tLDPersonDB = new LDPersonDB();
                                tLDPersonDB.setCustomerNo(tLLSubReportSchema.
                                        getCustomerNo());
                                tLDPersonDB.getInfo();

                                String strSql =
                                        "select trunc(months_between(sysdate,Birthday)/12) from LDPerson where "
                                        + "CustomerNo = '"
                                        + tLLSubReportSchema.getCustomerNo() +
                                        "'";
                                ExeSQL exesql = new ExeSQL();
                                String tAge = exesql.getOneValue(strSql);

                                tLLCaseSchema.setCustomerAge(tAge); //出险人年龄
                                tLLCaseSchema.setCustomerSex(tLDPersonDB.getSex()); //出险人性别
                                tLLCaseSchema.setAccDate(tLLSubReportSchema.
                                        getAccDate()); //出险日期
                                tLLCaseSchema.setAccidentDetail(
                                        tLLSubReportSchema.getAccidentDetail()); //出险细节
                                tLLCaseSchema.setCureDesc(tLLSubReportSchema.
                                        getCureDesc()); //治疗情况
                                tLLCaseSchema.setHospitalCode(
                                        tLLSubReportSchema.getHospitalCode()); //医院代码
                                if (cOperate.equals("AccInsert")) {
                                    tLLCaseSchema.setRgtType("02"); //案件类型，普通案件
                                }else
                                {
                                    tLLCaseSchema.setRgtType("01"); //案件类型，普通案件
                                }
                                tLLCaseSchema.setRgtState("20"); //案件状态
                                tLLCaseSchema.setAffixConclusion("0"); //单证齐全标志
                                tLLCaseSchema.setEditFlag("0"); //重要信息修改标志
                                tLLCaseSchema.setRgtDate(CurrentDate);
                                tLLCaseSchema.setSurveyFlag(tLLSubReportSchema.
                                        getSurveyFlag()); //调查报告标志
                                tLLCaseSchema.setSubmitFlag(tLLSubReportSchema.
                                        getSubmitFlag()); //发起呈报标志
                                tLLCaseSchema.setCondoleFlag(tLLSubReportSchema.
                                        getCondoleFlag()); //提起慰问标志
                                tLLCaseSchema.setAccResult1(tLLSubReportSchema.
                                        getAccResult1()); //ICD10主代码
                                tLLCaseSchema.setAccResult2(tLLSubReportSchema.
                                        getAccResult2()); //ICD10子代码
                                tLLCaseSchema.setAccdentDesc(tLLSubReportSchema.
                                        getAccDesc());
                                tLLCaseSchema.setRemark(tLLSubReportSchema.
                                        getRemark());
                                
                                //2009-04-24 zhangzheng 增加受益人信息
                                tLLCaseSchema.setBnfName(tLLSubReportSchema.getBnfName()); //受益人姓名
                                tLLCaseSchema.setBankCode(tLLSubReportSchema.getBankCode()); //银行编码
                                tLLCaseSchema.setBankAccNo(tLLSubReportSchema.getBankAccNo()); //银行账号
                                tLLCaseSchema.setCaseGetMode(tLLSubReportSchema.getCaseGetMode());//保险金领取方式
                                tLLCaseSchema.setRelationToInsured(tLLSubReportSchema.getRelationToInsured());//受益人与被保人关系
                                tLLCaseSchema.setBnfIDNo(tLLSubReportSchema.getBnfIDNo());//受益人证件号码
                                tLLCaseSchema.setBnfIDType(tLLSubReportSchema.getBnfIDType());//受益人证件类型
                                tLLCaseSchema.setBnfSex(tLLSubReportSchema.getBnfSex());//受益人性别
                                tLLCaseSchema.setBirthday(tLLSubReportSchema.getBirthday());//受益人出生日期
                                tLLCaseSchema.setBnfAccName(tLLSubReportSchema.getBnfAccName());//受益人出生日期
                                
                                tLLCaseSchema.setOperator(mG.Operator);
                                tLLCaseSchema.setMngCom(mG.ManageCom);
                                tLLCaseSchema.setMakeDate(CurrentDate);
                                tLLCaseSchema.setMakeTime(CurrentTime);
                                tLLCaseSchema.setModifyDate(CurrentDate);
                                tLLCaseSchema.setModifyTime(CurrentTime);

                                mLLCaseSet.add(tLLCaseSchema);
                            }
                        }

                        //立案理赔类型表(多条)-------------------------------------Beg
                        LLReportReasonDB tLLReportReasonDB = new
                                LLReportReasonDB();
                        String sql2 = "select * from LLReportReason where"
                                      + " rpno = '" + mRptNo + "'";
                        logger.debug("Start query LLReportReason:" + sql2);
                        LLReportReasonSet tLLReportReasonSet =
                                tLLReportReasonDB.executeQuery(sql2);
                        if (tLLReportReasonSet == null &&
                            tLLReportReasonSet.size() == 0) {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "dealData";
                            tError.errorMessage = "查询报案理赔类型表失败!";
                            this.mErrors.addOneError(tError);
                            tReturn = false;
                            return false;
                        } else {
                            mLLAppClaimReasonSet.clear();
                            for (int i = 1; i <= tLLReportReasonSet.size(); i++) {
                                LLReportReasonSchema tLLReportReasonSchema = new
                                        LLReportReasonSchema();
                                tLLReportReasonSchema = tLLReportReasonSet.get(
                                        i);
                                LLAppClaimReasonSchema tLLAppClaimReasonSchema = new
                                        LLAppClaimReasonSchema();

                                tLLAppClaimReasonSchema.setCaseNo(
                                        tLLReportReasonSchema.getRpNo());
                                tLLAppClaimReasonSchema.setRgtNo(
                                        tLLReportReasonSchema.getRpNo());
                                tLLAppClaimReasonSchema.setCustomerNo(
                                        tLLReportReasonSchema.getCustomerNo()); //出险人编码
                                tLLAppClaimReasonSchema.setReasonCode(
                                        tLLReportReasonSchema.getReasonCode()); //理赔类型
                                tLLAppClaimReasonSchema.setReason(
                                        tLLReportReasonSchema.getReason()); //类型
                                tLLAppClaimReasonSchema.setReasonType(
                                        tLLReportReasonSchema.getReasonType()); //原因
                                tLLAppClaimReasonSchema.setAffixGetDate(
                                        tLLReportReasonSchema.getAffixGetDate()); //材料齐备日期
                                tLLAppClaimReasonSchema.setOperator(mG.Operator);
                                tLLAppClaimReasonSchema.setMngCom(mG.ManageCom);
                                tLLAppClaimReasonSchema.setMakeDate(CurrentDate);
                                tLLAppClaimReasonSchema.setMakeTime(CurrentTime);
                                tLLAppClaimReasonSchema.setModifyDate(
                                        CurrentDate);
                                tLLAppClaimReasonSchema.setModifyTime(
                                        CurrentTime);
                                mLLAppClaimReasonSet.add(
                                        tLLAppClaimReasonSchema);
                            }
                        }

                        //附件表-------------------------------------------------Beg
                        LLReportAffixDB tLLReportAffixDB = new LLReportAffixDB();
                        String sql3 = "select * from LLReportAffix where"
                                      + " RptNo = '" + mRptNo + "'";
                        logger.debug("Start query LLReportAffix:" + sql3);
                        LLReportAffixSet tLLReportAffixSet =
                                tLLReportAffixDB.executeQuery(sql3);
                        if (tLLReportAffixSet == null &&
                            tLLReportAffixSet.size() == 0) {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "dealData";
                            tError.errorMessage = "查询报案理赔类型表失败!";
                            this.mErrors.addOneError(tError);
                            tReturn = false;
                            return false;
                        } else {
                            mLLAffixSet.clear();
                            for (int i = 1; i <= tLLReportAffixSet.size(); i++) {
                                LLReportAffixSchema tLLReportAffixSchema = new
                                        LLReportAffixSchema();
                                tLLReportAffixSchema = tLLReportAffixSet.get(i);
                                LLAffixSchema tLLAffixSchema = new
                                        LLAffixSchema();

                                tLLAffixSchema.setCaseNo(
                                        tLLReportAffixSchema.getRptNo());
                                tLLAffixSchema.setRgtNo(
                                        tLLReportAffixSchema.getRptNo());
                                tLLAffixSchema.setAffixNo(
                                        tLLReportAffixSchema.getAffixNo()); //附件号码
                                tLLAffixSchema.setSerialNo(tLLReportAffixSchema.
                                        getSerialNo()); //流水号
                                tLLAffixSchema.setAffixName(
                                        tLLReportAffixSchema.getAffixName()); //附件名称
                                tLLAffixSchema.setAffixCode(
                                        tLLReportAffixSchema.getAffixCode()); //附件代码
                                tLLAffixSchema.setAffixType(
                                        tLLReportAffixSchema.getAffixType()); //附件类型
                                tLLAffixSchema.setCustomerNo(
                                        tLLReportAffixSchema.getCustomerNo()); //出险人编码
                                tLLAffixSchema.setReasonCode(
                                        tLLReportAffixSchema.getReasonCode()); //原因代码
                                tLLAffixSchema.setProperty(tLLReportAffixSchema.
                                        getProperty()); //材料类型(是否原件)
                                tLLAffixSchema.setReadyCount(
                                        tLLReportAffixSchema.getReadyCount()); //齐备件数
                                tLLAffixSchema.setShortCount(
                                        tLLReportAffixSchema.getShortCount()); //缺少件数
                                tLLAffixSchema.setSupplyDate(
                                        tLLReportAffixSchema.getSupplyDate());
                                tLLAffixSchema.setFileEndDate(
                                        tLLReportAffixSchema.getFileEndDate());
                                tLLAffixSchema.setFileStartDate(
                                        tLLReportAffixSchema.getFileStartDate());
                                tLLAffixSchema.setFileSummary(
                                        tLLReportAffixSchema.getFileSummary());
                                tLLAffixSchema.setRemark(tLLReportAffixSchema.
                                        getRemark());
                                tLLAffixSchema.setNeedFlag(tLLReportAffixSchema.
                                        getNeedFlag());
                                tLLAffixSchema.setOperator(mG.
                                        Operator);
                                tLLAffixSchema.setMngCom(mG.
                                        ManageCom);
                                tLLAffixSchema.setMakeDate(
                                        CurrentDate);
                                tLLAffixSchema.setMakeTime(
                                        CurrentTime);
                                tLLAffixSchema.setModifyDate(
                                        CurrentDate);
                                tLLAffixSchema.setModifyTime(
                                        CurrentTime);
                                mLLAffixSet.add(tLLAffixSchema);
                            }
                        }
                        //更新报案表中立案标志-------------------------------------Beg
                        mLLReportSchema = new LLReportSchema();
                        LLReportDB ttLLReportDB = new LLReportDB();
                        ttLLReportDB.setRptNo(mRptNo);
                        ttLLReportDB.getInfo();
                        mLLReportSchema = ttLLReportDB.getSchema();
                        mLLReportSchema.setRgtFlag("20");
                    }
                    //打包提交数据
                    map.put(mLLRegisterSchema, "DELETE&INSERT");
                    map.put(mLLCaseSet, "DELETE&INSERT");
                    map.put(mLLAppClaimReasonSet, "DELETE&INSERT");
                    map.put(mLLAffixSet, "DELETE&INSERT");
                    map.put(mLLReportSchema, "DELETE&INSERT");

                    tReturn = true;
                    return true;
                }
            }
            else {//为非理算理赔的处理 -- liuyu-071011

                      //查询出险人是否存在于立案
                      String strSql = "select CaseNo from LLCase where "
                                      + "CustomerNo = '"
                                      + mLLCaseSchema.getCustomerNo()
                                      + "' and CaseNo = '"
                                      + mLLCaseSchema.getCaseNo() + "'";
                      ExeSQL exesql = new ExeSQL();
                      String tResult = exesql.getOneValue(strSql);
                      if (tResult.length() > 0) {
                          // @@错误处理
                          CError tError = new CError();
                          tError.moduleName = "LLClaimRegisterBL";
                          tError.functionName = "dealData";
                          tError.errorMessage = "出险人已存在于此立案中!";
                          this.mErrors.addOneError(tError);
                          return false;
                      } else {
                          //处理报案相关表，处理完返回的数据打包到map
                          if (!addReport()) {
                              //@@错误处理
                              CError tError = new CError();
                              tError.moduleName = "LLClaimRegisterBL";
                              tError.functionName = "dealData";
                              tError.errorMessage = "为报案相关表准备数据失败!";
                              this.mErrors.addOneError(tError);
                              tReturn = false;
                              return false;
                          }

                          if (!addInsuAcc(cOperate)) {
                              //@@错误处理
                              CError tError = new CError();
                              tError.moduleName = "LLClaimRegisterBL";
                              tError.functionName = "dealData";
                              tError.errorMessage = "账户相关表准备数据失败!";
                              this.mErrors.addOneError(tError);
                              tReturn = false;
                              return false;
                          }

                          //立案分案表
                          mLLCaseSchema.setRgtNo(mLLCaseSchema.getCaseNo()); //赔案号
                          if (cOperate.equals("AccInsert")) {
                              mLLCaseSchema.setRgtType("02"); //案件类型,账户案件
                          } else {
                              mLLCaseSchema.setRgtType("01"); //案件类型,简易案件
                          }
                          mLLCaseSchema.setRgtState("20"); //案件状态
                          mLLCaseSchema.setAffixConclusion("0"); //单证齐全标志
                          mLLCaseSchema.setEditFlag("0"); //重要信息修改标志
                          mLLCaseSchema.setRgtDate(CurrentDate);
                          mLLCaseSchema.setOperator(mG.Operator);
                          mLLCaseSchema.setMngCom(mG.ManageCom);
                          mLLCaseSchema.setMakeDate(CurrentDate);
                          mLLCaseSchema.setMakeTime(CurrentTime);
                          mLLCaseSchema.setModifyDate(CurrentDate);
                          mLLCaseSchema.setModifyTime(CurrentTime);
                          //立案理赔类型表(多条添加)
                          for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
                              mLLAppClaimReasonSet.get(i).setOperator(mG.
                                      Operator);
                              mLLAppClaimReasonSet.get(i).setMngCom(mG.
                                      ManageCom);
                              mLLAppClaimReasonSet.get(i).setMakeDate(
                                      CurrentDate);
                              mLLAppClaimReasonSet.get(i).setMakeTime(
                                      CurrentTime);
                              mLLAppClaimReasonSet.get(i).setModifyDate(
                                      CurrentDate);
                              mLLAppClaimReasonSet.get(i).setModifyTime(
                                      CurrentTime);
                          }
                          map.put(mLLAppClaimReasonSet, "INSERT");
                          map.put(mLLCaseSchema, "INSERT");
                          tReturn = true;
                          return true;
                      }
                  }
            }
        }
        if (cOperate.equals("ForAccInsert")) {
            if (!addInsuAcc(cOperate)) {
                //@@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimRegisterBL";
                tError.functionName = "dealData";
                tError.errorMessage = "账户相关表准备数据失败!";
                this.mErrors.addOneError(tError);
                tReturn = false;
                return false;
            }

            mLLRegisterSchema.setRgtObjNo("1");
            mLLRegisterSchema.setRgtClass("2");
            mLLRegisterSchema.setRgtState("02"); //*案件类型*，简易案件
            mLLRegisterSchema.setClmState("20");
            mLLRegisterSchema.setCustomerNo("1");
            mLLRegisterSchema.setRgtDate(CurrentDate);
            mLLRegisterSchema.setOperator(mG.Operator);
            mLLRegisterSchema.setMngCom(mG.ManageCom);
            mLLRegisterSchema.setMakeDate(CurrentDate);
            mLLRegisterSchema.setMakeTime(CurrentTime);
            mLLRegisterSchema.setModifyDate(CurrentDate);
            mLLRegisterSchema.setModifyTime(CurrentTime);

            mLLCaseSchema.setOperator(mG.Operator);
            mLLCaseSchema.setMngCom(mG.ManageCom);
            mLLCaseSchema.setMakeDate(CurrentDate);
            mLLCaseSchema.setMakeTime(CurrentTime);
            mLLCaseSchema.setModifyDate(CurrentDate);
            mLLCaseSchema.setModifyTime(CurrentTime);

            for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
                mLLAppClaimReasonSet.get(i).setRgtNo(mRptNo); //分案号=赔案号
                mLLAppClaimReasonSet.get(i).setOperator(mG.Operator);
                mLLAppClaimReasonSet.get(i).setMngCom(mG.ManageCom);
                mLLAppClaimReasonSet.get(i).setMakeDate(CurrentDate);
                mLLAppClaimReasonSet.get(i).setMakeTime(CurrentTime);
                mLLAppClaimReasonSet.get(i).setModifyDate(CurrentDate);
                mLLAppClaimReasonSet.get(i).setModifyTime(CurrentTime);
            }

            mLLReportSchema = new LLReportSchema();
            LLReportDB ttLLReportDB = new LLReportDB();
            ttLLReportDB.setRptNo(mRptNo);
            ttLLReportDB.getInfo();
            mLLReportSchema = ttLLReportDB.getSchema();
            mLLReportSchema.setRgtFlag("20");

//          帐单总表
            if (mLLFeeMainSchema != null) {
                if (mLLFeeMainSchema.getMainFeeNo() != null &&
                    mLLFeeMainSchema.getMainFeeNo().equals("0000000000")) { //非明细帐单
                    mLLFeeMainSchema.setCaseNo(mRptNo);
                    mLLFeeMainSchema.setClmNo(mRptNo);
                    mLLFeeMainSchema.setMngCom(mG.ManageCom);
                    mLLFeeMainSchema.setOperator(mG.Operator);
                    mLLFeeMainSchema.setMakeDate(CurrentDate);
                    mLLFeeMainSchema.setMakeTime(CurrentTime);
                    mLLFeeMainSchema.setModifyDate(CurrentDate);
                    mLLFeeMainSchema.setModifyTime(CurrentTime);
                    map.put(mLLFeeMainSchema, "DELETE&INSERT");
                }
            }
            //帐单明细表
            if (mLLCaseReceiptSchema != null) {
                if (mLLCaseReceiptSchema.getMainFeeNo() != null &&
                    mLLCaseReceiptSchema.getMainFeeNo().equals(
                            "0000000000")) { //非明细录入的帐单明细
                    String FeeDetailNo = PubFun1.CreateMaxNo(
                            "FeeDetailNo",
                            mG.ManageCom);
                    logger.debug("-----生成流水线号--- " + FeeDetailNo);
                    mLLCaseReceiptSchema.setClmNo(mRptNo);
                    mLLCaseReceiptSchema.setCaseNo(mRptNo);
                    mLLCaseReceiptSchema.setRgtNo(mRptNo);
                    mLLCaseReceiptSchema.setFeeDetailNo(FeeDetailNo);
                    mLLCaseReceiptSchema.setMngCom(mG.ManageCom);
                    mLLCaseReceiptSchema.setOperator(mG.Operator);
                    mLLCaseReceiptSchema.setMakeDate(CurrentDate);
                    mLLCaseReceiptSchema.setMakeTime(CurrentTime);
                    mLLCaseReceiptSchema.setModifyDate(CurrentDate);
                    mLLCaseReceiptSchema.setModifyTime(CurrentTime);
                    map.put(mLLCaseReceiptSchema, "DELETE&INSERT");
                }
            }

            map.put(mLLRegisterSchema, "DELETE&INSERT");
            map.put(mLLCaseSchema, "DELETE&INSERT");
            map.put(mLLAppClaimReasonSet, "DELETE&INSERT");
            map.put(mLLReportSchema, "DELETE&INSERT");

            tReturn = true;
            return true;
        }
        //更新纪录
        if (cOperate.equals("UPDATE") || cOperate.equals("ACCUPDATE")) {

            logger.debug("----------UPDATE dealData----------");
            if (!addInsuAcc(cOperate)) {
                //@@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimRegisterBL";
                tError.functionName = "dealData";
                tError.errorMessage = "账户相关表更新数据失败!";
                this.mErrors.addOneError(tError);
                tReturn = false;
                return false;
            }
            //更新立案信息
            LLRegisterDB tLLRegisterDB = new LLRegisterDB();
            tLLRegisterDB.setRgtNo(mLLRegisterSchema.getRgtNo());
            tLLRegisterDB.getInfo();
            //mLLRegisterSchema.setRgtObj(tLLRegisterDB.getRgtObj()); //号码类型(?)
            mLLRegisterSchema.setRgtObjNo(tLLRegisterDB.getRgtObjNo()); //其他号码(?)
            mLLRegisterSchema.setRgtClass(tLLRegisterDB.getRgtClass()); //申请类型(1个人,2团体)
            mLLRegisterSchema.setCustomerNo(tLLRegisterDB.getCustomerNo()); //客户号(?)
            mLLRegisterSchema.setRgtState(tLLRegisterDB.getRgtState()); //*案件类型*
            mLLRegisterSchema.setClmState(tLLRegisterDB.getClmState());
            mLLRegisterSchema.setRgtDate(tLLRegisterDB.getRgtDate());
            mLLRegisterSchema.setMngCom(tLLRegisterDB.getMngCom());
            mLLRegisterSchema.setOperator(tLLRegisterDB.getOperator());
            mLLRegisterSchema.setMakeDate(tLLRegisterDB.getMakeDate());
            mLLRegisterSchema.setMakeTime(tLLRegisterDB.getMakeTime());
            mLLRegisterSchema.setModifyDate(CurrentDate);
            mLLRegisterSchema.setModifyTime(CurrentTime);
            //更新报案信息
            /************************jinsh20070403点修改时将信息保存到报案信息*******************************/
            LLReportDB tLLReportDB = new LLReportDB();
            tLLReportDB.setRptNo(mLLReportSchema.getRptNo());
            tLLReportDB.getInfo();
            mLLReportSchema.setOperator(tLLReportDB.getOperator());  //应保存原来的报案操作员和时间
            mLLReportSchema.setMakeDate(tLLReportDB.getMakeDate());
            mLLReportSchema.setMakeTime(tLLReportDB.getMakeTime());
            mLLReportSchema.setAccidentCourse(tLLReportDB.getAccidentCourse());
            mLLReportSchema.setAccidentSite(tLLReportDB.getAccidentSite());
            mLLReportSchema.setAvaiFlag(tLLReportDB.getAvaiFlag());
            mLLReportSchema.setAvaliReason(tLLReportDB.getAvaliReason());
            mLLReportSchema.setCaseEndDate(tLLReportDB.getCaseEndDate());
            mLLReportSchema.setCaseNoDate(tLLReportDB.getCaseNoDate());
            mLLReportSchema.setEmail(tLLReportDB.getEmail());
            mLLReportSchema.setMngCom(tLLReportDB.getMngCom());
            mLLReportSchema.setNoRgtReason(tLLReportDB.getNoRgtReason());
            mLLReportSchema.setPeoples2(tLLReportDB.getPeoples2());
            mLLReportSchema.setPostCode(tLLReportDB.getPostCode());
            mLLReportSchema.setRelation(tLLReportDB.getRelation());
            mLLReportSchema.setRemark(tLLReportDB.getRemark());
            mLLReportSchema.setReturnMode(tLLReportDB.getReturnMode());
            mLLReportSchema.setRgtClass(tLLReportDB.getRgtClass());
            mLLReportSchema.setRgtFlag(tLLReportDB.getRgtFlag());
            //mLLReportSchema.setRgtObj(tLLReportDB.getRgtObj());
            //mLLReportSchema.setAccidentReason();
            mLLReportSchema.setRgtObjNo(tLLReportDB.getRgtObjNo());
            mLLReportSchema.setRgtReason(tLLReportDB.getRgtReason());
            mLLReportSchema.setRptDate(tLLReportDB.getRptDate());
            mLLReportSchema.setRptMode(tLLReportDB.getRptMode());
            mLLReportSchema.setRptorAddress(tLLReportDB.getRptorAddress());
            mLLReportSchema.setRptorMobile(tLLReportDB.getRptorMobile());
            mLLReportSchema.setRptorName(tLLReportDB.getRptorName());
            mLLReportSchema.setRptorPhone(tLLReportDB.getRptorPhone());
            mLLReportSchema.setModifyDate(CurrentDate);
            mLLReportSchema.setModifyTime(CurrentTime);

            LLSubReportDB tLLSubReportDB = new LLSubReportDB();
            tLLSubReportDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
            tLLSubReportDB.setCustomerNo(mLLSubReportSchema.getCustomerNo());
            tLLSubReportDB.getInfo();
            mLLSubReportSchema.setMngCom(tLLSubReportDB.getMngCom());
            mLLSubReportSchema.setOperator(tLLSubReportDB.getOperator());
            mLLSubReportSchema.setMakeDate(tLLSubReportDB.getMakeDate());
            mLLSubReportSchema.setMakeTime(tLLSubReportDB.getMakeTime());
            mLLSubReportSchema.setModifyDate(CurrentDate);
            mLLSubReportSchema.setModifyTime(CurrentTime);

            deleteSqlUp = " delete from LLReportReason where " + " RpNo = '" +
                          mLLSubReportSchema.getSubRptNo() + "'"
                          + " and CustomerNo = '" +
                          mLLSubReportSchema.getCustomerNo() + "'";

            map.put(deleteSqlUp, "DELETE");

            for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
                LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
                tLLReportReasonDB.setRpNo(mLLReportReasonSet.get(i).getRpNo());
                tLLReportReasonDB.setCustomerNo(mLLReportReasonSet.get(i).
                                                getCustomerNo());
                tLLReportReasonDB.setReasonCode(mLLReportReasonSet.get(i).
                                                getReasonCode());
                tLLReportReasonDB.getInfo();
                if (tLLReportReasonDB.getMakeDate() == null) {
                    mLLReportReasonSet.get(i).setOperator(mG.Operator);
                    mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
                    mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
                    mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
                    mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
                    mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
                    //如有伤残,则生成伤残打印数据
                    String tCode = mLLReportReasonSet.get(i).getReasonCode().
                                   substring(1, 3);
                    if (tCode.equals("01")) {
                        //生成打印数据--伤残鉴定通知书
                        if (!insertLOPRTManager("PCT001")) {
                            return false;
                        }
                    }
                } else {
                    mLLReportReasonSet.get(i).setSchema(tLLReportReasonDB.
                            getSchema());
                }
            }
            map.put(mLLReportSchema, "DELETE&INSERT");
            map.put(mLLSubReportSchema, "DELETE&INSERT");
//                map.put(mLLReportReasonSchema,"DELETE&INSERT");
            map.put(mLLReportReasonSet, "DELETE&INSERT");

            /************************jinsh20070403点修改时将信息保存到报案信息*******************************/

            //更新分案信息表的字段
            LLCaseDB tLLCaseDB = new LLCaseDB();
            tLLCaseDB.setCaseNo(mLLCaseSchema.getCaseNo());
            tLLCaseDB.setCustomerNo(mLLCaseSchema.getCustomerNo());
            tLLCaseDB.getInfo();
            mLLCaseSchema.setRgtNo(mLLCaseSchema.getCaseNo());
            mLLCaseSchema.setSurveyFlag(tLLCaseDB.getSurveyFlag()); //调查报告标志
            mLLCaseSchema.setSubmitFlag(tLLCaseDB.getSubmitFlag()); //发起呈报标志
            mLLCaseSchema.setCondoleFlag(tLLCaseDB.getCondoleFlag()); //提起慰问标志
            if (cOperate.equals("AccInsert") || cOperate.equals("ACCUPDATE")) {
                mLLCaseSchema.setRgtType("02"); //案件类型,账户案件
            } else {
                mLLCaseSchema.setRgtType("01"); //案件类型,简易案件
            }
            mLLCaseSchema.setRgtState("20"); //案件状态
            mLLCaseSchema.setAffixConclusion(tLLCaseDB.getAffixConclusion()); //单证齐全标志
            mLLCaseSchema.setEditFlag(tLLCaseDB.getEditFlag()); //重要信息修改标志
            mLLCaseSchema.setRgtDate(tLLCaseDB.getRgtDate());
            mLLCaseSchema.setMngCom(tLLCaseDB.getMngCom());
            mLLCaseSchema.setOperator(tLLCaseDB.getOperator());
            mLLCaseSchema.setMakeDate(tLLCaseDB.getMakeDate());
            mLLCaseSchema.setMakeTime(tLLCaseDB.getMakeTime());
            mLLCaseSchema.setModifyDate(CurrentDate);
            mLLCaseSchema.setModifyTime(CurrentTime);

            //------------------------------------------------------------------BEG
            //理赔类型表,首先删除所有该分案下的所有理赔类型,然后再添加前台更改后数据
            //------------------------------------------------------------------
            deleteSql = " delete from LLAppClaimReason where "
                        + " RgtNo = '" + mLLCaseSchema.getRgtNo() + "'"
                        + " and CustomerNo = '" + mLLCaseSchema.getCustomerNo() +
                        "'";
            //先删除伤残打印数据
            String tsql = "delete from LOPRTManager where "
                          + " otherno = '" + mRptNo + "'"
                          + " and code = 'PCT001'";
            map.put(tsql, "DELETE");
            for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
                LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
                tLLAppClaimReasonDB.setCaseNo(mLLAppClaimReasonSet.get(i).
                                              getRgtNo());
                tLLAppClaimReasonDB.setRgtNo(mLLAppClaimReasonSet.get(i).
                                             getRgtNo());
                tLLAppClaimReasonDB.setCustomerNo(mLLAppClaimReasonSet.get(i).
                                                  getCustomerNo());
                tLLAppClaimReasonDB.setReasonCode(mLLAppClaimReasonSet.get(i).
                                                  getReasonCode());
                tLLAppClaimReasonDB.getInfo();
                if (tLLAppClaimReasonDB.getMakeDate() == null) {
                    mLLAppClaimReasonSet.get(i).setOperator(mG.Operator);
                    mLLAppClaimReasonSet.get(i).setMngCom(mG.ManageCom);
                    mLLAppClaimReasonSet.get(i).setMakeDate(CurrentDate);
                    mLLAppClaimReasonSet.get(i).setMakeTime(CurrentTime);
                    mLLAppClaimReasonSet.get(i).setModifyDate(CurrentDate);
                    mLLAppClaimReasonSet.get(i).setModifyTime(CurrentTime);
                    //如有伤残,则生成伤残打印数据
                    String tCode = mLLAppClaimReasonSet.get(i).getReasonCode().
                                   substring(1, 3);
                    if (tCode.equals("01")) {
                        //生成打印数据--伤残鉴定通知书
                        if (!insertLOPRTManager("PCT001")) {
                            return false;
                        }
                    }
                } else {
                    mLLAppClaimReasonSet.get(i).setSchema(tLLAppClaimReasonDB.
                            getSchema());
                }
            }

            //修改LLAccident表
            logger.debug("存在的事件号：" + mLLAccidentSchema.getAccNo());
            LLAccidentSet tLLAccidentSet = new LLAccidentSet();
            LLAccidentDB tLLAccidentDB = new LLAccidentDB();
            String AccidentSQL = "select * from llaccident where accno='" +
                                 mLLAccidentSchema.getAccNo() + "'";
            tLLAccidentSet = tLLAccidentDB.executeQuery(AccidentSQL);
            if (tLLAccidentSet.size() > 0) {
                for (int k = 1; k <= tLLAccidentSet.size(); k++) {
                    LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
                    tLLAccidentSchema = tLLAccidentSet.get(k);
                    tLLAccidentSchema.setAccDate(mLLRegisterSchema.
                                                 getAccidentDate());
                    tLLAccidentSchema.setAccType(mLLRegisterSchema.
                                                 getAccidentReason());
                    tLLAccidentSchema.setOperator(mG.Operator);
                    tLLAccidentSchema.setMngCom(mG.ManageCom);
                    tLLAccidentSchema.setModifyDate(CurrentDate);
                    tLLAccidentSchema.setModifyTime(CurrentTime);
                    mLLAccidentSchema.setSchema(tLLAccidentSchema);
                }
            }
            //------------------------------------------------------------------End
            map.put(mLLRegisterSchema, "DELETE&INSERT");
            map.put(mLLCaseSchema, "DELETE&INSERT");
            map.put(deleteSql, "DELETE");
            map.put(mLLAppClaimReasonSet, "DELETE&INSERT");
            map.put(mLLAccidentSchema, "UPDATE");

            tReturn = true;
            return true;
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 修改原因：在帐户案件理赔中添加'删除'按钮，允许用户执行删除操作，其中只能删除案件下的
         *         某个人的相关记录
         * 修 改 人：万泽辉
         * 修改日期：2006-02-16
         * String tOperator 操作标识
         */
//  批量案件借用账户类案件的删除程序 wood
        if (cOperate.equals("ACCDELETE") || cOperate == "ACCDELETE") {
            logger.debug("----------DELETE 开始----------");
            if (!delReport(cOperate)) {
                //@@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimRegisterBL";
                tError.functionName = "dealData";
                tError.errorMessage = "账户相关表删除失败!";
                this.mErrors.addOneError(tError);
                tReturn = false;
                return false;
            }
            tReturn = true;
//            return true;
            logger.debug("----------DELETE 结束----------");
        }
        return tReturn;
//        return true;
    }

    /**
     * 判断事件、报案、立案信息是否存在
     * @todo 1 事件号为空，则报案、立案都不存在；事件号不为空，则判断报案号。
     *       2 报案号为空，则立案不存在；报案号不为空，则查询立案是否存在。
     */
    private void IsExist() {
        //事件是否存在
        if (mLLAccidentSchema.getAccNo().equals("") ||
            mLLAccidentSchema.getAccNo() == null) {
            mIsAccExist = false;
            mIsReportExist = false;
            mIsRegisterExist = false;
        } else {
            //事件存在
            mIsAccExist = true;
            mAccNo = mLLAccidentSchema.getAccNo();
            logger.debug("-----已有事件号= " + mAccNo);
            if (mLLRegisterSchema.getRgtNo().equals("") ||
                mLLRegisterSchema.getRgtNo() == null) {
                //立案不存在
                mIsReportExist = false;
                mIsRegisterExist = false;
            } else {
                //立案存在
                mIsReportExist = true;
                mRptNo = mLLRegisterSchema.getRgtNo();
                logger.debug("-----已有赔案号= " + mRptNo);
                //查询判断立案是否存在
                String strSql = "select RgtNo from LLRegister where "
                                + "RgtNo = " + mLLRegisterSchema.getRgtNo();
                ExeSQL exesql = new ExeSQL();
                String tResult = exesql.getOneValue(strSql);
                if (tResult.length() > 0) {
                    mIsRegisterExist = true;
                } else {
                    mIsRegisterExist = false;
                }
            }
        }
    }

    //账户相关数据生成
    private boolean addInsuAcc(String operator) {
        logger.debug("操作符:" + operator);
        String SerialNo = "";
        if (operator.equals("AccInsert") || operator.equals("ForAccInsert")) {
            try {
                //账户明细表
                logger.debug("mLLClaimAccountSet.size:" +
                                   mLLClaimAccountSet.size());
                //if(mLLClaimAccountSet.size()!=0&&mLLClaimAccountSet.size()==2)
                if (mLLClaimAccountSet.size() != 0) {
                    for (int i = 1; i <= mLLClaimAccountSet.size(); i++) {
                        LLClaimAccountSchema tLLClaimAccountSchema = new
                                LLClaimAccountSchema();
                        tLLClaimAccountSchema = mLLClaimAccountSet.get(i);
                        logger.debug("LLClaimAccount数据,第[" + i + "]条：" +
                                           tLLClaimAccountSchema.encode());
                        String tGrpContNo = "";
                        if (!mLLRegisterSchema.getGrpContNo().equals("")) {
                            tGrpContNo = mLLRegisterSchema.getGrpContNo();
                        } else {
                            if (!tLLClaimAccountSchema.getGrpContNo().equals("")) {
                                tGrpContNo = tLLClaimAccountSchema.getGrpContNo();
                            }
                        }

                        if (tLLClaimAccountSchema.getOtherType().equals("S")) {
                            String tPolNo = ""; //提高查询速度
                            LCInsureAccClassSet tLCInsureAccClassSet = new
                                    LCInsureAccClassSet();
                            LCInsureAccClassDB tLCInsureAccClassDB = new
                                    LCInsureAccClassDB();
                            LCPolDB tLCPolDB = new LCPolDB();
                            tLCPolDB.setGrpContNo(tGrpContNo);
                            tLCPolDB.setInsuredNo(mLLCaseSchema.getCustomerNo());
                            if (tLLClaimAccountSchema.getOtherNo() != null &&
                                !tLLClaimAccountSchema.getOtherNo().equals("")) {
                                tLCPolDB.setRiskCode(tLLClaimAccountSchema.
                                        getOtherNo());
                            }

                            LCPolSet tLCPolSet = tLCPolDB.query();
                            if (tLCPolSet.size() == 1) {
                                //tContNo = tLCPolSet.get(1).getContNo();
                                tPolNo = tLCPolSet.get(1).getPolNo();
                            } else {
                                tLCPolDB.setPolState("1");
                                tLCPolSet.clear();
                                tLCPolSet = tLCPolDB.query();
                                if (tLCPolSet.size() == 1) {
                                    tPolNo = tLCPolSet.get(1).getPolNo();
                                } else {
                                    //@@错误处理
                                    CError tError = new CError();
                                    tError.moduleName = "LLClaimRegisterBL";
                                    tError.functionName = "addInsuAcc";
                                    tError.errorMessage = "该" + tPolNo +
                                            "保单有误!";
                                    this.mErrors.addOneError(tError);
                                    return false;
                                }
                            }

                            String InsuAccSQL =
//                                    "select * from lcinsureaccclass where grpcontno='" +
//                                    tGrpContNo + "'and insuredno='" +
//                                    mLLCaseSchema.getCustomerNo() + "' ";
                                    "select * from lcinsureaccclass where polno='" +
                                    tPolNo + "' ";
//                            if (tLLClaimAccountSchema.getOtherNo() != null &&
//                                !tLLClaimAccountSchema.getOtherNo().equals("")) {
//                                InsuAccSQL += "and riskcode='" +
//                                        tLLClaimAccountSchema.getOtherNo() +
//                                        "'";
//                            }
                            logger.debug("InsuAccSQL:" + InsuAccSQL);
                            tLCInsureAccClassSet = tLCInsureAccClassDB.
                                    executeQuery(
                                            InsuAccSQL);
                            logger.debug("tLCInsureAccClassSet:" +
                                               tLCInsureAccClassSet.size());
                            if (tLCInsureAccClassSet.size() > 0) {
                                for (int k = 1; k <= tLCInsureAccClassSet.size();
                                             k++) {
                                    LCInsureAccClassSchema
                                            tLCInsureAccClassSchema = new
                                            LCInsureAccClassSchema();
                                    LLClaimAccountSchema sLLClaimAccountSchema = new
                                            LLClaimAccountSchema();
                                    SerialNo = PubFun1.CreateMaxNo("SerialNo",
                                            mG.ManageCom);
                                    logger.debug("-----生成流水线号--- " +
                                            SerialNo);
                                    tLCInsureAccClassSchema =
                                            tLCInsureAccClassSet.
                                            get(k);
                                    sLLClaimAccountSchema.setSchema(
                                            tLLClaimAccountSchema);
                                    sLLClaimAccountSchema.setOtherNo(
                                            tLCInsureAccClassSchema.getRiskCode());
                                    sLLClaimAccountSchema.setClmNo(mRptNo);
                                    sLLClaimAccountSchema.setSerialNo(SerialNo);
                                    sLLClaimAccountSchema.setGetDutyCode(
                                            "999999"); //暂存作为临时记录(个人报销额)
                                    sLLClaimAccountSchema.setPolNo(
                                            tLCInsureAccClassSchema.
                                            getPolNo());
                                    sLLClaimAccountSchema.setInsuAccNo(
                                            tLCInsureAccClassSchema.
                                            getInsuAccNo());
                                    sLLClaimAccountSchema.setPayPlanCode(
                                            tLCInsureAccClassSchema.
                                            getPayPlanCode());
                                    sLLClaimAccountSchema.setAccAscription(
                                            tLCInsureAccClassSchema.
                                            getAccAscription());
                                    sLLClaimAccountSchema.setMngCom(
                                            tLCInsureAccClassSchema.
                                            getManageCom());
                                    sLLClaimAccountSchema.setOperator(mG.
                                            Operator);
                                    sLLClaimAccountSchema.setMakeDate(
                                            CurrentDate);
                                    sLLClaimAccountSchema.setMakeTime(
                                            CurrentTime);
                                    sLLClaimAccountSchema.setModifyDate(
                                            CurrentDate);
                                    sLLClaimAccountSchema.setModifyTime(
                                            CurrentTime);
                                    sLLClaimAccountSet.add(
                                            sLLClaimAccountSchema);
                                }
                            }
                        } else if (tLLClaimAccountSchema.getOtherType().equals(
                                "P")) {
                            // String tContNo = ""; //个人合同号
                            String tPolNo2 = ""; //提高查询速度
                            /*这样处理当一个团单下有多个公共账户保单的事后会有问题
                             LCContDB tLCContDB =  new LCContDB();
                             tLCContDB.setGrpContNo(tGrpContNo);
                                                   tLCContDB.setPolType("2");
                             LCContSet tLCContSet = tLCContDB.query();
                                                   if (tLCContSet.size() == 1)
                                                   {
                                tContNo = tLCContSet.get(1).getContNo();
                                                   }else{
                                    //@@错误处理
                                    CError tError = new CError();
                                    tError.moduleName = "LLClaimRegisterBL";
                                    tError.functionName = "addInsuAcc";
                             tError.errorMessage = "该"+tGrpContNo+"保单的公共帐户有误!";
                                    this.mErrors.addOneError(tError);
                                    return false;
                                                   }
                             */
                            LCPolDB tLCPolDB = new LCPolDB();
                            tLCPolDB.setGrpContNo(tGrpContNo);
                            tLCPolDB.setPolTypeFlag("2");
                            if (tLLClaimAccountSchema.getOtherNo() != null &&
                                !tLLClaimAccountSchema.getOtherNo().equals("")) {
                                tLCPolDB.setRiskCode(tLLClaimAccountSchema.
                                        getOtherNo());
                            }
                            LCPolSet tLCPolSet = tLCPolDB.query();
                            if (tLCPolSet.size() == 1) {
                                //tContNo = tLCPolSet.get(1).getContNo();
                                tPolNo2 = tLCPolSet.get(1).getPolNo();
                            } else {
                                //@@错误处理
                                CError tError = new CError();
                                tError.moduleName = "LLClaimRegisterBL";
                                tError.functionName = "addInsuAcc";
                                tError.errorMessage = "该" + tGrpContNo +
                                        "保单的公共帐户有误!";
                                this.mErrors.addOneError(tError);
                                return false;
                            }

                            LCInsureAccClassSet pLCInsureAccClassSet = new
                                    LCInsureAccClassSet();
                            LCInsureAccClassDB pLCInsureAccClassDB = new
                                    LCInsureAccClassDB();
                            String InsuAccPSQL =
                                    "select * from lcinsureaccclass where  polno='" +
                                    tPolNo2 + "' ";
                            pLCInsureAccClassSet = pLCInsureAccClassDB.
                                    executeQuery(
                                            InsuAccPSQL);
                            if (pLCInsureAccClassSet.size() > 0) {
                                for (int j = 1; j <= pLCInsureAccClassSet.size();
                                             j++) {
                                    LCInsureAccClassSchema
                                            pLCInsureAccClassSchema = new
                                            LCInsureAccClassSchema();
                                    LLClaimAccountSchema pLLClaimAccountSchema = new
                                            LLClaimAccountSchema();
                                    SerialNo = PubFun1.CreateMaxNo("SerialNo",
                                            mG.ManageCom);
                                    logger.debug("-----生成流水线号--- " +
                                            SerialNo);
                                    pLCInsureAccClassSchema =
                                            pLCInsureAccClassSet.
                                            get(j);
                                    pLLClaimAccountSchema.setSchema(
                                            tLLClaimAccountSchema);
                                    pLLClaimAccountSchema.setOtherNo(
                                            pLCInsureAccClassSchema.getRiskCode());
                                    pLLClaimAccountSchema.setClmNo(mRptNo);
                                    pLLClaimAccountSchema.setSerialNo(SerialNo);
                                    pLLClaimAccountSchema.setGetDutyCode(
                                            "999998"); //暂存作为临时记录(团体报销额)
                                    pLLClaimAccountSchema.setPolNo(
                                            pLCInsureAccClassSchema.getPolNo());
                                    pLLClaimAccountSchema.setInsuAccNo(
                                            pLCInsureAccClassSchema.
                                            getInsuAccNo());
                                    pLLClaimAccountSchema.setPayPlanCode(
                                            pLCInsureAccClassSchema.
                                            getPayPlanCode());
                                    pLLClaimAccountSchema.setAccAscription(
                                            pLCInsureAccClassSchema.
                                            getAccAscription());
                                    pLLClaimAccountSchema.setMngCom(
                                            pLCInsureAccClassSchema.
                                            getManageCom());
                                    pLLClaimAccountSchema.setOperator(mG.
                                            Operator);
                                    pLLClaimAccountSchema.setMakeDate(
                                            CurrentDate);
                                    pLLClaimAccountSchema.setMakeTime(
                                            CurrentTime);
                                    pLLClaimAccountSchema.setModifyDate(
                                            CurrentDate);
                                    pLLClaimAccountSchema.setModifyTime(
                                            CurrentTime);
                                    sLLClaimAccountSet.add(
                                            pLLClaimAccountSchema);

                                }
                            }
                        }
                    }
                    //add by wood 由于允许多次导入，所以导入前先删除已有数据070618
                    map.put("delete LLClaimAccount where clmno='" + mRptNo +
                            "' and declineno='" +
                            sLLClaimAccountSet.get(1).getDeclineNo() + "'",
                            "DELETE");
                    //********************************************************************
                     map.put(sLLClaimAccountSet, "DELETE&INSERT");
                } else { //liuyu070611
                        // add by wood 由于允许多次导入，所以导入前先删除已有数据070618
                    map.put("delete LLClaimAccount where clmno='" + mRptNo +"' ", "DELETE");

                    logger.debug("start insert do LLClaimAccount-----");
                    String insertsql = "select a.customerno,b.riskcode,a.AccDate,b.grpcontno from llsubreport a, llreport b where a.subrptno=b.rptno and b.rptno='" +
                                       mRptNo + "' ";
                    ExeSQL tExeSQL = new ExeSQL();
                    SSRS tSSRS = new SSRS();
                    tSSRS = tExeSQL.execSQL(insertsql);
                    for (int k = 1; k <= tSSRS.getMaxRow(); k++) {

                        LCInsureAccClassSet tLCInsureAccClassSet = new
                                LCInsureAccClassSet();
                        LCInsureAccClassDB tLCInsureAccClassDB = new
                                LCInsureAccClassDB();

                        LCPolDB tLCPolDB = new LCPolDB();
                        tLCPolDB.setGrpContNo(tSSRS.GetText(k, 4));
                        tLCPolDB.setInsuredNo(tSSRS.GetText(k, 1));
                        //add by wood 如果不加险种号，得到Set不唯一
                        tLCPolDB.setRiskCode(tSSRS.GetText(k, 2));
                        LCPolSet tLCPolSet = tLCPolDB.query();
                        String tPolNo = ""; //个人合同号
                        if (tLCPolSet.size() == 1) {
                            tPolNo = tLCPolSet.get(1).getPolNo();
                        } else {
                            tLCPolDB.setPolState("1");
                            tLCPolSet.clear();
                            tLCPolSet = tLCPolDB.query();
                            if (tLCPolSet.size() == 1) {
                                tPolNo = tLCPolSet.get(1).getPolNo();
                            } else {
                                //@@错误处理
                                CError tError = new CError();
                                tError.moduleName = "LLClaimRegisterBL";
                                tError.functionName = "addInsuAcc";
                                tError.errorMessage = "该" + tPolNo +
                                        "保单有误!";
                                this.mErrors.addOneError(tError);
                                return false;
                                }
                        }
                        String InsuAccSQL =
                                "select * from lcinsureaccclass where polno='" +
                                tPolNo + "' ";
                        logger.debug("InsuAccSQL:" + InsuAccSQL);
                        tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(
                                InsuAccSQL);
                        logger.debug("tLCInsureAccClassSet:" +
                                           tLCInsureAccClassSet.size());
                        if (tLCInsureAccClassSet.size() > 0) {
                            for (int j = 1; j <= tLCInsureAccClassSet.size(); j++) {
                                LLClaimAccountSchema sLLClaimAccountSchema = new
                                        LLClaimAccountSchema();

                                LCInsureAccClassSchema tLCInsureAccClassSchema = new
                                        LCInsureAccClassSchema();
                                tLCInsureAccClassSchema = tLCInsureAccClassSet.
                                        get(j);

                                SerialNo = PubFun1.CreateMaxNo("SerialNo",
                                        mG.ManageCom);
                                logger.debug("-----生成流水线号--- " + SerialNo);

                                sLLClaimAccountSchema.setClmNo(mRptNo); //分案号=报案号=赔案号
                                sLLClaimAccountSchema.setDeclineNo(tSSRS.
                                        GetText(k, 1)); //出险客户号
                                //sLLClaimAccountSchema.setOtherNo(tSSRS.GetText(k, 2));
                                sLLClaimAccountSchema.setAccNo(String.valueOf(j));
                                sLLClaimAccountSchema.setOtherType("S"); //S个单P团单
                                sLLClaimAccountSchema.setPayDate(tSSRS.GetText(
                                        k, 3));
                                sLLClaimAccountSchema.setGrpContNo(tSSRS.
                                        GetText(k, 4));
                                sLLClaimAccountSchema.setAccPayMoney("");
                                sLLClaimAccountSchema.setSerialNo(SerialNo);
                                sLLClaimAccountSchema.setOtherNo(
                                        tLCInsureAccClassSchema.getRiskCode());
                                sLLClaimAccountSchema.setGetDutyCode("999999"); //暂存作为临时记录(个人报销额)
                                sLLClaimAccountSchema.setPolNo(
                                        tLCInsureAccClassSchema.
                                        getPolNo());
                                sLLClaimAccountSchema.setInsuAccNo(
                                        tLCInsureAccClassSchema.getInsuAccNo());
                                sLLClaimAccountSchema.setPayPlanCode(
                                        tLCInsureAccClassSchema.getPayPlanCode());
                                sLLClaimAccountSchema.setAccAscription(
                                        tLCInsureAccClassSchema.
                                        getAccAscription());
                                sLLClaimAccountSchema.setMngCom(
                                        tLCInsureAccClassSchema.
                                        getManageCom());
                                sLLClaimAccountSchema.setOperator(mG.Operator);
                                sLLClaimAccountSchema.setMakeDate(CurrentDate);
                                sLLClaimAccountSchema.setMakeTime(CurrentTime);
                                sLLClaimAccountSchema.setModifyDate(CurrentDate);
                                sLLClaimAccountSchema.setModifyTime(CurrentTime);
                                sLLClaimAccountSet.add(sLLClaimAccountSchema);

                            }
                        }

//公共帐户信息
                        LCPolDB ttLCPolDB = new LCPolDB();
                        ttLCPolDB.setGrpContNo(tSSRS.GetText(k, 4));
                        ttLCPolDB.setPolTypeFlag("2");
                        if (mLLRegisterSchema.getRiskCode() != null &&
                            !mLLRegisterSchema.getRiskCode().equals("")) {
                            ttLCPolDB.setRiskCode(mLLRegisterSchema.getRiskCode());
                        }
                        LCPolSet ttLCPolSet = ttLCPolDB.query();
                        String ttPolNo = ""; //个人合同号
                        if (ttLCPolSet.size() == 1) {
                            ttPolNo = ttLCPolSet.get(1).getPolNo();
                        } else {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "addInsuAcc";
                            tError.errorMessage = "该" + tSSRS.GetText(k, 4) +
                                                  "保单的公共帐户有误!";
                            this.mErrors.addOneError(tError);
                            return false;
                        }

                        LCInsureAccClassSet pLCInsureAccClassSet = new
                                LCInsureAccClassSet();
                        LCInsureAccClassDB pLCInsureAccClassDB = new
                                LCInsureAccClassDB();
                        String InsuAccPSQL =
                                "select * from lcinsureaccclass where  polno='" +
                                ttPolNo + "' ";
                        pLCInsureAccClassSet = pLCInsureAccClassDB.executeQuery(
                                InsuAccPSQL);
                        if (pLCInsureAccClassSet.size() > 0) {
                            for (int j = 1; j <= pLCInsureAccClassSet.size(); j++) {
                                LLClaimAccountSchema pLLClaimAccountSchema = new
                                        LLClaimAccountSchema();

                                LCInsureAccClassSchema pLCInsureAccClassSchema = new
                                        LCInsureAccClassSchema();

                                SerialNo = PubFun1.CreateMaxNo("SerialNo",
                                        mG.ManageCom);
                                logger.debug("-----生成流水线号--- " + SerialNo);
                                pLCInsureAccClassSchema = pLCInsureAccClassSet.
                                        get(j);

                                pLLClaimAccountSchema.setClmNo(mRptNo); //分案号=报案号=赔案号
                                pLLClaimAccountSchema.setDeclineNo(tSSRS.
                                        GetText(k, 1)); //出险客户号
                                pLLClaimAccountSchema.setOtherType("P"); //S个单P团单
                                pLLClaimAccountSchema.setPayDate(tSSRS.GetText(
                                        k, 3));
                                pLLClaimAccountSchema.setGrpContNo(tSSRS.
                                        GetText(k, 4));
                                pLLClaimAccountSchema.setAccPayMoney("");
                                pLLClaimAccountSchema.setOtherNo(
                                        pLCInsureAccClassSchema.getRiskCode());
                                pLLClaimAccountSchema.setAccNo(String.valueOf(j));
                                pLLClaimAccountSchema.setSerialNo(SerialNo);
                                pLLClaimAccountSchema.setGetDutyCode("999998"); //暂存作为临时记录(团体报销额)
                                pLLClaimAccountSchema.setPolNo(
                                        pLCInsureAccClassSchema.getPolNo());
                                pLLClaimAccountSchema.setInsuAccNo(
                                        pLCInsureAccClassSchema.getInsuAccNo());
                                pLLClaimAccountSchema.setPayPlanCode(
                                        pLCInsureAccClassSchema.getPayPlanCode());
                                pLLClaimAccountSchema.setAccAscription(
                                        pLCInsureAccClassSchema.
                                        getAccAscription());
                                pLLClaimAccountSchema.setMngCom(
                                        pLCInsureAccClassSchema.getManageCom());
                                pLLClaimAccountSchema.setOperator(mG.Operator);
                                pLLClaimAccountSchema.setMakeDate(CurrentDate);
                                pLLClaimAccountSchema.setMakeTime(CurrentTime);
                                pLLClaimAccountSchema.setModifyDate(CurrentDate);
                                pLLClaimAccountSchema.setModifyTime(CurrentTime);
                                pLLClaimAccountSet.add(pLLClaimAccountSchema);

                            }
                        }
                        //********************************************************************
                         map.put(sLLClaimAccountSet, "DELETE&INSERT");
                        map.put(pLLClaimAccountSet, "DELETE&INSERT");
                    }

                }
                /**==========================================================================
                 *  修改原因：只是接受[账户案件理赔]出险人倒入操作
                 *  修 改 人：万泽辉
                 *  修改日期：2006-01-17
                 ===========================================================================
                 */
                //帐单总表
                if (mLLFeeMainSchema != null) {
                    if (mLLFeeMainSchema.getMainFeeNo() != null &&
                        mLLFeeMainSchema.getMainFeeNo().equals("0000000000")) { //非明细帐单
                        mLLFeeMainSchema.setCaseNo(mRptNo);
                        mLLFeeMainSchema.setClmNo(mRptNo);
                        mLLFeeMainSchema.setMngCom(mG.ManageCom);
                        mLLFeeMainSchema.setOperator(mG.Operator);
                        mLLFeeMainSchema.setMakeDate(CurrentDate);
                        mLLFeeMainSchema.setMakeTime(CurrentTime);
                        mLLFeeMainSchema.setModifyDate(CurrentDate);
                        mLLFeeMainSchema.setModifyTime(CurrentTime);
                        //add by wood 由于允许多次导入，所以导入前先删除已有数据070618
                        map.put("delete LLFeeMain where caseno='" + mRptNo +
                                "' and customerno='" +
                                mLLFeeMainSchema.getCustomerNo() + "'",
                                "DELETE");
                        //********************************************************************
                         map.put(mLLFeeMainSchema, "DELETE&INSERT");
                    }
                }
                //帐单明细表
                if (mLLCaseReceiptSchema != null) {
                    if (mLLCaseReceiptSchema.getMainFeeNo() != null &&
                        mLLCaseReceiptSchema.getMainFeeNo().equals(
                                "0000000000")) { //非明细录入的帐单明细
                        String FeeDetailNo = PubFun1.CreateMaxNo(
                                "FeeDetailNo",
                                mG.ManageCom);
                        logger.debug("-----生成流水线号--- " + FeeDetailNo);
                        mLLCaseReceiptSchema.setClmNo(mRptNo);
                        mLLCaseReceiptSchema.setCaseNo(mRptNo);
                        mLLCaseReceiptSchema.setRgtNo(mRptNo);
                        mLLCaseReceiptSchema.setFeeDetailNo(FeeDetailNo);
                        mLLCaseReceiptSchema.setMngCom(mG.ManageCom);
                        mLLCaseReceiptSchema.setOperator(mG.Operator);
                        mLLCaseReceiptSchema.setMakeDate(CurrentDate);
                        mLLCaseReceiptSchema.setMakeTime(CurrentTime);
                        mLLCaseReceiptSchema.setModifyDate(CurrentDate);
                        mLLCaseReceiptSchema.setModifyTime(CurrentTime);
                        //add by wood 由于允许多次导入，所以导入前先删除已有数据070618
                        map.put("delete LLCaseReceipt where caseno='" + mRptNo +
                                "' and customerno='" +
                                mLLCaseReceiptSchema.getCustomerNo() + "'",
                                "DELETE");
                        //********************************************************************
                         map.put(mLLCaseReceiptSchema, "DELETE&INSERT");
                    }
                }
            } catch (Exception ex) {
                ex.getStackTrace();
                return false;
            }
        }
        if (operator.equals("ACCUPDATE")) {
            try {
                //帐户明细更新
                String AccountImSQL =
                        "select * from llclaimaccount where clmno='" +
                        mLLRegisterSchema.getRgtNo() + "'";
                logger.debug(" 账户更新记录:" + AccountImSQL);
                LLClaimAccountSet tLLClaimAccountSet = new LLClaimAccountSet();
                LLClaimAccountDB tLLClaimAccountDB = new LLClaimAccountDB();
                tLLClaimAccountSet = tLLClaimAccountDB.executeQuery(
                        AccountImSQL);
                logger.debug("mLLClaimAccountSet.size:" +
                                   mLLClaimAccountSet.size());
                if (mLLClaimAccountSet.size() != 0 &&
                    mLLClaimAccountSet.size() == 2) {
                    for (int n = 1; n <= mLLClaimAccountSet.size(); n++) {
                        LLClaimAccountSchema cLLClaimAccountSchema = new
                                LLClaimAccountSchema();
                        cLLClaimAccountSchema = mLLClaimAccountSet.get(n);
                        logger.debug("LLClaimAccount数据：" +
                                           cLLClaimAccountSchema.encode());
                        for (int m = 1; m <= tLLClaimAccountSet.size(); m++) {
                            LLClaimAccountSchema tLLClaimAccountSchema = new
                                    LLClaimAccountSchema();
                            tLLClaimAccountSchema = tLLClaimAccountSet.get(m);
                            if (tLLClaimAccountSchema.getOtherType().equals(
                                    cLLClaimAccountSchema.getOtherType())) {
                                String strSql =
                                        " update LLClaimAccount set PayDate = '" +
                                        cLLClaimAccountSchema.getPayDate() +
                                        "' ,"
                                        + " AccPayMoney = '" +
                                        cLLClaimAccountSchema.getAccPayMoney() +
                                        "',"
                                        + " MngCom = '" + mG.ManageCom + "',"
                                        + " Operator = '" + mG.Operator + "',"
                                        + " ModifyDate = '" + CurrentDate +
                                        "',"
                                        + " ModifyTime = '" + CurrentTime +
                                        "' "
                                        + " where ClmNo = '" +
                                        mLLRegisterSchema.getRgtNo() + "'"
                                        + " and OtherType = '" +
                                        cLLClaimAccountSchema.getOtherType() +
                                        "'"
                                        + " and DeclineNo = '" +
                                        cLLClaimAccountSchema.getDeclineNo() +
                                        "'";
                                map.put(strSql, "UPDATE");
                            }
                        }
                    }
                }

                //帐单主表/明细更新
                if (mLLFeeMainSchema.getMainFeeNo().equals("0000000000")) { //非明细帐单
                    String upLLFeeSQL = "select * from llfeemain where clmno='" +
                                        mLLFeeMainSchema.getClmNo()
                                        + "' and caseno='" +
                                        mLLFeeMainSchema.getCaseNo()
                                        + "' and MainFeeNo='" +
                                        mLLFeeMainSchema.getMainFeeNo()
                                        + "' and customerno='" +
                                        mLLCaseReceiptSchema.getCustomerNo()
                                        + "'";
                    logger.debug(" 帐单主表更新记录:" + upLLFeeSQL);
                    LLFeeMainSet bLLFeeMainSet = new LLFeeMainSet();
                    LLFeeMainDB bLLFeeMainDB = new LLFeeMainDB();
                    bLLFeeMainSet = bLLFeeMainDB.executeQuery(upLLFeeSQL);
                    if (bLLFeeMainSet.size() > 0 && bLLFeeMainSet.size() == 1) {
                        LLFeeMainSchema bLLFeeMainSchema = new LLFeeMainSchema();
                        bLLFeeMainSchema = bLLFeeMainSet.get(1);
                        bLLFeeMainSchema.setCustomerNo(mLLFeeMainSchema.
                                getCustomerNo());
                        bLLFeeMainSchema.setMngCom(mG.ManageCom);
                        bLLFeeMainSchema.setOperator(mG.Operator);
                        bLLFeeMainSchema.setModifyDate(CurrentDate);
                        bLLFeeMainSchema.setModifyTime(CurrentTime);
                        mLLFeeMainSchema.setSchema(bLLFeeMainSchema);
//                   map.put(mLLFeeMainSchema,"UPDATE");
                        map.put(mLLFeeMainSchema, "DELETE&INSERT");
                    } else {
                        //String DelFeeMainSQL="delete * from llfeemain where clmno='"+mLLRegisterSchema.getRgtNo()+"' and caseno='"+mLLFeeMainSchema.getCaseNo()+"' and MainFeeNo not in('"+mLLFeeMainSchema.getMainFeeNo()+"')";
                        mLLFeeMainSchema.setMngCom(mG.ManageCom);
                        mLLFeeMainSchema.setOperator(mG.Operator);
                        mLLFeeMainSchema.setMakeDate(CurrentDate);
                        mLLFeeMainSchema.setMakeTime(CurrentTime);
                        mLLFeeMainSchema.setModifyDate(CurrentDate);
                        mLLFeeMainSchema.setModifyTime(CurrentTime);
                        //map.put(DelFeeMainSQL,"DELETE");
                        map.put(mLLFeeMainSchema, "DELETE&INSERT");
                    }
                }

                if (mLLCaseReceiptSchema.getMainFeeNo().equals("0000000000")) { //非明细录入的帐单明细
                    String upLLCaseReceiptSQL =
                            "select * from LLCaseReceipt where clmno='" +
                            mLLCaseReceiptSchema.getClmNo()
                            + "' and caseno='" + mLLCaseReceiptSchema.getCaseNo()
                            + "' and MainFeeNo='" +
                            mLLCaseReceiptSchema.getMainFeeNo()
                            + "' and customerno='" +
                            mLLCaseReceiptSchema.getCustomerNo()
                            + "' ";
                    logger.debug(" 帐单明细更新记录:" + upLLCaseReceiptSQL);
                    LLCaseReceiptSet bLLCaseReceiptSet = new LLCaseReceiptSet();
                    LLCaseReceiptDB bLLCaseReceiptDB = new LLCaseReceiptDB();
                    bLLCaseReceiptSet = bLLCaseReceiptDB.executeQuery(
                            upLLCaseReceiptSQL);
                    if (bLLCaseReceiptSet.size() > 0 &&
                        bLLCaseReceiptSet.size() == 1) {
                        LLCaseReceiptSchema bLLCaseReceiptSchema = new
                                LLCaseReceiptSchema();
                        bLLCaseReceiptSchema = bLLCaseReceiptSet.get(1);
                        bLLCaseReceiptSchema.setFee(mLLCaseReceiptSchema.getFee());
                        bLLCaseReceiptSchema.setAdjSum(mLLCaseReceiptSchema.
                                getAdjSum());
                        bLLCaseReceiptSchema.setMngCom(mG.ManageCom);
                        bLLCaseReceiptSchema.setOperator(mG.Operator);
                        bLLCaseReceiptSchema.setModifyDate(CurrentDate);
                        bLLCaseReceiptSchema.setModifyTime(CurrentTime);
                        mLLCaseReceiptSchema.setSchema(bLLCaseReceiptSchema);
//                     map.put(mLLCaseReceiptSchema,"UPDATE");
                        map.put(mLLCaseReceiptSchema, "DELETE&INSERT");
                    } else {
                        String FeeDetailNo = PubFun1.CreateMaxNo("FeeDetailNo",
                                mG.ManageCom);
                        logger.debug("-----生成流水线号--- " + FeeDetailNo);
                        mLLCaseReceiptSchema.setFeeDetailNo(FeeDetailNo);
                        mLLCaseReceiptSchema.setMngCom(mG.ManageCom);
                        mLLCaseReceiptSchema.setOperator(mG.Operator);
                        mLLCaseReceiptSchema.setMakeDate(CurrentDate);
                        mLLCaseReceiptSchema.setMakeTime(CurrentTime);
                        mLLCaseReceiptSchema.setModifyDate(CurrentDate);
                        mLLCaseReceiptSchema.setModifyTime(CurrentTime);
                        String DelReceiptSQL =
                                "delete  from LLCaseReceipt where clmno='" +
                                mLLCaseReceiptSchema.getClmNo() +
                                "' and caseno='" +
                                mLLCaseReceiptSchema.getCaseNo() +
                                "' and MainFeeNo='" +
                                mLLCaseReceiptSchema.getMainFeeNo() +
                                "' and CustomerNo = '" +
                                mLLCaseReceiptSchema.getCustomerNo() + "'";
                        map.put(DelReceiptSQL, "DELETE");
                        map.put(mLLCaseReceiptSchema, "INSERT");
                    }
                }

            } catch (Exception ex) {
                ex.getStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 处理报案信息
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean addReport() {
        logger.debug("---LLClaimRegisterBL Start addReport:" + mRptNo);
        LLReportBL tLLReportBL = new LLReportBL();
        //获取报案页面信息
        mLLAccidentSchema.setAccNo(mAccNo); //事件号(新事件时为空)
        mLLReportSchema.setRptNo(mRptNo); //报案号=赔案号
        mLLSubReportSchema.setSubRptNo(mRptNo); //分案号=报案号=赔案号

        mLLAccidentSchema.setAccDate(mLLRegisterSchema.getAccidentDate()); //意外事故发生日期
        mLLReportSchema.setRptMode("05"); //报案方式(立案没有，故置为其他方式)
        mLLReportSchema.setAccidentDate(mLLRegisterSchema.getAccidentDate()); //意外事故发生日期
        mLLReportSchema.setAccidentReason(mLLRegisterSchema.getAccidentReason()); //出险原因
        mLLReportSchema.setRgtFlag("30"); //立案标志(来自立案)
        mLLReportSchema.setGrpContNo(mLLRegisterSchema.getGrpContNo()); //团体保单号
        mLLReportSchema.setGrpName(mLLRegisterSchema.getGrpName()); //单位名称
        mLLReportSchema.setAppntNo(mLLRegisterSchema.getAppntNo()); //团体客户号
        mLLReportSchema.setPeoples2(mLLRegisterSchema.getPeoples2()); //投保总人数
        mLLReportSchema.setRgtClass(mLLRegisterSchema.getRgtClass()); //团单个单

        mLLSubReportSchema.setCustomerNo(mLLCaseSchema.getCustomerNo()); //出险人编码
        mLLSubReportSchema.setCustomerName(mLLCaseSchema.getCustomerName()); //出险人姓名
        mLLSubReportSchema.setAccDate(mLLCaseSchema.getAccidentDate()); //出险日期

        for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
            mLLReportReasonSchema = new LLReportReasonSchema();
            mLLReportReasonSchema.setRpNo(mLLAppClaimReasonSet.get(i).getCaseNo()); //报案号=赔案号
            mLLReportReasonSchema.setCustomerNo(mLLAppClaimReasonSet.get(i).
                                                getCustomerNo()); //出险人编码
            mLLReportReasonSchema.setReasonCode(mLLAppClaimReasonSet.get(i).
                                                getReasonCode()); //理赔类型
            mLLReportReasonSet.add(mLLReportReasonSchema);
        }

        VData tVData = new VData();
        tVData.add(mG);
        tVData.add(mLLAccidentSchema);
        tVData.add(mLLReportSchema);
        tVData.add(mLLSubReportSchema);
        tVData.add(mLLReportReasonSet);

        if (!tLLReportBL.submitData(tVData, "INSERT")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLReportBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBL";
            tError.functionName = "addReport";
            tError.errorMessage = "LLReportBL处理数据失败!";
            this.mErrors.addOneError(tError);
            mResult.clear();
            tVData = null;
            return false;
        } else {
            //取得处理完的返回数据
            VData tempVData = new VData();
            tempVData = tLLReportBL.getResult();
            MMap tempMap = new MMap();
            tempMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            map.add(tempMap);
            LLReportSchema tLLReportSchema = new LLReportSchema();
            tLLReportSchema = (LLReportSchema) tempVData.getObjectByObjectName(
                    "LLReportSchema", 0);
            mRptNo = tLLReportSchema.getRptNo();
            logger.debug("返回生成的RptNo:" + mRptNo);
            tempVData.clear();
        }
        return true;
    }

    /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 修改原因：在帐户案件理赔中添加'删除'按钮，允许用户执行删除操作，其中只能删除案件下的
     *         某个人的相关记录
     * 修 改 人：万泽辉
     * 修改日期：2006-02-16
     * String tOperator 操作标识
     */
    private boolean delReport(String tOperator) {
        if (tOperator.equals("ACCDELETE") || tOperator == "ACCDELETE") {
            MMap nMap = new MMap();
            logger.debug("----ACCDELETE-开始------");
            String tCaseNo = mLLRegisterSchema.getRgtNo(); //赔案号
            String tCustomerNo = mLLAppClaimReasonSet.get(1).getCustomerNo(); //出险人客户号
            String tSql = "";
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.1 删除llcase表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            tSql = "delete from llcase where caseno = '" +
                   mLLCaseSchema.getCaseNo() + "' and "
                   + " customerno = '" + mLLCaseSchema.getCustomerNo() + "' ";
            nMap.put(tSql, "DELETE");
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.2 删除llsubreport表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            tSql = "delete from llsubreport where subrptno = '" + tCaseNo +
                   "' and customerno = '" + mLLCaseSchema.getCustomerNo() + "'";
            nMap.put(tSql, "DELETE");
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.3 删除llclaimaccount表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            tSql = "select serialno  from llclaimaccount where clmno = '" +
                   tCaseNo + "' "
                   + " and declineno = '" + tCustomerNo + "'";
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(tSql);
            int sum = tSSRS.getMaxRow();
            if (sum == 2) {
                for (int i = 1; i <= sum; i++) {
                    tSql = "delete from llclaimaccount where serialno = '" +
                           tSSRS.GetText(i, 1) + "'";
                    nMap.put(tSql, "DELETE");
                }
            }

            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.4 删除llappclaimreason,LLReportReason表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
                String nCaseNo = mLLAppClaimReasonSet.get(i).getCaseNo(); //赔案号
                String nRgtNo = mLLAppClaimReasonSet.get(i).getRgtNo(); //申请登记号
                String nCustomerNo = mLLAppClaimReasonSet.get(i).getCustomerNo(); //出险人客户号
                String nReasonCode = mLLAppClaimReasonSet.get(i).getReasonCode(); //理赔类型
                tSql = "delete from llappclaimreason where caseno = '" +
                       nCaseNo + "' and "
                       + " rgtno = '" + nRgtNo + "' and customerno = '" +
                       nCustomerNo + "' "
                       + " and reasoncode = '" + nReasonCode + "'";
                nMap.put(tSql, "DELETE");
            }
            tSql = "delete from LLReportReason where RpNo = '" + tCaseNo +
                   "' and customerno = '" +
                   mLLCaseSchema.getCustomerNo() + "' ";
            nMap.put(tSql, "DELETE");
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.5 删除llfeemain表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            //String tMainFeeNo = mLLFeeMainSchema.getMainFeeNo(); //账单号
            tSql = "delete from llfeemain where clmno = '" + tCaseNo +
                   "' and "
                   + " customerno = '" + tCustomerNo + "' and rgtno = '" +
                   tCaseNo + "'"
                   + " and mainfeeno = '0000000000'";
            nMap.put(tSql, "DELETE");
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.6 删除llclaimdetail,llclaimpolicy表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            tSql = "delete from llclaimdetail where ClmNo = '" + tCaseNo +
                   "' and CustomerNo = '" + tCustomerNo + "'";
            nMap.put(tSql, "DELETE");
            tSql = "delete from llclaimpolicy where ClmNo = '" + tCaseNo +
                   "' and InsuredNo = '" + tCustomerNo + "'";
            nMap.put(tSql, "DELETE");
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.7 删除llcasereceipt表中信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            tSql =
                    "select mainfeeno,feedetailno  from llcasereceipt where clmno = '" +
                    tCaseNo + "' "
                    + " and clmno = '" + tCaseNo + "' and "
                    + " customerno = '" + tCustomerNo + "'";
            SSRS tSSRS1 = tExeSQL.execSQL(tSql);
            if (tSSRS1.getMaxRow() > 0) {
                for (int i = 1; i <= tSSRS1.getMaxRow(); i++) {
                    String nMainFeeNo = tSSRS1.GetText(i, 1);
                    String nFeeDetailNo = tSSRS1.GetText(i, 2); //账单费用明细
                    tSql = "delete from llcasereceipt where caseno = '" +
                           tCaseNo +
                           "' and  clmno = '" + tCaseNo +
                           "' and mainfeeno = '" + nMainFeeNo + "' "
                           + " and feedetailno = '" + nFeeDetailNo + "'"
                           + " and customerno = '" + tCustomerNo + "'";
                    nMap.put(tSql, "DELETE");
                }
            }
            VData nResult = new VData();
            nResult.add(nMap);
            PubSubmit nPubSumit = new PubSubmit();
            if (!nPubSumit.submitData(nResult, tOperator)) {
                CError tError = new CError();
                tError.moduleName = "LLClaimRegisterBL";
                tError.functionName = "delReport";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            logger.debug("－－－－ACCDELETE－－结束－");
            return true;
        } else {
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBL";
            tError.functionName = "delReport";
            tError.errorMessage = "操作符传输失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //return true;
    }

    /**
     * 添加打印数据
     * 2005-8-16 14:49
     * @return boolean
     */
    private boolean insertLOPRTManager(String tCode) {
        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
        String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 10); //生成印刷流水号
        tLOPRTManagerSchema.setPrtSeq(tPrtSeq); //印刷流水号
        tLOPRTManagerSchema.setOtherNo(mRptNo); //对应其它号码
        tLOPRTManagerSchema.setOtherNoType("05"); //其它号码类型--05赔案号
        tLOPRTManagerSchema.setCode(tCode); //单据编码
        tLOPRTManagerSchema.setManageCom(mG.ManageCom); //管理机构
        tLOPRTManagerSchema.setReqCom(mG.ComCode); //发起机构
        tLOPRTManagerSchema.setReqOperator(mG.Operator); //发起人
        tLOPRTManagerSchema.setPrtType("0"); //打印方式
        tLOPRTManagerSchema.setStateFlag("0"); //打印状态
        tLOPRTManagerSchema.setMakeDate(CurrentDate); //入机日期(报案日期)
        tLOPRTManagerSchema.setMakeTime(CurrentTime); //入机时间
        tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
        tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); //批打检索日期
        tLOPRTManagerSchema.setStandbyFlag4(mLLCaseSchema.getCustomerNo()); //客户号码
//        tLOPRTManagerSchema.setStandbyFlag5();

        map.put(tLOPRTManagerSchema, "INSERT");
        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData() {
        try {
            //后台传送
            //前台传
            mResult.clear();
            mResult.add(mG);
            mResult.add(map);
            mResult.add(mTransferData);
            tResult.clear();
            tResult.add(mLLRegisterSchema);
            tResult.add(mLLAccidentSchema);

        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
