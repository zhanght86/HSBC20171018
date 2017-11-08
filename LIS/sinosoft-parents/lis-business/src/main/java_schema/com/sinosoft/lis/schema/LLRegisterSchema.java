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
import com.sinosoft.lis.db.LLRegisterDB;

/*
 * <p>ClassName: LLRegisterSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LLRegisterSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLRegisterSchema.class);
	// @Field
	/** 个人立案号(个人赔案号) */
	private String RgtNo;
	/** 案件类型 */
	private String RgtState;
	/** 申请类型 */
	private String RgtClass;
	/** 号码类型 */
	private String RgtObj;
	/** 其他号码 */
	private String RgtObjNo;
	/** 受理方式 */
	private String RgtType;
	/** 代理人代码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 申请人身份 */
	private String ApplyerType;
	/** 申请人证件类型 */
	private String IDType;
	/** 申请人证件号码 */
	private String IDNo;
	/** 立案人/申请人姓名 */
	private String RgtantName;
	/** 立案人/申请人性别 */
	private String RgtantSex;
	/** 立案人/申请人与被保人关系 */
	private String Relation;
	/** 立案人/申请人地址 */
	private String RgtantAddress;
	/** 立案人/申请人电话 */
	private String RgtantPhone;
	/** 立案人/申请人手机 */
	private String RgtantMobile;
	/** 立案人/申请人电邮 */
	private String Email;
	/** 立案人/申请人邮政编码 */
	private String PostCode;
	/** 客户号 */
	private String CustomerNo;
	/** 单位名称 */
	private String GrpName;
	/** 立案日期 */
	private Date RgtDate;
	/** 出险地点 */
	private String AccidentSite;
	/** 出险原因 */
	private String AccidentReason;
	/** 出险过程和结果 */
	private String AccidentCourse;
	/** 出险开始日期 */
	private Date AccStartDate;
	/** 出险结束日期 */
	private Date AccidentDate;
	/** 立案撤销原因 */
	private String RgtReason;
	/** 申请人数 */
	private int AppPeoples;
	/** 预估申请金额 */
	private double AppAmnt;
	/** 赔付金领取方式 */
	private String GetMode;
	/** 赔付金领取间隔 */
	private int GetIntv;
	/** 保险金领取方式 */
	private String CaseGetMode;
	/** 回执发送方式 */
	private String ReturnMode;
	/** 备注 */
	private String Remark;
	/** 审核人 */
	private String Handler;
	/** 统一给付标记 */
	private String TogetherFlag;
	/** 报案标志 */
	private String RptFlag;
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
	/** 赔案状态 */
	private String ClmState;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 经办人 */
	private String Handler1;
	/** 经办人联系电话 */
	private String Handler1Phone;
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
	/** 立案结论 */
	private String RgtConclusion;
	/** 不立案原因 */
	private String NoRgtReason;
	/** 受托人类型 */
	private String AssigneeType;
	/** 受托人代码 */
	private String AssigneeCode;
	/** 受托人姓名 */
	private String AssigneeName;
	/** 受托人性别 */
	private String AssigneeSex;
	/** 受托人电话 */
	private String AssigneePhone;
	/** 受托人地址 */
	private String AssigneeAddr;
	/** 受托人邮编 */
	private String AssigneeZip;
	/** 预估金额 */
	private double BeAdjSum;
	/** 账单录入标记 */
	private String FeeInputFlag;
	/** 收件人 */
	private String Recipients;
	/** 收件人姓名 */
	private String ReciName;
	/** 收件人地址 */
	private String ReciAddress;
	/** 收件人细节 */
	private String ReciDetails;
	/** 收件人关系 */
	private String ReciRela;
	/** 收件人电话 */
	private String ReciPhone;
	/** 收件人手机 */
	private String ReciMobile;
	/** 收件人邮编 */
	private String ReciZip;
	/** 收件人性别 */
	private String ReciSex;
	/** 收件人电邮 */
	private String ReciEmail;
	/** 投保总人数 */
	private int Peoples2;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 保单险种号 */
	private String RiskCode;
	/** 集体客户号码 */
	private String AppntNo;
	/** 团体预估金额 */
	private double GrpStandpay;
	/** 延迟立案备注 */
	private String DeferRgtRemark;
	/** 延迟立案原因 */
	private String DeferRgtReason;
	/** 案件给付类型 */
	private String CasePayType;
	/** 交接日期 */
	private Date AcceptedDate;
	/** 客户申请日期 */
	private Date ApplyDate;
	/** 立案通过日期 */
	private Date RgtConfDate;
	/** 立案来源 */
	private String RgtSources;
	/** 团体立案号 */
	private String GrpRgtNo;
	/** 出生日期 */
	private Date Birthday;
	/** 员工号 */
	private String EmployeNo;
	/** 发票张数 */
	private int BillCount;
	/** 回传发票张数 */
	private int BackBillCount;
	/** 影像件数 */
	private int ScanCount;
	/** 是否加急 */
	private String IsUrgent;
	/** 是否开据分割单 */
	private String IsOpenBillFlag;
	/** 是否退还发票 */
	private String IsBackBill;
	/** 受理标识 */
	private String AcceptFlag;
	/** 黑名单标志 */
	private String BlackFlag;
	/** 关联报案号 */
	private String RptNo;
	/** 抽检标记 */
	private String SpotCheckFlag;
	/** 特殊案件标识 */
	private String SpecCaseFlag;
	/** 历史赔案号 */
	private String OldClmNo;
	/** 权限限别 */
	private String ClaimLevel;
	/** 打印状态 */
	private String PrintState;
	/** 打印次数 */
	private int PrintCount;
	/** 打印日期 */
	private Date PrintDate;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** Column_110 */
	private String PreAuthNo;

	public static final int FIELDNUM = 110;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLRegisterSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RgtNo";

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
		LLRegisterSchema cloned = (LLRegisterSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("个人立案号(个人赔案号)RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
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
		if(aRgtState!=null && aRgtState.length()>6)
			throw new IllegalArgumentException("案件类型RgtState值"+aRgtState+"的长度"+aRgtState.length()+"大于最大值6");
		RgtState = aRgtState;
	}
	/**
	* 1 个人<p>
	* 2 团体<p>
	* 3 家庭单
	*/
	public String getRgtClass()
	{
		return RgtClass;
	}
	public void setRgtClass(String aRgtClass)
	{
		if(aRgtClass!=null && aRgtClass.length()>6)
			throw new IllegalArgumentException("申请类型RgtClass值"+aRgtClass+"的长度"+aRgtClass.length()+"大于最大值6");
		RgtClass = aRgtClass;
	}
	/**
	* 1个险报案号<p>
	* 2团险报案号
	*/
	public String getRgtObj()
	{
		return RgtObj;
	}
	public void setRgtObj(String aRgtObj)
	{
		if(aRgtObj!=null && aRgtObj.length()>6)
			throw new IllegalArgumentException("号码类型RgtObj值"+aRgtObj+"的长度"+aRgtObj.length()+"大于最大值6");
		RgtObj = aRgtObj;
	}
	public String getRgtObjNo()
	{
		return RgtObjNo;
	}
	public void setRgtObjNo(String aRgtObjNo)
	{
		if(aRgtObjNo!=null && aRgtObjNo.length()>20)
			throw new IllegalArgumentException("其他号码RgtObjNo值"+aRgtObjNo+"的长度"+aRgtObjNo.length()+"大于最大值20");
		RgtObjNo = aRgtObjNo;
	}
	/**
	* 【不用】<p>
	* 办理理赔的方式
	*/
	public String getRgtType()
	{
		return RgtType;
	}
	public void setRgtType(String aRgtType)
	{
		if(aRgtType!=null && aRgtType.length()>6)
			throw new IllegalArgumentException("受理方式RgtType值"+aRgtType+"的长度"+aRgtType.length()+"大于最大值6");
		RgtType = aRgtType;
	}
	/**
	* 【不用】
	*/
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
	/**
	* 【不用】
	*/
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
	/**
	* 【不用】<p>
	* 1 -- 被保险人、<p>
	* 2 -- 事故收益人/继承人，<p>
	* 3 -- 监护人、<p>
	* 4委托人<p>
	* 9 -- 其他
	*/
	public String getApplyerType()
	{
		return ApplyerType;
	}
	public void setApplyerType(String aApplyerType)
	{
		if(aApplyerType!=null && aApplyerType.length()>6)
			throw new IllegalArgumentException("申请人身份ApplyerType值"+aApplyerType+"的长度"+aApplyerType.length()+"大于最大值6");
		ApplyerType = aApplyerType;
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
		if(aIDType!=null && aIDType.length()>6)
			throw new IllegalArgumentException("申请人证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值6");
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
		if(aIDNo!=null && aIDNo.length()>30)
			throw new IllegalArgumentException("申请人证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值30");
		IDNo = aIDNo;
	}
	public String getRgtantName()
	{
		return RgtantName;
	}
	public void setRgtantName(String aRgtantName)
	{
		if(aRgtantName!=null && aRgtantName.length()>20)
			throw new IllegalArgumentException("立案人/申请人姓名RgtantName值"+aRgtantName+"的长度"+aRgtantName.length()+"大于最大值20");
		RgtantName = aRgtantName;
	}
	/**
	* 【不用】
	*/
	public String getRgtantSex()
	{
		return RgtantSex;
	}
	public void setRgtantSex(String aRgtantSex)
	{
		if(aRgtantSex!=null && aRgtantSex.length()>6)
			throw new IllegalArgumentException("立案人/申请人性别RgtantSex值"+aRgtantSex+"的长度"+aRgtantSex.length()+"大于最大值6");
		RgtantSex = aRgtantSex;
	}
	/**
	* GX01 	本人<p>
	* GX02 	保单服务人员<p>
	* GX03 	同事<p>
	* GX04 	朋友<p>
	* GX05 	亲戚<p>
	* GX06 	妻子<p>
	* GX07 	丈夫<p>
	* GX08 	儿女<p>
	* GX09 	父亲<p>
	* GX10 	母亲<p>
	* GX11 	其他
	*/
	public String getRelation()
	{
		return Relation;
	}
	public void setRelation(String aRelation)
	{
		if(aRelation!=null && aRelation.length()>6)
			throw new IllegalArgumentException("立案人/申请人与被保人关系Relation值"+aRelation+"的长度"+aRelation.length()+"大于最大值6");
		Relation = aRelation;
	}
	public String getRgtantAddress()
	{
		return RgtantAddress;
	}
	public void setRgtantAddress(String aRgtantAddress)
	{
		if(aRgtantAddress!=null && aRgtantAddress.length()>1000)
			throw new IllegalArgumentException("立案人/申请人地址RgtantAddress值"+aRgtantAddress+"的长度"+aRgtantAddress.length()+"大于最大值1000");
		RgtantAddress = aRgtantAddress;
	}
	public String getRgtantPhone()
	{
		return RgtantPhone;
	}
	public void setRgtantPhone(String aRgtantPhone)
	{
		if(aRgtantPhone!=null && aRgtantPhone.length()>18)
			throw new IllegalArgumentException("立案人/申请人电话RgtantPhone值"+aRgtantPhone+"的长度"+aRgtantPhone.length()+"大于最大值18");
		RgtantPhone = aRgtantPhone;
	}
	public String getRgtantMobile()
	{
		return RgtantMobile;
	}
	public void setRgtantMobile(String aRgtantMobile)
	{
		if(aRgtantMobile!=null && aRgtantMobile.length()>15)
			throw new IllegalArgumentException("立案人/申请人手机RgtantMobile值"+aRgtantMobile+"的长度"+aRgtantMobile.length()+"大于最大值15");
		RgtantMobile = aRgtantMobile;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		if(aEmail!=null && aEmail.length()>40)
			throw new IllegalArgumentException("立案人/申请人电邮Email值"+aEmail+"的长度"+aEmail.length()+"大于最大值40");
		Email = aEmail;
	}
	public String getPostCode()
	{
		return PostCode;
	}
	public void setPostCode(String aPostCode)
	{
		if(aPostCode!=null && aPostCode.length()>6)
			throw new IllegalArgumentException("立案人/申请人邮政编码PostCode值"+aPostCode+"的长度"+aPostCode.length()+"大于最大值6");
		PostCode = aPostCode;
	}
	/**
	* 【废弃不用】
	*/
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>100)
			throw new IllegalArgumentException("单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值100");
		GrpName = aGrpName;
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
	/**
	* 1意外、<p>
	* 2疾病
	*/
	public String getAccidentReason()
	{
		return AccidentReason;
	}
	public void setAccidentReason(String aAccidentReason)
	{
		if(aAccidentReason!=null && aAccidentReason.length()>2)
			throw new IllegalArgumentException("出险原因AccidentReason值"+aAccidentReason+"的长度"+aAccidentReason.length()+"大于最大值2");
		AccidentReason = aAccidentReason;
	}
	public String getAccidentCourse()
	{
		return AccidentCourse;
	}
	public void setAccidentCourse(String aAccidentCourse)
	{
		if(aAccidentCourse!=null && aAccidentCourse.length()>600)
			throw new IllegalArgumentException("出险过程和结果AccidentCourse值"+aAccidentCourse+"的长度"+aAccidentCourse.length()+"大于最大值600");
		AccidentCourse = aAccidentCourse;
	}
	/**
	* 如果是住院，则记录入院时间
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
	* 【不用】<p>
	* 如果使用，为文字描述项
	*/
	public String getRgtReason()
	{
		return RgtReason;
	}
	public void setRgtReason(String aRgtReason)
	{
		if(aRgtReason!=null && aRgtReason.length()>100)
			throw new IllegalArgumentException("立案撤销原因RgtReason值"+aRgtReason+"的长度"+aRgtReason.length()+"大于最大值100");
		RgtReason = aRgtReason;
	}
	public int getAppPeoples()
	{
		return AppPeoples;
	}
	public void setAppPeoples(int aAppPeoples)
	{
		AppPeoples = aAppPeoples;
	}
	public void setAppPeoples(String aAppPeoples)
	{
		if (aAppPeoples != null && !aAppPeoples.equals(""))
		{
			Integer tInteger = new Integer(aAppPeoples);
			int i = tInteger.intValue();
			AppPeoples = i;
		}
	}

	/**
	* 【不用】
	*/
	public double getAppAmnt()
	{
		return AppAmnt;
	}
	public void setAppAmnt(double aAppAmnt)
	{
		AppAmnt = aAppAmnt;
	}
	public void setAppAmnt(String aAppAmnt)
	{
		if (aAppAmnt != null && !aAppAmnt.equals(""))
		{
			Double tDouble = new Double(aAppAmnt);
			double d = tDouble.doubleValue();
			AppAmnt = d;
		}
	}

	/**
	* 1 一次统一给付<p>
	* 2 按年金方式领取<p>
	* 3 分期支付
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
	* 【不用】<p>
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
		if(aCaseGetMode!=null && aCaseGetMode.length()>6)
			throw new IllegalArgumentException("保险金领取方式CaseGetMode值"+aCaseGetMode+"的长度"+aCaseGetMode.length()+"大于最大值6");
		CaseGetMode = aCaseGetMode;
	}
	/**
	* 【不用】
	*/
	public String getReturnMode()
	{
		return ReturnMode;
	}
	public void setReturnMode(String aReturnMode)
	{
		if(aReturnMode!=null && aReturnMode.length()>6)
			throw new IllegalArgumentException("回执发送方式ReturnMode值"+aReturnMode+"的长度"+aReturnMode.length()+"大于最大值6");
		ReturnMode = aReturnMode;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>600)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值600");
		Remark = aRemark;
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
	* 【不用】<p>
	* １统一给付
	*/
	public String getTogetherFlag()
	{
		return TogetherFlag;
	}
	public void setTogetherFlag(String aTogetherFlag)
	{
		if(aTogetherFlag!=null && aTogetherFlag.length()>6)
			throw new IllegalArgumentException("统一给付标记TogetherFlag值"+aTogetherFlag+"的长度"+aTogetherFlag.length()+"大于最大值6");
		TogetherFlag = aTogetherFlag;
	}
	/**
	* 0已报案<p>
	* 1未报案
	*/
	public String getRptFlag()
	{
		return RptFlag;
	}
	public void setRptFlag(String aRptFlag)
	{
		if(aRptFlag!=null && aRptFlag.length()>6)
			throw new IllegalArgumentException("报案标志RptFlag值"+aRptFlag+"的长度"+aRptFlag.length()+"大于最大值6");
		RptFlag = aRptFlag;
	}
	/**
	* 【不用】<p>
	* 包括：全部核算、部分核算、没有核算
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
	* 【不用】<p>
	* 包括：全部核赔、部分核赔、没有核赔
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>6)
			throw new IllegalArgumentException("核赔标记UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值6");
		UWFlag = aUWFlag;
	}
	/**
	* 【不用】<p>
	* 包括：全部拒赔、部分拒赔、没有拒赔
	*/
	public String getDeclineFlag()
	{
		return DeclineFlag;
	}
	public void setDeclineFlag(String aDeclineFlag)
	{
		if(aDeclineFlag!=null && aDeclineFlag.length()>6)
			throw new IllegalArgumentException("拒赔标记DeclineFlag值"+aDeclineFlag+"的长度"+aDeclineFlag.length()+"大于最大值6");
		DeclineFlag = aDeclineFlag;
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
			throw new IllegalArgumentException("结案标记EndCaseFlag值"+aEndCaseFlag+"的长度"+aEndCaseFlag.length()+"大于最大值6");
		EndCaseFlag = aEndCaseFlag;
	}
	/**
	* 使用
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
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>30)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值30");
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
		if(aAccName!=null && aAccName.length()>200)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值200");
		AccName = aAccName;
	}
	/**
	* 【不用】
	*/
	public String getHandler1()
	{
		return Handler1;
	}
	public void setHandler1(String aHandler1)
	{
		if(aHandler1!=null && aHandler1.length()>10)
			throw new IllegalArgumentException("经办人Handler1值"+aHandler1+"的长度"+aHandler1.length()+"大于最大值10");
		Handler1 = aHandler1;
	}
	/**
	* 【不用】
	*/
	public String getHandler1Phone()
	{
		return Handler1Phone;
	}
	public void setHandler1Phone(String aHandler1Phone)
	{
		if(aHandler1Phone!=null && aHandler1Phone.length()>30)
			throw new IllegalArgumentException("经办人联系电话Handler1Phone值"+aHandler1Phone+"的长度"+aHandler1Phone.length()+"大于最大值30");
		Handler1Phone = aHandler1Phone;
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
	* 01  立案通过<p>
	* 02  不予立案<p>
	* 03  延迟立案
	*/
	public String getRgtConclusion()
	{
		return RgtConclusion;
	}
	public void setRgtConclusion(String aRgtConclusion)
	{
		if(aRgtConclusion!=null && aRgtConclusion.length()>6)
			throw new IllegalArgumentException("立案结论RgtConclusion值"+aRgtConclusion+"的长度"+aRgtConclusion.length()+"大于最大值6");
		RgtConclusion = aRgtConclusion;
	}
	public String getNoRgtReason()
	{
		return NoRgtReason;
	}
	public void setNoRgtReason(String aNoRgtReason)
	{
		if(aNoRgtReason!=null && aNoRgtReason.length()>6)
			throw new IllegalArgumentException("不立案原因NoRgtReason值"+aNoRgtReason+"的长度"+aNoRgtReason.length()+"大于最大值6");
		NoRgtReason = aNoRgtReason;
	}
	/**
	* 0业务员<p>
	* 1客户
	*/
	public String getAssigneeType()
	{
		return AssigneeType;
	}
	public void setAssigneeType(String aAssigneeType)
	{
		if(aAssigneeType!=null && aAssigneeType.length()>6)
			throw new IllegalArgumentException("受托人类型AssigneeType值"+aAssigneeType+"的长度"+aAssigneeType.length()+"大于最大值6");
		AssigneeType = aAssigneeType;
	}
	public String getAssigneeCode()
	{
		return AssigneeCode;
	}
	public void setAssigneeCode(String aAssigneeCode)
	{
		if(aAssigneeCode!=null && aAssigneeCode.length()>20)
			throw new IllegalArgumentException("受托人代码AssigneeCode值"+aAssigneeCode+"的长度"+aAssigneeCode.length()+"大于最大值20");
		AssigneeCode = aAssigneeCode;
	}
	public String getAssigneeName()
	{
		return AssigneeName;
	}
	public void setAssigneeName(String aAssigneeName)
	{
		if(aAssigneeName!=null && aAssigneeName.length()>40)
			throw new IllegalArgumentException("受托人姓名AssigneeName值"+aAssigneeName+"的长度"+aAssigneeName.length()+"大于最大值40");
		AssigneeName = aAssigneeName;
	}
	public String getAssigneeSex()
	{
		return AssigneeSex;
	}
	public void setAssigneeSex(String aAssigneeSex)
	{
		if(aAssigneeSex!=null && aAssigneeSex.length()>6)
			throw new IllegalArgumentException("受托人性别AssigneeSex值"+aAssigneeSex+"的长度"+aAssigneeSex.length()+"大于最大值6");
		AssigneeSex = aAssigneeSex;
	}
	public String getAssigneePhone()
	{
		return AssigneePhone;
	}
	public void setAssigneePhone(String aAssigneePhone)
	{
		if(aAssigneePhone!=null && aAssigneePhone.length()>100)
			throw new IllegalArgumentException("受托人电话AssigneePhone值"+aAssigneePhone+"的长度"+aAssigneePhone.length()+"大于最大值100");
		AssigneePhone = aAssigneePhone;
	}
	public String getAssigneeAddr()
	{
		return AssigneeAddr;
	}
	public void setAssigneeAddr(String aAssigneeAddr)
	{
		if(aAssigneeAddr!=null && aAssigneeAddr.length()>500)
			throw new IllegalArgumentException("受托人地址AssigneeAddr值"+aAssigneeAddr+"的长度"+aAssigneeAddr.length()+"大于最大值500");
		AssigneeAddr = aAssigneeAddr;
	}
	public String getAssigneeZip()
	{
		return AssigneeZip;
	}
	public void setAssigneeZip(String aAssigneeZip)
	{
		if(aAssigneeZip!=null && aAssigneeZip.length()>6)
			throw new IllegalArgumentException("受托人邮编AssigneeZip值"+aAssigneeZip+"的长度"+aAssigneeZip.length()+"大于最大值6");
		AssigneeZip = aAssigneeZip;
	}
	public double getBeAdjSum()
	{
		return BeAdjSum;
	}
	public void setBeAdjSum(double aBeAdjSum)
	{
		BeAdjSum = aBeAdjSum;
	}
	public void setBeAdjSum(String aBeAdjSum)
	{
		if (aBeAdjSum != null && !aBeAdjSum.equals(""))
		{
			Double tDouble = new Double(aBeAdjSum);
			double d = tDouble.doubleValue();
			BeAdjSum = d;
		}
	}

	/**
	* 0 不需要外包录入<p>
	* 1 正在外包录入<p>
	* 2 外包录入完成
	*/
	public String getFeeInputFlag()
	{
		return FeeInputFlag;
	}
	public void setFeeInputFlag(String aFeeInputFlag)
	{
		if(aFeeInputFlag!=null && aFeeInputFlag.length()>6)
			throw new IllegalArgumentException("账单录入标记FeeInputFlag值"+aFeeInputFlag+"的长度"+aFeeInputFlag.length()+"大于最大值6");
		FeeInputFlag = aFeeInputFlag;
	}
	public String getRecipients()
	{
		return Recipients;
	}
	public void setRecipients(String aRecipients)
	{
		if(aRecipients!=null && aRecipients.length()>40)
			throw new IllegalArgumentException("收件人Recipients值"+aRecipients+"的长度"+aRecipients.length()+"大于最大值40");
		Recipients = aRecipients;
	}
	public String getReciName()
	{
		return ReciName;
	}
	public void setReciName(String aReciName)
	{
		if(aReciName!=null && aReciName.length()>100)
			throw new IllegalArgumentException("收件人姓名ReciName值"+aReciName+"的长度"+aReciName.length()+"大于最大值100");
		ReciName = aReciName;
	}
	public String getReciAddress()
	{
		return ReciAddress;
	}
	public void setReciAddress(String aReciAddress)
	{
		if(aReciAddress!=null && aReciAddress.length()>400)
			throw new IllegalArgumentException("收件人地址ReciAddress值"+aReciAddress+"的长度"+aReciAddress.length()+"大于最大值400");
		ReciAddress = aReciAddress;
	}
	public String getReciDetails()
	{
		return ReciDetails;
	}
	public void setReciDetails(String aReciDetails)
	{
		if(aReciDetails!=null && aReciDetails.length()>1000)
			throw new IllegalArgumentException("收件人细节ReciDetails值"+aReciDetails+"的长度"+aReciDetails.length()+"大于最大值1000");
		ReciDetails = aReciDetails;
	}
	public String getReciRela()
	{
		return ReciRela;
	}
	public void setReciRela(String aReciRela)
	{
		if(aReciRela!=null && aReciRela.length()>6)
			throw new IllegalArgumentException("收件人关系ReciRela值"+aReciRela+"的长度"+aReciRela.length()+"大于最大值6");
		ReciRela = aReciRela;
	}
	public String getReciPhone()
	{
		return ReciPhone;
	}
	public void setReciPhone(String aReciPhone)
	{
		if(aReciPhone!=null && aReciPhone.length()>100)
			throw new IllegalArgumentException("收件人电话ReciPhone值"+aReciPhone+"的长度"+aReciPhone.length()+"大于最大值100");
		ReciPhone = aReciPhone;
	}
	public String getReciMobile()
	{
		return ReciMobile;
	}
	public void setReciMobile(String aReciMobile)
	{
		if(aReciMobile!=null && aReciMobile.length()>100)
			throw new IllegalArgumentException("收件人手机ReciMobile值"+aReciMobile+"的长度"+aReciMobile.length()+"大于最大值100");
		ReciMobile = aReciMobile;
	}
	public String getReciZip()
	{
		return ReciZip;
	}
	public void setReciZip(String aReciZip)
	{
		if(aReciZip!=null && aReciZip.length()>100)
			throw new IllegalArgumentException("收件人邮编ReciZip值"+aReciZip+"的长度"+aReciZip.length()+"大于最大值100");
		ReciZip = aReciZip;
	}
	public String getReciSex()
	{
		return ReciSex;
	}
	public void setReciSex(String aReciSex)
	{
		if(aReciSex!=null && aReciSex.length()>100)
			throw new IllegalArgumentException("收件人性别ReciSex值"+aReciSex+"的长度"+aReciSex.length()+"大于最大值100");
		ReciSex = aReciSex;
	}
	public String getReciEmail()
	{
		return ReciEmail;
	}
	public void setReciEmail(String aReciEmail)
	{
		if(aReciEmail!=null && aReciEmail.length()>100)
			throw new IllegalArgumentException("收件人电邮ReciEmail值"+aReciEmail+"的长度"+aReciEmail.length()+"大于最大值100");
		ReciEmail = aReciEmail;
	}
	public int getPeoples2()
	{
		return Peoples2;
	}
	public void setPeoples2(int aPeoples2)
	{
		Peoples2 = aPeoples2;
	}
	public void setPeoples2(String aPeoples2)
	{
		if (aPeoples2 != null && !aPeoples2.equals(""))
		{
			Integer tInteger = new Integer(aPeoples2);
			int i = tInteger.intValue();
			Peoples2 = i;
		}
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("保单险种号RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("集体客户号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public double getGrpStandpay()
	{
		return GrpStandpay;
	}
	public void setGrpStandpay(double aGrpStandpay)
	{
		GrpStandpay = aGrpStandpay;
	}
	public void setGrpStandpay(String aGrpStandpay)
	{
		if (aGrpStandpay != null && !aGrpStandpay.equals(""))
		{
			Double tDouble = new Double(aGrpStandpay);
			double d = tDouble.doubleValue();
			GrpStandpay = d;
		}
	}

	public String getDeferRgtRemark()
	{
		return DeferRgtRemark;
	}
	public void setDeferRgtRemark(String aDeferRgtRemark)
	{
		if(aDeferRgtRemark!=null && aDeferRgtRemark.length()>500)
			throw new IllegalArgumentException("延迟立案备注DeferRgtRemark值"+aDeferRgtRemark+"的长度"+aDeferRgtRemark.length()+"大于最大值500");
		DeferRgtRemark = aDeferRgtRemark;
	}
	public String getDeferRgtReason()
	{
		return DeferRgtReason;
	}
	public void setDeferRgtReason(String aDeferRgtReason)
	{
		if(aDeferRgtReason!=null && aDeferRgtReason.length()>500)
			throw new IllegalArgumentException("延迟立案原因DeferRgtReason值"+aDeferRgtReason+"的长度"+aDeferRgtReason.length()+"大于最大值500");
		DeferRgtReason = aDeferRgtReason;
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
	* 4	责任免除件
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
	public String getAcceptedDate()
	{
		if( AcceptedDate != null )
			return fDate.getString(AcceptedDate);
		else
			return null;
	}
	public void setAcceptedDate(Date aAcceptedDate)
	{
		AcceptedDate = aAcceptedDate;
	}
	public void setAcceptedDate(String aAcceptedDate)
	{
		if (aAcceptedDate != null && !aAcceptedDate.equals("") )
		{
			AcceptedDate = fDate.getDate( aAcceptedDate );
		}
		else
			AcceptedDate = null;
	}

	public String getApplyDate()
	{
		if( ApplyDate != null )
			return fDate.getString(ApplyDate);
		else
			return null;
	}
	public void setApplyDate(Date aApplyDate)
	{
		ApplyDate = aApplyDate;
	}
	public void setApplyDate(String aApplyDate)
	{
		if (aApplyDate != null && !aApplyDate.equals("") )
		{
			ApplyDate = fDate.getDate( aApplyDate );
		}
		else
			ApplyDate = null;
	}

	public String getRgtConfDate()
	{
		if( RgtConfDate != null )
			return fDate.getString(RgtConfDate);
		else
			return null;
	}
	public void setRgtConfDate(Date aRgtConfDate)
	{
		RgtConfDate = aRgtConfDate;
	}
	public void setRgtConfDate(String aRgtConfDate)
	{
		if (aRgtConfDate != null && !aRgtConfDate.equals("") )
		{
			RgtConfDate = fDate.getDate( aRgtConfDate );
		}
		else
			RgtConfDate = null;
	}

	/**
	* 1--有扫描立案<p>
	* 0--无扫描立案
	*/
	public String getRgtSources()
	{
		return RgtSources;
	}
	public void setRgtSources(String aRgtSources)
	{
		if(aRgtSources!=null && aRgtSources.length()>1)
			throw new IllegalArgumentException("立案来源RgtSources值"+aRgtSources+"的长度"+aRgtSources.length()+"大于最大值1");
		RgtSources = aRgtSources;
	}
	public String getGrpRgtNo()
	{
		return GrpRgtNo;
	}
	public void setGrpRgtNo(String aGrpRgtNo)
	{
		if(aGrpRgtNo!=null && aGrpRgtNo.length()>20)
			throw new IllegalArgumentException("团体立案号GrpRgtNo值"+aGrpRgtNo+"的长度"+aGrpRgtNo.length()+"大于最大值20");
		GrpRgtNo = aGrpRgtNo;
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

	public String getEmployeNo()
	{
		return EmployeNo;
	}
	public void setEmployeNo(String aEmployeNo)
	{
		if(aEmployeNo!=null && aEmployeNo.length()>30)
			throw new IllegalArgumentException("员工号EmployeNo值"+aEmployeNo+"的长度"+aEmployeNo.length()+"大于最大值30");
		EmployeNo = aEmployeNo;
	}
	public int getBillCount()
	{
		return BillCount;
	}
	public void setBillCount(int aBillCount)
	{
		BillCount = aBillCount;
	}
	public void setBillCount(String aBillCount)
	{
		if (aBillCount != null && !aBillCount.equals(""))
		{
			Integer tInteger = new Integer(aBillCount);
			int i = tInteger.intValue();
			BillCount = i;
		}
	}

	public int getBackBillCount()
	{
		return BackBillCount;
	}
	public void setBackBillCount(int aBackBillCount)
	{
		BackBillCount = aBackBillCount;
	}
	public void setBackBillCount(String aBackBillCount)
	{
		if (aBackBillCount != null && !aBackBillCount.equals(""))
		{
			Integer tInteger = new Integer(aBackBillCount);
			int i = tInteger.intValue();
			BackBillCount = i;
		}
	}

	public int getScanCount()
	{
		return ScanCount;
	}
	public void setScanCount(int aScanCount)
	{
		ScanCount = aScanCount;
	}
	public void setScanCount(String aScanCount)
	{
		if (aScanCount != null && !aScanCount.equals(""))
		{
			Integer tInteger = new Integer(aScanCount);
			int i = tInteger.intValue();
			ScanCount = i;
		}
	}

	public String getIsUrgent()
	{
		return IsUrgent;
	}
	public void setIsUrgent(String aIsUrgent)
	{
		if(aIsUrgent!=null && aIsUrgent.length()>1)
			throw new IllegalArgumentException("是否加急IsUrgent值"+aIsUrgent+"的长度"+aIsUrgent.length()+"大于最大值1");
		IsUrgent = aIsUrgent;
	}
	public String getIsOpenBillFlag()
	{
		return IsOpenBillFlag;
	}
	public void setIsOpenBillFlag(String aIsOpenBillFlag)
	{
		if(aIsOpenBillFlag!=null && aIsOpenBillFlag.length()>1)
			throw new IllegalArgumentException("是否开据分割单IsOpenBillFlag值"+aIsOpenBillFlag+"的长度"+aIsOpenBillFlag.length()+"大于最大值1");
		IsOpenBillFlag = aIsOpenBillFlag;
	}
	public String getIsBackBill()
	{
		return IsBackBill;
	}
	public void setIsBackBill(String aIsBackBill)
	{
		if(aIsBackBill!=null && aIsBackBill.length()>1)
			throw new IllegalArgumentException("是否退还发票IsBackBill值"+aIsBackBill+"的长度"+aIsBackBill.length()+"大于最大值1");
		IsBackBill = aIsBackBill;
	}
	/**
	* 用于区分客户类别，0-未受理，1-已受理
	*/
	public String getAcceptFlag()
	{
		return AcceptFlag;
	}
	public void setAcceptFlag(String aAcceptFlag)
	{
		if(aAcceptFlag!=null && aAcceptFlag.length()>6)
			throw new IllegalArgumentException("受理标识AcceptFlag值"+aAcceptFlag+"的长度"+aAcceptFlag.length()+"大于最大值6");
		AcceptFlag = aAcceptFlag;
	}
	public String getBlackFlag()
	{
		return BlackFlag;
	}
	public void setBlackFlag(String aBlackFlag)
	{
		if(aBlackFlag!=null && aBlackFlag.length()>6)
			throw new IllegalArgumentException("黑名单标志BlackFlag值"+aBlackFlag+"的长度"+aBlackFlag.length()+"大于最大值6");
		BlackFlag = aBlackFlag;
	}
	public String getRptNo()
	{
		return RptNo;
	}
	public void setRptNo(String aRptNo)
	{
		if(aRptNo!=null && aRptNo.length()>20)
			throw new IllegalArgumentException("关联报案号RptNo值"+aRptNo+"的长度"+aRptNo.length()+"大于最大值20");
		RptNo = aRptNo;
	}
	/**
	* 0-否，1-是
	*/
	public String getSpotCheckFlag()
	{
		return SpotCheckFlag;
	}
	public void setSpotCheckFlag(String aSpotCheckFlag)
	{
		if(aSpotCheckFlag!=null && aSpotCheckFlag.length()>6)
			throw new IllegalArgumentException("抽检标记SpotCheckFlag值"+aSpotCheckFlag+"的长度"+aSpotCheckFlag.length()+"大于最大值6");
		SpotCheckFlag = aSpotCheckFlag;
	}
	/**
	* 0-否，1-是
	*/
	public String getSpecCaseFlag()
	{
		return SpecCaseFlag;
	}
	public void setSpecCaseFlag(String aSpecCaseFlag)
	{
		if(aSpecCaseFlag!=null && aSpecCaseFlag.length()>1)
			throw new IllegalArgumentException("特殊案件标识SpecCaseFlag值"+aSpecCaseFlag+"的长度"+aSpecCaseFlag.length()+"大于最大值1");
		SpecCaseFlag = aSpecCaseFlag;
	}
	public String getOldClmNo()
	{
		return OldClmNo;
	}
	public void setOldClmNo(String aOldClmNo)
	{
		if(aOldClmNo!=null && aOldClmNo.length()>20)
			throw new IllegalArgumentException("历史赔案号OldClmNo值"+aOldClmNo+"的长度"+aOldClmNo.length()+"大于最大值20");
		OldClmNo = aOldClmNo;
	}
	public String getClaimLevel()
	{
		return ClaimLevel;
	}
	public void setClaimLevel(String aClaimLevel)
	{
		if(aClaimLevel!=null && aClaimLevel.length()>6)
			throw new IllegalArgumentException("权限限别ClaimLevel值"+aClaimLevel+"的长度"+aClaimLevel.length()+"大于最大值6");
		ClaimLevel = aClaimLevel;
	}
	/**
	* 0-未打印，1-已打印
	*/
	public String getPrintState()
	{
		return PrintState;
	}
	public void setPrintState(String aPrintState)
	{
		if(aPrintState!=null && aPrintState.length()>2)
			throw new IllegalArgumentException("打印状态PrintState值"+aPrintState+"的长度"+aPrintState.length()+"大于最大值2");
		PrintState = aPrintState;
	}
	public int getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(int aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		if (aPrintCount != null && !aPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aPrintCount);
			int i = tInteger.intValue();
			PrintCount = i;
		}
	}

	public String getPrintDate()
	{
		if( PrintDate != null )
			return fDate.getString(PrintDate);
		else
			return null;
	}
	public void setPrintDate(Date aPrintDate)
	{
		PrintDate = aPrintDate;
	}
	public void setPrintDate(String aPrintDate)
	{
		if (aPrintDate != null && !aPrintDate.equals("") )
		{
			PrintDate = fDate.getDate( aPrintDate );
		}
		else
			PrintDate = null;
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
	public String getPreAuthNo()
	{
		return PreAuthNo;
	}
	public void setPreAuthNo(String aPreAuthNo)
	{
		if(aPreAuthNo!=null && aPreAuthNo.length()>20)
			throw new IllegalArgumentException("Column_110PreAuthNo值"+aPreAuthNo+"的长度"+aPreAuthNo.length()+"大于最大值20");
		PreAuthNo = aPreAuthNo;
	}

	/**
	* 使用另外一个 LLRegisterSchema 对象给 Schema 赋值
	* @param: aLLRegisterSchema LLRegisterSchema
	**/
	public void setSchema(LLRegisterSchema aLLRegisterSchema)
	{
		this.RgtNo = aLLRegisterSchema.getRgtNo();
		this.RgtState = aLLRegisterSchema.getRgtState();
		this.RgtClass = aLLRegisterSchema.getRgtClass();
		this.RgtObj = aLLRegisterSchema.getRgtObj();
		this.RgtObjNo = aLLRegisterSchema.getRgtObjNo();
		this.RgtType = aLLRegisterSchema.getRgtType();
		this.AgentCode = aLLRegisterSchema.getAgentCode();
		this.AgentGroup = aLLRegisterSchema.getAgentGroup();
		this.ApplyerType = aLLRegisterSchema.getApplyerType();
		this.IDType = aLLRegisterSchema.getIDType();
		this.IDNo = aLLRegisterSchema.getIDNo();
		this.RgtantName = aLLRegisterSchema.getRgtantName();
		this.RgtantSex = aLLRegisterSchema.getRgtantSex();
		this.Relation = aLLRegisterSchema.getRelation();
		this.RgtantAddress = aLLRegisterSchema.getRgtantAddress();
		this.RgtantPhone = aLLRegisterSchema.getRgtantPhone();
		this.RgtantMobile = aLLRegisterSchema.getRgtantMobile();
		this.Email = aLLRegisterSchema.getEmail();
		this.PostCode = aLLRegisterSchema.getPostCode();
		this.CustomerNo = aLLRegisterSchema.getCustomerNo();
		this.GrpName = aLLRegisterSchema.getGrpName();
		this.RgtDate = fDate.getDate( aLLRegisterSchema.getRgtDate());
		this.AccidentSite = aLLRegisterSchema.getAccidentSite();
		this.AccidentReason = aLLRegisterSchema.getAccidentReason();
		this.AccidentCourse = aLLRegisterSchema.getAccidentCourse();
		this.AccStartDate = fDate.getDate( aLLRegisterSchema.getAccStartDate());
		this.AccidentDate = fDate.getDate( aLLRegisterSchema.getAccidentDate());
		this.RgtReason = aLLRegisterSchema.getRgtReason();
		this.AppPeoples = aLLRegisterSchema.getAppPeoples();
		this.AppAmnt = aLLRegisterSchema.getAppAmnt();
		this.GetMode = aLLRegisterSchema.getGetMode();
		this.GetIntv = aLLRegisterSchema.getGetIntv();
		this.CaseGetMode = aLLRegisterSchema.getCaseGetMode();
		this.ReturnMode = aLLRegisterSchema.getReturnMode();
		this.Remark = aLLRegisterSchema.getRemark();
		this.Handler = aLLRegisterSchema.getHandler();
		this.TogetherFlag = aLLRegisterSchema.getTogetherFlag();
		this.RptFlag = aLLRegisterSchema.getRptFlag();
		this.CalFlag = aLLRegisterSchema.getCalFlag();
		this.UWFlag = aLLRegisterSchema.getUWFlag();
		this.DeclineFlag = aLLRegisterSchema.getDeclineFlag();
		this.EndCaseFlag = aLLRegisterSchema.getEndCaseFlag();
		this.EndCaseDate = fDate.getDate( aLLRegisterSchema.getEndCaseDate());
		this.MngCom = aLLRegisterSchema.getMngCom();
		this.ClmState = aLLRegisterSchema.getClmState();
		this.BankCode = aLLRegisterSchema.getBankCode();
		this.BankAccNo = aLLRegisterSchema.getBankAccNo();
		this.AccName = aLLRegisterSchema.getAccName();
		this.Handler1 = aLLRegisterSchema.getHandler1();
		this.Handler1Phone = aLLRegisterSchema.getHandler1Phone();
		this.Operator = aLLRegisterSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLRegisterSchema.getMakeDate());
		this.MakeTime = aLLRegisterSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLRegisterSchema.getModifyDate());
		this.ModifyTime = aLLRegisterSchema.getModifyTime();
		this.RgtConclusion = aLLRegisterSchema.getRgtConclusion();
		this.NoRgtReason = aLLRegisterSchema.getNoRgtReason();
		this.AssigneeType = aLLRegisterSchema.getAssigneeType();
		this.AssigneeCode = aLLRegisterSchema.getAssigneeCode();
		this.AssigneeName = aLLRegisterSchema.getAssigneeName();
		this.AssigneeSex = aLLRegisterSchema.getAssigneeSex();
		this.AssigneePhone = aLLRegisterSchema.getAssigneePhone();
		this.AssigneeAddr = aLLRegisterSchema.getAssigneeAddr();
		this.AssigneeZip = aLLRegisterSchema.getAssigneeZip();
		this.BeAdjSum = aLLRegisterSchema.getBeAdjSum();
		this.FeeInputFlag = aLLRegisterSchema.getFeeInputFlag();
		this.Recipients = aLLRegisterSchema.getRecipients();
		this.ReciName = aLLRegisterSchema.getReciName();
		this.ReciAddress = aLLRegisterSchema.getReciAddress();
		this.ReciDetails = aLLRegisterSchema.getReciDetails();
		this.ReciRela = aLLRegisterSchema.getReciRela();
		this.ReciPhone = aLLRegisterSchema.getReciPhone();
		this.ReciMobile = aLLRegisterSchema.getReciMobile();
		this.ReciZip = aLLRegisterSchema.getReciZip();
		this.ReciSex = aLLRegisterSchema.getReciSex();
		this.ReciEmail = aLLRegisterSchema.getReciEmail();
		this.Peoples2 = aLLRegisterSchema.getPeoples2();
		this.GrpContNo = aLLRegisterSchema.getGrpContNo();
		this.RiskCode = aLLRegisterSchema.getRiskCode();
		this.AppntNo = aLLRegisterSchema.getAppntNo();
		this.GrpStandpay = aLLRegisterSchema.getGrpStandpay();
		this.DeferRgtRemark = aLLRegisterSchema.getDeferRgtRemark();
		this.DeferRgtReason = aLLRegisterSchema.getDeferRgtReason();
		this.CasePayType = aLLRegisterSchema.getCasePayType();
		this.AcceptedDate = fDate.getDate( aLLRegisterSchema.getAcceptedDate());
		this.ApplyDate = fDate.getDate( aLLRegisterSchema.getApplyDate());
		this.RgtConfDate = fDate.getDate( aLLRegisterSchema.getRgtConfDate());
		this.RgtSources = aLLRegisterSchema.getRgtSources();
		this.GrpRgtNo = aLLRegisterSchema.getGrpRgtNo();
		this.Birthday = fDate.getDate( aLLRegisterSchema.getBirthday());
		this.EmployeNo = aLLRegisterSchema.getEmployeNo();
		this.BillCount = aLLRegisterSchema.getBillCount();
		this.BackBillCount = aLLRegisterSchema.getBackBillCount();
		this.ScanCount = aLLRegisterSchema.getScanCount();
		this.IsUrgent = aLLRegisterSchema.getIsUrgent();
		this.IsOpenBillFlag = aLLRegisterSchema.getIsOpenBillFlag();
		this.IsBackBill = aLLRegisterSchema.getIsBackBill();
		this.AcceptFlag = aLLRegisterSchema.getAcceptFlag();
		this.BlackFlag = aLLRegisterSchema.getBlackFlag();
		this.RptNo = aLLRegisterSchema.getRptNo();
		this.SpotCheckFlag = aLLRegisterSchema.getSpotCheckFlag();
		this.SpecCaseFlag = aLLRegisterSchema.getSpecCaseFlag();
		this.OldClmNo = aLLRegisterSchema.getOldClmNo();
		this.ClaimLevel = aLLRegisterSchema.getClaimLevel();
		this.PrintState = aLLRegisterSchema.getPrintState();
		this.PrintCount = aLLRegisterSchema.getPrintCount();
		this.PrintDate = fDate.getDate( aLLRegisterSchema.getPrintDate());
		this.ComCode = aLLRegisterSchema.getComCode();
		this.ModifyOperator = aLLRegisterSchema.getModifyOperator();
		this.PreAuthNo = aLLRegisterSchema.getPreAuthNo();
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
			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("RgtState") == null )
				this.RgtState = null;
			else
				this.RgtState = rs.getString("RgtState").trim();

			if( rs.getString("RgtClass") == null )
				this.RgtClass = null;
			else
				this.RgtClass = rs.getString("RgtClass").trim();

			if( rs.getString("RgtObj") == null )
				this.RgtObj = null;
			else
				this.RgtObj = rs.getString("RgtObj").trim();

			if( rs.getString("RgtObjNo") == null )
				this.RgtObjNo = null;
			else
				this.RgtObjNo = rs.getString("RgtObjNo").trim();

			if( rs.getString("RgtType") == null )
				this.RgtType = null;
			else
				this.RgtType = rs.getString("RgtType").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("ApplyerType") == null )
				this.ApplyerType = null;
			else
				this.ApplyerType = rs.getString("ApplyerType").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("RgtantName") == null )
				this.RgtantName = null;
			else
				this.RgtantName = rs.getString("RgtantName").trim();

			if( rs.getString("RgtantSex") == null )
				this.RgtantSex = null;
			else
				this.RgtantSex = rs.getString("RgtantSex").trim();

			if( rs.getString("Relation") == null )
				this.Relation = null;
			else
				this.Relation = rs.getString("Relation").trim();

			if( rs.getString("RgtantAddress") == null )
				this.RgtantAddress = null;
			else
				this.RgtantAddress = rs.getString("RgtantAddress").trim();

			if( rs.getString("RgtantPhone") == null )
				this.RgtantPhone = null;
			else
				this.RgtantPhone = rs.getString("RgtantPhone").trim();

			if( rs.getString("RgtantMobile") == null )
				this.RgtantMobile = null;
			else
				this.RgtantMobile = rs.getString("RgtantMobile").trim();

			if( rs.getString("Email") == null )
				this.Email = null;
			else
				this.Email = rs.getString("Email").trim();

			if( rs.getString("PostCode") == null )
				this.PostCode = null;
			else
				this.PostCode = rs.getString("PostCode").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			this.RgtDate = rs.getDate("RgtDate");
			if( rs.getString("AccidentSite") == null )
				this.AccidentSite = null;
			else
				this.AccidentSite = rs.getString("AccidentSite").trim();

			if( rs.getString("AccidentReason") == null )
				this.AccidentReason = null;
			else
				this.AccidentReason = rs.getString("AccidentReason").trim();

			if( rs.getString("AccidentCourse") == null )
				this.AccidentCourse = null;
			else
				this.AccidentCourse = rs.getString("AccidentCourse").trim();

			this.AccStartDate = rs.getDate("AccStartDate");
			this.AccidentDate = rs.getDate("AccidentDate");
			if( rs.getString("RgtReason") == null )
				this.RgtReason = null;
			else
				this.RgtReason = rs.getString("RgtReason").trim();

			this.AppPeoples = rs.getInt("AppPeoples");
			this.AppAmnt = rs.getDouble("AppAmnt");
			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			this.GetIntv = rs.getInt("GetIntv");
			if( rs.getString("CaseGetMode") == null )
				this.CaseGetMode = null;
			else
				this.CaseGetMode = rs.getString("CaseGetMode").trim();

			if( rs.getString("ReturnMode") == null )
				this.ReturnMode = null;
			else
				this.ReturnMode = rs.getString("ReturnMode").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			if( rs.getString("TogetherFlag") == null )
				this.TogetherFlag = null;
			else
				this.TogetherFlag = rs.getString("TogetherFlag").trim();

			if( rs.getString("RptFlag") == null )
				this.RptFlag = null;
			else
				this.RptFlag = rs.getString("RptFlag").trim();

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

			if( rs.getString("ClmState") == null )
				this.ClmState = null;
			else
				this.ClmState = rs.getString("ClmState").trim();

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

			if( rs.getString("Handler1") == null )
				this.Handler1 = null;
			else
				this.Handler1 = rs.getString("Handler1").trim();

			if( rs.getString("Handler1Phone") == null )
				this.Handler1Phone = null;
			else
				this.Handler1Phone = rs.getString("Handler1Phone").trim();

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

			if( rs.getString("RgtConclusion") == null )
				this.RgtConclusion = null;
			else
				this.RgtConclusion = rs.getString("RgtConclusion").trim();

			if( rs.getString("NoRgtReason") == null )
				this.NoRgtReason = null;
			else
				this.NoRgtReason = rs.getString("NoRgtReason").trim();

			if( rs.getString("AssigneeType") == null )
				this.AssigneeType = null;
			else
				this.AssigneeType = rs.getString("AssigneeType").trim();

			if( rs.getString("AssigneeCode") == null )
				this.AssigneeCode = null;
			else
				this.AssigneeCode = rs.getString("AssigneeCode").trim();

			if( rs.getString("AssigneeName") == null )
				this.AssigneeName = null;
			else
				this.AssigneeName = rs.getString("AssigneeName").trim();

			if( rs.getString("AssigneeSex") == null )
				this.AssigneeSex = null;
			else
				this.AssigneeSex = rs.getString("AssigneeSex").trim();

			if( rs.getString("AssigneePhone") == null )
				this.AssigneePhone = null;
			else
				this.AssigneePhone = rs.getString("AssigneePhone").trim();

			if( rs.getString("AssigneeAddr") == null )
				this.AssigneeAddr = null;
			else
				this.AssigneeAddr = rs.getString("AssigneeAddr").trim();

			if( rs.getString("AssigneeZip") == null )
				this.AssigneeZip = null;
			else
				this.AssigneeZip = rs.getString("AssigneeZip").trim();

			this.BeAdjSum = rs.getDouble("BeAdjSum");
			if( rs.getString("FeeInputFlag") == null )
				this.FeeInputFlag = null;
			else
				this.FeeInputFlag = rs.getString("FeeInputFlag").trim();

			if( rs.getString("Recipients") == null )
				this.Recipients = null;
			else
				this.Recipients = rs.getString("Recipients").trim();

			if( rs.getString("ReciName") == null )
				this.ReciName = null;
			else
				this.ReciName = rs.getString("ReciName").trim();

			if( rs.getString("ReciAddress") == null )
				this.ReciAddress = null;
			else
				this.ReciAddress = rs.getString("ReciAddress").trim();

			if( rs.getString("ReciDetails") == null )
				this.ReciDetails = null;
			else
				this.ReciDetails = rs.getString("ReciDetails").trim();

			if( rs.getString("ReciRela") == null )
				this.ReciRela = null;
			else
				this.ReciRela = rs.getString("ReciRela").trim();

			if( rs.getString("ReciPhone") == null )
				this.ReciPhone = null;
			else
				this.ReciPhone = rs.getString("ReciPhone").trim();

			if( rs.getString("ReciMobile") == null )
				this.ReciMobile = null;
			else
				this.ReciMobile = rs.getString("ReciMobile").trim();

			if( rs.getString("ReciZip") == null )
				this.ReciZip = null;
			else
				this.ReciZip = rs.getString("ReciZip").trim();

			if( rs.getString("ReciSex") == null )
				this.ReciSex = null;
			else
				this.ReciSex = rs.getString("ReciSex").trim();

			if( rs.getString("ReciEmail") == null )
				this.ReciEmail = null;
			else
				this.ReciEmail = rs.getString("ReciEmail").trim();

			this.Peoples2 = rs.getInt("Peoples2");
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			this.GrpStandpay = rs.getDouble("GrpStandpay");
			if( rs.getString("DeferRgtRemark") == null )
				this.DeferRgtRemark = null;
			else
				this.DeferRgtRemark = rs.getString("DeferRgtRemark").trim();

			if( rs.getString("DeferRgtReason") == null )
				this.DeferRgtReason = null;
			else
				this.DeferRgtReason = rs.getString("DeferRgtReason").trim();

			if( rs.getString("CasePayType") == null )
				this.CasePayType = null;
			else
				this.CasePayType = rs.getString("CasePayType").trim();

			this.AcceptedDate = rs.getDate("AcceptedDate");
			this.ApplyDate = rs.getDate("ApplyDate");
			this.RgtConfDate = rs.getDate("RgtConfDate");
			if( rs.getString("RgtSources") == null )
				this.RgtSources = null;
			else
				this.RgtSources = rs.getString("RgtSources").trim();

			if( rs.getString("GrpRgtNo") == null )
				this.GrpRgtNo = null;
			else
				this.GrpRgtNo = rs.getString("GrpRgtNo").trim();

			this.Birthday = rs.getDate("Birthday");
			if( rs.getString("EmployeNo") == null )
				this.EmployeNo = null;
			else
				this.EmployeNo = rs.getString("EmployeNo").trim();

			this.BillCount = rs.getInt("BillCount");
			this.BackBillCount = rs.getInt("BackBillCount");
			this.ScanCount = rs.getInt("ScanCount");
			if( rs.getString("IsUrgent") == null )
				this.IsUrgent = null;
			else
				this.IsUrgent = rs.getString("IsUrgent").trim();

			if( rs.getString("IsOpenBillFlag") == null )
				this.IsOpenBillFlag = null;
			else
				this.IsOpenBillFlag = rs.getString("IsOpenBillFlag").trim();

			if( rs.getString("IsBackBill") == null )
				this.IsBackBill = null;
			else
				this.IsBackBill = rs.getString("IsBackBill").trim();

			if( rs.getString("AcceptFlag") == null )
				this.AcceptFlag = null;
			else
				this.AcceptFlag = rs.getString("AcceptFlag").trim();

			if( rs.getString("BlackFlag") == null )
				this.BlackFlag = null;
			else
				this.BlackFlag = rs.getString("BlackFlag").trim();

			if( rs.getString("RptNo") == null )
				this.RptNo = null;
			else
				this.RptNo = rs.getString("RptNo").trim();

			if( rs.getString("SpotCheckFlag") == null )
				this.SpotCheckFlag = null;
			else
				this.SpotCheckFlag = rs.getString("SpotCheckFlag").trim();

			if( rs.getString("SpecCaseFlag") == null )
				this.SpecCaseFlag = null;
			else
				this.SpecCaseFlag = rs.getString("SpecCaseFlag").trim();

			if( rs.getString("OldClmNo") == null )
				this.OldClmNo = null;
			else
				this.OldClmNo = rs.getString("OldClmNo").trim();

			if( rs.getString("ClaimLevel") == null )
				this.ClaimLevel = null;
			else
				this.ClaimLevel = rs.getString("ClaimLevel").trim();

			if( rs.getString("PrintState") == null )
				this.PrintState = null;
			else
				this.PrintState = rs.getString("PrintState").trim();

			this.PrintCount = rs.getInt("PrintCount");
			this.PrintDate = rs.getDate("PrintDate");
			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			if( rs.getString("PreAuthNo") == null )
				this.PreAuthNo = null;
			else
				this.PreAuthNo = rs.getString("PreAuthNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLRegister表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRegisterSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLRegisterSchema getSchema()
	{
		LLRegisterSchema aLLRegisterSchema = new LLRegisterSchema();
		aLLRegisterSchema.setSchema(this);
		return aLLRegisterSchema;
	}

	public LLRegisterDB getDB()
	{
		LLRegisterDB aDBOper = new LLRegisterDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLRegister描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtObjNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtantName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtantSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Relation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtantAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtantPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtantMobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RgtDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentSite)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentCourse)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccidentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TogetherFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndCaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndCaseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler1Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoRgtReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneePhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeAddr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssigneeZip)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BeAdjSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeInputFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Recipients)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciDetails)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciRela)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciMobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciZip)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReciEmail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GrpStandpay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeferRgtRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeferRgtReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AcceptedDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RgtConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtSources)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpRgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EmployeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BillCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BackBillCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ScanCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsUrgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsOpenBillFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsBackBill)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcceptFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlackFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotCheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecCaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PrintDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreAuthNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLRegister>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RgtClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RgtObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RgtObjNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RgtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ApplyerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RgtantName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RgtantSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RgtantAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RgtantPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			RgtantMobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PostCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			RgtDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			AccidentSite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AccidentReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AccidentCourse = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AccStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			RgtReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AppPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).intValue();
			AppAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).intValue();
			CaseGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ReturnMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			TogetherFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			RptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			DeclineFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			EndCaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ClmState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			Handler1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			Handler1Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			RgtConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			NoRgtReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			AssigneeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			AssigneeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			AssigneeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			AssigneeSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			AssigneePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			AssigneeAddr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			AssigneeZip = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			BeAdjSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			FeeInputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			Recipients = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			ReciName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			ReciAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			ReciDetails = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			ReciRela = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			ReciPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			ReciMobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			ReciZip = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			ReciSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			ReciEmail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			Peoples2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).intValue();
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			GrpStandpay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).doubleValue();
			DeferRgtRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			DeferRgtReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			CasePayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			AcceptedDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85,SysConst.PACKAGESPILTER));
			ApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86,SysConst.PACKAGESPILTER));
			RgtConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87,SysConst.PACKAGESPILTER));
			RgtSources = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			GrpRgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90,SysConst.PACKAGESPILTER));
			EmployeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			BillCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,92,SysConst.PACKAGESPILTER))).intValue();
			BackBillCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,93,SysConst.PACKAGESPILTER))).intValue();
			ScanCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,94,SysConst.PACKAGESPILTER))).intValue();
			IsUrgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			IsOpenBillFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			IsBackBill = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			AcceptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			BlackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			RptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			SpotCheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			SpecCaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			OldClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			ClaimLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			PrintState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,106,SysConst.PACKAGESPILTER))).intValue();
			PrintDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107,SysConst.PACKAGESPILTER));
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
			PreAuthNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRegisterSchema";
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("RgtState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtState));
		}
		if (FCode.equalsIgnoreCase("RgtClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtClass));
		}
		if (FCode.equalsIgnoreCase("RgtObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtObj));
		}
		if (FCode.equalsIgnoreCase("RgtObjNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtObjNo));
		}
		if (FCode.equalsIgnoreCase("RgtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtType));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("ApplyerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyerType));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("RgtantName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtantName));
		}
		if (FCode.equalsIgnoreCase("RgtantSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtantSex));
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relation));
		}
		if (FCode.equalsIgnoreCase("RgtantAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtantAddress));
		}
		if (FCode.equalsIgnoreCase("RgtantPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtantPhone));
		}
		if (FCode.equalsIgnoreCase("RgtantMobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtantMobile));
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Email));
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostCode));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("RgtDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
		}
		if (FCode.equalsIgnoreCase("AccidentSite"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentSite));
		}
		if (FCode.equalsIgnoreCase("AccidentReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentReason));
		}
		if (FCode.equalsIgnoreCase("AccidentCourse"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentCourse));
		}
		if (FCode.equalsIgnoreCase("AccStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccStartDate()));
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
		}
		if (FCode.equalsIgnoreCase("RgtReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtReason));
		}
		if (FCode.equalsIgnoreCase("AppPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppPeoples));
		}
		if (FCode.equalsIgnoreCase("AppAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppAmnt));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseGetMode));
		}
		if (FCode.equalsIgnoreCase("ReturnMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnMode));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("TogetherFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TogetherFlag));
		}
		if (FCode.equalsIgnoreCase("RptFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptFlag));
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
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmState));
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
		if (FCode.equalsIgnoreCase("Handler1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler1));
		}
		if (FCode.equalsIgnoreCase("Handler1Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler1Phone));
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
		if (FCode.equalsIgnoreCase("RgtConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtConclusion));
		}
		if (FCode.equalsIgnoreCase("NoRgtReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoRgtReason));
		}
		if (FCode.equalsIgnoreCase("AssigneeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeType));
		}
		if (FCode.equalsIgnoreCase("AssigneeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeCode));
		}
		if (FCode.equalsIgnoreCase("AssigneeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeName));
		}
		if (FCode.equalsIgnoreCase("AssigneeSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeSex));
		}
		if (FCode.equalsIgnoreCase("AssigneePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneePhone));
		}
		if (FCode.equalsIgnoreCase("AssigneeAddr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeAddr));
		}
		if (FCode.equalsIgnoreCase("AssigneeZip"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssigneeZip));
		}
		if (FCode.equalsIgnoreCase("BeAdjSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeAdjSum));
		}
		if (FCode.equalsIgnoreCase("FeeInputFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeInputFlag));
		}
		if (FCode.equalsIgnoreCase("Recipients"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Recipients));
		}
		if (FCode.equalsIgnoreCase("ReciName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciName));
		}
		if (FCode.equalsIgnoreCase("ReciAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciAddress));
		}
		if (FCode.equalsIgnoreCase("ReciDetails"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciDetails));
		}
		if (FCode.equalsIgnoreCase("ReciRela"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciRela));
		}
		if (FCode.equalsIgnoreCase("ReciPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciPhone));
		}
		if (FCode.equalsIgnoreCase("ReciMobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciMobile));
		}
		if (FCode.equalsIgnoreCase("ReciZip"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciZip));
		}
		if (FCode.equalsIgnoreCase("ReciSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciSex));
		}
		if (FCode.equalsIgnoreCase("ReciEmail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReciEmail));
		}
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples2));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("GrpStandpay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpStandpay));
		}
		if (FCode.equalsIgnoreCase("DeferRgtRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeferRgtRemark));
		}
		if (FCode.equalsIgnoreCase("DeferRgtReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeferRgtReason));
		}
		if (FCode.equalsIgnoreCase("CasePayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePayType));
		}
		if (FCode.equalsIgnoreCase("AcceptedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcceptedDate()));
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
		}
		if (FCode.equalsIgnoreCase("RgtConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRgtConfDate()));
		}
		if (FCode.equalsIgnoreCase("RgtSources"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtSources));
		}
		if (FCode.equalsIgnoreCase("GrpRgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpRgtNo));
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
		}
		if (FCode.equalsIgnoreCase("EmployeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmployeNo));
		}
		if (FCode.equalsIgnoreCase("BillCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillCount));
		}
		if (FCode.equalsIgnoreCase("BackBillCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackBillCount));
		}
		if (FCode.equalsIgnoreCase("ScanCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanCount));
		}
		if (FCode.equalsIgnoreCase("IsUrgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsUrgent));
		}
		if (FCode.equalsIgnoreCase("IsOpenBillFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsOpenBillFlag));
		}
		if (FCode.equalsIgnoreCase("IsBackBill"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsBackBill));
		}
		if (FCode.equalsIgnoreCase("AcceptFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptFlag));
		}
		if (FCode.equalsIgnoreCase("BlackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlackFlag));
		}
		if (FCode.equalsIgnoreCase("RptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptNo));
		}
		if (FCode.equalsIgnoreCase("SpotCheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotCheckFlag));
		}
		if (FCode.equalsIgnoreCase("SpecCaseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecCaseFlag));
		}
		if (FCode.equalsIgnoreCase("OldClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldClmNo));
		}
		if (FCode.equalsIgnoreCase("ClaimLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimLevel));
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintState));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("PrintDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPrintDate()));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("PreAuthNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreAuthNo));
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
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtState);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RgtClass);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RgtObj);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RgtObjNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RgtType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ApplyerType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RgtantName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RgtantSex);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Relation);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RgtantAddress);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RgtantPhone);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(RgtantMobile);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Email);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PostCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AccidentSite);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AccidentReason);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AccidentCourse);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccStartDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(RgtReason);
				break;
			case 28:
				strFieldValue = String.valueOf(AppPeoples);
				break;
			case 29:
				strFieldValue = String.valueOf(AppAmnt);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 31:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(CaseGetMode);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ReturnMode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(TogetherFlag);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(RptFlag);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(DeclineFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(EndCaseFlag);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ClmState);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(Handler1);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(Handler1Phone);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(RgtConclusion);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(NoRgtReason);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(AssigneeType);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(AssigneeCode);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(AssigneeName);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(AssigneeSex);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(AssigneePhone);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(AssigneeAddr);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(AssigneeZip);
				break;
			case 64:
				strFieldValue = String.valueOf(BeAdjSum);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(FeeInputFlag);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(Recipients);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(ReciName);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(ReciAddress);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(ReciDetails);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(ReciRela);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(ReciPhone);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(ReciMobile);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(ReciZip);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(ReciSex);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(ReciEmail);
				break;
			case 76:
				strFieldValue = String.valueOf(Peoples2);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 80:
				strFieldValue = String.valueOf(GrpStandpay);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(DeferRgtRemark);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(DeferRgtReason);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(CasePayType);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcceptedDate()));
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRgtConfDate()));
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(RgtSources);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(GrpRgtNo);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(EmployeNo);
				break;
			case 91:
				strFieldValue = String.valueOf(BillCount);
				break;
			case 92:
				strFieldValue = String.valueOf(BackBillCount);
				break;
			case 93:
				strFieldValue = String.valueOf(ScanCount);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(IsUrgent);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(IsOpenBillFlag);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(IsBackBill);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(AcceptFlag);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(BlackFlag);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(RptNo);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(SpotCheckFlag);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(SpecCaseFlag);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(OldClmNo);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(ClaimLevel);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(PrintState);
				break;
			case 105:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPrintDate()));
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(PreAuthNo);
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

		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
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
		if (FCode.equalsIgnoreCase("RgtClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtClass = FValue.trim();
			}
			else
				RgtClass = null;
		}
		if (FCode.equalsIgnoreCase("RgtObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtObj = FValue.trim();
			}
			else
				RgtObj = null;
		}
		if (FCode.equalsIgnoreCase("RgtObjNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtObjNo = FValue.trim();
			}
			else
				RgtObjNo = null;
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
		if (FCode.equalsIgnoreCase("ApplyerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyerType = FValue.trim();
			}
			else
				ApplyerType = null;
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
		if (FCode.equalsIgnoreCase("RgtantName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtantName = FValue.trim();
			}
			else
				RgtantName = null;
		}
		if (FCode.equalsIgnoreCase("RgtantSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtantSex = FValue.trim();
			}
			else
				RgtantSex = null;
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relation = FValue.trim();
			}
			else
				Relation = null;
		}
		if (FCode.equalsIgnoreCase("RgtantAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtantAddress = FValue.trim();
			}
			else
				RgtantAddress = null;
		}
		if (FCode.equalsIgnoreCase("RgtantPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtantPhone = FValue.trim();
			}
			else
				RgtantPhone = null;
		}
		if (FCode.equalsIgnoreCase("RgtantMobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtantMobile = FValue.trim();
			}
			else
				RgtantMobile = null;
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Email = FValue.trim();
			}
			else
				Email = null;
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostCode = FValue.trim();
			}
			else
				PostCode = null;
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
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
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
		if (FCode.equalsIgnoreCase("AccidentSite"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentSite = FValue.trim();
			}
			else
				AccidentSite = null;
		}
		if (FCode.equalsIgnoreCase("AccidentReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentReason = FValue.trim();
			}
			else
				AccidentReason = null;
		}
		if (FCode.equalsIgnoreCase("AccidentCourse"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentCourse = FValue.trim();
			}
			else
				AccidentCourse = null;
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
		if (FCode.equalsIgnoreCase("RgtReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtReason = FValue.trim();
			}
			else
				RgtReason = null;
		}
		if (FCode.equalsIgnoreCase("AppPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AppPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("AppAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AppAmnt = d;
			}
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
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseGetMode = FValue.trim();
			}
			else
				CaseGetMode = null;
		}
		if (FCode.equalsIgnoreCase("ReturnMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnMode = FValue.trim();
			}
			else
				ReturnMode = null;
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
		if (FCode.equalsIgnoreCase("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
		}
		if (FCode.equalsIgnoreCase("TogetherFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TogetherFlag = FValue.trim();
			}
			else
				TogetherFlag = null;
		}
		if (FCode.equalsIgnoreCase("RptFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptFlag = FValue.trim();
			}
			else
				RptFlag = null;
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
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmState = FValue.trim();
			}
			else
				ClmState = null;
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
		if (FCode.equalsIgnoreCase("Handler1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler1 = FValue.trim();
			}
			else
				Handler1 = null;
		}
		if (FCode.equalsIgnoreCase("Handler1Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler1Phone = FValue.trim();
			}
			else
				Handler1Phone = null;
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
		if (FCode.equalsIgnoreCase("RgtConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtConclusion = FValue.trim();
			}
			else
				RgtConclusion = null;
		}
		if (FCode.equalsIgnoreCase("NoRgtReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoRgtReason = FValue.trim();
			}
			else
				NoRgtReason = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeType = FValue.trim();
			}
			else
				AssigneeType = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeCode = FValue.trim();
			}
			else
				AssigneeCode = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeName = FValue.trim();
			}
			else
				AssigneeName = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeSex = FValue.trim();
			}
			else
				AssigneeSex = null;
		}
		if (FCode.equalsIgnoreCase("AssigneePhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneePhone = FValue.trim();
			}
			else
				AssigneePhone = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeAddr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeAddr = FValue.trim();
			}
			else
				AssigneeAddr = null;
		}
		if (FCode.equalsIgnoreCase("AssigneeZip"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssigneeZip = FValue.trim();
			}
			else
				AssigneeZip = null;
		}
		if (FCode.equalsIgnoreCase("BeAdjSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BeAdjSum = d;
			}
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
		if (FCode.equalsIgnoreCase("Recipients"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Recipients = FValue.trim();
			}
			else
				Recipients = null;
		}
		if (FCode.equalsIgnoreCase("ReciName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciName = FValue.trim();
			}
			else
				ReciName = null;
		}
		if (FCode.equalsIgnoreCase("ReciAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciAddress = FValue.trim();
			}
			else
				ReciAddress = null;
		}
		if (FCode.equalsIgnoreCase("ReciDetails"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciDetails = FValue.trim();
			}
			else
				ReciDetails = null;
		}
		if (FCode.equalsIgnoreCase("ReciRela"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciRela = FValue.trim();
			}
			else
				ReciRela = null;
		}
		if (FCode.equalsIgnoreCase("ReciPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciPhone = FValue.trim();
			}
			else
				ReciPhone = null;
		}
		if (FCode.equalsIgnoreCase("ReciMobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciMobile = FValue.trim();
			}
			else
				ReciMobile = null;
		}
		if (FCode.equalsIgnoreCase("ReciZip"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciZip = FValue.trim();
			}
			else
				ReciZip = null;
		}
		if (FCode.equalsIgnoreCase("ReciSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciSex = FValue.trim();
			}
			else
				ReciSex = null;
		}
		if (FCode.equalsIgnoreCase("ReciEmail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReciEmail = FValue.trim();
			}
			else
				ReciEmail = null;
		}
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples2 = i;
			}
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("GrpStandpay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GrpStandpay = d;
			}
		}
		if (FCode.equalsIgnoreCase("DeferRgtRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeferRgtRemark = FValue.trim();
			}
			else
				DeferRgtRemark = null;
		}
		if (FCode.equalsIgnoreCase("DeferRgtReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeferRgtReason = FValue.trim();
			}
			else
				DeferRgtReason = null;
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
		if (FCode.equalsIgnoreCase("AcceptedDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AcceptedDate = fDate.getDate( FValue );
			}
			else
				AcceptedDate = null;
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApplyDate = fDate.getDate( FValue );
			}
			else
				ApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("RgtConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RgtConfDate = fDate.getDate( FValue );
			}
			else
				RgtConfDate = null;
		}
		if (FCode.equalsIgnoreCase("RgtSources"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtSources = FValue.trim();
			}
			else
				RgtSources = null;
		}
		if (FCode.equalsIgnoreCase("GrpRgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpRgtNo = FValue.trim();
			}
			else
				GrpRgtNo = null;
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
		if (FCode.equalsIgnoreCase("EmployeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EmployeNo = FValue.trim();
			}
			else
				EmployeNo = null;
		}
		if (FCode.equalsIgnoreCase("BillCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BillCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("BackBillCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BackBillCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("ScanCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ScanCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("IsUrgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsUrgent = FValue.trim();
			}
			else
				IsUrgent = null;
		}
		if (FCode.equalsIgnoreCase("IsOpenBillFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsOpenBillFlag = FValue.trim();
			}
			else
				IsOpenBillFlag = null;
		}
		if (FCode.equalsIgnoreCase("IsBackBill"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsBackBill = FValue.trim();
			}
			else
				IsBackBill = null;
		}
		if (FCode.equalsIgnoreCase("AcceptFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcceptFlag = FValue.trim();
			}
			else
				AcceptFlag = null;
		}
		if (FCode.equalsIgnoreCase("BlackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlackFlag = FValue.trim();
			}
			else
				BlackFlag = null;
		}
		if (FCode.equalsIgnoreCase("RptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptNo = FValue.trim();
			}
			else
				RptNo = null;
		}
		if (FCode.equalsIgnoreCase("SpotCheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotCheckFlag = FValue.trim();
			}
			else
				SpotCheckFlag = null;
		}
		if (FCode.equalsIgnoreCase("SpecCaseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecCaseFlag = FValue.trim();
			}
			else
				SpecCaseFlag = null;
		}
		if (FCode.equalsIgnoreCase("OldClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldClmNo = FValue.trim();
			}
			else
				OldClmNo = null;
		}
		if (FCode.equalsIgnoreCase("ClaimLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimLevel = FValue.trim();
			}
			else
				ClaimLevel = null;
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintState = FValue.trim();
			}
			else
				PrintState = null;
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PrintDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PrintDate = fDate.getDate( FValue );
			}
			else
				PrintDate = null;
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
		if (FCode.equalsIgnoreCase("PreAuthNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreAuthNo = FValue.trim();
			}
			else
				PreAuthNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLRegisterSchema other = (LLRegisterSchema)otherObject;
		return
			RgtNo.equals(other.getRgtNo())
			&& RgtState.equals(other.getRgtState())
			&& RgtClass.equals(other.getRgtClass())
			&& RgtObj.equals(other.getRgtObj())
			&& RgtObjNo.equals(other.getRgtObjNo())
			&& RgtType.equals(other.getRgtType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& ApplyerType.equals(other.getApplyerType())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& RgtantName.equals(other.getRgtantName())
			&& RgtantSex.equals(other.getRgtantSex())
			&& Relation.equals(other.getRelation())
			&& RgtantAddress.equals(other.getRgtantAddress())
			&& RgtantPhone.equals(other.getRgtantPhone())
			&& RgtantMobile.equals(other.getRgtantMobile())
			&& Email.equals(other.getEmail())
			&& PostCode.equals(other.getPostCode())
			&& CustomerNo.equals(other.getCustomerNo())
			&& GrpName.equals(other.getGrpName())
			&& fDate.getString(RgtDate).equals(other.getRgtDate())
			&& AccidentSite.equals(other.getAccidentSite())
			&& AccidentReason.equals(other.getAccidentReason())
			&& AccidentCourse.equals(other.getAccidentCourse())
			&& fDate.getString(AccStartDate).equals(other.getAccStartDate())
			&& fDate.getString(AccidentDate).equals(other.getAccidentDate())
			&& RgtReason.equals(other.getRgtReason())
			&& AppPeoples == other.getAppPeoples()
			&& AppAmnt == other.getAppAmnt()
			&& GetMode.equals(other.getGetMode())
			&& GetIntv == other.getGetIntv()
			&& CaseGetMode.equals(other.getCaseGetMode())
			&& ReturnMode.equals(other.getReturnMode())
			&& Remark.equals(other.getRemark())
			&& Handler.equals(other.getHandler())
			&& TogetherFlag.equals(other.getTogetherFlag())
			&& RptFlag.equals(other.getRptFlag())
			&& CalFlag.equals(other.getCalFlag())
			&& UWFlag.equals(other.getUWFlag())
			&& DeclineFlag.equals(other.getDeclineFlag())
			&& EndCaseFlag.equals(other.getEndCaseFlag())
			&& fDate.getString(EndCaseDate).equals(other.getEndCaseDate())
			&& MngCom.equals(other.getMngCom())
			&& ClmState.equals(other.getClmState())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& Handler1.equals(other.getHandler1())
			&& Handler1Phone.equals(other.getHandler1Phone())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& RgtConclusion.equals(other.getRgtConclusion())
			&& NoRgtReason.equals(other.getNoRgtReason())
			&& AssigneeType.equals(other.getAssigneeType())
			&& AssigneeCode.equals(other.getAssigneeCode())
			&& AssigneeName.equals(other.getAssigneeName())
			&& AssigneeSex.equals(other.getAssigneeSex())
			&& AssigneePhone.equals(other.getAssigneePhone())
			&& AssigneeAddr.equals(other.getAssigneeAddr())
			&& AssigneeZip.equals(other.getAssigneeZip())
			&& BeAdjSum == other.getBeAdjSum()
			&& FeeInputFlag.equals(other.getFeeInputFlag())
			&& Recipients.equals(other.getRecipients())
			&& ReciName.equals(other.getReciName())
			&& ReciAddress.equals(other.getReciAddress())
			&& ReciDetails.equals(other.getReciDetails())
			&& ReciRela.equals(other.getReciRela())
			&& ReciPhone.equals(other.getReciPhone())
			&& ReciMobile.equals(other.getReciMobile())
			&& ReciZip.equals(other.getReciZip())
			&& ReciSex.equals(other.getReciSex())
			&& ReciEmail.equals(other.getReciEmail())
			&& Peoples2 == other.getPeoples2()
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& AppntNo.equals(other.getAppntNo())
			&& GrpStandpay == other.getGrpStandpay()
			&& DeferRgtRemark.equals(other.getDeferRgtRemark())
			&& DeferRgtReason.equals(other.getDeferRgtReason())
			&& CasePayType.equals(other.getCasePayType())
			&& fDate.getString(AcceptedDate).equals(other.getAcceptedDate())
			&& fDate.getString(ApplyDate).equals(other.getApplyDate())
			&& fDate.getString(RgtConfDate).equals(other.getRgtConfDate())
			&& RgtSources.equals(other.getRgtSources())
			&& GrpRgtNo.equals(other.getGrpRgtNo())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& EmployeNo.equals(other.getEmployeNo())
			&& BillCount == other.getBillCount()
			&& BackBillCount == other.getBackBillCount()
			&& ScanCount == other.getScanCount()
			&& IsUrgent.equals(other.getIsUrgent())
			&& IsOpenBillFlag.equals(other.getIsOpenBillFlag())
			&& IsBackBill.equals(other.getIsBackBill())
			&& AcceptFlag.equals(other.getAcceptFlag())
			&& BlackFlag.equals(other.getBlackFlag())
			&& RptNo.equals(other.getRptNo())
			&& SpotCheckFlag.equals(other.getSpotCheckFlag())
			&& SpecCaseFlag.equals(other.getSpecCaseFlag())
			&& OldClmNo.equals(other.getOldClmNo())
			&& ClaimLevel.equals(other.getClaimLevel())
			&& PrintState.equals(other.getPrintState())
			&& PrintCount == other.getPrintCount()
			&& fDate.getString(PrintDate).equals(other.getPrintDate())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& PreAuthNo.equals(other.getPreAuthNo());
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
		if( strFieldName.equals("RgtNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtState") ) {
			return 1;
		}
		if( strFieldName.equals("RgtClass") ) {
			return 2;
		}
		if( strFieldName.equals("RgtObj") ) {
			return 3;
		}
		if( strFieldName.equals("RgtObjNo") ) {
			return 4;
		}
		if( strFieldName.equals("RgtType") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 7;
		}
		if( strFieldName.equals("ApplyerType") ) {
			return 8;
		}
		if( strFieldName.equals("IDType") ) {
			return 9;
		}
		if( strFieldName.equals("IDNo") ) {
			return 10;
		}
		if( strFieldName.equals("RgtantName") ) {
			return 11;
		}
		if( strFieldName.equals("RgtantSex") ) {
			return 12;
		}
		if( strFieldName.equals("Relation") ) {
			return 13;
		}
		if( strFieldName.equals("RgtantAddress") ) {
			return 14;
		}
		if( strFieldName.equals("RgtantPhone") ) {
			return 15;
		}
		if( strFieldName.equals("RgtantMobile") ) {
			return 16;
		}
		if( strFieldName.equals("Email") ) {
			return 17;
		}
		if( strFieldName.equals("PostCode") ) {
			return 18;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 19;
		}
		if( strFieldName.equals("GrpName") ) {
			return 20;
		}
		if( strFieldName.equals("RgtDate") ) {
			return 21;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return 22;
		}
		if( strFieldName.equals("AccidentReason") ) {
			return 23;
		}
		if( strFieldName.equals("AccidentCourse") ) {
			return 24;
		}
		if( strFieldName.equals("AccStartDate") ) {
			return 25;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 26;
		}
		if( strFieldName.equals("RgtReason") ) {
			return 27;
		}
		if( strFieldName.equals("AppPeoples") ) {
			return 28;
		}
		if( strFieldName.equals("AppAmnt") ) {
			return 29;
		}
		if( strFieldName.equals("GetMode") ) {
			return 30;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 31;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return 32;
		}
		if( strFieldName.equals("ReturnMode") ) {
			return 33;
		}
		if( strFieldName.equals("Remark") ) {
			return 34;
		}
		if( strFieldName.equals("Handler") ) {
			return 35;
		}
		if( strFieldName.equals("TogetherFlag") ) {
			return 36;
		}
		if( strFieldName.equals("RptFlag") ) {
			return 37;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 38;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 39;
		}
		if( strFieldName.equals("DeclineFlag") ) {
			return 40;
		}
		if( strFieldName.equals("EndCaseFlag") ) {
			return 41;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 42;
		}
		if( strFieldName.equals("MngCom") ) {
			return 43;
		}
		if( strFieldName.equals("ClmState") ) {
			return 44;
		}
		if( strFieldName.equals("BankCode") ) {
			return 45;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 46;
		}
		if( strFieldName.equals("AccName") ) {
			return 47;
		}
		if( strFieldName.equals("Handler1") ) {
			return 48;
		}
		if( strFieldName.equals("Handler1Phone") ) {
			return 49;
		}
		if( strFieldName.equals("Operator") ) {
			return 50;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 51;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 52;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 53;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 54;
		}
		if( strFieldName.equals("RgtConclusion") ) {
			return 55;
		}
		if( strFieldName.equals("NoRgtReason") ) {
			return 56;
		}
		if( strFieldName.equals("AssigneeType") ) {
			return 57;
		}
		if( strFieldName.equals("AssigneeCode") ) {
			return 58;
		}
		if( strFieldName.equals("AssigneeName") ) {
			return 59;
		}
		if( strFieldName.equals("AssigneeSex") ) {
			return 60;
		}
		if( strFieldName.equals("AssigneePhone") ) {
			return 61;
		}
		if( strFieldName.equals("AssigneeAddr") ) {
			return 62;
		}
		if( strFieldName.equals("AssigneeZip") ) {
			return 63;
		}
		if( strFieldName.equals("BeAdjSum") ) {
			return 64;
		}
		if( strFieldName.equals("FeeInputFlag") ) {
			return 65;
		}
		if( strFieldName.equals("Recipients") ) {
			return 66;
		}
		if( strFieldName.equals("ReciName") ) {
			return 67;
		}
		if( strFieldName.equals("ReciAddress") ) {
			return 68;
		}
		if( strFieldName.equals("ReciDetails") ) {
			return 69;
		}
		if( strFieldName.equals("ReciRela") ) {
			return 70;
		}
		if( strFieldName.equals("ReciPhone") ) {
			return 71;
		}
		if( strFieldName.equals("ReciMobile") ) {
			return 72;
		}
		if( strFieldName.equals("ReciZip") ) {
			return 73;
		}
		if( strFieldName.equals("ReciSex") ) {
			return 74;
		}
		if( strFieldName.equals("ReciEmail") ) {
			return 75;
		}
		if( strFieldName.equals("Peoples2") ) {
			return 76;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 77;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 78;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 79;
		}
		if( strFieldName.equals("GrpStandpay") ) {
			return 80;
		}
		if( strFieldName.equals("DeferRgtRemark") ) {
			return 81;
		}
		if( strFieldName.equals("DeferRgtReason") ) {
			return 82;
		}
		if( strFieldName.equals("CasePayType") ) {
			return 83;
		}
		if( strFieldName.equals("AcceptedDate") ) {
			return 84;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return 85;
		}
		if( strFieldName.equals("RgtConfDate") ) {
			return 86;
		}
		if( strFieldName.equals("RgtSources") ) {
			return 87;
		}
		if( strFieldName.equals("GrpRgtNo") ) {
			return 88;
		}
		if( strFieldName.equals("Birthday") ) {
			return 89;
		}
		if( strFieldName.equals("EmployeNo") ) {
			return 90;
		}
		if( strFieldName.equals("BillCount") ) {
			return 91;
		}
		if( strFieldName.equals("BackBillCount") ) {
			return 92;
		}
		if( strFieldName.equals("ScanCount") ) {
			return 93;
		}
		if( strFieldName.equals("IsUrgent") ) {
			return 94;
		}
		if( strFieldName.equals("IsOpenBillFlag") ) {
			return 95;
		}
		if( strFieldName.equals("IsBackBill") ) {
			return 96;
		}
		if( strFieldName.equals("AcceptFlag") ) {
			return 97;
		}
		if( strFieldName.equals("BlackFlag") ) {
			return 98;
		}
		if( strFieldName.equals("RptNo") ) {
			return 99;
		}
		if( strFieldName.equals("SpotCheckFlag") ) {
			return 100;
		}
		if( strFieldName.equals("SpecCaseFlag") ) {
			return 101;
		}
		if( strFieldName.equals("OldClmNo") ) {
			return 102;
		}
		if( strFieldName.equals("ClaimLevel") ) {
			return 103;
		}
		if( strFieldName.equals("PrintState") ) {
			return 104;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 105;
		}
		if( strFieldName.equals("PrintDate") ) {
			return 106;
		}
		if( strFieldName.equals("ComCode") ) {
			return 107;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 108;
		}
		if( strFieldName.equals("PreAuthNo") ) {
			return 109;
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
				strFieldName = "RgtNo";
				break;
			case 1:
				strFieldName = "RgtState";
				break;
			case 2:
				strFieldName = "RgtClass";
				break;
			case 3:
				strFieldName = "RgtObj";
				break;
			case 4:
				strFieldName = "RgtObjNo";
				break;
			case 5:
				strFieldName = "RgtType";
				break;
			case 6:
				strFieldName = "AgentCode";
				break;
			case 7:
				strFieldName = "AgentGroup";
				break;
			case 8:
				strFieldName = "ApplyerType";
				break;
			case 9:
				strFieldName = "IDType";
				break;
			case 10:
				strFieldName = "IDNo";
				break;
			case 11:
				strFieldName = "RgtantName";
				break;
			case 12:
				strFieldName = "RgtantSex";
				break;
			case 13:
				strFieldName = "Relation";
				break;
			case 14:
				strFieldName = "RgtantAddress";
				break;
			case 15:
				strFieldName = "RgtantPhone";
				break;
			case 16:
				strFieldName = "RgtantMobile";
				break;
			case 17:
				strFieldName = "Email";
				break;
			case 18:
				strFieldName = "PostCode";
				break;
			case 19:
				strFieldName = "CustomerNo";
				break;
			case 20:
				strFieldName = "GrpName";
				break;
			case 21:
				strFieldName = "RgtDate";
				break;
			case 22:
				strFieldName = "AccidentSite";
				break;
			case 23:
				strFieldName = "AccidentReason";
				break;
			case 24:
				strFieldName = "AccidentCourse";
				break;
			case 25:
				strFieldName = "AccStartDate";
				break;
			case 26:
				strFieldName = "AccidentDate";
				break;
			case 27:
				strFieldName = "RgtReason";
				break;
			case 28:
				strFieldName = "AppPeoples";
				break;
			case 29:
				strFieldName = "AppAmnt";
				break;
			case 30:
				strFieldName = "GetMode";
				break;
			case 31:
				strFieldName = "GetIntv";
				break;
			case 32:
				strFieldName = "CaseGetMode";
				break;
			case 33:
				strFieldName = "ReturnMode";
				break;
			case 34:
				strFieldName = "Remark";
				break;
			case 35:
				strFieldName = "Handler";
				break;
			case 36:
				strFieldName = "TogetherFlag";
				break;
			case 37:
				strFieldName = "RptFlag";
				break;
			case 38:
				strFieldName = "CalFlag";
				break;
			case 39:
				strFieldName = "UWFlag";
				break;
			case 40:
				strFieldName = "DeclineFlag";
				break;
			case 41:
				strFieldName = "EndCaseFlag";
				break;
			case 42:
				strFieldName = "EndCaseDate";
				break;
			case 43:
				strFieldName = "MngCom";
				break;
			case 44:
				strFieldName = "ClmState";
				break;
			case 45:
				strFieldName = "BankCode";
				break;
			case 46:
				strFieldName = "BankAccNo";
				break;
			case 47:
				strFieldName = "AccName";
				break;
			case 48:
				strFieldName = "Handler1";
				break;
			case 49:
				strFieldName = "Handler1Phone";
				break;
			case 50:
				strFieldName = "Operator";
				break;
			case 51:
				strFieldName = "MakeDate";
				break;
			case 52:
				strFieldName = "MakeTime";
				break;
			case 53:
				strFieldName = "ModifyDate";
				break;
			case 54:
				strFieldName = "ModifyTime";
				break;
			case 55:
				strFieldName = "RgtConclusion";
				break;
			case 56:
				strFieldName = "NoRgtReason";
				break;
			case 57:
				strFieldName = "AssigneeType";
				break;
			case 58:
				strFieldName = "AssigneeCode";
				break;
			case 59:
				strFieldName = "AssigneeName";
				break;
			case 60:
				strFieldName = "AssigneeSex";
				break;
			case 61:
				strFieldName = "AssigneePhone";
				break;
			case 62:
				strFieldName = "AssigneeAddr";
				break;
			case 63:
				strFieldName = "AssigneeZip";
				break;
			case 64:
				strFieldName = "BeAdjSum";
				break;
			case 65:
				strFieldName = "FeeInputFlag";
				break;
			case 66:
				strFieldName = "Recipients";
				break;
			case 67:
				strFieldName = "ReciName";
				break;
			case 68:
				strFieldName = "ReciAddress";
				break;
			case 69:
				strFieldName = "ReciDetails";
				break;
			case 70:
				strFieldName = "ReciRela";
				break;
			case 71:
				strFieldName = "ReciPhone";
				break;
			case 72:
				strFieldName = "ReciMobile";
				break;
			case 73:
				strFieldName = "ReciZip";
				break;
			case 74:
				strFieldName = "ReciSex";
				break;
			case 75:
				strFieldName = "ReciEmail";
				break;
			case 76:
				strFieldName = "Peoples2";
				break;
			case 77:
				strFieldName = "GrpContNo";
				break;
			case 78:
				strFieldName = "RiskCode";
				break;
			case 79:
				strFieldName = "AppntNo";
				break;
			case 80:
				strFieldName = "GrpStandpay";
				break;
			case 81:
				strFieldName = "DeferRgtRemark";
				break;
			case 82:
				strFieldName = "DeferRgtReason";
				break;
			case 83:
				strFieldName = "CasePayType";
				break;
			case 84:
				strFieldName = "AcceptedDate";
				break;
			case 85:
				strFieldName = "ApplyDate";
				break;
			case 86:
				strFieldName = "RgtConfDate";
				break;
			case 87:
				strFieldName = "RgtSources";
				break;
			case 88:
				strFieldName = "GrpRgtNo";
				break;
			case 89:
				strFieldName = "Birthday";
				break;
			case 90:
				strFieldName = "EmployeNo";
				break;
			case 91:
				strFieldName = "BillCount";
				break;
			case 92:
				strFieldName = "BackBillCount";
				break;
			case 93:
				strFieldName = "ScanCount";
				break;
			case 94:
				strFieldName = "IsUrgent";
				break;
			case 95:
				strFieldName = "IsOpenBillFlag";
				break;
			case 96:
				strFieldName = "IsBackBill";
				break;
			case 97:
				strFieldName = "AcceptFlag";
				break;
			case 98:
				strFieldName = "BlackFlag";
				break;
			case 99:
				strFieldName = "RptNo";
				break;
			case 100:
				strFieldName = "SpotCheckFlag";
				break;
			case 101:
				strFieldName = "SpecCaseFlag";
				break;
			case 102:
				strFieldName = "OldClmNo";
				break;
			case 103:
				strFieldName = "ClaimLevel";
				break;
			case 104:
				strFieldName = "PrintState";
				break;
			case 105:
				strFieldName = "PrintCount";
				break;
			case 106:
				strFieldName = "PrintDate";
				break;
			case 107:
				strFieldName = "ComCode";
				break;
			case 108:
				strFieldName = "ModifyOperator";
				break;
			case 109:
				strFieldName = "PreAuthNo";
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
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtObjNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtantName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtantSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtantAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtantPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtantMobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Email") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccidentSite") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentCourse") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RgtReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TogetherFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptFlag") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("ClmState") ) {
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
		if( strFieldName.equals("Handler1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler1Phone") ) {
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
		if( strFieldName.equals("RgtConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoRgtReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeAddr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssigneeZip") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BeAdjSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeInputFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Recipients") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciDetails") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciRela") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciMobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciZip") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReciEmail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples2") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpStandpay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeferRgtRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeferRgtReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptedDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RgtConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RgtSources") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpRgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EmployeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BackBillCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ScanCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IsUrgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsOpenBillFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsBackBill") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlackFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotCheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecCaseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PrintDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreAuthNo") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_INT;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 85:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 86:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 89:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_INT;
				break;
			case 92:
				nFieldType = Schema.TYPE_INT;
				break;
			case 93:
				nFieldType = Schema.TYPE_INT;
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
			case 99:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_INT;
				break;
			case 106:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 107:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 108:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 109:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
