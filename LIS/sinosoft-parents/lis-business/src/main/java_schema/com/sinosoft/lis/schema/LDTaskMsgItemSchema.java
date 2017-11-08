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
import com.sinosoft.lis.db.LDTaskMsgItemDB;

/*
 * <p>ClassName: LDTaskMsgItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 任务提醒内容映射表
 */
public class LDTaskMsgItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDTaskMsgItemSchema.class);

	// @Field
	/** 任务代码 */
	private String TaskCode;
	/** 任务计划代码 */
	private String TaskPlanCode;
	/** 日志主题id */
	private String SubjectID;
	/** 日志监控点id */
	private String ItemID;
	/** 提醒类型 */
	private String MsgType;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDTaskMsgItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "TaskCode";
		pk[1] = "TaskPlanCode";
		pk[2] = "SubjectID";
		pk[3] = "ItemID";
		pk[4] = "MsgType";

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
		LDTaskMsgItemSchema cloned = (LDTaskMsgItemSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTaskCode()
	{
		return TaskCode;
	}
	public void setTaskCode(String aTaskCode)
	{
		if(aTaskCode!=null && aTaskCode.length()>6)
			throw new IllegalArgumentException("任务代码TaskCode值"+aTaskCode+"的长度"+aTaskCode.length()+"大于最大值6");
		TaskCode = aTaskCode;
	}
	public String getTaskPlanCode()
	{
		return TaskPlanCode;
	}
	public void setTaskPlanCode(String aTaskPlanCode)
	{
		if(aTaskPlanCode!=null && aTaskPlanCode.length()>6)
			throw new IllegalArgumentException("任务计划代码TaskPlanCode值"+aTaskPlanCode+"的长度"+aTaskPlanCode.length()+"大于最大值6");
		TaskPlanCode = aTaskPlanCode;
	}
	public String getSubjectID()
	{
		return SubjectID;
	}
	public void setSubjectID(String aSubjectID)
	{
		if(aSubjectID!=null && aSubjectID.length()>20)
			throw new IllegalArgumentException("日志主题idSubjectID值"+aSubjectID+"的长度"+aSubjectID.length()+"大于最大值20");
		SubjectID = aSubjectID;
	}
	/**
	* 01-业务状态监控（监控业务的处理状态及其变化）<p>
	* 02-业务过程监控（监控业务处理过程中的轨迹）<p>
	* 03-处理结果监控（监控业务处理的结果，如预警）<p>
	* 04-业务性能监控（监控业务逻辑运行的性能，如运行时间与处理条数的关系）
	*/
	public String getItemID()
	{
		return ItemID;
	}
	public void setItemID(String aItemID)
	{
		if(aItemID!=null && aItemID.length()>20)
			throw new IllegalArgumentException("日志监控点idItemID值"+aItemID+"的长度"+aItemID.length()+"大于最大值20");
		ItemID = aItemID;
	}
	/**
	* 01-邮件
	*/
	public String getMsgType()
	{
		return MsgType;
	}
	public void setMsgType(String aMsgType)
	{
		if(aMsgType!=null && aMsgType.length()>2)
			throw new IllegalArgumentException("提醒类型MsgType值"+aMsgType+"的长度"+aMsgType.length()+"大于最大值2");
		MsgType = aMsgType;
	}

	/**
	* 使用另外一个 LDTaskMsgItemSchema 对象给 Schema 赋值
	* @param: aLDTaskMsgItemSchema LDTaskMsgItemSchema
	**/
	public void setSchema(LDTaskMsgItemSchema aLDTaskMsgItemSchema)
	{
		this.TaskCode = aLDTaskMsgItemSchema.getTaskCode();
		this.TaskPlanCode = aLDTaskMsgItemSchema.getTaskPlanCode();
		this.SubjectID = aLDTaskMsgItemSchema.getSubjectID();
		this.ItemID = aLDTaskMsgItemSchema.getItemID();
		this.MsgType = aLDTaskMsgItemSchema.getMsgType();
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
			if( rs.getString("TaskCode") == null )
				this.TaskCode = null;
			else
				this.TaskCode = rs.getString("TaskCode").trim();

			if( rs.getString("TaskPlanCode") == null )
				this.TaskPlanCode = null;
			else
				this.TaskPlanCode = rs.getString("TaskPlanCode").trim();

			if( rs.getString("SubjectID") == null )
				this.SubjectID = null;
			else
				this.SubjectID = rs.getString("SubjectID").trim();

			if( rs.getString("ItemID") == null )
				this.ItemID = null;
			else
				this.ItemID = rs.getString("ItemID").trim();

			if( rs.getString("MsgType") == null )
				this.MsgType = null;
			else
				this.MsgType = rs.getString("MsgType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDTaskMsgItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskMsgItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDTaskMsgItemSchema getSchema()
	{
		LDTaskMsgItemSchema aLDTaskMsgItemSchema = new LDTaskMsgItemSchema();
		aLDTaskMsgItemSchema.setSchema(this);
		return aLDTaskMsgItemSchema;
	}

	public LDTaskMsgItemDB getDB()
	{
		LDTaskMsgItemDB aDBOper = new LDTaskMsgItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskMsgItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TaskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubjectID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MsgType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskMsgItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TaskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TaskPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubjectID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MsgType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskMsgItemSchema";
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
		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskCode));
		}
		if (FCode.equalsIgnoreCase("TaskPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskPlanCode));
		}
		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectID));
		}
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemID));
		}
		if (FCode.equalsIgnoreCase("MsgType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MsgType));
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
				strFieldValue = StrTool.GBKToUnicode(TaskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TaskPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubjectID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ItemID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MsgType);
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

		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskCode = FValue.trim();
			}
			else
				TaskCode = null;
		}
		if (FCode.equalsIgnoreCase("TaskPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskPlanCode = FValue.trim();
			}
			else
				TaskPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectID = FValue.trim();
			}
			else
				SubjectID = null;
		}
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemID = FValue.trim();
			}
			else
				ItemID = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDTaskMsgItemSchema other = (LDTaskMsgItemSchema)otherObject;
		return
			TaskCode.equals(other.getTaskCode())
			&& TaskPlanCode.equals(other.getTaskPlanCode())
			&& SubjectID.equals(other.getSubjectID())
			&& ItemID.equals(other.getItemID())
			&& MsgType.equals(other.getMsgType());
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
		if( strFieldName.equals("TaskCode") ) {
			return 0;
		}
		if( strFieldName.equals("TaskPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("SubjectID") ) {
			return 2;
		}
		if( strFieldName.equals("ItemID") ) {
			return 3;
		}
		if( strFieldName.equals("MsgType") ) {
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
				strFieldName = "TaskCode";
				break;
			case 1:
				strFieldName = "TaskPlanCode";
				break;
			case 2:
				strFieldName = "SubjectID";
				break;
			case 3:
				strFieldName = "ItemID";
				break;
			case 4:
				strFieldName = "MsgType";
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
		if( strFieldName.equals("TaskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubjectID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MsgType") ) {
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
