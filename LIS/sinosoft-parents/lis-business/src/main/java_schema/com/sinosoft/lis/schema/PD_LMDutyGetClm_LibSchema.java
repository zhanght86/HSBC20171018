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
import com.sinosoft.lis.db.PD_LMDutyGetClm_LibDB;

/*
 * <p>ClassName: PD_LMDutyGetClm_LibSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyGetClm_LibSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMDutyGetClm_LibSchema.class);

	// @Field
	/** 责任给付赔付库代码 */
	private String GetDutyCode2;
	/** 算法编码 */
	private String CalCode;
	/** 责任给付生存库名称 */
	private String GetDutyName;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 默认值 */
	private double DefaultVal;
	/** 反算算法 */
	private String CnterCalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 录入标志 */
	private String InpFlag;
	/** 统计类别 */
	private String StatType;
	/** 起付限 */
	private double MinGet;
	/** 给付后动作 */
	private String AfterGet;
	/** 赔付限额 */
	private double MaxGet;
	/** 赔付比例 */
	private double ClaimRate;
	/** 赔付天数限额 */
	private int ClmDayLmt;
	/** 累计赔付天数限额 */
	private int SumClmDayLmt;
	/** 免赔额 */
	private double Deductible;
	/** 免赔天数 */
	private int DeDuctDay;
	/** 观察期 */
	private int ObsPeriod;
	/** 被保人死亡后有效标记 */
	private String DeadValiFlag;
	/** 死亡给付与现值关系 */
	private String DeadToPValueFlag;
	/** 领取时是否需要重新计算 */
	private String NeedReCompute;
	/** 给付类型 */
	private String CasePolType;
	/** 伤残级别 */
	private String DeformityGrade;
	/** 责任匹配算法 */
	private String FilterCalCode;
	/** 赔付影响主险类型 */
	private String EffectOnMainRisk;
	/** 额外保障标记 */
	private String ExtraAmntFlag;
	/** 年度免赔额标记 */
	private String YearGetLimitFlag;
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

	public static final int FIELDNUM = 38;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyGetClm_LibSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GetDutyCode2";

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
		PD_LMDutyGetClm_LibSchema cloned = (PD_LMDutyGetClm_LibSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetDutyCode2()
	{
		return GetDutyCode2;
	}
	public void setGetDutyCode2(String aGetDutyCode2)
	{
		GetDutyCode2 = aGetDutyCode2;
	}
	/**
	* 1-3位：险种号码 4位：业务类型（该位不一定按照此规则来编写，现在赔付都是3） 1 承保 2 领取 3 赔付 4 现金价值 5 单证描述 5-6位 顺序号
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public double getDefaultVal()
	{
		return DefaultVal;
	}
	public void setDefaultVal(double aDefaultVal)
	{
		DefaultVal = aDefaultVal;
	}
	public void setDefaultVal(String aDefaultVal)
	{
		if (aDefaultVal != null && !aDefaultVal.equals(""))
		{
			Double tDouble = new Double(aDefaultVal);
			double d = tDouble.doubleValue();
			DefaultVal = d;
		}
	}

	public String getCnterCalCode()
	{
		return CnterCalCode;
	}
	public void setCnterCalCode(String aCnterCalCode)
	{
		CnterCalCode = aCnterCalCode;
	}
	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		OthCalCode = aOthCalCode;
	}
	/**
	* Y -- 录入、N -- 不录入
	*/
	public String getInpFlag()
	{
		return InpFlag;
	}
	public void setInpFlag(String aInpFlag)
	{
		InpFlag = aInpFlag;
	}
	/**
	* #yl 医疗 #sc 伤残#sw 死亡#tb 返还金
	*/
	public String getStatType()
	{
		return StatType;
	}
	public void setStatType(String aStatType)
	{
		StatType = aStatType;
	}
	public double getMinGet()
	{
		return MinGet;
	}
	public void setMinGet(double aMinGet)
	{
		MinGet = aMinGet;
	}
	public void setMinGet(String aMinGet)
	{
		if (aMinGet != null && !aMinGet.equals(""))
		{
			Double tDouble = new Double(aMinGet);
			double d = tDouble.doubleValue();
			MinGet = d;
		}
	}

	/**
	* #"000" 无动作# "001" 保额递减（暂不用）#"002" 保额递增（暂不用）# "003" 无条件销户 # "004" 最后一次给付销户
	*/
	public String getAfterGet()
	{
		return AfterGet;
	}
	public void setAfterGet(String aAfterGet)
	{
		AfterGet = aAfterGet;
	}
	public double getMaxGet()
	{
		return MaxGet;
	}
	public void setMaxGet(double aMaxGet)
	{
		MaxGet = aMaxGet;
	}
	public void setMaxGet(String aMaxGet)
	{
		if (aMaxGet != null && !aMaxGet.equals(""))
		{
			Double tDouble = new Double(aMaxGet);
			double d = tDouble.doubleValue();
			MaxGet = d;
		}
	}

	public double getClaimRate()
	{
		return ClaimRate;
	}
	public void setClaimRate(double aClaimRate)
	{
		ClaimRate = aClaimRate;
	}
	public void setClaimRate(String aClaimRate)
	{
		if (aClaimRate != null && !aClaimRate.equals(""))
		{
			Double tDouble = new Double(aClaimRate);
			double d = tDouble.doubleValue();
			ClaimRate = d;
		}
	}

	public int getClmDayLmt()
	{
		return ClmDayLmt;
	}
	public void setClmDayLmt(int aClmDayLmt)
	{
		ClmDayLmt = aClmDayLmt;
	}
	public void setClmDayLmt(String aClmDayLmt)
	{
		if (aClmDayLmt != null && !aClmDayLmt.equals(""))
		{
			Integer tInteger = new Integer(aClmDayLmt);
			int i = tInteger.intValue();
			ClmDayLmt = i;
		}
	}

	public int getSumClmDayLmt()
	{
		return SumClmDayLmt;
	}
	public void setSumClmDayLmt(int aSumClmDayLmt)
	{
		SumClmDayLmt = aSumClmDayLmt;
	}
	public void setSumClmDayLmt(String aSumClmDayLmt)
	{
		if (aSumClmDayLmt != null && !aSumClmDayLmt.equals(""))
		{
			Integer tInteger = new Integer(aSumClmDayLmt);
			int i = tInteger.intValue();
			SumClmDayLmt = i;
		}
	}

	public double getDeductible()
	{
		return Deductible;
	}
	public void setDeductible(double aDeductible)
	{
		Deductible = aDeductible;
	}
	public void setDeductible(String aDeductible)
	{
		if (aDeductible != null && !aDeductible.equals(""))
		{
			Double tDouble = new Double(aDeductible);
			double d = tDouble.doubleValue();
			Deductible = d;
		}
	}

	public int getDeDuctDay()
	{
		return DeDuctDay;
	}
	public void setDeDuctDay(int aDeDuctDay)
	{
		DeDuctDay = aDeDuctDay;
	}
	public void setDeDuctDay(String aDeDuctDay)
	{
		if (aDeDuctDay != null && !aDeDuctDay.equals(""))
		{
			Integer tInteger = new Integer(aDeDuctDay);
			int i = tInteger.intValue();
			DeDuctDay = i;
		}
	}

	public int getObsPeriod()
	{
		return ObsPeriod;
	}
	public void setObsPeriod(int aObsPeriod)
	{
		ObsPeriod = aObsPeriod;
	}
	public void setObsPeriod(String aObsPeriod)
	{
		if (aObsPeriod != null && !aObsPeriod.equals(""))
		{
			Integer tInteger = new Integer(aObsPeriod);
			int i = tInteger.intValue();
			ObsPeriod = i;
		}
	}

	/**
	* Y--有效、N--无效
	*/
	public String getDeadValiFlag()
	{
		return DeadValiFlag;
	}
	public void setDeadValiFlag(String aDeadValiFlag)
	{
		DeadValiFlag = aDeadValiFlag;
	}
	/**
	* N--无关 Y--有关
	*/
	public String getDeadToPValueFlag()
	{
		return DeadToPValueFlag;
	}
	public void setDeadToPValueFlag(String aDeadToPValueFlag)
	{
		DeadToPValueFlag = aDeadToPValueFlag;
	}
	/**
	* 0 －－ 领取时不需要重新计算 1 －－ 领取时需要重新计算
	*/
	public String getNeedReCompute()
	{
		return NeedReCompute;
	}
	public void setNeedReCompute(String aNeedReCompute)
	{
		NeedReCompute = aNeedReCompute;
	}
	/**
	* 0 －－ 被保人 1 －－ 投保人 2 －－ 连带被保人
	*/
	public String getCasePolType()
	{
		return CasePolType;
	}
	public void setCasePolType(String aCasePolType)
	{
		CasePolType = aCasePolType;
	}
	/**
	* 0 or null －－ 没有伤残比例 1 －－ 普通伤残 2 －－ 烧伤伤残 3 －－ 重要器官移植
	*/
	public String getDeformityGrade()
	{
		return DeformityGrade;
	}
	public void setDeformityGrade(String aDeformityGrade)
	{
		DeformityGrade = aDeformityGrade;
	}
	/**
	* 用于匹配算法的描述,返回值为1--匹配,0--不匹配 对于通常情况可以没有,理陪时使用GetDutyKind自动匹配责任 对于用GetDutyKind无法区分的情况,如女工生育责任;出险原因为疾病 出险类型为医疗,但是仅用该字段无法判断是否自动匹配该责任 此时需要在此描述一条SQL判断是否有生育的相关信息
	*/
	public String getFilterCalCode()
	{
		return FilterCalCode;
	}
	public void setFilterCalCode(String aFilterCalCode)
	{
		FilterCalCode = aFilterCalCode;
	}
	/**
	* 附加险影响主险标记 01.增加相应责任范围
	*/
	public String getEffectOnMainRisk()
	{
		return EffectOnMainRisk;
	}
	public void setEffectOnMainRisk(String aEffectOnMainRisk)
	{
		EffectOnMainRisk = aEffectOnMainRisk;
	}
	public String getExtraAmntFlag()
	{
		return ExtraAmntFlag;
	}
	public void setExtraAmntFlag(String aExtraAmntFlag)
	{
		ExtraAmntFlag = aExtraAmntFlag;
	}
	public String getYearGetLimitFlag()
	{
		return YearGetLimitFlag;
	}
	public void setYearGetLimitFlag(String aYearGetLimitFlag)
	{
		YearGetLimitFlag = aYearGetLimitFlag;
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


	/**
	* 使用另外一个 PD_LMDutyGetClm_LibSchema 对象给 Schema 赋值
	* @param: aPD_LMDutyGetClm_LibSchema PD_LMDutyGetClm_LibSchema
	**/
	public void setSchema(PD_LMDutyGetClm_LibSchema aPD_LMDutyGetClm_LibSchema)
	{
		this.GetDutyCode2 = aPD_LMDutyGetClm_LibSchema.getGetDutyCode2();
		this.CalCode = aPD_LMDutyGetClm_LibSchema.getCalCode();
		this.GetDutyName = aPD_LMDutyGetClm_LibSchema.getGetDutyName();
		this.GetDutyKind = aPD_LMDutyGetClm_LibSchema.getGetDutyKind();
		this.DefaultVal = aPD_LMDutyGetClm_LibSchema.getDefaultVal();
		this.CnterCalCode = aPD_LMDutyGetClm_LibSchema.getCnterCalCode();
		this.OthCalCode = aPD_LMDutyGetClm_LibSchema.getOthCalCode();
		this.InpFlag = aPD_LMDutyGetClm_LibSchema.getInpFlag();
		this.StatType = aPD_LMDutyGetClm_LibSchema.getStatType();
		this.MinGet = aPD_LMDutyGetClm_LibSchema.getMinGet();
		this.AfterGet = aPD_LMDutyGetClm_LibSchema.getAfterGet();
		this.MaxGet = aPD_LMDutyGetClm_LibSchema.getMaxGet();
		this.ClaimRate = aPD_LMDutyGetClm_LibSchema.getClaimRate();
		this.ClmDayLmt = aPD_LMDutyGetClm_LibSchema.getClmDayLmt();
		this.SumClmDayLmt = aPD_LMDutyGetClm_LibSchema.getSumClmDayLmt();
		this.Deductible = aPD_LMDutyGetClm_LibSchema.getDeductible();
		this.DeDuctDay = aPD_LMDutyGetClm_LibSchema.getDeDuctDay();
		this.ObsPeriod = aPD_LMDutyGetClm_LibSchema.getObsPeriod();
		this.DeadValiFlag = aPD_LMDutyGetClm_LibSchema.getDeadValiFlag();
		this.DeadToPValueFlag = aPD_LMDutyGetClm_LibSchema.getDeadToPValueFlag();
		this.NeedReCompute = aPD_LMDutyGetClm_LibSchema.getNeedReCompute();
		this.CasePolType = aPD_LMDutyGetClm_LibSchema.getCasePolType();
		this.DeformityGrade = aPD_LMDutyGetClm_LibSchema.getDeformityGrade();
		this.FilterCalCode = aPD_LMDutyGetClm_LibSchema.getFilterCalCode();
		this.EffectOnMainRisk = aPD_LMDutyGetClm_LibSchema.getEffectOnMainRisk();
		this.ExtraAmntFlag = aPD_LMDutyGetClm_LibSchema.getExtraAmntFlag();
		this.YearGetLimitFlag = aPD_LMDutyGetClm_LibSchema.getYearGetLimitFlag();
		this.Operator = aPD_LMDutyGetClm_LibSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyGetClm_LibSchema.getMakeDate());
		this.MakeTime = aPD_LMDutyGetClm_LibSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyGetClm_LibSchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyGetClm_LibSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyGetClm_LibSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyGetClm_LibSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyGetClm_LibSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyGetClm_LibSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyGetClm_LibSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyGetClm_LibSchema.getStandbyflag6();
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
			if( rs.getString("GetDutyCode2") == null )
				this.GetDutyCode2 = null;
			else
				this.GetDutyCode2 = rs.getString("GetDutyCode2").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			this.DefaultVal = rs.getDouble("DefaultVal");
			if( rs.getString("CnterCalCode") == null )
				this.CnterCalCode = null;
			else
				this.CnterCalCode = rs.getString("CnterCalCode").trim();

			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			if( rs.getString("InpFlag") == null )
				this.InpFlag = null;
			else
				this.InpFlag = rs.getString("InpFlag").trim();

			if( rs.getString("StatType") == null )
				this.StatType = null;
			else
				this.StatType = rs.getString("StatType").trim();

			this.MinGet = rs.getDouble("MinGet");
			if( rs.getString("AfterGet") == null )
				this.AfterGet = null;
			else
				this.AfterGet = rs.getString("AfterGet").trim();

			this.MaxGet = rs.getDouble("MaxGet");
			this.ClaimRate = rs.getDouble("ClaimRate");
			this.ClmDayLmt = rs.getInt("ClmDayLmt");
			this.SumClmDayLmt = rs.getInt("SumClmDayLmt");
			this.Deductible = rs.getDouble("Deductible");
			this.DeDuctDay = rs.getInt("DeDuctDay");
			this.ObsPeriod = rs.getInt("ObsPeriod");
			if( rs.getString("DeadValiFlag") == null )
				this.DeadValiFlag = null;
			else
				this.DeadValiFlag = rs.getString("DeadValiFlag").trim();

			if( rs.getString("DeadToPValueFlag") == null )
				this.DeadToPValueFlag = null;
			else
				this.DeadToPValueFlag = rs.getString("DeadToPValueFlag").trim();

			if( rs.getString("NeedReCompute") == null )
				this.NeedReCompute = null;
			else
				this.NeedReCompute = rs.getString("NeedReCompute").trim();

			if( rs.getString("CasePolType") == null )
				this.CasePolType = null;
			else
				this.CasePolType = rs.getString("CasePolType").trim();

			if( rs.getString("DeformityGrade") == null )
				this.DeformityGrade = null;
			else
				this.DeformityGrade = rs.getString("DeformityGrade").trim();

			if( rs.getString("FilterCalCode") == null )
				this.FilterCalCode = null;
			else
				this.FilterCalCode = rs.getString("FilterCalCode").trim();

			if( rs.getString("EffectOnMainRisk") == null )
				this.EffectOnMainRisk = null;
			else
				this.EffectOnMainRisk = rs.getString("EffectOnMainRisk").trim();

			if( rs.getString("ExtraAmntFlag") == null )
				this.ExtraAmntFlag = null;
			else
				this.ExtraAmntFlag = rs.getString("ExtraAmntFlag").trim();

			if( rs.getString("YearGetLimitFlag") == null )
				this.YearGetLimitFlag = null;
			else
				this.YearGetLimitFlag = rs.getString("YearGetLimitFlag").trim();

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
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMDutyGetClm_Lib表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetClm_LibSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyGetClm_LibSchema getSchema()
	{
		PD_LMDutyGetClm_LibSchema aPD_LMDutyGetClm_LibSchema = new PD_LMDutyGetClm_LibSchema();
		aPD_LMDutyGetClm_LibSchema.setSchema(this);
		return aPD_LMDutyGetClm_LibSchema;
	}

	public PD_LMDutyGetClm_LibDB getDB()
	{
		PD_LMDutyGetClm_LibDB aDBOper = new PD_LMDutyGetClm_LibDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetClm_Lib描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultVal));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CnterCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InpFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StatType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinGet));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterGet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxGet));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmDayLmt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumClmDayLmt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Deductible));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeDuctDay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ObsPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadToPValueFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedReCompute)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeformityGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FilterCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EffectOnMainRisk)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExtraAmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(YearGetLimitFlag)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetClm_Lib>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DefaultVal = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			CnterCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InpFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			StatType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MinGet = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			AfterGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MaxGet = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			ClmDayLmt= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			SumClmDayLmt= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			Deductible = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			DeDuctDay= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			ObsPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			DeadValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			DeadToPValueFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			NeedReCompute = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CasePolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DeformityGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			FilterCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			EffectOnMainRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ExtraAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			YearGetLimitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetClm_LibSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode2));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultVal));
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CnterCalCode));
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthCalCode));
		}
		if (FCode.equalsIgnoreCase("InpFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InpFlag));
		}
		if (FCode.equalsIgnoreCase("StatType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatType));
		}
		if (FCode.equalsIgnoreCase("MinGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinGet));
		}
		if (FCode.equalsIgnoreCase("AfterGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterGet));
		}
		if (FCode.equalsIgnoreCase("MaxGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxGet));
		}
		if (FCode.equalsIgnoreCase("ClaimRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimRate));
		}
		if (FCode.equalsIgnoreCase("ClmDayLmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmDayLmt));
		}
		if (FCode.equalsIgnoreCase("SumClmDayLmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumClmDayLmt));
		}
		if (FCode.equalsIgnoreCase("Deductible"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Deductible));
		}
		if (FCode.equalsIgnoreCase("DeDuctDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeDuctDay));
		}
		if (FCode.equalsIgnoreCase("ObsPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObsPeriod));
		}
		if (FCode.equalsIgnoreCase("DeadValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadValiFlag));
		}
		if (FCode.equalsIgnoreCase("DeadToPValueFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadToPValueFlag));
		}
		if (FCode.equalsIgnoreCase("NeedReCompute"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedReCompute));
		}
		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePolType));
		}
		if (FCode.equalsIgnoreCase("DeformityGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeformityGrade));
		}
		if (FCode.equalsIgnoreCase("FilterCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FilterCalCode));
		}
		if (FCode.equalsIgnoreCase("EffectOnMainRisk"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EffectOnMainRisk));
		}
		if (FCode.equalsIgnoreCase("ExtraAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtraAmntFlag));
		}
		if (FCode.equalsIgnoreCase("YearGetLimitFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YearGetLimitFlag));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode2);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 4:
				strFieldValue = String.valueOf(DefaultVal);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CnterCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InpFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(StatType);
				break;
			case 9:
				strFieldValue = String.valueOf(MinGet);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AfterGet);
				break;
			case 11:
				strFieldValue = String.valueOf(MaxGet);
				break;
			case 12:
				strFieldValue = String.valueOf(ClaimRate);
				break;
			case 13:
				strFieldValue = String.valueOf(ClmDayLmt);
				break;
			case 14:
				strFieldValue = String.valueOf(SumClmDayLmt);
				break;
			case 15:
				strFieldValue = String.valueOf(Deductible);
				break;
			case 16:
				strFieldValue = String.valueOf(DeDuctDay);
				break;
			case 17:
				strFieldValue = String.valueOf(ObsPeriod);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(DeadValiFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(DeadToPValueFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(NeedReCompute);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(CasePolType);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(DeformityGrade);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(FilterCalCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(EffectOnMainRisk);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ExtraAmntFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(YearGetLimitFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 34:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 35:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 36:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 37:
				strFieldValue = String.valueOf(Standbyflag6);
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

		if (FCode.equalsIgnoreCase("GetDutyCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode2 = FValue.trim();
			}
			else
				GetDutyCode2 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
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
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultVal = d;
			}
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CnterCalCode = FValue.trim();
			}
			else
				CnterCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthCalCode = FValue.trim();
			}
			else
				OthCalCode = null;
		}
		if (FCode.equalsIgnoreCase("InpFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InpFlag = FValue.trim();
			}
			else
				InpFlag = null;
		}
		if (FCode.equalsIgnoreCase("StatType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StatType = FValue.trim();
			}
			else
				StatType = null;
		}
		if (FCode.equalsIgnoreCase("MinGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MinGet = d;
			}
		}
		if (FCode.equalsIgnoreCase("AfterGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterGet = FValue.trim();
			}
			else
				AfterGet = null;
		}
		if (FCode.equalsIgnoreCase("MaxGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxGet = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmDayLmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmDayLmt = i;
			}
		}
		if (FCode.equalsIgnoreCase("SumClmDayLmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumClmDayLmt = i;
			}
		}
		if (FCode.equalsIgnoreCase("Deductible"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Deductible = d;
			}
		}
		if (FCode.equalsIgnoreCase("DeDuctDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DeDuctDay = i;
			}
		}
		if (FCode.equalsIgnoreCase("ObsPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ObsPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("DeadValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadValiFlag = FValue.trim();
			}
			else
				DeadValiFlag = null;
		}
		if (FCode.equalsIgnoreCase("DeadToPValueFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadToPValueFlag = FValue.trim();
			}
			else
				DeadToPValueFlag = null;
		}
		if (FCode.equalsIgnoreCase("NeedReCompute"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedReCompute = FValue.trim();
			}
			else
				NeedReCompute = null;
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
		if (FCode.equalsIgnoreCase("DeformityGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeformityGrade = FValue.trim();
			}
			else
				DeformityGrade = null;
		}
		if (FCode.equalsIgnoreCase("FilterCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FilterCalCode = FValue.trim();
			}
			else
				FilterCalCode = null;
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
		if (FCode.equalsIgnoreCase("ExtraAmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExtraAmntFlag = FValue.trim();
			}
			else
				ExtraAmntFlag = null;
		}
		if (FCode.equalsIgnoreCase("YearGetLimitFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YearGetLimitFlag = FValue.trim();
			}
			else
				YearGetLimitFlag = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMDutyGetClm_LibSchema other = (PD_LMDutyGetClm_LibSchema)otherObject;
		return
			GetDutyCode2.equals(other.getGetDutyCode2())
			&& CalCode.equals(other.getCalCode())
			&& GetDutyName.equals(other.getGetDutyName())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& DefaultVal == other.getDefaultVal()
			&& CnterCalCode.equals(other.getCnterCalCode())
			&& OthCalCode.equals(other.getOthCalCode())
			&& InpFlag.equals(other.getInpFlag())
			&& StatType.equals(other.getStatType())
			&& MinGet == other.getMinGet()
			&& AfterGet.equals(other.getAfterGet())
			&& MaxGet == other.getMaxGet()
			&& ClaimRate == other.getClaimRate()
			&& ClmDayLmt == other.getClmDayLmt()
			&& SumClmDayLmt == other.getSumClmDayLmt()
			&& Deductible == other.getDeductible()
			&& DeDuctDay == other.getDeDuctDay()
			&& ObsPeriod == other.getObsPeriod()
			&& DeadValiFlag.equals(other.getDeadValiFlag())
			&& DeadToPValueFlag.equals(other.getDeadToPValueFlag())
			&& NeedReCompute.equals(other.getNeedReCompute())
			&& CasePolType.equals(other.getCasePolType())
			&& DeformityGrade.equals(other.getDeformityGrade())
			&& FilterCalCode.equals(other.getFilterCalCode())
			&& EffectOnMainRisk.equals(other.getEffectOnMainRisk())
			&& ExtraAmntFlag.equals(other.getExtraAmntFlag())
			&& YearGetLimitFlag.equals(other.getYearGetLimitFlag())
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
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("GetDutyCode2") ) {
			return 0;
		}
		if( strFieldName.equals("CalCode") ) {
			return 1;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 3;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return 4;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 6;
		}
		if( strFieldName.equals("InpFlag") ) {
			return 7;
		}
		if( strFieldName.equals("StatType") ) {
			return 8;
		}
		if( strFieldName.equals("MinGet") ) {
			return 9;
		}
		if( strFieldName.equals("AfterGet") ) {
			return 10;
		}
		if( strFieldName.equals("MaxGet") ) {
			return 11;
		}
		if( strFieldName.equals("ClaimRate") ) {
			return 12;
		}
		if( strFieldName.equals("ClmDayLmt") ) {
			return 13;
		}
		if( strFieldName.equals("SumClmDayLmt") ) {
			return 14;
		}
		if( strFieldName.equals("Deductible") ) {
			return 15;
		}
		if( strFieldName.equals("DeDuctDay") ) {
			return 16;
		}
		if( strFieldName.equals("ObsPeriod") ) {
			return 17;
		}
		if( strFieldName.equals("DeadValiFlag") ) {
			return 18;
		}
		if( strFieldName.equals("DeadToPValueFlag") ) {
			return 19;
		}
		if( strFieldName.equals("NeedReCompute") ) {
			return 20;
		}
		if( strFieldName.equals("CasePolType") ) {
			return 21;
		}
		if( strFieldName.equals("DeformityGrade") ) {
			return 22;
		}
		if( strFieldName.equals("FilterCalCode") ) {
			return 23;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return 24;
		}
		if( strFieldName.equals("ExtraAmntFlag") ) {
			return 25;
		}
		if( strFieldName.equals("YearGetLimitFlag") ) {
			return 26;
		}
		if( strFieldName.equals("Operator") ) {
			return 27;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 28;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 32;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 33;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 34;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 35;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 36;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 37;
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
				strFieldName = "GetDutyCode2";
				break;
			case 1:
				strFieldName = "CalCode";
				break;
			case 2:
				strFieldName = "GetDutyName";
				break;
			case 3:
				strFieldName = "GetDutyKind";
				break;
			case 4:
				strFieldName = "DefaultVal";
				break;
			case 5:
				strFieldName = "CnterCalCode";
				break;
			case 6:
				strFieldName = "OthCalCode";
				break;
			case 7:
				strFieldName = "InpFlag";
				break;
			case 8:
				strFieldName = "StatType";
				break;
			case 9:
				strFieldName = "MinGet";
				break;
			case 10:
				strFieldName = "AfterGet";
				break;
			case 11:
				strFieldName = "MaxGet";
				break;
			case 12:
				strFieldName = "ClaimRate";
				break;
			case 13:
				strFieldName = "ClmDayLmt";
				break;
			case 14:
				strFieldName = "SumClmDayLmt";
				break;
			case 15:
				strFieldName = "Deductible";
				break;
			case 16:
				strFieldName = "DeDuctDay";
				break;
			case 17:
				strFieldName = "ObsPeriod";
				break;
			case 18:
				strFieldName = "DeadValiFlag";
				break;
			case 19:
				strFieldName = "DeadToPValueFlag";
				break;
			case 20:
				strFieldName = "NeedReCompute";
				break;
			case 21:
				strFieldName = "CasePolType";
				break;
			case 22:
				strFieldName = "DeformityGrade";
				break;
			case 23:
				strFieldName = "FilterCalCode";
				break;
			case 24:
				strFieldName = "EffectOnMainRisk";
				break;
			case 25:
				strFieldName = "ExtraAmntFlag";
				break;
			case 26:
				strFieldName = "YearGetLimitFlag";
				break;
			case 27:
				strFieldName = "Operator";
				break;
			case 28:
				strFieldName = "MakeDate";
				break;
			case 29:
				strFieldName = "MakeTime";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
				strFieldName = "ModifyTime";
				break;
			case 32:
				strFieldName = "Standbyflag1";
				break;
			case 33:
				strFieldName = "Standbyflag2";
				break;
			case 34:
				strFieldName = "Standbyflag3";
				break;
			case 35:
				strFieldName = "Standbyflag4";
				break;
			case 36:
				strFieldName = "Standbyflag5";
				break;
			case 37:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("GetDutyCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InpFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StatType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinGet") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AfterGet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxGet") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmDayLmt") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SumClmDayLmt") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Deductible") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeDuctDay") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ObsPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DeadValiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadToPValueFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedReCompute") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeformityGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FilterCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExtraAmntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YearGetLimitFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 35:
				nFieldType = Schema.TYPE_INT;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
