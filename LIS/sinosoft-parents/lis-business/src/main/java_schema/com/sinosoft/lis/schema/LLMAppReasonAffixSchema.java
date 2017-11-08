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
import com.sinosoft.lis.db.LLMAppReasonAffixDB;

/*
 * <p>ClassName: LLMAppReasonAffixSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLMAppReasonAffixSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLMAppReasonAffixSchema.class);

	// @Field
	/** 理赔类型代码 */
	private String ReasonCode;
	/** 申请材料代码 */
	private String AffixCode;
	/** 理赔类型名称 */
	private String ReasonName;
	/** 申请材料名称 */
	private String AffixName;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLMAppReasonAffixSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ReasonCode";
		pk[1] = "AffixCode";

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
		LLMAppReasonAffixSchema cloned = (LLMAppReasonAffixSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getReasonCode()
	{
		return ReasonCode;
	}
	public void setReasonCode(String aReasonCode)
	{
		ReasonCode = aReasonCode;
	}
	public String getAffixCode()
	{
		return AffixCode;
	}
	public void setAffixCode(String aAffixCode)
	{
		AffixCode = aAffixCode;
	}
	public String getReasonName()
	{
		return ReasonName;
	}
	public void setReasonName(String aReasonName)
	{
		ReasonName = aReasonName;
	}
	public String getAffixName()
	{
		return AffixName;
	}
	public void setAffixName(String aAffixName)
	{
		AffixName = aAffixName;
	}

	/**
	* 使用另外一个 LLMAppReasonAffixSchema 对象给 Schema 赋值
	* @param: aLLMAppReasonAffixSchema LLMAppReasonAffixSchema
	**/
	public void setSchema(LLMAppReasonAffixSchema aLLMAppReasonAffixSchema)
	{
		this.ReasonCode = aLLMAppReasonAffixSchema.getReasonCode();
		this.AffixCode = aLLMAppReasonAffixSchema.getAffixCode();
		this.ReasonName = aLLMAppReasonAffixSchema.getReasonName();
		this.AffixName = aLLMAppReasonAffixSchema.getAffixName();
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
			if( rs.getString("ReasonCode") == null )
				this.ReasonCode = null;
			else
				this.ReasonCode = rs.getString("ReasonCode").trim();

			if( rs.getString("AffixCode") == null )
				this.AffixCode = null;
			else
				this.AffixCode = rs.getString("AffixCode").trim();

			if( rs.getString("ReasonName") == null )
				this.ReasonName = null;
			else
				this.ReasonName = rs.getString("ReasonName").trim();

			if( rs.getString("AffixName") == null )
				this.AffixName = null;
			else
				this.AffixName = rs.getString("AffixName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLMAppReasonAffix表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMAppReasonAffixSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLMAppReasonAffixSchema getSchema()
	{
		LLMAppReasonAffixSchema aLLMAppReasonAffixSchema = new LLMAppReasonAffixSchema();
		aLLMAppReasonAffixSchema.setSchema(this);
		return aLLMAppReasonAffixSchema;
	}

	public LLMAppReasonAffixDB getDB()
	{
		LLMAppReasonAffixDB aDBOper = new LLMAppReasonAffixDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMAppReasonAffix描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMAppReasonAffix>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AffixCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ReasonName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AffixName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMAppReasonAffixSchema";
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
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonCode));
		}
		if (FCode.equalsIgnoreCase("AffixCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixCode));
		}
		if (FCode.equalsIgnoreCase("ReasonName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonName));
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixName));
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
				strFieldValue = StrTool.GBKToUnicode(ReasonCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AffixCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ReasonName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AffixName);
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

		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonCode = FValue.trim();
			}
			else
				ReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("AffixCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixCode = FValue.trim();
			}
			else
				AffixCode = null;
		}
		if (FCode.equalsIgnoreCase("ReasonName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonName = FValue.trim();
			}
			else
				ReasonName = null;
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixName = FValue.trim();
			}
			else
				AffixName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLMAppReasonAffixSchema other = (LLMAppReasonAffixSchema)otherObject;
		return
			ReasonCode.equals(other.getReasonCode())
			&& AffixCode.equals(other.getAffixCode())
			&& ReasonName.equals(other.getReasonName())
			&& AffixName.equals(other.getAffixName());
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
		if( strFieldName.equals("ReasonCode") ) {
			return 0;
		}
		if( strFieldName.equals("AffixCode") ) {
			return 1;
		}
		if( strFieldName.equals("ReasonName") ) {
			return 2;
		}
		if( strFieldName.equals("AffixName") ) {
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
				strFieldName = "ReasonCode";
				break;
			case 1:
				strFieldName = "AffixCode";
				break;
			case 2:
				strFieldName = "ReasonName";
				break;
			case 3:
				strFieldName = "AffixName";
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
		if( strFieldName.equals("ReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixName") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
