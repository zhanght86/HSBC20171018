package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FICustomerAccDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ChgData;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: FICustomerAccSchema
 * </p>
 * <p>
 * Description: 客户账户总表（FICustomerAcc）的Schema文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FICustomerAccSchema.class);

	// @Field
	/** 客户账户号码 */
	private String InsuAccNo;

	/** 币别 */
	private String Currency;

	/** 客户号码 */
	private String CustomerNo;

	/** 客户类型 */
	private String CustomerType;

	/** 账户类型 */
	private String AccType;

	/** 账户余额 */
	private double Summoney;

	/** 状态 */
	private String State;

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

	public static final int FIELDNUM = 12; // 数据库表的字段个数

	private static String[] PK; // 主键

	private FDate fDate = new FDate(); // 处理日期

	public CErrors mErrors; // 错误信息

	// @Constructor
	public FICustomerAccSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "InsuAccNo";

		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		FICustomerAccSchema cloned = (FICustomerAccSchema) super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getInsuAccNo()
	{
		return InsuAccNo;
	}

	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = StrTool.cTrim(aInsuAccNo);
	}

	public String getCurrency()
	{
		return Currency;
	}

	public void setCurrency(String aCurrency)
	{
		Currency = StrTool.cTrim(aCurrency);
	}

	public String getCustomerNo()
	{
		return CustomerNo;
	}

	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = StrTool.cTrim(aCustomerNo);
	}

	public String getCustomerType()
	{
		return CustomerType;
	}

	public void setCustomerType(String aCustomerType)
	{
		CustomerType = StrTool.cTrim(aCustomerType);
	}

	public String getAccType()
	{
		return AccType;
	}

	public void setAccType(String aAccType)
	{
		AccType = StrTool.cTrim(aAccType);
	}

	public double getSummoney()
	{
		return Summoney;
	}

	public void setSummoney(double aSummoney)
	{
		Summoney = aSummoney;
	}

	public void setSummoney(String aSummoney)
	{
		if (aSummoney != null && !aSummoney.equals(""))
		{
			Double tDouble = new Double(aSummoney);
			double d = Arith.round(tDouble.doubleValue(), 2);
			Summoney = d;
		}
	}

	public String getState()
	{
		return State;
	}

	public void setState(String aState)
	{
		State = StrTool.cTrim(aState);
	}

	public String getOperator()
	{
		return Operator;
	}

	public void setOperator(String aOperator)
	{
		Operator = StrTool.cTrim(aOperator);
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

	public String getModifyDate()
	{
		if (ModifyDate != null)
			return fDate.getString(ModifyDate);
		else
			return null;
	}

	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}

	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			ModifyDate = fDate.getDate(aModifyDate);
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}

	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = StrTool.cTrim(aModifyTime);
	}

	/**
	 * 使用另外一个 FICustomerAccSchema 对象给 Schema 赋值
	 * @param: aFICustomerAccSchema FICustomerAccSchema
	 **/
	public void setSchema(FICustomerAccSchema aFICustomerAccSchema)
	{
		this.InsuAccNo = aFICustomerAccSchema.getInsuAccNo();
		this.Currency = aFICustomerAccSchema.getCurrency();
		this.CustomerNo = aFICustomerAccSchema.getCustomerNo();
		this.CustomerType = aFICustomerAccSchema.getCustomerType();
		this.AccType = aFICustomerAccSchema.getAccType();
		this.Summoney = aFICustomerAccSchema.getSummoney();
		this.State = aFICustomerAccSchema.getState();
		this.Operator = aFICustomerAccSchema.getOperator();
		this.MakeDate = fDate.getDate(aFICustomerAccSchema.getMakeDate());
		this.MakeTime = aFICustomerAccSchema.getMakeTime();
		this.ModifyDate = fDate.getDate(aFICustomerAccSchema.getModifyDate());
		this.ModifyTime = aFICustomerAccSchema.getModifyTime();
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
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString(1);

			if (rs.getString(2) == null)
				this.Currency = null;
			else
				this.Currency = rs.getString(2);

			if (rs.getString(3) == null)
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString(3);

			if (rs.getString(4) == null)
				this.CustomerType = null;
			else
				this.CustomerType = rs.getString(4);

			if (rs.getString(5) == null)
				this.AccType = null;
			else
				this.AccType = rs.getString(5);

			this.Summoney = rs.getDouble(6);
			if (rs.getString(7) == null)
				this.State = null;
			else
				this.State = rs.getString(7);

			if (rs.getString(8) == null)
				this.Operator = null;
			else
				this.Operator = rs.getString(8);

			this.MakeDate = rs.getDate(9);
			if (rs.getString(10) == null)
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString(10);

			this.ModifyDate = rs.getDate(11);
			if (rs.getString(12) == null)
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString(12);

		}
		catch (SQLException sqle)
		{
			logger.debug("数据库中的FICustomerAcc表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public FICustomerAccSchema getSchema()
	{
		FICustomerAccSchema aFICustomerAccSchema = new FICustomerAccSchema();
		aFICustomerAccSchema.setSchema(this);
		return aFICustomerAccSchema;
	}

	public FICustomerAccDB getDB()
	{
		FICustomerAccDB aDBOper = new FICustomerAccDB();
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
		strReturn.append(StrTool.cTrim(InsuAccNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(ChgData.chgData(Summoney));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
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
	 * 数据解包，解包顺序参见<A href ={@docRoot}
	 * /dataStructure/tb.html#PrpFICustomerAcc>历史记账凭证主表信息</A>表字段
	 * @param: strMessage String 包含一条纪录数据的字符串
	 * @return: boolean
	 **/
	public boolean decode(String strMessage)
	{
		try
		{
			strMessage = StrTool.cTrim(strMessage);
			InsuAccNo = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			Currency = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			CustomerNo = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			CustomerType = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			AccType = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			Summoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER))).doubleValue();
			State = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER);
			Operator = StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER);
			MakeDate = fDate.getDate(StrTool.getStr(strMessage, 9, SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(strMessage, 10, SysConst.PACKAGESPILTER);
			ModifyDate = fDate.getDate(StrTool.getStr(strMessage, 11, SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(strMessage, 12, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccSchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.cTrim(InsuAccNo);
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.cTrim(Currency);
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.cTrim(CustomerNo);
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			strReturn = StrTool.cTrim(CustomerType);
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.cTrim(AccType);
		}
		if (FCode.equalsIgnoreCase("Summoney"))
		{
			strReturn = StrTool.cTrim(String.valueOf(Summoney));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.cTrim(State);
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.cTrim(Operator);
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.cTrim(this.getMakeDate());
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.cTrim(MakeTime);
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.cTrim(this.getModifyDate());
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.cTrim(ModifyTime);
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
				strFieldValue = StrTool.cTrim(InsuAccNo);
				break;
			case 1:
				strFieldValue = StrTool.cTrim(Currency);
				break;
			case 2:
				strFieldValue = StrTool.cTrim(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.cTrim(CustomerType);
				break;
			case 4:
				strFieldValue = StrTool.cTrim(AccType);
				break;
			case 5:
				strFieldValue = String.valueOf(Summoney);
				break;
			case 6:
				strFieldValue = StrTool.cTrim(State);
				break;
			case 7:
				strFieldValue = StrTool.cTrim(Operator);
				break;
			case 8:
				strFieldValue = StrTool.cTrim(this.getMakeDate());
				break;
			case 9:
				strFieldValue = StrTool.cTrim(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.cTrim(this.getModifyDate());
				break;
			case 11:
				strFieldValue = StrTool.cTrim(ModifyTime);
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

		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CustomerType = FValue.trim();
			}
			else
				CustomerType = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("Summoney"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double(FValue);
				double d = tDouble.doubleValue();
				Summoney = d;
			}
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
		FICustomerAccSchema other = (FICustomerAccSchema) otherObject;
		return InsuAccNo.equals(other.getInsuAccNo()) && Currency.equals(other.getCurrency())
				&& CustomerNo.equals(other.getCustomerNo()) && CustomerType.equals(other.getCustomerType())
				&& AccType.equals(other.getAccType()) && Summoney == other.getSummoney()
				&& State.equals(other.getState()) && Operator.equals(other.getOperator())
				&& fDate.getString(MakeDate).equals(other.getMakeDate()) && MakeTime.equals(other.getMakeTime())
				&& fDate.getString(ModifyDate).equals(other.getModifyDate())
				&& ModifyTime.equals(other.getModifyTime());
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
		if (strFieldName.equals("InsuAccNo"))
		{
			return 0;
		}
		if (strFieldName.equals("Currency"))
		{
			return 1;
		}
		if (strFieldName.equals("CustomerNo"))
		{
			return 2;
		}
		if (strFieldName.equals("CustomerType"))
		{
			return 3;
		}
		if (strFieldName.equals("AccType"))
		{
			return 4;
		}
		if (strFieldName.equals("Summoney"))
		{
			return 5;
		}
		if (strFieldName.equals("State"))
		{
			return 6;
		}
		if (strFieldName.equals("Operator"))
		{
			return 7;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return 8;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return 9;
		}
		if (strFieldName.equals("ModifyDate"))
		{
			return 10;
		}
		if (strFieldName.equals("ModifyTime"))
		{
			return 11;
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
				strFieldName = "InsuAccNo";
				break;
			case 1:
				strFieldName = "Currency";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "CustomerType";
				break;
			case 4:
				strFieldName = "AccType";
				break;
			case 5:
				strFieldName = "Summoney";
				break;
			case 6:
				strFieldName = "State";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
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
		if (strFieldName.equals("InsuAccNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Currency"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("CustomerNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("CustomerType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("AccType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Summoney"))
		{
			return Schema.TYPE_DOUBLE;
		}
		if (strFieldName.equals("State"))
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}