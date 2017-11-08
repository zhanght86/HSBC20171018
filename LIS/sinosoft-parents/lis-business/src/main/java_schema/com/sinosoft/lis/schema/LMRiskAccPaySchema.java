

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
import com.sinosoft.lis.db.LMRiskAccPayDB;

/*
 * <p>ClassName: LMRiskAccPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskAccPaySchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 默认比例 */
	private double DefaultRate;
	/** 是否需要录入 */
	private String NeedInput;
	/** 账户产生位置 */
	private String AccCreatePos;
	/** 转入账户时的算法编码(现金) */
	private String CalCodeMoney;
	/** 转入账户时的算法编码(股份) */
	private String CalCodeUnit;
	/** 账户转入计算标志 */
	private String CalFlag;
	/** 缴费编码 */
	private String PayPlanCode;
	/** 缴费名称 */
	private String PayPlanName;
	/** 账户交费转入位置 */
	private String PayNeedToAcc;
	/** 险种帐户交费名 */
	private String RiskAccPayName;
	/** 是否归属标记 */
	private String ascription;
	/** 投资比例 */
	private double InvestRate;
	/** 投资比例上限 */
	private double InvestMaxRate;
	/** 投资比例下限 */
	private double InvestMinRate;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskAccPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "InsuAccNo";
		pk[2] = "PayPlanCode";

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
		LMRiskAccPaySchema cloned = (LMRiskAccPaySchema)super.clone();
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
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	/**
	* 在录入界面中的默认显示比例。
	*/
	public double getDefaultRate()
	{
		return DefaultRate;
	}
	public void setDefaultRate(double aDefaultRate)
	{
		DefaultRate = aDefaultRate;
	}
	public void setDefaultRate(String aDefaultRate)
	{
		if (aDefaultRate != null && !aDefaultRate.equals(""))
		{
			Double tDouble = new Double(aDefaultRate);
			double d = tDouble.doubleValue();
			DefaultRate = d;
		}
	}

	/**
	* 0 －－ 和前台录入无关。<p>
	* 1 －－ 必须从前台录入，如果不录，报错。
	*/
	public String getNeedInput()
	{
		return NeedInput;
	}
	public void setNeedInput(String aNeedInput)
	{
		NeedInput = aNeedInput;
	}
	/**
	* 1 －－投保单录入时产生<p>
	* 2 －－缴费时产生<p>
	* 3 －－领取时产生<p>
	* 4 －－第一次账户缴费时产生
	*/
	public String getAccCreatePos()
	{
		return AccCreatePos;
	}
	public void setAccCreatePos(String aAccCreatePos)
	{
		AccCreatePos = aAccCreatePos;
	}
	public String getCalCodeMoney()
	{
		return CalCodeMoney;
	}
	public void setCalCodeMoney(String aCalCodeMoney)
	{
		CalCodeMoney = aCalCodeMoney;
	}
	public String getCalCodeUnit()
	{
		return CalCodeUnit;
	}
	public void setCalCodeUnit(String aCalCodeUnit)
	{
		CalCodeUnit = aCalCodeUnit;
	}
	/**
	* 判断使用那种算法进行计算。<p>
	* 0 －－ 完全转入账户<p>
	* 1 －－ 安现金计算转入账户<p>
	* 2 －－ 安股份计算转入账户<p>
	* 3 －－ 先算现金，然后按股份计算。
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getPayPlanName()
	{
		return PayPlanName;
	}
	public void setPayPlanName(String aPayPlanName)
	{
		PayPlanName = aPayPlanName;
	}
	/**
	* 1 －－ 表示立刻转入<p>
	* 0 －－ 表示不立刻转入
	*/
	public String getPayNeedToAcc()
	{
		return PayNeedToAcc;
	}
	public void setPayNeedToAcc(String aPayNeedToAcc)
	{
		PayNeedToAcc = aPayNeedToAcc;
	}
	/**
	* 对应于险种账户分类的名称
	*/
	public String getRiskAccPayName()
	{
		return RiskAccPayName;
	}
	public void setRiskAccPayName(String aRiskAccPayName)
	{
		RiskAccPayName = aRiskAccPayName;
	}
	/**
	* 0：表示初试未归属<p>
	* 1：表示初试归属
	*/
	public String getascription()
	{
		return ascription;
	}
	public void setascription(String aascription)
	{
		ascription = aascription;
	}
	public double getInvestRate()
	{
		return InvestRate;
	}
	public void setInvestRate(double aInvestRate)
	{
		InvestRate = aInvestRate;
	}
	public void setInvestRate(String aInvestRate)
	{
		if (aInvestRate != null && !aInvestRate.equals(""))
		{
			Double tDouble = new Double(aInvestRate);
			double d = tDouble.doubleValue();
			InvestRate = d;
		}
	}

	public double getInvestMaxRate()
	{
		return InvestMaxRate;
	}
	public void setInvestMaxRate(double aInvestMaxRate)
	{
		InvestMaxRate = aInvestMaxRate;
	}
	public void setInvestMaxRate(String aInvestMaxRate)
	{
		if (aInvestMaxRate != null && !aInvestMaxRate.equals(""))
		{
			Double tDouble = new Double(aInvestMaxRate);
			double d = tDouble.doubleValue();
			InvestMaxRate = d;
		}
	}

	public double getInvestMinRate()
	{
		return InvestMinRate;
	}
	public void setInvestMinRate(double aInvestMinRate)
	{
		InvestMinRate = aInvestMinRate;
	}
	public void setInvestMinRate(String aInvestMinRate)
	{
		if (aInvestMinRate != null && !aInvestMinRate.equals(""))
		{
			Double tDouble = new Double(aInvestMinRate);
			double d = tDouble.doubleValue();
			InvestMinRate = d;
		}
	}


	/**
	* 使用另外一个 LMRiskAccPaySchema 对象给 Schema 赋值
	* @param: aLMRiskAccPaySchema LMRiskAccPaySchema
	**/
	public void setSchema(LMRiskAccPaySchema aLMRiskAccPaySchema)
	{
		this.RiskCode = aLMRiskAccPaySchema.getRiskCode();
		this.RiskVer = aLMRiskAccPaySchema.getRiskVer();
		this.InsuAccNo = aLMRiskAccPaySchema.getInsuAccNo();
		this.DefaultRate = aLMRiskAccPaySchema.getDefaultRate();
		this.NeedInput = aLMRiskAccPaySchema.getNeedInput();
		this.AccCreatePos = aLMRiskAccPaySchema.getAccCreatePos();
		this.CalCodeMoney = aLMRiskAccPaySchema.getCalCodeMoney();
		this.CalCodeUnit = aLMRiskAccPaySchema.getCalCodeUnit();
		this.CalFlag = aLMRiskAccPaySchema.getCalFlag();
		this.PayPlanCode = aLMRiskAccPaySchema.getPayPlanCode();
		this.PayPlanName = aLMRiskAccPaySchema.getPayPlanName();
		this.PayNeedToAcc = aLMRiskAccPaySchema.getPayNeedToAcc();
		this.RiskAccPayName = aLMRiskAccPaySchema.getRiskAccPayName();
		this.ascription = aLMRiskAccPaySchema.getascription();
		this.InvestRate = aLMRiskAccPaySchema.getInvestRate();
		this.InvestMaxRate = aLMRiskAccPaySchema.getInvestMaxRate();
		this.InvestMinRate = aLMRiskAccPaySchema.getInvestMinRate();
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

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			this.DefaultRate = rs.getDouble("DefaultRate");
			if( rs.getString("NeedInput") == null )
				this.NeedInput = null;
			else
				this.NeedInput = rs.getString("NeedInput").trim();

			if( rs.getString("AccCreatePos") == null )
				this.AccCreatePos = null;
			else
				this.AccCreatePos = rs.getString("AccCreatePos").trim();

			if( rs.getString("CalCodeMoney") == null )
				this.CalCodeMoney = null;
			else
				this.CalCodeMoney = rs.getString("CalCodeMoney").trim();

			if( rs.getString("CalCodeUnit") == null )
				this.CalCodeUnit = null;
			else
				this.CalCodeUnit = rs.getString("CalCodeUnit").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("PayPlanName") == null )
				this.PayPlanName = null;
			else
				this.PayPlanName = rs.getString("PayPlanName").trim();

			if( rs.getString("PayNeedToAcc") == null )
				this.PayNeedToAcc = null;
			else
				this.PayNeedToAcc = rs.getString("PayNeedToAcc").trim();

			if( rs.getString("RiskAccPayName") == null )
				this.RiskAccPayName = null;
			else
				this.RiskAccPayName = rs.getString("RiskAccPayName").trim();

			if( rs.getString("ascription") == null )
				this.ascription = null;
			else
				this.ascription = rs.getString("ascription").trim();

			this.InvestRate = rs.getDouble("InvestRate");
			this.InvestMaxRate = rs.getDouble("InvestMaxRate");
			this.InvestMinRate = rs.getDouble("InvestMinRate");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRiskAccPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAccPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskAccPaySchema getSchema()
	{
		LMRiskAccPaySchema aLMRiskAccPaySchema = new LMRiskAccPaySchema();
		aLMRiskAccPaySchema.setSchema(this);
		return aLMRiskAccPaySchema;
	}

	public LMRiskAccPayDB getDB()
	{
		LMRiskAccPayDB aDBOper = new LMRiskAccPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskAccPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedInput)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCreatePos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeMoney)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayNeedToAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAccPayName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ascription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMaxRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMinRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskAccPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DefaultRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			NeedInput = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccCreatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalCodeMoney = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalCodeUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PayPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PayNeedToAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskAccPayName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ascription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InvestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMaxRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMinRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAccPaySchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("DefaultRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultRate));
		}
		if (FCode.equalsIgnoreCase("NeedInput"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedInput));
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCreatePos));
		}
		if (FCode.equalsIgnoreCase("CalCodeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeMoney));
		}
		if (FCode.equalsIgnoreCase("CalCodeUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeUnit));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanName));
		}
		if (FCode.equalsIgnoreCase("PayNeedToAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayNeedToAcc));
		}
		if (FCode.equalsIgnoreCase("RiskAccPayName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAccPayName));
		}
		if (FCode.equalsIgnoreCase("ascription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ascription));
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRate));
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMaxRate));
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMinRate));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 3:
				strFieldValue = String.valueOf(DefaultRate);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NeedInput);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccCreatePos);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalCodeMoney);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCodeUnit);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PayPlanName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PayNeedToAcc);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskAccPayName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ascription);
				break;
			case 14:
				strFieldValue = String.valueOf(InvestRate);
				break;
			case 15:
				strFieldValue = String.valueOf(InvestMaxRate);
				break;
			case 16:
				strFieldValue = String.valueOf(InvestMinRate);
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("DefaultRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("NeedInput"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedInput = FValue.trim();
			}
			else
				NeedInput = null;
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCreatePos = FValue.trim();
			}
			else
				AccCreatePos = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeMoney = FValue.trim();
			}
			else
				CalCodeMoney = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeUnit = FValue.trim();
			}
			else
				CalCodeUnit = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
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
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanName = FValue.trim();
			}
			else
				PayPlanName = null;
		}
		if (FCode.equalsIgnoreCase("PayNeedToAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayNeedToAcc = FValue.trim();
			}
			else
				PayNeedToAcc = null;
		}
		if (FCode.equalsIgnoreCase("RiskAccPayName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAccPayName = FValue.trim();
			}
			else
				RiskAccPayName = null;
		}
		if (FCode.equalsIgnoreCase("ascription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ascription = FValue.trim();
			}
			else
				ascription = null;
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMaxRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMinRate = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskAccPaySchema other = (LMRiskAccPaySchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& DefaultRate == other.getDefaultRate()
			&& NeedInput.equals(other.getNeedInput())
			&& AccCreatePos.equals(other.getAccCreatePos())
			&& CalCodeMoney.equals(other.getCalCodeMoney())
			&& CalCodeUnit.equals(other.getCalCodeUnit())
			&& CalFlag.equals(other.getCalFlag())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& PayPlanName.equals(other.getPayPlanName())
			&& PayNeedToAcc.equals(other.getPayNeedToAcc())
			&& RiskAccPayName.equals(other.getRiskAccPayName())
			&& ascription.equals(other.getascription())
			&& InvestRate == other.getInvestRate()
			&& InvestMaxRate == other.getInvestMaxRate()
			&& InvestMinRate == other.getInvestMinRate();
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 2;
		}
		if( strFieldName.equals("DefaultRate") ) {
			return 3;
		}
		if( strFieldName.equals("NeedInput") ) {
			return 4;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return 5;
		}
		if( strFieldName.equals("CalCodeMoney") ) {
			return 6;
		}
		if( strFieldName.equals("CalCodeUnit") ) {
			return 7;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 8;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 9;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return 10;
		}
		if( strFieldName.equals("PayNeedToAcc") ) {
			return 11;
		}
		if( strFieldName.equals("RiskAccPayName") ) {
			return 12;
		}
		if( strFieldName.equals("ascription") ) {
			return 13;
		}
		if( strFieldName.equals("InvestRate") ) {
			return 14;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return 15;
		}
		if( strFieldName.equals("InvestMinRate") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "InsuAccNo";
				break;
			case 3:
				strFieldName = "DefaultRate";
				break;
			case 4:
				strFieldName = "NeedInput";
				break;
			case 5:
				strFieldName = "AccCreatePos";
				break;
			case 6:
				strFieldName = "CalCodeMoney";
				break;
			case 7:
				strFieldName = "CalCodeUnit";
				break;
			case 8:
				strFieldName = "CalFlag";
				break;
			case 9:
				strFieldName = "PayPlanCode";
				break;
			case 10:
				strFieldName = "PayPlanName";
				break;
			case 11:
				strFieldName = "PayNeedToAcc";
				break;
			case 12:
				strFieldName = "RiskAccPayName";
				break;
			case 13:
				strFieldName = "ascription";
				break;
			case 14:
				strFieldName = "InvestRate";
				break;
			case 15:
				strFieldName = "InvestMaxRate";
				break;
			case 16:
				strFieldName = "InvestMinRate";
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NeedInput") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeMoney") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayNeedToAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAccPayName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ascription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMinRate") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
