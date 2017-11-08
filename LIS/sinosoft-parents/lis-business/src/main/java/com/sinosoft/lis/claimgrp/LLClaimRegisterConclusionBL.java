/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAppClaimReasonSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLClaimRegisterConclusionBL
{
private static Logger logger = Logger.getLogger(LLClaimRegisterConclusionBL.class);

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
    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private LLCaseSet mLLCaseSet = new LLCaseSet();
    private LLAffixSet mLLAffixSet = new LLAffixSet();

    private String mNoRgtReason = ""; //不予立案原因
    private String mDeferRgtReason = ""; //延迟立案原因
    private String mDeferRgtRemark = ""; //延迟立案备注
    private String mConclusionSave = "";
    private String mRptNo = "";
    private String mRgtState = "";
    private String mBeAdjSum = ""; //调整金额
    private String mCusNo = ""; //客户号码
    private String mGrpFlag = ""; //团单标记
    private String mRgtFlag = "0"; //用来区分立案操作和延迟立案操作的标志，0-延迟立案；1—立案操作
    public LLClaimRegisterConclusionBL()
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
                "----------LLClaimRegisterConclusionBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionBL after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionBL after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimRegisterConclusionBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterConclusionBL";
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
        logger.debug("---LLClaimRegisterConclusionBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
        mLLClaimDetailSet = (LLClaimDetailSet) mInputData.
                               getObjectByObjectName("LLClaimDetailSet", 0);
        mRptNo = (String) mTransferData.getValueByName("RptNo");
        mDeferRgtRemark = (String) mTransferData.getValueByName("DeferRgtRemark");
        mDeferRgtReason = (String) mTransferData.getValueByName("DeferRgtReason");
        mNoRgtReason = (String) mTransferData.getValueByName("NoRgtReason");
        mConclusionSave = (String) mTransferData.getValueByName("RgtConclusion");
        mRgtState = (String) mTransferData.getValueByName("RgtState");
        mBeAdjSum = (String) mTransferData.getValueByName("BeAdjSum");
        mGrpFlag = (String)mTransferData.getValueByName("GrpFlag");
        mRgtFlag = (String)mTransferData.getValueByName("RgtFlag");
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
//                tError.moduleName = "LLClaimRegisterConclusionBL";
//                tError.functionName = "checkInputData";
//                tError.errorMessage = "接受的意外事故发生日期为空!";
//                this.mErrors.addOneError(tError);
//                return false;
//            }
//            if (mLLCaseSchema.getCustomerNo() == null)//出险人编码
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LLClaimRegisterConclusionBL";
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
//            tError.moduleName = "LLClaimRegisterConclusionBL";
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
        logger.debug("---LLClaimRegisterConclusionBL start dealData()...");
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
            mLLRegisterSchema.setDeferRgtReason(mDeferRgtReason);
            mLLRegisterSchema.setDeferRgtRemark(mDeferRgtRemark);
            mLLRegisterSchema.setRgtState(mRgtState); //案件类型
            mLLRegisterSchema.setBeAdjSum(mBeAdjSum); //调整后金额
            
            mLLRegisterSchema.setCasePayType(mLLClaimPubFunBL.getCheckCasePayType(mRptNo));
            
			//99代表案件超过某种理赔类型的最大给付金,需要发起强制调查,但案件类型依然是一般给付件
			if(mLLRegisterSchema.getCasePayType().equals("99"))
			{
				mLLRegisterSchema.setCasePayType("0");
			}
			
            mLLRegisterSchema.setModifyDate(CurrentDate);
            mLLRegisterSchema.setModifyTime(CurrentTime);
            
            //如果不予立案,则设置结案标志和日期
            if (mConclusionSave.equals("02")) {
                mLLRegisterSchema.setEndCaseFlag("1");
                mLLRegisterSchema.setEndCaseDate(CurrentDate);
                mLLRegisterSchema.setClmState("70");

                String caseSql="update llclaimpolicy set ClmState='70',EndCaseDate='"+CurrentDate+"' where caseno='"+mRptNo+"' ";
                map.put(caseSql, "UPDATE");

                String clmSql="update llclaim set ClmState='70',EndCaseDate='"+CurrentDate+"',EndCaseFlag='1' where clmno='"+mRptNo+"' ";
                map.put(clmSql, "UPDATE");
            }
            else
            {
                String caseSql="update llclaimpolicy set ClmState='20',casepaytype='"+mLLRegisterSchema.getCasePayType()+"' where caseno='"+mRptNo+"' ";
                map.put(caseSql, "UPDATE");

                String clmSql="update llclaim set ClmState='20',casepaytype='"+mLLRegisterSchema.getCasePayType()+"' where clmno='"+mRptNo+"' ";
                map.put(clmSql, "UPDATE");

                mLLRegisterSchema.setClmState("20");

                if (mConclusionSave.equals("03") && mRgtFlag.equals("1")) //如果是做延迟立案操作，则记录回退次数
                {
                    String rptSql="update llreport set ReturnMode=nvl(ReturnMode,0)+1 where rptno='"+mRptNo+"' ";
                    map.put(rptSql, "UPDATE");
                }
            }


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
        //                tError.moduleName = "LLClaimRegisterConclusionBL";
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
            if (!mGrpFlag.equals("1")) {//团体立案不走这个流程
                //更改给付结论
                if (mLLClaimDetailSet.size() >= 1) {
                    for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {

                        String strSql = "";

                        //续涛,2005-08-08加入,如果给付编码为2,代表该保项不给,但是不属于拒付
                        if (mLLClaimDetailSet.get(i).getGiveType().equals("2")) {
                            strSql = " update llclaimdetail"
                                     + " set givetype = '" + //赔付结论
                                     mLLClaimDetailSet.get(i).getGiveType() + "',"
                                     + " StandPay = 0 ," //核算赔付金额
                                     + " RealPay = 0 " //核赔赔付金额
                                     + " where clmno = '" + //赔案号
                                     mLLClaimDetailSet.get(i).getClmNo() + "'"
                                     + " and caseno = '" + //分案号
                                     mLLClaimDetailSet.get(i).getCaseNo() + "'"
                                     + " and polno = '" + //保单号
                                     mLLClaimDetailSet.get(i).getPolNo() + "'"
                                     + " and dutycode = '" + //责任编码
                                     mLLClaimDetailSet.get(i).getDutyCode() + "'"
                                     + " and getdutykind = '" + //给付责任类型
                                     mLLClaimDetailSet.get(i).getGetDutyKind() + "'"
                                     + " and getdutycode ='" + //给付责任编码
                                     mLLClaimDetailSet.get(i).getGetDutyCode() + "'"
                                     + " and caserelano = '" + //受理事故号
                                     mLLClaimDetailSet.get(i).getCaseRelaNo() + "'";
                        } else {
                            strSql = " update llclaimdetail"
                                     + " set givetype = '" +
                                     mLLClaimDetailSet.get(i).getGiveType() + "'"
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

                        logger.debug(
                                "------------------------------------------------------");
                        logger.debug("--LLClaimRegisterConclusionBL--更新立案保项结论!" +
                                           strSql);
                        logger.debug(
                                "------------------------------------------------------");
                        map.put(strSql, "UPDATE");
                    }
                }
            }
        }
        //----------------------------------------------------------------------END

        //----------------------------------------------------------------------BEG
        //处理2: 数据插入到打印管理表
        //----------------------------------------------------------------------
        //首先删除
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
            tError.moduleName = "LLClaimRegisterConclusionBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
}
