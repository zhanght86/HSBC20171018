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
import com.sinosoft.lis.db.LLUWPremSubDB;

/*
 * <p>ClassName: LLUWPremSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLUWPremSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLUWPremSubSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 批次号 */
	private String BatNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 交费计划类型 */
	private String PayPlanType;
	/** 投保人类型 */
	private String AppntType;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 催缴标记 */
	private String UrgePayFlag;
	/** 是否和账户相关 */
	private String NeedAcc;
	/** 已交费次数 */
	private int PayTimes;
	/** 保费分配比率 */
	private double Rate;
	/** 起交日期 */
	private Date PayStartDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 交至日期 */
	private Date PaytoDate;
	/** 交费间隔 */
	private int PayIntv;
	/** 每期保费 */
	private double StandPrem;
	/** 实际保费 */
	private double Prem;
	/** 累计保费 */
	private double SumPrem;
	/** 额外风险评分 */
	private double SuppRiskScore;
	/** 免交标志 */
	private String FreeFlag;
	/** 免交比率 */
	private double FreeRate;
	/** 免交起期 */
	private Date FreeStartDate;
	/** 免交止期 */
	private Date FreeEndDate;
	/** 状态 */
	private String State;
	/** 管理机构 */
	private String ManageCom;
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
	/** 加费指向标记 */
	private String AddFeeDirect;
	/** 第二被保人加费评点 */
	private double SecInsuAddPoint;
	/** 承保时保单号 */
	private String NBPolNo;
	/** 回退号 */
	private String BackNo;
	/** 加费方式 */
	private String AddFeeType;
	/** 加费开始时间类型 */
	private String AddForm;

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLUWPremSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "ClmNo";
		pk[1] = "BatNo";
		pk[2] = "PolNo";
		pk[3] = "DutyCode";
		pk[4] = "PayPlanCode";

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
		LLUWPremSubSchema cloned = (LLUWPremSubSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getBatNo()
	{
		return BatNo;
	}
	public void setBatNo(String aBatNo)
	{
		BatNo = aBatNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
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
	/**
	* 更据描述取。<p>
	* 0 －－ 一个保费项描述。<p>
	* 01 －－ 首期健康加费<p>
	* 02 －－ 首期职业加费<p>
	* 11 －－ 复效健康加费<p>
	* 12 －－ 复效职业加费
	*/
	public String getPayPlanType()
	{
		return PayPlanType;
	}
	public void setPayPlanType(String aPayPlanType)
	{
		PayPlanType = aPayPlanType;
	}
	/**
	* 1 ---个人投保人<p>
	* 2 ---集体投保人
	*/
	public String getAppntType()
	{
		return AppntType;
	}
	public void setAppntType(String aAppntType)
	{
		AppntType = aAppntType;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
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
	public String getNeedAcc()
	{
		return NeedAcc;
	}
	public void setNeedAcc(String aNeedAcc)
	{
		NeedAcc = aNeedAcc;
	}
	public int getPayTimes()
	{
		return PayTimes;
	}
	public void setPayTimes(int aPayTimes)
	{
		PayTimes = aPayTimes;
	}
	public void setPayTimes(String aPayTimes)
	{
		if (aPayTimes != null && !aPayTimes.equals(""))
		{
			Integer tInteger = new Integer(aPayTimes);
			int i = tInteger.intValue();
			PayTimes = i;
		}
	}

	/**
	* 纪录每次交费中，该保费项缺省占总保费的比率
	*/
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

	public String getPayStartDate()
	{
		if( PayStartDate != null )
			return fDate.getString(PayStartDate);
		else
			return null;
	}
	public void setPayStartDate(Date aPayStartDate)
	{
		PayStartDate = aPayStartDate;
	}
	public void setPayStartDate(String aPayStartDate)
	{
		if (aPayStartDate != null && !aPayStartDate.equals("") )
		{
			PayStartDate = fDate.getDate( aPayStartDate );
		}
		else
			PayStartDate = null;
	}

	public String getPayEndDate()
	{
		if( PayEndDate != null )
			return fDate.getString(PayEndDate);
		else
			return null;
	}
	public void setPayEndDate(Date aPayEndDate)
	{
		PayEndDate = aPayEndDate;
	}
	public void setPayEndDate(String aPayEndDate)
	{
		if (aPayEndDate != null && !aPayEndDate.equals("") )
		{
			PayEndDate = fDate.getDate( aPayEndDate );
		}
		else
			PayEndDate = null;
	}

	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	/**
	* -1 －－ 不定期缴费<p>
	* 0  －－ 趸缴<p>
	* 1  －－ 月缴<p>
	* 3  －－ 季缴<p>
	* 6  －－ 半年缴<p>
	* 12 －－ 年缴<p>
	* 36 －－ 三年缴
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

	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	public double getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	public double getSuppRiskScore()
	{
		return SuppRiskScore;
	}
	public void setSuppRiskScore(double aSuppRiskScore)
	{
		SuppRiskScore = aSuppRiskScore;
	}
	public void setSuppRiskScore(String aSuppRiskScore)
	{
		if (aSuppRiskScore != null && !aSuppRiskScore.equals(""))
		{
			Double tDouble = new Double(aSuppRiskScore);
			double d = tDouble.doubleValue();
			SuppRiskScore = d;
		}
	}

	/**
	* 1---表示免交。<p>
	* 0---不免交.
	*/
	public String getFreeFlag()
	{
		return FreeFlag;
	}
	public void setFreeFlag(String aFreeFlag)
	{
		FreeFlag = aFreeFlag;
	}
	/**
	* 0 －－ 表示全免<p>
	* 1 －－ 表示不免交。
	*/
	public double getFreeRate()
	{
		return FreeRate;
	}
	public void setFreeRate(double aFreeRate)
	{
		FreeRate = aFreeRate;
	}
	public void setFreeRate(String aFreeRate)
	{
		if (aFreeRate != null && !aFreeRate.equals(""))
		{
			Double tDouble = new Double(aFreeRate);
			double d = tDouble.doubleValue();
			FreeRate = d;
		}
	}

	public String getFreeStartDate()
	{
		if( FreeStartDate != null )
			return fDate.getString(FreeStartDate);
		else
			return null;
	}
	public void setFreeStartDate(Date aFreeStartDate)
	{
		FreeStartDate = aFreeStartDate;
	}
	public void setFreeStartDate(String aFreeStartDate)
	{
		if (aFreeStartDate != null && !aFreeStartDate.equals("") )
		{
			FreeStartDate = fDate.getDate( aFreeStartDate );
		}
		else
			FreeStartDate = null;
	}

	public String getFreeEndDate()
	{
		if( FreeEndDate != null )
			return fDate.getString(FreeEndDate);
		else
			return null;
	}
	public void setFreeEndDate(Date aFreeEndDate)
	{
		FreeEndDate = aFreeEndDate;
	}
	public void setFreeEndDate(String aFreeEndDate)
	{
		if (aFreeEndDate != null && !aFreeEndDate.equals("") )
		{
			FreeEndDate = fDate.getDate( aFreeEndDate );
		}
		else
			FreeEndDate = null;
	}

	/**
	* 0:承保时的保费项<p>
	* 1:承保时的加费项<p>
	* 2:本次项目加费项　<p>
	* 3:前几次不通批单下的加费
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	* 00-	投保人<p>
	* 01-	被保人<p>
	* 对于正常的保费项,该字段为空<p>
	* 对于加费保费项记录该保费项是投保人加费还是被保险人加费<p>
	* 在保全退保,更换投保人等项目需要用到
	*/
	public String getAddFeeDirect()
	{
		return AddFeeDirect;
	}
	public void setAddFeeDirect(String aAddFeeDirect)
	{
		AddFeeDirect = aAddFeeDirect;
	}
	public double getSecInsuAddPoint()
	{
		return SecInsuAddPoint;
	}
	public void setSecInsuAddPoint(double aSecInsuAddPoint)
	{
		SecInsuAddPoint = aSecInsuAddPoint;
	}
	public void setSecInsuAddPoint(String aSecInsuAddPoint)
	{
		if (aSecInsuAddPoint != null && !aSecInsuAddPoint.equals(""))
		{
			Double tDouble = new Double(aSecInsuAddPoint);
			double d = tDouble.doubleValue();
			SecInsuAddPoint = d;
		}
	}

	public String getNBPolNo()
	{
		return NBPolNo;
	}
	public void setNBPolNo(String aNBPolNo)
	{
		NBPolNo = aNBPolNo;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		BackNo = aBackNo;
	}
	/**
	* 01-追溯加费<p>
	* 02-下期加费<p>
	* 03-下期加费
	*/
	public String getAddFeeType()
	{
		return AddFeeType;
	}
	public void setAddFeeType(String aAddFeeType)
	{
		AddFeeType = aAddFeeType;
	}
	public String getAddForm()
	{
		return AddForm;
	}
	public void setAddForm(String aAddForm)
	{
		AddForm = aAddForm;
	}

	/**
	* 使用另外一个 LLUWPremSubSchema 对象给 Schema 赋值
	* @param: aLLUWPremSubSchema LLUWPremSubSchema
	**/
	public void setSchema(LLUWPremSubSchema aLLUWPremSubSchema)
	{
		this.ClmNo = aLLUWPremSubSchema.getClmNo();
		this.BatNo = aLLUWPremSubSchema.getBatNo();
		this.GrpContNo = aLLUWPremSubSchema.getGrpContNo();
		this.ContNo = aLLUWPremSubSchema.getContNo();
		this.PolNo = aLLUWPremSubSchema.getPolNo();
		this.DutyCode = aLLUWPremSubSchema.getDutyCode();
		this.PayPlanCode = aLLUWPremSubSchema.getPayPlanCode();
		this.PayPlanType = aLLUWPremSubSchema.getPayPlanType();
		this.AppntType = aLLUWPremSubSchema.getAppntType();
		this.AppntNo = aLLUWPremSubSchema.getAppntNo();
		this.UrgePayFlag = aLLUWPremSubSchema.getUrgePayFlag();
		this.NeedAcc = aLLUWPremSubSchema.getNeedAcc();
		this.PayTimes = aLLUWPremSubSchema.getPayTimes();
		this.Rate = aLLUWPremSubSchema.getRate();
		this.PayStartDate = fDate.getDate( aLLUWPremSubSchema.getPayStartDate());
		this.PayEndDate = fDate.getDate( aLLUWPremSubSchema.getPayEndDate());
		this.PaytoDate = fDate.getDate( aLLUWPremSubSchema.getPaytoDate());
		this.PayIntv = aLLUWPremSubSchema.getPayIntv();
		this.StandPrem = aLLUWPremSubSchema.getStandPrem();
		this.Prem = aLLUWPremSubSchema.getPrem();
		this.SumPrem = aLLUWPremSubSchema.getSumPrem();
		this.SuppRiskScore = aLLUWPremSubSchema.getSuppRiskScore();
		this.FreeFlag = aLLUWPremSubSchema.getFreeFlag();
		this.FreeRate = aLLUWPremSubSchema.getFreeRate();
		this.FreeStartDate = fDate.getDate( aLLUWPremSubSchema.getFreeStartDate());
		this.FreeEndDate = fDate.getDate( aLLUWPremSubSchema.getFreeEndDate());
		this.State = aLLUWPremSubSchema.getState();
		this.ManageCom = aLLUWPremSubSchema.getManageCom();
		this.Operator = aLLUWPremSubSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLUWPremSubSchema.getMakeDate());
		this.MakeTime = aLLUWPremSubSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLUWPremSubSchema.getModifyDate());
		this.ModifyTime = aLLUWPremSubSchema.getModifyTime();
		this.AddFeeDirect = aLLUWPremSubSchema.getAddFeeDirect();
		this.SecInsuAddPoint = aLLUWPremSubSchema.getSecInsuAddPoint();
		this.NBPolNo = aLLUWPremSubSchema.getNBPolNo();
		this.BackNo = aLLUWPremSubSchema.getBackNo();
		this.AddFeeType = aLLUWPremSubSchema.getAddFeeType();
		this.AddForm = aLLUWPremSubSchema.getAddForm();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("BatNo") == null )
				this.BatNo = null;
			else
				this.BatNo = rs.getString("BatNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("PayPlanType") == null )
				this.PayPlanType = null;
			else
				this.PayPlanType = rs.getString("PayPlanType").trim();

			if( rs.getString("AppntType") == null )
				this.AppntType = null;
			else
				this.AppntType = rs.getString("AppntType").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("UrgePayFlag") == null )
				this.UrgePayFlag = null;
			else
				this.UrgePayFlag = rs.getString("UrgePayFlag").trim();

			if( rs.getString("NeedAcc") == null )
				this.NeedAcc = null;
			else
				this.NeedAcc = rs.getString("NeedAcc").trim();

			this.PayTimes = rs.getInt("PayTimes");
			this.Rate = rs.getDouble("Rate");
			this.PayStartDate = rs.getDate("PayStartDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.PayIntv = rs.getInt("PayIntv");
			this.StandPrem = rs.getDouble("StandPrem");
			this.Prem = rs.getDouble("Prem");
			this.SumPrem = rs.getDouble("SumPrem");
			this.SuppRiskScore = rs.getDouble("SuppRiskScore");
			if( rs.getString("FreeFlag") == null )
				this.FreeFlag = null;
			else
				this.FreeFlag = rs.getString("FreeFlag").trim();

			this.FreeRate = rs.getDouble("FreeRate");
			this.FreeStartDate = rs.getDate("FreeStartDate");
			this.FreeEndDate = rs.getDate("FreeEndDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			if( rs.getString("AddFeeDirect") == null )
				this.AddFeeDirect = null;
			else
				this.AddFeeDirect = rs.getString("AddFeeDirect").trim();

			this.SecInsuAddPoint = rs.getDouble("SecInsuAddPoint");
			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			if( rs.getString("AddFeeType") == null )
				this.AddFeeType = null;
			else
				this.AddFeeType = rs.getString("AddFeeType").trim();

			if( rs.getString("AddForm") == null )
				this.AddForm = null;
			else
				this.AddForm = rs.getString("AddForm").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLUWPremSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLUWPremSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLUWPremSubSchema getSchema()
	{
		LLUWPremSubSchema aLLUWPremSubSchema = new LLUWPremSubSchema();
		aLLUWPremSubSchema.setSchema(this);
		return aLLUWPremSubSchema;
	}

	public LLUWPremSubDB getDB()
	{
		LLUWPremSubDB aDBOper = new LLUWPremSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLUWPremSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SuppRiskScore));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FreeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FreeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FreeStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FreeEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeDirect)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SecInsuAddPoint));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddForm));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLUWPremSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayPlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AppntType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UrgePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			NeedAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PayTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			PayStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			SuppRiskScore = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			FreeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			FreeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			FreeStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			FreeEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AddFeeDirect = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			SecInsuAddPoint = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			AddFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			AddForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLUWPremSubSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanType));
		}
		if (FCode.equalsIgnoreCase("AppntType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntType));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgePayFlag));
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedAcc));
		}
		if (FCode.equalsIgnoreCase("PayTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayTimes));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("PayStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayStartDate()));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("SuppRiskScore"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuppRiskScore));
		}
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreeFlag));
		}
		if (FCode.equalsIgnoreCase("FreeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreeRate));
		}
		if (FCode.equalsIgnoreCase("FreeStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFreeStartDate()));
		}
		if (FCode.equalsIgnoreCase("FreeEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFreeEndDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("AddFeeDirect"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeDirect));
		}
		if (FCode.equalsIgnoreCase("SecInsuAddPoint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecInsuAddPoint));
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("AddFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeType));
		}
		if (FCode.equalsIgnoreCase("AddForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddForm));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayPlanType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AppntType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UrgePayFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(NeedAcc);
				break;
			case 12:
				strFieldValue = String.valueOf(PayTimes);
				break;
			case 13:
				strFieldValue = String.valueOf(Rate);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayStartDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 17:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 18:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 19:
				strFieldValue = String.valueOf(Prem);
				break;
			case 20:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 21:
				strFieldValue = String.valueOf(SuppRiskScore);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(FreeFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(FreeRate);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFreeStartDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFreeEndDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AddFeeDirect);
				break;
			case 34:
				strFieldValue = String.valueOf(SecInsuAddPoint);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(AddFeeType);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(AddForm);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatNo = FValue.trim();
			}
			else
				BatNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanType = FValue.trim();
			}
			else
				PayPlanType = null;
		}
		if (FCode.equalsIgnoreCase("AppntType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntType = FValue.trim();
			}
			else
				AppntType = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedAcc = FValue.trim();
			}
			else
				NeedAcc = null;
		}
		if (FCode.equalsIgnoreCase("PayTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayTimes = i;
			}
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
		if (FCode.equalsIgnoreCase("PayStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayStartDate = fDate.getDate( FValue );
			}
			else
				PayStartDate = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayEndDate = fDate.getDate( FValue );
			}
			else
				PayEndDate = null;
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
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
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("SuppRiskScore"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SuppRiskScore = d;
			}
		}
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FreeFlag = FValue.trim();
			}
			else
				FreeFlag = null;
		}
		if (FCode.equalsIgnoreCase("FreeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FreeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FreeStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FreeStartDate = fDate.getDate( FValue );
			}
			else
				FreeStartDate = null;
		}
		if (FCode.equalsIgnoreCase("FreeEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FreeEndDate = fDate.getDate( FValue );
			}
			else
				FreeEndDate = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("AddFeeDirect"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeDirect = FValue.trim();
			}
			else
				AddFeeDirect = null;
		}
		if (FCode.equalsIgnoreCase("SecInsuAddPoint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SecInsuAddPoint = d;
			}
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackNo = FValue.trim();
			}
			else
				BackNo = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeType = FValue.trim();
			}
			else
				AddFeeType = null;
		}
		if (FCode.equalsIgnoreCase("AddForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddForm = FValue.trim();
			}
			else
				AddForm = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLUWPremSubSchema other = (LLUWPremSubSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& BatNo.equals(other.getBatNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& PayPlanType.equals(other.getPayPlanType())
			&& AppntType.equals(other.getAppntType())
			&& AppntNo.equals(other.getAppntNo())
			&& UrgePayFlag.equals(other.getUrgePayFlag())
			&& NeedAcc.equals(other.getNeedAcc())
			&& PayTimes == other.getPayTimes()
			&& Rate == other.getRate()
			&& fDate.getString(PayStartDate).equals(other.getPayStartDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& PayIntv == other.getPayIntv()
			&& StandPrem == other.getStandPrem()
			&& Prem == other.getPrem()
			&& SumPrem == other.getSumPrem()
			&& SuppRiskScore == other.getSuppRiskScore()
			&& FreeFlag.equals(other.getFreeFlag())
			&& FreeRate == other.getFreeRate()
			&& fDate.getString(FreeStartDate).equals(other.getFreeStartDate())
			&& fDate.getString(FreeEndDate).equals(other.getFreeEndDate())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AddFeeDirect.equals(other.getAddFeeDirect())
			&& SecInsuAddPoint == other.getSecInsuAddPoint()
			&& NBPolNo.equals(other.getNBPolNo())
			&& BackNo.equals(other.getBackNo())
			&& AddFeeType.equals(other.getAddFeeType())
			&& AddForm.equals(other.getAddForm());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("BatNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("PolNo") ) {
			return 4;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 6;
		}
		if( strFieldName.equals("PayPlanType") ) {
			return 7;
		}
		if( strFieldName.equals("AppntType") ) {
			return 8;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 9;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return 10;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return 11;
		}
		if( strFieldName.equals("PayTimes") ) {
			return 12;
		}
		if( strFieldName.equals("Rate") ) {
			return 13;
		}
		if( strFieldName.equals("PayStartDate") ) {
			return 14;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 15;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 16;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 17;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 18;
		}
		if( strFieldName.equals("Prem") ) {
			return 19;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 20;
		}
		if( strFieldName.equals("SuppRiskScore") ) {
			return 21;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return 22;
		}
		if( strFieldName.equals("FreeRate") ) {
			return 23;
		}
		if( strFieldName.equals("FreeStartDate") ) {
			return 24;
		}
		if( strFieldName.equals("FreeEndDate") ) {
			return 25;
		}
		if( strFieldName.equals("State") ) {
			return 26;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 27;
		}
		if( strFieldName.equals("Operator") ) {
			return 28;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 29;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 32;
		}
		if( strFieldName.equals("AddFeeDirect") ) {
			return 33;
		}
		if( strFieldName.equals("SecInsuAddPoint") ) {
			return 34;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 35;
		}
		if( strFieldName.equals("BackNo") ) {
			return 36;
		}
		if( strFieldName.equals("AddFeeType") ) {
			return 37;
		}
		if( strFieldName.equals("AddForm") ) {
			return 38;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "BatNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "PolNo";
				break;
			case 5:
				strFieldName = "DutyCode";
				break;
			case 6:
				strFieldName = "PayPlanCode";
				break;
			case 7:
				strFieldName = "PayPlanType";
				break;
			case 8:
				strFieldName = "AppntType";
				break;
			case 9:
				strFieldName = "AppntNo";
				break;
			case 10:
				strFieldName = "UrgePayFlag";
				break;
			case 11:
				strFieldName = "NeedAcc";
				break;
			case 12:
				strFieldName = "PayTimes";
				break;
			case 13:
				strFieldName = "Rate";
				break;
			case 14:
				strFieldName = "PayStartDate";
				break;
			case 15:
				strFieldName = "PayEndDate";
				break;
			case 16:
				strFieldName = "PaytoDate";
				break;
			case 17:
				strFieldName = "PayIntv";
				break;
			case 18:
				strFieldName = "StandPrem";
				break;
			case 19:
				strFieldName = "Prem";
				break;
			case 20:
				strFieldName = "SumPrem";
				break;
			case 21:
				strFieldName = "SuppRiskScore";
				break;
			case 22:
				strFieldName = "FreeFlag";
				break;
			case 23:
				strFieldName = "FreeRate";
				break;
			case 24:
				strFieldName = "FreeStartDate";
				break;
			case 25:
				strFieldName = "FreeEndDate";
				break;
			case 26:
				strFieldName = "State";
				break;
			case 27:
				strFieldName = "ManageCom";
				break;
			case 28:
				strFieldName = "Operator";
				break;
			case 29:
				strFieldName = "MakeDate";
				break;
			case 30:
				strFieldName = "MakeTime";
				break;
			case 31:
				strFieldName = "ModifyDate";
				break;
			case 32:
				strFieldName = "ModifyTime";
				break;
			case 33:
				strFieldName = "AddFeeDirect";
				break;
			case 34:
				strFieldName = "SecInsuAddPoint";
				break;
			case 35:
				strFieldName = "NBPolNo";
				break;
			case 36:
				strFieldName = "BackNo";
				break;
			case 37:
				strFieldName = "AddFeeType";
				break;
			case 38:
				strFieldName = "AddForm";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SuppRiskScore") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FreeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FreeStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FreeEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("AddFeeDirect") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecInsuAddPoint") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddForm") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
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
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
