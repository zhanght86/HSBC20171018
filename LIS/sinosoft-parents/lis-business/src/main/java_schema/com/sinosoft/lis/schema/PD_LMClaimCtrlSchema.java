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
import com.sinosoft.lis.db.PD_LMClaimCtrlDB;

/*
 * <p>ClassName: PD_LMClaimCtrlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMClaimCtrlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMClaimCtrlSchema.class);

	// @Field
	/** 理赔控制编号 */
	private String ClaimCtrlCode;
	/** 理赔控制名称 */
	private String ClaimCtrlName;
	/** 类型 */
	private String ClaimCtrlType;
	/** 理赔控制描述 */
	private String ClaimEngineDesc;
	/** 有效期间 */
	private String PeriodFlag;
	/** 自定义期间标记 */
	private String DefPeriodFlag;
	/** 个人家庭标记 */
	private String FamilyFlag;
	/** 保单有效期/内外 */
	private String InsPeriodFlag;
	/** 赔付期间控制上限 */
	private int ClmPeriodMAX;
	/** 赔付期间上限单位 */
	private String ClmPeriodMAXFlag;
	/** 赔付期间上限控制计算参考 */
	private String ClmPeriodMAXCtrl;
	/** 赔付期间下限控制 */
	private int ClmPeriodMIN;
	/** 赔付期间下限控制单位 */
	private String ClmPeriodMINFlag;
	/** 赔付期间下限控制计算参考 */
	private String ClmPeriodMINCtrl;
	/** 理赔控制计算sql */
	private String CalCode;
	/** 理赔控制处理值类型 */
	private String CalResultType;
	/** 理赔控制默认值 */
	private double DefaultValue;
	/** 理赔控制计算方式 */
	private String CalCtrlFlag;
	/** 理赔费用控制计算sql */
	private String FeeCalCode;
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
	public PD_LMClaimCtrlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClaimCtrlCode";

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
		PD_LMClaimCtrlSchema cloned = (PD_LMClaimCtrlSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClaimCtrlCode()
	{
		return ClaimCtrlCode;
	}
	public void setClaimCtrlCode(String aClaimCtrlCode)
	{
		ClaimCtrlCode = aClaimCtrlCode;
	}
	public String getClaimCtrlName()
	{
		return ClaimCtrlName;
	}
	public void setClaimCtrlName(String aClaimCtrlName)
	{
		ClaimCtrlName = aClaimCtrlName;
	}
	/**
	* 1.扣减(免赔额) 2.给付比例(投保人,被保险人共付或者赔付比例) 3.金额险种(保额上限) 4.服务限制(天数等) 5.补贴金额
	*/
	public String getClaimCtrlType()
	{
		return ClaimCtrlType;
	}
	public void setClaimCtrlType(String aClaimCtrlType)
	{
		ClaimCtrlType = aClaimCtrlType;
	}
	public String getClaimEngineDesc()
	{
		return ClaimEngineDesc;
	}
	public void setClaimEngineDesc(String aClaimEngineDesc)
	{
		ClaimEngineDesc = aClaimEngineDesc;
	}
	/**
	* 1.保单年度 2.日历年度 3.保险期间 4.每月 5.每天 6.每次赔案
	*/
	public String getPeriodFlag()
	{
		return PeriodFlag;
	}
	public void setPeriodFlag(String aPeriodFlag)
	{
		PeriodFlag = aPeriodFlag;
	}
	/**
	* 用来定义在一定有效期间内根据时间段的不同,赔付值不同的情况.决定CPD_LMDefPeriod表的记录是否产生作用
	*/
	public String getDefPeriodFlag()
	{
		return DefPeriodFlag;
	}
	public void setDefPeriodFlag(String aDefPeriodFlag)
	{
		DefPeriodFlag = aDefPeriodFlag;
	}
	/**
	* 是否共用保额
	*/
	public String getFamilyFlag()
	{
		return FamilyFlag;
	}
	public void setFamilyFlag(String aFamilyFlag)
	{
		FamilyFlag = aFamilyFlag;
	}
	/**
	* 该累加器用于保险期间内还是保险期间外赔付标记 1.保险期间内 2.包括期外
	*/
	public String getInsPeriodFlag()
	{
		return InsPeriodFlag;
	}
	public void setInsPeriodFlag(String aInsPeriodFlag)
	{
		InsPeriodFlag = aInsPeriodFlag;
	}
	public int getClmPeriodMAX()
	{
		return ClmPeriodMAX;
	}
	public void setClmPeriodMAX(int aClmPeriodMAX)
	{
		ClmPeriodMAX = aClmPeriodMAX;
	}
	public void setClmPeriodMAX(String aClmPeriodMAX)
	{
		if (aClmPeriodMAX != null && !aClmPeriodMAX.equals(""))
		{
			Integer tInteger = new Integer(aClmPeriodMAX);
			int i = tInteger.intValue();
			ClmPeriodMAX = i;
		}
	}

	/**
	* Y--年 M--月 D--日 A--年龄
	*/
	public String getClmPeriodMAXFlag()
	{
		return ClmPeriodMAXFlag;
	}
	public void setClmPeriodMAXFlag(String aClmPeriodMAXFlag)
	{
		ClmPeriodMAXFlag = aClmPeriodMAXFlag;
	}
	/**
	* S--起保日期对应日 B--出生日期对应日 C—出险日期
	*/
	public String getClmPeriodMAXCtrl()
	{
		return ClmPeriodMAXCtrl;
	}
	public void setClmPeriodMAXCtrl(String aClmPeriodMAXCtrl)
	{
		ClmPeriodMAXCtrl = aClmPeriodMAXCtrl;
	}
	public int getClmPeriodMIN()
	{
		return ClmPeriodMIN;
	}
	public void setClmPeriodMIN(int aClmPeriodMIN)
	{
		ClmPeriodMIN = aClmPeriodMIN;
	}
	public void setClmPeriodMIN(String aClmPeriodMIN)
	{
		if (aClmPeriodMIN != null && !aClmPeriodMIN.equals(""))
		{
			Integer tInteger = new Integer(aClmPeriodMIN);
			int i = tInteger.intValue();
			ClmPeriodMIN = i;
		}
	}

	/**
	* Y--年 M--月 D--日 A--年龄
	*/
	public String getClmPeriodMINFlag()
	{
		return ClmPeriodMINFlag;
	}
	public void setClmPeriodMINFlag(String aClmPeriodMINFlag)
	{
		ClmPeriodMINFlag = aClmPeriodMINFlag;
	}
	/**
	* S--起保日期对应日 B--出生日期对应日 C—出险日期
	*/
	public String getClmPeriodMINCtrl()
	{
		return ClmPeriodMINCtrl;
	}
	public void setClmPeriodMINCtrl(String aClmPeriodMINCtrl)
	{
		ClmPeriodMINCtrl = aClmPeriodMINCtrl;
	}
	/**
	* 实例化累加器时计算公式
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 1.比例 2.金额
	*/
	public String getCalResultType()
	{
		return CalResultType;
	}
	public void setCalResultType(String aCalResultType)
	{
		CalResultType = aCalResultType;
	}
	public double getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(double aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		if (aDefaultValue != null && !aDefaultValue.equals(""))
		{
			Double tDouble = new Double(aDefaultValue);
			double d = tDouble.doubleValue();
			DefaultValue = d;
		}
	}

	/**
	* 1.取描述默认值 2.实例化时取默认计算要素计算 3.实例化时取约定计算要素计算
	*/
	public String getCalCtrlFlag()
	{
		return CalCtrlFlag;
	}
	public void setCalCtrlFlag(String aCalCtrlFlag)
	{
		CalCtrlFlag = aCalCtrlFlag;
	}
	public String getFeeCalCode()
	{
		return FeeCalCode;
	}
	public void setFeeCalCode(String aFeeCalCode)
	{
		FeeCalCode = aFeeCalCode;
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
	* 使用另外一个 PD_LMClaimCtrlSchema 对象给 Schema 赋值
	* @param: aPD_LMClaimCtrlSchema PD_LMClaimCtrlSchema
	**/
	public void setSchema(PD_LMClaimCtrlSchema aPD_LMClaimCtrlSchema)
	{
		this.ClaimCtrlCode = aPD_LMClaimCtrlSchema.getClaimCtrlCode();
		this.ClaimCtrlName = aPD_LMClaimCtrlSchema.getClaimCtrlName();
		this.ClaimCtrlType = aPD_LMClaimCtrlSchema.getClaimCtrlType();
		this.ClaimEngineDesc = aPD_LMClaimCtrlSchema.getClaimEngineDesc();
		this.PeriodFlag = aPD_LMClaimCtrlSchema.getPeriodFlag();
		this.DefPeriodFlag = aPD_LMClaimCtrlSchema.getDefPeriodFlag();
		this.FamilyFlag = aPD_LMClaimCtrlSchema.getFamilyFlag();
		this.InsPeriodFlag = aPD_LMClaimCtrlSchema.getInsPeriodFlag();
		this.ClmPeriodMAX = aPD_LMClaimCtrlSchema.getClmPeriodMAX();
		this.ClmPeriodMAXFlag = aPD_LMClaimCtrlSchema.getClmPeriodMAXFlag();
		this.ClmPeriodMAXCtrl = aPD_LMClaimCtrlSchema.getClmPeriodMAXCtrl();
		this.ClmPeriodMIN = aPD_LMClaimCtrlSchema.getClmPeriodMIN();
		this.ClmPeriodMINFlag = aPD_LMClaimCtrlSchema.getClmPeriodMINFlag();
		this.ClmPeriodMINCtrl = aPD_LMClaimCtrlSchema.getClmPeriodMINCtrl();
		this.CalCode = aPD_LMClaimCtrlSchema.getCalCode();
		this.CalResultType = aPD_LMClaimCtrlSchema.getCalResultType();
		this.DefaultValue = aPD_LMClaimCtrlSchema.getDefaultValue();
		this.CalCtrlFlag = aPD_LMClaimCtrlSchema.getCalCtrlFlag();
		this.FeeCalCode = aPD_LMClaimCtrlSchema.getFeeCalCode();
		this.Operator = aPD_LMClaimCtrlSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMClaimCtrlSchema.getMakeDate());
		this.MakeTime = aPD_LMClaimCtrlSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMClaimCtrlSchema.getModifyDate());
		this.ModifyTime = aPD_LMClaimCtrlSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMClaimCtrlSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMClaimCtrlSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMClaimCtrlSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMClaimCtrlSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMClaimCtrlSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMClaimCtrlSchema.getStandbyflag6();
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
			if( rs.getString("ClaimCtrlCode") == null )
				this.ClaimCtrlCode = null;
			else
				this.ClaimCtrlCode = rs.getString("ClaimCtrlCode").trim();

			if( rs.getString("ClaimCtrlName") == null )
				this.ClaimCtrlName = null;
			else
				this.ClaimCtrlName = rs.getString("ClaimCtrlName").trim();

			if( rs.getString("ClaimCtrlType") == null )
				this.ClaimCtrlType = null;
			else
				this.ClaimCtrlType = rs.getString("ClaimCtrlType").trim();

			if( rs.getString("ClaimEngineDesc") == null )
				this.ClaimEngineDesc = null;
			else
				this.ClaimEngineDesc = rs.getString("ClaimEngineDesc").trim();

			if( rs.getString("PeriodFlag") == null )
				this.PeriodFlag = null;
			else
				this.PeriodFlag = rs.getString("PeriodFlag").trim();

			if( rs.getString("DefPeriodFlag") == null )
				this.DefPeriodFlag = null;
			else
				this.DefPeriodFlag = rs.getString("DefPeriodFlag").trim();

			if( rs.getString("FamilyFlag") == null )
				this.FamilyFlag = null;
			else
				this.FamilyFlag = rs.getString("FamilyFlag").trim();

			if( rs.getString("InsPeriodFlag") == null )
				this.InsPeriodFlag = null;
			else
				this.InsPeriodFlag = rs.getString("InsPeriodFlag").trim();

			this.ClmPeriodMAX = rs.getInt("ClmPeriodMAX");
			if( rs.getString("ClmPeriodMAXFlag") == null )
				this.ClmPeriodMAXFlag = null;
			else
				this.ClmPeriodMAXFlag = rs.getString("ClmPeriodMAXFlag").trim();

			if( rs.getString("ClmPeriodMAXCtrl") == null )
				this.ClmPeriodMAXCtrl = null;
			else
				this.ClmPeriodMAXCtrl = rs.getString("ClmPeriodMAXCtrl").trim();

			this.ClmPeriodMIN = rs.getInt("ClmPeriodMIN");
			if( rs.getString("ClmPeriodMINFlag") == null )
				this.ClmPeriodMINFlag = null;
			else
				this.ClmPeriodMINFlag = rs.getString("ClmPeriodMINFlag").trim();

			if( rs.getString("ClmPeriodMINCtrl") == null )
				this.ClmPeriodMINCtrl = null;
			else
				this.ClmPeriodMINCtrl = rs.getString("ClmPeriodMINCtrl").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalResultType") == null )
				this.CalResultType = null;
			else
				this.CalResultType = rs.getString("CalResultType").trim();

			this.DefaultValue = rs.getDouble("DefaultValue");
			if( rs.getString("CalCtrlFlag") == null )
				this.CalCtrlFlag = null;
			else
				this.CalCtrlFlag = rs.getString("CalCtrlFlag").trim();

			if( rs.getString("FeeCalCode") == null )
				this.FeeCalCode = null;
			else
				this.FeeCalCode = rs.getString("FeeCalCode").trim();

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
			logger.debug("数据库中的PD_LMClaimCtrl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMClaimCtrlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMClaimCtrlSchema getSchema()
	{
		PD_LMClaimCtrlSchema aPD_LMClaimCtrlSchema = new PD_LMClaimCtrlSchema();
		aPD_LMClaimCtrlSchema.setSchema(this);
		return aPD_LMClaimCtrlSchema;
	}

	public PD_LMClaimCtrlDB getDB()
	{
		PD_LMClaimCtrlDB aDBOper = new PD_LMClaimCtrlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMClaimCtrl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClaimCtrlCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimCtrlName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimCtrlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimEngineDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmPeriodMAX));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMAXFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMAXCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmPeriodMIN));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMINFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMINCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalResultType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCtrlFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMClaimCtrl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClaimCtrlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClaimCtrlName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClaimCtrlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClaimEngineDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DefPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FamilyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ClmPeriodMAX= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			ClmPeriodMAXFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ClmPeriodMAXCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ClmPeriodMIN= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			ClmPeriodMINFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ClmPeriodMINCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CalResultType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DefaultValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			CalCtrlFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			FeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "PD_LMClaimCtrlSchema";
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
		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlCode));
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlName));
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlType));
		}
		if (FCode.equalsIgnoreCase("ClaimEngineDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimEngineDesc));
		}
		if (FCode.equalsIgnoreCase("PeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeriodFlag));
		}
		if (FCode.equalsIgnoreCase("DefPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("FamilyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyFlag));
		}
		if (FCode.equalsIgnoreCase("InsPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAX"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMAX));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMAXFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMAXCtrl));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMIN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMIN));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMINFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMINCtrl));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalResultType));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCtrlFlag));
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalCode));
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
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClaimEngineDesc);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PeriodFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DefPeriodFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FamilyFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsPeriodFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(ClmPeriodMAX);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMAXFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMAXCtrl);
				break;
			case 11:
				strFieldValue = String.valueOf(ClmPeriodMIN);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMINFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMINCtrl);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CalResultType);
				break;
			case 16:
				strFieldValue = String.valueOf(DefaultValue);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CalCtrlFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(FeeCalCode);
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

		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlCode = FValue.trim();
			}
			else
				ClaimCtrlCode = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlName = FValue.trim();
			}
			else
				ClaimCtrlName = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlType = FValue.trim();
			}
			else
				ClaimCtrlType = null;
		}
		if (FCode.equalsIgnoreCase("ClaimEngineDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimEngineDesc = FValue.trim();
			}
			else
				ClaimEngineDesc = null;
		}
		if (FCode.equalsIgnoreCase("PeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PeriodFlag = FValue.trim();
			}
			else
				PeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("DefPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefPeriodFlag = FValue.trim();
			}
			else
				DefPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("FamilyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyFlag = FValue.trim();
			}
			else
				FamilyFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsPeriodFlag = FValue.trim();
			}
			else
				InsPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAX"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmPeriodMAX = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMAXFlag = FValue.trim();
			}
			else
				ClmPeriodMAXFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMAXCtrl = FValue.trim();
			}
			else
				ClmPeriodMAXCtrl = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMIN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmPeriodMIN = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMINFlag = FValue.trim();
			}
			else
				ClmPeriodMINFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMINCtrl = FValue.trim();
			}
			else
				ClmPeriodMINCtrl = null;
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
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalResultType = FValue.trim();
			}
			else
				CalResultType = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCtrlFlag = FValue.trim();
			}
			else
				CalCtrlFlag = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalCode = FValue.trim();
			}
			else
				FeeCalCode = null;
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
		PD_LMClaimCtrlSchema other = (PD_LMClaimCtrlSchema)otherObject;
		return
			ClaimCtrlCode.equals(other.getClaimCtrlCode())
			&& ClaimCtrlName.equals(other.getClaimCtrlName())
			&& ClaimCtrlType.equals(other.getClaimCtrlType())
			&& ClaimEngineDesc.equals(other.getClaimEngineDesc())
			&& PeriodFlag.equals(other.getPeriodFlag())
			&& DefPeriodFlag.equals(other.getDefPeriodFlag())
			&& FamilyFlag.equals(other.getFamilyFlag())
			&& InsPeriodFlag.equals(other.getInsPeriodFlag())
			&& ClmPeriodMAX == other.getClmPeriodMAX()
			&& ClmPeriodMAXFlag.equals(other.getClmPeriodMAXFlag())
			&& ClmPeriodMAXCtrl.equals(other.getClmPeriodMAXCtrl())
			&& ClmPeriodMIN == other.getClmPeriodMIN()
			&& ClmPeriodMINFlag.equals(other.getClmPeriodMINFlag())
			&& ClmPeriodMINCtrl.equals(other.getClmPeriodMINCtrl())
			&& CalCode.equals(other.getCalCode())
			&& CalResultType.equals(other.getCalResultType())
			&& DefaultValue == other.getDefaultValue()
			&& CalCtrlFlag.equals(other.getCalCtrlFlag())
			&& FeeCalCode.equals(other.getFeeCalCode())
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return 0;
		}
		if( strFieldName.equals("ClaimCtrlName") ) {
			return 1;
		}
		if( strFieldName.equals("ClaimCtrlType") ) {
			return 2;
		}
		if( strFieldName.equals("ClaimEngineDesc") ) {
			return 3;
		}
		if( strFieldName.equals("PeriodFlag") ) {
			return 4;
		}
		if( strFieldName.equals("DefPeriodFlag") ) {
			return 5;
		}
		if( strFieldName.equals("FamilyFlag") ) {
			return 6;
		}
		if( strFieldName.equals("InsPeriodFlag") ) {
			return 7;
		}
		if( strFieldName.equals("ClmPeriodMAX") ) {
			return 8;
		}
		if( strFieldName.equals("ClmPeriodMAXFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ClmPeriodMAXCtrl") ) {
			return 10;
		}
		if( strFieldName.equals("ClmPeriodMIN") ) {
			return 11;
		}
		if( strFieldName.equals("ClmPeriodMINFlag") ) {
			return 12;
		}
		if( strFieldName.equals("ClmPeriodMINCtrl") ) {
			return 13;
		}
		if( strFieldName.equals("CalCode") ) {
			return 14;
		}
		if( strFieldName.equals("CalResultType") ) {
			return 15;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 16;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
			return 17;
		}
		if( strFieldName.equals("FeeCalCode") ) {
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
				strFieldName = "ClaimCtrlCode";
				break;
			case 1:
				strFieldName = "ClaimCtrlName";
				break;
			case 2:
				strFieldName = "ClaimCtrlType";
				break;
			case 3:
				strFieldName = "ClaimEngineDesc";
				break;
			case 4:
				strFieldName = "PeriodFlag";
				break;
			case 5:
				strFieldName = "DefPeriodFlag";
				break;
			case 6:
				strFieldName = "FamilyFlag";
				break;
			case 7:
				strFieldName = "InsPeriodFlag";
				break;
			case 8:
				strFieldName = "ClmPeriodMAX";
				break;
			case 9:
				strFieldName = "ClmPeriodMAXFlag";
				break;
			case 10:
				strFieldName = "ClmPeriodMAXCtrl";
				break;
			case 11:
				strFieldName = "ClmPeriodMIN";
				break;
			case 12:
				strFieldName = "ClmPeriodMINFlag";
				break;
			case 13:
				strFieldName = "ClmPeriodMINCtrl";
				break;
			case 14:
				strFieldName = "CalCode";
				break;
			case 15:
				strFieldName = "CalResultType";
				break;
			case 16:
				strFieldName = "DefaultValue";
				break;
			case 17:
				strFieldName = "CalCtrlFlag";
				break;
			case 18:
				strFieldName = "FeeCalCode";
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCtrlName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCtrlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimEngineDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMAX") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmPeriodMAXFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMAXCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMIN") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmPeriodMINFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMINCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalResultType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
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
