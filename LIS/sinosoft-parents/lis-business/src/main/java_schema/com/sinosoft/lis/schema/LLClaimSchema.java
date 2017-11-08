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
import com.sinosoft.lis.db.LLClaimDB;

/*
 * <p>ClassName: LLClaimSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 立案号 */
	private String RgtNo;
	/** 分案号 */
	private String CaseNo;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 赔案状态 */
	private String ClmState;
	/** 核算赔付金额 */
	private double StandPay;
	/** 预付金额 */
	private double BeforePay;
	/** 结算金额 */
	private double BalancePay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 据赔金额 */
	private double DeclinePay;
	/** 赔付结论 */
	private String GiveType;
	/** 赔付结论描述 */
	private String GiveTypeDesc;
	/** 理赔员 */
	private String ClmUWer;
	/** 拒赔号 */
	private String DeclineNo;
	/** 结案日期 */
	private Date EndCaseDate;
	/** 案件给付类型 */
	private String CasePayType;
	/** 审核类型 */
	private String CheckType;
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
	/** 保单结算标志 */
	private String ConBalFlag;
	/** 合同处理标志 */
	private String ConDealFlag;
	/** 回退标志 */
	private String BackFlag;
	/** 回退号 */
	private String BackNo;
	/** 结案标志 */
	private String EndCaseFlag;
	/** 币别 */
	private String Currency;
	/** 审核权限 */
	private String AuditPopedom;
	/** 二核状态 */
	private String UWState;
	/** 计算标志 */
	private String CalFlag;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 34;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClmNo";

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
		LLClaimSchema cloned = (LLClaimSchema)super.clone();
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
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	/**
	* 【废弃不用】
	*/
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
	/**
	* 【废弃不用】
	*/
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
	/**
	* 【废弃不用】<p>
	* #100  意外医疗 #101  意外伤残 #102  意外死 #103  意外医疗津贴 #104  意外高残 #200  疾病医疗 #201  疾病伤残 #202  疾病死亡 #203  疾病医疗津贴 #204  疾病高残<p>
	* 无用到
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>6)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值6");
		GetDutyKind = aGetDutyKind;
	}
	/**
	* 10 报案<p>
	* 20 立案<p>
	* 30 审核<p>
	* 35 预付<p>
	* 40 审批<p>
	* 50 结案<p>
	* 60 完成<p>
	* 70 关闭
	*/
	public String getClmState()
	{
		return ClmState;
	}
	public void setClmState(String aClmState)
	{
		if(aClmState!=null && aClmState.length()>6)
			throw new IllegalArgumentException("赔案状态ClmState值"+aClmState+"的长度"+aClmState.length()+"大于最大值6");
		ClmState = aClmState;
	}
	/**
	* 赔案计算出来的金额
	*/
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

	/**
	* 当作预付使用
	*/
	public double getBeforePay()
	{
		return BeforePay;
	}
	public void setBeforePay(double aBeforePay)
	{
		BeforePay = aBeforePay;
	}
	public void setBeforePay(String aBeforePay)
	{
		if (aBeforePay != null && !aBeforePay.equals(""))
		{
			Double tDouble = new Double(aBeforePay);
			double d = tDouble.doubleValue();
			BeforePay = d;
		}
	}

	/**
	* 保单结算,合同处理结算出来的金额
	*/
	public double getBalancePay()
	{
		return BalancePay;
	}
	public void setBalancePay(double aBalancePay)
	{
		BalancePay = aBalancePay;
	}
	public void setBalancePay(String aBalancePay)
	{
		if (aBalancePay != null && !aBalancePay.equals(""))
		{
			Double tDouble = new Double(aBalancePay);
			double d = tDouble.doubleValue();
			BalancePay = d;
		}
	}

	/**
	* 最后赔案确定赔付的金额,包括赔案金额,预付金额,结算金额的合计
	*/
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

	public double getDeclinePay()
	{
		return DeclinePay;
	}
	public void setDeclinePay(double aDeclinePay)
	{
		DeclinePay = aDeclinePay;
	}
	public void setDeclinePay(String aDeclinePay)
	{
		if (aDeclinePay != null && !aDeclinePay.equals(""))
		{
			Double tDouble = new Double(aDeclinePay);
			double d = tDouble.doubleValue();
			DeclinePay = d;
		}
	}

	/**
	* 0给付<p>
	* 1拒付<p>
	* 2客户撤案<p>
	* 3公司撤案
	*/
	public String getGiveType()
	{
		return GiveType;
	}
	public void setGiveType(String aGiveType)
	{
		if(aGiveType!=null && aGiveType.length()>10)
			throw new IllegalArgumentException("赔付结论GiveType值"+aGiveType+"的长度"+aGiveType.length()+"大于最大值10");
		GiveType = aGiveType;
	}
	public String getGiveTypeDesc()
	{
		return GiveTypeDesc;
	}
	public void setGiveTypeDesc(String aGiveTypeDesc)
	{
		if(aGiveTypeDesc!=null && aGiveTypeDesc.length()>4000)
			throw new IllegalArgumentException("赔付结论描述GiveTypeDesc值"+aGiveTypeDesc+"的长度"+aGiveTypeDesc.length()+"大于最大值4000");
		GiveTypeDesc = aGiveTypeDesc;
	}
	/**
	* 【废弃不用】
	*/
	public String getClmUWer()
	{
		return ClmUWer;
	}
	public void setClmUWer(String aClmUWer)
	{
		if(aClmUWer!=null && aClmUWer.length()>10)
			throw new IllegalArgumentException("理赔员ClmUWer值"+aClmUWer+"的长度"+aClmUWer.length()+"大于最大值10");
		ClmUWer = aClmUWer;
	}
	/**
	* 【废弃不用】
	*/
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

	/**
	* 个险:<p>
	* 0	一般给付件<p>
	* 1	短期给付件<p>
	* 2	协议给付件<p>
	* 3	预付给付件<p>
	* 4	责任免除件<p>
	* 5	简易案件<p>
	* <p>
	* 团险:<p>
	* <p>
	* 0	一般给付件<p>
	* 1	短期给付件<p>
	* 2	批次案件<p>
	* 3	通融/协议给付件<p>
	* 4	责任????件
	*/
	public String getCasePayType()
	{
		return CasePayType;
	}
	public void setCasePayType(String aCasePayType)
	{
		if(aCasePayType!=null && aCasePayType.length()>1)
			throw new IllegalArgumentException("案件给付类型CasePayType值"+aCasePayType+"的长度"+aCasePayType.length()+"大于最大值1");
		CasePayType = aCasePayType;
	}
	/**
	* [不用]<p>
	* 0 －－ 初次审核<p>
	* 1 －－ 审批退回审核<p>
	* 2 －－ 申诉审核
	*/
	public String getCheckType()
	{
		return CheckType;
	}
	public void setCheckType(String aCheckType)
	{
		if(aCheckType!=null && aCheckType.length()>1)
			throw new IllegalArgumentException("审核类型CheckType值"+aCheckType+"的长度"+aCheckType.length()+"大于最大值1");
		CheckType = aCheckType;
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
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
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
	* 0或Null未处理<p>
	* 1已处理
	*/
	public String getConBalFlag()
	{
		return ConBalFlag;
	}
	public void setConBalFlag(String aConBalFlag)
	{
		if(aConBalFlag!=null && aConBalFlag.length()>20)
			throw new IllegalArgumentException("保单结算标志ConBalFlag值"+aConBalFlag+"的长度"+aConBalFlag.length()+"大于最大值20");
		ConBalFlag = aConBalFlag;
	}
	/**
	* 0或Null未处理<p>
	* 1已处理
	*/
	public String getConDealFlag()
	{
		return ConDealFlag;
	}
	public void setConDealFlag(String aConDealFlag)
	{
		if(aConDealFlag!=null && aConDealFlag.length()>20)
			throw new IllegalArgumentException("合同处理标志ConDealFlag值"+aConDealFlag+"的长度"+aConDealFlag.length()+"大于最大值20");
		ConDealFlag = aConDealFlag;
	}
	/**
	* 0或Null未回退<p>
	* 1已回退
	*/
	public String getBackFlag()
	{
		return BackFlag;
	}
	public void setBackFlag(String aBackFlag)
	{
		if(aBackFlag!=null && aBackFlag.length()>6)
			throw new IllegalArgumentException("回退标志BackFlag值"+aBackFlag+"的长度"+aBackFlag.length()+"大于最大值6");
		BackFlag = aBackFlag;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		if(aBackNo!=null && aBackNo.length()>20)
			throw new IllegalArgumentException("回退号BackNo值"+aBackNo+"的长度"+aBackNo.length()+"大于最大值20");
		BackNo = aBackNo;
	}
	/**
	* 0未结案<p>
	* 1已结案
	*/
	public String getEndCaseFlag()
	{
		return EndCaseFlag;
	}
	public void setEndCaseFlag(String aEndCaseFlag)
	{
		if(aEndCaseFlag!=null && aEndCaseFlag.length()>6)
			throw new IllegalArgumentException("结案标志EndCaseFlag值"+aEndCaseFlag+"的长度"+aEndCaseFlag.length()+"大于最大值6");
		EndCaseFlag = aEndCaseFlag;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	public String getAuditPopedom()
	{
		return AuditPopedom;
	}
	public void setAuditPopedom(String aAuditPopedom)
	{
		if(aAuditPopedom!=null && aAuditPopedom.length()>20)
			throw new IllegalArgumentException("审核权限AuditPopedom值"+aAuditPopedom+"的长度"+aAuditPopedom.length()+"大于最大值20");
		AuditPopedom = aAuditPopedom;
	}
	public String getUWState()
	{
		return UWState;
	}
	public void setUWState(String aUWState)
	{
		if(aUWState!=null && aUWState.length()>1)
			throw new IllegalArgumentException("二核状态UWState值"+aUWState+"的长度"+aUWState.length()+"大于最大值1");
		UWState = aUWState;
	}
	/**
	* 0-未理算，1-部分理算，2-全部理算
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		if(aCalFlag!=null && aCalFlag.length()>20)
			throw new IllegalArgumentException("计算标志CalFlag值"+aCalFlag+"的长度"+aCalFlag.length()+"大于最大值20");
		CalFlag = aCalFlag;
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

	/**
	* 使用另外一个 LLClaimSchema 对象给 Schema 赋值
	* @param: aLLClaimSchema LLClaimSchema
	**/
	public void setSchema(LLClaimSchema aLLClaimSchema)
	{
		this.ClmNo = aLLClaimSchema.getClmNo();
		this.RgtNo = aLLClaimSchema.getRgtNo();
		this.CaseNo = aLLClaimSchema.getCaseNo();
		this.GetDutyKind = aLLClaimSchema.getGetDutyKind();
		this.ClmState = aLLClaimSchema.getClmState();
		this.StandPay = aLLClaimSchema.getStandPay();
		this.BeforePay = aLLClaimSchema.getBeforePay();
		this.BalancePay = aLLClaimSchema.getBalancePay();
		this.RealPay = aLLClaimSchema.getRealPay();
		this.DeclinePay = aLLClaimSchema.getDeclinePay();
		this.GiveType = aLLClaimSchema.getGiveType();
		this.GiveTypeDesc = aLLClaimSchema.getGiveTypeDesc();
		this.ClmUWer = aLLClaimSchema.getClmUWer();
		this.DeclineNo = aLLClaimSchema.getDeclineNo();
		this.EndCaseDate = fDate.getDate( aLLClaimSchema.getEndCaseDate());
		this.CasePayType = aLLClaimSchema.getCasePayType();
		this.CheckType = aLLClaimSchema.getCheckType();
		this.MngCom = aLLClaimSchema.getMngCom();
		this.Operator = aLLClaimSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLClaimSchema.getMakeDate());
		this.MakeTime = aLLClaimSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimSchema.getModifyDate());
		this.ModifyTime = aLLClaimSchema.getModifyTime();
		this.ConBalFlag = aLLClaimSchema.getConBalFlag();
		this.ConDealFlag = aLLClaimSchema.getConDealFlag();
		this.BackFlag = aLLClaimSchema.getBackFlag();
		this.BackNo = aLLClaimSchema.getBackNo();
		this.EndCaseFlag = aLLClaimSchema.getEndCaseFlag();
		this.Currency = aLLClaimSchema.getCurrency();
		this.AuditPopedom = aLLClaimSchema.getAuditPopedom();
		this.UWState = aLLClaimSchema.getUWState();
		this.CalFlag = aLLClaimSchema.getCalFlag();
		this.ComCode = aLLClaimSchema.getComCode();
		this.ModifyOperator = aLLClaimSchema.getModifyOperator();
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

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("ClmState") == null )
				this.ClmState = null;
			else
				this.ClmState = rs.getString("ClmState").trim();

			this.StandPay = rs.getDouble("StandPay");
			this.BeforePay = rs.getDouble("BeforePay");
			this.BalancePay = rs.getDouble("BalancePay");
			this.RealPay = rs.getDouble("RealPay");
			this.DeclinePay = rs.getDouble("DeclinePay");
			if( rs.getString("GiveType") == null )
				this.GiveType = null;
			else
				this.GiveType = rs.getString("GiveType").trim();

			if( rs.getString("GiveTypeDesc") == null )
				this.GiveTypeDesc = null;
			else
				this.GiveTypeDesc = rs.getString("GiveTypeDesc").trim();

			if( rs.getString("ClmUWer") == null )
				this.ClmUWer = null;
			else
				this.ClmUWer = rs.getString("ClmUWer").trim();

			if( rs.getString("DeclineNo") == null )
				this.DeclineNo = null;
			else
				this.DeclineNo = rs.getString("DeclineNo").trim();

			this.EndCaseDate = rs.getDate("EndCaseDate");
			if( rs.getString("CasePayType") == null )
				this.CasePayType = null;
			else
				this.CasePayType = rs.getString("CasePayType").trim();

			if( rs.getString("CheckType") == null )
				this.CheckType = null;
			else
				this.CheckType = rs.getString("CheckType").trim();

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

			if( rs.getString("ConBalFlag") == null )
				this.ConBalFlag = null;
			else
				this.ConBalFlag = rs.getString("ConBalFlag").trim();

			if( rs.getString("ConDealFlag") == null )
				this.ConDealFlag = null;
			else
				this.ConDealFlag = rs.getString("ConDealFlag").trim();

			if( rs.getString("BackFlag") == null )
				this.BackFlag = null;
			else
				this.BackFlag = rs.getString("BackFlag").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			if( rs.getString("EndCaseFlag") == null )
				this.EndCaseFlag = null;
			else
				this.EndCaseFlag = rs.getString("EndCaseFlag").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("AuditPopedom") == null )
				this.AuditPopedom = null;
			else
				this.AuditPopedom = rs.getString("AuditPopedom").trim();

			if( rs.getString("UWState") == null )
				this.UWState = null;
			else
				this.UWState = rs.getString("UWState").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

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
			logger.debug("数据库中的LLClaim表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimSchema getSchema()
	{
		LLClaimSchema aLLClaimSchema = new LLClaimSchema();
		aLLClaimSchema.setSchema(this);
		return aLLClaimSchema;
	}

	public LLClaimDB getDB()
	{
		LLClaimDB aDBOper = new LLClaimDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaim描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BeforePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BalancePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeclinePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndCaseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConBalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConDealFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndCaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaim>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClmState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			BeforePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			BalancePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			DeclinePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			GiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GiveTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			DeclineNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			CasePayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CheckType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ConBalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ConDealFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			BackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			EndCaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AuditPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			UWState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimSchema";
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmState));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("BeforePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforePay));
		}
		if (FCode.equalsIgnoreCase("BalancePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalancePay));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("DeclinePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclinePay));
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveType));
		}
		if (FCode.equalsIgnoreCase("GiveTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveTypeDesc));
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWer));
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineNo));
		}
		if (FCode.equalsIgnoreCase("EndCaseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
		}
		if (FCode.equalsIgnoreCase("CasePayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePayType));
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckType));
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
		if (FCode.equalsIgnoreCase("ConBalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConBalFlag));
		}
		if (FCode.equalsIgnoreCase("ConDealFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConDealFlag));
		}
		if (FCode.equalsIgnoreCase("BackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackFlag));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("EndCaseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndCaseFlag));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("AuditPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditPopedom));
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWState));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmState);
				break;
			case 5:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 6:
				strFieldValue = String.valueOf(BeforePay);
				break;
			case 7:
				strFieldValue = String.valueOf(BalancePay);
				break;
			case 8:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 9:
				strFieldValue = String.valueOf(DeclinePay);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GiveType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GiveTypeDesc);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ClmUWer);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(DeclineNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CasePayType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CheckType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
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
				strFieldValue = StrTool.GBKToUnicode(ConBalFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ConDealFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(BackFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(EndCaseFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AuditPopedom);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(UWState);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 33:
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
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
		if (FCode.equalsIgnoreCase("BeforePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BeforePay = d;
			}
		}
		if (FCode.equalsIgnoreCase("BalancePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BalancePay = d;
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
		if (FCode.equalsIgnoreCase("DeclinePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DeclinePay = d;
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
		if (FCode.equalsIgnoreCase("GiveTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveTypeDesc = FValue.trim();
			}
			else
				GiveTypeDesc = null;
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
		if (FCode.equalsIgnoreCase("CasePayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePayType = FValue.trim();
			}
			else
				CasePayType = null;
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckType = FValue.trim();
			}
			else
				CheckType = null;
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
		if (FCode.equalsIgnoreCase("ConBalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConBalFlag = FValue.trim();
			}
			else
				ConBalFlag = null;
		}
		if (FCode.equalsIgnoreCase("ConDealFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConDealFlag = FValue.trim();
			}
			else
				ConDealFlag = null;
		}
		if (FCode.equalsIgnoreCase("BackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackFlag = FValue.trim();
			}
			else
				BackFlag = null;
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackNo = FValue.trim();
			}
			else
				BackNo = null;
		}
		if (FCode.equalsIgnoreCase("EndCaseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndCaseFlag = FValue.trim();
			}
			else
				EndCaseFlag = null;
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
		if (FCode.equalsIgnoreCase("AuditPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditPopedom = FValue.trim();
			}
			else
				AuditPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWState = FValue.trim();
			}
			else
				UWState = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
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
		LLClaimSchema other = (LLClaimSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& RgtNo.equals(other.getRgtNo())
			&& CaseNo.equals(other.getCaseNo())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& ClmState.equals(other.getClmState())
			&& StandPay == other.getStandPay()
			&& BeforePay == other.getBeforePay()
			&& BalancePay == other.getBalancePay()
			&& RealPay == other.getRealPay()
			&& DeclinePay == other.getDeclinePay()
			&& GiveType.equals(other.getGiveType())
			&& GiveTypeDesc.equals(other.getGiveTypeDesc())
			&& ClmUWer.equals(other.getClmUWer())
			&& DeclineNo.equals(other.getDeclineNo())
			&& fDate.getString(EndCaseDate).equals(other.getEndCaseDate())
			&& CasePayType.equals(other.getCasePayType())
			&& CheckType.equals(other.getCheckType())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ConBalFlag.equals(other.getConBalFlag())
			&& ConDealFlag.equals(other.getConDealFlag())
			&& BackFlag.equals(other.getBackFlag())
			&& BackNo.equals(other.getBackNo())
			&& EndCaseFlag.equals(other.getEndCaseFlag())
			&& Currency.equals(other.getCurrency())
			&& AuditPopedom.equals(other.getAuditPopedom())
			&& UWState.equals(other.getUWState())
			&& CalFlag.equals(other.getCalFlag())
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 1;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 3;
		}
		if( strFieldName.equals("ClmState") ) {
			return 4;
		}
		if( strFieldName.equals("StandPay") ) {
			return 5;
		}
		if( strFieldName.equals("BeforePay") ) {
			return 6;
		}
		if( strFieldName.equals("BalancePay") ) {
			return 7;
		}
		if( strFieldName.equals("RealPay") ) {
			return 8;
		}
		if( strFieldName.equals("DeclinePay") ) {
			return 9;
		}
		if( strFieldName.equals("GiveType") ) {
			return 10;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return 11;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return 12;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return 13;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 14;
		}
		if( strFieldName.equals("CasePayType") ) {
			return 15;
		}
		if( strFieldName.equals("CheckType") ) {
			return 16;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("ConBalFlag") ) {
			return 23;
		}
		if( strFieldName.equals("ConDealFlag") ) {
			return 24;
		}
		if( strFieldName.equals("BackFlag") ) {
			return 25;
		}
		if( strFieldName.equals("BackNo") ) {
			return 26;
		}
		if( strFieldName.equals("EndCaseFlag") ) {
			return 27;
		}
		if( strFieldName.equals("Currency") ) {
			return 28;
		}
		if( strFieldName.equals("AuditPopedom") ) {
			return 29;
		}
		if( strFieldName.equals("UWState") ) {
			return 30;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 31;
		}
		if( strFieldName.equals("ComCode") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 33;
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
				strFieldName = "RgtNo";
				break;
			case 2:
				strFieldName = "CaseNo";
				break;
			case 3:
				strFieldName = "GetDutyKind";
				break;
			case 4:
				strFieldName = "ClmState";
				break;
			case 5:
				strFieldName = "StandPay";
				break;
			case 6:
				strFieldName = "BeforePay";
				break;
			case 7:
				strFieldName = "BalancePay";
				break;
			case 8:
				strFieldName = "RealPay";
				break;
			case 9:
				strFieldName = "DeclinePay";
				break;
			case 10:
				strFieldName = "GiveType";
				break;
			case 11:
				strFieldName = "GiveTypeDesc";
				break;
			case 12:
				strFieldName = "ClmUWer";
				break;
			case 13:
				strFieldName = "DeclineNo";
				break;
			case 14:
				strFieldName = "EndCaseDate";
				break;
			case 15:
				strFieldName = "CasePayType";
				break;
			case 16:
				strFieldName = "CheckType";
				break;
			case 17:
				strFieldName = "MngCom";
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
				strFieldName = "ConBalFlag";
				break;
			case 24:
				strFieldName = "ConDealFlag";
				break;
			case 25:
				strFieldName = "BackFlag";
				break;
			case 26:
				strFieldName = "BackNo";
				break;
			case 27:
				strFieldName = "EndCaseFlag";
				break;
			case 28:
				strFieldName = "Currency";
				break;
			case 29:
				strFieldName = "AuditPopedom";
				break;
			case 30:
				strFieldName = "UWState";
				break;
			case 31:
				strFieldName = "CalFlag";
				break;
			case 32:
				strFieldName = "ComCode";
				break;
			case 33:
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BeforePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalancePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeclinePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GiveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CasePayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckType") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("ConBalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConDealFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndCaseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
