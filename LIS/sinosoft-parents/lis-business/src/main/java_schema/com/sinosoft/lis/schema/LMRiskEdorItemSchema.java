

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
import com.sinosoft.lis.db.LMRiskEdorItemDB;

/*
 * <p>ClassName: LMRiskEdorItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskEdorItemSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种名称 */
	private String RiskName;
	/** 保全项目编码 */
	private String EdorCode;
	/** 财务处理类型 */
	private String FinType;
	/** 保全项目属性 */
	private String EdorProperty;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskEdorItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "EdorCode";

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
		LMRiskEdorItemSchema cloned = (LMRiskEdorItemSchema)super.clone();
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
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	public String getEdorCode()
	{
		return EdorCode;
	}
	public void setEdorCode(String aEdorCode)
	{
		EdorCode = aEdorCode;
	}
	/**
	* BF－补费<p>
	* TF－退费<p>
	* TB－退保金<p>
	* JK－借款<p>
	* HK－还款<p>
	* LX－利息<p>
	* GB－工本费
	*/
	public String getFinType()
	{
		return FinType;
	}
	public void setFinType(String aFinType)
	{
		FinType = aFinType;
	}
	/**
	* 0:普通项目<p>
	* 1:特殊项目（需要高权限用户处理）
	*/
	public String getEdorProperty()
	{
		return EdorProperty;
	}
	public void setEdorProperty(String aEdorProperty)
	{
		EdorProperty = aEdorProperty;
	}

	/**
	* 使用另外一个 LMRiskEdorItemSchema 对象给 Schema 赋值
	* @param: aLMRiskEdorItemSchema LMRiskEdorItemSchema
	**/
	public void setSchema(LMRiskEdorItemSchema aLMRiskEdorItemSchema)
	{
		this.RiskCode = aLMRiskEdorItemSchema.getRiskCode();
		this.RiskName = aLMRiskEdorItemSchema.getRiskName();
		this.EdorCode = aLMRiskEdorItemSchema.getEdorCode();
		this.FinType = aLMRiskEdorItemSchema.getFinType();
		this.EdorProperty = aLMRiskEdorItemSchema.getEdorProperty();
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

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("EdorCode") == null )
				this.EdorCode = null;
			else
				this.EdorCode = rs.getString("EdorCode").trim();

			if( rs.getString("FinType") == null )
				this.FinType = null;
			else
				this.FinType = rs.getString("FinType").trim();

			if( rs.getString("EdorProperty") == null )
				this.EdorProperty = null;
			else
				this.EdorProperty = rs.getString("EdorProperty").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRiskEdorItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskEdorItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskEdorItemSchema getSchema()
	{
		LMRiskEdorItemSchema aLMRiskEdorItemSchema = new LMRiskEdorItemSchema();
		aLMRiskEdorItemSchema.setSchema(this);
		return aLMRiskEdorItemSchema;
	}

	public LMRiskEdorItemDB getDB()
	{
		LMRiskEdorItemDB aDBOper = new LMRiskEdorItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskEdorItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorProperty));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskEdorItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FinType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EdorProperty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskEdorItemSchema";
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
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorCode));
		}
		if (FCode.equalsIgnoreCase("FinType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinType));
		}
		if (FCode.equalsIgnoreCase("EdorProperty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorProperty));
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
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FinType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EdorProperty);
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
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorCode = FValue.trim();
			}
			else
				EdorCode = null;
		}
		if (FCode.equalsIgnoreCase("FinType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinType = FValue.trim();
			}
			else
				FinType = null;
		}
		if (FCode.equalsIgnoreCase("EdorProperty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorProperty = FValue.trim();
			}
			else
				EdorProperty = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskEdorItemSchema other = (LMRiskEdorItemSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskName.equals(other.getRiskName())
			&& EdorCode.equals(other.getEdorCode())
			&& FinType.equals(other.getFinType())
			&& EdorProperty.equals(other.getEdorProperty());
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
		if( strFieldName.equals("RiskName") ) {
			return 1;
		}
		if( strFieldName.equals("EdorCode") ) {
			return 2;
		}
		if( strFieldName.equals("FinType") ) {
			return 3;
		}
		if( strFieldName.equals("EdorProperty") ) {
			return 4;
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
				strFieldName = "RiskName";
				break;
			case 2:
				strFieldName = "EdorCode";
				break;
			case 3:
				strFieldName = "FinType";
				break;
			case 4:
				strFieldName = "EdorProperty";
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
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorProperty") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
