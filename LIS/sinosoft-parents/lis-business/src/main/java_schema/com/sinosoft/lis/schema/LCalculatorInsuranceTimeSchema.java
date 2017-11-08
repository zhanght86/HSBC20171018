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
import com.sinosoft.lis.db.LCalculatorInsuranceTimeDB;

/*
 * <p>ClassName: LCalculatorInsuranceTimeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class LCalculatorInsuranceTimeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCalculatorInsuranceTimeSchema.class);
	// @Field
	/** 累加器编号 */
	private String CalculatorCode;
	/** 生效时长 */
	private int ValidPeriod;
	/** 生效时长单位 */
	private String ValidPeriodUnit;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCalculatorInsuranceTimeSchema()
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
		LCalculatorInsuranceTimeSchema cloned = (LCalculatorInsuranceTimeSchema)super.clone();
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
	public int getValidPeriod()
	{
		return ValidPeriod;
	}
	public void setValidPeriod(int aValidPeriod)
	{
		ValidPeriod = aValidPeriod;
	}
	public void setValidPeriod(String aValidPeriod)
	{
		if (aValidPeriod != null && !aValidPeriod.equals(""))
		{
			Integer tInteger = new Integer(aValidPeriod);
			int i = tInteger.intValue();
			ValidPeriod = i;
		}
	}

	public String getValidPeriodUnit()
	{
		return ValidPeriodUnit;
	}
	public void setValidPeriodUnit(String aValidPeriodUnit)
	{
		if(aValidPeriodUnit!=null && aValidPeriodUnit.length()>1)
			throw new IllegalArgumentException("生效时长单位ValidPeriodUnit值"+aValidPeriodUnit+"的长度"+aValidPeriodUnit.length()+"大于最大值1");
		ValidPeriodUnit = aValidPeriodUnit;
	}

	/**
	* 使用另外一个 LCalculatorInsuranceTimeSchema 对象给 Schema 赋值
	* @param: aLCalculatorInsuranceTimeSchema LCalculatorInsuranceTimeSchema
	**/
	public void setSchema(LCalculatorInsuranceTimeSchema aLCalculatorInsuranceTimeSchema)
	{
		this.CalculatorCode = aLCalculatorInsuranceTimeSchema.getCalculatorCode();
		this.ValidPeriod = aLCalculatorInsuranceTimeSchema.getValidPeriod();
		this.ValidPeriodUnit = aLCalculatorInsuranceTimeSchema.getValidPeriodUnit();
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

			this.ValidPeriod = rs.getInt("ValidPeriod");
			if( rs.getString("ValidPeriodUnit") == null )
				this.ValidPeriodUnit = null;
			else
				this.ValidPeriodUnit = rs.getString("ValidPeriodUnit").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCalculatorInsuranceTime表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCalculatorInsuranceTimeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCalculatorInsuranceTimeSchema getSchema()
	{
		LCalculatorInsuranceTimeSchema aLCalculatorInsuranceTimeSchema = new LCalculatorInsuranceTimeSchema();
		aLCalculatorInsuranceTimeSchema.setSchema(this);
		return aLCalculatorInsuranceTimeSchema;
	}

	public LCalculatorInsuranceTimeDB getDB()
	{
		LCalculatorInsuranceTimeDB aDBOper = new LCalculatorInsuranceTimeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCalculatorInsuranceTime描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalculatorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ValidPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidPeriodUnit));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCalculatorInsuranceTime>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalculatorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ValidPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ValidPeriodUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCalculatorInsuranceTimeSchema";
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
		if (FCode.equalsIgnoreCase("ValidPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidPeriod));
		}
		if (FCode.equalsIgnoreCase("ValidPeriodUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidPeriodUnit));
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
				strFieldValue = String.valueOf(ValidPeriod);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ValidPeriodUnit);
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
		if (FCode.equalsIgnoreCase("ValidPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ValidPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("ValidPeriodUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidPeriodUnit = FValue.trim();
			}
			else
				ValidPeriodUnit = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCalculatorInsuranceTimeSchema other = (LCalculatorInsuranceTimeSchema)otherObject;
		return
			CalculatorCode.equals(other.getCalculatorCode())
			&& ValidPeriod == other.getValidPeriod()
			&& ValidPeriodUnit.equals(other.getValidPeriodUnit());
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
		if( strFieldName.equals("ValidPeriod") ) {
			return 1;
		}
		if( strFieldName.equals("ValidPeriodUnit") ) {
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
				strFieldName = "ValidPeriod";
				break;
			case 2:
				strFieldName = "ValidPeriodUnit";
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
		if( strFieldName.equals("ValidPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ValidPeriodUnit") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
