/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.LPrtPrintLogDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtPrintLogSchema
 * </p>
 * <p>
 * Description: DB层 Schema 类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-02-15
 */
public class LPrtPrintLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtPrintLogSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;

	/** 打印ID */
	private String PrintID;

	/** 模板ID */
	private String TempleteID;

	/** 打印状态 */
	private String State;

	/** 打印输出 */
	private String OutPut;

	/** 打印备注 */
	private String Remark;

	/** 操作员 */
	private String Operator;

	/** 打印日期 */
	private Date PrintDate;

	/** 打印时间 */
	private String PrintTime;

	/** 数据库表的字段个数 */
	public static final int FIELDNUM = 9;

	/** 主键 */
	private static String[] PK;

	/** 日期处理 */
	private FDate fDate = new FDate();

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtPrintLogSchema()
	{
		mErrors = new CErrors();
		String[] pk = new String[1];
		pk[0] = "SerialNo";
		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		LPrtPrintLogSchema cloned = (LPrtPrintLogSchema) super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}

	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo.trim();
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

	public String getState()
	{
		return State;
	}

	public void setState(String aState)
	{
		State = aState.trim();
	}

	public String getOutPut()
	{
		return OutPut;
	}

	public void setOutPut(String aOutPut)
	{
		OutPut = aOutPut.trim();
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

	public String getPrintDate()
	{
		if (PrintDate == null)
			return null;
		else
			return fDate.getString(PrintDate);
	}

	public void setPrintDate(Date aPrintDate)
	{
		PrintDate = aPrintDate;
	}

	public void setPrintDate(String aPrintDate)
	{
		if (aPrintDate == null || aPrintDate.equals(""))
			PrintDate = null;
		else
			PrintDate = fDate.getDate(aPrintDate);
	}

	public String getPrintTime()
	{
		return PrintTime;
	}

	public void setPrintTime(String aPrintTime)
	{
		PrintTime = aPrintTime.trim();
	}

	/**
	 * 使用另外一个 LPrtPrintLogSchema 对象给 Schema 赋值
	 * @param: cLPrtPrintLogSchema LPrtPrintLogSchema
	 **/
	public void setSchema(LPrtPrintLogSchema cLPrtPrintLogSchema)
	{
		this.SerialNo = cLPrtPrintLogSchema.getSerialNo();
		this.PrintID = cLPrtPrintLogSchema.getPrintID();
		this.TempleteID = cLPrtPrintLogSchema.getTempleteID();
		this.State = cLPrtPrintLogSchema.getState();
		this.OutPut = cLPrtPrintLogSchema.getOutPut();
		this.Remark = cLPrtPrintLogSchema.getRemark();
		this.Operator = cLPrtPrintLogSchema.getOperator();
		this.PrintDate = fDate.getDate(cLPrtPrintLogSchema.getPrintDate());
		this.PrintTime = cLPrtPrintLogSchema.getPrintTime();
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
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString(1).trim();
			if (rs.getString(2) == null)
				this.PrintID = null;
			else
				this.PrintID = rs.getString(2).trim();
			if (rs.getString(3) == null)
				this.TempleteID = null;
			else
				this.TempleteID = rs.getString(3).trim();
			if (rs.getString(4) == null)
				this.State = null;
			else
				this.State = rs.getString(4).trim();
			if (rs.getString(5) == null)
				this.OutPut = null;
			else
				this.OutPut = rs.getString(5).trim();
			if (rs.getString(6) == null)
				this.Remark = null;
			else
				this.Remark = rs.getString(6).trim();
			if (rs.getString(7) == null)
				this.Operator = null;
			else
				this.Operator = rs.getString(7).trim();
			this.PrintDate = rs.getDate(8);
			if (rs.getString(9) == null)
				this.PrintTime = null;
			else
				this.PrintTime = rs.getString(9).trim();
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtPrintLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtPrintLogSchema getSchema()
	{
		LPrtPrintLogSchema aLPrtPrintLogSchema = new LPrtPrintLogSchema();
		aLPrtPrintLogSchema.setSchema(this);
		return aLPrtPrintLogSchema;
	}

	public LPrtPrintLogDB getDB()
	{
		LPrtPrintLogDB aDBOper = new LPrtPrintLogDB();
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
		strReturn.append(StrTool.cTrim(SerialNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintID));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempleteID));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPut));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(PrintDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintTime));
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
			SerialNo = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			PrintID = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			TempleteID = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			State = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			OutPut = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			Remark = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER);
			Operator = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER);
			PrintDate = fDate.getDate(StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER));
			PrintTime = StrTool.getStr(strMessage, 9, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtPrintLogSchema";
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
		if (FCode.equals("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(SerialNo);
		}
		if (FCode.equals("PrintID"))
		{
			strReturn = StrTool.GBKToUnicode(PrintID);
		}
		if (FCode.equals("TempleteID"))
		{
			strReturn = StrTool.GBKToUnicode(TempleteID);
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(State);
		}
		if (FCode.equals("OutPut"))
		{
			strReturn = StrTool.GBKToUnicode(OutPut);
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(Remark);
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(Operator);
		}
		if (FCode.equals("PrintDate"))
		{
			strReturn = StrTool.GBKToUnicode(this.getPrintDate());
		}
		if (FCode.equals("PrintTime"))
		{
			strReturn = StrTool.GBKToUnicode(PrintTime);
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PrintID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TempleteID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OutPut);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(this.getPrintDate());
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PrintTime);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("OutPut"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OutPut = FValue.trim();
			}
			else
				OutPut = null;
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
		if (FCode.equalsIgnoreCase("PrintDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PrintDate = fDate.getDate(FValue);
			}
			else
				PrintDate = null;
		}
		if (FCode.equalsIgnoreCase("PrintTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PrintTime = FValue.trim();
			}
			else
				PrintTime = null;
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
		LPrtPrintLogSchema other = (LPrtPrintLogSchema) otherObject;
		return (SerialNo == null ? other.getSerialNo() == null : SerialNo.equals(other.getSerialNo()))
				&& (PrintID == null ? other.getPrintID() == null : PrintID.equals(other.getPrintID()))
				&& (TempleteID == null ? other.getTempleteID() == null : TempleteID.equals(other.getTempleteID()))
				&& (State == null ? other.getState() == null : State.equals(other.getState()))
				&& (OutPut == null ? other.getOutPut() == null : OutPut.equals(other.getOutPut()))
				&& (Remark == null ? other.getRemark() == null : Remark.equals(other.getRemark()))
				&& (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
				&& (PrintDate == null ? other.getPrintDate() == null
						: fDate.getString(PrintDate).equals(other.getPrintDate()))
				&& (PrintTime == null ? other.getPrintTime() == null : PrintTime.equals(other.getPrintTime()));
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
		if (strFieldName.equals("SerialNo"))
		{
			return 0;
		}
		if (strFieldName.equals("PrintID"))
		{
			return 1;
		}
		if (strFieldName.equals("TempleteID"))
		{
			return 2;
		}
		if (strFieldName.equals("State"))
		{
			return 3;
		}
		if (strFieldName.equals("OutPut"))
		{
			return 4;
		}
		if (strFieldName.equals("Remark"))
		{
			return 5;
		}
		if (strFieldName.equals("Operator"))
		{
			return 6;
		}
		if (strFieldName.equals("PrintDate"))
		{
			return 7;
		}
		if (strFieldName.equals("PrintTime"))
		{
			return 8;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "PrintID";
				break;
			case 2:
				strFieldName = "TempleteID";
				break;
			case 3:
				strFieldName = "State";
				break;
			case 4:
				strFieldName = "OutPut";
				break;
			case 5:
				strFieldName = "Remark";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "PrintDate";
				break;
			case 8:
				strFieldName = "PrintTime";
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
		if (strFieldName.equals("SerialNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("PrintID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("TempleteID"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("State"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OutPut"))
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
		if (strFieldName.equals("PrintDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("PrintTime"))
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		}
		return nFieldType;
	}
}
