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

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:理算步骤七，计算理赔类型赔付</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalDutyKindBL
{
private static Logger logger = Logger.getLogger(LLClaimCalDutyKindBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();


//    private String mAccNo    = "";     //事件号
//    private String mAccDate  = "";     //意外事故发生日期
    private String mClmNo    = "";     //赔案号
//    private String mCusNo    = "";     //客户号
//    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单
//    private String mGetDutyKind;       //理赔类型
//    private String mGetDutyCode;       //责任编码


    //  理赔--赔案信息
    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LLClaimPolicySet   mLLClaimPolicySet   = new LLClaimPolicySet();



    private double m_Sum_ClaimFee = 0;          /*赔案给付金额*/
    private double m_Sum_JFFee = 0;             /*赔案拒付金额*/


    public LLClaimCalDutyKindBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {


        logger.debug("----------理算步骤七-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试-----开始----------");
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


        logger.debug("----------理算步骤七-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试-----结束----------");
        return true;
    }



    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {

        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
//        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
//      this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号

        return true;
    }




    private boolean dealData()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 先删除已经计算过的信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String strSQL1 = "delete from LLClaimDutyKind where ClmNo='" + this.mClmNo + "'";
        mMMap.put(strSQL1, "DELETE");


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 计算后，根据应给付金额，分割后金额，剩余有效保额，
         *  确定医疗类理赔类型最后应赔付的金额
         *  对理赔类型进行循环
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql =
            "select getdutykind,count(*) from lltoclaimduty where 1 =1 "
            + " and caseno='" + this.mClmNo + "' group by getdutykind";


        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String tgetdutykind = tSSRS.GetText(i, 1);         //
            calDutyKind(tgetdutykind);                         //根据理赔类型分类统计
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据保项的给付，拒付标志，更新保单上的给付金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if ( !getLLClaimPolicy() )
        {
            return false;
        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 更新赔案的总金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        m_Sum_ClaimFee = Double.parseDouble(new DecimalFormat("0.00").format(m_Sum_ClaimFee));

        LLClaimDB tLLClaimDB = new LLClaimDB();
        tLLClaimDB.setClmNo(this.mClmNo);
        if ( !tLLClaimDB.getInfo() )
        {
            // @@错误处理
            CError tError =new CError();
            tError.moduleName="LLClaimCalDutyKindBL";
            tError.functionName="getInfo";
            tError.errorMessage="没有查询到赔案信息";
            this.mErrors .addOneError(tError) ;
            return false;
        }
        else
        {
            mLLClaimSchema.setSchema(tLLClaimDB.getSchema());
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 更新赔案的总金额
         *  赔案赔付金额
         *  实际赔付金额 = 赔案赔付金额 - 预付金额 + 结算信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        mLLClaimSchema.setStandPay(m_Sum_ClaimFee);       /*该赔案下的给付金额*/
        mLLClaimSchema.setDeclinePay(m_Sum_JFFee);        /*该赔案下的拒付金额*/


        m_Sum_ClaimFee = mLLClaimSchema.getStandPay() 
        //- mLLClaimSchema.getBeforePay() 
        + mLLClaimSchema.getBalancePay();
        mLLClaimSchema.setRealPay(m_Sum_ClaimFee);
        mMMap.put(mLLClaimSchema, "UPDATE");

        return true;
    }


    /**
     * 目的：根据理赔类型进行统计
     *
     */

    private boolean calDutyKind(String pClaimType)
    {

		// 减去自费/自付金额后的参与理算的帐单金额
		double t_Sum_TabFee = 0;
		t_Sum_TabFee = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_TabFee));

//        //调整后的金额
//        double t_Sum_AdjFee = 0;
//        t_Sum_AdjFee = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_AdjFee));

        //类似225不参与计算的产品金额
        double t_Sum_SpeciPay = 0;
        t_Sum_SpeciPay = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_SpeciPay));

        //计算出来的理算金额
        double t_Sum_StandPay = 0;
        t_Sum_StandPay = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_StandPay));
        

        //最后赔付的金额
        double t_Sum_RealPay = 0;
        t_Sum_RealPay = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_RealPay));

