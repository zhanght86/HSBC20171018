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
import com.sinosoft.lis.db.LogItemDB;

/*
 * <p>ClassName: LogItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 日志
 */
public class LogItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LogItemSchema.class);

	// @Field
	/** 日志监控点id */
	private String ItemID;
	/** 日志控制点描述 */
	private String ItemDes;
	/** 日志辅助信息 */
	private String Remark;
	/** 日志控制语法 */
	private String Grammar;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LogItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ItemID";

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
		LogItemSchema cloned = (LogItemSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getItemID()
	{
		return ItemID;
	}
	public void setItemID(String aItemID)
	{
		if(aItemID!=null && aItemID.length()>20)
			throw new IllegalArgumentException("日志监控点idItemID值"+aItemID+"的长度"+aItemID.length()+"大于最大值20");
		ItemID = aItemID;
	}
	/**
	* NB,POS,CLM等
	*/
	public String getItemDes()
	{
		return ItemDes;
	}
	public void setItemDes(String aItemDes)
	{
		if(aItemDes!=null && aItemDes.length()>100)
			throw new IllegalArgumentException("日志控制点描述ItemDes值"+aItemDes+"的长度"+aItemDes.length()+"大于最大值100");
		ItemDes = aItemDes;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>100)
			throw new IllegalArgumentException("日志辅助信息Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值100");
		Remark = aRemark;
	}
	public String getGrammar()
	{
		return Grammar;
	}
	public void setGrammar(String aGrammar)
	{
		if(aGrammar!=null && aGrammar.length()>100)
			throw new IllegalArgumentException("日志控制语法Grammar值"+aGrammar+"的长度"+aGrammar.length()+"大于最大值100");
		Grammar = aGrammar;
	}

	/**
	* 使用另外一个 LogItemSchema 对象给 Schema 赋值
	* @param: aLogItemSchema LogItemSchema
	**/
	public void setSchema(LogItemSchema aLogItemSchema)
	{
		this.ItemID = aLogItemSchema.getItemID();
		this.ItemDes = aLogItemSchema.getItemDes();
		this.Remark = aLogItemSchema.getRemark();
		this.Grammar = aLogItemSchema.getGrammar();
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
			if( rs.getString("ItemID") == null )
				this.ItemID = null;
			else
				this.ItemID = rs.getString("ItemID").trim();

			if( rs.getString("ItemDes") == null )
				this.ItemDes = null;
			else
				this.ItemDes = rs.getString("ItemDes").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Grammar") == null )
				this.Grammar = null;
			else
				this.Grammar = rs.getString("Grammar").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LogItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LogItemSchema getSchema()
	{
		LogItemSchema aLogItemSchema = new LogItemSchema();
		aLogItemSchema.setSchema(this);
		return aLogItemSchema;
	}

	public LogItemDB getDB()
	{
		LogItemDB aDBOper = new LogItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ItemID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Grammar));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ItemDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Grammar = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogItemSchema";
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
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemID));
		}
		if (FCode.equalsIgnoreCase("ItemDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemDes));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Grammar"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Grammar));
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
				strFieldValue = StrTool.GBKToUnicode(ItemID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ItemDes);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Grammar);
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

		if (FCode.equalsIgnoreCase("ItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemID = FValue.trim();
			}
			else
				ItemID = null;
		}
		if (FCode.equalsIgnoreCase("ItemDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemDes = FValue.trim();
			}
			else
				ItemDes = null;
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
		if (FCode.equalsIgnoreCase("Grammar"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Grammar = FValue.trim();
			}
			else
				Grammar = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LogItemSchema other = (LogItemSchema)otherObject;
		return
			ItemID.equals(other.getItemID())
			&& ItemDes.equals(other.getItemDes())
			&& Remark.equals(other.getRemark())
			&& Grammar.equals(other.getGrammar());
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
		if( strFieldName.equals("ItemID") ) {
			return 0;
		}
		if( strFieldName.equals("ItemDes") ) {
			return 1;
		}
		if( strFieldName.equals("Remark") ) {
			return 2;
		}
		if( strFieldName.equals("Grammar") ) {
			return 3;
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
				strFieldName = "ItemID";
				break;
			case 1:
				strFieldName = "ItemDes";
				break;
			case 2:
				strFieldName = "Remark";
				break;
			case 3:
				strFieldName = "Grammar";
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
		if( strFieldName.equals("ItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Grammar") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
