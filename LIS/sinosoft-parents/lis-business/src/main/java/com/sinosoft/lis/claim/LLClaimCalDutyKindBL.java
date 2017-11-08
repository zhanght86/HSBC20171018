package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LLAppealDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDutyKindDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAppealSchema;
import com.sinosoft.lis.schema.LLClaimDutyKindSchema;
import com.sinosoft.lis.schema.LLClaimPolicySchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.vschema.LLAppealSet;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:理算步骤七，计算理赔类型赔付
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalDutyKindBL {
private static Logger logger = Logger.getLogger(LLClaimCalDutyKindBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	// private String mAccNo = ""; //事件号
	// private String mAccDate = ""; //意外事故发生日期
	private String mClmNo = ""; // 赔案号
	// private String mCusNo = ""; //客户号
	// private String mContType = ""; //总单类型,1-个人总投保单,2-集体总单
	// private String mGetDutyKind; //理赔类型
	// private String mGetDutyCode; //责任编码

	private double m_Sum_ClaimFee = 0; /* 赔案给付金额 */
	private double m_Sum_JFFee = 0; /* 赔案拒付金额 */

	public LLClaimCalDutyKindBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理算步骤七-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试-----开始----------");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------理算步骤七-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mAccNo = (String) mTransferData.getValueByName("AccNo"); //事件号
		// this.mAccDate = (String) mTransferData.getValueByName("AccDate");
		// //意外事故发生日期
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号

		return true;
	}

	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 先删除已经计算过的理赔类型信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String strSQL1 = "delete from LLClaimDutyKind where ClmNo='"
				+ "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL1);
		sqlbv.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv, "DELETE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 计算后，根据应给付金额，分割后金额，剩余有效保额， 确定医疗类理赔类型最后应赔付的金额 对理赔类型进行循环
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select getdutykind,count(*) from LLClaimDetail where 1 =1 "
				+ " and clmno ='" + "?clmno?" + "' group by getdutykind";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("clmno", this.mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String tGetDutyKind = tSSRS.GetText(i, 1);

			// 根据理赔类型分类统计
			if (!getLLClaimDutyKind(tGetDutyKind)) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 根据保项的给付，拒付标志，更新保单上的给付金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLLClaimPolicy()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 更新赔案的总金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLLClaim()) {
			return false;
		}

		return true;
	}

	/**
	 * 目的：根据理赔类型进行统计
	 * 
	 */

	private boolean getLLClaimDutyKind(String pClaimType) {

		// 减去自费/自付金额后的参与理算的帐单金额
		double t_Sum_TabFee = 0;
		t_Sum_TabFee = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_TabFee));

		// //调整后的金额
		// double t_Sum_AdjFee = 0;
		// t_Sum_AdjFee = Double.parseDouble(new
		// DecimalFormat("0.00").format(t_Sum_AdjFee));

		// 类似225不参与计算的产品金额
		double t_Sum_SpeciPay = 0;
		t_Sum_SpeciPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_SpeciPay));

		// 计算出来的理算金额
		double t_Sum_StandPay = 0;
		t_Sum_StandPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_StandPay));

		// 最后赔付的金额
		double t_Sum_RealPay = 0;
		t_Sum_RealPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_RealPay));

