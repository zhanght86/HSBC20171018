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
import com.sinosoft.lis.db.LLCasePolicyDB;

/*
 * <p>ClassName: LLCasePolicySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LLCasePolicySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCasePolicySchema.class);
	// @Field
	/** 分案保单类型 */
	private String CasePolType;
	/** 分案号 */
	private String CaseNo;
	/** 立案号 */
	private String RgtNo;
	/** 总单/合同号 */
	private String ContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 保单号 */
	private String PolNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 险种代码 */
	private String RiskCode;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 销售渠道 */
	private String SaleChnl;
	/** 代理人代码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人生日 */
	private Date InsuredBirthday;
	/** 投保人客户号 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 保单状态 */
	private String PolState;
	/** 分案保单状态 */
	private String CasePolState;
	/** 赔付标志 */
	private String ClmFlag;
	/** 赔案号 */
	private String ClmNo;
	/** 拒赔号 */
	private String DeclineNo;
	/** 结案日期 */
	private Date EndCaseDate;
	/** 管理机构 */
	private String MngCom;
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
	/** 保单性质标志 */
	private String PolType;
	/** 集体合同号 */
	private String GrpContNo;
	/** 受理事故号 */
	private String CaseRelaNo;

	public static final int FIELDNUM = 35;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCasePolicySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CaseNo";
		pk[1] = "PolNo";

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
		LLCasePolicySchema cloned = (LLCasePolicySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 包括：投保人出??分案、被保人出险分案<p>
	* 0 --- 被保人案件<p>
	* 1 --- 投保人案件
	*/
	public String getCasePolType()
	{
		return CasePolType;
	}
	public void setCasePolType(String aCasePolType)
	{
		if(aCasePolType!=null && aCasePolType.length()>1)
			throw new IllegalArgumentException("分案保单类型CasePolType值"+aCasePolType+"的长度"+aCasePolType.length()+"大于最大值1");
		CasePolType = aCasePolType;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("总单/合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单号GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单号PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		if(aKindCode!=null && aKindCode.length()>3)
			throw new IllegalArgumentException("险类代码KindCode值"+aKindCode+"的长度"+aKindCode.length()+"大于最大值3");
		KindCode = aKindCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		if(aRiskVer!=null && aRiskVer.length()>8)
			throw new IllegalArgumentException("险种版本号RiskVer值"+aRiskVer+"的长度"+aRiskVer.length()+"大于最大值8");
		RiskVer = aRiskVer;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
		if(aPolMngCom!=null && aPolMngCom.length()>10)
			throw new IllegalArgumentException("保单管理机构PolMngCom值"+aPolMngCom+"的长度"+aPolMngCom.length()+"大于最大值10");
		PolMngCom = aPolMngCom;
	}
	/**
	* 01-团险直销,02-个人营销,03-银行代理<p>
	* 04-兼业代理,05-专业代理,06-经纪公司<p>
	* 07-不计业绩销售渠道,99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		if(aSaleChnl!=null && aSaleChnl.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值2");
		SaleChnl = aSaleChnl;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		if(aAgentCode!=null && aAgentCode.length()>10)
			throw new IllegalArgumentException("代理人代码AgentCode值"+aAgentCode+"的长度"+aAgentCode.length()+"大于最大值10");
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		if(aAgentGroup!=null && aAgentGroup.length()>12)
			throw new IllegalArgumentException("代理人组别AgentGroup值"+aAgentGroup+"的长度"+aAgentGroup.length()+"大于最大值12");
		AgentGroup = aAgentGroup;
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
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		if(aInsuredName!=null && aInsuredName.length()>20)
			throw new IllegalArgumentException("被保人名称InsuredName值"+aInsuredName+"的长度"+aInsuredName.length()+"大于最大值20");
		InsuredName = aInsuredName;
	}
	public String getInsuredSex()
	{
		return InsuredSex;
	}
	public void setInsuredSex(String aInsuredSex)
	{
		if(aInsuredSex!=null && aInsuredSex.length()>1)
			throw new IllegalArgumentException("被保人性别InsuredSex值"+aInsuredSex+"的长度"+aInsuredSex.length()+"大于最大值1");
		InsuredSex = aInsuredSex;
	}
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
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
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>60)
			throw new IllegalArgumentException("投保人名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值60");
		AppntName = aAppntName;
	}
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		if(aPolState!=null && aPolState.length()>10)
			throw new IllegalArgumentException("保单状态PolState值"+aPolState+"的长度"+aPolState.length()+"大于最大值10");
		PolState = aPolState;
	}
	/**
	* 包括：分案保单、拒赔、核算、核赔、赔付结案
	*/
	public String getCasePolState()
	{
		return CasePolState;
	}
	public void setCasePolState(String aCasePolState)
	{
		if(aCasePolState!=null && aCasePolState.length()>1)
			throw new IllegalArgumentException("分案保单状态CasePolState值"+aCasePolState+"的长度"+aCasePolState.length()+"大于最大值1");
		CasePolState = aCasePolState;
	}
	public String getClmFlag()
	{
		return ClmFlag;
	}
	public void setClmFlag(String aClmFlag)
	{
		if(aClmFlag!=null && aClmFlag.length()>1)
			throw new IllegalArgumentException("赔付标志ClmFlag值"+aClmFlag+"的长度"+aClmFlag.length()+"大于最大值1");
		ClmFlag = aClmFlag;
	}
	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getDeclineNo()
	{
		return DeclineNo;
	}
	public void setDeclineNo(String aDeclineNo)
	{
		if(aDeclineNo!=null && aDeclineNo.length()>20)
			throw new IllegalArgumentException("拒赔号DeclineNo值"+aDeclineNo+"的长度"+aDeclineNo.length()+"大于最大值20");
		DeclineNo = aDeclineNo;
	}
	public String getEndCaseDate()
	{
		if( EndCaseDate != null )
			return fDate.getString(EndCaseDate);
		else
			return null;
	}
	public void setEndCaseDate(Date aEndCaseDate)
	{
		EndCaseDate = aEndCaseDate;
	}
	public void setEndCaseDate(String aEndCaseDate)
	{
		if (aEndCaseDate != null && !aEndCaseDate.equals("") )
		{
			EndCaseDate = fDate.getDate( aEndCaseDate );
		}
		else
			EndCaseDate = null;
	}

	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
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
	* 0 -- 临时保单<p>
	* 1 -- 正式保单
	*/
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		if(aPolType!=null && aPolType.length()>1)
			throw new IllegalArgumentException("保单性质标志PolType值"+aPolType+"的长度"+aPolType.length()+"大于最大值1");
		PolType = aPolType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	/**
	* 受理事故号
	*/
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		if(aCaseRelaNo!=null && aCaseRelaNo.length()>20)
			throw new IllegalArgumentException("受理事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}

	/**
	* 使用另外一个 LLCasePolicySchema 对象给 Schema 赋值
	* @param: aLLCasePolicySchema LLCasePolicySchema
	**/
	public void setSchema(LLCasePolicySchema aLLCasePolicySchema)
	{
		this.CasePolType = aLLCasePolicySchema.getCasePolType();
		this.CaseNo = aLLCasePolicySchema.getCaseNo();
		this.RgtNo = aLLCasePolicySchema.getRgtNo();
		this.ContNo = aLLCasePolicySchema.getContNo();
		this.GrpPolNo = aLLCasePolicySchema.getGrpPolNo();
		this.PolNo = aLLCasePolicySchema.getPolNo();
		this.KindCode = aLLCasePolicySchema.getKindCode();
		this.RiskVer = aLLCasePolicySchema.getRiskVer();
		this.RiskCode = aLLCasePolicySchema.getRiskCode();
		this.PolMngCom = aLLCasePolicySchema.getPolMngCom();
		this.SaleChnl = aLLCasePolicySchema.getSaleChnl();
		this.AgentCode = aLLCasePolicySchema.getAgentCode();
		this.AgentGroup = aLLCasePolicySchema.getAgentGroup();
		this.InsuredNo = aLLCasePolicySchema.getInsuredNo();
		this.InsuredName = aLLCasePolicySchema.getInsuredName();
		this.InsuredSex = aLLCasePolicySchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLLCasePolicySchema.getInsuredBirthday());
		this.AppntNo = aLLCasePolicySchema.getAppntNo();
		this.AppntName = aLLCasePolicySchema.getAppntName();
		this.CValiDate = fDate.getDate( aLLCasePolicySchema.getCValiDate());
		this.PolState = aLLCasePolicySchema.getPolState();
		this.CasePolState = aLLCasePolicySchema.getCasePolState();
		this.ClmFlag = aLLCasePolicySchema.getClmFlag();
		this.ClmNo = aLLCasePolicySchema.getClmNo();
		this.DeclineNo = aLLCasePolicySchema.getDeclineNo();
		this.EndCaseDate = fDate.getDate( aLLCasePolicySchema.getEndCaseDate());
		this.MngCom = aLLCasePolicySchema.getMngCom();
		this.Operator = aLLCasePolicySchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCasePolicySchema.getMakeDate());
		this.MakeTime = aLLCasePolicySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCasePolicySchema.getModifyDate());
		this.ModifyTime = aLLCasePolicySchema.getModifyTime();
		this.PolType = aLLCasePolicySchema.getPolType();
		this.GrpContNo = aLLCasePolicySchema.getGrpContNo();
		this.CaseRelaNo = aLLCasePolicySchema.getCaseRelaNo();
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
			if( rs.getString("CasePolType") == null )
				this.CasePolType = null;
			else
				this.CasePolType = rs.getString("CasePolType").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("PolMngCom") == null )
				this.PolMngCom = null;
			else
				this.PolMngCom = rs.getString("PolMngCom").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("CasePolState") == null )
				this.CasePolState = null;
			else
				this.CasePolState = rs.getString("CasePolState").trim();

			if( rs.getString("ClmFlag") == null )
				this.ClmFlag = null;
			else
				this.ClmFlag = rs.getString("ClmFlag").trim();

			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("DeclineNo") == null )
				this.DeclineNo = null;
			else
				this.DeclineNo = rs.getString("DeclineNo").trim();

			this.EndCaseDate = rs.getDate("EndCaseDate");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCasePolicy表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCasePolicySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCasePolicySchema getSchema()
	{
		LLCasePolicySchema aLLCasePolicySchema = new LLCasePolicySchema();
		aLLCasePolicySchema.setSchema(this);
		return aLLCasePolicySchema;
	}

	public LLCasePolicyDB getDB()
	{
		LLCasePolicyDB aDBOper = new LLCasePolicyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCasePolicy描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CasePolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndCaseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCasePolicy>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CasePolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CasePolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ClmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			DeclineNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCasePolicySchema";
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
		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePolType));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMngCom));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("CasePolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePolState));
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFlag));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineNo));
		}
		if (FCode.equalsIgnoreCase("EndCaseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
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
				strFieldValue = StrTool.GBKToUnicode(CasePolType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(CasePolState);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ClmFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(DeclineNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
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

		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePolType = FValue.trim();
			}
			else
				CasePolType = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
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
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolMngCom = FValue.trim();
			}
			else
				PolMngCom = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
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
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
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
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredBirthday = fDate.getDate( FValue );
			}
			else
				InsuredBirthday = null;
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
		}
		if (FCode.equalsIgnoreCase("CasePolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePolState = FValue.trim();
			}
			else
				CasePolState = null;
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFlag = FValue.trim();
			}
			else
				ClmFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeclineNo = FValue.trim();
			}
			else
				DeclineNo = null;
		}
		if (FCode.equalsIgnoreCase("EndCaseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndCaseDate = fDate.getDate( FValue );
			}
			else
				EndCaseDate = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("PolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolType = FValue.trim();
			}
			else
				PolType = null;
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
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCasePolicySchema other = (LLCasePolicySchema)otherObject;
		return
			CasePolType.equals(other.getCasePolType())
			&& CaseNo.equals(other.getCaseNo())
			&& RgtNo.equals(other.getRgtNo())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& PolNo.equals(other.getPolNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskCode.equals(other.getRiskCode())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& PolState.equals(other.getPolState())
			&& CasePolState.equals(other.getCasePolState())
			&& ClmFlag.equals(other.getClmFlag())
			&& ClmNo.equals(other.getClmNo())
			&& DeclineNo.equals(other.getDeclineNo())
			&& fDate.getString(EndCaseDate).equals(other.getEndCaseDate())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PolType.equals(other.getPolType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo());
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
		if( strFieldName.equals("CasePolType") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("PolNo") ) {
			return 5;
		}
		if( strFieldName.equals("KindCode") ) {
			return 6;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 7;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 8;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 9;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 10;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 11;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 13;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 14;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 15;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 16;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 17;
		}
		if( strFieldName.equals("AppntName") ) {
			return 18;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 19;
		}
		if( strFieldName.equals("PolState") ) {
			return 20;
		}
		if( strFieldName.equals("CasePolState") ) {
			return 21;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return 22;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 23;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return 24;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 25;
		}
		if( strFieldName.equals("MngCom") ) {
			return 26;
		}
		if( strFieldName.equals("Operator") ) {
			return 27;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 28;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
		}
		if( strFieldName.equals("PolType") ) {
			return 32;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 33;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 34;
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
				strFieldName = "CasePolType";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "RgtNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "GrpPolNo";
				break;
			case 5:
				strFieldName = "PolNo";
				break;
			case 6:
				strFieldName = "KindCode";
				break;
			case 7:
				strFieldName = "RiskVer";
				break;
			case 8:
				strFieldName = "RiskCode";
				break;
			case 9:
				strFieldName = "PolMngCom";
				break;
			case 10:
				strFieldName = "SaleChnl";
				break;
			case 11:
				strFieldName = "AgentCode";
				break;
			case 12:
				strFieldName = "AgentGroup";
				break;
			case 13:
				strFieldName = "InsuredNo";
				break;
			case 14:
				strFieldName = "InsuredName";
				break;
			case 15:
				strFieldName = "InsuredSex";
				break;
			case 16:
				strFieldName = "InsuredBirthday";
				break;
			case 17:
				strFieldName = "AppntNo";
				break;
			case 18:
				strFieldName = "AppntName";
				break;
			case 19:
				strFieldName = "CValiDate";
				break;
			case 20:
				strFieldName = "PolState";
				break;
			case 21:
				strFieldName = "CasePolState";
				break;
			case 22:
				strFieldName = "ClmFlag";
				break;
			case 23:
				strFieldName = "ClmNo";
				break;
			case 24:
				strFieldName = "DeclineNo";
				break;
			case 25:
				strFieldName = "EndCaseDate";
				break;
			case 26:
				strFieldName = "MngCom";
				break;
			case 27:
				strFieldName = "Operator";
				break;
			case 28:
				strFieldName = "MakeDate";
				break;
			case 29:
				strFieldName = "MakeTime";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
				strFieldName = "ModifyTime";
				break;
			case 32:
				strFieldName = "PolType";
				break;
			case 33:
				strFieldName = "GrpContNo";
				break;
			case 34:
				strFieldName = "CaseRelaNo";
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
		if( strFieldName.equals("CasePolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
