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
import com.sinosoft.lis.db.LLMainAskDB;

/*
 * <p>ClassName: LLMainAskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLMainAskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLMainAskSchema.class);
	// @Field
	/** 登记号码 */
	private String LogNo;
	/** 登记状态 */
	private String LogState;
	/** 登记类型 */
	private String AskType;
	/** 其他对应号码 */
	private String OtherNo;
	/** 其它对应号码类型 */
	private String OtherNoType;
	/** 登记方式 */
	private String AskMode;
	/** 登记人客户号 */
	private String LogerNo;
	/** 登记人姓名 */
	private String LogName;
	/** 登记人单位名称 */
	private String LogComp;
	/** 单位客户号 */
	private String LogCompNo;
	/** 登记日期 */
	private Date LogDate;
	/** 登记时间 */
	private String LogTime;
	/** 登记人电话 */
	private String Phone;
	/** 登记人手机 */
	private String Mobile;
	/** 登记人邮政编码 */
	private String PostCode;
	/** 登记人通讯地址 */
	private String AskAddress;
	/** 登记人电邮 */
	private String Email;
	/** 答疑类型 */
	private String AnswerType;
	/** 答复方式 */
	private String AnswerMode;
	/** 是否邮寄资料 */
	private String SendFlag;
	/** 资料寄出时间 */
	private Date SendDate;
	/** 转入部门 */
	private String SwitchCom;
	/** 转入日期(信息分发日期) */
	private Date SwitchDate;
	/** 转入时间(信息分发时间) */
	private String SwitchTime;
	/** 任务分发人 */
	private String Despatcher;
	/** 服务日期 */
	private Date CNSDate;
	/** 期望回复日期 */
	private Date ExpectRevertDate;
	/** 期望回复时间 */
	private String ExpectRevertTime;
	/** 是否咨询专家 */
	private String ExpertFlag;
	/** 答复完成日期 */
	private Date ReplyFDate;
	/** 处理完成日期 */
	private Date DealFDate;
	/** 咨询记录人 */
	private String CNSOperator;
	/** 答复占用时间 */
	private int RevertPeriod;
	/** 处理占用时间 */
	private int DealPeriod;
	/** 备注 */
	private String Remark;
	/** ???记有效标志 */
	private String AvaiFlag;
	/** 登记无效原因 */
	private String NotAvaliReason;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String MngCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 43;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLMainAskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogNo";

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
		LLMainAskSchema cloned = (LLMainAskSchema)super.clone();
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
	* 登记流水号码
	*/
	public String getLogNo()
	{
		return LogNo;
	}
	public void setLogNo(String aLogNo)
	{
		if(aLogNo!=null && aLogNo.length()>20)
			throw new IllegalArgumentException("登记号码LogNo值"+aLogNo+"的长度"+aLogNo.length()+"大于最大值20");
		LogNo = aLogNo;
	}
	/**
	* 0-提起询问，未进行回复 1-部分回复，但不完全， 2-该询问已经进行了完整的回复 3-(短时)延时答疑未分配 3-(短时)延时答疑分配
	*/
	public String getLogState()
	{
		return LogState;
	}
	public void setLogState(String aLogState)
	{
		if(aLogState!=null && aLogState.length()>1)
			throw new IllegalArgumentException("登记状态LogState值"+aLogState+"的长度"+aLogState.length()+"大于最大值1");
		LogState = aLogState;
	}
	/**
	* 0咨询 :咨询和通知产生登记号 1通知 2即有咨询也有通知 3健康咨询(用于健康管理中)
	*/
	public String getAskType()
	{
		return AskType;
	}
	public void setAskType(String aAskType)
	{
		if(aAskType!=null && aAskType.length()>1)
			throw new IllegalArgumentException("登记类型AskType值"+aAskType+"的长度"+aAskType.length()+"大于最大值1");
		AskType = aAskType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其他对应号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	/**
	* 0个人保单 1团体保单 2家庭单
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>1)
			throw new IllegalArgumentException("其它对应号码类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值1");
		OtherNoType = aOtherNoType;
	}
	/**
	* 00-信函 01-客户上门咨询； 02-电话； 03-传真； 04-邮件； 09-其他方式咨询 (咨询方式采用可扩展的方式，其他的方式待定)
	*/
	public String getAskMode()
	{
		return AskMode;
	}
	public void setAskMode(String aAskMode)
	{
		if(aAskMode!=null && aAskMode.length()>2)
			throw new IllegalArgumentException("登记方式AskMode值"+aAskMode+"的长度"+aAskMode.length()+"大于最大值2");
		AskMode = aAskMode;
	}
	public String getLogerNo()
	{
		return LogerNo;
	}
	public void setLogerNo(String aLogerNo)
	{
		if(aLogerNo!=null && aLogerNo.length()>20)
			throw new IllegalArgumentException("登记人客户号LogerNo值"+aLogerNo+"的长度"+aLogerNo.length()+"大于最大值20");
		LogerNo = aLogerNo;
	}
	public String getLogName()
	{
		return LogName;
	}
	public void setLogName(String aLogName)
	{
		if(aLogName!=null && aLogName.length()>20)
			throw new IllegalArgumentException("登记人姓名LogName值"+aLogName+"的长度"+aLogName.length()+"大于最大值20");
		LogName = aLogName;
	}
	public String getLogComp()
	{
		return LogComp;
	}
	public void setLogComp(String aLogComp)
	{
		if(aLogComp!=null && aLogComp.length()>100)
			throw new IllegalArgumentException("登记人单位名称LogComp值"+aLogComp+"的长度"+aLogComp.length()+"大于最大值100");
		LogComp = aLogComp;
	}
	public String getLogCompNo()
	{
		return LogCompNo;
	}
	public void setLogCompNo(String aLogCompNo)
	{
		if(aLogCompNo!=null && aLogCompNo.length()>20)
			throw new IllegalArgumentException("单位客户号LogCompNo值"+aLogCompNo+"的长度"+aLogCompNo.length()+"大于最大值20");
		LogCompNo = aLogCompNo;
	}
	public String getLogDate()
	{
		if( LogDate != null )
			return fDate.getString(LogDate);
		else
			return null;
	}
	public void setLogDate(Date aLogDate)
	{
		LogDate = aLogDate;
	}
	public void setLogDate(String aLogDate)
	{
		if (aLogDate != null && !aLogDate.equals("") )
		{
			LogDate = fDate.getDate( aLogDate );
		}
		else
			LogDate = null;
	}

	public String getLogTime()
	{
		return LogTime;
	}
	public void setLogTime(String aLogTime)
	{
		if(aLogTime!=null && aLogTime.length()>8)
			throw new IllegalArgumentException("登记时间LogTime值"+aLogTime+"的长度"+aLogTime.length()+"大于最大值8");
		LogTime = aLogTime;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>18)
			throw new IllegalArgumentException("登记人电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值18");
		Phone = aPhone;
	}
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		if(aMobile!=null && aMobile.length()>15)
			throw new IllegalArgumentException("登记人手机Mobile值"+aMobile+"的长度"+aMobile.length()+"大于最大值15");
		Mobile = aMobile;
	}
	public String getPostCode()
	{
		return PostCode;
	}
	public void setPostCode(String aPostCode)
	{
		if(aPostCode!=null && aPostCode.length()>6)
			throw new IllegalArgumentException("登记人邮政编码PostCode值"+aPostCode+"的长度"+aPostCode.length()+"大于最大值6");
		PostCode = aPostCode;
	}
	public String getAskAddress()
	{
		return AskAddress;
	}
	public void setAskAddress(String aAskAddress)
	{
		if(aAskAddress!=null && aAskAddress.length()>80)
			throw new IllegalArgumentException("登记人通讯地址AskAddress值"+aAskAddress+"的长度"+aAskAddress.length()+"大于最大值80");
		AskAddress = aAskAddress;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		if(aEmail!=null && aEmail.length()>40)
			throw new IllegalArgumentException("登记人电邮Email值"+aEmail+"的长度"+aEmail.length()+"大于最大值40");
		Email = aEmail;
	}
	/**
	* 即时答疑 短延时 长延时
	*/
	public String getAnswerType()
	{
		return AnswerType;
	}
	public void setAnswerType(String aAnswerType)
	{
		if(aAnswerType!=null && aAnswerType.length()>2)
			throw new IllegalArgumentException("答疑类型AnswerType值"+aAnswerType+"的长度"+aAnswerType.length()+"大于最大值2");
		AnswerType = aAnswerType;
	}
	/**
	* 00-信函 01-客户上门咨询； 02-电话； 03-传真； 04-邮件； 09-其他方式咨询 (咨询方式采用可扩展的方式，其他的方式待定)
	*/
	public String getAnswerMode()
	{
		return AnswerMode;
	}
	public void setAnswerMode(String aAnswerMode)
	{
		if(aAnswerMode!=null && aAnswerMode.length()>2)
			throw new IllegalArgumentException("答复方式AnswerMode值"+aAnswerMode+"的长度"+aAnswerMode.length()+"大于最大值2");
		AnswerMode = aAnswerMode;
	}
	public String getSendFlag()
	{
		return SendFlag;
	}
	public void setSendFlag(String aSendFlag)
	{
		if(aSendFlag!=null && aSendFlag.length()>2)
			throw new IllegalArgumentException("是否邮寄资料SendFlag值"+aSendFlag+"的长度"+aSendFlag.length()+"大于最大值2");
		SendFlag = aSendFlag;
	}
	public String getSendDate()
	{
		if( SendDate != null )
			return fDate.getString(SendDate);
		else
			return null;
	}
	public void setSendDate(Date aSendDate)
	{
		SendDate = aSendDate;
	}
	public void setSendDate(String aSendDate)
	{
		if (aSendDate != null && !aSendDate.equals("") )
		{
			SendDate = fDate.getDate( aSendDate );
		}
		else
			SendDate = null;
	}

	public String getSwitchCom()
	{
		return SwitchCom;
	}
	public void setSwitchCom(String aSwitchCom)
	{
		if(aSwitchCom!=null && aSwitchCom.length()>10)
			throw new IllegalArgumentException("转入部门SwitchCom值"+aSwitchCom+"的长度"+aSwitchCom.length()+"大于最大值10");
		SwitchCom = aSwitchCom;
	}
	public String getSwitchDate()
	{
		if( SwitchDate != null )
			return fDate.getString(SwitchDate);
		else
			return null;
	}
	public void setSwitchDate(Date aSwitchDate)
	{
		SwitchDate = aSwitchDate;
	}
	public void setSwitchDate(String aSwitchDate)
	{
		if (aSwitchDate != null && !aSwitchDate.equals("") )
		{
			SwitchDate = fDate.getDate( aSwitchDate );
		}
		else
			SwitchDate = null;
	}

	public String getSwitchTime()
	{
		return SwitchTime;
	}
	public void setSwitchTime(String aSwitchTime)
	{
		if(aSwitchTime!=null && aSwitchTime.length()>8)
			throw new IllegalArgumentException("转入时间(信息分发时间)SwitchTime值"+aSwitchTime+"的长度"+aSwitchTime.length()+"大于最大值8");
		SwitchTime = aSwitchTime;
	}
	public String getDespatcher()
	{
		return Despatcher;
	}
	public void setDespatcher(String aDespatcher)
	{
		if(aDespatcher!=null && aDespatcher.length()>10)
			throw new IllegalArgumentException("任务分发人Despatcher值"+aDespatcher+"的长度"+aDespatcher.length()+"大于最大值10");
		Despatcher = aDespatcher;
	}
	public String getCNSDate()
	{
		if( CNSDate != null )
			return fDate.getString(CNSDate);
		else
			return null;
	}
	public void setCNSDate(Date aCNSDate)
	{
		CNSDate = aCNSDate;
	}
	public void setCNSDate(String aCNSDate)
	{
		if (aCNSDate != null && !aCNSDate.equals("") )
		{
			CNSDate = fDate.getDate( aCNSDate );
		}
		else
			CNSDate = null;
	}

	public String getExpectRevertDate()
	{
		if( ExpectRevertDate != null )
			return fDate.getString(ExpectRevertDate);
		else
			return null;
	}
	public void setExpectRevertDate(Date aExpectRevertDate)
	{
		ExpectRevertDate = aExpectRevertDate;
	}
	public void setExpectRevertDate(String aExpectRevertDate)
	{
		if (aExpectRevertDate != null && !aExpectRevertDate.equals("") )
		{
			ExpectRevertDate = fDate.getDate( aExpectRevertDate );
		}
		else
			ExpectRevertDate = null;
	}

	public String getExpectRevertTime()
	{
		return ExpectRevertTime;
	}
	public void setExpectRevertTime(String aExpectRevertTime)
	{
		if(aExpectRevertTime!=null && aExpectRevertTime.length()>8)
			throw new IllegalArgumentException("期望回复时间ExpectRevertTime值"+aExpectRevertTime+"的长度"+aExpectRevertTime.length()+"大于最大值8");
		ExpectRevertTime = aExpectRevertTime;
	}
	public String getExpertFlag()
	{
		return ExpertFlag;
	}
	public void setExpertFlag(String aExpertFlag)
	{
		if(aExpertFlag!=null && aExpertFlag.length()>2)
			throw new IllegalArgumentException("是否咨询专家ExpertFlag值"+aExpertFlag+"的长度"+aExpertFlag.length()+"大于最大值2");
		ExpertFlag = aExpertFlag;
	}
	/**
	* 单位分钟
	*/
	public String getReplyFDate()
	{
		if( ReplyFDate != null )
			return fDate.getString(ReplyFDate);
		else
			return null;
	}
	public void setReplyFDate(Date aReplyFDate)
	{
		ReplyFDate = aReplyFDate;
	}
	public void setReplyFDate(String aReplyFDate)
	{
		if (aReplyFDate != null && !aReplyFDate.equals("") )
		{
			ReplyFDate = fDate.getDate( aReplyFDate );
		}
		else
			ReplyFDate = null;
	}

	/**
	* 单位分钟
	*/
	public String getDealFDate()
	{
		if( DealFDate != null )
			return fDate.getString(DealFDate);
		else
			return null;
	}
	public void setDealFDate(Date aDealFDate)
	{
		DealFDate = aDealFDate;
	}
	public void setDealFDate(String aDealFDate)
	{
		if (aDealFDate != null && !aDealFDate.equals("") )
		{
			DealFDate = fDate.getDate( aDealFDate );
		}
		else
			DealFDate = null;
	}

	public String getCNSOperator()
	{
		return CNSOperator;
	}
	public void setCNSOperator(String aCNSOperator)
	{
		if(aCNSOperator!=null && aCNSOperator.length()>10)
			throw new IllegalArgumentException("咨询记录人CNSOperator值"+aCNSOperator+"的长度"+aCNSOperator.length()+"大于最大值10");
		CNSOperator = aCNSOperator;
	}
	/**
	* 单位分钟
	*/
	public int getRevertPeriod()
	{
		return RevertPeriod;
	}
	public void setRevertPeriod(int aRevertPeriod)
	{
		RevertPeriod = aRevertPeriod;
	}
	public void setRevertPeriod(String aRevertPeriod)
	{
		if (aRevertPeriod != null && !aRevertPeriod.equals(""))
		{
			Integer tInteger = new Integer(aRevertPeriod);
			int i = tInteger.intValue();
			RevertPeriod = i;
		}
	}

	/**
	* 单位分钟
	*/
	public int getDealPeriod()
	{
		return DealPeriod;
	}
	public void setDealPeriod(int aDealPeriod)
	{
		DealPeriod = aDealPeriod;
	}
	public void setDealPeriod(String aDealPeriod)
	{
		if (aDealPeriod != null && !aDealPeriod.equals(""))
		{
			Integer tInteger = new Integer(aDealPeriod);
			int i = tInteger.intValue();
			DealPeriod = i;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>2000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值2000");
		Remark = aRemark;
	}
	public String getAvaiFlag()
	{
		return AvaiFlag;
	}
	public void setAvaiFlag(String aAvaiFlag)
	{
		if(aAvaiFlag!=null && aAvaiFlag.length()>1)
			throw new IllegalArgumentException("???记有效标志AvaiFlag值"+aAvaiFlag+"的长度"+aAvaiFlag.length()+"大于最大值1");
		AvaiFlag = aAvaiFlag;
	}
	public String getNotAvaliReason()
	{
		return NotAvaliReason;
	}
	public void setNotAvaliReason(String aNotAvaliReason)
	{
		if(aNotAvaliReason!=null && aNotAvaliReason.length()>200)
			throw new IllegalArgumentException("登记无效原因NotAvaliReason值"+aNotAvaliReason+"的长度"+aNotAvaliReason.length()+"大于最大值200");
		NotAvaliReason = aNotAvaliReason;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>6)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值6");
		Operator = aOperator;
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
	* 使用另外一个 LLMainAskSchema 对象给 Schema 赋值
	* @param: aLLMainAskSchema LLMainAskSchema
	**/
	public void setSchema(LLMainAskSchema aLLMainAskSchema)
	{
		this.LogNo = aLLMainAskSchema.getLogNo();
		this.LogState = aLLMainAskSchema.getLogState();
		this.AskType = aLLMainAskSchema.getAskType();
		this.OtherNo = aLLMainAskSchema.getOtherNo();
		this.OtherNoType = aLLMainAskSchema.getOtherNoType();
		this.AskMode = aLLMainAskSchema.getAskMode();
		this.LogerNo = aLLMainAskSchema.getLogerNo();
		this.LogName = aLLMainAskSchema.getLogName();
		this.LogComp = aLLMainAskSchema.getLogComp();
		this.LogCompNo = aLLMainAskSchema.getLogCompNo();
		this.LogDate = fDate.getDate( aLLMainAskSchema.getLogDate());
		this.LogTime = aLLMainAskSchema.getLogTime();
		this.Phone = aLLMainAskSchema.getPhone();
		this.Mobile = aLLMainAskSchema.getMobile();
		this.PostCode = aLLMainAskSchema.getPostCode();
		this.AskAddress = aLLMainAskSchema.getAskAddress();
		this.Email = aLLMainAskSchema.getEmail();
		this.AnswerType = aLLMainAskSchema.getAnswerType();
		this.AnswerMode = aLLMainAskSchema.getAnswerMode();
		this.SendFlag = aLLMainAskSchema.getSendFlag();
		this.SendDate = fDate.getDate( aLLMainAskSchema.getSendDate());
		this.SwitchCom = aLLMainAskSchema.getSwitchCom();
		this.SwitchDate = fDate.getDate( aLLMainAskSchema.getSwitchDate());
		this.SwitchTime = aLLMainAskSchema.getSwitchTime();
		this.Despatcher = aLLMainAskSchema.getDespatcher();
		this.CNSDate = fDate.getDate( aLLMainAskSchema.getCNSDate());
		this.ExpectRevertDate = fDate.getDate( aLLMainAskSchema.getExpectRevertDate());
		this.ExpectRevertTime = aLLMainAskSchema.getExpectRevertTime();
		this.ExpertFlag = aLLMainAskSchema.getExpertFlag();
		this.ReplyFDate = fDate.getDate( aLLMainAskSchema.getReplyFDate());
		this.DealFDate = fDate.getDate( aLLMainAskSchema.getDealFDate());
		this.CNSOperator = aLLMainAskSchema.getCNSOperator();
		this.RevertPeriod = aLLMainAskSchema.getRevertPeriod();
		this.DealPeriod = aLLMainAskSchema.getDealPeriod();
		this.Remark = aLLMainAskSchema.getRemark();
		this.AvaiFlag = aLLMainAskSchema.getAvaiFlag();
		this.NotAvaliReason = aLLMainAskSchema.getNotAvaliReason();
		this.Operator = aLLMainAskSchema.getOperator();
		this.MngCom = aLLMainAskSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLMainAskSchema.getMakeDate());
		this.MakeTime = aLLMainAskSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLMainAskSchema.getModifyDate());
		this.ModifyTime = aLLMainAskSchema.getModifyTime();
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
			if( rs.getString("LogNo") == null )
				this.LogNo = null;
			else
				this.LogNo = rs.getString("LogNo").trim();

			if( rs.getString("LogState") == null )
				this.LogState = null;
			else
				this.LogState = rs.getString("LogState").trim();

			if( rs.getString("AskType") == null )
				this.AskType = null;
			else
				this.AskType = rs.getString("AskType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("AskMode") == null )
				this.AskMode = null;
			else
				this.AskMode = rs.getString("AskMode").trim();

			if( rs.getString("LogerNo") == null )
				this.LogerNo = null;
			else
				this.LogerNo = rs.getString("LogerNo").trim();

			if( rs.getString("LogName") == null )
				this.LogName = null;
			else
				this.LogName = rs.getString("LogName").trim();

			if( rs.getString("LogComp") == null )
				this.LogComp = null;
			else
				this.LogComp = rs.getString("LogComp").trim();

			if( rs.getString("LogCompNo") == null )
				this.LogCompNo = null;
			else
				this.LogCompNo = rs.getString("LogCompNo").trim();

			this.LogDate = rs.getDate("LogDate");
			if( rs.getString("LogTime") == null )
				this.LogTime = null;
			else
				this.LogTime = rs.getString("LogTime").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("PostCode") == null )
				this.PostCode = null;
			else
				this.PostCode = rs.getString("PostCode").trim();

			if( rs.getString("AskAddress") == null )
				this.AskAddress = null;
			else
				this.AskAddress = rs.getString("AskAddress").trim();

			if( rs.getString("Email") == null )
				this.Email = null;
			else
				this.Email = rs.getString("Email").trim();

			if( rs.getString("AnswerType") == null )
				this.AnswerType = null;
			else
				this.AnswerType = rs.getString("AnswerType").trim();

			if( rs.getString("AnswerMode") == null )
				this.AnswerMode = null;
			else
				this.AnswerMode = rs.getString("AnswerMode").trim();

			if( rs.getString("SendFlag") == null )
				this.SendFlag = null;
			else
				this.SendFlag = rs.getString("SendFlag").trim();

			this.SendDate = rs.getDate("SendDate");
			if( rs.getString("SwitchCom") == null )
				this.SwitchCom = null;
			else
				this.SwitchCom = rs.getString("SwitchCom").trim();

			this.SwitchDate = rs.getDate("SwitchDate");
			if( rs.getString("SwitchTime") == null )
				this.SwitchTime = null;
			else
				this.SwitchTime = rs.getString("SwitchTime").trim();

			if( rs.getString("Despatcher") == null )
				this.Despatcher = null;
			else
				this.Despatcher = rs.getString("Despatcher").trim();

			this.CNSDate = rs.getDate("CNSDate");
			this.ExpectRevertDate = rs.getDate("ExpectRevertDate");
			if( rs.getString("ExpectRevertTime") == null )
				this.ExpectRevertTime = null;
			else
				this.ExpectRevertTime = rs.getString("ExpectRevertTime").trim();

			if( rs.getString("ExpertFlag") == null )
				this.ExpertFlag = null;
			else
				this.ExpertFlag = rs.getString("ExpertFlag").trim();

			this.ReplyFDate = rs.getDate("ReplyFDate");
			this.DealFDate = rs.getDate("DealFDate");
			if( rs.getString("CNSOperator") == null )
				this.CNSOperator = null;
			else
				this.CNSOperator = rs.getString("CNSOperator").trim();

			this.RevertPeriod = rs.getInt("RevertPeriod");
			this.DealPeriod = rs.getInt("DealPeriod");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("AvaiFlag") == null )
				this.AvaiFlag = null;
			else
				this.AvaiFlag = rs.getString("AvaiFlag").trim();

			if( rs.getString("NotAvaliReason") == null )
				this.NotAvaliReason = null;
			else
				this.NotAvaliReason = rs.getString("NotAvaliReason").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLMainAsk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLMainAskSchema getSchema()
	{
		LLMainAskSchema aLLMainAskSchema = new LLMainAskSchema();
		aLLMainAskSchema.setSchema(this);
		return aLLMainAskSchema;
	}

	public LLMainAskDB getDB()
	{
		LLMainAskDB aDBOper = new LLMainAskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMainAsk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(LogNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AskMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogComp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogCompNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LogDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AskAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AnswerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AnswerMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SendDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SwitchCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SwitchDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SwitchTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Despatcher)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CNSDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExpectRevertDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpectRevertTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpertFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReplyFDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DealFDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CNSOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RevertPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DealPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvaiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NotAvaliReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMainAsk>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			LogState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AskMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LogerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			LogName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			LogComp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			LogCompNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			LogDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			LogTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PostCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AskAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AnswerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AnswerMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			SendFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			SendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			SwitchCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			SwitchDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			SwitchTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Despatcher = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			CNSDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ExpectRevertDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ExpectRevertTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ExpertFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ReplyFDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			DealFDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			CNSOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			RevertPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).intValue();
			DealPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AvaiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			NotAvaliReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskSchema";
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
		if (FCode.equalsIgnoreCase("LogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));
		}
		if (FCode.equalsIgnoreCase("LogState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogState));
		}
		if (FCode.equalsIgnoreCase("AskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("AskMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskMode));
		}
		if (FCode.equalsIgnoreCase("LogerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogerNo));
		}
		if (FCode.equalsIgnoreCase("LogName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogName));
		}
		if (FCode.equalsIgnoreCase("LogComp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogComp));
		}
		if (FCode.equalsIgnoreCase("LogCompNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogCompNo));
		}
		if (FCode.equalsIgnoreCase("LogDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLogDate()));
		}
		if (FCode.equalsIgnoreCase("LogTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogTime));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostCode));
		}
		if (FCode.equalsIgnoreCase("AskAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskAddress));
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Email));
		}
		if (FCode.equalsIgnoreCase("AnswerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AnswerType));
		}
		if (FCode.equalsIgnoreCase("AnswerMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AnswerMode));
		}
		if (FCode.equalsIgnoreCase("SendFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendFlag));
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
		}
		if (FCode.equalsIgnoreCase("SwitchCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SwitchCom));
		}
		if (FCode.equalsIgnoreCase("SwitchDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSwitchDate()));
		}
		if (FCode.equalsIgnoreCase("SwitchTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SwitchTime));
		}
		if (FCode.equalsIgnoreCase("Despatcher"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Despatcher));
		}
		if (FCode.equalsIgnoreCase("CNSDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCNSDate()));
		}
		if (FCode.equalsIgnoreCase("ExpectRevertDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getExpectRevertDate()));
		}
		if (FCode.equalsIgnoreCase("ExpectRevertTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpectRevertTime));
		}
		if (FCode.equalsIgnoreCase("ExpertFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpertFlag));
		}
		if (FCode.equalsIgnoreCase("ReplyFDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReplyFDate()));
		}
		if (FCode.equalsIgnoreCase("DealFDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDealFDate()));
		}
		if (FCode.equalsIgnoreCase("CNSOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CNSOperator));
		}
		if (FCode.equalsIgnoreCase("RevertPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RevertPeriod));
		}
		if (FCode.equalsIgnoreCase("DealPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealPeriod));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("AvaiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvaiFlag));
		}
		if (FCode.equalsIgnoreCase("NotAvaliReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NotAvaliReason));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
				strFieldValue = StrTool.GBKToUnicode(LogNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(LogState);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AskType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AskMode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LogerNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(LogName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(LogComp);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(LogCompNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLogDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(LogTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PostCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AskAddress);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Email);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AnswerType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AnswerMode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(SendFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(SwitchCom);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSwitchDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(SwitchTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Despatcher);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCNSDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExpectRevertDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ExpectRevertTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ExpertFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReplyFDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDealFDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(CNSOperator);
				break;
			case 32:
				strFieldValue = String.valueOf(RevertPeriod);
				break;
			case 33:
				strFieldValue = String.valueOf(DealPeriod);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AvaiFlag);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(NotAvaliReason);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("LogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogNo = FValue.trim();
			}
			else
				LogNo = null;
		}
		if (FCode.equalsIgnoreCase("LogState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogState = FValue.trim();
			}
			else
				LogState = null;
		}
		if (FCode.equalsIgnoreCase("AskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskType = FValue.trim();
			}
			else
				AskType = null;
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
		if (FCode.equalsIgnoreCase("AskMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskMode = FValue.trim();
			}
			else
				AskMode = null;
		}
		if (FCode.equalsIgnoreCase("LogerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogerNo = FValue.trim();
			}
			else
				LogerNo = null;
		}
		if (FCode.equalsIgnoreCase("LogName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogName = FValue.trim();
			}
			else
				LogName = null;
		}
		if (FCode.equalsIgnoreCase("LogComp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogComp = FValue.trim();
			}
			else
				LogComp = null;
		}
		if (FCode.equalsIgnoreCase("LogCompNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogCompNo = FValue.trim();
			}
			else
				LogCompNo = null;
		}
		if (FCode.equalsIgnoreCase("LogDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LogDate = fDate.getDate( FValue );
			}
			else
				LogDate = null;
		}
		if (FCode.equalsIgnoreCase("LogTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogTime = FValue.trim();
			}
			else
				LogTime = null;
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
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
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
		if (FCode.equalsIgnoreCase("AskAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskAddress = FValue.trim();
			}
			else
				AskAddress = null;
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
		if (FCode.equalsIgnoreCase("AnswerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AnswerType = FValue.trim();
			}
			else
				AnswerType = null;
		}
		if (FCode.equalsIgnoreCase("AnswerMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AnswerMode = FValue.trim();
			}
			else
				AnswerMode = null;
		}
		if (FCode.equalsIgnoreCase("SendFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendFlag = FValue.trim();
			}
			else
				SendFlag = null;
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SendDate = fDate.getDate( FValue );
			}
			else
				SendDate = null;
		}
		if (FCode.equalsIgnoreCase("SwitchCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SwitchCom = FValue.trim();
			}
			else
				SwitchCom = null;
		}
		if (FCode.equalsIgnoreCase("SwitchDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SwitchDate = fDate.getDate( FValue );
			}
			else
				SwitchDate = null;
		}
		if (FCode.equalsIgnoreCase("SwitchTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SwitchTime = FValue.trim();
			}
			else
				SwitchTime = null;
		}
		if (FCode.equalsIgnoreCase("Despatcher"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Despatcher = FValue.trim();
			}
			else
				Despatcher = null;
		}
		if (FCode.equalsIgnoreCase("CNSDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CNSDate = fDate.getDate( FValue );
			}
			else
				CNSDate = null;
		}
		if (FCode.equalsIgnoreCase("ExpectRevertDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ExpectRevertDate = fDate.getDate( FValue );
			}
			else
				ExpectRevertDate = null;
		}
		if (FCode.equalsIgnoreCase("ExpectRevertTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpectRevertTime = FValue.trim();
			}
			else
				ExpectRevertTime = null;
		}
		if (FCode.equalsIgnoreCase("ExpertFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpertFlag = FValue.trim();
			}
			else
				ExpertFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReplyFDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReplyFDate = fDate.getDate( FValue );
			}
			else
				ReplyFDate = null;
		}
		if (FCode.equalsIgnoreCase("DealFDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DealFDate = fDate.getDate( FValue );
			}
			else
				DealFDate = null;
		}
		if (FCode.equalsIgnoreCase("CNSOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CNSOperator = FValue.trim();
			}
			else
				CNSOperator = null;
		}
		if (FCode.equalsIgnoreCase("RevertPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RevertPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("DealPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DealPeriod = i;
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
		if (FCode.equalsIgnoreCase("AvaiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvaiFlag = FValue.trim();
			}
			else
				AvaiFlag = null;
		}
		if (FCode.equalsIgnoreCase("NotAvaliReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NotAvaliReason = FValue.trim();
			}
			else
				NotAvaliReason = null;
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLMainAskSchema other = (LLMainAskSchema)otherObject;
		return
			LogNo.equals(other.getLogNo())
			&& LogState.equals(other.getLogState())
			&& AskType.equals(other.getAskType())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& AskMode.equals(other.getAskMode())
			&& LogerNo.equals(other.getLogerNo())
			&& LogName.equals(other.getLogName())
			&& LogComp.equals(other.getLogComp())
			&& LogCompNo.equals(other.getLogCompNo())
			&& fDate.getString(LogDate).equals(other.getLogDate())
			&& LogTime.equals(other.getLogTime())
			&& Phone.equals(other.getPhone())
			&& Mobile.equals(other.getMobile())
			&& PostCode.equals(other.getPostCode())
			&& AskAddress.equals(other.getAskAddress())
			&& Email.equals(other.getEmail())
			&& AnswerType.equals(other.getAnswerType())
			&& AnswerMode.equals(other.getAnswerMode())
			&& SendFlag.equals(other.getSendFlag())
			&& fDate.getString(SendDate).equals(other.getSendDate())
			&& SwitchCom.equals(other.getSwitchCom())
			&& fDate.getString(SwitchDate).equals(other.getSwitchDate())
			&& SwitchTime.equals(other.getSwitchTime())
			&& Despatcher.equals(other.getDespatcher())
			&& fDate.getString(CNSDate).equals(other.getCNSDate())
			&& fDate.getString(ExpectRevertDate).equals(other.getExpectRevertDate())
			&& ExpectRevertTime.equals(other.getExpectRevertTime())
			&& ExpertFlag.equals(other.getExpertFlag())
			&& fDate.getString(ReplyFDate).equals(other.getReplyFDate())
			&& fDate.getString(DealFDate).equals(other.getDealFDate())
			&& CNSOperator.equals(other.getCNSOperator())
			&& RevertPeriod == other.getRevertPeriod()
			&& DealPeriod == other.getDealPeriod()
			&& Remark.equals(other.getRemark())
			&& AvaiFlag.equals(other.getAvaiFlag())
			&& NotAvaliReason.equals(other.getNotAvaliReason())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("LogNo") ) {
			return 0;
		}
		if( strFieldName.equals("LogState") ) {
			return 1;
		}
		if( strFieldName.equals("AskType") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 4;
		}
		if( strFieldName.equals("AskMode") ) {
			return 5;
		}
		if( strFieldName.equals("LogerNo") ) {
			return 6;
		}
		if( strFieldName.equals("LogName") ) {
			return 7;
		}
		if( strFieldName.equals("LogComp") ) {
			return 8;
		}
		if( strFieldName.equals("LogCompNo") ) {
			return 9;
		}
		if( strFieldName.equals("LogDate") ) {
			return 10;
		}
		if( strFieldName.equals("LogTime") ) {
			return 11;
		}
		if( strFieldName.equals("Phone") ) {
			return 12;
		}
		if( strFieldName.equals("Mobile") ) {
			return 13;
		}
		if( strFieldName.equals("PostCode") ) {
			return 14;
		}
		if( strFieldName.equals("AskAddress") ) {
			return 15;
		}
		if( strFieldName.equals("Email") ) {
			return 16;
		}
		if( strFieldName.equals("AnswerType") ) {
			return 17;
		}
		if( strFieldName.equals("AnswerMode") ) {
			return 18;
		}
		if( strFieldName.equals("SendFlag") ) {
			return 19;
		}
		if( strFieldName.equals("SendDate") ) {
			return 20;
		}
		if( strFieldName.equals("SwitchCom") ) {
			return 21;
		}
		if( strFieldName.equals("SwitchDate") ) {
			return 22;
		}
		if( strFieldName.equals("SwitchTime") ) {
			return 23;
		}
		if( strFieldName.equals("Despatcher") ) {
			return 24;
		}
		if( strFieldName.equals("CNSDate") ) {
			return 25;
		}
		if( strFieldName.equals("ExpectRevertDate") ) {
			return 26;
		}
		if( strFieldName.equals("ExpectRevertTime") ) {
			return 27;
		}
		if( strFieldName.equals("ExpertFlag") ) {
			return 28;
		}
		if( strFieldName.equals("ReplyFDate") ) {
			return 29;
		}
		if( strFieldName.equals("DealFDate") ) {
			return 30;
		}
		if( strFieldName.equals("CNSOperator") ) {
			return 31;
		}
		if( strFieldName.equals("RevertPeriod") ) {
			return 32;
		}
		if( strFieldName.equals("DealPeriod") ) {
			return 33;
		}
		if( strFieldName.equals("Remark") ) {
			return 34;
		}
		if( strFieldName.equals("AvaiFlag") ) {
			return 35;
		}
		if( strFieldName.equals("NotAvaliReason") ) {
			return 36;
		}
		if( strFieldName.equals("Operator") ) {
			return 37;
		}
		if( strFieldName.equals("MngCom") ) {
			return 38;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 39;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 40;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 41;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 42;
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
				strFieldName = "LogNo";
				break;
			case 1:
				strFieldName = "LogState";
				break;
			case 2:
				strFieldName = "AskType";
				break;
			case 3:
				strFieldName = "OtherNo";
				break;
			case 4:
				strFieldName = "OtherNoType";
				break;
			case 5:
				strFieldName = "AskMode";
				break;
			case 6:
				strFieldName = "LogerNo";
				break;
			case 7:
				strFieldName = "LogName";
				break;
			case 8:
				strFieldName = "LogComp";
				break;
			case 9:
				strFieldName = "LogCompNo";
				break;
			case 10:
				strFieldName = "LogDate";
				break;
			case 11:
				strFieldName = "LogTime";
				break;
			case 12:
				strFieldName = "Phone";
				break;
			case 13:
				strFieldName = "Mobile";
				break;
			case 14:
				strFieldName = "PostCode";
				break;
			case 15:
				strFieldName = "AskAddress";
				break;
			case 16:
				strFieldName = "Email";
				break;
			case 17:
				strFieldName = "AnswerType";
				break;
			case 18:
				strFieldName = "AnswerMode";
				break;
			case 19:
				strFieldName = "SendFlag";
				break;
			case 20:
				strFieldName = "SendDate";
				break;
			case 21:
				strFieldName = "SwitchCom";
				break;
			case 22:
				strFieldName = "SwitchDate";
				break;
			case 23:
				strFieldName = "SwitchTime";
				break;
			case 24:
				strFieldName = "Despatcher";
				break;
			case 25:
				strFieldName = "CNSDate";
				break;
			case 26:
				strFieldName = "ExpectRevertDate";
				break;
			case 27:
				strFieldName = "ExpectRevertTime";
				break;
			case 28:
				strFieldName = "ExpertFlag";
				break;
			case 29:
				strFieldName = "ReplyFDate";
				break;
			case 30:
				strFieldName = "DealFDate";
				break;
			case 31:
				strFieldName = "CNSOperator";
				break;
			case 32:
				strFieldName = "RevertPeriod";
				break;
			case 33:
				strFieldName = "DealPeriod";
				break;
			case 34:
				strFieldName = "Remark";
				break;
			case 35:
				strFieldName = "AvaiFlag";
				break;
			case 36:
				strFieldName = "NotAvaliReason";
				break;
			case 37:
				strFieldName = "Operator";
				break;
			case 38:
				strFieldName = "MngCom";
				break;
			case 39:
				strFieldName = "MakeDate";
				break;
			case 40:
				strFieldName = "MakeTime";
				break;
			case 41:
				strFieldName = "ModifyDate";
				break;
			case 42:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("LogNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogComp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogCompNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LogTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Email") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AnswerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AnswerMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SwitchCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SwitchDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SwitchTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Despatcher") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CNSDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ExpectRevertDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ExpectRevertTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpertFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyFDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DealFDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CNSOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RevertPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DealPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvaiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NotAvaliReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_INT;
				break;
			case 33:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
