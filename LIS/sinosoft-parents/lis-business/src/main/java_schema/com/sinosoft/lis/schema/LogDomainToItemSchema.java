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
import com.sinosoft.lis.db.LogDomainToItemDB;

/*
 * <p>ClassName: LogDomainToItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 日志
 */
public class LogDomainToItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LogDomainToItemSchema.class);

	// @Field
	/** 日志主题id */
	private String SubjectID;
	/** 日志监控点id */
	private String ItemID;
	/** 控制点关键号码类型 */
	private String KeyType;
	/** 日志控制开关 */
	private String Switch;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LogDomainToItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SubjectID";
		pk[1] = "ItemID";

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
		LogDomainToItemSchema cloned = (LogDomainToItemSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSubjectID()
	{
		return SubjectID;
	}
	public void setSubjectID(String aSubjectID)
	{
		if(aSubjectID!=null && aSubjectID.length()>20)
			throw new IllegalArgumentException("日志主题idSubjectID值"+aSubjectID+"的长度"+aSubjectID.length()+"大于最大值20");
		SubjectID = aSubjectID;
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
	public String getKeyType()
	{
		return KeyType;
	}
	public void setKeyType(String aKeyType)
	{
		if(aKeyType!=null && aKeyType.length()>10)
			throw new IllegalArgumentException("控制点关键号码类型KeyType值"+aKeyType+"的长度"+aKeyType.length()+"大于最大值10");
		KeyType = aKeyType;
	}
	public String getSwitch()
	{
		return Switch;
	}
	public void setSwitch(String aSwitch)
	{
		if(aSwitch!=null && aSwitch.length()>1)
			throw new IllegalArgumentException("日志控制开关Switch值"+aSwitch+"的长度"+aSwitch.length()+"大于最大值1");
		Switch = aSwitch;
	}

	/**
	* 使用另外一个 LogDomainToItemSchema 对象给 Schema 赋值
	* @param: aLogDomainToItemSchema LogDomainToItemSchema
	**/
	public void setSchema(LogDomainToItemSchema aLogDomainToItemSchema)
	{
		this.SubjectID = aLogDomainToItemSchema.getSubjectID();
		this.ItemID = aLogDomainToItemSchema.getItemID();
		this.KeyType = aLogDomainToItemSchema.getKeyType();
		this.Switch = aLogDomainToItemSchema.getSwitch();
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
			if( rs.getString("SubjectID") == null )
				this.SubjectID = null;
			else
				this.SubjectID = rs.getString("SubjectID").trim();

			if( rs.getString("ItemID") == null )
				this.ItemID = null;
			else
				this.ItemID = rs.getString("ItemID").trim();

			if( rs.getString("KeyType") == null )
				this.KeyType = null;
			else
				this.KeyType = rs.getString("KeyType").trim();

			if( rs.getString("Switch") == null )
				this.Switch = null;
			else
				this.Switch = rs.getString("Switch").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LogDomainToItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogDomainToItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LogDomainToItemSchema getSchema()
	{
		LogDomainToItemSchema aLogDomainToItemSchema = new LogDomainToItemSchema();
		aLogDomainToItemSchema.setSchema(this);
		return aLogDomainToItemSchema;
	}

	public LogDomainToItemDB getDB()
	{
		LogDomainToItemDB aDBOper = new LogDomainToItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogDomainToItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubjectID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Switch));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogDomainToItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubjectID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			KeyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Switch = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogDomainToItemSchema";
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
		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectID));
		}
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemID));
		}
		if (FCode.equalsIgnoreCase("KeyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyType));
		}
		if (FCode.equalsIgnoreCase("Switch"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Switch));
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
				strFieldValue = StrTool.GBKToUnicode(SubjectID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ItemID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(KeyType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Switch);
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

		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectID = FValue.trim();
			}
			else
				SubjectID = null;
		}
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemID = FValue.trim();
			}
			else
				ItemID = null;
		}
		if (FCode.equalsIgnoreCase("KeyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyType = FValue.trim();
			}
			else
				KeyType = null;
		}
		if (FCode.equalsIgnoreCase("Switch"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Switch = FValue.trim();
			}
			else
				Switch = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LogDomainToItemSchema other = (LogDomainToItemSchema)otherObject;
		return
			SubjectID.equals(other.getSubjectID())
			&& ItemID.equals(other.getItemID())
			&& KeyType.equals(other.getKeyType())
			&& Switch.equals(other.getSwitch());
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
		if( strFieldName.equals("SubjectID") ) {
			return 0;
		}
		if( strFieldName.equals("ItemID") ) {
			return 1;
		}
		if( strFieldName.equals("KeyType") ) {
			return 2;
		}
		if( strFieldName.equals("Switch") ) {
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
				strFieldName = "SubjectID";
				break;
			case 1:
				strFieldName = "ItemID";
				break;
			case 2:
				strFieldName = "KeyType";
				break;
			case 3:
				strFieldName = "Switch";
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
		if( strFieldName.equals("SubjectID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Switch") ) {
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
