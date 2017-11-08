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
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.bq.BqPolBalBL;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔保单(险种)结算</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version: 1.0
 */
public class LLClaimPolDealBLF
{
private static Logger logger = Logger.getLogger(LLClaimPolDealBLF.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mVData = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private MMap mMap = new MMap();

    /** 全局数据 */
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private TransferData cTransferData = new TransferData(); //向BL传送数据用
    private Reflections mReflections = new Reflections();

    private String mClmNo = ""; //赔案号
    private String mContNo = ""; //合同号
    private String mAccDate = ""; //意外事故发生日期
    private String mRgtDate = ""; //立案日期
    private String mFeeOperationType = ""; //业务类型
    private String mSubFeeOperationType = ""; //子业务类型
    private String mFeeFinaType = ""; //财务类型
    private double mCal = 0; //赔付金额

    public LLClaimPolDealBLF()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LLClaimConfirmPassBL begin submitData----------");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after getInputData----------");
        if (!dealData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after dealData----------");
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after prepareOutputData----------");
        //进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return true or false
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        mInputData = (VData) cInputData.clone();
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);

        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号

        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No0.1 得到计算需要的信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getPrepareCalInfo())
        {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 查询出需要做保单结算的合同号
         * 注：产品定义定义的特殊副险项目，需要核实判断方法！
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "Select distinct a.contno from LLClaimDetail a, LMRiskApp b "
               + " where a.clmno = '" + mClmNo + "'"
               + " and a.givetype != '1'"
               + " and a.RiskCode = b.RiskCode"
               + " and (b.SubRiskFlag = 'M'"
               + " or a.getdutycode in ("
               + " select getdutycode from LMDutyGetClm "
               + " Where Getdutycode = a.getdutycode "
               + " and EffectOnMainRisk in('01','02')))";

        ExeSQL exesql = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS = exesql.execSQL(tSql);
        int tCount = tSSRS.getMaxRow();
        if (tCount >= 1)
        {
            for (int j = 1; j <= tCount; j++)
            {
                mContNo = tSSRS.GetText(j, 1);
                if (!mContNo.equals(""))
                {
                    if (!getLLPolDeal())
                    {
                        return false;
                    }
                }
            }
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 更改赔案表的保单结算标志
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql2 = "update llclaim set conbalflag = '1' where "
                     + " clmno = '" + mClmNo + "'";
        mMap.put(tSql2, "UPDATE");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 添加补交保费通知书打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //首先删除
        String tDSql = "delete from LOPRTManager where 1=1 "
                       + " and OtherNo = '" + mClmNo + "'"
                       + " and Code = 'PCT008'";
        mMap.put(tDSql, "DELETE");

        if (!insertLOPRTManager("PCT008")) //插入补交保费通知书
        {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 删除受益人分配信息,待重新分配
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tDSql2 = "delete from LLBnf where ClmNo='" + mClmNo + "'"
                        + " and BnfKind = 'A'";
        mMap.put(tDSql2, "DELETE");

        String tDSql3 = "delete from LJSGet where OtherNo='" + mClmNo + "'";
        mMap.put(tDSql3, "DELETE");

        String tDSql4 = "delete from LJSGetClaim where OtherNo='" + mClmNo + "'";
        mMap.put(tDSql4, "DELETE");

        return true;
    }

    /**
     * 得到结算需要的信息
     * @return boolean
     */
    private boolean getPrepareCalInfo()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 得到意外事故发生日期
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "select to_char(a.accDate,'yyyy-mm-dd') from LLAccident a,LLCaseRela b where 1=1 "
               + " and a.AccNo = b.CaseRelaNo "
               + " and b.CaseNo in ('" + this.mClmNo + "')";

        ExeSQL tExeSQL = new ExeSQL();
        String tAccDate = tExeSQL.getOneValue(tSql);
        if (tAccDate == null || tAccDate.equals(""))
        {
            mErrors.addOneError("意外事故发生日期没有找到!");
            return false;
        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 查询立案日期
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql2 = "select to_char(rgtdate,'yyyy-mm-dd') from llregister a where "
                      + " a.rgtno = '" + mClmNo + "'";
        ExeSQL tExeSQL2 = new ExeSQL();
        String tRgtDate = tExeSQL2.getOneValue(tSql2);
        if (tRgtDate.equals("") || tRgtDate == null)
        {
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getPolDealC0301";
            tError.errorMessage = "查询立案日期失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mAccDate = tAccDate.substring(0, 10).trim();
        mRgtDate = tRgtDate.substring(0, 10).trim();

        cTransferData.setNameAndValue("AccDate", mAccDate);
        cTransferData.setNameAndValue("ClmNo", mClmNo);

        return true;
    }

    /**
     * 根据终止结论,对理赔的合同状态表进行操作
     * @return boolean
     */
    private boolean getLLPolDeal()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 根据合同号查询出保单险种表的数据
         *       加入合同状态表的Terminate状态为0有效的判断条件
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "select LCPol.* from LCPol where 1=1 "
               + " and LCPol.ContNo = '" + mContNo + "'";

        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSql);
        if (tLCPolSet == null || tLCPolSet.size() == 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "没有查询到保单险种信息状态为有效的数据!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 保单结算前清空改赔案下所有保单结算信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tDsql = "";
        tDsql = " delete from LLBalance where ClmNo='" + mClmNo + "'"
                + " and ContNo = '" + mContNo + "'"
                + " and FeeOperationType like 'C%'";

        mMap.put(tDsql, "DELETE");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据保单(险种)进行结算操作
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 1; i <= tLCPolSet.size(); i++)
        {
            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema.setSchema(tLCPolSet.get(i));;
            mVData.add(tLCPolSchema);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.1 保单结算－－退出险以后所交的保费
             *  对应的业务类型编码为C01,C0101
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!getPolDealC01(tLCPolSchema))
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.2 保单结算－－补交保费 加 利息
             *  对应的业务类型编码为C02,C0201和C0202
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!getPolDealC02(tLCPolSchema))
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.3 保单结算－－质押贷款
             *  对应的业务类型编码为C03
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            //清偿贷款C0301
            if (!getPolDealC0301(tLCPolSchema))
            {
                return false;
            }
            //清偿利息C0302
            if (!getPolDealC0302(tLCPolSchema))
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.4 保单结算－－退生存金\养老金(客户退还保险公司)
             *  对应的业务类型编码为C04,C0401
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!getPolDealC0401(tLCPolSchema))
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.5 保单结算－－红利
             *  对应的业务类型编码为C05
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tBonusFlag = getBonusFlag(tLCPolSchema.getRiskCode());
            if (tBonusFlag.equals("Y"))
            {
//                //累计红利保额C0501
//                if (!getPolDealC0501(tLCPolSchema))
//                {
//                    return false;
//                }
                //终了红利C0502
                if (!getPolDealC0502(tLCPolSchema))
                {
                    return false;
                }
            }
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.6 保单结算－－利差返还
             *  对应的业务类型编码为C06,C0601
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
//            if (!getPolDealC0601(tLCPolSchema))
//            {
//                return false;
//            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.7 保单结算－－自动垫缴
             *  对应的业务类型编码为C07,C0701
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            //自动垫缴C0701
            if (!getPolDealC0701(tLCPolSchema))
            {
                return false;
            }
            //自动垫缴利息C0702
            if (!getPolDealC0702(tLCPolSchema))
            {
                return false;
            }

