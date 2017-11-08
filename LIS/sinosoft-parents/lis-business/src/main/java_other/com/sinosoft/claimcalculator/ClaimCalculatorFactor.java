/***********************************************************************
 * Module:  ClaimCalculatorFactor.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculatorFactor
 ***********************************************************************/

package com.sinosoft.claimcalculator;
import java.util.*;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.cache.CachedCalculatorTrace;
import com.sinosoft.lis.claim.LLClaimCalAutoBL;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;
import com.sinosoft.lis.vschema.LCalculatorTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;

/** 对应数据库表lmdutygetclaim
 * 
 * @pdOid 5d2d4bb5-76bf-4dd2-9772-d36c72db174e */
public class ClaimCalculatorFactor {
	private static Logger logger = Logger.getLogger(ClaimCalculatorFactor.class);	
	private LLClaimDetailSchema mLLClaimDetailSchema;
	private LLRegisterSchema mLLRegisterSchema;
	private double mApplyPay;
	private LLClaimDutyFeeSchema mLLClaimDutyFeeSchema;
	private LCalculatorTraceSet mLCalculatorTraceSet;
	private LLCaseSchema mLLCaseSchema;
	/** @pdOid bb2421de-3d26-4ea9-b0e2-21616042b35c */
   private String ProductCode;
   /** @pdOid 868a8313-9fba-44e9-b1b5-899bf23b0485 */
   private String DutyCode;
   /** @pdOid 679659fe-f722-49d9-934e-e7b5a572cdcd */
   private String GetDutyCode;
   /** @pdOid 338e34c4-e60e-4e90-8581-c05222d1c1ef */
   private String GetDutyKind;
   /** @pdOid 4debe214-7400-4d12-80c5-e47ae2ab22da */
   private String FeeCode;
 //累加器trace缓存
	private CachedCalculatorTrace mCCT = CachedCalculatorTrace.getInstance();
   
