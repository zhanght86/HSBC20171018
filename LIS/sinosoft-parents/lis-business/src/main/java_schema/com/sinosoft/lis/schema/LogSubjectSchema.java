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
import com.sinosoft.lis.db.LogSubjectDB;

/*
 * <p>ClassName: LogSubjectSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 日志
 */
public class LogSubjectSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LogSubjectSchema.class);

	// @Field
	/** 日志主题id */
	private String SubjectID;
	/** 日志主题描述 */
	private String SubjectDes;
	/** 日志监控部门 */
	private String Dept;
	/** 所属业务模块 */
	private String ServiceModule;
	/** 业务功能编码 */
	private String TaskCode;
	/** 业务功能描述 */
	private String TaskDescribe;
	/** 日志分类 */
	private String LogType;
	/** 日志监控域开关 */
	private String Switch;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LogSubjectSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SubjectID";

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
		LogSubjectSchema cloned = (LogSubjectSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getSubjectDes()
	{
		return SubjectDes;
	}
	public void setSubjectDes(String aSubjectDes)
	{
		if(aSubjectDes!=null && aSubjectDes.length()>200)
			throw new IllegalArgumentException("日志主题描述SubjectDes值"+aSubjectDes+"的长度"+aSubjectDes.length()+"大于最大值200");
		SubjectDes = aSubjectDes;
	}
	public String getDept()
	{
		return Dept;
	}
	public void setDept(String aDept)
	{
		if(aDept!=null && aDept.length()>50)
			throw new IllegalArgumentException("日志监控部门Dept值"+aDept+"的长度"+aDept.length()+"大于最大值50");
		Dept = aDept;
	}
	/**
	* NB,POS,CLM等
	*/
	public String getServiceModule()
	{
		return ServiceModule;
	}
	public void setServiceModule(String aServiceModule)
	{
		if(aServiceModule!=null && aServiceModule.length()>10)
			throw new IllegalArgumentException("所属业务模块ServiceModule值"+aServiceModule+"的长度"+aServiceModule.length()+"大于最大值10");
		ServiceModule = aServiceModule;
	}
	public String getTaskCode()
	{
		return TaskCode;
	}
	public void setTaskCode(String aTaskCode)
	{
		if(aTaskCode!=null && aTaskCode.length()>10)
			throw new IllegalArgumentException("业务功能编码TaskCode值"+aTaskCode+"的长度"+aTaskCode.length()+"大于最大值10");
		TaskCode = aTaskCode;
	}
	public String getTaskDescribe()
	{
		return TaskDescribe;
	}
	public void setTaskDescribe(String aTaskDescribe)
	{
		if(aTaskDescribe!=null && aTaskDescribe.length()>50)
			throw new IllegalArgumentException("业务功能描述TaskDescribe值"+aTaskDescribe+"的长度"+aTaskDescribe.length()+"大于最大值50");
		TaskDescribe = aTaskDescribe;
	}
	public String getLogType()
	{
		return LogType;
	}
	public void setLogType(String aLogType)
	{
		if(aLogType!=null && aLogType.length()>10)
			throw new IllegalArgumentException("日志分类LogType值"+aLogType+"的长度"+aLogType.length()+"大于最大值10");
		LogType = aLogType;
	}
	/**
	* NB,POS,CLM等
	*/
	public String getSwitch()
	{
		return Switch;
	}
	public void setSwitch(String aSwitch)
	{
		if(aSwitch!=null && aSwitch.length()>1)
			throw new IllegalArgumentException("日志监控域开关Switch值"+aSwitch+"的长度"+aSwitch.length()+"大于最大值1");
		Switch = aSwitch;
	}

	/**
	* 使用另外一个 LogSubjectSchema 对象给 Schema 赋值
	* @param: aLogSubjectSchema LogSubjectSchema
	**/
	public void setSchema(LogSubjectSchema aLogSubjectSchema)
	{
		this.SubjectID = aLogSubjectSchema.getSubjectID();
		this.SubjectDes = aLogSubjectSchema.getSubjectDes();
		this.Dept = aLogSubjectSchema.getDept();
		this.ServiceModule = aLogSubjectSchema.getServiceModule();
		this.TaskCode = aLogSubjectSchema.getTaskCode();
		this.TaskDescribe = aLogSubjectSchema.getTaskDescribe();
		this.LogType = aLogSubjectSchema.getLogType();
		this.Switch = aLogSubjectSchema.getSwitch();
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
			if( rs.getString("SubjectID") == null )
				this.SubjectID = null;
			else
				this.SubjectID = rs.getString("SubjectID").trim();

			if( rs.getString("SubjectDes") == null )
				this.SubjectDes = null;
			else
				this.SubjectDes = rs.getString("SubjectDes").trim();

			if( rs.getString("Dept") == null )
				this.Dept = null;
			else
				this.Dept = rs.getString("Dept").trim();

			if( rs.getString("ServiceModule") == null )
				this.ServiceModule = null;
			else
				this.ServiceModule = rs.getString("ServiceModule").trim();

			if( rs.getString("TaskCode") == null )
				this.TaskCode = null;
			else
				this.TaskCode = rs.getString("TaskCode").trim();

			if( rs.getString("TaskDescribe") == null )
				this.TaskDescribe = null;
			else
				this.TaskDescribe = rs.getString("TaskDescribe").trim();

			if( rs.getString("LogType") == null )
				this.LogType = null;
			else
				this.LogType = rs.getString("LogType").trim();

			if( rs.getString("Switch") == null )
				this.Switch = null;
			else
				this.Switch = rs.getString("Switch").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LogSubject表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogSubjectSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LogSubjectSchema getSchema()
	{
		LogSubjectSchema aLogSubjectSchema = new LogSubjectSchema();
		aLogSubjectSchema.setSchema(this);
		return aLogSubjectSchema;
	}

	public LogSubjectDB getDB()
	{
		LogSubjectDB aDBOper = new LogSubjectDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogSubject描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubjectID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubjectDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Dept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServiceModule)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskDescribe)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Switch));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogSubject>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubjectID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubjectDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Dept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ServiceModule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TaskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TaskDescribe = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LogType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Switch = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogSubjectSchema";
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
		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectID));
		}
		if (FCode.equalsIgnoreCase("SubjectDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectDes));
		}
		if (FCode.equalsIgnoreCase("Dept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dept));
		}
		if (FCode.equalsIgnoreCase("ServiceModule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServiceModule));
		}
		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskCode));
		}
		if (FCode.equalsIgnoreCase("TaskDescribe"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskDescribe));
		}
		if (FCode.equalsIgnoreCase("LogType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogType));
		}
		if (FCode.equalsIgnoreCase("Switch"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Switch));
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
				strFieldValue = StrTool.GBKToUnicode(SubjectID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubjectDes);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Dept);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ServiceModule);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TaskCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TaskDescribe);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LogType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Switch);
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

		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectID = FValue.trim();
			}
			else
				SubjectID = null;
		}
		if (FCode.equalsIgnoreCase("SubjectDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectDes = FValue.trim();
			}
			else
				SubjectDes = null;
		}
		if (FCode.equalsIgnoreCase("Dept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Dept = FValue.trim();
			}
			else
				Dept = null;
		}
		if (FCode.equalsIgnoreCase("ServiceModule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServiceModule = FValue.trim();
			}
			else
				ServiceModule = null;
		}
		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskCode = FValue.trim();
			}
			else
				TaskCode = null;
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
		if (FCode.equalsIgnoreCase("LogType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogType = FValue.trim();
			}
			else
				LogType = null;
		}
		if (FCode.equalsIgnoreCase("Switch"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Switch = FValue.trim();
			}
			else
				Switch = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LogSubjectSchema other = (LogSubjectSchema)otherObject;
		return
			SubjectID.equals(other.getSubjectID())
			&& SubjectDes.equals(other.getSubjectDes())
			&& Dept.equals(other.getDept())
			&& ServiceModule.equals(other.getServiceModule())
			&& TaskCode.equals(other.getTaskCode())
			&& TaskDescribe.equals(other.getTaskDescribe())
			&& LogType.equals(other.getLogType())
			&& Switch.equals(other.getSwitch());
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
		if( strFieldName.equals("SubjectID") ) {
			return 0;
		}
		if( strFieldName.equals("SubjectDes") ) {
			return 1;
		}
		if( strFieldName.equals("Dept") ) {
			return 2;
		}
		if( strFieldName.equals("ServiceModule") ) {
			return 3;
		}
		if( strFieldName.equals("TaskCode") ) {
			return 4;
		}
		if( strFieldName.equals("TaskDescribe") ) {
			return 5;
		}
		if( strFieldName.equals("LogType") ) {
			return 6;
		}
		if( strFieldName.equals("Switch") ) {
			return 7;
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
				strFieldName = "SubjectID";
				break;
			case 1:
				strFieldName = "SubjectDes";
				break;
			case 2:
				strFieldName = "Dept";
				break;
			case 3:
				strFieldName = "ServiceModule";
				break;
			case 4:
				strFieldName = "TaskCode";
				break;
			case 5:
				strFieldName = "TaskDescribe";
				break;
			case 6:
				strFieldName = "LogType";
				break;
			case 7:
				strFieldName = "Switch";
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
		if( strFieldName.equals("SubjectID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubjectDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Dept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServiceModule") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskDescribe") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Switch") ) {
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
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
