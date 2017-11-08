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
import com.sinosoft.lis.db.LPGrpEdorMainDB;

/*
 * <p>ClassName: LPGrpEdorMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPGrpEdorMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGrpEdorMainSchema.class);
	// @Field
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 批单号 */
	private String EdorNo;
	/** 团体合同号码 */
	private String GrpContNo;
	/** 批改申请号 */
	private String EdorAppNo;
	/** 申请人名称 */
	private String EdorAppName;
	/** 管理机构 */
	private String ManageCom;
	/** 变动的保费 */
	private double ChgPrem;
	/** 变动的保额 */
	private double ChgAmnt;
	/** 补/退费金额 */
	private double GetMoney;
	/** 补/退费利息 */
	private double GetInterest;
	/** 批改申请日期 */
	private Date EdorAppDate;
	/** 批改生效日期 */
	private Date EdorValiDate;
	/** 批改状态 */
	private String EdorState;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 通讯地址 */
	private String PostalAddress;
	/** 通讯邮编 */
	private String ZipCode;
	/** 通讯电话 */
	private String Phone;
	/** 打印标志 */
	private String PrintFlag;
	/** 核保级别 */
	private String UWGrade;
	/** 申请级别 */
	private String AppGrade;
	/** 核保标志 */
	private String UWState;
	/** 核保人 */
	private String UWOperator;
	/** 核保日期 */
	private Date UWDate;
	/** 核保时间 */
	private String UWTime;
	/** 确认人 */
	private String ConfOperator;
	/** 确认日期 */
	private Date ConfDate;
	/** 确认时间 */
	private String ConfTime;
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
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核人 */
	private String ApproveOperator;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 审批级别 */
	private String ApproveGrade;
	/** Gbs给付数据专用字段 */
	private String gbstfno;
	/** 特权确认标志 */
	private String SpecFlag;
	/** 币别 */
	private String Currency;
	/** 受理人员 */
	private String AcceptOperator;
	/** 受理日期 */
	private Date AcceptDate;
	/** 受理时间 */
	private String AcceptTime;
	/** 录入人员 */
	private String InputOperator;
	/** 录入日期 */
	private Date InputDate;
	/** 录入时间 */
	private String InputTime;
	/** 审核结论 */
	private String AuditConclu;
	/** 审核意见 */
	private String AuditOpinion;
	/** 审核人员 */
	private String AuditOperator;
	/** 审核日期 */
	private Date AuditDate;
	/** 审核时间 */
	private String AuditTime;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 55;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGrpEdorMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "EdorAcceptNo";
		pk[1] = "EdorNo";

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
		LPGrpEdorMainSchema cloned = (LPGrpEdorMainSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorAcceptNo()
	{
		return EdorAcceptNo;
	}
	public void setEdorAcceptNo(String aEdorAcceptNo)
	{
		if(aEdorAcceptNo!=null && aEdorAcceptNo.length()>20)
			throw new IllegalArgumentException("保全受理号EdorAcceptNo值"+aEdorAcceptNo+"的长度"+aEdorAcceptNo.length()+"大于最大值20");
		EdorAcceptNo = aEdorAcceptNo;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getEdorAppNo()
	{
		return EdorAppNo;
	}
	public void setEdorAppNo(String aEdorAppNo)
	{
		if(aEdorAppNo!=null && aEdorAppNo.length()>20)
			throw new IllegalArgumentException("批改申请号EdorAppNo值"+aEdorAppNo+"的长度"+aEdorAppNo.length()+"大于最大值20");
		EdorAppNo = aEdorAppNo;
	}
	/**
	* 一般为投保人名称
	*/
	public String getEdorAppName()
	{
		return EdorAppName;
	}
	public void setEdorAppName(String aEdorAppName)
	{
		if(aEdorAppName!=null && aEdorAppName.length()>120)
			throw new IllegalArgumentException("申请人名称EdorAppName值"+aEdorAppName+"的长度"+aEdorAppName.length()+"大于最大值120");
		EdorAppName = aEdorAppName;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
	}
	public double getChgPrem()
	{
		return ChgPrem;
	}
	public void setChgPrem(double aChgPrem)
	{
		ChgPrem = aChgPrem;
	}
	public void setChgPrem(String aChgPrem)
	{
		if (aChgPrem != null && !aChgPrem.equals(""))
		{
			Double tDouble = new Double(aChgPrem);
			double d = tDouble.doubleValue();
			ChgPrem = d;
		}
	}

	public double getChgAmnt()
	{
		return ChgAmnt;
	}
	public void setChgAmnt(double aChgAmnt)
	{
		ChgAmnt = aChgAmnt;
	}
	public void setChgAmnt(String aChgAmnt)
	{
		if (aChgAmnt != null && !aChgAmnt.equals(""))
		{
			Double tDouble = new Double(aChgAmnt);
			double d = tDouble.doubleValue();
			ChgAmnt = d;
		}
	}

	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	public double getGetInterest()
	{
		return GetInterest;
	}
	public void setGetInterest(double aGetInterest)
	{
		GetInterest = aGetInterest;
	}
	public void setGetInterest(String aGetInterest)
	{
		if (aGetInterest != null && !aGetInterest.equals(""))
		{
			Double tDouble = new Double(aGetInterest);
			double d = tDouble.doubleValue();
			GetInterest = d;
		}
	}

	public String getEdorAppDate()
	{
		if( EdorAppDate != null )
			return fDate.getString(EdorAppDate);
		else
			return null;
	}
	public void setEdorAppDate(Date aEdorAppDate)
	{
		EdorAppDate = aEdorAppDate;
	}
	public void setEdorAppDate(String aEdorAppDate)
	{
		if (aEdorAppDate != null && !aEdorAppDate.equals("") )
		{
			EdorAppDate = fDate.getDate( aEdorAppDate );
		}
		else
			EdorAppDate = null;
	}

	public String getEdorValiDate()
	{
		if( EdorValiDate != null )
			return fDate.getString(EdorValiDate);
		else
			return null;
	}
	public void setEdorValiDate(Date aEdorValiDate)
	{
		EdorValiDate = aEdorValiDate;
	}
	public void setEdorValiDate(String aEdorValiDate)
	{
		if (aEdorValiDate != null && !aEdorValiDate.equals("") )
		{
			EdorValiDate = fDate.getDate( aEdorValiDate );
		}
		else
			EdorValiDate = null;
	}

	/**
	* 0 - 确认生效<p>
	* 1 - 录入完成<p>
	* 2 - 申请确认<p>
	* 3 - 等待录入<p>
	* 4 - 逾期终止<p>
	* 5 - 复核修改<p>
	* 6 - 确认未生效<p>
	* 7 - 保全撤销<p>
	* 8 - 核保终止<p>
	* 9 - 复核终止<p>
	* a - 复核通过<p>
	* b - 保全回退<p>
	* <p>
	* 对应LDCode.CodeType = 'edorstate'
	*/
	public String getEdorState()
	{
		return EdorState;
	}
	public void setEdorState(String aEdorState)
	{
		if(aEdorState!=null && aEdorState.length()>2)
			throw new IllegalArgumentException("批改状态EdorState值"+aEdorState+"的长度"+aEdorState.length()+"大于最大值2");
		EdorState = aEdorState;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>10)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值10");
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		if(aBankAccNo!=null && aBankAccNo.length()>40)
			throw new IllegalArgumentException("银行帐号BankAccNo值"+aBankAccNo+"的长度"+aBankAccNo.length()+"大于最大值40");
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>60)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值60");
		AccName = aAccName;
	}
	public String getPostalAddress()
	{
		return PostalAddress;
	}
	public void setPostalAddress(String aPostalAddress)
	{
		if(aPostalAddress!=null && aPostalAddress.length()>80)
			throw new IllegalArgumentException("通讯地址PostalAddress值"+aPostalAddress+"的长度"+aPostalAddress.length()+"大于最大值80");
		PostalAddress = aPostalAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		if(aZipCode!=null && aZipCode.length()>6)
			throw new IllegalArgumentException("通讯邮编ZipCode值"+aZipCode+"的长度"+aZipCode.length()+"大于最大值6");
		ZipCode = aZipCode;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>18)
			throw new IllegalArgumentException("通讯电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值18");
		Phone = aPhone;
	}
	/**
	* 1－不能打印<p>
	* 2－可以打印，未打印<p>
	* 3－已打印
	*/
	public String getPrintFlag()
	{
		return PrintFlag;
	}
	public void setPrintFlag(String aPrintFlag)
	{
		if(aPrintFlag!=null && aPrintFlag.length()>1)
			throw new IllegalArgumentException("打印标志PrintFlag值"+aPrintFlag+"的长度"+aPrintFlag.length()+"大于最大值1");
		PrintFlag = aPrintFlag;
	}
	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		if(aUWGrade!=null && aUWGrade.length()>6)
			throw new IllegalArgumentException("核保级别UWGrade值"+aUWGrade+"的长度"+aUWGrade.length()+"大于最大值6");
		UWGrade = aUWGrade;
	}
	public String getAppGrade()
	{
		return AppGrade;
	}
	public void setAppGrade(String aAppGrade)
	{
		if(aAppGrade!=null && aAppGrade.length()>6)
			throw new IllegalArgumentException("申请级别AppGrade值"+aAppGrade+"的长度"+aAppGrade.length()+"大于最大值6");
		AppGrade = aAppGrade;
	}
	/**
	* 0---申请中未经过核保；<p>
	* 5-----核保但是没有完全通过；<p>
	* 9------核保通过
	*/
	public String getUWState()
	{
		return UWState;
	}
	public void setUWState(String aUWState)
	{
		if(aUWState!=null && aUWState.length()>1)
			throw new IllegalArgumentException("核保标志UWState值"+aUWState+"的长度"+aUWState.length()+"大于最大值1");
		UWState = aUWState;
	}
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		if(aUWOperator!=null && aUWOperator.length()>10)
			throw new IllegalArgumentException("核保人UWOperator值"+aUWOperator+"的长度"+aUWOperator.length()+"大于最大值10");
		UWOperator = aUWOperator;
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

	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
		if(aUWTime!=null && aUWTime.length()>8)
			throw new IllegalArgumentException("核保时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值8");
		UWTime = aUWTime;
	}
	public String getConfOperator()
	{
		return ConfOperator;
	}
	public void setConfOperator(String aConfOperator)
	{
		if(aConfOperator!=null && aConfOperator.length()>10)
			throw new IllegalArgumentException("确认人ConfOperator值"+aConfOperator+"的长度"+aConfOperator.length()+"大于最大值10");
		ConfOperator = aConfOperator;
	}
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

	public String getConfTime()
	{
		return ConfTime;
	}
	public void setConfTime(String aConfTime)
	{
		if(aConfTime!=null && aConfTime.length()>8)
			throw new IllegalArgumentException("确认时间ConfTime值"+aConfTime+"的长度"+aConfTime.length()+"大于最大值8");
		ConfTime = aConfTime;
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
	* 1 复核通过<p>
	* 2 复核修改<p>
	* 3 复核终止<p>
	* <p>
	* 对应LDCode.CodeType = 'edorapproveidea'
	*/
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		if(aApproveFlag!=null && aApproveFlag.length()>1)
			throw new IllegalArgumentException("复核状态ApproveFlag值"+aApproveFlag+"的长度"+aApproveFlag.length()+"大于最大值1");
		ApproveFlag = aApproveFlag;
	}
	public String getApproveOperator()
	{
		return ApproveOperator;
	}
	public void setApproveOperator(String aApproveOperator)
	{
		if(aApproveOperator!=null && aApproveOperator.length()>10)
			throw new IllegalArgumentException("复核人ApproveOperator值"+aApproveOperator+"的长度"+aApproveOperator.length()+"大于最大值10");
		ApproveOperator = aApproveOperator;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		if(aApproveTime!=null && aApproveTime.length()>8)
			throw new IllegalArgumentException("复核时间ApproveTime值"+aApproveTime+"的长度"+aApproveTime.length()+"大于最大值8");
		ApproveTime = aApproveTime;
	}
	public String getApproveGrade()
	{
		return ApproveGrade;
	}
	public void setApproveGrade(String aApproveGrade)
	{
		if(aApproveGrade!=null && aApproveGrade.length()>1)
			throw new IllegalArgumentException("审批级别ApproveGrade值"+aApproveGrade+"的长度"+aApproveGrade.length()+"大于最大值1");
		ApproveGrade = aApproveGrade;
	}
	public String getgbstfno()
	{
		return gbstfno;
	}
	public void setgbstfno(String agbstfno)
	{
		if(agbstfno!=null && agbstfno.length()>20)
			throw new IllegalArgumentException("Gbs给付数据专用字段gbstfno值"+agbstfno+"的长度"+agbstfno.length()+"大于最大值20");
		gbstfno = agbstfno;
	}
	/**
	* Y：特权确认
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		if(aSpecFlag!=null && aSpecFlag.length()>10)
			throw new IllegalArgumentException("特权确认标志SpecFlag值"+aSpecFlag+"的长度"+aSpecFlag.length()+"大于最大值10");
		SpecFlag = aSpecFlag;
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
	public String getAcceptOperator()
	{
		return AcceptOperator;
	}
	public void setAcceptOperator(String aAcceptOperator)
	{
		if(aAcceptOperator!=null && aAcceptOperator.length()>30)
			throw new IllegalArgumentException("受理人员AcceptOperator值"+aAcceptOperator+"的长度"+aAcceptOperator.length()+"大于最大值30");
		AcceptOperator = aAcceptOperator;
	}
	public String getAcceptDate()
	{
		if( AcceptDate != null )
			return fDate.getString(AcceptDate);
		else
			return null;
	}
	public void setAcceptDate(Date aAcceptDate)
	{
		AcceptDate = aAcceptDate;
	}
	public void setAcceptDate(String aAcceptDate)
	{
		if (aAcceptDate != null && !aAcceptDate.equals("") )
		{
			AcceptDate = fDate.getDate( aAcceptDate );
		}
		else
			AcceptDate = null;
	}

	public String getAcceptTime()
	{
		return AcceptTime;
	}
	public void setAcceptTime(String aAcceptTime)
	{
		if(aAcceptTime!=null && aAcceptTime.length()>8)
			throw new IllegalArgumentException("受理时间AcceptTime值"+aAcceptTime+"的长度"+aAcceptTime.length()+"大于最大值8");
		AcceptTime = aAcceptTime;
	}
	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		if(aInputOperator!=null && aInputOperator.length()>30)
			throw new IllegalArgumentException("录入人员InputOperator值"+aInputOperator+"的长度"+aInputOperator.length()+"大于最大值30");
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
		if(aInputTime!=null && aInputTime.length()>8)
			throw new IllegalArgumentException("录入时间InputTime值"+aInputTime+"的长度"+aInputTime.length()+"大于最大值8");
		InputTime = aInputTime;
	}
	public String getAuditConclu()
	{
		return AuditConclu;
	}
	public void setAuditConclu(String aAuditConclu)
	{
		if(aAuditConclu!=null && aAuditConclu.length()>2)
			throw new IllegalArgumentException("审核结论AuditConclu值"+aAuditConclu+"的长度"+aAuditConclu.length()+"大于最大值2");
		AuditConclu = aAuditConclu;
	}
	public String getAuditOpinion()
	{
		return AuditOpinion;
	}
	public void setAuditOpinion(String aAuditOpinion)
	{
		if(aAuditOpinion!=null && aAuditOpinion.length()>3000)
			throw new IllegalArgumentException("审核意见AuditOpinion值"+aAuditOpinion+"的长度"+aAuditOpinion.length()+"大于最大值3000");
		AuditOpinion = aAuditOpinion;
	}
	public String getAuditOperator()
	{
		return AuditOperator;
	}
	public void setAuditOperator(String aAuditOperator)
	{
		if(aAuditOperator!=null && aAuditOperator.length()>30)
			throw new IllegalArgumentException("审核人员AuditOperator值"+aAuditOperator+"的长度"+aAuditOperator.length()+"大于最大值30");
		AuditOperator = aAuditOperator;
	}
	public String getAuditDate()
	{
		if( AuditDate != null )
			return fDate.getString(AuditDate);
		else
			return null;
	}
	public void setAuditDate(Date aAuditDate)
	{
		AuditDate = aAuditDate;
	}
	public void setAuditDate(String aAuditDate)
	{
		if (aAuditDate != null && !aAuditDate.equals("") )
		{
			AuditDate = fDate.getDate( aAuditDate );
		}
		else
			AuditDate = null;
	}

	public String getAuditTime()
	{
		return AuditTime;
	}
	public void setAuditTime(String aAuditTime)
	{
		if(aAuditTime!=null && aAuditTime.length()>8)
			throw new IllegalArgumentException("审核时间AuditTime值"+aAuditTime+"的长度"+aAuditTime.length()+"大于最大值8");
		AuditTime = aAuditTime;
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
	* 使用另外一个 LPGrpEdorMainSchema 对象给 Schema 赋值
	* @param: aLPGrpEdorMainSchema LPGrpEdorMainSchema
	**/
	public void setSchema(LPGrpEdorMainSchema aLPGrpEdorMainSchema)
	{
		this.EdorAcceptNo = aLPGrpEdorMainSchema.getEdorAcceptNo();
		this.EdorNo = aLPGrpEdorMainSchema.getEdorNo();
		this.GrpContNo = aLPGrpEdorMainSchema.getGrpContNo();
		this.EdorAppNo = aLPGrpEdorMainSchema.getEdorAppNo();
		this.EdorAppName = aLPGrpEdorMainSchema.getEdorAppName();
		this.ManageCom = aLPGrpEdorMainSchema.getManageCom();
		this.ChgPrem = aLPGrpEdorMainSchema.getChgPrem();
		this.ChgAmnt = aLPGrpEdorMainSchema.getChgAmnt();
		this.GetMoney = aLPGrpEdorMainSchema.getGetMoney();
		this.GetInterest = aLPGrpEdorMainSchema.getGetInterest();
		this.EdorAppDate = fDate.getDate( aLPGrpEdorMainSchema.getEdorAppDate());
		this.EdorValiDate = fDate.getDate( aLPGrpEdorMainSchema.getEdorValiDate());
		this.EdorState = aLPGrpEdorMainSchema.getEdorState();
		this.BankCode = aLPGrpEdorMainSchema.getBankCode();
		this.BankAccNo = aLPGrpEdorMainSchema.getBankAccNo();
		this.AccName = aLPGrpEdorMainSchema.getAccName();
		this.PostalAddress = aLPGrpEdorMainSchema.getPostalAddress();
		this.ZipCode = aLPGrpEdorMainSchema.getZipCode();
		this.Phone = aLPGrpEdorMainSchema.getPhone();
		this.PrintFlag = aLPGrpEdorMainSchema.getPrintFlag();
		this.UWGrade = aLPGrpEdorMainSchema.getUWGrade();
		this.AppGrade = aLPGrpEdorMainSchema.getAppGrade();
		this.UWState = aLPGrpEdorMainSchema.getUWState();
		this.UWOperator = aLPGrpEdorMainSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLPGrpEdorMainSchema.getUWDate());
		this.UWTime = aLPGrpEdorMainSchema.getUWTime();
		this.ConfOperator = aLPGrpEdorMainSchema.getConfOperator();
		this.ConfDate = fDate.getDate( aLPGrpEdorMainSchema.getConfDate());
		this.ConfTime = aLPGrpEdorMainSchema.getConfTime();
		this.Operator = aLPGrpEdorMainSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPGrpEdorMainSchema.getMakeDate());
		this.MakeTime = aLPGrpEdorMainSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPGrpEdorMainSchema.getModifyDate());
		this.ModifyTime = aLPGrpEdorMainSchema.getModifyTime();
		this.ApproveFlag = aLPGrpEdorMainSchema.getApproveFlag();
		this.ApproveOperator = aLPGrpEdorMainSchema.getApproveOperator();
		this.ApproveDate = fDate.getDate( aLPGrpEdorMainSchema.getApproveDate());
		this.ApproveTime = aLPGrpEdorMainSchema.getApproveTime();
		this.ApproveGrade = aLPGrpEdorMainSchema.getApproveGrade();
		this.gbstfno = aLPGrpEdorMainSchema.getgbstfno();
		this.SpecFlag = aLPGrpEdorMainSchema.getSpecFlag();
		this.Currency = aLPGrpEdorMainSchema.getCurrency();
		this.AcceptOperator = aLPGrpEdorMainSchema.getAcceptOperator();
		this.AcceptDate = fDate.getDate( aLPGrpEdorMainSchema.getAcceptDate());
		this.AcceptTime = aLPGrpEdorMainSchema.getAcceptTime();
		this.InputOperator = aLPGrpEdorMainSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLPGrpEdorMainSchema.getInputDate());
		this.InputTime = aLPGrpEdorMainSchema.getInputTime();
		this.AuditConclu = aLPGrpEdorMainSchema.getAuditConclu();
		this.AuditOpinion = aLPGrpEdorMainSchema.getAuditOpinion();
		this.AuditOperator = aLPGrpEdorMainSchema.getAuditOperator();
		this.AuditDate = fDate.getDate( aLPGrpEdorMainSchema.getAuditDate());
		this.AuditTime = aLPGrpEdorMainSchema.getAuditTime();
		this.ComCode = aLPGrpEdorMainSchema.getComCode();
		this.ModifyOperator = aLPGrpEdorMainSchema.getModifyOperator();
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
			if( rs.getString("EdorAcceptNo") == null )
				this.EdorAcceptNo = null;
			else
				this.EdorAcceptNo = rs.getString("EdorAcceptNo").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("EdorAppNo") == null )
				this.EdorAppNo = null;
			else
				this.EdorAppNo = rs.getString("EdorAppNo").trim();

			if( rs.getString("EdorAppName") == null )
				this.EdorAppName = null;
			else
				this.EdorAppName = rs.getString("EdorAppName").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.ChgPrem = rs.getDouble("ChgPrem");
			this.ChgAmnt = rs.getDouble("ChgAmnt");
			this.GetMoney = rs.getDouble("GetMoney");
			this.GetInterest = rs.getDouble("GetInterest");
			this.EdorAppDate = rs.getDate("EdorAppDate");
			this.EdorValiDate = rs.getDate("EdorValiDate");
			if( rs.getString("EdorState") == null )
				this.EdorState = null;
			else
				this.EdorState = rs.getString("EdorState").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("PostalAddress") == null )
				this.PostalAddress = null;
			else
				this.PostalAddress = rs.getString("PostalAddress").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("PrintFlag") == null )
				this.PrintFlag = null;
			else
				this.PrintFlag = rs.getString("PrintFlag").trim();

			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

			if( rs.getString("AppGrade") == null )
				this.AppGrade = null;
			else
				this.AppGrade = rs.getString("AppGrade").trim();

			if( rs.getString("UWState") == null )
				this.UWState = null;
			else
				this.UWState = rs.getString("UWState").trim();

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("ConfOperator") == null )
				this.ConfOperator = null;
			else
				this.ConfOperator = rs.getString("ConfOperator").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ConfTime") == null )
				this.ConfTime = null;
			else
				this.ConfTime = rs.getString("ConfTime").trim();

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

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveOperator") == null )
				this.ApproveOperator = null;
			else
				this.ApproveOperator = rs.getString("ApproveOperator").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("ApproveGrade") == null )
				this.ApproveGrade = null;
			else
				this.ApproveGrade = rs.getString("ApproveGrade").trim();

			if( rs.getString("gbstfno") == null )
				this.gbstfno = null;
			else
				this.gbstfno = rs.getString("gbstfno").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("AcceptOperator") == null )
				this.AcceptOperator = null;
			else
				this.AcceptOperator = rs.getString("AcceptOperator").trim();

			this.AcceptDate = rs.getDate("AcceptDate");
			if( rs.getString("AcceptTime") == null )
				this.AcceptTime = null;
			else
				this.AcceptTime = rs.getString("AcceptTime").trim();

			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

			if( rs.getString("AuditConclu") == null )
				this.AuditConclu = null;
			else
				this.AuditConclu = rs.getString("AuditConclu").trim();

			if( rs.getString("AuditOpinion") == null )
				this.AuditOpinion = null;
			else
				this.AuditOpinion = rs.getString("AuditOpinion").trim();

			if( rs.getString("AuditOperator") == null )
				this.AuditOperator = null;
			else
				this.AuditOperator = rs.getString("AuditOperator").trim();

			this.AuditDate = rs.getDate("AuditDate");
			if( rs.getString("AuditTime") == null )
				this.AuditTime = null;
			else
				this.AuditTime = rs.getString("AuditTime").trim();

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
			logger.debug("数据库中的LPGrpEdorMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpEdorMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGrpEdorMainSchema getSchema()
	{
		LPGrpEdorMainSchema aLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		aLPGrpEdorMainSchema.setSchema(this);
		return aLPGrpEdorMainSchema;
	}

	public LPGrpEdorMainDB getDB()
	{
		LPGrpEdorMainDB aDBOper = new LPGrpEdorMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpEdorMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAppName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorAppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostalAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(gbstfno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AcceptDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AuditDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpEdorMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EdorAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EdorAppName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ChgPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			ChgAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			GetInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			EdorAppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EdorValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			EdorState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PrintFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AppGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			UWState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ConfOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ConfTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ApproveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ApproveGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			gbstfno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			AcceptOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			AcceptDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			AcceptTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			AuditConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			AuditOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			AuditOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			AuditDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			AuditTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpEdorMainSchema";
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
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAcceptNo));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAppNo));
		}
		if (FCode.equalsIgnoreCase("EdorAppName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAppName));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgPrem));
		}
		if (FCode.equalsIgnoreCase("ChgAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgAmnt));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetInterest));
		}
		if (FCode.equalsIgnoreCase("EdorAppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
		}
		if (FCode.equalsIgnoreCase("EdorValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
		}
		if (FCode.equalsIgnoreCase("EdorState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorState));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostalAddress));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("PrintFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintFlag));
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
		}
		if (FCode.equalsIgnoreCase("AppGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppGrade));
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWState));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfOperator));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfTime));
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
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveOperator));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("ApproveGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveGrade));
		}
		if (FCode.equalsIgnoreCase("gbstfno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(gbstfno));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("AcceptOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptOperator));
		}
		if (FCode.equalsIgnoreCase("AcceptDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcceptDate()));
		}
		if (FCode.equalsIgnoreCase("AcceptTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptTime));
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
		if (FCode.equalsIgnoreCase("AuditConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditConclu));
		}
		if (FCode.equalsIgnoreCase("AuditOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditOpinion));
		}
		if (FCode.equalsIgnoreCase("AuditOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditOperator));
		}
		if (FCode.equalsIgnoreCase("AuditDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
		}
		if (FCode.equalsIgnoreCase("AuditTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditTime));
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
				strFieldValue = StrTool.GBKToUnicode(EdorAcceptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EdorAppNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EdorAppName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = String.valueOf(ChgPrem);
				break;
			case 7:
				strFieldValue = String.valueOf(ChgAmnt);
				break;
			case 8:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 9:
				strFieldValue = String.valueOf(GetInterest);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(EdorState);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PrintFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AppGrade);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(UWState);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ConfOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ConfTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ApproveOperator);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ApproveGrade);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(gbstfno);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(AcceptOperator);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcceptDate()));
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(AcceptTime);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(AuditConclu);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(AuditOpinion);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(AuditOperator);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(AuditTime);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 54:
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

		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAcceptNo = FValue.trim();
			}
			else
				EdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAppNo = FValue.trim();
			}
			else
				EdorAppNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorAppName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAppName = FValue.trim();
			}
			else
				EdorAppName = null;
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
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChgAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("EdorAppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorAppDate = fDate.getDate( FValue );
			}
			else
				EdorAppDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorValiDate = fDate.getDate( FValue );
			}
			else
				EdorValiDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorState = FValue.trim();
			}
			else
				EdorState = null;
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostalAddress = FValue.trim();
			}
			else
				PostalAddress = null;
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
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
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
		}
		if (FCode.equalsIgnoreCase("AppGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppGrade = FValue.trim();
			}
			else
				AppGrade = null;
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
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
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
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWTime = FValue.trim();
			}
			else
				UWTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfOperator = FValue.trim();
			}
			else
				ConfOperator = null;
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
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfTime = FValue.trim();
			}
			else
				ConfTime = null;
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
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
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
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
		}
		if (FCode.equalsIgnoreCase("ApproveGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveGrade = FValue.trim();
			}
			else
				ApproveGrade = null;
		}
		if (FCode.equalsIgnoreCase("gbstfno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				gbstfno = FValue.trim();
			}
			else
				gbstfno = null;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("AcceptOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptOperator = FValue.trim();
			}
			else
				AcceptOperator = null;
		}
		if (FCode.equalsIgnoreCase("AcceptDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AcceptDate = fDate.getDate( FValue );
			}
			else
				AcceptDate = null;
		}
		if (FCode.equalsIgnoreCase("AcceptTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptTime = FValue.trim();
			}
			else
				AcceptTime = null;
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
		if (FCode.equalsIgnoreCase("AuditConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditConclu = FValue.trim();
			}
			else
				AuditConclu = null;
		}
		if (FCode.equalsIgnoreCase("AuditOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditOpinion = FValue.trim();
			}
			else
				AuditOpinion = null;
		}
		if (FCode.equalsIgnoreCase("AuditOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditOperator = FValue.trim();
			}
			else
				AuditOperator = null;
		}
		if (FCode.equalsIgnoreCase("AuditDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AuditDate = fDate.getDate( FValue );
			}
			else
				AuditDate = null;
		}
		if (FCode.equalsIgnoreCase("AuditTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditTime = FValue.trim();
			}
			else
				AuditTime = null;
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
		LPGrpEdorMainSchema other = (LPGrpEdorMainSchema)otherObject;
		return
			EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& EdorNo.equals(other.getEdorNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& EdorAppNo.equals(other.getEdorAppNo())
			&& EdorAppName.equals(other.getEdorAppName())
			&& ManageCom.equals(other.getManageCom())
			&& ChgPrem == other.getChgPrem()
			&& ChgAmnt == other.getChgAmnt()
			&& GetMoney == other.getGetMoney()
			&& GetInterest == other.getGetInterest()
			&& fDate.getString(EdorAppDate).equals(other.getEdorAppDate())
			&& fDate.getString(EdorValiDate).equals(other.getEdorValiDate())
			&& EdorState.equals(other.getEdorState())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& PostalAddress.equals(other.getPostalAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& PrintFlag.equals(other.getPrintFlag())
			&& UWGrade.equals(other.getUWGrade())
			&& AppGrade.equals(other.getAppGrade())
			&& UWState.equals(other.getUWState())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& ConfOperator.equals(other.getConfOperator())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ConfTime.equals(other.getConfTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveOperator.equals(other.getApproveOperator())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& ApproveGrade.equals(other.getApproveGrade())
			&& gbstfno.equals(other.getgbstfno())
			&& SpecFlag.equals(other.getSpecFlag())
			&& Currency.equals(other.getCurrency())
			&& AcceptOperator.equals(other.getAcceptOperator())
			&& fDate.getString(AcceptDate).equals(other.getAcceptDate())
			&& AcceptTime.equals(other.getAcceptTime())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& AuditConclu.equals(other.getAuditConclu())
			&& AuditOpinion.equals(other.getAuditOpinion())
			&& AuditOperator.equals(other.getAuditOperator())
			&& fDate.getString(AuditDate).equals(other.getAuditDate())
			&& AuditTime.equals(other.getAuditTime())
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return 3;
		}
		if( strFieldName.equals("EdorAppName") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return 6;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return 7;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 8;
		}
		if( strFieldName.equals("GetInterest") ) {
			return 9;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return 10;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return 11;
		}
		if( strFieldName.equals("EdorState") ) {
			return 12;
		}
		if( strFieldName.equals("BankCode") ) {
			return 13;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 14;
		}
		if( strFieldName.equals("AccName") ) {
			return 15;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 16;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 17;
		}
		if( strFieldName.equals("Phone") ) {
			return 18;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return 19;
		}
		if( strFieldName.equals("UWGrade") ) {
			return 20;
		}
		if( strFieldName.equals("AppGrade") ) {
			return 21;
		}
		if( strFieldName.equals("UWState") ) {
			return 22;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 23;
		}
		if( strFieldName.equals("UWDate") ) {
			return 24;
		}
		if( strFieldName.equals("UWTime") ) {
			return 25;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return 26;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 27;
		}
		if( strFieldName.equals("ConfTime") ) {
			return 28;
		}
		if( strFieldName.equals("Operator") ) {
			return 29;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 30;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 33;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 34;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return 35;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 36;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 37;
		}
		if( strFieldName.equals("ApproveGrade") ) {
			return 38;
		}
		if( strFieldName.equals("gbstfno") ) {
			return 39;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 40;
		}
		if( strFieldName.equals("Currency") ) {
			return 41;
		}
		if( strFieldName.equals("AcceptOperator") ) {
			return 42;
		}
		if( strFieldName.equals("AcceptDate") ) {
			return 43;
		}
		if( strFieldName.equals("AcceptTime") ) {
			return 44;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 45;
		}
		if( strFieldName.equals("InputDate") ) {
			return 46;
		}
		if( strFieldName.equals("InputTime") ) {
			return 47;
		}
		if( strFieldName.equals("AuditConclu") ) {
			return 48;
		}
		if( strFieldName.equals("AuditOpinion") ) {
			return 49;
		}
		if( strFieldName.equals("AuditOperator") ) {
			return 50;
		}
		if( strFieldName.equals("AuditDate") ) {
			return 51;
		}
		if( strFieldName.equals("AuditTime") ) {
			return 52;
		}
		if( strFieldName.equals("ComCode") ) {
			return 53;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 54;
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
				strFieldName = "EdorAcceptNo";
				break;
			case 1:
				strFieldName = "EdorNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "EdorAppNo";
				break;
			case 4:
				strFieldName = "EdorAppName";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "ChgPrem";
				break;
			case 7:
				strFieldName = "ChgAmnt";
				break;
			case 8:
				strFieldName = "GetMoney";
				break;
			case 9:
				strFieldName = "GetInterest";
				break;
			case 10:
				strFieldName = "EdorAppDate";
				break;
			case 11:
				strFieldName = "EdorValiDate";
				break;
			case 12:
				strFieldName = "EdorState";
				break;
			case 13:
				strFieldName = "BankCode";
				break;
			case 14:
				strFieldName = "BankAccNo";
				break;
			case 15:
				strFieldName = "AccName";
				break;
			case 16:
				strFieldName = "PostalAddress";
				break;
			case 17:
				strFieldName = "ZipCode";
				break;
			case 18:
				strFieldName = "Phone";
				break;
			case 19:
				strFieldName = "PrintFlag";
				break;
			case 20:
				strFieldName = "UWGrade";
				break;
			case 21:
				strFieldName = "AppGrade";
				break;
			case 22:
				strFieldName = "UWState";
				break;
			case 23:
				strFieldName = "UWOperator";
				break;
			case 24:
				strFieldName = "UWDate";
				break;
			case 25:
				strFieldName = "UWTime";
				break;
			case 26:
				strFieldName = "ConfOperator";
				break;
			case 27:
				strFieldName = "ConfDate";
				break;
			case 28:
				strFieldName = "ConfTime";
				break;
			case 29:
				strFieldName = "Operator";
				break;
			case 30:
				strFieldName = "MakeDate";
				break;
			case 31:
				strFieldName = "MakeTime";
				break;
			case 32:
				strFieldName = "ModifyDate";
				break;
			case 33:
				strFieldName = "ModifyTime";
				break;
			case 34:
				strFieldName = "ApproveFlag";
				break;
			case 35:
				strFieldName = "ApproveOperator";
				break;
			case 36:
				strFieldName = "ApproveDate";
				break;
			case 37:
				strFieldName = "ApproveTime";
				break;
			case 38:
				strFieldName = "ApproveGrade";
				break;
			case 39:
				strFieldName = "gbstfno";
				break;
			case 40:
				strFieldName = "SpecFlag";
				break;
			case 41:
				strFieldName = "Currency";
				break;
			case 42:
				strFieldName = "AcceptOperator";
				break;
			case 43:
				strFieldName = "AcceptDate";
				break;
			case 44:
				strFieldName = "AcceptTime";
				break;
			case 45:
				strFieldName = "InputOperator";
				break;
			case 46:
				strFieldName = "InputDate";
				break;
			case 47:
				strFieldName = "InputTime";
				break;
			case 48:
				strFieldName = "AuditConclu";
				break;
			case 49:
				strFieldName = "AuditOpinion";
				break;
			case 50:
				strFieldName = "AuditOperator";
				break;
			case 51:
				strFieldName = "AuditDate";
				break;
			case 52:
				strFieldName = "AuditTime";
				break;
			case 53:
				strFieldName = "ComCode";
				break;
			case 54:
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorAppName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfTime") ) {
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
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("gbstfno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AcceptTime") ) {
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
		if( strFieldName.equals("AuditConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AuditTime") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
			case 32:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
