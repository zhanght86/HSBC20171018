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
import com.sinosoft.lis.db.LLBCaseDB;

/*
 * <p>ClassName: LLBCaseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLBCaseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLBCaseSchema.class);

	// @Field
	/** 修改序号 */
	private String EditNo;
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
	/** 出险时间 */
	private Date AccDate;
	/** 出险结果1 */
	private String AccResult1;
	/** 出险结果2 */
	private String AccResult2;
	/** 阳性标志 */
	private String MasculineFlag;
	/** 修改原因 */
	private String EditReason;
	/** 回退号 */
	private String BackNo;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;

	public static final int FIELDNUM = 77;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLBCaseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EditNo";
		pk[1] = "CaseNo";
		pk[2] = "CustomerNo";

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
		LLBCaseSchema cloned = (LLBCaseSchema)super.clone();
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
	* 默认为是赔案号，暂时不用该字段，但保持原来设计
	*/
	public String getEditNo()
	{
		return EditNo;
	}
	public void setEditNo(String aEditNo)
	{
		EditNo = aEditNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	/**
	* 如果是团体，可以理解为:团体申请受理号
	*/
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	/**
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
		RgtType = aRgtType;
	}
	/**
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
		RgtState = aRgtState;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		CustomerName = aCustomerName;
	}
	/**
	* 1 －－ 意外<p>
	* 2 －－ 疾病
	*/
	public String getAccidentType()
	{
		return AccidentType;
	}
	public void setAccidentType(String aAccidentType)
	{
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

	public String getPostalAddress()
	{
		return PostalAddress;
	}
	public void setPostalAddress(String aPostalAddress)
	{
		PostalAddress = aPostalAddress;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
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

	public String getAccidentSite()
	{
		return AccidentSite;
	}
	public void setAccidentSite(String aAccidentSite)
	{
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

	public String getDieFlag()
	{
		return DieFlag;
	}
	public void setDieFlag(String aDieFlag)
	{
		DieFlag = aDieFlag;
	}
	/**
	* 0正常<p>
	* 1非正常
	*/
	public String getCustState()
	{
		return CustState;
	}
	public void setCustState(String aCustState)
	{
		CustState = aCustState;
	}
	public String getAccdentDesc()
	{
		return AccdentDesc;
	}
	public void setAccdentDesc(String aAccdentDesc)
	{
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

	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
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
		UWState = aUWState;
	}
	public String getDealer()
	{
		return Dealer;
	}
	public void setDealer(String aDealer)
	{
		Dealer = aDealer;
	}
	/**
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
		AppealFlag = aAppealFlag;
	}
	public String getTogetherGet()
	{
		return TogetherGet;
	}
	public void setTogetherGet(String aTogetherGet)
	{
		TogetherGet = aTogetherGet;
	}
	public String getGrpDealFlag()
	{
		return GrpDealFlag;
	}
	public void setGrpDealFlag(String aGrpDealFlag)
	{
		GrpDealFlag = aGrpDealFlag;
	}
	/**
	* 1 一次统一给付<p>
	* 2 分期支付<p>
	* 3 按年金方式领取
	*/
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		GetMode = aGetMode;
	}
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
		EndCaseFlag = aEndCaseFlag;
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
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
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
		CaseGetMode = aCaseGetMode;
	}
	public String getAccModifyReason()
	{
		return AccModifyReason;
	}
	public void setAccModifyReason(String aAccModifyReason)
	{
		AccModifyReason = aAccModifyReason;
	}
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
		VIPFlag = aVIPFlag;
	}
	public String getAccidentDetail()
	{
		return AccidentDetail;
	}
	public void setAccidentDetail(String aAccidentDetail)
	{
		AccidentDetail = aAccidentDetail;
	}
	public String getCureDesc()
	{
		return CureDesc;
	}
	public void setCureDesc(String aCureDesc)
	{
		CureDesc = aCureDesc;
	}
	/**
	* 不齐全、不齐全通过、齐全通过
	*/
	public String getAffixConclusion()
	{
		return AffixConclusion;
	}
	public void setAffixConclusion(String aAffixConclusion)
	{
		AffixConclusion = aAffixConclusion;
	}
	public String getAffixReason()
	{
		return AffixReason;
	}
	public void setAffixReason(String aAffixReason)
	{
		AffixReason = aAffixReason;
	}
	/**
	* 0 不需要<p>
	* 1 必须
	*/
	public String getFeeInputFlag()
	{
		return FeeInputFlag;
	}
	public void setFeeInputFlag(String aFeeInputFlag)
	{
		FeeInputFlag = aFeeInputFlag;
	}
	/**
	* 0有<p>
	* 1没有
	*/
	public String getSurveyFlag()
	{
		return SurveyFlag;
	}
	public void setSurveyFlag(String aSurveyFlag)
	{
		SurveyFlag = aSurveyFlag;
	}
	public String getSubmitFlag()
	{
		return SubmitFlag;
	}
	public void setSubmitFlag(String aSubmitFlag)
	{
		SubmitFlag = aSubmitFlag;
	}
	public String getCondoleFlag()
	{
		return CondoleFlag;
	}
	public void setCondoleFlag(String aCondoleFlag)
	{
		CondoleFlag = aCondoleFlag;
	}
	public String getEditFlag()
	{
		return EditFlag;
	}
	public void setEditFlag(String aEditFlag)
	{
		EditFlag = aEditFlag;
	}
	public String getSecondUWFlag()
	{
		return SecondUWFlag;
	}
	public void setSecondUWFlag(String aSecondUWFlag)
	{
		SecondUWFlag = aSecondUWFlag;
	}
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

	public String getAccResult1()
	{
		return AccResult1;
	}
	public void setAccResult1(String aAccResult1)
	{
		AccResult1 = aAccResult1;
	}
	public String getAccResult2()
	{
		return AccResult2;
	}
	public void setAccResult2(String aAccResult2)
	{
		AccResult2 = aAccResult2;
	}
	public String getMasculineFlag()
	{
		return MasculineFlag;
	}
	public void setMasculineFlag(String aMasculineFlag)
	{
		MasculineFlag = aMasculineFlag;
	}
	public String getEditReason()
	{
		return EditReason;
	}
	public void setEditReason(String aEditReason)
	{
		EditReason = aEditReason;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		BackNo = aBackNo;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		HospitalName = aHospitalName;
	}

	/**
	* 使用另外一个 LLBCaseSchema 对象给 Schema 赋值
	* @param: aLLBCaseSchema LLBCaseSchema
	**/
	public void setSchema(LLBCaseSchema aLLBCaseSchema)
	{
		this.EditNo = aLLBCaseSchema.getEditNo();
		this.CaseNo = aLLBCaseSchema.getCaseNo();
		this.RgtNo = aLLBCaseSchema.getRgtNo();
		this.RgtType = aLLBCaseSchema.getRgtType();
		this.RgtState = aLLBCaseSchema.getRgtState();
		this.CustomerNo = aLLBCaseSchema.getCustomerNo();
		this.CustomerName = aLLBCaseSchema.getCustomerName();
		this.AccidentType = aLLBCaseSchema.getAccidentType();
		this.ReceiptFlag = aLLBCaseSchema.getReceiptFlag();
		this.HospitalFlag = aLLBCaseSchema.getHospitalFlag();
		this.RgtDate = fDate.getDate( aLLBCaseSchema.getRgtDate());
		this.ClaimCalDate = fDate.getDate( aLLBCaseSchema.getClaimCalDate());
		this.AffixGetDate = fDate.getDate( aLLBCaseSchema.getAffixGetDate());
		this.InHospitalDate = fDate.getDate( aLLBCaseSchema.getInHospitalDate());
		this.OutHospitalDate = fDate.getDate( aLLBCaseSchema.getOutHospitalDate());
		this.InvaliHosDays = aLLBCaseSchema.getInvaliHosDays();
		this.InHospitalDays = aLLBCaseSchema.getInHospitalDays();
		this.DianoseDate = fDate.getDate( aLLBCaseSchema.getDianoseDate());
		this.PostalAddress = aLLBCaseSchema.getPostalAddress();
		this.Phone = aLLBCaseSchema.getPhone();
		this.AccStartDate = fDate.getDate( aLLBCaseSchema.getAccStartDate());
		this.AccidentDate = fDate.getDate( aLLBCaseSchema.getAccidentDate());
		this.AccidentSite = aLLBCaseSchema.getAccidentSite();
		this.DeathDate = fDate.getDate( aLLBCaseSchema.getDeathDate());
		this.DieFlag = aLLBCaseSchema.getDieFlag();
		this.CustState = aLLBCaseSchema.getCustState();
		this.AccdentDesc = aLLBCaseSchema.getAccdentDesc();
		this.CustBirthday = fDate.getDate( aLLBCaseSchema.getCustBirthday());
		this.CustomerSex = aLLBCaseSchema.getCustomerSex();
		this.CustomerAge = aLLBCaseSchema.getCustomerAge();
		this.IDType = aLLBCaseSchema.getIDType();
		this.IDNo = aLLBCaseSchema.getIDNo();
		this.Handler = aLLBCaseSchema.getHandler();
		this.HandleDate = fDate.getDate( aLLBCaseSchema.getHandleDate());
		this.UWState = aLLBCaseSchema.getUWState();
		this.Dealer = aLLBCaseSchema.getDealer();
		this.AppealFlag = aLLBCaseSchema.getAppealFlag();
		this.TogetherGet = aLLBCaseSchema.getTogetherGet();
		this.GrpDealFlag = aLLBCaseSchema.getGrpDealFlag();
		this.GetMode = aLLBCaseSchema.getGetMode();
		this.GetIntv = aLLBCaseSchema.getGetIntv();
		this.CalFlag = aLLBCaseSchema.getCalFlag();
		this.UWFlag = aLLBCaseSchema.getUWFlag();
		this.DeclineFlag = aLLBCaseSchema.getDeclineFlag();
		this.EndCaseFlag = aLLBCaseSchema.getEndCaseFlag();
		this.EndCaseDate = fDate.getDate( aLLBCaseSchema.getEndCaseDate());
		this.MngCom = aLLBCaseSchema.getMngCom();
		this.Operator = aLLBCaseSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLBCaseSchema.getMakeDate());
		this.MakeTime = aLLBCaseSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLBCaseSchema.getModifyDate());
		this.ModifyTime = aLLBCaseSchema.getModifyTime();
		this.BankCode = aLLBCaseSchema.getBankCode();
		this.BankAccNo = aLLBCaseSchema.getBankAccNo();
		this.AccName = aLLBCaseSchema.getAccName();
		this.CaseGetMode = aLLBCaseSchema.getCaseGetMode();
		this.AccModifyReason = aLLBCaseSchema.getAccModifyReason();
		this.CaseNoDate = fDate.getDate( aLLBCaseSchema.getCaseNoDate());
		this.VIPFlag = aLLBCaseSchema.getVIPFlag();
		this.AccidentDetail = aLLBCaseSchema.getAccidentDetail();
		this.CureDesc = aLLBCaseSchema.getCureDesc();
		this.AffixConclusion = aLLBCaseSchema.getAffixConclusion();
		this.AffixReason = aLLBCaseSchema.getAffixReason();
		this.FeeInputFlag = aLLBCaseSchema.getFeeInputFlag();
		this.SurveyFlag = aLLBCaseSchema.getSurveyFlag();
		this.SubmitFlag = aLLBCaseSchema.getSubmitFlag();
		this.CondoleFlag = aLLBCaseSchema.getCondoleFlag();
		this.EditFlag = aLLBCaseSchema.getEditFlag();
		this.SecondUWFlag = aLLBCaseSchema.getSecondUWFlag();
		this.AccDate = fDate.getDate( aLLBCaseSchema.getAccDate());
		this.AccResult1 = aLLBCaseSchema.getAccResult1();
		this.AccResult2 = aLLBCaseSchema.getAccResult2();
		this.MasculineFlag = aLLBCaseSchema.getMasculineFlag();
		this.EditReason = aLLBCaseSchema.getEditReason();
		this.BackNo = aLLBCaseSchema.getBackNo();
		this.HospitalCode = aLLBCaseSchema.getHospitalCode();
		this.HospitalName = aLLBCaseSchema.getHospitalName();
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
			if( rs.getString("EditNo") == null )
				this.EditNo = null;
			else
				this.EditNo = rs.getString("EditNo").trim();

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

			if( rs.getString("EditReason") == null )
				this.EditReason = null;
			else
				this.EditReason = rs.getString("EditReason").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLBCase表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLBCaseSchema getSchema()
	{
		LLBCaseSchema aLLBCaseSchema = new LLBCaseSchema();
		aLLBCaseSchema.setSchema(this);
		return aLLBCaseSchema;
	}

	public LLBCaseDB getDB()
	{
		LLBCaseDB aDBOper = new LLBCaseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBCase描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EditNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(EditReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBCase>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EditNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RgtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RgtState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccidentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ReceiptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HospitalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RgtDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ClaimCalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			AffixGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			InHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			OutHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			InvaliHosDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			InHospitalDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			DianoseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AccStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			AccidentSite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			DieFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			CustState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AccdentDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			CustBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			CustomerSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			CustomerAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			HandleDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			UWState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Dealer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			AppealFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			TogetherGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GrpDealFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).intValue();
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			DeclineFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			EndCaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			CaseGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			AccModifyReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			CaseNoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			VIPFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			AccidentDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			CureDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			AffixConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			AffixReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			FeeInputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			SurveyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			SubmitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			CondoleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			EditFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			SecondUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			AccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70,SysConst.PACKAGESPILTER));
			AccResult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			MasculineFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			EditReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseSchema";
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
		if (FCode.equalsIgnoreCase("EditNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EditNo));
		}
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
		if (FCode.equalsIgnoreCase("EditReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EditReason));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
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
				strFieldValue = StrTool.GBKToUnicode(EditNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RgtType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RgtState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccidentType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ReceiptFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HospitalFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getClaimCalDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAffixGetDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
				break;
			case 15:
				strFieldValue = String.valueOf(InvaliHosDays);
				break;
			case 16:
				strFieldValue = String.valueOf(InHospitalDays);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDianoseDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccStartDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AccidentSite);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(DieFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(CustState);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AccdentDesc);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustBirthday()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(CustomerSex);
				break;
			case 29:
				strFieldValue = String.valueOf(CustomerAge);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(UWState);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Dealer);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(AppealFlag);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(TogetherGet);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(GrpDealFlag);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 40:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(DeclineFlag);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(EndCaseFlag);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(CaseGetMode);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(AccModifyReason);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCaseNoDate()));
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(VIPFlag);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(AccidentDetail);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(CureDesc);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(AffixConclusion);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(AffixReason);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(FeeInputFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(SurveyFlag);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(SubmitFlag);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(CondoleFlag);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(EditFlag);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(SecondUWFlag);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(AccResult1);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(MasculineFlag);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(EditReason);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
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

		if (FCode.equalsIgnoreCase("EditNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EditNo = FValue.trim();
			}
			else
				EditNo = null;
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
		if (FCode.equalsIgnoreCase("EditReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EditReason = FValue.trim();
			}
			else
				EditReason = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLBCaseSchema other = (LLBCaseSchema)otherObject;
		return
			EditNo.equals(other.getEditNo())
			&& CaseNo.equals(other.getCaseNo())
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
			&& EditReason.equals(other.getEditReason())
			&& BackNo.equals(other.getBackNo())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName());
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
		if( strFieldName.equals("EditNo") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 2;
		}
		if( strFieldName.equals("RgtType") ) {
			return 3;
		}
		if( strFieldName.equals("RgtState") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 5;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 6;
		}
		if( strFieldName.equals("AccidentType") ) {
			return 7;
		}
		if( strFieldName.equals("ReceiptFlag") ) {
			return 8;
		}
		if( strFieldName.equals("HospitalFlag") ) {
			return 9;
		}
		if( strFieldName.equals("RgtDate") ) {
			return 10;
		}
		if( strFieldName.equals("ClaimCalDate") ) {
			return 11;
		}
		if( strFieldName.equals("AffixGetDate") ) {
			return 12;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return 13;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return 14;
		}
		if( strFieldName.equals("InvaliHosDays") ) {
			return 15;
		}
		if( strFieldName.equals("InHospitalDays") ) {
			return 16;
		}
		if( strFieldName.equals("DianoseDate") ) {
			return 17;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 18;
		}
		if( strFieldName.equals("Phone") ) {
			return 19;
		}
		if( strFieldName.equals("AccStartDate") ) {
			return 20;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 21;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return 22;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 23;
		}
		if( strFieldName.equals("DieFlag") ) {
			return 24;
		}
		if( strFieldName.equals("CustState") ) {
			return 25;
		}
		if( strFieldName.equals("AccdentDesc") ) {
			return 26;
		}
		if( strFieldName.equals("CustBirthday") ) {
			return 27;
		}
		if( strFieldName.equals("CustomerSex") ) {
			return 28;
		}
		if( strFieldName.equals("CustomerAge") ) {
			return 29;
		}
		if( strFieldName.equals("IDType") ) {
			return 30;
		}
		if( strFieldName.equals("IDNo") ) {
			return 31;
		}
		if( strFieldName.equals("Handler") ) {
			return 32;
		}
		if( strFieldName.equals("HandleDate") ) {
			return 33;
		}
		if( strFieldName.equals("UWState") ) {
			return 34;
		}
		if( strFieldName.equals("Dealer") ) {
			return 35;
		}
		if( strFieldName.equals("AppealFlag") ) {
			return 36;
		}
		if( strFieldName.equals("TogetherGet") ) {
			return 37;
		}
		if( strFieldName.equals("GrpDealFlag") ) {
			return 38;
		}
		if( strFieldName.equals("GetMode") ) {
			return 39;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 40;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 41;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 42;
		}
		if( strFieldName.equals("DeclineFlag") ) {
			return 43;
		}
		if( strFieldName.equals("EndCaseFlag") ) {
			return 44;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 45;
		}
		if( strFieldName.equals("MngCom") ) {
			return 46;
		}
		if( strFieldName.equals("Operator") ) {
			return 47;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 48;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 50;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 51;
		}
		if( strFieldName.equals("BankCode") ) {
			return 52;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 53;
		}
		if( strFieldName.equals("AccName") ) {
			return 54;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return 55;
		}
		if( strFieldName.equals("AccModifyReason") ) {
			return 56;
		}
		if( strFieldName.equals("CaseNoDate") ) {
			return 57;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return 58;
		}
		if( strFieldName.equals("AccidentDetail") ) {
			return 59;
		}
		if( strFieldName.equals("CureDesc") ) {
			return 60;
		}
		if( strFieldName.equals("AffixConclusion") ) {
			return 61;
		}
		if( strFieldName.equals("AffixReason") ) {
			return 62;
		}
		if( strFieldName.equals("FeeInputFlag") ) {
			return 63;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return 64;
		}
		if( strFieldName.equals("SubmitFlag") ) {
			return 65;
		}
		if( strFieldName.equals("CondoleFlag") ) {
			return 66;
		}
		if( strFieldName.equals("EditFlag") ) {
			return 67;
		}
		if( strFieldName.equals("SecondUWFlag") ) {
			return 68;
		}
		if( strFieldName.equals("AccDate") ) {
			return 69;
		}
		if( strFieldName.equals("AccResult1") ) {
			return 70;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 71;
		}
		if( strFieldName.equals("MasculineFlag") ) {
			return 72;
		}
		if( strFieldName.equals("EditReason") ) {
			return 73;
		}
		if( strFieldName.equals("BackNo") ) {
			return 74;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 75;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 76;
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
				strFieldName = "EditNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "RgtNo";
				break;
			case 3:
				strFieldName = "RgtType";
				break;
			case 4:
				strFieldName = "RgtState";
				break;
			case 5:
				strFieldName = "CustomerNo";
				break;
			case 6:
				strFieldName = "CustomerName";
				break;
			case 7:
				strFieldName = "AccidentType";
				break;
			case 8:
				strFieldName = "ReceiptFlag";
				break;
			case 9:
				strFieldName = "HospitalFlag";
				break;
			case 10:
				strFieldName = "RgtDate";
				break;
			case 11:
				strFieldName = "ClaimCalDate";
				break;
			case 12:
				strFieldName = "AffixGetDate";
				break;
			case 13:
				strFieldName = "InHospitalDate";
				break;
			case 14:
				strFieldName = "OutHospitalDate";
				break;
			case 15:
				strFieldName = "InvaliHosDays";
				break;
			case 16:
				strFieldName = "InHospitalDays";
				break;
			case 17:
				strFieldName = "DianoseDate";
				break;
			case 18:
				strFieldName = "PostalAddress";
				break;
			case 19:
				strFieldName = "Phone";
				break;
			case 20:
				strFieldName = "AccStartDate";
				break;
			case 21:
				strFieldName = "AccidentDate";
				break;
			case 22:
				strFieldName = "AccidentSite";
				break;
			case 23:
				strFieldName = "DeathDate";
				break;
			case 24:
				strFieldName = "DieFlag";
				break;
			case 25:
				strFieldName = "CustState";
				break;
			case 26:
				strFieldName = "AccdentDesc";
				break;
			case 27:
				strFieldName = "CustBirthday";
				break;
			case 28:
				strFieldName = "CustomerSex";
				break;
			case 29:
				strFieldName = "CustomerAge";
				break;
			case 30:
				strFieldName = "IDType";
				break;
			case 31:
				strFieldName = "IDNo";
				break;
			case 32:
				strFieldName = "Handler";
				break;
			case 33:
				strFieldName = "HandleDate";
				break;
			case 34:
				strFieldName = "UWState";
				break;
			case 35:
				strFieldName = "Dealer";
				break;
			case 36:
				strFieldName = "AppealFlag";
				break;
			case 37:
				strFieldName = "TogetherGet";
				break;
			case 38:
				strFieldName = "GrpDealFlag";
				break;
			case 39:
				strFieldName = "GetMode";
				break;
			case 40:
				strFieldName = "GetIntv";
				break;
			case 41:
				strFieldName = "CalFlag";
				break;
			case 42:
				strFieldName = "UWFlag";
				break;
			case 43:
				strFieldName = "DeclineFlag";
				break;
			case 44:
				strFieldName = "EndCaseFlag";
				break;
			case 45:
				strFieldName = "EndCaseDate";
				break;
			case 46:
				strFieldName = "MngCom";
				break;
			case 47:
				strFieldName = "Operator";
				break;
			case 48:
				strFieldName = "MakeDate";
				break;
			case 49:
				strFieldName = "MakeTime";
				break;
			case 50:
				strFieldName = "ModifyDate";
				break;
			case 51:
				strFieldName = "ModifyTime";
				break;
			case 52:
				strFieldName = "BankCode";
				break;
			case 53:
				strFieldName = "BankAccNo";
				break;
			case 54:
				strFieldName = "AccName";
				break;
			case 55:
				strFieldName = "CaseGetMode";
				break;
			case 56:
				strFieldName = "AccModifyReason";
				break;
			case 57:
				strFieldName = "CaseNoDate";
				break;
			case 58:
				strFieldName = "VIPFlag";
				break;
			case 59:
				strFieldName = "AccidentDetail";
				break;
			case 60:
				strFieldName = "CureDesc";
				break;
			case 61:
				strFieldName = "AffixConclusion";
				break;
			case 62:
				strFieldName = "AffixReason";
				break;
			case 63:
				strFieldName = "FeeInputFlag";
				break;
			case 64:
				strFieldName = "SurveyFlag";
				break;
			case 65:
				strFieldName = "SubmitFlag";
				break;
			case 66:
				strFieldName = "CondoleFlag";
				break;
			case 67:
				strFieldName = "EditFlag";
				break;
			case 68:
				strFieldName = "SecondUWFlag";
				break;
			case 69:
				strFieldName = "AccDate";
				break;
			case 70:
				strFieldName = "AccResult1";
				break;
			case 71:
				strFieldName = "AccResult2";
				break;
			case 72:
				strFieldName = "MasculineFlag";
				break;
			case 73:
				strFieldName = "EditReason";
				break;
			case 74:
				strFieldName = "BackNo";
				break;
			case 75:
				strFieldName = "HospitalCode";
				break;
			case 76:
				strFieldName = "HospitalName";
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
		if( strFieldName.equals("EditNo") ) {
			return Schema.TYPE_STRING;
		}
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
		if( strFieldName.equals("EditReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
