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
import com.sinosoft.lis.db.LARateCommisionBDB;

/*
 * <p>ClassName: LARateCommisionBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LARateCommisionBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LARateCommisionBSchema.class);

	// @Field
	/** 转储号码 */
	private String EdorNo;
	/** 转储类型 */
	private String EdorType;
	/** 展业类型 */
	private String BranchType;
	/** 险种 */
	private String RiskCode;
	/** 有效起期 */
	private Date StartDate;
	/** 有效止期 */
	private Date EndDate;
	/** 性别 */
	private String sex;
	/** 投保年龄 */
	private int AppAge;
	/** 保险年期 */
	private int Year;
	/** 交费间隔 */
	private String PayIntv;
	/** 保单年度 */
	private int CurYear;
	/** 要素1 */
	private String F01;
	/** 要素2 */
	private String F02;
	/** 要素3 */
	private double F03;
	/** 要素4 */
	private double F04;
	/** 要素5 */
	private String F05;
	/** 要素6 */
	private String F06;
	/** 比率 */
	private double rate;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 管理机构 */
	private String ManageCom;
	/** 费率版本 */
	private String VersionType;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LARateCommisionBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[19];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "BranchType";
		pk[3] = "RiskCode";
		pk[4] = "StartDate";
		pk[5] = "EndDate";
		pk[6] = "sex";
		pk[7] = "AppAge";
		pk[8] = "Year";
		pk[9] = "PayIntv";
		pk[10] = "CurYear";
		pk[11] = "F01";
		pk[12] = "F02";
		pk[13] = "F03";
		pk[14] = "F04";
		pk[15] = "F05";
		pk[16] = "F06";
		pk[17] = "ManageCom";
		pk[18] = "VersionType";

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
		LARateCommisionBSchema cloned = (LARateCommisionBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	/**
	* 01-删除备份<p>
	* 02-修改备份
	*/
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
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

	public String getsex()
	{
		return sex;
	}
	public void setsex(String asex)
	{
		sex = asex;
	}
	public int getAppAge()
	{
		return AppAge;
	}
	public void setAppAge(int aAppAge)
	{
		AppAge = aAppAge;
	}
	public void setAppAge(String aAppAge)
	{
		if (aAppAge != null && !aAppAge.equals(""))
		{
			Integer tInteger = new Integer(aAppAge);
			int i = tInteger.intValue();
			AppAge = i;
		}
	}

	public int getYear()
	{
		return Year;
	}
	public void setYear(int aYear)
	{
		Year = aYear;
	}
	public void setYear(String aYear)
	{
		if (aYear != null && !aYear.equals(""))
		{
			Integer tInteger = new Integer(aYear);
			int i = tInteger.intValue();
			Year = i;
		}
	}

	public String getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public int getCurYear()
	{
		return CurYear;
	}
	public void setCurYear(int aCurYear)
	{
		CurYear = aCurYear;
	}
	public void setCurYear(String aCurYear)
	{
		if (aCurYear != null && !aCurYear.equals(""))
		{
			Integer tInteger = new Integer(aCurYear);
			int i = tInteger.intValue();
			CurYear = i;
		}
	}

	/**
	* 111602的保费
	*/
	public String getF01()
	{
		return F01;
	}
	public void setF01(String aF01)
	{
		F01 = aF01;
	}
	public String getF02()
	{
		return F02;
	}
	public void setF02(String aF02)
	{
		F02 = aF02;
	}
	public double getF03()
	{
		return F03;
	}
	public void setF03(double aF03)
	{
		F03 = aF03;
	}
	public void setF03(String aF03)
	{
		if (aF03 != null && !aF03.equals(""))
		{
			Double tDouble = new Double(aF03);
			double d = tDouble.doubleValue();
			F03 = d;
		}
	}

	public double getF04()
	{
		return F04;
	}
	public void setF04(double aF04)
	{
		F04 = aF04;
	}
	public void setF04(String aF04)
	{
		if (aF04 != null && !aF04.equals(""))
		{
			Double tDouble = new Double(aF04);
			double d = tDouble.doubleValue();
			F04 = d;
		}
	}

	/**
	* 代理机构
	*/
	public String getF05()
	{
		return F05;
	}
	public void setF05(String aF05)
	{
		F05 = aF05;
	}
	/**
	* 保单类型<p>
	* 0-正常<p>
	* 1-优惠
	*/
	public String getF06()
	{
		return F06;
	}
	public void setF06(String aF06)
	{
		F06 = aF06;
	}
	public double getrate()
	{
		return rate;
	}
	public void setrate(double arate)
	{
		rate = arate;
	}
	public void setrate(String arate)
	{
		if (arate != null && !arate.equals(""))
		{
			Double tDouble = new Double(arate);
			double d = tDouble.doubleValue();
			rate = d;
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getVersionType()
	{
		return VersionType;
	}
	public void setVersionType(String aVersionType)
	{
		VersionType = aVersionType;
	}

	/**
	* 使用另外一个 LARateCommisionBSchema 对象给 Schema 赋值
	* @param: aLARateCommisionBSchema LARateCommisionBSchema
	**/
	public void setSchema(LARateCommisionBSchema aLARateCommisionBSchema)
	{
		this.EdorNo = aLARateCommisionBSchema.getEdorNo();
		this.EdorType = aLARateCommisionBSchema.getEdorType();
		this.BranchType = aLARateCommisionBSchema.getBranchType();
		this.RiskCode = aLARateCommisionBSchema.getRiskCode();
		this.StartDate = fDate.getDate( aLARateCommisionBSchema.getStartDate());
		this.EndDate = fDate.getDate( aLARateCommisionBSchema.getEndDate());
		this.sex = aLARateCommisionBSchema.getsex();
		this.AppAge = aLARateCommisionBSchema.getAppAge();
		this.Year = aLARateCommisionBSchema.getYear();
		this.PayIntv = aLARateCommisionBSchema.getPayIntv();
		this.CurYear = aLARateCommisionBSchema.getCurYear();
		this.F01 = aLARateCommisionBSchema.getF01();
		this.F02 = aLARateCommisionBSchema.getF02();
		this.F03 = aLARateCommisionBSchema.getF03();
		this.F04 = aLARateCommisionBSchema.getF04();
		this.F05 = aLARateCommisionBSchema.getF05();
		this.F06 = aLARateCommisionBSchema.getF06();
		this.rate = aLARateCommisionBSchema.getrate();
		this.Operator = aLARateCommisionBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLARateCommisionBSchema.getMakeDate());
		this.MakeTime = aLARateCommisionBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLARateCommisionBSchema.getModifyDate());
		this.ModifyTime = aLARateCommisionBSchema.getModifyTime();
		this.ManageCom = aLARateCommisionBSchema.getManageCom();
		this.VersionType = aLARateCommisionBSchema.getVersionType();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("sex") == null )
				this.sex = null;
			else
				this.sex = rs.getString("sex").trim();

			this.AppAge = rs.getInt("AppAge");
			this.Year = rs.getInt("Year");
			if( rs.getString("PayIntv") == null )
				this.PayIntv = null;
			else
				this.PayIntv = rs.getString("PayIntv").trim();

			this.CurYear = rs.getInt("CurYear");
			if( rs.getString("F01") == null )
				this.F01 = null;
			else
				this.F01 = rs.getString("F01").trim();

			if( rs.getString("F02") == null )
				this.F02 = null;
			else
				this.F02 = rs.getString("F02").trim();

			this.F03 = rs.getDouble("F03");
			this.F04 = rs.getDouble("F04");
			if( rs.getString("F05") == null )
				this.F05 = null;
			else
				this.F05 = rs.getString("F05").trim();

			if( rs.getString("F06") == null )
				this.F06 = null;
			else
				this.F06 = rs.getString("F06").trim();

			this.rate = rs.getDouble("rate");
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

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("VersionType") == null )
				this.VersionType = null;
			else
				this.VersionType = rs.getString("VersionType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LARateCommisionB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LARateCommisionBSchema getSchema()
	{
		LARateCommisionBSchema aLARateCommisionBSchema = new LARateCommisionBSchema();
		aLARateCommisionBSchema.setSchema(this);
		return aLARateCommisionBSchema;
	}

	public LARateCommisionBDB getDB()
	{
		LARateCommisionBDB aDBOper = new LARateCommisionBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLARateCommisionB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Year));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F01)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F02)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F03));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F04));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F05)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F06)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VersionType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLARateCommisionB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			Year= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			PayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CurYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			F01 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			F02 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			F03 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			F04 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			F05 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			F06 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			VersionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARateCommisionBSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(sex));
		}
		if (FCode.equalsIgnoreCase("AppAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppAge));
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Year));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("CurYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYear));
		}
		if (FCode.equalsIgnoreCase("F01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F01));
		}
		if (FCode.equalsIgnoreCase("F02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F02));
		}
		if (FCode.equalsIgnoreCase("F03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F03));
		}
		if (FCode.equalsIgnoreCase("F04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F04));
		}
		if (FCode.equalsIgnoreCase("F05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F05));
		}
		if (FCode.equalsIgnoreCase("F06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F06));
		}
		if (FCode.equalsIgnoreCase("rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(rate));
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("VersionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VersionType));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(sex);
				break;
			case 7:
				strFieldValue = String.valueOf(AppAge);
				break;
			case 8:
				strFieldValue = String.valueOf(Year);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PayIntv);
				break;
			case 10:
				strFieldValue = String.valueOf(CurYear);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(F01);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(F02);
				break;
			case 13:
				strFieldValue = String.valueOf(F03);
				break;
			case 14:
				strFieldValue = String.valueOf(F04);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(F05);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(F06);
				break;
			case 17:
				strFieldValue = String.valueOf(rate);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(VersionType);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
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
		if (FCode.equalsIgnoreCase("sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				sex = FValue.trim();
			}
			else
				sex = null;
		}
		if (FCode.equalsIgnoreCase("AppAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AppAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Year = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayIntv = FValue.trim();
			}
			else
				PayIntv = null;
		}
		if (FCode.equalsIgnoreCase("CurYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("F01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F01 = FValue.trim();
			}
			else
				F01 = null;
		}
		if (FCode.equalsIgnoreCase("F02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F02 = FValue.trim();
			}
			else
				F02 = null;
		}
		if (FCode.equalsIgnoreCase("F03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F03 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F04 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F05 = FValue.trim();
			}
			else
				F05 = null;
		}
		if (FCode.equalsIgnoreCase("F06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F06 = FValue.trim();
			}
			else
				F06 = null;
		}
		if (FCode.equalsIgnoreCase("rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				rate = d;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("VersionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VersionType = FValue.trim();
			}
			else
				VersionType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LARateCommisionBSchema other = (LARateCommisionBSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& BranchType.equals(other.getBranchType())
			&& RiskCode.equals(other.getRiskCode())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& sex.equals(other.getsex())
			&& AppAge == other.getAppAge()
			&& Year == other.getYear()
			&& PayIntv.equals(other.getPayIntv())
			&& CurYear == other.getCurYear()
			&& F01.equals(other.getF01())
			&& F02.equals(other.getF02())
			&& F03 == other.getF03()
			&& F04 == other.getF04()
			&& F05.equals(other.getF05())
			&& F06.equals(other.getF06())
			&& rate == other.getrate()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ManageCom.equals(other.getManageCom())
			&& VersionType.equals(other.getVersionType());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("BranchType") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("StartDate") ) {
			return 4;
		}
		if( strFieldName.equals("EndDate") ) {
			return 5;
		}
		if( strFieldName.equals("sex") ) {
			return 6;
		}
		if( strFieldName.equals("AppAge") ) {
			return 7;
		}
		if( strFieldName.equals("Year") ) {
			return 8;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 9;
		}
		if( strFieldName.equals("CurYear") ) {
			return 10;
		}
		if( strFieldName.equals("F01") ) {
			return 11;
		}
		if( strFieldName.equals("F02") ) {
			return 12;
		}
		if( strFieldName.equals("F03") ) {
			return 13;
		}
		if( strFieldName.equals("F04") ) {
			return 14;
		}
		if( strFieldName.equals("F05") ) {
			return 15;
		}
		if( strFieldName.equals("F06") ) {
			return 16;
		}
		if( strFieldName.equals("rate") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 23;
		}
		if( strFieldName.equals("VersionType") ) {
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "BranchType";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "StartDate";
				break;
			case 5:
				strFieldName = "EndDate";
				break;
			case 6:
				strFieldName = "sex";
				break;
			case 7:
				strFieldName = "AppAge";
				break;
			case 8:
				strFieldName = "Year";
				break;
			case 9:
				strFieldName = "PayIntv";
				break;
			case 10:
				strFieldName = "CurYear";
				break;
			case 11:
				strFieldName = "F01";
				break;
			case 12:
				strFieldName = "F02";
				break;
			case 13:
				strFieldName = "F03";
				break;
			case 14:
				strFieldName = "F04";
				break;
			case 15:
				strFieldName = "F05";
				break;
			case 16:
				strFieldName = "F06";
				break;
			case 17:
				strFieldName = "rate";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
				strFieldName = "ModifyTime";
				break;
			case 23:
				strFieldName = "ManageCom";
				break;
			case 24:
				strFieldName = "VersionType";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Year") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("F01") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F02") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F03") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F04") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F05") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F06") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("rate") ) {
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VersionType") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
