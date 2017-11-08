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
import com.sinosoft.lis.db.LDDiseaseDB;

/*
 * <p>ClassName: LDDiseaseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LDDiseaseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDDiseaseSchema.class);

	// @Field
	/** 疾病编码 */
	private String ICDCode;
	/** 疾病名称 */
	private String ICDName;
	/** 疾病上级编码 */
	private String UpICDCode;
	/** 疾病级别 */
	private String ICDLevel;
	/** 疾病类型 */
	private String ICDType;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDDiseaseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ICDCode";

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
		LDDiseaseSchema cloned = (LDDiseaseSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getICDCode()
	{
		return ICDCode;
	}
	public void setICDCode(String aICDCode)
	{
		ICDCode = aICDCode;
	}
	public String getICDName()
	{
		return ICDName;
	}
	public void setICDName(String aICDName)
	{
		ICDName = aICDName;
	}
	public String getUpICDCode()
	{
		return UpICDCode;
	}
	public void setUpICDCode(String aUpICDCode)
	{
		UpICDCode = aUpICDCode;
	}
	public String getICDLevel()
	{
		return ICDLevel;
	}
	public void setICDLevel(String aICDLevel)
	{
		ICDLevel = aICDLevel;
	}
	/**
	* 01 -- 恶性肿瘤<p>
	* 02 -- 急性心肌梗塞<p>
	* 03 -- 脑中风后遗症<p>
	* 04 -- 重大器官移植术或造血干细胞移植术<p>
	* 05 -- 冠状动脉搭桥术（或称冠状动脉旁路移植术）<p>
	* 06 -- 终末期肾病（或称慢性肾功能衰竭尿毒症期）<p>
	* 07 -- 其他重大疾病<p>
	* 08 -- 其他险因
	*/
	public String getICDType()
	{
		return ICDType;
	}
	public void setICDType(String aICDType)
	{
		ICDType = aICDType;
	}

	/**
	* 使用另外一个 LDDiseaseSchema 对象给 Schema 赋值
	* @param: aLDDiseaseSchema LDDiseaseSchema
	**/
	public void setSchema(LDDiseaseSchema aLDDiseaseSchema)
	{
		this.ICDCode = aLDDiseaseSchema.getICDCode();
		this.ICDName = aLDDiseaseSchema.getICDName();
		this.UpICDCode = aLDDiseaseSchema.getUpICDCode();
		this.ICDLevel = aLDDiseaseSchema.getICDLevel();
		this.ICDType = aLDDiseaseSchema.getICDType();
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
			if( rs.getString("ICDCode") == null )
				this.ICDCode = null;
			else
				this.ICDCode = rs.getString("ICDCode").trim();

			if( rs.getString("ICDName") == null )
				this.ICDName = null;
			else
				this.ICDName = rs.getString("ICDName").trim();

			if( rs.getString("UpICDCode") == null )
				this.UpICDCode = null;
			else
				this.UpICDCode = rs.getString("UpICDCode").trim();

			if( rs.getString("ICDLevel") == null )
				this.ICDLevel = null;
			else
				this.ICDLevel = rs.getString("ICDLevel").trim();

			if( rs.getString("ICDType") == null )
				this.ICDType = null;
			else
				this.ICDType = rs.getString("ICDType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDDisease表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDDiseaseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDDiseaseSchema getSchema()
	{
		LDDiseaseSchema aLDDiseaseSchema = new LDDiseaseSchema();
		aLDDiseaseSchema.setSchema(this);
		return aLDDiseaseSchema;
	}

	public LDDiseaseDB getDB()
	{
		LDDiseaseDB aDBOper = new LDDiseaseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDDisease描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ICDCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICDName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpICDCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICDLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICDType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDDisease>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ICDCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ICDName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UpICDCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ICDLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ICDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDDiseaseSchema";
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
		if (FCode.equalsIgnoreCase("ICDCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICDCode));
		}
		if (FCode.equalsIgnoreCase("ICDName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICDName));
		}
		if (FCode.equalsIgnoreCase("UpICDCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpICDCode));
		}
		if (FCode.equalsIgnoreCase("ICDLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICDLevel));
		}
		if (FCode.equalsIgnoreCase("ICDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICDType));
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
				strFieldValue = StrTool.GBKToUnicode(ICDCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ICDName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(UpICDCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ICDLevel);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ICDType);
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

		if (FCode.equalsIgnoreCase("ICDCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICDCode = FValue.trim();
			}
			else
				ICDCode = null;
		}
		if (FCode.equalsIgnoreCase("ICDName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICDName = FValue.trim();
			}
			else
				ICDName = null;
		}
		if (FCode.equalsIgnoreCase("UpICDCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpICDCode = FValue.trim();
			}
			else
				UpICDCode = null;
		}
		if (FCode.equalsIgnoreCase("ICDLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICDLevel = FValue.trim();
			}
			else
				ICDLevel = null;
		}
		if (FCode.equalsIgnoreCase("ICDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICDType = FValue.trim();
			}
			else
				ICDType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDDiseaseSchema other = (LDDiseaseSchema)otherObject;
		return
			ICDCode.equals(other.getICDCode())
			&& ICDName.equals(other.getICDName())
			&& UpICDCode.equals(other.getUpICDCode())
			&& ICDLevel.equals(other.getICDLevel())
			&& ICDType.equals(other.getICDType());
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
		if( strFieldName.equals("ICDCode") ) {
			return 0;
		}
		if( strFieldName.equals("ICDName") ) {
			return 1;
		}
		if( strFieldName.equals("UpICDCode") ) {
			return 2;
		}
		if( strFieldName.equals("ICDLevel") ) {
			return 3;
		}
		if( strFieldName.equals("ICDType") ) {
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
				strFieldName = "ICDCode";
				break;
			case 1:
				strFieldName = "ICDName";
				break;
			case 2:
				strFieldName = "UpICDCode";
				break;
			case 3:
				strFieldName = "ICDLevel";
				break;
			case 4:
				strFieldName = "ICDType";
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
		if( strFieldName.equals("ICDCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICDName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpICDCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICDLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICDType") ) {
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
