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
import com.sinosoft.lis.db.LMInsuAccRateDB;

/*
 * <p>ClassName: LMInsuAccRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_21
 */
public class LMInsuAccRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMInsuAccRateSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 结算日期 */
	private Date BalaDate;
	/** 利率应公布日期 */
	private Date SRateDate;
	/** 利率实际公布日期 */
	private Date ARateDate;
	/** 利率类型 */
	private String RateIntv;
	/** 利率 */
	private double Rate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 利率状态 */
	private String RateState;
	/** 结束日期 */
	private Date EndDate;
	/** 会计年度 */
	private int FiscalYear;
	/** 结算/分红标志 */
	private String Flag;
	/** 分红类型 */
	private String Bonustype;
	/** 开始日期 */
	private Date StartDate;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMInsuAccRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "InsuAccNo";
		pk[2] = "BalaDate";
		pk[3] = "RateIntv";

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
		LMInsuAccRateSchema cloned = (LMInsuAccRateSchema)super.clone();
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
		if(aRiskCode!=null && aRiskCode.length()>8)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值8");
		RiskCode = aRiskCode;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		if(aInsuAccNo!=null && aInsuAccNo.length()>20)
			throw new IllegalArgumentException("保险帐户号码InsuAccNo值"+aInsuAccNo+"的长度"+aInsuAccNo.length()+"大于最大值20");
		InsuAccNo = aInsuAccNo;
	}
	public String getBalaDate()
	{
		if( BalaDate != null )
			return fDate.getString(BalaDate);
		else
			return null;
	}
	public void setBalaDate(Date aBalaDate)
	{
		BalaDate = aBalaDate;
	}
	public void setBalaDate(String aBalaDate)
	{
		if (aBalaDate != null && !aBalaDate.equals("") )
		{
			BalaDate = fDate.getDate( aBalaDate );
		}
		else
			BalaDate = null;
	}

	public String getSRateDate()
	{
		if( SRateDate != null )
			return fDate.getString(SRateDate);
		else
			return null;
	}
	public void setSRateDate(Date aSRateDate)
	{
		SRateDate = aSRateDate;
	}
	public void setSRateDate(String aSRateDate)
	{
		if (aSRateDate != null && !aSRateDate.equals("") )
		{
			SRateDate = fDate.getDate( aSRateDate );
		}
		else
			SRateDate = null;
	}

	public String getARateDate()
	{
		if( ARateDate != null )
			return fDate.getString(ARateDate);
		else
			return null;
	}
	public void setARateDate(Date aARateDate)
	{
		ARateDate = aARateDate;
	}
	public void setARateDate(String aARateDate)
	{
		if (aARateDate != null && !aARateDate.equals("") )
		{
			ARateDate = fDate.getDate( aARateDate );
		}
		else
			ARateDate = null;
	}

	/**
	* Y －－ 年利率 M －－ 月利率 D －－ 日利率
	*/
	public String getRateIntv()
	{
		return RateIntv;
	}
	public void setRateIntv(String aRateIntv)
	{
		if(aRateIntv!=null && aRateIntv.length()>1)
			throw new IllegalArgumentException("利率类型RateIntv值"+aRateIntv+"的长度"+aRateIntv.length()+"大于最大值1");
		RateIntv = aRateIntv;
	}
	public double getRate()
	{
		return Rate;
	}
	public void setRate(double aRate)
	{
		Rate = aRate;
	}
	public void setRate(String aRate)
	{
		if (aRate != null && !aRate.equals(""))
		{
			Double tDouble = new Double(aRate);
			double d = tDouble.doubleValue();
			Rate = d;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	/**
	* 由于输入的利率要进行高级别人的复核 1--------已复核 0--------复核
	*/
	public String getRateState()
	{
		return RateState;
	}
	public void setRateState(String aRateState)
	{
		if(aRateState!=null && aRateState.length()>1)
			throw new IllegalArgumentException("利率状态RateState值"+aRateState+"的长度"+aRateState.length()+"大于最大值1");
		RateState = aRateState;
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

	public int getFiscalYear()
	{
		return FiscalYear;
	}
	public void setFiscalYear(int aFiscalYear)
	{
		FiscalYear = aFiscalYear;
	}
	public void setFiscalYear(String aFiscalYear)
	{
		if (aFiscalYear != null && !aFiscalYear.equals(""))
		{
			Integer tInteger = new Integer(aFiscalYear);
			int i = tInteger.intValue();
			FiscalYear = i;
		}
	}

	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		if(aFlag!=null && aFlag.length()>10)
			throw new IllegalArgumentException("结算/分红标志Flag值"+aFlag+"的长度"+aFlag.length()+"大于最大值10");
		Flag = aFlag;
	}
	/**
	* 1-优质客户,2-一般客户
	*/
	public String getBonustype()
	{
		return Bonustype;
	}
	public void setBonustype(String aBonustype)
	{
		if(aBonustype!=null && aBonustype.length()>10)
			throw new IllegalArgumentException("分红类型Bonustype值"+aBonustype+"的长度"+aBonustype.length()+"大于最大值10");
		Bonustype = aBonustype;
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


	/**
	* 使用另外一个 LMInsuAccRateSchema 对象给 Schema 赋值
	* @param: aLMInsuAccRateSchema LMInsuAccRateSchema
	**/
	public void setSchema(LMInsuAccRateSchema aLMInsuAccRateSchema)
	{
		this.RiskCode = aLMInsuAccRateSchema.getRiskCode();
		this.InsuAccNo = aLMInsuAccRateSchema.getInsuAccNo();
		this.BalaDate = fDate.getDate( aLMInsuAccRateSchema.getBalaDate());
		this.SRateDate = fDate.getDate( aLMInsuAccRateSchema.getSRateDate());
		this.ARateDate = fDate.getDate( aLMInsuAccRateSchema.getARateDate());
		this.RateIntv = aLMInsuAccRateSchema.getRateIntv();
		this.Rate = aLMInsuAccRateSchema.getRate();
		this.Operator = aLMInsuAccRateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLMInsuAccRateSchema.getMakeDate());
		this.MakeTime = aLMInsuAccRateSchema.getMakeTime();
		this.RateState = aLMInsuAccRateSchema.getRateState();
		this.EndDate = fDate.getDate( aLMInsuAccRateSchema.getEndDate());
		this.FiscalYear = aLMInsuAccRateSchema.getFiscalYear();
		this.Flag = aLMInsuAccRateSchema.getFlag();
		this.Bonustype = aLMInsuAccRateSchema.getBonustype();
		this.StartDate = fDate.getDate( aLMInsuAccRateSchema.getStartDate());
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

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			this.BalaDate = rs.getDate("BalaDate");
			this.SRateDate = rs.getDate("SRateDate");
			this.ARateDate = rs.getDate("ARateDate");
			if( rs.getString("RateIntv") == null )
				this.RateIntv = null;
			else
				this.RateIntv = rs.getString("RateIntv").trim();

			this.Rate = rs.getDouble("Rate");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("RateState") == null )
				this.RateState = null;
			else
				this.RateState = rs.getString("RateState").trim();

			this.EndDate = rs.getDate("EndDate");
			this.FiscalYear = rs.getInt("FiscalYear");
			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			if( rs.getString("Bonustype") == null )
				this.Bonustype = null;
			else
				this.Bonustype = rs.getString("Bonustype").trim();

			this.StartDate = rs.getDate("StartDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMInsuAccRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMInsuAccRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMInsuAccRateSchema getSchema()
	{
		LMInsuAccRateSchema aLMInsuAccRateSchema = new LMInsuAccRateSchema();
		aLMInsuAccRateSchema.setSchema(this);
		return aLMInsuAccRateSchema;
	}

	public LMInsuAccRateDB getDB()
	{
		LMInsuAccRateDB aDBOper = new LMInsuAccRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMInsuAccRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalaDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SRateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ARateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Bonustype)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMInsuAccRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BalaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			SRateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			ARateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			RateIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RateState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Bonustype = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMInsuAccRateSchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("BalaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
		}
		if (FCode.equalsIgnoreCase("SRateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSRateDate()));
		}
		if (FCode.equalsIgnoreCase("ARateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getARateDate()));
		}
		if (FCode.equalsIgnoreCase("RateIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateIntv));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
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
		if (FCode.equalsIgnoreCase("RateState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateState));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equalsIgnoreCase("Bonustype"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bonustype));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSRateDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getARateDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RateIntv);
				break;
			case 6:
				strFieldValue = String.valueOf(Rate);
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
				strFieldValue = StrTool.GBKToUnicode(RateState);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 12:
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Bonustype);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("BalaDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalaDate = fDate.getDate( FValue );
			}
			else
				BalaDate = null;
		}
		if (FCode.equalsIgnoreCase("SRateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SRateDate = fDate.getDate( FValue );
			}
			else
				SRateDate = null;
		}
		if (FCode.equalsIgnoreCase("ARateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ARateDate = fDate.getDate( FValue );
			}
			else
				ARateDate = null;
		}
		if (FCode.equalsIgnoreCase("RateIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateIntv = FValue.trim();
			}
			else
				RateIntv = null;
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
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
		if (FCode.equalsIgnoreCase("RateState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateState = FValue.trim();
			}
			else
				RateState = null;
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
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
		}
		if (FCode.equalsIgnoreCase("Bonustype"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bonustype = FValue.trim();
			}
			else
				Bonustype = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMInsuAccRateSchema other = (LMInsuAccRateSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& fDate.getString(BalaDate).equals(other.getBalaDate())
			&& fDate.getString(SRateDate).equals(other.getSRateDate())
			&& fDate.getString(ARateDate).equals(other.getARateDate())
			&& RateIntv.equals(other.getRateIntv())
			&& Rate == other.getRate()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& RateState.equals(other.getRateState())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& FiscalYear == other.getFiscalYear()
			&& Flag.equals(other.getFlag())
			&& Bonustype.equals(other.getBonustype())
			&& fDate.getString(StartDate).equals(other.getStartDate());
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 1;
		}
		if( strFieldName.equals("BalaDate") ) {
			return 2;
		}
		if( strFieldName.equals("SRateDate") ) {
			return 3;
		}
		if( strFieldName.equals("ARateDate") ) {
			return 4;
		}
		if( strFieldName.equals("RateIntv") ) {
			return 5;
		}
		if( strFieldName.equals("Rate") ) {
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
		if( strFieldName.equals("RateState") ) {
			return 10;
		}
		if( strFieldName.equals("EndDate") ) {
			return 11;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return 12;
		}
		if( strFieldName.equals("Flag") ) {
			return 13;
		}
		if( strFieldName.equals("Bonustype") ) {
			return 14;
		}
		if( strFieldName.equals("StartDate") ) {
			return 15;
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
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "BalaDate";
				break;
			case 3:
				strFieldName = "SRateDate";
				break;
			case 4:
				strFieldName = "ARateDate";
				break;
			case 5:
				strFieldName = "RateIntv";
				break;
			case 6:
				strFieldName = "Rate";
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
				strFieldName = "RateState";
				break;
			case 11:
				strFieldName = "EndDate";
				break;
			case 12:
				strFieldName = "FiscalYear";
				break;
			case 13:
				strFieldName = "Flag";
				break;
			case 14:
				strFieldName = "Bonustype";
				break;
			case 15:
				strFieldName = "StartDate";
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalaDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SRateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ARateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RateIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate") ) {
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
		if( strFieldName.equals("RateState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bonustype") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
