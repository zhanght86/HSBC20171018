

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
import com.sinosoft.lis.db.LMRiskFeeDB;

/*
 * <p>ClassName: LMRiskFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 管理费描述
 */
public class LMRiskFeeSchema implements Schema, Cloneable
{
	// @Field
	/** 管理费编码 */
	private String FeeCode;
	/** 管理费名称 */
	private String FeeName;
	/** 管理费说明 */
	private String FeeNoti;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 交费项编码 */
	private String PayPlanCode;
	/** 关系说明 */
	private String PayInsuAccName;
	/** 管理费分类 */
	private String FeeKind;
	/** 费用项目分类 */
	private String FeeItemType;
	/** 费用收取位置 */
	private String FeeTakePlace;
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
	/** 扣除管理费起始时间 */
	private Date FeeStartDate;
	/** 管理费顺序 */
	private int FeeNum;
	/** 计价基数类型 */
	private String FeeBaseType;
	/** 后续处理类名 */
	private String InterfaceClassName;
	/** 收取类型 */
	private String FeeType;
	/** 收取时点 */
	private String PeriodType;
	/** 险种编码 */
	private String RiskCode;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "FeeCode";
		pk[1] = "InsuAccNo";
		pk[2] = "PayPlanCode";
		pk[3] = "FeeCalMode";

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
		LMRiskFeeSchema cloned = (LMRiskFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		FeeCode = aFeeCode;
	}
	public String getFeeName()
	{
		return FeeName;
	}
	public void setFeeName(String aFeeName)
	{
		FeeName = aFeeName;
	}
	public String getFeeNoti()
	{
		return FeeNoti;
	}
	public void setFeeNoti(String aFeeNoti)
	{
		FeeNoti = aFeeNoti;
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
	* 如果该编码前6位为全0，则表示加费。<p>
	* 第7,8位表示加费的次数。
	*/
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getPayInsuAccName()
	{
		return PayInsuAccName;
	}
	public void setPayInsuAccName(String aPayInsuAccName)
	{
		PayInsuAccName = aPayInsuAccName;
	}
	/**
	* 01-团单管理费<p>
	* 02-团单转个单管理费<p>
	* 03-个单管理费
	*/
	public String getFeeKind()
	{
		return FeeKind;
	}
	public void setFeeKind(String aFeeKind)
	{
		FeeKind = aFeeKind;
	}
	/**
	* 01-管理费<p>
	* 02-行政管理费<p>
	* 03-投资管理费<p>
	* 04-帐户转换费<p>
	* 05-持续奖金奖励
	*/
	public String getFeeItemType()
	{
		return FeeItemType;
	}
	public void setFeeItemType(String aFeeItemType)
	{
		FeeItemType = aFeeItemType;
	}
	/**
	* 01－交费时<p>
	* 02－固定日期<p>
	* 03－生效对应日<p>
	* 04－转个人单对应日<p>
	* 05－每月的首次计价日<p>
	* 06－帐户转换时使用
	*/
	public String getFeeTakePlace()
	{
		return FeeTakePlace;
	}
	public void setFeeTakePlace(String aFeeTakePlace)
	{
		FeeTakePlace = aFeeTakePlace;
	}
	/**
	* 01-固定值（内扣）<p>
	* 02-固定比例 （内扣）<p>
	* 03-固定值（外缴）<p>
	* 04-固定比例 （外缴）<p>
	* 05-Min(固定值与比例结合)<p>
	* 06-Max(固定值和比例结合）<p>
	* 07-分档计算<p>
	* 08-累计分档计算
	*/
	public String getFeeCalMode()
	{
		return FeeCalMode;
	}
	public void setFeeCalMode(String aFeeCalMode)
	{
		FeeCalMode = aFeeCalMode;
	}
	/**
	* 0-直接取值<p>
	* 1-sql描述<p>
	* 2-class计算
	*/
	public String getFeeCalModeType()
	{
		return FeeCalModeType;
	}
	public void setFeeCalModeType(String aFeeCalModeType)
	{
		FeeCalModeType = aFeeCalModeType;
	}
	public String getFeeCalCode()
	{
		return FeeCalCode;
	}
	public void setFeeCalCode(String aFeeCalCode)
	{
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
	* 01-年<p>
	* 02-月<p>
	* 03-日
	*/
	public String getFeePeriod()
	{
		return FeePeriod;
	}
	public void setFeePeriod(String aFeePeriod)
	{
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
	* 1-缺省<p>
	* 0-普通
	*/
	public String getDefaultFlag()
	{
		return DefaultFlag;
	}
	public void setDefaultFlag(String aDefaultFlag)
	{
		DefaultFlag = aDefaultFlag;
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
	* 记录管理费设置的有效时间，在计价时保存计价日期，其他管理费记录时可保存一个统一值（1900-01-01)
	*/
	public String getFeeStartDate()
	{
		if( FeeStartDate != null )
			return fDate.getString(FeeStartDate);
		else
			return null;
	}
	public void setFeeStartDate(Date aFeeStartDate)
	{
		FeeStartDate = aFeeStartDate;
	}
	public void setFeeStartDate(String aFeeStartDate)
	{
		if (aFeeStartDate != null && !aFeeStartDate.equals("") )
		{
			FeeStartDate = fDate.getDate( aFeeStartDate );
		}
		else
			FeeStartDate = null;
	}

	/**
	* 按照费用扣减或奖励的顺序
	*/
	public int getFeeNum()
	{
		return FeeNum;
	}
	public void setFeeNum(int aFeeNum)
	{
		FeeNum = aFeeNum;
	}
	public void setFeeNum(String aFeeNum)
	{
		if (aFeeNum != null && !aFeeNum.equals(""))
		{
			Integer tInteger = new Integer(aFeeNum);
			int i = tInteger.intValue();
			FeeNum = i;
		}
	}

	/**
	* 1-以初始基数作为计价基础<p>
	* 2-以上次计算结果作为计价基础
	*/
	public String getFeeBaseType()
	{
		return FeeBaseType;
	}
	public void setFeeBaseType(String aFeeBaseType)
	{
		FeeBaseType = aFeeBaseType;
	}
	/**
	* 使用class计算管理费
	*/
	public String getInterfaceClassName()
	{
		return InterfaceClassName;
	}
	public void setInterfaceClassName(String aInterfaceClassName)
	{
		InterfaceClassName = aInterfaceClassName;
	}
	/**
	* 0-自然收取<p>
	* 1-按照交费账户顺序收取<p>
	* 2-按照投资账户顺序收取<p>
	* 3-按照交费投资账户顺序收取
	*/
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		FeeType = aFeeType;
	}
	/**
	* 0-当天收取<p>
	* 1-上一个计价日<p>
	* 2-下一个计价日
	*/
	public String getPeriodType()
	{
		return PeriodType;
	}
	public void setPeriodType(String aPeriodType)
	{
		PeriodType = aPeriodType;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}

	/**
	* 使用另外一个 LMRiskFeeSchema 对象给 Schema 赋值
	* @param: aLMRiskFeeSchema LMRiskFeeSchema
	**/
	public void setSchema(LMRiskFeeSchema aLMRiskFeeSchema)
	{
		this.FeeCode = aLMRiskFeeSchema.getFeeCode();
		this.FeeName = aLMRiskFeeSchema.getFeeName();
		this.FeeNoti = aLMRiskFeeSchema.getFeeNoti();
		this.InsuAccNo = aLMRiskFeeSchema.getInsuAccNo();
		this.PayPlanCode = aLMRiskFeeSchema.getPayPlanCode();
		this.PayInsuAccName = aLMRiskFeeSchema.getPayInsuAccName();
		this.FeeKind = aLMRiskFeeSchema.getFeeKind();
		this.FeeItemType = aLMRiskFeeSchema.getFeeItemType();
		this.FeeTakePlace = aLMRiskFeeSchema.getFeeTakePlace();
		this.FeeCalMode = aLMRiskFeeSchema.getFeeCalMode();
		this.FeeCalModeType = aLMRiskFeeSchema.getFeeCalModeType();
		this.FeeCalCode = aLMRiskFeeSchema.getFeeCalCode();
		this.FeeValue = aLMRiskFeeSchema.getFeeValue();
		this.CompareValue = aLMRiskFeeSchema.getCompareValue();
		this.FeePeriod = aLMRiskFeeSchema.getFeePeriod();
		this.MaxTime = aLMRiskFeeSchema.getMaxTime();
		this.DefaultFlag = aLMRiskFeeSchema.getDefaultFlag();
		this.Operator = aLMRiskFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLMRiskFeeSchema.getMakeDate());
		this.MakeTime = aLMRiskFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLMRiskFeeSchema.getModifyDate());
		this.ModifyTime = aLMRiskFeeSchema.getModifyTime();
		this.FeeStartDate = fDate.getDate( aLMRiskFeeSchema.getFeeStartDate());
		this.FeeNum = aLMRiskFeeSchema.getFeeNum();
		this.FeeBaseType = aLMRiskFeeSchema.getFeeBaseType();
		this.InterfaceClassName = aLMRiskFeeSchema.getInterfaceClassName();
		this.FeeType = aLMRiskFeeSchema.getFeeType();
		this.PeriodType = aLMRiskFeeSchema.getPeriodType();
		this.RiskCode = aLMRiskFeeSchema.getRiskCode();
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
			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("FeeName") == null )
				this.FeeName = null;
			else
				this.FeeName = rs.getString("FeeName").trim();

