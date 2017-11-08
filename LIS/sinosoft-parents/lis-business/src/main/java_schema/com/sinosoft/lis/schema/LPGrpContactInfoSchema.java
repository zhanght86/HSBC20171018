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
import com.sinosoft.lis.db.LPGrpContactInfoDB;

/*
 * <p>ClassName: LPGrpContactInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPGrpContactInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGrpContactInfoSchema.class);
	// @Field
	/** 保全批单号 */
	private String EdorNo;
	/** 保全项目 */
	private String EdorType;
	/** 团体保单号 */
	private String GrpContNo;
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 客户号码 */
	private String CustomerNo;
	/** 省 */
	private String Province;
	/** 市 */
	private String City;
	/** 区/县 */
	private String County;
	/** 单位地址 */
	private String Address;
	/** 单位邮编 */
	private String ZipCode;
	/** 联系人1 */
	private String LinkMan1;
	/** 部门1 */
	private String Department1;
	/** 职务1 */
	private String HeadShip1;
	/** 手机1 */
	private String MobilePhone1;
	/** 电话1 */
	private String Phone1;
	/** 传真1 */
	private String Fax1;
	/** Email1 */
	private String EMail1;
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

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGrpContactInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "GrpContNo";
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
		LPGrpContactInfoSchema cloned = (LPGrpContactInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("保全批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>10)
			throw new IllegalArgumentException("保全项目EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值10");
		EdorType = aEdorType;
	}
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
	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String aProvince)
	{
		if(aProvince!=null && aProvince.length()>10)
			throw new IllegalArgumentException("省Province值"+aProvince+"的长度"+aProvince.length()+"大于最大值10");
		Province = aProvince;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String aCity)
	{
		if(aCity!=null && aCity.length()>10)
			throw new IllegalArgumentException("市City值"+aCity+"的长度"+aCity.length()+"大于最大值10");
		City = aCity;
	}
	public String getCounty()
	{
		return County;
	}
	public void setCounty(String aCounty)
	{
		if(aCounty!=null && aCounty.length()>10)
			throw new IllegalArgumentException("区/县County值"+aCounty+"的长度"+aCounty.length()+"大于最大值10");
		County = aCounty;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		if(aAddress!=null && aAddress.length()>300)
			throw new IllegalArgumentException("单位地址Address值"+aAddress+"的长度"+aAddress.length()+"大于最大值300");
		Address = aAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		if(aZipCode!=null && aZipCode.length()>10)
			throw new IllegalArgumentException("单位邮编ZipCode值"+aZipCode+"的长度"+aZipCode.length()+"大于最大值10");
		ZipCode = aZipCode;
	}
	public String getLinkMan1()
	{
		return LinkMan1;
	}
	public void setLinkMan1(String aLinkMan1)
	{
		if(aLinkMan1!=null && aLinkMan1.length()>200)
			throw new IllegalArgumentException("联系人1LinkMan1值"+aLinkMan1+"的长度"+aLinkMan1.length()+"大于最大值200");
		LinkMan1 = aLinkMan1;
	}
	public String getDepartment1()
	{
		return Department1;
	}
	public void setDepartment1(String aDepartment1)
	{
		if(aDepartment1!=null && aDepartment1.length()>200)
			throw new IllegalArgumentException("部门1Department1值"+aDepartment1+"的长度"+aDepartment1.length()+"大于最大值200");
		Department1 = aDepartment1;
	}
	public String getHeadShip1()
	{
		return HeadShip1;
	}
	public void setHeadShip1(String aHeadShip1)
	{
		if(aHeadShip1!=null && aHeadShip1.length()>100)
			throw new IllegalArgumentException("职务1HeadShip1值"+aHeadShip1+"的长度"+aHeadShip1.length()+"大于最大值100");
		HeadShip1 = aHeadShip1;
	}
	public String getMobilePhone1()
	{
		return MobilePhone1;
	}
	public void setMobilePhone1(String aMobilePhone1)
	{
		if(aMobilePhone1!=null && aMobilePhone1.length()>20)
			throw new IllegalArgumentException("手机1MobilePhone1值"+aMobilePhone1+"的长度"+aMobilePhone1.length()+"大于最大值20");
		MobilePhone1 = aMobilePhone1;
	}
	public String getPhone1()
	{
		return Phone1;
	}
	public void setPhone1(String aPhone1)
	{
		if(aPhone1!=null && aPhone1.length()>20)
			throw new IllegalArgumentException("电话1Phone1值"+aPhone1+"的长度"+aPhone1.length()+"大于最大值20");
		Phone1 = aPhone1;
	}
	public String getFax1()
	{
		return Fax1;
	}
	public void setFax1(String aFax1)
	{
		if(aFax1!=null && aFax1.length()>20)
			throw new IllegalArgumentException("传真1Fax1值"+aFax1+"的长度"+aFax1.length()+"大于最大值20");
		Fax1 = aFax1;
	}
	public String getEMail1()
	{
		return EMail1;
	}
	public void setEMail1(String aEMail1)
	{
		if(aEMail1!=null && aEMail1.length()>60)
			throw new IllegalArgumentException("Email1EMail1值"+aEMail1+"的长度"+aEMail1.length()+"大于最大值60");
		EMail1 = aEMail1;
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
	* 使用另外一个 LPGrpContactInfoSchema 对象给 Schema 赋值
	* @param: aLPGrpContactInfoSchema LPGrpContactInfoSchema
	**/
	public void setSchema(LPGrpContactInfoSchema aLPGrpContactInfoSchema)
	{
		this.EdorNo = aLPGrpContactInfoSchema.getEdorNo();
		this.EdorType = aLPGrpContactInfoSchema.getEdorType();
		this.GrpContNo = aLPGrpContactInfoSchema.getGrpContNo();
		this.GrpPropNo = aLPGrpContactInfoSchema.getGrpPropNo();
		this.CustomerNo = aLPGrpContactInfoSchema.getCustomerNo();
		this.Province = aLPGrpContactInfoSchema.getProvince();
		this.City = aLPGrpContactInfoSchema.getCity();
		this.County = aLPGrpContactInfoSchema.getCounty();
		this.Address = aLPGrpContactInfoSchema.getAddress();
		this.ZipCode = aLPGrpContactInfoSchema.getZipCode();
		this.LinkMan1 = aLPGrpContactInfoSchema.getLinkMan1();
		this.Department1 = aLPGrpContactInfoSchema.getDepartment1();
		this.HeadShip1 = aLPGrpContactInfoSchema.getHeadShip1();
		this.MobilePhone1 = aLPGrpContactInfoSchema.getMobilePhone1();
		this.Phone1 = aLPGrpContactInfoSchema.getPhone1();
		this.Fax1 = aLPGrpContactInfoSchema.getFax1();
		this.EMail1 = aLPGrpContactInfoSchema.getEMail1();
		this.MakeOperator = aLPGrpContactInfoSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPGrpContactInfoSchema.getMakeDate());
		this.MakeTime = aLPGrpContactInfoSchema.getMakeTime();
		this.ModifyOperator = aLPGrpContactInfoSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPGrpContactInfoSchema.getModifyDate());
		this.ModifyTime = aLPGrpContactInfoSchema.getModifyTime();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

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

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("LinkMan1") == null )
				this.LinkMan1 = null;
			else
				this.LinkMan1 = rs.getString("LinkMan1").trim();

			if( rs.getString("Department1") == null )
				this.Department1 = null;
			else
				this.Department1 = rs.getString("Department1").trim();

			if( rs.getString("HeadShip1") == null )
				this.HeadShip1 = null;
			else
				this.HeadShip1 = rs.getString("HeadShip1").trim();

			if( rs.getString("MobilePhone1") == null )
				this.MobilePhone1 = null;
			else
				this.MobilePhone1 = rs.getString("MobilePhone1").trim();

			if( rs.getString("Phone1") == null )
				this.Phone1 = null;
			else
				this.Phone1 = rs.getString("Phone1").trim();

			if( rs.getString("Fax1") == null )
				this.Fax1 = null;
			else
				this.Fax1 = rs.getString("Fax1").trim();

			if( rs.getString("EMail1") == null )
				this.EMail1 = null;
			else
				this.EMail1 = rs.getString("EMail1").trim();

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
			logger.debug("数据库中的LPGrpContactInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContactInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGrpContactInfoSchema getSchema()
	{
		LPGrpContactInfoSchema aLPGrpContactInfoSchema = new LPGrpContactInfoSchema();
		aLPGrpContactInfoSchema.setSchema(this);
		return aLPGrpContactInfoSchema;
	}

	public LPGrpContactInfoDB getDB()
	{
		LPGrpContactInfoDB aDBOper = new LPGrpContactInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpContactInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Province)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(City)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(County)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MobilePhone1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpContactInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			LinkMan1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Department1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			HeadShip1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MobilePhone1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Phone1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Fax1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			EMail1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContactInfoSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
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
		if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("LinkMan1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan1));
		}
		if (FCode.equalsIgnoreCase("Department1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department1));
		}
		if (FCode.equalsIgnoreCase("HeadShip1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadShip1));
		}
		if (FCode.equalsIgnoreCase("MobilePhone1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobilePhone1));
		}
		if (FCode.equalsIgnoreCase("Phone1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone1));
		}
		if (FCode.equalsIgnoreCase("Fax1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax1));
		}
		if (FCode.equalsIgnoreCase("EMail1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail1));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(County);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(LinkMan1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Department1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(HeadShip1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MobilePhone1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Phone1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Fax1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(EMail1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
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
		if (FCode.equalsIgnoreCase("Address"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Address = FValue.trim();
			}
			else
				Address = null;
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan1 = FValue.trim();
			}
			else
				LinkMan1 = null;
		}
		if (FCode.equalsIgnoreCase("Department1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Department1 = FValue.trim();
			}
			else
				Department1 = null;
		}
		if (FCode.equalsIgnoreCase("HeadShip1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadShip1 = FValue.trim();
			}
			else
				HeadShip1 = null;
		}
		if (FCode.equalsIgnoreCase("MobilePhone1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobilePhone1 = FValue.trim();
			}
			else
				MobilePhone1 = null;
		}
		if (FCode.equalsIgnoreCase("Phone1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone1 = FValue.trim();
			}
			else
				Phone1 = null;
		}
		if (FCode.equalsIgnoreCase("Fax1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax1 = FValue.trim();
			}
			else
				Fax1 = null;
		}
		if (FCode.equalsIgnoreCase("EMail1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail1 = FValue.trim();
			}
			else
				EMail1 = null;
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
		LPGrpContactInfoSchema other = (LPGrpContactInfoSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Province.equals(other.getProvince())
			&& City.equals(other.getCity())
			&& County.equals(other.getCounty())
			&& Address.equals(other.getAddress())
			&& ZipCode.equals(other.getZipCode())
			&& LinkMan1.equals(other.getLinkMan1())
			&& Department1.equals(other.getDepartment1())
			&& HeadShip1.equals(other.getHeadShip1())
			&& MobilePhone1.equals(other.getMobilePhone1())
			&& Phone1.equals(other.getPhone1())
			&& Fax1.equals(other.getFax1())
			&& EMail1.equals(other.getEMail1())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 4;
		}
		if( strFieldName.equals("Province") ) {
			return 5;
		}
		if( strFieldName.equals("City") ) {
			return 6;
		}
		if( strFieldName.equals("County") ) {
			return 7;
		}
		if( strFieldName.equals("Address") ) {
			return 8;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 9;
		}
		if( strFieldName.equals("LinkMan1") ) {
			return 10;
		}
		if( strFieldName.equals("Department1") ) {
			return 11;
		}
		if( strFieldName.equals("HeadShip1") ) {
			return 12;
		}
		if( strFieldName.equals("MobilePhone1") ) {
			return 13;
		}
		if( strFieldName.equals("Phone1") ) {
			return 14;
		}
		if( strFieldName.equals("Fax1") ) {
			return 15;
		}
		if( strFieldName.equals("EMail1") ) {
			return 16;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "GrpPropNo";
				break;
			case 4:
				strFieldName = "CustomerNo";
				break;
			case 5:
				strFieldName = "Province";
				break;
			case 6:
				strFieldName = "City";
				break;
			case 7:
				strFieldName = "County";
				break;
			case 8:
				strFieldName = "Address";
				break;
			case 9:
				strFieldName = "ZipCode";
				break;
			case 10:
				strFieldName = "LinkMan1";
				break;
			case 11:
				strFieldName = "Department1";
				break;
			case 12:
				strFieldName = "HeadShip1";
				break;
			case 13:
				strFieldName = "MobilePhone1";
				break;
			case 14:
				strFieldName = "Phone1";
				break;
			case 15:
				strFieldName = "Fax1";
				break;
			case 16:
				strFieldName = "EMail1";
				break;
			case 17:
				strFieldName = "MakeOperator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyOperator";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
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
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadShip1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MobilePhone1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail1") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
