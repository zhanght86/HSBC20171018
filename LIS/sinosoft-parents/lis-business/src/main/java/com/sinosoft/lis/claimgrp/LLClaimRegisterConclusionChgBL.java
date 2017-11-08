/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.*;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.lis.db.LLReportDB;
import com.sinosoft.lis.vschema.LLReportSet;
import com.sinosoft.lis.db.LLSubReportDB;
import com.sinosoft.lis.vschema.LLSubReportSet;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLReportReasonDB;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.db.LLReportAffixDB;
import com.sinosoft.lis.vschema.LLReportAffixSet;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.db.LLCaseDB;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLClaimRegisterConclusionChgBL
{
private static Logger logger = Logger.getLogger(LLClaimRegisterConclusionChgBL.class);

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
    private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();
    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LLAppClaimReasonSchema mLLAppClaimReasonSchema = new
            LLAppClaimReasonSchema();
    private LLCaseSet mLLCaseSet = new LLCaseSet();
    private LLAffixSet mLLAffixSet = new LLAffixSet();

    private String mNoRgtReason = ""; //不予立案原因
    private String mConclusionSave = "";
    private String mRptNo = "";
    private String mRgtState = "";
    private String mBeAdjSum = ""; //调整金额
    private String mCusNo = ""; //客户号码

    public LLClaimRegisterConclusionChgBL()
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
                "----------LLClaimRegisterConclusionChgBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionChgBL after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionChgBL after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionChgBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionChgBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterConclusionChgBL";
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
        logger.debug("---LLClaimRegisterConclusionChgBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
        mLLClaimDetailSet = (LLClaimDetailSet) mInputData.
                               getObjectByObjectName("LLClaimDetailSet", 0);
        mRptNo = (String) mTransferData.getValueByName("RptNo");
        mNoRgtReason = (String) mTransferData.getValueByName("NoRgtReason");
        mConclusionSave = (String) mTransferData.getValueByName("RgtConclusion");
        mRgtState = (String) mTransferData.getValueByName("RgtState");
        mBeAdjSum = (String) mTransferData.getValueByName("BeAdjSum");

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
//                tError.moduleName = "LLClaimRegisterConclusionChgBL";
//                tError.functionName = "checkInputData";
//                tError.errorMessage = "接受的意外事故发生日期为空!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//            if (mLLCaseSchema.getCustomerNo() == null)//出险人编码
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimRegisterConclusionChgBL";
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
//            tError.moduleName = "LLClaimRegisterConclusionChgBL";
//            tError.functionName = "checkInputData";
//            tError.errorMessage = "在校验输入的数据时出错!";
//            this.mErrors.addOneError(tError);
//            return false;
//        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("---LLClaimRegisterConclusionChgBL start dealData()...");
        boolean tReturn = false;

        //----------------------------------------------------------------------BEG
        //处理1: 更新立案结论,更改保项结论
        //----------------------------------------------------------------------
        if (cOperate.equals("UPDATE"))
        {
            //更新立案表立案结论字段
            LLRegisterDB tLLRegisterDB = new LLRegisterDB();
            tLLRegisterDB.setRgtNo(mRptNo);
            tLLRegisterDB.getInfo();
            mLLRegisterSchema = tLLRegisterDB.getSchema();
            mLLRegisterSchema.setRgtConclusion(mConclusionSave);
            mLLRegisterSchema.setNoRgtReason(mNoRgtReason);
            mLLRegisterSchema.setRgtState(mRgtState); //案件类型
            mLLRegisterSchema.setBeAdjSum(mBeAdjSum); //调整后金额
            //如果不予立案,则设置结案标志和日期
            if (mConclusionSave.equals("02"))
            {
                mLLRegisterSchema.setEndCaseFlag("1");
                mLLRegisterSchema.setEndCaseDate(CurrentDate);
                mLLRegisterSchema.setClmState("70");

                String caseSql="update llcase set RgtState='70',EndCaseDate='"+CurrentDate+"',EndCaseFlag='1' where caseno='"+mRptNo+"' ";
                map.put(caseSql, "UPDATE");

                String clmSql="update llclaim set ClmState='70',EndCaseDate='"+CurrentDate+"' where clmno='"+mRptNo+"' ";
                map.put(clmSql, "UPDATE");
            }else{
                String caseSql="update llcase set RgtState='20' where caseno='"+mRptNo+"' ";
                map.put(caseSql, "UPDATE");

                String clmSql="update llclaim set ClmState='20' where clmno='"+mRptNo+"' ";
                map.put(clmSql, "UPDATE");

                mLLRegisterSchema.setClmState("20");
            }

            String caseSql="update llcase set RgtState='20' where caseno='"+mRptNo+"' ";
            map.put(caseSql, "UPDATE");

            String clmSql="update llclaim set ClmState='20' where clmno='"+mRptNo+"' ";
            map.put(clmSql, "UPDATE");

            mLLRegisterSchema.setClmState("20");
            mLLRegisterSchema.setModifyDate(CurrentDate);
            mLLRegisterSchema.setModifyTime(CurrentTime);

            map.put(mLLRegisterSchema, "DELETE&INSERT");

//            //更新分案表立案结论字段
//            LLCaseDB tLLCaseDB = new LLCaseDB();
//            tLLCaseDB.setRgtNo(mRptNo);
//            LLCaseSet tLLCaseSet = new LLCaseSet();
//            tLLCaseSet = tLLCaseDB.query();
//            if (tLLCaseSet == null && tLLCaseSet.size() == 0)
//            {
//                //@@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimRegisterConclusionChgBL";
//                tError.functionName = "dealData";
//                tError.errorMessage = "查询分案信息失败!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//            else
//            {
//                for (int j = 1; j <= tLLCaseSet.size(); j++)
//                {
//                    LLCaseSchema tLLCaseSchema = new LLCaseSchema();
//                    tLLCaseSchema.setSchema(tLLCaseSet.get(j));
//                    tLLCaseSchema.setRgtType(mRgtState);//案件类型
//                    mLLCaseSet.add(tLLCaseSchema);
//                }
//            }
//            map.put(mLLCaseSet, "DELETE&INSERT");

            //更改给付结论
            if (mLLClaimDetailSet.size() >= 1)
            {
                for (int i = 1; i <= mLLClaimDetailSet.size(); i++)
                {

                    String strSql = "";

                    //续涛,2005-08-08加入,如果给付编码为2,代表该保项不给,但是不属于拒付
                    if (mLLClaimDetailSet.get(i).getGiveType().equals("2"))
                    {
                        strSql = " update llclaimdetail"
                            + " set givetype = '"+
                            mLLClaimDetailSet.get(i).getGiveType()+"',"
                            + " StandPay = 0 ,"
                            + " RealPay = 0 "
                            + " where clmno = '" +
                            mLLClaimDetailSet.get(i).getClmNo() + "'"
                            + " and caseno = '" +
                            mLLClaimDetailSet.get(i).getCaseNo() + "'"
                            + " and polno = '" +
                            mLLClaimDetailSet.get(i).getPolNo() + "'"
                            + " and dutycode = '" +
                            mLLClaimDetailSet.get(i).getDutyCode() + "'"
                            + " and getdutykind = '" +
                            mLLClaimDetailSet.get(i).getGetDutyKind() + "'"
                            + " and getdutycode ='" +
                            mLLClaimDetailSet.get(i).getGetDutyCode() + "'"
                            + " and caserelano = '" +
                            mLLClaimDetailSet.get(i).getCaseRelaNo() + "'";
                    }
                    else
                    {
                        strSql = " update llclaimdetail"
                            + " set givetype = '"+
                            mLLClaimDetailSet.get(i).getGiveType()+"'"
                            + " where clmno = '" +
                            mLLClaimDetailSet.get(i).getClmNo() + "'"
                            + " and caseno = '" +
                            mLLClaimDetailSet.get(i).getCaseNo() + "'"
                            + " and polno = '" +
                            mLLClaimDetailSet.get(i).getPolNo() + "'"
                            + " and dutycode = '" +
                            mLLClaimDetailSet.get(i).getDutyCode() + "'"
                            + " and getdutykind = '" +
                            mLLClaimDetailSet.get(i).getGetDutyKind() + "'"
                            + " and getdutycode ='" +
                            mLLClaimDetailSet.get(i).getGetDutyCode() + "'"
                            + " and caserelano = '" +
                            mLLClaimDetailSet.get(i).getCaseRelaNo() + "'";
                    }

                    logger.debug("------------------------------------------------------");
                    logger.debug("--LLClaimRegisterConclusionChgBL--更新立案保项结论!"+strSql);
                    logger.debug("------------------------------------------------------");
                    map.put(strSql, "UPDATE");
                }
            }
        }
        //----------------------------------------------------------------------END

        //----------------------------------------------------------------------BEG
        //处理2: 数据插入到打印管理表
        //----------------------------------------------------------------------
        //首先删除
        /*
        String tDSql = "delete from LOPRTManager where 1=1 "
                       + " and OtherNo = '" + mRptNo + "'"
                       + " and (Code = 'PCT003' or code = 'PCT004' or code = 'PCT007')";
        map.put(tDSql, "DELETE");

        //查询出险人
        String tSSql = "select customerno from llcase where 1=1 "
                       + " and caseno = '" + mRptNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        mCusNo = tExeSQL.getOneValue(tSSql);

        if (!insertLOPRTManager("PCT004")) //插入单证签收清单
        {
            return false;
        }
        if (mConclusionSave.equals("02")) //不予立案
        {
            if (!insertLOPRTManager("PCT007"))
            {
                return false;
            }
        }
        else if (mConclusionSave.equals("03")) //延迟立案
        {
            if (!insertLOPRTManager("PCT003"))
            {
                return false;
            }
        }
        */
        //----------------------------------------------------------------------END

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
        //插入新值
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
        tLOPRTManagerSchema.setMakeDate(CurrentDate); //入机日期
        tLOPRTManagerSchema.setMakeTime(CurrentTime); //入机时间
        tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
        tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);
        tLOPRTManagerSchema.setStandbyFlag4(mCusNo); //客户号码
        tLOPRTManagerSchema.setStandbyFlag5(CurrentDate); //立案日期
        tLOPRTManagerSchema.setStandbyFlag6("20"); //赔案状态

        map.put(tLOPRTManagerSchema, "INSERT");
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
            tError.moduleName = "LLClaimRegisterConclusionChgBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
}