//2005-8-14 15:36 注释掉 周磊 原因:与退出险之后保费在主险重复
//            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//             * No3.8 保单结算－－保费豁免
//             *  对应的业务类型编码为C08,C0801
//             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//             */
//            if (!getPolDealC0801(tLCPolSchema))
//            {
//                return false;
//            }

        }

        return true;
    }

    /**
     *  对应保单结算退出险以后的保费
     *  对应的业务类型编码为C01,C0101
     * @return boolean
     */
    private boolean getPolDealC01(LCPolSchema tLCPolSchema)
    {
        mVData.clear();
        mVData.add(mGlobalInput);
        mVData.add(tLCPolSchema);
        cTransferData.removeByName("FeeOperationType");
        cTransferData.removeByName("SubFeeOperationType");
        cTransferData.setNameAndValue("FeeOperationType", "C01");
        cTransferData.setNameAndValue("SubFeeOperationType", "C0101");
        mVData.add(cTransferData);

        LLBalRecedeFeeAfterBL tLLBalRecedeFeeAfterBL = new
                LLBalRecedeFeeAfterBL();
        if (tLLBalRecedeFeeAfterBL.submitData(mVData, "") == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLBalRecedeFeeAfterBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getPolDealC01";
            tError.errorMessage = "退费保单结算数据查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            VData tempVData = tLLBalRecedeFeeAfterBL.getResult();
            MMap tMMap = new MMap();
            tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            mMap.add(tMMap);
        }

        return true;
    }


    /**
     * 保单结算--补交保费
     * 对应的业务类型编码为C02,C0201和C0202
     * @return boolean
     */
    private boolean getPolDealC02(LCPolSchema tLCPolSchema)
    {
        mVData.clear();
        mVData.add(mGlobalInput);
        mVData.add(tLCPolSchema);
        cTransferData.removeByName("FeeOperationType");
        cTransferData.removeByName("SubFeeOperationType");
        cTransferData.removeByName("FeeFinaType");
        cTransferData.setNameAndValue("FeeOperationType", "C02");
        cTransferData.setNameAndValue("SubFeeOperationType", "C0201");
        cTransferData.setNameAndValue("FeeFinaType", "BF");
        mVData.add(cTransferData);

        LLBalRecruitFeeBL tLLBalRecruitFeeBL = new LLBalRecruitFeeBL();
        if (tLLBalRecruitFeeBL.submitData(mVData, "") == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLBalRecruitFeeBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getPolDealC01";
            tError.errorMessage = "补交保费处理失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            VData tempVData = tLLBalRecruitFeeBL.getResult();
            MMap tMMap = new MMap();
            tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            mMap.add(tMMap);
        }
        return true;
    }

    /**
     * 保单结算－－质押贷款--清偿贷款本金
     * 对应的业务类型编码为C03,C0301
     * @return boolean
     */
    private boolean getPolDealC0301(LCPolSchema tLCPolSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));
        //调用保全方法
        BqPolBalBL tBqPolBalBL = new BqPolBalBL();
        if (tBqPolBalBL.calLoanCorpus(tLCPolSchema, mRgtDate))
        {
            t_Sum_Return = tBqPolBalBL.getCalResult();
        }
        if (t_Sum_Return <= 0)
        {
            t_Sum_Return = 0;
            logger.debug("#############################");
            logger.debug("#计算质押贷款--清偿贷款本金为零！");
            logger.debug("#############################");
            return true;
        }
        //准备数据
        mFeeOperationType = "C03";
        mSubFeeOperationType = "C0301";
        mFeeFinaType = "HK";
        mCal = 0 - t_Sum_Return;

        if (!updateLLBalance(tLCPolSchema))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 保单结算－－质押贷款--清偿利息
     * 对应的业务类型编码为C03,C0302
     * @return boolean
     */
    private boolean getPolDealC0302(LCPolSchema tLCPolSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));
        //调用保全方法
        BqPolBalBL tBqPolBalBL = new BqPolBalBL();
        if (tBqPolBalBL.calLoanInterest(tLCPolSchema, mRgtDate))
        {
            t_Sum_Return = tBqPolBalBL.getCalResult();
        }
        if (t_Sum_Return <= 0)
        {
            t_Sum_Return = 0;
            logger.debug("#############################");
            logger.debug("#####计算质押贷款--清偿利息为零！");
            logger.debug("#############################");
            return true;
        }
        //准备数据
        mFeeOperationType = "C03";
        mSubFeeOperationType = "C0302";
        mFeeFinaType = "LX";
        mCal = 0 - t_Sum_Return;

        if (!updateLLBalance(tLCPolSchema))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 保单结算，退生存金养老金(客户退还保险公司)
     * 对应的业务类型编码为C04,C0401
     * @return boolean
     */
    private boolean getPolDealC0401(LCPolSchema tLCPolSchema)
    {
        mVData.clear();
        mVData.add(mGlobalInput);
        mVData.add(tLCPolSchema);
        cTransferData.removeByName("FeeOperationType");
        cTransferData.removeByName("SubFeeOperationType");
        cTransferData.removeByName("FeeFinaType");
        cTransferData.setNameAndValue("FeeOperationType", "C04");
        cTransferData.setNameAndValue("SubFeeOperationType", "C0401");
        cTransferData.setNameAndValue("FeeFinaType", "EF");
        mVData.add(cTransferData);

        LLBalAliveFeeBL tLLBalAliveFeeBL = new LLBalAliveFeeBL();
        if (tLLBalAliveFeeBL.submitData(mVData, "") == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLBalAliveFeeBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getPolDealC01";
            tError.errorMessage = "退生存金养老金处理失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            VData tempVData = tLLBalAliveFeeBL.getResult();
            MMap tMMap = new MMap();
            tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            mMap.add(tMMap);
        }
        return true;
    }

    /**
     * 保单结算－－红利--累计红利保额
     * 对应的业务类型编码为C05,C0501
     * @return boolean
     */
    private boolean getPolDealC0501(LCPolSchema tLCPolSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));

        String tSql = "";
        ExeSQL tExeSQL = new ExeSQL();

        //得到当前年度
        String tYear = CurrentDate.substring(0, 4);

        //得到年度红利
        tSql = "select nvl(a.BonusAmnt,0) from LOEngBonusPol a where 1=1 "
               + " and a.PolNo ='" + tLCPolSchema.getPolNo() + "'";
