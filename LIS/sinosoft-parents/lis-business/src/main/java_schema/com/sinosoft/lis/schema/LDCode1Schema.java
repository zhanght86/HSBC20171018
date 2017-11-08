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
import com.sinosoft.lis.db.LDCode1DB;

/*
 * <p>ClassName: LDCode1Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDCode1Schema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCode1Schema.class);

	// @Field
	/** 编码类型 */
	private String CodeType;
	/** 编码 */
	private String Code;
	/** 子编码 */
	private String Code1;
	/** 编码名称 */
	private String CodeName;
	/** 编码别名 */
	private String CodeAlias;
	/** 机构代码 */
	private String ComCode;
	/** 其它标志 */
	private String OtherSign;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCode1Schema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "CodeType";
		pk[1] = "Code";
		pk[2] = "Code1";

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
		LDCode1Schema cloned = (LDCode1Schema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	public String getCode1()
	{
		return Code1;
	}
	public void setCode1(String aCode1)
	{
		Code1 = aCode1;
	}
	public String getCodeName()
	{
		return CodeName;
	}
	public void setCodeName(String aCodeName)
	{
		CodeName = aCodeName;
	}
	public String getCodeAlias()
	{
		return CodeAlias;
	}
	public void setCodeAlias(String aCodeAlias)
	{
		CodeAlias = aCodeAlias;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getOtherSign()
	{
		return OtherSign;
	}
	public void setOtherSign(String aOtherSign)
	{
		OtherSign = aOtherSign;
	}

	/**
	* 使用另外一个 LDCode1Schema 对象给 Schema 赋值
	* @param: aLDCode1Schema LDCode1Schema
	**/
	public void setSchema(LDCode1Schema aLDCode1Schema)
	{
		this.CodeType = aLDCode1Schema.getCodeType();
		this.Code = aLDCode1Schema.getCode();
		this.Code1 = aLDCode1Schema.getCode1();
		this.CodeName = aLDCode1Schema.getCodeName();
		this.CodeAlias = aLDCode1Schema.getCodeAlias();
		this.ComCode = aLDCode1Schema.getComCode();
		this.OtherSign = aLDCode1Schema.getOtherSign();
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
			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("Code1") == null )
				this.Code1 = null;
			else
				this.Code1 = rs.getString("Code1").trim();

			if( rs.getString("CodeName") == null )
				this.CodeName = null;
			else
				this.CodeName = rs.getString("CodeName").trim();

			if( rs.getString("CodeAlias") == null )
				this.CodeAlias = null;
			else
				this.CodeAlias = rs.getString("CodeAlias").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("OtherSign") == null )
				this.OtherSign = null;
			else
				this.OtherSign = rs.getString("OtherSign").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCode1表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCode1Schema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCode1Schema getSchema()
	{
		LDCode1Schema aLDCode1Schema = new LDCode1Schema();
		aLDCode1Schema.setSchema(this);
		return aLDCode1Schema;
	}

	public LDCode1DB getDB()
	{
		LDCode1DB aDBOper = new LDCode1DB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCode1描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeAlias)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherSign));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCode1>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Code1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CodeAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OtherSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCode1Schema";
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
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("Code1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code1));
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName));
		}
		if (FCode.equalsIgnoreCase("CodeAlias"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeAlias));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("OtherSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherSign));
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
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Code1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CodeAlias);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OtherSign);
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

		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("Code1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code1 = FValue.trim();
			}
			else
				Code1 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName = FValue.trim();
			}
			else
				CodeName = null;
		}
		if (FCode.equalsIgnoreCase("CodeAlias"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeAlias = FValue.trim();
			}
			else
				CodeAlias = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherSign = FValue.trim();
			}
			else
				OtherSign = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCode1Schema other = (LDCode1Schema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& Code.equals(other.getCode())
			&& Code1.equals(other.getCode1())
			&& CodeName.equals(other.getCodeName())
			&& CodeAlias.equals(other.getCodeAlias())
			&& ComCode.equals(other.getComCode())
			&& OtherSign.equals(other.getOtherSign());
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
		if( strFieldName.equals("CodeType") ) {
			return 0;
		}
		if( strFieldName.equals("Code") ) {
			return 1;
		}
		if( strFieldName.equals("Code1") ) {
			return 2;
		}
		if( strFieldName.equals("CodeName") ) {
			return 3;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return 4;
		}
		if( strFieldName.equals("ComCode") ) {
			return 5;
		}
		if( strFieldName.equals("OtherSign") ) {
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
				strFieldName = "CodeType";
				break;
			case 1:
				strFieldName = "Code";
				break;
			case 2:
				strFieldName = "Code1";
				break;
			case 3:
				strFieldName = "CodeName";
				break;
			case 4:
				strFieldName = "CodeAlias";
				break;
			case 5:
				strFieldName = "ComCode";
				break;
			case 6:
				strFieldName = "OtherSign";
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
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherSign") ) {
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
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
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
