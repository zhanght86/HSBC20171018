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
import com.sinosoft.lis.db.LLMSerialsDieaseDB;

/*
 * <p>ClassName: LLMSerialsDieaseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LLMSerialsDieaseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLMSerialsDieaseSchema.class);

	// @Field
	/** 分类 */
	private String DieaseType;
	/** 分类描述 */
	private String TypeDesc;
	/** 重大疾病代码 */
	private String Code;
	/** 重大疾病 */
	private String Name;
	/** 重大疾病描述 */
	private String Description;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLMSerialsDieaseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "DieaseType";
		pk[1] = "Code";

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
		LLMSerialsDieaseSchema cloned = (LLMSerialsDieaseSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDieaseType()
	{
		return DieaseType;
	}
	public void setDieaseType(String aDieaseType)
	{
		DieaseType = aDieaseType;
	}
	public String getTypeDesc()
	{
		return TypeDesc;
	}
	public void setTypeDesc(String aTypeDesc)
	{
		TypeDesc = aTypeDesc;
	}
	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String aDescription)
	{
		Description = aDescription;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLMSerialsDieaseSchema 对象给 Schema 赋值
	* @param: aLLMSerialsDieaseSchema LLMSerialsDieaseSchema
	**/
	public void setSchema(LLMSerialsDieaseSchema aLLMSerialsDieaseSchema)
	{
		this.DieaseType = aLLMSerialsDieaseSchema.getDieaseType();
		this.TypeDesc = aLLMSerialsDieaseSchema.getTypeDesc();
		this.Code = aLLMSerialsDieaseSchema.getCode();
		this.Name = aLLMSerialsDieaseSchema.getName();
		this.Description = aLLMSerialsDieaseSchema.getDescription();
		this.Remark = aLLMSerialsDieaseSchema.getRemark();
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
			if( rs.getString("DieaseType") == null )
				this.DieaseType = null;
			else
				this.DieaseType = rs.getString("DieaseType").trim();

			if( rs.getString("TypeDesc") == null )
				this.TypeDesc = null;
			else
				this.TypeDesc = rs.getString("TypeDesc").trim();

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Description") == null )
				this.Description = null;
			else
				this.Description = rs.getString("Description").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLMSerialsDiease表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMSerialsDieaseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLMSerialsDieaseSchema getSchema()
	{
		LLMSerialsDieaseSchema aLLMSerialsDieaseSchema = new LLMSerialsDieaseSchema();
		aLLMSerialsDieaseSchema.setSchema(this);
		return aLLMSerialsDieaseSchema;
	}

	public LLMSerialsDieaseDB getDB()
	{
		LLMSerialsDieaseDB aDBOper = new LLMSerialsDieaseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMSerialsDiease描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DieaseType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMSerialsDiease>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DieaseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Description = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMSerialsDieaseSchema";
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
		if (FCode.equalsIgnoreCase("DieaseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DieaseType));
		}
		if (FCode.equalsIgnoreCase("TypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TypeDesc));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Description));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(DieaseType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TypeDesc);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Description);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("DieaseType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DieaseType = FValue.trim();
			}
			else
				DieaseType = null;
		}
		if (FCode.equalsIgnoreCase("TypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TypeDesc = FValue.trim();
			}
			else
				TypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Description = FValue.trim();
			}
			else
				Description = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLMSerialsDieaseSchema other = (LLMSerialsDieaseSchema)otherObject;
		return
			DieaseType.equals(other.getDieaseType())
			&& TypeDesc.equals(other.getTypeDesc())
			&& Code.equals(other.getCode())
			&& Name.equals(other.getName())
			&& Description.equals(other.getDescription())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("DieaseType") ) {
			return 0;
		}
		if( strFieldName.equals("TypeDesc") ) {
			return 1;
		}
		if( strFieldName.equals("Code") ) {
			return 2;
		}
		if( strFieldName.equals("Name") ) {
			return 3;
		}
		if( strFieldName.equals("Description") ) {
			return 4;
		}
		if( strFieldName.equals("Remark") ) {
			return 5;
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
				strFieldName = "DieaseType";
				break;
			case 1:
				strFieldName = "TypeDesc";
				break;
			case 2:
				strFieldName = "Code";
				break;
			case 3:
				strFieldName = "Name";
				break;
			case 4:
				strFieldName = "Description";
				break;
			case 5:
				strFieldName = "Remark";
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
		if( strFieldName.equals("DieaseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Description") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