//        //临时金额
//        double t_Sum_TempPay = 0;
//        t_Sum_TempPay = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_TempPay));

        String tSql = "";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        
        LLClaimDutyKindSet mLLClaimDutyKindSet = new LLClaimDutyKindSet();
        LDExch tLDExch = new LDExch();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 在如果LLClaimDutyKind判断是否有该理赔类型的数据
         *  没有则新建一个Schema存放数据，有则获取原先的Schema
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimDutyKindDB tLLClaimDutyKindDB = new LLClaimDutyKindDB();
        tLLClaimDutyKindDB.setClmNo(this.mClmNo);
        tLLClaimDutyKindDB.setGetDutyKind(pClaimType);
        LLClaimDutyKindSet tLLClaimDutyKindSet= tLLClaimDutyKindDB.query();

        LLClaimDutyKindSchema tLLClaimDutyKindSchema = null;

        if(tLLClaimDutyKindSet == null || tLLClaimDutyKindSet.size() == 0 )
        {
            tLLClaimDutyKindSchema = new LLClaimDutyKindSchema();
            tLLClaimDutyKindSchema.setClmNo(this.mClmNo);
            tLLClaimDutyKindSchema.setGetDutyKind(pClaimType);
            tLLClaimDutyKindSchema.setCurrency((String) mTransferData.getValueByName("sumCurrency"));
        }
        else
        {
            tLLClaimDutyKindSchema = tLLClaimDutyKindSet.get(1);
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 找出理赔类型--医疗类下的扣除自费/自付金额后的帐单金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        if (pClaimType.equals("100") || pClaimType.equals("200"))
        {
    		tSql = "select currency,sum(K1) 账单金额,sum(K2) 自费金额 from ("
    			   +"select currency,nvl(Fee,0) K1,nvl(SelfAmnt,0)K2 from LLCaseReceipt where 1 = 1 "
				   + " and ClmNo ='" + this.mClmNo + "'"
				   +" union"
				   +" select currency,nvl(AdjSum, 0) K1, nvl(SelfAmnt, 0) k2 from LLOtherFactor where 1 = 1 "
				   + " and Feeitemtype='T' and ClmNo ='" + this.mClmNo + "'"
				   +" )"
				   + " group by currency ";

    		logger.debug("------------------------------------------------------");
    		logger.debug("--计算理赔类型下的帐单金额,自费/自付金额:" + tSql);
    		logger.debug("------------------------------------------------------");

    		tSSRS=tExeSQL.execSQL(tSql);
    		
            if (tExeSQL.mErrors.needDealError())
            {
                CError.buildErr(this, "计算理赔类型下的帐单金额发生错误!");

                logger.debug("------------------------------------------------------");
                logger.debug("--LLClaimCalDutyKindBL.calDutyKind()--计算理赔类型下的帐单金额发生错误!"+tSql);
                logger.debug("------------------------------------------------------");
            }
            
            double ttmoney =0;
			double zzmoney =0;
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				if(tSSRS.GetText(i,1).equals(tLLClaimDutyKindSchema.getCurrency()))
					ttmoney = ttmoney +Double.parseDouble(tSSRS.GetText(i, 2));
				else
					ttmoney = ttmoney +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i, 2)));
				
				if(tSSRS.GetText(1,1).equals(tLLClaimDutyKindSchema.getCurrency()))
					zzmoney = zzmoney +Double.parseDouble(tSSRS.GetText(i, 3));
				else
					zzmoney = zzmoney +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i, 3)));
			}

			//账单金额
			tLLClaimDutyKindSchema.setTabFeeMoney(ttmoney);
				
			//自费/自付金额
			tLLClaimDutyKindSchema.setSelfAmnt(zzmoney);
			
			t_Sum_TabFee = tLLClaimDutyKindSchema.getTabFeeMoney()-tLLClaimDutyKindSchema.getSelfAmnt();
			
			logger.debug("案件:"+tLLClaimDutyKindSchema.getClmNo()+",账单金额:"+tLLClaimDutyKindSchema.getTabFeeMoney()
					           +",自费自付金额:"+tLLClaimDutyKindSchema.getSelfAmnt()+",参与计算的账单金额:"+t_Sum_TabFee);
			
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 计算保项层面的数据，LLClaimDetail赔付明细，
         *  根据结论为给付,根据调整后的金额，也就是核赔赔付金额RealPay
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select currency,nvl(sum(RealPay),0) from LLClaimDetail where 1 = 1 "
            +" and ClmNo = '"+ this.mClmNo +"'"
            +" and GetDutyKind = '"+ pClaimType +"'" 
            +" and GiveType = '0' "
            +" group by currency ";

        //String tCal = tExeSQL.getOneValue(tSql);
        tSSRS=tExeSQL.execSQL(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "计算理赔类型下的给付单金额发生错误!");
 
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimCalDutyKindBL.calDutyKind()--计算理赔类型下的给付金额发生错误!"+tSql);
            logger.debug("------------------------------------------------------");

        }        
        
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			if(tSSRS.GetText(i,1).equals(tLLClaimDutyKindSchema.getCurrency()))
				t_Sum_StandPay = t_Sum_StandPay +Double.parseDouble(tSSRS.GetText(i, 2));
			else
				t_Sum_StandPay = t_Sum_StandPay +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i, 2)));
		}

        //t_Sum_StandPay = Double.parseDouble(tCal);
        tLLClaimDutyKindSchema.setStandPay(t_Sum_StandPay);

