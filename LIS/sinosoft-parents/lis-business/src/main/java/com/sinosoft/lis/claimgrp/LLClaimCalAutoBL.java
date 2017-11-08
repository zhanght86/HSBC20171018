package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bqgrp.GrpEdorCalZT;
//import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;
//import com.sinosoft.lis.bq.EdorCalZT;
//import com.sinosoft.lis.bq.BqCode;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 计算步骤五：理赔正式计算</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalAutoBL
{
private static Logger logger = Logger.getLogger(LLClaimCalAutoBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
//    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

//    private String CurrentDate = PubFun.getCurrentDate();                                                                                                               
//    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();


    private String mAccNo    = "";     //事件号
    private String mClmNo    = "";     //赔案号
//    private String mCusNo    = "";     //客户号
//    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单
    private String mClmState = "";     //赔案状态，20立案，30审核



    private LCPolSchema  mLCPolSchema  = new LCPolSchema();       //承保--个人保单险种表
    private LCDutySchema mLCDutySchema = new LCDutySchema();      //承保--个人保单险种责任表
    private LCGetSchema  mLCGetSchema  = new LCGetSchema();       //承保--个人领取项表

    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();    //立案登记信息
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();                //立案分案信息

    //  理赔--赔案信息
    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();

    //  理赔--赔案保单名细
    private LLClaimPolicySchema mLLClaimPolicySchema =new LLClaimPolicySchema();
    private LLClaimPolicySet mLLClaimPolicySet = new LLClaimPolicySet();

    //  理赔--赔付明细信息
    private LLClaimDetailSchema mLLClaimDetailSchema = new LLClaimDetailSchema();
    private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

    //  理赔--责任费用统计
    private LLClaimDutyFeeSchema mLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema();
    private LLClaimDutyFeeSet mLLClaimDutyFeeSet = new LLClaimDutyFeeSet();

    //  理赔责任控制表
     private  LLDutyCtrlSet mLLDutyCtrlSet = null;
     
     //private  String mExemptMoney="";//免陪额
     private  String mObserveDate="";//观察期
     private  String mExemptDate="";//免陪天数
     private  String mExemptMoney="";//免陪额
     private  String mGetRate="";//赔付比例
     private  String mStartLine="";//起付线
     private  String mEndLine="";//封顶线
     private  String mTotalLimit="";//剩余可赔付限额
     
     private  LMDutyGetClmSchema pLMDutyGetClmSchema=null;

//     String tContType = ""; //保单险种号
//     String tContPlanCode = ""; //保单险种号
    
	private String mAccDate = ""; // 意外事故发生日期
	private String mInsDate = ""; // 出险时间

    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
//    private ExeSQL mExeSQL = new ExeSQL();


    public LLClaimCalAutoBL(){
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */


    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------理算步骤五-----理赔正式计算-----LLClaimCalAutoBL测试-----开始----------");

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

        if (!pubSubmit())
        {
            return false;
        }

        logger.debug("----------理算步骤五-----理赔正式计算-----LLClaimCalAutoBL测试-----结束----------");
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {

        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate"); // 意外事故发生日期
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
//        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人投保单,2-集体总投保单
        this.mClmState= (String) mTransferData.getValueByName("ClmState");  //赔案状态，20立案，30审核
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 先删除已经计算过的信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String strSQL1 = "delete from LLClaim where ClmNo='" +this.mClmNo+ "'";

        String strSQL2 = "delete from LLClaimPolicy where ClmNo='" +this.mClmNo+ "'";

        String strSQL3 = "delete from LLClaimDetail where ClmNo='" +this.mClmNo+ "'";

        String strSQL4 = "delete from LLClaimDutyFee where ClmNo='" +this.mClmNo+ "'";


        mMMap.put(strSQL1, "DELETE");
        mMMap.put(strSQL2, "DELETE");
        mMMap.put(strSQL3, "DELETE");
        mMMap.put(strSQL4, "DELETE");
        
		mInsDate = mLLClaimPubFunBL.getInsDate(this.mClmNo);


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 创建赔案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getLLClaim())
        {
            return false;
        }
        
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 取得立案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        mLLRegisterSchema = mLLClaimPubFunBL.getLLRegister(this.mClmNo);
        if(mLLRegisterSchema==null)
        {
        	CError.buildErr(this, "查询不到立案信息!");
        	return false;
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 循环该赔案的匹配结果集,
         *  按保单、理赔类型进行的分组信息，分别进行计算
         *  主要针对于LLClaimPolicy表【赔案保单名细】
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql =
            "select risktype,polno,getdutykind,count(*) from lltoclaimduty where 1 =1 "
            + " and caseno='" + this.mClmNo + "' group by risktype,polno,getdutykind";


        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "理赔计算时发生错误!");
            return false;
        }

        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String pRiskType  = tSSRS.GetText(i,1);         //保单险种号
            String pPolNo     = tSSRS.GetText(i,2);         //保单险种号
            String pClaimType = tSSRS.GetText(i,3);         //理赔类型

            //调用calDutyPay()进行计算
            if (!calDutyPay(pPolNo,pClaimType))
            {
                return false;
            }

        }

        return true;
    }


    /**
    * 创建赔案记录
    * @return boolean
    */
    private boolean getLLClaim()
    {

        LLClaimDB tLLClaimDB = new LLClaimDB();
        tLLClaimDB.setClmNo(this.mClmNo);
        LLClaimSet tLLClaimSet = tLLClaimDB.query();

        if (tLLClaimSet == null || tLLClaimSet.size() == 0 )
        {
            mLLClaimSchema = new LLClaimSchema();
            mLLClaimSchema.setClmNo(this.mClmNo);
            mLLClaimSchema.setRgtNo(this.mClmNo);
            mLLClaimSchema.setCaseNo(this.mClmNo);
            mLLClaimSchema.setClmState(mClmState);              //赔案状态，20立案，30审核
            mLLClaimSchema.setCheckType("0");                   //审核类型，0初次审核,1签批退回审核,2申诉审核
            mLLClaimSchema.setCurrency((String) mTransferData.getValueByName("sumCurrency"));
        }
        else
        {
            mLLClaimSchema = tLLClaimSet.get(1);

        }
        mLLClaimSchema.setClmUWer(mGlobalInput.Operator);

        mLLClaimSchema.setGiveType("0");                    //赔付结论，0给付，1拒付

        mLLClaimSchema.setMngCom(mGlobalInput.ManageCom);
        mLLClaimSchema.setOperator(mGlobalInput.Operator);
        mLLClaimSchema.setMakeDate(PubFun.getCurrentDate());
        mLLClaimSchema.setMakeTime(PubFun.getCurrentTime());
        mLLClaimSchema.setModifyDate(PubFun.getCurrentDate());
        mLLClaimSchema.setModifyTime(PubFun.getCurrentTime());

        mLLClaimSchema.setStandPay(0);                    //核算赔付金额
        mLLClaimSchema.setBalancePay(0);                  //结算金额
        mLLClaimSchema.setBeforePay(getPrepaySum());      //预付金额
        mLLClaimSchema.setRealPay(0);                     //核赔赔付金额


        return true;
    }

    /**
     * 得到预付金额
     * @return
     */
    private double getPrepaySum()
    {
        double rValue;
        LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
        tLLPrepayClaimDB.setClmNo(this.mClmNo);
        LLPrepayClaimSet tLLPrepayClaimSet = tLLPrepayClaimDB.query();

        if (tLLPrepayClaimSet == null || tLLPrepayClaimSet.size() == 0 )
        {
            rValue = 0;
        }
        else
        {
//            Double.parseDouble(new DecimalFormat("0.00").format(m_Sum_ClaimFee));
            rValue = tLLPrepayClaimSet.get(1).getPrepaySum();
        }
        return rValue;

    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－开始－－－－－－－理赔计算－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 目的：按照保单,理赔类型 计算赔付
     * @param String pPolNo         保单号
     * @param String pClaimType     理赔类型
     */
    private boolean calDutyPay(String pPolNo,String pClaimType)
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        String tSqlTemp = "";
        //临时显示金额
        double t_Sum_Temp = 0;
        t_Sum_Temp = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Temp));


        //给付总金额,用于累计计算,总赔案层面
        double t_Sum_Claim = 0;
        t_Sum_Claim = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Claim));

        //理赔类型给付金额，用于保单理赔类型层面
        double t_Sum_DutyKind = 0;
        t_Sum_DutyKind = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_DutyKind));

        //保项给付金额，用于保项层面
        double t_Sum_DutyCode = 0;
        t_Sum_DutyCode = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_DutyCode));

        //保项的剩余金额，用于保项层面
        double t_Sum_DutyBalCode = 0;
        t_Sum_DutyBalCode = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_DutyBalCode));


        //医疗费用给付基本金额，用于费用层面
        double t_Sum_DutyFee = 0;
        t_Sum_DutyFee = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_DutyFee));

        //医疗费用给付基本金额，用于费用层面
        double t_Sum_DutyFeeFranchise = 0;
        t_Sum_DutyFeeFranchise = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_DutyFeeFranchise));


        //超出金额
        double toutAmnt = 0;


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 初始化LLClaimPolicySchema【赔案保单名细】
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        mLLClaimPolicySchema = new LLClaimPolicySchema();
        mLLClaimPolicySchema.setClmNo(this.mClmNo);                         //赔案号
        mLLClaimPolicySchema.setCaseNo(this.mClmNo);                        //分案号
        mLLClaimPolicySchema.setCaseRelaNo(this.mAccNo);                    //事件号
        mLLClaimPolicySchema.setRgtNo(this.mClmNo);                         //赔案号
        mLLClaimPolicySchema.setPolNo(pPolNo);                               //保单号
        mLLClaimPolicySchema.setGetDutyKind(pClaimType);                     //理赔类型
        mLLClaimPolicySchema.setMakeDate(PubFun.getCurrentDate());
        mLLClaimPolicySchema.setMakeTime(PubFun.getCurrentTime());
        mLLClaimPolicySchema.setOperator(this.mGlobalInput.Operator);        


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据保单号，取得LCPol【保单信息】mLCPolSchema信息
         *  将该保单的信息赋值给mLLClaimPolicySchema
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getLCLBPol(pPolNo))
        {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 查询LLToClaimDuty中该赔案，该保单，该理赔类型下的所有给付责任,
         *    也就是保项，分别进行计算
         *    主要针对于LLToClaimDuty表
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLToClaimDutyDB tLLToClaimDutyDB = new LLToClaimDutyDB();
        tLLToClaimDutyDB.setCaseNo(this.mClmNo);
        tLLToClaimDutyDB.setPolNo(pPolNo);
        tLLToClaimDutyDB.setGetDutyKind(pClaimType);

        LLToClaimDutySet tLLToClaimDutySet = tLLToClaimDutyDB.query();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 查询LPEdorItem中保全退保的金额,通过polno来查
         *    出险日期后退保的可以理赔,理算出来的金额减掉已经退还的保费
         *    2006-03-28 P.D 新增
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        double tGetMoney = 0;
        tSql = "select b.getMoney from lpedoritem a,ljagetendorse b where a.edortype IN ('ZT','CT')"
             + " and a.edorno = b.endorsementno and a.polno = b.polno"
             + " and a.polno = '"+pPolNo+"' and a.edorstate = '0' and b.operstate='0' ";
         ExeSQL tExeSQL = new ExeSQL();
         String getMoney = StrTool.cTrim(tExeSQL.getOneValue(tSql));
         if (getMoney == null || getMoney.length() == 0)
         {
             getMoney = "0";
         }
         tGetMoney = Double.parseDouble(getMoney);
         /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
          * No6.0 查询llclaimdetail中保全PosFlag保全标识3做过保全退保退费
          * 避免退保后多次理赔时出现重复扣除已经退回的保费
          *    2006-09-26 P.D 新增
          * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
         String tPosFlag = "";
         String tSqlPos = "select count(*) From llclaimdetail where polno = '"+pPolNo+"' and posflag = '3'";
         tPosFlag = tExeSQL.getOneValue(tSqlPos);


        for (int i = 1; i <= tLLToClaimDutySet.size(); i++)
        {


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.1 创建匹配数据记录Schema,并得到理赔类型，给付责任编码
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LLToClaimDutySchema tLLToClaimDutySchema = tLLToClaimDutySet.get(i);
            String tDutyCode = StrTool.cTrim(tLLToClaimDutySchema.getDutyCode());
            String tGetDutyKind = StrTool.cTrim(tLLToClaimDutySchema.getGetDutyKind());
            String tGetDutyCode = StrTool.cTrim(tLLToClaimDutySchema.getGetDutyCode());


            //医疗费用给付金代码,每次循环需要初始化
            t_Sum_DutyFee = 0;

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.2 创建赔付明细LLClaimDetail记录的Schema，便于后面添加内容
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            //LLClaimDetailSchema tLLClaimDetailSchema = null;
            LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
            tLLClaimDetailDB.setClmNo(this.mClmNo);
            tLLClaimDetailDB.setPolNo(tLLToClaimDutySchema.getPolNo());
            tLLClaimDetailDB.setDutyCode(tDutyCode);
            tLLClaimDetailDB.setGetDutyKind(tGetDutyKind);
            tLLClaimDetailDB.setGetDutyCode(tGetDutyCode);
            LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
            if(tLLClaimDetailSet != null && tLLClaimDetailSet.size() == 1 )
            {
                mLLClaimDetailSchema = tLLClaimDetailSet.get(1);
            }
            else
            {
                mLLClaimDetailSchema = new LLClaimDetailSchema();
            }

            mLLClaimDetailSchema.setClmNo(this.mClmNo);
            mLLClaimDetailSchema.setCaseNo(mLLClaimPolicySchema.getCaseNo());
            mLLClaimDetailSchema.setPolNo(mLCPolSchema.getPolNo());
            mLLClaimDetailSchema.setPolSort(tLLToClaimDutySchema.getPolSort());         //保单类型,C表保单，B表保单,A承保出单前出险
            mLLClaimDetailSchema.setPolType(tLLToClaimDutySchema.getPolType());         /*保单性质0 --个人单,1 --无名单,2 --（团单）公共帐户*/

            mLLClaimDetailSchema.setDutyCode(tDutyCode);
            mLLClaimDetailSchema.setGetDutyKind(tLLToClaimDutySchema.getGetDutyKind());
            mLLClaimDetailSchema.setGetDutyCode(tLLToClaimDutySchema.getGetDutyCode());
            mLLClaimDetailSchema.setCaseRelaNo(mLLClaimPolicySchema.getCaseRelaNo());

            mLLClaimDetailSchema.setDefoType("");           /*伤残类型*/
            mLLClaimDetailSchema.setRgtNo(this.mClmNo);     /*立案号*/
            mLLClaimDetailSchema.setDeclineNo("");          /*拒赔号*/
            mLLClaimDetailSchema.setStatType("");           /*统计类别*/

            mLLClaimDetailSchema.setContNo(mLCPolSchema.getContNo());
            mLLClaimDetailSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
            mLLClaimDetailSchema.setGrpContNo(mLCPolSchema.getGrpContNo());

            mLLClaimDetailSchema.setKindCode(mLCPolSchema.getKindCode());
            mLLClaimDetailSchema.setRiskCode(mLCPolSchema.getRiskCode());
            mLLClaimDetailSchema.setRiskVer(mLCPolSchema.getRiskVersion());
            mLLClaimDetailSchema.setPolMngCom(mLCPolSchema.getManageCom());
            mLLClaimDetailSchema.setSaleChnl(mLCPolSchema.getSaleChnl());
            mLLClaimDetailSchema.setAgentCode(mLCPolSchema.getAgentCode());

            mLLClaimDetailSchema.setAmnt(tLLToClaimDutySchema.getAmnt());               //有效保额
            mLLClaimDetailSchema.setGracePeriod(tLLToClaimDutySchema.getGracePeriod()); //缴费宽限期
            mLLClaimDetailSchema.setCasePolType(tLLToClaimDutySchema.getCasePolType()); //给付类型
            mLLClaimDetailSchema.setYearBonus(tLLToClaimDutySchema.getYearBonus());     //年度红利
            mLLClaimDetailSchema.setEndBonus(tLLToClaimDutySchema.getEndBonus());       //终了红利
            mLLClaimDetailSchema.setGiveType(tLLToClaimDutySchema.getGiveType());       //给付标志
            mLLClaimDetailSchema.setCustomerNo(tLLToClaimDutySchema.getCustomerNo());   //用于存放出险人编号
            mLLClaimPolicySchema.setInsuredNo(tLLToClaimDutySchema.getCustomerNo());   //用于存放出险人编号
            mLLClaimDetailSchema.setPrepayFlag(tLLToClaimDutySchema.getPrepayFlag());   //预付标志,0没有预付,1已经预付
            mLLClaimDetailSchema.setPrepaySum(tLLToClaimDutySchema.getPrepaySum());     //预付金额
            mLLClaimDetailSchema.setAcctFlag("0");                                      //帐户标志.0非帐户,1个险帐户
            mLLClaimDetailSchema.setPosFlag(tLLToClaimDutySchema.getPosFlag());         //0未做保全,1已做保全
            mLLClaimDetailSchema.setPosEdorNo(tLLToClaimDutySchema.getPosEdorNo());     //保全批单号
            mLLClaimDetailSchema.setCValiDate(mLCPolSchema.getCValiDate());             //保单生效日期
            mLLClaimDetailSchema.setEffectOnMainRisk(tLLToClaimDutySchema.getEffectOnMainRisk());//附加险影响主险标志
            mLLClaimDetailSchema.setRiskCalCode(tLLToClaimDutySchema.getRiskCalCode()); //用于保存主险的公式
            mLLClaimDetailSchema.setAddAmnt("0");                                       //加保保额
            mLLClaimDetailSchema.setNBPolNo(tLLToClaimDutySchema.getNBPolNo());         //做过续期抽档之后,把原号保存起来,用于后续业务
            mLLClaimDetailSchema.setPosEdorType(tLLToClaimDutySchema.getPosEdorType()); //保全业务类型
            mLLClaimDetailSchema.setRiskType(tLLToClaimDutySchema.getRiskType());       // 主附险标志 M主险 S附险

            mLLClaimDetailSchema.setMakeDate(PubFun.getCurrentDate());
            mLLClaimDetailSchema.setMakeTime(PubFun.getCurrentTime());
            mLLClaimDetailSchema.setModifyDate(PubFun.getCurrentDate());
            mLLClaimDetailSchema.setModifyTime(PubFun.getCurrentTime());
            mLLClaimDetailSchema.setOperator(this.mGlobalInput.Operator);
            mLLClaimDetailSchema.setMngCom(this.mGlobalInput.ManageCom);
            mLLClaimDetailSchema.setCurrency(tLLToClaimDutySchema.getCurrency());


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.3 判断是否是加保记录,如果不是,则取出加保的保额.
             *  如果是加保的数据,不进行后续的计算
             *  因为加保的DutyCode为8为,正常的DutyCode的为6位
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode());
            if (tDutyCode.length() == 6 && !mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"8"))
            {
                double tDAddAmnt = 0;

                tSql = "select nvl(sum(Amnt),0) from LLToClaimDuty where 1=1"
                    +" and CaseNo='"+this.mClmNo+"'"
                    +" and PolNo='"+mLCPolSchema.getPolNo()+"'"
                    +" and GetDutyCode='"+tGetDutyCode+"'"
                    +" and GetDutyKind='"+tGetDutyKind+"'"
                    +" and DutyCode like '"+tDutyCode+"%'"
                    +" and DutyCode not in ('"+tDutyCode+"')"
                    ;
//                ExeSQL tExeSQL = new ExeSQL();
                String tSAddAmnt = StrTool.cTrim(tExeSQL.getOneValue(tSql));
                if (tExeSQL.mErrors.needDealError())
                {
                    CError.buildErr(this, "查询加保保项的有效保额失败!"+tSql);
                    return false;
                }

                if (tSAddAmnt == null || tSAddAmnt.length() == 0)
                {
                    tSAddAmnt = "0";
                }
                tDAddAmnt = Double.parseDouble(tSAddAmnt);
                mLLClaimDetailSchema.setAddAmnt(tDAddAmnt);
            }
            else if (tDutyCode.length() == 8 && !mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"8"))
            {
                mLLClaimDetailSchema.setOverAmnt("0");
                mLLClaimDetailSchema.setStandPay("0");
                mLLClaimDetailSchema.setRealPay("0");
                mLLClaimDetailSchema.setAddAmnt("0");
                mLLClaimDetailSet.add(mLLClaimDetailSchema);
                continue;
            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.4 根据出险人编号查询查询立案分案信息,作为计算要素使用
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!this.getLLCase(mLLClaimDetailSchema.getCustomerNo()))
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.5 得到LCDuty【保单险种责任表】信息,mLCDutySchema,
             *  为后面的运算，用风险保额RiskAmnt和理算金额进行比较
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            SynLCLBDutyBL tSynLCLBDutyBL = new SynLCLBDutyBL();
            tSynLCLBDutyBL.setPolNo(pPolNo);
            tSynLCLBDutyBL.setDutyCode(tLLToClaimDutySchema.getDutyCode());
            tSynLCLBDutyBL.getInfo();
            if (tSynLCLBDutyBL.mErrors.needDealError())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tSynLCLBDutyBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "calDutyPay";
                tError.errorMessage = "LCLBDuty表查询失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            mLCDutySchema.setSchema(tSynLCLBDutyBL.getSchema());


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.6 根据保单号，给付责任编码得到LCGet【领取项表】的数据，tLCGetSchema
             *  为下一步取LCDuty的数据做准备
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            SynLCLBGetBL tSynLCLBGetBL = new SynLCLBGetBL();
            tSynLCLBGetBL.setPolNo(pPolNo);
            tSynLCLBGetBL.setDutyCode(tLLToClaimDutySchema.getDutyCode());
            tSynLCLBGetBL.setGetDutyCode(tGetDutyCode);
            tSynLCLBGetBL.getInfo();
            if (tSynLCLBGetBL.mErrors.needDealError())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tSynLCLBGetBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "calDutyPay";
                tError.errorMessage = "LCLBGet表查询失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            mLCGetSchema.setSchema(tSynLCLBGetBL.getSchema());

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.7 根据理赔类型，给付责任编码得到责任给付赔付计算公式代码
             *  可以确定tLMDutyGetClmSchema唯一的一条记录
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
            tLMDutyGetClmDB.setGetDutyCode(tGetDutyCode);
            tLMDutyGetClmDB.setGetDutyKind(tGetDutyKind);
            if (!tLMDutyGetClmDB.getInfo())
            {
                mErrors.addOneError("查询LMDutyGetClm错误");
                return false;
            }

            LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmDB.getSchema();

            //保项计算公式代码
            String t_CalDutyCode = tLMDutyGetClmSchema.getCalCode();
            logger.debug("保项计算公式代码 ====== "+t_CalDutyCode);


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.8 根据计算公式进行正常计算
             *  判断LMDutyGetClm表中的InpFlag录入标志进行判断
             *  给付金是否需要输入(给付间隔等): 2-给付时需要录入,1-承保需要输入,0 or null -不需
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            t_Sum_DutyCode = 0;//初始化定义保项金额

            String tInpFlag = "";
            tInpFlag = tLMDutyGetClmSchema.getInpFlag();
            if (tInpFlag == null)
            {
                tInpFlag = "0";
            }

  
            /**-----------------------------------------------------------------------
             * zhangzheng 2009-04-08 获得特约录入的免赔额
             * -----------------------------------------------------------------------
             */
            mLLDutyCtrlSet=mLLClaimPubFunBL.getLLDutyCtrl(mLLRegisterSchema.getGrpContNo());
            

            /**
           	   * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.1 承保时确定给付金金额
           	   * 根据计算公式进行非医疗费用(身故和重疾)计算【InpFlag＝0或1，承保时录入】
               * 
               * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
           	*/
                       
            if ( ( tInpFlag.equals("0") || tInpFlag.equals("1") ) 
            		&& tLLToClaimDutySchema.getGiveType().equals("0"))
            {
                t_Sum_Temp = mLLClaimDetailSchema.getAmnt();     /*保存计算前金额,用于显示*/
                t_Sum_DutyCode = executepay(t_Sum_Temp, t_CalDutyCode,"");

                logger.debug("======================================================================================================");
                logger.debug("死亡高残类理赔类型:["+tLLToClaimDutySchema.getGetDutyKind()+"],保项编码:["+tLLToClaimDutySchema.getGetDutyCode()+"],计算公式:["+t_CalDutyCode+"],计算前金额="+t_Sum_Temp+",计算后金额="+t_Sum_DutyCode);
                logger.debug("======================================================================================================");

            }//if 初次结算金额结束

            
        	/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1
			 * 根据计算公式进行医疗费用计算【InpFlag＝2，医疗费用录入】 并且给付标志为0给付
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
            if (tInpFlag.equals("2") && 
            		tLLToClaimDutySchema.getGiveType().equals("0"))
            {
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No7.10.1 查询LLToClaimDutyFee中该赔案，该保单，该理赔类型,该给付责任
                 *  下的所有费用信息
                 *
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                double tCutApartAmnt=0;
                LLToClaimDutyFeeDB tLLToClaimDutyFeeDB = new LLToClaimDutyFeeDB();

                tLLToClaimDutyFeeDB.setClmNo(this.mClmNo);
                tLLToClaimDutyFeeDB.setPolNo(pPolNo);
                tLLToClaimDutyFeeDB.setDutyCode(tDutyCode);
                tLLToClaimDutyFeeDB.setGetDutyType(tGetDutyKind);
                tLLToClaimDutyFeeDB.setGetDutyCode(tGetDutyCode);
                
                // TK 2005-12-12 P.D 新增条件 取到出险人的相关帐单数据
                tLLToClaimDutyFeeDB.setCustomerNo(tLLToClaimDutySchema.getCustomerNo());

                LLToClaimDutyFeeSet tLLToClaimDutyFeeSet = tLLToClaimDutyFeeDB.query();

                for (int m = 1; m <= tLLToClaimDutyFeeSet.size(); m++)
                {

                    LLToClaimDutyFeeSchema tLLToClaimDutyFeeSchema = tLLToClaimDutyFeeSet.get(m);

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No7.10.2 将费用信息放入mLLClaimDutyFeeSchema中
                     *  便于后面计算使用
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    mLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema();

                    mLLClaimDutyFeeSchema.setClmNo(tLLToClaimDutyFeeSchema.getClmNo());          //赔案号
                    mLLClaimDutyFeeSchema.setCaseNo(tLLToClaimDutyFeeSchema.getCaseNo());        //分案号
                    mLLClaimDutyFeeSchema.setPolNo(tLLToClaimDutyFeeSchema.getPolNo());          //保单号

                    mLLClaimDutyFeeSchema.setDutyCode(tLLToClaimDutySchema.getDutyCode());     //给付责任类型
                    mLLClaimDutyFeeSchema.setGetDutyType(tLLToClaimDutyFeeSchema.getGetDutyType());     //给付责任类型
                    mLLClaimDutyFeeSchema.setGetDutyCode(tLLToClaimDutyFeeSchema.getGetDutyCode());     //给付责任编码

                    mLLClaimDutyFeeSchema.setDutyFeeType(tLLToClaimDutyFeeSchema.getDutyFeeType());     //费用类型
                    mLLClaimDutyFeeSchema.setDutyFeeCode(tLLToClaimDutyFeeSchema.getDutyFeeCode());     //费用代码
                    mLLClaimDutyFeeSchema.setDutyFeeName(tLLToClaimDutyFeeSchema.getDutyFeeName());     //费用名称
                    mLLClaimDutyFeeSchema.setDutyFeeStaNo(tLLToClaimDutyFeeSchema.getDutyFeeStaNo());   //账单费用明细序号

                    mLLClaimDutyFeeSchema.setKindCode(tLLToClaimDutyFeeSchema.getKindCode());   //险类代码
                    mLLClaimDutyFeeSchema.setRiskCode(tLLToClaimDutyFeeSchema.getRiskCode());   //险种代码
                    mLLClaimDutyFeeSchema.setRiskVer(tLLToClaimDutyFeeSchema.getRiskVer());     //险种版本号
                    mLLClaimDutyFeeSchema.setPolMngCom(tLLToClaimDutyFeeSchema.getPolMngCom()); //保单管理机构


                    mLLClaimDutyFeeSchema.setHosID(tLLToClaimDutyFeeSchema.getHosID());         //医院编号
                    mLLClaimDutyFeeSchema.setHosName(tLLToClaimDutyFeeSchema.getHosName());     //医院名称
                    mLLClaimDutyFeeSchema.setHosGrade(tLLToClaimDutyFeeSchema.getHosGrade());   //医院等级

                    mLLClaimDutyFeeSchema.setStartDate(tLLToClaimDutyFeeSchema.getStartDate());  //开始时间
                    mLLClaimDutyFeeSchema.setEndDate(tLLToClaimDutyFeeSchema.getEndDate());      //结束时间
                    mLLClaimDutyFeeSchema.setDayCount(tLLToClaimDutyFeeSchema.getDayCount());    //天数


                    mLLClaimDutyFeeSchema.setOriSum(tLLToClaimDutyFeeSchema.getOriSum());   //原始金额
                    mLLClaimDutyFeeSchema.setAdjSum(tLLToClaimDutyFeeSchema.getAdjSum());   //调整金额
                    mLLClaimDutyFeeSchema.setCalSum(tLLToClaimDutyFeeSchema.getCalSum());   //理算金额

                    mLLClaimDutyFeeSchema.setDefoType(tLLToClaimDutyFeeSchema.getDefoType());     //伤残类型
                    mLLClaimDutyFeeSchema.setDefoCode(tLLToClaimDutyFeeSchema.getDefoCode());     //伤残代码
                    mLLClaimDutyFeeSchema.setDefoeName(tLLToClaimDutyFeeSchema.getDefoeName());   //伤残级别名称
                    mLLClaimDutyFeeSchema.setDefoRate(tLLToClaimDutyFeeSchema.getDefoRate());     //残疾给付比例
                    mLLClaimDutyFeeSchema.setRealRate(tLLToClaimDutyFeeSchema.getDefoRate());     //实际给付比例

                    mLLClaimDutyFeeSchema.setOutDutyAmnt(tLLToClaimDutyFeeSchema.getOutDutyAmnt());//免赔额
                    //mLLClaimDutyFeeSchema.setCutApartAmnt(tLLToClaimDutyFeeSchema.getCutApartAmnt());//分割单受益金额
                    //jixf chg 20051212
                    tCutApartAmnt=tCutApartAmnt+tLLToClaimDutyFeeSchema.getCutApartAmnt();
                    logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+tCutApartAmnt);
                    mLLClaimDutyFeeSchema.setCutApartAmnt(tCutApartAmnt);//分割单受益金额

                    mLLClaimDutyFeeSchema.setMakeDate(PubFun.getCurrentDate());
                    mLLClaimDutyFeeSchema.setModifyDate(PubFun.getCurrentDate());
                    mLLClaimDutyFeeSchema.setMakeTime(PubFun.getCurrentTime());
                    mLLClaimDutyFeeSchema.setModifyTime(PubFun.getCurrentTime());
                    mLLClaimDutyFeeSchema.setOperator(this.mGlobalInput.Operator);
                    mLLClaimDutyFeeSchema.setMngCom(this.mGlobalInput.ManageCom);

                    mLLClaimDutyFeeSchema.setGrpContNo(tLLToClaimDutySchema.getGrpContNo());
                    mLLClaimDutyFeeSchema.setGrpPolNo(tLLToClaimDutySchema.getGrpPolNo());
                    mLLClaimDutyFeeSchema.setContNo(tLLToClaimDutySchema.getContNo());
                    mLLClaimDutyFeeSchema.setDutyCode(tLLToClaimDutySchema.getDutyCode());

                    mLLClaimDutyFeeSchema.setNBPolNo(tLLToClaimDutySchema.getNBPolNo());// 做过续期抽档之后,把原号保存起来,用于后续业务
                    mLLClaimDutyFeeSchema.setCustomerNo(tLLToClaimDutySchema.getCustomerNo());     //客户号
                    mLLClaimDutyFeeSchema.setAdjReason(tLLToClaimDutyFeeSchema.getAdjReason());    //调整原因
                    mLLClaimDutyFeeSchema.setAdjRemark(tLLToClaimDutyFeeSchema.getAdjRemark());    //调整备注

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No7.10.3 计算门诊，住院费用
                     *  查询LLToClaimDutyFeeSchema中该费用类型
                     *  对应LMDutyGetFeeRela表中的计算公式。
                     *
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    String tDutyFeeType = mLLClaimDutyFeeSchema.getDutyFeeType();

                    if (tDutyFeeType.equals("A")  || tDutyFeeType.equals("B")||tDutyFeeType.equals("H"))
                    {
                        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                         * No找出计算公式并初始化理算金额
                         *  针对于门诊，费用编码前面需要增加A门诊或B住院
                         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                         */
                        String tDutyFeeCode1 = tLLToClaimDutyFeeSchema.getDutyFeeCode();
                        String tDutyFeeCode2 = tDutyFeeType + tDutyFeeCode1;

                        LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
        				tSql = "select * from LMDutyGetFeeRela where 1 = 1 "
							+ " and GetDutyCode = '" + tGetDutyCode + "'"
							+ " and GetDutyKind = '" + tGetDutyKind + "'"
							+ " and ClmFeeCode like '" 
							+ tLLToClaimDutyFeeSchema.getDutyFeeCode().substring(0, 2) + "%'";

                        LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB.executeQuery(tSql);
                        if (tLMDutyGetFeeRelaSet == null || tLMDutyGetFeeRelaSet.size() == 0 )
                        {
                            mErrors.addOneError("在LMDutyGetFeeRela表中没有查找到相关的数据，不能理算");
                            return false;
                        }

                        LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema = tLMDutyGetFeeRelaSet.get(1);
                        
                        
    					//01-录入或为空则账单金额从界面录入
						if(tLMDutyGetFeeRelaSchema.getClmFeeCalType()==null||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("")
								||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("01"))
						{
						
							t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额                    	
			                	 
			                t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(), "0.00");//待理算免赔额
				            t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//待理算金额
				            t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
							
							t_Sum_Temp = t_Sum_DutyFee;
							
					   }
						//调用算法进行校验,符合校验则将账单金额累计到计算金额,否则该账单金额被过滤掉
						else if(tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("02"))
						{
							String tCalCode = tLMDutyGetFeeRelaSchema.getClmFeeCalCode();
							
							if (tCalCode != null && !tCalCode.equals("")) {
								
								int tSum = (int)executepay(t_Sum_DutyFee, tCalCode,tLLToClaimDutyFeeSchema.getDutyFeeStaNo());
								
								if(tSum==1)
								{

									t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额                    	
					                	 
					                t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(), "0.00");//待理算免赔额
						            t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//待理算金额
						            t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
									
									t_Sum_Temp = t_Sum_DutyFee;
								}
							}
						}
						else
						{
							//取默认值
							t_Sum_DutyFee = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算金额	                    	
			                	 
							t_Sum_DutyFeeFranchise = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算免赔额
								
							t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
							t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
							
							
							t_Sum_Temp = t_Sum_DutyFee;
							
						}

						logger.debug("ClmNo(赔案号):"+tLLToClaimDutyFeeSchema.getClmNo()+",feeItemCode(账单子类型):"+tLLToClaimDutyFeeSchema.getDutyFeeCode()+
			            " ,原理算金额："+tLLToClaimDutyFeeSchema.getAdjSum()+",t_Sum_DutyFeeFranchise(免赔额):"+t_Sum_DutyFeeFranchise 
			             +" ,减去免赔额后的参与理算金额):"+t_Sum_DutyFee);



                    }//if 门诊，住院结束

                    
                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * 比例给付,原(伤残)费用计算
                     *
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    if (tDutyFeeType.equals("L"))
                    {
                        double tCaseRate   = 0;
                        double tExempt     = 0;
                        //double tStandMoney = 0;
                        //double tSumMoney   = 0;
                        tCaseRate = Double.parseDouble(new DecimalFormat("0.00").format(tCaseRate));
                        tCaseRate = mLLClaimDutyFeeSchema.getRealRate();
                       
                        
						t_Sum_DutyFee = Arith.round(getOamnt() + mLLClaimDetailSchema.getAddAmnt(),2);
                        
						t_Sum_Temp = t_Sum_DutyFee;

						t_Sum_DutyFee = Arith.round(t_Sum_DutyFee * tCaseRate,2);
						
						logger.debug("ClmNo(赔案号):"+mLLClaimDutyFeeSchema.getClmNo()+",feeItemCode(账单子类型):"+mLLClaimDutyFeeSchema.getDutyFeeCode()+
								",t_Sum_DutyFee(参与理算金额):"+t_Sum_Temp+",tCaseRate(给付比例):"+tCaseRate+",t_Sum_DutyFee2(理算后金额):"+t_Sum_DutyFee);

                    }

					/*
					 * 2008-10-17 根据MS的医疗账单类型没有手术信息表,D--手术,E--特定疾病,F--特定给付，而且代码类别已不是D,E,F，在此将这段屏蔽掉
					 * 今后如果又需要再根据设定的项目代码进行程序修改
					 */
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.6
					 * 手术、疾病，其他费用计算
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					if (//tDutyFeeType.equals("D") || tDutyFeeType.equals("E")|| tDutyFeeType.equals("F")||
							tDutyFeeType.equals("T")) {
						
                        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                         * No找出计算公式并初始化理算金额
                         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                         */

                        LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
                        tSql = "select * from LMDutyGetFeeRela where 1 = 1 "
                            +" and GetDutyCode = '"+tGetDutyCode+"'"
                            +" and GetDutyKind = '"+tGetDutyKind+"'"
                            +" and ClmFeeCode like '"+tLLToClaimDutyFeeSchema.getDutyFeeCode().substring(0, 2)+"%'"
                            ;

                        LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB.executeQuery(tSql);
                        if (tLMDutyGetFeeRelaDB.mErrors.needDealError())
                        {
                            // @@错误处理
                            CError.buildErr(this, "查询该产品费用定义信息出错，不能理算!");
                            return false;
                        }

                        if (tLMDutyGetFeeRelaSet == null || tLMDutyGetFeeRelaSet.size() == 0 )
                        {
                            CError.buildErr(this, "查询该产品费用定义信息出错，不能理算!");
                            return false;
                        }

                        LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema = tLMDutyGetFeeRelaSet.get(1);
                        
                    	//01-录入或为空则账单金额从界面录入
						if(tLMDutyGetFeeRelaSchema.getClmFeeCalType()==null||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("")
								||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("01"))
						{
							/**
							 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.3
							 * 根据计算公式进行计算 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
							 */

							t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额	                    	
			                	 
			                t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(), "0.00");//待理算免赔额
				            t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//待理算金额
				            t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
				            
							
							t_Sum_Temp = t_Sum_DutyFee;
							
						}
						//调用算法进行校验,符合校验则将账单金额累计到计算金额,否则该账单金额被过滤掉
						else if(tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("02"))
						{
							String tCalCode = tLMDutyGetFeeRelaSchema.getClmFeeCalCode();
							
							if (tCalCode != null && !tCalCode.equals("")) {
								
								int tSum = (int)executepay(t_Sum_DutyFee, tCalCode,tLLToClaimDutyFeeSchema.getDutyFeeStaNo());
								
								if(tSum==1)
								{

									/**
									 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.3
									 * 根据计算公式进行计算 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
									 */
			
									t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额
	
					                t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(), "0.00");//待理算免赔额
						            t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//待理算金额
						            t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
									
									t_Sum_Temp = t_Sum_DutyFee;

								}
							}
						}
						else
						{

							//取默认值
							t_Sum_DutyFee = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算金额
							
			                //团险
			                //if (mLLDutyCtrlSet == null) {		                    	
			                	 
							t_Sum_DutyFeeFranchise = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算免赔额
								
							t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
							t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
							
							t_Sum_Temp = t_Sum_DutyFee;
						
						}

						logger.debug("ClmNo(赔案号):"+tLLToClaimDutyFeeSchema.getClmNo()+",feeItemCode(账单子类型):"+tLLToClaimDutyFeeSchema.getDutyFeeCode()+
					            " ,原理算金额："+tLLToClaimDutyFeeSchema.getAdjSum()+",t_Sum_DutyFeeFranchise(免赔额):"+t_Sum_DutyFeeFranchise 
					             +" ,减去免赔额后的参与理算金额):"+t_Sum_DutyFee);

                    }
					
					
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.7
					 * 进行医疗费用层的置值 及 保项层面的金额累计
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */

					mLLClaimDutyFeeSchema.setCalSum(t_Sum_DutyFee);
					mLLClaimDutyFeeSchema.setCurrency(tLLToClaimDutyFeeSchema.getCurrency());
					mLLClaimDutyFeeSet.add(mLLClaimDutyFeeSchema);
					t_Sum_DutyCode = t_Sum_DutyCode + t_Sum_DutyFee;

					logger.debug("======================================================================================================");
					logger.debug("医疗费用层:理赔类型:["
							+ tLLToClaimDutySchema.getGetDutyKind()
							+ "],保项编码:["
							+ tLLToClaimDutySchema.getGetDutyCode()
							+ "],计算公式:[" + t_CalDutyCode + "],医疗计算金额="
							+ t_Sum_Temp + ",理算结果=" + t_Sum_DutyFee
							+ ",费用类型[" + tDutyFeeType + "],费用代码["
							+ mLLClaimDutyFeeSchema.getDutyFeeCode()
							+ "],费用名称["
							+ mLLClaimDutyFeeSchema.getDutyFeeName() + "]");
					logger.debug("======================================================================================================");

                }//for循环结束

                t_Sum_Temp = t_Sum_DutyCode; /*保存计算前金额,用于显示*/
                t_Sum_DutyCode = executepay(t_Sum_DutyCode, t_CalDutyCode,"");

            	logger.debug("======================================================================================================");
            	logger.debug("医疗保项层:理赔类型:["
					+ tLLToClaimDutySchema.getGetDutyKind() + "],保项编码:["
					+ tLLToClaimDutySchema.getGetDutyCode() + "],计算公式:["
					+ t_CalDutyCode + "],计算前金额=" + t_Sum_Temp + ",计算后金额="
					+ t_Sum_DutyCode);
	            logger.debug("======================================================================================================");

            }//if 7.8结束,【InpFlag＝2，医疗费用录入】


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.11 根据计算公式进行帐户型费用计算【InpFlag＝3，帐户处理】
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (tInpFlag.equals("3") && tLLToClaimDutySchema.getGiveType().equals("0"))
            {
                mLLClaimDetailSchema.setAcctFlag("1");//帐户标志.0非帐户,1个险帐户


                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No7.11.3 根据累计帐户余额+利息 计算 保项值
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                //有效保额 = 标准给付金额 - 已领金额
                t_Sum_Temp = mLLClaimDetailSchema.getAmnt();
              
                t_Sum_DutyCode = executepay(t_Sum_Temp , t_CalDutyCode,"");

                logger.debug("======================================================================================================");
                logger.debug("帐户保项层:理赔类型:["+tLLToClaimDutySchema.getGetDutyKind()+"],保项编码:["+tLLToClaimDutySchema.getGetDutyCode()+"],计算公式:["+t_CalDutyCode+"],计算前金额="+t_Sum_Temp+",计算后金额="+t_Sum_DutyCode);
                logger.debug("======================================================================================================");
            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.12 计算附加险[266,267非典附加],采用主险的保额,公式进行计算
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

            String tEffectOnMainRisk = StrTool.cTrim(mLLClaimDetailSchema.getEffectOnMainRisk());
            if (tEffectOnMainRisk.equals("01"))
            {
                t_CalDutyCode = StrTool.cTrim(mLLClaimDetailSchema.getRiskCalCode());
                t_Sum_Temp = mLLClaimDetailSchema.getAmnt();
                t_Sum_DutyCode = executepay(t_Sum_Temp , t_CalDutyCode);

                logger.debug("======================================================================================================");
                logger.debug("附加险影响主险计算:理赔类型:["+tLLToClaimDutySchema.getGetDutyKind()+"],保项编码:["+tLLToClaimDutySchema.getGetDutyCode()+"],计算公式:["+t_CalDutyCode+"],计算前金额="+t_Sum_Temp+",计算后金额="+t_Sum_DutyCode);
                logger.debug("======================================================================================================");

            }
            */


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.13 计算出的保项金额和保额相比较，不能超过标准保额,
             *  tLLToClaimDutySchema.getGetDutyKind().substring(2,3).equals("0") ||
             *  保项理算金额 >= 标准保额 － 已领金额
             *      则保项理算金额 ＝ 标准保额 － 已领金额
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
//            tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode());
//            if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"7"))//该判断不使用
            //Y代表校验，N代表不校验
//            if(tLMDutyGetClmSchema.getDeadToPValueFlag().equals("Y"))
//            {
//                //getAmnt在数据匹配时没有减去已经赔出去的钱，在LLClaimCalMatchBL里处理
////                t_Sum_DutyBalCode = PubFun.setPrecision(mLLClaimDetailSchema.getAmnt() - mLCGetSchema.getSumMoney()
////                                                        + mLLClaimDetailSchema.getAddAmnt() ,"0.00");
//                //原来的getAmnt =  标准保额 － 已领金额
//                t_Sum_DutyBalCode = PubFun.setPrecision(mLLClaimDetailSchema.getAmnt()
//                                                        + mLLClaimDetailSchema.getAddAmnt() ,"0.00");
//                if (t_Sum_DutyCode >= t_Sum_DutyBalCode )
//                {
//                    t_Sum_DutyCode = t_Sum_DutyBalCode;
//                }
//            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.14 对于承保前出险，只赔付保额的20％，最多不超过5万
             * 判断条件:
             * 1承保前保单
             * 2出险原因为意外
             * 3理赔类型不能是豁免
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            if (mLLClaimDetailSchema.getPolSort().equals("A")
                    && tLLToClaimDutySchema.getGetDutyKind().substring(0,1).equals("1")
                    && !tLLToClaimDutySchema.getGetDutyKind().substring(2,3).equals("9"))
            {
                t_Sum_Temp = PubFun.setPrecision(mLCGetSchema.getStandMoney() - mLCGetSchema.getSumMoney(),"0.00");
                t_Sum_Temp = t_Sum_Temp * 0.2;


//                if ( tLLToClaimDutySchema.getGetDutyKind().substring(1,2).equals("00") )
//                {
//                    if ( t_Sum_DutyCode > t_Sum_Temp )
//                    {
//                        t_Sum_DutyCode = t_Sum_Temp;
//                    }
//                }

                t_Sum_DutyCode = t_Sum_Temp;
                if (t_Sum_DutyCode >= 50000 )
                {
                    t_Sum_DutyCode = 50000;
                }


            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.15 对为成年人进行判断,如果小于16岁,最多给5万
             * 泰康没有这个控制 2006-03-27 注释 P.D
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
//
//            int tAge = PubFun.calInterval(mLCPolSchema.getInsuredBirthday(),this.mAccDate, "Y");
//            if ( tAge<=16 )
//            {
//                if (t_Sum_DutyCode >= 50000 )
//                {
//                    t_Sum_DutyCode = 50000;
//                }
//            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.20 格式化 总给付金额
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            String tSumMoney = new DecimalFormat("0.00").format(t_Sum_DutyCode);
            t_Sum_DutyCode = Double.parseDouble(tSumMoney);

//            if (t_Sum_DutyCode<0){
//
//                t_Sum_DutyCode=0;
//
//            }

            //保全退保后理赔  理算金额 - 保全退费
            //if(tGetMoney < 0 && tPosFlag.equals("0")){
            if(tGetMoney < 0){
                    t_Sum_DutyCode = t_Sum_DutyCode + tGetMoney;
                    if(t_Sum_DutyCode < 0){
                        tGetMoney = t_Sum_DutyCode;
                        t_Sum_DutyCode = 0;
                    }else{
                        tGetMoney = 0;
                    }
                mLLClaimDetailSchema.setPosFlag("3");         //3保全退费并且已经在本赔案中扣除
            }

            //保单理赔类型的给付总金额,用于累计计算
            if ( tLLToClaimDutySchema.getGiveType().equals("0") )
            {
                t_Sum_DutyKind = t_Sum_DutyCode + t_Sum_DutyKind;
            }
            else
            {
                t_Sum_DutyCode = 0 ;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.21 添加数据
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            mLLClaimDetailSchema.setStatType(tLMDutyGetClmSchema.getStatType());
            mLLClaimDetailSchema.setOverAmnt(toutAmnt);
            mLLClaimDetailSchema.setStandPay(t_Sum_DutyCode);
            mLLClaimDetailSchema.setRealPay(t_Sum_DutyCode);
            mLLClaimDetailSet.add(mLLClaimDetailSchema);

        } //No7.0 for 结束


      //创建赔案明细记录
      mLLClaimPolicySchema.setClmNo(this.mClmNo);
      mLLClaimPolicySchema.setGrpContNo(mLCPolSchema.getGrpContNo());
      mLLClaimPolicySchema.setStandPay(t_Sum_DutyKind);
      mLLClaimPolicySchema.setRealPay(t_Sum_DutyKind);
      mLLClaimPolicySchema.setModifyDate(PubFun.getCurrentDate());
      mLLClaimPolicySchema.setModifyTime(PubFun.getCurrentTime());
      mLLClaimPolicySchema.setGiveType("0");
      mLLClaimPolicySchema.setGiveTypeDesc("");


      if(mLLClaimSchema.getCurrency().equals(mLLClaimPolicySchema.getCurrency()))
			t_Sum_Claim = t_Sum_Claim + t_Sum_DutyKind;
		else
		{
			LDExch tLDExch = new LDExch();
			t_Sum_Claim = t_Sum_Claim + tLDExch.toOtherCur(mLLClaimPolicySchema.getCurrency(),mLLClaimSchema.getCurrency(),PubFun.getCurrentDate(),t_Sum_DutyKind);
		}

      //赔案给付金额
      this.mLLClaimSchema.setStandPay(t_Sum_Claim);

      //最后给付金额 = 赔案给付金额 - 预付金额 + 结算金额
      t_Sum_Claim = mLLClaimSchema.getStandPay() - mLLClaimSchema.getBeforePay() + mLLClaimSchema.getBalancePay();
      this.mLLClaimSchema.setRealPay(t_Sum_Claim);

      mLLClaimPolicySet.add(mLLClaimPolicySchema);


      return true;
    }


    /**
     * 目的：得到个人险种表信息
     * @return
     */
    private boolean getLCLBPol(String pPolNo)
    {

        LCPolSchema tLCPolSchema = new LCPolSchema();

        SynLCLBPolBL tSynLCLBPolBL = new SynLCLBPolBL();
        tSynLCLBPolBL.setPolNo(pPolNo);

        tSynLCLBPolBL.getInfo();
        if (tSynLCLBPolBL.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tSynLCLBPolBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "calDutyPay";
            tError.errorMessage = "LCLBDuty表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        tLCPolSchema.setSchema(tSynLCLBPolBL.getSchema());

        mLLClaimPolicySchema.setGrpContNo(tLCPolSchema.getGrpContNo()); //集体合同号
        mLLClaimPolicySchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());   //集体保单号
        mLLClaimPolicySchema.setContNo(tLCPolSchema.getContNo());       //个单合同号
        mLLClaimPolicySchema.setPolNo(tLCPolSchema.getPolNo());         //保单号

        mLLClaimPolicySchema.setKindCode(tLCPolSchema.getKindCode());   //险类代码
        mLLClaimPolicySchema.setRiskCode(tLCPolSchema.getRiskCode());   //险种代码
        mLLClaimPolicySchema.setRiskVer(tLCPolSchema.getRiskVersion()); //险种版本号
        mLLClaimPolicySchema.setPolMngCom(tLCPolSchema.getManageCom()); //保单管理机构

        mLLClaimPolicySchema.setInsuredNo(tLCPolSchema.getInsuredNo());     //被保人客户号
        mLLClaimPolicySchema.setInsuredName(tLCPolSchema.getInsuredName()); //被保人名称
        mLLClaimPolicySchema.setAppntNo(tLCPolSchema.getAppntNo());         //投保人客户号
        mLLClaimPolicySchema.setAppntName(tLCPolSchema.getAppntName());     //投保人名称

        mLLClaimPolicySchema.setCValiDate(tLCPolSchema.getCValiDate());     //保单生效日期
        mLLClaimPolicySchema.setPolState(tLCPolSchema.getPolState());       //保单状态
        mLLClaimPolicySchema.setMngCom(this.mGlobalInput.ManageCom);        //立案的管理机构
        mLLClaimPolicySchema.setCurrency(tLCPolSchema.getCurrency());

        mLCPolSchema.setSchema(tLCPolSchema);
        // logger.debug("查询的保单号码是"+mLCPolBL.getSchema().getPolNo());
        return true;
    }


    /**
     * 得到立案分案信息
     * @return
     */
    private boolean getLLCase(String pCusNo)
    {
        String tInsNo = mLCPolSchema.getInsuredNo();        //得到保单被保人编号
        String tAppNo = mLCPolSchema.getAppntNo();          //得到保单投保人编号


        LLCaseSet tLLCaseSet = new LLCaseSet();
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(this.mClmNo);
        tLLCaseDB.setCustomerNo(pCusNo);
        tLLCaseSet = tLLCaseDB.query();

        if(tLLCaseSet == null || tLLCaseSet.size() == 0 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLCaseDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getLLCase";
            tError.errorMessage = "在LLCaseDB表中没有找到记录，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            mLLCaseSchema = tLLCaseSet.get(1);

        }
        return true;
    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－结束－－－－－－－理赔计算－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－开始－－－－－－－计算要素准备－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    
    /**
     * 目的：理赔计算
     * @param pCalSum 计算金额
     * @param pCalCode 计算公式
     * @param pDutyFeeStaNo 账单明细编码
     * @return double
     */
    private double executepay(double pCalSum, String pCalCode,String pDutyFeeStaNo)
    {
        double rValue;

        if (pCalCode == null || pCalCode.equals(""))
        {
            return 0;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 获取出险时点之前做过保全复效的批单号
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tContNo = mLLClaimDetailSchema.getContNo();      //合同号
        String tPolNo = mLLClaimDetailSchema.getPolNo();        //保单险种号
        String tNBPolNo = mLLClaimDetailSchema.getNBPolNo();    //承保时的保单险种号
// 2006-03-27 泰康不需要这个要素 注释掉 P.D
//        LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemBefore(tContNo,tPolNo,this.mAccDate,"RE");//出险时点前的保全批单号
        String tPosEdorNoFront = "";
//        if (tLPEdorItemSchema!=null)
//        {
//            tPosEdorNoFront = StrTool.cTrim(tLPEdorItemSchema.getEdorNo());
//        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 设置各种计算要素
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */


        logger.debug("==================================================================");

        //增加基本要素,计算给付金
        TransferData tTransferData = new TransferData();
        
        logger.debug("案件:"+this.mClmNo+",出险日期:"+this.mInsDate);

        //出险日期
		tTransferData.setNameAndValue("AccidentDate", String.valueOf(this.mInsDate));
		
		// 住院天数
		tTransferData.setNameAndValue("DaysInHos", String.valueOf(getSumDayCount(mLLClaimDetailSchema)));
		
        //终了红利
        //tTransferData.setNameAndValue("FinalBonus", String.valueOf("0"));

        //投保人职业类别        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("OccupationType", String.valueOf(getOccupationType()));

        //住院天数
        //tTransferData.setNameAndValue("DaysInHos", String.valueOf(mLLClaimDutyFeeSchema.getDayCount()));//下面已有

        //保险结束日期
        tTransferData.setNameAndValue("EndPolDate", String.valueOf(mLCPolSchema.getEndDate()));

        //费用开始日期
        tTransferData.setNameAndValue("StartFeeDate", String.valueOf(mLLClaimDutyFeeSchema.getStartDate()));

        //费用结束日期
        tTransferData.setNameAndValue("EndFeeDate", String.valueOf(mLLClaimDutyFeeSchema.getEndDate()));

        //伤残比例
        tTransferData.setNameAndValue("DefoRate", String.valueOf(mLLClaimDutyFeeSchema.getRealRate()));

        //每天床位费
//        tTransferData.setNameAndValue("InHospdayFee", String.valueOf(mLLClaimDutyFeeSchema.getAdjSum()));

        //治疗类型（B为住院治疗；A为门诊治疗）        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("CureType", String.valueOf(mLLClaimDutyFeeSchema.getDutyFeeType()));

        //保费（包括健康加费和职业加费及出险时点的保全补退费）,取自出险时间,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("TotalPrem", String.valueOf(getTotalPrem()));

        //起领日期,取自出险时点,需要考虑保全         //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("GetStartDate", String.valueOf(getGetStartDate()));

        //累计红利保额,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("SumAmntBonus", String.valueOf(mLLClaimPubFunBL.getYearBonus(mLCPolSchema.getPolNo(),this.mAccDate)));

        //出险时已保整月数        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("Rgtmonths", String.valueOf(PubFun.calInterval(mLCPolSchema.getCValiDate(),this.mAccDate, "M")));

        //给付责任类型        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("GetDutyKind", String.valueOf(mLLClaimDetailSchema.getGetDutyKind()));

        //保单号
        tTransferData.setNameAndValue("PolNo", String.valueOf(mLLClaimDetailSchema.getPolNo()));

        //赔案号
        tTransferData.setNameAndValue("CaseNo", String.valueOf(this.mClmNo));

        //出险人编号
        tTransferData.setNameAndValue("InsuredNo", String.valueOf(mLLCaseSchema.getCustomerNo()));

        //出险时已保年期        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun.calInterval(mLCPolSchema.getCValiDate(),this.mAccDate, "Y")));

        //出险时已保天数
        tTransferData.setNameAndValue("RgtDays", String.valueOf(PubFun.calInterval(mLCPolSchema.getCValiDate(),mLLCaseSchema.getAccDate(), "D")+1));

        //被保人性别        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("Sex", String.valueOf(mLCPolSchema.getInsuredSex()));

        //累计保费(不包括健康加费和职业加费),取自出险时点,需要考虑保全
        tTransferData.setNameAndValue("SumPrem", String.valueOf(getAllPrem()));

        //保单生效日期
        tTransferData.setNameAndValue("CValiDate", String.valueOf(mLCPolSchema.getCValiDate()));

        //保险年期,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("Years", String.valueOf(getYears()));

        //已交费年期,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("AppAge", String.valueOf(PubFun.calInterval(mLCPolSchema.getCValiDate(),getPaytoDate(), "Y")));

       //被保人投保年龄        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("InsuredAppAge", String.valueOf(getInsuredAppAge()));

        //被保人0岁保单生效对应日        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("InsuredvalidBirth", getInsuredvalideBirth());

        //计算给付金
        tTransferData.setNameAndValue("Je_gf", String.valueOf(pCalSum));

        //份数,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("Mult", String.valueOf(getMult()));

        //交费间隔        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("PayIntv", String.valueOf(mLCPolSchema.getPayIntv()));

        //医疗费用序号        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("DutyFeeStaNo", String.valueOf(mLLClaimDutyFeeSchema.getDutyFeeStaNo()));

        //总保费[该要素将被删除],取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("Prem", String.valueOf(mLCPolSchema.getPrem()));


        //保单累计支付,取自出险时点,需要考虑保全  
        tTransferData.setNameAndValue("PolPay", String.valueOf(getPolPay()));
        
        tTransferData.setNameAndValue("PolPay1", String.valueOf(getPolPay1()));

        //生存标记(0表示为生存，1表示为死亡)        //2003-03-27 P.D 注释 泰康不需要这个要素
//       tTransferData.setNameAndValue("DeadFlag", String.valueOf(getDeadFlag()));

        //医疗费用编码
//        tTransferData.setNameAndValue("DutyFeeCode", String.valueOf(mLLClaimDutyFeeSchema.getDutyFeeCode()));

        //续保标记（0为没有续保，1为按期续保）        //2003-03-27 P.D 注释 泰康不需要这个要素
//        if (mLCPolSchema.getRenewCount() == 0)
//        {
//            tTransferData.setNameAndValue("RnewFlag", String.valueOf(mLCPolSchema.getRenewCount()));
//        }
//        else
//        {
//            tTransferData.setNameAndValue("RnewFlag", String.valueOf("1"));
//        }

		// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
		double tDAmnt = getAmnt();
		tTransferData.setNameAndValue("Amnt", String.valueOf(tDAmnt));
		
		// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额不一致时(既lcget的standmoney不一致)时
		double tMaxAmnt = getMaxAmnt();
		tTransferData.setNameAndValue("MaxAmnt", String.valueOf(tMaxAmnt));
		

        //初始基本保额,取自出险时点,需要考虑保全
        tTransferData.setNameAndValue("Oamnt", String.valueOf(getOamnt()));

        //交费年期        //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("PayYears", String.valueOf(mLCDutySchema.getPayYears()));

        //续保次数,用于医疗类附加险计算        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("RenewCount", String.valueOf(mLCPolSchema.getRenewCount()));

        //出险时年龄        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("GetAge", String.valueOf(PubFun.calInterval(mLCPolSchema.getInsuredBirthday(),this.mAccDate, "Y")));

        //责任代码
        tTransferData.setNameAndValue("DutyCode", String.valueOf(mLCDutySchema.getDutyCode()));
        
		// 有效保额(对于账户型的就是账户价值)
		tTransferData.setNameAndValue("ValidAmnt", String
				.valueOf(mLLClaimDetailSchema.getAmnt()));
		logger.debug("计算的有效保额是"
				+ tTransferData.getValueByName("ValidAmnt"));
		
		// InpFlag==2时直接从账户取的账户价值
		tTransferData.setNameAndValue("InsuAccBala", String
				.valueOf(getInsureAcc(mLCPolSchema.getPolNo(),String.valueOf(this.mInsDate))));
		logger.debug("InpFlag==2时直接从账户取的账户价值是"
				+ tTransferData.getValueByName("InsuAccBala"));
		
		// 得到每个给付责任的累计赔付金额,适用于除费用补偿型险种之外的所有险种
		tTransferData.setNameAndValue("GetDutySumPay", String.valueOf(getGetDutySumPay()));
		
		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任共享险种保额
		tTransferData.setNameAndValue("CurrentDutyPay", String.valueOf(getCurrentDutyPay()));
		
		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任分類共享不同的保额
		tTransferData.setNameAndValue("CurrentClassifiDutyPay", String.valueOf(getCurrentClassifiDutyPay()));
		
		// 得到每个给付责任的累计赔付金额,针对连带被保险人产品
		tTransferData.setNameAndValue("GetDutyRelateSumPay", String.valueOf(getGetDutyRelateSumPay()));
		
		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任分類共享不同的保额，针对连带被保险人产品
		tTransferData.setNameAndValue("CurrentRelateDutyPay", String.valueOf(getCurrentRelateDutyPay()));
		
		// 给付责任的赔付次数
		tTransferData.setNameAndValue("ClaimCount", String.valueOf(getGetDutyClaimCount()));
		
		// 与主被保险人关系
		tTransferData.setNameAndValue("RelationToInsured", String.valueOf(getRelationToInsured(mLCPolSchema.getPolNo(),mLLCaseSchema.getCustomerNo())));
	
		
		//账单费用项目编码
		tTransferData.setNameAndValue("DutyFeeCode", getDutyItemCode(mLLClaimDetailSchema.getClmNo(),pDutyFeeStaNo));
		
		//账单结束日期
		tTransferData.setNameAndValue("FeereceiEndDate", getFeereceiEndDate(mLLClaimDetailSchema.getClmNo(),pDutyFeeStaNo));
		
		

        //VPU        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("VPU", String.valueOf(getVPU()));

        //费用明细的流水号        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("DutyFeeStaNo", String.valueOf(mLLClaimDutyFeeSchema.getDutyFeeStaNo()));


        //事故号        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("CaseRelaNo", String.valueOf(mAccNo));


        //每期标准保费,,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("StandPrem", String.valueOf(getPeriodPrem()));


        //交费期限
        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("PayEndDate", String.valueOf(mLCDutySchema.getPayEndDate()));

        //交费终止期间
        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("PayEndYear", String.valueOf(mLCDutySchema.getPayEndYear()));

        //客户号
        tTransferData.setNameAndValue("CustomerNo", String.valueOf(mLLClaimDetailSchema.getCustomerNo()));

        //保单的复效日期        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("LRDate", String.valueOf(mLCPolSchema.getLastRevDate()));

        //保单是否复效的标记        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("LRFlag", String.valueOf(getLRFlag(tPosEdorNoFront,tPolNo)));

        //复效到出险时已保年期,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("LRYears", String.valueOf(PubFun.calInterval(getLastRevDate(tPosEdorNoFront,tContNo,tPolNo),this.mAccDate, "Y")));

        //复效到出险时已保天数,取自出险时点,需要考虑保全        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("LRDays", String.valueOf(PubFun.calInterval(getLastRevDate(tPosEdorNoFront,tContNo,tPolNo),this.mAccDate, "D")));

        // 交费次数 ,取自出险时点,需要考虑保全 //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("PayTimes", String.valueOf(getPayTimes()));

        // 利差返还后增加的保额 //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("RateAmnt", String.valueOf("0"));


        // 续保投保标志,0未续保,1已续保  //2003-03-27 P.D 注释 泰康不需要这个要素
        tTransferData.setNameAndValue("AppFlag", String.valueOf(getAppFlag(tPosEdorNoFront,mLCPolSchema)));

        // 当前准备计算的保单的投保标志,1正常,4终止,9续保  //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("CurrAppFlag", String.valueOf(mLCPolSchema.getAppFlag()));

        // 当前准备计算的保单的总共续保次数
        //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("CurrRenewCount", String.valueOf(getCurrRenewCount(mLCPolSchema)));

        // 投保单号   //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("ProposalNo", String.valueOf(mLCPolSchema.getProposalNo()));

        // 保单号
        tTransferData.setNameAndValue("PolNo", String.valueOf(mLCPolSchema.getPolNo()));



        // LCGet的开始时间    //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("LCGetStartDate", String.valueOf(mLCGetSchema.getGetStartDate()));

        // LCGet的终止时间 //2003-03-27 P.D 注释 泰康不需要这个要素
//        tTransferData.setNameAndValue("LCGetEndDate", String.valueOf(mLCGetSchema.getGetEndDate()));

        // 合同号
        tTransferData.setNameAndValue("ContNo", String.valueOf(mLCGetSchema.getContNo()));

        //给付责任编码
        tTransferData.setNameAndValue("GetDutyCode", String.valueOf(mLCGetSchema.getGetDutyCode()));

        //封顶线
        tTransferData.setNameAndValue("PeakLine",String.valueOf(mLCDutySchema.getPeakLine()));

        //档次
        tTransferData.setNameAndValue("StandbyFlag1",String.valueOf(mLCDutySchema.getStandbyFlag1()));

        //
        tTransferData.setNameAndValue("StandbyFlag2",String.valueOf(mLCDutySchema.getStandbyFlag2()));
        
        //住院天数
        //tTransferData.setNameAndValue("DaysInHos", String.valueOf(mLLClaimDutyFeeSchema.getDayCount()));//原来取住院天数时没有区分是住院还是门诊，这样就会把住院和门诊的天数加起来，会多取一天门诊的，导致计算住院费用时错误---liuyu--2008-1-11

        
        //过往赔付金额
        //tTransferData.setNameAndValue("SumMoney", String.valueOf(mLCGetSchema.getSumMoney()));
        //住院起付线
        //tTransferData.setNameAndValue("HospLineAmnt", String.valueOf(getHospLineAmnt(mLCGetSchema.getInsuredNo())));

        
        //理赔控制上限下限控制要素
        tTransferData.setNameAndValue("GrpContNo", String.valueOf(mLCGetSchema.getGrpContNo()));
//        tTransferData.setNameAndValue("ContType", tContType);
//        tTransferData.setNameAndValue("ContPlanCode", tContPlanCode);
        
        //险种编码
        tTransferData.setNameAndValue("RiskCode", String.valueOf(mLLClaimDetailSchema.getRiskCode()));
        /**
         * 2009-04-09 zhangzheng
         * 针对特约增加计算要素
         */
        pLMDutyGetClmSchema=mLLClaimPubFunBL.getLMDutyGetClm(mLLClaimDetailSchema.getGetDutyKind(), mLLClaimDetailSchema.getGetDutyCode());
        
        if(mLLDutyCtrlSet==null)
        {
        	  mExemptDate=String.valueOf(pLMDutyGetClmSchema.getDeDuctDay());//免陪天數
        	  
              mObserveDate=String.valueOf(pLMDutyGetClmSchema.getObsPeriod());//观察期
                          
      		  //赔付比例
      		  if (!mLLClaimPubFunBL.getLMRiskSort(mLLClaimDetailSchema.getRiskCode(), "18")) //投保人豁免险有可能不存在其作为被保险人的保单,所以不查询赔付比例
      		  {
      			  mGetRate=String.
	        		  valueOf(getSocialInsuRate(mLLClaimDetailSchema.getRiskCode(),
  	        				  mLLClaimPubFunBL.getLCInsured(tContNo, mLLClaimDetailSchema.getCustomerNo()).getSocialInsuFlag()));
      		  }

      		  mStartLine=String.valueOf(pLMDutyGetClmSchema.getMinGet());//起付线
      		  
      		  mEndLine=String.valueOf(pLMDutyGetClmSchema.getMaxGet());//封顶线

      		  mTotalLimit=String.valueOf(pCalSum);//赔付限额
         	
              mExemptMoney="0";//免赔额
        }
        else
        {
            //免陪天数
        	mExemptDate = mLLClaimPubFunBL.getLLDutyCtrlPoint(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
        										, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode(), "ExemptDate", "0","0");
        	
            //观察期
        	mObserveDate = mLLClaimPubFunBL.getLLDutyCtrlPoint(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
        										, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode(), "ObserveDate", "0","0");
        	
            //赔付比例
        	mGetRate = mLLClaimPubFunBL.getLLDutyCtrlPoint(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
        										, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode(), "ClaimRate", "0","0");
        	
            //起付线
        	mStartLine = mLLClaimPubFunBL.getLLDutyCtrlPoint(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
        										, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode(), "StartPay", "0","0");
        	
            //封顶线
        	mEndLine = mLLClaimPubFunBL.getLLDutyCtrlPoint(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
        										, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode(), "EndPay", "0","0");      	
    		
        	//剩余可赔付限额
    		mTotalLimit=String.valueOf(getTotalLimit(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
												, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode()
										        ,mLLClaimDetailSchema.getRiskCode(),String.valueOf(pCalSum)));

    		  //免赔额
            mExemptMoney = mLLClaimPubFunBL.getLLDutyCtrlPoint
            	 	(mLLRegisterSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo(), mLLClaimDetailSchema.getDutyCode(),
            	 			mLLClaimDetailSchema.getGetDutyCode(), "Exempt", "0","0");
            
           if(mExemptMoney==null||mExemptMoney.trim().equals("")||mExemptMoney.trim().equals("-1"))
            {
            	mExemptMoney="";
            }
 
        }
        
        
  	  	//免陪天数
        if(mExemptDate==null||mExemptDate.trim().equals("")||mExemptDate.trim().equals("-1"))
        {
      	  	tTransferData.setNameAndValue("ExemptDate", String.valueOf(pLMDutyGetClmSchema.getDeDuctDay()));
        }
        else
        {
            tTransferData.setNameAndValue("ExemptDate",mExemptDate);
        }
        

  	    //观察期
        if(mObserveDate==null||mObserveDate.trim().equals("")||mObserveDate.trim().equals("-1"))
        {
      	  	tTransferData.setNameAndValue("ObserveDate", String.valueOf(pLMDutyGetClmSchema.getObsPeriod()));
        }
        else
        {
            tTransferData.setNameAndValue("ObserveDate",mObserveDate);
        }
        
        
        if ((mLCPolSchema.getRiskCode().equals("211801"))&&(mGetRate==null||mGetRate.trim().equals("")||mGetRate.trim().equals("-1")))
        {
        	mGetRate="";
        	tTransferData.setNameAndValue("SocialInsuRate", "");
        }
        else
        {	
  	    //赔付比例
         if(mGetRate==null||mGetRate.trim().equals("")||mGetRate.trim().equals("-1"))
          {
        	  //投保人豁免险有可能不存在其作为被保险人的保单,所以不查询赔付比例
    		  if (!mLLClaimPubFunBL.getLMRiskSort(mLLClaimDetailSchema.getRiskCode(), "18")) 
    		  {
    			  mGetRate=String.
	        		  valueOf(getSocialInsuRate(mLLClaimDetailSchema.getRiskCode(),
	        				  mLLClaimPubFunBL.getLCInsured(tContNo, mLLClaimDetailSchema.getCustomerNo()).getSocialInsuFlag()));
    			  
    			  if(mGetRate==null||mGetRate.trim().equals(""))
    			  {
    	 	      	  	tTransferData.setNameAndValue("SocialInsuRate", "1");
    			  }
    			  else
    			  {  
    				  	//产品定义也没有描述,则默认为1
        	            tTransferData.setNameAndValue("SocialInsuRate",mGetRate);
    			  }

    		  }
    		  else
    		  {
    	      	  	tTransferData.setNameAndValue("SocialInsuRate", "1");
    		  }
         }
          else
          {
            tTransferData.setNameAndValue("SocialInsuRate",mGetRate);
          }
        }

      
		//起付线
        if(mStartLine==null||mStartLine.trim().equals("")||mStartLine.trim().equals("-1"))
        {
      	  	tTransferData.setNameAndValue("StartLineAmnt", String.valueOf(pLMDutyGetClmSchema.getMinGet()));
        }
        else
        {
            tTransferData.setNameAndValue("StartLineAmnt",mStartLine);
        }
        
        
		//封顶线
        if(mEndLine==null||mEndLine.trim().equals("")||mEndLine.trim().equals("-1"))
        {
      	    tTransferData.setNameAndValue("EndLineAmnt", String.valueOf(pLMDutyGetClmSchema.getMaxGet()));
        }
        else
        {
            tTransferData.setNameAndValue("EndLineAmnt", mEndLine);
        }
        
       	//剩余可赔付限额	  
        if(mTotalLimit==null||mTotalLimit.trim().equals("")||mTotalLimit.trim().equals("-1"))
        {
      	    tTransferData.setNameAndValue("TotalLimit", String.valueOf(pCalSum));
        }
        else
        {
            tTransferData.setNameAndValue("TotalLimit", mTotalLimit);
        }
        
  	  	//免陪额
        if(mExemptMoney==null||mExemptMoney.trim().equals("")||mExemptMoney.trim().equals("-1"))
        {
      	  	tTransferData.setNameAndValue("ExemptMoney", "");
        }
        else
        {
            tTransferData.setNameAndValue("ExemptMoney",mExemptMoney);
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 针对于266,267,附加险采用主险的计算要素
         * 泰康不需要这样的险种 2006-03-27   P.D 注释
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        String tRiskCode = mLCPolSchema.getRiskCode();
//        if ( mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"12") )
//        {
//            LLClaimDetailSchema tRMLLClaimDetailSchema = getRMRiskInfo();
//            if ( tRMLLClaimDetailSchema != null )
//            {
//                String tRMContNo = mLLClaimDetailSchema.getContNo();
//                String tRMPolNo = mLLClaimDetailSchema.getPolNo();
//
//                /* 获取出险时点之前做过保全复效的批单号 */
//                String tRMPosEdorNoFront = "";
//                LPEdorItemSchema tRMLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemBefore(tRMContNo,tRMPolNo,this.mAccDate,"RE");//出险时点前的保全批单号
//                if (tRMLPEdorItemSchema!=null)
//                {
//                    tRMPosEdorNoFront = StrTool.cTrim(tRMLPEdorItemSchema.getEdorNo());
//                }
//
//
//                // 附险险所在主险的理赔给付金,为产品所加
//                tTransferData.setNameAndValue("RMAmnt", String.valueOf(tRMLLClaimDetailSchema.getRealPay()+tRMLLClaimDetailSchema.getYearBonus()+tRMLLClaimDetailSchema.getEndBonus()));
//
//                // 附险险所在主险的有效保额,为产品所加,基本保额 + 累计年度红利保额（包括非整年度部分）＋终了红利
//                double t_Sum_Amnt = 0;
//                t_Sum_Amnt = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Amnt));
//
//                EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
//                t_Sum_Amnt = tEdorCalZT.getFinalBonus(mLCPolSchema.getPolNo(),this.mAccDate);
//                t_Sum_Amnt = Arith.round(t_Sum_Amnt,2);
//                t_Sum_Amnt = t_Sum_Amnt + tRMLLClaimDetailSchema.getAmnt() + mLLClaimPubFunBL.getYearBonus(tRMLLClaimDetailSchema.getPolNo(),this.mAccDate);
//
//                tTransferData.setNameAndValue("TRMAmnt", String.valueOf(t_Sum_Amnt));
//
//                // 附险险所在主险的理赔给付金,为产品所加
//                tTransferData.setNameAndValue("MLRFlag", String.valueOf(getLRFlag(tRMPosEdorNoFront,tRMPolNo)));
//
//                //复效到出险时已保年期,取自出险时点,需要考虑保全
//                tTransferData.setNameAndValue("MLRDays", String.valueOf(PubFun.calInterval(getLastRevDate(tRMPosEdorNoFront,tRMContNo,tRMPolNo),this.mAccDate, "Y")));
//
//                //复效到出险时已保天数,取自出险时点,需要考虑保全
//                tTransferData.setNameAndValue("MLRYears", String.valueOf(PubFun.calInterval(getLastRevDate(tRMPosEdorNoFront,tRMContNo,tRMPolNo),this.mAccDate, "D")));
//
//                //主险的保单号
//                tTransferData.setNameAndValue("MainPolNo", String.valueOf(tRMPolNo));
//
//            }
//            else
//            {
//                String tMainPolNo = mLCPolSchema.getMainPolNo();        //得到主险的保单险种号
//                LCPolSchema tRMLCPolSchema = mLLClaimPubFunBL.getLCPol(tMainPolNo);
//                if (tRMLCPolSchema==null)
//                {
//                    this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
//                    return 0;
//                }
//                else
//                {
//                    String tRMContNo = tRMLCPolSchema.getContNo();
//                    String tRMPolNo = tRMLCPolSchema.getPolNo();
//
//                    /* 获取出险时点之前做过保全复效的批单号 */
//                    String tRMPosEdorNoFront = "";
//                    LPEdorItemSchema tRMLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemBefore(tRMContNo,tRMPolNo,this.mAccDate,"RE");//出险时点前的保全批单号
//                    if (tRMLPEdorItemSchema!=null)
//                    {
//                        tRMPosEdorNoFront = StrTool.cTrim(tRMLPEdorItemSchema.getEdorNo());
//                    }
//
//
//
//                    // 附险险所在主险的理赔给付金,为产品所加
//                    tTransferData.setNameAndValue("RMAmnt", String.valueOf("0"));
//
//                    // 附险险所在主险的有效保额,为产品所加,基本保额 + 累计年度红利保额（包括非整年度部分）＋终了红利
//                    tTransferData.setNameAndValue("TRMAmnt", String.valueOf(tRMLCPolSchema.getAmnt()));
//
//                    // 附险险所在主险的理赔给付金,为产品所加
//                    tTransferData.setNameAndValue("MLRFlag", String.valueOf(getLRFlag(tRMPosEdorNoFront,tRMPolNo)));
//
//                    //复效到出险时已保年期,取自出险时点,需要考虑保全
//                    tTransferData.setNameAndValue("MLRDays", String.valueOf(PubFun.calInterval(getLastRevDate(tRMPosEdorNoFront,tRMContNo,tRMPolNo),this.mAccDate, "Y")));
//
//                    //复效到出险时已保天数,取自出险时点,需要考虑保全
//                    tTransferData.setNameAndValue("MLRYears", String.valueOf(PubFun.calInterval(getLastRevDate(tRMPosEdorNoFront,tRMContNo,tRMPolNo),this.mAccDate, "D")));
//
//                    //主险的保单号
//                    tTransferData.setNameAndValue("MainPolNo", String.valueOf(tRMLCPolSchema.getPolNo()));
//                }
//
//            }
//
//        }

        pLMDutyGetClmSchema=null;

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 将所有设置的参数加入到PubCalculator.addBasicFactor()中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        Vector tVector = tTransferData.getValueNames();
        PubCalculator tPubCalculator = new PubCalculator();

        for (int i = 0; i < tVector.size(); i++)
        {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
//            logger.debug("PubCalculator.addBasicFactor--tName:" + tName + "  tValue:" + tValue);
            tPubCalculator.addBasicFactor(tName, tValue);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 将所有设置的参数加入到Calculator.addBasicFactor()中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(pCalCode);

        tVector = tTransferData.getValueNames();
        logger.debug("==================================================================");
        for (int i = 0; i < tVector.size(); i++)
        {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
            logger.debug("理算计算要素名称--tName:" + tName + "  tValue:" + tValue);
            tCalculator.addBasicFactor(tName, tValue);
        }
        logger.debug("==================================================================");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 进行计算，Calculator.calculate()
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tStr = "";
        logger.debug("计算公式=" + tCalculator.getCalSQL());
        tStr = tCalculator.calculate();
        if (tStr.trim().equals(""))
        {
            rValue = 0;
        }
        else
        {
            rValue = Double.parseDouble(tStr);
        }

        if (tCalculator.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tCalculator.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "executepay";
            tError.errorMessage = "计算发生错误!原公式:"+pCalCode+"||,解析后的公式:"+tCalculator.getCalSQL();
            this.mErrors.addOneError(tError);
        }
        logger.debug("==================================================================");
        return rValue;
    }
    
    
    
    /**
	 * 得到本次理算的赔付限额
	 * Param:tGrpcontNo,tCustomerNo,tCtrlLevel(控制级别),tRiskCode(险种编码),tCalSum(传入的待理算的给付金)
	 * 限额只控制三个级别:团单级,险种级,保障计划级别,其中前两种级别是团单共享或险种共享,保障计划级别是针对每个人的
	 * @return
	 */
	private double getTotalLimit(String tGrpContNo,String tCustomerNo,String tDutyCode,String tGetDutyCode,
									String tRiskCode,String tCalSum) {
		
        //初始限额
        double t_Sum_InitLimit = 0;
        t_Sum_InitLimit = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_InitLimit));

        //已经扣除的限额
        double t_Sum_PayLimit = 0;
        t_Sum_PayLimit = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_PayLimit));

        //剩余限额
        double t_Sum_BalLimit = 0;
        t_Sum_BalLimit = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_BalLimit));
        
        //控制级别
        String tCtrlLevel="";
        String tContPlanCode="";
                   
		String tSql = "select ctrllevel,ContPlanCode from lldutyctrl where grpcontno='"+tGrpContNo+"' and totallimit >-1 order by ctrllevel";
		logger.debug("--查询团单录入:"+tGrpContNo+"限额的最高控制级别和保障计划编码sql:"+tSql);
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS=tExeSQL.execSQL(tSql);
		
		if(tSSRS.getMaxRow()==0)
		{
			return Double.parseDouble(tCalSum);
		}
		else
		{
			tCtrlLevel=tSSRS.GetText(1, 1);
			tContPlanCode=tSSRS.GetText(1, 2);
		}

		
		logger.debug("--查询团单:"+tGrpContNo+"限额的控制级别:"+tCtrlLevel+",保障计划编码:"+tContPlanCode);
		
		//控制级别为空时表示录入的限额这个控制点没有录入值,则直接返回给付金,传入后并不影响算法结果
		if(tCtrlLevel.trim().equals(""))
		{
			t_Sum_BalLimit=Double.parseDouble(tCalSum);
		}
		else
		{
			t_Sum_InitLimit=Double.parseDouble(mLLClaimPubFunBL.getLLDutyCtrlPoint(mLLClaimDetailSchema.getGrpContNo(), mLLClaimDetailSchema.getCustomerNo()
					, mLLClaimDetailSchema.getDutyCode(),mLLClaimDetailSchema.getGetDutyCode(), "TotalLimit", "0",tCtrlLevel)); 
			
		    //当理赔责任控制层录入中没有录入限额时,使限额当做理赔金
		    if(t_Sum_InitLimit==-1)
		    {
				t_Sum_BalLimit=Double.parseDouble(tCalSum);
		    }
		    else
		    {
		    		//通过保障计划承保,必须要判断本次理算的出险人是否
		    		if(tCtrlLevel.equals("4")&&(!mLLClaimPubFunBL.getCheckLLDutyCtrl(mLLClaimDetailSchema.getGrpContNo()
		    				,mLLClaimDetailSchema.getCustomerNo(),tCtrlLevel)))
		    		{
		    			//出险人并非通过保障计划承保,且录入的限额最高控制级别是保障计划级别,则剩余可赔付限额取给付金,并不影响原有结构算法
						t_Sum_BalLimit=Double.parseDouble(tCalSum);
		    		}
		    		else
		    		{
		    			t_Sum_PayLimit = getLLDutyCtrlSumPay(mLLClaimDetailSchema.getGrpContNo(),tCtrlLevel,tContPlanCode,mLLClaimDetailSchema.getRiskCode(),mLLClaimDetailSchema.getCustomerNo())
			               + getLLDutyCtrlCurrentPay(mLLClaimDetailSchema.getGrpContNo(),tCtrlLevel,tContPlanCode,mLLClaimDetailSchema.getRiskCode(),mLLClaimDetailSchema.getCustomerNo());	
		    			
		    	    	t_Sum_BalLimit =t_Sum_InitLimit-t_Sum_PayLimit;
		    		}
		
		    	logger.debug("查询"+tGrpContNo+"录入的团单级的初始限额:"+t_Sum_InitLimit+",已赔付限额:"+t_Sum_PayLimit+",可赔付限额:"+t_Sum_BalLimit);

		    }	
		}
		
		/**
		 * 2009-05-06 zhangzheng
		 * 防止团单先进行赔付后录入的责任控制限额,导致剩余赔付金额<0，此时赔付限额定为0
		 */
		if(t_Sum_BalLimit<0)
		{
			t_Sum_BalLimit=0;
		}
		
		logger.debug("查询"+tGrpContNo+"录入的团单级的初始限额:"+t_Sum_InitLimit+",已赔付限额:"+t_Sum_PayLimit+",调整后的可赔付限额:"+t_Sum_BalLimit);
	
		tExeSQL=null;
		tSql=null;
		tSSRS=null;

		return t_Sum_BalLimit;
	}
	
	
	 /**
	 * 得到理赔特约控制层的既往赔付金
	 * Params:tGrpContNo,tCtrlLevel(控制级别),tRiskCode,tCustomerNo
	 * @return 理赔特约控制层的既往赔付金
	 */
	private double getLLDutyCtrlSumPay(String tGrpContNo,String tCtrlLevel,String tContPlancode,String tRiskCode,String tCustomerNo) {

		// 计算既往赔付金
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("--团单:"+tGrpContNo+",控制级别:"+tCtrlLevel+",保障计划编码,"+tContPlancode+"险种编码:"+tRiskCode+",客户号:"+tCustomerNo);

		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();
		
		//控制点是团单级
		if(tCtrlLevel.trim().equals("1"))
		{
	        //得到团单累计既往支付
	        tSql = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where 1=1 "
	            +" and a.ClmNo = b.ClmNo"
	            +" and a.ClmState in ('50','60')"       //赔案状态为结案、完成
	            +" and a.ClmNo <>'"+this.mClmNo+"'"
	            +" and b.GiveType != '1'"              //给付状态为给付
	            +" and b.grpcontno ='"+tGrpContNo+"'";
		}
		
		//控制点是保障计划既往级别
		if(tCtrlLevel.trim().equals("4"))
		{
	        //得到保障计划累计支付
	        tSql = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b, lcinsured c where 1=1 "
	            +" and a.ClmNo = b.ClmNo and b.grpcontno = c.grpcontno and b.customerno = c.insuredno"
	            +" and a.ClmState in ('50','60')"       //赔案状态为结案、完成
	            +" and a.ClmNo <>'"+this.mClmNo+"'"
	            +" and b.GiveType != '1'"              //给付状态为给付
	            +" and b.grpcontno ='"+tGrpContNo+"'"
	            +" and b.customerno='"+tCustomerNo+"'"
	            +" and riskcode in(select riskcode from LCContPlanRisk d where d.grpcontno='"+tGrpContNo+"' and d.contplancode='"+tContPlancode+"')";
		}
		
		//控制点是险种级
		if(tCtrlLevel.trim().equals("5"))
		{
	        //得到险种级累计既往支付
	        tSql = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where 1=1 "
	            +" and a.ClmNo = b.ClmNo"
	            +" and a.ClmState in ('50','60')"       //赔案状态为结案、完成
	            +" and a.ClmNo <>'"+this.mClmNo+"'"
	            +" and b.GiveType != '1'"              //给付状态为给付
	            +" and b.riskcode ='"+tRiskCode+"'"
            	+" and b.grpcontno ='"+tGrpContNo+"'";
		}
		
	

		logger.debug("--团单:"+tGrpContNo+",控制级别:"+tCtrlLevel+",保障计划编码,"+tContPlancode+"下的既往赔付金sql:"+tSql);


		t_Sum_Return = Double.parseDouble(tExeSQL.getOneValue(tSql));

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算团单"+tGrpContNo+",控制级别"+
					tCtrlLevel+"下的既往赔付金失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		tExeSQL=null;
		return t_Sum_Return;
	}
    
	
	/**
	 * 得到理赔特约控制层的中已经计算出的赔付金
	 * Params:tGrpContNo,tCtrlLevel(控制级别),tRiskCode,tCustomerNo
	 * @return 理赔特约控制层的已经算出的理赔金
	 * @return
	 */
	private double getLLDutyCtrlCurrentPay(String tGrpContNo,String tCtrlLevel,String tContPlanCode,String tRiskCode,String tCustomerNo) {

		// 计算既往赔付金
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("团单:"+tGrpContNo+",控制级别:"+tCtrlLevel+",险种编码:"+tRiskCode+",客户号:"+tCustomerNo);
		
		logger.debug("mLLClaimDetailSet.size():"+mLLClaimDetailSet.size());
		
		//控制点是团单级(该团单下的所有人共享限额)
		if(tCtrlLevel.trim().equals("1"))
		{
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{
				t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
			}
		}
		
		
		//控制点是保障计划既往级别
		if(tCtrlLevel.trim().equals("4"))
		{
			LCContPlanRiskSet tLCContPlanRiskSet=mLLClaimPubFunBL.getLCContPlanRisk(tGrpContNo,tContPlanCode);
			
			if(tLCContPlanRiskSet!=null)
			{
				String[] tLCContPlanRisk=new String[tLCContPlanRiskSet.size()];
				
				//查询到该保障计划里的险种
				for(int i=1;i<=tLCContPlanRiskSet.size();i++)
				{
					tLCContPlanRisk[i-1]=tLCContPlanRiskSet.get(i).getRiskCode();
				}
				
				
				for(int i=1;i<=mLLClaimDetailSet.size();i++)
				{
					//首先是同一人
					if(mLLClaimDetailSet.get(i).getCustomerNo().equals(tCustomerNo.trim()))
					{
						if(whetherClassifiRisk(mLLClaimDetailSchema.getRiskCode(),tLCContPlanRisk))
						{
							t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
						}
					}			
				}
								
			}
		}
		
		//控制点是险种级(购买该险种的所有人共享限额)
		if(tCtrlLevel.trim().equals("5"))
		{
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{
				if(mLLClaimDetailSet.get(i).getRiskCode().equals(tRiskCode))
				{
					t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
				}
			}
		}
		
		logger.debug("团单:"+tGrpContNo+",控制级别下:"+tCtrlLevel+",险种编码:"+"外的其他已经算出的理赔金总额:"+t_Sum_Return);

		return t_Sum_Return;
	}
    
    
    
    /**
	 * 得到每个给付责任的累计赔付次数
	 * 
	 * @return
	 */
	private double getGetDutyClaimCount() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 得到每个给付责任的累计赔付金额  正常赔付+预付赔付
		tSql =  " select nvl(sum(Times),0)  from ("
			    +"  select nvl(count(1),0) Times from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and b.GetDutyCode='"+mLLClaimDetailSchema.getGetDutyCode()+"'"
				+ " and b.realpay>0"
				+ " and a.clmno!='"+this.mClmNo+"'"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  union"
				+"  select nvl(count(1),0)  Times"
				+"  from ljagetclaim d where d.feeoperationtype='B'"
				+ " and d.GetDutyCode='"+mLLClaimDetailSchema.getGetDutyCode()+"'"
				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  and d.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+"  and d.pay >0"
				+ " and d.otherno!='"+this.mClmNo+"'"
				+"  )";
			
		logger.debug("--计算要素ClaimCounts[保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+mLLClaimDetailSchema.getGetDutyCode()+"累计支付次数]:" + tSql);	

		String tPayFranchise = tExeSQL.getOneValue(tSql);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+
					mLLClaimDetailSchema.getGetDutyCode()+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}
		
		tExeSQL=null;
		tCValiDate=null;
		tSql=null;

		return t_Sum_Return;
	}
	
	
	
    /**
	 * 得到每个给付责任的累计赔付次数
	 * 
	 * @return
	 */
	private String getRelationToInsured(String tPolNo,String tCustomerNo) {

		// 计算免赔金额。
		String t_Relation_Return = "";

		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 得到每个给付责任的累计赔付金额  正常赔付+预付赔付
		tSql =  " select relationtoinsured from lcinsuredrelated "
			    +"  where polno='"+tPolNo+"'"
				+ " and customerno='"+tCustomerNo+"'";
			
		logger.debug("--查询要素RelationToInsured[保单"+tPolNo+",客户号"+tCustomerNo+"与主被保险人关系]:" + tSql);	

		t_Relation_Return = tExeSQL.getOneValue(tSql);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询保单"+tPolNo+",客户号"+tCustomerNo+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (t_Relation_Return == null || t_Relation_Return.equals("")) {
			t_Relation_Return = "00";
		}

		return t_Relation_Return;
	}
	
	
	
	
    /**
	 * 得到账户价值
	 * 
	 * @return
	 */
	private double getInsureAcc(String tPolNo,String mInsDate) {

		// 计算账户价值
		
		double cashValue=0.0;
		double tInterest=0.0;
		double tResultValue=0.0;
		
		
		 LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		 
		 tLCInsureAccClassDB.setPolNo(tPolNo);
		 LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();

		 AccountManage tAccountManage = new AccountManage();
		 
		 TransferData pTransferData =null;
		 
		 //循环
		 for(int i=1;i<=tLCInsureAccClassSet.size();i++)
		 {
			  pTransferData = new TransferData();
			  
			  //针对康福险种特殊处理，累计账户金额使用当前日期
			  if("000010".equals(tLCInsureAccClassSet.get(i).getInsuAccNo() ) || "000011".equals(tLCInsureAccClassSet.get(i).getInsuAccNo() ))
			  {
				  pTransferData = tAccountManage.getAccClassInterestNewMS(tLCInsureAccClassSet.get(i),PubFun.getCurrentDate(),"Y","D");
			  }
			  else
			  {
			      pTransferData = tAccountManage.getAccClassInterestNewMS(tLCInsureAccClassSet.get(i),mInsDate,"Y","D");
			  }
			  
			  //本息和
			  cashValue = cashValue+Double.parseDouble(String.valueOf(pTransferData.getValueByName("aAccClassSumPay")));
	
			  tInterest = tInterest+Double.parseDouble(String.valueOf(pTransferData.getValueByName("aAccClassInterest")));//利息
			  
			  pTransferData = null;
			  
		 }
		 

//LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//
//			tLPEdorItemSchema.setPolNo(tPolNo);
//			tLPEdorItemSchema.setEdorValiDate(mInsDate);//结息日期;
//			
//			GrpEdorCalZT tGrpEdorCalZT = new GrpEdorCalZT();
//			

//		try {
//			cashValue = tGrpEdorCalZT.calZTData(tLPEdorItemSchema);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//个人保单的现金价值
//		//double dInsuGet = tGrpEdorCalZT.getPersonalGet();//个人保单个人交费部分的现金价值
//		//double canShareMoney = tGrpEdorCalZT.getCompanyGet();//个人保单集体交费的现金价值


		tResultValue=cashValue-tInterest;
			
		if (tResultValue < 0) {
			tResultValue=0;
		}
		
		logger.debug("PolNo:"+tPolNo+",的账户价值:"+tResultValue);

		return cashValue;
	}
    

	
    /**
	 * 得到每个给付责任的累计赔付金额
	 * 
	 * @return
	 */
	private double getGetDutySumPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();
		
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("10");//责任下多个给付责任分类共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
	
		
		if(!tLMRiskSortDB.getInfo())
        {
			// 得到每个给付责任的累计赔付金额  正常赔付+预付赔付
			tSql =  " select nvl(sum(Pay),0)  from ("
			    +"  select nvl(sum(b.RealPay),0) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and b.GetDutyCode='"+mLLClaimDetailSchema.getGetDutyCode()+"'"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+ " and a.clmno!='"+this.mClmNo+"'"
				+"  union"
				+"  select nvl(sum(d.Pay),0)  Pay"
				+"  from ljagetclaim d where d.feeoperationtype='B'"
				+ " and d.GetDutyCode='"+mLLClaimDetailSchema.getGetDutyCode()+"'"
				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  and d.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and d.otherno!='"+this.mClmNo+"'"
				+"  )";
			
			logger.debug("--计算要素GetDutySumPay[出险人:"+mLLClaimDetailSchema.getCustomerNo()+",保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+mLLClaimDetailSchema.getGetDutyCode()+"累计支付]:" + tSql);
			
        }
		else
		{
			//能够查询到记录,表示该险种是多个给付责任分类共享保额,所以查询到应该是共享统一保额的所有给负责人的既往赔付金额
			tSql =  " select nvl(sum(Pay),0)  from ("
			    +"  select nvl(sum(b.RealPay),0) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and a.clmno!='"+this.mClmNo+"'"
				+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"' " 
				+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"' and remark='"+tLMRiskSortDB.getRemark()+"')"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  union"
				+"  select nvl(sum(d.Pay),0)  Pay"
				+"  from ljagetclaim d where d.feeoperationtype='B'"
				+ " and d.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"' " 
				+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"' and remark='"+tLMRiskSortDB.getRemark()+"')"
				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  and d.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and d.otherno!='"+this.mClmNo+"'"
				+"  )";
			
			logger.debug("--计算要素GetDutySumPay[出险人:"+mLLClaimDetailSchema.getCustomerNo()+",保单"+mLLClaimDetailSchema.getPolNo()+",险种:"+tLMRiskSortDB.getRiskCode()+"在LMRiskSort描述的Remark等于"+tLMRiskSortDB.getRemark()+"的所有给付责任的累计支付]:" + tSql);
			
		}



		String tPayFranchise = tExeSQL.getOneValue(tSql);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+
					mLLClaimDetailSchema.getGetDutyCode()+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}

		return t_Sum_Return;
	}
	
	
	/**
	 * 得到共享保额的给付责任中已经计算出的赔付金
	 * 如果不是共享保额的责任,或是第一个给付责任,则返回0
	 * 适用于责任下多个给付责任共享险种保额,理算时给付责任的相互冲减
	 * @return
	 */
	private double getCurrentDutyPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("开始计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的其他已经算出的理赔金总额");
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("9");//责任下多个给付责任共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		
		
		if(!tLMRiskSortDB.getInfo())
        {
			tLMRiskSortDB=null;
            return t_Sum_Return;
        }
		else
		{
			logger.debug("mLLClaimDetailSet.size():"+mLLClaimDetailSet.size());
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{
				//首先是同一张保单
				if(mLLClaimDetailSet.get(i).getPolNo().equals(mLLClaimDetailSchema.getPolNo()))
				{
					//针对同一个geidutykind,不同的getdutykind的多个给付责任共享保额的情况or同一个geidutycode,不同的getdutycode的多个给付责任共享保额的情况
					//或者针对康福等多个连带被保险人共用同一个账户的情况,根据CustomerNo
					if((!mLLClaimDetailSet.get(i).getGetDutyCode().equals(mLLClaimDetailSchema.getGetDutyCode()))||
							(!mLLClaimDetailSet.get(i).getGetDutyKind().equals(mLLClaimDetailSchema.getGetDutyKind()))||
								(!mLLClaimDetailSet.get(i).getCustomerNo().equals(mLLClaimDetailSchema.getCustomerNo()))){
						
						t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
					}
					else
					{
						logger.debug("自己无所谓累计！");
					}
				}
			}
		}
		
		logger.debug("计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的其他已经算出的理赔金总额:"+t_Sum_Return);

		return t_Sum_Return;
	}
	
	
	
	/**
	 * 针对连被保险人的算法
	 * 得到共享保额的给付责任中已经计算出的赔付金
	 * 如果不是共享保额的责任,或是第一个给付责任,则返回0
	 * 适用于责任下多个给付责任分類共享不同的保额,理算时给付责任的相互冲减
	 * @return
	 */
	private double getCurrentRelateDutyPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("开始计算案件:"+mLLClaimDetailSchema.getClmNo()+",出险人:"+mLLClaimDetailSchema.getCustomerNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的和本次循环理算的给付责任共享保额的其他已经算出给付责任的理赔金总额");
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("10");//适用于责任下多个给付责任分類共享不同的保额
		//tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		
		LMRiskSortSet tLMRiskSortSet = new LMRiskSortSet();
		tLMRiskSortSet=tLMRiskSortDB.query();
		if(tLMRiskSortSet.size()>0)
        {
			//分类数组,分别记录给付责任编码和分类数组
			String[][] classifieArray = new String[tLMRiskSortSet.size()][2];
			
			for(int i=0;i<tLMRiskSortSet.size();i++)
			{
				classifieArray[i][0] =  tLMRiskSortSet.get(i+1).getRiskSortValue();
				classifieArray[i][1] =  tLMRiskSortSet.get(i+1).getRemark();
				
				logger.debug("classifieArray["+i+"][0]:"+classifieArray[i][0]+",classifieArray["+i+"][1]:"+classifieArray[i][1]);
			}
			
			logger.debug("mLLClaimDetailSet.size():"+mLLClaimDetailSet.size());
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{		
				//首先要是同一人的同一险种
				if((mLLClaimDetailSet.get(i).getPolNo().equals(mLLClaimDetailSchema.getPolNo()))&&(mLLClaimDetailSet.get(i).getCustomerNo().equals(mLLClaimDetailSchema.getCustomerNo()))){
					
					//针对同一个geidutykind,不同的getdutykind的多个给付责任共享保额的情况or同一个getdutycode,不同的getdutycode的多个给付责任共享保额的情况,不同的连带被保险人客户号
					if((!mLLClaimDetailSet.get(i).getGetDutyCode().equals(mLLClaimDetailSchema.getGetDutyCode()))||
							(!mLLClaimDetailSet.get(i).getGetDutyKind().equals(mLLClaimDetailSchema.getGetDutyKind()))
							){
						
		
						if(whetherClassifiDuty(mLLClaimDetailSchema.getGetDutyCode(),mLLClaimDetailSet.get(i).getGetDutyCode(),classifieArray))
						{
							t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
						}
					}
					else
					{
						logger.debug("自己无所有共享！");
					}
						 
				}
			}
			
			classifieArray=null;
        }
		
		logger.debug("计算案件:"+mLLClaimDetailSchema.getClmNo()+",出险人:"+mLLClaimDetailSchema.getCustomerNo()+",险种:"+mLLClaimDetailSchema.getPolNo()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的和本次循环理算的给付责任共享保额的其他已经算出给付责任的理赔金总额:"+t_Sum_Return);

		return t_Sum_Return;
	}
	
	
	  /**
	 * 得到每个给付责任的累计赔付金额,针对连带保险人产品
	 * 
	 * @return
	 */
	private double getGetDutyRelateSumPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();
		
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("10");//责任下多个给付责任分类共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
	
		
		if(!tLMRiskSortDB.getInfo())
        {
			// 得到每个给付责任的累计赔付金额  正常赔付
			tSql =  " select nvl(sum(Pay),0)  from "
				+" ("
			    +"  select nvl(sum(b.RealPay),0) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and b.GetDutyCode='"+mLLClaimDetailSchema.getGetDutyCode()+"'"
				+ " and b.customerno='"+mLLClaimDetailSchema.getCustomerNo()+"'"
				+ " and a.clmno!='"+this.mClmNo+"'"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
//				+"  union"
//				+"  select nvl(sum(d.Pay),0)  Pay"
//				+"  from ljagetclaim d where d.feeoperationtype='B'"
//				+ " and d.GetDutyCode='"+mLLClaimDetailSchema.getGetDutyCode()+"'"
//				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
//				+"  and d.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+"  )";
			
			logger.debug("--计算要素GetDutySumPay[保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+mLLClaimDetailSchema.getGetDutyCode()+"累计支付]:" + tSql);
			
        }
		else
		{
			//能够查询到记录,表示该险种是多个给付责任分类共享保额,所以查询到应该是共享统一保额的所有给负责人的既往赔付金额
			tSql =  " select nvl(sum(Pay),0)  from "
				+ " ("
			    +"  select nvl(sum(b.RealPay),0) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.customerno='"+mLLClaimDetailSchema.getCustomerNo()+"'"
				+ " and b.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+ " and a.clmno!='"+this.mClmNo+"'"
				+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"' " 
				+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"' and remark='"+tLMRiskSortDB.getRemark()+"')"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
//				+"  union"
//				+"  select nvl(sum(d.Pay),0)  Pay"
//				+"  from ljagetclaim d where d.feeoperationtype='B'"
//				+ " and d.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"' " 
//				+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"' and remark='"+tLMRiskSortDB.getRemark()+"')"
//				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
//				+"  and d.PolNo ='" + mLLClaimDetailSchema.getPolNo() + "'"
				+"  )";
			
			logger.debug("--计算要素GetDutySumPay[保单"+mLLClaimDetailSchema.getPolNo()+",险种:"+tLMRiskSortDB.getRiskCode()+"在LMRiskSort描述的Remark等于"+tLMRiskSortDB.getRemark()+"的所有给付责任的累计支付]:" + tSql);
			
		}



		String tPayFranchise = tExeSQL.getOneValue(tSql);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+
					mLLClaimDetailSchema.getGetDutyCode()+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}

		return t_Sum_Return;
	}
	
	
	/**
	 * 得到共享保额的给付责任中已经计算出的赔付金
	 * 如果不是共享保额的责任,或是第一个给付责任,则返回0
	 * 适用于责任下多个给付责任分類共享不同的保额,理算时给付责任的相互冲减
	 * @return
	 */
	private double getCurrentClassifiDutyPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("开始计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的和本次循环理算的给付责任共享保额的其他已经算出给付责任的理赔金总额");
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("10");//适用于责任下多个给付责任分類共享不同的保额
		//tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		
		LMRiskSortSet tLMRiskSortSet = new LMRiskSortSet();
		tLMRiskSortSet=tLMRiskSortDB.query();
		if(tLMRiskSortSet.size()>0)
        {
			//分类数组,分别记录给付责任编码和分类数组
			String[][] classifieArray = new String[tLMRiskSortSet.size()][2];
			
			for(int i=0;i<tLMRiskSortSet.size();i++)
			{
				classifieArray[i][0] =  tLMRiskSortSet.get(i+1).getRiskSortValue();
				classifieArray[i][1] =  tLMRiskSortSet.get(i+1).getRemark();
				
				logger.debug("classifieArray["+i+"][0]:"+classifieArray[i][0]+",classifieArray["+i+"][1]:"+classifieArray[i][1]);
			}
			
			logger.debug("mLLClaimDetailSet.size():"+mLLClaimDetailSet.size());
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{		
				//首先要是同一险种
				if(mLLClaimDetailSet.get(i).getPolNo().equals(mLLClaimDetailSchema.getPolNo())){
					
					//针对同一个geidutykind,不同的getdutykind的多个给付责任共享保额的情况or同一个geidutycode,不同的getdutycode的多个给付责任共享保额的情况
					if((!mLLClaimDetailSet.get(i).getGetDutyCode().equals(mLLClaimDetailSchema.getGetDutyCode()))||
							(!mLLClaimDetailSet.get(i).getGetDutyKind().equals(mLLClaimDetailSchema.getGetDutyKind()))){
						
		
						if(whetherClassifiDuty(mLLClaimDetailSchema.getGetDutyCode(),mLLClaimDetailSet.get(i).getGetDutyCode(),classifieArray))
						{
							t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
						}
					}
					else
					{
						logger.debug("自己无所有共享！");
					}
						 
				}
			}
			
			classifieArray=null;
        }
		
		logger.debug("计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getPolNo()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的和本次循环理算的给付责任共享保额的其他已经算出给付责任的理赔金总额:"+t_Sum_Return);

		return t_Sum_Return;
	}
	
	

	/**
	 * 判断两个责任是否属于共享同一类保额
	 * 
	 * @return
	 */
	private boolean whetherClassifiDuty(String getdutycode1,String getdutycode2,String[][] mArray)
	{
		
		int k1=1000;
		int k2=1000;
		
		for(int i=0;i<mArray.length;i++)
		{
			//从数组中寻找getdutycode1所在的位置
			if(mArray[i][0].equals(getdutycode1))
			{
				k1=i;
				break;
			}
		}
		
		for(int i=0;i<mArray.length;i++)
		{
			//从数组中寻找getdutycode2所在的位置
			if(mArray[i][0].equals(getdutycode2))
			{
				k2=i;
				break;
			}
		}
		
		if(k1!=1000&&k2!=1000)
		{
			if(mArray[k1][1].equals(mArray[k2][1]))
			{
				return true;
			}
		}

		
		return false;
	}
	
	
	/**
	 * 判断两个险种是否相同
	 * 
	 * @return
	 */
	private boolean whetherClassifiRisk(String riskcode,String[] mArray)
	{
		
		int k1=1000;
		
		for(int i=0;i<mArray.length;i++)
		{
			//从数组中寻找getdutycode1所在的位置
			if(mArray[i].equals(riskcode))
			{
				k1=i;
				break;
			}
		}
		
		
		if(k1!=1000)
		{
			return true;
		}

		
		return false;
	}
	

    /**
	 * zhangzheng 2008-09-27
	 * 得到社保赔付比例
	 * @return
	 */
    private double getSocialInsuRate(String tRiskCode,String tSocialInsuFlag)
    {
      LMRiskSocialInsuDB tLMRiskSocialInsuDB=new LMRiskSocialInsuDB();
      tLMRiskSocialInsuDB.setRiskCode(tRiskCode);
      
      if(tSocialInsuFlag==null||tSocialInsuFlag.equals("")||tSocialInsuFlag.equals("0"))
      {
    	  tLMRiskSocialInsuDB.setSocialInsuFlag("0");
      }
      else
      {
    	  tLMRiskSocialInsuDB.setSocialInsuFlag(tSocialInsuFlag);
      }
      
      if(tLMRiskSocialInsuDB.getInfo())
      {
        return tLMRiskSocialInsuDB.getSocialInsuRate();
      }
      return 1.00;
    }
    
    
    /**
	 * zhangzheng 2009-02-18
	 * 得到门诊,住院,特种费用的费用项目编码
	 * @return
	 */
    private String getDutyItemCode(String tClmNo,String tDutyFeeStaNo)
    {
       String tItemCode="000";
            
       if(!(tDutyFeeStaNo==null||tDutyFeeStaNo.equals(""))){
    	   
           String sql=" select k from("
  	         +" select feeitemcode k from LLCaseReceipt where clmno='"+tClmNo+"' and feedetailno='"+tDutyFeeStaNo+"'"
  	         +" union "
  	         +" select factorcode k from llotherfactor where clmno='"+tClmNo+"' and SerialNo='"+tDutyFeeStaNo+"'"
  	         +" )";
     
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的费用项目编码sql:"+sql);
	     
	       ExeSQL tExeSQL = new ExeSQL();
	
	       tItemCode=tExeSQL.getOneValue(sql);
	     
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的费用项目编码:"+tItemCode);
       }

       
       return tItemCode;
    }
    
    /**
	 * zhangzheng 2009-02-18
	 * 得到门诊,住院,特种费用的账单结束日期
	 * @return
	 */
    private String getFeereceiEndDate(String tClmNo,String tDutyFeeStaNo)
    {
       String date="3000-01-01";
            
       if(!(tDutyFeeStaNo==null||tDutyFeeStaNo.equals(""))){
    	   
           String sql=" select k from("
  	         +" select enddate k from LLCaseReceipt where clmno='"+tClmNo+"' and feedetailno='"+tDutyFeeStaNo+"'"
  	         +" union "
  	         +" select enddate k from llotherfactor where clmno='"+tClmNo+"' and SerialNo='"+tDutyFeeStaNo+"'"
  	         +" )";
     
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的结束日期sql:"+sql);
	     
	       ExeSQL tExeSQL = new ExeSQL();
	
	       date=tExeSQL.getOneValue(sql).substring(0,10);
	     
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的结束日期:"+date);
       }

       
       return date;
    }

    /**
     * 得到基本保费:每期保费 * 期数
     * pPayPlanType : 0--全部保费,基本保费 + 健康职业加费
     *                1--基本保费
     *                2--01,02,03,04,健康职业加费
     *
     *                3--01,03健康加费
     *                4--02,04职业加费
     * @return
     */
    private double getBasePrem(String pPayPlanType)
    {

        double t_Sum_Return = 0;
        String tSql = "";
        String tWhereSql = "";

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 缴费状态
         * pPayPlanType : 0--全部保费,基本保费 + 健康职业加费
         *                1--基本保费
         *                2--01,02,03,04,健康职业加费
         *
         *                3--01,03健康加费
         *                4--02,04职业加费
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug("-------------------计算基本保费开始（每期保费 * 期数）-------------------");

        if (pPayPlanType.equals("0"))
        {
            tWhereSql = "";
        }
        else if (pPayPlanType.equals("1"))
        {
            tWhereSql = " and PayPlanType in ('0')";
        }
        else if (pPayPlanType.equals("2"))
        {
            tWhereSql = " and PayPlanType in ('01','02','03','04')";
        }
        else if (pPayPlanType.equals("3"))
        {
            tWhereSql = " and PayPlanType in ('01','03')";
        }
        else if (pPayPlanType.equals("4"))
        {
            tWhereSql = " and PayPlanType in ('02','04')";
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 得到P表的总保费
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LPPremSet tLPPremSet = new LPPremSet();
        String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());
        if (!tPosFlag.endsWith("0"))
        {
            String tPosEdorNo = mLLClaimDetailSchema.getPosEdorNo();

            tSql = "select * from LPPrem where 1=1 "
                +" and EdorNo = '"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                +" and ContNo = '"+mLLClaimDetailSchema.getContNo()+"'"
                +" and PolNo = '"+mLLClaimDetailSchema.getPolNo()+"'"
                ;
            tSql = tSql + tWhereSql;


            LPPremDB tLPPremDB = new LPPremDB();
            tLPPremSet = tLPPremDB.executeQuery(tSql);

            if (tLPPremDB.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tLPPremDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getTotalPrem";
                tError.errorMessage = "计算P表基本保费["+pPayPlanType+"]发生错误!";
                this.mErrors.addOneError(tError);
                return 0;
            }

            for (int i=1;i<=tLPPremSet.size();i++)
            {
                
    			LPPremSchema tLPPremSchema = tLPPremSet.get(i);

				String tPayIntv = String.valueOf(tLPPremSchema.getPayIntv());
				double tDPeriodTimes = mLLClaimPubFunBL.getLCPremPeriodTimes(
						tLPPremSchema.getPayStartDate(), tLPPremSchema
								.getPayEndDate(), tPayIntv, mInsDate);

				double t_Sum_Prem = tLPPremSchema.getPrem(); // 实际保费
				t_Sum_Prem = t_Sum_Prem * tDPeriodTimes;
				t_Sum_Return = t_Sum_Return + t_Sum_Prem;
            }
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 如果P表无数据,则得到C表的总保费
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tLPPremSet.size()> 0)
        {
            logger.debug("P表基本保费:["+t_Sum_Return+"]:"+tSql);
            return t_Sum_Return;
        }
        else
        {

            tSql = "select * from LCPrem where 1=1 "
                +" and ContNo = '"+mLLClaimDetailSchema.getContNo()+"'"
                +" and PolNo = '"+mLLClaimDetailSchema.getPolNo()+"'"
                ;
            tSql = tSql + tWhereSql;

            LCPremDB tLCPremDB = new LCPremDB();
            LCPremSet tLCPremSet = tLCPremDB.executeQuery(tSql);

            if (tLCPremDB.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tLCPremDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getTotalPrem";
                tError.errorMessage = "计算C表基本保费["+pPayPlanType+"]发生错误!";
                this.mErrors.addOneError(tError);
                return 0;
            }

            for (int i=1;i<=tLCPremSet.size();i++)
            {          	
        		LCPremSchema tLCPremSchema = tLCPremSet.get(i);

				String tPayIntv = String.valueOf(tLCPremSchema.getPayIntv());
				double tDPeriodTimes = mLLClaimPubFunBL.getLCPremPeriodTimes(
						tLCPremSchema.getPayStartDate(), tLCPremSchema
								.getPayEndDate(), tPayIntv, mInsDate);

				double t_Sum_Prem = tLCPremSchema.getPrem(); // 实际保费
				t_Sum_Prem = t_Sum_Prem * tDPeriodTimes;
				t_Sum_Return = t_Sum_Return + t_Sum_Prem;
            }
        }
        logger.debug("C表基本保费:["+t_Sum_Return+"]:"+tSql);
        logger.debug("-------------------计算基本保费结束（每期保费 * 期数）-------------------");
        return t_Sum_Return;
    }



    /**
     * 得到总保费（包括健康加费和职业加费及出险时点的保全补退费）
     * @return
     */
    private double getTotalPrem()
    {

        double t_Sum_Return = 0;

        logger.debug("----------------------------计算总保费开始（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");

        t_Sum_Return = getBasePrem("0");

        logger.debug("要素TotalPrem:["+t_Sum_Return+"]");
        logger.debug("----------------------------计算总保费结束（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
        return t_Sum_Return;
    }



    /*
     * 得到标准保费（不包括健康加费和职业加费）
     * @return
     */
    private double getAllPrem()
    {

        double t_Sum_Return = 0;

        logger.debug("----------------------------计算总保费开始（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");

        t_Sum_Return = getBasePrem("0");

        logger.debug("要素SumPrem[标准保费]:["+t_Sum_Return+"]");
        logger.debug("----------------------------计算总保费结束（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
        return t_Sum_Return;

    }

    /**
     * 得到住院天数
     * @return int
     */
    private int getDaysInHos(String tPolNo,String tDutyCode,String tDutyFeeType,String tDutyFeeStaNo){
        int tDaysInHos=0;
        String tdayconut="";
        ExeSQL tExeSQL = new ExeSQL();
        if (tDutyFeeType!=null&&(tDutyFeeType=="B"||tDutyFeeType.equals("B")))
        {
            String dSql =
                    "select sum(daycount) from llclaimdutyfee where clmno='" +
                    mClmNo + "' and polno='" + tPolNo + "' and dutycode='" +
                    tDutyCode + "' and dutyfeetype='B' and DutyFeeStaNo='"+tDutyFeeStaNo+"' ";
            tdayconut = tExeSQL.getOneValue(dSql);
            if (tdayconut == null || tdayconut.equals("")) {
                tdayconut = "0";
            }
            tDaysInHos = Integer.parseInt(tdayconut);
            return tDaysInHos;
        }else
            return tDaysInHos;
    }


    /**
     * 得到门诊帐单费用 （110）
     * @return double
     */
    private double getDoorPostFee110(String tCustomerNo,String tPolNo){
        String tSql = "";
        String tReturn  = "";
        String tReturn2 = "";
        double t_Sum_Return = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
//        tSql = "  select sum(AdjSum) from LLCaseReceipt where clmno = '"+mClmNo+"' and FeeItemType = 'A'"
//             + "  and CustomerNo = '"+tCustomerNo+"'";
        /*jixf chg 061019  这样如果llclaimdetail有多个责任的理赔会重复
       tSql = " select sum(a.AdjSum) from LLCaseReceipt a , LLClaimDetail c , llclaim b where  trim(a.FeeItemType) = 'A'"
            + " and a.CustomerNo = '"+tCustomerNo+"'"
            + " and a.caseno = c.caseno and c.polno = '"+tPolNo+"'"
            + " and c.getdutycode like '110%'"
            + " and a.caseno = b.caseno"
            + " and trim(b.clmstate)  = '60'";
*/
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a , LLClaimPolicy c , llclaim b where  trim(a.FeeItemType) = 'A'"
            + " and a.CustomerNo = '"+tCustomerNo+"'"
            + " and a.caseno = c.caseno and c.polno = '"+tPolNo+"'"
            + " and c.riskcode='110' "
            + " and a.caseno = b.caseno"
            + " and trim(b.clmstate)  = '60'";

        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a  where "
             + " trim(a.FeeItemType) = 'A'"
             + " and a.CustomerNo = '"+tCustomerNo+"'"
             + " and a.caseno  = '"+mClmNo+"'";
        tReturn2 = StrTool.cTrim(tExeSQL.getOneValue(tSql));
       if (tExeSQL.mErrors.needDealError()) {
           this.mErrors.copyAllErrors(tExeSQL.mErrors);
           CError tError = new CError();
           tError.moduleName = "LLClaimCalAutoBL";
           tError.functionName = "getOamnt";
           tError.errorMessage = "得到保全基本保额发生错误!";
           this.mErrors.addOneError(tError);
       }
       if (tReturn2 != null && tReturn2.length() > 0 && !tReturn2.equals("")) {
           t_Sum_Return = Double.parseDouble(tReturn2)+t_Sum_Return;
        }
        return t_Sum_Return;
    }

    /**
     * 得到住院帐单费用 （110）
     * @return double
     */
    private double getInHospFee110(String tCustomerNo,String tPolNo){
        String tSql = "";
        String tReturn  = "";
        String tReturn2 = "";
        double t_Sum_Return = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
//        tSql = "  select sum(AdjSum) from LLCaseReceipt where clmno = '"+mClmNo+"' and FeeItemType = 'B'"
//             + " and CustomerNo = '"+tCustomerNo+"'";
                /*jixf chg 061019  这样如果llclaimdetail有多个责任的理赔会重复
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a , LLClaimDetail c, llclaim b where  trim(a.FeeItemType) = 'B'"
             + " and a.CustomerNo = '"+tCustomerNo+"'"
             + " and a.caseno = c.caseno and c.polno = '"+tPolNo+"'"
             + " and c.getdutycode like '110%'"
             + " and a.caseno = b.caseno"
             + " and trim(b.clmstate)  = '60'";
*/
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a , LLClaimPolicy c, llclaim b where  trim(a.FeeItemType) = 'B'"
             + " and a.CustomerNo = '"+tCustomerNo+"'"
             + " and a.caseno = c.caseno and c.polno = '"+tPolNo+"'"
             + " and c.riskcode='110'"
             + " and a.caseno = b.caseno"
             + " and trim(b.clmstate)  = '60'";

        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        tSql =
                " select sum(a.AdjSum) from LLCaseReceipt a  where "
                + " trim(a.FeeItemType) = 'B'"
                + " and a.CustomerNo = '" + tCustomerNo + "'"
                + " and a.caseno  = '" + mClmNo + "'";
        tReturn2 = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn2 != null && tReturn2.length() > 0 && !tReturn2.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn2)+t_Sum_Return;
        }
        return t_Sum_Return;
    }

    /**
     * 得到门诊帐单费用 （106）
     * @return double
     */
    private double getDoorPostFee106(String tCustomerNo,String tPolNo){
        String tSql = "";
        String tReturn = "";
        String tReturn2 = "";
        double t_Sum_Return = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
//        tSql = "  select sum(AdjSum) from LLCaseReceipt where clmno = '"+mClmNo+"' and FeeItemType = 'A'"
//             + "  and CustomerNo = '"+tCustomerNo+"'";
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a , LLClaimDetail c, llclaim b where  trim(a.FeeItemType) = 'A'"
             + " and a.CustomerNo = '"+tCustomerNo+"'"
             + " and a.caseno = c.caseno and c.polno = '"+tPolNo+"'"
             + " and c.getdutycode like '106%'"
             + " and a.caseno = b.caseno"
             + " and trim(b.clmstate)  = '60'";

        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a  where "
             + " trim(a.FeeItemType) = 'A'"
             + " and a.CustomerNo = '"+tCustomerNo+"'"
             + " and a.caseno  = '"+mClmNo+"'";
        tReturn2 = StrTool.cTrim(tExeSQL.getOneValue(tSql));
       if (tExeSQL.mErrors.needDealError()) {
           this.mErrors.copyAllErrors(tExeSQL.mErrors);
           CError tError = new CError();
           tError.moduleName = "LLClaimCalAutoBL";
           tError.functionName = "getOamnt";
           tError.errorMessage = "得到保全基本保额发生错误!";
           this.mErrors.addOneError(tError);
       }
       if (tReturn2 != null && tReturn2.length() > 0 && !tReturn2.equals("")) {
           t_Sum_Return = Double.parseDouble(tReturn2)+t_Sum_Return;
        }
        return t_Sum_Return;
    }

    /**
     * 得到住院帐单费用 （106）
     * @return double
     */
    private double getInHospFee106(String tCustomerNo,String tPolNo){
        String tSql = "";
        String tReturn = "";
        String tReturn2 = "";
        double t_Sum_Return = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
//        tSql = "  select sum(AdjSum) from LLCaseReceipt where clmno = '"+mClmNo+"' and FeeItemType = 'B'"
//             + " and CustomerNo = '"+tCustomerNo+"'";
        tSql = " select sum(a.AdjSum) from LLCaseReceipt a , LLClaimDetail c, llclaim b where  trim(a.FeeItemType) = 'B'"
             + " and a.CustomerNo = '"+tCustomerNo+"'"
             + " and a.caseno = c.caseno and c.polno = '"+tPolNo+"'"
             + " and c.getdutycode like '106%'"
             + " and a.caseno = b.caseno"
             + " and trim(b.clmstate)  = '60'";
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        tSql =  " select sum(a.AdjSum) from LLCaseReceipt a  where "
                + " trim(a.FeeItemType) = 'B'"
                + " and a.CustomerNo = '" + tCustomerNo + "'"
                + " and a.caseno  = '" + mClmNo + "'";
        tReturn2 = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn2 != null && tReturn2.length() > 0 && !tReturn2.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn2) + t_Sum_Return;
        }

        return t_Sum_Return;
    }

    /**
     * 得到住院起付线
     * @param tCustomerNo String
     * @return double
     */
    private double getHospLineAmnt(String tCustomerNo){
        String tSql = "";
        String tReturn = "";
        double t_Sum_Return = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
        tSql = "  select sum(HospLineAmnt) from LLCaseReceipt where clmno = '"+mClmNo+"'"
             + " and CustomerNo = '"+tCustomerNo+"'";
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        return t_Sum_Return;
    }

    /**
     * 得到住院天数
     * @return double
     */
    private double getRealHospitalDays() {
        String tSql = "";
        String tReturn = "";
        double t_Sum_Return = 0;
        ExeSQL tExeSQL = new ExeSQL();
        tSql = "  select sum(DayCount) from LLCaseReceipt where clmno = '" + mClmNo +
               "' and FeeItemType = 'B'";
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        return t_Sum_Return;
    }

    /**
     * 得到生存领取的起领日期,取自出险时点
     * @return
     */
    private String getGetStartDate()
    {
        String tReturn = "";
        ExeSQL tExeSQL = new ExeSQL();
        String tSql = "";

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0") || mLLClaimDetailSchema.getPosFlag().endsWith("1"))
        {
            tSql = "select nvl(GetStartDate,'1900-01-01') from LCGet where 1=1 "
                +" and PolNo ='"+tPolNo+"'"
                +" and DutyCode ='"+mLCGetSchema.getDutyCode()+"'"
                +" and GetDutyCode in "
                    +" (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '"+mLCGetSchema.getDutyCode()+"'"
                    +" and a.GetDutyCode = b.GetDutyCode "
                    +" and a.GetDutyCode not in ('"+mLCGetSchema.getGetDutyCode()+"')"
                    +" and b.GetType2 = '1')"    //养老金
                +" and LiveGetType ='0'";        //生存意外给付标志,为0生存领取
        }
        else if (mLLClaimDetailSchema.getPosFlag().endsWith("2"))
        {
            tSql = "select nvl(GetStartDate,'1900-01-01') from LPGet where 1=1 "
                +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                +" and PolNo ='"+tNBPolNo+"'"
                +" and DutyCode ='"+mLCGetSchema.getDutyCode()+"'"
                +" and GetDutyCode in "
                    +" (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '"+mLCGetSchema.getDutyCode()+"'"
                    +" and a.GetDutyCode not in ('"+mLCGetSchema.getGetDutyCode()+"')"
                    +" and b.GetType2 = '1')"    //养老金
                +" and LiveGetType ='0'";        //生存意外给付标志,为0生存领取
        }

        logger.debug("要素GetStartDate[生存领取的起领日期]:"+tSql);
        tReturn = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全生存领取的起领日期发生错误!";
            this.mErrors.addOneError(tError);
        }


        if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
        {
            tReturn = tReturn.substring(0,10);
        }

        return tReturn;

    }



    /**
     * 得到基本保额,取自出险时点,需要考虑保全
     * @return
     */
    private double getAmnt()
    {
        String tReturn = "";
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0") || mLLClaimDetailSchema.getPosFlag().endsWith("1"))
        {
            t_Sum_Return = mLCGetSchema.getStandMoney();
        }
        else if (mLLClaimDetailSchema.getPosFlag().endsWith("2"))
        {
            ExeSQL tExeSQL = new ExeSQL();
            String tSql = "";
//            tSql = "select nvl(Amnt,0) from LPDuty where 1=1 "
//                +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
//                +" and PolNo ='"+mLCDutySchema.getPolNo()+"'"
//                +" and DutyCode ='"+mLCDutySchema.getDutyCode()+"'"
//    			+" and GetDutyCode ='" + mLCGetSchema.getGetDutyCode()
//    			+ "'";
            
			tSql = "select nvl(StandMoney,0) from LPGet where 1=1 "
				+ " and EdorNo ='" + mLLClaimDetailSchema.getPosEdorNo()
				+ "'" + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
				+ " and DutyCode ='" + mLCGetSchema.getDutyCode() + "'"
				+ " and GetDutyCode ='" + mLCGetSchema.getGetDutyCode()
				+ "'";

            logger.debug("要素Amnt[基本保额]:"+tSql);
            tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "得到保全基本保额发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                t_Sum_Return = Double.parseDouble(tReturn);
            }

        }

        return t_Sum_Return;

    }


    /**
	 * 得到基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
	 * 
	 * @return
	 */
	private double getMaxAmnt() {
		String tReturn = "";
		
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("9");//责任下多个给付责任分类共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		if(tLMRiskSortDB.getInfo())
        {
			String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
			String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
			String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());
			
			String tSql = "";
			ExeSQL tExeSQL = new ExeSQL();

			/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
			if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
				t_Sum_Return = mLCGetSchema.getStandMoney();
				
				tSql =  "  select max(StandMoney)  from Lcget b where 1=1 "
					+ " and b.PolNo ='" + tPolNo + "'"
					+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"' " 
					+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"')"
					+ " and b.DutyCode ='" + mLCGetSchema.getDutyCode() + "'";
				
			} 
			else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {

				tSql = "select max(StandMoney) from LPGet where 1=1 "
						+ " and EdorNo ='" + mLLClaimDetailSchema.getPosEdorNo()
						+ "'" + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
						+ " and DutyCode ='" + mLCGetSchema.getDutyCode() + "'"
						+ " and GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"'" + " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"')"
						+ "'";
			}
			
			logger.debug("要素MaxAmnt[基本保额]:" + tSql);
			tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
			
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "得到险种"+tPolNo+"的共享给付责任的最大保额发生错误!");
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				t_Sum_Return = Arith.round(Double.parseDouble(tReturn), 2);
			}
		}
		else
		{
			tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
			tLMRiskSortDB.setRiskSortType("10");//责任下多个给付责任分类共享保额相互冲减
			tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
			
			if(tLMRiskSortDB.getInfo())
	        {
				String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
				String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
				String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());
				
				String tSql = "";
				ExeSQL tExeSQL = new ExeSQL();

				/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
				if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
					t_Sum_Return = mLCGetSchema.getStandMoney();
					
					tSql =  "  select max(StandMoney)  from Lcget b where 1=1 "
						+ " and b.PolNo ='" + tPolNo + "'"
						+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"' " 
						+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"' and remark='"+tLMRiskSortDB.getRemark()+"')"
						+ " and b.DutyCode ='" + mLCGetSchema.getDutyCode() + "'";
				
				} 
				else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {

					tSql = "select max(StandMoney) from LPGet where 1=1 "
							+ " and EdorNo ='" + mLLClaimDetailSchema.getPosEdorNo()
							+ "'" + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
							+ " and DutyCode ='" + mLCGetSchema.getDutyCode() + "'"
							+ " and GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+tLMRiskSortDB.getRiskCode()+"'" 
							+ " and a.risksorttype ='"+tLMRiskSortDB.getRiskSortType()+"' and remark='"+tLMRiskSortDB.getRemark()+"')"
							+ "'";
				}
				
				logger.debug("要素MaxAmnt[基本保额]:" + tSql);
				tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
				
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "得到险种"+tPolNo+"的共享给付责任的最大保额发生错误!");
				}
				if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
					t_Sum_Return = Arith.round(Double.parseDouble(tReturn), 2);
				}
	        }		
		}
		
		return t_Sum_Return;

	}


    /**
     * 得到保险年期,取自出险时点,需要考虑保全
     * @return
     */
    private int getYears()
    {

        String tReturn = "";
        int    tIReturn = 0;
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0") || mLLClaimDetailSchema.getPosFlag().endsWith("1"))
        {
            tIReturn = mLCDutySchema.getYears();
        }
        else if (mLLClaimDetailSchema.getPosFlag().endsWith("2"))
        {
            String tSql = "select nvl(Years,0) from LPDuty where 1=1 "
                +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                +" and PolNo  ='"+tNBPolNo+"'"
                +" and DutyCode ='"+mLCDutySchema.getDutyCode()+"'";

            logger.debug("要素Years[保险年期]:"+tSql);
            ExeSQL tExeSQL = new ExeSQL();
            tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "计算保全保险年期发生错误!";
                this.mErrors.addOneError(tError);
            }

            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                tIReturn = Integer.parseInt(tReturn);
            }
        }

        return tIReturn;
    }



    /**
     * 得到已交费年期,取自出险时点,需要考虑保全
     * @return
     */
    private String getPaytoDate()
    {

        String tReturn = "";
        int    tIReturn = 0 ;
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0"))
        {
            tReturn = mLCPolSchema.getPaytoDate();
        }
        else
        {
            String tSql = "select nvl(PaytoDate,'1900-01-01') from LPPol where 1=1 "
                +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                +" and PolNo  ='"+tNBPolNo+"'";


            logger.debug("要素PaytoDate[已交费年期]:"+tSql);
            ExeSQL tExeSQL = new ExeSQL();
            tReturn = tExeSQL.getOneValue(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "计算保全已交费年期发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                tReturn = tReturn.substring(0,10);
            }
        }

        return tReturn;
    }


    /**
     * 得到份数 ,取自出险时点,需要考虑保全
     * @return
     */
    private double getMult()
    {

        String tReturn = "";
        int    tIReturn = 0 ;
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 0未做保全,1已做保全
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0") || mLLClaimDetailSchema.getPosFlag().endsWith("1"))
        {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.0 加保金额等于0,取Duty上的数据
             *      加保金额大于0,取LCPol上的数据
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (mLLClaimDetailSchema.getAddAmnt()==0)
            {
                t_Sum_Return = mLCDutySchema.getMult();
            }
            else
            {
                t_Sum_Return = mLCPolSchema.getMult();
            }
        }
        else if (mLLClaimDetailSchema.getPosFlag().endsWith("2"))
        {
            String tSql = "";
            if (mLLClaimDetailSchema.getAddAmnt()==0)
            {
                tSql = "select nvl(Mult,0) from LPDuty where 1=1 "
                    +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                    +" and PolNo  ='"+tNBPolNo+"'"
                    +" and DutyCode  ='"+mLCDutySchema.getDutyCode()+"'"
                    ;
            }
            else
            {
                tSql = "select nvl(Mult,0) from LPPol where 1=1 "
                    +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                    +" and PolNo  ='"+tNBPolNo+"'"
                    ;

            }


            logger.debug("要素Mult[份数]:"+tSql);
            ExeSQL tExeSQL = new ExeSQL();
            tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "计算保全份数发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                t_Sum_Return = Double.parseDouble(tReturn);
            }

        }
        return t_Sum_Return;
    }



    /**
     * 得到每期保费,取自出险时点,需要考虑保全
     * @return
     */
    private double getPeriodPrem()
    {

        String tReturn = "";
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tSql="";
        ExeSQL tExeSQL = new ExeSQL();

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0"))
        {
            tSql = "select nvl(StandPrem,0) from LCPrem where 1=1 "
                +" and PolNo  ='"+mLCDutySchema.getPolNo()+"'"
                +" and DutyCode  ='"+mLCDutySchema.getDutyCode()+"'"
                +" and substr(PayPlanCode,1,6) not in ('000000')";

            tReturn = tExeSQL.getOneValue(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "计算每期保费发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                t_Sum_Return = Double.parseDouble(tReturn);
            }
        }
        else
        {
            tSql = "select nvl(Prem,0) from LPPrem where 1=1 "
                +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
                +" and PolNo  ='"+tNBPolNo+"'"
                +" and DutyCode  ='"+mLCDutySchema.getDutyCode()+"'"
                +" and substr(PayPlanCode,1,6) not in ('000000')";


            tReturn = tExeSQL.getOneValue(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "计算保全每期保费发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                t_Sum_Return = Double.parseDouble(tReturn);
            }
        }

        logger.debug("要素PeriodPrem[每期保费]:"+tSql);
        return t_Sum_Return;
    }



    /**
     * 被保人投保年龄
     * @return
     */
    private int getInsuredAppAge()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 被保人被保人投保年龄 = 出险时经过的整保单年度 - 出生日期
         *  出险时经过的整保单年度 = 意外事故日期 - 保单生效对应日
         *  例如:保单的生效对应日为2005.05.04,出险日期为2008.05.06.
         *  则 (2008-2005) = 3 年,用2008.05.04 - 出生日期 = 被保人被保人投保年龄
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        int tYear = PubFun.calInterval(mLCPolSchema.getCValiDate(),this.mAccDate, "Y");
//        String tNewDate = PubFun.calDate(mLCPolSchema.getCValiDate(),tYear,"Y",mLCPolSchema.getCValiDate());
        String tInsuredBirthday = mLCPolSchema.getInsuredBirthday();
        int tNewAge = PubFun.calInterval(tInsuredBirthday,mLCPolSchema.getCValiDate(), "Y");


        return tNewAge;
    }



    /**
     * 计算被保人0岁保单生效对应日
     * @return
     */
    private String getInsuredvalideBirth()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 被保人0岁保单生效对应日 = 出险时经过的整保单年度 - 出生日期
         *  例如:保单的生效对应日为2005.05.04,出险日期为2008.05.06.
         *  则 (2008-2005) = 3 年,用2008.05.04 - 出生日期 = 被保人0岁保单生效对应日
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        FDate tInsuredBirthday = new FDate();
        FDate tCValidate = new FDate();
        tCValidate.getDate(mLCPolSchema.getCValiDate());

        Date tInsuredvalideBirth = null;
        tInsuredvalideBirth = PubFun.calDate(tInsuredBirthday.getDate(
            mLCPolSchema.getInsuredBirthday()), 0, "Y",tCValidate.getDate(mLCPolSchema.getCValiDate()));

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        return df.format(tInsuredvalideBirth);
    }

    /**
     * 得到投保人职业类别
     * @return
     */
    private String getOccupationType()
    {
        String tReturn = "";
        LCAppntDB tLCAppntDB = new LCAppntDB();
        tLCAppntDB.setContNo(mLCPolSchema.getContNo());
        LCAppntSet tLCAppntSet = tLCAppntDB.query();
        if (tLCAppntSet==null || tLCAppntSet.size()==0)
        {
//            this.mErrors.copyAllErrors(tLCAppntDB.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "getOccupationType";
//            tError.errorMessage = "错误:投保人信息没有找到!合同号:["+mLCPolSchema.getContNo()+"]";
//            this.mErrors.addOneError(tError);
            return tReturn;
        }

        tReturn = StrTool.cTrim(tLCAppntSet.get(1).getOccupationType());

        return tReturn;
    }

    /**
     * 得到保单累计支付
     * @return
     */
    private double getPolPay()
    {

        //计算免赔金额。
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tSql = "";
        String tCValiDate ="";
        ExeSQL tExeSQL = new ExeSQL();

        //得到保单累计支付
        tSql = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where 1=1 "
            +" and a.ClmNo = b.ClmNo"
            +" and a.ClmState in ('50','60')"       //赔案状态为结案、完成
            +" and a.ClmNo <>'"+this.mClmNo+"'"
            +" and b.GiveType != '1'"              //给付状态为给付
            +" and b.PolNo ='"+mLLClaimDetailSchema.getPolNo()+"'";


        logger.debug("要素PolPay[保单累计支付]:"+tSql);
        String tPayFranchise = tExeSQL.getOneValue(tSql);

        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "计算保单累计支付发生错误!";
            this.mErrors.addOneError(tError);
        }

        if (tPayFranchise != null && tPayFranchise.length() > 0)
        {
            t_Sum_Return = Double.parseDouble(tPayFranchise);
        }

        return t_Sum_Return;
    }
    /**
     * 针对康福险种：
     * @return
     */
    private double getPolPay1()
    {

        //计算免赔金额。
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tSql = "";
        String tCValiDate ="";
        ExeSQL tExeSQL = new ExeSQL();

        //得到保单累计支付
        tSql = "select nvl(sum(b.RealPay),0) from LLClaim a,LLClaimDetail b where 1=1 "
            +" and a.ClmNo = b.ClmNo"
            +" and a.ClmState in ('50')"       //赔案状态为结案、完成
            +" and a.ClmNo <>'"+this.mClmNo+"'"
            +" and b.GiveType != '1'"              //给付状态为给付
            +" and b.PolNo ='"+mLLClaimDetailSchema.getPolNo()+"'";


        logger.debug("要素PolPay[保单累计支付]:"+tSql);
        String tPayFranchise = tExeSQL.getOneValue(tSql);

        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "计算保单累计支付发生错误!";
            this.mErrors.addOneError(tError);
        }

        if (tPayFranchise != null && tPayFranchise.length() > 0)
        {
            t_Sum_Return = Double.parseDouble(tPayFranchise);
        }

        return t_Sum_Return;
    }


    /**
     * 得到个人客户表LDPerson的死亡时间,
     * 生存标记(0表示为生存，1表示为死亡)
     * @return
     */
    private String getDeadFlag()
    {
        String tDeadFlag = "";
        LDPersonDB tLDPersonDB = new LDPersonDB();
        tLDPersonDB.setCustomerNo(mLLClaimDetailSchema.getCustomerNo());
        LDPersonSet tLDPersonSet = tLDPersonDB.query();

        if (tLDPersonSet != null && tLDPersonSet.size() > 0 )
        {
            if (tLDPersonSet.get(1).getDeathDate() == null )
            {
                tDeadFlag = "0";
            }
            else
            {
                tDeadFlag = "1";
            }
        }

        return tDeadFlag;
    }


    /**
     * 得到初始基本保额
     * 先到P表去查，如果没有，认为是LCDuty的Amnt
     * @return
     */
    private double getOamnt()
    {
        String tReturn = "";
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
        if (mLLClaimDetailSchema.getPosFlag().endsWith("0") || mLLClaimDetailSchema.getPosFlag().endsWith("1"))
        {
           // t_Sum_Return = mLCDutySchema.getAmnt();
    		t_Sum_Return = mLCGetSchema.getStandMoney();
        }
        else
        {
//            String tSql = "select nvl(Amnt,0) from LPDuty where 1=1 "
//                +" and EdorNo ='"+mLLClaimDetailSchema.getPosEdorNo()+"'"
//                +" and PolNo  ='"+tNBPolNo+"'"
//                +" and DutyCode ='"+mLCDutySchema.getDutyCode()+"'";

        	String tSql = "select nvl(StandMoney,0) from LPGet where 1=1 "
				+ " and EdorNo ='" + mLLClaimDetailSchema.getPosEdorNo()
				+ "'" + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
				+ " and DutyCode ='" + mLCGetSchema.getDutyCode() + "'"
				+ " and GetDutyCode ='" + mLCGetSchema.getGetDutyCode()
				+ "'";
			
            logger.debug("要素Oamnt[初始基本保额]:"+tSql);
            ExeSQL tExeSQL = new ExeSQL();
            tReturn = tExeSQL.getOneValue(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getOamnt";
                tError.errorMessage = "计算保全初始基本保额发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                t_Sum_Return = Double.parseDouble(tReturn);
            }
        }

        return t_Sum_Return;
    }



    private double getVPU()
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tSql = "";

        ExeSQL tExeSQL = new ExeSQL();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No1.0 根据保单的合同号、被保人编号
         *  在LPEdorItem个险保全项目表中找出最早的核保完成时间
         *  如果日期为空，则说明没有发生保全变更项目，取C表的基本保额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select nvl(VPU,0) from LMDuty where 1=1 "
            +" and dutycode ='"+mLCDutySchema.getDutyCode()+"'";


        logger.debug("要素VPU:"+tSql);
        String tReturn = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getVPU";
            tError.errorMessage = "计算VPU发生错误!";
            this.mErrors.addOneError(tError);
        }

        if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
        {
            t_Sum_Return = Double.parseDouble(tReturn);
        }

        return t_Sum_Return;
    }


    /**
     * 保单是否复效的标记
     * 1复效
     * 0无复效
     * @return
     */
    private String getLRFlag(String ptPosEdorNoFront,String pPolNo)
    {

        String tSql = "";
        ExeSQL tExeSQL = new ExeSQL();
        String tReturn = "";


        if (ptPosEdorNoFront==null || ptPosEdorNoFront.length()==0)
        {
            return "0";
        }
        else
        {
            tSql = "select nvl(count(*),0) from LPPol where 1=1 "
                +" and EdorNo ='"+ptPosEdorNoFront+"'"
                +" and PolNo  ='"+pPolNo+"'";


            logger.debug("要素LRFlag[复效标记,保全]:"+tSql);
            tExeSQL = new ExeSQL();
            tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "LRFlag";
                tError.errorMessage = "得到保单是否复效的标记发生错误!";
                this.mErrors.addOneError(tError);
            }
        }
        return tReturn;

    }



    /**
     * 得到复效到出险时已保年期,取自出险时点,需要考虑保全
     * @return
     *
     */
    private String getLastRevDate(String ptPosEdorNoFront,String pContNo,String pPolNo)
    {
        String tReturn = "";
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        /*0未做保全,1已做保全*/
        if (ptPosEdorNoFront==null || ptPosEdorNoFront.length()==0)
        {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.0 本险种:保项保单号 = LCPol表的保单号
             *  计算主险[266,267] ,采用主险保单号
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (pPolNo.equals(mLCPolSchema.getPolNo()))
            {
                tReturn = mLCPolSchema.getLastRevDate();
                if (tReturn==null)
                {
                    tReturn = "2900-01-01";
                }
            }
            else
            {
                LCPolDB tLCPolDB = new LCPolDB();
                tLCPolDB.setPolNo(pPolNo);
                if (tLCPolDB.getInfo() == false)
                {
                    this.mErrors.copyAllErrors(tLCPolDB.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "UrgeNoticeOverdueBL";
                    tError.functionName = "getLastRevDate";
                    tError.errorMessage = "查询保单险种信息失败!";
                    this.mErrors.addOneError(tError);
                }
                tReturn = tLCPolDB.getLastRevDate();
                if (tReturn==null)
                {
                    tReturn = "2900-01-01";
                }
            }
        }
        else
        {
            String tSql = "select nvl(EdorValiDate,'2900-01-01') from LPEdorItem where 1=1 "
                +" and EdorNo ='"+ptPosEdorNoFront+"'"
                +" and ContNo ='"+pContNo+"'"
                ;

            logger.debug("要素LastRevDate[复效到出险时已保年期]:"+tSql);
            ExeSQL tExeSQL = new ExeSQL();
            tReturn = tExeSQL.getOneValue(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getLastRevDate";
                tError.errorMessage = "计算要素LastRevDate[复效到出险时已保年期]发生错误!";
                this.mErrors.addOneError(tError);
            }
            if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
            {
                tReturn = tReturn.substring(0,10);
            }
        }

        return tReturn;
    }

    /**
	 * zhangzheng 2008-12-09
	 * 得到实际住院天数
	 * @return
	 */
    private double getDayInCounts(String tClmNo,String tGetDutyCode,String tGetDutyKind)
    {
    	logger.debug("----------------------------开始计算实际住院天数---------------------------------------------------------------");

    	double tDayInDay=0.0;

		String sql = "select nvl(sum(daycount),0) from LLCaseReceipt a"
				+ " where  exists(select 1 from LMDutyGetFeeRela b where substr(a.feeitemcode,1,2)=b.ClmFeeCode and b.getdutycode='"+tGetDutyCode+"'"
				+ " and b.getdutykind='"+tGetDutyKind+"' and a.clmno='"+tClmNo+"')";
		
		logger.debug("--计算赔案" + tClmNo + "实际住院天数的sql:"+ sql);
		
		ExeSQL exesql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exesql.execSQL(sql);
		if (tSSRS.getMaxRow() > 0) {
			tDayInDay = Double.parseDouble(tSSRS.GetText(1, 1));
		}
	
		logger.debug("赔案" + tClmNo + ",GetDutyCode:"+tGetDutyCode+"," +
				",GetDutyKind:"+tGetDutyKind+",实际住院天数是"+ tDayInDay);
	
		logger.debug("----------------------------计算实际住院天数结束---------------------------------------------------------------");
		
		//释放强引用
		sql=null;
		exesql=null;
		tSSRS=null;
		
		return tDayInDay;
    }

    
    /**
     * 取多张账单累计住院日期
     * add by zhangzheng at 2009-02-25
     * @return int
     */
    public int getSumDayCount(LLClaimDetailSchema qLLClaimDetailSchema){
    	
        int tDays=0;
        
        //首先按照起始日期排序,保证所有日期是按起始递增排列,方便后边比较
        String sql="select * from LLToClaimDutyFee where clmno='"+qLLClaimDetailSchema.getClmNo()
                   +"' and polno='"+qLLClaimDetailSchema.getPolNo()+"'"
                   +" and dutycode='"+qLLClaimDetailSchema.getDutyCode()+"' and getdutytype in ('100','200') "
                   +" and getdutycode='"+qLLClaimDetailSchema.getGetDutyCode()+"' "//+partSql
                   +" order by startdate,enddate";
        
		logger.debug("--计算赔案" + qLLClaimDetailSchema.getCaseNo() + ",险种:"+qLLClaimDetailSchema.getRiskCode()+
						   ",责任:"+qLLClaimDetailSchema.getDutyCode()+",给付类型:"+qLLClaimDetailSchema.getGetDutyKind()+
						   ",给付责任编码"+qLLClaimDetailSchema.getGetDutyCode()+"下的账单累计实际住院天数的sql:"+ sql);
		
		
        LLToClaimDutyFeeDB tLLToClaimDutyFeeDB= new LLToClaimDutyFeeDB();

        LLToClaimDutyFeeSet tLLToClaimDutyFeeSet = tLLToClaimDutyFeeDB.executeQuery(sql);//取参与理算的账单信息
        if(tLLToClaimDutyFeeSet.size()==0){
            return 0;
        }
        
        //取得第一条记录的住院起始日期和结束日期作为标准日期
        String startdate=tLLToClaimDutyFeeSet.get(1).getStartDate();
        String enddate=tLLToClaimDutyFeeSet.get(1).getEndDate();
        
        logger.debug("第1条记录的起始日期:"+startdate+",截止日期是:"+enddate);
        
        if(tLLToClaimDutyFeeSet.size()==1){
        	
        	//如果只有一行则直接取天数
            startdate=tLLToClaimDutyFeeSet.get(1).getStartDate();
            enddate=tLLToClaimDutyFeeSet.get(1).getEndDate();
            tDays =  PubFun.calInterval(startdate,enddate, "D");

        }
        else
        {
            for (int i = 1; i <= tLLToClaimDutyFeeSet.size(); i++) 
            {
                logger.debug("本次循环的是第"+i+"条记录!");

                if(i==1)
                {
                    continue;
                }
                
                String laterDate = PubFun.getLaterDate(enddate,tLLToClaimDutyFeeSet.get(i).getEndDate());
                logger.debug("待计算的结束日期("+enddate+")和本次循环的结束日期("+
                		tLLToClaimDutyFeeSet.get(i).getEndDate()+")中大者是:"+laterDate);
  
                //如果两个日期相等,由于循环的次序是按起始日期排序的,所以如果两个中的大者依然是定的标准日期,则表示本次循环的日期是包含在标准日期范围内的
                if (laterDate.equals(enddate)) {
                	
                	//本次循环日期包含在标准日期内,被过滤掉!
                }
                else
                {
                    laterDate = PubFun.getLaterDate(enddate,tLLToClaimDutyFeeSet.get(i).getStartDate());
                    logger.debug("待比较的结束日期("+enddate+")和本次循环的起始日期("+
                    		tLLToClaimDutyFeeSet.get(i).getStartDate()+")中大者是:"+laterDate);
                    
                    //判断这个账单的起始时间是否在上一个的终止日之前
                    if (laterDate.equals(enddate)) 
                    {
                    	//进到这里,表示本次循环的结束日期比标准结束日期要大,所以扩展标准日期的范围,将本次循环的终止日期作为新的标准结束日期
                        enddate = tLLToClaimDutyFeeSet.get(i).getEndDate();//将后一个终止日期置为新的终止日期
                    } 
                    else 
                    {
                    	//进到这里,表示本次循环的起始日期和标准终止日期是有间隔的,可以理解是两段;
                    	
                        logger.debug("tDay1:"+tDays);
                        //先统计标准日期段的间隔天数
                        tDays = tDays+PubFun.calInterval(startdate,enddate, "D");
                        logger.debug("tDay2:"+tDays);
                        
                        //设置开始新的标准日期段
                        startdate = tLLToClaimDutyFeeSet.get(i).getStartDate();
                        enddate = tLLToClaimDutyFeeSet.get(i).getEndDate();
                        logger.debug("更新后的起始日期:"+startdate+",截止日期是:"+enddate);

                    }
                }

                
                //如果是最后一轮循环，取最后一个时间段的天数进行累计
                if(i==tLLToClaimDutyFeeSet.size())
                {
                    tDays = tDays+(PubFun.calInterval(startdate, enddate, "D"));
                }
            }
        }
        
        
    	if (tDays == 0) {
			tDays = 1;
		}
        
        logger.debug("CalDaysInHos ====================================="+tDays);
        
        return tDays;
    }
    

    /**
     * 交费次数,取自出险时点,需要考虑保全
     * @return
     */
    private String getPayTimes()
    {

        String tSql = "";
        ExeSQL tExeSQL = new ExeSQL();

        String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
        String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

        tSql = "select nvl(max(PayCount),0) from LJaPayPerson a where 1=1 "
            +" and a.PolNo ='"+tNBPolNo+"'"
            +" and (to_date('"+this.mAccDate+"','YYYY-MM-DD') <= LastPayToDate "
            +" or LastPayToDate < to_date('1900-01-01','yyyy-mm-dd'))";


        logger.debug("要素PayTimes[交费次数]:"+tSql);
        String tCal = StrTool.cTrim(tExeSQL.getOneValue(tSql));

        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getAddYearBonus";
            tError.errorMessage = "获取交费次数失败!";
            this.mErrors.addOneError(tError);
        }

        return tCal;
    }



    /**
     * 续保标志,0未续保,1已续保
     * @return
     */
    private String getAppFlag(String ptPosEdorNoFront,LCPolSchema pLCPolSchema)
    {
        String tReturn = "";
        String tSql = "";
//        if (ptPosEdorNoFront==null || ptPosEdorNoFront.length()==0)
//        {
            tSql = "select nvl(count(*),0) from lcpol where 1=1 "
                +" and proposalno='"+pLCPolSchema.getProposalNo()+"'"
                +" and renewcount>0 "
                +" and appflag=1 "
                +" and riskcode='"+pLCPolSchema.getRiskCode()+"'"
                +" and insuredno='"+pLCPolSchema.getInsuredNo()+"'"
                ;
//        }
//        else
//        {
//            tSql = "select nvl(count(*),0) from LPPol where 1=1 "
//                +" and EdorNo ='"+ptPosEdorNoFront+"'"
//                +" and proposalno='"+pLCPolSchema.getProposalNo()+"'"
//                +" and renewcount>0 "
//                +" and appflag=1 "
//                +" and riskcode='"+pLCPolSchema.getRiskCode()+"'"
//                +" and insuredno='"+pLCPolSchema.getInsuredNo()+"'"
//                ;
//        }


        logger.debug("要素AppFlag[续保标志]:"+tSql);
        ExeSQL tExeSQL = new ExeSQL();
        String tAppFlag = StrTool.cTrim(tExeSQL.getOneValue(tSql));

        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getAppFlag";
            tError.errorMessage = "计算续保标志发生错误!";
            this.mErrors.addOneError(tError);
        }

        if (tAppFlag.equals("0"))
        {
            tReturn = "0";
        }
        else
        {
            tReturn = "1";
        }

        return tReturn;
    }


    /**
     * 目的:当前准备计算的保单的总共续保次数
     * @param pLCPolSchema
     * @return
     */
    private String getCurrRenewCount(LCPolSchema pLCPolSchema)
    {
        String tReturn = "";
        String tSql = "select nvl(count(*),0) from lcpol where 1=1 "
            +" and proposalno='"+pLCPolSchema.getProposalNo()+"'"
            +" and renewcount>0 "
            ;


        ExeSQL tExeSQL = new ExeSQL();
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        logger.debug("要素CurrRenewCount[:当前准备计算的保单的总共续保次数]["+tReturn+"]:"+tSql);

        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getAppFlag";
            tError.errorMessage = "计算当前准备计算的保单的总共续保次数发生错误!";
            this.mErrors.addOneError(tError);
        }

        return tReturn;
    }


    /**
     * 返回附加险所在主险的保项信息
     * @return
     */
    private LLClaimDetailSchema getRMRiskInfo()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 返回主险所在的保项信息
         *  主附险标志 M主险 S附加险
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimDetailSchema tRLLClaimDetailSchema = null;

        String tCurrRiskType  = mLLClaimDetailSchema.getRiskType();
        if (tCurrRiskType.equals("M"))
        {
            return null;
        }


        String tCurrContNo = mLLClaimDetailSchema.getContNo();
        String tCurrPolNo  = mLLClaimDetailSchema.getPolNo();

        for (int i=1; i <= mLLClaimDetailSet.size() ; i++ )
        {
            LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);
            String tLoopContNo = tLLClaimDetailSchema.getContNo();
            String tLoopPolNo  = tLLClaimDetailSchema.getPolNo();

            String tLoopRiskType  = tLLClaimDetailSchema.getRiskType();

            if (tLoopRiskType.equals("M") && tCurrContNo.equals(tLoopContNo) && !tCurrPolNo.equals(tLoopPolNo) )
            {
                return tLLClaimDetailSchema;
            }
        }

        return tRLLClaimDetailSchema;
    }


    /**
     * 取出附加险所在的主险的理赔给付金
     * @return
     */
    private double getRMRealPay(LLClaimDetailSchema pLLClaimDetailSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        if (pLLClaimDetailSchema == null)
        {
            return t_Sum_Return;
        }
        else
        {
            t_Sum_Return = pLLClaimDetailSchema.getRealPay();
        }

        return t_Sum_Return;
    }


    /**
     * 取出附加险所在的主险的有效保额
     * @return
     */
    private double getRMAmnt(LLClaimDetailSchema pLLClaimDetailSchema)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        if (pLLClaimDetailSchema == null)
        {
            return t_Sum_Return;
        }
        else
        {
            t_Sum_Return = pLLClaimDetailSchema.getAmnt();
        }

        return t_Sum_Return;
    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－结束－－－－－－－计算要素准备－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {

        TransferData tTransferData = new TransferData();
        if ( mLLClaimPolicySet!=null && mLLClaimPolicySet.size()>0 )
        {
            mMMap.put(mLLClaimSchema, "DELETE&INSERT");
            tTransferData.setNameAndValue("Return","TRUE");
        }
        else
        {
            tTransferData.setNameAndValue("Return","FALSE");
        }
        mResult.add(tTransferData);

        mMMap.put(mLLClaimPolicySet, "INSERT");
        mMMap.put(mLLClaimDetailSet, "INSERT");
        mMMap.put(mLLClaimDutyFeeSet, "INSERT");


        mInputData.clear();
        mInputData.add(mMMap);
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
            tError.moduleName = "LLClaimCalCheckBL";
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


        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";


        String tAccNo = "80000001850";
        String tClmNo = "90000001970";
        String tAccDate = "2005-06-29";
        String tCusNo = "0000499030";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("AccDate",tAccDate);
        tTransferData.setNameAndValue("ContType","1");   //总单类型,1-个人总投保单,2-集体总单
        tTransferData.setNameAndValue("ClmState","20");  //赔案状态，20立案，30审核

        LLCaseSchema tLLCaseSchema = new LLCaseSchema();
        tLLCaseSchema.setCaseNo(tClmNo);
        tLLCaseSchema.setRgtNo(tClmNo);
        tLLCaseSchema.setCustomerNo(tCusNo);


        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);
        tVData.addElement(tLLCaseSchema);


        LLClaimCalAutoBL tClaimCalBL = new LLClaimCalAutoBL();
        tClaimCalBL.submitData(tVData, "");


    }

    private void jbInit() throws Exception {
    }


}

