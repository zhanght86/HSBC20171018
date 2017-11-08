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
import com.sinosoft.lis.db.LJTempFeeDB;

/*
 * <p>ClassName: LJTempFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJTempFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJTempFeeSchema.class);
	// @Field
	/** 暂交费收据号码 */
	private String TempFeeNo;
	/** 暂交费收据号类型 */
	private String TempFeeType;
	/** 险种编码 */
	private String RiskCode;
	/** 交费间隔 */
	private int PayIntv;
	/** 对应其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 交费金额 */
	private double PayMoney;
	/** 交费日期 */
	private Date PayDate;
	/** 到帐日期 */
	private Date EnterAccDate;
	/** 确认日期 */
	private Date ConfDate;
	/** 财务确认操作日期 */
	private Date ConfMakeDate;
	/** 财务确认操作时间 */
	private String ConfMakeTime;
	/** 销售渠道 */
	private String SaleChnl;
	/** 交费机构 */
	private String ManageCom;
	/** 管理机构 */
	private String PolicyCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 投保人名称 */
	private String APPntName;
	/** 代理人组别 */
	private String AgentGroup;
	/** 代理人编码 */
	private String AgentCode;
	/** 是否核销标志 */
	private String ConfFlag;
	/** 流水号 */
	private String SerialNo;
	/** 操作员 */
	private String Operator;
	/** 状态 */
	private String State;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 保单所属机构 */
	private String ContCom;
	/** 交费止期 */
	private int PayEndYear;
	/** 业务状态 */
	private String OperState;
	/** 收据类型 */
	private String TempFeeNoType;
	/** 预收标保 */
	private double StandPrem;
	/** 备注 */
	private String Remark;
	/** 代理人所在区 */
	private String Distict;
	/** 代理人所在部 */
	private String Department;
	/** 代理人所在组 */
	private String BranchCode;
	/** 险种明细 */
	private String RiskType;
	/** 交费年期 */
	private int PayYears;
	/** 币别 */
	private String Currency;
	/** 交费模式 */
	private String PayMode;
	/** 已用金额 */
	private double UsedMoney;
	/** 锁定金额 */
	private double LockMoney;
	/** 申请操作人 */
	private String AppOperator;
	/** 申请日期 */
	private Date AppDate;
	/** 申请时间 */
	private String AppTime;
	/** 录入操作人 */
	private String InputOperator;
	/** 录入日期 */
	private Date InputDate;
	/** 录入时间 */
	private String InputTime;
	/** 录入结论 */
	private String InputConclusion;
	/** 录入结论描述 */
	private String InputDesc;
	/** 审核操作人 */
	private String ConfirmOperator;
	/** 审核日期 */
	private Date ConfirmDate;
	/** 审核时间 */
	private String ConfirmTime;
	/** 审核结论 */
	private String ConfirmConclusion;
	/** 审核结论描述 */
	private String ConfirmDesc;
	/** 撤销操作人 */
	private String CancelOperator;
	/** 撤销日期 */
	private Date CancelDate;
	/** 撤销时间 */
	private String CancelTime;
	/** 撤销原因 */
	private String CancelDesc;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 62;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJTempFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TempFeeNo";
		pk[1] = "TempFeeType";
		pk[2] = "RiskCode";
		pk[3] = "Currency";

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
		LJTempFeeSchema cloned = (LJTempFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
	}
	/**
	* 1--新单交费<p>
	* 2--续期催收交费<p>
	* 3--预收交费<p>
	* 4--保全交费<p>
	* 5--预存保费（暂时没有相关业务）<p>
	* 6--理赔收费<p>
	* 7--红利抵交保费、增额缴清方式收费<p>
	* 8--续期非催收交费<p>
	* 9--结算缴费（暂时没有相关业务）
	*/
	public String getTempFeeType()
	{
		return TempFeeType;
	}
	public void setTempFeeType(String aTempFeeType)
	{
		TempFeeType = aTempFeeType;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 交费间隔<p>
	* -1 -- 不定期交,<p>
	* 0  -- 趸交,<p>
	* 1  -- 月交<p>
	* 3  -- 季交<p>
	* 6  -- 半年交<p>
	* 12 -- 年交
	*/
	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 0--交费对应的个单合同号<p>
	* 1--交费对应的集体合同号<p>
	* 2--理赔收费对应的理赔赔案号<p>
	* 4--新单交费对应的个、团单的印刷号<p>
	* 5--交费对应的客户号<p>
	* 10--保全收费对应的保全受理号
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	public double getPayMoney()
	{
		return PayMoney;
	}
	public void setPayMoney(double aPayMoney)
	{
		PayMoney = aPayMoney;
	}
	public void setPayMoney(String aPayMoney)
	{
		if (aPayMoney != null && !aPayMoney.equals(""))
		{
			Double tDouble = new Double(aPayMoney);
			double d = tDouble.doubleValue();
			PayMoney = d;
		}
	}

	public String getPayDate()
	{
		if( PayDate != null )
			return fDate.getString(PayDate);
		else
			return null;
	}
	public void setPayDate(Date aPayDate)
	{
		PayDate = aPayDate;
	}
	public void setPayDate(String aPayDate)
	{
		if (aPayDate != null && !aPayDate.equals("") )
		{
			PayDate = fDate.getDate( aPayDate );
		}
		else
			PayDate = null;
	}

	public String getEnterAccDate()
	{
		if( EnterAccDate != null )
			return fDate.getString(EnterAccDate);
		else
			return null;
	}
	public void setEnterAccDate(Date aEnterAccDate)
	{
		EnterAccDate = aEnterAccDate;
	}
	public void setEnterAccDate(String aEnterAccDate)
	{
		if (aEnterAccDate != null && !aEnterAccDate.equals("") )
		{
			EnterAccDate = fDate.getDate( aEnterAccDate );
		}
		else
			EnterAccDate = null;
	}

	/**
	* 指财务核销日期。
	*/
	public String getConfDate()
	{
		if( ConfDate != null )
			return fDate.getString(ConfDate);
		else
			return null;
	}
	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}
	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
	}

	/**
	* 备用<p>
	* <p>
	* 表示给付后，财务的实际确认日期。<p>
	* 财务做财务给付确认操作的日期。
	*/
	public String getConfMakeDate()
	{
		if( ConfMakeDate != null )
			return fDate.getString(ConfMakeDate);
		else
			return null;
	}
	public void setConfMakeDate(Date aConfMakeDate)
	{
		ConfMakeDate = aConfMakeDate;
	}
	public void setConfMakeDate(String aConfMakeDate)
	{
		if (aConfMakeDate != null && !aConfMakeDate.equals("") )
		{
			ConfMakeDate = fDate.getDate( aConfMakeDate );
		}
		else
			ConfMakeDate = null;
	}

	public String getConfMakeTime()
	{
		return ConfMakeTime;
	}
	public void setConfMakeTime(String aConfMakeTime)
	{
		ConfMakeTime = aConfMakeTime;
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getPolicyCom()
	{
		return PolicyCom;
	}
	public void setPolicyCom(String aPolicyCom)
	{
		PolicyCom = aPolicyCom;
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
	public String getAPPntName()
	{
		return APPntName;
	}
	public void setAPPntName(String aAPPntName)
	{
		APPntName = aAPPntName;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/**
	* 0 ---没有财务核销<p>
	* 1 ---已经财务核销
	*/
	public String getConfFlag()
	{
		return ConfFlag;
	}
	public void setConfFlag(String aConfFlag)
	{
		ConfFlag = aConfFlag;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 对于该操作员，做如下约定：<p>
	* SYS001 －－－ 表示专门用来附加险中止转主险暂缴费的操作员。
	*/
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 对于定额单交费，该字段存放单证编码。
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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
	public String getContCom()
	{
		return ContCom;
	}
	public void setContCom(String aContCom)
	{
		ContCom = aContCom;
	}
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/**
	* 标识业务上不能用的更新、删除的备份数据 operstate='1'，正常数据就是0，或为空
	*/
	public String getOperState()
	{
		return OperState;
	}
	public void setOperState(String aOperState)
	{
		OperState = aOperState;
	}
	public String getTempFeeNoType()
	{
		return TempFeeNoType;
	}
	public void setTempFeeNoType(String aTempFeeNoType)
	{
		TempFeeNoType = aTempFeeNoType;
	}
	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getDistict()
	{
		return Distict;
	}
	public void setDistict(String aDistict)
	{
		Distict = aDistict;
	}
	public String getDepartment()
	{
		return Department;
	}
	public void setDepartment(String aDepartment)
	{
		Department = aDepartment;
	}
	public String getBranchCode()
	{
		return BranchCode;
	}
	public void setBranchCode(String aBranchCode)
	{
		BranchCode = aBranchCode;
	}
	/**
	* 空为正常需要核销为保费收入的险种；<p>
	* 1--健康委托产品；<p>
	* 2--健康服务产品
	*/
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	/**
	* 对于终交年期标志为“年”：  表示需要交费的年数。<p>
	* 对于终交年期标志为“月”：  表示需要交费的月数<p>
	* 对于终交年期标志为“日”：  表示需要交费的天数<p>
	* 对于终交年期标志为“年龄”：该字段存放将根据年龄折算成的需要交费的年数。
	*/
	public int getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(int aPayYears)
	{
		PayYears = aPayYears;
	}
	public void setPayYears(String aPayYears)
	{
		if (aPayYears != null && !aPayYears.equals(""))
		{
			Integer tInteger = new Integer(aPayYears);
			int i = tInteger.intValue();
			PayYears = i;
		}
	}

	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/**
	* 00-匹配(正常缴费)，01-代扣
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	public double getUsedMoney()
	{
		return UsedMoney;
	}
	public void setUsedMoney(double aUsedMoney)
	{
		UsedMoney = aUsedMoney;
	}
	public void setUsedMoney(String aUsedMoney)
	{
		if (aUsedMoney != null && !aUsedMoney.equals(""))
		{
			Double tDouble = new Double(aUsedMoney);
			double d = tDouble.doubleValue();
			UsedMoney = d;
		}
	}

	public double getLockMoney()
	{
		return LockMoney;
	}
	public void setLockMoney(double aLockMoney)
	{
		LockMoney = aLockMoney;
	}
	public void setLockMoney(String aLockMoney)
	{
		if (aLockMoney != null && !aLockMoney.equals(""))
		{
			Double tDouble = new Double(aLockMoney);
			double d = tDouble.doubleValue();
			LockMoney = d;
		}
	}

	public String getAppOperator()
	{
		return AppOperator;
	}
	public void setAppOperator(String aAppOperator)
	{
		AppOperator = aAppOperator;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		AppTime = aAppTime;
	}
	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		InputOperator = aInputOperator;
	}
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	public String getInputTime()
	{
		return InputTime;
	}
	public void setInputTime(String aInputTime)
	{
		InputTime = aInputTime;
	}
	public String getInputConclusion()
	{
		return InputConclusion;
	}
	public void setInputConclusion(String aInputConclusion)
	{
		InputConclusion = aInputConclusion;
	}
	public String getInputDesc()
	{
		return InputDesc;
	}
	public void setInputDesc(String aInputDesc)
	{
		InputDesc = aInputDesc;
	}
	public String getConfirmOperator()
	{
		return ConfirmOperator;
	}
	public void setConfirmOperator(String aConfirmOperator)
	{
		ConfirmOperator = aConfirmOperator;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	public String getConfirmTime()
	{
		return ConfirmTime;
	}
	public void setConfirmTime(String aConfirmTime)
	{
		ConfirmTime = aConfirmTime;
	}
	public String getConfirmConclusion()
	{
		return ConfirmConclusion;
	}
	public void setConfirmConclusion(String aConfirmConclusion)
	{
		ConfirmConclusion = aConfirmConclusion;
	}
	public String getConfirmDesc()
	{
		return ConfirmDesc;
	}
	public void setConfirmDesc(String aConfirmDesc)
	{
		ConfirmDesc = aConfirmDesc;
	}
	public String getCancelOperator()
	{
		return CancelOperator;
	}
	public void setCancelOperator(String aCancelOperator)
	{
		CancelOperator = aCancelOperator;
	}
	public String getCancelDate()
	{
		if( CancelDate != null )
			return fDate.getString(CancelDate);
		else
			return null;
	}
	public void setCancelDate(Date aCancelDate)
	{
		CancelDate = aCancelDate;
	}
	public void setCancelDate(String aCancelDate)
	{
		if (aCancelDate != null && !aCancelDate.equals("") )
		{
			CancelDate = fDate.getDate( aCancelDate );
		}
		else
			CancelDate = null;
	}

	public String getCancelTime()
	{
		return CancelTime;
	}
	public void setCancelTime(String aCancelTime)
	{
		CancelTime = aCancelTime;
	}
	public String getCancelDesc()
	{
		return CancelDesc;
	}
	public void setCancelDesc(String aCancelDesc)
	{
		CancelDesc = aCancelDesc;
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

	/**
	* 使用另外一个 LJTempFeeSchema 对象给 Schema 赋值
	* @param: aLJTempFeeSchema LJTempFeeSchema
	**/
	public void setSchema(LJTempFeeSchema aLJTempFeeSchema)
	{
		this.TempFeeNo = aLJTempFeeSchema.getTempFeeNo();
		this.TempFeeType = aLJTempFeeSchema.getTempFeeType();
		this.RiskCode = aLJTempFeeSchema.getRiskCode();
		this.PayIntv = aLJTempFeeSchema.getPayIntv();
		this.OtherNo = aLJTempFeeSchema.getOtherNo();
		this.OtherNoType = aLJTempFeeSchema.getOtherNoType();
		this.PayMoney = aLJTempFeeSchema.getPayMoney();
		this.PayDate = fDate.getDate( aLJTempFeeSchema.getPayDate());
		this.EnterAccDate = fDate.getDate( aLJTempFeeSchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLJTempFeeSchema.getConfDate());
		this.ConfMakeDate = fDate.getDate( aLJTempFeeSchema.getConfMakeDate());
		this.ConfMakeTime = aLJTempFeeSchema.getConfMakeTime();
		this.SaleChnl = aLJTempFeeSchema.getSaleChnl();
		this.ManageCom = aLJTempFeeSchema.getManageCom();
		this.PolicyCom = aLJTempFeeSchema.getPolicyCom();
		this.AgentCom = aLJTempFeeSchema.getAgentCom();
		this.AgentType = aLJTempFeeSchema.getAgentType();
		this.APPntName = aLJTempFeeSchema.getAPPntName();
		this.AgentGroup = aLJTempFeeSchema.getAgentGroup();
		this.AgentCode = aLJTempFeeSchema.getAgentCode();
		this.ConfFlag = aLJTempFeeSchema.getConfFlag();
		this.SerialNo = aLJTempFeeSchema.getSerialNo();
		this.Operator = aLJTempFeeSchema.getOperator();
		this.State = aLJTempFeeSchema.getState();
		this.MakeTime = aLJTempFeeSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLJTempFeeSchema.getMakeDate());
		this.ModifyDate = fDate.getDate( aLJTempFeeSchema.getModifyDate());
		this.ModifyTime = aLJTempFeeSchema.getModifyTime();
		this.ContCom = aLJTempFeeSchema.getContCom();
		this.PayEndYear = aLJTempFeeSchema.getPayEndYear();
		this.OperState = aLJTempFeeSchema.getOperState();
		this.TempFeeNoType = aLJTempFeeSchema.getTempFeeNoType();
		this.StandPrem = aLJTempFeeSchema.getStandPrem();
		this.Remark = aLJTempFeeSchema.getRemark();
		this.Distict = aLJTempFeeSchema.getDistict();
		this.Department = aLJTempFeeSchema.getDepartment();
		this.BranchCode = aLJTempFeeSchema.getBranchCode();
		this.RiskType = aLJTempFeeSchema.getRiskType();
		this.PayYears = aLJTempFeeSchema.getPayYears();
		this.Currency = aLJTempFeeSchema.getCurrency();
		this.PayMode = aLJTempFeeSchema.getPayMode();
		this.UsedMoney = aLJTempFeeSchema.getUsedMoney();
		this.LockMoney = aLJTempFeeSchema.getLockMoney();
		this.AppOperator = aLJTempFeeSchema.getAppOperator();
		this.AppDate = fDate.getDate( aLJTempFeeSchema.getAppDate());
		this.AppTime = aLJTempFeeSchema.getAppTime();
		this.InputOperator = aLJTempFeeSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLJTempFeeSchema.getInputDate());
		this.InputTime = aLJTempFeeSchema.getInputTime();
		this.InputConclusion = aLJTempFeeSchema.getInputConclusion();
		this.InputDesc = aLJTempFeeSchema.getInputDesc();
		this.ConfirmOperator = aLJTempFeeSchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJTempFeeSchema.getConfirmDate());
		this.ConfirmTime = aLJTempFeeSchema.getConfirmTime();
		this.ConfirmConclusion = aLJTempFeeSchema.getConfirmConclusion();
		this.ConfirmDesc = aLJTempFeeSchema.getConfirmDesc();
		this.CancelOperator = aLJTempFeeSchema.getCancelOperator();
		this.CancelDate = fDate.getDate( aLJTempFeeSchema.getCancelDate());
		this.CancelTime = aLJTempFeeSchema.getCancelTime();
		this.CancelDesc = aLJTempFeeSchema.getCancelDesc();
		this.ComCode = aLJTempFeeSchema.getComCode();
		this.ModifyOperator = aLJTempFeeSchema.getModifyOperator();
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
			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			if( rs.getString("TempFeeType") == null )
				this.TempFeeType = null;
			else
				this.TempFeeType = rs.getString("TempFeeType").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			this.PayMoney = rs.getDouble("PayMoney");
			this.PayDate = rs.getDate("PayDate");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
			this.ConfMakeDate = rs.getDate("ConfMakeDate");
			if( rs.getString("ConfMakeTime") == null )
				this.ConfMakeTime = null;
			else
				this.ConfMakeTime = rs.getString("ConfMakeTime").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("PolicyCom") == null )
				this.PolicyCom = null;
			else
				this.PolicyCom = rs.getString("PolicyCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("APPntName") == null )
				this.APPntName = null;
			else
				this.APPntName = rs.getString("APPntName").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ConfFlag") == null )
				this.ConfFlag = null;
			else
				this.ConfFlag = rs.getString("ConfFlag").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("ContCom") == null )
				this.ContCom = null;
			else
				this.ContCom = rs.getString("ContCom").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("OperState") == null )
				this.OperState = null;
			else
				this.OperState = rs.getString("OperState").trim();

			if( rs.getString("TempFeeNoType") == null )
				this.TempFeeNoType = null;
			else
				this.TempFeeNoType = rs.getString("TempFeeNoType").trim();

			this.StandPrem = rs.getDouble("StandPrem");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Distict") == null )
				this.Distict = null;
			else
				this.Distict = rs.getString("Distict").trim();

			if( rs.getString("Department") == null )
				this.Department = null;
			else
				this.Department = rs.getString("Department").trim();

			if( rs.getString("BranchCode") == null )
				this.BranchCode = null;
			else
				this.BranchCode = rs.getString("BranchCode").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			this.PayYears = rs.getInt("PayYears");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			this.UsedMoney = rs.getDouble("UsedMoney");
			this.LockMoney = rs.getDouble("LockMoney");
			if( rs.getString("AppOperator") == null )
				this.AppOperator = null;
			else
				this.AppOperator = rs.getString("AppOperator").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

			if( rs.getString("InputConclusion") == null )
				this.InputConclusion = null;
			else
				this.InputConclusion = rs.getString("InputConclusion").trim();

			if( rs.getString("InputDesc") == null )
				this.InputDesc = null;
			else
				this.InputDesc = rs.getString("InputDesc").trim();

			if( rs.getString("ConfirmOperator") == null )
				this.ConfirmOperator = null;
			else
				this.ConfirmOperator = rs.getString("ConfirmOperator").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("ConfirmTime") == null )
				this.ConfirmTime = null;
			else
				this.ConfirmTime = rs.getString("ConfirmTime").trim();

			if( rs.getString("ConfirmConclusion") == null )
				this.ConfirmConclusion = null;
			else
				this.ConfirmConclusion = rs.getString("ConfirmConclusion").trim();

			if( rs.getString("ConfirmDesc") == null )
				this.ConfirmDesc = null;
			else
				this.ConfirmDesc = rs.getString("ConfirmDesc").trim();

			if( rs.getString("CancelOperator") == null )
				this.CancelOperator = null;
			else
				this.CancelOperator = rs.getString("CancelOperator").trim();

			this.CancelDate = rs.getDate("CancelDate");
			if( rs.getString("CancelTime") == null )
				this.CancelTime = null;
			else
				this.CancelTime = rs.getString("CancelTime").trim();

			if( rs.getString("CancelDesc") == null )
				this.CancelDesc = null;
			else
				this.CancelDesc = rs.getString("CancelDesc").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJTempFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJTempFeeSchema getSchema()
	{
		LJTempFeeSchema aLJTempFeeSchema = new LJTempFeeSchema();
		aLJTempFeeSchema.setSchema(this);
		return aLJTempFeeSchema;
	}

	public LJTempFeeDB getDB()
	{
		LJTempFeeDB aDBOper = new LJTempFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJTempFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(APPntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Distict)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UsedMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LockMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CancelOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CancelDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CancelTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CancelDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJTempFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TempFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ConfMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ConfMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PolicyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			APPntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ConfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ContCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).intValue();
			OperState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			TempFeeNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Distict = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Department = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			BranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).intValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			UsedMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			LockMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			AppOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			InputConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			InputDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53,SysConst.PACKAGESPILTER));
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			CancelOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			CancelDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			CancelTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			CancelDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeSchema";
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
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equalsIgnoreCase("TempFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeType));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ConfMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfMakeTime));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("APPntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPntName));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("ConfFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfFlag));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ContCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContCom));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperState));
		}
		if (FCode.equalsIgnoreCase("TempFeeNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNoType));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Distict"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Distict));
		}
		if (FCode.equalsIgnoreCase("Department"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department));
		}
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchCode));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("UsedMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UsedMoney));
		}
		if (FCode.equalsIgnoreCase("LockMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockMoney));
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOperator));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputTime));
		}
		if (FCode.equalsIgnoreCase("InputConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputConclusion));
		}
		if (FCode.equalsIgnoreCase("InputDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputDesc));
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmOperator));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmConclusion));
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmDesc));
		}
		if (FCode.equalsIgnoreCase("CancelOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CancelOperator));
		}
		if (FCode.equalsIgnoreCase("CancelDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCancelDate()));
		}
		if (FCode.equalsIgnoreCase("CancelTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CancelTime));
		}
		if (FCode.equalsIgnoreCase("CancelDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CancelDesc));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TempFeeType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 6:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ConfMakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PolicyCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(APPntName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ConfFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ContCom);
				break;
			case 29:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(OperState);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNoType);
				break;
			case 32:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Distict);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Department);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(BranchCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 38:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 41:
				strFieldValue = String.valueOf(UsedMoney);
				break;
			case 42:
				strFieldValue = String.valueOf(LockMoney);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AppOperator);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(InputConclusion);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(InputDesc);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(CancelOperator);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCancelDate()));
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(CancelTime);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(CancelDesc);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("TempFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeType = FValue.trim();
			}
			else
				TempFeeType = null;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
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
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayDate = fDate.getDate( FValue );
			}
			else
				PayDate = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnterAccDate = fDate.getDate( FValue );
			}
			else
				EnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfMakeDate = fDate.getDate( FValue );
			}
			else
				ConfMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfMakeTime = FValue.trim();
			}
			else
				ConfMakeTime = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyCom = FValue.trim();
			}
			else
				PolicyCom = null;
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
		if (FCode.equalsIgnoreCase("APPntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPntName = FValue.trim();
			}
			else
				APPntName = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("ConfFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfFlag = FValue.trim();
			}
			else
				ConfFlag = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
		if (FCode.equalsIgnoreCase("ContCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContCom = FValue.trim();
			}
			else
				ContCom = null;
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayEndYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperState = FValue.trim();
			}
			else
				OperState = null;
		}
		if (FCode.equalsIgnoreCase("TempFeeNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNoType = FValue.trim();
			}
			else
				TempFeeNoType = null;
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
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
		if (FCode.equalsIgnoreCase("Distict"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Distict = FValue.trim();
			}
			else
				Distict = null;
		}
		if (FCode.equalsIgnoreCase("Department"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Department = FValue.trim();
			}
			else
				Department = null;
		}
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchCode = FValue.trim();
			}
			else
				BranchCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
			}
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
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equalsIgnoreCase("UsedMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UsedMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LockMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LockMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppOperator = FValue.trim();
			}
			else
				AppOperator = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOperator = FValue.trim();
			}
			else
				InputOperator = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputTime = FValue.trim();
			}
			else
				InputTime = null;
		}
		if (FCode.equalsIgnoreCase("InputConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputConclusion = FValue.trim();
			}
			else
				InputConclusion = null;
		}
		if (FCode.equalsIgnoreCase("InputDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputDesc = FValue.trim();
			}
			else
				InputDesc = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmOperator = FValue.trim();
			}
			else
				ConfirmOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmTime = FValue.trim();
			}
			else
				ConfirmTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmConclusion = FValue.trim();
			}
			else
				ConfirmConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmDesc = FValue.trim();
			}
			else
				ConfirmDesc = null;
		}
		if (FCode.equalsIgnoreCase("CancelOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CancelOperator = FValue.trim();
			}
			else
				CancelOperator = null;
		}
		if (FCode.equalsIgnoreCase("CancelDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CancelDate = fDate.getDate( FValue );
			}
			else
				CancelDate = null;
		}
		if (FCode.equalsIgnoreCase("CancelTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CancelTime = FValue.trim();
			}
			else
				CancelTime = null;
		}
		if (FCode.equalsIgnoreCase("CancelDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CancelDesc = FValue.trim();
			}
			else
				CancelDesc = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJTempFeeSchema other = (LJTempFeeSchema)otherObject;
		return
			TempFeeNo.equals(other.getTempFeeNo())
			&& TempFeeType.equals(other.getTempFeeType())
			&& RiskCode.equals(other.getRiskCode())
			&& PayIntv == other.getPayIntv()
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& PayMoney == other.getPayMoney()
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& fDate.getString(ConfMakeDate).equals(other.getConfMakeDate())
			&& ConfMakeTime.equals(other.getConfMakeTime())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ManageCom.equals(other.getManageCom())
			&& PolicyCom.equals(other.getPolicyCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& APPntName.equals(other.getAPPntName())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode.equals(other.getAgentCode())
			&& ConfFlag.equals(other.getConfFlag())
			&& SerialNo.equals(other.getSerialNo())
			&& Operator.equals(other.getOperator())
			&& State.equals(other.getState())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ContCom.equals(other.getContCom())
			&& PayEndYear == other.getPayEndYear()
			&& OperState.equals(other.getOperState())
			&& TempFeeNoType.equals(other.getTempFeeNoType())
			&& StandPrem == other.getStandPrem()
			&& Remark.equals(other.getRemark())
			&& Distict.equals(other.getDistict())
			&& Department.equals(other.getDepartment())
			&& BranchCode.equals(other.getBranchCode())
			&& RiskType.equals(other.getRiskType())
			&& PayYears == other.getPayYears()
			&& Currency.equals(other.getCurrency())
			&& PayMode.equals(other.getPayMode())
			&& UsedMoney == other.getUsedMoney()
			&& LockMoney == other.getLockMoney()
			&& AppOperator.equals(other.getAppOperator())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& InputConclusion.equals(other.getInputConclusion())
			&& InputDesc.equals(other.getInputDesc())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmDesc.equals(other.getConfirmDesc())
			&& CancelOperator.equals(other.getCancelOperator())
			&& fDate.getString(CancelDate).equals(other.getCancelDate())
			&& CancelTime.equals(other.getCancelTime())
			&& CancelDesc.equals(other.getCancelDesc())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("TempFeeNo") ) {
			return 0;
		}
		if( strFieldName.equals("TempFeeType") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 4;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 5;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 6;
		}
		if( strFieldName.equals("PayDate") ) {
			return 7;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 8;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 9;
		}
		if( strFieldName.equals("ConfMakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("ConfMakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 12;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 13;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return 14;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 15;
		}
		if( strFieldName.equals("AgentType") ) {
			return 16;
		}
		if( strFieldName.equals("APPntName") ) {
			return 17;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 18;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 19;
		}
		if( strFieldName.equals("ConfFlag") ) {
			return 20;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 21;
		}
		if( strFieldName.equals("Operator") ) {
			return 22;
		}
		if( strFieldName.equals("State") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
		}
		if( strFieldName.equals("ContCom") ) {
			return 28;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 29;
		}
		if( strFieldName.equals("OperState") ) {
			return 30;
		}
		if( strFieldName.equals("TempFeeNoType") ) {
			return 31;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 32;
		}
		if( strFieldName.equals("Remark") ) {
			return 33;
		}
		if( strFieldName.equals("Distict") ) {
			return 34;
		}
		if( strFieldName.equals("Department") ) {
			return 35;
		}
		if( strFieldName.equals("BranchCode") ) {
			return 36;
		}
		if( strFieldName.equals("RiskType") ) {
			return 37;
		}
		if( strFieldName.equals("PayYears") ) {
			return 38;
		}
		if( strFieldName.equals("Currency") ) {
			return 39;
		}
		if( strFieldName.equals("PayMode") ) {
			return 40;
		}
		if( strFieldName.equals("UsedMoney") ) {
			return 41;
		}
		if( strFieldName.equals("LockMoney") ) {
			return 42;
		}
		if( strFieldName.equals("AppOperator") ) {
			return 43;
		}
		if( strFieldName.equals("AppDate") ) {
			return 44;
		}
		if( strFieldName.equals("AppTime") ) {
			return 45;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 46;
		}
		if( strFieldName.equals("InputDate") ) {
			return 47;
		}
		if( strFieldName.equals("InputTime") ) {
			return 48;
		}
		if( strFieldName.equals("InputConclusion") ) {
			return 49;
		}
		if( strFieldName.equals("InputDesc") ) {
			return 50;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 51;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 52;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 53;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 54;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 55;
		}
		if( strFieldName.equals("CancelOperator") ) {
			return 56;
		}
		if( strFieldName.equals("CancelDate") ) {
			return 57;
		}
		if( strFieldName.equals("CancelTime") ) {
			return 58;
		}
		if( strFieldName.equals("CancelDesc") ) {
			return 59;
		}
		if( strFieldName.equals("ComCode") ) {
			return 60;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 61;
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
				strFieldName = "TempFeeNo";
				break;
			case 1:
				strFieldName = "TempFeeType";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "PayIntv";
				break;
			case 4:
				strFieldName = "OtherNo";
				break;
			case 5:
				strFieldName = "OtherNoType";
				break;
			case 6:
				strFieldName = "PayMoney";
				break;
			case 7:
				strFieldName = "PayDate";
				break;
			case 8:
				strFieldName = "EnterAccDate";
				break;
			case 9:
				strFieldName = "ConfDate";
				break;
			case 10:
				strFieldName = "ConfMakeDate";
				break;
			case 11:
				strFieldName = "ConfMakeTime";
				break;
			case 12:
				strFieldName = "SaleChnl";
				break;
			case 13:
				strFieldName = "ManageCom";
				break;
			case 14:
				strFieldName = "PolicyCom";
				break;
			case 15:
				strFieldName = "AgentCom";
				break;
			case 16:
				strFieldName = "AgentType";
				break;
			case 17:
				strFieldName = "APPntName";
				break;
			case 18:
				strFieldName = "AgentGroup";
				break;
			case 19:
				strFieldName = "AgentCode";
				break;
			case 20:
				strFieldName = "ConfFlag";
				break;
			case 21:
				strFieldName = "SerialNo";
				break;
			case 22:
				strFieldName = "Operator";
				break;
			case 23:
				strFieldName = "State";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
				strFieldName = "ModifyTime";
				break;
			case 28:
				strFieldName = "ContCom";
				break;
			case 29:
				strFieldName = "PayEndYear";
				break;
			case 30:
				strFieldName = "OperState";
				break;
			case 31:
				strFieldName = "TempFeeNoType";
				break;
			case 32:
				strFieldName = "StandPrem";
				break;
			case 33:
				strFieldName = "Remark";
				break;
			case 34:
				strFieldName = "Distict";
				break;
			case 35:
				strFieldName = "Department";
				break;
			case 36:
				strFieldName = "BranchCode";
				break;
			case 37:
				strFieldName = "RiskType";
				break;
			case 38:
				strFieldName = "PayYears";
				break;
			case 39:
				strFieldName = "Currency";
				break;
			case 40:
				strFieldName = "PayMode";
				break;
			case 41:
				strFieldName = "UsedMoney";
				break;
			case 42:
				strFieldName = "LockMoney";
				break;
			case 43:
				strFieldName = "AppOperator";
				break;
			case 44:
				strFieldName = "AppDate";
				break;
			case 45:
				strFieldName = "AppTime";
				break;
			case 46:
				strFieldName = "InputOperator";
				break;
			case 47:
				strFieldName = "InputDate";
				break;
			case 48:
				strFieldName = "InputTime";
				break;
			case 49:
				strFieldName = "InputConclusion";
				break;
			case 50:
				strFieldName = "InputDesc";
				break;
			case 51:
				strFieldName = "ConfirmOperator";
				break;
			case 52:
				strFieldName = "ConfirmDate";
				break;
			case 53:
				strFieldName = "ConfirmTime";
				break;
			case 54:
				strFieldName = "ConfirmConclusion";
				break;
			case 55:
				strFieldName = "ConfirmDesc";
				break;
			case 56:
				strFieldName = "CancelOperator";
				break;
			case 57:
				strFieldName = "CancelDate";
				break;
			case 58:
				strFieldName = "CancelTime";
				break;
			case 59:
				strFieldName = "CancelDesc";
				break;
			case 60:
				strFieldName = "ComCode";
				break;
			case 61:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfMakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OperState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Distict") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UsedMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LockMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CancelOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CancelDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CancelTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CancelDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_INT;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_INT;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