//		// 临时金额
//		double t_Sum_TempPay = 0;
//		t_Sum_TempPay = Double.parseDouble(new DecimalFormat("0.00")
//				.format(t_Sum_TempPay));


		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		LLClaimDutyKindSet tLLClaimDutyKindSaveSet = new LLClaimDutyKindSet();
		LDExch tLDExch = new LDExch();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 在如果LLClaimDutyKind判断是否有该理赔类型的数据 没有则新建一个Schema存放数据，有则获取原先的Schema
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDutyKindDB tLLClaimDutyKindDB = new LLClaimDutyKindDB();
		tLLClaimDutyKindDB.setClmNo(this.mClmNo);
		tLLClaimDutyKindDB.setGetDutyKind(pClaimType);
		LLClaimDutyKindSet tLLClaimDutyKindSet = tLLClaimDutyKindDB.query();

		LLClaimDutyKindSchema tLLClaimDutyKindSchema = null;

		if (tLLClaimDutyKindSet == null || tLLClaimDutyKindSet.size() == 0) {
			tLLClaimDutyKindSchema = new LLClaimDutyKindSchema();
			tLLClaimDutyKindSchema.setClmNo(this.mClmNo);
			tLLClaimDutyKindSchema.setGetDutyKind(pClaimType);
			tLLClaimDutyKindSchema.setCurrency((String) mTransferData.getValueByName("sumCurrency"));
			
		} else {
			tLLClaimDutyKindSchema = tLLClaimDutyKindSet.get(1);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
		 * No3.0 找出理赔类型--医疗类下的扣除自费/自付金额后的帐单金额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (pClaimType.equals("100") || pClaimType.equals("200")) {

			LLAppealSchema tLLAppealSchema = getLLAppeal();
			if (tLLAppealSchema == null) {

				tSql = "select currency,sum(K1) 账单金额,sum(K2) 自费金额 from ("
	    			   +"select currency,(case when Fee is null then 0 else Fee end) K1,(case when SelfAmnt is null then 0 else SelfAmnt end)K2 from LLCaseReceipt where 1 = 1 "
					   + " and ClmNo ='" + "?ClmNo?" + "'"					   
					   +" union"
					   +" select currency,(case when AdjSum is null then 0 else AdjSum end) K1, (case when SelfAmnt is null then 0 else SelfAmnt end) k2 from LLOtherFactor where 1 = 1"
					   + " and Feeitemtype='T' and ClmNo ='" + "?ClmNo?" + "'"
					   +" ) g"
					   + " group by currency ";	
			} 
			else {
				//String tOldClmNO = StrTool.cTrim(tLLAppealSchema.getCaseNo());
				
				tSql = "select currency,sum(K1) 账单金额,sum(K2) 自费金额 from ("
	    			   +"select currency,(case when Fee is null then 0 else Fee end) K1,(case when SelfAmnt is null then 0 else SelfAmnt end) K2 from LLCaseReceipt where 1 = 1 "
					   + " and ClmNo ='" + "?ClmNo?" + "'"
					   +" union"
					   +" select currency,(case when AdjSum is null then 0 else AdjSum end) K1, (case when SelfAmnt is  null then 0 else SelfAmnt end) k2 from LLOtherFactor where 1 = 1"
					   + " and Feeitemtype='T' and ClmNo ='" + "?OldClmNo?" + "'"
					   +" ) g"
					   + " group by currency ";
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("ClmNo", this.mClmNo);
			if (tLLAppealSchema != null) {
			sqlbv2.put("OldClmNo", StrTool.cTrim(tLLAppealSchema.getCaseNo()));
			}
			logger.debug("------------------------------------------------------");
			logger.debug("--计算理赔类型下的帐单金额,自费/自付金额:" + tSql);
			logger.debug("------------------------------------------------------");

			tSSRS=tExeSQL.execSQL(sqlbv2);
			
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "计算帐单金额,自费/自付金额发生错误,"+tExeSQL.mErrors.getLastError());
				return false;
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

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
		 * No4.0 计算保项层面的数据，LLClaimDetail赔付明细， 根据结论为给付,根据调整后的金额，也就是核赔赔付金额RealPay
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select currency,(case when sum(RealPay) is null then 0 else sum(RealPay) end) from LLClaimDetail where 1 = 1 "
				+ " and ClmNo = '" + "?ClmNo?" + "'" + " and GetDutyKind = '"
				+ "?GetDutyKind?" + "'" + " and GiveType = '0'"
				+" group by currency ";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("ClmNo", this.mClmNo);
		sqlbv4.put("GetDutyKind", pClaimType);
		logger.debug("------------------------------------------------------");
		logger.debug("--计算理赔类型下的给付金额:" + tSql);
		logger.debug("------------------------------------------------------");

		tSSRS=tExeSQL.execSQL(sqlbv4);		
		
		//String tCal = StrTool.cTrim(tExeSQL.getOneValue(tSql));

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算理赔类型下的帐单金额发生错误,"+tExeSQL.mErrors.getLastError());
			return false;
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

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
		 * No5.0 找出该理赔类型下类似225不参与计算的产品金额 8.理赔医疗类附加险单独理算
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

//		if (pClaimType.equals("100") || pClaimType.equals("200")) {
//			tSql = "select nvl(sum(RealPay),0) from LLClaimDetail where 1 = 1 "
//					+ " and ClmNo = '"
//					+ this.mClmNo
//					+ "'"
//					+ " and GiveType = '0'"
//					+ " and RiskCode in (select riskcode from LMRiskSort where 1=1 and RiskSortType='8' )";
//
//			System.out
//					.println("------------------------------------------------------");
//			logger.debug("--计算医疗类单独理算特殊险种理赔类型下金额:" + tSql);
//			System.out
//					.println("------------------------------------------------------");
//
//			tExeSQL = new ExeSQL();
//			String tFee = StrTool.cTrim(tExeSQL.getOneValue(tSql));
//			if (tExeSQL.mErrors.needDealError()) {
//				CError.buildErr(this, "计算医疗类单独理算特殊险种理赔类型下金额发生错误,"+tExeSQL.mErrors.getLastError());
//				return false;
//			}
//
//			if (tFee != null && tFee.length() > 0 && !tFee.equals("")) {
//				t_Sum_SpeciPay = Double.parseDouble(tFee);
//			}
//		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 根据帐单金额，理算金额，相比较，找出最小的结果 也就是核算赔付金额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		if (pClaimType.equals("100") || pClaimType.equals("200")) {
//			
//			t_Sum_StandPay = t_Sum_StandPay - t_Sum_SpeciPay;
//			if (t_Sum_StandPay > t_Sum_TabFee) 
//			{
//				t_Sum_RealPay = t_Sum_TabFee;
//			} 
//			else {
//				t_Sum_RealPay = t_Sum_StandPay;
//			}
//		} 
//		else {
//			t_Sum_RealPay = t_Sum_StandPay;
//		}
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 2009-03-06 zhangzheng 医疗类理算出的金额不可能超过账单金额,在理算算法中已经控制,
		 * 而且如果只有津贴型的险种,账单金额可以为0,但理算出的结果有可能不为0,这里不用特殊处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		t_Sum_RealPay = t_Sum_StandPay;

		/* 为帐单金额,和保项层面核算赔付金额的最小值 加上 医疗类特殊附加险(类似225) */
		tLLClaimDutyKindSchema.setClaimMoney(t_Sum_RealPay + t_Sum_SpeciPay);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 
		 * 找出社保给付D001 ,第三方给付D002
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (pClaimType.equals("100") || pClaimType.equals("200")) {
			
			tSql = "select currency,FactorCode,(case when sum(FactorValue) is null then 0 else sum(FactorValue) end)"
					/*+ " nvl(sum(case FactorCode  when 'D001' then FactorValue end),0),"
					+ " nvl(sum(case FactorCode  when 'D002' then FactorValue end),0) "*/
					+ " from LLOtherFactor  where 1 = 1 " + " and Feeitemtype='D' and ClmNo ='"
					+ "?ClmNo?" + "'"
					+ " group by currency,FactorCode";

			logger.debug("------------------------------------------------------");
			logger.debug("--计算社保给付,第三方给付:" + tSql);
			logger.debug("------------------------------------------------------");
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("ClmNo", this.mClmNo);
			tSSRS = tExeSQL.execSQL(sqlbv5);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "计算社保给付,第三方给付发生错误,"+tExeSQL.mErrors.getLastError());
				return false;
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


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0 
		 * 核算赔付金额＋ 类似225不参与计算的产品金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		t_Sum_RealPay = t_Sum_RealPay + t_Sum_SpeciPay;


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.0 理赔类型的最后给付金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (t_Sum_RealPay < 0) {
			t_Sum_RealPay = 0;
		}
		tLLClaimDutyKindSchema.setRealPay(t_Sum_RealPay);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.0 汇总整个赔案层面的给付金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		m_Sum_ClaimFee = m_Sum_ClaimFee + t_Sum_RealPay;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.0 汇总整个赔案理赔类型层面的拒付金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select currency,a.GiveType,(case when count(a.RealPay) is null then 0 else count(a.RealPay) end),(case when sum(a.RealPay) is null then 0 else sum(a.RealPay) end),(case when count(a.DeclineAmnt) is null then 0 else count(a.DeclineAmnt) end),(case when sum(a.DeclineAmnt) is null then 0 else sum(a.DeclineAmnt) end) "
				/*+ " nvl(count(case a.GiveType  when '0' then a.RealPay end),0),"
				+ " nvl(sum(case a.GiveType  when '0' then a.RealPay end),0),"
				+ " nvl(count(case a.GiveType  when '1' then a.DeclineAmnt end),0),"
				+ " nvl(sum(case a.GiveType  when '1' then a.DeclineAmnt end),0) "*/
				+ " from LLClaimDetail a where 1 = 1 " + " and ClmNo = '"
				+ "?ClmNo?" + "'" + " and GetDutyKind = '" + "?GetDutyKind?" + "'"
				+ " group by currency,a.GiveType";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("ClmNo", this.mClmNo);
		sqlbv6.put("GetDutyKind", pClaimType);
		logger.debug("------------------------------------------------------");
		logger.debug("--计算理赔类型下给付、拒付金额:" + tSql);
		logger.debug("------------------------------------------------------");

		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv6);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算理赔类型下给付、拒付金额发生错误,"+tExeSQL.mErrors.getLastError());		
			return false;
		}

		/*if (tSSRS.getMaxRow() == 1) {
			double tGfs = Double.parseDouble(tSSRS.GetText(1, 1)); // 给付数
			double tGfje = Double.parseDouble(tSSRS.GetText(1, 2)); // 给付金额
			double tJfs = Double.parseDouble(tSSRS.GetText(1, 3)); // 拒付数
			double tJfje = Double.parseDouble(tSSRS.GetText(1, 4)); // 拒付金额

			tLLClaimDutyKindSchema.setDeclinePay(tJfje); // 该理赔类型下的拒付金额
			m_Sum_JFFee = m_Sum_JFFee + tJfje;
		}*/
		double tGfs = 0; // 给付数
		double tGfje = 0; // 给付金额
		double tJfs = 0; // 拒付数
		double tJfje = 0; // 拒付金额
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
		tLLClaimDutyKindSchema.setDeclinePay(tJfje); // 该理赔类型下的拒付金额
		m_Sum_JFFee = m_Sum_JFFee + tJfje;

		tLLClaimDutyKindSaveSet.add(tLLClaimDutyKindSchema);
		mMMap.put(tLLClaimDutyKindSaveSet, "INSERT");
		return true;
	}

	/**
	 * 根据保项的给付，拒付标志更新保单明细的给付状态，实际赔付金额
	 * 
	 * @return
	 */
	private boolean getLLClaimPolicy() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询所有保单下的理赔类型信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimPolicySet tLLClaimPolicySaveSet = new LLClaimPolicySet();
		LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		tLLClaimPolicyDB.setClmNo(this.mClmNo);
		LLClaimPolicySet tLLClaimPolicySet = tLLClaimPolicyDB.query();
		if (tLLClaimPolicySet == null || tLLClaimPolicySet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "没有查询到赔案保单信息");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据保单号,理赔类型在保项表查找所有的给付,拒付信息 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		LLClaimPolicySchema tLLClaimPolicySchema = null;
		for (int i = 1; i <= tLLClaimPolicySet.size(); i++) {
			tLLClaimPolicySchema = tLLClaimPolicySet.get(i);

			tSql = "select "
					+ " (case when count(case a.GiveType  when '0' then a.RealPay end) is null then 0 else count(case a.GiveType  when '0' then a.RealPay end) end),"
					+ " (case when sum(case a.GiveType  when '0' then a.RealPay end) is null then 0 else sum(case a.GiveType  when '0' then a.RealPay end) end),"
					+ " (case when count(case a.GiveType  when '1' then a.DeclineAmnt end) is null then 0 else count(case a.GiveType  when '1' then a.DeclineAmnt end) end),"
					+ " (case when sum(case a.GiveType  when '1' then a.DeclineAmnt end) is null then 0 else sum(case a.GiveType  when '1' then a.DeclineAmnt end) end), "
					+ " (case when count(case a.GiveType  when '2' then a.RealPay end) is null then 0 else count(case a.GiveType  when '2' then a.RealPay end) end) "
					+ " from LLClaimDetail a where 1 = 1 " + " and ClmNo = '"
					+ "?ClmNo?" + "'" + " and PolNo = '"
					+ "?PolNo?" + "'"
					+ " and GetDutyKind = '"
					+ "?GetDutyKind?" + "'";

			logger.debug("------------------------------------------------------");
			logger.debug("--计算保单理赔类型下给付，拒付金额：" + tSql);
			logger.debug("------------------------------------------------------");
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSql);
			sqlbv7.put("ClmNo", this.mClmNo);
			sqlbv7.put("PolNo", tLLClaimPolicySchema.getPolNo());
			sqlbv7.put("GetDutyKind", tLLClaimPolicySchema.getGetDutyKind());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv7);
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
			} else {
				// @@错误处理
				CError.buildErr(this, "计算保单理赔类型下给付，拒付金额发生错误,"+tExeSQL.mErrors.getLastError());
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 确定整个赔案的赔付金额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		mMMap.put(tLLClaimPolicySaveSet, "UPDATE");
		return true;
	}

	/**
	 * 更新理赔类型层面的结论，金额
	 * 
	 * @return
	 */
	private boolean getLLClaim() {

		// 理赔--赔案信息
		LLClaimSchema tLLClaimSchema = new LLClaimSchema();
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取赔案的信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(this.mClmNo);
		if (tLLClaimDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this, "没有查询到赔案信息");
			return false;
		} else {
			tLLClaimSchema.setSchema(tLLClaimDB.getSchema());
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 判断赔案的给付，拒付信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select "
				+ " (case when count(case a.GiveType when '0' then a.RealPay end) is null then 0 else count(case a.GiveType  when '0' then a.RealPay end) end),"
				+ " (case when sum(case a.GiveType when '0' then a.RealPay end) is null then 0 else sum(case a.GiveType  when '0' then a.RealPay end) end),"
				+ " (case when count(case a.GiveType when '1' then a.DeclineAmnt end) is null then 0 else count(case a.GiveType  when '1' then a.DeclineAmnt end) end),"
				+ " (case when sum(case a.GiveType when '1' then a.DeclineAmnt end) is null then 0 else sum(case a.GiveType  when '1' then a.DeclineAmnt end) end), "
				+ " (case when count(case a.GiveType when '2' then a.RealPay end) is null then 0 else count(case a.GiveType  when '2' then a.RealPay end) end) "
				+ " from LLClaimDetail a where 1 = 1 " + " and ClmNo = '"
				+ "?ClmNo?" + "'";
		
		logger.debug("------------------------------------------------------");
		logger.debug("--计算该赔案下的给付，拒付信息：" + tSql);
		logger.debug("------------------------------------------------------");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("ClmNo", this.mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv8);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算该赔案下的给付，拒付信息发生错误,"+tExeSQL.mErrors.getLastError());
			return false;
		}

		if (tSSRS.getMaxRow() == 1) {
			double tGfs = Double.parseDouble(tSSRS.GetText(1, 1)); // 给付数
			double tGfje = Double.parseDouble(tSSRS.GetText(1, 2)); // 给付金额
			double tJfs = Double.parseDouble(tSSRS.GetText(1, 3)); // 拒付数
			double tJfje = Double.parseDouble(tSSRS.GetText(1, 4)); // 拒付金额
			double tWgl = Double.parseDouble(tSSRS.GetText(1, 5)); // 无归类,标志为2的数据

			// 给付保项数为0,拒付数大于0,不赔保项数也为0,也就是说所有的保项为拒付,或不赔付
			if (tGfs == 0 && tWgl == 0) {
				tLLClaimSchema.setGiveType("1"); // 将保单的给付标志改为拒付1
			}

			// 给付保项数大于0,也就是说有的保项为给付的情况
			else if (tGfs > 0) {
				tLLClaimSchema.setGiveType("0"); // 将保单的给付标志改为给付0
			}
			// 给付保项数为0,无规类大于0
			else if (tGfs == 0 && tJfs == 0 && tWgl > 0) {
				tLLClaimSchema.setGiveType("2"); // 将保单的给付标志改为无规类
			}

		} else {
			// @@错误处理
			CError.buildErr(this, "查询到赔案保项信息不唯一");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 更新赔案层面的金额信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 赔案层面的汇总金额
		m_Sum_ClaimFee = Double.parseDouble(new DecimalFormat("0.00")
				.format(m_Sum_ClaimFee));
		tLLClaimSchema.setStandPay(m_Sum_ClaimFee); /* 该赔案下的给付金额 */
		tLLClaimSchema.setDeclinePay(m_Sum_JFFee); /* 该赔案下的拒付金额 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 更新赔案的总金额 赔案赔付金额
		 * 实际赔付金额 = 赔案赔付金额 - 预付金额 + 结算信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		m_Sum_ClaimFee = tLLClaimSchema.getStandPay()
				//- tLLClaimSchema.getBeforePay()
				+ tLLClaimSchema.getBalancePay();
		tLLClaimSchema.setRealPay(m_Sum_ClaimFee);
		mMMap.put(tLLClaimSchema, "UPDATE");

		return true;
	}

	/**
	 * 获取申述数据
	 * 
	 * @return
	 */
	private LLAppealSchema getLLAppeal() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该案件是否是申诉案件
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select * from LLAppeal where 1=1 " + " and AppealNo = '"
				+ "?mClmNo?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("mClmNo", this.mClmNo);
		LLAppealDB tLLAppealDB = new LLAppealDB();
		LLAppealSet tLLAppealSet = tLLAppealDB.executeQuery(sqlbv9);

		if (tLLAppealDB.mErrors.needDealError() == true) {
			mErrors.addOneError("在申诉信息中查找到相关的数据发生错误，不能理算!!!"
					+ tLLAppealDB.mErrors.getError(0).errorMessage);
			return null;
		}

		LLAppealSchema tLLAppealSchema = new LLAppealSchema();
		if (tLLAppealSet.size() == 0) {
			return null;
		} else {
			tLLAppealSchema = tLLAppealSet.get(1);
		}
		return tLLAppealSchema;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
