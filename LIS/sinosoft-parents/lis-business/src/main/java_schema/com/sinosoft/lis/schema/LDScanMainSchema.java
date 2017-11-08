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
import com.sinosoft.lis.db.LDScanMainDB;

/*
 * <p>ClassName: LDScanMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务未整理模型
 */
public class LDScanMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDScanMainSchema.class);

	// @Field
	/** 单证类型 */
	private String SubType;
	/** 单证类型名称 */
	private String SubTypeName;
	/** 影像页数 */
	private int NumPages;
	/** 定义页面 */
	private String DefPage;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDScanMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SubType";

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
		LDScanMainSchema cloned = (LDScanMainSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		if(aSubType!=null && aSubType.length()>8)
			throw new IllegalArgumentException("单证类型SubType值"+aSubType+"的长度"+aSubType.length()+"大于最大值8");
		SubType = aSubType;
	}
	public String getSubTypeName()
	{
		return SubTypeName;
	}
	public void setSubTypeName(String aSubTypeName)
	{
		if(aSubTypeName!=null && aSubTypeName.length()>50)
			throw new IllegalArgumentException("单证类型名称SubTypeName值"+aSubTypeName+"的长度"+aSubTypeName.length()+"大于最大值50");
		SubTypeName = aSubTypeName;
	}
	public int getNumPages()
	{
		return NumPages;
	}
	public void setNumPages(int aNumPages)
	{
		NumPages = aNumPages;
	}
	public void setNumPages(String aNumPages)
	{
		if (aNumPages != null && !aNumPages.equals(""))
		{
			Integer tInteger = new Integer(aNumPages);
			int i = tInteger.intValue();
			NumPages = i;
		}
	}

	public String getDefPage()
	{
		return DefPage;
	}
	public void setDefPage(String aDefPage)
	{
		if(aDefPage!=null && aDefPage.length()>150)
			throw new IllegalArgumentException("定义页面DefPage值"+aDefPage+"的长度"+aDefPage.length()+"大于最大值150");
		DefPage = aDefPage;
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

	/**
	* 使用另外一个 LDScanMainSchema 对象给 Schema 赋值
	* @param: aLDScanMainSchema LDScanMainSchema
	**/
	public void setSchema(LDScanMainSchema aLDScanMainSchema)
	{
		this.SubType = aLDScanMainSchema.getSubType();
		this.SubTypeName = aLDScanMainSchema.getSubTypeName();
		this.NumPages = aLDScanMainSchema.getNumPages();
		this.DefPage = aLDScanMainSchema.getDefPage();
		this.Operator = aLDScanMainSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDScanMainSchema.getMakeDate());
		this.MakeTime = aLDScanMainSchema.getMakeTime();
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
			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("SubTypeName") == null )
				this.SubTypeName = null;
			else
				this.SubTypeName = rs.getString("SubTypeName").trim();

			this.NumPages = rs.getInt("NumPages");
			if( rs.getString("DefPage") == null )
				this.DefPage = null;
			else
				this.DefPage = rs.getString("DefPage").trim();

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
			logger.debug("数据库中的LDScanMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDScanMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDScanMainSchema getSchema()
	{
		LDScanMainSchema aLDScanMainSchema = new LDScanMainSchema();
		aLDScanMainSchema.setSchema(this);
		return aLDScanMainSchema;
	}

	public LDScanMainDB getDB()
	{
		LDScanMainDB aDBOper = new LDScanMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDScanMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumPages));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefPage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDScanMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			NumPages= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			DefPage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDScanMainSchema";
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
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("SubTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubTypeName));
		}
		if (FCode.equalsIgnoreCase("NumPages"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumPages));
		}
		if (FCode.equalsIgnoreCase("DefPage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefPage));
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
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubTypeName);
				break;
			case 2:
				strFieldValue = String.valueOf(NumPages);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DefPage);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
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

		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("SubTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubTypeName = FValue.trim();
			}
			else
				SubTypeName = null;
		}
		if (FCode.equalsIgnoreCase("NumPages"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumPages = i;
			}
		}
		if (FCode.equalsIgnoreCase("DefPage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefPage = FValue.trim();
			}
			else
				DefPage = null;
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
		LDScanMainSchema other = (LDScanMainSchema)otherObject;
		return
			SubType.equals(other.getSubType())
			&& SubTypeName.equals(other.getSubTypeName())
			&& NumPages == other.getNumPages()
			&& DefPage.equals(other.getDefPage())
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
		if( strFieldName.equals("SubType") ) {
			return 0;
		}
		if( strFieldName.equals("SubTypeName") ) {
			return 1;
		}
		if( strFieldName.equals("NumPages") ) {
			return 2;
		}
		if( strFieldName.equals("DefPage") ) {
			return 3;
		}
		if( strFieldName.equals("Operator") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
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
				strFieldName = "SubType";
				break;
			case 1:
				strFieldName = "SubTypeName";
				break;
			case 2:
				strFieldName = "NumPages";
				break;
			case 3:
				strFieldName = "DefPage";
				break;
			case 4:
				strFieldName = "Operator";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
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
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NumPages") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DefPage") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
