/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCaseRelaSet;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.*;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.lis.db.LLReportDB;
import com.sinosoft.lis.vschema.LLReportSet;
import com.sinosoft.lis.db.LLSubReportDB;
import com.sinosoft.lis.vschema.LLSubReportSet;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLCaseRelaDB;
import com.sinosoft.lis.db.LLReportReasonDB;
import com.sinosoft.lis.db.LLReportRelaDB;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.db.LLReportAffixDB;
import com.sinosoft.lis.vschema.LLReportAffixSet;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.lis.db.*;
import com.sinosoft.service.BusinessService;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLClaimRegisterBL implements BusinessService
{
private static Logger logger = Logger.getLogger(LLClaimRegisterBL.class);

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
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();
//    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
//    private LLAppClaimReasonSchema mLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
    private LLCaseSet mLLCaseSet = new LLCaseSet();
    private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();
    private LLAffixSet mLLAffixSet = new LLAffixSet();
    private LWMissionSchema mLWMissionSchema = null;

    //报案相关
    private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();
//    private LLAccidentSubSchema mLLAccidentSubSchema = new LLAccidentSubSchema();
    private LLReportSchema mLLReportSchema = new LLReportSchema();
    private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();
//    private LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();
//    private LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();
    private LLCaseRelaSchema mLLCaseRelaSchema = null;
	private LLReportRelaSchema mLLReportRelaSchema = null;
	private LLSubReportSet mLLSubReportSet = null;
	private LLReportAffixSet mLLReportAffixSet = null;
	private LLAccidentSubSchema mLLAccidentSubSchema = null;
    private LLReportReasonSchema mLLReportReasonSchema = new LLReportReasonSchema();
    private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();
    

    //流水号类型,详细见SysMaxNo
    private String mRptNo = "";//报案号
    private String mAccNo = "";
    private String mRgtNo = "";// 立案号

//    private String mConclusionSave = "";
    private boolean mIsRegisterExist;
    private boolean mIsReportExist;
    private String mIsAccExist;
    private String mHospitalCode = "";
    private String mRemark = "";

    public LLClaimRegisterBL()
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
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LLClaimRegisterBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterBL after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterBL after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterBL after prepareOutputData----------");
        if(cOperate.equals("DELETE")){
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mResult, cOperate)) {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLEndCaseBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
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
        logger.debug("---LLClaimRegisterBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);

        mHospitalCode = (String) mTransferData.getValueByName("hospital");
        mRemark = (String) mTransferData.getValueByName("Remark");

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
        mLLReportSchema=(LLReportSchema)mInputData.getObjectByObjectName("LLReportSchema",0);
        mLLSubReportSchema=(LLSubReportSchema)mInputData.getObjectByObjectName("LLSubReportSchema",0);
        mLLReportReasonSchema=(LLReportReasonSchema)mInputData.getObjectByObjectName("LLReportReasonSchema",0);
        mLLReportReasonSet=(LLReportReasonSet)mInputData.getObjectByObjectName("LLReportReasonSet",0);
        /******************************jinsh20070403点修改时将信息保存到报案的表里********************************/


        if (mLLRegisterSchema == null && mLLCaseSchema == null)
        {
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
     * 校验传入的报案信息是否合法
     * @return：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        logger.debug("----------begin checkInputData----------");
//        logger.debug(mLLRegisterSchema.getAccDate());
//        try
//        {
//            //非空字段检验
//            if (mLLRegisterSchema.getAccDate() == null)//意外事故发生日期
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimRegisterBL";
//                tError.functionName = "checkInputData";
//                tError.errorMessage = "接受的意外事故发生日期为空!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//            if (mLLCaseSchema.getCustomerNo() == null)//出险人编码
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimRegisterBL";
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
//            tError.moduleName = "LLClaimRegisterBL";
//            tError.functionName = "checkInputData";
//            tError.errorMessage = "在校验输入的数据时出错!";
//            this.mErrors.addOneError(tError);
//            return false;
//        }
        return true;
    }

    /**
     * 更新立案阶段各个表，如果立案已经存在则判断是否需要进行增人操作，如果增人，则同步更新报案各个表
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate) {
        logger.debug("---LLClaimRegisterBL start dealData()...");
        boolean tReturn = false;
        String deleteSql = "";
        String deleteSqlUp = "";
        //----------------------------------------------------------------------Beg
        //功能：判断事件、报案、立案信息是否存在
        //处理：1 事件号为空，则报案、立案都不存在；事件号不为空，则判断报案号。
        //     2 报案号为空，则立案不存在；报案号不为空，则查询立案是否存在。
        //----------------------------------------------------------------------
        if(!cOperate.equals("DELETE")){
            IsExist();
        }
        //----------------------------------------------------------------------End

        if (cOperate.equals("INSERT") || cOperate.equals("insert||first"))
        //----------------------------------------------------------------------
        //功能：添加纪录
        //处理：1 报案不存在则新建所有
        //     2 报案存在。则判断立案，立案不存在则同步所有报案信息
        //                          立案存在则只更新立案分案信息
        //----------------------------------------------------------------------
        {
            //报案不存在
            if (!mIsReportExist)
            {
                logger.debug("---报案不存在:");

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
                }

//              重新生成立案号
				if(createRgtno()){
					CError.buildErr(this, "重新生成立案号时出错！");
					return false;
				}
                
                //立案表
                mLLRegisterSchema.setRgtNo(mRgtNo); //赔案号
                mLLRegisterSchema.setRgtObj("2"); //号码类型(?)
                mLLRegisterSchema.setRgtObjNo(mRgtNo); //其他号码(?)
//                mLLRegisterSchema.setRgtClass("1"); //申请类型,前台已输入
                mLLRegisterSchema.setCustomerNo("1"); //客户号(?)
//                mLLRegisterSchema.setRgtState("11"); //*案件类型*，普通案件
                mLLRegisterSchema.setClmState("20");
                mLLRegisterSchema.setRgtDate(CurrentDate);
                mLLRegisterSchema.setOperator(mG.Operator);
                mLLRegisterSchema.setMngCom(mG.ManageCom);
                mLLRegisterSchema.setMakeDate(CurrentDate);
                mLLRegisterSchema.setMakeTime(CurrentTime);
                mLLRegisterSchema.setModifyDate(CurrentDate);
                mLLRegisterSchema.setModifyTime(CurrentTime);





                //立案分案表
                mLLCaseSchema.setCaseNo(mLLRegisterSchema.getRgtNo()); //赔案号
                mLLCaseSchema.setRgtNo(mLLRegisterSchema.getRgtNo()); //赔案号
                mLLCaseSchema.setRgtType("11"); //案件类型，普通案件
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

                //理赔类型表(多条添加)
                for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++)
                {
                    mLLAppClaimReasonSet.get(i).setCaseNo(mLLRegisterSchema.getRgtNo());
                    mLLAppClaimReasonSet.get(i).setRgtNo(mLLRegisterSchema.getRgtNo());
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
            }
            //报案已存在
            else
            {
                logger.debug("---报案已存在:");
                //立案存在,只增加出险人
                if (mIsRegisterExist)
                {
                    logger.debug("---立案存在:");
                    //查询出险人是否存在于立案
                    tReturn=true;
//                    return true;
                   
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

                        //立案分案表
                        mLLCaseSchema.setRgtNo(mLLCaseSchema.getCaseNo()); //赔案号
                        mLLCaseSchema.setRgtType("11"); //案件类型
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
                    }
                    
                }
                //立案不存在
                else
                {
                    logger.debug("---立案不存在,同步报案数据:");
                    //-------------------------------------------------------------
                    //同步所有报案信息到立案,同时更新立案标志为已立案20
                    //-------------------------------------------------------------
                    //报案到立案表------------------------------------------------Beg
                    LLReportDB tLLReportDB = new LLReportDB();
                    LLReportSchema tLLReportSchema = new LLReportSchema();
                    String sql = "select * from LLReport where"
                                 + " RptNo = '" + mRptNo + "'";
                    logger.debug("Start query LLReport:" + sql);
                    LLReportSet tLLReportSet = tLLReportDB.executeQuery(sql);

                    if (tLLReportSet == null && tLLReportSet.size() == 0)
                    {
                        //@@错误处理
                        CError tError = new CError();
                        tError.moduleName = "LLClaimRegisterBL";
                        tError.functionName = "dealData";
                        tError.errorMessage = "查询报案表失败!";
                        this.mErrors.addOneError(tError);
                        tReturn = false;
                    }
                    else
                    {
                        tLLReportSchema = tLLReportSet.get(1);
//                      重新创建立案号
                        if(!createRgtno()){
                        	CError.buildErr(this, "生成立案号时出错！");
                        	return false;
                        }
                        mRptNo=tLLReportSchema.getRptNo();
                        //立案表
                        mLLRegisterSchema.setRgtNo(mRgtNo);//报案号=赔案号
//                        mLLRegisterSchema.setRgtantName(tLLReportSchema.getRptorName());//报案人姓名
//                        mLLRegisterSchema.setRgtantPhone(tLLReportSchema.getRptorPhone());//报案人电话
//                        mLLRegisterSchema.setRgtantAddress(tLLReportSchema.getRptorAddress());//报案人通讯地址
//                        mLLRegisterSchema.setRelation(tLLReportSchema.getRelation());//报案人与事故人关系
//                        mLLRegisterSchema.setAccidentSite(tLLReportSchema.getAccidentSite());//出险地点
                        mLLRegisterSchema.setAccidentDate(tLLReportSchema.getAccidentDate());//意外事故发生日期
                        mLLRegisterSchema.setAccidentReason(tLLReportSchema.getAccidentReason());//出险原因
                        mLLRegisterSchema.setRgtObj("2"); //号码类型(?)
                        mLLRegisterSchema.setRgtObjNo(mLLReportSchema.getRptNo()); //其他号码(?)
                        mLLRegisterSchema.setRgtClass("2"); //申请类型(?)
                        mLLRegisterSchema.setCustomerNo("1"); //客户号(?)
                        //tongmeng 2009-01-05 modify
                        mLLRegisterSchema.setRgtState(tLLReportSchema.getRgtState());
                       // mLLRegisterSchema.setRgtState(tLLReportSchema.getAvaliReason()); //*案件类型*，普通案件
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
                        mLLSubReportSet = tLLSubReportDB.executeQuery(sql1);

                        if (mLLSubReportSet == null && mLLSubReportSet.size() == 0)
                        {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "dealData";
                            tError.errorMessage = "查询报案分案表失败!";
                            this.mErrors.addOneError(tError);
                            tReturn = false;
                        }
                        else
                        {
                            mLLCaseSet.clear();
                            for (int j = 1; j <= mLLSubReportSet.size(); j++)
                            {
                                LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
                                tLLSubReportSchema = mLLSubReportSet.get(j);
                                LLCaseSchema tLLCaseSchema = new LLCaseSchema();

                                tLLCaseSchema.setCaseNo(mLLRegisterSchema.getRgtNo()); //赔案号
                                tLLCaseSchema.setRgtNo(mLLRegisterSchema.getRgtNo()); //赔案号
                                tLLCaseSchema.setCustomerNo(tLLSubReportSchema.getCustomerNo());//出险人编码
                                tLLCaseSchema.setCustomerName(tLLSubReportSchema.getCustomerName());//出险人姓名
                               // tLLCaseSchema.setRgtType("11");
                                //取得用户补充信息
                                LDPersonDB tLDPersonDB = new LDPersonDB();
                                tLDPersonDB.setCustomerNo(tLLSubReportSchema.getCustomerNo());
                                tLDPersonDB.getInfo();

                                String strSql = "select trunc(months_between(sysdate,Birthday)/12) from LDPerson where "
                                        + "CustomerNo = '"
                                        + tLLSubReportSchema.getCustomerNo() + "'";
                                ExeSQL exesql = new ExeSQL();
                                String tAge = exesql.getOneValue(strSql);
                                tLLCaseSchema.setCustomerAge(tAge); //出险人年龄

                                tLLCaseSchema.setCustomerSex(tLDPersonDB.getSex()); //出险人性别

                                tLLCaseSchema.setAccDate(tLLSubReportSchema.getAccDate()); //出险日期
                                tLLCaseSchema.setMedAccDate(tLLSubReportSchema.getAccDate());//医疗出险日期
                                tLLCaseSchema.setAccidentDetail(tLLSubReportSchema.getAccidentDetail()); //出险细节
                                tLLCaseSchema.setCureDesc(tLLSubReportSchema.getCureDesc()); //治疗情况
                                tLLCaseSchema.setHospitalCode(tLLSubReportSchema.getHospitalCode());//医院代码
                                //tongmeng 2009-01-05 add
                                //案件类型
                                tLLCaseSchema.setRgtType(tLLReportSchema.getRgtState());
                                //tLLCaseSchema.setRgtType(tLLReportSchema.getAvaliReason()); //案件类型，普通案件
                                tLLCaseSchema.setRgtState("20"); //案件状态
                                tLLCaseSchema.setAffixConclusion("0");//单证齐全标志
                                tLLCaseSchema.setEditFlag("0");//重要信息修改标志
                                tLLCaseSchema.setRgtDate(CurrentDate);
                                tLLCaseSchema.setSurveyFlag(tLLSubReportSchema.getSurveyFlag());//调查报告标志
                                tLLCaseSchema.setSubmitFlag(tLLSubReportSchema.getSubmitFlag());//发起呈报标志
                                tLLCaseSchema.setCondoleFlag(tLLSubReportSchema.getCondoleFlag());//提起慰问标志
                                tLLCaseSchema.setSecondUWFlag(tLLSubReportSchema.getCondoleFlag());//提起慰问标志
                                tLLCaseSchema.setAccResult1(tLLSubReportSchema.getAccResult1());//ICD10主代码
                                tLLCaseSchema.setAccResult2(tLLSubReportSchema.getAccResult2());//ICD10子代码
                                tLLCaseSchema.setAccdentDesc(tLLSubReportSchema.getAccDesc());
                                tLLCaseSchema.setRemark(tLLSubReportSchema.getRemark());
                                tLLCaseSchema.setOperator(mG.Operator);
                                tLLCaseSchema.setMngCom(mG.ManageCom);
                                tLLCaseSchema.setMakeDate(CurrentDate);
                                tLLCaseSchema.setMakeTime(CurrentTime);
                                tLLCaseSchema.setModifyDate(CurrentDate);
                                tLLCaseSchema.setModifyTime(CurrentTime);
                                
                                //2009-04-29 zhangzheng 增加受益人信息
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
                                
                                
                                mLLCaseSet.add(tLLCaseSchema);
                            }
                        }

                        //立案理赔类型表(多条)-------------------------------------Beg
                        LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
                        String sql2 = "select * from LLReportReason where"
                                    + " rpno = '" + mRptNo + "'";
                        logger.debug("Start query LLReportReason:" + sql2);
                        mLLReportReasonSet = tLLReportReasonDB.executeQuery(sql2);
                        if (mLLReportReasonSet == null && mLLReportReasonSet.size() == 0)
                        {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "dealData";
                            tError.errorMessage = "查询报案理赔类型表失败!";
                            this.mErrors.addOneError(tError);
                            tReturn = false;
                        }
                        else
                        {
                            mLLAppClaimReasonSet.clear();
                            for (int i = 1; i <= mLLReportReasonSet.size(); i++)
                            {
                                LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema();
                                tLLReportReasonSchema = mLLReportReasonSet.get(i);
                                LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();

                                tLLAppClaimReasonSchema.setCaseNo(mLLRegisterSchema.getRgtNo());
                                tLLAppClaimReasonSchema.setRgtNo(mLLRegisterSchema.getRgtNo());
                                tLLAppClaimReasonSchema.setCustomerNo(tLLReportReasonSchema.getCustomerNo());//出险人编码
                                tLLAppClaimReasonSchema.setReasonCode(tLLReportReasonSchema.getReasonCode());//理赔类型
                                //tLLAppClaimReasonSchema.setReason(tLLReportReasonSchema.getReason());//类型
                                //tLLAppClaimReasonSchema.setReasonType(tLLReportReasonSchema.getReasonType());//原因
                                tLLAppClaimReasonSchema.setAffixGetDate(tLLReportReasonSchema.getAffixGetDate());//材料齐备日期
                                tLLAppClaimReasonSchema.setOperator(mG.Operator);
                                tLLAppClaimReasonSchema.setMngCom(mG.ManageCom);
                                tLLAppClaimReasonSchema.setMakeDate(CurrentDate);
                                tLLAppClaimReasonSchema.setMakeTime(CurrentTime);
                                tLLAppClaimReasonSchema.setModifyDate(CurrentDate);
                                tLLAppClaimReasonSchema.setModifyTime(CurrentTime);
                                mLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
                            }
                        }

                        //附件表-------------------------------------------------Beg
                        LLReportAffixDB tLLReportAffixDB = new LLReportAffixDB();
                        String sql3 = "select * from LLReportAffix where"
                                      + " RptNo = '" + mRptNo + "'";
                        logger.debug("Start query LLReportAffix:" + sql3);
                        mLLReportAffixSet = tLLReportAffixDB.executeQuery(sql3);
                        if (mLLReportAffixSet == null &&
                        		mLLReportAffixSet.size() == 0)
                        {
                            //@@错误处理
                            CError tError = new CError();
                            tError.moduleName = "LLClaimRegisterBL";
                            tError.functionName = "dealData";
                            tError.errorMessage = "查询报案理赔类型表失败!";
                            this.mErrors.addOneError(tError);
                            tReturn = false;
                        }
                        else
                        {
                            mLLAffixSet.clear();
                            for (int i = 1; i <= mLLReportAffixSet.size(); i++)
                            {
                                LLReportAffixSchema tLLReportAffixSchema = new
                                        LLReportAffixSchema();
                                tLLReportAffixSchema = mLLReportAffixSet.get(i);
                                LLAffixSchema tLLAffixSchema = new LLAffixSchema();

                                tLLAffixSchema.setCaseNo(mLLRegisterSchema.getRgtNo());
                                tLLAffixSchema.setRgtNo(mLLRegisterSchema.getRgtNo());
                                tLLAffixSchema.setAffixNo(
                                        tLLReportAffixSchema.getAffixNo()); //附件号码
                                tLLAffixSchema.setSerialNo(tLLReportAffixSchema.getSerialNo());//流水号
                                tLLAffixSchema.setAffixName(tLLReportAffixSchema.getAffixName());//附件名称
                                tLLAffixSchema.setAffixCode(tLLReportAffixSchema.getAffixCode());//附件代码
                                tLLAffixSchema.setAffixType(tLLReportAffixSchema.getAffixType());//附件类型
                                tLLAffixSchema.setCustomerNo(
                                        tLLReportAffixSchema.getCustomerNo()); //出险人编码
                                tLLAffixSchema.setReasonCode(
                                        tLLReportAffixSchema.getReasonCode()); //原因代码
                                tLLAffixSchema.setProperty(tLLReportAffixSchema.getProperty());//材料类型(是否原件)
                                tLLAffixSchema.setReadyCount(tLLReportAffixSchema.getReadyCount());//齐备件数
                                tLLAffixSchema.setShortCount(tLLReportAffixSchema.getShortCount());//缺少件数
                                tLLAffixSchema.setSupplyDate(tLLReportAffixSchema.getSupplyDate());
                                tLLAffixSchema.setFileEndDate(tLLReportAffixSchema.getFileEndDate());
                                tLLAffixSchema.setFileStartDate(tLLReportAffixSchema.getFileStartDate());
                                tLLAffixSchema.setFileSummary(tLLReportAffixSchema.getFileSummary());
                                tLLAffixSchema.setRemark(tLLReportAffixSchema.getRemark());
                                tLLAffixSchema.setNeedFlag(tLLReportAffixSchema.getNeedFlag());
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
                        
                        LLReportRelaDB mLLReportRelaDB = new LLReportRelaDB();
						mLLReportRelaDB.setRptNo(mLLReportSchema.getRptNo());
						mLLReportRelaDB.setSubRptNo(mLLReportSchema.getRptNo());
						if(!mLLReportRelaDB.getInfo())
						{
							// @@错误处理
							CError.buildErr(this, "查询报案事件关联信息失败!");
							return false;
						}
						else
						{
							mLLReportRelaSchema =new LLReportRelaSchema();
							mLLReportRelaSchema.setSchema(mLLReportRelaDB.getSchema());
						}
						
						//分案事件关联
						LLCaseRelaDB mLLCaseRelaDB=new LLCaseRelaDB();
						LLCaseRelaSet mLLCaseRelaSet=new LLCaseRelaSet();
						
						mLLCaseRelaDB.setCaseNo(mLLReportSchema.getRptNo());
						mLLCaseRelaSet=mLLCaseRelaDB.query();
						if(mLLCaseRelaSet==null||mLLCaseRelaSet.size()==0)
						{
							// @@错误处理
							CError.buildErr(this, "查询分案事件关联信息失败!");
							return false;
						}
						else
						{
							mLLCaseRelaSchema =new LLCaseRelaSchema();
							mLLCaseRelaSchema.setSchema(mLLCaseRelaSet.get(1));
						}			
//						立案信息
						mResult.add(mLLRegisterSchema);//立案信息
						mResult.add(mLLCaseSet);//立案分案信息
						mResult.add(mLLAppClaimReasonSet);//报案理赔类型
						mResult.add(mLLAffixSet);//立案附件表
						//报案信息
						mResult.add(mLLReportSchema);//报案表
						mResult.add(mLLSubReportSet);//报案分案表
						mResult.add(mLLReportRelaSchema);//报案事件关联
						mResult.add(mLLCaseRelaSchema);//分案事件关联
						mResult.add(mLLReportReasonSet);//报案理赔类型
						mResult.add(mLLReportAffixSet);//报案附件表
						
//						//2008-11-28 配合传递数据的统一,增加一个LLAccidentSchema和LLAccidentSubSchema
						//事件信息
						mResult.add(mLLAccidentSchema);//事件信息
						mResult.add(mLLAccidentSubSchema);//分事件信息
						
                    }
//                    //打包提交数据
//                    map.put(mLLRegisterSchema, "DELETE&INSERT");
//                    map.put(mLLCaseSet, "DELETE&INSERT");
//                    map.put(mLLAppClaimReasonSet, "DELETE&INSERT");
//                    map.put(mLLAffixSet, "DELETE&INSERT");
//                    map.put(mLLReportSchema, "DELETE&INSERT");

                    tReturn = true;
                }
            }
        }

        //更新纪录
        if (cOperate.equals("UPDATE"))
        {
        	mRgtNo = mLLRegisterSchema.getRgtNo();
            logger.debug("----------UPDATE dealData----------");
            //更新事件信息
            String strSql = " select accno from LLCaseRela, LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo"
                          + " and LLCaseRela.CaseNo = '"+mLLRegisterSchema.getRgtNo()+"'";
            ExeSQL tExeSQL = new ExeSQL();
            String AccNo = tExeSQL.getOneValue(strSql);
            LLAccidentDB mLLAccidentDB = new LLAccidentDB();
            LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
            mLLAccidentDB.setAccNo(AccNo);
            mLLAccidentDB.getInfo();
            tLLAccidentSchema = mLLAccidentDB.getSchema();
            tLLAccidentSchema.setAccDate(mLLAccidentSchema.getAccDate());

            //更新立案信息
            LLRegisterDB tLLRegisterDB = new LLRegisterDB();
            tLLRegisterDB.setRgtNo(mLLRegisterSchema.getRgtNo());
            tLLRegisterDB.getInfo();
            mLLRegisterSchema.setRgtObj(tLLRegisterDB.getRgtObj()); //号码类型(?)
            mLLRegisterSchema.setRgtObjNo(tLLRegisterDB.getRgtObjNo()); //其他号码(?)
            mLLRegisterSchema.setRgtClass(tLLRegisterDB.getRgtClass()); //申请类型(?)
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
            mLLReportSchema.setOperator(tLLReportDB.getOperator()); //应保存原来的报案操作员和时间
            mLLReportSchema.setMakeDate(tLLReportDB.getMakeDate());
            mLLReportSchema.setMakeTime(tLLReportDB.getMakeTime());
            if (mLLReportSchema.getAccidentCourse()==null)
            {
                mLLReportSchema.setAccidentCourse(tLLReportDB.getAccidentCourse());
            }
            if(mLLReportSchema.getAccidentSite()==null)
            {
                mLLReportSchema.setAccidentSite(tLLReportDB.getAccidentSite());
            }
            if(mLLReportSchema.getAvaiFlag()==null)
            {
                mLLReportSchema.setAvaiFlag(tLLReportDB.getAvaiFlag());
            }
            if(mLLReportSchema.getAvaliReason()==null)
            {
                mLLReportSchema.setAvaliReason(tLLReportDB.getAvaliReason());
            }
            if(mLLReportSchema.getCaseEndDate()==null)
            {
                mLLReportSchema.setCaseEndDate(tLLReportDB.getCaseEndDate());
            }
            if(mLLReportSchema.getCaseNoDate()==null)
            {
                mLLReportSchema.setCaseNoDate(tLLReportDB.getCaseNoDate());
            }
            if(mLLReportSchema.getEmail()==null)
            {
                mLLReportSchema.setEmail(tLLReportDB.getEmail());
            }
            mLLReportSchema.setMngCom(tLLReportDB.getMngCom());
            if(mLLReportSchema.getNoRgtReason()==null)
            {
                mLLReportSchema.setNoRgtReason(tLLReportDB.getNoRgtReason());
            }
            if(mLLReportSchema.getPeoples2()==0)
            {
                mLLReportSchema.setPeoples2(tLLReportDB.getPeoples2());
            }
            if(mLLReportSchema.getPostCode()==null)
            {
                mLLReportSchema.setPostCode(tLLReportDB.getPostCode());
            }
            if(mLLReportSchema.getRelation()==null)
            {
                mLLReportSchema.setRelation(tLLReportDB.getRelation());
            }
            if(mLLReportSchema.getRemark()==null)
            {
                mLLReportSchema.setRemark(tLLReportDB.getRemark());
            }
            if(mLLReportSchema.getReturnMode()==null)
            {
                mLLReportSchema.setReturnMode(tLLReportDB.getReturnMode());
            }
            if(mLLReportSchema.getRgtClass()==null)
            {
                mLLReportSchema.setRgtClass(tLLReportDB.getRgtClass());
            }
            if(mLLReportSchema.getRgtFlag()==null)
            {
                mLLReportSchema.setRgtFlag(tLLReportDB.getRgtFlag());
            }
            if(mLLReportSchema.getRgtObj()==null)
            {
                mLLReportSchema.setRgtObj(tLLReportDB.getRgtObj());
            }
            if(mLLReportSchema.getRgtObjNo()==null)
            {
                mLLReportSchema.setRgtObjNo(tLLReportDB.getRgtObjNo());
            }
            if(mLLReportSchema.getRgtReason()==null)
            {
                mLLReportSchema.setRgtReason(tLLReportDB.getRgtReason());
            }
            if(mLLReportSchema.getRptDate()==null)
            {
                mLLReportSchema.setRptDate(tLLReportDB.getRptDate());
            }
            if(mLLReportSchema.getRptMode()==null)
            {
                mLLReportSchema.setRptMode(tLLReportDB.getRptMode());
            }
            if(mLLReportSchema.getRptorAddress()==null)
            {
                mLLReportSchema.setRptorAddress(tLLReportDB.getRptorAddress());
            }
            if(mLLReportSchema.getRptorMobile()==null)
            {
                mLLReportSchema.setRptorMobile(tLLReportDB.getRptorMobile());
            }
            if(mLLReportSchema.getRptorName()==null)
            {
                mLLReportSchema.setRptorName(tLLReportDB.getRptorName());
            }
            if(mLLReportSchema.getRptorPhone()==null)
            {
                mLLReportSchema.setRptorPhone(tLLReportDB.getRptorPhone());
            }
            mLLReportSchema.setModifyDate(CurrentDate);
            mLLReportSchema.setModifyTime(CurrentTime);



            LLSubReportDB tLLSubReportDB=new LLSubReportDB();
            tLLSubReportDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
            tLLSubReportDB.setCustomerNo(mLLSubReportSchema.getCustomerNo());
            tLLSubReportDB.getInfo();
            
            mLLSubReportSchema.setCureDesc(tLLSubReportDB.getCureDesc());
            mLLSubReportSchema.setMngCom(tLLSubReportDB.getMngCom());
            mLLSubReportSchema.setCondoleFlag(tLLSubReportDB.getCondoleFlag());
            mLLSubReportSchema.setOperator(tLLSubReportDB.getOperator());
            mLLSubReportSchema.setMakeDate(tLLSubReportDB.getMakeDate());
            mLLSubReportSchema.setMakeTime(tLLSubReportDB.getMakeTime());
            mLLSubReportSchema.setAccResult1(tLLSubReportDB.getAccResult1());
            mLLSubReportSchema.setAccResult2(tLLSubReportDB.getAccResult2());
            
            mLLSubReportSchema.setBnfIDType(tLLSubReportDB.getBnfIDType());//受益人证件类型
            mLLSubReportSchema.setBnfSex(tLLSubReportDB.getBnfSex());//受益人性别
            mLLSubReportSchema.setBirthday(tLLSubReportDB.getBirthday());//受益人出生日期
            mLLSubReportSchema.setBnfName(tLLSubReportDB.getBnfName());
            
            mLLSubReportSchema.setBnfIDNo(tLLSubReportDB.getBnfIDNo());// 受益人证件号码
            mLLSubReportSchema.setBankAccNo(tLLSubReportDB.getBankAccNo());
			mLLSubReportSchema.setBnfAccName(tLLSubReportDB.getBnfAccName());//受益人银行账户名
			
            mLLSubReportSchema.setBankCode(tLLSubReportDB.getBankCode());
            mLLSubReportSchema.setRelationToInsured(tLLSubReportDB.getRelationToInsured());// 与被保险人关系
            mLLSubReportSchema.setCaseGetMode(tLLSubReportDB.getCaseGetMode());// 领取方式
            
            mLLSubReportSchema.setModifyDate(CurrentDate);
            mLLSubReportSchema.setModifyTime(CurrentTime);
            deleteSqlUp = " delete from LLReportReason where "
                                    + " RpNo = '" + mLLSubReportSchema.getSubRptNo() + "'"
                                    + " and CustomerNo = '" + mLLSubReportSchema.getCustomerNo() + "'";

            for (int i = 1; i <= mLLReportReasonSet.size(); i++)
            {
                LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
                tLLReportReasonDB.setRpNo(mLLReportReasonSet.get(i).getRpNo());
                tLLReportReasonDB.setCustomerNo(mLLReportReasonSet.get(i).getCustomerNo());
                tLLReportReasonDB.setReasonCode(mLLReportReasonSet.get(i).getReasonCode());
                tLLReportReasonDB.getInfo();
                if(tLLReportReasonDB.getMakeDate() == null)
                {
                    mLLReportReasonSet.get(i).setOperator(mG.Operator);
                    mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
                    mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
                    mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
                    mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
                    mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
                    //如有伤残或高残,则生成伤残打印数据
                    String tCode = mLLReportReasonSet.get(i).getReasonCode().substring(1,3);
                    if (tCode.equals("01")||tCode.equals("03"))
                    {
                            //生成打印数据--伤残鉴定通知书
                            if (!insertLOPRTManager("PCT001"))
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        mLLReportReasonSet.get(i).setSchema(tLLReportReasonDB.getSchema());
                        mLLReportReasonSet.get(i).setRpNo(tLLReportReasonDB.getRpNo());
                        mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
                        mLLReportReasonSet.get(i).setOperator(mG.Operator);
                        mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
                        mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
                    }
                }
                map.put(mLLReportSchema,"DELETE&INSERT");
                map.put(mLLSubReportSchema,"DELETE&INSERT");
                map.put(deleteSqlUp,"DELETE");
                map.put(mLLReportReasonSet,"DELETE&INSERT");

                /************************jinsh20070403点修改时将信息保存到报案信息*******************************/


            //更新分案信息表的字段
            LLCaseDB tLLCaseDB = new LLCaseDB();
            tLLCaseDB.setCaseNo(mLLCaseSchema.getCaseNo());
            tLLCaseDB.setCustomerNo(mLLCaseSchema.getCustomerNo());
            tLLCaseDB.getInfo();
            mLLCaseSchema.setRgtNo(mLLCaseSchema.getCaseNo());
            mLLCaseSchema.setSurveyFlag(tLLCaseDB.getSurveyFlag());//调查报告标志
            mLLCaseSchema.setSubmitFlag(tLLCaseDB.getSubmitFlag()); //发起呈报标志
            mLLCaseSchema.setCondoleFlag(tLLCaseDB.getCondoleFlag()); //提起慰问标志
            mLLCaseSchema.setSecondUWFlag(tLLCaseDB.getSecondUWFlag());
            //mLLCaseSchema.setRgtType("11"); //案件类型，普通案件
            mLLCaseSchema.setRgtType(tLLCaseDB.getRgtType());//案件类型
            mLLCaseSchema.setRgtState("20"); //案件状态
//            mLLCaseSchema.setAffixConclusion(tLLCaseDB.getAffixConclusion());//单证齐全标志
            mLLCaseSchema.setEditFlag(tLLCaseDB.getEditFlag());//重要信息修改标志
            mLLCaseSchema.setCureDesc(tLLCaseDB.getCureDesc());//
            mLLCaseSchema.setRgtDate(tLLCaseDB.getRgtDate());
            mLLCaseSchema.setMngCom(tLLCaseDB.getMngCom());
            mLLCaseSchema.setOperator(tLLCaseDB.getOperator());
            mLLCaseSchema.setMakeDate(tLLCaseDB.getMakeDate());
            mLLCaseSchema.setMakeTime(tLLCaseDB.getMakeTime());
            mLLCaseSchema.setModifyDate(CurrentDate);
            mLLCaseSchema.setModifyTime(CurrentTime);
            
            mLLCaseSchema.setBnfIDType(tLLCaseDB.getBnfIDType());//受益人证件类型
            mLLCaseSchema.setBnfSex(tLLCaseDB.getBnfSex());//受益人性别
            mLLCaseSchema.setBirthday(tLLCaseDB.getBirthday());//受益人出生日期
            mLLCaseSchema.setBnfName(tLLCaseDB.getBnfName());
            
            mLLCaseSchema.setBnfIDNo(tLLCaseDB.getBnfIDNo());// 受益人证件号码
            mLLCaseSchema.setBankAccNo(tLLCaseDB.getBankAccNo());
            mLLCaseSchema.setBnfAccName(tLLCaseDB.getBnfAccName());//受益人银行账户名
			
            mLLCaseSchema.setBankCode(tLLCaseDB.getBankCode());
            mLLCaseSchema.setRelationToInsured(tLLCaseDB.getRelationToInsured());// 与被保险人关系
            mLLCaseSchema.setCaseGetMode(tLLCaseDB.getCaseGetMode());// 领取方式
            mLLCaseSchema.setAccResult1(tLLCaseDB.getAccResult1());
            mLLCaseSchema.setAccResult2(tLLCaseDB.getAccResult2());

            //------------------------------------------------------------------BEG
            //理赔类型表,首先删除所有该分案下的所有理赔类型,然后再添加前台更改后数据
            //------------------------------------------------------------------
            deleteSql = " delete from LLAppClaimReason where "
                        + " RgtNo = '" + mLLCaseSchema.getRgtNo() + "'"
                        + " and CustomerNo = '" + mLLCaseSchema.getCustomerNo() + "'";
            //先删除伤残打印数据
            String tsql = "delete from LOPRTManager where "
                        + " otherno = '" + mRptNo + "'"
                        + " and code = 'PCT001'";
            map.put(tsql,"DELETE");
            for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++)
            {
                LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
                tLLAppClaimReasonDB.setCaseNo(mLLAppClaimReasonSet.get(i).getRgtNo());
                tLLAppClaimReasonDB.setRgtNo(mLLAppClaimReasonSet.get(i).getRgtNo());
                tLLAppClaimReasonDB.setCustomerNo(mLLAppClaimReasonSet.get(i).getCustomerNo());
                tLLAppClaimReasonDB.setReasonCode(mLLAppClaimReasonSet.get(i).getReasonCode());
                tLLAppClaimReasonDB.getInfo();
                if(tLLAppClaimReasonDB.getMakeDate() == null)
                {
                    mLLAppClaimReasonSet.get(i).setOperator(mG.Operator);
                    mLLAppClaimReasonSet.get(i).setMngCom(mG.ManageCom);
                    mLLAppClaimReasonSet.get(i).setMakeDate(CurrentDate);
                    mLLAppClaimReasonSet.get(i).setMakeTime(CurrentTime);
                    mLLAppClaimReasonSet.get(i).setModifyDate(CurrentDate);
                    mLLAppClaimReasonSet.get(i).setModifyTime(CurrentTime);
                    //如有伤残,则生成伤残打印数据
                    String tCode = mLLAppClaimReasonSet.get(i).getReasonCode().substring(1,3);
                    if (tCode.equals("01"))
                    {
                        //生成打印数据--伤残鉴定通知书
                        if (!insertLOPRTManager("PCT001"))
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    mLLAppClaimReasonSet.get(i).setSchema(tLLAppClaimReasonDB.getSchema());
                }
            }
            
            //点击修改或保存（报案信息）时，时工作流表更新受理日期		
			String tMissionID = (String) mTransferData.getValueByName("MissionID");
			String tSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
			String tActivityID = "0000009015";
			
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setMissionID(tMissionID);
			tLWMissionDB.setSubMissionID(tSubMissionID);
			tLWMissionDB.setActivityID(tActivityID);
			
			if(!tLWMissionDB.getInfo())
			{
				// @@错误处理
				CError.buildErr(this, "查询"+tLWMissionDB.getInfo()+"失败!");
				return false;
			}
			
			mLWMissionSchema=tLWMissionDB.getSchema();
			
			String tAcceptedDate = (String) mLLRegisterSchema.getAcceptedDate();
			mLWMissionSchema.setMissionProp21(tAcceptedDate);
			
			// ------------------------------------------------------------------End
			map.put(mLWMissionSchema, "DELETE&INSERT");
            map.put(mLLRegisterSchema, "DELETE&INSERT");
            map.put(mLLCaseSchema, "DELETE&INSERT");
            map.put(deleteSql, "DELETE");
            map.put(mLLAppClaimReasonSet, "DELETE&INSERT");
            map.put(tLLAccidentSchema,"DELETE&INSERT");
            tReturn = true;
        }
        //删除数据
        if (cOperate.equals("DELETE")){
            String CaseNo = mLLCaseSchema.getCaseNo();
            String CustomerNo = mLLCaseSchema.getCustomerNo();
            String deleteSq2 = " delete  from LLReportAffix where "
                               + " RptNo = '" + CaseNo + "'"
                               + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq3 = " delete  from LLReportReason where "
                               + " RpNo = '" + CaseNo + "'"
                               + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq4 = " delete  from LLSubReport where "
                               + " SubRptNo = '" + CaseNo + "'"
                               + " and CustomerNo = '" + CustomerNo + "'";
            //add by wood 立案删除必须增加对以下数据的删除********************
            String deleteSq5 = " delete  from llcase where "
                + " caseno = '" + CaseNo + "'"
                + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq6 = " delete  from LLAppClaimReason where "
                + " caseno = '" + CaseNo + "'"
                + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq7 = " delete  from LLAffix where "
                + " RgtNo = '" + CaseNo + "'"
                + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq8 = " delete  from llfeemain where "
                + " clmno = '" + CaseNo + "'"
                + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq9 = " delete  from llclaimdetail where "
                + " clmno = '" + CaseNo + "'"
                + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq10 = " delete  from llcasereceipt where "
                + " caseno = '" + CaseNo + "'"
                + " and CustomerNo = '" + CustomerNo + "'";
            String deleteSq11 = " delete  from llclaimpolicy where "
                + " clmno = '" + CaseNo + "'"
                + " and InsuredNo = '" + CustomerNo + "'";
            map.put(deleteSq5, "DELETE");
            map.put(deleteSq6, "DELETE");
            map.put(deleteSq7, "DELETE");
            map.put(deleteSq8, "DELETE");
            map.put(deleteSq9, "DELETE");
            map.put(deleteSq10, "DELETE");
            map.put(deleteSq11, "DELETE");
            //*****************************************************************
            map.put(deleteSq2, "DELETE");
            map.put(deleteSq3, "DELETE");
            map.put(deleteSq4, "DELETE");
            tReturn = true;
        }
        //更新mTransferData中的值
        if(!perpareMissionProp())
        {
            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBL";
            tError.functionName = "perpareMissionProp";
            tError.errorMessage = "为工作流准备数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return tReturn;
    }

    /**
     * 判断事件、报案、立案信息是否存在
     * @todo 1 事件号为空，则报案、立案都不存在；事件号不为空，则判断报案号。
     *       2 报案号为空，则立案不存在；报案号不为空，则查询立案是否存在。
     */
    private void IsExist()
    {
        //事件是否存在
        if (mLLAccidentSchema.getAccNo().equals("") ||
            mLLAccidentSchema.getAccNo() == null)
        {
            mIsAccExist = "false";
            mIsReportExist = false;
            mIsRegisterExist = false;
        }
        else
        {
            //事件存在
            mIsAccExist = "true";
            mAccNo = mLLAccidentSchema.getAccNo();
            logger.debug("-----已有事件号= " + mAccNo);
            if (mLLRegisterSchema.getRgtNo().equals("") ||
                mLLRegisterSchema.getRgtNo() == null)
            {
                //报案不存在
                mIsReportExist = false;
                mIsRegisterExist = false;
            }
            else
            {
                //报案存在
                mIsReportExist = true;
                mRptNo = mLLRegisterSchema.getRgtNo();
                logger.debug("-----已有赔案号= " + mRptNo);
                //查询判断立案是否存在
                String strSql = "select RgtNo from LLRegister where "
                                + "RgtNo = " + mLLRegisterSchema.getRgtNo();
                ExeSQL exesql = new ExeSQL();
                String tResult = exesql.getOneValue(strSql);
                if (tResult.length() > 0)
                {
                    mIsRegisterExist = true;
                }
                else
                {
                    mIsRegisterExist = false;
                }
            }
        }
    }

    /**
     * 处理报案信息
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean addReport()
    {
        logger.debug("---LLClaimRegisterBL Start addReport:" + mRptNo);
        LLReportBL tLLReportBL = new LLReportBL();
        //获取报案页面信息

        mLLAccidentSchema.setAccNo(mAccNo); //事件号(新事件时为空)
        mLLAccidentSchema.setAccDate(mLLRegisterSchema.getAccidentDate()); //意外事故发生日期
        mLLAccidentSchema.setAccType(mLLRegisterSchema.getAccidentReason()); //事故类型===出险原因
        mLLAccidentSchema.setAccPlace((mLLRegisterSchema.getAccidentSite()));//出险地点
        mLLAccidentSchema.setAccDesc(mLLCaseSchema.getAccdentDesc());//事故描述
        
        mLLReportSchema.setRptNo(mRptNo); //报案号=赔案号
        mLLReportSchema.setRptorName(mLLRegisterSchema.getRgtantName()); //报案人姓名
        mLLReportSchema.setRptorPhone(mLLRegisterSchema.getRgtantPhone()); //报案人电话
        mLLReportSchema.setRptorAddress(mLLRegisterSchema.getRgtantAddress()); //报案人通讯地址
        mLLReportSchema.setRelation(mLLRegisterSchema.getRelation()); //报案人与事故人关系
        mLLReportSchema.setRptMode("05"); //报案方式(立案没有，故置为其他方式)
        mLLReportSchema.setAccidentSite(mLLRegisterSchema.getAccidentSite()); //出险地点
        mLLReportSchema.setAccidentDate(mLLRegisterSchema.getAccidentDate()); //意外事故发生日期
        mLLReportSchema.setAccidentReason(mLLRegisterSchema.getAccidentReason()); //出险原因
        mLLReportSchema.setRgtFlag("30");//立案标志(来自立案)
        mLLReportSchema.setRgtClass(mLLRegisterSchema.getRgtClass()); //团单个单
        mLLReportSchema.setAppntNo(mLLRegisterSchema.getAppntNo());//团体客户号
        mLLReportSchema.setGrpContNo(mLLRegisterSchema.getGrpContNo());//团体保单号

        //mLLSubReportSchema=new LLSubReportSchema();//报案分案信息
		mLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=报案号=赔案号
		mLLSubReportSchema.setCustomerNo(mLLCaseSchema.getCustomerNo()); // 出险人编码
		mLLSubReportSchema.setCustomerName(mLLCaseSchema.getCustomerName());//出险人姓名
		mLLSubReportSchema.setSex(mLLCaseSchema.getCustomerSex());//出险人性别
		
		mLLSubReportSchema.setMedAccDate(mLLCaseSchema.getMedAccDate());//医疗出险日期
		mLLSubReportSchema.setAccDate(mLLCaseSchema.getAccDate()); //其他出险日期
		mLLSubReportSchema.setHospitalCode(mLLCaseSchema.getHospitalCode()); // 治疗医院
		mLLSubReportSchema.setHospitalName(mLLCaseSchema.getHospitalName());//治疗医院名称
		mLLSubReportSchema.setAccidentDetail(mLLCaseSchema.getAccidentDetail()); // 出险细节
		
		// mLLSubReportSchema.setDieFlag(mLLCaseSchema.getDieFlag()); //死亡标志
		mLLSubReportSchema.setCureDesc(mLLCaseSchema.getCureDesc()); // 治疗情况
		mLLSubReportSchema.setSeqNo(mLLCaseSchema.getSeqNo());//序号
		
		mLLSubReportSchema.setRemark("直接立案自动补充报案数据"); // 备注
		mLLSubReportSchema.setAccDesc(mLLCaseSchema.getAccdentDesc());
		mLLSubReportSchema.setAccResult1(mLLCaseSchema.getAccResult1());// ICD10主代码
		mLLSubReportSchema.setAccResult2(mLLCaseSchema.getAccResult2());// ICD10子代码

//        for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++)
//        {
//            mLLReportReasonSchema = new LLReportReasonSchema();
//            mLLReportReasonSchema.setRpNo(mLLAppClaimReasonSet.get(i).getCaseNo()); //报案号=赔案号
//            mLLReportReasonSchema.setCustomerNo(mLLAppClaimReasonSet.get(i).
//                                                getCustomerNo()); //出险人编码
//            mLLReportReasonSchema.setReasonCode(mLLAppClaimReasonSet.get(i).
//                                                getReasonCode()); //理赔类型
//            mLLReportReasonSet.add(mLLReportReasonSchema);
//        }

        VData tVData = new VData();
        tVData.add(mG);
        tVData.add(mLLAccidentSchema);
        tVData.add(mLLReportSchema);
        tVData.add(mLLSubReportSchema);
        tVData.add(mLLReportReasonSet);

        if (!tLLReportBL.submitData(tVData, "INSERT"))
        {
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
        }
        else
        {
            //取得处理完的返回数据
            VData tempVData = new VData();
            tempVData = tLLReportBL.getResult();
            MMap tempMap = new MMap();
            tempMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            map.add(tempMap);
            LLReportSchema tLLReportSchema = new LLReportSchema();
            tLLReportSchema = (LLReportSchema) tempVData.getObjectByObjectName("LLReportSchema", 0);
            mRptNo = tLLReportSchema.getRptNo();
            logger.debug("返回生成的RptNo:"+mRptNo);
            tempVData.clear();
        }
        return true;
    }

    /**
     * 添加打印数据
     * 2005-8-16 14:49
     * @return boolean
     */
    private boolean insertLOPRTManager(String tCode)
    {
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
     * 更新mTransferData中的值，为工作流准备数据
     * @return boolean
     */
    private boolean perpareMissionProp()
    {
    	mTransferData.removeByName("RptNo");
		mTransferData.removeByName("RgtNo");
		mTransferData.removeByName("RptorState");
		mTransferData.setNameAndValue("RptNo", mRptNo);
		mTransferData.setNameAndValue("RgtNo", mRgtNo);
		mTransferData.setNameAndValue("IsAccExist", mIsAccExist);
		mTransferData.setNameAndValue("RptorState", "20");
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
            //后台传送
            //前台传
            //mResult.clear();
            mResult.add(mG);
            mResult.add(map);
            mResult.add(mTransferData);
        }
        catch (Exception ex)
        {
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

    
    /**
	 * 创建新的立案号(赔案号)
	 */
	private boolean createRgtno() {
//		团体立案号生成规则:
		//（1）普通案件："86XXXX"+4位年度号+061+7位流水号； 
		//（2）批次案件："86XXXX"+4位年度号+062+7位流水号；
		//（3）帐户案件："86XXXX"+4位年度号+063+7位流水号；
		//需要判断案件类型
		LLReportDB tLLReportDB = new LLReportDB();
		tLLReportDB.setRptNo(mRptNo);
		if(!tLLReportDB.getInfo()){
			CError.buildErr(this, "查询报案信息失败！");
			return false;
		}
		logger.debug("案件类型是："+tLLReportDB.getRgtState());
		if("11".equals(tLLReportDB.getRgtState())||"02".equals(tLLReportDB.getRgtState())
				||"03".equals(tLLReportDB.getRgtState())){
			
		}else{
			CError.buildErr(this, "获得的案件类型有误！");
			return false;
		}
		logger.debug("开始创建立案号(赔案号)");
		String strlimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strlimit);
		mRgtNo = PubFun1.CreateMaxNo("GRPRGTNO"+tLLReportDB.getRgtState(), strlimit);
		logger.debug("-----生成立案号= " + mRgtNo);
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
