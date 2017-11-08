

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
import com.sinosoft.lis.db.RIComLinkManInfoDB;

/*
 * <p>ClassName: RIComLinkManInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIComLinkManInfoSchema implements Schema, Cloneable
{
	// @Field
	/** 公司代码 */
	private String ReComCode;
	/** 联系人代码 */
	private String RelaCode;
	/** 联系人名 */
	private String RelaName;
	/** 联系电话 */
	private String RelaTel;
	/** E_mail */
	private String Email;
	/** 传真 */
	private String FaxNo;
	/** 部门 */
	private String Department;
	/** 手机 */
	private String MobileTel;
	/** 职务 */
	private String Duty;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 操作人 */
	private Date Operator;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIComLinkManInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ReComCode";
		pk[1] = "RelaCode";

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
		RIComLinkManInfoSchema cloned = (RIComLinkManInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getReComCode()
	{
		return ReComCode;
	}
	public void setReComCode(String aReComCode)
	{
		ReComCode = aReComCode;
	}
	public String getRelaCode()
	{
		return RelaCode;
	}
	public void setRelaCode(String aRelaCode)
	{
		RelaCode = aRelaCode;
	}
	public String getRelaName()
	{
		return RelaName;
	}
	public void setRelaName(String aRelaName)
	{
		RelaName = aRelaName;
	}
	public String getRelaTel()
	{
		return RelaTel;
	}
	public void setRelaTel(String aRelaTel)
	{
		RelaTel = aRelaTel;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		Email = aEmail;
	}
	public String getFaxNo()
	{
		return FaxNo;
	}
	public void setFaxNo(String aFaxNo)
	{
		FaxNo = aFaxNo;
	}
	public String getDepartment()
	{
		return Department;
	}
	public void setDepartment(String aDepartment)
	{
		Department = aDepartment;
	}
	public String getMobileTel()
	{
		return MobileTel;
	}
	public void setMobileTel(String aMobileTel)
	{
		MobileTel = aMobileTel;
	}
	public String getDuty()
	{
		return Duty;
	}
	public void setDuty(String aDuty)
	{
		Duty = aDuty;
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
	public String getOperator()
	{
		if( Operator != null )
			return fDate.getString(Operator);
		else
			return null;
	}
	public void setOperator(Date aOperator)
	{
		Operator = aOperator;
	}
	public void setOperator(String aOperator)
	{
		if (aOperator != null && !aOperator.equals("") )
		{
			Operator = fDate.getDate( aOperator );
		}
		else
			Operator = null;
	}


	/**
	* 使用另外一个 RIComLinkManInfoSchema 对象给 Schema 赋值
	* @param: aRIComLinkManInfoSchema RIComLinkManInfoSchema
	**/
	public void setSchema(RIComLinkManInfoSchema aRIComLinkManInfoSchema)
	{
		this.ReComCode = aRIComLinkManInfoSchema.getReComCode();
		this.RelaCode = aRIComLinkManInfoSchema.getRelaCode();
		this.RelaName = aRIComLinkManInfoSchema.getRelaName();
		this.RelaTel = aRIComLinkManInfoSchema.getRelaTel();
		this.Email = aRIComLinkManInfoSchema.getEmail();
		this.FaxNo = aRIComLinkManInfoSchema.getFaxNo();
		this.Department = aRIComLinkManInfoSchema.getDepartment();
		this.MobileTel = aRIComLinkManInfoSchema.getMobileTel();
		this.Duty = aRIComLinkManInfoSchema.getDuty();
		this.MakeDate = fDate.getDate( aRIComLinkManInfoSchema.getMakeDate());
		this.MakeTime = aRIComLinkManInfoSchema.getMakeTime();
		this.Operator = fDate.getDate( aRIComLinkManInfoSchema.getOperator());
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
			if( rs.getString("ReComCode") == null )
				this.ReComCode = null;
			else
				this.ReComCode = rs.getString("ReComCode").trim();

			if( rs.getString("RelaCode") == null )
				this.RelaCode = null;
			else
				this.RelaCode = rs.getString("RelaCode").trim();

			if( rs.getString("RelaName") == null )
				this.RelaName = null;
			else
				this.RelaName = rs.getString("RelaName").trim();

			if( rs.getString("RelaTel") == null )
				this.RelaTel = null;
			else
				this.RelaTel = rs.getString("RelaTel").trim();

			if( rs.getString("Email") == null )
				this.Email = null;
			else
				this.Email = rs.getString("Email").trim();

			if( rs.getString("FaxNo") == null )
				this.FaxNo = null;
			else
				this.FaxNo = rs.getString("FaxNo").trim();

			if( rs.getString("Department") == null )
				this.Department = null;
			else
				this.Department = rs.getString("Department").trim();

			if( rs.getString("MobileTel") == null )
				this.MobileTel = null;
			else
				this.MobileTel = rs.getString("MobileTel").trim();

			if( rs.getString("Duty") == null )
				this.Duty = null;
			else
				this.Duty = rs.getString("Duty").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.Operator = rs.getDate("Operator");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIComLinkManInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIComLinkManInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIComLinkManInfoSchema getSchema()
	{
		RIComLinkManInfoSchema aRIComLinkManInfoSchema = new RIComLinkManInfoSchema();
		aRIComLinkManInfoSchema.setSchema(this);
		return aRIComLinkManInfoSchema;
	}

	public RIComLinkManInfoDB getDB()
	{
		RIComLinkManInfoDB aDBOper = new RIComLinkManInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIComLinkManInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ReComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaTel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FaxNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MobileTel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Duty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Operator )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIComLinkManInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ReComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RelaCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RelaName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RelaTel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FaxNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Department = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MobileTel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Duty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIComLinkManInfoSchema";
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
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReComCode));
		}
		if (FCode.equalsIgnoreCase("RelaCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaCode));
		}
		if (FCode.equalsIgnoreCase("RelaName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaName));
		}
		if (FCode.equalsIgnoreCase("RelaTel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaTel));
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Email));
		}
		if (FCode.equalsIgnoreCase("FaxNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FaxNo));
		}
		if (FCode.equalsIgnoreCase("Department"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department));
		}
		if (FCode.equalsIgnoreCase("MobileTel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobileTel));
		}
		if (FCode.equalsIgnoreCase("Duty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Duty));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOperator()));
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
				strFieldValue = StrTool.GBKToUnicode(ReComCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RelaCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RelaName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RelaTel);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Email);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FaxNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Department);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MobileTel);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Duty);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOperator()));
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

		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReComCode = FValue.trim();
			}
			else
				ReComCode = null;
		}
		if (FCode.equalsIgnoreCase("RelaCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaCode = FValue.trim();
			}
			else
				RelaCode = null;
		}
		if (FCode.equalsIgnoreCase("RelaName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaName = FValue.trim();
			}
			else
				RelaName = null;
		}
		if (FCode.equalsIgnoreCase("RelaTel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaTel = FValue.trim();
			}
			else
				RelaTel = null;
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Email = FValue.trim();
			}
			else
				Email = null;
		}
		if (FCode.equalsIgnoreCase("FaxNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FaxNo = FValue.trim();
			}
			else
				FaxNo = null;
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
		if (FCode.equalsIgnoreCase("MobileTel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobileTel = FValue.trim();
			}
			else
				MobileTel = null;
		}
		if (FCode.equalsIgnoreCase("Duty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Duty = FValue.trim();
			}
			else
				Duty = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Operator = fDate.getDate( FValue );
			}
			else
				Operator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIComLinkManInfoSchema other = (RIComLinkManInfoSchema)otherObject;
		return
			ReComCode.equals(other.getReComCode())
			&& RelaCode.equals(other.getRelaCode())
			&& RelaName.equals(other.getRelaName())
			&& RelaTel.equals(other.getRelaTel())
			&& Email.equals(other.getEmail())
			&& FaxNo.equals(other.getFaxNo())
			&& Department.equals(other.getDepartment())
			&& MobileTel.equals(other.getMobileTel())
			&& Duty.equals(other.getDuty())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(Operator).equals(other.getOperator());
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
		if( strFieldName.equals("ReComCode") ) {
			return 0;
		}
		if( strFieldName.equals("RelaCode") ) {
			return 1;
		}
		if( strFieldName.equals("RelaName") ) {
			return 2;
		}
		if( strFieldName.equals("RelaTel") ) {
			return 3;
		}
		if( strFieldName.equals("Email") ) {
			return 4;
		}
		if( strFieldName.equals("FaxNo") ) {
			return 5;
		}
		if( strFieldName.equals("Department") ) {
			return 6;
		}
		if( strFieldName.equals("MobileTel") ) {
			return 7;
		}
		if( strFieldName.equals("Duty") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
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
				strFieldName = "ReComCode";
				break;
			case 1:
				strFieldName = "RelaCode";
				break;
			case 2:
				strFieldName = "RelaName";
				break;
			case 3:
				strFieldName = "RelaTel";
				break;
			case 4:
				strFieldName = "Email";
				break;
			case 5:
				strFieldName = "FaxNo";
				break;
			case 6:
				strFieldName = "Department";
				break;
			case 7:
				strFieldName = "MobileTel";
				break;
			case 8:
				strFieldName = "Duty";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "Operator";
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
		if( strFieldName.equals("ReComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaTel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Email") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FaxNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MobileTel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Duty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
