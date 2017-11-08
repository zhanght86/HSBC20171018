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
import com.sinosoft.lis.db.LLSubReportDB;

/*
 * <p>ClassName: LLSubReportSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLSubReportSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLSubReportSchema.class);
	// @Field
	/** 分报案号 */
	private String SubRptNo;
	/** 关联客户号码 */
	private String CustomerNo;
	/** 关联客户的名称 */
	private String CustomerName;
	/** 关联客户类型 */
	private String CustomerType;
	/** 事件主题 */
	private String AccSubject;
	/** 事故类型 */
	private String AccidentType;
	/** 其他出险日期 */
	private Date AccDate;
	/** 终止日期 */
	private Date AccEndDate;
	/** 事故描述 */
	private String AccDesc;
	/** 事故者状况 */
	private String CustSituation;
	/** 事故地点 */
	private String AccPlace;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 入院日期 */
	private Date InHospitalDate;
	/** 出院日期 */
	private Date OutHospitalDate;
	/** 备注 */
	private String Remark;
	/** 重大事件标志 */
	private String SeriousGrade;
	/** 调查报告标志 */
	private String SurveyFlag;
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
	/** 首诊日 */
	private Date FirstDiaDate;
	/** 医院级别 */
	private String HosGrade;
	/** 所在地 */
	private String LocalPlace;
	/** 医院联系电话 */
	private String HosTel;
	/** 死亡日期 */
	private Date DieDate;
	/** 事件原因 */
	private String AccCause;
	/** Vip标志 */
	private String VIPFlag;
	/** 出险细节 */
	private String AccidentDetail;
	/** 治疗情况 */
	private String CureDesc;
	/** 发起呈报标志 */
	private String SubmitFlag;
	/** 提起慰问标志 */
	private String CondoleFlag;
	/** 死亡标志 */
	private String DieFlag;
	/** 出险结果1 */
	private String AccResult1;
	/** 出险结果2 */
	private String AccResult2;
	/** 客户序号 */
	private int SeqNo;
	/** 关联客户性别 */
	private String Sex;
	/** 关联客户证件类型 */
	private String IDType;
	/** 关联客户证件号码 */
	private String IDNo;
	/** 医疗出险日期 */
	private Date MedAccDate;
	/** 收款人姓名 */
	private String BnfName;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 受益人证件号码 */
	private String BnfIDNo;
	/** 受益人与被保人关系 */
	private String RelationToInsured;
	/** 保险金领取方式 */
	private String CaseGetMode;
	/** 受益人证件类型 */
	private String BnfIDType;
	/** 受益人出生日期 */
	private Date Birthday;
	/** 受益人性别 */
	private String BnfSex;
	/** 受益人银行账户名 */
	private String BnfAccName;
	/** 投保人客户号 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 年龄 */
	private int Age;
	/** 员工号 */
	private String EmpNo;
	/** 重大案件呈报状态 */
	private String AccGradeState;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 60;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLSubReportSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SubRptNo";
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
		LLSubReportSchema cloned = (LLSubReportSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSubRptNo()
	{
		return SubRptNo;
	}
	public void setSubRptNo(String aSubRptNo)
	{
		if(aSubRptNo!=null && aSubRptNo.length()>20)
			throw new IllegalArgumentException("分报案号SubRptNo值"+aSubRptNo+"的长度"+aSubRptNo.length()+"大于最大值20");
		SubRptNo = aSubRptNo;
	}
	/**
	* 该次咨询所关联的客户号码<p>
	* 包括被保险人；投保人；连带被保险人；被保险人的配偶；被保险人的子女；被保险人的父母等等相关连的各种类型的人员。
	*/
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("关联客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>200)
			throw new IllegalArgumentException("关联客户的名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值200");
		CustomerName = aCustomerName;
	}
	/**
	* 【不用】<p>
	* 0-主被保险人；<p>
	* 1-投保人；<p>
	* 2-连带被保险人；<p>
	* 3-主被保人配偶；<p>
	* 4-主被保险人子女；<p>
	* 5-主被保险人父母
	*/
	public String getCustomerType()
	{
		return CustomerType;
	}
	public void setCustomerType(String aCustomerType)
	{
		if(aCustomerType!=null && aCustomerType.length()>20)
			throw new IllegalArgumentException("关联客户类型CustomerType值"+aCustomerType+"的长度"+aCustomerType.length()+"大于最大值20");
		CustomerType = aCustomerType;
	}
	/**
	* 【不用】
	*/
	public String getAccSubject()
	{
		return AccSubject;
	}
	public void setAccSubject(String aAccSubject)
	{
		if(aAccSubject!=null && aAccSubject.length()>600)
			throw new IllegalArgumentException("事件主题AccSubject值"+aAccSubject+"的长度"+aAccSubject.length()+"大于最大值600");
		AccSubject = aAccSubject;
	}
	/**
	* 同出险原因<p>
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
			throw new IllegalArgumentException("事故类型AccidentType值"+aAccidentType+"的长度"+aAccidentType.length()+"大于最大值6");
		AccidentType = aAccidentType;
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
	* 默认为出险日期
	*/
	public String getAccEndDate()
	{
		if( AccEndDate != null )
			return fDate.getString(AccEndDate);
		else
			return null;
	}
	public void setAccEndDate(Date aAccEndDate)
	{
		AccEndDate = aAccEndDate;
	}
	public void setAccEndDate(String aAccEndDate)
	{
		if (aAccEndDate != null && !aAccEndDate.equals("") )
		{
			AccEndDate = fDate.getDate( aAccEndDate );
		}
		else
			AccEndDate = null;
	}

	public String getAccDesc()
	{
		return AccDesc;
	}
	public void setAccDesc(String aAccDesc)
	{
		if(aAccDesc!=null && aAccDesc.length()>1000)
			throw new IllegalArgumentException("事故描述AccDesc值"+aAccDesc+"的长度"+aAccDesc.length()+"大于最大值1000");
		AccDesc = aAccDesc;
	}
	/**
	* 【不用】
	*/
	public String getCustSituation()
	{
		return CustSituation;
	}
	public void setCustSituation(String aCustSituation)
	{
		if(aCustSituation!=null && aCustSituation.length()>2)
			throw new IllegalArgumentException("事故者状况CustSituation值"+aCustSituation+"的长度"+aCustSituation.length()+"大于最大值2");
		CustSituation = aCustSituation;
	}
	/**
	* 【不用】
	*/
	public String getAccPlace()
	{
		return AccPlace;
	}
	public void setAccPlace(String aAccPlace)
	{
		if(aAccPlace!=null && aAccPlace.length()>100)
			throw new IllegalArgumentException("事故地点AccPlace值"+aAccPlace+"的长度"+aAccPlace.length()+"大于最大值100");
		AccPlace = aAccPlace;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>10)
			throw new IllegalArgumentException("医院代码HospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值10");
		HospitalCode = aHospitalCode;
	}
	/**
	* 【不用】
	*/
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		if(aHospitalName!=null && aHospitalName.length()>60)
			throw new IllegalArgumentException("医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值60");
		HospitalName = aHospitalName;
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
	/**
	* 【不用】<p>
	* 0 一般事件<p>
	* 1 重大事件
	*/
	public String getSeriousGrade()
	{
		return SeriousGrade;
	}
	public void setSeriousGrade(String aSeriousGrade)
	{
		if(aSeriousGrade!=null && aSeriousGrade.length()>6)
			throw new IllegalArgumentException("重大事件标志SeriousGrade值"+aSeriousGrade+"的长度"+aSeriousGrade.length()+"大于最大值6");
		SeriousGrade = aSeriousGrade;
	}
	/**
	* 【不用】<p>
	* 包括：<p>
	* 有<p>
	* 没有
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
	* 【不用】
	*/
	public String getFirstDiaDate()
	{
		if( FirstDiaDate != null )
			return fDate.getString(FirstDiaDate);
		else
			return null;
	}
	public void setFirstDiaDate(Date aFirstDiaDate)
	{
		FirstDiaDate = aFirstDiaDate;
	}
	public void setFirstDiaDate(String aFirstDiaDate)
	{
		if (aFirstDiaDate != null && !aFirstDiaDate.equals("") )
		{
			FirstDiaDate = fDate.getDate( aFirstDiaDate );
		}
		else
			FirstDiaDate = null;
	}

	/**
	* 【不用】
	*/
	public String getHosGrade()
	{
		return HosGrade;
	}
	public void setHosGrade(String aHosGrade)
	{
		if(aHosGrade!=null && aHosGrade.length()>6)
			throw new IllegalArgumentException("医院级别HosGrade值"+aHosGrade+"的长度"+aHosGrade.length()+"大于最大值6");
		HosGrade = aHosGrade;
	}
	/**
	* 【不用】
	*/
	public String getLocalPlace()
	{
		return LocalPlace;
	}
	public void setLocalPlace(String aLocalPlace)
	{
		if(aLocalPlace!=null && aLocalPlace.length()>200)
			throw new IllegalArgumentException("所在地LocalPlace值"+aLocalPlace+"的长度"+aLocalPlace.length()+"大于最大值200");
		LocalPlace = aLocalPlace;
	}
	/**
	* 【不用】
	*/
	public String getHosTel()
	{
		return HosTel;
	}
	public void setHosTel(String aHosTel)
	{
		if(aHosTel!=null && aHosTel.length()>20)
			throw new IllegalArgumentException("医院联系电话HosTel值"+aHosTel+"的长度"+aHosTel.length()+"大于最大值20");
		HosTel = aHosTel;
	}
	public String getDieDate()
	{
		if( DieDate != null )
			return fDate.getString(DieDate);
		else
			return null;
	}
	public void setDieDate(Date aDieDate)
	{
		DieDate = aDieDate;
	}
	public void setDieDate(String aDieDate)
	{
		if (aDieDate != null && !aDieDate.equals("") )
		{
			DieDate = fDate.getDate( aDieDate );
		}
		else
			DieDate = null;
	}

	/**
	* 【不用】<p>
	* 1 疾病<p>
	* 2 意外<p>
	* 可扩展
	*/
	public String getAccCause()
	{
		return AccCause;
	}
	public void setAccCause(String aAccCause)
	{
		if(aAccCause!=null && aAccCause.length()>1)
			throw new IllegalArgumentException("事件原因AccCause值"+aAccCause+"的长度"+aAccCause.length()+"大于最大值1");
		AccCause = aAccCause;
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
	* ICD10编码
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
	* 0或Null没发慰问<p>
	* 1发慰问
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
	* 0或Null未死亡<p>
	* 1死亡
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

	/**
	* 0:男<p>
	* 1:女
	*/
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		if(aSex!=null && aSex.length()>1)
			throw new IllegalArgumentException("关联客户性别Sex值"+aSex+"的长度"+aSex.length()+"大于最大值1");
		Sex = aSex;
	}
	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>2)
			throw new IllegalArgumentException("关联客户证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值2");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>30)
			throw new IllegalArgumentException("关联客户证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值30");
		IDNo = aIDNo;
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
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人客户号AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>200)
			throw new IllegalArgumentException("投保人名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值200");
		AppntName = aAppntName;
	}
	public int getAge()
	{
		return Age;
	}
	public void setAge(int aAge)
	{
		Age = aAge;
	}
	public void setAge(String aAge)
	{
		if (aAge != null && !aAge.equals(""))
		{
			Integer tInteger = new Integer(aAge);
			int i = tInteger.intValue();
			Age = i;
		}
	}

	public String getEmpNo()
	{
		return EmpNo;
	}
	public void setEmpNo(String aEmpNo)
	{
		if(aEmpNo!=null && aEmpNo.length()>30)
			throw new IllegalArgumentException("员工号EmpNo值"+aEmpNo+"的长度"+aEmpNo.length()+"大于最大值30");
		EmpNo = aEmpNo;
	}
	/**
	* 0-未呈报，1-已呈报，2-已回复
	*/
	public String getAccGradeState()
	{
		return AccGradeState;
	}
	public void setAccGradeState(String aAccGradeState)
	{
		if(aAccGradeState!=null && aAccGradeState.length()>2)
			throw new IllegalArgumentException("重大案件呈报状态AccGradeState值"+aAccGradeState+"的长度"+aAccGradeState.length()+"大于最大值2");
		AccGradeState = aAccGradeState;
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
	* 使用另外一个 LLSubReportSchema 对象给 Schema 赋值
	* @param: aLLSubReportSchema LLSubReportSchema
	**/
	public void setSchema(LLSubReportSchema aLLSubReportSchema)
	{
		this.SubRptNo = aLLSubReportSchema.getSubRptNo();
		this.CustomerNo = aLLSubReportSchema.getCustomerNo();
		this.CustomerName = aLLSubReportSchema.getCustomerName();
		this.CustomerType = aLLSubReportSchema.getCustomerType();
		this.AccSubject = aLLSubReportSchema.getAccSubject();
		this.AccidentType = aLLSubReportSchema.getAccidentType();
		this.AccDate = fDate.getDate( aLLSubReportSchema.getAccDate());
		this.AccEndDate = fDate.getDate( aLLSubReportSchema.getAccEndDate());
		this.AccDesc = aLLSubReportSchema.getAccDesc();
		this.CustSituation = aLLSubReportSchema.getCustSituation();
		this.AccPlace = aLLSubReportSchema.getAccPlace();
		this.HospitalCode = aLLSubReportSchema.getHospitalCode();
		this.HospitalName = aLLSubReportSchema.getHospitalName();
		this.InHospitalDate = fDate.getDate( aLLSubReportSchema.getInHospitalDate());
		this.OutHospitalDate = fDate.getDate( aLLSubReportSchema.getOutHospitalDate());
		this.Remark = aLLSubReportSchema.getRemark();
		this.SeriousGrade = aLLSubReportSchema.getSeriousGrade();
		this.SurveyFlag = aLLSubReportSchema.getSurveyFlag();
		this.Operator = aLLSubReportSchema.getOperator();
		this.MngCom = aLLSubReportSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLSubReportSchema.getMakeDate());
		this.MakeTime = aLLSubReportSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLSubReportSchema.getModifyDate());
		this.ModifyTime = aLLSubReportSchema.getModifyTime();
		this.FirstDiaDate = fDate.getDate( aLLSubReportSchema.getFirstDiaDate());
		this.HosGrade = aLLSubReportSchema.getHosGrade();
		this.LocalPlace = aLLSubReportSchema.getLocalPlace();
		this.HosTel = aLLSubReportSchema.getHosTel();
		this.DieDate = fDate.getDate( aLLSubReportSchema.getDieDate());
		this.AccCause = aLLSubReportSchema.getAccCause();
		this.VIPFlag = aLLSubReportSchema.getVIPFlag();
		this.AccidentDetail = aLLSubReportSchema.getAccidentDetail();
		this.CureDesc = aLLSubReportSchema.getCureDesc();
		this.SubmitFlag = aLLSubReportSchema.getSubmitFlag();
		this.CondoleFlag = aLLSubReportSchema.getCondoleFlag();
		this.DieFlag = aLLSubReportSchema.getDieFlag();
		this.AccResult1 = aLLSubReportSchema.getAccResult1();
		this.AccResult2 = aLLSubReportSchema.getAccResult2();
		this.SeqNo = aLLSubReportSchema.getSeqNo();
		this.Sex = aLLSubReportSchema.getSex();
		this.IDType = aLLSubReportSchema.getIDType();
		this.IDNo = aLLSubReportSchema.getIDNo();
		this.MedAccDate = fDate.getDate( aLLSubReportSchema.getMedAccDate());
		this.BnfName = aLLSubReportSchema.getBnfName();
		this.BankCode = aLLSubReportSchema.getBankCode();
		this.BankAccNo = aLLSubReportSchema.getBankAccNo();
		this.BnfIDNo = aLLSubReportSchema.getBnfIDNo();
		this.RelationToInsured = aLLSubReportSchema.getRelationToInsured();
		this.CaseGetMode = aLLSubReportSchema.getCaseGetMode();
		this.BnfIDType = aLLSubReportSchema.getBnfIDType();
		this.Birthday = fDate.getDate( aLLSubReportSchema.getBirthday());
		this.BnfSex = aLLSubReportSchema.getBnfSex();
		this.BnfAccName = aLLSubReportSchema.getBnfAccName();
		this.AppntNo = aLLSubReportSchema.getAppntNo();
		this.AppntName = aLLSubReportSchema.getAppntName();
		this.Age = aLLSubReportSchema.getAge();
		this.EmpNo = aLLSubReportSchema.getEmpNo();
		this.AccGradeState = aLLSubReportSchema.getAccGradeState();
		this.ComCode = aLLSubReportSchema.getComCode();
		this.ModifyOperator = aLLSubReportSchema.getModifyOperator();
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
			if( rs.getString("SubRptNo") == null )
				this.SubRptNo = null;
			else
				this.SubRptNo = rs.getString("SubRptNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("CustomerType") == null )
				this.CustomerType = null;
			else
				this.CustomerType = rs.getString("CustomerType").trim();

			if( rs.getString("AccSubject") == null )
				this.AccSubject = null;
			else
				this.AccSubject = rs.getString("AccSubject").trim();

			if( rs.getString("AccidentType") == null )
				this.AccidentType = null;
			else
				this.AccidentType = rs.getString("AccidentType").trim();

			this.AccDate = rs.getDate("AccDate");
			this.AccEndDate = rs.getDate("AccEndDate");
			if( rs.getString("AccDesc") == null )
				this.AccDesc = null;
			else
				this.AccDesc = rs.getString("AccDesc").trim();

			if( rs.getString("CustSituation") == null )
				this.CustSituation = null;
			else
				this.CustSituation = rs.getString("CustSituation").trim();

			if( rs.getString("AccPlace") == null )
				this.AccPlace = null;
			else
				this.AccPlace = rs.getString("AccPlace").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			this.InHospitalDate = rs.getDate("InHospitalDate");
			this.OutHospitalDate = rs.getDate("OutHospitalDate");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("SeriousGrade") == null )
				this.SeriousGrade = null;
			else
				this.SeriousGrade = rs.getString("SeriousGrade").trim();

			if( rs.getString("SurveyFlag") == null )
				this.SurveyFlag = null;
			else
				this.SurveyFlag = rs.getString("SurveyFlag").trim();

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

			this.FirstDiaDate = rs.getDate("FirstDiaDate");
			if( rs.getString("HosGrade") == null )
				this.HosGrade = null;
			else
				this.HosGrade = rs.getString("HosGrade").trim();

			if( rs.getString("LocalPlace") == null )
				this.LocalPlace = null;
			else
				this.LocalPlace = rs.getString("LocalPlace").trim();

			if( rs.getString("HosTel") == null )
				this.HosTel = null;
			else
				this.HosTel = rs.getString("HosTel").trim();

			this.DieDate = rs.getDate("DieDate");
			if( rs.getString("AccCause") == null )
				this.AccCause = null;
			else
				this.AccCause = rs.getString("AccCause").trim();

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

			if( rs.getString("SubmitFlag") == null )
				this.SubmitFlag = null;
			else
				this.SubmitFlag = rs.getString("SubmitFlag").trim();

			if( rs.getString("CondoleFlag") == null )
				this.CondoleFlag = null;
			else
				this.CondoleFlag = rs.getString("CondoleFlag").trim();

			if( rs.getString("DieFlag") == null )
				this.DieFlag = null;
			else
				this.DieFlag = rs.getString("DieFlag").trim();

			if( rs.getString("AccResult1") == null )
				this.AccResult1 = null;
			else
				this.AccResult1 = rs.getString("AccResult1").trim();

			if( rs.getString("AccResult2") == null )
				this.AccResult2 = null;
			else
				this.AccResult2 = rs.getString("AccResult2").trim();

			this.SeqNo = rs.getInt("SeqNo");
			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.MedAccDate = rs.getDate("MedAccDate");
			if( rs.getString("BnfName") == null )
				this.BnfName = null;
			else
				this.BnfName = rs.getString("BnfName").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("BnfIDNo") == null )
				this.BnfIDNo = null;
			else
				this.BnfIDNo = rs.getString("BnfIDNo").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("CaseGetMode") == null )
				this.CaseGetMode = null;
			else
				this.CaseGetMode = rs.getString("CaseGetMode").trim();

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

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.Age = rs.getInt("Age");
			if( rs.getString("EmpNo") == null )
				this.EmpNo = null;
			else
				this.EmpNo = rs.getString("EmpNo").trim();

			if( rs.getString("AccGradeState") == null )
				this.AccGradeState = null;
			else
				this.AccGradeState = rs.getString("AccGradeState").trim();

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
			logger.debug("数据库中的LLSubReport表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSubReportSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLSubReportSchema getSchema()
	{
		LLSubReportSchema aLLSubReportSchema = new LLSubReportSchema();
		aLLSubReportSchema.setSchema(this);
		return aLLSubReportSchema;
	}

	public LLSubReportDB getDB()
	{
		LLSubReportDB aDBOper = new LLSubReportDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLSubReport描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubRptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccSubject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustSituation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccPlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SeriousGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstDiaDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LocalPlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosTel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DieDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCause)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CureDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubmitFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CondoleFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DieFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SeqNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MedAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Age));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EmpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccGradeState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLSubReport>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubRptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccSubject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccidentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			AccEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			AccDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CustSituation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccPlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			OutHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SeriousGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			SurveyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			FirstDiaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			HosGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			LocalPlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			HosTel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			DieDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			AccCause = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			VIPFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			AccidentDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			CureDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			SubmitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			CondoleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			DieFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			AccResult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SeqNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).intValue();
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MedAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			BnfName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			BnfIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			CaseGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			BnfIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			BnfSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			BnfAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			Age= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).intValue();
			EmpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			AccGradeState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSubReportSchema";
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
		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRptNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerType));
		}
		if (FCode.equalsIgnoreCase("AccSubject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccSubject));
		}
		if (FCode.equalsIgnoreCase("AccidentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentType));
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
		}
		if (FCode.equalsIgnoreCase("AccEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccEndDate()));
		}
		if (FCode.equalsIgnoreCase("AccDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccDesc));
		}
		if (FCode.equalsIgnoreCase("CustSituation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustSituation));
		}
		if (FCode.equalsIgnoreCase("AccPlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccPlace));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("InHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("OutHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("SeriousGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeriousGrade));
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyFlag));
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
		if (FCode.equalsIgnoreCase("FirstDiaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstDiaDate()));
		}
		if (FCode.equalsIgnoreCase("HosGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosGrade));
		}
		if (FCode.equalsIgnoreCase("LocalPlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LocalPlace));
		}
		if (FCode.equalsIgnoreCase("HosTel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosTel));
		}
		if (FCode.equalsIgnoreCase("DieDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDieDate()));
		}
		if (FCode.equalsIgnoreCase("AccCause"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCause));
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
		if (FCode.equalsIgnoreCase("SubmitFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubmitFlag));
		}
		if (FCode.equalsIgnoreCase("CondoleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CondoleFlag));
		}
		if (FCode.equalsIgnoreCase("DieFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DieFlag));
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult1));
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult2));
		}
		if (FCode.equalsIgnoreCase("SeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeqNo));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("MedAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMedAccDate()));
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfName));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("BnfIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfIDNo));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseGetMode));
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Age));
		}
		if (FCode.equalsIgnoreCase("EmpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmpNo));
		}
		if (FCode.equalsIgnoreCase("AccGradeState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccGradeState));
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
				strFieldValue = StrTool.GBKToUnicode(SubRptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccSubject);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccidentType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccEndDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccDesc);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CustSituation);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccPlace);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SeriousGrade);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(SurveyFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstDiaDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(HosGrade);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(LocalPlace);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(HosTel);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDieDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AccCause);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(VIPFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(AccidentDetail);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(CureDesc);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SubmitFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(CondoleFlag);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(DieFlag);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(AccResult1);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 38:
				strFieldValue = String.valueOf(SeqNo);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMedAccDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(BnfName);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(BnfIDNo);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(CaseGetMode);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(BnfIDType);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(BnfSex);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(BnfAccName);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 55:
				strFieldValue = String.valueOf(Age);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(EmpNo);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(AccGradeState);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 59:
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

		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRptNo = FValue.trim();
			}
			else
				SubRptNo = null;
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
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerType = FValue.trim();
			}
			else
				CustomerType = null;
		}
		if (FCode.equalsIgnoreCase("AccSubject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccSubject = FValue.trim();
			}
			else
				AccSubject = null;
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
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccDate = fDate.getDate( FValue );
			}
			else
				AccDate = null;
		}
		if (FCode.equalsIgnoreCase("AccEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccEndDate = fDate.getDate( FValue );
			}
			else
				AccEndDate = null;
		}
		if (FCode.equalsIgnoreCase("AccDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccDesc = FValue.trim();
			}
			else
				AccDesc = null;
		}
		if (FCode.equalsIgnoreCase("CustSituation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustSituation = FValue.trim();
			}
			else
				CustSituation = null;
		}
		if (FCode.equalsIgnoreCase("AccPlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccPlace = FValue.trim();
			}
			else
				AccPlace = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("SeriousGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SeriousGrade = FValue.trim();
			}
			else
				SeriousGrade = null;
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
		if (FCode.equalsIgnoreCase("FirstDiaDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstDiaDate = fDate.getDate( FValue );
			}
			else
				FirstDiaDate = null;
		}
		if (FCode.equalsIgnoreCase("HosGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosGrade = FValue.trim();
			}
			else
				HosGrade = null;
		}
		if (FCode.equalsIgnoreCase("LocalPlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LocalPlace = FValue.trim();
			}
			else
				LocalPlace = null;
		}
		if (FCode.equalsIgnoreCase("HosTel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosTel = FValue.trim();
			}
			else
				HosTel = null;
		}
		if (FCode.equalsIgnoreCase("DieDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DieDate = fDate.getDate( FValue );
			}
			else
				DieDate = null;
		}
		if (FCode.equalsIgnoreCase("AccCause"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCause = FValue.trim();
			}
			else
				AccCause = null;
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
		if (FCode.equalsIgnoreCase("DieFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DieFlag = FValue.trim();
			}
			else
				DieFlag = null;
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
		if (FCode.equalsIgnoreCase("SeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SeqNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
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
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseGetMode = FValue.trim();
			}
			else
				CaseGetMode = null;
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
		if (FCode.equalsIgnoreCase("Age"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Age = i;
			}
		}
		if (FCode.equalsIgnoreCase("EmpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EmpNo = FValue.trim();
			}
			else
				EmpNo = null;
		}
		if (FCode.equalsIgnoreCase("AccGradeState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccGradeState = FValue.trim();
			}
			else
				AccGradeState = null;
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
		LLSubReportSchema other = (LLSubReportSchema)otherObject;
		return
			SubRptNo.equals(other.getSubRptNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& CustomerType.equals(other.getCustomerType())
			&& AccSubject.equals(other.getAccSubject())
			&& AccidentType.equals(other.getAccidentType())
			&& fDate.getString(AccDate).equals(other.getAccDate())
			&& fDate.getString(AccEndDate).equals(other.getAccEndDate())
			&& AccDesc.equals(other.getAccDesc())
			&& CustSituation.equals(other.getCustSituation())
			&& AccPlace.equals(other.getAccPlace())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& fDate.getString(InHospitalDate).equals(other.getInHospitalDate())
			&& fDate.getString(OutHospitalDate).equals(other.getOutHospitalDate())
			&& Remark.equals(other.getRemark())
			&& SeriousGrade.equals(other.getSeriousGrade())
			&& SurveyFlag.equals(other.getSurveyFlag())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(FirstDiaDate).equals(other.getFirstDiaDate())
			&& HosGrade.equals(other.getHosGrade())
			&& LocalPlace.equals(other.getLocalPlace())
			&& HosTel.equals(other.getHosTel())
			&& fDate.getString(DieDate).equals(other.getDieDate())
			&& AccCause.equals(other.getAccCause())
			&& VIPFlag.equals(other.getVIPFlag())
			&& AccidentDetail.equals(other.getAccidentDetail())
			&& CureDesc.equals(other.getCureDesc())
			&& SubmitFlag.equals(other.getSubmitFlag())
			&& CondoleFlag.equals(other.getCondoleFlag())
			&& DieFlag.equals(other.getDieFlag())
			&& AccResult1.equals(other.getAccResult1())
			&& AccResult2.equals(other.getAccResult2())
			&& SeqNo == other.getSeqNo()
			&& Sex.equals(other.getSex())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& fDate.getString(MedAccDate).equals(other.getMedAccDate())
			&& BnfName.equals(other.getBnfName())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& BnfIDNo.equals(other.getBnfIDNo())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& CaseGetMode.equals(other.getCaseGetMode())
			&& BnfIDType.equals(other.getBnfIDType())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& BnfSex.equals(other.getBnfSex())
			&& BnfAccName.equals(other.getBnfAccName())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& Age == other.getAge()
			&& EmpNo.equals(other.getEmpNo())
			&& AccGradeState.equals(other.getAccGradeState())
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
		if( strFieldName.equals("SubRptNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerType") ) {
			return 3;
		}
		if( strFieldName.equals("AccSubject") ) {
			return 4;
		}
		if( strFieldName.equals("AccidentType") ) {
			return 5;
		}
		if( strFieldName.equals("AccDate") ) {
			return 6;
		}
		if( strFieldName.equals("AccEndDate") ) {
			return 7;
		}
		if( strFieldName.equals("AccDesc") ) {
			return 8;
		}
		if( strFieldName.equals("CustSituation") ) {
			return 9;
		}
		if( strFieldName.equals("AccPlace") ) {
			return 10;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 11;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 12;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return 13;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
		}
		if( strFieldName.equals("SeriousGrade") ) {
			return 16;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("MngCom") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("FirstDiaDate") ) {
			return 24;
		}
		if( strFieldName.equals("HosGrade") ) {
			return 25;
		}
		if( strFieldName.equals("LocalPlace") ) {
			return 26;
		}
		if( strFieldName.equals("HosTel") ) {
			return 27;
		}
		if( strFieldName.equals("DieDate") ) {
			return 28;
		}
		if( strFieldName.equals("AccCause") ) {
			return 29;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return 30;
		}
		if( strFieldName.equals("AccidentDetail") ) {
			return 31;
		}
		if( strFieldName.equals("CureDesc") ) {
			return 32;
		}
		if( strFieldName.equals("SubmitFlag") ) {
			return 33;
		}
		if( strFieldName.equals("CondoleFlag") ) {
			return 34;
		}
		if( strFieldName.equals("DieFlag") ) {
			return 35;
		}
		if( strFieldName.equals("AccResult1") ) {
			return 36;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 37;
		}
		if( strFieldName.equals("SeqNo") ) {
			return 38;
		}
		if( strFieldName.equals("Sex") ) {
			return 39;
		}
		if( strFieldName.equals("IDType") ) {
			return 40;
		}
		if( strFieldName.equals("IDNo") ) {
			return 41;
		}
		if( strFieldName.equals("MedAccDate") ) {
			return 42;
		}
		if( strFieldName.equals("BnfName") ) {
			return 43;
		}
		if( strFieldName.equals("BankCode") ) {
			return 44;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 45;
		}
		if( strFieldName.equals("BnfIDNo") ) {
			return 46;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 47;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return 48;
		}
		if( strFieldName.equals("BnfIDType") ) {
			return 49;
		}
		if( strFieldName.equals("Birthday") ) {
			return 50;
		}
		if( strFieldName.equals("BnfSex") ) {
			return 51;
		}
		if( strFieldName.equals("BnfAccName") ) {
			return 52;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 53;
		}
		if( strFieldName.equals("AppntName") ) {
			return 54;
		}
		if( strFieldName.equals("Age") ) {
			return 55;
		}
		if( strFieldName.equals("EmpNo") ) {
			return 56;
		}
		if( strFieldName.equals("AccGradeState") ) {
			return 57;
		}
		if( strFieldName.equals("ComCode") ) {
			return 58;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 59;
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
				strFieldName = "SubRptNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "CustomerName";
				break;
			case 3:
				strFieldName = "CustomerType";
				break;
			case 4:
				strFieldName = "AccSubject";
				break;
			case 5:
				strFieldName = "AccidentType";
				break;
			case 6:
				strFieldName = "AccDate";
				break;
			case 7:
				strFieldName = "AccEndDate";
				break;
			case 8:
				strFieldName = "AccDesc";
				break;
			case 9:
				strFieldName = "CustSituation";
				break;
			case 10:
				strFieldName = "AccPlace";
				break;
			case 11:
				strFieldName = "HospitalCode";
				break;
			case 12:
				strFieldName = "HospitalName";
				break;
			case 13:
				strFieldName = "InHospitalDate";
				break;
			case 14:
				strFieldName = "OutHospitalDate";
				break;
			case 15:
				strFieldName = "Remark";
				break;
			case 16:
				strFieldName = "SeriousGrade";
				break;
			case 17:
				strFieldName = "SurveyFlag";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MngCom";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "FirstDiaDate";
				break;
			case 25:
				strFieldName = "HosGrade";
				break;
			case 26:
				strFieldName = "LocalPlace";
				break;
			case 27:
				strFieldName = "HosTel";
				break;
			case 28:
				strFieldName = "DieDate";
				break;
			case 29:
				strFieldName = "AccCause";
				break;
			case 30:
				strFieldName = "VIPFlag";
				break;
			case 31:
				strFieldName = "AccidentDetail";
				break;
			case 32:
				strFieldName = "CureDesc";
				break;
			case 33:
				strFieldName = "SubmitFlag";
				break;
			case 34:
				strFieldName = "CondoleFlag";
				break;
			case 35:
				strFieldName = "DieFlag";
				break;
			case 36:
				strFieldName = "AccResult1";
				break;
			case 37:
				strFieldName = "AccResult2";
				break;
			case 38:
				strFieldName = "SeqNo";
				break;
			case 39:
				strFieldName = "Sex";
				break;
			case 40:
				strFieldName = "IDType";
				break;
			case 41:
				strFieldName = "IDNo";
				break;
			case 42:
				strFieldName = "MedAccDate";
				break;
			case 43:
				strFieldName = "BnfName";
				break;
			case 44:
				strFieldName = "BankCode";
				break;
			case 45:
				strFieldName = "BankAccNo";
				break;
			case 46:
				strFieldName = "BnfIDNo";
				break;
			case 47:
				strFieldName = "RelationToInsured";
				break;
			case 48:
				strFieldName = "CaseGetMode";
				break;
			case 49:
				strFieldName = "BnfIDType";
				break;
			case 50:
				strFieldName = "Birthday";
				break;
			case 51:
				strFieldName = "BnfSex";
				break;
			case 52:
				strFieldName = "BnfAccName";
				break;
			case 53:
				strFieldName = "AppntNo";
				break;
			case 54:
				strFieldName = "AppntName";
				break;
			case 55:
				strFieldName = "Age";
				break;
			case 56:
				strFieldName = "EmpNo";
				break;
			case 57:
				strFieldName = "AccGradeState";
				break;
			case 58:
				strFieldName = "ComCode";
				break;
			case 59:
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
		if( strFieldName.equals("SubRptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccSubject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustSituation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccPlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SeriousGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyFlag") ) {
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
		if( strFieldName.equals("FirstDiaDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("HosGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LocalPlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosTel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DieDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccCause") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("SubmitFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CondoleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DieFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SeqNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BnfName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseGetMode") ) {
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
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Age") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EmpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccGradeState") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
