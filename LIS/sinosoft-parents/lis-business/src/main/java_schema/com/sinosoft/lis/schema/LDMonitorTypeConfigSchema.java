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
import com.sinosoft.lis.db.LDMonitorTypeConfigDB;

/*
 * <p>ClassName: LDMonitorTypeConfigSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 自动监控平台
 */
public class LDMonitorTypeConfigSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMonitorTypeConfigSchema.class);

	// @Field
	/** 监控类型编码 */
	private String MonitorTypeCode;
	/** 监控类型名称 */
	private String MonitorTypeName;
	/** 是否启用 */
	private String State;
	/** 数据抽取算法类 */
	private String SaveDataClass;
	/** 数据展现算法类 */
	private String ShowDataClass;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMonitorTypeConfigSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "MonitorTypeCode";

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
		LDMonitorTypeConfigSchema cloned = (LDMonitorTypeConfigSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMonitorTypeCode()
	{
		return MonitorTypeCode;
	}
	public void setMonitorTypeCode(String aMonitorTypeCode)
	{
		MonitorTypeCode = aMonitorTypeCode;
	}
	public String getMonitorTypeName()
	{
		return MonitorTypeName;
	}
	public void setMonitorTypeName(String aMonitorTypeName)
	{
		MonitorTypeName = aMonitorTypeName;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getSaveDataClass()
	{
		return SaveDataClass;
	}
	public void setSaveDataClass(String aSaveDataClass)
	{
		SaveDataClass = aSaveDataClass;
	}
	public String getShowDataClass()
	{
		return ShowDataClass;
	}
	public void setShowDataClass(String aShowDataClass)
	{
		ShowDataClass = aShowDataClass;
	}

	/**
	* 使用另外一个 LDMonitorTypeConfigSchema 对象给 Schema 赋值
	* @param: aLDMonitorTypeConfigSchema LDMonitorTypeConfigSchema
	**/
	public void setSchema(LDMonitorTypeConfigSchema aLDMonitorTypeConfigSchema)
	{
		this.MonitorTypeCode = aLDMonitorTypeConfigSchema.getMonitorTypeCode();
		this.MonitorTypeName = aLDMonitorTypeConfigSchema.getMonitorTypeName();
		this.State = aLDMonitorTypeConfigSchema.getState();
		this.SaveDataClass = aLDMonitorTypeConfigSchema.getSaveDataClass();
		this.ShowDataClass = aLDMonitorTypeConfigSchema.getShowDataClass();
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
			if( rs.getString("MonitorTypeCode") == null )
				this.MonitorTypeCode = null;
			else
				this.MonitorTypeCode = rs.getString("MonitorTypeCode").trim();

			if( rs.getString("MonitorTypeName") == null )
				this.MonitorTypeName = null;
			else
				this.MonitorTypeName = rs.getString("MonitorTypeName").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("SaveDataClass") == null )
				this.SaveDataClass = null;
			else
				this.SaveDataClass = rs.getString("SaveDataClass").trim();

			if( rs.getString("ShowDataClass") == null )
				this.ShowDataClass = null;
			else
				this.ShowDataClass = rs.getString("ShowDataClass").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMonitorTypeConfig表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMonitorTypeConfigSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMonitorTypeConfigSchema getSchema()
	{
		LDMonitorTypeConfigSchema aLDMonitorTypeConfigSchema = new LDMonitorTypeConfigSchema();
		aLDMonitorTypeConfigSchema.setSchema(this);
		return aLDMonitorTypeConfigSchema;
	}

	public LDMonitorTypeConfigDB getDB()
	{
		LDMonitorTypeConfigDB aDBOper = new LDMonitorTypeConfigDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMonitorTypeConfig描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MonitorTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MonitorTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaveDataClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowDataClass));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMonitorTypeConfig>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MonitorTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MonitorTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SaveDataClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ShowDataClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMonitorTypeConfigSchema";
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
		if (FCode.equalsIgnoreCase("MonitorTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorTypeCode));
		}
		if (FCode.equalsIgnoreCase("MonitorTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorTypeName));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("SaveDataClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaveDataClass));
		}
		if (FCode.equalsIgnoreCase("ShowDataClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowDataClass));
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
				strFieldValue = StrTool.GBKToUnicode(MonitorTypeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MonitorTypeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SaveDataClass);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ShowDataClass);
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

		if (FCode.equalsIgnoreCase("MonitorTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorTypeCode = FValue.trim();
			}
			else
				MonitorTypeCode = null;
		}
		if (FCode.equalsIgnoreCase("MonitorTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorTypeName = FValue.trim();
			}
			else
				MonitorTypeName = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("SaveDataClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaveDataClass = FValue.trim();
			}
			else
				SaveDataClass = null;
		}
		if (FCode.equalsIgnoreCase("ShowDataClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowDataClass = FValue.trim();
			}
			else
				ShowDataClass = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMonitorTypeConfigSchema other = (LDMonitorTypeConfigSchema)otherObject;
		return
			MonitorTypeCode.equals(other.getMonitorTypeCode())
			&& MonitorTypeName.equals(other.getMonitorTypeName())
			&& State.equals(other.getState())
			&& SaveDataClass.equals(other.getSaveDataClass())
			&& ShowDataClass.equals(other.getShowDataClass());
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
		if( strFieldName.equals("MonitorTypeCode") ) {
			return 0;
		}
		if( strFieldName.equals("MonitorTypeName") ) {
			return 1;
		}
		if( strFieldName.equals("State") ) {
			return 2;
		}
		if( strFieldName.equals("SaveDataClass") ) {
			return 3;
		}
		if( strFieldName.equals("ShowDataClass") ) {
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
				strFieldName = "MonitorTypeCode";
				break;
			case 1:
				strFieldName = "MonitorTypeName";
				break;
			case 2:
				strFieldName = "State";
				break;
			case 3:
				strFieldName = "SaveDataClass";
				break;
			case 4:
				strFieldName = "ShowDataClass";
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
		if( strFieldName.equals("MonitorTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MonitorTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaveDataClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowDataClass") ) {
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
