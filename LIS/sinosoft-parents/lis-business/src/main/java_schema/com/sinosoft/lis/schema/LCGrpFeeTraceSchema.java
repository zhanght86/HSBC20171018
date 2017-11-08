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
import com.sinosoft.lis.db.LCGrpFeeTraceDB;

/*
 * <p>ClassName: LCGrpFeeTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCGrpFeeTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpFeeTraceSchema.class);
	// @Field
	/** 修改序号 */
	private String SerialNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 险种编码 */
	private String RiskCode;
	/** 管理费编码 */
	private String FeeCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 交费项编码 */
	private String PayPlanCode;
	/** 关系说明 */
	private String PayInsuAccName;
	/** 管理费计算方式 */
	private String FeeCalMode;
	/** 计算类型 */
	private String FeeCalModeType;
	/** 管理费计算公式 */
	private String FeeCalCode;
	/** 固定值 */
	private double FeeValue;
	/** 比较值 */
	private double CompareValue;
	/** 扣除管理费周期 */
	private String FeePeriod;
	/** 扣除管理费最大次数 */
	private int MaxTime;
	/** 缺省标记 */
	private String DefaultFlag;
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

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpFeeTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LCGrpFeeTraceSchema cloned = (LCGrpFeeTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("修改序号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		if(aFeeCode!=null && aFeeCode.length()>6)
			throw new IllegalArgumentException("管理费编码FeeCode值"+aFeeCode+"的长度"+aFeeCode.length()+"大于最大值6");
		FeeCode = aFeeCode;
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
	/**
	* 如果该编码前6位为全0，则表示加费。 第7,8位表示加费的次数。
	*/
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		if(aPayPlanCode!=null && aPayPlanCode.length()>8)
			throw new IllegalArgumentException("交费项编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值8");
		PayPlanCode = aPayPlanCode;
	}
	public String getPayInsuAccName()
	{
		return PayInsuAccName;
	}
	public void setPayInsuAccName(String aPayInsuAccName)
	{
		if(aPayInsuAccName!=null && aPayInsuAccName.length()>200)
			throw new IllegalArgumentException("关系说明PayInsuAccName值"+aPayInsuAccName+"的长度"+aPayInsuAccName.length()+"大于最大值200");
		PayInsuAccName = aPayInsuAccName;
	}
	/**
	* 01-固定值（内扣） 02-固定比例 （内扣） 03-固定值（外缴） 04-固定比例 （外缴） 05-Min(固定值与比例结合) 06-Max(固定值和比例结合） 07-分档计算 08-累计分档计算
	*/
	public String getFeeCalMode()
	{
		return FeeCalMode;
	}
	public void setFeeCalMode(String aFeeCalMode)
	{
		if(aFeeCalMode!=null && aFeeCalMode.length()>2)
			throw new IllegalArgumentException("管理费计算方式FeeCalMode值"+aFeeCalMode+"的长度"+aFeeCalMode.length()+"大于最大值2");
		FeeCalMode = aFeeCalMode;
	}
	/**
	* 0-直接取值 1-sql描述
	*/
	public String getFeeCalModeType()
	{
		return FeeCalModeType;
	}
	public void setFeeCalModeType(String aFeeCalModeType)
	{
		if(aFeeCalModeType!=null && aFeeCalModeType.length()>1)
			throw new IllegalArgumentException("计算类型FeeCalModeType值"+aFeeCalModeType+"的长度"+aFeeCalModeType.length()+"大于最大值1");
		FeeCalModeType = aFeeCalModeType;
	}
	public String getFeeCalCode()
	{
		return FeeCalCode;
	}
	public void setFeeCalCode(String aFeeCalCode)
	{
		if(aFeeCalCode!=null && aFeeCalCode.length()>6)
			throw new IllegalArgumentException("管理费计算公式FeeCalCode值"+aFeeCalCode+"的长度"+aFeeCalCode.length()+"大于最大值6");
		FeeCalCode = aFeeCalCode;
	}
	public double getFeeValue()
	{
		return FeeValue;
	}
	public void setFeeValue(double aFeeValue)
	{
		FeeValue = aFeeValue;
	}
	public void setFeeValue(String aFeeValue)
	{
		if (aFeeValue != null && !aFeeValue.equals(""))
		{
			Double tDouble = new Double(aFeeValue);
			double d = tDouble.doubleValue();
			FeeValue = d;
		}
	}

	public double getCompareValue()
	{
		return CompareValue;
	}
	public void setCompareValue(double aCompareValue)
	{
		CompareValue = aCompareValue;
	}
	public void setCompareValue(String aCompareValue)
	{
		if (aCompareValue != null && !aCompareValue.equals(""))
		{
			Double tDouble = new Double(aCompareValue);
			double d = tDouble.doubleValue();
			CompareValue = d;
		}
	}

	/**
	* 01-年 02-月 03-日
	*/
	public String getFeePeriod()
	{
		return FeePeriod;
	}
	public void setFeePeriod(String aFeePeriod)
	{
		if(aFeePeriod!=null && aFeePeriod.length()>3)
			throw new IllegalArgumentException("扣除管理费周期FeePeriod值"+aFeePeriod+"的长度"+aFeePeriod.length()+"大于最大值3");
		FeePeriod = aFeePeriod;
	}
	public int getMaxTime()
	{
		return MaxTime;
	}
	public void setMaxTime(int aMaxTime)
	{
		MaxTime = aMaxTime;
	}
	public void setMaxTime(String aMaxTime)
	{
		if (aMaxTime != null && !aMaxTime.equals(""))
		{
			Integer tInteger = new Integer(aMaxTime);
			int i = tInteger.intValue();
			MaxTime = i;
		}
	}

	/**
	* 1-缺省 0-普通
	*/
	public String getDefaultFlag()
	{
		return DefaultFlag;
	}
	public void setDefaultFlag(String aDefaultFlag)
	{
		if(aDefaultFlag!=null && aDefaultFlag.length()>1)
			throw new IllegalArgumentException("缺省标记DefaultFlag值"+aDefaultFlag+"的长度"+aDefaultFlag.length()+"大于最大值1");
		DefaultFlag = aDefaultFlag;
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
	* 使用另外一个 LCGrpFeeTraceSchema 对象给 Schema 赋值
	* @param: aLCGrpFeeTraceSchema LCGrpFeeTraceSchema
	**/
	public void setSchema(LCGrpFeeTraceSchema aLCGrpFeeTraceSchema)
	{
		this.SerialNo = aLCGrpFeeTraceSchema.getSerialNo();
		this.GrpPolNo = aLCGrpFeeTraceSchema.getGrpPolNo();
		this.GrpContNo = aLCGrpFeeTraceSchema.getGrpContNo();
		this.RiskCode = aLCGrpFeeTraceSchema.getRiskCode();
		this.FeeCode = aLCGrpFeeTraceSchema.getFeeCode();
		this.InsuAccNo = aLCGrpFeeTraceSchema.getInsuAccNo();
		this.PayPlanCode = aLCGrpFeeTraceSchema.getPayPlanCode();
		this.PayInsuAccName = aLCGrpFeeTraceSchema.getPayInsuAccName();
		this.FeeCalMode = aLCGrpFeeTraceSchema.getFeeCalMode();
		this.FeeCalModeType = aLCGrpFeeTraceSchema.getFeeCalModeType();
		this.FeeCalCode = aLCGrpFeeTraceSchema.getFeeCalCode();
		this.FeeValue = aLCGrpFeeTraceSchema.getFeeValue();
		this.CompareValue = aLCGrpFeeTraceSchema.getCompareValue();
		this.FeePeriod = aLCGrpFeeTraceSchema.getFeePeriod();
		this.MaxTime = aLCGrpFeeTraceSchema.getMaxTime();
		this.DefaultFlag = aLCGrpFeeTraceSchema.getDefaultFlag();
		this.Operator = aLCGrpFeeTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpFeeTraceSchema.getMakeDate());
		this.MakeTime = aLCGrpFeeTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpFeeTraceSchema.getModifyDate());
		this.ModifyTime = aLCGrpFeeTraceSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("PayInsuAccName") == null )
				this.PayInsuAccName = null;
			else
				this.PayInsuAccName = rs.getString("PayInsuAccName").trim();

			if( rs.getString("FeeCalMode") == null )
				this.FeeCalMode = null;
			else
				this.FeeCalMode = rs.getString("FeeCalMode").trim();

			if( rs.getString("FeeCalModeType") == null )
				this.FeeCalModeType = null;
			else
				this.FeeCalModeType = rs.getString("FeeCalModeType").trim();

			if( rs.getString("FeeCalCode") == null )
				this.FeeCalCode = null;
			else
				this.FeeCalCode = rs.getString("FeeCalCode").trim();

			this.FeeValue = rs.getDouble("FeeValue");
			this.CompareValue = rs.getDouble("CompareValue");
			if( rs.getString("FeePeriod") == null )
				this.FeePeriod = null;
			else
				this.FeePeriod = rs.getString("FeePeriod").trim();

			this.MaxTime = rs.getInt("MaxTime");
			if( rs.getString("DefaultFlag") == null )
				this.DefaultFlag = null;
			else
				this.DefaultFlag = rs.getString("DefaultFlag").trim();

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
			logger.debug("数据库中的LCGrpFeeTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpFeeTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpFeeTraceSchema getSchema()
	{
		LCGrpFeeTraceSchema aLCGrpFeeTraceSchema = new LCGrpFeeTraceSchema();
		aLCGrpFeeTraceSchema.setSchema(this);
		return aLCGrpFeeTraceSchema;
	}

	public LCGrpFeeTraceDB getDB()
	{
		LCGrpFeeTraceDB aDBOper = new LCGrpFeeTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpFeeTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayInsuAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalModeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CompareValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeePeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxTime));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpFeeTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayInsuAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FeeCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			FeeCalModeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			FeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			FeeValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			CompareValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			FeePeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MaxTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			DefaultFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpFeeTraceSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("PayInsuAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayInsuAccName));
		}
		if (FCode.equalsIgnoreCase("FeeCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalMode));
		}
		if (FCode.equalsIgnoreCase("FeeCalModeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalModeType));
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalCode));
		}
		if (FCode.equalsIgnoreCase("FeeValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeValue));
		}
		if (FCode.equalsIgnoreCase("CompareValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompareValue));
		}
		if (FCode.equalsIgnoreCase("FeePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeePeriod));
		}
		if (FCode.equalsIgnoreCase("MaxTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxTime));
		}
		if (FCode.equalsIgnoreCase("DefaultFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultFlag));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayInsuAccName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FeeCalMode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(FeeCalModeType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FeeCalCode);
				break;
			case 11:
				strFieldValue = String.valueOf(FeeValue);
				break;
			case 12:
				strFieldValue = String.valueOf(CompareValue);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(FeePeriod);
				break;
			case 14:
				strFieldValue = String.valueOf(MaxTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DefaultFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PayInsuAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayInsuAccName = FValue.trim();
			}
			else
				PayInsuAccName = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalMode = FValue.trim();
			}
			else
				FeeCalMode = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalModeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalModeType = FValue.trim();
			}
			else
				FeeCalModeType = null;
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
		if (FCode.equalsIgnoreCase("FeeValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CompareValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CompareValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeePeriod = FValue.trim();
			}
			else
				FeePeriod = null;
		}
		if (FCode.equalsIgnoreCase("MaxTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxTime = i;
			}
		}
		if (FCode.equalsIgnoreCase("DefaultFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultFlag = FValue.trim();
			}
			else
				DefaultFlag = null;
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
		LCGrpFeeTraceSchema other = (LCGrpFeeTraceSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& FeeCode.equals(other.getFeeCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& PayInsuAccName.equals(other.getPayInsuAccName())
			&& FeeCalMode.equals(other.getFeeCalMode())
			&& FeeCalModeType.equals(other.getFeeCalModeType())
			&& FeeCalCode.equals(other.getFeeCalCode())
			&& FeeValue == other.getFeeValue()
			&& CompareValue == other.getCompareValue()
			&& FeePeriod.equals(other.getFeePeriod())
			&& MaxTime == other.getMaxTime()
			&& DefaultFlag.equals(other.getDefaultFlag())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 4;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 5;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 6;
		}
		if( strFieldName.equals("PayInsuAccName") ) {
			return 7;
		}
		if( strFieldName.equals("FeeCalMode") ) {
			return 8;
		}
		if( strFieldName.equals("FeeCalModeType") ) {
			return 9;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return 10;
		}
		if( strFieldName.equals("FeeValue") ) {
			return 11;
		}
		if( strFieldName.equals("CompareValue") ) {
			return 12;
		}
		if( strFieldName.equals("FeePeriod") ) {
			return 13;
		}
		if( strFieldName.equals("MaxTime") ) {
			return 14;
		}
		if( strFieldName.equals("DefaultFlag") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "FeeCode";
				break;
			case 5:
				strFieldName = "InsuAccNo";
				break;
			case 6:
				strFieldName = "PayPlanCode";
				break;
			case 7:
				strFieldName = "PayInsuAccName";
				break;
			case 8:
				strFieldName = "FeeCalMode";
				break;
			case 9:
				strFieldName = "FeeCalModeType";
				break;
			case 10:
				strFieldName = "FeeCalCode";
				break;
			case 11:
				strFieldName = "FeeValue";
				break;
			case 12:
				strFieldName = "CompareValue";
				break;
			case 13:
				strFieldName = "FeePeriod";
				break;
			case 14:
				strFieldName = "MaxTime";
				break;
			case 15:
				strFieldName = "DefaultFlag";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayInsuAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalModeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CompareValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeePeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DefaultFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
