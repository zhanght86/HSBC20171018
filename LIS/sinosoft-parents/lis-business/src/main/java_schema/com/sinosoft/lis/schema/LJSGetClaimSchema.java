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
import com.sinosoft.lis.db.LJSGetClaimDB;

/*
 * <p>ClassName: LJSGetClaimSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务付费
 */
public class LJSGetClaimSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJSGetClaimSchema.class);
	// @Field
	/** 给付通知书号码 */
	private String GetNoticeNo;
	/** 业务类型 */
	private String FeeOperationType;
	/** 子业务类型 */
	private String SubFeeOperationType;
	/** 财务类型 */
	private String FeeFinaType;
	/** 其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 保单号码 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 险类编码 */
	private String KindCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 给付日期 */
	private Date GetDate;
	/** 赔付金额 */
	private double Pay;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 流水号 */
	private String SerialNo;
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
	/** 币别 */
	private String Currency;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 净费用 */
	private double NetAmount;
	/** 税额 */
	private double TaxAmount;
	/** 税率 */
	private double Tax;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJSGetClaimSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[8];
		pk[0] = "GetNoticeNo";
		pk[1] = "FeeOperationType";
		pk[2] = "SubFeeOperationType";
		pk[3] = "FeeFinaType";
		pk[4] = "PolNo";
		pk[5] = "DutyCode";
		pk[6] = "GetDutyKind";
		pk[7] = "GetDutyCode";

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
		LJSGetClaimSchema cloned = (LJSGetClaimSchema)super.clone();
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
	* 对应赔付明���表的赔案号。
	*/
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
	}
	/**
	* A代表来自赔付明细表的数据<p>
	* B代表来自预付的数据
	*/
	public String getFeeOperationType()
	{
		return FeeOperationType;
	}
	public void setFeeOperationType(String aFeeOperationType)
	{
		FeeOperationType = aFeeOperationType;
	}
	/**
	* 该字段用来区分一个保全项目中可能产生多个业务类型对应同一种财务类型的情况<p>
	* 如退保时退费可能包括退保费,可能包括退的应领未领的金额.<p>
	* 该字段对应于ldcode1表里描述的保全财务业务类型对应关系里的业务类型
	*/
	public String getSubFeeOperationType()
	{
		return SubFeeOperationType;
	}
	public void setSubFeeOperationType(String aSubFeeOperationType)
	{
		SubFeeOperationType = aSubFeeOperationType;
	}
	/**
	* 对应以前的tf_je_type
	*/
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		FeeFinaType = aFeeFinaType;
	}
	/**
	* 合同号分为：<p>
	* 个单合同号<p>
	* 集体合同号
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 0 ---表示生存领取对应的合同号<p>
	* 1 ---表示生存领取对应的集体保单号<p>
	* 2 ---表示生存领取对应的个人保单号<p>
	* 3 ---表示批改号<p>
	* 4 ---暂交费退费,对应暂交费退费表的给付通知书号<p>
	* 5 ---表示赔付应收表中的给付通知书号（就是赔案号）
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
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
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
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
	* 对应kind_pay_li 中一种领取方式如有十年固定年金，无十年固定年金，总共领5次或领十次等
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
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
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
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
	public String getGetDate()
	{
		if( GetDate != null )
			return fDate.getString(GetDate);
		else
			return null;
	}
	public void setGetDate(Date aGetDate)
	{
		GetDate = aGetDate;
	}
	public void setGetDate(String aGetDate)
	{
		if (aGetDate != null && !aGetDate.equals("") )
		{
			GetDate = fDate.getDate( aGetDate );
		}
		else
			GetDate = null;
	}

	public double getPay()
	{
		return Pay;
	}
	public void setPay(double aPay)
	{
		Pay = aPay;
	}
	public void setPay(String aPay)
	{
		if (aPay != null && !aPay.equals(""))
		{
			Double tDouble = new Double(aPay);
			double d = tDouble.doubleValue();
			Pay = d;
		}
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/**
	* 对不同的代理机构号进行内部的分类。
	*/
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
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
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		ModifyOperator = aModifyOperator;
	}
	public double getNetAmount()
	{
		return NetAmount;
	}
	public void setNetAmount(double aNetAmount)
	{
		NetAmount = aNetAmount;
	}
	public void setNetAmount(String aNetAmount)
	{
		if (aNetAmount != null && !aNetAmount.equals(""))
		{
			Double tDouble = new Double(aNetAmount);
			double d = tDouble.doubleValue();
			NetAmount = d;
		}
	}

	public double getTaxAmount()
	{
		return TaxAmount;
	}
	public void setTaxAmount(double aTaxAmount)
	{
		TaxAmount = aTaxAmount;
	}
	public void setTaxAmount(String aTaxAmount)
	{
		if (aTaxAmount != null && !aTaxAmount.equals(""))
		{
			Double tDouble = new Double(aTaxAmount);
			double d = tDouble.doubleValue();
			TaxAmount = d;
		}
	}

	public double getTax()
	{
		return Tax;
	}
	public void setTax(double aTax)
	{
		Tax = aTax;
	}
	public void setTax(String aTax)
	{
		if (aTax != null && !aTax.equals(""))
		{
			Double tDouble = new Double(aTax);
			double d = tDouble.doubleValue();
			Tax = d;
		}
	}


	/**
	* 使用另外一个 LJSGetClaimSchema 对象给 Schema 赋值
	* @param: aLJSGetClaimSchema LJSGetClaimSchema
	**/
	public void setSchema(LJSGetClaimSchema aLJSGetClaimSchema)
	{
		this.GetNoticeNo = aLJSGetClaimSchema.getGetNoticeNo();
		this.FeeOperationType = aLJSGetClaimSchema.getFeeOperationType();
		this.SubFeeOperationType = aLJSGetClaimSchema.getSubFeeOperationType();
		this.FeeFinaType = aLJSGetClaimSchema.getFeeFinaType();
		this.OtherNo = aLJSGetClaimSchema.getOtherNo();
		this.OtherNoType = aLJSGetClaimSchema.getOtherNoType();
		this.SaleChnl = aLJSGetClaimSchema.getSaleChnl();
		this.GrpContNo = aLJSGetClaimSchema.getGrpContNo();
		this.ContNo = aLJSGetClaimSchema.getContNo();
		this.GrpPolNo = aLJSGetClaimSchema.getGrpPolNo();
		this.PolNo = aLJSGetClaimSchema.getPolNo();
		this.DutyCode = aLJSGetClaimSchema.getDutyCode();
		this.GetDutyKind = aLJSGetClaimSchema.getGetDutyKind();
		this.GetDutyCode = aLJSGetClaimSchema.getGetDutyCode();
		this.KindCode = aLJSGetClaimSchema.getKindCode();
		this.RiskCode = aLJSGetClaimSchema.getRiskCode();
		this.RiskVersion = aLJSGetClaimSchema.getRiskVersion();
		this.AgentCode = aLJSGetClaimSchema.getAgentCode();
		this.AgentGroup = aLJSGetClaimSchema.getAgentGroup();
		this.GetDate = fDate.getDate( aLJSGetClaimSchema.getGetDate());
		this.Pay = aLJSGetClaimSchema.getPay();
		this.ManageCom = aLJSGetClaimSchema.getManageCom();
		this.AgentCom = aLJSGetClaimSchema.getAgentCom();
		this.AgentType = aLJSGetClaimSchema.getAgentType();
		this.SerialNo = aLJSGetClaimSchema.getSerialNo();
		this.Operator = aLJSGetClaimSchema.getOperator();
		this.MakeDate = fDate.getDate( aLJSGetClaimSchema.getMakeDate());
		this.MakeTime = aLJSGetClaimSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJSGetClaimSchema.getModifyDate());
		this.ModifyTime = aLJSGetClaimSchema.getModifyTime();
		this.Currency = aLJSGetClaimSchema.getCurrency();
		this.ComCode = aLJSGetClaimSchema.getComCode();
		this.ModifyOperator = aLJSGetClaimSchema.getModifyOperator();
		this.NetAmount = aLJSGetClaimSchema.getNetAmount();
		this.TaxAmount = aLJSGetClaimSchema.getTaxAmount();
		this.Tax = aLJSGetClaimSchema.getTax();
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
			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("FeeOperationType") == null )
				this.FeeOperationType = null;
			else
				this.FeeOperationType = rs.getString("FeeOperationType").trim();

			if( rs.getString("SubFeeOperationType") == null )
				this.SubFeeOperationType = null;
			else
				this.SubFeeOperationType = rs.getString("SubFeeOperationType").trim();

			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

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

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			this.GetDate = rs.getDate("GetDate");
			this.Pay = rs.getDouble("Pay");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

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

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.NetAmount = rs.getDouble("NetAmount");
			this.TaxAmount = rs.getDouble("TaxAmount");
			this.Tax = rs.getDouble("Tax");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJSGetClaim表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSGetClaimSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJSGetClaimSchema getSchema()
	{
		LJSGetClaimSchema aLJSGetClaimSchema = new LJSGetClaimSchema();
		aLJSGetClaimSchema.setSchema(this);
		return aLJSGetClaimSchema;
	}

	public LJSGetClaimDB getDB()
	{
		LJSGetClaimDB aDBOper = new LJSGetClaimDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSGetClaim描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Pay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSGetClaim>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubFeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			GetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			Pay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSGetClaimSchema";
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
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeOperationType));
		}
		if (FCode.equalsIgnoreCase("SubFeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFeeOperationType));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
		}
		if (FCode.equalsIgnoreCase("Pay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Pay));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("NetAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetAmount));
		}
		if (FCode.equalsIgnoreCase("TaxAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaxAmount));
		}
		if (FCode.equalsIgnoreCase("Tax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Tax));
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
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeOperationType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubFeeOperationType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
				break;
			case 20:
				strFieldValue = String.valueOf(Pay);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 33:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 34:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 35:
				strFieldValue = String.valueOf(Tax);
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

		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeOperationType = FValue.trim();
			}
			else
				FeeOperationType = null;
		}
		if (FCode.equalsIgnoreCase("SubFeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFeeOperationType = FValue.trim();
			}
			else
				SubFeeOperationType = null;
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeFinaType = FValue.trim();
			}
			else
				FeeFinaType = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
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
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetDate = fDate.getDate( FValue );
			}
			else
				GetDate = null;
		}
		if (FCode.equalsIgnoreCase("Pay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Pay = d;
			}
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
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		if (FCode.equalsIgnoreCase("NetAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NetAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("TaxAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TaxAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("Tax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Tax = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJSGetClaimSchema other = (LJSGetClaimSchema)otherObject;
		return
			GetNoticeNo.equals(other.getGetNoticeNo())
			&& FeeOperationType.equals(other.getFeeOperationType())
			&& SubFeeOperationType.equals(other.getSubFeeOperationType())
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& fDate.getString(GetDate).equals(other.getGetDate())
			&& Pay == other.getPay()
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& SerialNo.equals(other.getSerialNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Currency.equals(other.getCurrency())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& NetAmount == other.getNetAmount()
			&& TaxAmount == other.getTaxAmount()
			&& Tax == other.getTax();
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return 0;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return 1;
		}
		if( strFieldName.equals("SubFeeOperationType") ) {
			return 2;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 4;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 5;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 6;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 7;
		}
		if( strFieldName.equals("ContNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 9;
		}
		if( strFieldName.equals("PolNo") ) {
			return 10;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 11;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 12;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 13;
		}
		if( strFieldName.equals("KindCode") ) {
			return 14;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 15;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 17;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 18;
		}
		if( strFieldName.equals("GetDate") ) {
			return 19;
		}
		if( strFieldName.equals("Pay") ) {
			return 20;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 21;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 22;
		}
		if( strFieldName.equals("AgentType") ) {
			return 23;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 24;
		}
		if( strFieldName.equals("Operator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 29;
		}
		if( strFieldName.equals("Currency") ) {
			return 30;
		}
		if( strFieldName.equals("ComCode") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 32;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 33;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 34;
		}
		if( strFieldName.equals("Tax") ) {
			return 35;
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
				strFieldName = "GetNoticeNo";
				break;
			case 1:
				strFieldName = "FeeOperationType";
				break;
			case 2:
				strFieldName = "SubFeeOperationType";
				break;
			case 3:
				strFieldName = "FeeFinaType";
				break;
			case 4:
				strFieldName = "OtherNo";
				break;
			case 5:
				strFieldName = "OtherNoType";
				break;
			case 6:
				strFieldName = "SaleChnl";
				break;
			case 7:
				strFieldName = "GrpContNo";
				break;
			case 8:
				strFieldName = "ContNo";
				break;
			case 9:
				strFieldName = "GrpPolNo";
				break;
			case 10:
				strFieldName = "PolNo";
				break;
			case 11:
				strFieldName = "DutyCode";
				break;
			case 12:
				strFieldName = "GetDutyKind";
				break;
			case 13:
				strFieldName = "GetDutyCode";
				break;
			case 14:
				strFieldName = "KindCode";
				break;
			case 15:
				strFieldName = "RiskCode";
				break;
			case 16:
				strFieldName = "RiskVersion";
				break;
			case 17:
				strFieldName = "AgentCode";
				break;
			case 18:
				strFieldName = "AgentGroup";
				break;
			case 19:
				strFieldName = "GetDate";
				break;
			case 20:
				strFieldName = "Pay";
				break;
			case 21:
				strFieldName = "ManageCom";
				break;
			case 22:
				strFieldName = "AgentCom";
				break;
			case 23:
				strFieldName = "AgentType";
				break;
			case 24:
				strFieldName = "SerialNo";
				break;
			case 25:
				strFieldName = "Operator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "ModifyDate";
				break;
			case 29:
				strFieldName = "ModifyTime";
				break;
			case 30:
				strFieldName = "Currency";
				break;
			case 31:
				strFieldName = "ComCode";
				break;
			case 32:
				strFieldName = "ModifyOperator";
				break;
			case 33:
				strFieldName = "NetAmount";
				break;
			case 34:
				strFieldName = "TaxAmount";
				break;
			case 35:
				strFieldName = "Tax";
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
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
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Pay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
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
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NetAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Tax") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
