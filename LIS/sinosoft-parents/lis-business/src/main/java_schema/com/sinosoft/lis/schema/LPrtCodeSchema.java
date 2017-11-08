/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.LPrtCodeDB;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtCodeSchema
 * </p>
 * <p>
 * Description: DB层 Schema 类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-03-01
 */
public class LPrtCodeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtCodeSchema.class);

	// @Field
	/** 类型 */
	private String CodeType;

	/** 模板ID */
	private String TempleteID;

	/** 编码 */
	private String Code;

	/** 内容1 */
	private String Content1;

	/** 内容2 */
	private String Content2;

	/** 数据库表的字段个数 */
	public static final int FIELDNUM = 5;

	/** 主键 */
	private static String[] PK;

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtCodeSchema()
	{
		mErrors = new CErrors();
		String[] pk = new String[3];
		pk[0] = "CodeType";
		pk[1] = "TempleteID";
		pk[2] = "Code";
		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		LPrtCodeSchema cloned = (LPrtCodeSchema) super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCodeType()
	{
		return CodeType;
	}

	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType.trim();
	}

	public String getTempleteID()
	{
		return TempleteID;
	}

	public void setTempleteID(String aTempleteID)
	{
		TempleteID = aTempleteID.trim();
	}

	public String getCode()
	{
		return Code;
	}

	public void setCode(String aCode)
	{
		Code = aCode.trim();
	}

	public String getContent1()
	{
		return Content1;
	}

	public void setContent1(String aContent1)
	{
		Content1 = aContent1.trim();
	}

	public String getContent2()
	{
		return Content2;
	}

	public void setContent2(String aContent2)
	{
		Content2 = aContent2.trim();
	}

	/**
	 * 使用另外一个 LPrtCodeSchema 对象给 Schema 赋值
	 * @param: cLPrtCodeSchema LPrtCodeSchema
	 **/
	public void setSchema(LPrtCodeSchema cLPrtCodeSchema)
	{
		this.CodeType = cLPrtCodeSchema.getCodeType();
		this.TempleteID = cLPrtCodeSchema.getTempleteID();
		this.Code = cLPrtCodeSchema.getCode();
		this.Content1 = cLPrtCodeSchema.getContent1();
		this.Content2 = cLPrtCodeSchema.getContent2();
	}

	/**
	 * 使用 ResultSet 中的第 i 行给 Schema 赋值
	 * @param: rs ResultSet
	 * @param: i int
	 * @return: boolean
	 **/
	public boolean setSchema(ResultSet rs, int i)
	{
		try
		{
			// rs.absolute(i); // 非滚动游标
			if (rs.getString(1) == null)
				this.CodeType = null;
			else
				this.CodeType = rs.getString(1).trim();
			if (rs.getString(2) == null)
				this.TempleteID = null;
			else
				this.TempleteID = rs.getString(2).trim();
			if (rs.getString(3) == null)
				this.Code = null;
			else
				this.Code = rs.getString(3).trim();
			if (rs.getString(4) == null)
				this.Content1 = null;
			else
				this.Content1 = rs.getString(4).trim();
			if (rs.getString(5) == null)
				this.Content2 = null;
			else
				this.Content2 = rs.getString(5).trim();
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtCodeSchema getSchema()
	{
		LPrtCodeSchema aLPrtCodeSchema = new LPrtCodeSchema();
		aLPrtCodeSchema.setSchema(this);
		return aLPrtCodeSchema;
	}

	public LPrtCodeDB getDB()
	{
		LPrtCodeDB aDBOper = new LPrtCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}

	/**
	 * 数据打包，按 XML 格式打包
	 * @return: String 返回打包后字符串
	 **/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CodeType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempleteID));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Content1));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Content2));
		return strReturn.toString();
	}

	/**
	 * 数据解包
	 * @param: strMessage String 包含一条纪录数据的字符串
	 * @return: boolean
	 **/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			TempleteID = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			Code = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			Content1 = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			Content2 = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtCodeSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 取得对应传入参数的String形式的字段值
	 * @param: FCode String 希望取得的字段名
	 * @return: String 如果没有对应的字段，返回"" 如果字段值为空，返回"null"
	 **/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equals("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(CodeType);
		}
		if (FCode.equals("TempleteID"))
		{
			strReturn = StrTool.GBKToUnicode(TempleteID);
		}
		if (FCode.equals("Code"))
		{
			strReturn = StrTool.GBKToUnicode(Code);
		}
		if (FCode.equals("Content1"))
		{
			strReturn = StrTool.GBKToUnicode(Content1);
		}
		if (FCode.equals("Content2"))
		{
			strReturn = StrTool.GBKToUnicode(Content2);
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
	 * @return: String 如果没有对应的字段，返回"" 如果字段值为空，返回"null"
	 **/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch (nFieldIndex)
		{
			case 0:
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TempleteID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Content1);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Content2);
				break;
			default:
				strFieldValue = "";
		}
		if (strFieldValue.equals(""))
		{
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
	public boolean setV(String FCode, String FValue)
	{
		if (StrTool.cTrim(FCode).equals(""))
			return false;

		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("TempleteID"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				TempleteID = FValue.trim();
			}
			else
				TempleteID = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("Content1"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Content1 = FValue.trim();
			}
			else
				Content1 = null;
		}
		if (FCode.equalsIgnoreCase("Content2"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Content2 = FValue.trim();
			}
			else
				Content2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject)
			return true;
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		LPrtCodeSchema other = (LPrtCodeSchema) otherObject;
		return (CodeType == null ? other.getCodeType() == null : CodeType.equals(other.getCodeType()))
				&& (TempleteID == null ? other.getTempleteID() == null : TempleteID.equals(other.getTempleteID()))
				&& (Code == null ? other.getCode() == null : Code.equals(other.getCode()))
				&& (Content1 == null ? other.getContent1() == null : Content1.equals(other.getContent1()))
				&& (Content2 == null ? other.getContent2() == null : Content2.equals(other.getContent2()));
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
	 * 取得Schema中指定字段名所对应的索引值 如果没有对应的字段，返回-1
	 * @param: strFieldName String
	 * @return: int
	 **/
	public int getFieldIndex(String strFieldName)
	{
		if (strFieldName.equals("CodeType"))
		{
			return 0;
		}
		if (strFieldName.equals("TempleteID"))
		{
			return 1;
		}
		if (strFieldName.equals("Code"))
		{
			return 2;
		}
		if (strFieldName.equals("Content1"))
		{
			return 3;
		}
		if (strFieldName.equals("Content2"))
		{
			return 4;
		}
		return -1;
	}

	/**
	 * 取得Schema中指定索引值所对应的字段名 如果没有对应的字段，返回""
	 * @param: nFieldIndex int
	 * @return: String
	 **/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch (nFieldIndex)
		{
			case 0:
				strFieldName = "CodeType";
				break;
			case 1:
				strFieldName = "TempleteID";
				break;
			case 2:
				strFieldName = "Code";
				break;
			case 3:
				strFieldName = "Content1";
				break;
			case 4:
				strFieldName = "Content2";
				break;
			default:
				strFieldName = "";
		}
		return strFieldName;
	}

	/**
	 * 取得Schema中指定字段名所对应的字段类型 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	 * @param: strFieldName String
	 * @return: int
	 **/
	public int getFieldType(String strFieldName)
	{
		if (strFieldName.equals("CodeType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("TempleteID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Code"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Content1"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Content2"))
		{
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	 * 取得Schema中指定索引值所对应的字段类型 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	 * @param: nFieldIndex int
	 * @return: int
	 **/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch (nFieldIndex)
		{
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
		}
		return nFieldType;
	}
}
