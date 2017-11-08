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
import com.sinosoft.lis.db.PD_LMEdorBonusZTDB;

/*
 * <p>ClassName: PD_LMEdorBonusZTSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMEdorBonusZTSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMEdorBonusZTSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 期缴时间间隔 */
	private String CycPayIntvType;
	/** 算法编码 */
	private String CycPayCalCode;
	/** 退保生存金计算类型 */
	private String LiveGetType;
	/** 计算方式参考 */
	private String CalCodeType;
	/** 退保年度计算类型 */
	private String ZTYearType;
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

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMEdorBonusZTSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "CycPayIntvType";

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
		PD_LMEdorBonusZTSchema cloned = (PD_LMEdorBonusZTSchema)super.clone();
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
	* 使用另外一个 PD_LMEdorBonusZTSchema 对象给 Schema 赋值
	* @param: aPD_LMEdorBonusZTSchema PD_LMEdorBonusZTSchema
	**/
	public void setSchema(PD_LMEdorBonusZTSchema aPD_LMEdorBonusZTSchema)
	{
		this.RiskCode = aPD_LMEdorBonusZTSchema.getRiskCode();
		this.DutyCode = aPD_LMEdorBonusZTSchema.getDutyCode();
		this.CycPayIntvType = aPD_LMEdorBonusZTSchema.getCycPayIntvType();
		this.CycPayCalCode = aPD_LMEdorBonusZTSchema.getCycPayCalCode();
		this.LiveGetType = aPD_LMEdorBonusZTSchema.getLiveGetType();
		this.CalCodeType = aPD_LMEdorBonusZTSchema.getCalCodeType();
		this.ZTYearType = aPD_LMEdorBonusZTSchema.getZTYearType();
		this.Operator = aPD_LMEdorBonusZTSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMEdorBonusZTSchema.getMakeDate());
		this.MakeTime = aPD_LMEdorBonusZTSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMEdorBonusZTSchema.getModifyDate());
		this.ModifyTime = aPD_LMEdorBonusZTSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMEdorBonusZTSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMEdorBonusZTSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMEdorBonusZTSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMEdorBonusZTSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMEdorBonusZTSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMEdorBonusZTSchema.getStandbyflag6();
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

			if( rs.getString("CalCodeType") == null )
				this.CalCodeType = null;
			else
				this.CalCodeType = rs.getString("CalCodeType").trim();

			if( rs.getString("ZTYearType") == null )
				this.ZTYearType = null;
			else
				this.ZTYearType = rs.getString("ZTYearType").trim();

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
			logger.debug("数据库中的PD_LMEdorBonusZT表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorBonusZTSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMEdorBonusZTSchema getSchema()
	{
		PD_LMEdorBonusZTSchema aPD_LMEdorBonusZTSchema = new PD_LMEdorBonusZTSchema();
		aPD_LMEdorBonusZTSchema.setSchema(this);
		return aPD_LMEdorBonusZTSchema;
	}

	public PD_LMEdorBonusZTDB getDB()
	{
		PD_LMEdorBonusZTDB aDBOper = new PD_LMEdorBonusZTDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMEdorBonusZT描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayIntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZTYearType)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMEdorBonusZT>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CycPayIntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CycPayCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LiveGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ZTYearType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorBonusZTSchema";
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
		if (FCode.equalsIgnoreCase("CalCodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeType));
		}
		if (FCode.equalsIgnoreCase("ZTYearType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZTYearType));
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
				strFieldValue = StrTool.GBKToUnicode(CycPayIntvType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CycPayCalCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LiveGetType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCodeType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ZTYearType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 14:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 15:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 16:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 17:
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
		PD_LMEdorBonusZTSchema other = (PD_LMEdorBonusZTSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& CycPayIntvType.equals(other.getCycPayIntvType())
			&& CycPayCalCode.equals(other.getCycPayCalCode())
			&& LiveGetType.equals(other.getLiveGetType())
			&& CalCodeType.equals(other.getCalCodeType())
			&& ZTYearType.equals(other.getZTYearType())
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
		if( strFieldName.equals("CycPayIntvType") ) {
			return 2;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return 3;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return 4;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return 5;
		}
		if( strFieldName.equals("ZTYearType") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 12;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 13;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 14;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 15;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 16;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 17;
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
				strFieldName = "CycPayIntvType";
				break;
			case 3:
				strFieldName = "CycPayCalCode";
				break;
			case 4:
				strFieldName = "LiveGetType";
				break;
			case 5:
				strFieldName = "CalCodeType";
				break;
			case 6:
				strFieldName = "ZTYearType";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
				break;
			case 12:
				strFieldName = "Standbyflag1";
				break;
			case 13:
				strFieldName = "Standbyflag2";
				break;
			case 14:
				strFieldName = "Standbyflag3";
				break;
			case 15:
				strFieldName = "Standbyflag4";
				break;
			case 16:
				strFieldName = "Standbyflag5";
				break;
			case 17:
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
		if( strFieldName.equals("CycPayIntvType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZTYearType") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