//
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * No5.0 找出该理赔类型下类似225不参与计算的产品金额
//         * 8.理赔医疗类附加险单独理算
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//
//        if (pClaimType.equals("100") || pClaimType.equals("200"))
//        {
//            tSql = "select nvl(sum(RealPay),0) from LLClaimDetail where 1 = 1 "
//                + " and ClmNo = '"+this.mClmNo+"'"
//                 +" and GiveType = '0'"
//                + " and RiskCode in (select riskcode from LMRiskSort where 1=1 and RiskSortType='8' )";
//
//
//            tExeSQL = new ExeSQL();
//            String tFee = tExeSQL.getOneValue(tSql);
//            if (tExeSQL.mErrors.needDealError())
//            {
//                this.mErrors.copyAllErrors(tExeSQL.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "LLClaimCalAutoBL";
//                tError.functionName = "calDutyKind";
//                tError.errorMessage = "计算医疗类单独理算特殊险种理赔类型下金额发生错误!";
//                logger.debug("------------------------------------------------------");
//                logger.debug("--LLClaimCalDutyKindBL.calDutyKind()--计算医疗类单独理算特殊险种理赔类型下金额发生错误!"+tSql);
//                logger.debug("------------------------------------------------------");
//                this.mErrors.addOneError(tError);
//            }
//
//            if ( tFee!=null && tFee.length()>0 && !tFee.equals(""))
//            {
//                t_Sum_SpeciPay = Double.parseDouble(tFee);
//            }
//        }

        /**
         * 2009-05-08 zhangzheng
         * 医疗类理算出的金额不可能超过账单金额,在理算算法中已经控制,
		 * 而且如果只有津贴型的险种,账单金额可以为0,但理算出的结果有可能不为0,这里不用特殊处理
         */
        

