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
import com.sinosoft.lis.db.LCPolBakDB;

/*
 * <p>ClassName: LCPolBakSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCPolBakSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPolBakSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 投保单险种号码 */
	private String ProposalNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 总单类型 */
	private String ContType;
	/** 保单类型标记 */
	private String PolTypeFlag;
	/** 主险保单号码 */
	private String MainPolNo;
	/** 主被保人保单号码 */
	private String MasterPolNo;
	/** 险类编码 */
	private String KindCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 销售渠道 */
	private String SaleChnl;
	/** 经办人 */
	private String Handler;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人生日 */
	private Date InsuredBirthday;
	/** 被保人投保年龄 */
	private int InsuredAppAge;
	/** 被保人数目 */
	private int InsuredPeoples;
	/** 被保人职业类别/工种编码 */
	private String OccupationType;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 险种生效日期 */
	private Date CValiDate;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private Date SignDate;
	/** 签单时间 */
	private String SignTime;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 交至日期 */
	private Date PaytoDate;
	/** 起领日期 */
	private Date GetStartDate;
	/** 保险责任终止日期 */
	private Date EndDate;
	/** 意外责任终止日期 */
	private Date AcciEndDate;
	/** 领取年龄年期标志 */
	private String GetYearFlag;
	/** 领取年龄年期 */
	private int GetYear;
	/** 终交年龄年期标志 */
	private String PayEndYearFlag;
	/** 终交年龄年期 */
	private int PayEndYear;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 保险年龄年期 */
	private int InsuYear;
	/** 意外年龄年期标志 */
	private String AcciYearFlag;
	/** 意外年龄年期 */
	private int AcciYear;
	/** 起领日期计算类型 */
	private String GetStartType;
	/** 是否指定生效日期 */
	private String SpecifyValiDate;
	/** 交费方式 */
	private String PayMode;
	/** 交费位置 */
	private String PayLocation;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费年期 */
	private int PayYears;
	/** 保险年期 */
	private int Years;
	/** 管理费比例 */
	private double ManageFeeRate;
	/** 浮动费率 */
	private double FloatRate;
	/** 保费算保额标志 */
	private String PremToAmnt;
	/** 总份数 */
	private double Mult;
	/** 总标准保费 */
	private double StandPrem;
	/** 总保费 */
	private double Prem;
	/** 总累计保费 */
	private double SumPrem;
	/** 总基本保额 */
	private double Amnt;
	/** 总风险保额 */
	private double RiskAmnt;
	/** 余额 */
	private double LeavingMoney;
	/** 批改次数 */
	private int EndorseTimes;
	/** 理赔次数 */
	private int ClaimTimes;
	/** 生存领取次数 */
	private int LiveTimes;
	/** 续保次数 */
	private int RenewCount;
	/** 最后一次给付日期 */
	private Date LastGetDate;
	/** 最后一次借款日期 */
	private Date LastLoanDate;
	/** 最后一次催收日期 */
	private Date LastRegetDate;
	/** 最后一次保全日期 */
	private Date LastEdorDate;
	/** 最近复效日期 */
	private Date LastRevDate;
	/** 续保标志 */
	private int RnewFlag;
	/** 停交标志 */
	private String StopFlag;
	/** 满期标志 */
	private String ExpiryFlag;
	/** 自动垫交标志 */
	private String AutoPayFlag;
	/** 利差返还方式 */
	private String InterestDifFlag;
	/** 减额交清标志 */
	private String SubFlag;
	/** 受益人标记 */
	private String BnfFlag;
	/** 是否体检件标志 */
	private String HealthCheckFlag;
	/** 告知标志 */
	private String ImpartFlag;
	/** 商业分保标记 */
	private String ReinsureFlag;
	/** 代收标志 */
	private String AgentPayFlag;
	/** 代付标志 */
	private String AgentGetFlag;
	/** 生存金领取方式 */
	private String LiveGetMode;
	/** 身故金领取方式 */
	private String DeadGetMode;
	/** 红利金领取方式 */
	private String BonusGetMode;
	/** 红利金领取人 */
	private String BonusMan;
	/** 被保人、投保人死亡标志 */
	private String DeadFlag;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 备注 */
	private String Remark;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 核保状态 */
	private String UWFlag;
	/** 最终核保人编码 */
	private String UWCode;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 其它保单状态 */
	private String PolState;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 等待期 */
	private int WaitPeriod;
	/** 领取形式 */
	private String GetForm;
	/** 领取银行编码 */
	private String GetBankCode;
	/** 领取银行账户 */
	private String GetBankAccNo;
	/** 领取银行户名 */
	private String GetAccName;
	/** 不丧失价值选择 */
	private String KeepValueOpt;
	/** 缴费规则编码 */
	private String PayRuleCode;
	/** 归属规则编码 */
	private String AscriptionRuleCode;
	/** 归属标记 */
	private String AscriptionFlag;
	/** 险种最初生效日 */
	private Date PCValiDate;
	/** 自动应用团体帐户标记 */
	private String AutoPubAccFlag;
	/** 主险险种顺序号 */
	private String RiskSequence;
	/** 取消提前给付特约标记 */
	private String CancleForegetSpecFlag;
	/** 乘坐日期 */
	private Date TakeDate;
	/** 乘坐时间 */
	private String TakeTime;
	/** 班次 */
	private String AirNo;
	/** 车票号 */
	private String TicketNo;
	/** 座位号 */
	private String SeatNo;
	/** 填单保费 */
	private double InputPrem;

	public static final int FIELDNUM = 131;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPolBakSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PolNo";

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
		LCPolBakSchema cloned = (LCPolBakSchema)super.clone();
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
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	/**
	* 冗余，标准在个人保单表<p>
	* 第3,4位的意义如下：<p>
	* 11：个人印刷号码<p>
	* 12：集体印刷号码<p>
	* 15：银行险印刷号码
	*/
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	/**
	* 2-集体总单,1-个人总投保单
	*/
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		ContType = aContType;
	}
	/**
	* 0 --个人单：<p>
	* 1 --无名单：<p>
	* 2 --（团单）公共帐户
	*/
	public String getPolTypeFlag()
	{
		return PolTypeFlag;
	}
	public void setPolTypeFlag(String aPolTypeFlag)
	{
		PolTypeFlag = aPolTypeFlag;
	}
	public String getMainPolNo()
	{
		return MainPolNo;
	}
	public void setMainPolNo(String aMainPolNo)
	{
		MainPolNo = aMainPolNo;
	}
	/**
	* 在无名单补名单的时候，存补的无名单的号码。
	*/
	public String getMasterPolNo()
	{
		return MasterPolNo;
	}
	public void setMasterPolNo(String aMasterPolNo)
	{
		MasterPolNo = aMasterPolNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/**
	* 对不同的代理机构号进行内部的分类。<p>
	* 柜员
	*/
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
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
	public String getAgentCode1()
	{
		return AgentCode1;
	}
	public void setAgentCode1(String aAgentCode1)
	{
		AgentCode1 = aAgentCode1;
	}
	/**
	* 1-个人营销,2-团险直销,3-银行代理
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getInsuredSex()
	{
		return InsuredSex;
	}
	public void setInsuredSex(String aInsuredSex)
	{
		InsuredSex = aInsuredSex;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
	}

	public int getInsuredAppAge()
	{
		return InsuredAppAge;
	}
	public void setInsuredAppAge(int aInsuredAppAge)
	{
		InsuredAppAge = aInsuredAppAge;
	}
	public void setInsuredAppAge(String aInsuredAppAge)
	{
		if (aInsuredAppAge != null && !aInsuredAppAge.equals(""))
		{
			Integer tInteger = new Integer(aInsuredAppAge);
			int i = tInteger.intValue();
			InsuredAppAge = i;
		}
	}

	public int getInsuredPeoples()
	{
		return InsuredPeoples;
	}
	public void setInsuredPeoples(int aInsuredPeoples)
	{
		InsuredPeoples = aInsuredPeoples;
	}
	public void setInsuredPeoples(String aInsuredPeoples)
	{
		if (aInsuredPeoples != null && !aInsuredPeoples.equals(""))
		{
			Integer tInteger = new Integer(aInsuredPeoples);
			int i = tInteger.intValue();
			InsuredPeoples = i;
		}
	}

	/**
	* 冗余，标准在个人客户表
	*/
	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
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

	public String getSignCom()
	{
		return SignCom;
	}
	public void setSignCom(String aSignCom)
	{
		SignCom = aSignCom;
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

	public String getSignTime()
	{
		return SignTime;
	}
	public void setSignTime(String aSignTime)
	{
		SignTime = aSignTime;
	}
	/**
	* 为责任表中最早的首交日期
	*/
	public String getFirstPayDate()
	{
		if( FirstPayDate != null )
			return fDate.getString(FirstPayDate);
		else
			return null;
	}
	public void setFirstPayDate(Date aFirstPayDate)
	{
		FirstPayDate = aFirstPayDate;
	}
	public void setFirstPayDate(String aFirstPayDate)
	{
		if (aFirstPayDate != null && !aFirstPayDate.equals("") )
		{
			FirstPayDate = fDate.getDate( aFirstPayDate );
		}
		else
			FirstPayDate = null;
	}

	/**
	* 为责任表中最晚的终交日期
	*/
	public String getPayEndDate()
	{
		if( PayEndDate != null )
			return fDate.getString(PayEndDate);
		else
			return null;
	}
	public void setPayEndDate(Date aPayEndDate)
	{
		PayEndDate = aPayEndDate;
	}
	public void setPayEndDate(String aPayEndDate)
	{
		if (aPayEndDate != null && !aPayEndDate.equals("") )
		{
			PayEndDate = fDate.getDate( aPayEndDate );
		}
		else
			PayEndDate = null;
	}

	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	/**
	* 为责任表中最早的起领日期
	*/
	public String getGetStartDate()
	{
		if( GetStartDate != null )
			return fDate.getString(GetStartDate);
		else
			return null;
	}
	public void setGetStartDate(Date aGetStartDate)
	{
		GetStartDate = aGetStartDate;
	}
	public void setGetStartDate(String aGetStartDate)
	{
		if (aGetStartDate != null && !aGetStartDate.equals("") )
		{
			GetStartDate = fDate.getDate( aGetStartDate );
		}
		else
			GetStartDate = null;
	}

	/**
	* 为责任表中最晚的终止日期
	*/
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

	/**
	* 为责任表中最晚的意外责任终止日期
	*/
	public String getAcciEndDate()
	{
		if( AcciEndDate != null )
			return fDate.getString(AcciEndDate);
		else
			return null;
	}
	public void setAcciEndDate(Date aAcciEndDate)
	{
		AcciEndDate = aAcciEndDate;
	}
	public void setAcciEndDate(String aAcciEndDate)
	{
		if (aAcciEndDate != null && !aAcciEndDate.equals("") )
		{
			AcciEndDate = fDate.getDate( aAcciEndDate );
		}
		else
			AcciEndDate = null;
	}

	public String getGetYearFlag()
	{
		return GetYearFlag;
	}
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
	}
	public int getGetYear()
	{
		return GetYear;
	}
	public void setGetYear(int aGetYear)
	{
		GetYear = aGetYear;
	}
	public void setGetYear(String aGetYear)
	{
		if (aGetYear != null && !aGetYear.equals(""))
		{
			Integer tInteger = new Integer(aGetYear);
			int i = tInteger.intValue();
			GetYear = i;
		}
	}

	/**
	* A－年龄，M－月，D－日，Y－年
	*/
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/**
	* Y －－ 年<p>
	* M －－ 月<p>
	* D －－ 日<p>
	* A －－ 岁
	*/
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

	public String getAcciYearFlag()
	{
		return AcciYearFlag;
	}
	public void setAcciYearFlag(String aAcciYearFlag)
	{
		AcciYearFlag = aAcciYearFlag;
	}
	public int getAcciYear()
	{
		return AcciYear;
	}
	public void setAcciYear(int aAcciYear)
	{
		AcciYear = aAcciYear;
	}
	public void setAcciYear(String aAcciYear)
	{
		if (aAcciYear != null && !aAcciYear.equals(""))
		{
			Integer tInteger = new Integer(aAcciYear);
			int i = tInteger.intValue();
			AcciYear = i;
		}
	}

	/**
	* 生日对应日或者保单生效对应日<p>
	* 0 --生日对应<p>
	* 1 --起保对应
	*/
	public String getGetStartType()
	{
		return GetStartType;
	}
	public void setGetStartType(String aGetStartType)
	{
		GetStartType = aGetStartType;
	}
	/**
	* 该字段主要用来接受界面的用户选择输入。<p>
	* 0 －－ 不指定生效日期<p>
	* 1 －－ 用户界面中指定生效日期
	*/
	public String getSpecifyValiDate()
	{
		return SpecifyValiDate;
	}
	public void setSpecifyValiDate(String aSpecifyValiDate)
	{
		SpecifyValiDate = aSpecifyValiDate;
	}
	/**
	* 0--老系统数据补录<p>
	* 1--现金<p>
	* 2--现金送款簿<p>
	* 3--支票<p>
	* 4--银行转帐（非制返盘）<p>
	* 5--内部转帐<p>
	* 6--POS收款<p>
	* 7--银行代扣（制返盘）<p>
	* 8--邮政业务<p>
	* 9--银行收款"
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	/**
	* 该字段表示续期交费方式<p>
	* 0 --银行自动转帐<p>
	* 1 --柜台交费<p>
	* 2 --人工收取<p>
	* <p>
	* <p>
	* paylocation1	8	银行转帐<p>
	* paylocation1	9	现金缴纳
	*/
	public String getPayLocation()
	{
		return PayLocation;
	}
	public void setPayLocation(String aPayLocation)
	{
		PayLocation = aPayLocation;
	}
	/**
	* 交费间隔<p>
	* -1 -- 不定期交,<p>
	* 0  -- 趸交,<p>
	* 1  -- 月交<p>
	* 3  -- 季交<p>
	* 6  -- 半年交<p>
	* 12 -- 年交
	*/
	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* 对于终交年期标志为“年”：  表示需要交费的年数。<p>
	* 对于终交年期标志为“月”：  表示需要交费的月数<p>
	* 对于终交年期标志为“日”：  表示需要交费的天数<p>
	* 对于终交年期标志为“年龄”：该字段存放将根据年龄折算成的需要交费的年数。
	*/
	public int getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(int aPayYears)
	{
		PayYears = aPayYears;
	}
	public void setPayYears(String aPayYears)
	{
		if (aPayYears != null && !aPayYears.equals(""))
		{
			Integer tInteger = new Integer(aPayYears);
			int i = tInteger.intValue();
			PayYears = i;
		}
	}

	/**
	* 保险责任区间
	*/
	public int getYears()
	{
		return Years;
	}
	public void setYears(int aYears)
	{
		Years = aYears;
	}
	public void setYears(String aYears)
	{
		if (aYears != null && !aYears.equals(""))
		{
			Integer tInteger = new Integer(aYears);
			int i = tInteger.intValue();
			Years = i;
		}
	}

	public double getManageFeeRate()
	{
		return ManageFeeRate;
	}
	public void setManageFeeRate(double aManageFeeRate)
	{
		ManageFeeRate = aManageFeeRate;
	}
	public void setManageFeeRate(String aManageFeeRate)
	{
		if (aManageFeeRate != null && !aManageFeeRate.equals(""))
		{
			Double tDouble = new Double(aManageFeeRate);
			double d = tDouble.doubleValue();
			ManageFeeRate = d;
		}
	}

	public double getFloatRate()
	{
		return FloatRate;
	}
	public void setFloatRate(double aFloatRate)
	{
		FloatRate = aFloatRate;
	}
	public void setFloatRate(String aFloatRate)
	{
		if (aFloatRate != null && !aFloatRate.equals(""))
		{
			Double tDouble = new Double(aFloatRate);
			double d = tDouble.doubleValue();
			FloatRate = d;
		}
	}

	public String getPremToAmnt()
	{
		return PremToAmnt;
	}
	public void setPremToAmnt(String aPremToAmnt)
	{
		PremToAmnt = aPremToAmnt;
	}
	public double getMult()
	{
		return Mult;
	}
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}

	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
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

	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	public double getRiskAmnt()
	{
		return RiskAmnt;
	}
	public void setRiskAmnt(double aRiskAmnt)
	{
		RiskAmnt = aRiskAmnt;
	}
	public void setRiskAmnt(String aRiskAmnt)
	{
		if (aRiskAmnt != null && !aRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aRiskAmnt);
			double d = tDouble.doubleValue();
			RiskAmnt = d;
		}
	}

	public double getLeavingMoney()
	{
		return LeavingMoney;
	}
	public void setLeavingMoney(double aLeavingMoney)
	{
		LeavingMoney = aLeavingMoney;
	}
	public void setLeavingMoney(String aLeavingMoney)
	{
		if (aLeavingMoney != null && !aLeavingMoney.equals(""))
		{
			Double tDouble = new Double(aLeavingMoney);
			double d = tDouble.doubleValue();
			LeavingMoney = d;
		}
	}

	public int getEndorseTimes()
	{
		return EndorseTimes;
	}
	public void setEndorseTimes(int aEndorseTimes)
	{
		EndorseTimes = aEndorseTimes;
	}
	public void setEndorseTimes(String aEndorseTimes)
	{
		if (aEndorseTimes != null && !aEndorseTimes.equals(""))
		{
			Integer tInteger = new Integer(aEndorseTimes);
			int i = tInteger.intValue();
			EndorseTimes = i;
		}
	}

	/**
	* 该字段记录立案的次数。只要立案了就认为进行理赔了，在该字段上加1。
	*/
	public int getClaimTimes()
	{
		return ClaimTimes;
	}
	public void setClaimTimes(int aClaimTimes)
	{
		ClaimTimes = aClaimTimes;
	}
	public void setClaimTimes(String aClaimTimes)
	{
		if (aClaimTimes != null && !aClaimTimes.equals(""))
		{
			Integer tInteger = new Integer(aClaimTimes);
			int i = tInteger.intValue();
			ClaimTimes = i;
		}
	}

	public int getLiveTimes()
	{
		return LiveTimes;
	}
	public void setLiveTimes(int aLiveTimes)
	{
		LiveTimes = aLiveTimes;
	}
	public void setLiveTimes(String aLiveTimes)
	{
		if (aLiveTimes != null && !aLiveTimes.equals(""))
		{
			Integer tInteger = new Integer(aLiveTimes);
			int i = tInteger.intValue();
			LiveTimes = i;
		}
	}

	/**
	* n --续保n次,n>0
	*/
	public int getRenewCount()
	{
		return RenewCount;
	}
	public void setRenewCount(int aRenewCount)
	{
		RenewCount = aRenewCount;
	}
	public void setRenewCount(String aRenewCount)
	{
		if (aRenewCount != null && !aRenewCount.equals(""))
		{
			Integer tInteger = new Integer(aRenewCount);
			int i = tInteger.intValue();
			RenewCount = i;
		}
	}

	public String getLastGetDate()
	{
		if( LastGetDate != null )
			return fDate.getString(LastGetDate);
		else
			return null;
	}
	public void setLastGetDate(Date aLastGetDate)
	{
		LastGetDate = aLastGetDate;
	}
	public void setLastGetDate(String aLastGetDate)
	{
		if (aLastGetDate != null && !aLastGetDate.equals("") )
		{
			LastGetDate = fDate.getDate( aLastGetDate );
		}
		else
			LastGetDate = null;
	}

	public String getLastLoanDate()
	{
		if( LastLoanDate != null )
			return fDate.getString(LastLoanDate);
		else
			return null;
	}
	public void setLastLoanDate(Date aLastLoanDate)
	{
		LastLoanDate = aLastLoanDate;
	}
	public void setLastLoanDate(String aLastLoanDate)
	{
		if (aLastLoanDate != null && !aLastLoanDate.equals("") )
		{
			LastLoanDate = fDate.getDate( aLastLoanDate );
		}
		else
			LastLoanDate = null;
	}

	public String getLastRegetDate()
	{
		if( LastRegetDate != null )
			return fDate.getString(LastRegetDate);
		else
			return null;
	}
	public void setLastRegetDate(Date aLastRegetDate)
	{
		LastRegetDate = aLastRegetDate;
	}
	public void setLastRegetDate(String aLastRegetDate)
	{
		if (aLastRegetDate != null && !aLastRegetDate.equals("") )
		{
			LastRegetDate = fDate.getDate( aLastRegetDate );
		}
		else
			LastRegetDate = null;
	}

	public String getLastEdorDate()
	{
		if( LastEdorDate != null )
			return fDate.getString(LastEdorDate);
		else
			return null;
	}
	public void setLastEdorDate(Date aLastEdorDate)
	{
		LastEdorDate = aLastEdorDate;
	}
	public void setLastEdorDate(String aLastEdorDate)
	{
		if (aLastEdorDate != null && !aLastEdorDate.equals("") )
		{
			LastEdorDate = fDate.getDate( aLastEdorDate );
		}
		else
			LastEdorDate = null;
	}

	public String getLastRevDate()
	{
		if( LastRevDate != null )
			return fDate.getString(LastRevDate);
		else
			return null;
	}
	public void setLastRevDate(Date aLastRevDate)
	{
		LastRevDate = aLastRevDate;
	}
	public void setLastRevDate(String aLastRevDate)
	{
		if (aLastRevDate != null && !aLastRevDate.equals("") )
		{
			LastRevDate = fDate.getDate( aLastRevDate );
		}
		else
			LastRevDate = null;
	}

	/**
	* -2 -- 非续保<p>
	* -1 -- 自动续保<p>
	* 0 -- 人工续保
	*/
	public int getRnewFlag()
	{
		return RnewFlag;
	}
	public void setRnewFlag(int aRnewFlag)
	{
		RnewFlag = aRnewFlag;
	}
	public void setRnewFlag(String aRnewFlag)
	{
		if (aRnewFlag != null && !aRnewFlag.equals(""))
		{
			Integer tInteger = new Integer(aRnewFlag);
			int i = tInteger.intValue();
			RnewFlag = i;
		}
	}

	/**
	* 0 --正常<p>
	* 1 --停交
	*/
	public String getStopFlag()
	{
		return StopFlag;
	}
	public void setStopFlag(String aStopFlag)
	{
		StopFlag = aStopFlag;
	}
	/**
	* 0 --正常<p>
	* 1 --满期,<p>
	* 2 --死亡续领
	*/
	public String getExpiryFlag()
	{
		return ExpiryFlag;
	}
	public void setExpiryFlag(String aExpiryFlag)
	{
		ExpiryFlag = aExpiryFlag;
	}
	/**
	* 0 --正常<p>
	* 1 --垫交
	*/
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		AutoPayFlag = aAutoPayFlag;
	}
	public String getInterestDifFlag()
	{
		return InterestDifFlag;
	}
	public void setInterestDifFlag(String aInterestDifFlag)
	{
		InterestDifFlag = aInterestDifFlag;
	}
	/**
	* 0 --正常<p>
	* 1 --减额交清
	*/
	public String getSubFlag()
	{
		return SubFlag;
	}
	public void setSubFlag(String aSubFlag)
	{
		SubFlag = aSubFlag;
	}
	/**
	* 0 -- 生存受益人<p>
	* 1 -- 死亡受益人
	*/
	public String getBnfFlag()
	{
		return BnfFlag;
	}
	public void setBnfFlag(String aBnfFlag)
	{
		BnfFlag = aBnfFlag;
	}
	/**
	* 0 --不是<p>
	* 1 --是
	*/
	public String getHealthCheckFlag()
	{
		return HealthCheckFlag;
	}
	public void setHealthCheckFlag(String aHealthCheckFlag)
	{
		HealthCheckFlag = aHealthCheckFlag;
	}
	/**
	* 0 --没有健康告知<p>
	* 1 --有健康告知
	*/
	public String getImpartFlag()
	{
		return ImpartFlag;
	}
	public void setImpartFlag(String aImpartFlag)
	{
		ImpartFlag = aImpartFlag;
	}
	/**
	* 0 --不需要商业分保<p>
	* 1 --需要商业分保
	*/
	public String getReinsureFlag()
	{
		return ReinsureFlag;
	}
	public void setReinsureFlag(String aReinsureFlag)
	{
		ReinsureFlag = aReinsureFlag;
	}
	/**
	* 0 ---表示不通过银行代扣<p>
	* 1 ---表示通过银行代扣
	*/
	public String getAgentPayFlag()
	{
		return AgentPayFlag;
	}
	public void setAgentPayFlag(String aAgentPayFlag)
	{
		AgentPayFlag = aAgentPayFlag;
	}
	/**
	* 0 ---表示补通过银行代付<p>
	* 1 ---表示通过银行代付
	*/
	public String getAgentGetFlag()
	{
		return AgentGetFlag;
	}
	public void setAgentGetFlag(String aAgentGetFlag)
	{
		AgentGetFlag = aAgentGetFlag;
	}
	/**
	* 1--累积生息<p>
	* 2--领取现金<p>
	* 3--抵缴保费<p>
	* 4--其他<p>
	* 5--增额交清
	*/
	public String getLiveGetMode()
	{
		return LiveGetMode;
	}
	public void setLiveGetMode(String aLiveGetMode)
	{
		LiveGetMode = aLiveGetMode;
	}
	/**
	* 1--累积生息<p>
	* 2--领取现金<p>
	* 3--抵缴保费<p>
	* 4--其他<p>
	* 5--增额交清
	*/
	public String getDeadGetMode()
	{
		return DeadGetMode;
	}
	public void setDeadGetMode(String aDeadGetMode)
	{
		DeadGetMode = aDeadGetMode;
	}
	/**
	* 1--累积生息<p>
	* 2--领取现金<p>
	* 3--抵缴保费<p>
	* 4--其他<p>
	* 5--增额交清
	*/
	public String getBonusGetMode()
	{
		return BonusGetMode;
	}
	public void setBonusGetMode(String aBonusGetMode)
	{
		BonusGetMode = aBonusGetMode;
	}
	/**
	* 0 －－ 同投保人<p>
	* 1 －－ 同被保人
	*/
	public String getBonusMan()
	{
		return BonusMan;
	}
	public void setBonusMan(String aBonusMan)
	{
		BonusMan = aBonusMan;
	}
	/**
	* 1 被保人死亡标志<p>
	* 2 投保人死亡标志
	*/
	public String getDeadFlag()
	{
		return DeadFlag;
	}
	public void setDeadFlag(String aDeadFlag)
	{
		DeadFlag = aDeadFlag;
	}
	public String getSmokeFlag()
	{
		return SmokeFlag;
	}
	public void setSmokeFlag(String aSmokeFlag)
	{
		SmokeFlag = aSmokeFlag;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* 0 －－ 未复核<p>
	* 1 －－ 复核没有通过<p>
	* 9 －－ 复核通过
	*/
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		ApproveFlag = aApproveFlag;
	}
	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		ApproveCode = aApproveCode;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		ApproveTime = aApproveTime;
	}
	/**
	* 1 拒保<p>
	* 2 延期<p>
	* 3 条件承保<p>
	* 4 通融<p>
	* 5 自动<p>
	* 6 待上级<p>
	* 7 问题件<p>
	* 8 核保通知书<p>
	* 9 正常<p>
	* a 撤单<p>
	* b 保险计划变更<p>
	* z 核保订正
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	public String getUWCode()
	{
		return UWCode;
	}
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
	}
	public String getUWDate()
	{
		if( UWDate != null )
			return fDate.getString(UWDate);
		else
			return null;
	}
	public void setUWDate(Date aUWDate)
	{
		UWDate = aUWDate;
	}
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
		UWTime = aUWTime;
	}
	public String getPolApplyDate()
	{
		if( PolApplyDate != null )
			return fDate.getString(PolApplyDate);
		else
			return null;
	}
	public void setPolApplyDate(Date aPolApplyDate)
	{
		PolApplyDate = aPolApplyDate;
	}
	public void setPolApplyDate(String aPolApplyDate)
	{
		if (aPolApplyDate != null && !aPolApplyDate.equals("") )
		{
			PolApplyDate = fDate.getDate( aPolApplyDate );
		}
		else
			PolApplyDate = null;
	}

	/**
	* 0 - 投保<p>
	* 1 - 承保<p>
	* 2 - 团体保单增人后未生效状态 或 新增附加险未生效状态<p>
	* 4 - 终止<p>
	* 9 - 附加险自动续保期间
	*/
	public String getAppFlag()
	{
		return AppFlag;
	}
	public void setAppFlag(String aAppFlag)
	{
		AppFlag = aAppFlag;
	}
	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* 对于险种编码：311603，存放的是同心卡的开卡日期
	*/
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* 银代险的内部优惠标志：（该字段对所有银代险有效）<p>
	* 1 默认 其他<p>
	*/
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据
	*/
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
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
	public int getWaitPeriod()
	{
		return WaitPeriod;
	}
	public void setWaitPeriod(int aWaitPeriod)
	{
		WaitPeriod = aWaitPeriod;
	}
	public void setWaitPeriod(String aWaitPeriod)
	{
		if (aWaitPeriod != null && !aWaitPeriod.equals(""))
		{
			Integer tInteger = new Integer(aWaitPeriod);
			int i = tInteger.intValue();
			WaitPeriod = i;
		}
	}

	/**
	* 0：银行转帐<p>
	* 1：自行领取（上柜领取）<p>
	* 2：网上支付
	*/
	public String getGetForm()
	{
		return GetForm;
	}
	public void setGetForm(String aGetForm)
	{
		GetForm = aGetForm;
	}
	public String getGetBankCode()
	{
		return GetBankCode;
	}
	public void setGetBankCode(String aGetBankCode)
	{
		GetBankCode = aGetBankCode;
	}
	public String getGetBankAccNo()
	{
		return GetBankAccNo;
	}
	public void setGetBankAccNo(String aGetBankAccNo)
	{
		GetBankAccNo = aGetBankAccNo;
	}
	public String getGetAccName()
	{
		return GetAccName;
	}
	public void setGetAccName(String aGetAccName)
	{
		GetAccName = aGetAccName;
	}
	public String getKeepValueOpt()
	{
		return KeepValueOpt;
	}
	public void setKeepValueOpt(String aKeepValueOpt)
	{
		KeepValueOpt = aKeepValueOpt;
	}
	public String getPayRuleCode()
	{
		return PayRuleCode;
	}
	public void setPayRuleCode(String aPayRuleCode)
	{
		PayRuleCode = aPayRuleCode;
	}
	public String getAscriptionRuleCode()
	{
		return AscriptionRuleCode;
	}
	public void setAscriptionRuleCode(String aAscriptionRuleCode)
	{
		AscriptionRuleCode = aAscriptionRuleCode;
	}
	/**
	* 0:按保单归属，1:按交费归属
	*/
	public String getAscriptionFlag()
	{
		return AscriptionFlag;
	}
	public void setAscriptionFlag(String aAscriptionFlag)
	{
		AscriptionFlag = aAscriptionFlag;
	}
	/**
	* 用于记录最初承保时，保单的生效日，方便做过换人的保单减人时计算保单的保险期间
	*/
	public String getPCValiDate()
	{
		if( PCValiDate != null )
			return fDate.getString(PCValiDate);
		else
			return null;
	}
	public void setPCValiDate(Date aPCValiDate)
	{
		PCValiDate = aPCValiDate;
	}
	public void setPCValiDate(String aPCValiDate)
	{
		if (aPCValiDate != null && !aPCValiDate.equals("") )
		{
			PCValiDate = fDate.getDate( aPCValiDate );
		}
		else
			PCValiDate = null;
	}

	public String getAutoPubAccFlag()
	{
		return AutoPubAccFlag;
	}
	public void setAutoPubAccFlag(String aAutoPubAccFlag)
	{
		AutoPubAccFlag = aAutoPubAccFlag;
	}
	public String getRiskSequence()
	{
		return RiskSequence;
	}
	public void setRiskSequence(String aRiskSequence)
	{
		RiskSequence = aRiskSequence;
	}
	/**
	* 1表示取消
	*/
	public String getCancleForegetSpecFlag()
	{
		return CancleForegetSpecFlag;
	}
	public void setCancleForegetSpecFlag(String aCancleForegetSpecFlag)
	{
		CancleForegetSpecFlag = aCancleForegetSpecFlag;
	}
	public String getTakeDate()
	{
		if( TakeDate != null )
			return fDate.getString(TakeDate);
		else
			return null;
	}
	public void setTakeDate(Date aTakeDate)
	{
		TakeDate = aTakeDate;
	}
	public void setTakeDate(String aTakeDate)
	{
		if (aTakeDate != null && !aTakeDate.equals("") )
		{
			TakeDate = fDate.getDate( aTakeDate );
		}
		else
			TakeDate = null;
	}

	public String getTakeTime()
	{
		return TakeTime;
	}
	public void setTakeTime(String aTakeTime)
	{
		TakeTime = aTakeTime;
	}
	public String getAirNo()
	{
		return AirNo;
	}
	public void setAirNo(String aAirNo)
	{
		AirNo = aAirNo;
	}
	public String getTicketNo()
	{
		return TicketNo;
	}
	public void setTicketNo(String aTicketNo)
	{
		TicketNo = aTicketNo;
	}
	public String getSeatNo()
	{
		return SeatNo;
	}
	public void setSeatNo(String aSeatNo)
	{
		SeatNo = aSeatNo;
	}
	public double getInputPrem()
	{
		return InputPrem;
	}
	public void setInputPrem(double aInputPrem)
	{
		InputPrem = aInputPrem;
	}
	public void setInputPrem(String aInputPrem)
	{
		if (aInputPrem != null && !aInputPrem.equals(""))
		{
			Double tDouble = new Double(aInputPrem);
			double d = tDouble.doubleValue();
			InputPrem = d;
		}
	}


	/**
	* 使用另外一个 LCPolBakSchema 对象给 Schema 赋值
	* @param: aLCPolBakSchema LCPolBakSchema
	**/
	public void setSchema(LCPolBakSchema aLCPolBakSchema)
	{
		this.GrpContNo = aLCPolBakSchema.getGrpContNo();
		this.GrpPolNo = aLCPolBakSchema.getGrpPolNo();
		this.ContNo = aLCPolBakSchema.getContNo();
		this.PolNo = aLCPolBakSchema.getPolNo();
		this.ProposalNo = aLCPolBakSchema.getProposalNo();
		this.PrtNo = aLCPolBakSchema.getPrtNo();
		this.ContType = aLCPolBakSchema.getContType();
		this.PolTypeFlag = aLCPolBakSchema.getPolTypeFlag();
		this.MainPolNo = aLCPolBakSchema.getMainPolNo();
		this.MasterPolNo = aLCPolBakSchema.getMasterPolNo();
		this.KindCode = aLCPolBakSchema.getKindCode();
		this.RiskCode = aLCPolBakSchema.getRiskCode();
		this.RiskVersion = aLCPolBakSchema.getRiskVersion();
		this.ManageCom = aLCPolBakSchema.getManageCom();
		this.AgentCom = aLCPolBakSchema.getAgentCom();
		this.AgentType = aLCPolBakSchema.getAgentType();
		this.AgentCode = aLCPolBakSchema.getAgentCode();
		this.AgentGroup = aLCPolBakSchema.getAgentGroup();
		this.AgentCode1 = aLCPolBakSchema.getAgentCode1();
		this.SaleChnl = aLCPolBakSchema.getSaleChnl();
		this.Handler = aLCPolBakSchema.getHandler();
		this.InsuredNo = aLCPolBakSchema.getInsuredNo();
		this.InsuredName = aLCPolBakSchema.getInsuredName();
		this.InsuredSex = aLCPolBakSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLCPolBakSchema.getInsuredBirthday());
		this.InsuredAppAge = aLCPolBakSchema.getInsuredAppAge();
		this.InsuredPeoples = aLCPolBakSchema.getInsuredPeoples();
		this.OccupationType = aLCPolBakSchema.getOccupationType();
		this.AppntNo = aLCPolBakSchema.getAppntNo();
		this.AppntName = aLCPolBakSchema.getAppntName();
		this.CValiDate = fDate.getDate( aLCPolBakSchema.getCValiDate());
		this.SignCom = aLCPolBakSchema.getSignCom();
		this.SignDate = fDate.getDate( aLCPolBakSchema.getSignDate());
		this.SignTime = aLCPolBakSchema.getSignTime();
		this.FirstPayDate = fDate.getDate( aLCPolBakSchema.getFirstPayDate());
		this.PayEndDate = fDate.getDate( aLCPolBakSchema.getPayEndDate());
		this.PaytoDate = fDate.getDate( aLCPolBakSchema.getPaytoDate());
		this.GetStartDate = fDate.getDate( aLCPolBakSchema.getGetStartDate());
		this.EndDate = fDate.getDate( aLCPolBakSchema.getEndDate());
		this.AcciEndDate = fDate.getDate( aLCPolBakSchema.getAcciEndDate());
		this.GetYearFlag = aLCPolBakSchema.getGetYearFlag();
		this.GetYear = aLCPolBakSchema.getGetYear();
		this.PayEndYearFlag = aLCPolBakSchema.getPayEndYearFlag();
		this.PayEndYear = aLCPolBakSchema.getPayEndYear();
		this.InsuYearFlag = aLCPolBakSchema.getInsuYearFlag();
		this.InsuYear = aLCPolBakSchema.getInsuYear();
		this.AcciYearFlag = aLCPolBakSchema.getAcciYearFlag();
		this.AcciYear = aLCPolBakSchema.getAcciYear();
		this.GetStartType = aLCPolBakSchema.getGetStartType();
		this.SpecifyValiDate = aLCPolBakSchema.getSpecifyValiDate();
		this.PayMode = aLCPolBakSchema.getPayMode();
		this.PayLocation = aLCPolBakSchema.getPayLocation();
		this.PayIntv = aLCPolBakSchema.getPayIntv();
		this.PayYears = aLCPolBakSchema.getPayYears();
		this.Years = aLCPolBakSchema.getYears();
		this.ManageFeeRate = aLCPolBakSchema.getManageFeeRate();
		this.FloatRate = aLCPolBakSchema.getFloatRate();
		this.PremToAmnt = aLCPolBakSchema.getPremToAmnt();
		this.Mult = aLCPolBakSchema.getMult();
		this.StandPrem = aLCPolBakSchema.getStandPrem();
		this.Prem = aLCPolBakSchema.getPrem();
		this.SumPrem = aLCPolBakSchema.getSumPrem();
		this.Amnt = aLCPolBakSchema.getAmnt();
		this.RiskAmnt = aLCPolBakSchema.getRiskAmnt();
		this.LeavingMoney = aLCPolBakSchema.getLeavingMoney();
		this.EndorseTimes = aLCPolBakSchema.getEndorseTimes();
		this.ClaimTimes = aLCPolBakSchema.getClaimTimes();
		this.LiveTimes = aLCPolBakSchema.getLiveTimes();
		this.RenewCount = aLCPolBakSchema.getRenewCount();
		this.LastGetDate = fDate.getDate( aLCPolBakSchema.getLastGetDate());
		this.LastLoanDate = fDate.getDate( aLCPolBakSchema.getLastLoanDate());
		this.LastRegetDate = fDate.getDate( aLCPolBakSchema.getLastRegetDate());
		this.LastEdorDate = fDate.getDate( aLCPolBakSchema.getLastEdorDate());
		this.LastRevDate = fDate.getDate( aLCPolBakSchema.getLastRevDate());
		this.RnewFlag = aLCPolBakSchema.getRnewFlag();
		this.StopFlag = aLCPolBakSchema.getStopFlag();
		this.ExpiryFlag = aLCPolBakSchema.getExpiryFlag();
		this.AutoPayFlag = aLCPolBakSchema.getAutoPayFlag();
		this.InterestDifFlag = aLCPolBakSchema.getInterestDifFlag();
		this.SubFlag = aLCPolBakSchema.getSubFlag();
		this.BnfFlag = aLCPolBakSchema.getBnfFlag();
		this.HealthCheckFlag = aLCPolBakSchema.getHealthCheckFlag();
		this.ImpartFlag = aLCPolBakSchema.getImpartFlag();
		this.ReinsureFlag = aLCPolBakSchema.getReinsureFlag();
		this.AgentPayFlag = aLCPolBakSchema.getAgentPayFlag();
		this.AgentGetFlag = aLCPolBakSchema.getAgentGetFlag();
		this.LiveGetMode = aLCPolBakSchema.getLiveGetMode();
		this.DeadGetMode = aLCPolBakSchema.getDeadGetMode();
		this.BonusGetMode = aLCPolBakSchema.getBonusGetMode();
		this.BonusMan = aLCPolBakSchema.getBonusMan();
		this.DeadFlag = aLCPolBakSchema.getDeadFlag();
		this.SmokeFlag = aLCPolBakSchema.getSmokeFlag();
		this.Remark = aLCPolBakSchema.getRemark();
		this.ApproveFlag = aLCPolBakSchema.getApproveFlag();
		this.ApproveCode = aLCPolBakSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLCPolBakSchema.getApproveDate());
		this.ApproveTime = aLCPolBakSchema.getApproveTime();
		this.UWFlag = aLCPolBakSchema.getUWFlag();
		this.UWCode = aLCPolBakSchema.getUWCode();
		this.UWDate = fDate.getDate( aLCPolBakSchema.getUWDate());
		this.UWTime = aLCPolBakSchema.getUWTime();
		this.PolApplyDate = fDate.getDate( aLCPolBakSchema.getPolApplyDate());
		this.AppFlag = aLCPolBakSchema.getAppFlag();
		this.PolState = aLCPolBakSchema.getPolState();
		this.StandbyFlag1 = aLCPolBakSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCPolBakSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLCPolBakSchema.getStandbyFlag3();
		this.Operator = aLCPolBakSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCPolBakSchema.getMakeDate());
		this.MakeTime = aLCPolBakSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPolBakSchema.getModifyDate());
		this.ModifyTime = aLCPolBakSchema.getModifyTime();
		this.WaitPeriod = aLCPolBakSchema.getWaitPeriod();
		this.GetForm = aLCPolBakSchema.getGetForm();
		this.GetBankCode = aLCPolBakSchema.getGetBankCode();
		this.GetBankAccNo = aLCPolBakSchema.getGetBankAccNo();
		this.GetAccName = aLCPolBakSchema.getGetAccName();
		this.KeepValueOpt = aLCPolBakSchema.getKeepValueOpt();
		this.PayRuleCode = aLCPolBakSchema.getPayRuleCode();
		this.AscriptionRuleCode = aLCPolBakSchema.getAscriptionRuleCode();
		this.AscriptionFlag = aLCPolBakSchema.getAscriptionFlag();
		this.PCValiDate = fDate.getDate( aLCPolBakSchema.getPCValiDate());
		this.AutoPubAccFlag = aLCPolBakSchema.getAutoPubAccFlag();
		this.RiskSequence = aLCPolBakSchema.getRiskSequence();
		this.CancleForegetSpecFlag = aLCPolBakSchema.getCancleForegetSpecFlag();
		this.TakeDate = fDate.getDate( aLCPolBakSchema.getTakeDate());
		this.TakeTime = aLCPolBakSchema.getTakeTime();
		this.AirNo = aLCPolBakSchema.getAirNo();
		this.TicketNo = aLCPolBakSchema.getTicketNo();
		this.SeatNo = aLCPolBakSchema.getSeatNo();
		this.InputPrem = aLCPolBakSchema.getInputPrem();
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

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("PolTypeFlag") == null )
				this.PolTypeFlag = null;
			else
				this.PolTypeFlag = rs.getString("PolTypeFlag").trim();

			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("MasterPolNo") == null )
				this.MasterPolNo = null;
			else
				this.MasterPolNo = rs.getString("MasterPolNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode1") == null )
				this.AgentCode1 = null;
			else
				this.AgentCode1 = rs.getString("AgentCode1").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			this.InsuredAppAge = rs.getInt("InsuredAppAge");
			this.InsuredPeoples = rs.getInt("InsuredPeoples");
			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.GetStartDate = rs.getDate("GetStartDate");
			this.EndDate = rs.getDate("EndDate");
			this.AcciEndDate = rs.getDate("AcciEndDate");
			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			this.GetYear = rs.getInt("GetYear");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("AcciYearFlag") == null )
				this.AcciYearFlag = null;
			else
				this.AcciYearFlag = rs.getString("AcciYearFlag").trim();

			this.AcciYear = rs.getInt("AcciYear");
			if( rs.getString("GetStartType") == null )
				this.GetStartType = null;
			else
				this.GetStartType = rs.getString("GetStartType").trim();

			if( rs.getString("SpecifyValiDate") == null )
				this.SpecifyValiDate = null;
			else
				this.SpecifyValiDate = rs.getString("SpecifyValiDate").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayLocation") == null )
				this.PayLocation = null;
			else
				this.PayLocation = rs.getString("PayLocation").trim();

			this.PayIntv = rs.getInt("PayIntv");
			this.PayYears = rs.getInt("PayYears");
			this.Years = rs.getInt("Years");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			this.FloatRate = rs.getDouble("FloatRate");
			if( rs.getString("PremToAmnt") == null )
				this.PremToAmnt = null;
			else
				this.PremToAmnt = rs.getString("PremToAmnt").trim();

			this.Mult = rs.getDouble("Mult");
			this.StandPrem = rs.getDouble("StandPrem");
			this.Prem = rs.getDouble("Prem");
			this.SumPrem = rs.getDouble("SumPrem");
			this.Amnt = rs.getDouble("Amnt");
			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.LeavingMoney = rs.getDouble("LeavingMoney");
			this.EndorseTimes = rs.getInt("EndorseTimes");
			this.ClaimTimes = rs.getInt("ClaimTimes");
			this.LiveTimes = rs.getInt("LiveTimes");
			this.RenewCount = rs.getInt("RenewCount");
			this.LastGetDate = rs.getDate("LastGetDate");
			this.LastLoanDate = rs.getDate("LastLoanDate");
			this.LastRegetDate = rs.getDate("LastRegetDate");
			this.LastEdorDate = rs.getDate("LastEdorDate");
			this.LastRevDate = rs.getDate("LastRevDate");
			this.RnewFlag = rs.getInt("RnewFlag");
			if( rs.getString("StopFlag") == null )
				this.StopFlag = null;
			else
				this.StopFlag = rs.getString("StopFlag").trim();

			if( rs.getString("ExpiryFlag") == null )
				this.ExpiryFlag = null;
			else
				this.ExpiryFlag = rs.getString("ExpiryFlag").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			if( rs.getString("InterestDifFlag") == null )
				this.InterestDifFlag = null;
			else
				this.InterestDifFlag = rs.getString("InterestDifFlag").trim();

			if( rs.getString("SubFlag") == null )
				this.SubFlag = null;
			else
				this.SubFlag = rs.getString("SubFlag").trim();

			if( rs.getString("BnfFlag") == null )
				this.BnfFlag = null;
			else
				this.BnfFlag = rs.getString("BnfFlag").trim();

			if( rs.getString("HealthCheckFlag") == null )
				this.HealthCheckFlag = null;
			else
				this.HealthCheckFlag = rs.getString("HealthCheckFlag").trim();

			if( rs.getString("ImpartFlag") == null )
				this.ImpartFlag = null;
			else
				this.ImpartFlag = rs.getString("ImpartFlag").trim();

			if( rs.getString("ReinsureFlag") == null )
				this.ReinsureFlag = null;
			else
				this.ReinsureFlag = rs.getString("ReinsureFlag").trim();

			if( rs.getString("AgentPayFlag") == null )
				this.AgentPayFlag = null;
			else
				this.AgentPayFlag = rs.getString("AgentPayFlag").trim();

			if( rs.getString("AgentGetFlag") == null )
				this.AgentGetFlag = null;
			else
				this.AgentGetFlag = rs.getString("AgentGetFlag").trim();

			if( rs.getString("LiveGetMode") == null )
				this.LiveGetMode = null;
			else
				this.LiveGetMode = rs.getString("LiveGetMode").trim();

			if( rs.getString("DeadGetMode") == null )
				this.DeadGetMode = null;
			else
				this.DeadGetMode = rs.getString("DeadGetMode").trim();

			if( rs.getString("BonusGetMode") == null )
				this.BonusGetMode = null;
			else
				this.BonusGetMode = rs.getString("BonusGetMode").trim();

			if( rs.getString("BonusMan") == null )
				this.BonusMan = null;
			else
				this.BonusMan = rs.getString("BonusMan").trim();

			if( rs.getString("DeadFlag") == null )
				this.DeadFlag = null;
			else
				this.DeadFlag = rs.getString("DeadFlag").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			this.PolApplyDate = rs.getDate("PolApplyDate");
			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

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

			this.WaitPeriod = rs.getInt("WaitPeriod");
			if( rs.getString("GetForm") == null )
				this.GetForm = null;
			else
				this.GetForm = rs.getString("GetForm").trim();

			if( rs.getString("GetBankCode") == null )
				this.GetBankCode = null;
			else
				this.GetBankCode = rs.getString("GetBankCode").trim();

			if( rs.getString("GetBankAccNo") == null )
				this.GetBankAccNo = null;
			else
				this.GetBankAccNo = rs.getString("GetBankAccNo").trim();

			if( rs.getString("GetAccName") == null )
				this.GetAccName = null;
			else
				this.GetAccName = rs.getString("GetAccName").trim();

			if( rs.getString("KeepValueOpt") == null )
				this.KeepValueOpt = null;
			else
				this.KeepValueOpt = rs.getString("KeepValueOpt").trim();

			if( rs.getString("PayRuleCode") == null )
				this.PayRuleCode = null;
			else
				this.PayRuleCode = rs.getString("PayRuleCode").trim();

			if( rs.getString("AscriptionRuleCode") == null )
				this.AscriptionRuleCode = null;
			else
				this.AscriptionRuleCode = rs.getString("AscriptionRuleCode").trim();

			if( rs.getString("AscriptionFlag") == null )
				this.AscriptionFlag = null;
			else
				this.AscriptionFlag = rs.getString("AscriptionFlag").trim();

			this.PCValiDate = rs.getDate("PCValiDate");
			if( rs.getString("AutoPubAccFlag") == null )
				this.AutoPubAccFlag = null;
			else
				this.AutoPubAccFlag = rs.getString("AutoPubAccFlag").trim();

			if( rs.getString("RiskSequence") == null )
				this.RiskSequence = null;
			else
				this.RiskSequence = rs.getString("RiskSequence").trim();

			if( rs.getString("CancleForegetSpecFlag") == null )
				this.CancleForegetSpecFlag = null;
			else
				this.CancleForegetSpecFlag = rs.getString("CancleForegetSpecFlag").trim();

			this.TakeDate = rs.getDate("TakeDate");
			if( rs.getString("TakeTime") == null )
				this.TakeTime = null;
			else
				this.TakeTime = rs.getString("TakeTime").trim();

			if( rs.getString("AirNo") == null )
				this.AirNo = null;
			else
				this.AirNo = rs.getString("AirNo").trim();

			if( rs.getString("TicketNo") == null )
				this.TicketNo = null;
			else
				this.TicketNo = rs.getString("TicketNo").trim();

			if( rs.getString("SeatNo") == null )
				this.SeatNo = null;
			else
				this.SeatNo = rs.getString("SeatNo").trim();

			this.InputPrem = rs.getDouble("InputPrem");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCPolBak表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolBakSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPolBakSchema getSchema()
	{
		LCPolBakSchema aLCPolBakSchema = new LCPolBakSchema();
		aLCPolBakSchema.setSchema(this);
		return aLCPolBakSchema;
	}

	public LCPolBakDB getDB()
	{
		LCPolBakDB aDBOper = new LCPolBakDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPolBak描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolTypeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MasterPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredAppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AcciEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AcciYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecifyValiDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FloatRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremToAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LeavingMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndorseTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LiveTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RenewCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastLoanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastRegetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastEdorDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastRevDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RnewFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StopFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpiryFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestDifFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthCheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WaitPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetForm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeepValueOpt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PCValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPubAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskSequence)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CancleForegetSpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AirNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TicketNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SeatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InputPrem));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPolBak>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PolTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MasterPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			InsuredAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			InsuredPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			AcciEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).intValue();
			AcciYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			AcciYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).intValue();
			GetStartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			SpecifyValiDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).intValue();
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).intValue();
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).doubleValue();
			FloatRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).doubleValue();
			PremToAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,64,SysConst.PACKAGESPILTER))).doubleValue();
			LeavingMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			EndorseTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,66,SysConst.PACKAGESPILTER))).intValue();
			ClaimTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).intValue();
			LiveTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,68,SysConst.PACKAGESPILTER))).intValue();
			RenewCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).intValue();
			LastGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70,SysConst.PACKAGESPILTER));
			LastLoanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71,SysConst.PACKAGESPILTER));
			LastRegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72,SysConst.PACKAGESPILTER));
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73,SysConst.PACKAGESPILTER));
			LastRevDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74,SysConst.PACKAGESPILTER));
			RnewFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).intValue();
			StopFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			ExpiryFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			InterestDifFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			SubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			BnfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			HealthCheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			ImpartFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			ReinsureFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			AgentPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			AgentGetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			LiveGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			DeadGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			BonusGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			BonusMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			DeadFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102,SysConst.PACKAGESPILTER));
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112, SysConst.PACKAGESPILTER );
			WaitPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,113,SysConst.PACKAGESPILTER))).intValue();
			GetForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114, SysConst.PACKAGESPILTER );
			GetBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			GetBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 116, SysConst.PACKAGESPILTER );
			GetAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 117, SysConst.PACKAGESPILTER );
			KeepValueOpt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 118, SysConst.PACKAGESPILTER );
			PayRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 119, SysConst.PACKAGESPILTER );
			AscriptionRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 120, SysConst.PACKAGESPILTER );
			AscriptionFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 121, SysConst.PACKAGESPILTER );
			PCValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 122,SysConst.PACKAGESPILTER));
			AutoPubAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 123, SysConst.PACKAGESPILTER );
			RiskSequence = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 124, SysConst.PACKAGESPILTER );
			CancleForegetSpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 125, SysConst.PACKAGESPILTER );
			TakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 126,SysConst.PACKAGESPILTER));
			TakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 127, SysConst.PACKAGESPILTER );
			AirNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 128, SysConst.PACKAGESPILTER );
			TicketNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 129, SysConst.PACKAGESPILTER );
			SeatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 130, SysConst.PACKAGESPILTER );
			InputPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,131,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolBakSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("PolTypeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolTypeFlag));
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equalsIgnoreCase("MasterPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MasterPolNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode1));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
		}
		if (FCode.equalsIgnoreCase("InsuredAppAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAppAge));
		}
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredPeoples));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignCom));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignTime));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearFlag));
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("AcciYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYearFlag));
		}
		if (FCode.equalsIgnoreCase("AcciYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYear));
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartType));
		}
		if (FCode.equalsIgnoreCase("SpecifyValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecifyValiDate));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PayLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLocation));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FloatRate));
		}
		if (FCode.equalsIgnoreCase("PremToAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremToAmnt));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmnt));
		}
		if (FCode.equalsIgnoreCase("LeavingMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LeavingMoney));
		}
		if (FCode.equalsIgnoreCase("EndorseTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndorseTimes));
		}
		if (FCode.equalsIgnoreCase("ClaimTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimTimes));
		}
		if (FCode.equalsIgnoreCase("LiveTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveTimes));
		}
		if (FCode.equalsIgnoreCase("RenewCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewCount));
		}
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
		}
		if (FCode.equalsIgnoreCase("LastLoanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
		}
		if (FCode.equalsIgnoreCase("LastRegetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastRegetDate()));
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
		}
		if (FCode.equalsIgnoreCase("LastRevDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastRevDate()));
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewFlag));
		}
		if (FCode.equalsIgnoreCase("StopFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StopFlag));
		}
		if (FCode.equalsIgnoreCase("ExpiryFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpiryFlag));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("InterestDifFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestDifFlag));
		}
		if (FCode.equalsIgnoreCase("SubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFlag));
		}
		if (FCode.equalsIgnoreCase("BnfFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfFlag));
		}
		if (FCode.equalsIgnoreCase("HealthCheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthCheckFlag));
		}
		if (FCode.equalsIgnoreCase("ImpartFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartFlag));
		}
		if (FCode.equalsIgnoreCase("ReinsureFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureFlag));
		}
		if (FCode.equalsIgnoreCase("AgentPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPayFlag));
		}
		if (FCode.equalsIgnoreCase("AgentGetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetFlag));
		}
		if (FCode.equalsIgnoreCase("LiveGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveGetMode));
		}
		if (FCode.equalsIgnoreCase("DeadGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadGetMode));
		}
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGetMode));
		}
		if (FCode.equalsIgnoreCase("BonusMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusMan));
		}
		if (FCode.equalsIgnoreCase("DeadFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadFlag));
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
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
		if (FCode.equalsIgnoreCase("WaitPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WaitPeriod));
		}
		if (FCode.equalsIgnoreCase("GetForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetForm));
		}
		if (FCode.equalsIgnoreCase("GetBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetBankCode));
		}
		if (FCode.equalsIgnoreCase("GetBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetBankAccNo));
		}
		if (FCode.equalsIgnoreCase("GetAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetAccName));
		}
		if (FCode.equalsIgnoreCase("KeepValueOpt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeepValueOpt));
		}
		if (FCode.equalsIgnoreCase("PayRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayRuleCode));
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionRuleCode));
		}
		if (FCode.equalsIgnoreCase("AscriptionFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionFlag));
		}
		if (FCode.equalsIgnoreCase("PCValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPCValiDate()));
		}
		if (FCode.equalsIgnoreCase("AutoPubAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPubAccFlag));
		}
		if (FCode.equalsIgnoreCase("RiskSequence"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSequence));
		}
		if (FCode.equalsIgnoreCase("CancleForegetSpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CancleForegetSpecFlag));
		}
		if (FCode.equalsIgnoreCase("TakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTakeDate()));
		}
		if (FCode.equalsIgnoreCase("TakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TakeTime));
		}
		if (FCode.equalsIgnoreCase("AirNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AirNo));
		}
		if (FCode.equalsIgnoreCase("TicketNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TicketNo));
		}
		if (FCode.equalsIgnoreCase("SeatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeatNo));
		}
		if (FCode.equalsIgnoreCase("InputPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputPrem));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PolTypeFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MasterPolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 25:
				strFieldValue = String.valueOf(InsuredAppAge);
				break;
			case 26:
				strFieldValue = String.valueOf(InsuredPeoples);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 41:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 43:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 45:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(AcciYearFlag);
				break;
			case 47:
				strFieldValue = String.valueOf(AcciYear);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(GetStartType);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(SpecifyValiDate);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
				break;
			case 52:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 53:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 54:
				strFieldValue = String.valueOf(Years);
				break;
			case 55:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 56:
				strFieldValue = String.valueOf(FloatRate);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(PremToAmnt);
				break;
			case 58:
				strFieldValue = String.valueOf(Mult);
				break;
			case 59:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 60:
				strFieldValue = String.valueOf(Prem);
				break;
			case 61:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 62:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 63:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 64:
				strFieldValue = String.valueOf(LeavingMoney);
				break;
			case 65:
				strFieldValue = String.valueOf(EndorseTimes);
				break;
			case 66:
				strFieldValue = String.valueOf(ClaimTimes);
				break;
			case 67:
				strFieldValue = String.valueOf(LiveTimes);
				break;
			case 68:
				strFieldValue = String.valueOf(RenewCount);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastRegetDate()));
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastRevDate()));
				break;
			case 74:
				strFieldValue = String.valueOf(RnewFlag);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(StopFlag);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(ExpiryFlag);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(InterestDifFlag);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(SubFlag);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(BnfFlag);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(HealthCheckFlag);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(ImpartFlag);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(ReinsureFlag);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(AgentPayFlag);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(AgentGetFlag);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(LiveGetMode);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(DeadGetMode);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(BonusGetMode);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(BonusMan);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(DeadFlag);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 112:
				strFieldValue = String.valueOf(WaitPeriod);
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(GetForm);
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(GetBankCode);
				break;
			case 115:
				strFieldValue = StrTool.GBKToUnicode(GetBankAccNo);
				break;
			case 116:
				strFieldValue = StrTool.GBKToUnicode(GetAccName);
				break;
			case 117:
				strFieldValue = StrTool.GBKToUnicode(KeepValueOpt);
				break;
			case 118:
				strFieldValue = StrTool.GBKToUnicode(PayRuleCode);
				break;
			case 119:
				strFieldValue = StrTool.GBKToUnicode(AscriptionRuleCode);
				break;
			case 120:
				strFieldValue = StrTool.GBKToUnicode(AscriptionFlag);
				break;
			case 121:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPCValiDate()));
				break;
			case 122:
				strFieldValue = StrTool.GBKToUnicode(AutoPubAccFlag);
				break;
			case 123:
				strFieldValue = StrTool.GBKToUnicode(RiskSequence);
				break;
			case 124:
				strFieldValue = StrTool.GBKToUnicode(CancleForegetSpecFlag);
				break;
			case 125:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTakeDate()));
				break;
			case 126:
				strFieldValue = StrTool.GBKToUnicode(TakeTime);
				break;
			case 127:
				strFieldValue = StrTool.GBKToUnicode(AirNo);
				break;
			case 128:
				strFieldValue = StrTool.GBKToUnicode(TicketNo);
				break;
			case 129:
				strFieldValue = StrTool.GBKToUnicode(SeatNo);
				break;
			case 130:
				strFieldValue = String.valueOf(InputPrem);
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
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
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("PolTypeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolTypeFlag = FValue.trim();
			}
			else
				PolTypeFlag = null;
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolNo = FValue.trim();
			}
			else
				MainPolNo = null;
		}
		if (FCode.equalsIgnoreCase("MasterPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MasterPolNo = FValue.trim();
			}
			else
				MasterPolNo = null;
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
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
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
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
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode1 = FValue.trim();
			}
			else
				AgentCode1 = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredBirthday = fDate.getDate( FValue );
			}
			else
				InsuredBirthday = null;
		}
		if (FCode.equalsIgnoreCase("InsuredAppAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredAppAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
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
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignCom = FValue.trim();
			}
			else
				SignCom = null;
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
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignTime = FValue.trim();
			}
			else
				SignTime = null;
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayEndDate = fDate.getDate( FValue );
			}
			else
				PayEndDate = null;
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetStartDate = fDate.getDate( FValue );
			}
			else
				GetStartDate = null;
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
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AcciEndDate = fDate.getDate( FValue );
			}
			else
				AcciEndDate = null;
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetYearFlag = FValue.trim();
			}
			else
				GetYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayEndYear = i;
			}
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
		if (FCode.equalsIgnoreCase("AcciYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcciYearFlag = FValue.trim();
			}
			else
				AcciYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("AcciYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AcciYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartType = FValue.trim();
			}
			else
				GetStartType = null;
		}
		if (FCode.equalsIgnoreCase("SpecifyValiDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecifyValiDate = FValue.trim();
			}
			else
				SpecifyValiDate = null;
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equalsIgnoreCase("PayLocation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayLocation = FValue.trim();
			}
			else
				PayLocation = null;
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
			}
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
			}
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ManageFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FloatRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremToAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremToAmnt = FValue.trim();
			}
			else
				PremToAmnt = null;
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
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
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("LeavingMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LeavingMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndorseTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndorseTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClaimTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("LiveTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LiveTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("RenewCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RenewCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastGetDate = fDate.getDate( FValue );
			}
			else
				LastGetDate = null;
		}
		if (FCode.equalsIgnoreCase("LastLoanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastLoanDate = fDate.getDate( FValue );
			}
			else
				LastLoanDate = null;
		}
		if (FCode.equalsIgnoreCase("LastRegetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastRegetDate = fDate.getDate( FValue );
			}
			else
				LastRegetDate = null;
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastEdorDate = fDate.getDate( FValue );
			}
			else
				LastEdorDate = null;
		}
		if (FCode.equalsIgnoreCase("LastRevDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastRevDate = fDate.getDate( FValue );
			}
			else
				LastRevDate = null;
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RnewFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("StopFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StopFlag = FValue.trim();
			}
			else
				StopFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExpiryFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpiryFlag = FValue.trim();
			}
			else
				ExpiryFlag = null;
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayFlag = FValue.trim();
			}
			else
				AutoPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("InterestDifFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestDifFlag = FValue.trim();
			}
			else
				InterestDifFlag = null;
		}
		if (FCode.equalsIgnoreCase("SubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFlag = FValue.trim();
			}
			else
				SubFlag = null;
		}
		if (FCode.equalsIgnoreCase("BnfFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfFlag = FValue.trim();
			}
			else
				BnfFlag = null;
		}
		if (FCode.equalsIgnoreCase("HealthCheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthCheckFlag = FValue.trim();
			}
			else
				HealthCheckFlag = null;
		}
		if (FCode.equalsIgnoreCase("ImpartFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartFlag = FValue.trim();
			}
			else
				ImpartFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReinsureFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureFlag = FValue.trim();
			}
			else
				ReinsureFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPayFlag = FValue.trim();
			}
			else
				AgentPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetFlag = FValue.trim();
			}
			else
				AgentGetFlag = null;
		}
		if (FCode.equalsIgnoreCase("LiveGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveGetMode = FValue.trim();
			}
			else
				LiveGetMode = null;
		}
		if (FCode.equalsIgnoreCase("DeadGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadGetMode = FValue.trim();
			}
			else
				DeadGetMode = null;
		}
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusGetMode = FValue.trim();
			}
			else
				BonusGetMode = null;
		}
		if (FCode.equalsIgnoreCase("BonusMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusMan = FValue.trim();
			}
			else
				BonusMan = null;
		}
		if (FCode.equalsIgnoreCase("DeadFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadFlag = FValue.trim();
			}
			else
				DeadFlag = null;
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SmokeFlag = FValue.trim();
			}
			else
				SmokeFlag = null;
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
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveCode = FValue.trim();
			}
			else
				ApproveCode = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
			}
			else
				UWDate = null;
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWTime = FValue.trim();
			}
			else
				UWTime = null;
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PolApplyDate = fDate.getDate( FValue );
			}
			else
				PolApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
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
		if (FCode.equalsIgnoreCase("WaitPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WaitPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetForm = FValue.trim();
			}
			else
				GetForm = null;
		}
		if (FCode.equalsIgnoreCase("GetBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetBankCode = FValue.trim();
			}
			else
				GetBankCode = null;
		}
		if (FCode.equalsIgnoreCase("GetBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetBankAccNo = FValue.trim();
			}
			else
				GetBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("GetAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetAccName = FValue.trim();
			}
			else
				GetAccName = null;
		}
		if (FCode.equalsIgnoreCase("KeepValueOpt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeepValueOpt = FValue.trim();
			}
			else
				KeepValueOpt = null;
		}
		if (FCode.equalsIgnoreCase("PayRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayRuleCode = FValue.trim();
			}
			else
				PayRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptionRuleCode = FValue.trim();
			}
			else
				AscriptionRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("AscriptionFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptionFlag = FValue.trim();
			}
			else
				AscriptionFlag = null;
		}
		if (FCode.equalsIgnoreCase("PCValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PCValiDate = fDate.getDate( FValue );
			}
			else
				PCValiDate = null;
		}
		if (FCode.equalsIgnoreCase("AutoPubAccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPubAccFlag = FValue.trim();
			}
			else
				AutoPubAccFlag = null;
		}
		if (FCode.equalsIgnoreCase("RiskSequence"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskSequence = FValue.trim();
			}
			else
				RiskSequence = null;
		}
		if (FCode.equalsIgnoreCase("CancleForegetSpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CancleForegetSpecFlag = FValue.trim();
			}
			else
				CancleForegetSpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("TakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TakeDate = fDate.getDate( FValue );
			}
			else
				TakeDate = null;
		}
		if (FCode.equalsIgnoreCase("TakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TakeTime = FValue.trim();
			}
			else
				TakeTime = null;
		}
		if (FCode.equalsIgnoreCase("AirNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AirNo = FValue.trim();
			}
			else
				AirNo = null;
		}
		if (FCode.equalsIgnoreCase("TicketNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TicketNo = FValue.trim();
			}
			else
				TicketNo = null;
		}
		if (FCode.equalsIgnoreCase("SeatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SeatNo = FValue.trim();
			}
			else
				SeatNo = null;
		}
		if (FCode.equalsIgnoreCase("InputPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InputPrem = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCPolBakSchema other = (LCPolBakSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ContType.equals(other.getContType())
			&& PolTypeFlag.equals(other.getPolTypeFlag())
			&& MainPolNo.equals(other.getMainPolNo())
			&& MasterPolNo.equals(other.getMasterPolNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Handler.equals(other.getHandler())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredAppAge == other.getInsuredAppAge()
			&& InsuredPeoples == other.getInsuredPeoples()
			&& OccupationType.equals(other.getOccupationType())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& SignCom.equals(other.getSignCom())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(AcciEndDate).equals(other.getAcciEndDate())
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& GetYear == other.getGetYear()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& AcciYearFlag.equals(other.getAcciYearFlag())
			&& AcciYear == other.getAcciYear()
			&& GetStartType.equals(other.getGetStartType())
			&& SpecifyValiDate.equals(other.getSpecifyValiDate())
			&& PayMode.equals(other.getPayMode())
			&& PayLocation.equals(other.getPayLocation())
			&& PayIntv == other.getPayIntv()
			&& PayYears == other.getPayYears()
			&& Years == other.getYears()
			&& ManageFeeRate == other.getManageFeeRate()
			&& FloatRate == other.getFloatRate()
			&& PremToAmnt.equals(other.getPremToAmnt())
			&& Mult == other.getMult()
			&& StandPrem == other.getStandPrem()
			&& Prem == other.getPrem()
			&& SumPrem == other.getSumPrem()
			&& Amnt == other.getAmnt()
			&& RiskAmnt == other.getRiskAmnt()
			&& LeavingMoney == other.getLeavingMoney()
			&& EndorseTimes == other.getEndorseTimes()
			&& ClaimTimes == other.getClaimTimes()
			&& LiveTimes == other.getLiveTimes()
			&& RenewCount == other.getRenewCount()
			&& fDate.getString(LastGetDate).equals(other.getLastGetDate())
			&& fDate.getString(LastLoanDate).equals(other.getLastLoanDate())
			&& fDate.getString(LastRegetDate).equals(other.getLastRegetDate())
			&& fDate.getString(LastEdorDate).equals(other.getLastEdorDate())
			&& fDate.getString(LastRevDate).equals(other.getLastRevDate())
			&& RnewFlag == other.getRnewFlag()
			&& StopFlag.equals(other.getStopFlag())
			&& ExpiryFlag.equals(other.getExpiryFlag())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& InterestDifFlag.equals(other.getInterestDifFlag())
			&& SubFlag.equals(other.getSubFlag())
			&& BnfFlag.equals(other.getBnfFlag())
			&& HealthCheckFlag.equals(other.getHealthCheckFlag())
			&& ImpartFlag.equals(other.getImpartFlag())
			&& ReinsureFlag.equals(other.getReinsureFlag())
			&& AgentPayFlag.equals(other.getAgentPayFlag())
			&& AgentGetFlag.equals(other.getAgentGetFlag())
			&& LiveGetMode.equals(other.getLiveGetMode())
			&& DeadGetMode.equals(other.getDeadGetMode())
			&& BonusGetMode.equals(other.getBonusGetMode())
			&& BonusMan.equals(other.getBonusMan())
			&& DeadFlag.equals(other.getDeadFlag())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& Remark.equals(other.getRemark())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& AppFlag.equals(other.getAppFlag())
			&& PolState.equals(other.getPolState())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& WaitPeriod == other.getWaitPeriod()
			&& GetForm.equals(other.getGetForm())
			&& GetBankCode.equals(other.getGetBankCode())
			&& GetBankAccNo.equals(other.getGetBankAccNo())
			&& GetAccName.equals(other.getGetAccName())
			&& KeepValueOpt.equals(other.getKeepValueOpt())
			&& PayRuleCode.equals(other.getPayRuleCode())
			&& AscriptionRuleCode.equals(other.getAscriptionRuleCode())
			&& AscriptionFlag.equals(other.getAscriptionFlag())
			&& fDate.getString(PCValiDate).equals(other.getPCValiDate())
			&& AutoPubAccFlag.equals(other.getAutoPubAccFlag())
			&& RiskSequence.equals(other.getRiskSequence())
			&& CancleForegetSpecFlag.equals(other.getCancleForegetSpecFlag())
			&& fDate.getString(TakeDate).equals(other.getTakeDate())
			&& TakeTime.equals(other.getTakeTime())
			&& AirNo.equals(other.getAirNo())
			&& TicketNo.equals(other.getTicketNo())
			&& SeatNo.equals(other.getSeatNo())
			&& InputPrem == other.getInputPrem();
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 4;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 5;
		}
		if( strFieldName.equals("ContType") ) {
			return 6;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return 7;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return 9;
		}
		if( strFieldName.equals("KindCode") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 12;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 13;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 14;
		}
		if( strFieldName.equals("AgentType") ) {
			return 15;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 16;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 17;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 18;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 19;
		}
		if( strFieldName.equals("Handler") ) {
			return 20;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 21;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 22;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 23;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return 25;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 26;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 27;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 28;
		}
		if( strFieldName.equals("AppntName") ) {
			return 29;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 30;
		}
		if( strFieldName.equals("SignCom") ) {
			return 31;
		}
		if( strFieldName.equals("SignDate") ) {
			return 32;
		}
		if( strFieldName.equals("SignTime") ) {
			return 33;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 34;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 35;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 36;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 37;
		}
		if( strFieldName.equals("EndDate") ) {
			return 38;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return 39;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 40;
		}
		if( strFieldName.equals("GetYear") ) {
			return 41;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 42;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 43;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 44;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 45;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return 46;
		}
		if( strFieldName.equals("AcciYear") ) {
			return 47;
		}
		if( strFieldName.equals("GetStartType") ) {
			return 48;
		}
		if( strFieldName.equals("SpecifyValiDate") ) {
			return 49;
		}
		if( strFieldName.equals("PayMode") ) {
			return 50;
		}
		if( strFieldName.equals("PayLocation") ) {
			return 51;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 52;
		}
		if( strFieldName.equals("PayYears") ) {
			return 53;
		}
		if( strFieldName.equals("Years") ) {
			return 54;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 55;
		}
		if( strFieldName.equals("FloatRate") ) {
			return 56;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return 57;
		}
		if( strFieldName.equals("Mult") ) {
			return 58;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 59;
		}
		if( strFieldName.equals("Prem") ) {
			return 60;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 61;
		}
		if( strFieldName.equals("Amnt") ) {
			return 62;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 63;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return 64;
		}
		if( strFieldName.equals("EndorseTimes") ) {
			return 65;
		}
		if( strFieldName.equals("ClaimTimes") ) {
			return 66;
		}
		if( strFieldName.equals("LiveTimes") ) {
			return 67;
		}
		if( strFieldName.equals("RenewCount") ) {
			return 68;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return 69;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return 70;
		}
		if( strFieldName.equals("LastRegetDate") ) {
			return 71;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 72;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return 73;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 74;
		}
		if( strFieldName.equals("StopFlag") ) {
			return 75;
		}
		if( strFieldName.equals("ExpiryFlag") ) {
			return 76;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 77;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return 78;
		}
		if( strFieldName.equals("SubFlag") ) {
			return 79;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return 80;
		}
		if( strFieldName.equals("HealthCheckFlag") ) {
			return 81;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return 82;
		}
		if( strFieldName.equals("ReinsureFlag") ) {
			return 83;
		}
		if( strFieldName.equals("AgentPayFlag") ) {
			return 84;
		}
		if( strFieldName.equals("AgentGetFlag") ) {
			return 85;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return 86;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return 87;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return 88;
		}
		if( strFieldName.equals("BonusMan") ) {
			return 89;
		}
		if( strFieldName.equals("DeadFlag") ) {
			return 90;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 91;
		}
		if( strFieldName.equals("Remark") ) {
			return 92;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 93;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 94;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 95;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 96;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 97;
		}
		if( strFieldName.equals("UWCode") ) {
			return 98;
		}
		if( strFieldName.equals("UWDate") ) {
			return 99;
		}
		if( strFieldName.equals("UWTime") ) {
			return 100;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 101;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 102;
		}
		if( strFieldName.equals("PolState") ) {
			return 103;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 104;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 105;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 106;
		}
		if( strFieldName.equals("Operator") ) {
			return 107;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 108;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 109;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 110;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 111;
		}
		if( strFieldName.equals("WaitPeriod") ) {
			return 112;
		}
		if( strFieldName.equals("GetForm") ) {
			return 113;
		}
		if( strFieldName.equals("GetBankCode") ) {
			return 114;
		}
		if( strFieldName.equals("GetBankAccNo") ) {
			return 115;
		}
		if( strFieldName.equals("GetAccName") ) {
			return 116;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return 117;
		}
		if( strFieldName.equals("PayRuleCode") ) {
			return 118;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return 119;
		}
		if( strFieldName.equals("AscriptionFlag") ) {
			return 120;
		}
		if( strFieldName.equals("PCValiDate") ) {
			return 121;
		}
		if( strFieldName.equals("AutoPubAccFlag") ) {
			return 122;
		}
		if( strFieldName.equals("RiskSequence") ) {
			return 123;
		}
		if( strFieldName.equals("CancleForegetSpecFlag") ) {
			return 124;
		}
		if( strFieldName.equals("TakeDate") ) {
			return 125;
		}
		if( strFieldName.equals("TakeTime") ) {
			return 126;
		}
		if( strFieldName.equals("AirNo") ) {
			return 127;
		}
		if( strFieldName.equals("TicketNo") ) {
			return 128;
		}
		if( strFieldName.equals("SeatNo") ) {
			return 129;
		}
		if( strFieldName.equals("InputPrem") ) {
			return 130;
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
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "PolNo";
				break;
			case 4:
				strFieldName = "ProposalNo";
				break;
			case 5:
				strFieldName = "PrtNo";
				break;
			case 6:
				strFieldName = "ContType";
				break;
			case 7:
				strFieldName = "PolTypeFlag";
				break;
			case 8:
				strFieldName = "MainPolNo";
				break;
			case 9:
				strFieldName = "MasterPolNo";
				break;
			case 10:
				strFieldName = "KindCode";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "RiskVersion";
				break;
			case 13:
				strFieldName = "ManageCom";
				break;
			case 14:
				strFieldName = "AgentCom";
				break;
			case 15:
				strFieldName = "AgentType";
				break;
			case 16:
				strFieldName = "AgentCode";
				break;
			case 17:
				strFieldName = "AgentGroup";
				break;
			case 18:
				strFieldName = "AgentCode1";
				break;
			case 19:
				strFieldName = "SaleChnl";
				break;
			case 20:
				strFieldName = "Handler";
				break;
			case 21:
				strFieldName = "InsuredNo";
				break;
			case 22:
				strFieldName = "InsuredName";
				break;
			case 23:
				strFieldName = "InsuredSex";
				break;
			case 24:
				strFieldName = "InsuredBirthday";
				break;
			case 25:
				strFieldName = "InsuredAppAge";
				break;
			case 26:
				strFieldName = "InsuredPeoples";
				break;
			case 27:
				strFieldName = "OccupationType";
				break;
			case 28:
				strFieldName = "AppntNo";
				break;
			case 29:
				strFieldName = "AppntName";
				break;
			case 30:
				strFieldName = "CValiDate";
				break;
			case 31:
				strFieldName = "SignCom";
				break;
			case 32:
				strFieldName = "SignDate";
				break;
			case 33:
				strFieldName = "SignTime";
				break;
			case 34:
				strFieldName = "FirstPayDate";
				break;
			case 35:
				strFieldName = "PayEndDate";
				break;
			case 36:
				strFieldName = "PaytoDate";
				break;
			case 37:
				strFieldName = "GetStartDate";
				break;
			case 38:
				strFieldName = "EndDate";
				break;
			case 39:
				strFieldName = "AcciEndDate";
				break;
			case 40:
				strFieldName = "GetYearFlag";
				break;
			case 41:
				strFieldName = "GetYear";
				break;
			case 42:
				strFieldName = "PayEndYearFlag";
				break;
			case 43:
				strFieldName = "PayEndYear";
				break;
			case 44:
				strFieldName = "InsuYearFlag";
				break;
			case 45:
				strFieldName = "InsuYear";
				break;
			case 46:
				strFieldName = "AcciYearFlag";
				break;
			case 47:
				strFieldName = "AcciYear";
				break;
			case 48:
				strFieldName = "GetStartType";
				break;
			case 49:
				strFieldName = "SpecifyValiDate";
				break;
			case 50:
				strFieldName = "PayMode";
				break;
			case 51:
				strFieldName = "PayLocation";
				break;
			case 52:
				strFieldName = "PayIntv";
				break;
			case 53:
				strFieldName = "PayYears";
				break;
			case 54:
				strFieldName = "Years";
				break;
			case 55:
				strFieldName = "ManageFeeRate";
				break;
			case 56:
				strFieldName = "FloatRate";
				break;
			case 57:
				strFieldName = "PremToAmnt";
				break;
			case 58:
				strFieldName = "Mult";
				break;
			case 59:
				strFieldName = "StandPrem";
				break;
			case 60:
				strFieldName = "Prem";
				break;
			case 61:
				strFieldName = "SumPrem";
				break;
			case 62:
				strFieldName = "Amnt";
				break;
			case 63:
				strFieldName = "RiskAmnt";
				break;
			case 64:
				strFieldName = "LeavingMoney";
				break;
			case 65:
				strFieldName = "EndorseTimes";
				break;
			case 66:
				strFieldName = "ClaimTimes";
				break;
			case 67:
				strFieldName = "LiveTimes";
				break;
			case 68:
				strFieldName = "RenewCount";
				break;
			case 69:
				strFieldName = "LastGetDate";
				break;
			case 70:
				strFieldName = "LastLoanDate";
				break;
			case 71:
				strFieldName = "LastRegetDate";
				break;
			case 72:
				strFieldName = "LastEdorDate";
				break;
			case 73:
				strFieldName = "LastRevDate";
				break;
			case 74:
				strFieldName = "RnewFlag";
				break;
			case 75:
				strFieldName = "StopFlag";
				break;
			case 76:
				strFieldName = "ExpiryFlag";
				break;
			case 77:
				strFieldName = "AutoPayFlag";
				break;
			case 78:
				strFieldName = "InterestDifFlag";
				break;
			case 79:
				strFieldName = "SubFlag";
				break;
			case 80:
				strFieldName = "BnfFlag";
				break;
			case 81:
				strFieldName = "HealthCheckFlag";
				break;
			case 82:
				strFieldName = "ImpartFlag";
				break;
			case 83:
				strFieldName = "ReinsureFlag";
				break;
			case 84:
				strFieldName = "AgentPayFlag";
				break;
			case 85:
				strFieldName = "AgentGetFlag";
				break;
			case 86:
				strFieldName = "LiveGetMode";
				break;
			case 87:
				strFieldName = "DeadGetMode";
				break;
			case 88:
				strFieldName = "BonusGetMode";
				break;
			case 89:
				strFieldName = "BonusMan";
				break;
			case 90:
				strFieldName = "DeadFlag";
				break;
			case 91:
				strFieldName = "SmokeFlag";
				break;
			case 92:
				strFieldName = "Remark";
				break;
			case 93:
				strFieldName = "ApproveFlag";
				break;
			case 94:
				strFieldName = "ApproveCode";
				break;
			case 95:
				strFieldName = "ApproveDate";
				break;
			case 96:
				strFieldName = "ApproveTime";
				break;
			case 97:
				strFieldName = "UWFlag";
				break;
			case 98:
				strFieldName = "UWCode";
				break;
			case 99:
				strFieldName = "UWDate";
				break;
			case 100:
				strFieldName = "UWTime";
				break;
			case 101:
				strFieldName = "PolApplyDate";
				break;
			case 102:
				strFieldName = "AppFlag";
				break;
			case 103:
				strFieldName = "PolState";
				break;
			case 104:
				strFieldName = "StandbyFlag1";
				break;
			case 105:
				strFieldName = "StandbyFlag2";
				break;
			case 106:
				strFieldName = "StandbyFlag3";
				break;
			case 107:
				strFieldName = "Operator";
				break;
			case 108:
				strFieldName = "MakeDate";
				break;
			case 109:
				strFieldName = "MakeTime";
				break;
			case 110:
				strFieldName = "ModifyDate";
				break;
			case 111:
				strFieldName = "ModifyTime";
				break;
			case 112:
				strFieldName = "WaitPeriod";
				break;
			case 113:
				strFieldName = "GetForm";
				break;
			case 114:
				strFieldName = "GetBankCode";
				break;
			case 115:
				strFieldName = "GetBankAccNo";
				break;
			case 116:
				strFieldName = "GetAccName";
				break;
			case 117:
				strFieldName = "KeepValueOpt";
				break;
			case 118:
				strFieldName = "PayRuleCode";
				break;
			case 119:
				strFieldName = "AscriptionRuleCode";
				break;
			case 120:
				strFieldName = "AscriptionFlag";
				break;
			case 121:
				strFieldName = "PCValiDate";
				break;
			case 122:
				strFieldName = "AutoPubAccFlag";
				break;
			case 123:
				strFieldName = "RiskSequence";
				break;
			case 124:
				strFieldName = "CancleForegetSpecFlag";
				break;
			case 125:
				strFieldName = "TakeDate";
				break;
			case 126:
				strFieldName = "TakeTime";
				break;
			case 127:
				strFieldName = "AirNo";
				break;
			case 128:
				strFieldName = "TicketNo";
				break;
			case 129:
				strFieldName = "SeatNo";
				break;
			case 130:
				strFieldName = "InputPrem";
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetStartType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecifyValiDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLocation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FloatRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndorseTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClaimTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LiveTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RenewCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastRegetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StopFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthCheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
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
		if( strFieldName.equals("WaitPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetForm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptionFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PCValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AutoPubAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskSequence") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CancleForegetSpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AirNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TicketNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SeatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputPrem") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_INT;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_INT;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_INT;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_INT;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_INT;
				break;
			case 53:
				nFieldType = Schema.TYPE_INT;
				break;
			case 54:
				nFieldType = Schema.TYPE_INT;
				break;
			case 55:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 59:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 60:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 61:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 62:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 63:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 64:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 65:
				nFieldType = Schema.TYPE_INT;
				break;
			case 66:
				nFieldType = Schema.TYPE_INT;
				break;
			case 67:
				nFieldType = Schema.TYPE_INT;
				break;
			case 68:
				nFieldType = Schema.TYPE_INT;
				break;
			case 69:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 70:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 71:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 73:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 74:
				nFieldType = Schema.TYPE_INT;
				break;
			case 75:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 89:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 95:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 96:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 97:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 98:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 99:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 106:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 107:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 108:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 109:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 110:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 111:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 112:
				nFieldType = Schema.TYPE_INT;
				break;
			case 113:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 114:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 115:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 116:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 117:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 118:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 119:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 120:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 121:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 122:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 123:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 124:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 125:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 126:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 127:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 128:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 129:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 130:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
