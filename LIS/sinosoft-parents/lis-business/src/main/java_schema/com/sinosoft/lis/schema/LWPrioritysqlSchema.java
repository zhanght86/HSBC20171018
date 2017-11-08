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
import com.sinosoft.lis.db.LWPrioritysqlDB;

/*
 * <p>ClassName: LWPrioritysqlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 优先级别sql
 */
public class LWPrioritysqlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWPrioritysqlSchema.class);
	// @Field
	/** 流程id */
	private String ProcessID;
	/** 活动id */
	private String activityID;
	/** 优先级别id */
	private String PriorityID;
	/** 优先级别sql */
	private String PrioritySQL;
	/** 流程版本号 */
	private String version;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWPrioritysqlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ProcessID";
		pk[1] = "activityID";
		pk[2] = "version";

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
		LWPrioritysqlSchema cloned = (LWPrioritysqlSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		if(aProcessID!=null && aProcessID.length()>20)
			throw new IllegalArgumentException("流程idProcessID值"+aProcessID+"的长度"+aProcessID.length()+"大于最大值20");
		ProcessID = aProcessID;
	}
	public String getactivityID()
	{
		return activityID;
	}
	public void setactivityID(String aactivityID)
	{
		if(aactivityID!=null && aactivityID.length()>20)
			throw new IllegalArgumentException("活动idactivityID值"+aactivityID+"的长度"+aactivityID.length()+"大于最大值20");
		activityID = aactivityID;
	}
	public String getPriorityID()
	{
		return PriorityID;
	}
	public void setPriorityID(String aPriorityID)
	{
		if(aPriorityID!=null && aPriorityID.length()>10)
			throw new IllegalArgumentException("优先级别idPriorityID值"+aPriorityID+"的长度"+aPriorityID.length()+"大于最大值10");
		PriorityID = aPriorityID;
	}
	public String getPrioritySQL()
	{
		return PrioritySQL;
	}
	public void setPrioritySQL(String aPrioritySQL)
	{
		if(aPrioritySQL!=null && aPrioritySQL.length()>200)
			throw new IllegalArgumentException("优先级别sqlPrioritySQL值"+aPrioritySQL+"的长度"+aPrioritySQL.length()+"大于最大值200");
		PrioritySQL = aPrioritySQL;
	}
	public String getversion()
	{
		return version;
	}
	public void setversion(String aversion)
	{
		if(aversion!=null && aversion.length()>20)
			throw new IllegalArgumentException("流程版本号version值"+aversion+"的长度"+aversion.length()+"大于最大值20");
		version = aversion;
	}

	/**
	* 使用另外一个 LWPrioritysqlSchema 对象给 Schema 赋值
	* @param: aLWPrioritysqlSchema LWPrioritysqlSchema
	**/
	public void setSchema(LWPrioritysqlSchema aLWPrioritysqlSchema)
	{
		this.ProcessID = aLWPrioritysqlSchema.getProcessID();
		this.activityID = aLWPrioritysqlSchema.getactivityID();
		this.PriorityID = aLWPrioritysqlSchema.getPriorityID();
		this.PrioritySQL = aLWPrioritysqlSchema.getPrioritySQL();
		this.version = aLWPrioritysqlSchema.getversion();
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
			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

			if( rs.getString("activityID") == null )
				this.activityID = null;
			else
				this.activityID = rs.getString("activityID").trim();

			if( rs.getString("PriorityID") == null )
				this.PriorityID = null;
			else
				this.PriorityID = rs.getString("PriorityID").trim();

			if( rs.getString("PrioritySQL") == null )
				this.PrioritySQL = null;
			else
				this.PrioritySQL = rs.getString("PrioritySQL").trim();

			if( rs.getString("version") == null )
				this.version = null;
			else
				this.version = rs.getString("version").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWPrioritysql表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWPrioritysqlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWPrioritysqlSchema getSchema()
	{
		LWPrioritysqlSchema aLWPrioritysqlSchema = new LWPrioritysqlSchema();
		aLWPrioritysqlSchema.setSchema(this);
		return aLWPrioritysqlSchema;
	}

	public LWPrioritysqlDB getDB()
	{
		LWPrioritysqlDB aDBOper = new LWPrioritysqlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWPrioritysql描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(activityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PriorityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrioritySQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(version));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWPrioritysql>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			activityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PriorityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrioritySQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			version = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWPrioritysqlSchema";
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
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
		}
		if (FCode.equalsIgnoreCase("activityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(activityID));
		}
		if (FCode.equalsIgnoreCase("PriorityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PriorityID));
		}
		if (FCode.equalsIgnoreCase("PrioritySQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrioritySQL));
		}
		if (FCode.equalsIgnoreCase("version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(version));
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
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(activityID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PriorityID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrioritySQL);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(version);
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

		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessID = FValue.trim();
			}
			else
				ProcessID = null;
		}
		if (FCode.equalsIgnoreCase("activityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				activityID = FValue.trim();
			}
			else
				activityID = null;
		}
		if (FCode.equalsIgnoreCase("PriorityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PriorityID = FValue.trim();
			}
			else
				PriorityID = null;
		}
		if (FCode.equalsIgnoreCase("PrioritySQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrioritySQL = FValue.trim();
			}
			else
				PrioritySQL = null;
		}
		if (FCode.equalsIgnoreCase("version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				version = FValue.trim();
			}
			else
				version = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWPrioritysqlSchema other = (LWPrioritysqlSchema)otherObject;
		return
			ProcessID.equals(other.getProcessID())
			&& activityID.equals(other.getactivityID())
			&& PriorityID.equals(other.getPriorityID())
			&& PrioritySQL.equals(other.getPrioritySQL())
			&& version.equals(other.getversion());
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
		if( strFieldName.equals("ProcessID") ) {
			return 0;
		}
		if( strFieldName.equals("activityID") ) {
			return 1;
		}
		if( strFieldName.equals("PriorityID") ) {
			return 2;
		}
		if( strFieldName.equals("PrioritySQL") ) {
			return 3;
		}
		if( strFieldName.equals("version") ) {
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
				strFieldName = "ProcessID";
				break;
			case 1:
				strFieldName = "activityID";
				break;
			case 2:
				strFieldName = "PriorityID";
				break;
			case 3:
				strFieldName = "PrioritySQL";
				break;
			case 4:
				strFieldName = "version";
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
		if( strFieldName.equals("ProcessID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("activityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PriorityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrioritySQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("version") ) {
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
