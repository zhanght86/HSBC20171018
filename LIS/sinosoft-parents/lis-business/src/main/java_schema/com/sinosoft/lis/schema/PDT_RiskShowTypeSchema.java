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
import com.sinosoft.lis.db.PDT_RiskShowTypeDB;

/*
 * <p>ClassName: PDT_RiskShowTypeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 险种测试功能
 */
public class PDT_RiskShowTypeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PDT_RiskShowTypeSchema.class);

	// @Field
	/** 模板id */
	private String TemplateID;
	/** 显示类型 */
	private String ShowType;
	/** 显示类型关联 */
	private String ShowTypeRela;
	/** 显示方式 */
	private String ShowMethod;
	/** 显示sql */
	private String ShowSQL;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PDT_RiskShowTypeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TemplateID";
		pk[1] = "ShowType";

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
		PDT_RiskShowTypeSchema cloned = (PDT_RiskShowTypeSchema)super.clone();
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
	* Risk 险种<p>
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
	* 1-查询产品模型表SQL
	*/
	public String getShowTypeRela()
	{
		return ShowTypeRela;
	}
	public void setShowTypeRela(String aShowTypeRela)
	{
		if(aShowTypeRela!=null && aShowTypeRela.length()>1)
			throw new IllegalArgumentException("显示类型关联ShowTypeRela值"+aShowTypeRela+"的长度"+aShowTypeRela.length()+"大于最大值1");
		ShowTypeRela = aShowTypeRela;
	}
	/**
	* 0-字段式<p>
	* 1-Multiline
	*/
	public String getShowMethod()
	{
		return ShowMethod;
	}
	public void setShowMethod(String aShowMethod)
	{
		if(aShowMethod!=null && aShowMethod.length()>1)
			throw new IllegalArgumentException("显示方式ShowMethod值"+aShowMethod+"的长度"+aShowMethod.length()+"大于最大值1");
		ShowMethod = aShowMethod;
	}
	public String getShowSQL()
	{
		return ShowSQL;
	}
	public void setShowSQL(String aShowSQL)
	{
		if(aShowSQL!=null && aShowSQL.length()>4000)
			throw new IllegalArgumentException("显示sqlShowSQL值"+aShowSQL+"的长度"+aShowSQL.length()+"大于最大值4000");
		ShowSQL = aShowSQL;
	}

	/**
	* 使用另外一个 PDT_RiskShowTypeSchema 对象给 Schema 赋值
	* @param: aPDT_RiskShowTypeSchema PDT_RiskShowTypeSchema
	**/
	public void setSchema(PDT_RiskShowTypeSchema aPDT_RiskShowTypeSchema)
	{
		this.TemplateID = aPDT_RiskShowTypeSchema.getTemplateID();
		this.ShowType = aPDT_RiskShowTypeSchema.getShowType();
		this.ShowTypeRela = aPDT_RiskShowTypeSchema.getShowTypeRela();
		this.ShowMethod = aPDT_RiskShowTypeSchema.getShowMethod();
		this.ShowSQL = aPDT_RiskShowTypeSchema.getShowSQL();
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

			if( rs.getString("ShowTypeRela") == null )
				this.ShowTypeRela = null;
			else
				this.ShowTypeRela = rs.getString("ShowTypeRela").trim();

			if( rs.getString("ShowMethod") == null )
				this.ShowMethod = null;
			else
				this.ShowMethod = rs.getString("ShowMethod").trim();

			if( rs.getString("ShowSQL") == null )
				this.ShowSQL = null;
			else
				this.ShowSQL = rs.getString("ShowSQL").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PDT_RiskShowType表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDT_RiskShowTypeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PDT_RiskShowTypeSchema getSchema()
	{
		PDT_RiskShowTypeSchema aPDT_RiskShowTypeSchema = new PDT_RiskShowTypeSchema();
		aPDT_RiskShowTypeSchema.setSchema(this);
		return aPDT_RiskShowTypeSchema;
	}

	public PDT_RiskShowTypeDB getDB()
	{
		PDT_RiskShowTypeDB aDBOper = new PDT_RiskShowTypeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskShowType描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TemplateID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowTypeRela)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowMethod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowSQL));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskShowType>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TemplateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ShowType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ShowTypeRela = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ShowMethod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ShowSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDT_RiskShowTypeSchema";
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
		if (FCode.equalsIgnoreCase("ShowTypeRela"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowTypeRela));
		}
		if (FCode.equalsIgnoreCase("ShowMethod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowMethod));
		}
		if (FCode.equalsIgnoreCase("ShowSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowSQL));
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
				strFieldValue = StrTool.GBKToUnicode(ShowTypeRela);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ShowMethod);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ShowSQL);
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
		if (FCode.equalsIgnoreCase("ShowTypeRela"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowTypeRela = FValue.trim();
			}
			else
				ShowTypeRela = null;
		}
		if (FCode.equalsIgnoreCase("ShowMethod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowMethod = FValue.trim();
			}
			else
				ShowMethod = null;
		}
		if (FCode.equalsIgnoreCase("ShowSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowSQL = FValue.trim();
			}
			else
				ShowSQL = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PDT_RiskShowTypeSchema other = (PDT_RiskShowTypeSchema)otherObject;
		return
			TemplateID.equals(other.getTemplateID())
			&& ShowType.equals(other.getShowType())
			&& ShowTypeRela.equals(other.getShowTypeRela())
			&& ShowMethod.equals(other.getShowMethod())
			&& ShowSQL.equals(other.getShowSQL());
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
		if( strFieldName.equals("ShowTypeRela") ) {
			return 2;
		}
		if( strFieldName.equals("ShowMethod") ) {
			return 3;
		}
		if( strFieldName.equals("ShowSQL") ) {
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
				strFieldName = "TemplateID";
				break;
			case 1:
				strFieldName = "ShowType";
				break;
			case 2:
				strFieldName = "ShowTypeRela";
				break;
			case 3:
				strFieldName = "ShowMethod";
				break;
			case 4:
				strFieldName = "ShowSQL";
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
		if( strFieldName.equals("ShowTypeRela") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowMethod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowSQL") ) {
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
