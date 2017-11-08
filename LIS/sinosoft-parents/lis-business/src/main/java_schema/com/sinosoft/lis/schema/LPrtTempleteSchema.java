/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.LPrtTempleteDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtTempleteSchema
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
public class LPrtTempleteSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtTempleteSchema.class);

	// @Field
	/** 模板id */
	private String TempleteID;

	/** 模板名称 */
	private String TempleteName;

	/** 语言 */
	private String Language;

	/** 模板类型 */
	private String TempleteType;

	/** 输出类型 */
	private String OutputType;

	/** 输出位置 */
	private String Output;

	/** 文件 */
	private String FilePath;

	/** 是否默认模板 */
	private String DefaultType;

	/** 审批状态 */
	private String State;

	/** 审批意见 */
	private String Remark;

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
	public static final int FIELDNUM = 15;

	/** 主键 */
	private static String[] PK;

	/** 日期处理 */
	private FDate fDate = new FDate();

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtTempleteSchema()
	{
		mErrors = new CErrors();
		String[] pk = new String[1];
		pk[0] = "TempleteID";
		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		LPrtTempleteSchema cloned = (LPrtTempleteSchema) super.clone();
		cloned.fDate = (FDate) fDate.clone();
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

	public String getTempleteName()
	{
		return TempleteName;
	}

	public void setTempleteName(String aTempleteName)
	{
		TempleteName = aTempleteName.trim();
	}

	public String getLanguage()
	{
		return Language;
	}

	public void setLanguage(String aLanguage)
	{
		Language = aLanguage.trim();
	}

	public String getTempleteType()
	{
		return TempleteType;
	}

	public void setTempleteType(String aTempleteType)
	{
		TempleteType = aTempleteType.trim();
	}

	public String getOutputType()
	{
		return OutputType;
	}

	public void setOutputType(String aOutputType)
	{
		OutputType = aOutputType.trim();
	}

	public String getOutput()
	{
		return Output;
	}

	public void setOutput(String aOutput)
	{
		Output = aOutput.trim();
	}

	public String getFilePath()
	{
		return FilePath;
	}

	public void setFilePath(String aFilePath)
	{
		FilePath = aFilePath.trim();
	}

	public String getDefaultType()
	{
		return DefaultType;
	}

	public void setDefaultType(String aDefaultType)
	{
		DefaultType = aDefaultType.trim();
	}

	public String getState()
	{
		return State;
	}

	public void setState(String aState)
	{
		State = aState.trim();
	}

	public String getRemark()
	{
		return Remark;
	}

	public void setRemark(String aRemark)
	{
		Remark = aRemark.trim();
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
	 * 使用另外一个 LPrtTempleteSchema 对象给 Schema 赋值
	 * @param: cLPrtTempleteSchema LPrtTempleteSchema
	 **/
	public void setSchema(LPrtTempleteSchema cLPrtTempleteSchema)
	{
		this.TempleteID = cLPrtTempleteSchema.getTempleteID();
		this.TempleteName = cLPrtTempleteSchema.getTempleteName();
		this.Language = cLPrtTempleteSchema.getLanguage();
		this.TempleteType = cLPrtTempleteSchema.getTempleteType();
		this.OutputType = cLPrtTempleteSchema.getOutputType();
		this.Output = cLPrtTempleteSchema.getOutput();
		this.FilePath = cLPrtTempleteSchema.getFilePath();
		this.DefaultType = cLPrtTempleteSchema.getDefaultType();
		this.State = cLPrtTempleteSchema.getState();
		this.Remark = cLPrtTempleteSchema.getRemark();
		this.Operator = cLPrtTempleteSchema.getOperator();
		this.MakeDate = fDate.getDate(cLPrtTempleteSchema.getMakeDate());
		this.MakeTime = cLPrtTempleteSchema.getMakeTime();
		this.ModifyDate = fDate.getDate(cLPrtTempleteSchema.getModifyDate());
		this.ModifyTime = cLPrtTempleteSchema.getModifyTime();
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
				this.TempleteName = null;
			else
				this.TempleteName = rs.getString(2).trim();
			if (rs.getString(3) == null)
				this.Language = null;
			else
				this.Language = rs.getString(3).trim();
			if (rs.getString(4) == null)
				this.TempleteType = null;
			else
				this.TempleteType = rs.getString(4).trim();
			if (rs.getString(5) == null)
				this.OutputType = null;
			else
				this.OutputType = rs.getString(5).trim();
			if (rs.getString(6) == null)
				this.Output = null;
			else
				this.Output = rs.getString(6).trim();
			if (rs.getString(7) == null)
				this.FilePath = null;
			else
				this.FilePath = rs.getString(7).trim();
			if (rs.getString(8) == null)
				this.DefaultType = null;
			else
				this.DefaultType = rs.getString(8).trim();
			if (rs.getString(9) == null)
				this.State = null;
			else
				this.State = rs.getString(9).trim();
			if (rs.getString(10) == null)
				this.Remark = null;
			else
				this.Remark = rs.getString(10).trim();
			if (rs.getString(11) == null)
				this.Operator = null;
			else
				this.Operator = rs.getString(11).trim();
			this.MakeDate = rs.getDate(12);
			if (rs.getString(13) == null)
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString(13).trim();
			this.ModifyDate = rs.getDate(14);
			if (rs.getString(15) == null)
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString(15).trim();
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtTempleteSchema getSchema()
	{
		LPrtTempleteSchema aLPrtTempleteSchema = new LPrtTempleteSchema();
		aLPrtTempleteSchema.setSchema(this);
		return aLPrtTempleteSchema;
	}

	public LPrtTempleteDB getDB()
	{
		LPrtTempleteDB aDBOper = new LPrtTempleteDB();
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
		strReturn.append(StrTool.cTrim(TempleteName));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Language));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempleteType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutputType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Output));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FilePath));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
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
			TempleteID = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			TempleteName = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			Language = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			TempleteType = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			OutputType = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			Output = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER);
			FilePath = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER);
			DefaultType = StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER);
			State = StrTool.getStr(strMessage, 9, SysConst.PACKAGESPILTER);
			Remark = StrTool.getStr(strMessage, 10, SysConst.PACKAGESPILTER);
			Operator = StrTool.getStr(strMessage, 11, SysConst.PACKAGESPILTER);
			MakeDate = fDate.getDate(StrTool.getStr(strMessage, 12, SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(strMessage, 13, SysConst.PACKAGESPILTER);
			ModifyDate = fDate.getDate(StrTool.getStr(strMessage, 14, SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(strMessage, 15, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteSchema";
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
		if (FCode.equals("TempleteName"))
		{
			strReturn = StrTool.GBKToUnicode(TempleteName);
		}
		if (FCode.equals("Language"))
		{
			strReturn = StrTool.GBKToUnicode(Language);
		}
		if (FCode.equals("TempleteType"))
		{
			strReturn = StrTool.GBKToUnicode(TempleteType);
		}
		if (FCode.equals("OutputType"))
		{
			strReturn = StrTool.GBKToUnicode(OutputType);
		}
		if (FCode.equals("Output"))
		{
			strReturn = StrTool.GBKToUnicode(Output);
		}
		if (FCode.equals("FilePath"))
		{
			strReturn = StrTool.GBKToUnicode(FilePath);
		}
		if (FCode.equals("DefaultType"))
		{
			strReturn = StrTool.GBKToUnicode(DefaultType);
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(State);
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(Remark);
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
				strFieldValue = StrTool.GBKToUnicode(TempleteID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TempleteName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TempleteType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OutputType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Output);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FilePath);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DefaultType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(this.getMakeDate());
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(this.getModifyDate());
				break;
			case 14:
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

		if (FCode.equalsIgnoreCase("TempleteID"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				TempleteID = FValue.trim();
			}
			else
				TempleteID = null;
		}
		if (FCode.equalsIgnoreCase("TempleteName"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				TempleteName = FValue.trim();
			}
			else
				TempleteName = null;
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
		if (FCode.equalsIgnoreCase("TempleteType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				TempleteType = FValue.trim();
			}
			else
				TempleteType = null;
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
		if (FCode.equalsIgnoreCase("Output"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Output = FValue.trim();
			}
			else
				Output = null;
		}
		if (FCode.equalsIgnoreCase("FilePath"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				FilePath = FValue.trim();
			}
			else
				FilePath = null;
		}
		if (FCode.equalsIgnoreCase("DefaultType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				DefaultType = FValue.trim();
			}
			else
				DefaultType = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		LPrtTempleteSchema other = (LPrtTempleteSchema) otherObject;
		return (TempleteID == null ? other.getTempleteID() == null : TempleteID.equals(other.getTempleteID()))
				&& (TempleteName == null ? other.getTempleteName() == null
						: TempleteName.equals(other.getTempleteName()))
				&& (Language == null ? other.getLanguage() == null : Language.equals(other.getLanguage()))
				&& (TempleteType == null ? other.getTempleteType() == null
						: TempleteType.equals(other.getTempleteType()))
				&& (OutputType == null ? other.getOutputType() == null : OutputType.equals(other.getOutputType()))
				&& (Output == null ? other.getOutput() == null : Output.equals(other.getOutput()))
				&& (FilePath == null ? other.getFilePath() == null : FilePath.equals(other.getFilePath()))
				&& (DefaultType == null ? other.getDefaultType() == null : DefaultType.equals(other.getDefaultType()))
				&& (State == null ? other.getState() == null : State.equals(other.getState()))
				&& (Remark == null ? other.getRemark() == null : Remark.equals(other.getRemark()))
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
		if (strFieldName.equals("TempleteID"))
		{
			return 0;
		}
		if (strFieldName.equals("TempleteName"))
		{
			return 1;
		}
		if (strFieldName.equals("Language"))
		{
			return 2;
		}
		if (strFieldName.equals("TempleteType"))
		{
			return 3;
		}
		if (strFieldName.equals("OutputType"))
		{
			return 4;
		}
		if (strFieldName.equals("Output"))
		{
			return 5;
		}
		if (strFieldName.equals("FilePath"))
		{
			return 6;
		}
		if (strFieldName.equals("DefaultType"))
		{
			return 7;
		}
		if (strFieldName.equals("State"))
		{
			return 8;
		}
		if (strFieldName.equals("Remark"))
		{
			return 9;
		}
		if (strFieldName.equals("Operator"))
		{
			return 10;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return 11;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return 12;
		}
		if (strFieldName.equals("ModifyDate"))
		{
			return 13;
		}
		if (strFieldName.equals("ModifyTime"))
		{
			return 14;
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
				strFieldName = "TempleteName";
				break;
			case 2:
				strFieldName = "Language";
				break;
			case 3:
				strFieldName = "TempleteType";
				break;
			case 4:
				strFieldName = "OutputType";
				break;
			case 5:
				strFieldName = "Output";
				break;
			case 6:
				strFieldName = "FilePath";
				break;
			case 7:
				strFieldName = "DefaultType";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
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
		if (strFieldName.equals("TempleteID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("TempleteName"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Language"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("TempleteType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OutputType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Output"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("FilePath"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("DefaultType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("State"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Remark"))
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		}
		return nFieldType;
	}
}
