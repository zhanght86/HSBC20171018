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
import com.sinosoft.lis.db.LCGrpAirContDB;

/*
 * <p>ClassName: LCGrpAirContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 短期险平台接口表
 */
public class LCGrpAirContSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpAirContSchema.class);

	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 单证印刷号 */
	private String PrtNo;
	/** 单证类型编码 */
	private String CertifyCode;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构编码 */
	private String AgentCom;
	/** 密码 */
	private String Password;
	/** 单位名称 */
	private String GrpName;
	/** 单位地址 */
	private String GrpAddress;
	/** 单位联系电话 */
	private String GrpPhone;
	/** 总份数 */
	private double SumMult;
	/** 总保费 */
	private double SumPrem;
	/** 总保额 */
	private double SumAmnt;
	/** 被保险人数 */
	private int Peoples;
	/** 保单状态 */
	private String PolState;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 保险年期年龄标志 */
	private String InsuYearFlag;
	/** 保险年期年龄 */
	private int InsuYear;
	/** 终止日期 */
	private Date EndDate;
	/** 生效日期 */
	private Date CValiDate;
	/** 合同成立日期 */
	private Date OrganizeDate;
	/** 合同成立时间 */
	private String OrganizeTime;
	/** 签单日期 */
	private Date SignDate;
	/** 险种编码 */
	private String RiskCode;
	/** 险种款式 */
	private String RiskType;
	/** 状态 */
	private String State;
	/** 结算日期 */
	private Date BalanceDate;
	/** 旅游行程 */
	private String Destination;
	/** 旅行团编号 */
	private String AgencyNo;
	/** 预留字段1 */
	private String StandByFlag1;
	/** 预留字段2 */
	private String StandByFlag2;
	/** 预留字段3 */
	private String StandByFlag3;
	/** 代理公司登录用户 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 37;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpAirContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpContNo";

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
		LCGrpAirContSchema cloned = (LCGrpAirContSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		GrpName = aGrpName;
	}
	/**
	* 0                   	身份证<p>
	* 1                   	护照<p>
	* 2                   	军官证<p>
	* 3                   	驾照<p>
	* 4                   	户口本<p>
	* 5                   	学生证<p>
	* 6                   	工作证<p>
	* 8                   	其它<p>
	* 9                   	无证件
	*/
	public String getGrpAddress()
	{
		return GrpAddress;
	}
	public void setGrpAddress(String aGrpAddress)
	{
		GrpAddress = aGrpAddress;
	}
	public String getGrpPhone()
	{
		return GrpPhone;
	}
	public void setGrpPhone(String aGrpPhone)
	{
		GrpPhone = aGrpPhone;
	}
	public double getSumMult()
	{
		return SumMult;
	}
	public void setSumMult(double aSumMult)
	{
		SumMult = aSumMult;
	}
	public void setSumMult(String aSumMult)
	{
		if (aSumMult != null && !aSumMult.equals(""))
		{
			Double tDouble = new Double(aSumMult);
			double d = tDouble.doubleValue();
			SumMult = d;
		}
	}

	public double getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	public double getSumAmnt()
	{
		return SumAmnt;
	}
	public void setSumAmnt(double aSumAmnt)
	{
		SumAmnt = aSumAmnt;
	}
	public void setSumAmnt(String aSumAmnt)
	{
		if (aSumAmnt != null && !aSumAmnt.equals(""))
		{
			Double tDouble = new Double(aSumAmnt);
			double d = tDouble.doubleValue();
			SumAmnt = d;
		}
	}

	public int getPeoples()
	{
		return Peoples;
	}
	public void setPeoples(int aPeoples)
	{
		Peoples = aPeoples;
	}
	public void setPeoples(String aPeoples)
	{
		if (aPeoples != null && !aPeoples.equals(""))
		{
			Integer tInteger = new Integer(aPeoples);
			int i = tInteger.intValue();
			Peoples = i;
		}
	}

	/**
	* 0-正在录入<p>
	* 1-有效<p>
	* 2-撤单
	*/
	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
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
	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	public int getInsuYear()
	{
		return InsuYear;
	}
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
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

	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	public String getOrganizeDate()
	{
		if( OrganizeDate != null )
			return fDate.getString(OrganizeDate);
		else
			return null;
	}
	public void setOrganizeDate(Date aOrganizeDate)
	{
		OrganizeDate = aOrganizeDate;
	}
	public void setOrganizeDate(String aOrganizeDate)
	{
		if (aOrganizeDate != null && !aOrganizeDate.equals("") )
		{
			OrganizeDate = fDate.getDate( aOrganizeDate );
		}
		else
			OrganizeDate = null;
	}

	public String getOrganizeTime()
	{
		return OrganizeTime;
	}
	public void setOrganizeTime(String aOrganizeTime)
	{
		OrganizeTime = aOrganizeTime;
	}
	public String getSignDate()
	{
		if( SignDate != null )
			return fDate.getString(SignDate);
		else
			return null;
	}
	public void setSignDate(Date aSignDate)
	{
		SignDate = aSignDate;
	}
	public void setSignDate(String aSignDate)
	{
		if (aSignDate != null && !aSignDate.equals("") )
		{
			SignDate = fDate.getDate( aSignDate );
		}
		else
			SignDate = null;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	/**
	* A- 保单信息保存完毕<p>
	* B-选中并需要结算<p>
	* C-结算处理完毕（生成了保单信息）
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getBalanceDate()
	{
		if( BalanceDate != null )
			return fDate.getString(BalanceDate);
		else
			return null;
	}
	public void setBalanceDate(Date aBalanceDate)
	{
		BalanceDate = aBalanceDate;
	}
	public void setBalanceDate(String aBalanceDate)
	{
		if (aBalanceDate != null && !aBalanceDate.equals("") )
		{
			BalanceDate = fDate.getDate( aBalanceDate );
		}
		else
			BalanceDate = null;
	}

	public String getDestination()
	{
		return Destination;
	}
	public void setDestination(String aDestination)
	{
		Destination = aDestination;
	}
	public String getAgencyNo()
	{
		return AgencyNo;
	}
	public void setAgencyNo(String aAgencyNo)
	{
		AgencyNo = aAgencyNo;
	}
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		StandByFlag2 = aStandByFlag2;
	}
	public String getStandByFlag3()
	{
		return StandByFlag3;
	}
	public void setStandByFlag3(String aStandByFlag3)
	{
		StandByFlag3 = aStandByFlag3;
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
	* 使用另外一个 LCGrpAirContSchema 对象给 Schema 赋值
	* @param: aLCGrpAirContSchema LCGrpAirContSchema
	**/
	public void setSchema(LCGrpAirContSchema aLCGrpAirContSchema)
	{
		this.GrpContNo = aLCGrpAirContSchema.getGrpContNo();
		this.PrtNo = aLCGrpAirContSchema.getPrtNo();
		this.CertifyCode = aLCGrpAirContSchema.getCertifyCode();
		this.ManageCom = aLCGrpAirContSchema.getManageCom();
		this.AgentCom = aLCGrpAirContSchema.getAgentCom();
		this.Password = aLCGrpAirContSchema.getPassword();
		this.GrpName = aLCGrpAirContSchema.getGrpName();
		this.GrpAddress = aLCGrpAirContSchema.getGrpAddress();
		this.GrpPhone = aLCGrpAirContSchema.getGrpPhone();
		this.SumMult = aLCGrpAirContSchema.getSumMult();
		this.SumPrem = aLCGrpAirContSchema.getSumPrem();
		this.SumAmnt = aLCGrpAirContSchema.getSumAmnt();
		this.Peoples = aLCGrpAirContSchema.getPeoples();
		this.PolState = aLCGrpAirContSchema.getPolState();
		this.AgentCode = aLCGrpAirContSchema.getAgentCode();
		this.AgentGroup = aLCGrpAirContSchema.getAgentGroup();
		this.InsuYearFlag = aLCGrpAirContSchema.getInsuYearFlag();
		this.InsuYear = aLCGrpAirContSchema.getInsuYear();
		this.EndDate = fDate.getDate( aLCGrpAirContSchema.getEndDate());
		this.CValiDate = fDate.getDate( aLCGrpAirContSchema.getCValiDate());
		this.OrganizeDate = fDate.getDate( aLCGrpAirContSchema.getOrganizeDate());
		this.OrganizeTime = aLCGrpAirContSchema.getOrganizeTime();
		this.SignDate = fDate.getDate( aLCGrpAirContSchema.getSignDate());
		this.RiskCode = aLCGrpAirContSchema.getRiskCode();
		this.RiskType = aLCGrpAirContSchema.getRiskType();
		this.State = aLCGrpAirContSchema.getState();
		this.BalanceDate = fDate.getDate( aLCGrpAirContSchema.getBalanceDate());
		this.Destination = aLCGrpAirContSchema.getDestination();
		this.AgencyNo = aLCGrpAirContSchema.getAgencyNo();
		this.StandByFlag1 = aLCGrpAirContSchema.getStandByFlag1();
		this.StandByFlag2 = aLCGrpAirContSchema.getStandByFlag2();
		this.StandByFlag3 = aLCGrpAirContSchema.getStandByFlag3();
		this.Operator = aLCGrpAirContSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpAirContSchema.getMakeDate());
		this.MakeTime = aLCGrpAirContSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpAirContSchema.getModifyDate());
		this.ModifyTime = aLCGrpAirContSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("GrpAddress") == null )
				this.GrpAddress = null;
			else
				this.GrpAddress = rs.getString("GrpAddress").trim();

			if( rs.getString("GrpPhone") == null )
				this.GrpPhone = null;
			else
				this.GrpPhone = rs.getString("GrpPhone").trim();

			this.SumMult = rs.getDouble("SumMult");
			this.SumPrem = rs.getDouble("SumPrem");
			this.SumAmnt = rs.getDouble("SumAmnt");
			this.Peoples = rs.getInt("Peoples");
			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			this.EndDate = rs.getDate("EndDate");
			this.CValiDate = rs.getDate("CValiDate");
			this.OrganizeDate = rs.getDate("OrganizeDate");
			if( rs.getString("OrganizeTime") == null )
				this.OrganizeTime = null;
			else
				this.OrganizeTime = rs.getString("OrganizeTime").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.BalanceDate = rs.getDate("BalanceDate");
			if( rs.getString("Destination") == null )
				this.Destination = null;
			else
				this.Destination = rs.getString("Destination").trim();

			if( rs.getString("AgencyNo") == null )
				this.AgencyNo = null;
			else
				this.AgencyNo = rs.getString("AgencyNo").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("StandByFlag3") == null )
				this.StandByFlag3 = null;
			else
				this.StandByFlag3 = rs.getString("StandByFlag3").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpAirCont表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAirContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpAirContSchema getSchema()
	{
		LCGrpAirContSchema aLCGrpAirContSchema = new LCGrpAirContSchema();
		aLCGrpAirContSchema.setSchema(this);
		return aLCGrpAirContSchema;
	}

	public LCGrpAirContDB getDB()
	{
		LCGrpAirContDB aDBOper = new LCGrpAirContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpAirCont描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumMult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OrganizeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganizeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalanceDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Destination)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgencyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpAirCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SumMult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			SumAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			OrganizeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			OrganizeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			BalanceDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			Destination = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AgencyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			StandByFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAirContSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddress));
		}
		if (FCode.equalsIgnoreCase("GrpPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPhone));
		}
		if (FCode.equalsIgnoreCase("SumMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMult));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("SumAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumAmnt));
		}
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("OrganizeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOrganizeDate()));
		}
		if (FCode.equalsIgnoreCase("OrganizeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganizeTime));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("BalanceDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalanceDate()));
		}
		if (FCode.equalsIgnoreCase("Destination"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Destination));
		}
		if (FCode.equalsIgnoreCase("AgencyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgencyNo));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag3));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpAddress);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpPhone);
				break;
			case 9:
				strFieldValue = String.valueOf(SumMult);
				break;
			case 10:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 11:
				strFieldValue = String.valueOf(SumAmnt);
				break;
			case 12:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 17:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOrganizeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(OrganizeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalanceDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Destination);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AgencyNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag3);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 36:
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
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
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpAddress = FValue.trim();
			}
			else
				GrpAddress = null;
		}
		if (FCode.equalsIgnoreCase("GrpPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPhone = FValue.trim();
			}
			else
				GrpPhone = null;
		}
		if (FCode.equalsIgnoreCase("SumMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMult = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
		}
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
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
			}
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("OrganizeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OrganizeDate = fDate.getDate( FValue );
			}
			else
				OrganizeDate = null;
		}
		if (FCode.equalsIgnoreCase("OrganizeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrganizeTime = FValue.trim();
			}
			else
				OrganizeTime = null;
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
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
		if (FCode.equalsIgnoreCase("BalanceDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalanceDate = fDate.getDate( FValue );
			}
			else
				BalanceDate = null;
		}
		if (FCode.equalsIgnoreCase("Destination"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Destination = FValue.trim();
			}
			else
				Destination = null;
		}
		if (FCode.equalsIgnoreCase("AgencyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgencyNo = FValue.trim();
			}
			else
				AgencyNo = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag1 = FValue.trim();
			}
			else
				StandByFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag2 = FValue.trim();
			}
			else
				StandByFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag3 = FValue.trim();
			}
			else
				StandByFlag3 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpAirContSchema other = (LCGrpAirContSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& CertifyCode.equals(other.getCertifyCode())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& Password.equals(other.getPassword())
			&& GrpName.equals(other.getGrpName())
			&& GrpAddress.equals(other.getGrpAddress())
			&& GrpPhone.equals(other.getGrpPhone())
			&& SumMult == other.getSumMult()
			&& SumPrem == other.getSumPrem()
			&& SumAmnt == other.getSumAmnt()
			&& Peoples == other.getPeoples()
			&& PolState.equals(other.getPolState())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(OrganizeDate).equals(other.getOrganizeDate())
			&& OrganizeTime.equals(other.getOrganizeTime())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskType.equals(other.getRiskType())
			&& State.equals(other.getState())
			&& fDate.getString(BalanceDate).equals(other.getBalanceDate())
			&& Destination.equals(other.getDestination())
			&& AgencyNo.equals(other.getAgencyNo())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& StandByFlag3.equals(other.getStandByFlag3())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 1;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 4;
		}
		if( strFieldName.equals("Password") ) {
			return 5;
		}
		if( strFieldName.equals("GrpName") ) {
			return 6;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return 7;
		}
		if( strFieldName.equals("GrpPhone") ) {
			return 8;
		}
		if( strFieldName.equals("SumMult") ) {
			return 9;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 10;
		}
		if( strFieldName.equals("SumAmnt") ) {
			return 11;
		}
		if( strFieldName.equals("Peoples") ) {
			return 12;
		}
		if( strFieldName.equals("PolState") ) {
			return 13;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 14;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 15;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 16;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 17;
		}
		if( strFieldName.equals("EndDate") ) {
			return 18;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 19;
		}
		if( strFieldName.equals("OrganizeDate") ) {
			return 20;
		}
		if( strFieldName.equals("OrganizeTime") ) {
			return 21;
		}
		if( strFieldName.equals("SignDate") ) {
			return 22;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 23;
		}
		if( strFieldName.equals("RiskType") ) {
			return 24;
		}
		if( strFieldName.equals("State") ) {
			return 25;
		}
		if( strFieldName.equals("BalanceDate") ) {
			return 26;
		}
		if( strFieldName.equals("Destination") ) {
			return 27;
		}
		if( strFieldName.equals("AgencyNo") ) {
			return 28;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 29;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 30;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return 31;
		}
		if( strFieldName.equals("Operator") ) {
			return 32;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 33;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 34;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 35;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 36;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "PrtNo";
				break;
			case 2:
				strFieldName = "CertifyCode";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "AgentCom";
				break;
			case 5:
				strFieldName = "Password";
				break;
			case 6:
				strFieldName = "GrpName";
				break;
			case 7:
				strFieldName = "GrpAddress";
				break;
			case 8:
				strFieldName = "GrpPhone";
				break;
			case 9:
				strFieldName = "SumMult";
				break;
			case 10:
				strFieldName = "SumPrem";
				break;
			case 11:
				strFieldName = "SumAmnt";
				break;
			case 12:
				strFieldName = "Peoples";
				break;
			case 13:
				strFieldName = "PolState";
				break;
			case 14:
				strFieldName = "AgentCode";
				break;
			case 15:
				strFieldName = "AgentGroup";
				break;
			case 16:
				strFieldName = "InsuYearFlag";
				break;
			case 17:
				strFieldName = "InsuYear";
				break;
			case 18:
				strFieldName = "EndDate";
				break;
			case 19:
				strFieldName = "CValiDate";
				break;
			case 20:
				strFieldName = "OrganizeDate";
				break;
			case 21:
				strFieldName = "OrganizeTime";
				break;
			case 22:
				strFieldName = "SignDate";
				break;
			case 23:
				strFieldName = "RiskCode";
				break;
			case 24:
				strFieldName = "RiskType";
				break;
			case 25:
				strFieldName = "State";
				break;
			case 26:
				strFieldName = "BalanceDate";
				break;
			case 27:
				strFieldName = "Destination";
				break;
			case 28:
				strFieldName = "AgencyNo";
				break;
			case 29:
				strFieldName = "StandByFlag1";
				break;
			case 30:
				strFieldName = "StandByFlag2";
				break;
			case 31:
				strFieldName = "StandByFlag3";
				break;
			case 32:
				strFieldName = "Operator";
				break;
			case 33:
				strFieldName = "MakeDate";
				break;
			case 34:
				strFieldName = "MakeTime";
				break;
			case 35:
				strFieldName = "ModifyDate";
				break;
			case 36:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumMult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Peoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OrganizeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OrganizeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Destination") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgencyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag3") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
