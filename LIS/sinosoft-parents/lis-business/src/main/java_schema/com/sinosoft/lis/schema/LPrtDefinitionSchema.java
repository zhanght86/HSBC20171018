/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.LPrtDefinitionDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtDefinitionSchema
 * </p>
 * <p>
 * Description: DB层 Schema 类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-02-21
 */
public class LPrtDefinitionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtDefinitionSchema.class);

	// @Field
	/** 打印id */
	private String PrintID;

	/** 打印名称 */
	private String PrintName;

	/** 打印对象 */
	private String PrintObject;

	/** 打印类型 */
	private String PrintType;

	/** 状态 */
	private String State;

	/** 语言选择 */
	private String LanguageType;

	/** 操作员 */
	private String Operator;

	/** 入机日期 */
	private Date MakeDate;

	/** 入机时间 */
	private String MakeTime;

	/** 最后一次修改日期 */
	private Date ModifyDate;

	/** 最后一次修改时间 */
	private String ModifyTime;

	/** 数据库表的字段个数 */
	public static final int FIELDNUM = 11;

	/** 主键 */
	private static String[] PK;

	/** 日期处理 */
	private FDate fDate = new FDate();

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtDefinitionSchema()
	{
		mErrors = new CErrors();
		String[] pk = new String[1];
		pk[0] = "PrintID";
		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		LPrtDefinitionSchema cloned = (LPrtDefinitionSchema) super.clone();
		cloned.fDate = (FDate) fDate.clone();
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

	public String getPrintName()
	{
		return PrintName;
	}

	public void setPrintName(String aPrintName)
	{
		PrintName = aPrintName.trim();
	}

	public String getPrintObject()
	{
		return PrintObject;
	}

	public void setPrintObject(String aPrintObject)
	{
		PrintObject = aPrintObject.trim();
	}

	public String getPrintType()
	{
		return PrintType;
	}

	public void setPrintType(String aPrintType)
	{
		PrintType = aPrintType.trim();
	}

	public String getState()
	{
		return State;
	}

	public void setState(String aState)
	{
		State = aState.trim();
	}

	public String getLanguageType()
	{
		return LanguageType;
	}

	public void setLanguageType(String aLanguageType)
	{
		LanguageType = aLanguageType.trim();
	}

	public String getOperator()
	{
		return Operator;
	}

	public void setOperator(String aOperator)
	{
		Operator = aOperator.trim();
	}

	public String getMakeDate()
	{
		if (MakeDate == null)
			return null;
		else
			return fDate.getString(MakeDate);
	}

	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}

	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate == null || aMakeDate.equals(""))
			MakeDate = null;
		else
			MakeDate = fDate.getDate(aMakeDate);
	}

	public String getMakeTime()
	{
		return MakeTime;
	}

	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime.trim();
	}

	public String getModifyDate()
	{
		if (ModifyDate == null)
			return null;
		else
			return fDate.getString(ModifyDate);
	}

	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}

	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate == null || aModifyDate.equals(""))
			ModifyDate = null;
		else
			ModifyDate = fDate.getDate(aModifyDate);
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}

	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime.trim();
	}

	/**
	 * 使用另外一个 LPrtDefinitionSchema 对象给 Schema 赋值
	 * @param: cLPrtDefinitionSchema LPrtDefinitionSchema
	 **/
	public void setSchema(LPrtDefinitionSchema cLPrtDefinitionSchema)
	{
		this.PrintID = cLPrtDefinitionSchema.getPrintID();
		this.PrintName = cLPrtDefinitionSchema.getPrintName();
		this.PrintObject = cLPrtDefinitionSchema.getPrintObject();
		this.PrintType = cLPrtDefinitionSchema.getPrintType();
		this.State = cLPrtDefinitionSchema.getState();
		this.LanguageType = cLPrtDefinitionSchema.getLanguageType();
		this.Operator = cLPrtDefinitionSchema.getOperator();
		this.MakeDate = fDate.getDate(cLPrtDefinitionSchema.getMakeDate());
		this.MakeTime = cLPrtDefinitionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate(cLPrtDefinitionSchema.getModifyDate());
		this.ModifyTime = cLPrtDefinitionSchema.getModifyTime();
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
				this.PrintName = null;
			else
				this.PrintName = rs.getString(2).trim();
			if (rs.getString(3) == null)
				this.PrintObject = null;
			else
				this.PrintObject = rs.getString(3).trim();
			if (rs.getString(4) == null)
				this.PrintType = null;
			else
				this.PrintType = rs.getString(4).trim();
			if (rs.getString(5) == null)
				this.State = null;
			else
				this.State = rs.getString(5).trim();
			if (rs.getString(6) == null)
				this.LanguageType = null;
			else
				this.LanguageType = rs.getString(6).trim();
			if (rs.getString(7) == null)
				this.Operator = null;
			else
				this.Operator = rs.getString(7).trim();
			this.MakeDate = rs.getDate(8);
			if (rs.getString(9) == null)
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString(9).trim();
			this.ModifyDate = rs.getDate(10);
			if (rs.getString(11) == null)
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString(11).trim();
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtDefinitionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtDefinitionSchema getSchema()
	{
		LPrtDefinitionSchema aLPrtDefinitionSchema = new LPrtDefinitionSchema();
		aLPrtDefinitionSchema.setSchema(this);
		return aLPrtDefinitionSchema;
	}

	public LPrtDefinitionDB getDB()
	{
		LPrtDefinitionDB aDBOper = new LPrtDefinitionDB();
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
		strReturn.append(StrTool.cTrim(PrintName));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintObject));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LanguageType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(ModifyDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
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
			PrintName = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			PrintObject = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			PrintType = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			State = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			LanguageType = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER);
			Operator = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER);
			MakeDate = fDate.getDate(StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(strMessage, 9, SysConst.PACKAGESPILTER);
			ModifyDate = fDate.getDate(StrTool.getStr(strMessage, 10, SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(strMessage, 11, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtDefinitionSchema";
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
		if (FCode.equals("PrintName"))
		{
			strReturn = StrTool.GBKToUnicode(PrintName);
		}
		if (FCode.equals("PrintObject"))
		{
			strReturn = StrTool.GBKToUnicode(PrintObject);
		}
		if (FCode.equals("PrintType"))
		{
			strReturn = StrTool.GBKToUnicode(PrintType);
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(State);
		}
		if (FCode.equals("LanguageType"))
		{
			strReturn = StrTool.GBKToUnicode(LanguageType);
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(Operator);
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(this.getMakeDate());
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(MakeTime);
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(this.getModifyDate());
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(ModifyTime);
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
				strFieldValue = StrTool.GBKToUnicode(PrintName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrintObject);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrintType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(LanguageType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(this.getMakeDate());
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(this.getModifyDate());
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equalsIgnoreCase("PrintName"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PrintName = FValue.trim();
			}
			else
				PrintName = null;
		}
		if (FCode.equalsIgnoreCase("PrintObject"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PrintObject = FValue.trim();
			}
			else
				PrintObject = null;
		}
		if (FCode.equalsIgnoreCase("PrintType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PrintType = FValue.trim();
			}
			else
				PrintType = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("LanguageType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				LanguageType = FValue.trim();
			}
			else
				LanguageType = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				MakeDate = fDate.getDate(FValue);
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ModifyDate = fDate.getDate(FValue);
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		LPrtDefinitionSchema other = (LPrtDefinitionSchema) otherObject;
		return (PrintID == null ? other.getPrintID() == null : PrintID.equals(other.getPrintID()))
				&& (PrintName == null ? other.getPrintName() == null : PrintName.equals(other.getPrintName()))
				&& (PrintObject == null ? other.getPrintObject() == null : PrintObject.equals(other.getPrintObject()))
				&& (PrintType == null ? other.getPrintType() == null : PrintType.equals(other.getPrintType()))
				&& (State == null ? other.getState() == null : State.equals(other.getState()))
				&& (LanguageType == null ? other.getLanguageType() == null
						: LanguageType.equals(other.getLanguageType()))
				&& (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
				&& (MakeDate == null ? other.getMakeDate() == null
						: fDate.getString(MakeDate).equals(other.getMakeDate()))
				&& (MakeTime == null ? other.getMakeTime() == null : MakeTime.equals(other.getMakeTime()))
				&& (ModifyDate == null ? other.getModifyDate() == null
						: fDate.getString(ModifyDate).equals(other.getModifyDate()))
				&& (ModifyTime == null ? other.getModifyTime() == null : ModifyTime.equals(other.getModifyTime()));
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
		if (strFieldName.equals("PrintName"))
		{
			return 1;
		}
		if (strFieldName.equals("PrintObject"))
		{
			return 2;
		}
		if (strFieldName.equals("PrintType"))
		{
			return 3;
		}
		if (strFieldName.equals("State"))
		{
			return 4;
		}
		if (strFieldName.equals("LanguageType"))
		{
			return 5;
		}
		if (strFieldName.equals("Operator"))
		{
			return 6;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return 7;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return 8;
		}
		if (strFieldName.equals("ModifyDate"))
		{
			return 9;
		}
		if (strFieldName.equals("ModifyTime"))
		{
			return 10;
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
				strFieldName = "PrintName";
				break;
			case 2:
				strFieldName = "PrintObject";
				break;
			case 3:
				strFieldName = "PrintType";
				break;
			case 4:
				strFieldName = "State";
				break;
			case 5:
				strFieldName = "LanguageType";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
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
		if (strFieldName.equals("PrintName"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("PrintObject"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("PrintType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("State"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("LanguageType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Operator"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("ModifyDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("ModifyTime"))
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
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		}
		return nFieldType;
	}
}
