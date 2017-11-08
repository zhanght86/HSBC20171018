

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
import com.sinosoft.lis.db.PD_LMRiskAccPayDB;

/*
 * <p>ClassName: PD_LMRiskAccPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMRiskAccPaySchema implements Schema, Cloneable
{
	// @Field
	/** 缴费编码 */
	private String PayPlanCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 默认比例 */
	private double DefaultRate;
	/** 是否需要录入 */
	private String NeedInput;
	/** 账户产生位置 */
	private String AccCreatePos;
	/** 转入账户时的算法编码(现金) */
	private String CalCodeMoney;
	/** 转入账户时的算法编码(股份) */
	private String CalCodeUnit;
	/** 账户转入计算标志 */
	private String CalFlag;
	/** 缴费名称 */
	private String PayPlanName;
	/** 账户交费转入位置 */
	private String PayNeedToAcc;
	/** 险种帐户交费名 */
	private String RiskAccPayName;
	/** 是否归属标记 */
	private String ascription;
	/** 投资比例下限 */
	private double InvestMinRate;
	/** 投资比例上限 */
	private double InvestMaxRate;
	/** 投资比例 */
	private double InvestRate;
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

	public static final int FIELDNUM = 28;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskAccPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "PayPlanCode";
		pk[1] = "InsuAccNo";
		pk[2] = "RiskCode";

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
		PD_LMRiskAccPaySchema cloned = (PD_LMRiskAccPaySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	/**
	* 在录入界面中的默认显示比例。
	*/
	public double getDefaultRate()
	{
		return DefaultRate;
	}
	public void setDefaultRate(double aDefaultRate)
	{
		DefaultRate = aDefaultRate;
	}
	public void setDefaultRate(String aDefaultRate)
	{
		if (aDefaultRate != null && !aDefaultRate.equals(""))
		{
			Double tDouble = new Double(aDefaultRate);
			double d = tDouble.doubleValue();
			DefaultRate = d;
		}
	}

	/**
	* 0 －－ 和前台录入无关。 1 －－ 必须从前台录入，如果不录，报错。
	*/
	public String getNeedInput()
	{
		return NeedInput;
	}
	public void setNeedInput(String aNeedInput)
	{
		NeedInput = aNeedInput;
	}
	/**
	* 1 －－投保单录入时产生 2 －－缴费时产生 3 －－领取时产生 4 －－第一次账户缴费时产生
	*/
	public String getAccCreatePos()
	{
		return AccCreatePos;
	}
	public void setAccCreatePos(String aAccCreatePos)
	{
		AccCreatePos = aAccCreatePos;
	}
	public String getCalCodeMoney()
	{
		return CalCodeMoney;
	}
	public void setCalCodeMoney(String aCalCodeMoney)
	{
		CalCodeMoney = aCalCodeMoney;
	}
	public String getCalCodeUnit()
	{
		return CalCodeUnit;
	}
	public void setCalCodeUnit(String aCalCodeUnit)
	{
		CalCodeUnit = aCalCodeUnit;
	}
	/**
	* 判断使用那种算法进行计算。 0 －－ 完全转入账户 1 －－ 安现金计算转入账户 2 －－ 安股份计算转入账户 3 －－ 先算现金，然后按股份计算。
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	public String getPayPlanName()
	{
		return PayPlanName;
	}
	public void setPayPlanName(String aPayPlanName)
	{
		PayPlanName = aPayPlanName;
	}
	/**
	* 1 －－ 表示立刻转入 0 －－ 表示不立刻转入
	*/
	public String getPayNeedToAcc()
	{
		return PayNeedToAcc;
	}
	public void setPayNeedToAcc(String aPayNeedToAcc)
	{
		PayNeedToAcc = aPayNeedToAcc;
	}
	/**
	* 对应于险种账户分类的名称
	*/
	public String getRiskAccPayName()
	{
		return RiskAccPayName;
	}
	public void setRiskAccPayName(String aRiskAccPayName)
	{
		RiskAccPayName = aRiskAccPayName;
	}
	/**
	* 0：表示初试未归属 1：表示初试归属
	*/
	public String getascription()
	{
		return ascription;
	}
	public void setascription(String aascription)
	{
		ascription = aascription;
	}
	/**
	* 增加险种级投资计划设置；用于描述条款中指定投资计划分配比例的情况 或者描述单一投资账户的险种,一般为个险产品
	*/
	public double getInvestMinRate()
	{
		return InvestMinRate;
	}
	public void setInvestMinRate(double aInvestMinRate)
	{
		InvestMinRate = aInvestMinRate;
	}
	public void setInvestMinRate(String aInvestMinRate)
	{
		if (aInvestMinRate != null && !aInvestMinRate.equals(""))
		{
			Double tDouble = new Double(aInvestMinRate);
			double d = tDouble.doubleValue();
			InvestMinRate = d;
		}
	}

	public double getInvestMaxRate()
	{
		return InvestMaxRate;
	}
	public void setInvestMaxRate(double aInvestMaxRate)
	{
		InvestMaxRate = aInvestMaxRate;
	}
	public void setInvestMaxRate(String aInvestMaxRate)
	{
		if (aInvestMaxRate != null && !aInvestMaxRate.equals(""))
		{
			Double tDouble = new Double(aInvestMaxRate);
			double d = tDouble.doubleValue();
			InvestMaxRate = d;
		}
	}

	public double getInvestRate()
	{
		return InvestRate;
	}
	public void setInvestRate(double aInvestRate)
	{
		InvestRate = aInvestRate;
	}
	public void setInvestRate(String aInvestRate)
	{
		if (aInvestRate != null && !aInvestRate.equals(""))
		{
			Double tDouble = new Double(aInvestRate);
			double d = tDouble.doubleValue();
			InvestRate = d;
		}
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
	* 使用另外一个 PD_LMRiskAccPaySchema 对象给 Schema 赋值
	* @param: aPD_LMRiskAccPaySchema PD_LMRiskAccPaySchema
	**/
	public void setSchema(PD_LMRiskAccPaySchema aPD_LMRiskAccPaySchema)
	{
		this.PayPlanCode = aPD_LMRiskAccPaySchema.getPayPlanCode();
		this.InsuAccNo = aPD_LMRiskAccPaySchema.getInsuAccNo();
		this.RiskCode = aPD_LMRiskAccPaySchema.getRiskCode();
		this.RiskVer = aPD_LMRiskAccPaySchema.getRiskVer();
		this.DefaultRate = aPD_LMRiskAccPaySchema.getDefaultRate();
		this.NeedInput = aPD_LMRiskAccPaySchema.getNeedInput();
		this.AccCreatePos = aPD_LMRiskAccPaySchema.getAccCreatePos();
		this.CalCodeMoney = aPD_LMRiskAccPaySchema.getCalCodeMoney();
		this.CalCodeUnit = aPD_LMRiskAccPaySchema.getCalCodeUnit();
		this.CalFlag = aPD_LMRiskAccPaySchema.getCalFlag();
		this.PayPlanName = aPD_LMRiskAccPaySchema.getPayPlanName();
		this.PayNeedToAcc = aPD_LMRiskAccPaySchema.getPayNeedToAcc();
		this.RiskAccPayName = aPD_LMRiskAccPaySchema.getRiskAccPayName();
		this.ascription = aPD_LMRiskAccPaySchema.getascription();
		this.InvestMinRate = aPD_LMRiskAccPaySchema.getInvestMinRate();
		this.InvestMaxRate = aPD_LMRiskAccPaySchema.getInvestMaxRate();
		this.InvestRate = aPD_LMRiskAccPaySchema.getInvestRate();
		this.Operator = aPD_LMRiskAccPaySchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskAccPaySchema.getMakeDate());
		this.MakeTime = aPD_LMRiskAccPaySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskAccPaySchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskAccPaySchema.getModifyTime();
		this.Standbyflag1 = aPD_LMRiskAccPaySchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMRiskAccPaySchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMRiskAccPaySchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMRiskAccPaySchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMRiskAccPaySchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMRiskAccPaySchema.getStandbyflag6();
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
			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			this.DefaultRate = rs.getDouble("DefaultRate");
			if( rs.getString("NeedInput") == null )
				this.NeedInput = null;
			else
				this.NeedInput = rs.getString("NeedInput").trim();

			if( rs.getString("AccCreatePos") == null )
				this.AccCreatePos = null;
			else
				this.AccCreatePos = rs.getString("AccCreatePos").trim();

			if( rs.getString("CalCodeMoney") == null )
				this.CalCodeMoney = null;
			else
				this.CalCodeMoney = rs.getString("CalCodeMoney").trim();

			if( rs.getString("CalCodeUnit") == null )
				this.CalCodeUnit = null;
			else
				this.CalCodeUnit = rs.getString("CalCodeUnit").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("PayPlanName") == null )
				this.PayPlanName = null;
			else
				this.PayPlanName = rs.getString("PayPlanName").trim();

			if( rs.getString("PayNeedToAcc") == null )
				this.PayNeedToAcc = null;
			else
				this.PayNeedToAcc = rs.getString("PayNeedToAcc").trim();

			if( rs.getString("RiskAccPayName") == null )
				this.RiskAccPayName = null;
			else
				this.RiskAccPayName = rs.getString("RiskAccPayName").trim();

			if( rs.getString("ascription") == null )
				this.ascription = null;
			else
				this.ascription = rs.getString("ascription").trim();

			this.InvestMinRate = rs.getDouble("InvestMinRate");
			this.InvestMaxRate = rs.getDouble("InvestMaxRate");
			this.InvestRate = rs.getDouble("InvestRate");
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
			System.out.println("数据库中的PD_LMRiskAccPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskAccPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskAccPaySchema getSchema()
	{
		PD_LMRiskAccPaySchema aPD_LMRiskAccPaySchema = new PD_LMRiskAccPaySchema();
		aPD_LMRiskAccPaySchema.setSchema(this);
		return aPD_LMRiskAccPaySchema;
	}

	public PD_LMRiskAccPayDB getDB()
	{
		PD_LMRiskAccPayDB aDBOper = new PD_LMRiskAccPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskAccPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedInput)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCreatePos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeMoney)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayNeedToAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAccPayName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ascription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMinRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMaxRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestRate));strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskAccPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DefaultRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			NeedInput = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccCreatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalCodeMoney = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCodeUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PayPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PayNeedToAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskAccPayName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ascription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InvestMinRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMaxRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			InvestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskAccPaySchema";
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("DefaultRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultRate));
		}
		if (FCode.equalsIgnoreCase("NeedInput"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedInput));
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCreatePos));
		}
		if (FCode.equalsIgnoreCase("CalCodeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeMoney));
		}
		if (FCode.equalsIgnoreCase("CalCodeUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeUnit));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanName));
		}
		if (FCode.equalsIgnoreCase("PayNeedToAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayNeedToAcc));
		}
		if (FCode.equalsIgnoreCase("RiskAccPayName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAccPayName));
		}
		if (FCode.equalsIgnoreCase("ascription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ascription));
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMinRate));
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMaxRate));
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRate));
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
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 4:
				strFieldValue = String.valueOf(DefaultRate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NeedInput);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccCreatePos);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCodeMoney);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCodeUnit);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PayPlanName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PayNeedToAcc);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskAccPayName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ascription);
				break;
			case 14:
				strFieldValue = String.valueOf(InvestMinRate);
				break;
			case 15:
				strFieldValue = String.valueOf(InvestMaxRate);
				break;
			case 16:
				strFieldValue = String.valueOf(InvestRate);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 24:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 25:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 26:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 27:
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

		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equalsIgnoreCase("DefaultRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("NeedInput"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedInput = FValue.trim();
			}
			else
				NeedInput = null;
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCreatePos = FValue.trim();
			}
			else
				AccCreatePos = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeMoney = FValue.trim();
			}
			else
				CalCodeMoney = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeUnit = FValue.trim();
			}
			else
				CalCodeUnit = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanName = FValue.trim();
			}
			else
				PayPlanName = null;
		}
		if (FCode.equalsIgnoreCase("PayNeedToAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayNeedToAcc = FValue.trim();
			}
			else
				PayNeedToAcc = null;
		}
		if (FCode.equalsIgnoreCase("RiskAccPayName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAccPayName = FValue.trim();
			}
			else
				RiskAccPayName = null;
		}
		if (FCode.equalsIgnoreCase("ascription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ascription = FValue.trim();
			}
			else
				ascription = null;
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMinRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMaxRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestRate = d;
			}
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
		PD_LMRiskAccPaySchema other = (PD_LMRiskAccPaySchema)otherObject;
		return
			PayPlanCode.equals(other.getPayPlanCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& DefaultRate == other.getDefaultRate()
			&& NeedInput.equals(other.getNeedInput())
			&& AccCreatePos.equals(other.getAccCreatePos())
			&& CalCodeMoney.equals(other.getCalCodeMoney())
			&& CalCodeUnit.equals(other.getCalCodeUnit())
			&& CalFlag.equals(other.getCalFlag())
			&& PayPlanName.equals(other.getPayPlanName())
			&& PayNeedToAcc.equals(other.getPayNeedToAcc())
			&& RiskAccPayName.equals(other.getRiskAccPayName())
			&& ascription.equals(other.getascription())
			&& InvestMinRate == other.getInvestMinRate()
			&& InvestMaxRate == other.getInvestMaxRate()
			&& InvestRate == other.getInvestRate()
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
		if( strFieldName.equals("PayPlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 3;
		}
		if( strFieldName.equals("DefaultRate") ) {
			return 4;
		}
		if( strFieldName.equals("NeedInput") ) {
			return 5;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return 6;
		}
		if( strFieldName.equals("CalCodeMoney") ) {
			return 7;
		}
		if( strFieldName.equals("CalCodeUnit") ) {
			return 8;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 9;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return 10;
		}
		if( strFieldName.equals("PayNeedToAcc") ) {
			return 11;
		}
		if( strFieldName.equals("RiskAccPayName") ) {
			return 12;
		}
		if( strFieldName.equals("ascription") ) {
			return 13;
		}
		if( strFieldName.equals("InvestMinRate") ) {
			return 14;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return 15;
		}
		if( strFieldName.equals("InvestRate") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 22;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 23;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 24;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 25;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 26;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 27;
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
				strFieldName = "PayPlanCode";
				break;
			case 1:
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "RiskVer";
				break;
			case 4:
				strFieldName = "DefaultRate";
				break;
			case 5:
				strFieldName = "NeedInput";
				break;
			case 6:
				strFieldName = "AccCreatePos";
				break;
			case 7:
				strFieldName = "CalCodeMoney";
				break;
			case 8:
				strFieldName = "CalCodeUnit";
				break;
			case 9:
				strFieldName = "CalFlag";
				break;
			case 10:
				strFieldName = "PayPlanName";
				break;
			case 11:
				strFieldName = "PayNeedToAcc";
				break;
			case 12:
				strFieldName = "RiskAccPayName";
				break;
			case 13:
				strFieldName = "ascription";
				break;
			case 14:
				strFieldName = "InvestMinRate";
				break;
			case 15:
				strFieldName = "InvestMaxRate";
				break;
			case 16:
				strFieldName = "InvestRate";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "Standbyflag1";
				break;
			case 23:
				strFieldName = "Standbyflag2";
				break;
			case 24:
				strFieldName = "Standbyflag3";
				break;
			case 25:
				strFieldName = "Standbyflag4";
				break;
			case 26:
				strFieldName = "Standbyflag5";
				break;
			case 27:
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
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NeedInput") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeMoney") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayNeedToAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAccPayName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ascription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestMinRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestRate") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