/**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
 * 被注销掉的程序,留下来备用
 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
 */
//  //住院天数
//  tTransferData.setNameAndValue("DaysInHos", getRealHospitalDays());
//  //住院费用
//  tTransferData.setNameAndValue("InHospFee", String.valueOf(getInHospFee()));
//  //门诊费用
//  tTransferData.setNameAndValue("DoorPostFee", String.valueOf(getDoorPostFee()));
//  //保险人个人帐户的帐户金额
//  tTransferData.setNameAndValue("InsuAccBala", String.valueOf(getInsuAccBala()));
//  if (!mLCPolSchema.getGrpPolNo().equals("00000000000000000000"))
//  {
//      LCGrpPolBL tLCGrpPolBL = new LCGrpPolBL();
//      tLCGrpPolBL.setGrpPolNo(mLCPolSchema.getGrpPolNo());
//      if (tLCGrpPolBL.getInfo())
//      {
//          LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
//          tLCGrpPolSchema.setSchema(tLCGrpPolBL.getSchema());
//
//          //赔付比例
//          tTransferData.setNameAndValue("GetRate",String.valueOf(tLCGrpPolSchema.get  ()));
//
//          //免赔额
//          tTransferData.setNameAndValue("GetLimit",String.valueOf(tLCGrpPolSchema.getGetLimit()));
//
//          //备用属性1
//          tTransferData.setNameAndValue("StandbyFlag1",String.valueOf(tLCGrpPolSchema.getStandbyFlag1()));
//
//          //备用属性2
//          tTransferData.setNameAndValue("StandbyFlag2",String.valueOf(tLCGrpPolSchema.getStandbyFlag2()));
//
//          //备用属性3
//          tTransferData.setNameAndValue("StandbyFlag3",String.valueOf(tLCGrpPolSchema.getStandbyFlag3()));
//      }
//
//      //历次保费本息和
//      tTransferData.setNameAndValue("PremIntSum",String.valueOf(getPremIntSum()));
//
//
//      //开始领取但未领完的年金和
//      tTransferData.setNameAndValue("RemainAnn",String.valueOf(getRemainAnn()));
//  }
//
//  //保单的备用属性1
//  tTransferData.setNameAndValue("PolStandbyFlag1",String.valueOf(mLCPolSchema.getStandbyFlag1()));



  /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
   * No3.0 该责任已经赔付金额累计
   * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
   */
  //double tSumPay = getSumDutyPay(mLCPolSchema.getPolNo(),mLCDutySchema.getDutyCode());
  //tTransferData.setNameAndValue("SumPay", String.valueOf(tSumPay));

