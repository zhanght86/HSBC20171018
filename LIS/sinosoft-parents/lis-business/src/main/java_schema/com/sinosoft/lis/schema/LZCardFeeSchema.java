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
import com.sinosoft.lis.db.LZCardFeeDB;

/*
 * <p>ClassName: LZCardFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZCardFeeSchema.class);

	// @Field
	/** 单证编码 */
	private String CertifyCode;
	/** 单证名称 */
	private String CertifyName;
	/** 单证单位 */
	private String Unit;
	/** 单证定价 */
	private double Price;
	/** 单证日期 */
	private Date DefineDate;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZCardFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CertifyCode";
		pk[1] = "ManageCom";

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
		LZCardFeeSchema cloned = (LZCardFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getCertifyName()
	{
		return CertifyName;
	}
	public void setCertifyName(String aCertifyName)
	{
		CertifyName = aCertifyName;
	}
	public String getUnit()
	{
		return Unit;
	}
	public void setUnit(String aUnit)
	{
		Unit = aUnit;
	}
	public double getPrice()
	{
		return Price;
	}
	public void setPrice(double aPrice)
	{
		Price = aPrice;
	}
	public void setPrice(String aPrice)
	{
		if (aPrice != null && !aPrice.equals(""))
		{
			Double tDouble = new Double(aPrice);
			double d = tDouble.doubleValue();
			Price = d;
		}
	}

	public String getDefineDate()
	{
		if( DefineDate != null )
			return fDate.getString(DefineDate);
		else
			return null;
	}
	public void setDefineDate(Date aDefineDate)
	{
		DefineDate = aDefineDate;
	}
	public void setDefineDate(String aDefineDate)
	{
		if (aDefineDate != null && !aDefineDate.equals("") )
		{
			DefineDate = fDate.getDate( aDefineDate );
		}
		else
			DefineDate = null;
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}

	/**
	* 使用另外一个 LZCardFeeSchema 对象给 Schema 赋值
	* @param: aLZCardFeeSchema LZCardFeeSchema
	**/
	public void setSchema(LZCardFeeSchema aLZCardFeeSchema)
	{
		this.CertifyCode = aLZCardFeeSchema.getCertifyCode();
		this.CertifyName = aLZCardFeeSchema.getCertifyName();
		this.Unit = aLZCardFeeSchema.getUnit();
		this.Price = aLZCardFeeSchema.getPrice();
		this.DefineDate = fDate.getDate( aLZCardFeeSchema.getDefineDate());
		this.Operator = aLZCardFeeSchema.getOperator();
		this.ManageCom = aLZCardFeeSchema.getManageCom();
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
			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("CertifyName") == null )
				this.CertifyName = null;
			else
				this.CertifyName = rs.getString("CertifyName").trim();

			if( rs.getString("Unit") == null )
				this.Unit = null;
			else
				this.Unit = rs.getString("Unit").trim();

			this.Price = rs.getDouble("Price");
			this.DefineDate = rs.getDate("DefineDate");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZCardFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZCardFeeSchema getSchema()
	{
		LZCardFeeSchema aLZCardFeeSchema = new LZCardFeeSchema();
		aLZCardFeeSchema.setSchema(this);
		return aLZCardFeeSchema;
	}

	public LZCardFeeDB getDB()
	{
		LZCardFeeDB aDBOper = new LZCardFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Unit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Price));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DefineDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Unit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Price = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			DefineDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardFeeSchema";
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("CertifyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyName));
		}
		if (FCode.equalsIgnoreCase("Unit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Unit));
		}
		if (FCode.equalsIgnoreCase("Price"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Price));
		}
		if (FCode.equalsIgnoreCase("DefineDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDefineDate()));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Unit);
				break;
			case 3:
				strFieldValue = String.valueOf(Price);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDefineDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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

		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("CertifyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyName = FValue.trim();
			}
			else
				CertifyName = null;
		}
		if (FCode.equalsIgnoreCase("Unit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Unit = FValue.trim();
			}
			else
				Unit = null;
		}
		if (FCode.equalsIgnoreCase("Price"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Price = d;
			}
		}
		if (FCode.equalsIgnoreCase("DefineDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DefineDate = fDate.getDate( FValue );
			}
			else
				DefineDate = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZCardFeeSchema other = (LZCardFeeSchema)otherObject;
		return
			CertifyCode.equals(other.getCertifyCode())
			&& CertifyName.equals(other.getCertifyName())
			&& Unit.equals(other.getUnit())
			&& Price == other.getPrice()
			&& fDate.getString(DefineDate).equals(other.getDefineDate())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom());
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
		if( strFieldName.equals("CertifyCode") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyName") ) {
			return 1;
		}
		if( strFieldName.equals("Unit") ) {
			return 2;
		}
		if( strFieldName.equals("Price") ) {
			return 3;
		}
		if( strFieldName.equals("DefineDate") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
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
				strFieldName = "CertifyCode";
				break;
			case 1:
				strFieldName = "CertifyName";
				break;
			case 2:
				strFieldName = "Unit";
				break;
			case 3:
				strFieldName = "Price";
				break;
			case 4:
				strFieldName = "DefineDate";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Unit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Price") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DefineDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
