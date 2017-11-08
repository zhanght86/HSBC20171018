

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
import com.sinosoft.lis.db.PD_LMDutyGetDB;

/*
 * <p>ClassName: PD_LMDutyGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_3
 */
public class PD_LMDutyGetSchema implements Schema, Cloneable
{
	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 算法编码 */
	private String CalCode;
	/** 责任代码 */
	private String DutyCode;
	/** 给付名称 */
	private String GetDutyName;
	/** 给付类型1 */
	private String GetDutyKind;
	/** 给付类型 */
	private String Type;
	/** 给付间隔 */
	private int GetIntv;
	/** 默认值 */
	private double DefaultVal;
	/** 反算算法 */
	private String CnterCalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 起领期间 */
	private int GetYear;
	/** 起领期间单位 */
	private String GetYearFlag;
	/** 起领日期计算参照 */
	private String StartDateCalRef;
	/** 起领日期计算方式 */
	private String StartDateCalMode;
	/** 起领期间上限 */
	private int MinGetStartPeriod;
	/** 止领期间 */
	private int GetEndPeriod;
	/** 止领期间单位 */
	private String GetEndUnit;
	/** 止领日期计算参照 */
	private String EndDateCalRef;
	/** 止领日期计算方式 */
	private String EndDateCalMode;
	/** 止领期间下限 */
	private int MaxGetEndPeriod;
	/** 递增标记 */
	private String AddFlag;
	/** 性别关联标记 */
	private String SexRelaFlag;
	/** 单位投保关联标记 */
	private String UnitAppRelaFlag;
	/** 算入保额标记 */
	private String AddAmntFlag;
	/** 现金领取标记 */
	private String DiscntFlag;
	/** 利率标记 */
	private String InterestFlag;
	/** 多被保人分享标记 */
	private String ShareFlag;
	/** 录入标记 */
	private String InpFlag;
	/** 受益人标记 */
	private String BnfFlag;
	/** 催付标记 */
	private String UrgeGetFlag;
	/** 被保人死亡后有效标记 */
	private String DeadValiFlag;
	/** 给付初始化标记 */
	private String GetInitFlag;
	/** 起付限 */
	private double GetLimit;
	/** 赔付限额 */
	private double MaxGet;
	/** 赔付比例 */
	private double GetRate;
	/** 是否和账户相关 */
	private String NeedAcc;
	/** 默认申请标志 */
	private String CanGet;
	/** 是否是账户结清后才能申请 */
	private String NeedCancelAcc;
	/** 给付分类1 */
	private String GetType1;
	/** 是否允许零值标记 */
	private String ZeroFlag;
	/** 给付分类2 */
	private String GetType2;
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
	/** 给付分类3 */
	private String GetType3;
	/** 建议书算法 */
	private String PCalCode;
	/** 保额重算标记 */
	private String RCalAmntFlag;
	/** 保额重算时点算法 */
	private String RCalAmntCode;

