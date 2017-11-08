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
import com.sinosoft.lis.db.LMStandFeeRateDB;

/*
 * <p>ClassName: LMStandFeeRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMStandFeeRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMStandFeeRateSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 直销渠道佣金率 */
	private double DirSellCommRate;
	/** 中介渠道佣金率 */
	private double AgencyCommRate;
	/** 银团渠道佣金率 */
	private double BankCommRate;
	/** 综开渠道代理人佣金率 */
	private double AgentCommRate;
	/** 综开渠道专员佣金率 */
	private double SpecialistCommRate;
	/** 中介渠道手续费率 */
	private double AgencyChargeRate;
	/** 银行渠道手续费率 */
	private double BankChargeRate;
	/** 业务费用率 */
	private double BusiFeeRate;
	/** 预期理赔率 */
	private double PreLossRatio;
	/** 渠道公摊 */
	private double PoolRate;
	/** 税费 */
	private double TaxFeeRate;
	/** 直接管理费 */
	private double ManageFeeRate;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMStandFeeRateSchema()
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
		LMStandFeeRateSchema cloned = (LMStandFeeRateSchema)super.clone();
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
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	public double getDirSellCommRate()
	{
		return DirSellCommRate;
	}
	public void setDirSellCommRate(double aDirSellCommRate)
	{
		DirSellCommRate = aDirSellCommRate;
	}
	public void setDirSellCommRate(String aDirSellCommRate)
	{
		if (aDirSellCommRate != null && !aDirSellCommRate.equals(""))
		{
			Double tDouble = new Double(aDirSellCommRate);
			double d = tDouble.doubleValue();
			DirSellCommRate = d;
		}
	}

	public double getAgencyCommRate()
	{
		return AgencyCommRate;
	}
	public void setAgencyCommRate(double aAgencyCommRate)
	{
		AgencyCommRate = aAgencyCommRate;
	}
	public void setAgencyCommRate(String aAgencyCommRate)
	{
		if (aAgencyCommRate != null && !aAgencyCommRate.equals(""))
		{
			Double tDouble = new Double(aAgencyCommRate);
			double d = tDouble.doubleValue();
			AgencyCommRate = d;
		}
	}

	public double getBankCommRate()
	{
		return BankCommRate;
	}
	public void setBankCommRate(double aBankCommRate)
	{
		BankCommRate = aBankCommRate;
	}
	public void setBankCommRate(String aBankCommRate)
	{
		if (aBankCommRate != null && !aBankCommRate.equals(""))
		{
			Double tDouble = new Double(aBankCommRate);
			double d = tDouble.doubleValue();
			BankCommRate = d;
		}
	}

	public double getAgentCommRate()
	{
		return AgentCommRate;
	}
	public void setAgentCommRate(double aAgentCommRate)
	{
		AgentCommRate = aAgentCommRate;
	}
	public void setAgentCommRate(String aAgentCommRate)
	{
		if (aAgentCommRate != null && !aAgentCommRate.equals(""))
		{
			Double tDouble = new Double(aAgentCommRate);
			double d = tDouble.doubleValue();
			AgentCommRate = d;
		}
	}

	public double getSpecialistCommRate()
	{
		return SpecialistCommRate;
	}
	public void setSpecialistCommRate(double aSpecialistCommRate)
	{
		SpecialistCommRate = aSpecialistCommRate;
	}
	public void setSpecialistCommRate(String aSpecialistCommRate)
	{
		if (aSpecialistCommRate != null && !aSpecialistCommRate.equals(""))
		{
			Double tDouble = new Double(aSpecialistCommRate);
			double d = tDouble.doubleValue();
			SpecialistCommRate = d;
		}
	}

	public double getAgencyChargeRate()
	{
		return AgencyChargeRate;
	}
	public void setAgencyChargeRate(double aAgencyChargeRate)
	{
		AgencyChargeRate = aAgencyChargeRate;
	}
	public void setAgencyChargeRate(String aAgencyChargeRate)
	{
		if (aAgencyChargeRate != null && !aAgencyChargeRate.equals(""))
		{
			Double tDouble = new Double(aAgencyChargeRate);
			double d = tDouble.doubleValue();
			AgencyChargeRate = d;
		}
	}

	public double getBankChargeRate()
	{
		return BankChargeRate;
	}
	public void setBankChargeRate(double aBankChargeRate)
	{
		BankChargeRate = aBankChargeRate;
	}
	public void setBankChargeRate(String aBankChargeRate)
	{
		if (aBankChargeRate != null && !aBankChargeRate.equals(""))
		{
			Double tDouble = new Double(aBankChargeRate);
			double d = tDouble.doubleValue();
			BankChargeRate = d;
		}
	}

	public double getBusiFeeRate()
	{
		return BusiFeeRate;
	}
	public void setBusiFeeRate(double aBusiFeeRate)
	{
		BusiFeeRate = aBusiFeeRate;
	}
	public void setBusiFeeRate(String aBusiFeeRate)
	{
		if (aBusiFeeRate != null && !aBusiFeeRate.equals(""))
		{
			Double tDouble = new Double(aBusiFeeRate);
			double d = tDouble.doubleValue();
			BusiFeeRate = d;
		}
	}

	public double getPreLossRatio()
	{
		return PreLossRatio;
	}
	public void setPreLossRatio(double aPreLossRatio)
	{
		PreLossRatio = aPreLossRatio;
	}
	public void setPreLossRatio(String aPreLossRatio)
	{
		if (aPreLossRatio != null && !aPreLossRatio.equals(""))
		{
			Double tDouble = new Double(aPreLossRatio);
			double d = tDouble.doubleValue();
			PreLossRatio = d;
		}
	}

	public double getPoolRate()
	{
		return PoolRate;
	}
	public void setPoolRate(double aPoolRate)
	{
		PoolRate = aPoolRate;
	}
	public void setPoolRate(String aPoolRate)
	{
		if (aPoolRate != null && !aPoolRate.equals(""))
		{
			Double tDouble = new Double(aPoolRate);
			double d = tDouble.doubleValue();
			PoolRate = d;
		}
	}

	public double getTaxFeeRate()
	{
		return TaxFeeRate;
	}
	public void setTaxFeeRate(double aTaxFeeRate)
	{
		TaxFeeRate = aTaxFeeRate;
	}
	public void setTaxFeeRate(String aTaxFeeRate)
	{
		if (aTaxFeeRate != null && !aTaxFeeRate.equals(""))
		{
			Double tDouble = new Double(aTaxFeeRate);
			double d = tDouble.doubleValue();
			TaxFeeRate = d;
		}
	}

	public double getManageFeeRate()
	{
		return ManageFeeRate;
	}
	public void setManageFeeRate(double aManageFeeRate)
	{
		ManageFeeRate = aManageFeeRate;
	}
	public void setManageFeeRate(String aManageFeeRate)
	{
		if (aManageFeeRate != null && !aManageFeeRate.equals(""))
		{
			Double tDouble = new Double(aManageFeeRate);
			double d = tDouble.doubleValue();
			ManageFeeRate = d;
		}
	}


	/**
	* 使用另外一个 LMStandFeeRateSchema 对象给 Schema 赋值
	* @param: aLMStandFeeRateSchema LMStandFeeRateSchema
	**/
	public void setSchema(LMStandFeeRateSchema aLMStandFeeRateSchema)
	{
		this.RiskCode = aLMStandFeeRateSchema.getRiskCode();
		this.DirSellCommRate = aLMStandFeeRateSchema.getDirSellCommRate();
		this.AgencyCommRate = aLMStandFeeRateSchema.getAgencyCommRate();
		this.BankCommRate = aLMStandFeeRateSchema.getBankCommRate();
		this.AgentCommRate = aLMStandFeeRateSchema.getAgentCommRate();
		this.SpecialistCommRate = aLMStandFeeRateSchema.getSpecialistCommRate();
		this.AgencyChargeRate = aLMStandFeeRateSchema.getAgencyChargeRate();
		this.BankChargeRate = aLMStandFeeRateSchema.getBankChargeRate();
		this.BusiFeeRate = aLMStandFeeRateSchema.getBusiFeeRate();
		this.PreLossRatio = aLMStandFeeRateSchema.getPreLossRatio();
		this.PoolRate = aLMStandFeeRateSchema.getPoolRate();
		this.TaxFeeRate = aLMStandFeeRateSchema.getTaxFeeRate();
		this.ManageFeeRate = aLMStandFeeRateSchema.getManageFeeRate();
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

			this.DirSellCommRate = rs.getDouble("DirSellCommRate");
			this.AgencyCommRate = rs.getDouble("AgencyCommRate");
			this.BankCommRate = rs.getDouble("BankCommRate");
			this.AgentCommRate = rs.getDouble("AgentCommRate");
			this.SpecialistCommRate = rs.getDouble("SpecialistCommRate");
			this.AgencyChargeRate = rs.getDouble("AgencyChargeRate");
			this.BankChargeRate = rs.getDouble("BankChargeRate");
			this.BusiFeeRate = rs.getDouble("BusiFeeRate");
			this.PreLossRatio = rs.getDouble("PreLossRatio");
			this.PoolRate = rs.getDouble("PoolRate");
			this.TaxFeeRate = rs.getDouble("TaxFeeRate");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMStandFeeRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMStandFeeRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMStandFeeRateSchema getSchema()
	{
		LMStandFeeRateSchema aLMStandFeeRateSchema = new LMStandFeeRateSchema();
		aLMStandFeeRateSchema.setSchema(this);
		return aLMStandFeeRateSchema;
	}

	public LMStandFeeRateDB getDB()
	{
		LMStandFeeRateDB aDBOper = new LMStandFeeRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMStandFeeRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DirSellCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AgencyCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BankCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AgentCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SpecialistCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AgencyChargeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BankChargeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BusiFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreLossRatio));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PoolRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMStandFeeRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DirSellCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).doubleValue();
			AgencyCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			BankCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			AgentCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			SpecialistCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AgencyChargeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			BankChargeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			BusiFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			PreLossRatio = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PoolRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			TaxFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMStandFeeRateSchema";
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
		if (FCode.equalsIgnoreCase("DirSellCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DirSellCommRate));
		}
		if (FCode.equalsIgnoreCase("AgencyCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgencyCommRate));
		}
		if (FCode.equalsIgnoreCase("BankCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCommRate));
		}
		if (FCode.equalsIgnoreCase("AgentCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCommRate));
		}
		if (FCode.equalsIgnoreCase("SpecialistCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecialistCommRate));
		}
		if (FCode.equalsIgnoreCase("AgencyChargeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgencyChargeRate));
		}
		if (FCode.equalsIgnoreCase("BankChargeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankChargeRate));
		}
		if (FCode.equalsIgnoreCase("BusiFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiFeeRate));
		}
		if (FCode.equalsIgnoreCase("PreLossRatio"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreLossRatio));
		}
		if (FCode.equalsIgnoreCase("PoolRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PoolRate));
		}
		if (FCode.equalsIgnoreCase("TaxFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaxFeeRate));
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
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
				strFieldValue = String.valueOf(DirSellCommRate);
				break;
			case 2:
				strFieldValue = String.valueOf(AgencyCommRate);
				break;
			case 3:
				strFieldValue = String.valueOf(BankCommRate);
				break;
			case 4:
				strFieldValue = String.valueOf(AgentCommRate);
				break;
			case 5:
				strFieldValue = String.valueOf(SpecialistCommRate);
				break;
			case 6:
				strFieldValue = String.valueOf(AgencyChargeRate);
				break;
			case 7:
				strFieldValue = String.valueOf(BankChargeRate);
				break;
			case 8:
				strFieldValue = String.valueOf(BusiFeeRate);
				break;
			case 9:
				strFieldValue = String.valueOf(PreLossRatio);
				break;
			case 10:
				strFieldValue = String.valueOf(PoolRate);
				break;
			case 11:
				strFieldValue = String.valueOf(TaxFeeRate);
				break;
			case 12:
				strFieldValue = String.valueOf(ManageFeeRate);
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
		if (FCode.equalsIgnoreCase("DirSellCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DirSellCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AgencyCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AgencyCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("BankCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BankCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AgentCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AgentCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("SpecialistCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SpecialistCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AgencyChargeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AgencyChargeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("BankChargeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BankChargeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("BusiFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BusiFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PreLossRatio"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreLossRatio = d;
			}
		}
		if (FCode.equalsIgnoreCase("PoolRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PoolRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("TaxFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TaxFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ManageFeeRate = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMStandFeeRateSchema other = (LMStandFeeRateSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DirSellCommRate == other.getDirSellCommRate()
			&& AgencyCommRate == other.getAgencyCommRate()
			&& BankCommRate == other.getBankCommRate()
			&& AgentCommRate == other.getAgentCommRate()
			&& SpecialistCommRate == other.getSpecialistCommRate()
			&& AgencyChargeRate == other.getAgencyChargeRate()
			&& BankChargeRate == other.getBankChargeRate()
			&& BusiFeeRate == other.getBusiFeeRate()
			&& PreLossRatio == other.getPreLossRatio()
			&& PoolRate == other.getPoolRate()
			&& TaxFeeRate == other.getTaxFeeRate()
			&& ManageFeeRate == other.getManageFeeRate();
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
		if( strFieldName.equals("DirSellCommRate") ) {
			return 1;
		}
		if( strFieldName.equals("AgencyCommRate") ) {
			return 2;
		}
		if( strFieldName.equals("BankCommRate") ) {
			return 3;
		}
		if( strFieldName.equals("AgentCommRate") ) {
			return 4;
		}
		if( strFieldName.equals("SpecialistCommRate") ) {
			return 5;
		}
		if( strFieldName.equals("AgencyChargeRate") ) {
			return 6;
		}
		if( strFieldName.equals("BankChargeRate") ) {
			return 7;
		}
		if( strFieldName.equals("BusiFeeRate") ) {
			return 8;
		}
		if( strFieldName.equals("PreLossRatio") ) {
			return 9;
		}
		if( strFieldName.equals("PoolRate") ) {
			return 10;
		}
		if( strFieldName.equals("TaxFeeRate") ) {
			return 11;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 12;
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
				strFieldName = "DirSellCommRate";
				break;
			case 2:
				strFieldName = "AgencyCommRate";
				break;
			case 3:
				strFieldName = "BankCommRate";
				break;
			case 4:
				strFieldName = "AgentCommRate";
				break;
			case 5:
				strFieldName = "SpecialistCommRate";
				break;
			case 6:
				strFieldName = "AgencyChargeRate";
				break;
			case 7:
				strFieldName = "BankChargeRate";
				break;
			case 8:
				strFieldName = "BusiFeeRate";
				break;
			case 9:
				strFieldName = "PreLossRatio";
				break;
			case 10:
				strFieldName = "PoolRate";
				break;
			case 11:
				strFieldName = "TaxFeeRate";
				break;
			case 12:
				strFieldName = "ManageFeeRate";
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
		if( strFieldName.equals("DirSellCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgencyCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BankCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgentCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SpecialistCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgencyChargeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BankChargeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BusiFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PreLossRatio") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PoolRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TaxFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 2:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
