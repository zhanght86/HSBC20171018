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
import com.sinosoft.lis.db.LDTaskPlanTransBakDB;

/*
 * <p>ClassName: LDTaskPlanTransBakSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 任务计划转移
 */
public class LDTaskPlanTransBakSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDTaskPlanTransBakSchema.class);

	// @Field
	/** 序列号 */
	private String SerialNo;
	/** 任务计划编码 */
	private String TaskPlanCode;
	/** 原服务器ip */
	private String OldServerIP;
	/** 原服务器端口 */
	private String OldServerPort;
	/** 新服务器ip */
	private String NewServerIP;
	/** 新服务器端口 */
	private String NewServerPort;
	/** 状态 */
	private String State;
	/** 录入日期 */
	private Date MakeDate;
	/** 录入时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDTaskPlanTransBakSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "SerialNo";
		pk[1] = "TaskPlanCode";
		pk[2] = "OldServerIP";
		pk[3] = "OldServerPort";

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
		LDTaskPlanTransBakSchema cloned = (LDTaskPlanTransBakSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("序列号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getTaskPlanCode()
	{
		return TaskPlanCode;
	}
	public void setTaskPlanCode(String aTaskPlanCode)
	{
		if(aTaskPlanCode!=null && aTaskPlanCode.length()>6)
			throw new IllegalArgumentException("任务计划编码TaskPlanCode值"+aTaskPlanCode+"的长度"+aTaskPlanCode.length()+"大于最大值6");
		TaskPlanCode = aTaskPlanCode;
	}
	public String getOldServerIP()
	{
		return OldServerIP;
	}
	public void setOldServerIP(String aOldServerIP)
	{
		if(aOldServerIP!=null && aOldServerIP.length()>20)
			throw new IllegalArgumentException("原服务器ipOldServerIP值"+aOldServerIP+"的长度"+aOldServerIP.length()+"大于最大值20");
		OldServerIP = aOldServerIP;
	}
	/**
	* 0: 未启用<p>
	* 1: 启用<p>
	* 未启用的任务无效
	*/
	public String getOldServerPort()
	{
		return OldServerPort;
	}
	public void setOldServerPort(String aOldServerPort)
	{
		if(aOldServerPort!=null && aOldServerPort.length()>20)
			throw new IllegalArgumentException("原服务器端口OldServerPort值"+aOldServerPort+"的长度"+aOldServerPort.length()+"大于最大值20");
		OldServerPort = aOldServerPort;
	}
	public String getNewServerIP()
	{
		return NewServerIP;
	}
	public void setNewServerIP(String aNewServerIP)
	{
		if(aNewServerIP!=null && aNewServerIP.length()>20)
			throw new IllegalArgumentException("新服务器ipNewServerIP值"+aNewServerIP+"的长度"+aNewServerIP.length()+"大于最大值20");
		NewServerIP = aNewServerIP;
	}
	/**
	* 0: 未启用<p>
	* 1: 启用<p>
	* 未启用的任务无效
	*/
	public String getNewServerPort()
	{
		return NewServerPort;
	}
	public void setNewServerPort(String aNewServerPort)
	{
		if(aNewServerPort!=null && aNewServerPort.length()>20)
			throw new IllegalArgumentException("新服务器端口NewServerPort值"+aNewServerPort+"的长度"+aNewServerPort.length()+"大于最大值20");
		NewServerPort = aNewServerPort;
	}
	/**
	* 0-待转移<p>
	* 1-已转移<p>
	* 2-已执行<p>
	* 3-待释放<p>
	* 4-已释放
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>1)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值1");
		State = aState;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("录入时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDTaskPlanTransBakSchema 对象给 Schema 赋值
	* @param: aLDTaskPlanTransBakSchema LDTaskPlanTransBakSchema
	**/
	public void setSchema(LDTaskPlanTransBakSchema aLDTaskPlanTransBakSchema)
	{
		this.SerialNo = aLDTaskPlanTransBakSchema.getSerialNo();
		this.TaskPlanCode = aLDTaskPlanTransBakSchema.getTaskPlanCode();
		this.OldServerIP = aLDTaskPlanTransBakSchema.getOldServerIP();
		this.OldServerPort = aLDTaskPlanTransBakSchema.getOldServerPort();
		this.NewServerIP = aLDTaskPlanTransBakSchema.getNewServerIP();
		this.NewServerPort = aLDTaskPlanTransBakSchema.getNewServerPort();
		this.State = aLDTaskPlanTransBakSchema.getState();
		this.MakeDate = fDate.getDate( aLDTaskPlanTransBakSchema.getMakeDate());
		this.MakeTime = aLDTaskPlanTransBakSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDTaskPlanTransBakSchema.getModifyDate());
		this.ModifyTime = aLDTaskPlanTransBakSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("TaskPlanCode") == null )
				this.TaskPlanCode = null;
			else
				this.TaskPlanCode = rs.getString("TaskPlanCode").trim();

			if( rs.getString("OldServerIP") == null )
				this.OldServerIP = null;
			else
				this.OldServerIP = rs.getString("OldServerIP").trim();

			if( rs.getString("OldServerPort") == null )
				this.OldServerPort = null;
			else
				this.OldServerPort = rs.getString("OldServerPort").trim();

			if( rs.getString("NewServerIP") == null )
				this.NewServerIP = null;
			else
				this.NewServerIP = rs.getString("NewServerIP").trim();

			if( rs.getString("NewServerPort") == null )
				this.NewServerPort = null;
			else
				this.NewServerPort = rs.getString("NewServerPort").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDTaskPlanTransBak表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskPlanTransBakSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDTaskPlanTransBakSchema getSchema()
	{
		LDTaskPlanTransBakSchema aLDTaskPlanTransBakSchema = new LDTaskPlanTransBakSchema();
		aLDTaskPlanTransBakSchema.setSchema(this);
		return aLDTaskPlanTransBakSchema;
	}

	public LDTaskPlanTransBakDB getDB()
	{
		LDTaskPlanTransBakDB aDBOper = new LDTaskPlanTransBakDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskPlanTransBak描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldServerIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldServerPort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewServerIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewServerPort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskPlanTransBak>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TaskPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OldServerIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OldServerPort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NewServerIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NewServerPort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskPlanTransBakSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("TaskPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskPlanCode));
		}
		if (FCode.equalsIgnoreCase("OldServerIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldServerIP));
		}
		if (FCode.equalsIgnoreCase("OldServerPort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldServerPort));
		}
		if (FCode.equalsIgnoreCase("NewServerIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewServerIP));
		}
		if (FCode.equalsIgnoreCase("NewServerPort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewServerPort));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TaskPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OldServerIP);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OldServerPort);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NewServerIP);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NewServerPort);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("OldServerIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldServerIP = FValue.trim();
			}
			else
				OldServerIP = null;
		}
		if (FCode.equalsIgnoreCase("OldServerPort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldServerPort = FValue.trim();
			}
			else
				OldServerPort = null;
		}
		if (FCode.equalsIgnoreCase("NewServerIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewServerIP = FValue.trim();
			}
			else
				NewServerIP = null;
		}
		if (FCode.equalsIgnoreCase("NewServerPort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewServerPort = FValue.trim();
			}
			else
				NewServerPort = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDTaskPlanTransBakSchema other = (LDTaskPlanTransBakSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& TaskPlanCode.equals(other.getTaskPlanCode())
			&& OldServerIP.equals(other.getOldServerIP())
			&& OldServerPort.equals(other.getOldServerPort())
			&& NewServerIP.equals(other.getNewServerIP())
			&& NewServerPort.equals(other.getNewServerPort())
			&& State.equals(other.getState())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("TaskPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("OldServerIP") ) {
			return 2;
		}
		if( strFieldName.equals("OldServerPort") ) {
			return 3;
		}
		if( strFieldName.equals("NewServerIP") ) {
			return 4;
		}
		if( strFieldName.equals("NewServerPort") ) {
			return 5;
		}
		if( strFieldName.equals("State") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "TaskPlanCode";
				break;
			case 2:
				strFieldName = "OldServerIP";
				break;
			case 3:
				strFieldName = "OldServerPort";
				break;
			case 4:
				strFieldName = "NewServerIP";
				break;
			case 5:
				strFieldName = "NewServerPort";
				break;
			case 6:
				strFieldName = "State";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldServerIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldServerPort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewServerIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewServerPort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
