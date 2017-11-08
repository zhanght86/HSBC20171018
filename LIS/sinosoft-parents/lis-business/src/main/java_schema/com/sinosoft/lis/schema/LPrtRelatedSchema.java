/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.LPrtRelatedDB;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtRelatedSchema
 * </p>
 * <p>
 * Description: DB层 Schema 类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-01-24
 */
public class LPrtRelatedSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtRelatedSchema.class);

	// @Field
	/** 打印id */
	private String PrintID;

	/** 模板id */
	private String TempleteID;

	/** 语言 */
	private String Language;

	/** 输出类型 */
	private String OutputType;

	/** 数据库表的字段个数 */
	public static final int FIELDNUM = 4;

	/** 主键 */
	private static String[] PK;

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtRelatedSchema()
	{
		mErrors = new CErrors();
		String[] pk = new String[4];
		pk[0] = "PrintID";
		pk[1] = "TempleteID";
		pk[2] = "Language";
		pk[3] = "OutputType";
		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		LPrtRelatedSchema cloned = (LPrtRelatedSchema) super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPrintID()
	{
		return PrintID;
	}

	public void setPrintID(String aPrintID)
	{
		PrintID = aPrintID.trim();
	}

	public String getTempleteID()
	{
		return TempleteID;
	}

	public void setTempleteID(String aTempleteID)
	{
		TempleteID = aTempleteID.trim();
	}

	public String getLanguage()
	{
		return Language;
	}

	public void setLanguage(String aLanguage)
	{
		Language = aLanguage.trim();
	}

	public String getOutputType()
	{
		return OutputType;
	}

	public void setOutputType(String aOutputType)
	{
		OutputType = aOutputType.trim();
	}

	/**
	 * 使用另外一个 LPrtRelatedSchema 对象给 Schema 赋值
	 * @param: cLPrtRelatedSchema LPrtRelatedSchema
	 **/
	public void setSchema(LPrtRelatedSchema cLPrtRelatedSchema)
	{
		this.PrintID = cLPrtRelatedSchema.getPrintID();
		this.TempleteID = cLPrtRelatedSchema.getTempleteID();
		this.Language = cLPrtRelatedSchema.getLanguage();
		this.OutputType = cLPrtRelatedSchema.getOutputType();
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
				this.PrintID = null;
			else
				this.PrintID = rs.getString(1).trim();
			if (rs.getString(2) == null)
				this.TempleteID = null;
			else
				this.TempleteID = rs.getString(2).trim();
			if (rs.getString(3) == null)
				this.Language = null;
			else
				this.Language = rs.getString(3).trim();
			if (rs.getString(4) == null)
				this.OutputType = null;
			else
				this.OutputType = rs.getString(4).trim();
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtRelatedSchema getSchema()
	{
		LPrtRelatedSchema aLPrtRelatedSchema = new LPrtRelatedSchema();
		aLPrtRelatedSchema.setSchema(this);
		return aLPrtRelatedSchema;
	}

	public LPrtRelatedDB getDB()
	{
		LPrtRelatedDB aDBOper = new LPrtRelatedDB();
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
		strReturn.append(StrTool.cTrim(PrintID));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempleteID));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Language));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutputType));
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
			PrintID = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			TempleteID = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			Language = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			OutputType = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedSchema";
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
		if (FCode.equals("PrintID"))
		{
			strReturn = StrTool.GBKToUnicode(PrintID);
		}
		if (FCode.equals("TempleteID"))
		{
			strReturn = StrTool.GBKToUnicode(TempleteID);
		}
		if (FCode.equals("Language"))
		{
			strReturn = StrTool.GBKToUnicode(Language);
		}
		if (FCode.equals("OutputType"))
		{
			strReturn = StrTool.GBKToUnicode(OutputType);
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
				strFieldValue = StrTool.GBKToUnicode(PrintID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TempleteID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OutputType);
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

		if (FCode.equalsIgnoreCase("PrintID"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PrintID = FValue.trim();
			}
			else
				PrintID = null;
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
		if (FCode.equalsIgnoreCase("Language"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Language = FValue.trim();
			}
			else
				Language = null;
		}
		if (FCode.equalsIgnoreCase("OutputType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OutputType = FValue.trim();
			}
			else
				OutputType = null;
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
		LPrtRelatedSchema other = (LPrtRelatedSchema) otherObject;
		return (PrintID == null ? other.getPrintID() == null : PrintID.equals(other.getPrintID()))
				&& (TempleteID == null ? other.getTempleteID() == null : TempleteID.equals(other.getTempleteID()))
				&& (Language == null ? other.getLanguage() == null : Language.equals(other.getLanguage()))
				&& (OutputType == null ? other.getOutputType() == null : OutputType.equals(other.getOutputType()));
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
		if (strFieldName.equals("PrintID"))
		{
			return 0;
		}
		if (strFieldName.equals("TempleteID"))
		{
			return 1;
		}
		if (strFieldName.equals("Language"))
		{
			return 2;
		}
		if (strFieldName.equals("OutputType"))
		{
			return 3;
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
				strFieldName = "PrintID";
				break;
			case 1:
				strFieldName = "TempleteID";
				break;
			case 2:
				strFieldName = "Language";
				break;
			case 3:
				strFieldName = "OutputType";
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
		if (strFieldName.equals("PrintID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("TempleteID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Language"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OutputType"))
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		}
		return nFieldType;
	}
}
