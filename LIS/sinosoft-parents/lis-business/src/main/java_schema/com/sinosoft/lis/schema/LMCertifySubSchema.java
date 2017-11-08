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
import com.sinosoft.lis.db.LMCertifySubDB;

/*
 * <p>ClassName: LMCertifySubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LMCertifySubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMCertifySubSchema.class);

	// @Field
	/** 单证编码 */
	private String CertifyCode;
	/** 单证类型 */
	private String CertifyClass;
	/** 数据库服务器 */
	private String DBServer;
	/** 数据库名称 */
	private String DBName;
	/** 表名 */
	private String TableName;
	/** 字段名 */
	private String FieldName;
	/** 执行前sql */
	private String BeforeSQL;
	/** 执行后sql */
	private String AfterSQL;
	/** 错误信息 */
	private String ErrorMessage;
	/** 自动回收 */
	private String AutoTakeBack;
	/** 执行调用接口类 */
	private String InterfaceClass;
	/** 打印后自动发放 */
	private String AutoSend;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCertifySubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CertifyCode";

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
		LMCertifySubSchema cloned = (LMCertifySubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getCertifyClass()
	{
		return CertifyClass;
	}
	public void setCertifyClass(String aCertifyClass)
	{
		CertifyClass = aCertifyClass;
	}
	public String getDBServer()
	{
		return DBServer;
	}
	public void setDBServer(String aDBServer)
	{
		DBServer = aDBServer;
	}
	public String getDBName()
	{
		return DBName;
	}
	public void setDBName(String aDBName)
	{
		DBName = aDBName;
	}
	/**
	* 系统单证来源的数据库表
	*/
	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String aTableName)
	{
		TableName = aTableName;
	}
	public String getFieldName()
	{
		return FieldName;
	}
	public void setFieldName(String aFieldName)
	{
		FieldName = aFieldName;
	}
	/**
	* 单证发放之前执行的SQL;
	*/
	public String getBeforeSQL()
	{
		return BeforeSQL;
	}
	public void setBeforeSQL(String aBeforeSQL)
	{
		BeforeSQL = aBeforeSQL;
	}
	/**
	* 单证回收之后执行的SQL;
	*/
	public String getAfterSQL()
	{
		return AfterSQL;
	}
	public void setAfterSQL(String aAfterSQL)
	{
		AfterSQL = aAfterSQL;
	}
	/**
	* 当系统单证校验不通过的时候提示的错误信息
	*/
	public String getErrorMessage()
	{
		return ErrorMessage;
	}
	public void setErrorMessage(String aErrorMessage)
	{
		ErrorMessage = aErrorMessage;
	}
	/**
	* “Y” 自动回收 “N” 手动回收
	*/
	public String getAutoTakeBack()
	{
		return AutoTakeBack;
	}
	public void setAutoTakeBack(String aAutoTakeBack)
	{
		AutoTakeBack = aAutoTakeBack;
	}
	public String getInterfaceClass()
	{
		return InterfaceClass;
	}
	public void setInterfaceClass(String aInterfaceClass)
	{
		InterfaceClass = aInterfaceClass;
	}
	/**
	* “Y” 自动发放 “N” 手动发放
	*/
	public String getAutoSend()
	{
		return AutoSend;
	}
	public void setAutoSend(String aAutoSend)
	{
		AutoSend = aAutoSend;
	}

	/**
	* 使用另外一个 LMCertifySubSchema 对象给 Schema 赋值
	* @param: aLMCertifySubSchema LMCertifySubSchema
	**/
	public void setSchema(LMCertifySubSchema aLMCertifySubSchema)
	{
		this.CertifyCode = aLMCertifySubSchema.getCertifyCode();
		this.CertifyClass = aLMCertifySubSchema.getCertifyClass();
		this.DBServer = aLMCertifySubSchema.getDBServer();
		this.DBName = aLMCertifySubSchema.getDBName();
		this.TableName = aLMCertifySubSchema.getTableName();
		this.FieldName = aLMCertifySubSchema.getFieldName();
		this.BeforeSQL = aLMCertifySubSchema.getBeforeSQL();
		this.AfterSQL = aLMCertifySubSchema.getAfterSQL();
		this.ErrorMessage = aLMCertifySubSchema.getErrorMessage();
		this.AutoTakeBack = aLMCertifySubSchema.getAutoTakeBack();
		this.InterfaceClass = aLMCertifySubSchema.getInterfaceClass();
		this.AutoSend = aLMCertifySubSchema.getAutoSend();
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
			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("CertifyClass") == null )
				this.CertifyClass = null;
			else
				this.CertifyClass = rs.getString("CertifyClass").trim();

			if( rs.getString("DBServer") == null )
				this.DBServer = null;
			else
				this.DBServer = rs.getString("DBServer").trim();

			if( rs.getString("DBName") == null )
				this.DBName = null;
			else
				this.DBName = rs.getString("DBName").trim();

			if( rs.getString("TableName") == null )
				this.TableName = null;
			else
				this.TableName = rs.getString("TableName").trim();

			if( rs.getString("FieldName") == null )
				this.FieldName = null;
			else
				this.FieldName = rs.getString("FieldName").trim();

			if( rs.getString("BeforeSQL") == null )
				this.BeforeSQL = null;
			else
				this.BeforeSQL = rs.getString("BeforeSQL").trim();

			if( rs.getString("AfterSQL") == null )
				this.AfterSQL = null;
			else
				this.AfterSQL = rs.getString("AfterSQL").trim();

			if( rs.getString("ErrorMessage") == null )
				this.ErrorMessage = null;
			else
				this.ErrorMessage = rs.getString("ErrorMessage").trim();

			if( rs.getString("AutoTakeBack") == null )
				this.AutoTakeBack = null;
			else
				this.AutoTakeBack = rs.getString("AutoTakeBack").trim();

			if( rs.getString("InterfaceClass") == null )
				this.InterfaceClass = null;
			else
				this.InterfaceClass = rs.getString("InterfaceClass").trim();

			if( rs.getString("AutoSend") == null )
				this.AutoSend = null;
			else
				this.AutoSend = rs.getString("AutoSend").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMCertifySub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCertifySubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMCertifySubSchema getSchema()
	{
		LMCertifySubSchema aLMCertifySubSchema = new LMCertifySubSchema();
		aLMCertifySubSchema.setSchema(this);
		return aLMCertifySubSchema;
	}

	public LMCertifySubDB getDB()
	{
		LMCertifySubDB aDBOper = new LMCertifySubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCertifySub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DBServer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DBName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BeforeSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorMessage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoTakeBack)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterfaceClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoSend));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCertifySub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DBServer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DBName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BeforeSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AfterSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ErrorMessage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AutoTakeBack = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InterfaceClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AutoSend = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCertifySubSchema";
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("CertifyClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyClass));
		}
		if (FCode.equalsIgnoreCase("DBServer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DBServer));
		}
		if (FCode.equalsIgnoreCase("DBName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DBName));
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableName));
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
		}
		if (FCode.equalsIgnoreCase("BeforeSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BeforeSQL));
		}
		if (FCode.equalsIgnoreCase("AfterSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterSQL));
		}
		if (FCode.equalsIgnoreCase("ErrorMessage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorMessage));
		}
		if (FCode.equalsIgnoreCase("AutoTakeBack"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoTakeBack));
		}
		if (FCode.equalsIgnoreCase("InterfaceClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterfaceClass));
		}
		if (FCode.equalsIgnoreCase("AutoSend"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoSend));
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
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyClass);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DBServer);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DBName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TableName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FieldName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BeforeSQL);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AfterSQL);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ErrorMessage);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AutoTakeBack);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InterfaceClass);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AutoSend);
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

		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("CertifyClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyClass = FValue.trim();
			}
			else
				CertifyClass = null;
		}
		if (FCode.equalsIgnoreCase("DBServer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DBServer = FValue.trim();
			}
			else
				DBServer = null;
		}
		if (FCode.equalsIgnoreCase("DBName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DBName = FValue.trim();
			}
			else
				DBName = null;
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableName = FValue.trim();
			}
			else
				TableName = null;
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldName = FValue.trim();
			}
			else
				FieldName = null;
		}
		if (FCode.equalsIgnoreCase("BeforeSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BeforeSQL = FValue.trim();
			}
			else
				BeforeSQL = null;
		}
		if (FCode.equalsIgnoreCase("AfterSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterSQL = FValue.trim();
			}
			else
				AfterSQL = null;
		}
		if (FCode.equalsIgnoreCase("ErrorMessage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorMessage = FValue.trim();
			}
			else
				ErrorMessage = null;
		}
		if (FCode.equalsIgnoreCase("AutoTakeBack"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoTakeBack = FValue.trim();
			}
			else
				AutoTakeBack = null;
		}
		if (FCode.equalsIgnoreCase("InterfaceClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterfaceClass = FValue.trim();
			}
			else
				InterfaceClass = null;
		}
		if (FCode.equalsIgnoreCase("AutoSend"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoSend = FValue.trim();
			}
			else
				AutoSend = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCertifySubSchema other = (LMCertifySubSchema)otherObject;
		return
			CertifyCode.equals(other.getCertifyCode())
			&& CertifyClass.equals(other.getCertifyClass())
			&& DBServer.equals(other.getDBServer())
			&& DBName.equals(other.getDBName())
			&& TableName.equals(other.getTableName())
			&& FieldName.equals(other.getFieldName())
			&& BeforeSQL.equals(other.getBeforeSQL())
			&& AfterSQL.equals(other.getAfterSQL())
			&& ErrorMessage.equals(other.getErrorMessage())
			&& AutoTakeBack.equals(other.getAutoTakeBack())
			&& InterfaceClass.equals(other.getInterfaceClass())
			&& AutoSend.equals(other.getAutoSend());
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
		if( strFieldName.equals("CertifyCode") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyClass") ) {
			return 1;
		}
		if( strFieldName.equals("DBServer") ) {
			return 2;
		}
		if( strFieldName.equals("DBName") ) {
			return 3;
		}
		if( strFieldName.equals("TableName") ) {
			return 4;
		}
		if( strFieldName.equals("FieldName") ) {
			return 5;
		}
		if( strFieldName.equals("BeforeSQL") ) {
			return 6;
		}
		if( strFieldName.equals("AfterSQL") ) {
			return 7;
		}
		if( strFieldName.equals("ErrorMessage") ) {
			return 8;
		}
		if( strFieldName.equals("AutoTakeBack") ) {
			return 9;
		}
		if( strFieldName.equals("InterfaceClass") ) {
			return 10;
		}
		if( strFieldName.equals("AutoSend") ) {
			return 11;
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
				strFieldName = "CertifyCode";
				break;
			case 1:
				strFieldName = "CertifyClass";
				break;
			case 2:
				strFieldName = "DBServer";
				break;
			case 3:
				strFieldName = "DBName";
				break;
			case 4:
				strFieldName = "TableName";
				break;
			case 5:
				strFieldName = "FieldName";
				break;
			case 6:
				strFieldName = "BeforeSQL";
				break;
			case 7:
				strFieldName = "AfterSQL";
				break;
			case 8:
				strFieldName = "ErrorMessage";
				break;
			case 9:
				strFieldName = "AutoTakeBack";
				break;
			case 10:
				strFieldName = "InterfaceClass";
				break;
			case 11:
				strFieldName = "AutoSend";
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
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DBServer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DBName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BeforeSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AfterSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorMessage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoTakeBack") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterfaceClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoSend") ) {
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
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
