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
import com.sinosoft.lis.db.LBGeneralToRiskDB;

/*
 * <p>ClassName: LBGeneralToRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LBGeneralToRiskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBGeneralToRiskSchema.class);
	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体投保单号码 */
	private String GrpProposalNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 处理机构 */
	private String ExecuteCom;
	/** 代理机构 */
	private String AgentCom;
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
	/** 付款方式 */
	private String GetFlag;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
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
	/** 分红??率 */
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
	/** 管??费比例 */
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

	public static final int FIELDNUM = 71;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBGeneralToRiskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "GrpContNo";
		pk[1] = "GrpPolNo";
		pk[2] = "ExecuteCom";

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
		LBGeneralToRiskSchema cloned = (LBGeneralToRiskSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
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
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		if(aGrpProposalNo!=null && aGrpProposalNo.length()>20)
			throw new IllegalArgumentException("集体投保单号码GrpProposalNo值"+aGrpProposalNo+"的长度"+aGrpProposalNo.length()+"大于最大值20");
		GrpProposalNo = aGrpProposalNo;
	}
	/**
	* 冗余，标准在集体保单???
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
	public String getExecuteCom()
	{
		return ExecuteCom;
	}
	public void setExecuteCom(String aExecuteCom)
	{
		if(aExecuteCom!=null && aExecuteCom.length()>10)
			throw new IllegalArgumentException("处理机构ExecuteCom值"+aExecuteCom+"的长度"+aExecuteCom.length()+"大于最大值10");
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
		if(aAgentCom!=null && aAgentCom.length()>20)
			throw new IllegalArgumentException("代理机构AgentCom值"+aAgentCom+"的长度"+aAgentCom.length()+"大于最大值20");
		AgentCom = aAgentCom;
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
	* 01-团险直销,02-个人营销,03-银行代理 04-兼业代理,05-专业代理,06-经纪公司 07-不计业绩销售渠道,99-其他
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
		if(aGrpName!=null && aGrpName.length()>200)
			throw new IllegalArgumentException("单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值200");
		GrpName = aGrpName;
	}
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		if(aGetFlag!=null && aGetFlag.length()>1)
			throw new IllegalArgumentException("付款方式GetFlag值"+aGetFlag+"的长度"+aGetFlag.length()+"大于最大值1");
		GetFlag = aGetFlag;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>10)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值10");
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		if(aBankAccNo!=null && aBankAccNo.length()>40)
			throw new IllegalArgumentException("银行帐号BankAccNo值"+aBankAccNo+"的长度"+aBankAccNo.length()+"大于最大值40");
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>60)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值60");
		AccName = aAccName;
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
	* 1---退费 2---充当续期保费
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
	* 1 现金 2 现金支票 3 转账支票 4 银行转账 5 内部转帐 6 银行托收 7 其他
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
	* 交费间隔 -1 -- 不定期交, 0 -- 趸交, 1 -- 月交 3 -- 季交 6 -- 半年交 12 -- 年交
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
	* [1-保障计划标记] [2]-计算方向 1-保费算保额 3-保额算保费) [3]-交费标记 M－月，D－日，Y－年
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
	* 1 拒保 2 延期 3 条件承保 4 通融 5 自动 6 待上级 7 问题件 8 核保通知书 9 正常 a 撤单 b 保险计划变更 z 核保订正
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
	* 根据不同险种的特殊要求，存放不同的数据 对于险种编码：311603，存放的是同心卡的开卡日期
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
	* 根据不同险种的特殊要求，存放不同的数据 银代险的内部优惠标志：（该字段对所有银代险有效） 1 默认 其他 
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

	/**
	* 使用另外一个 LBGeneralToRiskSchema 对象给 Schema 赋值
	* @param: aLBGeneralToRiskSchema LBGeneralToRiskSchema
	**/
	public void setSchema(LBGeneralToRiskSchema aLBGeneralToRiskSchema)
	{
		this.EdorNo = aLBGeneralToRiskSchema.getEdorNo();
		this.GrpContNo = aLBGeneralToRiskSchema.getGrpContNo();
		this.GrpPolNo = aLBGeneralToRiskSchema.getGrpPolNo();
		this.GrpProposalNo = aLBGeneralToRiskSchema.getGrpProposalNo();
		this.PrtNo = aLBGeneralToRiskSchema.getPrtNo();
		this.ExecuteCom = aLBGeneralToRiskSchema.getExecuteCom();
		this.AgentCom = aLBGeneralToRiskSchema.getAgentCom();
		this.KindCode = aLBGeneralToRiskSchema.getKindCode();
		this.RiskCode = aLBGeneralToRiskSchema.getRiskCode();
		this.RiskVersion = aLBGeneralToRiskSchema.getRiskVersion();
		this.SaleChnl = aLBGeneralToRiskSchema.getSaleChnl();
		this.ManageCom = aLBGeneralToRiskSchema.getManageCom();
		this.AgentType = aLBGeneralToRiskSchema.getAgentType();
		this.AgentCode = aLBGeneralToRiskSchema.getAgentCode();
		this.AgentGroup = aLBGeneralToRiskSchema.getAgentGroup();
		this.AgentCode1 = aLBGeneralToRiskSchema.getAgentCode1();
		this.CustomerNo = aLBGeneralToRiskSchema.getCustomerNo();
		this.AddressNo = aLBGeneralToRiskSchema.getAddressNo();
		this.GrpName = aLBGeneralToRiskSchema.getGrpName();
		this.GetFlag = aLBGeneralToRiskSchema.getGetFlag();
		this.BankCode = aLBGeneralToRiskSchema.getBankCode();
		this.BankAccNo = aLBGeneralToRiskSchema.getBankAccNo();
		this.AccName = aLBGeneralToRiskSchema.getAccName();
		this.FirstPayDate = fDate.getDate( aLBGeneralToRiskSchema.getFirstPayDate());
		this.PayEndDate = fDate.getDate( aLBGeneralToRiskSchema.getPayEndDate());
		this.PaytoDate = fDate.getDate( aLBGeneralToRiskSchema.getPaytoDate());
		this.RegetDate = fDate.getDate( aLBGeneralToRiskSchema.getRegetDate());
		this.LastEdorDate = fDate.getDate( aLBGeneralToRiskSchema.getLastEdorDate());
		this.SSFlag = aLBGeneralToRiskSchema.getSSFlag();
		this.PeakLine = aLBGeneralToRiskSchema.getPeakLine();
		this.GetLimit = aLBGeneralToRiskSchema.getGetLimit();
		this.GetRate = aLBGeneralToRiskSchema.getGetRate();
		this.BonusRate = aLBGeneralToRiskSchema.getBonusRate();
		this.MaxMedFee = aLBGeneralToRiskSchema.getMaxMedFee();
		this.OutPayFlag = aLBGeneralToRiskSchema.getOutPayFlag();
		this.EmployeeRate = aLBGeneralToRiskSchema.getEmployeeRate();
		this.FamilyRate = aLBGeneralToRiskSchema.getFamilyRate();
		this.SpecFlag = aLBGeneralToRiskSchema.getSpecFlag();
		this.ExpPeoples = aLBGeneralToRiskSchema.getExpPeoples();
		this.ExpPremium = aLBGeneralToRiskSchema.getExpPremium();
		this.ExpAmnt = aLBGeneralToRiskSchema.getExpAmnt();
		this.PayMode = aLBGeneralToRiskSchema.getPayMode();
		this.ManageFeeRate = aLBGeneralToRiskSchema.getManageFeeRate();
		this.PayIntv = aLBGeneralToRiskSchema.getPayIntv();
		this.CValiDate = fDate.getDate( aLBGeneralToRiskSchema.getCValiDate());
		this.Peoples2 = aLBGeneralToRiskSchema.getPeoples2();
		this.Mult = aLBGeneralToRiskSchema.getMult();
		this.Prem = aLBGeneralToRiskSchema.getPrem();
		this.Amnt = aLBGeneralToRiskSchema.getAmnt();
		this.SumPrem = aLBGeneralToRiskSchema.getSumPrem();
		this.SumPay = aLBGeneralToRiskSchema.getSumPay();
		this.Dif = aLBGeneralToRiskSchema.getDif();
		this.State = aLBGeneralToRiskSchema.getState();
		this.ApproveFlag = aLBGeneralToRiskSchema.getApproveFlag();
		this.ApproveCode = aLBGeneralToRiskSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLBGeneralToRiskSchema.getApproveDate());
		this.ApproveTime = aLBGeneralToRiskSchema.getApproveTime();
		this.UWFlag = aLBGeneralToRiskSchema.getUWFlag();
		this.UWOperator = aLBGeneralToRiskSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLBGeneralToRiskSchema.getUWDate());
		this.UWTime = aLBGeneralToRiskSchema.getUWTime();
		this.AppFlag = aLBGeneralToRiskSchema.getAppFlag();
		this.Remark = aLBGeneralToRiskSchema.getRemark();
		this.StandbyFlag1 = aLBGeneralToRiskSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLBGeneralToRiskSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLBGeneralToRiskSchema.getStandbyFlag3();
		this.Operator = aLBGeneralToRiskSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBGeneralToRiskSchema.getMakeDate());
		this.MakeTime = aLBGeneralToRiskSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBGeneralToRiskSchema.getModifyDate());
		this.ModifyTime = aLBGeneralToRiskSchema.getModifyTime();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

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

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBGeneralToRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGeneralToRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBGeneralToRiskSchema getSchema()
	{
		LBGeneralToRiskSchema aLBGeneralToRiskSchema = new LBGeneralToRiskSchema();
		aLBGeneralToRiskSchema.setSchema(this);
		return aLBGeneralToRiskSchema;
	}

	public LBGeneralToRiskDB getDB()
	{
		LBGeneralToRiskDB aDBOper = new LBGeneralToRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBGeneralToRisk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExecuteCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBGeneralToRisk>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			RegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			SSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PeakLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			BonusRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			MaxMedFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			EmployeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			FamilyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ExpPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).intValue();
			ExpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			ExpAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).intValue();
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			Peoples2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).doubleValue();
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGeneralToRiskSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
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
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(SSFlag);
				break;
			case 29:
				strFieldValue = String.valueOf(PeakLine);
				break;
			case 30:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 31:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 32:
				strFieldValue = String.valueOf(BonusRate);
				break;
			case 33:
				strFieldValue = String.valueOf(MaxMedFee);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 35:
				strFieldValue = String.valueOf(EmployeeRate);
				break;
			case 36:
				strFieldValue = String.valueOf(FamilyRate);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 38:
				strFieldValue = String.valueOf(ExpPeoples);
				break;
			case 39:
				strFieldValue = String.valueOf(ExpPremium);
				break;
			case 40:
				strFieldValue = String.valueOf(ExpAmnt);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 42:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 43:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 45:
				strFieldValue = String.valueOf(Peoples2);
				break;
			case 46:
				strFieldValue = String.valueOf(Mult);
				break;
			case 47:
				strFieldValue = String.valueOf(Prem);
				break;
			case 48:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 49:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 50:
				strFieldValue = String.valueOf(SumPay);
				break;
			case 51:
				strFieldValue = String.valueOf(Dif);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 70:
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFlag = FValue.trim();
			}
			else
				GetFlag = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBGeneralToRiskSchema other = (LBGeneralToRiskSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& AgentCom.equals(other.getAgentCom())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ManageCom.equals(other.getManageCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AddressNo.equals(other.getAddressNo())
			&& GrpName.equals(other.getGrpName())
			&& GetFlag.equals(other.getGetFlag())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 3;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 4;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 6;
		}
		if( strFieldName.equals("KindCode") ) {
			return 7;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 8;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 9;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("AgentType") ) {
			return 12;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 13;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 14;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 15;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 16;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 17;
		}
		if( strFieldName.equals("GrpName") ) {
			return 18;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 19;
		}
		if( strFieldName.equals("BankCode") ) {
			return 20;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 21;
		}
		if( strFieldName.equals("AccName") ) {
			return 22;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 23;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 24;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 25;
		}
		if( strFieldName.equals("RegetDate") ) {
			return 26;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 27;
		}
		if( strFieldName.equals("SSFlag") ) {
			return 28;
		}
		if( strFieldName.equals("PeakLine") ) {
			return 29;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 30;
		}
		if( strFieldName.equals("GetRate") ) {
			return 31;
		}
		if( strFieldName.equals("BonusRate") ) {
			return 32;
		}
		if( strFieldName.equals("MaxMedFee") ) {
			return 33;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 34;
		}
		if( strFieldName.equals("EmployeeRate") ) {
			return 35;
		}
		if( strFieldName.equals("FamilyRate") ) {
			return 36;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 37;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return 38;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return 39;
		}
		if( strFieldName.equals("ExpAmnt") ) {
			return 40;
		}
		if( strFieldName.equals("PayMode") ) {
			return 41;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 42;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 43;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 44;
		}
		if( strFieldName.equals("Peoples2") ) {
			return 45;
		}
		if( strFieldName.equals("Mult") ) {
			return 46;
		}
		if( strFieldName.equals("Prem") ) {
			return 47;
		}
		if( strFieldName.equals("Amnt") ) {
			return 48;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 49;
		}
		if( strFieldName.equals("SumPay") ) {
			return 50;
		}
		if( strFieldName.equals("Dif") ) {
			return 51;
		}
		if( strFieldName.equals("State") ) {
			return 52;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 53;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 54;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 55;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 56;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 57;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 58;
		}
		if( strFieldName.equals("UWDate") ) {
			return 59;
		}
		if( strFieldName.equals("UWTime") ) {
			return 60;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 61;
		}
		if( strFieldName.equals("Remark") ) {
			return 62;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 63;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 64;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 65;
		}
		if( strFieldName.equals("Operator") ) {
			return 66;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 67;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 68;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 69;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 70;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "GrpPolNo";
				break;
			case 3:
				strFieldName = "GrpProposalNo";
				break;
			case 4:
				strFieldName = "PrtNo";
				break;
			case 5:
				strFieldName = "ExecuteCom";
				break;
			case 6:
				strFieldName = "AgentCom";
				break;
			case 7:
				strFieldName = "KindCode";
				break;
			case 8:
				strFieldName = "RiskCode";
				break;
			case 9:
				strFieldName = "RiskVersion";
				break;
			case 10:
				strFieldName = "SaleChnl";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "AgentType";
				break;
			case 13:
				strFieldName = "AgentCode";
				break;
			case 14:
				strFieldName = "AgentGroup";
				break;
			case 15:
				strFieldName = "AgentCode1";
				break;
			case 16:
				strFieldName = "CustomerNo";
				break;
			case 17:
				strFieldName = "AddressNo";
				break;
			case 18:
				strFieldName = "GrpName";
				break;
			case 19:
				strFieldName = "GetFlag";
				break;
			case 20:
				strFieldName = "BankCode";
				break;
			case 21:
				strFieldName = "BankAccNo";
				break;
			case 22:
				strFieldName = "AccName";
				break;
			case 23:
				strFieldName = "FirstPayDate";
				break;
			case 24:
				strFieldName = "PayEndDate";
				break;
			case 25:
				strFieldName = "PaytoDate";
				break;
			case 26:
				strFieldName = "RegetDate";
				break;
			case 27:
				strFieldName = "LastEdorDate";
				break;
			case 28:
				strFieldName = "SSFlag";
				break;
			case 29:
				strFieldName = "PeakLine";
				break;
			case 30:
				strFieldName = "GetLimit";
				break;
			case 31:
				strFieldName = "GetRate";
				break;
			case 32:
				strFieldName = "BonusRate";
				break;
			case 33:
				strFieldName = "MaxMedFee";
				break;
			case 34:
				strFieldName = "OutPayFlag";
				break;
			case 35:
				strFieldName = "EmployeeRate";
				break;
			case 36:
				strFieldName = "FamilyRate";
				break;
			case 37:
				strFieldName = "SpecFlag";
				break;
			case 38:
				strFieldName = "ExpPeoples";
				break;
			case 39:
				strFieldName = "ExpPremium";
				break;
			case 40:
				strFieldName = "ExpAmnt";
				break;
			case 41:
				strFieldName = "PayMode";
				break;
			case 42:
				strFieldName = "ManageFeeRate";
				break;
			case 43:
				strFieldName = "PayIntv";
				break;
			case 44:
				strFieldName = "CValiDate";
				break;
			case 45:
				strFieldName = "Peoples2";
				break;
			case 46:
				strFieldName = "Mult";
				break;
			case 47:
				strFieldName = "Prem";
				break;
			case 48:
				strFieldName = "Amnt";
				break;
			case 49:
				strFieldName = "SumPrem";
				break;
			case 50:
				strFieldName = "SumPay";
				break;
			case 51:
				strFieldName = "Dif";
				break;
			case 52:
				strFieldName = "State";
				break;
			case 53:
				strFieldName = "ApproveFlag";
				break;
			case 54:
				strFieldName = "ApproveCode";
				break;
			case 55:
				strFieldName = "ApproveDate";
				break;
			case 56:
				strFieldName = "ApproveTime";
				break;
			case 57:
				strFieldName = "UWFlag";
				break;
			case 58:
				strFieldName = "UWOperator";
				break;
			case 59:
				strFieldName = "UWDate";
				break;
			case 60:
				strFieldName = "UWTime";
				break;
			case 61:
				strFieldName = "AppFlag";
				break;
			case 62:
				strFieldName = "Remark";
				break;
			case 63:
				strFieldName = "StandbyFlag1";
				break;
			case 64:
				strFieldName = "StandbyFlag2";
				break;
			case 65:
				strFieldName = "StandbyFlag3";
				break;
			case 66:
				strFieldName = "Operator";
				break;
			case 67:
				strFieldName = "MakeDate";
				break;
			case 68:
				strFieldName = "MakeTime";
				break;
			case 69:
				strFieldName = "ModifyDate";
				break;
			case 70:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
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
		if( strFieldName.equals("GetFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_INT;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_INT;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 45:
				nFieldType = Schema.TYPE_INT;
				break;
			case 46:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 49:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
