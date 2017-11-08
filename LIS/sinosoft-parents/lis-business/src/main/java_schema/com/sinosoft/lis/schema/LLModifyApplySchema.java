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
import com.sinosoft.lis.db.LLModifyApplyDB;

/*
 * <p>ClassName: LLModifyApplySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LLModifyApplySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLModifyApplySchema.class);
	// @Field
	/** 申请编码 */
	private String ApplyNo;
	/** 申请批次号 */
	private String ApplyBatchNo;
	/** 申请机构 */
	private String ApplyCom;
	/** 保项修改原因编码 */
	private String ReasonNo;
	/** 规则类型 */
	private String RuleType;
	/** 险种编码 */
	private String RiskCode;
	/** 调整方向 */
	private String AdjustDirection;
	/** 向上调整规则 */
	private String UpAdjustRule;
	/** 向上调整比例 */
	private double UpAdjustRate;
	/** 生效起期 */
	private Date StartDate;
	/** 生效止期 */
	private Date EndDate;
	/** 备注 */
	private String Remark;
	/** 状态 */
	private String ApplyState;
	/** 申请日期 */
	private Date ApplyDate;
	/** 申请操作员 */
	private String ApplyOperator;
	/** 复核操作员 */
	private String CheckOperator;
	/** 复核结论 */
	private String CheckConclusion;
	/** 复核意见 */
	private String CheckIdea;
	/** 审批操作员 */
	private String ApproveOperator;
	/** 审批结论 */
	private String ApproveConclusion;
	/** 审批意见 */
	private String ApproveIdea;
	/** 操作机构 */
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

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLModifyApplySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ApplyNo";
		pk[1] = "ApplyBatchNo";

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
		LLModifyApplySchema cloned = (LLModifyApplySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getApplyNo()
	{
		return ApplyNo;
	}
	public void setApplyNo(String aApplyNo)
	{
		if(aApplyNo!=null && aApplyNo.length()>20)
			throw new IllegalArgumentException("申请编码ApplyNo值"+aApplyNo+"的长度"+aApplyNo.length()+"大于最大值20");
		ApplyNo = aApplyNo;
	}
	public String getApplyBatchNo()
	{
		return ApplyBatchNo;
	}
	public void setApplyBatchNo(String aApplyBatchNo)
	{
		if(aApplyBatchNo!=null && aApplyBatchNo.length()>20)
			throw new IllegalArgumentException("申请批次号ApplyBatchNo值"+aApplyBatchNo+"的长度"+aApplyBatchNo.length()+"大于最大值20");
		ApplyBatchNo = aApplyBatchNo;
	}
	public String getApplyCom()
	{
		return ApplyCom;
	}
	public void setApplyCom(String aApplyCom)
	{
		if(aApplyCom!=null && aApplyCom.length()>20)
			throw new IllegalArgumentException("申请机构ApplyCom值"+aApplyCom+"的长度"+aApplyCom.length()+"大于最大值20");
		ApplyCom = aApplyCom;
	}
	public String getReasonNo()
	{
		return ReasonNo;
	}
	public void setReasonNo(String aReasonNo)
	{
		if(aReasonNo!=null && aReasonNo.length()>2)
			throw new IllegalArgumentException("保项修改原因编码ReasonNo值"+aReasonNo+"的长度"+aReasonNo.length()+"大于最大值2");
		ReasonNo = aReasonNo;
	}
	/**
	* 00-险种，01-险种保单，02-险种保单方案
	*/
	public String getRuleType()
	{
		return RuleType;
	}
	public void setRuleType(String aRuleType)
	{
		if(aRuleType!=null && aRuleType.length()>2)
			throw new IllegalArgumentException("规则类型RuleType值"+aRuleType+"的长度"+aRuleType.length()+"大于最大值2");
		RuleType = aRuleType;
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
	/**
	* 00-不确定，01-向上，02-向下
	*/
	public String getAdjustDirection()
	{
		return AdjustDirection;
	}
	public void setAdjustDirection(String aAdjustDirection)
	{
		if(aAdjustDirection!=null && aAdjustDirection.length()>2)
			throw new IllegalArgumentException("调整方向AdjustDirection值"+aAdjustDirection+"的长度"+aAdjustDirection.length()+"大于最大值2");
		AdjustDirection = aAdjustDirection;
	}
	/**
	* 00-账单发生额（医疗险），01-审核金额（医疗险），02-保额（非医疗险）
	*/
	public String getUpAdjustRule()
	{
		return UpAdjustRule;
	}
	public void setUpAdjustRule(String aUpAdjustRule)
	{
		if(aUpAdjustRule!=null && aUpAdjustRule.length()>2)
			throw new IllegalArgumentException("向上调整规则UpAdjustRule值"+aUpAdjustRule+"的长度"+aUpAdjustRule.length()+"大于最大值2");
		UpAdjustRule = aUpAdjustRule;
	}
	public double getUpAdjustRate()
	{
		return UpAdjustRate;
	}
	public void setUpAdjustRate(double aUpAdjustRate)
	{
		UpAdjustRate = aUpAdjustRate;
	}
	public void setUpAdjustRate(String aUpAdjustRate)
	{
		if (aUpAdjustRate != null && !aUpAdjustRate.equals(""))
		{
			Double tDouble = new Double(aUpAdjustRate);
			double d = tDouble.doubleValue();
			UpAdjustRate = d;
		}
	}

	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	/**
	* 0-申请，1-复核，2-审批，3-生效，4-失效
	*/
	public String getApplyState()
	{
		return ApplyState;
	}
	public void setApplyState(String aApplyState)
	{
		if(aApplyState!=null && aApplyState.length()>2)
			throw new IllegalArgumentException("状态ApplyState值"+aApplyState+"的长度"+aApplyState.length()+"大于最大值2");
		ApplyState = aApplyState;
	}
	public String getApplyDate()
	{
		if( ApplyDate != null )
			return fDate.getString(ApplyDate);
		else
			return null;
	}
	public void setApplyDate(Date aApplyDate)
	{
		ApplyDate = aApplyDate;
	}
	public void setApplyDate(String aApplyDate)
	{
		if (aApplyDate != null && !aApplyDate.equals("") )
		{
			ApplyDate = fDate.getDate( aApplyDate );
		}
		else
			ApplyDate = null;
	}

	public String getApplyOperator()
	{
		return ApplyOperator;
	}
	public void setApplyOperator(String aApplyOperator)
	{
		if(aApplyOperator!=null && aApplyOperator.length()>30)
			throw new IllegalArgumentException("申请操作员ApplyOperator值"+aApplyOperator+"的长度"+aApplyOperator.length()+"大于最大值30");
		ApplyOperator = aApplyOperator;
	}
	public String getCheckOperator()
	{
		return CheckOperator;
	}
	public void setCheckOperator(String aCheckOperator)
	{
		if(aCheckOperator!=null && aCheckOperator.length()>30)
			throw new IllegalArgumentException("复核操作员CheckOperator值"+aCheckOperator+"的长度"+aCheckOperator.length()+"大于最大值30");
		CheckOperator = aCheckOperator;
	}
	public String getCheckConclusion()
	{
		return CheckConclusion;
	}
	public void setCheckConclusion(String aCheckConclusion)
	{
		if(aCheckConclusion!=null && aCheckConclusion.length()>2)
			throw new IllegalArgumentException("复核结论CheckConclusion值"+aCheckConclusion+"的长度"+aCheckConclusion.length()+"大于最大值2");
		CheckConclusion = aCheckConclusion;
	}
	public String getCheckIdea()
	{
		return CheckIdea;
	}
	public void setCheckIdea(String aCheckIdea)
	{
		if(aCheckIdea!=null && aCheckIdea.length()>1000)
			throw new IllegalArgumentException("复核意见CheckIdea值"+aCheckIdea+"的长度"+aCheckIdea.length()+"大于最大值1000");
		CheckIdea = aCheckIdea;
	}
	public String getApproveOperator()
	{
		return ApproveOperator;
	}
	public void setApproveOperator(String aApproveOperator)
	{
		if(aApproveOperator!=null && aApproveOperator.length()>30)
			throw new IllegalArgumentException("审批操作员ApproveOperator值"+aApproveOperator+"的长度"+aApproveOperator.length()+"大于最大值30");
		ApproveOperator = aApproveOperator;
	}
	public String getApproveConclusion()
	{
		return ApproveConclusion;
	}
	public void setApproveConclusion(String aApproveConclusion)
	{
		if(aApproveConclusion!=null && aApproveConclusion.length()>2)
			throw new IllegalArgumentException("审批结论ApproveConclusion值"+aApproveConclusion+"的长度"+aApproveConclusion.length()+"大于最大值2");
		ApproveConclusion = aApproveConclusion;
	}
	public String getApproveIdea()
	{
		return ApproveIdea;
	}
	public void setApproveIdea(String aApproveIdea)
	{
		if(aApproveIdea!=null && aApproveIdea.length()>1000)
			throw new IllegalArgumentException("审批意见ApproveIdea值"+aApproveIdea+"的长度"+aApproveIdea.length()+"大于最大值1000");
		ApproveIdea = aApproveIdea;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("操作机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
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
	* 使用另外一个 LLModifyApplySchema 对象给 Schema 赋值
	* @param: aLLModifyApplySchema LLModifyApplySchema
	**/
	public void setSchema(LLModifyApplySchema aLLModifyApplySchema)
	{
		this.ApplyNo = aLLModifyApplySchema.getApplyNo();
		this.ApplyBatchNo = aLLModifyApplySchema.getApplyBatchNo();
		this.ApplyCom = aLLModifyApplySchema.getApplyCom();
		this.ReasonNo = aLLModifyApplySchema.getReasonNo();
		this.RuleType = aLLModifyApplySchema.getRuleType();
		this.RiskCode = aLLModifyApplySchema.getRiskCode();
		this.AdjustDirection = aLLModifyApplySchema.getAdjustDirection();
		this.UpAdjustRule = aLLModifyApplySchema.getUpAdjustRule();
		this.UpAdjustRate = aLLModifyApplySchema.getUpAdjustRate();
		this.StartDate = fDate.getDate( aLLModifyApplySchema.getStartDate());
		this.EndDate = fDate.getDate( aLLModifyApplySchema.getEndDate());
		this.Remark = aLLModifyApplySchema.getRemark();
		this.ApplyState = aLLModifyApplySchema.getApplyState();
		this.ApplyDate = fDate.getDate( aLLModifyApplySchema.getApplyDate());
		this.ApplyOperator = aLLModifyApplySchema.getApplyOperator();
		this.CheckOperator = aLLModifyApplySchema.getCheckOperator();
		this.CheckConclusion = aLLModifyApplySchema.getCheckConclusion();
		this.CheckIdea = aLLModifyApplySchema.getCheckIdea();
		this.ApproveOperator = aLLModifyApplySchema.getApproveOperator();
		this.ApproveConclusion = aLLModifyApplySchema.getApproveConclusion();
		this.ApproveIdea = aLLModifyApplySchema.getApproveIdea();
		this.ManageCom = aLLModifyApplySchema.getManageCom();
		this.ComCode = aLLModifyApplySchema.getComCode();
		this.MakeOperator = aLLModifyApplySchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLModifyApplySchema.getMakeDate());
		this.MakeTime = aLLModifyApplySchema.getMakeTime();
		this.ModifyOperator = aLLModifyApplySchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLModifyApplySchema.getModifyDate());
		this.ModifyTime = aLLModifyApplySchema.getModifyTime();
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
			if( rs.getString("ApplyNo") == null )
				this.ApplyNo = null;
			else
				this.ApplyNo = rs.getString("ApplyNo").trim();

			if( rs.getString("ApplyBatchNo") == null )
				this.ApplyBatchNo = null;
			else
				this.ApplyBatchNo = rs.getString("ApplyBatchNo").trim();

			if( rs.getString("ApplyCom") == null )
				this.ApplyCom = null;
			else
				this.ApplyCom = rs.getString("ApplyCom").trim();

			if( rs.getString("ReasonNo") == null )
				this.ReasonNo = null;
			else
				this.ReasonNo = rs.getString("ReasonNo").trim();

			if( rs.getString("RuleType") == null )
				this.RuleType = null;
			else
				this.RuleType = rs.getString("RuleType").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AdjustDirection") == null )
				this.AdjustDirection = null;
			else
				this.AdjustDirection = rs.getString("AdjustDirection").trim();

			if( rs.getString("UpAdjustRule") == null )
				this.UpAdjustRule = null;
			else
				this.UpAdjustRule = rs.getString("UpAdjustRule").trim();

			this.UpAdjustRate = rs.getDouble("UpAdjustRate");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ApplyState") == null )
				this.ApplyState = null;
			else
				this.ApplyState = rs.getString("ApplyState").trim();

			this.ApplyDate = rs.getDate("ApplyDate");
			if( rs.getString("ApplyOperator") == null )
				this.ApplyOperator = null;
			else
				this.ApplyOperator = rs.getString("ApplyOperator").trim();

			if( rs.getString("CheckOperator") == null )
				this.CheckOperator = null;
			else
				this.CheckOperator = rs.getString("CheckOperator").trim();

			if( rs.getString("CheckConclusion") == null )
				this.CheckConclusion = null;
			else
				this.CheckConclusion = rs.getString("CheckConclusion").trim();

			if( rs.getString("CheckIdea") == null )
				this.CheckIdea = null;
			else
				this.CheckIdea = rs.getString("CheckIdea").trim();

			if( rs.getString("ApproveOperator") == null )
				this.ApproveOperator = null;
			else
				this.ApproveOperator = rs.getString("ApproveOperator").trim();

			if( rs.getString("ApproveConclusion") == null )
				this.ApproveConclusion = null;
			else
				this.ApproveConclusion = rs.getString("ApproveConclusion").trim();

			if( rs.getString("ApproveIdea") == null )
				this.ApproveIdea = null;
			else
				this.ApproveIdea = rs.getString("ApproveIdea").trim();

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
			logger.debug("数据库中的LLModifyApply表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLModifyApplySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLModifyApplySchema getSchema()
	{
		LLModifyApplySchema aLLModifyApplySchema = new LLModifyApplySchema();
		aLLModifyApplySchema.setSchema(this);
		return aLLModifyApplySchema;
	}

	public LLModifyApplyDB getDB()
	{
		LLModifyApplyDB aDBOper = new LLModifyApplyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLModifyApply描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ApplyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyBatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjustDirection)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpAdjustRule)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UpAdjustRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveIdea)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLModifyApply>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ApplyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ApplyBatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ApplyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReasonNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AdjustDirection = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UpAdjustRule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UpAdjustRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ApplyState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ApplyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CheckOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CheckConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CheckIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ApproveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ApproveConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ApproveIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLModifyApplySchema";
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
		if (FCode.equalsIgnoreCase("ApplyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyNo));
		}
		if (FCode.equalsIgnoreCase("ApplyBatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyBatchNo));
		}
		if (FCode.equalsIgnoreCase("ApplyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyCom));
		}
		if (FCode.equalsIgnoreCase("ReasonNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonNo));
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleType));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AdjustDirection"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjustDirection));
		}
		if (FCode.equalsIgnoreCase("UpAdjustRule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpAdjustRule));
		}
		if (FCode.equalsIgnoreCase("UpAdjustRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpAdjustRate));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ApplyState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyState));
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
		}
		if (FCode.equalsIgnoreCase("ApplyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyOperator));
		}
		if (FCode.equalsIgnoreCase("CheckOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckOperator));
		}
		if (FCode.equalsIgnoreCase("CheckConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckConclusion));
		}
		if (FCode.equalsIgnoreCase("CheckIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckIdea));
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveOperator));
		}
		if (FCode.equalsIgnoreCase("ApproveConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveConclusion));
		}
		if (FCode.equalsIgnoreCase("ApproveIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveIdea));
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
				strFieldValue = StrTool.GBKToUnicode(ApplyNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ApplyBatchNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ApplyCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReasonNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AdjustDirection);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UpAdjustRule);
				break;
			case 8:
				strFieldValue = String.valueOf(UpAdjustRate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ApplyState);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ApplyOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CheckOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CheckConclusion);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CheckIdea);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ApproveOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ApproveConclusion);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ApproveIdea);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 28:
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

		if (FCode.equalsIgnoreCase("ApplyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyNo = FValue.trim();
			}
			else
				ApplyNo = null;
		}
		if (FCode.equalsIgnoreCase("ApplyBatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyBatchNo = FValue.trim();
			}
			else
				ApplyBatchNo = null;
		}
		if (FCode.equalsIgnoreCase("ApplyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyCom = FValue.trim();
			}
			else
				ApplyCom = null;
		}
		if (FCode.equalsIgnoreCase("ReasonNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonNo = FValue.trim();
			}
			else
				ReasonNo = null;
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleType = FValue.trim();
			}
			else
				RuleType = null;
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
		if (FCode.equalsIgnoreCase("AdjustDirection"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjustDirection = FValue.trim();
			}
			else
				AdjustDirection = null;
		}
		if (FCode.equalsIgnoreCase("UpAdjustRule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpAdjustRule = FValue.trim();
			}
			else
				UpAdjustRule = null;
		}
		if (FCode.equalsIgnoreCase("UpAdjustRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UpAdjustRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		if (FCode.equalsIgnoreCase("ApplyState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyState = FValue.trim();
			}
			else
				ApplyState = null;
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApplyDate = fDate.getDate( FValue );
			}
			else
				ApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("ApplyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyOperator = FValue.trim();
			}
			else
				ApplyOperator = null;
		}
		if (FCode.equalsIgnoreCase("CheckOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckOperator = FValue.trim();
			}
			else
				CheckOperator = null;
		}
		if (FCode.equalsIgnoreCase("CheckConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckConclusion = FValue.trim();
			}
			else
				CheckConclusion = null;
		}
		if (FCode.equalsIgnoreCase("CheckIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckIdea = FValue.trim();
			}
			else
				CheckIdea = null;
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveOperator = FValue.trim();
			}
			else
				ApproveOperator = null;
		}
		if (FCode.equalsIgnoreCase("ApproveConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveConclusion = FValue.trim();
			}
			else
				ApproveConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ApproveIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveIdea = FValue.trim();
			}
			else
				ApproveIdea = null;
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
		LLModifyApplySchema other = (LLModifyApplySchema)otherObject;
		return
			ApplyNo.equals(other.getApplyNo())
			&& ApplyBatchNo.equals(other.getApplyBatchNo())
			&& ApplyCom.equals(other.getApplyCom())
			&& ReasonNo.equals(other.getReasonNo())
			&& RuleType.equals(other.getRuleType())
			&& RiskCode.equals(other.getRiskCode())
			&& AdjustDirection.equals(other.getAdjustDirection())
			&& UpAdjustRule.equals(other.getUpAdjustRule())
			&& UpAdjustRate == other.getUpAdjustRate()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& Remark.equals(other.getRemark())
			&& ApplyState.equals(other.getApplyState())
			&& fDate.getString(ApplyDate).equals(other.getApplyDate())
			&& ApplyOperator.equals(other.getApplyOperator())
			&& CheckOperator.equals(other.getCheckOperator())
			&& CheckConclusion.equals(other.getCheckConclusion())
			&& CheckIdea.equals(other.getCheckIdea())
			&& ApproveOperator.equals(other.getApproveOperator())
			&& ApproveConclusion.equals(other.getApproveConclusion())
			&& ApproveIdea.equals(other.getApproveIdea())
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
		if( strFieldName.equals("ApplyNo") ) {
			return 0;
		}
		if( strFieldName.equals("ApplyBatchNo") ) {
			return 1;
		}
		if( strFieldName.equals("ApplyCom") ) {
			return 2;
		}
		if( strFieldName.equals("ReasonNo") ) {
			return 3;
		}
		if( strFieldName.equals("RuleType") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("AdjustDirection") ) {
			return 6;
		}
		if( strFieldName.equals("UpAdjustRule") ) {
			return 7;
		}
		if( strFieldName.equals("UpAdjustRate") ) {
			return 8;
		}
		if( strFieldName.equals("StartDate") ) {
			return 9;
		}
		if( strFieldName.equals("EndDate") ) {
			return 10;
		}
		if( strFieldName.equals("Remark") ) {
			return 11;
		}
		if( strFieldName.equals("ApplyState") ) {
			return 12;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ApplyOperator") ) {
			return 14;
		}
		if( strFieldName.equals("CheckOperator") ) {
			return 15;
		}
		if( strFieldName.equals("CheckConclusion") ) {
			return 16;
		}
		if( strFieldName.equals("CheckIdea") ) {
			return 17;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return 18;
		}
		if( strFieldName.equals("ApproveConclusion") ) {
			return 19;
		}
		if( strFieldName.equals("ApproveIdea") ) {
			return 20;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 21;
		}
		if( strFieldName.equals("ComCode") ) {
			return 22;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "ApplyNo";
				break;
			case 1:
				strFieldName = "ApplyBatchNo";
				break;
			case 2:
				strFieldName = "ApplyCom";
				break;
			case 3:
				strFieldName = "ReasonNo";
				break;
			case 4:
				strFieldName = "RuleType";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "AdjustDirection";
				break;
			case 7:
				strFieldName = "UpAdjustRule";
				break;
			case 8:
				strFieldName = "UpAdjustRate";
				break;
			case 9:
				strFieldName = "StartDate";
				break;
			case 10:
				strFieldName = "EndDate";
				break;
			case 11:
				strFieldName = "Remark";
				break;
			case 12:
				strFieldName = "ApplyState";
				break;
			case 13:
				strFieldName = "ApplyDate";
				break;
			case 14:
				strFieldName = "ApplyOperator";
				break;
			case 15:
				strFieldName = "CheckOperator";
				break;
			case 16:
				strFieldName = "CheckConclusion";
				break;
			case 17:
				strFieldName = "CheckIdea";
				break;
			case 18:
				strFieldName = "ApproveOperator";
				break;
			case 19:
				strFieldName = "ApproveConclusion";
				break;
			case 20:
				strFieldName = "ApproveIdea";
				break;
			case 21:
				strFieldName = "ManageCom";
				break;
			case 22:
				strFieldName = "ComCode";
				break;
			case 23:
				strFieldName = "MakeOperator";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyOperator";
				break;
			case 27:
				strFieldName = "ModifyDate";
				break;
			case 28:
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
		if( strFieldName.equals("ApplyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyBatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjustDirection") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpAdjustRule") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpAdjustRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApplyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveIdea") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
