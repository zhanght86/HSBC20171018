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
import com.sinosoft.lis.db.LWActivityDB;

/*
 * <p>ClassName: LWActivitySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWActivitySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWActivitySchema.class);
	// @Field
	/** 活动id */
	private String ActivityID;
	/** 活动名 */
	private String ActivityName;
	/** 活动类型 */
	private String ActivityType;
	/** 活动说明 */
	private String ActivityDesc;
	/** 活动进入前动作 */
	private String BeforeInit;
	/** 活动进入前动作类型 */
	private String BeforeInitType;
	/** 活动进入后动作 */
	private String AfterInit;
	/** 活动进入后动作类型 */
	private String AfterInitType;
	/** 活动结束前动作 */
	private String BeforeEnd;
	/** 活动结束前动作类型 */
	private String BeforeEndType;
	/** 活动结束后动作 */
	private String AfterEnd;
	/** 活动结束后动作类型 */
	private String AfterEndType;
	/** 平均等待时间 */
	private int WatingTime;
	/** 平均执行时间 */
	private int WorkingTime;
	/** 超时时间 */
	private int TimeOut;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 业务类型 */
	private String BusiType;
	/** 活动属性 */
	private String IsNeed;
	/** 活动标志 */
	private String ActivityFlag;
	/** 重要等级 */
	private int ImpDegree;
	/** 功能节点号 */
	private String FunctionID;
	/** 创建动作 */
	private String CreateAction;
	/** 创建动作类型 */
	private String CreateActionType;
	/** 分配动作 */
	private String ApplyAction;
	/** 分配动作类型 */
	private String ApplyActionType;
	/** 删除动作 */
	private String DeleteAction;
	/** 删除动作类型 */
	private String DeleteActionType;
	/** 聚合模式 */
	private String Together;
	/** 菜单节点 */
	private String MenuNodeCode;

	public static final int FIELDNUM = 33;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWActivitySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ActivityID";

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
		LWActivitySchema cloned = (LWActivitySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("活动idActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	public String getActivityName()
	{
		return ActivityName;
	}
	public void setActivityName(String aActivityName)
	{
		if(aActivityName!=null && aActivityName.length()>50)
			throw new IllegalArgumentException("活动名ActivityName值"+aActivityName+"的长度"+aActivityName.length()+"大于最大值50");
		ActivityName = aActivityName;
	}
	/**
	* 1	自动分支活动：自动分支活动主要是为了解决在一个活动完成后，需要同时转移到几个其他的活动中。<p>
	* 2	自动等待活动：自动等待活动一般和自动分支活动配合使用，当某个活动同时转移到多个活动后，往往需要等待其他活动同时完成后才能继续执行。<p>
	* 3	子工作流活动：子工作流活动指将一系列活动包含在一起的活动，实际上该活动本身是一个工作流，这种子工作流活动只能有一个入口，但可以有多个出口。<p>
	* 4	可选活动：这些活动如果在上一活动产生的输出条件不能满足该活动的输入条件时，工作流引擎认为这种过程是正确的，允许进行活动的转移。如果该活动为必要活动，<p>
	* 则工作流引擎将认为该转移是不正确的，不允许进行转移。如果可选活动的后续活动为自动等待活动，则自动等待活动将会自动判断，如果在本次任务中没有该非必要<p>
	* 活动，自动等待程序将不会等待该活动的结束。<p>
	* 5	必要活动：这些活动一旦包含在工作流中，则这些活动是必须执行的。
	*/
	public String getActivityType()
	{
		return ActivityType;
	}
	public void setActivityType(String aActivityType)
	{
		if(aActivityType!=null && aActivityType.length()>1)
			throw new IllegalArgumentException("活动类型ActivityType值"+aActivityType+"的长度"+aActivityType.length()+"大于最大值1");
		ActivityType = aActivityType;
	}
	public String getActivityDesc()
	{
		return ActivityDesc;
	}
	public void setActivityDesc(String aActivityDesc)
	{
		if(aActivityDesc!=null && aActivityDesc.length()>1000)
			throw new IllegalArgumentException("活动说明ActivityDesc值"+aActivityDesc+"的长度"+aActivityDesc.length()+"大于最大值1000");
		ActivityDesc = aActivityDesc;
	}
	public String getBeforeInit()
	{
		return BeforeInit;
	}
	public void setBeforeInit(String aBeforeInit)
	{
		if(aBeforeInit!=null && aBeforeInit.length()>1000)
			throw new IllegalArgumentException("活动进入前动作BeforeInit值"+aBeforeInit+"的长度"+aBeforeInit.length()+"大于最大值1000");
		BeforeInit = aBeforeInit;
	}
	/**
	* 0 -- 默认，表示动作是一个SQL语句<p>
	* 1 -- 表示调用的是一个类<p>
	* 如果该字段为空，表示没有需要执行的动作，只要转移就可以。
	*/
	public String getBeforeInitType()
	{
		return BeforeInitType;
	}
	public void setBeforeInitType(String aBeforeInitType)
	{
		if(aBeforeInitType!=null && aBeforeInitType.length()>1)
			throw new IllegalArgumentException("活动进入前动作类型BeforeInitType值"+aBeforeInitType+"的长度"+aBeforeInitType.length()+"大于最大值1");
		BeforeInitType = aBeforeInitType;
	}
	public String getAfterInit()
	{
		return AfterInit;
	}
	public void setAfterInit(String aAfterInit)
	{
		if(aAfterInit!=null && aAfterInit.length()>1000)
			throw new IllegalArgumentException("活动进入后动作AfterInit值"+aAfterInit+"的长度"+aAfterInit.length()+"大于最大值1000");
		AfterInit = aAfterInit;
	}
	/**
	* 0 -- 默认，表示动作是一个SQL语句<p>
	* 1 -- 表示调用的是一个类<p>
	* 如果该字段为空，表示没有需要执行的动作，只要转移就可以。
	*/
	public String getAfterInitType()
	{
		return AfterInitType;
	}
	public void setAfterInitType(String aAfterInitType)
	{
		if(aAfterInitType!=null && aAfterInitType.length()>1)
			throw new IllegalArgumentException("活动进入后动作类型AfterInitType值"+aAfterInitType+"的长度"+aAfterInitType.length()+"大于最大值1");
		AfterInitType = aAfterInitType;
	}
	public String getBeforeEnd()
	{
		return BeforeEnd;
	}
	public void setBeforeEnd(String aBeforeEnd)
	{
		if(aBeforeEnd!=null && aBeforeEnd.length()>1000)
			throw new IllegalArgumentException("活动结束前动作BeforeEnd值"+aBeforeEnd+"的长度"+aBeforeEnd.length()+"大于最大值1000");
		BeforeEnd = aBeforeEnd;
	}
	/**
	* 0 -- 默认，表示动作是一个SQL语句<p>
	* 1 -- 表示调用的是一个类<p>
	* 如果该字段为空，表示没有需要执行的动作，只要转移就可以。
	*/
	public String getBeforeEndType()
	{
		return BeforeEndType;
	}
	public void setBeforeEndType(String aBeforeEndType)
	{
		if(aBeforeEndType!=null && aBeforeEndType.length()>1)
			throw new IllegalArgumentException("活动结束前动作类型BeforeEndType值"+aBeforeEndType+"的长度"+aBeforeEndType.length()+"大于最大值1");
		BeforeEndType = aBeforeEndType;
	}
	public String getAfterEnd()
	{
		return AfterEnd;
	}
	public void setAfterEnd(String aAfterEnd)
	{
		if(aAfterEnd!=null && aAfterEnd.length()>1000)
			throw new IllegalArgumentException("活动结束后动作AfterEnd值"+aAfterEnd+"的长度"+aAfterEnd.length()+"大于最大值1000");
		AfterEnd = aAfterEnd;
	}
	/**
	* 0 -- 默认，表示动作是一个SQL语句<p>
	* 1 -- 表示调用的是一个类<p>
	* 如果该字段为空，表示没有需要执行的动作，只要转移就可以。
	*/
	public String getAfterEndType()
	{
		return AfterEndType;
	}
	public void setAfterEndType(String aAfterEndType)
	{
		if(aAfterEndType!=null && aAfterEndType.length()>1)
			throw new IllegalArgumentException("活动结束后动作类型AfterEndType值"+aAfterEndType+"的长度"+aAfterEndType.length()+"大于最大值1");
		AfterEndType = aAfterEndType;
	}
	public int getWatingTime()
	{
		return WatingTime;
	}
	public void setWatingTime(int aWatingTime)
	{
		WatingTime = aWatingTime;
	}
	public void setWatingTime(String aWatingTime)
	{
		if (aWatingTime != null && !aWatingTime.equals(""))
		{
			Integer tInteger = new Integer(aWatingTime);
			int i = tInteger.intValue();
			WatingTime = i;
		}
	}

	public int getWorkingTime()
	{
		return WorkingTime;
	}
	public void setWorkingTime(int aWorkingTime)
	{
		WorkingTime = aWorkingTime;
	}
	public void setWorkingTime(String aWorkingTime)
	{
		if (aWorkingTime != null && !aWorkingTime.equals(""))
		{
			Integer tInteger = new Integer(aWorkingTime);
			int i = tInteger.intValue();
			WorkingTime = i;
		}
	}

	public int getTimeOut()
	{
		return TimeOut;
	}
	public void setTimeOut(int aTimeOut)
	{
		TimeOut = aTimeOut;
	}
	public void setTimeOut(String aTimeOut)
	{
		if (aTimeOut != null && !aTimeOut.equals(""))
		{
			Integer tInteger = new Integer(aTimeOut);
			int i = tInteger.intValue();
			TimeOut = i;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员代码Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	/**
	* 1--账管系统<p>
	* 2--受托系统
	*/
	public String getBusiType()
	{
		return BusiType;
	}
	public void setBusiType(String aBusiType)
	{
		if(aBusiType!=null && aBusiType.length()>4)
			throw new IllegalArgumentException("业务类型BusiType值"+aBusiType+"的长度"+aBusiType.length()+"大于最大值4");
		BusiType = aBusiType;
	}
	/**
	* Y--必需节点<p>
	* N--非必需节点
	*/
	public String getIsNeed()
	{
		return IsNeed;
	}
	public void setIsNeed(String aIsNeed)
	{
		if(aIsNeed!=null && aIsNeed.length()>2)
			throw new IllegalArgumentException("活动属性IsNeed值"+aIsNeed+"的长度"+aIsNeed.length()+"大于最大值2");
		IsNeed = aIsNeed;
	}
	/**
	* 1-头节点<p>
	* 2-中间节点<p>
	* 3-尾节点
	*/
	public String getActivityFlag()
	{
		return ActivityFlag;
	}
	public void setActivityFlag(String aActivityFlag)
	{
		if(aActivityFlag!=null && aActivityFlag.length()>2)
			throw new IllegalArgumentException("活动标志ActivityFlag值"+aActivityFlag+"的长度"+aActivityFlag.length()+"大于最大值2");
		ActivityFlag = aActivityFlag;
	}
	public int getImpDegree()
	{
		return ImpDegree;
	}
	public void setImpDegree(int aImpDegree)
	{
		ImpDegree = aImpDegree;
	}
	public void setImpDegree(String aImpDegree)
	{
		if (aImpDegree != null && !aImpDegree.equals(""))
		{
			Integer tInteger = new Integer(aImpDegree);
			int i = tInteger.intValue();
			ImpDegree = i;
		}
	}

	public String getFunctionID()
	{
		return FunctionID;
	}
	public void setFunctionID(String aFunctionID)
	{
		if(aFunctionID!=null && aFunctionID.length()>10)
			throw new IllegalArgumentException("功能节点号FunctionID值"+aFunctionID+"的长度"+aFunctionID.length()+"大于最大值10");
		FunctionID = aFunctionID;
	}
	public String getCreateAction()
	{
		return CreateAction;
	}
	public void setCreateAction(String aCreateAction)
	{
		if(aCreateAction!=null && aCreateAction.length()>1000)
			throw new IllegalArgumentException("创建动作CreateAction值"+aCreateAction+"的长度"+aCreateAction.length()+"大于最大值1000");
		CreateAction = aCreateAction;
	}
	public String getCreateActionType()
	{
		return CreateActionType;
	}
	public void setCreateActionType(String aCreateActionType)
	{
		if(aCreateActionType!=null && aCreateActionType.length()>1)
			throw new IllegalArgumentException("创建动作类型CreateActionType值"+aCreateActionType+"的长度"+aCreateActionType.length()+"大于最大值1");
		CreateActionType = aCreateActionType;
	}
	public String getApplyAction()
	{
		return ApplyAction;
	}
	public void setApplyAction(String aApplyAction)
	{
		if(aApplyAction!=null && aApplyAction.length()>1000)
			throw new IllegalArgumentException("分配动作ApplyAction值"+aApplyAction+"的长度"+aApplyAction.length()+"大于最大值1000");
		ApplyAction = aApplyAction;
	}
	public String getApplyActionType()
	{
		return ApplyActionType;
	}
	public void setApplyActionType(String aApplyActionType)
	{
		if(aApplyActionType!=null && aApplyActionType.length()>1)
			throw new IllegalArgumentException("分配动作类型ApplyActionType值"+aApplyActionType+"的长度"+aApplyActionType.length()+"大于最大值1");
		ApplyActionType = aApplyActionType;
	}
	public String getDeleteAction()
	{
		return DeleteAction;
	}
	public void setDeleteAction(String aDeleteAction)
	{
		if(aDeleteAction!=null && aDeleteAction.length()>1000)
			throw new IllegalArgumentException("删除动作DeleteAction值"+aDeleteAction+"的长度"+aDeleteAction.length()+"大于最大值1000");
		DeleteAction = aDeleteAction;
	}
	public String getDeleteActionType()
	{
		return DeleteActionType;
	}
	public void setDeleteActionType(String aDeleteActionType)
	{
		if(aDeleteActionType!=null && aDeleteActionType.length()>1)
			throw new IllegalArgumentException("删除动作类型DeleteActionType值"+aDeleteActionType+"的长度"+aDeleteActionType.length()+"大于最大值1");
		DeleteActionType = aDeleteActionType;
	}
	public String getTogether()
	{
		return Together;
	}
	public void setTogether(String aTogether)
	{
		if(aTogether!=null && aTogether.length()>2)
			throw new IllegalArgumentException("聚合模式Together值"+aTogether+"的长度"+aTogether.length()+"大于最大值2");
		Together = aTogether;
	}
	public String getMenuNodeCode()
	{
		return MenuNodeCode;
	}
	public void setMenuNodeCode(String aMenuNodeCode)
	{
		if(aMenuNodeCode!=null && aMenuNodeCode.length()>5)
			throw new IllegalArgumentException("菜单节点MenuNodeCode值"+aMenuNodeCode+"的长度"+aMenuNodeCode.length()+"大于最大值5");
		MenuNodeCode = aMenuNodeCode;
	}

	/**
	* 使用另外一个 LWActivitySchema 对象给 Schema 赋值
	* @param: aLWActivitySchema LWActivitySchema
	**/
	public void setSchema(LWActivitySchema aLWActivitySchema)
	{
		this.ActivityID = aLWActivitySchema.getActivityID();
		this.ActivityName = aLWActivitySchema.getActivityName();
		this.ActivityType = aLWActivitySchema.getActivityType();
		this.ActivityDesc = aLWActivitySchema.getActivityDesc();
		this.BeforeInit = aLWActivitySchema.getBeforeInit();
		this.BeforeInitType = aLWActivitySchema.getBeforeInitType();
		this.AfterInit = aLWActivitySchema.getAfterInit();
		this.AfterInitType = aLWActivitySchema.getAfterInitType();
		this.BeforeEnd = aLWActivitySchema.getBeforeEnd();
		this.BeforeEndType = aLWActivitySchema.getBeforeEndType();
		this.AfterEnd = aLWActivitySchema.getAfterEnd();
		this.AfterEndType = aLWActivitySchema.getAfterEndType();
		this.WatingTime = aLWActivitySchema.getWatingTime();
		this.WorkingTime = aLWActivitySchema.getWorkingTime();
		this.TimeOut = aLWActivitySchema.getTimeOut();
		this.Operator = aLWActivitySchema.getOperator();
		this.MakeDate = fDate.getDate( aLWActivitySchema.getMakeDate());
		this.MakeTime = aLWActivitySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLWActivitySchema.getModifyDate());
		this.ModifyTime = aLWActivitySchema.getModifyTime();
		this.BusiType = aLWActivitySchema.getBusiType();
		this.IsNeed = aLWActivitySchema.getIsNeed();
		this.ActivityFlag = aLWActivitySchema.getActivityFlag();
		this.ImpDegree = aLWActivitySchema.getImpDegree();
		this.FunctionID = aLWActivitySchema.getFunctionID();
		this.CreateAction = aLWActivitySchema.getCreateAction();
		this.CreateActionType = aLWActivitySchema.getCreateActionType();
		this.ApplyAction = aLWActivitySchema.getApplyAction();
		this.ApplyActionType = aLWActivitySchema.getApplyActionType();
		this.DeleteAction = aLWActivitySchema.getDeleteAction();
		this.DeleteActionType = aLWActivitySchema.getDeleteActionType();
		this.Together = aLWActivitySchema.getTogether();
		this.MenuNodeCode = aLWActivitySchema.getMenuNodeCode();
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
			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			if( rs.getString("ActivityName") == null )
				this.ActivityName = null;
			else
				this.ActivityName = rs.getString("ActivityName").trim();

			if( rs.getString("ActivityType") == null )
				this.ActivityType = null;
			else
				this.ActivityType = rs.getString("ActivityType").trim();

			if( rs.getString("ActivityDesc") == null )
				this.ActivityDesc = null;
			else
				this.ActivityDesc = rs.getString("ActivityDesc").trim();

			if( rs.getString("BeforeInit") == null )
				this.BeforeInit = null;
			else
				this.BeforeInit = rs.getString("BeforeInit").trim();

			if( rs.getString("BeforeInitType") == null )
				this.BeforeInitType = null;
			else
				this.BeforeInitType = rs.getString("BeforeInitType").trim();

			if( rs.getString("AfterInit") == null )
				this.AfterInit = null;
			else
				this.AfterInit = rs.getString("AfterInit").trim();

			if( rs.getString("AfterInitType") == null )
				this.AfterInitType = null;
			else
				this.AfterInitType = rs.getString("AfterInitType").trim();

			if( rs.getString("BeforeEnd") == null )
				this.BeforeEnd = null;
			else
				this.BeforeEnd = rs.getString("BeforeEnd").trim();

			if( rs.getString("BeforeEndType") == null )
				this.BeforeEndType = null;
			else
				this.BeforeEndType = rs.getString("BeforeEndType").trim();

			if( rs.getString("AfterEnd") == null )
				this.AfterEnd = null;
			else
				this.AfterEnd = rs.getString("AfterEnd").trim();

			if( rs.getString("AfterEndType") == null )
				this.AfterEndType = null;
			else
				this.AfterEndType = rs.getString("AfterEndType").trim();

			this.WatingTime = rs.getInt("WatingTime");
			this.WorkingTime = rs.getInt("WorkingTime");
			this.TimeOut = rs.getInt("TimeOut");
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

			if( rs.getString("BusiType") == null )
				this.BusiType = null;
			else
				this.BusiType = rs.getString("BusiType").trim();

			if( rs.getString("IsNeed") == null )
				this.IsNeed = null;
			else
				this.IsNeed = rs.getString("IsNeed").trim();

			if( rs.getString("ActivityFlag") == null )
				this.ActivityFlag = null;
			else
				this.ActivityFlag = rs.getString("ActivityFlag").trim();

			this.ImpDegree = rs.getInt("ImpDegree");
			if( rs.getString("FunctionID") == null )
				this.FunctionID = null;
			else
				this.FunctionID = rs.getString("FunctionID").trim();

			if( rs.getString("CreateAction") == null )
				this.CreateAction = null;
			else
				this.CreateAction = rs.getString("CreateAction").trim();

			if( rs.getString("CreateActionType") == null )
				this.CreateActionType = null;
			else
				this.CreateActionType = rs.getString("CreateActionType").trim();

			if( rs.getString("ApplyAction") == null )
				this.ApplyAction = null;
			else
				this.ApplyAction = rs.getString("ApplyAction").trim();

			if( rs.getString("ApplyActionType") == null )
				this.ApplyActionType = null;
			else
				this.ApplyActionType = rs.getString("ApplyActionType").trim();

			if( rs.getString("DeleteAction") == null )
				this.DeleteAction = null;
			else
				this.DeleteAction = rs.getString("DeleteAction").trim();

			if( rs.getString("DeleteActionType") == null )
				this.DeleteActionType = null;
			else
				this.DeleteActionType = rs.getString("DeleteActionType").trim();

			if( rs.getString("Together") == null )
				this.Together = null;
			else
				this.Together = rs.getString("Together").trim();

			if( rs.getString("MenuNodeCode") == null )
				this.MenuNodeCode = null;
			else
				this.MenuNodeCode = rs.getString("MenuNodeCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWActivity表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivitySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWActivitySchema getSchema()
	{
		LWActivitySchema aLWActivitySchema = new LWActivitySchema();
		aLWActivitySchema.setSchema(this);
		return aLWActivitySchema;
	}

	public LWActivityDB getDB()
	{
		LWActivityDB aDBOper = new LWActivityDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWActivity描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BeforeInit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BeforeInitType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterInit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterInitType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BeforeEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BeforeEndType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterEndType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WatingTime));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WorkingTime));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TimeOut));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsNeed)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ImpDegree));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FunctionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreateAction)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreateActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyAction)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeleteAction)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeleteActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Together)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MenuNodeCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWActivity>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ActivityName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ActivityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ActivityDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BeforeInit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BeforeInitType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AfterInit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AfterInitType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BeforeEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BeforeEndType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AfterEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AfterEndType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			WatingTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			WorkingTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			TimeOut= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			BusiType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			IsNeed = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ActivityFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ImpDegree= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			FunctionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			CreateAction = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CreateActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ApplyAction = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ApplyActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			DeleteAction = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			DeleteActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Together = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			MenuNodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWActivitySchema";
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
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("ActivityName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityName));
		}
		if (FCode.equalsIgnoreCase("ActivityType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityType));
		}
		if (FCode.equalsIgnoreCase("ActivityDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityDesc));
		}
		if (FCode.equalsIgnoreCase("BeforeInit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforeInit));
		}
		if (FCode.equalsIgnoreCase("BeforeInitType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforeInitType));
		}
		if (FCode.equalsIgnoreCase("AfterInit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterInit));
		}
		if (FCode.equalsIgnoreCase("AfterInitType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterInitType));
		}
		if (FCode.equalsIgnoreCase("BeforeEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforeEnd));
		}
		if (FCode.equalsIgnoreCase("BeforeEndType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforeEndType));
		}
		if (FCode.equalsIgnoreCase("AfterEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterEnd));
		}
		if (FCode.equalsIgnoreCase("AfterEndType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterEndType));
		}
		if (FCode.equalsIgnoreCase("WatingTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WatingTime));
		}
		if (FCode.equalsIgnoreCase("WorkingTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkingTime));
		}
		if (FCode.equalsIgnoreCase("TimeOut"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TimeOut));
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
		if (FCode.equalsIgnoreCase("BusiType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiType));
		}
		if (FCode.equalsIgnoreCase("IsNeed"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsNeed));
		}
		if (FCode.equalsIgnoreCase("ActivityFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityFlag));
		}
		if (FCode.equalsIgnoreCase("ImpDegree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpDegree));
		}
		if (FCode.equalsIgnoreCase("FunctionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FunctionID));
		}
		if (FCode.equalsIgnoreCase("CreateAction"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreateAction));
		}
		if (FCode.equalsIgnoreCase("CreateActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreateActionType));
		}
		if (FCode.equalsIgnoreCase("ApplyAction"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyAction));
		}
		if (FCode.equalsIgnoreCase("ApplyActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyActionType));
		}
		if (FCode.equalsIgnoreCase("DeleteAction"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeleteAction));
		}
		if (FCode.equalsIgnoreCase("DeleteActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeleteActionType));
		}
		if (FCode.equalsIgnoreCase("Together"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Together));
		}
		if (FCode.equalsIgnoreCase("MenuNodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MenuNodeCode));
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
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ActivityName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ActivityType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ActivityDesc);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BeforeInit);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BeforeInitType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AfterInit);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AfterInitType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BeforeEnd);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BeforeEndType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AfterEnd);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AfterEndType);
				break;
			case 12:
				strFieldValue = String.valueOf(WatingTime);
				break;
			case 13:
				strFieldValue = String.valueOf(WorkingTime);
				break;
			case 14:
				strFieldValue = String.valueOf(TimeOut);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(BusiType);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(IsNeed);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ActivityFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(ImpDegree);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(FunctionID);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(CreateAction);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(CreateActionType);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ApplyAction);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ApplyActionType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(DeleteAction);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(DeleteActionType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Together);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(MenuNodeCode);
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

		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityID = FValue.trim();
			}
			else
				ActivityID = null;
		}
		if (FCode.equalsIgnoreCase("ActivityName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityName = FValue.trim();
			}
			else
				ActivityName = null;
		}
		if (FCode.equalsIgnoreCase("ActivityType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityType = FValue.trim();
			}
			else
				ActivityType = null;
		}
		if (FCode.equalsIgnoreCase("ActivityDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityDesc = FValue.trim();
			}
			else
				ActivityDesc = null;
		}
		if (FCode.equalsIgnoreCase("BeforeInit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BeforeInit = FValue.trim();
			}
			else
				BeforeInit = null;
		}
		if (FCode.equalsIgnoreCase("BeforeInitType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BeforeInitType = FValue.trim();
			}
			else
				BeforeInitType = null;
		}
		if (FCode.equalsIgnoreCase("AfterInit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterInit = FValue.trim();
			}
			else
				AfterInit = null;
		}
		if (FCode.equalsIgnoreCase("AfterInitType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterInitType = FValue.trim();
			}
			else
				AfterInitType = null;
		}
		if (FCode.equalsIgnoreCase("BeforeEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BeforeEnd = FValue.trim();
			}
			else
				BeforeEnd = null;
		}
		if (FCode.equalsIgnoreCase("BeforeEndType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BeforeEndType = FValue.trim();
			}
			else
				BeforeEndType = null;
		}
		if (FCode.equalsIgnoreCase("AfterEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterEnd = FValue.trim();
			}
			else
				AfterEnd = null;
		}
		if (FCode.equalsIgnoreCase("AfterEndType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterEndType = FValue.trim();
			}
			else
				AfterEndType = null;
		}
		if (FCode.equalsIgnoreCase("WatingTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WatingTime = i;
			}
		}
		if (FCode.equalsIgnoreCase("WorkingTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WorkingTime = i;
			}
		}
		if (FCode.equalsIgnoreCase("TimeOut"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TimeOut = i;
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
		if (FCode.equalsIgnoreCase("BusiType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiType = FValue.trim();
			}
			else
				BusiType = null;
		}
		if (FCode.equalsIgnoreCase("IsNeed"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsNeed = FValue.trim();
			}
			else
				IsNeed = null;
		}
		if (FCode.equalsIgnoreCase("ActivityFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityFlag = FValue.trim();
			}
			else
				ActivityFlag = null;
		}
		if (FCode.equalsIgnoreCase("ImpDegree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ImpDegree = i;
			}
		}
		if (FCode.equalsIgnoreCase("FunctionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FunctionID = FValue.trim();
			}
			else
				FunctionID = null;
		}
		if (FCode.equalsIgnoreCase("CreateAction"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreateAction = FValue.trim();
			}
			else
				CreateAction = null;
		}
		if (FCode.equalsIgnoreCase("CreateActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreateActionType = FValue.trim();
			}
			else
				CreateActionType = null;
		}
		if (FCode.equalsIgnoreCase("ApplyAction"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyAction = FValue.trim();
			}
			else
				ApplyAction = null;
		}
		if (FCode.equalsIgnoreCase("ApplyActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyActionType = FValue.trim();
			}
			else
				ApplyActionType = null;
		}
		if (FCode.equalsIgnoreCase("DeleteAction"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeleteAction = FValue.trim();
			}
			else
				DeleteAction = null;
		}
		if (FCode.equalsIgnoreCase("DeleteActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeleteActionType = FValue.trim();
			}
			else
				DeleteActionType = null;
		}
		if (FCode.equalsIgnoreCase("Together"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Together = FValue.trim();
			}
			else
				Together = null;
		}
		if (FCode.equalsIgnoreCase("MenuNodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MenuNodeCode = FValue.trim();
			}
			else
				MenuNodeCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWActivitySchema other = (LWActivitySchema)otherObject;
		return
			ActivityID.equals(other.getActivityID())
			&& ActivityName.equals(other.getActivityName())
			&& ActivityType.equals(other.getActivityType())
			&& ActivityDesc.equals(other.getActivityDesc())
			&& BeforeInit.equals(other.getBeforeInit())
			&& BeforeInitType.equals(other.getBeforeInitType())
			&& AfterInit.equals(other.getAfterInit())
			&& AfterInitType.equals(other.getAfterInitType())
			&& BeforeEnd.equals(other.getBeforeEnd())
			&& BeforeEndType.equals(other.getBeforeEndType())
			&& AfterEnd.equals(other.getAfterEnd())
			&& AfterEndType.equals(other.getAfterEndType())
			&& WatingTime == other.getWatingTime()
			&& WorkingTime == other.getWorkingTime()
			&& TimeOut == other.getTimeOut()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BusiType.equals(other.getBusiType())
			&& IsNeed.equals(other.getIsNeed())
			&& ActivityFlag.equals(other.getActivityFlag())
			&& ImpDegree == other.getImpDegree()
			&& FunctionID.equals(other.getFunctionID())
			&& CreateAction.equals(other.getCreateAction())
			&& CreateActionType.equals(other.getCreateActionType())
			&& ApplyAction.equals(other.getApplyAction())
			&& ApplyActionType.equals(other.getApplyActionType())
			&& DeleteAction.equals(other.getDeleteAction())
			&& DeleteActionType.equals(other.getDeleteActionType())
			&& Together.equals(other.getTogether())
			&& MenuNodeCode.equals(other.getMenuNodeCode());
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
		if( strFieldName.equals("ActivityID") ) {
			return 0;
		}
		if( strFieldName.equals("ActivityName") ) {
			return 1;
		}
		if( strFieldName.equals("ActivityType") ) {
			return 2;
		}
		if( strFieldName.equals("ActivityDesc") ) {
			return 3;
		}
		if( strFieldName.equals("BeforeInit") ) {
			return 4;
		}
		if( strFieldName.equals("BeforeInitType") ) {
			return 5;
		}
		if( strFieldName.equals("AfterInit") ) {
			return 6;
		}
		if( strFieldName.equals("AfterInitType") ) {
			return 7;
		}
		if( strFieldName.equals("BeforeEnd") ) {
			return 8;
		}
		if( strFieldName.equals("BeforeEndType") ) {
			return 9;
		}
		if( strFieldName.equals("AfterEnd") ) {
			return 10;
		}
		if( strFieldName.equals("AfterEndType") ) {
			return 11;
		}
		if( strFieldName.equals("WatingTime") ) {
			return 12;
		}
		if( strFieldName.equals("WorkingTime") ) {
			return 13;
		}
		if( strFieldName.equals("TimeOut") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("BusiType") ) {
			return 20;
		}
		if( strFieldName.equals("IsNeed") ) {
			return 21;
		}
		if( strFieldName.equals("ActivityFlag") ) {
			return 22;
		}
		if( strFieldName.equals("ImpDegree") ) {
			return 23;
		}
		if( strFieldName.equals("FunctionID") ) {
			return 24;
		}
		if( strFieldName.equals("CreateAction") ) {
			return 25;
		}
		if( strFieldName.equals("CreateActionType") ) {
			return 26;
		}
		if( strFieldName.equals("ApplyAction") ) {
			return 27;
		}
		if( strFieldName.equals("ApplyActionType") ) {
			return 28;
		}
		if( strFieldName.equals("DeleteAction") ) {
			return 29;
		}
		if( strFieldName.equals("DeleteActionType") ) {
			return 30;
		}
		if( strFieldName.equals("Together") ) {
			return 31;
		}
		if( strFieldName.equals("MenuNodeCode") ) {
			return 32;
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
				strFieldName = "ActivityID";
				break;
			case 1:
				strFieldName = "ActivityName";
				break;
			case 2:
				strFieldName = "ActivityType";
				break;
			case 3:
				strFieldName = "ActivityDesc";
				break;
			case 4:
				strFieldName = "BeforeInit";
				break;
			case 5:
				strFieldName = "BeforeInitType";
				break;
			case 6:
				strFieldName = "AfterInit";
				break;
			case 7:
				strFieldName = "AfterInitType";
				break;
			case 8:
				strFieldName = "BeforeEnd";
				break;
			case 9:
				strFieldName = "BeforeEndType";
				break;
			case 10:
				strFieldName = "AfterEnd";
				break;
			case 11:
				strFieldName = "AfterEndType";
				break;
			case 12:
				strFieldName = "WatingTime";
				break;
			case 13:
				strFieldName = "WorkingTime";
				break;
			case 14:
				strFieldName = "TimeOut";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "BusiType";
				break;
			case 21:
				strFieldName = "IsNeed";
				break;
			case 22:
				strFieldName = "ActivityFlag";
				break;
			case 23:
				strFieldName = "ImpDegree";
				break;
			case 24:
				strFieldName = "FunctionID";
				break;
			case 25:
				strFieldName = "CreateAction";
				break;
			case 26:
				strFieldName = "CreateActionType";
				break;
			case 27:
				strFieldName = "ApplyAction";
				break;
			case 28:
				strFieldName = "ApplyActionType";
				break;
			case 29:
				strFieldName = "DeleteAction";
				break;
			case 30:
				strFieldName = "DeleteActionType";
				break;
			case 31:
				strFieldName = "Together";
				break;
			case 32:
				strFieldName = "MenuNodeCode";
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
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BeforeInit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BeforeInitType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AfterInit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AfterInitType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BeforeEnd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BeforeEndType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AfterEnd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AfterEndType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WatingTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WorkingTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TimeOut") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("BusiType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsNeed") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpDegree") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FunctionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreateAction") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreateActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyAction") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeleteAction") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeleteActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Together") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MenuNodeCode") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
