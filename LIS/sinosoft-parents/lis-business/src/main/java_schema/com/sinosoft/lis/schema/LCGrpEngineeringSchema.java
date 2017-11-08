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
import com.sinosoft.lis.db.LCGrpEngineeringDB;

/*
 * <p>ClassName: LCGrpEngineeringSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCGrpEngineeringSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpEngineeringSchema.class);
	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 工程名称 */
	private String EnginName;
	/** 工程类型 */
	private String EnginType;
	/** 工程面积 */
	private double EnginArea;
	/** 工程造价 */
	private double EnginCost;
	/** 工程地点 */
	private String EnginPlace;
	/** 工程起期 */
	private Date EnginStartDate;
	/** 工程止期 */
	private Date EnginEndDate;
	/** 承包方名称 */
	private String InsurerName;
	/** 承包方资质 */
	private String InsurerType;
	/** 施工方名称 */
	private String ContractorName;
	/** 施工方资质 */
	private String ContractorType;
	/** 施工类型 */
	private String ContractorType2;
	/** 详细描述 */
	private String DetailDes;
	/** 工程概述 */
	private String EnginDesc;
	/** 工程状况说明 */
	private String EnginCondition;
	/** 特殊标记 */
	private String SpecFlag;
	/** 保费计算方式 */
	private String PremCalMode;
	/** 保险费率 */
	private double PremFeeRate;
	/** 趸交保费 */
	private double FirstPrem;
	/** 施工人数 */
	private int ContractorPeoples;
	/** 安监证明 */
	private String SafeProve;
	/** 备注 */
	private String Remark;
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
	/** 施工天数 */
	private String EnginDays;

	public static final int FIELDNUM = 33;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpEngineeringSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpContNo";

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
		LCGrpEngineeringSchema cloned = (LCGrpEngineeringSchema)super.clone();
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
	* SGP+两位年份+4位省市行政代码(保监）+9位流水
	*/
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("团体投保单号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
	}
	public String getEnginName()
	{
		return EnginName;
	}
	public void setEnginName(String aEnginName)
	{
		if(aEnginName!=null && aEnginName.length()>300)
			throw new IllegalArgumentException("工程名称EnginName值"+aEnginName+"的长度"+aEnginName.length()+"大于最大值300");
		EnginName = aEnginName;
	}
	/**
	* 00-建筑工程，01-安装工程
	*/
	public String getEnginType()
	{
		return EnginType;
	}
	public void setEnginType(String aEnginType)
	{
		if(aEnginType!=null && aEnginType.length()>2)
			throw new IllegalArgumentException("工程类型EnginType值"+aEnginType+"的长度"+aEnginType.length()+"大于最大值2");
		EnginType = aEnginType;
	}
	public double getEnginArea()
	{
		return EnginArea;
	}
	public void setEnginArea(double aEnginArea)
	{
		EnginArea = aEnginArea;
	}
	public void setEnginArea(String aEnginArea)
	{
		if (aEnginArea != null && !aEnginArea.equals(""))
		{
			Double tDouble = new Double(aEnginArea);
			double d = tDouble.doubleValue();
			EnginArea = d;
		}
	}

	public double getEnginCost()
	{
		return EnginCost;
	}
	public void setEnginCost(double aEnginCost)
	{
		EnginCost = aEnginCost;
	}
	public void setEnginCost(String aEnginCost)
	{
		if (aEnginCost != null && !aEnginCost.equals(""))
		{
			Double tDouble = new Double(aEnginCost);
			double d = tDouble.doubleValue();
			EnginCost = d;
		}
	}

	public String getEnginPlace()
	{
		return EnginPlace;
	}
	public void setEnginPlace(String aEnginPlace)
	{
		if(aEnginPlace!=null && aEnginPlace.length()>300)
			throw new IllegalArgumentException("工程地点EnginPlace值"+aEnginPlace+"的长度"+aEnginPlace.length()+"大于最大值300");
		EnginPlace = aEnginPlace;
	}
	public String getEnginStartDate()
	{
		if( EnginStartDate != null )
			return fDate.getString(EnginStartDate);
		else
			return null;
	}
	public void setEnginStartDate(Date aEnginStartDate)
	{
		EnginStartDate = aEnginStartDate;
	}
	public void setEnginStartDate(String aEnginStartDate)
	{
		if (aEnginStartDate != null && !aEnginStartDate.equals("") )
		{
			EnginStartDate = fDate.getDate( aEnginStartDate );
		}
		else
			EnginStartDate = null;
	}

	public String getEnginEndDate()
	{
		if( EnginEndDate != null )
			return fDate.getString(EnginEndDate);
		else
			return null;
	}
	public void setEnginEndDate(Date aEnginEndDate)
	{
		EnginEndDate = aEnginEndDate;
	}
	public void setEnginEndDate(String aEnginEndDate)
	{
		if (aEnginEndDate != null && !aEnginEndDate.equals("") )
		{
			EnginEndDate = fDate.getDate( aEnginEndDate );
		}
		else
			EnginEndDate = null;
	}

	public String getInsurerName()
	{
		return InsurerName;
	}
	public void setInsurerName(String aInsurerName)
	{
		if(aInsurerName!=null && aInsurerName.length()>300)
			throw new IllegalArgumentException("承包方名称InsurerName值"+aInsurerName+"的长度"+aInsurerName.length()+"大于最大值300");
		InsurerName = aInsurerName;
	}
	/**
	* 00-施工总承包资质，01-专业承包资质，02-劳务分包资质
	*/
	public String getInsurerType()
	{
		return InsurerType;
	}
	public void setInsurerType(String aInsurerType)
	{
		if(aInsurerType!=null && aInsurerType.length()>2)
			throw new IllegalArgumentException("承包方资质InsurerType值"+aInsurerType+"的长度"+aInsurerType.length()+"大于最大值2");
		InsurerType = aInsurerType;
	}
	public String getContractorName()
	{
		return ContractorName;
	}
	public void setContractorName(String aContractorName)
	{
		if(aContractorName!=null && aContractorName.length()>300)
			throw new IllegalArgumentException("施工方名称ContractorName值"+aContractorName+"的长度"+aContractorName.length()+"大于最大值300");
		ContractorName = aContractorName;
	}
	/**
	* 00-特级资质标准，01-一级资质标准，02-二级资质标准，03-三级资质标准，04-其他
	*/
	public String getContractorType()
	{
		return ContractorType;
	}
	public void setContractorType(String aContractorType)
	{
		if(aContractorType!=null && aContractorType.length()>2)
			throw new IllegalArgumentException("施工方资质ContractorType值"+aContractorType+"的长度"+aContractorType.length()+"大于最大值2");
		ContractorType = aContractorType;
	}
	public String getContractorType2()
	{
		return ContractorType2;
	}
	public void setContractorType2(String aContractorType2)
	{
		if(aContractorType2!=null && aContractorType2.length()>2)
			throw new IllegalArgumentException("施工类型ContractorType2值"+aContractorType2+"的长度"+aContractorType2.length()+"大于最大值2");
		ContractorType2 = aContractorType2;
	}
	public String getDetailDes()
	{
		return DetailDes;
	}
	public void setDetailDes(String aDetailDes)
	{
		if(aDetailDes!=null && aDetailDes.length()>3000)
			throw new IllegalArgumentException("详细描述DetailDes值"+aDetailDes+"的长度"+aDetailDes.length()+"大于最大值3000");
		DetailDes = aDetailDes;
	}
	public String getEnginDesc()
	{
		return EnginDesc;
	}
	public void setEnginDesc(String aEnginDesc)
	{
		if(aEnginDesc!=null && aEnginDesc.length()>3000)
			throw new IllegalArgumentException("工程概述EnginDesc值"+aEnginDesc+"的长度"+aEnginDesc.length()+"大于最大值3000");
		EnginDesc = aEnginDesc;
	}
	public String getEnginCondition()
	{
		return EnginCondition;
	}
	public void setEnginCondition(String aEnginCondition)
	{
		if(aEnginCondition!=null && aEnginCondition.length()>3000)
			throw new IllegalArgumentException("工程状况说明EnginCondition值"+aEnginCondition+"的长度"+aEnginCondition.length()+"大于最大值3000");
		EnginCondition = aEnginCondition;
	}
	/**
	* 涉及海上、水下、高空、爆破及井下作业
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		if(aSpecFlag!=null && aSpecFlag.length()>10)
			throw new IllegalArgumentException("特殊标记SpecFlag值"+aSpecFlag+"的长度"+aSpecFlag.length()+"大于最大值10");
		SpecFlag = aSpecFlag;
	}
	public String getPremCalMode()
	{
		return PremCalMode;
	}
	public void setPremCalMode(String aPremCalMode)
	{
		if(aPremCalMode!=null && aPremCalMode.length()>2)
			throw new IllegalArgumentException("保费计算方式PremCalMode值"+aPremCalMode+"的长度"+aPremCalMode.length()+"大于最大值2");
		PremCalMode = aPremCalMode;
	}
	public double getPremFeeRate()
	{
		return PremFeeRate;
	}
	public void setPremFeeRate(double aPremFeeRate)
	{
		PremFeeRate = aPremFeeRate;
	}
	public void setPremFeeRate(String aPremFeeRate)
	{
		if (aPremFeeRate != null && !aPremFeeRate.equals(""))
		{
			Double tDouble = new Double(aPremFeeRate);
			double d = tDouble.doubleValue();
			PremFeeRate = d;
		}
	}

	public double getFirstPrem()
	{
		return FirstPrem;
	}
	public void setFirstPrem(double aFirstPrem)
	{
		FirstPrem = aFirstPrem;
	}
	public void setFirstPrem(String aFirstPrem)
	{
		if (aFirstPrem != null && !aFirstPrem.equals(""))
		{
			Double tDouble = new Double(aFirstPrem);
			double d = tDouble.doubleValue();
			FirstPrem = d;
		}
	}

	public int getContractorPeoples()
	{
		return ContractorPeoples;
	}
	public void setContractorPeoples(int aContractorPeoples)
	{
		ContractorPeoples = aContractorPeoples;
	}
	public void setContractorPeoples(String aContractorPeoples)
	{
		if (aContractorPeoples != null && !aContractorPeoples.equals(""))
		{
			Integer tInteger = new Integer(aContractorPeoples);
			int i = tInteger.intValue();
			ContractorPeoples = i;
		}
	}

	public String getSafeProve()
	{
		return SafeProve;
	}
	public void setSafeProve(String aSafeProve)
	{
		if(aSafeProve!=null && aSafeProve.length()>2)
			throw new IllegalArgumentException("安监证明SafeProve值"+aSafeProve+"的长度"+aSafeProve.length()+"大于最大值2");
		SafeProve = aSafeProve;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>3000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值3000");
		Remark = aRemark;
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
	public String getEnginDays()
	{
		return EnginDays;
	}
	public void setEnginDays(String aEnginDays)
	{
		if(aEnginDays!=null && aEnginDays.length()>20)
			throw new IllegalArgumentException("施工天数EnginDays值"+aEnginDays+"的长度"+aEnginDays.length()+"大于最大值20");
		EnginDays = aEnginDays;
	}

	/**
	* 使用另外一个 LCGrpEngineeringSchema 对象给 Schema 赋值
	* @param: aLCGrpEngineeringSchema LCGrpEngineeringSchema
	**/
	public void setSchema(LCGrpEngineeringSchema aLCGrpEngineeringSchema)
	{
		this.GrpContNo = aLCGrpEngineeringSchema.getGrpContNo();
		this.GrpPropNo = aLCGrpEngineeringSchema.getGrpPropNo();
		this.EnginName = aLCGrpEngineeringSchema.getEnginName();
		this.EnginType = aLCGrpEngineeringSchema.getEnginType();
		this.EnginArea = aLCGrpEngineeringSchema.getEnginArea();
		this.EnginCost = aLCGrpEngineeringSchema.getEnginCost();
		this.EnginPlace = aLCGrpEngineeringSchema.getEnginPlace();
		this.EnginStartDate = fDate.getDate( aLCGrpEngineeringSchema.getEnginStartDate());
		this.EnginEndDate = fDate.getDate( aLCGrpEngineeringSchema.getEnginEndDate());
		this.InsurerName = aLCGrpEngineeringSchema.getInsurerName();
		this.InsurerType = aLCGrpEngineeringSchema.getInsurerType();
		this.ContractorName = aLCGrpEngineeringSchema.getContractorName();
		this.ContractorType = aLCGrpEngineeringSchema.getContractorType();
		this.ContractorType2 = aLCGrpEngineeringSchema.getContractorType2();
		this.DetailDes = aLCGrpEngineeringSchema.getDetailDes();
		this.EnginDesc = aLCGrpEngineeringSchema.getEnginDesc();
		this.EnginCondition = aLCGrpEngineeringSchema.getEnginCondition();
		this.SpecFlag = aLCGrpEngineeringSchema.getSpecFlag();
		this.PremCalMode = aLCGrpEngineeringSchema.getPremCalMode();
		this.PremFeeRate = aLCGrpEngineeringSchema.getPremFeeRate();
		this.FirstPrem = aLCGrpEngineeringSchema.getFirstPrem();
		this.ContractorPeoples = aLCGrpEngineeringSchema.getContractorPeoples();
		this.SafeProve = aLCGrpEngineeringSchema.getSafeProve();
		this.Remark = aLCGrpEngineeringSchema.getRemark();
		this.ManageCom = aLCGrpEngineeringSchema.getManageCom();
		this.ComCode = aLCGrpEngineeringSchema.getComCode();
		this.MakeOperator = aLCGrpEngineeringSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLCGrpEngineeringSchema.getMakeDate());
		this.MakeTime = aLCGrpEngineeringSchema.getMakeTime();
		this.ModifyOperator = aLCGrpEngineeringSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLCGrpEngineeringSchema.getModifyDate());
		this.ModifyTime = aLCGrpEngineeringSchema.getModifyTime();
		this.EnginDays = aLCGrpEngineeringSchema.getEnginDays();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("EnginName") == null )
				this.EnginName = null;
			else
				this.EnginName = rs.getString("EnginName").trim();

			if( rs.getString("EnginType") == null )
				this.EnginType = null;
			else
				this.EnginType = rs.getString("EnginType").trim();

			this.EnginArea = rs.getDouble("EnginArea");
			this.EnginCost = rs.getDouble("EnginCost");
			if( rs.getString("EnginPlace") == null )
				this.EnginPlace = null;
			else
				this.EnginPlace = rs.getString("EnginPlace").trim();

			this.EnginStartDate = rs.getDate("EnginStartDate");
			this.EnginEndDate = rs.getDate("EnginEndDate");
			if( rs.getString("InsurerName") == null )
				this.InsurerName = null;
			else
				this.InsurerName = rs.getString("InsurerName").trim();

			if( rs.getString("InsurerType") == null )
				this.InsurerType = null;
			else
				this.InsurerType = rs.getString("InsurerType").trim();

			if( rs.getString("ContractorName") == null )
				this.ContractorName = null;
			else
				this.ContractorName = rs.getString("ContractorName").trim();

			if( rs.getString("ContractorType") == null )
				this.ContractorType = null;
			else
				this.ContractorType = rs.getString("ContractorType").trim();

			if( rs.getString("ContractorType2") == null )
				this.ContractorType2 = null;
			else
				this.ContractorType2 = rs.getString("ContractorType2").trim();

			if( rs.getString("DetailDes") == null )
				this.DetailDes = null;
			else
				this.DetailDes = rs.getString("DetailDes").trim();

			if( rs.getString("EnginDesc") == null )
				this.EnginDesc = null;
			else
				this.EnginDesc = rs.getString("EnginDesc").trim();

			if( rs.getString("EnginCondition") == null )
				this.EnginCondition = null;
			else
				this.EnginCondition = rs.getString("EnginCondition").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("PremCalMode") == null )
				this.PremCalMode = null;
			else
				this.PremCalMode = rs.getString("PremCalMode").trim();

			this.PremFeeRate = rs.getDouble("PremFeeRate");
			this.FirstPrem = rs.getDouble("FirstPrem");
			this.ContractorPeoples = rs.getInt("ContractorPeoples");
			if( rs.getString("SafeProve") == null )
				this.SafeProve = null;
			else
				this.SafeProve = rs.getString("SafeProve").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("EnginDays") == null )
				this.EnginDays = null;
			else
				this.EnginDays = rs.getString("EnginDays").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpEngineering表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpEngineeringSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpEngineeringSchema getSchema()
	{
		LCGrpEngineeringSchema aLCGrpEngineeringSchema = new LCGrpEngineeringSchema();
		aLCGrpEngineeringSchema.setSchema(this);
		return aLCGrpEngineeringSchema;
	}

	public LCGrpEngineeringDB getDB()
	{
		LCGrpEngineeringDB aDBOper = new LCGrpEngineeringDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpEngineering描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnginArea));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnginCost));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginPlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnginStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnginEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsurerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsurerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContractorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContractorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContractorType2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DetailDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginCondition)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FirstPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ContractorPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SafeProve)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginDays));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpEngineering>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EnginName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EnginType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EnginArea = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			EnginCost = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			EnginPlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EnginStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EnginEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			InsurerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InsurerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ContractorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ContractorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ContractorType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DetailDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			EnginDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			EnginCondition = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PremCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PremFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			FirstPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			ContractorPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			SafeProve = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			EnginDays = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpEngineeringSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("EnginName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginName));
		}
		if (FCode.equalsIgnoreCase("EnginType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginType));
		}
		if (FCode.equalsIgnoreCase("EnginArea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginArea));
		}
		if (FCode.equalsIgnoreCase("EnginCost"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginCost));
		}
		if (FCode.equalsIgnoreCase("EnginPlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginPlace));
		}
		if (FCode.equalsIgnoreCase("EnginStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnginStartDate()));
		}
		if (FCode.equalsIgnoreCase("EnginEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnginEndDate()));
		}
		if (FCode.equalsIgnoreCase("InsurerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsurerName));
		}
		if (FCode.equalsIgnoreCase("InsurerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsurerType));
		}
		if (FCode.equalsIgnoreCase("ContractorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContractorName));
		}
		if (FCode.equalsIgnoreCase("ContractorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContractorType));
		}
		if (FCode.equalsIgnoreCase("ContractorType2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContractorType2));
		}
		if (FCode.equalsIgnoreCase("DetailDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailDes));
		}
		if (FCode.equalsIgnoreCase("EnginDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginDesc));
		}
		if (FCode.equalsIgnoreCase("EnginCondition"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginCondition));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("PremCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremCalMode));
		}
		if (FCode.equalsIgnoreCase("PremFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremFeeRate));
		}
		if (FCode.equalsIgnoreCase("FirstPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstPrem));
		}
		if (FCode.equalsIgnoreCase("ContractorPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContractorPeoples));
		}
		if (FCode.equalsIgnoreCase("SafeProve"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SafeProve));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("EnginDays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginDays));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EnginName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EnginType);
				break;
			case 4:
				strFieldValue = String.valueOf(EnginArea);
				break;
			case 5:
				strFieldValue = String.valueOf(EnginCost);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EnginPlace);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnginStartDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnginEndDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InsurerName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InsurerType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ContractorName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ContractorType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ContractorType2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DetailDes);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EnginDesc);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(EnginCondition);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PremCalMode);
				break;
			case 19:
				strFieldValue = String.valueOf(PremFeeRate);
				break;
			case 20:
				strFieldValue = String.valueOf(FirstPrem);
				break;
			case 21:
				strFieldValue = String.valueOf(ContractorPeoples);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(SafeProve);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(EnginDays);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("EnginName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginName = FValue.trim();
			}
			else
				EnginName = null;
		}
		if (FCode.equalsIgnoreCase("EnginType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginType = FValue.trim();
			}
			else
				EnginType = null;
		}
		if (FCode.equalsIgnoreCase("EnginArea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnginArea = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnginCost"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnginCost = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnginPlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginPlace = FValue.trim();
			}
			else
				EnginPlace = null;
		}
		if (FCode.equalsIgnoreCase("EnginStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnginStartDate = fDate.getDate( FValue );
			}
			else
				EnginStartDate = null;
		}
		if (FCode.equalsIgnoreCase("EnginEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnginEndDate = fDate.getDate( FValue );
			}
			else
				EnginEndDate = null;
		}
		if (FCode.equalsIgnoreCase("InsurerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsurerName = FValue.trim();
			}
			else
				InsurerName = null;
		}
		if (FCode.equalsIgnoreCase("InsurerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsurerType = FValue.trim();
			}
			else
				InsurerType = null;
		}
		if (FCode.equalsIgnoreCase("ContractorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContractorName = FValue.trim();
			}
			else
				ContractorName = null;
		}
		if (FCode.equalsIgnoreCase("ContractorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContractorType = FValue.trim();
			}
			else
				ContractorType = null;
		}
		if (FCode.equalsIgnoreCase("ContractorType2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContractorType2 = FValue.trim();
			}
			else
				ContractorType2 = null;
		}
		if (FCode.equalsIgnoreCase("DetailDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailDes = FValue.trim();
			}
			else
				DetailDes = null;
		}
		if (FCode.equalsIgnoreCase("EnginDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginDesc = FValue.trim();
			}
			else
				EnginDesc = null;
		}
		if (FCode.equalsIgnoreCase("EnginCondition"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginCondition = FValue.trim();
			}
			else
				EnginCondition = null;
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecFlag = FValue.trim();
			}
			else
				SpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("PremCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremCalMode = FValue.trim();
			}
			else
				PremCalMode = null;
		}
		if (FCode.equalsIgnoreCase("PremFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FirstPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FirstPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ContractorPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ContractorPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("SafeProve"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SafeProve = FValue.trim();
			}
			else
				SafeProve = null;
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
		if (FCode.equalsIgnoreCase("EnginDays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginDays = FValue.trim();
			}
			else
				EnginDays = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpEngineeringSchema other = (LCGrpEngineeringSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& EnginName.equals(other.getEnginName())
			&& EnginType.equals(other.getEnginType())
			&& EnginArea == other.getEnginArea()
			&& EnginCost == other.getEnginCost()
			&& EnginPlace.equals(other.getEnginPlace())
			&& fDate.getString(EnginStartDate).equals(other.getEnginStartDate())
			&& fDate.getString(EnginEndDate).equals(other.getEnginEndDate())
			&& InsurerName.equals(other.getInsurerName())
			&& InsurerType.equals(other.getInsurerType())
			&& ContractorName.equals(other.getContractorName())
			&& ContractorType.equals(other.getContractorType())
			&& ContractorType2.equals(other.getContractorType2())
			&& DetailDes.equals(other.getDetailDes())
			&& EnginDesc.equals(other.getEnginDesc())
			&& EnginCondition.equals(other.getEnginCondition())
			&& SpecFlag.equals(other.getSpecFlag())
			&& PremCalMode.equals(other.getPremCalMode())
			&& PremFeeRate == other.getPremFeeRate()
			&& FirstPrem == other.getFirstPrem()
			&& ContractorPeoples == other.getContractorPeoples()
			&& SafeProve.equals(other.getSafeProve())
			&& Remark.equals(other.getRemark())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& EnginDays.equals(other.getEnginDays());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return 1;
		}
		if( strFieldName.equals("EnginName") ) {
			return 2;
		}
		if( strFieldName.equals("EnginType") ) {
			return 3;
		}
		if( strFieldName.equals("EnginArea") ) {
			return 4;
		}
		if( strFieldName.equals("EnginCost") ) {
			return 5;
		}
		if( strFieldName.equals("EnginPlace") ) {
			return 6;
		}
		if( strFieldName.equals("EnginStartDate") ) {
			return 7;
		}
		if( strFieldName.equals("EnginEndDate") ) {
			return 8;
		}
		if( strFieldName.equals("InsurerName") ) {
			return 9;
		}
		if( strFieldName.equals("InsurerType") ) {
			return 10;
		}
		if( strFieldName.equals("ContractorName") ) {
			return 11;
		}
		if( strFieldName.equals("ContractorType") ) {
			return 12;
		}
		if( strFieldName.equals("ContractorType2") ) {
			return 13;
		}
		if( strFieldName.equals("DetailDes") ) {
			return 14;
		}
		if( strFieldName.equals("EnginDesc") ) {
			return 15;
		}
		if( strFieldName.equals("EnginCondition") ) {
			return 16;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 17;
		}
		if( strFieldName.equals("PremCalMode") ) {
			return 18;
		}
		if( strFieldName.equals("PremFeeRate") ) {
			return 19;
		}
		if( strFieldName.equals("FirstPrem") ) {
			return 20;
		}
		if( strFieldName.equals("ContractorPeoples") ) {
			return 21;
		}
		if( strFieldName.equals("SafeProve") ) {
			return 22;
		}
		if( strFieldName.equals("Remark") ) {
			return 23;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 24;
		}
		if( strFieldName.equals("ComCode") ) {
			return 25;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 26;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 27;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
		}
		if( strFieldName.equals("EnginDays") ) {
			return 32;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPropNo";
				break;
			case 2:
				strFieldName = "EnginName";
				break;
			case 3:
				strFieldName = "EnginType";
				break;
			case 4:
				strFieldName = "EnginArea";
				break;
			case 5:
				strFieldName = "EnginCost";
				break;
			case 6:
				strFieldName = "EnginPlace";
				break;
			case 7:
				strFieldName = "EnginStartDate";
				break;
			case 8:
				strFieldName = "EnginEndDate";
				break;
			case 9:
				strFieldName = "InsurerName";
				break;
			case 10:
				strFieldName = "InsurerType";
				break;
			case 11:
				strFieldName = "ContractorName";
				break;
			case 12:
				strFieldName = "ContractorType";
				break;
			case 13:
				strFieldName = "ContractorType2";
				break;
			case 14:
				strFieldName = "DetailDes";
				break;
			case 15:
				strFieldName = "EnginDesc";
				break;
			case 16:
				strFieldName = "EnginCondition";
				break;
			case 17:
				strFieldName = "SpecFlag";
				break;
			case 18:
				strFieldName = "PremCalMode";
				break;
			case 19:
				strFieldName = "PremFeeRate";
				break;
			case 20:
				strFieldName = "FirstPrem";
				break;
			case 21:
				strFieldName = "ContractorPeoples";
				break;
			case 22:
				strFieldName = "SafeProve";
				break;
			case 23:
				strFieldName = "Remark";
				break;
			case 24:
				strFieldName = "ManageCom";
				break;
			case 25:
				strFieldName = "ComCode";
				break;
			case 26:
				strFieldName = "MakeOperator";
				break;
			case 27:
				strFieldName = "MakeDate";
				break;
			case 28:
				strFieldName = "MakeTime";
				break;
			case 29:
				strFieldName = "ModifyOperator";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
				strFieldName = "ModifyTime";
				break;
			case 32:
				strFieldName = "EnginDays";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginArea") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnginCost") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnginPlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnginEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsurerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsurerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContractorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContractorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContractorType2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginCondition") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FirstPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ContractorPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SafeProve") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("EnginDays") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
