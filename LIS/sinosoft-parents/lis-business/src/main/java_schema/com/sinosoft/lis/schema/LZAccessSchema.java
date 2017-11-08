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
import com.sinosoft.lis.db.LZAccessDB;

/*
 * <p>ClassName: LZAccessSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZAccessSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZAccessSchema.class);

	// @Field
	/** 发放机构 */
	private String SendOutCom;
	/** 接收机构 */
	private String ReceiveCom;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZAccessSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SendOutCom";
		pk[1] = "ReceiveCom";

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
		LZAccessSchema cloned = (LZAccessSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSendOutCom()
	{
		return SendOutCom;
	}
	public void setSendOutCom(String aSendOutCom)
	{
		SendOutCom = aSendOutCom;
	}
	public String getReceiveCom()
	{
		return ReceiveCom;
	}
	public void setReceiveCom(String aReceiveCom)
	{
		ReceiveCom = aReceiveCom;
	}

	/**
	* 使用另外一个 LZAccessSchema 对象给 Schema 赋值
	* @param: aLZAccessSchema LZAccessSchema
	**/
	public void setSchema(LZAccessSchema aLZAccessSchema)
	{
		this.SendOutCom = aLZAccessSchema.getSendOutCom();
		this.ReceiveCom = aLZAccessSchema.getReceiveCom();
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
			if( rs.getString("SendOutCom") == null )
				this.SendOutCom = null;
			else
				this.SendOutCom = rs.getString("SendOutCom").trim();

			if( rs.getString("ReceiveCom") == null )
				this.ReceiveCom = null;
			else
				this.ReceiveCom = rs.getString("ReceiveCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZAccess表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZAccessSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZAccessSchema getSchema()
	{
		LZAccessSchema aLZAccessSchema = new LZAccessSchema();
		aLZAccessSchema.setSchema(this);
		return aLZAccessSchema;
	}

	public LZAccessDB getDB()
	{
		LZAccessDB aDBOper = new LZAccessDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZAccess描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SendOutCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZAccess>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SendOutCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ReceiveCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZAccessSchema";
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
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendOutCom));
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveCom));
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
				strFieldValue = StrTool.GBKToUnicode(SendOutCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ReceiveCom);
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

		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendOutCom = FValue.trim();
			}
			else
				SendOutCom = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveCom = FValue.trim();
			}
			else
				ReceiveCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZAccessSchema other = (LZAccessSchema)otherObject;
		return
			SendOutCom.equals(other.getSendOutCom())
			&& ReceiveCom.equals(other.getReceiveCom());
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
		if( strFieldName.equals("SendOutCom") ) {
			return 0;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return 1;
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
				strFieldName = "SendOutCom";
				break;
			case 1:
				strFieldName = "ReceiveCom";
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
		if( strFieldName.equals("SendOutCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveCom") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
