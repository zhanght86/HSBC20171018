

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
import com.sinosoft.lis.db.LMDutyDB;

/*
 * <p>ClassName: LMDutySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LMDutySchema implements Schema, Cloneable
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
	/** 算入保额标记 */
	private String AddAmntFlag;
	/** 建议书计算方法 */
	private String PCalMode;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutySchema()
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
		LMDutySchema cloned = (LMDutySchema)super.clone();
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
	* 0  协议类  (保费保额均输入)<p>
	* 1  保费算领取.(与 je_pay 中的 bm_cal 相对应）<p>
	* 2  份数算保费.(与 kind_spec 中的 bm_cal 相对应）<p>
	* 3  领取反算保费.(与 je_pay 中的 bm_cal1 相对应)<p>
	* 4  养老类正反算   (例如：提供一保额计算保费，再从保费计算其他保额)<p>
	* 5  保费对应交费计划 (填保费:auto_plan;不填保费:inp_plan)<p>
	* P                   	保费算保额<p>
	* A                   	保费保额互算<p>
	* G                   	保额算保费<p>
	* O                   	其他因素算保费保额<p>
	* I                   	录入保费保额
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
	* 缴费和领取、保险年期之间的关系。<p>
	* 注意：该字段必须描述<p>
	* 0 －－ 表示需要录入<p>
	* 1 －－ 和缴费终止期间的值相同<p>
	* 2 －－ 和起领期间的值相同<p>
	* 3 －－ 和保险期间的值相同<p>
	* 4 －－ 从描述表取其默认值<p>
	* 5 －－ 更据算法，将缴费终止期间和默认值相加<p>
	* 6 －－ 更据算法，将起领期间和默认值相加<p>
	* 7 －－ 更据算法，将保险期间和默认值相加
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
	* 缴费和领取、保险年期之间的关系。<p>
	* 注意：该字段必须描述<p>
	* 0 －－ 表示需要录入<p>
	* 1 －－ 和缴费终止期间的值相同<p>
	* 2 －－ 和起领期间的值相同<p>
	* 3 －－ 和保险期间的值相同<p>
	* 4 －－ 从描述表取其默认值<p>
	* 5 －－ 更据算法，将缴费终止期间和默认值相加<p>
	* 6 －－ 更据算法，将起领期间和默认值相加<p>
	* 7 －－ 更据算法，将保险期间和默认值相加
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
	* 缴费和领取、保险年期之间的关系。<p>
	* 注意：该字段必须描述<p>
	* 0 －－ 表示需要录入<p>
	* 1 －－ 和缴费终止期间的值相同<p>
	* 2 －－ 和起领期间的值相同<p>
	* 3 －－ 和保险期间的值相同<p>
	* 4 －－ 从描述表取其默认值<p>
	* 5 －－ 更据算法，将缴费终止期间和默认值相加<p>
	* 6 －－ 更据算法，将起领期间和默认值相加<p>
	* 7 －－ 更据算法，将保险期间和默认值相加
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
	* 1.按保额销售<p>
	* 2.按份数销售<p>
	* 3.按档次销售<p>
	* 该字段用来控制保全加保,减保时的控制,按保额销售的减保额,按份数卖的减份数
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
	* 险种单位保额<p>
	* 用处:在险种描述算法中可以用?VPU?作为单位保额使用
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
	* 00--没有加费<p>
	* 10--有投保人健康加费<p>
	* 20--有投保人职业加费<p>
	* 等<p>
	* 第一位表示是否有投保人加费(0-无,1-健康加费,2-职业加费,3-两种加费都有)<p>
	* 第二位表示是否有被保人加费(0-无,1-健康加费,2-职业加费,3-两种加费都有)
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
	/**
	* 1-代表保额累加<p>
	* 2-代表取责任保额中最大的，作为保单保额
	*/
	public String getAddAmntFlag()
	{
		return AddAmntFlag;
	}
	public void setAddAmntFlag(String aAddAmntFlag)
	{
		AddAmntFlag = aAddAmntFlag;
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
	* 使用另外一个 LMDutySchema 对象给 Schema 赋值
	* @param: aLMDutySchema LMDutySchema
	**/
	public void setSchema(LMDutySchema aLMDutySchema)
	{
		this.DutyCode = aLMDutySchema.getDutyCode();
		this.DutyName = aLMDutySchema.getDutyName();
		this.PayEndYear = aLMDutySchema.getPayEndYear();
		this.PayEndYearFlag = aLMDutySchema.getPayEndYearFlag();
		this.PayEndDateCalRef = aLMDutySchema.getPayEndDateCalRef();
		this.PayEndDateCalMode = aLMDutySchema.getPayEndDateCalMode();
		this.GetYear = aLMDutySchema.getGetYear();
		this.GetYearFlag = aLMDutySchema.getGetYearFlag();
		this.InsuYear = aLMDutySchema.getInsuYear();
		this.InsuYearFlag = aLMDutySchema.getInsuYearFlag();
		this.AcciYear = aLMDutySchema.getAcciYear();
		this.AcciYearFlag = aLMDutySchema.getAcciYearFlag();
		this.CalMode = aLMDutySchema.getCalMode();
		this.PayEndYearRela = aLMDutySchema.getPayEndYearRela();
		this.GetYearRela = aLMDutySchema.getGetYearRela();
		this.InsuYearRela = aLMDutySchema.getInsuYearRela();
		this.BasicCalCode = aLMDutySchema.getBasicCalCode();
		this.RiskCalCode = aLMDutySchema.getRiskCalCode();
		this.AmntFlag = aLMDutySchema.getAmntFlag();
		this.VPU = aLMDutySchema.getVPU();
		this.AddFeeFlag = aLMDutySchema.getAddFeeFlag();
		this.EndDateCalMode = aLMDutySchema.getEndDateCalMode();
		this.AddAmntFlag = aLMDutySchema.getAddAmntFlag();
		this.PCalMode = aLMDutySchema.getPCalMode();
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

			if( rs.getString("PCalMode") == null )
				this.PCalMode = null;
			else
				this.PCalMode = rs.getString("PCalMode").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMDuty表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutySchema getSchema()
	{
		LMDutySchema aLMDutySchema = new LMDutySchema();
		aLMDutySchema.setSchema(this);
		return aLMDutySchema;
	}

	public LMDutyDB getDB()
	{
		LMDutyDB aDBOper = new LMDutyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDuty描述/A>表字段
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
		strReturn.append(StrTool.cTrim(PCalMode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDuty>历史记账凭证主表信息</A>表字段
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
			PCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutySchema";
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
		LMDutySchema other = (LMDutySchema)otherObject;
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
		if( strFieldName.equals("PCalMode") ) {
			return 23;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