	public static final int FIELDNUM = 56;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GetDutyCode";

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
		PD_LMDutyGetSchema cloned = (PD_LMDutyGetSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
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
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	/**
	* 与Type含义相同，区分生存给付和意外/医疗给付
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	/**
	* 0 -- 生存领取 1 -- 意外给付
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	public int getGetIntv()
	{
		return GetIntv;
	}
	public void setGetIntv(int aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	public void setGetIntv(String aGetIntv)
	{
		if (aGetIntv != null && !aGetIntv.equals(""))
		{
			Integer tInteger = new Integer(aGetIntv);
			int i = tInteger.intValue();
			GetIntv = i;
		}
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

	/**
	* 保额算保额
	*/
	public String getCnterCalCode()
	{
		return CnterCalCode;
	}
	public void setCnterCalCode(String aCnterCalCode)
	{
		CnterCalCode = aCnterCalCode;
	}
	/**
	* 其他算保额
	*/
	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		OthCalCode = aOthCalCode;
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
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getGetYearFlag()
	{
		return GetYearFlag;
	}
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getStartDateCalRef()
	{
		return StartDateCalRef;
	}
	public void setStartDateCalRef(String aStartDateCalRef)
	{
		StartDateCalRef = aStartDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getStartDateCalMode()
	{
		return StartDateCalMode;
	}
	public void setStartDateCalMode(String aStartDateCalMode)
	{
		StartDateCalMode = aStartDateCalMode;
	}
	public int getMinGetStartPeriod()
	{
		return MinGetStartPeriod;
	}
	public void setMinGetStartPeriod(int aMinGetStartPeriod)
	{
		MinGetStartPeriod = aMinGetStartPeriod;
	}
	public void setMinGetStartPeriod(String aMinGetStartPeriod)
	{
		if (aMinGetStartPeriod != null && !aMinGetStartPeriod.equals(""))
		{
			Integer tInteger = new Integer(aMinGetStartPeriod);
			int i = tInteger.intValue();
			MinGetStartPeriod = i;
		}
	}

	public int getGetEndPeriod()
	{
		return GetEndPeriod;
	}
	public void setGetEndPeriod(int aGetEndPeriod)
	{
		GetEndPeriod = aGetEndPeriod;
	}
	public void setGetEndPeriod(String aGetEndPeriod)
	{
		if (aGetEndPeriod != null && !aGetEndPeriod.equals(""))
		{
			Integer tInteger = new Integer(aGetEndPeriod);
			int i = tInteger.intValue();
			GetEndPeriod = i;
		}
	}

	/**
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getGetEndUnit()
	{
		return GetEndUnit;
	}
	public void setGetEndUnit(String aGetEndUnit)
	{
		GetEndUnit = aGetEndUnit;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getEndDateCalRef()
	{
		return EndDateCalRef;
	}
	public void setEndDateCalRef(String aEndDateCalRef)
	{
		EndDateCalRef = aEndDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取保险责任终止日期
	*/
	public String getEndDateCalMode()
	{
		return EndDateCalMode;
	}
	public void setEndDateCalMode(String aEndDateCalMode)
	{
		EndDateCalMode = aEndDateCalMode;
	}
	public int getMaxGetEndPeriod()
	{
		return MaxGetEndPeriod;
	}
	public void setMaxGetEndPeriod(int aMaxGetEndPeriod)
	{
		MaxGetEndPeriod = aMaxGetEndPeriod;
	}
	public void setMaxGetEndPeriod(String aMaxGetEndPeriod)
	{
		if (aMaxGetEndPeriod != null && !aMaxGetEndPeriod.equals(""))
		{
			Integer tInteger = new Integer(aMaxGetEndPeriod);
			int i = tInteger.intValue();
			MaxGetEndPeriod = i;
		}
	}

	/**
	* Y--是、N--否
	*/
	public String getAddFlag()
	{
		return AddFlag;
	}
	public void setAddFlag(String aAddFlag)
	{
		AddFlag = aAddFlag;
	}
	/**
	* N－无关　F－男性　M－女性
	*/
	public String getSexRelaFlag()
	{
		return SexRelaFlag;
	}
	public void setSexRelaFlag(String aSexRelaFlag)
	{
		SexRelaFlag = aSexRelaFlag;
	}
	/**
	* N－无关　Y－有关
	*/
	public String getUnitAppRelaFlag()
	{
		return UnitAppRelaFlag;
	}
	public void setUnitAppRelaFlag(String aUnitAppRelaFlag)
	{
		UnitAppRelaFlag = aUnitAppRelaFlag;
	}
	/**
	* Y - 加. N - 不加.
	*/
	public String getAddAmntFlag()
	{
		return AddAmntFlag;
	}
	public void setAddAmntFlag(String aAddAmntFlag)
	{
		AddAmntFlag = aAddAmntFlag;
	}
	/**
	* 1 只能现金领取 0 可以选择 Y--允许、N--不允许
	*/
	public String getDiscntFlag()
	{
		return DiscntFlag;
	}
	public void setDiscntFlag(String aDiscntFlag)
	{
		DiscntFlag = aDiscntFlag;
	}
	/**
	* 变额领取时是否采用复利计算　0－使用规定利率　1－使用复利
	*/
	public String getInterestFlag()
	{
		return InterestFlag;
	}
	public void setInterestFlag(String aInterestFlag)
	{
		InterestFlag = aInterestFlag;
	}
	/**
	* A - 主(m)从(s)被保险人共有、M - 主(m)被保险人共有、S - 从(s)被保险人共有
	*/
	public String getShareFlag()
	{
		return ShareFlag;
	}
	public void setShareFlag(String aShareFlag)
	{
		ShareFlag = aShareFlag;
	}
	/**
	* 给付金是否需要输入(给付间隔等): 2-给付时需要录入,1-承保需要输入,0 or null -不需
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
	* I--受益人是被保险人(Insured)、A--是投保人(Appnt)、N--无限制
	*/
	public String getBnfFlag()
	{
		return BnfFlag;
	}
	public void setBnfFlag(String aBnfFlag)
	{
		BnfFlag = aBnfFlag;
	}
	/**
	* Y--发催收、N--不发催收
	*/
	public String getUrgeGetFlag()
	{
		return UrgeGetFlag;
	}
	public void setUrgeGetFlag(String aUrgeGetFlag)
	{
		UrgeGetFlag = aUrgeGetFlag;
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
	* null 保险责任期有固定给付金额、 -1~-n 按次数重置的最大给付金额、 1 ~ n 按年期重置的最大给付金额
	*/
	public String getGetInitFlag()
	{
		return GetInitFlag;
	}
	public void setGetInitFlag(String aGetInitFlag)
	{
		GetInitFlag = aGetInitFlag;
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

	/**
	* 0 －－ 账户无关 1 －－ 账户相关
	*/
	public String getNeedAcc()
	{
		return NeedAcc;
	}
	public void setNeedAcc(String aNeedAcc)
	{
		NeedAcc = aNeedAcc;
	}
	/**
	* 0 －－ 不用申请就可以领取，为该字段默认值 1 －－ 需要申请后才可领。
	*/
	public String getCanGet()
	{
		return CanGet;
	}
	public void setCanGet(String aCanGet)
	{
		CanGet = aCanGet;
	}
	/**
	* 0 －－ 不需要账户结清 1 －－ 必须要账户结清后才能进行领取。
	*/
	public String getNeedCancelAcc()
	{
		return NeedCancelAcc;
	}
	public void setNeedCancelAcc(String aNeedCancelAcc)
	{
		NeedCancelAcc = aNeedCancelAcc;
	}
	/**
	* 0 －－ 满期金 1 －－ 年金
	*/
	public String getGetType1()
	{
		return GetType1;
	}
	public void setGetType1(String aGetType1)
	{
		GetType1 = aGetType1;
	}
	/**
	* Y--允许 N--不允许 not null
	*/
	public String getZeroFlag()
	{
		return ZeroFlag;
	}
	public void setZeroFlag(String aZeroFlag)
	{
		ZeroFlag = aZeroFlag;
	}
	/**
	* 0 －－ 非养老金 1 －－ 养老金
	*/
	public String getGetType2()
	{
		return GetType2;
	}
	public void setGetType2(String aGetType2)
	{
		GetType2 = aGetType2;
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
	* 中银人寿：<p>
	* 1--周年红利<p>
	* 2--终期红利<p>
	* 3--特别红利<p>
	* 4--保证可支取现金<p>
	* 5--PDA
	*/
	public String getGetType3()
	{
		return GetType3;
	}
	public void setGetType3(String aGetType3)
	{
		GetType3 = aGetType3;
	}
	public String getPCalCode()
	{
		return PCalCode;
	}
	public void setPCalCode(String aPCalCode)
	{
		PCalCode = aPCalCode;
	}
	/**
	* Y-需要；N-不需要
	*/
	public String getRCalAmntFlag()
	{
		return RCalAmntFlag;
	}
	public void setRCalAmntFlag(String aRCalAmntFlag)
	{
		RCalAmntFlag = aRCalAmntFlag;
	}
	public String getRCalAmntCode()
	{
		return RCalAmntCode;
	}
	public void setRCalAmntCode(String aRCalAmntCode)
	{
		RCalAmntCode = aRCalAmntCode;
	}

	/**
	* 使用另外一个 PD_LMDutyGetSchema 对象给 Schema 赋值
	* @param: aPD_LMDutyGetSchema PD_LMDutyGetSchema
	**/
	public void setSchema(PD_LMDutyGetSchema aPD_LMDutyGetSchema)
	{
		this.GetDutyCode = aPD_LMDutyGetSchema.getGetDutyCode();
		this.CalCode = aPD_LMDutyGetSchema.getCalCode();
		this.DutyCode = aPD_LMDutyGetSchema.getDutyCode();
		this.GetDutyName = aPD_LMDutyGetSchema.getGetDutyName();
		this.GetDutyKind = aPD_LMDutyGetSchema.getGetDutyKind();
		this.Type = aPD_LMDutyGetSchema.getType();
		this.GetIntv = aPD_LMDutyGetSchema.getGetIntv();
		this.DefaultVal = aPD_LMDutyGetSchema.getDefaultVal();
		this.CnterCalCode = aPD_LMDutyGetSchema.getCnterCalCode();
		this.OthCalCode = aPD_LMDutyGetSchema.getOthCalCode();
		this.GetYear = aPD_LMDutyGetSchema.getGetYear();
		this.GetYearFlag = aPD_LMDutyGetSchema.getGetYearFlag();
		this.StartDateCalRef = aPD_LMDutyGetSchema.getStartDateCalRef();
		this.StartDateCalMode = aPD_LMDutyGetSchema.getStartDateCalMode();
		this.MinGetStartPeriod = aPD_LMDutyGetSchema.getMinGetStartPeriod();
		this.GetEndPeriod = aPD_LMDutyGetSchema.getGetEndPeriod();
		this.GetEndUnit = aPD_LMDutyGetSchema.getGetEndUnit();
		this.EndDateCalRef = aPD_LMDutyGetSchema.getEndDateCalRef();
		this.EndDateCalMode = aPD_LMDutyGetSchema.getEndDateCalMode();
		this.MaxGetEndPeriod = aPD_LMDutyGetSchema.getMaxGetEndPeriod();
		this.AddFlag = aPD_LMDutyGetSchema.getAddFlag();
		this.SexRelaFlag = aPD_LMDutyGetSchema.getSexRelaFlag();
		this.UnitAppRelaFlag = aPD_LMDutyGetSchema.getUnitAppRelaFlag();
		this.AddAmntFlag = aPD_LMDutyGetSchema.getAddAmntFlag();
		this.DiscntFlag = aPD_LMDutyGetSchema.getDiscntFlag();
		this.InterestFlag = aPD_LMDutyGetSchema.getInterestFlag();
		this.ShareFlag = aPD_LMDutyGetSchema.getShareFlag();
		this.InpFlag = aPD_LMDutyGetSchema.getInpFlag();
		this.BnfFlag = aPD_LMDutyGetSchema.getBnfFlag();
		this.UrgeGetFlag = aPD_LMDutyGetSchema.getUrgeGetFlag();
		this.DeadValiFlag = aPD_LMDutyGetSchema.getDeadValiFlag();
		this.GetInitFlag = aPD_LMDutyGetSchema.getGetInitFlag();
		this.GetLimit = aPD_LMDutyGetSchema.getGetLimit();
		this.MaxGet = aPD_LMDutyGetSchema.getMaxGet();
		this.GetRate = aPD_LMDutyGetSchema.getGetRate();
		this.NeedAcc = aPD_LMDutyGetSchema.getNeedAcc();
		this.CanGet = aPD_LMDutyGetSchema.getCanGet();
		this.NeedCancelAcc = aPD_LMDutyGetSchema.getNeedCancelAcc();
		this.GetType1 = aPD_LMDutyGetSchema.getGetType1();
		this.ZeroFlag = aPD_LMDutyGetSchema.getZeroFlag();
		this.GetType2 = aPD_LMDutyGetSchema.getGetType2();
		this.Operator = aPD_LMDutyGetSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyGetSchema.getMakeDate());
		this.MakeTime = aPD_LMDutyGetSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyGetSchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyGetSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyGetSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyGetSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyGetSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyGetSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyGetSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyGetSchema.getStandbyflag6();
		this.GetType3 = aPD_LMDutyGetSchema.getGetType3();
		this.PCalCode = aPD_LMDutyGetSchema.getPCalCode();
		this.RCalAmntFlag = aPD_LMDutyGetSchema.getRCalAmntFlag();
		this.RCalAmntCode = aPD_LMDutyGetSchema.getRCalAmntCode();
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
			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			this.GetIntv = rs.getInt("GetIntv");
			this.DefaultVal = rs.getDouble("DefaultVal");
			if( rs.getString("CnterCalCode") == null )
				this.CnterCalCode = null;
			else
				this.CnterCalCode = rs.getString("CnterCalCode").trim();

			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			this.GetYear = rs.getInt("GetYear");
			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			if( rs.getString("StartDateCalRef") == null )
				this.StartDateCalRef = null;
			else
				this.StartDateCalRef = rs.getString("StartDateCalRef").trim();

			if( rs.getString("StartDateCalMode") == null )
				this.StartDateCalMode = null;
			else
				this.StartDateCalMode = rs.getString("StartDateCalMode").trim();

			this.MinGetStartPeriod = rs.getInt("MinGetStartPeriod");
			this.GetEndPeriod = rs.getInt("GetEndPeriod");
			if( rs.getString("GetEndUnit") == null )
				this.GetEndUnit = null;
			else
				this.GetEndUnit = rs.getString("GetEndUnit").trim();

			if( rs.getString("EndDateCalRef") == null )
				this.EndDateCalRef = null;
			else
				this.EndDateCalRef = rs.getString("EndDateCalRef").trim();

			if( rs.getString("EndDateCalMode") == null )
				this.EndDateCalMode = null;
			else
				this.EndDateCalMode = rs.getString("EndDateCalMode").trim();

			this.MaxGetEndPeriod = rs.getInt("MaxGetEndPeriod");
			if( rs.getString("AddFlag") == null )
				this.AddFlag = null;
			else
				this.AddFlag = rs.getString("AddFlag").trim();

			if( rs.getString("SexRelaFlag") == null )
				this.SexRelaFlag = null;
			else
				this.SexRelaFlag = rs.getString("SexRelaFlag").trim();

			if( rs.getString("UnitAppRelaFlag") == null )
				this.UnitAppRelaFlag = null;
			else
				this.UnitAppRelaFlag = rs.getString("UnitAppRelaFlag").trim();

			if( rs.getString("AddAmntFlag") == null )
				this.AddAmntFlag = null;
			else
				this.AddAmntFlag = rs.getString("AddAmntFlag").trim();

			if( rs.getString("DiscntFlag") == null )
				this.DiscntFlag = null;
			else
				this.DiscntFlag = rs.getString("DiscntFlag").trim();

			if( rs.getString("InterestFlag") == null )
				this.InterestFlag = null;
			else
				this.InterestFlag = rs.getString("InterestFlag").trim();

			if( rs.getString("ShareFlag") == null )
				this.ShareFlag = null;
			else
				this.ShareFlag = rs.getString("ShareFlag").trim();

			if( rs.getString("InpFlag") == null )
				this.InpFlag = null;
			else
				this.InpFlag = rs.getString("InpFlag").trim();

			if( rs.getString("BnfFlag") == null )
				this.BnfFlag = null;
			else
				this.BnfFlag = rs.getString("BnfFlag").trim();

			if( rs.getString("UrgeGetFlag") == null )
				this.UrgeGetFlag = null;
			else
				this.UrgeGetFlag = rs.getString("UrgeGetFlag").trim();

			if( rs.getString("DeadValiFlag") == null )
				this.DeadValiFlag = null;
			else
				this.DeadValiFlag = rs.getString("DeadValiFlag").trim();

			if( rs.getString("GetInitFlag") == null )
				this.GetInitFlag = null;
			else
				this.GetInitFlag = rs.getString("GetInitFlag").trim();

			this.GetLimit = rs.getDouble("GetLimit");
			this.MaxGet = rs.getDouble("MaxGet");
			this.GetRate = rs.getDouble("GetRate");
			if( rs.getString("NeedAcc") == null )
				this.NeedAcc = null;
			else
				this.NeedAcc = rs.getString("NeedAcc").trim();

			if( rs.getString("CanGet") == null )
				this.CanGet = null;
			else
				this.CanGet = rs.getString("CanGet").trim();

			if( rs.getString("NeedCancelAcc") == null )
				this.NeedCancelAcc = null;
			else
				this.NeedCancelAcc = rs.getString("NeedCancelAcc").trim();

			if( rs.getString("GetType1") == null )
				this.GetType1 = null;
			else
				this.GetType1 = rs.getString("GetType1").trim();

			if( rs.getString("ZeroFlag") == null )
				this.ZeroFlag = null;
			else
				this.ZeroFlag = rs.getString("ZeroFlag").trim();

			if( rs.getString("GetType2") == null )
				this.GetType2 = null;
			else
				this.GetType2 = rs.getString("GetType2").trim();

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
			if( rs.getString("GetType3") == null )
				this.GetType3 = null;
			else
				this.GetType3 = rs.getString("GetType3").trim();

			if( rs.getString("PCalCode") == null )
				this.PCalCode = null;
			else
				this.PCalCode = rs.getString("PCalCode").trim();

			if( rs.getString("RCalAmntFlag") == null )
				this.RCalAmntFlag = null;
			else
				this.RCalAmntFlag = rs.getString("RCalAmntFlag").trim();

			if( rs.getString("RCalAmntCode") == null )
				this.RCalAmntCode = null;
			else
				this.RCalAmntCode = rs.getString("RCalAmntCode").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMDutyGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyGetSchema getSchema()
	{
		PD_LMDutyGetSchema aPD_LMDutyGetSchema = new PD_LMDutyGetSchema();
		aPD_LMDutyGetSchema.setSchema(this);
		return aPD_LMDutyGetSchema;
	}

	public PD_LMDutyGetDB getDB()
	{
		PD_LMDutyGetDB aDBOper = new PD_LMDutyGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultVal));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CnterCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinGetStartPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetEndPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetEndUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxGetEndPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SexRelaFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnitAppRelaFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddAmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiscntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShareFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InpFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgeGetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetInitFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxGet));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CanGet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedCancelAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZeroFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetType2)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(GetType3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RCalAmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RCalAmntCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			DefaultVal = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CnterCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StartDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StartDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MinGetStartPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			GetEndPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			GetEndUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			EndDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			EndDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MaxGetEndPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			AddFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			SexRelaFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			UnitAppRelaFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AddAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			DiscntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InterestFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ShareFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InpFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BnfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			UrgeGetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			DeadValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			GetInitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			MaxGet = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			NeedAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			CanGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			NeedCancelAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GetType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ZeroFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			GetType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			GetType3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			PCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			RCalAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			RCalAmntCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
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
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearFlag));
		}
		if (FCode.equalsIgnoreCase("StartDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDateCalRef));
		}
		if (FCode.equalsIgnoreCase("StartDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDateCalMode));
		}
		if (FCode.equalsIgnoreCase("MinGetStartPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinGetStartPeriod));
		}
		if (FCode.equalsIgnoreCase("GetEndPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetEndPeriod));
		}
		if (FCode.equalsIgnoreCase("GetEndUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetEndUnit));
		}
		if (FCode.equalsIgnoreCase("EndDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDateCalRef));
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("MaxGetEndPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxGetEndPeriod));
		}
		if (FCode.equalsIgnoreCase("AddFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFlag));
		}
		if (FCode.equalsIgnoreCase("SexRelaFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SexRelaFlag));
		}
		if (FCode.equalsIgnoreCase("UnitAppRelaFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitAppRelaFlag));
		}
		if (FCode.equalsIgnoreCase("AddAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddAmntFlag));
		}
		if (FCode.equalsIgnoreCase("DiscntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiscntFlag));
		}
		if (FCode.equalsIgnoreCase("InterestFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestFlag));
		}
		if (FCode.equalsIgnoreCase("ShareFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShareFlag));
		}
		if (FCode.equalsIgnoreCase("InpFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InpFlag));
		}
		if (FCode.equalsIgnoreCase("BnfFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfFlag));
		}
		if (FCode.equalsIgnoreCase("UrgeGetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgeGetFlag));
		}
		if (FCode.equalsIgnoreCase("DeadValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadValiFlag));
		}
		if (FCode.equalsIgnoreCase("GetInitFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetInitFlag));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("MaxGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxGet));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedAcc));
		}
		if (FCode.equalsIgnoreCase("CanGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CanGet));
		}
		if (FCode.equalsIgnoreCase("NeedCancelAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedCancelAcc));
		}
		if (FCode.equalsIgnoreCase("GetType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetType1));
		}
		if (FCode.equalsIgnoreCase("ZeroFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZeroFlag));
		}
		if (FCode.equalsIgnoreCase("GetType2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetType2));
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
		if (FCode.equalsIgnoreCase("GetType3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetType3));
		}
		if (FCode.equalsIgnoreCase("PCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PCalCode));
		}
		if (FCode.equalsIgnoreCase("RCalAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCalAmntFlag));
		}
		if (FCode.equalsIgnoreCase("RCalAmntCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCalAmntCode));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 6:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 7:
				strFieldValue = String.valueOf(DefaultVal);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CnterCalCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 10:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StartDateCalRef);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StartDateCalMode);
				break;
			case 14:
				strFieldValue = String.valueOf(MinGetStartPeriod);
				break;
			case 15:
				strFieldValue = String.valueOf(GetEndPeriod);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(GetEndUnit);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(EndDateCalRef);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(EndDateCalMode);
				break;
			case 19:
				strFieldValue = String.valueOf(MaxGetEndPeriod);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AddFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(SexRelaFlag);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(UnitAppRelaFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AddAmntFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(DiscntFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InterestFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ShareFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InpFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BnfFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(UrgeGetFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(DeadValiFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(GetInitFlag);
				break;
			case 32:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 33:
				strFieldValue = String.valueOf(MaxGet);
				break;
			case 34:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(NeedAcc);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(CanGet);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(NeedCancelAcc);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(GetType1);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ZeroFlag);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(GetType2);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 48:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 49:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 50:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 51:
				strFieldValue = String.valueOf(Standbyflag6);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(GetType3);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(PCalCode);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(RCalAmntFlag);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(RCalAmntCode);
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

		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
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
		if (FCode.equalsIgnoreCase("StartDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDateCalRef = FValue.trim();
			}
			else
				StartDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("StartDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDateCalMode = FValue.trim();
			}
			else
				StartDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("MinGetStartPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinGetStartPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetEndPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetEndPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetEndUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetEndUnit = FValue.trim();
			}
			else
				GetEndUnit = null;
		}
		if (FCode.equalsIgnoreCase("EndDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDateCalRef = FValue.trim();
			}
			else
				EndDateCalRef = null;
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
		if (FCode.equalsIgnoreCase("MaxGetEndPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxGetEndPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("AddFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFlag = FValue.trim();
			}
			else
				AddFlag = null;
		}
		if (FCode.equalsIgnoreCase("SexRelaFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SexRelaFlag = FValue.trim();
			}
			else
				SexRelaFlag = null;
		}
		if (FCode.equalsIgnoreCase("UnitAppRelaFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitAppRelaFlag = FValue.trim();
			}
			else
				UnitAppRelaFlag = null;
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
		if (FCode.equalsIgnoreCase("DiscntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiscntFlag = FValue.trim();
			}
			else
				DiscntFlag = null;
		}
		if (FCode.equalsIgnoreCase("InterestFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestFlag = FValue.trim();
			}
			else
				InterestFlag = null;
		}
		if (FCode.equalsIgnoreCase("ShareFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShareFlag = FValue.trim();
			}
			else
				ShareFlag = null;
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
		if (FCode.equalsIgnoreCase("BnfFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfFlag = FValue.trim();
			}
			else
				BnfFlag = null;
		}
		if (FCode.equalsIgnoreCase("UrgeGetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgeGetFlag = FValue.trim();
			}
			else
				UrgeGetFlag = null;
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
		if (FCode.equalsIgnoreCase("GetInitFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetInitFlag = FValue.trim();
			}
			else
				GetInitFlag = null;
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
		if (FCode.equalsIgnoreCase("MaxGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxGet = d;
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
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedAcc = FValue.trim();
			}
			else
				NeedAcc = null;
		}
		if (FCode.equalsIgnoreCase("CanGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CanGet = FValue.trim();
			}
			else
				CanGet = null;
		}
		if (FCode.equalsIgnoreCase("NeedCancelAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedCancelAcc = FValue.trim();
			}
			else
				NeedCancelAcc = null;
		}
		if (FCode.equalsIgnoreCase("GetType1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetType1 = FValue.trim();
			}
			else
				GetType1 = null;
		}
		if (FCode.equalsIgnoreCase("ZeroFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZeroFlag = FValue.trim();
			}
			else
				ZeroFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetType2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetType2 = FValue.trim();
			}
			else
				GetType2 = null;
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
		if (FCode.equalsIgnoreCase("GetType3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetType3 = FValue.trim();
			}
			else
				GetType3 = null;
		}
		if (FCode.equalsIgnoreCase("PCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PCalCode = FValue.trim();
			}
			else
				PCalCode = null;
		}
		if (FCode.equalsIgnoreCase("RCalAmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCalAmntFlag = FValue.trim();
			}
			else
				RCalAmntFlag = null;
		}
		if (FCode.equalsIgnoreCase("RCalAmntCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCalAmntCode = FValue.trim();
			}
			else
				RCalAmntCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMDutyGetSchema other = (PD_LMDutyGetSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& CalCode.equals(other.getCalCode())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyName.equals(other.getGetDutyName())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& Type.equals(other.getType())
			&& GetIntv == other.getGetIntv()
			&& DefaultVal == other.getDefaultVal()
			&& CnterCalCode.equals(other.getCnterCalCode())
			&& OthCalCode.equals(other.getOthCalCode())
			&& GetYear == other.getGetYear()
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& StartDateCalRef.equals(other.getStartDateCalRef())
			&& StartDateCalMode.equals(other.getStartDateCalMode())
			&& MinGetStartPeriod == other.getMinGetStartPeriod()
			&& GetEndPeriod == other.getGetEndPeriod()
			&& GetEndUnit.equals(other.getGetEndUnit())
			&& EndDateCalRef.equals(other.getEndDateCalRef())
			&& EndDateCalMode.equals(other.getEndDateCalMode())
			&& MaxGetEndPeriod == other.getMaxGetEndPeriod()
			&& AddFlag.equals(other.getAddFlag())
			&& SexRelaFlag.equals(other.getSexRelaFlag())
			&& UnitAppRelaFlag.equals(other.getUnitAppRelaFlag())
			&& AddAmntFlag.equals(other.getAddAmntFlag())
			&& DiscntFlag.equals(other.getDiscntFlag())
			&& InterestFlag.equals(other.getInterestFlag())
			&& ShareFlag.equals(other.getShareFlag())
			&& InpFlag.equals(other.getInpFlag())
			&& BnfFlag.equals(other.getBnfFlag())
			&& UrgeGetFlag.equals(other.getUrgeGetFlag())
			&& DeadValiFlag.equals(other.getDeadValiFlag())
			&& GetInitFlag.equals(other.getGetInitFlag())
			&& GetLimit == other.getGetLimit()
			&& MaxGet == other.getMaxGet()
			&& GetRate == other.getGetRate()
			&& NeedAcc.equals(other.getNeedAcc())
			&& CanGet.equals(other.getCanGet())
			&& NeedCancelAcc.equals(other.getNeedCancelAcc())
			&& GetType1.equals(other.getGetType1())
			&& ZeroFlag.equals(other.getZeroFlag())
			&& GetType2.equals(other.getGetType2())
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
			&& GetType3.equals(other.getGetType3())
			&& PCalCode.equals(other.getPCalCode())
			&& RCalAmntFlag.equals(other.getRCalAmntFlag())
			&& RCalAmntCode.equals(other.getRCalAmntCode());
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
		if( strFieldName.equals("GetDutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("CalCode") ) {
			return 1;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 4;
		}
		if( strFieldName.equals("Type") ) {
			return 5;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 6;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return 7;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return 8;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 9;
		}
		if( strFieldName.equals("GetYear") ) {
			return 10;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 11;
		}
		if( strFieldName.equals("StartDateCalRef") ) {
			return 12;
		}
		if( strFieldName.equals("StartDateCalMode") ) {
			return 13;
		}
		if( strFieldName.equals("MinGetStartPeriod") ) {
			return 14;
		}
		if( strFieldName.equals("GetEndPeriod") ) {
			return 15;
		}
		if( strFieldName.equals("GetEndUnit") ) {
			return 16;
		}
		if( strFieldName.equals("EndDateCalRef") ) {
			return 17;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return 18;
		}
		if( strFieldName.equals("MaxGetEndPeriod") ) {
			return 19;
		}
		if( strFieldName.equals("AddFlag") ) {
			return 20;
		}
		if( strFieldName.equals("SexRelaFlag") ) {
			return 21;
		}
		if( strFieldName.equals("UnitAppRelaFlag") ) {
			return 22;
		}
		if( strFieldName.equals("AddAmntFlag") ) {
			return 23;
		}
		if( strFieldName.equals("DiscntFlag") ) {
			return 24;
		}
		if( strFieldName.equals("InterestFlag") ) {
			return 25;
		}
		if( strFieldName.equals("ShareFlag") ) {
			return 26;
		}
		if( strFieldName.equals("InpFlag") ) {
			return 27;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return 28;
		}
		if( strFieldName.equals("UrgeGetFlag") ) {
			return 29;
		}
		if( strFieldName.equals("DeadValiFlag") ) {
			return 30;
		}
		if( strFieldName.equals("GetInitFlag") ) {
			return 31;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 32;
		}
		if( strFieldName.equals("MaxGet") ) {
			return 33;
		}
		if( strFieldName.equals("GetRate") ) {
			return 34;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return 35;
		}
		if( strFieldName.equals("CanGet") ) {
			return 36;
		}
		if( strFieldName.equals("NeedCancelAcc") ) {
			return 37;
		}
		if( strFieldName.equals("GetType1") ) {
			return 38;
		}
		if( strFieldName.equals("ZeroFlag") ) {
			return 39;
		}
		if( strFieldName.equals("GetType2") ) {
			return 40;
		}
		if( strFieldName.equals("Operator") ) {
			return 41;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 42;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 45;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 46;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 47;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 48;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 49;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 50;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 51;
		}
		if( strFieldName.equals("GetType3") ) {
			return 52;
		}
		if( strFieldName.equals("PCalCode") ) {
			return 53;
		}
		if( strFieldName.equals("RCalAmntFlag") ) {
			return 54;
		}
		if( strFieldName.equals("RCalAmntCode") ) {
			return 55;
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
				strFieldName = "GetDutyCode";
				break;
			case 1:
				strFieldName = "CalCode";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "GetDutyName";
				break;
			case 4:
				strFieldName = "GetDutyKind";
				break;
			case 5:
				strFieldName = "Type";
				break;
			case 6:
				strFieldName = "GetIntv";
				break;
			case 7:
				strFieldName = "DefaultVal";
				break;
			case 8:
				strFieldName = "CnterCalCode";
				break;
			case 9:
				strFieldName = "OthCalCode";
				break;
			case 10:
				strFieldName = "GetYear";
				break;
			case 11:
				strFieldName = "GetYearFlag";
				break;
			case 12:
				strFieldName = "StartDateCalRef";
				break;
			case 13:
				strFieldName = "StartDateCalMode";
				break;
			case 14:
				strFieldName = "MinGetStartPeriod";
				break;
			case 15:
				strFieldName = "GetEndPeriod";
				break;
			case 16:
				strFieldName = "GetEndUnit";
				break;
			case 17:
				strFieldName = "EndDateCalRef";
				break;
			case 18:
				strFieldName = "EndDateCalMode";
				break;
			case 19:
				strFieldName = "MaxGetEndPeriod";
				break;
			case 20:
				strFieldName = "AddFlag";
				break;
			case 21:
				strFieldName = "SexRelaFlag";
				break;
			case 22:
				strFieldName = "UnitAppRelaFlag";
				break;
			case 23:
				strFieldName = "AddAmntFlag";
				break;
			case 24:
				strFieldName = "DiscntFlag";
				break;
			case 25:
				strFieldName = "InterestFlag";
				break;
			case 26:
				strFieldName = "ShareFlag";
				break;
			case 27:
				strFieldName = "InpFlag";
				break;
			case 28:
				strFieldName = "BnfFlag";
				break;
			case 29:
				strFieldName = "UrgeGetFlag";
				break;
			case 30:
				strFieldName = "DeadValiFlag";
				break;
			case 31:
				strFieldName = "GetInitFlag";
				break;
			case 32:
				strFieldName = "GetLimit";
				break;
			case 33:
				strFieldName = "MaxGet";
				break;
			case 34:
				strFieldName = "GetRate";
				break;
			case 35:
				strFieldName = "NeedAcc";
				break;
			case 36:
				strFieldName = "CanGet";
				break;
			case 37:
				strFieldName = "NeedCancelAcc";
				break;
			case 38:
				strFieldName = "GetType1";
				break;
			case 39:
				strFieldName = "ZeroFlag";
				break;
			case 40:
				strFieldName = "GetType2";
				break;
			case 41:
				strFieldName = "Operator";
				break;
			case 42:
				strFieldName = "MakeDate";
				break;
			case 43:
				strFieldName = "MakeTime";
				break;
			case 44:
				strFieldName = "ModifyDate";
				break;
			case 45:
				strFieldName = "ModifyTime";
				break;
			case 46:
				strFieldName = "Standbyflag1";
				break;
			case 47:
				strFieldName = "Standbyflag2";
				break;
			case 48:
				strFieldName = "Standbyflag3";
				break;
			case 49:
				strFieldName = "Standbyflag4";
				break;
			case 50:
				strFieldName = "Standbyflag5";
				break;
			case 51:
				strFieldName = "Standbyflag6";
				break;
			case 52:
				strFieldName = "GetType3";
				break;
			case 53:
				strFieldName = "PCalCode";
				break;
			case 54:
				strFieldName = "RCalAmntFlag";
				break;
			case 55:
				strFieldName = "RCalAmntCode";
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
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinGetStartPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetEndPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetEndUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxGetEndPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AddFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SexRelaFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitAppRelaFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddAmntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiscntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShareFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InpFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgeGetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadValiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetInitFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxGet") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CanGet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedCancelAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetType1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZeroFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetType2") ) {
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
		if( strFieldName.equals("GetType3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCalAmntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCalAmntCode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 49:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
