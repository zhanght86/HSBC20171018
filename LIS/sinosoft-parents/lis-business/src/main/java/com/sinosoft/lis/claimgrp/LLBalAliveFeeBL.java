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
 * <p>Description: 保单结算:生存金、养老金</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLBalAliveFeeBL
{
private static Logger logger = Logger.getLogger(LLBalAliveFeeBL.class);


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

    private String mFeeOperationType   = "";     //业务类型
    private String mSubFeeOperationType= "";     //子业务类型


    public LLBalAliveFeeBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------保单结算:生存金、养老金-----开始----------");

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

        logger.debug("---------保单结算:生存金、养老金------结束----------");
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

        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {

        if (!getLLJSGet(mLCPolSchema,this.mAccDate))
        {
            return false;
        }

        if (!getLJSGetDraw(mLCPolSchema,this.mAccDate))
        {
            return false;
        }

        if (!getLJAGetDraw(mLCPolSchema,this.mAccDate))
        {
            return false;
        }

        return true;
    }

    /**
     * 进行个人应付总表退费处理,将信息放入理赔暂存表
     * @param LCPolSchema
     * @return
     */
    private boolean getLLJSGet(LCPolSchema pLCPolSchema,String pDate)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－BEG
         * No1.0 查询
         * 在LJSGet应付总表判断是否有信息
         * 根据保单险种号,意外事故发生日期 < LastGettoDate上次领至日期为条件进行判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = " select * from LJSGet where 1=1 "
            +" and GetNoticeNo in (select distinct GetNoticeNo from LJSGetDraw where 1=1 "
                +" and PolNo = '" + pLCPolSchema.getPolNo() + "'"
                +" and LastGettoDate > to_date('" + pDate + "','yyyy-mm-dd') )";

        LJSGetDB tLJSGetDB = new LJSGetDB();
        LJSGetSet tLJSGetSet = tLJSGetDB.executeQuery(tSql);

        if (tLJSGetDB.mErrors.needDealError() == true)
        {
            this.mErrors.copyAllErrors(tLJSGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLBalAliveFeeBL";
            tError.functionName = "getLLJSGet";
            tError.errorMessage = "查询出险日期以后的个人应付汇总信息发生错误!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 将数据放入暂存表,准备审批通过后删除
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLJSGetSet tLLJSGetSet = new LLJSGetSet();
        for (int i=1;i<=tLJSGetSet.size();i++)
        {
            LJSGetSchema tLJSGetSchema = tLJSGetSet.get(i);

            LLJSGetSchema tLLJSGetSchema = new LLJSGetSchema();
            mReflections.transFields(tLLJSGetSchema,tLJSGetSchema);
            tLLJSGetSchema.setClmNo(this.mClmNo);

            tLLJSGetSet.add(tLLJSGetSchema);
        }

        this.mMMap.put(tLLJSGetSet,"DELETE&INSERT");
        return true;
    }

    /**
     * 进行给付表(生存领取_实付)退费处理,将信息放入理赔暂存表
     * @param LCPolSchema
     * @return
     */
    private boolean getLJSGetDraw(LCPolSchema pLCPolSchema,String pDate)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询
         * 在LJAGetDraw给付表(生存领取_实付)判断是否有信息
         * 根据保单险种号,意外事故发生日期 < LastGettoDate上次领至日期为条件进行判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "select * from LJSGetDraw where 1=1 "
            +" and PolNo = '"+pLCPolSchema.getPolNo()+"'"
            +" and LastGettoDate > to_date('"+pDate+ "','yyyy-mm-dd') ";

        LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
        LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.executeQuery(tSql);
        if (tLJSGetDrawDB.mErrors.needDealError() == true)
        {
            this.mErrors.copyAllErrors(tLJSGetDrawDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLBalAliveFeeBL";
            tError.functionName = "getLJSGetDraw";
            tError.errorMessage = "查询出险日期以后的个人应付信息发生错误!"+tSql;
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 将数据放入暂存表,准备审批通过后删除
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLJSGetDrawSet tLLJSGetDrawSet = new LLJSGetDrawSet();
        for (int i=1;i<=tLJSGetDrawSet.size();i++)
        {
            LJSGetDrawSchema tLJSGetDrawSchema = tLJSGetDrawSet.get(i);

            LLJSGetDrawSchema tLLJSGetDrawSchema = new LLJSGetDrawSchema();
            mReflections.transFields(tLLJSGetDrawSchema,tLJSGetDrawSchema);
            tLLJSGetDrawSchema.setClmNo(this.mClmNo);

            tLLJSGetDrawSet.add(tLLJSGetDrawSchema);
        }

        this.mMMap.put(tLLJSGetDrawSet,"DELETE&INSERT");
        return true;
    }





    /**
     * 进行给付表(生存领取_实付)退费处理(客户退给保险公司)
     * @param LCPolSchema
     * @return
     */
    private boolean getLJAGetDraw(LCPolSchema pLCPolSchema,String pDate)
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 在LJAGetDraw实收个人交费表
         * 根据保单险种号,出险日期 <= LastGettoDate上次领至日期为条件进行判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "select nvl(sum(LJAGetDraw.GetMoney),0) from LJAGetDraw where 1=1 "
            +" and PolNo = '" + pLCPolSchema.getPolNo() + "'"
            +" and LastGettoDate >= to_date('" + pDate + "','yyyy-mm-dd') ";

        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        ExeSQL tExeSQL = new ExeSQL();
        String tCal = tExeSQL.getOneValue(tSql);
        double tDCal = Double.parseDouble(tCal);
        t_Sum_Return = 0 - tDCal;

        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLBalAliveFeeBL";
            tError.functionName = "getLJAGetDraw";
            tError.errorMessage = "计算出险日期以后的生存金养老金发生错误!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (tCal == null || StrTool.cTrim(tCal).equals("") || tCal.length() == 0 || tCal.equals("0") )
        {
            t_Sum_Return = Double.parseDouble(tCal);
        }
        else
        {
            LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
            tLLBalanceSchema.setClmNo(this.mClmNo);
            tLLBalanceSchema.setFeeOperationType(this.mFeeOperationType);           //业务类型
            tLLBalanceSchema.setSubFeeOperationType(this.mSubFeeOperationType);     //子业务类型
            tLLBalanceSchema.setFeeFinaType("EF");             //财务类型

            tLLBalanceSchema.setBatNo("0");             //批次号
            tLLBalanceSchema.setOtherNo("0");           //其它号码
            tLLBalanceSchema.setOtherNoType("0");       //财务类型
            tLLBalanceSchema.setSaleChnl("0");          //销售渠道

            tLLBalanceSchema.setGrpContNo(mLCPolSchema.getGrpContNo());      //集体合同号码
            tLLBalanceSchema.setContNo(mLCPolSchema.getContNo());            //合同号码
            tLLBalanceSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());        //集体保单号码
            tLLBalanceSchema.setPolNo(mLCPolSchema.getPolNo());              //保单号码

            tLLBalanceSchema.setDutyCode("0");                               //责任编码
            tLLBalanceSchema.setGetDutyKind("0");                            //给付责任类型
            tLLBalanceSchema.setGetDutyCode("0");                            //给付责任编码

            tLLBalanceSchema.setKindCode(mLCPolSchema.getKindCode());        //险类编码
            tLLBalanceSchema.setRiskCode(mLCPolSchema.getRiskCode());        //险种编码
            tLLBalanceSchema.setRiskVersion(mLCPolSchema.getRiskVersion());  //险种版本

            tLLBalanceSchema.setAgentCode(mLCPolSchema.getAgentCode());      //代理人编码
            tLLBalanceSchema.setAgentGroup(mLCPolSchema.getAgentGroup());    //代理人组别

            tLLBalanceSchema.setGetDate(this.CurrentDate);                  //给付日期
            tLLBalanceSchema.setPay(t_Sum_Return);                                  //赔付金额

            tLLBalanceSchema.setPayFlag("0");                               //支付标志,0未支付,1已支付
            tLLBalanceSchema.setState("0");                                 //状态,0有效
            tLLBalanceSchema.setDealFlag("0");                              //处理标志,0未处理

            tLLBalanceSchema.setManageCom(mGlobalInput.ManageCom);          //管理机构
            tLLBalanceSchema.setAgentCom("");                               //代理机构
            tLLBalanceSchema.setAgentType("");                              //代理机构内部分类

            tLLBalanceSchema.setOperator(mGlobalInput.Operator);            //操作员
            tLLBalanceSchema.setMakeDate(PubFun.getCurrentDate());                //入机日期
            tLLBalanceSchema.setMakeTime(PubFun.getCurrentTime());                //入机时间
            tLLBalanceSchema.setModifyDate(PubFun.getCurrentDate());              //入机日期
            tLLBalanceSchema.setModifyTime(PubFun.getCurrentTime());              //入机时间

            mMMap.put(tLLBalanceSchema,"INSERT");

        }

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
            tError.moduleName = "LLBalAliveFeeBL";
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
