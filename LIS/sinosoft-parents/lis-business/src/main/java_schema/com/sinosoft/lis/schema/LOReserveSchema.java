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
import com.sinosoft.lis.db.LOReserveDB;

/*
 * <p>ClassName: LOReserveSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOReserveSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOReserveSchema.class);

	// @Field
	/** 管理机构 */
	private String ManageCom;
	/** 合同号 */
	private String ContNo;
	/** 保单号码 */
	private String PolNo;
	/** 保单状态 */
	private String PolStatus;
	/** 险种编码 */
	private String RiskCode;
	/** 销售渠道 */
	private String SaleChnl;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人出日日期 */
	private Date InsuredBirthday;
	/** 被保人投保年龄 */
	private int InsuredAppAge;
	/** 第二被保人性别 */
	private String SecInsuredSex;
	/** 第二被保人出生日期 */
	private Date SecBirthDay;
	/** 第二被保人年龄 */
	private int SecInsuredAge;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 最近复效日期 */
	private Date LastRevDate;
	/** 入机日期 */
	private Date MakeDate;
	/** 保险期间 */
	private int Years;
	/** 交费期间 */
	private int PayYears;
	/** 交费间隔 */
	private int PayIntv;
	/** 每期保费 */
	private double StandPrem;
	/** 基本保额 */
	private double Amnt;
	/** 失效日期 */
	private Date InvalidDate;
	/** 免交开始日期 */
	private Date FreeStartDate;
	/** 被保人死亡日期 */
	private Date DeadDate;
	/** 该领未领金额 */
	private double ShouldGetMoney;
	/** 合同终止原因 */
	private String StopReason;
	/** 合同终止日期 */
	private Date StopDate;
	/** 养老金领取年龄 */
	private int RevGetAge;
	/** 养老金开始领取日期 */
	private Date RevGetDate;
	/** 养老金首次领取金额 */
	private double RevGetMoney;
	/** 养老金领取方式 */
	private int RevGetIntv;
	/** 年金给付金额 */
	private double AnnMoney;
	/** 死亡伤残给付金额 */
	private double DeathMoney;
	/** 医疗及重疾给付金额 */
	private double MedMoney;
	/** 满期给付金额 */
	private double ExpMoney;
	/** 累计已付金额 */
	private double TotalMoney;
	/** 职业加费 */
	private double OccPrem;
	/** 健康加费 */
	private double HealthPrem;
	/** 上会计年度保费收入 */
	private double LastPayPrem;
	/** 保单年度 */
	private int YearIdx;
	/** 计算年度 */
	private int CalYear;
	/** 责任金三 */
	private double Rev3;
	/** 责任金一 */
	private double Rev1;
	/** 责任金二 */
	private double Rev2;
	/** 责任金四 */
	private double Rev4;
	/** 保费计算检查标志 */
	private int CheckFlag;
	/** 准备金计算检查标志 */
	private int CalFlag;
	/** 数据提取日期 */
	private Date ModifyDate;
	/** 签单日期 */
	private Date SignDate;
	/** 本年累计保费 */
	private double ThisPayPrem;
	/** 交至日期 */
	private Date PayToDate;
	/** 管理费比例 */
	private double ManageFeeRate;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 缴费年龄年期标志 */
	private String PayEndYearFlag;
	/** 退保金额 */
	private double SurMoney;
	/** 吸烟标志 */
	private String SmokingFlag;
	/** 职业类别 */
	private String OccupationType;
	/** 保单送达日期 */
	private Date CustomGetPolDate;
	/** 打折系数 */
	private double FloatRate;
	/** 集体保单号 */
	private String GroupNo;
	/** 退费金额 */
	private double GetMoney;
	/** Getdutykind */
	private String GetDutyKind;
	/** 代理机构 */
	private String AgentCom;
	/** 中介机构类别 */
	private String ACType;
	/** 保险止期 */
	private Date EndDate;
	/** 保费免交标志 */
	private String FreeFlag;
	/** 自动垫交状态 */
	private String AutoPayFlag;
	/** 最近结算帐户价值 */
	private double Av_last_cal;
	/** 最近结算日期 */
	private Date Acval_cal_date;
	/** 年初帐户价值 */
	private double Av_BOY;
	/** 月初帐户价值 */
	private double Av_BOM;
	/** 本月部分退保金额 */
	private double TBMoney;
	/** 本月部分退保日期 */
	private Date TBMDate;
	/** 转换渠道 */
	private String TransSaleChnl;
	/** 累计部分领取金额 */
	private double Cuml_PartSurrender;
	/** 分红保额 */
	private double BonusAmnt;
	/** 其他加费 */
	private double OtherPrem;

	public static final int FIELDNUM = 77;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOReserveSchema()
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
		LOReserveSchema cloned = (LOReserveSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	public String getPolStatus()
	{
		return PolStatus;
	}
	public void setPolStatus(String aPolStatus)
	{
		PolStatus = aPolStatus;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
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
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
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

	public String getSecInsuredSex()
	{
		return SecInsuredSex;
	}
	public void setSecInsuredSex(String aSecInsuredSex)
	{
		SecInsuredSex = aSecInsuredSex;
	}
	public String getSecBirthDay()
	{
		if( SecBirthDay != null )
			return fDate.getString(SecBirthDay);
		else
			return null;
	}
	public void setSecBirthDay(Date aSecBirthDay)
	{
		SecBirthDay = aSecBirthDay;
	}
	public void setSecBirthDay(String aSecBirthDay)
	{
		if (aSecBirthDay != null && !aSecBirthDay.equals("") )
		{
			SecBirthDay = fDate.getDate( aSecBirthDay );
		}
		else
			SecBirthDay = null;
	}

	public int getSecInsuredAge()
	{
		return SecInsuredAge;
	}
	public void setSecInsuredAge(int aSecInsuredAge)
	{
		SecInsuredAge = aSecInsuredAge;
	}
	public void setSecInsuredAge(String aSecInsuredAge)
	{
		if (aSecInsuredAge != null && !aSecInsuredAge.equals(""))
		{
			Integer tInteger = new Integer(aSecInsuredAge);
			int i = tInteger.intValue();
			SecInsuredAge = i;
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
	* -1 －－ 不定期缴费<p>
	* 0  －－ 趸缴<p>
	* 1  －－ 月缴<p>
	* 3  －－ 季缴<p>
	* 6  －－ 半年缴<p>
	* 12 －－ 年缴<p>
	* 36 －－ 三年缴
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

	public String getInvalidDate()
	{
		if( InvalidDate != null )
			return fDate.getString(InvalidDate);
		else
			return null;
	}
	public void setInvalidDate(Date aInvalidDate)
	{
		InvalidDate = aInvalidDate;
	}
	public void setInvalidDate(String aInvalidDate)
	{
		if (aInvalidDate != null && !aInvalidDate.equals("") )
		{
			InvalidDate = fDate.getDate( aInvalidDate );
		}
		else
			InvalidDate = null;
	}

	public String getFreeStartDate()
	{
		if( FreeStartDate != null )
			return fDate.getString(FreeStartDate);
		else
			return null;
	}
	public void setFreeStartDate(Date aFreeStartDate)
	{
		FreeStartDate = aFreeStartDate;
	}
	public void setFreeStartDate(String aFreeStartDate)
	{
		if (aFreeStartDate != null && !aFreeStartDate.equals("") )
		{
			FreeStartDate = fDate.getDate( aFreeStartDate );
		}
		else
			FreeStartDate = null;
	}

	public String getDeadDate()
	{
		if( DeadDate != null )
			return fDate.getString(DeadDate);
		else
			return null;
	}
	public void setDeadDate(Date aDeadDate)
	{
		DeadDate = aDeadDate;
	}
	public void setDeadDate(String aDeadDate)
	{
		if (aDeadDate != null && !aDeadDate.equals("") )
		{
			DeadDate = fDate.getDate( aDeadDate );
		}
		else
			DeadDate = null;
	}

	public double getShouldGetMoney()
	{
		return ShouldGetMoney;
	}
	public void setShouldGetMoney(double aShouldGetMoney)
	{
		ShouldGetMoney = aShouldGetMoney;
	}
	public void setShouldGetMoney(String aShouldGetMoney)
	{
		if (aShouldGetMoney != null && !aShouldGetMoney.equals(""))
		{
			Double tDouble = new Double(aShouldGetMoney);
			double d = tDouble.doubleValue();
			ShouldGetMoney = d;
		}
	}

	public String getStopReason()
	{
		return StopReason;
	}
	public void setStopReason(String aStopReason)
	{
		StopReason = aStopReason;
	}
	public String getStopDate()
	{
		if( StopDate != null )
			return fDate.getString(StopDate);
		else
			return null;
	}
	public void setStopDate(Date aStopDate)
	{
		StopDate = aStopDate;
	}
	public void setStopDate(String aStopDate)
	{
		if (aStopDate != null && !aStopDate.equals("") )
		{
			StopDate = fDate.getDate( aStopDate );
		}
		else
			StopDate = null;
	}

	public int getRevGetAge()
	{
		return RevGetAge;
	}
	public void setRevGetAge(int aRevGetAge)
	{
		RevGetAge = aRevGetAge;
	}
	public void setRevGetAge(String aRevGetAge)
	{
		if (aRevGetAge != null && !aRevGetAge.equals(""))
		{
			Integer tInteger = new Integer(aRevGetAge);
			int i = tInteger.intValue();
			RevGetAge = i;
		}
	}

	public String getRevGetDate()
	{
		if( RevGetDate != null )
			return fDate.getString(RevGetDate);
		else
			return null;
	}
	public void setRevGetDate(Date aRevGetDate)
	{
		RevGetDate = aRevGetDate;
	}
	public void setRevGetDate(String aRevGetDate)
	{
		if (aRevGetDate != null && !aRevGetDate.equals("") )
		{
			RevGetDate = fDate.getDate( aRevGetDate );
		}
		else
			RevGetDate = null;
	}

	public double getRevGetMoney()
	{
		return RevGetMoney;
	}
	public void setRevGetMoney(double aRevGetMoney)
	{
		RevGetMoney = aRevGetMoney;
	}
	public void setRevGetMoney(String aRevGetMoney)
	{
		if (aRevGetMoney != null && !aRevGetMoney.equals(""))
		{
			Double tDouble = new Double(aRevGetMoney);
			double d = tDouble.doubleValue();
			RevGetMoney = d;
		}
	}

	public int getRevGetIntv()
	{
		return RevGetIntv;
	}
	public void setRevGetIntv(int aRevGetIntv)
	{
		RevGetIntv = aRevGetIntv;
	}
	public void setRevGetIntv(String aRevGetIntv)
	{
		if (aRevGetIntv != null && !aRevGetIntv.equals(""))
		{
			Integer tInteger = new Integer(aRevGetIntv);
			int i = tInteger.intValue();
			RevGetIntv = i;
		}
	}

	public double getAnnMoney()
	{
		return AnnMoney;
	}
	public void setAnnMoney(double aAnnMoney)
	{
		AnnMoney = aAnnMoney;
	}
	public void setAnnMoney(String aAnnMoney)
	{
		if (aAnnMoney != null && !aAnnMoney.equals(""))
		{
			Double tDouble = new Double(aAnnMoney);
			double d = tDouble.doubleValue();
			AnnMoney = d;
		}
	}

	public double getDeathMoney()
	{
		return DeathMoney;
	}
	public void setDeathMoney(double aDeathMoney)
	{
		DeathMoney = aDeathMoney;
	}
	public void setDeathMoney(String aDeathMoney)
	{
		if (aDeathMoney != null && !aDeathMoney.equals(""))
		{
			Double tDouble = new Double(aDeathMoney);
			double d = tDouble.doubleValue();
			DeathMoney = d;
		}
	}

	public double getMedMoney()
	{
		return MedMoney;
	}
	public void setMedMoney(double aMedMoney)
	{
		MedMoney = aMedMoney;
	}
	public void setMedMoney(String aMedMoney)
	{
		if (aMedMoney != null && !aMedMoney.equals(""))
		{
			Double tDouble = new Double(aMedMoney);
			double d = tDouble.doubleValue();
			MedMoney = d;
		}
	}

	public double getExpMoney()
	{
		return ExpMoney;
	}
	public void setExpMoney(double aExpMoney)
	{
		ExpMoney = aExpMoney;
	}
	public void setExpMoney(String aExpMoney)
	{
		if (aExpMoney != null && !aExpMoney.equals(""))
		{
			Double tDouble = new Double(aExpMoney);
			double d = tDouble.doubleValue();
			ExpMoney = d;
		}
	}

	public double getTotalMoney()
	{
		return TotalMoney;
	}
	public void setTotalMoney(double aTotalMoney)
	{
		TotalMoney = aTotalMoney;
	}
	public void setTotalMoney(String aTotalMoney)
	{
		if (aTotalMoney != null && !aTotalMoney.equals(""))
		{
			Double tDouble = new Double(aTotalMoney);
			double d = tDouble.doubleValue();
			TotalMoney = d;
		}
	}

	public double getOccPrem()
	{
		return OccPrem;
	}
	public void setOccPrem(double aOccPrem)
	{
		OccPrem = aOccPrem;
	}
	public void setOccPrem(String aOccPrem)
	{
		if (aOccPrem != null && !aOccPrem.equals(""))
		{
			Double tDouble = new Double(aOccPrem);
			double d = tDouble.doubleValue();
			OccPrem = d;
		}
	}

	public double getHealthPrem()
	{
		return HealthPrem;
	}
	public void setHealthPrem(double aHealthPrem)
	{
		HealthPrem = aHealthPrem;
	}
	public void setHealthPrem(String aHealthPrem)
	{
		if (aHealthPrem != null && !aHealthPrem.equals(""))
		{
			Double tDouble = new Double(aHealthPrem);
			double d = tDouble.doubleValue();
			HealthPrem = d;
		}
	}

	public double getLastPayPrem()
	{
		return LastPayPrem;
	}
	public void setLastPayPrem(double aLastPayPrem)
	{
		LastPayPrem = aLastPayPrem;
	}
	public void setLastPayPrem(String aLastPayPrem)
	{
		if (aLastPayPrem != null && !aLastPayPrem.equals(""))
		{
			Double tDouble = new Double(aLastPayPrem);
			double d = tDouble.doubleValue();
			LastPayPrem = d;
		}
	}

	public int getYearIdx()
	{
		return YearIdx;
	}
	public void setYearIdx(int aYearIdx)
	{
		YearIdx = aYearIdx;
	}
	public void setYearIdx(String aYearIdx)
	{
		if (aYearIdx != null && !aYearIdx.equals(""))
		{
			Integer tInteger = new Integer(aYearIdx);
			int i = tInteger.intValue();
			YearIdx = i;
		}
	}

	public int getCalYear()
	{
		return CalYear;
	}
	public void setCalYear(int aCalYear)
	{
		CalYear = aCalYear;
	}
	public void setCalYear(String aCalYear)
	{
		if (aCalYear != null && !aCalYear.equals(""))
		{
			Integer tInteger = new Integer(aCalYear);
			int i = tInteger.intValue();
			CalYear = i;
		}
	}

	public double getRev3()
	{
		return Rev3;
	}
	public void setRev3(double aRev3)
	{
		Rev3 = aRev3;
	}
	public void setRev3(String aRev3)
	{
		if (aRev3 != null && !aRev3.equals(""))
		{
			Double tDouble = new Double(aRev3);
			double d = tDouble.doubleValue();
			Rev3 = d;
		}
	}

	public double getRev1()
	{
		return Rev1;
	}
	public void setRev1(double aRev1)
	{
		Rev1 = aRev1;
	}
	public void setRev1(String aRev1)
	{
		if (aRev1 != null && !aRev1.equals(""))
		{
			Double tDouble = new Double(aRev1);
			double d = tDouble.doubleValue();
			Rev1 = d;
		}
	}

	public double getRev2()
	{
		return Rev2;
	}
	public void setRev2(double aRev2)
	{
		Rev2 = aRev2;
	}
	public void setRev2(String aRev2)
	{
		if (aRev2 != null && !aRev2.equals(""))
		{
			Double tDouble = new Double(aRev2);
			double d = tDouble.doubleValue();
			Rev2 = d;
		}
	}

	public double getRev4()
	{
		return Rev4;
	}
	public void setRev4(double aRev4)
	{
		Rev4 = aRev4;
	}
	public void setRev4(String aRev4)
	{
		if (aRev4 != null && !aRev4.equals(""))
		{
			Double tDouble = new Double(aRev4);
			double d = tDouble.doubleValue();
			Rev4 = d;
		}
	}

	public int getCheckFlag()
	{
		return CheckFlag;
	}
	public void setCheckFlag(int aCheckFlag)
	{
		CheckFlag = aCheckFlag;
	}
	public void setCheckFlag(String aCheckFlag)
	{
		if (aCheckFlag != null && !aCheckFlag.equals(""))
		{
			Integer tInteger = new Integer(aCheckFlag);
			int i = tInteger.intValue();
			CheckFlag = i;
		}
	}

	public int getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(int aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		if (aCalFlag != null && !aCalFlag.equals(""))
		{
			Integer tInteger = new Integer(aCalFlag);
			int i = tInteger.intValue();
			CalFlag = i;
		}
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

	public double getThisPayPrem()
	{
		return ThisPayPrem;
	}
	public void setThisPayPrem(double aThisPayPrem)
	{
		ThisPayPrem = aThisPayPrem;
	}
	public void setThisPayPrem(String aThisPayPrem)
	{
		if (aThisPayPrem != null && !aThisPayPrem.equals(""))
		{
			Double tDouble = new Double(aThisPayPrem);
			double d = tDouble.doubleValue();
			ThisPayPrem = d;
		}
	}

	public String getPayToDate()
	{
		if( PayToDate != null )
			return fDate.getString(PayToDate);
		else
			return null;
	}
	public void setPayToDate(Date aPayToDate)
	{
		PayToDate = aPayToDate;
	}
	public void setPayToDate(String aPayToDate)
	{
		if (aPayToDate != null && !aPayToDate.equals("") )
		{
			PayToDate = fDate.getDate( aPayToDate );
		}
		else
			PayToDate = null;
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

	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	public double getSurMoney()
	{
		return SurMoney;
	}
	public void setSurMoney(double aSurMoney)
	{
		SurMoney = aSurMoney;
	}
	public void setSurMoney(String aSurMoney)
	{
		if (aSurMoney != null && !aSurMoney.equals(""))
		{
			Double tDouble = new Double(aSurMoney);
			double d = tDouble.doubleValue();
			SurMoney = d;
		}
	}

	public String getSmokingFlag()
	{
		return SmokingFlag;
	}
	public void setSmokingFlag(String aSmokingFlag)
	{
		SmokingFlag = aSmokingFlag;
	}
	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
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

	public String getGroupNo()
	{
		return GroupNo;
	}
	public void setGroupNo(String aGroupNo)
	{
		GroupNo = aGroupNo;
	}
	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
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
	* 01-银行代理<p>
	* 02-兼业代理<p>
	* 03-专业代理<p>
	* 04-经济公司<p>
	* 05-其它中介业务)
	*/
	public String getACType()
	{
		return ACType;
	}
	public void setACType(String aACType)
	{
		ACType = aACType;
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

	public String getFreeFlag()
	{
		return FreeFlag;
	}
	public void setFreeFlag(String aFreeFlag)
	{
		FreeFlag = aFreeFlag;
	}
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		AutoPayFlag = aAutoPayFlag;
	}
	public double getAv_last_cal()
	{
		return Av_last_cal;
	}
	public void setAv_last_cal(double aAv_last_cal)
	{
		Av_last_cal = aAv_last_cal;
	}
	public void setAv_last_cal(String aAv_last_cal)
	{
		if (aAv_last_cal != null && !aAv_last_cal.equals(""))
		{
			Double tDouble = new Double(aAv_last_cal);
			double d = tDouble.doubleValue();
			Av_last_cal = d;
		}
	}

	public String getAcval_cal_date()
	{
		if( Acval_cal_date != null )
			return fDate.getString(Acval_cal_date);
		else
			return null;
	}
	public void setAcval_cal_date(Date aAcval_cal_date)
	{
		Acval_cal_date = aAcval_cal_date;
	}
	public void setAcval_cal_date(String aAcval_cal_date)
	{
		if (aAcval_cal_date != null && !aAcval_cal_date.equals("") )
		{
			Acval_cal_date = fDate.getDate( aAcval_cal_date );
		}
		else
			Acval_cal_date = null;
	}

	public double getAv_BOY()
	{
		return Av_BOY;
	}
	public void setAv_BOY(double aAv_BOY)
	{
		Av_BOY = aAv_BOY;
	}
	public void setAv_BOY(String aAv_BOY)
	{
		if (aAv_BOY != null && !aAv_BOY.equals(""))
		{
			Double tDouble = new Double(aAv_BOY);
			double d = tDouble.doubleValue();
			Av_BOY = d;
		}
	}

	public double getAv_BOM()
	{
		return Av_BOM;
	}
	public void setAv_BOM(double aAv_BOM)
	{
		Av_BOM = aAv_BOM;
	}
	public void setAv_BOM(String aAv_BOM)
	{
		if (aAv_BOM != null && !aAv_BOM.equals(""))
		{
			Double tDouble = new Double(aAv_BOM);
			double d = tDouble.doubleValue();
			Av_BOM = d;
		}
	}

	public double getTBMoney()
	{
		return TBMoney;
	}
	public void setTBMoney(double aTBMoney)
	{
		TBMoney = aTBMoney;
	}
	public void setTBMoney(String aTBMoney)
	{
		if (aTBMoney != null && !aTBMoney.equals(""))
		{
			Double tDouble = new Double(aTBMoney);
			double d = tDouble.doubleValue();
			TBMoney = d;
		}
	}

	public String getTBMDate()
	{
		if( TBMDate != null )
			return fDate.getString(TBMDate);
		else
			return null;
	}
	public void setTBMDate(Date aTBMDate)
	{
		TBMDate = aTBMDate;
	}
	public void setTBMDate(String aTBMDate)
	{
		if (aTBMDate != null && !aTBMDate.equals("") )
		{
			TBMDate = fDate.getDate( aTBMDate );
		}
		else
			TBMDate = null;
	}

	public String getTransSaleChnl()
	{
		return TransSaleChnl;
	}
	public void setTransSaleChnl(String aTransSaleChnl)
	{
		TransSaleChnl = aTransSaleChnl;
	}
	public double getCuml_PartSurrender()
	{
		return Cuml_PartSurrender;
	}
	public void setCuml_PartSurrender(double aCuml_PartSurrender)
	{
		Cuml_PartSurrender = aCuml_PartSurrender;
	}
	public void setCuml_PartSurrender(String aCuml_PartSurrender)
	{
		if (aCuml_PartSurrender != null && !aCuml_PartSurrender.equals(""))
		{
			Double tDouble = new Double(aCuml_PartSurrender);
			double d = tDouble.doubleValue();
			Cuml_PartSurrender = d;
		}
	}

	public double getBonusAmnt()
	{
		return BonusAmnt;
	}
	public void setBonusAmnt(double aBonusAmnt)
	{
		BonusAmnt = aBonusAmnt;
	}
	public void setBonusAmnt(String aBonusAmnt)
	{
		if (aBonusAmnt != null && !aBonusAmnt.equals(""))
		{
			Double tDouble = new Double(aBonusAmnt);
			double d = tDouble.doubleValue();
			BonusAmnt = d;
		}
	}

	public double getOtherPrem()
	{
		return OtherPrem;
	}
	public void setOtherPrem(double aOtherPrem)
	{
		OtherPrem = aOtherPrem;
	}
	public void setOtherPrem(String aOtherPrem)
	{
		if (aOtherPrem != null && !aOtherPrem.equals(""))
		{
			Double tDouble = new Double(aOtherPrem);
			double d = tDouble.doubleValue();
			OtherPrem = d;
		}
	}


	/**
	* 使用另外一个 LOReserveSchema 对象给 Schema 赋值
	* @param: aLOReserveSchema LOReserveSchema
	**/
	public void setSchema(LOReserveSchema aLOReserveSchema)
	{
		this.ManageCom = aLOReserveSchema.getManageCom();
		this.ContNo = aLOReserveSchema.getContNo();
		this.PolNo = aLOReserveSchema.getPolNo();
		this.PolStatus = aLOReserveSchema.getPolStatus();
		this.RiskCode = aLOReserveSchema.getRiskCode();
		this.SaleChnl = aLOReserveSchema.getSaleChnl();
		this.InsuredNo = aLOReserveSchema.getInsuredNo();
		this.InsuredSex = aLOReserveSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLOReserveSchema.getInsuredBirthday());
		this.InsuredAppAge = aLOReserveSchema.getInsuredAppAge();
		this.SecInsuredSex = aLOReserveSchema.getSecInsuredSex();
		this.SecBirthDay = fDate.getDate( aLOReserveSchema.getSecBirthDay());
		this.SecInsuredAge = aLOReserveSchema.getSecInsuredAge();
		this.CValiDate = fDate.getDate( aLOReserveSchema.getCValiDate());
		this.LastRevDate = fDate.getDate( aLOReserveSchema.getLastRevDate());
		this.MakeDate = fDate.getDate( aLOReserveSchema.getMakeDate());
		this.Years = aLOReserveSchema.getYears();
		this.PayYears = aLOReserveSchema.getPayYears();
		this.PayIntv = aLOReserveSchema.getPayIntv();
		this.StandPrem = aLOReserveSchema.getStandPrem();
		this.Amnt = aLOReserveSchema.getAmnt();
		this.InvalidDate = fDate.getDate( aLOReserveSchema.getInvalidDate());
		this.FreeStartDate = fDate.getDate( aLOReserveSchema.getFreeStartDate());
		this.DeadDate = fDate.getDate( aLOReserveSchema.getDeadDate());
		this.ShouldGetMoney = aLOReserveSchema.getShouldGetMoney();
		this.StopReason = aLOReserveSchema.getStopReason();
		this.StopDate = fDate.getDate( aLOReserveSchema.getStopDate());
		this.RevGetAge = aLOReserveSchema.getRevGetAge();
		this.RevGetDate = fDate.getDate( aLOReserveSchema.getRevGetDate());
		this.RevGetMoney = aLOReserveSchema.getRevGetMoney();
		this.RevGetIntv = aLOReserveSchema.getRevGetIntv();
		this.AnnMoney = aLOReserveSchema.getAnnMoney();
		this.DeathMoney = aLOReserveSchema.getDeathMoney();
		this.MedMoney = aLOReserveSchema.getMedMoney();
		this.ExpMoney = aLOReserveSchema.getExpMoney();
		this.TotalMoney = aLOReserveSchema.getTotalMoney();
		this.OccPrem = aLOReserveSchema.getOccPrem();
		this.HealthPrem = aLOReserveSchema.getHealthPrem();
		this.LastPayPrem = aLOReserveSchema.getLastPayPrem();
		this.YearIdx = aLOReserveSchema.getYearIdx();
		this.CalYear = aLOReserveSchema.getCalYear();
		this.Rev3 = aLOReserveSchema.getRev3();
		this.Rev1 = aLOReserveSchema.getRev1();
		this.Rev2 = aLOReserveSchema.getRev2();
		this.Rev4 = aLOReserveSchema.getRev4();
		this.CheckFlag = aLOReserveSchema.getCheckFlag();
		this.CalFlag = aLOReserveSchema.getCalFlag();
		this.ModifyDate = fDate.getDate( aLOReserveSchema.getModifyDate());
		this.SignDate = fDate.getDate( aLOReserveSchema.getSignDate());
		this.ThisPayPrem = aLOReserveSchema.getThisPayPrem();
		this.PayToDate = fDate.getDate( aLOReserveSchema.getPayToDate());
		this.ManageFeeRate = aLOReserveSchema.getManageFeeRate();
		this.InsuYearFlag = aLOReserveSchema.getInsuYearFlag();
		this.PayEndYearFlag = aLOReserveSchema.getPayEndYearFlag();
		this.SurMoney = aLOReserveSchema.getSurMoney();
		this.SmokingFlag = aLOReserveSchema.getSmokingFlag();
		this.OccupationType = aLOReserveSchema.getOccupationType();
		this.CustomGetPolDate = fDate.getDate( aLOReserveSchema.getCustomGetPolDate());
		this.FloatRate = aLOReserveSchema.getFloatRate();
		this.GroupNo = aLOReserveSchema.getGroupNo();
		this.GetMoney = aLOReserveSchema.getGetMoney();
		this.GetDutyKind = aLOReserveSchema.getGetDutyKind();
		this.AgentCom = aLOReserveSchema.getAgentCom();
		this.ACType = aLOReserveSchema.getACType();
		this.EndDate = fDate.getDate( aLOReserveSchema.getEndDate());
		this.FreeFlag = aLOReserveSchema.getFreeFlag();
		this.AutoPayFlag = aLOReserveSchema.getAutoPayFlag();
		this.Av_last_cal = aLOReserveSchema.getAv_last_cal();
		this.Acval_cal_date = fDate.getDate( aLOReserveSchema.getAcval_cal_date());
		this.Av_BOY = aLOReserveSchema.getAv_BOY();
		this.Av_BOM = aLOReserveSchema.getAv_BOM();
		this.TBMoney = aLOReserveSchema.getTBMoney();
		this.TBMDate = fDate.getDate( aLOReserveSchema.getTBMDate());
		this.TransSaleChnl = aLOReserveSchema.getTransSaleChnl();
		this.Cuml_PartSurrender = aLOReserveSchema.getCuml_PartSurrender();
		this.BonusAmnt = aLOReserveSchema.getBonusAmnt();
		this.OtherPrem = aLOReserveSchema.getOtherPrem();
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
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("PolStatus") == null )
				this.PolStatus = null;
			else
				this.PolStatus = rs.getString("PolStatus").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			this.InsuredAppAge = rs.getInt("InsuredAppAge");
			if( rs.getString("SecInsuredSex") == null )
				this.SecInsuredSex = null;
			else
				this.SecInsuredSex = rs.getString("SecInsuredSex").trim();

			this.SecBirthDay = rs.getDate("SecBirthDay");
			this.SecInsuredAge = rs.getInt("SecInsuredAge");
			this.CValiDate = rs.getDate("CValiDate");
			this.LastRevDate = rs.getDate("LastRevDate");
			this.MakeDate = rs.getDate("MakeDate");
			this.Years = rs.getInt("Years");
			this.PayYears = rs.getInt("PayYears");
			this.PayIntv = rs.getInt("PayIntv");
			this.StandPrem = rs.getDouble("StandPrem");
			this.Amnt = rs.getDouble("Amnt");
			this.InvalidDate = rs.getDate("InvalidDate");
			this.FreeStartDate = rs.getDate("FreeStartDate");
			this.DeadDate = rs.getDate("DeadDate");
			this.ShouldGetMoney = rs.getDouble("ShouldGetMoney");
			if( rs.getString("StopReason") == null )
				this.StopReason = null;
			else
				this.StopReason = rs.getString("StopReason").trim();

			this.StopDate = rs.getDate("StopDate");
			this.RevGetAge = rs.getInt("RevGetAge");
			this.RevGetDate = rs.getDate("RevGetDate");
			this.RevGetMoney = rs.getDouble("RevGetMoney");
			this.RevGetIntv = rs.getInt("RevGetIntv");
			this.AnnMoney = rs.getDouble("AnnMoney");
			this.DeathMoney = rs.getDouble("DeathMoney");
			this.MedMoney = rs.getDouble("MedMoney");
			this.ExpMoney = rs.getDouble("ExpMoney");
			this.TotalMoney = rs.getDouble("TotalMoney");
			this.OccPrem = rs.getDouble("OccPrem");
			this.HealthPrem = rs.getDouble("HealthPrem");
			this.LastPayPrem = rs.getDouble("LastPayPrem");
			this.YearIdx = rs.getInt("YearIdx");
			this.CalYear = rs.getInt("CalYear");
			this.Rev3 = rs.getDouble("Rev3");
			this.Rev1 = rs.getDouble("Rev1");
			this.Rev2 = rs.getDouble("Rev2");
			this.Rev4 = rs.getDouble("Rev4");
			this.CheckFlag = rs.getInt("CheckFlag");
			this.CalFlag = rs.getInt("CalFlag");
			this.ModifyDate = rs.getDate("ModifyDate");
			this.SignDate = rs.getDate("SignDate");
			this.ThisPayPrem = rs.getDouble("ThisPayPrem");
			this.PayToDate = rs.getDate("PayToDate");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.SurMoney = rs.getDouble("SurMoney");
			if( rs.getString("SmokingFlag") == null )
				this.SmokingFlag = null;
			else
				this.SmokingFlag = rs.getString("SmokingFlag").trim();

			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			this.FloatRate = rs.getDouble("FloatRate");
			if( rs.getString("GroupNo") == null )
				this.GroupNo = null;
			else
				this.GroupNo = rs.getString("GroupNo").trim();

			this.GetMoney = rs.getDouble("GetMoney");
			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("ACType") == null )
				this.ACType = null;
			else
				this.ACType = rs.getString("ACType").trim();

			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("FreeFlag") == null )
				this.FreeFlag = null;
			else
				this.FreeFlag = rs.getString("FreeFlag").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			this.Av_last_cal = rs.getDouble("Av_last_cal");
			this.Acval_cal_date = rs.getDate("Acval_cal_date");
			this.Av_BOY = rs.getDouble("Av_BOY");
			this.Av_BOM = rs.getDouble("Av_BOM");
			this.TBMoney = rs.getDouble("TBMoney");
			this.TBMDate = rs.getDate("TBMDate");
			if( rs.getString("TransSaleChnl") == null )
				this.TransSaleChnl = null;
			else
				this.TransSaleChnl = rs.getString("TransSaleChnl").trim();

			this.Cuml_PartSurrender = rs.getDouble("Cuml_PartSurrender");
			this.BonusAmnt = rs.getDouble("BonusAmnt");
			this.OtherPrem = rs.getDouble("OtherPrem");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOReserve表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOReserveSchema getSchema()
	{
		LOReserveSchema aLOReserveSchema = new LOReserveSchema();
		aLOReserveSchema.setSchema(this);
		return aLOReserveSchema;
	}

	public LOReserveDB getDB()
	{
		LOReserveDB aDBOper = new LOReserveDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOReserve描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredAppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecInsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SecBirthDay ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SecInsuredAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastRevDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InvalidDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FreeStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeadDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ShouldGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StopReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StopDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RevGetAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RevGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RevGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RevGetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AnnMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeathMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MedMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OccPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HealthPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LastPayPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(YearIdx));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rev3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rev1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rev2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rev4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CheckFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ThisPayPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SurMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokingFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FloatRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GroupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ACType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FreeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Av_last_cal));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Acval_cal_date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Av_BOY));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Av_BOM));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TBMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TBMDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransSaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Cuml_PartSurrender));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherPrem));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOReserve>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			InsuredAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			SecInsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SecBirthDay = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			SecInsuredAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			LastRevDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			InvalidDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			FreeStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			DeadDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ShouldGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			StopReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			StopDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			RevGetAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			RevGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			RevGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			RevGetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			AnnMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			DeathMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			MedMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			ExpMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			TotalMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			OccPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			HealthPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			LastPayPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			YearIdx= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
			CalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).intValue();
			Rev3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			Rev1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			Rev2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			Rev4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			CheckFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).intValue();
			CalFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			ThisPayPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).doubleValue();
			PayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			SurMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			SmokingFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			FloatRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).doubleValue();
			GroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			ACType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65,SysConst.PACKAGESPILTER));
			FreeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			Av_last_cal = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,68,SysConst.PACKAGESPILTER))).doubleValue();
			Acval_cal_date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69,SysConst.PACKAGESPILTER));
			Av_BOY = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).doubleValue();
			Av_BOM = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).doubleValue();
			TBMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).doubleValue();
			TBMDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73,SysConst.PACKAGESPILTER));
			TransSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			Cuml_PartSurrender = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			BonusAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).doubleValue();
			OtherPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveSchema";
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("PolStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolStatus));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
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
		if (FCode.equalsIgnoreCase("SecInsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecInsuredSex));
		}
		if (FCode.equalsIgnoreCase("SecBirthDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSecBirthDay()));
		}
		if (FCode.equalsIgnoreCase("SecInsuredAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecInsuredAge));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("LastRevDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastRevDate()));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("InvalidDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInvalidDate()));
		}
		if (FCode.equalsIgnoreCase("FreeStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFreeStartDate()));
		}
		if (FCode.equalsIgnoreCase("DeadDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeadDate()));
		}
		if (FCode.equalsIgnoreCase("ShouldGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShouldGetMoney));
		}
		if (FCode.equalsIgnoreCase("StopReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StopReason));
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
		}
		if (FCode.equalsIgnoreCase("RevGetAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RevGetAge));
		}
		if (FCode.equalsIgnoreCase("RevGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRevGetDate()));
		}
		if (FCode.equalsIgnoreCase("RevGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RevGetMoney));
		}
		if (FCode.equalsIgnoreCase("RevGetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RevGetIntv));
		}
		if (FCode.equalsIgnoreCase("AnnMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AnnMoney));
		}
		if (FCode.equalsIgnoreCase("DeathMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeathMoney));
		}
		if (FCode.equalsIgnoreCase("MedMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedMoney));
		}
		if (FCode.equalsIgnoreCase("ExpMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpMoney));
		}
		if (FCode.equalsIgnoreCase("TotalMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalMoney));
		}
		if (FCode.equalsIgnoreCase("OccPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccPrem));
		}
		if (FCode.equalsIgnoreCase("HealthPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthPrem));
		}
		if (FCode.equalsIgnoreCase("LastPayPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastPayPrem));
		}
		if (FCode.equalsIgnoreCase("YearIdx"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YearIdx));
		}
		if (FCode.equalsIgnoreCase("CalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalYear));
		}
		if (FCode.equalsIgnoreCase("Rev3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rev3));
		}
		if (FCode.equalsIgnoreCase("Rev1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rev1));
		}
		if (FCode.equalsIgnoreCase("Rev2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rev2));
		}
		if (FCode.equalsIgnoreCase("Rev4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rev4));
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckFlag));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("ThisPayPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ThisPayPrem));
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("SurMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurMoney));
		}
		if (FCode.equalsIgnoreCase("SmokingFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokingFlag));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FloatRate));
		}
		if (FCode.equalsIgnoreCase("GroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupNo));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("ACType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ACType));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreeFlag));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("Av_last_cal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Av_last_cal));
		}
		if (FCode.equalsIgnoreCase("Acval_cal_date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcval_cal_date()));
		}
		if (FCode.equalsIgnoreCase("Av_BOY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Av_BOY));
		}
		if (FCode.equalsIgnoreCase("Av_BOM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Av_BOM));
		}
		if (FCode.equalsIgnoreCase("TBMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TBMoney));
		}
		if (FCode.equalsIgnoreCase("TBMDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTBMDate()));
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSaleChnl));
		}
		if (FCode.equalsIgnoreCase("Cuml_PartSurrender"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Cuml_PartSurrender));
		}
		if (FCode.equalsIgnoreCase("BonusAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusAmnt));
		}
		if (FCode.equalsIgnoreCase("OtherPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherPrem));
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
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolStatus);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 9:
				strFieldValue = String.valueOf(InsuredAppAge);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SecInsuredSex);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSecBirthDay()));
				break;
			case 12:
				strFieldValue = String.valueOf(SecInsuredAge);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastRevDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = String.valueOf(Years);
				break;
			case 17:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 18:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 19:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 20:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInvalidDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFreeStartDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeadDate()));
				break;
			case 24:
				strFieldValue = String.valueOf(ShouldGetMoney);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(StopReason);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
				break;
			case 27:
				strFieldValue = String.valueOf(RevGetAge);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRevGetDate()));
				break;
			case 29:
				strFieldValue = String.valueOf(RevGetMoney);
				break;
			case 30:
				strFieldValue = String.valueOf(RevGetIntv);
				break;
			case 31:
				strFieldValue = String.valueOf(AnnMoney);
				break;
			case 32:
				strFieldValue = String.valueOf(DeathMoney);
				break;
			case 33:
				strFieldValue = String.valueOf(MedMoney);
				break;
			case 34:
				strFieldValue = String.valueOf(ExpMoney);
				break;
			case 35:
				strFieldValue = String.valueOf(TotalMoney);
				break;
			case 36:
				strFieldValue = String.valueOf(OccPrem);
				break;
			case 37:
				strFieldValue = String.valueOf(HealthPrem);
				break;
			case 38:
				strFieldValue = String.valueOf(LastPayPrem);
				break;
			case 39:
				strFieldValue = String.valueOf(YearIdx);
				break;
			case 40:
				strFieldValue = String.valueOf(CalYear);
				break;
			case 41:
				strFieldValue = String.valueOf(Rev3);
				break;
			case 42:
				strFieldValue = String.valueOf(Rev1);
				break;
			case 43:
				strFieldValue = String.valueOf(Rev2);
				break;
			case 44:
				strFieldValue = String.valueOf(Rev4);
				break;
			case 45:
				strFieldValue = String.valueOf(CheckFlag);
				break;
			case 46:
				strFieldValue = String.valueOf(CalFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 49:
				strFieldValue = String.valueOf(ThisPayPrem);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
				break;
			case 51:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 54:
				strFieldValue = String.valueOf(SurMoney);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(SmokingFlag);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 58:
				strFieldValue = String.valueOf(FloatRate);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(GroupNo);
				break;
			case 60:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(ACType);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(FreeFlag);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 67:
				strFieldValue = String.valueOf(Av_last_cal);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcval_cal_date()));
				break;
			case 69:
				strFieldValue = String.valueOf(Av_BOY);
				break;
			case 70:
				strFieldValue = String.valueOf(Av_BOM);
				break;
			case 71:
				strFieldValue = String.valueOf(TBMoney);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTBMDate()));
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(TransSaleChnl);
				break;
			case 74:
				strFieldValue = String.valueOf(Cuml_PartSurrender);
				break;
			case 75:
				strFieldValue = String.valueOf(BonusAmnt);
				break;
			case 76:
				strFieldValue = String.valueOf(OtherPrem);
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

		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("PolStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolStatus = FValue.trim();
			}
			else
				PolStatus = null;
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
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
		if (FCode.equalsIgnoreCase("SecInsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecInsuredSex = FValue.trim();
			}
			else
				SecInsuredSex = null;
		}
		if (FCode.equalsIgnoreCase("SecBirthDay"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SecBirthDay = fDate.getDate( FValue );
			}
			else
				SecBirthDay = null;
		}
		if (FCode.equalsIgnoreCase("SecInsuredAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SecInsuredAge = i;
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
		if (FCode.equalsIgnoreCase("LastRevDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastRevDate = fDate.getDate( FValue );
			}
			else
				LastRevDate = null;
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
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
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
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvalidDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InvalidDate = fDate.getDate( FValue );
			}
			else
				InvalidDate = null;
		}
		if (FCode.equalsIgnoreCase("FreeStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FreeStartDate = fDate.getDate( FValue );
			}
			else
				FreeStartDate = null;
		}
		if (FCode.equalsIgnoreCase("DeadDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeadDate = fDate.getDate( FValue );
			}
			else
				DeadDate = null;
		}
		if (FCode.equalsIgnoreCase("ShouldGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ShouldGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("StopReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StopReason = FValue.trim();
			}
			else
				StopReason = null;
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StopDate = fDate.getDate( FValue );
			}
			else
				StopDate = null;
		}
		if (FCode.equalsIgnoreCase("RevGetAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RevGetAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("RevGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RevGetDate = fDate.getDate( FValue );
			}
			else
				RevGetDate = null;
		}
		if (FCode.equalsIgnoreCase("RevGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RevGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("RevGetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RevGetIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("AnnMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AnnMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("DeathMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DeathMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("MedMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MedMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("TotalMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("OccPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OccPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("HealthPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				HealthPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastPayPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastPayPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("YearIdx"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				YearIdx = i;
			}
		}
		if (FCode.equalsIgnoreCase("CalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("Rev3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rev3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Rev1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rev1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Rev2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rev2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Rev4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rev4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CheckFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalFlag = i;
			}
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
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("ThisPayPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ThisPayPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayToDate = fDate.getDate( FValue );
			}
			else
				PayToDate = null;
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
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
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
		if (FCode.equalsIgnoreCase("SurMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SurMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("SmokingFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SmokingFlag = FValue.trim();
			}
			else
				SmokingFlag = null;
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
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomGetPolDate = null;
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
		if (FCode.equalsIgnoreCase("GroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GroupNo = FValue.trim();
			}
			else
				GroupNo = null;
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("ACType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ACType = FValue.trim();
			}
			else
				ACType = null;
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
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FreeFlag = FValue.trim();
			}
			else
				FreeFlag = null;
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
		if (FCode.equalsIgnoreCase("Av_last_cal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Av_last_cal = d;
			}
		}
		if (FCode.equalsIgnoreCase("Acval_cal_date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Acval_cal_date = fDate.getDate( FValue );
			}
			else
				Acval_cal_date = null;
		}
		if (FCode.equalsIgnoreCase("Av_BOY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Av_BOY = d;
			}
		}
		if (FCode.equalsIgnoreCase("Av_BOM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Av_BOM = d;
			}
		}
		if (FCode.equalsIgnoreCase("TBMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TBMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("TBMDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TBMDate = fDate.getDate( FValue );
			}
			else
				TBMDate = null;
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
		if (FCode.equalsIgnoreCase("Cuml_PartSurrender"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Cuml_PartSurrender = d;
			}
		}
		if (FCode.equalsIgnoreCase("BonusAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OtherPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OtherPrem = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOReserveSchema other = (LOReserveSchema)otherObject;
		return
			ManageCom.equals(other.getManageCom())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& PolStatus.equals(other.getPolStatus())
			&& RiskCode.equals(other.getRiskCode())
			&& SaleChnl.equals(other.getSaleChnl())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredAppAge == other.getInsuredAppAge()
			&& SecInsuredSex.equals(other.getSecInsuredSex())
			&& fDate.getString(SecBirthDay).equals(other.getSecBirthDay())
			&& SecInsuredAge == other.getSecInsuredAge()
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(LastRevDate).equals(other.getLastRevDate())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& Years == other.getYears()
			&& PayYears == other.getPayYears()
			&& PayIntv == other.getPayIntv()
			&& StandPrem == other.getStandPrem()
			&& Amnt == other.getAmnt()
			&& fDate.getString(InvalidDate).equals(other.getInvalidDate())
			&& fDate.getString(FreeStartDate).equals(other.getFreeStartDate())
			&& fDate.getString(DeadDate).equals(other.getDeadDate())
			&& ShouldGetMoney == other.getShouldGetMoney()
			&& StopReason.equals(other.getStopReason())
			&& fDate.getString(StopDate).equals(other.getStopDate())
			&& RevGetAge == other.getRevGetAge()
			&& fDate.getString(RevGetDate).equals(other.getRevGetDate())
			&& RevGetMoney == other.getRevGetMoney()
			&& RevGetIntv == other.getRevGetIntv()
			&& AnnMoney == other.getAnnMoney()
			&& DeathMoney == other.getDeathMoney()
			&& MedMoney == other.getMedMoney()
			&& ExpMoney == other.getExpMoney()
			&& TotalMoney == other.getTotalMoney()
			&& OccPrem == other.getOccPrem()
			&& HealthPrem == other.getHealthPrem()
			&& LastPayPrem == other.getLastPayPrem()
			&& YearIdx == other.getYearIdx()
			&& CalYear == other.getCalYear()
			&& Rev3 == other.getRev3()
			&& Rev1 == other.getRev1()
			&& Rev2 == other.getRev2()
			&& Rev4 == other.getRev4()
			&& CheckFlag == other.getCheckFlag()
			&& CalFlag == other.getCalFlag()
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& ThisPayPrem == other.getThisPayPrem()
			&& fDate.getString(PayToDate).equals(other.getPayToDate())
			&& ManageFeeRate == other.getManageFeeRate()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& SurMoney == other.getSurMoney()
			&& SmokingFlag.equals(other.getSmokingFlag())
			&& OccupationType.equals(other.getOccupationType())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& FloatRate == other.getFloatRate()
			&& GroupNo.equals(other.getGroupNo())
			&& GetMoney == other.getGetMoney()
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& AgentCom.equals(other.getAgentCom())
			&& ACType.equals(other.getACType())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& FreeFlag.equals(other.getFreeFlag())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& Av_last_cal == other.getAv_last_cal()
			&& fDate.getString(Acval_cal_date).equals(other.getAcval_cal_date())
			&& Av_BOY == other.getAv_BOY()
			&& Av_BOM == other.getAv_BOM()
			&& TBMoney == other.getTBMoney()
			&& fDate.getString(TBMDate).equals(other.getTBMDate())
			&& TransSaleChnl.equals(other.getTransSaleChnl())
			&& Cuml_PartSurrender == other.getCuml_PartSurrender()
			&& BonusAmnt == other.getBonusAmnt()
			&& OtherPrem == other.getOtherPrem();
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
		if( strFieldName.equals("ManageCom") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolStatus") ) {
			return 3;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 4;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 5;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 6;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 7;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 8;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return 9;
		}
		if( strFieldName.equals("SecInsuredSex") ) {
			return 10;
		}
		if( strFieldName.equals("SecBirthDay") ) {
			return 11;
		}
		if( strFieldName.equals("SecInsuredAge") ) {
			return 12;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 13;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("Years") ) {
			return 16;
		}
		if( strFieldName.equals("PayYears") ) {
			return 17;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 18;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 19;
		}
		if( strFieldName.equals("Amnt") ) {
			return 20;
		}
		if( strFieldName.equals("InvalidDate") ) {
			return 21;
		}
		if( strFieldName.equals("FreeStartDate") ) {
			return 22;
		}
		if( strFieldName.equals("DeadDate") ) {
			return 23;
		}
		if( strFieldName.equals("ShouldGetMoney") ) {
			return 24;
		}
		if( strFieldName.equals("StopReason") ) {
			return 25;
		}
		if( strFieldName.equals("StopDate") ) {
			return 26;
		}
		if( strFieldName.equals("RevGetAge") ) {
			return 27;
		}
		if( strFieldName.equals("RevGetDate") ) {
			return 28;
		}
		if( strFieldName.equals("RevGetMoney") ) {
			return 29;
		}
		if( strFieldName.equals("RevGetIntv") ) {
			return 30;
		}
		if( strFieldName.equals("AnnMoney") ) {
			return 31;
		}
		if( strFieldName.equals("DeathMoney") ) {
			return 32;
		}
		if( strFieldName.equals("MedMoney") ) {
			return 33;
		}
		if( strFieldName.equals("ExpMoney") ) {
			return 34;
		}
		if( strFieldName.equals("TotalMoney") ) {
			return 35;
		}
		if( strFieldName.equals("OccPrem") ) {
			return 36;
		}
		if( strFieldName.equals("HealthPrem") ) {
			return 37;
		}
		if( strFieldName.equals("LastPayPrem") ) {
			return 38;
		}
		if( strFieldName.equals("YearIdx") ) {
			return 39;
		}
		if( strFieldName.equals("CalYear") ) {
			return 40;
		}
		if( strFieldName.equals("Rev3") ) {
			return 41;
		}
		if( strFieldName.equals("Rev1") ) {
			return 42;
		}
		if( strFieldName.equals("Rev2") ) {
			return 43;
		}
		if( strFieldName.equals("Rev4") ) {
			return 44;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return 45;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 46;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 47;
		}
		if( strFieldName.equals("SignDate") ) {
			return 48;
		}
		if( strFieldName.equals("ThisPayPrem") ) {
			return 49;
		}
		if( strFieldName.equals("PayToDate") ) {
			return 50;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 51;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 52;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 53;
		}
		if( strFieldName.equals("SurMoney") ) {
			return 54;
		}
		if( strFieldName.equals("SmokingFlag") ) {
			return 55;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 56;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 57;
		}
		if( strFieldName.equals("FloatRate") ) {
			return 58;
		}
		if( strFieldName.equals("GroupNo") ) {
			return 59;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 60;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 61;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 62;
		}
		if( strFieldName.equals("ACType") ) {
			return 63;
		}
		if( strFieldName.equals("EndDate") ) {
			return 64;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return 65;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 66;
		}
		if( strFieldName.equals("Av_last_cal") ) {
			return 67;
		}
		if( strFieldName.equals("Acval_cal_date") ) {
			return 68;
		}
		if( strFieldName.equals("Av_BOY") ) {
			return 69;
		}
		if( strFieldName.equals("Av_BOM") ) {
			return 70;
		}
		if( strFieldName.equals("TBMoney") ) {
			return 71;
		}
		if( strFieldName.equals("TBMDate") ) {
			return 72;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return 73;
		}
		if( strFieldName.equals("Cuml_PartSurrender") ) {
			return 74;
		}
		if( strFieldName.equals("BonusAmnt") ) {
			return 75;
		}
		if( strFieldName.equals("OtherPrem") ) {
			return 76;
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
				strFieldName = "ManageCom";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "PolStatus";
				break;
			case 4:
				strFieldName = "RiskCode";
				break;
			case 5:
				strFieldName = "SaleChnl";
				break;
			case 6:
				strFieldName = "InsuredNo";
				break;
			case 7:
				strFieldName = "InsuredSex";
				break;
			case 8:
				strFieldName = "InsuredBirthday";
				break;
			case 9:
				strFieldName = "InsuredAppAge";
				break;
			case 10:
				strFieldName = "SecInsuredSex";
				break;
			case 11:
				strFieldName = "SecBirthDay";
				break;
			case 12:
				strFieldName = "SecInsuredAge";
				break;
			case 13:
				strFieldName = "CValiDate";
				break;
			case 14:
				strFieldName = "LastRevDate";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "Years";
				break;
			case 17:
				strFieldName = "PayYears";
				break;
			case 18:
				strFieldName = "PayIntv";
				break;
			case 19:
				strFieldName = "StandPrem";
				break;
			case 20:
				strFieldName = "Amnt";
				break;
			case 21:
				strFieldName = "InvalidDate";
				break;
			case 22:
				strFieldName = "FreeStartDate";
				break;
			case 23:
				strFieldName = "DeadDate";
				break;
			case 24:
				strFieldName = "ShouldGetMoney";
				break;
			case 25:
				strFieldName = "StopReason";
				break;
			case 26:
				strFieldName = "StopDate";
				break;
			case 27:
				strFieldName = "RevGetAge";
				break;
			case 28:
				strFieldName = "RevGetDate";
				break;
			case 29:
				strFieldName = "RevGetMoney";
				break;
			case 30:
				strFieldName = "RevGetIntv";
				break;
			case 31:
				strFieldName = "AnnMoney";
				break;
			case 32:
				strFieldName = "DeathMoney";
				break;
			case 33:
				strFieldName = "MedMoney";
				break;
			case 34:
				strFieldName = "ExpMoney";
				break;
			case 35:
				strFieldName = "TotalMoney";
				break;
			case 36:
				strFieldName = "OccPrem";
				break;
			case 37:
				strFieldName = "HealthPrem";
				break;
			case 38:
				strFieldName = "LastPayPrem";
				break;
			case 39:
				strFieldName = "YearIdx";
				break;
			case 40:
				strFieldName = "CalYear";
				break;
			case 41:
				strFieldName = "Rev3";
				break;
			case 42:
				strFieldName = "Rev1";
				break;
			case 43:
				strFieldName = "Rev2";
				break;
			case 44:
				strFieldName = "Rev4";
				break;
			case 45:
				strFieldName = "CheckFlag";
				break;
			case 46:
				strFieldName = "CalFlag";
				break;
			case 47:
				strFieldName = "ModifyDate";
				break;
			case 48:
				strFieldName = "SignDate";
				break;
			case 49:
				strFieldName = "ThisPayPrem";
				break;
			case 50:
				strFieldName = "PayToDate";
				break;
			case 51:
				strFieldName = "ManageFeeRate";
				break;
			case 52:
				strFieldName = "InsuYearFlag";
				break;
			case 53:
				strFieldName = "PayEndYearFlag";
				break;
			case 54:
				strFieldName = "SurMoney";
				break;
			case 55:
				strFieldName = "SmokingFlag";
				break;
			case 56:
				strFieldName = "OccupationType";
				break;
			case 57:
				strFieldName = "CustomGetPolDate";
				break;
			case 58:
				strFieldName = "FloatRate";
				break;
			case 59:
				strFieldName = "GroupNo";
				break;
			case 60:
				strFieldName = "GetMoney";
				break;
			case 61:
				strFieldName = "GetDutyKind";
				break;
			case 62:
				strFieldName = "AgentCom";
				break;
			case 63:
				strFieldName = "ACType";
				break;
			case 64:
				strFieldName = "EndDate";
				break;
			case 65:
				strFieldName = "FreeFlag";
				break;
			case 66:
				strFieldName = "AutoPayFlag";
				break;
			case 67:
				strFieldName = "Av_last_cal";
				break;
			case 68:
				strFieldName = "Acval_cal_date";
				break;
			case 69:
				strFieldName = "Av_BOY";
				break;
			case 70:
				strFieldName = "Av_BOM";
				break;
			case 71:
				strFieldName = "TBMoney";
				break;
			case 72:
				strFieldName = "TBMDate";
				break;
			case 73:
				strFieldName = "TransSaleChnl";
				break;
			case 74:
				strFieldName = "Cuml_PartSurrender";
				break;
			case 75:
				strFieldName = "BonusAmnt";
				break;
			case 76:
				strFieldName = "OtherPrem";
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolStatus") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
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
		if( strFieldName.equals("SecInsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecBirthDay") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SecInsuredAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastRevDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvalidDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FreeStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DeadDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ShouldGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StopReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StopDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RevGetAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RevGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RevGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RevGetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AnnMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeathMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MedMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TotalMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OccPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HealthPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastPayPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YearIdx") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Rev3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Rev1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Rev2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Rev4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ThisPayPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SmokingFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FloatRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GroupNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ACType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Av_last_cal") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Acval_cal_date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Av_BOY") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Av_BOM") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TBMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TBMDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Cuml_PartSurrender") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OtherPrem") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 40:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 46:
				nFieldType = Schema.TYPE_INT;
				break;
			case 47:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 48:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 49:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 50:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 58:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 68:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 69:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 70:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 71:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 75:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 76:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