///**
// * 保单是否复效的标记
// * 1复效
// * 0无复效
// * @return
// */
//private String getLRFlag(LLClaimDetailSchema pLLClaimDetailSchema,String ptPosEdorNoFront)
//{
//
//    String tSql = "";
//    ExeSQL tExeSQL = new ExeSQL();
//    String tReturn = "";
//
//
//
//
//    /*0未做保全,1已做保全*/
//    if (mLLClaimDetailSchema.getPosFlag().endsWith("0"))
//    {
//        tSql = "select nvl(count(*),0) from lcpol a where 1=1 "
//            +" and a.PolNo ='"+pLLClaimDetailSchema.getPolNo()+"'"
//            +" and LastRevDate is not null "
//            +" and LastRevDate <> CValiDate ";
//
//        logger.debug("要素LRFlag[复效标记]:"+tSql);
//        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
//        if (tExeSQL.mErrors.needDealError())
//        {
//            this.mErrors.copyAllErrors(tExeSQL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "getAddYearBonus";
//            tError.errorMessage = "得到保单是否复效的标记!";
//            this.mErrors.addOneError(tError);
//        }
//    }
//    else
//    {
//        tSql = "select nvl(count(*),0) from LPPol where 1=1 "
//            +" and EdorNo ='"+pLLClaimDetailSchema.getPosEdorNo()+"'"
//            +" and PolNo  ='"+pLLClaimDetailSchema.getPolNo()+"'";
//
//
//        logger.debug("要素LRFlag[复效标记,保全]:"+tSql);
//        tExeSQL = new ExeSQL();
//        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
//        if (tExeSQL.mErrors.needDealError())
//        {
//            this.mErrors.copyAllErrors(tExeSQL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "LRFlag";
//            tError.errorMessage = "得到保单是否复效的标记发生错误!";
//            this.mErrors.addOneError(tError);
//        }
//    }
//    return tReturn;
//
//}


