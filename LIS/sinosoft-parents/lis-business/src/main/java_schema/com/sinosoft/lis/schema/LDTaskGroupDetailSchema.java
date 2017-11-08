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
import com.sinosoft.lis.db.LDTaskGroupDetailDB;

/*
 * <p>ClassName: LDTaskGroupDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LDTaskGroupDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDTaskGroupDetailSchema.class);

	// @Field
	/** 任务组代码 */
	private String TaskGroupCode;
	/** 任务代码 */
	private String TaskCode;
	/** 依赖任务代码 */
	private String DependTaskCode;
	/** 描述 */
	private String Describe;
	/** 操作员 */
	private String Operator;
	/** 录入日期 */
	private Date MakeDate;
	/** 录入时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 依赖类型 */
	private String DependType;
	/** 失败后动作 */
	private String ActionAfterFail;
	/** 执行顺序 */
	private int TaskOrder;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDTaskGroupDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "TaskGroupCode";
		pk[1] = "TaskCode";
		pk[2] = "DependTaskCode";

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
		LDTaskGroupDetailSchema cloned = (LDTaskGroupDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTaskGroupCode()
	{
		return TaskGroupCode;
	}
	public void setTaskGroupCode(String aTaskGroupCode)
	{
		if(aTaskGroupCode!=null && aTaskGroupCode.length()>6)
			throw new IllegalArgumentException("任务组代码TaskGroupCode值"+aTaskGroupCode+"的长度"+aTaskGroupCode.length()+"大于最大值6");
		TaskGroupCode = aTaskGroupCode;
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
	public String getDependTaskCode()
	{
		return DependTaskCode;
	}
	public void setDependTaskCode(String aDependTaskCode)
	{
		if(aDependTaskCode!=null && aDependTaskCode.length()>6)
			throw new IllegalArgumentException("依赖任务代码DependTaskCode值"+aDependTaskCode+"的长度"+aDependTaskCode.length()+"大于最大值6");
		DependTaskCode = aDependTaskCode;
	}
	public String getDescribe()
	{
		return Describe;
	}
	public void setDescribe(String aDescribe)
	{
		if(aDescribe!=null && aDescribe.length()>50)
			throw new IllegalArgumentException("描述Describe值"+aDescribe+"的长度"+aDescribe.length()+"大于最大值50");
		Describe = aDescribe;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>8)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值8");
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
	* 00 - 无依赖<p>
	* 01 - 父任务需要正确执行完毕才执行<p>
	* 02 - 父任务只要执行完毕即可执行<p>
	* <p>
	* <p>
	* FinishFinish（FF）、FinishStart（FS）、StartFinish（SF）和StartStart（SS）<p>
	* 可以参考Project 定义前置任务的方式.
	*/
	public String getDependType()
	{
		return DependType;
	}
	public void setDependType(String aDependType)
	{
		if(aDependType!=null && aDependType.length()>2)
			throw new IllegalArgumentException("依赖类型DependType值"+aDependType+"的长度"+aDependType.length()+"大于最大值2");
		DependType = aDependType;
	}
	/**
	* 00 - 无动作<p>
	* 01 - 挂起(挂起后,该任务在解挂前不能再次运行)
	*/
	public String getActionAfterFail()
	{
		return ActionAfterFail;
	}
	public void setActionAfterFail(String aActionAfterFail)
	{
		if(aActionAfterFail!=null && aActionAfterFail.length()>2)
			throw new IllegalArgumentException("失败后动作ActionAfterFail值"+aActionAfterFail+"的长度"+aActionAfterFail.length()+"大于最大值2");
		ActionAfterFail = aActionAfterFail;
	}
	public int getTaskOrder()
	{
		return TaskOrder;
	}
	public void setTaskOrder(int aTaskOrder)
	{
		TaskOrder = aTaskOrder;
	}
	public void setTaskOrder(String aTaskOrder)
	{
		if (aTaskOrder != null && !aTaskOrder.equals(""))
		{
			Integer tInteger = new Integer(aTaskOrder);
			int i = tInteger.intValue();
			TaskOrder = i;
		}
	}


	/**
	* 使用另外一个 LDTaskGroupDetailSchema 对象给 Schema 赋值
	* @param: aLDTaskGroupDetailSchema LDTaskGroupDetailSchema
	**/
	public void setSchema(LDTaskGroupDetailSchema aLDTaskGroupDetailSchema)
	{
		this.TaskGroupCode = aLDTaskGroupDetailSchema.getTaskGroupCode();
		this.TaskCode = aLDTaskGroupDetailSchema.getTaskCode();
		this.DependTaskCode = aLDTaskGroupDetailSchema.getDependTaskCode();
		this.Describe = aLDTaskGroupDetailSchema.getDescribe();
		this.Operator = aLDTaskGroupDetailSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDTaskGroupDetailSchema.getMakeDate());
		this.MakeTime = aLDTaskGroupDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDTaskGroupDetailSchema.getModifyDate());
		this.ModifyTime = aLDTaskGroupDetailSchema.getModifyTime();
		this.DependType = aLDTaskGroupDetailSchema.getDependType();
		this.ActionAfterFail = aLDTaskGroupDetailSchema.getActionAfterFail();
		this.TaskOrder = aLDTaskGroupDetailSchema.getTaskOrder();
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
			if( rs.getString("TaskGroupCode") == null )
				this.TaskGroupCode = null;
			else
				this.TaskGroupCode = rs.getString("TaskGroupCode").trim();

			if( rs.getString("TaskCode") == null )
				this.TaskCode = null;
			else
				this.TaskCode = rs.getString("TaskCode").trim();

			if( rs.getString("DependTaskCode") == null )
				this.DependTaskCode = null;
			else
				this.DependTaskCode = rs.getString("DependTaskCode").trim();

			if( rs.getString("Describe") == null )
				this.Describe = null;
			else
				this.Describe = rs.getString("Describe").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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

			if( rs.getString("DependType") == null )
				this.DependType = null;
			else
				this.DependType = rs.getString("DependType").trim();

			if( rs.getString("ActionAfterFail") == null )
				this.ActionAfterFail = null;
			else
				this.ActionAfterFail = rs.getString("ActionAfterFail").trim();

			this.TaskOrder = rs.getInt("TaskOrder");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDTaskGroupDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskGroupDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDTaskGroupDetailSchema getSchema()
	{
		LDTaskGroupDetailSchema aLDTaskGroupDetailSchema = new LDTaskGroupDetailSchema();
		aLDTaskGroupDetailSchema.setSchema(this);
		return aLDTaskGroupDetailSchema;
	}

	public LDTaskGroupDetailDB getDB()
	{
		LDTaskGroupDetailDB aDBOper = new LDTaskGroupDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskGroupDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TaskGroupCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DependTaskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Describe)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DependType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionAfterFail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaskOrder));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskGroupDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TaskGroupCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TaskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DependTaskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Describe = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DependType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ActionAfterFail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TaskOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskGroupDetailSchema";
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
		if (FCode.equalsIgnoreCase("TaskGroupCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskGroupCode));
		}
		if (FCode.equalsIgnoreCase("TaskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskCode));
		}
		if (FCode.equalsIgnoreCase("DependTaskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DependTaskCode));
		}
		if (FCode.equalsIgnoreCase("Describe"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Describe));
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("DependType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DependType));
		}
		if (FCode.equalsIgnoreCase("ActionAfterFail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionAfterFail));
		}
		if (FCode.equalsIgnoreCase("TaskOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskOrder));
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
				strFieldValue = StrTool.GBKToUnicode(TaskGroupCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TaskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DependTaskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Describe);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DependType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ActionAfterFail);
				break;
			case 11:
				strFieldValue = String.valueOf(TaskOrder);
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

		if (FCode.equalsIgnoreCase("TaskGroupCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskGroupCode = FValue.trim();
			}
			else
				TaskGroupCode = null;
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
		if (FCode.equalsIgnoreCase("DependTaskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DependTaskCode = FValue.trim();
			}
			else
				DependTaskCode = null;
		}
		if (FCode.equalsIgnoreCase("Describe"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Describe = FValue.trim();
			}
			else
				Describe = null;
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
		if (FCode.equalsIgnoreCase("DependType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DependType = FValue.trim();
			}
			else
				DependType = null;
		}
		if (FCode.equalsIgnoreCase("ActionAfterFail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionAfterFail = FValue.trim();
			}
			else
				ActionAfterFail = null;
		}
		if (FCode.equalsIgnoreCase("TaskOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TaskOrder = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDTaskGroupDetailSchema other = (LDTaskGroupDetailSchema)otherObject;
		return
			TaskGroupCode.equals(other.getTaskGroupCode())
			&& TaskCode.equals(other.getTaskCode())
			&& DependTaskCode.equals(other.getDependTaskCode())
			&& Describe.equals(other.getDescribe())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& DependType.equals(other.getDependType())
			&& ActionAfterFail.equals(other.getActionAfterFail())
			&& TaskOrder == other.getTaskOrder();
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
		if( strFieldName.equals("TaskGroupCode") ) {
			return 0;
		}
		if( strFieldName.equals("TaskCode") ) {
			return 1;
		}
		if( strFieldName.equals("DependTaskCode") ) {
			return 2;
		}
		if( strFieldName.equals("Describe") ) {
			return 3;
		}
		if( strFieldName.equals("Operator") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 8;
		}
		if( strFieldName.equals("DependType") ) {
			return 9;
		}
		if( strFieldName.equals("ActionAfterFail") ) {
			return 10;
		}
		if( strFieldName.equals("TaskOrder") ) {
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
				strFieldName = "TaskGroupCode";
				break;
			case 1:
				strFieldName = "TaskCode";
				break;
			case 2:
				strFieldName = "DependTaskCode";
				break;
			case 3:
				strFieldName = "Describe";
				break;
			case 4:
				strFieldName = "Operator";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "ModifyDate";
				break;
			case 8:
				strFieldName = "ModifyTime";
				break;
			case 9:
				strFieldName = "DependType";
				break;
			case 10:
				strFieldName = "ActionAfterFail";
				break;
			case 11:
				strFieldName = "TaskOrder";
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
		if( strFieldName.equals("TaskGroupCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DependTaskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Describe") ) {
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
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DependType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionAfterFail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskOrder") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
