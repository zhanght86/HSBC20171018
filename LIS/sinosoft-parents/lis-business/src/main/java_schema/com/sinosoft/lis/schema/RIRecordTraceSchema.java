

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIRecordTraceDB;

/*
 * <p>ClassName: RIRecordTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIRecordTraceSchema implements Schema, Cloneable
{
	// @Field
	/** 序列号 */
	private String SerialNo;
	/** 批次号 */
	private String BatchNo;
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 其他号码 */
	private String OtherNo;
	/** 合同号码 */
	private String ContNo;
	/** 区域编号 */
	private String AreaID;
	/** 事件编号 */
	private String EventNo;
	/** 事件类型 */
	private String EventType;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 分保公司编号 */
	private String IncomeCompanyNo;
	/** 共保费用类型 */
	private String FeeType;
	/** 费用金额 */
	private double FeeMoney;
	/** 当前保额 */
	private double CurentAmnt;
	/** 分出保额 */
	private double CessionAmount;
	/** 分保比例 */
	private double CessionRate;
	/** 分保保额变量 */
	private double AmountChang;
	/** 分保保费变量 */
	private double PremChang;
	/** 分保佣金变量 */
	private double CommChang;
	/** 摊回赔款金额 */
	private double ReturnPay;
	/** 共保费用摊回 */
	private double ReturnFee;
	/** 保全增减标记 */
	private String AddSubFlag;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;
	/** 备用字符串属性4 */
	private String StandbyString4;
	/** 备用数字属性1 */
	private double StandbyDouble1;
	/** 备用数字属性2 */
	private double StandbyDouble2;
	/** 备用数字属性3 */
	private double StandbyDouble3;
	/** 备用数字属性4 */
	private double StandbyDouble4;
	/** 备用数字属性5 */
	private double StandbyDouble5;
	/** 备用数字属性6 */
	private double StandbyDouble6;
	/** 备用数字属性7 */
	private double StandbyDouble7;
	/** 备用数字属性8 */
	private double StandbyDouble8;
	/** 备用数字属性9 */
	private double StandbyDouble9;
	/** 备用日期属性1 */
	private double StandbyDate1;
	/** 备用日期属性2 */
	private double StandbyDate2;
	/** 临分标志 */
	private String ReinsureType;
	/** 分保日期 */
	private Date RIDate;
	/** 记账日期 */
	private Date FIDate;
	/** 币别 */
	private String Currency;
	/** 账单编号 */
	private String BillNo;
	/** 账单批次号 */
	private String BillBatchNo;
	/** 结算标志 */
	private String SettleFlag;
	/** 加费前分保保费 */
	private double PrePremChang;

	public static final int FIELDNUM = 46;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIRecordTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		RIRecordTraceSchema cloned = (RIRecordTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	/**
	* AccumulateMode = 01-个人单合同累计  OtherNo=contno 个人合同号码<p>
	* AccumulateMode = 02-个人多合同累计  OtherNo=contno 个人客户号码<p>
	* AccumulateMode = 03-多人多合同累计  OtherNo=“000000”
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 团单为contno 个单为contno + ',' + insuredno
	*/
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getAreaID()
	{
		return AreaID;
	}
	public void setAreaID(String aAreaID)
	{
		AreaID = aAreaID;
	}
	public String getEventNo()
	{
		return EventNo;
	}
	public void setEventNo(String aEventNo)
	{
		EventNo = aEventNo;
	}
	public String getEventType()
	{
		return EventType;
	}
	public void setEventType(String aEventType)
	{
		EventType = aEventType;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getIncomeCompanyNo()
	{
		return IncomeCompanyNo;
	}
	public void setIncomeCompanyNo(String aIncomeCompanyNo)
	{
		IncomeCompanyNo = aIncomeCompanyNo;
	}
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		FeeType = aFeeType;
	}
	public double getFeeMoney()
	{
		return FeeMoney;
	}
	public void setFeeMoney(double aFeeMoney)
	{
		FeeMoney = aFeeMoney;
	}
	public void setFeeMoney(String aFeeMoney)
	{
		if (aFeeMoney != null && !aFeeMoney.equals(""))
		{
			Double tDouble = new Double(aFeeMoney);
			double d = tDouble.doubleValue();
			FeeMoney = d;
		}
	}

	public double getCurentAmnt()
	{
		return CurentAmnt;
	}
	public void setCurentAmnt(double aCurentAmnt)
	{
		CurentAmnt = aCurentAmnt;
	}
	public void setCurentAmnt(String aCurentAmnt)
	{
		if (aCurentAmnt != null && !aCurentAmnt.equals(""))
		{
			Double tDouble = new Double(aCurentAmnt);
			double d = tDouble.doubleValue();
			CurentAmnt = d;
		}
	}

	public double getCessionAmount()
	{
		return CessionAmount;
	}
	public void setCessionAmount(double aCessionAmount)
	{
		CessionAmount = aCessionAmount;
	}
	public void setCessionAmount(String aCessionAmount)
	{
		if (aCessionAmount != null && !aCessionAmount.equals(""))
		{
			Double tDouble = new Double(aCessionAmount);
			double d = tDouble.doubleValue();
			CessionAmount = d;
		}
	}

	public double getCessionRate()
	{
		return CessionRate;
	}
	public void setCessionRate(double aCessionRate)
	{
		CessionRate = aCessionRate;
	}
	public void setCessionRate(String aCessionRate)
	{
		if (aCessionRate != null && !aCessionRate.equals(""))
		{
			Double tDouble = new Double(aCessionRate);
			double d = tDouble.doubleValue();
			CessionRate = d;
		}
	}

	public double getAmountChang()
	{
		return AmountChang;
	}
	public void setAmountChang(double aAmountChang)
	{
		AmountChang = aAmountChang;
	}
	public void setAmountChang(String aAmountChang)
	{
		if (aAmountChang != null && !aAmountChang.equals(""))
		{
			Double tDouble = new Double(aAmountChang);
			double d = tDouble.doubleValue();
			AmountChang = d;
		}
	}

	public double getPremChang()
	{
		return PremChang;
	}
	public void setPremChang(double aPremChang)
	{
		PremChang = aPremChang;
	}
	public void setPremChang(String aPremChang)
	{
		if (aPremChang != null && !aPremChang.equals(""))
		{
			Double tDouble = new Double(aPremChang);
			double d = tDouble.doubleValue();
			PremChang = d;
		}
	}

	public double getCommChang()
	{
		return CommChang;
	}
	public void setCommChang(double aCommChang)
	{
		CommChang = aCommChang;
	}
	public void setCommChang(String aCommChang)
	{
		if (aCommChang != null && !aCommChang.equals(""))
		{
			Double tDouble = new Double(aCommChang);
			double d = tDouble.doubleValue();
			CommChang = d;
		}
	}

	public double getReturnPay()
	{
		return ReturnPay;
	}
	public void setReturnPay(double aReturnPay)
	{
		ReturnPay = aReturnPay;
	}
	public void setReturnPay(String aReturnPay)
	{
		if (aReturnPay != null && !aReturnPay.equals(""))
		{
			Double tDouble = new Double(aReturnPay);
			double d = tDouble.doubleValue();
			ReturnPay = d;
		}
	}

	public double getReturnFee()
	{
		return ReturnFee;
	}
	public void setReturnFee(double aReturnFee)
	{
		ReturnFee = aReturnFee;
	}
	public void setReturnFee(String aReturnFee)
	{
		if (aReturnFee != null && !aReturnFee.equals(""))
		{
			Double tDouble = new Double(aReturnFee);
			double d = tDouble.doubleValue();
			ReturnFee = d;
		}
	}

	/**
	* 1－增额<p>
	* 2－减额<p>
	* 保额增加与减少采用不同算法
	*/
	public String getAddSubFlag()
	{
		return AddSubFlag;
	}
	public void setAddSubFlag(String aAddSubFlag)
	{
		AddSubFlag = aAddSubFlag;
	}
	/**
	* 临分区域标志<p>
	* 00：合同分保部分; 03：临分部分
	*/
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	public String getStandbyString4()
	{
		return StandbyString4;
	}
	public void setStandbyString4(String aStandbyString4)
	{
		StandbyString4 = aStandbyString4;
	}
	public double getStandbyDouble1()
	{
		return StandbyDouble1;
	}
	public void setStandbyDouble1(double aStandbyDouble1)
	{
		StandbyDouble1 = aStandbyDouble1;
	}
	public void setStandbyDouble1(String aStandbyDouble1)
	{
		if (aStandbyDouble1 != null && !aStandbyDouble1.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble1);
			double d = tDouble.doubleValue();
			StandbyDouble1 = d;
		}
	}

	public double getStandbyDouble2()
	{
		return StandbyDouble2;
	}
	public void setStandbyDouble2(double aStandbyDouble2)
	{
		StandbyDouble2 = aStandbyDouble2;
	}
	public void setStandbyDouble2(String aStandbyDouble2)
	{
		if (aStandbyDouble2 != null && !aStandbyDouble2.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble2);
			double d = tDouble.doubleValue();
			StandbyDouble2 = d;
		}
	}

	public double getStandbyDouble3()
	{
		return StandbyDouble3;
	}
	public void setStandbyDouble3(double aStandbyDouble3)
	{
		StandbyDouble3 = aStandbyDouble3;
	}
	public void setStandbyDouble3(String aStandbyDouble3)
	{
		if (aStandbyDouble3 != null && !aStandbyDouble3.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble3);
			double d = tDouble.doubleValue();
			StandbyDouble3 = d;
		}
	}

	public double getStandbyDouble4()
	{
		return StandbyDouble4;
	}
	public void setStandbyDouble4(double aStandbyDouble4)
	{
		StandbyDouble4 = aStandbyDouble4;
	}
	public void setStandbyDouble4(String aStandbyDouble4)
	{
		if (aStandbyDouble4 != null && !aStandbyDouble4.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble4);
			double d = tDouble.doubleValue();
			StandbyDouble4 = d;
		}
	}

	public double getStandbyDouble5()
	{
		return StandbyDouble5;
	}
	public void setStandbyDouble5(double aStandbyDouble5)
	{
		StandbyDouble5 = aStandbyDouble5;
	}
	public void setStandbyDouble5(String aStandbyDouble5)
	{
		if (aStandbyDouble5 != null && !aStandbyDouble5.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble5);
			double d = tDouble.doubleValue();
			StandbyDouble5 = d;
		}
	}

	public double getStandbyDouble6()
	{
		return StandbyDouble6;
	}
	public void setStandbyDouble6(double aStandbyDouble6)
	{
		StandbyDouble6 = aStandbyDouble6;
	}
	public void setStandbyDouble6(String aStandbyDouble6)
	{
		if (aStandbyDouble6 != null && !aStandbyDouble6.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble6);
			double d = tDouble.doubleValue();
			StandbyDouble6 = d;
		}
	}

	public double getStandbyDouble7()
	{
		return StandbyDouble7;
	}
	public void setStandbyDouble7(double aStandbyDouble7)
	{
		StandbyDouble7 = aStandbyDouble7;
	}
	public void setStandbyDouble7(String aStandbyDouble7)
	{
		if (aStandbyDouble7 != null && !aStandbyDouble7.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble7);
			double d = tDouble.doubleValue();
			StandbyDouble7 = d;
		}
	}

	public double getStandbyDouble8()
	{
		return StandbyDouble8;
	}
	public void setStandbyDouble8(double aStandbyDouble8)
	{
		StandbyDouble8 = aStandbyDouble8;
	}
	public void setStandbyDouble8(String aStandbyDouble8)
	{
		if (aStandbyDouble8 != null && !aStandbyDouble8.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble8);
			double d = tDouble.doubleValue();
			StandbyDouble8 = d;
		}
	}

	public double getStandbyDouble9()
	{
		return StandbyDouble9;
	}
	public void setStandbyDouble9(double aStandbyDouble9)
	{
		StandbyDouble9 = aStandbyDouble9;
	}
	public void setStandbyDouble9(String aStandbyDouble9)
	{
		if (aStandbyDouble9 != null && !aStandbyDouble9.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble9);
			double d = tDouble.doubleValue();
			StandbyDouble9 = d;
		}
	}

	public double getStandbyDate1()
	{
		return StandbyDate1;
	}
	public void setStandbyDate1(double aStandbyDate1)
	{
		StandbyDate1 = aStandbyDate1;
	}
	public void setStandbyDate1(String aStandbyDate1)
	{
		if (aStandbyDate1 != null && !aStandbyDate1.equals(""))
		{
			Double tDouble = new Double(aStandbyDate1);
			double d = tDouble.doubleValue();
			StandbyDate1 = d;
		}
	}

	public double getStandbyDate2()
	{
		return StandbyDate2;
	}
	public void setStandbyDate2(double aStandbyDate2)
	{
		StandbyDate2 = aStandbyDate2;
	}
	public void setStandbyDate2(String aStandbyDate2)
	{
		if (aStandbyDate2 != null && !aStandbyDate2.equals(""))
		{
			Double tDouble = new Double(aStandbyDate2);
			double d = tDouble.doubleValue();
			StandbyDate2 = d;
		}
	}

	/**
	* 1-临分合同,2-正常合同
	*/
	public String getReinsureType()
	{
		return ReinsureType;
	}
	public void setReinsureType(String aReinsureType)
	{
		ReinsureType = aReinsureType;
	}
	public String getRIDate()
	{
		if( RIDate != null )
			return fDate.getString(RIDate);
		else
			return null;
	}
	public void setRIDate(Date aRIDate)
	{
		RIDate = aRIDate;
	}
	public void setRIDate(String aRIDate)
	{
		if (aRIDate != null && !aRIDate.equals("") )
		{
			RIDate = fDate.getDate( aRIDate );
		}
		else
			RIDate = null;
	}

	public String getFIDate()
	{
		if( FIDate != null )
			return fDate.getString(FIDate);
		else
			return null;
	}
	public void setFIDate(Date aFIDate)
	{
		FIDate = aFIDate;
	}
	public void setFIDate(String aFIDate)
	{
		if (aFIDate != null && !aFIDate.equals("") )
		{
			FIDate = fDate.getDate( aFIDate );
		}
		else
			FIDate = null;
	}

	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getBillNo()
	{
		return BillNo;
	}
	public void setBillNo(String aBillNo)
	{
		BillNo = aBillNo;
	}
	public String getBillBatchNo()
	{
		return BillBatchNo;
	}
	public void setBillBatchNo(String aBillBatchNo)
	{
		BillBatchNo = aBillBatchNo;
	}
	public String getSettleFlag()
	{
		return SettleFlag;
	}
	public void setSettleFlag(String aSettleFlag)
	{
		SettleFlag = aSettleFlag;
	}
	public double getPrePremChang()
	{
		return PrePremChang;
	}
	public void setPrePremChang(double aPrePremChang)
	{
		PrePremChang = aPrePremChang;
	}
	public void setPrePremChang(String aPrePremChang)
	{
		if (aPrePremChang != null && !aPrePremChang.equals(""))
		{
			Double tDouble = new Double(aPrePremChang);
			double d = tDouble.doubleValue();
			PrePremChang = d;
		}
	}


	/**
	* 使用另外一个 RIRecordTraceSchema 对象给 Schema 赋值
	* @param: aRIRecordTraceSchema RIRecordTraceSchema
	**/
	public void setSchema(RIRecordTraceSchema aRIRecordTraceSchema)
	{
		this.SerialNo = aRIRecordTraceSchema.getSerialNo();
		this.BatchNo = aRIRecordTraceSchema.getBatchNo();
		this.AccumulateDefNO = aRIRecordTraceSchema.getAccumulateDefNO();
		this.RIPreceptNo = aRIRecordTraceSchema.getRIPreceptNo();
		this.OtherNo = aRIRecordTraceSchema.getOtherNo();
		this.ContNo = aRIRecordTraceSchema.getContNo();
		this.AreaID = aRIRecordTraceSchema.getAreaID();
		this.EventNo = aRIRecordTraceSchema.getEventNo();
		this.EventType = aRIRecordTraceSchema.getEventType();
		this.RiskCode = aRIRecordTraceSchema.getRiskCode();
		this.DutyCode = aRIRecordTraceSchema.getDutyCode();
		this.IncomeCompanyNo = aRIRecordTraceSchema.getIncomeCompanyNo();
		this.FeeType = aRIRecordTraceSchema.getFeeType();
		this.FeeMoney = aRIRecordTraceSchema.getFeeMoney();
		this.CurentAmnt = aRIRecordTraceSchema.getCurentAmnt();
		this.CessionAmount = aRIRecordTraceSchema.getCessionAmount();
		this.CessionRate = aRIRecordTraceSchema.getCessionRate();
		this.AmountChang = aRIRecordTraceSchema.getAmountChang();
		this.PremChang = aRIRecordTraceSchema.getPremChang();
		this.CommChang = aRIRecordTraceSchema.getCommChang();
		this.ReturnPay = aRIRecordTraceSchema.getReturnPay();
		this.ReturnFee = aRIRecordTraceSchema.getReturnFee();
		this.AddSubFlag = aRIRecordTraceSchema.getAddSubFlag();
		this.StandbyString1 = aRIRecordTraceSchema.getStandbyString1();
		this.StandbyString2 = aRIRecordTraceSchema.getStandbyString2();
		this.StandbyString3 = aRIRecordTraceSchema.getStandbyString3();
		this.StandbyString4 = aRIRecordTraceSchema.getStandbyString4();
		this.StandbyDouble1 = aRIRecordTraceSchema.getStandbyDouble1();
		this.StandbyDouble2 = aRIRecordTraceSchema.getStandbyDouble2();
		this.StandbyDouble3 = aRIRecordTraceSchema.getStandbyDouble3();
		this.StandbyDouble4 = aRIRecordTraceSchema.getStandbyDouble4();
		this.StandbyDouble5 = aRIRecordTraceSchema.getStandbyDouble5();
		this.StandbyDouble6 = aRIRecordTraceSchema.getStandbyDouble6();
		this.StandbyDouble7 = aRIRecordTraceSchema.getStandbyDouble7();
		this.StandbyDouble8 = aRIRecordTraceSchema.getStandbyDouble8();
		this.StandbyDouble9 = aRIRecordTraceSchema.getStandbyDouble9();
		this.StandbyDate1 = aRIRecordTraceSchema.getStandbyDate1();
		this.StandbyDate2 = aRIRecordTraceSchema.getStandbyDate2();
		this.ReinsureType = aRIRecordTraceSchema.getReinsureType();
		this.RIDate = fDate.getDate( aRIRecordTraceSchema.getRIDate());
		this.FIDate = fDate.getDate( aRIRecordTraceSchema.getFIDate());
		this.Currency = aRIRecordTraceSchema.getCurrency();
		this.BillNo = aRIRecordTraceSchema.getBillNo();
		this.BillBatchNo = aRIRecordTraceSchema.getBillBatchNo();
		this.SettleFlag = aRIRecordTraceSchema.getSettleFlag();
		this.PrePremChang = aRIRecordTraceSchema.getPrePremChang();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("AreaID") == null )
				this.AreaID = null;
			else
				this.AreaID = rs.getString("AreaID").trim();

			if( rs.getString("EventNo") == null )
				this.EventNo = null;
			else
				this.EventNo = rs.getString("EventNo").trim();

			if( rs.getString("EventType") == null )
				this.EventType = null;
			else
				this.EventType = rs.getString("EventType").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("IncomeCompanyNo") == null )
				this.IncomeCompanyNo = null;
			else
				this.IncomeCompanyNo = rs.getString("IncomeCompanyNo").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			this.FeeMoney = rs.getDouble("FeeMoney");
			this.CurentAmnt = rs.getDouble("CurentAmnt");
			this.CessionAmount = rs.getDouble("CessionAmount");
			this.CessionRate = rs.getDouble("CessionRate");
			this.AmountChang = rs.getDouble("AmountChang");
			this.PremChang = rs.getDouble("PremChang");
			this.CommChang = rs.getDouble("CommChang");
			this.ReturnPay = rs.getDouble("ReturnPay");
			this.ReturnFee = rs.getDouble("ReturnFee");
			if( rs.getString("AddSubFlag") == null )
				this.AddSubFlag = null;
			else
				this.AddSubFlag = rs.getString("AddSubFlag").trim();

			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			if( rs.getString("StandbyString4") == null )
				this.StandbyString4 = null;
			else
				this.StandbyString4 = rs.getString("StandbyString4").trim();

			this.StandbyDouble1 = rs.getDouble("StandbyDouble1");
			this.StandbyDouble2 = rs.getDouble("StandbyDouble2");
			this.StandbyDouble3 = rs.getDouble("StandbyDouble3");
			this.StandbyDouble4 = rs.getDouble("StandbyDouble4");
			this.StandbyDouble5 = rs.getDouble("StandbyDouble5");
			this.StandbyDouble6 = rs.getDouble("StandbyDouble6");
			this.StandbyDouble7 = rs.getDouble("StandbyDouble7");
			this.StandbyDouble8 = rs.getDouble("StandbyDouble8");
			this.StandbyDouble9 = rs.getDouble("StandbyDouble9");
			this.StandbyDate1 = rs.getDouble("StandbyDate1");
			this.StandbyDate2 = rs.getDouble("StandbyDate2");
			if( rs.getString("ReinsureType") == null )
				this.ReinsureType = null;
			else
				this.ReinsureType = rs.getString("ReinsureType").trim();

			this.RIDate = rs.getDate("RIDate");
			this.FIDate = rs.getDate("FIDate");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("BillNo") == null )
				this.BillNo = null;
			else
				this.BillNo = rs.getString("BillNo").trim();

			if( rs.getString("BillBatchNo") == null )
				this.BillBatchNo = null;
			else
				this.BillBatchNo = rs.getString("BillBatchNo").trim();

			if( rs.getString("SettleFlag") == null )
				this.SettleFlag = null;
			else
				this.SettleFlag = rs.getString("SettleFlag").trim();

			this.PrePremChang = rs.getDouble("PrePremChang");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIRecordTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIRecordTraceSchema getSchema()
	{
		RIRecordTraceSchema aRIRecordTraceSchema = new RIRecordTraceSchema();
		aRIRecordTraceSchema.setSchema(this);
		return aRIRecordTraceSchema;
	}

	public RIRecordTraceDB getDB()
	{
		RIRecordTraceDB aDBOper = new RIRecordTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIRecordTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EventNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EventType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IncomeCompanyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurentAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AmountChang));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremChang));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommChang));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReturnPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReturnFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddSubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble9));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDate1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDate2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RIDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FIDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillBatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SettleFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrePremChang));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIRecordTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AreaID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EventNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			EventType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IncomeCompanyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			FeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			CurentAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			CessionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			CessionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			AmountChang = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			PremChang = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			CommChang = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			ReturnPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			ReturnFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			AddSubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			StandbyString4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			StandbyDouble1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble9 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDate1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDate2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			ReinsureType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			RIDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			FIDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41,SysConst.PACKAGESPILTER));
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			BillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			BillBatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			SettleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			PrePremChang = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaID));
		}
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventNo));
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventType));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyNo));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("FeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMoney));
		}
		if (FCode.equalsIgnoreCase("CurentAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurentAmnt));
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionAmount));
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionRate));
		}
		if (FCode.equalsIgnoreCase("AmountChang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmountChang));
		}
		if (FCode.equalsIgnoreCase("PremChang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremChang));
		}
		if (FCode.equalsIgnoreCase("CommChang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommChang));
		}
		if (FCode.equalsIgnoreCase("ReturnPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnPay));
		}
		if (FCode.equalsIgnoreCase("ReturnFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnFee));
		}
		if (FCode.equalsIgnoreCase("AddSubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddSubFlag));
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("StandbyString4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString4));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble1));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble2));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble3));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble4));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble5));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble6));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble7));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble8));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble9));
		}
		if (FCode.equalsIgnoreCase("StandbyDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDate1));
		}
		if (FCode.equalsIgnoreCase("StandbyDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDate2));
		}
		if (FCode.equalsIgnoreCase("ReinsureType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureType));
		}
		if (FCode.equalsIgnoreCase("RIDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRIDate()));
		}
		if (FCode.equalsIgnoreCase("FIDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFIDate()));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillNo));
		}
		if (FCode.equalsIgnoreCase("BillBatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillBatchNo));
		}
		if (FCode.equalsIgnoreCase("SettleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SettleFlag));
		}
		if (FCode.equalsIgnoreCase("PrePremChang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrePremChang));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AreaID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(EventNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(EventType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IncomeCompanyNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 13:
				strFieldValue = String.valueOf(FeeMoney);
				break;
			case 14:
				strFieldValue = String.valueOf(CurentAmnt);
				break;
			case 15:
				strFieldValue = String.valueOf(CessionAmount);
				break;
			case 16:
				strFieldValue = String.valueOf(CessionRate);
				break;
			case 17:
				strFieldValue = String.valueOf(AmountChang);
				break;
			case 18:
				strFieldValue = String.valueOf(PremChang);
				break;
			case 19:
				strFieldValue = String.valueOf(CommChang);
				break;
			case 20:
				strFieldValue = String.valueOf(ReturnPay);
				break;
			case 21:
				strFieldValue = String.valueOf(ReturnFee);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AddSubFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(StandbyString4);
				break;
			case 27:
				strFieldValue = String.valueOf(StandbyDouble1);
				break;
			case 28:
				strFieldValue = String.valueOf(StandbyDouble2);
				break;
			case 29:
				strFieldValue = String.valueOf(StandbyDouble3);
				break;
			case 30:
				strFieldValue = String.valueOf(StandbyDouble4);
				break;
			case 31:
				strFieldValue = String.valueOf(StandbyDouble5);
				break;
			case 32:
				strFieldValue = String.valueOf(StandbyDouble6);
				break;
			case 33:
				strFieldValue = String.valueOf(StandbyDouble7);
				break;
			case 34:
				strFieldValue = String.valueOf(StandbyDouble8);
				break;
			case 35:
				strFieldValue = String.valueOf(StandbyDouble9);
				break;
			case 36:
				strFieldValue = String.valueOf(StandbyDate1);
				break;
			case 37:
				strFieldValue = String.valueOf(StandbyDate2);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ReinsureType);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRIDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFIDate()));
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(BillNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(BillBatchNo);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(SettleFlag);
				break;
			case 45:
				strFieldValue = String.valueOf(PrePremChang);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
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
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaID = FValue.trim();
			}
			else
				AreaID = null;
		}
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventNo = FValue.trim();
			}
			else
				EventNo = null;
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventType = FValue.trim();
			}
			else
				EventType = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IncomeCompanyNo = FValue.trim();
			}
			else
				IncomeCompanyNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		if (FCode.equalsIgnoreCase("FeeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurentAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurentAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AmountChang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AmountChang = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremChang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremChang = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommChang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommChang = d;
			}
		}
		if (FCode.equalsIgnoreCase("ReturnPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ReturnPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("ReturnFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ReturnFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("AddSubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddSubFlag = FValue.trim();
			}
			else
				AddSubFlag = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString4 = FValue.trim();
			}
			else
				StandbyString4 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble9 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDate1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDate1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDate2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDate2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ReinsureType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureType = FValue.trim();
			}
			else
				ReinsureType = null;
		}
		if (FCode.equalsIgnoreCase("RIDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RIDate = fDate.getDate( FValue );
			}
			else
				RIDate = null;
		}
		if (FCode.equalsIgnoreCase("FIDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FIDate = fDate.getDate( FValue );
			}
			else
				FIDate = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillNo = FValue.trim();
			}
			else
				BillNo = null;
		}
		if (FCode.equalsIgnoreCase("BillBatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillBatchNo = FValue.trim();
			}
			else
				BillBatchNo = null;
		}
		if (FCode.equalsIgnoreCase("SettleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SettleFlag = FValue.trim();
			}
			else
				SettleFlag = null;
		}
		if (FCode.equalsIgnoreCase("PrePremChang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrePremChang = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIRecordTraceSchema other = (RIRecordTraceSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& BatchNo.equals(other.getBatchNo())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& OtherNo.equals(other.getOtherNo())
			&& ContNo.equals(other.getContNo())
			&& AreaID.equals(other.getAreaID())
			&& EventNo.equals(other.getEventNo())
			&& EventType.equals(other.getEventType())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& IncomeCompanyNo.equals(other.getIncomeCompanyNo())
			&& FeeType.equals(other.getFeeType())
			&& FeeMoney == other.getFeeMoney()
			&& CurentAmnt == other.getCurentAmnt()
			&& CessionAmount == other.getCessionAmount()
			&& CessionRate == other.getCessionRate()
			&& AmountChang == other.getAmountChang()
			&& PremChang == other.getPremChang()
			&& CommChang == other.getCommChang()
			&& ReturnPay == other.getReturnPay()
			&& ReturnFee == other.getReturnFee()
			&& AddSubFlag.equals(other.getAddSubFlag())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& StandbyString4.equals(other.getStandbyString4())
			&& StandbyDouble1 == other.getStandbyDouble1()
			&& StandbyDouble2 == other.getStandbyDouble2()
			&& StandbyDouble3 == other.getStandbyDouble3()
			&& StandbyDouble4 == other.getStandbyDouble4()
			&& StandbyDouble5 == other.getStandbyDouble5()
			&& StandbyDouble6 == other.getStandbyDouble6()
			&& StandbyDouble7 == other.getStandbyDouble7()
			&& StandbyDouble8 == other.getStandbyDouble8()
			&& StandbyDouble9 == other.getStandbyDouble9()
			&& StandbyDate1 == other.getStandbyDate1()
			&& StandbyDate2 == other.getStandbyDate2()
			&& ReinsureType.equals(other.getReinsureType())
			&& fDate.getString(RIDate).equals(other.getRIDate())
			&& fDate.getString(FIDate).equals(other.getFIDate())
			&& Currency.equals(other.getCurrency())
			&& BillNo.equals(other.getBillNo())
			&& BillBatchNo.equals(other.getBillBatchNo())
			&& SettleFlag.equals(other.getSettleFlag())
			&& PrePremChang == other.getPrePremChang();
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 1;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 2;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 4;
		}
		if( strFieldName.equals("ContNo") ) {
			return 5;
		}
		if( strFieldName.equals("AreaID") ) {
			return 6;
		}
		if( strFieldName.equals("EventNo") ) {
			return 7;
		}
		if( strFieldName.equals("EventType") ) {
			return 8;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 9;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 10;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return 11;
		}
		if( strFieldName.equals("FeeType") ) {
			return 12;
		}
		if( strFieldName.equals("FeeMoney") ) {
			return 13;
		}
		if( strFieldName.equals("CurentAmnt") ) {
			return 14;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return 15;
		}
		if( strFieldName.equals("CessionRate") ) {
			return 16;
		}
		if( strFieldName.equals("AmountChang") ) {
			return 17;
		}
		if( strFieldName.equals("PremChang") ) {
			return 18;
		}
		if( strFieldName.equals("CommChang") ) {
			return 19;
		}
		if( strFieldName.equals("ReturnPay") ) {
			return 20;
		}
		if( strFieldName.equals("ReturnFee") ) {
			return 21;
		}
		if( strFieldName.equals("AddSubFlag") ) {
			return 22;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 23;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 24;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 25;
		}
		if( strFieldName.equals("StandbyString4") ) {
			return 26;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return 27;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return 28;
		}
		if( strFieldName.equals("StandbyDouble3") ) {
			return 29;
		}
		if( strFieldName.equals("StandbyDouble4") ) {
			return 30;
		}
		if( strFieldName.equals("StandbyDouble5") ) {
			return 31;
		}
		if( strFieldName.equals("StandbyDouble6") ) {
			return 32;
		}
		if( strFieldName.equals("StandbyDouble7") ) {
			return 33;
		}
		if( strFieldName.equals("StandbyDouble8") ) {
			return 34;
		}
		if( strFieldName.equals("StandbyDouble9") ) {
			return 35;
		}
		if( strFieldName.equals("StandbyDate1") ) {
			return 36;
		}
		if( strFieldName.equals("StandbyDate2") ) {
			return 37;
		}
		if( strFieldName.equals("ReinsureType") ) {
			return 38;
		}
		if( strFieldName.equals("RIDate") ) {
			return 39;
		}
		if( strFieldName.equals("FIDate") ) {
			return 40;
		}
		if( strFieldName.equals("Currency") ) {
			return 41;
		}
		if( strFieldName.equals("BillNo") ) {
			return 42;
		}
		if( strFieldName.equals("BillBatchNo") ) {
			return 43;
		}
		if( strFieldName.equals("SettleFlag") ) {
			return 44;
		}
		if( strFieldName.equals("PrePremChang") ) {
			return 45;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "BatchNo";
				break;
			case 2:
				strFieldName = "AccumulateDefNO";
				break;
			case 3:
				strFieldName = "RIPreceptNo";
				break;
			case 4:
				strFieldName = "OtherNo";
				break;
			case 5:
				strFieldName = "ContNo";
				break;
			case 6:
				strFieldName = "AreaID";
				break;
			case 7:
				strFieldName = "EventNo";
				break;
			case 8:
				strFieldName = "EventType";
				break;
			case 9:
				strFieldName = "RiskCode";
				break;
			case 10:
				strFieldName = "DutyCode";
				break;
			case 11:
				strFieldName = "IncomeCompanyNo";
				break;
			case 12:
				strFieldName = "FeeType";
				break;
			case 13:
				strFieldName = "FeeMoney";
				break;
			case 14:
				strFieldName = "CurentAmnt";
				break;
			case 15:
				strFieldName = "CessionAmount";
				break;
			case 16:
				strFieldName = "CessionRate";
				break;
			case 17:
				strFieldName = "AmountChang";
				break;
			case 18:
				strFieldName = "PremChang";
				break;
			case 19:
				strFieldName = "CommChang";
				break;
			case 20:
				strFieldName = "ReturnPay";
				break;
			case 21:
				strFieldName = "ReturnFee";
				break;
			case 22:
				strFieldName = "AddSubFlag";
				break;
			case 23:
				strFieldName = "StandbyString1";
				break;
			case 24:
				strFieldName = "StandbyString2";
				break;
			case 25:
				strFieldName = "StandbyString3";
				break;
			case 26:
				strFieldName = "StandbyString4";
				break;
			case 27:
				strFieldName = "StandbyDouble1";
				break;
			case 28:
				strFieldName = "StandbyDouble2";
				break;
			case 29:
				strFieldName = "StandbyDouble3";
				break;
			case 30:
				strFieldName = "StandbyDouble4";
				break;
			case 31:
				strFieldName = "StandbyDouble5";
				break;
			case 32:
				strFieldName = "StandbyDouble6";
				break;
			case 33:
				strFieldName = "StandbyDouble7";
				break;
			case 34:
				strFieldName = "StandbyDouble8";
				break;
			case 35:
				strFieldName = "StandbyDouble9";
				break;
			case 36:
				strFieldName = "StandbyDate1";
				break;
			case 37:
				strFieldName = "StandbyDate2";
				break;
			case 38:
				strFieldName = "ReinsureType";
				break;
			case 39:
				strFieldName = "RIDate";
				break;
			case 40:
				strFieldName = "FIDate";
				break;
			case 41:
				strFieldName = "Currency";
				break;
			case 42:
				strFieldName = "BillNo";
				break;
			case 43:
				strFieldName = "BillBatchNo";
				break;
			case 44:
				strFieldName = "SettleFlag";
				break;
			case 45:
				strFieldName = "PrePremChang";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurentAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AmountChang") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremChang") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommChang") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ReturnPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ReturnFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddSubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble9") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDate1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDate2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ReinsureType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FIDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillBatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SettleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrePremChang") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
