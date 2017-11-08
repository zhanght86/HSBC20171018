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
import com.sinosoft.lis.db.LWProcessTranDB;

/*
 * <p>ClassName: LWProcessTranSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流
 */
public class LWProcessTranSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWProcessTranSchema.class);
	// @Field
	/** 转移id */
	private String TranID;
	/** 过程id */
	private String ProcessID;
	/** 转移起点 */
	private String TranStartActivityID;
	/** 转移终点 */
	private String TranEndActivityID;
	/** 转移条件类型 */
	private String TranCondType;
	/** 转移条件 */
	private String TranCond;
	/** 起点类型 */
	private String StartType;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWProcessTranSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TranID";

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
		LWProcessTranSchema cloned = (LWProcessTranSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTranID()
	{
		return TranID;
	}
	public void setTranID(String aTranID)
	{
		if(aTranID!=null && aTranID.length()>10)
			throw new IllegalArgumentException("转移idTranID值"+aTranID+"的长度"+aTranID.length()+"大于最大值10");
		TranID = aTranID;
	}
	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		if(aProcessID!=null && aProcessID.length()>10)
			throw new IllegalArgumentException("过程idProcessID值"+aProcessID+"的长度"+aProcessID.length()+"大于最大值10");
		ProcessID = aProcessID;
	}
	public String getTranStartActivityID()
	{
		return TranStartActivityID;
	}
	public void setTranStartActivityID(String aTranStartActivityID)
	{
		if(aTranStartActivityID!=null && aTranStartActivityID.length()>10)
			throw new IllegalArgumentException("转移起点TranStartActivityID值"+aTranStartActivityID+"的长度"+aTranStartActivityID.length()+"大于最大值10");
		TranStartActivityID = aTranStartActivityID;
	}
	public String getTranEndActivityID()
	{
		return TranEndActivityID;
	}
	public void setTranEndActivityID(String aTranEndActivityID)
	{
		if(aTranEndActivityID!=null && aTranEndActivityID.length()>10)
			throw new IllegalArgumentException("转移终点TranEndActivityID值"+aTranEndActivityID+"的长度"+aTranEndActivityID.length()+"大于最大值10");
		TranEndActivityID = aTranEndActivityID;
	}
	/**
	* 0-无转移条件，1-转移条件为SQL，2-转移条件为类
	*/
	public String getTranCondType()
	{
		return TranCondType;
	}
	public void setTranCondType(String aTranCondType)
	{
		if(aTranCondType!=null && aTranCondType.length()>1)
			throw new IllegalArgumentException("转移条件类型TranCondType值"+aTranCondType+"的长度"+aTranCondType.length()+"大于最大值1");
		TranCondType = aTranCondType;
	}
	public String getTranCond()
	{
		return TranCond;
	}
	public void setTranCond(String aTranCond)
	{
		if(aTranCond!=null && aTranCond.length()>1000)
			throw new IllegalArgumentException("转移条件TranCond值"+aTranCond+"的长度"+aTranCond.length()+"大于最大值1000");
		TranCond = aTranCond;
	}
	/**
	* 0-流转，1-衍生
	*/
	public String getStartType()
	{
		return StartType;
	}
	public void setStartType(String aStartType)
	{
		if(aStartType!=null && aStartType.length()>1)
			throw new IllegalArgumentException("起点类型StartType值"+aStartType+"的长度"+aStartType.length()+"大于最大值1");
		StartType = aStartType;
	}

	/**
	* 使用另外一个 LWProcessTranSchema 对象给 Schema 赋值
	* @param: aLWProcessTranSchema LWProcessTranSchema
	**/
	public void setSchema(LWProcessTranSchema aLWProcessTranSchema)
	{
		this.TranID = aLWProcessTranSchema.getTranID();
		this.ProcessID = aLWProcessTranSchema.getProcessID();
		this.TranStartActivityID = aLWProcessTranSchema.getTranStartActivityID();
		this.TranEndActivityID = aLWProcessTranSchema.getTranEndActivityID();
		this.TranCondType = aLWProcessTranSchema.getTranCondType();
		this.TranCond = aLWProcessTranSchema.getTranCond();
		this.StartType = aLWProcessTranSchema.getStartType();
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
			if( rs.getString("TranID") == null )
				this.TranID = null;
			else
				this.TranID = rs.getString("TranID").trim();

			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

			if( rs.getString("TranStartActivityID") == null )
				this.TranStartActivityID = null;
			else
				this.TranStartActivityID = rs.getString("TranStartActivityID").trim();

			if( rs.getString("TranEndActivityID") == null )
				this.TranEndActivityID = null;
			else
				this.TranEndActivityID = rs.getString("TranEndActivityID").trim();

			if( rs.getString("TranCondType") == null )
				this.TranCondType = null;
			else
				this.TranCondType = rs.getString("TranCondType").trim();

			if( rs.getString("TranCond") == null )
				this.TranCond = null;
			else
				this.TranCond = rs.getString("TranCond").trim();

			if( rs.getString("StartType") == null )
				this.StartType = null;
			else
				this.StartType = rs.getString("StartType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWProcessTran表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWProcessTranSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWProcessTranSchema getSchema()
	{
		LWProcessTranSchema aLWProcessTranSchema = new LWProcessTranSchema();
		aLWProcessTranSchema.setSchema(this);
		return aLWProcessTranSchema;
	}

	public LWProcessTranDB getDB()
	{
		LWProcessTranDB aDBOper = new LWProcessTranDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWProcessTran描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TranID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TranStartActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TranEndActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TranCondType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TranCond)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWProcessTran>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TranID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TranStartActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TranEndActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TranCondType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TranCond = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			StartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWProcessTranSchema";
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
		if (FCode.equalsIgnoreCase("TranID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranID));
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
		}
		if (FCode.equalsIgnoreCase("TranStartActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranStartActivityID));
		}
		if (FCode.equalsIgnoreCase("TranEndActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranEndActivityID));
		}
		if (FCode.equalsIgnoreCase("TranCondType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCondType));
		}
		if (FCode.equalsIgnoreCase("TranCond"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCond));
		}
		if (FCode.equalsIgnoreCase("StartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartType));
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
				strFieldValue = StrTool.GBKToUnicode(TranID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TranStartActivityID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TranEndActivityID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TranCondType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TranCond);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(StartType);
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

		if (FCode.equalsIgnoreCase("TranID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranID = FValue.trim();
			}
			else
				TranID = null;
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessID = FValue.trim();
			}
			else
				ProcessID = null;
		}
		if (FCode.equalsIgnoreCase("TranStartActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranStartActivityID = FValue.trim();
			}
			else
				TranStartActivityID = null;
		}
		if (FCode.equalsIgnoreCase("TranEndActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranEndActivityID = FValue.trim();
			}
			else
				TranEndActivityID = null;
		}
		if (FCode.equalsIgnoreCase("TranCondType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranCondType = FValue.trim();
			}
			else
				TranCondType = null;
		}
		if (FCode.equalsIgnoreCase("TranCond"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranCond = FValue.trim();
			}
			else
				TranCond = null;
		}
		if (FCode.equalsIgnoreCase("StartType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartType = FValue.trim();
			}
			else
				StartType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWProcessTranSchema other = (LWProcessTranSchema)otherObject;
		return
			TranID.equals(other.getTranID())
			&& ProcessID.equals(other.getProcessID())
			&& TranStartActivityID.equals(other.getTranStartActivityID())
			&& TranEndActivityID.equals(other.getTranEndActivityID())
			&& TranCondType.equals(other.getTranCondType())
			&& TranCond.equals(other.getTranCond())
			&& StartType.equals(other.getStartType());
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
		if( strFieldName.equals("TranID") ) {
			return 0;
		}
		if( strFieldName.equals("ProcessID") ) {
			return 1;
		}
		if( strFieldName.equals("TranStartActivityID") ) {
			return 2;
		}
		if( strFieldName.equals("TranEndActivityID") ) {
			return 3;
		}
		if( strFieldName.equals("TranCondType") ) {
			return 4;
		}
		if( strFieldName.equals("TranCond") ) {
			return 5;
		}
		if( strFieldName.equals("StartType") ) {
			return 6;
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
				strFieldName = "TranID";
				break;
			case 1:
				strFieldName = "ProcessID";
				break;
			case 2:
				strFieldName = "TranStartActivityID";
				break;
			case 3:
				strFieldName = "TranEndActivityID";
				break;
			case 4:
				strFieldName = "TranCondType";
				break;
			case 5:
				strFieldName = "TranCond";
				break;
			case 6:
				strFieldName = "StartType";
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
		if( strFieldName.equals("TranID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranStartActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranEndActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranCondType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranCond") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
