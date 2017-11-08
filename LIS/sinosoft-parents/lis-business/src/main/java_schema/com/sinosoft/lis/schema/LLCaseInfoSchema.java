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
import com.sinosoft.lis.db.LLCaseInfoDB;

/*
 * <p>ClassName: LLCaseInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLCaseInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseInfoSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 序号 */
	private String SerialNo;
	/** 事故号 */
	private String CaseRelaNo;
	/** 立案号(申请登记号) */
	private String RgtNo;
	/** 附件序号 */
	private String AffixNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 类型 */
	private String Type;
	/** 代码 */
	private String Code;
	/** 名称 */
	private String Name;
	/** 诊断 */
	private String Diagnosis;
	/** 赔付标志 */
	private String ClmFlag;
	/** 残疾类型 */
	private String DefoType;
	/** 伤残级别 */
	private String DefoGrade;
	/** 伤残代码 */
	private String DefoCode;
	/** 伤残级别名称 */
	private String DefoName;
	/** 残疾原因 */
	private String DeformityReason;
	/** 残疾给付比例 */
	private double DeformityRate;
	/** 申请给付比例 */
	private double AppDeformityRate;
	/** 实际给付比例 */
	private double RealRate;
	/** 调整原因 */
	private String AdjReason;
	/** 调整备注 */
	private String AdjRemark;
	/** 病例号码 */
	private String No;
	/** 确诊日期 */
	private Date DianoseDate;
	/** 确诊依据 */
	private String DiagnoseDesc;
	/** 诊断医院代码 */
	private String HospitalCode;
	/** 诊断医院名称 */
	private String HospitalName;
	/** 诊断医生号码 */
	private String DoctorNo;
	/** 诊断医生名称 */
	private String DoctorName;
	/** 鉴定单位 */
	private String JudgeOrgan;
	/** 鉴定单位名称 */
	private String JudgeOrganName;
	/** 鉴定时间 */
	private Date JudgeDate;
	/** 鉴定依据 */
	private String JudgeDepend;
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
	/** 残疾部位编码 */
	private String PartCode;
	/** 伤残代码名称 */
	private String DefoCodeName;
	/** 伤残大类 */
	private String DefoClass;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "SerialNo";
		pk[3] = "CustomerNo";

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
		LLCaseInfoSchema cloned = (LLCaseInfoSchema)super.clone();
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
			throw new IllegalArgumentException("事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}
	/**
	* 如果是团体，可以理解为:团???申请受理号
	*/
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号(申请登记号)RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	/**
	* [不用]
	*/
	public String getAffixNo()
	{
		return AffixNo;
	}
	public void setAffixNo(String aAffixNo)
	{
		if(aAffixNo!=null && aAffixNo.length()>20)
			throw new IllegalArgumentException("附件序号AffixNo值"+aAffixNo+"的长度"+aAffixNo.length()+"大于最大值20");
		AffixNo = aAffixNo;
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
		if(aCustomerName!=null && aCustomerName.length()>40)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值40");
		CustomerName = aCustomerName;
	}
	/**
	* [不用]<p>
	* 保留<p>
	* 0疾病<p>
	* 1伤残
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		if(aType!=null && aType.length()>6)
			throw new IllegalArgumentException("类型Type值"+aType+"的长度"+aType.length()+"大于最大值6");
		Type = aType;
	}
	/**
	* [不用]
	*/
	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		if(aCode!=null && aCode.length()>10)
			throw new IllegalArgumentException("代码Code值"+aCode+"的长度"+aCode.length()+"大于最大值10");
		Code = aCode;
	}
	/**
	* [不用]
	*/
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>100)
			throw new IllegalArgumentException("名称Name值"+aName+"的长度"+aName.length()+"大于最大值100");
		Name = aName;
	}
	/**
	* [不用]
	*/
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
	/**
	* [不用]
	*/
	public String getClmFlag()
	{
		return ClmFlag;
	}
	public void setClmFlag(String aClmFlag)
	{
		if(aClmFlag!=null && aClmFlag.length()>6)
			throw new IllegalArgumentException("赔付标志ClmFlag值"+aClmFlag+"的长度"+aClmFlag.length()+"大于最大值6");
		ClmFlag = aClmFlag;
	}
	/**
	* LF―伤残		<p>
	* LZ――骨折		<p>
	* LG―烧伤		<p>
	* LI―重要器官切除		<p>
	* LM―高度残疾
	*/
	public String getDefoType()
	{
		return DefoType;
	}
	public void setDefoType(String aDefoType)
	{
		if(aDefoType!=null && aDefoType.length()>10)
			throw new IllegalArgumentException("残疾类型DefoType值"+aDefoType+"的长度"+aDefoType.length()+"大于最大值10");
		DefoType = aDefoType;
	}
	public String getDefoGrade()
	{
		return DefoGrade;
	}
	public void setDefoGrade(String aDefoGrade)
	{
		if(aDefoGrade!=null && aDefoGrade.length()>10)
			throw new IllegalArgumentException("伤残级别DefoGrade值"+aDefoGrade+"的长度"+aDefoGrade.length()+"大于最大值10");
		DefoGrade = aDefoGrade;
	}
	/**
	* LF―伤残		<p>
	* 新代码		给付比例<p>
	* LF101	双目永久完全失明的	1<p>
	* LF102	两上肢腕关节以上或两下肢踝关节以上缺失的	1<p>
	* LF103	一上肢腕关节以上及一下肢踝关节以上缺失的	1<p>
	* LF104	一目永久完全失明及一上肢腕关节以上缺失的	1<p>
	* LF105	一目永久完全失明及一下肢踝关节以上缺失的	1<p>
	* LF106	四肢关节机能永久完全丧失的	1<p>
	* LF107	咀嚼、吞咽机能永久完全丧失的	1<p>
	* LF108	中枢神经系统机能或胸、腹部脏器机能极度障碍，终身不能从事任何工作，为维持生命必要的日常生活活动，全需他人扶助的	1<p>
	* LF201	两上肢、或两下肢、或一上肢及一下肢，各有三大关节中的两个关节以上机能永久完全丧失的	0.75<p>
	* LF202	十手指缺失的	0.75<p>
	* LF301	一上肢腕关节以上缺失或一上肢的三大关节全部机能永久完全丧失的	0.5<p>
	* LF302	一下肢踝关节以上缺失或一下肢的三大关节全部机能永久完全丧失的	0.5<p>
	* LF303	双耳听觉机能永久完全丧失的	0.5<p>
	* LF304	十手指机能永久完全丧失的	0.5<p>
	* LF305	十足趾缺失的	0.5<p>
	* LF401	一目永久完全失明的	0.3<p>
	* LF402	一上肢三大关节中，有二关节之机能永久完全丧失的	0.3<p>
	* LF403	一下肢三大关节中，有二关节之机能永久完全丧失的	0.3<p>
	* LF404	一手含拇指及食指，有四手指以上缺失的	0.3<p>
	* LF405	一下肢永久缩短5公分以上的	0.3<p>
	* LF406	语言机能永久完全丧失的	0.3<p>
	* LF407	十足趾机能永久完全丧失的	0.3<p>
	* LF501	一上肢三大关节中，有一关节之机能永久完全丧失的	0.2<p>
	* LF502	一下肢三大关节中，有一关节之机能永久完全丧失的	0.2<p>
	* LF503	两手拇指缺失的	0.2<p>
	* LF504	一足五趾缺失的	0.2<p>
	* LF505	两眼眼睑显著缺损的	0.2<p>
	* LF506	一耳听觉机能永久完全丧失的	0.2<p>
	* LF507	鼻部缺损且嗅觉机能遗存显著障碍的	0.2<p>
	* LF601	一手拇指及食指缺失，或含拇指或食指有三个或三个以上手指缺失的	0.15<p>
	* LF602	一手含拇指或食指有三个或三个以上手指机能永久完全丧失的	0.15<p>
	* LF603	一足五趾机能永久完全丧失的	0.15<p>
	* LF701	一手拇指或食指缺失，或中指、无名指和小拇指中有二个或二个以上手指缺失的	0.1<p>
	* LF702	一手拇指及食指机能永久完全丧失的	0.1<p>
	* <p>
	* <p>
	* LG―烧伤		<p>
	* 代码	烧伤分类	给付比例<p>
	* LG001	头颈部烧伤占表皮面积不少于8%	1<p>
	* LG002	躯干及四肢烧伤占表皮面积不少于20%	1<p>
	* LG003	头颈部烧伤占表皮面积足5%但少于8%	0.75<p>
	* LG004	躯干及四肢烧伤占表皮面积足15%但少于20%	0.75<p>
	* LG005	头颈部烧伤占表皮面积足2%但少于5%	0.5<p>
	* LG006	躯干及四肢烧伤占表皮面积足10%但少于15%	0.5<p>
	* <p>
	* <p>
	* LI―重要器官切除		<p>
	* 代码	分类	给付比例<p>
	* LI001	一侧肺叶切除	0.5<p>
	* LI002	一半及以上肝切除	0.5<p>
	* LI003	一个及以上肾脏切除	0.5<p>
	* LI004	小肠三分之二以上切除	0.5<p>
	* <p>
	* <p>
	* LM―高度残疾		<p>
	* 代码	分类	给付比例<p>
	* LM001	双目永久完全失明	1<p>
	* LM002	两上肢腕关节以上或两下肢踝关节以上缺失	1<p>
	* LM003	一上肢腕关节以上及一下肢踝关节以上缺失	1<p>
	* LM004	一目永久完全失明及一上肢腕关节以上缺失	1<p>
	* LM005	一目永久完全失明及一下肢踝关节以上缺失	1<p>
	* LM006	四肢关节机能永久完全丧失	1<p>
	* LM007	咀嚼、吞咽机能永久完全丧失	1<p>
	* LM008	中枢神经系统机能或胸、腹部脏器机能极度障碍，不能从事工作全需他人扶助者	1<p>
	* <p>
	* <p>
	* <p>
	* LZ――骨折		<p>
	* 代码	给付比例	分类<p>
	* LZK001	0.5	开放性骨盆骨折<p>
	* LZK002	0.5	开放性髋骨骨折<p>
	* LZK003	0.5	开放性股骨骨折<p>
	* LZK004	0.3	开放性踝骨骨折<p>
	* LZK005	0.3	开放性胫骨骨折<p>
	* LZK006	0.3	开放性腓骨骨折<p>
	* LZK007	0.3	开放性膝盖骨骨折<p>
	* LZK008	0.3	开放性跟骨骨折<p>
	* LZK009	0.2	开放性肱骨骨折<p>
	* LZK010	0.2	开放性桡骨骨折<p>
	* LZK011	0.2	开放性尺骨骨折<p>
	* LZK012	0.2	开放性腕骨骨折<p>
	* LZK013	0.2	开放性椎骨骨折<p>
	* LZK014	0.2	开放性颅骨骨折<p>
	* LZK015	0.2	开放性锁骨骨折<p>
	* LZK016	0.1	开放性肋骨骨折<p>
	* LZK017	0.1	开放性颧骨骨折<p>
	* LZK018	0.1	开放性尾骨骨折<p>
	* LZK019	0.1	开放性上颌骨骨折<p>
	* LZK020	0.1	开放性下颌骨骨折<p>
	* LZK021	0.1	开放性鼻骨骨折<p>
	* LZK022	0.1	开放性趾骨骨折<p>
	* LZK023	0.1	开放性指骨骨折<p>
	* LZK024	0.1	开放性肩胛骨骨折<p>
	* LZK025	0.1	开放性胸骨骨折<p>
	* LZK026	0.1	开放性掌骨骨折<p>
	* LZK027	0.1	开放性跖骨骨折<p>
	* LZK028	0.1	开放性跗骨骨折<p>
	* LZK029	0.1	开放性桡骨远端骨折<p>
	* LZR001	0.25	非开放性骨盆骨折<p>
	* LZR002	0.25	非开放性髋骨骨折<p>
	* LZR003	0.25	非开放性股骨骨折<p>
	* LZR004	0.15	非开放性踝骨骨折<p>
	* LZR005	0.15	非开放性胫骨骨折<p>
	* LZR006	0.15	非开放性腓骨骨折<p>
	* LZR007	0.15	非开放性膝盖骨骨折<p>
	* LZR008	0.15	非开放性跟骨骨折<p>
	* LZR009	0.1	非开放性肱骨骨折<p>
	* LZR010	0.1	非开放性桡骨骨折<p>
	* LZR011	0.1	非开放性尺骨骨折<p>
	* LZR012	0.1	非开放性腕骨骨折<p>
	* LZR013	0.1	非开放性椎骨骨折<p>
	* LZR014	0.1	非开放性颅骨骨折<p>
	* LZR015	0.1	非开放性锁骨骨折<p>
	* LZR016	0.05	非开放性肋骨骨折<p>
	* LZR017	0.05	非开放性颧骨骨折<p>
	* LZR018	0.05	非开放性尾骨骨折<p>
	* LZR019	0.05	非开放性上颌骨骨折<p>
	* LZR020	0.05	非开放性下颌骨骨折<p>
	* LZR021	0.05	非开放性鼻骨骨折<p>
	* LZR022	0.05	非开放性趾骨骨折<p>
	* LZR023	0.05	非开放性指骨骨折<p>
	* LZR024	0.05	非开放性肩胛骨骨折<p>
	* LZR025	0.05	非开放性胸骨骨折<p>
	* LZR026	0.05	非开放性掌骨骨折<p>
	* LZR027	0.05	非开放性跖骨骨折<p>
	* LZR028	0.05	非开放性跗骨骨折<p>
	* LZR029	0.05	非开放性桡骨远端骨折
	*/
	public String getDefoCode()
	{
		return DefoCode;
	}
	public void setDefoCode(String aDefoCode)
	{
		if(aDefoCode!=null && aDefoCode.length()>100)
			throw new IllegalArgumentException("伤残代码DefoCode值"+aDefoCode+"的长度"+aDefoCode.length()+"大于最大值100");
		DefoCode = aDefoCode;
	}
	public String getDefoName()
	{
		return DefoName;
	}
	public void setDefoName(String aDefoName)
	{
		if(aDefoName!=null && aDefoName.length()>600)
			throw new IllegalArgumentException("伤残级别名称DefoName值"+aDefoName+"的长度"+aDefoName.length()+"大于最大值600");
		DefoName = aDefoName;
	}
	/**
	* 0-疾病残疾<p>
	* 1-意外残疾
	*/
	public String getDeformityReason()
	{
		return DeformityReason;
	}
	public void setDeformityReason(String aDeformityReason)
	{
		if(aDeformityReason!=null && aDeformityReason.length()>6)
			throw new IllegalArgumentException("残疾原因DeformityReason值"+aDeformityReason+"的长度"+aDeformityReason.length()+"大于最大值6");
		DeformityReason = aDeformityReason;
	}
	public double getDeformityRate()
	{
		return DeformityRate;
	}
	public void setDeformityRate(double aDeformityRate)
	{
		DeformityRate = aDeformityRate;
	}
	public void setDeformityRate(String aDeformityRate)
	{
		if (aDeformityRate != null && !aDeformityRate.equals(""))
		{
			Double tDouble = new Double(aDeformityRate);
			double d = tDouble.doubleValue();
			DeformityRate = d;
		}
	}

	public double getAppDeformityRate()
	{
		return AppDeformityRate;
	}
	public void setAppDeformityRate(double aAppDeformityRate)
	{
		AppDeformityRate = aAppDeformityRate;
	}
	public void setAppDeformityRate(String aAppDeformityRate)
	{
		if (aAppDeformityRate != null && !aAppDeformityRate.equals(""))
		{
			Double tDouble = new Double(aAppDeformityRate);
			double d = tDouble.doubleValue();
			AppDeformityRate = d;
		}
	}

	public double getRealRate()
	{
		return RealRate;
	}
	public void setRealRate(double aRealRate)
	{
		RealRate = aRealRate;
	}
	public void setRealRate(String aRealRate)
	{
		if (aRealRate != null && !aRealRate.equals(""))
		{
			Double tDouble = new Double(aRealRate);
			double d = tDouble.doubleValue();
			RealRate = d;
		}
	}

	public String getAdjReason()
	{
		return AdjReason;
	}
	public void setAdjReason(String aAdjReason)
	{
		if(aAdjReason!=null && aAdjReason.length()>6)
			throw new IllegalArgumentException("调整原因AdjReason值"+aAdjReason+"的长度"+aAdjReason.length()+"大于最大值6");
		AdjReason = aAdjReason;
	}
	public String getAdjRemark()
	{
		return AdjRemark;
	}
	public void setAdjRemark(String aAdjRemark)
	{
		if(aAdjRemark!=null && aAdjRemark.length()>1000)
			throw new IllegalArgumentException("调整备注AdjRemark值"+aAdjRemark+"的长度"+aAdjRemark.length()+"大于最大值1000");
		AdjRemark = aAdjRemark;
	}
	public String getNo()
	{
		return No;
	}
	public void setNo(String aNo)
	{
		if(aNo!=null && aNo.length()>40)
			throw new IllegalArgumentException("病例号码No值"+aNo+"的长度"+aNo.length()+"大于最大值40");
		No = aNo;
	}
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
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>20)
			throw new IllegalArgumentException("诊断医院代码HospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值20");
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		if(aHospitalName!=null && aHospitalName.length()>600)
			throw new IllegalArgumentException("诊断医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值600");
		HospitalName = aHospitalName;
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
	public String getPartCode()
	{
		return PartCode;
	}
	public void setPartCode(String aPartCode)
	{
		if(aPartCode!=null && aPartCode.length()>2)
			throw new IllegalArgumentException("残疾部位编码PartCode值"+aPartCode+"的长度"+aPartCode.length()+"大于最大值2");
		PartCode = aPartCode;
	}
	public String getDefoCodeName()
	{
		return DefoCodeName;
	}
	public void setDefoCodeName(String aDefoCodeName)
	{
		if(aDefoCodeName!=null && aDefoCodeName.length()>600)
			throw new IllegalArgumentException("伤残代码名称DefoCodeName值"+aDefoCodeName+"的长度"+aDefoCodeName.length()+"大于最大值600");
		DefoCodeName = aDefoCodeName;
	}
	/**
	* 只针对新伤残编码(10级128项)
	*/
	public String getDefoClass()
	{
		return DefoClass;
	}
	public void setDefoClass(String aDefoClass)
	{
		if(aDefoClass!=null && aDefoClass.length()>10)
			throw new IllegalArgumentException("伤残大类DefoClass值"+aDefoClass+"的长度"+aDefoClass.length()+"大于最大值10");
		DefoClass = aDefoClass;
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
	* 使用另外一个 LLCaseInfoSchema 对象给 Schema 赋值
	* @param: aLLCaseInfoSchema LLCaseInfoSchema
	**/
	public void setSchema(LLCaseInfoSchema aLLCaseInfoSchema)
	{
		this.ClmNo = aLLCaseInfoSchema.getClmNo();
		this.CaseNo = aLLCaseInfoSchema.getCaseNo();
		this.SerialNo = aLLCaseInfoSchema.getSerialNo();
		this.CaseRelaNo = aLLCaseInfoSchema.getCaseRelaNo();
		this.RgtNo = aLLCaseInfoSchema.getRgtNo();
		this.AffixNo = aLLCaseInfoSchema.getAffixNo();
		this.CustomerNo = aLLCaseInfoSchema.getCustomerNo();
		this.CustomerName = aLLCaseInfoSchema.getCustomerName();
		this.Type = aLLCaseInfoSchema.getType();
		this.Code = aLLCaseInfoSchema.getCode();
		this.Name = aLLCaseInfoSchema.getName();
		this.Diagnosis = aLLCaseInfoSchema.getDiagnosis();
		this.ClmFlag = aLLCaseInfoSchema.getClmFlag();
		this.DefoType = aLLCaseInfoSchema.getDefoType();
		this.DefoGrade = aLLCaseInfoSchema.getDefoGrade();
		this.DefoCode = aLLCaseInfoSchema.getDefoCode();
		this.DefoName = aLLCaseInfoSchema.getDefoName();
		this.DeformityReason = aLLCaseInfoSchema.getDeformityReason();
		this.DeformityRate = aLLCaseInfoSchema.getDeformityRate();
		this.AppDeformityRate = aLLCaseInfoSchema.getAppDeformityRate();
		this.RealRate = aLLCaseInfoSchema.getRealRate();
		this.AdjReason = aLLCaseInfoSchema.getAdjReason();
		this.AdjRemark = aLLCaseInfoSchema.getAdjRemark();
		this.No = aLLCaseInfoSchema.getNo();
		this.DianoseDate = fDate.getDate( aLLCaseInfoSchema.getDianoseDate());
		this.DiagnoseDesc = aLLCaseInfoSchema.getDiagnoseDesc();
		this.HospitalCode = aLLCaseInfoSchema.getHospitalCode();
		this.HospitalName = aLLCaseInfoSchema.getHospitalName();
		this.DoctorNo = aLLCaseInfoSchema.getDoctorNo();
		this.DoctorName = aLLCaseInfoSchema.getDoctorName();
		this.JudgeOrgan = aLLCaseInfoSchema.getJudgeOrgan();
		this.JudgeOrganName = aLLCaseInfoSchema.getJudgeOrganName();
		this.JudgeDate = fDate.getDate( aLLCaseInfoSchema.getJudgeDate());
		this.JudgeDepend = aLLCaseInfoSchema.getJudgeDepend();
		this.MngCom = aLLCaseInfoSchema.getMngCom();
		this.Operator = aLLCaseInfoSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCaseInfoSchema.getMakeDate());
		this.MakeTime = aLLCaseInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCaseInfoSchema.getModifyDate());
		this.ModifyTime = aLLCaseInfoSchema.getModifyTime();
		this.PartCode = aLLCaseInfoSchema.getPartCode();
		this.DefoCodeName = aLLCaseInfoSchema.getDefoCodeName();
		this.DefoClass = aLLCaseInfoSchema.getDefoClass();
		this.ComCode = aLLCaseInfoSchema.getComCode();
		this.ModifyOperator = aLLCaseInfoSchema.getModifyOperator();
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

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("AffixNo") == null )
				this.AffixNo = null;
			else
				this.AffixNo = rs.getString("AffixNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Diagnosis") == null )
				this.Diagnosis = null;
			else
				this.Diagnosis = rs.getString("Diagnosis").trim();

			if( rs.getString("ClmFlag") == null )
				this.ClmFlag = null;
			else
				this.ClmFlag = rs.getString("ClmFlag").trim();

			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("DefoGrade") == null )
				this.DefoGrade = null;
			else
				this.DefoGrade = rs.getString("DefoGrade").trim();

			if( rs.getString("DefoCode") == null )
				this.DefoCode = null;
			else
				this.DefoCode = rs.getString("DefoCode").trim();

			if( rs.getString("DefoName") == null )
				this.DefoName = null;
			else
				this.DefoName = rs.getString("DefoName").trim();

			if( rs.getString("DeformityReason") == null )
				this.DeformityReason = null;
			else
				this.DeformityReason = rs.getString("DeformityReason").trim();

			this.DeformityRate = rs.getDouble("DeformityRate");
			this.AppDeformityRate = rs.getDouble("AppDeformityRate");
			this.RealRate = rs.getDouble("RealRate");
			if( rs.getString("AdjReason") == null )
				this.AdjReason = null;
			else
				this.AdjReason = rs.getString("AdjReason").trim();

			if( rs.getString("AdjRemark") == null )
				this.AdjRemark = null;
			else
				this.AdjRemark = rs.getString("AdjRemark").trim();

			if( rs.getString("No") == null )
				this.No = null;
			else
				this.No = rs.getString("No").trim();

			this.DianoseDate = rs.getDate("DianoseDate");
			if( rs.getString("DiagnoseDesc") == null )
				this.DiagnoseDesc = null;
			else
				this.DiagnoseDesc = rs.getString("DiagnoseDesc").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			if( rs.getString("DoctorNo") == null )
				this.DoctorNo = null;
			else
				this.DoctorNo = rs.getString("DoctorNo").trim();

			if( rs.getString("DoctorName") == null )
				this.DoctorName = null;
			else
				this.DoctorName = rs.getString("DoctorName").trim();

			if( rs.getString("JudgeOrgan") == null )
				this.JudgeOrgan = null;
			else
				this.JudgeOrgan = rs.getString("JudgeOrgan").trim();

			if( rs.getString("JudgeOrganName") == null )
				this.JudgeOrganName = null;
			else
				this.JudgeOrganName = rs.getString("JudgeOrganName").trim();

			this.JudgeDate = rs.getDate("JudgeDate");
			if( rs.getString("JudgeDepend") == null )
				this.JudgeDepend = null;
			else
				this.JudgeDepend = rs.getString("JudgeDepend").trim();

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

			if( rs.getString("PartCode") == null )
				this.PartCode = null;
			else
				this.PartCode = rs.getString("PartCode").trim();

			if( rs.getString("DefoCodeName") == null )
				this.DefoCodeName = null;
			else
				this.DefoCodeName = rs.getString("DefoCodeName").trim();

			if( rs.getString("DefoClass") == null )
				this.DefoClass = null;
			else
				this.DefoClass = rs.getString("DefoClass").trim();

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
			logger.debug("数据库中的LLCaseInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseInfoSchema getSchema()
	{
		LLCaseInfoSchema aLLCaseInfoSchema = new LLCaseInfoSchema();
		aLLCaseInfoSchema.setSchema(this);
		return aLLCaseInfoSchema;
	}

	public LLCaseInfoDB getDB()
	{
		LLCaseInfoDB aDBOper = new LLCaseInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Diagnosis)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeformityReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeformityRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppDeformityRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(No)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DianoseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiagnoseDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoctorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoctorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeOrgan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeOrganName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( JudgeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeDepend)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PartCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoCodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AffixNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Diagnosis = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ClmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DefoGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			DefoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DefoName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			DeformityReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			DeformityRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			AppDeformityRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			RealRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			No = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			DianoseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			DiagnoseDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			DoctorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			DoctorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			JudgeOrgan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			JudgeOrganName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			JudgeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			JudgeDepend = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			PartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			DefoCodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			DefoClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseInfoSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("AffixNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Diagnosis"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Diagnosis));
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFlag));
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("DefoGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoGrade));
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoCode));
		}
		if (FCode.equalsIgnoreCase("DefoName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoName));
		}
		if (FCode.equalsIgnoreCase("DeformityReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeformityReason));
		}
		if (FCode.equalsIgnoreCase("DeformityRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeformityRate));
		}
		if (FCode.equalsIgnoreCase("AppDeformityRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppDeformityRate));
		}
		if (FCode.equalsIgnoreCase("RealRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealRate));
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjReason));
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjRemark));
		}
		if (FCode.equalsIgnoreCase("No"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(No));
		}
		if (FCode.equalsIgnoreCase("DianoseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDianoseDate()));
		}
		if (FCode.equalsIgnoreCase("DiagnoseDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiagnoseDesc));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("DoctorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoctorNo));
		}
		if (FCode.equalsIgnoreCase("DoctorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoctorName));
		}
		if (FCode.equalsIgnoreCase("JudgeOrgan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeOrgan));
		}
		if (FCode.equalsIgnoreCase("JudgeOrganName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeOrganName));
		}
		if (FCode.equalsIgnoreCase("JudgeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getJudgeDate()));
		}
		if (FCode.equalsIgnoreCase("JudgeDepend"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeDepend));
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
		if (FCode.equalsIgnoreCase("PartCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PartCode));
		}
		if (FCode.equalsIgnoreCase("DefoCodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoCodeName));
		}
		if (FCode.equalsIgnoreCase("DefoClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoClass));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AffixNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Diagnosis);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ClmFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DefoGrade);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DefoCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(DefoName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(DeformityReason);
				break;
			case 18:
				strFieldValue = String.valueOf(DeformityRate);
				break;
			case 19:
				strFieldValue = String.valueOf(AppDeformityRate);
				break;
			case 20:
				strFieldValue = String.valueOf(RealRate);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(No);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDianoseDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(DiagnoseDesc);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(DoctorNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(DoctorName);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(JudgeOrgan);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(JudgeOrganName);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJudgeDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(JudgeDepend);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(PartCode);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(DefoCodeName);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(DefoClass);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 44:
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
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
		if (FCode.equalsIgnoreCase("AffixNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixNo = FValue.trim();
			}
			else
				AffixNo = null;
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
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
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
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoType = FValue.trim();
			}
			else
				DefoType = null;
		}
		if (FCode.equalsIgnoreCase("DefoGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoGrade = FValue.trim();
			}
			else
				DefoGrade = null;
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoCode = FValue.trim();
			}
			else
				DefoCode = null;
		}
		if (FCode.equalsIgnoreCase("DefoName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoName = FValue.trim();
			}
			else
				DefoName = null;
		}
		if (FCode.equalsIgnoreCase("DeformityReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeformityReason = FValue.trim();
			}
			else
				DeformityReason = null;
		}
		if (FCode.equalsIgnoreCase("DeformityRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DeformityRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AppDeformityRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AppDeformityRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjReason = FValue.trim();
			}
			else
				AdjReason = null;
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjRemark = FValue.trim();
			}
			else
				AdjRemark = null;
		}
		if (FCode.equalsIgnoreCase("No"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				No = FValue.trim();
			}
			else
				No = null;
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
		if (FCode.equalsIgnoreCase("DiagnoseDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiagnoseDesc = FValue.trim();
			}
			else
				DiagnoseDesc = null;
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
		if (FCode.equalsIgnoreCase("JudgeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				JudgeDate = fDate.getDate( FValue );
			}
			else
				JudgeDate = null;
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
		if (FCode.equalsIgnoreCase("PartCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PartCode = FValue.trim();
			}
			else
				PartCode = null;
		}
		if (FCode.equalsIgnoreCase("DefoCodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoCodeName = FValue.trim();
			}
			else
				DefoCodeName = null;
		}
		if (FCode.equalsIgnoreCase("DefoClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoClass = FValue.trim();
			}
			else
				DefoClass = null;
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
		LLCaseInfoSchema other = (LLCaseInfoSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& SerialNo.equals(other.getSerialNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& RgtNo.equals(other.getRgtNo())
			&& AffixNo.equals(other.getAffixNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& Type.equals(other.getType())
			&& Code.equals(other.getCode())
			&& Name.equals(other.getName())
			&& Diagnosis.equals(other.getDiagnosis())
			&& ClmFlag.equals(other.getClmFlag())
			&& DefoType.equals(other.getDefoType())
			&& DefoGrade.equals(other.getDefoGrade())
			&& DefoCode.equals(other.getDefoCode())
			&& DefoName.equals(other.getDefoName())
			&& DeformityReason.equals(other.getDeformityReason())
			&& DeformityRate == other.getDeformityRate()
			&& AppDeformityRate == other.getAppDeformityRate()
			&& RealRate == other.getRealRate()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& No.equals(other.getNo())
			&& fDate.getString(DianoseDate).equals(other.getDianoseDate())
			&& DiagnoseDesc.equals(other.getDiagnoseDesc())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& DoctorNo.equals(other.getDoctorNo())
			&& DoctorName.equals(other.getDoctorName())
			&& JudgeOrgan.equals(other.getJudgeOrgan())
			&& JudgeOrganName.equals(other.getJudgeOrganName())
			&& fDate.getString(JudgeDate).equals(other.getJudgeDate())
			&& JudgeDepend.equals(other.getJudgeDepend())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PartCode.equals(other.getPartCode())
			&& DefoCodeName.equals(other.getDefoCodeName())
			&& DefoClass.equals(other.getDefoClass())
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
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 3;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 4;
		}
		if( strFieldName.equals("AffixNo") ) {
			return 5;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 6;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 7;
		}
		if( strFieldName.equals("Type") ) {
			return 8;
		}
		if( strFieldName.equals("Code") ) {
			return 9;
		}
		if( strFieldName.equals("Name") ) {
			return 10;
		}
		if( strFieldName.equals("Diagnosis") ) {
			return 11;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return 12;
		}
		if( strFieldName.equals("DefoType") ) {
			return 13;
		}
		if( strFieldName.equals("DefoGrade") ) {
			return 14;
		}
		if( strFieldName.equals("DefoCode") ) {
			return 15;
		}
		if( strFieldName.equals("DefoName") ) {
			return 16;
		}
		if( strFieldName.equals("DeformityReason") ) {
			return 17;
		}
		if( strFieldName.equals("DeformityRate") ) {
			return 18;
		}
		if( strFieldName.equals("AppDeformityRate") ) {
			return 19;
		}
		if( strFieldName.equals("RealRate") ) {
			return 20;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 21;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 22;
		}
		if( strFieldName.equals("No") ) {
			return 23;
		}
		if( strFieldName.equals("DianoseDate") ) {
			return 24;
		}
		if( strFieldName.equals("DiagnoseDesc") ) {
			return 25;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 26;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 27;
		}
		if( strFieldName.equals("DoctorNo") ) {
			return 28;
		}
		if( strFieldName.equals("DoctorName") ) {
			return 29;
		}
		if( strFieldName.equals("JudgeOrgan") ) {
			return 30;
		}
		if( strFieldName.equals("JudgeOrganName") ) {
			return 31;
		}
		if( strFieldName.equals("JudgeDate") ) {
			return 32;
		}
		if( strFieldName.equals("JudgeDepend") ) {
			return 33;
		}
		if( strFieldName.equals("MngCom") ) {
			return 34;
		}
		if( strFieldName.equals("Operator") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 39;
		}
		if( strFieldName.equals("PartCode") ) {
			return 40;
		}
		if( strFieldName.equals("DefoCodeName") ) {
			return 41;
		}
		if( strFieldName.equals("DefoClass") ) {
			return 42;
		}
		if( strFieldName.equals("ComCode") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 44;
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
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "CaseRelaNo";
				break;
			case 4:
				strFieldName = "RgtNo";
				break;
			case 5:
				strFieldName = "AffixNo";
				break;
			case 6:
				strFieldName = "CustomerNo";
				break;
			case 7:
				strFieldName = "CustomerName";
				break;
			case 8:
				strFieldName = "Type";
				break;
			case 9:
				strFieldName = "Code";
				break;
			case 10:
				strFieldName = "Name";
				break;
			case 11:
				strFieldName = "Diagnosis";
				break;
			case 12:
				strFieldName = "ClmFlag";
				break;
			case 13:
				strFieldName = "DefoType";
				break;
			case 14:
				strFieldName = "DefoGrade";
				break;
			case 15:
				strFieldName = "DefoCode";
				break;
			case 16:
				strFieldName = "DefoName";
				break;
			case 17:
				strFieldName = "DeformityReason";
				break;
			case 18:
				strFieldName = "DeformityRate";
				break;
			case 19:
				strFieldName = "AppDeformityRate";
				break;
			case 20:
				strFieldName = "RealRate";
				break;
			case 21:
				strFieldName = "AdjReason";
				break;
			case 22:
				strFieldName = "AdjRemark";
				break;
			case 23:
				strFieldName = "No";
				break;
			case 24:
				strFieldName = "DianoseDate";
				break;
			case 25:
				strFieldName = "DiagnoseDesc";
				break;
			case 26:
				strFieldName = "HospitalCode";
				break;
			case 27:
				strFieldName = "HospitalName";
				break;
			case 28:
				strFieldName = "DoctorNo";
				break;
			case 29:
				strFieldName = "DoctorName";
				break;
			case 30:
				strFieldName = "JudgeOrgan";
				break;
			case 31:
				strFieldName = "JudgeOrganName";
				break;
			case 32:
				strFieldName = "JudgeDate";
				break;
			case 33:
				strFieldName = "JudgeDepend";
				break;
			case 34:
				strFieldName = "MngCom";
				break;
			case 35:
				strFieldName = "Operator";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyDate";
				break;
			case 39:
				strFieldName = "ModifyTime";
				break;
			case 40:
				strFieldName = "PartCode";
				break;
			case 41:
				strFieldName = "DefoCodeName";
				break;
			case 42:
				strFieldName = "DefoClass";
				break;
			case 43:
				strFieldName = "ComCode";
				break;
			case 44:
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Diagnosis") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeformityReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeformityRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppDeformityRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("No") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DianoseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DiagnoseDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoctorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoctorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeOrgan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeOrganName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("JudgeDepend") ) {
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
		if( strFieldName.equals("PartCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoCodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoClass") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
