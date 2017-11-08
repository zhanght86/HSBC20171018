/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

// schema 与数据库不一致，暂不处理


package com.sinosoft.lis.schema;

import org.apache.log4j.Logger;
import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LFRiskAppDB;

/*
 * 与数据库结构不一致，暂不处理
 * <p>ClassName: LFRiskAppSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */

public class LFRiskAppSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LFRiskAppSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 管理机构 */
	private String ManageCom;
	/** 缴费间隔 */
	private int PayIntv;
	/** 销售渠道 */
	private String SaleChnl;
	/** 首期续期标志 */
	private String FirstPayFlag;
	/** 团单个单标志 */
	private String PersonPolFlag;
	/** 报表日期 */
	private Date ReportDate;
	/** Amnt */
	private double Amnt;
	/** Amntsum */
	private double AmntSum;
	/** Prem */
	private double Prem;
	/** Premsum */
	private double PremSum;
	/** Insuredcount */
	private int InsuredCount;
	/** Insuredcountsum */
	private int InsuredCountSum;
	/** Polcount */
	private int PolCount;
	/** Polcountsum */
	private int PolCountSum;
	/** 本年_保额_新增 */
	private double CurYearAmnt;
	/** 本年_保额_累计 */
	private double CurYearAmntSum;
	/** 期末有效_保额_新增 */
	private double AllYearAmnt;
	/** 期末有效_保额_累计 */
	private double AllYearAmntSum;
	/** 本年_保费_新增 */
	private double CurYearPrem;
	/** 本年_保费_累计 */
	private double CurYearPremSum;
	/** 期末有效_保费_新增 */
	private double AllYearPrem;
	/** 期末有效_保费_累计 */
	private double AllYearPremSum;
	/** 本年_承保人次_新增 */
	private int CurYearInsured;
	/** 本年_承保人次_累计 */
	private int CurYearInsuredSum;
	/** 期末有效_承保人次_新增 */
	private int AllYearInsured;
	/** 期末有效_承保人次_累计 */
	private int AllYearInsuredSum;
	/** 本年_保单件次_新增 */
	private int CurYearPol;
	/** 本年_保单件次_累计 */
	private int CurYearPolSum;
	/** 期末有效_承保件次_新增 */
	private int AllYearPol;
	/** 期末有效_承保件次_累计 */
	private int AllYearPolSum;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 33;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFRiskAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "RiskCode";
		pk[1] = "ManageCom";
		pk[2] = "PayIntv";
		pk[3] = "SaleChnl";
		pk[4] = "FirstPayFlag";
		pk[5] = "PersonPolFlag";
		pk[6] = "ReportDate";

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
		LFRiskAppSchema cloned = (LFRiskAppSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>6)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值6");
		RiskCode = aRiskCode;
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
	* 01--个人代理<p>
	* 02--公司直销<p>
	* 03--保险专业代理<p>
	* 04--银行邮政代理<p>
	* 05--其他兼业代理<p>
	* 06--保险经纪业务
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
	public String getFirstPayFlag()
	{
		return FirstPayFlag;
	}
	public void setFirstPayFlag(String aFirstPayFlag)
	{
		if(aFirstPayFlag!=null && aFirstPayFlag.length()>1)
			throw new IllegalArgumentException("首期续期标志FirstPayFlag值"+aFirstPayFlag+"的长度"+aFirstPayFlag.length()+"大于最大值1");
		FirstPayFlag = aFirstPayFlag;
	}
	public String getPersonPolFlag()
	{
		return PersonPolFlag;
	}
	public void setPersonPolFlag(String aPersonPolFlag)
	{
		if(aPersonPolFlag!=null && aPersonPolFlag.length()>1)
			throw new IllegalArgumentException("团单个单标志PersonPolFlag值"+aPersonPolFlag+"的长度"+aPersonPolFlag.length()+"大于最大值1");
		PersonPolFlag = aPersonPolFlag;
	}
	public String getReportDate()
	{
		if( ReportDate != null )
			return fDate.getString(ReportDate);
		else
			return null;
	}
	public void setReportDate(Date aReportDate)
	{
		ReportDate = aReportDate;
	}
	public void setReportDate(String aReportDate)
	{
		if (aReportDate != null && !aReportDate.equals("") )
		{
			ReportDate = fDate.getDate( aReportDate );
		}
		else
			ReportDate = null;
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

	public double getAmntSum()
	{
		return AmntSum;
	}
	public void setAmntSum(double aAmntSum)
	{
		AmntSum = aAmntSum;
	}
	public void setAmntSum(String aAmntSum)
	{
		if (aAmntSum != null && !aAmntSum.equals(""))
		{
			Double tDouble = new Double(aAmntSum);
			double d = tDouble.doubleValue();
			AmntSum = d;
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

	public double getPremSum()
	{
		return PremSum;
	}
	public void setPremSum(double aPremSum)
	{
		PremSum = aPremSum;
	}
	public void setPremSum(String aPremSum)
	{
		if (aPremSum != null && !aPremSum.equals(""))
		{
			Double tDouble = new Double(aPremSum);
			double d = tDouble.doubleValue();
			PremSum = d;
		}
	}

	public int getInsuredCount()
	{
		return InsuredCount;
	}
	public void setInsuredCount(int aInsuredCount)
	{
		InsuredCount = aInsuredCount;
	}
	public void setInsuredCount(String aInsuredCount)
	{
		if (aInsuredCount != null && !aInsuredCount.equals(""))
		{
			Integer tInteger = new Integer(aInsuredCount);
			int i = tInteger.intValue();
			InsuredCount = i;
		}
	}

	public int getInsuredCountSum()
	{
		return InsuredCountSum;
	}
	public void setInsuredCountSum(int aInsuredCountSum)
	{
		InsuredCountSum = aInsuredCountSum;
	}
	public void setInsuredCountSum(String aInsuredCountSum)
	{
		if (aInsuredCountSum != null && !aInsuredCountSum.equals(""))
		{
			Integer tInteger = new Integer(aInsuredCountSum);
			int i = tInteger.intValue();
			InsuredCountSum = i;
		}
	}

	public int getPolCount()
	{
		return PolCount;
	}
	public void setPolCount(int aPolCount)
	{
		PolCount = aPolCount;
	}
	public void setPolCount(String aPolCount)
	{
		if (aPolCount != null && !aPolCount.equals(""))
		{
			Integer tInteger = new Integer(aPolCount);
			int i = tInteger.intValue();
			PolCount = i;
		}
	}

	public int getPolCountSum()
	{
		return PolCountSum;
	}
	public void setPolCountSum(int aPolCountSum)
	{
		PolCountSum = aPolCountSum;
	}
	public void setPolCountSum(String aPolCountSum)
	{
		if (aPolCountSum != null && !aPolCountSum.equals(""))
		{
			Integer tInteger = new Integer(aPolCountSum);
			int i = tInteger.intValue();
			PolCountSum = i;
		}
	}

	/**
	* 从本年年初到现在承保的保单在当天的保额增额
	*/
	public double getCurYearAmnt()
	{
		return CurYearAmnt;
	}
	public void setCurYearAmnt(double aCurYearAmnt)
	{
		CurYearAmnt = aCurYearAmnt;
	}
	public void setCurYearAmnt(String aCurYearAmnt)
	{
		if (aCurYearAmnt != null && !aCurYearAmnt.equals(""))
		{
			Double tDouble = new Double(aCurYearAmnt);
			double d = tDouble.doubleValue();
			CurYearAmnt = d;
		}
	}

	/**
	* 从本年年初到现在承保的保单到当天的保额累计
	*/
	public double getCurYearAmntSum()
	{
		return CurYearAmntSum;
	}
	public void setCurYearAmntSum(double aCurYearAmntSum)
	{
		CurYearAmntSum = aCurYearAmntSum;
	}
	public void setCurYearAmntSum(String aCurYearAmntSum)
	{
		if (aCurYearAmntSum != null && !aCurYearAmntSum.equals(""))
		{
			Double tDouble = new Double(aCurYearAmntSum);
			double d = tDouble.doubleValue();
			CurYearAmntSum = d;
		}
	}

	/**
	* 从开业到现在承保的保单在当天的保额增额
	*/
	public double getAllYearAmnt()
	{
		return AllYearAmnt;
	}
	public void setAllYearAmnt(double aAllYearAmnt)
	{
		AllYearAmnt = aAllYearAmnt;
	}
	public void setAllYearAmnt(String aAllYearAmnt)
	{
		if (aAllYearAmnt != null && !aAllYearAmnt.equals(""))
		{
			Double tDouble = new Double(aAllYearAmnt);
			double d = tDouble.doubleValue();
			AllYearAmnt = d;
		}
	}

	/**
	* 从开业到现在承保的保单到当天的保额累计
	*/
	public double getAllYearAmntSum()
	{
		return AllYearAmntSum;
	}
	public void setAllYearAmntSum(double aAllYearAmntSum)
	{
		AllYearAmntSum = aAllYearAmntSum;
	}
	public void setAllYearAmntSum(String aAllYearAmntSum)
	{
		if (aAllYearAmntSum != null && !aAllYearAmntSum.equals(""))
		{
			Double tDouble = new Double(aAllYearAmntSum);
			double d = tDouble.doubleValue();
			AllYearAmntSum = d;
		}
	}

	public double getCurYearPrem()
	{
		return CurYearPrem;
	}
	public void setCurYearPrem(double aCurYearPrem)
	{
		CurYearPrem = aCurYearPrem;
	}
	public void setCurYearPrem(String aCurYearPrem)
	{
		if (aCurYearPrem != null && !aCurYearPrem.equals(""))
		{
			Double tDouble = new Double(aCurYearPrem);
			double d = tDouble.doubleValue();
			CurYearPrem = d;
		}
	}

	public double getCurYearPremSum()
	{
		return CurYearPremSum;
	}
	public void setCurYearPremSum(double aCurYearPremSum)
	{
		CurYearPremSum = aCurYearPremSum;
	}
	public void setCurYearPremSum(String aCurYearPremSum)
	{
		if (aCurYearPremSum != null && !aCurYearPremSum.equals(""))
		{
			Double tDouble = new Double(aCurYearPremSum);
			double d = tDouble.doubleValue();
			CurYearPremSum = d;
		}
	}

	public double getAllYearPrem()
	{
		return AllYearPrem;
	}
	public void setAllYearPrem(double aAllYearPrem)
	{
		AllYearPrem = aAllYearPrem;
	}
	public void setAllYearPrem(String aAllYearPrem)
	{
		if (aAllYearPrem != null && !aAllYearPrem.equals(""))
		{
			Double tDouble = new Double(aAllYearPrem);
			double d = tDouble.doubleValue();
			AllYearPrem = d;
		}
	}

	public double getAllYearPremSum()
	{
		return AllYearPremSum;
	}
	public void setAllYearPremSum(double aAllYearPremSum)
	{
		AllYearPremSum = aAllYearPremSum;
	}
	public void setAllYearPremSum(String aAllYearPremSum)
	{
		if (aAllYearPremSum != null && !aAllYearPremSum.equals(""))
		{
			Double tDouble = new Double(aAllYearPremSum);
			double d = tDouble.doubleValue();
			AllYearPremSum = d;
		}
	}

	public int getCurYearInsured()
	{
		return CurYearInsured;
	}
	public void setCurYearInsured(int aCurYearInsured)
	{
		CurYearInsured = aCurYearInsured;
	}
	public void setCurYearInsured(String aCurYearInsured)
	{
		if (aCurYearInsured != null && !aCurYearInsured.equals(""))
		{
			Integer tInteger = new Integer(aCurYearInsured);
			int i = tInteger.intValue();
			CurYearInsured = i;
		}
	}

	public int getCurYearInsuredSum()
	{
		return CurYearInsuredSum;
	}
	public void setCurYearInsuredSum(int aCurYearInsuredSum)
	{
		CurYearInsuredSum = aCurYearInsuredSum;
	}
	public void setCurYearInsuredSum(String aCurYearInsuredSum)
	{
		if (aCurYearInsuredSum != null && !aCurYearInsuredSum.equals(""))
		{
			Integer tInteger = new Integer(aCurYearInsuredSum);
			int i = tInteger.intValue();
			CurYearInsuredSum = i;
		}
	}

	public int getAllYearInsured()
	{
		return AllYearInsured;
	}
	public void setAllYearInsured(int aAllYearInsured)
	{
		AllYearInsured = aAllYearInsured;
	}
	public void setAllYearInsured(String aAllYearInsured)
	{
		if (aAllYearInsured != null && !aAllYearInsured.equals(""))
		{
			Integer tInteger = new Integer(aAllYearInsured);
			int i = tInteger.intValue();
			AllYearInsured = i;
		}
	}

	public int getAllYearInsuredSum()
	{
		return AllYearInsuredSum;
	}
	public void setAllYearInsuredSum(int aAllYearInsuredSum)
	{
		AllYearInsuredSum = aAllYearInsuredSum;
	}
	public void setAllYearInsuredSum(String aAllYearInsuredSum)
	{
		if (aAllYearInsuredSum != null && !aAllYearInsuredSum.equals(""))
		{
			Integer tInteger = new Integer(aAllYearInsuredSum);
			int i = tInteger.intValue();
			AllYearInsuredSum = i;
		}
	}

	public int getCurYearPol()
	{
		return CurYearPol;
	}
	public void setCurYearPol(int aCurYearPol)
	{
		CurYearPol = aCurYearPol;
	}
	public void setCurYearPol(String aCurYearPol)
	{
		if (aCurYearPol != null && !aCurYearPol.equals(""))
		{
			Integer tInteger = new Integer(aCurYearPol);
			int i = tInteger.intValue();
			CurYearPol = i;
		}
	}

	public int getCurYearPolSum()
	{
		return CurYearPolSum;
	}
	public void setCurYearPolSum(int aCurYearPolSum)
	{
		CurYearPolSum = aCurYearPolSum;
	}
	public void setCurYearPolSum(String aCurYearPolSum)
	{
		if (aCurYearPolSum != null && !aCurYearPolSum.equals(""))
		{
			Integer tInteger = new Integer(aCurYearPolSum);
			int i = tInteger.intValue();
			CurYearPolSum = i;
		}
	}

	public int getAllYearPol()
	{
		return AllYearPol;
	}
	public void setAllYearPol(int aAllYearPol)
	{
		AllYearPol = aAllYearPol;
	}
	public void setAllYearPol(String aAllYearPol)
	{
		if (aAllYearPol != null && !aAllYearPol.equals(""))
		{
			Integer tInteger = new Integer(aAllYearPol);
			int i = tInteger.intValue();
			AllYearPol = i;
		}
	}

	public int getAllYearPolSum()
	{
		return AllYearPolSum;
	}
	public void setAllYearPolSum(int aAllYearPolSum)
	{
		AllYearPolSum = aAllYearPolSum;
	}
	public void setAllYearPolSum(String aAllYearPolSum)
	{
		if (aAllYearPolSum != null && !aAllYearPolSum.equals(""))
		{
			Integer tInteger = new Integer(aAllYearPolSum);
			int i = tInteger.intValue();
			AllYearPolSum = i;
		}
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

	/**
	* 使用另外一个 LFRiskAppSchema 对象给 Schema 赋值
	* @param: aLFRiskAppSchema LFRiskAppSchema
	**/
	public void setSchema(LFRiskAppSchema aLFRiskAppSchema)
	{
		this.RiskCode = aLFRiskAppSchema.getRiskCode();
		this.ManageCom = aLFRiskAppSchema.getManageCom();
		this.PayIntv = aLFRiskAppSchema.getPayIntv();
		this.SaleChnl = aLFRiskAppSchema.getSaleChnl();
		this.FirstPayFlag = aLFRiskAppSchema.getFirstPayFlag();
		this.PersonPolFlag = aLFRiskAppSchema.getPersonPolFlag();
		this.ReportDate = fDate.getDate( aLFRiskAppSchema.getReportDate());
		this.Amnt = aLFRiskAppSchema.getAmnt();
		this.AmntSum = aLFRiskAppSchema.getAmntSum();
		this.Prem = aLFRiskAppSchema.getPrem();
		this.PremSum = aLFRiskAppSchema.getPremSum();
		this.InsuredCount = aLFRiskAppSchema.getInsuredCount();
		this.InsuredCountSum = aLFRiskAppSchema.getInsuredCountSum();
		this.PolCount = aLFRiskAppSchema.getPolCount();
		this.PolCountSum = aLFRiskAppSchema.getPolCountSum();
		this.CurYearAmnt = aLFRiskAppSchema.getCurYearAmnt();
		this.CurYearAmntSum = aLFRiskAppSchema.getCurYearAmntSum();
		this.AllYearAmnt = aLFRiskAppSchema.getAllYearAmnt();
		this.AllYearAmntSum = aLFRiskAppSchema.getAllYearAmntSum();
		this.CurYearPrem = aLFRiskAppSchema.getCurYearPrem();
		this.CurYearPremSum = aLFRiskAppSchema.getCurYearPremSum();
		this.AllYearPrem = aLFRiskAppSchema.getAllYearPrem();
		this.AllYearPremSum = aLFRiskAppSchema.getAllYearPremSum();
		this.CurYearInsured = aLFRiskAppSchema.getCurYearInsured();
		this.CurYearInsuredSum = aLFRiskAppSchema.getCurYearInsuredSum();
		this.AllYearInsured = aLFRiskAppSchema.getAllYearInsured();
		this.AllYearInsuredSum = aLFRiskAppSchema.getAllYearInsuredSum();
		this.CurYearPol = aLFRiskAppSchema.getCurYearPol();
		this.CurYearPolSum = aLFRiskAppSchema.getCurYearPolSum();
		this.AllYearPol = aLFRiskAppSchema.getAllYearPol();
		this.AllYearPolSum = aLFRiskAppSchema.getAllYearPolSum();
		this.MakeDate = fDate.getDate( aLFRiskAppSchema.getMakeDate());
		this.MakeTime = aLFRiskAppSchema.getMakeTime();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("FirstPayFlag") == null )
				this.FirstPayFlag = null;
			else
				this.FirstPayFlag = rs.getString("FirstPayFlag").trim();

			if( rs.getString("PersonPolFlag") == null )
				this.PersonPolFlag = null;
			else
				this.PersonPolFlag = rs.getString("PersonPolFlag").trim();

			this.ReportDate = rs.getDate("ReportDate");
			this.Amnt = rs.getDouble("Amnt");
			this.AmntSum = rs.getDouble("AmntSum");
			this.Prem = rs.getDouble("Prem");
			this.PremSum = rs.getDouble("PremSum");
			this.InsuredCount = rs.getInt("InsuredCount");
			this.InsuredCountSum = rs.getInt("InsuredCountSum");
			this.PolCount = rs.getInt("PolCount");
			this.PolCountSum = rs.getInt("PolCountSum");
			this.CurYearAmnt = rs.getDouble("CurYearAmnt");
			this.CurYearAmntSum = rs.getDouble("CurYearAmntSum");
			this.AllYearAmnt = rs.getDouble("AllYearAmnt");
			this.AllYearAmntSum = rs.getDouble("AllYearAmntSum");
			this.CurYearPrem = rs.getDouble("CurYearPrem");
			this.CurYearPremSum = rs.getDouble("CurYearPremSum");
			this.AllYearPrem = rs.getDouble("AllYearPrem");
			this.AllYearPremSum = rs.getDouble("AllYearPremSum");
			this.CurYearInsured = rs.getInt("CurYearInsured");
			this.CurYearInsuredSum = rs.getInt("CurYearInsuredSum");
			this.AllYearInsured = rs.getInt("AllYearInsured");
			this.AllYearInsuredSum = rs.getInt("AllYearInsuredSum");
			this.CurYearPol = rs.getInt("CurYearPol");
			this.CurYearPolSum = rs.getInt("CurYearPolSum");
			this.AllYearPol = rs.getInt("AllYearPol");
			this.AllYearPolSum = rs.getInt("AllYearPolSum");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LFRiskApp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LFRiskAppSchema getSchema()
	{
		LFRiskAppSchema aLFRiskAppSchema = new LFRiskAppSchema();
		aLFRiskAppSchema.setSchema(this);
		return aLFRiskAppSchema;
	}

	public LFRiskAppDB getDB()
	{
		LFRiskAppDB aDBOper = new LFRiskAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFRiskApp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PersonPolFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReportDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AmntSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredCountSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolCountSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearAmntSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearAmntSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearPremSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearPremSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearInsured));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearInsuredSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearInsured));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearInsuredSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearPol));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearPolSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearPol));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AllYearPolSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFRiskApp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FirstPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PersonPolFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ReportDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			AmntSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PremSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			InsuredCountSum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			PolCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			PolCountSum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			CurYearAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			CurYearAmntSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			AllYearAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			AllYearAmntSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			CurYearPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			CurYearPremSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			AllYearPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			AllYearPremSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			CurYearInsured= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			CurYearInsuredSum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			AllYearInsured= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			AllYearInsuredSum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			CurYearPol= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			CurYearPolSum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).intValue();
			AllYearPol= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).intValue();
			AllYearPolSum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("FirstPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstPayFlag));
		}
		if (FCode.equalsIgnoreCase("PersonPolFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PersonPolFlag));
		}
		if (FCode.equalsIgnoreCase("ReportDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReportDate()));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("AmntSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntSum));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("PremSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremSum));
		}
		if (FCode.equalsIgnoreCase("InsuredCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredCount));
		}
		if (FCode.equalsIgnoreCase("InsuredCountSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredCountSum));
		}
		if (FCode.equalsIgnoreCase("PolCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolCount));
		}
		if (FCode.equalsIgnoreCase("PolCountSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolCountSum));
		}
		if (FCode.equalsIgnoreCase("CurYearAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearAmnt));
		}
		if (FCode.equalsIgnoreCase("CurYearAmntSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearAmntSum));
		}
		if (FCode.equalsIgnoreCase("AllYearAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearAmnt));
		}
		if (FCode.equalsIgnoreCase("AllYearAmntSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearAmntSum));
		}
		if (FCode.equalsIgnoreCase("CurYearPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearPrem));
		}
		if (FCode.equalsIgnoreCase("CurYearPremSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearPremSum));
		}
		if (FCode.equalsIgnoreCase("AllYearPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearPrem));
		}
		if (FCode.equalsIgnoreCase("AllYearPremSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearPremSum));
		}
		if (FCode.equalsIgnoreCase("CurYearInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearInsured));
		}
		if (FCode.equalsIgnoreCase("CurYearInsuredSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearInsuredSum));
		}
		if (FCode.equalsIgnoreCase("AllYearInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearInsured));
		}
		if (FCode.equalsIgnoreCase("AllYearInsuredSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearInsuredSum));
		}
		if (FCode.equalsIgnoreCase("CurYearPol"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearPol));
		}
		if (FCode.equalsIgnoreCase("CurYearPolSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearPolSum));
		}
		if (FCode.equalsIgnoreCase("AllYearPol"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearPol));
		}
		if (FCode.equalsIgnoreCase("AllYearPolSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AllYearPolSum));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 2:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FirstPayFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PersonPolFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReportDate()));
				break;
			case 7:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 8:
				strFieldValue = String.valueOf(AmntSum);
				break;
			case 9:
				strFieldValue = String.valueOf(Prem);
				break;
			case 10:
				strFieldValue = String.valueOf(PremSum);
				break;
			case 11:
				strFieldValue = String.valueOf(InsuredCount);
				break;
			case 12:
				strFieldValue = String.valueOf(InsuredCountSum);
				break;
			case 13:
				strFieldValue = String.valueOf(PolCount);
				break;
			case 14:
				strFieldValue = String.valueOf(PolCountSum);
				break;
			case 15:
				strFieldValue = String.valueOf(CurYearAmnt);
				break;
			case 16:
				strFieldValue = String.valueOf(CurYearAmntSum);
				break;
			case 17:
				strFieldValue = String.valueOf(AllYearAmnt);
				break;
			case 18:
				strFieldValue = String.valueOf(AllYearAmntSum);
				break;
			case 19:
				strFieldValue = String.valueOf(CurYearPrem);
				break;
			case 20:
				strFieldValue = String.valueOf(CurYearPremSum);
				break;
			case 21:
				strFieldValue = String.valueOf(AllYearPrem);
				break;
			case 22:
				strFieldValue = String.valueOf(AllYearPremSum);
				break;
			case 23:
				strFieldValue = String.valueOf(CurYearInsured);
				break;
			case 24:
				strFieldValue = String.valueOf(CurYearInsuredSum);
				break;
			case 25:
				strFieldValue = String.valueOf(AllYearInsured);
				break;
			case 26:
				strFieldValue = String.valueOf(AllYearInsuredSum);
				break;
			case 27:
				strFieldValue = String.valueOf(CurYearPol);
				break;
			case 28:
				strFieldValue = String.valueOf(CurYearPolSum);
				break;
			case 29:
				strFieldValue = String.valueOf(AllYearPol);
				break;
			case 30:
				strFieldValue = String.valueOf(AllYearPolSum);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
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
		if (FCode.equalsIgnoreCase("FirstPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstPayFlag = FValue.trim();
			}
			else
				FirstPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("PersonPolFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PersonPolFlag = FValue.trim();
			}
			else
				PersonPolFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReportDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReportDate = fDate.getDate( FValue );
			}
			else
				ReportDate = null;
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
		if (FCode.equalsIgnoreCase("AmntSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AmntSum = d;
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
		if (FCode.equalsIgnoreCase("PremSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredCountSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredCountSum = i;
			}
		}
		if (FCode.equalsIgnoreCase("PolCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PolCountSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolCountSum = i;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurYearAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearAmntSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurYearAmntSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AllYearAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearAmntSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AllYearAmntSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurYearPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearPremSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurYearPremSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AllYearPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearPremSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AllYearPremSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurYearInsured = i;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearInsuredSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurYearInsuredSum = i;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AllYearInsured = i;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearInsuredSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AllYearInsuredSum = i;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearPol"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurYearPol = i;
			}
		}
		if (FCode.equalsIgnoreCase("CurYearPolSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurYearPolSum = i;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearPol"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AllYearPol = i;
			}
		}
		if (FCode.equalsIgnoreCase("AllYearPolSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AllYearPolSum = i;
			}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFRiskAppSchema other = (LFRiskAppSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ManageCom.equals(other.getManageCom())
			&& PayIntv == other.getPayIntv()
			&& SaleChnl.equals(other.getSaleChnl())
			&& FirstPayFlag.equals(other.getFirstPayFlag())
			&& PersonPolFlag.equals(other.getPersonPolFlag())
			&& fDate.getString(ReportDate).equals(other.getReportDate())
			&& Amnt == other.getAmnt()
			&& AmntSum == other.getAmntSum()
			&& Prem == other.getPrem()
			&& PremSum == other.getPremSum()
			&& InsuredCount == other.getInsuredCount()
			&& InsuredCountSum == other.getInsuredCountSum()
			&& PolCount == other.getPolCount()
			&& PolCountSum == other.getPolCountSum()
			&& CurYearAmnt == other.getCurYearAmnt()
			&& CurYearAmntSum == other.getCurYearAmntSum()
			&& AllYearAmnt == other.getAllYearAmnt()
			&& AllYearAmntSum == other.getAllYearAmntSum()
			&& CurYearPrem == other.getCurYearPrem()
			&& CurYearPremSum == other.getCurYearPremSum()
			&& AllYearPrem == other.getAllYearPrem()
			&& AllYearPremSum == other.getAllYearPremSum()
			&& CurYearInsured == other.getCurYearInsured()
			&& CurYearInsuredSum == other.getCurYearInsuredSum()
			&& AllYearInsured == other.getAllYearInsured()
			&& AllYearInsuredSum == other.getAllYearInsuredSum()
			&& CurYearPol == other.getCurYearPol()
			&& CurYearPolSum == other.getCurYearPolSum()
			&& AllYearPol == other.getAllYearPol()
			&& AllYearPolSum == other.getAllYearPolSum()
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 1;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 2;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 3;
		}
		if( strFieldName.equals("FirstPayFlag") ) {
			return 4;
		}
		if( strFieldName.equals("PersonPolFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ReportDate") ) {
			return 6;
		}
		if( strFieldName.equals("Amnt") ) {
			return 7;
		}
		if( strFieldName.equals("AmntSum") ) {
			return 8;
		}
		if( strFieldName.equals("Prem") ) {
			return 9;
		}
		if( strFieldName.equals("PremSum") ) {
			return 10;
		}
		if( strFieldName.equals("InsuredCount") ) {
			return 11;
		}
		if( strFieldName.equals("InsuredCountSum") ) {
			return 12;
		}
		if( strFieldName.equals("PolCount") ) {
			return 13;
		}
		if( strFieldName.equals("PolCountSum") ) {
			return 14;
		}
		if( strFieldName.equals("CurYearAmnt") ) {
			return 15;
		}
		if( strFieldName.equals("CurYearAmntSum") ) {
			return 16;
		}
		if( strFieldName.equals("AllYearAmnt") ) {
			return 17;
		}
		if( strFieldName.equals("AllYearAmntSum") ) {
			return 18;
		}
		if( strFieldName.equals("CurYearPrem") ) {
			return 19;
		}
		if( strFieldName.equals("CurYearPremSum") ) {
			return 20;
		}
		if( strFieldName.equals("AllYearPrem") ) {
			return 21;
		}
		if( strFieldName.equals("AllYearPremSum") ) {
			return 22;
		}
		if( strFieldName.equals("CurYearInsured") ) {
			return 23;
		}
		if( strFieldName.equals("CurYearInsuredSum") ) {
			return 24;
		}
		if( strFieldName.equals("AllYearInsured") ) {
			return 25;
		}
		if( strFieldName.equals("AllYearInsuredSum") ) {
			return 26;
		}
		if( strFieldName.equals("CurYearPol") ) {
			return 27;
		}
		if( strFieldName.equals("CurYearPolSum") ) {
			return 28;
		}
		if( strFieldName.equals("AllYearPol") ) {
			return 29;
		}
		if( strFieldName.equals("AllYearPolSum") ) {
			return 30;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 31;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 32;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "ManageCom";
				break;
			case 2:
				strFieldName = "PayIntv";
				break;
			case 3:
				strFieldName = "SaleChnl";
				break;
			case 4:
				strFieldName = "FirstPayFlag";
				break;
			case 5:
				strFieldName = "PersonPolFlag";
				break;
			case 6:
				strFieldName = "ReportDate";
				break;
			case 7:
				strFieldName = "Amnt";
				break;
			case 8:
				strFieldName = "AmntSum";
				break;
			case 9:
				strFieldName = "Prem";
				break;
			case 10:
				strFieldName = "PremSum";
				break;
			case 11:
				strFieldName = "InsuredCount";
				break;
			case 12:
				strFieldName = "InsuredCountSum";
				break;
			case 13:
				strFieldName = "PolCount";
				break;
			case 14:
				strFieldName = "PolCountSum";
				break;
			case 15:
				strFieldName = "CurYearAmnt";
				break;
			case 16:
				strFieldName = "CurYearAmntSum";
				break;
			case 17:
				strFieldName = "AllYearAmnt";
				break;
			case 18:
				strFieldName = "AllYearAmntSum";
				break;
			case 19:
				strFieldName = "CurYearPrem";
				break;
			case 20:
				strFieldName = "CurYearPremSum";
				break;
			case 21:
				strFieldName = "AllYearPrem";
				break;
			case 22:
				strFieldName = "AllYearPremSum";
				break;
			case 23:
				strFieldName = "CurYearInsured";
				break;
			case 24:
				strFieldName = "CurYearInsuredSum";
				break;
			case 25:
				strFieldName = "AllYearInsured";
				break;
			case 26:
				strFieldName = "AllYearInsuredSum";
				break;
			case 27:
				strFieldName = "CurYearPol";
				break;
			case 28:
				strFieldName = "CurYearPolSum";
				break;
			case 29:
				strFieldName = "AllYearPol";
				break;
			case 30:
				strFieldName = "AllYearPolSum";
				break;
			case 31:
				strFieldName = "MakeDate";
				break;
			case 32:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PersonPolFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AmntSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredCountSum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolCountSum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CurYearAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurYearAmntSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AllYearAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AllYearAmntSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurYearPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurYearPremSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AllYearPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AllYearPremSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurYearInsured") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CurYearInsuredSum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AllYearInsured") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AllYearInsuredSum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CurYearPol") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CurYearPolSum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AllYearPol") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AllYearPolSum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_INT;
				break;
			case 29:
				nFieldType = Schema.TYPE_INT;
				break;
			case 30:
				nFieldType = Schema.TYPE_INT;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
