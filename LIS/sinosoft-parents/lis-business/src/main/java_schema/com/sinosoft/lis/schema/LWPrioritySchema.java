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
import com.sinosoft.lis.db.LWPriorityDB;

/*
 * <p>ClassName: LWPrioritySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 优先级别
 */
public class LWPrioritySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWPrioritySchema.class);
	// @Field
	/** 优先级i */
	private String Priorityid;
	/** 优先级名称 */
	private String PriorityName;
	/** 优先级显色id */
	private String Colorid;
	/** 时效范围 */
	private double Range;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWPrioritySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Priorityid";

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
		LWPrioritySchema cloned = (LWPrioritySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPriorityid()
	{
		return Priorityid;
	}
	public void setPriorityid(String aPriorityid)
	{
		if(aPriorityid!=null && aPriorityid.length()>20)
			throw new IllegalArgumentException("优先级iPriorityid值"+aPriorityid+"的长度"+aPriorityid.length()+"大于最大值20");
		Priorityid = aPriorityid;
	}
	public String getPriorityName()
	{
		return PriorityName;
	}
	public void setPriorityName(String aPriorityName)
	{
		if(aPriorityName!=null && aPriorityName.length()>20)
			throw new IllegalArgumentException("优先级名称PriorityName值"+aPriorityName+"的长度"+aPriorityName.length()+"大于最大值20");
		PriorityName = aPriorityName;
	}
	public String getColorid()
	{
		return Colorid;
	}
	public void setColorid(String aColorid)
	{
		if(aColorid!=null && aColorid.length()>10)
			throw new IllegalArgumentException("优先级显色idColorid值"+aColorid+"的长度"+aColorid.length()+"大于最大值10");
		Colorid = aColorid;
	}
	public double getRange()
	{
		return Range;
	}
	public void setRange(double aRange)
	{
		Range = aRange;
	}
	public void setRange(String aRange)
	{
		if (aRange != null && !aRange.equals(""))
		{
			Double tDouble = new Double(aRange);
			double d = tDouble.doubleValue();
			Range = d;
		}
	}


	/**
	* 使用另外一个 LWPrioritySchema 对象给 Schema 赋值
	* @param: aLWPrioritySchema LWPrioritySchema
	**/
	public void setSchema(LWPrioritySchema aLWPrioritySchema)
	{
		this.Priorityid = aLWPrioritySchema.getPriorityid();
		this.PriorityName = aLWPrioritySchema.getPriorityName();
		this.Colorid = aLWPrioritySchema.getColorid();
		this.Range = aLWPrioritySchema.getRange();
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
			if( rs.getString("Priorityid") == null )
				this.Priorityid = null;
			else
				this.Priorityid = rs.getString("Priorityid").trim();

			if( rs.getString("PriorityName") == null )
				this.PriorityName = null;
			else
				this.PriorityName = rs.getString("PriorityName").trim();

			if( rs.getString("Colorid") == null )
				this.Colorid = null;
			else
				this.Colorid = rs.getString("Colorid").trim();

			this.Range = rs.getDouble("Range");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWPriority表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWPrioritySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWPrioritySchema getSchema()
	{
		LWPrioritySchema aLWPrioritySchema = new LWPrioritySchema();
		aLWPrioritySchema.setSchema(this);
		return aLWPrioritySchema;
	}

	public LWPriorityDB getDB()
	{
		LWPriorityDB aDBOper = new LWPriorityDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWPriority描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Priorityid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PriorityName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Colorid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Range));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWPriority>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Priorityid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PriorityName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Colorid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Range = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWPrioritySchema";
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
		if (FCode.equalsIgnoreCase("Priorityid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Priorityid));
		}
		if (FCode.equalsIgnoreCase("PriorityName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriorityName));
		}
		if (FCode.equalsIgnoreCase("Colorid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Colorid));
		}
		if (FCode.equalsIgnoreCase("Range"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Range));
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
				strFieldValue = StrTool.GBKToUnicode(Priorityid);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PriorityName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Colorid);
				break;
			case 3:
				strFieldValue = String.valueOf(Range);
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

		if (FCode.equalsIgnoreCase("Priorityid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Priorityid = FValue.trim();
			}
			else
				Priorityid = null;
		}
		if (FCode.equalsIgnoreCase("PriorityName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PriorityName = FValue.trim();
			}
			else
				PriorityName = null;
		}
		if (FCode.equalsIgnoreCase("Colorid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Colorid = FValue.trim();
			}
			else
				Colorid = null;
		}
		if (FCode.equalsIgnoreCase("Range"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Range = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWPrioritySchema other = (LWPrioritySchema)otherObject;
		return
			Priorityid.equals(other.getPriorityid())
			&& PriorityName.equals(other.getPriorityName())
			&& Colorid.equals(other.getColorid())
			&& Range == other.getRange();
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
		if( strFieldName.equals("Priorityid") ) {
			return 0;
		}
		if( strFieldName.equals("PriorityName") ) {
			return 1;
		}
		if( strFieldName.equals("Colorid") ) {
			return 2;
		}
		if( strFieldName.equals("Range") ) {
			return 3;
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
				strFieldName = "Priorityid";
				break;
			case 1:
				strFieldName = "PriorityName";
				break;
			case 2:
				strFieldName = "Colorid";
				break;
			case 3:
				strFieldName = "Range";
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
		if( strFieldName.equals("Priorityid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriorityName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Colorid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Range") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
