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
import com.sinosoft.lis.db.ES_SERVER_INFODB;

/*
 * <p>ClassName: ES_SERVER_INFOSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_SERVER_INFOSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_SERVER_INFOSchema.class);

	// @Field
	/** 服务器名称 */
	private String HostName;
	/** 服务器ip */
	private String ServerIP;
	/** Ftp登陆用户 */
	private String LoginNameFTP;
	/** Ftp登陆密码 */
	private String LoginPwdFTP;
	/** Ftp路径 */
	private String PicPathFTP;
	/** 服务器状态 */
	private String ServerFlag;
	/** Http访问ip与端口 */
	private String ServerPort;
	/** Http路径 */
	private String PicPath;
	/** 服务器物理路径 */
	private String ServerBasePath;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_SERVER_INFOSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "HostName";

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
		ES_SERVER_INFOSchema cloned = (ES_SERVER_INFOSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 服务器名
	*/
	public String getHostName()
	{
		return HostName;
	}
	public void setHostName(String aHostName)
	{
		HostName = aHostName;
	}
	public String getServerIP()
	{
		return ServerIP;
	}
	public void setServerIP(String aServerIP)
	{
		ServerIP = aServerIP;
	}
	public String getLoginNameFTP()
	{
		return LoginNameFTP;
	}
	public void setLoginNameFTP(String aLoginNameFTP)
	{
		LoginNameFTP = aLoginNameFTP;
	}
	public String getLoginPwdFTP()
	{
		return LoginPwdFTP;
	}
	public void setLoginPwdFTP(String aLoginPwdFTP)
	{
		LoginPwdFTP = aLoginPwdFTP;
	}
	/**
	* FTP上载相对路径
	*/
	public String getPicPathFTP()
	{
		return PicPathFTP;
	}
	public void setPicPathFTP(String aPicPathFTP)
	{
		PicPathFTP = aPicPathFTP;
	}
	/**
	* 暂时无用
	*/
	public String getServerFlag()
	{
		return ServerFlag;
	}
	public void setServerFlag(String aServerFlag)
	{
		ServerFlag = aServerFlag;
	}
	/**
	* 例：127.0.0.1:7001
	*/
	public String getServerPort()
	{
		return ServerPort;
	}
	public void setServerPort(String aServerPort)
	{
		ServerPort = aServerPort;
	}
	/**
	* HTTP上载相对路径
	*/
	public String getPicPath()
	{
		return PicPath;
	}
	public void setPicPath(String aPicPath)
	{
		PicPath = aPicPath;
	}
	public String getServerBasePath()
	{
		return ServerBasePath;
	}
	public void setServerBasePath(String aServerBasePath)
	{
		ServerBasePath = aServerBasePath;
	}

	/**
	* 使用另外一个 ES_SERVER_INFOSchema 对象给 Schema 赋值
	* @param: aES_SERVER_INFOSchema ES_SERVER_INFOSchema
	**/
	public void setSchema(ES_SERVER_INFOSchema aES_SERVER_INFOSchema)
	{
		this.HostName = aES_SERVER_INFOSchema.getHostName();
		this.ServerIP = aES_SERVER_INFOSchema.getServerIP();
		this.LoginNameFTP = aES_SERVER_INFOSchema.getLoginNameFTP();
		this.LoginPwdFTP = aES_SERVER_INFOSchema.getLoginPwdFTP();
		this.PicPathFTP = aES_SERVER_INFOSchema.getPicPathFTP();
		this.ServerFlag = aES_SERVER_INFOSchema.getServerFlag();
		this.ServerPort = aES_SERVER_INFOSchema.getServerPort();
		this.PicPath = aES_SERVER_INFOSchema.getPicPath();
		this.ServerBasePath = aES_SERVER_INFOSchema.getServerBasePath();
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
			if( rs.getString("HostName") == null )
				this.HostName = null;
			else
				this.HostName = rs.getString("HostName").trim();

			if( rs.getString("ServerIP") == null )
				this.ServerIP = null;
			else
				this.ServerIP = rs.getString("ServerIP").trim();

			if( rs.getString("LoginNameFTP") == null )
				this.LoginNameFTP = null;
			else
				this.LoginNameFTP = rs.getString("LoginNameFTP").trim();

			if( rs.getString("LoginPwdFTP") == null )
				this.LoginPwdFTP = null;
			else
				this.LoginPwdFTP = rs.getString("LoginPwdFTP").trim();

			if( rs.getString("PicPathFTP") == null )
				this.PicPathFTP = null;
			else
				this.PicPathFTP = rs.getString("PicPathFTP").trim();

			if( rs.getString("ServerFlag") == null )
				this.ServerFlag = null;
			else
				this.ServerFlag = rs.getString("ServerFlag").trim();

			if( rs.getString("ServerPort") == null )
				this.ServerPort = null;
			else
				this.ServerPort = rs.getString("ServerPort").trim();

			if( rs.getString("PicPath") == null )
				this.PicPath = null;
			else
				this.PicPath = rs.getString("PicPath").trim();

			if( rs.getString("ServerBasePath") == null )
				this.ServerBasePath = null;
			else
				this.ServerBasePath = rs.getString("ServerBasePath").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_SERVER_INFO表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_SERVER_INFOSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_SERVER_INFOSchema getSchema()
	{
		ES_SERVER_INFOSchema aES_SERVER_INFOSchema = new ES_SERVER_INFOSchema();
		aES_SERVER_INFOSchema.setSchema(this);
		return aES_SERVER_INFOSchema;
	}

	public ES_SERVER_INFODB getDB()
	{
		ES_SERVER_INFODB aDBOper = new ES_SERVER_INFODB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_SERVER_INFO描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(HostName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoginNameFTP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoginPwdFTP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PicPathFTP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerPort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PicPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerBasePath));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_SERVER_INFO>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			HostName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ServerIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LoginNameFTP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LoginPwdFTP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PicPathFTP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ServerFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ServerPort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PicPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ServerBasePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_SERVER_INFOSchema";
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
		if (FCode.equalsIgnoreCase("HostName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HostName));
		}
		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerIP));
		}
		if (FCode.equalsIgnoreCase("LoginNameFTP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoginNameFTP));
		}
		if (FCode.equalsIgnoreCase("LoginPwdFTP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoginPwdFTP));
		}
		if (FCode.equalsIgnoreCase("PicPathFTP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PicPathFTP));
		}
		if (FCode.equalsIgnoreCase("ServerFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerFlag));
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerPort));
		}
		if (FCode.equalsIgnoreCase("PicPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PicPath));
		}
		if (FCode.equalsIgnoreCase("ServerBasePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerBasePath));
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
				strFieldValue = StrTool.GBKToUnicode(HostName);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ServerIP);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(LoginNameFTP);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LoginPwdFTP);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PicPathFTP);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ServerFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ServerPort);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PicPath);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ServerBasePath);
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

		if (FCode.equalsIgnoreCase("HostName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HostName = FValue.trim();
			}
			else
				HostName = null;
		}
		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerIP = FValue.trim();
			}
			else
				ServerIP = null;
		}
		if (FCode.equalsIgnoreCase("LoginNameFTP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoginNameFTP = FValue.trim();
			}
			else
				LoginNameFTP = null;
		}
		if (FCode.equalsIgnoreCase("LoginPwdFTP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoginPwdFTP = FValue.trim();
			}
			else
				LoginPwdFTP = null;
		}
		if (FCode.equalsIgnoreCase("PicPathFTP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PicPathFTP = FValue.trim();
			}
			else
				PicPathFTP = null;
		}
		if (FCode.equalsIgnoreCase("ServerFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerFlag = FValue.trim();
			}
			else
				ServerFlag = null;
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
		if (FCode.equalsIgnoreCase("PicPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PicPath = FValue.trim();
			}
			else
				PicPath = null;
		}
		if (FCode.equalsIgnoreCase("ServerBasePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerBasePath = FValue.trim();
			}
			else
				ServerBasePath = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_SERVER_INFOSchema other = (ES_SERVER_INFOSchema)otherObject;
		return
			HostName.equals(other.getHostName())
			&& ServerIP.equals(other.getServerIP())
			&& LoginNameFTP.equals(other.getLoginNameFTP())
			&& LoginPwdFTP.equals(other.getLoginPwdFTP())
			&& PicPathFTP.equals(other.getPicPathFTP())
			&& ServerFlag.equals(other.getServerFlag())
			&& ServerPort.equals(other.getServerPort())
			&& PicPath.equals(other.getPicPath())
			&& ServerBasePath.equals(other.getServerBasePath());
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
		if( strFieldName.equals("HostName") ) {
			return 0;
		}
		if( strFieldName.equals("ServerIP") ) {
			return 1;
		}
		if( strFieldName.equals("LoginNameFTP") ) {
			return 2;
		}
		if( strFieldName.equals("LoginPwdFTP") ) {
			return 3;
		}
		if( strFieldName.equals("PicPathFTP") ) {
			return 4;
		}
		if( strFieldName.equals("ServerFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ServerPort") ) {
			return 6;
		}
		if( strFieldName.equals("PicPath") ) {
			return 7;
		}
		if( strFieldName.equals("ServerBasePath") ) {
			return 8;
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
				strFieldName = "HostName";
				break;
			case 1:
				strFieldName = "ServerIP";
				break;
			case 2:
				strFieldName = "LoginNameFTP";
				break;
			case 3:
				strFieldName = "LoginPwdFTP";
				break;
			case 4:
				strFieldName = "PicPathFTP";
				break;
			case 5:
				strFieldName = "ServerFlag";
				break;
			case 6:
				strFieldName = "ServerPort";
				break;
			case 7:
				strFieldName = "PicPath";
				break;
			case 8:
				strFieldName = "ServerBasePath";
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
		if( strFieldName.equals("HostName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoginNameFTP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoginPwdFTP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PicPathFTP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerPort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PicPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerBasePath") ) {
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
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
