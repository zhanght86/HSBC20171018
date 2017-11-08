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
import com.sinosoft.lis.db.LBPOPolBDB;

/*
 * <p>ClassName: LBPOPolBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LBPOPolBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOPolBSchema.class);

	// @Field
	/** 转储号 */
	private String IDX;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 录入次数 */
	private int InputNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 添空号 */
	private String FillNo;
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
	/** 被保人数目 */
	private String InsuredPeoples;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 险种生效日期 */
	private String CValiDate;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private String SignDate;
	/** 签单时间 */
	private String SignTime;
	/** 首期交费日期 */
	private String FirstPayDate;
	/** 终交日期 */
	private String PayEndDate;
	/** 交至日期 */
	private String PaytoDate;
	/** 起领日期 */
	private String GetStartDate;
	/** 保险责任终止日期 */
	private String EndDate;
	/** 意外责任终止日期 */
	private String AcciEndDate;
	/** 领取年龄年期标志 */
	private String GetYearFlag;
	/** 领取年龄年期 */
	private String GetYear;
	/** 终交年龄年期标志 */
	private String PayEndYearFlag;
	/** 终交年龄年期 */
	private String PayEndYear;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 保险年龄年期 */
	private String InsuYear;
	/** 意外年龄年期标志 */
	private String AcciYearFlag;
	/** 意外年龄年期 */
	private String AcciYear;
	/** 起领日期计算类型 */
	private String GetStartType;
	/** 是否指定生效日期 */
	private String SpecifyValiDate;
	/** 交费方式 */
	private String PayMode;
	/** 交费位置 */
	private String PayLocation;
	/** 交费间隔 */
	private String PayIntv;
	/** 交费年期 */
	private String PayYears;
	/** 保险年期 */
	private String Years;
	/** 管理费比例 */
	private String ManageFeeRate;
	/** 浮动费率 */
	private double FloatRate;
	/** 保费算保额标志 */
	private String PremToAmnt;
	/** 总份数 */
	private String Mult;
	/** 总标准保费 */
	private String StandPrem;
	/** 总保费 */
	private String Prem;
	/** 总累计保费 */
	private String SumPrem;
	/** 总基本保额 */
	private String Amnt;
	/** 总风险保额 */
	private String RiskAmnt;
	/** 余额 */
	private String LeavingMoney;
	/** 批改次数 */
	private String EndorseTimes;
	/** 理赔次数 */
	private String ClaimTimes;
	/** 生存领取次数 */
	private String LiveTimes;
	/** 续保次数 */
	private String RenewCount;
	/** 最后一次给付日期 */
	private String LastGetDate;
	/** 最后一次借款日期 */
	private String LastLoanDate;
	/** 最后一次催收日期 */
	private String LastRegetDate;
	/** 最后一次保全日期 */
	private String LastEdorDate;
	/** 最近复效日期 */
	private String LastRevDate;
	/** 续保标志 */
	private String RnewFlag;
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
	/** 生存金处理方式 */
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
	private String ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 核保状态 */
	private String UWFlag;
	/** 最终核保人编码 */
	private String UWCode;
	/** 核保完成日期 */
	private String UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单申请日期 */
	private String PolApplyDate;
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
	private String MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private String ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 等待期 */
	private String WaitPeriod;
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
	/** 自动应用团体帐户标记 */
	private String AutoPubAccFlag;
	/** 主险险种顺序号 */
	private String RiskSequence;
	/** 领取期限 */
	private String GetLimit;
	/** 转储日期 */
	private Date TransDate;
	/** 转储时间 */
	private String TransTime;
	/** 转储操作员 */
	private String TransOperator;

	public static final int FIELDNUM = 123;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOPolBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "IDX";
		pk[1] = "InputNo";
		pk[2] = "PolNo";
		pk[3] = "FillNo";

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
		LBPOPolBSchema cloned = (LBPOPolBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getIDX()
	{
		return IDX;
	}
	public void setIDX(String aIDX)
	{
		IDX = aIDX;
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
	public int getInputNo()
	{
		return InputNo;
	}
	public void setInputNo(int aInputNo)
	{
		InputNo = aInputNo;
	}
	public void setInputNo(String aInputNo)
	{
		if (aInputNo != null && !aInputNo.equals(""))
		{
			Integer tInteger = new Integer(aInputNo);
			int i = tInteger.intValue();
			InputNo = i;
		}
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getFillNo()
	{
		return FillNo;
	}
	public void setFillNo(String aFillNo)
	{
		FillNo = aFillNo;
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
	public String getInsuredPeoples()
	{
		return InsuredPeoples;
	}
	public void setInsuredPeoples(String aInsuredPeoples)
	{
		InsuredPeoples = aInsuredPeoples;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getCValiDate()
	{
		return CValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		CValiDate = aCValiDate;
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
		return SignDate;
	}
	public void setSignDate(String aSignDate)
	{
		SignDate = aSignDate;
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
		return FirstPayDate;
	}
	public void setFirstPayDate(String aFirstPayDate)
	{
		FirstPayDate = aFirstPayDate;
	}
	/**
	* 为责任表中最晚的终交日期
	*/
	public String getPayEndDate()
	{
		return PayEndDate;
	}
	public void setPayEndDate(String aPayEndDate)
	{
		PayEndDate = aPayEndDate;
	}
	public String getPaytoDate()
	{
		return PaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	/**
	* 为责任表中最早的起领日期
	*/
	public String getGetStartDate()
	{
		return GetStartDate;
	}
	public void setGetStartDate(String aGetStartDate)
	{
		GetStartDate = aGetStartDate;
	}
	/**
	* 为责任表中最晚的终止日期
	*/
	public String getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(String aEndDate)
	{
		EndDate = aEndDate;
	}
	/**
	* 为责任表中最晚的意外责任终止日期
	*/
	public String getAcciEndDate()
	{
		return AcciEndDate;
	}
	public void setAcciEndDate(String aAcciEndDate)
	{
		AcciEndDate = aAcciEndDate;
	}
	public String getGetYearFlag()
	{
		return GetYearFlag;
	}
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
	}
	public String getGetYear()
	{
		return GetYear;
	}
	public void setGetYear(String aGetYear)
	{
		GetYear = aGetYear;
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
	public String getPayEndYear()
	{
		return PayEndYear;
	}
	public void setPayEndYear(String aPayEndYear)
	{
		PayEndYear = aPayEndYear;
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
	public String getInsuYear()
	{
		return InsuYear;
	}
	public void setInsuYear(String aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	public String getAcciYearFlag()
	{
		return AcciYearFlag;
	}
	public void setAcciYearFlag(String aAcciYearFlag)
	{
		AcciYearFlag = aAcciYearFlag;
	}
	public String getAcciYear()
	{
		return AcciYear;
	}
	public void setAcciYear(String aAcciYear)
	{
		AcciYear = aAcciYear;
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
	public String getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	/**
	* 对于终交年期标志为“年”：  表示需要交费的年数。<p>
	* 对于终交年期标志为“月”：  表示需要交费的月数<p>
	* 对于终交年期标志为“日”：  表示需要交费的天数<p>
	* 对于终交年期标志为“年龄”：该字段存放将根据年龄折算成的需要交费的年数。
	*/
	public String getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(String aPayYears)
	{
		PayYears = aPayYears;
	}
	/**
	* 保险责任区间
	*/
	public String getYears()
	{
		return Years;
	}
	public void setYears(String aYears)
	{
		Years = aYears;
	}
	public String getManageFeeRate()
	{
		return ManageFeeRate;
	}
	public void setManageFeeRate(String aManageFeeRate)
	{
		ManageFeeRate = aManageFeeRate;
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
	public String getMult()
	{
		return Mult;
	}
	public void setMult(String aMult)
	{
		Mult = aMult;
	}
	public String getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public String getPrem()
	{
		return Prem;
	}
	public void setPrem(String aPrem)
	{
		Prem = aPrem;
	}
	public String getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public String getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(String aAmnt)
	{
		Amnt = aAmnt;
	}
	public String getRiskAmnt()
	{
		return RiskAmnt;
	}
	public void setRiskAmnt(String aRiskAmnt)
	{
		RiskAmnt = aRiskAmnt;
	}
	public String getLeavingMoney()
	{
		return LeavingMoney;
	}
	public void setLeavingMoney(String aLeavingMoney)
	{
		LeavingMoney = aLeavingMoney;
	}
	public String getEndorseTimes()
	{
		return EndorseTimes;
	}
	public void setEndorseTimes(String aEndorseTimes)
	{
		EndorseTimes = aEndorseTimes;
	}
	/**
	* 该字段记录立案的次数。只要立案了就认为进行理赔了，在该字段上加1。
	*/
	public String getClaimTimes()
	{
		return ClaimTimes;
	}
	public void setClaimTimes(String aClaimTimes)
	{
		ClaimTimes = aClaimTimes;
	}
	public String getLiveTimes()
	{
		return LiveTimes;
	}
	public void setLiveTimes(String aLiveTimes)
	{
		LiveTimes = aLiveTimes;
	}
	/**
	* n --续保n次,n>0
	*/
	public String getRenewCount()
	{
		return RenewCount;
	}
	public void setRenewCount(String aRenewCount)
	{
		RenewCount = aRenewCount;
	}
	public String getLastGetDate()
	{
		return LastGetDate;
	}
	public void setLastGetDate(String aLastGetDate)
	{
		LastGetDate = aLastGetDate;
	}
	public String getLastLoanDate()
	{
		return LastLoanDate;
	}
	public void setLastLoanDate(String aLastLoanDate)
	{
		LastLoanDate = aLastLoanDate;
	}
	public String getLastRegetDate()
	{
		return LastRegetDate;
	}
	public void setLastRegetDate(String aLastRegetDate)
	{
		LastRegetDate = aLastRegetDate;
	}
	public String getLastEdorDate()
	{
		return LastEdorDate;
	}
	public void setLastEdorDate(String aLastEdorDate)
	{
		LastEdorDate = aLastEdorDate;
	}
	public String getLastRevDate()
	{
		return LastRevDate;
	}
	public void setLastRevDate(String aLastRevDate)
	{
		LastRevDate = aLastRevDate;
	}
	/**
	* -2 -- 非续保<p>
	* -1 -- 自动续保<p>
	* 0 -- 人工续保
	*/
	public String getRnewFlag()
	{
		return RnewFlag;
	}
	public void setRnewFlag(String aRnewFlag)
	{
		RnewFlag = aRnewFlag;
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
	/**
	* 暂用 GetYears 领取期间
	*/
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
		return ApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		ApproveDate = aApproveDate;
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
		return UWDate;
	}
	public void setUWDate(String aUWDate)
	{
		UWDate = aUWDate;
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
		return PolApplyDate;
	}
	public void setPolApplyDate(String aPolApplyDate)
	{
		PolApplyDate = aPolApplyDate;
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
	* 对于险种编码：311603，存放的是同心卡的开卡日期<p>
	* <p>
	* <p>
	* <p>
	* 被用作记录按档次卖的险种的档次
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
	*<p>
	* <p>
	* <p>
	* 被用作记录职业加费
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
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* <p>
	* 被用作记录年金领取方式
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
		return MakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		MakeDate = aMakeDate;
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
		return ModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	public String getWaitPeriod()
	{
		return WaitPeriod;
	}
	public void setWaitPeriod(String aWaitPeriod)
	{
		WaitPeriod = aWaitPeriod;
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
	public String getGetLimit()
	{
		return GetLimit;
	}
	public void setGetLimit(String aGetLimit)
	{
		GetLimit = aGetLimit;
	}
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	public String getTransTime()
	{
		return TransTime;
	}
	public void setTransTime(String aTransTime)
	{
		TransTime = aTransTime;
	}
	public String getTransOperator()
	{
		return TransOperator;
	}
	public void setTransOperator(String aTransOperator)
	{
		TransOperator = aTransOperator;
	}

	/**
	* 使用另外一个 LBPOPolBSchema 对象给 Schema 赋值
	* @param: aLBPOPolBSchema LBPOPolBSchema
	**/
	public void setSchema(LBPOPolBSchema aLBPOPolBSchema)
	{
		this.IDX = aLBPOPolBSchema.getIDX();
		this.GrpContNo = aLBPOPolBSchema.getGrpContNo();
		this.GrpPolNo = aLBPOPolBSchema.getGrpPolNo();
		this.ContNo = aLBPOPolBSchema.getContNo();
		this.InputNo = aLBPOPolBSchema.getInputNo();
		this.PolNo = aLBPOPolBSchema.getPolNo();
		this.FillNo = aLBPOPolBSchema.getFillNo();
		this.ProposalNo = aLBPOPolBSchema.getProposalNo();
		this.PrtNo = aLBPOPolBSchema.getPrtNo();
		this.ContType = aLBPOPolBSchema.getContType();
		this.PolTypeFlag = aLBPOPolBSchema.getPolTypeFlag();
		this.MainPolNo = aLBPOPolBSchema.getMainPolNo();
		this.MasterPolNo = aLBPOPolBSchema.getMasterPolNo();
		this.KindCode = aLBPOPolBSchema.getKindCode();
		this.RiskCode = aLBPOPolBSchema.getRiskCode();
		this.RiskVersion = aLBPOPolBSchema.getRiskVersion();
		this.ManageCom = aLBPOPolBSchema.getManageCom();
		this.AgentCom = aLBPOPolBSchema.getAgentCom();
		this.AgentType = aLBPOPolBSchema.getAgentType();
		this.AgentCode = aLBPOPolBSchema.getAgentCode();
		this.AgentGroup = aLBPOPolBSchema.getAgentGroup();
		this.AgentCode1 = aLBPOPolBSchema.getAgentCode1();
		this.SaleChnl = aLBPOPolBSchema.getSaleChnl();
		this.Handler = aLBPOPolBSchema.getHandler();
		this.InsuredNo = aLBPOPolBSchema.getInsuredNo();
		this.InsuredPeoples = aLBPOPolBSchema.getInsuredPeoples();
		this.AppntNo = aLBPOPolBSchema.getAppntNo();
		this.CValiDate = aLBPOPolBSchema.getCValiDate();
		this.SignCom = aLBPOPolBSchema.getSignCom();
		this.SignDate = aLBPOPolBSchema.getSignDate();
		this.SignTime = aLBPOPolBSchema.getSignTime();
		this.FirstPayDate = aLBPOPolBSchema.getFirstPayDate();
		this.PayEndDate = aLBPOPolBSchema.getPayEndDate();
		this.PaytoDate = aLBPOPolBSchema.getPaytoDate();
		this.GetStartDate = aLBPOPolBSchema.getGetStartDate();
		this.EndDate = aLBPOPolBSchema.getEndDate();
		this.AcciEndDate = aLBPOPolBSchema.getAcciEndDate();
		this.GetYearFlag = aLBPOPolBSchema.getGetYearFlag();
		this.GetYear = aLBPOPolBSchema.getGetYear();
		this.PayEndYearFlag = aLBPOPolBSchema.getPayEndYearFlag();
		this.PayEndYear = aLBPOPolBSchema.getPayEndYear();
		this.InsuYearFlag = aLBPOPolBSchema.getInsuYearFlag();
		this.InsuYear = aLBPOPolBSchema.getInsuYear();
		this.AcciYearFlag = aLBPOPolBSchema.getAcciYearFlag();
		this.AcciYear = aLBPOPolBSchema.getAcciYear();
		this.GetStartType = aLBPOPolBSchema.getGetStartType();
		this.SpecifyValiDate = aLBPOPolBSchema.getSpecifyValiDate();
		this.PayMode = aLBPOPolBSchema.getPayMode();
		this.PayLocation = aLBPOPolBSchema.getPayLocation();
		this.PayIntv = aLBPOPolBSchema.getPayIntv();
		this.PayYears = aLBPOPolBSchema.getPayYears();
		this.Years = aLBPOPolBSchema.getYears();
		this.ManageFeeRate = aLBPOPolBSchema.getManageFeeRate();
		this.FloatRate = aLBPOPolBSchema.getFloatRate();
		this.PremToAmnt = aLBPOPolBSchema.getPremToAmnt();
		this.Mult = aLBPOPolBSchema.getMult();
		this.StandPrem = aLBPOPolBSchema.getStandPrem();
		this.Prem = aLBPOPolBSchema.getPrem();
		this.SumPrem = aLBPOPolBSchema.getSumPrem();
		this.Amnt = aLBPOPolBSchema.getAmnt();
		this.RiskAmnt = aLBPOPolBSchema.getRiskAmnt();
		this.LeavingMoney = aLBPOPolBSchema.getLeavingMoney();
		this.EndorseTimes = aLBPOPolBSchema.getEndorseTimes();
		this.ClaimTimes = aLBPOPolBSchema.getClaimTimes();
		this.LiveTimes = aLBPOPolBSchema.getLiveTimes();
		this.RenewCount = aLBPOPolBSchema.getRenewCount();
		this.LastGetDate = aLBPOPolBSchema.getLastGetDate();
		this.LastLoanDate = aLBPOPolBSchema.getLastLoanDate();
		this.LastRegetDate = aLBPOPolBSchema.getLastRegetDate();
		this.LastEdorDate = aLBPOPolBSchema.getLastEdorDate();
		this.LastRevDate = aLBPOPolBSchema.getLastRevDate();
		this.RnewFlag = aLBPOPolBSchema.getRnewFlag();
		this.StopFlag = aLBPOPolBSchema.getStopFlag();
		this.ExpiryFlag = aLBPOPolBSchema.getExpiryFlag();
		this.AutoPayFlag = aLBPOPolBSchema.getAutoPayFlag();
		this.InterestDifFlag = aLBPOPolBSchema.getInterestDifFlag();
		this.SubFlag = aLBPOPolBSchema.getSubFlag();
		this.BnfFlag = aLBPOPolBSchema.getBnfFlag();
		this.HealthCheckFlag = aLBPOPolBSchema.getHealthCheckFlag();
		this.ImpartFlag = aLBPOPolBSchema.getImpartFlag();
		this.ReinsureFlag = aLBPOPolBSchema.getReinsureFlag();
		this.AgentPayFlag = aLBPOPolBSchema.getAgentPayFlag();
		this.AgentGetFlag = aLBPOPolBSchema.getAgentGetFlag();
		this.LiveGetMode = aLBPOPolBSchema.getLiveGetMode();
		this.DeadGetMode = aLBPOPolBSchema.getDeadGetMode();
		this.BonusGetMode = aLBPOPolBSchema.getBonusGetMode();
		this.BonusMan = aLBPOPolBSchema.getBonusMan();
		this.DeadFlag = aLBPOPolBSchema.getDeadFlag();
		this.SmokeFlag = aLBPOPolBSchema.getSmokeFlag();
		this.Remark = aLBPOPolBSchema.getRemark();
		this.ApproveFlag = aLBPOPolBSchema.getApproveFlag();
		this.ApproveCode = aLBPOPolBSchema.getApproveCode();
		this.ApproveDate = aLBPOPolBSchema.getApproveDate();
		this.ApproveTime = aLBPOPolBSchema.getApproveTime();
		this.UWFlag = aLBPOPolBSchema.getUWFlag();
		this.UWCode = aLBPOPolBSchema.getUWCode();
		this.UWDate = aLBPOPolBSchema.getUWDate();
		this.UWTime = aLBPOPolBSchema.getUWTime();
		this.PolApplyDate = aLBPOPolBSchema.getPolApplyDate();
		this.AppFlag = aLBPOPolBSchema.getAppFlag();
		this.PolState = aLBPOPolBSchema.getPolState();
		this.StandbyFlag1 = aLBPOPolBSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLBPOPolBSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLBPOPolBSchema.getStandbyFlag3();
		this.Operator = aLBPOPolBSchema.getOperator();
		this.MakeDate = aLBPOPolBSchema.getMakeDate();
		this.MakeTime = aLBPOPolBSchema.getMakeTime();
		this.ModifyDate = aLBPOPolBSchema.getModifyDate();
		this.ModifyTime = aLBPOPolBSchema.getModifyTime();
		this.WaitPeriod = aLBPOPolBSchema.getWaitPeriod();
		this.GetForm = aLBPOPolBSchema.getGetForm();
		this.GetBankCode = aLBPOPolBSchema.getGetBankCode();
		this.GetBankAccNo = aLBPOPolBSchema.getGetBankAccNo();
		this.GetAccName = aLBPOPolBSchema.getGetAccName();
		this.KeepValueOpt = aLBPOPolBSchema.getKeepValueOpt();
		this.PayRuleCode = aLBPOPolBSchema.getPayRuleCode();
		this.AscriptionRuleCode = aLBPOPolBSchema.getAscriptionRuleCode();
		this.AutoPubAccFlag = aLBPOPolBSchema.getAutoPubAccFlag();
		this.RiskSequence = aLBPOPolBSchema.getRiskSequence();
		this.GetLimit = aLBPOPolBSchema.getGetLimit();
		this.TransDate = fDate.getDate( aLBPOPolBSchema.getTransDate());
		this.TransTime = aLBPOPolBSchema.getTransTime();
		this.TransOperator = aLBPOPolBSchema.getTransOperator();
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
			if( rs.getString("IDX") == null )
				this.IDX = null;
			else
				this.IDX = rs.getString("IDX").trim();

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

			this.InputNo = rs.getInt("InputNo");
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("FillNo") == null )
				this.FillNo = null;
			else
				this.FillNo = rs.getString("FillNo").trim();

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

			if( rs.getString("InsuredPeoples") == null )
				this.InsuredPeoples = null;
			else
				this.InsuredPeoples = rs.getString("InsuredPeoples").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("CValiDate") == null )
				this.CValiDate = null;
			else
				this.CValiDate = rs.getString("CValiDate").trim();

			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			if( rs.getString("SignDate") == null )
				this.SignDate = null;
			else
				this.SignDate = rs.getString("SignDate").trim();

			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			if( rs.getString("FirstPayDate") == null )
				this.FirstPayDate = null;
			else
				this.FirstPayDate = rs.getString("FirstPayDate").trim();

			if( rs.getString("PayEndDate") == null )
				this.PayEndDate = null;
			else
				this.PayEndDate = rs.getString("PayEndDate").trim();

			if( rs.getString("PaytoDate") == null )
				this.PaytoDate = null;
			else
				this.PaytoDate = rs.getString("PaytoDate").trim();

			if( rs.getString("GetStartDate") == null )
				this.GetStartDate = null;
			else
				this.GetStartDate = rs.getString("GetStartDate").trim();

			if( rs.getString("EndDate") == null )
				this.EndDate = null;
			else
				this.EndDate = rs.getString("EndDate").trim();

			if( rs.getString("AcciEndDate") == null )
				this.AcciEndDate = null;
			else
				this.AcciEndDate = rs.getString("AcciEndDate").trim();

			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			if( rs.getString("GetYear") == null )
				this.GetYear = null;
			else
				this.GetYear = rs.getString("GetYear").trim();

			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			if( rs.getString("PayEndYear") == null )
				this.PayEndYear = null;
			else
				this.PayEndYear = rs.getString("PayEndYear").trim();

			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			if( rs.getString("InsuYear") == null )
				this.InsuYear = null;
			else
				this.InsuYear = rs.getString("InsuYear").trim();

			if( rs.getString("AcciYearFlag") == null )
				this.AcciYearFlag = null;
			else
				this.AcciYearFlag = rs.getString("AcciYearFlag").trim();

			if( rs.getString("AcciYear") == null )
				this.AcciYear = null;
			else
				this.AcciYear = rs.getString("AcciYear").trim();

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

			if( rs.getString("PayIntv") == null )
				this.PayIntv = null;
			else
				this.PayIntv = rs.getString("PayIntv").trim();

			if( rs.getString("PayYears") == null )
				this.PayYears = null;
			else
				this.PayYears = rs.getString("PayYears").trim();

			if( rs.getString("Years") == null )
				this.Years = null;
			else
				this.Years = rs.getString("Years").trim();

			if( rs.getString("ManageFeeRate") == null )
				this.ManageFeeRate = null;
			else
				this.ManageFeeRate = rs.getString("ManageFeeRate").trim();

			this.FloatRate = rs.getDouble("FloatRate");
			if( rs.getString("PremToAmnt") == null )
				this.PremToAmnt = null;
			else
				this.PremToAmnt = rs.getString("PremToAmnt").trim();

			if( rs.getString("Mult") == null )
				this.Mult = null;
			else
				this.Mult = rs.getString("Mult").trim();

			if( rs.getString("StandPrem") == null )
				this.StandPrem = null;
			else
				this.StandPrem = rs.getString("StandPrem").trim();

			if( rs.getString("Prem") == null )
				this.Prem = null;
			else
				this.Prem = rs.getString("Prem").trim();

			if( rs.getString("SumPrem") == null )
				this.SumPrem = null;
			else
				this.SumPrem = rs.getString("SumPrem").trim();

			if( rs.getString("Amnt") == null )
				this.Amnt = null;
			else
				this.Amnt = rs.getString("Amnt").trim();

			if( rs.getString("RiskAmnt") == null )
				this.RiskAmnt = null;
			else
				this.RiskAmnt = rs.getString("RiskAmnt").trim();

			if( rs.getString("LeavingMoney") == null )
				this.LeavingMoney = null;
			else
				this.LeavingMoney = rs.getString("LeavingMoney").trim();

			if( rs.getString("EndorseTimes") == null )
				this.EndorseTimes = null;
			else
				this.EndorseTimes = rs.getString("EndorseTimes").trim();

			if( rs.getString("ClaimTimes") == null )
				this.ClaimTimes = null;
			else
				this.ClaimTimes = rs.getString("ClaimTimes").trim();

			if( rs.getString("LiveTimes") == null )
				this.LiveTimes = null;
			else
				this.LiveTimes = rs.getString("LiveTimes").trim();

			if( rs.getString("RenewCount") == null )
				this.RenewCount = null;
			else
				this.RenewCount = rs.getString("RenewCount").trim();

			if( rs.getString("LastGetDate") == null )
				this.LastGetDate = null;
			else
				this.LastGetDate = rs.getString("LastGetDate").trim();

			if( rs.getString("LastLoanDate") == null )
				this.LastLoanDate = null;
			else
				this.LastLoanDate = rs.getString("LastLoanDate").trim();

			if( rs.getString("LastRegetDate") == null )
				this.LastRegetDate = null;
			else
				this.LastRegetDate = rs.getString("LastRegetDate").trim();

			if( rs.getString("LastEdorDate") == null )
				this.LastEdorDate = null;
			else
				this.LastEdorDate = rs.getString("LastEdorDate").trim();

			if( rs.getString("LastRevDate") == null )
				this.LastRevDate = null;
			else
				this.LastRevDate = rs.getString("LastRevDate").trim();

			if( rs.getString("RnewFlag") == null )
				this.RnewFlag = null;
			else
				this.RnewFlag = rs.getString("RnewFlag").trim();

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

			if( rs.getString("ApproveDate") == null )
				this.ApproveDate = null;
			else
				this.ApproveDate = rs.getString("ApproveDate").trim();

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

			if( rs.getString("UWDate") == null )
				this.UWDate = null;
			else
				this.UWDate = rs.getString("UWDate").trim();

			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("PolApplyDate") == null )
				this.PolApplyDate = null;
			else
				this.PolApplyDate = rs.getString("PolApplyDate").trim();

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

			if( rs.getString("MakeDate") == null )
				this.MakeDate = null;
			else
				this.MakeDate = rs.getString("MakeDate").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyDate") == null )
				this.ModifyDate = null;
			else
				this.ModifyDate = rs.getString("ModifyDate").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("WaitPeriod") == null )
				this.WaitPeriod = null;
			else
				this.WaitPeriod = rs.getString("WaitPeriod").trim();

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

			if( rs.getString("AutoPubAccFlag") == null )
				this.AutoPubAccFlag = null;
			else
				this.AutoPubAccFlag = rs.getString("AutoPubAccFlag").trim();

			if( rs.getString("RiskSequence") == null )
				this.RiskSequence = null;
			else
				this.RiskSequence = rs.getString("RiskSequence").trim();

			if( rs.getString("GetLimit") == null )
				this.GetLimit = null;
			else
				this.GetLimit = rs.getString("GetLimit").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("TransTime") == null )
				this.TransTime = null;
			else
				this.TransTime = rs.getString("TransTime").trim();

			if( rs.getString("TransOperator") == null )
				this.TransOperator = null;
			else
				this.TransOperator = rs.getString("TransOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPOPolB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOPolBSchema getSchema()
	{
		LBPOPolBSchema aLBPOPolBSchema = new LBPOPolBSchema();
		aLBPOPolBSchema.setSchema(this);
		return aLBPOPolBSchema;
	}

	public LBPOPolBDB getDB()
	{
		LBPOPolBDB aDBOper = new LBPOPolBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOPolB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(IDX)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InputNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FillNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(InsuredPeoples)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CValiDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstPayDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PaytoDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciEndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecifyValiDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayYears)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Years)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageFeeRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FloatRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremToAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SumPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Amnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LeavingMoney)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndorseTimes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimTimes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveTimes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewCount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastGetDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastLoanDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastRegetDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastEdorDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastRevDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RnewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ApproveDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolApplyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WaitPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetForm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeepValueOpt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPubAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskSequence)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetLimit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOPolB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			IDX = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InputNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PolTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MasterPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InsuredPeoples = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			CValiDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			SignDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			FirstPayDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PayEndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			PaytoDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			GetStartDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			EndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			AcciEndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GetYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			PayEndYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			InsuYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			AcciYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			AcciYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			GetStartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			SpecifyValiDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			PayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			PayYears = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			Years = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			ManageFeeRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			FloatRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).doubleValue();
			PremToAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			Mult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			StandPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			Prem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			SumPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			Amnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			RiskAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			LeavingMoney = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			EndorseTimes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			ClaimTimes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			LiveTimes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			RenewCount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			LastGetDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			LastLoanDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			LastRegetDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			LastEdorDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			LastRevDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			RnewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			StopFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			ExpiryFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			InterestDifFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			SubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			BnfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			HealthCheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			ImpartFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			ReinsureFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			AgentPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			AgentGetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			LiveGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			DeadGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			BonusGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			BonusMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			DeadFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			ApproveDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			UWDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			PolApplyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			MakeDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			ModifyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
			WaitPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
			GetForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111, SysConst.PACKAGESPILTER );
			GetBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112, SysConst.PACKAGESPILTER );
			GetBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 113, SysConst.PACKAGESPILTER );
			GetAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114, SysConst.PACKAGESPILTER );
			KeepValueOpt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			PayRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 116, SysConst.PACKAGESPILTER );
			AscriptionRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 117, SysConst.PACKAGESPILTER );
			AutoPubAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 118, SysConst.PACKAGESPILTER );
			RiskSequence = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 119, SysConst.PACKAGESPILTER );
			GetLimit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 120, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 121,SysConst.PACKAGESPILTER));
			TransTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 122, SysConst.PACKAGESPILTER );
			TransOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 123, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolBSchema";
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
		if (FCode.equalsIgnoreCase("IDX"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDX));
		}
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
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FillNo));
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
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredPeoples));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CValiDate));
		}
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignCom));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDate));
		}
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignTime));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstPayDate));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDate));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PaytoDate));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartDate));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDate));
		}
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciEndDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastGetDate));
		}
		if (FCode.equalsIgnoreCase("LastLoanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastLoanDate));
		}
		if (FCode.equalsIgnoreCase("LastRegetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastRegetDate));
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastEdorDate));
		}
		if (FCode.equalsIgnoreCase("LastRevDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastRevDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWDate));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolApplyDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
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
		if (FCode.equalsIgnoreCase("AutoPubAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPubAccFlag));
		}
		if (FCode.equalsIgnoreCase("RiskSequence"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSequence));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransTime));
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransOperator));
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
				strFieldValue = StrTool.GBKToUnicode(IDX);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = String.valueOf(InputNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FillNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PolTypeFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MasterPolNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InsuredPeoples);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CValiDate);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SignDate);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(FirstPayDate);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PayEndDate);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(PaytoDate);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(GetStartDate);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(EndDate);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(AcciEndDate);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(GetYear);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(PayEndYear);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(InsuYear);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AcciYearFlag);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(AcciYear);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(GetStartType);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(SpecifyValiDate);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(PayIntv);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(PayYears);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(Years);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(ManageFeeRate);
				break;
			case 53:
				strFieldValue = String.valueOf(FloatRate);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(PremToAmnt);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(Mult);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(StandPrem);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(Prem);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(SumPrem);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(Amnt);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(RiskAmnt);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(LeavingMoney);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(EndorseTimes);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(ClaimTimes);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(LiveTimes);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(RenewCount);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(LastGetDate);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(LastLoanDate);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(LastRegetDate);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(LastEdorDate);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(LastRevDate);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(RnewFlag);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(StopFlag);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(ExpiryFlag);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(InterestDifFlag);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(SubFlag);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(BnfFlag);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(HealthCheckFlag);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(ImpartFlag);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(ReinsureFlag);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(AgentPayFlag);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(AgentGetFlag);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(LiveGetMode);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(DeadGetMode);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(BonusGetMode);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(BonusMan);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(DeadFlag);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(ApproveDate);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(UWDate);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(PolApplyDate);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(MakeDate);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(ModifyDate);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(WaitPeriod);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(GetForm);
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(GetBankCode);
				break;
			case 112:
				strFieldValue = StrTool.GBKToUnicode(GetBankAccNo);
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(GetAccName);
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(KeepValueOpt);
				break;
			case 115:
				strFieldValue = StrTool.GBKToUnicode(PayRuleCode);
				break;
			case 116:
				strFieldValue = StrTool.GBKToUnicode(AscriptionRuleCode);
				break;
			case 117:
				strFieldValue = StrTool.GBKToUnicode(AutoPubAccFlag);
				break;
			case 118:
				strFieldValue = StrTool.GBKToUnicode(RiskSequence);
				break;
			case 119:
				strFieldValue = StrTool.GBKToUnicode(GetLimit);
				break;
			case 120:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 121:
				strFieldValue = StrTool.GBKToUnicode(TransTime);
				break;
			case 122:
				strFieldValue = StrTool.GBKToUnicode(TransOperator);
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

		if (FCode.equalsIgnoreCase("IDX"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDX = FValue.trim();
			}
			else
				IDX = null;
		}
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
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InputNo = i;
			}
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
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FillNo = FValue.trim();
			}
			else
				FillNo = null;
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
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredPeoples = FValue.trim();
			}
			else
				InsuredPeoples = null;
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CValiDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				SignDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				FirstPayDate = FValue.trim();
			}
			else
				FirstPayDate = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDate = FValue.trim();
			}
			else
				PayEndDate = null;
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PaytoDate = FValue.trim();
			}
			else
				PaytoDate = null;
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartDate = FValue.trim();
			}
			else
				GetStartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDate = FValue.trim();
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcciEndDate = FValue.trim();
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
				GetYear = FValue.trim();
			}
			else
				GetYear = null;
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
				PayEndYear = FValue.trim();
			}
			else
				PayEndYear = null;
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
				InsuYear = FValue.trim();
			}
			else
				InsuYear = null;
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
				AcciYear = FValue.trim();
			}
			else
				AcciYear = null;
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
				PayIntv = FValue.trim();
			}
			else
				PayIntv = null;
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayYears = FValue.trim();
			}
			else
				PayYears = null;
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Years = FValue.trim();
			}
			else
				Years = null;
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageFeeRate = FValue.trim();
			}
			else
				ManageFeeRate = null;
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
				Mult = FValue.trim();
			}
			else
				Mult = null;
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandPrem = FValue.trim();
			}
			else
				StandPrem = null;
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prem = FValue.trim();
			}
			else
				Prem = null;
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SumPrem = FValue.trim();
			}
			else
				SumPrem = null;
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Amnt = FValue.trim();
			}
			else
				Amnt = null;
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAmnt = FValue.trim();
			}
			else
				RiskAmnt = null;
		}
		if (FCode.equalsIgnoreCase("LeavingMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LeavingMoney = FValue.trim();
			}
			else
				LeavingMoney = null;
		}
		if (FCode.equalsIgnoreCase("EndorseTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndorseTimes = FValue.trim();
			}
			else
				EndorseTimes = null;
		}
		if (FCode.equalsIgnoreCase("ClaimTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimTimes = FValue.trim();
			}
			else
				ClaimTimes = null;
		}
		if (FCode.equalsIgnoreCase("LiveTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveTimes = FValue.trim();
			}
			else
				LiveTimes = null;
		}
		if (FCode.equalsIgnoreCase("RenewCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewCount = FValue.trim();
			}
			else
				RenewCount = null;
		}
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastGetDate = FValue.trim();
			}
			else
				LastGetDate = null;
		}
		if (FCode.equalsIgnoreCase("LastLoanDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastLoanDate = FValue.trim();
			}
			else
				LastLoanDate = null;
		}
		if (FCode.equalsIgnoreCase("LastRegetDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastRegetDate = FValue.trim();
			}
			else
				LastRegetDate = null;
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastEdorDate = FValue.trim();
			}
			else
				LastEdorDate = null;
		}
		if (FCode.equalsIgnoreCase("LastRevDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastRevDate = FValue.trim();
			}
			else
				LastRevDate = null;
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RnewFlag = FValue.trim();
			}
			else
				RnewFlag = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				ApproveDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				UWDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				PolApplyDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				MakeDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ModifyDate = FValue.trim();
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
				WaitPeriod = FValue.trim();
			}
			else
				WaitPeriod = null;
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
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetLimit = FValue.trim();
			}
			else
				GetLimit = null;
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransTime = FValue.trim();
			}
			else
				TransTime = null;
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransOperator = FValue.trim();
			}
			else
				TransOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPOPolBSchema other = (LBPOPolBSchema)otherObject;
		return
			IDX.equals(other.getIDX())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& InputNo == other.getInputNo()
			&& PolNo.equals(other.getPolNo())
			&& FillNo.equals(other.getFillNo())
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
			&& InsuredPeoples.equals(other.getInsuredPeoples())
			&& AppntNo.equals(other.getAppntNo())
			&& CValiDate.equals(other.getCValiDate())
			&& SignCom.equals(other.getSignCom())
			&& SignDate.equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& FirstPayDate.equals(other.getFirstPayDate())
			&& PayEndDate.equals(other.getPayEndDate())
			&& PaytoDate.equals(other.getPaytoDate())
			&& GetStartDate.equals(other.getGetStartDate())
			&& EndDate.equals(other.getEndDate())
			&& AcciEndDate.equals(other.getAcciEndDate())
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& GetYear.equals(other.getGetYear())
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear.equals(other.getPayEndYear())
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear.equals(other.getInsuYear())
			&& AcciYearFlag.equals(other.getAcciYearFlag())
			&& AcciYear.equals(other.getAcciYear())
			&& GetStartType.equals(other.getGetStartType())
			&& SpecifyValiDate.equals(other.getSpecifyValiDate())
			&& PayMode.equals(other.getPayMode())
			&& PayLocation.equals(other.getPayLocation())
			&& PayIntv.equals(other.getPayIntv())
			&& PayYears.equals(other.getPayYears())
			&& Years.equals(other.getYears())
			&& ManageFeeRate.equals(other.getManageFeeRate())
			&& FloatRate == other.getFloatRate()
			&& PremToAmnt.equals(other.getPremToAmnt())
			&& Mult.equals(other.getMult())
			&& StandPrem.equals(other.getStandPrem())
			&& Prem.equals(other.getPrem())
			&& SumPrem.equals(other.getSumPrem())
			&& Amnt.equals(other.getAmnt())
			&& RiskAmnt.equals(other.getRiskAmnt())
			&& LeavingMoney.equals(other.getLeavingMoney())
			&& EndorseTimes.equals(other.getEndorseTimes())
			&& ClaimTimes.equals(other.getClaimTimes())
			&& LiveTimes.equals(other.getLiveTimes())
			&& RenewCount.equals(other.getRenewCount())
			&& LastGetDate.equals(other.getLastGetDate())
			&& LastLoanDate.equals(other.getLastLoanDate())
			&& LastRegetDate.equals(other.getLastRegetDate())
			&& LastEdorDate.equals(other.getLastEdorDate())
			&& LastRevDate.equals(other.getLastRevDate())
			&& RnewFlag.equals(other.getRnewFlag())
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
			&& ApproveDate.equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode())
			&& UWDate.equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& PolApplyDate.equals(other.getPolApplyDate())
			&& AppFlag.equals(other.getAppFlag())
			&& PolState.equals(other.getPolState())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& Operator.equals(other.getOperator())
			&& MakeDate.equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate.equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& WaitPeriod.equals(other.getWaitPeriod())
			&& GetForm.equals(other.getGetForm())
			&& GetBankCode.equals(other.getGetBankCode())
			&& GetBankAccNo.equals(other.getGetBankAccNo())
			&& GetAccName.equals(other.getGetAccName())
			&& KeepValueOpt.equals(other.getKeepValueOpt())
			&& PayRuleCode.equals(other.getPayRuleCode())
			&& AscriptionRuleCode.equals(other.getAscriptionRuleCode())
			&& AutoPubAccFlag.equals(other.getAutoPubAccFlag())
			&& RiskSequence.equals(other.getRiskSequence())
			&& GetLimit.equals(other.getGetLimit())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& TransTime.equals(other.getTransTime())
			&& TransOperator.equals(other.getTransOperator());
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
		if( strFieldName.equals("IDX") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("InputNo") ) {
			return 4;
		}
		if( strFieldName.equals("PolNo") ) {
			return 5;
		}
		if( strFieldName.equals("FillNo") ) {
			return 6;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 7;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 8;
		}
		if( strFieldName.equals("ContType") ) {
			return 9;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return 10;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 11;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return 12;
		}
		if( strFieldName.equals("KindCode") ) {
			return 13;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 14;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 15;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 17;
		}
		if( strFieldName.equals("AgentType") ) {
			return 18;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 19;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 20;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 21;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 22;
		}
		if( strFieldName.equals("Handler") ) {
			return 23;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 25;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 26;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 27;
		}
		if( strFieldName.equals("SignCom") ) {
			return 28;
		}
		if( strFieldName.equals("SignDate") ) {
			return 29;
		}
		if( strFieldName.equals("SignTime") ) {
			return 30;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 31;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 32;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 33;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 34;
		}
		if( strFieldName.equals("EndDate") ) {
			return 35;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return 36;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 37;
		}
		if( strFieldName.equals("GetYear") ) {
			return 38;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 39;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 40;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 41;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 42;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return 43;
		}
		if( strFieldName.equals("AcciYear") ) {
			return 44;
		}
		if( strFieldName.equals("GetStartType") ) {
			return 45;
		}
		if( strFieldName.equals("SpecifyValiDate") ) {
			return 46;
		}
		if( strFieldName.equals("PayMode") ) {
			return 47;
		}
		if( strFieldName.equals("PayLocation") ) {
			return 48;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 49;
		}
		if( strFieldName.equals("PayYears") ) {
			return 50;
		}
		if( strFieldName.equals("Years") ) {
			return 51;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 52;
		}
		if( strFieldName.equals("FloatRate") ) {
			return 53;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return 54;
		}
		if( strFieldName.equals("Mult") ) {
			return 55;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 56;
		}
		if( strFieldName.equals("Prem") ) {
			return 57;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 58;
		}
		if( strFieldName.equals("Amnt") ) {
			return 59;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 60;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return 61;
		}
		if( strFieldName.equals("EndorseTimes") ) {
			return 62;
		}
		if( strFieldName.equals("ClaimTimes") ) {
			return 63;
		}
		if( strFieldName.equals("LiveTimes") ) {
			return 64;
		}
		if( strFieldName.equals("RenewCount") ) {
			return 65;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return 66;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return 67;
		}
		if( strFieldName.equals("LastRegetDate") ) {
			return 68;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 69;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return 70;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 71;
		}
		if( strFieldName.equals("StopFlag") ) {
			return 72;
		}
		if( strFieldName.equals("ExpiryFlag") ) {
			return 73;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 74;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return 75;
		}
		if( strFieldName.equals("SubFlag") ) {
			return 76;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return 77;
		}
		if( strFieldName.equals("HealthCheckFlag") ) {
			return 78;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return 79;
		}
		if( strFieldName.equals("ReinsureFlag") ) {
			return 80;
		}
		if( strFieldName.equals("AgentPayFlag") ) {
			return 81;
		}
		if( strFieldName.equals("AgentGetFlag") ) {
			return 82;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return 83;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return 84;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return 85;
		}
		if( strFieldName.equals("BonusMan") ) {
			return 86;
		}
		if( strFieldName.equals("DeadFlag") ) {
			return 87;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 88;
		}
		if( strFieldName.equals("Remark") ) {
			return 89;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 90;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 91;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 92;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 93;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 94;
		}
		if( strFieldName.equals("UWCode") ) {
			return 95;
		}
		if( strFieldName.equals("UWDate") ) {
			return 96;
		}
		if( strFieldName.equals("UWTime") ) {
			return 97;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 98;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 99;
		}
		if( strFieldName.equals("PolState") ) {
			return 100;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 101;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 102;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 103;
		}
		if( strFieldName.equals("Operator") ) {
			return 104;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 105;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 106;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 107;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 108;
		}
		if( strFieldName.equals("WaitPeriod") ) {
			return 109;
		}
		if( strFieldName.equals("GetForm") ) {
			return 110;
		}
		if( strFieldName.equals("GetBankCode") ) {
			return 111;
		}
		if( strFieldName.equals("GetBankAccNo") ) {
			return 112;
		}
		if( strFieldName.equals("GetAccName") ) {
			return 113;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return 114;
		}
		if( strFieldName.equals("PayRuleCode") ) {
			return 115;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return 116;
		}
		if( strFieldName.equals("AutoPubAccFlag") ) {
			return 117;
		}
		if( strFieldName.equals("RiskSequence") ) {
			return 118;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 119;
		}
		if( strFieldName.equals("TransDate") ) {
			return 120;
		}
		if( strFieldName.equals("TransTime") ) {
			return 121;
		}
		if( strFieldName.equals("TransOperator") ) {
			return 122;
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
				strFieldName = "IDX";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "GrpPolNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "InputNo";
				break;
			case 5:
				strFieldName = "PolNo";
				break;
			case 6:
				strFieldName = "FillNo";
				break;
			case 7:
				strFieldName = "ProposalNo";
				break;
			case 8:
				strFieldName = "PrtNo";
				break;
			case 9:
				strFieldName = "ContType";
				break;
			case 10:
				strFieldName = "PolTypeFlag";
				break;
			case 11:
				strFieldName = "MainPolNo";
				break;
			case 12:
				strFieldName = "MasterPolNo";
				break;
			case 13:
				strFieldName = "KindCode";
				break;
			case 14:
				strFieldName = "RiskCode";
				break;
			case 15:
				strFieldName = "RiskVersion";
				break;
			case 16:
				strFieldName = "ManageCom";
				break;
			case 17:
				strFieldName = "AgentCom";
				break;
			case 18:
				strFieldName = "AgentType";
				break;
			case 19:
				strFieldName = "AgentCode";
				break;
			case 20:
				strFieldName = "AgentGroup";
				break;
			case 21:
				strFieldName = "AgentCode1";
				break;
			case 22:
				strFieldName = "SaleChnl";
				break;
			case 23:
				strFieldName = "Handler";
				break;
			case 24:
				strFieldName = "InsuredNo";
				break;
			case 25:
				strFieldName = "InsuredPeoples";
				break;
			case 26:
				strFieldName = "AppntNo";
				break;
			case 27:
				strFieldName = "CValiDate";
				break;
			case 28:
				strFieldName = "SignCom";
				break;
			case 29:
				strFieldName = "SignDate";
				break;
			case 30:
				strFieldName = "SignTime";
				break;
			case 31:
				strFieldName = "FirstPayDate";
				break;
			case 32:
				strFieldName = "PayEndDate";
				break;
			case 33:
				strFieldName = "PaytoDate";
				break;
			case 34:
				strFieldName = "GetStartDate";
				break;
			case 35:
				strFieldName = "EndDate";
				break;
			case 36:
				strFieldName = "AcciEndDate";
				break;
			case 37:
				strFieldName = "GetYearFlag";
				break;
			case 38:
				strFieldName = "GetYear";
				break;
			case 39:
				strFieldName = "PayEndYearFlag";
				break;
			case 40:
				strFieldName = "PayEndYear";
				break;
			case 41:
				strFieldName = "InsuYearFlag";
				break;
			case 42:
				strFieldName = "InsuYear";
				break;
			case 43:
				strFieldName = "AcciYearFlag";
				break;
			case 44:
				strFieldName = "AcciYear";
				break;
			case 45:
				strFieldName = "GetStartType";
				break;
			case 46:
				strFieldName = "SpecifyValiDate";
				break;
			case 47:
				strFieldName = "PayMode";
				break;
			case 48:
				strFieldName = "PayLocation";
				break;
			case 49:
				strFieldName = "PayIntv";
				break;
			case 50:
				strFieldName = "PayYears";
				break;
			case 51:
				strFieldName = "Years";
				break;
			case 52:
				strFieldName = "ManageFeeRate";
				break;
			case 53:
				strFieldName = "FloatRate";
				break;
			case 54:
				strFieldName = "PremToAmnt";
				break;
			case 55:
				strFieldName = "Mult";
				break;
			case 56:
				strFieldName = "StandPrem";
				break;
			case 57:
				strFieldName = "Prem";
				break;
			case 58:
				strFieldName = "SumPrem";
				break;
			case 59:
				strFieldName = "Amnt";
				break;
			case 60:
				strFieldName = "RiskAmnt";
				break;
			case 61:
				strFieldName = "LeavingMoney";
				break;
			case 62:
				strFieldName = "EndorseTimes";
				break;
			case 63:
				strFieldName = "ClaimTimes";
				break;
			case 64:
				strFieldName = "LiveTimes";
				break;
			case 65:
				strFieldName = "RenewCount";
				break;
			case 66:
				strFieldName = "LastGetDate";
				break;
			case 67:
				strFieldName = "LastLoanDate";
				break;
			case 68:
				strFieldName = "LastRegetDate";
				break;
			case 69:
				strFieldName = "LastEdorDate";
				break;
			case 70:
				strFieldName = "LastRevDate";
				break;
			case 71:
				strFieldName = "RnewFlag";
				break;
			case 72:
				strFieldName = "StopFlag";
				break;
			case 73:
				strFieldName = "ExpiryFlag";
				break;
			case 74:
				strFieldName = "AutoPayFlag";
				break;
			case 75:
				strFieldName = "InterestDifFlag";
				break;
			case 76:
				strFieldName = "SubFlag";
				break;
			case 77:
				strFieldName = "BnfFlag";
				break;
			case 78:
				strFieldName = "HealthCheckFlag";
				break;
			case 79:
				strFieldName = "ImpartFlag";
				break;
			case 80:
				strFieldName = "ReinsureFlag";
				break;
			case 81:
				strFieldName = "AgentPayFlag";
				break;
			case 82:
				strFieldName = "AgentGetFlag";
				break;
			case 83:
				strFieldName = "LiveGetMode";
				break;
			case 84:
				strFieldName = "DeadGetMode";
				break;
			case 85:
				strFieldName = "BonusGetMode";
				break;
			case 86:
				strFieldName = "BonusMan";
				break;
			case 87:
				strFieldName = "DeadFlag";
				break;
			case 88:
				strFieldName = "SmokeFlag";
				break;
			case 89:
				strFieldName = "Remark";
				break;
			case 90:
				strFieldName = "ApproveFlag";
				break;
			case 91:
				strFieldName = "ApproveCode";
				break;
			case 92:
				strFieldName = "ApproveDate";
				break;
			case 93:
				strFieldName = "ApproveTime";
				break;
			case 94:
				strFieldName = "UWFlag";
				break;
			case 95:
				strFieldName = "UWCode";
				break;
			case 96:
				strFieldName = "UWDate";
				break;
			case 97:
				strFieldName = "UWTime";
				break;
			case 98:
				strFieldName = "PolApplyDate";
				break;
			case 99:
				strFieldName = "AppFlag";
				break;
			case 100:
				strFieldName = "PolState";
				break;
			case 101:
				strFieldName = "StandbyFlag1";
				break;
			case 102:
				strFieldName = "StandbyFlag2";
				break;
			case 103:
				strFieldName = "StandbyFlag3";
				break;
			case 104:
				strFieldName = "Operator";
				break;
			case 105:
				strFieldName = "MakeDate";
				break;
			case 106:
				strFieldName = "MakeTime";
				break;
			case 107:
				strFieldName = "ModifyDate";
				break;
			case 108:
				strFieldName = "ModifyTime";
				break;
			case 109:
				strFieldName = "WaitPeriod";
				break;
			case 110:
				strFieldName = "GetForm";
				break;
			case 111:
				strFieldName = "GetBankCode";
				break;
			case 112:
				strFieldName = "GetBankAccNo";
				break;
			case 113:
				strFieldName = "GetAccName";
				break;
			case 114:
				strFieldName = "KeepValueOpt";
				break;
			case 115:
				strFieldName = "PayRuleCode";
				break;
			case 116:
				strFieldName = "AscriptionRuleCode";
				break;
			case 117:
				strFieldName = "AutoPubAccFlag";
				break;
			case 118:
				strFieldName = "RiskSequence";
				break;
			case 119:
				strFieldName = "GetLimit";
				break;
			case 120:
				strFieldName = "TransDate";
				break;
			case 121:
				strFieldName = "TransTime";
				break;
			case 122:
				strFieldName = "TransOperator";
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
		if( strFieldName.equals("IDX") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FillNo") ) {
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
		if( strFieldName.equals("InsuredPeoples") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciYear") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FloatRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndorseTimes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimTimes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveTimes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RenewCount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastRegetDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WaitPeriod") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("AutoPubAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskSequence") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 109:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 110:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 111:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 112:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 121:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 122:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