//
///**
// * 得到复效到出险时已保年期,取自出险时点,需要考虑保全
// * @return
// *
// */
//private String getLastRevDate(LLClaimDetailSchema pLLClaimDetailSchema)
//{
//
//    String tReturn = "";
//    double t_Sum_Return = 0;
//    t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));
//
//
//
//
//    /*0未做保全,1已做保全*/
//    if (pLLClaimDetailSchema.getPosFlag().endsWith("0"))
//    {
//        if (pLLClaimDetailSchema.getPolNo().equals(mLCPolSchema.getPolNo()))
//        {
//            tReturn = mLCPolSchema.getLastRevDate();
//            if (tReturn==null)
//            {
//                tReturn = "1900-01-01";
//            }
//        }
//        else
//        {
//            LCPolDB tLCPolDB = new LCPolDB();
//            tLCPolDB.setPolNo(pLLClaimDetailSchema.getPolNo());
//            if (tLCPolDB.getInfo() == false)
//            {
//                this.mErrors.copyAllErrors(tLCPolDB.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "UrgeNoticeOverdueBL";
//                tError.functionName = "getLastRevDate";
//                tError.errorMessage = "查询保单险种信息失败!";
//                this.mErrors.addOneError(tError);
//            }
//            tReturn = tLCPolDB.getLastRevDate();
//            if (tReturn==null)
//            {
//                tReturn = "1900-01-01";
//            }
//
//        }
//
//    }
//    else
//    {
//        String tSql = "select nvl(LastRevDate,'1900-01-01') from LPPol where 1=1 "
//            +" and EdorNo ='"+pLLClaimDetailSchema.getPosEdorNo()+"'"
//            +" and PolNo  ='"+pLLClaimDetailSchema.getPolNo()+"'";
//
//        logger.debug("要素LastRevDate[复效到出险时已保年期]:"+tSql);
//        ExeSQL tExeSQL = new ExeSQL();
//        tReturn = tExeSQL.getOneValue(tSql);
//        if (tExeSQL.mErrors.needDealError())
//        {
//            this.mErrors.copyAllErrors(tExeSQL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "getLastRevDate";
//            tError.errorMessage = "计算要素LastRevDate[复效到出险时已保年期]发生错误!";
//            this.mErrors.addOneError(tError);
//        }
//        if ( tReturn!=null && tReturn.length()>0 && !tReturn.equals(""))
//        {
//            tReturn = tReturn.substring(0,10);
//        }
//
//    }
//
//
//    return tReturn;
//
//
//
//}

