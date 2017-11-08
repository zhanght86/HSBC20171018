

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIGUWErrorDB;

/*
 * <p>ClassName: RIGUWErrorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIGUWErrorSchema implements Schema, Cloneable
{
	// @Field
	/** 批次号 */
	private String BatchNo;
	/** 序列号 */
	private String SerialNo;
	/** 算法编码 */
	private String ArithmeticID;
	/** 应用类别 */
	private String CalItemType;
	/** 规则类别 */
	private String RuleType;
	/** 核保主码 */
	private String AuditCode;
	/** 核保附码 */
	private String AuditAffixCode;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 总单投保单号码 */
	private String ProposalGrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体投保单险种号码 */
	private String GrpProposalNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 投保单号码 */
	private String ProposalNo;
	/** 保险计划 */
	private String ContPlanCode;
	/** 责任号码 */
	private String DutyCode;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 核保出错信息 */
	private String UWError;
	/** 险种编码 */
	private String Riskcode;
	/** 当前值 */
	private String CurrValue;
	/** 核保可通过标记（分保） */
	private String UWPassFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;
	/** 备用数字属性1 */
	private double StandbyDouble1;
	/** 备用数字属性2 */
	private double StandbyDouble2;

	public static final int FIELDNUM = 33;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIGUWErrorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		RIGUWErrorSchema cloned = (RIGUWErrorSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getArithmeticID()
	{
		return ArithmeticID;
	}
	public void setArithmeticID(String aArithmeticID)
	{
		ArithmeticID = aArithmeticID;
	}
	/**
	* 01-个险新单<p>
	* 03-个险保全<p>
	* 05-团险新单<p>
	* 06-团险新单<p>
	* <p>
	* 04-再保核赔
	*/
	public String getCalItemType()
	{
		return CalItemType;
	}
	public void setCalItemType(String aCalItemType)
	{
		CalItemType = aCalItemType;
	}
	/**
	* 01：临分核保<p>
	* <p>
	* 04：再保核赔
	*/
	public String getRuleType()
	{
		return RuleType;
	}
	public void setRuleType(String aRuleType)
	{
		RuleType = aRuleType;
	}
	/**
	* 审核类型: <p>
	* 01-新单数据 审核主码为：000000<p>
	* 03-保全数据 审核主码为：edorno<p>
	* 04-理赔数据 审核主码为：caseno
	*/
	public String getAuditCode()
	{
		return AuditCode;
	}
	public void setAuditCode(String aAuditCode)
	{
		AuditCode = aAuditCode;
	}
	/**
	* 审核类型: <p>
	* 01-新单数据 审核附码为：000000<p>
	* 03-保全数据 审核附码为：EdorType<p>
	* 04-理赔数据 审核附码为：000000
	*/
	public String getAuditAffixCode()
	{
		return AuditAffixCode;
	}
	public void setAuditAffixCode(String aAuditAffixCode)
	{
		AuditAffixCode = aAuditAffixCode;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		ProposalGrpContNo = aProposalGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		GrpProposalNo = aGrpProposalNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	public String getUWError()
	{
		return UWError;
	}
	public void setUWError(String aUWError)
	{
		UWError = aUWError;
	}
	public String getRiskcode()
	{
		return Riskcode;
	}
	public void setRiskcode(String aRiskcode)
	{
		Riskcode = aRiskcode;
	}
	public String getCurrValue()
	{
		return CurrValue;
	}
	public void setCurrValue(String aCurrValue)
	{
		CurrValue = aCurrValue;
	}
	public String getUWPassFlag()
	{
		return UWPassFlag;
	}
	public void setUWPassFlag(String aUWPassFlag)
	{
		UWPassFlag = aUWPassFlag;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	public double getStandbyDouble1()
	{
		return StandbyDouble1;
	}
	public void setStandbyDouble1(double aStandbyDouble1)
	{
		StandbyDouble1 = aStandbyDouble1;
	}
	public void setStandbyDouble1(String aStandbyDouble1)
	{
		if (aStandbyDouble1 != null && !aStandbyDouble1.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble1);
			double d = tDouble.doubleValue();
			StandbyDouble1 = d;
		}
	}

	public double getStandbyDouble2()
	{
		return StandbyDouble2;
	}
	public void setStandbyDouble2(double aStandbyDouble2)
	{
		StandbyDouble2 = aStandbyDouble2;
	}
	public void setStandbyDouble2(String aStandbyDouble2)
	{
		if (aStandbyDouble2 != null && !aStandbyDouble2.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble2);
			double d = tDouble.doubleValue();
			StandbyDouble2 = d;
		}
	}


	/**
	* 使用另外一个 RIGUWErrorSchema 对象给 Schema 赋值
	* @param: aRIGUWErrorSchema RIGUWErrorSchema
	**/
	public void setSchema(RIGUWErrorSchema aRIGUWErrorSchema)
	{
		this.BatchNo = aRIGUWErrorSchema.getBatchNo();
		this.SerialNo = aRIGUWErrorSchema.getSerialNo();
		this.ArithmeticID = aRIGUWErrorSchema.getArithmeticID();
		this.CalItemType = aRIGUWErrorSchema.getCalItemType();
		this.RuleType = aRIGUWErrorSchema.getRuleType();
		this.AuditCode = aRIGUWErrorSchema.getAuditCode();
		this.AuditAffixCode = aRIGUWErrorSchema.getAuditAffixCode();
		this.GrpContNo = aRIGUWErrorSchema.getGrpContNo();
		this.ProposalGrpContNo = aRIGUWErrorSchema.getProposalGrpContNo();
		this.GrpPolNo = aRIGUWErrorSchema.getGrpPolNo();
		this.GrpProposalNo = aRIGUWErrorSchema.getGrpProposalNo();
		this.PolNo = aRIGUWErrorSchema.getPolNo();
		this.ProposalNo = aRIGUWErrorSchema.getProposalNo();
		this.ContPlanCode = aRIGUWErrorSchema.getContPlanCode();
		this.DutyCode = aRIGUWErrorSchema.getDutyCode();
		this.InsuredNo = aRIGUWErrorSchema.getInsuredNo();
		this.InsuredName = aRIGUWErrorSchema.getInsuredName();
		this.AppntNo = aRIGUWErrorSchema.getAppntNo();
		this.AppntName = aRIGUWErrorSchema.getAppntName();
		this.UWError = aRIGUWErrorSchema.getUWError();
		this.Riskcode = aRIGUWErrorSchema.getRiskcode();
		this.CurrValue = aRIGUWErrorSchema.getCurrValue();
		this.UWPassFlag = aRIGUWErrorSchema.getUWPassFlag();
		this.ManageCom = aRIGUWErrorSchema.getManageCom();
		this.MakeDate = fDate.getDate( aRIGUWErrorSchema.getMakeDate());
		this.MakeTime = aRIGUWErrorSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aRIGUWErrorSchema.getModifyDate());
		this.ModifyTime = aRIGUWErrorSchema.getModifyTime();
		this.StandbyString1 = aRIGUWErrorSchema.getStandbyString1();
		this.StandbyString2 = aRIGUWErrorSchema.getStandbyString2();
		this.StandbyString3 = aRIGUWErrorSchema.getStandbyString3();
		this.StandbyDouble1 = aRIGUWErrorSchema.getStandbyDouble1();
		this.StandbyDouble2 = aRIGUWErrorSchema.getStandbyDouble2();
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
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("ArithmeticID") == null )
				this.ArithmeticID = null;
			else
				this.ArithmeticID = rs.getString("ArithmeticID").trim();

			if( rs.getString("CalItemType") == null )
				this.CalItemType = null;
			else
				this.CalItemType = rs.getString("CalItemType").trim();

			if( rs.getString("RuleType") == null )
				this.RuleType = null;
			else
				this.RuleType = rs.getString("RuleType").trim();

			if( rs.getString("AuditCode") == null )
				this.AuditCode = null;
			else
				this.AuditCode = rs.getString("AuditCode").trim();

			if( rs.getString("AuditAffixCode") == null )
				this.AuditAffixCode = null;
			else
				this.AuditAffixCode = rs.getString("AuditAffixCode").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("UWError") == null )
				this.UWError = null;
			else
				this.UWError = rs.getString("UWError").trim();

			if( rs.getString("Riskcode") == null )
				this.Riskcode = null;
			else
				this.Riskcode = rs.getString("Riskcode").trim();

			if( rs.getString("CurrValue") == null )
				this.CurrValue = null;
			else
				this.CurrValue = rs.getString("CurrValue").trim();

			if( rs.getString("UWPassFlag") == null )
				this.UWPassFlag = null;
			else
				this.UWPassFlag = rs.getString("UWPassFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			this.StandbyDouble1 = rs.getDouble("StandbyDouble1");
			this.StandbyDouble2 = rs.getDouble("StandbyDouble2");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIGUWError表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIGUWErrorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIGUWErrorSchema getSchema()
	{
		RIGUWErrorSchema aRIGUWErrorSchema = new RIGUWErrorSchema();
		aRIGUWErrorSchema.setSchema(this);
		return aRIGUWErrorSchema;
	}

	public RIGUWErrorDB getDB()
	{
		RIGUWErrorDB aDBOper = new RIGUWErrorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIGUWError描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditAffixCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWError)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Riskcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIGUWError>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ArithmeticID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AuditCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AuditAffixCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			UWError = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Riskcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CurrValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			UWPassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			StandbyDouble1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIGUWErrorSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticID));
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemType));
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleType));
		}
		if (FCode.equalsIgnoreCase("AuditCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditCode));
		}
		if (FCode.equalsIgnoreCase("AuditAffixCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditAffixCode));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("UWError"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWError));
		}
		if (FCode.equalsIgnoreCase("Riskcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Riskcode));
		}
		if (FCode.equalsIgnoreCase("CurrValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrValue));
		}
		if (FCode.equalsIgnoreCase("UWPassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPassFlag));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble1));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble2));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalItemType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AuditCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AuditAffixCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(UWError);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Riskcode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(CurrValue);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(UWPassFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 31:
				strFieldValue = String.valueOf(StandbyDouble1);
				break;
			case 32:
				strFieldValue = String.valueOf(StandbyDouble2);
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

		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticID = FValue.trim();
			}
			else
				ArithmeticID = null;
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemType = FValue.trim();
			}
			else
				CalItemType = null;
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
		if (FCode.equalsIgnoreCase("AuditCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditCode = FValue.trim();
			}
			else
				AuditCode = null;
		}
		if (FCode.equalsIgnoreCase("AuditAffixCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditAffixCode = FValue.trim();
			}
			else
				AuditAffixCode = null;
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
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
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpProposalNo = FValue.trim();
			}
			else
				GrpProposalNo = null;
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
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
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
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
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
		if (FCode.equalsIgnoreCase("UWError"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWError = FValue.trim();
			}
			else
				UWError = null;
		}
		if (FCode.equalsIgnoreCase("Riskcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Riskcode = FValue.trim();
			}
			else
				Riskcode = null;
		}
		if (FCode.equalsIgnoreCase("CurrValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrValue = FValue.trim();
			}
			else
				CurrValue = null;
		}
		if (FCode.equalsIgnoreCase("UWPassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPassFlag = FValue.trim();
			}
			else
				UWPassFlag = null;
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
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble2 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIGUWErrorSchema other = (RIGUWErrorSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& SerialNo.equals(other.getSerialNo())
			&& ArithmeticID.equals(other.getArithmeticID())
			&& CalItemType.equals(other.getCalItemType())
			&& RuleType.equals(other.getRuleType())
			&& AuditCode.equals(other.getAuditCode())
			&& AuditAffixCode.equals(other.getAuditAffixCode())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& PolNo.equals(other.getPolNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& DutyCode.equals(other.getDutyCode())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& UWError.equals(other.getUWError())
			&& Riskcode.equals(other.getRiskcode())
			&& CurrValue.equals(other.getCurrValue())
			&& UWPassFlag.equals(other.getUWPassFlag())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& StandbyDouble1 == other.getStandbyDouble1()
			&& StandbyDouble2 == other.getStandbyDouble2();
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
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return 2;
		}
		if( strFieldName.equals("CalItemType") ) {
			return 3;
		}
		if( strFieldName.equals("RuleType") ) {
			return 4;
		}
		if( strFieldName.equals("AuditCode") ) {
			return 5;
		}
		if( strFieldName.equals("AuditAffixCode") ) {
			return 6;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 7;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 10;
		}
		if( strFieldName.equals("PolNo") ) {
			return 11;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 12;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 13;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 14;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 15;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 16;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 17;
		}
		if( strFieldName.equals("AppntName") ) {
			return 18;
		}
		if( strFieldName.equals("UWError") ) {
			return 19;
		}
		if( strFieldName.equals("Riskcode") ) {
			return 20;
		}
		if( strFieldName.equals("CurrValue") ) {
			return 21;
		}
		if( strFieldName.equals("UWPassFlag") ) {
			return 22;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 28;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 29;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 30;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return 31;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
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
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "ArithmeticID";
				break;
			case 3:
				strFieldName = "CalItemType";
				break;
			case 4:
				strFieldName = "RuleType";
				break;
			case 5:
				strFieldName = "AuditCode";
				break;
			case 6:
				strFieldName = "AuditAffixCode";
				break;
			case 7:
				strFieldName = "GrpContNo";
				break;
			case 8:
				strFieldName = "ProposalGrpContNo";
				break;
			case 9:
				strFieldName = "GrpPolNo";
				break;
			case 10:
				strFieldName = "GrpProposalNo";
				break;
			case 11:
				strFieldName = "PolNo";
				break;
			case 12:
				strFieldName = "ProposalNo";
				break;
			case 13:
				strFieldName = "ContPlanCode";
				break;
			case 14:
				strFieldName = "DutyCode";
				break;
			case 15:
				strFieldName = "InsuredNo";
				break;
			case 16:
				strFieldName = "InsuredName";
				break;
			case 17:
				strFieldName = "AppntNo";
				break;
			case 18:
				strFieldName = "AppntName";
				break;
			case 19:
				strFieldName = "UWError";
				break;
			case 20:
				strFieldName = "Riskcode";
				break;
			case 21:
				strFieldName = "CurrValue";
				break;
			case 22:
				strFieldName = "UWPassFlag";
				break;
			case 23:
				strFieldName = "ManageCom";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
				strFieldName = "ModifyTime";
				break;
			case 28:
				strFieldName = "StandbyString1";
				break;
			case 29:
				strFieldName = "StandbyString2";
				break;
			case 30:
				strFieldName = "StandbyString3";
				break;
			case 31:
				strFieldName = "StandbyDouble1";
				break;
			case 32:
				strFieldName = "StandbyDouble2";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditAffixCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWError") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Riskcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
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
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
