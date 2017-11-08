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
import com.sinosoft.lis.db.PD_LMClaimCtrlFeeDB;

/*
 * <p>ClassName: PD_LMClaimCtrlFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMClaimCtrlFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMClaimCtrlFeeSchema.class);

	// @Field
	/** 理赔控制编号 */
	private String ClaimCtrlCode;
	/** 起始费用 */
	private double ClmFeeMIN;
	/** 起始费用单位 */
	private String ClmFeeMINFlag;
	/** 费用期间间隔 */
	private double ClmFeeInterval;
	/** 费用期间间隔单位 */
	private String ClmFeeFlag;
	/** 赔付金额计算sql */
	private String CalCode2;
	/** 赔付金额类型 */
	private String CalResultType;
	/** 赔付金额默认值 */
	private double DefaultValue;
	/** 赔付金额计算方式 */
	private String CalCtrlFlag;
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
	public PD_LMClaimCtrlFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClaimCtrlCode";

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
		PD_LMClaimCtrlFeeSchema cloned = (PD_LMClaimCtrlFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClaimCtrlCode()
	{
		return ClaimCtrlCode;
	}
	public void setClaimCtrlCode(String aClaimCtrlCode)
	{
		ClaimCtrlCode = aClaimCtrlCode;
	}
	public double getClmFeeMIN()
	{
		return ClmFeeMIN;
	}
	public void setClmFeeMIN(double aClmFeeMIN)
	{
		ClmFeeMIN = aClmFeeMIN;
	}
	public void setClmFeeMIN(String aClmFeeMIN)
	{
		if (aClmFeeMIN != null && !aClmFeeMIN.equals(""))
		{
			Double tDouble = new Double(aClmFeeMIN);
			double d = tDouble.doubleValue();
			ClmFeeMIN = d;
		}
	}

	public String getClmFeeMINFlag()
	{
		return ClmFeeMINFlag;
	}
	public void setClmFeeMINFlag(String aClmFeeMINFlag)
	{
		ClmFeeMINFlag = aClmFeeMINFlag;
	}
	public double getClmFeeInterval()
	{
		return ClmFeeInterval;
	}
	public void setClmFeeInterval(double aClmFeeInterval)
	{
		ClmFeeInterval = aClmFeeInterval;
	}
	public void setClmFeeInterval(String aClmFeeInterval)
	{
		if (aClmFeeInterval != null && !aClmFeeInterval.equals(""))
		{
			Double tDouble = new Double(aClmFeeInterval);
			double d = tDouble.doubleValue();
			ClmFeeInterval = d;
		}
	}

	public String getClmFeeFlag()
	{
		return ClmFeeFlag;
	}
	public void setClmFeeFlag(String aClmFeeFlag)
	{
		ClmFeeFlag = aClmFeeFlag;
	}
	public String getCalCode2()
	{
		return CalCode2;
	}
	public void setCalCode2(String aCalCode2)
	{
		CalCode2 = aCalCode2;
	}
	/**
	* 1.金额 2.比例
	*/
	public String getCalResultType()
	{
		return CalResultType;
	}
	public void setCalResultType(String aCalResultType)
	{
		CalResultType = aCalResultType;
	}
	public double getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(double aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		if (aDefaultValue != null && !aDefaultValue.equals(""))
		{
			Double tDouble = new Double(aDefaultValue);
			double d = tDouble.doubleValue();
			DefaultValue = d;
		}
	}

	public String getCalCtrlFlag()
	{
		return CalCtrlFlag;
	}
	public void setCalCtrlFlag(String aCalCtrlFlag)
	{
		CalCtrlFlag = aCalCtrlFlag;
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
	* 使用另外一个 PD_LMClaimCtrlFeeSchema 对象给 Schema 赋值
	* @param: aPD_LMClaimCtrlFeeSchema PD_LMClaimCtrlFeeSchema
	**/
	public void setSchema(PD_LMClaimCtrlFeeSchema aPD_LMClaimCtrlFeeSchema)
	{
		this.ClaimCtrlCode = aPD_LMClaimCtrlFeeSchema.getClaimCtrlCode();
		this.ClmFeeMIN = aPD_LMClaimCtrlFeeSchema.getClmFeeMIN();
		this.ClmFeeMINFlag = aPD_LMClaimCtrlFeeSchema.getClmFeeMINFlag();
		this.ClmFeeInterval = aPD_LMClaimCtrlFeeSchema.getClmFeeInterval();
		this.ClmFeeFlag = aPD_LMClaimCtrlFeeSchema.getClmFeeFlag();
		this.CalCode2 = aPD_LMClaimCtrlFeeSchema.getCalCode2();
		this.CalResultType = aPD_LMClaimCtrlFeeSchema.getCalResultType();
		this.DefaultValue = aPD_LMClaimCtrlFeeSchema.getDefaultValue();
		this.CalCtrlFlag = aPD_LMClaimCtrlFeeSchema.getCalCtrlFlag();
		this.Operator = aPD_LMClaimCtrlFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMClaimCtrlFeeSchema.getMakeDate());
		this.MakeTime = aPD_LMClaimCtrlFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMClaimCtrlFeeSchema.getModifyDate());
		this.ModifyTime = aPD_LMClaimCtrlFeeSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMClaimCtrlFeeSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMClaimCtrlFeeSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMClaimCtrlFeeSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMClaimCtrlFeeSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMClaimCtrlFeeSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMClaimCtrlFeeSchema.getStandbyflag6();
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
			if( rs.getString("ClaimCtrlCode") == null )
				this.ClaimCtrlCode = null;
			else
				this.ClaimCtrlCode = rs.getString("ClaimCtrlCode").trim();

			this.ClmFeeMIN = rs.getDouble("ClmFeeMIN");
			if( rs.getString("ClmFeeMINFlag") == null )
				this.ClmFeeMINFlag = null;
			else
				this.ClmFeeMINFlag = rs.getString("ClmFeeMINFlag").trim();

			this.ClmFeeInterval = rs.getDouble("ClmFeeInterval");
			if( rs.getString("ClmFeeFlag") == null )
				this.ClmFeeFlag = null;
			else
				this.ClmFeeFlag = rs.getString("ClmFeeFlag").trim();

			if( rs.getString("CalCode2") == null )
				this.CalCode2 = null;
			else
				this.CalCode2 = rs.getString("CalCode2").trim();

			if( rs.getString("CalResultType") == null )
				this.CalResultType = null;
			else
				this.CalResultType = rs.getString("CalResultType").trim();

			this.DefaultValue = rs.getDouble("DefaultValue");
			if( rs.getString("CalCtrlFlag") == null )
				this.CalCtrlFlag = null;
			else
				this.CalCtrlFlag = rs.getString("CalCtrlFlag").trim();

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
			logger.debug("数据库中的PD_LMClaimCtrlFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMClaimCtrlFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMClaimCtrlFeeSchema getSchema()
	{
		PD_LMClaimCtrlFeeSchema aPD_LMClaimCtrlFeeSchema = new PD_LMClaimCtrlFeeSchema();
		aPD_LMClaimCtrlFeeSchema.setSchema(this);
		return aPD_LMClaimCtrlFeeSchema;
	}

	public PD_LMClaimCtrlFeeDB getDB()
	{
		PD_LMClaimCtrlFeeDB aDBOper = new PD_LMClaimCtrlFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMClaimCtrlFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClaimCtrlCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmFeeMIN));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeMINFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmFeeInterval));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalResultType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCtrlFlag)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMClaimCtrlFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClaimCtrlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClmFeeMIN = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).doubleValue();
			ClmFeeMINFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmFeeInterval = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ClmFeeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalResultType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DefaultValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CalCtrlFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "PD_LMClaimCtrlFeeSchema";
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
		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlCode));
		}
		if (FCode.equalsIgnoreCase("ClmFeeMIN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeMIN));
		}
		if (FCode.equalsIgnoreCase("ClmFeeMINFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeMINFlag));
		}
		if (FCode.equalsIgnoreCase("ClmFeeInterval"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeInterval));
		}
		if (FCode.equalsIgnoreCase("ClmFeeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeFlag));
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode2));
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalResultType));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCtrlFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlCode);
				break;
			case 1:
				strFieldValue = String.valueOf(ClmFeeMIN);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeMINFlag);
				break;
			case 3:
				strFieldValue = String.valueOf(ClmFeeInterval);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCode2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalResultType);
				break;
			case 7:
				strFieldValue = String.valueOf(DefaultValue);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCtrlFlag);
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

		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlCode = FValue.trim();
			}
			else
				ClaimCtrlCode = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeMIN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClmFeeMIN = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmFeeMINFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeMINFlag = FValue.trim();
			}
			else
				ClmFeeMINFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeInterval"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClmFeeInterval = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmFeeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeFlag = FValue.trim();
			}
			else
				ClmFeeFlag = null;
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode2 = FValue.trim();
			}
			else
				CalCode2 = null;
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalResultType = FValue.trim();
			}
			else
				CalResultType = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCtrlFlag = FValue.trim();
			}
			else
				CalCtrlFlag = null;
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
		PD_LMClaimCtrlFeeSchema other = (PD_LMClaimCtrlFeeSchema)otherObject;
		return
			ClaimCtrlCode.equals(other.getClaimCtrlCode())
			&& ClmFeeMIN == other.getClmFeeMIN()
			&& ClmFeeMINFlag.equals(other.getClmFeeMINFlag())
			&& ClmFeeInterval == other.getClmFeeInterval()
			&& ClmFeeFlag.equals(other.getClmFeeFlag())
			&& CalCode2.equals(other.getCalCode2())
			&& CalResultType.equals(other.getCalResultType())
			&& DefaultValue == other.getDefaultValue()
			&& CalCtrlFlag.equals(other.getCalCtrlFlag())
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return 0;
		}
		if( strFieldName.equals("ClmFeeMIN") ) {
			return 1;
		}
		if( strFieldName.equals("ClmFeeMINFlag") ) {
			return 2;
		}
		if( strFieldName.equals("ClmFeeInterval") ) {
			return 3;
		}
		if( strFieldName.equals("ClmFeeFlag") ) {
			return 4;
		}
		if( strFieldName.equals("CalCode2") ) {
			return 5;
		}
		if( strFieldName.equals("CalResultType") ) {
			return 6;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 7;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
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
				strFieldName = "ClaimCtrlCode";
				break;
			case 1:
				strFieldName = "ClmFeeMIN";
				break;
			case 2:
				strFieldName = "ClmFeeMINFlag";
				break;
			case 3:
				strFieldName = "ClmFeeInterval";
				break;
			case 4:
				strFieldName = "ClmFeeFlag";
				break;
			case 5:
				strFieldName = "CalCode2";
				break;
			case 6:
				strFieldName = "CalResultType";
				break;
			case 7:
				strFieldName = "DefaultValue";
				break;
			case 8:
				strFieldName = "CalCtrlFlag";
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeMIN") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmFeeMINFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeInterval") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmFeeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalResultType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
