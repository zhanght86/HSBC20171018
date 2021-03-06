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
import com.sinosoft.lis.db.LPGrpAddressDB;

/*
 * <p>ClassName: LPGrpAddressSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPGrpAddressSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGrpAddressSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 客户号码 */
	private String CustomerNo;
	/** 地址号码 */
	private String AddressNo;
	/** 单位地址 */
	private String GrpAddress;
	/** 单位邮编 */
	private String GrpZipCode;
	/** 联系人1 */
	private String LinkMan1;
	/** 部门1 */
	private String Department1;
	/** 职务1 */
	private String HeadShip1;
	/** 联系电话1 */
	private String Phone1;
	/** E_mail1 */
	private String E_Mail1;
	/** 传真1 */
	private String Fax1;
	/** 联系人2 */
	private String LinkMan2;
	/** 部门2 */
	private String Department2;
	/** 职务2 */
	private String HeadShip2;
	/** 联系电话2 */
	private String Phone2;
	/** E_mail2 */
	private String E_Mail2;
	/** 传真2 */
	private String Fax2;
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
	/** 手机1 */
	private String MobilePhone1;
	/** 手机2 */
	private String MobilePhone2;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGrpAddressSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "CustomerNo";
		pk[3] = "AddressNo";

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
		LPGrpAddressSchema cloned = (LPGrpAddressSchema)super.clone();
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
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getAddressNo()
	{
		return AddressNo;
	}
	public void setAddressNo(String aAddressNo)
	{
		AddressNo = aAddressNo;
	}
	public String getGrpAddress()
	{
		return GrpAddress;
	}
	public void setGrpAddress(String aGrpAddress)
	{
		GrpAddress = aGrpAddress;
	}
	public String getGrpZipCode()
	{
		return GrpZipCode;
	}
	public void setGrpZipCode(String aGrpZipCode)
	{
		GrpZipCode = aGrpZipCode;
	}
	public String getLinkMan1()
	{
		return LinkMan1;
	}
	public void setLinkMan1(String aLinkMan1)
	{
		LinkMan1 = aLinkMan1;
	}
	public String getDepartment1()
	{
		return Department1;
	}
	public void setDepartment1(String aDepartment1)
	{
		Department1 = aDepartment1;
	}
	public String getHeadShip1()
	{
		return HeadShip1;
	}
	public void setHeadShip1(String aHeadShip1)
	{
		HeadShip1 = aHeadShip1;
	}
	public String getPhone1()
	{
		return Phone1;
	}
	public void setPhone1(String aPhone1)
	{
		Phone1 = aPhone1;
	}
	public String getE_Mail1()
	{
		return E_Mail1;
	}
	public void setE_Mail1(String aE_Mail1)
	{
		E_Mail1 = aE_Mail1;
	}
	public String getFax1()
	{
		return Fax1;
	}
	public void setFax1(String aFax1)
	{
		Fax1 = aFax1;
	}
	public String getLinkMan2()
	{
		return LinkMan2;
	}
	public void setLinkMan2(String aLinkMan2)
	{
		LinkMan2 = aLinkMan2;
	}
	public String getDepartment2()
	{
		return Department2;
	}
	public void setDepartment2(String aDepartment2)
	{
		Department2 = aDepartment2;
	}
	public String getHeadShip2()
	{
		return HeadShip2;
	}
	public void setHeadShip2(String aHeadShip2)
	{
		HeadShip2 = aHeadShip2;
	}
	public String getPhone2()
	{
		return Phone2;
	}
	public void setPhone2(String aPhone2)
	{
		Phone2 = aPhone2;
	}
	public String getE_Mail2()
	{
		return E_Mail2;
	}
	public void setE_Mail2(String aE_Mail2)
	{
		E_Mail2 = aE_Mail2;
	}
	public String getFax2()
	{
		return Fax2;
	}
	public void setFax2(String aFax2)
	{
		Fax2 = aFax2;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
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
		ModifyTime = aModifyTime;
	}
	public String getMobilePhone1()
	{
		return MobilePhone1;
	}
	public void setMobilePhone1(String aMobilePhone1)
	{
		MobilePhone1 = aMobilePhone1;
	}
	public String getMobilePhone2()
	{
		return MobilePhone2;
	}
	public void setMobilePhone2(String aMobilePhone2)
	{
		MobilePhone2 = aMobilePhone2;
	}

	/**
	* 使用另外一个 LPGrpAddressSchema 对象给 Schema 赋值
	* @param: aLPGrpAddressSchema LPGrpAddressSchema
	**/
	public void setSchema(LPGrpAddressSchema aLPGrpAddressSchema)
	{
		this.EdorNo = aLPGrpAddressSchema.getEdorNo();
		this.EdorType = aLPGrpAddressSchema.getEdorType();
		this.CustomerNo = aLPGrpAddressSchema.getCustomerNo();
		this.AddressNo = aLPGrpAddressSchema.getAddressNo();
		this.GrpAddress = aLPGrpAddressSchema.getGrpAddress();
		this.GrpZipCode = aLPGrpAddressSchema.getGrpZipCode();
		this.LinkMan1 = aLPGrpAddressSchema.getLinkMan1();
		this.Department1 = aLPGrpAddressSchema.getDepartment1();
		this.HeadShip1 = aLPGrpAddressSchema.getHeadShip1();
		this.Phone1 = aLPGrpAddressSchema.getPhone1();
		this.E_Mail1 = aLPGrpAddressSchema.getE_Mail1();
		this.Fax1 = aLPGrpAddressSchema.getFax1();
		this.LinkMan2 = aLPGrpAddressSchema.getLinkMan2();
		this.Department2 = aLPGrpAddressSchema.getDepartment2();
		this.HeadShip2 = aLPGrpAddressSchema.getHeadShip2();
		this.Phone2 = aLPGrpAddressSchema.getPhone2();
		this.E_Mail2 = aLPGrpAddressSchema.getE_Mail2();
		this.Fax2 = aLPGrpAddressSchema.getFax2();
		this.Operator = aLPGrpAddressSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPGrpAddressSchema.getMakeDate());
		this.MakeTime = aLPGrpAddressSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPGrpAddressSchema.getModifyDate());
		this.ModifyTime = aLPGrpAddressSchema.getModifyTime();
		this.MobilePhone1 = aLPGrpAddressSchema.getMobilePhone1();
		this.MobilePhone2 = aLPGrpAddressSchema.getMobilePhone2();
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

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("GrpAddress") == null )
				this.GrpAddress = null;
			else
				this.GrpAddress = rs.getString("GrpAddress").trim();

			if( rs.getString("GrpZipCode") == null )
				this.GrpZipCode = null;
			else
				this.GrpZipCode = rs.getString("GrpZipCode").trim();

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

			if( rs.getString("Phone1") == null )
				this.Phone1 = null;
			else
				this.Phone1 = rs.getString("Phone1").trim();

			if( rs.getString("E_Mail1") == null )
				this.E_Mail1 = null;
			else
				this.E_Mail1 = rs.getString("E_Mail1").trim();

			if( rs.getString("Fax1") == null )
				this.Fax1 = null;
			else
				this.Fax1 = rs.getString("Fax1").trim();

			if( rs.getString("LinkMan2") == null )
				this.LinkMan2 = null;
			else
				this.LinkMan2 = rs.getString("LinkMan2").trim();

			if( rs.getString("Department2") == null )
				this.Department2 = null;
			else
				this.Department2 = rs.getString("Department2").trim();

			if( rs.getString("HeadShip2") == null )
				this.HeadShip2 = null;
			else
				this.HeadShip2 = rs.getString("HeadShip2").trim();

			if( rs.getString("Phone2") == null )
				this.Phone2 = null;
			else
				this.Phone2 = rs.getString("Phone2").trim();

			if( rs.getString("E_Mail2") == null )
				this.E_Mail2 = null;
			else
				this.E_Mail2 = rs.getString("E_Mail2").trim();

			if( rs.getString("Fax2") == null )
				this.Fax2 = null;
			else
				this.Fax2 = rs.getString("Fax2").trim();

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

			if( rs.getString("MobilePhone1") == null )
				this.MobilePhone1 = null;
			else
				this.MobilePhone1 = rs.getString("MobilePhone1").trim();

			if( rs.getString("MobilePhone2") == null )
				this.MobilePhone2 = null;
			else
				this.MobilePhone2 = rs.getString("MobilePhone2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPGrpAddress表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpAddressSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGrpAddressSchema getSchema()
	{
		LPGrpAddressSchema aLPGrpAddressSchema = new LPGrpAddressSchema();
		aLPGrpAddressSchema.setSchema(this);
		return aLPGrpAddressSchema;
	}

	public LPGrpAddressDB getDB()
	{
		LPGrpAddressDB aDBOper = new LPGrpAddressDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpAddress描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(E_Mail1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(E_Mail2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MobilePhone1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MobilePhone2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpAddress>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LinkMan1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Department1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HeadShip1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Phone1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			E_Mail1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Fax1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			LinkMan2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Department2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			HeadShip2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Phone2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			E_Mail2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Fax2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MobilePhone1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MobilePhone2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpAddressSchema";
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddress));
		}
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpZipCode));
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
		if (FCode.equalsIgnoreCase("Phone1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone1));
		}
		if (FCode.equalsIgnoreCase("E_Mail1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(E_Mail1));
		}
		if (FCode.equalsIgnoreCase("Fax1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax1));
		}
		if (FCode.equalsIgnoreCase("LinkMan2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan2));
		}
		if (FCode.equalsIgnoreCase("Department2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department2));
		}
		if (FCode.equalsIgnoreCase("HeadShip2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadShip2));
		}
		if (FCode.equalsIgnoreCase("Phone2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone2));
		}
		if (FCode.equalsIgnoreCase("E_Mail2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(E_Mail2));
		}
		if (FCode.equalsIgnoreCase("Fax2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax2));
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
		if (FCode.equalsIgnoreCase("MobilePhone1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobilePhone1));
		}
		if (FCode.equalsIgnoreCase("MobilePhone2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobilePhone2));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpAddress);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpZipCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LinkMan1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Department1);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HeadShip1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Phone1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(E_Mail1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Fax1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(LinkMan2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Department2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(HeadShip2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Phone2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(E_Mail2);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Fax2);
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
				strFieldValue = StrTool.GBKToUnicode(MobilePhone1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MobilePhone2);
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddressNo = FValue.trim();
			}
			else
				AddressNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpAddress = FValue.trim();
			}
			else
				GrpAddress = null;
		}
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpZipCode = FValue.trim();
			}
			else
				GrpZipCode = null;
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
		if (FCode.equalsIgnoreCase("Phone1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone1 = FValue.trim();
			}
			else
				Phone1 = null;
		}
		if (FCode.equalsIgnoreCase("E_Mail1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				E_Mail1 = FValue.trim();
			}
			else
				E_Mail1 = null;
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
		if (FCode.equalsIgnoreCase("LinkMan2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan2 = FValue.trim();
			}
			else
				LinkMan2 = null;
		}
		if (FCode.equalsIgnoreCase("Department2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Department2 = FValue.trim();
			}
			else
				Department2 = null;
		}
		if (FCode.equalsIgnoreCase("HeadShip2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadShip2 = FValue.trim();
			}
			else
				HeadShip2 = null;
		}
		if (FCode.equalsIgnoreCase("Phone2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone2 = FValue.trim();
			}
			else
				Phone2 = null;
		}
		if (FCode.equalsIgnoreCase("E_Mail2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				E_Mail2 = FValue.trim();
			}
			else
				E_Mail2 = null;
		}
		if (FCode.equalsIgnoreCase("Fax2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax2 = FValue.trim();
			}
			else
				Fax2 = null;
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
		if (FCode.equalsIgnoreCase("MobilePhone1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobilePhone1 = FValue.trim();
			}
			else
				MobilePhone1 = null;
		}
		if (FCode.equalsIgnoreCase("MobilePhone2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobilePhone2 = FValue.trim();
			}
			else
				MobilePhone2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPGrpAddressSchema other = (LPGrpAddressSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AddressNo.equals(other.getAddressNo())
			&& GrpAddress.equals(other.getGrpAddress())
			&& GrpZipCode.equals(other.getGrpZipCode())
			&& LinkMan1.equals(other.getLinkMan1())
			&& Department1.equals(other.getDepartment1())
			&& HeadShip1.equals(other.getHeadShip1())
			&& Phone1.equals(other.getPhone1())
			&& E_Mail1.equals(other.getE_Mail1())
			&& Fax1.equals(other.getFax1())
			&& LinkMan2.equals(other.getLinkMan2())
			&& Department2.equals(other.getDepartment2())
			&& HeadShip2.equals(other.getHeadShip2())
			&& Phone2.equals(other.getPhone2())
			&& E_Mail2.equals(other.getE_Mail2())
			&& Fax2.equals(other.getFax2())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& MobilePhone1.equals(other.getMobilePhone1())
			&& MobilePhone2.equals(other.getMobilePhone2());
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
		if( strFieldName.equals("CustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return 4;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return 5;
		}
		if( strFieldName.equals("LinkMan1") ) {
			return 6;
		}
		if( strFieldName.equals("Department1") ) {
			return 7;
		}
		if( strFieldName.equals("HeadShip1") ) {
			return 8;
		}
		if( strFieldName.equals("Phone1") ) {
			return 9;
		}
		if( strFieldName.equals("E_Mail1") ) {
			return 10;
		}
		if( strFieldName.equals("Fax1") ) {
			return 11;
		}
		if( strFieldName.equals("LinkMan2") ) {
			return 12;
		}
		if( strFieldName.equals("Department2") ) {
			return 13;
		}
		if( strFieldName.equals("HeadShip2") ) {
			return 14;
		}
		if( strFieldName.equals("Phone2") ) {
			return 15;
		}
		if( strFieldName.equals("E_Mail2") ) {
			return 16;
		}
		if( strFieldName.equals("Fax2") ) {
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
		if( strFieldName.equals("MobilePhone1") ) {
			return 23;
		}
		if( strFieldName.equals("MobilePhone2") ) {
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "AddressNo";
				break;
			case 4:
				strFieldName = "GrpAddress";
				break;
			case 5:
				strFieldName = "GrpZipCode";
				break;
			case 6:
				strFieldName = "LinkMan1";
				break;
			case 7:
				strFieldName = "Department1";
				break;
			case 8:
				strFieldName = "HeadShip1";
				break;
			case 9:
				strFieldName = "Phone1";
				break;
			case 10:
				strFieldName = "E_Mail1";
				break;
			case 11:
				strFieldName = "Fax1";
				break;
			case 12:
				strFieldName = "LinkMan2";
				break;
			case 13:
				strFieldName = "Department2";
				break;
			case 14:
				strFieldName = "HeadShip2";
				break;
			case 15:
				strFieldName = "Phone2";
				break;
			case 16:
				strFieldName = "E_Mail2";
				break;
			case 17:
				strFieldName = "Fax2";
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
				strFieldName = "MobilePhone1";
				break;
			case 24:
				strFieldName = "MobilePhone2";
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpZipCode") ) {
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
		if( strFieldName.equals("Phone1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("E_Mail1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadShip2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("E_Mail2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax2") ) {
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
		if( strFieldName.equals("MobilePhone1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MobilePhone2") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
