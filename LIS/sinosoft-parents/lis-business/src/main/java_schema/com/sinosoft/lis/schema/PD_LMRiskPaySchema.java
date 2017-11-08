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
import com.sinosoft.lis.db.PD_LMRiskPayDB;

/*
 * <p>ClassName: PD_LMRiskPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMRiskPaySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMRiskPaySchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 催缴标记 */
	private String UrgePayFlag;
	/** 手续费类型 */
	private String ChargeType;
	/** 分解缴费间隔 */
	private String CutPayIntv;
	/** 免交涉及加费 */
	private String PayAvoidType;
	/** 手续费与保费关系 */
	private String ChargeAndPrem;
	/** 结算日类型 */
	private String BalaDateType;
	/** 免交标记 */
	private String PayAvoidFlag;
	/** 缴费与复效关系 */
	private String PayAndRevEffe;
	/** 缴费宽限期 */
	private int GracePeriod;
	/** 宽限期单位 */
	private String GracePeriodUnit;
	/** 宽限日期计算方式 */
	private String GraceDateCalMode;
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
	/** 宽限期算法 */
	private String GraceCalCode;
	/** 逾期处理方式 */
	private String OverdueDeal;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		PD_LMRiskPaySchema cloned = (PD_LMRiskPaySchema)super.clone();
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	/**
	* Y--催付、N--不催付
	*/
	public String getUrgePayFlag()
	{
		return UrgePayFlag;
	}
	public void setUrgePayFlag(String aUrgePayFlag)
	{
		UrgePayFlag = aUrgePayFlag;
	}
	/**
	* 1-假手续费 0 - 真手续费
	*/
	public String getChargeType()
	{
		return ChargeType;
	}
	public void setChargeType(String aChargeType)
	{
		ChargeType = aChargeType;
	}
	/**
	* N - 不可分解 (交费间隔不同计算保费方法不同)、Y - 可分解 (交费与间隔成倍数关系)
	*/
	public String getCutPayIntv()
	{
		return CutPayIntv;
	}
	public void setCutPayIntv(String aCutPayIntv)
	{
		CutPayIntv = aCutPayIntv;
	}
	/**
	* N-不涉及、Y-涉及加费(没用)
	*/
	public String getPayAvoidType()
	{
		return PayAvoidType;
	}
	public void setPayAvoidType(String aPayAvoidType)
	{
		PayAvoidType = aPayAvoidType;
	}
	/**
	* N-手续费不能冲减保费 Y-手续费可以冲减保费
	*/
	public String getChargeAndPrem()
	{
		return ChargeAndPrem;
	}
	public void setChargeAndPrem(String aChargeAndPrem)
	{
		ChargeAndPrem = aChargeAndPrem;
	}
	/**
	* 1-结算日为会计年度末 2-结算日为起保日期 （针对基金险）其他为操作日期
	*/
	public String getBalaDateType()
	{
		return BalaDateType;
	}
	public void setBalaDateType(String aBalaDateType)
	{
		BalaDateType = aBalaDateType;
	}
	/**
	* Y--能、N--不能
	*/
	public String getPayAvoidFlag()
	{
		return PayAvoidFlag;
	}
	public void setPayAvoidFlag(String aPayAvoidFlag)
	{
		PayAvoidFlag = aPayAvoidFlag;
	}
	/**
	* N 失效后不能交费必须作复效批改、Y 失效后可以交费不必作复效批改
	*/
	public String getPayAndRevEffe()
	{
		return PayAndRevEffe;
	}
	public void setPayAndRevEffe(String aPayAndRevEffe)
	{
		PayAndRevEffe = aPayAndRevEffe;
	}
	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* Y--年 M--月 D--日
	*/
	public String getGracePeriodUnit()
	{
		return GracePeriodUnit;
	}
	public void setGracePeriodUnit(String aGracePeriodUnit)
	{
		GracePeriodUnit = aGracePeriodUnit;
	}
	/**
	* 0 - 以计算为准、1 - 下月一号、2 - 下年一号
	*/
	public String getGraceDateCalMode()
	{
		return GraceDateCalMode;
	}
	public void setGraceDateCalMode(String aGraceDateCalMode)
	{
		GraceDateCalMode = aGraceDateCalMode;
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

	public String getGraceCalCode()
	{
		return GraceCalCode;
	}
	public void setGraceCalCode(String aGraceCalCode)
	{
		GraceCalCode = aGraceCalCode;
	}
	/**
	* L: 失效（Lapse） H：缓缴（Premiem Holiday） P: 缴清（Paid-up） R: 减额缴清（RPU） E: 展期（ETI） N: 不失效（Non-Lapse）
	*/
	public String getOverdueDeal()
	{
		return OverdueDeal;
	}
	public void setOverdueDeal(String aOverdueDeal)
	{
		OverdueDeal = aOverdueDeal;
	}

	/**
	* 使用另外一个 PD_LMRiskPaySchema 对象给 Schema 赋值
	* @param: aPD_LMRiskPaySchema PD_LMRiskPaySchema
	**/
	public void setSchema(PD_LMRiskPaySchema aPD_LMRiskPaySchema)
	{
		this.RiskCode = aPD_LMRiskPaySchema.getRiskCode();
		this.RiskVer = aPD_LMRiskPaySchema.getRiskVer();
		this.RiskName = aPD_LMRiskPaySchema.getRiskName();
		this.UrgePayFlag = aPD_LMRiskPaySchema.getUrgePayFlag();
		this.ChargeType = aPD_LMRiskPaySchema.getChargeType();
		this.CutPayIntv = aPD_LMRiskPaySchema.getCutPayIntv();
		this.PayAvoidType = aPD_LMRiskPaySchema.getPayAvoidType();
		this.ChargeAndPrem = aPD_LMRiskPaySchema.getChargeAndPrem();
		this.BalaDateType = aPD_LMRiskPaySchema.getBalaDateType();
		this.PayAvoidFlag = aPD_LMRiskPaySchema.getPayAvoidFlag();
		this.PayAndRevEffe = aPD_LMRiskPaySchema.getPayAndRevEffe();
		this.GracePeriod = aPD_LMRiskPaySchema.getGracePeriod();
		this.GracePeriodUnit = aPD_LMRiskPaySchema.getGracePeriodUnit();
		this.GraceDateCalMode = aPD_LMRiskPaySchema.getGraceDateCalMode();
		this.Operator = aPD_LMRiskPaySchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskPaySchema.getMakeDate());
		this.MakeTime = aPD_LMRiskPaySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskPaySchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskPaySchema.getModifyTime();
		this.Standbyflag1 = aPD_LMRiskPaySchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMRiskPaySchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMRiskPaySchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMRiskPaySchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMRiskPaySchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMRiskPaySchema.getStandbyflag6();
		this.GraceCalCode = aPD_LMRiskPaySchema.getGraceCalCode();
		this.OverdueDeal = aPD_LMRiskPaySchema.getOverdueDeal();
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("UrgePayFlag") == null )
				this.UrgePayFlag = null;
			else
				this.UrgePayFlag = rs.getString("UrgePayFlag").trim();

			if( rs.getString("ChargeType") == null )
				this.ChargeType = null;
			else
				this.ChargeType = rs.getString("ChargeType").trim();

			if( rs.getString("CutPayIntv") == null )
				this.CutPayIntv = null;
			else
				this.CutPayIntv = rs.getString("CutPayIntv").trim();

			if( rs.getString("PayAvoidType") == null )
				this.PayAvoidType = null;
			else
				this.PayAvoidType = rs.getString("PayAvoidType").trim();

			if( rs.getString("ChargeAndPrem") == null )
				this.ChargeAndPrem = null;
			else
				this.ChargeAndPrem = rs.getString("ChargeAndPrem").trim();

			if( rs.getString("BalaDateType") == null )
				this.BalaDateType = null;
			else
				this.BalaDateType = rs.getString("BalaDateType").trim();

			if( rs.getString("PayAvoidFlag") == null )
				this.PayAvoidFlag = null;
			else
				this.PayAvoidFlag = rs.getString("PayAvoidFlag").trim();

			if( rs.getString("PayAndRevEffe") == null )
				this.PayAndRevEffe = null;
			else
				this.PayAndRevEffe = rs.getString("PayAndRevEffe").trim();

			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("GracePeriodUnit") == null )
				this.GracePeriodUnit = null;
			else
				this.GracePeriodUnit = rs.getString("GracePeriodUnit").trim();

			if( rs.getString("GraceDateCalMode") == null )
				this.GraceDateCalMode = null;
			else
				this.GraceDateCalMode = rs.getString("GraceDateCalMode").trim();

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
			if( rs.getString("GraceCalCode") == null )
				this.GraceCalCode = null;
			else
				this.GraceCalCode = rs.getString("GraceCalCode").trim();

			if( rs.getString("OverdueDeal") == null )
				this.OverdueDeal = null;
			else
				this.OverdueDeal = rs.getString("OverdueDeal").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMRiskPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskPaySchema getSchema()
	{
		PD_LMRiskPaySchema aPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
		aPD_LMRiskPaySchema.setSchema(this);
		return aPD_LMRiskPaySchema;
	}

	public PD_LMRiskPayDB getDB()
	{
		PD_LMRiskPayDB aDBOper = new PD_LMRiskPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChargeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CutPayIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAvoidType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChargeAndPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalaDateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAvoidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAndRevEffe)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GracePeriodUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GraceDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(GraceCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OverdueDeal));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UrgePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChargeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CutPayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PayAvoidType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ChargeAndPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BalaDateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PayAvoidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PayAndRevEffe = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			GracePeriodUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GraceDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
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
			GraceCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			OverdueDeal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskPaySchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgePayFlag));
		}
		if (FCode.equalsIgnoreCase("ChargeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeType));
		}
		if (FCode.equalsIgnoreCase("CutPayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutPayIntv));
		}
		if (FCode.equalsIgnoreCase("PayAvoidType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAvoidType));
		}
		if (FCode.equalsIgnoreCase("ChargeAndPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeAndPrem));
		}
		if (FCode.equalsIgnoreCase("BalaDateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalaDateType));
		}
		if (FCode.equalsIgnoreCase("PayAvoidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAvoidFlag));
		}
		if (FCode.equalsIgnoreCase("PayAndRevEffe"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAndRevEffe));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("GracePeriodUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriodUnit));
		}
		if (FCode.equalsIgnoreCase("GraceDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GraceDateCalMode));
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
		if (FCode.equalsIgnoreCase("GraceCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GraceCalCode));
		}
		if (FCode.equalsIgnoreCase("OverdueDeal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OverdueDeal));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UrgePayFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ChargeType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CutPayIntv);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayAvoidType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ChargeAndPrem);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BalaDateType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PayAvoidFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PayAndRevEffe);
				break;
			case 11:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GracePeriodUnit);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(GraceDateCalMode);
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
			case 25:
				strFieldValue = StrTool.GBKToUnicode(GraceCalCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(OverdueDeal);
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgePayFlag = FValue.trim();
			}
			else
				UrgePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChargeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChargeType = FValue.trim();
			}
			else
				ChargeType = null;
		}
		if (FCode.equalsIgnoreCase("CutPayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CutPayIntv = FValue.trim();
			}
			else
				CutPayIntv = null;
		}
		if (FCode.equalsIgnoreCase("PayAvoidType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAvoidType = FValue.trim();
			}
			else
				PayAvoidType = null;
		}
		if (FCode.equalsIgnoreCase("ChargeAndPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChargeAndPrem = FValue.trim();
			}
			else
				ChargeAndPrem = null;
		}
		if (FCode.equalsIgnoreCase("BalaDateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalaDateType = FValue.trim();
			}
			else
				BalaDateType = null;
		}
		if (FCode.equalsIgnoreCase("PayAvoidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAvoidFlag = FValue.trim();
			}
			else
				PayAvoidFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayAndRevEffe"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAndRevEffe = FValue.trim();
			}
			else
				PayAndRevEffe = null;
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GracePeriodUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GracePeriodUnit = FValue.trim();
			}
			else
				GracePeriodUnit = null;
		}
		if (FCode.equalsIgnoreCase("GraceDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GraceDateCalMode = FValue.trim();
			}
			else
				GraceDateCalMode = null;
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
		if (FCode.equalsIgnoreCase("GraceCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GraceCalCode = FValue.trim();
			}
			else
				GraceCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OverdueDeal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OverdueDeal = FValue.trim();
			}
			else
				OverdueDeal = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMRiskPaySchema other = (PD_LMRiskPaySchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& UrgePayFlag.equals(other.getUrgePayFlag())
			&& ChargeType.equals(other.getChargeType())
			&& CutPayIntv.equals(other.getCutPayIntv())
			&& PayAvoidType.equals(other.getPayAvoidType())
			&& ChargeAndPrem.equals(other.getChargeAndPrem())
			&& BalaDateType.equals(other.getBalaDateType())
			&& PayAvoidFlag.equals(other.getPayAvoidFlag())
			&& PayAndRevEffe.equals(other.getPayAndRevEffe())
			&& GracePeriod == other.getGracePeriod()
			&& GracePeriodUnit.equals(other.getGracePeriodUnit())
			&& GraceDateCalMode.equals(other.getGraceDateCalMode())
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
			&& GraceCalCode.equals(other.getGraceCalCode())
			&& OverdueDeal.equals(other.getOverdueDeal());
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskName") ) {
			return 2;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return 3;
		}
		if( strFieldName.equals("ChargeType") ) {
			return 4;
		}
		if( strFieldName.equals("CutPayIntv") ) {
			return 5;
		}
		if( strFieldName.equals("PayAvoidType") ) {
			return 6;
		}
		if( strFieldName.equals("ChargeAndPrem") ) {
			return 7;
		}
		if( strFieldName.equals("BalaDateType") ) {
			return 8;
		}
		if( strFieldName.equals("PayAvoidFlag") ) {
			return 9;
		}
		if( strFieldName.equals("PayAndRevEffe") ) {
			return 10;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 11;
		}
		if( strFieldName.equals("GracePeriodUnit") ) {
			return 12;
		}
		if( strFieldName.equals("GraceDateCalMode") ) {
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
		if( strFieldName.equals("GraceCalCode") ) {
			return 25;
		}
		if( strFieldName.equals("OverdueDeal") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskName";
				break;
			case 3:
				strFieldName = "UrgePayFlag";
				break;
			case 4:
				strFieldName = "ChargeType";
				break;
			case 5:
				strFieldName = "CutPayIntv";
				break;
			case 6:
				strFieldName = "PayAvoidType";
				break;
			case 7:
				strFieldName = "ChargeAndPrem";
				break;
			case 8:
				strFieldName = "BalaDateType";
				break;
			case 9:
				strFieldName = "PayAvoidFlag";
				break;
			case 10:
				strFieldName = "PayAndRevEffe";
				break;
			case 11:
				strFieldName = "GracePeriod";
				break;
			case 12:
				strFieldName = "GracePeriodUnit";
				break;
			case 13:
				strFieldName = "GraceDateCalMode";
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
			case 25:
				strFieldName = "GraceCalCode";
				break;
			case 26:
				strFieldName = "OverdueDeal";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChargeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CutPayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAvoidType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChargeAndPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalaDateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAvoidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAndRevEffe") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GracePeriodUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GraceDateCalMode") ) {
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
		if( strFieldName.equals("GraceCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OverdueDeal") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 25:
				nFieldType = Schema.TYPE_STRING;
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
