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
import com.sinosoft.lis.db.ES_BATCHNODB;

/*
 * <p>ClassName: ES_BATCHNOSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: Es_Customer
 * @CreateDate：2016-03-04
 */
public class ES_BATCHNOSchema implements Schema, Cloneable
{
	// @Field
	/** Batchno */
	private String batchno;
	/** Pinbatch */
	private String pinbatch;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_BATCHNOSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "batchno";

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
                ES_BATCHNOSchema cloned = (ES_BATCHNOSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getbatchno()
	{
		return batchno;
	}
	public void setbatchno(String abatchno)
	{
		batchno = abatchno;
	}
	public String getpinbatch()
	{
		return pinbatch;
	}
	public void setpinbatch(String apinbatch)
	{
		pinbatch = apinbatch;
	}

	/**
	* 使用另外一个 ES_BATCHNOSchema 对象给 Schema 赋值
	* @param: aES_BATCHNOSchema ES_BATCHNOSchema
	**/
	public void setSchema(ES_BATCHNOSchema aES_BATCHNOSchema)
	{
		this.batchno = aES_BATCHNOSchema.getbatchno();
		this.pinbatch = aES_BATCHNOSchema.getpinbatch();
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
			if( rs.getString("batchno") == null )
				this.batchno = null;
			else
				this.batchno = rs.getString("batchno").trim();

			if( rs.getString("pinbatch") == null )
				this.pinbatch = null;
			else
				this.pinbatch = rs.getString("pinbatch").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的ES_BATCHNO表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_BATCHNOSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_BATCHNOSchema getSchema()
	{
		ES_BATCHNOSchema aES_BATCHNOSchema = new ES_BATCHNOSchema();
		aES_BATCHNOSchema.setSchema(this);
		return aES_BATCHNOSchema;
	}

	public ES_BATCHNODB getDB()
	{
		ES_BATCHNODB aDBOper = new ES_BATCHNODB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_BATCHNO描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(batchno)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(pinbatch));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_BATCHNO>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			batchno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			pinbatch = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_BATCHNOSchema";
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
		if (FCode.equalsIgnoreCase("batchno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(batchno));
		}
		if (FCode.equalsIgnoreCase("pinbatch"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(pinbatch));
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
				strFieldValue = StrTool.GBKToUnicode(batchno);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(pinbatch);
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

		if (FCode.equalsIgnoreCase("batchno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				batchno = FValue.trim();
			}
			else
				batchno = null;
		}
		if (FCode.equalsIgnoreCase("pinbatch"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				pinbatch = FValue.trim();
			}
			else
				pinbatch = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_BATCHNOSchema other = (ES_BATCHNOSchema)otherObject;
		return
			batchno.equals(other.getbatchno())
			&& pinbatch.equals(other.getpinbatch());
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
		if( strFieldName.equals("batchno") ) {
			return 0;
		}
		if( strFieldName.equals("pinbatch") ) {
			return 1;
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
				strFieldName = "batchno";
				break;
			case 1:
				strFieldName = "pinbatch";
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
		if( strFieldName.equals("batchno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("pinbatch") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
