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
import com.sinosoft.lis.db.ES_DOC_DEFDB;

/*
 * <p>ClassName: ES_DOC_DEFSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOC_DEFSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOC_DEFSchema.class);

	// @Field
	/** 业务类型编码 */
	private String BussType;
	/** 业务类型名称 */
	private String BussTypeName;
	/** 单证细类编码 */
	private String SubType;
	/** 单证细类名称 */
	private String SubTypeName;
	/** 有效标志 */
	private String ValidFlag;
	/** 单证纸张类型 */
	private String PaperType;
	/** 是否作为业务号码 */
	private String CodeFlag;
	/** 新案例开始标识 */
	private String NewCaseFlag;
	/** 单证版本 */
	private String Version;
	/** 单证号码长度 */
	private int CodeLen;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOC_DEFSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "BussType";
		pk[1] = "SubType";

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
		ES_DOC_DEFSchema cloned = (ES_DOC_DEFSchema)super.clone();
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
	public String getBussTypeName()
	{
		return BussTypeName;
	}
	public void setBussTypeName(String aBussTypeName)
	{
		BussTypeName = aBussTypeName;
	}
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
	}
	public String getSubTypeName()
	{
		return SubTypeName;
	}
	public void setSubTypeName(String aSubTypeName)
	{
		SubTypeName = aSubTypeName;
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
	public String getPaperType()
	{
		return PaperType;
	}
	public void setPaperType(String aPaperType)
	{
		PaperType = aPaperType;
	}
	/**
	* 0-是<p>
	* 1-否
	*/
	public String getCodeFlag()
	{
		return CodeFlag;
	}
	public void setCodeFlag(String aCodeFlag)
	{
		CodeFlag = aCodeFlag;
	}
	/**
	* 0-是<p>
	* 1-否
	*/
	public String getNewCaseFlag()
	{
		return NewCaseFlag;
	}
	public void setNewCaseFlag(String aNewCaseFlag)
	{
		NewCaseFlag = aNewCaseFlag;
	}
	public String getVersion()
	{
		return Version;
	}
	public void setVersion(String aVersion)
	{
		Version = aVersion;
	}
	public int getCodeLen()
	{
		return CodeLen;
	}
	public void setCodeLen(int aCodeLen)
	{
		CodeLen = aCodeLen;
	}
	public void setCodeLen(String aCodeLen)
	{
		if (aCodeLen != null && !aCodeLen.equals(""))
		{
			Integer tInteger = new Integer(aCodeLen);
			int i = tInteger.intValue();
			CodeLen = i;
		}
	}


	/**
	* 使用另外一个 ES_DOC_DEFSchema 对象给 Schema 赋值
	* @param: aES_DOC_DEFSchema ES_DOC_DEFSchema
	**/
	public void setSchema(ES_DOC_DEFSchema aES_DOC_DEFSchema)
	{
		this.BussType = aES_DOC_DEFSchema.getBussType();
		this.BussTypeName = aES_DOC_DEFSchema.getBussTypeName();
		this.SubType = aES_DOC_DEFSchema.getSubType();
		this.SubTypeName = aES_DOC_DEFSchema.getSubTypeName();
		this.ValidFlag = aES_DOC_DEFSchema.getValidFlag();
		this.PaperType = aES_DOC_DEFSchema.getPaperType();
		this.CodeFlag = aES_DOC_DEFSchema.getCodeFlag();
		this.NewCaseFlag = aES_DOC_DEFSchema.getNewCaseFlag();
		this.Version = aES_DOC_DEFSchema.getVersion();
		this.CodeLen = aES_DOC_DEFSchema.getCodeLen();
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

			if( rs.getString("BussTypeName") == null )
				this.BussTypeName = null;
			else
				this.BussTypeName = rs.getString("BussTypeName").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("SubTypeName") == null )
				this.SubTypeName = null;
			else
				this.SubTypeName = rs.getString("SubTypeName").trim();

			if( rs.getString("ValidFlag") == null )
				this.ValidFlag = null;
			else
				this.ValidFlag = rs.getString("ValidFlag").trim();

			if( rs.getString("PaperType") == null )
				this.PaperType = null;
			else
				this.PaperType = rs.getString("PaperType").trim();

			if( rs.getString("CodeFlag") == null )
				this.CodeFlag = null;
			else
				this.CodeFlag = rs.getString("CodeFlag").trim();

			if( rs.getString("NewCaseFlag") == null )
				this.NewCaseFlag = null;
			else
				this.NewCaseFlag = rs.getString("NewCaseFlag").trim();

			if( rs.getString("Version") == null )
				this.Version = null;
			else
				this.Version = rs.getString("Version").trim();

			this.CodeLen = rs.getInt("CodeLen");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOC_DEF表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_DEFSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOC_DEFSchema getSchema()
	{
		ES_DOC_DEFSchema aES_DOC_DEFSchema = new ES_DOC_DEFSchema();
		aES_DOC_DEFSchema.setSchema(this);
		return aES_DOC_DEFSchema;
	}

	public ES_DOC_DEFDB getDB()
	{
		ES_DOC_DEFDB aDBOper = new ES_DOC_DEFDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_DEF描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PaperType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewCaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Version)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CodeLen));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_DEF>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BussTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ValidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PaperType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CodeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NewCaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Version = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CodeLen= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_DEFSchema";
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
		if (FCode.equalsIgnoreCase("BussTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussTypeName));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("SubTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubTypeName));
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidFlag));
		}
		if (FCode.equalsIgnoreCase("PaperType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PaperType));
		}
		if (FCode.equalsIgnoreCase("CodeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeFlag));
		}
		if (FCode.equalsIgnoreCase("NewCaseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewCaseFlag));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
		}
		if (FCode.equalsIgnoreCase("CodeLen"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeLen));
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
				strFieldValue = StrTool.GBKToUnicode(BussTypeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubTypeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ValidFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PaperType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CodeFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NewCaseFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Version);
				break;
			case 9:
				strFieldValue = String.valueOf(CodeLen);
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
		if (FCode.equalsIgnoreCase("BussTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussTypeName = FValue.trim();
			}
			else
				BussTypeName = null;
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
		if (FCode.equalsIgnoreCase("SubTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubTypeName = FValue.trim();
			}
			else
				SubTypeName = null;
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
		if (FCode.equalsIgnoreCase("PaperType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PaperType = FValue.trim();
			}
			else
				PaperType = null;
		}
		if (FCode.equalsIgnoreCase("CodeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeFlag = FValue.trim();
			}
			else
				CodeFlag = null;
		}
		if (FCode.equalsIgnoreCase("NewCaseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewCaseFlag = FValue.trim();
			}
			else
				NewCaseFlag = null;
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Version = FValue.trim();
			}
			else
				Version = null;
		}
		if (FCode.equalsIgnoreCase("CodeLen"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CodeLen = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOC_DEFSchema other = (ES_DOC_DEFSchema)otherObject;
		return
			BussType.equals(other.getBussType())
			&& BussTypeName.equals(other.getBussTypeName())
			&& SubType.equals(other.getSubType())
			&& SubTypeName.equals(other.getSubTypeName())
			&& ValidFlag.equals(other.getValidFlag())
			&& PaperType.equals(other.getPaperType())
			&& CodeFlag.equals(other.getCodeFlag())
			&& NewCaseFlag.equals(other.getNewCaseFlag())
			&& Version.equals(other.getVersion())
			&& CodeLen == other.getCodeLen();
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
		if( strFieldName.equals("BussTypeName") ) {
			return 1;
		}
		if( strFieldName.equals("SubType") ) {
			return 2;
		}
		if( strFieldName.equals("SubTypeName") ) {
			return 3;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return 4;
		}
		if( strFieldName.equals("PaperType") ) {
			return 5;
		}
		if( strFieldName.equals("CodeFlag") ) {
			return 6;
		}
		if( strFieldName.equals("NewCaseFlag") ) {
			return 7;
		}
		if( strFieldName.equals("Version") ) {
			return 8;
		}
		if( strFieldName.equals("CodeLen") ) {
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
				strFieldName = "BussType";
				break;
			case 1:
				strFieldName = "BussTypeName";
				break;
			case 2:
				strFieldName = "SubType";
				break;
			case 3:
				strFieldName = "SubTypeName";
				break;
			case 4:
				strFieldName = "ValidFlag";
				break;
			case 5:
				strFieldName = "PaperType";
				break;
			case 6:
				strFieldName = "CodeFlag";
				break;
			case 7:
				strFieldName = "NewCaseFlag";
				break;
			case 8:
				strFieldName = "Version";
				break;
			case 9:
				strFieldName = "CodeLen";
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
		if( strFieldName.equals("BussTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PaperType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewCaseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Version") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeLen") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
