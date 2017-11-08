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
import com.sinosoft.lis.db.LDComGrpToComDB;

/*
 * <p>ClassName: LDComGrpToComSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LDComGrpToComSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDComGrpToComSchema.class);
	// @Field
	/** 机构分组编码 */
	private String ComGrpCode;
	/** 机构编码 */
	private String ComCode;
	/** 机构编码类型 */
	private String ComCodeType;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDComGrpToComSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ComGrpCode";
		pk[1] = "ComCode";

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
		LDComGrpToComSchema cloned = (LDComGrpToComSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getComGrpCode()
	{
		return ComGrpCode;
	}
	public void setComGrpCode(String aComGrpCode)
	{
		if(aComGrpCode!=null && aComGrpCode.length()>6)
			throw new IllegalArgumentException("机构分组编码ComGrpCode值"+aComGrpCode+"的长度"+aComGrpCode.length()+"大于最大值6");
		ComGrpCode = aComGrpCode;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>8)
			throw new IllegalArgumentException("机构编码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值8");
		ComCode = aComCode;
	}
	/**
	* 01--总公司 02--分公司 03--中心支公司
	*/
	public String getComCodeType()
	{
		return ComCodeType;
	}
	public void setComCodeType(String aComCodeType)
	{
		if(aComCodeType!=null && aComCodeType.length()>2)
			throw new IllegalArgumentException("机构编码类型ComCodeType值"+aComCodeType+"的长度"+aComCodeType.length()+"大于最大值2");
		ComCodeType = aComCodeType;
	}

	/**
	* 使用另外一个 LDComGrpToComSchema 对象给 Schema 赋值
	* @param: aLDComGrpToComSchema LDComGrpToComSchema
	**/
	public void setSchema(LDComGrpToComSchema aLDComGrpToComSchema)
	{
		this.ComGrpCode = aLDComGrpToComSchema.getComGrpCode();
		this.ComCode = aLDComGrpToComSchema.getComCode();
		this.ComCodeType = aLDComGrpToComSchema.getComCodeType();
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
			if( rs.getString("ComGrpCode") == null )
				this.ComGrpCode = null;
			else
				this.ComGrpCode = rs.getString("ComGrpCode").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ComCodeType") == null )
				this.ComCodeType = null;
			else
				this.ComCodeType = rs.getString("ComCodeType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDComGrpToCom表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComGrpToComSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDComGrpToComSchema getSchema()
	{
		LDComGrpToComSchema aLDComGrpToComSchema = new LDComGrpToComSchema();
		aLDComGrpToComSchema.setSchema(this);
		return aLDComGrpToComSchema;
	}

	public LDComGrpToComDB getDB()
	{
		LDComGrpToComDB aDBOper = new LDComGrpToComDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDComGrpToCom描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ComGrpCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCodeType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDComGrpToCom>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ComGrpCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ComCodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComGrpToComSchema";
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
		if (FCode.equalsIgnoreCase("ComGrpCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComGrpCode));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ComCodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCodeType));
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
				strFieldValue = StrTool.GBKToUnicode(ComGrpCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ComCodeType);
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

		if (FCode.equalsIgnoreCase("ComGrpCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComGrpCode = FValue.trim();
			}
			else
				ComGrpCode = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("ComCodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCodeType = FValue.trim();
			}
			else
				ComCodeType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDComGrpToComSchema other = (LDComGrpToComSchema)otherObject;
		return
			ComGrpCode.equals(other.getComGrpCode())
			&& ComCode.equals(other.getComCode())
			&& ComCodeType.equals(other.getComCodeType());
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
		if( strFieldName.equals("ComGrpCode") ) {
			return 0;
		}
		if( strFieldName.equals("ComCode") ) {
			return 1;
		}
		if( strFieldName.equals("ComCodeType") ) {
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
				strFieldName = "ComGrpCode";
				break;
			case 1:
				strFieldName = "ComCode";
				break;
			case 2:
				strFieldName = "ComCodeType";
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
		if( strFieldName.equals("ComGrpCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCodeType") ) {
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
