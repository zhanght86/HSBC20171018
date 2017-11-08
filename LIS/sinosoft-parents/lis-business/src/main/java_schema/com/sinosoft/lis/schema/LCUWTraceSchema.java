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
import com.sinosoft.lis.db.LCUWTraceDB;

/*
 * <p>ClassName: LCUWTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCUWTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCUWTraceSchema.class);
	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 个人保单号 */
	private String ContNo;
	/** 客户号 */
	private String InsuredNo;
	/** 险种编码 */
	private String RiskCode;
	/** 规则层级 */
	private String RuleLevel;
	/** 核保顺序号 */
	private int UWNo;
	/** 规则编码 */
	private String RuleCode;
	/** 延至日 */
	private String PostponeDay;
	/** 延至日期 */
	private Date PostponeDate;
	/** 自动核保结论 */
	private String AutoUWConclu;
	/** 自动核保日期 */
	private Date AutoUWDate;
	/** 核保出错信息 */
	private String UWError;
	/** 人工核保结论 */
	private String UWConclu;
	/** 人工核保意见 */
	private String UWIdea;
	/** 人工核保日期 */
	private Date UWDate;
	/** 上报标志 */
	private String UpReport;
	/** 上报内容 */
	private String UpReportContent;
	/** 是否体检件 */
	private String HealthFlag;
	/** 是否问题件 */
	private String QuesFlag;
	/** 特约标志 */
	private String SpecFlag;
	/** 加费标志 */
	private String AddPremFlag;
	/** 加费原因 */
	private String AddPremReason;
	/** 生调标志 */
	private String ReportFlag;
	/** 核保通知书打印标记 */
	private String PrintFlag;
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

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCUWTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "ContNo";
		pk[1] = "InsuredNo";
		pk[2] = "RiskCode";
		pk[3] = "RuleLevel";
		pk[4] = "UWNo";
		pk[5] = "RuleCode";

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
		LCUWTraceSchema cloned = (LCUWTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>20)
			throw new IllegalArgumentException("客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值20");
		InsuredNo = aInsuredNo;
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
	/**
	* 00-团体保单，01-团体险种，10-个人保单，11-个人险种
	*/
	public String getRuleLevel()
	{
		return RuleLevel;
	}
	public void setRuleLevel(String aRuleLevel)
	{
		if(aRuleLevel!=null && aRuleLevel.length()>2)
			throw new IllegalArgumentException("规则层级RuleLevel值"+aRuleLevel+"的长度"+aRuleLevel.length()+"大于最大值2");
		RuleLevel = aRuleLevel;
	}
	public int getUWNo()
	{
		return UWNo;
	}
	public void setUWNo(int aUWNo)
	{
		UWNo = aUWNo;
	}
	public void setUWNo(String aUWNo)
	{
		if (aUWNo != null && !aUWNo.equals(""))
		{
			Integer tInteger = new Integer(aUWNo);
			int i = tInteger.intValue();
			UWNo = i;
		}
	}

	public String getRuleCode()
	{
		return RuleCode;
	}
	public void setRuleCode(String aRuleCode)
	{
		if(aRuleCode!=null && aRuleCode.length()>10)
			throw new IllegalArgumentException("规则编码RuleCode值"+aRuleCode+"的长度"+aRuleCode.length()+"大于最大值10");
		RuleCode = aRuleCode;
	}
	public String getPostponeDay()
	{
		return PostponeDay;
	}
	public void setPostponeDay(String aPostponeDay)
	{
		if(aPostponeDay!=null && aPostponeDay.length()>60)
			throw new IllegalArgumentException("延至日PostponeDay值"+aPostponeDay+"的长度"+aPostponeDay.length()+"大于最大值60");
		PostponeDay = aPostponeDay;
	}
	public String getPostponeDate()
	{
		if( PostponeDate != null )
			return fDate.getString(PostponeDate);
		else
			return null;
	}
	public void setPostponeDate(Date aPostponeDate)
	{
		PostponeDate = aPostponeDate;
	}
	public void setPostponeDate(String aPostponeDate)
	{
		if (aPostponeDate != null && !aPostponeDate.equals("") )
		{
			PostponeDate = fDate.getDate( aPostponeDate );
		}
		else
			PostponeDate = null;
	}

	public String getAutoUWConclu()
	{
		return AutoUWConclu;
	}
	public void setAutoUWConclu(String aAutoUWConclu)
	{
		if(aAutoUWConclu!=null && aAutoUWConclu.length()>1)
			throw new IllegalArgumentException("自动核保结论AutoUWConclu值"+aAutoUWConclu+"的长度"+aAutoUWConclu.length()+"大于最大值1");
		AutoUWConclu = aAutoUWConclu;
	}
	public String getAutoUWDate()
	{
		if( AutoUWDate != null )
			return fDate.getString(AutoUWDate);
		else
			return null;
	}
	public void setAutoUWDate(Date aAutoUWDate)
	{
		AutoUWDate = aAutoUWDate;
	}
	public void setAutoUWDate(String aAutoUWDate)
	{
		if (aAutoUWDate != null && !aAutoUWDate.equals("") )
		{
			AutoUWDate = fDate.getDate( aAutoUWDate );
		}
		else
			AutoUWDate = null;
	}

	public String getUWError()
	{
		return UWError;
	}
	public void setUWError(String aUWError)
	{
		if(aUWError!=null && aUWError.length()>4000)
			throw new IllegalArgumentException("核保出错信息UWError值"+aUWError+"的长度"+aUWError.length()+"大于最大值4000");
		UWError = aUWError;
	}
	public String getUWConclu()
	{
		return UWConclu;
	}
	public void setUWConclu(String aUWConclu)
	{
		if(aUWConclu!=null && aUWConclu.length()>1)
			throw new IllegalArgumentException("人工核保结论UWConclu值"+aUWConclu+"的长度"+aUWConclu.length()+"大于最大值1");
		UWConclu = aUWConclu;
	}
	public String getUWIdea()
	{
		return UWIdea;
	}
	public void setUWIdea(String aUWIdea)
	{
		if(aUWIdea!=null && aUWIdea.length()>3000)
			throw new IllegalArgumentException("人工核保意见UWIdea值"+aUWIdea+"的长度"+aUWIdea.length()+"大于最大值3000");
		UWIdea = aUWIdea;
	}
	public String getUWDate()
	{
		if( UWDate != null )
			return fDate.getString(UWDate);
		else
			return null;
	}
	public void setUWDate(Date aUWDate)
	{
		UWDate = aUWDate;
	}
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	public String getUpReport()
	{
		return UpReport;
	}
	public void setUpReport(String aUpReport)
	{
		if(aUpReport!=null && aUpReport.length()>1)
			throw new IllegalArgumentException("上报标志UpReport值"+aUpReport+"的长度"+aUpReport.length()+"大于最大值1");
		UpReport = aUpReport;
	}
	public String getUpReportContent()
	{
		return UpReportContent;
	}
	public void setUpReportContent(String aUpReportContent)
	{
		if(aUpReportContent!=null && aUpReportContent.length()>255)
			throw new IllegalArgumentException("上报内容UpReportContent值"+aUpReportContent+"的长度"+aUpReportContent.length()+"大于最大值255");
		UpReportContent = aUpReportContent;
	}
	public String getHealthFlag()
	{
		return HealthFlag;
	}
	public void setHealthFlag(String aHealthFlag)
	{
		if(aHealthFlag!=null && aHealthFlag.length()>1)
			throw new IllegalArgumentException("是否体检件HealthFlag值"+aHealthFlag+"的长度"+aHealthFlag.length()+"大于最大值1");
		HealthFlag = aHealthFlag;
	}
	public String getQuesFlag()
	{
		return QuesFlag;
	}
	public void setQuesFlag(String aQuesFlag)
	{
		if(aQuesFlag!=null && aQuesFlag.length()>1)
			throw new IllegalArgumentException("是否问题件QuesFlag值"+aQuesFlag+"的长度"+aQuesFlag.length()+"大于最大值1");
		QuesFlag = aQuesFlag;
	}
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		if(aSpecFlag!=null && aSpecFlag.length()>1)
			throw new IllegalArgumentException("特约标志SpecFlag值"+aSpecFlag+"的长度"+aSpecFlag.length()+"大于最大值1");
		SpecFlag = aSpecFlag;
	}
	public String getAddPremFlag()
	{
		return AddPremFlag;
	}
	public void setAddPremFlag(String aAddPremFlag)
	{
		if(aAddPremFlag!=null && aAddPremFlag.length()>1)
			throw new IllegalArgumentException("加费标志AddPremFlag值"+aAddPremFlag+"的长度"+aAddPremFlag.length()+"大于最大值1");
		AddPremFlag = aAddPremFlag;
	}
	public String getAddPremReason()
	{
		return AddPremReason;
	}
	public void setAddPremReason(String aAddPremReason)
	{
		if(aAddPremReason!=null && aAddPremReason.length()>255)
			throw new IllegalArgumentException("加费原因AddPremReason值"+aAddPremReason+"的长度"+aAddPremReason.length()+"大于最大值255");
		AddPremReason = aAddPremReason;
	}
	public String getReportFlag()
	{
		return ReportFlag;
	}
	public void setReportFlag(String aReportFlag)
	{
		if(aReportFlag!=null && aReportFlag.length()>1)
			throw new IllegalArgumentException("生调标志ReportFlag值"+aReportFlag+"的长度"+aReportFlag.length()+"大于最大值1");
		ReportFlag = aReportFlag;
	}
	public String getPrintFlag()
	{
		return PrintFlag;
	}
	public void setPrintFlag(String aPrintFlag)
	{
		if(aPrintFlag!=null && aPrintFlag.length()>1)
			throw new IllegalArgumentException("核保通知书打印标记PrintFlag值"+aPrintFlag+"的长度"+aPrintFlag.length()+"大于最大值1");
		PrintFlag = aPrintFlag;
	}
	/**
	* 承保管理机构
	*/
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
	/**
	* 承保公司代码
	*/
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
	* 使用另外一个 LCUWTraceSchema 对象给 Schema 赋值
	* @param: aLCUWTraceSchema LCUWTraceSchema
	**/
	public void setSchema(LCUWTraceSchema aLCUWTraceSchema)
	{
		this.GrpContNo = aLCUWTraceSchema.getGrpContNo();
		this.ContNo = aLCUWTraceSchema.getContNo();
		this.InsuredNo = aLCUWTraceSchema.getInsuredNo();
		this.RiskCode = aLCUWTraceSchema.getRiskCode();
		this.RuleLevel = aLCUWTraceSchema.getRuleLevel();
		this.UWNo = aLCUWTraceSchema.getUWNo();
		this.RuleCode = aLCUWTraceSchema.getRuleCode();
		this.PostponeDay = aLCUWTraceSchema.getPostponeDay();
		this.PostponeDate = fDate.getDate( aLCUWTraceSchema.getPostponeDate());
		this.AutoUWConclu = aLCUWTraceSchema.getAutoUWConclu();
		this.AutoUWDate = fDate.getDate( aLCUWTraceSchema.getAutoUWDate());
		this.UWError = aLCUWTraceSchema.getUWError();
		this.UWConclu = aLCUWTraceSchema.getUWConclu();
		this.UWIdea = aLCUWTraceSchema.getUWIdea();
		this.UWDate = fDate.getDate( aLCUWTraceSchema.getUWDate());
		this.UpReport = aLCUWTraceSchema.getUpReport();
		this.UpReportContent = aLCUWTraceSchema.getUpReportContent();
		this.HealthFlag = aLCUWTraceSchema.getHealthFlag();
		this.QuesFlag = aLCUWTraceSchema.getQuesFlag();
		this.SpecFlag = aLCUWTraceSchema.getSpecFlag();
		this.AddPremFlag = aLCUWTraceSchema.getAddPremFlag();
		this.AddPremReason = aLCUWTraceSchema.getAddPremReason();
		this.ReportFlag = aLCUWTraceSchema.getReportFlag();
		this.PrintFlag = aLCUWTraceSchema.getPrintFlag();
		this.ManageCom = aLCUWTraceSchema.getManageCom();
		this.ComCode = aLCUWTraceSchema.getComCode();
		this.MakeOperator = aLCUWTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLCUWTraceSchema.getMakeDate());
		this.MakeTime = aLCUWTraceSchema.getMakeTime();
		this.ModifyOperator = aLCUWTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLCUWTraceSchema.getModifyDate());
		this.ModifyTime = aLCUWTraceSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RuleLevel") == null )
				this.RuleLevel = null;
			else
				this.RuleLevel = rs.getString("RuleLevel").trim();

			this.UWNo = rs.getInt("UWNo");
			if( rs.getString("RuleCode") == null )
				this.RuleCode = null;
			else
				this.RuleCode = rs.getString("RuleCode").trim();

			if( rs.getString("PostponeDay") == null )
				this.PostponeDay = null;
			else
				this.PostponeDay = rs.getString("PostponeDay").trim();

			this.PostponeDate = rs.getDate("PostponeDate");
			if( rs.getString("AutoUWConclu") == null )
				this.AutoUWConclu = null;
			else
				this.AutoUWConclu = rs.getString("AutoUWConclu").trim();

			this.AutoUWDate = rs.getDate("AutoUWDate");
			if( rs.getString("UWError") == null )
				this.UWError = null;
			else
				this.UWError = rs.getString("UWError").trim();

			if( rs.getString("UWConclu") == null )
				this.UWConclu = null;
			else
				this.UWConclu = rs.getString("UWConclu").trim();

			if( rs.getString("UWIdea") == null )
				this.UWIdea = null;
			else
				this.UWIdea = rs.getString("UWIdea").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UpReport") == null )
				this.UpReport = null;
			else
				this.UpReport = rs.getString("UpReport").trim();

			if( rs.getString("UpReportContent") == null )
				this.UpReportContent = null;
			else
				this.UpReportContent = rs.getString("UpReportContent").trim();

			if( rs.getString("HealthFlag") == null )
				this.HealthFlag = null;
			else
				this.HealthFlag = rs.getString("HealthFlag").trim();

			if( rs.getString("QuesFlag") == null )
				this.QuesFlag = null;
			else
				this.QuesFlag = rs.getString("QuesFlag").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("AddPremFlag") == null )
				this.AddPremFlag = null;
			else
				this.AddPremFlag = rs.getString("AddPremFlag").trim();

			if( rs.getString("AddPremReason") == null )
				this.AddPremReason = null;
			else
				this.AddPremReason = rs.getString("AddPremReason").trim();

			if( rs.getString("ReportFlag") == null )
				this.ReportFlag = null;
			else
				this.ReportFlag = rs.getString("ReportFlag").trim();

			if( rs.getString("PrintFlag") == null )
				this.PrintFlag = null;
			else
				this.PrintFlag = rs.getString("PrintFlag").trim();

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
			logger.debug("数据库中的LCUWTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCUWTraceSchema getSchema()
	{
		LCUWTraceSchema aLCUWTraceSchema = new LCUWTraceSchema();
		aLCUWTraceSchema.setSchema(this);
		return aLCUWTraceSchema;
	}

	public LCUWTraceDB getDB()
	{
		LCUWTraceDB aDBOper = new LCUWTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCUWTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostponeDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PostponeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoUWConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AutoUWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWError)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpReport)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpReportContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuesFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddPremFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddPremReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintFlag)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCUWTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UWNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PostponeDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PostponeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			AutoUWConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AutoUWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			UWError = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			UWConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			UWIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			UpReport = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			UpReportContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			HealthFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			QuesFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AddPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AddPremReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ReportFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PrintFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWTraceSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RuleLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleLevel));
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWNo));
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCode));
		}
		if (FCode.equalsIgnoreCase("PostponeDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostponeDay));
		}
		if (FCode.equalsIgnoreCase("PostponeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPostponeDate()));
		}
		if (FCode.equalsIgnoreCase("AutoUWConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoUWConclu));
		}
		if (FCode.equalsIgnoreCase("AutoUWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAutoUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWError"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWError));
		}
		if (FCode.equalsIgnoreCase("UWConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWConclu));
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWIdea));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UpReport"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpReport));
		}
		if (FCode.equalsIgnoreCase("UpReportContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpReportContent));
		}
		if (FCode.equalsIgnoreCase("HealthFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthFlag));
		}
		if (FCode.equalsIgnoreCase("QuesFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuesFlag));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("AddPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPremFlag));
		}
		if (FCode.equalsIgnoreCase("AddPremReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPremReason));
		}
		if (FCode.equalsIgnoreCase("ReportFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportFlag));
		}
		if (FCode.equalsIgnoreCase("PrintFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintFlag));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleLevel);
				break;
			case 5:
				strFieldValue = String.valueOf(UWNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PostponeDay);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPostponeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AutoUWConclu);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAutoUWDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWError);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(UWConclu);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(UWIdea);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(UpReport);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(UpReportContent);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(HealthFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(QuesFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AddPremFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AddPremReason);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ReportFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(PrintFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
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
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equalsIgnoreCase("RuleLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleLevel = FValue.trim();
			}
			else
				RuleLevel = null;
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCode = FValue.trim();
			}
			else
				RuleCode = null;
		}
		if (FCode.equalsIgnoreCase("PostponeDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostponeDay = FValue.trim();
			}
			else
				PostponeDay = null;
		}
		if (FCode.equalsIgnoreCase("PostponeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PostponeDate = fDate.getDate( FValue );
			}
			else
				PostponeDate = null;
		}
		if (FCode.equalsIgnoreCase("AutoUWConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoUWConclu = FValue.trim();
			}
			else
				AutoUWConclu = null;
		}
		if (FCode.equalsIgnoreCase("AutoUWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AutoUWDate = fDate.getDate( FValue );
			}
			else
				AutoUWDate = null;
		}
		if (FCode.equalsIgnoreCase("UWError"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWError = FValue.trim();
			}
			else
				UWError = null;
		}
		if (FCode.equalsIgnoreCase("UWConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWConclu = FValue.trim();
			}
			else
				UWConclu = null;
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWIdea = FValue.trim();
			}
			else
				UWIdea = null;
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
			}
			else
				UWDate = null;
		}
		if (FCode.equalsIgnoreCase("UpReport"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpReport = FValue.trim();
			}
			else
				UpReport = null;
		}
		if (FCode.equalsIgnoreCase("UpReportContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpReportContent = FValue.trim();
			}
			else
				UpReportContent = null;
		}
		if (FCode.equalsIgnoreCase("HealthFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthFlag = FValue.trim();
			}
			else
				HealthFlag = null;
		}
		if (FCode.equalsIgnoreCase("QuesFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuesFlag = FValue.trim();
			}
			else
				QuesFlag = null;
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecFlag = FValue.trim();
			}
			else
				SpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("AddPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddPremFlag = FValue.trim();
			}
			else
				AddPremFlag = null;
		}
		if (FCode.equalsIgnoreCase("AddPremReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddPremReason = FValue.trim();
			}
			else
				AddPremReason = null;
		}
		if (FCode.equalsIgnoreCase("ReportFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportFlag = FValue.trim();
			}
			else
				ReportFlag = null;
		}
		if (FCode.equalsIgnoreCase("PrintFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintFlag = FValue.trim();
			}
			else
				PrintFlag = null;
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
		LCUWTraceSchema other = (LCUWTraceSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& RiskCode.equals(other.getRiskCode())
			&& RuleLevel.equals(other.getRuleLevel())
			&& UWNo == other.getUWNo()
			&& RuleCode.equals(other.getRuleCode())
			&& PostponeDay.equals(other.getPostponeDay())
			&& fDate.getString(PostponeDate).equals(other.getPostponeDate())
			&& AutoUWConclu.equals(other.getAutoUWConclu())
			&& fDate.getString(AutoUWDate).equals(other.getAutoUWDate())
			&& UWError.equals(other.getUWError())
			&& UWConclu.equals(other.getUWConclu())
			&& UWIdea.equals(other.getUWIdea())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UpReport.equals(other.getUpReport())
			&& UpReportContent.equals(other.getUpReportContent())
			&& HealthFlag.equals(other.getHealthFlag())
			&& QuesFlag.equals(other.getQuesFlag())
			&& SpecFlag.equals(other.getSpecFlag())
			&& AddPremFlag.equals(other.getAddPremFlag())
			&& AddPremReason.equals(other.getAddPremReason())
			&& ReportFlag.equals(other.getReportFlag())
			&& PrintFlag.equals(other.getPrintFlag())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("RuleLevel") ) {
			return 4;
		}
		if( strFieldName.equals("UWNo") ) {
			return 5;
		}
		if( strFieldName.equals("RuleCode") ) {
			return 6;
		}
		if( strFieldName.equals("PostponeDay") ) {
			return 7;
		}
		if( strFieldName.equals("PostponeDate") ) {
			return 8;
		}
		if( strFieldName.equals("AutoUWConclu") ) {
			return 9;
		}
		if( strFieldName.equals("AutoUWDate") ) {
			return 10;
		}
		if( strFieldName.equals("UWError") ) {
			return 11;
		}
		if( strFieldName.equals("UWConclu") ) {
			return 12;
		}
		if( strFieldName.equals("UWIdea") ) {
			return 13;
		}
		if( strFieldName.equals("UWDate") ) {
			return 14;
		}
		if( strFieldName.equals("UpReport") ) {
			return 15;
		}
		if( strFieldName.equals("UpReportContent") ) {
			return 16;
		}
		if( strFieldName.equals("HealthFlag") ) {
			return 17;
		}
		if( strFieldName.equals("QuesFlag") ) {
			return 18;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 19;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return 20;
		}
		if( strFieldName.equals("AddPremReason") ) {
			return 21;
		}
		if( strFieldName.equals("ReportFlag") ) {
			return 22;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return 23;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 24;
		}
		if( strFieldName.equals("ComCode") ) {
			return 25;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 26;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 27;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "InsuredNo";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "RuleLevel";
				break;
			case 5:
				strFieldName = "UWNo";
				break;
			case 6:
				strFieldName = "RuleCode";
				break;
			case 7:
				strFieldName = "PostponeDay";
				break;
			case 8:
				strFieldName = "PostponeDate";
				break;
			case 9:
				strFieldName = "AutoUWConclu";
				break;
			case 10:
				strFieldName = "AutoUWDate";
				break;
			case 11:
				strFieldName = "UWError";
				break;
			case 12:
				strFieldName = "UWConclu";
				break;
			case 13:
				strFieldName = "UWIdea";
				break;
			case 14:
				strFieldName = "UWDate";
				break;
			case 15:
				strFieldName = "UpReport";
				break;
			case 16:
				strFieldName = "UpReportContent";
				break;
			case 17:
				strFieldName = "HealthFlag";
				break;
			case 18:
				strFieldName = "QuesFlag";
				break;
			case 19:
				strFieldName = "SpecFlag";
				break;
			case 20:
				strFieldName = "AddPremFlag";
				break;
			case 21:
				strFieldName = "AddPremReason";
				break;
			case 22:
				strFieldName = "ReportFlag";
				break;
			case 23:
				strFieldName = "PrintFlag";
				break;
			case 24:
				strFieldName = "ManageCom";
				break;
			case 25:
				strFieldName = "ComCode";
				break;
			case 26:
				strFieldName = "MakeOperator";
				break;
			case 27:
				strFieldName = "MakeDate";
				break;
			case 28:
				strFieldName = "MakeTime";
				break;
			case 29:
				strFieldName = "ModifyOperator";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostponeDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostponeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AutoUWConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoUWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWError") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UpReport") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpReportContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuesFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPremReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
