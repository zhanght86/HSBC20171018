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
import com.sinosoft.lis.db.LDCodeRelaDB;

/*
 * <p>ClassName: LDCodeRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LDCodeRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCodeRelaSchema.class);

	// @Field
	/** 控制编码 */
	private String RelaType;
	/** 代码1 */
	private String Code1;
	/** 代码2 */
	private String Code2;
	/** 代码3 */
	private String Code3;
	/** 控制说明 */
	private String Description;
	/** 其它标志 */
	private String OtherSign;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCodeRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RelaType";
		pk[1] = "Code1";
		pk[2] = "Code2";
		pk[3] = "Code3";

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
		LDCodeRelaSchema cloned = (LDCodeRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRelaType()
	{
		return RelaType;
	}
	public void setRelaType(String aRelaType)
	{
		RelaType = aRelaType;
	}
	public String getCode1()
	{
		return Code1;
	}
	public void setCode1(String aCode1)
	{
		Code1 = aCode1;
	}
	public String getCode2()
	{
		return Code2;
	}
	public void setCode2(String aCode2)
	{
		Code2 = aCode2;
	}
	public String getCode3()
	{
		return Code3;
	}
	public void setCode3(String aCode3)
	{
		Code3 = aCode3;
	}
	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String aDescription)
	{
		Description = aDescription;
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
	* 使用另外一个 LDCodeRelaSchema 对象给 Schema 赋值
	* @param: aLDCodeRelaSchema LDCodeRelaSchema
	**/
	public void setSchema(LDCodeRelaSchema aLDCodeRelaSchema)
	{
		this.RelaType = aLDCodeRelaSchema.getRelaType();
		this.Code1 = aLDCodeRelaSchema.getCode1();
		this.Code2 = aLDCodeRelaSchema.getCode2();
		this.Code3 = aLDCodeRelaSchema.getCode3();
		this.Description = aLDCodeRelaSchema.getDescription();
		this.OtherSign = aLDCodeRelaSchema.getOtherSign();
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
			if( rs.getString("RelaType") == null )
				this.RelaType = null;
			else
				this.RelaType = rs.getString("RelaType").trim();

			if( rs.getString("Code1") == null )
				this.Code1 = null;
			else
				this.Code1 = rs.getString("Code1").trim();

			if( rs.getString("Code2") == null )
				this.Code2 = null;
			else
				this.Code2 = rs.getString("Code2").trim();

			if( rs.getString("Code3") == null )
				this.Code3 = null;
			else
				this.Code3 = rs.getString("Code3").trim();

			if( rs.getString("Description") == null )
				this.Description = null;
			else
				this.Description = rs.getString("Description").trim();

			if( rs.getString("OtherSign") == null )
				this.OtherSign = null;
			else
				this.OtherSign = rs.getString("OtherSign").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCodeRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCodeRelaSchema getSchema()
	{
		LDCodeRelaSchema aLDCodeRelaSchema = new LDCodeRelaSchema();
		aLDCodeRelaSchema.setSchema(this);
		return aLDCodeRelaSchema;
	}

	public LDCodeRelaDB getDB()
	{
		LDCodeRelaDB aDBOper = new LDCodeRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCodeRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherSign));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCodeRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Code1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Code2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Code3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Description = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeRelaSchema";
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
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaType));
		}
		if (FCode.equalsIgnoreCase("Code1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code1));
		}
		if (FCode.equalsIgnoreCase("Code2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code2));
		}
		if (FCode.equalsIgnoreCase("Code3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code3));
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Description));
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
				strFieldValue = StrTool.GBKToUnicode(RelaType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Code1);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Code2);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Code3);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Description);
				break;
			case 5:
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

		if (FCode.equalsIgnoreCase("RelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaType = FValue.trim();
			}
			else
				RelaType = null;
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
		if (FCode.equalsIgnoreCase("Code2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code2 = FValue.trim();
			}
			else
				Code2 = null;
		}
		if (FCode.equalsIgnoreCase("Code3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code3 = FValue.trim();
			}
			else
				Code3 = null;
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Description = FValue.trim();
			}
			else
				Description = null;
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
		LDCodeRelaSchema other = (LDCodeRelaSchema)otherObject;
		return
			RelaType.equals(other.getRelaType())
			&& Code1.equals(other.getCode1())
			&& Code2.equals(other.getCode2())
			&& Code3.equals(other.getCode3())
			&& Description.equals(other.getDescription())
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
		if( strFieldName.equals("RelaType") ) {
			return 0;
		}
		if( strFieldName.equals("Code1") ) {
			return 1;
		}
		if( strFieldName.equals("Code2") ) {
			return 2;
		}
		if( strFieldName.equals("Code3") ) {
			return 3;
		}
		if( strFieldName.equals("Description") ) {
			return 4;
		}
		if( strFieldName.equals("OtherSign") ) {
			return 5;
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
				strFieldName = "RelaType";
				break;
			case 1:
				strFieldName = "Code1";
				break;
			case 2:
				strFieldName = "Code2";
				break;
			case 3:
				strFieldName = "Code3";
				break;
			case 4:
				strFieldName = "Description";
				break;
			case 5:
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
		if( strFieldName.equals("RelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Description") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
