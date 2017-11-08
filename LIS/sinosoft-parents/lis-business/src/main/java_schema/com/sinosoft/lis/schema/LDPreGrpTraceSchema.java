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
import com.sinosoft.lis.db.LDPreGrpTraceDB;

/*
 * <p>ClassName: LDPreGrpTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LDPreGrpTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPreGrpTraceSchema.class);
	// @Field
	/** 轨迹号 */
	private String TraceID;
	/** 准客户号码 */
	private String PreCustomerNo;
	/** 准客户名称 */
	private String PreCustomerName;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 单位性质 */
	private String GrpNature;
	/** 行业分类 */
	private String BusiCategory;
	/** 单位总人数 */
	private int SumNumPeople;
	/** 预计投保总人数 */
	private int PreSumPeople;
	/** 预计保费规模 */
	private double PreSumPrem;
	/** 所属上级客户 */
	private String UpCustomerNo;
	/** 销售渠道 */
	private String SaleChannel;
	/** 单位地址(省) */
	private String Province;
	/** 单位地址(市) */
	private String City;
	/** 单位地址(区/县) */
	private String County;
	/** 详细地址 */
	private String DetailAddress;
	/** 公司简介 */
	private String CustomerIntro;
	/** 客户类型 */
	private String CustomerType;
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

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPreGrpTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TraceID";
		pk[1] = "PreCustomerNo";

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
		LDPreGrpTraceSchema cloned = (LDPreGrpTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTraceID()
	{
		return TraceID;
	}
	public void setTraceID(String aTraceID)
	{
		if(aTraceID!=null && aTraceID.length()>10)
			throw new IllegalArgumentException("轨迹号TraceID值"+aTraceID+"的长度"+aTraceID.length()+"大于最大值10");
		TraceID = aTraceID;
	}
	public String getPreCustomerNo()
	{
		return PreCustomerNo;
	}
	public void setPreCustomerNo(String aPreCustomerNo)
	{
		if(aPreCustomerNo!=null && aPreCustomerNo.length()>10)
			throw new IllegalArgumentException("准客户号码PreCustomerNo值"+aPreCustomerNo+"的长度"+aPreCustomerNo.length()+"大于最大值10");
		PreCustomerNo = aPreCustomerNo;
	}
	public String getPreCustomerName()
	{
		return PreCustomerName;
	}
	public void setPreCustomerName(String aPreCustomerName)
	{
		if(aPreCustomerName!=null && aPreCustomerName.length()>120)
			throw new IllegalArgumentException("准客户名称PreCustomerName值"+aPreCustomerName+"的长度"+aPreCustomerName.length()+"大于最大值120");
		PreCustomerName = aPreCustomerName;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>2)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值2");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>30)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值30");
		IDNo = aIDNo;
	}
	public String getGrpNature()
	{
		return GrpNature;
	}
	public void setGrpNature(String aGrpNature)
	{
		if(aGrpNature!=null && aGrpNature.length()>3)
			throw new IllegalArgumentException("单位性质GrpNature值"+aGrpNature+"的长度"+aGrpNature.length()+"大于最大值3");
		GrpNature = aGrpNature;
	}
	public String getBusiCategory()
	{
		return BusiCategory;
	}
	public void setBusiCategory(String aBusiCategory)
	{
		if(aBusiCategory!=null && aBusiCategory.length()>2)
			throw new IllegalArgumentException("行业分类BusiCategory值"+aBusiCategory+"的长度"+aBusiCategory.length()+"大于最大值2");
		BusiCategory = aBusiCategory;
	}
	public int getSumNumPeople()
	{
		return SumNumPeople;
	}
	public void setSumNumPeople(int aSumNumPeople)
	{
		SumNumPeople = aSumNumPeople;
	}
	public void setSumNumPeople(String aSumNumPeople)
	{
		if (aSumNumPeople != null && !aSumNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aSumNumPeople);
			int i = tInteger.intValue();
			SumNumPeople = i;
		}
	}

	public int getPreSumPeople()
	{
		return PreSumPeople;
	}
	public void setPreSumPeople(int aPreSumPeople)
	{
		PreSumPeople = aPreSumPeople;
	}
	public void setPreSumPeople(String aPreSumPeople)
	{
		if (aPreSumPeople != null && !aPreSumPeople.equals(""))
		{
			Integer tInteger = new Integer(aPreSumPeople);
			int i = tInteger.intValue();
			PreSumPeople = i;
		}
	}

	public double getPreSumPrem()
	{
		return PreSumPrem;
	}
	public void setPreSumPrem(double aPreSumPrem)
	{
		PreSumPrem = aPreSumPrem;
	}
	public void setPreSumPrem(String aPreSumPrem)
	{
		if (aPreSumPrem != null && !aPreSumPrem.equals(""))
		{
			Double tDouble = new Double(aPreSumPrem);
			double d = tDouble.doubleValue();
			PreSumPrem = d;
		}
	}

	public String getUpCustomerNo()
	{
		return UpCustomerNo;
	}
	public void setUpCustomerNo(String aUpCustomerNo)
	{
		if(aUpCustomerNo!=null && aUpCustomerNo.length()>10)
			throw new IllegalArgumentException("所属上级客户UpCustomerNo值"+aUpCustomerNo+"的长度"+aUpCustomerNo.length()+"大于最大值10");
		UpCustomerNo = aUpCustomerNo;
	}
	public String getSaleChannel()
	{
		return SaleChannel;
	}
	public void setSaleChannel(String aSaleChannel)
	{
		if(aSaleChannel!=null && aSaleChannel.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChannel值"+aSaleChannel+"的长度"+aSaleChannel.length()+"大于最大值2");
		SaleChannel = aSaleChannel;
	}
	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String aProvince)
	{
		if(aProvince!=null && aProvince.length()>6)
			throw new IllegalArgumentException("单位地址(省)Province值"+aProvince+"的长度"+aProvince.length()+"大于最大值6");
		Province = aProvince;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String aCity)
	{
		if(aCity!=null && aCity.length()>6)
			throw new IllegalArgumentException("单位地址(市)City值"+aCity+"的长度"+aCity.length()+"大于最大值6");
		City = aCity;
	}
	public String getCounty()
	{
		return County;
	}
	public void setCounty(String aCounty)
	{
		if(aCounty!=null && aCounty.length()>6)
			throw new IllegalArgumentException("单位地址(区/县)County值"+aCounty+"的长度"+aCounty.length()+"大于最大值6");
		County = aCounty;
	}
	public String getDetailAddress()
	{
		return DetailAddress;
	}
	public void setDetailAddress(String aDetailAddress)
	{
		if(aDetailAddress!=null && aDetailAddress.length()>200)
			throw new IllegalArgumentException("详细地址DetailAddress值"+aDetailAddress+"的长度"+aDetailAddress.length()+"大于最大值200");
		DetailAddress = aDetailAddress;
	}
	public String getCustomerIntro()
	{
		return CustomerIntro;
	}
	public void setCustomerIntro(String aCustomerIntro)
	{
		if(aCustomerIntro!=null && aCustomerIntro.length()>1000)
			throw new IllegalArgumentException("公司简介CustomerIntro值"+aCustomerIntro+"的长度"+aCustomerIntro.length()+"大于最大值1000");
		CustomerIntro = aCustomerIntro;
	}
	/**
	* 0-准客户<p>
	* 1-客户
	*/
	public String getCustomerType()
	{
		return CustomerType;
	}
	public void setCustomerType(String aCustomerType)
	{
		if(aCustomerType!=null && aCustomerType.length()>2)
			throw new IllegalArgumentException("客户类型CustomerType值"+aCustomerType+"的长度"+aCustomerType.length()+"大于最大值2");
		CustomerType = aCustomerType;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
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
	* 使用另外一个 LDPreGrpTraceSchema 对象给 Schema 赋值
	* @param: aLDPreGrpTraceSchema LDPreGrpTraceSchema
	**/
	public void setSchema(LDPreGrpTraceSchema aLDPreGrpTraceSchema)
	{
		this.TraceID = aLDPreGrpTraceSchema.getTraceID();
		this.PreCustomerNo = aLDPreGrpTraceSchema.getPreCustomerNo();
		this.PreCustomerName = aLDPreGrpTraceSchema.getPreCustomerName();
		this.IDType = aLDPreGrpTraceSchema.getIDType();
		this.IDNo = aLDPreGrpTraceSchema.getIDNo();
		this.GrpNature = aLDPreGrpTraceSchema.getGrpNature();
		this.BusiCategory = aLDPreGrpTraceSchema.getBusiCategory();
		this.SumNumPeople = aLDPreGrpTraceSchema.getSumNumPeople();
		this.PreSumPeople = aLDPreGrpTraceSchema.getPreSumPeople();
		this.PreSumPrem = aLDPreGrpTraceSchema.getPreSumPrem();
		this.UpCustomerNo = aLDPreGrpTraceSchema.getUpCustomerNo();
		this.SaleChannel = aLDPreGrpTraceSchema.getSaleChannel();
		this.Province = aLDPreGrpTraceSchema.getProvince();
		this.City = aLDPreGrpTraceSchema.getCity();
		this.County = aLDPreGrpTraceSchema.getCounty();
		this.DetailAddress = aLDPreGrpTraceSchema.getDetailAddress();
		this.CustomerIntro = aLDPreGrpTraceSchema.getCustomerIntro();
		this.CustomerType = aLDPreGrpTraceSchema.getCustomerType();
		this.State = aLDPreGrpTraceSchema.getState();
		this.ManageCom = aLDPreGrpTraceSchema.getManageCom();
		this.ComCode = aLDPreGrpTraceSchema.getComCode();
		this.MakeOperator = aLDPreGrpTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDPreGrpTraceSchema.getMakeDate());
		this.MakeTime = aLDPreGrpTraceSchema.getMakeTime();
		this.ModifyOperator = aLDPreGrpTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDPreGrpTraceSchema.getModifyDate());
		this.ModifyTime = aLDPreGrpTraceSchema.getModifyTime();
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
			if( rs.getString("TraceID") == null )
				this.TraceID = null;
			else
				this.TraceID = rs.getString("TraceID").trim();

			if( rs.getString("PreCustomerNo") == null )
				this.PreCustomerNo = null;
			else
				this.PreCustomerNo = rs.getString("PreCustomerNo").trim();

			if( rs.getString("PreCustomerName") == null )
				this.PreCustomerName = null;
			else
				this.PreCustomerName = rs.getString("PreCustomerName").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			if( rs.getString("BusiCategory") == null )
				this.BusiCategory = null;
			else
				this.BusiCategory = rs.getString("BusiCategory").trim();

			this.SumNumPeople = rs.getInt("SumNumPeople");
			this.PreSumPeople = rs.getInt("PreSumPeople");
			this.PreSumPrem = rs.getDouble("PreSumPrem");
			if( rs.getString("UpCustomerNo") == null )
				this.UpCustomerNo = null;
			else
				this.UpCustomerNo = rs.getString("UpCustomerNo").trim();

			if( rs.getString("SaleChannel") == null )
				this.SaleChannel = null;
			else
				this.SaleChannel = rs.getString("SaleChannel").trim();

			if( rs.getString("Province") == null )
				this.Province = null;
			else
				this.Province = rs.getString("Province").trim();

			if( rs.getString("City") == null )
				this.City = null;
			else
				this.City = rs.getString("City").trim();

			if( rs.getString("County") == null )
				this.County = null;
			else
				this.County = rs.getString("County").trim();

			if( rs.getString("DetailAddress") == null )
				this.DetailAddress = null;
			else
				this.DetailAddress = rs.getString("DetailAddress").trim();

			if( rs.getString("CustomerIntro") == null )
				this.CustomerIntro = null;
			else
				this.CustomerIntro = rs.getString("CustomerIntro").trim();

			if( rs.getString("CustomerType") == null )
				this.CustomerType = null;
			else
				this.CustomerType = rs.getString("CustomerType").trim();

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
			logger.debug("数据库中的LDPreGrpTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPreGrpTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPreGrpTraceSchema getSchema()
	{
		LDPreGrpTraceSchema aLDPreGrpTraceSchema = new LDPreGrpTraceSchema();
		aLDPreGrpTraceSchema.setSchema(this);
		return aLDPreGrpTraceSchema;
	}

	public LDPreGrpTraceDB getDB()
	{
		LDPreGrpTraceDB aDBOper = new LDPreGrpTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPreGrpTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TraceID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreSumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreSumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChannel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Province)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(City)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(County)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DetailAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerIntro)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPreGrpTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TraceID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PreCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PreCustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BusiCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SumNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			PreSumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			PreSumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			UpCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SaleChannel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			DetailAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CustomerIntro = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CustomerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPreGrpTraceSchema";
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
		if (FCode.equalsIgnoreCase("TraceID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TraceID));
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerNo));
		}
		if (FCode.equalsIgnoreCase("PreCustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerName));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("BusiCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiCategory));
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumNumPeople));
		}
		if (FCode.equalsIgnoreCase("PreSumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreSumPeople));
		}
		if (FCode.equalsIgnoreCase("PreSumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreSumPrem));
		}
		if (FCode.equalsIgnoreCase("UpCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpCustomerNo));
		}
		if (FCode.equalsIgnoreCase("SaleChannel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChannel));
		}
		if (FCode.equalsIgnoreCase("Province"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Province));
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(City));
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(County));
		}
		if (FCode.equalsIgnoreCase("DetailAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailAddress));
		}
		if (FCode.equalsIgnoreCase("CustomerIntro"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerIntro));
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerType));
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
				strFieldValue = StrTool.GBKToUnicode(TraceID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BusiCategory);
				break;
			case 7:
				strFieldValue = String.valueOf(SumNumPeople);
				break;
			case 8:
				strFieldValue = String.valueOf(PreSumPeople);
				break;
			case 9:
				strFieldValue = String.valueOf(PreSumPrem);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UpCustomerNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SaleChannel);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(County);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DetailAddress);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CustomerIntro);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CustomerType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
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

		if (FCode.equalsIgnoreCase("TraceID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TraceID = FValue.trim();
			}
			else
				TraceID = null;
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerNo = FValue.trim();
			}
			else
				PreCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("PreCustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerName = FValue.trim();
			}
			else
				PreCustomerName = null;
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
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNature = FValue.trim();
			}
			else
				GrpNature = null;
		}
		if (FCode.equalsIgnoreCase("BusiCategory"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiCategory = FValue.trim();
			}
			else
				BusiCategory = null;
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumNumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("PreSumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PreSumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("PreSumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreSumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("UpCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpCustomerNo = FValue.trim();
			}
			else
				UpCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("SaleChannel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChannel = FValue.trim();
			}
			else
				SaleChannel = null;
		}
		if (FCode.equalsIgnoreCase("Province"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Province = FValue.trim();
			}
			else
				Province = null;
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				City = FValue.trim();
			}
			else
				City = null;
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				County = FValue.trim();
			}
			else
				County = null;
		}
		if (FCode.equalsIgnoreCase("DetailAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailAddress = FValue.trim();
			}
			else
				DetailAddress = null;
		}
		if (FCode.equalsIgnoreCase("CustomerIntro"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerIntro = FValue.trim();
			}
			else
				CustomerIntro = null;
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
		LDPreGrpTraceSchema other = (LDPreGrpTraceSchema)otherObject;
		return
			TraceID.equals(other.getTraceID())
			&& PreCustomerNo.equals(other.getPreCustomerNo())
			&& PreCustomerName.equals(other.getPreCustomerName())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& GrpNature.equals(other.getGrpNature())
			&& BusiCategory.equals(other.getBusiCategory())
			&& SumNumPeople == other.getSumNumPeople()
			&& PreSumPeople == other.getPreSumPeople()
			&& PreSumPrem == other.getPreSumPrem()
			&& UpCustomerNo.equals(other.getUpCustomerNo())
			&& SaleChannel.equals(other.getSaleChannel())
			&& Province.equals(other.getProvince())
			&& City.equals(other.getCity())
			&& County.equals(other.getCounty())
			&& DetailAddress.equals(other.getDetailAddress())
			&& CustomerIntro.equals(other.getCustomerIntro())
			&& CustomerType.equals(other.getCustomerType())
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
		if( strFieldName.equals("TraceID") ) {
			return 0;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("PreCustomerName") ) {
			return 2;
		}
		if( strFieldName.equals("IDType") ) {
			return 3;
		}
		if( strFieldName.equals("IDNo") ) {
			return 4;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 5;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return 6;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return 7;
		}
		if( strFieldName.equals("PreSumPeople") ) {
			return 8;
		}
		if( strFieldName.equals("PreSumPrem") ) {
			return 9;
		}
		if( strFieldName.equals("UpCustomerNo") ) {
			return 10;
		}
		if( strFieldName.equals("SaleChannel") ) {
			return 11;
		}
		if( strFieldName.equals("Province") ) {
			return 12;
		}
		if( strFieldName.equals("City") ) {
			return 13;
		}
		if( strFieldName.equals("County") ) {
			return 14;
		}
		if( strFieldName.equals("DetailAddress") ) {
			return 15;
		}
		if( strFieldName.equals("CustomerIntro") ) {
			return 16;
		}
		if( strFieldName.equals("CustomerType") ) {
			return 17;
		}
		if( strFieldName.equals("State") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 19;
		}
		if( strFieldName.equals("ComCode") ) {
			return 20;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
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
				strFieldName = "TraceID";
				break;
			case 1:
				strFieldName = "PreCustomerNo";
				break;
			case 2:
				strFieldName = "PreCustomerName";
				break;
			case 3:
				strFieldName = "IDType";
				break;
			case 4:
				strFieldName = "IDNo";
				break;
			case 5:
				strFieldName = "GrpNature";
				break;
			case 6:
				strFieldName = "BusiCategory";
				break;
			case 7:
				strFieldName = "SumNumPeople";
				break;
			case 8:
				strFieldName = "PreSumPeople";
				break;
			case 9:
				strFieldName = "PreSumPrem";
				break;
			case 10:
				strFieldName = "UpCustomerNo";
				break;
			case 11:
				strFieldName = "SaleChannel";
				break;
			case 12:
				strFieldName = "Province";
				break;
			case 13:
				strFieldName = "City";
				break;
			case 14:
				strFieldName = "County";
				break;
			case 15:
				strFieldName = "DetailAddress";
				break;
			case 16:
				strFieldName = "CustomerIntro";
				break;
			case 17:
				strFieldName = "CustomerType";
				break;
			case 18:
				strFieldName = "State";
				break;
			case 19:
				strFieldName = "ManageCom";
				break;
			case 20:
				strFieldName = "ComCode";
				break;
			case 21:
				strFieldName = "MakeOperator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyOperator";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
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
		if( strFieldName.equals("TraceID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreCustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PreSumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PreSumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UpCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChannel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Province") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("City") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("County") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerIntro") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerType") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
