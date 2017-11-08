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
 * <p>Description: 保单结算,补交保费</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLBalRecruitFeeBL
{
private static Logger logger = Logger.getLogger(LLBalRecruitFeeBL.class);

    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    private Reflections mReflections = new Reflections();

    private LCPolSchema mLCPolSchema = new LCPolSchema();

    private String mClmNo    = "";     //赔案号
    private String mAccDate  = "";     //意外事故发生日期
    private String mRgtDate  = "";     //立案日期

    private String mFeeOperationType   = "";     //业务类型
    private String mSubFeeOperationType= "";     //子业务类型
    private String mFeeFinaType = ""; //财务类型

    public LLBalRecruitFeeBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("--------保单结算,补交保费-------开始----------");

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }
        if (!dealData())
        {
            return false;
        }
        if (!prepareOutputData())
        {
            return false;
        }
//        if (!pubSubmit())
//        {
//            return false;
//        }
        logger.debug("----------保单结算,补交保费-----结束----------");
        return true;
    }

    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {
        this.mInputData = (VData)cInputData.clone();
        this.mOperate = cOperate;

        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName("LCPolSchema", 0);
        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);

        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate");  //意外事故发生日期

        this.mFeeOperationType =(String) mTransferData.getValueByName("FeeOperationType");        //业务类型
        this.mSubFeeOperationType = (String) mTransferData.getValueByName("SubFeeOperationType"); //子业务类型
        this.mFeeFinaType = (String) mTransferData.getValueByName("FeeFinaType");  //财务类型

        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        /**---------------------------------------------------------------------BEG
         * No.0 判断是否存在暂交费
         *----------------------------------------------------------------------*/
        if (!getLJTempFee(mLCPolSchema, mAccDate))
        {
            return false;
        }

        /**---------------------------------------------------------------------BEG
         * No.1 判断如果为附加险,M为主险,S为附加险,主险才作补费处理
         *----------------------------------------------------------------------*/
        String tSql = "select SubRiskFlag from LMRiskApp where 1=1 "
                      + " and riskcode = '" + mLCPolSchema.getRiskCode() + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tSubRiskFlag = tExeSQL.getOneValue(tSql);
        if (tSubRiskFlag == null || tSubRiskFlag.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "LLBalRecruitFeeBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询险种定义失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**---------------------------------------------------------------------BEG
         * No.2 计算缴费宽限期
         *----------------------------------------------------------------------*/
        String tApseDate = PubFun.calLapseDate(mLCPolSchema.getPaytoDate(),
                                               mLCPolSchema.getRiskCode());

        /**---------------------------------------------------------------------BEG
         * No.3 计算缴费宽限期
         *----------------------------------------------------------------------*/
        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterDB.setRgtNo(mClmNo);
        if (tLLRegisterDB.getInfo())
        {
            tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
        }
        mRgtDate = tLLRegisterSchema.getRgtDate(); //立案日期

        /**---------------------------------------------------------------------BEG
         * No.4 判断出险日期(意外事故发生日期)、立案时间是否在宽限期内
         *----------------------------------------------------------------------*/
        String tBegin = PubFun.getLaterDate(mLCPolSchema.getPaytoDate(),mAccDate);
        String tEnd = PubFun.getBeforeDate(tApseDate,mAccDate);
        if (tBegin.equals(mAccDate) && tEnd.equals(mAccDate))
        {
            //判断立案日期是否在宽限期内
//            String tSeSql = "select substr(a.rgtdate,1,10) from llregister a where "
//                          + "a.rgtno = '" + mClmNo + "'";
//            ExeSQL tExeSQL2 = new ExeSQL();
//            String tRgtDate = tExeSQL2.getOneValue(tSeSql);
            String tBDate = PubFun.getLaterDate(mLCPolSchema.getPaytoDate(),mRgtDate);
            String tEDate = PubFun.getBeforeDate(tApseDate,mRgtDate);
            if (tBDate.equals(mRgtDate) && tEDate.equals(mRgtDate))
            {
                //在宽限期内,则查找该主险种（保单）所在合同下所有险种进行补费操作
                String tFsql1 = "";
                tFsql1 = "select * from lcpol a where a.contno in ("
                       + " select b.contno from lcpol b where b.polno = '"
                       + mLCPolSchema.getPolNo() + "')";
                LCPolDB tLCPolDB = new LCPolDB();
                LCPolSet tLCPolSet = tLCPolDB.executeQuery(tFsql1);
                if (tLCPolSet == null || tLCPolSet.size() == 0)
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tLCPolDB.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "LLBalRecruitFeeBL";
                    tError.functionName = "dealData";
                    tError.errorMessage = "没有查询合同下所有险种信息!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
                for (int i = 1; i <= tLCPolSet.size(); i++)
                {
                    LCPolSchema tLCPolSchema = new LCPolSchema();
                    tLCPolSchema = tLCPolSet.get(i);
                    //险种进行补费操作
                    if (!calSumMoney(tLCPolSchema))
                    {
                        return false;
                    }
                }
            }
            else
            {
                //不在宽限期内，对主险种进行补费操作
                if (!calSumMoney(mLCPolSchema))
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 进行暂交费的判断
     * @param tLLExemptSchema
     * @return
     */
    private boolean getLJTempFee(LCPolSchema pLCPolSchema,String pDate)
    {

        String tSql = "";
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 判断LJSPayPerson应收个人交费表是否有数据,根据检索的条件
         * 在LJTempFee暂交费表判断是否有数据,如果有,给出提示,要求先退费.
         *
         * 根据保单险种号,出险日期 <= LastPayToDate原交至日期为条件进行判断
         * 2005.05.01 <= 2005.10.01
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select * from LJTempFee where 1=1 "
            +" and TempFeeNo = (select GetNoticeNo from LJSPayPerson where 1=1 "
            +" and LastPayToDate >= to_date('"+this.mAccDate+ "','yyyy-mm-dd') "
            +" and PolNo = '"+this.mLCPolSchema.getPolNo()+"')"
            +" and confflag not in ('1') and operstate='0' ";

        LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
        LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(tSql);

        if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0 )
        {

        }
        else
        {
            //this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLBalRecedeFeeAfterBL";
            tError.functionName = "getFilterCalCode";
            tError.errorMessage = "合同号:["+mLCPolSchema.getContNo()+"],保单险种号:["+mLCPolSchema.getPolNo()+"],存在暂交费记录,请先进行退费操作,然后再进行此操作!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 公用:对险种进行补费操作
     * @return boolean
     */
    private boolean calSumMoney(LCPolSchema tLCPolSchema)
    {
        //计算应收总额--------------------------------------------------------------本金
        this.mFeeOperationType = "C02";
        this.mSubFeeOperationType = "C0201";
        this.mFeeFinaType = "BF";
        String tFsql2 = "";
        tFsql2 = "select sum(a.SumDuePayMoney) "
                 + " from LJSPayPerson a "
                 + " where a.polno = '" + tLCPolSchema.getPolNo() + "'"
                 + " and a.LastPayToDate = to_date('"
                 + tLCPolSchema.getPaytoDate() + "','yyyy-mm-dd')"
                 + " group by polno";
        ExeSQL tExeSQL4 = new ExeSQL();
        String tSum = tExeSQL4.getOneValue(tFsql2);

        if (tSum == null || tSum.equals(""))
        {
            tSum = "0";
        }
        double tSumM = Double.parseDouble(tSum);
        tSumM = Double.parseDouble(new DecimalFormat("0.00").format(tSumM));

        //存负值
        tSumM = 0 - tSumM;

        //插入数据到理赔结算表
        if (tSumM != 0)
        {
            if (!updateLLBalance(tLCPolSchema, tSumM))
            {
                return false;
            }
        }

        //计算补交保费利息(取当前5年贷款利率) 2005-8-29 17:37 周磊---------------------利息
        this.mFeeOperationType = "C02";
        this.mSubFeeOperationType = "C0202";
        this.mFeeFinaType = "LX";

        //取当前5年贷款利率
        String tSql = "select rate from ldbankrate where "
                    + " period = '5' and depst_loan = 'L' "
                    + " and to_date('" + CurrentDate + "', 'YYYY-MM-DD') >= declaredate "
                    + " and to_date('" + CurrentDate + "', 'YYYY-MM-DD') < enddate";
        ExeSQL tExeSQL = new ExeSQL();
        String tRate = tExeSQL.getOneValue(tSql);
        double aRate = Double.parseDouble(tRate);
        tSumM = Math.abs(tSumM); //本金
        double tRateFee = AccountManage.getMultiAccInterest(aRate,tSumM,
                                              tLCPolSchema.getPaytoDate(),
                                              mRgtDate,"C", "D");
        //存负值
        tRateFee = 0 - tRateFee;

        //插入数据到理赔结算表
        if (tRateFee != 0)
        {
            if (!updateLLBalance(tLCPolSchema, tRateFee))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 公用:插入数据到理赔结算表
     * @return boolean
     */
    private boolean updateLLBalance(LCPolSchema tLCPolSchema, double tCal)
    {
        if (mClmNo.equals("") ||
            mFeeOperationType.equals("") ||
            mSubFeeOperationType.equals("") ||
            mFeeFinaType.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "LLBalRecruitFeeBL";
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
        tLLBalanceSchema.setPay(tCal); //赔付金额

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

        mMMap.put(tLLBalanceSchema, "DELETE&INSERT");

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        mInputData.clear();
        mInputData.add(mMMap);

        mResult.clear();
        mResult.add(mMMap);
        return true;
    }

    /**
     * 提交数据
     * @return
     */
    private boolean pubSubmit()
    {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }


    public VData getResult()
    {
        return mResult;
    }


    public static void main(String[] args)
    {


        String tClaimFeeCode = "abcdefg";
        tClaimFeeCode = tClaimFeeCode.substring(1);


        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";


        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("ClmNo","90000004960");
        tTransferData.setNameAndValue("AccDate", "2005-07-22");

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);


        LLClaimCalPortalBL tLLClaimCalPortalBL = new LLClaimCalPortalBL();
        tLLClaimCalPortalBL.submitData(tVData, "");
    }


}
