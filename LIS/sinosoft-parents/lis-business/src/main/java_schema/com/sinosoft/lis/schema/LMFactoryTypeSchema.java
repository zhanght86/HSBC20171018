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
import com.sinosoft.lis.db.LMFactoryTypeDB;

/*
 * <p>ClassName: LMFactoryTypeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LMFactoryTypeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMFactoryTypeSchema.class);

	// @Field
	/** 要素类型 */
	private String FactoryType;
	/** 要素类型名称 */
	private String FactoryTypeName;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMFactoryTypeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "FactoryType";

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
		LMFactoryTypeSchema cloned = (LMFactoryTypeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* ｓｑｌ模板类别<p>
	* 000000 保单要素<p>
	* 000001 保单责任要素<p>
	* 000002 保单给付要素<p>
	* 000003 保单账户要素<p>
	* 000004 理赔计算要素
	*/
	public String getFactoryType()
	{
		return FactoryType;
	}
	public void setFactoryType(String aFactoryType)
	{
		FactoryType = aFactoryType;
	}
	public String getFactoryTypeName()
	{
		return FactoryTypeName;
	}
	public void setFactoryTypeName(String aFactoryTypeName)
	{
		FactoryTypeName = aFactoryTypeName;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LMFactoryTypeSchema 对象给 Schema 赋值
	* @param: aLMFactoryTypeSchema LMFactoryTypeSchema
	**/
	public void setSchema(LMFactoryTypeSchema aLMFactoryTypeSchema)
	{
		this.FactoryType = aLMFactoryTypeSchema.getFactoryType();
		this.FactoryTypeName = aLMFactoryTypeSchema.getFactoryTypeName();
		this.Remark = aLMFactoryTypeSchema.getRemark();
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
			if( rs.getString("FactoryType") == null )
				this.FactoryType = null;
			else
				this.FactoryType = rs.getString("FactoryType").trim();

			if( rs.getString("FactoryTypeName") == null )
				this.FactoryTypeName = null;
			else
				this.FactoryTypeName = rs.getString("FactoryTypeName").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMFactoryType表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactoryTypeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMFactoryTypeSchema getSchema()
	{
		LMFactoryTypeSchema aLMFactoryTypeSchema = new LMFactoryTypeSchema();
		aLMFactoryTypeSchema.setSchema(this);
		return aLMFactoryTypeSchema;
	}

	public LMFactoryTypeDB getDB()
	{
		LMFactoryTypeDB aDBOper = new LMFactoryTypeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFactoryType描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FactoryType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFactoryType>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FactoryType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactoryTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactoryTypeSchema";
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
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryType));
		}
		if (FCode.equalsIgnoreCase("FactoryTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryTypeName));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(FactoryType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FactoryTypeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryType = FValue.trim();
			}
			else
				FactoryType = null;
		}
		if (FCode.equalsIgnoreCase("FactoryTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryTypeName = FValue.trim();
			}
			else
				FactoryTypeName = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMFactoryTypeSchema other = (LMFactoryTypeSchema)otherObject;
		return
			FactoryType.equals(other.getFactoryType())
			&& FactoryTypeName.equals(other.getFactoryTypeName())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("FactoryType") ) {
			return 0;
		}
		if( strFieldName.equals("FactoryTypeName") ) {
			return 1;
		}
		if( strFieldName.equals("Remark") ) {
			return 2;
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
				strFieldName = "FactoryType";
				break;
			case 1:
				strFieldName = "FactoryTypeName";
				break;
			case 2:
				strFieldName = "Remark";
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
		if( strFieldName.equals("FactoryType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
