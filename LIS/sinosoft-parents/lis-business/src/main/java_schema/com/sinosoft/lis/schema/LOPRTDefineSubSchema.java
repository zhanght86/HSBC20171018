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
import com.sinosoft.lis.db.LOPRTDefineSubDB;

/*
 * <p>ClassName: LOPRTDefineSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOPRTDefineSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOPRTDefineSubSchema.class);

	// @Field
	/** 业务类型 */
	private String Code;
	/** 关联属性 */
	private String RelaAttr;
	/** 属性定义sql */
	private String RelAttrSQL;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOPRTDefineSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "Code";
		pk[1] = "RelaAttr";

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
		LOPRTDefineSubSchema cloned = (LOPRTDefineSubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	/**
	* 定义该打印类型下可变属性
	*/
	public String getRelaAttr()
	{
		return RelaAttr;
	}
	public void setRelaAttr(String aRelaAttr)
	{
		RelaAttr = aRelaAttr;
	}
	/**
	* 属性的取值SQL
	*/
	public String getRelAttrSQL()
	{
		return RelAttrSQL;
	}
	public void setRelAttrSQL(String aRelAttrSQL)
	{
		RelAttrSQL = aRelAttrSQL;
	}

	/**
	* 使用另外一个 LOPRTDefineSubSchema 对象给 Schema 赋值
	* @param: aLOPRTDefineSubSchema LOPRTDefineSubSchema
	**/
	public void setSchema(LOPRTDefineSubSchema aLOPRTDefineSubSchema)
	{
		this.Code = aLOPRTDefineSubSchema.getCode();
		this.RelaAttr = aLOPRTDefineSubSchema.getRelaAttr();
		this.RelAttrSQL = aLOPRTDefineSubSchema.getRelAttrSQL();
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
			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("RelaAttr") == null )
				this.RelaAttr = null;
			else
				this.RelaAttr = rs.getString("RelaAttr").trim();

			if( rs.getString("RelAttrSQL") == null )
				this.RelAttrSQL = null;
			else
				this.RelAttrSQL = rs.getString("RelAttrSQL").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOPRTDefineSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPRTDefineSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOPRTDefineSubSchema getSchema()
	{
		LOPRTDefineSubSchema aLOPRTDefineSubSchema = new LOPRTDefineSubSchema();
		aLOPRTDefineSubSchema.setSchema(this);
		return aLOPRTDefineSubSchema;
	}

	public LOPRTDefineSubDB getDB()
	{
		LOPRTDefineSubDB aDBOper = new LOPRTDefineSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPRTDefineSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelAttrSQL));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPRTDefineSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RelaAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RelAttrSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPRTDefineSubSchema";
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
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("RelaAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaAttr));
		}
		if (FCode.equalsIgnoreCase("RelAttrSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelAttrSQL));
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
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RelaAttr);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RelAttrSQL);
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

		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("RelaAttr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaAttr = FValue.trim();
			}
			else
				RelaAttr = null;
		}
		if (FCode.equalsIgnoreCase("RelAttrSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelAttrSQL = FValue.trim();
			}
			else
				RelAttrSQL = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOPRTDefineSubSchema other = (LOPRTDefineSubSchema)otherObject;
		return
			Code.equals(other.getCode())
			&& RelaAttr.equals(other.getRelaAttr())
			&& RelAttrSQL.equals(other.getRelAttrSQL());
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
		if( strFieldName.equals("Code") ) {
			return 0;
		}
		if( strFieldName.equals("RelaAttr") ) {
			return 1;
		}
		if( strFieldName.equals("RelAttrSQL") ) {
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
				strFieldName = "Code";
				break;
			case 1:
				strFieldName = "RelaAttr";
				break;
			case 2:
				strFieldName = "RelAttrSQL";
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
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelAttrSQL") ) {
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
