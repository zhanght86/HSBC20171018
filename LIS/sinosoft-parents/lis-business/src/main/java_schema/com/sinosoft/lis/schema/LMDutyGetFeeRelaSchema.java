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
import com.sinosoft.lis.db.LMDutyGetFeeRelaDB;

/*
 * <p>ClassName: LMDutyGetFeeRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyGetFeeRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyGetFeeRelaSchema.class);

	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 给付名称 */
	private String GetDutyName;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 账单项目编码 */
	private String ClmFeeCode;
	/** 账单项目名称 */
	private String ClmFeeName;
	/** 费用计算方式 */
	private String ClmFeeCalType;
	/** 费用明细计算公式 */
	private String ClmFeeCalCode;
	/** 费用默认值 */
	private String ClmFeeDefValue;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyGetFeeRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "GetDutyCode";
		pk[1] = "GetDutyKind";
		pk[2] = "ClmFeeCode";

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
		LMDutyGetFeeRelaSchema cloned = (LMDutyGetFeeRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	/**
	* X02--身故金 X01--残疾金 X00--医疗 X为1，指意外；X为2，指疾病 001--临时保单意外死亡
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getClmFeeCode()
	{
		return ClmFeeCode;
	}
	public void setClmFeeCode(String aClmFeeCode)
	{
		ClmFeeCode = aClmFeeCode;
	}
	public String getClmFeeName()
	{
		return ClmFeeName;
	}
	public void setClmFeeName(String aClmFeeName)
	{
		ClmFeeName = aClmFeeName;
	}
	/**
	* 00-取默认值 01-录入 02-使用计算公式
	*/
	public String getClmFeeCalType()
	{
		return ClmFeeCalType;
	}
	public void setClmFeeCalType(String aClmFeeCalType)
	{
		ClmFeeCalType = aClmFeeCalType;
	}
	public String getClmFeeCalCode()
	{
		return ClmFeeCalCode;
	}
	public void setClmFeeCalCode(String aClmFeeCalCode)
	{
		ClmFeeCalCode = aClmFeeCalCode;
	}
	public String getClmFeeDefValue()
	{
		return ClmFeeDefValue;
	}
	public void setClmFeeDefValue(String aClmFeeDefValue)
	{
		ClmFeeDefValue = aClmFeeDefValue;
	}

	/**
	* 使用另外一个 LMDutyGetFeeRelaSchema 对象给 Schema 赋值
	* @param: aLMDutyGetFeeRelaSchema LMDutyGetFeeRelaSchema
	**/
	public void setSchema(LMDutyGetFeeRelaSchema aLMDutyGetFeeRelaSchema)
	{
		this.GetDutyCode = aLMDutyGetFeeRelaSchema.getGetDutyCode();
		this.GetDutyName = aLMDutyGetFeeRelaSchema.getGetDutyName();
		this.GetDutyKind = aLMDutyGetFeeRelaSchema.getGetDutyKind();
		this.ClmFeeCode = aLMDutyGetFeeRelaSchema.getClmFeeCode();
		this.ClmFeeName = aLMDutyGetFeeRelaSchema.getClmFeeName();
		this.ClmFeeCalType = aLMDutyGetFeeRelaSchema.getClmFeeCalType();
		this.ClmFeeCalCode = aLMDutyGetFeeRelaSchema.getClmFeeCalCode();
		this.ClmFeeDefValue = aLMDutyGetFeeRelaSchema.getClmFeeDefValue();
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
			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("ClmFeeCode") == null )
				this.ClmFeeCode = null;
			else
				this.ClmFeeCode = rs.getString("ClmFeeCode").trim();

			if( rs.getString("ClmFeeName") == null )
				this.ClmFeeName = null;
			else
				this.ClmFeeName = rs.getString("ClmFeeName").trim();

			if( rs.getString("ClmFeeCalType") == null )
				this.ClmFeeCalType = null;
			else
				this.ClmFeeCalType = rs.getString("ClmFeeCalType").trim();

			if( rs.getString("ClmFeeCalCode") == null )
				this.ClmFeeCalCode = null;
			else
				this.ClmFeeCalCode = rs.getString("ClmFeeCalCode").trim();

			if( rs.getString("ClmFeeDefValue") == null )
				this.ClmFeeDefValue = null;
			else
				this.ClmFeeDefValue = rs.getString("ClmFeeDefValue").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyGetFeeRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyGetFeeRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyGetFeeRelaSchema getSchema()
	{
		LMDutyGetFeeRelaSchema aLMDutyGetFeeRelaSchema = new LMDutyGetFeeRelaSchema();
		aLMDutyGetFeeRelaSchema.setSchema(this);
		return aLMDutyGetFeeRelaSchema;
	}

	public LMDutyGetFeeRelaDB getDB()
	{
		LMDutyGetFeeRelaDB aDBOper = new LMDutyGetFeeRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyGetFeeRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeDefValue));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyGetFeeRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmFeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClmFeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ClmFeeCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClmFeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ClmFeeDefValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyGetFeeRelaSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("ClmFeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeCode));
		}
		if (FCode.equalsIgnoreCase("ClmFeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeName));
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeCalType));
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeCalCode));
		}
		if (FCode.equalsIgnoreCase("ClmFeeDefValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeDefValue));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeCalType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeCalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeDefValue);
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

		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeCode = FValue.trim();
			}
			else
				ClmFeeCode = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeName = FValue.trim();
			}
			else
				ClmFeeName = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeCalType = FValue.trim();
			}
			else
				ClmFeeCalType = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeCalCode = FValue.trim();
			}
			else
				ClmFeeCalCode = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeDefValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeDefValue = FValue.trim();
			}
			else
				ClmFeeDefValue = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyGetFeeRelaSchema other = (LMDutyGetFeeRelaSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyName.equals(other.getGetDutyName())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& ClmFeeCode.equals(other.getClmFeeCode())
			&& ClmFeeName.equals(other.getClmFeeName())
			&& ClmFeeCalType.equals(other.getClmFeeCalType())
			&& ClmFeeCalCode.equals(other.getClmFeeCalCode())
			&& ClmFeeDefValue.equals(other.getClmFeeDefValue());
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
		if( strFieldName.equals("GetDutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 1;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 2;
		}
		if( strFieldName.equals("ClmFeeCode") ) {
			return 3;
		}
		if( strFieldName.equals("ClmFeeName") ) {
			return 4;
		}
		if( strFieldName.equals("ClmFeeCalType") ) {
			return 5;
		}
		if( strFieldName.equals("ClmFeeCalCode") ) {
			return 6;
		}
		if( strFieldName.equals("ClmFeeDefValue") ) {
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
				strFieldName = "GetDutyCode";
				break;
			case 1:
				strFieldName = "GetDutyName";
				break;
			case 2:
				strFieldName = "GetDutyKind";
				break;
			case 3:
				strFieldName = "ClmFeeCode";
				break;
			case 4:
				strFieldName = "ClmFeeName";
				break;
			case 5:
				strFieldName = "ClmFeeCalType";
				break;
			case 6:
				strFieldName = "ClmFeeCalCode";
				break;
			case 7:
				strFieldName = "ClmFeeDefValue";
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
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeDefValue") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
