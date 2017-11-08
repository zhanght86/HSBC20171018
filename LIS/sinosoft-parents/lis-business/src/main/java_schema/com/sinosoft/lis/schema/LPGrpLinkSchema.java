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
import com.sinosoft.lis.db.LPGrpLinkDB;

/*
 * <p>ClassName: LPGrpLinkSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPGrpLinkSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGrpLinkSchema.class);
	// @Field
	/** 保全批单号 */
	private String EdorNo;
	/** 保全项目 */
	private String EdorType;
	/** 团体保单号 */
	private String GrpContNo;
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 联系号 */
	private int ContactNo;
	/** 客户号码 */
	private String CustomerNo;
	/** 联系人标识 */
	private String LinkManFlag;
	/** 联系人姓名 */
	private String LinkMan;
	/** 部门 */
	private String Department;
	/** 职务 */
	private String HeadShip;
	/** 手机 */
	private String MobilePhone;
	/** 电话 */
	private String Phone;
	/** 传真 */
	private String Fax;
	/** Email */
	private String EMail;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 证件开始日期 */
	private Date IDStartDate;
	/** 证件结束日期 */
	private Date IDEndDate;
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGrpLinkSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "GrpContNo";
		pk[3] = "ContactNo";
		pk[4] = "CustomerNo";

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
		LPGrpLinkSchema cloned = (LPGrpLinkSchema)super.clone();
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
	/**
	* 联系信息号
	*/
	public int getContactNo()
	{
		return ContactNo;
	}
	public void setContactNo(int aContactNo)
	{
		ContactNo = aContactNo;
	}
	public void setContactNo(String aContactNo)
	{
		if (aContactNo != null && !aContactNo.equals(""))
		{
			Integer tInteger = new Integer(aContactNo);
			int i = tInteger.intValue();
			ContactNo = i;
		}
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
	/**
	* 00-主联系人<p>
	* 01-附属联系人
	*/
	public String getLinkManFlag()
	{
		return LinkManFlag;
	}
	public void setLinkManFlag(String aLinkManFlag)
	{
		if(aLinkManFlag!=null && aLinkManFlag.length()>2)
			throw new IllegalArgumentException("联系人标识LinkManFlag值"+aLinkManFlag+"的长度"+aLinkManFlag.length()+"大于最大值2");
		LinkManFlag = aLinkManFlag;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		if(aLinkMan!=null && aLinkMan.length()>200)
			throw new IllegalArgumentException("联系人姓名LinkMan值"+aLinkMan+"的长度"+aLinkMan.length()+"大于最大值200");
		LinkMan = aLinkMan;
	}
	public String getDepartment()
	{
		return Department;
	}
	public void setDepartment(String aDepartment)
	{
		if(aDepartment!=null && aDepartment.length()>200)
			throw new IllegalArgumentException("部门Department值"+aDepartment+"的长度"+aDepartment.length()+"大于最大值200");
		Department = aDepartment;
	}
	public String getHeadShip()
	{
		return HeadShip;
	}
	public void setHeadShip(String aHeadShip)
	{
		if(aHeadShip!=null && aHeadShip.length()>100)
			throw new IllegalArgumentException("职务HeadShip值"+aHeadShip+"的长度"+aHeadShip.length()+"大于最大值100");
		HeadShip = aHeadShip;
	}
	public String getMobilePhone()
	{
		return MobilePhone;
	}
	public void setMobilePhone(String aMobilePhone)
	{
		if(aMobilePhone!=null && aMobilePhone.length()>20)
			throw new IllegalArgumentException("手机MobilePhone值"+aMobilePhone+"的长度"+aMobilePhone.length()+"大于最大值20");
		MobilePhone = aMobilePhone;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>20)
			throw new IllegalArgumentException("电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值20");
		Phone = aPhone;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>20)
			throw new IllegalArgumentException("传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值20");
		Fax = aFax;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>60)
			throw new IllegalArgumentException("EmailEMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值60");
		EMail = aEMail;
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
	/**
	* 证件签发日期
	*/
	public String getIDStartDate()
	{
		if( IDStartDate != null )
			return fDate.getString(IDStartDate);
		else
			return null;
	}
	public void setIDStartDate(Date aIDStartDate)
	{
		IDStartDate = aIDStartDate;
	}
	public void setIDStartDate(String aIDStartDate)
	{
		if (aIDStartDate != null && !aIDStartDate.equals("") )
		{
			IDStartDate = fDate.getDate( aIDStartDate );
		}
		else
			IDStartDate = null;
	}

	public String getIDEndDate()
	{
		if( IDEndDate != null )
			return fDate.getString(IDEndDate);
		else
			return null;
	}
	public void setIDEndDate(Date aIDEndDate)
	{
		IDEndDate = aIDEndDate;
	}
	public void setIDEndDate(String aIDEndDate)
	{
		if (aIDEndDate != null && !aIDEndDate.equals("") )
		{
			IDEndDate = fDate.getDate( aIDEndDate );
		}
		else
			IDEndDate = null;
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
	* 使用另外一个 LPGrpLinkSchema 对象给 Schema 赋值
	* @param: aLPGrpLinkSchema LPGrpLinkSchema
	**/
	public void setSchema(LPGrpLinkSchema aLPGrpLinkSchema)
	{
		this.EdorNo = aLPGrpLinkSchema.getEdorNo();
		this.EdorType = aLPGrpLinkSchema.getEdorType();
		this.GrpContNo = aLPGrpLinkSchema.getGrpContNo();
		this.GrpPropNo = aLPGrpLinkSchema.getGrpPropNo();
		this.ContactNo = aLPGrpLinkSchema.getContactNo();
		this.CustomerNo = aLPGrpLinkSchema.getCustomerNo();
		this.LinkManFlag = aLPGrpLinkSchema.getLinkManFlag();
		this.LinkMan = aLPGrpLinkSchema.getLinkMan();
		this.Department = aLPGrpLinkSchema.getDepartment();
		this.HeadShip = aLPGrpLinkSchema.getHeadShip();
		this.MobilePhone = aLPGrpLinkSchema.getMobilePhone();
		this.Phone = aLPGrpLinkSchema.getPhone();
		this.Fax = aLPGrpLinkSchema.getFax();
		this.EMail = aLPGrpLinkSchema.getEMail();
		this.IDType = aLPGrpLinkSchema.getIDType();
		this.IDNo = aLPGrpLinkSchema.getIDNo();
		this.IDStartDate = fDate.getDate( aLPGrpLinkSchema.getIDStartDate());
		this.IDEndDate = fDate.getDate( aLPGrpLinkSchema.getIDEndDate());
		this.MakeOperator = aLPGrpLinkSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPGrpLinkSchema.getMakeDate());
		this.MakeTime = aLPGrpLinkSchema.getMakeTime();
		this.ModifyOperator = aLPGrpLinkSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPGrpLinkSchema.getModifyDate());
		this.ModifyTime = aLPGrpLinkSchema.getModifyTime();
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

			this.ContactNo = rs.getInt("ContactNo");
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("LinkManFlag") == null )
				this.LinkManFlag = null;
			else
				this.LinkManFlag = rs.getString("LinkManFlag").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			if( rs.getString("Department") == null )
				this.Department = null;
			else
				this.Department = rs.getString("Department").trim();

			if( rs.getString("HeadShip") == null )
				this.HeadShip = null;
			else
				this.HeadShip = rs.getString("HeadShip").trim();

			if( rs.getString("MobilePhone") == null )
				this.MobilePhone = null;
			else
				this.MobilePhone = rs.getString("MobilePhone").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.IDStartDate = rs.getDate("IDStartDate");
			this.IDEndDate = rs.getDate("IDEndDate");
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
			logger.debug("数据库中的LPGrpLink表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpLinkSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGrpLinkSchema getSchema()
	{
		LPGrpLinkSchema aLPGrpLinkSchema = new LPGrpLinkSchema();
		aLPGrpLinkSchema.setSchema(this);
		return aLPGrpLinkSchema;
	}

	public LPGrpLinkDB getDB()
	{
		LPGrpLinkDB aDBOper = new LPGrpLinkDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpLink描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ContactNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkManFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MobilePhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpLink>历史记账凭证主表信息</A>表字段
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
			ContactNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LinkManFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Department = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HeadShip = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MobilePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			IDStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			IDEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpLinkSchema";
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
		if (FCode.equalsIgnoreCase("ContactNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContactNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("LinkManFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkManFlag));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("Department"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department));
		}
		if (FCode.equalsIgnoreCase("HeadShip"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadShip));
		}
		if (FCode.equalsIgnoreCase("MobilePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobilePhone));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("IDStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDStartDate()));
		}
		if (FCode.equalsIgnoreCase("IDEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDEndDate()));
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
				strFieldValue = String.valueOf(ContactNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LinkManFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Department);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HeadShip);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MobilePhone);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDStartDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDEndDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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
		if (FCode.equalsIgnoreCase("ContactNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ContactNo = i;
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
		if (FCode.equalsIgnoreCase("LinkManFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkManFlag = FValue.trim();
			}
			else
				LinkManFlag = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan = FValue.trim();
			}
			else
				LinkMan = null;
		}
		if (FCode.equalsIgnoreCase("Department"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Department = FValue.trim();
			}
			else
				Department = null;
		}
		if (FCode.equalsIgnoreCase("HeadShip"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadShip = FValue.trim();
			}
			else
				HeadShip = null;
		}
		if (FCode.equalsIgnoreCase("MobilePhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobilePhone = FValue.trim();
			}
			else
				MobilePhone = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax = FValue.trim();
			}
			else
				Fax = null;
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail = FValue.trim();
			}
			else
				EMail = null;
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
		if (FCode.equalsIgnoreCase("IDStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDStartDate = fDate.getDate( FValue );
			}
			else
				IDStartDate = null;
		}
		if (FCode.equalsIgnoreCase("IDEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDEndDate = fDate.getDate( FValue );
			}
			else
				IDEndDate = null;
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
		LPGrpLinkSchema other = (LPGrpLinkSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& ContactNo == other.getContactNo()
			&& CustomerNo.equals(other.getCustomerNo())
			&& LinkManFlag.equals(other.getLinkManFlag())
			&& LinkMan.equals(other.getLinkMan())
			&& Department.equals(other.getDepartment())
			&& HeadShip.equals(other.getHeadShip())
			&& MobilePhone.equals(other.getMobilePhone())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& EMail.equals(other.getEMail())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& fDate.getString(IDStartDate).equals(other.getIDStartDate())
			&& fDate.getString(IDEndDate).equals(other.getIDEndDate())
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
		if( strFieldName.equals("ContactNo") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 5;
		}
		if( strFieldName.equals("LinkManFlag") ) {
			return 6;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 7;
		}
		if( strFieldName.equals("Department") ) {
			return 8;
		}
		if( strFieldName.equals("HeadShip") ) {
			return 9;
		}
		if( strFieldName.equals("MobilePhone") ) {
			return 10;
		}
		if( strFieldName.equals("Phone") ) {
			return 11;
		}
		if( strFieldName.equals("Fax") ) {
			return 12;
		}
		if( strFieldName.equals("EMail") ) {
			return 13;
		}
		if( strFieldName.equals("IDType") ) {
			return 14;
		}
		if( strFieldName.equals("IDNo") ) {
			return 15;
		}
		if( strFieldName.equals("IDStartDate") ) {
			return 16;
		}
		if( strFieldName.equals("IDEndDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
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
				strFieldName = "ContactNo";
				break;
			case 5:
				strFieldName = "CustomerNo";
				break;
			case 6:
				strFieldName = "LinkManFlag";
				break;
			case 7:
				strFieldName = "LinkMan";
				break;
			case 8:
				strFieldName = "Department";
				break;
			case 9:
				strFieldName = "HeadShip";
				break;
			case 10:
				strFieldName = "MobilePhone";
				break;
			case 11:
				strFieldName = "Phone";
				break;
			case 12:
				strFieldName = "Fax";
				break;
			case 13:
				strFieldName = "EMail";
				break;
			case 14:
				strFieldName = "IDType";
				break;
			case 15:
				strFieldName = "IDNo";
				break;
			case 16:
				strFieldName = "IDStartDate";
				break;
			case 17:
				strFieldName = "IDEndDate";
				break;
			case 18:
				strFieldName = "MakeOperator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyOperator";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
		if( strFieldName.equals("ContactNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkManFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadShip") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MobilePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDEndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
