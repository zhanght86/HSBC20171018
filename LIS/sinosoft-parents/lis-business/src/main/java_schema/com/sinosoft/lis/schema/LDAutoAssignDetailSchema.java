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
import com.sinosoft.lis.db.LDAutoAssignDetailDB;

/*
 * <p>ClassName: LDAutoAssignDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 自动分配
 */
public class LDAutoAssignDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDAutoAssignDetailSchema.class);

	// @Field
	/** 流水号 */
	private String Serialno;
	/** 任务号 */
	private String TaskNo;
	/** 工作流节点 */
	private String Activityid;
	/** 业务员 */
	private String AssignNo;
	/** 任务id */
	private String MissionID;
	/** 子任务id */
	private String SubMissionID;
	/** 印刷号 */
	private String PrtNo;
	/** 操作员 */
	private String Operator;
	/** 生成时间 */
	private String MakeTime;
	/** 生成日期 */
	private Date MakeDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 修改日期 */
	private Date ModifyDate;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDAutoAssignDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "Serialno";
		pk[1] = "TaskNo";
		pk[2] = "Activityid";
		pk[3] = "AssignNo";

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
		LDAutoAssignDetailSchema cloned = (LDAutoAssignDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialno()
	{
		return Serialno;
	}
	public void setSerialno(String aSerialno)
	{
		Serialno = aSerialno;
	}
	public String getTaskNo()
	{
		return TaskNo;
	}
	public void setTaskNo(String aTaskNo)
	{
		TaskNo = aTaskNo;
	}
	public String getActivityid()
	{
		return Activityid;
	}
	public void setActivityid(String aActivityid)
	{
		Activityid = aActivityid;
	}
	public String getAssignNo()
	{
		return AssignNo;
	}
	public void setAssignNo(String aAssignNo)
	{
		AssignNo = aAssignNo;
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
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
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


	/**
	* 使用另外一个 LDAutoAssignDetailSchema 对象给 Schema 赋值
	* @param: aLDAutoAssignDetailSchema LDAutoAssignDetailSchema
	**/
	public void setSchema(LDAutoAssignDetailSchema aLDAutoAssignDetailSchema)
	{
		this.Serialno = aLDAutoAssignDetailSchema.getSerialno();
		this.TaskNo = aLDAutoAssignDetailSchema.getTaskNo();
		this.Activityid = aLDAutoAssignDetailSchema.getActivityid();
		this.AssignNo = aLDAutoAssignDetailSchema.getAssignNo();
		this.MissionID = aLDAutoAssignDetailSchema.getMissionID();
		this.SubMissionID = aLDAutoAssignDetailSchema.getSubMissionID();
		this.PrtNo = aLDAutoAssignDetailSchema.getPrtNo();
		this.Operator = aLDAutoAssignDetailSchema.getOperator();
		this.MakeTime = aLDAutoAssignDetailSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLDAutoAssignDetailSchema.getMakeDate());
		this.ModifyTime = aLDAutoAssignDetailSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aLDAutoAssignDetailSchema.getModifyDate());
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
			if( rs.getString("Serialno") == null )
				this.Serialno = null;
			else
				this.Serialno = rs.getString("Serialno").trim();

			if( rs.getString("TaskNo") == null )
				this.TaskNo = null;
			else
				this.TaskNo = rs.getString("TaskNo").trim();

			if( rs.getString("Activityid") == null )
				this.Activityid = null;
			else
				this.Activityid = rs.getString("Activityid").trim();

			if( rs.getString("AssignNo") == null )
				this.AssignNo = null;
			else
				this.AssignNo = rs.getString("AssignNo").trim();

			if( rs.getString("MissionID") == null )
				this.MissionID = null;
			else
				this.MissionID = rs.getString("MissionID").trim();

			if( rs.getString("SubMissionID") == null )
				this.SubMissionID = null;
			else
				this.SubMissionID = rs.getString("SubMissionID").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDAutoAssignDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAutoAssignDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDAutoAssignDetailSchema getSchema()
	{
		LDAutoAssignDetailSchema aLDAutoAssignDetailSchema = new LDAutoAssignDetailSchema();
		aLDAutoAssignDetailSchema.setSchema(this);
		return aLDAutoAssignDetailSchema;
	}

	public LDAutoAssignDetailDB getDB()
	{
		LDAutoAssignDetailDB aDBOper = new LDAutoAssignDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAutoAssignDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Serialno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Activityid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssignNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubMissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAutoAssignDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Serialno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TaskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Activityid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AssignNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SubMissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAutoAssignDetailSchema";
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
		if (FCode.equalsIgnoreCase("Serialno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Serialno));
		}
		if (FCode.equalsIgnoreCase("TaskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskNo));
		}
		if (FCode.equalsIgnoreCase("Activityid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Activityid));
		}
		if (FCode.equalsIgnoreCase("AssignNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssignNo));
		}
		if (FCode.equalsIgnoreCase("MissionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionID));
		}
		if (FCode.equalsIgnoreCase("SubMissionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubMissionID));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
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
				strFieldValue = StrTool.GBKToUnicode(Serialno);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TaskNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Activityid);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AssignNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MissionID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SubMissionID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
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

		if (FCode.equalsIgnoreCase("Serialno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Serialno = FValue.trim();
			}
			else
				Serialno = null;
		}
		if (FCode.equalsIgnoreCase("TaskNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskNo = FValue.trim();
			}
			else
				TaskNo = null;
		}
		if (FCode.equalsIgnoreCase("Activityid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Activityid = FValue.trim();
			}
			else
				Activityid = null;
		}
		if (FCode.equalsIgnoreCase("AssignNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssignNo = FValue.trim();
			}
			else
				AssignNo = null;
		}
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
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
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
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDAutoAssignDetailSchema other = (LDAutoAssignDetailSchema)otherObject;
		return
			Serialno.equals(other.getSerialno())
			&& TaskNo.equals(other.getTaskNo())
			&& Activityid.equals(other.getActivityid())
			&& AssignNo.equals(other.getAssignNo())
			&& MissionID.equals(other.getMissionID())
			&& SubMissionID.equals(other.getSubMissionID())
			&& PrtNo.equals(other.getPrtNo())
			&& Operator.equals(other.getOperator())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate());
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
		if( strFieldName.equals("Serialno") ) {
			return 0;
		}
		if( strFieldName.equals("TaskNo") ) {
			return 1;
		}
		if( strFieldName.equals("Activityid") ) {
			return 2;
		}
		if( strFieldName.equals("AssignNo") ) {
			return 3;
		}
		if( strFieldName.equals("MissionID") ) {
			return 4;
		}
		if( strFieldName.equals("SubMissionID") ) {
			return 5;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
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
				strFieldName = "Serialno";
				break;
			case 1:
				strFieldName = "TaskNo";
				break;
			case 2:
				strFieldName = "Activityid";
				break;
			case 3:
				strFieldName = "AssignNo";
				break;
			case 4:
				strFieldName = "MissionID";
				break;
			case 5:
				strFieldName = "SubMissionID";
				break;
			case 6:
				strFieldName = "PrtNo";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
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
		if( strFieldName.equals("Serialno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Activityid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssignNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubMissionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
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
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
