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
import com.sinosoft.lis.db.LWLockDB;

/*
 * <p>ClassName: LWLockSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWLockSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWLockSchema.class);

	// @Field
	/** 任务id */
	private String MissionID;
	/** 子任务id */
	private String SubMissionID;
	/** 过程id */
	private String ProcessID;
	/** 锁的活动id */
	private String LockActivityID;
	/** 锁类型 */
	private String LockType;
	/** 锁状态 */
	private String LockStatus;
	/** 最后操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 超时时间 */
	private double TimeOut;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWLockSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "MissionID";
		pk[1] = "SubMissionID";
		pk[2] = "LockActivityID";
		pk[3] = "LockType";

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
		LWLockSchema cloned = (LWLockSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMissionID()
	{
		return MissionID;
	}
	public void setMissionID(String aMissionID)
	{
		MissionID = aMissionID;
	}
	/**
	* 对于子任务ID，一般为全0，但是对于一个任务没有完成，需要再次递归执行该任务时，则会产生子任务ID
	*/
	public String getSubMissionID()
	{
		return SubMissionID;
	}
	public void setSubMissionID(String aSubMissionID)
	{
		SubMissionID = aSubMissionID;
	}
	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		ProcessID = aProcessID;
	}
	public String getLockActivityID()
	{
		return LockActivityID;
	}
	public void setLockActivityID(String aLockActivityID)
	{
		LockActivityID = aLockActivityID;
	}
	public String getLockType()
	{
		return LockType;
	}
	public void setLockType(String aLockType)
	{
		LockType = aLockType;
	}
	/**
	* 0 -- 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）<p>
	* 1 -- 任务产生完毕待处理，<p>
	* 2 -- 处理中，<p>
	* 3 -- 处理完成，<p>
	* 4 -- 暂停
	*/
	public String getLockStatus()
	{
		return LockStatus;
	}
	public void setLockStatus(String aLockStatus)
	{
		LockStatus = aLockStatus;
	}
	/**
	* 纪录最后一次操作的操作员
	*/
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 纪录产生该纪录的操作员。
	*/
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
	public double getTimeOut()
	{
		return TimeOut;
	}
	public void setTimeOut(double aTimeOut)
	{
		TimeOut = aTimeOut;
	}
	public void setTimeOut(String aTimeOut)
	{
		if (aTimeOut != null && !aTimeOut.equals(""))
		{
			Double tDouble = new Double(aTimeOut);
			double d = tDouble.doubleValue();
			TimeOut = d;
		}
	}


	/**
	* 使用另外一个 LWLockSchema 对象给 Schema 赋值
	* @param: aLWLockSchema LWLockSchema
	**/
	public void setSchema(LWLockSchema aLWLockSchema)
	{
		this.MissionID = aLWLockSchema.getMissionID();
		this.SubMissionID = aLWLockSchema.getSubMissionID();
		this.ProcessID = aLWLockSchema.getProcessID();
		this.LockActivityID = aLWLockSchema.getLockActivityID();
		this.LockType = aLWLockSchema.getLockType();
		this.LockStatus = aLWLockSchema.getLockStatus();
		this.Operator = aLWLockSchema.getOperator();
		this.MakeDate = fDate.getDate( aLWLockSchema.getMakeDate());
		this.MakeTime = aLWLockSchema.getMakeTime();
		this.TimeOut = aLWLockSchema.getTimeOut();
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
			if( rs.getString("MissionID") == null )
				this.MissionID = null;
			else
				this.MissionID = rs.getString("MissionID").trim();

			if( rs.getString("SubMissionID") == null )
				this.SubMissionID = null;
			else
				this.SubMissionID = rs.getString("SubMissionID").trim();

			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

			if( rs.getString("LockActivityID") == null )
				this.LockActivityID = null;
			else
				this.LockActivityID = rs.getString("LockActivityID").trim();

			if( rs.getString("LockType") == null )
				this.LockType = null;
			else
				this.LockType = rs.getString("LockType").trim();

			if( rs.getString("LockStatus") == null )
				this.LockStatus = null;
			else
				this.LockStatus = rs.getString("LockStatus").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.TimeOut = rs.getDouble("TimeOut");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWLock表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWLockSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWLockSchema getSchema()
	{
		LWLockSchema aLWLockSchema = new LWLockSchema();
		aLWLockSchema.setSchema(this);
		return aLWLockSchema;
	}

	public LWLockDB getDB()
	{
		LWLockDB aDBOper = new LWLockDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWLock描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubMissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TimeOut));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWLock>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubMissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LockActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LockType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			LockStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TimeOut = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWLockSchema";
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
		if (FCode.equalsIgnoreCase("MissionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionID));
		}
		if (FCode.equalsIgnoreCase("SubMissionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubMissionID));
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
		}
		if (FCode.equalsIgnoreCase("LockActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockActivityID));
		}
		if (FCode.equalsIgnoreCase("LockType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockType));
		}
		if (FCode.equalsIgnoreCase("LockStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockStatus));
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
		if (FCode.equalsIgnoreCase("TimeOut"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TimeOut));
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
				strFieldValue = StrTool.GBKToUnicode(MissionID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubMissionID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LockActivityID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LockType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(LockStatus);
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
			case 9:
				strFieldValue = String.valueOf(TimeOut);
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

		if (FCode.equalsIgnoreCase("MissionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionID = FValue.trim();
			}
			else
				MissionID = null;
		}
		if (FCode.equalsIgnoreCase("SubMissionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubMissionID = FValue.trim();
			}
			else
				SubMissionID = null;
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessID = FValue.trim();
			}
			else
				ProcessID = null;
		}
		if (FCode.equalsIgnoreCase("LockActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockActivityID = FValue.trim();
			}
			else
				LockActivityID = null;
		}
		if (FCode.equalsIgnoreCase("LockType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockType = FValue.trim();
			}
			else
				LockType = null;
		}
		if (FCode.equalsIgnoreCase("LockStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockStatus = FValue.trim();
			}
			else
				LockStatus = null;
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
		if (FCode.equalsIgnoreCase("TimeOut"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TimeOut = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWLockSchema other = (LWLockSchema)otherObject;
		return
			MissionID.equals(other.getMissionID())
			&& SubMissionID.equals(other.getSubMissionID())
			&& ProcessID.equals(other.getProcessID())
			&& LockActivityID.equals(other.getLockActivityID())
			&& LockType.equals(other.getLockType())
			&& LockStatus.equals(other.getLockStatus())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& TimeOut == other.getTimeOut();
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
		if( strFieldName.equals("MissionID") ) {
			return 0;
		}
		if( strFieldName.equals("SubMissionID") ) {
			return 1;
		}
		if( strFieldName.equals("ProcessID") ) {
			return 2;
		}
		if( strFieldName.equals("LockActivityID") ) {
			return 3;
		}
		if( strFieldName.equals("LockType") ) {
			return 4;
		}
		if( strFieldName.equals("LockStatus") ) {
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
		if( strFieldName.equals("TimeOut") ) {
			return 9;
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
				strFieldName = "MissionID";
				break;
			case 1:
				strFieldName = "SubMissionID";
				break;
			case 2:
				strFieldName = "ProcessID";
				break;
			case 3:
				strFieldName = "LockActivityID";
				break;
			case 4:
				strFieldName = "LockType";
				break;
			case 5:
				strFieldName = "LockStatus";
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
			case 9:
				strFieldName = "TimeOut";
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
		if( strFieldName.equals("MissionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubMissionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LockActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LockType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LockStatus") ) {
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
		if( strFieldName.equals("TimeOut") ) {
			return Schema.TYPE_DOUBLE;
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
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