   /** 根据理赔给付责任代码、账单费用类型编码关联到所有相关的累加器，逐一计算，得到累加器控制后的赔付金额；如果赔付金额不为0，则在计算过程中对累加器要素值（余额）进行扣减
    * 
    * @pdOid 13dfca8d-20cd-42dc-9093-d9bc621abc3c */
   public double getClaimCalculationResult() {
      // TODO: implement
	   
		//按照累加器层级由低到高排序，级别较低的先计算
	   //增加累加器顺序orderby：层级相同的累加器，按累加器顺序先后计算
		String tSql = "select a.CalculatorCode,b.CtrlFactorType,b.ctrllevel,c.calorder from LCalculatorFactor a,LCalculatorMain b,LCalculatorOrder c " +
				" where a.CalculatorCode=b.CalculatorCode and b.calculatorcode=c.calculatorcode"
			+ " and a.riskcode='?riskcode?'"
			+ " and a.dutycode='?dutycode?'"
			+ " and a.getdutycode='?getdutycode?'"
			+ " and a.getdutykind='?getdutykind?'"
			+ " order by b.ctrllevel,c.calorder";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("riskcode", this.ProductCode);
		sqlbv.put("dutycode", this.DutyCode);
		sqlbv.put("getdutycode", this.GetDutyCode);
		sqlbv.put("getdutykind", this.GetDutyKind);
		//最新的累加器方案要求按照免赔额-赔付-限额的顺序计算累加器，因此在描述一个getdutycode的累加器时，calorder必须按照免赔额-赔付-限额的顺序，否则计算结果会出错

		logger.info("--按照累加器层级由低到高排序，级别较低的先计算；层级相同的累加器，按累加器顺序先后计算:"+tSql);
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询赔案"+this.mLLClaimDetailSchema.getClmNo()+"累加器错误,"+
					tExeSQL.mErrors.getError(0).errorMessage);
			logger.info("------------------------------------------------------");
			logger.info("--ClaimCalculatorFactor.getClaimCalculationResult()--理赔计算时发生错误!"
					+ tSql);
			logger.info("------------------------------------------------------");
			return 0;
		}

		//tPaidSum的初始值=本次累加器计算前的赔付金额
		double tPaidSum = this.mApplyPay;
		mLCalculatorTraceSet = new LCalculatorTraceSet();
		//如果上述sql没有查到结果，说明没有关联到与该赔付相关的累加器，下面这个for循环不会进入，将直接返回原结果tPaidSum
		for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
			if (tPaidSum <= 0){
				//已经为0了就不要再继续算了
				break;
			}
			String tCalculatorCode = tSSRS.GetText(j, 1);
			String tCtrlFactorType = tSSRS.GetText(j, 2);
			//tCalResult存放本次累加器计算后的赔付金额
			double tCalResult = 0;

			//查ldcode：根据累加器类型获得相应的累加器类名
			tSql = "select codename from ldcode where codetype='clmcalculatorservice' and code='?tCtrlFactorType?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tCtrlFactorType", tCtrlFactorType);
			SSRS tSSRS1 = tExeSQL.execSQL(sqlbv1);
			String tServiceName = tSSRS1.GetText(1, 1);
			if (tServiceName==null || "".equals(tServiceName)){
				logger.info("--ClaimCalculatorFactor.getClaimCalculationResult()--累加器类查询失败!");
				return 0;
			}

			try{
				Class tClass = Class.forName(tServiceName);
				//ClaimCalculator tCalculator =  (ClaimCalculator) tClass.newInstance();
				ClaimCalculatorElement tCalculator =  (ClaimCalculatorElement) tClass.newInstance();
				tCalculator.setCode(tCalculatorCode);
				tCalculator.setLLClaimDetailSchema(this.mLLClaimDetailSchema);
				tCalculator.setLLRegisterSchema(this.mLLRegisterSchema);
				//xuyunpeng add
				tCalculator.setLLCaseSchema(mLLCaseSchema);
				tCalculator.setApplyPay(tPaidSum);
				if (this.mLLClaimDutyFeeSchema!=null){
					tCalculator.setLLClaimDutyFeeSchema(mLLClaimDutyFeeSchema);
					logger.info("=====clmno="+this.mLLClaimDetailSchema.getClmNo());
					logger.info("----------CalculatorCode="+tCalculatorCode);
					logger.info("----------GetDutyCode="+this.GetDutyCode);
					logger.info("----------GetDutyKind="+this.GetDutyKind);
					logger.info("----------FeeCode="+mLLClaimDutyFeeSchema.getDutyFeeCode());
					logger.info("----------DutyFeeStaNo="+mLLClaimDutyFeeSchema.getDutyFeeStaNo());
					logger.info("----------ApplyPay="+tPaidSum);
				}
				tCalResult = tCalculator.getClaimCalculationResult();
				logger.info("=====clmno="+this.mLLClaimDetailSchema.getClmNo()+"-----CalculatorCode="+tCalculatorCode+"-----CalResult="+tCalResult);
				//无论结果如何，前一个累加器的计算结果金额都应该作为后一个累加器的申请（入口）金额
//				if (tCalResult < tPaidSum){
//					tPaidSum = tCalResult;
//				}
				tPaidSum = tCalResult;
				LCalculatorTraceSchema tLCalculatorTraceSchema = tCalculator.getLCalculatorTraceSchema();
				if (tLCalculatorTraceSchema!=null){
					this.mLCalculatorTraceSet.add(tLCalculatorTraceSchema);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				//如果赔案有一个累加器计算出现异常，为保险起见，跳出前要清除它的累加器trace缓存
				mCCT.remove(this.mLLClaimDetailSchema.getClmNo());
				return 0;
			}

		}
		//tPaidSum的最终值=经累加器修正过的赔付金额
      return tPaidSum;
   }

   public LCalculatorTraceSet getLCalculatorTraceSet(){
	   return this.mLCalculatorTraceSet;
   }
   
   public void setLLRegisterSchema(LLRegisterSchema tLLRegisterSchema){
	   this.mLLRegisterSchema = tLLRegisterSchema;
   }

   public void setLLClaimDetailSchema(LLClaimDetailSchema tLLClaimDetailSchema){
	   this.mLLClaimDetailSchema = tLLClaimDetailSchema;
	   setProductCode(this.mLLClaimDetailSchema.getRiskCode()); // 险种代码
	   setDutyCode(this.mLLClaimDetailSchema.getDutyCode()); // 责任代码
	   setGetDutyCode(this.mLLClaimDetailSchema.getGetDutyCode()); // 给付责任
//	   setGetDutyKind(this.mLLClaimDetailSchema.getGetDutyKind()); // 理赔类型
	   //不能取LLClaimDetail.GetDutyKind，要取LCGet.GetDutyKind，只能现查。
	   ExeSQL mExeSQL = new ExeSQL(); 
	   String tSql = "";
	   SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	   if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	   tSql = "select getdutykind from lcget where polno='?polno?' and rownum=1";
	   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		   tSql = "select getdutykind from lcget where polno='?polno?' limit 0,1";
	   }
	   sqlbv2.sql(tSql);
	   sqlbv2.put("polno", this.mLLClaimDetailSchema.getPolNo());
