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
import com.sinosoft.lis.db.LCProposalDB;

/*
 * <p>ClassName: LCProposalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新单管理
 */
public class LCProposalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCProposalSchema.class);
	// @Field
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 印刷号 */
	private String PrtNo;
	/** 其他类型 */
	private String OtherType;
	/** 其他号码 */
	private String OtherNo;
	/** 报价单号 */
	private String OfferListNo;
	/** 投保书打印方式 */
	private String PrtType;
	/** 保单产品类型 */
	private String ContPlanType;
	/** 保单类型 */
	private String ContType;
	/** 保单方案标识 */
	private String PolicyFlag;
	/** 卡单标识 */
	private String CardFlag;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
	/** 扫描人 */
	private String ScanOperator;
	/** 扫描日期 */
	private Date ScanDate;
	/** 扫描时间 */
	private String ScanTime;
	/** 扫描机构 */
	private String ScanCom;
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

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCProposalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpPropNo";

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
		LCProposalSchema cloned = (LCProposalSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		if(aPrtNo!=null && aPrtNo.length()>20)
			throw new IllegalArgumentException("印刷号PrtNo值"+aPrtNo+"的长度"+aPrtNo.length()+"大于最大值20");
		PrtNo = aPrtNo;
	}
	public String getOtherType()
	{
		return OtherType;
	}
	public void setOtherType(String aOtherType)
	{
		if(aOtherType!=null && aOtherType.length()>2)
			throw new IllegalArgumentException("其他类型OtherType值"+aOtherType+"的长度"+aOtherType.length()+"大于最大值2");
		OtherType = aOtherType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其他号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getOfferListNo()
	{
		return OfferListNo;
	}
	public void setOfferListNo(String aOfferListNo)
	{
		if(aOfferListNo!=null && aOfferListNo.length()>20)
			throw new IllegalArgumentException("报价单号OfferListNo值"+aOfferListNo+"的长度"+aOfferListNo.length()+"大于最大值20");
		OfferListNo = aOfferListNo;
	}
	/**
	* 0-系统打印<p>
	* 1-预印
	*/
	public String getPrtType()
	{
		return PrtType;
	}
	public void setPrtType(String aPrtType)
	{
		if(aPrtType!=null && aPrtType.length()>2)
			throw new IllegalArgumentException("投保书打印方式PrtType值"+aPrtType+"的长度"+aPrtType.length()+"大于最大值2");
		PrtType = aPrtType;
	}
	/**
	* 00-普通保单<p>
	* 01-建工险<p>
	* 02-账户型
	*/
	public String getContPlanType()
	{
		return ContPlanType;
	}
	public void setContPlanType(String aContPlanType)
	{
		if(aContPlanType!=null && aContPlanType.length()>2)
			throw new IllegalArgumentException("保单产品类型ContPlanType值"+aContPlanType+"的长度"+aContPlanType.length()+"大于最大值2");
		ContPlanType = aContPlanType;
	}
	/**
	* 01-团体保单，02-个人保单，03-家庭单，09-其它
	*/
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		if(aContType!=null && aContType.length()>2)
			throw new IllegalArgumentException("保单类型ContType值"+aContType+"的长度"+aContType.length()+"大于最大值2");
		ContType = aContType;
	}
	/**
	* 0-普通保单<p>
	* C-弹性福利计划核心方案保单<p>
	* S-弹性福利计划自选方案保单
	*/
	public String getPolicyFlag()
	{
		return PolicyFlag;
	}
	public void setPolicyFlag(String aPolicyFlag)
	{
		if(aPolicyFlag!=null && aPolicyFlag.length()>2)
			throw new IllegalArgumentException("保单方案标识PolicyFlag值"+aPolicyFlag+"的长度"+aPolicyFlag.length()+"大于最大值2");
		PolicyFlag = aPolicyFlag;
	}
	public String getCardFlag()
	{
		return CardFlag;
	}
	public void setCardFlag(String aCardFlag)
	{
		if(aCardFlag!=null && aCardFlag.length()>2)
			throw new IllegalArgumentException("卡单标识CardFlag值"+aCardFlag+"的长度"+aCardFlag.length()+"大于最大值2");
		CardFlag = aCardFlag;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>30)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值30");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>30)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值30");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>30)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值30");
		Segment3 = aSegment3;
	}
	public String getScanOperator()
	{
		return ScanOperator;
	}
	public void setScanOperator(String aScanOperator)
	{
		if(aScanOperator!=null && aScanOperator.length()>30)
			throw new IllegalArgumentException("扫描人ScanOperator值"+aScanOperator+"的长度"+aScanOperator.length()+"大于最大值30");
		ScanOperator = aScanOperator;
	}
	public String getScanDate()
	{
		if( ScanDate != null )
			return fDate.getString(ScanDate);
		else
			return null;
	}
	public void setScanDate(Date aScanDate)
	{
		ScanDate = aScanDate;
	}
	public void setScanDate(String aScanDate)
	{
		if (aScanDate != null && !aScanDate.equals("") )
		{
			ScanDate = fDate.getDate( aScanDate );
		}
		else
			ScanDate = null;
	}

	public String getScanTime()
	{
		return ScanTime;
	}
	public void setScanTime(String aScanTime)
	{
		if(aScanTime!=null && aScanTime.length()>8)
			throw new IllegalArgumentException("扫描时间ScanTime值"+aScanTime+"的长度"+aScanTime.length()+"大于最大值8");
		ScanTime = aScanTime;
	}
	public String getScanCom()
	{
		return ScanCom;
	}
	public void setScanCom(String aScanCom)
	{
		if(aScanCom!=null && aScanCom.length()>20)
			throw new IllegalArgumentException("扫描机构ScanCom值"+aScanCom+"的长度"+aScanCom.length()+"大于最大值20");
		ScanCom = aScanCom;
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
	* 使用另外一个 LCProposalSchema 对象给 Schema 赋值
	* @param: aLCProposalSchema LCProposalSchema
	**/
	public void setSchema(LCProposalSchema aLCProposalSchema)
	{
		this.GrpPropNo = aLCProposalSchema.getGrpPropNo();
		this.PrtNo = aLCProposalSchema.getPrtNo();
		this.OtherType = aLCProposalSchema.getOtherType();
		this.OtherNo = aLCProposalSchema.getOtherNo();
		this.OfferListNo = aLCProposalSchema.getOfferListNo();
		this.PrtType = aLCProposalSchema.getPrtType();
		this.ContPlanType = aLCProposalSchema.getContPlanType();
		this.ContType = aLCProposalSchema.getContType();
		this.PolicyFlag = aLCProposalSchema.getPolicyFlag();
		this.CardFlag = aLCProposalSchema.getCardFlag();
		this.Segment1 = aLCProposalSchema.getSegment1();
		this.Segment2 = aLCProposalSchema.getSegment2();
		this.Segment3 = aLCProposalSchema.getSegment3();
		this.ScanOperator = aLCProposalSchema.getScanOperator();
		this.ScanDate = fDate.getDate( aLCProposalSchema.getScanDate());
		this.ScanTime = aLCProposalSchema.getScanTime();
		this.ScanCom = aLCProposalSchema.getScanCom();
		this.ManageCom = aLCProposalSchema.getManageCom();
		this.ComCode = aLCProposalSchema.getComCode();
		this.MakeOperator = aLCProposalSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLCProposalSchema.getMakeDate());
		this.MakeTime = aLCProposalSchema.getMakeTime();
		this.ModifyOperator = aLCProposalSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLCProposalSchema.getModifyDate());
		this.ModifyTime = aLCProposalSchema.getModifyTime();
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
			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("OtherType") == null )
				this.OtherType = null;
			else
				this.OtherType = rs.getString("OtherType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OfferListNo") == null )
				this.OfferListNo = null;
			else
				this.OfferListNo = rs.getString("OfferListNo").trim();

			if( rs.getString("PrtType") == null )
				this.PrtType = null;
			else
				this.PrtType = rs.getString("PrtType").trim();

			if( rs.getString("ContPlanType") == null )
				this.ContPlanType = null;
			else
				this.ContPlanType = rs.getString("ContPlanType").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("PolicyFlag") == null )
				this.PolicyFlag = null;
			else
				this.PolicyFlag = rs.getString("PolicyFlag").trim();

			if( rs.getString("CardFlag") == null )
				this.CardFlag = null;
			else
				this.CardFlag = rs.getString("CardFlag").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("ScanOperator") == null )
				this.ScanOperator = null;
			else
				this.ScanOperator = rs.getString("ScanOperator").trim();

			this.ScanDate = rs.getDate("ScanDate");
			if( rs.getString("ScanTime") == null )
				this.ScanTime = null;
			else
				this.ScanTime = rs.getString("ScanTime").trim();

			if( rs.getString("ScanCom") == null )
				this.ScanCom = null;
			else
				this.ScanCom = rs.getString("ScanCom").trim();

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
			logger.debug("数据库中的LCProposal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCProposalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCProposalSchema getSchema()
	{
		LCProposalSchema aLCProposalSchema = new LCProposalSchema();
		aLCProposalSchema.setSchema(this);
		return aLCProposalSchema;
	}

	public LCProposalDB getDB()
	{
		LCProposalDB aDBOper = new LCProposalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCProposal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OfferListNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ScanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanCom)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCProposal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OfferListNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ContPlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PolicyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CardFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ScanOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ScanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ScanTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ScanCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCProposalSchema";
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
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferListNo));
		}
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtType));
		}
		if (FCode.equalsIgnoreCase("ContPlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanType));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("PolicyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyFlag));
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardFlag));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanOperator));
		}
		if (FCode.equalsIgnoreCase("ScanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getScanDate()));
		}
		if (FCode.equalsIgnoreCase("ScanTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanTime));
		}
		if (FCode.equalsIgnoreCase("ScanCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanCom));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OfferListNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ContPlanType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PolicyFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CardFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ScanOperator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getScanDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ScanTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ScanCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
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

		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherType = FValue.trim();
			}
			else
				OtherType = null;
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
		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OfferListNo = FValue.trim();
			}
			else
				OfferListNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtType = FValue.trim();
			}
			else
				PrtType = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanType = FValue.trim();
			}
			else
				ContPlanType = null;
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("PolicyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyFlag = FValue.trim();
			}
			else
				PolicyFlag = null;
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardFlag = FValue.trim();
			}
			else
				CardFlag = null;
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanOperator = FValue.trim();
			}
			else
				ScanOperator = null;
		}
		if (FCode.equalsIgnoreCase("ScanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ScanDate = fDate.getDate( FValue );
			}
			else
				ScanDate = null;
		}
		if (FCode.equalsIgnoreCase("ScanTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanTime = FValue.trim();
			}
			else
				ScanTime = null;
		}
		if (FCode.equalsIgnoreCase("ScanCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanCom = FValue.trim();
			}
			else
				ScanCom = null;
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
		LCProposalSchema other = (LCProposalSchema)otherObject;
		return
			GrpPropNo.equals(other.getGrpPropNo())
			&& PrtNo.equals(other.getPrtNo())
			&& OtherType.equals(other.getOtherType())
			&& OtherNo.equals(other.getOtherNo())
			&& OfferListNo.equals(other.getOfferListNo())
			&& PrtType.equals(other.getPrtType())
			&& ContPlanType.equals(other.getContPlanType())
			&& ContType.equals(other.getContType())
			&& PolicyFlag.equals(other.getPolicyFlag())
			&& CardFlag.equals(other.getCardFlag())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& ScanOperator.equals(other.getScanOperator())
			&& fDate.getString(ScanDate).equals(other.getScanDate())
			&& ScanTime.equals(other.getScanTime())
			&& ScanCom.equals(other.getScanCom())
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
		if( strFieldName.equals("GrpPropNo") ) {
			return 0;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherType") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("OfferListNo") ) {
			return 4;
		}
		if( strFieldName.equals("PrtType") ) {
			return 5;
		}
		if( strFieldName.equals("ContPlanType") ) {
			return 6;
		}
		if( strFieldName.equals("ContType") ) {
			return 7;
		}
		if( strFieldName.equals("PolicyFlag") ) {
			return 8;
		}
		if( strFieldName.equals("CardFlag") ) {
			return 9;
		}
		if( strFieldName.equals("Segment1") ) {
			return 10;
		}
		if( strFieldName.equals("Segment2") ) {
			return 11;
		}
		if( strFieldName.equals("Segment3") ) {
			return 12;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return 13;
		}
		if( strFieldName.equals("ScanDate") ) {
			return 14;
		}
		if( strFieldName.equals("ScanTime") ) {
			return 15;
		}
		if( strFieldName.equals("ScanCom") ) {
			return 16;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 17;
		}
		if( strFieldName.equals("ComCode") ) {
			return 18;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
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
				strFieldName = "GrpPropNo";
				break;
			case 1:
				strFieldName = "PrtNo";
				break;
			case 2:
				strFieldName = "OtherType";
				break;
			case 3:
				strFieldName = "OtherNo";
				break;
			case 4:
				strFieldName = "OfferListNo";
				break;
			case 5:
				strFieldName = "PrtType";
				break;
			case 6:
				strFieldName = "ContPlanType";
				break;
			case 7:
				strFieldName = "ContType";
				break;
			case 8:
				strFieldName = "PolicyFlag";
				break;
			case 9:
				strFieldName = "CardFlag";
				break;
			case 10:
				strFieldName = "Segment1";
				break;
			case 11:
				strFieldName = "Segment2";
				break;
			case 12:
				strFieldName = "Segment3";
				break;
			case 13:
				strFieldName = "ScanOperator";
				break;
			case 14:
				strFieldName = "ScanDate";
				break;
			case 15:
				strFieldName = "ScanTime";
				break;
			case 16:
				strFieldName = "ScanCom";
				break;
			case 17:
				strFieldName = "ManageCom";
				break;
			case 18:
				strFieldName = "ComCode";
				break;
			case 19:
				strFieldName = "MakeOperator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyOperator";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
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
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OfferListNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ScanTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