//               + " and a.FiscalYear = to_number('" + tYear + "')";

        String tCal = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getPolDealC0501";
            tError.errorMessage = "计算年度红利发生错误!";
            this.mErrors.addOneError(tError);
        }

        if (tCal != null && tCal.length() > 0)
        {
            t_Sum_Return = Double.parseDouble(tCal);

            if (t_Sum_Return != 0)
            {
                //准备数据
                mFeeOperationType = "C05";
                mSubFeeOperationType = "C0501";
                mFeeFinaType = "EF";
                mCal = t_Sum_Return;

                if (!updateLLBalance(tLCPolSchema))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        logger.debug("#############################");
        logger.debug("#####计算累计红利保额为零！");
        logger.debug("#############################");
        return true;
    }

    /**
     * 保单结算－－红利--终了红利
     * 对应的业务类型编码为C05,C0502
     * @return boolean
     */
    private boolean getPolDealC0502(LCPolSchema tLCPolSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));
        EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
        t_Sum_Return = tEdorCalZT.getFinalBonus(tLCPolSchema.getPolNo(),mAccDate);
        t_Sum_Return = Arith.round(t_Sum_Return,2);
        if (t_Sum_Return <= 0)
        {
            t_Sum_Return = 0;
            logger.debug("#############################");
            logger.debug("#####计算终了红利为零！");
            logger.debug("#############################");
            return true;
        }
        //准备数据
        mFeeOperationType = "C05";
        mSubFeeOperationType = "C0502";
        mFeeFinaType = "CB";
        mCal = t_Sum_Return;

        if (!updateLLBalance(tLCPolSchema))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 保单结算－－自动垫缴--本金
     * 对应的业务类型编码为C07,C0701
     * @return boolean
     */
    private boolean getPolDealC0701(LCPolSchema tLCPolSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));
        //调用保全方法
        BqPolBalBL tBqPolBalBL = new BqPolBalBL();
        if (tBqPolBalBL.calAutoPayPrem(tLCPolSchema.getPolNo(), mAccDate))
        {
            t_Sum_Return = tBqPolBalBL.getCalResult();
        }
        if (t_Sum_Return <= 0)
        {
            t_Sum_Return = 0;
            logger.debug("#############################");
            logger.debug("#####计算自动垫缴--本金为零！");
            logger.debug("#############################");
            return true;
        }
        //准备数据
        mFeeOperationType = "C07";
        mSubFeeOperationType = "C0701";
        mFeeFinaType = "BF";
        mCal = 0 - t_Sum_Return;

        if (!updateLLBalance(tLCPolSchema))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 保单结算－－自动垫缴--自垫保费的利息
     * 对应的业务类型编码为C07,C0702
     * @return boolean
     */
    private boolean getPolDealC0702(LCPolSchema tLCPolSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));
        //调用保全方法
        BqPolBalBL tBqPolBalBL = new BqPolBalBL();
        if (tBqPolBalBL.calAutoPayInterest(tLCPolSchema.getPolNo(), mAccDate))
        {
            t_Sum_Return = tBqPolBalBL.getCalResult();
        }
        if (t_Sum_Return <= 0)
        {
            t_Sum_Return = 0;
            logger.debug("####################################");
            logger.debug("#####计算自动垫缴--自垫保费的利息为零！");
            logger.debug("####################################");
            return true;
        }
        //准备数据
        mFeeOperationType = "C07";
        mSubFeeOperationType = "C0702";
        mFeeFinaType = "LX";
        mCal = 0 - t_Sum_Return;

        if (!updateLLBalance(tLCPolSchema))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 保单结算，保费豁免
     * 对应的业务类型编码为C08,C0801
     * @return boolean
     */
    private boolean getPolDealC0801(LCPolSchema tLCPolSchema)
    {
        mVData.clear();
        mVData.add(mGlobalInput);
        mVData.add(tLCPolSchema);
        cTransferData.removeByName("FeeOperationType");
        cTransferData.removeByName("SubFeeOperationType");
        cTransferData.removeByName("FeeFinaType");
        cTransferData.setNameAndValue("FeeOperationType", "C08");
        cTransferData.setNameAndValue("SubFeeOperationType", "C0801");
        cTransferData.setNameAndValue("FeeFinaType", "TF");
        mVData.add(cTransferData);

        LLBalExemptFeeBL tLLBalExemptFeeBL = new LLBalExemptFeeBL();
        if (tLLBalExemptFeeBL.submitData(mVData, "") == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLBalExemptFeeBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "getPolDealC01";
            tError.errorMessage = "保费豁免处理失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            VData tempVData = tLLBalExemptFeeBL.getResult();
            MMap tMMap = new MMap();
            tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            mMap.add(tMMap);
        }
        return true;
    }

    /**
     * 公用:插入数据到理赔结算表
     * @return boolean
     */
    private boolean updateLLBalance(LCPolSchema tLCPolSchema)
    {
        if (mClmNo.equals("") ||
            mFeeOperationType.equals("") ||
            mSubFeeOperationType.equals("") ||
            mFeeFinaType.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "LLClaimPolDealBLF";
            tError.functionName = "updateLLBalance";
            tError.errorMessage = "传输到理赔结算表数据不齐全!";
            this.mErrors.addOneError(tError);
            return false;
        }

        LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
        tLLBalanceSchema.setClmNo(mClmNo);
        tLLBalanceSchema.setFeeOperationType(mFeeOperationType); //业务类型
        tLLBalanceSchema.setSubFeeOperationType(mSubFeeOperationType); //子业务类型
        tLLBalanceSchema.setFeeFinaType(mFeeFinaType); //财务类型

        tLLBalanceSchema.setBatNo("0"); //批次号
        tLLBalanceSchema.setOtherNo("0"); //其它号码
        tLLBalanceSchema.setOtherNoType("0"); //其它号码类型
        tLLBalanceSchema.setSaleChnl("0"); //销售渠道

        tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo()); //集体合同号码
        tLLBalanceSchema.setContNo(tLCPolSchema.getContNo()); //合同号码
        tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); //集体保单号码
        tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo()); //保单号码

        tLLBalanceSchema.setDutyCode("0"); //责任编码
        tLLBalanceSchema.setGetDutyKind("0"); //给付责任类型
        tLLBalanceSchema.setGetDutyCode("0"); //给付责任编码

        tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode()); //险类编码
        tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode()); //险种编码
        tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion()); //险种版本

        tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode()); //代理人编码
        tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup()); //代理人组别

        tLLBalanceSchema.setGetDate(this.CurrentDate); //给付日期
        tLLBalanceSchema.setPay(mCal); //赔付金额

        tLLBalanceSchema.setPayFlag("0"); //支付标志,0未支付,1已支付
        tLLBalanceSchema.setState("0"); //状态,0有效
        tLLBalanceSchema.setDealFlag("0"); //处理标志,0未处理

        tLLBalanceSchema.setManageCom(mGlobalInput.ManageCom); //管理机构
        tLLBalanceSchema.setAgentCom(""); //代理机构
        tLLBalanceSchema.setAgentType(""); //代理机构内部分类

        tLLBalanceSchema.setOperator(mGlobalInput.Operator); //操作员
        tLLBalanceSchema.setMakeDate(this.CurrentDate); //入机日期
        tLLBalanceSchema.setMakeTime(this.CurrentTime); //入机时间
        tLLBalanceSchema.setModifyDate(this.CurrentDate); //入机日期
        tLLBalanceSchema.setModifyTime(this.CurrentTime); //入机时间

        mMap.put(tLLBalanceSchema, "INSERT");

        return true;
    }

    /**
     * 判断险种是否是分红险
     * @param aRiskCode
     * @param aPayToDate
     * @return
     */
    private String getBonusFlag(String sRiskcode)
    {
        //判断险种是否是分红险
        String sql = " select BonusFlag from lmriskapp " +
                     " where riskcode = '" + sRiskcode + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String sBonusFlag = tExeSQL.getOneValue(sql);
        if (tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "查询分红险标志失败!");
            return null;
        }
        if (sBonusFlag == null || sBonusFlag.equals("") || sBonusFlag.equals("0"))
        {
            //不是分红险
            sBonusFlag = "N";
        }
        else
        {
            sBonusFlag = "Y";
        }

        return sBonusFlag;
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
        tLOPRTManagerSchema.setOtherNo(mClmNo); //对应其它号码
        tLOPRTManagerSchema.setOtherNoType("05"); //其它号码类型--05赔案号
        tLOPRTManagerSchema.setCode(tCode); //单据编码
        tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); //管理机构
        tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode); //发起机构
        tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator); //发起人
        tLOPRTManagerSchema.setPrtType("0"); //打印方式
        tLOPRTManagerSchema.setStateFlag("0"); //打印状态
        tLOPRTManagerSchema.setMakeDate(CurrentDate); //入机日期
        tLOPRTManagerSchema.setMakeTime(CurrentTime); //入机时间
        tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
        tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);

        //查询立案信息
        LLCaseDB tLLCaseDB = new LLCaseDB();
        String tSSql = "select * from llcase where 1=1 "
                     + " and caseno = '" + mClmNo + "'";
        LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(tSSql);
        String tCusNo = tLLCaseSet.get(1).getCustomerNo();

        tLOPRTManagerSchema.setStandbyFlag4(tCusNo); //客户号码
        tLOPRTManagerSchema.setStandbyFlag5(mRgtDate); //立案日期
        tLOPRTManagerSchema.setStandbyFlag6("50"); //赔案状态

        mMap.put(tLOPRTManagerSchema, "INSERT");
        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        mInputData.clear();
        mInputData.add(mMap);
        return true;
    }

    /**
     * 返回前台需要的数据
     * @return vdata
     */
    public VData getResult()
    {
        return mResult;
    }


    public static void main(String[] args)
    {

        String tClaimFeeCode = "abcdefg";
        tClaimFeeCode = tClaimFeeCode.substring(1);

        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";

        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("ClmNo","90000001176");
        tTransferData.setNameAndValue("AccDate", "2002-05-01");

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);

        LLClaimPolDealBLF tLLClaimPolDealBLF = new LLClaimPolDealBLF();

        if (tLLClaimPolDealBLF.submitData(tVData, "") == false)
        {
            logger.debug("-------false-------");
        }

        int n = tLLClaimPolDealBLF.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            String Content = "";
            Content = Content + "原因是: " +
                      tLLClaimPolDealBLF.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        }
    }


}
