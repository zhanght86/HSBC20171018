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
import com.sinosoft.lis.db.LATaskDescribeDB;

/*
 * <p>ClassName: LATaskDescribeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LATaskDescribeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LATaskDescribeSchema.class);

	// @Field
	/** 任务号 */
	private String TaskID;
	/** 任务描述 */
	private String TaskDescribe;
	/** 任务状态 */
	private String TaskState;
	/** 任务表名 */
	private String TableName;
	/** 任务计算类型 */
	private String CalType;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LATaskDescribeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TaskID";

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
		LATaskDescribeSchema cloned = (LATaskDescribeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTaskID()
	{
		return TaskID;
	}
	public void setTaskID(String aTaskID)
	{
		TaskID = aTaskID;
	}
	public String getTaskDescribe()
	{
		return TaskDescribe;
	}
	public void setTaskDescribe(String aTaskDescribe)
	{
		TaskDescribe = aTaskDescribe;
	}
	public String getTaskState()
	{
		return TaskState;
	}
	public void setTaskState(String aTaskState)
	{
		TaskState = aTaskState;
	}
	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String aTableName)
	{
		TableName = aTableName;
	}
	/**
	* A -- 指标描述中每次返回一个值<p>
	* <p>
	* B -- 指标描述中每次返回对应字段的全部值
	*/
	public String getCalType()
	{
		return CalType;
	}
	public void setCalType(String aCalType)
	{
		CalType = aCalType;
	}

	/**
	* 使用另外一个 LATaskDescribeSchema 对象给 Schema 赋值
	* @param: aLATaskDescribeSchema LATaskDescribeSchema
	**/
	public void setSchema(LATaskDescribeSchema aLATaskDescribeSchema)
	{
		this.TaskID = aLATaskDescribeSchema.getTaskID();
		this.TaskDescribe = aLATaskDescribeSchema.getTaskDescribe();
		this.TaskState = aLATaskDescribeSchema.getTaskState();
		this.TableName = aLATaskDescribeSchema.getTableName();
		this.CalType = aLATaskDescribeSchema.getCalType();
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
			if( rs.getString("TaskID") == null )
				this.TaskID = null;
			else
				this.TaskID = rs.getString("TaskID").trim();

			if( rs.getString("TaskDescribe") == null )
				this.TaskDescribe = null;
			else
				this.TaskDescribe = rs.getString("TaskDescribe").trim();

			if( rs.getString("TaskState") == null )
				this.TaskState = null;
			else
				this.TaskState = rs.getString("TaskState").trim();

			if( rs.getString("TableName") == null )
				this.TableName = null;
			else
				this.TableName = rs.getString("TableName").trim();

			if( rs.getString("CalType") == null )
				this.CalType = null;
			else
				this.CalType = rs.getString("CalType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LATaskDescribe表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATaskDescribeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LATaskDescribeSchema getSchema()
	{
		LATaskDescribeSchema aLATaskDescribeSchema = new LATaskDescribeSchema();
		aLATaskDescribeSchema.setSchema(this);
		return aLATaskDescribeSchema;
	}

	public LATaskDescribeDB getDB()
	{
		LATaskDescribeDB aDBOper = new LATaskDescribeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATaskDescribe描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TaskID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskDescribe)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATaskDescribe>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TaskID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TaskDescribe = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TaskState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATaskDescribeSchema";
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
		if (FCode.equalsIgnoreCase("TaskID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskID));
		}
		if (FCode.equalsIgnoreCase("TaskDescribe"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskDescribe));
		}
		if (FCode.equalsIgnoreCase("TaskState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskState));
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableName));
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalType));
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
				strFieldValue = StrTool.GBKToUnicode(TaskID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TaskDescribe);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TaskState);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TableName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalType);
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

		if (FCode.equalsIgnoreCase("TaskID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskID = FValue.trim();
			}
			else
				TaskID = null;
		}
		if (FCode.equalsIgnoreCase("TaskDescribe"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskDescribe = FValue.trim();
			}
			else
				TaskDescribe = null;
		}
		if (FCode.equalsIgnoreCase("TaskState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskState = FValue.trim();
			}
			else
				TaskState = null;
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableName = FValue.trim();
			}
			else
				TableName = null;
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalType = FValue.trim();
			}
			else
				CalType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LATaskDescribeSchema other = (LATaskDescribeSchema)otherObject;
		return
			TaskID.equals(other.getTaskID())
			&& TaskDescribe.equals(other.getTaskDescribe())
			&& TaskState.equals(other.getTaskState())
			&& TableName.equals(other.getTableName())
			&& CalType.equals(other.getCalType());
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
		if( strFieldName.equals("TaskID") ) {
			return 0;
		}
		if( strFieldName.equals("TaskDescribe") ) {
			return 1;
		}
		if( strFieldName.equals("TaskState") ) {
			return 2;
		}
		if( strFieldName.equals("TableName") ) {
			return 3;
		}
		if( strFieldName.equals("CalType") ) {
			return 4;
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
				strFieldName = "TaskID";
				break;
			case 1:
				strFieldName = "TaskDescribe";
				break;
			case 2:
				strFieldName = "TaskState";
				break;
			case 3:
				strFieldName = "TableName";
				break;
			case 4:
				strFieldName = "CalType";
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
		if( strFieldName.equals("TaskID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskDescribe") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
