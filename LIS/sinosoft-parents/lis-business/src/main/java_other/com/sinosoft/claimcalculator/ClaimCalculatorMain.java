/***********************************************************************
 * Module:  ClaimCalculatorFactor.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculatorFactor
 ***********************************************************************/

package com.sinosoft.claimcalculator;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.cache.CachedCalculatorTrace;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LCalculatorTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;

public class ClaimCalculatorMain {
	private static Logger logger = Logger.getLogger(ClaimCalculatorMain.class);
	private LLClaimDetailSchema mLLClaimDetailSchema;
	private LLRegisterSchema mLLRegisterSchema;
	private LLCaseSchema mLLCaseSchema;
	private double mApplyPay;
	private LLClaimDutyFeeSchema mLLClaimDutyFeeSchema;
	private LCalculatorTraceSet mLCalculatorTraceSet;
	private String productCode;
	private String dutyCode;
	private String getDutyCode;
	private String getDutyKind;
	private String feeCode;
	//add by wsp 不关联预授权时，固定金额*0.8
	private double authRela = 0.0;
	// 累加器trace缓存
	private CachedCalculatorTrace mCCT = CachedCalculatorTrace.getInstance();

	public double getAuthRela(){
		return authRela;
	}
	public void setAuthRela(double authRela){
		this.authRela=authRela;
	}
	
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getGetDutyCode() {
		return getDutyCode;
	}

	public void setGetDutyCode(String getDutyCode) {
		this.getDutyCode = getDutyCode;
	}

	public String getGetDutyKind() {
		return getDutyKind;
	}

	public void setGetDutyKind(String getDutyKind) {
		this.getDutyKind = getDutyKind;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * 根据理赔给付责任代码、账单费用类型编码关联到所有相关的累加器，逐一计算，得到累加器控制后的赔付金额；如果赔付金额不为0，
	 * 则在计算过程中对累加器要素值（余额）进行扣减
	 * 
	 */
	public double getClaimCalculationResult() {

		// 按照累加器层级由低到高排序，级别较低的先计算
		// 增加累加器顺序orderby：层级相同的累加器，按累加器顺序先后计算
		String tSql = "select a.CalculatorCode,b.CtrlFactorType,b.ctrllevel,c.calorder from LCalculatorFactor a,LCalculatorMain b,LCalculatorOrder c "
				+ " where a.CalculatorCode=b.CalculatorCode and b.calculatorcode=c.calculatorcode"
				+ " and a.riskcode='"
				+ "?riskcode?"
				+ "'"
				+ " and a.dutycode='"
				+ "?dutycode?"
				+ "'"
				+ " and a.getdutycode='"
				+ "?getdutycode?"
				+ "'"
				+ " and a.getdutykind='"
				+ "?getdutykind?"
				+ "'"
				+ " order by b.ctrllevel,c.calorder";
		// 最新的累加器方案要求按照免赔额-赔付-限额的顺序计算累加器，因此在描述一个getdutycode的累加器时，calorder必须按照免赔额-赔付-限额的顺序，否则计算结果会出错

		logger.info("--按照累加器层级由低到高排序，级别较低的先计算；层级相同的累加器，按累加器顺序先后计算:" + tSql);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("riskcode", this.productCode);
		sqlbv.put("dutycode", this.dutyCode);
		sqlbv.put("getdutycode", this.getDutyCode);
		sqlbv.put("getdutykind", this.getDutyKind);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询赔案" + this.mLLClaimDetailSchema.getClmNo()
					+ "累加器错误," + tExeSQL.mErrors.getError(0).errorMessage);
			logger.info("------------------------------------------------------");
			logger.info("--ClaimCalculatorFactor.getClaimCalculationResult()--理赔计算时发生错误!"
					+ tSql);
			logger.info("------------------------------------------------------");
			return 0;
		}

		// tPaidSum的初始值=本次累加器计算前的赔付金额
		double tPaidSum = this.mApplyPay;
		mLCalculatorTraceSet = new LCalculatorTraceSet();
		// 如果上述sql没有查到结果，说明没有关联到与该赔付相关的累加器，下面这个for循环不会进入，将直接返回原结果tPaidSum
		for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
			if (tPaidSum <= 0) {
				// 已经为0了就不要再继续算了
				break;
			}
			String tCalculatorCode = tSSRS.GetText(j, 1);
			String tCtrlFactorType = tSSRS.GetText(j, 2);
			// tCalResult存放本次累加器计算后的赔付金额
			double tCalResult = 0;

