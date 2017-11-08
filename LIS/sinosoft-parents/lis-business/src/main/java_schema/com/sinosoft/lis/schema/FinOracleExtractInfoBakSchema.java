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
import com.sinosoft.lis.db.FinOracleExtractInfoBakDB;

/*
 * <p>ClassName: FinOracleExtractInfoBakSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class FinOracleExtractInfoBakSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FinOracleExtractInfoBakSchema.class);
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 批次 */
	private String SourceBatchID;
	/** 记账日期 */
	private Date AccountingDate;
	/** 币种 */
	private String CurrencyCode;
	/** 汇率日期 */
	private Date CurrencyConversionDate;
	/** 汇率 */
	private double CurrencyConversionRate;
	/** 汇率类型 */
	private String CurrencyConversionType;
	/** 借方录入 */
	private double EnteredDr;
	/** 贷方录入 */
	private double EnteredCr;
	/** 借方记账 */
	private double AccountedDr;
	/** 贷方记账 */
	private double AccountedCr;
	/** 金额类型 */
	private String ActualFlag;
	/** 财务公司代码 */
	private String Segment1;
	/** 成本中心 */
	private String Segment2;
	/** 会计科目 */
	private String Segment3;
	/** 银行明细 */
	private String Segment4;
	/** 渠道 */
	private String Segment5;
	/** 险种代码 */
	private String Segment6;
	/** 缴费期 */
	private String Segment7;
	/** 预算段 */
	private String Segment8;
	/** 备用1 */
	private String Segment9;
	/** 行说明 */
	private String LineDescription;
	/** 弹性域1 */
	private String Attribute1;
	/** 弹性域2 */
	private String Attribute2;
	/** 弹性域3 */
	private String Attribute3;
	/** 弹性域4 */
	private String Attribute4;
	/** 弹性域5 */
	private String Attribute5;
	/** 弹性域6 */
	private String Attribute6;
	/** 弹性域7 */
	private String Attribute7;
	/** 弹性域8 */
	private String Attribute8;
	/** 弹性域9 */
	private String Attribute9;
	/** 弹性域10 */
	private String Attribute10;
	/** 弹性域11 */
	private String Attribute11;
	/** 弹性域12 */
	private String Attribute12;
	/** 弹性域13 */
	private String Attribute13;
	/** 弹性域14 */
	private String Attribute14;
	/** 弹性域15 */
	private String Attribute15;
	/** 数据来源 */
	private String DataSource;
	/** 状态 */
	private String State;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 删除管理机构 */
	private String DeleteManageCom;
	/** 删除公司代码 */
	private String DeleteComCode;
	/** 删除操作员 */
	private String DeleteOperator;
	/** 删除日期 */
	private Date DeleteDate;
	/** 删除时间 */
	private String DeleteTime;

	public static final int FIELDNUM = 49;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FinOracleExtractInfoBakSchema()
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
		FinOracleExtractInfoBakSchema cloned = (FinOracleExtractInfoBakSchema)super.clone();
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
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getSourceBatchID()
	{
		return SourceBatchID;
	}
	public void setSourceBatchID(String aSourceBatchID)
	{
		if(aSourceBatchID!=null && aSourceBatchID.length()>20)
			throw new IllegalArgumentException("批次SourceBatchID值"+aSourceBatchID+"的长度"+aSourceBatchID.length()+"大于最大值20");
		SourceBatchID = aSourceBatchID;
	}
	public String getAccountingDate()
	{
		if( AccountingDate != null )
			return fDate.getString(AccountingDate);
		else
			return null;
	}
	public void setAccountingDate(Date aAccountingDate)
	{
		AccountingDate = aAccountingDate;
	}
	public void setAccountingDate(String aAccountingDate)
	{
		if (aAccountingDate != null && !aAccountingDate.equals("") )
		{
			AccountingDate = fDate.getDate( aAccountingDate );
		}
		else
			AccountingDate = null;
	}

	public String getCurrencyCode()
	{
		return CurrencyCode;
	}
	public void setCurrencyCode(String aCurrencyCode)
	{
		if(aCurrencyCode!=null && aCurrencyCode.length()>10)
			throw new IllegalArgumentException("币种CurrencyCode值"+aCurrencyCode+"的长度"+aCurrencyCode.length()+"大于最大值10");
		CurrencyCode = aCurrencyCode;
	}
	public String getCurrencyConversionDate()
	{
		if( CurrencyConversionDate != null )
			return fDate.getString(CurrencyConversionDate);
		else
			return null;
	}
	public void setCurrencyConversionDate(Date aCurrencyConversionDate)
	{
		CurrencyConversionDate = aCurrencyConversionDate;
	}
	public void setCurrencyConversionDate(String aCurrencyConversionDate)
	{
		if (aCurrencyConversionDate != null && !aCurrencyConversionDate.equals("") )
		{
			CurrencyConversionDate = fDate.getDate( aCurrencyConversionDate );
		}
		else
			CurrencyConversionDate = null;
	}

	public double getCurrencyConversionRate()
	{
		return CurrencyConversionRate;
	}
	public void setCurrencyConversionRate(double aCurrencyConversionRate)
	{
		CurrencyConversionRate = aCurrencyConversionRate;
	}
	public void setCurrencyConversionRate(String aCurrencyConversionRate)
	{
		if (aCurrencyConversionRate != null && !aCurrencyConversionRate.equals(""))
		{
			Double tDouble = new Double(aCurrencyConversionRate);
			double d = tDouble.doubleValue();
			CurrencyConversionRate = d;
		}
	}

	public String getCurrencyConversionType()
	{
		return CurrencyConversionType;
	}
	public void setCurrencyConversionType(String aCurrencyConversionType)
	{
		if(aCurrencyConversionType!=null && aCurrencyConversionType.length()>10)
			throw new IllegalArgumentException("汇率类型CurrencyConversionType值"+aCurrencyConversionType+"的长度"+aCurrencyConversionType.length()+"大于最大值10");
		CurrencyConversionType = aCurrencyConversionType;
	}
	public double getEnteredDr()
	{
		return EnteredDr;
	}
	public void setEnteredDr(double aEnteredDr)
	{
		EnteredDr = aEnteredDr;
	}
	public void setEnteredDr(String aEnteredDr)
	{
		if (aEnteredDr != null && !aEnteredDr.equals(""))
		{
			Double tDouble = new Double(aEnteredDr);
			double d = tDouble.doubleValue();
			EnteredDr = d;
		}
	}

	public double getEnteredCr()
	{
		return EnteredCr;
	}
	public void setEnteredCr(double aEnteredCr)
	{
		EnteredCr = aEnteredCr;
	}
	public void setEnteredCr(String aEnteredCr)
	{
		if (aEnteredCr != null && !aEnteredCr.equals(""))
		{
			Double tDouble = new Double(aEnteredCr);
			double d = tDouble.doubleValue();
			EnteredCr = d;
		}
	}

	public double getAccountedDr()
	{
		return AccountedDr;
	}
	public void setAccountedDr(double aAccountedDr)
	{
		AccountedDr = aAccountedDr;
	}
	public void setAccountedDr(String aAccountedDr)
	{
		if (aAccountedDr != null && !aAccountedDr.equals(""))
		{
			Double tDouble = new Double(aAccountedDr);
			double d = tDouble.doubleValue();
			AccountedDr = d;
		}
	}

	public double getAccountedCr()
	{
		return AccountedCr;
	}
	public void setAccountedCr(double aAccountedCr)
	{
		AccountedCr = aAccountedCr;
	}
	public void setAccountedCr(String aAccountedCr)
	{
		if (aAccountedCr != null && !aAccountedCr.equals(""))
		{
			Double tDouble = new Double(aAccountedCr);
			double d = tDouble.doubleValue();
			AccountedCr = d;
		}
	}

	public String getActualFlag()
	{
		return ActualFlag;
	}
	public void setActualFlag(String aActualFlag)
	{
		if(aActualFlag!=null && aActualFlag.length()>10)
			throw new IllegalArgumentException("金额类型ActualFlag值"+aActualFlag+"的长度"+aActualFlag.length()+"大于最大值10");
		ActualFlag = aActualFlag;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			throw new IllegalArgumentException("财务公司代码Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>20)
			throw new IllegalArgumentException("成本中心Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值20");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>20)
			throw new IllegalArgumentException("会计科目Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值20");
		Segment3 = aSegment3;
	}
	public String getSegment4()
	{
		return Segment4;
	}
	public void setSegment4(String aSegment4)
	{
		if(aSegment4!=null && aSegment4.length()>20)
			throw new IllegalArgumentException("银行明细Segment4值"+aSegment4+"的长度"+aSegment4.length()+"大于最大值20");
		Segment4 = aSegment4;
	}
	public String getSegment5()
	{
		return Segment5;
	}
	public void setSegment5(String aSegment5)
	{
		if(aSegment5!=null && aSegment5.length()>20)
			throw new IllegalArgumentException("渠道Segment5值"+aSegment5+"的长度"+aSegment5.length()+"大于最大值20");
		Segment5 = aSegment5;
	}
	public String getSegment6()
	{
		return Segment6;
	}
	public void setSegment6(String aSegment6)
	{
		if(aSegment6!=null && aSegment6.length()>20)
			throw new IllegalArgumentException("险种代码Segment6值"+aSegment6+"的长度"+aSegment6.length()+"大于最大值20");
		Segment6 = aSegment6;
	}
	public String getSegment7()
	{
		return Segment7;
	}
	public void setSegment7(String aSegment7)
	{
		if(aSegment7!=null && aSegment7.length()>20)
			throw new IllegalArgumentException("缴费期Segment7值"+aSegment7+"的长度"+aSegment7.length()+"大于最大值20");
		Segment7 = aSegment7;
	}
	public String getSegment8()
	{
		return Segment8;
	}
	public void setSegment8(String aSegment8)
	{
		if(aSegment8!=null && aSegment8.length()>20)
			throw new IllegalArgumentException("预算段Segment8值"+aSegment8+"的长度"+aSegment8.length()+"大于最大值20");
		Segment8 = aSegment8;
	}
	public String getSegment9()
	{
		return Segment9;
	}
	public void setSegment9(String aSegment9)
	{
		if(aSegment9!=null && aSegment9.length()>20)
			throw new IllegalArgumentException("备用1Segment9值"+aSegment9+"的长度"+aSegment9.length()+"大于最大值20");
		Segment9 = aSegment9;
	}
	public String getLineDescription()
	{
		return LineDescription;
	}
	public void setLineDescription(String aLineDescription)
	{
		if(aLineDescription!=null && aLineDescription.length()>40)
			throw new IllegalArgumentException("行说明LineDescription值"+aLineDescription+"的长度"+aLineDescription.length()+"大于最大值40");
		LineDescription = aLineDescription;
	}
	public String getAttribute1()
	{
		return Attribute1;
	}
	public void setAttribute1(String aAttribute1)
	{
		if(aAttribute1!=null && aAttribute1.length()>100)
			throw new IllegalArgumentException("弹性域1Attribute1值"+aAttribute1+"的长度"+aAttribute1.length()+"大于最大值100");
		Attribute1 = aAttribute1;
	}
	public String getAttribute2()
	{
		return Attribute2;
	}
	public void setAttribute2(String aAttribute2)
	{
		if(aAttribute2!=null && aAttribute2.length()>100)
			throw new IllegalArgumentException("弹性域2Attribute2值"+aAttribute2+"的长度"+aAttribute2.length()+"大于最大值100");
		Attribute2 = aAttribute2;
	}
	public String getAttribute3()
	{
		return Attribute3;
	}
	public void setAttribute3(String aAttribute3)
	{
		if(aAttribute3!=null && aAttribute3.length()>100)
			throw new IllegalArgumentException("弹性域3Attribute3值"+aAttribute3+"的长度"+aAttribute3.length()+"大于最大值100");
		Attribute3 = aAttribute3;
	}
	public String getAttribute4()
	{
		return Attribute4;
	}
	public void setAttribute4(String aAttribute4)
	{
		if(aAttribute4!=null && aAttribute4.length()>100)
			throw new IllegalArgumentException("弹性域4Attribute4值"+aAttribute4+"的长度"+aAttribute4.length()+"大于最大值100");
		Attribute4 = aAttribute4;
	}
	public String getAttribute5()
	{
		return Attribute5;
	}
	public void setAttribute5(String aAttribute5)
	{
		if(aAttribute5!=null && aAttribute5.length()>100)
			throw new IllegalArgumentException("弹性域5Attribute5值"+aAttribute5+"的长度"+aAttribute5.length()+"大于最大值100");
		Attribute5 = aAttribute5;
	}
	public String getAttribute6()
	{
		return Attribute6;
	}
	public void setAttribute6(String aAttribute6)
	{
		if(aAttribute6!=null && aAttribute6.length()>100)
			throw new IllegalArgumentException("弹性域6Attribute6值"+aAttribute6+"的长度"+aAttribute6.length()+"大于最大值100");
		Attribute6 = aAttribute6;
	}
	public String getAttribute7()
	{
		return Attribute7;
	}
	public void setAttribute7(String aAttribute7)
	{
		if(aAttribute7!=null && aAttribute7.length()>100)
			throw new IllegalArgumentException("弹性域7Attribute7值"+aAttribute7+"的长度"+aAttribute7.length()+"大于最大值100");
		Attribute7 = aAttribute7;
	}
	public String getAttribute8()
	{
		return Attribute8;
	}
	public void setAttribute8(String aAttribute8)
	{
		if(aAttribute8!=null && aAttribute8.length()>100)
			throw new IllegalArgumentException("弹性域8Attribute8值"+aAttribute8+"的长度"+aAttribute8.length()+"大于最大值100");
		Attribute8 = aAttribute8;
	}
	public String getAttribute9()
	{
		return Attribute9;
	}
	public void setAttribute9(String aAttribute9)
	{
		if(aAttribute9!=null && aAttribute9.length()>100)
			throw new IllegalArgumentException("弹性域9Attribute9值"+aAttribute9+"的长度"+aAttribute9.length()+"大于最大值100");
		Attribute9 = aAttribute9;
	}
	public String getAttribute10()
	{
		return Attribute10;
	}
	public void setAttribute10(String aAttribute10)
	{
		if(aAttribute10!=null && aAttribute10.length()>100)
			throw new IllegalArgumentException("弹性域10Attribute10值"+aAttribute10+"的长度"+aAttribute10.length()+"大于最大值100");
		Attribute10 = aAttribute10;
	}
	public String getAttribute11()
	{
		return Attribute11;
	}
	public void setAttribute11(String aAttribute11)
	{
		if(aAttribute11!=null && aAttribute11.length()>100)
			throw new IllegalArgumentException("弹性域11Attribute11值"+aAttribute11+"的长度"+aAttribute11.length()+"大于最大值100");
		Attribute11 = aAttribute11;
	}
	public String getAttribute12()
	{
		return Attribute12;
	}
	public void setAttribute12(String aAttribute12)
	{
		if(aAttribute12!=null && aAttribute12.length()>100)
			throw new IllegalArgumentException("弹性域12Attribute12值"+aAttribute12+"的长度"+aAttribute12.length()+"大于最大值100");
		Attribute12 = aAttribute12;
	}
	public String getAttribute13()
	{
		return Attribute13;
	}
	public void setAttribute13(String aAttribute13)
	{
		if(aAttribute13!=null && aAttribute13.length()>100)
			throw new IllegalArgumentException("弹性域13Attribute13值"+aAttribute13+"的长度"+aAttribute13.length()+"大于最大值100");
		Attribute13 = aAttribute13;
	}
	public String getAttribute14()
	{
		return Attribute14;
	}
	public void setAttribute14(String aAttribute14)
	{
		if(aAttribute14!=null && aAttribute14.length()>100)
			throw new IllegalArgumentException("弹性域14Attribute14值"+aAttribute14+"的长度"+aAttribute14.length()+"大于最大值100");
		Attribute14 = aAttribute14;
	}
	public String getAttribute15()
	{
		return Attribute15;
	}
	public void setAttribute15(String aAttribute15)
	{
		if(aAttribute15!=null && aAttribute15.length()>100)
			throw new IllegalArgumentException("弹性域15Attribute15值"+aAttribute15+"的长度"+aAttribute15.length()+"大于最大值100");
		Attribute15 = aAttribute15;
	}
	/**
	* 0-系统抽取，1-手工补充
	*/
	public String getDataSource()
	{
		return DataSource;
	}
	public void setDataSource(String aDataSource)
	{
		if(aDataSource!=null && aDataSource.length()>1)
			throw new IllegalArgumentException("数据来源DataSource值"+aDataSource+"的长度"+aDataSource.length()+"大于最大值1");
		DataSource = aDataSource;
	}
	/**
	* 目前使用文件对接，默认为已写入：0-未写入，1-已写入
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>1)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值1");
		State = aState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getDeleteManageCom()
	{
		return DeleteManageCom;
	}
	public void setDeleteManageCom(String aDeleteManageCom)
	{
		if(aDeleteManageCom!=null && aDeleteManageCom.length()>20)
			throw new IllegalArgumentException("删除管理机构DeleteManageCom值"+aDeleteManageCom+"的长度"+aDeleteManageCom.length()+"大于最大值20");
		DeleteManageCom = aDeleteManageCom;
	}
	public String getDeleteComCode()
	{
		return DeleteComCode;
	}
	public void setDeleteComCode(String aDeleteComCode)
	{
		if(aDeleteComCode!=null && aDeleteComCode.length()>20)
			throw new IllegalArgumentException("删除公司代码DeleteComCode值"+aDeleteComCode+"的长度"+aDeleteComCode.length()+"大于最大值20");
		DeleteComCode = aDeleteComCode;
	}
	public String getDeleteOperator()
	{
		return DeleteOperator;
	}
	public void setDeleteOperator(String aDeleteOperator)
	{
		if(aDeleteOperator!=null && aDeleteOperator.length()>30)
			throw new IllegalArgumentException("删除操作员DeleteOperator值"+aDeleteOperator+"的长度"+aDeleteOperator.length()+"大于最大值30");
		DeleteOperator = aDeleteOperator;
	}
	public String getDeleteDate()
	{
		if( DeleteDate != null )
			return fDate.getString(DeleteDate);
		else
			return null;
	}
	public void setDeleteDate(Date aDeleteDate)
	{
		DeleteDate = aDeleteDate;
	}
	public void setDeleteDate(String aDeleteDate)
	{
		if (aDeleteDate != null && !aDeleteDate.equals("") )
		{
			DeleteDate = fDate.getDate( aDeleteDate );
		}
		else
			DeleteDate = null;
	}

	public String getDeleteTime()
	{
		return DeleteTime;
	}
	public void setDeleteTime(String aDeleteTime)
	{
		if(aDeleteTime!=null && aDeleteTime.length()>8)
			throw new IllegalArgumentException("删除时间DeleteTime值"+aDeleteTime+"的长度"+aDeleteTime.length()+"大于最大值8");
		DeleteTime = aDeleteTime;
	}

	/**
	* 使用另外一个 FinOracleExtractInfoBakSchema 对象给 Schema 赋值
	* @param: aFinOracleExtractInfoBakSchema FinOracleExtractInfoBakSchema
	**/
	public void setSchema(FinOracleExtractInfoBakSchema aFinOracleExtractInfoBakSchema)
	{
		this.SerialNo = aFinOracleExtractInfoBakSchema.getSerialNo();
		this.SourceBatchID = aFinOracleExtractInfoBakSchema.getSourceBatchID();
		this.AccountingDate = fDate.getDate( aFinOracleExtractInfoBakSchema.getAccountingDate());
		this.CurrencyCode = aFinOracleExtractInfoBakSchema.getCurrencyCode();
		this.CurrencyConversionDate = fDate.getDate( aFinOracleExtractInfoBakSchema.getCurrencyConversionDate());
		this.CurrencyConversionRate = aFinOracleExtractInfoBakSchema.getCurrencyConversionRate();
		this.CurrencyConversionType = aFinOracleExtractInfoBakSchema.getCurrencyConversionType();
		this.EnteredDr = aFinOracleExtractInfoBakSchema.getEnteredDr();
		this.EnteredCr = aFinOracleExtractInfoBakSchema.getEnteredCr();
		this.AccountedDr = aFinOracleExtractInfoBakSchema.getAccountedDr();
		this.AccountedCr = aFinOracleExtractInfoBakSchema.getAccountedCr();
		this.ActualFlag = aFinOracleExtractInfoBakSchema.getActualFlag();
		this.Segment1 = aFinOracleExtractInfoBakSchema.getSegment1();
		this.Segment2 = aFinOracleExtractInfoBakSchema.getSegment2();
		this.Segment3 = aFinOracleExtractInfoBakSchema.getSegment3();
		this.Segment4 = aFinOracleExtractInfoBakSchema.getSegment4();
		this.Segment5 = aFinOracleExtractInfoBakSchema.getSegment5();
		this.Segment6 = aFinOracleExtractInfoBakSchema.getSegment6();
		this.Segment7 = aFinOracleExtractInfoBakSchema.getSegment7();
		this.Segment8 = aFinOracleExtractInfoBakSchema.getSegment8();
		this.Segment9 = aFinOracleExtractInfoBakSchema.getSegment9();
		this.LineDescription = aFinOracleExtractInfoBakSchema.getLineDescription();
		this.Attribute1 = aFinOracleExtractInfoBakSchema.getAttribute1();
		this.Attribute2 = aFinOracleExtractInfoBakSchema.getAttribute2();
		this.Attribute3 = aFinOracleExtractInfoBakSchema.getAttribute3();
		this.Attribute4 = aFinOracleExtractInfoBakSchema.getAttribute4();
		this.Attribute5 = aFinOracleExtractInfoBakSchema.getAttribute5();
		this.Attribute6 = aFinOracleExtractInfoBakSchema.getAttribute6();
		this.Attribute7 = aFinOracleExtractInfoBakSchema.getAttribute7();
		this.Attribute8 = aFinOracleExtractInfoBakSchema.getAttribute8();
		this.Attribute9 = aFinOracleExtractInfoBakSchema.getAttribute9();
		this.Attribute10 = aFinOracleExtractInfoBakSchema.getAttribute10();
		this.Attribute11 = aFinOracleExtractInfoBakSchema.getAttribute11();
		this.Attribute12 = aFinOracleExtractInfoBakSchema.getAttribute12();
		this.Attribute13 = aFinOracleExtractInfoBakSchema.getAttribute13();
		this.Attribute14 = aFinOracleExtractInfoBakSchema.getAttribute14();
		this.Attribute15 = aFinOracleExtractInfoBakSchema.getAttribute15();
		this.DataSource = aFinOracleExtractInfoBakSchema.getDataSource();
		this.State = aFinOracleExtractInfoBakSchema.getState();
		this.ManageCom = aFinOracleExtractInfoBakSchema.getManageCom();
		this.ComCode = aFinOracleExtractInfoBakSchema.getComCode();
		this.MakeOperator = aFinOracleExtractInfoBakSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aFinOracleExtractInfoBakSchema.getMakeDate());
		this.MakeTime = aFinOracleExtractInfoBakSchema.getMakeTime();
		this.DeleteManageCom = aFinOracleExtractInfoBakSchema.getDeleteManageCom();
		this.DeleteComCode = aFinOracleExtractInfoBakSchema.getDeleteComCode();
		this.DeleteOperator = aFinOracleExtractInfoBakSchema.getDeleteOperator();
		this.DeleteDate = fDate.getDate( aFinOracleExtractInfoBakSchema.getDeleteDate());
		this.DeleteTime = aFinOracleExtractInfoBakSchema.getDeleteTime();
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

			if( rs.getString("SourceBatchID") == null )
				this.SourceBatchID = null;
			else
				this.SourceBatchID = rs.getString("SourceBatchID").trim();

			this.AccountingDate = rs.getDate("AccountingDate");
			if( rs.getString("CurrencyCode") == null )
				this.CurrencyCode = null;
			else
				this.CurrencyCode = rs.getString("CurrencyCode").trim();

			this.CurrencyConversionDate = rs.getDate("CurrencyConversionDate");
			this.CurrencyConversionRate = rs.getDouble("CurrencyConversionRate");
			if( rs.getString("CurrencyConversionType") == null )
				this.CurrencyConversionType = null;
			else
				this.CurrencyConversionType = rs.getString("CurrencyConversionType").trim();

			this.EnteredDr = rs.getDouble("EnteredDr");
			this.EnteredCr = rs.getDouble("EnteredCr");
			this.AccountedDr = rs.getDouble("AccountedDr");
			this.AccountedCr = rs.getDouble("AccountedCr");
			if( rs.getString("ActualFlag") == null )
				this.ActualFlag = null;
			else
				this.ActualFlag = rs.getString("ActualFlag").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("Segment4") == null )
				this.Segment4 = null;
			else
				this.Segment4 = rs.getString("Segment4").trim();

			if( rs.getString("Segment5") == null )
				this.Segment5 = null;
			else
				this.Segment5 = rs.getString("Segment5").trim();

			if( rs.getString("Segment6") == null )
				this.Segment6 = null;
			else
				this.Segment6 = rs.getString("Segment6").trim();

			if( rs.getString("Segment7") == null )
				this.Segment7 = null;
			else
				this.Segment7 = rs.getString("Segment7").trim();

			if( rs.getString("Segment8") == null )
				this.Segment8 = null;
			else
				this.Segment8 = rs.getString("Segment8").trim();

			if( rs.getString("Segment9") == null )
				this.Segment9 = null;
			else
				this.Segment9 = rs.getString("Segment9").trim();

			if( rs.getString("LineDescription") == null )
				this.LineDescription = null;
			else
				this.LineDescription = rs.getString("LineDescription").trim();

			if( rs.getString("Attribute1") == null )
				this.Attribute1 = null;
			else
				this.Attribute1 = rs.getString("Attribute1").trim();

			if( rs.getString("Attribute2") == null )
				this.Attribute2 = null;
			else
				this.Attribute2 = rs.getString("Attribute2").trim();

			if( rs.getString("Attribute3") == null )
				this.Attribute3 = null;
			else
				this.Attribute3 = rs.getString("Attribute3").trim();

			if( rs.getString("Attribute4") == null )
				this.Attribute4 = null;
			else
				this.Attribute4 = rs.getString("Attribute4").trim();

			if( rs.getString("Attribute5") == null )
				this.Attribute5 = null;
			else
				this.Attribute5 = rs.getString("Attribute5").trim();

			if( rs.getString("Attribute6") == null )
				this.Attribute6 = null;
			else
				this.Attribute6 = rs.getString("Attribute6").trim();

			if( rs.getString("Attribute7") == null )
				this.Attribute7 = null;
			else
				this.Attribute7 = rs.getString("Attribute7").trim();

			if( rs.getString("Attribute8") == null )
				this.Attribute8 = null;
			else
				this.Attribute8 = rs.getString("Attribute8").trim();

			if( rs.getString("Attribute9") == null )
				this.Attribute9 = null;
			else
				this.Attribute9 = rs.getString("Attribute9").trim();

			if( rs.getString("Attribute10") == null )
				this.Attribute10 = null;
			else
				this.Attribute10 = rs.getString("Attribute10").trim();

			if( rs.getString("Attribute11") == null )
				this.Attribute11 = null;
			else
				this.Attribute11 = rs.getString("Attribute11").trim();

			if( rs.getString("Attribute12") == null )
				this.Attribute12 = null;
			else
				this.Attribute12 = rs.getString("Attribute12").trim();

			if( rs.getString("Attribute13") == null )
				this.Attribute13 = null;
			else
				this.Attribute13 = rs.getString("Attribute13").trim();

			if( rs.getString("Attribute14") == null )
				this.Attribute14 = null;
			else
				this.Attribute14 = rs.getString("Attribute14").trim();

			if( rs.getString("Attribute15") == null )
				this.Attribute15 = null;
			else
				this.Attribute15 = rs.getString("Attribute15").trim();

			if( rs.getString("DataSource") == null )
				this.DataSource = null;
			else
				this.DataSource = rs.getString("DataSource").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("DeleteManageCom") == null )
				this.DeleteManageCom = null;
			else
				this.DeleteManageCom = rs.getString("DeleteManageCom").trim();

			if( rs.getString("DeleteComCode") == null )
				this.DeleteComCode = null;
			else
				this.DeleteComCode = rs.getString("DeleteComCode").trim();

			if( rs.getString("DeleteOperator") == null )
				this.DeleteOperator = null;
			else
				this.DeleteOperator = rs.getString("DeleteOperator").trim();

			this.DeleteDate = rs.getDate("DeleteDate");
			if( rs.getString("DeleteTime") == null )
				this.DeleteTime = null;
			else
				this.DeleteTime = rs.getString("DeleteTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FinOracleExtractInfoBak表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinOracleExtractInfoBakSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FinOracleExtractInfoBakSchema getSchema()
	{
		FinOracleExtractInfoBakSchema aFinOracleExtractInfoBakSchema = new FinOracleExtractInfoBakSchema();
		aFinOracleExtractInfoBakSchema.setSchema(this);
		return aFinOracleExtractInfoBakSchema;
	}

	public FinOracleExtractInfoBakDB getDB()
	{
		FinOracleExtractInfoBakDB aDBOper = new FinOracleExtractInfoBakDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinOracleExtractInfoBak描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourceBatchID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccountingDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrencyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurrencyConversionDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurrencyConversionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrencyConversionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnteredDr));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnteredCr));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccountedDr));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccountedCr));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActualFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LineDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DataSource)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeleteManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeleteComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeleteOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeleteDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeleteTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinOracleExtractInfoBak>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SourceBatchID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccountingDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			CurrencyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CurrencyConversionDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			CurrencyConversionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			CurrencyConversionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EnteredDr = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			EnteredCr = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			AccountedDr = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			AccountedCr = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			ActualFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Segment4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Segment5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Segment6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Segment7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Segment8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Segment9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			LineDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Attribute1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Attribute2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Attribute3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Attribute4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Attribute5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Attribute6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Attribute7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Attribute8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Attribute9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Attribute10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Attribute11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Attribute12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Attribute13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Attribute14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Attribute15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			DataSource = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			DeleteManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			DeleteComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			DeleteOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			DeleteDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			DeleteTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinOracleExtractInfoBakSchema";
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
		if (FCode.equalsIgnoreCase("SourceBatchID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceBatchID));
		}
		if (FCode.equalsIgnoreCase("AccountingDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccountingDate()));
		}
		if (FCode.equalsIgnoreCase("CurrencyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrencyCode));
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurrencyConversionDate()));
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrencyConversionRate));
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrencyConversionType));
		}
		if (FCode.equalsIgnoreCase("EnteredDr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnteredDr));
		}
		if (FCode.equalsIgnoreCase("EnteredCr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnteredCr));
		}
		if (FCode.equalsIgnoreCase("AccountedDr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountedDr));
		}
		if (FCode.equalsIgnoreCase("AccountedCr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountedCr));
		}
		if (FCode.equalsIgnoreCase("ActualFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualFlag));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("Segment4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment4));
		}
		if (FCode.equalsIgnoreCase("Segment5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment5));
		}
		if (FCode.equalsIgnoreCase("Segment6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment6));
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment7));
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment8));
		}
		if (FCode.equalsIgnoreCase("Segment9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment9));
		}
		if (FCode.equalsIgnoreCase("LineDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LineDescription));
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute1));
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute2));
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute3));
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute4));
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute5));
		}
		if (FCode.equalsIgnoreCase("Attribute6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute6));
		}
		if (FCode.equalsIgnoreCase("Attribute7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute7));
		}
		if (FCode.equalsIgnoreCase("Attribute8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute8));
		}
		if (FCode.equalsIgnoreCase("Attribute9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute9));
		}
		if (FCode.equalsIgnoreCase("Attribute10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute10));
		}
		if (FCode.equalsIgnoreCase("Attribute11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute11));
		}
		if (FCode.equalsIgnoreCase("Attribute12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute12));
		}
		if (FCode.equalsIgnoreCase("Attribute13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute13));
		}
		if (FCode.equalsIgnoreCase("Attribute14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute14));
		}
		if (FCode.equalsIgnoreCase("Attribute15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute15));
		}
		if (FCode.equalsIgnoreCase("DataSource"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataSource));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("DeleteManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeleteManageCom));
		}
		if (FCode.equalsIgnoreCase("DeleteComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeleteComCode));
		}
		if (FCode.equalsIgnoreCase("DeleteOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeleteOperator));
		}
		if (FCode.equalsIgnoreCase("DeleteDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeleteDate()));
		}
		if (FCode.equalsIgnoreCase("DeleteTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeleteTime));
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
				strFieldValue = StrTool.GBKToUnicode(SourceBatchID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccountingDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CurrencyCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurrencyConversionDate()));
				break;
			case 5:
				strFieldValue = String.valueOf(CurrencyConversionRate);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CurrencyConversionType);
				break;
			case 7:
				strFieldValue = String.valueOf(EnteredDr);
				break;
			case 8:
				strFieldValue = String.valueOf(EnteredCr);
				break;
			case 9:
				strFieldValue = String.valueOf(AccountedDr);
				break;
			case 10:
				strFieldValue = String.valueOf(AccountedCr);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ActualFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Segment4);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Segment5);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Segment6);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Segment7);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Segment8);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Segment9);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(LineDescription);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Attribute1);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Attribute2);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Attribute3);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Attribute4);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Attribute5);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Attribute6);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Attribute7);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Attribute8);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Attribute9);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Attribute10);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Attribute11);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Attribute12);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Attribute13);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Attribute14);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Attribute15);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(DataSource);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(DeleteManageCom);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(DeleteComCode);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(DeleteOperator);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeleteDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(DeleteTime);
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
		if (FCode.equalsIgnoreCase("SourceBatchID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceBatchID = FValue.trim();
			}
			else
				SourceBatchID = null;
		}
		if (FCode.equalsIgnoreCase("AccountingDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccountingDate = fDate.getDate( FValue );
			}
			else
				AccountingDate = null;
		}
		if (FCode.equalsIgnoreCase("CurrencyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrencyCode = FValue.trim();
			}
			else
				CurrencyCode = null;
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurrencyConversionDate = fDate.getDate( FValue );
			}
			else
				CurrencyConversionDate = null;
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurrencyConversionRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrencyConversionType = FValue.trim();
			}
			else
				CurrencyConversionType = null;
		}
		if (FCode.equalsIgnoreCase("EnteredDr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnteredDr = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnteredCr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnteredCr = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccountedDr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccountedDr = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccountedCr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccountedCr = d;
			}
		}
		if (FCode.equalsIgnoreCase("ActualFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActualFlag = FValue.trim();
			}
			else
				ActualFlag = null;
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("Segment4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment4 = FValue.trim();
			}
			else
				Segment4 = null;
		}
		if (FCode.equalsIgnoreCase("Segment5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment5 = FValue.trim();
			}
			else
				Segment5 = null;
		}
		if (FCode.equalsIgnoreCase("Segment6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment6 = FValue.trim();
			}
			else
				Segment6 = null;
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment7 = FValue.trim();
			}
			else
				Segment7 = null;
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment8 = FValue.trim();
			}
			else
				Segment8 = null;
		}
		if (FCode.equalsIgnoreCase("Segment9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment9 = FValue.trim();
			}
			else
				Segment9 = null;
		}
		if (FCode.equalsIgnoreCase("LineDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LineDescription = FValue.trim();
			}
			else
				LineDescription = null;
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute1 = FValue.trim();
			}
			else
				Attribute1 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute2 = FValue.trim();
			}
			else
				Attribute2 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute3 = FValue.trim();
			}
			else
				Attribute3 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute4 = FValue.trim();
			}
			else
				Attribute4 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute5 = FValue.trim();
			}
			else
				Attribute5 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute6 = FValue.trim();
			}
			else
				Attribute6 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute7 = FValue.trim();
			}
			else
				Attribute7 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute8 = FValue.trim();
			}
			else
				Attribute8 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute9 = FValue.trim();
			}
			else
				Attribute9 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute10 = FValue.trim();
			}
			else
				Attribute10 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute11 = FValue.trim();
			}
			else
				Attribute11 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute12 = FValue.trim();
			}
			else
				Attribute12 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute13 = FValue.trim();
			}
			else
				Attribute13 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute14 = FValue.trim();
			}
			else
				Attribute14 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute15 = FValue.trim();
			}
			else
				Attribute15 = null;
		}
		if (FCode.equalsIgnoreCase("DataSource"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataSource = FValue.trim();
			}
			else
				DataSource = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("DeleteManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeleteManageCom = FValue.trim();
			}
			else
				DeleteManageCom = null;
		}
		if (FCode.equalsIgnoreCase("DeleteComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeleteComCode = FValue.trim();
			}
			else
				DeleteComCode = null;
		}
		if (FCode.equalsIgnoreCase("DeleteOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeleteOperator = FValue.trim();
			}
			else
				DeleteOperator = null;
		}
		if (FCode.equalsIgnoreCase("DeleteDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeleteDate = fDate.getDate( FValue );
			}
			else
				DeleteDate = null;
		}
		if (FCode.equalsIgnoreCase("DeleteTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeleteTime = FValue.trim();
			}
			else
				DeleteTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FinOracleExtractInfoBakSchema other = (FinOracleExtractInfoBakSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& SourceBatchID.equals(other.getSourceBatchID())
			&& fDate.getString(AccountingDate).equals(other.getAccountingDate())
			&& CurrencyCode.equals(other.getCurrencyCode())
			&& fDate.getString(CurrencyConversionDate).equals(other.getCurrencyConversionDate())
			&& CurrencyConversionRate == other.getCurrencyConversionRate()
			&& CurrencyConversionType.equals(other.getCurrencyConversionType())
			&& EnteredDr == other.getEnteredDr()
			&& EnteredCr == other.getEnteredCr()
			&& AccountedDr == other.getAccountedDr()
			&& AccountedCr == other.getAccountedCr()
			&& ActualFlag.equals(other.getActualFlag())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& Segment4.equals(other.getSegment4())
			&& Segment5.equals(other.getSegment5())
			&& Segment6.equals(other.getSegment6())
			&& Segment7.equals(other.getSegment7())
			&& Segment8.equals(other.getSegment8())
			&& Segment9.equals(other.getSegment9())
			&& LineDescription.equals(other.getLineDescription())
			&& Attribute1.equals(other.getAttribute1())
			&& Attribute2.equals(other.getAttribute2())
			&& Attribute3.equals(other.getAttribute3())
			&& Attribute4.equals(other.getAttribute4())
			&& Attribute5.equals(other.getAttribute5())
			&& Attribute6.equals(other.getAttribute6())
			&& Attribute7.equals(other.getAttribute7())
			&& Attribute8.equals(other.getAttribute8())
			&& Attribute9.equals(other.getAttribute9())
			&& Attribute10.equals(other.getAttribute10())
			&& Attribute11.equals(other.getAttribute11())
			&& Attribute12.equals(other.getAttribute12())
			&& Attribute13.equals(other.getAttribute13())
			&& Attribute14.equals(other.getAttribute14())
			&& Attribute15.equals(other.getAttribute15())
			&& DataSource.equals(other.getDataSource())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& DeleteManageCom.equals(other.getDeleteManageCom())
			&& DeleteComCode.equals(other.getDeleteComCode())
			&& DeleteOperator.equals(other.getDeleteOperator())
			&& fDate.getString(DeleteDate).equals(other.getDeleteDate())
			&& DeleteTime.equals(other.getDeleteTime());
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
		if( strFieldName.equals("SourceBatchID") ) {
			return 1;
		}
		if( strFieldName.equals("AccountingDate") ) {
			return 2;
		}
		if( strFieldName.equals("CurrencyCode") ) {
			return 3;
		}
		if( strFieldName.equals("CurrencyConversionDate") ) {
			return 4;
		}
		if( strFieldName.equals("CurrencyConversionRate") ) {
			return 5;
		}
		if( strFieldName.equals("CurrencyConversionType") ) {
			return 6;
		}
		if( strFieldName.equals("EnteredDr") ) {
			return 7;
		}
		if( strFieldName.equals("EnteredCr") ) {
			return 8;
		}
		if( strFieldName.equals("AccountedDr") ) {
			return 9;
		}
		if( strFieldName.equals("AccountedCr") ) {
			return 10;
		}
		if( strFieldName.equals("ActualFlag") ) {
			return 11;
		}
		if( strFieldName.equals("Segment1") ) {
			return 12;
		}
		if( strFieldName.equals("Segment2") ) {
			return 13;
		}
		if( strFieldName.equals("Segment3") ) {
			return 14;
		}
		if( strFieldName.equals("Segment4") ) {
			return 15;
		}
		if( strFieldName.equals("Segment5") ) {
			return 16;
		}
		if( strFieldName.equals("Segment6") ) {
			return 17;
		}
		if( strFieldName.equals("Segment7") ) {
			return 18;
		}
		if( strFieldName.equals("Segment8") ) {
			return 19;
		}
		if( strFieldName.equals("Segment9") ) {
			return 20;
		}
		if( strFieldName.equals("LineDescription") ) {
			return 21;
		}
		if( strFieldName.equals("Attribute1") ) {
			return 22;
		}
		if( strFieldName.equals("Attribute2") ) {
			return 23;
		}
		if( strFieldName.equals("Attribute3") ) {
			return 24;
		}
		if( strFieldName.equals("Attribute4") ) {
			return 25;
		}
		if( strFieldName.equals("Attribute5") ) {
			return 26;
		}
		if( strFieldName.equals("Attribute6") ) {
			return 27;
		}
		if( strFieldName.equals("Attribute7") ) {
			return 28;
		}
		if( strFieldName.equals("Attribute8") ) {
			return 29;
		}
		if( strFieldName.equals("Attribute9") ) {
			return 30;
		}
		if( strFieldName.equals("Attribute10") ) {
			return 31;
		}
		if( strFieldName.equals("Attribute11") ) {
			return 32;
		}
		if( strFieldName.equals("Attribute12") ) {
			return 33;
		}
		if( strFieldName.equals("Attribute13") ) {
			return 34;
		}
		if( strFieldName.equals("Attribute14") ) {
			return 35;
		}
		if( strFieldName.equals("Attribute15") ) {
			return 36;
		}
		if( strFieldName.equals("DataSource") ) {
			return 37;
		}
		if( strFieldName.equals("State") ) {
			return 38;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 39;
		}
		if( strFieldName.equals("ComCode") ) {
			return 40;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 41;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 42;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 43;
		}
		if( strFieldName.equals("DeleteManageCom") ) {
			return 44;
		}
		if( strFieldName.equals("DeleteComCode") ) {
			return 45;
		}
		if( strFieldName.equals("DeleteOperator") ) {
			return 46;
		}
		if( strFieldName.equals("DeleteDate") ) {
			return 47;
		}
		if( strFieldName.equals("DeleteTime") ) {
			return 48;
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
				strFieldName = "SourceBatchID";
				break;
			case 2:
				strFieldName = "AccountingDate";
				break;
			case 3:
				strFieldName = "CurrencyCode";
				break;
			case 4:
				strFieldName = "CurrencyConversionDate";
				break;
			case 5:
				strFieldName = "CurrencyConversionRate";
				break;
			case 6:
				strFieldName = "CurrencyConversionType";
				break;
			case 7:
				strFieldName = "EnteredDr";
				break;
			case 8:
				strFieldName = "EnteredCr";
				break;
			case 9:
				strFieldName = "AccountedDr";
				break;
			case 10:
				strFieldName = "AccountedCr";
				break;
			case 11:
				strFieldName = "ActualFlag";
				break;
			case 12:
				strFieldName = "Segment1";
				break;
			case 13:
				strFieldName = "Segment2";
				break;
			case 14:
				strFieldName = "Segment3";
				break;
			case 15:
				strFieldName = "Segment4";
				break;
			case 16:
				strFieldName = "Segment5";
				break;
			case 17:
				strFieldName = "Segment6";
				break;
			case 18:
				strFieldName = "Segment7";
				break;
			case 19:
				strFieldName = "Segment8";
				break;
			case 20:
				strFieldName = "Segment9";
				break;
			case 21:
				strFieldName = "LineDescription";
				break;
			case 22:
				strFieldName = "Attribute1";
				break;
			case 23:
				strFieldName = "Attribute2";
				break;
			case 24:
				strFieldName = "Attribute3";
				break;
			case 25:
				strFieldName = "Attribute4";
				break;
			case 26:
				strFieldName = "Attribute5";
				break;
			case 27:
				strFieldName = "Attribute6";
				break;
			case 28:
				strFieldName = "Attribute7";
				break;
			case 29:
				strFieldName = "Attribute8";
				break;
			case 30:
				strFieldName = "Attribute9";
				break;
			case 31:
				strFieldName = "Attribute10";
				break;
			case 32:
				strFieldName = "Attribute11";
				break;
			case 33:
				strFieldName = "Attribute12";
				break;
			case 34:
				strFieldName = "Attribute13";
				break;
			case 35:
				strFieldName = "Attribute14";
				break;
			case 36:
				strFieldName = "Attribute15";
				break;
			case 37:
				strFieldName = "DataSource";
				break;
			case 38:
				strFieldName = "State";
				break;
			case 39:
				strFieldName = "ManageCom";
				break;
			case 40:
				strFieldName = "ComCode";
				break;
			case 41:
				strFieldName = "MakeOperator";
				break;
			case 42:
				strFieldName = "MakeDate";
				break;
			case 43:
				strFieldName = "MakeTime";
				break;
			case 44:
				strFieldName = "DeleteManageCom";
				break;
			case 45:
				strFieldName = "DeleteComCode";
				break;
			case 46:
				strFieldName = "DeleteOperator";
				break;
			case 47:
				strFieldName = "DeleteDate";
				break;
			case 48:
				strFieldName = "DeleteTime";
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
		if( strFieldName.equals("SourceBatchID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountingDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurrencyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrencyConversionDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurrencyConversionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurrencyConversionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnteredDr") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnteredCr") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccountedDr") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccountedCr") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ActualFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LineDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataSource") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeleteManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeleteComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeleteOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeleteDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DeleteTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
