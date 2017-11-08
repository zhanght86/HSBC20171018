package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Vector;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LLToClaimDutyFeeDB;
import com.sinosoft.lis.db.LMClaimCtrlDB;
import com.sinosoft.lis.db.LMDutyClmCtrlRelaDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLToClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LMClaimCtrlSchema;
import com.sinosoft.lis.schema.LMDutyClmCtrlRelaSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LLDutyCtrlSet;
import com.sinosoft.lis.vschema.LLToClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LMDutyClmCtrlRelaSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 *
 * <p>Description: 计算步骤四：进行免赔计算</p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 *
 * <p>Company: SinoSoft Co. Ltd,</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LLClaimCalFranchiseBL
{
private static Logger logger = Logger.getLogger(LLClaimCalFranchiseBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private MMap mMMap = new MMap();                        /** 往后面传输的数据库操作 */

    private LCPolSchema mLCPolSchema = new LCPolSchema();

    private String mAccNo = "";     //事件号
    private String mAccDate = "";   //意外事故发生日期
    private String mClmNo = "";     //赔案号
    private String mContType = "";  //总单类型,1-个人总投保单,2-集体总单
	private String mInsDate = ""; // 出险时间
    private  LLDutyCtrlSet mLLDutyCtrlSet = null;
    private  String mExemptMoney="";//免陪额
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();    //立案登记信息
	
//    private LLDutyCtrlSchema mLLDutyCtrlSchema = new LLDutyCtrlSchema();
//    private LLDutyCtrlSchema mLLDutyCtrlSchema2 = new LLDutyCtrlSchema();
    private String mDutyCode = "";
//    private String mLLDutyCtrlFlag ="0";


    public LLClaimCalFranchiseBL(){}


    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData,String cOperate)
    {

        logger.debug("----------理算步骤四-----免赔额计算-----LLClaimCalFranchiseBL测试-----开始----------");

        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }


        if (!dealData())
        {
            return false;
        }


//        if (!prepareOutputData())
//        {
//            return false;
//        }

//        if (!pubSubmit())
//        {
//            return false;
//        }

        logger.debug("----------理算步骤四-----免赔额计算-----LLClaimCalFranchiseBL测试-----结束----------");
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {

        mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人投保单,2-集体总投保单
        
		mInsDate = this.mLLClaimPubFunBL.getInsDate(mClmNo);

        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {


    	
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.1 取得立案信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
    	
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 6.2 对匹配的数据进行免赔额的计算
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        mLLRegisterSchema = mLLClaimPubFunBL.getLLRegister(this.mClmNo);
        if(mLLRegisterSchema==null)
        {
        	CError.buildErr(this, "查询不到立案信息!");
        	return false;
        }
        
        if (!getFranchiseRisk())
        {
            return false;
        }

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－开始－－－－－－－进行免赔计算－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 获取需要进行免赔计算的险种信息
     * @return
     */
    private boolean getFranchiseRisk()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 1.0 从待理算责任LLToClaimDuty表中找出该赔案下有多少险种,多少保单
         *  必须根据每张保单进行计算
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        String tSql = "select CaseNo,RiskCode,PolNo,count(*),grpcontno,contno,dutycode"
        String tSql = "select distinct CaseNo,RiskCode,PolNo,grpcontno,contno,dutycode,getdutykind,getdutycode,customerno"
   //      + ",(select contplancode from lcinsured where contno=LLToClaimDuty.contno and rowno='1') "
           + " from LLToClaimDuty where 1 = 1"
           +" and CaseNo = '"+this.mClmNo+"'"
           +" and substr(getdutykind,2)='00'"
           +" group by CaseNo,RiskCode,PolNo,grpcontno,contno,dutycode,getdutykind,getdutycode,customerno";
        
        logger.debug("--从待理算责任LLToClaimDuty表中找出该赔案下有多少险种,多少保单 必须根据每张保单进行计算sql： "+tSql);
		
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);

        if (tExeSQL.mErrors.needDealError())
        {
			CError.buildErr(this, "获取免赔计算的险种信息发生错误!");

            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimCalFranchiseBL.getFranchiseRisk()--LLClaimCalFranchiseBL!"+tSql);
            logger.debug("------------------------------------------------------");
        }
        
     // 得到计算赔案汇总信息需要用到的币种
		String tqSql = "select distinct(currency) from LLToClaimDuty where 1 = 1 and CaseNo = '"+ this.mClmNo + "'"
			+" and substr(getdutykind,2)='00'";
		String tdCurrency="";
		SSRS tSSRS1 = tExeSQL.execSQL(tqSql);

		if (tSSRS1 != null) {
			if(tSSRS1.getMaxRow()==1)
				tdCurrency = tSSRS1.GetText(1, 1);
			else
			{
				String tSql1 = "select * from ldcode1 where CodeType='currencyprecision' and code1 in (select sysvarvalue from LDSysVar where sysvar='nativeplace')";
				LDCode1DB tLDCode1DB = new LDCode1DB();
				LDCode1Set tLDCode1Set = tLDCode1DB.executeQuery(tSql1);

				if (tLDCode1Set != null) {					
					tdCurrency = tLDCode1Set.get(1).getCodeName();
				}
			}
		}
		this.mTransferData.setNameAndValue("sumCurrency", tdCurrency);
		logger.debug("赔案汇总信息用的币种为： "+tdCurrency);

        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String tClmNo    = tSSRS.GetText(i, 1);             //赔案号
            String tRiskCode = tSSRS.GetText(i, 2);             //保单险种号
            String tPolNo    = tSSRS.GetText(i, 3);             //保单号
            String tGrpContNo= tSSRS.GetText(i, 4);             //集体保单号
            String tContNo   = tSSRS.GetText(i, 5);             //保单号
            String tDutyCode = tSSRS.GetText(i, 6);             //责任代码
            String tGetDutyKind = tSSRS.GetText(i, 7);             //给付责任类型
            String tGetDutyCode = tSSRS.GetText(i, 8);             //给付责任编码
            String tCustomerNo = tSSRS.GetText(i, 9);             //给付责任编码
            mDutyCode = tDutyCode;


    			
             // zhangzheng 2009-04-08 团险特约处理 统一 ，屏蔽这段逻辑

//            //conttype=1
//            LLDutyCtrlDB mLLDutyCtrlDB = new LLDutyCtrlDB();
//            LLDutyCtrllevelDB mLLDutyCtrllevelDB = new LLDutyCtrllevelDB();
//            String tSql2 = " select b.conttype, c.contplancode from lcpol b, lcinsured c"
//                         + " where b.polno = '"+tPolNo+"'"
//                         + " and c.contno = '"+tContNo+"'";
//
//            SSRS tSSRS1 = tExeSQL.execSQL(tSql2);
//            for (int a = 1; a <= tSSRS1.getMaxRow(); a++) {
//            String tContType = "1"; //保单险种号
//            String tContPlanCode = tSSRS1.GetText(a, 2); //保单险种号
//
//            mLLDutyCtrlDB.setContType(tContType);
//            //ContNo
//            mLLDutyCtrlDB.setContNo(tContNo);
//            mLLDutyCtrlDB.setDutyCode(tDutyCode);
//            if (tContPlanCode != null && !tContPlanCode.equals("")){
//                   mLLDutyCtrlDB.setContPlanCode(tContPlanCode);
//            }else{
//                    mLLDutyCtrlDB.setContPlanCode("0");
//            }
//
//            mLLDutyCtrlDB.setGrpContNo(tGrpContNo);
////              mLLDutyCtrllevelDB.setContNo(mLCGetSchema.getGrpContNo());
////              mLLDutyCtrllevelDB.setContType(tContType);
////              mLLDutyCtrllevelDB.setDutyCode(mLCGetSchema.getDutyCode());
////              mLLDutyCtrllevelDB.setContPlanCode(tContPlanCode);
//
//            if (mLLDutyCtrlDB.getInfo()) {
//              mLLDutyCtrlFlag="1";
//              mLLDutyCtrlSchema = mLLDutyCtrlDB.getSchema();
//              logger.debug("**************个人单有值 ******************");
//              }
//            }
//
//            //conttype=2
//            LLDutyCtrlDB mLLDutyCtrlDB2 = new LLDutyCtrlDB();
//            String tSql211 = " select b.conttype, c.contplancode from lcpol b, lcinsured c"
//                           + " where b.polno = '"+tPolNo+"'"
//                           + " and c.contno = '"+tContNo+"'";
//
//            SSRS tSSRS12 = tExeSQL.execSQL(tSql211);
//            for (int a = 1; a <= tSSRS12.getMaxRow(); a++) {
//            String tContType2 = "2"; //保单险种号
//            String tContPlanCode2 = tSSRS12.GetText(a, 2); //保单险种号
//            mLLDutyCtrlDB2.setContType(tContType2);
//            //GrpContNo
//            mLLDutyCtrlDB2.setContNo(tGrpContNo);
//            mLLDutyCtrlDB2.setDutyCode(tDutyCode);
//            if (tContPlanCode2 != null && !tContPlanCode2.equals("")){
//                   mLLDutyCtrlDB2.setContPlanCode(tContPlanCode2);
//            }else{
//                    mLLDutyCtrlDB2.setContPlanCode("0");
//            }
//            mLLDutyCtrlDB2.setGrpContNo(tGrpContNo);
////              mLLDutyCtrllevelDB.setContNo(mLCGetSchema.getGrpContNo());
////              mLLDutyCtrllevelDB.setContType(tContType);
////              mLLDutyCtrllevelDB.setDutyCode(mLCGetSchema.getDutyCode());
////              mLLDutyCtrllevelDB.setContPlanCode(tContPlanCode);
//
//            if (mLLDutyCtrlDB2.getInfo()) {
//    	          mLLDutyCtrlFlag="2";
//    	          mLLDutyCtrlSchema2 = mLLDutyCtrlDB2.getSchema();
//    	          logger.debug("**************团体单有值 ******************");
//    	       }
//            }

        	//获得险种信息
            if (!getLCPol(tPolNo))
            {
                 return false;
            }

    			//免陪额处理
            if (!getFranchiseCtrl(tRiskCode,tPolNo,tDutyCode,tGetDutyKind,tGetDutyCode,tCustomerNo))
            {
                 return false;
            }         
            
            
            tClmNo     = null;             //赔案号
            tRiskCode  = null;             //保单险种号
            tPolNo     = null;             //保单号
            tGrpContNo = null;             //集体保单号
            tContNo    = null;             //保单号
            tDutyCode  = null;             //责任代码
            tCustomerNo = null;            //客户号
            tGetDutyKind = null;           //给付责任类型
        }

        return true;
    }


    /**
     * 获取需要进行免赔计算的险种对应的累加器编号信息
     * @return
     */
    private boolean getFranchiseCtrl(String pRiskCode,String pPolNo,String pDutyCode,String pGetDutyKind,String pGetdutyCode,String pCustomerNo)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 1.0 找出该险种在LMDutyClmCtrlRela表中有多少控制器，对结果进行排序
         *  根据每个控制器进行计算
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

    	/*        String tSql = "select distinct ClaimCtrlCode,GetDutyCode,serialno from LMDutyClmCtrlRela where 1 = 1"
            + " and RiskCode = '"+pRiskCode+"'"
            + " order by serialno";
    	 */
    	
    	//应该按责任取
          String tSql = "select distinct ClaimCtrlCode,DutyCode,GetDutyCode,serialno from LMDutyClmCtrlRela where 1 = 1"
          + " and RiskCode = '"+pRiskCode+"'"
          + " and dutycode='"+pDutyCode+ "'"
          + " and getdutyCode='"+pGetdutyCode+"'"
          + " and getdutykind='"+pGetDutyKind+"'"
          + " order by GetDutyCode,ClaimCtrlCode";

        logger.debug("找出该险种在LMDutyClmCtrlRela表中有多少控制器，对结果进行排序:"+tSql);
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);

        if (tExeSQL.mErrors.needDealError())
        {

            CError.buildErr(this, "获取免赔计算的险种计算器信息发生错误!");

            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimCalFranchiseBL.getFranchiseCtrl()--获取免赔计算的险种计算器信息发生错误!"+tSql);
            logger.debug("------------------------------------------------------");

        }

        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String tClaimCtrlCode  = StrTool.cTrim(tSSRS.GetText(i,1));     //控制器编号信息
            String tDutyCode    = StrTool.cTrim(tSSRS.GetText(i,2));     //控制器编号信息
			String tGetDutyCode = StrTool.cTrim(tSSRS.GetText(i, 3)); // 给付类型信息


            if (!getFranchiseCtrlToFee(pRiskCode,pPolNo,tClaimCtrlCode,
            		pDutyCode,pGetDutyKind,pGetdutyCode,pCustomerNo))
            {
                return false;
            }
        }

        return true;
    }


    /**
     * 获取需要进行免赔计算的险种对应的累加器编号,所对应的保项、保项下的费用信息
     * @return
     */
    private boolean getFranchiseCtrlToFee(String pRiskCode,String pPolNo,
    		String pClaimCtrlCode,String pDutyCode,String pGetDutyKind,String pGetDutyCode,String pCustomerNo)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 1.0  定义免赔计算变量
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //用于提交到后台的结果集
        LLToClaimDutyFeeSet tLLToClaimDutyFeeSaveSet = new LLToClaimDutyFeeSet();

        //免赔额
        double t_Sum_InitFranchise = 0;
        t_Sum_InitFranchise = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_InitFranchise));

        //已经扣除的免赔额
        double t_Sum_PayFranchise = 0;
        t_Sum_PayFranchise = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_PayFranchise));

        //剩余的免赔额
        double t_Sum_BalFranchise = 0;
        t_Sum_BalFranchise = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_BalFranchise));

		// 社保第三方金额数组
		double[] tSocOtherFee = {0.0,0.0};

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 2.0 找出该累加器LMClaimCtrl的详细描述信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LMClaimCtrlDB tLMClaimCtrlDB = new LMClaimCtrlDB();
        tLMClaimCtrlDB.setClaimCtrlCode(pClaimCtrlCode);
        LMClaimCtrlSchema tLMClaimCtrlSchema = tLMClaimCtrlDB.query().get(1);


        if (tLMClaimCtrlSchema == null)
        {
			CError.buildErr(this, "险种[" + pRiskCode + "],控制器编码[" + pClaimCtrlCode
					+ "]没有找到,免赔计算终止!!!");
            return false;

        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 2.2 判断控制器的类型ClaimCtrlType是否为1，不为1继续循环，不需要进行计算
         *  1.扣减(免赔额)
         *  2.给付比例(投保人,被保险人共付或者赔付比例)
         *  3.金额险种(保额上限)
         *  4.服务限制(天数等)
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tLMClaimCtrlSchema.getClaimCtrlType().equals("1"))
        {
        	
            /**-----------------------------------------------------------------------
             * zhangzheng 2009-04-10 获得特约录入的免赔额
             * -----------------------------------------------------------------------
             */
            mLLDutyCtrlSet=mLLClaimPubFunBL.getLLDutyCtrl(mLLRegisterSchema.getGrpContNo());
            
            if(mLLDutyCtrlSet!=null)
            {
                //免赔额
                mExemptMoney = mLLClaimPubFunBL.getLLDutyCtrlPoint
                	 	(mLLRegisterSchema.getGrpContNo(), pCustomerNo, pDutyCode,
                	 			pGetDutyCode, "Exempt", "0","0");
                
                if(mExemptMoney==null||mExemptMoney.trim().equals("")||mExemptMoney.trim().equals("-1"))
                {
                	mExemptMoney="0";
                }
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * 2.3 判断控制器的理赔控制计算方式CalCtrlFlag,计算初始的免赔额
             *  1.取描述默认值
             *  2.实例化时取默认计算要素计算
             *  3.实例化时取约定计算要素计算
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (tLMClaimCtrlSchema.getCalCtrlFlag().equals("1"))
            {
                if(mLLDutyCtrlSet!=null&&(!mExemptMoney.trim().equals("0")))
                {
                    t_Sum_InitFranchise = PubFun.setPrecision(Double.parseDouble(mExemptMoney),"0.00");
                }
                else
                {
                    t_Sum_InitFranchise = PubFun.setPrecision(tLMClaimCtrlSchema.getDefaultValue(),"0.00");
                }

            }
            else
            {
                //根据定义的计算公式取出初始免赔额。
                String tCalCode = tLMClaimCtrlSchema.getCalCode();
                t_Sum_InitFranchise = executeCalCode(tCalCode);

            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * 2.4 判断控制器的有效期间PeriodFlag,计算已经扣除的免赔额
             *  1.保单年度
             *  2.日历年度
             *  3.保险期间
             *  4.每月
             *  5.每天
             *  6.每次赔案
             *  7.每次事故
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tPeriodFlag = tLMClaimCtrlSchema.getPeriodFlag();
			logger.debug(pClaimCtrlCode+"的有效期间periodFlag:"+tPeriodFlag);
			

            if (tPeriodFlag.equals("1"))
            {
                //同一保单年度内已扣除的免赔额进行累积（例：237产品）
                t_Sum_PayFranchise = getFranchiseYear(pPolNo,pCustomerNo);
            }
            else if (tPeriodFlag.equals("6"))
            {
                //每次赔案的免赔额，属于固定金额
                t_Sum_PayFranchise = 0;
            }
            else if (tPeriodFlag.equals("7"))
            {
                //每次事故累计扣除的免赔额进行累积（例：产品）
                t_Sum_PayFranchise = getFranchiseAccident(pPolNo,pCustomerNo);
            }
            else
            {
                // @@错误处理
    			CError.buildErr(this, "进行免赔计算时控制器的有效期间PeriodFlag=[" + tPeriodFlag
						+ "]没有进行定义,免赔计算终止!!!");
    			
                logger.debug("------------------------------------------------------");
                logger.debug("--LLClaimCalFranchiseBL.getFranchiseCtrlToFee()--制器的有效期间PeriodFlag="+tPeriodFlag+"]，没有进行定义,将不进行免赔计算!");
                logger.debug("------------------------------------------------------");
            }

			/**
			 * zhangzheng 2008-12-20
			 * 取社保和第三方给付金额,同免赔额一起账单的扣减
			 */
			//tSocOtherFee=mLLClaimPubFunBL.getSocOtherFee(this.mClmNo,mLCPolSchema.getInsuredNo());
			tSocOtherFee=mLLClaimPubFunBL.getSocOtherFee(this.mClmNo,mLCPolSchema.getInsuredNo(),(String) mTransferData.getValueByName("sumCurrency"));

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * 2.5 计算剩余免赔额,只有当大于0的时候才进行计算,小于0继续循环
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */           
			t_Sum_BalFranchise = Arith.round(t_Sum_InitFranchise
					+tSocOtherFee[0]+tSocOtherFee[1], 2)- t_Sum_PayFranchise;
			
			logger.debug("t_Sum_BalFranchise:"+t_Sum_BalFranchise);

            if (t_Sum_BalFranchise > 0 )
            {

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * 5.0 找出LMDutyClmCtrlRela表中该累加器下的所有费用描述信息，
                 *  主要是该累加器下定义描述了那些费用编码需要进行免赔额的计算
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                LMDutyClmCtrlRelaDB tLMDutyClmCtrlRelaDB = new LMDutyClmCtrlRelaDB();
                tLMDutyClmCtrlRelaDB.setClaimCtrlCode(pClaimCtrlCode);
                tLMDutyClmCtrlRelaDB.setRiskCode(pRiskCode);
                tLMDutyClmCtrlRelaDB.setDutyCode(pDutyCode);
                tLMDutyClmCtrlRelaDB.setGetDutyCode(pGetDutyCode);
                tLMDutyClmCtrlRelaDB.setGetDutyKind(pGetDutyKind);
                
                LMDutyClmCtrlRelaSet tLMDutyClmCtrlRelaSet = tLMDutyClmCtrlRelaDB.query();


                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * 6.0 对查询出来的费用信息进行免赔计算
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                for (int i = 1; i <= tLMDutyClmCtrlRelaSet.size(); i++)
                {
                    LMDutyClmCtrlRelaSchema tLMDutyClmCtrlRelaSchema = tLMDutyClmCtrlRelaSet.get(i);

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * 6.1 费用或保项编码定义为00，不进行免赔计算
                     *
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */

					if(!(tLMDutyClmCtrlRelaSchema.getFeecode()==null||
							tLMDutyClmCtrlRelaSchema.getFeecode().equals(""))){
						
						if (tLMDutyClmCtrlRelaSchema.getFeecode().equals("00")
								|| tLMDutyClmCtrlRelaSchema.getGetDutyCode()
										.equals("00")) {
							continue;
						}
					}

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * 6.6 根据控制器对应的费用信息，查询LLToClaimDutyFee表里所有符合条件的数据
                     *  也就是费用数据，逐项进行免赔额的扣除。
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    //String tDutyCode = StrTool.cTrim(tLMDutyClmCtrlRelaSchema.getDutyCode());            //责任
                    //String tGetDutyCode = StrTool.cTrim(tLMDutyClmCtrlRelaSchema.getGetDutyCode());      //给付责任

                    String tDutyFeeCode = tLMDutyClmCtrlRelaSchema.getFeecode();
                    String tFeeItemType  = tLMDutyClmCtrlRelaSchema.getFeeItemType().trim();
                    String tSql  = "";

                    if (tFeeItemType.equals("A") || tFeeItemType.equals("B") )
                    {

                        tSql = "select * from LLToClaimDutyFee where 1=1 "
                            + " and ClmNo    = '"+ this.mClmNo +"'"
                            + " and PolNo    = '"+ pPolNo +"'"
                            + " and RiskCode = '"+ pRiskCode +"'"
                            + " and GetDutyCode = '"+ pGetDutyCode +"'"
                            + " and DutyFeeType = '"+ tFeeItemType +"'"
                            + " and DutyFeeCode like '"+ tFeeItemType +"%'"
                            + " and CustomerNo ='"+pCustomerNo+"'";


//                        tSql = "select * from LLCaseReceipt where 1 = 1"
//                            +" and FeeItemCode like '"+ tFeeItemType +"%'"
//                            +" and FeeItemType in ('"+tFeeItemType+"')"
//                            +" and MainFeeNo in (select MainFeeNo from LLFeeMain where 1 = 1 "
//                                    +" and ClmNo = '"+ this.mClmNo +"'"
//                                    +")";
                    }
                    else
                    {
                        tSql = "select * from LLToClaimDutyFee where 1=1 "
                            + " and ClmNo    = '"+ this.mClmNo +"'"
                            + " and PolNo    = '"+ pPolNo +"'"
                            + " and RiskCode = '"+ pRiskCode +"'"
                            + " and GetDutyCode = '"+ pGetDutyCode +"'"
                            + " and DutyFeeType = '"+ tFeeItemType +"'"
                            + " and DutyFeeCode like '"+ tFeeItemType +"%'"
                            + " and CustomerNo ='"+pCustomerNo+"'";

                    }


                    logger.debug("--根据控制器对应的费用信息，查询LLToClaimDutyFee表里账单类型为"+tLMDutyClmCtrlRelaSchema.getFeeItemType()+"的数据:"+tSql);

                    LLToClaimDutyFeeDB tLLToClaimDutyFeeDB = new LLToClaimDutyFeeDB();
                    LLToClaimDutyFeeSet tLLToClaimDutyFeeSet = tLLToClaimDutyFeeDB.executeQuery(tSql);
                    if (tLLToClaimDutyFeeDB.mErrors.needDealError())
                    {
                        // @@错误处理
						CError.buildErr(this, "查询控制器对应的费用信息信息发生错误,免赔计算终止!!!");
						
                        logger.debug("------------------------------------------------------");
                        logger.debug("--LLClaimCalFranchiseBL.getFranchiseCtrlToFee()--查询控制器对应的费用信息信息发生错误!"+tSql);
                        logger.debug("------------------------------------------------------");

                    }


        			/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 6.3
					 * 查询LLToClaimDutyFee表里所有符合条件的数据 也就是费用数据，逐项进行免赔额的扣除。
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
                    for(int m=1;m<=tLLToClaimDutyFeeSet.size();m++)
                    {
                        LLToClaimDutyFeeSchema tLLToClaimDutyFeeSchema = tLLToClaimDutyFeeSet.get(m);
                        
            			String tGetDutyKind = StrTool.cTrim(tLLToClaimDutyFeeSchema.getGetDutyType()); // 理赔类型
            			String tFeeDetailNo = StrTool.cTrim(tLLToClaimDutyFeeSchema.getDutyFeeStaNo());

                        //调整金额，也就是参与计算的金额。
                        double t_Sum_Adj = 0;
                        t_Sum_Adj = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Adj));
                        t_Sum_Adj = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(),"0.00");

                        //已经计算过的免赔额
                        double t_Sum_Out = 0;
                        t_Sum_Out = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Out));
                        t_Sum_Out = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(),"0.00");

                        //  待计算金额 > 免赔额 and 待计算免赔额 > 计算金额
                        //  对免赔额进行扣减
                        if (t_Sum_Adj > t_Sum_Out && t_Sum_BalFranchise > t_Sum_Adj )
                        {
                            t_Sum_Out = t_Sum_Adj;
                        }
                        else if (t_Sum_Adj > t_Sum_Out && t_Sum_BalFranchise < t_Sum_Adj )
                        {
                            t_Sum_Out = t_Sum_BalFranchise;
                        }
                        
						logger.debug("扣除完tFeeDetailNo(账单明细编码)："+tFeeDetailNo+"后的t_Sum_BalFranchise(剩余免赔额):"+t_Sum_BalFranchise+
								   ",扣除免赔额后的账单金额(待理算金额):"+t_Sum_Adj+",待理算免赔额:"+t_Sum_Out);

                        t_Sum_BalFranchise = t_Sum_BalFranchise  - t_Sum_Out;
                        tLLToClaimDutyFeeSchema.setOutDutyAmnt(t_Sum_Out);

                        tLLToClaimDutyFeeSaveSet.add(tLLToClaimDutyFeeSchema);
                    }

                }



                MMap tMMap = new MMap();
                tMMap.put(tLLToClaimDutyFeeSaveSet, "UPDATE");

                mInputData.clear();
                mInputData.add(tMMap);

                if (!pubSubmit())
                {
                    return false;
                }


            } //if 2.5 计算剩余免赔额,只有当大于0的时候才进行计算,小于0继续循环

        }//if 2.2 判断控制器的类型ClaimCtrlType是否为1，不为1继续循环，不需要进行计算


        return true;
    }



    /**
     * 获取每次事故免赔额
     * 根据意外事故号进行判断
     * @return
     */
    private double getFranchiseAccident(String pPolNo,String pCustomerNo)
    {

        //计算免赔金额。
        double t_Sum_PayFranchise = 0;
        t_Sum_PayFranchise = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_PayFranchise));

