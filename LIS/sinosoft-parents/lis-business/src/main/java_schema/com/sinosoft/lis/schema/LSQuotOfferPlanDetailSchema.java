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
import com.sinosoft.lis.db.LSQuotOfferPlanDetailDB;

/*
 * <p>ClassName: LSQuotOfferPlanDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotOfferPlanDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotOfferPlanDetailSchema.class);
	// @Field
	/** 报价单号 */
	private String OfferListNo;
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
	/** 最终值 */
	private String FinalValue;
	/** 变更类型 */
	private String ChangeType;
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

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotOfferPlanDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "OfferListNo";
		pk[1] = "SysPlanCode";
		pk[2] = "PlanCode";
		pk[3] = "RiskCode";
		pk[4] = "DutyCode";

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
		LSQuotOfferPlanDetailSchema cloned = (LSQuotOfferPlanDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getOfferListNo()
	{
		return OfferListNo;
	}
	public void setOfferListNo(String aOfferListNo)
	{
		if(aOfferListNo!=null && aOfferListNo.length()>20)
			throw new IllegalArgumentException("报价单号OfferListNo值"+aOfferListNo+"的长度"+aOfferListNo.length()+"大于最大值20");
		OfferListNo = aOfferListNo;
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
	public String getChangeType()
	{
		return ChangeType;
	}
	public void setChangeType(String aChangeType)
	{
		if(aChangeType!=null && aChangeType.length()>2)
			throw new IllegalArgumentException("变更类型ChangeType值"+aChangeType+"的长度"+aChangeType.length()+"大于最大值2");
		ChangeType = aChangeType;
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
	* 使用另外一个 LSQuotOfferPlanDetailSchema 对象给 Schema 赋值
	* @param: aLSQuotOfferPlanDetailSchema LSQuotOfferPlanDetailSchema
	**/
	public void setSchema(LSQuotOfferPlanDetailSchema aLSQuotOfferPlanDetailSchema)
	{
		this.OfferListNo = aLSQuotOfferPlanDetailSchema.getOfferListNo();
		this.SysPlanCode = aLSQuotOfferPlanDetailSchema.getSysPlanCode();
		this.PlanCode = aLSQuotOfferPlanDetailSchema.getPlanCode();
		this.RiskCode = aLSQuotOfferPlanDetailSchema.getRiskCode();
		this.DutyCode = aLSQuotOfferPlanDetailSchema.getDutyCode();
		this.AmntType = aLSQuotOfferPlanDetailSchema.getAmntType();
		this.FixedAmnt = aLSQuotOfferPlanDetailSchema.getFixedAmnt();
		this.SalaryMult = aLSQuotOfferPlanDetailSchema.getSalaryMult();
		this.MaxAmnt = aLSQuotOfferPlanDetailSchema.getMaxAmnt();
		this.MinAmnt = aLSQuotOfferPlanDetailSchema.getMinAmnt();
		this.ExceptPremType = aLSQuotOfferPlanDetailSchema.getExceptPremType();
		this.ExceptPrem = aLSQuotOfferPlanDetailSchema.getExceptPrem();
		this.StandValue = aLSQuotOfferPlanDetailSchema.getStandValue();
		this.InitPrem = aLSQuotOfferPlanDetailSchema.getInitPrem();
		this.ExceptYield = aLSQuotOfferPlanDetailSchema.getExceptYield();
		this.FinalValue = aLSQuotOfferPlanDetailSchema.getFinalValue();
		this.ChangeType = aLSQuotOfferPlanDetailSchema.getChangeType();
		this.RelaShareFlag = aLSQuotOfferPlanDetailSchema.getRelaShareFlag();
		this.Remark = aLSQuotOfferPlanDetailSchema.getRemark();
		this.MakeOperator = aLSQuotOfferPlanDetailSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotOfferPlanDetailSchema.getMakeDate());
		this.MakeTime = aLSQuotOfferPlanDetailSchema.getMakeTime();
		this.ModifyOperator = aLSQuotOfferPlanDetailSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotOfferPlanDetailSchema.getModifyDate());
		this.ModifyTime = aLSQuotOfferPlanDetailSchema.getModifyTime();
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
			if( rs.getString("OfferListNo") == null )
				this.OfferListNo = null;
			else
				this.OfferListNo = rs.getString("OfferListNo").trim();

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

			if( rs.getString("FinalValue") == null )
				this.FinalValue = null;
			else
				this.FinalValue = rs.getString("FinalValue").trim();

			if( rs.getString("ChangeType") == null )
				this.ChangeType = null;
			else
				this.ChangeType = rs.getString("ChangeType").trim();

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
			logger.debug("数据库中的LSQuotOfferPlanDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotOfferPlanDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotOfferPlanDetailSchema getSchema()
	{
		LSQuotOfferPlanDetailSchema aLSQuotOfferPlanDetailSchema = new LSQuotOfferPlanDetailSchema();
		aLSQuotOfferPlanDetailSchema.setSchema(this);
		return aLSQuotOfferPlanDetailSchema;
	}

	public LSQuotOfferPlanDetailDB getDB()
	{
		LSQuotOfferPlanDetailDB aDBOper = new LSQuotOfferPlanDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotOfferPlanDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OfferListNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(FinalValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChangeType)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotOfferPlanDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OfferListNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AmntType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FixedAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SalaryMult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MaxAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MinAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ExceptPremType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ExceptPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InitPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ExceptYield = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			FinalValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ChangeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RelaShareFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "LSQuotOfferPlanDetailSchema";
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
		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferListNo));
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
		if (FCode.equalsIgnoreCase("FinalValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinalValue));
		}
		if (FCode.equalsIgnoreCase("ChangeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangeType));
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
				strFieldValue = StrTool.GBKToUnicode(OfferListNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AmntType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FixedAmnt);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SalaryMult);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MaxAmnt);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MinAmnt);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ExceptPremType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ExceptPrem);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandValue);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InitPrem);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ExceptYield);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(FinalValue);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ChangeType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RelaShareFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OfferListNo = FValue.trim();
			}
			else
				OfferListNo = null;
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
		if (FCode.equalsIgnoreCase("FinalValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinalValue = FValue.trim();
			}
			else
				FinalValue = null;
		}
		if (FCode.equalsIgnoreCase("ChangeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChangeType = FValue.trim();
			}
			else
				ChangeType = null;
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
		LSQuotOfferPlanDetailSchema other = (LSQuotOfferPlanDetailSchema)otherObject;
		return
			OfferListNo.equals(other.getOfferListNo())
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
			&& FinalValue.equals(other.getFinalValue())
			&& ChangeType.equals(other.getChangeType())
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
		if( strFieldName.equals("OfferListNo") ) {
			return 0;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("AmntType") ) {
			return 5;
		}
		if( strFieldName.equals("FixedAmnt") ) {
			return 6;
		}
		if( strFieldName.equals("SalaryMult") ) {
			return 7;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return 8;
		}
		if( strFieldName.equals("MinAmnt") ) {
			return 9;
		}
		if( strFieldName.equals("ExceptPremType") ) {
			return 10;
		}
		if( strFieldName.equals("ExceptPrem") ) {
			return 11;
		}
		if( strFieldName.equals("StandValue") ) {
			return 12;
		}
		if( strFieldName.equals("InitPrem") ) {
			return 13;
		}
		if( strFieldName.equals("ExceptYield") ) {
			return 14;
		}
		if( strFieldName.equals("FinalValue") ) {
			return 15;
		}
		if( strFieldName.equals("ChangeType") ) {
			return 16;
		}
		if( strFieldName.equals("RelaShareFlag") ) {
			return 17;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "OfferListNo";
				break;
			case 1:
				strFieldName = "SysPlanCode";
				break;
			case 2:
				strFieldName = "PlanCode";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "AmntType";
				break;
			case 6:
				strFieldName = "FixedAmnt";
				break;
			case 7:
				strFieldName = "SalaryMult";
				break;
			case 8:
				strFieldName = "MaxAmnt";
				break;
			case 9:
				strFieldName = "MinAmnt";
				break;
			case 10:
				strFieldName = "ExceptPremType";
				break;
			case 11:
				strFieldName = "ExceptPrem";
				break;
			case 12:
				strFieldName = "StandValue";
				break;
			case 13:
				strFieldName = "InitPrem";
				break;
			case 14:
				strFieldName = "ExceptYield";
				break;
			case 15:
				strFieldName = "FinalValue";
				break;
			case 16:
				strFieldName = "ChangeType";
				break;
			case 17:
				strFieldName = "RelaShareFlag";
				break;
			case 18:
				strFieldName = "Remark";
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
		if( strFieldName.equals("OfferListNo") ) {
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
		if( strFieldName.equals("FinalValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChangeType") ) {
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
