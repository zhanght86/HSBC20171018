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
import com.sinosoft.lis.db.PD_LMDutyGetClmCalDB;

/*
 * <p>ClassName: PD_LMDutyGetClmCalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyGetClmCalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMDutyGetClmCalSchema.class);

	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付名称 */
	private String GetDutyName;
	/** 算法1 */
	private String CalCode1;
	/** 算法2 */
	private String CalCode2;
	/** 算法3 */
	private String CalCode3;
	/** 算法4 */
	private String CalCode4;
	/** 算法5 */
	private String CalCode5;
	/** 算法6 */
	private String CalCode6;
	/** 算法7 */
	private String CalCode7;
	/** 算法8 */
	private String CalCode8;
	/** 算法9 */
	private String CalCode9;
	/** 算法10 */
	private String CalCode10;
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyGetClmCalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GetDutyCode";
		pk[1] = "GetDutyKind";

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
		PD_LMDutyGetClmCalSchema cloned = (PD_LMDutyGetClmCalSchema)super.clone();
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
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	/**
	* 用于描述不用于理赔权限分配的公式
	*/
	public String getCalCode1()
	{
		return CalCode1;
	}
	public void setCalCode1(String aCalCode1)
	{
		CalCode1 = aCalCode1;
	}
	/**
	* 未成年人保额
	*/
	public String getCalCode2()
	{
		return CalCode2;
	}
	public void setCalCode2(String aCalCode2)
	{
		CalCode2 = aCalCode2;
	}
	/**
	* 描述涉及未成年人的不用于权限分配的公式
	*/
	public String getCalCode3()
	{
		return CalCode3;
	}
	public void setCalCode3(String aCalCode3)
	{
		CalCode3 = aCalCode3;
	}
	public String getCalCode4()
	{
		return CalCode4;
	}
	public void setCalCode4(String aCalCode4)
	{
		CalCode4 = aCalCode4;
	}
	public String getCalCode5()
	{
		return CalCode5;
	}
	public void setCalCode5(String aCalCode5)
	{
		CalCode5 = aCalCode5;
	}
	public String getCalCode6()
	{
		return CalCode6;
	}
	public void setCalCode6(String aCalCode6)
	{
		CalCode6 = aCalCode6;
	}
	public String getCalCode7()
	{
		return CalCode7;
	}
	public void setCalCode7(String aCalCode7)
	{
		CalCode7 = aCalCode7;
	}
	public String getCalCode8()
	{
		return CalCode8;
	}
	public void setCalCode8(String aCalCode8)
	{
		CalCode8 = aCalCode8;
	}
	public String getCalCode9()
	{
		return CalCode9;
	}
	public void setCalCode9(String aCalCode9)
	{
		CalCode9 = aCalCode9;
	}
	public String getCalCode10()
	{
		return CalCode10;
	}
	public void setCalCode10(String aCalCode10)
	{
		CalCode10 = aCalCode10;
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
	* 使用另外一个 PD_LMDutyGetClmCalSchema 对象给 Schema 赋值
	* @param: aPD_LMDutyGetClmCalSchema PD_LMDutyGetClmCalSchema
	**/
	public void setSchema(PD_LMDutyGetClmCalSchema aPD_LMDutyGetClmCalSchema)
	{
		this.GetDutyCode = aPD_LMDutyGetClmCalSchema.getGetDutyCode();
		this.GetDutyKind = aPD_LMDutyGetClmCalSchema.getGetDutyKind();
		this.GetDutyName = aPD_LMDutyGetClmCalSchema.getGetDutyName();
		this.CalCode1 = aPD_LMDutyGetClmCalSchema.getCalCode1();
		this.CalCode2 = aPD_LMDutyGetClmCalSchema.getCalCode2();
		this.CalCode3 = aPD_LMDutyGetClmCalSchema.getCalCode3();
		this.CalCode4 = aPD_LMDutyGetClmCalSchema.getCalCode4();
		this.CalCode5 = aPD_LMDutyGetClmCalSchema.getCalCode5();
		this.CalCode6 = aPD_LMDutyGetClmCalSchema.getCalCode6();
		this.CalCode7 = aPD_LMDutyGetClmCalSchema.getCalCode7();
		this.CalCode8 = aPD_LMDutyGetClmCalSchema.getCalCode8();
		this.CalCode9 = aPD_LMDutyGetClmCalSchema.getCalCode9();
		this.CalCode10 = aPD_LMDutyGetClmCalSchema.getCalCode10();
		this.Operator = aPD_LMDutyGetClmCalSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyGetClmCalSchema.getMakeDate());
		this.MakeTime = aPD_LMDutyGetClmCalSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyGetClmCalSchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyGetClmCalSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyGetClmCalSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyGetClmCalSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyGetClmCalSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyGetClmCalSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyGetClmCalSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyGetClmCalSchema.getStandbyflag6();
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

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("CalCode1") == null )
				this.CalCode1 = null;
			else
				this.CalCode1 = rs.getString("CalCode1").trim();

			if( rs.getString("CalCode2") == null )
				this.CalCode2 = null;
			else
				this.CalCode2 = rs.getString("CalCode2").trim();

			if( rs.getString("CalCode3") == null )
				this.CalCode3 = null;
			else
				this.CalCode3 = rs.getString("CalCode3").trim();

			if( rs.getString("CalCode4") == null )
				this.CalCode4 = null;
			else
				this.CalCode4 = rs.getString("CalCode4").trim();

			if( rs.getString("CalCode5") == null )
				this.CalCode5 = null;
			else
				this.CalCode5 = rs.getString("CalCode5").trim();

			if( rs.getString("CalCode6") == null )
				this.CalCode6 = null;
			else
				this.CalCode6 = rs.getString("CalCode6").trim();

			if( rs.getString("CalCode7") == null )
				this.CalCode7 = null;
			else
				this.CalCode7 = rs.getString("CalCode7").trim();

			if( rs.getString("CalCode8") == null )
				this.CalCode8 = null;
			else
				this.CalCode8 = rs.getString("CalCode8").trim();

			if( rs.getString("CalCode9") == null )
				this.CalCode9 = null;
			else
				this.CalCode9 = rs.getString("CalCode9").trim();

			if( rs.getString("CalCode10") == null )
				this.CalCode10 = null;
			else
				this.CalCode10 = rs.getString("CalCode10").trim();

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
			logger.debug("数据库中的PD_LMDutyGetClmCal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetClmCalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyGetClmCalSchema getSchema()
	{
		PD_LMDutyGetClmCalSchema aPD_LMDutyGetClmCalSchema = new PD_LMDutyGetClmCalSchema();
		aPD_LMDutyGetClmCalSchema.setSchema(this);
		return aPD_LMDutyGetClmCalSchema;
	}

	public PD_LMDutyGetClmCalDB getDB()
	{
		PD_LMDutyGetClmCalDB aDBOper = new PD_LMDutyGetClmCalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetClmCal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode10)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetClmCal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCode3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalCode4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalCode5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCode6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalCode7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CalCode8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalCode9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CalCode10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetClmCalSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("CalCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode1));
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode2));
		}
		if (FCode.equalsIgnoreCase("CalCode3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode3));
		}
		if (FCode.equalsIgnoreCase("CalCode4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode4));
		}
		if (FCode.equalsIgnoreCase("CalCode5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode5));
		}
		if (FCode.equalsIgnoreCase("CalCode6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode6));
		}
		if (FCode.equalsIgnoreCase("CalCode7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode7));
		}
		if (FCode.equalsIgnoreCase("CalCode8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode8));
		}
		if (FCode.equalsIgnoreCase("CalCode9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode9));
		}
		if (FCode.equalsIgnoreCase("CalCode10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode10));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalCode1);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalCode2);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCode3);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalCode4);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCode5);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCode6);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalCode7);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CalCode8);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalCode9);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CalCode10);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 20:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 21:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 22:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 23:
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
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
		}
		if (FCode.equalsIgnoreCase("CalCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode1 = FValue.trim();
			}
			else
				CalCode1 = null;
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
		if (FCode.equalsIgnoreCase("CalCode3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode3 = FValue.trim();
			}
			else
				CalCode3 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode4 = FValue.trim();
			}
			else
				CalCode4 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode5 = FValue.trim();
			}
			else
				CalCode5 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode6 = FValue.trim();
			}
			else
				CalCode6 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode7 = FValue.trim();
			}
			else
				CalCode7 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode8 = FValue.trim();
			}
			else
				CalCode8 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode9 = FValue.trim();
			}
			else
				CalCode9 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode10 = FValue.trim();
			}
			else
				CalCode10 = null;
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
		PD_LMDutyGetClmCalSchema other = (PD_LMDutyGetClmCalSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& GetDutyName.equals(other.getGetDutyName())
			&& CalCode1.equals(other.getCalCode1())
			&& CalCode2.equals(other.getCalCode2())
			&& CalCode3.equals(other.getCalCode3())
			&& CalCode4.equals(other.getCalCode4())
			&& CalCode5.equals(other.getCalCode5())
			&& CalCode6.equals(other.getCalCode6())
			&& CalCode7.equals(other.getCalCode7())
			&& CalCode8.equals(other.getCalCode8())
			&& CalCode9.equals(other.getCalCode9())
			&& CalCode10.equals(other.getCalCode10())
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
		if( strFieldName.equals("GetDutyName") ) {
			return 2;
		}
		if( strFieldName.equals("CalCode1") ) {
			return 3;
		}
		if( strFieldName.equals("CalCode2") ) {
			return 4;
		}
		if( strFieldName.equals("CalCode3") ) {
			return 5;
		}
		if( strFieldName.equals("CalCode4") ) {
			return 6;
		}
		if( strFieldName.equals("CalCode5") ) {
			return 7;
		}
		if( strFieldName.equals("CalCode6") ) {
			return 8;
		}
		if( strFieldName.equals("CalCode7") ) {
			return 9;
		}
		if( strFieldName.equals("CalCode8") ) {
			return 10;
		}
		if( strFieldName.equals("CalCode9") ) {
			return 11;
		}
		if( strFieldName.equals("CalCode10") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 22;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 23;
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
				strFieldName = "GetDutyName";
				break;
			case 3:
				strFieldName = "CalCode1";
				break;
			case 4:
				strFieldName = "CalCode2";
				break;
			case 5:
				strFieldName = "CalCode3";
				break;
			case 6:
				strFieldName = "CalCode4";
				break;
			case 7:
				strFieldName = "CalCode5";
				break;
			case 8:
				strFieldName = "CalCode6";
				break;
			case 9:
				strFieldName = "CalCode7";
				break;
			case 10:
				strFieldName = "CalCode8";
				break;
			case 11:
				strFieldName = "CalCode9";
				break;
			case 12:
				strFieldName = "CalCode10";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "Standbyflag1";
				break;
			case 19:
				strFieldName = "Standbyflag2";
				break;
			case 20:
				strFieldName = "Standbyflag3";
				break;
			case 21:
				strFieldName = "Standbyflag4";
				break;
			case 22:
				strFieldName = "Standbyflag5";
				break;
			case 23:
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
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode10") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