//        String tSql = "select nvl(sum(a.OutDutyAmnt),0) from LLClaimDutyFee a,LLClaimDetail b where 1 = 1"
//            + " and a.ClmNo = b.ClmNo "
//            + " and a.ClmNo not in ( '"+ this.mClmNo + "')"
//            + " and a.ClmNo in "
//                + " (select e.CaseNo from LLCaseRela e, LLClaim f where 1=1 "
//                    + " and e.CaseNo = f.ClmNo "
//                    + " and e.CaseRelaNo='"+this.mAccNo+"'"
//                    + " and  f.GiveType in ('0','4') )" //只计算给付案件
//            + " and a.PolNo = '"+pPolNo+"'"
//            + " and b.GiveType != '1'";
        
		String tSql = "select nvl(sum(a.OutDutyAmnt),0) from LLClaimDutyFee a,LLClaimDetail b where 1 = 1"
			+ " and a.ClmNo = b.ClmNo "
			+ " and a.ClmNo not in ( '"
			+ this.mClmNo
			+ "')"
			+ " and a.ClmNo in "
			+ " (select e.CaseNo from LLCaseRela e, LLClaim f where 1=1 "
			+ " and e.CaseNo = f.ClmNo "
			+ " and e.CaseRelaNo='"
			+ this.mAccNo + "'" + " and f.GiveType = '0')" // 只计算给付案件
			+ " and a.PolNo = '" + pPolNo + "'" + " and b.GiveType = '0'"
			+ " and a.customerNo='"+pCustomerNo+"'";


        ExeSQL tExeSQL = new ExeSQL();
        String tPayFranchise = tExeSQL.getOneValue(tSql);

        if (tExeSQL.mErrors.needDealError())
        {
			CError.buildErr(this, "计算事故免赔额发生错误!");
   
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimCalFranchiseBL.getFranchiseAccident()--计算事故免赔额发生错误!"+tSql);
            logger.debug("------------------------------------------------------");
        }


        if (tPayFranchise != null && tPayFranchise.length() > 0)
        {
            return Double.parseDouble(tPayFranchise);
        }


        return t_Sum_PayFranchise;

    }


    /**
     * 获取年度免赔额
     * 同一保单年度内已扣除的免赔额进行累积（例：237产品）
     * @return
     */
    private double getFranchiseYear(String pPolNo,String pCustomerNo)
    {

        //计算免赔金额。
        double t_Sum_PayFranchise = 0;
        t_Sum_PayFranchise = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_PayFranchise));

        String tSql = "";
        String tCValiDate ="";
        ExeSQL tExeSQL = new ExeSQL();

        //得到保单的险种生效日期
        tSql = "select * from LCPol where 1=1 and PolNo='"+pPolNo+"'";

        LCPolSet tLCPolSet = new LCPolDB().executeQuery(tSql);

        if(tLCPolSet != null && tLCPolSet.size() == 1 )
        {
            tCValiDate = tLCPolSet.get(1).getCValiDate();

            //计算保单生效日期和事故日期之间的差值
            int tInterval = PubFun.calInterval(tCValiDate,this.mAccDate,"Y");

            String tBDate ="";
            String tEDate ="";

            //计算保单年度的开始，结束日期
            if (tInterval == 0)
            {
                tBDate = tCValiDate;
                tEDate = PubFun.calDate(tCValiDate,1,"Y",tCValiDate);
            }
            else if (tInterval < 0)
            {
                tBDate = "1800-01-01";
                tEDate = "1800-01-01";
            }
            else
            {
                tBDate = PubFun.calDate(tCValiDate,tInterval,"Y",tCValiDate);
                tEDate = PubFun.calDate(tBDate,1,"Y",tCValiDate);
            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * 根据lcpol表取免赔额
             *             + " and a.ClmNo in "
                + " (select e.CaseNo from LLCaseRela e, LLClaim f where 1=1 "
                    + " and e.CaseNo = f.ClmNo "
                    + " and e.CaseRelaNo='"+this.mAccNo+"'"
                    + " and f.GiveType = '0')" //只计算给付案件
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
//            tSql = "select nvl(sum(a.OutDutyAmnt),0) from LLClaimDutyFee a,LLCaseRela b,LLAccident c ,LLClaim d where 1 = 1"
//                + " and a.ClmNo = b.CaseNo"
//                + " and a.ClmNo = d.ClmNo"
//                + " and d.ClmState in ('50','60') "//只计算状态为结案,完成的费用
//                + " and b.CaseRelaNo = c.AccNo"
//                + " and a.ClmNo not in ( '"+ this.mClmNo + "')"
//                + " and a.PolNo = '"+pPolNo+"'"
//                + " and c.AccDate >= to_date('"+tBDate+ "','yyyy-mm-dd')"
//                + " and c.AccDate <= to_date('"+tEDate+ "','yyyy-mm-dd')";
            tSql = "select nvl(sum(a.OutDutyAmnt),0) from LLClaimDutyFee a,LLCase c ,LLClaim d where 1 = 1"
                + " and a.ClmNo = d.ClmNo"
                + " and d.ClmState in ('50','60') "//只计算状态为结案,完成的费用
                + " and a.ClmNo = c.CaseNo"
                + " and a.ClmNo not in ( '"+ this.mClmNo + "')"
                + " and a.PolNo = '"+pPolNo+"'"
                + " and a.customerno='"+pCustomerNo+"'"
                + " and a.customerno=c.customerno ";
                //+ " and c.AccDate >= to_date('"+tBDate+ "','yyyy-mm-dd')"
                //+ " and c.AccDate <= to_date('"+tEDate+ "','yyyy-mm-dd')";

            String tPayFranchise = StrTool.cTrim(tExeSQL.getOneValue(tSql));
            if (tExeSQL.mErrors.needDealError())
            {
				CError.buildErr(this, "计算年度免赔额发生错误!");
        
                logger.debug("------------------------------------------------------");
                logger.debug("--LLClaimCalFranchiseBL.getFranchiseYear()--计算年度免赔额发生错误!"+tSql);
                logger.debug("------------------------------------------------------");

            }

            if (tPayFranchise != null && tPayFranchise.length() > 0)
            {
                t_Sum_PayFranchise = Double.parseDouble(tPayFranchise);
            }


        }

        return t_Sum_PayFranchise;

    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－开始－－－－－－－进行计算要素准备－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**
     * 得到保单险种信息
     */
    private boolean getLCPol(String pPolNo)
    {

        SynLCLBPolBL tSynLCLBPolBL = new SynLCLBPolBL();
        tSynLCLBPolBL.setPolNo(pPolNo);

        tSynLCLBPolBL.getInfo();
        if (tSynLCLBPolBL.mErrors.needDealError())
        {
            // @@错误处理
			CError.buildErr(this, "险种信息查询失败!");
            return false;
        }

        mLCPolSchema.setSchema(tSynLCLBPolBL.getSchema());

        return true;
    }



    /**
     * 目的：理赔计算
     * @param pCalSum 计算金额
     * @param pCalCode 计算公式
     * @return double
     */
    private double executeCalCode(String pCalCode)
    {
        double rValue;

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 设置各种参数
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        //增加基本要素,计算给付金
        TransferData tTransferData = new TransferData();
//
//      tTransferData.setNameAndValue("Je_gf", String.valueOf(pCalSum));

        tTransferData.setNameAndValue("PolNo", String.valueOf(mLCPolSchema.getPolNo()));

        // zhangzheng 2009-04-08 团险特约处理 统一 ，屏蔽这段逻辑
//        String getExempt="";
//
//        if (mLLDutyCtrlFlag.equals("1") && mLLDutyCtrlSchema.getExempt()>=0){
//           getExempt=String.valueOf(mLLDutyCtrlSchema.getExempt());
//        }
//        else
//        if (mLLDutyCtrlFlag.equals("2") && mLLDutyCtrlSchema2.getExempt()>=0){
//           getExempt=String.valueOf(mLLDutyCtrlSchema2.getExempt());
//        }else{
//           getExempt="-1";
//        }
//        tTransferData.setNameAndValue("Exempt", getExempt);

        tTransferData.setNameAndValue("DutyCode", mDutyCode);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 将所有设置的参数加入到PubCalculator.addBasicFactor()中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        Vector tVector = tTransferData.getValueNames();
        PubCalculator tPubCalculator = new PubCalculator();
        logger.debug("-----------------------执行免赔算法-----打印计算要素--------------------");
        for (int i = 0; i < tVector.size(); i++)
        {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
            logger.debug("计算要素--tName:" + tName + "  tValue:" + tValue);
            tPubCalculator.addBasicFactor(tName, tValue);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 将所有设置的参数加入到Calculator.addBasicFactor()中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(pCalCode);
        logger.debug("-----------------------执行免赔算法-----调用公式计算--------------------");
        tVector = tTransferData.getValueNames();
        for (int i = 0; i < tVector.size(); i++)
        {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
            //logger.debug("executeCalCode--tName:" + tName + "  tValue:" + tValue);
            tCalculator.addBasicFactor(tName, tValue);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 进行计算，Calculator.calculate()
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tStr = "";
        logger.debug("CalSQL=" + tCalculator.getCalSQL());
        tStr = tCalculator.calculate();

        logger.debug("----------结束---------执行免赔算法-------------------------------------");
        if (tStr.trim().equals(""))
        {
            rValue = 0;
        }
        else
        {
            rValue = Double.parseDouble(tStr);
        }

        return rValue;

    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－结束－－－－－－－进行免赔计算－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {

        mInputData.clear();
        mInputData.add(mMMap);
        return true;
    }


    private boolean pubSubmit()
    {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalFranchiseBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }



    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args)
    {

        GlobalInput tG = new GlobalInput();

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo","80000000630");
        tTransferData.setNameAndValue("AccDate","2005-05-30");
        tTransferData.setNameAndValue("ClmNo","90000000690");
        tTransferData.setNameAndValue("ContType","1");


        VData tVData = new VData();

        tVData.add( tG );
        tVData.add( tTransferData );

        LLClaimCalFranchiseBL tLLClaimCalFranchiseBL = new LLClaimCalFranchiseBL();

        String tContent = "";
        if (tLLClaimCalFranchiseBL.submitData(tVData,"") == false)
        {
            int n = tLLClaimCalFranchiseBL.mErrors.getErrorCount();

            for (int i = 0; i < n; i++)
            {
                tContent = tContent + tLLClaimCalFranchiseBL.mErrors.getError(i).errorMessage;

            }
        }

    }

}
