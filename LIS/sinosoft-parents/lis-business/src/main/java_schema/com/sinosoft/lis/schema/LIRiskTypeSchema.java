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
import com.sinosoft.lis.db.LIRiskTypeDB;

/*
 * <p>ClassName: LIRiskTypeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIRiskTypeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIRiskTypeSchema.class);

	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 险种表述 */
	private String RiskName;
	/** 性质1 */
	private String RiskType1;
	/** 性质1描述 */
	private String Type1Name;
	/** 性质2 */
	private String RiskType2;
	/** 性质2描述 */
	private String Type2Name;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIRiskTypeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		LIRiskTypeSchema cloned = (LIRiskTypeSchema)super.clone();
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
	/**
	* 1长期健康险<p>
	* <p>
	* 2短期健康险<p>
	* <p>
	* 3长期寿险<p>
	* <p>
	* 4短期意外险<p>
	* <p>
	* 5极短期意外险<p>
	* <p>
	* 6还本险
	*/
	public String getRiskType1()
	{
		return RiskType1;
	}
	public void setRiskType1(String aRiskType1)
	{
		RiskType1 = aRiskType1;
	}
	public String getType1Name()
	{
		return Type1Name;
	}
	public void setType1Name(String aType1Name)
	{
		Type1Name = aType1Name;
	}
	/**
	* 1	分红<p>
	* 2	传统<p>
	* 3	投连
	*/
	public String getRiskType2()
	{
		return RiskType2;
	}
	public void setRiskType2(String aRiskType2)
	{
		RiskType2 = aRiskType2;
	}
	public String getType2Name()
	{
		return Type2Name;
	}
	public void setType2Name(String aType2Name)
	{
		Type2Name = aType2Name;
	}

	/**
	* 使用另外一个 LIRiskTypeSchema 对象给 Schema 赋值
	* @param: aLIRiskTypeSchema LIRiskTypeSchema
	**/
	public void setSchema(LIRiskTypeSchema aLIRiskTypeSchema)
	{
		this.RiskCode = aLIRiskTypeSchema.getRiskCode();
		this.RiskName = aLIRiskTypeSchema.getRiskName();
		this.RiskType1 = aLIRiskTypeSchema.getRiskType1();
		this.Type1Name = aLIRiskTypeSchema.getType1Name();
		this.RiskType2 = aLIRiskTypeSchema.getRiskType2();
		this.Type2Name = aLIRiskTypeSchema.getType2Name();
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

			if( rs.getString("RiskType1") == null )
				this.RiskType1 = null;
			else
				this.RiskType1 = rs.getString("RiskType1").trim();

			if( rs.getString("Type1Name") == null )
				this.Type1Name = null;
			else
				this.Type1Name = rs.getString("Type1Name").trim();

			if( rs.getString("RiskType2") == null )
				this.RiskType2 = null;
			else
				this.RiskType2 = rs.getString("RiskType2").trim();

			if( rs.getString("Type2Name") == null )
				this.Type2Name = null;
			else
				this.Type2Name = rs.getString("Type2Name").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIRiskType表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIRiskTypeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIRiskTypeSchema getSchema()
	{
		LIRiskTypeSchema aLIRiskTypeSchema = new LIRiskTypeSchema();
		aLIRiskTypeSchema.setSchema(this);
		return aLIRiskTypeSchema;
	}

	public LIRiskTypeDB getDB()
	{
		LIRiskTypeDB aDBOper = new LIRiskTypeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIRiskType描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type1Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type2Name));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIRiskType>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Type1Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Type2Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIRiskTypeSchema";
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
		if (FCode.equalsIgnoreCase("RiskType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType1));
		}
		if (FCode.equalsIgnoreCase("Type1Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type1Name));
		}
		if (FCode.equalsIgnoreCase("RiskType2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType2));
		}
		if (FCode.equalsIgnoreCase("Type2Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type2Name));
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
				strFieldValue = StrTool.GBKToUnicode(RiskType1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Type1Name);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskType2);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Type2Name);
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
		if (FCode.equalsIgnoreCase("RiskType1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType1 = FValue.trim();
			}
			else
				RiskType1 = null;
		}
		if (FCode.equalsIgnoreCase("Type1Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type1Name = FValue.trim();
			}
			else
				Type1Name = null;
		}
		if (FCode.equalsIgnoreCase("RiskType2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType2 = FValue.trim();
			}
			else
				RiskType2 = null;
		}
		if (FCode.equalsIgnoreCase("Type2Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type2Name = FValue.trim();
			}
			else
				Type2Name = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIRiskTypeSchema other = (LIRiskTypeSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskName.equals(other.getRiskName())
			&& RiskType1.equals(other.getRiskType1())
			&& Type1Name.equals(other.getType1Name())
			&& RiskType2.equals(other.getRiskType2())
			&& Type2Name.equals(other.getType2Name());
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
		if( strFieldName.equals("RiskType1") ) {
			return 2;
		}
		if( strFieldName.equals("Type1Name") ) {
			return 3;
		}
		if( strFieldName.equals("RiskType2") ) {
			return 4;
		}
		if( strFieldName.equals("Type2Name") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskName";
				break;
			case 2:
				strFieldName = "RiskType1";
				break;
			case 3:
				strFieldName = "Type1Name";
				break;
			case 4:
				strFieldName = "RiskType2";
				break;
			case 5:
				strFieldName = "Type2Name";
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
		if( strFieldName.equals("RiskType1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type1Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type2Name") ) {
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
