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
import com.sinosoft.lis.db.LLToClaimDutyDB;

/*
 * <p>ClassName: LLToClaimDutySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLToClaimDutySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLToClaimDutySchema.class);
	// @Field
	/** 分案号 */
	private String CaseNo;
	/** 保单号 */
	private String PolNo;
	/** 保单类型 */
	private String PolSort;
	/** 保单性质 */
	private String PolType;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 受理事故号 */
	private String CaseRelaNo;
	/** 伤残类型 */
	private String DefoType;
	/** 事件号 */
	private String SubRptNo;
	/** 集体合同号 */
	private String GrpContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 个单合同号 */
	private String ContNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 理算次数 */
	private int ClaimCount;
	/** 预估金额 */
	private double EstClaimMoney;
	/** 赔付结论 */
	private String GiveType;
	/** 赔付结论描述 */
	private String GiveTypeDesc;
	/** 赔付结论依据 */
	private String GiveReason;
	/** 赔付结论依据描述 */
	private String GiveReasonDesc;
	/** 有效保额 */
	private double Amnt;
	/** 年度红利 */
	private double YearBonus;
	/** 终了红利 */
	private double EndBonus;
	/** 缴费宽限期 */
	private int GracePeriod;
	/** 给付类型 */
	private String CasePolType;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 预付标志 */
	private String PrepayFlag;
	/** 预付金额 */
	private double PrepaySum;
	/** 保全标志 */
	private String PosFlag;
	/** 保全批单号[出险之后] */
	private String PosEdorNo;
	/** 附险影响主险标志 */
	private String EffectOnMainRisk;
	/** 附险影响主险的公式 */
	private String RiskCalCode;
	/** 加保保额 */
	private double AddAmnt;
	/** 承保时保单号 */
	private String NBPolNo;
	/** 保全批改类型 */
	private String PosEdorType;
	/** 险种类型 */
	private String RiskType;
	/** 权限理赔额 */
	private double PopedomPay;
	/** 保费理赔额 */
	private double InsFeePay;
	/** 币别 */
	private String Currency;
	/** 赔案号 */
	private String ClmNo;
	/** 业务类型 */
	private String BussType;
	/** 业务号 */
	private String BussNo;
	/** 拓展标志 */
	private String ExpandFlag;
	/** 拓展责任 */
	private String ExpandDutyCode;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 55;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLToClaimDutySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "CaseNo";
		pk[1] = "PolNo";
		pk[2] = "DutyCode";
		pk[3] = "GetDutyCode";
		pk[4] = "GetDutyKind";
		pk[5] = "CaseRelaNo";
		pk[6] = "CustomerNo";

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
		LLToClaimDutySchema cloned = (LLToClaimDutySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
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
	/**
	* C -- C表保单<p>
	* B -- B表保单<p>
	* A -- 未承保保单
	*/
	public String getPolSort()
	{
		return PolSort;
	}
	public void setPolSort(String aPolSort)
	{
		if(aPolSort!=null && aPolSort.length()>6)
			throw new IllegalArgumentException("保单类型PolSort值"+aPolSort+"的长度"+aPolSort.length()+"大于最大值6");
		PolSort = aPolSort;
	}
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		if(aPolType!=null && aPolType.length()>6)
			throw new IllegalArgumentException("保单性质PolType值"+aPolType+"的长度"+aPolType.length()+"大于最大值6");
		PolType = aPolType;
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
	/**
	* #100  意外医疗<p>
	* #101  意外伤残<p>
	* #102  意外死<p>
	* #103  意外医疗津贴<p>
	* #104  意外高残<p>
	* #200  疾病医疗<p>
	* #201  疾病伤残<p>
	* #202  疾病死亡<p>
	* #203  疾病医疗津贴<p>
	* #204  疾病高残
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>10)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值10");
		GetDutyKind = aGetDutyKind;
	}
	/**
	* 受理事故号
	*/
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		if(aCaseRelaNo!=null && aCaseRelaNo.length()>20)
			throw new IllegalArgumentException("受理事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}
	/**
	* 1--普通伤残(使用7级34项残疾给付表)<p>
	* 2--普通伤残(使用老的残疾给付表)
	*/
	public String getDefoType()
	{
		return DefoType;
	}
	public void setDefoType(String aDefoType)
	{
		if(aDefoType!=null && aDefoType.length()>10)
			throw new IllegalArgumentException("伤残类型DefoType值"+aDefoType+"的长度"+aDefoType.length()+"大于最大值10");
		DefoType = aDefoType;
	}
	public String getSubRptNo()
	{
		return SubRptNo;
	}
	public void setSubRptNo(String aSubRptNo)
	{
		if(aSubRptNo!=null && aSubRptNo.length()>20)
			throw new IllegalArgumentException("事件号SubRptNo值"+aSubRptNo+"的长度"+aSubRptNo.length()+"大于最大值20");
		SubRptNo = aSubRptNo;
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个单合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
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
	public int getClaimCount()
	{
		return ClaimCount;
	}
	public void setClaimCount(int aClaimCount)
	{
		ClaimCount = aClaimCount;
	}
	public void setClaimCount(String aClaimCount)
	{
		if (aClaimCount != null && !aClaimCount.equals(""))
		{
			Integer tInteger = new Integer(aClaimCount);
			int i = tInteger.intValue();
			ClaimCount = i;
		}
	}

	public double getEstClaimMoney()
	{
		return EstClaimMoney;
	}
	public void setEstClaimMoney(double aEstClaimMoney)
	{
		EstClaimMoney = aEstClaimMoney;
	}
	public void setEstClaimMoney(String aEstClaimMoney)
	{
		if (aEstClaimMoney != null && !aEstClaimMoney.equals(""))
		{
			Double tDouble = new Double(aEstClaimMoney);
			double d = tDouble.doubleValue();
			EstClaimMoney = d;
		}
	}

	/**
	* 0给付<p>
	* 1拒付
	*/
	public String getGiveType()
	{
		return GiveType;
	}
	public void setGiveType(String aGiveType)
	{
		if(aGiveType!=null && aGiveType.length()>10)
			throw new IllegalArgumentException("赔付结论GiveType值"+aGiveType+"的长度"+aGiveType.length()+"大于最大值10");
		GiveType = aGiveType;
	}
	public String getGiveTypeDesc()
	{
		return GiveTypeDesc;
	}
	public void setGiveTypeDesc(String aGiveTypeDesc)
	{
		if(aGiveTypeDesc!=null && aGiveTypeDesc.length()>1000)
			throw new IllegalArgumentException("赔付结论描述GiveTypeDesc值"+aGiveTypeDesc+"的长度"+aGiveTypeDesc.length()+"大于最大值1000");
		GiveTypeDesc = aGiveTypeDesc;
	}
	public String getGiveReason()
	{
		return GiveReason;
	}
	public void setGiveReason(String aGiveReason)
	{
		if(aGiveReason!=null && aGiveReason.length()>10)
			throw new IllegalArgumentException("赔付结论依据GiveReason值"+aGiveReason+"的长度"+aGiveReason.length()+"大于最大值10");
		GiveReason = aGiveReason;
	}
	public String getGiveReasonDesc()
	{
		return GiveReasonDesc;
	}
	public void setGiveReasonDesc(String aGiveReasonDesc)
	{
		if(aGiveReasonDesc!=null && aGiveReasonDesc.length()>1000)
			throw new IllegalArgumentException("赔付结论依据描述GiveReasonDesc值"+aGiveReasonDesc+"的长度"+aGiveReasonDesc.length()+"大于最大值1000");
		GiveReasonDesc = aGiveReasonDesc;
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

	public double getYearBonus()
	{
		return YearBonus;
	}
	public void setYearBonus(double aYearBonus)
	{
		YearBonus = aYearBonus;
	}
	public void setYearBonus(String aYearBonus)
	{
		if (aYearBonus != null && !aYearBonus.equals(""))
		{
			Double tDouble = new Double(aYearBonus);
			double d = tDouble.doubleValue();
			YearBonus = d;
		}
	}

	public double getEndBonus()
	{
		return EndBonus;
	}
	public void setEndBonus(double aEndBonus)
	{
		EndBonus = aEndBonus;
	}
	public void setEndBonus(String aEndBonus)
	{
		if (aEndBonus != null && !aEndBonus.equals(""))
		{
			Double tDouble = new Double(aEndBonus);
			double d = tDouble.doubleValue();
			EndBonus = d;
		}
	}

	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* 00- 投保人<p>
	* 01- 被保人<p>
	* <p>
	* [不用了]<p>
	* 0 －－ 被保人<p>
	* 1 －－ 投保人<p>
	* 2 －－ 连带被保人
	*/
	public String getCasePolType()
	{
		return CasePolType;
	}
	public void setCasePolType(String aCasePolType)
	{
		if(aCasePolType!=null && aCasePolType.length()>6)
			throw new IllegalArgumentException("给付类型CasePolType值"+aCasePolType+"的长度"+aCasePolType.length()+"大于最大值6");
		CasePolType = aCasePolType;
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
	/**
	* 0没有预付<p>
	* 1已经预付
	*/
	public String getPrepayFlag()
	{
		return PrepayFlag;
	}
	public void setPrepayFlag(String aPrepayFlag)
	{
		if(aPrepayFlag!=null && aPrepayFlag.length()>6)
			throw new IllegalArgumentException("预付标志PrepayFlag值"+aPrepayFlag+"的长度"+aPrepayFlag.length()+"大于最大值6");
		PrepayFlag = aPrepayFlag;
	}
	public double getPrepaySum()
	{
		return PrepaySum;
	}
	public void setPrepaySum(double aPrepaySum)
	{
		PrepaySum = aPrepaySum;
	}
	public void setPrepaySum(String aPrepaySum)
	{
		if (aPrepaySum != null && !aPrepaySum.equals(""))
		{
			Double tDouble = new Double(aPrepaySum);
			double d = tDouble.doubleValue();
			PrepaySum = d;
		}
	}

	/**
	* 0未做保全<p>
	* 1已做保全LPGet不发生变化,<p>
	* 2已做保全LPGet发生变化
	*/
	public String getPosFlag()
	{
		return PosFlag;
	}
	public void setPosFlag(String aPosFlag)
	{
		if(aPosFlag!=null && aPosFlag.length()>6)
			throw new IllegalArgumentException("保全标志PosFlag值"+aPosFlag+"的长度"+aPosFlag.length()+"大于最大值6");
		PosFlag = aPosFlag;
	}
	public String getPosEdorNo()
	{
		return PosEdorNo;
	}
	public void setPosEdorNo(String aPosEdorNo)
	{
		if(aPosEdorNo!=null && aPosEdorNo.length()>20)
			throw new IllegalArgumentException("保全批单号[出险之后]PosEdorNo值"+aPosEdorNo+"的长度"+aPosEdorNo.length()+"大于最大值20");
		PosEdorNo = aPosEdorNo;
	}
	public String getEffectOnMainRisk()
	{
		return EffectOnMainRisk;
	}
	public void setEffectOnMainRisk(String aEffectOnMainRisk)
	{
		if(aEffectOnMainRisk!=null && aEffectOnMainRisk.length()>6)
			throw new IllegalArgumentException("附险影响主险标志EffectOnMainRisk值"+aEffectOnMainRisk+"的长度"+aEffectOnMainRisk.length()+"大于最大值6");
		EffectOnMainRisk = aEffectOnMainRisk;
	}
	public String getRiskCalCode()
	{
		return RiskCalCode;
	}
	public void setRiskCalCode(String aRiskCalCode)
	{
		if(aRiskCalCode!=null && aRiskCalCode.length()>400)
			throw new IllegalArgumentException("附险影响主险的公式RiskCalCode值"+aRiskCalCode+"的长度"+aRiskCalCode.length()+"大于最大值400");
		RiskCalCode = aRiskCalCode;
	}
	public double getAddAmnt()
	{
		return AddAmnt;
	}
	public void setAddAmnt(double aAddAmnt)
	{
		AddAmnt = aAddAmnt;
	}
	public void setAddAmnt(String aAddAmnt)
	{
		if (aAddAmnt != null && !aAddAmnt.equals(""))
		{
			Double tDouble = new Double(aAddAmnt);
			double d = tDouble.doubleValue();
			AddAmnt = d;
		}
	}

	public String getNBPolNo()
	{
		return NBPolNo;
	}
	public void setNBPolNo(String aNBPolNo)
	{
		if(aNBPolNo!=null && aNBPolNo.length()>20)
			throw new IllegalArgumentException("承保时保单号NBPolNo值"+aNBPolNo+"的长度"+aNBPolNo.length()+"大于最大值20");
		NBPolNo = aNBPolNo;
	}
	public String getPosEdorType()
	{
		return PosEdorType;
	}
	public void setPosEdorType(String aPosEdorType)
	{
		if(aPosEdorType!=null && aPosEdorType.length()>6)
			throw new IllegalArgumentException("保全批改类型PosEdorType值"+aPosEdorType+"的长度"+aPosEdorType.length()+"大于最大值6");
		PosEdorType = aPosEdorType;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		if(aRiskType!=null && aRiskType.length()>6)
			throw new IllegalArgumentException("险种类型RiskType值"+aRiskType+"的长度"+aRiskType.length()+"大于最大值6");
		RiskType = aRiskType;
	}
	/**
	* [用于权限分配时的扣除额]
	*/
	public double getPopedomPay()
	{
		return PopedomPay;
	}
	public void setPopedomPay(double aPopedomPay)
	{
		PopedomPay = aPopedomPay;
	}
	public void setPopedomPay(String aPopedomPay)
	{
		if (aPopedomPay != null && !aPopedomPay.equals(""))
		{
			Double tDouble = new Double(aPopedomPay);
			double d = tDouble.doubleValue();
			PopedomPay = d;
		}
	}

	/**
	* [暂不用]
	*/
	public double getInsFeePay()
	{
		return InsFeePay;
	}
	public void setInsFeePay(double aInsFeePay)
	{
		InsFeePay = aInsFeePay;
	}
	public void setInsFeePay(String aInsFeePay)
	{
		if (aInsFeePay != null && !aInsFeePay.equals(""))
		{
			Double tDouble = new Double(aInsFeePay);
			double d = tDouble.doubleValue();
			InsFeePay = d;
		}
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
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>10)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值10");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getExpandFlag()
	{
		return ExpandFlag;
	}
	public void setExpandFlag(String aExpandFlag)
	{
		if(aExpandFlag!=null && aExpandFlag.length()>2)
			throw new IllegalArgumentException("拓展标志ExpandFlag值"+aExpandFlag+"的长度"+aExpandFlag.length()+"大于最大值2");
		ExpandFlag = aExpandFlag;
	}
	public String getExpandDutyCode()
	{
		return ExpandDutyCode;
	}
	public void setExpandDutyCode(String aExpandDutyCode)
	{
		if(aExpandDutyCode!=null && aExpandDutyCode.length()>10)
			throw new IllegalArgumentException("拓展责任ExpandDutyCode值"+aExpandDutyCode+"的长度"+aExpandDutyCode.length()+"大于最大值10");
		ExpandDutyCode = aExpandDutyCode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
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
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	* 使用另外一个 LLToClaimDutySchema 对象给 Schema 赋值
	* @param: aLLToClaimDutySchema LLToClaimDutySchema
	**/
	public void setSchema(LLToClaimDutySchema aLLToClaimDutySchema)
	{
		this.CaseNo = aLLToClaimDutySchema.getCaseNo();
		this.PolNo = aLLToClaimDutySchema.getPolNo();
		this.PolSort = aLLToClaimDutySchema.getPolSort();
		this.PolType = aLLToClaimDutySchema.getPolType();
		this.DutyCode = aLLToClaimDutySchema.getDutyCode();
		this.GetDutyCode = aLLToClaimDutySchema.getGetDutyCode();
		this.GetDutyKind = aLLToClaimDutySchema.getGetDutyKind();
		this.CaseRelaNo = aLLToClaimDutySchema.getCaseRelaNo();
		this.DefoType = aLLToClaimDutySchema.getDefoType();
		this.SubRptNo = aLLToClaimDutySchema.getSubRptNo();
		this.GrpContNo = aLLToClaimDutySchema.getGrpContNo();
		this.GrpPolNo = aLLToClaimDutySchema.getGrpPolNo();
		this.ContNo = aLLToClaimDutySchema.getContNo();
		this.KindCode = aLLToClaimDutySchema.getKindCode();
		this.RiskCode = aLLToClaimDutySchema.getRiskCode();
		this.RiskVer = aLLToClaimDutySchema.getRiskVer();
		this.PolMngCom = aLLToClaimDutySchema.getPolMngCom();
		this.ClaimCount = aLLToClaimDutySchema.getClaimCount();
		this.EstClaimMoney = aLLToClaimDutySchema.getEstClaimMoney();
		this.GiveType = aLLToClaimDutySchema.getGiveType();
		this.GiveTypeDesc = aLLToClaimDutySchema.getGiveTypeDesc();
		this.GiveReason = aLLToClaimDutySchema.getGiveReason();
		this.GiveReasonDesc = aLLToClaimDutySchema.getGiveReasonDesc();
		this.Amnt = aLLToClaimDutySchema.getAmnt();
		this.YearBonus = aLLToClaimDutySchema.getYearBonus();
		this.EndBonus = aLLToClaimDutySchema.getEndBonus();
		this.GracePeriod = aLLToClaimDutySchema.getGracePeriod();
		this.CasePolType = aLLToClaimDutySchema.getCasePolType();
		this.CustomerNo = aLLToClaimDutySchema.getCustomerNo();
		this.PrepayFlag = aLLToClaimDutySchema.getPrepayFlag();
		this.PrepaySum = aLLToClaimDutySchema.getPrepaySum();
		this.PosFlag = aLLToClaimDutySchema.getPosFlag();
		this.PosEdorNo = aLLToClaimDutySchema.getPosEdorNo();
		this.EffectOnMainRisk = aLLToClaimDutySchema.getEffectOnMainRisk();
		this.RiskCalCode = aLLToClaimDutySchema.getRiskCalCode();
		this.AddAmnt = aLLToClaimDutySchema.getAddAmnt();
		this.NBPolNo = aLLToClaimDutySchema.getNBPolNo();
		this.PosEdorType = aLLToClaimDutySchema.getPosEdorType();
		this.RiskType = aLLToClaimDutySchema.getRiskType();
		this.PopedomPay = aLLToClaimDutySchema.getPopedomPay();
		this.InsFeePay = aLLToClaimDutySchema.getInsFeePay();
		this.Currency = aLLToClaimDutySchema.getCurrency();
		this.ClmNo = aLLToClaimDutySchema.getClmNo();
		this.BussType = aLLToClaimDutySchema.getBussType();
		this.BussNo = aLLToClaimDutySchema.getBussNo();
		this.ExpandFlag = aLLToClaimDutySchema.getExpandFlag();
		this.ExpandDutyCode = aLLToClaimDutySchema.getExpandDutyCode();
		this.ManageCom = aLLToClaimDutySchema.getManageCom();
		this.ComCode = aLLToClaimDutySchema.getComCode();
		this.MakeOperator = aLLToClaimDutySchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLToClaimDutySchema.getMakeDate());
		this.MakeTime = aLLToClaimDutySchema.getMakeTime();
		this.ModifyOperator = aLLToClaimDutySchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLToClaimDutySchema.getModifyDate());
		this.ModifyTime = aLLToClaimDutySchema.getModifyTime();
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
			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("PolSort") == null )
				this.PolSort = null;
			else
				this.PolSort = rs.getString("PolSort").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("SubRptNo") == null )
				this.SubRptNo = null;
			else
				this.SubRptNo = rs.getString("SubRptNo").trim();

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

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("PolMngCom") == null )
				this.PolMngCom = null;
			else
				this.PolMngCom = rs.getString("PolMngCom").trim();

			this.ClaimCount = rs.getInt("ClaimCount");
			this.EstClaimMoney = rs.getDouble("EstClaimMoney");
			if( rs.getString("GiveType") == null )
				this.GiveType = null;
			else
				this.GiveType = rs.getString("GiveType").trim();

			if( rs.getString("GiveTypeDesc") == null )
				this.GiveTypeDesc = null;
			else
				this.GiveTypeDesc = rs.getString("GiveTypeDesc").trim();

			if( rs.getString("GiveReason") == null )
				this.GiveReason = null;
			else
				this.GiveReason = rs.getString("GiveReason").trim();

			if( rs.getString("GiveReasonDesc") == null )
				this.GiveReasonDesc = null;
			else
				this.GiveReasonDesc = rs.getString("GiveReasonDesc").trim();

			this.Amnt = rs.getDouble("Amnt");
			this.YearBonus = rs.getDouble("YearBonus");
			this.EndBonus = rs.getDouble("EndBonus");
			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("CasePolType") == null )
				this.CasePolType = null;
			else
				this.CasePolType = rs.getString("CasePolType").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("PrepayFlag") == null )
				this.PrepayFlag = null;
			else
				this.PrepayFlag = rs.getString("PrepayFlag").trim();

			this.PrepaySum = rs.getDouble("PrepaySum");
			if( rs.getString("PosFlag") == null )
				this.PosFlag = null;
			else
				this.PosFlag = rs.getString("PosFlag").trim();

			if( rs.getString("PosEdorNo") == null )
				this.PosEdorNo = null;
			else
				this.PosEdorNo = rs.getString("PosEdorNo").trim();

			if( rs.getString("EffectOnMainRisk") == null )
				this.EffectOnMainRisk = null;
			else
				this.EffectOnMainRisk = rs.getString("EffectOnMainRisk").trim();

			if( rs.getString("RiskCalCode") == null )
				this.RiskCalCode = null;
			else
				this.RiskCalCode = rs.getString("RiskCalCode").trim();

			this.AddAmnt = rs.getDouble("AddAmnt");
			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("PosEdorType") == null )
				this.PosEdorType = null;
			else
				this.PosEdorType = rs.getString("PosEdorType").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			this.PopedomPay = rs.getDouble("PopedomPay");
			this.InsFeePay = rs.getDouble("InsFeePay");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("ExpandFlag") == null )
				this.ExpandFlag = null;
			else
				this.ExpandFlag = rs.getString("ExpandFlag").trim();

			if( rs.getString("ExpandDutyCode") == null )
				this.ExpandDutyCode = null;
			else
				this.ExpandDutyCode = rs.getString("ExpandDutyCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLToClaimDuty表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLToClaimDutySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLToClaimDutySchema getSchema()
	{
		LLToClaimDutySchema aLLToClaimDutySchema = new LLToClaimDutySchema();
		aLLToClaimDutySchema.setSchema(this);
		return aLLToClaimDutySchema;
	}

	public LLToClaimDutyDB getDB()
	{
		LLToClaimDutyDB aDBOper = new LLToClaimDutyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLToClaimDuty描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubRptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EstClaimMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(YearBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrepayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrepaySum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosEdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EffectOnMainRisk)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosEdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PopedomPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsFeePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpandFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpandDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLToClaimDuty>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SubRptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ClaimCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			EstClaimMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			GiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GiveTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			GiveReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			GiveReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			YearBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			EndBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			CasePolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PrepayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			PrepaySum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			PosFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PosEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			EffectOnMainRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AddAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			PosEdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			PopedomPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			InsFeePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			ExpandFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			ExpandDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLToClaimDutySchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("PolSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolSort));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRptNo));
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMngCom));
		}
		if (FCode.equalsIgnoreCase("ClaimCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCount));
		}
		if (FCode.equalsIgnoreCase("EstClaimMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EstClaimMoney));
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveType));
		}
		if (FCode.equalsIgnoreCase("GiveTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveTypeDesc));
		}
		if (FCode.equalsIgnoreCase("GiveReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveReason));
		}
		if (FCode.equalsIgnoreCase("GiveReasonDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveReasonDesc));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("YearBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YearBonus));
		}
		if (FCode.equalsIgnoreCase("EndBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndBonus));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePolType));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("PrepayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepayFlag));
		}
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepaySum));
		}
		if (FCode.equalsIgnoreCase("PosFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosFlag));
		}
		if (FCode.equalsIgnoreCase("PosEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosEdorNo));
		}
		if (FCode.equalsIgnoreCase("EffectOnMainRisk"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EffectOnMainRisk));
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalCode));
		}
		if (FCode.equalsIgnoreCase("AddAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddAmnt));
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("PosEdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosEdorType));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("PopedomPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopedomPay));
		}
		if (FCode.equalsIgnoreCase("InsFeePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsFeePay));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("ExpandFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpandFlag));
		}
		if (FCode.equalsIgnoreCase("ExpandDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpandDutyCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolSort);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SubRptNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 17:
				strFieldValue = String.valueOf(ClaimCount);
				break;
			case 18:
				strFieldValue = String.valueOf(EstClaimMoney);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(GiveType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GiveTypeDesc);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(GiveReason);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(GiveReasonDesc);
				break;
			case 23:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 24:
				strFieldValue = String.valueOf(YearBonus);
				break;
			case 25:
				strFieldValue = String.valueOf(EndBonus);
				break;
			case 26:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CasePolType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(PrepayFlag);
				break;
			case 30:
				strFieldValue = String.valueOf(PrepaySum);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(PosFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PosEdorNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(EffectOnMainRisk);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 35:
				strFieldValue = String.valueOf(AddAmnt);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(PosEdorType);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 39:
				strFieldValue = String.valueOf(PopedomPay);
				break;
			case 40:
				strFieldValue = String.valueOf(InsFeePay);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ExpandFlag);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(ExpandDutyCode);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 54:
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

		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("PolSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolSort = FValue.trim();
			}
			else
				PolSort = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoType = FValue.trim();
			}
			else
				DefoType = null;
		}
		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRptNo = FValue.trim();
			}
			else
				SubRptNo = null;
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
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
		if (FCode.equalsIgnoreCase("ClaimCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClaimCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("EstClaimMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EstClaimMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveType = FValue.trim();
			}
			else
				GiveType = null;
		}
		if (FCode.equalsIgnoreCase("GiveTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveTypeDesc = FValue.trim();
			}
			else
				GiveTypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("GiveReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveReason = FValue.trim();
			}
			else
				GiveReason = null;
		}
		if (FCode.equalsIgnoreCase("GiveReasonDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveReasonDesc = FValue.trim();
			}
			else
				GiveReasonDesc = null;
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
		if (FCode.equalsIgnoreCase("YearBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YearBonus = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EndBonus = d;
			}
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePolType = FValue.trim();
			}
			else
				CasePolType = null;
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
		if (FCode.equalsIgnoreCase("PrepayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrepayFlag = FValue.trim();
			}
			else
				PrepayFlag = null;
		}
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrepaySum = d;
			}
		}
		if (FCode.equalsIgnoreCase("PosFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosFlag = FValue.trim();
			}
			else
				PosFlag = null;
		}
		if (FCode.equalsIgnoreCase("PosEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosEdorNo = FValue.trim();
			}
			else
				PosEdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EffectOnMainRisk"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EffectOnMainRisk = FValue.trim();
			}
			else
				EffectOnMainRisk = null;
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalCode = FValue.trim();
			}
			else
				RiskCalCode = null;
		}
		if (FCode.equalsIgnoreCase("AddAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
		}
		if (FCode.equalsIgnoreCase("PosEdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosEdorType = FValue.trim();
			}
			else
				PosEdorType = null;
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
		if (FCode.equalsIgnoreCase("PopedomPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PopedomPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsFeePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsFeePay = d;
			}
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("ExpandFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpandFlag = FValue.trim();
			}
			else
				ExpandFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExpandDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpandDutyCode = FValue.trim();
			}
			else
				ExpandDutyCode = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LLToClaimDutySchema other = (LLToClaimDutySchema)otherObject;
		return
			CaseNo.equals(other.getCaseNo())
			&& PolNo.equals(other.getPolNo())
			&& PolSort.equals(other.getPolSort())
			&& PolType.equals(other.getPolType())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& DefoType.equals(other.getDefoType())
			&& SubRptNo.equals(other.getSubRptNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& ClaimCount == other.getClaimCount()
			&& EstClaimMoney == other.getEstClaimMoney()
			&& GiveType.equals(other.getGiveType())
			&& GiveTypeDesc.equals(other.getGiveTypeDesc())
			&& GiveReason.equals(other.getGiveReason())
			&& GiveReasonDesc.equals(other.getGiveReasonDesc())
			&& Amnt == other.getAmnt()
			&& YearBonus == other.getYearBonus()
			&& EndBonus == other.getEndBonus()
			&& GracePeriod == other.getGracePeriod()
			&& CasePolType.equals(other.getCasePolType())
			&& CustomerNo.equals(other.getCustomerNo())
			&& PrepayFlag.equals(other.getPrepayFlag())
			&& PrepaySum == other.getPrepaySum()
			&& PosFlag.equals(other.getPosFlag())
			&& PosEdorNo.equals(other.getPosEdorNo())
			&& EffectOnMainRisk.equals(other.getEffectOnMainRisk())
			&& RiskCalCode.equals(other.getRiskCalCode())
			&& AddAmnt == other.getAddAmnt()
			&& NBPolNo.equals(other.getNBPolNo())
			&& PosEdorType.equals(other.getPosEdorType())
			&& RiskType.equals(other.getRiskType())
			&& PopedomPay == other.getPopedomPay()
			&& InsFeePay == other.getInsFeePay()
			&& Currency.equals(other.getCurrency())
			&& ClmNo.equals(other.getClmNo())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& ExpandFlag.equals(other.getExpandFlag())
			&& ExpandDutyCode.equals(other.getExpandDutyCode())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("CaseNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolSort") ) {
			return 2;
		}
		if( strFieldName.equals("PolType") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 6;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 7;
		}
		if( strFieldName.equals("DefoType") ) {
			return 8;
		}
		if( strFieldName.equals("SubRptNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 10;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 11;
		}
		if( strFieldName.equals("ContNo") ) {
			return 12;
		}
		if( strFieldName.equals("KindCode") ) {
			return 13;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 14;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 15;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 16;
		}
		if( strFieldName.equals("ClaimCount") ) {
			return 17;
		}
		if( strFieldName.equals("EstClaimMoney") ) {
			return 18;
		}
		if( strFieldName.equals("GiveType") ) {
			return 19;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return 20;
		}
		if( strFieldName.equals("GiveReason") ) {
			return 21;
		}
		if( strFieldName.equals("GiveReasonDesc") ) {
			return 22;
		}
		if( strFieldName.equals("Amnt") ) {
			return 23;
		}
		if( strFieldName.equals("YearBonus") ) {
			return 24;
		}
		if( strFieldName.equals("EndBonus") ) {
			return 25;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 26;
		}
		if( strFieldName.equals("CasePolType") ) {
			return 27;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 28;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return 29;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return 30;
		}
		if( strFieldName.equals("PosFlag") ) {
			return 31;
		}
		if( strFieldName.equals("PosEdorNo") ) {
			return 32;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return 33;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 34;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return 35;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 36;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return 37;
		}
		if( strFieldName.equals("RiskType") ) {
			return 38;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return 39;
		}
		if( strFieldName.equals("InsFeePay") ) {
			return 40;
		}
		if( strFieldName.equals("Currency") ) {
			return 41;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 42;
		}
		if( strFieldName.equals("BussType") ) {
			return 43;
		}
		if( strFieldName.equals("BussNo") ) {
			return 44;
		}
		if( strFieldName.equals("ExpandFlag") ) {
			return 45;
		}
		if( strFieldName.equals("ExpandDutyCode") ) {
			return 46;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 47;
		}
		if( strFieldName.equals("ComCode") ) {
			return 48;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 49;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 50;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 51;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 52;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 53;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 54;
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
				strFieldName = "CaseNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "PolSort";
				break;
			case 3:
				strFieldName = "PolType";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "GetDutyCode";
				break;
			case 6:
				strFieldName = "GetDutyKind";
				break;
			case 7:
				strFieldName = "CaseRelaNo";
				break;
			case 8:
				strFieldName = "DefoType";
				break;
			case 9:
				strFieldName = "SubRptNo";
				break;
			case 10:
				strFieldName = "GrpContNo";
				break;
			case 11:
				strFieldName = "GrpPolNo";
				break;
			case 12:
				strFieldName = "ContNo";
				break;
			case 13:
				strFieldName = "KindCode";
				break;
			case 14:
				strFieldName = "RiskCode";
				break;
			case 15:
				strFieldName = "RiskVer";
				break;
			case 16:
				strFieldName = "PolMngCom";
				break;
			case 17:
				strFieldName = "ClaimCount";
				break;
			case 18:
				strFieldName = "EstClaimMoney";
				break;
			case 19:
				strFieldName = "GiveType";
				break;
			case 20:
				strFieldName = "GiveTypeDesc";
				break;
			case 21:
				strFieldName = "GiveReason";
				break;
			case 22:
				strFieldName = "GiveReasonDesc";
				break;
			case 23:
				strFieldName = "Amnt";
				break;
			case 24:
				strFieldName = "YearBonus";
				break;
			case 25:
				strFieldName = "EndBonus";
				break;
			case 26:
				strFieldName = "GracePeriod";
				break;
			case 27:
				strFieldName = "CasePolType";
				break;
			case 28:
				strFieldName = "CustomerNo";
				break;
			case 29:
				strFieldName = "PrepayFlag";
				break;
			case 30:
				strFieldName = "PrepaySum";
				break;
			case 31:
				strFieldName = "PosFlag";
				break;
			case 32:
				strFieldName = "PosEdorNo";
				break;
			case 33:
				strFieldName = "EffectOnMainRisk";
				break;
			case 34:
				strFieldName = "RiskCalCode";
				break;
			case 35:
				strFieldName = "AddAmnt";
				break;
			case 36:
				strFieldName = "NBPolNo";
				break;
			case 37:
				strFieldName = "PosEdorType";
				break;
			case 38:
				strFieldName = "RiskType";
				break;
			case 39:
				strFieldName = "PopedomPay";
				break;
			case 40:
				strFieldName = "InsFeePay";
				break;
			case 41:
				strFieldName = "Currency";
				break;
			case 42:
				strFieldName = "ClmNo";
				break;
			case 43:
				strFieldName = "BussType";
				break;
			case 44:
				strFieldName = "BussNo";
				break;
			case 45:
				strFieldName = "ExpandFlag";
				break;
			case 46:
				strFieldName = "ExpandDutyCode";
				break;
			case 47:
				strFieldName = "ManageCom";
				break;
			case 48:
				strFieldName = "ComCode";
				break;
			case 49:
				strFieldName = "MakeOperator";
				break;
			case 50:
				strFieldName = "MakeDate";
				break;
			case 51:
				strFieldName = "MakeTime";
				break;
			case 52:
				strFieldName = "ModifyOperator";
				break;
			case 53:
				strFieldName = "ModifyDate";
				break;
			case 54:
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRptNo") ) {
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
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EstClaimMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GiveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveReasonDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YearBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CasePolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PosFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PosEdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsFeePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpandFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpandDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
