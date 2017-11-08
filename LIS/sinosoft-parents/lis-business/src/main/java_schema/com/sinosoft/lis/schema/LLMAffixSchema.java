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
import com.sinosoft.lis.db.LLMAffixDB;

/*
 * <p>ClassName: LLMAffixSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLMAffixSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLMAffixSchema.class);
	// @Field
	/** 材料类型代码 */
	private String AffixTypeCode;
	/** 材料类型名称 */
	private String AffixTypeName;
	/** 材料代码 */
	private String AffixCode;
	/** 材料名称 */
	private String AffixName;
	/** 管理机构 */
	private String ManageCome;
	/** 必需标志 */
	private String NeedFlag;
	/** 是否原件 */
	private String OriFlag;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLMAffixSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "AffixTypeCode";
		pk[1] = "AffixCode";
		pk[2] = "ManageCome";

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
		LLMAffixSchema cloned = (LLMAffixSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAffixTypeCode()
	{
		return AffixTypeCode;
	}
	public void setAffixTypeCode(String aAffixTypeCode)
	{
		if(aAffixTypeCode!=null && aAffixTypeCode.length()>6)
			throw new IllegalArgumentException("材料类型代码AffixTypeCode值"+aAffixTypeCode+"的长度"+aAffixTypeCode.length()+"大于最大值6");
		AffixTypeCode = aAffixTypeCode;
	}
	public String getAffixTypeName()
	{
		return AffixTypeName;
	}
	public void setAffixTypeName(String aAffixTypeName)
	{
		if(aAffixTypeName!=null && aAffixTypeName.length()>100)
			throw new IllegalArgumentException("材料类型名称AffixTypeName值"+aAffixTypeName+"的长度"+aAffixTypeName.length()+"大于最大值100");
		AffixTypeName = aAffixTypeName;
	}
	public String getAffixCode()
	{
		return AffixCode;
	}
	public void setAffixCode(String aAffixCode)
	{
		if(aAffixCode!=null && aAffixCode.length()>10)
			throw new IllegalArgumentException("材料代码AffixCode值"+aAffixCode+"的长度"+aAffixCode.length()+"大于最大值10");
		AffixCode = aAffixCode;
	}
	public String getAffixName()
	{
		return AffixName;
	}
	public void setAffixName(String aAffixName)
	{
		if(aAffixName!=null && aAffixName.length()>450)
			throw new IllegalArgumentException("材料名称AffixName值"+aAffixName+"的长度"+aAffixName.length()+"大于最大值450");
		AffixName = aAffixName;
	}
	public String getManageCome()
	{
		return ManageCome;
	}
	public void setManageCome(String aManageCome)
	{
		if(aManageCome!=null && aManageCome.length()>10)
			throw new IllegalArgumentException("管理机构ManageCome值"+aManageCome+"的长度"+aManageCome.length()+"大于最大值10");
		ManageCome = aManageCome;
	}
	/**
	* 0必需<p>
	* 1非必需
	*/
	public String getNeedFlag()
	{
		return NeedFlag;
	}
	public void setNeedFlag(String aNeedFlag)
	{
		if(aNeedFlag!=null && aNeedFlag.length()>6)
			throw new IllegalArgumentException("必需标志NeedFlag值"+aNeedFlag+"的长度"+aNeedFlag.length()+"大于最大值6");
		NeedFlag = aNeedFlag;
	}
	/**
	* 0是<p>
	* 1否
	*/
	public String getOriFlag()
	{
		return OriFlag;
	}
	public void setOriFlag(String aOriFlag)
	{
		if(aOriFlag!=null && aOriFlag.length()>6)
			throw new IllegalArgumentException("是否原件OriFlag值"+aOriFlag+"的长度"+aOriFlag.length()+"大于最大值6");
		OriFlag = aOriFlag;
	}

	/**
	* 使用另外一个 LLMAffixSchema 对象给 Schema 赋值
	* @param: aLLMAffixSchema LLMAffixSchema
	**/
	public void setSchema(LLMAffixSchema aLLMAffixSchema)
	{
		this.AffixTypeCode = aLLMAffixSchema.getAffixTypeCode();
		this.AffixTypeName = aLLMAffixSchema.getAffixTypeName();
		this.AffixCode = aLLMAffixSchema.getAffixCode();
		this.AffixName = aLLMAffixSchema.getAffixName();
		this.ManageCome = aLLMAffixSchema.getManageCome();
		this.NeedFlag = aLLMAffixSchema.getNeedFlag();
		this.OriFlag = aLLMAffixSchema.getOriFlag();
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
			if( rs.getString("AffixTypeCode") == null )
				this.AffixTypeCode = null;
			else
				this.AffixTypeCode = rs.getString("AffixTypeCode").trim();

			if( rs.getString("AffixTypeName") == null )
				this.AffixTypeName = null;
			else
				this.AffixTypeName = rs.getString("AffixTypeName").trim();

			if( rs.getString("AffixCode") == null )
				this.AffixCode = null;
			else
				this.AffixCode = rs.getString("AffixCode").trim();

			if( rs.getString("AffixName") == null )
				this.AffixName = null;
			else
				this.AffixName = rs.getString("AffixName").trim();

			if( rs.getString("ManageCome") == null )
				this.ManageCome = null;
			else
				this.ManageCome = rs.getString("ManageCome").trim();

			if( rs.getString("NeedFlag") == null )
				this.NeedFlag = null;
			else
				this.NeedFlag = rs.getString("NeedFlag").trim();

			if( rs.getString("OriFlag") == null )
				this.OriFlag = null;
			else
				this.OriFlag = rs.getString("OriFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLMAffix表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMAffixSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLMAffixSchema getSchema()
	{
		LLMAffixSchema aLLMAffixSchema = new LLMAffixSchema();
		aLLMAffixSchema.setSchema(this);
		return aLLMAffixSchema;
	}

	public LLMAffixDB getDB()
	{
		LLMAffixDB aDBOper = new LLMAffixDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMAffix描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AffixTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCome)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMAffix>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AffixTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AffixTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AffixCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AffixName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageCome = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NeedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OriFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMAffixSchema";
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
		if (FCode.equalsIgnoreCase("AffixTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixTypeCode));
		}
		if (FCode.equalsIgnoreCase("AffixTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixTypeName));
		}
		if (FCode.equalsIgnoreCase("AffixCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixCode));
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixName));
		}
		if (FCode.equalsIgnoreCase("ManageCome"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCome));
		}
		if (FCode.equalsIgnoreCase("NeedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedFlag));
		}
		if (FCode.equalsIgnoreCase("OriFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriFlag));
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
				strFieldValue = StrTool.GBKToUnicode(AffixTypeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AffixTypeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AffixCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AffixName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCome);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NeedFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OriFlag);
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

		if (FCode.equalsIgnoreCase("AffixTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixTypeCode = FValue.trim();
			}
			else
				AffixTypeCode = null;
		}
		if (FCode.equalsIgnoreCase("AffixTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixTypeName = FValue.trim();
			}
			else
				AffixTypeName = null;
		}
		if (FCode.equalsIgnoreCase("AffixCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixCode = FValue.trim();
			}
			else
				AffixCode = null;
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixName = FValue.trim();
			}
			else
				AffixName = null;
		}
		if (FCode.equalsIgnoreCase("ManageCome"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCome = FValue.trim();
			}
			else
				ManageCome = null;
		}
		if (FCode.equalsIgnoreCase("NeedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedFlag = FValue.trim();
			}
			else
				NeedFlag = null;
		}
		if (FCode.equalsIgnoreCase("OriFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriFlag = FValue.trim();
			}
			else
				OriFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLMAffixSchema other = (LLMAffixSchema)otherObject;
		return
			AffixTypeCode.equals(other.getAffixTypeCode())
			&& AffixTypeName.equals(other.getAffixTypeName())
			&& AffixCode.equals(other.getAffixCode())
			&& AffixName.equals(other.getAffixName())
			&& ManageCome.equals(other.getManageCome())
			&& NeedFlag.equals(other.getNeedFlag())
			&& OriFlag.equals(other.getOriFlag());
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
		if( strFieldName.equals("AffixTypeCode") ) {
			return 0;
		}
		if( strFieldName.equals("AffixTypeName") ) {
			return 1;
		}
		if( strFieldName.equals("AffixCode") ) {
			return 2;
		}
		if( strFieldName.equals("AffixName") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCome") ) {
			return 4;
		}
		if( strFieldName.equals("NeedFlag") ) {
			return 5;
		}
		if( strFieldName.equals("OriFlag") ) {
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
				strFieldName = "AffixTypeCode";
				break;
			case 1:
				strFieldName = "AffixTypeName";
				break;
			case 2:
				strFieldName = "AffixCode";
				break;
			case 3:
				strFieldName = "AffixName";
				break;
			case 4:
				strFieldName = "ManageCome";
				break;
			case 5:
				strFieldName = "NeedFlag";
				break;
			case 6:
				strFieldName = "OriFlag";
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
		if( strFieldName.equals("AffixTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCome") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriFlag") ) {
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
