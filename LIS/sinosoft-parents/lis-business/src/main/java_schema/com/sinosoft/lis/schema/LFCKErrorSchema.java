package com.sinosoft.lis.schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.LFCKErrorDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import org.apache.log4j.Logger;

/**
 * <p>
 * ClassName: LFCKErrorSchema
 * </p>
 * <p>
 * Description: 保监会报表校验错误信息表（LFCKError）的Schema文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-11-03
 */
public class LFCKErrorSchema implements Schema, Cloneable
{
	private static Logger logger = Logger.getLogger(LFCKErrorSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;

	/** 内部科目编码 */
	private String ItemCode;

	/** 外部管理机构 */
	private String comcodeisc;

	/** 校验规则编码 */
	private String CKRuleCode;

	/** 校验出错信息 */
	private String CKError;

	/** 生成日期 */
	private Date MakeDate;

	/** 生成时间 */
	private String MakeTime;

	public static final int FIELDNUM = 7; // 数据库表的字段个数

	private static String[] PK; // 主键

	private FDate fDate = new FDate(); // 处理日期

	public CErrors mErrors; // 错误信息

	// @Constructor
	public LFCKErrorSchema()
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
		LFCKErrorSchema cloned = (LFCKErrorSchema) super.clone();
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
		SerialNo = StrTool.cTrim(aSerialNo);
	}

	public String getItemCode()
	{
		return ItemCode;
	}

	public void setItemCode(String aItemCode)
	{
		ItemCode = StrTool.cTrim(aItemCode);
	}

	public String getcomcodeisc()
	{
		return comcodeisc;
	}

	public void setcomcodeisc(String acomcodeisc)
	{
		comcodeisc = StrTool.cTrim(acomcodeisc);
	}

	public String getCKRuleCode()
	{
		return CKRuleCode;
	}

	public void setCKRuleCode(String aCKRuleCode)
	{
		CKRuleCode = StrTool.cTrim(aCKRuleCode);
	}

	public String getCKError()
	{
		return CKError;
	}

	public void setCKError(String aCKError)
	{
		CKError = StrTool.cTrim(aCKError);
	}

