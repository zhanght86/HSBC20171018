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
import com.sinosoft.lis.db.LLCaseCureDB;

/*
 * <p>ClassName: LLCaseCureSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LLCaseCureSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseCureSchema.class);
	// @Field
	/** 序号 */
	private String SerialNo;
	/** 分案号 */
	private String CaseNo;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 出险类型 */
	private String AccidentType;
	/** 费用项目代码 */
	private String FeeItemCode;
	/** 费用项目名称 */
	private String FeeItemName;
	/** 费用金额 */
	private double Fee;
	/** 门诊/住院号码 */
	private String CureNo;
	/** 住院收据号码 */
	private String ReceiptNo;
	/** 入院日期 */
	private Date InHospitalDate;
	/** 出院日期 */
	private Date OutHospitalDate;
	/** 实际住院天数 */
	private int InHospitalDays;
	/** 疾病代码 */
	private String DiseaseCode;
	/** 疾病名称 */
	private String DiseaseName;
	/** 诊断 */
	private String Diagnosis;
	/** 赔付标志 */
	private String ClmFlag;
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
	/** 申请赔付金额 */
	private double ApplyFee;
	/** 主要疾病标志 */
	private String MainDiseaseFlag;
	/** 重疾标记 */
	private String SeriousFlag;
	/** 鉴定时间 */
	private Date JudgeDate;
	/** 诊断医生号码 */
	private String DoctorNo;
	/** 诊断医生名称 */
	private String DoctorName;
	/** 受理事故号 */
	private String CaseRelaNo;
	/** 临床诊断 */
	private String Diagnose;
	/** 确诊依据 */
	private String DiagnoseDesc;
	/** 鉴定单位 */
	private String JudgeOrgan;
	/** 鉴定单位名称 */
	private String JudgeOrganName;
	/** 鉴定依据 */
	private String JudgeDepend;
	/** 确诊日期 */
	private Date DiagnoseDate;

	public static final int FIELDNUM = 38;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseCureSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "CaseNo";

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
		LLCaseCureSchema cloned = (LLCaseCureSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("序号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
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
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
		CustomerName = aCustomerName;
	}
	/**
	* 包括：意外、疾病
	*/
	public String getAccidentType()
	{
		return AccidentType;
	}
	public void setAccidentType(String aAccidentType)
	{
		if(aAccidentType!=null && aAccidentType.length()>3)
			throw new IllegalArgumentException("出险类型AccidentType值"+aAccidentType+"的长度"+aAccidentType.length()+"大于最大值3");
		AccidentType = aAccidentType;
	}
	/**
	* 具体的费用项目
	*/
	public String getFeeItemCode()
	{
		return FeeItemCode;
	}
	public void setFeeItemCode(String aFeeItemCode)
	{
		if(aFeeItemCode!=null && aFeeItemCode.length()>10)
			throw new IllegalArgumentException("费用项目代码FeeItemCode值"+aFeeItemCode+"的长度"+aFeeItemCode.length()+"大于最大值10");
		FeeItemCode = aFeeItemCode;
	}
	public String getFeeItemName()
	{
		return FeeItemName;
	}
	public void setFeeItemName(String aFeeItemName)
	{
		if(aFeeItemName!=null && aFeeItemName.length()>60)
			throw new IllegalArgumentException("费用项目名称FeeItemName值"+aFeeItemName+"的长度"+aFeeItemName.length()+"大于最大值60");
		FeeItemName = aFeeItemName;
	}
	public double getFee()
	{
		return Fee;
	}
	public void setFee(double aFee)
	{
		Fee = aFee;
	}
	public void setFee(String aFee)
	{
		if (aFee != null && !aFee.equals(""))
		{
			Double tDouble = new Double(aFee);
			double d = tDouble.doubleValue();
			Fee = d;
		}
	}

	public String getCureNo()
	{
		return CureNo;
	}
	public void setCureNo(String aCureNo)
	{
		if(aCureNo!=null && aCureNo.length()>20)
			throw new IllegalArgumentException("门诊/住院号码CureNo值"+aCureNo+"的长度"+aCureNo.length()+"大于最大值20");
		CureNo = aCureNo;
	}
	public String getReceiptNo()
	{
		return ReceiptNo;
	}
	public void setReceiptNo(String aReceiptNo)
	{
		if(aReceiptNo!=null && aReceiptNo.length()>20)
			throw new IllegalArgumentException("住院收据号码ReceiptNo值"+aReceiptNo+"的长度"+aReceiptNo.length()+"大于最大值20");
		ReceiptNo = aReceiptNo;
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

	public String getDiseaseCode()
	{
		return DiseaseCode;
	}
	public void setDiseaseCode(String aDiseaseCode)
	{
		if(aDiseaseCode!=null && aDiseaseCode.length()>10)
			throw new IllegalArgumentException("疾病代码DiseaseCode值"+aDiseaseCode+"的长度"+aDiseaseCode.length()+"大于最大值10");
		DiseaseCode = aDiseaseCode;
	}
	public String getDiseaseName()
	{
		return DiseaseName;
	}
	public void setDiseaseName(String aDiseaseName)
	{
		if(aDiseaseName!=null && aDiseaseName.length()>30)
			throw new IllegalArgumentException("疾病名称DiseaseName值"+aDiseaseName+"的长度"+aDiseaseName.length()+"大于最大值30");
		DiseaseName = aDiseaseName;
	}
	public String getDiagnosis()
	{
		return Diagnosis;
	}
	public void setDiagnosis(String aDiagnosis)
	{
		if(aDiagnosis!=null && aDiagnosis.length()>600)
			throw new IllegalArgumentException("诊断Diagnosis值"+aDiagnosis+"的长度"+aDiagnosis.length()+"大于最大值600");
		Diagnosis = aDiagnosis;
	}
	public String getClmFlag()
	{
		return ClmFlag;
	}
	public void setClmFlag(String aClmFlag)
	{
		if(aClmFlag!=null && aClmFlag.length()>1)
			throw new IllegalArgumentException("赔付标志ClmFlag值"+aClmFlag+"的长度"+aClmFlag.length()+"大于最大值1");
		ClmFlag = aClmFlag;
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
	public double getApplyFee()
	{
		return ApplyFee;
	}
	public void setApplyFee(double aApplyFee)
	{
		ApplyFee = aApplyFee;
	}
	public void setApplyFee(String aApplyFee)
	{
		if (aApplyFee != null && !aApplyFee.equals(""))
		{
			Double tDouble = new Double(aApplyFee);
			double d = tDouble.doubleValue();
			ApplyFee = d;
		}
	}

	/**
	* 0 次要疾病<p>
	* 1 主要疾病
	*/
	public String getMainDiseaseFlag()
	{
		return MainDiseaseFlag;
	}
	public void setMainDiseaseFlag(String aMainDiseaseFlag)
	{
		if(aMainDiseaseFlag!=null && aMainDiseaseFlag.length()>10)
			throw new IllegalArgumentException("主要疾病标志MainDiseaseFlag值"+aMainDiseaseFlag+"的长度"+aMainDiseaseFlag.length()+"大于最大值10");
		MainDiseaseFlag = aMainDiseaseFlag;
	}
	/**
	* 0 一般疾病<p>
	* 1 重要疾病
	*/
	public String getSeriousFlag()
	{
		return SeriousFlag;
	}
	public void setSeriousFlag(String aSeriousFlag)
	{
		if(aSeriousFlag!=null && aSeriousFlag.length()>10)
			throw new IllegalArgumentException("重疾标记SeriousFlag值"+aSeriousFlag+"的长度"+aSeriousFlag.length()+"大于最大值10");
		SeriousFlag = aSeriousFlag;
	}
	public String getJudgeDate()
	{
		if( JudgeDate != null )
			return fDate.getString(JudgeDate);
		else
			return null;
	}
	public void setJudgeDate(Date aJudgeDate)
	{
		JudgeDate = aJudgeDate;
	}
	public void setJudgeDate(String aJudgeDate)
	{
		if (aJudgeDate != null && !aJudgeDate.equals("") )
		{
			JudgeDate = fDate.getDate( aJudgeDate );
		}
		else
			JudgeDate = null;
	}

	public String getDoctorNo()
	{
		return DoctorNo;
	}
	public void setDoctorNo(String aDoctorNo)
	{
		if(aDoctorNo!=null && aDoctorNo.length()>20)
			throw new IllegalArgumentException("诊断医生号码DoctorNo值"+aDoctorNo+"的长度"+aDoctorNo.length()+"大于最大值20");
		DoctorNo = aDoctorNo;
	}
	public String getDoctorName()
	{
		return DoctorName;
	}
	public void setDoctorName(String aDoctorName)
	{
		if(aDoctorName!=null && aDoctorName.length()>60)
			throw new IllegalArgumentException("诊断医生名称DoctorName值"+aDoctorName+"的长度"+aDoctorName.length()+"大于最大值60");
		DoctorName = aDoctorName;
	}
	/**
	* 受理事故号
	*/
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		if(aCaseRelaNo!=null && aCaseRelaNo.length()>20)
			throw new IllegalArgumentException("受理事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}
	public String getDiagnose()
	{
		return Diagnose;
	}
	public void setDiagnose(String aDiagnose)
	{
		if(aDiagnose!=null && aDiagnose.length()>600)
			throw new IllegalArgumentException("临床诊断Diagnose值"+aDiagnose+"的长度"+aDiagnose.length()+"大于最大值600");
		Diagnose = aDiagnose;
	}
	public String getDiagnoseDesc()
	{
		return DiagnoseDesc;
	}
	public void setDiagnoseDesc(String aDiagnoseDesc)
	{
		if(aDiagnoseDesc!=null && aDiagnoseDesc.length()>100)
			throw new IllegalArgumentException("确诊依据DiagnoseDesc值"+aDiagnoseDesc+"的长度"+aDiagnoseDesc.length()+"大于最大值100");
		DiagnoseDesc = aDiagnoseDesc;
	}
	public String getJudgeOrgan()
	{
		return JudgeOrgan;
	}
	public void setJudgeOrgan(String aJudgeOrgan)
	{
		if(aJudgeOrgan!=null && aJudgeOrgan.length()>20)
			throw new IllegalArgumentException("鉴定单位JudgeOrgan值"+aJudgeOrgan+"的长度"+aJudgeOrgan.length()+"大于最大值20");
		JudgeOrgan = aJudgeOrgan;
	}
	public String getJudgeOrganName()
	{
		return JudgeOrganName;
	}
	public void setJudgeOrganName(String aJudgeOrganName)
	{
		if(aJudgeOrganName!=null && aJudgeOrganName.length()>100)
			throw new IllegalArgumentException("鉴定单位名称JudgeOrganName值"+aJudgeOrganName+"的长度"+aJudgeOrganName.length()+"大于最大值100");
		JudgeOrganName = aJudgeOrganName;
	}
	public String getJudgeDepend()
	{
		return JudgeDepend;
	}
	public void setJudgeDepend(String aJudgeDepend)
	{
		if(aJudgeDepend!=null && aJudgeDepend.length()>100)
			throw new IllegalArgumentException("鉴定依据JudgeDepend值"+aJudgeDepend+"的长度"+aJudgeDepend.length()+"大于最大值100");
		JudgeDepend = aJudgeDepend;
	}
	public String getDiagnoseDate()
	{
		if( DiagnoseDate != null )
			return fDate.getString(DiagnoseDate);
		else
			return null;
	}
	public void setDiagnoseDate(Date aDiagnoseDate)
	{
		DiagnoseDate = aDiagnoseDate;
	}
	public void setDiagnoseDate(String aDiagnoseDate)
	{
		if (aDiagnoseDate != null && !aDiagnoseDate.equals("") )
		{
			DiagnoseDate = fDate.getDate( aDiagnoseDate );
		}
		else
			DiagnoseDate = null;
	}


	/**
	* 使用另外一个 LLCaseCureSchema 对象给 Schema 赋值
	* @param: aLLCaseCureSchema LLCaseCureSchema
	**/
	public void setSchema(LLCaseCureSchema aLLCaseCureSchema)
	{
		this.SerialNo = aLLCaseCureSchema.getSerialNo();
		this.CaseNo = aLLCaseCureSchema.getCaseNo();
		this.HospitalCode = aLLCaseCureSchema.getHospitalCode();
		this.HospitalName = aLLCaseCureSchema.getHospitalName();
		this.CustomerNo = aLLCaseCureSchema.getCustomerNo();
		this.CustomerName = aLLCaseCureSchema.getCustomerName();
		this.AccidentType = aLLCaseCureSchema.getAccidentType();
		this.FeeItemCode = aLLCaseCureSchema.getFeeItemCode();
		this.FeeItemName = aLLCaseCureSchema.getFeeItemName();
		this.Fee = aLLCaseCureSchema.getFee();
		this.CureNo = aLLCaseCureSchema.getCureNo();
		this.ReceiptNo = aLLCaseCureSchema.getReceiptNo();
		this.InHospitalDate = fDate.getDate( aLLCaseCureSchema.getInHospitalDate());
		this.OutHospitalDate = fDate.getDate( aLLCaseCureSchema.getOutHospitalDate());
		this.InHospitalDays = aLLCaseCureSchema.getInHospitalDays();
		this.DiseaseCode = aLLCaseCureSchema.getDiseaseCode();
		this.DiseaseName = aLLCaseCureSchema.getDiseaseName();
		this.Diagnosis = aLLCaseCureSchema.getDiagnosis();
		this.ClmFlag = aLLCaseCureSchema.getClmFlag();
		this.MngCom = aLLCaseCureSchema.getMngCom();
		this.Operator = aLLCaseCureSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCaseCureSchema.getMakeDate());
		this.MakeTime = aLLCaseCureSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCaseCureSchema.getModifyDate());
		this.ModifyTime = aLLCaseCureSchema.getModifyTime();
		this.ApplyFee = aLLCaseCureSchema.getApplyFee();
		this.MainDiseaseFlag = aLLCaseCureSchema.getMainDiseaseFlag();
		this.SeriousFlag = aLLCaseCureSchema.getSeriousFlag();
		this.JudgeDate = fDate.getDate( aLLCaseCureSchema.getJudgeDate());
		this.DoctorNo = aLLCaseCureSchema.getDoctorNo();
		this.DoctorName = aLLCaseCureSchema.getDoctorName();
		this.CaseRelaNo = aLLCaseCureSchema.getCaseRelaNo();
		this.Diagnose = aLLCaseCureSchema.getDiagnose();
		this.DiagnoseDesc = aLLCaseCureSchema.getDiagnoseDesc();
		this.JudgeOrgan = aLLCaseCureSchema.getJudgeOrgan();
		this.JudgeOrganName = aLLCaseCureSchema.getJudgeOrganName();
		this.JudgeDepend = aLLCaseCureSchema.getJudgeDepend();
		this.DiagnoseDate = fDate.getDate( aLLCaseCureSchema.getDiagnoseDate());
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

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

			if( rs.getString("FeeItemCode") == null )
				this.FeeItemCode = null;
			else
				this.FeeItemCode = rs.getString("FeeItemCode").trim();

			if( rs.getString("FeeItemName") == null )
				this.FeeItemName = null;
			else
				this.FeeItemName = rs.getString("FeeItemName").trim();

			this.Fee = rs.getDouble("Fee");
			if( rs.getString("CureNo") == null )
				this.CureNo = null;
			else
				this.CureNo = rs.getString("CureNo").trim();

			if( rs.getString("ReceiptNo") == null )
				this.ReceiptNo = null;
			else
				this.ReceiptNo = rs.getString("ReceiptNo").trim();

			this.InHospitalDate = rs.getDate("InHospitalDate");
			this.OutHospitalDate = rs.getDate("OutHospitalDate");
			this.InHospitalDays = rs.getInt("InHospitalDays");
			if( rs.getString("DiseaseCode") == null )
				this.DiseaseCode = null;
			else
				this.DiseaseCode = rs.getString("DiseaseCode").trim();

			if( rs.getString("DiseaseName") == null )
				this.DiseaseName = null;
			else
				this.DiseaseName = rs.getString("DiseaseName").trim();

			if( rs.getString("Diagnosis") == null )
				this.Diagnosis = null;
			else
				this.Diagnosis = rs.getString("Diagnosis").trim();

			if( rs.getString("ClmFlag") == null )
				this.ClmFlag = null;
			else
				this.ClmFlag = rs.getString("ClmFlag").trim();

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

			this.ApplyFee = rs.getDouble("ApplyFee");
			if( rs.getString("MainDiseaseFlag") == null )
				this.MainDiseaseFlag = null;
			else
				this.MainDiseaseFlag = rs.getString("MainDiseaseFlag").trim();

			if( rs.getString("SeriousFlag") == null )
				this.SeriousFlag = null;
			else
				this.SeriousFlag = rs.getString("SeriousFlag").trim();

			this.JudgeDate = rs.getDate("JudgeDate");
			if( rs.getString("DoctorNo") == null )
				this.DoctorNo = null;
			else
				this.DoctorNo = rs.getString("DoctorNo").trim();

			if( rs.getString("DoctorName") == null )
				this.DoctorName = null;
			else
				this.DoctorName = rs.getString("DoctorName").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("Diagnose") == null )
				this.Diagnose = null;
			else
				this.Diagnose = rs.getString("Diagnose").trim();

			if( rs.getString("DiagnoseDesc") == null )
				this.DiagnoseDesc = null;
			else
				this.DiagnoseDesc = rs.getString("DiagnoseDesc").trim();

			if( rs.getString("JudgeOrgan") == null )
				this.JudgeOrgan = null;
			else
				this.JudgeOrgan = rs.getString("JudgeOrgan").trim();

			if( rs.getString("JudgeOrganName") == null )
				this.JudgeOrganName = null;
			else
				this.JudgeOrganName = rs.getString("JudgeOrganName").trim();

			if( rs.getString("JudgeDepend") == null )
				this.JudgeDepend = null;
			else
				this.JudgeDepend = rs.getString("JudgeDepend").trim();

			this.DiagnoseDate = rs.getDate("DiagnoseDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCaseCure表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseCureSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseCureSchema getSchema()
	{
		LLCaseCureSchema aLLCaseCureSchema = new LLCaseCureSchema();
		aLLCaseCureSchema.setSchema(this);
		return aLLCaseCureSchema;
	}

	public LLCaseCureDB getDB()
	{
		LLCaseCureDB aDBOper = new LLCaseCureDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseCure描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CureNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InHospitalDays));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Diagnosis)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ApplyFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainDiseaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SeriousFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( JudgeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoctorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoctorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Diagnose)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiagnoseDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeOrgan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeOrganName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeDepend)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DiagnoseDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseCure>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccidentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FeeItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FeeItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			CureNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ReceiptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			OutHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			InHospitalDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			DiseaseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DiseaseName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Diagnosis = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ClmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ApplyFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			MainDiseaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			SeriousFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			JudgeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			DoctorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			DoctorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Diagnose = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			DiagnoseDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			JudgeOrgan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			JudgeOrganName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			JudgeDepend = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			DiagnoseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseCureSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
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
		if (FCode.equalsIgnoreCase("FeeItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemCode));
		}
		if (FCode.equalsIgnoreCase("FeeItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemName));
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fee));
		}
		if (FCode.equalsIgnoreCase("CureNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CureNo));
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptNo));
		}
		if (FCode.equalsIgnoreCase("InHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("OutHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("InHospitalDays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InHospitalDays));
		}
		if (FCode.equalsIgnoreCase("DiseaseCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseCode));
		}
		if (FCode.equalsIgnoreCase("DiseaseName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseName));
		}
		if (FCode.equalsIgnoreCase("Diagnosis"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Diagnosis));
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFlag));
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
		if (FCode.equalsIgnoreCase("ApplyFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyFee));
		}
		if (FCode.equalsIgnoreCase("MainDiseaseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainDiseaseFlag));
		}
		if (FCode.equalsIgnoreCase("SeriousFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeriousFlag));
		}
		if (FCode.equalsIgnoreCase("JudgeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getJudgeDate()));
		}
		if (FCode.equalsIgnoreCase("DoctorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoctorNo));
		}
		if (FCode.equalsIgnoreCase("DoctorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoctorName));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("Diagnose"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Diagnose));
		}
		if (FCode.equalsIgnoreCase("DiagnoseDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiagnoseDesc));
		}
		if (FCode.equalsIgnoreCase("JudgeOrgan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeOrgan));
		}
		if (FCode.equalsIgnoreCase("JudgeOrganName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeOrganName));
		}
		if (FCode.equalsIgnoreCase("JudgeDepend"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeDepend));
		}
		if (FCode.equalsIgnoreCase("DiagnoseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDiagnoseDate()));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
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
				strFieldValue = StrTool.GBKToUnicode(FeeItemCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FeeItemName);
				break;
			case 9:
				strFieldValue = String.valueOf(Fee);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CureNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReceiptNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
				break;
			case 14:
				strFieldValue = String.valueOf(InHospitalDays);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DiseaseCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(DiseaseName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Diagnosis);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ClmFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 25:
				strFieldValue = String.valueOf(ApplyFee);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MainDiseaseFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(SeriousFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJudgeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(DoctorNo);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(DoctorName);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Diagnose);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(DiagnoseDesc);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(JudgeOrgan);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(JudgeOrganName);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(JudgeDepend);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDiagnoseDate()));
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("FeeItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemCode = FValue.trim();
			}
			else
				FeeItemCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemName = FValue.trim();
			}
			else
				FeeItemName = null;
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Fee = d;
			}
		}
		if (FCode.equalsIgnoreCase("CureNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CureNo = FValue.trim();
			}
			else
				CureNo = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiptNo = FValue.trim();
			}
			else
				ReceiptNo = null;
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
		if (FCode.equalsIgnoreCase("InHospitalDays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InHospitalDays = i;
			}
		}
		if (FCode.equalsIgnoreCase("DiseaseCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseaseCode = FValue.trim();
			}
			else
				DiseaseCode = null;
		}
		if (FCode.equalsIgnoreCase("DiseaseName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseaseName = FValue.trim();
			}
			else
				DiseaseName = null;
		}
		if (FCode.equalsIgnoreCase("Diagnosis"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Diagnosis = FValue.trim();
			}
			else
				Diagnosis = null;
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFlag = FValue.trim();
			}
			else
				ClmFlag = null;
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
		if (FCode.equalsIgnoreCase("ApplyFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ApplyFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("MainDiseaseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainDiseaseFlag = FValue.trim();
			}
			else
				MainDiseaseFlag = null;
		}
		if (FCode.equalsIgnoreCase("SeriousFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SeriousFlag = FValue.trim();
			}
			else
				SeriousFlag = null;
		}
		if (FCode.equalsIgnoreCase("JudgeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				JudgeDate = fDate.getDate( FValue );
			}
			else
				JudgeDate = null;
		}
		if (FCode.equalsIgnoreCase("DoctorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoctorNo = FValue.trim();
			}
			else
				DoctorNo = null;
		}
		if (FCode.equalsIgnoreCase("DoctorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoctorName = FValue.trim();
			}
			else
				DoctorName = null;
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("Diagnose"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Diagnose = FValue.trim();
			}
			else
				Diagnose = null;
		}
		if (FCode.equalsIgnoreCase("DiagnoseDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiagnoseDesc = FValue.trim();
			}
			else
				DiagnoseDesc = null;
		}
		if (FCode.equalsIgnoreCase("JudgeOrgan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JudgeOrgan = FValue.trim();
			}
			else
				JudgeOrgan = null;
		}
		if (FCode.equalsIgnoreCase("JudgeOrganName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JudgeOrganName = FValue.trim();
			}
			else
				JudgeOrganName = null;
		}
		if (FCode.equalsIgnoreCase("JudgeDepend"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JudgeDepend = FValue.trim();
			}
			else
				JudgeDepend = null;
		}
		if (FCode.equalsIgnoreCase("DiagnoseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DiagnoseDate = fDate.getDate( FValue );
			}
			else
				DiagnoseDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCaseCureSchema other = (LLCaseCureSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& CaseNo.equals(other.getCaseNo())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& AccidentType.equals(other.getAccidentType())
			&& FeeItemCode.equals(other.getFeeItemCode())
			&& FeeItemName.equals(other.getFeeItemName())
			&& Fee == other.getFee()
			&& CureNo.equals(other.getCureNo())
			&& ReceiptNo.equals(other.getReceiptNo())
			&& fDate.getString(InHospitalDate).equals(other.getInHospitalDate())
			&& fDate.getString(OutHospitalDate).equals(other.getOutHospitalDate())
			&& InHospitalDays == other.getInHospitalDays()
			&& DiseaseCode.equals(other.getDiseaseCode())
			&& DiseaseName.equals(other.getDiseaseName())
			&& Diagnosis.equals(other.getDiagnosis())
			&& ClmFlag.equals(other.getClmFlag())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ApplyFee == other.getApplyFee()
			&& MainDiseaseFlag.equals(other.getMainDiseaseFlag())
			&& SeriousFlag.equals(other.getSeriousFlag())
			&& fDate.getString(JudgeDate).equals(other.getJudgeDate())
			&& DoctorNo.equals(other.getDoctorNo())
			&& DoctorName.equals(other.getDoctorName())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& Diagnose.equals(other.getDiagnose())
			&& DiagnoseDesc.equals(other.getDiagnoseDesc())
			&& JudgeOrgan.equals(other.getJudgeOrgan())
			&& JudgeOrganName.equals(other.getJudgeOrganName())
			&& JudgeDepend.equals(other.getJudgeDepend())
			&& fDate.getString(DiagnoseDate).equals(other.getDiagnoseDate());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 2;
		}
		if( strFieldName.equals("HospitalName") ) {
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
		if( strFieldName.equals("FeeItemCode") ) {
			return 7;
		}
		if( strFieldName.equals("FeeItemName") ) {
			return 8;
		}
		if( strFieldName.equals("Fee") ) {
			return 9;
		}
		if( strFieldName.equals("CureNo") ) {
			return 10;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return 11;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return 12;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return 13;
		}
		if( strFieldName.equals("InHospitalDays") ) {
			return 14;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return 15;
		}
		if( strFieldName.equals("DiseaseName") ) {
			return 16;
		}
		if( strFieldName.equals("Diagnosis") ) {
			return 17;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return 18;
		}
		if( strFieldName.equals("MngCom") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
		}
		if( strFieldName.equals("ApplyFee") ) {
			return 25;
		}
		if( strFieldName.equals("MainDiseaseFlag") ) {
			return 26;
		}
		if( strFieldName.equals("SeriousFlag") ) {
			return 27;
		}
		if( strFieldName.equals("JudgeDate") ) {
			return 28;
		}
		if( strFieldName.equals("DoctorNo") ) {
			return 29;
		}
		if( strFieldName.equals("DoctorName") ) {
			return 30;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 31;
		}
		if( strFieldName.equals("Diagnose") ) {
			return 32;
		}
		if( strFieldName.equals("DiagnoseDesc") ) {
			return 33;
		}
		if( strFieldName.equals("JudgeOrgan") ) {
			return 34;
		}
		if( strFieldName.equals("JudgeOrganName") ) {
			return 35;
		}
		if( strFieldName.equals("JudgeDepend") ) {
			return 36;
		}
		if( strFieldName.equals("DiagnoseDate") ) {
			return 37;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "HospitalCode";
				break;
			case 3:
				strFieldName = "HospitalName";
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
				strFieldName = "FeeItemCode";
				break;
			case 8:
				strFieldName = "FeeItemName";
				break;
			case 9:
				strFieldName = "Fee";
				break;
			case 10:
				strFieldName = "CureNo";
				break;
			case 11:
				strFieldName = "ReceiptNo";
				break;
			case 12:
				strFieldName = "InHospitalDate";
				break;
			case 13:
				strFieldName = "OutHospitalDate";
				break;
			case 14:
				strFieldName = "InHospitalDays";
				break;
			case 15:
				strFieldName = "DiseaseCode";
				break;
			case 16:
				strFieldName = "DiseaseName";
				break;
			case 17:
				strFieldName = "Diagnosis";
				break;
			case 18:
				strFieldName = "ClmFlag";
				break;
			case 19:
				strFieldName = "MngCom";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
				break;
			case 25:
				strFieldName = "ApplyFee";
				break;
			case 26:
				strFieldName = "MainDiseaseFlag";
				break;
			case 27:
				strFieldName = "SeriousFlag";
				break;
			case 28:
				strFieldName = "JudgeDate";
				break;
			case 29:
				strFieldName = "DoctorNo";
				break;
			case 30:
				strFieldName = "DoctorName";
				break;
			case 31:
				strFieldName = "CaseRelaNo";
				break;
			case 32:
				strFieldName = "Diagnose";
				break;
			case 33:
				strFieldName = "DiagnoseDesc";
				break;
			case 34:
				strFieldName = "JudgeOrgan";
				break;
			case 35:
				strFieldName = "JudgeOrganName";
				break;
			case 36:
				strFieldName = "JudgeDepend";
				break;
			case 37:
				strFieldName = "DiagnoseDate";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
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
		if( strFieldName.equals("FeeItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CureNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InHospitalDays") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseaseName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Diagnosis") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFlag") ) {
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
		if( strFieldName.equals("ApplyFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MainDiseaseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SeriousFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DoctorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoctorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Diagnose") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiagnoseDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeOrgan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeOrganName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeDepend") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiagnoseDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
