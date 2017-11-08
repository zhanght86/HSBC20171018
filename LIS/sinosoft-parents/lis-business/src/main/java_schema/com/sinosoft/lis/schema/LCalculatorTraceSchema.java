/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import org.apache.log4j.Logger;
import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LCalculatorTraceDB;

/*
 * <p>ClassName: LCalculatorTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class LCalculatorTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCalculatorTraceSchema.class);
	// @Field
	/** 序号 */
	private String SerNo;
	/** 集体保单号 */
	private String GrpContNo;
	/** 保单计划号 */
	private String ContPlanCode;
	/** 主被保人客户号 */
	private String MainInsuredNo;
	/** 个单保单号 */
	private String ContNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 险种代码 */
	private String RiskCode;
	/** 连生被保人客户号 */
	private String RelatedInsuredNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 累加器编号 */
	private String CalculatorCode;
	/** 控制器级别 */
	private int CtrlLevel;
	/** 赔案号 */
	private String ClmNo;
	/** 累加器类型 */
	private String Type;
	/** 累加器要素类型 */
	private String CtrlFactorType;
	/** 累加器要素单位 */
	private String CtrlFactorUnit;
	/** 本次待赔 */
	private double ApplyPay;
	/** 总待赔 */
	private double SumApplyPay;
	/** 赔付 */
	private double UsedLimit;
	/** 余额 */
	private double SumNotPay;
	/** 总已赔 */
	private double SumPaid;
	/** 赔付前累加器限额 */
	private double BeforeUsedLimit;
	/** 赔付后累加器限额 */
	private double AfterUsedLimit;
	/** 本次赔付金额 */
	private double ActualPay;
	/** 费用类型 */
	private String DutyFeeType;
	/** 费用代码 */
	private String DutyFeeCode;
	/** 费用序号 */
	private String DutyFeeStaNo;
	/** 出险日期 */
	private Date AccidentDate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 34;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCalculatorTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerNo";

		PK = pk;
	}

	/**
	* Schema克隆
	* @return Object
	* @throws CloneNotSupportedException
	*/
	public Object clone()
		throws CloneNotSupportedException
	{
		LCalculatorTraceSchema cloned = (LCalculatorTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerNo()
	{
		return SerNo;
	}
	public void setSerNo(String aSerNo)
	{
		if(aSerNo!=null && aSerNo.length()>20)
			throw new IllegalArgumentException("序号SerNo值"+aSerNo+"的长度"+aSerNo.length()+"大于最大值20");
		SerNo = aSerNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		if(aContPlanCode!=null && aContPlanCode.length()>10)
			throw new IllegalArgumentException("保单计划号ContPlanCode值"+aContPlanCode+"的长度"+aContPlanCode.length()+"大于最大值10");
		ContPlanCode = aContPlanCode;
	}
	public String getMainInsuredNo()
	{
		return MainInsuredNo;
	}
	public void setMainInsuredNo(String aMainInsuredNo)
	{
		if(aMainInsuredNo!=null && aMainInsuredNo.length()>20)
			throw new IllegalArgumentException("主被保人客户号MainInsuredNo值"+aMainInsuredNo+"的长度"+aMainInsuredNo.length()+"大于最大值20");
		MainInsuredNo = aMainInsuredNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个单保单号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>20)
			throw new IllegalArgumentException("被保人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值20");
		InsuredNo = aInsuredNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getRelatedInsuredNo()
	{
		return RelatedInsuredNo;
	}
	public void setRelatedInsuredNo(String aRelatedInsuredNo)
	{
		if(aRelatedInsuredNo!=null && aRelatedInsuredNo.length()>20)
			throw new IllegalArgumentException("连生被保人客户号RelatedInsuredNo值"+aRelatedInsuredNo+"的长度"+aRelatedInsuredNo.length()+"大于最大值20");
		RelatedInsuredNo = aRelatedInsuredNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>10)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值10");
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>6)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值6");
		GetDutyCode = aGetDutyCode;
	}
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>6)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值6");
		GetDutyKind = aGetDutyKind;
	}
	public String getCalculatorCode()
	{
		return CalculatorCode;
	}
	public void setCalculatorCode(String aCalculatorCode)
	{
		if(aCalculatorCode!=null && aCalculatorCode.length()>20)
			throw new IllegalArgumentException("累加器编号CalculatorCode值"+aCalculatorCode+"的长度"+aCalculatorCode.length()+"大于最大值20");
		CalculatorCode = aCalculatorCode;
	}
	public int getCtrlLevel()
	{
		return CtrlLevel;
	}
	public void setCtrlLevel(int aCtrlLevel)
	{
		CtrlLevel = aCtrlLevel;
	}
	public void setCtrlLevel(String aCtrlLevel)
	{
		if (aCtrlLevel != null && !aCtrlLevel.equals(""))
		{
			Integer tInteger = new Integer(aCtrlLevel);
			int i = tInteger.intValue();
			CtrlLevel = i;
		}
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	/**
	* 1-限额<p>
	* 2-免赔额<p>
	* 3-计算(公式)
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		if(aType!=null && aType.length()>1)
			throw new IllegalArgumentException("累加器类型Type值"+aType+"的长度"+aType.length()+"大于最大值1");
		Type = aType;
	}
	/**
	* 1-金额<p>
	* 2-天数<p>
	* 3-次数<p>
	* 4-天金额<p>
	* 5-次金额
	*/
	public String getCtrlFactorType()
	{
		return CtrlFactorType;
	}
	public void setCtrlFactorType(String aCtrlFactorType)
	{
		if(aCtrlFactorType!=null && aCtrlFactorType.length()>1)
			throw new IllegalArgumentException("累加器要素类型CtrlFactorType值"+aCtrlFactorType+"的长度"+aCtrlFactorType.length()+"大于最大值1");
		CtrlFactorType = aCtrlFactorType;
	}
	/**
	* 1-金额（元）<p>
	* 2-天<p>
	* 3-次<p>
	* 4-小时
	*/
	public String getCtrlFactorUnit()
	{
		return CtrlFactorUnit;
	}
	public void setCtrlFactorUnit(String aCtrlFactorUnit)
	{
		if(aCtrlFactorUnit!=null && aCtrlFactorUnit.length()>1)
			throw new IllegalArgumentException("累加器要素单位CtrlFactorUnit值"+aCtrlFactorUnit+"的长度"+aCtrlFactorUnit.length()+"大于最大值1");
		CtrlFactorUnit = aCtrlFactorUnit;
	}
	/**
	* 原字段名：申请赔付金额，即当前账单金额/次/天
	*/
	public double getApplyPay()
	{
		return ApplyPay;
	}
	public void setApplyPay(double aApplyPay)
	{
		ApplyPay = aApplyPay;
	}
	public void setApplyPay(String aApplyPay)
	{
		if (aApplyPay != null && !aApplyPay.equals(""))
		{
			Double tDouble = new Double(aApplyPay);
			double d = tDouble.doubleValue();
			ApplyPay = d;
		}
	}

	/**
	* 总待赔=历史余额(+本次待赔)<p>
	* 历史余额=同get、同账单、上一条trace的赔付余额<p>
	* (+本次待赔)：只有该账单的第一个累加器时需要加，之后的不需要——避免加重了
	*/
	public double getSumApplyPay()
	{
		return SumApplyPay;
	}
	public void setSumApplyPay(double aSumApplyPay)
	{
		SumApplyPay = aSumApplyPay;
	}
	public void setSumApplyPay(String aSumApplyPay)
	{
		if (aSumApplyPay != null && !aSumApplyPay.equals(""))
		{
			Double tDouble = new Double(aSumApplyPay);
			double d = tDouble.doubleValue();
			SumApplyPay = d;
		}
	}

	/**
	* 原名：本次赔付使用的限额。<p>
	* 分累加器类型列举取值规则如下：<p>
	* 1.赔付：计算<p>
	* 2.限额：总待/限/0，或min(总已赔（上一条的）或总待赔,限额-关联总赔付），详细:<p>
	*   如果没有上一条trace，则=min(总待赔,(限额-关联总赔付))<p>
	*   如果有上一条trace，许分两种情况处理：<p>
	*     1）上条trace的保单号与当前相同，则=min(总已赔（上一条的）,(限额-关联总赔付)）<p>
	*     2）上条trace的保单号与当前不同，则=min(总待赔,(限额-关联总赔付)）——注意，这种情况下总待赔=上一条trace的余额<p>
	* 3.免赔：0/免赔额：总待未达免赔额存0；达到时存免赔额；达到后不存了
	*/
	public double getUsedLimit()
	{
		return UsedLimit;
	}
	public void setUsedLimit(double aUsedLimit)
	{
		UsedLimit = aUsedLimit;
	}
	public void setUsedLimit(String aUsedLimit)
	{
		if (aUsedLimit != null && !aUsedLimit.equals(""))
		{
			Double tDouble = new Double(aUsedLimit);
			double d = tDouble.doubleValue();
			UsedLimit = d;
		}
	}

	/**
	* 经本次理赔计算后仍未赔部分<p>
	* 分累加器类型列举取值规则如下：<p>
	* 1.赔付：总待-赔<p>
	* 2.限额：总已赔（上一条的）+总待赔-赔付，详细：<p>
	*   如果没有上一条trace，则=总待赔-赔付<p>
	*   如果有上一条trace，许分两种情况处理：<p>
	*     1）上条trace的保单号与当前相同，则=总已赔（上一条的）+总待赔-赔付<p>
	*     2）上条trace的保单号与当前不同，则=总待赔-赔付——注意，这种情况下总待赔=上一条trace的余额<p>
	* <p>
	* 3.免赔：总待赔/总待赔-免赔
	*/
	public double getSumNotPay()
	{
		return SumNotPay;
	}
	public void setSumNotPay(double aSumNotPay)
	{
		SumNotPay = aSumNotPay;
	}
	public void setSumNotPay(String aSumNotPay)
	{
		if (aSumNotPay != null && !aSumNotPay.equals(""))
		{
			Double tDouble = new Double(aSumNotPay);
			double d = tDouble.doubleValue();
			SumNotPay = d;
		}
	}

	/**
	* 1.类型：赔/限，取值：赔付<p>
	* 2.类型：免赔，取值0
	*/
	public double getSumPaid()
	{
		return SumPaid;
	}
	public void setSumPaid(double aSumPaid)
	{
		SumPaid = aSumPaid;
	}
	public void setSumPaid(String aSumPaid)
	{
		if (aSumPaid != null && !aSumPaid.equals(""))
		{
			Double tDouble = new Double(aSumPaid);
			double d = tDouble.doubleValue();
			SumPaid = d;
		}
	}

	public double getBeforeUsedLimit()
	{
		return BeforeUsedLimit;
	}
	public void setBeforeUsedLimit(double aBeforeUsedLimit)
	{
		BeforeUsedLimit = aBeforeUsedLimit;
	}
	public void setBeforeUsedLimit(String aBeforeUsedLimit)
	{
		if (aBeforeUsedLimit != null && !aBeforeUsedLimit.equals(""))
		{
			Double tDouble = new Double(aBeforeUsedLimit);
			double d = tDouble.doubleValue();
			BeforeUsedLimit = d;
		}
	}

	public double getAfterUsedLimit()
	{
		return AfterUsedLimit;
	}
	public void setAfterUsedLimit(double aAfterUsedLimit)
	{
		AfterUsedLimit = aAfterUsedLimit;
	}
	public void setAfterUsedLimit(String aAfterUsedLimit)
	{
		if (aAfterUsedLimit != null && !aAfterUsedLimit.equals(""))
		{
			Double tDouble = new Double(aAfterUsedLimit);
			double d = tDouble.doubleValue();
			AfterUsedLimit = d;
		}
	}

	/**
	* 累加器要素类型是限额/天金额/次金额的，本字段取值同UsedLimit；是次数/天数的，本字段取值UsedLimit*DeFaultValue
	*/
	public double getActualPay()
	{
		return ActualPay;
	}
	public void setActualPay(double aActualPay)
	{
		ActualPay = aActualPay;
	}
	public void setActualPay(String aActualPay)
	{
		if (aActualPay != null && !aActualPay.equals(""))
		{
			Double tDouble = new Double(aActualPay);
			double d = tDouble.doubleValue();
			ActualPay = d;
		}
	}

	/**
	* “费用类型、费用代码、费用序号”这三个字段只有在账单理赔计算时才会被用到，用来表示当前tace记录的是那一个账单的累加器计算结果。
	*/
	public String getDutyFeeType()
	{
		return DutyFeeType;
	}
	public void setDutyFeeType(String aDutyFeeType)
	{
		if(aDutyFeeType!=null && aDutyFeeType.length()>10)
			throw new IllegalArgumentException("费用类型DutyFeeType值"+aDutyFeeType+"的长度"+aDutyFeeType.length()+"大于最大值10");
		DutyFeeType = aDutyFeeType;
	}
	public String getDutyFeeCode()
	{
		return DutyFeeCode;
	}
	public void setDutyFeeCode(String aDutyFeeCode)
	{
		if(aDutyFeeCode!=null && aDutyFeeCode.length()>10)
			throw new IllegalArgumentException("费用代码DutyFeeCode值"+aDutyFeeCode+"的长度"+aDutyFeeCode.length()+"大于最大值10");
		DutyFeeCode = aDutyFeeCode;
	}
	public String getDutyFeeStaNo()
	{
		return DutyFeeStaNo;
	}
	public void setDutyFeeStaNo(String aDutyFeeStaNo)
	{
		if(aDutyFeeStaNo!=null && aDutyFeeStaNo.length()>20)
			throw new IllegalArgumentException("费用序号DutyFeeStaNo值"+aDutyFeeStaNo+"的长度"+aDutyFeeStaNo.length()+"大于最大值20");
		DutyFeeStaNo = aDutyFeeStaNo;
	}
	public String getAccidentDate()
	{
		if( AccidentDate != null )
			return fDate.getString(AccidentDate);
		else
			return null;
	}
	public void setAccidentDate(Date aAccidentDate)
	{
		AccidentDate = aAccidentDate;
	}
	public void setAccidentDate(String aAccidentDate)
	{
		if (aAccidentDate != null && !aAccidentDate.equals("") )
		{
			AccidentDate = fDate.getDate( aAccidentDate );
		}
		else
			AccidentDate = null;
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LCalculatorTraceSchema 对象给 Schema 赋值
	* @param: aLCalculatorTraceSchema LCalculatorTraceSchema
	**/
	public void setSchema(LCalculatorTraceSchema aLCalculatorTraceSchema)
	{
		this.SerNo = aLCalculatorTraceSchema.getSerNo();
		this.GrpContNo = aLCalculatorTraceSchema.getGrpContNo();
		this.ContPlanCode = aLCalculatorTraceSchema.getContPlanCode();
		this.MainInsuredNo = aLCalculatorTraceSchema.getMainInsuredNo();
		this.ContNo = aLCalculatorTraceSchema.getContNo();
		this.InsuredNo = aLCalculatorTraceSchema.getInsuredNo();
		this.RiskCode = aLCalculatorTraceSchema.getRiskCode();
		this.RelatedInsuredNo = aLCalculatorTraceSchema.getRelatedInsuredNo();
		this.DutyCode = aLCalculatorTraceSchema.getDutyCode();
		this.GetDutyCode = aLCalculatorTraceSchema.getGetDutyCode();
		this.GetDutyKind = aLCalculatorTraceSchema.getGetDutyKind();
		this.CalculatorCode = aLCalculatorTraceSchema.getCalculatorCode();
		this.CtrlLevel = aLCalculatorTraceSchema.getCtrlLevel();
		this.ClmNo = aLCalculatorTraceSchema.getClmNo();
		this.Type = aLCalculatorTraceSchema.getType();
		this.CtrlFactorType = aLCalculatorTraceSchema.getCtrlFactorType();
		this.CtrlFactorUnit = aLCalculatorTraceSchema.getCtrlFactorUnit();
		this.ApplyPay = aLCalculatorTraceSchema.getApplyPay();
		this.SumApplyPay = aLCalculatorTraceSchema.getSumApplyPay();
		this.UsedLimit = aLCalculatorTraceSchema.getUsedLimit();
		this.SumNotPay = aLCalculatorTraceSchema.getSumNotPay();
		this.SumPaid = aLCalculatorTraceSchema.getSumPaid();
		this.BeforeUsedLimit = aLCalculatorTraceSchema.getBeforeUsedLimit();
		this.AfterUsedLimit = aLCalculatorTraceSchema.getAfterUsedLimit();
		this.ActualPay = aLCalculatorTraceSchema.getActualPay();
		this.DutyFeeType = aLCalculatorTraceSchema.getDutyFeeType();
		this.DutyFeeCode = aLCalculatorTraceSchema.getDutyFeeCode();
		this.DutyFeeStaNo = aLCalculatorTraceSchema.getDutyFeeStaNo();
		this.AccidentDate = fDate.getDate( aLCalculatorTraceSchema.getAccidentDate());
		this.Operator = aLCalculatorTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCalculatorTraceSchema.getMakeDate());
		this.MakeTime = aLCalculatorTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCalculatorTraceSchema.getModifyDate());
		this.ModifyTime = aLCalculatorTraceSchema.getModifyTime();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("SerNo") == null )
				this.SerNo = null;
			else
				this.SerNo = rs.getString("SerNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("MainInsuredNo") == null )
				this.MainInsuredNo = null;
			else
				this.MainInsuredNo = rs.getString("MainInsuredNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RelatedInsuredNo") == null )
				this.RelatedInsuredNo = null;
			else
				this.RelatedInsuredNo = rs.getString("RelatedInsuredNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("CalculatorCode") == null )
				this.CalculatorCode = null;
			else
				this.CalculatorCode = rs.getString("CalculatorCode").trim();

			this.CtrlLevel = rs.getInt("CtrlLevel");
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("CtrlFactorType") == null )
				this.CtrlFactorType = null;
			else
				this.CtrlFactorType = rs.getString("CtrlFactorType").trim();

			if( rs.getString("CtrlFactorUnit") == null )
				this.CtrlFactorUnit = null;
			else
				this.CtrlFactorUnit = rs.getString("CtrlFactorUnit").trim();

			this.ApplyPay = rs.getDouble("ApplyPay");
			this.SumApplyPay = rs.getDouble("SumApplyPay");
			this.UsedLimit = rs.getDouble("UsedLimit");
			this.SumNotPay = rs.getDouble("SumNotPay");
			this.SumPaid = rs.getDouble("SumPaid");
			this.BeforeUsedLimit = rs.getDouble("BeforeUsedLimit");
			this.AfterUsedLimit = rs.getDouble("AfterUsedLimit");
			this.ActualPay = rs.getDouble("ActualPay");
			if( rs.getString("DutyFeeType") == null )
				this.DutyFeeType = null;
			else
				this.DutyFeeType = rs.getString("DutyFeeType").trim();

			if( rs.getString("DutyFeeCode") == null )
				this.DutyFeeCode = null;
			else
				this.DutyFeeCode = rs.getString("DutyFeeCode").trim();

			if( rs.getString("DutyFeeStaNo") == null )
				this.DutyFeeStaNo = null;
			else
				this.DutyFeeStaNo = rs.getString("DutyFeeStaNo").trim();

			this.AccidentDate = rs.getDate("AccidentDate");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCalculatorTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCalculatorTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCalculatorTraceSchema getSchema()
	{
		LCalculatorTraceSchema aLCalculatorTraceSchema = new LCalculatorTraceSchema();
		aLCalculatorTraceSchema.setSchema(this);
		return aLCalculatorTraceSchema;
	}

	public LCalculatorTraceDB getDB()
	{
		LCalculatorTraceDB aDBOper = new LCalculatorTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCalculatorTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelatedInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalculatorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlFactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlFactorUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ApplyPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumApplyPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UsedLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumNotPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPaid));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BeforeUsedLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AfterUsedLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ActualPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeStaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccidentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCalculatorTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MainInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RelatedInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalculatorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CtrlLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CtrlFactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CtrlFactorUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ApplyPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			SumApplyPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			UsedLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			SumNotPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			SumPaid = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			BeforeUsedLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			AfterUsedLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			ActualPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			DutyFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			DutyFeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			DutyFeeStaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCalculatorTraceSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode String 希望取得的字段名
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equalsIgnoreCase("SerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("MainInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainInsuredNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RelatedInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelatedInsuredNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalculatorCode));
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlLevel));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("CtrlFactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlFactorType));
		}
		if (FCode.equalsIgnoreCase("CtrlFactorUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlFactorUnit));
		}
		if (FCode.equalsIgnoreCase("ApplyPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyPay));
		}
		if (FCode.equalsIgnoreCase("SumApplyPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumApplyPay));
		}
		if (FCode.equalsIgnoreCase("UsedLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UsedLimit));
		}
		if (FCode.equalsIgnoreCase("SumNotPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumNotPay));
		}
		if (FCode.equalsIgnoreCase("SumPaid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPaid));
		}
		if (FCode.equalsIgnoreCase("BeforeUsedLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforeUsedLimit));
		}
		if (FCode.equalsIgnoreCase("AfterUsedLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterUsedLimit));
		}
		if (FCode.equalsIgnoreCase("ActualPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualPay));
		}
		if (FCode.equalsIgnoreCase("DutyFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeType));
		}
		if (FCode.equalsIgnoreCase("DutyFeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeCode));
		}
		if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeStaNo));
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex int 指定的字段索引值
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(SerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MainInsuredNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RelatedInsuredNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalculatorCode);
				break;
			case 12:
				strFieldValue = String.valueOf(CtrlLevel);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CtrlFactorType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CtrlFactorUnit);
				break;
			case 17:
				strFieldValue = String.valueOf(ApplyPay);
				break;
			case 18:
				strFieldValue = String.valueOf(SumApplyPay);
				break;
			case 19:
				strFieldValue = String.valueOf(UsedLimit);
				break;
			case 20:
				strFieldValue = String.valueOf(SumNotPay);
				break;
			case 21:
				strFieldValue = String.valueOf(SumPaid);
				break;
			case 22:
				strFieldValue = String.valueOf(BeforeUsedLimit);
				break;
			case 23:
				strFieldValue = String.valueOf(AfterUsedLimit);
				break;
			case 24:
				strFieldValue = String.valueOf(ActualPay);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeType);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeStaNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
	}

	/**
	* 设置对应传入参数的String形式的字段值
	* @param: FCode String 需要赋值的对象
	* @param: FValue String 要赋的值
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equalsIgnoreCase("SerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerNo = FValue.trim();
			}
			else
				SerNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("MainInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainInsuredNo = FValue.trim();
			}
			else
				MainInsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RelatedInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelatedInsuredNo = FValue.trim();
			}
			else
				RelatedInsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalculatorCode = FValue.trim();
			}
			else
				CalculatorCode = null;
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("CtrlFactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlFactorType = FValue.trim();
			}
			else
				CtrlFactorType = null;
		}
		if (FCode.equalsIgnoreCase("CtrlFactorUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlFactorUnit = FValue.trim();
			}
			else
				CtrlFactorUnit = null;
		}
		if (FCode.equalsIgnoreCase("ApplyPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ApplyPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumApplyPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumApplyPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("UsedLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UsedLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumNotPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumNotPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPaid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPaid = d;
			}
		}
		if (FCode.equalsIgnoreCase("BeforeUsedLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BeforeUsedLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("AfterUsedLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AfterUsedLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("ActualPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ActualPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("DutyFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeType = FValue.trim();
			}
			else
				DutyFeeType = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeCode = FValue.trim();
			}
			else
				DutyFeeCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeStaNo = FValue.trim();
			}
			else
				DutyFeeStaNo = null;
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccidentDate = fDate.getDate( FValue );
			}
			else
				AccidentDate = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCalculatorTraceSchema other = (LCalculatorTraceSchema)otherObject;
		return
			SerNo.equals(other.getSerNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& MainInsuredNo.equals(other.getMainInsuredNo())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& RiskCode.equals(other.getRiskCode())
			&& RelatedInsuredNo.equals(other.getRelatedInsuredNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& CalculatorCode.equals(other.getCalculatorCode())
			&& CtrlLevel == other.getCtrlLevel()
			&& ClmNo.equals(other.getClmNo())
			&& Type.equals(other.getType())
			&& CtrlFactorType.equals(other.getCtrlFactorType())
			&& CtrlFactorUnit.equals(other.getCtrlFactorUnit())
			&& ApplyPay == other.getApplyPay()
			&& SumApplyPay == other.getSumApplyPay()
			&& UsedLimit == other.getUsedLimit()
			&& SumNotPay == other.getSumNotPay()
			&& SumPaid == other.getSumPaid()
			&& BeforeUsedLimit == other.getBeforeUsedLimit()
			&& AfterUsedLimit == other.getAfterUsedLimit()
			&& ActualPay == other.getActualPay()
			&& DutyFeeType.equals(other.getDutyFeeType())
			&& DutyFeeCode.equals(other.getDutyFeeCode())
			&& DutyFeeStaNo.equals(other.getDutyFeeStaNo())
			&& fDate.getString(AccidentDate).equals(other.getAccidentDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
	}

	/**
	* 取得Schema拥有字段的数量
       * @return: int
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("SerNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("MainInsuredNo") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("RelatedInsuredNo") ) {
			return 7;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 8;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 9;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 10;
		}
		if( strFieldName.equals("CalculatorCode") ) {
			return 11;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return 12;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 13;
		}
		if( strFieldName.equals("Type") ) {
			return 14;
		}
		if( strFieldName.equals("CtrlFactorType") ) {
			return 15;
		}
		if( strFieldName.equals("CtrlFactorUnit") ) {
			return 16;
		}
		if( strFieldName.equals("ApplyPay") ) {
			return 17;
		}
		if( strFieldName.equals("SumApplyPay") ) {
			return 18;
		}
		if( strFieldName.equals("UsedLimit") ) {
			return 19;
		}
		if( strFieldName.equals("SumNotPay") ) {
			return 20;
		}
		if( strFieldName.equals("SumPaid") ) {
			return 21;
		}
		if( strFieldName.equals("BeforeUsedLimit") ) {
			return 22;
		}
		if( strFieldName.equals("AfterUsedLimit") ) {
			return 23;
		}
		if( strFieldName.equals("ActualPay") ) {
			return 24;
		}
		if( strFieldName.equals("DutyFeeType") ) {
			return 25;
		}
		if( strFieldName.equals("DutyFeeCode") ) {
			return 26;
		}
		if( strFieldName.equals("DutyFeeStaNo") ) {
			return 27;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 28;
		}
		if( strFieldName.equals("Operator") ) {
			return 29;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 30;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 33;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
       * @param: nFieldIndex int
       * @return: String
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "SerNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ContPlanCode";
				break;
			case 3:
				strFieldName = "MainInsuredNo";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "InsuredNo";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "RelatedInsuredNo";
				break;
			case 8:
				strFieldName = "DutyCode";
				break;
			case 9:
				strFieldName = "GetDutyCode";
				break;
			case 10:
				strFieldName = "GetDutyKind";
				break;
			case 11:
				strFieldName = "CalculatorCode";
				break;
			case 12:
				strFieldName = "CtrlLevel";
				break;
			case 13:
				strFieldName = "ClmNo";
				break;
			case 14:
				strFieldName = "Type";
				break;
			case 15:
				strFieldName = "CtrlFactorType";
				break;
			case 16:
				strFieldName = "CtrlFactorUnit";
				break;
			case 17:
				strFieldName = "ApplyPay";
				break;
			case 18:
				strFieldName = "SumApplyPay";
				break;
			case 19:
				strFieldName = "UsedLimit";
				break;
			case 20:
				strFieldName = "SumNotPay";
				break;
			case 21:
				strFieldName = "SumPaid";
				break;
			case 22:
				strFieldName = "BeforeUsedLimit";
				break;
			case 23:
				strFieldName = "AfterUsedLimit";
				break;
			case 24:
				strFieldName = "ActualPay";
				break;
			case 25:
				strFieldName = "DutyFeeType";
				break;
			case 26:
				strFieldName = "DutyFeeCode";
				break;
			case 27:
				strFieldName = "DutyFeeStaNo";
				break;
			case 28:
				strFieldName = "AccidentDate";
				break;
			case 29:
				strFieldName = "Operator";
				break;
			case 30:
				strFieldName = "MakeDate";
				break;
			case 31:
				strFieldName = "MakeTime";
				break;
			case 32:
				strFieldName = "ModifyDate";
				break;
			case 33:
				strFieldName = "ModifyTime";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("SerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelatedInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalculatorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlFactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlFactorUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumApplyPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UsedLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumNotPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPaid") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BeforeUsedLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AfterUsedLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ActualPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DutyFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeStaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: nFieldIndex int
       * @return: int
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
