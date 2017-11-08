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
import com.sinosoft.lis.db.LLCaseDB;

/*
 * <p>ClassName: LLCaseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 */
public class LLCaseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseSchema.class);
	// @Field
	/** 分案号(赔案号) */
	private String CaseNo;
	/** 立案号(赔案号) */
	private String RgtNo;
	/** 案件类型 */
	private String RgtType;
	/** 案件状态 */
	private String RgtState;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 出险类型 */
	private String AccidentType;
	/** 收据信息标志 */
	private String ReceiptFlag;
	/** 医院信息标志 */
	private String HospitalFlag;
	/** 立案日期 */
	private Date RgtDate;
	/** 理算日期 */
	private Date ClaimCalDate;
	/** 材料齐备日期 */
	private Date AffixGetDate;
	/** 入院日期 */
	private Date InHospitalDate;
	/** 出院日期 */
	private Date OutHospitalDate;
	/** 无效住院天数 */
	private int InvaliHosDays;
	/** 实际住院天数 */
	private int InHospitalDays;
	/** 确诊日期 */
	private Date DianoseDate;
	/** 联系地址 */
	private String PostalAddress;
	/** 联系电话 */
	private String Phone;
	/** 出险开始日期 */
	private Date AccStartDate;
	/** 出险结束日期 */
	private Date AccidentDate;
	/** 出险地点 */
	private String AccidentSite;
	/** 死亡日期 */
	private Date DeathDate;
	/** 死亡标志 */
	private String DieFlag;
	/** 非正常修改状态 */
	private String CustState;
	/** 事故经过描述 */
	private String AccdentDesc;
	/** 出险人生日 */
	private Date CustBirthday;
	/** 出险人性别 */
	private String CustomerSex;
	/** 出险人年龄 */
	private double CustomerAge;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 审核人 */
	private String Handler;
	/** 审核日期 */
	private Date HandleDate;
	/** 审核状态 */
	private String UWState;
	/** 当前处理人 */
	private String Dealer;
	/** 申诉标志 */
	private String AppealFlag;
	/** 全部保单是否统一给付 */
	private String TogetherGet;
	/** 团体批处理标志 */
	private String GrpDealFlag;
	/** 赔付金领取方式 */
	private String GetMode;
	/** 赔付金领取间隔 */
	private int GetIntv;
	/** 核算标记 */
	private String CalFlag;
	/** 核赔标记 */
	private String UWFlag;
	/** 拒赔标记 */
	private String DeclineFlag;
	/** 结案标记 */
	private String EndCaseFlag;
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
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 保险金领取方式 */
	private String CaseGetMode;
	/** 账户修改原因 */
	private String AccModifyReason;
	/** 报备产生日期 */
	private Date CaseNoDate;
	/** Vip标志 */
	private String VIPFlag;
	/** 出险细节 */
	private String AccidentDetail;
	/** 治疗情况 */
	private String CureDesc;
	/** 单证检查结论 */
	private String AffixConclusion;
	/** 单证不全原因 */
	private String AffixReason;
	/** 账单录入标记 */
	private String FeeInputFlag;
	/** 调查报告标志 */
	private String SurveyFlag;
	/** 发起呈报标志 */
	private String SubmitFlag;
	/** 提起慰问标志 */
	private String CondoleFlag;
	/** 信息修改标志 */
	private String EditFlag;
	/** 二核标志 */
	private String SecondUWFlag;
	/** 其他出险日期 */
	private Date AccDate;
	/** 出险结果1 */
	private String AccResult1;
	/** 出险结果2 */
	private String AccResult2;
	/** 阳性标志 */
	private String MasculineFlag;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 备注 */
	private String Remark;
	/** 客户序号 */
	private int SeqNo;
	/** 个人预估金额 */
	private double Standpay;
	/** 医疗出险日期 */
	private Date MedAccDate;
	/** 收款人姓名 */
	private String BnfName;
	/** 受益人证件号码 */
	private String BnfIDNo;
	/** 受益人与被保人关系 */
	private String RelationToInsured;
	/** 受益人证件类型 */
	private String BnfIDType;
	/** 受益人出生日期 */
	private Date Birthday;
	/** 受益人性别 */
	private String BnfSex;
	/** 受益人银行账户名 */
	private String BnfAccName;
	/** 赔付金额 */
	private double RealPay;
	/** 医院类别 */
	private String HospitalClass;
	/** 出险地点（省） */
	private String AccProvince;
	/** 出险地点（市） */
	private String AccCity;
	/** 出险地点（区/县） */
	private String AccCounty;
	/** 出险地点2 */
	private String AccSite;
	/** 事件来源 */
	private String CaseSource;
	/** 事件状态 */
	private String CaseState;
	/** 关闭事件原因 */
	private String CloseCaseReason;
	/** 关闭事件备注 */
	private String CloseCaseRemark;
	/** 乐容回写事件号 */
	private String LRCaseNo;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** Column_99 */
	private String ContNo;

	public static final int FIELDNUM = 99;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CaseNo";
		pk[1] = "CustomerNo";

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
		LLCaseSchema cloned = (LLCaseSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号(赔案号)CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	/**
	* 如果是个险，等同于个人赔案号<p>
	* 如果是团险，等同于团体赔案号
	*/
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号(赔案号)RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	/**
	* 【不用】<p>
	* 01简易案件<p>
	* 11普通案件<p>
	* 12诉讼案件<p>
	* 13申诉案件<p>
	* 14疑难案件
	*/
	public String getRgtType()
	{
		return RgtType;
	}
	public void setRgtType(String aRgtType)
	{
		if(aRgtType!=null && aRgtType.length()>6)
			throw new IllegalArgumentException("案件类型RgtType值"+aRgtType+"的长度"+aRgtType.length()+"大于最大值6");
		RgtType = aRgtType;
	}
	/**
	* 【不用】<p>
	* <p>
	* 01 简易案件<p>
	* 02 帐户案件<p>
	* 03 批量案件<p>
	* 11 普通案件<p>
	* 12 诉讼案件<p>
	* 13 申诉案件<p>
	* 14 疑难案件
	*/
	public String getRgtState()
	{
		return RgtState;
	}
	public void setRgtState(String aRgtState)
	{
		if(aRgtState!=null && aRgtState.length()>6)
			throw new IllegalArgumentException("案件状态RgtState值"+aRgtState+"的长度"+aRgtState.length()+"大于最大值6");
		RgtState = aRgtState;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	/**
	* 【不用】
	*/
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>200)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值200");
		CustomerName = aCustomerName;
	}
	/**
	* 【不用】<p>
	* 1 －－ 意外<p>
	* 2 －－ 疾病
	*/
	public String getAccidentType()
	{
		return AccidentType;
	}
	public void setAccidentType(String aAccidentType)
	{
		if(aAccidentType!=null && aAccidentType.length()>6)
			throw new IllegalArgumentException("出险类型AccidentType值"+aAccidentType+"的长度"+aAccidentType.length()+"大于最大值6");
		AccidentType = aAccidentType;
	}
	/**
	* 【不用】<p>
	* 0有<p>
	* 1没有
	*/
	public String getReceiptFlag()
	{
		return ReceiptFlag;
	}
	public void setReceiptFlag(String aReceiptFlag)
	{
		if(aReceiptFlag!=null && aReceiptFlag.length()>6)
			throw new IllegalArgumentException("收据信息标志ReceiptFlag值"+aReceiptFlag+"的长度"+aReceiptFlag.length()+"大于最大值6");
		ReceiptFlag = aReceiptFlag;
	}
	/**
	* 【不用】<p>
	* 0有<p>
	* 1没有
	*/
	public String getHospitalFlag()
	{
		return HospitalFlag;
	}
	public void setHospitalFlag(String aHospitalFlag)
	{
		if(aHospitalFlag!=null && aHospitalFlag.length()>6)
			throw new IllegalArgumentException("医院信息标志HospitalFlag值"+aHospitalFlag+"的长度"+aHospitalFlag.length()+"大于最大值6");
		HospitalFlag = aHospitalFlag;
	}
	public String getRgtDate()
	{
		if( RgtDate != null )
			return fDate.getString(RgtDate);
		else
			return null;
	}
	public void setRgtDate(Date aRgtDate)
	{
		RgtDate = aRgtDate;
	}
	public void setRgtDate(String aRgtDate)
	{
		if (aRgtDate != null && !aRgtDate.equals("") )
		{
			RgtDate = fDate.getDate( aRgtDate );
		}
		else
			RgtDate = null;
	}

	/**
	* 【不用】
	*/
	public String getClaimCalDate()
	{
		if( ClaimCalDate != null )
			return fDate.getString(ClaimCalDate);
		else
			return null;
	}
	public void setClaimCalDate(Date aClaimCalDate)
	{
		ClaimCalDate = aClaimCalDate;
	}
	public void setClaimCalDate(String aClaimCalDate)
	{
		if (aClaimCalDate != null && !aClaimCalDate.equals("") )
		{
			ClaimCalDate = fDate.getDate( aClaimCalDate );
		}
		else
			ClaimCalDate = null;
	}

	/**
	* 【不用】
	*/
	public String getAffixGetDate()
	{
		if( AffixGetDate != null )
			return fDate.getString(AffixGetDate);
		else
			return null;
	}
	public void setAffixGetDate(Date aAffixGetDate)
	{
		AffixGetDate = aAffixGetDate;
	}
	public void setAffixGetDate(String aAffixGetDate)
	{
		if (aAffixGetDate != null && !aAffixGetDate.equals("") )
		{
			AffixGetDate = fDate.getDate( aAffixGetDate );
		}
		else
			AffixGetDate = null;
	}

	/**
	* 【不用】
	*/
	public String getInHospitalDate()
	{
		if( InHospitalDate != null )
			return fDate.getString(InHospitalDate);
		else
			return null;
	}
	public void setInHospitalDate(Date aInHospitalDate)
	{
		InHospitalDate = aInHospitalDate;
	}
	public void setInHospitalDate(String aInHospitalDate)
	{
		if (aInHospitalDate != null && !aInHospitalDate.equals("") )
		{
			InHospitalDate = fDate.getDate( aInHospitalDate );
		}
		else
			InHospitalDate = null;
	}

	/**
	* 【不用】
	*/
	public String getOutHospitalDate()
	{
		if( OutHospitalDate != null )
			return fDate.getString(OutHospitalDate);
		else
			return null;
	}
	public void setOutHospitalDate(Date aOutHospitalDate)
	{
		OutHospitalDate = aOutHospitalDate;
	}
	public void setOutHospitalDate(String aOutHospitalDate)
	{
		if (aOutHospitalDate != null && !aOutHospitalDate.equals("") )
		{
			OutHospitalDate = fDate.getDate( aOutHospitalDate );
		}
		else
			OutHospitalDate = null;
	}

	/**
	* 【不用】
	*/
	public int getInvaliHosDays()
	{
		return InvaliHosDays;
	}
	public void setInvaliHosDays(int aInvaliHosDays)
	{
		InvaliHosDays = aInvaliHosDays;
	}
	public void setInvaliHosDays(String aInvaliHosDays)
	{
		if (aInvaliHosDays != null && !aInvaliHosDays.equals(""))
		{
			Integer tInteger = new Integer(aInvaliHosDays);
			int i = tInteger.intValue();
			InvaliHosDays = i;
		}
	}

	/**
	* 【不用】
	*/
	public int getInHospitalDays()
	{
		return InHospitalDays;
	}
	public void setInHospitalDays(int aInHospitalDays)
	{
		InHospitalDays = aInHospitalDays;
	}
	public void setInHospitalDays(String aInHospitalDays)
	{
		if (aInHospitalDays != null && !aInHospitalDays.equals(""))
		{
			Integer tInteger = new Integer(aInHospitalDays);
			int i = tInteger.intValue();
			InHospitalDays = i;
		}
	}

	/**
	* 【不用】
	*/
	public String getDianoseDate()
	{
		if( DianoseDate != null )
			return fDate.getString(DianoseDate);
		else
			return null;
	}
	public void setDianoseDate(Date aDianoseDate)
	{
		DianoseDate = aDianoseDate;
	}
	public void setDianoseDate(String aDianoseDate)
	{
		if (aDianoseDate != null && !aDianoseDate.equals("") )
		{
			DianoseDate = fDate.getDate( aDianoseDate );
		}
		else
			DianoseDate = null;
	}

	/**
	* 【不用】
	*/
	public String getPostalAddress()
	{
		return PostalAddress;
	}
	public void setPostalAddress(String aPostalAddress)
	{
		if(aPostalAddress!=null && aPostalAddress.length()>100)
			throw new IllegalArgumentException("联系地址PostalAddress值"+aPostalAddress+"的长度"+aPostalAddress.length()+"大于最大值100");
		PostalAddress = aPostalAddress;
	}
	/**
	* 【不用】
	*/
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>10)
			throw new IllegalArgumentException("联系电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值10");
		Phone = aPhone;
	}
	/**
	* 【不用】
	*/
	public String getAccStartDate()
	{
		if( AccStartDate != null )
			return fDate.getString(AccStartDate);
		else
			return null;
	}
	public void setAccStartDate(Date aAccStartDate)
	{
		AccStartDate = aAccStartDate;
	}
	public void setAccStartDate(String aAccStartDate)
	{
		if (aAccStartDate != null && !aAccStartDate.equals("") )
		{
			AccStartDate = fDate.getDate( aAccStartDate );
		}
		else
			AccStartDate = null;
	}

	/**
	* 【不用】
	*/
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

	/**
	* 【不用】
	*/
	public String getAccidentSite()
	{
		return AccidentSite;
	}
	public void setAccidentSite(String aAccidentSite)
	{
		if(aAccidentSite!=null && aAccidentSite.length()>60)
			throw new IllegalArgumentException("出险地点AccidentSite值"+aAccidentSite+"的长度"+aAccidentSite.length()+"大于最大值60");
		AccidentSite = aAccidentSite;
	}
	public String getDeathDate()
	{
		if( DeathDate != null )
			return fDate.getString(DeathDate);
		else
			return null;
	}
	public void setDeathDate(Date aDeathDate)
	{
		DeathDate = aDeathDate;
	}
	public void setDeathDate(String aDeathDate)
	{
		if (aDeathDate != null && !aDeathDate.equals("") )
		{
			DeathDate = fDate.getDate( aDeathDate );
		}
		else
			DeathDate = null;
	}

	/**
	* 0或Null未死亡<p>
	* 1已死亡
	*/
	public String getDieFlag()
	{
		return DieFlag;
	}
	public void setDieFlag(String aDieFlag)
	{
		if(aDieFlag!=null && aDieFlag.length()>6)
			throw new IllegalArgumentException("死亡标志DieFlag值"+aDieFlag+"的长度"+aDieFlag.length()+"大于最大值6");
		DieFlag = aDieFlag;
	}
	/**
	* 0或Null正常<p>
	* 1非正常
	*/
	public String getCustState()
	{
		return CustState;
	}
	public void setCustState(String aCustState)
	{
		if(aCustState!=null && aCustState.length()>2)
			throw new IllegalArgumentException("非正常修改状态CustState值"+aCustState+"的长度"+aCustState.length()+"大于最大值2");
		CustState = aCustState;
	}
	public String getAccdentDesc()
	{
		return AccdentDesc;
	}
	public void setAccdentDesc(String aAccdentDesc)
	{
		if(aAccdentDesc!=null && aAccdentDesc.length()>2000)
			throw new IllegalArgumentException("事故经过描述AccdentDesc值"+aAccdentDesc+"的长度"+aAccdentDesc.length()+"大于最大值2000");
		AccdentDesc = aAccdentDesc;
	}
	public String getCustBirthday()
	{
		if( CustBirthday != null )
			return fDate.getString(CustBirthday);
		else
			return null;
	}
	public void setCustBirthday(Date aCustBirthday)
	{
		CustBirthday = aCustBirthday;
	}
	public void setCustBirthday(String aCustBirthday)
	{
		if (aCustBirthday != null && !aCustBirthday.equals("") )
		{
			CustBirthday = fDate.getDate( aCustBirthday );
		}
		else
			CustBirthday = null;
	}

	public String getCustomerSex()
	{
		return CustomerSex;
	}
	public void setCustomerSex(String aCustomerSex)
	{
		if(aCustomerSex!=null && aCustomerSex.length()>1)
			throw new IllegalArgumentException("出险人性别CustomerSex值"+aCustomerSex+"的长度"+aCustomerSex.length()+"大于最大值1");
		CustomerSex = aCustomerSex;
	}
	public double getCustomerAge()
	{
		return CustomerAge;
	}
	public void setCustomerAge(double aCustomerAge)
	{
		CustomerAge = aCustomerAge;
	}
	public void setCustomerAge(String aCustomerAge)
	{
		if (aCustomerAge != null && !aCustomerAge.equals(""))
		{
			Double tDouble = new Double(aCustomerAge);
			double d = tDouble.doubleValue();
			CustomerAge = d;
		}
	}

	/**
	* 【不用】
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>1)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值1");
		IDType = aIDType;
	}
	/**
	* 【不用】
	*/
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>20)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值20");
		IDNo = aIDNo;
	}
	/**
	* 【不用】
	*/
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		if(aHandler!=null && aHandler.length()>10)
			throw new IllegalArgumentException("审核人Handler值"+aHandler+"的长度"+aHandler.length()+"大于最大值10");
		Handler = aHandler;
	}
	/**
	* 【不用】
	*/
	public String getHandleDate()
	{
		if( HandleDate != null )
			return fDate.getString(HandleDate);
		else
			return null;
	}
	public void setHandleDate(Date aHandleDate)
	{
		HandleDate = aHandleDate;
	}
	public void setHandleDate(String aHandleDate)
	{
		if (aHandleDate != null && !aHandleDate.equals("") )
		{
			HandleDate = fDate.getDate( aHandleDate );
		}
		else
			HandleDate = null;
	}

	/**
	* 【不用】<p>
	* 1 理算<p>
	* 2 审核回退(回退到再理算)<p>
	* 3 再理算返回<p>
	* 4 提起核保<p>
	* 5 核保返回<p>
	* 6 上报<p>
	* 7 审核回退(上级回退给理算)<p>
	* 8 再理算返回(核赔员上级)<p>
	* 9 提起核保<p>
	* 10 核保返回<p>
	* 11 审核返回(给受理核赔员)<p>
	* (由受理核赔员最后做结论)
	*/
	public String getUWState()
	{
		return UWState;
	}
	public void setUWState(String aUWState)
	{
		if(aUWState!=null && aUWState.length()>10)
			throw new IllegalArgumentException("审核状态UWState值"+aUWState+"的长度"+aUWState.length()+"大于最大值10");
		UWState = aUWState;
	}
	/**
	* 【不用】
	*/
	public String getDealer()
	{
		return Dealer;
	}
	public void setDealer(String aDealer)
	{
		if(aDealer!=null && aDealer.length()>10)
			throw new IllegalArgumentException("当前处理人Dealer值"+aDealer+"的长度"+aDealer.length()+"大于最大值10");
		Dealer = aDealer;
	}
	/**
	* 【不用】<p>
	* 0 or null 正常状态<p>
	* 1 申诉<p>
	* 2 错误
	*/
	public String getAppealFlag()
	{
		return AppealFlag;
	}
	public void setAppealFlag(String aAppealFlag)
	{
		if(aAppealFlag!=null && aAppealFlag.length()>6)
			throw new IllegalArgumentException("申诉标志AppealFlag值"+aAppealFlag+"的长度"+aAppealFlag.length()+"大于最大值6");
		AppealFlag = aAppealFlag;
	}
	/**
	* 【不用】
	*/
	public String getTogetherGet()
	{
		return TogetherGet;
	}
	public void setTogetherGet(String aTogetherGet)
	{
		if(aTogetherGet!=null && aTogetherGet.length()>6)
			throw new IllegalArgumentException("全部保单是否统一给付TogetherGet值"+aTogetherGet+"的长度"+aTogetherGet.length()+"大于最大值6");
		TogetherGet = aTogetherGet;
	}
	/**
	* 【不用】
	*/
	public String getGrpDealFlag()
	{
		return GrpDealFlag;
	}
	public void setGrpDealFlag(String aGrpDealFlag)
	{
		if(aGrpDealFlag!=null && aGrpDealFlag.length()>6)
			throw new IllegalArgumentException("团体批处理标志GrpDealFlag值"+aGrpDealFlag+"的长度"+aGrpDealFlag.length()+"大于最大值6");
		GrpDealFlag = aGrpDealFlag;
	}
	/**
	* 【不用】1 一次统一给付<p>
	* 2 分期支付<p>
	* 3 按年金方式领取
	*/
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		if(aGetMode!=null && aGetMode.length()>6)
			throw new IllegalArgumentException("赔付金领取方式GetMode值"+aGetMode+"的长度"+aGetMode.length()+"大于最大值6");
		GetMode = aGetMode;
	}
	/**
	* 【不用】
	*/
	public int getGetIntv()
	{
		return GetIntv;
	}
	public void setGetIntv(int aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	public void setGetIntv(String aGetIntv)
	{
		if (aGetIntv != null && !aGetIntv.equals(""))
		{
			Integer tInteger = new Integer(aGetIntv);
			int i = tInteger.intValue();
			GetIntv = i;
		}
	}

	/**
	* 【暂时不用】<p>
	* 包括：全部核算、部分核算、没有核算<p>
	* 加:抽检返回重新核算
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		if(aCalFlag!=null && aCalFlag.length()>6)
			throw new IllegalArgumentException("核算标记CalFlag值"+aCalFlag+"的长度"+aCalFlag.length()+"大于最大值6");
		CalFlag = aCalFlag;
	}
	/**
	* 【暂时不用】<p>
	* 包括：全部核赔、部分核赔、没有核赔
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>1)
			throw new IllegalArgumentException("核赔标记UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值1");
		UWFlag = aUWFlag;
	}
	/**
	* 【暂时不用】<p>
	* 包括：全部拒赔、部分拒赔、没有拒赔
	*/
	public String getDeclineFlag()
	{
		return DeclineFlag;
	}
	public void setDeclineFlag(String aDeclineFlag)
	{
		if(aDeclineFlag!=null && aDeclineFlag.length()>1)
			throw new IllegalArgumentException("拒赔标记DeclineFlag值"+aDeclineFlag+"的长度"+aDeclineFlag.length()+"大于最大值1");
		DeclineFlag = aDeclineFlag;
	}
	/**
	* 【暂时不用】<p>
	* 包括：全部结案、部分结案、没有结案
	*/
	public String getEndCaseFlag()
	{
		return EndCaseFlag;
	}
	public void setEndCaseFlag(String aEndCaseFlag)
	{
		if(aEndCaseFlag!=null && aEndCaseFlag.length()>1)
			throw new IllegalArgumentException("结案标记EndCaseFlag值"+aEndCaseFlag+"的长度"+aEndCaseFlag.length()+"大于最大值1");
		EndCaseFlag = aEndCaseFlag;
	}
	/**
	* 【不用】
	*/
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
	/**
	* 【不用】
	*/
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
	/**
	* 1	现金<p>
	* 2	现金支票<p>
	* 3	转账支票<p>
	* 4	银行转账<p>
	* 5	内部转帐<p>
	* 6	银行托收<p>
	* 7	其他<p>
	* 8 new 统一转账(团单，家庭单按一个申请统一转账)
	*/
	public String getCaseGetMode()
	{
		return CaseGetMode;
	}
	public void setCaseGetMode(String aCaseGetMode)
	{
		if(aCaseGetMode!=null && aCaseGetMode.length()>1)
			throw new IllegalArgumentException("保险金领取方式CaseGetMode值"+aCaseGetMode+"的长度"+aCaseGetMode.length()+"大于最大值1");
		CaseGetMode = aCaseGetMode;
	}
	/**
	* 【不用】
	*/
	public String getAccModifyReason()
	{
		return AccModifyReason;
	}
	public void setAccModifyReason(String aAccModifyReason)
	{
		if(aAccModifyReason!=null && aAccModifyReason.length()>400)
			throw new IllegalArgumentException("账户修改原因AccModifyReason值"+aAccModifyReason+"的长度"+aAccModifyReason.length()+"大于最大值400");
		AccModifyReason = aAccModifyReason;
	}
	/**
	* 【不用】
	*/
	public String getCaseNoDate()
	{
		if( CaseNoDate != null )
			return fDate.getString(CaseNoDate);
		else
			return null;
	}
	public void setCaseNoDate(Date aCaseNoDate)
	{
		CaseNoDate = aCaseNoDate;
	}
	public void setCaseNoDate(String aCaseNoDate)
	{
		if (aCaseNoDate != null && !aCaseNoDate.equals("") )
		{
			CaseNoDate = fDate.getDate( aCaseNoDate );
		}
		else
			CaseNoDate = null;
	}

	public String getVIPFlag()
	{
		return VIPFlag;
	}
	public void setVIPFlag(String aVIPFlag)
	{
		if(aVIPFlag!=null && aVIPFlag.length()>6)
			throw new IllegalArgumentException("Vip标志VIPFlag值"+aVIPFlag+"的长度"+aVIPFlag.length()+"大于最大值6");
		VIPFlag = aVIPFlag;
	}
	/**
	* 取自ICD10
	*/
	public String getAccidentDetail()
	{
		return AccidentDetail;
	}
	public void setAccidentDetail(String aAccidentDetail)
	{
		if(aAccidentDetail!=null && aAccidentDetail.length()>10)
			throw new IllegalArgumentException("出险细节AccidentDetail值"+aAccidentDetail+"的长度"+aAccidentDetail.length()+"大于最大值10");
		AccidentDetail = aAccidentDetail;
	}
	/**
	* 01 	门诊<p>
	* 02 	住院<p>
	* 03 	综合
	*/
	public String getCureDesc()
	{
		return CureDesc;
	}
	public void setCureDesc(String aCureDesc)
	{
		if(aCureDesc!=null && aCureDesc.length()>1000)
			throw new IllegalArgumentException("治疗情况CureDesc值"+aCureDesc+"的长度"+aCureDesc.length()+"大于最大值1000");
		CureDesc = aCureDesc;
	}
	/**
	* 0不齐全、<p>
	* 1齐全
	*/
	public String getAffixConclusion()
	{
		return AffixConclusion;
	}
	public void setAffixConclusion(String aAffixConclusion)
	{
		if(aAffixConclusion!=null && aAffixConclusion.length()>6)
			throw new IllegalArgumentException("单证检查结论AffixConclusion值"+aAffixConclusion+"的长度"+aAffixConclusion.length()+"大于最大值6");
		AffixConclusion = aAffixConclusion;
	}
	public String getAffixReason()
	{
		return AffixReason;
	}
	public void setAffixReason(String aAffixReason)
	{
		if(aAffixReason!=null && aAffixReason.length()>1000)
			throw new IllegalArgumentException("单证不全原因AffixReason值"+aAffixReason+"的长度"+aAffixReason.length()+"大于最大值1000");
		AffixReason = aAffixReason;
	}
	/**
	* 0或Null 不需要<p>
	* 1 必须
	*/
	public String getFeeInputFlag()
	{
		return FeeInputFlag;
	}
	public void setFeeInputFlag(String aFeeInputFlag)
	{
		if(aFeeInputFlag!=null && aFeeInputFlag.length()>1)
			throw new IllegalArgumentException("账单录入标记FeeInputFlag值"+aFeeInputFlag+"的长度"+aFeeInputFlag.length()+"大于最大值1");
		FeeInputFlag = aFeeInputFlag;
	}
	/**
	* 0或Null没有<p>
	* 1有
	*/
	public String getSurveyFlag()
	{
		return SurveyFlag;
	}
	public void setSurveyFlag(String aSurveyFlag)
	{
		if(aSurveyFlag!=null && aSurveyFlag.length()>6)
			throw new IllegalArgumentException("调查报告标志SurveyFlag值"+aSurveyFlag+"的长度"+aSurveyFlag.length()+"大于最大值6");
		SurveyFlag = aSurveyFlag;
	}
	/**
	* 【不用】
	*/
	public String getSubmitFlag()
	{
		return SubmitFlag;
	}
	public void setSubmitFlag(String aSubmitFlag)
	{
		if(aSubmitFlag!=null && aSubmitFlag.length()>6)
			throw new IllegalArgumentException("发起呈报标志SubmitFlag值"+aSubmitFlag+"的长度"+aSubmitFlag.length()+"大于最大值6");
		SubmitFlag = aSubmitFlag;
	}
	/**
	* 0未提起<p>
	* 1已提起
	*/
	public String getCondoleFlag()
	{
		return CondoleFlag;
	}
	public void setCondoleFlag(String aCondoleFlag)
	{
		if(aCondoleFlag!=null && aCondoleFlag.length()>6)
			throw new IllegalArgumentException("提起慰问标志CondoleFlag值"+aCondoleFlag+"的长度"+aCondoleFlag.length()+"大于最大值6");
		CondoleFlag = aCondoleFlag;
	}
	/**
	* 0未修改<p>
	* 1已修改
	*/
	public String getEditFlag()
	{
		return EditFlag;
	}
	public void setEditFlag(String aEditFlag)
	{
		if(aEditFlag!=null && aEditFlag.length()>6)
			throw new IllegalArgumentException("信息修改标志EditFlag值"+aEditFlag+"的长度"+aEditFlag.length()+"大于最大值6");
		EditFlag = aEditFlag;
	}
	/**
	* 【不用】
	*/
	public String getSecondUWFlag()
	{
		return SecondUWFlag;
	}
	public void setSecondUWFlag(String aSecondUWFlag)
	{
		if(aSecondUWFlag!=null && aSecondUWFlag.length()>6)
			throw new IllegalArgumentException("二核标志SecondUWFlag值"+aSecondUWFlag+"的长度"+aSecondUWFlag.length()+"大于最大值6");
		SecondUWFlag = aSecondUWFlag;
	}
	/**
	* 记录理赔类型为非医疗时的出险日期
	*/
	public String getAccDate()
	{
		if( AccDate != null )
			return fDate.getString(AccDate);
		else
			return null;
	}
	public void setAccDate(Date aAccDate)
	{
		AccDate = aAccDate;
	}
	public void setAccDate(String aAccDate)
	{
		if (aAccDate != null && !aAccDate.equals("") )
		{
			AccDate = fDate.getDate( aAccDate );
		}
		else
			AccDate = null;
	}

	/**
	* 取自ICD10
	*/
	public String getAccResult1()
	{
		return AccResult1;
	}
	public void setAccResult1(String aAccResult1)
	{
		if(aAccResult1!=null && aAccResult1.length()>10)
			throw new IllegalArgumentException("出险结果1AccResult1值"+aAccResult1+"的长度"+aAccResult1.length()+"大于最大值10");
		AccResult1 = aAccResult1;
	}
	/**
	* 取自ICD10
	*/
	public String getAccResult2()
	{
		return AccResult2;
	}
	public void setAccResult2(String aAccResult2)
	{
		if(aAccResult2!=null && aAccResult2.length()>10)
			throw new IllegalArgumentException("出险结果2AccResult2值"+aAccResult2+"的长度"+aAccResult2.length()+"大于最大值10");
		AccResult2 = aAccResult2;
	}
	/**
	* 0或Null无<p>
	* 1有
	*/
	public String getMasculineFlag()
	{
		return MasculineFlag;
	}
	public void setMasculineFlag(String aMasculineFlag)
	{
		if(aMasculineFlag!=null && aMasculineFlag.length()>6)
			throw new IllegalArgumentException("阳性标志MasculineFlag值"+aMasculineFlag+"的长度"+aMasculineFlag.length()+"大于最大值6");
		MasculineFlag = aMasculineFlag;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>20)
			throw new IllegalArgumentException("医院代码HospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值20");
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		if(aHospitalName!=null && aHospitalName.length()>100)
			throw new IllegalArgumentException("医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值100");
		HospitalName = aHospitalName;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public int getSeqNo()
	{
		return SeqNo;
	}
	public void setSeqNo(int aSeqNo)
	{
		SeqNo = aSeqNo;
	}
	public void setSeqNo(String aSeqNo)
	{
		if (aSeqNo != null && !aSeqNo.equals(""))
		{
			Integer tInteger = new Integer(aSeqNo);
			int i = tInteger.intValue();
			SeqNo = i;
		}
	}

	public double getStandpay()
	{
		return Standpay;
	}
	public void setStandpay(double aStandpay)
	{
		Standpay = aStandpay;
	}
	public void setStandpay(String aStandpay)
	{
		if (aStandpay != null && !aStandpay.equals(""))
		{
			Double tDouble = new Double(aStandpay);
			double d = tDouble.doubleValue();
			Standpay = d;
		}
	}

	/**
	* 记录理赔类型为医疗时的出险日期
	*/
	public String getMedAccDate()
	{
		if( MedAccDate != null )
			return fDate.getString(MedAccDate);
		else
			return null;
	}
	public void setMedAccDate(Date aMedAccDate)
	{
		MedAccDate = aMedAccDate;
	}
	public void setMedAccDate(String aMedAccDate)
	{
		if (aMedAccDate != null && !aMedAccDate.equals("") )
		{
			MedAccDate = fDate.getDate( aMedAccDate );
		}
		else
			MedAccDate = null;
	}

	public String getBnfName()
	{
		return BnfName;
	}
	public void setBnfName(String aBnfName)
	{
		if(aBnfName!=null && aBnfName.length()>20)
			throw new IllegalArgumentException("收款人姓名BnfName值"+aBnfName+"的长度"+aBnfName.length()+"大于最大值20");
		BnfName = aBnfName;
	}
	public String getBnfIDNo()
	{
		return BnfIDNo;
	}
	public void setBnfIDNo(String aBnfIDNo)
	{
		if(aBnfIDNo!=null && aBnfIDNo.length()>20)
			throw new IllegalArgumentException("受益人证件号码BnfIDNo值"+aBnfIDNo+"的长度"+aBnfIDNo.length()+"大于最大值20");
		BnfIDNo = aBnfIDNo;
	}
	/**
	* 00  	本人<p>
	* 01  	父子<p>
	* 02  	父女<p>
	* 03  	母子<p>
	* 04  	母女<p>
	* 05  	祖孙<p>
	* 07  	夫妻<p>
	* 08  	兄弟<p>
	* 09  	兄妹<p>
	* 10  	姐弟<p>
	* 11  	姐妹<p>
	* 12  	叔侄<p>
	* 13  	姑侄<p>
	* 14  	外甥<p>
	* 15  	媳<p>
	* 16  	婿<p>
	* 17  	姐夫<p>
	* 18  	朋友<p>
	* 19  	同事<p>
	* 20  	师生<p>
	* 21  	雇佣<p>
	* 22  	其他<p>
	* 23  	法定
	*/
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		if(aRelationToInsured!=null && aRelationToInsured.length()>6)
			throw new IllegalArgumentException("受益人与被保人关系RelationToInsured值"+aRelationToInsured+"的长度"+aRelationToInsured.length()+"大于最大值6");
		RelationToInsured = aRelationToInsured;
	}
	public String getBnfIDType()
	{
		return BnfIDType;
	}
	public void setBnfIDType(String aBnfIDType)
	{
		if(aBnfIDType!=null && aBnfIDType.length()>6)
			throw new IllegalArgumentException("受益人证件类型BnfIDType值"+aBnfIDType+"的长度"+aBnfIDType.length()+"大于最大值6");
		BnfIDType = aBnfIDType;
	}
	public String getBirthday()
	{
		if( Birthday != null )
			return fDate.getString(Birthday);
		else
			return null;
	}
	public void setBirthday(Date aBirthday)
	{
		Birthday = aBirthday;
	}
	public void setBirthday(String aBirthday)
	{
		if (aBirthday != null && !aBirthday.equals("") )
		{
			Birthday = fDate.getDate( aBirthday );
		}
		else
			Birthday = null;
	}

	public String getBnfSex()
	{
		return BnfSex;
	}
	public void setBnfSex(String aBnfSex)
	{
		if(aBnfSex!=null && aBnfSex.length()>6)
			throw new IllegalArgumentException("受益人性别BnfSex值"+aBnfSex+"的长度"+aBnfSex.length()+"大于最大值6");
		BnfSex = aBnfSex;
	}
	public String getBnfAccName()
	{
		return BnfAccName;
	}
	public void setBnfAccName(String aBnfAccName)
	{
		if(aBnfAccName!=null && aBnfAccName.length()>60)
			throw new IllegalArgumentException("受益人银行账户名BnfAccName值"+aBnfAccName+"的长度"+aBnfAccName.length()+"大于最大值60");
		BnfAccName = aBnfAccName;
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

	public String getHospitalClass()
	{
		return HospitalClass;
	}
	public void setHospitalClass(String aHospitalClass)
	{
		if(aHospitalClass!=null && aHospitalClass.length()>20)
			throw new IllegalArgumentException("医院类别HospitalClass值"+aHospitalClass+"的长度"+aHospitalClass.length()+"大于最大值20");
		HospitalClass = aHospitalClass;
	}
	public String getAccProvince()
	{
		return AccProvince;
	}
	public void setAccProvince(String aAccProvince)
	{
		if(aAccProvince!=null && aAccProvince.length()>30)
			throw new IllegalArgumentException("出险地点（省）AccProvince值"+aAccProvince+"的长度"+aAccProvince.length()+"大于最大值30");
		AccProvince = aAccProvince;
	}
	public String getAccCity()
	{
		return AccCity;
	}
	public void setAccCity(String aAccCity)
	{
		if(aAccCity!=null && aAccCity.length()>30)
			throw new IllegalArgumentException("出险地点（市）AccCity值"+aAccCity+"的长度"+aAccCity.length()+"大于最大值30");
		AccCity = aAccCity;
	}
	public String getAccCounty()
	{
		return AccCounty;
	}
	public void setAccCounty(String aAccCounty)
	{
		if(aAccCounty!=null && aAccCounty.length()>30)
			throw new IllegalArgumentException("出险地点（区/县）AccCounty值"+aAccCounty+"的长度"+aAccCounty.length()+"大于最大值30");
		AccCounty = aAccCounty;
	}
	public String getAccSite()
	{
		return AccSite;
	}
	public void setAccSite(String aAccSite)
	{
		if(aAccSite!=null && aAccSite.length()>200)
			throw new IllegalArgumentException("出险地点2AccSite值"+aAccSite+"的长度"+aAccSite.length()+"大于最大值200");
		AccSite = aAccSite;
	}
	public String getCaseSource()
	{
		return CaseSource;
	}
	public void setCaseSource(String aCaseSource)
	{
		if(aCaseSource!=null && aCaseSource.length()>10)
			throw new IllegalArgumentException("事件来源CaseSource值"+aCaseSource+"的长度"+aCaseSource.length()+"大于最大值10");
		CaseSource = aCaseSource;
	}
	/**
	* 0-正常，1-关闭
	*/
	public String getCaseState()
	{
		return CaseState;
	}
	public void setCaseState(String aCaseState)
	{
		if(aCaseState!=null && aCaseState.length()>6)
			throw new IllegalArgumentException("事件状态CaseState值"+aCaseState+"的长度"+aCaseState.length()+"大于最大值6");
		CaseState = aCaseState;
	}
	public String getCloseCaseReason()
	{
		return CloseCaseReason;
	}
	public void setCloseCaseReason(String aCloseCaseReason)
	{
		if(aCloseCaseReason!=null && aCloseCaseReason.length()>10)
			throw new IllegalArgumentException("关闭事件原因CloseCaseReason值"+aCloseCaseReason+"的长度"+aCloseCaseReason.length()+"大于最大值10");
		CloseCaseReason = aCloseCaseReason;
	}
	public String getCloseCaseRemark()
	{
		return CloseCaseRemark;
	}
	public void setCloseCaseRemark(String aCloseCaseRemark)
	{
		if(aCloseCaseRemark!=null && aCloseCaseRemark.length()>1000)
			throw new IllegalArgumentException("关闭事件备注CloseCaseRemark值"+aCloseCaseRemark+"的长度"+aCloseCaseRemark.length()+"大于最大值1000");
		CloseCaseRemark = aCloseCaseRemark;
	}
	public String getLRCaseNo()
	{
		return LRCaseNo;
	}
	public void setLRCaseNo(String aLRCaseNo)
	{
		if(aLRCaseNo!=null && aLRCaseNo.length()>20)
			throw new IllegalArgumentException("乐容回写事件号LRCaseNo值"+aLRCaseNo+"的长度"+aLRCaseNo.length()+"大于最大值20");
		LRCaseNo = aLRCaseNo;
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("Column_99ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}

	/**
	* 使用另外一个 LLCaseSchema 对象给 Schema 赋值
	* @param: aLLCaseSchema LLCaseSchema
	**/
	public void setSchema(LLCaseSchema aLLCaseSchema)
	{
		this.CaseNo = aLLCaseSchema.getCaseNo();
		this.RgtNo = aLLCaseSchema.getRgtNo();
		this.RgtType = aLLCaseSchema.getRgtType();
		this.RgtState = aLLCaseSchema.getRgtState();
		this.CustomerNo = aLLCaseSchema.getCustomerNo();
		this.CustomerName = aLLCaseSchema.getCustomerName();
		this.AccidentType = aLLCaseSchema.getAccidentType();
		this.ReceiptFlag = aLLCaseSchema.getReceiptFlag();
		this.HospitalFlag = aLLCaseSchema.getHospitalFlag();
		this.RgtDate = fDate.getDate( aLLCaseSchema.getRgtDate());
		this.ClaimCalDate = fDate.getDate( aLLCaseSchema.getClaimCalDate());
		this.AffixGetDate = fDate.getDate( aLLCaseSchema.getAffixGetDate());
		this.InHospitalDate = fDate.getDate( aLLCaseSchema.getInHospitalDate());
		this.OutHospitalDate = fDate.getDate( aLLCaseSchema.getOutHospitalDate());
		this.InvaliHosDays = aLLCaseSchema.getInvaliHosDays();
		this.InHospitalDays = aLLCaseSchema.getInHospitalDays();
		this.DianoseDate = fDate.getDate( aLLCaseSchema.getDianoseDate());
		this.PostalAddress = aLLCaseSchema.getPostalAddress();
		this.Phone = aLLCaseSchema.getPhone();
		this.AccStartDate = fDate.getDate( aLLCaseSchema.getAccStartDate());
		this.AccidentDate = fDate.getDate( aLLCaseSchema.getAccidentDate());
		this.AccidentSite = aLLCaseSchema.getAccidentSite();
		this.DeathDate = fDate.getDate( aLLCaseSchema.getDeathDate());
		this.DieFlag = aLLCaseSchema.getDieFlag();
		this.CustState = aLLCaseSchema.getCustState();
		this.AccdentDesc = aLLCaseSchema.getAccdentDesc();
		this.CustBirthday = fDate.getDate( aLLCaseSchema.getCustBirthday());
		this.CustomerSex = aLLCaseSchema.getCustomerSex();
		this.CustomerAge = aLLCaseSchema.getCustomerAge();
		this.IDType = aLLCaseSchema.getIDType();
		this.IDNo = aLLCaseSchema.getIDNo();
		this.Handler = aLLCaseSchema.getHandler();
		this.HandleDate = fDate.getDate( aLLCaseSchema.getHandleDate());
		this.UWState = aLLCaseSchema.getUWState();
		this.Dealer = aLLCaseSchema.getDealer();
		this.AppealFlag = aLLCaseSchema.getAppealFlag();
		this.TogetherGet = aLLCaseSchema.getTogetherGet();
		this.GrpDealFlag = aLLCaseSchema.getGrpDealFlag();
		this.GetMode = aLLCaseSchema.getGetMode();
		this.GetIntv = aLLCaseSchema.getGetIntv();
		this.CalFlag = aLLCaseSchema.getCalFlag();
		this.UWFlag = aLLCaseSchema.getUWFlag();
		this.DeclineFlag = aLLCaseSchema.getDeclineFlag();
		this.EndCaseFlag = aLLCaseSchema.getEndCaseFlag();
		this.EndCaseDate = fDate.getDate( aLLCaseSchema.getEndCaseDate());
		this.MngCom = aLLCaseSchema.getMngCom();
		this.Operator = aLLCaseSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCaseSchema.getMakeDate());
		this.MakeTime = aLLCaseSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCaseSchema.getModifyDate());
		this.ModifyTime = aLLCaseSchema.getModifyTime();
		this.BankCode = aLLCaseSchema.getBankCode();
		this.BankAccNo = aLLCaseSchema.getBankAccNo();
		this.AccName = aLLCaseSchema.getAccName();
		this.CaseGetMode = aLLCaseSchema.getCaseGetMode();
		this.AccModifyReason = aLLCaseSchema.getAccModifyReason();
		this.CaseNoDate = fDate.getDate( aLLCaseSchema.getCaseNoDate());
		this.VIPFlag = aLLCaseSchema.getVIPFlag();
		this.AccidentDetail = aLLCaseSchema.getAccidentDetail();
		this.CureDesc = aLLCaseSchema.getCureDesc();
		this.AffixConclusion = aLLCaseSchema.getAffixConclusion();
		this.AffixReason = aLLCaseSchema.getAffixReason();
		this.FeeInputFlag = aLLCaseSchema.getFeeInputFlag();
		this.SurveyFlag = aLLCaseSchema.getSurveyFlag();
		this.SubmitFlag = aLLCaseSchema.getSubmitFlag();
		this.CondoleFlag = aLLCaseSchema.getCondoleFlag();
		this.EditFlag = aLLCaseSchema.getEditFlag();
		this.SecondUWFlag = aLLCaseSchema.getSecondUWFlag();
		this.AccDate = fDate.getDate( aLLCaseSchema.getAccDate());
		this.AccResult1 = aLLCaseSchema.getAccResult1();
		this.AccResult2 = aLLCaseSchema.getAccResult2();
		this.MasculineFlag = aLLCaseSchema.getMasculineFlag();
		this.HospitalCode = aLLCaseSchema.getHospitalCode();
		this.HospitalName = aLLCaseSchema.getHospitalName();
		this.Remark = aLLCaseSchema.getRemark();
		this.SeqNo = aLLCaseSchema.getSeqNo();
		this.Standpay = aLLCaseSchema.getStandpay();
		this.MedAccDate = fDate.getDate( aLLCaseSchema.getMedAccDate());
		this.BnfName = aLLCaseSchema.getBnfName();
		this.BnfIDNo = aLLCaseSchema.getBnfIDNo();
		this.RelationToInsured = aLLCaseSchema.getRelationToInsured();
		this.BnfIDType = aLLCaseSchema.getBnfIDType();
		this.Birthday = fDate.getDate( aLLCaseSchema.getBirthday());
		this.BnfSex = aLLCaseSchema.getBnfSex();
		this.BnfAccName = aLLCaseSchema.getBnfAccName();
		this.RealPay = aLLCaseSchema.getRealPay();
		this.HospitalClass = aLLCaseSchema.getHospitalClass();
		this.AccProvince = aLLCaseSchema.getAccProvince();
		this.AccCity = aLLCaseSchema.getAccCity();
		this.AccCounty = aLLCaseSchema.getAccCounty();
		this.AccSite = aLLCaseSchema.getAccSite();
		this.CaseSource = aLLCaseSchema.getCaseSource();
		this.CaseState = aLLCaseSchema.getCaseState();
		this.CloseCaseReason = aLLCaseSchema.getCloseCaseReason();
		this.CloseCaseRemark = aLLCaseSchema.getCloseCaseRemark();
		this.LRCaseNo = aLLCaseSchema.getLRCaseNo();
		this.ComCode = aLLCaseSchema.getComCode();
		this.ModifyOperator = aLLCaseSchema.getModifyOperator();
		this.ContNo = aLLCaseSchema.getContNo();
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
			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("RgtType") == null )
				this.RgtType = null;
			else
				this.RgtType = rs.getString("RgtType").trim();

			if( rs.getString("RgtState") == null )
				this.RgtState = null;
			else
				this.RgtState = rs.getString("RgtState").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("AccidentType") == null )
				this.AccidentType = null;
			else
				this.AccidentType = rs.getString("AccidentType").trim();

			if( rs.getString("ReceiptFlag") == null )
				this.ReceiptFlag = null;
			else
				this.ReceiptFlag = rs.getString("ReceiptFlag").trim();

			if( rs.getString("HospitalFlag") == null )
				this.HospitalFlag = null;
			else
				this.HospitalFlag = rs.getString("HospitalFlag").trim();

			this.RgtDate = rs.getDate("RgtDate");
			this.ClaimCalDate = rs.getDate("ClaimCalDate");
			this.AffixGetDate = rs.getDate("AffixGetDate");
			this.InHospitalDate = rs.getDate("InHospitalDate");
			this.OutHospitalDate = rs.getDate("OutHospitalDate");
			this.InvaliHosDays = rs.getInt("InvaliHosDays");
			this.InHospitalDays = rs.getInt("InHospitalDays");
			this.DianoseDate = rs.getDate("DianoseDate");
			if( rs.getString("PostalAddress") == null )
				this.PostalAddress = null;
			else
				this.PostalAddress = rs.getString("PostalAddress").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			this.AccStartDate = rs.getDate("AccStartDate");
			this.AccidentDate = rs.getDate("AccidentDate");
			if( rs.getString("AccidentSite") == null )
				this.AccidentSite = null;
			else
				this.AccidentSite = rs.getString("AccidentSite").trim();

			this.DeathDate = rs.getDate("DeathDate");
			if( rs.getString("DieFlag") == null )
				this.DieFlag = null;
			else
				this.DieFlag = rs.getString("DieFlag").trim();

			if( rs.getString("CustState") == null )
				this.CustState = null;
			else
				this.CustState = rs.getString("CustState").trim();

			if( rs.getString("AccdentDesc") == null )
				this.AccdentDesc = null;
			else
				this.AccdentDesc = rs.getString("AccdentDesc").trim();

			this.CustBirthday = rs.getDate("CustBirthday");
			if( rs.getString("CustomerSex") == null )
				this.CustomerSex = null;
			else
				this.CustomerSex = rs.getString("CustomerSex").trim();

			this.CustomerAge = rs.getDouble("CustomerAge");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			this.HandleDate = rs.getDate("HandleDate");
			if( rs.getString("UWState") == null )
				this.UWState = null;
			else
				this.UWState = rs.getString("UWState").trim();

			if( rs.getString("Dealer") == null )
				this.Dealer = null;
			else
				this.Dealer = rs.getString("Dealer").trim();

			if( rs.getString("AppealFlag") == null )
				this.AppealFlag = null;
			else
				this.AppealFlag = rs.getString("AppealFlag").trim();

			if( rs.getString("TogetherGet") == null )
				this.TogetherGet = null;
			else
				this.TogetherGet = rs.getString("TogetherGet").trim();

			if( rs.getString("GrpDealFlag") == null )
				this.GrpDealFlag = null;
			else
				this.GrpDealFlag = rs.getString("GrpDealFlag").trim();

			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			this.GetIntv = rs.getInt("GetIntv");
			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("DeclineFlag") == null )
				this.DeclineFlag = null;
			else
				this.DeclineFlag = rs.getString("DeclineFlag").trim();

			if( rs.getString("EndCaseFlag") == null )
				this.EndCaseFlag = null;
			else
				this.EndCaseFlag = rs.getString("EndCaseFlag").trim();

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

			if( rs.getString("CaseGetMode") == null )
				this.CaseGetMode = null;
			else
				this.CaseGetMode = rs.getString("CaseGetMode").trim();

			if( rs.getString("AccModifyReason") == null )
				this.AccModifyReason = null;
			else
				this.AccModifyReason = rs.getString("AccModifyReason").trim();

			this.CaseNoDate = rs.getDate("CaseNoDate");
			if( rs.getString("VIPFlag") == null )
				this.VIPFlag = null;
			else
				this.VIPFlag = rs.getString("VIPFlag").trim();

			if( rs.getString("AccidentDetail") == null )
				this.AccidentDetail = null;
			else
				this.AccidentDetail = rs.getString("AccidentDetail").trim();

			if( rs.getString("CureDesc") == null )
				this.CureDesc = null;
			else
				this.CureDesc = rs.getString("CureDesc").trim();

			if( rs.getString("AffixConclusion") == null )
				this.AffixConclusion = null;
			else
				this.AffixConclusion = rs.getString("AffixConclusion").trim();

			if( rs.getString("AffixReason") == null )
				this.AffixReason = null;
			else
				this.AffixReason = rs.getString("AffixReason").trim();

			if( rs.getString("FeeInputFlag") == null )
				this.FeeInputFlag = null;
			else
				this.FeeInputFlag = rs.getString("FeeInputFlag").trim();

			if( rs.getString("SurveyFlag") == null )
				this.SurveyFlag = null;
			else
				this.SurveyFlag = rs.getString("SurveyFlag").trim();

			if( rs.getString("SubmitFlag") == null )
				this.SubmitFlag = null;
			else
				this.SubmitFlag = rs.getString("SubmitFlag").trim();

			if( rs.getString("CondoleFlag") == null )
				this.CondoleFlag = null;
			else
				this.CondoleFlag = rs.getString("CondoleFlag").trim();

			if( rs.getString("EditFlag") == null )
				this.EditFlag = null;
			else
				this.EditFlag = rs.getString("EditFlag").trim();

			if( rs.getString("SecondUWFlag") == null )
				this.SecondUWFlag = null;
			else
				this.SecondUWFlag = rs.getString("SecondUWFlag").trim();

			this.AccDate = rs.getDate("AccDate");
			if( rs.getString("AccResult1") == null )
				this.AccResult1 = null;
			else
				this.AccResult1 = rs.getString("AccResult1").trim();

			if( rs.getString("AccResult2") == null )
				this.AccResult2 = null;
			else
				this.AccResult2 = rs.getString("AccResult2").trim();

			if( rs.getString("MasculineFlag") == null )
				this.MasculineFlag = null;
			else
				this.MasculineFlag = rs.getString("MasculineFlag").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.SeqNo = rs.getInt("SeqNo");
			this.Standpay = rs.getDouble("Standpay");
			this.MedAccDate = rs.getDate("MedAccDate");
			if( rs.getString("BnfName") == null )
				this.BnfName = null;
			else
				this.BnfName = rs.getString("BnfName").trim();

			if( rs.getString("BnfIDNo") == null )
				this.BnfIDNo = null;
			else
				this.BnfIDNo = rs.getString("BnfIDNo").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("BnfIDType") == null )
				this.BnfIDType = null;
			else
				this.BnfIDType = rs.getString("BnfIDType").trim();

			this.Birthday = rs.getDate("Birthday");
			if( rs.getString("BnfSex") == null )
				this.BnfSex = null;
			else
				this.BnfSex = rs.getString("BnfSex").trim();

			if( rs.getString("BnfAccName") == null )
				this.BnfAccName = null;
			else
				this.BnfAccName = rs.getString("BnfAccName").trim();

			this.RealPay = rs.getDouble("RealPay");
			if( rs.getString("HospitalClass") == null )
				this.HospitalClass = null;
			else
				this.HospitalClass = rs.getString("HospitalClass").trim();

			if( rs.getString("AccProvince") == null )
				this.AccProvince = null;
			else
				this.AccProvince = rs.getString("AccProvince").trim();

			if( rs.getString("AccCity") == null )
				this.AccCity = null;
			else
				this.AccCity = rs.getString("AccCity").trim();

			if( rs.getString("AccCounty") == null )
				this.AccCounty = null;
			else
				this.AccCounty = rs.getString("AccCounty").trim();

			if( rs.getString("AccSite") == null )
				this.AccSite = null;
			else
				this.AccSite = rs.getString("AccSite").trim();

			if( rs.getString("CaseSource") == null )
				this.CaseSource = null;
			else
				this.CaseSource = rs.getString("CaseSource").trim();

			if( rs.getString("CaseState") == null )
				this.CaseState = null;
			else
				this.CaseState = rs.getString("CaseState").trim();

			if( rs.getString("CloseCaseReason") == null )
				this.CloseCaseReason = null;
			else
				this.CloseCaseReason = rs.getString("CloseCaseReason").trim();

			if( rs.getString("CloseCaseRemark") == null )
				this.CloseCaseRemark = null;
			else
				this.CloseCaseRemark = rs.getString("CloseCaseRemark").trim();

			if( rs.getString("LRCaseNo") == null )
				this.LRCaseNo = null;
			else
				this.LRCaseNo = rs.getString("LRCaseNo").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCase表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseSchema getSchema()
	{
		LLCaseSchema aLLCaseSchema = new LLCaseSchema();
		aLLCaseSchema.setSchema(this);
		return aLLCaseSchema;
	}

	public LLCaseDB getDB()
	{
		LLCaseDB aDBOper = new LLCaseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCase描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RgtDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ClaimCalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AffixGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvaliHosDays));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InHospitalDays));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DianoseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostalAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccidentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentSite)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeathDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DieFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccdentDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CustomerAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HandleDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Dealer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TogetherGet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpDealFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndCaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndCaseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccModifyReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CaseNoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CureDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeInputFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubmitFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CondoleFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EditFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecondUWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MasculineFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SeqNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standpay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MedAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCounty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccSite)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseSource)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CloseCaseReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CloseCaseRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LRCaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCase>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RgtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RgtState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccidentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReceiptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HospitalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RgtDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ClaimCalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			AffixGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			InHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			OutHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			InvaliHosDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			InHospitalDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			DianoseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AccStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			AccidentSite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			DieFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			CustState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AccdentDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CustBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			CustomerSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			CustomerAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			HandleDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			UWState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Dealer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AppealFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			TogetherGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			GrpDealFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			DeclineFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			EndCaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			CaseGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			AccModifyReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			CaseNoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57,SysConst.PACKAGESPILTER));
			VIPFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			AccidentDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			CureDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			AffixConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			AffixReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			FeeInputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			SurveyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			SubmitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			CondoleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			EditFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			SecondUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			AccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69,SysConst.PACKAGESPILTER));
			AccResult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			MasculineFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			SeqNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).intValue();
			Standpay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).doubleValue();
			MedAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78,SysConst.PACKAGESPILTER));
			BnfName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			BnfIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			BnfIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83,SysConst.PACKAGESPILTER));
			BnfSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			BnfAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,86,SysConst.PACKAGESPILTER))).doubleValue();
			HospitalClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			AccProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			AccCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			AccCounty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			AccSite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			CaseSource = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			CaseState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			CloseCaseReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			CloseCaseRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			LRCaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("RgtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtType));
		}
		if (FCode.equalsIgnoreCase("RgtState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtState));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("AccidentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentType));
		}
		if (FCode.equalsIgnoreCase("ReceiptFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptFlag));
		}
		if (FCode.equalsIgnoreCase("HospitalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalFlag));
		}
		if (FCode.equalsIgnoreCase("RgtDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
		}
		if (FCode.equalsIgnoreCase("ClaimCalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getClaimCalDate()));
		}
		if (FCode.equalsIgnoreCase("AffixGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAffixGetDate()));
		}
		if (FCode.equalsIgnoreCase("InHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("OutHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("InvaliHosDays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvaliHosDays));
		}
		if (FCode.equalsIgnoreCase("InHospitalDays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InHospitalDays));
		}
		if (FCode.equalsIgnoreCase("DianoseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDianoseDate()));
		}
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostalAddress));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("AccStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccStartDate()));
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
		}
		if (FCode.equalsIgnoreCase("AccidentSite"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentSite));
		}
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
		}
		if (FCode.equalsIgnoreCase("DieFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DieFlag));
		}
		if (FCode.equalsIgnoreCase("CustState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustState));
		}
		if (FCode.equalsIgnoreCase("AccdentDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccdentDesc));
		}
		if (FCode.equalsIgnoreCase("CustBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustBirthday()));
		}
		if (FCode.equalsIgnoreCase("CustomerSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerSex));
		}
		if (FCode.equalsIgnoreCase("CustomerAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerAge));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWState));
		}
		if (FCode.equalsIgnoreCase("Dealer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dealer));
		}
		if (FCode.equalsIgnoreCase("AppealFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealFlag));
		}
		if (FCode.equalsIgnoreCase("TogetherGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TogetherGet));
		}
		if (FCode.equalsIgnoreCase("GrpDealFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpDealFlag));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("DeclineFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineFlag));
		}
		if (FCode.equalsIgnoreCase("EndCaseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndCaseFlag));
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
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseGetMode));
		}
		if (FCode.equalsIgnoreCase("AccModifyReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccModifyReason));
		}
		if (FCode.equalsIgnoreCase("CaseNoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCaseNoDate()));
		}
		if (FCode.equalsIgnoreCase("VIPFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPFlag));
		}
		if (FCode.equalsIgnoreCase("AccidentDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentDetail));
		}
		if (FCode.equalsIgnoreCase("CureDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CureDesc));
		}
		if (FCode.equalsIgnoreCase("AffixConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixConclusion));
		}
		if (FCode.equalsIgnoreCase("AffixReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixReason));
		}
		if (FCode.equalsIgnoreCase("FeeInputFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeInputFlag));
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyFlag));
		}
		if (FCode.equalsIgnoreCase("SubmitFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubmitFlag));
		}
		if (FCode.equalsIgnoreCase("CondoleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CondoleFlag));
		}
		if (FCode.equalsIgnoreCase("EditFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EditFlag));
		}
		if (FCode.equalsIgnoreCase("SecondUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondUWFlag));
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult1));
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult2));
		}
		if (FCode.equalsIgnoreCase("MasculineFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MasculineFlag));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("SeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeqNo));
		}
		if (FCode.equalsIgnoreCase("Standpay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standpay));
		}
		if (FCode.equalsIgnoreCase("MedAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMedAccDate()));
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfName));
		}
		if (FCode.equalsIgnoreCase("BnfIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfIDNo));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("BnfIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfIDType));
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
		}
		if (FCode.equalsIgnoreCase("BnfSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfSex));
		}
		if (FCode.equalsIgnoreCase("BnfAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfAccName));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("HospitalClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalClass));
		}
		if (FCode.equalsIgnoreCase("AccProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccProvince));
		}
		if (FCode.equalsIgnoreCase("AccCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCity));
		}
		if (FCode.equalsIgnoreCase("AccCounty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCounty));
		}
		if (FCode.equalsIgnoreCase("AccSite"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccSite));
		}
		if (FCode.equalsIgnoreCase("CaseSource"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseSource));
		}
		if (FCode.equalsIgnoreCase("CaseState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseState));
		}
		if (FCode.equalsIgnoreCase("CloseCaseReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CloseCaseReason));
		}
		if (FCode.equalsIgnoreCase("CloseCaseRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CloseCaseRemark));
		}
		if (FCode.equalsIgnoreCase("LRCaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LRCaseNo));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RgtType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RgtState);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccidentType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ReceiptFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HospitalFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getClaimCalDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAffixGetDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
				break;
			case 14:
				strFieldValue = String.valueOf(InvaliHosDays);
				break;
			case 15:
				strFieldValue = String.valueOf(InHospitalDays);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDianoseDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccStartDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AccidentSite);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(DieFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(CustState);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AccdentDesc);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustBirthday()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CustomerSex);
				break;
			case 28:
				strFieldValue = String.valueOf(CustomerAge);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(UWState);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Dealer);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AppealFlag);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(TogetherGet);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(GrpDealFlag);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 39:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(DeclineFlag);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(EndCaseFlag);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(CaseGetMode);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(AccModifyReason);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCaseNoDate()));
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(VIPFlag);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(AccidentDetail);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(CureDesc);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(AffixConclusion);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(AffixReason);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(FeeInputFlag);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(SurveyFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(SubmitFlag);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(CondoleFlag);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(EditFlag);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(SecondUWFlag);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(AccResult1);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(MasculineFlag);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 75:
				strFieldValue = String.valueOf(SeqNo);
				break;
			case 76:
				strFieldValue = String.valueOf(Standpay);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMedAccDate()));
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(BnfName);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(BnfIDNo);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(BnfIDType);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(BnfSex);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(BnfAccName);
				break;
			case 85:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(HospitalClass);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(AccProvince);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(AccCity);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(AccCounty);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(AccSite);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(CaseSource);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(CaseState);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(CloseCaseReason);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(CloseCaseRemark);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(LRCaseNo);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
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
		if (FCode.equalsIgnoreCase("RgtType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtType = FValue.trim();
			}
			else
				RgtType = null;
		}
		if (FCode.equalsIgnoreCase("RgtState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtState = FValue.trim();
			}
			else
				RgtState = null;
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
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("AccidentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentType = FValue.trim();
			}
			else
				AccidentType = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiptFlag = FValue.trim();
			}
			else
				ReceiptFlag = null;
		}
		if (FCode.equalsIgnoreCase("HospitalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalFlag = FValue.trim();
			}
			else
				HospitalFlag = null;
		}
		if (FCode.equalsIgnoreCase("RgtDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RgtDate = fDate.getDate( FValue );
			}
			else
				RgtDate = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ClaimCalDate = fDate.getDate( FValue );
			}
			else
				ClaimCalDate = null;
		}
		if (FCode.equalsIgnoreCase("AffixGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AffixGetDate = fDate.getDate( FValue );
			}
			else
				AffixGetDate = null;
		}
		if (FCode.equalsIgnoreCase("InHospitalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InHospitalDate = fDate.getDate( FValue );
			}
			else
				InHospitalDate = null;
		}
		if (FCode.equalsIgnoreCase("OutHospitalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutHospitalDate = fDate.getDate( FValue );
			}
			else
				OutHospitalDate = null;
		}
		if (FCode.equalsIgnoreCase("InvaliHosDays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InvaliHosDays = i;
			}
		}
		if (FCode.equalsIgnoreCase("InHospitalDays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InHospitalDays = i;
			}
		}
		if (FCode.equalsIgnoreCase("DianoseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DianoseDate = fDate.getDate( FValue );
			}
			else
				DianoseDate = null;
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
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("AccStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccStartDate = fDate.getDate( FValue );
			}
			else
				AccStartDate = null;
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
		if (FCode.equalsIgnoreCase("AccidentSite"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentSite = FValue.trim();
			}
			else
				AccidentSite = null;
		}
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeathDate = fDate.getDate( FValue );
			}
			else
				DeathDate = null;
		}
		if (FCode.equalsIgnoreCase("DieFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DieFlag = FValue.trim();
			}
			else
				DieFlag = null;
		}
		if (FCode.equalsIgnoreCase("CustState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustState = FValue.trim();
			}
			else
				CustState = null;
		}
		if (FCode.equalsIgnoreCase("AccdentDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccdentDesc = FValue.trim();
			}
			else
				AccdentDesc = null;
		}
		if (FCode.equalsIgnoreCase("CustBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustBirthday = fDate.getDate( FValue );
			}
			else
				CustBirthday = null;
		}
		if (FCode.equalsIgnoreCase("CustomerSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerSex = FValue.trim();
			}
			else
				CustomerSex = null;
		}
		if (FCode.equalsIgnoreCase("CustomerAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CustomerAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				HandleDate = fDate.getDate( FValue );
			}
			else
				HandleDate = null;
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
		if (FCode.equalsIgnoreCase("Dealer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Dealer = FValue.trim();
			}
			else
				Dealer = null;
		}
		if (FCode.equalsIgnoreCase("AppealFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealFlag = FValue.trim();
			}
			else
				AppealFlag = null;
		}
		if (FCode.equalsIgnoreCase("TogetherGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TogetherGet = FValue.trim();
			}
			else
				TogetherGet = null;
		}
		if (FCode.equalsIgnoreCase("GrpDealFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpDealFlag = FValue.trim();
			}
			else
				GrpDealFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetMode = FValue.trim();
			}
			else
				GetMode = null;
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
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
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("DeclineFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeclineFlag = FValue.trim();
			}
			else
				DeclineFlag = null;
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
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseGetMode = FValue.trim();
			}
			else
				CaseGetMode = null;
		}
		if (FCode.equalsIgnoreCase("AccModifyReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccModifyReason = FValue.trim();
			}
			else
				AccModifyReason = null;
		}
		if (FCode.equalsIgnoreCase("CaseNoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CaseNoDate = fDate.getDate( FValue );
			}
			else
				CaseNoDate = null;
		}
		if (FCode.equalsIgnoreCase("VIPFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VIPFlag = FValue.trim();
			}
			else
				VIPFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccidentDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentDetail = FValue.trim();
			}
			else
				AccidentDetail = null;
		}
		if (FCode.equalsIgnoreCase("CureDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CureDesc = FValue.trim();
			}
			else
				CureDesc = null;
		}
		if (FCode.equalsIgnoreCase("AffixConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixConclusion = FValue.trim();
			}
			else
				AffixConclusion = null;
		}
		if (FCode.equalsIgnoreCase("AffixReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixReason = FValue.trim();
			}
			else
				AffixReason = null;
		}
		if (FCode.equalsIgnoreCase("FeeInputFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeInputFlag = FValue.trim();
			}
			else
				FeeInputFlag = null;
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyFlag = FValue.trim();
			}
			else
				SurveyFlag = null;
		}
		if (FCode.equalsIgnoreCase("SubmitFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubmitFlag = FValue.trim();
			}
			else
				SubmitFlag = null;
		}
		if (FCode.equalsIgnoreCase("CondoleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CondoleFlag = FValue.trim();
			}
			else
				CondoleFlag = null;
		}
		if (FCode.equalsIgnoreCase("EditFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EditFlag = FValue.trim();
			}
			else
				EditFlag = null;
		}
		if (FCode.equalsIgnoreCase("SecondUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecondUWFlag = FValue.trim();
			}
			else
				SecondUWFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccDate = fDate.getDate( FValue );
			}
			else
				AccDate = null;
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult1 = FValue.trim();
			}
			else
				AccResult1 = null;
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
		if (FCode.equalsIgnoreCase("MasculineFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MasculineFlag = FValue.trim();
			}
			else
				MasculineFlag = null;
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalCode = FValue.trim();
			}
			else
				HospitalCode = null;
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalName = FValue.trim();
			}
			else
				HospitalName = null;
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
		if (FCode.equalsIgnoreCase("SeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SeqNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standpay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standpay = d;
			}
		}
		if (FCode.equalsIgnoreCase("MedAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MedAccDate = fDate.getDate( FValue );
			}
			else
				MedAccDate = null;
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfName = FValue.trim();
			}
			else
				BnfName = null;
		}
		if (FCode.equalsIgnoreCase("BnfIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfIDNo = FValue.trim();
			}
			else
				BnfIDNo = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("BnfIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfIDType = FValue.trim();
			}
			else
				BnfIDType = null;
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Birthday = fDate.getDate( FValue );
			}
			else
				Birthday = null;
		}
		if (FCode.equalsIgnoreCase("BnfSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfSex = FValue.trim();
			}
			else
				BnfSex = null;
		}
		if (FCode.equalsIgnoreCase("BnfAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfAccName = FValue.trim();
			}
			else
				BnfAccName = null;
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
		if (FCode.equalsIgnoreCase("HospitalClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalClass = FValue.trim();
			}
			else
				HospitalClass = null;
		}
		if (FCode.equalsIgnoreCase("AccProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccProvince = FValue.trim();
			}
			else
				AccProvince = null;
		}
		if (FCode.equalsIgnoreCase("AccCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCity = FValue.trim();
			}
			else
				AccCity = null;
		}
		if (FCode.equalsIgnoreCase("AccCounty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCounty = FValue.trim();
			}
			else
				AccCounty = null;
		}
		if (FCode.equalsIgnoreCase("AccSite"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccSite = FValue.trim();
			}
			else
				AccSite = null;
		}
		if (FCode.equalsIgnoreCase("CaseSource"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseSource = FValue.trim();
			}
			else
				CaseSource = null;
		}
		if (FCode.equalsIgnoreCase("CaseState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseState = FValue.trim();
			}
			else
				CaseState = null;
		}
		if (FCode.equalsIgnoreCase("CloseCaseReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CloseCaseReason = FValue.trim();
			}
			else
				CloseCaseReason = null;
		}
		if (FCode.equalsIgnoreCase("CloseCaseRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CloseCaseRemark = FValue.trim();
			}
			else
				CloseCaseRemark = null;
		}
		if (FCode.equalsIgnoreCase("LRCaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LRCaseNo = FValue.trim();
			}
			else
				LRCaseNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCaseSchema other = (LLCaseSchema)otherObject;
		return
			CaseNo.equals(other.getCaseNo())
			&& RgtNo.equals(other.getRgtNo())
			&& RgtType.equals(other.getRgtType())
			&& RgtState.equals(other.getRgtState())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& AccidentType.equals(other.getAccidentType())
			&& ReceiptFlag.equals(other.getReceiptFlag())
			&& HospitalFlag.equals(other.getHospitalFlag())
			&& fDate.getString(RgtDate).equals(other.getRgtDate())
			&& fDate.getString(ClaimCalDate).equals(other.getClaimCalDate())
			&& fDate.getString(AffixGetDate).equals(other.getAffixGetDate())
			&& fDate.getString(InHospitalDate).equals(other.getInHospitalDate())
			&& fDate.getString(OutHospitalDate).equals(other.getOutHospitalDate())
			&& InvaliHosDays == other.getInvaliHosDays()
			&& InHospitalDays == other.getInHospitalDays()
			&& fDate.getString(DianoseDate).equals(other.getDianoseDate())
			&& PostalAddress.equals(other.getPostalAddress())
			&& Phone.equals(other.getPhone())
			&& fDate.getString(AccStartDate).equals(other.getAccStartDate())
			&& fDate.getString(AccidentDate).equals(other.getAccidentDate())
			&& AccidentSite.equals(other.getAccidentSite())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& DieFlag.equals(other.getDieFlag())
			&& CustState.equals(other.getCustState())
			&& AccdentDesc.equals(other.getAccdentDesc())
			&& fDate.getString(CustBirthday).equals(other.getCustBirthday())
			&& CustomerSex.equals(other.getCustomerSex())
			&& CustomerAge == other.getCustomerAge()
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& Handler.equals(other.getHandler())
			&& fDate.getString(HandleDate).equals(other.getHandleDate())
			&& UWState.equals(other.getUWState())
			&& Dealer.equals(other.getDealer())
			&& AppealFlag.equals(other.getAppealFlag())
			&& TogetherGet.equals(other.getTogetherGet())
			&& GrpDealFlag.equals(other.getGrpDealFlag())
			&& GetMode.equals(other.getGetMode())
			&& GetIntv == other.getGetIntv()
			&& CalFlag.equals(other.getCalFlag())
			&& UWFlag.equals(other.getUWFlag())
			&& DeclineFlag.equals(other.getDeclineFlag())
			&& EndCaseFlag.equals(other.getEndCaseFlag())
			&& fDate.getString(EndCaseDate).equals(other.getEndCaseDate())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& CaseGetMode.equals(other.getCaseGetMode())
			&& AccModifyReason.equals(other.getAccModifyReason())
			&& fDate.getString(CaseNoDate).equals(other.getCaseNoDate())
			&& VIPFlag.equals(other.getVIPFlag())
			&& AccidentDetail.equals(other.getAccidentDetail())
			&& CureDesc.equals(other.getCureDesc())
			&& AffixConclusion.equals(other.getAffixConclusion())
			&& AffixReason.equals(other.getAffixReason())
			&& FeeInputFlag.equals(other.getFeeInputFlag())
			&& SurveyFlag.equals(other.getSurveyFlag())
			&& SubmitFlag.equals(other.getSubmitFlag())
			&& CondoleFlag.equals(other.getCondoleFlag())
			&& EditFlag.equals(other.getEditFlag())
			&& SecondUWFlag.equals(other.getSecondUWFlag())
			&& fDate.getString(AccDate).equals(other.getAccDate())
			&& AccResult1.equals(other.getAccResult1())
			&& AccResult2.equals(other.getAccResult2())
			&& MasculineFlag.equals(other.getMasculineFlag())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& Remark.equals(other.getRemark())
			&& SeqNo == other.getSeqNo()
			&& Standpay == other.getStandpay()
			&& fDate.getString(MedAccDate).equals(other.getMedAccDate())
			&& BnfName.equals(other.getBnfName())
			&& BnfIDNo.equals(other.getBnfIDNo())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& BnfIDType.equals(other.getBnfIDType())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& BnfSex.equals(other.getBnfSex())
			&& BnfAccName.equals(other.getBnfAccName())
			&& RealPay == other.getRealPay()
			&& HospitalClass.equals(other.getHospitalClass())
			&& AccProvince.equals(other.getAccProvince())
			&& AccCity.equals(other.getAccCity())
			&& AccCounty.equals(other.getAccCounty())
			&& AccSite.equals(other.getAccSite())
			&& CaseSource.equals(other.getCaseSource())
			&& CaseState.equals(other.getCaseState())
			&& CloseCaseReason.equals(other.getCloseCaseReason())
			&& CloseCaseRemark.equals(other.getCloseCaseRemark())
			&& LRCaseNo.equals(other.getLRCaseNo())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& ContNo.equals(other.getContNo());
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
		if( strFieldName.equals("CaseNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 1;
		}
		if( strFieldName.equals("RgtType") ) {
			return 2;
		}
		if( strFieldName.equals("RgtState") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 5;
		}
		if( strFieldName.equals("AccidentType") ) {
			return 6;
		}
		if( strFieldName.equals("ReceiptFlag") ) {
			return 7;
		}
		if( strFieldName.equals("HospitalFlag") ) {
			return 8;
		}
		if( strFieldName.equals("RgtDate") ) {
			return 9;
		}
		if( strFieldName.equals("ClaimCalDate") ) {
			return 10;
		}
		if( strFieldName.equals("AffixGetDate") ) {
			return 11;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return 12;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return 13;
		}
		if( strFieldName.equals("InvaliHosDays") ) {
			return 14;
		}
		if( strFieldName.equals("InHospitalDays") ) {
			return 15;
		}
		if( strFieldName.equals("DianoseDate") ) {
			return 16;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 17;
		}
		if( strFieldName.equals("Phone") ) {
			return 18;
		}
		if( strFieldName.equals("AccStartDate") ) {
			return 19;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 20;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return 21;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 22;
		}
		if( strFieldName.equals("DieFlag") ) {
			return 23;
		}
		if( strFieldName.equals("CustState") ) {
			return 24;
		}
		if( strFieldName.equals("AccdentDesc") ) {
			return 25;
		}
		if( strFieldName.equals("CustBirthday") ) {
			return 26;
		}
		if( strFieldName.equals("CustomerSex") ) {
			return 27;
		}
		if( strFieldName.equals("CustomerAge") ) {
			return 28;
		}
		if( strFieldName.equals("IDType") ) {
			return 29;
		}
		if( strFieldName.equals("IDNo") ) {
			return 30;
		}
		if( strFieldName.equals("Handler") ) {
			return 31;
		}
		if( strFieldName.equals("HandleDate") ) {
			return 32;
		}
		if( strFieldName.equals("UWState") ) {
			return 33;
		}
		if( strFieldName.equals("Dealer") ) {
			return 34;
		}
		if( strFieldName.equals("AppealFlag") ) {
			return 35;
		}
		if( strFieldName.equals("TogetherGet") ) {
			return 36;
		}
		if( strFieldName.equals("GrpDealFlag") ) {
			return 37;
		}
		if( strFieldName.equals("GetMode") ) {
			return 38;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 39;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 40;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 41;
		}
		if( strFieldName.equals("DeclineFlag") ) {
			return 42;
		}
		if( strFieldName.equals("EndCaseFlag") ) {
			return 43;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 44;
		}
		if( strFieldName.equals("MngCom") ) {
			return 45;
		}
		if( strFieldName.equals("Operator") ) {
			return 46;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 47;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 48;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 50;
		}
		if( strFieldName.equals("BankCode") ) {
			return 51;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 52;
		}
		if( strFieldName.equals("AccName") ) {
			return 53;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return 54;
		}
		if( strFieldName.equals("AccModifyReason") ) {
			return 55;
		}
		if( strFieldName.equals("CaseNoDate") ) {
			return 56;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return 57;
		}
		if( strFieldName.equals("AccidentDetail") ) {
			return 58;
		}
		if( strFieldName.equals("CureDesc") ) {
			return 59;
		}
		if( strFieldName.equals("AffixConclusion") ) {
			return 60;
		}
		if( strFieldName.equals("AffixReason") ) {
			return 61;
		}
		if( strFieldName.equals("FeeInputFlag") ) {
			return 62;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return 63;
		}
		if( strFieldName.equals("SubmitFlag") ) {
			return 64;
		}
		if( strFieldName.equals("CondoleFlag") ) {
			return 65;
		}
		if( strFieldName.equals("EditFlag") ) {
			return 66;
		}
		if( strFieldName.equals("SecondUWFlag") ) {
			return 67;
		}
		if( strFieldName.equals("AccDate") ) {
			return 68;
		}
		if( strFieldName.equals("AccResult1") ) {
			return 69;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 70;
		}
		if( strFieldName.equals("MasculineFlag") ) {
			return 71;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 72;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 73;
		}
		if( strFieldName.equals("Remark") ) {
			return 74;
		}
		if( strFieldName.equals("SeqNo") ) {
			return 75;
		}
		if( strFieldName.equals("Standpay") ) {
			return 76;
		}
		if( strFieldName.equals("MedAccDate") ) {
			return 77;
		}
		if( strFieldName.equals("BnfName") ) {
			return 78;
		}
		if( strFieldName.equals("BnfIDNo") ) {
			return 79;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 80;
		}
		if( strFieldName.equals("BnfIDType") ) {
			return 81;
		}
		if( strFieldName.equals("Birthday") ) {
			return 82;
		}
		if( strFieldName.equals("BnfSex") ) {
			return 83;
		}
		if( strFieldName.equals("BnfAccName") ) {
			return 84;
		}
		if( strFieldName.equals("RealPay") ) {
			return 85;
		}
		if( strFieldName.equals("HospitalClass") ) {
			return 86;
		}
		if( strFieldName.equals("AccProvince") ) {
			return 87;
		}
		if( strFieldName.equals("AccCity") ) {
			return 88;
		}
		if( strFieldName.equals("AccCounty") ) {
			return 89;
		}
		if( strFieldName.equals("AccSite") ) {
			return 90;
		}
		if( strFieldName.equals("CaseSource") ) {
			return 91;
		}
		if( strFieldName.equals("CaseState") ) {
			return 92;
		}
		if( strFieldName.equals("CloseCaseReason") ) {
			return 93;
		}
		if( strFieldName.equals("CloseCaseRemark") ) {
			return 94;
		}
		if( strFieldName.equals("LRCaseNo") ) {
			return 95;
		}
		if( strFieldName.equals("ComCode") ) {
			return 96;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 97;
		}
		if( strFieldName.equals("ContNo") ) {
			return 98;
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
				strFieldName = "CaseNo";
				break;
			case 1:
				strFieldName = "RgtNo";
				break;
			case 2:
				strFieldName = "RgtType";
				break;
			case 3:
				strFieldName = "RgtState";
				break;
			case 4:
				strFieldName = "CustomerNo";
				break;
			case 5:
				strFieldName = "CustomerName";
				break;
			case 6:
				strFieldName = "AccidentType";
				break;
			case 7:
				strFieldName = "ReceiptFlag";
				break;
			case 8:
				strFieldName = "HospitalFlag";
				break;
			case 9:
				strFieldName = "RgtDate";
				break;
			case 10:
				strFieldName = "ClaimCalDate";
				break;
			case 11:
				strFieldName = "AffixGetDate";
				break;
			case 12:
				strFieldName = "InHospitalDate";
				break;
			case 13:
				strFieldName = "OutHospitalDate";
				break;
			case 14:
				strFieldName = "InvaliHosDays";
				break;
			case 15:
				strFieldName = "InHospitalDays";
				break;
			case 16:
				strFieldName = "DianoseDate";
				break;
			case 17:
				strFieldName = "PostalAddress";
				break;
			case 18:
				strFieldName = "Phone";
				break;
			case 19:
				strFieldName = "AccStartDate";
				break;
			case 20:
				strFieldName = "AccidentDate";
				break;
			case 21:
				strFieldName = "AccidentSite";
				break;
			case 22:
				strFieldName = "DeathDate";
				break;
			case 23:
				strFieldName = "DieFlag";
				break;
			case 24:
				strFieldName = "CustState";
				break;
			case 25:
				strFieldName = "AccdentDesc";
				break;
			case 26:
				strFieldName = "CustBirthday";
				break;
			case 27:
				strFieldName = "CustomerSex";
				break;
			case 28:
				strFieldName = "CustomerAge";
				break;
			case 29:
				strFieldName = "IDType";
				break;
			case 30:
				strFieldName = "IDNo";
				break;
			case 31:
				strFieldName = "Handler";
				break;
			case 32:
				strFieldName = "HandleDate";
				break;
			case 33:
				strFieldName = "UWState";
				break;
			case 34:
				strFieldName = "Dealer";
				break;
			case 35:
				strFieldName = "AppealFlag";
				break;
			case 36:
				strFieldName = "TogetherGet";
				break;
			case 37:
				strFieldName = "GrpDealFlag";
				break;
			case 38:
				strFieldName = "GetMode";
				break;
			case 39:
				strFieldName = "GetIntv";
				break;
			case 40:
				strFieldName = "CalFlag";
				break;
			case 41:
				strFieldName = "UWFlag";
				break;
			case 42:
				strFieldName = "DeclineFlag";
				break;
			case 43:
				strFieldName = "EndCaseFlag";
				break;
			case 44:
				strFieldName = "EndCaseDate";
				break;
			case 45:
				strFieldName = "MngCom";
				break;
			case 46:
				strFieldName = "Operator";
				break;
			case 47:
				strFieldName = "MakeDate";
				break;
			case 48:
				strFieldName = "MakeTime";
				break;
			case 49:
				strFieldName = "ModifyDate";
				break;
			case 50:
				strFieldName = "ModifyTime";
				break;
			case 51:
				strFieldName = "BankCode";
				break;
			case 52:
				strFieldName = "BankAccNo";
				break;
			case 53:
				strFieldName = "AccName";
				break;
			case 54:
				strFieldName = "CaseGetMode";
				break;
			case 55:
				strFieldName = "AccModifyReason";
				break;
			case 56:
				strFieldName = "CaseNoDate";
				break;
			case 57:
				strFieldName = "VIPFlag";
				break;
			case 58:
				strFieldName = "AccidentDetail";
				break;
			case 59:
				strFieldName = "CureDesc";
				break;
			case 60:
				strFieldName = "AffixConclusion";
				break;
			case 61:
				strFieldName = "AffixReason";
				break;
			case 62:
				strFieldName = "FeeInputFlag";
				break;
			case 63:
				strFieldName = "SurveyFlag";
				break;
			case 64:
				strFieldName = "SubmitFlag";
				break;
			case 65:
				strFieldName = "CondoleFlag";
				break;
			case 66:
				strFieldName = "EditFlag";
				break;
			case 67:
				strFieldName = "SecondUWFlag";
				break;
			case 68:
				strFieldName = "AccDate";
				break;
			case 69:
				strFieldName = "AccResult1";
				break;
			case 70:
				strFieldName = "AccResult2";
				break;
			case 71:
				strFieldName = "MasculineFlag";
				break;
			case 72:
				strFieldName = "HospitalCode";
				break;
			case 73:
				strFieldName = "HospitalName";
				break;
			case 74:
				strFieldName = "Remark";
				break;
			case 75:
				strFieldName = "SeqNo";
				break;
			case 76:
				strFieldName = "Standpay";
				break;
			case 77:
				strFieldName = "MedAccDate";
				break;
			case 78:
				strFieldName = "BnfName";
				break;
			case 79:
				strFieldName = "BnfIDNo";
				break;
			case 80:
				strFieldName = "RelationToInsured";
				break;
			case 81:
				strFieldName = "BnfIDType";
				break;
			case 82:
				strFieldName = "Birthday";
				break;
			case 83:
				strFieldName = "BnfSex";
				break;
			case 84:
				strFieldName = "BnfAccName";
				break;
			case 85:
				strFieldName = "RealPay";
				break;
			case 86:
				strFieldName = "HospitalClass";
				break;
			case 87:
				strFieldName = "AccProvince";
				break;
			case 88:
				strFieldName = "AccCity";
				break;
			case 89:
				strFieldName = "AccCounty";
				break;
			case 90:
				strFieldName = "AccSite";
				break;
			case 91:
				strFieldName = "CaseSource";
				break;
			case 92:
				strFieldName = "CaseState";
				break;
			case 93:
				strFieldName = "CloseCaseReason";
				break;
			case 94:
				strFieldName = "CloseCaseRemark";
				break;
			case 95:
				strFieldName = "LRCaseNo";
				break;
			case 96:
				strFieldName = "ComCode";
				break;
			case 97:
				strFieldName = "ModifyOperator";
				break;
			case 98:
				strFieldName = "ContNo";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ClaimCalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AffixGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InvaliHosDays") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InHospitalDays") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DianoseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeathDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DieFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccdentDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomerSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandleDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Dealer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TogetherGet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpDealFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclineFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndCaseFlag") ) {
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
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccModifyReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CureDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeInputFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubmitFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CondoleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EditFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecondUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccResult1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MasculineFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SeqNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standpay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MedAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BnfName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BnfSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HospitalClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCounty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccSite") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseSource") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CloseCaseReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CloseCaseRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LRCaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
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
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
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
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 75:
				nFieldType = Schema.TYPE_INT;
				break;
			case 76:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 77:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 89:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 95:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 96:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 97:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 98:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
