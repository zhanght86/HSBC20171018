

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
import com.sinosoft.lis.db.RiskInputConfigDB;

/*
 * <p>ClassName: RiskInputConfigSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class RiskInputConfigSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 控制类型 */
	private String CtrlType;
	/** 字段长度 */
	private String ParamsLen;
	/** 数据类型 */
	private String SubDivision;
	/** 默认显示 */
	private String Displayed;
	/** 字段默认值 */
	private String DefaultValue;
	/** 顺序 */
	private String OrderNo;
	/** 控件名称 */
	private String CtrlName;
	/** 是否必录 */
	private String IsRequired;
	/** 名称类型 */
	private String NameType;
	/** 备注1 */
	private String Remark1;
	/** 备注2 */
	private String Remark2;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RiskInputConfigSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "CtrlType";
		pk[2] = "OrderNo";

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
		RiskInputConfigSchema cloned = (RiskInputConfigSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getCtrlType()
	{
		return CtrlType;
	}
	public void setCtrlType(String aCtrlType)
	{
		CtrlType = aCtrlType;
	}
	public String getParamsLen()
	{
		return ParamsLen;
	}
	public void setParamsLen(String aParamsLen)
	{
		ParamsLen = aParamsLen;
	}
	public String getSubDivision()
	{
		return SubDivision;
	}
	public void setSubDivision(String aSubDivision)
	{
		SubDivision = aSubDivision;
	}
	public String getDisplayed()
	{
		return Displayed;
	}
	public void setDisplayed(String aDisplayed)
	{
		Displayed = aDisplayed;
	}
	public String getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public String getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	public String getCtrlName()
	{
		return CtrlName;
	}
	public void setCtrlName(String aCtrlName)
	{
		CtrlName = aCtrlName;
	}
	public String getIsRequired()
	{
		return IsRequired;
	}
	public void setIsRequired(String aIsRequired)
	{
		IsRequired = aIsRequired;
	}
	public String getNameType()
	{
		return NameType;
	}
	public void setNameType(String aNameType)
	{
		NameType = aNameType;
	}
	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		Remark1 = aRemark1;
	}
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		Remark2 = aRemark2;
	}

	/**
	* 使用另外一个 RiskInputConfigSchema 对象给 Schema 赋值
	* @param: aRiskInputConfigSchema RiskInputConfigSchema
	**/
	public void setSchema(RiskInputConfigSchema aRiskInputConfigSchema)
	{
		this.RiskCode = aRiskInputConfigSchema.getRiskCode();
		this.CtrlType = aRiskInputConfigSchema.getCtrlType();
		this.ParamsLen = aRiskInputConfigSchema.getParamsLen();
		this.SubDivision = aRiskInputConfigSchema.getSubDivision();
		this.Displayed = aRiskInputConfigSchema.getDisplayed();
		this.DefaultValue = aRiskInputConfigSchema.getDefaultValue();
		this.OrderNo = aRiskInputConfigSchema.getOrderNo();
		this.CtrlName = aRiskInputConfigSchema.getCtrlName();
		this.IsRequired = aRiskInputConfigSchema.getIsRequired();
		this.NameType = aRiskInputConfigSchema.getNameType();
		this.Remark1 = aRiskInputConfigSchema.getRemark1();
		this.Remark2 = aRiskInputConfigSchema.getRemark2();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("CtrlType") == null )
				this.CtrlType = null;
			else
				this.CtrlType = rs.getString("CtrlType").trim();

			if( rs.getString("ParamsLen") == null )
				this.ParamsLen = null;
			else
				this.ParamsLen = rs.getString("ParamsLen").trim();

			if( rs.getString("SubDivision") == null )
				this.SubDivision = null;
			else
				this.SubDivision = rs.getString("SubDivision").trim();

			if( rs.getString("Displayed") == null )
				this.Displayed = null;
			else
				this.Displayed = rs.getString("Displayed").trim();

			if( rs.getString("DefaultValue") == null )
				this.DefaultValue = null;
			else
				this.DefaultValue = rs.getString("DefaultValue").trim();

			if( rs.getString("OrderNo") == null )
				this.OrderNo = null;
			else
				this.OrderNo = rs.getString("OrderNo").trim();

			if( rs.getString("CtrlName") == null )
				this.CtrlName = null;
			else
				this.CtrlName = rs.getString("CtrlName").trim();

			if( rs.getString("IsRequired") == null )
				this.IsRequired = null;
			else
				this.IsRequired = rs.getString("IsRequired").trim();

			if( rs.getString("NameType") == null )
				this.NameType = null;
			else
				this.NameType = rs.getString("NameType").trim();

			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RiskInputConfig表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RiskInputConfigSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RiskInputConfigSchema getSchema()
	{
		RiskInputConfigSchema aRiskInputConfigSchema = new RiskInputConfigSchema();
		aRiskInputConfigSchema.setSchema(this);
		return aRiskInputConfigSchema;
	}

	public RiskInputConfigDB getDB()
	{
		RiskInputConfigDB aDBOper = new RiskInputConfigDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRiskInputConfig描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamsLen)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubDivision)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Displayed)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrderNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsRequired)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NameType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRiskInputConfig>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CtrlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ParamsLen = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubDivision = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Displayed = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DefaultValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OrderNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CtrlName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			IsRequired = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			NameType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RiskInputConfigSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlType));
		}
		if (FCode.equalsIgnoreCase("ParamsLen"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsLen));
		}
		if (FCode.equalsIgnoreCase("SubDivision"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubDivision));
		}
		if (FCode.equalsIgnoreCase("Displayed"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Displayed));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equalsIgnoreCase("CtrlName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlName));
		}
		if (FCode.equalsIgnoreCase("IsRequired"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsRequired));
		}
		if (FCode.equalsIgnoreCase("NameType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NameType));
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CtrlType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ParamsLen);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubDivision);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Displayed);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DefaultValue);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OrderNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CtrlName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(IsRequired);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(NameType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlType = FValue.trim();
			}
			else
				CtrlType = null;
		}
		if (FCode.equalsIgnoreCase("ParamsLen"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsLen = FValue.trim();
			}
			else
				ParamsLen = null;
		}
		if (FCode.equalsIgnoreCase("SubDivision"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubDivision = FValue.trim();
			}
			else
				SubDivision = null;
		}
		if (FCode.equalsIgnoreCase("Displayed"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Displayed = FValue.trim();
			}
			else
				Displayed = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultValue = FValue.trim();
			}
			else
				DefaultValue = null;
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrderNo = FValue.trim();
			}
			else
				OrderNo = null;
		}
		if (FCode.equalsIgnoreCase("CtrlName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlName = FValue.trim();
			}
			else
				CtrlName = null;
		}
		if (FCode.equalsIgnoreCase("IsRequired"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsRequired = FValue.trim();
			}
			else
				IsRequired = null;
		}
		if (FCode.equalsIgnoreCase("NameType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NameType = FValue.trim();
			}
			else
				NameType = null;
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark1 = FValue.trim();
			}
			else
				Remark1 = null;
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RiskInputConfigSchema other = (RiskInputConfigSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& CtrlType.equals(other.getCtrlType())
			&& ParamsLen.equals(other.getParamsLen())
			&& SubDivision.equals(other.getSubDivision())
			&& Displayed.equals(other.getDisplayed())
			&& DefaultValue.equals(other.getDefaultValue())
			&& OrderNo.equals(other.getOrderNo())
			&& CtrlName.equals(other.getCtrlName())
			&& IsRequired.equals(other.getIsRequired())
			&& NameType.equals(other.getNameType())
			&& Remark1.equals(other.getRemark1())
			&& Remark2.equals(other.getRemark2());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("CtrlType") ) {
			return 1;
		}
		if( strFieldName.equals("ParamsLen") ) {
			return 2;
		}
		if( strFieldName.equals("SubDivision") ) {
			return 3;
		}
		if( strFieldName.equals("Displayed") ) {
			return 4;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 5;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 6;
		}
		if( strFieldName.equals("CtrlName") ) {
			return 7;
		}
		if( strFieldName.equals("IsRequired") ) {
			return 8;
		}
		if( strFieldName.equals("NameType") ) {
			return 9;
		}
		if( strFieldName.equals("Remark1") ) {
			return 10;
		}
		if( strFieldName.equals("Remark2") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "CtrlType";
				break;
			case 2:
				strFieldName = "ParamsLen";
				break;
			case 3:
				strFieldName = "SubDivision";
				break;
			case 4:
				strFieldName = "Displayed";
				break;
			case 5:
				strFieldName = "DefaultValue";
				break;
			case 6:
				strFieldName = "OrderNo";
				break;
			case 7:
				strFieldName = "CtrlName";
				break;
			case 8:
				strFieldName = "IsRequired";
				break;
			case 9:
				strFieldName = "NameType";
				break;
			case 10:
				strFieldName = "Remark1";
				break;
			case 11:
				strFieldName = "Remark2";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsLen") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubDivision") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Displayed") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsRequired") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NameType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}

