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
import com.sinosoft.lis.db.LMCalFactorSortDB;

/*
 * <p>ClassName: LMCalFactorSortSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMCalFactorSortSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMCalFactorSortSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 给付代码 */
	private String GetDutyCode;
	/** 责任代码 */
	private String DutyCode;
	/** 要素类别 */
	private String FactorType;
	/** 要素子类别 */
	private String FactorSubType;
	/** 计算要素名 */
	private String FactorName;
	/** 计算要素说明 */
	private String FactorDesc;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCalFactorSortSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "RiskCode";
		pk[1] = "GetDutyCode";
		pk[2] = "DutyCode";
		pk[3] = "FactorType";
		pk[4] = "FactorSubType";
		pk[5] = "FactorName";

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
		LMCalFactorSortSchema cloned = (LMCalFactorSortSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 如果没有启用，用000000表示
	*/
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	/**
	* 如果没有启用，用000000表示
	*/
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	/**
	* 其中：<p>
	* 1-新契约<p>
	* 2-保全<p>
	* 3-理赔<p>
	* -------------------------------------<p>
	* 理赔<p>
	* 31-筛选算法<p>
	* 32-理算算法
	*/
	public String getFactorType()
	{
		return FactorType;
	}
	public void setFactorType(String aFactorType)
	{
		FactorType = aFactorType;
	}
	/**
	* 用于区分更细的分类<p>
	* 例如：<p>
	* 0代表正常计算<p>
	* 1。。n代表其他情况的计算
	*/
	public String getFactorSubType()
	{
		return FactorSubType;
	}
	public void setFactorSubType(String aFactorSubType)
	{
		FactorSubType = aFactorSubType;
	}
	public String getFactorName()
	{
		return FactorName;
	}
	public void setFactorName(String aFactorName)
	{
		FactorName = aFactorName;
	}
	public String getFactorDesc()
	{
		return FactorDesc;
	}
	public void setFactorDesc(String aFactorDesc)
	{
		FactorDesc = aFactorDesc;
	}

	/**
	* 使用另外一个 LMCalFactorSortSchema 对象给 Schema 赋值
	* @param: aLMCalFactorSortSchema LMCalFactorSortSchema
	**/
	public void setSchema(LMCalFactorSortSchema aLMCalFactorSortSchema)
	{
		this.RiskCode = aLMCalFactorSortSchema.getRiskCode();
		this.GetDutyCode = aLMCalFactorSortSchema.getGetDutyCode();
		this.DutyCode = aLMCalFactorSortSchema.getDutyCode();
		this.FactorType = aLMCalFactorSortSchema.getFactorType();
		this.FactorSubType = aLMCalFactorSortSchema.getFactorSubType();
		this.FactorName = aLMCalFactorSortSchema.getFactorName();
		this.FactorDesc = aLMCalFactorSortSchema.getFactorDesc();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("FactorType") == null )
				this.FactorType = null;
			else
				this.FactorType = rs.getString("FactorType").trim();

			if( rs.getString("FactorSubType") == null )
				this.FactorSubType = null;
			else
				this.FactorSubType = rs.getString("FactorSubType").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("FactorDesc") == null )
				this.FactorDesc = null;
			else
				this.FactorDesc = rs.getString("FactorDesc").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMCalFactorSort表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalFactorSortSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMCalFactorSortSchema getSchema()
	{
		LMCalFactorSortSchema aLMCalFactorSortSchema = new LMCalFactorSortSchema();
		aLMCalFactorSortSchema.setSchema(this);
		return aLMCalFactorSortSchema;
	}

	public LMCalFactorSortDB getDB()
	{
		LMCalFactorSortDB aDBOper = new LMCalFactorSortDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalFactorSort描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorSubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalFactorSort>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FactorSubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FactorDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalFactorSortSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("FactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorType));
		}
		if (FCode.equalsIgnoreCase("FactorSubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorSubType));
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equalsIgnoreCase("FactorDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorDesc));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactorType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FactorSubType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FactorDesc);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorType = FValue.trim();
			}
			else
				FactorType = null;
		}
		if (FCode.equalsIgnoreCase("FactorSubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorSubType = FValue.trim();
			}
			else
				FactorSubType = null;
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorName = FValue.trim();
			}
			else
				FactorName = null;
		}
		if (FCode.equalsIgnoreCase("FactorDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorDesc = FValue.trim();
			}
			else
				FactorDesc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCalFactorSortSchema other = (LMCalFactorSortSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& DutyCode.equals(other.getDutyCode())
			&& FactorType.equals(other.getFactorType())
			&& FactorSubType.equals(other.getFactorSubType())
			&& FactorName.equals(other.getFactorName())
			&& FactorDesc.equals(other.getFactorDesc());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 1;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("FactorType") ) {
			return 3;
		}
		if( strFieldName.equals("FactorSubType") ) {
			return 4;
		}
		if( strFieldName.equals("FactorName") ) {
			return 5;
		}
		if( strFieldName.equals("FactorDesc") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "GetDutyCode";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "FactorType";
				break;
			case 4:
				strFieldName = "FactorSubType";
				break;
			case 5:
				strFieldName = "FactorName";
				break;
			case 6:
				strFieldName = "FactorDesc";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorSubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorDesc") ) {
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
