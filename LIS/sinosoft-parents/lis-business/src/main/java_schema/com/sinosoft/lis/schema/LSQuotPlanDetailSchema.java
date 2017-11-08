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
import com.sinosoft.lis.db.LSQuotPlanDetailDB;

/*
 * <p>ClassName: LSQuotPlanDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotPlanDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotPlanDetailSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 系统方案编码 */
	private String SysPlanCode;
	/** 方案编码 */
	private String PlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 保额类型 */
	private String AmntType;
	/** 固定保额 */
	private String FixedAmnt;
	/** 月薪倍数 */
	private String SalaryMult;
	/** 最高保额 */
	private String MaxAmnt;
	/** 最低保额 */
	private String MinAmnt;
	/** 期望保费类型 */
	private String ExceptPremType;
	/** 期望保费/费率/折扣 */
	private String ExceptPrem;
	/** 参考保费/费率 */
	private String StandValue;
	/** 初始保费 */
	private String InitPrem;
	/** 预期收益率 */
	private String ExceptYield;
	/** 核保值(中支) */
	private String SubUWValue;
	/** 核保值(分公司) */
	private String BranchUWValue;
	/** 核保值(总公司) */
	private String UWValue;
	/** 最终值 */
	private String FinalValue;
	/** 最终保费 */
	private String FinalPrem;
	/** 最终保额 */
	private String FinalAmnt;
	/** 主附共用标识 */
	private String RelaShareFlag;
	/** 备注 */
	private String Remark;
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

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotPlanDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";
		pk[2] = "SysPlanCode";
		pk[3] = "PlanCode";
		pk[4] = "RiskCode";
		pk[5] = "DutyCode";

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
		LSQuotPlanDetailSchema cloned = (LSQuotPlanDetailSchema)super.clone();
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
	public String getAmntType()
	{
		return AmntType;
	}
	public void setAmntType(String aAmntType)
	{
		if(aAmntType!=null && aAmntType.length()>2)
			throw new IllegalArgumentException("保额类型AmntType值"+aAmntType+"的长度"+aAmntType.length()+"大于最大值2");
		AmntType = aAmntType;
	}
	public String getFixedAmnt()
	{
		return FixedAmnt;
	}
	public void setFixedAmnt(String aFixedAmnt)
	{
		if(aFixedAmnt!=null && aFixedAmnt.length()>20)
			throw new IllegalArgumentException("固定保额FixedAmnt值"+aFixedAmnt+"的长度"+aFixedAmnt.length()+"大于最大值20");
		FixedAmnt = aFixedAmnt;
	}
	public String getSalaryMult()
	{
		return SalaryMult;
	}
	public void setSalaryMult(String aSalaryMult)
	{
		if(aSalaryMult!=null && aSalaryMult.length()>20)
			throw new IllegalArgumentException("月薪倍数SalaryMult值"+aSalaryMult+"的长度"+aSalaryMult.length()+"大于最大值20");
		SalaryMult = aSalaryMult;
	}
	public String getMaxAmnt()
	{
		return MaxAmnt;
	}
	public void setMaxAmnt(String aMaxAmnt)
	{
		if(aMaxAmnt!=null && aMaxAmnt.length()>20)
			throw new IllegalArgumentException("最高保额MaxAmnt值"+aMaxAmnt+"的长度"+aMaxAmnt.length()+"大于最大值20");
		MaxAmnt = aMaxAmnt;
	}
	public String getMinAmnt()
	{
		return MinAmnt;
	}
	public void setMinAmnt(String aMinAmnt)
	{
		if(aMinAmnt!=null && aMinAmnt.length()>20)
			throw new IllegalArgumentException("最低保额MinAmnt值"+aMinAmnt+"的长度"+aMinAmnt.length()+"大于最大值20");
		MinAmnt = aMinAmnt;
	}
	public String getExceptPremType()
	{
		return ExceptPremType;
	}
	public void setExceptPremType(String aExceptPremType)
	{
		if(aExceptPremType!=null && aExceptPremType.length()>2)
			throw new IllegalArgumentException("期望保费类型ExceptPremType值"+aExceptPremType+"的长度"+aExceptPremType.length()+"大于最大值2");
		ExceptPremType = aExceptPremType;
	}
	public String getExceptPrem()
	{
		return ExceptPrem;
	}
	public void setExceptPrem(String aExceptPrem)
	{
		if(aExceptPrem!=null && aExceptPrem.length()>20)
			throw new IllegalArgumentException("期望保费/费率/折扣ExceptPrem值"+aExceptPrem+"的长度"+aExceptPrem.length()+"大于最大值20");
		ExceptPrem = aExceptPrem;
	}
	public String getStandValue()
	{
		return StandValue;
	}
	public void setStandValue(String aStandValue)
	{
		if(aStandValue!=null && aStandValue.length()>20)
			throw new IllegalArgumentException("参考保费/费率StandValue值"+aStandValue+"的长度"+aStandValue.length()+"大于最大值20");
		StandValue = aStandValue;
	}
	public String getInitPrem()
	{
		return InitPrem;
	}
	public void setInitPrem(String aInitPrem)
	{
		if(aInitPrem!=null && aInitPrem.length()>20)
			throw new IllegalArgumentException("初始保费InitPrem值"+aInitPrem+"的长度"+aInitPrem.length()+"大于最大值20");
		InitPrem = aInitPrem;
	}
	public String getExceptYield()
	{
		return ExceptYield;
	}
	public void setExceptYield(String aExceptYield)
	{
		if(aExceptYield!=null && aExceptYield.length()>20)
			throw new IllegalArgumentException("预期收益率ExceptYield值"+aExceptYield+"的长度"+aExceptYield.length()+"大于最大值20");
		ExceptYield = aExceptYield;
	}
	public String getSubUWValue()
	{
		return SubUWValue;
	}
	public void setSubUWValue(String aSubUWValue)
	{
		if(aSubUWValue!=null && aSubUWValue.length()>20)
			throw new IllegalArgumentException("核保值(中支)SubUWValue值"+aSubUWValue+"的长度"+aSubUWValue.length()+"大于最大值20");
		SubUWValue = aSubUWValue;
	}
	public String getBranchUWValue()
	{
		return BranchUWValue;
	}
	public void setBranchUWValue(String aBranchUWValue)
	{
		if(aBranchUWValue!=null && aBranchUWValue.length()>20)
			throw new IllegalArgumentException("核保值(分公司)BranchUWValue值"+aBranchUWValue+"的长度"+aBranchUWValue.length()+"大于最大值20");
		BranchUWValue = aBranchUWValue;
	}
	public String getUWValue()
	{
		return UWValue;
	}
	public void setUWValue(String aUWValue)
	{
		if(aUWValue!=null && aUWValue.length()>20)
			throw new IllegalArgumentException("核保值(总公司)UWValue值"+aUWValue+"的长度"+aUWValue.length()+"大于最大值20");
		UWValue = aUWValue;
	}
	public String getFinalValue()
	{
		return FinalValue;
	}
	public void setFinalValue(String aFinalValue)
	{
		if(aFinalValue!=null && aFinalValue.length()>20)
			throw new IllegalArgumentException("最终值FinalValue值"+aFinalValue+"的长度"+aFinalValue.length()+"大于最大值20");
		FinalValue = aFinalValue;
	}
	public String getFinalPrem()
	{
		return FinalPrem;
	}
	public void setFinalPrem(String aFinalPrem)
	{
		if(aFinalPrem!=null && aFinalPrem.length()>20)
			throw new IllegalArgumentException("最终保费FinalPrem值"+aFinalPrem+"的长度"+aFinalPrem.length()+"大于最大值20");
		FinalPrem = aFinalPrem;
	}
	public String getFinalAmnt()
	{
		return FinalAmnt;
	}
	public void setFinalAmnt(String aFinalAmnt)
	{
		if(aFinalAmnt!=null && aFinalAmnt.length()>20)
			throw new IllegalArgumentException("最终保额FinalAmnt值"+aFinalAmnt+"的长度"+aFinalAmnt.length()+"大于最大值20");
		FinalAmnt = aFinalAmnt;
	}
	/**
	* 0-无附属方案;1-有附属方案
	*/
	public String getRelaShareFlag()
	{
		return RelaShareFlag;
	}
	public void setRelaShareFlag(String aRelaShareFlag)
	{
		if(aRelaShareFlag!=null && aRelaShareFlag.length()>4)
			throw new IllegalArgumentException("主附共用标识RelaShareFlag值"+aRelaShareFlag+"的长度"+aRelaShareFlag.length()+"大于最大值4");
		RelaShareFlag = aRelaShareFlag;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>3000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值3000");
		Remark = aRemark;
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
	* 使用另外一个 LSQuotPlanDetailSchema 对象给 Schema 赋值
	* @param: aLSQuotPlanDetailSchema LSQuotPlanDetailSchema
	**/
	public void setSchema(LSQuotPlanDetailSchema aLSQuotPlanDetailSchema)
	{
		this.QuotNo = aLSQuotPlanDetailSchema.getQuotNo();
		this.QuotBatNo = aLSQuotPlanDetailSchema.getQuotBatNo();
		this.SysPlanCode = aLSQuotPlanDetailSchema.getSysPlanCode();
		this.PlanCode = aLSQuotPlanDetailSchema.getPlanCode();
		this.RiskCode = aLSQuotPlanDetailSchema.getRiskCode();
		this.DutyCode = aLSQuotPlanDetailSchema.getDutyCode();
		this.AmntType = aLSQuotPlanDetailSchema.getAmntType();
		this.FixedAmnt = aLSQuotPlanDetailSchema.getFixedAmnt();
		this.SalaryMult = aLSQuotPlanDetailSchema.getSalaryMult();
		this.MaxAmnt = aLSQuotPlanDetailSchema.getMaxAmnt();
		this.MinAmnt = aLSQuotPlanDetailSchema.getMinAmnt();
		this.ExceptPremType = aLSQuotPlanDetailSchema.getExceptPremType();
		this.ExceptPrem = aLSQuotPlanDetailSchema.getExceptPrem();
		this.StandValue = aLSQuotPlanDetailSchema.getStandValue();
		this.InitPrem = aLSQuotPlanDetailSchema.getInitPrem();
		this.ExceptYield = aLSQuotPlanDetailSchema.getExceptYield();
		this.SubUWValue = aLSQuotPlanDetailSchema.getSubUWValue();
		this.BranchUWValue = aLSQuotPlanDetailSchema.getBranchUWValue();
		this.UWValue = aLSQuotPlanDetailSchema.getUWValue();
		this.FinalValue = aLSQuotPlanDetailSchema.getFinalValue();
		this.FinalPrem = aLSQuotPlanDetailSchema.getFinalPrem();
		this.FinalAmnt = aLSQuotPlanDetailSchema.getFinalAmnt();
		this.RelaShareFlag = aLSQuotPlanDetailSchema.getRelaShareFlag();
		this.Remark = aLSQuotPlanDetailSchema.getRemark();
		this.MakeOperator = aLSQuotPlanDetailSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotPlanDetailSchema.getMakeDate());
		this.MakeTime = aLSQuotPlanDetailSchema.getMakeTime();
		this.ModifyOperator = aLSQuotPlanDetailSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotPlanDetailSchema.getModifyDate());
		this.ModifyTime = aLSQuotPlanDetailSchema.getModifyTime();
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

			if( rs.getString("AmntType") == null )
				this.AmntType = null;
			else
				this.AmntType = rs.getString("AmntType").trim();

			if( rs.getString("FixedAmnt") == null )
				this.FixedAmnt = null;
			else
				this.FixedAmnt = rs.getString("FixedAmnt").trim();

			if( rs.getString("SalaryMult") == null )
				this.SalaryMult = null;
			else
				this.SalaryMult = rs.getString("SalaryMult").trim();

			if( rs.getString("MaxAmnt") == null )
				this.MaxAmnt = null;
			else
				this.MaxAmnt = rs.getString("MaxAmnt").trim();

			if( rs.getString("MinAmnt") == null )
				this.MinAmnt = null;
			else
				this.MinAmnt = rs.getString("MinAmnt").trim();

			if( rs.getString("ExceptPremType") == null )
				this.ExceptPremType = null;
			else
				this.ExceptPremType = rs.getString("ExceptPremType").trim();

			if( rs.getString("ExceptPrem") == null )
				this.ExceptPrem = null;
			else
				this.ExceptPrem = rs.getString("ExceptPrem").trim();

			if( rs.getString("StandValue") == null )
				this.StandValue = null;
			else
				this.StandValue = rs.getString("StandValue").trim();

			if( rs.getString("InitPrem") == null )
				this.InitPrem = null;
			else
				this.InitPrem = rs.getString("InitPrem").trim();

			if( rs.getString("ExceptYield") == null )
				this.ExceptYield = null;
			else
				this.ExceptYield = rs.getString("ExceptYield").trim();

			if( rs.getString("SubUWValue") == null )
				this.SubUWValue = null;
			else
				this.SubUWValue = rs.getString("SubUWValue").trim();

			if( rs.getString("BranchUWValue") == null )
				this.BranchUWValue = null;
			else
				this.BranchUWValue = rs.getString("BranchUWValue").trim();

			if( rs.getString("UWValue") == null )
				this.UWValue = null;
			else
				this.UWValue = rs.getString("UWValue").trim();

			if( rs.getString("FinalValue") == null )
				this.FinalValue = null;
			else
				this.FinalValue = rs.getString("FinalValue").trim();

			if( rs.getString("FinalPrem") == null )
				this.FinalPrem = null;
			else
				this.FinalPrem = rs.getString("FinalPrem").trim();

			if( rs.getString("FinalAmnt") == null )
				this.FinalAmnt = null;
			else
				this.FinalAmnt = rs.getString("FinalAmnt").trim();

			if( rs.getString("RelaShareFlag") == null )
				this.RelaShareFlag = null;
			else
				this.RelaShareFlag = rs.getString("RelaShareFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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
			logger.debug("数据库中的LSQuotPlanDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotPlanDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotPlanDetailSchema getSchema()
	{
		LSQuotPlanDetailSchema aLSQuotPlanDetailSchema = new LSQuotPlanDetailSchema();
		aLSQuotPlanDetailSchema.setSchema(this);
		return aLSQuotPlanDetailSchema;
	}

	public LSQuotPlanDetailDB getDB()
	{
		LSQuotPlanDetailDB aDBOper = new LSQuotPlanDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotPlanDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AmntType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FixedAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SalaryMult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MaxAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MinAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExceptPremType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExceptPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InitPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExceptYield)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubUWValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUWValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinalValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinalPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinalAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaShareFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotPlanDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AmntType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FixedAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SalaryMult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MaxAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MinAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ExceptPremType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ExceptPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InitPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ExceptYield = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SubUWValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BranchUWValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			UWValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			FinalValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			FinalPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			FinalAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			RelaShareFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotPlanDetailSchema";
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
		if (FCode.equalsIgnoreCase("AmntType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntType));
		}
		if (FCode.equalsIgnoreCase("FixedAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FixedAmnt));
		}
		if (FCode.equalsIgnoreCase("SalaryMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SalaryMult));
		}
		if (FCode.equalsIgnoreCase("MaxAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAmnt));
		}
		if (FCode.equalsIgnoreCase("MinAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinAmnt));
		}
		if (FCode.equalsIgnoreCase("ExceptPremType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExceptPremType));
		}
		if (FCode.equalsIgnoreCase("ExceptPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExceptPrem));
		}
		if (FCode.equalsIgnoreCase("StandValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandValue));
		}
		if (FCode.equalsIgnoreCase("InitPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitPrem));
		}
		if (FCode.equalsIgnoreCase("ExceptYield"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExceptYield));
		}
		if (FCode.equalsIgnoreCase("SubUWValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubUWValue));
		}
		if (FCode.equalsIgnoreCase("BranchUWValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUWValue));
		}
		if (FCode.equalsIgnoreCase("UWValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWValue));
		}
		if (FCode.equalsIgnoreCase("FinalValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinalValue));
		}
		if (FCode.equalsIgnoreCase("FinalPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinalPrem));
		}
		if (FCode.equalsIgnoreCase("FinalAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinalAmnt));
		}
		if (FCode.equalsIgnoreCase("RelaShareFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaShareFlag));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AmntType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FixedAmnt);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SalaryMult);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MaxAmnt);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MinAmnt);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ExceptPremType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ExceptPrem);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandValue);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InitPrem);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ExceptYield);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SubUWValue);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BranchUWValue);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(UWValue);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(FinalValue);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(FinalPrem);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(FinalAmnt);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(RelaShareFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 29:
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
		if (FCode.equalsIgnoreCase("AmntType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmntType = FValue.trim();
			}
			else
				AmntType = null;
		}
		if (FCode.equalsIgnoreCase("FixedAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FixedAmnt = FValue.trim();
			}
			else
				FixedAmnt = null;
		}
		if (FCode.equalsIgnoreCase("SalaryMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SalaryMult = FValue.trim();
			}
			else
				SalaryMult = null;
		}
		if (FCode.equalsIgnoreCase("MaxAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MaxAmnt = FValue.trim();
			}
			else
				MaxAmnt = null;
		}
		if (FCode.equalsIgnoreCase("MinAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MinAmnt = FValue.trim();
			}
			else
				MinAmnt = null;
		}
		if (FCode.equalsIgnoreCase("ExceptPremType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExceptPremType = FValue.trim();
			}
			else
				ExceptPremType = null;
		}
		if (FCode.equalsIgnoreCase("ExceptPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExceptPrem = FValue.trim();
			}
			else
				ExceptPrem = null;
		}
		if (FCode.equalsIgnoreCase("StandValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandValue = FValue.trim();
			}
			else
				StandValue = null;
		}
		if (FCode.equalsIgnoreCase("InitPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitPrem = FValue.trim();
			}
			else
				InitPrem = null;
		}
		if (FCode.equalsIgnoreCase("ExceptYield"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExceptYield = FValue.trim();
			}
			else
				ExceptYield = null;
		}
		if (FCode.equalsIgnoreCase("SubUWValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubUWValue = FValue.trim();
			}
			else
				SubUWValue = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUWValue = FValue.trim();
			}
			else
				BranchUWValue = null;
		}
		if (FCode.equalsIgnoreCase("UWValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWValue = FValue.trim();
			}
			else
				UWValue = null;
		}
		if (FCode.equalsIgnoreCase("FinalValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinalValue = FValue.trim();
			}
			else
				FinalValue = null;
		}
		if (FCode.equalsIgnoreCase("FinalPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinalPrem = FValue.trim();
			}
			else
				FinalPrem = null;
		}
		if (FCode.equalsIgnoreCase("FinalAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinalAmnt = FValue.trim();
			}
			else
				FinalAmnt = null;
		}
		if (FCode.equalsIgnoreCase("RelaShareFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaShareFlag = FValue.trim();
			}
			else
				RelaShareFlag = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		LSQuotPlanDetailSchema other = (LSQuotPlanDetailSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& AmntType.equals(other.getAmntType())
			&& FixedAmnt.equals(other.getFixedAmnt())
			&& SalaryMult.equals(other.getSalaryMult())
			&& MaxAmnt.equals(other.getMaxAmnt())
			&& MinAmnt.equals(other.getMinAmnt())
			&& ExceptPremType.equals(other.getExceptPremType())
			&& ExceptPrem.equals(other.getExceptPrem())
			&& StandValue.equals(other.getStandValue())
			&& InitPrem.equals(other.getInitPrem())
			&& ExceptYield.equals(other.getExceptYield())
			&& SubUWValue.equals(other.getSubUWValue())
			&& BranchUWValue.equals(other.getBranchUWValue())
			&& UWValue.equals(other.getUWValue())
			&& FinalValue.equals(other.getFinalValue())
			&& FinalPrem.equals(other.getFinalPrem())
			&& FinalAmnt.equals(other.getFinalAmnt())
			&& RelaShareFlag.equals(other.getRelaShareFlag())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("SysPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 3;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 4;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("AmntType") ) {
			return 6;
		}
		if( strFieldName.equals("FixedAmnt") ) {
			return 7;
		}
		if( strFieldName.equals("SalaryMult") ) {
			return 8;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return 9;
		}
		if( strFieldName.equals("MinAmnt") ) {
			return 10;
		}
		if( strFieldName.equals("ExceptPremType") ) {
			return 11;
		}
		if( strFieldName.equals("ExceptPrem") ) {
			return 12;
		}
		if( strFieldName.equals("StandValue") ) {
			return 13;
		}
		if( strFieldName.equals("InitPrem") ) {
			return 14;
		}
		if( strFieldName.equals("ExceptYield") ) {
			return 15;
		}
		if( strFieldName.equals("SubUWValue") ) {
			return 16;
		}
		if( strFieldName.equals("BranchUWValue") ) {
			return 17;
		}
		if( strFieldName.equals("UWValue") ) {
			return 18;
		}
		if( strFieldName.equals("FinalValue") ) {
			return 19;
		}
		if( strFieldName.equals("FinalPrem") ) {
			return 20;
		}
		if( strFieldName.equals("FinalAmnt") ) {
			return 21;
		}
		if( strFieldName.equals("RelaShareFlag") ) {
			return 22;
		}
		if( strFieldName.equals("Remark") ) {
			return 23;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 29;
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
				strFieldName = "SysPlanCode";
				break;
			case 3:
				strFieldName = "PlanCode";
				break;
			case 4:
				strFieldName = "RiskCode";
				break;
			case 5:
				strFieldName = "DutyCode";
				break;
			case 6:
				strFieldName = "AmntType";
				break;
			case 7:
				strFieldName = "FixedAmnt";
				break;
			case 8:
				strFieldName = "SalaryMult";
				break;
			case 9:
				strFieldName = "MaxAmnt";
				break;
			case 10:
				strFieldName = "MinAmnt";
				break;
			case 11:
				strFieldName = "ExceptPremType";
				break;
			case 12:
				strFieldName = "ExceptPrem";
				break;
			case 13:
				strFieldName = "StandValue";
				break;
			case 14:
				strFieldName = "InitPrem";
				break;
			case 15:
				strFieldName = "ExceptYield";
				break;
			case 16:
				strFieldName = "SubUWValue";
				break;
			case 17:
				strFieldName = "BranchUWValue";
				break;
			case 18:
				strFieldName = "UWValue";
				break;
			case 19:
				strFieldName = "FinalValue";
				break;
			case 20:
				strFieldName = "FinalPrem";
				break;
			case 21:
				strFieldName = "FinalAmnt";
				break;
			case 22:
				strFieldName = "RelaShareFlag";
				break;
			case 23:
				strFieldName = "Remark";
				break;
			case 24:
				strFieldName = "MakeOperator";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
				strFieldName = "MakeTime";
				break;
			case 27:
				strFieldName = "ModifyOperator";
				break;
			case 28:
				strFieldName = "ModifyDate";
				break;
			case 29:
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
		if( strFieldName.equals("AmntType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FixedAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SalaryMult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExceptPremType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExceptPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExceptYield") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubUWValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUWValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinalValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinalPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinalAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaShareFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
