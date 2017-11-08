/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIRuleDealLogDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIRuleDealLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIRuleDealLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIRuleDealLogSchema.class);

	// @Field
	/** 校验流水号码 */
	private String CheckSerialNo;
	/** 版本编号 */
	private String VersionNo;
	/** 校验批次号码 */
	private String RuleDealBatchNo;
	/** 数据源批次号码 */
	private String DataSourceBatchNo;
	/** 校验事件结点 */
	private String CallPointID;
	/** 校验结果 */
	private String RuleDealResult;
	/** 校验人 */
	private String DealOperator;
	/** 校验计划 */
	private String RulePlanID;
	/** 校验日期 */
	private Date RuleDealDate;
	/** 校验时间 */
	private String RuleDealTime;
	/** 日志文件路径 */
	private String LogFilePath;
	/** 日志文件名称 */
	private String LogFileName;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIRuleDealLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CheckSerialNo";

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
                FIRuleDealLogSchema cloned = (FIRuleDealLogSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCheckSerialNo()
	{
		return CheckSerialNo;
	}
	public void setCheckSerialNo(String aCheckSerialNo)
	{
		CheckSerialNo = aCheckSerialNo;
	}
	public String getVersionNo()
	{
		return VersionNo;
	}
	public void setVersionNo(String aVersionNo)
	{
		VersionNo = aVersionNo;
	}
	public String getRuleDealBatchNo()
	{
		return RuleDealBatchNo;
	}
	public void setRuleDealBatchNo(String aRuleDealBatchNo)
	{
		RuleDealBatchNo = aRuleDealBatchNo;
	}
	public String getDataSourceBatchNo()
	{
		return DataSourceBatchNo;
	}
	public void setDataSourceBatchNo(String aDataSourceBatchNo)
	{
		DataSourceBatchNo = aDataSourceBatchNo;
	}
	public String getCallPointID()
	{
		return CallPointID;
	}
	public void setCallPointID(String aCallPointID)
	{
		CallPointID = aCallPointID;
	}
	public String getRuleDealResult()
	{
		return RuleDealResult;
	}
	public void setRuleDealResult(String aRuleDealResult)
	{
		RuleDealResult = aRuleDealResult;
	}
	public String getDealOperator()
	{
		return DealOperator;
	}
	public void setDealOperator(String aDealOperator)
	{
		DealOperator = aDealOperator;
	}
	public String getRulePlanID()
	{
		return RulePlanID;
	}
	public void setRulePlanID(String aRulePlanID)
	{
		RulePlanID = aRulePlanID;
	}
	public String getRuleDealDate()
	{
		if( RuleDealDate != null )
			return fDate.getString(RuleDealDate);
		else
			return null;
	}
	public void setRuleDealDate(Date aRuleDealDate)
	{
		RuleDealDate = aRuleDealDate;
	}
	public void setRuleDealDate(String aRuleDealDate)
	{
		if (aRuleDealDate != null && !aRuleDealDate.equals("") )
		{
			RuleDealDate = fDate.getDate( aRuleDealDate );
		}
		else
			RuleDealDate = null;
	}

	public String getRuleDealTime()
	{
		return RuleDealTime;
	}
	public void setRuleDealTime(String aRuleDealTime)
	{
		RuleDealTime = aRuleDealTime;
	}
	public String getLogFilePath()
	{
		return LogFilePath;
	}
	public void setLogFilePath(String aLogFilePath)
	{
		LogFilePath = aLogFilePath;
	}
	public String getLogFileName()
	{
		return LogFileName;
	}
	public void setLogFileName(String aLogFileName)
	{
		LogFileName = aLogFileName;
	}

	/**
	* 使用另外一个 FIRuleDealLogSchema 对象给 Schema 赋值
	* @param: aFIRuleDealLogSchema FIRuleDealLogSchema
	**/
	public void setSchema(FIRuleDealLogSchema aFIRuleDealLogSchema)
	{
		this.CheckSerialNo = aFIRuleDealLogSchema.getCheckSerialNo();
		this.VersionNo = aFIRuleDealLogSchema.getVersionNo();
		this.RuleDealBatchNo = aFIRuleDealLogSchema.getRuleDealBatchNo();
		this.DataSourceBatchNo = aFIRuleDealLogSchema.getDataSourceBatchNo();
		this.CallPointID = aFIRuleDealLogSchema.getCallPointID();
		this.RuleDealResult = aFIRuleDealLogSchema.getRuleDealResult();
		this.DealOperator = aFIRuleDealLogSchema.getDealOperator();
		this.RulePlanID = aFIRuleDealLogSchema.getRulePlanID();
		this.RuleDealDate = fDate.getDate( aFIRuleDealLogSchema.getRuleDealDate());
		this.RuleDealTime = aFIRuleDealLogSchema.getRuleDealTime();
		this.LogFilePath = aFIRuleDealLogSchema.getLogFilePath();
		this.LogFileName = aFIRuleDealLogSchema.getLogFileName();
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
			if( rs.getString("CheckSerialNo") == null )
				this.CheckSerialNo = null;
			else
				this.CheckSerialNo = rs.getString("CheckSerialNo").trim();

			if( rs.getString("VersionNo") == null )
				this.VersionNo = null;
			else
				this.VersionNo = rs.getString("VersionNo").trim();

			if( rs.getString("RuleDealBatchNo") == null )
				this.RuleDealBatchNo = null;
			else
				this.RuleDealBatchNo = rs.getString("RuleDealBatchNo").trim();

			if( rs.getString("DataSourceBatchNo") == null )
				this.DataSourceBatchNo = null;
			else
				this.DataSourceBatchNo = rs.getString("DataSourceBatchNo").trim();

			if( rs.getString("CallPointID") == null )
				this.CallPointID = null;
			else
				this.CallPointID = rs.getString("CallPointID").trim();

			if( rs.getString("RuleDealResult") == null )
				this.RuleDealResult = null;
			else
				this.RuleDealResult = rs.getString("RuleDealResult").trim();

			if( rs.getString("DealOperator") == null )
				this.DealOperator = null;
			else
				this.DealOperator = rs.getString("DealOperator").trim();

			if( rs.getString("RulePlanID") == null )
				this.RulePlanID = null;
			else
				this.RulePlanID = rs.getString("RulePlanID").trim();

			this.RuleDealDate = rs.getDate("RuleDealDate");
			if( rs.getString("RuleDealTime") == null )
				this.RuleDealTime = null;
			else
				this.RuleDealTime = rs.getString("RuleDealTime").trim();

			if( rs.getString("LogFilePath") == null )
				this.LogFilePath = null;
			else
				this.LogFilePath = rs.getString("LogFilePath").trim();

			if( rs.getString("LogFileName") == null )
				this.LogFileName = null;
			else
				this.LogFileName = rs.getString("LogFileName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIRuleDealLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRuleDealLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIRuleDealLogSchema getSchema()
	{
		FIRuleDealLogSchema aFIRuleDealLogSchema = new FIRuleDealLogSchema();
		aFIRuleDealLogSchema.setSchema(this);
		return aFIRuleDealLogSchema;
	}

	public FIRuleDealLogDB getDB()
	{
		FIRuleDealLogDB aDBOper = new FIRuleDealLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRuleDealLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(CheckSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleDealBatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataSourceBatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CallPointID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleDealResult)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DealOperator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RulePlanID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( RuleDealDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleDealTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LogFilePath)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LogFileName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRuleDealLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CheckSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleDealBatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DataSourceBatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CallPointID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RuleDealResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DealOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RulePlanID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RuleDealDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			RuleDealTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			LogFilePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			LogFileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRuleDealLogSchema";
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
		if (FCode.equalsIgnoreCase("CheckSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckSerialNo));
		}
		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VersionNo));
		}
		if (FCode.equalsIgnoreCase("RuleDealBatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealBatchNo));
		}
		if (FCode.equalsIgnoreCase("DataSourceBatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataSourceBatchNo));
		}
		if (FCode.equalsIgnoreCase("CallPointID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CallPointID));
		}
		if (FCode.equalsIgnoreCase("RuleDealResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealResult));
		}
		if (FCode.equalsIgnoreCase("DealOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealOperator));
		}
		if (FCode.equalsIgnoreCase("RulePlanID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RulePlanID));
		}
		if (FCode.equalsIgnoreCase("RuleDealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRuleDealDate()));
		}
		if (FCode.equalsIgnoreCase("RuleDealTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealTime));
		}
		if (FCode.equalsIgnoreCase("LogFilePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogFilePath));
		}
		if (FCode.equalsIgnoreCase("LogFileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogFileName));
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
				strFieldValue = StrTool.GBKToUnicode(CheckSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(VersionNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleDealBatchNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DataSourceBatchNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CallPointID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RuleDealResult);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DealOperator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RulePlanID);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRuleDealDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RuleDealTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(LogFilePath);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(LogFileName);
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

		if (FCode.equalsIgnoreCase("CheckSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckSerialNo = FValue.trim();
			}
			else
				CheckSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VersionNo = FValue.trim();
			}
			else
				VersionNo = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealBatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealBatchNo = FValue.trim();
			}
			else
				RuleDealBatchNo = null;
		}
		if (FCode.equalsIgnoreCase("DataSourceBatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataSourceBatchNo = FValue.trim();
			}
			else
				DataSourceBatchNo = null;
		}
		if (FCode.equalsIgnoreCase("CallPointID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CallPointID = FValue.trim();
			}
			else
				CallPointID = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealResult = FValue.trim();
			}
			else
				RuleDealResult = null;
		}
		if (FCode.equalsIgnoreCase("DealOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealOperator = FValue.trim();
			}
			else
				DealOperator = null;
		}
		if (FCode.equalsIgnoreCase("RulePlanID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RulePlanID = FValue.trim();
			}
			else
				RulePlanID = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RuleDealDate = fDate.getDate( FValue );
			}
			else
				RuleDealDate = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealTime = FValue.trim();
			}
			else
				RuleDealTime = null;
		}
		if (FCode.equalsIgnoreCase("LogFilePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogFilePath = FValue.trim();
			}
			else
				LogFilePath = null;
		}
		if (FCode.equalsIgnoreCase("LogFileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogFileName = FValue.trim();
			}
			else
				LogFileName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIRuleDealLogSchema other = (FIRuleDealLogSchema)otherObject;
		return
			CheckSerialNo.equals(other.getCheckSerialNo())
			&& VersionNo.equals(other.getVersionNo())
			&& RuleDealBatchNo.equals(other.getRuleDealBatchNo())
			&& DataSourceBatchNo.equals(other.getDataSourceBatchNo())
			&& CallPointID.equals(other.getCallPointID())
			&& RuleDealResult.equals(other.getRuleDealResult())
			&& DealOperator.equals(other.getDealOperator())
			&& RulePlanID.equals(other.getRulePlanID())
			&& fDate.getString(RuleDealDate).equals(other.getRuleDealDate())
			&& RuleDealTime.equals(other.getRuleDealTime())
			&& LogFilePath.equals(other.getLogFilePath())
			&& LogFileName.equals(other.getLogFileName());
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
		if( strFieldName.equals("CheckSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("VersionNo") ) {
			return 1;
		}
		if( strFieldName.equals("RuleDealBatchNo") ) {
			return 2;
		}
		if( strFieldName.equals("DataSourceBatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("CallPointID") ) {
			return 4;
		}
		if( strFieldName.equals("RuleDealResult") ) {
			return 5;
		}
		if( strFieldName.equals("DealOperator") ) {
			return 6;
		}
		if( strFieldName.equals("RulePlanID") ) {
			return 7;
		}
		if( strFieldName.equals("RuleDealDate") ) {
			return 8;
		}
		if( strFieldName.equals("RuleDealTime") ) {
			return 9;
		}
		if( strFieldName.equals("LogFilePath") ) {
			return 10;
		}
		if( strFieldName.equals("LogFileName") ) {
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
				strFieldName = "CheckSerialNo";
				break;
			case 1:
				strFieldName = "VersionNo";
				break;
			case 2:
				strFieldName = "RuleDealBatchNo";
				break;
			case 3:
				strFieldName = "DataSourceBatchNo";
				break;
			case 4:
				strFieldName = "CallPointID";
				break;
			case 5:
				strFieldName = "RuleDealResult";
				break;
			case 6:
				strFieldName = "DealOperator";
				break;
			case 7:
				strFieldName = "RulePlanID";
				break;
			case 8:
				strFieldName = "RuleDealDate";
				break;
			case 9:
				strFieldName = "RuleDealTime";
				break;
			case 10:
				strFieldName = "LogFilePath";
				break;
			case 11:
				strFieldName = "LogFileName";
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
		if( strFieldName.equals("CheckSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VersionNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealBatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataSourceBatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CallPointID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RulePlanID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RuleDealTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogFilePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogFileName") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
