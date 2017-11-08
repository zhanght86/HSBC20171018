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
import com.sinosoft.lis.db.LLCaseReceiptDB;

/*
 * <p>ClassName: LLCaseReceiptSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLCaseReceiptSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseReceiptSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 账单号 */
	private String MainFeeNo;
	/** 账单费用明细 */
	private String FeeDetailNo;
	/** 立案号(申请登记号) */
	private String RgtNo;
	/** 费用项目类型 */
	private String FeeItemType;
	/** 费用项目代码 */
	private String FeeItemCode;
	/** 费用项目名称 */
	private String FeeItemName;
	/** 费用金额 */
	private double Fee;
	/** 调整金额 */
	private double AdjSum;
	/** 调整原因 */
	private String AdjReason;
	/** 调整备注 */
	private String AdjRemark;
	/** 起始日期 */
	private Date StartDate;
	/** 结束日期 */
	private Date EndDate;
	/** 实际天数 */
	private String DayCount;
	/** 处理标记 */
	private String DealFlag;
	/** 有效标记 */
	private String AvaliFlag;
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
	/** 先期给付 */
	private double PreAmnt;
	/** 自费金额 */
	private double SelfAmnt;
	/** 不合理费用 */
	private double RefuseAmnt;
	/** 分割单受益金额 */
	private double CutApartAmnt;
	/** 住院次数 */
	private int HospNum;
	/** 住院起付线 */
	private double HospLineAmnt;
	/** 疾病代码 */
	private String DiseaseCode;
	/** 社保赔付费用 */
	private double SecurityAmnt;
	/** 疾病名称 */
	private String DiseaseName;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 币别 */
	private String Currency;
	/** 部分保额 */
	private double PartlyselfAmnt;
	/** 不合理保额 */
	private double UnreasonAmnt;
	/** 备注 */
	private String Remark;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseReceiptSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "MainFeeNo";
		pk[3] = "FeeDetailNo";

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
		LLCaseReceiptSchema cloned = (LLCaseReceiptSchema)super.clone();
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
	* 如果是个险，等同于个险赔案号<p>
	* 如果是团险，等同于团险赔案号
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
	public String getMainFeeNo()
	{
		return MainFeeNo;
	}
	public void setMainFeeNo(String aMainFeeNo)
	{
		if(aMainFeeNo!=null && aMainFeeNo.length()>60)
			throw new IllegalArgumentException("账单号MainFeeNo值"+aMainFeeNo+"的长度"+aMainFeeNo.length()+"大于最大值60");
		MainFeeNo = aMainFeeNo;
	}
	public String getFeeDetailNo()
	{
		return FeeDetailNo;
	}
	public void setFeeDetailNo(String aFeeDetailNo)
	{
		if(aFeeDetailNo!=null && aFeeDetailNo.length()>20)
			throw new IllegalArgumentException("账单费用明细FeeDetailNo值"+aFeeDetailNo+"的长度"+aFeeDetailNo.length()+"大于最大值20");
		FeeDetailNo = aFeeDetailNo;
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
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号(申请登记号)RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	/**
	* A  门诊<p>
	* B  住院<p>
	* C  费用补偿
	*/
	public String getFeeItemType()
	{
		return FeeItemType;
	}
	public void setFeeItemType(String aFeeItemType)
	{
		if(aFeeItemType!=null && aFeeItemType.length()>6)
			throw new IllegalArgumentException("费用项目类型FeeItemType值"+aFeeItemType+"的长度"+aFeeItemType.length()+"大于最大值6");
		FeeItemType = aFeeItemType;
	}
	/**
	* A―门诊	<p>
	* AX001	医疗费-门诊医疗费<p>
	* 	<p>
	* AL001	治疗费-治疗费不细分<p>
	* AL002	治疗费-一般护理费               <p>
	* AL003	治疗费-材料费                   <p>
	* AL004	治疗费-抢救费                   <p>
	* 	<p>
	* AJ001	检查费-不细分                   <p>
	* AJ002	检查费-B超                      <p>
	* AJ003	检查费-CT                       <p>
	* AJ004	检查费-核磁共振(ARI)            <p>
	* AJ005	检查费-放射费                   <p>
	* 	<p>
	* AH001	化验费-不细分<p>
	* 	<p>
	* AY001	药费-不细分                     <p>
	* AY002	药费-西药费                     <p>
	* AY003	药费-中药费                     <p>
	* AY004	药费-草药费                     <p>
	* 	<p>
	* 	<p>
	* AC001	床位费-不细分                   <p>
	* AC002	床位费-重症监护/烧伤病房床位费  <p>
	* AC003	床位费-婴儿床位费               <p>
	* 	<p>
	* AS001	手术费-不细分<p>
	* 	<p>
	* AQ001	其它-不细分<p>
	* AQ002	其它-伙食费<p>
	* AQ003	其它-空调费<p>
	* AQ004	其它-陪住费<p>
	* AQ005	其它-特殊护理费<p>
	* AQ006	其它-住院、转院救护车费<p>
	* AQ007	其它-材料费<p>
	* AQ008	其它-接生费<p>
	* AQ009	其它-取暖费<p>
	* AQ010	其它-挂号费<p>
	* AQ011	其它-停尸费<p>
	* AQ012	其它-病历费<p>
	* <p>
	* <p>
	* <p>
	* <p>
	* B―住院	<p>
	* BX001	医疗费-住院医疗费<p>
	* 	<p>
	* BL001	治疗费-治疗费不细分<p>
	* BL002	治疗费-一般护理费<p>
	* BL003	治疗费-材料费<p>
	* BL004	治疗费-抢救费<p>
	* 	<p>
	* BJ001	检查费-不细分<p>
	* BJ002	检查费-B超<p>
	* BJ003	检查费-CT<p>
	* BJ004	检查费-核磁共振(ARI)<p>
	* BJ005	检查费-放射费<p>
	* 	<p>
	* BH001	化验费-不细分<p>
	* 	<p>
	* BY001	药费-不细分        <p>
	* BY002	药费-西药费<p>
	* BY003	药费-中药费<p>
	* BY004	药费-草药费<p>
	* 	<p>
	* 	<p>
	* BC001	床位费-不细分<p>
	* BC002	床位费-重症监护/烧伤病房床位费<p>
	* BC003	床位费-婴儿床位费<p>
	* 	<p>
	* BS001	手术费-不细分<p>
	* 	<p>
	* BQ001	其它-不细分<p>
	* BQ002	其它-伙食费<p>
	* BQ003	其它-空调费<p>
	* BQ004	其它-陪住费<p>
	* BQ005	其它-特殊护理费<p>
	* BQ006	其它-住院、转院救护车费<p>
	* BQ007	其它-材料费<p>
	* BQ008	其它-接生费<p>
	* BQ009	其它-取暖费<p>
	* BQ011	其它-停尸费<p>
	* BQ012	其它-病历费<p>
	* 	<p>
	* <p>
	* <p>
	* C―费用补偿	<p>
	* CA001	门诊医疗费-住院前<p>
	* CA002	门诊医疗费-住院后<p>
	* CBC001	住院医疗费-基本医疗内床位费<p>
	* CBQ001	住院医疗费-基本医疗内其他医疗费用<p>
	* CBN001	住院医疗费-基本医疗内药品费<p>
	* CBW001	住院医疗费-基本医疗外药品费
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

	/**
	* 参与计算的金额，已经扣除了责任外的金额
	*/
	public double getAdjSum()
	{
		return AdjSum;
	}
	public void setAdjSum(double aAdjSum)
	{
		AdjSum = aAdjSum;
	}
	public void setAdjSum(String aAdjSum)
	{
		if (aAdjSum != null && !aAdjSum.equals(""))
		{
			Double tDouble = new Double(aAdjSum);
			double d = tDouble.doubleValue();
			AdjSum = d;
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
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getDayCount()
	{
		return DayCount;
	}
	public void setDayCount(String aDayCount)
	{
		if(aDayCount!=null && aDayCount.length()>6)
			throw new IllegalArgumentException("实际天数DayCount值"+aDayCount+"的长度"+aDayCount.length()+"大于最大值6");
		DayCount = aDayCount;
	}
	/**
	* 1-正常费用<p>
	* 0-出险前费用
	*/
	public String getDealFlag()
	{
		return DealFlag;
	}
	public void setDealFlag(String aDealFlag)
	{
		if(aDealFlag!=null && aDealFlag.length()>6)
			throw new IllegalArgumentException("处理标记DealFlag值"+aDealFlag+"的长度"+aDealFlag.length()+"大于最大值6");
		DealFlag = aDealFlag;
	}
	/**
	* [不用]
	*/
	public String getAvaliFlag()
	{
		return AvaliFlag;
	}
	public void setAvaliFlag(String aAvaliFlag)
	{
		if(aAvaliFlag!=null && aAvaliFlag.length()>1)
			throw new IllegalArgumentException("有效标记AvaliFlag值"+aAvaliFlag+"的长度"+aAvaliFlag.length()+"大于最大值1");
		AvaliFlag = aAvaliFlag;
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
	public double getPreAmnt()
	{
		return PreAmnt;
	}
	public void setPreAmnt(double aPreAmnt)
	{
		PreAmnt = aPreAmnt;
	}
	public void setPreAmnt(String aPreAmnt)
	{
		if (aPreAmnt != null && !aPreAmnt.equals(""))
		{
			Double tDouble = new Double(aPreAmnt);
			double d = tDouble.doubleValue();
			PreAmnt = d;
		}
	}

	public double getSelfAmnt()
	{
		return SelfAmnt;
	}
	public void setSelfAmnt(double aSelfAmnt)
	{
		SelfAmnt = aSelfAmnt;
	}
	public void setSelfAmnt(String aSelfAmnt)
	{
		if (aSelfAmnt != null && !aSelfAmnt.equals(""))
		{
			Double tDouble = new Double(aSelfAmnt);
			double d = tDouble.doubleValue();
			SelfAmnt = d;
		}
	}

	public double getRefuseAmnt()
	{
		return RefuseAmnt;
	}
	public void setRefuseAmnt(double aRefuseAmnt)
	{
		RefuseAmnt = aRefuseAmnt;
	}
	public void setRefuseAmnt(String aRefuseAmnt)
	{
		if (aRefuseAmnt != null && !aRefuseAmnt.equals(""))
		{
			Double tDouble = new Double(aRefuseAmnt);
			double d = tDouble.doubleValue();
			RefuseAmnt = d;
		}
	}

	public double getCutApartAmnt()
	{
		return CutApartAmnt;
	}
	public void setCutApartAmnt(double aCutApartAmnt)
	{
		CutApartAmnt = aCutApartAmnt;
	}
	public void setCutApartAmnt(String aCutApartAmnt)
	{
		if (aCutApartAmnt != null && !aCutApartAmnt.equals(""))
		{
			Double tDouble = new Double(aCutApartAmnt);
			double d = tDouble.doubleValue();
			CutApartAmnt = d;
		}
	}

	public int getHospNum()
	{
		return HospNum;
	}
	public void setHospNum(int aHospNum)
	{
		HospNum = aHospNum;
	}
	public void setHospNum(String aHospNum)
	{
		if (aHospNum != null && !aHospNum.equals(""))
		{
			Integer tInteger = new Integer(aHospNum);
			int i = tInteger.intValue();
			HospNum = i;
		}
	}

	public double getHospLineAmnt()
	{
		return HospLineAmnt;
	}
	public void setHospLineAmnt(double aHospLineAmnt)
	{
		HospLineAmnt = aHospLineAmnt;
	}
	public void setHospLineAmnt(String aHospLineAmnt)
	{
		if (aHospLineAmnt != null && !aHospLineAmnt.equals(""))
		{
			Double tDouble = new Double(aHospLineAmnt);
			double d = tDouble.doubleValue();
			HospLineAmnt = d;
		}
	}

	public String getDiseaseCode()
	{
		return DiseaseCode;
	}
	public void setDiseaseCode(String aDiseaseCode)
	{
		if(aDiseaseCode!=null && aDiseaseCode.length()>20)
			throw new IllegalArgumentException("疾病代码DiseaseCode值"+aDiseaseCode+"的长度"+aDiseaseCode.length()+"大于最大值20");
		DiseaseCode = aDiseaseCode;
	}
	public double getSecurityAmnt()
	{
		return SecurityAmnt;
	}
	public void setSecurityAmnt(double aSecurityAmnt)
	{
		SecurityAmnt = aSecurityAmnt;
	}
	public void setSecurityAmnt(String aSecurityAmnt)
	{
		if (aSecurityAmnt != null && !aSecurityAmnt.equals(""))
		{
			Double tDouble = new Double(aSecurityAmnt);
			double d = tDouble.doubleValue();
			SecurityAmnt = d;
		}
	}

	public String getDiseaseName()
	{
		return DiseaseName;
	}
	public void setDiseaseName(String aDiseaseName)
	{
		if(aDiseaseName!=null && aDiseaseName.length()>200)
			throw new IllegalArgumentException("疾病名称DiseaseName值"+aDiseaseName+"的长度"+aDiseaseName.length()+"大于最大值200");
		DiseaseName = aDiseaseName;
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
	public double getPartlyselfAmnt()
	{
		return PartlyselfAmnt;
	}
	public void setPartlyselfAmnt(double aPartlyselfAmnt)
	{
		PartlyselfAmnt = aPartlyselfAmnt;
	}
	public void setPartlyselfAmnt(String aPartlyselfAmnt)
	{
		if (aPartlyselfAmnt != null && !aPartlyselfAmnt.equals(""))
		{
			Double tDouble = new Double(aPartlyselfAmnt);
			double d = tDouble.doubleValue();
			PartlyselfAmnt = d;
		}
	}

	public double getUnreasonAmnt()
	{
		return UnreasonAmnt;
	}
	public void setUnreasonAmnt(double aUnreasonAmnt)
	{
		UnreasonAmnt = aUnreasonAmnt;
	}
	public void setUnreasonAmnt(String aUnreasonAmnt)
	{
		if (aUnreasonAmnt != null && !aUnreasonAmnt.equals(""))
		{
			Double tDouble = new Double(aUnreasonAmnt);
			double d = tDouble.doubleValue();
			UnreasonAmnt = d;
		}
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
	* 使用另外一个 LLCaseReceiptSchema 对象给 Schema 赋值
	* @param: aLLCaseReceiptSchema LLCaseReceiptSchema
	**/
	public void setSchema(LLCaseReceiptSchema aLLCaseReceiptSchema)
	{
		this.ClmNo = aLLCaseReceiptSchema.getClmNo();
		this.CaseNo = aLLCaseReceiptSchema.getCaseNo();
		this.MainFeeNo = aLLCaseReceiptSchema.getMainFeeNo();
		this.FeeDetailNo = aLLCaseReceiptSchema.getFeeDetailNo();
		this.RgtNo = aLLCaseReceiptSchema.getRgtNo();
		this.FeeItemType = aLLCaseReceiptSchema.getFeeItemType();
		this.FeeItemCode = aLLCaseReceiptSchema.getFeeItemCode();
		this.FeeItemName = aLLCaseReceiptSchema.getFeeItemName();
		this.Fee = aLLCaseReceiptSchema.getFee();
		this.AdjSum = aLLCaseReceiptSchema.getAdjSum();
		this.AdjReason = aLLCaseReceiptSchema.getAdjReason();
		this.AdjRemark = aLLCaseReceiptSchema.getAdjRemark();
		this.StartDate = fDate.getDate( aLLCaseReceiptSchema.getStartDate());
		this.EndDate = fDate.getDate( aLLCaseReceiptSchema.getEndDate());
		this.DayCount = aLLCaseReceiptSchema.getDayCount();
		this.DealFlag = aLLCaseReceiptSchema.getDealFlag();
		this.AvaliFlag = aLLCaseReceiptSchema.getAvaliFlag();
		this.MngCom = aLLCaseReceiptSchema.getMngCom();
		this.Operator = aLLCaseReceiptSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCaseReceiptSchema.getMakeDate());
		this.MakeTime = aLLCaseReceiptSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCaseReceiptSchema.getModifyDate());
		this.ModifyTime = aLLCaseReceiptSchema.getModifyTime();
		this.PreAmnt = aLLCaseReceiptSchema.getPreAmnt();
		this.SelfAmnt = aLLCaseReceiptSchema.getSelfAmnt();
		this.RefuseAmnt = aLLCaseReceiptSchema.getRefuseAmnt();
		this.CutApartAmnt = aLLCaseReceiptSchema.getCutApartAmnt();
		this.HospNum = aLLCaseReceiptSchema.getHospNum();
		this.HospLineAmnt = aLLCaseReceiptSchema.getHospLineAmnt();
		this.DiseaseCode = aLLCaseReceiptSchema.getDiseaseCode();
		this.SecurityAmnt = aLLCaseReceiptSchema.getSecurityAmnt();
		this.DiseaseName = aLLCaseReceiptSchema.getDiseaseName();
		this.CustomerNo = aLLCaseReceiptSchema.getCustomerNo();
		this.Currency = aLLCaseReceiptSchema.getCurrency();
		this.PartlyselfAmnt = aLLCaseReceiptSchema.getPartlyselfAmnt();
		this.UnreasonAmnt = aLLCaseReceiptSchema.getUnreasonAmnt();
		this.Remark = aLLCaseReceiptSchema.getRemark();
		this.ComCode = aLLCaseReceiptSchema.getComCode();
		this.ModifyOperator = aLLCaseReceiptSchema.getModifyOperator();
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

			if( rs.getString("MainFeeNo") == null )
				this.MainFeeNo = null;
			else
				this.MainFeeNo = rs.getString("MainFeeNo").trim();

			if( rs.getString("FeeDetailNo") == null )
				this.FeeDetailNo = null;
			else
				this.FeeDetailNo = rs.getString("FeeDetailNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("FeeItemType") == null )
				this.FeeItemType = null;
			else
				this.FeeItemType = rs.getString("FeeItemType").trim();

			if( rs.getString("FeeItemCode") == null )
				this.FeeItemCode = null;
			else
				this.FeeItemCode = rs.getString("FeeItemCode").trim();

			if( rs.getString("FeeItemName") == null )
				this.FeeItemName = null;
			else
				this.FeeItemName = rs.getString("FeeItemName").trim();

			this.Fee = rs.getDouble("Fee");
			this.AdjSum = rs.getDouble("AdjSum");
			if( rs.getString("AdjReason") == null )
				this.AdjReason = null;
			else
				this.AdjReason = rs.getString("AdjReason").trim();

			if( rs.getString("AdjRemark") == null )
				this.AdjRemark = null;
			else
				this.AdjRemark = rs.getString("AdjRemark").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("DayCount") == null )
				this.DayCount = null;
			else
				this.DayCount = rs.getString("DayCount").trim();

			if( rs.getString("DealFlag") == null )
				this.DealFlag = null;
			else
				this.DealFlag = rs.getString("DealFlag").trim();

			if( rs.getString("AvaliFlag") == null )
				this.AvaliFlag = null;
			else
				this.AvaliFlag = rs.getString("AvaliFlag").trim();

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

			this.PreAmnt = rs.getDouble("PreAmnt");
			this.SelfAmnt = rs.getDouble("SelfAmnt");
			this.RefuseAmnt = rs.getDouble("RefuseAmnt");
			this.CutApartAmnt = rs.getDouble("CutApartAmnt");
			this.HospNum = rs.getInt("HospNum");
			this.HospLineAmnt = rs.getDouble("HospLineAmnt");
			if( rs.getString("DiseaseCode") == null )
				this.DiseaseCode = null;
			else
				this.DiseaseCode = rs.getString("DiseaseCode").trim();

			this.SecurityAmnt = rs.getDouble("SecurityAmnt");
			if( rs.getString("DiseaseName") == null )
				this.DiseaseName = null;
			else
				this.DiseaseName = rs.getString("DiseaseName").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.PartlyselfAmnt = rs.getDouble("PartlyselfAmnt");
			this.UnreasonAmnt = rs.getDouble("UnreasonAmnt");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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
			logger.debug("数据库中的LLCaseReceipt表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseReceiptSchema getSchema()
	{
		LLCaseReceiptSchema aLLCaseReceiptSchema = new LLCaseReceiptSchema();
		aLLCaseReceiptSchema.setSchema(this);
		return aLLCaseReceiptSchema;
	}

	public LLCaseReceiptDB getDB()
	{
		LLCaseReceiptDB aDBOper = new LLCaseReceiptDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseReceipt描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeDetailNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DayCount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvaliFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SelfAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RefuseAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CutApartAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HospNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HospLineAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SecurityAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PartlyselfAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UnreasonAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseReceipt>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MainFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FeeDetailNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FeeItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FeeItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			AdjSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			DayCount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			DealFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AvaliFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PreAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			SelfAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			RefuseAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			CutApartAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			HospNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			HospLineAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			DiseaseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			SecurityAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			DiseaseName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			PartlyselfAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			UnreasonAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptSchema";
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
		if (FCode.equalsIgnoreCase("MainFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainFeeNo));
		}
		if (FCode.equalsIgnoreCase("FeeDetailNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeDetailNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemType));
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
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjSum));
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjReason));
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjRemark));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("DayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DayCount));
		}
		if (FCode.equalsIgnoreCase("DealFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealFlag));
		}
		if (FCode.equalsIgnoreCase("AvaliFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvaliFlag));
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
		if (FCode.equalsIgnoreCase("PreAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreAmnt));
		}
		if (FCode.equalsIgnoreCase("SelfAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SelfAmnt));
		}
		if (FCode.equalsIgnoreCase("RefuseAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RefuseAmnt));
		}
		if (FCode.equalsIgnoreCase("CutApartAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutApartAmnt));
		}
		if (FCode.equalsIgnoreCase("HospNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospNum));
		}
		if (FCode.equalsIgnoreCase("HospLineAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospLineAmnt));
		}
		if (FCode.equalsIgnoreCase("DiseaseCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseCode));
		}
		if (FCode.equalsIgnoreCase("SecurityAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecurityAmnt));
		}
		if (FCode.equalsIgnoreCase("DiseaseName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseName));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("PartlyselfAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PartlyselfAmnt));
		}
		if (FCode.equalsIgnoreCase("UnreasonAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnreasonAmnt));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(MainFeeNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeDetailNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FeeItemType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FeeItemCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FeeItemName);
				break;
			case 8:
				strFieldValue = String.valueOf(Fee);
				break;
			case 9:
				strFieldValue = String.valueOf(AdjSum);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DayCount);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DealFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AvaliFlag);
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
				strFieldValue = String.valueOf(PreAmnt);
				break;
			case 24:
				strFieldValue = String.valueOf(SelfAmnt);
				break;
			case 25:
				strFieldValue = String.valueOf(RefuseAmnt);
				break;
			case 26:
				strFieldValue = String.valueOf(CutApartAmnt);
				break;
			case 27:
				strFieldValue = String.valueOf(HospNum);
				break;
			case 28:
				strFieldValue = String.valueOf(HospLineAmnt);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(DiseaseCode);
				break;
			case 30:
				strFieldValue = String.valueOf(SecurityAmnt);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(DiseaseName);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 34:
				strFieldValue = String.valueOf(PartlyselfAmnt);
				break;
			case 35:
				strFieldValue = String.valueOf(UnreasonAmnt);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 38:
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
		if (FCode.equalsIgnoreCase("MainFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainFeeNo = FValue.trim();
			}
			else
				MainFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeDetailNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeDetailNo = FValue.trim();
			}
			else
				FeeDetailNo = null;
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
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemType = FValue.trim();
			}
			else
				FeeItemType = null;
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
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjSum = d;
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
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("DayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DayCount = FValue.trim();
			}
			else
				DayCount = null;
		}
		if (FCode.equalsIgnoreCase("DealFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealFlag = FValue.trim();
			}
			else
				DealFlag = null;
		}
		if (FCode.equalsIgnoreCase("AvaliFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvaliFlag = FValue.trim();
			}
			else
				AvaliFlag = null;
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
		if (FCode.equalsIgnoreCase("PreAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("SelfAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SelfAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("RefuseAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RefuseAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("CutApartAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CutApartAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("HospNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				HospNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("HospLineAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				HospLineAmnt = d;
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
		if (FCode.equalsIgnoreCase("SecurityAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SecurityAmnt = d;
			}
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
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
		if (FCode.equalsIgnoreCase("PartlyselfAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PartlyselfAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("UnreasonAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnreasonAmnt = d;
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
		LLCaseReceiptSchema other = (LLCaseReceiptSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& MainFeeNo.equals(other.getMainFeeNo())
			&& FeeDetailNo.equals(other.getFeeDetailNo())
			&& RgtNo.equals(other.getRgtNo())
			&& FeeItemType.equals(other.getFeeItemType())
			&& FeeItemCode.equals(other.getFeeItemCode())
			&& FeeItemName.equals(other.getFeeItemName())
			&& Fee == other.getFee()
			&& AdjSum == other.getAdjSum()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& DayCount.equals(other.getDayCount())
			&& DealFlag.equals(other.getDealFlag())
			&& AvaliFlag.equals(other.getAvaliFlag())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PreAmnt == other.getPreAmnt()
			&& SelfAmnt == other.getSelfAmnt()
			&& RefuseAmnt == other.getRefuseAmnt()
			&& CutApartAmnt == other.getCutApartAmnt()
			&& HospNum == other.getHospNum()
			&& HospLineAmnt == other.getHospLineAmnt()
			&& DiseaseCode.equals(other.getDiseaseCode())
			&& SecurityAmnt == other.getSecurityAmnt()
			&& DiseaseName.equals(other.getDiseaseName())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Currency.equals(other.getCurrency())
			&& PartlyselfAmnt == other.getPartlyselfAmnt()
			&& UnreasonAmnt == other.getUnreasonAmnt()
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("MainFeeNo") ) {
			return 2;
		}
		if( strFieldName.equals("FeeDetailNo") ) {
			return 3;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 4;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return 5;
		}
		if( strFieldName.equals("FeeItemCode") ) {
			return 6;
		}
		if( strFieldName.equals("FeeItemName") ) {
			return 7;
		}
		if( strFieldName.equals("Fee") ) {
			return 8;
		}
		if( strFieldName.equals("AdjSum") ) {
			return 9;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 10;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 11;
		}
		if( strFieldName.equals("StartDate") ) {
			return 12;
		}
		if( strFieldName.equals("EndDate") ) {
			return 13;
		}
		if( strFieldName.equals("DayCount") ) {
			return 14;
		}
		if( strFieldName.equals("DealFlag") ) {
			return 15;
		}
		if( strFieldName.equals("AvaliFlag") ) {
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
		if( strFieldName.equals("PreAmnt") ) {
			return 23;
		}
		if( strFieldName.equals("SelfAmnt") ) {
			return 24;
		}
		if( strFieldName.equals("RefuseAmnt") ) {
			return 25;
		}
		if( strFieldName.equals("CutApartAmnt") ) {
			return 26;
		}
		if( strFieldName.equals("HospNum") ) {
			return 27;
		}
		if( strFieldName.equals("HospLineAmnt") ) {
			return 28;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return 29;
		}
		if( strFieldName.equals("SecurityAmnt") ) {
			return 30;
		}
		if( strFieldName.equals("DiseaseName") ) {
			return 31;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 32;
		}
		if( strFieldName.equals("Currency") ) {
			return 33;
		}
		if( strFieldName.equals("PartlyselfAmnt") ) {
			return 34;
		}
		if( strFieldName.equals("UnreasonAmnt") ) {
			return 35;
		}
		if( strFieldName.equals("Remark") ) {
			return 36;
		}
		if( strFieldName.equals("ComCode") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 38;
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
				strFieldName = "MainFeeNo";
				break;
			case 3:
				strFieldName = "FeeDetailNo";
				break;
			case 4:
				strFieldName = "RgtNo";
				break;
			case 5:
				strFieldName = "FeeItemType";
				break;
			case 6:
				strFieldName = "FeeItemCode";
				break;
			case 7:
				strFieldName = "FeeItemName";
				break;
			case 8:
				strFieldName = "Fee";
				break;
			case 9:
				strFieldName = "AdjSum";
				break;
			case 10:
				strFieldName = "AdjReason";
				break;
			case 11:
				strFieldName = "AdjRemark";
				break;
			case 12:
				strFieldName = "StartDate";
				break;
			case 13:
				strFieldName = "EndDate";
				break;
			case 14:
				strFieldName = "DayCount";
				break;
			case 15:
				strFieldName = "DealFlag";
				break;
			case 16:
				strFieldName = "AvaliFlag";
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
				strFieldName = "PreAmnt";
				break;
			case 24:
				strFieldName = "SelfAmnt";
				break;
			case 25:
				strFieldName = "RefuseAmnt";
				break;
			case 26:
				strFieldName = "CutApartAmnt";
				break;
			case 27:
				strFieldName = "HospNum";
				break;
			case 28:
				strFieldName = "HospLineAmnt";
				break;
			case 29:
				strFieldName = "DiseaseCode";
				break;
			case 30:
				strFieldName = "SecurityAmnt";
				break;
			case 31:
				strFieldName = "DiseaseName";
				break;
			case 32:
				strFieldName = "CustomerNo";
				break;
			case 33:
				strFieldName = "Currency";
				break;
			case 34:
				strFieldName = "PartlyselfAmnt";
				break;
			case 35:
				strFieldName = "UnreasonAmnt";
				break;
			case 36:
				strFieldName = "Remark";
				break;
			case 37:
				strFieldName = "ComCode";
				break;
			case 38:
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
		if( strFieldName.equals("MainFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeDetailNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeItemType") ) {
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
		if( strFieldName.equals("AdjSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DayCount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvaliFlag") ) {
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
		if( strFieldName.equals("PreAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SelfAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RefuseAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CutApartAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HospNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("HospLineAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecurityAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DiseaseName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PartlyselfAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UnreasonAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
