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
import com.sinosoft.lis.db.LDTaxTableColumnConfigDB;

/*
 * <p>ClassName: LDTaxTableColumnConfigSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LDTaxTableColumnConfigSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDTaxTableColumnConfigSchema.class);
	// @Field
	/** 数据表名 */
	private String TableCode;
	/** 总费用字段名 */
	private String SumAmountName;
	/** 费用类型字段名 */
	private String FeeTypeName;
	/** 险种编码字段名 */
	private String RiskCodeName;
	/** 管理机构字段名 */
	private String ManageComName;
	/** 币种字段名 */
	private String CurrencyName;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDTaxTableColumnConfigSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TableCode";

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
		LDTaxTableColumnConfigSchema cloned = (LDTaxTableColumnConfigSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTableCode()
	{
		return TableCode;
	}
	public void setTableCode(String aTableCode)
	{
		if(aTableCode!=null && aTableCode.length()>50)
			throw new IllegalArgumentException("数据表名TableCode值"+aTableCode+"的长度"+aTableCode.length()+"大于最大值50");
		TableCode = aTableCode;
	}
	public String getSumAmountName()
	{
		return SumAmountName;
	}
	public void setSumAmountName(String aSumAmountName)
	{
		if(aSumAmountName!=null && aSumAmountName.length()>50)
			throw new IllegalArgumentException("总费用字段名SumAmountName值"+aSumAmountName+"的长度"+aSumAmountName.length()+"大于最大值50");
		SumAmountName = aSumAmountName;
	}
	public String getFeeTypeName()
	{
		return FeeTypeName;
	}
	public void setFeeTypeName(String aFeeTypeName)
	{
		if(aFeeTypeName!=null && aFeeTypeName.length()>50)
			throw new IllegalArgumentException("费用类型字段名FeeTypeName值"+aFeeTypeName+"的长度"+aFeeTypeName.length()+"大于最大值50");
		FeeTypeName = aFeeTypeName;
	}
	public String getRiskCodeName()
	{
		return RiskCodeName;
	}
	public void setRiskCodeName(String aRiskCodeName)
	{
		if(aRiskCodeName!=null && aRiskCodeName.length()>10)
			throw new IllegalArgumentException("险种编码字段名RiskCodeName值"+aRiskCodeName+"的长度"+aRiskCodeName.length()+"大于最大值10");
		RiskCodeName = aRiskCodeName;
	}
	public String getManageComName()
	{
		return ManageComName;
	}
	public void setManageComName(String aManageComName)
	{
		if(aManageComName!=null && aManageComName.length()>10)
			throw new IllegalArgumentException("管理机构字段名ManageComName值"+aManageComName+"的长度"+aManageComName.length()+"大于最大值10");
		ManageComName = aManageComName;
	}
	public String getCurrencyName()
	{
		return CurrencyName;
	}
	public void setCurrencyName(String aCurrencyName)
	{
		if(aCurrencyName!=null && aCurrencyName.length()>50)
			throw new IllegalArgumentException("币种字段名CurrencyName值"+aCurrencyName+"的长度"+aCurrencyName.length()+"大于最大值50");
		CurrencyName = aCurrencyName;
	}

	/**
	* 使用另外一个 LDTaxTableColumnConfigSchema 对象给 Schema 赋值
	* @param: aLDTaxTableColumnConfigSchema LDTaxTableColumnConfigSchema
	**/
	public void setSchema(LDTaxTableColumnConfigSchema aLDTaxTableColumnConfigSchema)
	{
		this.TableCode = aLDTaxTableColumnConfigSchema.getTableCode();
		this.SumAmountName = aLDTaxTableColumnConfigSchema.getSumAmountName();
		this.FeeTypeName = aLDTaxTableColumnConfigSchema.getFeeTypeName();
		this.RiskCodeName = aLDTaxTableColumnConfigSchema.getRiskCodeName();
		this.ManageComName = aLDTaxTableColumnConfigSchema.getManageComName();
		this.CurrencyName = aLDTaxTableColumnConfigSchema.getCurrencyName();
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
			if( rs.getString("TableCode") == null )
				this.TableCode = null;
			else
				this.TableCode = rs.getString("TableCode").trim();

			if( rs.getString("SumAmountName") == null )
				this.SumAmountName = null;
			else
				this.SumAmountName = rs.getString("SumAmountName").trim();

			if( rs.getString("FeeTypeName") == null )
				this.FeeTypeName = null;
			else
				this.FeeTypeName = rs.getString("FeeTypeName").trim();

			if( rs.getString("RiskCodeName") == null )
				this.RiskCodeName = null;
			else
				this.RiskCodeName = rs.getString("RiskCodeName").trim();

			if( rs.getString("ManageComName") == null )
				this.ManageComName = null;
			else
				this.ManageComName = rs.getString("ManageComName").trim();

			if( rs.getString("CurrencyName") == null )
				this.CurrencyName = null;
			else
				this.CurrencyName = rs.getString("CurrencyName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDTaxTableColumnConfig表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaxTableColumnConfigSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDTaxTableColumnConfigSchema getSchema()
	{
		LDTaxTableColumnConfigSchema aLDTaxTableColumnConfigSchema = new LDTaxTableColumnConfigSchema();
		aLDTaxTableColumnConfigSchema.setSchema(this);
		return aLDTaxTableColumnConfigSchema;
	}

	public LDTaxTableColumnConfigDB getDB()
	{
		LDTaxTableColumnConfigDB aDBOper = new LDTaxTableColumnConfigDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaxTableColumnConfig描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TableCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SumAmountName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageComName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrencyName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaxTableColumnConfig>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TableCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SumAmountName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FeeTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CurrencyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaxTableColumnConfigSchema";
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
		if (FCode.equalsIgnoreCase("TableCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableCode));
		}
		if (FCode.equalsIgnoreCase("SumAmountName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumAmountName));
		}
		if (FCode.equalsIgnoreCase("FeeTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTypeName));
		}
		if (FCode.equalsIgnoreCase("RiskCodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCodeName));
		}
		if (FCode.equalsIgnoreCase("ManageComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageComName));
		}
		if (FCode.equalsIgnoreCase("CurrencyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrencyName));
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
				strFieldValue = StrTool.GBKToUnicode(TableCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SumAmountName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FeeTypeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageComName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CurrencyName);
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

		if (FCode.equalsIgnoreCase("TableCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableCode = FValue.trim();
			}
			else
				TableCode = null;
		}
		if (FCode.equalsIgnoreCase("SumAmountName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SumAmountName = FValue.trim();
			}
			else
				SumAmountName = null;
		}
		if (FCode.equalsIgnoreCase("FeeTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTypeName = FValue.trim();
			}
			else
				FeeTypeName = null;
		}
		if (FCode.equalsIgnoreCase("RiskCodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCodeName = FValue.trim();
			}
			else
				RiskCodeName = null;
		}
		if (FCode.equalsIgnoreCase("ManageComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageComName = FValue.trim();
			}
			else
				ManageComName = null;
		}
		if (FCode.equalsIgnoreCase("CurrencyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrencyName = FValue.trim();
			}
			else
				CurrencyName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDTaxTableColumnConfigSchema other = (LDTaxTableColumnConfigSchema)otherObject;
		return
			TableCode.equals(other.getTableCode())
			&& SumAmountName.equals(other.getSumAmountName())
			&& FeeTypeName.equals(other.getFeeTypeName())
			&& RiskCodeName.equals(other.getRiskCodeName())
			&& ManageComName.equals(other.getManageComName())
			&& CurrencyName.equals(other.getCurrencyName());
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
		if( strFieldName.equals("TableCode") ) {
			return 0;
		}
		if( strFieldName.equals("SumAmountName") ) {
			return 1;
		}
		if( strFieldName.equals("FeeTypeName") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCodeName") ) {
			return 3;
		}
		if( strFieldName.equals("ManageComName") ) {
			return 4;
		}
		if( strFieldName.equals("CurrencyName") ) {
			return 5;
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
				strFieldName = "TableCode";
				break;
			case 1:
				strFieldName = "SumAmountName";
				break;
			case 2:
				strFieldName = "FeeTypeName";
				break;
			case 3:
				strFieldName = "RiskCodeName";
				break;
			case 4:
				strFieldName = "ManageComName";
				break;
			case 5:
				strFieldName = "CurrencyName";
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
		if( strFieldName.equals("TableCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumAmountName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrencyName") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
