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
import com.sinosoft.lis.db.LLFeeMainDB;

/*
 * <p>ClassName: LLFeeMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLFeeMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLFeeMainSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 账单号 */
	private String MainFeeNo;
	/** 事故号 */
	private String CaseRelaNo;
	/** 立案号(申请登记号) */
	private String RgtNo;
	/** 附件代码 */
	private String AffixNo;
	/** (附件)流水号 */
	private int SerialNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 出险人性别 */
	private String CustomerSex;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 医院等级 */
	private String HospitalGrade;
	/** 责任类型 */
	private String DutyKind;
	/** 账单号码 */
	private String ReceiptNo;
	/** 账单种类 */
	private String FeeType;
	/** 账单属性 */
	private String FeeAtti;
	/** 帐单类型 */
	private String FeeAffixType;
	/** 账单日期 */
	private Date FeeDate;
	/** 住院起始日期 */
	private Date HospStartDate;
	/** 住院结??日期 */
	private Date HospEndDate;
	/** 实际住院天数 */
	private int RealHospDate;
	/** 重症监护天数 */
	private int SeriousWard;
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
	/** 币别 */
	private String Currency;
	/** 账单金额 */
	private double BillMoney;
	/** 扣除金额 */
	private double RefuseMoney;
	/** 合理金额 */
	private double AdjSum;
	/** 扣款说明 */
	private String DeductDesc;
	/** 备注 */
	private String Remark;
	/** 其他说明 */
	private String OtherExplain;
	/** 影像扫描件序号 */
	private String ECMNO;
	/** 主要诊断编码 */
	private String AccResult1;
	/** 诊断详情编码 */
	private String AccResult2;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLFeeMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "MainFeeNo";
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
		LLFeeMainSchema cloned = (LLFeeMainSchema)super.clone();
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
	* 系统生成代码
	*/
	public String getAffixNo()
	{
		return AffixNo;
	}
	public void setAffixNo(String aAffixNo)
	{
		if(aAffixNo!=null && aAffixNo.length()>20)
			throw new IllegalArgumentException("附件代码AffixNo值"+aAffixNo+"的长度"+aAffixNo.length()+"大于最大值20");
		AffixNo = aAffixNo;
	}
	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
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
		if(aHospitalName!=null && aHospitalName.length()>600)
			throw new IllegalArgumentException("医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值600");
		HospitalName = aHospitalName;
	}
	public String getHospitalGrade()
	{
		return HospitalGrade;
	}
	public void setHospitalGrade(String aHospitalGrade)
	{
		if(aHospitalGrade!=null && aHospitalGrade.length()>10)
			throw new IllegalArgumentException("医院等级HospitalGrade值"+aHospitalGrade+"的长度"+aHospitalGrade.length()+"大于最大值10");
		HospitalGrade = aHospitalGrade;
	}
	public String getDutyKind()
	{
		return DutyKind;
	}
	public void setDutyKind(String aDutyKind)
	{
		if(aDutyKind!=null && aDutyKind.length()>2)
			throw new IllegalArgumentException("责任类型DutyKind值"+aDutyKind+"的长度"+aDutyKind.length()+"大于最大值2");
		DutyKind = aDutyKind;
	}
	public String getReceiptNo()
	{
		return ReceiptNo;
	}
	public void setReceiptNo(String aReceiptNo)
	{
		if(aReceiptNo!=null && aReceiptNo.length()>20)
			throw new IllegalArgumentException("账单号码ReceiptNo值"+aReceiptNo+"的长度"+aReceiptNo.length()+"大于最大值20");
		ReceiptNo = aReceiptNo;
	}
	/**
	* 0 门诊、<p>
	* [不用]1 住院
	*/
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		if(aFeeType!=null && aFeeType.length()>2)
			throw new IllegalArgumentException("账单种类FeeType值"+aFeeType+"的长度"+aFeeType.length()+"大于最大值2");
		FeeType = aFeeType;
	}
	/**
	* [不用]医院、社保结算后
	*/
	public String getFeeAtti()
	{
		return FeeAtti;
	}
	public void setFeeAtti(String aFeeAtti)
	{
		if(aFeeAtti!=null && aFeeAtti.length()>2)
			throw new IllegalArgumentException("账单属性FeeAtti值"+aFeeAtti+"的长度"+aFeeAtti.length()+"大于最大值2");
		FeeAtti = aFeeAtti;
	}
	/**
	* [不用]原件，复印件
	*/
	public String getFeeAffixType()
	{
		return FeeAffixType;
	}
	public void setFeeAffixType(String aFeeAffixType)
	{
		if(aFeeAffixType!=null && aFeeAffixType.length()>2)
			throw new IllegalArgumentException("帐单类型FeeAffixType值"+aFeeAffixType+"的长度"+aFeeAffixType.length()+"大于最大值2");
		FeeAffixType = aFeeAffixType;
	}
	public String getFeeDate()
	{
		if( FeeDate != null )
			return fDate.getString(FeeDate);
		else
			return null;
	}
	public void setFeeDate(Date aFeeDate)
	{
		FeeDate = aFeeDate;
	}
	public void setFeeDate(String aFeeDate)
	{
		if (aFeeDate != null && !aFeeDate.equals("") )
		{
			FeeDate = fDate.getDate( aFeeDate );
		}
		else
			FeeDate = null;
	}

	public String getHospStartDate()
	{
		if( HospStartDate != null )
			return fDate.getString(HospStartDate);
		else
			return null;
	}
	public void setHospStartDate(Date aHospStartDate)
	{
		HospStartDate = aHospStartDate;
	}
	public void setHospStartDate(String aHospStartDate)
	{
		if (aHospStartDate != null && !aHospStartDate.equals("") )
		{
			HospStartDate = fDate.getDate( aHospStartDate );
		}
		else
			HospStartDate = null;
	}

	public String getHospEndDate()
	{
		if( HospEndDate != null )
			return fDate.getString(HospEndDate);
		else
			return null;
	}
	public void setHospEndDate(Date aHospEndDate)
	{
		HospEndDate = aHospEndDate;
	}
	public void setHospEndDate(String aHospEndDate)
	{
		if (aHospEndDate != null && !aHospEndDate.equals("") )
		{
			HospEndDate = fDate.getDate( aHospEndDate );
		}
		else
			HospEndDate = null;
	}

	public int getRealHospDate()
	{
		return RealHospDate;
	}
	public void setRealHospDate(int aRealHospDate)
	{
		RealHospDate = aRealHospDate;
	}
	public void setRealHospDate(String aRealHospDate)
	{
		if (aRealHospDate != null && !aRealHospDate.equals(""))
		{
			Integer tInteger = new Integer(aRealHospDate);
			int i = tInteger.intValue();
			RealHospDate = i;
		}
	}

	public int getSeriousWard()
	{
		return SeriousWard;
	}
	public void setSeriousWard(int aSeriousWard)
	{
		SeriousWard = aSeriousWard;
	}
	public void setSeriousWard(String aSeriousWard)
	{
		if (aSeriousWard != null && !aSeriousWard.equals(""))
		{
			Integer tInteger = new Integer(aSeriousWard);
			int i = tInteger.intValue();
			SeriousWard = i;
		}
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
	public double getBillMoney()
	{
		return BillMoney;
	}
	public void setBillMoney(double aBillMoney)
	{
		BillMoney = aBillMoney;
	}
	public void setBillMoney(String aBillMoney)
	{
		if (aBillMoney != null && !aBillMoney.equals(""))
		{
			Double tDouble = new Double(aBillMoney);
			double d = tDouble.doubleValue();
			BillMoney = d;
		}
	}

	/**
	* 参与计算的金额，已经扣除了责任外的金额
	*/
	public double getRefuseMoney()
	{
		return RefuseMoney;
	}
	public void setRefuseMoney(double aRefuseMoney)
	{
		RefuseMoney = aRefuseMoney;
	}
	public void setRefuseMoney(String aRefuseMoney)
	{
		if (aRefuseMoney != null && !aRefuseMoney.equals(""))
		{
			Double tDouble = new Double(aRefuseMoney);
			double d = tDouble.doubleValue();
			RefuseMoney = d;
		}
	}

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

	public String getDeductDesc()
	{
		return DeductDesc;
	}
	public void setDeductDesc(String aDeductDesc)
	{
		if(aDeductDesc!=null && aDeductDesc.length()>1000)
			throw new IllegalArgumentException("扣款说明DeductDesc值"+aDeductDesc+"的长度"+aDeductDesc.length()+"大于最大值1000");
		DeductDesc = aDeductDesc;
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
	public String getOtherExplain()
	{
		return OtherExplain;
	}
	public void setOtherExplain(String aOtherExplain)
	{
		if(aOtherExplain!=null && aOtherExplain.length()>600)
			throw new IllegalArgumentException("其他说明OtherExplain值"+aOtherExplain+"的长度"+aOtherExplain.length()+"大于最大值600");
		OtherExplain = aOtherExplain;
	}
	public String getECMNO()
	{
		return ECMNO;
	}
	public void setECMNO(String aECMNO)
	{
		if(aECMNO!=null && aECMNO.length()>600)
			throw new IllegalArgumentException("影像扫描件序号ECMNO值"+aECMNO+"的长度"+aECMNO.length()+"大于最大值600");
		ECMNO = aECMNO;
	}
	public String getAccResult1()
	{
		return AccResult1;
	}
	public void setAccResult1(String aAccResult1)
	{
		if(aAccResult1!=null && aAccResult1.length()>60)
			throw new IllegalArgumentException("主要诊断编码AccResult1值"+aAccResult1+"的长度"+aAccResult1.length()+"大于最大值60");
		AccResult1 = aAccResult1;
	}
	public String getAccResult2()
	{
		return AccResult2;
	}
	public void setAccResult2(String aAccResult2)
	{
		if(aAccResult2!=null && aAccResult2.length()>60)
			throw new IllegalArgumentException("诊断详情编码AccResult2值"+aAccResult2+"的长度"+aAccResult2.length()+"大于最大值60");
		AccResult2 = aAccResult2;
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
	* 使用另外一个 LLFeeMainSchema 对象给 Schema 赋值
	* @param: aLLFeeMainSchema LLFeeMainSchema
	**/
	public void setSchema(LLFeeMainSchema aLLFeeMainSchema)
	{
		this.ClmNo = aLLFeeMainSchema.getClmNo();
		this.CaseNo = aLLFeeMainSchema.getCaseNo();
		this.MainFeeNo = aLLFeeMainSchema.getMainFeeNo();
		this.CaseRelaNo = aLLFeeMainSchema.getCaseRelaNo();
		this.RgtNo = aLLFeeMainSchema.getRgtNo();
		this.AffixNo = aLLFeeMainSchema.getAffixNo();
		this.SerialNo = aLLFeeMainSchema.getSerialNo();
		this.CustomerNo = aLLFeeMainSchema.getCustomerNo();
		this.CustomerName = aLLFeeMainSchema.getCustomerName();
		this.CustomerSex = aLLFeeMainSchema.getCustomerSex();
		this.HospitalCode = aLLFeeMainSchema.getHospitalCode();
		this.HospitalName = aLLFeeMainSchema.getHospitalName();
		this.HospitalGrade = aLLFeeMainSchema.getHospitalGrade();
		this.DutyKind = aLLFeeMainSchema.getDutyKind();
		this.ReceiptNo = aLLFeeMainSchema.getReceiptNo();
		this.FeeType = aLLFeeMainSchema.getFeeType();
		this.FeeAtti = aLLFeeMainSchema.getFeeAtti();
		this.FeeAffixType = aLLFeeMainSchema.getFeeAffixType();
		this.FeeDate = fDate.getDate( aLLFeeMainSchema.getFeeDate());
		this.HospStartDate = fDate.getDate( aLLFeeMainSchema.getHospStartDate());
		this.HospEndDate = fDate.getDate( aLLFeeMainSchema.getHospEndDate());
		this.RealHospDate = aLLFeeMainSchema.getRealHospDate();
		this.SeriousWard = aLLFeeMainSchema.getSeriousWard();
		this.MngCom = aLLFeeMainSchema.getMngCom();
		this.Operator = aLLFeeMainSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLFeeMainSchema.getMakeDate());
		this.MakeTime = aLLFeeMainSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLFeeMainSchema.getModifyDate());
		this.ModifyTime = aLLFeeMainSchema.getModifyTime();
		this.Currency = aLLFeeMainSchema.getCurrency();
		this.BillMoney = aLLFeeMainSchema.getBillMoney();
		this.RefuseMoney = aLLFeeMainSchema.getRefuseMoney();
		this.AdjSum = aLLFeeMainSchema.getAdjSum();
		this.DeductDesc = aLLFeeMainSchema.getDeductDesc();
		this.Remark = aLLFeeMainSchema.getRemark();
		this.OtherExplain = aLLFeeMainSchema.getOtherExplain();
		this.ECMNO = aLLFeeMainSchema.getECMNO();
		this.AccResult1 = aLLFeeMainSchema.getAccResult1();
		this.AccResult2 = aLLFeeMainSchema.getAccResult2();
		this.ComCode = aLLFeeMainSchema.getComCode();
		this.ModifyOperator = aLLFeeMainSchema.getModifyOperator();
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

			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("CustomerSex") == null )
				this.CustomerSex = null;
			else
				this.CustomerSex = rs.getString("CustomerSex").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			if( rs.getString("HospitalGrade") == null )
				this.HospitalGrade = null;
			else
				this.HospitalGrade = rs.getString("HospitalGrade").trim();

			if( rs.getString("DutyKind") == null )
				this.DutyKind = null;
			else
				this.DutyKind = rs.getString("DutyKind").trim();

			if( rs.getString("ReceiptNo") == null )
				this.ReceiptNo = null;
			else
				this.ReceiptNo = rs.getString("ReceiptNo").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			if( rs.getString("FeeAtti") == null )
				this.FeeAtti = null;
			else
				this.FeeAtti = rs.getString("FeeAtti").trim();

			if( rs.getString("FeeAffixType") == null )
				this.FeeAffixType = null;
			else
				this.FeeAffixType = rs.getString("FeeAffixType").trim();

			this.FeeDate = rs.getDate("FeeDate");
			this.HospStartDate = rs.getDate("HospStartDate");
			this.HospEndDate = rs.getDate("HospEndDate");
			this.RealHospDate = rs.getInt("RealHospDate");
			this.SeriousWard = rs.getInt("SeriousWard");
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

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.BillMoney = rs.getDouble("BillMoney");
			this.RefuseMoney = rs.getDouble("RefuseMoney");
			this.AdjSum = rs.getDouble("AdjSum");
			if( rs.getString("DeductDesc") == null )
				this.DeductDesc = null;
			else
				this.DeductDesc = rs.getString("DeductDesc").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("OtherExplain") == null )
				this.OtherExplain = null;
			else
				this.OtherExplain = rs.getString("OtherExplain").trim();

			if( rs.getString("ECMNO") == null )
				this.ECMNO = null;
			else
				this.ECMNO = rs.getString("ECMNO").trim();

			if( rs.getString("AccResult1") == null )
				this.AccResult1 = null;
			else
				this.AccResult1 = rs.getString("AccResult1").trim();

			if( rs.getString("AccResult2") == null )
				this.AccResult2 = null;
			else
				this.AccResult2 = rs.getString("AccResult2").trim();

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
			logger.debug("数据库中的LLFeeMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLFeeMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLFeeMainSchema getSchema()
	{
		LLFeeMainSchema aLLFeeMainSchema = new LLFeeMainSchema();
		aLLFeeMainSchema.setSchema(this);
		return aLLFeeMainSchema;
	}

	public LLFeeMainDB getDB()
	{
		LLFeeMainDB aDBOper = new LLFeeMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLFeeMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeAtti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeAffixType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FeeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HospStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HospEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealHospDate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SeriousWard));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BillMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RefuseMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeductDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherExplain)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ECMNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLFeeMain>历史记账凭证主表信息</A>表字段
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
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AffixNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CustomerSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			HospitalGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			DutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ReceiptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			FeeAtti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			FeeAffixType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			FeeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			HospStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			HospEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			RealHospDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			SeriousWard= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			BillMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			RefuseMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			AdjSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			DeductDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			OtherExplain = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			ECMNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			AccResult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLFeeMainSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("CustomerSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerSex));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("HospitalGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalGrade));
		}
		if (FCode.equalsIgnoreCase("DutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyKind));
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptNo));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("FeeAtti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeAtti));
		}
		if (FCode.equalsIgnoreCase("FeeAffixType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeAffixType));
		}
		if (FCode.equalsIgnoreCase("FeeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFeeDate()));
		}
		if (FCode.equalsIgnoreCase("HospStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHospStartDate()));
		}
		if (FCode.equalsIgnoreCase("HospEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHospEndDate()));
		}
		if (FCode.equalsIgnoreCase("RealHospDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealHospDate));
		}
		if (FCode.equalsIgnoreCase("SeriousWard"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeriousWard));
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("BillMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillMoney));
		}
		if (FCode.equalsIgnoreCase("RefuseMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RefuseMoney));
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjSum));
		}
		if (FCode.equalsIgnoreCase("DeductDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeductDesc));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("OtherExplain"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherExplain));
		}
		if (FCode.equalsIgnoreCase("ECMNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ECMNO));
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult1));
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult2));
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
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AffixNo);
				break;
			case 6:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CustomerSex);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(HospitalGrade);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(DutyKind);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ReceiptNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(FeeAtti);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(FeeAffixType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFeeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHospStartDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHospEndDate()));
				break;
			case 21:
				strFieldValue = String.valueOf(RealHospDate);
				break;
			case 22:
				strFieldValue = String.valueOf(SeriousWard);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 30:
				strFieldValue = String.valueOf(BillMoney);
				break;
			case 31:
				strFieldValue = String.valueOf(RefuseMoney);
				break;
			case 32:
				strFieldValue = String.valueOf(AdjSum);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(DeductDesc);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OtherExplain);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(ECMNO);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(AccResult1);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 40:
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
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
		if (FCode.equalsIgnoreCase("CustomerSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerSex = FValue.trim();
			}
			else
				CustomerSex = null;
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
		if (FCode.equalsIgnoreCase("HospitalGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalGrade = FValue.trim();
			}
			else
				HospitalGrade = null;
		}
		if (FCode.equalsIgnoreCase("DutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyKind = FValue.trim();
			}
			else
				DutyKind = null;
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
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		if (FCode.equalsIgnoreCase("FeeAtti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeAtti = FValue.trim();
			}
			else
				FeeAtti = null;
		}
		if (FCode.equalsIgnoreCase("FeeAffixType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeAffixType = FValue.trim();
			}
			else
				FeeAffixType = null;
		}
		if (FCode.equalsIgnoreCase("FeeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FeeDate = fDate.getDate( FValue );
			}
			else
				FeeDate = null;
		}
		if (FCode.equalsIgnoreCase("HospStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				HospStartDate = fDate.getDate( FValue );
			}
			else
				HospStartDate = null;
		}
		if (FCode.equalsIgnoreCase("HospEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				HospEndDate = fDate.getDate( FValue );
			}
			else
				HospEndDate = null;
		}
		if (FCode.equalsIgnoreCase("RealHospDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RealHospDate = i;
			}
		}
		if (FCode.equalsIgnoreCase("SeriousWard"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SeriousWard = i;
			}
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("BillMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BillMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("RefuseMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RefuseMoney = d;
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
		if (FCode.equalsIgnoreCase("DeductDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeductDesc = FValue.trim();
			}
			else
				DeductDesc = null;
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
		if (FCode.equalsIgnoreCase("OtherExplain"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherExplain = FValue.trim();
			}
			else
				OtherExplain = null;
		}
		if (FCode.equalsIgnoreCase("ECMNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ECMNO = FValue.trim();
			}
			else
				ECMNO = null;
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
		LLFeeMainSchema other = (LLFeeMainSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& MainFeeNo.equals(other.getMainFeeNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& RgtNo.equals(other.getRgtNo())
			&& AffixNo.equals(other.getAffixNo())
			&& SerialNo == other.getSerialNo()
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& CustomerSex.equals(other.getCustomerSex())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& HospitalGrade.equals(other.getHospitalGrade())
			&& DutyKind.equals(other.getDutyKind())
			&& ReceiptNo.equals(other.getReceiptNo())
			&& FeeType.equals(other.getFeeType())
			&& FeeAtti.equals(other.getFeeAtti())
			&& FeeAffixType.equals(other.getFeeAffixType())
			&& fDate.getString(FeeDate).equals(other.getFeeDate())
			&& fDate.getString(HospStartDate).equals(other.getHospStartDate())
			&& fDate.getString(HospEndDate).equals(other.getHospEndDate())
			&& RealHospDate == other.getRealHospDate()
			&& SeriousWard == other.getSeriousWard()
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Currency.equals(other.getCurrency())
			&& BillMoney == other.getBillMoney()
			&& RefuseMoney == other.getRefuseMoney()
			&& AdjSum == other.getAdjSum()
			&& DeductDesc.equals(other.getDeductDesc())
			&& Remark.equals(other.getRemark())
			&& OtherExplain.equals(other.getOtherExplain())
			&& ECMNO.equals(other.getECMNO())
			&& AccResult1.equals(other.getAccResult1())
			&& AccResult2.equals(other.getAccResult2())
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
		if( strFieldName.equals("CaseRelaNo") ) {
			return 3;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 4;
		}
		if( strFieldName.equals("AffixNo") ) {
			return 5;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 6;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 7;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 8;
		}
		if( strFieldName.equals("CustomerSex") ) {
			return 9;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 10;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 11;
		}
		if( strFieldName.equals("HospitalGrade") ) {
			return 12;
		}
		if( strFieldName.equals("DutyKind") ) {
			return 13;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return 14;
		}
		if( strFieldName.equals("FeeType") ) {
			return 15;
		}
		if( strFieldName.equals("FeeAtti") ) {
			return 16;
		}
		if( strFieldName.equals("FeeAffixType") ) {
			return 17;
		}
		if( strFieldName.equals("FeeDate") ) {
			return 18;
		}
		if( strFieldName.equals("HospStartDate") ) {
			return 19;
		}
		if( strFieldName.equals("HospEndDate") ) {
			return 20;
		}
		if( strFieldName.equals("RealHospDate") ) {
			return 21;
		}
		if( strFieldName.equals("SeriousWard") ) {
			return 22;
		}
		if( strFieldName.equals("MngCom") ) {
			return 23;
		}
		if( strFieldName.equals("Operator") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 28;
		}
		if( strFieldName.equals("Currency") ) {
			return 29;
		}
		if( strFieldName.equals("BillMoney") ) {
			return 30;
		}
		if( strFieldName.equals("RefuseMoney") ) {
			return 31;
		}
		if( strFieldName.equals("AdjSum") ) {
			return 32;
		}
		if( strFieldName.equals("DeductDesc") ) {
			return 33;
		}
		if( strFieldName.equals("Remark") ) {
			return 34;
		}
		if( strFieldName.equals("OtherExplain") ) {
			return 35;
		}
		if( strFieldName.equals("ECMNO") ) {
			return 36;
		}
		if( strFieldName.equals("AccResult1") ) {
			return 37;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 38;
		}
		if( strFieldName.equals("ComCode") ) {
			return 39;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 40;
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
				strFieldName = "CaseRelaNo";
				break;
			case 4:
				strFieldName = "RgtNo";
				break;
			case 5:
				strFieldName = "AffixNo";
				break;
			case 6:
				strFieldName = "SerialNo";
				break;
			case 7:
				strFieldName = "CustomerNo";
				break;
			case 8:
				strFieldName = "CustomerName";
				break;
			case 9:
				strFieldName = "CustomerSex";
				break;
			case 10:
				strFieldName = "HospitalCode";
				break;
			case 11:
				strFieldName = "HospitalName";
				break;
			case 12:
				strFieldName = "HospitalGrade";
				break;
			case 13:
				strFieldName = "DutyKind";
				break;
			case 14:
				strFieldName = "ReceiptNo";
				break;
			case 15:
				strFieldName = "FeeType";
				break;
			case 16:
				strFieldName = "FeeAtti";
				break;
			case 17:
				strFieldName = "FeeAffixType";
				break;
			case 18:
				strFieldName = "FeeDate";
				break;
			case 19:
				strFieldName = "HospStartDate";
				break;
			case 20:
				strFieldName = "HospEndDate";
				break;
			case 21:
				strFieldName = "RealHospDate";
				break;
			case 22:
				strFieldName = "SeriousWard";
				break;
			case 23:
				strFieldName = "MngCom";
				break;
			case 24:
				strFieldName = "Operator";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
				strFieldName = "MakeTime";
				break;
			case 27:
				strFieldName = "ModifyDate";
				break;
			case 28:
				strFieldName = "ModifyTime";
				break;
			case 29:
				strFieldName = "Currency";
				break;
			case 30:
				strFieldName = "BillMoney";
				break;
			case 31:
				strFieldName = "RefuseMoney";
				break;
			case 32:
				strFieldName = "AdjSum";
				break;
			case 33:
				strFieldName = "DeductDesc";
				break;
			case 34:
				strFieldName = "Remark";
				break;
			case 35:
				strFieldName = "OtherExplain";
				break;
			case 36:
				strFieldName = "ECMNO";
				break;
			case 37:
				strFieldName = "AccResult1";
				break;
			case 38:
				strFieldName = "AccResult2";
				break;
			case 39:
				strFieldName = "ComCode";
				break;
			case 40:
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
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeAtti") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeAffixType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("HospStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("HospEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RealHospDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SeriousWard") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RefuseMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeductDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherExplain") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ECMNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult2") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
