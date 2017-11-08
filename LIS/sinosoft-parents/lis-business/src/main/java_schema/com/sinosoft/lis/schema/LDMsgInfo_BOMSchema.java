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
import com.sinosoft.lis.db.LDMsgInfo_BOMDB;

/*
 * <p>ClassName: LDMsgInfo_BOMSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎多语言方案
 */
public class LDMsgInfo_BOMSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMsgInfo_BOMSchema.class);

	// @Field
	/** 显示语言 */
	private String Language;
	/** 显示类型 */
	private String MsgType;
	/** 关键字 */
	private String KeyID;
	/** 显示信息 */
	private String Msg;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMsgInfo_BOMSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "Language";
		pk[1] = "MsgType";
		pk[2] = "KeyID";

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
		LDMsgInfo_BOMSchema cloned = (LDMsgInfo_BOMSchema)super.clone();
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
	public String getMsgType()
	{
		return MsgType;
	}
	public void setMsgType(String aMsgType)
	{
		MsgType = aMsgType;
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
	* 使用另外一个 LDMsgInfo_BOMSchema 对象给 Schema 赋值
	* @param: aLDMsgInfo_BOMSchema LDMsgInfo_BOMSchema
	**/
	public void setSchema(LDMsgInfo_BOMSchema aLDMsgInfo_BOMSchema)
	{
		this.Language = aLDMsgInfo_BOMSchema.getLanguage();
		this.MsgType = aLDMsgInfo_BOMSchema.getMsgType();
		this.KeyID = aLDMsgInfo_BOMSchema.getKeyID();
		this.Msg = aLDMsgInfo_BOMSchema.getMsg();
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

			if( rs.getString("MsgType") == null )
				this.MsgType = null;
			else
				this.MsgType = rs.getString("MsgType").trim();

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
			logger.debug("数据库中的LDMsgInfo_BOM表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMsgInfo_BOMSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMsgInfo_BOMSchema getSchema()
	{
		LDMsgInfo_BOMSchema aLDMsgInfo_BOMSchema = new LDMsgInfo_BOMSchema();
		aLDMsgInfo_BOMSchema.setSchema(this);
		return aLDMsgInfo_BOMSchema;
	}

	public LDMsgInfo_BOMDB getDB()
	{
		LDMsgInfo_BOMDB aDBOper = new LDMsgInfo_BOMDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMsgInfo_BOM描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Language)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MsgType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeyID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Msg));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMsgInfo_BOM>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Language = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MsgType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			KeyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Msg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMsgInfo_BOMSchema";
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
		if (FCode.equalsIgnoreCase("MsgType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MsgType));
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
				strFieldValue = StrTool.GBKToUnicode(MsgType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(KeyID);
				break;
			case 3:
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
		if (FCode.equalsIgnoreCase("MsgType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MsgType = FValue.trim();
			}
			else
				MsgType = null;
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
		LDMsgInfo_BOMSchema other = (LDMsgInfo_BOMSchema)otherObject;
		return
			Language.equals(other.getLanguage())
			&& MsgType.equals(other.getMsgType())
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
		if( strFieldName.equals("MsgType") ) {
			return 1;
		}
		if( strFieldName.equals("KeyID") ) {
			return 2;
		}
		if( strFieldName.equals("Msg") ) {
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
				strFieldName = "Language";
				break;
			case 1:
				strFieldName = "MsgType";
				break;
			case 2:
				strFieldName = "KeyID";
				break;
			case 3:
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
		if( strFieldName.equals("MsgType") ) {
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
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
