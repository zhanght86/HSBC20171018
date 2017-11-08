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
import com.sinosoft.lis.db.LLClaimAccountDB;

/*
 * <p>ClassName: LLClaimAccountSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LLClaimAccountSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimAccountSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 拒赔号 */
	private String DeclineNo;
	/** 帐户号码 */
	private String AccNo;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 总单/合同号 */
	private String ContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 保单号 */
	private String PolNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 险种代码 */
	private String RiskCode;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 销售渠道 */
	private String SaleChnl;
	/** 代理人代码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 账户赔付金额 */
	private double AccPayMoney;
	/** 管理机构 */
	private String MngCom;
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
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 集体合同号 */
	private String GrpContNo;
	/** 费用时间 */
	private Date PayDate;
	/** 对应其它号码 */
	private String OtherNo;
	/** 流水号 */
	private String SerialNo;
	/** 账户归属属性 */
	private String AccAscription;
	/** 对应其它号码类型 */
	private String OtherType;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 投保人客户号 */
	private String AppntNo;
	/** 责任编码 */
	private String DutyCode;
	/** 归属类型 */
	private String AscriptType;
	/** 账户类型 */
	private String AccType;
	/** 投资类型 */
	private String InvestType;
	/** 账户结算方式 */
	private String AccComputeFlag;
	/** 账户赔付单位数 */
	private double AccPayUnitCount;
	/** 账户赔付利息 */
	private double AccPayInterest;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 40;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimAccountSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "ClmNo";
		pk[1] = "DeclineNo";
		pk[2] = "AccNo";
		pk[3] = "GetDutyCode";
		pk[4] = "PolNo";

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
		LLClaimAccountSchema cloned = (LLClaimAccountSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getDeclineNo()
	{
		return DeclineNo;
	}
	public void setDeclineNo(String aDeclineNo)
	{
		if(aDeclineNo!=null && aDeclineNo.length()>20)
			throw new IllegalArgumentException("拒赔号DeclineNo值"+aDeclineNo+"的长度"+aDeclineNo.length()+"大于最大值20");
		DeclineNo = aDeclineNo;
	}
	public String getAccNo()
	{
		return AccNo;
	}
	public void setAccNo(String aAccNo)
	{
		if(aAccNo!=null && aAccNo.length()>20)
			throw new IllegalArgumentException("帐户号码AccNo值"+aAccNo+"的长度"+aAccNo.length()+"大于最大值20");
		AccNo = aAccNo;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>10)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值10");
		GetDutyCode = aGetDutyCode;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("总单/合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单号GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单号PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		if(aKindCode!=null && aKindCode.length()>3)
			throw new IllegalArgumentException("险类代码KindCode值"+aKindCode+"的长度"+aKindCode.length()+"大于最大值3");
		KindCode = aKindCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		if(aRiskVer!=null && aRiskVer.length()>8)
			throw new IllegalArgumentException("险种版本号RiskVer值"+aRiskVer+"的长度"+aRiskVer.length()+"大于最大值8");
		RiskVer = aRiskVer;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
		if(aPolMngCom!=null && aPolMngCom.length()>10)
			throw new IllegalArgumentException("保单管理机构PolMngCom值"+aPolMngCom+"的长度"+aPolMngCom.length()+"大于最大值10");
		PolMngCom = aPolMngCom;
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
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		if(aAgentCode!=null && aAgentCode.length()>10)
			throw new IllegalArgumentException("代理人代码AgentCode值"+aAgentCode+"的长度"+aAgentCode.length()+"大于最大值10");
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
	public double getAccPayMoney()
	{
		return AccPayMoney;
	}
	public void setAccPayMoney(double aAccPayMoney)
	{
		AccPayMoney = aAccPayMoney;
	}
	public void setAccPayMoney(String aAccPayMoney)
	{
		if (aAccPayMoney != null && !aAccPayMoney.equals(""))
		{
			Double tDouble = new Double(aAccPayMoney);
			double d = tDouble.doubleValue();
			AccPayMoney = d;
		}
	}

	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
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
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		if(aInsuAccNo!=null && aInsuAccNo.length()>20)
			throw new IllegalArgumentException("保险帐户号码InsuAccNo值"+aInsuAccNo+"的长度"+aInsuAccNo.length()+"大于最大值20");
		InsuAccNo = aInsuAccNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getPayDate()
	{
		if( PayDate != null )
			return fDate.getString(PayDate);
		else
			return null;
	}
	public void setPayDate(Date aPayDate)
	{
		PayDate = aPayDate;
	}
	public void setPayDate(String aPayDate)
	{
		if (aPayDate != null && !aPayDate.equals("") )
		{
			PayDate = fDate.getDate( aPayDate );
		}
		else
			PayDate = null;
	}

	/**
	* 对于每次交费都产生新账号的情况，该字段存放交费号。
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("对应其它号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	/**
	* 0-未归属<p>
	* 1-已归属
	*/
	public String getAccAscription()
	{
		return AccAscription;
	}
	public void setAccAscription(String aAccAscription)
	{
		if(aAccAscription!=null && aAccAscription.length()>10)
			throw new IllegalArgumentException("账户归属属性AccAscription值"+aAccAscription+"的长度"+aAccAscription.length()+"大于最大值10");
		AccAscription = aAccAscription;
	}
	/**
	* 1 －－ 个人保单号<p>
	* 2 －－ 缴费收据号<p>
	* 3 －－ 集体保单号(对于集体公共账户对应的集体保单号码)
	*/
	public String getOtherType()
	{
		return OtherType;
	}
	public void setOtherType(String aOtherType)
	{
		if(aOtherType!=null && aOtherType.length()>10)
			throw new IllegalArgumentException("对应其它号码类型OtherType值"+aOtherType+"的长度"+aOtherType.length()+"大于最大值10");
		OtherType = aOtherType;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		if(aPayPlanCode!=null && aPayPlanCode.length()>10)
			throw new IllegalArgumentException("交费计划编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值10");
		PayPlanCode = aPayPlanCode;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人客户号AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>10)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值10");
		DutyCode = aDutyCode;
	}
	public String getAscriptType()
	{
		return AscriptType;
	}
	public void setAscriptType(String aAscriptType)
	{
		if(aAscriptType!=null && aAscriptType.length()>2)
			throw new IllegalArgumentException("归属类型AscriptType值"+aAscriptType+"的长度"+aAscriptType.length()+"大于最大值2");
		AscriptType = aAscriptType;
	}
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		if(aAccType!=null && aAccType.length()>3)
			throw new IllegalArgumentException("账户类型AccType值"+aAccType+"的长度"+aAccType.length()+"大于最大值3");
		AccType = aAccType;
	}
	public String getInvestType()
	{
		return InvestType;
	}
	public void setInvestType(String aInvestType)
	{
		if(aInvestType!=null && aInvestType.length()>3)
			throw new IllegalArgumentException("投资类型InvestType值"+aInvestType+"的长度"+aInvestType.length()+"大于最大值3");
		InvestType = aInvestType;
	}
	public String getAccComputeFlag()
	{
		return AccComputeFlag;
	}
	public void setAccComputeFlag(String aAccComputeFlag)
	{
		if(aAccComputeFlag!=null && aAccComputeFlag.length()>3)
			throw new IllegalArgumentException("账户结算方式AccComputeFlag值"+aAccComputeFlag+"的长度"+aAccComputeFlag.length()+"大于最大值3");
		AccComputeFlag = aAccComputeFlag;
	}
	public double getAccPayUnitCount()
	{
		return AccPayUnitCount;
	}
	public void setAccPayUnitCount(double aAccPayUnitCount)
	{
		AccPayUnitCount = aAccPayUnitCount;
	}
	public void setAccPayUnitCount(String aAccPayUnitCount)
	{
		if (aAccPayUnitCount != null && !aAccPayUnitCount.equals(""))
		{
			Double tDouble = new Double(aAccPayUnitCount);
			double d = tDouble.doubleValue();
			AccPayUnitCount = d;
		}
	}

	public double getAccPayInterest()
	{
		return AccPayInterest;
	}
	public void setAccPayInterest(double aAccPayInterest)
	{
		AccPayInterest = aAccPayInterest;
	}
	public void setAccPayInterest(String aAccPayInterest)
	{
		if (aAccPayInterest != null && !aAccPayInterest.equals(""))
		{
			Double tDouble = new Double(aAccPayInterest);
			double d = tDouble.doubleValue();
			AccPayInterest = d;
		}
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
	* 使用另外一个 LLClaimAccountSchema 对象给 Schema 赋值
	* @param: aLLClaimAccountSchema LLClaimAccountSchema
	**/
	public void setSchema(LLClaimAccountSchema aLLClaimAccountSchema)
	{
		this.ClmNo = aLLClaimAccountSchema.getClmNo();
		this.DeclineNo = aLLClaimAccountSchema.getDeclineNo();
		this.AccNo = aLLClaimAccountSchema.getAccNo();
		this.GetDutyCode = aLLClaimAccountSchema.getGetDutyCode();
		this.ContNo = aLLClaimAccountSchema.getContNo();
		this.GrpPolNo = aLLClaimAccountSchema.getGrpPolNo();
		this.PolNo = aLLClaimAccountSchema.getPolNo();
		this.KindCode = aLLClaimAccountSchema.getKindCode();
		this.RiskVer = aLLClaimAccountSchema.getRiskVer();
		this.RiskCode = aLLClaimAccountSchema.getRiskCode();
		this.PolMngCom = aLLClaimAccountSchema.getPolMngCom();
		this.SaleChnl = aLLClaimAccountSchema.getSaleChnl();
		this.AgentCode = aLLClaimAccountSchema.getAgentCode();
		this.AgentGroup = aLLClaimAccountSchema.getAgentGroup();
		this.AccPayMoney = aLLClaimAccountSchema.getAccPayMoney();
		this.MngCom = aLLClaimAccountSchema.getMngCom();
		this.Operator = aLLClaimAccountSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLClaimAccountSchema.getMakeDate());
		this.MakeTime = aLLClaimAccountSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimAccountSchema.getModifyDate());
		this.ModifyTime = aLLClaimAccountSchema.getModifyTime();
		this.InsuAccNo = aLLClaimAccountSchema.getInsuAccNo();
		this.GrpContNo = aLLClaimAccountSchema.getGrpContNo();
		this.PayDate = fDate.getDate( aLLClaimAccountSchema.getPayDate());
		this.OtherNo = aLLClaimAccountSchema.getOtherNo();
		this.SerialNo = aLLClaimAccountSchema.getSerialNo();
		this.AccAscription = aLLClaimAccountSchema.getAccAscription();
		this.OtherType = aLLClaimAccountSchema.getOtherType();
		this.PayPlanCode = aLLClaimAccountSchema.getPayPlanCode();
		this.CustomerNo = aLLClaimAccountSchema.getCustomerNo();
		this.AppntNo = aLLClaimAccountSchema.getAppntNo();
		this.DutyCode = aLLClaimAccountSchema.getDutyCode();
		this.AscriptType = aLLClaimAccountSchema.getAscriptType();
		this.AccType = aLLClaimAccountSchema.getAccType();
		this.InvestType = aLLClaimAccountSchema.getInvestType();
		this.AccComputeFlag = aLLClaimAccountSchema.getAccComputeFlag();
		this.AccPayUnitCount = aLLClaimAccountSchema.getAccPayUnitCount();
		this.AccPayInterest = aLLClaimAccountSchema.getAccPayInterest();
		this.ComCode = aLLClaimAccountSchema.getComCode();
		this.ModifyOperator = aLLClaimAccountSchema.getModifyOperator();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("DeclineNo") == null )
				this.DeclineNo = null;
			else
				this.DeclineNo = rs.getString("DeclineNo").trim();

			if( rs.getString("AccNo") == null )
				this.AccNo = null;
			else
				this.AccNo = rs.getString("AccNo").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("PolMngCom") == null )
				this.PolMngCom = null;
			else
				this.PolMngCom = rs.getString("PolMngCom").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			this.AccPayMoney = rs.getDouble("AccPayMoney");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			this.PayDate = rs.getDate("PayDate");
			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("AccAscription") == null )
				this.AccAscription = null;
			else
				this.AccAscription = rs.getString("AccAscription").trim();

			if( rs.getString("OtherType") == null )
				this.OtherType = null;
			else
				this.OtherType = rs.getString("OtherType").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("AscriptType") == null )
				this.AscriptType = null;
			else
				this.AscriptType = rs.getString("AscriptType").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("AccComputeFlag") == null )
				this.AccComputeFlag = null;
			else
				this.AccComputeFlag = rs.getString("AccComputeFlag").trim();

			this.AccPayUnitCount = rs.getDouble("AccPayUnitCount");
			this.AccPayInterest = rs.getDouble("AccPayInterest");
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
			logger.debug("数据库中的LLClaimAccount表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimAccountSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimAccountSchema getSchema()
	{
		LLClaimAccountSchema aLLClaimAccountSchema = new LLClaimAccountSchema();
		aLLClaimAccountSchema.setSchema(this);
		return aLLClaimAccountSchema;
	}

	public LLClaimAccountDB getDB()
	{
		LLClaimAccountDB aDBOper = new LLClaimAccountDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimAccount描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccAscription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccComputeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccPayUnitCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccPayInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimAccount>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DeclineNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AccPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AccAscription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			OtherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			AscriptType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AccComputeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			AccPayUnitCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			AccPayInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimAccountSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineNo));
		}
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMngCom));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("AccPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccPayMoney));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("AccAscription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccAscription));
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherType));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("AscriptType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptType));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("AccComputeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccComputeFlag));
		}
		if (FCode.equalsIgnoreCase("AccPayUnitCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccPayUnitCount));
		}
		if (FCode.equalsIgnoreCase("AccPayInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccPayInterest));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DeclineNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 14:
				strFieldValue = String.valueOf(AccPayMoney);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AccAscription);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(OtherType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AscriptType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AccComputeFlag);
				break;
			case 36:
				strFieldValue = String.valueOf(AccPayUnitCount);
				break;
			case 37:
				strFieldValue = String.valueOf(AccPayInterest);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 39:
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeclineNo = FValue.trim();
			}
			else
				DeclineNo = null;
		}
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccNo = FValue.trim();
			}
			else
				AccNo = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
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
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolMngCom = FValue.trim();
			}
			else
				PolMngCom = null;
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
		if (FCode.equalsIgnoreCase("AccPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccPayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayDate = fDate.getDate( FValue );
			}
			else
				PayDate = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("AccAscription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccAscription = FValue.trim();
			}
			else
				AccAscription = null;
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherType = FValue.trim();
			}
			else
				OtherType = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("AscriptType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptType = FValue.trim();
			}
			else
				AscriptType = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestType = FValue.trim();
			}
			else
				InvestType = null;
		}
		if (FCode.equalsIgnoreCase("AccComputeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccComputeFlag = FValue.trim();
			}
			else
				AccComputeFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccPayUnitCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccPayUnitCount = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccPayInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccPayInterest = d;
			}
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
		LLClaimAccountSchema other = (LLClaimAccountSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& DeclineNo.equals(other.getDeclineNo())
			&& AccNo.equals(other.getAccNo())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& PolNo.equals(other.getPolNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskCode.equals(other.getRiskCode())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AccPayMoney == other.getAccPayMoney()
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& OtherNo.equals(other.getOtherNo())
			&& SerialNo.equals(other.getSerialNo())
			&& AccAscription.equals(other.getAccAscription())
			&& OtherType.equals(other.getOtherType())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AppntNo.equals(other.getAppntNo())
			&& DutyCode.equals(other.getDutyCode())
			&& AscriptType.equals(other.getAscriptType())
			&& AccType.equals(other.getAccType())
			&& InvestType.equals(other.getInvestType())
			&& AccComputeFlag.equals(other.getAccComputeFlag())
			&& AccPayUnitCount == other.getAccPayUnitCount()
			&& AccPayInterest == other.getAccPayInterest()
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return 1;
		}
		if( strFieldName.equals("AccNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
			return 4;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 5;
		}
		if( strFieldName.equals("PolNo") ) {
			return 6;
		}
		if( strFieldName.equals("KindCode") ) {
			return 7;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 8;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 9;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 10;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 11;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 12;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 13;
		}
		if( strFieldName.equals("AccPayMoney") ) {
			return 14;
		}
		if( strFieldName.equals("MngCom") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 21;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 22;
		}
		if( strFieldName.equals("PayDate") ) {
			return 23;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 24;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 25;
		}
		if( strFieldName.equals("AccAscription") ) {
			return 26;
		}
		if( strFieldName.equals("OtherType") ) {
			return 27;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 28;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 29;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 30;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 31;
		}
		if( strFieldName.equals("AscriptType") ) {
			return 32;
		}
		if( strFieldName.equals("AccType") ) {
			return 33;
		}
		if( strFieldName.equals("InvestType") ) {
			return 34;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return 35;
		}
		if( strFieldName.equals("AccPayUnitCount") ) {
			return 36;
		}
		if( strFieldName.equals("AccPayInterest") ) {
			return 37;
		}
		if( strFieldName.equals("ComCode") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 39;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "DeclineNo";
				break;
			case 2:
				strFieldName = "AccNo";
				break;
			case 3:
				strFieldName = "GetDutyCode";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "GrpPolNo";
				break;
			case 6:
				strFieldName = "PolNo";
				break;
			case 7:
				strFieldName = "KindCode";
				break;
			case 8:
				strFieldName = "RiskVer";
				break;
			case 9:
				strFieldName = "RiskCode";
				break;
			case 10:
				strFieldName = "PolMngCom";
				break;
			case 11:
				strFieldName = "SaleChnl";
				break;
			case 12:
				strFieldName = "AgentCode";
				break;
			case 13:
				strFieldName = "AgentGroup";
				break;
			case 14:
				strFieldName = "AccPayMoney";
				break;
			case 15:
				strFieldName = "MngCom";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "ModifyTime";
				break;
			case 21:
				strFieldName = "InsuAccNo";
				break;
			case 22:
				strFieldName = "GrpContNo";
				break;
			case 23:
				strFieldName = "PayDate";
				break;
			case 24:
				strFieldName = "OtherNo";
				break;
			case 25:
				strFieldName = "SerialNo";
				break;
			case 26:
				strFieldName = "AccAscription";
				break;
			case 27:
				strFieldName = "OtherType";
				break;
			case 28:
				strFieldName = "PayPlanCode";
				break;
			case 29:
				strFieldName = "CustomerNo";
				break;
			case 30:
				strFieldName = "AppntNo";
				break;
			case 31:
				strFieldName = "DutyCode";
				break;
			case 32:
				strFieldName = "AscriptType";
				break;
			case 33:
				strFieldName = "AccType";
				break;
			case 34:
				strFieldName = "InvestType";
				break;
			case 35:
				strFieldName = "AccComputeFlag";
				break;
			case 36:
				strFieldName = "AccPayUnitCount";
				break;
			case 37:
				strFieldName = "AccPayInterest";
				break;
			case 38:
				strFieldName = "ComCode";
				break;
			case 39:
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccAscription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccPayUnitCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccPayInterest") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
