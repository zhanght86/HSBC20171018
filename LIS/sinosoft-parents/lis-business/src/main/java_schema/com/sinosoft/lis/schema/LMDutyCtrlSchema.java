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
import com.sinosoft.lis.db.LMDutyCtrlDB;

/*
 * <p>ClassName: LMDutyCtrlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyCtrlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyCtrlSchema.class);

	// @Field
	/** 责任代码 */
	private String DutyCode;
	/** 其他编码 */
	private String OtherCode;
	/** 字段名称 */
	private String FieldName;
	/** 录入标志 */
	private String InpFlag;
	/** 控制类型 */
	private String CtrlType;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyCtrlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "DutyCode";
		pk[1] = "OtherCode";
		pk[2] = "FieldName";

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
		LMDutyCtrlSchema cloned = (LMDutyCtrlSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getOtherCode()
	{
		return OtherCode;
	}
	public void setOtherCode(String aOtherCode)
	{
		OtherCode = aOtherCode;
	}
	public String getFieldName()
	{
		return FieldName;
	}
	public void setFieldName(String aFieldName)
	{
		FieldName = aFieldName;
	}
	/**
	* Y -- 录入、N -- 不录入
	*/
	public String getInpFlag()
	{
		return InpFlag;
	}
	public void setInpFlag(String aInpFlag)
	{
		InpFlag = aInpFlag;
	}
	/**
	* P -- 保费项、G -- 领取项、D -- 责任
	*/
	public String getCtrlType()
	{
		return CtrlType;
	}
	public void setCtrlType(String aCtrlType)
	{
		CtrlType = aCtrlType;
	}

	/**
	* 使用另外一个 LMDutyCtrlSchema 对象给 Schema 赋值
	* @param: aLMDutyCtrlSchema LMDutyCtrlSchema
	**/
	public void setSchema(LMDutyCtrlSchema aLMDutyCtrlSchema)
	{
		this.DutyCode = aLMDutyCtrlSchema.getDutyCode();
		this.OtherCode = aLMDutyCtrlSchema.getOtherCode();
		this.FieldName = aLMDutyCtrlSchema.getFieldName();
		this.InpFlag = aLMDutyCtrlSchema.getInpFlag();
		this.CtrlType = aLMDutyCtrlSchema.getCtrlType();
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
			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("OtherCode") == null )
				this.OtherCode = null;
			else
				this.OtherCode = rs.getString("OtherCode").trim();

			if( rs.getString("FieldName") == null )
				this.FieldName = null;
			else
				this.FieldName = rs.getString("FieldName").trim();

			if( rs.getString("InpFlag") == null )
				this.InpFlag = null;
			else
				this.InpFlag = rs.getString("InpFlag").trim();

			if( rs.getString("CtrlType") == null )
				this.CtrlType = null;
			else
				this.CtrlType = rs.getString("CtrlType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyCtrl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyCtrlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyCtrlSchema getSchema()
	{
		LMDutyCtrlSchema aLMDutyCtrlSchema = new LMDutyCtrlSchema();
		aLMDutyCtrlSchema.setSchema(this);
		return aLMDutyCtrlSchema;
	}

	public LMDutyCtrlDB getDB()
	{
		LMDutyCtrlDB aDBOper = new LMDutyCtrlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyCtrl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InpFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyCtrl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InpFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CtrlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyCtrlSchema";
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("OtherCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherCode));
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
		}
		if (FCode.equalsIgnoreCase("InpFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InpFlag));
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlType));
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
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FieldName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InpFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CtrlType);
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

		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherCode = FValue.trim();
			}
			else
				OtherCode = null;
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldName = FValue.trim();
			}
			else
				FieldName = null;
		}
		if (FCode.equalsIgnoreCase("InpFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InpFlag = FValue.trim();
			}
			else
				InpFlag = null;
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlType = FValue.trim();
			}
			else
				CtrlType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyCtrlSchema other = (LMDutyCtrlSchema)otherObject;
		return
			DutyCode.equals(other.getDutyCode())
			&& OtherCode.equals(other.getOtherCode())
			&& FieldName.equals(other.getFieldName())
			&& InpFlag.equals(other.getInpFlag())
			&& CtrlType.equals(other.getCtrlType());
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
		if( strFieldName.equals("DutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("OtherCode") ) {
			return 1;
		}
		if( strFieldName.equals("FieldName") ) {
			return 2;
		}
		if( strFieldName.equals("InpFlag") ) {
			return 3;
		}
		if( strFieldName.equals("CtrlType") ) {
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
				strFieldName = "DutyCode";
				break;
			case 1:
				strFieldName = "OtherCode";
				break;
			case 2:
				strFieldName = "FieldName";
				break;
			case 3:
				strFieldName = "InpFlag";
				break;
			case 4:
				strFieldName = "CtrlType";
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
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InpFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlType") ) {
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
