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
import com.sinosoft.lis.db.PD_LMRiskPremRateDB;

/*
 * <p>ClassName: PD_LMRiskPremRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2010-09-12
 */
public class PD_LMRiskPremRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMRiskPremRateSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 折扣类型 */
	private String RateType;
	/** 险种参数代码 */
	private String ParamsType;
	/** 险种参数名称 */
	private String ParamsName;
	/** 参数值上限 */
	private double MaxParamsValue;
	/** 参数值下限 */
	private double MinParamsValue;
	/** 参数单位 */
	private String ParamsFlag;
	/** 保费折扣率 */
	private double PremRate;
	/** 保费折扣率算法 */
	private String CalCode;
	/** 与其他算法的关系 */
	private String PremRateRale;
	/** 起始时间 */
	private Date StartDate;
	/** 终止时间 */
	private Date EndDate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskPremRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "RiskCode";
		pk[1] = "RateType";
		pk[2] = "ParamsType";
		pk[3] = "MaxParamsValue";
		pk[4] = "MinParamsValue";
		pk[5] = "ParamsFlag";

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
                PD_LMRiskPremRateSchema cloned = (PD_LMRiskPremRateSchema)super.clone();
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
	public String getRateType()
	{
		return RateType;
	}
	public void setRateType(String aRateType)
	{
		RateType = aRateType;
	}
	public String getParamsType()
	{
		return ParamsType;
	}
	public void setParamsType(String aParamsType)
	{
		ParamsType = aParamsType;
	}
	public String getParamsName()
	{
		return ParamsName;
	}
	public void setParamsName(String aParamsName)
	{
		ParamsName = aParamsName;
	}
	public double getMaxParamsValue()
	{
		return MaxParamsValue;
	}
	public void setMaxParamsValue(double aMaxParamsValue)
	{
		MaxParamsValue = aMaxParamsValue;
	}
	public void setMaxParamsValue(String aMaxParamsValue)
	{
		if (aMaxParamsValue != null && !aMaxParamsValue.equals(""))
		{
			Double tDouble = new Double(aMaxParamsValue);
			double d = tDouble.doubleValue();
			MaxParamsValue = d;
		}
	}

	public double getMinParamsValue()
	{
		return MinParamsValue;
	}
	public void setMinParamsValue(double aMinParamsValue)
	{
		MinParamsValue = aMinParamsValue;
	}
	public void setMinParamsValue(String aMinParamsValue)
	{
		if (aMinParamsValue != null && !aMinParamsValue.equals(""))
		{
			Double tDouble = new Double(aMinParamsValue);
			double d = tDouble.doubleValue();
			MinParamsValue = d;
		}
	}

	public String getParamsFlag()
	{
		return ParamsFlag;
	}
	public void setParamsFlag(String aParamsFlag)
	{
		ParamsFlag = aParamsFlag;
	}
	public double getPremRate()
	{
		return PremRate;
	}
	public void setPremRate(double aPremRate)
	{
		PremRate = aPremRate;
	}
	public void setPremRate(String aPremRate)
	{
		if (aPremRate != null && !aPremRate.equals(""))
		{
			Double tDouble = new Double(aPremRate);
			double d = tDouble.doubleValue();
			PremRate = d;
		}
	}

	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getPremRateRale()
	{
		return PremRateRale;
	}
	public void setPremRateRale(String aPremRateRale)
	{
		PremRateRale = aPremRateRale;
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

	/**
	* 使用另外一个 PD_LMRiskPremRateSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskPremRateSchema PD_LMRiskPremRateSchema
	**/
	public void setSchema(PD_LMRiskPremRateSchema aPD_LMRiskPremRateSchema)
	{
		this.RiskCode = aPD_LMRiskPremRateSchema.getRiskCode();
		this.RiskVer = aPD_LMRiskPremRateSchema.getRiskVer();
		this.RateType = aPD_LMRiskPremRateSchema.getRateType();
		this.ParamsType = aPD_LMRiskPremRateSchema.getParamsType();
		this.ParamsName = aPD_LMRiskPremRateSchema.getParamsName();
		this.MaxParamsValue = aPD_LMRiskPremRateSchema.getMaxParamsValue();
		this.MinParamsValue = aPD_LMRiskPremRateSchema.getMinParamsValue();
		this.ParamsFlag = aPD_LMRiskPremRateSchema.getParamsFlag();
		this.PremRate = aPD_LMRiskPremRateSchema.getPremRate();
		this.CalCode = aPD_LMRiskPremRateSchema.getCalCode();
		this.PremRateRale = aPD_LMRiskPremRateSchema.getPremRateRale();
		this.StartDate = fDate.getDate( aPD_LMRiskPremRateSchema.getStartDate());
		this.EndDate = fDate.getDate( aPD_LMRiskPremRateSchema.getEndDate());
		this.Operator = aPD_LMRiskPremRateSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskPremRateSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskPremRateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskPremRateSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskPremRateSchema.getModifyTime();
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

			if( rs.getString("RateType") == null )
				this.RateType = null;
			else
				this.RateType = rs.getString("RateType").trim();

			if( rs.getString("ParamsType") == null )
				this.ParamsType = null;
			else
				this.ParamsType = rs.getString("ParamsType").trim();

			if( rs.getString("ParamsName") == null )
				this.ParamsName = null;
			else
				this.ParamsName = rs.getString("ParamsName").trim();

			this.MaxParamsValue = rs.getDouble("MaxParamsValue");
			this.MinParamsValue = rs.getDouble("MinParamsValue");
			if( rs.getString("ParamsFlag") == null )
				this.ParamsFlag = null;
			else
				this.ParamsFlag = rs.getString("ParamsFlag").trim();

			this.PremRate = rs.getDouble("PremRate");
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("PremRateRale") == null )
				this.PremRateRale = null;
			else
				this.PremRateRale = rs.getString("PremRateRale").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMRiskPremRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskPremRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskPremRateSchema getSchema()
	{
		PD_LMRiskPremRateSchema aPD_LMRiskPremRateSchema = new PD_LMRiskPremRateSchema();
		aPD_LMRiskPremRateSchema.setSchema(this);
		return aPD_LMRiskPremRateSchema;
	}

	public PD_LMRiskPremRateDB getDB()
	{
		PD_LMRiskPremRateDB aDBOper = new PD_LMRiskPremRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskPremRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RateType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParamsType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParamsName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(MaxParamsValue));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(MinParamsValue));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParamsFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(PremRate));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(PremRateRale)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskPremRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ParamsType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ParamsName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MaxParamsValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			MinParamsValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			ParamsFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PremRateRale = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskPremRateSchema";
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
		if (FCode.equalsIgnoreCase("RateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateType));
		}
		if (FCode.equalsIgnoreCase("ParamsType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsType));
		}
		if (FCode.equalsIgnoreCase("ParamsName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsName));
		}
		if (FCode.equalsIgnoreCase("MaxParamsValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxParamsValue));
		}
		if (FCode.equalsIgnoreCase("MinParamsValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinParamsValue));
		}
		if (FCode.equalsIgnoreCase("ParamsFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsFlag));
		}
		if (FCode.equalsIgnoreCase("PremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremRate));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("PremRateRale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremRateRale));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(RateType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ParamsType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ParamsName);
				break;
			case 5:
				strFieldValue = String.valueOf(MaxParamsValue);
				break;
			case 6:
				strFieldValue = String.valueOf(MinParamsValue);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ParamsFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(PremRate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PremRateRale);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
		if (FCode.equalsIgnoreCase("RateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateType = FValue.trim();
			}
			else
				RateType = null;
		}
		if (FCode.equalsIgnoreCase("ParamsType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsType = FValue.trim();
			}
			else
				ParamsType = null;
		}
		if (FCode.equalsIgnoreCase("ParamsName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsName = FValue.trim();
			}
			else
				ParamsName = null;
		}
		if (FCode.equalsIgnoreCase("MaxParamsValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxParamsValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("MinParamsValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MinParamsValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamsFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsFlag = FValue.trim();
			}
			else
				ParamsFlag = null;
		}
		if (FCode.equalsIgnoreCase("PremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremRate = d;
			}
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
		if (FCode.equalsIgnoreCase("PremRateRale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremRateRale = FValue.trim();
			}
			else
				PremRateRale = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMRiskPremRateSchema other = (PD_LMRiskPremRateSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RateType.equals(other.getRateType())
			&& ParamsType.equals(other.getParamsType())
			&& ParamsName.equals(other.getParamsName())
			&& MaxParamsValue == other.getMaxParamsValue()
			&& MinParamsValue == other.getMinParamsValue()
			&& ParamsFlag.equals(other.getParamsFlag())
			&& PremRate == other.getPremRate()
			&& CalCode.equals(other.getCalCode())
			&& PremRateRale.equals(other.getPremRateRale())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("RateType") ) {
			return 2;
		}
		if( strFieldName.equals("ParamsType") ) {
			return 3;
		}
		if( strFieldName.equals("ParamsName") ) {
			return 4;
		}
		if( strFieldName.equals("MaxParamsValue") ) {
			return 5;
		}
		if( strFieldName.equals("MinParamsValue") ) {
			return 6;
		}
		if( strFieldName.equals("ParamsFlag") ) {
			return 7;
		}
		if( strFieldName.equals("PremRate") ) {
			return 8;
		}
		if( strFieldName.equals("CalCode") ) {
			return 9;
		}
		if( strFieldName.equals("PremRateRale") ) {
			return 10;
		}
		if( strFieldName.equals("StartDate") ) {
			return 11;
		}
		if( strFieldName.equals("EndDate") ) {
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
				strFieldName = "RateType";
				break;
			case 3:
				strFieldName = "ParamsType";
				break;
			case 4:
				strFieldName = "ParamsName";
				break;
			case 5:
				strFieldName = "MaxParamsValue";
				break;
			case 6:
				strFieldName = "MinParamsValue";
				break;
			case 7:
				strFieldName = "ParamsFlag";
				break;
			case 8:
				strFieldName = "PremRate";
				break;
			case 9:
				strFieldName = "CalCode";
				break;
			case 10:
				strFieldName = "PremRateRale";
				break;
			case 11:
				strFieldName = "StartDate";
				break;
			case 12:
				strFieldName = "EndDate";
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
		if( strFieldName.equals("RateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxParamsValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MinParamsValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamsFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremRateRale") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
