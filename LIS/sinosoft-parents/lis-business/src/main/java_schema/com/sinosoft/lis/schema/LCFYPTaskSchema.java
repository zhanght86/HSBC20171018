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
import com.sinosoft.lis.db.LCFYPTaskDB;

/*
 * <p>ClassName: LCFYPTaskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LCFYPTaskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCFYPTaskSchema.class);

	// @Field
	/** 任务类型 */
	private String TargetType;
	/** 计划任务实施年份 */
	private String TargetYear;
	/** 计划任务实施月份 */
	private String TargetMonth;
	/** 计划任务保费 */
	private double TargetPrem;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCFYPTaskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TargetType";
		pk[1] = "TargetYear";
		pk[2] = "TargetMonth";
		pk[3] = "ManageCom";

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
		LCFYPTaskSchema cloned = (LCFYPTaskSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 0－续收保费计划
	*/
	public String getTargetType()
	{
		return TargetType;
	}
	public void setTargetType(String aTargetType)
	{
		TargetType = aTargetType;
	}
	public String getTargetYear()
	{
		return TargetYear;
	}
	public void setTargetYear(String aTargetYear)
	{
		TargetYear = aTargetYear;
	}
	/**
	* C－意外险：<p>
	* L－寿险；<p>
	* H－附加险
	*/
	public String getTargetMonth()
	{
		return TargetMonth;
	}
	public void setTargetMonth(String aTargetMonth)
	{
		TargetMonth = aTargetMonth;
	}
	public double getTargetPrem()
	{
		return TargetPrem;
	}
	public void setTargetPrem(double aTargetPrem)
	{
		TargetPrem = aTargetPrem;
	}
	public void setTargetPrem(String aTargetPrem)
	{
		if (aTargetPrem != null && !aTargetPrem.equals(""))
		{
			Double tDouble = new Double(aTargetPrem);
			double d = tDouble.doubleValue();
			TargetPrem = d;
		}
	}

	/**
	* E－极短期<p>
	* L－长期<p>
	* S－一年期
	*/
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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

	/**
	* 使用另外一个 LCFYPTaskSchema 对象给 Schema 赋值
	* @param: aLCFYPTaskSchema LCFYPTaskSchema
	**/
	public void setSchema(LCFYPTaskSchema aLCFYPTaskSchema)
	{
		this.TargetType = aLCFYPTaskSchema.getTargetType();
		this.TargetYear = aLCFYPTaskSchema.getTargetYear();
		this.TargetMonth = aLCFYPTaskSchema.getTargetMonth();
		this.TargetPrem = aLCFYPTaskSchema.getTargetPrem();
		this.ManageCom = aLCFYPTaskSchema.getManageCom();
		this.Operator = aLCFYPTaskSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCFYPTaskSchema.getMakeDate());
		this.MakeTime = aLCFYPTaskSchema.getMakeTime();
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
			if( rs.getString("TargetType") == null )
				this.TargetType = null;
			else
				this.TargetType = rs.getString("TargetType").trim();

			if( rs.getString("TargetYear") == null )
				this.TargetYear = null;
			else
				this.TargetYear = rs.getString("TargetYear").trim();

			if( rs.getString("TargetMonth") == null )
				this.TargetMonth = null;
			else
				this.TargetMonth = rs.getString("TargetMonth").trim();

			this.TargetPrem = rs.getDouble("TargetPrem");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCFYPTask表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCFYPTaskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCFYPTaskSchema getSchema()
	{
		LCFYPTaskSchema aLCFYPTaskSchema = new LCFYPTaskSchema();
		aLCFYPTaskSchema.setSchema(this);
		return aLCFYPTaskSchema;
	}

	public LCFYPTaskDB getDB()
	{
		LCFYPTaskDB aDBOper = new LCFYPTaskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCFYPTask描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TargetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TargetYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TargetMonth)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TargetPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCFYPTask>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TargetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TargetYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TargetMonth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TargetPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCFYPTaskSchema";
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
		if (FCode.equalsIgnoreCase("TargetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TargetType));
		}
		if (FCode.equalsIgnoreCase("TargetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TargetYear));
		}
		if (FCode.equalsIgnoreCase("TargetMonth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TargetMonth));
		}
		if (FCode.equalsIgnoreCase("TargetPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TargetPrem));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(TargetType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TargetYear);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TargetMonth);
				break;
			case 3:
				strFieldValue = String.valueOf(TargetPrem);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("TargetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TargetType = FValue.trim();
			}
			else
				TargetType = null;
		}
		if (FCode.equalsIgnoreCase("TargetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TargetYear = FValue.trim();
			}
			else
				TargetYear = null;
		}
		if (FCode.equalsIgnoreCase("TargetMonth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TargetMonth = FValue.trim();
			}
			else
				TargetMonth = null;
		}
		if (FCode.equalsIgnoreCase("TargetPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TargetPrem = d;
			}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCFYPTaskSchema other = (LCFYPTaskSchema)otherObject;
		return
			TargetType.equals(other.getTargetType())
			&& TargetYear.equals(other.getTargetYear())
			&& TargetMonth.equals(other.getTargetMonth())
			&& TargetPrem == other.getTargetPrem()
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("TargetType") ) {
			return 0;
		}
		if( strFieldName.equals("TargetYear") ) {
			return 1;
		}
		if( strFieldName.equals("TargetMonth") ) {
			return 2;
		}
		if( strFieldName.equals("TargetPrem") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
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
				strFieldName = "TargetType";
				break;
			case 1:
				strFieldName = "TargetYear";
				break;
			case 2:
				strFieldName = "TargetMonth";
				break;
			case 3:
				strFieldName = "TargetPrem";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("TargetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TargetYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TargetMonth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TargetPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
