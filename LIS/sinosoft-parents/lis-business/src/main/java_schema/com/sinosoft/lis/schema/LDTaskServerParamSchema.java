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
import com.sinosoft.lis.db.LDTaskServerParamDB;

/*
 * <p>ClassName: LDTaskServerParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 任务服务节点参数表
 */
public class LDTaskServerParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDTaskServerParamSchema.class);

	// @Field
	/** 服务器ip */
	private String ServerIP;
	/** 服务器端口 */
	private String ServerPort;
	/** 参数名称 */
	private String ParamName;
	/** 参数值 */
	private String ParamValue;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDTaskServerParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ServerIP";
		pk[1] = "ServerPort";
		pk[2] = "ParamName";

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
		LDTaskServerParamSchema cloned = (LDTaskServerParamSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getServerIP()
	{
		return ServerIP;
	}
	public void setServerIP(String aServerIP)
	{
		if(aServerIP!=null && aServerIP.length()>20)
			throw new IllegalArgumentException("服务器ipServerIP值"+aServerIP+"的长度"+aServerIP.length()+"大于最大值20");
		ServerIP = aServerIP;
	}
	/**
	* 0: 未启用<p>
	* 1: 启用<p>
	* 未启用的任务无效
	*/
	public String getServerPort()
	{
		return ServerPort;
	}
	public void setServerPort(String aServerPort)
	{
		if(aServerPort!=null && aServerPort.length()>20)
			throw new IllegalArgumentException("服务器端口ServerPort值"+aServerPort+"的长度"+aServerPort.length()+"大于最大值20");
		ServerPort = aServerPort;
	}
	public String getParamName()
	{
		return ParamName;
	}
	public void setParamName(String aParamName)
	{
		if(aParamName!=null && aParamName.length()>20)
			throw new IllegalArgumentException("参数名称ParamName值"+aParamName+"的长度"+aParamName.length()+"大于最大值20");
		ParamName = aParamName;
	}
	public String getParamValue()
	{
		return ParamValue;
	}
	public void setParamValue(String aParamValue)
	{
		if(aParamValue!=null && aParamValue.length()>100)
			throw new IllegalArgumentException("参数值ParamValue值"+aParamValue+"的长度"+aParamValue.length()+"大于最大值100");
		ParamValue = aParamValue;
	}

	/**
	* 使用另外一个 LDTaskServerParamSchema 对象给 Schema 赋值
	* @param: aLDTaskServerParamSchema LDTaskServerParamSchema
	**/
	public void setSchema(LDTaskServerParamSchema aLDTaskServerParamSchema)
	{
		this.ServerIP = aLDTaskServerParamSchema.getServerIP();
		this.ServerPort = aLDTaskServerParamSchema.getServerPort();
		this.ParamName = aLDTaskServerParamSchema.getParamName();
		this.ParamValue = aLDTaskServerParamSchema.getParamValue();
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
			if( rs.getString("ServerIP") == null )
				this.ServerIP = null;
			else
				this.ServerIP = rs.getString("ServerIP").trim();

			if( rs.getString("ServerPort") == null )
				this.ServerPort = null;
			else
				this.ServerPort = rs.getString("ServerPort").trim();

			if( rs.getString("ParamName") == null )
				this.ParamName = null;
			else
				this.ParamName = rs.getString("ParamName").trim();

			if( rs.getString("ParamValue") == null )
				this.ParamValue = null;
			else
				this.ParamValue = rs.getString("ParamValue").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDTaskServerParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskServerParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDTaskServerParamSchema getSchema()
	{
		LDTaskServerParamSchema aLDTaskServerParamSchema = new LDTaskServerParamSchema();
		aLDTaskServerParamSchema.setSchema(this);
		return aLDTaskServerParamSchema;
	}

	public LDTaskServerParamDB getDB()
	{
		LDTaskServerParamDB aDBOper = new LDTaskServerParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskServerParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ServerIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerPort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamValue));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDTaskServerParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ServerIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ServerPort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ParamName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ParamValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDTaskServerParamSchema";
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
		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerIP));
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerPort));
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamName));
		}
		if (FCode.equalsIgnoreCase("ParamValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamValue));
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
				strFieldValue = StrTool.GBKToUnicode(ServerIP);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ServerPort);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ParamName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ParamValue);
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

		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerIP = FValue.trim();
			}
			else
				ServerIP = null;
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerPort = FValue.trim();
			}
			else
				ServerPort = null;
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamName = FValue.trim();
			}
			else
				ParamName = null;
		}
		if (FCode.equalsIgnoreCase("ParamValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamValue = FValue.trim();
			}
			else
				ParamValue = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDTaskServerParamSchema other = (LDTaskServerParamSchema)otherObject;
		return
			ServerIP.equals(other.getServerIP())
			&& ServerPort.equals(other.getServerPort())
			&& ParamName.equals(other.getParamName())
			&& ParamValue.equals(other.getParamValue());
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
		if( strFieldName.equals("ServerIP") ) {
			return 0;
		}
		if( strFieldName.equals("ServerPort") ) {
			return 1;
		}
		if( strFieldName.equals("ParamName") ) {
			return 2;
		}
		if( strFieldName.equals("ParamValue") ) {
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
				strFieldName = "ServerIP";
				break;
			case 1:
				strFieldName = "ServerPort";
				break;
			case 2:
				strFieldName = "ParamName";
				break;
			case 3:
				strFieldName = "ParamValue";
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
		if( strFieldName.equals("ServerIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerPort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamValue") ) {
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
