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
import com.sinosoft.lis.db.LJModifyBankConfirmDB;

/*
 * <p>ClassName: LJModifyBankConfirmSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJModifyBankConfirmSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJModifyBankConfirmSchema.class);
	// @Field
	/** 申请批次号 */
	private String ApplyBatNo;
	/** 业务发起类型 */
	private String FromBussType;
	/** 业务发起号码 */
	private String FromBussNo;
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
	/** 客户号 */
	private String CustomerNo;
	/** 当前客户开户行 */
	private String CurHeadBankCode;
	/** 当前银行编码 */
	private String CurBankCode;
	/** 当前所在省编码 */
	private String CurBankProvince;
	/** 当前所在市编码 */
	private String CurBankCity;
	/** 当前银行帐号 */
	private String CurBankAccNo;
	/** 当前银行帐户名 */
	private String CurAccName;
	/** 目标客户开户行 */
	private String TarHeadBankCode;
	/** 目标银行编码 */
	private String TarBankCode;
	/** 目标所在省编码 */
	private String TarBankProvince;
	/** 目标所在市编码 */
	private String TarBankCity;
	/** 目标银行帐号 */
	private String TarBankAccNo;
	/** 目标银行帐户名 */
	private String TarAccName;
	/** 金额 */
	private double Money;
	/** 申请人员 */
	private String AppOperator;
	/** 申请日期 */
	private Date AppDate;
	/** 申请时间 */
	private String AppTime;
	/** 申请描述 */
	private String AppDesc;
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
	/** 备注 */
	private String Remark;
	/** 状态 */
	private String State;
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

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJModifyBankConfirmSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ApplyBatNo";

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
		LJModifyBankConfirmSchema cloned = (LJModifyBankConfirmSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getFromBussType()
	{
		return FromBussType;
	}
	public void setFromBussType(String aFromBussType)
	{
		if(aFromBussType!=null && aFromBussType.length()>2)
			throw new IllegalArgumentException("业务发起类型FromBussType值"+aFromBussType+"的长度"+aFromBussType.length()+"大于最大值2");
		FromBussType = aFromBussType;
	}
	public String getFromBussNo()
	{
		return FromBussNo;
	}
	public void setFromBussNo(String aFromBussNo)
	{
		if(aFromBussNo!=null && aFromBussNo.length()>20)
			throw new IllegalArgumentException("业务发起号码FromBussNo值"+aFromBussNo+"的长度"+aFromBussNo.length()+"大于最大值20");
		FromBussNo = aFromBussNo;
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
			throw new IllegalArgumentException("客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCurHeadBankCode()
	{
		return CurHeadBankCode;
	}
	public void setCurHeadBankCode(String aCurHeadBankCode)
	{
		if(aCurHeadBankCode!=null && aCurHeadBankCode.length()>30)
			throw new IllegalArgumentException("当前客户开户行CurHeadBankCode值"+aCurHeadBankCode+"的长度"+aCurHeadBankCode.length()+"大于最大值30");
		CurHeadBankCode = aCurHeadBankCode;
	}
	public String getCurBankCode()
	{
		return CurBankCode;
	}
	public void setCurBankCode(String aCurBankCode)
	{
		if(aCurBankCode!=null && aCurBankCode.length()>30)
			throw new IllegalArgumentException("当前银行编码CurBankCode值"+aCurBankCode+"的长度"+aCurBankCode.length()+"大于最大值30");
		CurBankCode = aCurBankCode;
	}
	public String getCurBankProvince()
	{
		return CurBankProvince;
	}
	public void setCurBankProvince(String aCurBankProvince)
	{
		if(aCurBankProvince!=null && aCurBankProvince.length()>30)
			throw new IllegalArgumentException("当前所在省编码CurBankProvince值"+aCurBankProvince+"的长度"+aCurBankProvince.length()+"大于最大值30");
		CurBankProvince = aCurBankProvince;
	}
	public String getCurBankCity()
	{
		return CurBankCity;
	}
	public void setCurBankCity(String aCurBankCity)
	{
		if(aCurBankCity!=null && aCurBankCity.length()>30)
			throw new IllegalArgumentException("当前所在市编码CurBankCity值"+aCurBankCity+"的长度"+aCurBankCity.length()+"大于最大值30");
		CurBankCity = aCurBankCity;
	}
	public String getCurBankAccNo()
	{
		return CurBankAccNo;
	}
	public void setCurBankAccNo(String aCurBankAccNo)
	{
		if(aCurBankAccNo!=null && aCurBankAccNo.length()>30)
			throw new IllegalArgumentException("当前银行帐号CurBankAccNo值"+aCurBankAccNo+"的长度"+aCurBankAccNo.length()+"大于最大值30");
		CurBankAccNo = aCurBankAccNo;
	}
	public String getCurAccName()
	{
		return CurAccName;
	}
	public void setCurAccName(String aCurAccName)
	{
		if(aCurAccName!=null && aCurAccName.length()>200)
			throw new IllegalArgumentException("当前银行帐户名CurAccName值"+aCurAccName+"的长度"+aCurAccName.length()+"大于最大值200");
		CurAccName = aCurAccName;
	}
	public String getTarHeadBankCode()
	{
		return TarHeadBankCode;
	}
	public void setTarHeadBankCode(String aTarHeadBankCode)
	{
		if(aTarHeadBankCode!=null && aTarHeadBankCode.length()>30)
			throw new IllegalArgumentException("目标客户开户行TarHeadBankCode值"+aTarHeadBankCode+"的长度"+aTarHeadBankCode.length()+"大于最大值30");
		TarHeadBankCode = aTarHeadBankCode;
	}
	public String getTarBankCode()
	{
		return TarBankCode;
	}
	public void setTarBankCode(String aTarBankCode)
	{
		if(aTarBankCode!=null && aTarBankCode.length()>30)
			throw new IllegalArgumentException("目标银行编码TarBankCode值"+aTarBankCode+"的长度"+aTarBankCode.length()+"大于最大值30");
		TarBankCode = aTarBankCode;
	}
	public String getTarBankProvince()
	{
		return TarBankProvince;
	}
	public void setTarBankProvince(String aTarBankProvince)
	{
		if(aTarBankProvince!=null && aTarBankProvince.length()>30)
			throw new IllegalArgumentException("目标所在省编码TarBankProvince值"+aTarBankProvince+"的长度"+aTarBankProvince.length()+"大于最大值30");
		TarBankProvince = aTarBankProvince;
	}
	public String getTarBankCity()
	{
		return TarBankCity;
	}
	public void setTarBankCity(String aTarBankCity)
	{
		if(aTarBankCity!=null && aTarBankCity.length()>30)
			throw new IllegalArgumentException("目标所在市编码TarBankCity值"+aTarBankCity+"的长度"+aTarBankCity.length()+"大于最大值30");
		TarBankCity = aTarBankCity;
	}
	public String getTarBankAccNo()
	{
		return TarBankAccNo;
	}
	public void setTarBankAccNo(String aTarBankAccNo)
	{
		if(aTarBankAccNo!=null && aTarBankAccNo.length()>30)
			throw new IllegalArgumentException("目标银行帐号TarBankAccNo值"+aTarBankAccNo+"的长度"+aTarBankAccNo.length()+"大于最大值30");
		TarBankAccNo = aTarBankAccNo;
	}
	public String getTarAccName()
	{
		return TarAccName;
	}
	public void setTarAccName(String aTarAccName)
	{
		if(aTarAccName!=null && aTarAccName.length()>200)
			throw new IllegalArgumentException("目标银行帐户名TarAccName值"+aTarAccName+"的长度"+aTarAccName.length()+"大于最大值200");
		TarAccName = aTarAccName;
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

	public String getAppOperator()
	{
		return AppOperator;
	}
	public void setAppOperator(String aAppOperator)
	{
		if(aAppOperator!=null && aAppOperator.length()>30)
			throw new IllegalArgumentException("申请人员AppOperator值"+aAppOperator+"的长度"+aAppOperator.length()+"大于最大值30");
		AppOperator = aAppOperator;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		if(aAppTime!=null && aAppTime.length()>8)
			throw new IllegalArgumentException("申请时间AppTime值"+aAppTime+"的长度"+aAppTime.length()+"大于最大值8");
		AppTime = aAppTime;
	}
	public String getAppDesc()
	{
		return AppDesc;
	}
	public void setAppDesc(String aAppDesc)
	{
		if(aAppDesc!=null && aAppDesc.length()>3000)
			throw new IllegalArgumentException("申请描述AppDesc值"+aAppDesc+"的长度"+aAppDesc.length()+"大于最大值3000");
		AppDesc = aAppDesc;
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
	* 0-已申请待审核，1-待审核，2-审核通过，3-撤销
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>1)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值1");
		State = aState;
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

	/**
	* 使用另外一个 LJModifyBankConfirmSchema 对象给 Schema 赋值
	* @param: aLJModifyBankConfirmSchema LJModifyBankConfirmSchema
	**/
	public void setSchema(LJModifyBankConfirmSchema aLJModifyBankConfirmSchema)
	{
		this.ApplyBatNo = aLJModifyBankConfirmSchema.getApplyBatNo();
		this.FromBussType = aLJModifyBankConfirmSchema.getFromBussType();
		this.FromBussNo = aLJModifyBankConfirmSchema.getFromBussNo();
		this.PayCode = aLJModifyBankConfirmSchema.getPayCode();
		this.MainBussNo = aLJModifyBankConfirmSchema.getMainBussNo();
		this.BussType = aLJModifyBankConfirmSchema.getBussType();
		this.BussNo = aLJModifyBankConfirmSchema.getBussNo();
		this.OtherBussNo = aLJModifyBankConfirmSchema.getOtherBussNo();
		this.CustomerNo = aLJModifyBankConfirmSchema.getCustomerNo();
		this.CurHeadBankCode = aLJModifyBankConfirmSchema.getCurHeadBankCode();
		this.CurBankCode = aLJModifyBankConfirmSchema.getCurBankCode();
		this.CurBankProvince = aLJModifyBankConfirmSchema.getCurBankProvince();
		this.CurBankCity = aLJModifyBankConfirmSchema.getCurBankCity();
		this.CurBankAccNo = aLJModifyBankConfirmSchema.getCurBankAccNo();
		this.CurAccName = aLJModifyBankConfirmSchema.getCurAccName();
		this.TarHeadBankCode = aLJModifyBankConfirmSchema.getTarHeadBankCode();
		this.TarBankCode = aLJModifyBankConfirmSchema.getTarBankCode();
		this.TarBankProvince = aLJModifyBankConfirmSchema.getTarBankProvince();
		this.TarBankCity = aLJModifyBankConfirmSchema.getTarBankCity();
		this.TarBankAccNo = aLJModifyBankConfirmSchema.getTarBankAccNo();
		this.TarAccName = aLJModifyBankConfirmSchema.getTarAccName();
		this.Money = aLJModifyBankConfirmSchema.getMoney();
		this.AppOperator = aLJModifyBankConfirmSchema.getAppOperator();
		this.AppDate = fDate.getDate( aLJModifyBankConfirmSchema.getAppDate());
		this.AppTime = aLJModifyBankConfirmSchema.getAppTime();
		this.AppDesc = aLJModifyBankConfirmSchema.getAppDesc();
		this.ConfirmOperator = aLJModifyBankConfirmSchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJModifyBankConfirmSchema.getConfirmDate());
		this.ConfirmTime = aLJModifyBankConfirmSchema.getConfirmTime();
		this.ConfirmConclusion = aLJModifyBankConfirmSchema.getConfirmConclusion();
		this.ConfirmDesc = aLJModifyBankConfirmSchema.getConfirmDesc();
		this.Remark = aLJModifyBankConfirmSchema.getRemark();
		this.State = aLJModifyBankConfirmSchema.getState();
		this.ManageCom = aLJModifyBankConfirmSchema.getManageCom();
		this.ComCode = aLJModifyBankConfirmSchema.getComCode();
		this.MakeOperator = aLJModifyBankConfirmSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJModifyBankConfirmSchema.getMakeDate());
		this.MakeTime = aLJModifyBankConfirmSchema.getMakeTime();
		this.ModifyOperator = aLJModifyBankConfirmSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJModifyBankConfirmSchema.getModifyDate());
		this.ModifyTime = aLJModifyBankConfirmSchema.getModifyTime();
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
			if( rs.getString("ApplyBatNo") == null )
				this.ApplyBatNo = null;
			else
				this.ApplyBatNo = rs.getString("ApplyBatNo").trim();

			if( rs.getString("FromBussType") == null )
				this.FromBussType = null;
			else
				this.FromBussType = rs.getString("FromBussType").trim();

			if( rs.getString("FromBussNo") == null )
				this.FromBussNo = null;
			else
				this.FromBussNo = rs.getString("FromBussNo").trim();

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

			if( rs.getString("CurHeadBankCode") == null )
				this.CurHeadBankCode = null;
			else
				this.CurHeadBankCode = rs.getString("CurHeadBankCode").trim();

			if( rs.getString("CurBankCode") == null )
				this.CurBankCode = null;
			else
				this.CurBankCode = rs.getString("CurBankCode").trim();

			if( rs.getString("CurBankProvince") == null )
				this.CurBankProvince = null;
			else
				this.CurBankProvince = rs.getString("CurBankProvince").trim();

			if( rs.getString("CurBankCity") == null )
				this.CurBankCity = null;
			else
				this.CurBankCity = rs.getString("CurBankCity").trim();

			if( rs.getString("CurBankAccNo") == null )
				this.CurBankAccNo = null;
			else
				this.CurBankAccNo = rs.getString("CurBankAccNo").trim();

			if( rs.getString("CurAccName") == null )
				this.CurAccName = null;
			else
				this.CurAccName = rs.getString("CurAccName").trim();

			if( rs.getString("TarHeadBankCode") == null )
				this.TarHeadBankCode = null;
			else
				this.TarHeadBankCode = rs.getString("TarHeadBankCode").trim();

			if( rs.getString("TarBankCode") == null )
				this.TarBankCode = null;
			else
				this.TarBankCode = rs.getString("TarBankCode").trim();

			if( rs.getString("TarBankProvince") == null )
				this.TarBankProvince = null;
			else
				this.TarBankProvince = rs.getString("TarBankProvince").trim();

			if( rs.getString("TarBankCity") == null )
				this.TarBankCity = null;
			else
				this.TarBankCity = rs.getString("TarBankCity").trim();

			if( rs.getString("TarBankAccNo") == null )
				this.TarBankAccNo = null;
			else
				this.TarBankAccNo = rs.getString("TarBankAccNo").trim();

			if( rs.getString("TarAccName") == null )
				this.TarAccName = null;
			else
				this.TarAccName = rs.getString("TarAccName").trim();

			this.Money = rs.getDouble("Money");
			if( rs.getString("AppOperator") == null )
				this.AppOperator = null;
			else
				this.AppOperator = rs.getString("AppOperator").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("AppDesc") == null )
				this.AppDesc = null;
			else
				this.AppDesc = rs.getString("AppDesc").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJModifyBankConfirm表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJModifyBankConfirmSchema getSchema()
	{
		LJModifyBankConfirmSchema aLJModifyBankConfirmSchema = new LJModifyBankConfirmSchema();
		aLJModifyBankConfirmSchema.setSchema(this);
		return aLJModifyBankConfirmSchema;
	}

	public LJModifyBankConfirmDB getDB()
	{
		LJModifyBankConfirmDB aDBOper = new LJModifyBankConfirmDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJModifyBankConfirm描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ApplyBatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FromBussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FromBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurHeadBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurBankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurBankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarHeadBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Money));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJModifyBankConfirm>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ApplyBatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FromBussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FromBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MainBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OtherBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CurHeadBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CurBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CurBankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CurBankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CurBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CurAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			TarHeadBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			TarBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			TarBankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			TarBankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			TarBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			TarAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			AppOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AppDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmSchema";
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
		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyBatNo));
		}
		if (FCode.equalsIgnoreCase("FromBussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FromBussType));
		}
		if (FCode.equalsIgnoreCase("FromBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FromBussNo));
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
		if (FCode.equalsIgnoreCase("CurHeadBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurHeadBankCode));
		}
		if (FCode.equalsIgnoreCase("CurBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurBankCode));
		}
		if (FCode.equalsIgnoreCase("CurBankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurBankProvince));
		}
		if (FCode.equalsIgnoreCase("CurBankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurBankCity));
		}
		if (FCode.equalsIgnoreCase("CurBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurBankAccNo));
		}
		if (FCode.equalsIgnoreCase("CurAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurAccName));
		}
		if (FCode.equalsIgnoreCase("TarHeadBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarHeadBankCode));
		}
		if (FCode.equalsIgnoreCase("TarBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankCode));
		}
		if (FCode.equalsIgnoreCase("TarBankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankProvince));
		}
		if (FCode.equalsIgnoreCase("TarBankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankCity));
		}
		if (FCode.equalsIgnoreCase("TarBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankAccNo));
		}
		if (FCode.equalsIgnoreCase("TarAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarAccName));
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Money));
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOperator));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("AppDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppDesc));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(ApplyBatNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FromBussType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FromBussNo);
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
				strFieldValue = StrTool.GBKToUnicode(CurHeadBankCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CurBankCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CurBankProvince);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CurBankCity);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CurBankAccNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CurAccName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(TarHeadBankCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(TarBankCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(TarBankProvince);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(TarBankCity);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(TarBankAccNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(TarAccName);
				break;
			case 21:
				strFieldValue = String.valueOf(Money);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AppOperator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AppDesc);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 40:
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

		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyBatNo = FValue.trim();
			}
			else
				ApplyBatNo = null;
		}
		if (FCode.equalsIgnoreCase("FromBussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FromBussType = FValue.trim();
			}
			else
				FromBussType = null;
		}
		if (FCode.equalsIgnoreCase("FromBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FromBussNo = FValue.trim();
			}
			else
				FromBussNo = null;
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
		if (FCode.equalsIgnoreCase("CurHeadBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurHeadBankCode = FValue.trim();
			}
			else
				CurHeadBankCode = null;
		}
		if (FCode.equalsIgnoreCase("CurBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurBankCode = FValue.trim();
			}
			else
				CurBankCode = null;
		}
		if (FCode.equalsIgnoreCase("CurBankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurBankProvince = FValue.trim();
			}
			else
				CurBankProvince = null;
		}
		if (FCode.equalsIgnoreCase("CurBankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurBankCity = FValue.trim();
			}
			else
				CurBankCity = null;
		}
		if (FCode.equalsIgnoreCase("CurBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurBankAccNo = FValue.trim();
			}
			else
				CurBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("CurAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurAccName = FValue.trim();
			}
			else
				CurAccName = null;
		}
		if (FCode.equalsIgnoreCase("TarHeadBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarHeadBankCode = FValue.trim();
			}
			else
				TarHeadBankCode = null;
		}
		if (FCode.equalsIgnoreCase("TarBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankCode = FValue.trim();
			}
			else
				TarBankCode = null;
		}
		if (FCode.equalsIgnoreCase("TarBankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankProvince = FValue.trim();
			}
			else
				TarBankProvince = null;
		}
		if (FCode.equalsIgnoreCase("TarBankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankCity = FValue.trim();
			}
			else
				TarBankCity = null;
		}
		if (FCode.equalsIgnoreCase("TarBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankAccNo = FValue.trim();
			}
			else
				TarBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("TarAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarAccName = FValue.trim();
			}
			else
				TarAccName = null;
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
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppOperator = FValue.trim();
			}
			else
				AppOperator = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("AppDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppDesc = FValue.trim();
			}
			else
				AppDesc = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJModifyBankConfirmSchema other = (LJModifyBankConfirmSchema)otherObject;
		return
			ApplyBatNo.equals(other.getApplyBatNo())
			&& FromBussType.equals(other.getFromBussType())
			&& FromBussNo.equals(other.getFromBussNo())
			&& PayCode.equals(other.getPayCode())
			&& MainBussNo.equals(other.getMainBussNo())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& OtherBussNo.equals(other.getOtherBussNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CurHeadBankCode.equals(other.getCurHeadBankCode())
			&& CurBankCode.equals(other.getCurBankCode())
			&& CurBankProvince.equals(other.getCurBankProvince())
			&& CurBankCity.equals(other.getCurBankCity())
			&& CurBankAccNo.equals(other.getCurBankAccNo())
			&& CurAccName.equals(other.getCurAccName())
			&& TarHeadBankCode.equals(other.getTarHeadBankCode())
			&& TarBankCode.equals(other.getTarBankCode())
			&& TarBankProvince.equals(other.getTarBankProvince())
			&& TarBankCity.equals(other.getTarBankCity())
			&& TarBankAccNo.equals(other.getTarBankAccNo())
			&& TarAccName.equals(other.getTarAccName())
			&& Money == other.getMoney()
			&& AppOperator.equals(other.getAppOperator())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& AppDesc.equals(other.getAppDesc())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmDesc.equals(other.getConfirmDesc())
			&& Remark.equals(other.getRemark())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("ApplyBatNo") ) {
			return 0;
		}
		if( strFieldName.equals("FromBussType") ) {
			return 1;
		}
		if( strFieldName.equals("FromBussNo") ) {
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
		if( strFieldName.equals("CurHeadBankCode") ) {
			return 9;
		}
		if( strFieldName.equals("CurBankCode") ) {
			return 10;
		}
		if( strFieldName.equals("CurBankProvince") ) {
			return 11;
		}
		if( strFieldName.equals("CurBankCity") ) {
			return 12;
		}
		if( strFieldName.equals("CurBankAccNo") ) {
			return 13;
		}
		if( strFieldName.equals("CurAccName") ) {
			return 14;
		}
		if( strFieldName.equals("TarHeadBankCode") ) {
			return 15;
		}
		if( strFieldName.equals("TarBankCode") ) {
			return 16;
		}
		if( strFieldName.equals("TarBankProvince") ) {
			return 17;
		}
		if( strFieldName.equals("TarBankCity") ) {
			return 18;
		}
		if( strFieldName.equals("TarBankAccNo") ) {
			return 19;
		}
		if( strFieldName.equals("TarAccName") ) {
			return 20;
		}
		if( strFieldName.equals("Money") ) {
			return 21;
		}
		if( strFieldName.equals("AppOperator") ) {
			return 22;
		}
		if( strFieldName.equals("AppDate") ) {
			return 23;
		}
		if( strFieldName.equals("AppTime") ) {
			return 24;
		}
		if( strFieldName.equals("AppDesc") ) {
			return 25;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 26;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 27;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 28;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 29;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 30;
		}
		if( strFieldName.equals("Remark") ) {
			return 31;
		}
		if( strFieldName.equals("State") ) {
			return 32;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 33;
		}
		if( strFieldName.equals("ComCode") ) {
			return 34;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 39;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "ApplyBatNo";
				break;
			case 1:
				strFieldName = "FromBussType";
				break;
			case 2:
				strFieldName = "FromBussNo";
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
				strFieldName = "CurHeadBankCode";
				break;
			case 10:
				strFieldName = "CurBankCode";
				break;
			case 11:
				strFieldName = "CurBankProvince";
				break;
			case 12:
				strFieldName = "CurBankCity";
				break;
			case 13:
				strFieldName = "CurBankAccNo";
				break;
			case 14:
				strFieldName = "CurAccName";
				break;
			case 15:
				strFieldName = "TarHeadBankCode";
				break;
			case 16:
				strFieldName = "TarBankCode";
				break;
			case 17:
				strFieldName = "TarBankProvince";
				break;
			case 18:
				strFieldName = "TarBankCity";
				break;
			case 19:
				strFieldName = "TarBankAccNo";
				break;
			case 20:
				strFieldName = "TarAccName";
				break;
			case 21:
				strFieldName = "Money";
				break;
			case 22:
				strFieldName = "AppOperator";
				break;
			case 23:
				strFieldName = "AppDate";
				break;
			case 24:
				strFieldName = "AppTime";
				break;
			case 25:
				strFieldName = "AppDesc";
				break;
			case 26:
				strFieldName = "ConfirmOperator";
				break;
			case 27:
				strFieldName = "ConfirmDate";
				break;
			case 28:
				strFieldName = "ConfirmTime";
				break;
			case 29:
				strFieldName = "ConfirmConclusion";
				break;
			case 30:
				strFieldName = "ConfirmDesc";
				break;
			case 31:
				strFieldName = "Remark";
				break;
			case 32:
				strFieldName = "State";
				break;
			case 33:
				strFieldName = "ManageCom";
				break;
			case 34:
				strFieldName = "ComCode";
				break;
			case 35:
				strFieldName = "MakeOperator";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyOperator";
				break;
			case 39:
				strFieldName = "ModifyDate";
				break;
			case 40:
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
		if( strFieldName.equals("ApplyBatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FromBussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FromBussNo") ) {
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
		if( strFieldName.equals("CurHeadBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurBankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurBankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarHeadBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Money") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDesc") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
