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
import com.sinosoft.lis.db.LSQuotFeeDB;

/*
 * <p>ClassName: LSQuotFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotFeeSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 险种编码 */
	private String RiskCode;
	/** 参考佣金比例 */
	private double StandCommRate;
	/** 期望佣金比例 */
	private double ExpectCommRate;
	/** 参考手续费比例 */
	private double StandChargeRate;
	/** 险种手续费比例 */
	private double ExpectChargeRate;
	/** 参考业务费用率 */
	private double StandBusiFeeRate;
	/** 期望业务费用率 */
	private double ExpectBusiFeeRate;
	/** 预期理赔率 */
	private double PreLossRatio;
	/** 渠道公摊 */
	private double PoolRate;
	/** 税费 */
	private double TaxFeeRate;
	/** 直接管理费 */
	private double ManageFeeRate;
	/** 参考费用率合计 */
	private double StandSumRate;
	/** 期望费用率合计 */
	private double ExpectSumRate;
	/** 保费合计 */
	private double RiskSumPrem;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";
		pk[2] = "RiskCode";

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
		LSQuotFeeSchema cloned = (LSQuotFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuotNo()
	{
		return QuotNo;
	}
	public void setQuotNo(String aQuotNo)
	{
		if(aQuotNo!=null && aQuotNo.length()>20)
			throw new IllegalArgumentException("询价号QuotNo值"+aQuotNo+"的长度"+aQuotNo.length()+"大于最大值20");
		QuotNo = aQuotNo;
	}
	public int getQuotBatNo()
	{
		return QuotBatNo;
	}
	public void setQuotBatNo(int aQuotBatNo)
	{
		QuotBatNo = aQuotBatNo;
	}
	public void setQuotBatNo(String aQuotBatNo)
	{
		if (aQuotBatNo != null && !aQuotBatNo.equals(""))
		{
			Integer tInteger = new Integer(aQuotBatNo);
			int i = tInteger.intValue();
			QuotBatNo = i;
		}
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
	public double getStandCommRate()
	{
		return StandCommRate;
	}
	public void setStandCommRate(double aStandCommRate)
	{
		StandCommRate = aStandCommRate;
	}
	public void setStandCommRate(String aStandCommRate)
	{
		if (aStandCommRate != null && !aStandCommRate.equals(""))
		{
			Double tDouble = new Double(aStandCommRate);
			double d = tDouble.doubleValue();
			StandCommRate = d;
		}
	}

	public double getExpectCommRate()
	{
		return ExpectCommRate;
	}
	public void setExpectCommRate(double aExpectCommRate)
	{
		ExpectCommRate = aExpectCommRate;
	}
	public void setExpectCommRate(String aExpectCommRate)
	{
		if (aExpectCommRate != null && !aExpectCommRate.equals(""))
		{
			Double tDouble = new Double(aExpectCommRate);
			double d = tDouble.doubleValue();
			ExpectCommRate = d;
		}
	}

	public double getStandChargeRate()
	{
		return StandChargeRate;
	}
	public void setStandChargeRate(double aStandChargeRate)
	{
		StandChargeRate = aStandChargeRate;
	}
	public void setStandChargeRate(String aStandChargeRate)
	{
		if (aStandChargeRate != null && !aStandChargeRate.equals(""))
		{
			Double tDouble = new Double(aStandChargeRate);
			double d = tDouble.doubleValue();
			StandChargeRate = d;
		}
	}

	public double getExpectChargeRate()
	{
		return ExpectChargeRate;
	}
	public void setExpectChargeRate(double aExpectChargeRate)
	{
		ExpectChargeRate = aExpectChargeRate;
	}
	public void setExpectChargeRate(String aExpectChargeRate)
	{
		if (aExpectChargeRate != null && !aExpectChargeRate.equals(""))
		{
			Double tDouble = new Double(aExpectChargeRate);
			double d = tDouble.doubleValue();
			ExpectChargeRate = d;
		}
	}

	public double getStandBusiFeeRate()
	{
		return StandBusiFeeRate;
	}
	public void setStandBusiFeeRate(double aStandBusiFeeRate)
	{
		StandBusiFeeRate = aStandBusiFeeRate;
	}
	public void setStandBusiFeeRate(String aStandBusiFeeRate)
	{
		if (aStandBusiFeeRate != null && !aStandBusiFeeRate.equals(""))
		{
			Double tDouble = new Double(aStandBusiFeeRate);
			double d = tDouble.doubleValue();
			StandBusiFeeRate = d;
		}
	}

	public double getExpectBusiFeeRate()
	{
		return ExpectBusiFeeRate;
	}
	public void setExpectBusiFeeRate(double aExpectBusiFeeRate)
	{
		ExpectBusiFeeRate = aExpectBusiFeeRate;
	}
	public void setExpectBusiFeeRate(String aExpectBusiFeeRate)
	{
		if (aExpectBusiFeeRate != null && !aExpectBusiFeeRate.equals(""))
		{
			Double tDouble = new Double(aExpectBusiFeeRate);
			double d = tDouble.doubleValue();
			ExpectBusiFeeRate = d;
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

	public double getStandSumRate()
	{
		return StandSumRate;
	}
	public void setStandSumRate(double aStandSumRate)
	{
		StandSumRate = aStandSumRate;
	}
	public void setStandSumRate(String aStandSumRate)
	{
		if (aStandSumRate != null && !aStandSumRate.equals(""))
		{
			Double tDouble = new Double(aStandSumRate);
			double d = tDouble.doubleValue();
			StandSumRate = d;
		}
	}

	public double getExpectSumRate()
	{
		return ExpectSumRate;
	}
	public void setExpectSumRate(double aExpectSumRate)
	{
		ExpectSumRate = aExpectSumRate;
	}
	public void setExpectSumRate(String aExpectSumRate)
	{
		if (aExpectSumRate != null && !aExpectSumRate.equals(""))
		{
			Double tDouble = new Double(aExpectSumRate);
			double d = tDouble.doubleValue();
			ExpectSumRate = d;
		}
	}

	public double getRiskSumPrem()
	{
		return RiskSumPrem;
	}
	public void setRiskSumPrem(double aRiskSumPrem)
	{
		RiskSumPrem = aRiskSumPrem;
	}
	public void setRiskSumPrem(String aRiskSumPrem)
	{
		if (aRiskSumPrem != null && !aRiskSumPrem.equals(""))
		{
			Double tDouble = new Double(aRiskSumPrem);
			double d = tDouble.doubleValue();
			RiskSumPrem = d;
		}
	}

	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>20)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值20");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>20)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值20");
		Segment3 = aSegment3;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LSQuotFeeSchema 对象给 Schema 赋值
	* @param: aLSQuotFeeSchema LSQuotFeeSchema
	**/
	public void setSchema(LSQuotFeeSchema aLSQuotFeeSchema)
	{
		this.QuotNo = aLSQuotFeeSchema.getQuotNo();
		this.QuotBatNo = aLSQuotFeeSchema.getQuotBatNo();
		this.RiskCode = aLSQuotFeeSchema.getRiskCode();
		this.StandCommRate = aLSQuotFeeSchema.getStandCommRate();
		this.ExpectCommRate = aLSQuotFeeSchema.getExpectCommRate();
		this.StandChargeRate = aLSQuotFeeSchema.getStandChargeRate();
		this.ExpectChargeRate = aLSQuotFeeSchema.getExpectChargeRate();
		this.StandBusiFeeRate = aLSQuotFeeSchema.getStandBusiFeeRate();
		this.ExpectBusiFeeRate = aLSQuotFeeSchema.getExpectBusiFeeRate();
		this.PreLossRatio = aLSQuotFeeSchema.getPreLossRatio();
		this.PoolRate = aLSQuotFeeSchema.getPoolRate();
		this.TaxFeeRate = aLSQuotFeeSchema.getTaxFeeRate();
		this.ManageFeeRate = aLSQuotFeeSchema.getManageFeeRate();
		this.StandSumRate = aLSQuotFeeSchema.getStandSumRate();
		this.ExpectSumRate = aLSQuotFeeSchema.getExpectSumRate();
		this.RiskSumPrem = aLSQuotFeeSchema.getRiskSumPrem();
		this.Segment1 = aLSQuotFeeSchema.getSegment1();
		this.Segment2 = aLSQuotFeeSchema.getSegment2();
		this.Segment3 = aLSQuotFeeSchema.getSegment3();
		this.MakeOperator = aLSQuotFeeSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotFeeSchema.getMakeDate());
		this.MakeTime = aLSQuotFeeSchema.getMakeTime();
		this.ModifyOperator = aLSQuotFeeSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotFeeSchema.getModifyDate());
		this.ModifyTime = aLSQuotFeeSchema.getModifyTime();
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
			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.StandCommRate = rs.getDouble("StandCommRate");
			this.ExpectCommRate = rs.getDouble("ExpectCommRate");
			this.StandChargeRate = rs.getDouble("StandChargeRate");
			this.ExpectChargeRate = rs.getDouble("ExpectChargeRate");
			this.StandBusiFeeRate = rs.getDouble("StandBusiFeeRate");
			this.ExpectBusiFeeRate = rs.getDouble("ExpectBusiFeeRate");
			this.PreLossRatio = rs.getDouble("PreLossRatio");
			this.PoolRate = rs.getDouble("PoolRate");
			this.TaxFeeRate = rs.getDouble("TaxFeeRate");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			this.StandSumRate = rs.getDouble("StandSumRate");
			this.ExpectSumRate = rs.getDouble("ExpectSumRate");
			this.RiskSumPrem = rs.getDouble("RiskSumPrem");
			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LSQuotFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotFeeSchema getSchema()
	{
		LSQuotFeeSchema aLSQuotFeeSchema = new LSQuotFeeSchema();
		aLSQuotFeeSchema.setSchema(this);
		return aLSQuotFeeSchema;
	}

	public LSQuotFeeDB getDB()
	{
		LSQuotFeeDB aDBOper = new LSQuotFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpectCommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandChargeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpectChargeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandBusiFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpectBusiFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreLossRatio));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PoolRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandSumRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpectSumRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskSumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StandCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ExpectCommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			StandChargeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			ExpectChargeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			StandBusiFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			ExpectBusiFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			PreLossRatio = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PoolRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			TaxFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			StandSumRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			ExpectSumRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			RiskSumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotFeeSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("StandCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandCommRate));
		}
		if (FCode.equalsIgnoreCase("ExpectCommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpectCommRate));
		}
		if (FCode.equalsIgnoreCase("StandChargeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandChargeRate));
		}
		if (FCode.equalsIgnoreCase("ExpectChargeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpectChargeRate));
		}
		if (FCode.equalsIgnoreCase("StandBusiFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandBusiFeeRate));
		}
		if (FCode.equalsIgnoreCase("ExpectBusiFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpectBusiFeeRate));
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
		if (FCode.equalsIgnoreCase("StandSumRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandSumRate));
		}
		if (FCode.equalsIgnoreCase("ExpectSumRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpectSumRate));
		}
		if (FCode.equalsIgnoreCase("RiskSumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSumPrem));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 1:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = String.valueOf(StandCommRate);
				break;
			case 4:
				strFieldValue = String.valueOf(ExpectCommRate);
				break;
			case 5:
				strFieldValue = String.valueOf(StandChargeRate);
				break;
			case 6:
				strFieldValue = String.valueOf(ExpectChargeRate);
				break;
			case 7:
				strFieldValue = String.valueOf(StandBusiFeeRate);
				break;
			case 8:
				strFieldValue = String.valueOf(ExpectBusiFeeRate);
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
			case 13:
				strFieldValue = String.valueOf(StandSumRate);
				break;
			case 14:
				strFieldValue = String.valueOf(ExpectSumRate);
				break;
			case 15:
				strFieldValue = String.valueOf(RiskSumPrem);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
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

		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotNo = FValue.trim();
			}
			else
				QuotNo = null;
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				QuotBatNo = i;
			}
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
		if (FCode.equalsIgnoreCase("StandCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpectCommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpectCommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandChargeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandChargeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpectChargeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpectChargeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandBusiFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandBusiFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpectBusiFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpectBusiFeeRate = d;
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
		if (FCode.equalsIgnoreCase("StandSumRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandSumRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpectSumRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpectSumRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RiskSumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskSumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LSQuotFeeSchema other = (LSQuotFeeSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& RiskCode.equals(other.getRiskCode())
			&& StandCommRate == other.getStandCommRate()
			&& ExpectCommRate == other.getExpectCommRate()
			&& StandChargeRate == other.getStandChargeRate()
			&& ExpectChargeRate == other.getExpectChargeRate()
			&& StandBusiFeeRate == other.getStandBusiFeeRate()
			&& ExpectBusiFeeRate == other.getExpectBusiFeeRate()
			&& PreLossRatio == other.getPreLossRatio()
			&& PoolRate == other.getPoolRate()
			&& TaxFeeRate == other.getTaxFeeRate()
			&& ManageFeeRate == other.getManageFeeRate()
			&& StandSumRate == other.getStandSumRate()
			&& ExpectSumRate == other.getExpectSumRate()
			&& RiskSumPrem == other.getRiskSumPrem()
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("QuotNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("StandCommRate") ) {
			return 3;
		}
		if( strFieldName.equals("ExpectCommRate") ) {
			return 4;
		}
		if( strFieldName.equals("StandChargeRate") ) {
			return 5;
		}
		if( strFieldName.equals("ExpectChargeRate") ) {
			return 6;
		}
		if( strFieldName.equals("StandBusiFeeRate") ) {
			return 7;
		}
		if( strFieldName.equals("ExpectBusiFeeRate") ) {
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
		if( strFieldName.equals("StandSumRate") ) {
			return 13;
		}
		if( strFieldName.equals("ExpectSumRate") ) {
			return 14;
		}
		if( strFieldName.equals("RiskSumPrem") ) {
			return 15;
		}
		if( strFieldName.equals("Segment1") ) {
			return 16;
		}
		if( strFieldName.equals("Segment2") ) {
			return 17;
		}
		if( strFieldName.equals("Segment3") ) {
			return 18;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "QuotNo";
				break;
			case 1:
				strFieldName = "QuotBatNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "StandCommRate";
				break;
			case 4:
				strFieldName = "ExpectCommRate";
				break;
			case 5:
				strFieldName = "StandChargeRate";
				break;
			case 6:
				strFieldName = "ExpectChargeRate";
				break;
			case 7:
				strFieldName = "StandBusiFeeRate";
				break;
			case 8:
				strFieldName = "ExpectBusiFeeRate";
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
			case 13:
				strFieldName = "StandSumRate";
				break;
			case 14:
				strFieldName = "ExpectSumRate";
				break;
			case 15:
				strFieldName = "RiskSumPrem";
				break;
			case 16:
				strFieldName = "Segment1";
				break;
			case 17:
				strFieldName = "Segment2";
				break;
			case 18:
				strFieldName = "Segment3";
				break;
			case 19:
				strFieldName = "MakeOperator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyOperator";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpectCommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandChargeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpectChargeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandBusiFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpectBusiFeeRate") ) {
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
		if( strFieldName.equals("StandSumRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpectSumRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskSumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
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
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
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
