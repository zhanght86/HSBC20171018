

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
import com.sinosoft.lis.db.PD_LMRiskDB;

/*
 * <p>ClassName: PD_LMRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMRiskSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 险种简称 */
	private String RiskShortName;
	/** 险种英文名称 */
	private String RiskEnName;
	/** 险种英文简称 */
	private String RiskEnShortName;
	/** 选择责任标记 */
	private String ChoDutyFlag;
	/** 续期收费标记 */
	private String CPayFlag;
	/** 生存给付标记 */
	private String GetFlag;
	/** 保全标记 */
	private String EdorFlag;
	/** 续保标记 */
	private String RnewFlag;
	/** 核保标记 */
	private String UWFlag;
	/** 分保标记 */
	private String RinsFlag;
	/** 保险帐户标记 */
	private String InsuAccFlag;
	/** 预定利率 */
	private double DestRate;
	/** 原险种编码 */
	private String OrigRiskCode;
	/** 子版本号 */
	private String SubRiskVer;
	/** 险种统计名称 */
	private String RiskStatName;
	/** 公共保额标记 */
	private String PubAmntFlag;
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

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskSchema()
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
		PD_LMRiskSchema cloned = (PD_LMRiskSchema)super.clone();
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
	public String getRiskShortName()
	{
		return RiskShortName;
	}
	public void setRiskShortName(String aRiskShortName)
	{
		RiskShortName = aRiskShortName;
	}
	public String getRiskEnName()
	{
		return RiskEnName;
	}
	public void setRiskEnName(String aRiskEnName)
	{
		RiskEnName = aRiskEnName;
	}
	public String getRiskEnShortName()
	{
		return RiskEnShortName;
	}
	public void setRiskEnShortName(String aRiskEnShortName)
	{
		RiskEnShortName = aRiskEnShortName;
	}
	/**
	* Y--是 N--否
	*/
	public String getChoDutyFlag()
	{
		return ChoDutyFlag;
	}
	public void setChoDutyFlag(String aChoDutyFlag)
	{
		ChoDutyFlag = aChoDutyFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getCPayFlag()
	{
		return CPayFlag;
	}
	public void setCPayFlag(String aCPayFlag)
	{
		CPayFlag = aCPayFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		GetFlag = aGetFlag;
	}
	/**
	* Y--是、N--否
	*/
	public String getEdorFlag()
	{
		return EdorFlag;
	}
	public void setEdorFlag(String aEdorFlag)
	{
		EdorFlag = aEdorFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getRnewFlag()
	{
		return RnewFlag;
	}
	public void setRnewFlag(String aRnewFlag)
	{
		RnewFlag = aRnewFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getRinsFlag()
	{
		return RinsFlag;
	}
	public void setRinsFlag(String aRinsFlag)
	{
		RinsFlag = aRinsFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getInsuAccFlag()
	{
		return InsuAccFlag;
	}
	public void setInsuAccFlag(String aInsuAccFlag)
	{
		InsuAccFlag = aInsuAccFlag;
	}
	/**
	* 该险种的预定利率,利差返还时用到
	*/
	public double getDestRate()
	{
		return DestRate;
	}
	public void setDestRate(double aDestRate)
	{
		DestRate = aDestRate;
	}
	public void setDestRate(String aDestRate)
	{
		if (aDestRate != null && !aDestRate.equals(""))
		{
			Double tDouble = new Double(aDestRate);
			double d = tDouble.doubleValue();
			DestRate = d;
		}
	}

	public String getOrigRiskCode()
	{
		return OrigRiskCode;
	}
	public void setOrigRiskCode(String aOrigRiskCode)
	{
		OrigRiskCode = aOrigRiskCode;
	}
	public String getSubRiskVer()
	{
		return SubRiskVer;
	}
	public void setSubRiskVer(String aSubRiskVer)
	{
		SubRiskVer = aSubRiskVer;
	}
	/**
	* 统计时显示的名称
	*/
	public String getRiskStatName()
	{
		return RiskStatName;
	}
	public void setRiskStatName(String aRiskStatName)
	{
		RiskStatName = aRiskStatName;
	}
	public String getPubAmntFlag()
	{
		return PubAmntFlag;
	}
	public void setPubAmntFlag(String aPubAmntFlag)
	{
		PubAmntFlag = aPubAmntFlag;
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
	* 使用另外一个 PD_LMRiskSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskSchema PD_LMRiskSchema
	**/
	public void setSchema(PD_LMRiskSchema aPD_LMRiskSchema)
	{
		this.RiskCode = aPD_LMRiskSchema.getRiskCode();
		this.RiskVer = aPD_LMRiskSchema.getRiskVer();
		this.RiskName = aPD_LMRiskSchema.getRiskName();
		this.RiskShortName = aPD_LMRiskSchema.getRiskShortName();
		this.RiskEnName = aPD_LMRiskSchema.getRiskEnName();
		this.RiskEnShortName = aPD_LMRiskSchema.getRiskEnShortName();
		this.ChoDutyFlag = aPD_LMRiskSchema.getChoDutyFlag();
		this.CPayFlag = aPD_LMRiskSchema.getCPayFlag();
		this.GetFlag = aPD_LMRiskSchema.getGetFlag();
		this.EdorFlag = aPD_LMRiskSchema.getEdorFlag();
		this.RnewFlag = aPD_LMRiskSchema.getRnewFlag();
		this.UWFlag = aPD_LMRiskSchema.getUWFlag();
		this.RinsFlag = aPD_LMRiskSchema.getRinsFlag();
		this.InsuAccFlag = aPD_LMRiskSchema.getInsuAccFlag();
		this.DestRate = aPD_LMRiskSchema.getDestRate();
		this.OrigRiskCode = aPD_LMRiskSchema.getOrigRiskCode();
		this.SubRiskVer = aPD_LMRiskSchema.getSubRiskVer();
		this.RiskStatName = aPD_LMRiskSchema.getRiskStatName();
		this.PubAmntFlag = aPD_LMRiskSchema.getPubAmntFlag();
		this.Operator = aPD_LMRiskSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMRiskSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMRiskSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMRiskSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMRiskSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMRiskSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMRiskSchema.getStandbyflag6();
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

			if( rs.getString("RiskShortName") == null )
				this.RiskShortName = null;
			else
				this.RiskShortName = rs.getString("RiskShortName").trim();

			if( rs.getString("RiskEnName") == null )
				this.RiskEnName = null;
			else
				this.RiskEnName = rs.getString("RiskEnName").trim();

			if( rs.getString("RiskEnShortName") == null )
				this.RiskEnShortName = null;
			else
				this.RiskEnShortName = rs.getString("RiskEnShortName").trim();

			if( rs.getString("ChoDutyFlag") == null )
				this.ChoDutyFlag = null;
			else
				this.ChoDutyFlag = rs.getString("ChoDutyFlag").trim();

			if( rs.getString("CPayFlag") == null )
				this.CPayFlag = null;
			else
				this.CPayFlag = rs.getString("CPayFlag").trim();

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

			if( rs.getString("EdorFlag") == null )
				this.EdorFlag = null;
			else
				this.EdorFlag = rs.getString("EdorFlag").trim();

			if( rs.getString("RnewFlag") == null )
				this.RnewFlag = null;
			else
				this.RnewFlag = rs.getString("RnewFlag").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("RinsFlag") == null )
				this.RinsFlag = null;
			else
				this.RinsFlag = rs.getString("RinsFlag").trim();

			if( rs.getString("InsuAccFlag") == null )
				this.InsuAccFlag = null;
			else
				this.InsuAccFlag = rs.getString("InsuAccFlag").trim();

			this.DestRate = rs.getDouble("DestRate");
			if( rs.getString("OrigRiskCode") == null )
				this.OrigRiskCode = null;
			else
				this.OrigRiskCode = rs.getString("OrigRiskCode").trim();

			if( rs.getString("SubRiskVer") == null )
				this.SubRiskVer = null;
			else
				this.SubRiskVer = rs.getString("SubRiskVer").trim();

			if( rs.getString("RiskStatName") == null )
				this.RiskStatName = null;
			else
				this.RiskStatName = rs.getString("RiskStatName").trim();

			if( rs.getString("PubAmntFlag") == null )
				this.PubAmntFlag = null;
			else
				this.PubAmntFlag = rs.getString("PubAmntFlag").trim();

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
			System.out.println("数据库中的PD_LMRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskSchema getSchema()
	{
		PD_LMRiskSchema aPD_LMRiskSchema = new PD_LMRiskSchema();
		aPD_LMRiskSchema.setSchema(this);
		return aPD_LMRiskSchema;
	}

	public PD_LMRiskDB getDB()
	{
		PD_LMRiskDB aDBOper = new PD_LMRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRisk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskShortName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskEnName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskEnShortName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChoDutyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RnewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RinsFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrigRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubRiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskStatName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PubAmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRisk>历史记账凭证主表信息</A>表字段
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
			RiskShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskEnName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskEnShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ChoDutyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			EdorFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RnewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RinsFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			OrigRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SubRiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RiskStatName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PubAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskSchema";
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
		if (FCode.equalsIgnoreCase("RiskShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskShortName));
		}
		if (FCode.equalsIgnoreCase("RiskEnName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskEnName));
		}
		if (FCode.equalsIgnoreCase("RiskEnShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskEnShortName));
		}
		if (FCode.equalsIgnoreCase("ChoDutyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChoDutyFlag));
		}
		if (FCode.equalsIgnoreCase("CPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CPayFlag));
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
		}
		if (FCode.equalsIgnoreCase("EdorFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorFlag));
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewFlag));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("RinsFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RinsFlag));
		}
		if (FCode.equalsIgnoreCase("InsuAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccFlag));
		}
		if (FCode.equalsIgnoreCase("DestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestRate));
		}
		if (FCode.equalsIgnoreCase("OrigRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrigRiskCode));
		}
		if (FCode.equalsIgnoreCase("SubRiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskStatName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskStatName));
		}
		if (FCode.equalsIgnoreCase("PubAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PubAmntFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskShortName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskEnName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskEnShortName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ChoDutyFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CPayFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(EdorFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RnewFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RinsFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuAccFlag);
				break;
			case 14:
				strFieldValue = String.valueOf(DestRate);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(OrigRiskCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SubRiskVer);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RiskStatName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PubAmntFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 26:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 27:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 28:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 29:
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
		if (FCode.equalsIgnoreCase("RiskShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskShortName = FValue.trim();
			}
			else
				RiskShortName = null;
		}
		if (FCode.equalsIgnoreCase("RiskEnName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskEnName = FValue.trim();
			}
			else
				RiskEnName = null;
		}
		if (FCode.equalsIgnoreCase("RiskEnShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskEnShortName = FValue.trim();
			}
			else
				RiskEnShortName = null;
		}
		if (FCode.equalsIgnoreCase("ChoDutyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChoDutyFlag = FValue.trim();
			}
			else
				ChoDutyFlag = null;
		}
		if (FCode.equalsIgnoreCase("CPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CPayFlag = FValue.trim();
			}
			else
				CPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFlag = FValue.trim();
			}
			else
				GetFlag = null;
		}
		if (FCode.equalsIgnoreCase("EdorFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorFlag = FValue.trim();
			}
			else
				EdorFlag = null;
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RnewFlag = FValue.trim();
			}
			else
				RnewFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("RinsFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RinsFlag = FValue.trim();
			}
			else
				RinsFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccFlag = FValue.trim();
			}
			else
				InsuAccFlag = null;
		}
		if (FCode.equalsIgnoreCase("DestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OrigRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrigRiskCode = FValue.trim();
			}
			else
				OrigRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("SubRiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskVer = FValue.trim();
			}
			else
				SubRiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskStatName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskStatName = FValue.trim();
			}
			else
				RiskStatName = null;
		}
		if (FCode.equalsIgnoreCase("PubAmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PubAmntFlag = FValue.trim();
			}
			else
				PubAmntFlag = null;
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
		PD_LMRiskSchema other = (PD_LMRiskSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& RiskShortName.equals(other.getRiskShortName())
			&& RiskEnName.equals(other.getRiskEnName())
			&& RiskEnShortName.equals(other.getRiskEnShortName())
			&& ChoDutyFlag.equals(other.getChoDutyFlag())
			&& CPayFlag.equals(other.getCPayFlag())
			&& GetFlag.equals(other.getGetFlag())
			&& EdorFlag.equals(other.getEdorFlag())
			&& RnewFlag.equals(other.getRnewFlag())
			&& UWFlag.equals(other.getUWFlag())
			&& RinsFlag.equals(other.getRinsFlag())
			&& InsuAccFlag.equals(other.getInsuAccFlag())
			&& DestRate == other.getDestRate()
			&& OrigRiskCode.equals(other.getOrigRiskCode())
			&& SubRiskVer.equals(other.getSubRiskVer())
			&& RiskStatName.equals(other.getRiskStatName())
			&& PubAmntFlag.equals(other.getPubAmntFlag())
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskName") ) {
			return 2;
		}
		if( strFieldName.equals("RiskShortName") ) {
			return 3;
		}
		if( strFieldName.equals("RiskEnName") ) {
			return 4;
		}
		if( strFieldName.equals("RiskEnShortName") ) {
			return 5;
		}
		if( strFieldName.equals("ChoDutyFlag") ) {
			return 6;
		}
		if( strFieldName.equals("CPayFlag") ) {
			return 7;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 8;
		}
		if( strFieldName.equals("EdorFlag") ) {
			return 9;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 10;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 11;
		}
		if( strFieldName.equals("RinsFlag") ) {
			return 12;
		}
		if( strFieldName.equals("InsuAccFlag") ) {
			return 13;
		}
		if( strFieldName.equals("DestRate") ) {
			return 14;
		}
		if( strFieldName.equals("OrigRiskCode") ) {
			return 15;
		}
		if( strFieldName.equals("SubRiskVer") ) {
			return 16;
		}
		if( strFieldName.equals("RiskStatName") ) {
			return 17;
		}
		if( strFieldName.equals("PubAmntFlag") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 24;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 25;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 26;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 27;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 28;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 29;
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
				strFieldName = "RiskShortName";
				break;
			case 4:
				strFieldName = "RiskEnName";
				break;
			case 5:
				strFieldName = "RiskEnShortName";
				break;
			case 6:
				strFieldName = "ChoDutyFlag";
				break;
			case 7:
				strFieldName = "CPayFlag";
				break;
			case 8:
				strFieldName = "GetFlag";
				break;
			case 9:
				strFieldName = "EdorFlag";
				break;
			case 10:
				strFieldName = "RnewFlag";
				break;
			case 11:
				strFieldName = "UWFlag";
				break;
			case 12:
				strFieldName = "RinsFlag";
				break;
			case 13:
				strFieldName = "InsuAccFlag";
				break;
			case 14:
				strFieldName = "DestRate";
				break;
			case 15:
				strFieldName = "OrigRiskCode";
				break;
			case 16:
				strFieldName = "SubRiskVer";
				break;
			case 17:
				strFieldName = "RiskStatName";
				break;
			case 18:
				strFieldName = "PubAmntFlag";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "Standbyflag1";
				break;
			case 25:
				strFieldName = "Standbyflag2";
				break;
			case 26:
				strFieldName = "Standbyflag3";
				break;
			case 27:
				strFieldName = "Standbyflag4";
				break;
			case 28:
				strFieldName = "Standbyflag5";
				break;
			case 29:
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskEnName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskEnShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChoDutyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RinsFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OrigRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskStatName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PubAmntFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
