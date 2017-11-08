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
import com.sinosoft.lis.db.LRResultDetailDB;

/*
 * <p>ClassName: LRResultDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎
 */
public class LRResultDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRResultDetailSchema.class);

	// @Field
	/** 执行批次号 */
	private String SerialNo;
	/** 执行顺序号 */
	private int No;
	/** 模板编号 */
	private String TemplateId;
	/** 模板版本号 */
	private int Version;
	/** 规则配置项编号2 */
	private String RuleId;
	/** 行异常标志 */
	private String ExceptionFlag;
	/** 执行结果 */
	private String Result;
	/** 人工核保级别 */
	private String UWLevel;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRResultDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "No";

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
		LRResultDetailSchema cloned = (LRResultDetailSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 在一系列规则处理中多条规则的执行顺序
	*/
	public int getNo()
	{
		return No;
	}
	public void setNo(int aNo)
	{
		No = aNo;
	}
	public void setNo(String aNo)
	{
		if (aNo != null && !aNo.equals(""))
		{
			Integer tInteger = new Integer(aNo);
			int i = tInteger.intValue();
			No = i;
		}
	}

	public String getTemplateId()
	{
		return TemplateId;
	}
	public void setTemplateId(String aTemplateId)
	{
		TemplateId = aTemplateId;
	}
	public int getVersion()
	{
		return Version;
	}
	public void setVersion(int aVersion)
	{
		Version = aVersion;
	}
	public void setVersion(String aVersion)
	{
		if (aVersion != null && !aVersion.equals(""))
		{
			Integer tInteger = new Integer(aVersion);
			int i = tInteger.intValue();
			Version = i;
		}
	}

	public String getRuleId()
	{
		return RuleId;
	}
	public void setRuleId(String aRuleId)
	{
		RuleId = aRuleId;
	}
	/**
	* 0——执行异常<p>
	* 1——正常执行
	*/
	public String getExceptionFlag()
	{
		return ExceptionFlag;
	}
	public void setExceptionFlag(String aExceptionFlag)
	{
		ExceptionFlag = aExceptionFlag;
	}
	/**
	* 0——不通过<p>
	* 1——通过
	*/
	public String getResult()
	{
		return Result;
	}
	public void setResult(String aResult)
	{
		Result = aResult;
	}
	public String getUWLevel()
	{
		return UWLevel;
	}
	public void setUWLevel(String aUWLevel)
	{
		UWLevel = aUWLevel;
	}

	/**
	* 使用另外一个 LRResultDetailSchema 对象给 Schema 赋值
	* @param: aLRResultDetailSchema LRResultDetailSchema
	**/
	public void setSchema(LRResultDetailSchema aLRResultDetailSchema)
	{
		this.SerialNo = aLRResultDetailSchema.getSerialNo();
		this.No = aLRResultDetailSchema.getNo();
		this.TemplateId = aLRResultDetailSchema.getTemplateId();
		this.Version = aLRResultDetailSchema.getVersion();
		this.RuleId = aLRResultDetailSchema.getRuleId();
		this.ExceptionFlag = aLRResultDetailSchema.getExceptionFlag();
		this.Result = aLRResultDetailSchema.getResult();
		this.UWLevel = aLRResultDetailSchema.getUWLevel();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			this.No = rs.getInt("No");
			if( rs.getString("TemplateId") == null )
				this.TemplateId = null;
			else
				this.TemplateId = rs.getString("TemplateId").trim();

			this.Version = rs.getInt("Version");
			if( rs.getString("RuleId") == null )
				this.RuleId = null;
			else
				this.RuleId = rs.getString("RuleId").trim();

			if( rs.getString("ExceptionFlag") == null )
				this.ExceptionFlag = null;
			else
				this.ExceptionFlag = rs.getString("ExceptionFlag").trim();

			if( rs.getString("Result") == null )
				this.Result = null;
			else
				this.Result = rs.getString("Result").trim();

			if( rs.getString("UWLevel") == null )
				this.UWLevel = null;
			else
				this.UWLevel = rs.getString("UWLevel").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRResultDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRResultDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRResultDetailSchema getSchema()
	{
		LRResultDetailSchema aLRResultDetailSchema = new LRResultDetailSchema();
		aLRResultDetailSchema.setSchema(this);
		return aLRResultDetailSchema;
	}

	public LRResultDetailDB getDB()
	{
		LRResultDetailDB aDBOper = new LRResultDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRResultDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(No));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TemplateId)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Version));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleId)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExceptionFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Result)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWLevel));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRResultDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			No= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			TemplateId = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Version= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			RuleId = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ExceptionFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Result = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UWLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRResultDetailSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("No"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(No));
		}
		if (FCode.equalsIgnoreCase("TemplateId"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TemplateId));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
		}
		if (FCode.equalsIgnoreCase("RuleId"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleId));
		}
		if (FCode.equalsIgnoreCase("ExceptionFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExceptionFlag));
		}
		if (FCode.equalsIgnoreCase("Result"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Result));
		}
		if (FCode.equalsIgnoreCase("UWLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWLevel));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = String.valueOf(No);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TemplateId);
				break;
			case 3:
				strFieldValue = String.valueOf(Version);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleId);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ExceptionFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Result);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UWLevel);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("No"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				No = i;
			}
		}
		if (FCode.equalsIgnoreCase("TemplateId"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TemplateId = FValue.trim();
			}
			else
				TemplateId = null;
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Version = i;
			}
		}
		if (FCode.equalsIgnoreCase("RuleId"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleId = FValue.trim();
			}
			else
				RuleId = null;
		}
		if (FCode.equalsIgnoreCase("ExceptionFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExceptionFlag = FValue.trim();
			}
			else
				ExceptionFlag = null;
		}
		if (FCode.equalsIgnoreCase("Result"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Result = FValue.trim();
			}
			else
				Result = null;
		}
		if (FCode.equalsIgnoreCase("UWLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWLevel = FValue.trim();
			}
			else
				UWLevel = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRResultDetailSchema other = (LRResultDetailSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& No == other.getNo()
			&& TemplateId.equals(other.getTemplateId())
			&& Version == other.getVersion()
			&& RuleId.equals(other.getRuleId())
			&& ExceptionFlag.equals(other.getExceptionFlag())
			&& Result.equals(other.getResult())
			&& UWLevel.equals(other.getUWLevel());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("No") ) {
			return 1;
		}
		if( strFieldName.equals("TemplateId") ) {
			return 2;
		}
		if( strFieldName.equals("Version") ) {
			return 3;
		}
		if( strFieldName.equals("RuleId") ) {
			return 4;
		}
		if( strFieldName.equals("ExceptionFlag") ) {
			return 5;
		}
		if( strFieldName.equals("Result") ) {
			return 6;
		}
		if( strFieldName.equals("UWLevel") ) {
			return 7;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "No";
				break;
			case 2:
				strFieldName = "TemplateId";
				break;
			case 3:
				strFieldName = "Version";
				break;
			case 4:
				strFieldName = "RuleId";
				break;
			case 5:
				strFieldName = "ExceptionFlag";
				break;
			case 6:
				strFieldName = "Result";
				break;
			case 7:
				strFieldName = "UWLevel";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("No") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TemplateId") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Version") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RuleId") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExceptionFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Result") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWLevel") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
