

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
import com.sinosoft.lis.db.PD_LMUWDB;

/*
 * <p>ClassName: PD_LMUWSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMUWSchema implements Schema, Cloneable
{
	// @Field
	/** 核保编码 */
	private String UWCode;
	/** 算法编码 */
	private String CalCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 关联保单类型 */
	private String RelaPolType;
	/** 核保类型 */
	private String UWType;
	/** 核保顺序号 */
	private int UWOrder;
	/** 其他算法 */
	private String OthCalCode;
	/** 备注 */
	private String Remark;
	/** 核保级别 */
	private String UWGrade;
	/** 核保结果控制 */
	private String UWResult;
	/** 核保通过标记 */
	private String PassFlag;
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
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag6 */
	private double Standbyflag6;
	/** Standbyflag5 */
	private double Standbyflag5;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMUWSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "UWCode";

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
		PD_LMUWSchema cloned = (PD_LMUWSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUWCode()
	{
		return UWCode;
	}
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
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
	* 承保核保 G--分单(Group) I--个单(Individual) 保全核保 GC--团单总单(Group Contract) GP-团单险种单 GIC--团单下个单总单(Group Individual Contract) GIP--团单下个单险种单(Group Individual Pol) IC--个单总单(Individual Contract) IP--个单下险种单(Individual Pol)
	*/
	public String getRelaPolType()
	{
		return RelaPolType;
	}
	public void setRelaPolType(String aRelaPolType)
	{
		RelaPolType = aRelaPolType;
	}
	/**
	* 1--个单、团单险种的承保核保 11 -- 个单、团单险种承保核保的公共规则 12 －－ 个单、团单人工核保上报核保级别 13 －－ 个单人工核保非标准体核保级别校验 14 －－ 个单人工核保拒保延期级别校验 15 －－ 团体下个人核保规则 16 －－ 团体下个人上报核保级别 19 －－ 个单、团单合同核保规则 2--保全核保 21 -- 保全个单承保核保的公共规则 22 －－ 保全个单人工核保上报核保级别 23 －－ 保全个单人工核保非标准体核保级别校验 24 －－ 保全个单人工核保拒保延期级别校验 25 －－ 保全团体下个人核保规则 XX--保全项目 3--续保核保、 4--核赔
	*/
	public String getUWType()
	{
		return UWType;
	}
	public void setUWType(String aUWType)
	{
		UWType = aUWType;
	}
	public int getUWOrder()
	{
		return UWOrder;
	}
	public void setUWOrder(int aUWOrder)
	{
		UWOrder = aUWOrder;
	}
	public void setUWOrder(String aUWOrder)
	{
		if (aUWOrder != null && !aUWOrder.equals(""))
		{
			Integer tInteger = new Integer(aUWOrder);
			int i = tInteger.intValue();
			UWOrder = i;
		}
	}

	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		OthCalCode = aOthCalCode;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* 核保核赔时有用,分级核保核赔.
	*/
	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		UWGrade = aUWGrade;
	}
	/**
	* 对核保核赔,返回上报级别. 对控制描述,返回控制类型: 对 checkfield F(f) 返回值,不显示. 控制附险，定额单等 N(n) 显示提示信息,display p_rta1t H(h) 不显示提示信息,不display p_rta1t Y(y) 不合要求才显示,显示提示信息
	*/
	public String getUWResult()
	{
		return UWResult;
	}
	public void setUWResult(String aUWResult)
	{
		UWResult = aUWResult;
	}
	/**
	* 核保可通过标记(“N”-不能核保通过标志)在remark中记录标记, 在外部关联程序修改此标记后,可以由核保员决定是否核保通过
	*/
	public String getPassFlag()
	{
		return PassFlag;
	}
	public void setPassFlag(String aPassFlag)
	{
		PassFlag = aPassFlag;
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
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
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


	/**
	* 使用另外一个 PD_LMUWSchema 对象给 Schema 赋值
	* @param: aPD_LMUWSchema PD_LMUWSchema
	**/
	public void setSchema(PD_LMUWSchema aPD_LMUWSchema)
	{
		this.UWCode = aPD_LMUWSchema.getUWCode();
		this.CalCode = aPD_LMUWSchema.getCalCode();
		this.RiskCode = aPD_LMUWSchema.getRiskCode();
		this.RiskVer = aPD_LMUWSchema.getRiskVer();
		this.RiskName = aPD_LMUWSchema.getRiskName();
		this.RelaPolType = aPD_LMUWSchema.getRelaPolType();
		this.UWType = aPD_LMUWSchema.getUWType();
		this.UWOrder = aPD_LMUWSchema.getUWOrder();
		this.OthCalCode = aPD_LMUWSchema.getOthCalCode();
		this.Remark = aPD_LMUWSchema.getRemark();
		this.UWGrade = aPD_LMUWSchema.getUWGrade();
		this.UWResult = aPD_LMUWSchema.getUWResult();
		this.PassFlag = aPD_LMUWSchema.getPassFlag();
		this.Operator = aPD_LMUWSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMUWSchema.getMakeDate());
		this.MakeTime = aPD_LMUWSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMUWSchema.getModifyDate());
		this.ModifyTime = aPD_LMUWSchema.getModifyTime();
		this.Standbyflag2 = aPD_LMUWSchema.getStandbyflag2();
		this.Standbyflag1 = aPD_LMUWSchema.getStandbyflag1();
		this.Standbyflag3 = aPD_LMUWSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMUWSchema.getStandbyflag4();
		this.Standbyflag6 = aPD_LMUWSchema.getStandbyflag6();
		this.Standbyflag5 = aPD_LMUWSchema.getStandbyflag5();
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
			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

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

			if( rs.getString("RelaPolType") == null )
				this.RelaPolType = null;
			else
				this.RelaPolType = rs.getString("RelaPolType").trim();

			if( rs.getString("UWType") == null )
				this.UWType = null;
			else
				this.UWType = rs.getString("UWType").trim();

			this.UWOrder = rs.getInt("UWOrder");
			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

			if( rs.getString("UWResult") == null )
				this.UWResult = null;
			else
				this.UWResult = rs.getString("UWResult").trim();

			if( rs.getString("PassFlag") == null )
				this.PassFlag = null;
			else
				this.PassFlag = rs.getString("PassFlag").trim();

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

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMUW表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMUWSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMUWSchema getSchema()
	{
		PD_LMUWSchema aPD_LMUWSchema = new PD_LMUWSchema();
		aPD_LMUWSchema.setSchema(this);
		return aPD_LMUWSchema;
	}

	public PD_LMUWDB getDB()
	{
		PD_LMUWDB aDBOper = new PD_LMUWDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMUW描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaPolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMUW>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RelaPolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UWOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMUWSchema";
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
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
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
		if (FCode.equalsIgnoreCase("RelaPolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaPolType));
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWType));
		}
		if (FCode.equalsIgnoreCase("UWOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOrder));
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthCalCode));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
		}
		if (FCode.equalsIgnoreCase("UWResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWResult));
		}
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PassFlag));
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
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
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
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RelaPolType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UWType);
				break;
			case 7:
				strFieldValue = String.valueOf(UWOrder);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWResult);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PassFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 20:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 21:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 22:
				strFieldValue = String.valueOf(Standbyflag6);
				break;
			case 23:
				strFieldValue = String.valueOf(Standbyflag5);
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

		if (FCode.equalsIgnoreCase("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
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
		if (FCode.equalsIgnoreCase("RelaPolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaPolType = FValue.trim();
			}
			else
				RelaPolType = null;
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWType = FValue.trim();
			}
			else
				UWType = null;
		}
		if (FCode.equalsIgnoreCase("UWOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWOrder = i;
			}
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
		}
		if (FCode.equalsIgnoreCase("UWResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWResult = FValue.trim();
			}
			else
				UWResult = null;
		}
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PassFlag = FValue.trim();
			}
			else
				PassFlag = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMUWSchema other = (PD_LMUWSchema)otherObject;
		return
			UWCode.equals(other.getUWCode())
			&& CalCode.equals(other.getCalCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& RelaPolType.equals(other.getRelaPolType())
			&& UWType.equals(other.getUWType())
			&& UWOrder == other.getUWOrder()
			&& OthCalCode.equals(other.getOthCalCode())
			&& Remark.equals(other.getRemark())
			&& UWGrade.equals(other.getUWGrade())
			&& UWResult.equals(other.getUWResult())
			&& PassFlag.equals(other.getPassFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag6 == other.getStandbyflag6()
			&& Standbyflag5 == other.getStandbyflag5();
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
		if( strFieldName.equals("UWCode") ) {
			return 0;
		}
		if( strFieldName.equals("CalCode") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 3;
		}
		if( strFieldName.equals("RiskName") ) {
			return 4;
		}
		if( strFieldName.equals("RelaPolType") ) {
			return 5;
		}
		if( strFieldName.equals("UWType") ) {
			return 6;
		}
		if( strFieldName.equals("UWOrder") ) {
			return 7;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("UWGrade") ) {
			return 10;
		}
		if( strFieldName.equals("UWResult") ) {
			return 11;
		}
		if( strFieldName.equals("PassFlag") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 22;
		}
		if( strFieldName.equals("Standbyflag5") ) {
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
				strFieldName = "UWCode";
				break;
			case 1:
				strFieldName = "CalCode";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "RiskVer";
				break;
			case 4:
				strFieldName = "RiskName";
				break;
			case 5:
				strFieldName = "RelaPolType";
				break;
			case 6:
				strFieldName = "UWType";
				break;
			case 7:
				strFieldName = "UWOrder";
				break;
			case 8:
				strFieldName = "OthCalCode";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "UWGrade";
				break;
			case 11:
				strFieldName = "UWResult";
				break;
			case 12:
				strFieldName = "PassFlag";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "Standbyflag2";
				break;
			case 19:
				strFieldName = "Standbyflag1";
				break;
			case 20:
				strFieldName = "Standbyflag3";
				break;
			case 21:
				strFieldName = "Standbyflag4";
				break;
			case 22:
				strFieldName = "Standbyflag6";
				break;
			case 23:
				strFieldName = "Standbyflag5";
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
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaPolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassFlag") ) {
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
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag5") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