			// 查ldcode：根据累加器类型获得相应的累加器类名
			tSql = "select codename from ldcode where codetype='clmcalculatorservice' and code='"
					+ "?tCtrlFactorType?" + "'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tCtrlFactorType", tCtrlFactorType);
			SSRS tSSRS1 = tExeSQL.execSQL(sqlbv1);
			String tServiceName = tSSRS1.GetText(1, 1);
			if (tServiceName == null || "".equals(tServiceName)) {
				logger.info("--ClaimCalculatorFactor.getClaimCalculationResult()--累加器类查询失败!");
				return 0;
			}

			try {
				Class<?> tClass = Class.forName(tServiceName);
				ClaimCalculatorElement tCalculator = (ClaimCalculatorElement) tClass
						.newInstance();
				tCalculator.setCode(tCalculatorCode);
				tCalculator.setLLClaimDetailSchema(this.mLLClaimDetailSchema);
				tCalculator.setLLRegisterSchema(this.mLLRegisterSchema);
				//add 20151207 by wsp
				tCalculator.setLLCaseSchema(this.mLLCaseSchema);
				
				tCalculator.setApplyPay(tPaidSum);
				tCalculator.setAuthRela(authRela);
				
				if (this.mLLClaimDutyFeeSchema != null) {
					tCalculator.setLLClaimDutyFeeSchema(mLLClaimDutyFeeSchema);
					logger.info("=====clmno="
							+ this.mLLClaimDetailSchema.getClmNo());
					logger.info("----------CalculatorCode=" + tCalculatorCode);
					logger.info("----------GetDutyCode=" + this.getDutyCode);
					logger.info("----------GetDutyKind=" + this.getDutyKind);
					logger.info("----------FeeCode="
							+ mLLClaimDutyFeeSchema.getDutyFeeCode());
					logger.info("----------DutyFeeStaNo="
							+ mLLClaimDutyFeeSchema.getDutyFeeStaNo());
					logger.info("----------ApplyPay=" + tPaidSum);
				}
				tCalResult = tCalculator.getClaimCalculationResult();
				logger.info("=====clmno="
						+ this.mLLClaimDetailSchema.getClmNo()
						+ "-----CalculatorCode=" + tCalculatorCode
						+ "-----CalResult=" + tCalResult);
				// 无论结果如何，前一个累加器的计算结果金额都应该作为后一个累加器的申请（入口）金额
				// if (tCalResult < tPaidSum){
				// tPaidSum = tCalResult;
				// }
				tPaidSum = tCalResult;
				LCalculatorTraceSchema tLCalculatorTraceSchema = tCalculator
						.getLCalculatorTraceSchema();
				if (tLCalculatorTraceSchema != null) {
					this.mLCalculatorTraceSet.add(tLCalculatorTraceSchema);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				// 如果赔案有一个累加器计算出现异常，为保险起见，跳出前要清除它的累加器trace缓存
				mCCT.remove(this.mLLClaimDetailSchema.getClmNo());
				return 0;
			}

		}
		// tPaidSum的最终值=经累加器修正过的赔付金额
		return tPaidSum;
	}

	public LCalculatorTraceSet getLCalculatorTraceSet() {
		return this.mLCalculatorTraceSet;
	}

	public void setLLRegisterSchema(LLRegisterSchema tLLRegisterSchema) {
		this.mLLRegisterSchema = tLLRegisterSchema;
	}
	
	public void setLLCaseSchema(LLCaseSchema tLLCaseSchema) {
		this.mLLCaseSchema = tLLCaseSchema;
	}

	public void setLLClaimDetailSchema(LLClaimDetailSchema tLLClaimDetailSchema) {
		this.mLLClaimDetailSchema = tLLClaimDetailSchema;
		setProductCode(this.mLLClaimDetailSchema.getRiskCode()); // 险种代码
		setDutyCode(this.mLLClaimDetailSchema.getDutyCode()); // 责任代码
		setGetDutyCode(this.mLLClaimDetailSchema.getGetDutyCode()); // 给付责任
		// setGetDutyKind(this.mLLClaimDetailSchema.getGetDutyKind()); // 理赔类型
		// 不能取LLClaimDetail.GetDutyKind，要取LCGet.GetDutyKind，只能现查。
		ExeSQL mExeSQL = new ExeSQL();
		// 修改2015-12-07 by wsp begin
		String tSql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tSql = "select contplancode from lccont where contno='"
					+ "?contno?" + "' and rownum=1";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tSql = "select contplancode from lccont where contno='"
					+ "?contno?" + "' limit 1";
		}
		 
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("contno", this.mLLClaimDetailSchema.getContNo());
		//end
		// logger.info("===get getdutykind==="+tSql);
		String tGetDutyKind = mExeSQL.getOneValue(sqlbv2);
		if (tGetDutyKind == null || "".equals(tGetDutyKind)) {
			setGetDutyKind(this.mLLClaimDetailSchema.getGetDutyKind()); // 理赔类型
		} else {
			setGetDutyKind(tGetDutyKind); // 理赔类型
		}

	}

	public void setApplyPay(double tApplyPay) {
		this.mApplyPay = tApplyPay;
	}

	public void setLLClaimDutyFeeSchema(
			LLClaimDutyFeeSchema tLLClaimDutyFeeSchema) {
		this.mLLClaimDutyFeeSchema = tLLClaimDutyFeeSchema;
	}

}