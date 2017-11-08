

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.PD_LMDutyDB;

/*
 * <p>ClassName: PD_LMDutySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class PD_LMDutySchema implements Schema, Cloneable
{
	// @Field
	/** 责任代码 */
	private String DutyCode;
	/** 责任名称 */
	private String DutyName;
	/** 缴费终止期间 */
	private int PayEndYear;
	/** 缴费终止期间单位 */
	private String PayEndYearFlag;
	/** 缴费终止日期计算参照 */
	private String PayEndDateCalRef;
	/** 缴费终止日期计算方式 */
	private String PayEndDateCalMode;
	/** 起领期间 */
	private int GetYear;
	/** 起领期间单位 */
	private String GetYearFlag;
	/** 保险期间 */
	private int InsuYear;
	/** 保险期间单位 */
	private String InsuYearFlag;
	/** 意外责任期间 */
	private int AcciYear;
	/** 意外责任期间单位 */
	private String AcciYearFlag;
	/** 计算方法 */
	private String CalMode;
	/** 缴费终止期间关系 */
	private String PayEndYearRela;
	/** 起领期间关系 */
	private String GetYearRela;
	/** 保险期间关系 */
	private String InsuYearRela;
	/** 基本保额算法 */
	private String BasicCalCode;
	/** 风险保额算法 */
	private String RiskCalCode;
	/** 保额标记 */
	private String AmntFlag;
	/** 单位保额 */
	private double VPU;
	/** 加费类型 */
	private String AddFeeFlag;
	/** 保险终止日期计算方式 */
	private String EndDateCalMode;
	/** 是否加入保额标记 */
	private String AddAmntFlag;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;
	/** 建议书计算方法 */
	private String PCalMode;

	public static final int FIELDNUM = 35;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "DutyCode";

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
		PD_LMDutySchema cloned = (PD_LMDutySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getDutyName()
	{
		return DutyName;
	}
	public void setDutyName(String aDutyName)
	{
		DutyName = aDutyName;
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
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getPayEndDateCalRef()
	{
		return PayEndDateCalRef;
	}
	public void setPayEndDateCalRef(String aPayEndDateCalRef)
	{
		PayEndDateCalRef = aPayEndDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getPayEndDateCalMode()
	{
		return PayEndDateCalMode;
	}
	public void setPayEndDateCalMode(String aPayEndDateCalMode)
	{
		PayEndDateCalMode = aPayEndDateCalMode;
	}
	public int getGetYear()
	{
		return GetYear;
	}
	public void setGetYear(int aGetYear)
	{
		GetYear = aGetYear;
	}
	public void setGetYear(String aGetYear)
	{
		if (aGetYear != null && !aGetYear.equals(""))
		{
			Integer tInteger = new Integer(aGetYear);
			int i = tInteger.intValue();
			GetYear = i;
		}
	}

	/**
	* M--年、Y--月、D--日、A--年龄
	*/
	public String getGetYearFlag()
	{
		return GetYearFlag;
	}
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
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
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	public int getAcciYear()
	{
		return AcciYear;
	}
	public void setAcciYear(int aAcciYear)
	{
		AcciYear = aAcciYear;
	}
	public void setAcciYear(String aAcciYear)
	{
		if (aAcciYear != null && !aAcciYear.equals(""))
		{
			Integer tInteger = new Integer(aAcciYear);
			int i = tInteger.intValue();
			AcciYear = i;
		}
	}

	/**
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getAcciYearFlag()
	{
		return AcciYearFlag;
	}
	public void setAcciYearFlag(String aAcciYearFlag)
	{
		AcciYearFlag = aAcciYearFlag;
	}
	/**
	* /** 0 协议类 (保费保额均输入) 1 保费算领取.(与 je_pay 中的 bm_cal 相对应） 2 份数算保费.(与 kind_spec 中的 bm_cal 相对应） 3 领取反算保费.(与 je_pay 中的 bm_cal1 相对应) 4 养老类正反算 (例如：提供一保额计算保费，再从保费计算其他保额) 5 保费对应交费计划 (填保费:auto_plan;不填保费:inp_plan) **/
	 /**
	 *P 保费算保额 A 保费保额互算 G 保额算保费 O 其他因素算保费保额 I 录入保费保额
	*/
	public String getCalMode()
	{
		return CalMode;
	}
	public void setCalMode(String aCalMode)
	{
		CalMode = aCalMode;
	}
	/**
	* 缴费和领取、保险年期之间的关系。 注意：该字段必须描述 0 －－ 表示需要录入 1 －－ 和缴费终止期间的值相同 2 －－ 和起领期间的值相同 3 －－ 和保险期间的值相同 4 －－ 从描述表取其默认值 5 －－ 更据算法，将缴费终止期间和默认值相加 6 －－ 更据算法，将起领期间和默认值相加 7 －－ 更据算法，将保险期间和默认值相加
	*/
	public String getPayEndYearRela()
	{
		return PayEndYearRela;
	}
	public void setPayEndYearRela(String aPayEndYearRela)
	{
		PayEndYearRela = aPayEndYearRela;
	}
	/**
	* 缴费和领取、保险年期之间的关系。 注意：该字段必须描述 0 －－ 表示需要录入 1 －－ 和缴费终止期间的值相同 2 －－ 和起领期间的值相同 3 －－ 和保险期间的值相同 4 －－ 从描述表取其默认值 5 －－ 更据算法，将缴费终止期间和默认值相加 6 －－ 更据算法，将起领期间和默认值相加 7 －－ 更据算法，将保险期间和默认值相加
	*/
	public String getGetYearRela()
	{
		return GetYearRela;
	}
	public void setGetYearRela(String aGetYearRela)
	{
		GetYearRela = aGetYearRela;
	}
	/**
	* 缴费和领取、保险年期之间的关系。 注意：该字段必须描述 0 －－ 表示需要录入 1 －－ 和缴费终止期间的值相同 2 －－ 和起领期间的值相同 3 －－ 和保险期间的值相同 4 －－ 从描述表取其默认值 5 －－ 更据算法，将缴费终止期间和默认值相加 6 －－ 更据算法，将起领期间和默认值相加 7 －－ 更据算法，将保险期间和默认值相加
	*/
	public String getInsuYearRela()
	{
		return InsuYearRela;
	}
	public void setInsuYearRela(String aInsuYearRela)
	{
		InsuYearRela = aInsuYearRela;
	}
	public String getBasicCalCode()
	{
		return BasicCalCode;
	}
	public void setBasicCalCode(String aBasicCalCode)
	{
		BasicCalCode = aBasicCalCode;
	}
	public String getRiskCalCode()
	{
		return RiskCalCode;
	}
	public void setRiskCalCode(String aRiskCalCode)
	{
		RiskCalCode = aRiskCalCode;
	}
	/**
	* 1.按保额销售 2.按份数销售 3.按档次销售 该字段用来控制保全加保,减保时的控制,按保额销售的减保额,按份数卖的减份数
	*/
	public String getAmntFlag()
	{
		return AmntFlag;
	}
	public void setAmntFlag(String aAmntFlag)
	{
		AmntFlag = aAmntFlag;
	}
	/**
	* 险种单位保额 用处:在险种描述算法中可以用?VPU?作为单位保额使用
	*/
	public double getVPU()
	{
		return VPU;
	}
	public void setVPU(double aVPU)
	{
		VPU = aVPU;
	}
	public void setVPU(String aVPU)
	{
		if (aVPU != null && !aVPU.equals(""))
		{
			Double tDouble = new Double(aVPU);
			double d = tDouble.doubleValue();
			VPU = d;
		}
	}

	/**
	* 00--没有加费 10--有投保人健康加费 20--有投保人职业加费 等 第一位表示是否有投保人加费(0-无,1-健康加费,2-职业加费,3-两种加费都有) 第二位表示是否有被保人加费(0-无,1-健康加费,2-职业加费,3-两种加费都有)
	*/
	public String getAddFeeFlag()
	{
		return AddFeeFlag;
	}
	public void setAddFeeFlag(String aAddFeeFlag)
	{
		AddFeeFlag = aAddFeeFlag;
	}
	public String getEndDateCalMode()
	{
		return EndDateCalMode;
	}
	public void setEndDateCalMode(String aEndDateCalMode)
	{
		EndDateCalMode = aEndDateCalMode;
	}
	public String getAddAmntFlag()
	{
		return AddAmntFlag;
	}
	public void setAddAmntFlag(String aAddAmntFlag)
	{
		AddAmntFlag = aAddAmntFlag;
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}

	public String getPCalMode()
	{
		return PCalMode;
	}
	public void setPCalMode(String aPCalMode)
	{
		PCalMode = aPCalMode;
	}

	/**
	* 使用另外一个 PD_LMDutySchema 对象给 Schema 赋值
	* @param: aPD_LMDutySchema PD_LMDutySchema
	**/
	public void setSchema(PD_LMDutySchema aPD_LMDutySchema)
	{
		this.DutyCode = aPD_LMDutySchema.getDutyCode();
		this.DutyName = aPD_LMDutySchema.getDutyName();
		this.PayEndYear = aPD_LMDutySchema.getPayEndYear();
		this.PayEndYearFlag = aPD_LMDutySchema.getPayEndYearFlag();
		this.PayEndDateCalRef = aPD_LMDutySchema.getPayEndDateCalRef();
		this.PayEndDateCalMode = aPD_LMDutySchema.getPayEndDateCalMode();
		this.GetYear = aPD_LMDutySchema.getGetYear();
		this.GetYearFlag = aPD_LMDutySchema.getGetYearFlag();
		this.InsuYear = aPD_LMDutySchema.getInsuYear();
		this.InsuYearFlag = aPD_LMDutySchema.getInsuYearFlag();
		this.AcciYear = aPD_LMDutySchema.getAcciYear();
		this.AcciYearFlag = aPD_LMDutySchema.getAcciYearFlag();
		this.CalMode = aPD_LMDutySchema.getCalMode();
		this.PayEndYearRela = aPD_LMDutySchema.getPayEndYearRela();
		this.GetYearRela = aPD_LMDutySchema.getGetYearRela();
		this.InsuYearRela = aPD_LMDutySchema.getInsuYearRela();
		this.BasicCalCode = aPD_LMDutySchema.getBasicCalCode();
		this.RiskCalCode = aPD_LMDutySchema.getRiskCalCode();
		this.AmntFlag = aPD_LMDutySchema.getAmntFlag();
		this.VPU = aPD_LMDutySchema.getVPU();
		this.AddFeeFlag = aPD_LMDutySchema.getAddFeeFlag();
		this.EndDateCalMode = aPD_LMDutySchema.getEndDateCalMode();
		this.AddAmntFlag = aPD_LMDutySchema.getAddAmntFlag();
		this.Operator = aPD_LMDutySchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutySchema.getMakeDate());
		this.MakeTime = aPD_LMDutySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutySchema.getModifyDate());
		this.ModifyTime = aPD_LMDutySchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutySchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutySchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutySchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutySchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutySchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutySchema.getStandbyflag6();
		this.PCalMode = aPD_LMDutySchema.getPCalMode();
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
			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("DutyName") == null )
				this.DutyName = null;
			else
				this.DutyName = rs.getString("DutyName").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			if( rs.getString("PayEndDateCalRef") == null )
				this.PayEndDateCalRef = null;
			else
				this.PayEndDateCalRef = rs.getString("PayEndDateCalRef").trim();

			if( rs.getString("PayEndDateCalMode") == null )
				this.PayEndDateCalMode = null;
			else
				this.PayEndDateCalMode = rs.getString("PayEndDateCalMode").trim();

			this.GetYear = rs.getInt("GetYear");
			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.AcciYear = rs.getInt("AcciYear");
			if( rs.getString("AcciYearFlag") == null )
				this.AcciYearFlag = null;
			else
				this.AcciYearFlag = rs.getString("AcciYearFlag").trim();

			if( rs.getString("CalMode") == null )
				this.CalMode = null;
			else
				this.CalMode = rs.getString("CalMode").trim();

			if( rs.getString("PayEndYearRela") == null )
				this.PayEndYearRela = null;
			else
				this.PayEndYearRela = rs.getString("PayEndYearRela").trim();

			if( rs.getString("GetYearRela") == null )
				this.GetYearRela = null;
			else
				this.GetYearRela = rs.getString("GetYearRela").trim();

			if( rs.getString("InsuYearRela") == null )
				this.InsuYearRela = null;
			else
				this.InsuYearRela = rs.getString("InsuYearRela").trim();

			if( rs.getString("BasicCalCode") == null )
				this.BasicCalCode = null;
			else
				this.BasicCalCode = rs.getString("BasicCalCode").trim();

			if( rs.getString("RiskCalCode") == null )
				this.RiskCalCode = null;
			else
				this.RiskCalCode = rs.getString("RiskCalCode").trim();

			if( rs.getString("AmntFlag") == null )
				this.AmntFlag = null;
			else
				this.AmntFlag = rs.getString("AmntFlag").trim();

			this.VPU = rs.getDouble("VPU");
			if( rs.getString("AddFeeFlag") == null )
				this.AddFeeFlag = null;
			else
				this.AddFeeFlag = rs.getString("AddFeeFlag").trim();

			if( rs.getString("EndDateCalMode") == null )
				this.EndDateCalMode = null;
			else
				this.EndDateCalMode = rs.getString("EndDateCalMode").trim();

			if( rs.getString("AddAmntFlag") == null )
				this.AddAmntFlag = null;
			else
				this.AddAmntFlag = rs.getString("AddAmntFlag").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
			if( rs.getString("PCalMode") == null )
				this.PCalMode = null;
			else
				this.PCalMode = rs.getString("PCalMode").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMDuty表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutySchema getSchema()
	{
		PD_LMDutySchema aPD_LMDutySchema = new PD_LMDutySchema();
		aPD_LMDutySchema.setSchema(this);
		return aPD_LMDutySchema;
	}

	public PD_LMDutyDB getDB()
	{
		PD_LMDutyDB aDBOper = new PD_LMDutyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDuty描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AcciYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearRela)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearRela)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearRela)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BasicCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(VPU));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddAmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PCalMode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDuty>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayEndDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayEndDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AcciYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			AcciYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PayEndYearRela = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			GetYearRela = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			InsuYearRela = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BasicCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			VPU = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			AddFeeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			EndDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AddAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			PCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutySchema";
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("DutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyName));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDateCalRef));
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("AcciYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYear));
		}
		if (FCode.equalsIgnoreCase("AcciYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYearFlag));
		}
		if (FCode.equalsIgnoreCase("CalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalMode));
		}
		if (FCode.equalsIgnoreCase("PayEndYearRela"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearRela));
		}
		if (FCode.equalsIgnoreCase("GetYearRela"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearRela));
		}
		if (FCode.equalsIgnoreCase("InsuYearRela"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearRela));
		}
		if (FCode.equalsIgnoreCase("BasicCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BasicCalCode));
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalCode));
		}
		if (FCode.equalsIgnoreCase("AmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntFlag));
		}
		if (FCode.equalsIgnoreCase("VPU"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VPU));
		}
		if (FCode.equalsIgnoreCase("AddFeeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeFlag));
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("AddAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddAmntFlag));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
		}
		if (FCode.equalsIgnoreCase("PCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PCalMode));
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
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DutyName);
				break;
			case 2:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayEndDateCalRef);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PayEndDateCalMode);
				break;
			case 6:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 10:
				strFieldValue = String.valueOf(AcciYear);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AcciYearFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CalMode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearRela);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GetYearRela);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(InsuYearRela);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BasicCalCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AmntFlag);
				break;
			case 19:
				strFieldValue = String.valueOf(VPU);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AddFeeFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(EndDateCalMode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AddAmntFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 30:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 31:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 32:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 33:
				strFieldValue = String.valueOf(Standbyflag6);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(PCalMode);
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

		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyName = FValue.trim();
			}
			else
				DutyName = null;
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
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDateCalRef = FValue.trim();
			}
			else
				PayEndDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDateCalMode = FValue.trim();
			}
			else
				PayEndDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetYear = i;
			}
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
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
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
		if (FCode.equalsIgnoreCase("AcciYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AcciYear = i;
			}
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
		if (FCode.equalsIgnoreCase("CalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalMode = FValue.trim();
			}
			else
				CalMode = null;
		}
		if (FCode.equalsIgnoreCase("PayEndYearRela"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearRela = FValue.trim();
			}
			else
				PayEndYearRela = null;
		}
		if (FCode.equalsIgnoreCase("GetYearRela"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetYearRela = FValue.trim();
			}
			else
				GetYearRela = null;
		}
		if (FCode.equalsIgnoreCase("InsuYearRela"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearRela = FValue.trim();
			}
			else
				InsuYearRela = null;
		}
		if (FCode.equalsIgnoreCase("BasicCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BasicCalCode = FValue.trim();
			}
			else
				BasicCalCode = null;
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
		if (FCode.equalsIgnoreCase("AmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmntFlag = FValue.trim();
			}
			else
				AmntFlag = null;
		}
		if (FCode.equalsIgnoreCase("VPU"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				VPU = d;
			}
		}
		if (FCode.equalsIgnoreCase("AddFeeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeFlag = FValue.trim();
			}
			else
				AddFeeFlag = null;
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDateCalMode = FValue.trim();
			}
			else
				EndDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("AddAmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddAmntFlag = FValue.trim();
			}
			else
				AddAmntFlag = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("PCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PCalMode = FValue.trim();
			}
			else
				PCalMode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMDutySchema other = (PD_LMDutySchema)otherObject;
		return
			DutyCode.equals(other.getDutyCode())
			&& DutyName.equals(other.getDutyName())
			&& PayEndYear == other.getPayEndYear()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndDateCalRef.equals(other.getPayEndDateCalRef())
			&& PayEndDateCalMode.equals(other.getPayEndDateCalMode())
			&& GetYear == other.getGetYear()
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& InsuYear == other.getInsuYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& AcciYear == other.getAcciYear()
			&& AcciYearFlag.equals(other.getAcciYearFlag())
			&& CalMode.equals(other.getCalMode())
			&& PayEndYearRela.equals(other.getPayEndYearRela())
			&& GetYearRela.equals(other.getGetYearRela())
			&& InsuYearRela.equals(other.getInsuYearRela())
			&& BasicCalCode.equals(other.getBasicCalCode())
			&& RiskCalCode.equals(other.getRiskCalCode())
			&& AmntFlag.equals(other.getAmntFlag())
			&& VPU == other.getVPU()
			&& AddFeeFlag.equals(other.getAddFeeFlag())
			&& EndDateCalMode.equals(other.getEndDateCalMode())
			&& AddAmntFlag.equals(other.getAddAmntFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6()
			&& PCalMode.equals(other.getPCalMode());
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
		if( strFieldName.equals("DutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("DutyName") ) {
			return 1;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 2;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 3;
		}
		if( strFieldName.equals("PayEndDateCalRef") ) {
			return 4;
		}
		if( strFieldName.equals("PayEndDateCalMode") ) {
			return 5;
		}
		if( strFieldName.equals("GetYear") ) {
			return 6;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 7;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 8;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 9;
		}
		if( strFieldName.equals("AcciYear") ) {
			return 10;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return 11;
		}
		if( strFieldName.equals("CalMode") ) {
			return 12;
		}
		if( strFieldName.equals("PayEndYearRela") ) {
			return 13;
		}
		if( strFieldName.equals("GetYearRela") ) {
			return 14;
		}
		if( strFieldName.equals("InsuYearRela") ) {
			return 15;
		}
		if( strFieldName.equals("BasicCalCode") ) {
			return 16;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 17;
		}
		if( strFieldName.equals("AmntFlag") ) {
			return 18;
		}
		if( strFieldName.equals("VPU") ) {
			return 19;
		}
		if( strFieldName.equals("AddFeeFlag") ) {
			return 20;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return 21;
		}
		if( strFieldName.equals("AddAmntFlag") ) {
			return 22;
		}
		if( strFieldName.equals("Operator") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 28;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 29;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 30;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 31;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 32;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 33;
		}
		if( strFieldName.equals("PCalMode") ) {
			return 34;
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
				strFieldName = "DutyCode";
				break;
			case 1:
				strFieldName = "DutyName";
				break;
			case 2:
				strFieldName = "PayEndYear";
				break;
			case 3:
				strFieldName = "PayEndYearFlag";
				break;
			case 4:
				strFieldName = "PayEndDateCalRef";
				break;
			case 5:
				strFieldName = "PayEndDateCalMode";
				break;
			case 6:
				strFieldName = "GetYear";
				break;
			case 7:
				strFieldName = "GetYearFlag";
				break;
			case 8:
				strFieldName = "InsuYear";
				break;
			case 9:
				strFieldName = "InsuYearFlag";
				break;
			case 10:
				strFieldName = "AcciYear";
				break;
			case 11:
				strFieldName = "AcciYearFlag";
				break;
			case 12:
				strFieldName = "CalMode";
				break;
			case 13:
				strFieldName = "PayEndYearRela";
				break;
			case 14:
				strFieldName = "GetYearRela";
				break;
			case 15:
				strFieldName = "InsuYearRela";
				break;
			case 16:
				strFieldName = "BasicCalCode";
				break;
			case 17:
				strFieldName = "RiskCalCode";
				break;
			case 18:
				strFieldName = "AmntFlag";
				break;
			case 19:
				strFieldName = "VPU";
				break;
			case 20:
				strFieldName = "AddFeeFlag";
				break;
			case 21:
				strFieldName = "EndDateCalMode";
				break;
			case 22:
				strFieldName = "AddAmntFlag";
				break;
			case 23:
				strFieldName = "Operator";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
				strFieldName = "ModifyTime";
				break;
			case 28:
				strFieldName = "Standbyflag1";
				break;
			case 29:
				strFieldName = "Standbyflag2";
				break;
			case 30:
				strFieldName = "Standbyflag3";
				break;
			case 31:
				strFieldName = "Standbyflag4";
				break;
			case 32:
				strFieldName = "Standbyflag5";
				break;
			case 33:
				strFieldName = "Standbyflag6";
				break;
			case 34:
				strFieldName = "PCalMode";
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
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYearRela") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYearRela") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYearRela") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BasicCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VPU") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddFeeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddAmntFlag") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PCalMode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 31:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
