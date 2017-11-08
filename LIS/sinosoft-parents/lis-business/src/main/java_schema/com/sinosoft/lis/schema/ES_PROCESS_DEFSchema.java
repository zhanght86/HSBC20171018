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
import com.sinosoft.lis.db.ES_PROCESS_DEFDB;

/*
 * <p>ClassName: ES_PROCESS_DEFSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_PROCESS_DEFSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_PROCESS_DEFSchema.class);

	// @Field
	/** 业务类型编码 */
	private String BussType;
	/** 单证细类编码 */
	private String SubType;
	/** 服务类型 */
	private String ServiceType;
	/** 单证处理类型代码 */
	private String ProcessCode;
	/** 单证处理类型名称 */
	private String ProcessName;
	/** 单证处理服务类名 */
	private String ProcessService;
	/** 有效标志 */
	private String ValidFlag;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_PROCESS_DEFSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "BussType";
		pk[1] = "SubType";
		pk[2] = "ServiceType";
		pk[3] = "ProcessCode";

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
		ES_PROCESS_DEFSchema cloned = (ES_PROCESS_DEFSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		BussType = aBussType;
	}
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
	}
	/**
	* 1 上载处理调用的服务<p>
	* 2 修改处理调用的服务<p>
	* 3 回写处理调用的服务
	*/
	public String getServiceType()
	{
		return ServiceType;
	}
	public void setServiceType(String aServiceType)
	{
		ServiceType = aServiceType;
	}
	/**
	* 1*** 上载处理调用的服务<p>
	* 2*** 修改处理调用的服务<p>
	* 3*** 回写处理调用的服务
	*/
	public String getProcessCode()
	{
		return ProcessCode;
	}
	public void setProcessCode(String aProcessCode)
	{
		ProcessCode = aProcessCode;
	}
	public String getProcessName()
	{
		return ProcessName;
	}
	public void setProcessName(String aProcessName)
	{
		ProcessName = aProcessName;
	}
	public String getProcessService()
	{
		return ProcessService;
	}
	public void setProcessService(String aProcessService)
	{
		ProcessService = aProcessService;
	}
	/**
	* 0 有效<p>
	* 1 无效
	*/
	public String getValidFlag()
	{
		return ValidFlag;
	}
	public void setValidFlag(String aValidFlag)
	{
		ValidFlag = aValidFlag;
	}

	/**
	* 使用另外一个 ES_PROCESS_DEFSchema 对象给 Schema 赋值
	* @param: aES_PROCESS_DEFSchema ES_PROCESS_DEFSchema
	**/
	public void setSchema(ES_PROCESS_DEFSchema aES_PROCESS_DEFSchema)
	{
		this.BussType = aES_PROCESS_DEFSchema.getBussType();
		this.SubType = aES_PROCESS_DEFSchema.getSubType();
		this.ServiceType = aES_PROCESS_DEFSchema.getServiceType();
		this.ProcessCode = aES_PROCESS_DEFSchema.getProcessCode();
		this.ProcessName = aES_PROCESS_DEFSchema.getProcessName();
		this.ProcessService = aES_PROCESS_DEFSchema.getProcessService();
		this.ValidFlag = aES_PROCESS_DEFSchema.getValidFlag();
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
			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("ServiceType") == null )
				this.ServiceType = null;
			else
				this.ServiceType = rs.getString("ServiceType").trim();

			if( rs.getString("ProcessCode") == null )
				this.ProcessCode = null;
			else
				this.ProcessCode = rs.getString("ProcessCode").trim();

			if( rs.getString("ProcessName") == null )
				this.ProcessName = null;
			else
				this.ProcessName = rs.getString("ProcessName").trim();

			if( rs.getString("ProcessService") == null )
				this.ProcessService = null;
			else
				this.ProcessService = rs.getString("ProcessService").trim();

			if( rs.getString("ValidFlag") == null )
				this.ValidFlag = null;
			else
				this.ValidFlag = rs.getString("ValidFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_PROCESS_DEF表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_PROCESS_DEFSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_PROCESS_DEFSchema getSchema()
	{
		ES_PROCESS_DEFSchema aES_PROCESS_DEFSchema = new ES_PROCESS_DEFSchema();
		aES_PROCESS_DEFSchema.setSchema(this);
		return aES_PROCESS_DEFSchema;
	}

	public ES_PROCESS_DEFDB getDB()
	{
		ES_PROCESS_DEFDB aDBOper = new ES_PROCESS_DEFDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_PROCESS_DEF描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServiceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessService)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_PROCESS_DEF>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ServiceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProcessCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProcessName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ProcessService = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ValidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_PROCESS_DEFSchema";
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
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("ServiceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServiceType));
		}
		if (FCode.equalsIgnoreCase("ProcessCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessCode));
		}
		if (FCode.equalsIgnoreCase("ProcessName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessName));
		}
		if (FCode.equalsIgnoreCase("ProcessService"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessService));
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidFlag));
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
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ServiceType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProcessCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProcessName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ProcessService);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ValidFlag);
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

		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("ServiceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServiceType = FValue.trim();
			}
			else
				ServiceType = null;
		}
		if (FCode.equalsIgnoreCase("ProcessCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessCode = FValue.trim();
			}
			else
				ProcessCode = null;
		}
		if (FCode.equalsIgnoreCase("ProcessName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessName = FValue.trim();
			}
			else
				ProcessName = null;
		}
		if (FCode.equalsIgnoreCase("ProcessService"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessService = FValue.trim();
			}
			else
				ProcessService = null;
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidFlag = FValue.trim();
			}
			else
				ValidFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_PROCESS_DEFSchema other = (ES_PROCESS_DEFSchema)otherObject;
		return
			BussType.equals(other.getBussType())
			&& SubType.equals(other.getSubType())
			&& ServiceType.equals(other.getServiceType())
			&& ProcessCode.equals(other.getProcessCode())
			&& ProcessName.equals(other.getProcessName())
			&& ProcessService.equals(other.getProcessService())
			&& ValidFlag.equals(other.getValidFlag());
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
		if( strFieldName.equals("BussType") ) {
			return 0;
		}
		if( strFieldName.equals("SubType") ) {
			return 1;
		}
		if( strFieldName.equals("ServiceType") ) {
			return 2;
		}
		if( strFieldName.equals("ProcessCode") ) {
			return 3;
		}
		if( strFieldName.equals("ProcessName") ) {
			return 4;
		}
		if( strFieldName.equals("ProcessService") ) {
			return 5;
		}
		if( strFieldName.equals("ValidFlag") ) {
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
				strFieldName = "BussType";
				break;
			case 1:
				strFieldName = "SubType";
				break;
			case 2:
				strFieldName = "ServiceType";
				break;
			case 3:
				strFieldName = "ProcessCode";
				break;
			case 4:
				strFieldName = "ProcessName";
				break;
			case 5:
				strFieldName = "ProcessService";
				break;
			case 6:
				strFieldName = "ValidFlag";
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
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServiceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessService") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidFlag") ) {
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
