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
import com.sinosoft.lis.db.LPContPlanPerInvestDB;

/*
 * <p>ClassName: LPContPlanPerInvestSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPContPlanPerInvestSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPContPlanPerInvestSchema.class);
	// @Field
	/** 保全批单号 */
	private String EdorNo;
	/** 保全项目 */
	private String EdorType;
	/** 保单号 */
	private String PolicyNo;
	/** 系统方案编码 */
	private String SysPlanCode;
	/** 方案编码 */
	private String PlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 缴费项编码 */
	private String PayPlanCode;
	/** 保险账户编码 */
	private String InsuAccNo;
	/** 投资比例下限 */
	private double InvestMinRate;
	/** 投资比例上限 */
	private double InvestMaxRate;
	/** 投资比例 */
	private double InvestRate;
	/** 录入投资金额 */
	private double InvestMoney;
	/** 实际投资金额 */
	private double RealInvestMoney;
	/** 备用字段1 */
	private String Other1;
	/** 备用字段2 */
	private String Other2;
	/** 备用字段3 */
	private String Other3;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPContPlanPerInvestSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[9];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "PolicyNo";
		pk[3] = "SysPlanCode";
		pk[4] = "PlanCode";
		pk[5] = "RiskCode";
		pk[6] = "DutyCode";
		pk[7] = "PayPlanCode";
		pk[8] = "InsuAccNo";

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
		LPContPlanPerInvestSchema cloned = (LPContPlanPerInvestSchema)super.clone();
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
	public String getPolicyNo()
	{
		return PolicyNo;
	}
	public void setPolicyNo(String aPolicyNo)
	{
		if(aPolicyNo!=null && aPolicyNo.length()>20)
			throw new IllegalArgumentException("保单号PolicyNo值"+aPolicyNo+"的长度"+aPolicyNo.length()+"大于最大值20");
		PolicyNo = aPolicyNo;
	}
	public String getSysPlanCode()
	{
		return SysPlanCode;
	}
	public void setSysPlanCode(String aSysPlanCode)
	{
		if(aSysPlanCode!=null && aSysPlanCode.length()>10)
			throw new IllegalArgumentException("系统方案编码SysPlanCode值"+aSysPlanCode+"的长度"+aSysPlanCode.length()+"大于最大值10");
		SysPlanCode = aSysPlanCode;
	}
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>10)
			throw new IllegalArgumentException("方案编码PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值10");
		PlanCode = aPlanCode;
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
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>20)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值20");
		DutyCode = aDutyCode;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		if(aPayPlanCode!=null && aPayPlanCode.length()>20)
			throw new IllegalArgumentException("缴费项编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值20");
		PayPlanCode = aPayPlanCode;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		if(aInsuAccNo!=null && aInsuAccNo.length()>20)
			throw new IllegalArgumentException("保险账户编码InsuAccNo值"+aInsuAccNo+"的长度"+aInsuAccNo.length()+"大于最大值20");
		InsuAccNo = aInsuAccNo;
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

	/**
	* 客户录入的投资金额
	*/
	public double getInvestMoney()
	{
		return InvestMoney;
	}
	public void setInvestMoney(double aInvestMoney)
	{
		InvestMoney = aInvestMoney;
	}
	public void setInvestMoney(String aInvestMoney)
	{
		if (aInvestMoney != null && !aInvestMoney.equals(""))
		{
			Double tDouble = new Double(aInvestMoney);
			double d = tDouble.doubleValue();
			InvestMoney = d;
		}
	}

	/**
	* 启用PFIS Profiles后得到的实际投资金额
	*/
	public double getRealInvestMoney()
	{
		return RealInvestMoney;
	}
	public void setRealInvestMoney(double aRealInvestMoney)
	{
		RealInvestMoney = aRealInvestMoney;
	}
	public void setRealInvestMoney(String aRealInvestMoney)
	{
		if (aRealInvestMoney != null && !aRealInvestMoney.equals(""))
		{
			Double tDouble = new Double(aRealInvestMoney);
			double d = tDouble.doubleValue();
			RealInvestMoney = d;
		}
	}

	public String getOther1()
	{
		return Other1;
	}
	public void setOther1(String aOther1)
	{
		if(aOther1!=null && aOther1.length()>20)
			throw new IllegalArgumentException("备用字段1Other1值"+aOther1+"的长度"+aOther1.length()+"大于最大值20");
		Other1 = aOther1;
	}
	public String getOther2()
	{
		return Other2;
	}
	public void setOther2(String aOther2)
	{
		if(aOther2!=null && aOther2.length()>200)
			throw new IllegalArgumentException("备用字段2Other2值"+aOther2+"的长度"+aOther2.length()+"大于最大值200");
		Other2 = aOther2;
	}
	public String getOther3()
	{
		return Other3;
	}
	public void setOther3(String aOther3)
	{
		if(aOther3!=null && aOther3.length()>1000)
			throw new IllegalArgumentException("备用字段3Other3值"+aOther3+"的长度"+aOther3.length()+"大于最大值1000");
		Other3 = aOther3;
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
			throw new IllegalArgumentException("最后一次操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
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
	* 使用另外一个 LPContPlanPerInvestSchema 对象给 Schema 赋值
	* @param: aLPContPlanPerInvestSchema LPContPlanPerInvestSchema
	**/
	public void setSchema(LPContPlanPerInvestSchema aLPContPlanPerInvestSchema)
	{
		this.EdorNo = aLPContPlanPerInvestSchema.getEdorNo();
		this.EdorType = aLPContPlanPerInvestSchema.getEdorType();
		this.PolicyNo = aLPContPlanPerInvestSchema.getPolicyNo();
		this.SysPlanCode = aLPContPlanPerInvestSchema.getSysPlanCode();
		this.PlanCode = aLPContPlanPerInvestSchema.getPlanCode();
		this.RiskCode = aLPContPlanPerInvestSchema.getRiskCode();
		this.DutyCode = aLPContPlanPerInvestSchema.getDutyCode();
		this.PayPlanCode = aLPContPlanPerInvestSchema.getPayPlanCode();
		this.InsuAccNo = aLPContPlanPerInvestSchema.getInsuAccNo();
		this.InvestMinRate = aLPContPlanPerInvestSchema.getInvestMinRate();
		this.InvestMaxRate = aLPContPlanPerInvestSchema.getInvestMaxRate();
		this.InvestRate = aLPContPlanPerInvestSchema.getInvestRate();
		this.InvestMoney = aLPContPlanPerInvestSchema.getInvestMoney();
		this.RealInvestMoney = aLPContPlanPerInvestSchema.getRealInvestMoney();
		this.Other1 = aLPContPlanPerInvestSchema.getOther1();
		this.Other2 = aLPContPlanPerInvestSchema.getOther2();
		this.Other3 = aLPContPlanPerInvestSchema.getOther3();
		this.MakeOperator = aLPContPlanPerInvestSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPContPlanPerInvestSchema.getMakeDate());
		this.MakeTime = aLPContPlanPerInvestSchema.getMakeTime();
		this.ModifyOperator = aLPContPlanPerInvestSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPContPlanPerInvestSchema.getModifyDate());
		this.ModifyTime = aLPContPlanPerInvestSchema.getModifyTime();
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

			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("SysPlanCode") == null )
				this.SysPlanCode = null;
			else
				this.SysPlanCode = rs.getString("SysPlanCode").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			this.InvestMinRate = rs.getDouble("InvestMinRate");
			this.InvestMaxRate = rs.getDouble("InvestMaxRate");
			this.InvestRate = rs.getDouble("InvestRate");
			this.InvestMoney = rs.getDouble("InvestMoney");
			this.RealInvestMoney = rs.getDouble("RealInvestMoney");
			if( rs.getString("Other1") == null )
				this.Other1 = null;
			else
				this.Other1 = rs.getString("Other1").trim();

			if( rs.getString("Other2") == null )
				this.Other2 = null;
			else
				this.Other2 = rs.getString("Other2").trim();

			if( rs.getString("Other3") == null )
				this.Other3 = null;
			else
				this.Other3 = rs.getString("Other3").trim();

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
			logger.debug("数据库中的LPContPlanPerInvest表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanPerInvestSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPContPlanPerInvestSchema getSchema()
	{
		LPContPlanPerInvestSchema aLPContPlanPerInvestSchema = new LPContPlanPerInvestSchema();
		aLPContPlanPerInvestSchema.setSchema(this);
		return aLPContPlanPerInvestSchema;
	}

	public LPContPlanPerInvestDB getDB()
	{
		LPContPlanPerInvestDB aDBOper = new LPContPlanPerInvestDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPContPlanPerInvest描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMinRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMaxRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealInvestMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Other1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Other2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Other3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPContPlanPerInvest>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InvestMinRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMaxRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			InvestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			RealInvestMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Other1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Other2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Other3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanPerInvestSchema";
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
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMinRate));
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMaxRate));
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRate));
		}
		if (FCode.equalsIgnoreCase("InvestMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMoney));
		}
		if (FCode.equalsIgnoreCase("RealInvestMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealInvestMoney));
		}
		if (FCode.equalsIgnoreCase("Other1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Other1));
		}
		if (FCode.equalsIgnoreCase("Other2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Other2));
		}
		if (FCode.equalsIgnoreCase("Other3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Other3));
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
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 9:
				strFieldValue = String.valueOf(InvestMinRate);
				break;
			case 10:
				strFieldValue = String.valueOf(InvestMaxRate);
				break;
			case 11:
				strFieldValue = String.valueOf(InvestRate);
				break;
			case 12:
				strFieldValue = String.valueOf(InvestMoney);
				break;
			case 13:
				strFieldValue = String.valueOf(RealInvestMoney);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Other1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Other2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Other3);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
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
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNo = FValue.trim();
			}
			else
				PolicyNo = null;
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysPlanCode = FValue.trim();
			}
			else
				SysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMinRate = d;
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
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealInvestMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealInvestMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("Other1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Other1 = FValue.trim();
			}
			else
				Other1 = null;
		}
		if (FCode.equalsIgnoreCase("Other2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Other2 = FValue.trim();
			}
			else
				Other2 = null;
		}
		if (FCode.equalsIgnoreCase("Other3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Other3 = FValue.trim();
			}
			else
				Other3 = null;
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
		LPContPlanPerInvestSchema other = (LPContPlanPerInvestSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& PolicyNo.equals(other.getPolicyNo())
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& InvestMinRate == other.getInvestMinRate()
			&& InvestMaxRate == other.getInvestMaxRate()
			&& InvestRate == other.getInvestRate()
			&& InvestMoney == other.getInvestMoney()
			&& RealInvestMoney == other.getRealInvestMoney()
			&& Other1.equals(other.getOther1())
			&& Other2.equals(other.getOther2())
			&& Other3.equals(other.getOther3())
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
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 3;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 6;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 7;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 8;
		}
		if( strFieldName.equals("InvestMinRate") ) {
			return 9;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return 10;
		}
		if( strFieldName.equals("InvestRate") ) {
			return 11;
		}
		if( strFieldName.equals("InvestMoney") ) {
			return 12;
		}
		if( strFieldName.equals("RealInvestMoney") ) {
			return 13;
		}
		if( strFieldName.equals("Other1") ) {
			return 14;
		}
		if( strFieldName.equals("Other2") ) {
			return 15;
		}
		if( strFieldName.equals("Other3") ) {
			return 16;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
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
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "SysPlanCode";
				break;
			case 4:
				strFieldName = "PlanCode";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "DutyCode";
				break;
			case 7:
				strFieldName = "PayPlanCode";
				break;
			case 8:
				strFieldName = "InsuAccNo";
				break;
			case 9:
				strFieldName = "InvestMinRate";
				break;
			case 10:
				strFieldName = "InvestMaxRate";
				break;
			case 11:
				strFieldName = "InvestRate";
				break;
			case 12:
				strFieldName = "InvestMoney";
				break;
			case 13:
				strFieldName = "RealInvestMoney";
				break;
			case 14:
				strFieldName = "Other1";
				break;
			case 15:
				strFieldName = "Other2";
				break;
			case 16:
				strFieldName = "Other3";
				break;
			case 17:
				strFieldName = "MakeOperator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyOperator";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
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
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestMinRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealInvestMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Other1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Other2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Other3") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
