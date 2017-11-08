

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
import com.sinosoft.lis.db.LMDutyGetClmDB;

/*
 * <p>ClassName: LMDutyGetClmSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyGetClmSchema implements Schema, Cloneable
{
	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 给付名称 */
	private String GetDutyName;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 默认值 */
	private double DefaultVal;
	/** 算法 */
	private String CalCode;
	/** 反算算法 */
	private String CnterCalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 录入标记 */
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
	/** 每日住院给付标记 */
	private String GetByHosday;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyGetClmSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GetDutyCode";
		pk[1] = "GetDutyKind";

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
		LMDutyGetClmSchema cloned = (LMDutyGetClmSchema)super.clone();
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
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	/**
	* 100  意外医疗<p>
	* 101  意外伤残<p>
	* 102  意外死亡<p>
	* 103  意外高残<p>
	* 104  意外重大疾病<p>
	* 105  意外特种疾病<p>
	* 106  意外失业失能<p>
	* 109  意外豁免<p>
	* 200  疾病医疗<p>
	* 201  疾病伤残<p>
	* 202  疾病死亡<p>
	* 203  疾病高残<p>
	* 204  疾病重大疾病<p>
	* 205  疾病特种疾病<p>
	* 206  疾病失业失能<p>
	* 209  疾病豁免
	*/
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

	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
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
	* #"000" 无动作# "001"   保额递减（暂不用）#"002"    保额递增（暂不用）# "003" 无条件销户 # "004" 最后一次给付销户
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
	* N--无关  Y--有关
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
	* 0 －－ 领取时不需要重新计算<p>
	* 1 －－ 领取时需要重新计算
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
		CasePolType = aCasePolType;
	}
	/**
	* 0 or null －－ 没有伤残比例<p>
	* 1 －－ 普通伤残<p>
	* 2 －－ 烧伤伤残<p>
	* 3 －－ 重要器官移植
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
	* 用于匹配算法的描述,返回值为1--匹配,0--不匹配<p>
	* 对于通常情况可以没有,理陪时使用GetDutyKind自动匹配责任<p>
	* 对于用GetDutyKind无法区分的情况,如女工生育责任;出险原因为疾病<p>
	* 出险类型为医疗,但是仅用该字段无法判断是否自动匹配该责任<p>
	* 此时需要在此描述一条SQL判断是否有生育的相关信息
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
	* 附加险影响主险标记<p>
	* 01.增加相应责任范围
	*/
	public String getEffectOnMainRisk()
	{
		return EffectOnMainRisk;
	}
	public void setEffectOnMainRisk(String aEffectOnMainRisk)
	{
		EffectOnMainRisk = aEffectOnMainRisk;
	}
	public String getGetByHosday()
	{
		return GetByHosday;
	}
	public void setGetByHosday(String aGetByHosday)
	{
		GetByHosday = aGetByHosday;
	}

	/**
	* 使用另外一个 LMDutyGetClmSchema 对象给 Schema 赋值
	* @param: aLMDutyGetClmSchema LMDutyGetClmSchema
	**/
	public void setSchema(LMDutyGetClmSchema aLMDutyGetClmSchema)
	{
		this.GetDutyCode = aLMDutyGetClmSchema.getGetDutyCode();
		this.GetDutyName = aLMDutyGetClmSchema.getGetDutyName();
		this.GetDutyKind = aLMDutyGetClmSchema.getGetDutyKind();
		this.DefaultVal = aLMDutyGetClmSchema.getDefaultVal();
		this.CalCode = aLMDutyGetClmSchema.getCalCode();
		this.CnterCalCode = aLMDutyGetClmSchema.getCnterCalCode();
		this.OthCalCode = aLMDutyGetClmSchema.getOthCalCode();
		this.InpFlag = aLMDutyGetClmSchema.getInpFlag();
		this.StatType = aLMDutyGetClmSchema.getStatType();
		this.MinGet = aLMDutyGetClmSchema.getMinGet();
		this.AfterGet = aLMDutyGetClmSchema.getAfterGet();
		this.MaxGet = aLMDutyGetClmSchema.getMaxGet();
		this.ClaimRate = aLMDutyGetClmSchema.getClaimRate();
		this.ClmDayLmt = aLMDutyGetClmSchema.getClmDayLmt();
		this.SumClmDayLmt = aLMDutyGetClmSchema.getSumClmDayLmt();
		this.Deductible = aLMDutyGetClmSchema.getDeductible();
		this.DeDuctDay = aLMDutyGetClmSchema.getDeDuctDay();
		this.ObsPeriod = aLMDutyGetClmSchema.getObsPeriod();
		this.DeadValiFlag = aLMDutyGetClmSchema.getDeadValiFlag();
		this.DeadToPValueFlag = aLMDutyGetClmSchema.getDeadToPValueFlag();
		this.NeedReCompute = aLMDutyGetClmSchema.getNeedReCompute();
		this.CasePolType = aLMDutyGetClmSchema.getCasePolType();
		this.DeformityGrade = aLMDutyGetClmSchema.getDeformityGrade();
		this.FilterCalCode = aLMDutyGetClmSchema.getFilterCalCode();
		this.EffectOnMainRisk = aLMDutyGetClmSchema.getEffectOnMainRisk();
		this.GetByHosday = aLMDutyGetClmSchema.getGetByHosday();
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

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			this.DefaultVal = rs.getDouble("DefaultVal");
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

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

			if( rs.getString("GetByHosday") == null )
				this.GetByHosday = null;
			else
				this.GetByHosday = rs.getString("GetByHosday").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMDutyGetClm表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyGetClmSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyGetClmSchema getSchema()
	{
		LMDutyGetClmSchema aLMDutyGetClmSchema = new LMDutyGetClmSchema();
		aLMDutyGetClmSchema.setSchema(this);
		return aLMDutyGetClmSchema;
	}

	public LMDutyGetClmDB getDB()
	{
		LMDutyGetClmDB aDBOper = new LMDutyGetClmDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyGetClm描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultVal));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(GetByHosday));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyGetClm>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DefaultVal = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
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
			GetByHosday = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyGetClmSchema";
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
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
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
		if (FCode.equalsIgnoreCase("GetByHosday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetByHosday));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 3:
				strFieldValue = String.valueOf(DefaultVal);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
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
				strFieldValue = StrTool.GBKToUnicode(GetByHosday);
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
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
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
		if (FCode.equalsIgnoreCase("GetByHosday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetByHosday = FValue.trim();
			}
			else
				GetByHosday = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyGetClmSchema other = (LMDutyGetClmSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyName.equals(other.getGetDutyName())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& DefaultVal == other.getDefaultVal()
			&& CalCode.equals(other.getCalCode())
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
			&& GetByHosday.equals(other.getGetByHosday());
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
		if( strFieldName.equals("GetDutyName") ) {
			return 1;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 2;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return 3;
		}
		if( strFieldName.equals("CalCode") ) {
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
		if( strFieldName.equals("GetByHosday") ) {
			return 25;
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
				strFieldName = "GetDutyName";
				break;
			case 2:
				strFieldName = "GetDutyKind";
				break;
			case 3:
				strFieldName = "DefaultVal";
				break;
			case 4:
				strFieldName = "CalCode";
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
				strFieldName = "GetByHosday";
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
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("GetByHosday") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
