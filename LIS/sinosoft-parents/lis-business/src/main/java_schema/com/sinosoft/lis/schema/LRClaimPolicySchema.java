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
import com.sinosoft.lis.db.LRClaimPolicyDB;

/*
 * <p>ClassName: LRClaimPolicySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRClaimPolicySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRClaimPolicySchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 保单号 */
	private String PolNo;
	/** 再保险公司 */
	private String ReinsureCom;
	/** 再保项目 */
	private String ReinsurItem;
	/** 保单年度 */
	private int InsuredYear;
	/** 立案号 */
	private String RgtNo;
	/** 分案号 */
	private String CaseNo;
	/** 总单/合同号 */
	private String ContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
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
	/** 投保人客户号 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 出险日期 */
	private Date AccidentDate;
	/** 保单状态 */
	private String PolState;
	/** 赔案状态 */
	private String ClmState;
	/** 核算赔付金额 */
	private double StandPay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 理赔员 */
	private String ClmUWer;
	/** 结案日期 */
	private Date EndCaseDate;
	/** 管理机构 */
	private String MngCom;
	/** 分保开始日期 */
	private Date CessStart;
	/** 分保结束日期 */
	private Date CessEnd;
	/** 分保比例 */
	private double CessionRate;
	/** 分保额 */
	private double CessionAmount;
	/** 摊回赔付金额 */
	private double ReturnPay;
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
	/** 险种计算分类 */
	private String RiskCalSort;
	/** 备注 */
	private String Remark;
	/** 协议类型 */
	private String ProtItem;
	/** 特殊标志 */
	private String SpecFlag;
	/** 疾病代码 */
	private String AccResult2;
	/** 转换渠道 */
	private String TransSaleChnl;
	/** 责任代码 */
	private String DutyCode;

	public static final int FIELDNUM = 48;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRClaimPolicySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[8];
		pk[0] = "ClmNo";
		pk[1] = "PolNo";
		pk[2] = "ReinsureCom";
		pk[3] = "ReinsurItem";
		pk[4] = "InsuredYear";
		pk[5] = "GetDutyKind";
		pk[6] = "RiskCalSort";
		pk[7] = "DutyCode";

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
		LRClaimPolicySchema cloned = (LRClaimPolicySchema)super.clone();
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
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getReinsureCom()
	{
		return ReinsureCom;
	}
	public void setReinsureCom(String aReinsureCom)
	{
		ReinsureCom = aReinsureCom;
	}
	/**
	* L--法定分保<p>
	* C--商业分保
	*/
	public String getReinsurItem()
	{
		return ReinsurItem;
	}
	public void setReinsurItem(String aReinsurItem)
	{
		ReinsurItem = aReinsurItem;
	}
	public int getInsuredYear()
	{
		return InsuredYear;
	}
	public void setInsuredYear(int aInsuredYear)
	{
		InsuredYear = aInsuredYear;
	}
	public void setInsuredYear(String aInsuredYear)
	{
		if (aInsuredYear != null && !aInsuredYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuredYear);
			int i = tInteger.intValue();
			InsuredYear = i;
		}
	}

	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	/**
	* #100  意外医疗 #101  意外伤残 #102  意外死 #103  意外医疗津贴 #104  意外高残 #200  疾病医疗 #201  疾病伤残 #202  疾病死亡 #203  疾病医疗津贴 #204  疾病高残
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
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
		SaleChnl = aSaleChnl;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
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

	public String getAccidentDate()
	{
		if( AccidentDate != null )
			return fDate.getString(AccidentDate);
		else
			return null;
	}
	public void setAccidentDate(Date aAccidentDate)
	{
		AccidentDate = aAccidentDate;
	}
	public void setAccidentDate(String aAccidentDate)
	{
		if (aAccidentDate != null && !aAccidentDate.equals("") )
		{
			AccidentDate = fDate.getDate( aAccidentDate );
		}
		else
			AccidentDate = null;
	}

	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
	}
	/**
	* 0:  已立案<p>
	* 1： 已理算<p>
	* 2： 已核赔<p>
	* 3： 已给付<p>
	* 4:  拒赔<p>
	* 5： 调查中
	*/
	public String getClmState()
	{
		return ClmState;
	}
	public void setClmState(String aClmState)
	{
		ClmState = aClmState;
	}
	public double getStandPay()
	{
		return StandPay;
	}
	public void setStandPay(double aStandPay)
	{
		StandPay = aStandPay;
	}
	public void setStandPay(String aStandPay)
	{
		if (aStandPay != null && !aStandPay.equals(""))
		{
			Double tDouble = new Double(aStandPay);
			double d = tDouble.doubleValue();
			StandPay = d;
		}
	}

	public double getRealPay()
	{
		return RealPay;
	}
	public void setRealPay(double aRealPay)
	{
		RealPay = aRealPay;
	}
	public void setRealPay(String aRealPay)
	{
		if (aRealPay != null && !aRealPay.equals(""))
		{
			Double tDouble = new Double(aRealPay);
			double d = tDouble.doubleValue();
			RealPay = d;
		}
	}

	public String getClmUWer()
	{
		return ClmUWer;
	}
	public void setClmUWer(String aClmUWer)
	{
		ClmUWer = aClmUWer;
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
		MngCom = aMngCom;
	}
	public String getCessStart()
	{
		if( CessStart != null )
			return fDate.getString(CessStart);
		else
			return null;
	}
	public void setCessStart(Date aCessStart)
	{
		CessStart = aCessStart;
	}
	public void setCessStart(String aCessStart)
	{
		if (aCessStart != null && !aCessStart.equals("") )
		{
			CessStart = fDate.getDate( aCessStart );
		}
		else
			CessStart = null;
	}

	public String getCessEnd()
	{
		if( CessEnd != null )
			return fDate.getString(CessEnd);
		else
			return null;
	}
	public void setCessEnd(Date aCessEnd)
	{
		CessEnd = aCessEnd;
	}
	public void setCessEnd(String aCessEnd)
	{
		if (aCessEnd != null && !aCessEnd.equals("") )
		{
			CessEnd = fDate.getDate( aCessEnd );
		}
		else
			CessEnd = null;
	}

	public double getCessionRate()
	{
		return CessionRate;
	}
	public void setCessionRate(double aCessionRate)
	{
		CessionRate = aCessionRate;
	}
	public void setCessionRate(String aCessionRate)
	{
		if (aCessionRate != null && !aCessionRate.equals(""))
		{
			Double tDouble = new Double(aCessionRate);
			double d = tDouble.doubleValue();
			CessionRate = d;
		}
	}

	public double getCessionAmount()
	{
		return CessionAmount;
	}
	public void setCessionAmount(double aCessionAmount)
	{
		CessionAmount = aCessionAmount;
	}
	public void setCessionAmount(String aCessionAmount)
	{
		if (aCessionAmount != null && !aCessionAmount.equals(""))
		{
			Double tDouble = new Double(aCessionAmount);
			double d = tDouble.doubleValue();
			CessionAmount = d;
		}
	}

	public double getReturnPay()
	{
		return ReturnPay;
	}
	public void setReturnPay(double aReturnPay)
	{
		ReturnPay = aReturnPay;
	}
	public void setReturnPay(String aReturnPay)
	{
		if (aReturnPay != null && !aReturnPay.equals(""))
		{
			Double tDouble = new Double(aReturnPay);
			double d = tDouble.doubleValue();
			ReturnPay = d;
		}
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
		PolType = aPolType;
	}
	public String getRiskCalSort()
	{
		return RiskCalSort;
	}
	public void setRiskCalSort(String aRiskCalSort)
	{
		RiskCalSort = aRiskCalSort;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* T--合同分保<p>
	* F－临时分保
	*/
	public String getProtItem()
	{
		return ProtItem;
	}
	public void setProtItem(String aProtItem)
	{
		ProtItem = aProtItem;
	}
	/**
	* 1。其它（一般是合同分保）<p>
	* 2。临分<p>
	* 3。自留
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		SpecFlag = aSpecFlag;
	}
	public String getAccResult2()
	{
		return AccResult2;
	}
	public void setAccResult2(String aAccResult2)
	{
		AccResult2 = aAccResult2;
	}
	public String getTransSaleChnl()
	{
		return TransSaleChnl;
	}
	public void setTransSaleChnl(String aTransSaleChnl)
	{
		TransSaleChnl = aTransSaleChnl;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}

	/**
	* 使用另外一个 LRClaimPolicySchema 对象给 Schema 赋值
	* @param: aLRClaimPolicySchema LRClaimPolicySchema
	**/
	public void setSchema(LRClaimPolicySchema aLRClaimPolicySchema)
	{
		this.ClmNo = aLRClaimPolicySchema.getClmNo();
		this.PolNo = aLRClaimPolicySchema.getPolNo();
		this.ReinsureCom = aLRClaimPolicySchema.getReinsureCom();
		this.ReinsurItem = aLRClaimPolicySchema.getReinsurItem();
		this.InsuredYear = aLRClaimPolicySchema.getInsuredYear();
		this.RgtNo = aLRClaimPolicySchema.getRgtNo();
		this.CaseNo = aLRClaimPolicySchema.getCaseNo();
		this.ContNo = aLRClaimPolicySchema.getContNo();
		this.GrpPolNo = aLRClaimPolicySchema.getGrpPolNo();
		this.GetDutyKind = aLRClaimPolicySchema.getGetDutyKind();
		this.KindCode = aLRClaimPolicySchema.getKindCode();
		this.RiskCode = aLRClaimPolicySchema.getRiskCode();
		this.RiskVer = aLRClaimPolicySchema.getRiskVer();
		this.PolMngCom = aLRClaimPolicySchema.getPolMngCom();
		this.SaleChnl = aLRClaimPolicySchema.getSaleChnl();
		this.AgentCode = aLRClaimPolicySchema.getAgentCode();
		this.AgentGroup = aLRClaimPolicySchema.getAgentGroup();
		this.InsuredNo = aLRClaimPolicySchema.getInsuredNo();
		this.InsuredName = aLRClaimPolicySchema.getInsuredName();
		this.AppntNo = aLRClaimPolicySchema.getAppntNo();
		this.AppntName = aLRClaimPolicySchema.getAppntName();
		this.CValiDate = fDate.getDate( aLRClaimPolicySchema.getCValiDate());
		this.AccidentDate = fDate.getDate( aLRClaimPolicySchema.getAccidentDate());
		this.PolState = aLRClaimPolicySchema.getPolState();
		this.ClmState = aLRClaimPolicySchema.getClmState();
		this.StandPay = aLRClaimPolicySchema.getStandPay();
		this.RealPay = aLRClaimPolicySchema.getRealPay();
		this.ClmUWer = aLRClaimPolicySchema.getClmUWer();
		this.EndCaseDate = fDate.getDate( aLRClaimPolicySchema.getEndCaseDate());
		this.MngCom = aLRClaimPolicySchema.getMngCom();
		this.CessStart = fDate.getDate( aLRClaimPolicySchema.getCessStart());
		this.CessEnd = fDate.getDate( aLRClaimPolicySchema.getCessEnd());
		this.CessionRate = aLRClaimPolicySchema.getCessionRate();
		this.CessionAmount = aLRClaimPolicySchema.getCessionAmount();
		this.ReturnPay = aLRClaimPolicySchema.getReturnPay();
		this.Operator = aLRClaimPolicySchema.getOperator();
		this.MakeDate = fDate.getDate( aLRClaimPolicySchema.getMakeDate());
		this.MakeTime = aLRClaimPolicySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLRClaimPolicySchema.getModifyDate());
		this.ModifyTime = aLRClaimPolicySchema.getModifyTime();
		this.PolType = aLRClaimPolicySchema.getPolType();
		this.RiskCalSort = aLRClaimPolicySchema.getRiskCalSort();
		this.Remark = aLRClaimPolicySchema.getRemark();
		this.ProtItem = aLRClaimPolicySchema.getProtItem();
		this.SpecFlag = aLRClaimPolicySchema.getSpecFlag();
		this.AccResult2 = aLRClaimPolicySchema.getAccResult2();
		this.TransSaleChnl = aLRClaimPolicySchema.getTransSaleChnl();
		this.DutyCode = aLRClaimPolicySchema.getDutyCode();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ReinsureCom") == null )
				this.ReinsureCom = null;
			else
				this.ReinsureCom = rs.getString("ReinsureCom").trim();

			if( rs.getString("ReinsurItem") == null )
				this.ReinsurItem = null;
			else
				this.ReinsurItem = rs.getString("ReinsurItem").trim();

			this.InsuredYear = rs.getInt("InsuredYear");
			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

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

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.AccidentDate = rs.getDate("AccidentDate");
			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("ClmState") == null )
				this.ClmState = null;
			else
				this.ClmState = rs.getString("ClmState").trim();

			this.StandPay = rs.getDouble("StandPay");
			this.RealPay = rs.getDouble("RealPay");
			if( rs.getString("ClmUWer") == null )
				this.ClmUWer = null;
			else
				this.ClmUWer = rs.getString("ClmUWer").trim();

			this.EndCaseDate = rs.getDate("EndCaseDate");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			this.CessStart = rs.getDate("CessStart");
			this.CessEnd = rs.getDate("CessEnd");
			this.CessionRate = rs.getDouble("CessionRate");
			this.CessionAmount = rs.getDouble("CessionAmount");
			this.ReturnPay = rs.getDouble("ReturnPay");
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

			if( rs.getString("RiskCalSort") == null )
				this.RiskCalSort = null;
			else
				this.RiskCalSort = rs.getString("RiskCalSort").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ProtItem") == null )
				this.ProtItem = null;
			else
				this.ProtItem = rs.getString("ProtItem").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("AccResult2") == null )
				this.AccResult2 = null;
			else
				this.AccResult2 = rs.getString("AccResult2").trim();

			if( rs.getString("TransSaleChnl") == null )
				this.TransSaleChnl = null;
			else
				this.TransSaleChnl = rs.getString("TransSaleChnl").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRClaimPolicy表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRClaimPolicySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRClaimPolicySchema getSchema()
	{
		LRClaimPolicySchema aLRClaimPolicySchema = new LRClaimPolicySchema();
		aLRClaimPolicySchema.setSchema(this);
		return aLRClaimPolicySchema;
	}

	public LRClaimPolicyDB getDB()
	{
		LRClaimPolicyDB aDBOper = new LRClaimPolicyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRClaimPolicy描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsurItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccidentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndCaseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CessStart ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CessEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReturnPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransSaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRClaimPolicy>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ReinsureCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReinsurItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InsuredYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ClmState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			ClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CessStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			CessEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			CessionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			CessionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			ReturnPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			RiskCalSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ProtItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			TransSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRClaimPolicySchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureCom));
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurItem));
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredYear));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
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
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmState));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWer));
		}
		if (FCode.equalsIgnoreCase("EndCaseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("CessStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCessStart()));
		}
		if (FCode.equalsIgnoreCase("CessEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCessEnd()));
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionRate));
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionAmount));
		}
		if (FCode.equalsIgnoreCase("ReturnPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnPay));
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
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalSort));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ProtItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtItem));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult2));
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSaleChnl));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ReinsureCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReinsurItem);
				break;
			case 4:
				strFieldValue = String.valueOf(InsuredYear);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ClmState);
				break;
			case 25:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 26:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ClmUWer);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCessStart()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCessEnd()));
				break;
			case 32:
				strFieldValue = String.valueOf(CessionRate);
				break;
			case 33:
				strFieldValue = String.valueOf(CessionAmount);
				break;
			case 34:
				strFieldValue = String.valueOf(ReturnPay);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(RiskCalSort);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ProtItem);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(TransSaleChnl);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureCom = FValue.trim();
			}
			else
				ReinsureCom = null;
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsurItem = FValue.trim();
			}
			else
				ReinsurItem = null;
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredYear = i;
			}
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccidentDate = fDate.getDate( FValue );
			}
			else
				AccidentDate = null;
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
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmState = FValue.trim();
			}
			else
				ClmState = null;
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWer = FValue.trim();
			}
			else
				ClmUWer = null;
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
		if (FCode.equalsIgnoreCase("CessStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CessStart = fDate.getDate( FValue );
			}
			else
				CessStart = null;
		}
		if (FCode.equalsIgnoreCase("CessEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CessEnd = fDate.getDate( FValue );
			}
			else
				CessEnd = null;
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("ReturnPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ReturnPay = d;
			}
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
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalSort = FValue.trim();
			}
			else
				RiskCalSort = null;
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
		if (FCode.equalsIgnoreCase("ProtItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtItem = FValue.trim();
			}
			else
				ProtItem = null;
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
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult2 = FValue.trim();
			}
			else
				AccResult2 = null;
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransSaleChnl = FValue.trim();
			}
			else
				TransSaleChnl = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRClaimPolicySchema other = (LRClaimPolicySchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& PolNo.equals(other.getPolNo())
			&& ReinsureCom.equals(other.getReinsureCom())
			&& ReinsurItem.equals(other.getReinsurItem())
			&& InsuredYear == other.getInsuredYear()
			&& RgtNo.equals(other.getRgtNo())
			&& CaseNo.equals(other.getCaseNo())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(AccidentDate).equals(other.getAccidentDate())
			&& PolState.equals(other.getPolState())
			&& ClmState.equals(other.getClmState())
			&& StandPay == other.getStandPay()
			&& RealPay == other.getRealPay()
			&& ClmUWer.equals(other.getClmUWer())
			&& fDate.getString(EndCaseDate).equals(other.getEndCaseDate())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(CessStart).equals(other.getCessStart())
			&& fDate.getString(CessEnd).equals(other.getCessEnd())
			&& CessionRate == other.getCessionRate()
			&& CessionAmount == other.getCessionAmount()
			&& ReturnPay == other.getReturnPay()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PolType.equals(other.getPolType())
			&& RiskCalSort.equals(other.getRiskCalSort())
			&& Remark.equals(other.getRemark())
			&& ProtItem.equals(other.getProtItem())
			&& SpecFlag.equals(other.getSpecFlag())
			&& AccResult2.equals(other.getAccResult2())
			&& TransSaleChnl.equals(other.getTransSaleChnl())
			&& DutyCode.equals(other.getDutyCode());
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
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return 2;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return 3;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return 4;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 5;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 6;
		}
		if( strFieldName.equals("ContNo") ) {
			return 7;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 9;
		}
		if( strFieldName.equals("KindCode") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 12;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 13;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 14;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 15;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 16;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 17;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 18;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 19;
		}
		if( strFieldName.equals("AppntName") ) {
			return 20;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 21;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 22;
		}
		if( strFieldName.equals("PolState") ) {
			return 23;
		}
		if( strFieldName.equals("ClmState") ) {
			return 24;
		}
		if( strFieldName.equals("StandPay") ) {
			return 25;
		}
		if( strFieldName.equals("RealPay") ) {
			return 26;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return 27;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 28;
		}
		if( strFieldName.equals("MngCom") ) {
			return 29;
		}
		if( strFieldName.equals("CessStart") ) {
			return 30;
		}
		if( strFieldName.equals("CessEnd") ) {
			return 31;
		}
		if( strFieldName.equals("CessionRate") ) {
			return 32;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return 33;
		}
		if( strFieldName.equals("ReturnPay") ) {
			return 34;
		}
		if( strFieldName.equals("Operator") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 39;
		}
		if( strFieldName.equals("PolType") ) {
			return 40;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return 41;
		}
		if( strFieldName.equals("Remark") ) {
			return 42;
		}
		if( strFieldName.equals("ProtItem") ) {
			return 43;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 44;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 45;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return 46;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 47;
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
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "ReinsureCom";
				break;
			case 3:
				strFieldName = "ReinsurItem";
				break;
			case 4:
				strFieldName = "InsuredYear";
				break;
			case 5:
				strFieldName = "RgtNo";
				break;
			case 6:
				strFieldName = "CaseNo";
				break;
			case 7:
				strFieldName = "ContNo";
				break;
			case 8:
				strFieldName = "GrpPolNo";
				break;
			case 9:
				strFieldName = "GetDutyKind";
				break;
			case 10:
				strFieldName = "KindCode";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "RiskVer";
				break;
			case 13:
				strFieldName = "PolMngCom";
				break;
			case 14:
				strFieldName = "SaleChnl";
				break;
			case 15:
				strFieldName = "AgentCode";
				break;
			case 16:
				strFieldName = "AgentGroup";
				break;
			case 17:
				strFieldName = "InsuredNo";
				break;
			case 18:
				strFieldName = "InsuredName";
				break;
			case 19:
				strFieldName = "AppntNo";
				break;
			case 20:
				strFieldName = "AppntName";
				break;
			case 21:
				strFieldName = "CValiDate";
				break;
			case 22:
				strFieldName = "AccidentDate";
				break;
			case 23:
				strFieldName = "PolState";
				break;
			case 24:
				strFieldName = "ClmState";
				break;
			case 25:
				strFieldName = "StandPay";
				break;
			case 26:
				strFieldName = "RealPay";
				break;
			case 27:
				strFieldName = "ClmUWer";
				break;
			case 28:
				strFieldName = "EndCaseDate";
				break;
			case 29:
				strFieldName = "MngCom";
				break;
			case 30:
				strFieldName = "CessStart";
				break;
			case 31:
				strFieldName = "CessEnd";
				break;
			case 32:
				strFieldName = "CessionRate";
				break;
			case 33:
				strFieldName = "CessionAmount";
				break;
			case 34:
				strFieldName = "ReturnPay";
				break;
			case 35:
				strFieldName = "Operator";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyDate";
				break;
			case 39:
				strFieldName = "ModifyTime";
				break;
			case 40:
				strFieldName = "PolType";
				break;
			case 41:
				strFieldName = "RiskCalSort";
				break;
			case 42:
				strFieldName = "Remark";
				break;
			case 43:
				strFieldName = "ProtItem";
				break;
			case 44:
				strFieldName = "SpecFlag";
				break;
			case 45:
				strFieldName = "AccResult2";
				break;
			case 46:
				strFieldName = "TransSaleChnl";
				break;
			case 47:
				strFieldName = "DutyCode";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
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
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CessStart") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CessEnd") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CessionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ReturnPay") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("RiskCalSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProtItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
