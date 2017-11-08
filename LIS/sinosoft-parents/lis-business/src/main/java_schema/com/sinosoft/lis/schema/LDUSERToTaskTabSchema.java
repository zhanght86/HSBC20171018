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
import com.sinosoft.lis.db.LDUSERToTaskTabDB;

/*
 * <p>ClassName: LDUSERToTaskTabSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 用户和任务 服务 标签页配置 
 */
public class LDUSERToTaskTabSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUSERToTaskTabSchema.class);

	// @Field
	/** 标签页id */
	private String TaskTabID;
	/** 用户id */
	private String UserID;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUSERToTaskTabSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TaskTabID";
		pk[1] = "UserID";

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
		LDUSERToTaskTabSchema cloned = (LDUSERToTaskTabSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTaskTabID()
	{
		return TaskTabID;
	}
	public void setTaskTabID(String aTaskTabID)
	{
		if(aTaskTabID!=null && aTaskTabID.length()>20)
			throw new IllegalArgumentException("标签页idTaskTabID值"+aTaskTabID+"的长度"+aTaskTabID.length()+"大于最大值20");
		TaskTabID = aTaskTabID;
	}
	public String getUserID()
	{
		return UserID;
	}
	public void setUserID(String aUserID)
	{
		if(aUserID!=null && aUserID.length()>20)
			throw new IllegalArgumentException("用户idUserID值"+aUserID+"的长度"+aUserID.length()+"大于最大值20");
		UserID = aUserID;
	}

	/**
	* 使用另外一个 LDUSERToTaskTabSchema 对象给 Schema 赋值
	* @param: aLDUSERToTaskTabSchema LDUSERToTaskTabSchema
	**/
	public void setSchema(LDUSERToTaskTabSchema aLDUSERToTaskTabSchema)
	{
		this.TaskTabID = aLDUSERToTaskTabSchema.getTaskTabID();
		this.UserID = aLDUSERToTaskTabSchema.getUserID();
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
			if( rs.getString("TaskTabID") == null )
				this.TaskTabID = null;
			else
				this.TaskTabID = rs.getString("TaskTabID").trim();

			if( rs.getString("UserID") == null )
				this.UserID = null;
			else
				this.UserID = rs.getString("UserID").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUSERToTaskTab表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUSERToTaskTabSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUSERToTaskTabSchema getSchema()
	{
		LDUSERToTaskTabSchema aLDUSERToTaskTabSchema = new LDUSERToTaskTabSchema();
		aLDUSERToTaskTabSchema.setSchema(this);
		return aLDUSERToTaskTabSchema;
	}

	public LDUSERToTaskTabDB getDB()
	{
		LDUSERToTaskTabDB aDBOper = new LDUSERToTaskTabDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUSERToTaskTab描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TaskTabID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserID));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUSERToTaskTab>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TaskTabID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UserID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUSERToTaskTabSchema";
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
		if (FCode.equalsIgnoreCase("TaskTabID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskTabID));
		}
		if (FCode.equalsIgnoreCase("UserID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserID));
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
				strFieldValue = StrTool.GBKToUnicode(TaskTabID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UserID);
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

		if (FCode.equalsIgnoreCase("TaskTabID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskTabID = FValue.trim();
			}
			else
				TaskTabID = null;
		}
		if (FCode.equalsIgnoreCase("UserID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserID = FValue.trim();
			}
			else
				UserID = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUSERToTaskTabSchema other = (LDUSERToTaskTabSchema)otherObject;
		return
			TaskTabID.equals(other.getTaskTabID())
			&& UserID.equals(other.getUserID());
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
		if( strFieldName.equals("TaskTabID") ) {
			return 0;
		}
		if( strFieldName.equals("UserID") ) {
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
				strFieldName = "TaskTabID";
				break;
			case 1:
				strFieldName = "UserID";
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
		if( strFieldName.equals("TaskTabID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserID") ) {
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
