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
import com.sinosoft.lis.db.LWMissionDB;

/*
 * <p>ClassName: LWMissionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWMissionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWMissionSchema.class);
	// @Field
	/** 任务id */
	private String MissionID;
	/** 子任务id */
	private String SubMissionID;
	/** 过程id */
	private String ProcessID;
	/** 当前活动id */
	private String ActivityID;
	/** 当前活动状态 */
	private String ActivityStatus;
	/** 任务属性1 */
	private String MissionProp1;
	/** 任务属性2 */
	private String MissionProp2;
	/** 任务属性3 */
	private String MissionProp3;
	/** 任务属性4 */
	private String MissionProp4;
	/** 任务属性5 */
	private String MissionProp5;
	/** 任务属性6 */
	private String MissionProp6;
	/** 任务属性7 */
	private String MissionProp7;
	/** 任务属性8 */
	private String MissionProp8;
	/** 任务属性9 */
	private String MissionProp9;
	/** 任务属性10 */
	private String MissionProp10;
	/** 任务属性11 */
	private String MissionProp11;
	/** 任务属性12 */
	private String MissionProp12;
	/** 任务属性13 */
	private String MissionProp13;
	/** 任务属性14 */
	private String MissionProp14;
	/** 任务属性15 */
	private String MissionProp15;
	/** 任务属性16 */
	private String MissionProp16;
	/** 任务属性17 */
	private String MissionProp17;
	/** 任务属性18 */
	private String MissionProp18;
	/** 任务属性19 */
	private String MissionProp19;
	/** 任务属性20 */
	private String MissionProp20;
	/** 默认提交的操作员代码 */
	private String DefaultOperator;
	/** 最后操作员代码 */
	private String LastOperator;
	/** 创建者操作员代码 */
	private String CreateOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 进入日期 */
	private Date InDate;
	/** 进入时间 */
	private String InTime;
	/** 退出日期 */
	private Date OutDate;
	/** 退出时间 */
	private String OutTime;
	/** 任务属性21 */
	private String MissionProp21;
	/** 任务属性22 */
	private String MissionProp22;
	/** 任务属性23 */
	private String MissionProp23;
	/** 任务属性24 */
	private String MissionProp24;
	/** 任务属性25 */
	private String MissionProp25;
	/** 时效id */
	private String TimeID;
	/** 标准结束日期 */
	private Date StandEndDate;
	/** 标准结束时间 */
	private String StandEndTime;
	/** 操作机构 */
	private String OperateCom;
	/** 主任务id */
	private String MainMissionID;
	/** Sql优先级id */
	private String SQLPriorityID;
	/** 时效优先级id */
	private String PriorityID;
	/** 版本控制 */
	private String VERSION;

	public static final int FIELDNUM = 49;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWMissionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "MissionID";
		pk[1] = "SubMissionID";
		pk[2] = "ActivityID";

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
		LWMissionSchema cloned = (LWMissionSchema)super.clone();
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
		if(aMissionID!=null && aMissionID.length()>20)
			throw new IllegalArgumentException("任务idMissionID值"+aMissionID+"的长度"+aMissionID.length()+"大于最大值20");
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
		if(aSubMissionID!=null && aSubMissionID.length()>20)
			throw new IllegalArgumentException("子任务idSubMissionID值"+aSubMissionID+"的长度"+aSubMissionID.length()+"大于最大值20");
		SubMissionID = aSubMissionID;
	}
	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		if(aProcessID!=null && aProcessID.length()>10)
			throw new IllegalArgumentException("过程idProcessID值"+aProcessID+"的长度"+aProcessID.length()+"大于最大值10");
		ProcessID = aProcessID;
	}
	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("当前活动idActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	/**
	* 0 -- 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）<p>
	* 1 -- 任务产生完毕待处理，<p>
	* 2 -- 处理中，<p>
	* 3 -- 处理完成，<p>
	* 4 -- 暂停
	*/
	public String getActivityStatus()
	{
		return ActivityStatus;
	}
	public void setActivityStatus(String aActivityStatus)
	{
		if(aActivityStatus!=null && aActivityStatus.length()>1)
			throw new IllegalArgumentException("当前活动状态ActivityStatus值"+aActivityStatus+"的长度"+aActivityStatus.length()+"大于最大值1");
		ActivityStatus = aActivityStatus;
	}
	public String getMissionProp1()
	{
		return MissionProp1;
	}
	public void setMissionProp1(String aMissionProp1)
	{
		if(aMissionProp1!=null && aMissionProp1.length()>90)
			throw new IllegalArgumentException("任务属性1MissionProp1值"+aMissionProp1+"的长度"+aMissionProp1.length()+"大于最大值90");
		MissionProp1 = aMissionProp1;
	}
	public String getMissionProp2()
	{
		return MissionProp2;
	}
	public void setMissionProp2(String aMissionProp2)
	{
		if(aMissionProp2!=null && aMissionProp2.length()>90)
			throw new IllegalArgumentException("任务属性2MissionProp2值"+aMissionProp2+"的长度"+aMissionProp2.length()+"大于最大值90");
		MissionProp2 = aMissionProp2;
	}
	public String getMissionProp3()
	{
		return MissionProp3;
	}
	public void setMissionProp3(String aMissionProp3)
	{
		if(aMissionProp3!=null && aMissionProp3.length()>90)
			throw new IllegalArgumentException("任务属性3MissionProp3值"+aMissionProp3+"的长度"+aMissionProp3.length()+"大于最大值90");
		MissionProp3 = aMissionProp3;
	}
	public String getMissionProp4()
	{
		return MissionProp4;
	}
	public void setMissionProp4(String aMissionProp4)
	{
		if(aMissionProp4!=null && aMissionProp4.length()>90)
			throw new IllegalArgumentException("任务属性4MissionProp4值"+aMissionProp4+"的长度"+aMissionProp4.length()+"大于最大值90");
		MissionProp4 = aMissionProp4;
	}
	public String getMissionProp5()
	{
		return MissionProp5;
	}
	public void setMissionProp5(String aMissionProp5)
	{
		if(aMissionProp5!=null && aMissionProp5.length()>90)
			throw new IllegalArgumentException("任务属性5MissionProp5值"+aMissionProp5+"的长度"+aMissionProp5.length()+"大于最大值90");
		MissionProp5 = aMissionProp5;
	}
	public String getMissionProp6()
	{
		return MissionProp6;
	}
	public void setMissionProp6(String aMissionProp6)
	{
		if(aMissionProp6!=null && aMissionProp6.length()>90)
			throw new IllegalArgumentException("任务属性6MissionProp6值"+aMissionProp6+"的长度"+aMissionProp6.length()+"大于最大值90");
		MissionProp6 = aMissionProp6;
	}
	public String getMissionProp7()
	{
		return MissionProp7;
	}
	public void setMissionProp7(String aMissionProp7)
	{
		if(aMissionProp7!=null && aMissionProp7.length()>90)
			throw new IllegalArgumentException("任务属性7MissionProp7值"+aMissionProp7+"的长度"+aMissionProp7.length()+"大于最大值90");
		MissionProp7 = aMissionProp7;
	}
	public String getMissionProp8()
	{
		return MissionProp8;
	}
	public void setMissionProp8(String aMissionProp8)
	{
		if(aMissionProp8!=null && aMissionProp8.length()>90)
			throw new IllegalArgumentException("任务属性8MissionProp8值"+aMissionProp8+"的长度"+aMissionProp8.length()+"大于最大值90");
		MissionProp8 = aMissionProp8;
	}
	public String getMissionProp9()
	{
		return MissionProp9;
	}
	public void setMissionProp9(String aMissionProp9)
	{
		if(aMissionProp9!=null && aMissionProp9.length()>90)
			throw new IllegalArgumentException("任务属性9MissionProp9值"+aMissionProp9+"的长度"+aMissionProp9.length()+"大于最大值90");
		MissionProp9 = aMissionProp9;
	}
	public String getMissionProp10()
	{
		return MissionProp10;
	}
	public void setMissionProp10(String aMissionProp10)
	{
		if(aMissionProp10!=null && aMissionProp10.length()>90)
			throw new IllegalArgumentException("任务属性10MissionProp10值"+aMissionProp10+"的长度"+aMissionProp10.length()+"大于最大值90");
		MissionProp10 = aMissionProp10;
	}
	public String getMissionProp11()
	{
		return MissionProp11;
	}
	public void setMissionProp11(String aMissionProp11)
	{
		if(aMissionProp11!=null && aMissionProp11.length()>90)
			throw new IllegalArgumentException("任务属性11MissionProp11值"+aMissionProp11+"的长度"+aMissionProp11.length()+"大于最大值90");
		MissionProp11 = aMissionProp11;
	}
	public String getMissionProp12()
	{
		return MissionProp12;
	}
	public void setMissionProp12(String aMissionProp12)
	{
		if(aMissionProp12!=null && aMissionProp12.length()>90)
			throw new IllegalArgumentException("任务属性12MissionProp12值"+aMissionProp12+"的长度"+aMissionProp12.length()+"大于最大值90");
		MissionProp12 = aMissionProp12;
	}
	public String getMissionProp13()
	{
		return MissionProp13;
	}
	public void setMissionProp13(String aMissionProp13)
	{
		if(aMissionProp13!=null && aMissionProp13.length()>90)
			throw new IllegalArgumentException("任务属性13MissionProp13值"+aMissionProp13+"的长度"+aMissionProp13.length()+"大于最大值90");
		MissionProp13 = aMissionProp13;
	}
	public String getMissionProp14()
	{
		return MissionProp14;
	}
	public void setMissionProp14(String aMissionProp14)
	{
		if(aMissionProp14!=null && aMissionProp14.length()>90)
			throw new IllegalArgumentException("任务属性14MissionProp14值"+aMissionProp14+"的长度"+aMissionProp14.length()+"大于最大值90");
		MissionProp14 = aMissionProp14;
	}
	public String getMissionProp15()
	{
		return MissionProp15;
	}
	public void setMissionProp15(String aMissionProp15)
	{
		if(aMissionProp15!=null && aMissionProp15.length()>90)
			throw new IllegalArgumentException("任务属性15MissionProp15值"+aMissionProp15+"的长度"+aMissionProp15.length()+"大于最大值90");
		MissionProp15 = aMissionProp15;
	}
	public String getMissionProp16()
	{
		return MissionProp16;
	}
	public void setMissionProp16(String aMissionProp16)
	{
		if(aMissionProp16!=null && aMissionProp16.length()>90)
			throw new IllegalArgumentException("任务属性16MissionProp16值"+aMissionProp16+"的长度"+aMissionProp16.length()+"大于最大值90");
		MissionProp16 = aMissionProp16;
	}
	public String getMissionProp17()
	{
		return MissionProp17;
	}
	public void setMissionProp17(String aMissionProp17)
	{
		if(aMissionProp17!=null && aMissionProp17.length()>90)
			throw new IllegalArgumentException("任务属性17MissionProp17值"+aMissionProp17+"的长度"+aMissionProp17.length()+"大于最大值90");
		MissionProp17 = aMissionProp17;
	}
	public String getMissionProp18()
	{
		return MissionProp18;
	}
	public void setMissionProp18(String aMissionProp18)
	{
		if(aMissionProp18!=null && aMissionProp18.length()>90)
			throw new IllegalArgumentException("任务属性18MissionProp18值"+aMissionProp18+"的长度"+aMissionProp18.length()+"大于最大值90");
		MissionProp18 = aMissionProp18;
	}
	public String getMissionProp19()
	{
		return MissionProp19;
	}
	public void setMissionProp19(String aMissionProp19)
	{
		if(aMissionProp19!=null && aMissionProp19.length()>90)
			throw new IllegalArgumentException("任务属性19MissionProp19值"+aMissionProp19+"的长度"+aMissionProp19.length()+"大于最大值90");
		MissionProp19 = aMissionProp19;
	}
	public String getMissionProp20()
	{
		return MissionProp20;
	}
	public void setMissionProp20(String aMissionProp20)
	{
		if(aMissionProp20!=null && aMissionProp20.length()>90)
			throw new IllegalArgumentException("任务属性20MissionProp20值"+aMissionProp20+"的长度"+aMissionProp20.length()+"大于最大值90");
		MissionProp20 = aMissionProp20;
	}
	/**
	* 默认提交的操作员代码表示系统通过某种算法默认提交的操作员代码。
	*/
	public String getDefaultOperator()
	{
		return DefaultOperator;
	}
	public void setDefaultOperator(String aDefaultOperator)
	{
		if(aDefaultOperator!=null && aDefaultOperator.length()>10)
			throw new IllegalArgumentException("默认提交的操作员代码DefaultOperator值"+aDefaultOperator+"的长度"+aDefaultOperator.length()+"大于最大值10");
		DefaultOperator = aDefaultOperator;
	}
	/**
	* 纪录最后一次操作的操作员
	*/
	public String getLastOperator()
	{
		return LastOperator;
	}
	public void setLastOperator(String aLastOperator)
	{
		if(aLastOperator!=null && aLastOperator.length()>10)
			throw new IllegalArgumentException("最后操作员代码LastOperator值"+aLastOperator+"的长度"+aLastOperator.length()+"大于最大值10");
		LastOperator = aLastOperator;
	}
	/**
	* 纪录创建该纪录的操作员。
	*/
	public String getCreateOperator()
	{
		return CreateOperator;
	}
	public void setCreateOperator(String aCreateOperator)
	{
		if(aCreateOperator!=null && aCreateOperator.length()>10)
			throw new IllegalArgumentException("创建者操作员代码CreateOperator值"+aCreateOperator+"的长度"+aCreateOperator.length()+"大于最大值10");
		CreateOperator = aCreateOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	/**
	* 纪录最后一次修改的日期。
	*/
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
	public String getInDate()
	{
		if( InDate != null )
			return fDate.getString(InDate);
		else
			return null;
	}
	public void setInDate(Date aInDate)
	{
		InDate = aInDate;
	}
	public void setInDate(String aInDate)
	{
		if (aInDate != null && !aInDate.equals("") )
		{
			InDate = fDate.getDate( aInDate );
		}
		else
			InDate = null;
	}

	public String getInTime()
	{
		return InTime;
	}
	public void setInTime(String aInTime)
	{
		if(aInTime!=null && aInTime.length()>8)
			throw new IllegalArgumentException("进入时间InTime值"+aInTime+"的长度"+aInTime.length()+"大于最大值8");
		InTime = aInTime;
	}
	public String getOutDate()
	{
		if( OutDate != null )
			return fDate.getString(OutDate);
		else
			return null;
	}
	public void setOutDate(Date aOutDate)
	{
		OutDate = aOutDate;
	}
	public void setOutDate(String aOutDate)
	{
		if (aOutDate != null && !aOutDate.equals("") )
		{
			OutDate = fDate.getDate( aOutDate );
		}
		else
			OutDate = null;
	}

	public String getOutTime()
	{
		return OutTime;
	}
	public void setOutTime(String aOutTime)
	{
		if(aOutTime!=null && aOutTime.length()>8)
			throw new IllegalArgumentException("退出时间OutTime值"+aOutTime+"的长度"+aOutTime.length()+"大于最大值8");
		OutTime = aOutTime;
	}
	public String getMissionProp21()
	{
		return MissionProp21;
	}
	public void setMissionProp21(String aMissionProp21)
	{
		if(aMissionProp21!=null && aMissionProp21.length()>90)
			throw new IllegalArgumentException("任务属性21MissionProp21值"+aMissionProp21+"的长度"+aMissionProp21.length()+"大于最大值90");
		MissionProp21 = aMissionProp21;
	}
	public String getMissionProp22()
	{
		return MissionProp22;
	}
	public void setMissionProp22(String aMissionProp22)
	{
		if(aMissionProp22!=null && aMissionProp22.length()>90)
			throw new IllegalArgumentException("任务属性22MissionProp22值"+aMissionProp22+"的长度"+aMissionProp22.length()+"大于最大值90");
		MissionProp22 = aMissionProp22;
	}
	public String getMissionProp23()
	{
		return MissionProp23;
	}
	public void setMissionProp23(String aMissionProp23)
	{
		if(aMissionProp23!=null && aMissionProp23.length()>90)
			throw new IllegalArgumentException("任务属性23MissionProp23值"+aMissionProp23+"的长度"+aMissionProp23.length()+"大于最大值90");
		MissionProp23 = aMissionProp23;
	}
	public String getMissionProp24()
	{
		return MissionProp24;
	}
	public void setMissionProp24(String aMissionProp24)
	{
		if(aMissionProp24!=null && aMissionProp24.length()>90)
			throw new IllegalArgumentException("任务属性24MissionProp24值"+aMissionProp24+"的长度"+aMissionProp24.length()+"大于最大值90");
		MissionProp24 = aMissionProp24;
	}
	public String getMissionProp25()
	{
		return MissionProp25;
	}
	public void setMissionProp25(String aMissionProp25)
	{
		if(aMissionProp25!=null && aMissionProp25.length()>90)
			throw new IllegalArgumentException("任务属性25MissionProp25值"+aMissionProp25+"的长度"+aMissionProp25.length()+"大于最大值90");
		MissionProp25 = aMissionProp25;
	}
	public String getTimeID()
	{
		return TimeID;
	}
	public void setTimeID(String aTimeID)
	{
		if(aTimeID!=null && aTimeID.length()>20)
			throw new IllegalArgumentException("时效idTimeID值"+aTimeID+"的长度"+aTimeID.length()+"大于最大值20");
		TimeID = aTimeID;
	}
	public String getStandEndDate()
	{
		if( StandEndDate != null )
			return fDate.getString(StandEndDate);
		else
			return null;
	}
	public void setStandEndDate(Date aStandEndDate)
	{
		StandEndDate = aStandEndDate;
	}
	public void setStandEndDate(String aStandEndDate)
	{
		if (aStandEndDate != null && !aStandEndDate.equals("") )
		{
			StandEndDate = fDate.getDate( aStandEndDate );
		}
		else
			StandEndDate = null;
	}

	public String getStandEndTime()
	{
		return StandEndTime;
	}
	public void setStandEndTime(String aStandEndTime)
	{
		if(aStandEndTime!=null && aStandEndTime.length()>8)
			throw new IllegalArgumentException("标准结束时间StandEndTime值"+aStandEndTime+"的长度"+aStandEndTime.length()+"大于最大值8");
		StandEndTime = aStandEndTime;
	}
	public String getOperateCom()
	{
		return OperateCom;
	}
	public void setOperateCom(String aOperateCom)
	{
		if(aOperateCom!=null && aOperateCom.length()>20)
			throw new IllegalArgumentException("操作机构OperateCom值"+aOperateCom+"的长度"+aOperateCom.length()+"大于最大值20");
		OperateCom = aOperateCom;
	}
	public String getMainMissionID()
	{
		return MainMissionID;
	}
	public void setMainMissionID(String aMainMissionID)
	{
		if(aMainMissionID!=null && aMainMissionID.length()>20)
			throw new IllegalArgumentException("主任务idMainMissionID值"+aMainMissionID+"的长度"+aMainMissionID.length()+"大于最大值20");
		MainMissionID = aMainMissionID;
	}
	public String getSQLPriorityID()
	{
		return SQLPriorityID;
	}
	public void setSQLPriorityID(String aSQLPriorityID)
	{
		if(aSQLPriorityID!=null && aSQLPriorityID.length()>20)
			throw new IllegalArgumentException("Sql优先级idSQLPriorityID值"+aSQLPriorityID+"的长度"+aSQLPriorityID.length()+"大于最大值20");
		SQLPriorityID = aSQLPriorityID;
	}
	public String getPriorityID()
	{
		return PriorityID;
	}
	public void setPriorityID(String aPriorityID)
	{
		if(aPriorityID!=null && aPriorityID.length()>20)
			throw new IllegalArgumentException("时效优先级idPriorityID值"+aPriorityID+"的长度"+aPriorityID.length()+"大于最大值20");
		PriorityID = aPriorityID;
	}
	public String getVERSION()
	{
		return VERSION;
	}
	public void setVERSION(String aVERSION)
	{
		if(aVERSION!=null && aVERSION.length()>20)
			throw new IllegalArgumentException("版本控制VERSION值"+aVERSION+"的长度"+aVERSION.length()+"大于最大值20");
		VERSION = aVERSION;
	}

	/**
	* 使用另外一个 LWMissionSchema 对象给 Schema 赋值
	* @param: aLWMissionSchema LWMissionSchema
	**/
	public void setSchema(LWMissionSchema aLWMissionSchema)
	{
		this.MissionID = aLWMissionSchema.getMissionID();
		this.SubMissionID = aLWMissionSchema.getSubMissionID();
		this.ProcessID = aLWMissionSchema.getProcessID();
		this.ActivityID = aLWMissionSchema.getActivityID();
		this.ActivityStatus = aLWMissionSchema.getActivityStatus();
		this.MissionProp1 = aLWMissionSchema.getMissionProp1();
		this.MissionProp2 = aLWMissionSchema.getMissionProp2();
		this.MissionProp3 = aLWMissionSchema.getMissionProp3();
		this.MissionProp4 = aLWMissionSchema.getMissionProp4();
		this.MissionProp5 = aLWMissionSchema.getMissionProp5();
		this.MissionProp6 = aLWMissionSchema.getMissionProp6();
		this.MissionProp7 = aLWMissionSchema.getMissionProp7();
		this.MissionProp8 = aLWMissionSchema.getMissionProp8();
		this.MissionProp9 = aLWMissionSchema.getMissionProp9();
		this.MissionProp10 = aLWMissionSchema.getMissionProp10();
		this.MissionProp11 = aLWMissionSchema.getMissionProp11();
		this.MissionProp12 = aLWMissionSchema.getMissionProp12();
		this.MissionProp13 = aLWMissionSchema.getMissionProp13();
		this.MissionProp14 = aLWMissionSchema.getMissionProp14();
		this.MissionProp15 = aLWMissionSchema.getMissionProp15();
		this.MissionProp16 = aLWMissionSchema.getMissionProp16();
		this.MissionProp17 = aLWMissionSchema.getMissionProp17();
		this.MissionProp18 = aLWMissionSchema.getMissionProp18();
		this.MissionProp19 = aLWMissionSchema.getMissionProp19();
		this.MissionProp20 = aLWMissionSchema.getMissionProp20();
		this.DefaultOperator = aLWMissionSchema.getDefaultOperator();
		this.LastOperator = aLWMissionSchema.getLastOperator();
		this.CreateOperator = aLWMissionSchema.getCreateOperator();
		this.MakeDate = fDate.getDate( aLWMissionSchema.getMakeDate());
		this.MakeTime = aLWMissionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLWMissionSchema.getModifyDate());
		this.ModifyTime = aLWMissionSchema.getModifyTime();
		this.InDate = fDate.getDate( aLWMissionSchema.getInDate());
		this.InTime = aLWMissionSchema.getInTime();
		this.OutDate = fDate.getDate( aLWMissionSchema.getOutDate());
		this.OutTime = aLWMissionSchema.getOutTime();
		this.MissionProp21 = aLWMissionSchema.getMissionProp21();
		this.MissionProp22 = aLWMissionSchema.getMissionProp22();
		this.MissionProp23 = aLWMissionSchema.getMissionProp23();
		this.MissionProp24 = aLWMissionSchema.getMissionProp24();
		this.MissionProp25 = aLWMissionSchema.getMissionProp25();
		this.TimeID = aLWMissionSchema.getTimeID();
		this.StandEndDate = fDate.getDate( aLWMissionSchema.getStandEndDate());
		this.StandEndTime = aLWMissionSchema.getStandEndTime();
		this.OperateCom = aLWMissionSchema.getOperateCom();
		this.MainMissionID = aLWMissionSchema.getMainMissionID();
		this.SQLPriorityID = aLWMissionSchema.getSQLPriorityID();
		this.PriorityID = aLWMissionSchema.getPriorityID();
		this.VERSION = aLWMissionSchema.getVERSION();
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

			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			if( rs.getString("ActivityStatus") == null )
				this.ActivityStatus = null;
			else
				this.ActivityStatus = rs.getString("ActivityStatus").trim();

			if( rs.getString("MissionProp1") == null )
				this.MissionProp1 = null;
			else
				this.MissionProp1 = rs.getString("MissionProp1").trim();

			if( rs.getString("MissionProp2") == null )
				this.MissionProp2 = null;
			else
				this.MissionProp2 = rs.getString("MissionProp2").trim();

			if( rs.getString("MissionProp3") == null )
				this.MissionProp3 = null;
			else
				this.MissionProp3 = rs.getString("MissionProp3").trim();

			if( rs.getString("MissionProp4") == null )
				this.MissionProp4 = null;
			else
				this.MissionProp4 = rs.getString("MissionProp4").trim();

			if( rs.getString("MissionProp5") == null )
				this.MissionProp5 = null;
			else
				this.MissionProp5 = rs.getString("MissionProp5").trim();

			if( rs.getString("MissionProp6") == null )
				this.MissionProp6 = null;
			else
				this.MissionProp6 = rs.getString("MissionProp6").trim();

			if( rs.getString("MissionProp7") == null )
				this.MissionProp7 = null;
			else
				this.MissionProp7 = rs.getString("MissionProp7").trim();

			if( rs.getString("MissionProp8") == null )
				this.MissionProp8 = null;
			else
				this.MissionProp8 = rs.getString("MissionProp8").trim();

			if( rs.getString("MissionProp9") == null )
				this.MissionProp9 = null;
			else
				this.MissionProp9 = rs.getString("MissionProp9").trim();

			if( rs.getString("MissionProp10") == null )
				this.MissionProp10 = null;
			else
				this.MissionProp10 = rs.getString("MissionProp10").trim();

			if( rs.getString("MissionProp11") == null )
				this.MissionProp11 = null;
			else
				this.MissionProp11 = rs.getString("MissionProp11").trim();

			if( rs.getString("MissionProp12") == null )
				this.MissionProp12 = null;
			else
				this.MissionProp12 = rs.getString("MissionProp12").trim();

			if( rs.getString("MissionProp13") == null )
				this.MissionProp13 = null;
			else
				this.MissionProp13 = rs.getString("MissionProp13").trim();

			if( rs.getString("MissionProp14") == null )
				this.MissionProp14 = null;
			else
				this.MissionProp14 = rs.getString("MissionProp14").trim();

			if( rs.getString("MissionProp15") == null )
				this.MissionProp15 = null;
			else
				this.MissionProp15 = rs.getString("MissionProp15").trim();

			if( rs.getString("MissionProp16") == null )
				this.MissionProp16 = null;
			else
				this.MissionProp16 = rs.getString("MissionProp16").trim();

			if( rs.getString("MissionProp17") == null )
				this.MissionProp17 = null;
			else
				this.MissionProp17 = rs.getString("MissionProp17").trim();

			if( rs.getString("MissionProp18") == null )
				this.MissionProp18 = null;
			else
				this.MissionProp18 = rs.getString("MissionProp18").trim();

			if( rs.getString("MissionProp19") == null )
				this.MissionProp19 = null;
			else
				this.MissionProp19 = rs.getString("MissionProp19").trim();

			if( rs.getString("MissionProp20") == null )
				this.MissionProp20 = null;
			else
				this.MissionProp20 = rs.getString("MissionProp20").trim();

			if( rs.getString("DefaultOperator") == null )
				this.DefaultOperator = null;
			else
				this.DefaultOperator = rs.getString("DefaultOperator").trim();

			if( rs.getString("LastOperator") == null )
				this.LastOperator = null;
			else
				this.LastOperator = rs.getString("LastOperator").trim();

			if( rs.getString("CreateOperator") == null )
				this.CreateOperator = null;
			else
				this.CreateOperator = rs.getString("CreateOperator").trim();

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

			this.InDate = rs.getDate("InDate");
			if( rs.getString("InTime") == null )
				this.InTime = null;
			else
				this.InTime = rs.getString("InTime").trim();

			this.OutDate = rs.getDate("OutDate");
			if( rs.getString("OutTime") == null )
				this.OutTime = null;
			else
				this.OutTime = rs.getString("OutTime").trim();

			if( rs.getString("MissionProp21") == null )
				this.MissionProp21 = null;
			else
				this.MissionProp21 = rs.getString("MissionProp21").trim();

			if( rs.getString("MissionProp22") == null )
				this.MissionProp22 = null;
			else
				this.MissionProp22 = rs.getString("MissionProp22").trim();

			if( rs.getString("MissionProp23") == null )
				this.MissionProp23 = null;
			else
				this.MissionProp23 = rs.getString("MissionProp23").trim();

			if( rs.getString("MissionProp24") == null )
				this.MissionProp24 = null;
			else
				this.MissionProp24 = rs.getString("MissionProp24").trim();

			if( rs.getString("MissionProp25") == null )
				this.MissionProp25 = null;
			else
				this.MissionProp25 = rs.getString("MissionProp25").trim();

			if( rs.getString("TimeID") == null )
				this.TimeID = null;
			else
				this.TimeID = rs.getString("TimeID").trim();

			this.StandEndDate = rs.getDate("StandEndDate");
			if( rs.getString("StandEndTime") == null )
				this.StandEndTime = null;
			else
				this.StandEndTime = rs.getString("StandEndTime").trim();

			if( rs.getString("OperateCom") == null )
				this.OperateCom = null;
			else
				this.OperateCom = rs.getString("OperateCom").trim();

			if( rs.getString("MainMissionID") == null )
				this.MainMissionID = null;
			else
				this.MainMissionID = rs.getString("MainMissionID").trim();

			if( rs.getString("SQLPriorityID") == null )
				this.SQLPriorityID = null;
			else
				this.SQLPriorityID = rs.getString("SQLPriorityID").trim();

			if( rs.getString("PriorityID") == null )
				this.PriorityID = null;
			else
				this.PriorityID = rs.getString("PriorityID").trim();

			if( rs.getString("VERSION") == null )
				this.VERSION = null;
			else
				this.VERSION = rs.getString("VERSION").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWMission表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWMissionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWMissionSchema getSchema()
	{
		LWMissionSchema aLWMissionSchema = new LWMissionSchema();
		aLWMissionSchema.setSchema(this);
		return aLWMissionSchema;
	}

	public LWMissionDB getDB()
	{
		LWMissionDB aDBOper = new LWMissionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWMission描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubMissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp16)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp17)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp18)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp19)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp20)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreateOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp21)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp22)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp23)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp24)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MissionProp25)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TimeID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandEndTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperateCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainMissionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SQLPriorityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PriorityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VERSION));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWMission>历史记账凭证主表信息</A>表字段
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
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ActivityStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MissionProp1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MissionProp2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MissionProp3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MissionProp4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MissionProp5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MissionProp6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MissionProp7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MissionProp8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MissionProp9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MissionProp10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MissionProp11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MissionProp12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MissionProp13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MissionProp14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MissionProp15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MissionProp16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MissionProp17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MissionProp18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MissionProp19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MissionProp20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			DefaultOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			LastOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			CreateOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			InTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			OutDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			OutTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MissionProp21 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			MissionProp22 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MissionProp23 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			MissionProp24 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			MissionProp25 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			TimeID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			StandEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			StandEndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			OperateCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			MainMissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			SQLPriorityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			PriorityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			VERSION = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWMissionSchema";
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
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("ActivityStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityStatus));
		}
		if (FCode.equalsIgnoreCase("MissionProp1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp1));
		}
		if (FCode.equalsIgnoreCase("MissionProp2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp2));
		}
		if (FCode.equalsIgnoreCase("MissionProp3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp3));
		}
		if (FCode.equalsIgnoreCase("MissionProp4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp4));
		}
		if (FCode.equalsIgnoreCase("MissionProp5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp5));
		}
		if (FCode.equalsIgnoreCase("MissionProp6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp6));
		}
		if (FCode.equalsIgnoreCase("MissionProp7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp7));
		}
		if (FCode.equalsIgnoreCase("MissionProp8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp8));
		}
		if (FCode.equalsIgnoreCase("MissionProp9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp9));
		}
		if (FCode.equalsIgnoreCase("MissionProp10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp10));
		}
		if (FCode.equalsIgnoreCase("MissionProp11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp11));
		}
		if (FCode.equalsIgnoreCase("MissionProp12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp12));
		}
		if (FCode.equalsIgnoreCase("MissionProp13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp13));
		}
		if (FCode.equalsIgnoreCase("MissionProp14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp14));
		}
		if (FCode.equalsIgnoreCase("MissionProp15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp15));
		}
		if (FCode.equalsIgnoreCase("MissionProp16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp16));
		}
		if (FCode.equalsIgnoreCase("MissionProp17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp17));
		}
		if (FCode.equalsIgnoreCase("MissionProp18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp18));
		}
		if (FCode.equalsIgnoreCase("MissionProp19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp19));
		}
		if (FCode.equalsIgnoreCase("MissionProp20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp20));
		}
		if (FCode.equalsIgnoreCase("DefaultOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultOperator));
		}
		if (FCode.equalsIgnoreCase("LastOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastOperator));
		}
		if (FCode.equalsIgnoreCase("CreateOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreateOperator));
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
		if (FCode.equalsIgnoreCase("InDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInDate()));
		}
		if (FCode.equalsIgnoreCase("InTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InTime));
		}
		if (FCode.equalsIgnoreCase("OutDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutDate()));
		}
		if (FCode.equalsIgnoreCase("OutTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutTime));
		}
		if (FCode.equalsIgnoreCase("MissionProp21"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp21));
		}
		if (FCode.equalsIgnoreCase("MissionProp22"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp22));
		}
		if (FCode.equalsIgnoreCase("MissionProp23"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp23));
		}
		if (FCode.equalsIgnoreCase("MissionProp24"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp24));
		}
		if (FCode.equalsIgnoreCase("MissionProp25"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MissionProp25));
		}
		if (FCode.equalsIgnoreCase("TimeID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TimeID));
		}
		if (FCode.equalsIgnoreCase("StandEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandEndDate()));
		}
		if (FCode.equalsIgnoreCase("StandEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandEndTime));
		}
		if (FCode.equalsIgnoreCase("OperateCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperateCom));
		}
		if (FCode.equalsIgnoreCase("MainMissionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainMissionID));
		}
		if (FCode.equalsIgnoreCase("SQLPriorityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SQLPriorityID));
		}
		if (FCode.equalsIgnoreCase("PriorityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriorityID));
		}
		if (FCode.equalsIgnoreCase("VERSION"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VERSION));
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
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ActivityStatus);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MissionProp1);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MissionProp2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MissionProp3);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MissionProp4);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MissionProp5);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MissionProp6);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MissionProp7);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MissionProp8);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MissionProp9);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MissionProp10);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MissionProp11);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MissionProp12);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MissionProp13);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MissionProp14);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MissionProp15);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MissionProp16);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MissionProp17);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MissionProp18);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MissionProp19);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MissionProp20);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(DefaultOperator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(LastOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CreateOperator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(InTime);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OutTime);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(MissionProp21);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MissionProp22);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(MissionProp23);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(MissionProp24);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(MissionProp25);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(TimeID);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandEndDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(StandEndTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(OperateCom);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(MainMissionID);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(SQLPriorityID);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(PriorityID);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(VERSION);
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
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityID = FValue.trim();
			}
			else
				ActivityID = null;
		}
		if (FCode.equalsIgnoreCase("ActivityStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityStatus = FValue.trim();
			}
			else
				ActivityStatus = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp1 = FValue.trim();
			}
			else
				MissionProp1 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp2 = FValue.trim();
			}
			else
				MissionProp2 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp3 = FValue.trim();
			}
			else
				MissionProp3 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp4 = FValue.trim();
			}
			else
				MissionProp4 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp5 = FValue.trim();
			}
			else
				MissionProp5 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp6 = FValue.trim();
			}
			else
				MissionProp6 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp7 = FValue.trim();
			}
			else
				MissionProp7 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp8 = FValue.trim();
			}
			else
				MissionProp8 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp9 = FValue.trim();
			}
			else
				MissionProp9 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp10 = FValue.trim();
			}
			else
				MissionProp10 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp11 = FValue.trim();
			}
			else
				MissionProp11 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp12 = FValue.trim();
			}
			else
				MissionProp12 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp13 = FValue.trim();
			}
			else
				MissionProp13 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp14 = FValue.trim();
			}
			else
				MissionProp14 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp15 = FValue.trim();
			}
			else
				MissionProp15 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp16 = FValue.trim();
			}
			else
				MissionProp16 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp17 = FValue.trim();
			}
			else
				MissionProp17 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp18 = FValue.trim();
			}
			else
				MissionProp18 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp19 = FValue.trim();
			}
			else
				MissionProp19 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp20 = FValue.trim();
			}
			else
				MissionProp20 = null;
		}
		if (FCode.equalsIgnoreCase("DefaultOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultOperator = FValue.trim();
			}
			else
				DefaultOperator = null;
		}
		if (FCode.equalsIgnoreCase("LastOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastOperator = FValue.trim();
			}
			else
				LastOperator = null;
		}
		if (FCode.equalsIgnoreCase("CreateOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreateOperator = FValue.trim();
			}
			else
				CreateOperator = null;
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
		if (FCode.equalsIgnoreCase("InDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InDate = fDate.getDate( FValue );
			}
			else
				InDate = null;
		}
		if (FCode.equalsIgnoreCase("InTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InTime = FValue.trim();
			}
			else
				InTime = null;
		}
		if (FCode.equalsIgnoreCase("OutDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutDate = fDate.getDate( FValue );
			}
			else
				OutDate = null;
		}
		if (FCode.equalsIgnoreCase("OutTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutTime = FValue.trim();
			}
			else
				OutTime = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp21"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp21 = FValue.trim();
			}
			else
				MissionProp21 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp22"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp22 = FValue.trim();
			}
			else
				MissionProp22 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp23"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp23 = FValue.trim();
			}
			else
				MissionProp23 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp24"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp24 = FValue.trim();
			}
			else
				MissionProp24 = null;
		}
		if (FCode.equalsIgnoreCase("MissionProp25"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MissionProp25 = FValue.trim();
			}
			else
				MissionProp25 = null;
		}
		if (FCode.equalsIgnoreCase("TimeID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TimeID = FValue.trim();
			}
			else
				TimeID = null;
		}
		if (FCode.equalsIgnoreCase("StandEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandEndDate = fDate.getDate( FValue );
			}
			else
				StandEndDate = null;
		}
		if (FCode.equalsIgnoreCase("StandEndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandEndTime = FValue.trim();
			}
			else
				StandEndTime = null;
		}
		if (FCode.equalsIgnoreCase("OperateCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperateCom = FValue.trim();
			}
			else
				OperateCom = null;
		}
		if (FCode.equalsIgnoreCase("MainMissionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainMissionID = FValue.trim();
			}
			else
				MainMissionID = null;
		}
		if (FCode.equalsIgnoreCase("SQLPriorityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SQLPriorityID = FValue.trim();
			}
			else
				SQLPriorityID = null;
		}
		if (FCode.equalsIgnoreCase("PriorityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PriorityID = FValue.trim();
			}
			else
				PriorityID = null;
		}
		if (FCode.equalsIgnoreCase("VERSION"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VERSION = FValue.trim();
			}
			else
				VERSION = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWMissionSchema other = (LWMissionSchema)otherObject;
		return
			MissionID.equals(other.getMissionID())
			&& SubMissionID.equals(other.getSubMissionID())
			&& ProcessID.equals(other.getProcessID())
			&& ActivityID.equals(other.getActivityID())
			&& ActivityStatus.equals(other.getActivityStatus())
			&& MissionProp1.equals(other.getMissionProp1())
			&& MissionProp2.equals(other.getMissionProp2())
			&& MissionProp3.equals(other.getMissionProp3())
			&& MissionProp4.equals(other.getMissionProp4())
			&& MissionProp5.equals(other.getMissionProp5())
			&& MissionProp6.equals(other.getMissionProp6())
			&& MissionProp7.equals(other.getMissionProp7())
			&& MissionProp8.equals(other.getMissionProp8())
			&& MissionProp9.equals(other.getMissionProp9())
			&& MissionProp10.equals(other.getMissionProp10())
			&& MissionProp11.equals(other.getMissionProp11())
			&& MissionProp12.equals(other.getMissionProp12())
			&& MissionProp13.equals(other.getMissionProp13())
			&& MissionProp14.equals(other.getMissionProp14())
			&& MissionProp15.equals(other.getMissionProp15())
			&& MissionProp16.equals(other.getMissionProp16())
			&& MissionProp17.equals(other.getMissionProp17())
			&& MissionProp18.equals(other.getMissionProp18())
			&& MissionProp19.equals(other.getMissionProp19())
			&& MissionProp20.equals(other.getMissionProp20())
			&& DefaultOperator.equals(other.getDefaultOperator())
			&& LastOperator.equals(other.getLastOperator())
			&& CreateOperator.equals(other.getCreateOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(InDate).equals(other.getInDate())
			&& InTime.equals(other.getInTime())
			&& fDate.getString(OutDate).equals(other.getOutDate())
			&& OutTime.equals(other.getOutTime())
			&& MissionProp21.equals(other.getMissionProp21())
			&& MissionProp22.equals(other.getMissionProp22())
			&& MissionProp23.equals(other.getMissionProp23())
			&& MissionProp24.equals(other.getMissionProp24())
			&& MissionProp25.equals(other.getMissionProp25())
			&& TimeID.equals(other.getTimeID())
			&& fDate.getString(StandEndDate).equals(other.getStandEndDate())
			&& StandEndTime.equals(other.getStandEndTime())
			&& OperateCom.equals(other.getOperateCom())
			&& MainMissionID.equals(other.getMainMissionID())
			&& SQLPriorityID.equals(other.getSQLPriorityID())
			&& PriorityID.equals(other.getPriorityID())
			&& VERSION.equals(other.getVERSION());
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
		if( strFieldName.equals("ActivityID") ) {
			return 3;
		}
		if( strFieldName.equals("ActivityStatus") ) {
			return 4;
		}
		if( strFieldName.equals("MissionProp1") ) {
			return 5;
		}
		if( strFieldName.equals("MissionProp2") ) {
			return 6;
		}
		if( strFieldName.equals("MissionProp3") ) {
			return 7;
		}
		if( strFieldName.equals("MissionProp4") ) {
			return 8;
		}
		if( strFieldName.equals("MissionProp5") ) {
			return 9;
		}
		if( strFieldName.equals("MissionProp6") ) {
			return 10;
		}
		if( strFieldName.equals("MissionProp7") ) {
			return 11;
		}
		if( strFieldName.equals("MissionProp8") ) {
			return 12;
		}
		if( strFieldName.equals("MissionProp9") ) {
			return 13;
		}
		if( strFieldName.equals("MissionProp10") ) {
			return 14;
		}
		if( strFieldName.equals("MissionProp11") ) {
			return 15;
		}
		if( strFieldName.equals("MissionProp12") ) {
			return 16;
		}
		if( strFieldName.equals("MissionProp13") ) {
			return 17;
		}
		if( strFieldName.equals("MissionProp14") ) {
			return 18;
		}
		if( strFieldName.equals("MissionProp15") ) {
			return 19;
		}
		if( strFieldName.equals("MissionProp16") ) {
			return 20;
		}
		if( strFieldName.equals("MissionProp17") ) {
			return 21;
		}
		if( strFieldName.equals("MissionProp18") ) {
			return 22;
		}
		if( strFieldName.equals("MissionProp19") ) {
			return 23;
		}
		if( strFieldName.equals("MissionProp20") ) {
			return 24;
		}
		if( strFieldName.equals("DefaultOperator") ) {
			return 25;
		}
		if( strFieldName.equals("LastOperator") ) {
			return 26;
		}
		if( strFieldName.equals("CreateOperator") ) {
			return 27;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 28;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
		}
		if( strFieldName.equals("InDate") ) {
			return 32;
		}
		if( strFieldName.equals("InTime") ) {
			return 33;
		}
		if( strFieldName.equals("OutDate") ) {
			return 34;
		}
		if( strFieldName.equals("OutTime") ) {
			return 35;
		}
		if( strFieldName.equals("MissionProp21") ) {
			return 36;
		}
		if( strFieldName.equals("MissionProp22") ) {
			return 37;
		}
		if( strFieldName.equals("MissionProp23") ) {
			return 38;
		}
		if( strFieldName.equals("MissionProp24") ) {
			return 39;
		}
		if( strFieldName.equals("MissionProp25") ) {
			return 40;
		}
		if( strFieldName.equals("TimeID") ) {
			return 41;
		}
		if( strFieldName.equals("StandEndDate") ) {
			return 42;
		}
		if( strFieldName.equals("StandEndTime") ) {
			return 43;
		}
		if( strFieldName.equals("OperateCom") ) {
			return 44;
		}
		if( strFieldName.equals("MainMissionID") ) {
			return 45;
		}
		if( strFieldName.equals("SQLPriorityID") ) {
			return 46;
		}
		if( strFieldName.equals("PriorityID") ) {
			return 47;
		}
		if( strFieldName.equals("VERSION") ) {
			return 48;
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
				strFieldName = "ActivityID";
				break;
			case 4:
				strFieldName = "ActivityStatus";
				break;
			case 5:
				strFieldName = "MissionProp1";
				break;
			case 6:
				strFieldName = "MissionProp2";
				break;
			case 7:
				strFieldName = "MissionProp3";
				break;
			case 8:
				strFieldName = "MissionProp4";
				break;
			case 9:
				strFieldName = "MissionProp5";
				break;
			case 10:
				strFieldName = "MissionProp6";
				break;
			case 11:
				strFieldName = "MissionProp7";
				break;
			case 12:
				strFieldName = "MissionProp8";
				break;
			case 13:
				strFieldName = "MissionProp9";
				break;
			case 14:
				strFieldName = "MissionProp10";
				break;
			case 15:
				strFieldName = "MissionProp11";
				break;
			case 16:
				strFieldName = "MissionProp12";
				break;
			case 17:
				strFieldName = "MissionProp13";
				break;
			case 18:
				strFieldName = "MissionProp14";
				break;
			case 19:
				strFieldName = "MissionProp15";
				break;
			case 20:
				strFieldName = "MissionProp16";
				break;
			case 21:
				strFieldName = "MissionProp17";
				break;
			case 22:
				strFieldName = "MissionProp18";
				break;
			case 23:
				strFieldName = "MissionProp19";
				break;
			case 24:
				strFieldName = "MissionProp20";
				break;
			case 25:
				strFieldName = "DefaultOperator";
				break;
			case 26:
				strFieldName = "LastOperator";
				break;
			case 27:
				strFieldName = "CreateOperator";
				break;
			case 28:
				strFieldName = "MakeDate";
				break;
			case 29:
				strFieldName = "MakeTime";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
				strFieldName = "ModifyTime";
				break;
			case 32:
				strFieldName = "InDate";
				break;
			case 33:
				strFieldName = "InTime";
				break;
			case 34:
				strFieldName = "OutDate";
				break;
			case 35:
				strFieldName = "OutTime";
				break;
			case 36:
				strFieldName = "MissionProp21";
				break;
			case 37:
				strFieldName = "MissionProp22";
				break;
			case 38:
				strFieldName = "MissionProp23";
				break;
			case 39:
				strFieldName = "MissionProp24";
				break;
			case 40:
				strFieldName = "MissionProp25";
				break;
			case 41:
				strFieldName = "TimeID";
				break;
			case 42:
				strFieldName = "StandEndDate";
				break;
			case 43:
				strFieldName = "StandEndTime";
				break;
			case 44:
				strFieldName = "OperateCom";
				break;
			case 45:
				strFieldName = "MainMissionID";
				break;
			case 46:
				strFieldName = "SQLPriorityID";
				break;
			case 47:
				strFieldName = "PriorityID";
				break;
			case 48:
				strFieldName = "VERSION";
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
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityStatus") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp16") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp17") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp18") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp19") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp20") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreateOperator") ) {
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
		if( strFieldName.equals("InDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp21") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp22") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp23") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp24") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MissionProp25") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TimeID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandEndTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperateCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainMissionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SQLPriorityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriorityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VERSION") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
