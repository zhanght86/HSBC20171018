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
import com.sinosoft.lis.db.LLAccidentTypeDB;

/*
 * <p>ClassName: LLAccidentTypeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLAccidentTypeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAccidentTypeSchema.class);
	// @Field
	/** 父类编码 */
	private String AccidentClase;
	/** 意外编码 */
	private String AccidentNo;
	/** 意外名称 */
	private String AccName;
	/** 拼音索引 */
	private String PYSY;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAccidentTypeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AccidentNo";

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
		LLAccidentTypeSchema cloned = (LLAccidentTypeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccidentClase()
	{
		return AccidentClase;
	}
	public void setAccidentClase(String aAccidentClase)
	{
		if(aAccidentClase!=null && aAccidentClase.length()>20)
			throw new IllegalArgumentException("父类编码AccidentClase值"+aAccidentClase+"的长度"+aAccidentClase.length()+"大于最大值20");
		AccidentClase = aAccidentClase;
	}
	public String getAccidentNo()
	{
		return AccidentNo;
	}
	public void setAccidentNo(String aAccidentNo)
	{
		if(aAccidentNo!=null && aAccidentNo.length()>20)
			throw new IllegalArgumentException("意外编码AccidentNo值"+aAccidentNo+"的长度"+aAccidentNo.length()+"大于最大值20");
		AccidentNo = aAccidentNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>200)
			throw new IllegalArgumentException("意外名称AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值200");
		AccName = aAccName;
	}
	public String getPYSY()
	{
		return PYSY;
	}
	public void setPYSY(String aPYSY)
	{
		if(aPYSY!=null && aPYSY.length()>10)
			throw new IllegalArgumentException("拼音索引PYSY值"+aPYSY+"的长度"+aPYSY.length()+"大于最大值10");
		PYSY = aPYSY;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>500)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值500");
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLAccidentTypeSchema 对象给 Schema 赋值
	* @param: aLLAccidentTypeSchema LLAccidentTypeSchema
	**/
	public void setSchema(LLAccidentTypeSchema aLLAccidentTypeSchema)
	{
		this.AccidentClase = aLLAccidentTypeSchema.getAccidentClase();
		this.AccidentNo = aLLAccidentTypeSchema.getAccidentNo();
		this.AccName = aLLAccidentTypeSchema.getAccName();
		this.PYSY = aLLAccidentTypeSchema.getPYSY();
		this.Remark = aLLAccidentTypeSchema.getRemark();
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
			if( rs.getString("AccidentClase") == null )
				this.AccidentClase = null;
			else
				this.AccidentClase = rs.getString("AccidentClase").trim();

			if( rs.getString("AccidentNo") == null )
				this.AccidentNo = null;
			else
				this.AccidentNo = rs.getString("AccidentNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("PYSY") == null )
				this.PYSY = null;
			else
				this.PYSY = rs.getString("PYSY").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAccidentType表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAccidentTypeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAccidentTypeSchema getSchema()
	{
		LLAccidentTypeSchema aLLAccidentTypeSchema = new LLAccidentTypeSchema();
		aLLAccidentTypeSchema.setSchema(this);
		return aLLAccidentTypeSchema;
	}

	public LLAccidentTypeDB getDB()
	{
		LLAccidentTypeDB aDBOper = new LLAccidentTypeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAccidentType描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccidentClase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccidentNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PYSY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAccidentType>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccidentClase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccidentNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PYSY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAccidentTypeSchema";
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
		if (FCode.equalsIgnoreCase("AccidentClase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentClase));
		}
		if (FCode.equalsIgnoreCase("AccidentNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentNo));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("PYSY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PYSY));
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
				strFieldValue = StrTool.GBKToUnicode(AccidentClase);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccidentNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PYSY);
				break;
			case 4:
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

		if (FCode.equalsIgnoreCase("AccidentClase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentClase = FValue.trim();
			}
			else
				AccidentClase = null;
		}
		if (FCode.equalsIgnoreCase("AccidentNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccidentNo = FValue.trim();
			}
			else
				AccidentNo = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("PYSY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PYSY = FValue.trim();
			}
			else
				PYSY = null;
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
		LLAccidentTypeSchema other = (LLAccidentTypeSchema)otherObject;
		return
			AccidentClase.equals(other.getAccidentClase())
			&& AccidentNo.equals(other.getAccidentNo())
			&& AccName.equals(other.getAccName())
			&& PYSY.equals(other.getPYSY())
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
		if( strFieldName.equals("AccidentClase") ) {
			return 0;
		}
		if( strFieldName.equals("AccidentNo") ) {
			return 1;
		}
		if( strFieldName.equals("AccName") ) {
			return 2;
		}
		if( strFieldName.equals("PYSY") ) {
			return 3;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "AccidentClase";
				break;
			case 1:
				strFieldName = "AccidentNo";
				break;
			case 2:
				strFieldName = "AccName";
				break;
			case 3:
				strFieldName = "PYSY";
				break;
			case 4:
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
		if( strFieldName.equals("AccidentClase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PYSY") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
