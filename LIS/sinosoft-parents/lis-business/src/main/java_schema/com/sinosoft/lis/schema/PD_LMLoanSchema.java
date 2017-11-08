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
import com.sinosoft.lis.db.PD_LMLoanDB;

/*
 * <p>ClassName: PD_LMLoanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMLoanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMLoanSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 控制类别 */
	private String ControlType;
	/** 能借款比例 */
	private double CanRate;
	/** 借款利息方式 */
	private String InterestType;
	/** 默认借款利率 */
	private double InterestRate;
	/** 利率类型 */
	private String InterestMode;
	/** 计算利率类型 */
	private String RateCalType;
	/** 计算利率编码 */
	private String RateCalCode;
	/** 是否按固定利率计算 */
	private String SpecifyRate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMLoanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		PD_LMLoanSchema cloned = (PD_LMLoanSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 0--保费 1--现金价值
	*/
	public String getControlType()
	{
		return ControlType;
	}
	public void setControlType(String aControlType)
	{
		ControlType = aControlType;
	}
	public double getCanRate()
	{
		return CanRate;
	}
	public void setCanRate(double aCanRate)
	{
		CanRate = aCanRate;
	}
	public void setCanRate(String aCanRate)
	{
		if (aCanRate != null && !aCanRate.equals(""))
		{
			Double tDouble = new Double(aCanRate);
			double d = tDouble.doubleValue();
			CanRate = d;
		}
	}

	/**
	* 1--按单利计算 2--按复利计算
	*/
	public String getInterestType()
	{
		return InterestType;
	}
	public void setInterestType(String aInterestType)
	{
		InterestType = aInterestType;
	}
	/**
	* 指默认固定利率，针对固定利率
	*/
	public double getInterestRate()
	{
		return InterestRate;
	}
	public void setInterestRate(double aInterestRate)
	{
		InterestRate = aInterestRate;
	}
	public void setInterestRate(String aInterestRate)
	{
		if (aInterestRate != null && !aInterestRate.equals(""))
		{
			Double tDouble = new Double(aInterestRate);
			double d = tDouble.doubleValue();
			InterestRate = d;
		}
	}

	/**
	* 针对固定利率该字段对于浮动利率没有意义，在浮动利率描述表中已经能够体现，该字段只是对固定利率有意义。 1--年利率 2--月利率 3--日利率
	*/
	public String getInterestMode()
	{
		return InterestMode;
	}
	public void setInterestMode(String aInterestMode)
	{
		InterestMode = aInterestMode;
	}
	/**
	* 针对浮动利率 1--表示按照浮动利率表进行计算（在计算利率方法字段中描述的是浮动利率表名） 2--表示按照一个计算编码计算
	*/
	public String getRateCalType()
	{
		return RateCalType;
	}
	public void setRateCalType(String aRateCalType)
	{
		RateCalType = aRateCalType;
	}
	/**
	* 针对浮动利率
	*/
	public String getRateCalCode()
	{
		return RateCalCode;
	}
	public void setRateCalCode(String aRateCalCode)
	{
		RateCalCode = aRateCalCode;
	}
	/**
	* 1--按固定利率计算 2--按浮动利率计算
	*/
	public String getSpecifyRate()
	{
		return SpecifyRate;
	}
	public void setSpecifyRate(String aSpecifyRate)
	{
		SpecifyRate = aSpecifyRate;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
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
		ModifyTime = aModifyTime;
	}
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}


	/**
	* 使用另外一个 PD_LMLoanSchema 对象给 Schema 赋值
	* @param: aPD_LMLoanSchema PD_LMLoanSchema
	**/
	public void setSchema(PD_LMLoanSchema aPD_LMLoanSchema)
	{
		this.RiskCode = aPD_LMLoanSchema.getRiskCode();
		this.ControlType = aPD_LMLoanSchema.getControlType();
		this.CanRate = aPD_LMLoanSchema.getCanRate();
		this.InterestType = aPD_LMLoanSchema.getInterestType();
		this.InterestRate = aPD_LMLoanSchema.getInterestRate();
		this.InterestMode = aPD_LMLoanSchema.getInterestMode();
		this.RateCalType = aPD_LMLoanSchema.getRateCalType();
		this.RateCalCode = aPD_LMLoanSchema.getRateCalCode();
		this.SpecifyRate = aPD_LMLoanSchema.getSpecifyRate();
		this.Operator = aPD_LMLoanSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMLoanSchema.getMakeDate());
		this.MakeTime = aPD_LMLoanSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMLoanSchema.getModifyDate());
		this.ModifyTime = aPD_LMLoanSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMLoanSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMLoanSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMLoanSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMLoanSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMLoanSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMLoanSchema.getStandbyflag6();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ControlType") == null )
				this.ControlType = null;
			else
				this.ControlType = rs.getString("ControlType").trim();

			this.CanRate = rs.getDouble("CanRate");
			if( rs.getString("InterestType") == null )
				this.InterestType = null;
			else
				this.InterestType = rs.getString("InterestType").trim();

			this.InterestRate = rs.getDouble("InterestRate");
			if( rs.getString("InterestMode") == null )
				this.InterestMode = null;
			else
				this.InterestMode = rs.getString("InterestMode").trim();

			if( rs.getString("RateCalType") == null )
				this.RateCalType = null;
			else
				this.RateCalType = rs.getString("RateCalType").trim();

			if( rs.getString("RateCalCode") == null )
				this.RateCalCode = null;
			else
				this.RateCalCode = rs.getString("RateCalCode").trim();

			if( rs.getString("SpecifyRate") == null )
				this.SpecifyRate = null;
			else
				this.SpecifyRate = rs.getString("SpecifyRate").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMLoan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMLoanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMLoanSchema getSchema()
	{
		PD_LMLoanSchema aPD_LMLoanSchema = new PD_LMLoanSchema();
		aPD_LMLoanSchema.setSchema(this);
		return aPD_LMLoanSchema;
	}

	public PD_LMLoanDB getDB()
	{
		PD_LMLoanDB aDBOper = new PD_LMLoanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMLoan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ControlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CanRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InterestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecifyRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMLoan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ControlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CanRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			InterestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InterestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			InterestMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RateCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RateCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SpecifyRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMLoanSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ControlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ControlType));
		}
		if (FCode.equalsIgnoreCase("CanRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CanRate));
		}
		if (FCode.equalsIgnoreCase("InterestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestType));
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestRate));
		}
		if (FCode.equalsIgnoreCase("InterestMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestMode));
		}
		if (FCode.equalsIgnoreCase("RateCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateCalType));
		}
		if (FCode.equalsIgnoreCase("RateCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateCalCode));
		}
		if (FCode.equalsIgnoreCase("SpecifyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecifyRate));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ControlType);
				break;
			case 2:
				strFieldValue = String.valueOf(CanRate);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InterestType);
				break;
			case 4:
				strFieldValue = String.valueOf(InterestRate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InterestMode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RateCalType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RateCalCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SpecifyRate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 16:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 17:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 18:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 19:
				strFieldValue = String.valueOf(Standbyflag6);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ControlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ControlType = FValue.trim();
			}
			else
				ControlType = null;
		}
		if (FCode.equalsIgnoreCase("CanRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CanRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InterestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestType = FValue.trim();
			}
			else
				InterestType = null;
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InterestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InterestMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestMode = FValue.trim();
			}
			else
				InterestMode = null;
		}
		if (FCode.equalsIgnoreCase("RateCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateCalType = FValue.trim();
			}
			else
				RateCalType = null;
		}
		if (FCode.equalsIgnoreCase("RateCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateCalCode = FValue.trim();
			}
			else
				RateCalCode = null;
		}
		if (FCode.equalsIgnoreCase("SpecifyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecifyRate = FValue.trim();
			}
			else
				SpecifyRate = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMLoanSchema other = (PD_LMLoanSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ControlType.equals(other.getControlType())
			&& CanRate == other.getCanRate()
			&& InterestType.equals(other.getInterestType())
			&& InterestRate == other.getInterestRate()
			&& InterestMode.equals(other.getInterestMode())
			&& RateCalType.equals(other.getRateCalType())
			&& RateCalCode.equals(other.getRateCalCode())
			&& SpecifyRate.equals(other.getSpecifyRate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("ControlType") ) {
			return 1;
		}
		if( strFieldName.equals("CanRate") ) {
			return 2;
		}
		if( strFieldName.equals("InterestType") ) {
			return 3;
		}
		if( strFieldName.equals("InterestRate") ) {
			return 4;
		}
		if( strFieldName.equals("InterestMode") ) {
			return 5;
		}
		if( strFieldName.equals("RateCalType") ) {
			return 6;
		}
		if( strFieldName.equals("RateCalCode") ) {
			return 7;
		}
		if( strFieldName.equals("SpecifyRate") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 14;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 15;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 16;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 19;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "ControlType";
				break;
			case 2:
				strFieldName = "CanRate";
				break;
			case 3:
				strFieldName = "InterestType";
				break;
			case 4:
				strFieldName = "InterestRate";
				break;
			case 5:
				strFieldName = "InterestMode";
				break;
			case 6:
				strFieldName = "RateCalType";
				break;
			case 7:
				strFieldName = "RateCalCode";
				break;
			case 8:
				strFieldName = "SpecifyRate";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "Standbyflag1";
				break;
			case 15:
				strFieldName = "Standbyflag2";
				break;
			case 16:
				strFieldName = "Standbyflag3";
				break;
			case 17:
				strFieldName = "Standbyflag4";
				break;
			case 18:
				strFieldName = "Standbyflag5";
				break;
			case 19:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ControlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CanRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InterestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InterestMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecifyRate") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
