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
import com.sinosoft.lis.db.LCContBakDB;

/*
 * <p>ClassName: LCContBakSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCContBakSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContBakSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 总单类型 */
	private String ContType;
	/** 家庭单类型 */
	private String FamilyType;
	/** 家庭保障号 */
	private String FamilyID;
	/** 保单类型标记 */
	private String PolType;
	/** 卡单标志 */
	private String CardFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 处理机构 */
	private String ExecuteCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 经办人 */
	private String Handler;
	/** 保单口令 */
	private String Password;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 投保人性别 */
	private String AppntSex;
	/** 投保人出生日期 */
	private Date AppntBirthday;
	/** 投保人证件类型 */
	private String AppntIDType;
	/** 投保人证件号码 */
	private String AppntIDNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人出生日期 */
	private Date InsuredBirthday;
	/** 证件类型 */
	private String InsuredIDType;
	/** 证件号码 */
	private String InsuredIDNo;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费方式 */
	private String PayMode;
	/** 交费位置 */
	private String PayLocation;
	/** 合同争议处理方式 */
	private String DisputedFlag;
	/** 溢交处理方式 */
	private String OutPayFlag;
	/** 保单送达方式 */
	private String GetPolMode;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private Date SignDate;
	/** 签单时间 */
	private String SignTime;
	/** 银行委托书号码 */
	private String ConsignNo;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 保单打印次数 */
	private int PrintCount;
	/** 遗失补发次数 */
	private int LostTimes;
	/** 语种标记 */
	private String Lang;
	/** 币别 */
	private String Currency;
	/** 备注 */
	private String Remark;
	/** 人数 */
	private int Peoples;
	/** 份数 */
	private double Mult;
	/** 保费 */
	private double Prem;
	/** 保额 */
	private double Amnt;
	/** 累计保费 */
	private double SumPrem;
	/** 余额 */
	private double Dif;
	/** 交至日期 */
	private Date PaytoDate;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 录单人 */
	private String InputOperator;
	/** 录单完成日期 */
	private Date InputDate;
	/** 录单完成时间 */
	private String InputTime;
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
	/** 核保人 */
	private String UWOperator;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 保单送达日期 */
	private Date GetPolDate;
	/** 保单送达时间 */
	private String GetPolTime;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
	/** 状态 */
	private String State;
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
	/** 初审人 */
	private String FirstTrialOperator;
	/** 初审日期 */
	private Date FirstTrialDate;
	/** 初审时间 */
	private String FirstTrialTime;
	/** 收单人 */
	private String ReceiveOperator;
	/** 收单日期 */
	private Date ReceiveDate;
	/** 收单时间 */
	private String ReceiveTime;
	/** 暂收据号 */
	private String TempFeeNo;
	/** 销售方式 */
	private String SellType;
	/** 强制人工核保标志 */
	private String ForceUWFlag;
	/** 强制人工核保原因 */
	private String ForceUWReason;
	/** 首期银行编码 */
	private String NewBankCode;
	/** 首期银行帐号 */
	private String NewBankAccNo;
	/** 首期银行帐户名 */
	private String NewAccName;
	/** 首期交费方式 */
	private String NewPayMode;
	/** 银代银行代码 */
	private String AgentBankCode;
	/** 银代柜员 */
	private String BankAgent;
	/** 自动垫交标志 */
	private String AutoPayFlag;
	/** 续保标志 */
	private int RnewFlag;
	/** 家庭保单号码 */
	private String FamilyContNo;
	/** 商业因素标准体承保标志 */
	private String BussFlag;
	/** 初审员签名 */
	private String SignName;
	/** 合同成立日期 */
	private Date OrganizeDate;
	/** 合同成立时间 */
	private String OrganizeTime;
	/** 首期自动发盘标志 */
	private String NewAutoSendBankFlag;
	/** 综拓专员编码 */
	private String AgentCodeOper;
	/** 综拓助理编码 */
	private String AgentCodeAssi;
	/** 延迟送达原因代码 */
	private String DelayReasonCode;
	/** 延迟送达原因 */
	private String DelayReasonDesc;
	/** 续期缴费提示 */
	private String XQremindflag;

	public static final int FIELDNUM = 109;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContBakSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ContNo";

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
		LCContBakSchema cloned = (LCContBakSchema)super.clone();
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/**
	* 同投保单号
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
	* 0-个人，1-家庭单
	*/
	public String getFamilyType()
	{
		return FamilyType;
	}
	public void setFamilyType(String aFamilyType)
	{
		FamilyType = aFamilyType;
	}
	public String getFamilyID()
	{
		return FamilyID;
	}
	public void setFamilyID(String aFamilyID)
	{
		FamilyID = aFamilyID;
	}
	/**
	* 0 --个人单：<p>
	* 1 --无名单；如果是个单表示生日单（数据转换）<p>
	* 2 --（团单）公共帐户
	*/
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		PolType = aPolType;
	}
	/**
	* 0 --正常<p>
	* 1 --定额单<p>
	* 3 --卡单
	*/
	public String getCardFlag()
	{
		return CardFlag;
	}
	public void setCardFlag(String aCardFlag)
	{
		CardFlag = aCardFlag;
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
	* 关联统括保单处理
	*/
	public String getExecuteCom()
	{
		return ExecuteCom;
	}
	public void setExecuteCom(String aExecuteCom)
	{
		ExecuteCom = aExecuteCom;
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
	* 代理机构计算佣金的方式
	*/
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
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
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
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
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getAppntSex()
	{
		return AppntSex;
	}
	public void setAppntSex(String aAppntSex)
	{
		AppntSex = aAppntSex;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getAppntBirthday()
	{
		if( AppntBirthday != null )
			return fDate.getString(AppntBirthday);
		else
			return null;
	}
	public void setAppntBirthday(Date aAppntBirthday)
	{
		AppntBirthday = aAppntBirthday;
	}
	public void setAppntBirthday(String aAppntBirthday)
	{
		if (aAppntBirthday != null && !aAppntBirthday.equals("") )
		{
			AppntBirthday = fDate.getDate( aAppntBirthday );
		}
		else
			AppntBirthday = null;
	}

	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getAppntIDType()
	{
		return AppntIDType;
	}
	public void setAppntIDType(String aAppntIDType)
	{
		AppntIDType = aAppntIDType;
	}
	public String getAppntIDNo()
	{
		return AppntIDNo;
	}
	public void setAppntIDNo(String aAppntIDNo)
	{
		AppntIDNo = aAppntIDNo;
	}
	/**
	* 家庭单时需要反写
	*/
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	/**
	* 冗余，作为保单层面的标准
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
	* 冗余，作为保单层面的标准
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
	* 冗余，作为保单层面的标准
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

	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getInsuredIDType()
	{
		return InsuredIDType;
	}
	public void setInsuredIDType(String aInsuredIDType)
	{
		InsuredIDType = aInsuredIDType;
	}
	public String getInsuredIDNo()
	{
		return InsuredIDNo;
	}
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		InsuredIDNo = aInsuredIDNo;
	}
	/**
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
	* 0--老系统数据补录<p>
	* 1--现金<p>
	* 2--现金送款簿<p>
	* 3--支票<p>
	* 4--银行转帐（非制返盘）<p>
	* 5--内部转帐<p>
	* 6--POS收款<p>
	* 7--银行代扣（制返盘）<p>
	* 8--邮政业务<p>
	* 9--银行收款
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
	* 该字段表示续期交费形式<p>
	* 0 --银行转帐<p>
	* 1 --自行缴纳<p>
	* 2 --上门缴费
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
	* 备用
	*/
	public String getDisputedFlag()
	{
		return DisputedFlag;
	}
	public void setDisputedFlag(String aDisputedFlag)
	{
		DisputedFlag = aDisputedFlag;
	}
	/**
	* 备用
	*/
	public String getOutPayFlag()
	{
		return OutPayFlag;
	}
	public void setOutPayFlag(String aOutPayFlag)
	{
		OutPayFlag = aOutPayFlag;
	}
	/**
	* 暂时备用，数据转换数据都为0，新系统数据为1<p>
	* 0 -- 返回银行领取<p>
	* 1 -- 邮寄或专递
	*/
	public String getGetPolMode()
	{
		return GetPolMode;
	}
	public void setGetPolMode(String aGetPolMode)
	{
		GetPolMode = aGetPolMode;
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
	* 通过银行投保时客户需要填写的委托书
	*/
	public String getConsignNo()
	{
		return ConsignNo;
	}
	public void setConsignNo(String aConsignNo)
	{
		ConsignNo = aConsignNo;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public int getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(int aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		if (aPrintCount != null && !aPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aPrintCount);
			int i = tInteger.intValue();
			PrintCount = i;
		}
	}

	public int getLostTimes()
	{
		return LostTimes;
	}
	public void setLostTimes(int aLostTimes)
	{
		LostTimes = aLostTimes;
	}
	public void setLostTimes(String aLostTimes)
	{
		if (aLostTimes != null && !aLostTimes.equals(""))
		{
			Integer tInteger = new Integer(aLostTimes);
			int i = tInteger.intValue();
			LostTimes = i;
		}
	}

	public String getLang()
	{
		return Lang;
	}
	public void setLang(String aLang)
	{
		Lang = aLang;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/**
	* 数据转化需求将此字段由char(60)扩为varchar2(1600)
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	* 目前没有实际意义
	*/
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

	/**
	* 目前没有实际意义
	*/
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

	/**
	* 目前没有实际意义
	*/
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

	/**
	* 保单账户余额，用于交纳续期保费
	*/
	public double getDif()
	{
		return Dif;
	}
	public void setDif(double aDif)
	{
		Dif = aDif;
	}
	public void setDif(String aDif)
	{
		if (aDif != null && !aDif.equals(""))
		{
			Double tDouble = new Double(aDif);
			double d = tDouble.doubleValue();
			Dif = d;
		}
	}

	/**
	* 备用
	*/
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

	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		InputOperator = aInputOperator;
	}
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	public String getInputTime()
	{
		return InputTime;
	}
	public void setInputTime(String aInputTime)
	{
		InputTime = aInputTime;
	}
	/**
	* 0-未复核<p>
	* 1-复核不通过<p>
	* 9-复核通过
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
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
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
	/**
	* 0 - 投保<p>
	* 1 - 承保<p>
	* 2 - 团体保单增人后未生效状态<p>
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

	public String getGetPolDate()
	{
		if( GetPolDate != null )
			return fDate.getString(GetPolDate);
		else
			return null;
	}
	public void setGetPolDate(Date aGetPolDate)
	{
		GetPolDate = aGetPolDate;
	}
	public void setGetPolDate(String aGetPolDate)
	{
		if (aGetPolDate != null && !aGetPolDate.equals("") )
		{
			GetPolDate = fDate.getDate( aGetPolDate );
		}
		else
			GetPolDate = null;
	}

	public String getGetPolTime()
	{
		return GetPolTime;
	}
	public void setGetPolTime(String aGetPolTime)
	{
		GetPolTime = aGetPolTime;
	}
	public String getCustomGetPolDate()
	{
		if( CustomGetPolDate != null )
			return fDate.getString(CustomGetPolDate);
		else
			return null;
	}
	public void setCustomGetPolDate(Date aCustomGetPolDate)
	{
		CustomGetPolDate = aCustomGetPolDate;
	}
	public void setCustomGetPolDate(String aCustomGetPolDate)
	{
		if (aCustomGetPolDate != null && !aCustomGetPolDate.equals("") )
		{
			CustomGetPolDate = fDate.getDate( aCustomGetPolDate );
		}
		else
			CustomGetPolDate = null;
	}

	/**
	* 1001&&&& -- 通知书逾期<p>
	* 1002&&&& -- 通知书逾期作废<p>
	* 1003&&&& -- 签单逾期作废
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	public String getFirstTrialOperator()
	{
		return FirstTrialOperator;
	}
	public void setFirstTrialOperator(String aFirstTrialOperator)
	{
		FirstTrialOperator = aFirstTrialOperator;
	}
	public String getFirstTrialDate()
	{
		if( FirstTrialDate != null )
			return fDate.getString(FirstTrialDate);
		else
			return null;
	}
	public void setFirstTrialDate(Date aFirstTrialDate)
	{
		FirstTrialDate = aFirstTrialDate;
	}
	public void setFirstTrialDate(String aFirstTrialDate)
	{
		if (aFirstTrialDate != null && !aFirstTrialDate.equals("") )
		{
			FirstTrialDate = fDate.getDate( aFirstTrialDate );
		}
		else
			FirstTrialDate = null;
	}

	public String getFirstTrialTime()
	{
		return FirstTrialTime;
	}
	public void setFirstTrialTime(String aFirstTrialTime)
	{
		FirstTrialTime = aFirstTrialTime;
	}
	public String getReceiveOperator()
	{
		return ReceiveOperator;
	}
	public void setReceiveOperator(String aReceiveOperator)
	{
		ReceiveOperator = aReceiveOperator;
	}
	public String getReceiveDate()
	{
		if( ReceiveDate != null )
			return fDate.getString(ReceiveDate);
		else
			return null;
	}
	public void setReceiveDate(Date aReceiveDate)
	{
		ReceiveDate = aReceiveDate;
	}
	public void setReceiveDate(String aReceiveDate)
	{
		if (aReceiveDate != null && !aReceiveDate.equals("") )
		{
			ReceiveDate = fDate.getDate( aReceiveDate );
		}
		else
			ReceiveDate = null;
	}

	public String getReceiveTime()
	{
		return ReceiveTime;
	}
	public void setReceiveTime(String aReceiveTime)
	{
		ReceiveTime = aReceiveTime;
	}
	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
	}
	public String getSellType()
	{
		return SellType;
	}
	public void setSellType(String aSellType)
	{
		SellType = aSellType;
	}
	/**
	* 1-为强制
	*/
	public String getForceUWFlag()
	{
		return ForceUWFlag;
	}
	public void setForceUWFlag(String aForceUWFlag)
	{
		ForceUWFlag = aForceUWFlag;
	}
	public String getForceUWReason()
	{
		return ForceUWReason;
	}
	public void setForceUWReason(String aForceUWReason)
	{
		ForceUWReason = aForceUWReason;
	}
	public String getNewBankCode()
	{
		return NewBankCode;
	}
	public void setNewBankCode(String aNewBankCode)
	{
		NewBankCode = aNewBankCode;
	}
	public String getNewBankAccNo()
	{
		return NewBankAccNo;
	}
	public void setNewBankAccNo(String aNewBankAccNo)
	{
		NewBankAccNo = aNewBankAccNo;
	}
	public String getNewAccName()
	{
		return NewAccName;
	}
	public void setNewAccName(String aNewAccName)
	{
		NewAccName = aNewAccName;
	}
	public String getNewPayMode()
	{
		return NewPayMode;
	}
	public void setNewPayMode(String aNewPayMode)
	{
		NewPayMode = aNewPayMode;
	}
	public String getAgentBankCode()
	{
		return AgentBankCode;
	}
	public void setAgentBankCode(String aAgentBankCode)
	{
		AgentBankCode = aAgentBankCode;
	}
	public String getBankAgent()
	{
		return BankAgent;
	}
	public void setBankAgent(String aBankAgent)
	{
		BankAgent = aBankAgent;
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
	/**
	* -2 -- 非续保 <p>
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

	public String getFamilyContNo()
	{
		return FamilyContNo;
	}
	public void setFamilyContNo(String aFamilyContNo)
	{
		FamilyContNo = aFamilyContNo;
	}
	/**
	* Y表示有
	*/
	public String getBussFlag()
	{
		return BussFlag;
	}
	public void setBussFlag(String aBussFlag)
	{
		BussFlag = aBussFlag;
	}
	public String getSignName()
	{
		return SignName;
	}
	public void setSignName(String aSignName)
	{
		SignName = aSignName;
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
	/**
	* 投保单默认为自动发盘，核保师在人工核保可指定保单的自动发盘<p>
	* 标志<p>
	* <p>
	* 0              表示不自动发盘<p>
	* 1或者空   表示自动发盘
	*/
	public String getNewAutoSendBankFlag()
	{
		return NewAutoSendBankFlag;
	}
	public void setNewAutoSendBankFlag(String aNewAutoSendBankFlag)
	{
		NewAutoSendBankFlag = aNewAutoSendBankFlag;
	}
	public String getAgentCodeOper()
	{
		return AgentCodeOper;
	}
	public void setAgentCodeOper(String aAgentCodeOper)
	{
		AgentCodeOper = aAgentCodeOper;
	}
	public String getAgentCodeAssi()
	{
		return AgentCodeAssi;
	}
	public void setAgentCodeAssi(String aAgentCodeAssi)
	{
		AgentCodeAssi = aAgentCodeAssi;
	}
	public String getDelayReasonCode()
	{
		return DelayReasonCode;
	}
	public void setDelayReasonCode(String aDelayReasonCode)
	{
		DelayReasonCode = aDelayReasonCode;
	}
	public String getDelayReasonDesc()
	{
		return DelayReasonDesc;
	}
	public void setDelayReasonDesc(String aDelayReasonDesc)
	{
		DelayReasonDesc = aDelayReasonDesc;
	}
	/**
	* 续期交费是否需要提示：<p>
	* <p>
	* 0 ----  否<p>
	* 1 ----  是
	*/
	public String getXQremindflag()
	{
		return XQremindflag;
	}
	public void setXQremindflag(String aXQremindflag)
	{
		XQremindflag = aXQremindflag;
	}

	/**
	* 使用另外一个 LCContBakSchema 对象给 Schema 赋值
	* @param: aLCContBakSchema LCContBakSchema
	**/
	public void setSchema(LCContBakSchema aLCContBakSchema)
	{
		this.GrpContNo = aLCContBakSchema.getGrpContNo();
		this.ContNo = aLCContBakSchema.getContNo();
		this.ProposalContNo = aLCContBakSchema.getProposalContNo();
		this.PrtNo = aLCContBakSchema.getPrtNo();
		this.ContType = aLCContBakSchema.getContType();
		this.FamilyType = aLCContBakSchema.getFamilyType();
		this.FamilyID = aLCContBakSchema.getFamilyID();
		this.PolType = aLCContBakSchema.getPolType();
		this.CardFlag = aLCContBakSchema.getCardFlag();
		this.ManageCom = aLCContBakSchema.getManageCom();
		this.ExecuteCom = aLCContBakSchema.getExecuteCom();
		this.AgentCom = aLCContBakSchema.getAgentCom();
		this.AgentCode = aLCContBakSchema.getAgentCode();
		this.AgentGroup = aLCContBakSchema.getAgentGroup();
		this.AgentCode1 = aLCContBakSchema.getAgentCode1();
		this.AgentType = aLCContBakSchema.getAgentType();
		this.SaleChnl = aLCContBakSchema.getSaleChnl();
		this.Handler = aLCContBakSchema.getHandler();
		this.Password = aLCContBakSchema.getPassword();
		this.AppntNo = aLCContBakSchema.getAppntNo();
		this.AppntName = aLCContBakSchema.getAppntName();
		this.AppntSex = aLCContBakSchema.getAppntSex();
		this.AppntBirthday = fDate.getDate( aLCContBakSchema.getAppntBirthday());
		this.AppntIDType = aLCContBakSchema.getAppntIDType();
		this.AppntIDNo = aLCContBakSchema.getAppntIDNo();
		this.InsuredNo = aLCContBakSchema.getInsuredNo();
		this.InsuredName = aLCContBakSchema.getInsuredName();
		this.InsuredSex = aLCContBakSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLCContBakSchema.getInsuredBirthday());
		this.InsuredIDType = aLCContBakSchema.getInsuredIDType();
		this.InsuredIDNo = aLCContBakSchema.getInsuredIDNo();
		this.PayIntv = aLCContBakSchema.getPayIntv();
		this.PayMode = aLCContBakSchema.getPayMode();
		this.PayLocation = aLCContBakSchema.getPayLocation();
		this.DisputedFlag = aLCContBakSchema.getDisputedFlag();
		this.OutPayFlag = aLCContBakSchema.getOutPayFlag();
		this.GetPolMode = aLCContBakSchema.getGetPolMode();
		this.SignCom = aLCContBakSchema.getSignCom();
		this.SignDate = fDate.getDate( aLCContBakSchema.getSignDate());
		this.SignTime = aLCContBakSchema.getSignTime();
		this.ConsignNo = aLCContBakSchema.getConsignNo();
		this.BankCode = aLCContBakSchema.getBankCode();
		this.BankAccNo = aLCContBakSchema.getBankAccNo();
		this.AccName = aLCContBakSchema.getAccName();
		this.PrintCount = aLCContBakSchema.getPrintCount();
		this.LostTimes = aLCContBakSchema.getLostTimes();
		this.Lang = aLCContBakSchema.getLang();
		this.Currency = aLCContBakSchema.getCurrency();
		this.Remark = aLCContBakSchema.getRemark();
		this.Peoples = aLCContBakSchema.getPeoples();
		this.Mult = aLCContBakSchema.getMult();
		this.Prem = aLCContBakSchema.getPrem();
		this.Amnt = aLCContBakSchema.getAmnt();
		this.SumPrem = aLCContBakSchema.getSumPrem();
		this.Dif = aLCContBakSchema.getDif();
		this.PaytoDate = fDate.getDate( aLCContBakSchema.getPaytoDate());
		this.FirstPayDate = fDate.getDate( aLCContBakSchema.getFirstPayDate());
		this.CValiDate = fDate.getDate( aLCContBakSchema.getCValiDate());
		this.InputOperator = aLCContBakSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLCContBakSchema.getInputDate());
		this.InputTime = aLCContBakSchema.getInputTime();
		this.ApproveFlag = aLCContBakSchema.getApproveFlag();
		this.ApproveCode = aLCContBakSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLCContBakSchema.getApproveDate());
		this.ApproveTime = aLCContBakSchema.getApproveTime();
		this.UWFlag = aLCContBakSchema.getUWFlag();
		this.UWOperator = aLCContBakSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLCContBakSchema.getUWDate());
		this.UWTime = aLCContBakSchema.getUWTime();
		this.AppFlag = aLCContBakSchema.getAppFlag();
		this.PolApplyDate = fDate.getDate( aLCContBakSchema.getPolApplyDate());
		this.GetPolDate = fDate.getDate( aLCContBakSchema.getGetPolDate());
		this.GetPolTime = aLCContBakSchema.getGetPolTime();
		this.CustomGetPolDate = fDate.getDate( aLCContBakSchema.getCustomGetPolDate());
		this.State = aLCContBakSchema.getState();
		this.Operator = aLCContBakSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCContBakSchema.getMakeDate());
		this.MakeTime = aLCContBakSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCContBakSchema.getModifyDate());
		this.ModifyTime = aLCContBakSchema.getModifyTime();
		this.FirstTrialOperator = aLCContBakSchema.getFirstTrialOperator();
		this.FirstTrialDate = fDate.getDate( aLCContBakSchema.getFirstTrialDate());
		this.FirstTrialTime = aLCContBakSchema.getFirstTrialTime();
		this.ReceiveOperator = aLCContBakSchema.getReceiveOperator();
		this.ReceiveDate = fDate.getDate( aLCContBakSchema.getReceiveDate());
		this.ReceiveTime = aLCContBakSchema.getReceiveTime();
		this.TempFeeNo = aLCContBakSchema.getTempFeeNo();
		this.SellType = aLCContBakSchema.getSellType();
		this.ForceUWFlag = aLCContBakSchema.getForceUWFlag();
		this.ForceUWReason = aLCContBakSchema.getForceUWReason();
		this.NewBankCode = aLCContBakSchema.getNewBankCode();
		this.NewBankAccNo = aLCContBakSchema.getNewBankAccNo();
		this.NewAccName = aLCContBakSchema.getNewAccName();
		this.NewPayMode = aLCContBakSchema.getNewPayMode();
		this.AgentBankCode = aLCContBakSchema.getAgentBankCode();
		this.BankAgent = aLCContBakSchema.getBankAgent();
		this.AutoPayFlag = aLCContBakSchema.getAutoPayFlag();
		this.RnewFlag = aLCContBakSchema.getRnewFlag();
		this.FamilyContNo = aLCContBakSchema.getFamilyContNo();
		this.BussFlag = aLCContBakSchema.getBussFlag();
		this.SignName = aLCContBakSchema.getSignName();
		this.OrganizeDate = fDate.getDate( aLCContBakSchema.getOrganizeDate());
		this.OrganizeTime = aLCContBakSchema.getOrganizeTime();
		this.NewAutoSendBankFlag = aLCContBakSchema.getNewAutoSendBankFlag();
		this.AgentCodeOper = aLCContBakSchema.getAgentCodeOper();
		this.AgentCodeAssi = aLCContBakSchema.getAgentCodeAssi();
		this.DelayReasonCode = aLCContBakSchema.getDelayReasonCode();
		this.DelayReasonDesc = aLCContBakSchema.getDelayReasonDesc();
		this.XQremindflag = aLCContBakSchema.getXQremindflag();
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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("FamilyType") == null )
				this.FamilyType = null;
			else
				this.FamilyType = rs.getString("FamilyType").trim();

			if( rs.getString("FamilyID") == null )
				this.FamilyID = null;
			else
				this.FamilyID = rs.getString("FamilyID").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			if( rs.getString("CardFlag") == null )
				this.CardFlag = null;
			else
				this.CardFlag = rs.getString("CardFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

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

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntSex") == null )
				this.AppntSex = null;
			else
				this.AppntSex = rs.getString("AppntSex").trim();

			this.AppntBirthday = rs.getDate("AppntBirthday");
			if( rs.getString("AppntIDType") == null )
				this.AppntIDType = null;
			else
				this.AppntIDType = rs.getString("AppntIDType").trim();

			if( rs.getString("AppntIDNo") == null )
				this.AppntIDNo = null;
			else
				this.AppntIDNo = rs.getString("AppntIDNo").trim();

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
			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayLocation") == null )
				this.PayLocation = null;
			else
				this.PayLocation = rs.getString("PayLocation").trim();

			if( rs.getString("DisputedFlag") == null )
				this.DisputedFlag = null;
			else
				this.DisputedFlag = rs.getString("DisputedFlag").trim();

			if( rs.getString("OutPayFlag") == null )
				this.OutPayFlag = null;
			else
				this.OutPayFlag = rs.getString("OutPayFlag").trim();

			if( rs.getString("GetPolMode") == null )
				this.GetPolMode = null;
			else
				this.GetPolMode = rs.getString("GetPolMode").trim();

			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			if( rs.getString("ConsignNo") == null )
				this.ConsignNo = null;
			else
				this.ConsignNo = rs.getString("ConsignNo").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.PrintCount = rs.getInt("PrintCount");
			this.LostTimes = rs.getInt("LostTimes");
			if( rs.getString("Lang") == null )
				this.Lang = null;
			else
				this.Lang = rs.getString("Lang").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.Peoples = rs.getInt("Peoples");
			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.SumPrem = rs.getDouble("SumPrem");
			this.Dif = rs.getDouble("Dif");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

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

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			this.PolApplyDate = rs.getDate("PolApplyDate");
			this.GetPolDate = rs.getDate("GetPolDate");
			if( rs.getString("GetPolTime") == null )
				this.GetPolTime = null;
			else
				this.GetPolTime = rs.getString("GetPolTime").trim();

			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("FirstTrialOperator") == null )
				this.FirstTrialOperator = null;
			else
				this.FirstTrialOperator = rs.getString("FirstTrialOperator").trim();

			this.FirstTrialDate = rs.getDate("FirstTrialDate");
			if( rs.getString("FirstTrialTime") == null )
				this.FirstTrialTime = null;
			else
				this.FirstTrialTime = rs.getString("FirstTrialTime").trim();

			if( rs.getString("ReceiveOperator") == null )
				this.ReceiveOperator = null;
			else
				this.ReceiveOperator = rs.getString("ReceiveOperator").trim();

			this.ReceiveDate = rs.getDate("ReceiveDate");
			if( rs.getString("ReceiveTime") == null )
				this.ReceiveTime = null;
			else
				this.ReceiveTime = rs.getString("ReceiveTime").trim();

			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			if( rs.getString("SellType") == null )
				this.SellType = null;
			else
				this.SellType = rs.getString("SellType").trim();

			if( rs.getString("ForceUWFlag") == null )
				this.ForceUWFlag = null;
			else
				this.ForceUWFlag = rs.getString("ForceUWFlag").trim();

			if( rs.getString("ForceUWReason") == null )
				this.ForceUWReason = null;
			else
				this.ForceUWReason = rs.getString("ForceUWReason").trim();

			if( rs.getString("NewBankCode") == null )
				this.NewBankCode = null;
			else
				this.NewBankCode = rs.getString("NewBankCode").trim();

			if( rs.getString("NewBankAccNo") == null )
				this.NewBankAccNo = null;
			else
				this.NewBankAccNo = rs.getString("NewBankAccNo").trim();

			if( rs.getString("NewAccName") == null )
				this.NewAccName = null;
			else
				this.NewAccName = rs.getString("NewAccName").trim();

			if( rs.getString("NewPayMode") == null )
				this.NewPayMode = null;
			else
				this.NewPayMode = rs.getString("NewPayMode").trim();

			if( rs.getString("AgentBankCode") == null )
				this.AgentBankCode = null;
			else
				this.AgentBankCode = rs.getString("AgentBankCode").trim();

			if( rs.getString("BankAgent") == null )
				this.BankAgent = null;
			else
				this.BankAgent = rs.getString("BankAgent").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			this.RnewFlag = rs.getInt("RnewFlag");
			if( rs.getString("FamilyContNo") == null )
				this.FamilyContNo = null;
			else
				this.FamilyContNo = rs.getString("FamilyContNo").trim();

			if( rs.getString("BussFlag") == null )
				this.BussFlag = null;
			else
				this.BussFlag = rs.getString("BussFlag").trim();

			if( rs.getString("SignName") == null )
				this.SignName = null;
			else
				this.SignName = rs.getString("SignName").trim();

			this.OrganizeDate = rs.getDate("OrganizeDate");
			if( rs.getString("OrganizeTime") == null )
				this.OrganizeTime = null;
			else
				this.OrganizeTime = rs.getString("OrganizeTime").trim();

			if( rs.getString("NewAutoSendBankFlag") == null )
				this.NewAutoSendBankFlag = null;
			else
				this.NewAutoSendBankFlag = rs.getString("NewAutoSendBankFlag").trim();

			if( rs.getString("AgentCodeOper") == null )
				this.AgentCodeOper = null;
			else
				this.AgentCodeOper = rs.getString("AgentCodeOper").trim();

			if( rs.getString("AgentCodeAssi") == null )
				this.AgentCodeAssi = null;
			else
				this.AgentCodeAssi = rs.getString("AgentCodeAssi").trim();

			if( rs.getString("DelayReasonCode") == null )
				this.DelayReasonCode = null;
			else
				this.DelayReasonCode = rs.getString("DelayReasonCode").trim();

			if( rs.getString("DelayReasonDesc") == null )
				this.DelayReasonDesc = null;
			else
				this.DelayReasonDesc = rs.getString("DelayReasonDesc").trim();

			if( rs.getString("XQremindflag") == null )
				this.XQremindflag = null;
			else
				this.XQremindflag = rs.getString("XQremindflag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCContBak表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContBakSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContBakSchema getSchema()
	{
		LCContBakSchema aLCContBakSchema = new LCContBakSchema();
		aLCContBakSchema.setSchema(this);
		return aLCContBakSchema;
	}

	public LCContBakDB getDB()
	{
		LCContBakDB aDBOper = new LCContBakDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContBak描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExecuteCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppntBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisputedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConsignNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LostTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Lang)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Dif));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstTrialDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReceiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SellType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForceUWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForceUWReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewPayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RnewFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OrganizeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganizeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewAutoSendBankFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeAssi)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(XQremindflag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContBak>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FamilyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CardFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AppntSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AppntBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			AppntIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AppntIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ConsignNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).intValue();
			LostTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).intValue();
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56,SysConst.PACKAGESPILTER));
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57,SysConst.PACKAGESPILTER));
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72,SysConst.PACKAGESPILTER));
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			FirstTrialOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			FirstTrialDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82,SysConst.PACKAGESPILTER));
			FirstTrialTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			ReceiveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			ReceiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85,SysConst.PACKAGESPILTER));
			ReceiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			SellType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			ForceUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			ForceUWReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			NewBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			NewBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			NewAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			NewPayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			AgentBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			BankAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			RnewFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,98,SysConst.PACKAGESPILTER))).intValue();
			FamilyContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			BussFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			SignName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			OrganizeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102,SysConst.PACKAGESPILTER));
			OrganizeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			NewAutoSendBankFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			AgentCodeOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			AgentCodeAssi = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			DelayReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			DelayReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			XQremindflag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContBakSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("FamilyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyType));
		}
		if (FCode.equalsIgnoreCase("FamilyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyID));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardFlag));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
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
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("AppntSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntSex));
		}
		if (FCode.equalsIgnoreCase("AppntBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
		}
		if (FCode.equalsIgnoreCase("AppntIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDType));
		}
		if (FCode.equalsIgnoreCase("AppntIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDNo));
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
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PayLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLocation));
		}
		if (FCode.equalsIgnoreCase("DisputedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisputedFlag));
		}
		if (FCode.equalsIgnoreCase("OutPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayFlag));
		}
		if (FCode.equalsIgnoreCase("GetPolMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolMode));
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
		if (FCode.equalsIgnoreCase("ConsignNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConsignNo));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostTimes));
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Lang));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("Dif"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dif));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputTime));
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
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolTime));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("FirstTrialOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialOperator));
		}
		if (FCode.equalsIgnoreCase("FirstTrialDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
		}
		if (FCode.equalsIgnoreCase("FirstTrialTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialTime));
		}
		if (FCode.equalsIgnoreCase("ReceiveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveOperator));
		}
		if (FCode.equalsIgnoreCase("ReceiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
		}
		if (FCode.equalsIgnoreCase("ReceiveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveTime));
		}
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equalsIgnoreCase("SellType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SellType));
		}
		if (FCode.equalsIgnoreCase("ForceUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForceUWFlag));
		}
		if (FCode.equalsIgnoreCase("ForceUWReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForceUWReason));
		}
		if (FCode.equalsIgnoreCase("NewBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankCode));
		}
		if (FCode.equalsIgnoreCase("NewBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankAccNo));
		}
		if (FCode.equalsIgnoreCase("NewAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewAccName));
		}
		if (FCode.equalsIgnoreCase("NewPayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewPayMode));
		}
		if (FCode.equalsIgnoreCase("AgentBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentBankCode));
		}
		if (FCode.equalsIgnoreCase("BankAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAgent));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewFlag));
		}
		if (FCode.equalsIgnoreCase("FamilyContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyContNo));
		}
		if (FCode.equalsIgnoreCase("BussFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussFlag));
		}
		if (FCode.equalsIgnoreCase("SignName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignName));
		}
		if (FCode.equalsIgnoreCase("OrganizeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOrganizeDate()));
		}
		if (FCode.equalsIgnoreCase("OrganizeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganizeTime));
		}
		if (FCode.equalsIgnoreCase("NewAutoSendBankFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewAutoSendBankFlag));
		}
		if (FCode.equalsIgnoreCase("AgentCodeOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCodeOper));
		}
		if (FCode.equalsIgnoreCase("AgentCodeAssi"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCodeAssi));
		}
		if (FCode.equalsIgnoreCase("DelayReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DelayReasonCode));
		}
		if (FCode.equalsIgnoreCase("DelayReasonDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DelayReasonDesc));
		}
		if (FCode.equalsIgnoreCase("XQremindflag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(XQremindflag));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FamilyType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CardFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AppntSex);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AppntIDType);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AppntIDNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 31:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(DisputedFlag);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(GetPolMode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(ConsignNo);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 44:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 45:
				strFieldValue = String.valueOf(LostTimes);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 49:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 50:
				strFieldValue = String.valueOf(Mult);
				break;
			case 51:
				strFieldValue = String.valueOf(Prem);
				break;
			case 52:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 53:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 54:
				strFieldValue = String.valueOf(Dif);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialOperator);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialTime);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(ReceiveOperator);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(ReceiveTime);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(SellType);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(ForceUWFlag);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(ForceUWReason);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(NewBankCode);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(NewBankAccNo);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(NewAccName);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(NewPayMode);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(AgentBankCode);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(BankAgent);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 97:
				strFieldValue = String.valueOf(RnewFlag);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(FamilyContNo);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(BussFlag);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(SignName);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOrganizeDate()));
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(OrganizeTime);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(NewAutoSendBankFlag);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeOper);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeAssi);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonCode);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonDesc);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(XQremindflag);
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equalsIgnoreCase("FamilyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyType = FValue.trim();
			}
			else
				FamilyType = null;
		}
		if (FCode.equalsIgnoreCase("FamilyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyID = FValue.trim();
			}
			else
				FamilyID = null;
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolType = FValue.trim();
			}
			else
				PolType = null;
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardFlag = FValue.trim();
			}
			else
				CardFlag = null;
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
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExecuteCom = FValue.trim();
			}
			else
				ExecuteCom = null;
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
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
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
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equalsIgnoreCase("AppntSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntSex = FValue.trim();
			}
			else
				AppntSex = null;
		}
		if (FCode.equalsIgnoreCase("AppntBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppntBirthday = fDate.getDate( FValue );
			}
			else
				AppntBirthday = null;
		}
		if (FCode.equalsIgnoreCase("AppntIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDType = FValue.trim();
			}
			else
				AppntIDType = null;
		}
		if (FCode.equalsIgnoreCase("AppntIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDNo = FValue.trim();
			}
			else
				AppntIDNo = null;
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
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDType = FValue.trim();
			}
			else
				InsuredIDType = null;
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
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
		if (FCode.equalsIgnoreCase("DisputedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisputedFlag = FValue.trim();
			}
			else
				DisputedFlag = null;
		}
		if (FCode.equalsIgnoreCase("OutPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutPayFlag = FValue.trim();
			}
			else
				OutPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetPolMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolMode = FValue.trim();
			}
			else
				GetPolMode = null;
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
		if (FCode.equalsIgnoreCase("ConsignNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConsignNo = FValue.trim();
			}
			else
				ConsignNo = null;
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LostTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Lang = FValue.trim();
			}
			else
				Lang = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
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
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples = i;
			}
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
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
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
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Dif"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Dif = d;
			}
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
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
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
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOperator = FValue.trim();
			}
			else
				InputOperator = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputTime = FValue.trim();
			}
			else
				InputTime = null;
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
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
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
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
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
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetPolDate = fDate.getDate( FValue );
			}
			else
				GetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolTime = FValue.trim();
			}
			else
				GetPolTime = null;
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomGetPolDate = null;
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
		if (FCode.equalsIgnoreCase("FirstTrialOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialOperator = FValue.trim();
			}
			else
				FirstTrialOperator = null;
		}
		if (FCode.equalsIgnoreCase("FirstTrialDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstTrialDate = fDate.getDate( FValue );
			}
			else
				FirstTrialDate = null;
		}
		if (FCode.equalsIgnoreCase("FirstTrialTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialTime = FValue.trim();
			}
			else
				FirstTrialTime = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveOperator = FValue.trim();
			}
			else
				ReceiveOperator = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReceiveDate = fDate.getDate( FValue );
			}
			else
				ReceiveDate = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveTime = FValue.trim();
			}
			else
				ReceiveTime = null;
		}
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("SellType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SellType = FValue.trim();
			}
			else
				SellType = null;
		}
		if (FCode.equalsIgnoreCase("ForceUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForceUWFlag = FValue.trim();
			}
			else
				ForceUWFlag = null;
		}
		if (FCode.equalsIgnoreCase("ForceUWReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForceUWReason = FValue.trim();
			}
			else
				ForceUWReason = null;
		}
		if (FCode.equalsIgnoreCase("NewBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankCode = FValue.trim();
			}
			else
				NewBankCode = null;
		}
		if (FCode.equalsIgnoreCase("NewBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankAccNo = FValue.trim();
			}
			else
				NewBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("NewAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewAccName = FValue.trim();
			}
			else
				NewAccName = null;
		}
		if (FCode.equalsIgnoreCase("NewPayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewPayMode = FValue.trim();
			}
			else
				NewPayMode = null;
		}
		if (FCode.equalsIgnoreCase("AgentBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentBankCode = FValue.trim();
			}
			else
				AgentBankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAgent = FValue.trim();
			}
			else
				BankAgent = null;
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
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RnewFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("FamilyContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyContNo = FValue.trim();
			}
			else
				FamilyContNo = null;
		}
		if (FCode.equalsIgnoreCase("BussFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussFlag = FValue.trim();
			}
			else
				BussFlag = null;
		}
		if (FCode.equalsIgnoreCase("SignName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignName = FValue.trim();
			}
			else
				SignName = null;
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
		if (FCode.equalsIgnoreCase("NewAutoSendBankFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewAutoSendBankFlag = FValue.trim();
			}
			else
				NewAutoSendBankFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentCodeOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCodeOper = FValue.trim();
			}
			else
				AgentCodeOper = null;
		}
		if (FCode.equalsIgnoreCase("AgentCodeAssi"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCodeAssi = FValue.trim();
			}
			else
				AgentCodeAssi = null;
		}
		if (FCode.equalsIgnoreCase("DelayReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DelayReasonCode = FValue.trim();
			}
			else
				DelayReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("DelayReasonDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DelayReasonDesc = FValue.trim();
			}
			else
				DelayReasonDesc = null;
		}
		if (FCode.equalsIgnoreCase("XQremindflag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				XQremindflag = FValue.trim();
			}
			else
				XQremindflag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCContBakSchema other = (LCContBakSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ContType.equals(other.getContType())
			&& FamilyType.equals(other.getFamilyType())
			&& FamilyID.equals(other.getFamilyID())
			&& PolType.equals(other.getPolType())
			&& CardFlag.equals(other.getCardFlag())
			&& ManageCom.equals(other.getManageCom())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& AgentType.equals(other.getAgentType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Handler.equals(other.getHandler())
			&& Password.equals(other.getPassword())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& AppntSex.equals(other.getAppntSex())
			&& fDate.getString(AppntBirthday).equals(other.getAppntBirthday())
			&& AppntIDType.equals(other.getAppntIDType())
			&& AppntIDNo.equals(other.getAppntIDNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& PayIntv == other.getPayIntv()
			&& PayMode.equals(other.getPayMode())
			&& PayLocation.equals(other.getPayLocation())
			&& DisputedFlag.equals(other.getDisputedFlag())
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& GetPolMode.equals(other.getGetPolMode())
			&& SignCom.equals(other.getSignCom())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& ConsignNo.equals(other.getConsignNo())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& PrintCount == other.getPrintCount()
			&& LostTimes == other.getLostTimes()
			&& Lang.equals(other.getLang())
			&& Currency.equals(other.getCurrency())
			&& Remark.equals(other.getRemark())
			&& Peoples == other.getPeoples()
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& SumPrem == other.getSumPrem()
			&& Dif == other.getDif()
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& AppFlag.equals(other.getAppFlag())
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& GetPolTime.equals(other.getGetPolTime())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& FirstTrialOperator.equals(other.getFirstTrialOperator())
			&& fDate.getString(FirstTrialDate).equals(other.getFirstTrialDate())
			&& FirstTrialTime.equals(other.getFirstTrialTime())
			&& ReceiveOperator.equals(other.getReceiveOperator())
			&& fDate.getString(ReceiveDate).equals(other.getReceiveDate())
			&& ReceiveTime.equals(other.getReceiveTime())
			&& TempFeeNo.equals(other.getTempFeeNo())
			&& SellType.equals(other.getSellType())
			&& ForceUWFlag.equals(other.getForceUWFlag())
			&& ForceUWReason.equals(other.getForceUWReason())
			&& NewBankCode.equals(other.getNewBankCode())
			&& NewBankAccNo.equals(other.getNewBankAccNo())
			&& NewAccName.equals(other.getNewAccName())
			&& NewPayMode.equals(other.getNewPayMode())
			&& AgentBankCode.equals(other.getAgentBankCode())
			&& BankAgent.equals(other.getBankAgent())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& RnewFlag == other.getRnewFlag()
			&& FamilyContNo.equals(other.getFamilyContNo())
			&& BussFlag.equals(other.getBussFlag())
			&& SignName.equals(other.getSignName())
			&& fDate.getString(OrganizeDate).equals(other.getOrganizeDate())
			&& OrganizeTime.equals(other.getOrganizeTime())
			&& NewAutoSendBankFlag.equals(other.getNewAutoSendBankFlag())
			&& AgentCodeOper.equals(other.getAgentCodeOper())
			&& AgentCodeAssi.equals(other.getAgentCodeAssi())
			&& DelayReasonCode.equals(other.getDelayReasonCode())
			&& DelayReasonDesc.equals(other.getDelayReasonDesc())
			&& XQremindflag.equals(other.getXQremindflag());
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
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("ContType") ) {
			return 4;
		}
		if( strFieldName.equals("FamilyType") ) {
			return 5;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 6;
		}
		if( strFieldName.equals("PolType") ) {
			return 7;
		}
		if( strFieldName.equals("CardFlag") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 9;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 10;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 11;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 12;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 13;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 14;
		}
		if( strFieldName.equals("AgentType") ) {
			return 15;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 16;
		}
		if( strFieldName.equals("Handler") ) {
			return 17;
		}
		if( strFieldName.equals("Password") ) {
			return 18;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 19;
		}
		if( strFieldName.equals("AppntName") ) {
			return 20;
		}
		if( strFieldName.equals("AppntSex") ) {
			return 21;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return 22;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return 23;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 25;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 26;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 27;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 28;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 29;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 30;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 31;
		}
		if( strFieldName.equals("PayMode") ) {
			return 32;
		}
		if( strFieldName.equals("PayLocation") ) {
			return 33;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return 34;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 35;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return 36;
		}
		if( strFieldName.equals("SignCom") ) {
			return 37;
		}
		if( strFieldName.equals("SignDate") ) {
			return 38;
		}
		if( strFieldName.equals("SignTime") ) {
			return 39;
		}
		if( strFieldName.equals("ConsignNo") ) {
			return 40;
		}
		if( strFieldName.equals("BankCode") ) {
			return 41;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 42;
		}
		if( strFieldName.equals("AccName") ) {
			return 43;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 44;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 45;
		}
		if( strFieldName.equals("Lang") ) {
			return 46;
		}
		if( strFieldName.equals("Currency") ) {
			return 47;
		}
		if( strFieldName.equals("Remark") ) {
			return 48;
		}
		if( strFieldName.equals("Peoples") ) {
			return 49;
		}
		if( strFieldName.equals("Mult") ) {
			return 50;
		}
		if( strFieldName.equals("Prem") ) {
			return 51;
		}
		if( strFieldName.equals("Amnt") ) {
			return 52;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 53;
		}
		if( strFieldName.equals("Dif") ) {
			return 54;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 55;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 56;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 57;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 58;
		}
		if( strFieldName.equals("InputDate") ) {
			return 59;
		}
		if( strFieldName.equals("InputTime") ) {
			return 60;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 61;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 62;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 63;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 64;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 65;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 66;
		}
		if( strFieldName.equals("UWDate") ) {
			return 67;
		}
		if( strFieldName.equals("UWTime") ) {
			return 68;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 69;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 70;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 71;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 72;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 73;
		}
		if( strFieldName.equals("State") ) {
			return 74;
		}
		if( strFieldName.equals("Operator") ) {
			return 75;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 76;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 77;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 78;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 79;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return 80;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return 81;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return 82;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return 83;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 84;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return 85;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 86;
		}
		if( strFieldName.equals("SellType") ) {
			return 87;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return 88;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return 89;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return 90;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return 91;
		}
		if( strFieldName.equals("NewAccName") ) {
			return 92;
		}
		if( strFieldName.equals("NewPayMode") ) {
			return 93;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return 94;
		}
		if( strFieldName.equals("BankAgent") ) {
			return 95;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 96;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 97;
		}
		if( strFieldName.equals("FamilyContNo") ) {
			return 98;
		}
		if( strFieldName.equals("BussFlag") ) {
			return 99;
		}
		if( strFieldName.equals("SignName") ) {
			return 100;
		}
		if( strFieldName.equals("OrganizeDate") ) {
			return 101;
		}
		if( strFieldName.equals("OrganizeTime") ) {
			return 102;
		}
		if( strFieldName.equals("NewAutoSendBankFlag") ) {
			return 103;
		}
		if( strFieldName.equals("AgentCodeOper") ) {
			return 104;
		}
		if( strFieldName.equals("AgentCodeAssi") ) {
			return 105;
		}
		if( strFieldName.equals("DelayReasonCode") ) {
			return 106;
		}
		if( strFieldName.equals("DelayReasonDesc") ) {
			return 107;
		}
		if( strFieldName.equals("XQremindflag") ) {
			return 108;
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
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "ProposalContNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "ContType";
				break;
			case 5:
				strFieldName = "FamilyType";
				break;
			case 6:
				strFieldName = "FamilyID";
				break;
			case 7:
				strFieldName = "PolType";
				break;
			case 8:
				strFieldName = "CardFlag";
				break;
			case 9:
				strFieldName = "ManageCom";
				break;
			case 10:
				strFieldName = "ExecuteCom";
				break;
			case 11:
				strFieldName = "AgentCom";
				break;
			case 12:
				strFieldName = "AgentCode";
				break;
			case 13:
				strFieldName = "AgentGroup";
				break;
			case 14:
				strFieldName = "AgentCode1";
				break;
			case 15:
				strFieldName = "AgentType";
				break;
			case 16:
				strFieldName = "SaleChnl";
				break;
			case 17:
				strFieldName = "Handler";
				break;
			case 18:
				strFieldName = "Password";
				break;
			case 19:
				strFieldName = "AppntNo";
				break;
			case 20:
				strFieldName = "AppntName";
				break;
			case 21:
				strFieldName = "AppntSex";
				break;
			case 22:
				strFieldName = "AppntBirthday";
				break;
			case 23:
				strFieldName = "AppntIDType";
				break;
			case 24:
				strFieldName = "AppntIDNo";
				break;
			case 25:
				strFieldName = "InsuredNo";
				break;
			case 26:
				strFieldName = "InsuredName";
				break;
			case 27:
				strFieldName = "InsuredSex";
				break;
			case 28:
				strFieldName = "InsuredBirthday";
				break;
			case 29:
				strFieldName = "InsuredIDType";
				break;
			case 30:
				strFieldName = "InsuredIDNo";
				break;
			case 31:
				strFieldName = "PayIntv";
				break;
			case 32:
				strFieldName = "PayMode";
				break;
			case 33:
				strFieldName = "PayLocation";
				break;
			case 34:
				strFieldName = "DisputedFlag";
				break;
			case 35:
				strFieldName = "OutPayFlag";
				break;
			case 36:
				strFieldName = "GetPolMode";
				break;
			case 37:
				strFieldName = "SignCom";
				break;
			case 38:
				strFieldName = "SignDate";
				break;
			case 39:
				strFieldName = "SignTime";
				break;
			case 40:
				strFieldName = "ConsignNo";
				break;
			case 41:
				strFieldName = "BankCode";
				break;
			case 42:
				strFieldName = "BankAccNo";
				break;
			case 43:
				strFieldName = "AccName";
				break;
			case 44:
				strFieldName = "PrintCount";
				break;
			case 45:
				strFieldName = "LostTimes";
				break;
			case 46:
				strFieldName = "Lang";
				break;
			case 47:
				strFieldName = "Currency";
				break;
			case 48:
				strFieldName = "Remark";
				break;
			case 49:
				strFieldName = "Peoples";
				break;
			case 50:
				strFieldName = "Mult";
				break;
			case 51:
				strFieldName = "Prem";
				break;
			case 52:
				strFieldName = "Amnt";
				break;
			case 53:
				strFieldName = "SumPrem";
				break;
			case 54:
				strFieldName = "Dif";
				break;
			case 55:
				strFieldName = "PaytoDate";
				break;
			case 56:
				strFieldName = "FirstPayDate";
				break;
			case 57:
				strFieldName = "CValiDate";
				break;
			case 58:
				strFieldName = "InputOperator";
				break;
			case 59:
				strFieldName = "InputDate";
				break;
			case 60:
				strFieldName = "InputTime";
				break;
			case 61:
				strFieldName = "ApproveFlag";
				break;
			case 62:
				strFieldName = "ApproveCode";
				break;
			case 63:
				strFieldName = "ApproveDate";
				break;
			case 64:
				strFieldName = "ApproveTime";
				break;
			case 65:
				strFieldName = "UWFlag";
				break;
			case 66:
				strFieldName = "UWOperator";
				break;
			case 67:
				strFieldName = "UWDate";
				break;
			case 68:
				strFieldName = "UWTime";
				break;
			case 69:
				strFieldName = "AppFlag";
				break;
			case 70:
				strFieldName = "PolApplyDate";
				break;
			case 71:
				strFieldName = "GetPolDate";
				break;
			case 72:
				strFieldName = "GetPolTime";
				break;
			case 73:
				strFieldName = "CustomGetPolDate";
				break;
			case 74:
				strFieldName = "State";
				break;
			case 75:
				strFieldName = "Operator";
				break;
			case 76:
				strFieldName = "MakeDate";
				break;
			case 77:
				strFieldName = "MakeTime";
				break;
			case 78:
				strFieldName = "ModifyDate";
				break;
			case 79:
				strFieldName = "ModifyTime";
				break;
			case 80:
				strFieldName = "FirstTrialOperator";
				break;
			case 81:
				strFieldName = "FirstTrialDate";
				break;
			case 82:
				strFieldName = "FirstTrialTime";
				break;
			case 83:
				strFieldName = "ReceiveOperator";
				break;
			case 84:
				strFieldName = "ReceiveDate";
				break;
			case 85:
				strFieldName = "ReceiveTime";
				break;
			case 86:
				strFieldName = "TempFeeNo";
				break;
			case 87:
				strFieldName = "SellType";
				break;
			case 88:
				strFieldName = "ForceUWFlag";
				break;
			case 89:
				strFieldName = "ForceUWReason";
				break;
			case 90:
				strFieldName = "NewBankCode";
				break;
			case 91:
				strFieldName = "NewBankAccNo";
				break;
			case 92:
				strFieldName = "NewAccName";
				break;
			case 93:
				strFieldName = "NewPayMode";
				break;
			case 94:
				strFieldName = "AgentBankCode";
				break;
			case 95:
				strFieldName = "BankAgent";
				break;
			case 96:
				strFieldName = "AutoPayFlag";
				break;
			case 97:
				strFieldName = "RnewFlag";
				break;
			case 98:
				strFieldName = "FamilyContNo";
				break;
			case 99:
				strFieldName = "BussFlag";
				break;
			case 100:
				strFieldName = "SignName";
				break;
			case 101:
				strFieldName = "OrganizeDate";
				break;
			case 102:
				strFieldName = "OrganizeTime";
				break;
			case 103:
				strFieldName = "NewAutoSendBankFlag";
				break;
			case 104:
				strFieldName = "AgentCodeOper";
				break;
			case 105:
				strFieldName = "AgentCodeAssi";
				break;
			case 106:
				strFieldName = "DelayReasonCode";
				break;
			case 107:
				strFieldName = "DelayReasonDesc";
				break;
			case 108:
				strFieldName = "XQremindflag";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
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
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntIDNo") ) {
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
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLocation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("ConsignNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LostTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Lang") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Dif") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputTime") ) {
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
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("FirstTrialOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SellType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewPayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FamilyContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrganizeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OrganizeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewAutoSendBankFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCodeOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCodeAssi") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DelayReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DelayReasonDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("XQremindflag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 45:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 53:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 54:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 57:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 71:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 74:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 75:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