			if( rs.getString("FeeNoti") == null )
				this.FeeNoti = null;
			else
				this.FeeNoti = rs.getString("FeeNoti").trim();

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

			if( rs.getString("FeeKind") == null )
				this.FeeKind = null;
			else
				this.FeeKind = rs.getString("FeeKind").trim();

			if( rs.getString("FeeItemType") == null )
				this.FeeItemType = null;
			else
				this.FeeItemType = rs.getString("FeeItemType").trim();

			if( rs.getString("FeeTakePlace") == null )
				this.FeeTakePlace = null;
			else
				this.FeeTakePlace = rs.getString("FeeTakePlace").trim();

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

			this.FeeStartDate = rs.getDate("FeeStartDate");
			this.FeeNum = rs.getInt("FeeNum");
			if( rs.getString("FeeBaseType") == null )
				this.FeeBaseType = null;
			else
				this.FeeBaseType = rs.getString("FeeBaseType").trim();

			if( rs.getString("InterfaceClassName") == null )
				this.InterfaceClassName = null;
			else
				this.InterfaceClassName = rs.getString("InterfaceClassName").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			if( rs.getString("PeriodType") == null )
				this.PeriodType = null;
			else
				this.PeriodType = rs.getString("PeriodType").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRiskFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskFeeSchema getSchema()
	{
		LMRiskFeeSchema aLMRiskFeeSchema = new LMRiskFeeSchema();
		aLMRiskFeeSchema.setSchema(this);
		return aLMRiskFeeSchema;
	}

