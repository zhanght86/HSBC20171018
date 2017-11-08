/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIOperationLogDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIOperationLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIOperationLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIOperationLogSchema.class);

	// @Field
	/** 事件号码 */
	private String EventNo;
	/** 事件类型 */
	private String EventType;
	/** 日志文件名称 */
	private String LogFileName;
	/** 日志文件路径 */
	private String LogFilePath;
	/** 执行状态 */
	private String PerformState;
	/** 其他标志 */
	private String othernoMark;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIOperationLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "EventNo";

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
                FIOperationLogSchema cloned = (FIOperationLogSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEventNo()
	{
		return EventNo;
	}
	public void setEventNo(String aEventNo)
	{
		EventNo = aEventNo;
	}
	public String getEventType()
	{
		return EventType;
	}
	public void setEventType(String aEventType)
	{
		EventType = aEventType;
	}
	public String getLogFileName()
	{
		return LogFileName;
	}
	public void setLogFileName(String aLogFileName)
	{
		LogFileName = aLogFileName;
	}
	public String getLogFilePath()
	{
		return LogFilePath;
	}
	public void setLogFilePath(String aLogFilePath)
	{
		LogFilePath = aLogFilePath;
	}
	public String getPerformState()
	{
		return PerformState;
	}
	public void setPerformState(String aPerformState)
	{
		PerformState = aPerformState;
	}
	public String getothernoMark()
	{
		return othernoMark;
	}
	public void setothernoMark(String aothernoMark)
	{
		othernoMark = aothernoMark;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 FIOperationLogSchema 对象给 Schema 赋值
	* @param: aFIOperationLogSchema FIOperationLogSchema
	**/
	public void setSchema(FIOperationLogSchema aFIOperationLogSchema)
	{
		this.EventNo = aFIOperationLogSchema.getEventNo();
		this.EventType = aFIOperationLogSchema.getEventType();
		this.LogFileName = aFIOperationLogSchema.getLogFileName();
		this.LogFilePath = aFIOperationLogSchema.getLogFilePath();
		this.PerformState = aFIOperationLogSchema.getPerformState();
		this.othernoMark = aFIOperationLogSchema.getothernoMark();
		this.Operator = aFIOperationLogSchema.getOperator();
		this.MakeDate = fDate.getDate( aFIOperationLogSchema.getMakeDate());
		this.MakeTime = aFIOperationLogSchema.getMakeTime();
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
			if( rs.getString("EventNo") == null )
				this.EventNo = null;
			else
				this.EventNo = rs.getString("EventNo").trim();

			if( rs.getString("EventType") == null )
				this.EventType = null;
			else
				this.EventType = rs.getString("EventType").trim();

			if( rs.getString("LogFileName") == null )
				this.LogFileName = null;
			else
				this.LogFileName = rs.getString("LogFileName").trim();

			if( rs.getString("LogFilePath") == null )
				this.LogFilePath = null;
			else
				this.LogFilePath = rs.getString("LogFilePath").trim();

			if( rs.getString("PerformState") == null )
				this.PerformState = null;
			else
				this.PerformState = rs.getString("PerformState").trim();

			if( rs.getString("othernoMark") == null )
				this.othernoMark = null;
			else
				this.othernoMark = rs.getString("othernoMark").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIOperationLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIOperationLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIOperationLogSchema getSchema()
	{
		FIOperationLogSchema aFIOperationLogSchema = new FIOperationLogSchema();
		aFIOperationLogSchema.setSchema(this);
		return aFIOperationLogSchema;
	}

	public FIOperationLogDB getDB()
	{
		FIOperationLogDB aDBOper = new FIOperationLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIOperationLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(EventNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(EventType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LogFileName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LogFilePath)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(PerformState)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(othernoMark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIOperationLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EventNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EventType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LogFileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LogFilePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PerformState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			othernoMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIOperationLogSchema";
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
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventNo));
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventType));
		}
		if (FCode.equalsIgnoreCase("LogFileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogFileName));
		}
		if (FCode.equalsIgnoreCase("LogFilePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogFilePath));
		}
		if (FCode.equalsIgnoreCase("PerformState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PerformState));
		}
		if (FCode.equalsIgnoreCase("othernoMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(othernoMark));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(EventNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EventType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(LogFileName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LogFilePath);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PerformState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(othernoMark);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("EventNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventNo = FValue.trim();
			}
			else
				EventNo = null;
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventType = FValue.trim();
			}
			else
				EventType = null;
		}
		if (FCode.equalsIgnoreCase("LogFileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogFileName = FValue.trim();
			}
			else
				LogFileName = null;
		}
		if (FCode.equalsIgnoreCase("LogFilePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogFilePath = FValue.trim();
			}
			else
				LogFilePath = null;
		}
		if (FCode.equalsIgnoreCase("PerformState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PerformState = FValue.trim();
			}
			else
				PerformState = null;
		}
		if (FCode.equalsIgnoreCase("othernoMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				othernoMark = FValue.trim();
			}
			else
				othernoMark = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIOperationLogSchema other = (FIOperationLogSchema)otherObject;
		return
			EventNo.equals(other.getEventNo())
			&& EventType.equals(other.getEventType())
			&& LogFileName.equals(other.getLogFileName())
			&& LogFilePath.equals(other.getLogFilePath())
			&& PerformState.equals(other.getPerformState())
			&& othernoMark.equals(other.getothernoMark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("EventNo") ) {
			return 0;
		}
		if( strFieldName.equals("EventType") ) {
			return 1;
		}
		if( strFieldName.equals("LogFileName") ) {
			return 2;
		}
		if( strFieldName.equals("LogFilePath") ) {
			return 3;
		}
		if( strFieldName.equals("PerformState") ) {
			return 4;
		}
		if( strFieldName.equals("othernoMark") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
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
				strFieldName = "EventNo";
				break;
			case 1:
				strFieldName = "EventType";
				break;
			case 2:
				strFieldName = "LogFileName";
				break;
			case 3:
				strFieldName = "LogFilePath";
				break;
			case 4:
				strFieldName = "PerformState";
				break;
			case 5:
				strFieldName = "othernoMark";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("EventNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogFileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogFilePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PerformState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("othernoMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
