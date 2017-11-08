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
import com.sinosoft.lis.db.LDMonitorDataSubDB;

/*
 * <p>ClassName: LDMonitorDataSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 自动监控平台
 */
public class LDMonitorDataSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMonitorDataSubSchema.class);

	// @Field
	/** 批次号 */
	private String BatchNo;
	/** 附属标记 */
	private String OtherFlag;
	/** 监控字段编码 */
	private String MonitorColCode;
	/** 数据 */
	private String MonitorData;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMonitorDataSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BatchNo";
		pk[1] = "OtherFlag";
		pk[2] = "MonitorColCode";

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
		LDMonitorDataSubSchema cloned = (LDMonitorDataSubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getOtherFlag()
	{
		return OtherFlag;
	}
	public void setOtherFlag(String aOtherFlag)
	{
		OtherFlag = aOtherFlag;
	}
	public String getMonitorColCode()
	{
		return MonitorColCode;
	}
	public void setMonitorColCode(String aMonitorColCode)
	{
		MonitorColCode = aMonitorColCode;
	}
	public String getMonitorData()
	{
		return MonitorData;
	}
	public void setMonitorData(String aMonitorData)
	{
		MonitorData = aMonitorData;
	}

	/**
	* 使用另外一个 LDMonitorDataSubSchema 对象给 Schema 赋值
	* @param: aLDMonitorDataSubSchema LDMonitorDataSubSchema
	**/
	public void setSchema(LDMonitorDataSubSchema aLDMonitorDataSubSchema)
	{
		this.BatchNo = aLDMonitorDataSubSchema.getBatchNo();
		this.OtherFlag = aLDMonitorDataSubSchema.getOtherFlag();
		this.MonitorColCode = aLDMonitorDataSubSchema.getMonitorColCode();
		this.MonitorData = aLDMonitorDataSubSchema.getMonitorData();
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
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("OtherFlag") == null )
				this.OtherFlag = null;
			else
				this.OtherFlag = rs.getString("OtherFlag").trim();

			if( rs.getString("MonitorColCode") == null )
				this.MonitorColCode = null;
			else
				this.MonitorColCode = rs.getString("MonitorColCode").trim();

			if( rs.getString("MonitorData") == null )
				this.MonitorData = null;
			else
				this.MonitorData = rs.getString("MonitorData").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMonitorDataSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMonitorDataSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMonitorDataSubSchema getSchema()
	{
		LDMonitorDataSubSchema aLDMonitorDataSubSchema = new LDMonitorDataSubSchema();
		aLDMonitorDataSubSchema.setSchema(this);
		return aLDMonitorDataSubSchema;
	}

	public LDMonitorDataSubDB getDB()
	{
		LDMonitorDataSubDB aDBOper = new LDMonitorDataSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMonitorDataSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MonitorColCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MonitorData));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMonitorDataSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MonitorColCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MonitorData = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMonitorDataSubSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("OtherFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherFlag));
		}
		if (FCode.equalsIgnoreCase("MonitorColCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorColCode));
		}
		if (FCode.equalsIgnoreCase("MonitorData"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorData));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherFlag);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MonitorColCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MonitorData);
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

		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherFlag = FValue.trim();
			}
			else
				OtherFlag = null;
		}
		if (FCode.equalsIgnoreCase("MonitorColCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorColCode = FValue.trim();
			}
			else
				MonitorColCode = null;
		}
		if (FCode.equalsIgnoreCase("MonitorData"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorData = FValue.trim();
			}
			else
				MonitorData = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMonitorDataSubSchema other = (LDMonitorDataSubSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& OtherFlag.equals(other.getOtherFlag())
			&& MonitorColCode.equals(other.getMonitorColCode())
			&& MonitorData.equals(other.getMonitorData());
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
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("OtherFlag") ) {
			return 1;
		}
		if( strFieldName.equals("MonitorColCode") ) {
			return 2;
		}
		if( strFieldName.equals("MonitorData") ) {
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
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "OtherFlag";
				break;
			case 2:
				strFieldName = "MonitorColCode";
				break;
			case 3:
				strFieldName = "MonitorData";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MonitorColCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MonitorData") ) {
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
