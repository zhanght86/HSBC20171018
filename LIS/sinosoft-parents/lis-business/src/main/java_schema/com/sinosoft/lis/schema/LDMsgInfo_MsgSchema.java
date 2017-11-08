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
import com.sinosoft.lis.db.LDMsgInfo_MsgDB;

/*
 * <p>ClassName: LDMsgInfo_MsgSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎多语言方案
 */
public class LDMsgInfo_MsgSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMsgInfo_MsgSchema.class);

	// @Field
	/** 显示语言 */
	private String Language;
	/** 关键字 */
	private String KeyID;
	/** 显示信息 */
	private String Msg;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMsgInfo_MsgSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "Language";
		pk[1] = "KeyID";

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
		LDMsgInfo_MsgSchema cloned = (LDMsgInfo_MsgSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getLanguage()
	{
		return Language;
	}
	public void setLanguage(String aLanguage)
	{
		Language = aLanguage;
	}
	public String getKeyID()
	{
		return KeyID;
	}
	public void setKeyID(String aKeyID)
	{
		KeyID = aKeyID;
	}
	public String getMsg()
	{
		return Msg;
	}
	public void setMsg(String aMsg)
	{
		Msg = aMsg;
	}

	/**
	* 使用另外一个 LDMsgInfo_MsgSchema 对象给 Schema 赋值
	* @param: aLDMsgInfo_MsgSchema LDMsgInfo_MsgSchema
	**/
	public void setSchema(LDMsgInfo_MsgSchema aLDMsgInfo_MsgSchema)
	{
		this.Language = aLDMsgInfo_MsgSchema.getLanguage();
		this.KeyID = aLDMsgInfo_MsgSchema.getKeyID();
		this.Msg = aLDMsgInfo_MsgSchema.getMsg();
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
			if( rs.getString("Language") == null )
				this.Language = null;
			else
				this.Language = rs.getString("Language").trim();

			if( rs.getString("KeyID") == null )
				this.KeyID = null;
			else
				this.KeyID = rs.getString("KeyID").trim();

			if( rs.getString("Msg") == null )
				this.Msg = null;
			else
				this.Msg = rs.getString("Msg").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMsgInfo_Msg表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMsgInfo_MsgSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMsgInfo_MsgSchema getSchema()
	{
		LDMsgInfo_MsgSchema aLDMsgInfo_MsgSchema = new LDMsgInfo_MsgSchema();
		aLDMsgInfo_MsgSchema.setSchema(this);
		return aLDMsgInfo_MsgSchema;
	}

	public LDMsgInfo_MsgDB getDB()
	{
		LDMsgInfo_MsgDB aDBOper = new LDMsgInfo_MsgDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMsgInfo_Msg描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Language)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeyID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Msg));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMsgInfo_Msg>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Language = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			KeyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Msg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMsgInfo_MsgSchema";
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
		if (FCode.equalsIgnoreCase("Language"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Language));
		}
		if (FCode.equalsIgnoreCase("KeyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyID));
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Msg));
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
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(KeyID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Msg);
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

		if (FCode.equalsIgnoreCase("Language"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Language = FValue.trim();
			}
			else
				Language = null;
		}
		if (FCode.equalsIgnoreCase("KeyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyID = FValue.trim();
			}
			else
				KeyID = null;
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Msg = FValue.trim();
			}
			else
				Msg = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMsgInfo_MsgSchema other = (LDMsgInfo_MsgSchema)otherObject;
		return
			Language.equals(other.getLanguage())
			&& KeyID.equals(other.getKeyID())
			&& Msg.equals(other.getMsg());
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
		if( strFieldName.equals("Language") ) {
			return 0;
		}
		if( strFieldName.equals("KeyID") ) {
			return 1;
		}
		if( strFieldName.equals("Msg") ) {
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
				strFieldName = "Language";
				break;
			case 1:
				strFieldName = "KeyID";
				break;
			case 2:
				strFieldName = "Msg";
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
		if( strFieldName.equals("Language") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Msg") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
