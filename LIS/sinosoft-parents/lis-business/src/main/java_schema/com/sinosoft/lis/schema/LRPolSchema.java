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
import com.sinosoft.lis.db.LRPolDB;

/*
 * <p>ClassName: LRPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRPolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRPolSchema.class);

	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 再保险公司 */
	private String ReinsureCom;
	/** 再保项目 */
	private String ReinsurItem;
	/** 保单年度 */
	private int InsuredYear;
	/** 总单/合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 投保单号码 */
	private String ProposalNo;
	/** 主险保单号码 */
	private String MainPolNo;
	/** 主被保人保单号码 */
	private String MasterPolNo;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
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
	/** 保险年期 */
	private int Years;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 保险责任终止日期 */
	private Date EndDate;
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
	/** 交费间隔 */
	private int PayIntv;
	/** 交费方式 */
	private String PayMode;
	/** 交费年期 */
	private int PayYears;
	/** 终交年龄年期标志 */
	private String PayEndYearFlag;
	/** 终交年龄年期 */
	private int PayEndYear;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 保险年龄年期 */
	private int InsuYear;
	/** 协议类型 */
	private String ProtItem;
	/** 分保开始日期 */
	private Date CessStart;
	/** 分保结束日期 */
	private Date CessEnd;
	/** 保单年初现值 */
	private double EnterCA;
	/** 分保比例 */
	private double CessionRate;
	/** 分保额 */
	private double CessionAmount;
	/** 分保费率 */
	private double CessPremRate;
	/** 分保费 */
	private double CessPrem;
	/** 分保佣金比例 */
	private double CessCommRate;
	/** 分保佣金 */
	private double CessComm;
	/** 额外保费 */
	private double ExPrem;
	/** 额外分保费率 */
	private double ExCessPremRate;
	/** 额外分保费 */
	private double ExCessPrem;
	/** 额外分保佣金 */
	private double ExCessComm;
	/** 额外分保佣金比例 */
	private double ExcessCommRate;
	/** 保单状态 */
	private String PolStat;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 签单日期 */
	private Date SignDate;
	/** 累计风险保额 */
	private double SumRiskAmount;
	/** 当前风险保额 */
	private double NowRiskAmount;
	/** 险种计算分类 */
	private String RiskCalSort;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 投保人类型 */
	private String AppntType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 旧保单号 */
	private String OldPolNo;
	/** 转换渠道 */
	private String TransSaleChnl;
	/** 责任代码 */
	private String DutyCode;

	public static final int FIELDNUM = 62;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRPolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "PolNo";
		pk[1] = "ReinsureCom";
		pk[2] = "ReinsurItem";
		pk[3] = "InsuredYear";
		pk[4] = "RiskCalSort";
		pk[5] = "DutyCode";

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
		LRPolSchema cloned = (LRPolSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getReinsureCom()
	{
		return ReinsureCom;
	}
	public void setReinsureCom(String aReinsureCom)
	{
		ReinsureCom = aReinsureCom;
	}
	/**
	* L--法定分保<p>
	* C--商业分保
	*/
	public String getReinsurItem()
	{
		return ReinsurItem;
	}
	public void setReinsurItem(String aReinsurItem)
	{
		ReinsurItem = aReinsurItem;
	}
	public int getInsuredYear()
	{
		return InsuredYear;
	}
	public void setInsuredYear(int aInsuredYear)
	{
		InsuredYear = aInsuredYear;
	}
	public void setInsuredYear(String aInsuredYear)
	{
		if (aInsuredYear != null && !aInsuredYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuredYear);
			int i = tInteger.intValue();
			InsuredYear = i;
		}
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
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
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	public String getInsuredSex()
	{
		return InsuredSex;
	}
	public void setInsuredSex(String aInsuredSex)
	{
		InsuredSex = aInsuredSex;
	}
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
		PayMode = aPayMode;
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

	/**
	* T--合同分保<p>
	* F－临时分保
	*/
	public String getProtItem()
	{
		return ProtItem;
	}
	public void setProtItem(String aProtItem)
	{
		ProtItem = aProtItem;
	}
	public String getCessStart()
	{
		if( CessStart != null )
			return fDate.getString(CessStart);
		else
			return null;
	}
	public void setCessStart(Date aCessStart)
	{
		CessStart = aCessStart;
	}
	public void setCessStart(String aCessStart)
	{
		if (aCessStart != null && !aCessStart.equals("") )
		{
			CessStart = fDate.getDate( aCessStart );
		}
		else
			CessStart = null;
	}

	public String getCessEnd()
	{
		if( CessEnd != null )
			return fDate.getString(CessEnd);
		else
			return null;
	}
	public void setCessEnd(Date aCessEnd)
	{
		CessEnd = aCessEnd;
	}
	public void setCessEnd(String aCessEnd)
	{
		if (aCessEnd != null && !aCessEnd.equals("") )
		{
			CessEnd = fDate.getDate( aCessEnd );
		}
		else
			CessEnd = null;
	}

	public double getEnterCA()
	{
		return EnterCA;
	}
	public void setEnterCA(double aEnterCA)
	{
		EnterCA = aEnterCA;
	}
	public void setEnterCA(String aEnterCA)
	{
		if (aEnterCA != null && !aEnterCA.equals(""))
		{
			Double tDouble = new Double(aEnterCA);
			double d = tDouble.doubleValue();
			EnterCA = d;
		}
	}

	public double getCessionRate()
	{
		return CessionRate;
	}
	public void setCessionRate(double aCessionRate)
	{
		CessionRate = aCessionRate;
	}
	public void setCessionRate(String aCessionRate)
	{
		if (aCessionRate != null && !aCessionRate.equals(""))
		{
			Double tDouble = new Double(aCessionRate);
			double d = tDouble.doubleValue();
			CessionRate = d;
		}
	}

	public double getCessionAmount()
	{
		return CessionAmount;
	}
	public void setCessionAmount(double aCessionAmount)
	{
		CessionAmount = aCessionAmount;
	}
	public void setCessionAmount(String aCessionAmount)
	{
		if (aCessionAmount != null && !aCessionAmount.equals(""))
		{
			Double tDouble = new Double(aCessionAmount);
			double d = tDouble.doubleValue();
			CessionAmount = d;
		}
	}

	public double getCessPremRate()
	{
		return CessPremRate;
	}
	public void setCessPremRate(double aCessPremRate)
	{
		CessPremRate = aCessPremRate;
	}
	public void setCessPremRate(String aCessPremRate)
	{
		if (aCessPremRate != null && !aCessPremRate.equals(""))
		{
			Double tDouble = new Double(aCessPremRate);
			double d = tDouble.doubleValue();
			CessPremRate = d;
		}
	}

	public double getCessPrem()
	{
		return CessPrem;
	}
	public void setCessPrem(double aCessPrem)
	{
		CessPrem = aCessPrem;
	}
	public void setCessPrem(String aCessPrem)
	{
		if (aCessPrem != null && !aCessPrem.equals(""))
		{
			Double tDouble = new Double(aCessPrem);
			double d = tDouble.doubleValue();
			CessPrem = d;
		}
	}

	public double getCessCommRate()
	{
		return CessCommRate;
	}
	public void setCessCommRate(double aCessCommRate)
	{
		CessCommRate = aCessCommRate;
	}
	public void setCessCommRate(String aCessCommRate)
	{
		if (aCessCommRate != null && !aCessCommRate.equals(""))
		{
			Double tDouble = new Double(aCessCommRate);
			double d = tDouble.doubleValue();
			CessCommRate = d;
		}
	}

	public double getCessComm()
	{
		return CessComm;
	}
	public void setCessComm(double aCessComm)
	{
		CessComm = aCessComm;
	}
	public void setCessComm(String aCessComm)
	{
		if (aCessComm != null && !aCessComm.equals(""))
		{
			Double tDouble = new Double(aCessComm);
			double d = tDouble.doubleValue();
			CessComm = d;
		}
	}

	public double getExPrem()
	{
		return ExPrem;
	}
	public void setExPrem(double aExPrem)
	{
		ExPrem = aExPrem;
	}
	public void setExPrem(String aExPrem)
	{
		if (aExPrem != null && !aExPrem.equals(""))
		{
			Double tDouble = new Double(aExPrem);
			double d = tDouble.doubleValue();
			ExPrem = d;
		}
	}

	public double getExCessPremRate()
	{
		return ExCessPremRate;
	}
	public void setExCessPremRate(double aExCessPremRate)
	{
		ExCessPremRate = aExCessPremRate;
	}
	public void setExCessPremRate(String aExCessPremRate)
	{
		if (aExCessPremRate != null && !aExCessPremRate.equals(""))
		{
			Double tDouble = new Double(aExCessPremRate);
			double d = tDouble.doubleValue();
			ExCessPremRate = d;
		}
	}

	public double getExCessPrem()
	{
		return ExCessPrem;
	}
	public void setExCessPrem(double aExCessPrem)
	{
		ExCessPrem = aExCessPrem;
	}
	public void setExCessPrem(String aExCessPrem)
	{
		if (aExCessPrem != null && !aExCessPrem.equals(""))
		{
			Double tDouble = new Double(aExCessPrem);
			double d = tDouble.doubleValue();
			ExCessPrem = d;
		}
	}

	public double getExCessComm()
	{
		return ExCessComm;
	}
	public void setExCessComm(double aExCessComm)
	{
		ExCessComm = aExCessComm;
	}
	public void setExCessComm(String aExCessComm)
	{
		if (aExCessComm != null && !aExCessComm.equals(""))
		{
			Double tDouble = new Double(aExCessComm);
			double d = tDouble.doubleValue();
			ExCessComm = d;
		}
	}

	public double getExcessCommRate()
	{
		return ExcessCommRate;
	}
	public void setExcessCommRate(double aExcessCommRate)
	{
		ExcessCommRate = aExcessCommRate;
	}
	public void setExcessCommRate(String aExcessCommRate)
	{
		if (aExcessCommRate != null && !aExcessCommRate.equals(""))
		{
			Double tDouble = new Double(aExcessCommRate);
			double d = tDouble.doubleValue();
			ExcessCommRate = d;
		}
	}

	public String getPolStat()
	{
		return PolStat;
	}
	public void setPolStat(String aPolStat)
	{
		PolStat = aPolStat;
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

	public double getSumRiskAmount()
	{
		return SumRiskAmount;
	}
	public void setSumRiskAmount(double aSumRiskAmount)
	{
		SumRiskAmount = aSumRiskAmount;
	}
	public void setSumRiskAmount(String aSumRiskAmount)
	{
		if (aSumRiskAmount != null && !aSumRiskAmount.equals(""))
		{
			Double tDouble = new Double(aSumRiskAmount);
			double d = tDouble.doubleValue();
			SumRiskAmount = d;
		}
	}

	public double getNowRiskAmount()
	{
		return NowRiskAmount;
	}
	public void setNowRiskAmount(double aNowRiskAmount)
	{
		NowRiskAmount = aNowRiskAmount;
	}
	public void setNowRiskAmount(String aNowRiskAmount)
	{
		if (aNowRiskAmount != null && !aNowRiskAmount.equals(""))
		{
			Double tDouble = new Double(aNowRiskAmount);
			double d = tDouble.doubleValue();
			NowRiskAmount = d;
		}
	}

	public String getRiskCalSort()
	{
		return RiskCalSort;
	}
	public void setRiskCalSort(String aRiskCalSort)
	{
		RiskCalSort = aRiskCalSort;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	public String getAppntType()
	{
		return AppntType;
	}
	public void setAppntType(String aAppntType)
	{
		AppntType = aAppntType;
	}
	/**
	* 01-团险直销,<p>
	* 02-个人营销,<p>
	* 03-银行代理<p>
	* 04-兼业代理,<p>
	* 05-专业代理,<p>
	* 06-经纪公司<p>
	* 07-不计业绩销售渠道,<p>
	* 99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	/**
	* 续保时记录原始保单号；<p>
	* 非续保时记录本身的保单号
	*/
	public String getOldPolNo()
	{
		return OldPolNo;
	}
	public void setOldPolNo(String aOldPolNo)
	{
		OldPolNo = aOldPolNo;
	}
	public String getTransSaleChnl()
	{
		return TransSaleChnl;
	}
	public void setTransSaleChnl(String aTransSaleChnl)
	{
		TransSaleChnl = aTransSaleChnl;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}

	/**
	* 使用另外一个 LRPolSchema 对象给 Schema 赋值
	* @param: aLRPolSchema LRPolSchema
	**/
	public void setSchema(LRPolSchema aLRPolSchema)
	{
		this.PolNo = aLRPolSchema.getPolNo();
		this.ReinsureCom = aLRPolSchema.getReinsureCom();
		this.ReinsurItem = aLRPolSchema.getReinsurItem();
		this.InsuredYear = aLRPolSchema.getInsuredYear();
		this.ContNo = aLRPolSchema.getContNo();
		this.GrpPolNo = aLRPolSchema.getGrpPolNo();
		this.ProposalNo = aLRPolSchema.getProposalNo();
		this.MainPolNo = aLRPolSchema.getMainPolNo();
		this.MasterPolNo = aLRPolSchema.getMasterPolNo();
		this.RiskCode = aLRPolSchema.getRiskCode();
		this.RiskVersion = aLRPolSchema.getRiskVersion();
		this.InsuredNo = aLRPolSchema.getInsuredNo();
		this.InsuredName = aLRPolSchema.getInsuredName();
		this.InsuredSex = aLRPolSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLRPolSchema.getInsuredBirthday());
		this.InsuredAppAge = aLRPolSchema.getInsuredAppAge();
		this.Years = aLRPolSchema.getYears();
		this.CValiDate = fDate.getDate( aLRPolSchema.getCValiDate());
		this.EndDate = fDate.getDate( aLRPolSchema.getEndDate());
		this.StandPrem = aLRPolSchema.getStandPrem();
		this.Prem = aLRPolSchema.getPrem();
		this.SumPrem = aLRPolSchema.getSumPrem();
		this.Amnt = aLRPolSchema.getAmnt();
		this.RiskAmnt = aLRPolSchema.getRiskAmnt();
		this.PayIntv = aLRPolSchema.getPayIntv();
		this.PayMode = aLRPolSchema.getPayMode();
		this.PayYears = aLRPolSchema.getPayYears();
		this.PayEndYearFlag = aLRPolSchema.getPayEndYearFlag();
		this.PayEndYear = aLRPolSchema.getPayEndYear();
		this.InsuYearFlag = aLRPolSchema.getInsuYearFlag();
		this.InsuYear = aLRPolSchema.getInsuYear();
		this.ProtItem = aLRPolSchema.getProtItem();
		this.CessStart = fDate.getDate( aLRPolSchema.getCessStart());
		this.CessEnd = fDate.getDate( aLRPolSchema.getCessEnd());
		this.EnterCA = aLRPolSchema.getEnterCA();
		this.CessionRate = aLRPolSchema.getCessionRate();
		this.CessionAmount = aLRPolSchema.getCessionAmount();
		this.CessPremRate = aLRPolSchema.getCessPremRate();
		this.CessPrem = aLRPolSchema.getCessPrem();
		this.CessCommRate = aLRPolSchema.getCessCommRate();
		this.CessComm = aLRPolSchema.getCessComm();
		this.ExPrem = aLRPolSchema.getExPrem();
		this.ExCessPremRate = aLRPolSchema.getExCessPremRate();
		this.ExCessPrem = aLRPolSchema.getExCessPrem();
		this.ExCessComm = aLRPolSchema.getExCessComm();
		this.ExcessCommRate = aLRPolSchema.getExcessCommRate();
		this.PolStat = aLRPolSchema.getPolStat();
		this.MakeDate = fDate.getDate( aLRPolSchema.getMakeDate());
		this.MakeTime = aLRPolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLRPolSchema.getModifyDate());
		this.ModifyTime = aLRPolSchema.getModifyTime();
		this.SignDate = fDate.getDate( aLRPolSchema.getSignDate());
		this.SumRiskAmount = aLRPolSchema.getSumRiskAmount();
		this.NowRiskAmount = aLRPolSchema.getNowRiskAmount();
		this.RiskCalSort = aLRPolSchema.getRiskCalSort();
		this.AppntNo = aLRPolSchema.getAppntNo();
		this.AppntName = aLRPolSchema.getAppntName();
		this.AppntType = aLRPolSchema.getAppntType();
		this.SaleChnl = aLRPolSchema.getSaleChnl();
		this.OldPolNo = aLRPolSchema.getOldPolNo();
		this.TransSaleChnl = aLRPolSchema.getTransSaleChnl();
		this.DutyCode = aLRPolSchema.getDutyCode();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ReinsureCom") == null )
				this.ReinsureCom = null;
			else
				this.ReinsureCom = rs.getString("ReinsureCom").trim();

			if( rs.getString("ReinsurItem") == null )
				this.ReinsurItem = null;
			else
				this.ReinsurItem = rs.getString("ReinsurItem").trim();

			this.InsuredYear = rs.getInt("InsuredYear");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("MasterPolNo") == null )
				this.MasterPolNo = null;
			else
				this.MasterPolNo = rs.getString("MasterPolNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

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
			this.Years = rs.getInt("Years");
			this.CValiDate = rs.getDate("CValiDate");
			this.EndDate = rs.getDate("EndDate");
			this.StandPrem = rs.getDouble("StandPrem");
			this.Prem = rs.getDouble("Prem");
			this.SumPrem = rs.getDouble("SumPrem");
			this.Amnt = rs.getDouble("Amnt");
			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			this.PayYears = rs.getInt("PayYears");
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
			if( rs.getString("ProtItem") == null )
				this.ProtItem = null;
			else
				this.ProtItem = rs.getString("ProtItem").trim();

			this.CessStart = rs.getDate("CessStart");
			this.CessEnd = rs.getDate("CessEnd");
			this.EnterCA = rs.getDouble("EnterCA");
			this.CessionRate = rs.getDouble("CessionRate");
			this.CessionAmount = rs.getDouble("CessionAmount");
			this.CessPremRate = rs.getDouble("CessPremRate");
			this.CessPrem = rs.getDouble("CessPrem");
			this.CessCommRate = rs.getDouble("CessCommRate");
			this.CessComm = rs.getDouble("CessComm");
			this.ExPrem = rs.getDouble("ExPrem");
			this.ExCessPremRate = rs.getDouble("ExCessPremRate");
			this.ExCessPrem = rs.getDouble("ExCessPrem");
			this.ExCessComm = rs.getDouble("ExCessComm");
			this.ExcessCommRate = rs.getDouble("ExcessCommRate");
			if( rs.getString("PolStat") == null )
				this.PolStat = null;
			else
				this.PolStat = rs.getString("PolStat").trim();

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

			this.SignDate = rs.getDate("SignDate");
			this.SumRiskAmount = rs.getDouble("SumRiskAmount");
			this.NowRiskAmount = rs.getDouble("NowRiskAmount");
			if( rs.getString("RiskCalSort") == null )
				this.RiskCalSort = null;
			else
				this.RiskCalSort = rs.getString("RiskCalSort").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntType") == null )
				this.AppntType = null;
			else
				this.AppntType = rs.getString("AppntType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("OldPolNo") == null )
				this.OldPolNo = null;
			else
				this.OldPolNo = rs.getString("OldPolNo").trim();

			if( rs.getString("TransSaleChnl") == null )
				this.TransSaleChnl = null;
			else
				this.TransSaleChnl = rs.getString("TransSaleChnl").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRPol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRPolSchema getSchema()
	{
		LRPolSchema aLRPolSchema = new LRPolSchema();
		aLRPolSchema.setSchema(this);
		return aLRPolSchema;
	}

	public LRPolDB getDB()
	{
		LRPolDB aDBOper = new LRPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRPol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsurItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MasterPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredAppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CessStart ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CessEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnterCA));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessPremRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessComm));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExCessPremRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExCessPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExCessComm));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExcessCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolStat)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumRiskAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NowRiskAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransSaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ReinsureCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ReinsurItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuredYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MasterPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			InsuredAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			ProtItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			CessStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			CessEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			EnterCA = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			CessionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			CessionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			CessPremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			CessPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			CessCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			CessComm = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			ExPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			ExCessPremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			ExCessPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			ExCessComm = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			ExcessCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
			PolStat = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			SumRiskAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).doubleValue();
			NowRiskAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).doubleValue();
			RiskCalSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			AppntType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			OldPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			TransSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRPolSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureCom));
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurItem));
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredYear));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equalsIgnoreCase("MasterPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MasterPolNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
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
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
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
		if (FCode.equalsIgnoreCase("ProtItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtItem));
		}
		if (FCode.equalsIgnoreCase("CessStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCessStart()));
		}
		if (FCode.equalsIgnoreCase("CessEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCessEnd()));
		}
		if (FCode.equalsIgnoreCase("EnterCA"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnterCA));
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionRate));
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionAmount));
		}
		if (FCode.equalsIgnoreCase("CessPremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessPremRate));
		}
		if (FCode.equalsIgnoreCase("CessPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessPrem));
		}
		if (FCode.equalsIgnoreCase("CessCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessCommRate));
		}
		if (FCode.equalsIgnoreCase("CessComm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessComm));
		}
		if (FCode.equalsIgnoreCase("ExPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExPrem));
		}
		if (FCode.equalsIgnoreCase("ExCessPremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExCessPremRate));
		}
		if (FCode.equalsIgnoreCase("ExCessPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExCessPrem));
		}
		if (FCode.equalsIgnoreCase("ExCessComm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExCessComm));
		}
		if (FCode.equalsIgnoreCase("ExcessCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExcessCommRate));
		}
		if (FCode.equalsIgnoreCase("PolStat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolStat));
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
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("SumRiskAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumRiskAmount));
		}
		if (FCode.equalsIgnoreCase("NowRiskAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NowRiskAmount));
		}
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalSort));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("AppntType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntType));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("OldPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldPolNo));
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSaleChnl));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ReinsureCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ReinsurItem);
				break;
			case 3:
				strFieldValue = String.valueOf(InsuredYear);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MasterPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 15:
				strFieldValue = String.valueOf(InsuredAppAge);
				break;
			case 16:
				strFieldValue = String.valueOf(Years);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 19:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 20:
				strFieldValue = String.valueOf(Prem);
				break;
			case 21:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 22:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 23:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 24:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 26:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 28:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 30:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ProtItem);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCessStart()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCessEnd()));
				break;
			case 34:
				strFieldValue = String.valueOf(EnterCA);
				break;
			case 35:
				strFieldValue = String.valueOf(CessionRate);
				break;
			case 36:
				strFieldValue = String.valueOf(CessionAmount);
				break;
			case 37:
				strFieldValue = String.valueOf(CessPremRate);
				break;
			case 38:
				strFieldValue = String.valueOf(CessPrem);
				break;
			case 39:
				strFieldValue = String.valueOf(CessCommRate);
				break;
			case 40:
				strFieldValue = String.valueOf(CessComm);
				break;
			case 41:
				strFieldValue = String.valueOf(ExPrem);
				break;
			case 42:
				strFieldValue = String.valueOf(ExCessPremRate);
				break;
			case 43:
				strFieldValue = String.valueOf(ExCessPrem);
				break;
			case 44:
				strFieldValue = String.valueOf(ExCessComm);
				break;
			case 45:
				strFieldValue = String.valueOf(ExcessCommRate);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(PolStat);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 52:
				strFieldValue = String.valueOf(SumRiskAmount);
				break;
			case 53:
				strFieldValue = String.valueOf(NowRiskAmount);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(RiskCalSort);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(AppntType);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(OldPolNo);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(TransSaleChnl);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureCom = FValue.trim();
			}
			else
				ReinsureCom = null;
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsurItem = FValue.trim();
			}
			else
				ReinsurItem = null;
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredYear = i;
			}
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
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
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
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
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
		if (FCode.equalsIgnoreCase("ProtItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtItem = FValue.trim();
			}
			else
				ProtItem = null;
		}
		if (FCode.equalsIgnoreCase("CessStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CessStart = fDate.getDate( FValue );
			}
			else
				CessStart = null;
		}
		if (FCode.equalsIgnoreCase("CessEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CessEnd = fDate.getDate( FValue );
			}
			else
				CessEnd = null;
		}
		if (FCode.equalsIgnoreCase("EnterCA"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnterCA = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessPremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessPremRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessComm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessComm = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExCessPremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExCessPremRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExCessPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExCessPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExCessComm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExCessComm = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExcessCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExcessCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PolStat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolStat = FValue.trim();
			}
			else
				PolStat = null;
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
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("SumRiskAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumRiskAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("NowRiskAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NowRiskAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalSort = FValue.trim();
			}
			else
				RiskCalSort = null;
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
		if (FCode.equalsIgnoreCase("AppntType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntType = FValue.trim();
			}
			else
				AppntType = null;
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
		if (FCode.equalsIgnoreCase("OldPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldPolNo = FValue.trim();
			}
			else
				OldPolNo = null;
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransSaleChnl = FValue.trim();
			}
			else
				TransSaleChnl = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRPolSchema other = (LRPolSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& ReinsureCom.equals(other.getReinsureCom())
			&& ReinsurItem.equals(other.getReinsurItem())
			&& InsuredYear == other.getInsuredYear()
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& MainPolNo.equals(other.getMainPolNo())
			&& MasterPolNo.equals(other.getMasterPolNo())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredAppAge == other.getInsuredAppAge()
			&& Years == other.getYears()
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& StandPrem == other.getStandPrem()
			&& Prem == other.getPrem()
			&& SumPrem == other.getSumPrem()
			&& Amnt == other.getAmnt()
			&& RiskAmnt == other.getRiskAmnt()
			&& PayIntv == other.getPayIntv()
			&& PayMode.equals(other.getPayMode())
			&& PayYears == other.getPayYears()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& ProtItem.equals(other.getProtItem())
			&& fDate.getString(CessStart).equals(other.getCessStart())
			&& fDate.getString(CessEnd).equals(other.getCessEnd())
			&& EnterCA == other.getEnterCA()
			&& CessionRate == other.getCessionRate()
			&& CessionAmount == other.getCessionAmount()
			&& CessPremRate == other.getCessPremRate()
			&& CessPrem == other.getCessPrem()
			&& CessCommRate == other.getCessCommRate()
			&& CessComm == other.getCessComm()
			&& ExPrem == other.getExPrem()
			&& ExCessPremRate == other.getExCessPremRate()
			&& ExCessPrem == other.getExCessPrem()
			&& ExCessComm == other.getExCessComm()
			&& ExcessCommRate == other.getExcessCommRate()
			&& PolStat.equals(other.getPolStat())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SumRiskAmount == other.getSumRiskAmount()
			&& NowRiskAmount == other.getNowRiskAmount()
			&& RiskCalSort.equals(other.getRiskCalSort())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& AppntType.equals(other.getAppntType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& OldPolNo.equals(other.getOldPolNo())
			&& TransSaleChnl.equals(other.getTransSaleChnl())
			&& DutyCode.equals(other.getDutyCode());
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return 1;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return 2;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
			return 4;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 5;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 6;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 7;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 9;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 10;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 11;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 13;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 14;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return 15;
		}
		if( strFieldName.equals("Years") ) {
			return 16;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 17;
		}
		if( strFieldName.equals("EndDate") ) {
			return 18;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 19;
		}
		if( strFieldName.equals("Prem") ) {
			return 20;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 21;
		}
		if( strFieldName.equals("Amnt") ) {
			return 22;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 23;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 24;
		}
		if( strFieldName.equals("PayMode") ) {
			return 25;
		}
		if( strFieldName.equals("PayYears") ) {
			return 26;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 27;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 28;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 29;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 30;
		}
		if( strFieldName.equals("ProtItem") ) {
			return 31;
		}
		if( strFieldName.equals("CessStart") ) {
			return 32;
		}
		if( strFieldName.equals("CessEnd") ) {
			return 33;
		}
		if( strFieldName.equals("EnterCA") ) {
			return 34;
		}
		if( strFieldName.equals("CessionRate") ) {
			return 35;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return 36;
		}
		if( strFieldName.equals("CessPremRate") ) {
			return 37;
		}
		if( strFieldName.equals("CessPrem") ) {
			return 38;
		}
		if( strFieldName.equals("CessCommRate") ) {
			return 39;
		}
		if( strFieldName.equals("CessComm") ) {
			return 40;
		}
		if( strFieldName.equals("ExPrem") ) {
			return 41;
		}
		if( strFieldName.equals("ExCessPremRate") ) {
			return 42;
		}
		if( strFieldName.equals("ExCessPrem") ) {
			return 43;
		}
		if( strFieldName.equals("ExCessComm") ) {
			return 44;
		}
		if( strFieldName.equals("ExcessCommRate") ) {
			return 45;
		}
		if( strFieldName.equals("PolStat") ) {
			return 46;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 47;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 48;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 50;
		}
		if( strFieldName.equals("SignDate") ) {
			return 51;
		}
		if( strFieldName.equals("SumRiskAmount") ) {
			return 52;
		}
		if( strFieldName.equals("NowRiskAmount") ) {
			return 53;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return 54;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 55;
		}
		if( strFieldName.equals("AppntName") ) {
			return 56;
		}
		if( strFieldName.equals("AppntType") ) {
			return 57;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 58;
		}
		if( strFieldName.equals("OldPolNo") ) {
			return 59;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return 60;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 61;
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "ReinsureCom";
				break;
			case 2:
				strFieldName = "ReinsurItem";
				break;
			case 3:
				strFieldName = "InsuredYear";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "GrpPolNo";
				break;
			case 6:
				strFieldName = "ProposalNo";
				break;
			case 7:
				strFieldName = "MainPolNo";
				break;
			case 8:
				strFieldName = "MasterPolNo";
				break;
			case 9:
				strFieldName = "RiskCode";
				break;
			case 10:
				strFieldName = "RiskVersion";
				break;
			case 11:
				strFieldName = "InsuredNo";
				break;
			case 12:
				strFieldName = "InsuredName";
				break;
			case 13:
				strFieldName = "InsuredSex";
				break;
			case 14:
				strFieldName = "InsuredBirthday";
				break;
			case 15:
				strFieldName = "InsuredAppAge";
				break;
			case 16:
				strFieldName = "Years";
				break;
			case 17:
				strFieldName = "CValiDate";
				break;
			case 18:
				strFieldName = "EndDate";
				break;
			case 19:
				strFieldName = "StandPrem";
				break;
			case 20:
				strFieldName = "Prem";
				break;
			case 21:
				strFieldName = "SumPrem";
				break;
			case 22:
				strFieldName = "Amnt";
				break;
			case 23:
				strFieldName = "RiskAmnt";
				break;
			case 24:
				strFieldName = "PayIntv";
				break;
			case 25:
				strFieldName = "PayMode";
				break;
			case 26:
				strFieldName = "PayYears";
				break;
			case 27:
				strFieldName = "PayEndYearFlag";
				break;
			case 28:
				strFieldName = "PayEndYear";
				break;
			case 29:
				strFieldName = "InsuYearFlag";
				break;
			case 30:
				strFieldName = "InsuYear";
				break;
			case 31:
				strFieldName = "ProtItem";
				break;
			case 32:
				strFieldName = "CessStart";
				break;
			case 33:
				strFieldName = "CessEnd";
				break;
			case 34:
				strFieldName = "EnterCA";
				break;
			case 35:
				strFieldName = "CessionRate";
				break;
			case 36:
				strFieldName = "CessionAmount";
				break;
			case 37:
				strFieldName = "CessPremRate";
				break;
			case 38:
				strFieldName = "CessPrem";
				break;
			case 39:
				strFieldName = "CessCommRate";
				break;
			case 40:
				strFieldName = "CessComm";
				break;
			case 41:
				strFieldName = "ExPrem";
				break;
			case 42:
				strFieldName = "ExCessPremRate";
				break;
			case 43:
				strFieldName = "ExCessPrem";
				break;
			case 44:
				strFieldName = "ExCessComm";
				break;
			case 45:
				strFieldName = "ExcessCommRate";
				break;
			case 46:
				strFieldName = "PolStat";
				break;
			case 47:
				strFieldName = "MakeDate";
				break;
			case 48:
				strFieldName = "MakeTime";
				break;
			case 49:
				strFieldName = "ModifyDate";
				break;
			case 50:
				strFieldName = "ModifyTime";
				break;
			case 51:
				strFieldName = "SignDate";
				break;
			case 52:
				strFieldName = "SumRiskAmount";
				break;
			case 53:
				strFieldName = "NowRiskAmount";
				break;
			case 54:
				strFieldName = "RiskCalSort";
				break;
			case 55:
				strFieldName = "AppntNo";
				break;
			case 56:
				strFieldName = "AppntName";
				break;
			case 57:
				strFieldName = "AppntType";
				break;
			case 58:
				strFieldName = "SaleChnl";
				break;
			case 59:
				strFieldName = "OldPolNo";
				break;
			case 60:
				strFieldName = "TransSaleChnl";
				break;
			case 61:
				strFieldName = "DutyCode";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MasterPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
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
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayYears") ) {
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
		if( strFieldName.equals("ProtItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CessStart") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CessEnd") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnterCA") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessPremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessComm") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExCessPremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExCessPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExCessComm") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExcessCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolStat") ) {
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
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SumRiskAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NowRiskAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_INT;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_INT;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 52:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
