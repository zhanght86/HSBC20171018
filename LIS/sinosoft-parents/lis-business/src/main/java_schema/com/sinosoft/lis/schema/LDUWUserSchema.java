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
import com.sinosoft.lis.db.LDUWUserDB;

/*
 * <p>ClassName: LDUWUserSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWUserSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWUserSchema.class);
	// @Field
	/** 核保师编码 */
	private String UserCode;
	/** 核保师类别 */
	private String UWType;
	/** 上级核保师 */
	private String UpUserCode;
	/** 上级核保级别 */
	private String UpUWPopedom;
	/** 其他上级核保师 */
	private String OtherUserCode;
	/** 其他上级核保师级别 */
	private String OtherUpUWPopedom;
	/** 核保师组别 */
	private String UWBranchCode;
	/** 核保权限 */
	private String UWPopedom;
	/** 核保师权限名称描述 */
	private String UserDescription;
	/** 核保师状态 */
	private String UserState;
	/** 首席核保标志 */
	private String PopUWFlag;
	/** 有效开始日期 */
	private Date ValidStartDate;
	/** 有效结束日期 */
	private Date ValidEndDate;
	/** 是否暂停 */
	private String IsPendFlag;
	/** 暂停原因 */
	private String PendReason;
	/** 备注 */
	private String Remark;
	/** 操作员代码 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 加费评点 */
	private int AddPoint;
	/** 理赔权限 */
	private String ClaimPopedom;
	/** 保全权限 */
	private String EdorPopedom;
	/** 核保费率 */
	private double UWRate;
	/** 险种比例 */
	private double RiskRate;
	/** 特殊职业权限 */
	private String SpecJob;
	/** 最低保额权限 */
	private String LowestAmnt;
	/** 超龄人员权限 */
	private String OverAge;
	/** 核保中间动作权限 */
	private String UWProcessFlag;
	/** 超权限操作标记 */
	private String SurpassGradeFlag;
	/** 是否有员工单操作标志 */
	private String UWOperatorFlag;
	/** 核保主管标识 */
	private String SupervisorFlag;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 35;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWUserSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "UserCode";
		pk[1] = "UWType";

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
		LDUWUserSchema cloned = (LDUWUserSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String aUserCode)
	{
		if(aUserCode!=null && aUserCode.length()>10)
			throw new IllegalArgumentException("核保师编码UserCode值"+aUserCode+"的长度"+aUserCode.length()+"大于最大值10");
		UserCode = aUserCode;
	}
	/**
	* 1-个险<p>
	* 2-团险
	*/
	public String getUWType()
	{
		return UWType;
	}
	public void setUWType(String aUWType)
	{
		if(aUWType!=null && aUWType.length()>1)
			throw new IllegalArgumentException("核保师类别UWType值"+aUWType+"的长度"+aUWType.length()+"大于最大值1");
		UWType = aUWType;
	}
	public String getUpUserCode()
	{
		return UpUserCode;
	}
	public void setUpUserCode(String aUpUserCode)
	{
		if(aUpUserCode!=null && aUpUserCode.length()>10)
			throw new IllegalArgumentException("上级核保师UpUserCode值"+aUpUserCode+"的长度"+aUpUserCode.length()+"大于最大值10");
		UpUserCode = aUpUserCode;
	}
	public String getUpUWPopedom()
	{
		return UpUWPopedom;
	}
	public void setUpUWPopedom(String aUpUWPopedom)
	{
		if(aUpUWPopedom!=null && aUpUWPopedom.length()>10)
			throw new IllegalArgumentException("上级核保级别UpUWPopedom值"+aUpUWPopedom+"的长度"+aUpUWPopedom.length()+"大于最大值10");
		UpUWPopedom = aUpUWPopedom;
	}
	/**
	* 01-银代经理<p>
	* 02-银代协理<p>
	* 03-渠道经理<p>
	* 04-银代客户经理<p>
	* 05-培训岗<p>
	* 06-企划策划岗<p>
	* 07-销售支援岗<p>
	* 08-综合岗<p>
	* <p>
	* <p>
	* 10-普通代理人<p>
	* 11-组主管<p>
	* 12-部主管<p>
	* 13-督导长<p>
	* 14-区域督导长<p>
	* <p>
	* <p>
	* 20-法人客户经理<p>
	* 21-法人组经理<p>
	* 22-???人协理<p>
	* 23－法人部经理
	*/
	public String getOtherUserCode()
	{
		return OtherUserCode;
	}
	public void setOtherUserCode(String aOtherUserCode)
	{
		if(aOtherUserCode!=null && aOtherUserCode.length()>10)
			throw new IllegalArgumentException("其他上级核保师OtherUserCode值"+aOtherUserCode+"的长度"+aOtherUserCode.length()+"大于最大值10");
		OtherUserCode = aOtherUserCode;
	}
	public String getOtherUpUWPopedom()
	{
		return OtherUpUWPopedom;
	}
	public void setOtherUpUWPopedom(String aOtherUpUWPopedom)
	{
		if(aOtherUpUWPopedom!=null && aOtherUpUWPopedom.length()>2)
			throw new IllegalArgumentException("其他上级核保师级别OtherUpUWPopedom值"+aOtherUpUWPopedom+"的长度"+aOtherUpUWPopedom.length()+"大于最大值2");
		OtherUpUWPopedom = aOtherUpUWPopedom;
	}
	public String getUWBranchCode()
	{
		return UWBranchCode;
	}
	public void setUWBranchCode(String aUWBranchCode)
	{
		if(aUWBranchCode!=null && aUWBranchCode.length()>12)
			throw new IllegalArgumentException("核保师组别UWBranchCode值"+aUWBranchCode+"的长度"+aUWBranchCode.length()+"大于最大值12");
		UWBranchCode = aUWBranchCode;
	}
	/**
	* uwpopedom	A	A级核保员<p>
	* uwpopedom	B	B级核保员<p>
	* uwpopedom	C	C级核保员<p>
	* uwpopedom	D	D级核保员<p>
	* uwpopedom	E	E级核保员<p>
	* uwpopedom	F	F级核保员
	*/
	public String getUWPopedom()
	{
		return UWPopedom;
	}
	public void setUWPopedom(String aUWPopedom)
	{
		if(aUWPopedom!=null && aUWPopedom.length()>10)
			throw new IllegalArgumentException("核保权限UWPopedom值"+aUWPopedom+"的长度"+aUWPopedom.length()+"大于最大值10");
		UWPopedom = aUWPopedom;
	}
	public String getUserDescription()
	{
		return UserDescription;
	}
	public void setUserDescription(String aUserDescription)
	{
		if(aUserDescription!=null && aUserDescription.length()>200)
			throw new IllegalArgumentException("核保师权限名称描述UserDescription值"+aUserDescription+"的长度"+aUserDescription.length()+"大于最大值200");
		UserDescription = aUserDescription;
	}
	/**
	* userstate	0	有效<p>
	* userstate	1	失效
	*/
	public String getUserState()
	{
		return UserState;
	}
	public void setUserState(String aUserState)
	{
		if(aUserState!=null && aUserState.length()>1)
			throw new IllegalArgumentException("核保师状态UserState值"+aUserState+"的长度"+aUserState.length()+"大于最大值1");
		UserState = aUserState;
	}
	public String getPopUWFlag()
	{
		return PopUWFlag;
	}
	public void setPopUWFlag(String aPopUWFlag)
	{
		if(aPopUWFlag!=null && aPopUWFlag.length()>1)
			throw new IllegalArgumentException("首席核保标志PopUWFlag值"+aPopUWFlag+"的长度"+aPopUWFlag.length()+"大于最大值1");
		PopUWFlag = aPopUWFlag;
	}
	public String getValidStartDate()
	{
		if( ValidStartDate != null )
			return fDate.getString(ValidStartDate);
		else
			return null;
	}
	public void setValidStartDate(Date aValidStartDate)
	{
		ValidStartDate = aValidStartDate;
	}
	public void setValidStartDate(String aValidStartDate)
	{
		if (aValidStartDate != null && !aValidStartDate.equals("") )
		{
			ValidStartDate = fDate.getDate( aValidStartDate );
		}
		else
			ValidStartDate = null;
	}

	public String getValidEndDate()
	{
		if( ValidEndDate != null )
			return fDate.getString(ValidEndDate);
		else
			return null;
	}
	public void setValidEndDate(Date aValidEndDate)
	{
		ValidEndDate = aValidEndDate;
	}
	public void setValidEndDate(String aValidEndDate)
	{
		if (aValidEndDate != null && !aValidEndDate.equals("") )
		{
			ValidEndDate = fDate.getDate( aValidEndDate );
		}
		else
			ValidEndDate = null;
	}

	public String getIsPendFlag()
	{
		return IsPendFlag;
	}
	public void setIsPendFlag(String aIsPendFlag)
	{
		if(aIsPendFlag!=null && aIsPendFlag.length()>1)
			throw new IllegalArgumentException("是否暂停IsPendFlag值"+aIsPendFlag+"的长度"+aIsPendFlag.length()+"大于最大值1");
		IsPendFlag = aIsPendFlag;
	}
	public String getPendReason()
	{
		return PendReason;
	}
	public void setPendReason(String aPendReason)
	{
		if(aPendReason!=null && aPendReason.length()>120)
			throw new IllegalArgumentException("暂停原因PendReason值"+aPendReason+"的长度"+aPendReason.length()+"大于最大值120");
		PendReason = aPendReason;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>120)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值120");
		Remark = aRemark;
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
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
	public int getAddPoint()
	{
		return AddPoint;
	}
	public void setAddPoint(int aAddPoint)
	{
		AddPoint = aAddPoint;
	}
	public void setAddPoint(String aAddPoint)
	{
		if (aAddPoint != null && !aAddPoint.equals(""))
		{
			Integer tInteger = new Integer(aAddPoint);
			int i = tInteger.intValue();
			AddPoint = i;
		}
	}

	/**
	* Y-有<p>
	* N-无
	*/
	public String getClaimPopedom()
	{
		return ClaimPopedom;
	}
	public void setClaimPopedom(String aClaimPopedom)
	{
		if(aClaimPopedom!=null && aClaimPopedom.length()>2)
			throw new IllegalArgumentException("理赔权限ClaimPopedom值"+aClaimPopedom+"的长度"+aClaimPopedom.length()+"大于最大值2");
		ClaimPopedom = aClaimPopedom;
	}
	/**
	* Y-有<p>
	* N-无
	*/
	public String getEdorPopedom()
	{
		return EdorPopedom;
	}
	public void setEdorPopedom(String aEdorPopedom)
	{
		if(aEdorPopedom!=null && aEdorPopedom.length()>2)
			throw new IllegalArgumentException("保全权限EdorPopedom值"+aEdorPopedom+"的长度"+aEdorPopedom.length()+"大于最大值2");
		EdorPopedom = aEdorPopedom;
	}
	public double getUWRate()
	{
		return UWRate;
	}
	public void setUWRate(double aUWRate)
	{
		UWRate = aUWRate;
	}
	public void setUWRate(String aUWRate)
	{
		if (aUWRate != null && !aUWRate.equals(""))
		{
			Double tDouble = new Double(aUWRate);
			double d = tDouble.doubleValue();
			UWRate = d;
		}
	}

	public double getRiskRate()
	{
		return RiskRate;
	}
	public void setRiskRate(double aRiskRate)
	{
		RiskRate = aRiskRate;
	}
	public void setRiskRate(String aRiskRate)
	{
		if (aRiskRate != null && !aRiskRate.equals(""))
		{
			Double tDouble = new Double(aRiskRate);
			double d = tDouble.doubleValue();
			RiskRate = d;
		}
	}

	public String getSpecJob()
	{
		return SpecJob;
	}
	public void setSpecJob(String aSpecJob)
	{
		if(aSpecJob!=null && aSpecJob.length()>2)
			throw new IllegalArgumentException("特殊职业权限SpecJob值"+aSpecJob+"的长度"+aSpecJob.length()+"大于最大值2");
		SpecJob = aSpecJob;
	}
	public String getLowestAmnt()
	{
		return LowestAmnt;
	}
	public void setLowestAmnt(String aLowestAmnt)
	{
		if(aLowestAmnt!=null && aLowestAmnt.length()>2)
			throw new IllegalArgumentException("最低保额权限LowestAmnt值"+aLowestAmnt+"的长度"+aLowestAmnt.length()+"大于最大值2");
		LowestAmnt = aLowestAmnt;
	}
	public String getOverAge()
	{
		return OverAge;
	}
	public void setOverAge(String aOverAge)
	{
		if(aOverAge!=null && aOverAge.length()>2)
			throw new IllegalArgumentException("超龄人员权限OverAge值"+aOverAge+"的长度"+aOverAge.length()+"大于最大值2");
		OverAge = aOverAge;
	}
	/**
	* 0-无核保中间动作权限<p>
	* 1-有核保中间动作权限<p>
	* <p>
	* 对于临时核保师,只能录入问题件,体检通知书,生调通知书,但是不能点击发送核保通知书,再报呈报,核保等待等功能.即不能做改变保单核保状态的操作.<p>
	* <p>
	* 1-有中间核保动作权限的核保师,可以做以上操作
	*/
	public String getUWProcessFlag()
	{
		return UWProcessFlag;
	}
	public void setUWProcessFlag(String aUWProcessFlag)
	{
		if(aUWProcessFlag!=null && aUWProcessFlag.length()>2)
			throw new IllegalArgumentException("核保中间动作权限UWProcessFlag值"+aUWProcessFlag+"的长度"+aUWProcessFlag.length()+"大于最大值2");
		UWProcessFlag = aUWProcessFlag;
	}
	/**
	* 0-不可以做超权限核保<p>
	* 1-可以做超权限核保,但是不能下核保结论.
	*/
	public String getSurpassGradeFlag()
	{
		return SurpassGradeFlag;
	}
	public void setSurpassGradeFlag(String aSurpassGradeFlag)
	{
		if(aSurpassGradeFlag!=null && aSurpassGradeFlag.length()>2)
			throw new IllegalArgumentException("超权限操作标记SurpassGradeFlag值"+aSurpassGradeFlag+"的长度"+aSurpassGradeFlag.length()+"大于最大值2");
		SurpassGradeFlag = aSurpassGradeFlag;
	}
	/**
	* Y表示有
	*/
	public String getUWOperatorFlag()
	{
		return UWOperatorFlag;
	}
	public void setUWOperatorFlag(String aUWOperatorFlag)
	{
		if(aUWOperatorFlag!=null && aUWOperatorFlag.length()>2)
			throw new IllegalArgumentException("是否有员工单操作标志UWOperatorFlag值"+aUWOperatorFlag+"的长度"+aUWOperatorFlag.length()+"大于最大值2");
		UWOperatorFlag = aUWOperatorFlag;
	}
	public String getSupervisorFlag()
	{
		return SupervisorFlag;
	}
	public void setSupervisorFlag(String aSupervisorFlag)
	{
		if(aSupervisorFlag!=null && aSupervisorFlag.length()>2)
			throw new IllegalArgumentException("核保主管标识SupervisorFlag值"+aSupervisorFlag+"的长度"+aSupervisorFlag.length()+"大于最大值2");
		SupervisorFlag = aSupervisorFlag;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 LDUWUserSchema 对象给 Schema 赋值
	* @param: aLDUWUserSchema LDUWUserSchema
	**/
	public void setSchema(LDUWUserSchema aLDUWUserSchema)
	{
		this.UserCode = aLDUWUserSchema.getUserCode();
		this.UWType = aLDUWUserSchema.getUWType();
		this.UpUserCode = aLDUWUserSchema.getUpUserCode();
		this.UpUWPopedom = aLDUWUserSchema.getUpUWPopedom();
		this.OtherUserCode = aLDUWUserSchema.getOtherUserCode();
		this.OtherUpUWPopedom = aLDUWUserSchema.getOtherUpUWPopedom();
		this.UWBranchCode = aLDUWUserSchema.getUWBranchCode();
		this.UWPopedom = aLDUWUserSchema.getUWPopedom();
		this.UserDescription = aLDUWUserSchema.getUserDescription();
		this.UserState = aLDUWUserSchema.getUserState();
		this.PopUWFlag = aLDUWUserSchema.getPopUWFlag();
		this.ValidStartDate = fDate.getDate( aLDUWUserSchema.getValidStartDate());
		this.ValidEndDate = fDate.getDate( aLDUWUserSchema.getValidEndDate());
		this.IsPendFlag = aLDUWUserSchema.getIsPendFlag();
		this.PendReason = aLDUWUserSchema.getPendReason();
		this.Remark = aLDUWUserSchema.getRemark();
		this.Operator = aLDUWUserSchema.getOperator();
		this.ManageCom = aLDUWUserSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLDUWUserSchema.getMakeDate());
		this.MakeTime = aLDUWUserSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDUWUserSchema.getModifyDate());
		this.ModifyTime = aLDUWUserSchema.getModifyTime();
		this.AddPoint = aLDUWUserSchema.getAddPoint();
		this.ClaimPopedom = aLDUWUserSchema.getClaimPopedom();
		this.EdorPopedom = aLDUWUserSchema.getEdorPopedom();
		this.UWRate = aLDUWUserSchema.getUWRate();
		this.RiskRate = aLDUWUserSchema.getRiskRate();
		this.SpecJob = aLDUWUserSchema.getSpecJob();
		this.LowestAmnt = aLDUWUserSchema.getLowestAmnt();
		this.OverAge = aLDUWUserSchema.getOverAge();
		this.UWProcessFlag = aLDUWUserSchema.getUWProcessFlag();
		this.SurpassGradeFlag = aLDUWUserSchema.getSurpassGradeFlag();
		this.UWOperatorFlag = aLDUWUserSchema.getUWOperatorFlag();
		this.SupervisorFlag = aLDUWUserSchema.getSupervisorFlag();
		this.ModifyOperator = aLDUWUserSchema.getModifyOperator();
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
			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("UWType") == null )
				this.UWType = null;
			else
				this.UWType = rs.getString("UWType").trim();

			if( rs.getString("UpUserCode") == null )
				this.UpUserCode = null;
			else
				this.UpUserCode = rs.getString("UpUserCode").trim();

			if( rs.getString("UpUWPopedom") == null )
				this.UpUWPopedom = null;
			else
				this.UpUWPopedom = rs.getString("UpUWPopedom").trim();

			if( rs.getString("OtherUserCode") == null )
				this.OtherUserCode = null;
			else
				this.OtherUserCode = rs.getString("OtherUserCode").trim();

			if( rs.getString("OtherUpUWPopedom") == null )
				this.OtherUpUWPopedom = null;
			else
				this.OtherUpUWPopedom = rs.getString("OtherUpUWPopedom").trim();

			if( rs.getString("UWBranchCode") == null )
				this.UWBranchCode = null;
			else
				this.UWBranchCode = rs.getString("UWBranchCode").trim();

			if( rs.getString("UWPopedom") == null )
				this.UWPopedom = null;
			else
				this.UWPopedom = rs.getString("UWPopedom").trim();

			if( rs.getString("UserDescription") == null )
				this.UserDescription = null;
			else
				this.UserDescription = rs.getString("UserDescription").trim();

			if( rs.getString("UserState") == null )
				this.UserState = null;
			else
				this.UserState = rs.getString("UserState").trim();

			if( rs.getString("PopUWFlag") == null )
				this.PopUWFlag = null;
			else
				this.PopUWFlag = rs.getString("PopUWFlag").trim();

			this.ValidStartDate = rs.getDate("ValidStartDate");
			this.ValidEndDate = rs.getDate("ValidEndDate");
			if( rs.getString("IsPendFlag") == null )
				this.IsPendFlag = null;
			else
				this.IsPendFlag = rs.getString("IsPendFlag").trim();

			if( rs.getString("PendReason") == null )
				this.PendReason = null;
			else
				this.PendReason = rs.getString("PendReason").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			this.AddPoint = rs.getInt("AddPoint");
			if( rs.getString("ClaimPopedom") == null )
				this.ClaimPopedom = null;
			else
				this.ClaimPopedom = rs.getString("ClaimPopedom").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

			this.UWRate = rs.getDouble("UWRate");
			this.RiskRate = rs.getDouble("RiskRate");
			if( rs.getString("SpecJob") == null )
				this.SpecJob = null;
			else
				this.SpecJob = rs.getString("SpecJob").trim();

			if( rs.getString("LowestAmnt") == null )
				this.LowestAmnt = null;
			else
				this.LowestAmnt = rs.getString("LowestAmnt").trim();

			if( rs.getString("OverAge") == null )
				this.OverAge = null;
			else
				this.OverAge = rs.getString("OverAge").trim();

			if( rs.getString("UWProcessFlag") == null )
				this.UWProcessFlag = null;
			else
				this.UWProcessFlag = rs.getString("UWProcessFlag").trim();

			if( rs.getString("SurpassGradeFlag") == null )
				this.SurpassGradeFlag = null;
			else
				this.SurpassGradeFlag = rs.getString("SurpassGradeFlag").trim();

			if( rs.getString("UWOperatorFlag") == null )
				this.UWOperatorFlag = null;
			else
				this.UWOperatorFlag = rs.getString("UWOperatorFlag").trim();

			if( rs.getString("SupervisorFlag") == null )
				this.SupervisorFlag = null;
			else
				this.SupervisorFlag = rs.getString("SupervisorFlag").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWUser表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWUserSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWUserSchema getSchema()
	{
		LDUWUserSchema aLDUWUserSchema = new LDUWUserSchema();
		aLDUWUserSchema.setSchema(this);
		return aLDUWUserSchema;
	}

	public LDUWUserDB getDB()
	{
		LDUWUserDB aDBOper = new LDUWUserDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWUser描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpUWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherUpUWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWBranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PopUWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValidStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValidEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsPendFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PendReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddPoint));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecJob)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LowestAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OverAge)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWProcessFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurpassGradeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperatorFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SupervisorFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWUser>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UpUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UpUWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherUpUWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWBranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UserDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UserState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PopUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ValidStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ValidEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			IsPendFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PendReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AddPoint= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			ClaimPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			UWRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			RiskRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			SpecJob = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			LowestAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			OverAge = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			UWProcessFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			SurpassGradeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			UWOperatorFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			SupervisorFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWUserSchema";
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
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWType));
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpUserCode));
		}
		if (FCode.equalsIgnoreCase("UpUWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpUWPopedom));
		}
		if (FCode.equalsIgnoreCase("OtherUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherUserCode));
		}
		if (FCode.equalsIgnoreCase("OtherUpUWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherUpUWPopedom));
		}
		if (FCode.equalsIgnoreCase("UWBranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWBranchCode));
		}
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedom));
		}
		if (FCode.equalsIgnoreCase("UserDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserDescription));
		}
		if (FCode.equalsIgnoreCase("UserState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserState));
		}
		if (FCode.equalsIgnoreCase("PopUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopUWFlag));
		}
		if (FCode.equalsIgnoreCase("ValidStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidStartDate()));
		}
		if (FCode.equalsIgnoreCase("ValidEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidEndDate()));
		}
		if (FCode.equalsIgnoreCase("IsPendFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsPendFlag));
		}
		if (FCode.equalsIgnoreCase("PendReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PendReason));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("AddPoint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPoint));
		}
		if (FCode.equalsIgnoreCase("ClaimPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimPopedom));
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
		}
		if (FCode.equalsIgnoreCase("UWRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWRate));
		}
		if (FCode.equalsIgnoreCase("RiskRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskRate));
		}
		if (FCode.equalsIgnoreCase("SpecJob"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecJob));
		}
		if (FCode.equalsIgnoreCase("LowestAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LowestAmnt));
		}
		if (FCode.equalsIgnoreCase("OverAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OverAge));
		}
		if (FCode.equalsIgnoreCase("UWProcessFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWProcessFlag));
		}
		if (FCode.equalsIgnoreCase("SurpassGradeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurpassGradeFlag));
		}
		if (FCode.equalsIgnoreCase("UWOperatorFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperatorFlag));
		}
		if (FCode.equalsIgnoreCase("SupervisorFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SupervisorFlag));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UWType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(UpUserCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UpUWPopedom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherUserCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherUpUWPopedom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UWBranchCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UWPopedom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UserDescription);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UserState);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PopUWFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidStartDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidEndDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IsPendFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PendReason);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = String.valueOf(AddPoint);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ClaimPopedom);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
				break;
			case 25:
				strFieldValue = String.valueOf(UWRate);
				break;
			case 26:
				strFieldValue = String.valueOf(RiskRate);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(SpecJob);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(LowestAmnt);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(OverAge);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(UWProcessFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(SurpassGradeFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(UWOperatorFlag);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SupervisorFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWType = FValue.trim();
			}
			else
				UWType = null;
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpUserCode = FValue.trim();
			}
			else
				UpUserCode = null;
		}
		if (FCode.equalsIgnoreCase("UpUWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpUWPopedom = FValue.trim();
			}
			else
				UpUWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("OtherUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherUserCode = FValue.trim();
			}
			else
				OtherUserCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherUpUWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherUpUWPopedom = FValue.trim();
			}
			else
				OtherUpUWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWBranchCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWBranchCode = FValue.trim();
			}
			else
				UWBranchCode = null;
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
		if (FCode.equalsIgnoreCase("UserDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserDescription = FValue.trim();
			}
			else
				UserDescription = null;
		}
		if (FCode.equalsIgnoreCase("UserState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserState = FValue.trim();
			}
			else
				UserState = null;
		}
		if (FCode.equalsIgnoreCase("PopUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PopUWFlag = FValue.trim();
			}
			else
				PopUWFlag = null;
		}
		if (FCode.equalsIgnoreCase("ValidStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidStartDate = fDate.getDate( FValue );
			}
			else
				ValidStartDate = null;
		}
		if (FCode.equalsIgnoreCase("ValidEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidEndDate = fDate.getDate( FValue );
			}
			else
				ValidEndDate = null;
		}
		if (FCode.equalsIgnoreCase("IsPendFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsPendFlag = FValue.trim();
			}
			else
				IsPendFlag = null;
		}
		if (FCode.equalsIgnoreCase("PendReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PendReason = FValue.trim();
			}
			else
				PendReason = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("AddPoint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AddPoint = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimPopedom = FValue.trim();
			}
			else
				ClaimPopedom = null;
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorPopedom = FValue.trim();
			}
			else
				EdorPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UWRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RiskRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("SpecJob"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecJob = FValue.trim();
			}
			else
				SpecJob = null;
		}
		if (FCode.equalsIgnoreCase("LowestAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LowestAmnt = FValue.trim();
			}
			else
				LowestAmnt = null;
		}
		if (FCode.equalsIgnoreCase("OverAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OverAge = FValue.trim();
			}
			else
				OverAge = null;
		}
		if (FCode.equalsIgnoreCase("UWProcessFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWProcessFlag = FValue.trim();
			}
			else
				UWProcessFlag = null;
		}
		if (FCode.equalsIgnoreCase("SurpassGradeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurpassGradeFlag = FValue.trim();
			}
			else
				SurpassGradeFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWOperatorFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperatorFlag = FValue.trim();
			}
			else
				UWOperatorFlag = null;
		}
		if (FCode.equalsIgnoreCase("SupervisorFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SupervisorFlag = FValue.trim();
			}
			else
				SupervisorFlag = null;
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWUserSchema other = (LDUWUserSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& UWType.equals(other.getUWType())
			&& UpUserCode.equals(other.getUpUserCode())
			&& UpUWPopedom.equals(other.getUpUWPopedom())
			&& OtherUserCode.equals(other.getOtherUserCode())
			&& OtherUpUWPopedom.equals(other.getOtherUpUWPopedom())
			&& UWBranchCode.equals(other.getUWBranchCode())
			&& UWPopedom.equals(other.getUWPopedom())
			&& UserDescription.equals(other.getUserDescription())
			&& UserState.equals(other.getUserState())
			&& PopUWFlag.equals(other.getPopUWFlag())
			&& fDate.getString(ValidStartDate).equals(other.getValidStartDate())
			&& fDate.getString(ValidEndDate).equals(other.getValidEndDate())
			&& IsPendFlag.equals(other.getIsPendFlag())
			&& PendReason.equals(other.getPendReason())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AddPoint == other.getAddPoint()
			&& ClaimPopedom.equals(other.getClaimPopedom())
			&& EdorPopedom.equals(other.getEdorPopedom())
			&& UWRate == other.getUWRate()
			&& RiskRate == other.getRiskRate()
			&& SpecJob.equals(other.getSpecJob())
			&& LowestAmnt.equals(other.getLowestAmnt())
			&& OverAge.equals(other.getOverAge())
			&& UWProcessFlag.equals(other.getUWProcessFlag())
			&& SurpassGradeFlag.equals(other.getSurpassGradeFlag())
			&& UWOperatorFlag.equals(other.getUWOperatorFlag())
			&& SupervisorFlag.equals(other.getSupervisorFlag())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("UserCode") ) {
			return 0;
		}
		if( strFieldName.equals("UWType") ) {
			return 1;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return 2;
		}
		if( strFieldName.equals("UpUWPopedom") ) {
			return 3;
		}
		if( strFieldName.equals("OtherUserCode") ) {
			return 4;
		}
		if( strFieldName.equals("OtherUpUWPopedom") ) {
			return 5;
		}
		if( strFieldName.equals("UWBranchCode") ) {
			return 6;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return 7;
		}
		if( strFieldName.equals("UserDescription") ) {
			return 8;
		}
		if( strFieldName.equals("UserState") ) {
			return 9;
		}
		if( strFieldName.equals("PopUWFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ValidStartDate") ) {
			return 11;
		}
		if( strFieldName.equals("ValidEndDate") ) {
			return 12;
		}
		if( strFieldName.equals("IsPendFlag") ) {
			return 13;
		}
		if( strFieldName.equals("PendReason") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("AddPoint") ) {
			return 22;
		}
		if( strFieldName.equals("ClaimPopedom") ) {
			return 23;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return 24;
		}
		if( strFieldName.equals("UWRate") ) {
			return 25;
		}
		if( strFieldName.equals("RiskRate") ) {
			return 26;
		}
		if( strFieldName.equals("SpecJob") ) {
			return 27;
		}
		if( strFieldName.equals("LowestAmnt") ) {
			return 28;
		}
		if( strFieldName.equals("OverAge") ) {
			return 29;
		}
		if( strFieldName.equals("UWProcessFlag") ) {
			return 30;
		}
		if( strFieldName.equals("SurpassGradeFlag") ) {
			return 31;
		}
		if( strFieldName.equals("UWOperatorFlag") ) {
			return 32;
		}
		if( strFieldName.equals("SupervisorFlag") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 34;
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
				strFieldName = "UserCode";
				break;
			case 1:
				strFieldName = "UWType";
				break;
			case 2:
				strFieldName = "UpUserCode";
				break;
			case 3:
				strFieldName = "UpUWPopedom";
				break;
			case 4:
				strFieldName = "OtherUserCode";
				break;
			case 5:
				strFieldName = "OtherUpUWPopedom";
				break;
			case 6:
				strFieldName = "UWBranchCode";
				break;
			case 7:
				strFieldName = "UWPopedom";
				break;
			case 8:
				strFieldName = "UserDescription";
				break;
			case 9:
				strFieldName = "UserState";
				break;
			case 10:
				strFieldName = "PopUWFlag";
				break;
			case 11:
				strFieldName = "ValidStartDate";
				break;
			case 12:
				strFieldName = "ValidEndDate";
				break;
			case 13:
				strFieldName = "IsPendFlag";
				break;
			case 14:
				strFieldName = "PendReason";
				break;
			case 15:
				strFieldName = "Remark";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "ManageCom";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "AddPoint";
				break;
			case 23:
				strFieldName = "ClaimPopedom";
				break;
			case 24:
				strFieldName = "EdorPopedom";
				break;
			case 25:
				strFieldName = "UWRate";
				break;
			case 26:
				strFieldName = "RiskRate";
				break;
			case 27:
				strFieldName = "SpecJob";
				break;
			case 28:
				strFieldName = "LowestAmnt";
				break;
			case 29:
				strFieldName = "OverAge";
				break;
			case 30:
				strFieldName = "UWProcessFlag";
				break;
			case 31:
				strFieldName = "SurpassGradeFlag";
				break;
			case 32:
				strFieldName = "UWOperatorFlag";
				break;
			case 33:
				strFieldName = "SupervisorFlag";
				break;
			case 34:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpUWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherUpUWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWBranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ValidEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IsPendFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PendReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("AddPoint") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClaimPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SpecJob") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LowestAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OverAge") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWProcessFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurpassGradeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOperatorFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SupervisorFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
