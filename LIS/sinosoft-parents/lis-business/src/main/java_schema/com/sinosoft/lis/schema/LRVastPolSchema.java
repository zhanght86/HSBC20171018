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
import com.sinosoft.lis.db.LRVastPolDB;

/*
 * <p>ClassName: LRVastPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRVastPolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRVastPolSchema.class);

	// @Field
	/** 计算年度 */
	private int CalculateYear;
	/** 保单号 */
	private String PolNo;
	/** 团单号 */
	private String GrppolNo;
	/** 再保机构 */
	private String ReinsureCom;
	/** 保单状态 */
	private String PolState;
	/** 险种代码 */
	private String RiskCode;
	/** 险种计算类型 */
	private String RiskCalCode;
	/** 销售渠道 */
	private String SaleChnl;
	/** 签单日期 */
	private Date SignDate;
	/** 生效日期 */
	private Date Cvalidate;
	/** 被保险人客户号 */
	private String InsuredNo;
	/** 被保险人姓名 */
	private String InsuredName;
	/** 被保险人性别 */
	private String InsuredSex;
	/** 被保险人证件号 */
	private String InsuredIDNo;
	/** 保费 */
	private double Prem;
	/** 基本保额 */
	private double Amnt;
	/** 保单责任终止日 */
	private Date EndDate;
	/** 分保比例 */
	private double CessionRate;
	/** 分保额 */
	private double CessionAmount;
	/** 分保保费 */
	private double CessPrem;
	/** 时间点 */
	private Date Time;
	/** 调整总保费 */
	private double ChgPrem;
	/** 调整分保费 */
	private double ShrePrem;
	/** 自留风险保额 */
	private double LeftRiskAmnt;
	/** 净自留保费 */
	private double LeftPrem;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRVastPolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "CalculateYear";
		pk[1] = "PolNo";
		pk[2] = "GrppolNo";
		pk[3] = "ReinsureCom";
		pk[4] = "RiskCode";

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
		LRVastPolSchema cloned = (LRVastPolSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getCalculateYear()
	{
		return CalculateYear;
	}
	public void setCalculateYear(int aCalculateYear)
	{
		CalculateYear = aCalculateYear;
	}
	public void setCalculateYear(String aCalculateYear)
	{
		if (aCalculateYear != null && !aCalculateYear.equals(""))
		{
			Integer tInteger = new Integer(aCalculateYear);
			int i = tInteger.intValue();
			CalculateYear = i;
		}
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getGrppolNo()
	{
		return GrppolNo;
	}
	public void setGrppolNo(String aGrppolNo)
	{
		GrppolNo = aGrppolNo;
	}
	public String getReinsureCom()
	{
		return ReinsureCom;
	}
	public void setReinsureCom(String aReinsureCom)
	{
		ReinsureCom = aReinsureCom;
	}
	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
	}
	/**
	* 参考巨灾险种描述表中的险种描述
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 第一类：<p>
	* 1：一年期寿险<p>
	* 2: 一年期意外险<p>
	* 3: 一年期意外附加险<p>
	* 4: 长期寿险<p>
	* 5: 长期意外险<p>
	* 6: 长期意外附加险<p>
	* <p>
	* <p>
	* 第二类：<p>
	* 7：极短期意外险
	*/
	public String getRiskCalCode()
	{
		return RiskCalCode;
	}
	public void setRiskCalCode(String aRiskCalCode)
	{
		RiskCalCode = aRiskCalCode;
	}
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
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

	public String getCvalidate()
	{
		if( Cvalidate != null )
			return fDate.getString(Cvalidate);
		else
			return null;
	}
	public void setCvalidate(Date aCvalidate)
	{
		Cvalidate = aCvalidate;
	}
	public void setCvalidate(String aCvalidate)
	{
		if (aCvalidate != null && !aCvalidate.equals("") )
		{
			Cvalidate = fDate.getDate( aCvalidate );
		}
		else
			Cvalidate = null;
	}

	/**
	* S--溢额<p>
	* Q--成数<p>
	* M--溢额＋成数
	*/
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
	public String getInsuredIDNo()
	{
		return InsuredIDNo;
	}
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		InsuredIDNo = aInsuredIDNo;
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

	/**
	* 计算巨灾保单的时点
	*/
	public String getTime()
	{
		if( Time != null )
			return fDate.getString(Time);
		else
			return null;
	}
	public void setTime(Date aTime)
	{
		Time = aTime;
	}
	public void setTime(String aTime)
	{
		if (aTime != null && !aTime.equals("") )
		{
			Time = fDate.getDate( aTime );
		}
		else
			Time = null;
	}

	public double getChgPrem()
	{
		return ChgPrem;
	}
	public void setChgPrem(double aChgPrem)
	{
		ChgPrem = aChgPrem;
	}
	public void setChgPrem(String aChgPrem)
	{
		if (aChgPrem != null && !aChgPrem.equals(""))
		{
			Double tDouble = new Double(aChgPrem);
			double d = tDouble.doubleValue();
			ChgPrem = d;
		}
	}

	public double getShrePrem()
	{
		return ShrePrem;
	}
	public void setShrePrem(double aShrePrem)
	{
		ShrePrem = aShrePrem;
	}
	public void setShrePrem(String aShrePrem)
	{
		if (aShrePrem != null && !aShrePrem.equals(""))
		{
			Double tDouble = new Double(aShrePrem);
			double d = tDouble.doubleValue();
			ShrePrem = d;
		}
	}

	/**
	* 自留风险保额＝当前风险保额（nowriskamnt）－分保额（包括法定和商业分保额）<p>
	*                   －（调整总保额－调整分保额）
	*/
	public double getLeftRiskAmnt()
	{
		return LeftRiskAmnt;
	}
	public void setLeftRiskAmnt(double aLeftRiskAmnt)
	{
		LeftRiskAmnt = aLeftRiskAmnt;
	}
	public void setLeftRiskAmnt(String aLeftRiskAmnt)
	{
		if (aLeftRiskAmnt != null && !aLeftRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aLeftRiskAmnt);
			double d = tDouble.doubleValue();
			LeftRiskAmnt = d;
		}
	}

	/**
	* 净自留保费＝保费（prem）－分保费（cessprem）－（调整保费－调整分保费）
	*/
	public double getLeftPrem()
	{
		return LeftPrem;
	}
	public void setLeftPrem(double aLeftPrem)
	{
		LeftPrem = aLeftPrem;
	}
	public void setLeftPrem(String aLeftPrem)
	{
		if (aLeftPrem != null && !aLeftPrem.equals(""))
		{
			Double tDouble = new Double(aLeftPrem);
			double d = tDouble.doubleValue();
			LeftPrem = d;
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
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 LRVastPolSchema 对象给 Schema 赋值
	* @param: aLRVastPolSchema LRVastPolSchema
	**/
	public void setSchema(LRVastPolSchema aLRVastPolSchema)
	{
		this.CalculateYear = aLRVastPolSchema.getCalculateYear();
		this.PolNo = aLRVastPolSchema.getPolNo();
		this.GrppolNo = aLRVastPolSchema.getGrppolNo();
		this.ReinsureCom = aLRVastPolSchema.getReinsureCom();
		this.PolState = aLRVastPolSchema.getPolState();
		this.RiskCode = aLRVastPolSchema.getRiskCode();
		this.RiskCalCode = aLRVastPolSchema.getRiskCalCode();
		this.SaleChnl = aLRVastPolSchema.getSaleChnl();
		this.SignDate = fDate.getDate( aLRVastPolSchema.getSignDate());
		this.Cvalidate = fDate.getDate( aLRVastPolSchema.getCvalidate());
		this.InsuredNo = aLRVastPolSchema.getInsuredNo();
		this.InsuredName = aLRVastPolSchema.getInsuredName();
		this.InsuredSex = aLRVastPolSchema.getInsuredSex();
		this.InsuredIDNo = aLRVastPolSchema.getInsuredIDNo();
		this.Prem = aLRVastPolSchema.getPrem();
		this.Amnt = aLRVastPolSchema.getAmnt();
		this.EndDate = fDate.getDate( aLRVastPolSchema.getEndDate());
		this.CessionRate = aLRVastPolSchema.getCessionRate();
		this.CessionAmount = aLRVastPolSchema.getCessionAmount();
		this.CessPrem = aLRVastPolSchema.getCessPrem();
		this.Time = fDate.getDate( aLRVastPolSchema.getTime());
		this.ChgPrem = aLRVastPolSchema.getChgPrem();
		this.ShrePrem = aLRVastPolSchema.getShrePrem();
		this.LeftRiskAmnt = aLRVastPolSchema.getLeftRiskAmnt();
		this.LeftPrem = aLRVastPolSchema.getLeftPrem();
		this.MakeDate = fDate.getDate( aLRVastPolSchema.getMakeDate());
		this.MakeTime = aLRVastPolSchema.getMakeTime();
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
			this.CalculateYear = rs.getInt("CalculateYear");
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GrppolNo") == null )
				this.GrppolNo = null;
			else
				this.GrppolNo = rs.getString("GrppolNo").trim();

			if( rs.getString("ReinsureCom") == null )
				this.ReinsureCom = null;
			else
				this.ReinsureCom = rs.getString("ReinsureCom").trim();

			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskCalCode") == null )
				this.RiskCalCode = null;
			else
				this.RiskCalCode = rs.getString("RiskCalCode").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			this.SignDate = rs.getDate("SignDate");
			this.Cvalidate = rs.getDate("Cvalidate");
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

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.EndDate = rs.getDate("EndDate");
			this.CessionRate = rs.getDouble("CessionRate");
			this.CessionAmount = rs.getDouble("CessionAmount");
			this.CessPrem = rs.getDouble("CessPrem");
			this.Time = rs.getDate("Time");
			this.ChgPrem = rs.getDouble("ChgPrem");
			this.ShrePrem = rs.getDouble("ShrePrem");
			this.LeftRiskAmnt = rs.getDouble("LeftRiskAmnt");
			this.LeftPrem = rs.getDouble("LeftPrem");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRVastPol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRVastPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRVastPolSchema getSchema()
	{
		LRVastPolSchema aLRVastPolSchema = new LRVastPolSchema();
		aLRVastPolSchema.setSchema(this);
		return aLRVastPolSchema;
	}

	public LRVastPolDB getDB()
	{
		LRVastPolDB aDBOper = new LRVastPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRVastPol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(CalculateYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrppolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Cvalidate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Time ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ShrePrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LeftRiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LeftPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRVastPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalculateYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrppolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReinsureCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			Cvalidate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			CessionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			CessionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			CessPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Time = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ChgPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			ShrePrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			LeftRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			LeftPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRVastPolSchema";
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
		if (FCode.equalsIgnoreCase("CalculateYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalculateYear));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GrppolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrppolNo));
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureCom));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalCode));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("Cvalidate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCvalidate()));
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
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionRate));
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionAmount));
		}
		if (FCode.equalsIgnoreCase("CessPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessPrem));
		}
		if (FCode.equalsIgnoreCase("Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTime()));
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgPrem));
		}
		if (FCode.equalsIgnoreCase("ShrePrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShrePrem));
		}
		if (FCode.equalsIgnoreCase("LeftRiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LeftRiskAmnt));
		}
		if (FCode.equalsIgnoreCase("LeftPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LeftPrem));
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
				strFieldValue = String.valueOf(CalculateYear);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrppolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReinsureCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCvalidate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 14:
				strFieldValue = String.valueOf(Prem);
				break;
			case 15:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 17:
				strFieldValue = String.valueOf(CessionRate);
				break;
			case 18:
				strFieldValue = String.valueOf(CessionAmount);
				break;
			case 19:
				strFieldValue = String.valueOf(CessPrem);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTime()));
				break;
			case 21:
				strFieldValue = String.valueOf(ChgPrem);
				break;
			case 22:
				strFieldValue = String.valueOf(ShrePrem);
				break;
			case 23:
				strFieldValue = String.valueOf(LeftRiskAmnt);
				break;
			case 24:
				strFieldValue = String.valueOf(LeftPrem);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
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

		if (FCode.equalsIgnoreCase("CalculateYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalculateYear = i;
			}
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
		if (FCode.equalsIgnoreCase("GrppolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrppolNo = FValue.trim();
			}
			else
				GrppolNo = null;
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
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
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
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalCode = FValue.trim();
			}
			else
				RiskCalCode = null;
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
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("Cvalidate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Cvalidate = fDate.getDate( FValue );
			}
			else
				Cvalidate = null;
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
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
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
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
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
		if (FCode.equalsIgnoreCase("CessPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Time"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Time = fDate.getDate( FValue );
			}
			else
				Time = null;
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ShrePrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ShrePrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("LeftRiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LeftRiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("LeftPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LeftPrem = d;
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
		LRVastPolSchema other = (LRVastPolSchema)otherObject;
		return
			CalculateYear == other.getCalculateYear()
			&& PolNo.equals(other.getPolNo())
			&& GrppolNo.equals(other.getGrppolNo())
			&& ReinsureCom.equals(other.getReinsureCom())
			&& PolState.equals(other.getPolState())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskCalCode.equals(other.getRiskCalCode())
			&& SaleChnl.equals(other.getSaleChnl())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& fDate.getString(Cvalidate).equals(other.getCvalidate())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& CessionRate == other.getCessionRate()
			&& CessionAmount == other.getCessionAmount()
			&& CessPrem == other.getCessPrem()
			&& fDate.getString(Time).equals(other.getTime())
			&& ChgPrem == other.getChgPrem()
			&& ShrePrem == other.getShrePrem()
			&& LeftRiskAmnt == other.getLeftRiskAmnt()
			&& LeftPrem == other.getLeftPrem()
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
		if( strFieldName.equals("CalculateYear") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrppolNo") ) {
			return 2;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return 3;
		}
		if( strFieldName.equals("PolState") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 6;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 7;
		}
		if( strFieldName.equals("SignDate") ) {
			return 8;
		}
		if( strFieldName.equals("Cvalidate") ) {
			return 9;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 10;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 11;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 13;
		}
		if( strFieldName.equals("Prem") ) {
			return 14;
		}
		if( strFieldName.equals("Amnt") ) {
			return 15;
		}
		if( strFieldName.equals("EndDate") ) {
			return 16;
		}
		if( strFieldName.equals("CessionRate") ) {
			return 17;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return 18;
		}
		if( strFieldName.equals("CessPrem") ) {
			return 19;
		}
		if( strFieldName.equals("Time") ) {
			return 20;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return 21;
		}
		if( strFieldName.equals("ShrePrem") ) {
			return 22;
		}
		if( strFieldName.equals("LeftRiskAmnt") ) {
			return 23;
		}
		if( strFieldName.equals("LeftPrem") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
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
				strFieldName = "CalculateYear";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "GrppolNo";
				break;
			case 3:
				strFieldName = "ReinsureCom";
				break;
			case 4:
				strFieldName = "PolState";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "RiskCalCode";
				break;
			case 7:
				strFieldName = "SaleChnl";
				break;
			case 8:
				strFieldName = "SignDate";
				break;
			case 9:
				strFieldName = "Cvalidate";
				break;
			case 10:
				strFieldName = "InsuredNo";
				break;
			case 11:
				strFieldName = "InsuredName";
				break;
			case 12:
				strFieldName = "InsuredSex";
				break;
			case 13:
				strFieldName = "InsuredIDNo";
				break;
			case 14:
				strFieldName = "Prem";
				break;
			case 15:
				strFieldName = "Amnt";
				break;
			case 16:
				strFieldName = "EndDate";
				break;
			case 17:
				strFieldName = "CessionRate";
				break;
			case 18:
				strFieldName = "CessionAmount";
				break;
			case 19:
				strFieldName = "CessPrem";
				break;
			case 20:
				strFieldName = "Time";
				break;
			case 21:
				strFieldName = "ChgPrem";
				break;
			case 22:
				strFieldName = "ShrePrem";
				break;
			case 23:
				strFieldName = "LeftRiskAmnt";
				break;
			case 24:
				strFieldName = "LeftPrem";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
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
		if( strFieldName.equals("CalculateYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrppolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Cvalidate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CessionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Time") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ShrePrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LeftRiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LeftPrem") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
