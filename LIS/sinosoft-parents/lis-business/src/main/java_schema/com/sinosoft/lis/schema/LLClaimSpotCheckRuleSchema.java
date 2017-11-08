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
import com.sinosoft.lis.db.LLClaimSpotCheckRuleDB;

/*
 * <p>ClassName: LLClaimSpotCheckRuleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LLClaimSpotCheckRuleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimSpotCheckRuleSchema.class);
	// @Field
	/** 抽检序号 */
	private String RuleNo;
	/** 抽检类型 */
	private String RuleType;
	/** 抽检机构 */
	private String RuleMngCom;
	/** 抽检用户 */
	private String CheckUserCode;
	/** 保单号 */
	private String GrpContNo;
	/** 投保人号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 险种编码 */
	private String RiskCode;
	/** 抽检比例 */
	private double Rate;
	/** 审核金额 */
	private double ApplyMoney;
	/** 赔付金额 */
	private double RealMoney;
	/** 给付方式 */
	private String GiveType;
	/** 理赔结论 */
	private String ClaimConclusion;
	/** 是否与受益人一致 */
	private String IsBnf;
	/** 抽检起期 */
	private Date StartDate;
	/** 抽检止期 */
	private Date EndDate;
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimSpotCheckRuleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RuleNo";

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
		LLClaimSpotCheckRuleSchema cloned = (LLClaimSpotCheckRuleSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRuleNo()
	{
		return RuleNo;
	}
	public void setRuleNo(String aRuleNo)
	{
		if(aRuleNo!=null && aRuleNo.length()>20)
			throw new IllegalArgumentException("抽检序号RuleNo值"+aRuleNo+"的长度"+aRuleNo.length()+"大于最大值20");
		RuleNo = aRuleNo;
	}
	/**
	* 0-未结案赔案抽检；1-结案抽检；2-自动调查
	*/
	public String getRuleType()
	{
		return RuleType;
	}
	public void setRuleType(String aRuleType)
	{
		if(aRuleType!=null && aRuleType.length()>2)
			throw new IllegalArgumentException("抽检类型RuleType值"+aRuleType+"的长度"+aRuleType.length()+"大于最大值2");
		RuleType = aRuleType;
	}
	public String getRuleMngCom()
	{
		return RuleMngCom;
	}
	public void setRuleMngCom(String aRuleMngCom)
	{
		if(aRuleMngCom!=null && aRuleMngCom.length()>20)
			throw new IllegalArgumentException("抽检机构RuleMngCom值"+aRuleMngCom+"的长度"+aRuleMngCom.length()+"大于最大值20");
		RuleMngCom = aRuleMngCom;
	}
	public String getCheckUserCode()
	{
		return CheckUserCode;
	}
	public void setCheckUserCode(String aCheckUserCode)
	{
		if(aCheckUserCode!=null && aCheckUserCode.length()>30)
			throw new IllegalArgumentException("抽检用户CheckUserCode值"+aCheckUserCode+"的长度"+aCheckUserCode.length()+"大于最大值30");
		CheckUserCode = aCheckUserCode;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>200)
			throw new IllegalArgumentException("投保人名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值200");
		AppntName = aAppntName;
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

	public double getApplyMoney()
	{
		return ApplyMoney;
	}
	public void setApplyMoney(double aApplyMoney)
	{
		ApplyMoney = aApplyMoney;
	}
	public void setApplyMoney(String aApplyMoney)
	{
		if (aApplyMoney != null && !aApplyMoney.equals(""))
		{
			Double tDouble = new Double(aApplyMoney);
			double d = tDouble.doubleValue();
			ApplyMoney = d;
		}
	}

	public double getRealMoney()
	{
		return RealMoney;
	}
	public void setRealMoney(double aRealMoney)
	{
		RealMoney = aRealMoney;
	}
	public void setRealMoney(String aRealMoney)
	{
		if (aRealMoney != null && !aRealMoney.equals(""))
		{
			Double tDouble = new Double(aRealMoney);
			double d = tDouble.doubleValue();
			RealMoney = d;
		}
	}

	public String getGiveType()
	{
		return GiveType;
	}
	public void setGiveType(String aGiveType)
	{
		if(aGiveType!=null && aGiveType.length()>2)
			throw new IllegalArgumentException("给付方式GiveType值"+aGiveType+"的长度"+aGiveType.length()+"大于最大值2");
		GiveType = aGiveType;
	}
	public String getClaimConclusion()
	{
		return ClaimConclusion;
	}
	public void setClaimConclusion(String aClaimConclusion)
	{
		if(aClaimConclusion!=null && aClaimConclusion.length()>2)
			throw new IllegalArgumentException("理赔结论ClaimConclusion值"+aClaimConclusion+"的长度"+aClaimConclusion.length()+"大于最大值2");
		ClaimConclusion = aClaimConclusion;
	}
	public String getIsBnf()
	{
		return IsBnf;
	}
	public void setIsBnf(String aIsBnf)
	{
		if(aIsBnf!=null && aIsBnf.length()>2)
			throw new IllegalArgumentException("是否与受益人一致IsBnf值"+aIsBnf+"的长度"+aIsBnf.length()+"大于最大值2");
		IsBnf = aIsBnf;
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
	* 使用另外一个 LLClaimSpotCheckRuleSchema 对象给 Schema 赋值
	* @param: aLLClaimSpotCheckRuleSchema LLClaimSpotCheckRuleSchema
	**/
	public void setSchema(LLClaimSpotCheckRuleSchema aLLClaimSpotCheckRuleSchema)
	{
		this.RuleNo = aLLClaimSpotCheckRuleSchema.getRuleNo();
		this.RuleType = aLLClaimSpotCheckRuleSchema.getRuleType();
		this.RuleMngCom = aLLClaimSpotCheckRuleSchema.getRuleMngCom();
		this.CheckUserCode = aLLClaimSpotCheckRuleSchema.getCheckUserCode();
		this.GrpContNo = aLLClaimSpotCheckRuleSchema.getGrpContNo();
		this.AppntNo = aLLClaimSpotCheckRuleSchema.getAppntNo();
		this.AppntName = aLLClaimSpotCheckRuleSchema.getAppntName();
		this.RiskCode = aLLClaimSpotCheckRuleSchema.getRiskCode();
		this.Rate = aLLClaimSpotCheckRuleSchema.getRate();
		this.ApplyMoney = aLLClaimSpotCheckRuleSchema.getApplyMoney();
		this.RealMoney = aLLClaimSpotCheckRuleSchema.getRealMoney();
		this.GiveType = aLLClaimSpotCheckRuleSchema.getGiveType();
		this.ClaimConclusion = aLLClaimSpotCheckRuleSchema.getClaimConclusion();
		this.IsBnf = aLLClaimSpotCheckRuleSchema.getIsBnf();
		this.StartDate = fDate.getDate( aLLClaimSpotCheckRuleSchema.getStartDate());
		this.EndDate = fDate.getDate( aLLClaimSpotCheckRuleSchema.getEndDate());
		this.ManageCom = aLLClaimSpotCheckRuleSchema.getManageCom();
		this.ComCode = aLLClaimSpotCheckRuleSchema.getComCode();
		this.MakeOperator = aLLClaimSpotCheckRuleSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLClaimSpotCheckRuleSchema.getMakeDate());
		this.MakeTime = aLLClaimSpotCheckRuleSchema.getMakeTime();
		this.ModifyOperator = aLLClaimSpotCheckRuleSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLClaimSpotCheckRuleSchema.getModifyDate());
		this.ModifyTime = aLLClaimSpotCheckRuleSchema.getModifyTime();
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
			if( rs.getString("RuleNo") == null )
				this.RuleNo = null;
			else
				this.RuleNo = rs.getString("RuleNo").trim();

			if( rs.getString("RuleType") == null )
				this.RuleType = null;
			else
				this.RuleType = rs.getString("RuleType").trim();

			if( rs.getString("RuleMngCom") == null )
				this.RuleMngCom = null;
			else
				this.RuleMngCom = rs.getString("RuleMngCom").trim();

			if( rs.getString("CheckUserCode") == null )
				this.CheckUserCode = null;
			else
				this.CheckUserCode = rs.getString("CheckUserCode").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.Rate = rs.getDouble("Rate");
			this.ApplyMoney = rs.getDouble("ApplyMoney");
			this.RealMoney = rs.getDouble("RealMoney");
			if( rs.getString("GiveType") == null )
				this.GiveType = null;
			else
				this.GiveType = rs.getString("GiveType").trim();

			if( rs.getString("ClaimConclusion") == null )
				this.ClaimConclusion = null;
			else
				this.ClaimConclusion = rs.getString("ClaimConclusion").trim();

			if( rs.getString("IsBnf") == null )
				this.IsBnf = null;
			else
				this.IsBnf = rs.getString("IsBnf").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
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
			logger.debug("数据库中的LLClaimSpotCheckRule表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimSpotCheckRuleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimSpotCheckRuleSchema getSchema()
	{
		LLClaimSpotCheckRuleSchema aLLClaimSpotCheckRuleSchema = new LLClaimSpotCheckRuleSchema();
		aLLClaimSpotCheckRuleSchema.setSchema(this);
		return aLLClaimSpotCheckRuleSchema;
	}

	public LLClaimSpotCheckRuleDB getDB()
	{
		LLClaimSpotCheckRuleDB aDBOper = new LLClaimSpotCheckRuleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimSpotCheckRule描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RuleNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ApplyMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsBnf)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimSpotCheckRule>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RuleNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RuleType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CheckUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			ApplyMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			RealMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			GiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ClaimConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			IsBnf = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimSpotCheckRuleSchema";
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
		if (FCode.equalsIgnoreCase("RuleNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleNo));
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleType));
		}
		if (FCode.equalsIgnoreCase("RuleMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleMngCom));
		}
		if (FCode.equalsIgnoreCase("CheckUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckUserCode));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("ApplyMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyMoney));
		}
		if (FCode.equalsIgnoreCase("RealMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealMoney));
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveType));
		}
		if (FCode.equalsIgnoreCase("ClaimConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimConclusion));
		}
		if (FCode.equalsIgnoreCase("IsBnf"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsBnf));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(RuleNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RuleType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleMngCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CheckUserCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 8:
				strFieldValue = String.valueOf(Rate);
				break;
			case 9:
				strFieldValue = String.valueOf(ApplyMoney);
				break;
			case 10:
				strFieldValue = String.valueOf(RealMoney);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GiveType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ClaimConclusion);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IsBnf);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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

		if (FCode.equalsIgnoreCase("RuleNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleNo = FValue.trim();
			}
			else
				RuleNo = null;
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
		if (FCode.equalsIgnoreCase("RuleMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleMngCom = FValue.trim();
			}
			else
				RuleMngCom = null;
		}
		if (FCode.equalsIgnoreCase("CheckUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckUserCode = FValue.trim();
			}
			else
				CheckUserCode = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
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
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ApplyMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ApplyMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveType = FValue.trim();
			}
			else
				GiveType = null;
		}
		if (FCode.equalsIgnoreCase("ClaimConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimConclusion = FValue.trim();
			}
			else
				ClaimConclusion = null;
		}
		if (FCode.equalsIgnoreCase("IsBnf"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsBnf = FValue.trim();
			}
			else
				IsBnf = null;
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
		LLClaimSpotCheckRuleSchema other = (LLClaimSpotCheckRuleSchema)otherObject;
		return
			RuleNo.equals(other.getRuleNo())
			&& RuleType.equals(other.getRuleType())
			&& RuleMngCom.equals(other.getRuleMngCom())
			&& CheckUserCode.equals(other.getCheckUserCode())
			&& GrpContNo.equals(other.getGrpContNo())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& RiskCode.equals(other.getRiskCode())
			&& Rate == other.getRate()
			&& ApplyMoney == other.getApplyMoney()
			&& RealMoney == other.getRealMoney()
			&& GiveType.equals(other.getGiveType())
			&& ClaimConclusion.equals(other.getClaimConclusion())
			&& IsBnf.equals(other.getIsBnf())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
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
		if( strFieldName.equals("RuleNo") ) {
			return 0;
		}
		if( strFieldName.equals("RuleType") ) {
			return 1;
		}
		if( strFieldName.equals("RuleMngCom") ) {
			return 2;
		}
		if( strFieldName.equals("CheckUserCode") ) {
			return 3;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 4;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 5;
		}
		if( strFieldName.equals("AppntName") ) {
			return 6;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("Rate") ) {
			return 8;
		}
		if( strFieldName.equals("ApplyMoney") ) {
			return 9;
		}
		if( strFieldName.equals("RealMoney") ) {
			return 10;
		}
		if( strFieldName.equals("GiveType") ) {
			return 11;
		}
		if( strFieldName.equals("ClaimConclusion") ) {
			return 12;
		}
		if( strFieldName.equals("IsBnf") ) {
			return 13;
		}
		if( strFieldName.equals("StartDate") ) {
			return 14;
		}
		if( strFieldName.equals("EndDate") ) {
			return 15;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 16;
		}
		if( strFieldName.equals("ComCode") ) {
			return 17;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
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
				strFieldName = "RuleNo";
				break;
			case 1:
				strFieldName = "RuleType";
				break;
			case 2:
				strFieldName = "RuleMngCom";
				break;
			case 3:
				strFieldName = "CheckUserCode";
				break;
			case 4:
				strFieldName = "GrpContNo";
				break;
			case 5:
				strFieldName = "AppntNo";
				break;
			case 6:
				strFieldName = "AppntName";
				break;
			case 7:
				strFieldName = "RiskCode";
				break;
			case 8:
				strFieldName = "Rate";
				break;
			case 9:
				strFieldName = "ApplyMoney";
				break;
			case 10:
				strFieldName = "RealMoney";
				break;
			case 11:
				strFieldName = "GiveType";
				break;
			case 12:
				strFieldName = "ClaimConclusion";
				break;
			case 13:
				strFieldName = "IsBnf";
				break;
			case 14:
				strFieldName = "StartDate";
				break;
			case 15:
				strFieldName = "EndDate";
				break;
			case 16:
				strFieldName = "ManageCom";
				break;
			case 17:
				strFieldName = "ComCode";
				break;
			case 18:
				strFieldName = "MakeOperator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyOperator";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
		if( strFieldName.equals("RuleNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ApplyMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GiveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsBnf") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
