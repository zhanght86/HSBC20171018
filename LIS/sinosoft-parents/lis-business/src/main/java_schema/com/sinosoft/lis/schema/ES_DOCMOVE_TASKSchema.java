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
import com.sinosoft.lis.db.ES_DOCMOVE_TASKDB;

/*
 * <p>ClassName: ES_DOCMOVE_TASKSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOCMOVE_TASKSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOCMOVE_TASKSchema.class);

	// @Field
	/** 起始时间 */
	private String StartTime;
	/** 批次号 */
	private String MoveID;
	/** 起始日期 */
	private Date StartDate;
	/** 操作机构 */
	private String ManageCom;
	/** 任务编码 */
	private int TaskCode;
	/** 任务类型 */
	private String TaskType;
	/** 计划迁移数量 */
	private int TaskNumber;
	/** 成功迁移数量 */
	private int SuccNumber;
	/** 目标机构 */
	private String ToManageCom;
	/** Enddate */
	private Date EndDate;
	/** Endtime */
	private String EndTime;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOCMOVE_TASKSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "MoveID";
		pk[1] = "ManageCom";
		pk[2] = "TaskCode";
		pk[3] = "TaskType";
		pk[4] = "ToManageCom";

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
		ES_DOCMOVE_TASKSchema cloned = (ES_DOCMOVE_TASKSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getStartTime()
	{
		return StartTime;
	}
	public void setStartTime(String aStartTime)
	{
		StartTime = aStartTime;
	}
	public String getMoveID()
	{
		return MoveID;
	}
	public void setMoveID(String aMoveID)
	{
		MoveID = aMoveID;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public int getTaskCode()
	{
		return TaskCode;
	}
	public void setTaskCode(int aTaskCode)
	{
		TaskCode = aTaskCode;
	}
	public void setTaskCode(String aTaskCode)
	{
		if (aTaskCode != null && !aTaskCode.equals(""))
		{
			Integer tInteger = new Integer(aTaskCode);
			int i = tInteger.intValue();
			TaskCode = i;
		}
	}

	/**
	* 0-全新迁移完成<p>
	* 1-正在迁移(未完成)<p>
	* 2-重传完成
	*/
	public String getTaskType()
	{
		return TaskType;
	}
	public void setTaskType(String aTaskType)
	{
		TaskType = aTaskType;
	}
	public int getTaskNumber()
	{
		return TaskNumber;
	}
	public void setTaskNumber(int aTaskNumber)
	{
		TaskNumber = aTaskNumber;
	}
	public void setTaskNumber(String aTaskNumber)
	{
		if (aTaskNumber != null && !aTaskNumber.equals(""))
		{
			Integer tInteger = new Integer(aTaskNumber);
			int i = tInteger.intValue();
			TaskNumber = i;
		}
	}

	public int getSuccNumber()
	{
		return SuccNumber;
	}
	public void setSuccNumber(int aSuccNumber)
	{
		SuccNumber = aSuccNumber;
	}
	public void setSuccNumber(String aSuccNumber)
	{
		if (aSuccNumber != null && !aSuccNumber.equals(""))
		{
			Integer tInteger = new Integer(aSuccNumber);
			int i = tInteger.intValue();
			SuccNumber = i;
		}
	}

	public String getToManageCom()
	{
		return ToManageCom;
	}
	public void setToManageCom(String aToManageCom)
	{
		ToManageCom = aToManageCom;
	}
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getEndTime()
	{
		return EndTime;
	}
	public void setEndTime(String aEndTime)
	{
		EndTime = aEndTime;
	}

	/**
	* 使用另外一个 ES_DOCMOVE_TASKSchema 对象给 Schema 赋值
	* @param: aES_DOCMOVE_TASKSchema ES_DOCMOVE_TASKSchema
	**/
	public void setSchema(ES_DOCMOVE_TASKSchema aES_DOCMOVE_TASKSchema)
	{
		this.StartTime = aES_DOCMOVE_TASKSchema.getStartTime();
		this.MoveID = aES_DOCMOVE_TASKSchema.getMoveID();
		this.StartDate = fDate.getDate( aES_DOCMOVE_TASKSchema.getStartDate());
		this.ManageCom = aES_DOCMOVE_TASKSchema.getManageCom();
		this.TaskCode = aES_DOCMOVE_TASKSchema.getTaskCode();
		this.TaskType = aES_DOCMOVE_TASKSchema.getTaskType();
		this.TaskNumber = aES_DOCMOVE_TASKSchema.getTaskNumber();
		this.SuccNumber = aES_DOCMOVE_TASKSchema.getSuccNumber();
		this.ToManageCom = aES_DOCMOVE_TASKSchema.getToManageCom();
		this.EndDate = fDate.getDate( aES_DOCMOVE_TASKSchema.getEndDate());
		this.EndTime = aES_DOCMOVE_TASKSchema.getEndTime();
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
			if( rs.getString("StartTime") == null )
				this.StartTime = null;
			else
				this.StartTime = rs.getString("StartTime").trim();

			if( rs.getString("MoveID") == null )
				this.MoveID = null;
			else
				this.MoveID = rs.getString("MoveID").trim();

			this.StartDate = rs.getDate("StartDate");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.TaskCode = rs.getInt("TaskCode");
			if( rs.getString("TaskType") == null )
				this.TaskType = null;
			else
				this.TaskType = rs.getString("TaskType").trim();

			this.TaskNumber = rs.getInt("TaskNumber");
			this.SuccNumber = rs.getInt("SuccNumber");
			if( rs.getString("ToManageCom") == null )
				this.ToManageCom = null;
			else
				this.ToManageCom = rs.getString("ToManageCom").trim();

			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("EndTime") == null )
				this.EndTime = null;
			else
				this.EndTime = rs.getString("EndTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOCMOVE_TASK表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOCMOVE_TASKSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOCMOVE_TASKSchema getSchema()
	{
		ES_DOCMOVE_TASKSchema aES_DOCMOVE_TASKSchema = new ES_DOCMOVE_TASKSchema();
		aES_DOCMOVE_TASKSchema.setSchema(this);
		return aES_DOCMOVE_TASKSchema;
	}

	public ES_DOCMOVE_TASKDB getDB()
	{
		ES_DOCMOVE_TASKDB aDBOper = new ES_DOCMOVE_TASKDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOCMOVE_TASK描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(StartTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoveID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaskCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaskNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SuccNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOCMOVE_TASK>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			StartTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MoveID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TaskCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			TaskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TaskNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			SuccNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			ToManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			EndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOCMOVE_TASKSchema";
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
		if (FCode.equalsIgnoreCase("StartTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartTime));
		}
		if (FCode.equalsIgnoreCase("MoveID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoveID));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskCode));
		}
		if (FCode.equalsIgnoreCase("TaskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskType));
		}
		if (FCode.equalsIgnoreCase("TaskNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskNumber));
		}
		if (FCode.equalsIgnoreCase("SuccNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuccNumber));
		}
		if (FCode.equalsIgnoreCase("ToManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToManageCom));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("EndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndTime));
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
				strFieldValue = StrTool.GBKToUnicode(StartTime);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MoveID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = String.valueOf(TaskCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TaskType);
				break;
			case 6:
				strFieldValue = String.valueOf(TaskNumber);
				break;
			case 7:
				strFieldValue = String.valueOf(SuccNumber);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ToManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(EndTime);
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

		if (FCode.equalsIgnoreCase("StartTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartTime = FValue.trim();
			}
			else
				StartTime = null;
		}
		if (FCode.equalsIgnoreCase("MoveID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoveID = FValue.trim();
			}
			else
				MoveID = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TaskCode = i;
			}
		}
		if (FCode.equalsIgnoreCase("TaskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskType = FValue.trim();
			}
			else
				TaskType = null;
		}
		if (FCode.equalsIgnoreCase("TaskNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TaskNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("SuccNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SuccNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("ToManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToManageCom = FValue.trim();
			}
			else
				ToManageCom = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("EndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndTime = FValue.trim();
			}
			else
				EndTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOCMOVE_TASKSchema other = (ES_DOCMOVE_TASKSchema)otherObject;
		return
			StartTime.equals(other.getStartTime())
			&& MoveID.equals(other.getMoveID())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& ManageCom.equals(other.getManageCom())
			&& TaskCode == other.getTaskCode()
			&& TaskType.equals(other.getTaskType())
			&& TaskNumber == other.getTaskNumber()
			&& SuccNumber == other.getSuccNumber()
			&& ToManageCom.equals(other.getToManageCom())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& EndTime.equals(other.getEndTime());
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
		if( strFieldName.equals("StartTime") ) {
			return 0;
		}
		if( strFieldName.equals("MoveID") ) {
			return 1;
		}
		if( strFieldName.equals("StartDate") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("TaskCode") ) {
			return 4;
		}
		if( strFieldName.equals("TaskType") ) {
			return 5;
		}
		if( strFieldName.equals("TaskNumber") ) {
			return 6;
		}
		if( strFieldName.equals("SuccNumber") ) {
			return 7;
		}
		if( strFieldName.equals("ToManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("EndDate") ) {
			return 9;
		}
		if( strFieldName.equals("EndTime") ) {
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
				strFieldName = "StartTime";
				break;
			case 1:
				strFieldName = "MoveID";
				break;
			case 2:
				strFieldName = "StartDate";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "TaskCode";
				break;
			case 5:
				strFieldName = "TaskType";
				break;
			case 6:
				strFieldName = "TaskNumber";
				break;
			case 7:
				strFieldName = "SuccNumber";
				break;
			case 8:
				strFieldName = "ToManageCom";
				break;
			case 9:
				strFieldName = "EndDate";
				break;
			case 10:
				strFieldName = "EndTime";
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
		if( strFieldName.equals("StartTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MoveID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TaskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SuccNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ToManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
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