//	   logger.info("===get getdutykind==="+tSql);
	   String tGetDutyKind = mExeSQL.getOneValue(sqlbv2);
	   if ( tGetDutyKind == null || "".equals(tGetDutyKind) ){
		   setGetDutyKind("A"); // 理赔类型
	   } else {
		   setGetDutyKind(tGetDutyKind); // 理赔类型
	   }
	   
   }

   public void setApplyPay(double tApplyPay){
	   this.mApplyPay = tApplyPay;
   }

   public void setLLClaimDutyFeeSchema(LLClaimDutyFeeSchema tLLClaimDutyFeeSchema){
	   this.mLLClaimDutyFeeSchema = tLLClaimDutyFeeSchema;
   }
   
   public void setLLCaseSchema(LLCaseSchema tLLCaseSchema){
	   this.mLLCaseSchema = tLLCaseSchema;
   }

   /** @pdOid 18129c89-aef8-42c4-b4ff-aa0cc4a0d8b1 */
   public void setProductCode(String tProductCode) {
      // TODO: implement
	   this.ProductCode = tProductCode;
   }
   
   /** @pdOid faa9300b-831e-4833-b628-685c993080a4 */
   public String getProductCode() {
      // TODO: implement
      return this.ProductCode;
   }
   
   /** @pdOid 04a201f9-719d-4963-97f2-d2fdc84e8c47 */
   public void setDutyCode(String tDutyCode) {
      // TODO: implement
	   this.DutyCode = tDutyCode;
   }
   
   /** @pdOid ca77f038-be23-4113-9923-df45136ce5e0 */
   public String getDutyCode() {
      // TODO: implement
      return this.DutyCode;
   }
   
   /** @pdOid 51be26c1-59df-4d9f-af3d-70efa74c309d */
   public void setGetDutyCode(String tGetDutyCode) {
      // TODO: implement
	   this.GetDutyCode = tGetDutyCode;
   }
   
   /** @pdOid 08bd06eb-5552-4a17-9a20-6b20c669250c */
   public String getGetDutyCode() {
      // TODO: implement
      return this.GetDutyCode;
   }
   
   /** @pdOid 670431fb-5503-4684-8823-a60c69db64b8 */
   public void setGetDutyKind(String tGetDutyKind) {
      // TODO: implement
	   this.GetDutyKind = tGetDutyKind;
   }
   
   /** @pdOid 4dba1e54-59ec-470c-b024-e0f255fbf3d7 */
   public String getGetDutyKind() {
      // TODO: implement
      return this.GetDutyKind;
   }
   
   /** @pdOid 94cb0213-1544-4bb4-b941-24df8901947e */
   public void setFeeCode(String tFeeCode) {
      // TODO: implement
	   this.FeeCode = tFeeCode;
   }
   
   /** @pdOid 3028ac7b-b778-4133-a5d2-94ca4b0ef857 */
   public String getFeeCode() {
      // TODO: implement
      return this.FeeCode;
   }

}