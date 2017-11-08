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
import com.sinosoft.lis.db.LMDutyPayDB;

/*
 * <p>ClassName: LMDutyPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyPaySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyPaySchema.class);

	// @Field
	/** 缴费编码 */
	private String PayPlanCode;
	/** 缴费名称 */
	private String PayPlanName;
	/** 缴费类型 */
	private String Type;
	/** 交费间隔 */
	private int PayIntv;
	/** 缴费终止期间单位 */
	private String PayEndYearFlag;
	/** 缴费终止期间 */
	private int PayEndYear;
	/** 缴费终止日期计算参照 */
	private String PayEndDateCalRef;
	/** 缴费终止日期计算方式 */
	private String PayEndDateCalMode;
	/** 默认值 */
	private String DefaultVal;
	/** 算法 */
	private String CalCode;
	/** 反算算法 */
	private String CnterCalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 保费分配比例 */
	private double Rate;
	/** 最低限额 */
	private double MinPay;
	/** 保证收益率 */
	private double AssuYield;
	/** 提取管理费比例 */
	private double FeeRate;
	/** 缴至日期计算方法 */
	private String PayToDateCalMode;
	/** 催缴标记 */
	private String UrgePayFlag;
	/** 部分缴费标记 */
	private String PayLackFlag;
	/** 挂帐标记 */
	private String PayOverFlag;
	/** 挂帐处理 */
	private String PayOverDeal;
	/** 免交标记 */
	private String AvoidPayFlag;
	/** 缴费宽限期 */
	private int GracePeriod;
	/** 公用标记 */
	private String PubFlag;
	/** 是否允许零值标记 */
	private String ZeroFlag;
	/** 是否和账户相关 */
	private String NeedAcc;
	/** 交费目的分类 */
	private String PayAimClass;
	/** 交费起始期间 */
	private int PayStartYear;
	/** 交费起始期间单位 */
	private String PayStartYearFlag;
	/** 交费起始日期计算参照 */
	private String PayStartDateCalRef;
	/** 交费起始日期计算方式 */
	private String PayStartDateCalMode;

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PayPlanCode";

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
		LMDutyPaySchema cloned = (LMDutyPaySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	* 0 －－ 一个保费项描述。 1 －－ 健康加费 2 －－ 职业加费 以下为以前的描述，现在不再使用。 账户性质(101-交费/211-帐户交费型现金制;212-帐户交费型股份制;221-帐户普通型现金制; 222-帐户普通型股份制;301-附加费)(2001/10/10) ##[1]-1:交费;2:帐户;3:加费 ##[2]-0:无关;1:交费履历型;2:普通帐户型 ##[3]-0:无关;1:单位为金额;2:单位为股份 ##[4]-0:普通帐户；1－门诊帐户 ##[5]-0：普通帐户；1－分红帐户（分红帐户在分红时有2.5%的保障收益，因此在保全时先不计算出利息，而在结算时计算分红结果）
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	/**
	* -1 表示随意缴。 0 －－ 表示趸缴 1 －－ 月缴 3 －－ 季缴 6 －－ 半年缴 12 －－ 年缴
	*/
	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getPayEndDateCalRef()
	{
		return PayEndDateCalRef;
	}
	public void setPayEndDateCalRef(String aPayEndDateCalRef)
	{
		PayEndDateCalRef = aPayEndDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getPayEndDateCalMode()
	{
		return PayEndDateCalMode;
	}
	public void setPayEndDateCalMode(String aPayEndDateCalMode)
	{
		PayEndDateCalMode = aPayEndDateCalMode;
	}
	public String getDefaultVal()
	{
		return DefaultVal;
	}
	public void setDefaultVal(String aDefaultVal)
	{
		DefaultVal = aDefaultVal;
	}
	/**
	* 保额算保费
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 保费算保费
	*/
	public String getCnterCalCode()
	{
		return CnterCalCode;
	}
	public void setCnterCalCode(String aCnterCalCode)
	{
		CnterCalCode = aCnterCalCode;
	}
	/**
	* 其他算保费
	*/
	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		OthCalCode = aOthCalCode;
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

	public double getMinPay()
	{
		return MinPay;
	}
	public void setMinPay(double aMinPay)
	{
		MinPay = aMinPay;
	}
	public void setMinPay(String aMinPay)
	{
		if (aMinPay != null && !aMinPay.equals(""))
		{
			Double tDouble = new Double(aMinPay);
			double d = tDouble.doubleValue();
			MinPay = d;
		}
	}

	public double getAssuYield()
	{
		return AssuYield;
	}
	public void setAssuYield(double aAssuYield)
	{
		AssuYield = aAssuYield;
	}
	public void setAssuYield(String aAssuYield)
	{
		if (aAssuYield != null && !aAssuYield.equals(""))
		{
			Double tDouble = new Double(aAssuYield);
			double d = tDouble.doubleValue();
			AssuYield = d;
		}
	}

	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}

	/**
	* 0-正常算法、1-取当月一日
	*/
	public String getPayToDateCalMode()
	{
		return PayToDateCalMode;
	}
	public void setPayToDateCalMode(String aPayToDateCalMode)
	{
		PayToDateCalMode = aPayToDateCalMode;
	}
	/**
	* Y--催缴、N--不催缴。
	*/
	public String getUrgePayFlag()
	{
		return UrgePayFlag;
	}
	public void setUrgePayFlag(String aUrgePayFlag)
	{
		UrgePayFlag = aUrgePayFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getPayLackFlag()
	{
		return PayLackFlag;
	}
	public void setPayLackFlag(String aPayLackFlag)
	{
		PayLackFlag = aPayLackFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getPayOverFlag()
	{
		return PayOverFlag;
	}
	public void setPayOverFlag(String aPayOverFlag)
	{
		PayOverFlag = aPayOverFlag;
	}
	/**
	* 表明多缴部分的记帐方式
	*/
	public String getPayOverDeal()
	{
		return PayOverDeal;
	}
	public void setPayOverDeal(String aPayOverDeal)
	{
		PayOverDeal = aPayOverDeal;
	}
	/**
	* Y--是 N--否
	*/
	public String getAvoidPayFlag()
	{
		return AvoidPayFlag;
	}
	public void setAvoidPayFlag(String aAvoidPayFlag)
	{
		AvoidPayFlag = aAvoidPayFlag;
	}
	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* Y--公用帐户、N--普通帐户
	*/
	public String getPubFlag()
	{
		return PubFlag;
	}
	public void setPubFlag(String aPubFlag)
	{
		PubFlag = aPubFlag;
	}
	/**
	* Y--允许 N--不允许 not null
	*/
	public String getZeroFlag()
	{
		return ZeroFlag;
	}
	public void setZeroFlag(String aZeroFlag)
	{
		ZeroFlag = aZeroFlag;
	}
	/**
	* 0 －－ 账户无关 1 －－ 账户相关
	*/
	public String getNeedAcc()
	{
		return NeedAcc;
	}
	public void setNeedAcc(String aNeedAcc)
	{
		NeedAcc = aNeedAcc;
	}
	/**
	* 1 --个人交费 2 --集体交费
	*/
	public String getPayAimClass()
	{
		return PayAimClass;
	}
	public void setPayAimClass(String aPayAimClass)
	{
		PayAimClass = aPayAimClass;
	}
	public int getPayStartYear()
	{
		return PayStartYear;
	}
	public void setPayStartYear(int aPayStartYear)
	{
		PayStartYear = aPayStartYear;
	}
	public void setPayStartYear(String aPayStartYear)
	{
		if (aPayStartYear != null && !aPayStartYear.equals(""))
		{
			Integer tInteger = new Integer(aPayStartYear);
			int i = tInteger.intValue();
			PayStartYear = i;
		}
	}

	/**
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getPayStartYearFlag()
	{
		return PayStartYearFlag;
	}
	public void setPayStartYearFlag(String aPayStartYearFlag)
	{
		PayStartYearFlag = aPayStartYearFlag;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getPayStartDateCalRef()
	{
		return PayStartDateCalRef;
	}
	public void setPayStartDateCalRef(String aPayStartDateCalRef)
	{
		PayStartDateCalRef = aPayStartDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getPayStartDateCalMode()
	{
		return PayStartDateCalMode;
	}
	public void setPayStartDateCalMode(String aPayStartDateCalMode)
	{
		PayStartDateCalMode = aPayStartDateCalMode;
	}

	/**
	* 使用另外一个 LMDutyPaySchema 对象给 Schema 赋值
	* @param: aLMDutyPaySchema LMDutyPaySchema
	**/
	public void setSchema(LMDutyPaySchema aLMDutyPaySchema)
	{
		this.PayPlanCode = aLMDutyPaySchema.getPayPlanCode();
		this.PayPlanName = aLMDutyPaySchema.getPayPlanName();
		this.Type = aLMDutyPaySchema.getType();
		this.PayIntv = aLMDutyPaySchema.getPayIntv();
		this.PayEndYearFlag = aLMDutyPaySchema.getPayEndYearFlag();
		this.PayEndYear = aLMDutyPaySchema.getPayEndYear();
		this.PayEndDateCalRef = aLMDutyPaySchema.getPayEndDateCalRef();
		this.PayEndDateCalMode = aLMDutyPaySchema.getPayEndDateCalMode();
		this.DefaultVal = aLMDutyPaySchema.getDefaultVal();
		this.CalCode = aLMDutyPaySchema.getCalCode();
		this.CnterCalCode = aLMDutyPaySchema.getCnterCalCode();
		this.OthCalCode = aLMDutyPaySchema.getOthCalCode();
		this.Rate = aLMDutyPaySchema.getRate();
		this.MinPay = aLMDutyPaySchema.getMinPay();
		this.AssuYield = aLMDutyPaySchema.getAssuYield();
		this.FeeRate = aLMDutyPaySchema.getFeeRate();
		this.PayToDateCalMode = aLMDutyPaySchema.getPayToDateCalMode();
		this.UrgePayFlag = aLMDutyPaySchema.getUrgePayFlag();
		this.PayLackFlag = aLMDutyPaySchema.getPayLackFlag();
		this.PayOverFlag = aLMDutyPaySchema.getPayOverFlag();
		this.PayOverDeal = aLMDutyPaySchema.getPayOverDeal();
		this.AvoidPayFlag = aLMDutyPaySchema.getAvoidPayFlag();
		this.GracePeriod = aLMDutyPaySchema.getGracePeriod();
		this.PubFlag = aLMDutyPaySchema.getPubFlag();
		this.ZeroFlag = aLMDutyPaySchema.getZeroFlag();
		this.NeedAcc = aLMDutyPaySchema.getNeedAcc();
		this.PayAimClass = aLMDutyPaySchema.getPayAimClass();
		this.PayStartYear = aLMDutyPaySchema.getPayStartYear();
		this.PayStartYearFlag = aLMDutyPaySchema.getPayStartYearFlag();
		this.PayStartDateCalRef = aLMDutyPaySchema.getPayStartDateCalRef();
		this.PayStartDateCalMode = aLMDutyPaySchema.getPayStartDateCalMode();
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
			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("PayPlanName") == null )
				this.PayPlanName = null;
			else
				this.PayPlanName = rs.getString("PayPlanName").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("PayEndDateCalRef") == null )
				this.PayEndDateCalRef = null;
			else
				this.PayEndDateCalRef = rs.getString("PayEndDateCalRef").trim();

			if( rs.getString("PayEndDateCalMode") == null )
				this.PayEndDateCalMode = null;
			else
				this.PayEndDateCalMode = rs.getString("PayEndDateCalMode").trim();

			if( rs.getString("DefaultVal") == null )
				this.DefaultVal = null;
			else
				this.DefaultVal = rs.getString("DefaultVal").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CnterCalCode") == null )
				this.CnterCalCode = null;
			else
				this.CnterCalCode = rs.getString("CnterCalCode").trim();

			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			this.Rate = rs.getDouble("Rate");
			this.MinPay = rs.getDouble("MinPay");
			this.AssuYield = rs.getDouble("AssuYield");
			this.FeeRate = rs.getDouble("FeeRate");
			if( rs.getString("PayToDateCalMode") == null )
				this.PayToDateCalMode = null;
			else
				this.PayToDateCalMode = rs.getString("PayToDateCalMode").trim();

			if( rs.getString("UrgePayFlag") == null )
				this.UrgePayFlag = null;
			else
				this.UrgePayFlag = rs.getString("UrgePayFlag").trim();

			if( rs.getString("PayLackFlag") == null )
				this.PayLackFlag = null;
			else
				this.PayLackFlag = rs.getString("PayLackFlag").trim();

			if( rs.getString("PayOverFlag") == null )
				this.PayOverFlag = null;
			else
				this.PayOverFlag = rs.getString("PayOverFlag").trim();

			if( rs.getString("PayOverDeal") == null )
				this.PayOverDeal = null;
			else
				this.PayOverDeal = rs.getString("PayOverDeal").trim();

			if( rs.getString("AvoidPayFlag") == null )
				this.AvoidPayFlag = null;
			else
				this.AvoidPayFlag = rs.getString("AvoidPayFlag").trim();

			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("PubFlag") == null )
				this.PubFlag = null;
			else
				this.PubFlag = rs.getString("PubFlag").trim();

			if( rs.getString("ZeroFlag") == null )
				this.ZeroFlag = null;
			else
				this.ZeroFlag = rs.getString("ZeroFlag").trim();

			if( rs.getString("NeedAcc") == null )
				this.NeedAcc = null;
			else
				this.NeedAcc = rs.getString("NeedAcc").trim();

			if( rs.getString("PayAimClass") == null )
				this.PayAimClass = null;
			else
				this.PayAimClass = rs.getString("PayAimClass").trim();

			this.PayStartYear = rs.getInt("PayStartYear");
			if( rs.getString("PayStartYearFlag") == null )
				this.PayStartYearFlag = null;
			else
				this.PayStartYearFlag = rs.getString("PayStartYearFlag").trim();

			if( rs.getString("PayStartDateCalRef") == null )
				this.PayStartDateCalRef = null;
			else
				this.PayStartDateCalRef = rs.getString("PayStartDateCalRef").trim();

			if( rs.getString("PayStartDateCalMode") == null )
				this.PayStartDateCalMode = null;
			else
				this.PayStartDateCalMode = rs.getString("PayStartDateCalMode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyPaySchema getSchema()
	{
		LMDutyPaySchema aLMDutyPaySchema = new LMDutyPaySchema();
		aLMDutyPaySchema.setSchema(this);
		return aLMDutyPaySchema;
	}

	public LMDutyPayDB getDB()
	{
		LMDutyPayDB aDBOper = new LMDutyPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultVal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CnterCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssuYield));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayToDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLackFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayOverFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayOverDeal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvoidPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZeroFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAimClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayStartYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayStartYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayStartDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayStartDateCalMode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			PayEndDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayEndDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DefaultVal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CnterCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			MinPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			AssuYield = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			PayToDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			UrgePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PayLackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PayOverFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PayOverDeal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AvoidPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			PubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ZeroFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			NeedAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			PayAimClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			PayStartYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			PayStartYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PayStartDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			PayStartDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyPaySchema";
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanName));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDateCalRef));
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultVal));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CnterCalCode));
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthCalCode));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("MinPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinPay));
		}
		if (FCode.equalsIgnoreCase("AssuYield"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuYield));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
		}
		if (FCode.equalsIgnoreCase("PayToDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayToDateCalMode));
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgePayFlag));
		}
		if (FCode.equalsIgnoreCase("PayLackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLackFlag));
		}
		if (FCode.equalsIgnoreCase("PayOverFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayOverFlag));
		}
		if (FCode.equalsIgnoreCase("PayOverDeal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayOverDeal));
		}
		if (FCode.equalsIgnoreCase("AvoidPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvoidPayFlag));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("PubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PubFlag));
		}
		if (FCode.equalsIgnoreCase("ZeroFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZeroFlag));
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedAcc));
		}
		if (FCode.equalsIgnoreCase("PayAimClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAimClass));
		}
		if (FCode.equalsIgnoreCase("PayStartYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartYear));
		}
		if (FCode.equalsIgnoreCase("PayStartYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartDateCalRef));
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartDateCalMode));
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
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PayPlanName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 3:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 5:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayEndDateCalRef);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayEndDateCalMode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DefaultVal);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CnterCalCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 12:
				strFieldValue = String.valueOf(Rate);
				break;
			case 13:
				strFieldValue = String.valueOf(MinPay);
				break;
			case 14:
				strFieldValue = String.valueOf(AssuYield);
				break;
			case 15:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PayToDateCalMode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(UrgePayFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PayLackFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PayOverFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PayOverDeal);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AvoidPayFlag);
				break;
			case 22:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(PubFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ZeroFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(NeedAcc);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(PayAimClass);
				break;
			case 27:
				strFieldValue = String.valueOf(PayStartYear);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PayStartYearFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(PayStartDateCalRef);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(PayStartDateCalMode);
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
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayEndYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDateCalRef = FValue.trim();
			}
			else
				PayEndDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDateCalMode = FValue.trim();
			}
			else
				PayEndDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultVal = FValue.trim();
			}
			else
				DefaultVal = null;
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
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CnterCalCode = FValue.trim();
			}
			else
				CnterCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthCalCode = FValue.trim();
			}
			else
				OthCalCode = null;
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
		if (FCode.equalsIgnoreCase("MinPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MinPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("AssuYield"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AssuYield = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayToDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayToDateCalMode = FValue.trim();
			}
			else
				PayToDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgePayFlag = FValue.trim();
			}
			else
				UrgePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayLackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayLackFlag = FValue.trim();
			}
			else
				PayLackFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayOverFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayOverFlag = FValue.trim();
			}
			else
				PayOverFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayOverDeal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayOverDeal = FValue.trim();
			}
			else
				PayOverDeal = null;
		}
		if (FCode.equalsIgnoreCase("AvoidPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvoidPayFlag = FValue.trim();
			}
			else
				AvoidPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("PubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PubFlag = FValue.trim();
			}
			else
				PubFlag = null;
		}
		if (FCode.equalsIgnoreCase("ZeroFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZeroFlag = FValue.trim();
			}
			else
				ZeroFlag = null;
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedAcc = FValue.trim();
			}
			else
				NeedAcc = null;
		}
		if (FCode.equalsIgnoreCase("PayAimClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAimClass = FValue.trim();
			}
			else
				PayAimClass = null;
		}
		if (FCode.equalsIgnoreCase("PayStartYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayStartYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayStartYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayStartYearFlag = FValue.trim();
			}
			else
				PayStartYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayStartDateCalRef = FValue.trim();
			}
			else
				PayStartDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayStartDateCalMode = FValue.trim();
			}
			else
				PayStartDateCalMode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyPaySchema other = (LMDutyPaySchema)otherObject;
		return
			PayPlanCode.equals(other.getPayPlanCode())
			&& PayPlanName.equals(other.getPayPlanName())
			&& Type.equals(other.getType())
			&& PayIntv == other.getPayIntv()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& PayEndDateCalRef.equals(other.getPayEndDateCalRef())
			&& PayEndDateCalMode.equals(other.getPayEndDateCalMode())
			&& DefaultVal.equals(other.getDefaultVal())
			&& CalCode.equals(other.getCalCode())
			&& CnterCalCode.equals(other.getCnterCalCode())
			&& OthCalCode.equals(other.getOthCalCode())
			&& Rate == other.getRate()
			&& MinPay == other.getMinPay()
			&& AssuYield == other.getAssuYield()
			&& FeeRate == other.getFeeRate()
			&& PayToDateCalMode.equals(other.getPayToDateCalMode())
			&& UrgePayFlag.equals(other.getUrgePayFlag())
			&& PayLackFlag.equals(other.getPayLackFlag())
			&& PayOverFlag.equals(other.getPayOverFlag())
			&& PayOverDeal.equals(other.getPayOverDeal())
			&& AvoidPayFlag.equals(other.getAvoidPayFlag())
			&& GracePeriod == other.getGracePeriod()
			&& PubFlag.equals(other.getPubFlag())
			&& ZeroFlag.equals(other.getZeroFlag())
			&& NeedAcc.equals(other.getNeedAcc())
			&& PayAimClass.equals(other.getPayAimClass())
			&& PayStartYear == other.getPayStartYear()
			&& PayStartYearFlag.equals(other.getPayStartYearFlag())
			&& PayStartDateCalRef.equals(other.getPayStartDateCalRef())
			&& PayStartDateCalMode.equals(other.getPayStartDateCalMode());
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
		if( strFieldName.equals("PayPlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return 1;
		}
		if( strFieldName.equals("Type") ) {
			return 2;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 3;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 4;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 5;
		}
		if( strFieldName.equals("PayEndDateCalRef") ) {
			return 6;
		}
		if( strFieldName.equals("PayEndDateCalMode") ) {
			return 7;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return 8;
		}
		if( strFieldName.equals("CalCode") ) {
			return 9;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return 10;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 11;
		}
		if( strFieldName.equals("Rate") ) {
			return 12;
		}
		if( strFieldName.equals("MinPay") ) {
			return 13;
		}
		if( strFieldName.equals("AssuYield") ) {
			return 14;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 15;
		}
		if( strFieldName.equals("PayToDateCalMode") ) {
			return 16;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return 17;
		}
		if( strFieldName.equals("PayLackFlag") ) {
			return 18;
		}
		if( strFieldName.equals("PayOverFlag") ) {
			return 19;
		}
		if( strFieldName.equals("PayOverDeal") ) {
			return 20;
		}
		if( strFieldName.equals("AvoidPayFlag") ) {
			return 21;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 22;
		}
		if( strFieldName.equals("PubFlag") ) {
			return 23;
		}
		if( strFieldName.equals("ZeroFlag") ) {
			return 24;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return 25;
		}
		if( strFieldName.equals("PayAimClass") ) {
			return 26;
		}
		if( strFieldName.equals("PayStartYear") ) {
			return 27;
		}
		if( strFieldName.equals("PayStartYearFlag") ) {
			return 28;
		}
		if( strFieldName.equals("PayStartDateCalRef") ) {
			return 29;
		}
		if( strFieldName.equals("PayStartDateCalMode") ) {
			return 30;
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
				strFieldName = "PayPlanCode";
				break;
			case 1:
				strFieldName = "PayPlanName";
				break;
			case 2:
				strFieldName = "Type";
				break;
			case 3:
				strFieldName = "PayIntv";
				break;
			case 4:
				strFieldName = "PayEndYearFlag";
				break;
			case 5:
				strFieldName = "PayEndYear";
				break;
			case 6:
				strFieldName = "PayEndDateCalRef";
				break;
			case 7:
				strFieldName = "PayEndDateCalMode";
				break;
			case 8:
				strFieldName = "DefaultVal";
				break;
			case 9:
				strFieldName = "CalCode";
				break;
			case 10:
				strFieldName = "CnterCalCode";
				break;
			case 11:
				strFieldName = "OthCalCode";
				break;
			case 12:
				strFieldName = "Rate";
				break;
			case 13:
				strFieldName = "MinPay";
				break;
			case 14:
				strFieldName = "AssuYield";
				break;
			case 15:
				strFieldName = "FeeRate";
				break;
			case 16:
				strFieldName = "PayToDateCalMode";
				break;
			case 17:
				strFieldName = "UrgePayFlag";
				break;
			case 18:
				strFieldName = "PayLackFlag";
				break;
			case 19:
				strFieldName = "PayOverFlag";
				break;
			case 20:
				strFieldName = "PayOverDeal";
				break;
			case 21:
				strFieldName = "AvoidPayFlag";
				break;
			case 22:
				strFieldName = "GracePeriod";
				break;
			case 23:
				strFieldName = "PubFlag";
				break;
			case 24:
				strFieldName = "ZeroFlag";
				break;
			case 25:
				strFieldName = "NeedAcc";
				break;
			case 26:
				strFieldName = "PayAimClass";
				break;
			case 27:
				strFieldName = "PayStartYear";
				break;
			case 28:
				strFieldName = "PayStartYearFlag";
				break;
			case 29:
				strFieldName = "PayStartDateCalRef";
				break;
			case 30:
				strFieldName = "PayStartDateCalMode";
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
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MinPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AssuYield") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayToDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLackFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayOverFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayOverDeal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvoidPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZeroFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAimClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayStartYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayStartYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayStartDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayStartDateCalMode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
