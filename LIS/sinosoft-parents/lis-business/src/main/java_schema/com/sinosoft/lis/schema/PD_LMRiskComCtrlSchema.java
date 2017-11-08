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
import com.sinosoft.lis.db.PD_LMRiskComCtrlDB;

/*
 * <p>ClassName: PD_LMRiskComCtrlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class PD_LMRiskComCtrlSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 管理机构 */
	private String ManageComGrp;
	/** 开办日期 */
	private Date StartDate;
	/** 停办日期 */
	private Date EndDate;
	/** 销售保额上限 */
	private double MAXAmnt;
	/** 销售份数上限 */
	private double MAXMult;
	/** 销售保费上限 */
	private double MAXPrem;
	/** 销售保额下限 */
	private double MINAmnt;
	/** 销售份数下限 */
	private double MINMult;
	/** 销售保费下限 */
	private double MINPrem;
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
	/** 销售渠道 */
	private String SaleChnl;
	/** 币种 */
	private String Currency;
	/** Lis起售日期 */
	private Date LISStartDate;
	/** Lis停售日期 */
	private Date LISEndDate;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskComCtrlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "ManageComGrp";
		pk[2] = "SaleChnl";
		pk[3] = "Currency";

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
		PD_LMRiskComCtrlSchema cloned = (PD_LMRiskComCtrlSchema)super.clone();
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
		RiskCode = aRiskCode;
	}
	public String getManageComGrp()
	{
		return ManageComGrp;
	}
	public void setManageComGrp(String aManageComGrp)
	{
		ManageComGrp = aManageComGrp;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
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

	public double getMAXAmnt()
	{
		return MAXAmnt;
	}
	public void setMAXAmnt(double aMAXAmnt)
	{
		MAXAmnt = aMAXAmnt;
	}
	public void setMAXAmnt(String aMAXAmnt)
	{
		if (aMAXAmnt != null && !aMAXAmnt.equals(""))
		{
			Double tDouble = new Double(aMAXAmnt);
			double d = tDouble.doubleValue();
			MAXAmnt = d;
		}
	}

	public double getMAXMult()
	{
		return MAXMult;
	}
	public void setMAXMult(double aMAXMult)
	{
		MAXMult = aMAXMult;
	}
	public void setMAXMult(String aMAXMult)
	{
		if (aMAXMult != null && !aMAXMult.equals(""))
		{
			Double tDouble = new Double(aMAXMult);
			double d = tDouble.doubleValue();
			MAXMult = d;
		}
	}

	public double getMAXPrem()
	{
		return MAXPrem;
	}
	public void setMAXPrem(double aMAXPrem)
	{
		MAXPrem = aMAXPrem;
	}
	public void setMAXPrem(String aMAXPrem)
	{
		if (aMAXPrem != null && !aMAXPrem.equals(""))
		{
			Double tDouble = new Double(aMAXPrem);
			double d = tDouble.doubleValue();
			MAXPrem = d;
		}
	}

	public double getMINAmnt()
	{
		return MINAmnt;
	}
	public void setMINAmnt(double aMINAmnt)
	{
		MINAmnt = aMINAmnt;
	}
	public void setMINAmnt(String aMINAmnt)
	{
		if (aMINAmnt != null && !aMINAmnt.equals(""))
		{
			Double tDouble = new Double(aMINAmnt);
			double d = tDouble.doubleValue();
			MINAmnt = d;
		}
	}

	public double getMINMult()
	{
		return MINMult;
	}
	public void setMINMult(double aMINMult)
	{
		MINMult = aMINMult;
	}
	public void setMINMult(String aMINMult)
	{
		if (aMINMult != null && !aMINMult.equals(""))
		{
			Double tDouble = new Double(aMINMult);
			double d = tDouble.doubleValue();
			MINMult = d;
		}
	}

	public double getMINPrem()
	{
		return MINPrem;
	}
	public void setMINPrem(double aMINPrem)
	{
		MINPrem = aMINPrem;
	}
	public void setMINPrem(String aMINPrem)
	{
		if (aMINPrem != null && !aMINPrem.equals(""))
		{
			Double tDouble = new Double(aMINPrem);
			double d = tDouble.doubleValue();
			MINPrem = d;
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
	* 中银人寿：<p>
	* 销售渠道：<p>
	* 01--Bancassurance<p>
	* 02--Brokerage<p>
	* 03--Telemarketing<p>
	* 04--Corporate Distribution*
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
	* 中银人寿<p>
	* 币种:<p>
	* 01--人民币<p>
	* 13--港币<p>
	* 14--美元*
	*/
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getLISStartDate()
	{
		if( LISStartDate != null )
			return fDate.getString(LISStartDate);
		else
			return null;
	}
	public void setLISStartDate(Date aLISStartDate)
	{
		LISStartDate = aLISStartDate;
	}
	public void setLISStartDate(String aLISStartDate)
	{
		if (aLISStartDate != null && !aLISStartDate.equals("") )
		{
			LISStartDate = fDate.getDate( aLISStartDate );
		}
		else
			LISStartDate = null;
	}

	public String getLISEndDate()
	{
		if( LISEndDate != null )
			return fDate.getString(LISEndDate);
		else
			return null;
	}
	public void setLISEndDate(Date aLISEndDate)
	{
		LISEndDate = aLISEndDate;
	}
	public void setLISEndDate(String aLISEndDate)
	{
		if (aLISEndDate != null && !aLISEndDate.equals("") )
		{
			LISEndDate = fDate.getDate( aLISEndDate );
		}
		else
			LISEndDate = null;
	}


	/**
	* 使用另外一个 PD_LMRiskComCtrlSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskComCtrlSchema PD_LMRiskComCtrlSchema
	**/
	public void setSchema(PD_LMRiskComCtrlSchema aPD_LMRiskComCtrlSchema)
	{
		this.RiskCode = aPD_LMRiskComCtrlSchema.getRiskCode();
		this.ManageComGrp = aPD_LMRiskComCtrlSchema.getManageComGrp();
		this.StartDate = fDate.getDate( aPD_LMRiskComCtrlSchema.getStartDate());
		this.EndDate = fDate.getDate( aPD_LMRiskComCtrlSchema.getEndDate());
		this.MAXAmnt = aPD_LMRiskComCtrlSchema.getMAXAmnt();
		this.MAXMult = aPD_LMRiskComCtrlSchema.getMAXMult();
		this.MAXPrem = aPD_LMRiskComCtrlSchema.getMAXPrem();
		this.MINAmnt = aPD_LMRiskComCtrlSchema.getMINAmnt();
		this.MINMult = aPD_LMRiskComCtrlSchema.getMINMult();
		this.MINPrem = aPD_LMRiskComCtrlSchema.getMINPrem();
		this.Operator = aPD_LMRiskComCtrlSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskComCtrlSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskComCtrlSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskComCtrlSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskComCtrlSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMRiskComCtrlSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMRiskComCtrlSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMRiskComCtrlSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMRiskComCtrlSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMRiskComCtrlSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMRiskComCtrlSchema.getStandbyflag6();
		this.SaleChnl = aPD_LMRiskComCtrlSchema.getSaleChnl();
		this.Currency = aPD_LMRiskComCtrlSchema.getCurrency();
		this.LISStartDate = fDate.getDate( aPD_LMRiskComCtrlSchema.getLISStartDate());
		this.LISEndDate = fDate.getDate( aPD_LMRiskComCtrlSchema.getLISEndDate());
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

			if( rs.getString("ManageComGrp") == null )
				this.ManageComGrp = null;
			else
				this.ManageComGrp = rs.getString("ManageComGrp").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.MAXAmnt = rs.getDouble("MAXAmnt");
			this.MAXMult = rs.getDouble("MAXMult");
			this.MAXPrem = rs.getDouble("MAXPrem");
			this.MINAmnt = rs.getDouble("MINAmnt");
			this.MINMult = rs.getDouble("MINMult");
			this.MINPrem = rs.getDouble("MINPrem");
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
			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.LISStartDate = rs.getDate("LISStartDate");
			this.LISEndDate = rs.getDate("LISEndDate");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMRiskComCtrl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskComCtrlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskComCtrlSchema getSchema()
	{
		PD_LMRiskComCtrlSchema aPD_LMRiskComCtrlSchema = new PD_LMRiskComCtrlSchema();
		aPD_LMRiskComCtrlSchema.setSchema(this);
		return aPD_LMRiskComCtrlSchema;
	}

	public PD_LMRiskComCtrlDB getDB()
	{
		PD_LMRiskComCtrlDB aDBOper = new PD_LMRiskComCtrlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskComCtrl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageComGrp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MAXAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MAXMult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MAXPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MINAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MINMult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MINPrem));strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LISStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LISEndDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskComCtrl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ManageComGrp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			MAXAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			MAXMult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			MAXPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			MINAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			MINMult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			MINPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			LISStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			LISEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskComCtrlSchema";
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
		if (FCode.equalsIgnoreCase("ManageComGrp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageComGrp));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("MAXAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXAmnt));
		}
		if (FCode.equalsIgnoreCase("MAXMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXMult));
		}
		if (FCode.equalsIgnoreCase("MAXPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXPrem));
		}
		if (FCode.equalsIgnoreCase("MINAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MINAmnt));
		}
		if (FCode.equalsIgnoreCase("MINMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MINMult));
		}
		if (FCode.equalsIgnoreCase("MINPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MINPrem));
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("LISStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLISStartDate()));
		}
		if (FCode.equalsIgnoreCase("LISEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLISEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(ManageComGrp);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 4:
				strFieldValue = String.valueOf(MAXAmnt);
				break;
			case 5:
				strFieldValue = String.valueOf(MAXMult);
				break;
			case 6:
				strFieldValue = String.valueOf(MAXPrem);
				break;
			case 7:
				strFieldValue = String.valueOf(MINAmnt);
				break;
			case 8:
				strFieldValue = String.valueOf(MINMult);
				break;
			case 9:
				strFieldValue = String.valueOf(MINPrem);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 17:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 18:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 19:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 20:
				strFieldValue = String.valueOf(Standbyflag6);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLISStartDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLISEndDate()));
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
		if (FCode.equalsIgnoreCase("ManageComGrp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageComGrp = FValue.trim();
			}
			else
				ManageComGrp = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
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
		if (FCode.equalsIgnoreCase("MAXAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MAXAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("MAXMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MAXMult = d;
			}
		}
		if (FCode.equalsIgnoreCase("MAXPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MAXPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("MINAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MINAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("MINMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MINMult = d;
			}
		}
		if (FCode.equalsIgnoreCase("MINPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MINPrem = d;
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("LISStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LISStartDate = fDate.getDate( FValue );
			}
			else
				LISStartDate = null;
		}
		if (FCode.equalsIgnoreCase("LISEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LISEndDate = fDate.getDate( FValue );
			}
			else
				LISEndDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMRiskComCtrlSchema other = (PD_LMRiskComCtrlSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ManageComGrp.equals(other.getManageComGrp())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& MAXAmnt == other.getMAXAmnt()
			&& MAXMult == other.getMAXMult()
			&& MAXPrem == other.getMAXPrem()
			&& MINAmnt == other.getMINAmnt()
			&& MINMult == other.getMINMult()
			&& MINPrem == other.getMINPrem()
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
			&& SaleChnl.equals(other.getSaleChnl())
			&& Currency.equals(other.getCurrency())
			&& fDate.getString(LISStartDate).equals(other.getLISStartDate())
			&& fDate.getString(LISEndDate).equals(other.getLISEndDate());
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
		if( strFieldName.equals("ManageComGrp") ) {
			return 1;
		}
		if( strFieldName.equals("StartDate") ) {
			return 2;
		}
		if( strFieldName.equals("EndDate") ) {
			return 3;
		}
		if( strFieldName.equals("MAXAmnt") ) {
			return 4;
		}
		if( strFieldName.equals("MAXMult") ) {
			return 5;
		}
		if( strFieldName.equals("MAXPrem") ) {
			return 6;
		}
		if( strFieldName.equals("MINAmnt") ) {
			return 7;
		}
		if( strFieldName.equals("MINMult") ) {
			return 8;
		}
		if( strFieldName.equals("MINPrem") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 15;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 16;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 20;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 21;
		}
		if( strFieldName.equals("Currency") ) {
			return 22;
		}
		if( strFieldName.equals("LISStartDate") ) {
			return 23;
		}
		if( strFieldName.equals("LISEndDate") ) {
			return 24;
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
				strFieldName = "ManageComGrp";
				break;
			case 2:
				strFieldName = "StartDate";
				break;
			case 3:
				strFieldName = "EndDate";
				break;
			case 4:
				strFieldName = "MAXAmnt";
				break;
			case 5:
				strFieldName = "MAXMult";
				break;
			case 6:
				strFieldName = "MAXPrem";
				break;
			case 7:
				strFieldName = "MINAmnt";
				break;
			case 8:
				strFieldName = "MINMult";
				break;
			case 9:
				strFieldName = "MINPrem";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
				strFieldName = "Standbyflag1";
				break;
			case 16:
				strFieldName = "Standbyflag2";
				break;
			case 17:
				strFieldName = "Standbyflag3";
				break;
			case 18:
				strFieldName = "Standbyflag4";
				break;
			case 19:
				strFieldName = "Standbyflag5";
				break;
			case 20:
				strFieldName = "Standbyflag6";
				break;
			case 21:
				strFieldName = "SaleChnl";
				break;
			case 22:
				strFieldName = "Currency";
				break;
			case 23:
				strFieldName = "LISStartDate";
				break;
			case 24:
				strFieldName = "LISEndDate";
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
		if( strFieldName.equals("ManageComGrp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MAXAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MAXMult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MAXPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MINAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MINMult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MINPrem") ) {
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
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LISStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LISEndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
