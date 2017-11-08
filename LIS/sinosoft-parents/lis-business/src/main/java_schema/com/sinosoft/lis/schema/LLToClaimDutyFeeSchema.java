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
import com.sinosoft.lis.db.LLToClaimDutyFeeDB;

/*
 * <p>ClassName: LLToClaimDutyFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class LLToClaimDutyFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLToClaimDutyFeeSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 保单号 */
	private String PolNo;
	/** 给付责任类型 */
	private String GetDutyType;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 费用类型 */
	private String DutyFeeType;
	/** 费用代码 */
	private String DutyFeeCode;
	/** 费用名称 */
	private String DutyFeeName;
	/** 序号 */
	private String DutyFeeStaNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 医院编号 */
	private String HosID;
	/** 医院名称 */
	private String HosName;
	/** 医院等级 */
	private String HosGrade;
	/** 开始时间 */
	private Date StartDate;
	/** 结束时间 */
	private Date EndDate;
	/** 天数 */
	private int DayCount;
	/** 伤残类型 */
	private String DefoType;
	/** 伤残代码 */
	private String DefoCode;
	/** 伤残代码名称 */
	private String DefoeName;
	/** 残疾给付比例 */
	private double DefoRate;
	/** 实际给付比例 */
	private double RealRate;
	/** 原始金额 */
	private double OriSum;
	/** 调整金额 */
	private double AdjSum;
	/** 理算金额 */
	private double CalSum;
	/** 调整原因 */
	private String AdjReason;
	/** 调整备注 */
	private String AdjRemark;
	/** 免赔额 */
	private double OutDutyAmnt;
	/** 免赔比例 */
	private double OutDutyRate;
	/** 免赔类型 */
	private String OutDutyType;
	/** 责任编码 */
	private String DutyCode;
	/** 承保时保单号 */
	private String NBPolNo;
	/** 分割单受益金额 */
	private double CutApartAmnt;
	/** 费用项目类型 */
	private String FeeItemType;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 社保给付金额 */
	private double SocPay;
	/** 第三方给付金额 */
	private double OthPay;
	/** 币别 */
	private String Currency;
	/** 业务类型 */
	private String BussType;
	/** 业务号 */
	private String BussNo;
	/** 账单编码 */
	private String BillNo;
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
	/** 次数 */
	private String Times;

	public static final int FIELDNUM = 52;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLToClaimDutyFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[10];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "PolNo";
		pk[3] = "GetDutyType";
		pk[4] = "GetDutyCode";
		pk[5] = "DutyFeeType";
		pk[6] = "DutyFeeCode";
		pk[7] = "DutyFeeStaNo";
		pk[8] = "DutyCode";
		pk[9] = "CustomerNo";

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
		LLToClaimDutyFeeSchema cloned = (LLToClaimDutyFeeSchema)super.clone();
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
	public String getGetDutyType()
	{
		return GetDutyType;
	}
	public void setGetDutyType(String aGetDutyType)
	{
		if(aGetDutyType!=null && aGetDutyType.length()>10)
			throw new IllegalArgumentException("给付责任类型GetDutyType值"+aGetDutyType+"的长度"+aGetDutyType.length()+"大于最大值10");
		GetDutyType = aGetDutyType;
	}
	/**
	* 保项编码
	*/
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
	* 指门诊，住院，手术，特殊，其他等5钟
	*/
	public String getDutyFeeType()
	{
		return DutyFeeType;
	}
	public void setDutyFeeType(String aDutyFeeType)
	{
		if(aDutyFeeType!=null && aDutyFeeType.length()>10)
			throw new IllegalArgumentException("费用类型DutyFeeType值"+aDutyFeeType+"的长度"+aDutyFeeType.length()+"大于最大值10");
		DutyFeeType = aDutyFeeType;
	}
	/**
	* 住院费，X光费等费用明细
	*/
	public String getDutyFeeCode()
	{
		return DutyFeeCode;
	}
	public void setDutyFeeCode(String aDutyFeeCode)
	{
		if(aDutyFeeCode!=null && aDutyFeeCode.length()>10)
			throw new IllegalArgumentException("费用代码DutyFeeCode值"+aDutyFeeCode+"的长度"+aDutyFeeCode.length()+"大于最大值10");
		DutyFeeCode = aDutyFeeCode;
	}
	/**
	* 住院费，X光费等费用明细
	*/
	public String getDutyFeeName()
	{
		return DutyFeeName;
	}
	public void setDutyFeeName(String aDutyFeeName)
	{
		if(aDutyFeeName!=null && aDutyFeeName.length()>100)
			throw new IllegalArgumentException("费用名称DutyFeeName值"+aDutyFeeName+"的长度"+aDutyFeeName.length()+"大于最大值100");
		DutyFeeName = aDutyFeeName;
	}
	/**
	* 对应医疗费用录入的序号
	*/
	public String getDutyFeeStaNo()
	{
		return DutyFeeStaNo;
	}
	public void setDutyFeeStaNo(String aDutyFeeStaNo)
	{
		if(aDutyFeeStaNo!=null && aDutyFeeStaNo.length()>20)
			throw new IllegalArgumentException("序号DutyFeeStaNo值"+aDutyFeeStaNo+"的长度"+aDutyFeeStaNo.length()+"大于最大值20");
		DutyFeeStaNo = aDutyFeeStaNo;
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
	public String getHosID()
	{
		return HosID;
	}
	public void setHosID(String aHosID)
	{
		if(aHosID!=null && aHosID.length()>20)
			throw new IllegalArgumentException("医院编号HosID值"+aHosID+"的长度"+aHosID.length()+"大于最大值20");
		HosID = aHosID;
	}
	public String getHosName()
	{
		return HosName;
	}
	public void setHosName(String aHosName)
	{
		if(aHosName!=null && aHosName.length()>60)
			throw new IllegalArgumentException("医院名称HosName值"+aHosName+"的长度"+aHosName.length()+"大于最大值60");
		HosName = aHosName;
	}
	public String getHosGrade()
	{
		return HosGrade;
	}
	public void setHosGrade(String aHosGrade)
	{
		if(aHosGrade!=null && aHosGrade.length()>10)
			throw new IllegalArgumentException("医院等级HosGrade值"+aHosGrade+"的长度"+aHosGrade.length()+"大于最大值10");
		HosGrade = aHosGrade;
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

	public int getDayCount()
	{
		return DayCount;
	}
	public void setDayCount(int aDayCount)
	{
		DayCount = aDayCount;
	}
	public void setDayCount(String aDayCount)
	{
		if (aDayCount != null && !aDayCount.equals(""))
		{
			Integer tInteger = new Integer(aDayCount);
			int i = tInteger.intValue();
			DayCount = i;
		}
	}

	/**
	* 1--普通伤残(使用7级34项残疾给付���)<p>
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
	/**
	* 七级三十四项
	*/
	public String getDefoCode()
	{
		return DefoCode;
	}
	public void setDefoCode(String aDefoCode)
	{
		if(aDefoCode!=null && aDefoCode.length()>100)
			throw new IllegalArgumentException("伤残代码DefoCode值"+aDefoCode+"的长度"+aDefoCode.length()+"大于最大值100");
		DefoCode = aDefoCode;
	}
	public String getDefoeName()
	{
		return DefoeName;
	}
	public void setDefoeName(String aDefoeName)
	{
		if(aDefoeName!=null && aDefoeName.length()>600)
			throw new IllegalArgumentException("伤残代码名称DefoeName值"+aDefoeName+"的长度"+aDefoeName.length()+"大于最大值600");
		DefoeName = aDefoeName;
	}
	public double getDefoRate()
	{
		return DefoRate;
	}
	public void setDefoRate(double aDefoRate)
	{
		DefoRate = aDefoRate;
	}
	public void setDefoRate(String aDefoRate)
	{
		if (aDefoRate != null && !aDefoRate.equals(""))
		{
			Double tDouble = new Double(aDefoRate);
			double d = tDouble.doubleValue();
			DefoRate = d;
		}
	}

	public double getRealRate()
	{
		return RealRate;
	}
	public void setRealRate(double aRealRate)
	{
		RealRate = aRealRate;
	}
	public void setRealRate(String aRealRate)
	{
		if (aRealRate != null && !aRealRate.equals(""))
		{
			Double tDouble = new Double(aRealRate);
			double d = tDouble.doubleValue();
			RealRate = d;
		}
	}

	public double getOriSum()
	{
		return OriSum;
	}
	public void setOriSum(double aOriSum)
	{
		OriSum = aOriSum;
	}
	public void setOriSum(String aOriSum)
	{
		if (aOriSum != null && !aOriSum.equals(""))
		{
			Double tDouble = new Double(aOriSum);
			double d = tDouble.doubleValue();
			OriSum = d;
		}
	}

	public double getAdjSum()
	{
		return AdjSum;
	}
	public void setAdjSum(double aAdjSum)
	{
		AdjSum = aAdjSum;
	}
	public void setAdjSum(String aAdjSum)
	{
		if (aAdjSum != null && !aAdjSum.equals(""))
		{
			Double tDouble = new Double(aAdjSum);
			double d = tDouble.doubleValue();
			AdjSum = d;
		}
	}

	public double getCalSum()
	{
		return CalSum;
	}
	public void setCalSum(double aCalSum)
	{
		CalSum = aCalSum;
	}
	public void setCalSum(String aCalSum)
	{
		if (aCalSum != null && !aCalSum.equals(""))
		{
			Double tDouble = new Double(aCalSum);
			double d = tDouble.doubleValue();
			CalSum = d;
		}
	}

	public String getAdjReason()
	{
		return AdjReason;
	}
	public void setAdjReason(String aAdjReason)
	{
		if(aAdjReason!=null && aAdjReason.length()>6)
			throw new IllegalArgumentException("调整原因AdjReason值"+aAdjReason+"的长度"+aAdjReason.length()+"大于最大值6");
		AdjReason = aAdjReason;
	}
	public String getAdjRemark()
	{
		return AdjRemark;
	}
	public void setAdjRemark(String aAdjRemark)
	{
		if(aAdjRemark!=null && aAdjRemark.length()>1000)
			throw new IllegalArgumentException("调整备注AdjRemark值"+aAdjRemark+"的长度"+aAdjRemark.length()+"大于最大值1000");
		AdjRemark = aAdjRemark;
	}
	public double getOutDutyAmnt()
	{
		return OutDutyAmnt;
	}
	public void setOutDutyAmnt(double aOutDutyAmnt)
	{
		OutDutyAmnt = aOutDutyAmnt;
	}
	public void setOutDutyAmnt(String aOutDutyAmnt)
	{
		if (aOutDutyAmnt != null && !aOutDutyAmnt.equals(""))
		{
			Double tDouble = new Double(aOutDutyAmnt);
			double d = tDouble.doubleValue();
			OutDutyAmnt = d;
		}
	}

	public double getOutDutyRate()
	{
		return OutDutyRate;
	}
	public void setOutDutyRate(double aOutDutyRate)
	{
		OutDutyRate = aOutDutyRate;
	}
	public void setOutDutyRate(String aOutDutyRate)
	{
		if (aOutDutyRate != null && !aOutDutyRate.equals(""))
		{
			Double tDouble = new Double(aOutDutyRate);
			double d = tDouble.doubleValue();
			OutDutyRate = d;
		}
	}

	public String getOutDutyType()
	{
		return OutDutyType;
	}
	public void setOutDutyType(String aOutDutyType)
	{
		if(aOutDutyType!=null && aOutDutyType.length()>6)
			throw new IllegalArgumentException("免赔类型OutDutyType值"+aOutDutyType+"的长度"+aOutDutyType.length()+"大于最大值6");
		OutDutyType = aOutDutyType;
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
	public double getCutApartAmnt()
	{
		return CutApartAmnt;
	}
	public void setCutApartAmnt(double aCutApartAmnt)
	{
		CutApartAmnt = aCutApartAmnt;
	}
	public void setCutApartAmnt(String aCutApartAmnt)
	{
		if (aCutApartAmnt != null && !aCutApartAmnt.equals(""))
		{
			Double tDouble = new Double(aCutApartAmnt);
			double d = tDouble.doubleValue();
			CutApartAmnt = d;
		}
	}

	/**
	* 包括：<p>
	* 待用
	*/
	public String getFeeItemType()
	{
		return FeeItemType;
	}
	public void setFeeItemType(String aFeeItemType)
	{
		if(aFeeItemType!=null && aFeeItemType.length()>6)
			throw new IllegalArgumentException("费用项目类型FeeItemType值"+aFeeItemType+"的长度"+aFeeItemType.length()+"大于最大值6");
		FeeItemType = aFeeItemType;
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
	public double getSocPay()
	{
		return SocPay;
	}
	public void setSocPay(double aSocPay)
	{
		SocPay = aSocPay;
	}
	public void setSocPay(String aSocPay)
	{
		if (aSocPay != null && !aSocPay.equals(""))
		{
			Double tDouble = new Double(aSocPay);
			double d = tDouble.doubleValue();
			SocPay = d;
		}
	}

	public double getOthPay()
	{
		return OthPay;
	}
	public void setOthPay(double aOthPay)
	{
		OthPay = aOthPay;
	}
	public void setOthPay(String aOthPay)
	{
		if (aOthPay != null && !aOthPay.equals(""))
		{
			Double tDouble = new Double(aOthPay);
			double d = tDouble.doubleValue();
			OthPay = d;
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
	public String getBillNo()
	{
		return BillNo;
	}
	public void setBillNo(String aBillNo)
	{
		if(aBillNo!=null && aBillNo.length()>20)
			throw new IllegalArgumentException("账单编码BillNo值"+aBillNo+"的长度"+aBillNo.length()+"大于最大值20");
		BillNo = aBillNo;
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
	public String getTimes()
	{
		return Times;
	}
	public void setTimes(String aTimes)
	{
		if(aTimes!=null && aTimes.length()>8)
			throw new IllegalArgumentException("次数Times值"+aTimes+"的长度"+aTimes.length()+"大于最大值8");
		Times = aTimes;
	}

	/**
	* 使用另外一个 LLToClaimDutyFeeSchema 对象给 Schema 赋值
	* @param: aLLToClaimDutyFeeSchema LLToClaimDutyFeeSchema
	**/
	public void setSchema(LLToClaimDutyFeeSchema aLLToClaimDutyFeeSchema)
	{
		this.ClmNo = aLLToClaimDutyFeeSchema.getClmNo();
		this.CaseNo = aLLToClaimDutyFeeSchema.getCaseNo();
		this.PolNo = aLLToClaimDutyFeeSchema.getPolNo();
		this.GetDutyType = aLLToClaimDutyFeeSchema.getGetDutyType();
		this.GetDutyCode = aLLToClaimDutyFeeSchema.getGetDutyCode();
		this.DutyFeeType = aLLToClaimDutyFeeSchema.getDutyFeeType();
		this.DutyFeeCode = aLLToClaimDutyFeeSchema.getDutyFeeCode();
		this.DutyFeeName = aLLToClaimDutyFeeSchema.getDutyFeeName();
		this.DutyFeeStaNo = aLLToClaimDutyFeeSchema.getDutyFeeStaNo();
		this.KindCode = aLLToClaimDutyFeeSchema.getKindCode();
		this.RiskCode = aLLToClaimDutyFeeSchema.getRiskCode();
		this.RiskVer = aLLToClaimDutyFeeSchema.getRiskVer();
		this.PolMngCom = aLLToClaimDutyFeeSchema.getPolMngCom();
		this.HosID = aLLToClaimDutyFeeSchema.getHosID();
		this.HosName = aLLToClaimDutyFeeSchema.getHosName();
		this.HosGrade = aLLToClaimDutyFeeSchema.getHosGrade();
		this.StartDate = fDate.getDate( aLLToClaimDutyFeeSchema.getStartDate());
		this.EndDate = fDate.getDate( aLLToClaimDutyFeeSchema.getEndDate());
		this.DayCount = aLLToClaimDutyFeeSchema.getDayCount();
		this.DefoType = aLLToClaimDutyFeeSchema.getDefoType();
		this.DefoCode = aLLToClaimDutyFeeSchema.getDefoCode();
		this.DefoeName = aLLToClaimDutyFeeSchema.getDefoeName();
		this.DefoRate = aLLToClaimDutyFeeSchema.getDefoRate();
		this.RealRate = aLLToClaimDutyFeeSchema.getRealRate();
		this.OriSum = aLLToClaimDutyFeeSchema.getOriSum();
		this.AdjSum = aLLToClaimDutyFeeSchema.getAdjSum();
		this.CalSum = aLLToClaimDutyFeeSchema.getCalSum();
		this.AdjReason = aLLToClaimDutyFeeSchema.getAdjReason();
		this.AdjRemark = aLLToClaimDutyFeeSchema.getAdjRemark();
		this.OutDutyAmnt = aLLToClaimDutyFeeSchema.getOutDutyAmnt();
		this.OutDutyRate = aLLToClaimDutyFeeSchema.getOutDutyRate();
		this.OutDutyType = aLLToClaimDutyFeeSchema.getOutDutyType();
		this.DutyCode = aLLToClaimDutyFeeSchema.getDutyCode();
		this.NBPolNo = aLLToClaimDutyFeeSchema.getNBPolNo();
		this.CutApartAmnt = aLLToClaimDutyFeeSchema.getCutApartAmnt();
		this.FeeItemType = aLLToClaimDutyFeeSchema.getFeeItemType();
		this.CustomerNo = aLLToClaimDutyFeeSchema.getCustomerNo();
		this.SocPay = aLLToClaimDutyFeeSchema.getSocPay();
		this.OthPay = aLLToClaimDutyFeeSchema.getOthPay();
		this.Currency = aLLToClaimDutyFeeSchema.getCurrency();
		this.BussType = aLLToClaimDutyFeeSchema.getBussType();
		this.BussNo = aLLToClaimDutyFeeSchema.getBussNo();
		this.BillNo = aLLToClaimDutyFeeSchema.getBillNo();
		this.ManageCom = aLLToClaimDutyFeeSchema.getManageCom();
		this.ComCode = aLLToClaimDutyFeeSchema.getComCode();
		this.MakeOperator = aLLToClaimDutyFeeSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLToClaimDutyFeeSchema.getMakeDate());
		this.MakeTime = aLLToClaimDutyFeeSchema.getMakeTime();
		this.ModifyOperator = aLLToClaimDutyFeeSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLToClaimDutyFeeSchema.getModifyDate());
		this.ModifyTime = aLLToClaimDutyFeeSchema.getModifyTime();
		this.Times = aLLToClaimDutyFeeSchema.getTimes();
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

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GetDutyType") == null )
				this.GetDutyType = null;
			else
				this.GetDutyType = rs.getString("GetDutyType").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("DutyFeeType") == null )
				this.DutyFeeType = null;
			else
				this.DutyFeeType = rs.getString("DutyFeeType").trim();

			if( rs.getString("DutyFeeCode") == null )
				this.DutyFeeCode = null;
			else
				this.DutyFeeCode = rs.getString("DutyFeeCode").trim();

			if( rs.getString("DutyFeeName") == null )
				this.DutyFeeName = null;
			else
				this.DutyFeeName = rs.getString("DutyFeeName").trim();

			if( rs.getString("DutyFeeStaNo") == null )
				this.DutyFeeStaNo = null;
			else
				this.DutyFeeStaNo = rs.getString("DutyFeeStaNo").trim();

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

			if( rs.getString("HosID") == null )
				this.HosID = null;
			else
				this.HosID = rs.getString("HosID").trim();

			if( rs.getString("HosName") == null )
				this.HosName = null;
			else
				this.HosName = rs.getString("HosName").trim();

			if( rs.getString("HosGrade") == null )
				this.HosGrade = null;
			else
				this.HosGrade = rs.getString("HosGrade").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.DayCount = rs.getInt("DayCount");
			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("DefoCode") == null )
				this.DefoCode = null;
			else
				this.DefoCode = rs.getString("DefoCode").trim();

			if( rs.getString("DefoeName") == null )
				this.DefoeName = null;
			else
				this.DefoeName = rs.getString("DefoeName").trim();

			this.DefoRate = rs.getDouble("DefoRate");
			this.RealRate = rs.getDouble("RealRate");
			this.OriSum = rs.getDouble("OriSum");
			this.AdjSum = rs.getDouble("AdjSum");
			this.CalSum = rs.getDouble("CalSum");
			if( rs.getString("AdjReason") == null )
				this.AdjReason = null;
			else
				this.AdjReason = rs.getString("AdjReason").trim();

			if( rs.getString("AdjRemark") == null )
				this.AdjRemark = null;
			else
				this.AdjRemark = rs.getString("AdjRemark").trim();

			this.OutDutyAmnt = rs.getDouble("OutDutyAmnt");
			this.OutDutyRate = rs.getDouble("OutDutyRate");
			if( rs.getString("OutDutyType") == null )
				this.OutDutyType = null;
			else
				this.OutDutyType = rs.getString("OutDutyType").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			this.CutApartAmnt = rs.getDouble("CutApartAmnt");
			if( rs.getString("FeeItemType") == null )
				this.FeeItemType = null;
			else
				this.FeeItemType = rs.getString("FeeItemType").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			this.SocPay = rs.getDouble("SocPay");
			this.OthPay = rs.getDouble("OthPay");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("BillNo") == null )
				this.BillNo = null;
			else
				this.BillNo = rs.getString("BillNo").trim();

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

			if( rs.getString("Times") == null )
				this.Times = null;
			else
				this.Times = rs.getString("Times").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLToClaimDutyFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLToClaimDutyFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLToClaimDutyFeeSchema getSchema()
	{
		LLToClaimDutyFeeSchema aLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();
		aLLToClaimDutyFeeSchema.setSchema(this);
		return aLLToClaimDutyFeeSchema;
	}

	public LLToClaimDutyFeeDB getDB()
	{
		LLToClaimDutyFeeDB aDBOper = new LLToClaimDutyFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLToClaimDutyFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeStaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefoRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OriSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutDutyAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutDutyRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutDutyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CutApartAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SocPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OthPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Times));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLToClaimDutyFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DutyFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DutyFeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DutyFeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DutyFeeStaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			HosID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			HosName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			HosGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			DayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			DefoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			DefoeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DefoRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			RealRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			OriSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			AdjSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			CalSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			OutDutyAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			CutApartAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			FeeItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SocPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			OthPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			BillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			Times = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLToClaimDutyFeeSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyType));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("DutyFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeType));
		}
		if (FCode.equalsIgnoreCase("DutyFeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeCode));
		}
		if (FCode.equalsIgnoreCase("DutyFeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeName));
		}
		if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeStaNo));
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
		if (FCode.equalsIgnoreCase("HosID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosID));
		}
		if (FCode.equalsIgnoreCase("HosName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosName));
		}
		if (FCode.equalsIgnoreCase("HosGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosGrade));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("DayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DayCount));
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoCode));
		}
		if (FCode.equalsIgnoreCase("DefoeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoeName));
		}
		if (FCode.equalsIgnoreCase("DefoRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoRate));
		}
		if (FCode.equalsIgnoreCase("RealRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealRate));
		}
		if (FCode.equalsIgnoreCase("OriSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriSum));
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjSum));
		}
		if (FCode.equalsIgnoreCase("CalSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSum));
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjReason));
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjRemark));
		}
		if (FCode.equalsIgnoreCase("OutDutyAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyAmnt));
		}
		if (FCode.equalsIgnoreCase("OutDutyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyRate));
		}
		if (FCode.equalsIgnoreCase("OutDutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyType));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("CutApartAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutApartAmnt));
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemType));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("SocPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocPay));
		}
		if (FCode.equalsIgnoreCase("OthPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthPay));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillNo));
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
		if (FCode.equalsIgnoreCase("Times"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Times));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeStaNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(HosID);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(HosName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(HosGrade);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 18:
				strFieldValue = String.valueOf(DayCount);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(DefoCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(DefoeName);
				break;
			case 22:
				strFieldValue = String.valueOf(DefoRate);
				break;
			case 23:
				strFieldValue = String.valueOf(RealRate);
				break;
			case 24:
				strFieldValue = String.valueOf(OriSum);
				break;
			case 25:
				strFieldValue = String.valueOf(AdjSum);
				break;
			case 26:
				strFieldValue = String.valueOf(CalSum);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 29:
				strFieldValue = String.valueOf(OutDutyAmnt);
				break;
			case 30:
				strFieldValue = String.valueOf(OutDutyRate);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(OutDutyType);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 34:
				strFieldValue = String.valueOf(CutApartAmnt);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(FeeItemType);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 37:
				strFieldValue = String.valueOf(SocPay);
				break;
			case 38:
				strFieldValue = String.valueOf(OthPay);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(BillNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(Times);
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
		if (FCode.equalsIgnoreCase("GetDutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyType = FValue.trim();
			}
			else
				GetDutyType = null;
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
		if (FCode.equalsIgnoreCase("DutyFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeType = FValue.trim();
			}
			else
				DutyFeeType = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeCode = FValue.trim();
			}
			else
				DutyFeeCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeName = FValue.trim();
			}
			else
				DutyFeeName = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeStaNo = FValue.trim();
			}
			else
				DutyFeeStaNo = null;
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
		if (FCode.equalsIgnoreCase("HosID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosID = FValue.trim();
			}
			else
				HosID = null;
		}
		if (FCode.equalsIgnoreCase("HosName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosName = FValue.trim();
			}
			else
				HosName = null;
		}
		if (FCode.equalsIgnoreCase("HosGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosGrade = FValue.trim();
			}
			else
				HosGrade = null;
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("DayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DayCount = i;
			}
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
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoCode = FValue.trim();
			}
			else
				DefoCode = null;
		}
		if (FCode.equalsIgnoreCase("DefoeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoeName = FValue.trim();
			}
			else
				DefoeName = null;
		}
		if (FCode.equalsIgnoreCase("DefoRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefoRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OriSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OriSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CalSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjReason = FValue.trim();
			}
			else
				AdjReason = null;
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjRemark = FValue.trim();
			}
			else
				AdjRemark = null;
		}
		if (FCode.equalsIgnoreCase("OutDutyAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutDutyAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutDutyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutDutyRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutDutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutDutyType = FValue.trim();
			}
			else
				OutDutyType = null;
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
		}
		if (FCode.equalsIgnoreCase("CutApartAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CutApartAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemType = FValue.trim();
			}
			else
				FeeItemType = null;
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
		if (FCode.equalsIgnoreCase("SocPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SocPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("OthPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OthPay = d;
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
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillNo = FValue.trim();
			}
			else
				BillNo = null;
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
		if (FCode.equalsIgnoreCase("Times"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Times = FValue.trim();
			}
			else
				Times = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLToClaimDutyFeeSchema other = (LLToClaimDutyFeeSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& PolNo.equals(other.getPolNo())
			&& GetDutyType.equals(other.getGetDutyType())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& DutyFeeType.equals(other.getDutyFeeType())
			&& DutyFeeCode.equals(other.getDutyFeeCode())
			&& DutyFeeName.equals(other.getDutyFeeName())
			&& DutyFeeStaNo.equals(other.getDutyFeeStaNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& HosID.equals(other.getHosID())
			&& HosName.equals(other.getHosName())
			&& HosGrade.equals(other.getHosGrade())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& DayCount == other.getDayCount()
			&& DefoType.equals(other.getDefoType())
			&& DefoCode.equals(other.getDefoCode())
			&& DefoeName.equals(other.getDefoeName())
			&& DefoRate == other.getDefoRate()
			&& RealRate == other.getRealRate()
			&& OriSum == other.getOriSum()
			&& AdjSum == other.getAdjSum()
			&& CalSum == other.getCalSum()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& OutDutyAmnt == other.getOutDutyAmnt()
			&& OutDutyRate == other.getOutDutyRate()
			&& OutDutyType.equals(other.getOutDutyType())
			&& DutyCode.equals(other.getDutyCode())
			&& NBPolNo.equals(other.getNBPolNo())
			&& CutApartAmnt == other.getCutApartAmnt()
			&& FeeItemType.equals(other.getFeeItemType())
			&& CustomerNo.equals(other.getCustomerNo())
			&& SocPay == other.getSocPay()
			&& OthPay == other.getOthPay()
			&& Currency.equals(other.getCurrency())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& BillNo.equals(other.getBillNo())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Times.equals(other.getTimes());
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
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyType") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("DutyFeeType") ) {
			return 5;
		}
		if( strFieldName.equals("DutyFeeCode") ) {
			return 6;
		}
		if( strFieldName.equals("DutyFeeName") ) {
			return 7;
		}
		if( strFieldName.equals("DutyFeeStaNo") ) {
			return 8;
		}
		if( strFieldName.equals("KindCode") ) {
			return 9;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 10;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 11;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 12;
		}
		if( strFieldName.equals("HosID") ) {
			return 13;
		}
		if( strFieldName.equals("HosName") ) {
			return 14;
		}
		if( strFieldName.equals("HosGrade") ) {
			return 15;
		}
		if( strFieldName.equals("StartDate") ) {
			return 16;
		}
		if( strFieldName.equals("EndDate") ) {
			return 17;
		}
		if( strFieldName.equals("DayCount") ) {
			return 18;
		}
		if( strFieldName.equals("DefoType") ) {
			return 19;
		}
		if( strFieldName.equals("DefoCode") ) {
			return 20;
		}
		if( strFieldName.equals("DefoeName") ) {
			return 21;
		}
		if( strFieldName.equals("DefoRate") ) {
			return 22;
		}
		if( strFieldName.equals("RealRate") ) {
			return 23;
		}
		if( strFieldName.equals("OriSum") ) {
			return 24;
		}
		if( strFieldName.equals("AdjSum") ) {
			return 25;
		}
		if( strFieldName.equals("CalSum") ) {
			return 26;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 27;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 28;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return 29;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return 30;
		}
		if( strFieldName.equals("OutDutyType") ) {
			return 31;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 32;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 33;
		}
		if( strFieldName.equals("CutApartAmnt") ) {
			return 34;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return 35;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 36;
		}
		if( strFieldName.equals("SocPay") ) {
			return 37;
		}
		if( strFieldName.equals("OthPay") ) {
			return 38;
		}
		if( strFieldName.equals("Currency") ) {
			return 39;
		}
		if( strFieldName.equals("BussType") ) {
			return 40;
		}
		if( strFieldName.equals("BussNo") ) {
			return 41;
		}
		if( strFieldName.equals("BillNo") ) {
			return 42;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 43;
		}
		if( strFieldName.equals("ComCode") ) {
			return 44;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 45;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 46;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 47;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 48;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 50;
		}
		if( strFieldName.equals("Times") ) {
			return 51;
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
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "GetDutyType";
				break;
			case 4:
				strFieldName = "GetDutyCode";
				break;
			case 5:
				strFieldName = "DutyFeeType";
				break;
			case 6:
				strFieldName = "DutyFeeCode";
				break;
			case 7:
				strFieldName = "DutyFeeName";
				break;
			case 8:
				strFieldName = "DutyFeeStaNo";
				break;
			case 9:
				strFieldName = "KindCode";
				break;
			case 10:
				strFieldName = "RiskCode";
				break;
			case 11:
				strFieldName = "RiskVer";
				break;
			case 12:
				strFieldName = "PolMngCom";
				break;
			case 13:
				strFieldName = "HosID";
				break;
			case 14:
				strFieldName = "HosName";
				break;
			case 15:
				strFieldName = "HosGrade";
				break;
			case 16:
				strFieldName = "StartDate";
				break;
			case 17:
				strFieldName = "EndDate";
				break;
			case 18:
				strFieldName = "DayCount";
				break;
			case 19:
				strFieldName = "DefoType";
				break;
			case 20:
				strFieldName = "DefoCode";
				break;
			case 21:
				strFieldName = "DefoeName";
				break;
			case 22:
				strFieldName = "DefoRate";
				break;
			case 23:
				strFieldName = "RealRate";
				break;
			case 24:
				strFieldName = "OriSum";
				break;
			case 25:
				strFieldName = "AdjSum";
				break;
			case 26:
				strFieldName = "CalSum";
				break;
			case 27:
				strFieldName = "AdjReason";
				break;
			case 28:
				strFieldName = "AdjRemark";
				break;
			case 29:
				strFieldName = "OutDutyAmnt";
				break;
			case 30:
				strFieldName = "OutDutyRate";
				break;
			case 31:
				strFieldName = "OutDutyType";
				break;
			case 32:
				strFieldName = "DutyCode";
				break;
			case 33:
				strFieldName = "NBPolNo";
				break;
			case 34:
				strFieldName = "CutApartAmnt";
				break;
			case 35:
				strFieldName = "FeeItemType";
				break;
			case 36:
				strFieldName = "CustomerNo";
				break;
			case 37:
				strFieldName = "SocPay";
				break;
			case 38:
				strFieldName = "OthPay";
				break;
			case 39:
				strFieldName = "Currency";
				break;
			case 40:
				strFieldName = "BussType";
				break;
			case 41:
				strFieldName = "BussNo";
				break;
			case 42:
				strFieldName = "BillNo";
				break;
			case 43:
				strFieldName = "ManageCom";
				break;
			case 44:
				strFieldName = "ComCode";
				break;
			case 45:
				strFieldName = "MakeOperator";
				break;
			case 46:
				strFieldName = "MakeDate";
				break;
			case 47:
				strFieldName = "MakeTime";
				break;
			case 48:
				strFieldName = "ModifyOperator";
				break;
			case 49:
				strFieldName = "ModifyDate";
				break;
			case 50:
				strFieldName = "ModifyTime";
				break;
			case 51:
				strFieldName = "Times";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeStaNo") ) {
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
		if( strFieldName.equals("HosID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OriSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutDutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CutApartAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OthPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillNo") ) {
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
		if( strFieldName.equals("Times") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
