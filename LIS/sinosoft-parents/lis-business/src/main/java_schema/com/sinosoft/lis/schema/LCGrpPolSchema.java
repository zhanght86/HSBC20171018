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
import com.sinosoft.lis.db.LCGrpPolDB;

/*
 * <p>ClassName: LCGrpPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCGrpPolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpPolSchema.class);
	// @Field
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体投保单险种号码 */
	private String GrpProposalNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 险类编码 */
	private String KindCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 销售渠道 */
	private String SaleChnl;
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
	/** 客户号码 */
	private String CustomerNo;
	/** 地址号码 */
	private String AddressNo;
	/** 单位名称 */
	private String GrpName;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 交至日期 */
	private Date PaytoDate;
	/** 最后一次催收日期 */
	private Date RegetDate;
	/** 最后一次保全日期 */
	private Date LastEdorDate;
	/** 社保标记 */
	private String SSFlag;
	/** 封顶线 */
	private double PeakLine;
	/** 起付限 */
	private double GetLimit;
	/** 赔付比例 */
	private double GetRate;
	/** 分红比率 */
	private double BonusRate;
	/** 医疗费用限额 */
	private double MaxMedFee;
	/** 溢交处理方式 */
	private String OutPayFlag;
	/** 雇员自付比例 */
	private double EmployeeRate;
	/** 家属自付比例 */
	private double FamilyRate;
	/** 团体特殊业务标志 */
	private String SpecFlag;
	/** 预计人数 */
	private int ExpPeoples;
	/** 预计保费 */
	private double ExpPremium;
	/** 预计保额 */
	private double ExpAmnt;
	/** 交费方式 */
	private String PayMode;
	/** 管理费比例 */
	private double ManageFeeRate;
	/** 交费间隔 */
	private int PayIntv;
	/** 险种生效日期 */
	private Date CValiDate;
	/** 投保总人数 */
	private int Peoples2;
	/** 份数 */
	private double Mult;
	/** 保费 */
	private double Prem;
	/** 保额 */
	private double Amnt;
	/** 累计保费 */
	private double SumPrem;
	/** 累计交费 */
	private double SumPay;
	/** 差额 */
	private double Dif;
	/** 状态 */
	private String State;
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
	/** 备注 */
	private String Remark;
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
	/** 在职投保人数 */
	private int OnWorkPeoples;
	/** 退休投保人数 */
	private int OffWorkPeoples;
	/** 其它投保人数 */
	private int OtherPeoples;
	/** 连带投保人数 */
	private int RelaPeoples;
	/** 连带配偶投保人数 */
	private int RelaMatePeoples;
	/** 连带子女投保人数 */
	private int RelaYoungPeoples;
	/** 连带其它投保人数 */
	private int RelaOtherPeoples;
	/** 等待期 */
	private int WaitPeriod;
	/** 分红标志 */
	private String BonusFlag;
	/** 健康保险专项业务分类 */
	private String HealthInsurType;
	/** 归属标记 */
	private String AscriptionFlag;
	/** 初始费用比例 */
	private double InitRate;
	/** 健康保障委托管理业务分类 */
	private String HealthProType;
	/** 是否分保标记 */
	private String DistriFlag;
	/** 最初投保人数 */
	private int Peoples3;
	/** 摊回手续费比例 */
	private double FeeRate;
	/** 续保保单标记 */
	private String RenewFlag;
	/** 分保比例 */
	private double DistriRate;
	/** 固定收益比例 */
	private double FixprofitRate;
	/** 手续费比例 */
	private double ChargeFeeRate;
	/** 佣金比例 */
	private double CommRate;
	/** 续保标记 */
	private String StandbyFlag4;
	/** 币别 */
	private String Currency;
	/** 投连账户生效日标志 */
	private String UintLinkValiFlag;
	/** 续保次数 */
	private int RenewCount;
	/** 续保保单号 */
	private String RenewContNo;
	/** 期初人数 */
	private int InitNumPeople;
	/** 期初份数 */
	private double InitMult;
	/** 期初保额 */
	private double InitAmnt;
	/** 期初风险保额 */
	private double InitRiskAmnt;
	/** 期初保费 */
	private double InitPrem;
	/** 期初标准保费 */
	private double InitStandPrem;
	/** 当前风险保额 */
	private double RiskAmnt;
	/** 当前标准保费 */
	private double StandPrem;
	/** 累计投保人数 */
	private int SumNumPeople;
	/** 生效日期类型 */
	private String ValDateType;
	/** 终止日期 */
	private Date EndDate;
	/** 自动垫交标识 */
	private String AutoPayFlag;
	/** 减额交清标识 */
	private String SubFlag;
	/** 停止交费标识 */
	private String StopFlag;
	/** 不丧失价值选择 */
	private String KeepValueOpt;
	/** 语言 */
	private String Lang;
	/** 业务拓展费比例 */
	private double GradeFeeRate;
	/** 其他费用比例 */
	private double OtherFeeRate;
	/** 计价方式 */
	private String PricingMode;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 112;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpPolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpPolNo";

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
		LCGrpPolSchema cloned = (LCGrpPolSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		if(aGrpProposalNo!=null && aGrpProposalNo.length()>20)
			throw new IllegalArgumentException("集体投保单险种号码GrpProposalNo值"+aGrpProposalNo+"的长度"+aGrpProposalNo.length()+"大于最大值20");
		GrpProposalNo = aGrpProposalNo;
	}
	/**
	* 冗余，标准在集体保单表
	*/
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		if(aPrtNo!=null && aPrtNo.length()>20)
			throw new IllegalArgumentException("印刷号码PrtNo值"+aPrtNo+"的长度"+aPrtNo.length()+"大于最大值20");
		PrtNo = aPrtNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		if(aKindCode!=null && aKindCode.length()>3)
			throw new IllegalArgumentException("险类编码KindCode值"+aKindCode+"的长度"+aKindCode.length()+"大于最大值3");
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		if(aRiskVersion!=null && aRiskVersion.length()>8)
			throw new IllegalArgumentException("险种版本RiskVersion值"+aRiskVersion+"的长度"+aRiskVersion.length()+"大于最大值8");
		RiskVersion = aRiskVersion;
	}
	/**
	* 01-团险直销,02-个人营销,03-银行代理<p>
	* 04-兼业代理,05-专业代理,06-经纪公司<p>
	* 07-不计业绩销售渠道,99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		if(aSaleChnl!=null && aSaleChnl.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值2");
		SaleChnl = aSaleChnl;
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
	/**
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		if(aAgentCom!=null && aAgentCom.length()>20)
			throw new IllegalArgumentException("代理机构AgentCom值"+aAgentCom+"的长度"+aAgentCom.length()+"大于最大值20");
		AgentCom = aAgentCom;
	}
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		if(aAgentType!=null && aAgentType.length()>20)
			throw new IllegalArgumentException("代理机构内部分类AgentType值"+aAgentType+"的长度"+aAgentType.length()+"大于最大值20");
		AgentType = aAgentType;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		if(aAgentCode!=null && aAgentCode.length()>10)
			throw new IllegalArgumentException("代理人编码AgentCode值"+aAgentCode+"的长度"+aAgentCode.length()+"大于最大值10");
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		if(aAgentGroup!=null && aAgentGroup.length()>12)
			throw new IllegalArgumentException("代理人组别AgentGroup值"+aAgentGroup+"的长度"+aAgentGroup.length()+"大于最大值12");
		AgentGroup = aAgentGroup;
	}
	public String getAgentCode1()
	{
		return AgentCode1;
	}
	public void setAgentCode1(String aAgentCode1)
	{
		if(aAgentCode1!=null && aAgentCode1.length()>10)
			throw new IllegalArgumentException("联合代理人代码AgentCode1值"+aAgentCode1+"的长度"+aAgentCode1.length()+"大于最大值10");
		AgentCode1 = aAgentCode1;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getAddressNo()
	{
		return AddressNo;
	}
	public void setAddressNo(String aAddressNo)
	{
		if(aAddressNo!=null && aAddressNo.length()>20)
			throw new IllegalArgumentException("地址号码AddressNo值"+aAddressNo+"的长度"+aAddressNo.length()+"大于最大值20");
		AddressNo = aAddressNo;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>60)
			throw new IllegalArgumentException("单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值60");
		GrpName = aGrpName;
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

	public String getRegetDate()
	{
		if( RegetDate != null )
			return fDate.getString(RegetDate);
		else
			return null;
	}
	public void setRegetDate(Date aRegetDate)
	{
		RegetDate = aRegetDate;
	}
	public void setRegetDate(String aRegetDate)
	{
		if (aRegetDate != null && !aRegetDate.equals("") )
		{
			RegetDate = fDate.getDate( aRegetDate );
		}
		else
			RegetDate = null;
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

	/**
	* 0-非社保<p>
	* 1-参加社保
	*/
	public String getSSFlag()
	{
		return SSFlag;
	}
	public void setSSFlag(String aSSFlag)
	{
		if(aSSFlag!=null && aSSFlag.length()>1)
			throw new IllegalArgumentException("社保标记SSFlag值"+aSSFlag+"的长度"+aSSFlag.length()+"大于最大值1");
		SSFlag = aSSFlag;
	}
	public double getPeakLine()
	{
		return PeakLine;
	}
	public void setPeakLine(double aPeakLine)
	{
		PeakLine = aPeakLine;
	}
	public void setPeakLine(String aPeakLine)
	{
		if (aPeakLine != null && !aPeakLine.equals(""))
		{
			Double tDouble = new Double(aPeakLine);
			double d = tDouble.doubleValue();
			PeakLine = d;
		}
	}

	public double getGetLimit()
	{
		return GetLimit;
	}
	public void setGetLimit(double aGetLimit)
	{
		GetLimit = aGetLimit;
	}
	public void setGetLimit(String aGetLimit)
	{
		if (aGetLimit != null && !aGetLimit.equals(""))
		{
			Double tDouble = new Double(aGetLimit);
			double d = tDouble.doubleValue();
			GetLimit = d;
		}
	}

	public double getGetRate()
	{
		return GetRate;
	}
	public void setGetRate(double aGetRate)
	{
		GetRate = aGetRate;
	}
	public void setGetRate(String aGetRate)
	{
		if (aGetRate != null && !aGetRate.equals(""))
		{
			Double tDouble = new Double(aGetRate);
			double d = tDouble.doubleValue();
			GetRate = d;
		}
	}

	public double getBonusRate()
	{
		return BonusRate;
	}
	public void setBonusRate(double aBonusRate)
	{
		BonusRate = aBonusRate;
	}
	public void setBonusRate(String aBonusRate)
	{
		if (aBonusRate != null && !aBonusRate.equals(""))
		{
			Double tDouble = new Double(aBonusRate);
			double d = tDouble.doubleValue();
			BonusRate = d;
		}
	}

	public double getMaxMedFee()
	{
		return MaxMedFee;
	}
	public void setMaxMedFee(double aMaxMedFee)
	{
		MaxMedFee = aMaxMedFee;
	}
	public void setMaxMedFee(String aMaxMedFee)
	{
		if (aMaxMedFee != null && !aMaxMedFee.equals(""))
		{
			Double tDouble = new Double(aMaxMedFee);
			double d = tDouble.doubleValue();
			MaxMedFee = d;
		}
	}

	/**
	* 1---退费<p>
	* 2---充当续期保费
	*/
	public String getOutPayFlag()
	{
		return OutPayFlag;
	}
	public void setOutPayFlag(String aOutPayFlag)
	{
		if(aOutPayFlag!=null && aOutPayFlag.length()>1)
			throw new IllegalArgumentException("溢交处理方式OutPayFlag值"+aOutPayFlag+"的长度"+aOutPayFlag.length()+"大于最大值1");
		OutPayFlag = aOutPayFlag;
	}
	public double getEmployeeRate()
	{
		return EmployeeRate;
	}
	public void setEmployeeRate(double aEmployeeRate)
	{
		EmployeeRate = aEmployeeRate;
	}
	public void setEmployeeRate(String aEmployeeRate)
	{
		if (aEmployeeRate != null && !aEmployeeRate.equals(""))
		{
			Double tDouble = new Double(aEmployeeRate);
			double d = tDouble.doubleValue();
			EmployeeRate = d;
		}
	}

	public double getFamilyRate()
	{
		return FamilyRate;
	}
	public void setFamilyRate(double aFamilyRate)
	{
		FamilyRate = aFamilyRate;
	}
	public void setFamilyRate(String aFamilyRate)
	{
		if (aFamilyRate != null && !aFamilyRate.equals(""))
		{
			Double tDouble = new Double(aFamilyRate);
			double d = tDouble.doubleValue();
			FamilyRate = d;
		}
	}

	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		if(aSpecFlag!=null && aSpecFlag.length()>1)
			throw new IllegalArgumentException("团体特殊业务标志SpecFlag值"+aSpecFlag+"的长度"+aSpecFlag.length()+"大于最大值1");
		SpecFlag = aSpecFlag;
	}
	public int getExpPeoples()
	{
		return ExpPeoples;
	}
	public void setExpPeoples(int aExpPeoples)
	{
		ExpPeoples = aExpPeoples;
	}
	public void setExpPeoples(String aExpPeoples)
	{
		if (aExpPeoples != null && !aExpPeoples.equals(""))
		{
			Integer tInteger = new Integer(aExpPeoples);
			int i = tInteger.intValue();
			ExpPeoples = i;
		}
	}

	public double getExpPremium()
	{
		return ExpPremium;
	}
	public void setExpPremium(double aExpPremium)
	{
		ExpPremium = aExpPremium;
	}
	public void setExpPremium(String aExpPremium)
	{
		if (aExpPremium != null && !aExpPremium.equals(""))
		{
			Double tDouble = new Double(aExpPremium);
			double d = tDouble.doubleValue();
			ExpPremium = d;
		}
	}

	public double getExpAmnt()
	{
		return ExpAmnt;
	}
	public void setExpAmnt(double aExpAmnt)
	{
		ExpAmnt = aExpAmnt;
	}
	public void setExpAmnt(String aExpAmnt)
	{
		if (aExpAmnt != null && !aExpAmnt.equals(""))
		{
			Double tDouble = new Double(aExpAmnt);
			double d = tDouble.doubleValue();
			ExpAmnt = d;
		}
	}

	/**
	* 1	现金<p>
	* 2	现金支票<p>
	* 3	转账支票<p>
	* 4	银行转账<p>
	* 5	内部转帐<p>
	* 6	银行托收<p>
	* 7	其他
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		if(aPayMode!=null && aPayMode.length()>1)
			throw new IllegalArgumentException("交费方式PayMode值"+aPayMode+"的长度"+aPayMode.length()+"大于最大值1");
		PayMode = aPayMode;
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

	public int getPeoples2()
	{
		return Peoples2;
	}
	public void setPeoples2(int aPeoples2)
	{
		Peoples2 = aPeoples2;
	}
	public void setPeoples2(String aPeoples2)
	{
		if (aPeoples2 != null && !aPeoples2.equals(""))
		{
			Integer tInteger = new Integer(aPeoples2);
			int i = tInteger.intValue();
			Peoples2 = i;
		}
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

	public double getSumPay()
	{
		return SumPay;
	}
	public void setSumPay(double aSumPay)
	{
		SumPay = aSumPay;
	}
	public void setSumPay(String aSumPay)
	{
		if (aSumPay != null && !aSumPay.equals(""))
		{
			Double tDouble = new Double(aSumPay);
			double d = tDouble.doubleValue();
			SumPay = d;
		}
	}

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
	* [1-保障计划标记]<p>
	* [2]-计算方向<p>
	* 1-保费算保额<p>
	* 3-保额算保费)<p>
	* [3]-交费标记<p>
	* M－月，D－日，Y－年
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>10)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值10");
		State = aState;
	}
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		if(aApproveFlag!=null && aApproveFlag.length()>1)
			throw new IllegalArgumentException("复核状态ApproveFlag值"+aApproveFlag+"的长度"+aApproveFlag.length()+"大于最大值1");
		ApproveFlag = aApproveFlag;
	}
	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		if(aApproveCode!=null && aApproveCode.length()>10)
			throw new IllegalArgumentException("复核人编码ApproveCode值"+aApproveCode+"的长度"+aApproveCode.length()+"大于最大值10");
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
		if(aApproveTime!=null && aApproveTime.length()>8)
			throw new IllegalArgumentException("复核时间ApproveTime值"+aApproveTime+"的长度"+aApproveTime.length()+"大于最大值8");
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
		if(aUWFlag!=null && aUWFlag.length()>1)
			throw new IllegalArgumentException("核保状态UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值1");
		UWFlag = aUWFlag;
	}
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		if(aUWOperator!=null && aUWOperator.length()>10)
			throw new IllegalArgumentException("核保人UWOperator值"+aUWOperator+"的长度"+aUWOperator.length()+"大于最大值10");
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
		if(aUWTime!=null && aUWTime.length()>8)
			throw new IllegalArgumentException("核保完成时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值8");
		UWTime = aUWTime;
	}
	/**
	* 0－投保,1－承保,2-续保期间
	*/
	public String getAppFlag()
	{
		return AppFlag;
	}
	public void setAppFlag(String aAppFlag)
	{
		if(aAppFlag!=null && aAppFlag.length()>1)
			throw new IllegalArgumentException("投保单/保单标志AppFlag值"+aAppFlag+"的长度"+aAppFlag.length()+"大于最大值1");
		AppFlag = aAppFlag;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>255)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值255");
		Remark = aRemark;
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
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>10)
			throw new IllegalArgumentException("备用属性字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值10");
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
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>10)
			throw new IllegalArgumentException("备用属性字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值10");
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
		if(aStandbyFlag3!=null && aStandbyFlag3.length()>10)
			throw new IllegalArgumentException("备用属性字段3StandbyFlag3值"+aStandbyFlag3+"的长度"+aStandbyFlag3.length()+"大于最大值10");
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
	public int getOnWorkPeoples()
	{
		return OnWorkPeoples;
	}
	public void setOnWorkPeoples(int aOnWorkPeoples)
	{
		OnWorkPeoples = aOnWorkPeoples;
	}
	public void setOnWorkPeoples(String aOnWorkPeoples)
	{
		if (aOnWorkPeoples != null && !aOnWorkPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOnWorkPeoples);
			int i = tInteger.intValue();
			OnWorkPeoples = i;
		}
	}

	public int getOffWorkPeoples()
	{
		return OffWorkPeoples;
	}
	public void setOffWorkPeoples(int aOffWorkPeoples)
	{
		OffWorkPeoples = aOffWorkPeoples;
	}
	public void setOffWorkPeoples(String aOffWorkPeoples)
	{
		if (aOffWorkPeoples != null && !aOffWorkPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOffWorkPeoples);
			int i = tInteger.intValue();
			OffWorkPeoples = i;
		}
	}

	public int getOtherPeoples()
	{
		return OtherPeoples;
	}
	public void setOtherPeoples(int aOtherPeoples)
	{
		OtherPeoples = aOtherPeoples;
	}
	public void setOtherPeoples(String aOtherPeoples)
	{
		if (aOtherPeoples != null && !aOtherPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOtherPeoples);
			int i = tInteger.intValue();
			OtherPeoples = i;
		}
	}

	public int getRelaPeoples()
	{
		return RelaPeoples;
	}
	public void setRelaPeoples(int aRelaPeoples)
	{
		RelaPeoples = aRelaPeoples;
	}
	public void setRelaPeoples(String aRelaPeoples)
	{
		if (aRelaPeoples != null && !aRelaPeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaPeoples);
			int i = tInteger.intValue();
			RelaPeoples = i;
		}
	}

	public int getRelaMatePeoples()
	{
		return RelaMatePeoples;
	}
	public void setRelaMatePeoples(int aRelaMatePeoples)
	{
		RelaMatePeoples = aRelaMatePeoples;
	}
	public void setRelaMatePeoples(String aRelaMatePeoples)
	{
		if (aRelaMatePeoples != null && !aRelaMatePeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaMatePeoples);
			int i = tInteger.intValue();
			RelaMatePeoples = i;
		}
	}

	public int getRelaYoungPeoples()
	{
		return RelaYoungPeoples;
	}
	public void setRelaYoungPeoples(int aRelaYoungPeoples)
	{
		RelaYoungPeoples = aRelaYoungPeoples;
	}
	public void setRelaYoungPeoples(String aRelaYoungPeoples)
	{
		if (aRelaYoungPeoples != null && !aRelaYoungPeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaYoungPeoples);
			int i = tInteger.intValue();
			RelaYoungPeoples = i;
		}
	}

	public int getRelaOtherPeoples()
	{
		return RelaOtherPeoples;
	}
	public void setRelaOtherPeoples(int aRelaOtherPeoples)
	{
		RelaOtherPeoples = aRelaOtherPeoples;
	}
	public void setRelaOtherPeoples(String aRelaOtherPeoples)
	{
		if (aRelaOtherPeoples != null && !aRelaOtherPeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaOtherPeoples);
			int i = tInteger.intValue();
			RelaOtherPeoples = i;
		}
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

	public String getBonusFlag()
	{
		return BonusFlag;
	}
	public void setBonusFlag(String aBonusFlag)
	{
		if(aBonusFlag!=null && aBonusFlag.length()>1)
			throw new IllegalArgumentException("分红标志BonusFlag值"+aBonusFlag+"的长度"+aBonusFlag.length()+"大于最大值1");
		BonusFlag = aBonusFlag;
	}
	/**
	* 01-新农合<p>
	* 02-新农合补充<p>
	* 03-城镇职工基本医疗<p>
	* 04-城镇职工补充医疗<p>
	* 05-城镇居民基本医疗<p>
	* 06-城镇居民补充医疗<p>
	* 07-医疗救助<p>
	* 08-企事业团体补充医疗
	*/
	public String getHealthInsurType()
	{
		return HealthInsurType;
	}
	public void setHealthInsurType(String aHealthInsurType)
	{
		if(aHealthInsurType!=null && aHealthInsurType.length()>10)
			throw new IllegalArgumentException("健康保险专项业务分类HealthInsurType值"+aHealthInsurType+"的长度"+aHealthInsurType.length()+"大于最大值10");
		HealthInsurType = aHealthInsurType;
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
		if(aAscriptionFlag!=null && aAscriptionFlag.length()>10)
			throw new IllegalArgumentException("归属标记AscriptionFlag值"+aAscriptionFlag+"的长度"+aAscriptionFlag.length()+"大于最大值10");
		AscriptionFlag = aAscriptionFlag;
	}
	public double getInitRate()
	{
		return InitRate;
	}
	public void setInitRate(double aInitRate)
	{
		InitRate = aInitRate;
	}
	public void setInitRate(String aInitRate)
	{
		if (aInitRate != null && !aInitRate.equals(""))
		{
			Double tDouble = new Double(aInitRate);
			double d = tDouble.doubleValue();
			InitRate = d;
		}
	}

	/**
	* 01-新农合<p>
	*  02-新农合补充<p>
	*  03-城镇职工基本医疗<p>
	*  04-城镇职工补充医疗<p>
	*  05-城镇居民基本医疗<p>
	*  06-城镇居民补充医疗<p>
	*  07-医疗救助<p>
	*  08-企事业团体补充医疗<p>
	*  09-其它委托管理业务
	*/
	public String getHealthProType()
	{
		return HealthProType;
	}
	public void setHealthProType(String aHealthProType)
	{
		if(aHealthProType!=null && aHealthProType.length()>10)
			throw new IllegalArgumentException("健康保障委托管理业务分类HealthProType值"+aHealthProType+"的长度"+aHealthProType.length()+"大于最大值10");
		HealthProType = aHealthProType;
	}
	/**
	* 0-否<p>
	* 1－法定分保<p>
	* 2－商业分保
	*/
	public String getDistriFlag()
	{
		return DistriFlag;
	}
	public void setDistriFlag(String aDistriFlag)
	{
		if(aDistriFlag!=null && aDistriFlag.length()>10)
			throw new IllegalArgumentException("是否分保标记DistriFlag值"+aDistriFlag+"的长度"+aDistriFlag.length()+"大于最大值10");
		DistriFlag = aDistriFlag;
	}
	public int getPeoples3()
	{
		return Peoples3;
	}
	public void setPeoples3(int aPeoples3)
	{
		Peoples3 = aPeoples3;
	}
	public void setPeoples3(String aPeoples3)
	{
		if (aPeoples3 != null && !aPeoples3.equals(""))
		{
			Integer tInteger = new Integer(aPeoples3);
			int i = tInteger.intValue();
			Peoples3 = i;
		}
	}

	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}

	/**
	* 0-否<p>
	* 1－是
	*/
	public String getRenewFlag()
	{
		return RenewFlag;
	}
	public void setRenewFlag(String aRenewFlag)
	{
		if(aRenewFlag!=null && aRenewFlag.length()>10)
			throw new IllegalArgumentException("续保保单标记RenewFlag值"+aRenewFlag+"的长度"+aRenewFlag.length()+"大于最大值10");
		RenewFlag = aRenewFlag;
	}
	public double getDistriRate()
	{
		return DistriRate;
	}
	public void setDistriRate(double aDistriRate)
	{
		DistriRate = aDistriRate;
	}
	public void setDistriRate(String aDistriRate)
	{
		if (aDistriRate != null && !aDistriRate.equals(""))
		{
			Double tDouble = new Double(aDistriRate);
			double d = tDouble.doubleValue();
			DistriRate = d;
		}
	}

	public double getFixprofitRate()
	{
		return FixprofitRate;
	}
	public void setFixprofitRate(double aFixprofitRate)
	{
		FixprofitRate = aFixprofitRate;
	}
	public void setFixprofitRate(String aFixprofitRate)
	{
		if (aFixprofitRate != null && !aFixprofitRate.equals(""))
		{
			Double tDouble = new Double(aFixprofitRate);
			double d = tDouble.doubleValue();
			FixprofitRate = d;
		}
	}

	public double getChargeFeeRate()
	{
		return ChargeFeeRate;
	}
	public void setChargeFeeRate(double aChargeFeeRate)
	{
		ChargeFeeRate = aChargeFeeRate;
	}
	public void setChargeFeeRate(String aChargeFeeRate)
	{
		if (aChargeFeeRate != null && !aChargeFeeRate.equals(""))
		{
			Double tDouble = new Double(aChargeFeeRate);
			double d = tDouble.doubleValue();
			ChargeFeeRate = d;
		}
	}

	public double getCommRate()
	{
		return CommRate;
	}
	public void setCommRate(double aCommRate)
	{
		CommRate = aCommRate;
	}
	public void setCommRate(String aCommRate)
	{
		if (aCommRate != null && !aCommRate.equals(""))
		{
			Double tDouble = new Double(aCommRate);
			double d = tDouble.doubleValue();
			CommRate = d;
		}
	}

	public String getStandbyFlag4()
	{
		return StandbyFlag4;
	}
	public void setStandbyFlag4(String aStandbyFlag4)
	{
		if(aStandbyFlag4!=null && aStandbyFlag4.length()>10)
			throw new IllegalArgumentException("续保标记StandbyFlag4值"+aStandbyFlag4+"的长度"+aStandbyFlag4.length()+"大于最大值10");
		StandbyFlag4 = aStandbyFlag4;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	public String getUintLinkValiFlag()
	{
		return UintLinkValiFlag;
	}
	public void setUintLinkValiFlag(String aUintLinkValiFlag)
	{
		if(aUintLinkValiFlag!=null && aUintLinkValiFlag.length()>2)
			throw new IllegalArgumentException("投连账户生效日标志UintLinkValiFlag值"+aUintLinkValiFlag+"的长度"+aUintLinkValiFlag.length()+"大于最大值2");
		UintLinkValiFlag = aUintLinkValiFlag;
	}
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

	/**
	* 0-否，1-是
	*/
	public String getRenewContNo()
	{
		return RenewContNo;
	}
	public void setRenewContNo(String aRenewContNo)
	{
		if(aRenewContNo!=null && aRenewContNo.length()>20)
			throw new IllegalArgumentException("续保保单号RenewContNo值"+aRenewContNo+"的长度"+aRenewContNo.length()+"大于最大值20");
		RenewContNo = aRenewContNo;
	}
	public int getInitNumPeople()
	{
		return InitNumPeople;
	}
	public void setInitNumPeople(int aInitNumPeople)
	{
		InitNumPeople = aInitNumPeople;
	}
	public void setInitNumPeople(String aInitNumPeople)
	{
		if (aInitNumPeople != null && !aInitNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aInitNumPeople);
			int i = tInteger.intValue();
			InitNumPeople = i;
		}
	}

	public double getInitMult()
	{
		return InitMult;
	}
	public void setInitMult(double aInitMult)
	{
		InitMult = aInitMult;
	}
	public void setInitMult(String aInitMult)
	{
		if (aInitMult != null && !aInitMult.equals(""))
		{
			Double tDouble = new Double(aInitMult);
			double d = tDouble.doubleValue();
			InitMult = d;
		}
	}

	/**
	* 期初基本保额
	*/
	public double getInitAmnt()
	{
		return InitAmnt;
	}
	public void setInitAmnt(double aInitAmnt)
	{
		InitAmnt = aInitAmnt;
	}
	public void setInitAmnt(String aInitAmnt)
	{
		if (aInitAmnt != null && !aInitAmnt.equals(""))
		{
			Double tDouble = new Double(aInitAmnt);
			double d = tDouble.doubleValue();
			InitAmnt = d;
		}
	}

	public double getInitRiskAmnt()
	{
		return InitRiskAmnt;
	}
	public void setInitRiskAmnt(double aInitRiskAmnt)
	{
		InitRiskAmnt = aInitRiskAmnt;
	}
	public void setInitRiskAmnt(String aInitRiskAmnt)
	{
		if (aInitRiskAmnt != null && !aInitRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aInitRiskAmnt);
			double d = tDouble.doubleValue();
			InitRiskAmnt = d;
		}
	}

	public double getInitPrem()
	{
		return InitPrem;
	}
	public void setInitPrem(double aInitPrem)
	{
		InitPrem = aInitPrem;
	}
	public void setInitPrem(String aInitPrem)
	{
		if (aInitPrem != null && !aInitPrem.equals(""))
		{
			Double tDouble = new Double(aInitPrem);
			double d = tDouble.doubleValue();
			InitPrem = d;
		}
	}

	public double getInitStandPrem()
	{
		return InitStandPrem;
	}
	public void setInitStandPrem(double aInitStandPrem)
	{
		InitStandPrem = aInitStandPrem;
	}
	public void setInitStandPrem(String aInitStandPrem)
	{
		if (aInitStandPrem != null && !aInitStandPrem.equals(""))
		{
			Double tDouble = new Double(aInitStandPrem);
			double d = tDouble.doubleValue();
			InitStandPrem = d;
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

	public int getSumNumPeople()
	{
		return SumNumPeople;
	}
	public void setSumNumPeople(int aSumNumPeople)
	{
		SumNumPeople = aSumNumPeople;
	}
	public void setSumNumPeople(String aSumNumPeople)
	{
		if (aSumNumPeople != null && !aSumNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aSumNumPeople);
			int i = tInteger.intValue();
			SumNumPeople = i;
		}
	}

	public String getValDateType()
	{
		return ValDateType;
	}
	public void setValDateType(String aValDateType)
	{
		if(aValDateType!=null && aValDateType.length()>2)
			throw new IllegalArgumentException("生效日期类型ValDateType值"+aValDateType+"的长度"+aValDateType.length()+"大于最大值2");
		ValDateType = aValDateType;
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

	/**
	* 0-否，1-是
	*/
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		if(aAutoPayFlag!=null && aAutoPayFlag.length()>1)
			throw new IllegalArgumentException("自动垫交标识AutoPayFlag值"+aAutoPayFlag+"的长度"+aAutoPayFlag.length()+"大于最大值1");
		AutoPayFlag = aAutoPayFlag;
	}
	/**
	* 0-否，1-是
	*/
	public String getSubFlag()
	{
		return SubFlag;
	}
	public void setSubFlag(String aSubFlag)
	{
		if(aSubFlag!=null && aSubFlag.length()>1)
			throw new IllegalArgumentException("减额交清标识SubFlag值"+aSubFlag+"的长度"+aSubFlag.length()+"大于最大值1");
		SubFlag = aSubFlag;
	}
	/**
	* 0-否，1-是
	*/
	public String getStopFlag()
	{
		return StopFlag;
	}
	public void setStopFlag(String aStopFlag)
	{
		if(aStopFlag!=null && aStopFlag.length()>1)
			throw new IllegalArgumentException("停止交费标识StopFlag值"+aStopFlag+"的长度"+aStopFlag.length()+"大于最大值1");
		StopFlag = aStopFlag;
	}
	/**
	* 0-否，1-是
	*/
	public String getKeepValueOpt()
	{
		return KeepValueOpt;
	}
	public void setKeepValueOpt(String aKeepValueOpt)
	{
		if(aKeepValueOpt!=null && aKeepValueOpt.length()>1)
			throw new IllegalArgumentException("不丧失价值选择KeepValueOpt值"+aKeepValueOpt+"的长度"+aKeepValueOpt.length()+"大于最大值1");
		KeepValueOpt = aKeepValueOpt;
	}
	public String getLang()
	{
		return Lang;
	}
	public void setLang(String aLang)
	{
		if(aLang!=null && aLang.length()>10)
			throw new IllegalArgumentException("语言Lang值"+aLang+"的长度"+aLang.length()+"大于最大值10");
		Lang = aLang;
	}
	public double getGradeFeeRate()
	{
		return GradeFeeRate;
	}
	public void setGradeFeeRate(double aGradeFeeRate)
	{
		GradeFeeRate = aGradeFeeRate;
	}
	public void setGradeFeeRate(String aGradeFeeRate)
	{
		if (aGradeFeeRate != null && !aGradeFeeRate.equals(""))
		{
			Double tDouble = new Double(aGradeFeeRate);
			double d = tDouble.doubleValue();
			GradeFeeRate = d;
		}
	}

	public double getOtherFeeRate()
	{
		return OtherFeeRate;
	}
	public void setOtherFeeRate(double aOtherFeeRate)
	{
		OtherFeeRate = aOtherFeeRate;
	}
	public void setOtherFeeRate(String aOtherFeeRate)
	{
		if (aOtherFeeRate != null && !aOtherFeeRate.equals(""))
		{
			Double tDouble = new Double(aOtherFeeRate);
			double d = tDouble.doubleValue();
			OtherFeeRate = d;
		}
	}

	public String getPricingMode()
	{
		return PricingMode;
	}
	public void setPricingMode(String aPricingMode)
	{
		if(aPricingMode!=null && aPricingMode.length()>10)
			throw new IllegalArgumentException("计价方式PricingMode值"+aPricingMode+"的长度"+aPricingMode.length()+"大于最大值10");
		PricingMode = aPricingMode;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
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
	* 使用另外一个 LCGrpPolSchema 对象给 Schema 赋值
	* @param: aLCGrpPolSchema LCGrpPolSchema
	**/
	public void setSchema(LCGrpPolSchema aLCGrpPolSchema)
	{
		this.GrpPolNo = aLCGrpPolSchema.getGrpPolNo();
		this.GrpContNo = aLCGrpPolSchema.getGrpContNo();
		this.GrpProposalNo = aLCGrpPolSchema.getGrpProposalNo();
		this.PrtNo = aLCGrpPolSchema.getPrtNo();
		this.KindCode = aLCGrpPolSchema.getKindCode();
		this.RiskCode = aLCGrpPolSchema.getRiskCode();
		this.RiskVersion = aLCGrpPolSchema.getRiskVersion();
		this.SaleChnl = aLCGrpPolSchema.getSaleChnl();
		this.ManageCom = aLCGrpPolSchema.getManageCom();
		this.AgentCom = aLCGrpPolSchema.getAgentCom();
		this.AgentType = aLCGrpPolSchema.getAgentType();
		this.AgentCode = aLCGrpPolSchema.getAgentCode();
		this.AgentGroup = aLCGrpPolSchema.getAgentGroup();
		this.AgentCode1 = aLCGrpPolSchema.getAgentCode1();
		this.CustomerNo = aLCGrpPolSchema.getCustomerNo();
		this.AddressNo = aLCGrpPolSchema.getAddressNo();
		this.GrpName = aLCGrpPolSchema.getGrpName();
		this.FirstPayDate = fDate.getDate( aLCGrpPolSchema.getFirstPayDate());
		this.PayEndDate = fDate.getDate( aLCGrpPolSchema.getPayEndDate());
		this.PaytoDate = fDate.getDate( aLCGrpPolSchema.getPaytoDate());
		this.RegetDate = fDate.getDate( aLCGrpPolSchema.getRegetDate());
		this.LastEdorDate = fDate.getDate( aLCGrpPolSchema.getLastEdorDate());
		this.SSFlag = aLCGrpPolSchema.getSSFlag();
		this.PeakLine = aLCGrpPolSchema.getPeakLine();
		this.GetLimit = aLCGrpPolSchema.getGetLimit();
		this.GetRate = aLCGrpPolSchema.getGetRate();
		this.BonusRate = aLCGrpPolSchema.getBonusRate();
		this.MaxMedFee = aLCGrpPolSchema.getMaxMedFee();
		this.OutPayFlag = aLCGrpPolSchema.getOutPayFlag();
		this.EmployeeRate = aLCGrpPolSchema.getEmployeeRate();
		this.FamilyRate = aLCGrpPolSchema.getFamilyRate();
		this.SpecFlag = aLCGrpPolSchema.getSpecFlag();
		this.ExpPeoples = aLCGrpPolSchema.getExpPeoples();
		this.ExpPremium = aLCGrpPolSchema.getExpPremium();
		this.ExpAmnt = aLCGrpPolSchema.getExpAmnt();
		this.PayMode = aLCGrpPolSchema.getPayMode();
		this.ManageFeeRate = aLCGrpPolSchema.getManageFeeRate();
		this.PayIntv = aLCGrpPolSchema.getPayIntv();
		this.CValiDate = fDate.getDate( aLCGrpPolSchema.getCValiDate());
		this.Peoples2 = aLCGrpPolSchema.getPeoples2();
		this.Mult = aLCGrpPolSchema.getMult();
		this.Prem = aLCGrpPolSchema.getPrem();
		this.Amnt = aLCGrpPolSchema.getAmnt();
		this.SumPrem = aLCGrpPolSchema.getSumPrem();
		this.SumPay = aLCGrpPolSchema.getSumPay();
		this.Dif = aLCGrpPolSchema.getDif();
		this.State = aLCGrpPolSchema.getState();
		this.ApproveFlag = aLCGrpPolSchema.getApproveFlag();
		this.ApproveCode = aLCGrpPolSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLCGrpPolSchema.getApproveDate());
		this.ApproveTime = aLCGrpPolSchema.getApproveTime();
		this.UWFlag = aLCGrpPolSchema.getUWFlag();
		this.UWOperator = aLCGrpPolSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLCGrpPolSchema.getUWDate());
		this.UWTime = aLCGrpPolSchema.getUWTime();
		this.AppFlag = aLCGrpPolSchema.getAppFlag();
		this.Remark = aLCGrpPolSchema.getRemark();
		this.StandbyFlag1 = aLCGrpPolSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCGrpPolSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLCGrpPolSchema.getStandbyFlag3();
		this.Operator = aLCGrpPolSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpPolSchema.getMakeDate());
		this.MakeTime = aLCGrpPolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpPolSchema.getModifyDate());
		this.ModifyTime = aLCGrpPolSchema.getModifyTime();
		this.OnWorkPeoples = aLCGrpPolSchema.getOnWorkPeoples();
		this.OffWorkPeoples = aLCGrpPolSchema.getOffWorkPeoples();
		this.OtherPeoples = aLCGrpPolSchema.getOtherPeoples();
		this.RelaPeoples = aLCGrpPolSchema.getRelaPeoples();
		this.RelaMatePeoples = aLCGrpPolSchema.getRelaMatePeoples();
		this.RelaYoungPeoples = aLCGrpPolSchema.getRelaYoungPeoples();
		this.RelaOtherPeoples = aLCGrpPolSchema.getRelaOtherPeoples();
		this.WaitPeriod = aLCGrpPolSchema.getWaitPeriod();
		this.BonusFlag = aLCGrpPolSchema.getBonusFlag();
		this.HealthInsurType = aLCGrpPolSchema.getHealthInsurType();
		this.AscriptionFlag = aLCGrpPolSchema.getAscriptionFlag();
		this.InitRate = aLCGrpPolSchema.getInitRate();
		this.HealthProType = aLCGrpPolSchema.getHealthProType();
		this.DistriFlag = aLCGrpPolSchema.getDistriFlag();
		this.Peoples3 = aLCGrpPolSchema.getPeoples3();
		this.FeeRate = aLCGrpPolSchema.getFeeRate();
		this.RenewFlag = aLCGrpPolSchema.getRenewFlag();
		this.DistriRate = aLCGrpPolSchema.getDistriRate();
		this.FixprofitRate = aLCGrpPolSchema.getFixprofitRate();
		this.ChargeFeeRate = aLCGrpPolSchema.getChargeFeeRate();
		this.CommRate = aLCGrpPolSchema.getCommRate();
		this.StandbyFlag4 = aLCGrpPolSchema.getStandbyFlag4();
		this.Currency = aLCGrpPolSchema.getCurrency();
		this.UintLinkValiFlag = aLCGrpPolSchema.getUintLinkValiFlag();
		this.RenewCount = aLCGrpPolSchema.getRenewCount();
		this.RenewContNo = aLCGrpPolSchema.getRenewContNo();
		this.InitNumPeople = aLCGrpPolSchema.getInitNumPeople();
		this.InitMult = aLCGrpPolSchema.getInitMult();
		this.InitAmnt = aLCGrpPolSchema.getInitAmnt();
		this.InitRiskAmnt = aLCGrpPolSchema.getInitRiskAmnt();
		this.InitPrem = aLCGrpPolSchema.getInitPrem();
		this.InitStandPrem = aLCGrpPolSchema.getInitStandPrem();
		this.RiskAmnt = aLCGrpPolSchema.getRiskAmnt();
		this.StandPrem = aLCGrpPolSchema.getStandPrem();
		this.SumNumPeople = aLCGrpPolSchema.getSumNumPeople();
		this.ValDateType = aLCGrpPolSchema.getValDateType();
		this.EndDate = fDate.getDate( aLCGrpPolSchema.getEndDate());
		this.AutoPayFlag = aLCGrpPolSchema.getAutoPayFlag();
		this.SubFlag = aLCGrpPolSchema.getSubFlag();
		this.StopFlag = aLCGrpPolSchema.getStopFlag();
		this.KeepValueOpt = aLCGrpPolSchema.getKeepValueOpt();
		this.Lang = aLCGrpPolSchema.getLang();
		this.GradeFeeRate = aLCGrpPolSchema.getGradeFeeRate();
		this.OtherFeeRate = aLCGrpPolSchema.getOtherFeeRate();
		this.PricingMode = aLCGrpPolSchema.getPricingMode();
		this.ComCode = aLCGrpPolSchema.getComCode();
		this.ModifyOperator = aLCGrpPolSchema.getModifyOperator();
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
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

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

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

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

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.RegetDate = rs.getDate("RegetDate");
			this.LastEdorDate = rs.getDate("LastEdorDate");
			if( rs.getString("SSFlag") == null )
				this.SSFlag = null;
			else
				this.SSFlag = rs.getString("SSFlag").trim();

			this.PeakLine = rs.getDouble("PeakLine");
			this.GetLimit = rs.getDouble("GetLimit");
			this.GetRate = rs.getDouble("GetRate");
			this.BonusRate = rs.getDouble("BonusRate");
			this.MaxMedFee = rs.getDouble("MaxMedFee");
			if( rs.getString("OutPayFlag") == null )
				this.OutPayFlag = null;
			else
				this.OutPayFlag = rs.getString("OutPayFlag").trim();

			this.EmployeeRate = rs.getDouble("EmployeeRate");
			this.FamilyRate = rs.getDouble("FamilyRate");
			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			this.ExpPeoples = rs.getInt("ExpPeoples");
			this.ExpPremium = rs.getDouble("ExpPremium");
			this.ExpAmnt = rs.getDouble("ExpAmnt");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			this.PayIntv = rs.getInt("PayIntv");
			this.CValiDate = rs.getDate("CValiDate");
			this.Peoples2 = rs.getInt("Peoples2");
			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.SumPrem = rs.getDouble("SumPrem");
			this.SumPay = rs.getDouble("SumPay");
			this.Dif = rs.getDouble("Dif");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			this.OnWorkPeoples = rs.getInt("OnWorkPeoples");
			this.OffWorkPeoples = rs.getInt("OffWorkPeoples");
			this.OtherPeoples = rs.getInt("OtherPeoples");
			this.RelaPeoples = rs.getInt("RelaPeoples");
			this.RelaMatePeoples = rs.getInt("RelaMatePeoples");
			this.RelaYoungPeoples = rs.getInt("RelaYoungPeoples");
			this.RelaOtherPeoples = rs.getInt("RelaOtherPeoples");
			this.WaitPeriod = rs.getInt("WaitPeriod");
			if( rs.getString("BonusFlag") == null )
				this.BonusFlag = null;
			else
				this.BonusFlag = rs.getString("BonusFlag").trim();

			if( rs.getString("HealthInsurType") == null )
				this.HealthInsurType = null;
			else
				this.HealthInsurType = rs.getString("HealthInsurType").trim();

			if( rs.getString("AscriptionFlag") == null )
				this.AscriptionFlag = null;
			else
				this.AscriptionFlag = rs.getString("AscriptionFlag").trim();

			this.InitRate = rs.getDouble("InitRate");
			if( rs.getString("HealthProType") == null )
				this.HealthProType = null;
			else
				this.HealthProType = rs.getString("HealthProType").trim();

			if( rs.getString("DistriFlag") == null )
				this.DistriFlag = null;
			else
				this.DistriFlag = rs.getString("DistriFlag").trim();

			this.Peoples3 = rs.getInt("Peoples3");
			this.FeeRate = rs.getDouble("FeeRate");
			if( rs.getString("RenewFlag") == null )
				this.RenewFlag = null;
			else
				this.RenewFlag = rs.getString("RenewFlag").trim();

			this.DistriRate = rs.getDouble("DistriRate");
			this.FixprofitRate = rs.getDouble("FixprofitRate");
			this.ChargeFeeRate = rs.getDouble("ChargeFeeRate");
			this.CommRate = rs.getDouble("CommRate");
			if( rs.getString("StandbyFlag4") == null )
				this.StandbyFlag4 = null;
			else
				this.StandbyFlag4 = rs.getString("StandbyFlag4").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("UintLinkValiFlag") == null )
				this.UintLinkValiFlag = null;
			else
				this.UintLinkValiFlag = rs.getString("UintLinkValiFlag").trim();

			this.RenewCount = rs.getInt("RenewCount");
			if( rs.getString("RenewContNo") == null )
				this.RenewContNo = null;
			else
				this.RenewContNo = rs.getString("RenewContNo").trim();

			this.InitNumPeople = rs.getInt("InitNumPeople");
			this.InitMult = rs.getDouble("InitMult");
			this.InitAmnt = rs.getDouble("InitAmnt");
			this.InitRiskAmnt = rs.getDouble("InitRiskAmnt");
			this.InitPrem = rs.getDouble("InitPrem");
			this.InitStandPrem = rs.getDouble("InitStandPrem");
			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.StandPrem = rs.getDouble("StandPrem");
			this.SumNumPeople = rs.getInt("SumNumPeople");
			if( rs.getString("ValDateType") == null )
				this.ValDateType = null;
			else
				this.ValDateType = rs.getString("ValDateType").trim();

			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			if( rs.getString("SubFlag") == null )
				this.SubFlag = null;
			else
				this.SubFlag = rs.getString("SubFlag").trim();

			if( rs.getString("StopFlag") == null )
				this.StopFlag = null;
			else
				this.StopFlag = rs.getString("StopFlag").trim();

			if( rs.getString("KeepValueOpt") == null )
				this.KeepValueOpt = null;
			else
				this.KeepValueOpt = rs.getString("KeepValueOpt").trim();

			if( rs.getString("Lang") == null )
				this.Lang = null;
			else
				this.Lang = rs.getString("Lang").trim();

			this.GradeFeeRate = rs.getDouble("GradeFeeRate");
			this.OtherFeeRate = rs.getDouble("OtherFeeRate");
			if( rs.getString("PricingMode") == null )
				this.PricingMode = null;
			else
				this.PricingMode = rs.getString("PricingMode").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpPol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpPolSchema getSchema()
	{
		LCGrpPolSchema aLCGrpPolSchema = new LCGrpPolSchema();
		aLCGrpPolSchema.setSchema(this);
		return aLCGrpPolSchema;
	}

	public LCGrpPolDB getDB()
	{
		LCGrpPolDB aDBOper = new LCGrpPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpPol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RegetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastEdorDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SSFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PeakLine));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxMedFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EmployeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FamilyRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPremium));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Dif));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnWorkPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OffWorkPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaMatePeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaYoungPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaOtherPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WaitPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthInsurType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthProType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DistriFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DistriRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FixprofitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChargeFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UintLinkValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RenewCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitMult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitRiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitStandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValDateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StopFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeepValueOpt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Lang)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GradeFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PricingMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			RegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			SSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PeakLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			BonusRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			MaxMedFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			EmployeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			FamilyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ExpPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).intValue();
			ExpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			ExpAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).intValue();
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			Peoples2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			OnWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,66,SysConst.PACKAGESPILTER))).intValue();
			OffWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).intValue();
			OtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,68,SysConst.PACKAGESPILTER))).intValue();
			RelaPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).intValue();
			RelaMatePeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).intValue();
			RelaYoungPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).intValue();
			RelaOtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).intValue();
			WaitPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,73,SysConst.PACKAGESPILTER))).intValue();
			BonusFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			HealthInsurType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			AscriptionFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			InitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).doubleValue();
			HealthProType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			DistriFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			Peoples3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).intValue();
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).doubleValue();
			RenewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			DistriRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,83,SysConst.PACKAGESPILTER))).doubleValue();
			FixprofitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,84,SysConst.PACKAGESPILTER))).doubleValue();
			ChargeFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,85,SysConst.PACKAGESPILTER))).doubleValue();
			CommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,86,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			UintLinkValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			RenewCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,90,SysConst.PACKAGESPILTER))).intValue();
			RenewContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			InitNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,92,SysConst.PACKAGESPILTER))).intValue();
			InitMult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,93,SysConst.PACKAGESPILTER))).doubleValue();
			InitAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,94,SysConst.PACKAGESPILTER))).doubleValue();
			InitRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,95,SysConst.PACKAGESPILTER))).doubleValue();
			InitPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,96,SysConst.PACKAGESPILTER))).doubleValue();
			InitStandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,97,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,98,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,99,SysConst.PACKAGESPILTER))).doubleValue();
			SumNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,100,SysConst.PACKAGESPILTER))).intValue();
			ValDateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102,SysConst.PACKAGESPILTER));
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			SubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			StopFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			KeepValueOpt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			GradeFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,108,SysConst.PACKAGESPILTER))).doubleValue();
			OtherFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,109,SysConst.PACKAGESPILTER))).doubleValue();
			PricingMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
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
		if (FCode.equalsIgnoreCase("RegetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
		}
		if (FCode.equalsIgnoreCase("SSFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SSFlag));
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeakLine));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusRate));
		}
		if (FCode.equalsIgnoreCase("MaxMedFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxMedFee));
		}
		if (FCode.equalsIgnoreCase("OutPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayFlag));
		}
		if (FCode.equalsIgnoreCase("EmployeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmployeeRate));
		}
		if (FCode.equalsIgnoreCase("FamilyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyRate));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("ExpPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpPeoples));
		}
		if (FCode.equalsIgnoreCase("ExpPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpPremium));
		}
		if (FCode.equalsIgnoreCase("ExpAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpAmnt));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples2));
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
		if (FCode.equalsIgnoreCase("SumPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPay));
		}
		if (FCode.equalsIgnoreCase("Dif"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dif));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("OnWorkPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnWorkPeoples));
		}
		if (FCode.equalsIgnoreCase("OffWorkPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OffWorkPeoples));
		}
		if (FCode.equalsIgnoreCase("OtherPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherPeoples));
		}
		if (FCode.equalsIgnoreCase("RelaPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaPeoples));
		}
		if (FCode.equalsIgnoreCase("RelaMatePeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaMatePeoples));
		}
		if (FCode.equalsIgnoreCase("RelaYoungPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaYoungPeoples));
		}
		if (FCode.equalsIgnoreCase("RelaOtherPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaOtherPeoples));
		}
		if (FCode.equalsIgnoreCase("WaitPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WaitPeriod));
		}
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusFlag));
		}
		if (FCode.equalsIgnoreCase("HealthInsurType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthInsurType));
		}
		if (FCode.equalsIgnoreCase("AscriptionFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionFlag));
		}
		if (FCode.equalsIgnoreCase("InitRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitRate));
		}
		if (FCode.equalsIgnoreCase("HealthProType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthProType));
		}
		if (FCode.equalsIgnoreCase("DistriFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistriFlag));
		}
		if (FCode.equalsIgnoreCase("Peoples3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples3));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
		}
		if (FCode.equalsIgnoreCase("RenewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewFlag));
		}
		if (FCode.equalsIgnoreCase("DistriRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistriRate));
		}
		if (FCode.equalsIgnoreCase("FixprofitRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FixprofitRate));
		}
		if (FCode.equalsIgnoreCase("ChargeFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeFeeRate));
		}
		if (FCode.equalsIgnoreCase("CommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommRate));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag4));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("UintLinkValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UintLinkValiFlag));
		}
		if (FCode.equalsIgnoreCase("RenewCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewCount));
		}
		if (FCode.equalsIgnoreCase("RenewContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewContNo));
		}
		if (FCode.equalsIgnoreCase("InitNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitNumPeople));
		}
		if (FCode.equalsIgnoreCase("InitMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitMult));
		}
		if (FCode.equalsIgnoreCase("InitAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitAmnt));
		}
		if (FCode.equalsIgnoreCase("InitRiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitRiskAmnt));
		}
		if (FCode.equalsIgnoreCase("InitPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitPrem));
		}
		if (FCode.equalsIgnoreCase("InitStandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitStandPrem));
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmnt));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumNumPeople));
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValDateType));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("SubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFlag));
		}
		if (FCode.equalsIgnoreCase("StopFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StopFlag));
		}
		if (FCode.equalsIgnoreCase("KeepValueOpt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeepValueOpt));
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Lang));
		}
		if (FCode.equalsIgnoreCase("GradeFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GradeFeeRate));
		}
		if (FCode.equalsIgnoreCase("OtherFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherFeeRate));
		}
		if (FCode.equalsIgnoreCase("PricingMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PricingMode));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(SSFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(PeakLine);
				break;
			case 24:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 25:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 26:
				strFieldValue = String.valueOf(BonusRate);
				break;
			case 27:
				strFieldValue = String.valueOf(MaxMedFee);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 29:
				strFieldValue = String.valueOf(EmployeeRate);
				break;
			case 30:
				strFieldValue = String.valueOf(FamilyRate);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 32:
				strFieldValue = String.valueOf(ExpPeoples);
				break;
			case 33:
				strFieldValue = String.valueOf(ExpPremium);
				break;
			case 34:
				strFieldValue = String.valueOf(ExpAmnt);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 36:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 37:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 39:
				strFieldValue = String.valueOf(Peoples2);
				break;
			case 40:
				strFieldValue = String.valueOf(Mult);
				break;
			case 41:
				strFieldValue = String.valueOf(Prem);
				break;
			case 42:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 43:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 44:
				strFieldValue = String.valueOf(SumPay);
				break;
			case 45:
				strFieldValue = String.valueOf(Dif);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 65:
				strFieldValue = String.valueOf(OnWorkPeoples);
				break;
			case 66:
				strFieldValue = String.valueOf(OffWorkPeoples);
				break;
			case 67:
				strFieldValue = String.valueOf(OtherPeoples);
				break;
			case 68:
				strFieldValue = String.valueOf(RelaPeoples);
				break;
			case 69:
				strFieldValue = String.valueOf(RelaMatePeoples);
				break;
			case 70:
				strFieldValue = String.valueOf(RelaYoungPeoples);
				break;
			case 71:
				strFieldValue = String.valueOf(RelaOtherPeoples);
				break;
			case 72:
				strFieldValue = String.valueOf(WaitPeriod);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(BonusFlag);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(HealthInsurType);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(AscriptionFlag);
				break;
			case 76:
				strFieldValue = String.valueOf(InitRate);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(HealthProType);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(DistriFlag);
				break;
			case 79:
				strFieldValue = String.valueOf(Peoples3);
				break;
			case 80:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(RenewFlag);
				break;
			case 82:
				strFieldValue = String.valueOf(DistriRate);
				break;
			case 83:
				strFieldValue = String.valueOf(FixprofitRate);
				break;
			case 84:
				strFieldValue = String.valueOf(ChargeFeeRate);
				break;
			case 85:
				strFieldValue = String.valueOf(CommRate);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag4);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(UintLinkValiFlag);
				break;
			case 89:
				strFieldValue = String.valueOf(RenewCount);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(RenewContNo);
				break;
			case 91:
				strFieldValue = String.valueOf(InitNumPeople);
				break;
			case 92:
				strFieldValue = String.valueOf(InitMult);
				break;
			case 93:
				strFieldValue = String.valueOf(InitAmnt);
				break;
			case 94:
				strFieldValue = String.valueOf(InitRiskAmnt);
				break;
			case 95:
				strFieldValue = String.valueOf(InitPrem);
				break;
			case 96:
				strFieldValue = String.valueOf(InitStandPrem);
				break;
			case 97:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 98:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 99:
				strFieldValue = String.valueOf(SumNumPeople);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(ValDateType);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(SubFlag);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(StopFlag);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(KeepValueOpt);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 107:
				strFieldValue = String.valueOf(GradeFeeRate);
				break;
			case 108:
				strFieldValue = String.valueOf(OtherFeeRate);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(PricingMode);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 111:
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

		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpProposalNo = FValue.trim();
			}
			else
				GrpProposalNo = null;
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddressNo = FValue.trim();
			}
			else
				AddressNo = null;
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
		if (FCode.equalsIgnoreCase("RegetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RegetDate = fDate.getDate( FValue );
			}
			else
				RegetDate = null;
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
		if (FCode.equalsIgnoreCase("SSFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SSFlag = FValue.trim();
			}
			else
				SSFlag = null;
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PeakLine = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxMedFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxMedFee = d;
			}
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
		if (FCode.equalsIgnoreCase("EmployeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EmployeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FamilyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FamilyRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecFlag = FValue.trim();
			}
			else
				SpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExpPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ExpPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("ExpPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpPremium = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpAmnt = d;
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
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ManageFeeRate = d;
			}
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples2 = i;
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
		if (FCode.equalsIgnoreCase("SumPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPay = d;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("OnWorkPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnWorkPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OffWorkPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OffWorkPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OtherPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaMatePeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaMatePeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaYoungPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaYoungPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaOtherPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaOtherPeoples = i;
			}
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
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusFlag = FValue.trim();
			}
			else
				BonusFlag = null;
		}
		if (FCode.equalsIgnoreCase("HealthInsurType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthInsurType = FValue.trim();
			}
			else
				HealthInsurType = null;
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
		if (FCode.equalsIgnoreCase("InitRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("HealthProType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthProType = FValue.trim();
			}
			else
				HealthProType = null;
		}
		if (FCode.equalsIgnoreCase("DistriFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DistriFlag = FValue.trim();
			}
			else
				DistriFlag = null;
		}
		if (FCode.equalsIgnoreCase("Peoples3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RenewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewFlag = FValue.trim();
			}
			else
				RenewFlag = null;
		}
		if (FCode.equalsIgnoreCase("DistriRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DistriRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FixprofitRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FixprofitRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChargeFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChargeFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag4 = FValue.trim();
			}
			else
				StandbyFlag4 = null;
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
		if (FCode.equalsIgnoreCase("UintLinkValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UintLinkValiFlag = FValue.trim();
			}
			else
				UintLinkValiFlag = null;
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
		if (FCode.equalsIgnoreCase("RenewContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewContNo = FValue.trim();
			}
			else
				RenewContNo = null;
		}
		if (FCode.equalsIgnoreCase("InitNumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InitNumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("InitMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitMult = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitRiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitRiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitStandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitStandPrem = d;
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
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumNumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValDateType = FValue.trim();
			}
			else
				ValDateType = null;
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
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayFlag = FValue.trim();
			}
			else
				AutoPayFlag = null;
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
		if (FCode.equalsIgnoreCase("StopFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StopFlag = FValue.trim();
			}
			else
				StopFlag = null;
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
		if (FCode.equalsIgnoreCase("Lang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Lang = FValue.trim();
			}
			else
				Lang = null;
		}
		if (FCode.equalsIgnoreCase("GradeFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GradeFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OtherFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OtherFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PricingMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PricingMode = FValue.trim();
			}
			else
				PricingMode = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		LCGrpPolSchema other = (LCGrpPolSchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& PrtNo.equals(other.getPrtNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AddressNo.equals(other.getAddressNo())
			&& GrpName.equals(other.getGrpName())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(RegetDate).equals(other.getRegetDate())
			&& fDate.getString(LastEdorDate).equals(other.getLastEdorDate())
			&& SSFlag.equals(other.getSSFlag())
			&& PeakLine == other.getPeakLine()
			&& GetLimit == other.getGetLimit()
			&& GetRate == other.getGetRate()
			&& BonusRate == other.getBonusRate()
			&& MaxMedFee == other.getMaxMedFee()
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& EmployeeRate == other.getEmployeeRate()
			&& FamilyRate == other.getFamilyRate()
			&& SpecFlag.equals(other.getSpecFlag())
			&& ExpPeoples == other.getExpPeoples()
			&& ExpPremium == other.getExpPremium()
			&& ExpAmnt == other.getExpAmnt()
			&& PayMode.equals(other.getPayMode())
			&& ManageFeeRate == other.getManageFeeRate()
			&& PayIntv == other.getPayIntv()
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& Peoples2 == other.getPeoples2()
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& SumPrem == other.getSumPrem()
			&& SumPay == other.getSumPay()
			&& Dif == other.getDif()
			&& State.equals(other.getState())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& AppFlag.equals(other.getAppFlag())
			&& Remark.equals(other.getRemark())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& OnWorkPeoples == other.getOnWorkPeoples()
			&& OffWorkPeoples == other.getOffWorkPeoples()
			&& OtherPeoples == other.getOtherPeoples()
			&& RelaPeoples == other.getRelaPeoples()
			&& RelaMatePeoples == other.getRelaMatePeoples()
			&& RelaYoungPeoples == other.getRelaYoungPeoples()
			&& RelaOtherPeoples == other.getRelaOtherPeoples()
			&& WaitPeriod == other.getWaitPeriod()
			&& BonusFlag.equals(other.getBonusFlag())
			&& HealthInsurType.equals(other.getHealthInsurType())
			&& AscriptionFlag.equals(other.getAscriptionFlag())
			&& InitRate == other.getInitRate()
			&& HealthProType.equals(other.getHealthProType())
			&& DistriFlag.equals(other.getDistriFlag())
			&& Peoples3 == other.getPeoples3()
			&& FeeRate == other.getFeeRate()
			&& RenewFlag.equals(other.getRenewFlag())
			&& DistriRate == other.getDistriRate()
			&& FixprofitRate == other.getFixprofitRate()
			&& ChargeFeeRate == other.getChargeFeeRate()
			&& CommRate == other.getCommRate()
			&& StandbyFlag4.equals(other.getStandbyFlag4())
			&& Currency.equals(other.getCurrency())
			&& UintLinkValiFlag.equals(other.getUintLinkValiFlag())
			&& RenewCount == other.getRenewCount()
			&& RenewContNo.equals(other.getRenewContNo())
			&& InitNumPeople == other.getInitNumPeople()
			&& InitMult == other.getInitMult()
			&& InitAmnt == other.getInitAmnt()
			&& InitRiskAmnt == other.getInitRiskAmnt()
			&& InitPrem == other.getInitPrem()
			&& InitStandPrem == other.getInitStandPrem()
			&& RiskAmnt == other.getRiskAmnt()
			&& StandPrem == other.getStandPrem()
			&& SumNumPeople == other.getSumNumPeople()
			&& ValDateType.equals(other.getValDateType())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& SubFlag.equals(other.getSubFlag())
			&& StopFlag.equals(other.getStopFlag())
			&& KeepValueOpt.equals(other.getKeepValueOpt())
			&& Lang.equals(other.getLang())
			&& GradeFeeRate == other.getGradeFeeRate()
			&& OtherFeeRate == other.getOtherFeeRate()
			&& PricingMode.equals(other.getPricingMode())
			&& ComCode.equals(other.getComCode())
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("KindCode") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 6;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 9;
		}
		if( strFieldName.equals("AgentType") ) {
			return 10;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 11;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 12;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 13;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 14;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 15;
		}
		if( strFieldName.equals("GrpName") ) {
			return 16;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 17;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 18;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 19;
		}
		if( strFieldName.equals("RegetDate") ) {
			return 20;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 21;
		}
		if( strFieldName.equals("SSFlag") ) {
			return 22;
		}
		if( strFieldName.equals("PeakLine") ) {
			return 23;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 24;
		}
		if( strFieldName.equals("GetRate") ) {
			return 25;
		}
		if( strFieldName.equals("BonusRate") ) {
			return 26;
		}
		if( strFieldName.equals("MaxMedFee") ) {
			return 27;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 28;
		}
		if( strFieldName.equals("EmployeeRate") ) {
			return 29;
		}
		if( strFieldName.equals("FamilyRate") ) {
			return 30;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 31;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return 32;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return 33;
		}
		if( strFieldName.equals("ExpAmnt") ) {
			return 34;
		}
		if( strFieldName.equals("PayMode") ) {
			return 35;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 36;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 37;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 38;
		}
		if( strFieldName.equals("Peoples2") ) {
			return 39;
		}
		if( strFieldName.equals("Mult") ) {
			return 40;
		}
		if( strFieldName.equals("Prem") ) {
			return 41;
		}
		if( strFieldName.equals("Amnt") ) {
			return 42;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 43;
		}
		if( strFieldName.equals("SumPay") ) {
			return 44;
		}
		if( strFieldName.equals("Dif") ) {
			return 45;
		}
		if( strFieldName.equals("State") ) {
			return 46;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 47;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 48;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 49;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 50;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 51;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 52;
		}
		if( strFieldName.equals("UWDate") ) {
			return 53;
		}
		if( strFieldName.equals("UWTime") ) {
			return 54;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 55;
		}
		if( strFieldName.equals("Remark") ) {
			return 56;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 57;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 58;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 59;
		}
		if( strFieldName.equals("Operator") ) {
			return 60;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 61;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 62;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 63;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 64;
		}
		if( strFieldName.equals("OnWorkPeoples") ) {
			return 65;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return 66;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return 67;
		}
		if( strFieldName.equals("RelaPeoples") ) {
			return 68;
		}
		if( strFieldName.equals("RelaMatePeoples") ) {
			return 69;
		}
		if( strFieldName.equals("RelaYoungPeoples") ) {
			return 70;
		}
		if( strFieldName.equals("RelaOtherPeoples") ) {
			return 71;
		}
		if( strFieldName.equals("WaitPeriod") ) {
			return 72;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return 73;
		}
		if( strFieldName.equals("HealthInsurType") ) {
			return 74;
		}
		if( strFieldName.equals("AscriptionFlag") ) {
			return 75;
		}
		if( strFieldName.equals("InitRate") ) {
			return 76;
		}
		if( strFieldName.equals("HealthProType") ) {
			return 77;
		}
		if( strFieldName.equals("DistriFlag") ) {
			return 78;
		}
		if( strFieldName.equals("Peoples3") ) {
			return 79;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 80;
		}
		if( strFieldName.equals("RenewFlag") ) {
			return 81;
		}
		if( strFieldName.equals("DistriRate") ) {
			return 82;
		}
		if( strFieldName.equals("FixprofitRate") ) {
			return 83;
		}
		if( strFieldName.equals("ChargeFeeRate") ) {
			return 84;
		}
		if( strFieldName.equals("CommRate") ) {
			return 85;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
			return 86;
		}
		if( strFieldName.equals("Currency") ) {
			return 87;
		}
		if( strFieldName.equals("UintLinkValiFlag") ) {
			return 88;
		}
		if( strFieldName.equals("RenewCount") ) {
			return 89;
		}
		if( strFieldName.equals("RenewContNo") ) {
			return 90;
		}
		if( strFieldName.equals("InitNumPeople") ) {
			return 91;
		}
		if( strFieldName.equals("InitMult") ) {
			return 92;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return 93;
		}
		if( strFieldName.equals("InitRiskAmnt") ) {
			return 94;
		}
		if( strFieldName.equals("InitPrem") ) {
			return 95;
		}
		if( strFieldName.equals("InitStandPrem") ) {
			return 96;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 97;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 98;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return 99;
		}
		if( strFieldName.equals("ValDateType") ) {
			return 100;
		}
		if( strFieldName.equals("EndDate") ) {
			return 101;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 102;
		}
		if( strFieldName.equals("SubFlag") ) {
			return 103;
		}
		if( strFieldName.equals("StopFlag") ) {
			return 104;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return 105;
		}
		if( strFieldName.equals("Lang") ) {
			return 106;
		}
		if( strFieldName.equals("GradeFeeRate") ) {
			return 107;
		}
		if( strFieldName.equals("OtherFeeRate") ) {
			return 108;
		}
		if( strFieldName.equals("PricingMode") ) {
			return 109;
		}
		if( strFieldName.equals("ComCode") ) {
			return 110;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 111;
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "GrpProposalNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "KindCode";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "RiskVersion";
				break;
			case 7:
				strFieldName = "SaleChnl";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "AgentCom";
				break;
			case 10:
				strFieldName = "AgentType";
				break;
			case 11:
				strFieldName = "AgentCode";
				break;
			case 12:
				strFieldName = "AgentGroup";
				break;
			case 13:
				strFieldName = "AgentCode1";
				break;
			case 14:
				strFieldName = "CustomerNo";
				break;
			case 15:
				strFieldName = "AddressNo";
				break;
			case 16:
				strFieldName = "GrpName";
				break;
			case 17:
				strFieldName = "FirstPayDate";
				break;
			case 18:
				strFieldName = "PayEndDate";
				break;
			case 19:
				strFieldName = "PaytoDate";
				break;
			case 20:
				strFieldName = "RegetDate";
				break;
			case 21:
				strFieldName = "LastEdorDate";
				break;
			case 22:
				strFieldName = "SSFlag";
				break;
			case 23:
				strFieldName = "PeakLine";
				break;
			case 24:
				strFieldName = "GetLimit";
				break;
			case 25:
				strFieldName = "GetRate";
				break;
			case 26:
				strFieldName = "BonusRate";
				break;
			case 27:
				strFieldName = "MaxMedFee";
				break;
			case 28:
				strFieldName = "OutPayFlag";
				break;
			case 29:
				strFieldName = "EmployeeRate";
				break;
			case 30:
				strFieldName = "FamilyRate";
				break;
			case 31:
				strFieldName = "SpecFlag";
				break;
			case 32:
				strFieldName = "ExpPeoples";
				break;
			case 33:
				strFieldName = "ExpPremium";
				break;
			case 34:
				strFieldName = "ExpAmnt";
				break;
			case 35:
				strFieldName = "PayMode";
				break;
			case 36:
				strFieldName = "ManageFeeRate";
				break;
			case 37:
				strFieldName = "PayIntv";
				break;
			case 38:
				strFieldName = "CValiDate";
				break;
			case 39:
				strFieldName = "Peoples2";
				break;
			case 40:
				strFieldName = "Mult";
				break;
			case 41:
				strFieldName = "Prem";
				break;
			case 42:
				strFieldName = "Amnt";
				break;
			case 43:
				strFieldName = "SumPrem";
				break;
			case 44:
				strFieldName = "SumPay";
				break;
			case 45:
				strFieldName = "Dif";
				break;
			case 46:
				strFieldName = "State";
				break;
			case 47:
				strFieldName = "ApproveFlag";
				break;
			case 48:
				strFieldName = "ApproveCode";
				break;
			case 49:
				strFieldName = "ApproveDate";
				break;
			case 50:
				strFieldName = "ApproveTime";
				break;
			case 51:
				strFieldName = "UWFlag";
				break;
			case 52:
				strFieldName = "UWOperator";
				break;
			case 53:
				strFieldName = "UWDate";
				break;
			case 54:
				strFieldName = "UWTime";
				break;
			case 55:
				strFieldName = "AppFlag";
				break;
			case 56:
				strFieldName = "Remark";
				break;
			case 57:
				strFieldName = "StandbyFlag1";
				break;
			case 58:
				strFieldName = "StandbyFlag2";
				break;
			case 59:
				strFieldName = "StandbyFlag3";
				break;
			case 60:
				strFieldName = "Operator";
				break;
			case 61:
				strFieldName = "MakeDate";
				break;
			case 62:
				strFieldName = "MakeTime";
				break;
			case 63:
				strFieldName = "ModifyDate";
				break;
			case 64:
				strFieldName = "ModifyTime";
				break;
			case 65:
				strFieldName = "OnWorkPeoples";
				break;
			case 66:
				strFieldName = "OffWorkPeoples";
				break;
			case 67:
				strFieldName = "OtherPeoples";
				break;
			case 68:
				strFieldName = "RelaPeoples";
				break;
			case 69:
				strFieldName = "RelaMatePeoples";
				break;
			case 70:
				strFieldName = "RelaYoungPeoples";
				break;
			case 71:
				strFieldName = "RelaOtherPeoples";
				break;
			case 72:
				strFieldName = "WaitPeriod";
				break;
			case 73:
				strFieldName = "BonusFlag";
				break;
			case 74:
				strFieldName = "HealthInsurType";
				break;
			case 75:
				strFieldName = "AscriptionFlag";
				break;
			case 76:
				strFieldName = "InitRate";
				break;
			case 77:
				strFieldName = "HealthProType";
				break;
			case 78:
				strFieldName = "DistriFlag";
				break;
			case 79:
				strFieldName = "Peoples3";
				break;
			case 80:
				strFieldName = "FeeRate";
				break;
			case 81:
				strFieldName = "RenewFlag";
				break;
			case 82:
				strFieldName = "DistriRate";
				break;
			case 83:
				strFieldName = "FixprofitRate";
				break;
			case 84:
				strFieldName = "ChargeFeeRate";
				break;
			case 85:
				strFieldName = "CommRate";
				break;
			case 86:
				strFieldName = "StandbyFlag4";
				break;
			case 87:
				strFieldName = "Currency";
				break;
			case 88:
				strFieldName = "UintLinkValiFlag";
				break;
			case 89:
				strFieldName = "RenewCount";
				break;
			case 90:
				strFieldName = "RenewContNo";
				break;
			case 91:
				strFieldName = "InitNumPeople";
				break;
			case 92:
				strFieldName = "InitMult";
				break;
			case 93:
				strFieldName = "InitAmnt";
				break;
			case 94:
				strFieldName = "InitRiskAmnt";
				break;
			case 95:
				strFieldName = "InitPrem";
				break;
			case 96:
				strFieldName = "InitStandPrem";
				break;
			case 97:
				strFieldName = "RiskAmnt";
				break;
			case 98:
				strFieldName = "StandPrem";
				break;
			case 99:
				strFieldName = "SumNumPeople";
				break;
			case 100:
				strFieldName = "ValDateType";
				break;
			case 101:
				strFieldName = "EndDate";
				break;
			case 102:
				strFieldName = "AutoPayFlag";
				break;
			case 103:
				strFieldName = "SubFlag";
				break;
			case 104:
				strFieldName = "StopFlag";
				break;
			case 105:
				strFieldName = "KeepValueOpt";
				break;
			case 106:
				strFieldName = "Lang";
				break;
			case 107:
				strFieldName = "GradeFeeRate";
				break;
			case 108:
				strFieldName = "OtherFeeRate";
				break;
			case 109:
				strFieldName = "PricingMode";
				break;
			case 110:
				strFieldName = "ComCode";
				break;
			case 111:
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
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
		if( strFieldName.equals("SaleChnl") ) {
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
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
		if( strFieldName.equals("RegetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SSFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeakLine") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxMedFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EmployeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FamilyRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Peoples2") ) {
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
		if( strFieldName.equals("SumPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Dif") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("OnWorkPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaMatePeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaYoungPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaOtherPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WaitPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthInsurType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptionFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HealthProType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistriFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RenewFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistriRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FixprofitRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChargeFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UintLinkValiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RenewCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RenewContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitNumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InitMult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitRiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitStandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ValDateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StopFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeepValueOpt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Lang") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GradeFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OtherFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PricingMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_INT;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_INT;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_INT;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 44:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 45:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 70:
				nFieldType = Schema.TYPE_INT;
				break;
			case 71:
				nFieldType = Schema.TYPE_INT;
				break;
			case 72:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_INT;
				break;
			case 80:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 83:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 84:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 85:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_INT;
				break;
			case 92:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 93:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 94:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 95:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 96:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 97:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 98:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 99:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 108:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
