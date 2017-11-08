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
import com.sinosoft.lis.db.LCalculatorAgeRangeDB;

/*
 * <p>ClassName: LCalculatorAgeRangeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class LCalculatorAgeRangeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCalculatorAgeRangeSchema.class);
	// @Field
	/** 累加器编号 */
	private String CalculatorCode;
	/** 起始年龄 */
	private int StartAge;
	/** 结束年龄 */
	private int EndAge;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCalculatorAgeRangeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CalculatorCode";

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
		LCalculatorAgeRangeSchema cloned = (LCalculatorAgeRangeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCalculatorCode()
	{
		return CalculatorCode;
	}
	public void setCalculatorCode(String aCalculatorCode)
	{
		if(aCalculatorCode!=null && aCalculatorCode.length()>20)
			throw new IllegalArgumentException("累加器编号CalculatorCode值"+aCalculatorCode+"的长度"+aCalculatorCode.length()+"大于最大值20");
		CalculatorCode = aCalculatorCode;
	}
	public int getStartAge()
	{
		return StartAge;
	}
	public void setStartAge(int aStartAge)
	{
		StartAge = aStartAge;
	}
	public void setStartAge(String aStartAge)
	{
		if (aStartAge != null && !aStartAge.equals(""))
		{
			Integer tInteger = new Integer(aStartAge);
			int i = tInteger.intValue();
			StartAge = i;
		}
	}

	public int getEndAge()
	{
		return EndAge;
	}
	public void setEndAge(int aEndAge)
	{
		EndAge = aEndAge;
	}
	public void setEndAge(String aEndAge)
	{
		if (aEndAge != null && !aEndAge.equals(""))
		{
			Integer tInteger = new Integer(aEndAge);
			int i = tInteger.intValue();
			EndAge = i;
		}
	}


	/**
	* 使用另外一个 LCalculatorAgeRangeSchema 对象给 Schema 赋值
	* @param: aLCalculatorAgeRangeSchema LCalculatorAgeRangeSchema
	**/
	public void setSchema(LCalculatorAgeRangeSchema aLCalculatorAgeRangeSchema)
	{
		this.CalculatorCode = aLCalculatorAgeRangeSchema.getCalculatorCode();
		this.StartAge = aLCalculatorAgeRangeSchema.getStartAge();
		this.EndAge = aLCalculatorAgeRangeSchema.getEndAge();
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
			if( rs.getString("CalculatorCode") == null )
				this.CalculatorCode = null;
			else
				this.CalculatorCode = rs.getString("CalculatorCode").trim();

			this.StartAge = rs.getInt("StartAge");
			this.EndAge = rs.getInt("EndAge");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCalculatorAgeRange表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCalculatorAgeRangeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCalculatorAgeRangeSchema getSchema()
	{
		LCalculatorAgeRangeSchema aLCalculatorAgeRangeSchema = new LCalculatorAgeRangeSchema();
		aLCalculatorAgeRangeSchema.setSchema(this);
		return aLCalculatorAgeRangeSchema;
	}

	public LCalculatorAgeRangeDB getDB()
	{
		LCalculatorAgeRangeDB aDBOper = new LCalculatorAgeRangeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCalculatorAgeRange描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalculatorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndAge));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCalculatorAgeRange>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalculatorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			StartAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			EndAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCalculatorAgeRangeSchema";
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
		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalculatorCode));
		}
		if (FCode.equalsIgnoreCase("StartAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartAge));
		}
		if (FCode.equalsIgnoreCase("EndAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndAge));
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
				strFieldValue = StrTool.GBKToUnicode(CalculatorCode);
				break;
			case 1:
				strFieldValue = String.valueOf(StartAge);
				break;
			case 2:
				strFieldValue = String.valueOf(EndAge);
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

		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalculatorCode = FValue.trim();
			}
			else
				CalculatorCode = null;
		}
		if (FCode.equalsIgnoreCase("StartAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StartAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("EndAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndAge = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCalculatorAgeRangeSchema other = (LCalculatorAgeRangeSchema)otherObject;
		return
			CalculatorCode.equals(other.getCalculatorCode())
			&& StartAge == other.getStartAge()
			&& EndAge == other.getEndAge();
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
		if( strFieldName.equals("CalculatorCode") ) {
			return 0;
		}
		if( strFieldName.equals("StartAge") ) {
			return 1;
		}
		if( strFieldName.equals("EndAge") ) {
			return 2;
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
				strFieldName = "CalculatorCode";
				break;
			case 1:
				strFieldName = "StartAge";
				break;
			case 2:
				strFieldName = "EndAge";
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
		if( strFieldName.equals("CalculatorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndAge") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
