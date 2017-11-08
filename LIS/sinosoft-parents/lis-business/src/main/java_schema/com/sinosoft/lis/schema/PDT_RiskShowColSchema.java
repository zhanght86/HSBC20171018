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
import com.sinosoft.lis.db.PDT_RiskShowColDB;

/*
 * <p>ClassName: PDT_RiskShowColSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 险种测试功能
 */
public class PDT_RiskShowColSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PDT_RiskShowColSchema.class);

	// @Field
	/** 模板id */
	private String TemplateID;
	/** 显示类型 */
	private String ShowType;
	/** 显示字段编码 */
	private String ColCode;
	/** 显示字段名称 */
	private String ColName;
	/** 显示顺序 */
	private int ColOrder;
	/** 下拉选择标记 */
	private String OptionFlag;
	/** 显示属性 */
	private String ColProperties;
	/** 过滤sql */
	private String FilterSql;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PDT_RiskShowColSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "TemplateID";
		pk[1] = "ShowType";
		pk[2] = "ColCode";
		pk[3] = "ColName";
		pk[4] = "ColOrder";

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
		PDT_RiskShowColSchema cloned = (PDT_RiskShowColSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTemplateID()
	{
		return TemplateID;
	}
	public void setTemplateID(String aTemplateID)
	{
		if(aTemplateID!=null && aTemplateID.length()>10)
			throw new IllegalArgumentException("模板idTemplateID值"+aTemplateID+"的长度"+aTemplateID.length()+"大于最大值10");
		TemplateID = aTemplateID;
	}
	/**
	* Appnt 投保人<p>
	* Inserd 被保人<p>
	* RiskCode 险种<p>
	* Duty 责任<p>
	* Pay 缴费
	*/
	public String getShowType()
	{
		return ShowType;
	}
	public void setShowType(String aShowType)
	{
		if(aShowType!=null && aShowType.length()>10)
			throw new IllegalArgumentException("显示类型ShowType值"+aShowType+"的长度"+aShowType.length()+"大于最大值10");
		ShowType = aShowType;
	}
	/**
	* 0-查询detail表<p>
	* 1-查询产品模型表
	*/
	public String getColCode()
	{
		return ColCode;
	}
	public void setColCode(String aColCode)
	{
		if(aColCode!=null && aColCode.length()>100)
			throw new IllegalArgumentException("显示字段编码ColCode值"+aColCode+"的长度"+aColCode.length()+"大于最大值100");
		ColCode = aColCode;
	}
	public String getColName()
	{
		return ColName;
	}
	public void setColName(String aColName)
	{
		if(aColName!=null && aColName.length()>100)
			throw new IllegalArgumentException("显示字段名称ColName值"+aColName+"的长度"+aColName.length()+"大于最大值100");
		ColName = aColName;
	}
	public int getColOrder()
	{
		return ColOrder;
	}
	public void setColOrder(int aColOrder)
	{
		ColOrder = aColOrder;
	}
	public void setColOrder(String aColOrder)
	{
		if (aColOrder != null && !aColOrder.equals(""))
		{
			Integer tInteger = new Integer(aColOrder);
			int i = tInteger.intValue();
			ColOrder = i;
		}
	}

	/**
	* 0 - 非下拉<p>
	* <p>
	* 1 - 下拉
	*/
	public String getOptionFlag()
	{
		return OptionFlag;
	}
	public void setOptionFlag(String aOptionFlag)
	{
		if(aOptionFlag!=null && aOptionFlag.length()>1)
			throw new IllegalArgumentException("下拉选择标记OptionFlag值"+aOptionFlag+"的长度"+aOptionFlag.length()+"大于最大值1");
		OptionFlag = aOptionFlag;
	}
	public String getColProperties()
	{
		return ColProperties;
	}
	public void setColProperties(String aColProperties)
	{
		if(aColProperties!=null && aColProperties.length()>1000)
			throw new IllegalArgumentException("显示属性ColProperties值"+aColProperties+"的长度"+aColProperties.length()+"大于最大值1000");
		ColProperties = aColProperties;
	}
	public String getFilterSql()
	{
		return FilterSql;
	}
	public void setFilterSql(String aFilterSql)
	{
		if(aFilterSql!=null && aFilterSql.length()>4000)
			throw new IllegalArgumentException("过滤sqlFilterSql值"+aFilterSql+"的长度"+aFilterSql.length()+"大于最大值4000");
		FilterSql = aFilterSql;
	}

	/**
	* 使用另外一个 PDT_RiskShowColSchema 对象给 Schema 赋值
	* @param: aPDT_RiskShowColSchema PDT_RiskShowColSchema
	**/
	public void setSchema(PDT_RiskShowColSchema aPDT_RiskShowColSchema)
	{
		this.TemplateID = aPDT_RiskShowColSchema.getTemplateID();
		this.ShowType = aPDT_RiskShowColSchema.getShowType();
		this.ColCode = aPDT_RiskShowColSchema.getColCode();
		this.ColName = aPDT_RiskShowColSchema.getColName();
		this.ColOrder = aPDT_RiskShowColSchema.getColOrder();
		this.OptionFlag = aPDT_RiskShowColSchema.getOptionFlag();
		this.ColProperties = aPDT_RiskShowColSchema.getColProperties();
		this.FilterSql = aPDT_RiskShowColSchema.getFilterSql();
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
			if( rs.getString("TemplateID") == null )
				this.TemplateID = null;
			else
				this.TemplateID = rs.getString("TemplateID").trim();

			if( rs.getString("ShowType") == null )
				this.ShowType = null;
			else
				this.ShowType = rs.getString("ShowType").trim();

			if( rs.getString("ColCode") == null )
				this.ColCode = null;
			else
				this.ColCode = rs.getString("ColCode").trim();

			if( rs.getString("ColName") == null )
				this.ColName = null;
			else
				this.ColName = rs.getString("ColName").trim();

			this.ColOrder = rs.getInt("ColOrder");
			if( rs.getString("OptionFlag") == null )
				this.OptionFlag = null;
			else
				this.OptionFlag = rs.getString("OptionFlag").trim();

			if( rs.getString("ColProperties") == null )
				this.ColProperties = null;
			else
				this.ColProperties = rs.getString("ColProperties").trim();

			if( rs.getString("FilterSql") == null )
				this.FilterSql = null;
			else
				this.FilterSql = rs.getString("FilterSql").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PDT_RiskShowCol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDT_RiskShowColSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PDT_RiskShowColSchema getSchema()
	{
		PDT_RiskShowColSchema aPDT_RiskShowColSchema = new PDT_RiskShowColSchema();
		aPDT_RiskShowColSchema.setSchema(this);
		return aPDT_RiskShowColSchema;
	}

	public PDT_RiskShowColDB getDB()
	{
		PDT_RiskShowColDB aDBOper = new PDT_RiskShowColDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskShowCol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TemplateID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ColOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OptionFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColProperties)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FilterSql));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskShowCol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TemplateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ShowType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ColCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ColName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ColOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			OptionFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ColProperties = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FilterSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDT_RiskShowColSchema";
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
		if (FCode.equalsIgnoreCase("TemplateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TemplateID));
		}
		if (FCode.equalsIgnoreCase("ShowType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowType));
		}
		if (FCode.equalsIgnoreCase("ColCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColCode));
		}
		if (FCode.equalsIgnoreCase("ColName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColName));
		}
		if (FCode.equalsIgnoreCase("ColOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColOrder));
		}
		if (FCode.equalsIgnoreCase("OptionFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OptionFlag));
		}
		if (FCode.equalsIgnoreCase("ColProperties"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColProperties));
		}
		if (FCode.equalsIgnoreCase("FilterSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FilterSql));
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
				strFieldValue = StrTool.GBKToUnicode(TemplateID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ShowType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ColCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ColName);
				break;
			case 4:
				strFieldValue = String.valueOf(ColOrder);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OptionFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ColProperties);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FilterSql);
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

		if (FCode.equalsIgnoreCase("TemplateID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TemplateID = FValue.trim();
			}
			else
				TemplateID = null;
		}
		if (FCode.equalsIgnoreCase("ShowType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowType = FValue.trim();
			}
			else
				ShowType = null;
		}
		if (FCode.equalsIgnoreCase("ColCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColCode = FValue.trim();
			}
			else
				ColCode = null;
		}
		if (FCode.equalsIgnoreCase("ColName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColName = FValue.trim();
			}
			else
				ColName = null;
		}
		if (FCode.equalsIgnoreCase("ColOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ColOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("OptionFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OptionFlag = FValue.trim();
			}
			else
				OptionFlag = null;
		}
		if (FCode.equalsIgnoreCase("ColProperties"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColProperties = FValue.trim();
			}
			else
				ColProperties = null;
		}
		if (FCode.equalsIgnoreCase("FilterSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FilterSql = FValue.trim();
			}
			else
				FilterSql = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PDT_RiskShowColSchema other = (PDT_RiskShowColSchema)otherObject;
		return
			TemplateID.equals(other.getTemplateID())
			&& ShowType.equals(other.getShowType())
			&& ColCode.equals(other.getColCode())
			&& ColName.equals(other.getColName())
			&& ColOrder == other.getColOrder()
			&& OptionFlag.equals(other.getOptionFlag())
			&& ColProperties.equals(other.getColProperties())
			&& FilterSql.equals(other.getFilterSql());
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
		if( strFieldName.equals("TemplateID") ) {
			return 0;
		}
		if( strFieldName.equals("ShowType") ) {
			return 1;
		}
		if( strFieldName.equals("ColCode") ) {
			return 2;
		}
		if( strFieldName.equals("ColName") ) {
			return 3;
		}
		if( strFieldName.equals("ColOrder") ) {
			return 4;
		}
		if( strFieldName.equals("OptionFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ColProperties") ) {
			return 6;
		}
		if( strFieldName.equals("FilterSql") ) {
			return 7;
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
				strFieldName = "TemplateID";
				break;
			case 1:
				strFieldName = "ShowType";
				break;
			case 2:
				strFieldName = "ColCode";
				break;
			case 3:
				strFieldName = "ColName";
				break;
			case 4:
				strFieldName = "ColOrder";
				break;
			case 5:
				strFieldName = "OptionFlag";
				break;
			case 6:
				strFieldName = "ColProperties";
				break;
			case 7:
				strFieldName = "FilterSql";
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
		if( strFieldName.equals("TemplateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OptionFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColProperties") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FilterSql") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
