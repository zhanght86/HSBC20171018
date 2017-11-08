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
import com.sinosoft.lis.db.LQGetDB;

/*
 * <p>ClassName: LQGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LQGetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LQGetSchema.class);
	// @Field
	/** 报价单号 */
	private String AskPriceNo;
	/** 报价号 */
	private String AskNo;
	/** 投保等级编号 */
	private String ClassNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 领取方式 */
	private String GetMode;
	/** 起付限 */
	private double GetLimit;
	/** 赔付比例 */
	private double GetRate;
	/** 催付标记 */
	private String UrgeGetFlag;
	/** 生存意外给付标志 */
	private String LiveGetType;
	/** 递增率 */
	private double AddRate;
	/** 默认申请标志 */
	private String CanGet;
	/** 是否和账户相关 */
	private String NeedAcc;
	/** 是否是账户结清后才能申请 */
	private String NeedCancelAcc;
	/** 标准给付金额 */
	private double StandMoney;
	/** 实际给付金额 */
	private double ActuGet;
	/** 已领金额 */
	private double SumMoney;
	/** 领取间隔 */
	private int GetIntv;
	/** 领至日期 */
	private Date GettoDate;
	/** 起领日期 */
	private Date GetStartDate;
	/** 止领日期 */
	private Date GetEndDate;
	/** 结算日期 */
	private Date BalaDate;
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
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 止领标志 */
	private String GetEndState;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 33;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LQGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "AskPriceNo";
		pk[1] = "AskNo";
		pk[2] = "ClassNo";
		pk[3] = "DutyCode";
		pk[4] = "GetDutyCode";

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
		LQGetSchema cloned = (LQGetSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAskPriceNo()
	{
		return AskPriceNo;
	}
	public void setAskPriceNo(String aAskPriceNo)
	{
		if(aAskPriceNo!=null && aAskPriceNo.length()>20)
			throw new IllegalArgumentException("报价单号AskPriceNo值"+aAskPriceNo+"的长度"+aAskPriceNo.length()+"大于最大值20");
		AskPriceNo = aAskPriceNo;
	}
	public String getAskNo()
	{
		return AskNo;
	}
	public void setAskNo(String aAskNo)
	{
		if(aAskNo!=null && aAskNo.length()>50)
			throw new IllegalArgumentException("报价号AskNo值"+aAskNo+"的长度"+aAskNo.length()+"大于最大值50");
		AskNo = aAskNo;
	}
	public String getClassNo()
	{
		return ClassNo;
	}
	public void setClassNo(String aClassNo)
	{
		if(aClassNo!=null && aClassNo.length()>50)
			throw new IllegalArgumentException("投保等级编号ClassNo值"+aClassNo+"的长度"+aClassNo.length()+"大于最大值50");
		ClassNo = aClassNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>10)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值10");
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>6)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值6");
		GetDutyCode = aGetDutyCode;
	}
	/**
	* 对应kind_pay_li 中一种领取方式<p>
	* 如有十年固定年金，<p>
	* 无十年固定年金，<p>
	* 总共领5次或领十次等
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>6)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值6");
		GetDutyKind = aGetDutyKind;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号码InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	/**
	* 1--领取现金<p>
	* 2--抵缴保费<p>
	* 3--购买缴清增额保险<p>
	* 4--累积生息<p>
	* 9--其他
	*/
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		if(aGetMode!=null && aGetMode.length()>1)
			throw new IllegalArgumentException("领取方式GetMode值"+aGetMode+"的长度"+aGetMode.length()+"大于最大值1");
		GetMode = aGetMode;
	}
	public double getGetLimit()
	{
		return GetLimit;
	}
	public void setGetLimit(double aGetLimit)
	{
		GetLimit = aGetLimit;
	}
	public void setGetLimit(String aGetLimit)
	{
		if (aGetLimit != null && !aGetLimit.equals(""))
		{
			Double tDouble = new Double(aGetLimit);
			double d = tDouble.doubleValue();
			GetLimit = d;
		}
	}

	public double getGetRate()
	{
		return GetRate;
	}
	public void setGetRate(double aGetRate)
	{
		GetRate = aGetRate;
	}
	public void setGetRate(String aGetRate)
	{
		if (aGetRate != null && !aGetRate.equals(""))
		{
			Double tDouble = new Double(aGetRate);
			double d = tDouble.doubleValue();
			GetRate = d;
		}
	}

	/**
	* Y--发催付、N--不发催付
	*/
	public String getUrgeGetFlag()
	{
		return UrgeGetFlag;
	}
	public void setUrgeGetFlag(String aUrgeGetFlag)
	{
		if(aUrgeGetFlag!=null && aUrgeGetFlag.length()>1)
			throw new IllegalArgumentException("催付标记UrgeGetFlag值"+aUrgeGetFlag+"的长度"+aUrgeGetFlag.length()+"大于最大值1");
		UrgeGetFlag = aUrgeGetFlag;
	}
	/**
	* 0 -- 生存领取<p>
	* 1 -- 意外给付
	*/
	public String getLiveGetType()
	{
		return LiveGetType;
	}
	public void setLiveGetType(String aLiveGetType)
	{
		if(aLiveGetType!=null && aLiveGetType.length()>1)
			throw new IllegalArgumentException("生存意外给付标志LiveGetType值"+aLiveGetType+"的长度"+aLiveGetType.length()+"大于最大值1");
		LiveGetType = aLiveGetType;
	}
	public double getAddRate()
	{
		return AddRate;
	}
	public void setAddRate(double aAddRate)
	{
		AddRate = aAddRate;
	}
	public void setAddRate(String aAddRate)
	{
		if (aAddRate != null && !aAddRate.equals(""))
		{
			Double tDouble = new Double(aAddRate);
			double d = tDouble.doubleValue();
			AddRate = d;
		}
	}

	/**
	* 0 －－ 不用申请就可以领取，为该字段默认值<p>
	* 1 －－ 需要申请后才可领。
	*/
	public String getCanGet()
	{
		return CanGet;
	}
	public void setCanGet(String aCanGet)
	{
		if(aCanGet!=null && aCanGet.length()>1)
			throw new IllegalArgumentException("默认申请标志CanGet值"+aCanGet+"的长度"+aCanGet.length()+"大于最大值1");
		CanGet = aCanGet;
	}
	public String getNeedAcc()
	{
		return NeedAcc;
	}
	public void setNeedAcc(String aNeedAcc)
	{
		if(aNeedAcc!=null && aNeedAcc.length()>1)
			throw new IllegalArgumentException("是否和账户相关NeedAcc值"+aNeedAcc+"的长度"+aNeedAcc.length()+"大于最大值1");
		NeedAcc = aNeedAcc;
	}
	/**
	* 0 －－ 不需要账户结清<p>
	* 1 －－ 必须要账户结清后才能进行领取。
	*/
	public String getNeedCancelAcc()
	{
		return NeedCancelAcc;
	}
	public void setNeedCancelAcc(String aNeedCancelAcc)
	{
		if(aNeedCancelAcc!=null && aNeedCancelAcc.length()>1)
			throw new IllegalArgumentException("是否是账户结清后才能申请NeedCancelAcc值"+aNeedCancelAcc+"的长度"+aNeedCancelAcc.length()+"大于最大值1");
		NeedCancelAcc = aNeedCancelAcc;
	}
	public double getStandMoney()
	{
		return StandMoney;
	}
	public void setStandMoney(double aStandMoney)
	{
		StandMoney = aStandMoney;
	}
	public void setStandMoney(String aStandMoney)
	{
		if (aStandMoney != null && !aStandMoney.equals(""))
		{
			Double tDouble = new Double(aStandMoney);
			double d = tDouble.doubleValue();
			StandMoney = d;
		}
	}

	public double getActuGet()
	{
		return ActuGet;
	}
	public void setActuGet(double aActuGet)
	{
		ActuGet = aActuGet;
	}
	public void setActuGet(String aActuGet)
	{
		if (aActuGet != null && !aActuGet.equals(""))
		{
			Double tDouble = new Double(aActuGet);
			double d = tDouble.doubleValue();
			ActuGet = d;
		}
	}

	public double getSumMoney()
	{
		return SumMoney;
	}
	public void setSumMoney(double aSumMoney)
	{
		SumMoney = aSumMoney;
	}
	public void setSumMoney(String aSumMoney)
	{
		if (aSumMoney != null && !aSumMoney.equals(""))
		{
			Double tDouble = new Double(aSumMoney);
			double d = tDouble.doubleValue();
			SumMoney = d;
		}
	}

	/**
	* 0 -- 趸领<p>
	* 1 －－ 月领<p>
	* 3 －－ 季领<p>
	* 12－－ 年龄<p>
	* 36－－ 每3年领
	*/
	public int getGetIntv()
	{
		return GetIntv;
	}
	public void setGetIntv(int aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	public void setGetIntv(String aGetIntv)
	{
		if (aGetIntv != null && !aGetIntv.equals(""))
		{
			Integer tInteger = new Integer(aGetIntv);
			int i = tInteger.intValue();
			GetIntv = i;
		}
	}

	public String getGettoDate()
	{
		if( GettoDate != null )
			return fDate.getString(GettoDate);
		else
			return null;
	}
	public void setGettoDate(Date aGettoDate)
	{
		GettoDate = aGettoDate;
	}
	public void setGettoDate(String aGettoDate)
	{
		if (aGettoDate != null && !aGettoDate.equals("") )
		{
			GettoDate = fDate.getDate( aGettoDate );
		}
		else
			GettoDate = null;
	}

	public String getGetStartDate()
	{
		if( GetStartDate != null )
			return fDate.getString(GetStartDate);
		else
			return null;
	}
	public void setGetStartDate(Date aGetStartDate)
	{
		GetStartDate = aGetStartDate;
	}
	public void setGetStartDate(String aGetStartDate)
	{
		if (aGetStartDate != null && !aGetStartDate.equals("") )
		{
			GetStartDate = fDate.getDate( aGetStartDate );
		}
		else
			GetStartDate = null;
	}

	public String getGetEndDate()
	{
		if( GetEndDate != null )
			return fDate.getString(GetEndDate);
		else
			return null;
	}
	public void setGetEndDate(Date aGetEndDate)
	{
		GetEndDate = aGetEndDate;
	}
	public void setGetEndDate(String aGetEndDate)
	{
		if (aGetEndDate != null && !aGetEndDate.equals("") )
		{
			GetEndDate = fDate.getDate( aGetEndDate );
		}
		else
			GetEndDate = null;
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

	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>10)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值10");
		State = aState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
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

	/**
	* 0: 领取未终止<p>
	* 1: 领取终止
	*/
	public String getGetEndState()
	{
		return GetEndState;
	}
	public void setGetEndState(String aGetEndState)
	{
		if(aGetEndState!=null && aGetEndState.length()>6)
			throw new IllegalArgumentException("止领标志GetEndState值"+aGetEndState+"的长度"+aGetEndState.length()+"大于最大值6");
		GetEndState = aGetEndState;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}

	/**
	* 使用另外一个 LQGetSchema 对象给 Schema 赋值
	* @param: aLQGetSchema LQGetSchema
	**/
	public void setSchema(LQGetSchema aLQGetSchema)
	{
		this.AskPriceNo = aLQGetSchema.getAskPriceNo();
		this.AskNo = aLQGetSchema.getAskNo();
		this.ClassNo = aLQGetSchema.getClassNo();
		this.DutyCode = aLQGetSchema.getDutyCode();
		this.GetDutyCode = aLQGetSchema.getGetDutyCode();
		this.GetDutyKind = aLQGetSchema.getGetDutyKind();
		this.InsuredNo = aLQGetSchema.getInsuredNo();
		this.GetMode = aLQGetSchema.getGetMode();
		this.GetLimit = aLQGetSchema.getGetLimit();
		this.GetRate = aLQGetSchema.getGetRate();
		this.UrgeGetFlag = aLQGetSchema.getUrgeGetFlag();
		this.LiveGetType = aLQGetSchema.getLiveGetType();
		this.AddRate = aLQGetSchema.getAddRate();
		this.CanGet = aLQGetSchema.getCanGet();
		this.NeedAcc = aLQGetSchema.getNeedAcc();
		this.NeedCancelAcc = aLQGetSchema.getNeedCancelAcc();
		this.StandMoney = aLQGetSchema.getStandMoney();
		this.ActuGet = aLQGetSchema.getActuGet();
		this.SumMoney = aLQGetSchema.getSumMoney();
		this.GetIntv = aLQGetSchema.getGetIntv();
		this.GettoDate = fDate.getDate( aLQGetSchema.getGettoDate());
		this.GetStartDate = fDate.getDate( aLQGetSchema.getGetStartDate());
		this.GetEndDate = fDate.getDate( aLQGetSchema.getGetEndDate());
		this.BalaDate = fDate.getDate( aLQGetSchema.getBalaDate());
		this.State = aLQGetSchema.getState();
		this.ManageCom = aLQGetSchema.getManageCom();
		this.Operator = aLQGetSchema.getOperator();
		this.MakeDate = fDate.getDate( aLQGetSchema.getMakeDate());
		this.MakeTime = aLQGetSchema.getMakeTime();
		this.ModifyTime = aLQGetSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aLQGetSchema.getModifyDate());
		this.GetEndState = aLQGetSchema.getGetEndState();
		this.Currency = aLQGetSchema.getCurrency();
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
			if( rs.getString("AskPriceNo") == null )
				this.AskPriceNo = null;
			else
				this.AskPriceNo = rs.getString("AskPriceNo").trim();

			if( rs.getString("AskNo") == null )
				this.AskNo = null;
			else
				this.AskNo = rs.getString("AskNo").trim();

			if( rs.getString("ClassNo") == null )
				this.ClassNo = null;
			else
				this.ClassNo = rs.getString("ClassNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			this.GetLimit = rs.getDouble("GetLimit");
			this.GetRate = rs.getDouble("GetRate");
			if( rs.getString("UrgeGetFlag") == null )
				this.UrgeGetFlag = null;
			else
				this.UrgeGetFlag = rs.getString("UrgeGetFlag").trim();

			if( rs.getString("LiveGetType") == null )
				this.LiveGetType = null;
			else
				this.LiveGetType = rs.getString("LiveGetType").trim();

			this.AddRate = rs.getDouble("AddRate");
			if( rs.getString("CanGet") == null )
				this.CanGet = null;
			else
				this.CanGet = rs.getString("CanGet").trim();

			if( rs.getString("NeedAcc") == null )
				this.NeedAcc = null;
			else
				this.NeedAcc = rs.getString("NeedAcc").trim();

			if( rs.getString("NeedCancelAcc") == null )
				this.NeedCancelAcc = null;
			else
				this.NeedCancelAcc = rs.getString("NeedCancelAcc").trim();

			this.StandMoney = rs.getDouble("StandMoney");
			this.ActuGet = rs.getDouble("ActuGet");
			this.SumMoney = rs.getDouble("SumMoney");
			this.GetIntv = rs.getInt("GetIntv");
			this.GettoDate = rs.getDate("GettoDate");
			this.GetStartDate = rs.getDate("GetStartDate");
			this.GetEndDate = rs.getDate("GetEndDate");
			this.BalaDate = rs.getDate("BalaDate");
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

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("GetEndState") == null )
				this.GetEndState = null;
			else
				this.GetEndState = rs.getString("GetEndState").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LQGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LQGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LQGetSchema getSchema()
	{
		LQGetSchema aLQGetSchema = new LQGetSchema();
		aLQGetSchema.setSchema(this);
		return aLQGetSchema;
	}

	public LQGetDB getDB()
	{
		LQGetDB aDBOper = new LQGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AskPriceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AskNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClassNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgeGetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CanGet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedCancelAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ActuGet));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GettoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalaDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetEndState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AskPriceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClassNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			UrgeGetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			LiveGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AddRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			CanGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			NeedAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			NeedCancelAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			ActuGet = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			GettoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			GetEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			BalaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			GetEndState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LQGetSchema";
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
		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskPriceNo));
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskNo));
		}
		if (FCode.equalsIgnoreCase("ClassNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClassNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("UrgeGetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgeGetFlag));
		}
		if (FCode.equalsIgnoreCase("LiveGetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveGetType));
		}
		if (FCode.equalsIgnoreCase("AddRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddRate));
		}
		if (FCode.equalsIgnoreCase("CanGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CanGet));
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedAcc));
		}
		if (FCode.equalsIgnoreCase("NeedCancelAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedCancelAcc));
		}
		if (FCode.equalsIgnoreCase("StandMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandMoney));
		}
		if (FCode.equalsIgnoreCase("ActuGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActuGet));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("GettoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGettoDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("GetEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetEndDate()));
		}
		if (FCode.equalsIgnoreCase("BalaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("GetEndState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetEndState));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(AskPriceNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AskNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClassNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 8:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 9:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UrgeGetFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(LiveGetType);
				break;
			case 12:
				strFieldValue = String.valueOf(AddRate);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CanGet);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(NeedAcc);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(NeedCancelAcc);
				break;
			case 16:
				strFieldValue = String.valueOf(StandMoney);
				break;
			case 17:
				strFieldValue = String.valueOf(ActuGet);
				break;
			case 18:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 19:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGettoDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetEndDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(GetEndState);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Currency);
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

		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskPriceNo = FValue.trim();
			}
			else
				AskPriceNo = null;
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskNo = FValue.trim();
			}
			else
				AskNo = null;
		}
		if (FCode.equalsIgnoreCase("ClassNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClassNo = FValue.trim();
			}
			else
				ClassNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetMode = FValue.trim();
			}
			else
				GetMode = null;
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("UrgeGetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgeGetFlag = FValue.trim();
			}
			else
				UrgeGetFlag = null;
		}
		if (FCode.equalsIgnoreCase("LiveGetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveGetType = FValue.trim();
			}
			else
				LiveGetType = null;
		}
		if (FCode.equalsIgnoreCase("AddRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CanGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CanGet = FValue.trim();
			}
			else
				CanGet = null;
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
		if (FCode.equalsIgnoreCase("NeedCancelAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedCancelAcc = FValue.trim();
			}
			else
				NeedCancelAcc = null;
		}
		if (FCode.equalsIgnoreCase("StandMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ActuGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ActuGet = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("GettoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GettoDate = fDate.getDate( FValue );
			}
			else
				GettoDate = null;
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetStartDate = fDate.getDate( FValue );
			}
			else
				GetStartDate = null;
		}
		if (FCode.equalsIgnoreCase("GetEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetEndDate = fDate.getDate( FValue );
			}
			else
				GetEndDate = null;
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		if (FCode.equalsIgnoreCase("GetEndState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetEndState = FValue.trim();
			}
			else
				GetEndState = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LQGetSchema other = (LQGetSchema)otherObject;
		return
			AskPriceNo.equals(other.getAskPriceNo())
			&& AskNo.equals(other.getAskNo())
			&& ClassNo.equals(other.getClassNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& InsuredNo.equals(other.getInsuredNo())
			&& GetMode.equals(other.getGetMode())
			&& GetLimit == other.getGetLimit()
			&& GetRate == other.getGetRate()
			&& UrgeGetFlag.equals(other.getUrgeGetFlag())
			&& LiveGetType.equals(other.getLiveGetType())
			&& AddRate == other.getAddRate()
			&& CanGet.equals(other.getCanGet())
			&& NeedAcc.equals(other.getNeedAcc())
			&& NeedCancelAcc.equals(other.getNeedCancelAcc())
			&& StandMoney == other.getStandMoney()
			&& ActuGet == other.getActuGet()
			&& SumMoney == other.getSumMoney()
			&& GetIntv == other.getGetIntv()
			&& fDate.getString(GettoDate).equals(other.getGettoDate())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& fDate.getString(GetEndDate).equals(other.getGetEndDate())
			&& fDate.getString(BalaDate).equals(other.getBalaDate())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& GetEndState.equals(other.getGetEndState())
			&& Currency.equals(other.getCurrency());
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
		if( strFieldName.equals("AskPriceNo") ) {
			return 0;
		}
		if( strFieldName.equals("AskNo") ) {
			return 1;
		}
		if( strFieldName.equals("ClassNo") ) {
			return 2;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 5;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 6;
		}
		if( strFieldName.equals("GetMode") ) {
			return 7;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 8;
		}
		if( strFieldName.equals("GetRate") ) {
			return 9;
		}
		if( strFieldName.equals("UrgeGetFlag") ) {
			return 10;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return 11;
		}
		if( strFieldName.equals("AddRate") ) {
			return 12;
		}
		if( strFieldName.equals("CanGet") ) {
			return 13;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return 14;
		}
		if( strFieldName.equals("NeedCancelAcc") ) {
			return 15;
		}
		if( strFieldName.equals("StandMoney") ) {
			return 16;
		}
		if( strFieldName.equals("ActuGet") ) {
			return 17;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 18;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 19;
		}
		if( strFieldName.equals("GettoDate") ) {
			return 20;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 21;
		}
		if( strFieldName.equals("GetEndDate") ) {
			return 22;
		}
		if( strFieldName.equals("BalaDate") ) {
			return 23;
		}
		if( strFieldName.equals("State") ) {
			return 24;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 25;
		}
		if( strFieldName.equals("Operator") ) {
			return 26;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 27;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("GetEndState") ) {
			return 31;
		}
		if( strFieldName.equals("Currency") ) {
			return 32;
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
				strFieldName = "AskPriceNo";
				break;
			case 1:
				strFieldName = "AskNo";
				break;
			case 2:
				strFieldName = "ClassNo";
				break;
			case 3:
				strFieldName = "DutyCode";
				break;
			case 4:
				strFieldName = "GetDutyCode";
				break;
			case 5:
				strFieldName = "GetDutyKind";
				break;
			case 6:
				strFieldName = "InsuredNo";
				break;
			case 7:
				strFieldName = "GetMode";
				break;
			case 8:
				strFieldName = "GetLimit";
				break;
			case 9:
				strFieldName = "GetRate";
				break;
			case 10:
				strFieldName = "UrgeGetFlag";
				break;
			case 11:
				strFieldName = "LiveGetType";
				break;
			case 12:
				strFieldName = "AddRate";
				break;
			case 13:
				strFieldName = "CanGet";
				break;
			case 14:
				strFieldName = "NeedAcc";
				break;
			case 15:
				strFieldName = "NeedCancelAcc";
				break;
			case 16:
				strFieldName = "StandMoney";
				break;
			case 17:
				strFieldName = "ActuGet";
				break;
			case 18:
				strFieldName = "SumMoney";
				break;
			case 19:
				strFieldName = "GetIntv";
				break;
			case 20:
				strFieldName = "GettoDate";
				break;
			case 21:
				strFieldName = "GetStartDate";
				break;
			case 22:
				strFieldName = "GetEndDate";
				break;
			case 23:
				strFieldName = "BalaDate";
				break;
			case 24:
				strFieldName = "State";
				break;
			case 25:
				strFieldName = "ManageCom";
				break;
			case 26:
				strFieldName = "Operator";
				break;
			case 27:
				strFieldName = "MakeDate";
				break;
			case 28:
				strFieldName = "MakeTime";
				break;
			case 29:
				strFieldName = "ModifyTime";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
				strFieldName = "GetEndState";
				break;
			case 32:
				strFieldName = "Currency";
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
		if( strFieldName.equals("AskPriceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClassNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UrgeGetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CanGet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedCancelAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ActuGet") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GettoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalaDate") ) {
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
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetEndState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
