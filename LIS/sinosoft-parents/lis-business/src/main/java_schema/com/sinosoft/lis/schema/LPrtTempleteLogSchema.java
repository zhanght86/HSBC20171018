/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.LPrtTempleteLogDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtTempleteLogSchema
 * </p>
 * <p>
 * Description: DB层 Schema 类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-02-14
 */
public class LPrtTempleteLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPrtTempleteLogSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;

	/** 打印id */
	private String PrintID;

	/** 模板ID */
	private String TempleteID;

	/** 功能类型 */
	private String BusinessType;

	/** 操作类型 */
	private String OperateType;

	/** 操作员 */
	private String Operator;

	/** 日期 */
	private Date OperateDate;

	/** 时间 */
	private String OperateTime;

	/** 数据库表的字段个数 */
	public static final int FIELDNUM = 8;

	/** 主键 */
	private static String[] PK;

	/** 日期处理 */
	private FDate fDate = new FDate();

	/** 错误信息 */
	public CErrors mErrors;

	// @Constructor
	public LPrtTempleteLogSchema()
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
		LPrtTempleteLogSchema cloned = (LPrtTempleteLogSchema) super.clone();
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

	public String getBusinessType()
	{
		return BusinessType;
	}

	public void setBusinessType(String aBusinessType)
	{
		BusinessType = aBusinessType.trim();
	}

	public String getOperateType()
	{
		return OperateType;
	}

	public void setOperateType(String aOperateType)
	{
		OperateType = aOperateType.trim();
	}

	public String getOperator()
	{
		return Operator;
	}

	public void setOperator(String aOperator)
	{
		Operator = aOperator.trim();
	}

	public String getOperateDate()
	{
		if (OperateDate == null)
			return null;
		else
			return fDate.getString(OperateDate);
	}

	public void setOperateDate(Date aOperateDate)
	{
		OperateDate = aOperateDate;
	}

	public void setOperateDate(String aOperateDate)
	{
		if (aOperateDate == null || aOperateDate.equals(""))
			OperateDate = null;
		else
			OperateDate = fDate.getDate(aOperateDate);
	}

	public String getOperateTime()
	{
		return OperateTime;
	}

	public void setOperateTime(String aOperateTime)
	{
		OperateTime = aOperateTime.trim();
	}

	/**
	 * 使用另外一个 LPrtTempleteLogSchema 对象给 Schema 赋值
	 * @param: cLPrtTempleteLogSchema LPrtTempleteLogSchema
	 **/
	public void setSchema(LPrtTempleteLogSchema cLPrtTempleteLogSchema)
	{
		this.SerialNo = cLPrtTempleteLogSchema.getSerialNo();
		this.PrintID = cLPrtTempleteLogSchema.getPrintID();
		this.TempleteID = cLPrtTempleteLogSchema.getTempleteID();
		this.BusinessType = cLPrtTempleteLogSchema.getBusinessType();
		this.OperateType = cLPrtTempleteLogSchema.getOperateType();
		this.Operator = cLPrtTempleteLogSchema.getOperator();
		this.OperateDate = fDate.getDate(cLPrtTempleteLogSchema.getOperateDate());
		this.OperateTime = cLPrtTempleteLogSchema.getOperateTime();
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
				this.BusinessType = null;
			else
				this.BusinessType = rs.getString(4).trim();
			if (rs.getString(5) == null)
				this.OperateType = null;
			else
				this.OperateType = rs.getString(5).trim();
			if (rs.getString(6) == null)
				this.Operator = null;
			else
				this.Operator = rs.getString(6).trim();
			this.OperateDate = rs.getDate(7);
			if (rs.getString(8) == null)
				this.OperateTime = null;
			else
				this.OperateTime = rs.getString(8).trim();
		}
		catch (SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LPrtTempleteLogSchema getSchema()
	{
		LPrtTempleteLogSchema aLPrtTempleteLogSchema = new LPrtTempleteLogSchema();
		aLPrtTempleteLogSchema.setSchema(this);
		return aLPrtTempleteLogSchema;
	}

	public LPrtTempleteLogDB getDB()
	{
		LPrtTempleteLogDB aDBOper = new LPrtTempleteLogDB();
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
		strReturn.append(StrTool.cTrim(BusinessType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperateType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(OperateDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperateTime));
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
			BusinessType = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			OperateType = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			Operator = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER);
			OperateDate = fDate.getDate(StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER));
			OperateTime = StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteLogSchema";
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
		if (FCode.equals("BusinessType"))
		{
			strReturn = StrTool.GBKToUnicode(BusinessType);
		}
		if (FCode.equals("OperateType"))
		{
			strReturn = StrTool.GBKToUnicode(OperateType);
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(Operator);
		}
		if (FCode.equals("OperateDate"))
		{
			strReturn = StrTool.GBKToUnicode(this.getOperateDate());
		}
		if (FCode.equals("OperateTime"))
		{
			strReturn = StrTool.GBKToUnicode(OperateTime);
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
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OperateType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(this.getOperateDate());
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OperateTime);
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
		if (FCode.equalsIgnoreCase("BusinessType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				BusinessType = FValue.trim();
			}
			else
				BusinessType = null;
		}
		if (FCode.equalsIgnoreCase("OperateType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OperateType = FValue.trim();
			}
			else
				OperateType = null;
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
		if (FCode.equalsIgnoreCase("OperateDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OperateDate = fDate.getDate(FValue);
			}
			else
				OperateDate = null;
		}
		if (FCode.equalsIgnoreCase("OperateTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OperateTime = FValue.trim();
			}
			else
				OperateTime = null;
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
		LPrtTempleteLogSchema other = (LPrtTempleteLogSchema) otherObject;
		return (SerialNo == null ? other.getSerialNo() == null : SerialNo.equals(other.getSerialNo()))
				&& (PrintID == null ? other.getPrintID() == null : PrintID.equals(other.getPrintID()))
				&& (TempleteID == null ? other.getTempleteID() == null : TempleteID.equals(other.getTempleteID()))
				&& (BusinessType == null ? other.getBusinessType() == null
						: BusinessType.equals(other.getBusinessType()))
				&& (OperateType == null ? other.getOperateType() == null : OperateType.equals(other.getOperateType()))
				&& (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
				&& (OperateDate == null ? other.getOperateDate() == null
						: fDate.getString(OperateDate).equals(other.getOperateDate()))
				&& (OperateTime == null ? other.getOperateTime() == null : OperateTime.equals(other.getOperateTime()));
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
		if (strFieldName.equals("BusinessType"))
		{
			return 3;
		}
		if (strFieldName.equals("OperateType"))
		{
			return 4;
		}
		if (strFieldName.equals("Operator"))
		{
			return 5;
		}
		if (strFieldName.equals("OperateDate"))
		{
			return 6;
		}
		if (strFieldName.equals("OperateTime"))
		{
			return 7;
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
				strFieldName = "BusinessType";
				break;
			case 4:
				strFieldName = "OperateType";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "OperateDate";
				break;
			case 7:
				strFieldName = "OperateTime";
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
		if (strFieldName.equals("BusinessType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OperateType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Operator"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OperateDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("OperateTime"))
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		}
		return nFieldType;
	}
}
