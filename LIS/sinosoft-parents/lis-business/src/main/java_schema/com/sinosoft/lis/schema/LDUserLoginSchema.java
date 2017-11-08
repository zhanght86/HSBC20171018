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
import com.sinosoft.lis.db.LDUserLoginDB;

/*
 * <p>ClassName: LDUserLoginSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUserLoginSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUserLoginSchema.class);

	// @Field
	/** 用户编码 */
	private String UserCode;
	/** 机构编码 */
	private String ComCode;
	/** 客户端名称 */
	private String ClientName;
	/** 客户端类型 */
	private String ClientType;
	/** 客户端ip */
	private String ClientIP;
	/** 打印机类型 */
	private String PrinterType;
	/** 输入方法 */
	private String InputType;
	/** 注册年月 */
	private Date RgtDate;
	/** 登陆日期 */
	private Date LoginDate;
	/** 登陆时间 */
	private Date LoginTime;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUserLoginSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "UserCode";

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
		LDUserLoginSchema cloned = (LDUserLoginSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String aUserCode)
	{
		UserCode = aUserCode;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getClientName()
	{
		return ClientName;
	}
	public void setClientName(String aClientName)
	{
		ClientName = aClientName;
	}
	public String getClientType()
	{
		return ClientType;
	}
	public void setClientType(String aClientType)
	{
		ClientType = aClientType;
	}
	public String getClientIP()
	{
		return ClientIP;
	}
	public void setClientIP(String aClientIP)
	{
		ClientIP = aClientIP;
	}
	public String getPrinterType()
	{
		return PrinterType;
	}
	public void setPrinterType(String aPrinterType)
	{
		PrinterType = aPrinterType;
	}
	public String getInputType()
	{
		return InputType;
	}
	public void setInputType(String aInputType)
	{
		InputType = aInputType;
	}
	public String getRgtDate()
	{
		if( RgtDate != null )
			return fDate.getString(RgtDate);
		else
			return null;
	}
	public void setRgtDate(Date aRgtDate)
	{
		RgtDate = aRgtDate;
	}
	public void setRgtDate(String aRgtDate)
	{
		if (aRgtDate != null && !aRgtDate.equals("") )
		{
			RgtDate = fDate.getDate( aRgtDate );
		}
		else
			RgtDate = null;
	}

	public String getLoginDate()
	{
		if( LoginDate != null )
			return fDate.getString(LoginDate);
		else
			return null;
	}
	public void setLoginDate(Date aLoginDate)
	{
		LoginDate = aLoginDate;
	}
	public void setLoginDate(String aLoginDate)
	{
		if (aLoginDate != null && !aLoginDate.equals("") )
		{
			LoginDate = fDate.getDate( aLoginDate );
		}
		else
			LoginDate = null;
	}

	public String getLoginTime()
	{
		if( LoginTime != null )
			return fDate.getString(LoginTime);
		else
			return null;
	}
	public void setLoginTime(Date aLoginTime)
	{
		LoginTime = aLoginTime;
	}
	public void setLoginTime(String aLoginTime)
	{
		if (aLoginTime != null && !aLoginTime.equals("") )
		{
			LoginTime = fDate.getDate( aLoginTime );
		}
		else
			LoginTime = null;
	}


	/**
	* 使用另外一个 LDUserLoginSchema 对象给 Schema 赋值
	* @param: aLDUserLoginSchema LDUserLoginSchema
	**/
	public void setSchema(LDUserLoginSchema aLDUserLoginSchema)
	{
		this.UserCode = aLDUserLoginSchema.getUserCode();
		this.ComCode = aLDUserLoginSchema.getComCode();
		this.ClientName = aLDUserLoginSchema.getClientName();
		this.ClientType = aLDUserLoginSchema.getClientType();
		this.ClientIP = aLDUserLoginSchema.getClientIP();
		this.PrinterType = aLDUserLoginSchema.getPrinterType();
		this.InputType = aLDUserLoginSchema.getInputType();
		this.RgtDate = fDate.getDate( aLDUserLoginSchema.getRgtDate());
		this.LoginDate = fDate.getDate( aLDUserLoginSchema.getLoginDate());
		this.LoginTime = fDate.getDate( aLDUserLoginSchema.getLoginTime());
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
			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ClientName") == null )
				this.ClientName = null;
			else
				this.ClientName = rs.getString("ClientName").trim();

			if( rs.getString("ClientType") == null )
				this.ClientType = null;
			else
				this.ClientType = rs.getString("ClientType").trim();

			if( rs.getString("ClientIP") == null )
				this.ClientIP = null;
			else
				this.ClientIP = rs.getString("ClientIP").trim();

			if( rs.getString("PrinterType") == null )
				this.PrinterType = null;
			else
				this.PrinterType = rs.getString("PrinterType").trim();

			if( rs.getString("InputType") == null )
				this.InputType = null;
			else
				this.InputType = rs.getString("InputType").trim();

			this.RgtDate = rs.getDate("RgtDate");
			this.LoginDate = rs.getDate("LoginDate");
			this.LoginTime = rs.getDate("LoginTime");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUserLogin表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserLoginSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUserLoginSchema getSchema()
	{
		LDUserLoginSchema aLDUserLoginSchema = new LDUserLoginSchema();
		aLDUserLoginSchema.setSchema(this);
		return aLDUserLoginSchema;
	}

	public LDUserLoginDB getDB()
	{
		LDUserLoginDB aDBOper = new LDUserLoginDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUserLogin描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClientName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClientType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClientIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrinterType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RgtDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LoginDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LoginTime )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUserLogin>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClientName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClientType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClientIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrinterType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InputType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RgtDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			LoginDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			LoginTime = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserLoginSchema";
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
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ClientName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClientName));
		}
		if (FCode.equalsIgnoreCase("ClientType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClientType));
		}
		if (FCode.equalsIgnoreCase("ClientIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClientIP));
		}
		if (FCode.equalsIgnoreCase("PrinterType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrinterType));
		}
		if (FCode.equalsIgnoreCase("InputType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputType));
		}
		if (FCode.equalsIgnoreCase("RgtDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
		}
		if (FCode.equalsIgnoreCase("LoginDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLoginDate()));
		}
		if (FCode.equalsIgnoreCase("LoginTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLoginTime()));
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
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClientName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClientType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClientIP);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrinterType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InputType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRgtDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLoginDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLoginTime()));
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

		if (FCode.equalsIgnoreCase("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
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
		if (FCode.equalsIgnoreCase("ClientName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClientName = FValue.trim();
			}
			else
				ClientName = null;
		}
		if (FCode.equalsIgnoreCase("ClientType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClientType = FValue.trim();
			}
			else
				ClientType = null;
		}
		if (FCode.equalsIgnoreCase("ClientIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClientIP = FValue.trim();
			}
			else
				ClientIP = null;
		}
		if (FCode.equalsIgnoreCase("PrinterType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrinterType = FValue.trim();
			}
			else
				PrinterType = null;
		}
		if (FCode.equalsIgnoreCase("InputType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputType = FValue.trim();
			}
			else
				InputType = null;
		}
		if (FCode.equalsIgnoreCase("RgtDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RgtDate = fDate.getDate( FValue );
			}
			else
				RgtDate = null;
		}
		if (FCode.equalsIgnoreCase("LoginDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LoginDate = fDate.getDate( FValue );
			}
			else
				LoginDate = null;
		}
		if (FCode.equalsIgnoreCase("LoginTime"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LoginTime = fDate.getDate( FValue );
			}
			else
				LoginTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUserLoginSchema other = (LDUserLoginSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& ComCode.equals(other.getComCode())
			&& ClientName.equals(other.getClientName())
			&& ClientType.equals(other.getClientType())
			&& ClientIP.equals(other.getClientIP())
			&& PrinterType.equals(other.getPrinterType())
			&& InputType.equals(other.getInputType())
			&& fDate.getString(RgtDate).equals(other.getRgtDate())
			&& fDate.getString(LoginDate).equals(other.getLoginDate())
			&& fDate.getString(LoginTime).equals(other.getLoginTime());
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
		if( strFieldName.equals("UserCode") ) {
			return 0;
		}
		if( strFieldName.equals("ComCode") ) {
			return 1;
		}
		if( strFieldName.equals("ClientName") ) {
			return 2;
		}
		if( strFieldName.equals("ClientType") ) {
			return 3;
		}
		if( strFieldName.equals("ClientIP") ) {
			return 4;
		}
		if( strFieldName.equals("PrinterType") ) {
			return 5;
		}
		if( strFieldName.equals("InputType") ) {
			return 6;
		}
		if( strFieldName.equals("RgtDate") ) {
			return 7;
		}
		if( strFieldName.equals("LoginDate") ) {
			return 8;
		}
		if( strFieldName.equals("LoginTime") ) {
			return 9;
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
				strFieldName = "UserCode";
				break;
			case 1:
				strFieldName = "ComCode";
				break;
			case 2:
				strFieldName = "ClientName";
				break;
			case 3:
				strFieldName = "ClientType";
				break;
			case 4:
				strFieldName = "ClientIP";
				break;
			case 5:
				strFieldName = "PrinterType";
				break;
			case 6:
				strFieldName = "InputType";
				break;
			case 7:
				strFieldName = "RgtDate";
				break;
			case 8:
				strFieldName = "LoginDate";
				break;
			case 9:
				strFieldName = "LoginTime";
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
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClientName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClientType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClientIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrinterType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LoginDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LoginTime") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
