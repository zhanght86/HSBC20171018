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
import com.sinosoft.lis.db.PD_LMEdorZT1DB;

/*
 * <p>ClassName: PD_LMEdorZT1Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMEdorZT1Schema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMEdorZT1Schema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 退保计算类型 */
	private String SurrCalType;
	/** 期缴时间间隔 */
	private String CycPayIntvType;
	/** 算法编码 */
	private String CycPayCalCode;
	/** 退保生存金计算类型 */
	private String LiveGetType;
	/** 死亡退保金计算类型 */
	private String DeadGetType;
	/** 计算方式参考 */
	private String CalCodeType;
	/** 退保年度计算类型 */
	private String ZTYearType;
	/** 趸交算法编码（备用） */
	private String OnePayCalCode;
	/** 趸缴时间间隔（备用） */
	private String OnePayIntvType;
	/** 备用 */
	private String OutGetType;
	/** 现金价值计算公式（备用） */
	private String CashValueCode;
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

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMEdorZT1Schema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "PayPlanCode";
		pk[3] = "SurrCalType";
		pk[4] = "CycPayIntvType";

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
		PD_LMEdorZT1Schema cloned = (PD_LMEdorZT1Schema)super.clone();
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
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getSurrCalType()
	{
		return SurrCalType;
	}
	public void setSurrCalType(String aSurrCalType)
	{
		SurrCalType = aSurrCalType;
	}
	public String getCycPayIntvType()
	{
		return CycPayIntvType;
	}
	public void setCycPayIntvType(String aCycPayIntvType)
	{
		CycPayIntvType = aCycPayIntvType;
	}
	public String getCycPayCalCode()
	{
		return CycPayCalCode;
	}
	public void setCycPayCalCode(String aCycPayCalCode)
	{
		CycPayCalCode = aCycPayCalCode;
	}
	public String getLiveGetType()
	{
		return LiveGetType;
	}
	public void setLiveGetType(String aLiveGetType)
	{
		LiveGetType = aLiveGetType;
	}
	public String getDeadGetType()
	{
		return DeadGetType;
	}
	public void setDeadGetType(String aDeadGetType)
	{
		DeadGetType = aDeadGetType;
	}
	public String getCalCodeType()
	{
		return CalCodeType;
	}
	public void setCalCodeType(String aCalCodeType)
	{
		CalCodeType = aCalCodeType;
	}
	public String getZTYearType()
	{
		return ZTYearType;
	}
	public void setZTYearType(String aZTYearType)
	{
		ZTYearType = aZTYearType;
	}
	public String getOnePayCalCode()
	{
		return OnePayCalCode;
	}
	public void setOnePayCalCode(String aOnePayCalCode)
	{
		OnePayCalCode = aOnePayCalCode;
	}
	public String getOnePayIntvType()
	{
		return OnePayIntvType;
	}
	public void setOnePayIntvType(String aOnePayIntvType)
	{
		OnePayIntvType = aOnePayIntvType;
	}
	public String getOutGetType()
	{
		return OutGetType;
	}
	public void setOutGetType(String aOutGetType)
	{
		OutGetType = aOutGetType;
	}
	public String getCashValueCode()
	{
		return CashValueCode;
	}
	public void setCashValueCode(String aCashValueCode)
	{
		CashValueCode = aCashValueCode;
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
	* 使用另外一个 PD_LMEdorZT1Schema 对象给 Schema 赋值
	* @param: aPD_LMEdorZT1Schema PD_LMEdorZT1Schema
	**/
	public void setSchema(PD_LMEdorZT1Schema aPD_LMEdorZT1Schema)
	{
		this.RiskCode = aPD_LMEdorZT1Schema.getRiskCode();
		this.DutyCode = aPD_LMEdorZT1Schema.getDutyCode();
		this.PayPlanCode = aPD_LMEdorZT1Schema.getPayPlanCode();
		this.SurrCalType = aPD_LMEdorZT1Schema.getSurrCalType();
		this.CycPayIntvType = aPD_LMEdorZT1Schema.getCycPayIntvType();
		this.CycPayCalCode = aPD_LMEdorZT1Schema.getCycPayCalCode();
		this.LiveGetType = aPD_LMEdorZT1Schema.getLiveGetType();
		this.DeadGetType = aPD_LMEdorZT1Schema.getDeadGetType();
		this.CalCodeType = aPD_LMEdorZT1Schema.getCalCodeType();
		this.ZTYearType = aPD_LMEdorZT1Schema.getZTYearType();
		this.OnePayCalCode = aPD_LMEdorZT1Schema.getOnePayCalCode();
		this.OnePayIntvType = aPD_LMEdorZT1Schema.getOnePayIntvType();
		this.OutGetType = aPD_LMEdorZT1Schema.getOutGetType();
		this.CashValueCode = aPD_LMEdorZT1Schema.getCashValueCode();
		this.Operator = aPD_LMEdorZT1Schema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMEdorZT1Schema.getMakeDate());
		this.MakeTime = aPD_LMEdorZT1Schema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMEdorZT1Schema.getModifyDate());
		this.ModifyTime = aPD_LMEdorZT1Schema.getModifyTime();
		this.Standbyflag1 = aPD_LMEdorZT1Schema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMEdorZT1Schema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMEdorZT1Schema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMEdorZT1Schema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMEdorZT1Schema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMEdorZT1Schema.getStandbyflag6();
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

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("SurrCalType") == null )
				this.SurrCalType = null;
			else
				this.SurrCalType = rs.getString("SurrCalType").trim();

			if( rs.getString("CycPayIntvType") == null )
				this.CycPayIntvType = null;
			else
				this.CycPayIntvType = rs.getString("CycPayIntvType").trim();

			if( rs.getString("CycPayCalCode") == null )
				this.CycPayCalCode = null;
			else
				this.CycPayCalCode = rs.getString("CycPayCalCode").trim();

			if( rs.getString("LiveGetType") == null )
				this.LiveGetType = null;
			else
				this.LiveGetType = rs.getString("LiveGetType").trim();

			if( rs.getString("DeadGetType") == null )
				this.DeadGetType = null;
			else
				this.DeadGetType = rs.getString("DeadGetType").trim();

			if( rs.getString("CalCodeType") == null )
				this.CalCodeType = null;
			else
				this.CalCodeType = rs.getString("CalCodeType").trim();

			if( rs.getString("ZTYearType") == null )
				this.ZTYearType = null;
			else
				this.ZTYearType = rs.getString("ZTYearType").trim();

			if( rs.getString("OnePayCalCode") == null )
				this.OnePayCalCode = null;
			else
				this.OnePayCalCode = rs.getString("OnePayCalCode").trim();

			if( rs.getString("OnePayIntvType") == null )
				this.OnePayIntvType = null;
			else
				this.OnePayIntvType = rs.getString("OnePayIntvType").trim();

			if( rs.getString("OutGetType") == null )
				this.OutGetType = null;
			else
				this.OutGetType = rs.getString("OutGetType").trim();

			if( rs.getString("CashValueCode") == null )
				this.CashValueCode = null;
			else
				this.CashValueCode = rs.getString("CashValueCode").trim();

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
			logger.debug("数据库中的PD_LMEdorZT1表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1Schema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMEdorZT1Schema getSchema()
	{
		PD_LMEdorZT1Schema aPD_LMEdorZT1Schema = new PD_LMEdorZT1Schema();
		aPD_LMEdorZT1Schema.setSchema(this);
		return aPD_LMEdorZT1Schema;
	}

	public PD_LMEdorZT1DB getDB()
	{
		PD_LMEdorZT1DB aDBOper = new PD_LMEdorZT1DB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMEdorZT1描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurrCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayIntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZTYearType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OnePayCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OnePayIntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CashValueCode)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMEdorZT1>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SurrCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CycPayIntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CycPayCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LiveGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DeadGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ZTYearType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OnePayCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OnePayIntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OutGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CashValueCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1Schema";
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("SurrCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurrCalType));
		}
		if (FCode.equalsIgnoreCase("CycPayIntvType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycPayIntvType));
		}
		if (FCode.equalsIgnoreCase("CycPayCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycPayCalCode));
		}
		if (FCode.equalsIgnoreCase("LiveGetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveGetType));
		}
		if (FCode.equalsIgnoreCase("DeadGetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadGetType));
		}
		if (FCode.equalsIgnoreCase("CalCodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeType));
		}
		if (FCode.equalsIgnoreCase("ZTYearType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZTYearType));
		}
		if (FCode.equalsIgnoreCase("OnePayCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayCalCode));
		}
		if (FCode.equalsIgnoreCase("OnePayIntvType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayIntvType));
		}
		if (FCode.equalsIgnoreCase("OutGetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutGetType));
		}
		if (FCode.equalsIgnoreCase("CashValueCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CashValueCode));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SurrCalType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CycPayIntvType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CycPayCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LiveGetType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DeadGetType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCodeType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ZTYearType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(OnePayCalCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(OnePayIntvType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OutGetType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CashValueCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 21:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 22:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 23:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 24:
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("SurrCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurrCalType = FValue.trim();
			}
			else
				SurrCalType = null;
		}
		if (FCode.equalsIgnoreCase("CycPayIntvType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycPayIntvType = FValue.trim();
			}
			else
				CycPayIntvType = null;
		}
		if (FCode.equalsIgnoreCase("CycPayCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycPayCalCode = FValue.trim();
			}
			else
				CycPayCalCode = null;
		}
		if (FCode.equalsIgnoreCase("LiveGetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveGetType = FValue.trim();
			}
			else
				LiveGetType = null;
		}
		if (FCode.equalsIgnoreCase("DeadGetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadGetType = FValue.trim();
			}
			else
				DeadGetType = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeType = FValue.trim();
			}
			else
				CalCodeType = null;
		}
		if (FCode.equalsIgnoreCase("ZTYearType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZTYearType = FValue.trim();
			}
			else
				ZTYearType = null;
		}
		if (FCode.equalsIgnoreCase("OnePayCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OnePayCalCode = FValue.trim();
			}
			else
				OnePayCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OnePayIntvType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OnePayIntvType = FValue.trim();
			}
			else
				OnePayIntvType = null;
		}
		if (FCode.equalsIgnoreCase("OutGetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutGetType = FValue.trim();
			}
			else
				OutGetType = null;
		}
		if (FCode.equalsIgnoreCase("CashValueCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CashValueCode = FValue.trim();
			}
			else
				CashValueCode = null;
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
		PD_LMEdorZT1Schema other = (PD_LMEdorZT1Schema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& SurrCalType.equals(other.getSurrCalType())
			&& CycPayIntvType.equals(other.getCycPayIntvType())
			&& CycPayCalCode.equals(other.getCycPayCalCode())
			&& LiveGetType.equals(other.getLiveGetType())
			&& DeadGetType.equals(other.getDeadGetType())
			&& CalCodeType.equals(other.getCalCodeType())
			&& ZTYearType.equals(other.getZTYearType())
			&& OnePayCalCode.equals(other.getOnePayCalCode())
			&& OnePayIntvType.equals(other.getOnePayIntvType())
			&& OutGetType.equals(other.getOutGetType())
			&& CashValueCode.equals(other.getCashValueCode())
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 1;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("SurrCalType") ) {
			return 3;
		}
		if( strFieldName.equals("CycPayIntvType") ) {
			return 4;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return 6;
		}
		if( strFieldName.equals("DeadGetType") ) {
			return 7;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return 8;
		}
		if( strFieldName.equals("ZTYearType") ) {
			return 9;
		}
		if( strFieldName.equals("OnePayCalCode") ) {
			return 10;
		}
		if( strFieldName.equals("OnePayIntvType") ) {
			return 11;
		}
		if( strFieldName.equals("OutGetType") ) {
			return 12;
		}
		if( strFieldName.equals("CashValueCode") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 22;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 23;
		}
		if( strFieldName.equals("Standbyflag6") ) {
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
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "PayPlanCode";
				break;
			case 3:
				strFieldName = "SurrCalType";
				break;
			case 4:
				strFieldName = "CycPayIntvType";
				break;
			case 5:
				strFieldName = "CycPayCalCode";
				break;
			case 6:
				strFieldName = "LiveGetType";
				break;
			case 7:
				strFieldName = "DeadGetType";
				break;
			case 8:
				strFieldName = "CalCodeType";
				break;
			case 9:
				strFieldName = "ZTYearType";
				break;
			case 10:
				strFieldName = "OnePayCalCode";
				break;
			case 11:
				strFieldName = "OnePayIntvType";
				break;
			case 12:
				strFieldName = "OutGetType";
				break;
			case 13:
				strFieldName = "CashValueCode";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "Standbyflag1";
				break;
			case 20:
				strFieldName = "Standbyflag2";
				break;
			case 21:
				strFieldName = "Standbyflag3";
				break;
			case 22:
				strFieldName = "Standbyflag4";
				break;
			case 23:
				strFieldName = "Standbyflag5";
				break;
			case 24:
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurrCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycPayIntvType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadGetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZTYearType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnePayCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnePayIntvType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutGetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CashValueCode") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
