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
import com.sinosoft.lis.db.LDAssignPlanDB;

/*
 * <p>ClassName: LDAssignPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 自动分配
 */
public class LDAssignPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDAssignPlanSchema.class);

	// @Field
	/** 任务号 */
	private String TaskNo;
	/** 工作流节点 */
	private String Activityid;
	/** 节点名称 */
	private String ServiceName;
	/** 任务起始时间 */
	private String TaskStartTime;
	/** 任务终止时间 */
	private String TaskEndTime;
	/** 业务员 */
	private String AssignNo;
	/** 计划分配单数 */
	private int PlanAmount;
	/** 操作员 */
	private String Operator;
	/** 启动状态 */
	private String State;
	/** 生成时间 */
	private String MakeTime;
	/** 生成日期 */
	private Date MakeDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 核保级别 */
	private String UWPopedom;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDAssignPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "TaskNo";
		pk[1] = "Activityid";
		pk[2] = "AssignNo";

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
		LDAssignPlanSchema cloned = (LDAssignPlanSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getServiceName()
	{
		return ServiceName;
	}
	public void setServiceName(String aServiceName)
	{
		ServiceName = aServiceName;
	}
	public String getTaskStartTime()
	{
		return TaskStartTime;
	}
	public void setTaskStartTime(String aTaskStartTime)
	{
		TaskStartTime = aTaskStartTime;
	}
	public String getTaskEndTime()
	{
		return TaskEndTime;
	}
	public void setTaskEndTime(String aTaskEndTime)
	{
		TaskEndTime = aTaskEndTime;
	}
	public String getAssignNo()
	{
		return AssignNo;
	}
	public void setAssignNo(String aAssignNo)
	{
		AssignNo = aAssignNo;
	}
	public int getPlanAmount()
	{
		return PlanAmount;
	}
	public void setPlanAmount(int aPlanAmount)
	{
		PlanAmount = aPlanAmount;
	}
	public void setPlanAmount(String aPlanAmount)
	{
		if (aPlanAmount != null && !aPlanAmount.equals(""))
		{
			Integer tInteger = new Integer(aPlanAmount);
			int i = tInteger.intValue();
			PlanAmount = i;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 0-启动<p>
	* 1-终止
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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

	public String getUWPopedom()
	{
		return UWPopedom;
	}
	public void setUWPopedom(String aUWPopedom)
	{
		UWPopedom = aUWPopedom;
	}

	/**
	* 使用另外一个 LDAssignPlanSchema 对象给 Schema 赋值
	* @param: aLDAssignPlanSchema LDAssignPlanSchema
	**/
	public void setSchema(LDAssignPlanSchema aLDAssignPlanSchema)
	{
		this.TaskNo = aLDAssignPlanSchema.getTaskNo();
		this.Activityid = aLDAssignPlanSchema.getActivityid();
		this.ServiceName = aLDAssignPlanSchema.getServiceName();
		this.TaskStartTime = aLDAssignPlanSchema.getTaskStartTime();
		this.TaskEndTime = aLDAssignPlanSchema.getTaskEndTime();
		this.AssignNo = aLDAssignPlanSchema.getAssignNo();
		this.PlanAmount = aLDAssignPlanSchema.getPlanAmount();
		this.Operator = aLDAssignPlanSchema.getOperator();
		this.State = aLDAssignPlanSchema.getState();
		this.MakeTime = aLDAssignPlanSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLDAssignPlanSchema.getMakeDate());
		this.ModifyTime = aLDAssignPlanSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aLDAssignPlanSchema.getModifyDate());
		this.UWPopedom = aLDAssignPlanSchema.getUWPopedom();
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
			if( rs.getString("TaskNo") == null )
				this.TaskNo = null;
			else
				this.TaskNo = rs.getString("TaskNo").trim();

			if( rs.getString("Activityid") == null )
				this.Activityid = null;
			else
				this.Activityid = rs.getString("Activityid").trim();

			if( rs.getString("ServiceName") == null )
				this.ServiceName = null;
			else
				this.ServiceName = rs.getString("ServiceName").trim();

			if( rs.getString("TaskStartTime") == null )
				this.TaskStartTime = null;
			else
				this.TaskStartTime = rs.getString("TaskStartTime").trim();

			if( rs.getString("TaskEndTime") == null )
				this.TaskEndTime = null;
			else
				this.TaskEndTime = rs.getString("TaskEndTime").trim();

			if( rs.getString("AssignNo") == null )
				this.AssignNo = null;
			else
				this.AssignNo = rs.getString("AssignNo").trim();

			this.PlanAmount = rs.getInt("PlanAmount");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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
			if( rs.getString("UWPopedom") == null )
				this.UWPopedom = null;
			else
				this.UWPopedom = rs.getString("UWPopedom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDAssignPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAssignPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDAssignPlanSchema getSchema()
	{
		LDAssignPlanSchema aLDAssignPlanSchema = new LDAssignPlanSchema();
		aLDAssignPlanSchema.setSchema(this);
		return aLDAssignPlanSchema;
	}

	public LDAssignPlanDB getDB()
	{
		LDAssignPlanDB aDBOper = new LDAssignPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAssignPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TaskNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Activityid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServiceName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskStartTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskEndTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssignNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PlanAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAssignPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TaskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Activityid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ServiceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TaskStartTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TaskEndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AssignNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PlanAmount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			UWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAssignPlanSchema";
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
		if (FCode.equalsIgnoreCase("TaskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskNo));
		}
		if (FCode.equalsIgnoreCase("Activityid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Activityid));
		}
		if (FCode.equalsIgnoreCase("ServiceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServiceName));
		}
		if (FCode.equalsIgnoreCase("TaskStartTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskStartTime));
		}
		if (FCode.equalsIgnoreCase("TaskEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskEndTime));
		}
		if (FCode.equalsIgnoreCase("AssignNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssignNo));
		}
		if (FCode.equalsIgnoreCase("PlanAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanAmount));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedom));
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
				strFieldValue = StrTool.GBKToUnicode(TaskNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Activityid);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ServiceName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TaskStartTime);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TaskEndTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AssignNo);
				break;
			case 6:
				strFieldValue = String.valueOf(PlanAmount);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(UWPopedom);
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
		if (FCode.equalsIgnoreCase("ServiceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServiceName = FValue.trim();
			}
			else
				ServiceName = null;
		}
		if (FCode.equalsIgnoreCase("TaskStartTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskStartTime = FValue.trim();
			}
			else
				TaskStartTime = null;
		}
		if (FCode.equalsIgnoreCase("TaskEndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskEndTime = FValue.trim();
			}
			else
				TaskEndTime = null;
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
		if (FCode.equalsIgnoreCase("PlanAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PlanAmount = i;
			}
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedom = FValue.trim();
			}
			else
				UWPopedom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDAssignPlanSchema other = (LDAssignPlanSchema)otherObject;
		return
			TaskNo.equals(other.getTaskNo())
			&& Activityid.equals(other.getActivityid())
			&& ServiceName.equals(other.getServiceName())
			&& TaskStartTime.equals(other.getTaskStartTime())
			&& TaskEndTime.equals(other.getTaskEndTime())
			&& AssignNo.equals(other.getAssignNo())
			&& PlanAmount == other.getPlanAmount()
			&& Operator.equals(other.getOperator())
			&& State.equals(other.getState())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& UWPopedom.equals(other.getUWPopedom());
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
		if( strFieldName.equals("TaskNo") ) {
			return 0;
		}
		if( strFieldName.equals("Activityid") ) {
			return 1;
		}
		if( strFieldName.equals("ServiceName") ) {
			return 2;
		}
		if( strFieldName.equals("TaskStartTime") ) {
			return 3;
		}
		if( strFieldName.equals("TaskEndTime") ) {
			return 4;
		}
		if( strFieldName.equals("AssignNo") ) {
			return 5;
		}
		if( strFieldName.equals("PlanAmount") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return 13;
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
				strFieldName = "TaskNo";
				break;
			case 1:
				strFieldName = "Activityid";
				break;
			case 2:
				strFieldName = "ServiceName";
				break;
			case 3:
				strFieldName = "TaskStartTime";
				break;
			case 4:
				strFieldName = "TaskEndTime";
				break;
			case 5:
				strFieldName = "AssignNo";
				break;
			case 6:
				strFieldName = "PlanAmount";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "UWPopedom";
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
		if( strFieldName.equals("TaskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Activityid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServiceName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskStartTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskEndTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssignNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanAmount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("UWPopedom") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
