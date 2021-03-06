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
import com.sinosoft.lis.db.LDHospitalDB;

/*
 * <p>ClassName: LDHospitalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LDHospitalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDHospitalSchema.class);

	// @Field
	/** 体检医院编码 */
	private String HospitCode;
	/** 体检医院名称 */
	private String HospitalName;
	/** 体检医院简称 */
	private String HospitShortName;
	/** 管理机构 */
	private String MngCom;
	/** 定点医院标记 */
	private String FixFlag;
	/** 医院地址 */
	private String Address;
	/** 医院邮编 */
	private String ZipCode;
	/** 医院电话 */
	private String Phone;
	/** 医院网址 */
	private String WebAddress;
	/** 医院传真 */
	private String Fax;
	/** 医院资格号 */
	private String HospitLicencNo;
	/** 开户银行 */
	private String bankCode;
	/** 账户名 */
	private String AccName;
	/** 账户编码 */
	private String bankAccNo;
	/** 医院状态 */
	private String HosState;
	/** 备注 */
	private String Remark;
	/** 联系人 */
	private String LinkMan;
	/** 签署日期 */
	private Date ValidityDate;
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
	/** 黑名单标记 */
	private String BlackListFlag;
	/** 原因类别 */
	private String ReasonType;
	/** 体检医院级别 */
	private String HospitalGrade;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDHospitalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "HospitCode";

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
		LDHospitalSchema cloned = (LDHospitalSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getHospitCode()
	{
		return HospitCode;
	}
	public void setHospitCode(String aHospitCode)
	{
		HospitCode = aHospitCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		HospitalName = aHospitalName;
	}
	public String getHospitShortName()
	{
		return HospitShortName;
	}
	public void setHospitShortName(String aHospitShortName)
	{
		HospitShortName = aHospitShortName;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
	}
	public String getFixFlag()
	{
		return FixFlag;
	}
	public void setFixFlag(String aFixFlag)
	{
		FixFlag = aFixFlag;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		Address = aAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		ZipCode = aZipCode;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getWebAddress()
	{
		return WebAddress;
	}
	public void setWebAddress(String aWebAddress)
	{
		WebAddress = aWebAddress;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		Fax = aFax;
	}
	public String getHospitLicencNo()
	{
		return HospitLicencNo;
	}
	public void setHospitLicencNo(String aHospitLicencNo)
	{
		HospitLicencNo = aHospitLicencNo;
	}
	public String getbankCode()
	{
		return bankCode;
	}
	public void setbankCode(String abankCode)
	{
		bankCode = abankCode;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public String getbankAccNo()
	{
		return bankAccNo;
	}
	public void setbankAccNo(String abankAccNo)
	{
		bankAccNo = abankAccNo;
	}
	/**
	* 0有效<p>
	* 1暂停<p>
	* 2终止
	*/
	public String getHosState()
	{
		return HosState;
	}
	public void setHosState(String aHosState)
	{
		HosState = aHosState;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		LinkMan = aLinkMan;
	}
	public String getValidityDate()
	{
		if( ValidityDate != null )
			return fDate.getString(ValidityDate);
		else
			return null;
	}
	public void setValidityDate(Date aValidityDate)
	{
		ValidityDate = aValidityDate;
	}
	public void setValidityDate(String aValidityDate)
	{
		if (aValidityDate != null && !aValidityDate.equals("") )
		{
			ValidityDate = fDate.getDate( aValidityDate );
		}
		else
			ValidityDate = null;
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
	public String getBlackListFlag()
	{
		return BlackListFlag;
	}
	public void setBlackListFlag(String aBlackListFlag)
	{
		BlackListFlag = aBlackListFlag;
	}
	public String getReasonType()
	{
		return ReasonType;
	}
	public void setReasonType(String aReasonType)
	{
		ReasonType = aReasonType;
	}
	public String getHospitalGrade()
	{
		return HospitalGrade;
	}
	public void setHospitalGrade(String aHospitalGrade)
	{
		HospitalGrade = aHospitalGrade;
	}

	/**
	* 使用另外一个 LDHospitalSchema 对象给 Schema 赋值
	* @param: aLDHospitalSchema LDHospitalSchema
	**/
	public void setSchema(LDHospitalSchema aLDHospitalSchema)
	{
		this.HospitCode = aLDHospitalSchema.getHospitCode();
		this.HospitalName = aLDHospitalSchema.getHospitalName();
		this.HospitShortName = aLDHospitalSchema.getHospitShortName();
		this.MngCom = aLDHospitalSchema.getMngCom();
		this.FixFlag = aLDHospitalSchema.getFixFlag();
		this.Address = aLDHospitalSchema.getAddress();
		this.ZipCode = aLDHospitalSchema.getZipCode();
		this.Phone = aLDHospitalSchema.getPhone();
		this.WebAddress = aLDHospitalSchema.getWebAddress();
		this.Fax = aLDHospitalSchema.getFax();
		this.HospitLicencNo = aLDHospitalSchema.getHospitLicencNo();
		this.bankCode = aLDHospitalSchema.getbankCode();
		this.AccName = aLDHospitalSchema.getAccName();
		this.bankAccNo = aLDHospitalSchema.getbankAccNo();
		this.HosState = aLDHospitalSchema.getHosState();
		this.Remark = aLDHospitalSchema.getRemark();
		this.LinkMan = aLDHospitalSchema.getLinkMan();
		this.ValidityDate = fDate.getDate( aLDHospitalSchema.getValidityDate());
		this.Operator = aLDHospitalSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDHospitalSchema.getMakeDate());
		this.MakeTime = aLDHospitalSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDHospitalSchema.getModifyDate());
		this.ModifyTime = aLDHospitalSchema.getModifyTime();
		this.BlackListFlag = aLDHospitalSchema.getBlackListFlag();
		this.ReasonType = aLDHospitalSchema.getReasonType();
		this.HospitalGrade = aLDHospitalSchema.getHospitalGrade();
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
			if( rs.getString("HospitCode") == null )
				this.HospitCode = null;
			else
				this.HospitCode = rs.getString("HospitCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			if( rs.getString("HospitShortName") == null )
				this.HospitShortName = null;
			else
				this.HospitShortName = rs.getString("HospitShortName").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			if( rs.getString("FixFlag") == null )
				this.FixFlag = null;
			else
				this.FixFlag = rs.getString("FixFlag").trim();

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("WebAddress") == null )
				this.WebAddress = null;
			else
				this.WebAddress = rs.getString("WebAddress").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("HospitLicencNo") == null )
				this.HospitLicencNo = null;
			else
				this.HospitLicencNo = rs.getString("HospitLicencNo").trim();

			if( rs.getString("bankCode") == null )
				this.bankCode = null;
			else
				this.bankCode = rs.getString("bankCode").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("bankAccNo") == null )
				this.bankAccNo = null;
			else
				this.bankAccNo = rs.getString("bankAccNo").trim();

			if( rs.getString("HosState") == null )
				this.HosState = null;
			else
				this.HosState = rs.getString("HosState").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			this.ValidityDate = rs.getDate("ValidityDate");
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

			if( rs.getString("BlackListFlag") == null )
				this.BlackListFlag = null;
			else
				this.BlackListFlag = rs.getString("BlackListFlag").trim();

			if( rs.getString("ReasonType") == null )
				this.ReasonType = null;
			else
				this.ReasonType = rs.getString("ReasonType").trim();

			if( rs.getString("HospitalGrade") == null )
				this.HospitalGrade = null;
			else
				this.HospitalGrade = rs.getString("HospitalGrade").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDHospital表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDHospitalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDHospitalSchema getSchema()
	{
		LDHospitalSchema aLDHospitalSchema = new LDHospitalSchema();
		aLDHospitalSchema.setSchema(this);
		return aLDHospitalSchema;
	}

	public LDHospitalDB getDB()
	{
		LDHospitalDB aDBOper = new LDHospitalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDHospital描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(HospitCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitShortName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FixFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WebAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitLicencNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(bankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(bankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValidityDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlackListFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalGrade));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDHospital>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			HospitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			HospitShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FixFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			WebAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			HospitLicencNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			bankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			bankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			HosState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ValidityDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BlackListFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ReasonType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			HospitalGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDHospitalSchema";
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
		if (FCode.equalsIgnoreCase("HospitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("HospitShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitShortName));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("FixFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FixFlag));
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("WebAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAddress));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("HospitLicencNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitLicencNo));
		}
		if (FCode.equalsIgnoreCase("bankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bankCode));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("bankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bankAccNo));
		}
		if (FCode.equalsIgnoreCase("HosState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosState));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("ValidityDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidityDate()));
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
		if (FCode.equalsIgnoreCase("BlackListFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlackListFlag));
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonType));
		}
		if (FCode.equalsIgnoreCase("HospitalGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalGrade));
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
				strFieldValue = StrTool.GBKToUnicode(HospitCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(HospitShortName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FixFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(WebAddress);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(HospitLicencNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(bankCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(bankAccNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(HosState);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidityDate()));
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
				strFieldValue = StrTool.GBKToUnicode(BlackListFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ReasonType);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(HospitalGrade);
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

		if (FCode.equalsIgnoreCase("HospitCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitCode = FValue.trim();
			}
			else
				HospitCode = null;
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
		if (FCode.equalsIgnoreCase("HospitShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitShortName = FValue.trim();
			}
			else
				HospitShortName = null;
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
		if (FCode.equalsIgnoreCase("FixFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FixFlag = FValue.trim();
			}
			else
				FixFlag = null;
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
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("WebAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAddress = FValue.trim();
			}
			else
				WebAddress = null;
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
		if (FCode.equalsIgnoreCase("HospitLicencNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitLicencNo = FValue.trim();
			}
			else
				HospitLicencNo = null;
		}
		if (FCode.equalsIgnoreCase("bankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bankCode = FValue.trim();
			}
			else
				bankCode = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("bankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bankAccNo = FValue.trim();
			}
			else
				bankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("HosState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosState = FValue.trim();
			}
			else
				HosState = null;
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
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan = FValue.trim();
			}
			else
				LinkMan = null;
		}
		if (FCode.equalsIgnoreCase("ValidityDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidityDate = fDate.getDate( FValue );
			}
			else
				ValidityDate = null;
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
		if (FCode.equalsIgnoreCase("BlackListFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlackListFlag = FValue.trim();
			}
			else
				BlackListFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonType = FValue.trim();
			}
			else
				ReasonType = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDHospitalSchema other = (LDHospitalSchema)otherObject;
		return
			HospitCode.equals(other.getHospitCode())
			&& HospitalName.equals(other.getHospitalName())
			&& HospitShortName.equals(other.getHospitShortName())
			&& MngCom.equals(other.getMngCom())
			&& FixFlag.equals(other.getFixFlag())
			&& Address.equals(other.getAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& WebAddress.equals(other.getWebAddress())
			&& Fax.equals(other.getFax())
			&& HospitLicencNo.equals(other.getHospitLicencNo())
			&& bankCode.equals(other.getbankCode())
			&& AccName.equals(other.getAccName())
			&& bankAccNo.equals(other.getbankAccNo())
			&& HosState.equals(other.getHosState())
			&& Remark.equals(other.getRemark())
			&& LinkMan.equals(other.getLinkMan())
			&& fDate.getString(ValidityDate).equals(other.getValidityDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BlackListFlag.equals(other.getBlackListFlag())
			&& ReasonType.equals(other.getReasonType())
			&& HospitalGrade.equals(other.getHospitalGrade());
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
		if( strFieldName.equals("HospitCode") ) {
			return 0;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 1;
		}
		if( strFieldName.equals("HospitShortName") ) {
			return 2;
		}
		if( strFieldName.equals("MngCom") ) {
			return 3;
		}
		if( strFieldName.equals("FixFlag") ) {
			return 4;
		}
		if( strFieldName.equals("Address") ) {
			return 5;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 6;
		}
		if( strFieldName.equals("Phone") ) {
			return 7;
		}
		if( strFieldName.equals("WebAddress") ) {
			return 8;
		}
		if( strFieldName.equals("Fax") ) {
			return 9;
		}
		if( strFieldName.equals("HospitLicencNo") ) {
			return 10;
		}
		if( strFieldName.equals("bankCode") ) {
			return 11;
		}
		if( strFieldName.equals("AccName") ) {
			return 12;
		}
		if( strFieldName.equals("bankAccNo") ) {
			return 13;
		}
		if( strFieldName.equals("HosState") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 16;
		}
		if( strFieldName.equals("ValidityDate") ) {
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
		if( strFieldName.equals("BlackListFlag") ) {
			return 23;
		}
		if( strFieldName.equals("ReasonType") ) {
			return 24;
		}
		if( strFieldName.equals("HospitalGrade") ) {
			return 25;
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
				strFieldName = "HospitCode";
				break;
			case 1:
				strFieldName = "HospitalName";
				break;
			case 2:
				strFieldName = "HospitShortName";
				break;
			case 3:
				strFieldName = "MngCom";
				break;
			case 4:
				strFieldName = "FixFlag";
				break;
			case 5:
				strFieldName = "Address";
				break;
			case 6:
				strFieldName = "ZipCode";
				break;
			case 7:
				strFieldName = "Phone";
				break;
			case 8:
				strFieldName = "WebAddress";
				break;
			case 9:
				strFieldName = "Fax";
				break;
			case 10:
				strFieldName = "HospitLicencNo";
				break;
			case 11:
				strFieldName = "bankCode";
				break;
			case 12:
				strFieldName = "AccName";
				break;
			case 13:
				strFieldName = "bankAccNo";
				break;
			case 14:
				strFieldName = "HosState";
				break;
			case 15:
				strFieldName = "Remark";
				break;
			case 16:
				strFieldName = "LinkMan";
				break;
			case 17:
				strFieldName = "ValidityDate";
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
				strFieldName = "BlackListFlag";
				break;
			case 24:
				strFieldName = "ReasonType";
				break;
			case 25:
				strFieldName = "HospitalGrade";
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
		if( strFieldName.equals("HospitCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FixFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitLicencNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidityDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("BlackListFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalGrade") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