//        if (pClaimType.equals("100") || pClaimType.equals("200"))
//        {
//            t_Sum_StandPay = t_Sum_StandPay - t_Sum_SpeciPay;
//            
//            if (t_Sum_StandPay > t_Sum_TabFee)
//            {
//                t_Sum_RealPay = t_Sum_TabFee;
//            }
//            else
//            {
//                t_Sum_RealPay = t_Sum_StandPay;
//            }
//            
//        }
//        else
//        {
//            t_Sum_RealPay = t_Sum_StandPay;
//        }


		t_Sum_RealPay = t_Sum_StandPay;

        /*为帐单金额,和保项层面核算赔付金额的最小值 加上 医疗类特殊附加险(类似225)*/
        tLLClaimDutyKindSchema.setClaimMoney(t_Sum_RealPay + t_Sum_SpeciPay);
        
        

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No7.0 找出社保给付B001 , 第三方给付B002
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        if (pClaimType.equals("100") || pClaimType.equals("200"))
        {
            tSql = "select currency,FactorCode,nvl(sum(FactorValue),0)"
                /*+" nvl(sum(case FactorCode  when 'D001' then FactorValue end),0),"
                +" nvl(sum(case FactorCode  when 'D002' then FactorValue end),0) "*/
                +" from LLOtherFactor  where 1 = 1 "
                +" and ClmNo ='" +this.mClmNo+"'"
                + " group by currency,FactorCode";


            tSSRS = tExeSQL.execSQL(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                CError.buildErr(this, "计算社保给付,第三方给付发生错误!");

                logger.debug("------------------------------------------------------");
                logger.debug("--LLClaimCalDutyKindBL.calDutyKind()--计算社保给付,第三方给付发生错误!"+tSql);
                logger.debug("------------------------------------------------------");
            }
            double tSocOtherFee1=0;
			double tSocOtherFee2=0;

			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				
				if(tSSRS.GetText(i,2).equals("D001"))
				{
					if(tSSRS.GetText(i,1).equals(tLLClaimDutyKindSchema.getCurrency()))
						tSocOtherFee1 = tSocOtherFee1 +Double.parseDouble(tSSRS.GetText(i,3));
					else
						tSocOtherFee1 = tSocOtherFee1 +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
				}				
				if(tSSRS.GetText(i,2).equals("D002"))
				{
					if(tSSRS.GetText(i,1).equals(tLLClaimDutyKindSchema.getCurrency()))
						tSocOtherFee2 = tSocOtherFee2 + Double.parseDouble(tSSRS.GetText(i,3));
					else
						tSocOtherFee2 = tSocOtherFee2 +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
				}
			}
			tLLClaimDutyKindSchema.setSocPay(tSocOtherFee1);
			tLLClaimDutyKindSchema.setOthPay(tSocOtherFee2);
			
			logger.debug("案件:"+tLLClaimDutyKindSchema.getClmNo()+"的社保给付给付金额:"+tLLClaimDutyKindSchema.getSocPay()
					+",第三方给付金额:"+tLLClaimDutyKindSchema.getOthPay());
			
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No8.0 Min 理算金额，分割后金额[原始医疗费用总额－第三方给付总额]
         *  相比较，找出最小的结果
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //泰康没有这个要求注释掉 2006-02-16 P.D
//        if (pClaimType.equals("100") || pClaimType.equals("200"))
//        {
//            t_Sum_TempPay = t_Sum_TabFee
//                - PubFun.setPrecision(tLLClaimDutyKindSchema.getSocPay(),"0.00")
//                - PubFun.setPrecision(tLLClaimDutyKindSchema.getOthPay(),"0.00")
//                ;
//
//            if (t_Sum_RealPay > t_Sum_TempPay)
//            {
//                t_Sum_RealPay = t_Sum_TempPay;
//            }
//        }

        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No9.0 核算赔付金额 － 社保给付 － 第三方给付 ＋ 类似225不参与计算的产品金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        t_Sum_RealPay = t_Sum_RealPay +  t_Sum_SpeciPay;


        if ( t_Sum_RealPay < 0)
        {
            t_Sum_RealPay = 0 ;
        }
        
        tLLClaimDutyKindSchema.setRealPay(t_Sum_RealPay);

        mLLClaimDutyKindSet.add(tLLClaimDutyKindSchema);


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No10.0 汇总整个赔案层面的金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        m_Sum_ClaimFee = m_Sum_ClaimFee + t_Sum_RealPay;


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No11.0 汇总整个赔案层面的拒付金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select currency,a.GiveType,nvl(count(a.RealPay),0),nvl(sum(a.RealPay),0),nvl(count(a.DeclineAmnt),0),nvl(sum(a.DeclineAmnt),0) "
            /*+ " nvl(count(case a.GiveType  when '0' then a.RealPay end),0),"
            + " nvl(sum(case a.GiveType  when '0' then a.RealPay end),0),"
            + " nvl(count(case a.GiveType  when '1' then a.DeclineAmnt end),0),"
            + " nvl(sum(case a.GiveType  when '1' then a.DeclineAmnt end),0) "*/
            + " from LLClaimDetail a where 1 = 1 "
            + " and ClmNo = '"+this.mClmNo+"'"
            + " and GetDutyKind = '"+pClaimType+"'"
            + " group by currency,a.GiveType";

        tExeSQL = new ExeSQL();
        tSSRS = tExeSQL.execSQL(tSql);
        if (tExeSQL.mErrors.needDealError())
        {

            CError.buildErr(this, "计算理赔类型下拒付金额发生错误!");

            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimCalDutyKindBL.calDutyKind()--计算理赔类型下拒付金额发生错误!"+tSql);
            logger.debug("------------------------------------------------------");

        }

        
        /*if ( tSSRS.getMaxRow() == 1)
        {
            double  tGfs = Double.parseDouble(tSSRS.GetText(1,1));          //给付数
            double  tGfje = Double.parseDouble(tSSRS.GetText(1,2));         //给付金额
            double  tJfs = Double.parseDouble(tSSRS.GetText(1,3));          //拒付数
            double  tJfje = Double.parseDouble(tSSRS.GetText(1,4));         //拒付金额


            tLLClaimDutyKindSchema.setDeclinePay(tJfje);        //该理赔类型下的拒付金额
            m_Sum_JFFee = m_Sum_JFFee + tJfje;
        }*/
        double tGfs = 0; //给付数
		double tGfje = 0; //给付金额
		double tJfs = 0; //拒付数
		double tJfje = 0; //拒付金额
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			
			if(tSSRS.GetText(i,2).equals("0"))
			{
				tGfs = tGfs +Double.parseDouble(tSSRS.GetText(i,3));
				if(tSSRS.GetText(i,1).equals(tLLClaimDutyKindSchema.getCurrency()))					
					tGfje = tGfje +Double.parseDouble(tSSRS.GetText(i,4));					
				else
					tGfje = tGfje +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,4)));					
			}				
			if(tSSRS.GetText(i,2).equals("1"))
			{
				tJfs = tJfs +Double.parseDouble(tSSRS.GetText(i,5));
				if(tSSRS.GetText(i,1).equals(tLLClaimDutyKindSchema.getCurrency()))					
					tJfje = tJfje +Double.parseDouble(tSSRS.GetText(i,6));					
				else
					tJfje = tJfje +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimDutyKindSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,6)));
			}
		}
		tLLClaimDutyKindSchema.setDeclinePay(tJfje); //该理赔类型下的拒付金额
		m_Sum_JFFee = m_Sum_JFFee + tJfje;

        mMMap.put(mLLClaimDutyKindSet, "INSERT");

        mLLClaimSchema.setStandPay(t_Sum_StandPay);       /*该赔案下的理算总金额*/

        return true;
    }



    /**
     * 根据保项的给付，拒付标志，更新理赔类型上的给付,拒付金额
     * @return
     */
    private boolean getLLClaimDutyKind()
    {


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询该赔案下的所有理赔类型信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimDutyKindSet tLLClaimDutyKindSaveSet = new LLClaimDutyKindSet();
        LLClaimDutyKindDB tLLClaimDutyKindDB = new LLClaimDutyKindDB();
        tLLClaimDutyKindDB.setClmNo(this.mClmNo);
        LLClaimDutyKindSet tLLClaimDutyKindSet = tLLClaimDutyKindDB.query();

        if ( tLLClaimDutyKindSet == null || tLLClaimDutyKindSet.size() == 0 )
        {
            // @@错误处理
            CError tError =new CError();
            tError.moduleName="LLClaimCalDutyKindBL";
            tError.functionName="getLLClaimDutyKind";
            tError.errorMessage="没有查询到赔案理赔类型信息";
            this.mErrors .addOneError(tError) ;
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 根据保单号,理赔类型在保项表查找所有的给付,拒付信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        LLClaimDutyKindSchema tLLClaimDutyKindSchema = null ;
        for ( int i=1 ; i<=tLLClaimDutyKindSet.size() ; i++ )
        {
            tLLClaimDutyKindSchema = tLLClaimDutyKindSet.get(i);


            tSql = "select "
                + " nvl(count(case a.GiveType  when '0' then a.RealPay end),0),"
                + " nvl(sum(case a.GiveType  when '0' then a.RealPay end),0),"
                + " nvl(count(case a.GiveType  when '1' then a.DeclineAmnt end),0),"
                + " nvl(sum(case a.GiveType  when '1' then a.DeclineAmnt end),0) "
                + " from LLClaimDetail a where 1 = 1 "
                + " and ClmNo = '"+this.mClmNo+"'"
                + " and GetDutyKind = '"+tLLClaimDutyKindSchema.getGetDutyKind()+"'";

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(tSql);
            if (tExeSQL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "calDutyKind";
                tError.errorMessage = "计算理赔类型下金额发生错误!";
                logger.debug("------------------------------------------------------");
                logger.debug("--LLClaimCalDutyKindBL.getLLClaimDutyKind()--计算理赔类型下金额发生错误!"+tSql);
                logger.debug("------------------------------------------------------");
                this.mErrors.addOneError(tError);
            }

            if ( tSSRS.getMaxRow() == 1)
            {
                double  tGfs = Double.parseDouble(tSSRS.GetText(1,1));          //给付数
                double  tGfje = Double.parseDouble(tSSRS.GetText(1,2));         //给付金额
                double  tJfs = Double.parseDouble(tSSRS.GetText(1,3));          //拒付数
                double  tJfje = Double.parseDouble(tSSRS.GetText(1,4));         //拒付金额


                tLLClaimDutyKindSchema.setDeclinePay(tJfje);        //该理赔类型下的拒付金额
                m_Sum_JFFee = m_Sum_JFFee + tJfje;
                tLLClaimDutyKindSaveSet.add(tLLClaimDutyKindSchema);

            }
            else
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="LLClaimCalDutyKindBL";
                tError.functionName="getLLClaimPolicy";
                tError.errorMessage="查询到赔案理赔类型信息不唯一!!!";
                this.mErrors .addOneError(tError) ;
                return false;
            }
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 确定整个赔案的赔付金额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        mMMap.put(tLLClaimDutyKindSaveSet, "UPDATE");
        return true;
    }




    /**
     * 根据保项的给付，拒付标志更新保单的给付状态，实际赔付金额
     * @return
     */
    private boolean getLLClaimPolicy()
    {


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询所有保单下的理赔类型信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimPolicySet tLLClaimPolicySaveSet = new LLClaimPolicySet();
        LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
        tLLClaimPolicyDB.setClmNo(this.mClmNo);
        LLClaimPolicySet tLLClaimPolicySet = tLLClaimPolicyDB.query();
        if ( tLLClaimPolicySet == null || tLLClaimPolicySet.size() == 0 )
        {
            // @@错误处理
            CError tError =new CError();
            tError.moduleName="LLClaimCalDutyKindBL";
            tError.functionName="getLLClaimPolicy";
            tError.errorMessage="没有查询到赔案保单信息";
            this.mErrors .addOneError(tError) ;
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 根据保单号,理赔类型在保项表查找所有的给付,拒付信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        LLClaimPolicySchema tLLClaimPolicySchema = null ;
        for ( int i=1 ; i<=tLLClaimPolicySet.size() ; i++ )
        {
            tLLClaimPolicySchema = tLLClaimPolicySet.get(i);


//            tSql = "select "
//                + " nvl(count(case a.GiveType  when '0' then a.RealPay end),0),"
//                + " nvl(sum(case a.GiveType  when '0' then a.RealPay end),0),"
//                + " nvl(count(case a.GiveType  when '1' then a.RealPay end),0),"
//                + " nvl(sum(case a.GiveType  when '1' then a.RealPay end),0), "
//                + " nvl(count(case a.GiveType  when '2' then a.RealPay end),0), "
//                + " nvl(sum(case a.GiveType  when '2' then a.RealPay end),0),"
//                + " nvl(count(case a.GiveType  when '3' then a.RealPay end),0), "
//                + " nvl(sum(case a.GiveType  when '3' then a.RealPay end),0)"
//                + " from LLClaimDetail a where 1 = 1 "
//                + " and ClmNo = '"+this.mClmNo+"'"
//                + " and PolNo = '"+tLLClaimPolicySchema.getPolNo()+"'"
//                + " and GetDutyKind = '"+tLLClaimPolicySchema.getGetDutyKind()+"'";
//
//            ExeSQL tExeSQL = new ExeSQL();
//            SSRS tSSRS = tExeSQL.execSQL(tSql);
//            if (tExeSQL.mErrors.needDealError())
//            {
//                this.mErrors.copyAllErrors(tExeSQL.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "LLClaimCalAutoBL";
//                tError.functionName = "getLLClaimPolicy";
//                tError.errorMessage = "计算理赔类型下金额发生错误!";
//                this.mErrors.addOneError(tError);
//                logger.debug("------------------------------------------------------");
//                logger.debug("--LLClaimCalDutyKindBL.getLLClaimPolicy()--计算理赔类型下金额发生错误!"+tSql);
//                logger.debug("------------------------------------------------------");
//            }
//
//            if ( tSSRS.getMaxRow() == 1)
//            {
//                double  tGfs = Double.parseDouble(tSSRS.GetText(1,1));          //给付数
//                double  tGfje = Double.parseDouble(tSSRS.GetText(1,2));         //给付金额
//                double  tJfs = Double.parseDouble(tSSRS.GetText(1,3));          //拒付数
//                double  tJfje = Double.parseDouble(tSSRS.GetText(1,4));         //拒付金额
//                double  tXys = Double.parseDouble(tSSRS.GetText(1,5));          //协议赔付数
//                double  tXyje = Double.parseDouble(tSSRS.GetText(1,6));         //协议赔付金额
//                double  tTrs = Double.parseDouble(tSSRS.GetText(1,7));          //通融赔付数
//                double  tTrje = Double.parseDouble(tSSRS.GetText(1,8));         //通融赔付金额
//                //给付保项数为0,拒付数大于0,不赔保项数也为0,也就是说所有的保项为拒付,或不赔付
//                if ( tGfs == 0 && tXys == 0 && tTrs == 0)
//                {
//                    tLLClaimPolicySchema.setGiveType("1");  //将保单的给付标志改为拒付1
//                    tLLClaimPolicySchema.setRealPay("0");   //将保单的给付金额改为0,因为拒付的金额0,不改realpay，保持一致
//                }
//
//                //给付保项数大于0,也就是说有的保项为给付的情况
//                //else if ( tGfs > 0 && tGfje!=0) // liuyu-20070921  这种条件，如果调为0的话，那么不会修改llclaimpolicy的realpay，这样在权限校验时可能会过不去。
//                else if ( tGfs > 0 && tGfje>=0 )
//                {
//                    tLLClaimPolicySchema.setGiveType("0");    //将保单的给付标志改为给付0
//                    tLLClaimPolicySchema.setRealPay(tGfje);   //将保单的给付金额改为给付总金额
//                }
//                //协议赔付数大于0
//                //else if ( tXys > 0 && tXyje!=0)
//                else if ( tXys > 0 && tXyje>=0 )
//                {
//                    tLLClaimPolicySchema.setGiveType("2");
//                    //tLLClaimPolicySchema.setRealPay(tXyje);
//                }
//                //通融赔付数大于0
//                //else if ( tTrs > 0 & tTrje!=0)
//                else if ( tTrs > 0 & tTrje>=0 )
//                {
//                    tLLClaimPolicySchema.setGiveType("3");
//                    //tLLClaimPolicySchema.setRealPay(tTrje);
//                }
//
//                String polRealPay=null;
//                String polSql="select sum(realpay) from LLClaimDetail where ClmNo = '"+this.mClmNo+"' and PolNo = '"+tLLClaimPolicySchema.getPolNo()+"' ";
//                polRealPay=tExeSQL.getOneValue(polSql);
//                tLLClaimPolicySchema.setRealPay(polRealPay); // 重新置realpay，保持和detail表一直，否则修改顺序不一致会导致所得值不同。liuyu-2008-06-20
//
//                tLLClaimPolicySaveSet.add(tLLClaimPolicySchema);
//
//            }
//            else
//            {
//                // @@错误处理
//                CError tError =new CError();
//                tError.moduleName="LLClaimCalDutyKindBL";
//                tError.functionName="getLLClaimPolicy";
//                tError.errorMessage="查询到赔案保项信息不唯一!!!";
//                this.mErrors .addOneError(tError) ;
//                return false;
//            }
            
	        	tSql = "select "
					+ " nvl(count(case a.GiveType  when '0' then a.RealPay end),0),"
					+ " nvl(sum(case a.GiveType  when '0' then a.RealPay end),0),"
					+ " nvl(count(case a.GiveType  when '1' then a.DeclineAmnt end),0),"
					+ " nvl(sum(case a.GiveType  when '1' then a.DeclineAmnt end),0), "
					+ " nvl(count(case a.GiveType  when '2' then a.RealPay end),0) "
					+ " from LLClaimDetail a where 1 = 1 " + " and ClmNo = '"
					+ this.mClmNo + "'" + " and PolNo = '"
					+ tLLClaimPolicySchema.getPolNo() + "'"
					+ " and GetDutyKind = '"
					+ tLLClaimPolicySchema.getGetDutyKind() + "'";
	
			logger.debug("------------------------------------------------------");
			logger.debug("--计算保单理赔类型下给付，拒付金额：" + tSql);
			logger.debug("------------------------------------------------------");
	
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(tSql);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "计算保单理赔类型下给付，拒付金额发生错误,"+tExeSQL.mErrors.getLastError());
				return false;
			}
	
			if (tSSRS.getMaxRow() == 1) {
				double tGfs = Double.parseDouble(tSSRS.GetText(1, 1)); // 给付数
				double tGfje = Double.parseDouble(tSSRS.GetText(1, 2)); // 给付金额
				double tJfs = Double.parseDouble(tSSRS.GetText(1, 3)); // 拒付数
				double tJfje = Double.parseDouble(tSSRS.GetText(1, 4)); // 拒付金额
				double tWgl = Double.parseDouble(tSSRS.GetText(1, 5)); // 客户撤案类,标志为2的数据
	
				// 给付保项数为0,拒付数大于0,不赔保项数也为0,也就是说所有的保项为拒付,或不赔付
				if (tGfs == 0 && tWgl == 0) {
					tLLClaimPolicySchema.setGiveType("1"); // 将保单的给付标志改为拒付1
					tLLClaimPolicySchema.setRealPay("0"); // 将保单的给付金额改为0,因为拒付的金额0
				}
	
				// 给付保项数大于0,也就是说有的保项为给付的情况
				else if (tGfs > 0) {
					tLLClaimPolicySchema.setGiveType("0"); // 将保单的给付标志改为给付0
					tLLClaimPolicySchema.setRealPay(tGfje); // 将保单的给付金额改为给付总金额
				}
				// 给付保项数为0,客户撤案数大于0
				else if (tGfs == 0 && tJfs == 0 && tWgl > 0) {
					tLLClaimPolicySchema.setGiveType("2"); // 将保单的给付标志改为客户撤案类
					tLLClaimPolicySchema.setRealPay("0");
					tLLClaimPolicySchema.setStandPay("0");
				}
	
				tLLClaimPolicySaveSet.add(tLLClaimPolicySchema);
			} 
			else {
				// @@错误处理
				CError.buildErr(this, "计算保单理赔类型下给付，拒付金额发生错误,"+tExeSQL.mErrors.getLastError());
				return false;
			}
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 确定整个赔案的赔付金额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        mMMap.put(tLLClaimPolicySaveSet, "UPDATE");
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
            tError.moduleName = "LLClaimCalDutyKindBL";
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


        String tAccNo = "80000005570";
        String tClmNo = "90000001424";
        String tAccDate = "2005-09-30";
        String tCusNo = "0000497310";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("AccDate",tAccDate);
        tTransferData.setNameAndValue("CusNo",tCusNo);

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

        //检录一下
        //CaseCheckBL ccb = new CaseCheckBL();
    }
}