//tSql = "select nvl(count(*),0) from lcpol a where 1=1 "
//    +" and a.PolNo ='"+pLLClaimDetailSchema.getPolNo()+"'"
//    +" and LastRevDate is not null "
//    +" and LastRevDate <> CValiDate ";
//
//logger.debug("要素LRFlag[复效标记]:"+tSql);
//tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
//if (tExeSQL.mErrors.needDealError())
//{
//    this.mErrors.copyAllErrors(tExeSQL.mErrors);
//    CError tError = new CError();
//    tError.moduleName = "LLClaimCalAutoBL";
//    tError.functionName = "getAddYearBonus";
//    tError.errorMessage = "得到保单是否复效的标记!";
//    this.mErrors.addOneError(tError);
//}


//private double getTotalPrem()
//{
//    double t_Sum_Return = 0;
//    t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));
//
//    String tSql = "";
//    ExeSQL tExeSQL = new ExeSQL();
//
//    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//     * No1.0 得到LJAPayPerson实收个人交费表的总金额
//     *  LastPayToDate原交至日期 <= 意外事故发生日期
//     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//     */
//    String tPolNo   = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
//    String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
//
//    tSql = "select nvl(sum(LJAPayPerson.SumActuPayMoney),0) from LJAPayPerson where 1=1 "
//        +" and PolNo = '"+tNBPolNo+"'"
//        +" and LastPayToDate <= to_date('"+this.mAccDate+ "','yyyy-mm-dd') ";
//
//
//    String tTotalPrem = tExeSQL.getOneValue(tSql);
//    logger.debug("----------------------------计算总保费开始（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
//    logger.debug("要素TotalPrem[实收个人交费表]["+tTotalPrem+"]:"+tSql);
//
//    if (tExeSQL.mErrors.needDealError())
//    {
//        this.mErrors.copyAllErrors(tExeSQL.mErrors);
//        CError tError = new CError();
//        tError.moduleName = "LLClaimCalAutoBL";
//        tError.functionName = "getTotalPrem";
//        tError.errorMessage = "计算总保费(包括健康加费和职业加费及出险时点的保全补退费)发生错误!";
//        this.mErrors.addOneError(tError);
//    }
//
//    if (tTotalPrem != null && tTotalPrem.length() > 0)
//    {
//        t_Sum_Return = Double.parseDouble(tTotalPrem);
//    }
//
//    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//     * No2.0 得到LJSPayPerson应收个人交费表的总金额
//     *  LastPayToDate原交至日期 <= 意外事故发生日期
//     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//     */
//    tSql = "select nvl(sum(LJSPayPerson.SumActuPayMoney),0) from LJSPayPerson where 1=1 "
//        +" and PolNo = '"+tPolNo+"'"
//        +" and LastPayToDate <= to_date('"+this.mAccDate+ "','yyyy-mm-dd') ";
//
//    String tPlanPrem = tExeSQL.getOneValue(tSql);
//    logger.debug("要素TotalPrem[应收个人交费表]["+tPlanPrem+"]:"+tSql);
//
//
//    if (tExeSQL.mErrors.needDealError())
//    {
//        this.mErrors.copyAllErrors(tExeSQL.mErrors);
//        CError tError = new CError();
//        tError.moduleName = "LLClaimCalAutoBL";
//        tError.functionName = "getTotalPrem";
//        tError.errorMessage = "计算总保费(包括健康加费和职业加费及出险时点的保全补退费)发生错误!";
//        this.mErrors.addOneError(tError);
//    }
//
//    if (tPlanPrem != null && tPlanPrem.length() > 0)
//    {
//        t_Sum_Return = t_Sum_Return + Double.parseDouble(tPlanPrem);
//    }
//
//    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//     * No3.0 得到LJAGetEndorse批改补退费表(实收/实付)的补退费总金额
//     *  EdorValiDate批改生效日期 <= 意外事故发生日期
//     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//     */
//    tSql = "select nvl(sum(LJAGetEndorse.GetMoney),0) from LJAGetEndorse where 1=1 "
//        +" and PolNo = '"+tNBPolNo+"'"
//        +" and EndorsementNo in (select distinct EdorNo from LPEdorMain where EdorValiDate <= to_date('"+this.mAccDate+ "','yyyy-mm-dd'))"
//        +" and FeeFinaType in ('TF','BF')";
//
//
//    String tBTFee = tExeSQL.getOneValue(tSql);
//    logger.debug("要素TotalPrem[批改补退费表]["+tBTFee+"]:"+tSql);
//
//    if (tExeSQL.mErrors.needDealError())
//    {
//        this.mErrors.copyAllErrors(tExeSQL.mErrors);
//        CError tError = new CError();
//        tError.moduleName = "LLClaimCalAutoBL";
//        tError.functionName = "getTotalPrem";
//        tError.errorMessage = "计算总保费的补退费发生错误!";
//        this.mErrors.addOneError(tError);
//    }
//
//    if (tBTFee != null && tBTFee.length() > 0)
//    {
//        t_Sum_Return = t_Sum_Return + Double.parseDouble(tBTFee);
//    }
//
//    logger.debug("要素TotalPrem[总保费]:["+t_Sum_Return+"]");
//    logger.debug("----------------------------计算总保费结束（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
//    return t_Sum_Return;
//}


