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
import com.sinosoft.lis.db.LDWorkCalendarDB;

/*
 * <p>ClassName: LDWorkCalendarSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作日历
 */
public class LDWorkCalendarSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDWorkCalendarSchema.class);
	// @Field
	/** 序列号 */
	private String CalSN;
	/** 工作日类型 */
	private String CalType;
	/** 年份 */
	private String Year;
	/** 日期 */
	private Date CalDate;
	/** 日期类型 */
	private String DateType;
	/** 其他属性 */
	private String OtherProp;
	/** 上午开始时间 */
	private String AmBegin;
	/** 上午结束时间 */
	private String AmEnd;
	/** 上午工作时间 */
	private String AMWorkTime;
	/** 下午开始时间 */
	private String PmBegin;
	/** 下午结束时间 */
	private String PmEnd;
	/** 下午工作时间 */
	private String PMWorkTime;
	/** 日工作时间 */
	private String WorkTime;
	/** 操作机构 */
	private String OperateCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDWorkCalendarSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CalSN";

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
		LDWorkCalendarSchema cloned = (LDWorkCalendarSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCalSN()
	{
		return CalSN;
	}
	public void setCalSN(String aCalSN)
	{
		if(aCalSN!=null && aCalSN.length()>20)
			throw new IllegalArgumentException("序列号CalSN值"+aCalSN+"的长度"+aCalSN.length()+"大于最大值20");
		CalSN = aCalSN;
	}
	/**
	* 节假日
	*/
	public String getCalType()
	{
		return CalType;
	}
	public void setCalType(String aCalType)
	{
		if(aCalType!=null && aCalType.length()>2)
			throw new IllegalArgumentException("工作日类型CalType值"+aCalType+"的长度"+aCalType.length()+"大于最大值2");
		CalType = aCalType;
	}
	public String getYear()
	{
		return Year;
	}
	public void setYear(String aYear)
	{
		if(aYear!=null && aYear.length()>20)
			throw new IllegalArgumentException("年份Year值"+aYear+"的长度"+aYear.length()+"大于最大值20");
		Year = aYear;
	}
	public String getCalDate()
	{
		if( CalDate != null )
			return fDate.getString(CalDate);
		else
			return null;
	}
	public void setCalDate(Date aCalDate)
	{
		CalDate = aCalDate;
	}
	public void setCalDate(String aCalDate)
	{
		if (aCalDate != null && !aCalDate.equals("") )
		{
			CalDate = fDate.getDate( aCalDate );
		}
		else
			CalDate = null;
	}

	/**
	* 节假日
	*/
	public String getDateType()
	{
		return DateType;
	}
	public void setDateType(String aDateType)
	{
		if(aDateType!=null && aDateType.length()>2)
			throw new IllegalArgumentException("日期类型DateType值"+aDateType+"的长度"+aDateType.length()+"大于最大值2");
		DateType = aDateType;
	}
	public String getOtherProp()
	{
		return OtherProp;
	}
	public void setOtherProp(String aOtherProp)
	{
		if(aOtherProp!=null && aOtherProp.length()>100)
			throw new IllegalArgumentException("其他属性OtherProp值"+aOtherProp+"的长度"+aOtherProp.length()+"大于最大值100");
		OtherProp = aOtherProp;
	}
	public String getAmBegin()
	{
		return AmBegin;
	}
	public void setAmBegin(String aAmBegin)
	{
		if(aAmBegin!=null && aAmBegin.length()>10)
			throw new IllegalArgumentException("上午开始时间AmBegin值"+aAmBegin+"的长度"+aAmBegin.length()+"大于最大值10");
		AmBegin = aAmBegin;
	}
	public String getAmEnd()
	{
		return AmEnd;
	}
	public void setAmEnd(String aAmEnd)
	{
		if(aAmEnd!=null && aAmEnd.length()>10)
			throw new IllegalArgumentException("上午结束时间AmEnd值"+aAmEnd+"的长度"+aAmEnd.length()+"大于最大值10");
		AmEnd = aAmEnd;
	}
	public String getAMWorkTime()
	{
		return AMWorkTime;
	}
	public void setAMWorkTime(String aAMWorkTime)
	{
		if(aAMWorkTime!=null && aAMWorkTime.length()>10)
			throw new IllegalArgumentException("上午工作时间AMWorkTime值"+aAMWorkTime+"的长度"+aAMWorkTime.length()+"大于最大值10");
		AMWorkTime = aAMWorkTime;
	}
	public String getPmBegin()
	{
		return PmBegin;
	}
	public void setPmBegin(String aPmBegin)
	{
		if(aPmBegin!=null && aPmBegin.length()>10)
			throw new IllegalArgumentException("下午开始时间PmBegin值"+aPmBegin+"的长度"+aPmBegin.length()+"大于最大值10");
		PmBegin = aPmBegin;
	}
	public String getPmEnd()
	{
		return PmEnd;
	}
	public void setPmEnd(String aPmEnd)
	{
		if(aPmEnd!=null && aPmEnd.length()>10)
			throw new IllegalArgumentException("下午结束时间PmEnd值"+aPmEnd+"的长度"+aPmEnd.length()+"大于最大值10");
		PmEnd = aPmEnd;
	}
	public String getPMWorkTime()
	{
		return PMWorkTime;
	}
	public void setPMWorkTime(String aPMWorkTime)
	{
		if(aPMWorkTime!=null && aPMWorkTime.length()>10)
			throw new IllegalArgumentException("下午工作时间PMWorkTime值"+aPMWorkTime+"的长度"+aPMWorkTime.length()+"大于最大值10");
		PMWorkTime = aPMWorkTime;
	}
	public String getWorkTime()
	{
		return WorkTime;
	}
	public void setWorkTime(String aWorkTime)
	{
		if(aWorkTime!=null && aWorkTime.length()>10)
			throw new IllegalArgumentException("日工作时间WorkTime值"+aWorkTime+"的长度"+aWorkTime.length()+"大于最大值10");
		WorkTime = aWorkTime;
	}
	public String getOperateCom()
	{
		return OperateCom;
	}
	public void setOperateCom(String aOperateCom)
	{
		if(aOperateCom!=null && aOperateCom.length()>20)
			throw new IllegalArgumentException("操作机构OperateCom值"+aOperateCom+"的长度"+aOperateCom.length()+"大于最大值20");
		OperateCom = aOperateCom;
	}
	/**
	* 对于该操作员，做如下约定：<p>
	* SYS001 －－－ 表示专门用来附加险中止转主险暂缴费的操作员。
	*/
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>10)
			throw new IllegalArgumentException("最后一次操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值10");
		ModifyOperator = aModifyOperator;
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
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDWorkCalendarSchema 对象给 Schema 赋值
	* @param: aLDWorkCalendarSchema LDWorkCalendarSchema
	**/
	public void setSchema(LDWorkCalendarSchema aLDWorkCalendarSchema)
	{
		this.CalSN = aLDWorkCalendarSchema.getCalSN();
		this.CalType = aLDWorkCalendarSchema.getCalType();
		this.Year = aLDWorkCalendarSchema.getYear();
		this.CalDate = fDate.getDate( aLDWorkCalendarSchema.getCalDate());
		this.DateType = aLDWorkCalendarSchema.getDateType();
		this.OtherProp = aLDWorkCalendarSchema.getOtherProp();
		this.AmBegin = aLDWorkCalendarSchema.getAmBegin();
		this.AmEnd = aLDWorkCalendarSchema.getAmEnd();
		this.AMWorkTime = aLDWorkCalendarSchema.getAMWorkTime();
		this.PmBegin = aLDWorkCalendarSchema.getPmBegin();
		this.PmEnd = aLDWorkCalendarSchema.getPmEnd();
		this.PMWorkTime = aLDWorkCalendarSchema.getPMWorkTime();
		this.WorkTime = aLDWorkCalendarSchema.getWorkTime();
		this.OperateCom = aLDWorkCalendarSchema.getOperateCom();
		this.Operator = aLDWorkCalendarSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDWorkCalendarSchema.getMakeDate());
		this.MakeTime = aLDWorkCalendarSchema.getMakeTime();
		this.ModifyOperator = aLDWorkCalendarSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDWorkCalendarSchema.getModifyDate());
		this.ModifyTime = aLDWorkCalendarSchema.getModifyTime();
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
			if( rs.getString("CalSN") == null )
				this.CalSN = null;
			else
				this.CalSN = rs.getString("CalSN").trim();

			if( rs.getString("CalType") == null )
				this.CalType = null;
			else
				this.CalType = rs.getString("CalType").trim();

			if( rs.getString("Year") == null )
				this.Year = null;
			else
				this.Year = rs.getString("Year").trim();

			this.CalDate = rs.getDate("CalDate");
			if( rs.getString("DateType") == null )
				this.DateType = null;
			else
				this.DateType = rs.getString("DateType").trim();

			if( rs.getString("OtherProp") == null )
				this.OtherProp = null;
			else
				this.OtherProp = rs.getString("OtherProp").trim();

			if( rs.getString("AmBegin") == null )
				this.AmBegin = null;
			else
				this.AmBegin = rs.getString("AmBegin").trim();

			if( rs.getString("AmEnd") == null )
				this.AmEnd = null;
			else
				this.AmEnd = rs.getString("AmEnd").trim();

			if( rs.getString("AMWorkTime") == null )
				this.AMWorkTime = null;
			else
				this.AMWorkTime = rs.getString("AMWorkTime").trim();

			if( rs.getString("PmBegin") == null )
				this.PmBegin = null;
			else
				this.PmBegin = rs.getString("PmBegin").trim();

			if( rs.getString("PmEnd") == null )
				this.PmEnd = null;
			else
				this.PmEnd = rs.getString("PmEnd").trim();

			if( rs.getString("PMWorkTime") == null )
				this.PMWorkTime = null;
			else
				this.PMWorkTime = rs.getString("PMWorkTime").trim();

			if( rs.getString("WorkTime") == null )
				this.WorkTime = null;
			else
				this.WorkTime = rs.getString("WorkTime").trim();

			if( rs.getString("OperateCom") == null )
				this.OperateCom = null;
			else
				this.OperateCom = rs.getString("OperateCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDWorkCalendar表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWorkCalendarSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDWorkCalendarSchema getSchema()
	{
		LDWorkCalendarSchema aLDWorkCalendarSchema = new LDWorkCalendarSchema();
		aLDWorkCalendarSchema.setSchema(this);
		return aLDWorkCalendarSchema;
	}

	public LDWorkCalendarDB getDB()
	{
		LDWorkCalendarDB aDBOper = new LDWorkCalendarDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWorkCalendar描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalSN)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Year)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherProp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AmBegin)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AmEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AMWorkTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PmBegin)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PmEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PMWorkTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperateCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWorkCalendar>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalSN = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Year = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			DateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherProp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AmBegin = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AmEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AMWorkTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PmBegin = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PmEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PMWorkTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			WorkTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			OperateCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWorkCalendarSchema";
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
		if (FCode.equalsIgnoreCase("CalSN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSN));
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalType));
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Year));
		}
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
		}
		if (FCode.equalsIgnoreCase("DateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DateType));
		}
		if (FCode.equalsIgnoreCase("OtherProp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherProp));
		}
		if (FCode.equalsIgnoreCase("AmBegin"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmBegin));
		}
		if (FCode.equalsIgnoreCase("AmEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmEnd));
		}
		if (FCode.equalsIgnoreCase("AMWorkTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AMWorkTime));
		}
		if (FCode.equalsIgnoreCase("PmBegin"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PmBegin));
		}
		if (FCode.equalsIgnoreCase("PmEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PmEnd));
		}
		if (FCode.equalsIgnoreCase("PMWorkTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PMWorkTime));
		}
		if (FCode.equalsIgnoreCase("WorkTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkTime));
		}
		if (FCode.equalsIgnoreCase("OperateCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperateCom));
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CalSN);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CalType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Year);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DateType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherProp);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AmBegin);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AmEnd);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AMWorkTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PmBegin);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PmEnd);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PMWorkTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(WorkTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(OperateCom);
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
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
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

		if (FCode.equalsIgnoreCase("CalSN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSN = FValue.trim();
			}
			else
				CalSN = null;
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalType = FValue.trim();
			}
			else
				CalType = null;
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Year = FValue.trim();
			}
			else
				Year = null;
		}
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CalDate = fDate.getDate( FValue );
			}
			else
				CalDate = null;
		}
		if (FCode.equalsIgnoreCase("DateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DateType = FValue.trim();
			}
			else
				DateType = null;
		}
		if (FCode.equalsIgnoreCase("OtherProp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherProp = FValue.trim();
			}
			else
				OtherProp = null;
		}
		if (FCode.equalsIgnoreCase("AmBegin"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmBegin = FValue.trim();
			}
			else
				AmBegin = null;
		}
		if (FCode.equalsIgnoreCase("AmEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmEnd = FValue.trim();
			}
			else
				AmEnd = null;
		}
		if (FCode.equalsIgnoreCase("AMWorkTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AMWorkTime = FValue.trim();
			}
			else
				AMWorkTime = null;
		}
		if (FCode.equalsIgnoreCase("PmBegin"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PmBegin = FValue.trim();
			}
			else
				PmBegin = null;
		}
		if (FCode.equalsIgnoreCase("PmEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PmEnd = FValue.trim();
			}
			else
				PmEnd = null;
		}
		if (FCode.equalsIgnoreCase("PMWorkTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PMWorkTime = FValue.trim();
			}
			else
				PMWorkTime = null;
		}
		if (FCode.equalsIgnoreCase("WorkTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkTime = FValue.trim();
			}
			else
				WorkTime = null;
		}
		if (FCode.equalsIgnoreCase("OperateCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperateCom = FValue.trim();
			}
			else
				OperateCom = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LDWorkCalendarSchema other = (LDWorkCalendarSchema)otherObject;
		return
			CalSN.equals(other.getCalSN())
			&& CalType.equals(other.getCalType())
			&& Year.equals(other.getYear())
			&& fDate.getString(CalDate).equals(other.getCalDate())
			&& DateType.equals(other.getDateType())
			&& OtherProp.equals(other.getOtherProp())
			&& AmBegin.equals(other.getAmBegin())
			&& AmEnd.equals(other.getAmEnd())
			&& AMWorkTime.equals(other.getAMWorkTime())
			&& PmBegin.equals(other.getPmBegin())
			&& PmEnd.equals(other.getPmEnd())
			&& PMWorkTime.equals(other.getPMWorkTime())
			&& WorkTime.equals(other.getWorkTime())
			&& OperateCom.equals(other.getOperateCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("CalSN") ) {
			return 0;
		}
		if( strFieldName.equals("CalType") ) {
			return 1;
		}
		if( strFieldName.equals("Year") ) {
			return 2;
		}
		if( strFieldName.equals("CalDate") ) {
			return 3;
		}
		if( strFieldName.equals("DateType") ) {
			return 4;
		}
		if( strFieldName.equals("OtherProp") ) {
			return 5;
		}
		if( strFieldName.equals("AmBegin") ) {
			return 6;
		}
		if( strFieldName.equals("AmEnd") ) {
			return 7;
		}
		if( strFieldName.equals("AMWorkTime") ) {
			return 8;
		}
		if( strFieldName.equals("PmBegin") ) {
			return 9;
		}
		if( strFieldName.equals("PmEnd") ) {
			return 10;
		}
		if( strFieldName.equals("PMWorkTime") ) {
			return 11;
		}
		if( strFieldName.equals("WorkTime") ) {
			return 12;
		}
		if( strFieldName.equals("OperateCom") ) {
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
		if( strFieldName.equals("ModifyOperator") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "CalSN";
				break;
			case 1:
				strFieldName = "CalType";
				break;
			case 2:
				strFieldName = "Year";
				break;
			case 3:
				strFieldName = "CalDate";
				break;
			case 4:
				strFieldName = "DateType";
				break;
			case 5:
				strFieldName = "OtherProp";
				break;
			case 6:
				strFieldName = "AmBegin";
				break;
			case 7:
				strFieldName = "AmEnd";
				break;
			case 8:
				strFieldName = "AMWorkTime";
				break;
			case 9:
				strFieldName = "PmBegin";
				break;
			case 10:
				strFieldName = "PmEnd";
				break;
			case 11:
				strFieldName = "PMWorkTime";
				break;
			case 12:
				strFieldName = "WorkTime";
				break;
			case 13:
				strFieldName = "OperateCom";
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
				strFieldName = "ModifyOperator";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
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
		if( strFieldName.equals("CalSN") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Year") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherProp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmBegin") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmEnd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AMWorkTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PmBegin") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PmEnd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PMWorkTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperateCom") ) {
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
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
