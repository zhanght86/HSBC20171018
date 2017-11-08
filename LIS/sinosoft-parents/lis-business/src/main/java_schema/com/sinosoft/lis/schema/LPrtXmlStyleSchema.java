/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.LPrtXmlStyleDB;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtXmlStyleSchema
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
public class LPrtXmlStyleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtXmlStyleSchema.class);

	// @Field
	/** 模板id */
	private String TempleteID;

	/** 标签描述 */
	private String Description;

	/** 层级 */
	private int XmlLevel;

	/** 数据库表的字段个数 */
	public static final int FIELDNUM = 3;

	/** 主键 */
	private static String[] PK;

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtXmlStyleSchema()
	{
		mErrors = new CErrors();
		String[] pk = new String[0];
		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		LPrtXmlStyleSchema cloned = (LPrtXmlStyleSchema) super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTempleteID()
	{
		return TempleteID;
	}

	public void setTempleteID(String aTempleteID)
	{
		TempleteID = aTempleteID.trim();
	}

	public String getDescription()
	{
		return Description;
	}

	public void setDescription(String aDescription)
	{
		Description = aDescription.trim();
	}

	public int getXmlLevel()
	{
		return XmlLevel;
	}

	public void setXmlLevel(int aXmlLevel)
	{
		XmlLevel = aXmlLevel;
	}

	public void setXmlLevel(String aXmlLevel)
	{
		if (aXmlLevel != null && !aXmlLevel.equals(""))
		{
			Integer tInteger = new Integer(aXmlLevel);
			int i = tInteger.intValue();
			XmlLevel = i;
		}
	}

	/**
	 * 使用另外一个 LPrtXmlStyleSchema 对象给 Schema 赋值
	 * @param: cLPrtXmlStyleSchema LPrtXmlStyleSchema
	 **/
	public void setSchema(LPrtXmlStyleSchema cLPrtXmlStyleSchema)
	{
		this.TempleteID = cLPrtXmlStyleSchema.getTempleteID();
		this.Description = cLPrtXmlStyleSchema.getDescription();
		this.XmlLevel = cLPrtXmlStyleSchema.getXmlLevel();
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
				this.TempleteID = null;
			else
				this.TempleteID = rs.getString(1).trim();
			if (rs.getString(2) == null)
				this.Description = null;
			else
				this.Description = rs.getString(2).trim();
			this.XmlLevel = rs.getInt(3);
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtXmlStyleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtXmlStyleSchema getSchema()
	{
		LPrtXmlStyleSchema aLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
		aLPrtXmlStyleSchema.setSchema(this);
		return aLPrtXmlStyleSchema;
	}

	public LPrtXmlStyleDB getDB()
	{
		LPrtXmlStyleDB aDBOper = new LPrtXmlStyleDB();
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
		strReturn.append(StrTool.cTrim(TempleteID));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(ChgData.chgData(XmlLevel));
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
			TempleteID = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			Description = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			XmlLevel = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER))).intValue();
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtXmlStyleSchema";
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
		if (FCode.equals("TempleteID"))
		{
			strReturn = StrTool.GBKToUnicode(TempleteID);
		}
		if (FCode.equals("Description"))
		{
			strReturn = StrTool.GBKToUnicode(Description);
		}
		if (FCode.equals("XmlLevel"))
		{
			strReturn = String.valueOf(XmlLevel);
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
				strFieldValue = StrTool.GBKToUnicode(TempleteID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Description);
				break;
			case 2:
				strFieldValue = String.valueOf(XmlLevel);
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

		if (FCode.equalsIgnoreCase("TempleteID"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				TempleteID = FValue.trim();
			}
			else
				TempleteID = null;
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Description = FValue.trim();
			}
			else
				Description = null;
		}
		if (FCode.equalsIgnoreCase("XmlLevel"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer(FValue);
				int i = tInteger.intValue();
				XmlLevel = i;
			}
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
		LPrtXmlStyleSchema other = (LPrtXmlStyleSchema) otherObject;
		return (TempleteID == null ? other.getTempleteID() == null : TempleteID.equals(other.getTempleteID()))
				&& (Description == null ? other.getDescription() == null : Description.equals(other.getDescription()))
				&& XmlLevel == other.getXmlLevel();
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
		if (strFieldName.equals("TempleteID"))
		{
			return 0;
		}
		if (strFieldName.equals("Description"))
		{
			return 1;
		}
		if (strFieldName.equals("XmlLevel"))
		{
			return 2;
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
				strFieldName = "TempleteID";
				break;
			case 1:
				strFieldName = "Description";
				break;
			case 2:
				strFieldName = "XmlLevel";
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
		if (strFieldName.equals("TempleteID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Description"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("XmlLevel"))
		{
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		}
		return nFieldType;
	}
}