	public LMRiskFeeDB getDB()
	{
		LMRiskFeeDB aDBOper = new LMRiskFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeNoti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayInsuAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTakePlace)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FeeStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeBaseType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterfaceClassName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PeriodType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FeeNoti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayInsuAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FeeItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FeeTakePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			FeeCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			FeeCalModeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			FeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			FeeValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			CompareValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			FeePeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MaxTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			DefaultFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			FeeStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			FeeNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			FeeBaseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InterfaceClassName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			PeriodType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskFeeSchema";
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("FeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeName));
		}
		if (FCode.equalsIgnoreCase("FeeNoti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeNoti));
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
		if (FCode.equalsIgnoreCase("FeeKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeKind));
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemType));
		}
		if (FCode.equalsIgnoreCase("FeeTakePlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTakePlace));
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
		if (FCode.equalsIgnoreCase("FeeStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFeeStartDate()));
		}
		if (FCode.equalsIgnoreCase("FeeNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeNum));
		}
		if (FCode.equalsIgnoreCase("FeeBaseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeBaseType));
		}
		if (FCode.equalsIgnoreCase("InterfaceClassName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterfaceClassName));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("PeriodType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeriodType));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
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
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FeeNoti);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PayInsuAccName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FeeKind);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FeeItemType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FeeTakePlace);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(FeeCalMode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FeeCalModeType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(FeeCalCode);
				break;
			case 12:
				strFieldValue = String.valueOf(FeeValue);
				break;
			case 13:
				strFieldValue = String.valueOf(CompareValue);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(FeePeriod);
				break;
			case 15:
				strFieldValue = String.valueOf(MaxTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(DefaultFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFeeStartDate()));
				break;
			case 23:
				strFieldValue = String.valueOf(FeeNum);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(FeeBaseType);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InterfaceClassName);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(PeriodType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
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

		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeName = FValue.trim();
			}
			else
				FeeName = null;
		}
		if (FCode.equalsIgnoreCase("FeeNoti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeNoti = FValue.trim();
			}
			else
				FeeNoti = null;
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
		if (FCode.equalsIgnoreCase("FeeKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeKind = FValue.trim();
			}
			else
				FeeKind = null;
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemType = FValue.trim();
			}
			else
				FeeItemType = null;
		}
		if (FCode.equalsIgnoreCase("FeeTakePlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTakePlace = FValue.trim();
			}
			else
				FeeTakePlace = null;
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
		if (FCode.equalsIgnoreCase("FeeStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FeeStartDate = fDate.getDate( FValue );
			}
			else
				FeeStartDate = null;
		}
		if (FCode.equalsIgnoreCase("FeeNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FeeNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeBaseType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeBaseType = FValue.trim();
			}
			else
				FeeBaseType = null;
		}
		if (FCode.equalsIgnoreCase("InterfaceClassName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterfaceClassName = FValue.trim();
			}
			else
				InterfaceClassName = null;
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		if (FCode.equalsIgnoreCase("PeriodType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PeriodType = FValue.trim();
			}
			else
				PeriodType = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskFeeSchema other = (LMRiskFeeSchema)otherObject;
		return
			FeeCode.equals(other.getFeeCode())
			&& FeeName.equals(other.getFeeName())
			&& FeeNoti.equals(other.getFeeNoti())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& PayInsuAccName.equals(other.getPayInsuAccName())
			&& FeeKind.equals(other.getFeeKind())
			&& FeeItemType.equals(other.getFeeItemType())
			&& FeeTakePlace.equals(other.getFeeTakePlace())
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
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(FeeStartDate).equals(other.getFeeStartDate())
			&& FeeNum == other.getFeeNum()
			&& FeeBaseType.equals(other.getFeeBaseType())
			&& InterfaceClassName.equals(other.getInterfaceClassName())
			&& FeeType.equals(other.getFeeType())
			&& PeriodType.equals(other.getPeriodType())
			&& RiskCode.equals(other.getRiskCode());
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
		if( strFieldName.equals("FeeCode") ) {
			return 0;
		}
		if( strFieldName.equals("FeeName") ) {
			return 1;
		}
		if( strFieldName.equals("FeeNoti") ) {
			return 2;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 3;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("PayInsuAccName") ) {
			return 5;
		}
		if( strFieldName.equals("FeeKind") ) {
			return 6;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return 7;
		}
		if( strFieldName.equals("FeeTakePlace") ) {
			return 8;
		}
		if( strFieldName.equals("FeeCalMode") ) {
			return 9;
		}
		if( strFieldName.equals("FeeCalModeType") ) {
			return 10;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return 11;
		}
		if( strFieldName.equals("FeeValue") ) {
			return 12;
		}
		if( strFieldName.equals("CompareValue") ) {
			return 13;
		}
		if( strFieldName.equals("FeePeriod") ) {
			return 14;
		}
		if( strFieldName.equals("MaxTime") ) {
			return 15;
		}
		if( strFieldName.equals("DefaultFlag") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("FeeStartDate") ) {
			return 22;
		}
		if( strFieldName.equals("FeeNum") ) {
			return 23;
		}
		if( strFieldName.equals("FeeBaseType") ) {
			return 24;
		}
		if( strFieldName.equals("InterfaceClassName") ) {
			return 25;
		}
		if( strFieldName.equals("FeeType") ) {
			return 26;
		}
		if( strFieldName.equals("PeriodType") ) {
			return 27;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 28;
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
				strFieldName = "FeeCode";
				break;
			case 1:
				strFieldName = "FeeName";
				break;
			case 2:
				strFieldName = "FeeNoti";
				break;
			case 3:
				strFieldName = "InsuAccNo";
				break;
			case 4:
				strFieldName = "PayPlanCode";
				break;
			case 5:
				strFieldName = "PayInsuAccName";
				break;
			case 6:
				strFieldName = "FeeKind";
				break;
			case 7:
				strFieldName = "FeeItemType";
				break;
			case 8:
				strFieldName = "FeeTakePlace";
				break;
			case 9:
				strFieldName = "FeeCalMode";
				break;
			case 10:
				strFieldName = "FeeCalModeType";
				break;
			case 11:
				strFieldName = "FeeCalCode";
				break;
			case 12:
				strFieldName = "FeeValue";
				break;
			case 13:
				strFieldName = "CompareValue";
				break;
			case 14:
				strFieldName = "FeePeriod";
				break;
			case 15:
				strFieldName = "MaxTime";
				break;
			case 16:
				strFieldName = "DefaultFlag";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "FeeStartDate";
				break;
			case 23:
				strFieldName = "FeeNum";
				break;
			case 24:
				strFieldName = "FeeBaseType";
				break;
			case 25:
				strFieldName = "InterfaceClassName";
				break;
			case 26:
				strFieldName = "FeeType";
				break;
			case 27:
				strFieldName = "PeriodType";
				break;
			case 28:
				strFieldName = "RiskCode";
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
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeNoti") ) {
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
		if( strFieldName.equals("FeeKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeTakePlace") ) {
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
		if( strFieldName.equals("FeeStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FeeNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeBaseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterfaceClassName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeriodType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
