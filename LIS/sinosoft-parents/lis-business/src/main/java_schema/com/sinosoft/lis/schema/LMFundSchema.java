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
import com.sinosoft.lis.db.LMFundDB;

/*
 * <p>ClassName: LMFundSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LMFundSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMFundSchema.class);

	// @Field
	/** 基金代码 */
	private String FundNo;
	/** 外部代码 */
	private String ForeignFundNo;
	/** 基金简称 */
	private String FundName;
	/** 币种 */
	private String Currency;
	/** 地区 */
	private String Region;
	/** 投资方向 */
	private String InvestmentDirection;
	/** 风险等级 */
	private String RiskRating;
	/** 基金类型 */
	private String FundType;
	/** 基金公司代码 */
	private String FundCompanycode;
	/** 投资收益类型 */
	private String InvestFlag;
	/** 基金开通日期 */
	private Date OpenDate;
	/** 基金停售日期 */
	private Date EndDate;
	/** 基金状态 */
	private String FundState;
	/** 计价周期 */
	private String CalValueFreq;
	/** 小数位数 */
	private String UnitDecimal;
	/** 四舍五入标记 */
	private String RoundMethod;
	/** 基金描述 */
	private String Remark;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMFundSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "FundNo";

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
		LMFundSchema cloned = (LMFundSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFundNo()
	{
		return FundNo;
	}
	public void setFundNo(String aFundNo)
	{
		if(aFundNo!=null && aFundNo.length()>20)
			throw new IllegalArgumentException("基金代码FundNo值"+aFundNo+"的长度"+aFundNo.length()+"大于最大值20");
		FundNo = aFundNo;
	}
	public String getForeignFundNo()
	{
		return ForeignFundNo;
	}
	public void setForeignFundNo(String aForeignFundNo)
	{
		if(aForeignFundNo!=null && aForeignFundNo.length()>20)
			throw new IllegalArgumentException("外部代码ForeignFundNo值"+aForeignFundNo+"的长度"+aForeignFundNo.length()+"大于最大值20");
		ForeignFundNo = aForeignFundNo;
	}
	public String getFundName()
	{
		return FundName;
	}
	public void setFundName(String aFundName)
	{
		if(aFundName!=null && aFundName.length()>100)
			throw new IllegalArgumentException("基金简称FundName值"+aFundName+"的长度"+aFundName.length()+"大于最大值100");
		FundName = aFundName;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>2)
			throw new IllegalArgumentException("币种Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值2");
		Currency = aCurrency;
	}
	public String getRegion()
	{
		return Region;
	}
	public void setRegion(String aRegion)
	{
		if(aRegion!=null && aRegion.length()>120)
			throw new IllegalArgumentException("地区Region值"+aRegion+"的长度"+aRegion.length()+"大于最大值120");
		Region = aRegion;
	}
	public String getInvestmentDirection()
	{
		return InvestmentDirection;
	}
	public void setInvestmentDirection(String aInvestmentDirection)
	{
		if(aInvestmentDirection!=null && aInvestmentDirection.length()>1)
			throw new IllegalArgumentException("投资方向InvestmentDirection值"+aInvestmentDirection+"的长度"+aInvestmentDirection.length()+"大于最大值1");
		InvestmentDirection = aInvestmentDirection;
	}
	public String getRiskRating()
	{
		return RiskRating;
	}
	public void setRiskRating(String aRiskRating)
	{
		if(aRiskRating!=null && aRiskRating.length()>1)
			throw new IllegalArgumentException("风险等级RiskRating值"+aRiskRating+"的长度"+aRiskRating.length()+"大于最大值1");
		RiskRating = aRiskRating;
	}
	public String getFundType()
	{
		return FundType;
	}
	public void setFundType(String aFundType)
	{
		if(aFundType!=null && aFundType.length()>20)
			throw new IllegalArgumentException("基金类型FundType值"+aFundType+"的长度"+aFundType.length()+"大于最大值20");
		FundType = aFundType;
	}
	public String getFundCompanycode()
	{
		return FundCompanycode;
	}
	public void setFundCompanycode(String aFundCompanycode)
	{
		if(aFundCompanycode!=null && aFundCompanycode.length()>3)
			throw new IllegalArgumentException("基金公司代码FundCompanycode值"+aFundCompanycode+"的长度"+aFundCompanycode.length()+"大于最大值3");
		FundCompanycode = aFundCompanycode;
	}
	public String getInvestFlag()
	{
		return InvestFlag;
	}
	public void setInvestFlag(String aInvestFlag)
	{
		if(aInvestFlag!=null && aInvestFlag.length()>1)
			throw new IllegalArgumentException("投资收益类型InvestFlag值"+aInvestFlag+"的长度"+aInvestFlag.length()+"大于最大值1");
		InvestFlag = aInvestFlag;
	}
	public String getOpenDate()
	{
		if( OpenDate != null )
			return fDate.getString(OpenDate);
		else
			return null;
	}
	public void setOpenDate(Date aOpenDate)
	{
		OpenDate = aOpenDate;
	}
	public void setOpenDate(String aOpenDate)
	{
		if (aOpenDate != null && !aOpenDate.equals("") )
		{
			OpenDate = fDate.getDate( aOpenDate );
		}
		else
			OpenDate = null;
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

	/**
	* 0-待开通<p>
	* 1-有效<p>
	* 2-暂停<p>
	* 3-终止
	*/
	public String getFundState()
	{
		return FundState;
	}
	public void setFundState(String aFundState)
	{
		if(aFundState!=null && aFundState.length()>1)
			throw new IllegalArgumentException("基金状态FundState值"+aFundState+"的长度"+aFundState.length()+"大于最大值1");
		FundState = aFundState;
	}
	public String getCalValueFreq()
	{
		return CalValueFreq;
	}
	public void setCalValueFreq(String aCalValueFreq)
	{
		if(aCalValueFreq!=null && aCalValueFreq.length()>30)
			throw new IllegalArgumentException("计价周期CalValueFreq值"+aCalValueFreq+"的长度"+aCalValueFreq.length()+"大于最大值30");
		CalValueFreq = aCalValueFreq;
	}
	public String getUnitDecimal()
	{
		return UnitDecimal;
	}
	public void setUnitDecimal(String aUnitDecimal)
	{
		if(aUnitDecimal!=null && aUnitDecimal.length()>1)
			throw new IllegalArgumentException("小数位数UnitDecimal值"+aUnitDecimal+"的长度"+aUnitDecimal.length()+"大于最大值1");
		UnitDecimal = aUnitDecimal;
	}
	/**
	* 1－直接截取<p>
	* 2－四舍五入
	*/
	public String getRoundMethod()
	{
		return RoundMethod;
	}
	public void setRoundMethod(String aRoundMethod)
	{
		if(aRoundMethod!=null && aRoundMethod.length()>1)
			throw new IllegalArgumentException("四舍五入标记RoundMethod值"+aRoundMethod+"的长度"+aRoundMethod.length()+"大于最大值1");
		RoundMethod = aRoundMethod;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("基金描述Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LMFundSchema 对象给 Schema 赋值
	* @param: aLMFundSchema LMFundSchema
	**/
	public void setSchema(LMFundSchema aLMFundSchema)
	{
		this.FundNo = aLMFundSchema.getFundNo();
		this.ForeignFundNo = aLMFundSchema.getForeignFundNo();
		this.FundName = aLMFundSchema.getFundName();
		this.Currency = aLMFundSchema.getCurrency();
		this.Region = aLMFundSchema.getRegion();
		this.InvestmentDirection = aLMFundSchema.getInvestmentDirection();
		this.RiskRating = aLMFundSchema.getRiskRating();
		this.FundType = aLMFundSchema.getFundType();
		this.FundCompanycode = aLMFundSchema.getFundCompanycode();
		this.InvestFlag = aLMFundSchema.getInvestFlag();
		this.OpenDate = fDate.getDate( aLMFundSchema.getOpenDate());
		this.EndDate = fDate.getDate( aLMFundSchema.getEndDate());
		this.FundState = aLMFundSchema.getFundState();
		this.CalValueFreq = aLMFundSchema.getCalValueFreq();
		this.UnitDecimal = aLMFundSchema.getUnitDecimal();
		this.RoundMethod = aLMFundSchema.getRoundMethod();
		this.Remark = aLMFundSchema.getRemark();
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
			if( rs.getString("FundNo") == null )
				this.FundNo = null;
			else
				this.FundNo = rs.getString("FundNo").trim();

			if( rs.getString("ForeignFundNo") == null )
				this.ForeignFundNo = null;
			else
				this.ForeignFundNo = rs.getString("ForeignFundNo").trim();

			if( rs.getString("FundName") == null )
				this.FundName = null;
			else
				this.FundName = rs.getString("FundName").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("Region") == null )
				this.Region = null;
			else
				this.Region = rs.getString("Region").trim();

			if( rs.getString("InvestmentDirection") == null )
				this.InvestmentDirection = null;
			else
				this.InvestmentDirection = rs.getString("InvestmentDirection").trim();

			if( rs.getString("RiskRating") == null )
				this.RiskRating = null;
			else
				this.RiskRating = rs.getString("RiskRating").trim();

			if( rs.getString("FundType") == null )
				this.FundType = null;
			else
				this.FundType = rs.getString("FundType").trim();

			if( rs.getString("FundCompanycode") == null )
				this.FundCompanycode = null;
			else
				this.FundCompanycode = rs.getString("FundCompanycode").trim();

			if( rs.getString("InvestFlag") == null )
				this.InvestFlag = null;
			else
				this.InvestFlag = rs.getString("InvestFlag").trim();

			this.OpenDate = rs.getDate("OpenDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("FundState") == null )
				this.FundState = null;
			else
				this.FundState = rs.getString("FundState").trim();

			if( rs.getString("CalValueFreq") == null )
				this.CalValueFreq = null;
			else
				this.CalValueFreq = rs.getString("CalValueFreq").trim();

			if( rs.getString("UnitDecimal") == null )
				this.UnitDecimal = null;
			else
				this.UnitDecimal = rs.getString("UnitDecimal").trim();

			if( rs.getString("RoundMethod") == null )
				this.RoundMethod = null;
			else
				this.RoundMethod = rs.getString("RoundMethod").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMFund表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFundSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMFundSchema getSchema()
	{
		LMFundSchema aLMFundSchema = new LMFundSchema();
		aLMFundSchema.setSchema(this);
		return aLMFundSchema;
	}

	public LMFundDB getDB()
	{
		LMFundDB aDBOper = new LMFundDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFund描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FundNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForeignFundNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Region)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestmentDirection)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskRating)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundCompanycode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OpenDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalValueFreq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnitDecimal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RoundMethod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFund>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FundNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ForeignFundNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FundName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Region = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InvestmentDirection = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskRating = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FundType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FundCompanycode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InvestFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OpenDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			FundState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CalValueFreq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			UnitDecimal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RoundMethod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFundSchema";
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
		if (FCode.equalsIgnoreCase("FundNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundNo));
		}
		if (FCode.equalsIgnoreCase("ForeignFundNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForeignFundNo));
		}
		if (FCode.equalsIgnoreCase("FundName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundName));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("Region"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Region));
		}
		if (FCode.equalsIgnoreCase("InvestmentDirection"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestmentDirection));
		}
		if (FCode.equalsIgnoreCase("RiskRating"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskRating));
		}
		if (FCode.equalsIgnoreCase("FundType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundType));
		}
		if (FCode.equalsIgnoreCase("FundCompanycode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCompanycode));
		}
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestFlag));
		}
		if (FCode.equalsIgnoreCase("OpenDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOpenDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("FundState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundState));
		}
		if (FCode.equalsIgnoreCase("CalValueFreq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalValueFreq));
		}
		if (FCode.equalsIgnoreCase("UnitDecimal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitDecimal));
		}
		if (FCode.equalsIgnoreCase("RoundMethod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RoundMethod));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(FundNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ForeignFundNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FundName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Region);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InvestmentDirection);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskRating);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FundType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FundCompanycode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InvestFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOpenDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(FundState);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CalValueFreq);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(UnitDecimal);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RoundMethod);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("FundNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundNo = FValue.trim();
			}
			else
				FundNo = null;
		}
		if (FCode.equalsIgnoreCase("ForeignFundNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForeignFundNo = FValue.trim();
			}
			else
				ForeignFundNo = null;
		}
		if (FCode.equalsIgnoreCase("FundName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundName = FValue.trim();
			}
			else
				FundName = null;
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
		if (FCode.equalsIgnoreCase("Region"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Region = FValue.trim();
			}
			else
				Region = null;
		}
		if (FCode.equalsIgnoreCase("InvestmentDirection"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestmentDirection = FValue.trim();
			}
			else
				InvestmentDirection = null;
		}
		if (FCode.equalsIgnoreCase("RiskRating"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskRating = FValue.trim();
			}
			else
				RiskRating = null;
		}
		if (FCode.equalsIgnoreCase("FundType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundType = FValue.trim();
			}
			else
				FundType = null;
		}
		if (FCode.equalsIgnoreCase("FundCompanycode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundCompanycode = FValue.trim();
			}
			else
				FundCompanycode = null;
		}
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestFlag = FValue.trim();
			}
			else
				InvestFlag = null;
		}
		if (FCode.equalsIgnoreCase("OpenDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OpenDate = fDate.getDate( FValue );
			}
			else
				OpenDate = null;
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
		if (FCode.equalsIgnoreCase("FundState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundState = FValue.trim();
			}
			else
				FundState = null;
		}
		if (FCode.equalsIgnoreCase("CalValueFreq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalValueFreq = FValue.trim();
			}
			else
				CalValueFreq = null;
		}
		if (FCode.equalsIgnoreCase("UnitDecimal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitDecimal = FValue.trim();
			}
			else
				UnitDecimal = null;
		}
		if (FCode.equalsIgnoreCase("RoundMethod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RoundMethod = FValue.trim();
			}
			else
				RoundMethod = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMFundSchema other = (LMFundSchema)otherObject;
		return
			FundNo.equals(other.getFundNo())
			&& ForeignFundNo.equals(other.getForeignFundNo())
			&& FundName.equals(other.getFundName())
			&& Currency.equals(other.getCurrency())
			&& Region.equals(other.getRegion())
			&& InvestmentDirection.equals(other.getInvestmentDirection())
			&& RiskRating.equals(other.getRiskRating())
			&& FundType.equals(other.getFundType())
			&& FundCompanycode.equals(other.getFundCompanycode())
			&& InvestFlag.equals(other.getInvestFlag())
			&& fDate.getString(OpenDate).equals(other.getOpenDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& FundState.equals(other.getFundState())
			&& CalValueFreq.equals(other.getCalValueFreq())
			&& UnitDecimal.equals(other.getUnitDecimal())
			&& RoundMethod.equals(other.getRoundMethod())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("FundNo") ) {
			return 0;
		}
		if( strFieldName.equals("ForeignFundNo") ) {
			return 1;
		}
		if( strFieldName.equals("FundName") ) {
			return 2;
		}
		if( strFieldName.equals("Currency") ) {
			return 3;
		}
		if( strFieldName.equals("Region") ) {
			return 4;
		}
		if( strFieldName.equals("InvestmentDirection") ) {
			return 5;
		}
		if( strFieldName.equals("RiskRating") ) {
			return 6;
		}
		if( strFieldName.equals("FundType") ) {
			return 7;
		}
		if( strFieldName.equals("FundCompanycode") ) {
			return 8;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return 9;
		}
		if( strFieldName.equals("OpenDate") ) {
			return 10;
		}
		if( strFieldName.equals("EndDate") ) {
			return 11;
		}
		if( strFieldName.equals("FundState") ) {
			return 12;
		}
		if( strFieldName.equals("CalValueFreq") ) {
			return 13;
		}
		if( strFieldName.equals("UnitDecimal") ) {
			return 14;
		}
		if( strFieldName.equals("RoundMethod") ) {
			return 15;
		}
		if( strFieldName.equals("Remark") ) {
			return 16;
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
				strFieldName = "FundNo";
				break;
			case 1:
				strFieldName = "ForeignFundNo";
				break;
			case 2:
				strFieldName = "FundName";
				break;
			case 3:
				strFieldName = "Currency";
				break;
			case 4:
				strFieldName = "Region";
				break;
			case 5:
				strFieldName = "InvestmentDirection";
				break;
			case 6:
				strFieldName = "RiskRating";
				break;
			case 7:
				strFieldName = "FundType";
				break;
			case 8:
				strFieldName = "FundCompanycode";
				break;
			case 9:
				strFieldName = "InvestFlag";
				break;
			case 10:
				strFieldName = "OpenDate";
				break;
			case 11:
				strFieldName = "EndDate";
				break;
			case 12:
				strFieldName = "FundState";
				break;
			case 13:
				strFieldName = "CalValueFreq";
				break;
			case 14:
				strFieldName = "UnitDecimal";
				break;
			case 15:
				strFieldName = "RoundMethod";
				break;
			case 16:
				strFieldName = "Remark";
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
		if( strFieldName.equals("FundNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForeignFundNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Region") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestmentDirection") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskRating") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundCompanycode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OpenDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FundState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalValueFreq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitDecimal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RoundMethod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
