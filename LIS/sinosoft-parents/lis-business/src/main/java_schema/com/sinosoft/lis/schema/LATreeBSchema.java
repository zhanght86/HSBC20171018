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
import com.sinosoft.lis.db.LATreeBDB;

/*
 * <p>ClassName: LATreeBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LATreeBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LATreeBSchema.class);

	// @Field
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人展业机构代码 */
	private String AgentGroup;
	/** 管理机构 */
	private String ManageCom;
	/** 代理人系列 */
	private String AgentSeries;
	/** 代理人级别 */
	private String AgentGrade;
	/** 代理人上次系列 */
	private String AgentLastSeries;
	/** 代理人上次级别 */
	private String AgentLastGrade;
	/** 增员代理人 */
	private String IntroAgency;
	/** 上级代理人 */
	private String UpAgent;
	/** 代理人类别 */
	private String OthUpAgent;
	/** 增员链断裂标记 */
	private String IntroBreakFlag;
	/** 增员抽佣起期 */
	private Date IntroCommStart;
	/** 增员抽佣止期 */
	private Date IntroCommEnd;
	/** 育成主管代理人 */
	private String EduManager;
	/** 育成链断裂标记 */
	private String RearBreakFlag;
	/** 育成抽佣起期 */
	private Date RearCommStart;
	/** 育成抽佣止期 */
	private Date RearCommEnd;
	/** 归属顺序 */
	private String AscriptSeries;
	/** 前职级起聘日期 */
	private Date OldStartDate;
	/** 前职级解聘日期 */
	private Date OldEndDate;
	/** 现职级起聘日期 */
	private Date StartDate;
	/** 考核开始日期 */
	private Date AstartDate;
	/** 考核类型 */
	private String AssessType;
	/** 考核状态 */
	private String State;
	/** 原操作员代码 */
	private String Operator2;
	/** 原入机日期 */
	private Date MakeDate2;
	/** 原入机时间 */
	private String MakeTime2;
	/** 原最后一次修改日期 */
	private Date ModifyDate2;
	/** 原最后一次修改时间 */
	private String ModifyTime2;
	/** 转储类别 */
	private String RemoveType;
	/** 批改号 */
	private String EdorNO;
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
	/** 员工属性 */
	private String BranchCode;
	/** 指标计算编码 */
	private String IndexCalNo;
	/** 上次外部显示职级 */
	private String LastAgentGrade1;
	/** 外部显示职级 */
	private String AgentGrade1;
	/** 入司职级 */
	private String EmployGrade;

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LATreeBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AgentCode";
		pk[1] = "EdorNO";

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
		LATreeBSchema cloned = (LATreeBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAgentSeries()
	{
		return AgentSeries;
	}
	public void setAgentSeries(String aAgentSeries)
	{
		AgentSeries = aAgentSeries;
	}
	/**
	* A01:理财专员<p>
	* A02:理财主任<p>
	* A03：见习业务经理<p>
	* <p>
	* A04：业务经理一级<p>
	* <p>
	* A05:业务经理二级<p>
	* A06:高级业务经理一级<p>
	* <p>
	* A07:高级业务经理二级<p>
	* A08:督导长<p>
	* <p>
	* A09:区域督导长<p>
	* <p>
	* "
	*/
	public String getAgentGrade()
	{
		return AgentGrade;
	}
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	public String getAgentLastSeries()
	{
		return AgentLastSeries;
	}
	public void setAgentLastSeries(String aAgentLastSeries)
	{
		AgentLastSeries = aAgentLastSeries;
	}
	public String getAgentLastGrade()
	{
		return AgentLastGrade;
	}
	public void setAgentLastGrade(String aAgentLastGrade)
	{
		AgentLastGrade = aAgentLastGrade;
	}
	public String getIntroAgency()
	{
		return IntroAgency;
	}
	public void setIntroAgency(String aIntroAgency)
	{
		IntroAgency = aIntroAgency;
	}
	public String getUpAgent()
	{
		return UpAgent;
	}
	public void setUpAgent(String aUpAgent)
	{
		UpAgent = aUpAgent;
	}
	/**
	* 01-银代经理<p>
	* 02-银代协理<p>
	* 03-渠道经理<p>
	* 04-银代客户经理<p>
	* 05-培训岗<p>
	* <p>
	* 06-企划策划岗<p>
	* <p>
	* 07-销售支援岗<p>
	* 08-综合岗<p>
	* <p>
	* <p>
	* <p>
	* 10-普通代理人<p>
	* 11-组主管<p>
	* <p>
	* 12-部主管<p>
	* <p>
	* 13-督导长<p>
	* <p>
	* 14-区域督导长<p>
	* <p>
	* <p>
	* <p>
	* 20-法人客户经理<p>
	* 21-法人组经理<p>
	* <p>
	* 22-法人协理<p>
	* 23－法人部经理
	*/
	public String getOthUpAgent()
	{
		return OthUpAgent;
	}
	public void setOthUpAgent(String aOthUpAgent)
	{
		OthUpAgent = aOthUpAgent;
	}
	public String getIntroBreakFlag()
	{
		return IntroBreakFlag;
	}
	public void setIntroBreakFlag(String aIntroBreakFlag)
	{
		IntroBreakFlag = aIntroBreakFlag;
	}
	public String getIntroCommStart()
	{
		if( IntroCommStart != null )
			return fDate.getString(IntroCommStart);
		else
			return null;
	}
	public void setIntroCommStart(Date aIntroCommStart)
	{
		IntroCommStart = aIntroCommStart;
	}
	public void setIntroCommStart(String aIntroCommStart)
	{
		if (aIntroCommStart != null && !aIntroCommStart.equals("") )
		{
			IntroCommStart = fDate.getDate( aIntroCommStart );
		}
		else
			IntroCommStart = null;
	}

	public String getIntroCommEnd()
	{
		if( IntroCommEnd != null )
			return fDate.getString(IntroCommEnd);
		else
			return null;
	}
	public void setIntroCommEnd(Date aIntroCommEnd)
	{
		IntroCommEnd = aIntroCommEnd;
	}
	public void setIntroCommEnd(String aIntroCommEnd)
	{
		if (aIntroCommEnd != null && !aIntroCommEnd.equals("") )
		{
			IntroCommEnd = fDate.getDate( aIntroCommEnd );
		}
		else
			IntroCommEnd = null;
	}

	public String getEduManager()
	{
		return EduManager;
	}
	public void setEduManager(String aEduManager)
	{
		EduManager = aEduManager;
	}
	public String getRearBreakFlag()
	{
		return RearBreakFlag;
	}
	public void setRearBreakFlag(String aRearBreakFlag)
	{
		RearBreakFlag = aRearBreakFlag;
	}
	public String getRearCommStart()
	{
		if( RearCommStart != null )
			return fDate.getString(RearCommStart);
		else
			return null;
	}
	public void setRearCommStart(Date aRearCommStart)
	{
		RearCommStart = aRearCommStart;
	}
	public void setRearCommStart(String aRearCommStart)
	{
		if (aRearCommStart != null && !aRearCommStart.equals("") )
		{
			RearCommStart = fDate.getDate( aRearCommStart );
		}
		else
			RearCommStart = null;
	}

	public String getRearCommEnd()
	{
		if( RearCommEnd != null )
			return fDate.getString(RearCommEnd);
		else
			return null;
	}
	public void setRearCommEnd(Date aRearCommEnd)
	{
		RearCommEnd = aRearCommEnd;
	}
	public void setRearCommEnd(String aRearCommEnd)
	{
		if (aRearCommEnd != null && !aRearCommEnd.equals("") )
		{
			RearCommEnd = fDate.getDate( aRearCommEnd );
		}
		else
			RearCommEnd = null;
	}

	/**
	* 育成链，将育成代理人顺序加入
	*/
	public String getAscriptSeries()
	{
		return AscriptSeries;
	}
	public void setAscriptSeries(String aAscriptSeries)
	{
		AscriptSeries = aAscriptSeries;
	}
	public String getOldStartDate()
	{
		if( OldStartDate != null )
			return fDate.getString(OldStartDate);
		else
			return null;
	}
	public void setOldStartDate(Date aOldStartDate)
	{
		OldStartDate = aOldStartDate;
	}
	public void setOldStartDate(String aOldStartDate)
	{
		if (aOldStartDate != null && !aOldStartDate.equals("") )
		{
			OldStartDate = fDate.getDate( aOldStartDate );
		}
		else
			OldStartDate = null;
	}

	public String getOldEndDate()
	{
		if( OldEndDate != null )
			return fDate.getString(OldEndDate);
		else
			return null;
	}
	public void setOldEndDate(Date aOldEndDate)
	{
		OldEndDate = aOldEndDate;
	}
	public void setOldEndDate(String aOldEndDate)
	{
		if (aOldEndDate != null && !aOldEndDate.equals("") )
		{
			OldEndDate = fDate.getDate( aOldEndDate );
		}
		else
			OldEndDate = null;
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

	public String getAstartDate()
	{
		if( AstartDate != null )
			return fDate.getString(AstartDate);
		else
			return null;
	}
	public void setAstartDate(Date aAstartDate)
	{
		AstartDate = aAstartDate;
	}
	public void setAstartDate(String aAstartDate)
	{
		if (aAstartDate != null && !aAstartDate.equals("") )
		{
			AstartDate = fDate.getDate( aAstartDate );
		}
		else
			AstartDate = null;
	}

	/**
	* 正常 0<p>
	* 升级 1<p>
	* 降级 2<p>
	* 称号保留期 3
	*/
	public String getAssessType()
	{
		return AssessType;
	}
	public void setAssessType(String aAssessType)
	{
		AssessType = aAssessType;
	}
	/**
	* 考核状态（正常－0、升级－1、降级－2、离职-3）
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getOperator2()
	{
		return Operator2;
	}
	public void setOperator2(String aOperator2)
	{
		Operator2 = aOperator2;
	}
	public String getMakeDate2()
	{
		if( MakeDate2 != null )
			return fDate.getString(MakeDate2);
		else
			return null;
	}
	public void setMakeDate2(Date aMakeDate2)
	{
		MakeDate2 = aMakeDate2;
	}
	public void setMakeDate2(String aMakeDate2)
	{
		if (aMakeDate2 != null && !aMakeDate2.equals("") )
		{
			MakeDate2 = fDate.getDate( aMakeDate2 );
		}
		else
			MakeDate2 = null;
	}

	public String getMakeTime2()
	{
		return MakeTime2;
	}
	public void setMakeTime2(String aMakeTime2)
	{
		MakeTime2 = aMakeTime2;
	}
	public String getModifyDate2()
	{
		if( ModifyDate2 != null )
			return fDate.getString(ModifyDate2);
		else
			return null;
	}
	public void setModifyDate2(Date aModifyDate2)
	{
		ModifyDate2 = aModifyDate2;
	}
	public void setModifyDate2(String aModifyDate2)
	{
		if (aModifyDate2 != null && !aModifyDate2.equals("") )
		{
			ModifyDate2 = fDate.getDate( aModifyDate2 );
		}
		else
			ModifyDate2 = null;
	}

	public String getModifyTime2()
	{
		return ModifyTime2;
	}
	public void setModifyTime2(String aModifyTime2)
	{
		ModifyTime2 = aModifyTime2;
	}
	/**
	* 01-考核结果<p>
	* 02-机构调整<p>
	* 03-人员调动<p>
	* 04-二次增员<p>
	* 05-原始修改备份<p>
	* 06-轨迹修改备份（育成关系，所属关系）<p>
	* 07-主管任命修改
	*/
	public String getRemoveType()
	{
		return RemoveType;
	}
	public void setRemoveType(String aRemoveType)
	{
		RemoveType = aRemoveType;
	}
	public String getEdorNO()
	{
		return EdorNO;
	}
	public void setEdorNO(String aEdorNO)
	{
		EdorNO = aEdorNO;
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
		ModifyTime = aModifyTime;
	}
	/**
	* 00-非员工<p>
	* <p>
	* 01-员工
	*/
	public String getBranchCode()
	{
		return BranchCode;
	}
	public void setBranchCode(String aBranchCode)
	{
		BranchCode = aBranchCode;
	}
	public String getIndexCalNo()
	{
		return IndexCalNo;
	}
	public void setIndexCalNo(String aIndexCalNo)
	{
		IndexCalNo = aIndexCalNo;
	}
	public String getLastAgentGrade1()
	{
		return LastAgentGrade1;
	}
	public void setLastAgentGrade1(String aLastAgentGrade1)
	{
		LastAgentGrade1 = aLastAgentGrade1;
	}
	public String getAgentGrade1()
	{
		return AgentGrade1;
	}
	public void setAgentGrade1(String aAgentGrade1)
	{
		AgentGrade1 = aAgentGrade1;
	}
	/**
	* 入司职级 个险用<p>
	* <p>
	* like M%
	*/
	public String getEmployGrade()
	{
		return EmployGrade;
	}
	public void setEmployGrade(String aEmployGrade)
	{
		EmployGrade = aEmployGrade;
	}

	/**
	* 使用另外一个 LATreeBSchema 对象给 Schema 赋值
	* @param: aLATreeBSchema LATreeBSchema
	**/
	public void setSchema(LATreeBSchema aLATreeBSchema)
	{
		this.AgentCode = aLATreeBSchema.getAgentCode();
		this.AgentGroup = aLATreeBSchema.getAgentGroup();
		this.ManageCom = aLATreeBSchema.getManageCom();
		this.AgentSeries = aLATreeBSchema.getAgentSeries();
		this.AgentGrade = aLATreeBSchema.getAgentGrade();
		this.AgentLastSeries = aLATreeBSchema.getAgentLastSeries();
		this.AgentLastGrade = aLATreeBSchema.getAgentLastGrade();
		this.IntroAgency = aLATreeBSchema.getIntroAgency();
		this.UpAgent = aLATreeBSchema.getUpAgent();
		this.OthUpAgent = aLATreeBSchema.getOthUpAgent();
		this.IntroBreakFlag = aLATreeBSchema.getIntroBreakFlag();
		this.IntroCommStart = fDate.getDate( aLATreeBSchema.getIntroCommStart());
		this.IntroCommEnd = fDate.getDate( aLATreeBSchema.getIntroCommEnd());
		this.EduManager = aLATreeBSchema.getEduManager();
		this.RearBreakFlag = aLATreeBSchema.getRearBreakFlag();
		this.RearCommStart = fDate.getDate( aLATreeBSchema.getRearCommStart());
		this.RearCommEnd = fDate.getDate( aLATreeBSchema.getRearCommEnd());
		this.AscriptSeries = aLATreeBSchema.getAscriptSeries();
		this.OldStartDate = fDate.getDate( aLATreeBSchema.getOldStartDate());
		this.OldEndDate = fDate.getDate( aLATreeBSchema.getOldEndDate());
		this.StartDate = fDate.getDate( aLATreeBSchema.getStartDate());
		this.AstartDate = fDate.getDate( aLATreeBSchema.getAstartDate());
		this.AssessType = aLATreeBSchema.getAssessType();
		this.State = aLATreeBSchema.getState();
		this.Operator2 = aLATreeBSchema.getOperator2();
		this.MakeDate2 = fDate.getDate( aLATreeBSchema.getMakeDate2());
		this.MakeTime2 = aLATreeBSchema.getMakeTime2();
		this.ModifyDate2 = fDate.getDate( aLATreeBSchema.getModifyDate2());
		this.ModifyTime2 = aLATreeBSchema.getModifyTime2();
		this.RemoveType = aLATreeBSchema.getRemoveType();
		this.EdorNO = aLATreeBSchema.getEdorNO();
		this.Operator = aLATreeBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLATreeBSchema.getMakeDate());
		this.MakeTime = aLATreeBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLATreeBSchema.getModifyDate());
		this.ModifyTime = aLATreeBSchema.getModifyTime();
		this.BranchCode = aLATreeBSchema.getBranchCode();
		this.IndexCalNo = aLATreeBSchema.getIndexCalNo();
		this.LastAgentGrade1 = aLATreeBSchema.getLastAgentGrade1();
		this.AgentGrade1 = aLATreeBSchema.getAgentGrade1();
		this.EmployGrade = aLATreeBSchema.getEmployGrade();
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
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentSeries") == null )
				this.AgentSeries = null;
			else
				this.AgentSeries = rs.getString("AgentSeries").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			if( rs.getString("AgentLastSeries") == null )
				this.AgentLastSeries = null;
			else
				this.AgentLastSeries = rs.getString("AgentLastSeries").trim();

			if( rs.getString("AgentLastGrade") == null )
				this.AgentLastGrade = null;
			else
				this.AgentLastGrade = rs.getString("AgentLastGrade").trim();

			if( rs.getString("IntroAgency") == null )
				this.IntroAgency = null;
			else
				this.IntroAgency = rs.getString("IntroAgency").trim();

			if( rs.getString("UpAgent") == null )
				this.UpAgent = null;
			else
				this.UpAgent = rs.getString("UpAgent").trim();

			if( rs.getString("OthUpAgent") == null )
				this.OthUpAgent = null;
			else
				this.OthUpAgent = rs.getString("OthUpAgent").trim();

			if( rs.getString("IntroBreakFlag") == null )
				this.IntroBreakFlag = null;
			else
				this.IntroBreakFlag = rs.getString("IntroBreakFlag").trim();

			this.IntroCommStart = rs.getDate("IntroCommStart");
			this.IntroCommEnd = rs.getDate("IntroCommEnd");
			if( rs.getString("EduManager") == null )
				this.EduManager = null;
			else
				this.EduManager = rs.getString("EduManager").trim();

			if( rs.getString("RearBreakFlag") == null )
				this.RearBreakFlag = null;
			else
				this.RearBreakFlag = rs.getString("RearBreakFlag").trim();

			this.RearCommStart = rs.getDate("RearCommStart");
			this.RearCommEnd = rs.getDate("RearCommEnd");
			if( rs.getString("AscriptSeries") == null )
				this.AscriptSeries = null;
			else
				this.AscriptSeries = rs.getString("AscriptSeries").trim();

			this.OldStartDate = rs.getDate("OldStartDate");
			this.OldEndDate = rs.getDate("OldEndDate");
			this.StartDate = rs.getDate("StartDate");
			this.AstartDate = rs.getDate("AstartDate");
			if( rs.getString("AssessType") == null )
				this.AssessType = null;
			else
				this.AssessType = rs.getString("AssessType").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Operator2") == null )
				this.Operator2 = null;
			else
				this.Operator2 = rs.getString("Operator2").trim();

			this.MakeDate2 = rs.getDate("MakeDate2");
			if( rs.getString("MakeTime2") == null )
				this.MakeTime2 = null;
			else
				this.MakeTime2 = rs.getString("MakeTime2").trim();

			this.ModifyDate2 = rs.getDate("ModifyDate2");
			if( rs.getString("ModifyTime2") == null )
				this.ModifyTime2 = null;
			else
				this.ModifyTime2 = rs.getString("ModifyTime2").trim();

			if( rs.getString("RemoveType") == null )
				this.RemoveType = null;
			else
				this.RemoveType = rs.getString("RemoveType").trim();

			if( rs.getString("EdorNO") == null )
				this.EdorNO = null;
			else
				this.EdorNO = rs.getString("EdorNO").trim();

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

			if( rs.getString("BranchCode") == null )
				this.BranchCode = null;
			else
				this.BranchCode = rs.getString("BranchCode").trim();

			if( rs.getString("IndexCalNo") == null )
				this.IndexCalNo = null;
			else
				this.IndexCalNo = rs.getString("IndexCalNo").trim();

			if( rs.getString("LastAgentGrade1") == null )
				this.LastAgentGrade1 = null;
			else
				this.LastAgentGrade1 = rs.getString("LastAgentGrade1").trim();

			if( rs.getString("AgentGrade1") == null )
				this.AgentGrade1 = null;
			else
				this.AgentGrade1 = rs.getString("AgentGrade1").trim();

			if( rs.getString("EmployGrade") == null )
				this.EmployGrade = null;
			else
				this.EmployGrade = rs.getString("EmployGrade").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LATreeB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LATreeBSchema getSchema()
	{
		LATreeBSchema aLATreeBSchema = new LATreeBSchema();
		aLATreeBSchema.setSchema(this);
		return aLATreeBSchema;
	}

	public LATreeBDB getDB()
	{
		LATreeBDB aDBOper = new LATreeBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATreeB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentSeries)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentLastSeries)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentLastGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IntroAgency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpAgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthUpAgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IntroBreakFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IntroCommStart ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IntroCommEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EduManager)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RearBreakFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RearCommStart ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RearCommEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptSeries)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OldStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OldEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AstartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RemoveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexCalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastAgentGrade1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EmployGrade));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATreeB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AgentSeries = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentLastSeries = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentLastGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IntroAgency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UpAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OthUpAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IntroBreakFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IntroCommStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			IntroCommEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			EduManager = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RearBreakFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RearCommStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			RearCommEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			AscriptSeries = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			OldStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			OldEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			AstartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			AssessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Operator2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			RemoveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			EdorNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			BranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			LastAgentGrade1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			AgentGrade1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			EmployGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBSchema";
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentSeries"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentSeries));
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equalsIgnoreCase("AgentLastSeries"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentLastSeries));
		}
		if (FCode.equalsIgnoreCase("AgentLastGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentLastGrade));
		}
		if (FCode.equalsIgnoreCase("IntroAgency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IntroAgency));
		}
		if (FCode.equalsIgnoreCase("UpAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpAgent));
		}
		if (FCode.equalsIgnoreCase("OthUpAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthUpAgent));
		}
		if (FCode.equalsIgnoreCase("IntroBreakFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IntroBreakFlag));
		}
		if (FCode.equalsIgnoreCase("IntroCommStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIntroCommStart()));
		}
		if (FCode.equalsIgnoreCase("IntroCommEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIntroCommEnd()));
		}
		if (FCode.equalsIgnoreCase("EduManager"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EduManager));
		}
		if (FCode.equalsIgnoreCase("RearBreakFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RearBreakFlag));
		}
		if (FCode.equalsIgnoreCase("RearCommStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRearCommStart()));
		}
		if (FCode.equalsIgnoreCase("RearCommEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRearCommEnd()));
		}
		if (FCode.equalsIgnoreCase("AscriptSeries"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptSeries));
		}
		if (FCode.equalsIgnoreCase("OldStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOldStartDate()));
		}
		if (FCode.equalsIgnoreCase("OldEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOldEndDate()));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("AstartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAstartDate()));
		}
		if (FCode.equalsIgnoreCase("AssessType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssessType));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator2));
		}
		if (FCode.equalsIgnoreCase("MakeDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate2()));
		}
		if (FCode.equalsIgnoreCase("MakeTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime2));
		}
		if (FCode.equalsIgnoreCase("ModifyDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate2()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime2));
		}
		if (FCode.equalsIgnoreCase("RemoveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RemoveType));
		}
		if (FCode.equalsIgnoreCase("EdorNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNO));
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
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchCode));
		}
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexCalNo));
		}
		if (FCode.equalsIgnoreCase("LastAgentGrade1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastAgentGrade1));
		}
		if (FCode.equalsIgnoreCase("AgentGrade1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade1));
		}
		if (FCode.equalsIgnoreCase("EmployGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmployGrade));
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
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentSeries);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentLastSeries);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentLastGrade);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IntroAgency);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UpAgent);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OthUpAgent);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IntroBreakFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIntroCommStart()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIntroCommEnd()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(EduManager);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RearBreakFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRearCommStart()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRearCommEnd()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AscriptSeries);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOldStartDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOldEndDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAstartDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AssessType);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Operator2);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate2()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime2);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate2()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime2);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(RemoveType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(EdorNO);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(BranchCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(IndexCalNo);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(LastAgentGrade1);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade1);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(EmployGrade);
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

		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
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
		if (FCode.equalsIgnoreCase("AgentSeries"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentSeries = FValue.trim();
			}
			else
				AgentSeries = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
		}
		if (FCode.equalsIgnoreCase("AgentLastSeries"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentLastSeries = FValue.trim();
			}
			else
				AgentLastSeries = null;
		}
		if (FCode.equalsIgnoreCase("AgentLastGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentLastGrade = FValue.trim();
			}
			else
				AgentLastGrade = null;
		}
		if (FCode.equalsIgnoreCase("IntroAgency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IntroAgency = FValue.trim();
			}
			else
				IntroAgency = null;
		}
		if (FCode.equalsIgnoreCase("UpAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpAgent = FValue.trim();
			}
			else
				UpAgent = null;
		}
		if (FCode.equalsIgnoreCase("OthUpAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthUpAgent = FValue.trim();
			}
			else
				OthUpAgent = null;
		}
		if (FCode.equalsIgnoreCase("IntroBreakFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IntroBreakFlag = FValue.trim();
			}
			else
				IntroBreakFlag = null;
		}
		if (FCode.equalsIgnoreCase("IntroCommStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IntroCommStart = fDate.getDate( FValue );
			}
			else
				IntroCommStart = null;
		}
		if (FCode.equalsIgnoreCase("IntroCommEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IntroCommEnd = fDate.getDate( FValue );
			}
			else
				IntroCommEnd = null;
		}
		if (FCode.equalsIgnoreCase("EduManager"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EduManager = FValue.trim();
			}
			else
				EduManager = null;
		}
		if (FCode.equalsIgnoreCase("RearBreakFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RearBreakFlag = FValue.trim();
			}
			else
				RearBreakFlag = null;
		}
		if (FCode.equalsIgnoreCase("RearCommStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RearCommStart = fDate.getDate( FValue );
			}
			else
				RearCommStart = null;
		}
		if (FCode.equalsIgnoreCase("RearCommEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RearCommEnd = fDate.getDate( FValue );
			}
			else
				RearCommEnd = null;
		}
		if (FCode.equalsIgnoreCase("AscriptSeries"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptSeries = FValue.trim();
			}
			else
				AscriptSeries = null;
		}
		if (FCode.equalsIgnoreCase("OldStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OldStartDate = fDate.getDate( FValue );
			}
			else
				OldStartDate = null;
		}
		if (FCode.equalsIgnoreCase("OldEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OldEndDate = fDate.getDate( FValue );
			}
			else
				OldEndDate = null;
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
		if (FCode.equalsIgnoreCase("AstartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AstartDate = fDate.getDate( FValue );
			}
			else
				AstartDate = null;
		}
		if (FCode.equalsIgnoreCase("AssessType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssessType = FValue.trim();
			}
			else
				AssessType = null;
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
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator2 = FValue.trim();
			}
			else
				Operator2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate2 = fDate.getDate( FValue );
			}
			else
				MakeDate2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime2 = FValue.trim();
			}
			else
				MakeTime2 = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate2 = fDate.getDate( FValue );
			}
			else
				ModifyDate2 = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime2 = FValue.trim();
			}
			else
				ModifyTime2 = null;
		}
		if (FCode.equalsIgnoreCase("RemoveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RemoveType = FValue.trim();
			}
			else
				RemoveType = null;
		}
		if (FCode.equalsIgnoreCase("EdorNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNO = FValue.trim();
			}
			else
				EdorNO = null;
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
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchCode = FValue.trim();
			}
			else
				BranchCode = null;
		}
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexCalNo = FValue.trim();
			}
			else
				IndexCalNo = null;
		}
		if (FCode.equalsIgnoreCase("LastAgentGrade1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastAgentGrade1 = FValue.trim();
			}
			else
				LastAgentGrade1 = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade1 = FValue.trim();
			}
			else
				AgentGrade1 = null;
		}
		if (FCode.equalsIgnoreCase("EmployGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EmployGrade = FValue.trim();
			}
			else
				EmployGrade = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LATreeBSchema other = (LATreeBSchema)otherObject;
		return
			AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& ManageCom.equals(other.getManageCom())
			&& AgentSeries.equals(other.getAgentSeries())
			&& AgentGrade.equals(other.getAgentGrade())
			&& AgentLastSeries.equals(other.getAgentLastSeries())
			&& AgentLastGrade.equals(other.getAgentLastGrade())
			&& IntroAgency.equals(other.getIntroAgency())
			&& UpAgent.equals(other.getUpAgent())
			&& OthUpAgent.equals(other.getOthUpAgent())
			&& IntroBreakFlag.equals(other.getIntroBreakFlag())
			&& fDate.getString(IntroCommStart).equals(other.getIntroCommStart())
			&& fDate.getString(IntroCommEnd).equals(other.getIntroCommEnd())
			&& EduManager.equals(other.getEduManager())
			&& RearBreakFlag.equals(other.getRearBreakFlag())
			&& fDate.getString(RearCommStart).equals(other.getRearCommStart())
			&& fDate.getString(RearCommEnd).equals(other.getRearCommEnd())
			&& AscriptSeries.equals(other.getAscriptSeries())
			&& fDate.getString(OldStartDate).equals(other.getOldStartDate())
			&& fDate.getString(OldEndDate).equals(other.getOldEndDate())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(AstartDate).equals(other.getAstartDate())
			&& AssessType.equals(other.getAssessType())
			&& State.equals(other.getState())
			&& Operator2.equals(other.getOperator2())
			&& fDate.getString(MakeDate2).equals(other.getMakeDate2())
			&& MakeTime2.equals(other.getMakeTime2())
			&& fDate.getString(ModifyDate2).equals(other.getModifyDate2())
			&& ModifyTime2.equals(other.getModifyTime2())
			&& RemoveType.equals(other.getRemoveType())
			&& EdorNO.equals(other.getEdorNO())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchCode.equals(other.getBranchCode())
			&& IndexCalNo.equals(other.getIndexCalNo())
			&& LastAgentGrade1.equals(other.getLastAgentGrade1())
			&& AgentGrade1.equals(other.getAgentGrade1())
			&& EmployGrade.equals(other.getEmployGrade());
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
		if( strFieldName.equals("AgentCode") ) {
			return 0;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("AgentSeries") ) {
			return 3;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 4;
		}
		if( strFieldName.equals("AgentLastSeries") ) {
			return 5;
		}
		if( strFieldName.equals("AgentLastGrade") ) {
			return 6;
		}
		if( strFieldName.equals("IntroAgency") ) {
			return 7;
		}
		if( strFieldName.equals("UpAgent") ) {
			return 8;
		}
		if( strFieldName.equals("OthUpAgent") ) {
			return 9;
		}
		if( strFieldName.equals("IntroBreakFlag") ) {
			return 10;
		}
		if( strFieldName.equals("IntroCommStart") ) {
			return 11;
		}
		if( strFieldName.equals("IntroCommEnd") ) {
			return 12;
		}
		if( strFieldName.equals("EduManager") ) {
			return 13;
		}
		if( strFieldName.equals("RearBreakFlag") ) {
			return 14;
		}
		if( strFieldName.equals("RearCommStart") ) {
			return 15;
		}
		if( strFieldName.equals("RearCommEnd") ) {
			return 16;
		}
		if( strFieldName.equals("AscriptSeries") ) {
			return 17;
		}
		if( strFieldName.equals("OldStartDate") ) {
			return 18;
		}
		if( strFieldName.equals("OldEndDate") ) {
			return 19;
		}
		if( strFieldName.equals("StartDate") ) {
			return 20;
		}
		if( strFieldName.equals("AstartDate") ) {
			return 21;
		}
		if( strFieldName.equals("AssessType") ) {
			return 22;
		}
		if( strFieldName.equals("State") ) {
			return 23;
		}
		if( strFieldName.equals("Operator2") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate2") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime2") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate2") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime2") ) {
			return 28;
		}
		if( strFieldName.equals("RemoveType") ) {
			return 29;
		}
		if( strFieldName.equals("EdorNO") ) {
			return 30;
		}
		if( strFieldName.equals("Operator") ) {
			return 31;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 32;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 34;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 35;
		}
		if( strFieldName.equals("BranchCode") ) {
			return 36;
		}
		if( strFieldName.equals("IndexCalNo") ) {
			return 37;
		}
		if( strFieldName.equals("LastAgentGrade1") ) {
			return 38;
		}
		if( strFieldName.equals("AgentGrade1") ) {
			return 39;
		}
		if( strFieldName.equals("EmployGrade") ) {
			return 40;
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
				strFieldName = "AgentCode";
				break;
			case 1:
				strFieldName = "AgentGroup";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "AgentSeries";
				break;
			case 4:
				strFieldName = "AgentGrade";
				break;
			case 5:
				strFieldName = "AgentLastSeries";
				break;
			case 6:
				strFieldName = "AgentLastGrade";
				break;
			case 7:
				strFieldName = "IntroAgency";
				break;
			case 8:
				strFieldName = "UpAgent";
				break;
			case 9:
				strFieldName = "OthUpAgent";
				break;
			case 10:
				strFieldName = "IntroBreakFlag";
				break;
			case 11:
				strFieldName = "IntroCommStart";
				break;
			case 12:
				strFieldName = "IntroCommEnd";
				break;
			case 13:
				strFieldName = "EduManager";
				break;
			case 14:
				strFieldName = "RearBreakFlag";
				break;
			case 15:
				strFieldName = "RearCommStart";
				break;
			case 16:
				strFieldName = "RearCommEnd";
				break;
			case 17:
				strFieldName = "AscriptSeries";
				break;
			case 18:
				strFieldName = "OldStartDate";
				break;
			case 19:
				strFieldName = "OldEndDate";
				break;
			case 20:
				strFieldName = "StartDate";
				break;
			case 21:
				strFieldName = "AstartDate";
				break;
			case 22:
				strFieldName = "AssessType";
				break;
			case 23:
				strFieldName = "State";
				break;
			case 24:
				strFieldName = "Operator2";
				break;
			case 25:
				strFieldName = "MakeDate2";
				break;
			case 26:
				strFieldName = "MakeTime2";
				break;
			case 27:
				strFieldName = "ModifyDate2";
				break;
			case 28:
				strFieldName = "ModifyTime2";
				break;
			case 29:
				strFieldName = "RemoveType";
				break;
			case 30:
				strFieldName = "EdorNO";
				break;
			case 31:
				strFieldName = "Operator";
				break;
			case 32:
				strFieldName = "MakeDate";
				break;
			case 33:
				strFieldName = "MakeTime";
				break;
			case 34:
				strFieldName = "ModifyDate";
				break;
			case 35:
				strFieldName = "ModifyTime";
				break;
			case 36:
				strFieldName = "BranchCode";
				break;
			case 37:
				strFieldName = "IndexCalNo";
				break;
			case 38:
				strFieldName = "LastAgentGrade1";
				break;
			case 39:
				strFieldName = "AgentGrade1";
				break;
			case 40:
				strFieldName = "EmployGrade";
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
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentSeries") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentLastSeries") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentLastGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IntroAgency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthUpAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IntroBreakFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IntroCommStart") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IntroCommEnd") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EduManager") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RearBreakFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RearCommStart") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RearCommEnd") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AscriptSeries") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OldEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AstartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AssessType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RemoveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNO") ) {
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
		if( strFieldName.equals("BranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IndexCalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastAgentGrade1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EmployGrade") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
