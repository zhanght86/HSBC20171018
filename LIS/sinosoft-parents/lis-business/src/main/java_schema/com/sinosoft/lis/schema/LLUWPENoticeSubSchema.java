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
import com.sinosoft.lis.db.LLUWPENoticeSubDB;

/*
 * <p>ClassName: LLUWPENoticeSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLUWPENoticeSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLUWPENoticeSubSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 批次号 */
	private String BatNo;
	/** 流水号 */
	private String SerialNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 打印流水号 */
	private String PrtSeq;
	/** 投保人姓名 */
	private String AppName;
	/** 险种名称 */
	private String RiskName;
	/** 体检日期 */
	private Date PEDate;
	/** 体检客户号码 */
	private String CustomerNo;
	/** 体检客户姓名 */
	private String Name;
	/** 体检地点 */
	private String PEAddress;
	/** 体检前条件 */
	private String PEBeforeCond;
	/** 打印标记 */
	private String PrintFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 代理人姓名 */
	private String AgentName;
	/** 代理人编码 */
	private String AgentCode;
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
	/** 备注 */
	private String Remark;
	/** 体检结论 */
	private String PEResult;
	/** 团单体检总表打印流水号 */
	private String GrpPrtSeq;
	/** 体检医师 */
	private String PEDoctor;
	/** 复查时间 */
	private Date REPEDate;
	/** 体检原因 */
	private String PEReason;
	/** 阳性标记 */
	private String MasculineFlag;
	/** 客户类型 */
	private String CustomerType;
	/** 体检医院编码 */
	private String HospitCode;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLUWPENoticeSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "ClmNo";
		pk[1] = "BatNo";
		pk[2] = "SerialNo";
		pk[3] = "ContNo";
		pk[4] = "CustomerNo";

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
		LLUWPENoticeSubSchema cloned = (LLUWPENoticeSubSchema)super.clone();
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
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
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
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getAppName()
	{
		return AppName;
	}
	public void setAppName(String aAppName)
	{
		AppName = aAppName;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	public String getPEDate()
	{
		if( PEDate != null )
			return fDate.getString(PEDate);
		else
			return null;
	}
	public void setPEDate(Date aPEDate)
	{
		PEDate = aPEDate;
	}
	public void setPEDate(String aPEDate)
	{
		if (aPEDate != null && !aPEDate.equals("") )
		{
			PEDate = fDate.getDate( aPEDate );
		}
		else
			PEDate = null;
	}

	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getPEAddress()
	{
		return PEAddress;
	}
	public void setPEAddress(String aPEAddress)
	{
		PEAddress = aPEAddress;
	}
	/**
	* Y -- 表示空腹<p>
	* N -- 表示无需空腹
	*/
	public String getPEBeforeCond()
	{
		return PEBeforeCond;
	}
	public void setPEBeforeCond(String aPEBeforeCond)
	{
		PEBeforeCond = aPEBeforeCond;
	}
	/**
	* 0 --- 表示发放未打印<p>
	* 1 --- 表示已打印<p>
	* 2 --- 表示已回收
	*/
	public String getPrintFlag()
	{
		return PrintFlag;
	}
	public void setPrintFlag(String aPrintFlag)
	{
		PrintFlag = aPrintFlag;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAgentName()
	{
		return AgentName;
	}
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getPEResult()
	{
		return PEResult;
	}
	public void setPEResult(String aPEResult)
	{
		PEResult = aPEResult;
	}
	public String getGrpPrtSeq()
	{
		return GrpPrtSeq;
	}
	public void setGrpPrtSeq(String aGrpPrtSeq)
	{
		GrpPrtSeq = aGrpPrtSeq;
	}
	public String getPEDoctor()
	{
		return PEDoctor;
	}
	public void setPEDoctor(String aPEDoctor)
	{
		PEDoctor = aPEDoctor;
	}
	public String getREPEDate()
	{
		if( REPEDate != null )
			return fDate.getString(REPEDate);
		else
			return null;
	}
	public void setREPEDate(Date aREPEDate)
	{
		REPEDate = aREPEDate;
	}
	public void setREPEDate(String aREPEDate)
	{
		if (aREPEDate != null && !aREPEDate.equals("") )
		{
			REPEDate = fDate.getDate( aREPEDate );
		}
		else
			REPEDate = null;
	}

	public String getPEReason()
	{
		return PEReason;
	}
	public void setPEReason(String aPEReason)
	{
		PEReason = aPEReason;
	}
	public String getMasculineFlag()
	{
		return MasculineFlag;
	}
	public void setMasculineFlag(String aMasculineFlag)
	{
		MasculineFlag = aMasculineFlag;
	}
	public String getCustomerType()
	{
		return CustomerType;
	}
	public void setCustomerType(String aCustomerType)
	{
		CustomerType = aCustomerType;
	}
	public String getHospitCode()
	{
		return HospitCode;
	}
	public void setHospitCode(String aHospitCode)
	{
		HospitCode = aHospitCode;
	}

	/**
	* 使用另外一个 LLUWPENoticeSubSchema 对象给 Schema 赋值
	* @param: aLLUWPENoticeSubSchema LLUWPENoticeSubSchema
	**/
	public void setSchema(LLUWPENoticeSubSchema aLLUWPENoticeSubSchema)
	{
		this.ClmNo = aLLUWPENoticeSubSchema.getClmNo();
		this.BatNo = aLLUWPENoticeSubSchema.getBatNo();
		this.SerialNo = aLLUWPENoticeSubSchema.getSerialNo();
		this.GrpContNo = aLLUWPENoticeSubSchema.getGrpContNo();
		this.ContNo = aLLUWPENoticeSubSchema.getContNo();
		this.ProposalContNo = aLLUWPENoticeSubSchema.getProposalContNo();
		this.PrtSeq = aLLUWPENoticeSubSchema.getPrtSeq();
		this.AppName = aLLUWPENoticeSubSchema.getAppName();
		this.RiskName = aLLUWPENoticeSubSchema.getRiskName();
		this.PEDate = fDate.getDate( aLLUWPENoticeSubSchema.getPEDate());
		this.CustomerNo = aLLUWPENoticeSubSchema.getCustomerNo();
		this.Name = aLLUWPENoticeSubSchema.getName();
		this.PEAddress = aLLUWPENoticeSubSchema.getPEAddress();
		this.PEBeforeCond = aLLUWPENoticeSubSchema.getPEBeforeCond();
		this.PrintFlag = aLLUWPENoticeSubSchema.getPrintFlag();
		this.ManageCom = aLLUWPENoticeSubSchema.getManageCom();
		this.AgentName = aLLUWPENoticeSubSchema.getAgentName();
		this.AgentCode = aLLUWPENoticeSubSchema.getAgentCode();
		this.Operator = aLLUWPENoticeSubSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLUWPENoticeSubSchema.getMakeDate());
		this.MakeTime = aLLUWPENoticeSubSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLUWPENoticeSubSchema.getModifyDate());
		this.ModifyTime = aLLUWPENoticeSubSchema.getModifyTime();
		this.Remark = aLLUWPENoticeSubSchema.getRemark();
		this.PEResult = aLLUWPENoticeSubSchema.getPEResult();
		this.GrpPrtSeq = aLLUWPENoticeSubSchema.getGrpPrtSeq();
		this.PEDoctor = aLLUWPENoticeSubSchema.getPEDoctor();
		this.REPEDate = fDate.getDate( aLLUWPENoticeSubSchema.getREPEDate());
		this.PEReason = aLLUWPENoticeSubSchema.getPEReason();
		this.MasculineFlag = aLLUWPENoticeSubSchema.getMasculineFlag();
		this.CustomerType = aLLUWPENoticeSubSchema.getCustomerType();
		this.HospitCode = aLLUWPENoticeSubSchema.getHospitCode();
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

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("AppName") == null )
				this.AppName = null;
			else
				this.AppName = rs.getString("AppName").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			this.PEDate = rs.getDate("PEDate");
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("PEAddress") == null )
				this.PEAddress = null;
			else
				this.PEAddress = rs.getString("PEAddress").trim();

			if( rs.getString("PEBeforeCond") == null )
				this.PEBeforeCond = null;
			else
				this.PEBeforeCond = rs.getString("PEBeforeCond").trim();

			if( rs.getString("PrintFlag") == null )
				this.PrintFlag = null;
			else
				this.PrintFlag = rs.getString("PrintFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("PEResult") == null )
				this.PEResult = null;
			else
				this.PEResult = rs.getString("PEResult").trim();

			if( rs.getString("GrpPrtSeq") == null )
				this.GrpPrtSeq = null;
			else
				this.GrpPrtSeq = rs.getString("GrpPrtSeq").trim();

			if( rs.getString("PEDoctor") == null )
				this.PEDoctor = null;
			else
				this.PEDoctor = rs.getString("PEDoctor").trim();

			this.REPEDate = rs.getDate("REPEDate");
			if( rs.getString("PEReason") == null )
				this.PEReason = null;
			else
				this.PEReason = rs.getString("PEReason").trim();

			if( rs.getString("MasculineFlag") == null )
				this.MasculineFlag = null;
			else
				this.MasculineFlag = rs.getString("MasculineFlag").trim();

			if( rs.getString("CustomerType") == null )
				this.CustomerType = null;
			else
				this.CustomerType = rs.getString("CustomerType").trim();

			if( rs.getString("HospitCode") == null )
				this.HospitCode = null;
			else
				this.HospitCode = rs.getString("HospitCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLUWPENoticeSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLUWPENoticeSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLUWPENoticeSubSchema getSchema()
	{
		LLUWPENoticeSubSchema aLLUWPENoticeSubSchema = new LLUWPENoticeSubSchema();
		aLLUWPENoticeSubSchema.setSchema(this);
		return aLLUWPENoticeSubSchema;
	}

	public LLUWPENoticeSubDB getDB()
	{
		LLUWPENoticeSubDB aDBOper = new LLUWPENoticeSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLUWPENoticeSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PEDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEBeforeCond)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEDoctor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( REPEDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PEReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MasculineFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLUWPENoticeSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PEDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PEAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PEBeforeCond = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PrintFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PEResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			GrpPrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			PEDoctor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			REPEDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			PEReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			MasculineFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CustomerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			HospitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLUWPENoticeSubSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("AppName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppName));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("PEDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPEDate()));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("PEAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEAddress));
		}
		if (FCode.equalsIgnoreCase("PEBeforeCond"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEBeforeCond));
		}
		if (FCode.equalsIgnoreCase("PrintFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintFlag));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("PEResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEResult));
		}
		if (FCode.equalsIgnoreCase("GrpPrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPrtSeq));
		}
		if (FCode.equalsIgnoreCase("PEDoctor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEDoctor));
		}
		if (FCode.equalsIgnoreCase("REPEDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getREPEDate()));
		}
		if (FCode.equalsIgnoreCase("PEReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PEReason));
		}
		if (FCode.equalsIgnoreCase("MasculineFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MasculineFlag));
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerType));
		}
		if (FCode.equalsIgnoreCase("HospitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitCode));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPEDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PEAddress);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PEBeforeCond);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PrintFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(PEResult);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(GrpPrtSeq);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(PEDoctor);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getREPEDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PEReason);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MasculineFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(CustomerType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(HospitCode);
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("AppName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppName = FValue.trim();
			}
			else
				AppName = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("PEDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PEDate = fDate.getDate( FValue );
			}
			else
				PEDate = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("PEAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEAddress = FValue.trim();
			}
			else
				PEAddress = null;
		}
		if (FCode.equalsIgnoreCase("PEBeforeCond"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEBeforeCond = FValue.trim();
			}
			else
				PEBeforeCond = null;
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
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("PEResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEResult = FValue.trim();
			}
			else
				PEResult = null;
		}
		if (FCode.equalsIgnoreCase("GrpPrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPrtSeq = FValue.trim();
			}
			else
				GrpPrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("PEDoctor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEDoctor = FValue.trim();
			}
			else
				PEDoctor = null;
		}
		if (FCode.equalsIgnoreCase("REPEDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				REPEDate = fDate.getDate( FValue );
			}
			else
				REPEDate = null;
		}
		if (FCode.equalsIgnoreCase("PEReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PEReason = FValue.trim();
			}
			else
				PEReason = null;
		}
		if (FCode.equalsIgnoreCase("MasculineFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MasculineFlag = FValue.trim();
			}
			else
				MasculineFlag = null;
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerType = FValue.trim();
			}
			else
				CustomerType = null;
		}
		if (FCode.equalsIgnoreCase("HospitCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitCode = FValue.trim();
			}
			else
				HospitCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLUWPENoticeSubSchema other = (LLUWPENoticeSubSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& BatNo.equals(other.getBatNo())
			&& SerialNo.equals(other.getSerialNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtSeq.equals(other.getPrtSeq())
			&& AppName.equals(other.getAppName())
			&& RiskName.equals(other.getRiskName())
			&& fDate.getString(PEDate).equals(other.getPEDate())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& PEAddress.equals(other.getPEAddress())
			&& PEBeforeCond.equals(other.getPEBeforeCond())
			&& PrintFlag.equals(other.getPrintFlag())
			&& ManageCom.equals(other.getManageCom())
			&& AgentName.equals(other.getAgentName())
			&& AgentCode.equals(other.getAgentCode())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark())
			&& PEResult.equals(other.getPEResult())
			&& GrpPrtSeq.equals(other.getGrpPrtSeq())
			&& PEDoctor.equals(other.getPEDoctor())
			&& fDate.getString(REPEDate).equals(other.getREPEDate())
			&& PEReason.equals(other.getPEReason())
			&& MasculineFlag.equals(other.getMasculineFlag())
			&& CustomerType.equals(other.getCustomerType())
			&& HospitCode.equals(other.getHospitCode());
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
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
			return 4;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 5;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 6;
		}
		if( strFieldName.equals("AppName") ) {
			return 7;
		}
		if( strFieldName.equals("RiskName") ) {
			return 8;
		}
		if( strFieldName.equals("PEDate") ) {
			return 9;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 10;
		}
		if( strFieldName.equals("Name") ) {
			return 11;
		}
		if( strFieldName.equals("PEAddress") ) {
			return 12;
		}
		if( strFieldName.equals("PEBeforeCond") ) {
			return 13;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return 14;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 15;
		}
		if( strFieldName.equals("AgentName") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
		}
		if( strFieldName.equals("Remark") ) {
			return 23;
		}
		if( strFieldName.equals("PEResult") ) {
			return 24;
		}
		if( strFieldName.equals("GrpPrtSeq") ) {
			return 25;
		}
		if( strFieldName.equals("PEDoctor") ) {
			return 26;
		}
		if( strFieldName.equals("REPEDate") ) {
			return 27;
		}
		if( strFieldName.equals("PEReason") ) {
			return 28;
		}
		if( strFieldName.equals("MasculineFlag") ) {
			return 29;
		}
		if( strFieldName.equals("CustomerType") ) {
			return 30;
		}
		if( strFieldName.equals("HospitCode") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "BatNo";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "GrpContNo";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "ProposalContNo";
				break;
			case 6:
				strFieldName = "PrtSeq";
				break;
			case 7:
				strFieldName = "AppName";
				break;
			case 8:
				strFieldName = "RiskName";
				break;
			case 9:
				strFieldName = "PEDate";
				break;
			case 10:
				strFieldName = "CustomerNo";
				break;
			case 11:
				strFieldName = "Name";
				break;
			case 12:
				strFieldName = "PEAddress";
				break;
			case 13:
				strFieldName = "PEBeforeCond";
				break;
			case 14:
				strFieldName = "PrintFlag";
				break;
			case 15:
				strFieldName = "ManageCom";
				break;
			case 16:
				strFieldName = "AgentName";
				break;
			case 17:
				strFieldName = "AgentCode";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
				strFieldName = "ModifyTime";
				break;
			case 23:
				strFieldName = "Remark";
				break;
			case 24:
				strFieldName = "PEResult";
				break;
			case 25:
				strFieldName = "GrpPrtSeq";
				break;
			case 26:
				strFieldName = "PEDoctor";
				break;
			case 27:
				strFieldName = "REPEDate";
				break;
			case 28:
				strFieldName = "PEReason";
				break;
			case 29:
				strFieldName = "MasculineFlag";
				break;
			case 30:
				strFieldName = "CustomerType";
				break;
			case 31:
				strFieldName = "HospitCode";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEBeforeCond") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PEDoctor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("REPEDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PEReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MasculineFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitCode") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
