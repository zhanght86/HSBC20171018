

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIComInfoDB;

/*
 * <p>ClassName: RIComInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIComInfoSchema implements Schema, Cloneable
{
	// @Field
	/** 再保公司编码 */
	private String ComPanyNo;
	/** 公司名称 */
	private String ComPanyName;
	/** 公司地址 */
	private String GrpAddress;
	/** 公司邮编 */
	private String GrpZipCode;
	/** 传真 */
	private String Fax;
	/** 公司网址 */
	private String HomePage;
	/** 联系人 */
	private String LinkMan;
	/** 联系人电话 */
	private String Phone;
	/** 联系人邮箱 */
	private String EMail;
	/** 公司类型 */
	private String Remark;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 备注 */
	private String Information;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIComInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ComPanyNo";

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
		RIComInfoSchema cloned = (RIComInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getComPanyNo()
	{
		return ComPanyNo;
	}
	public void setComPanyNo(String aComPanyNo)
	{
		ComPanyNo = aComPanyNo;
	}
	public String getComPanyName()
	{
		return ComPanyName;
	}
	public void setComPanyName(String aComPanyName)
	{
		ComPanyName = aComPanyName;
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
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		Fax = aFax;
	}
	public String getHomePage()
	{
		return HomePage;
	}
	public void setHomePage(String aHomePage)
	{
		HomePage = aHomePage;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		LinkMan = aLinkMan;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		EMail = aEMail;
	}
	/**
	* 01 - 分出公司<p>
	* 02 - 分入公司
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	public String getInformation()
	{
		return Information;
	}
	public void setInformation(String aInformation)
	{
		Information = aInformation;
	}

	/**
	* 使用另外一个 RIComInfoSchema 对象给 Schema 赋值
	* @param: aRIComInfoSchema RIComInfoSchema
	**/
	public void setSchema(RIComInfoSchema aRIComInfoSchema)
	{
		this.ComPanyNo = aRIComInfoSchema.getComPanyNo();
		this.ComPanyName = aRIComInfoSchema.getComPanyName();
		this.GrpAddress = aRIComInfoSchema.getGrpAddress();
		this.GrpZipCode = aRIComInfoSchema.getGrpZipCode();
		this.Fax = aRIComInfoSchema.getFax();
		this.HomePage = aRIComInfoSchema.getHomePage();
		this.LinkMan = aRIComInfoSchema.getLinkMan();
		this.Phone = aRIComInfoSchema.getPhone();
		this.EMail = aRIComInfoSchema.getEMail();
		this.Remark = aRIComInfoSchema.getRemark();
		this.Operator = aRIComInfoSchema.getOperator();
		this.MakeDate = fDate.getDate( aRIComInfoSchema.getMakeDate());
		this.MakeTime = aRIComInfoSchema.getMakeTime();
		this.Information = aRIComInfoSchema.getInformation();
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
			if( rs.getString("ComPanyNo") == null )
				this.ComPanyNo = null;
			else
				this.ComPanyNo = rs.getString("ComPanyNo").trim();

			if( rs.getString("ComPanyName") == null )
				this.ComPanyName = null;
			else
				this.ComPanyName = rs.getString("ComPanyName").trim();

			if( rs.getString("GrpAddress") == null )
				this.GrpAddress = null;
			else
				this.GrpAddress = rs.getString("GrpAddress").trim();

			if( rs.getString("GrpZipCode") == null )
				this.GrpZipCode = null;
			else
				this.GrpZipCode = rs.getString("GrpZipCode").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("HomePage") == null )
				this.HomePage = null;
			else
				this.HomePage = rs.getString("HomePage").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Information") == null )
				this.Information = null;
			else
				this.Information = rs.getString("Information").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIComInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIComInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIComInfoSchema getSchema()
	{
		RIComInfoSchema aRIComInfoSchema = new RIComInfoSchema();
		aRIComInfoSchema.setSchema(this);
		return aRIComInfoSchema;
	}

	public RIComInfoDB getDB()
	{
		RIComInfoDB aDBOper = new RIComInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIComInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ComPanyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComPanyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomePage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Information));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIComInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ComPanyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ComPanyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			HomePage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Information = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIComInfoSchema";
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
		if (FCode.equalsIgnoreCase("ComPanyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComPanyNo));
		}
		if (FCode.equalsIgnoreCase("ComPanyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComPanyName));
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddress));
		}
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpZipCode));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("HomePage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomePage));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("Information"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Information));
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
				strFieldValue = StrTool.GBKToUnicode(ComPanyNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ComPanyName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpAddress);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpZipCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(HomePage);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Information);
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

		if (FCode.equalsIgnoreCase("ComPanyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComPanyNo = FValue.trim();
			}
			else
				ComPanyNo = null;
		}
		if (FCode.equalsIgnoreCase("ComPanyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComPanyName = FValue.trim();
			}
			else
				ComPanyName = null;
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
		if (FCode.equalsIgnoreCase("Fax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax = FValue.trim();
			}
			else
				Fax = null;
		}
		if (FCode.equalsIgnoreCase("HomePage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomePage = FValue.trim();
			}
			else
				HomePage = null;
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
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("Information"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Information = FValue.trim();
			}
			else
				Information = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIComInfoSchema other = (RIComInfoSchema)otherObject;
		return
			ComPanyNo.equals(other.getComPanyNo())
			&& ComPanyName.equals(other.getComPanyName())
			&& GrpAddress.equals(other.getGrpAddress())
			&& GrpZipCode.equals(other.getGrpZipCode())
			&& Fax.equals(other.getFax())
			&& HomePage.equals(other.getHomePage())
			&& LinkMan.equals(other.getLinkMan())
			&& Phone.equals(other.getPhone())
			&& EMail.equals(other.getEMail())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Information.equals(other.getInformation());
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
		if( strFieldName.equals("ComPanyNo") ) {
			return 0;
		}
		if( strFieldName.equals("ComPanyName") ) {
			return 1;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return 2;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return 3;
		}
		if( strFieldName.equals("Fax") ) {
			return 4;
		}
		if( strFieldName.equals("HomePage") ) {
			return 5;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 6;
		}
		if( strFieldName.equals("Phone") ) {
			return 7;
		}
		if( strFieldName.equals("EMail") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("Information") ) {
			return 13;
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
				strFieldName = "ComPanyNo";
				break;
			case 1:
				strFieldName = "ComPanyName";
				break;
			case 2:
				strFieldName = "GrpAddress";
				break;
			case 3:
				strFieldName = "GrpZipCode";
				break;
			case 4:
				strFieldName = "Fax";
				break;
			case 5:
				strFieldName = "HomePage";
				break;
			case 6:
				strFieldName = "LinkMan";
				break;
			case 7:
				strFieldName = "Phone";
				break;
			case 8:
				strFieldName = "EMail";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "Information";
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
		if( strFieldName.equals("ComPanyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComPanyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomePage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("Information") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
