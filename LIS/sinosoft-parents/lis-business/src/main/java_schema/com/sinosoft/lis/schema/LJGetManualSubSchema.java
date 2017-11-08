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
import com.sinosoft.lis.db.LJGetManualSubDB;

/*
 * <p>ClassName: LJGetManualSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJGetManualSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJGetManualSubSchema.class);
	// @Field
	/** 发盘号 */
	private String TransNo;
	/** 申请批次号 */
	private String ApplyBatNo;
	/** 批次状态 */
	private String BatState;
	/** 付费号 */
	private String PayCode;
	/** 主业务号 */
	private String MainBussNo;
	/** 业务类型 */
	private String BussType;
	/** 业务号 */
	private String BussNo;
	/** 其他业务号 */
	private String OtherBussNo;
	/** 客户号码 */
	private String CustomerNo;
	/** 金额 */
	private double Money;
	/** 应付日期 */
	private Date ShouldDate;
	/** 客户开户行 */
	private String CustBankCode;
	/** 银行编码 */
	private String BankCode;
	/** 客户开户行省 */
	private String CustBankProvince;
	/** 客户开户行市 */
	private String CustBankCity;
	/** 客户开户行明细 */
	private String CustBankDetail;
	/** 客户银行账号 */
	private String CustBankAccNo;
	/** 客户账户名 */
	private String CustAccName;
	/** 共保公司编码 */
	private String InsuranceCom;
	/** 业务机构 */
	private String BussCom;
	/** 审核人员 */
	private String ConfirmOperator;
	/** 审核日期 */
	private Date ConfirmDate;
	/** 审核时间 */
	private String ConfirmTime;
	/** 审核结论 */
	private String ConfirmConclusion;
	/** 审核结论描述 */
	private String ConfirmDesc;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 到账日期 */
	private Date EnterAccDate;
	/** 申请提交日期 */
	private Date AppSubmitDate;
	/** 申请提交时间 */
	private String AppSubmitTime;
	/** 付费处理方式 */
	private String GetDealType;

	public static final int FIELDNUM = 37;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJGetManualSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TransNo";

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
		LJGetManualSubSchema cloned = (LJGetManualSubSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTransNo()
	{
		return TransNo;
	}
	public void setTransNo(String aTransNo)
	{
		if(aTransNo!=null && aTransNo.length()>20)
			throw new IllegalArgumentException("发盘号TransNo值"+aTransNo+"的长度"+aTransNo.length()+"大于最大值20");
		TransNo = aTransNo;
	}
	public String getApplyBatNo()
	{
		return ApplyBatNo;
	}
	public void setApplyBatNo(String aApplyBatNo)
	{
		if(aApplyBatNo!=null && aApplyBatNo.length()>20)
			throw new IllegalArgumentException("申请批次号ApplyBatNo值"+aApplyBatNo+"的长度"+aApplyBatNo.length()+"大于最大值20");
		ApplyBatNo = aApplyBatNo;
	}
	/**
	* 0-已申请待确认，1-待确认，2-确认成功，3-银行信息修改，4-撤销
	*/
	public String getBatState()
	{
		return BatState;
	}
	public void setBatState(String aBatState)
	{
		if(aBatState!=null && aBatState.length()>2)
			throw new IllegalArgumentException("批次状态BatState值"+aBatState+"的长度"+aBatState.length()+"大于最大值2");
		BatState = aBatState;
	}
	public String getPayCode()
	{
		return PayCode;
	}
	public void setPayCode(String aPayCode)
	{
		if(aPayCode!=null && aPayCode.length()>20)
			throw new IllegalArgumentException("付费号PayCode值"+aPayCode+"的长度"+aPayCode.length()+"大于最大值20");
		PayCode = aPayCode;
	}
	public String getMainBussNo()
	{
		return MainBussNo;
	}
	public void setMainBussNo(String aMainBussNo)
	{
		if(aMainBussNo!=null && aMainBussNo.length()>20)
			throw new IllegalArgumentException("主业务号MainBussNo值"+aMainBussNo+"的长度"+aMainBussNo.length()+"大于最大值20");
		MainBussNo = aMainBussNo;
	}
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>2)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值2");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getOtherBussNo()
	{
		return OtherBussNo;
	}
	public void setOtherBussNo(String aOtherBussNo)
	{
		if(aOtherBussNo!=null && aOtherBussNo.length()>20)
			throw new IllegalArgumentException("其他业务号OtherBussNo值"+aOtherBussNo+"的长度"+aOtherBussNo.length()+"大于最大值20");
		OtherBussNo = aOtherBussNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public double getMoney()
	{
		return Money;
	}
	public void setMoney(double aMoney)
	{
		Money = aMoney;
	}
	public void setMoney(String aMoney)
	{
		if (aMoney != null && !aMoney.equals(""))
		{
			Double tDouble = new Double(aMoney);
			double d = tDouble.doubleValue();
			Money = d;
		}
	}

	public String getShouldDate()
	{
		if( ShouldDate != null )
			return fDate.getString(ShouldDate);
		else
			return null;
	}
	public void setShouldDate(Date aShouldDate)
	{
		ShouldDate = aShouldDate;
	}
	public void setShouldDate(String aShouldDate)
	{
		if (aShouldDate != null && !aShouldDate.equals("") )
		{
			ShouldDate = fDate.getDate( aShouldDate );
		}
		else
			ShouldDate = null;
	}

	public String getCustBankCode()
	{
		return CustBankCode;
	}
	public void setCustBankCode(String aCustBankCode)
	{
		if(aCustBankCode!=null && aCustBankCode.length()>30)
			throw new IllegalArgumentException("客户开户行CustBankCode值"+aCustBankCode+"的长度"+aCustBankCode.length()+"大于最大值30");
		CustBankCode = aCustBankCode;
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
	public String getCustBankProvince()
	{
		return CustBankProvince;
	}
	public void setCustBankProvince(String aCustBankProvince)
	{
		if(aCustBankProvince!=null && aCustBankProvince.length()>30)
			throw new IllegalArgumentException("客户开户行省CustBankProvince值"+aCustBankProvince+"的长度"+aCustBankProvince.length()+"大于最大值30");
		CustBankProvince = aCustBankProvince;
	}
	public String getCustBankCity()
	{
		return CustBankCity;
	}
	public void setCustBankCity(String aCustBankCity)
	{
		if(aCustBankCity!=null && aCustBankCity.length()>30)
			throw new IllegalArgumentException("客户开户行市CustBankCity值"+aCustBankCity+"的长度"+aCustBankCity.length()+"大于最大值30");
		CustBankCity = aCustBankCity;
	}
	public String getCustBankDetail()
	{
		return CustBankDetail;
	}
	public void setCustBankDetail(String aCustBankDetail)
	{
		if(aCustBankDetail!=null && aCustBankDetail.length()>300)
			throw new IllegalArgumentException("客户开户行明细CustBankDetail值"+aCustBankDetail+"的长度"+aCustBankDetail.length()+"大于最大值300");
		CustBankDetail = aCustBankDetail;
	}
	public String getCustBankAccNo()
	{
		return CustBankAccNo;
	}
	public void setCustBankAccNo(String aCustBankAccNo)
	{
		if(aCustBankAccNo!=null && aCustBankAccNo.length()>30)
			throw new IllegalArgumentException("客户银行账号CustBankAccNo值"+aCustBankAccNo+"的长度"+aCustBankAccNo.length()+"大于最大值30");
		CustBankAccNo = aCustBankAccNo;
	}
	public String getCustAccName()
	{
		return CustAccName;
	}
	public void setCustAccName(String aCustAccName)
	{
		if(aCustAccName!=null && aCustAccName.length()>200)
			throw new IllegalArgumentException("客户账户名CustAccName值"+aCustAccName+"的长度"+aCustAccName.length()+"大于最大值200");
		CustAccName = aCustAccName;
	}
	public String getInsuranceCom()
	{
		return InsuranceCom;
	}
	public void setInsuranceCom(String aInsuranceCom)
	{
		if(aInsuranceCom!=null && aInsuranceCom.length()>20)
			throw new IllegalArgumentException("共保公司编码InsuranceCom值"+aInsuranceCom+"的长度"+aInsuranceCom.length()+"大于最大值20");
		InsuranceCom = aInsuranceCom;
	}
	public String getBussCom()
	{
		return BussCom;
	}
	public void setBussCom(String aBussCom)
	{
		if(aBussCom!=null && aBussCom.length()>20)
			throw new IllegalArgumentException("业务机构BussCom值"+aBussCom+"的长度"+aBussCom.length()+"大于最大值20");
		BussCom = aBussCom;
	}
	public String getConfirmOperator()
	{
		return ConfirmOperator;
	}
	public void setConfirmOperator(String aConfirmOperator)
	{
		if(aConfirmOperator!=null && aConfirmOperator.length()>30)
			throw new IllegalArgumentException("审核人员ConfirmOperator值"+aConfirmOperator+"的长度"+aConfirmOperator.length()+"大于最大值30");
		ConfirmOperator = aConfirmOperator;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	public String getConfirmTime()
	{
		return ConfirmTime;
	}
	public void setConfirmTime(String aConfirmTime)
	{
		if(aConfirmTime!=null && aConfirmTime.length()>8)
			throw new IllegalArgumentException("审核时间ConfirmTime值"+aConfirmTime+"的长度"+aConfirmTime.length()+"大于最大值8");
		ConfirmTime = aConfirmTime;
	}
	public String getConfirmConclusion()
	{
		return ConfirmConclusion;
	}
	public void setConfirmConclusion(String aConfirmConclusion)
	{
		if(aConfirmConclusion!=null && aConfirmConclusion.length()>2)
			throw new IllegalArgumentException("审核结论ConfirmConclusion值"+aConfirmConclusion+"的长度"+aConfirmConclusion.length()+"大于最大值2");
		ConfirmConclusion = aConfirmConclusion;
	}
	public String getConfirmDesc()
	{
		return ConfirmDesc;
	}
	public void setConfirmDesc(String aConfirmDesc)
	{
		if(aConfirmDesc!=null && aConfirmDesc.length()>3000)
			throw new IllegalArgumentException("审核结论描述ConfirmDesc值"+aConfirmDesc+"的长度"+aConfirmDesc.length()+"大于最大值3000");
		ConfirmDesc = aConfirmDesc;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
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
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getEnterAccDate()
	{
		if( EnterAccDate != null )
			return fDate.getString(EnterAccDate);
		else
			return null;
	}
	public void setEnterAccDate(Date aEnterAccDate)
	{
		EnterAccDate = aEnterAccDate;
	}
	public void setEnterAccDate(String aEnterAccDate)
	{
		if (aEnterAccDate != null && !aEnterAccDate.equals("") )
		{
			EnterAccDate = fDate.getDate( aEnterAccDate );
		}
		else
			EnterAccDate = null;
	}

	public String getAppSubmitDate()
	{
		if( AppSubmitDate != null )
			return fDate.getString(AppSubmitDate);
		else
			return null;
	}
	public void setAppSubmitDate(Date aAppSubmitDate)
	{
		AppSubmitDate = aAppSubmitDate;
	}
	public void setAppSubmitDate(String aAppSubmitDate)
	{
		if (aAppSubmitDate != null && !aAppSubmitDate.equals("") )
		{
			AppSubmitDate = fDate.getDate( aAppSubmitDate );
		}
		else
			AppSubmitDate = null;
	}

	public String getAppSubmitTime()
	{
		return AppSubmitTime;
	}
	public void setAppSubmitTime(String aAppSubmitTime)
	{
		if(aAppSubmitTime!=null && aAppSubmitTime.length()>8)
			throw new IllegalArgumentException("申请提交时间AppSubmitTime值"+aAppSubmitTime+"的长度"+aAppSubmitTime.length()+"大于最大值8");
		AppSubmitTime = aAppSubmitTime;
	}
	/**
	* 00-正常支付，01-转溢缴
	*/
	public String getGetDealType()
	{
		return GetDealType;
	}
	public void setGetDealType(String aGetDealType)
	{
		if(aGetDealType!=null && aGetDealType.length()>2)
			throw new IllegalArgumentException("付费处理方式GetDealType值"+aGetDealType+"的长度"+aGetDealType.length()+"大于最大值2");
		GetDealType = aGetDealType;
	}

	/**
	* 使用另外一个 LJGetManualSubSchema 对象给 Schema 赋值
	* @param: aLJGetManualSubSchema LJGetManualSubSchema
	**/
	public void setSchema(LJGetManualSubSchema aLJGetManualSubSchema)
	{
		this.TransNo = aLJGetManualSubSchema.getTransNo();
		this.ApplyBatNo = aLJGetManualSubSchema.getApplyBatNo();
		this.BatState = aLJGetManualSubSchema.getBatState();
		this.PayCode = aLJGetManualSubSchema.getPayCode();
		this.MainBussNo = aLJGetManualSubSchema.getMainBussNo();
		this.BussType = aLJGetManualSubSchema.getBussType();
		this.BussNo = aLJGetManualSubSchema.getBussNo();
		this.OtherBussNo = aLJGetManualSubSchema.getOtherBussNo();
		this.CustomerNo = aLJGetManualSubSchema.getCustomerNo();
		this.Money = aLJGetManualSubSchema.getMoney();
		this.ShouldDate = fDate.getDate( aLJGetManualSubSchema.getShouldDate());
		this.CustBankCode = aLJGetManualSubSchema.getCustBankCode();
		this.BankCode = aLJGetManualSubSchema.getBankCode();
		this.CustBankProvince = aLJGetManualSubSchema.getCustBankProvince();
		this.CustBankCity = aLJGetManualSubSchema.getCustBankCity();
		this.CustBankDetail = aLJGetManualSubSchema.getCustBankDetail();
		this.CustBankAccNo = aLJGetManualSubSchema.getCustBankAccNo();
		this.CustAccName = aLJGetManualSubSchema.getCustAccName();
		this.InsuranceCom = aLJGetManualSubSchema.getInsuranceCom();
		this.BussCom = aLJGetManualSubSchema.getBussCom();
		this.ConfirmOperator = aLJGetManualSubSchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJGetManualSubSchema.getConfirmDate());
		this.ConfirmTime = aLJGetManualSubSchema.getConfirmTime();
		this.ConfirmConclusion = aLJGetManualSubSchema.getConfirmConclusion();
		this.ConfirmDesc = aLJGetManualSubSchema.getConfirmDesc();
		this.ManageCom = aLJGetManualSubSchema.getManageCom();
		this.ComCode = aLJGetManualSubSchema.getComCode();
		this.MakeOperator = aLJGetManualSubSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJGetManualSubSchema.getMakeDate());
		this.MakeTime = aLJGetManualSubSchema.getMakeTime();
		this.ModifyOperator = aLJGetManualSubSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJGetManualSubSchema.getModifyDate());
		this.ModifyTime = aLJGetManualSubSchema.getModifyTime();
		this.EnterAccDate = fDate.getDate( aLJGetManualSubSchema.getEnterAccDate());
		this.AppSubmitDate = fDate.getDate( aLJGetManualSubSchema.getAppSubmitDate());
		this.AppSubmitTime = aLJGetManualSubSchema.getAppSubmitTime();
		this.GetDealType = aLJGetManualSubSchema.getGetDealType();
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
			if( rs.getString("TransNo") == null )
				this.TransNo = null;
			else
				this.TransNo = rs.getString("TransNo").trim();

			if( rs.getString("ApplyBatNo") == null )
				this.ApplyBatNo = null;
			else
				this.ApplyBatNo = rs.getString("ApplyBatNo").trim();

			if( rs.getString("BatState") == null )
				this.BatState = null;
			else
				this.BatState = rs.getString("BatState").trim();

			if( rs.getString("PayCode") == null )
				this.PayCode = null;
			else
				this.PayCode = rs.getString("PayCode").trim();

			if( rs.getString("MainBussNo") == null )
				this.MainBussNo = null;
			else
				this.MainBussNo = rs.getString("MainBussNo").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("OtherBussNo") == null )
				this.OtherBussNo = null;
			else
				this.OtherBussNo = rs.getString("OtherBussNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			this.Money = rs.getDouble("Money");
			this.ShouldDate = rs.getDate("ShouldDate");
			if( rs.getString("CustBankCode") == null )
				this.CustBankCode = null;
			else
				this.CustBankCode = rs.getString("CustBankCode").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("CustBankProvince") == null )
				this.CustBankProvince = null;
			else
				this.CustBankProvince = rs.getString("CustBankProvince").trim();

			if( rs.getString("CustBankCity") == null )
				this.CustBankCity = null;
			else
				this.CustBankCity = rs.getString("CustBankCity").trim();

			if( rs.getString("CustBankDetail") == null )
				this.CustBankDetail = null;
			else
				this.CustBankDetail = rs.getString("CustBankDetail").trim();

			if( rs.getString("CustBankAccNo") == null )
				this.CustBankAccNo = null;
			else
				this.CustBankAccNo = rs.getString("CustBankAccNo").trim();

			if( rs.getString("CustAccName") == null )
				this.CustAccName = null;
			else
				this.CustAccName = rs.getString("CustAccName").trim();

			if( rs.getString("InsuranceCom") == null )
				this.InsuranceCom = null;
			else
				this.InsuranceCom = rs.getString("InsuranceCom").trim();

			if( rs.getString("BussCom") == null )
				this.BussCom = null;
			else
				this.BussCom = rs.getString("BussCom").trim();

			if( rs.getString("ConfirmOperator") == null )
				this.ConfirmOperator = null;
			else
				this.ConfirmOperator = rs.getString("ConfirmOperator").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("ConfirmTime") == null )
				this.ConfirmTime = null;
			else
				this.ConfirmTime = rs.getString("ConfirmTime").trim();

			if( rs.getString("ConfirmConclusion") == null )
				this.ConfirmConclusion = null;
			else
				this.ConfirmConclusion = rs.getString("ConfirmConclusion").trim();

			if( rs.getString("ConfirmDesc") == null )
				this.ConfirmDesc = null;
			else
				this.ConfirmDesc = rs.getString("ConfirmDesc").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.AppSubmitDate = rs.getDate("AppSubmitDate");
			if( rs.getString("AppSubmitTime") == null )
				this.AppSubmitTime = null;
			else
				this.AppSubmitTime = rs.getString("AppSubmitTime").trim();

			if( rs.getString("GetDealType") == null )
				this.GetDealType = null;
			else
				this.GetDealType = rs.getString("GetDealType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJGetManualSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJGetManualSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJGetManualSubSchema getSchema()
	{
		LJGetManualSubSchema aLJGetManualSubSchema = new LJGetManualSubSchema();
		aLJGetManualSubSchema.setSchema(this);
		return aLJGetManualSubSchema;
	}

	public LJGetManualSubDB getDB()
	{
		LJGetManualSubDB aDBOper = new LJGetManualSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJGetManualSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TransNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyBatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Money));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ShouldDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuranceCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppSubmitDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppSubmitTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDealType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJGetManualSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TransNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ApplyBatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MainBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OtherBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			ShouldDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			CustBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CustBankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CustBankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CustBankDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CustBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CustAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InsuranceCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BussCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			AppSubmitDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			AppSubmitTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			GetDealType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJGetManualSubSchema";
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
		if (FCode.equalsIgnoreCase("TransNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransNo));
		}
		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyBatNo));
		}
		if (FCode.equalsIgnoreCase("BatState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatState));
		}
		if (FCode.equalsIgnoreCase("PayCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCode));
		}
		if (FCode.equalsIgnoreCase("MainBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainBussNo));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("OtherBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherBussNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Money));
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCode));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("CustBankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankProvince));
		}
		if (FCode.equalsIgnoreCase("CustBankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCity));
		}
		if (FCode.equalsIgnoreCase("CustBankDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankDetail));
		}
		if (FCode.equalsIgnoreCase("CustBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankAccNo));
		}
		if (FCode.equalsIgnoreCase("CustAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustAccName));
		}
		if (FCode.equalsIgnoreCase("InsuranceCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuranceCom));
		}
		if (FCode.equalsIgnoreCase("BussCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussCom));
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmOperator));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmConclusion));
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmDesc));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("AppSubmitDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppSubmitDate()));
		}
		if (FCode.equalsIgnoreCase("AppSubmitTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppSubmitTime));
		}
		if (FCode.equalsIgnoreCase("GetDealType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDealType));
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
				strFieldValue = StrTool.GBKToUnicode(TransNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ApplyBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatState);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MainBussNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OtherBussNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 9:
				strFieldValue = String.valueOf(Money);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CustBankCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CustBankProvince);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CustBankCity);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CustBankDetail);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CustBankAccNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CustAccName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InsuranceCom);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BussCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppSubmitDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AppSubmitTime);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(GetDealType);
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

		if (FCode.equalsIgnoreCase("TransNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransNo = FValue.trim();
			}
			else
				TransNo = null;
		}
		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyBatNo = FValue.trim();
			}
			else
				ApplyBatNo = null;
		}
		if (FCode.equalsIgnoreCase("BatState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatState = FValue.trim();
			}
			else
				BatState = null;
		}
		if (FCode.equalsIgnoreCase("PayCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayCode = FValue.trim();
			}
			else
				PayCode = null;
		}
		if (FCode.equalsIgnoreCase("MainBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainBussNo = FValue.trim();
			}
			else
				MainBussNo = null;
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherBussNo = FValue.trim();
			}
			else
				OtherBussNo = null;
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
		if (FCode.equalsIgnoreCase("Money"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Money = d;
			}
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ShouldDate = fDate.getDate( FValue );
			}
			else
				ShouldDate = null;
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCode = FValue.trim();
			}
			else
				CustBankCode = null;
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
		if (FCode.equalsIgnoreCase("CustBankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankProvince = FValue.trim();
			}
			else
				CustBankProvince = null;
		}
		if (FCode.equalsIgnoreCase("CustBankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCity = FValue.trim();
			}
			else
				CustBankCity = null;
		}
		if (FCode.equalsIgnoreCase("CustBankDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankDetail = FValue.trim();
			}
			else
				CustBankDetail = null;
		}
		if (FCode.equalsIgnoreCase("CustBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankAccNo = FValue.trim();
			}
			else
				CustBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("CustAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustAccName = FValue.trim();
			}
			else
				CustAccName = null;
		}
		if (FCode.equalsIgnoreCase("InsuranceCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuranceCom = FValue.trim();
			}
			else
				InsuranceCom = null;
		}
		if (FCode.equalsIgnoreCase("BussCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussCom = FValue.trim();
			}
			else
				BussCom = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmOperator = FValue.trim();
			}
			else
				ConfirmOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmTime = FValue.trim();
			}
			else
				ConfirmTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmConclusion = FValue.trim();
			}
			else
				ConfirmConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmDesc = FValue.trim();
			}
			else
				ConfirmDesc = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnterAccDate = fDate.getDate( FValue );
			}
			else
				EnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("AppSubmitDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppSubmitDate = fDate.getDate( FValue );
			}
			else
				AppSubmitDate = null;
		}
		if (FCode.equalsIgnoreCase("AppSubmitTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppSubmitTime = FValue.trim();
			}
			else
				AppSubmitTime = null;
		}
		if (FCode.equalsIgnoreCase("GetDealType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDealType = FValue.trim();
			}
			else
				GetDealType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJGetManualSubSchema other = (LJGetManualSubSchema)otherObject;
		return
			TransNo.equals(other.getTransNo())
			&& ApplyBatNo.equals(other.getApplyBatNo())
			&& BatState.equals(other.getBatState())
			&& PayCode.equals(other.getPayCode())
			&& MainBussNo.equals(other.getMainBussNo())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& OtherBussNo.equals(other.getOtherBussNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Money == other.getMoney()
			&& fDate.getString(ShouldDate).equals(other.getShouldDate())
			&& CustBankCode.equals(other.getCustBankCode())
			&& BankCode.equals(other.getBankCode())
			&& CustBankProvince.equals(other.getCustBankProvince())
			&& CustBankCity.equals(other.getCustBankCity())
			&& CustBankDetail.equals(other.getCustBankDetail())
			&& CustBankAccNo.equals(other.getCustBankAccNo())
			&& CustAccName.equals(other.getCustAccName())
			&& InsuranceCom.equals(other.getInsuranceCom())
			&& BussCom.equals(other.getBussCom())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmDesc.equals(other.getConfirmDesc())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(AppSubmitDate).equals(other.getAppSubmitDate())
			&& AppSubmitTime.equals(other.getAppSubmitTime())
			&& GetDealType.equals(other.getGetDealType());
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
		if( strFieldName.equals("TransNo") ) {
			return 0;
		}
		if( strFieldName.equals("ApplyBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("BatState") ) {
			return 2;
		}
		if( strFieldName.equals("PayCode") ) {
			return 3;
		}
		if( strFieldName.equals("MainBussNo") ) {
			return 4;
		}
		if( strFieldName.equals("BussType") ) {
			return 5;
		}
		if( strFieldName.equals("BussNo") ) {
			return 6;
		}
		if( strFieldName.equals("OtherBussNo") ) {
			return 7;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 8;
		}
		if( strFieldName.equals("Money") ) {
			return 9;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return 10;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return 11;
		}
		if( strFieldName.equals("BankCode") ) {
			return 12;
		}
		if( strFieldName.equals("CustBankProvince") ) {
			return 13;
		}
		if( strFieldName.equals("CustBankCity") ) {
			return 14;
		}
		if( strFieldName.equals("CustBankDetail") ) {
			return 15;
		}
		if( strFieldName.equals("CustBankAccNo") ) {
			return 16;
		}
		if( strFieldName.equals("CustAccName") ) {
			return 17;
		}
		if( strFieldName.equals("InsuranceCom") ) {
			return 18;
		}
		if( strFieldName.equals("BussCom") ) {
			return 19;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 20;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 21;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 22;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 23;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 24;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 25;
		}
		if( strFieldName.equals("ComCode") ) {
			return 26;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 27;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 28;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 32;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 33;
		}
		if( strFieldName.equals("AppSubmitDate") ) {
			return 34;
		}
		if( strFieldName.equals("AppSubmitTime") ) {
			return 35;
		}
		if( strFieldName.equals("GetDealType") ) {
			return 36;
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
				strFieldName = "TransNo";
				break;
			case 1:
				strFieldName = "ApplyBatNo";
				break;
			case 2:
				strFieldName = "BatState";
				break;
			case 3:
				strFieldName = "PayCode";
				break;
			case 4:
				strFieldName = "MainBussNo";
				break;
			case 5:
				strFieldName = "BussType";
				break;
			case 6:
				strFieldName = "BussNo";
				break;
			case 7:
				strFieldName = "OtherBussNo";
				break;
			case 8:
				strFieldName = "CustomerNo";
				break;
			case 9:
				strFieldName = "Money";
				break;
			case 10:
				strFieldName = "ShouldDate";
				break;
			case 11:
				strFieldName = "CustBankCode";
				break;
			case 12:
				strFieldName = "BankCode";
				break;
			case 13:
				strFieldName = "CustBankProvince";
				break;
			case 14:
				strFieldName = "CustBankCity";
				break;
			case 15:
				strFieldName = "CustBankDetail";
				break;
			case 16:
				strFieldName = "CustBankAccNo";
				break;
			case 17:
				strFieldName = "CustAccName";
				break;
			case 18:
				strFieldName = "InsuranceCom";
				break;
			case 19:
				strFieldName = "BussCom";
				break;
			case 20:
				strFieldName = "ConfirmOperator";
				break;
			case 21:
				strFieldName = "ConfirmDate";
				break;
			case 22:
				strFieldName = "ConfirmTime";
				break;
			case 23:
				strFieldName = "ConfirmConclusion";
				break;
			case 24:
				strFieldName = "ConfirmDesc";
				break;
			case 25:
				strFieldName = "ManageCom";
				break;
			case 26:
				strFieldName = "ComCode";
				break;
			case 27:
				strFieldName = "MakeOperator";
				break;
			case 28:
				strFieldName = "MakeDate";
				break;
			case 29:
				strFieldName = "MakeTime";
				break;
			case 30:
				strFieldName = "ModifyOperator";
				break;
			case 31:
				strFieldName = "ModifyDate";
				break;
			case 32:
				strFieldName = "ModifyTime";
				break;
			case 33:
				strFieldName = "EnterAccDate";
				break;
			case 34:
				strFieldName = "AppSubmitDate";
				break;
			case 35:
				strFieldName = "AppSubmitTime";
				break;
			case 36:
				strFieldName = "GetDealType";
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
		if( strFieldName.equals("TransNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyBatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Money") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuranceCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppSubmitDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppSubmitTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDealType") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