	public String getMakeDate()
	{
		if (MakeDate != null)
			return fDate.getString(MakeDate);
		else
			return null;
	}

	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}

	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			MakeDate = fDate.getDate(aMakeDate);
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}

	public void setMakeTime(String aMakeTime)
	{
		MakeTime = StrTool.cTrim(aMakeTime);
	}

	/**
	 * 使用另外一个 LFCKErrorSchema 对象给 Schema 赋值
	 * @param: aLFCKErrorSchema LFCKErrorSchema
	 **/
	public void setSchema(LFCKErrorSchema aLFCKErrorSchema)
	{
		this.SerialNo = aLFCKErrorSchema.getSerialNo();
		this.ItemCode = aLFCKErrorSchema.getItemCode();
		this.comcodeisc = aLFCKErrorSchema.getcomcodeisc();
		this.CKRuleCode = aLFCKErrorSchema.getCKRuleCode();
		this.CKError = aLFCKErrorSchema.getCKError();
		this.MakeDate = fDate.getDate(aLFCKErrorSchema.getMakeDate());
		this.MakeTime = aLFCKErrorSchema.getMakeTime();
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
				this.SerialNo = rs.getString(1);

			if (rs.getString(2) == null)
				this.ItemCode = null;
			else
				this.ItemCode = rs.getString(2);

			if (rs.getString(3) == null)
				this.comcodeisc = null;
			else
				this.comcodeisc = rs.getString(3);

			if (rs.getString(4) == null)
				this.CKRuleCode = null;
			else
				this.CKRuleCode = rs.getString(4);

			if (rs.getString(5) == null)
				this.CKError = null;
			else
				this.CKError = rs.getString(5);

			this.MakeDate = rs.getDate(6);
			if (rs.getString(7) == null)
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString(7);

		}
		catch (SQLException sqle)
		{
			logger.debug("数据库中的LFCKError表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public LFCKErrorSchema getSchema()
	{
		LFCKErrorSchema aLFCKErrorSchema = new LFCKErrorSchema();
		aLFCKErrorSchema.setSchema(this);
		return aLFCKErrorSchema;
	}

	public LFCKErrorDB getDB()
	{
		LFCKErrorDB aDBOper = new LFCKErrorDB();
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
		strReturn.append(StrTool.cTrim(ItemCode));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(comcodeisc));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CKRuleCode));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CKError));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	 * 数据解包，解包顺序参见<A href ={@docRoot}
	 * /dataStructure/tb.html#PrpLFCKError>历史记账凭证主表信息</A>表字段
	 * @param: strMessage String 包含一条纪录数据的字符串
	 * @return: boolean
	 **/
	public boolean decode(String strMessage)
	{
		try
		{
			strMessage = StrTool.cTrim(strMessage);
			SerialNo = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			ItemCode = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			comcodeisc = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			CKRuleCode = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			CKError = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			MakeDate = fDate.getDate(StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.cTrim(SerialNo);
		}
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			strReturn = StrTool.cTrim(ItemCode);
		}
		if (FCode.equalsIgnoreCase("comcodeisc"))
		{
			strReturn = StrTool.cTrim(comcodeisc);
		}
		if (FCode.equalsIgnoreCase("CKRuleCode"))
		{
			strReturn = StrTool.cTrim(CKRuleCode);
		}
		if (FCode.equalsIgnoreCase("CKError"))
		{
			strReturn = StrTool.cTrim(CKError);
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.cTrim(this.getMakeDate());
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.cTrim(MakeTime);
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
				strFieldValue = StrTool.cTrim(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.cTrim(ItemCode);
				break;
			case 2:
				strFieldValue = StrTool.cTrim(comcodeisc);
				break;
			case 3:
				strFieldValue = StrTool.cTrim(CKRuleCode);
				break;
			case 4:
				strFieldValue = StrTool.cTrim(CKError);
				break;
			case 5:
				strFieldValue = StrTool.cTrim(this.getMakeDate());
				break;
			case 6:
				strFieldValue = StrTool.cTrim(MakeTime);
				break;
			default:
				strFieldValue = "";
		};
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
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ItemCode = FValue.trim();
			}
			else
				ItemCode = null;
		}
		if (FCode.equalsIgnoreCase("comcodeisc"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				comcodeisc = FValue.trim();
			}
			else
				comcodeisc = null;
		}
		if (FCode.equalsIgnoreCase("CKRuleCode"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CKRuleCode = FValue.trim();
			}
			else
				CKRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("CKError"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CKError = FValue.trim();
			}
			else
				CKError = null;
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
		LFCKErrorSchema other = (LFCKErrorSchema) otherObject;
		return SerialNo.equals(other.getSerialNo()) && ItemCode.equals(other.getItemCode())
				&& comcodeisc.equals(other.getcomcodeisc()) && CKRuleCode.equals(other.getCKRuleCode())
				&& CKError.equals(other.getCKError()) && fDate.getString(MakeDate).equals(other.getMakeDate())
				&& MakeTime.equals(other.getMakeTime());
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
		if (strFieldName.equals("ItemCode"))
		{
			return 1;
		}
		if (strFieldName.equals("comcodeisc"))
		{
			return 2;
		}
		if (strFieldName.equals("CKRuleCode"))
		{
			return 3;
		}
		if (strFieldName.equals("CKError"))
		{
			return 4;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return 5;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return 6;
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
				strFieldName = "ItemCode";
				break;
			case 2:
				strFieldName = "comcodeisc";
				break;
			case 3:
				strFieldName = "CKRuleCode";
				break;
			case 4:
				strFieldName = "CKError";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			default:
				strFieldName = "";
		};
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
		if (strFieldName.equals("ItemCode"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("comcodeisc"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("CKRuleCode"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("CKError"))
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
