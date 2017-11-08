

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIUnikeyDB;

/*
 * <p>ClassName: RIUnikeySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIUnikeySchema implements Schema, Cloneable
{
	// @Field
	/** 索引 */
	private String Unikey;
	/** 类型 */
	private String KeyType;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIUnikeySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "Unikey";
		pk[1] = "KeyType";

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
		RIUnikeySchema cloned = (RIUnikeySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUnikey()
	{
		return Unikey;
	}
	public void setUnikey(String aUnikey)
	{
		Unikey = aUnikey;
	}
	public String getKeyType()
	{
		return KeyType;
	}
	public void setKeyType(String aKeyType)
	{
		KeyType = aKeyType;
	}

	/**
	* 使用另外一个 RIUnikeySchema 对象给 Schema 赋值
	* @param: aRIUnikeySchema RIUnikeySchema
	**/
	public void setSchema(RIUnikeySchema aRIUnikeySchema)
	{
		this.Unikey = aRIUnikeySchema.getUnikey();
		this.KeyType = aRIUnikeySchema.getKeyType();
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
			if( rs.getString("Unikey") == null )
				this.Unikey = null;
			else
				this.Unikey = rs.getString("Unikey").trim();

			if( rs.getString("KeyType") == null )
				this.KeyType = null;
			else
				this.KeyType = rs.getString("KeyType").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIUnikey表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIUnikeySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIUnikeySchema getSchema()
	{
		RIUnikeySchema aRIUnikeySchema = new RIUnikeySchema();
		aRIUnikeySchema.setSchema(this);
		return aRIUnikeySchema;
	}

	public RIUnikeyDB getDB()
	{
		RIUnikeyDB aDBOper = new RIUnikeyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIUnikey描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Unikey)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeyType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIUnikey>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Unikey = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			KeyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIUnikeySchema";
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
		if (FCode.equalsIgnoreCase("Unikey"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Unikey));
		}
		if (FCode.equalsIgnoreCase("KeyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyType));
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
				strFieldValue = StrTool.GBKToUnicode(Unikey);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(KeyType);
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

		if (FCode.equalsIgnoreCase("Unikey"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Unikey = FValue.trim();
			}
			else
				Unikey = null;
		}
		if (FCode.equalsIgnoreCase("KeyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyType = FValue.trim();
			}
			else
				KeyType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIUnikeySchema other = (RIUnikeySchema)otherObject;
		return
			Unikey.equals(other.getUnikey())
			&& KeyType.equals(other.getKeyType());
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
		if( strFieldName.equals("Unikey") ) {
			return 0;
		}
		if( strFieldName.equals("KeyType") ) {
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
				strFieldName = "Unikey";
				break;
			case 1:
				strFieldName = "KeyType";
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
		if( strFieldName.equals("Unikey") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeyType") ) {
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

