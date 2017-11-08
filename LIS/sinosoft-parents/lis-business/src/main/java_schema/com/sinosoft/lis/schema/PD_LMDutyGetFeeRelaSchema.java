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
import com.sinosoft.lis.db.PD_LMDutyGetFeeRelaDB;

/*
 * <p>ClassName: PD_LMDutyGetFeeRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyGetFeeRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMDutyGetFeeRelaSchema.class);

	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 账单项目编码 */
	private String ClmFeeCode;
	/** 给付名称 */
	private String GetDutyName;
	/** 账单项目名称 */
	private String ClmFeeName;
	/** 费用计算方式 */
	private String ClmFeeCalType;
	/** 费用明细计算公式 */
	private String ClmFeeCalCode;
	/** 费用默认值 */
	private String ClmFeeDefValue;
	/** 每日限额控制方式 */
	private String DayFeeMAXCtrl;
	/** 每日限额计算公式 */
	private String DayFeeMaxCalCode;
	/** 每日限额固定值 */
	private double DayFeeMaxValue;
	/** 总限额控制方式 */
	private String TotalFeeMaxCtrl;
	/** 总限额计算公式 */
	private String TotalFeeMaxCalCode;
	/** 总限额固定值 */
	private double TotalFeeMaxValue;
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

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyGetFeeRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "GetDutyCode";
		pk[1] = "GetDutyKind";
		pk[2] = "ClmFeeCode";

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
		PD_LMDutyGetFeeRelaSchema cloned = (PD_LMDutyGetFeeRelaSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getClmFeeCode()
	{
		return ClmFeeCode;
	}
	public void setClmFeeCode(String aClmFeeCode)
	{
		ClmFeeCode = aClmFeeCode;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	public String getClmFeeName()
	{
		return ClmFeeName;
	}
	public void setClmFeeName(String aClmFeeName)
	{
		ClmFeeName = aClmFeeName;
	}
	/**
	* 00-取默认值 01-录入 02-使用计算公式
	*/
	public String getClmFeeCalType()
	{
		return ClmFeeCalType;
	}
	public void setClmFeeCalType(String aClmFeeCalType)
	{
		ClmFeeCalType = aClmFeeCalType;
	}
	public String getClmFeeCalCode()
	{
		return ClmFeeCalCode;
	}
	public void setClmFeeCalCode(String aClmFeeCalCode)
	{
		ClmFeeCalCode = aClmFeeCalCode;
	}
	public String getClmFeeDefValue()
	{
		return ClmFeeDefValue;
	}
	public void setClmFeeDefValue(String aClmFeeDefValue)
	{
		ClmFeeDefValue = aClmFeeDefValue;
	}
	public String getDayFeeMAXCtrl()
	{
		return DayFeeMAXCtrl;
	}
	public void setDayFeeMAXCtrl(String aDayFeeMAXCtrl)
	{
		DayFeeMAXCtrl = aDayFeeMAXCtrl;
	}
	public String getDayFeeMaxCalCode()
	{
		return DayFeeMaxCalCode;
	}
	public void setDayFeeMaxCalCode(String aDayFeeMaxCalCode)
	{
		DayFeeMaxCalCode = aDayFeeMaxCalCode;
	}
	public double getDayFeeMaxValue()
	{
		return DayFeeMaxValue;
	}
	public void setDayFeeMaxValue(double aDayFeeMaxValue)
	{
		DayFeeMaxValue = aDayFeeMaxValue;
	}
	public void setDayFeeMaxValue(String aDayFeeMaxValue)
	{
		if (aDayFeeMaxValue != null && !aDayFeeMaxValue.equals(""))
		{
			Double tDouble = new Double(aDayFeeMaxValue);
			double d = tDouble.doubleValue();
			DayFeeMaxValue = d;
		}
	}

	public String getTotalFeeMaxCtrl()
	{
		return TotalFeeMaxCtrl;
	}
	public void setTotalFeeMaxCtrl(String aTotalFeeMaxCtrl)
	{
		TotalFeeMaxCtrl = aTotalFeeMaxCtrl;
	}
	public String getTotalFeeMaxCalCode()
	{
		return TotalFeeMaxCalCode;
	}
	public void setTotalFeeMaxCalCode(String aTotalFeeMaxCalCode)
	{
		TotalFeeMaxCalCode = aTotalFeeMaxCalCode;
	}
	public double getTotalFeeMaxValue()
	{
		return TotalFeeMaxValue;
	}
	public void setTotalFeeMaxValue(double aTotalFeeMaxValue)
	{
		TotalFeeMaxValue = aTotalFeeMaxValue;
	}
	public void setTotalFeeMaxValue(String aTotalFeeMaxValue)
	{
		if (aTotalFeeMaxValue != null && !aTotalFeeMaxValue.equals(""))
		{
			Double tDouble = new Double(aTotalFeeMaxValue);
			double d = tDouble.doubleValue();
			TotalFeeMaxValue = d;
		}
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
	* 使用另外一个 PD_LMDutyGetFeeRelaSchema 对象给 Schema 赋值
	* @param: aPD_LMDutyGetFeeRelaSchema PD_LMDutyGetFeeRelaSchema
	**/
	public void setSchema(PD_LMDutyGetFeeRelaSchema aPD_LMDutyGetFeeRelaSchema)
	{
		this.GetDutyCode = aPD_LMDutyGetFeeRelaSchema.getGetDutyCode();
		this.GetDutyKind = aPD_LMDutyGetFeeRelaSchema.getGetDutyKind();
		this.ClmFeeCode = aPD_LMDutyGetFeeRelaSchema.getClmFeeCode();
		this.GetDutyName = aPD_LMDutyGetFeeRelaSchema.getGetDutyName();
		this.ClmFeeName = aPD_LMDutyGetFeeRelaSchema.getClmFeeName();
		this.ClmFeeCalType = aPD_LMDutyGetFeeRelaSchema.getClmFeeCalType();
		this.ClmFeeCalCode = aPD_LMDutyGetFeeRelaSchema.getClmFeeCalCode();
		this.ClmFeeDefValue = aPD_LMDutyGetFeeRelaSchema.getClmFeeDefValue();
		this.DayFeeMAXCtrl = aPD_LMDutyGetFeeRelaSchema.getDayFeeMAXCtrl();
		this.DayFeeMaxCalCode = aPD_LMDutyGetFeeRelaSchema.getDayFeeMaxCalCode();
		this.DayFeeMaxValue = aPD_LMDutyGetFeeRelaSchema.getDayFeeMaxValue();
		this.TotalFeeMaxCtrl = aPD_LMDutyGetFeeRelaSchema.getTotalFeeMaxCtrl();
		this.TotalFeeMaxCalCode = aPD_LMDutyGetFeeRelaSchema.getTotalFeeMaxCalCode();
		this.TotalFeeMaxValue = aPD_LMDutyGetFeeRelaSchema.getTotalFeeMaxValue();
		this.Operator = aPD_LMDutyGetFeeRelaSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyGetFeeRelaSchema.getMakeDate());
		this.MakeTime = aPD_LMDutyGetFeeRelaSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyGetFeeRelaSchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyGetFeeRelaSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyGetFeeRelaSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyGetFeeRelaSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyGetFeeRelaSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyGetFeeRelaSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyGetFeeRelaSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyGetFeeRelaSchema.getStandbyflag6();
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
			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("ClmFeeCode") == null )
				this.ClmFeeCode = null;
			else
				this.ClmFeeCode = rs.getString("ClmFeeCode").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("ClmFeeName") == null )
				this.ClmFeeName = null;
			else
				this.ClmFeeName = rs.getString("ClmFeeName").trim();

			if( rs.getString("ClmFeeCalType") == null )
				this.ClmFeeCalType = null;
			else
				this.ClmFeeCalType = rs.getString("ClmFeeCalType").trim();

			if( rs.getString("ClmFeeCalCode") == null )
				this.ClmFeeCalCode = null;
			else
				this.ClmFeeCalCode = rs.getString("ClmFeeCalCode").trim();

			if( rs.getString("ClmFeeDefValue") == null )
				this.ClmFeeDefValue = null;
			else
				this.ClmFeeDefValue = rs.getString("ClmFeeDefValue").trim();

			if( rs.getString("DayFeeMAXCtrl") == null )
				this.DayFeeMAXCtrl = null;
			else
				this.DayFeeMAXCtrl = rs.getString("DayFeeMAXCtrl").trim();

			if( rs.getString("DayFeeMaxCalCode") == null )
				this.DayFeeMaxCalCode = null;
			else
				this.DayFeeMaxCalCode = rs.getString("DayFeeMaxCalCode").trim();

			this.DayFeeMaxValue = rs.getDouble("DayFeeMaxValue");
			if( rs.getString("TotalFeeMaxCtrl") == null )
				this.TotalFeeMaxCtrl = null;
			else
				this.TotalFeeMaxCtrl = rs.getString("TotalFeeMaxCtrl").trim();

			if( rs.getString("TotalFeeMaxCalCode") == null )
				this.TotalFeeMaxCalCode = null;
			else
				this.TotalFeeMaxCalCode = rs.getString("TotalFeeMaxCalCode").trim();

			this.TotalFeeMaxValue = rs.getDouble("TotalFeeMaxValue");
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
			logger.debug("数据库中的PD_LMDutyGetFeeRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetFeeRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyGetFeeRelaSchema getSchema()
	{
		PD_LMDutyGetFeeRelaSchema aPD_LMDutyGetFeeRelaSchema = new PD_LMDutyGetFeeRelaSchema();
		aPD_LMDutyGetFeeRelaSchema.setSchema(this);
		return aPD_LMDutyGetFeeRelaSchema;
	}

	public PD_LMDutyGetFeeRelaDB getDB()
	{
		PD_LMDutyGetFeeRelaDB aDBOper = new PD_LMDutyGetFeeRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetFeeRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeDefValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DayFeeMAXCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DayFeeMaxCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DayFeeMaxValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TotalFeeMaxCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TotalFeeMaxCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalFeeMaxValue));strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetFeeRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClmFeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClmFeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ClmFeeCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClmFeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ClmFeeDefValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DayFeeMAXCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DayFeeMaxCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DayFeeMaxValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			TotalFeeMaxCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			TotalFeeMaxCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			TotalFeeMaxValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetFeeRelaSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("ClmFeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("ClmFeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeName));
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeCalType));
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeCalCode));
		}
		if (FCode.equalsIgnoreCase("ClmFeeDefValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeDefValue));
		}
		if (FCode.equalsIgnoreCase("DayFeeMAXCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DayFeeMAXCtrl));
		}
		if (FCode.equalsIgnoreCase("DayFeeMaxCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DayFeeMaxCalCode));
		}
		if (FCode.equalsIgnoreCase("DayFeeMaxValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DayFeeMaxValue));
		}
		if (FCode.equalsIgnoreCase("TotalFeeMaxCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalFeeMaxCtrl));
		}
		if (FCode.equalsIgnoreCase("TotalFeeMaxCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalFeeMaxCalCode));
		}
		if (FCode.equalsIgnoreCase("TotalFeeMaxValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalFeeMaxValue));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeCalType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeCalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeDefValue);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DayFeeMAXCtrl);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DayFeeMaxCalCode);
				break;
			case 10:
				strFieldValue = String.valueOf(DayFeeMaxValue);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(TotalFeeMaxCtrl);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(TotalFeeMaxCalCode);
				break;
			case 13:
				strFieldValue = String.valueOf(TotalFeeMaxValue);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 21:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 22:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 23:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 24:
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
		if (FCode.equalsIgnoreCase("ClmFeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeCode = FValue.trim();
			}
			else
				ClmFeeCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeName = FValue.trim();
			}
			else
				ClmFeeName = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeCalType = FValue.trim();
			}
			else
				ClmFeeCalType = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeCalCode = FValue.trim();
			}
			else
				ClmFeeCalCode = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeDefValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeDefValue = FValue.trim();
			}
			else
				ClmFeeDefValue = null;
		}
		if (FCode.equalsIgnoreCase("DayFeeMAXCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DayFeeMAXCtrl = FValue.trim();
			}
			else
				DayFeeMAXCtrl = null;
		}
		if (FCode.equalsIgnoreCase("DayFeeMaxCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DayFeeMaxCalCode = FValue.trim();
			}
			else
				DayFeeMaxCalCode = null;
		}
		if (FCode.equalsIgnoreCase("DayFeeMaxValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DayFeeMaxValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("TotalFeeMaxCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TotalFeeMaxCtrl = FValue.trim();
			}
			else
				TotalFeeMaxCtrl = null;
		}
		if (FCode.equalsIgnoreCase("TotalFeeMaxCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TotalFeeMaxCalCode = FValue.trim();
			}
			else
				TotalFeeMaxCalCode = null;
		}
		if (FCode.equalsIgnoreCase("TotalFeeMaxValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalFeeMaxValue = d;
			}
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
		PD_LMDutyGetFeeRelaSchema other = (PD_LMDutyGetFeeRelaSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& ClmFeeCode.equals(other.getClmFeeCode())
			&& GetDutyName.equals(other.getGetDutyName())
			&& ClmFeeName.equals(other.getClmFeeName())
			&& ClmFeeCalType.equals(other.getClmFeeCalType())
			&& ClmFeeCalCode.equals(other.getClmFeeCalCode())
			&& ClmFeeDefValue.equals(other.getClmFeeDefValue())
			&& DayFeeMAXCtrl.equals(other.getDayFeeMAXCtrl())
			&& DayFeeMaxCalCode.equals(other.getDayFeeMaxCalCode())
			&& DayFeeMaxValue == other.getDayFeeMaxValue()
			&& TotalFeeMaxCtrl.equals(other.getTotalFeeMaxCtrl())
			&& TotalFeeMaxCalCode.equals(other.getTotalFeeMaxCalCode())
			&& TotalFeeMaxValue == other.getTotalFeeMaxValue()
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
		if( strFieldName.equals("GetDutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 1;
		}
		if( strFieldName.equals("ClmFeeCode") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 3;
		}
		if( strFieldName.equals("ClmFeeName") ) {
			return 4;
		}
		if( strFieldName.equals("ClmFeeCalType") ) {
			return 5;
		}
		if( strFieldName.equals("ClmFeeCalCode") ) {
			return 6;
		}
		if( strFieldName.equals("ClmFeeDefValue") ) {
			return 7;
		}
		if( strFieldName.equals("DayFeeMAXCtrl") ) {
			return 8;
		}
		if( strFieldName.equals("DayFeeMaxCalCode") ) {
			return 9;
		}
		if( strFieldName.equals("DayFeeMaxValue") ) {
			return 10;
		}
		if( strFieldName.equals("TotalFeeMaxCtrl") ) {
			return 11;
		}
		if( strFieldName.equals("TotalFeeMaxCalCode") ) {
			return 12;
		}
		if( strFieldName.equals("TotalFeeMaxValue") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 22;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 23;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 24;
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
				strFieldName = "GetDutyCode";
				break;
			case 1:
				strFieldName = "GetDutyKind";
				break;
			case 2:
				strFieldName = "ClmFeeCode";
				break;
			case 3:
				strFieldName = "GetDutyName";
				break;
			case 4:
				strFieldName = "ClmFeeName";
				break;
			case 5:
				strFieldName = "ClmFeeCalType";
				break;
			case 6:
				strFieldName = "ClmFeeCalCode";
				break;
			case 7:
				strFieldName = "ClmFeeDefValue";
				break;
			case 8:
				strFieldName = "DayFeeMAXCtrl";
				break;
			case 9:
				strFieldName = "DayFeeMaxCalCode";
				break;
			case 10:
				strFieldName = "DayFeeMaxValue";
				break;
			case 11:
				strFieldName = "TotalFeeMaxCtrl";
				break;
			case 12:
				strFieldName = "TotalFeeMaxCalCode";
				break;
			case 13:
				strFieldName = "TotalFeeMaxValue";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "Standbyflag1";
				break;
			case 20:
				strFieldName = "Standbyflag2";
				break;
			case 21:
				strFieldName = "Standbyflag3";
				break;
			case 22:
				strFieldName = "Standbyflag4";
				break;
			case 23:
				strFieldName = "Standbyflag5";
				break;
			case 24:
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
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeDefValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DayFeeMAXCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DayFeeMaxCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DayFeeMaxValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TotalFeeMaxCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TotalFeeMaxCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TotalFeeMaxValue") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
