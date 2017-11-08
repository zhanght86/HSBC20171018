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
import com.sinosoft.lis.db.LPInsuredDrawDetailDB;

/*
 * <p>ClassName: LPInsuredDrawDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPInsuredDrawDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPInsuredDrawDetailSchema.class);
	// @Field
	/** 保全批单号 */
	private String EdorNo;
	/** 保全项目 */
	private String EdorType;
	/** 团体保单号 */
	private String GrpContNo;
	/** 团单险种号 */
	private String GrpPolNo;
	/** 个人保单号 */
	private String ContNo;
	/** 投保人客户号 */
	private String AppntNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 个人险种号 */
	private String PolNo;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 年金金额 */
	private double SumMoney;
	/** 领取方式 */
	private String GetMode;
	/** 领取间隔 */
	private int GetIntv;
	/** 起领日期 */
	private Date GetStartDate;
	/** 领至日期 */
	private Date GetToDate;
	/** 止领日期 */
	private Date GetEndDate;
	/** 生存领取次数 */
	private int GetTimes;
	/** 每次领取金额 */
	private double GetMoney;
	/** 下一次生调日期 */
	private Date NextLiveDate;
	/** 下一次领取日期 */
	private Date NextGetDate;
	/** 已领金额 */
	private double AlreadyMoney;
	/** 止领状态 */
	private String GetEndState;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
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

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPInsuredDrawDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "PolNo";
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
		LPInsuredDrawDetailSchema cloned = (LPInsuredDrawDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("保全批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>10)
			throw new IllegalArgumentException("保全项目EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值10");
		EdorType = aEdorType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("团单险种号GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个人保单号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>24)
			throw new IllegalArgumentException("投保人客户号AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值24");
		AppntNo = aAppntNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("个人险种号PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
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
		if(aGetDutyCode!=null && aGetDutyCode.length()>10)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值10");
		GetDutyCode = aGetDutyCode;
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

	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		if(aGetMode!=null && aGetMode.length()>10)
			throw new IllegalArgumentException("领取方式GetMode值"+aGetMode+"的长度"+aGetMode.length()+"大于最大值10");
		GetMode = aGetMode;
	}
	/**
	* 以月为单位
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

	public String getGetToDate()
	{
		if( GetToDate != null )
			return fDate.getString(GetToDate);
		else
			return null;
	}
	public void setGetToDate(Date aGetToDate)
	{
		GetToDate = aGetToDate;
	}
	public void setGetToDate(String aGetToDate)
	{
		if (aGetToDate != null && !aGetToDate.equals("") )
		{
			GetToDate = fDate.getDate( aGetToDate );
		}
		else
			GetToDate = null;
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

	public int getGetTimes()
	{
		return GetTimes;
	}
	public void setGetTimes(int aGetTimes)
	{
		GetTimes = aGetTimes;
	}
	public void setGetTimes(String aGetTimes)
	{
		if (aGetTimes != null && !aGetTimes.equals(""))
		{
			Integer tInteger = new Integer(aGetTimes);
			int i = tInteger.intValue();
			GetTimes = i;
		}
	}

	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	public String getNextLiveDate()
	{
		if( NextLiveDate != null )
			return fDate.getString(NextLiveDate);
		else
			return null;
	}
	public void setNextLiveDate(Date aNextLiveDate)
	{
		NextLiveDate = aNextLiveDate;
	}
	public void setNextLiveDate(String aNextLiveDate)
	{
		if (aNextLiveDate != null && !aNextLiveDate.equals("") )
		{
			NextLiveDate = fDate.getDate( aNextLiveDate );
		}
		else
			NextLiveDate = null;
	}

	public String getNextGetDate()
	{
		if( NextGetDate != null )
			return fDate.getString(NextGetDate);
		else
			return null;
	}
	public void setNextGetDate(Date aNextGetDate)
	{
		NextGetDate = aNextGetDate;
	}
	public void setNextGetDate(String aNextGetDate)
	{
		if (aNextGetDate != null && !aNextGetDate.equals("") )
		{
			NextGetDate = fDate.getDate( aNextGetDate );
		}
		else
			NextGetDate = null;
	}

	public double getAlreadyMoney()
	{
		return AlreadyMoney;
	}
	public void setAlreadyMoney(double aAlreadyMoney)
	{
		AlreadyMoney = aAlreadyMoney;
	}
	public void setAlreadyMoney(String aAlreadyMoney)
	{
		if (aAlreadyMoney != null && !aAlreadyMoney.equals(""))
		{
			Double tDouble = new Double(aAlreadyMoney);
			double d = tDouble.doubleValue();
			AlreadyMoney = d;
		}
	}

	/**
	* 0:账户有效 1:账户注销
	*/
	public String getGetEndState()
	{
		return GetEndState;
	}
	public void setGetEndState(String aGetEndState)
	{
		if(aGetEndState!=null && aGetEndState.length()>2)
			throw new IllegalArgumentException("止领状态GetEndState值"+aGetEndState+"的长度"+aGetEndState.length()+"大于最大值2");
		GetEndState = aGetEndState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
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
	* 使用另外一个 LPInsuredDrawDetailSchema 对象给 Schema 赋值
	* @param: aLPInsuredDrawDetailSchema LPInsuredDrawDetailSchema
	**/
	public void setSchema(LPInsuredDrawDetailSchema aLPInsuredDrawDetailSchema)
	{
		this.EdorNo = aLPInsuredDrawDetailSchema.getEdorNo();
		this.EdorType = aLPInsuredDrawDetailSchema.getEdorType();
		this.GrpContNo = aLPInsuredDrawDetailSchema.getGrpContNo();
		this.GrpPolNo = aLPInsuredDrawDetailSchema.getGrpPolNo();
		this.ContNo = aLPInsuredDrawDetailSchema.getContNo();
		this.AppntNo = aLPInsuredDrawDetailSchema.getAppntNo();
		this.InsuredNo = aLPInsuredDrawDetailSchema.getInsuredNo();
		this.PolNo = aLPInsuredDrawDetailSchema.getPolNo();
		this.RiskCode = aLPInsuredDrawDetailSchema.getRiskCode();
		this.DutyCode = aLPInsuredDrawDetailSchema.getDutyCode();
		this.GetDutyCode = aLPInsuredDrawDetailSchema.getGetDutyCode();
		this.SumMoney = aLPInsuredDrawDetailSchema.getSumMoney();
		this.GetMode = aLPInsuredDrawDetailSchema.getGetMode();
		this.GetIntv = aLPInsuredDrawDetailSchema.getGetIntv();
		this.GetStartDate = fDate.getDate( aLPInsuredDrawDetailSchema.getGetStartDate());
		this.GetToDate = fDate.getDate( aLPInsuredDrawDetailSchema.getGetToDate());
		this.GetEndDate = fDate.getDate( aLPInsuredDrawDetailSchema.getGetEndDate());
		this.GetTimes = aLPInsuredDrawDetailSchema.getGetTimes();
		this.GetMoney = aLPInsuredDrawDetailSchema.getGetMoney();
		this.NextLiveDate = fDate.getDate( aLPInsuredDrawDetailSchema.getNextLiveDate());
		this.NextGetDate = fDate.getDate( aLPInsuredDrawDetailSchema.getNextGetDate());
		this.AlreadyMoney = aLPInsuredDrawDetailSchema.getAlreadyMoney();
		this.GetEndState = aLPInsuredDrawDetailSchema.getGetEndState();
		this.ManageCom = aLPInsuredDrawDetailSchema.getManageCom();
		this.ComCode = aLPInsuredDrawDetailSchema.getComCode();
		this.MakeOperator = aLPInsuredDrawDetailSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPInsuredDrawDetailSchema.getMakeDate());
		this.MakeTime = aLPInsuredDrawDetailSchema.getMakeTime();
		this.ModifyOperator = aLPInsuredDrawDetailSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPInsuredDrawDetailSchema.getModifyDate());
		this.ModifyTime = aLPInsuredDrawDetailSchema.getModifyTime();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			this.SumMoney = rs.getDouble("SumMoney");
			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			this.GetIntv = rs.getInt("GetIntv");
			this.GetStartDate = rs.getDate("GetStartDate");
			this.GetToDate = rs.getDate("GetToDate");
			this.GetEndDate = rs.getDate("GetEndDate");
			this.GetTimes = rs.getInt("GetTimes");
			this.GetMoney = rs.getDouble("GetMoney");
			this.NextLiveDate = rs.getDate("NextLiveDate");
			this.NextGetDate = rs.getDate("NextGetDate");
			this.AlreadyMoney = rs.getDouble("AlreadyMoney");
			if( rs.getString("GetEndState") == null )
				this.GetEndState = null;
			else
				this.GetEndState = rs.getString("GetEndState").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

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
			logger.debug("数据库中的LPInsuredDrawDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDrawDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPInsuredDrawDetailSchema getSchema()
	{
		LPInsuredDrawDetailSchema aLPInsuredDrawDetailSchema = new LPInsuredDrawDetailSchema();
		aLPInsuredDrawDetailSchema.setSchema(this);
		return aLPInsuredDrawDetailSchema;
	}

	public LPInsuredDrawDetailDB getDB()
	{
		LPInsuredDrawDetailDB aDBOper = new LPInsuredDrawDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsuredDrawDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( NextLiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( NextGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AlreadyMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetEndState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsuredDrawDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			GetToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			GetEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			GetTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			NextLiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			NextGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			AlreadyMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			GetEndState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDrawDetailSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("GetToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetToDate()));
		}
		if (FCode.equalsIgnoreCase("GetEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetEndDate()));
		}
		if (FCode.equalsIgnoreCase("GetTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetTimes));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("NextLiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getNextLiveDate()));
		}
		if (FCode.equalsIgnoreCase("NextGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getNextGetDate()));
		}
		if (FCode.equalsIgnoreCase("AlreadyMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AlreadyMoney));
		}
		if (FCode.equalsIgnoreCase("GetEndState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetEndState));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 11:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 13:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetToDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetEndDate()));
				break;
			case 17:
				strFieldValue = String.valueOf(GetTimes);
				break;
			case 18:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getNextLiveDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getNextGetDate()));
				break;
			case 21:
				strFieldValue = String.valueOf(AlreadyMoney);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(GetEndState);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 30:
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
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
		if (FCode.equalsIgnoreCase("GetToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetToDate = fDate.getDate( FValue );
			}
			else
				GetToDate = null;
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
		if (FCode.equalsIgnoreCase("GetTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("NextLiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				NextLiveDate = fDate.getDate( FValue );
			}
			else
				NextLiveDate = null;
		}
		if (FCode.equalsIgnoreCase("NextGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				NextGetDate = fDate.getDate( FValue );
			}
			else
				NextGetDate = null;
		}
		if (FCode.equalsIgnoreCase("AlreadyMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AlreadyMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		LPInsuredDrawDetailSchema other = (LPInsuredDrawDetailSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& AppntNo.equals(other.getAppntNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& PolNo.equals(other.getPolNo())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& SumMoney == other.getSumMoney()
			&& GetMode.equals(other.getGetMode())
			&& GetIntv == other.getGetIntv()
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& fDate.getString(GetToDate).equals(other.getGetToDate())
			&& fDate.getString(GetEndDate).equals(other.getGetEndDate())
			&& GetTimes == other.getGetTimes()
			&& GetMoney == other.getGetMoney()
			&& fDate.getString(NextLiveDate).equals(other.getNextLiveDate())
			&& fDate.getString(NextGetDate).equals(other.getNextGetDate())
			&& AlreadyMoney == other.getAlreadyMoney()
			&& GetEndState.equals(other.getGetEndState())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
			return 4;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 5;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 6;
		}
		if( strFieldName.equals("PolNo") ) {
			return 7;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 8;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 9;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 10;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 11;
		}
		if( strFieldName.equals("GetMode") ) {
			return 12;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 13;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 14;
		}
		if( strFieldName.equals("GetToDate") ) {
			return 15;
		}
		if( strFieldName.equals("GetEndDate") ) {
			return 16;
		}
		if( strFieldName.equals("GetTimes") ) {
			return 17;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 18;
		}
		if( strFieldName.equals("NextLiveDate") ) {
			return 19;
		}
		if( strFieldName.equals("NextGetDate") ) {
			return 20;
		}
		if( strFieldName.equals("AlreadyMoney") ) {
			return 21;
		}
		if( strFieldName.equals("GetEndState") ) {
			return 22;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 23;
		}
		if( strFieldName.equals("ComCode") ) {
			return 24;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "GrpPolNo";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "AppntNo";
				break;
			case 6:
				strFieldName = "InsuredNo";
				break;
			case 7:
				strFieldName = "PolNo";
				break;
			case 8:
				strFieldName = "RiskCode";
				break;
			case 9:
				strFieldName = "DutyCode";
				break;
			case 10:
				strFieldName = "GetDutyCode";
				break;
			case 11:
				strFieldName = "SumMoney";
				break;
			case 12:
				strFieldName = "GetMode";
				break;
			case 13:
				strFieldName = "GetIntv";
				break;
			case 14:
				strFieldName = "GetStartDate";
				break;
			case 15:
				strFieldName = "GetToDate";
				break;
			case 16:
				strFieldName = "GetEndDate";
				break;
			case 17:
				strFieldName = "GetTimes";
				break;
			case 18:
				strFieldName = "GetMoney";
				break;
			case 19:
				strFieldName = "NextLiveDate";
				break;
			case 20:
				strFieldName = "NextGetDate";
				break;
			case 21:
				strFieldName = "AlreadyMoney";
				break;
			case 22:
				strFieldName = "GetEndState";
				break;
			case 23:
				strFieldName = "ManageCom";
				break;
			case 24:
				strFieldName = "ComCode";
				break;
			case 25:
				strFieldName = "MakeOperator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "ModifyOperator";
				break;
			case 29:
				strFieldName = "ModifyDate";
				break;
			case 30:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NextLiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("NextGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AlreadyMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetEndState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
