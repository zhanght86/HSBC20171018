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
import com.sinosoft.lis.db.LMAccTriggerDB;

/*
 * <p>ClassName: LMAccTriggerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LMAccTriggerSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMAccTriggerSchema.class);

	// @Field
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 账户范围 */
	private String AccType;
	/** 处理计算方法 */
	private String ActionCalMode;
	/** 计算类型 */
	private String ActionCalModeType;
	/** 计算公式 */
	private String ActionCalCode;
	/** 固定值 */
	private double Value;
	/** 比较值 */
	private double CompareValue;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMAccTriggerSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "PayPlanCode";
		pk[1] = "InsuAccNo";
		pk[2] = "ActionCalMode";

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
		LMAccTriggerSchema cloned = (LMAccTriggerSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 按照账户进行处理交费计划编码为全零。
	*/
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	/**
	* 00-账户<p>
	* 01-子账户
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	/**
	* 01-固定值（内扣）<p>
	* 02-固定比例 （内扣）<p>
	* 03-固定值（外缴）<p>
	* 04-固定比例 （外缴）<p>
	* 05-Min(固定值与比例结合)<p>
	* 06-Max(固定值和比例结合）
	*/
	public String getActionCalMode()
	{
		return ActionCalMode;
	}
	public void setActionCalMode(String aActionCalMode)
	{
		ActionCalMode = aActionCalMode;
	}
	/**
	* 0-直接取值<p>
	* 1-sql描述
	*/
	public String getActionCalModeType()
	{
		return ActionCalModeType;
	}
	public void setActionCalModeType(String aActionCalModeType)
	{
		ActionCalModeType = aActionCalModeType;
	}
	public String getActionCalCode()
	{
		return ActionCalCode;
	}
	public void setActionCalCode(String aActionCalCode)
	{
		ActionCalCode = aActionCalCode;
	}
	public double getValue()
	{
		return Value;
	}
	public void setValue(double aValue)
	{
		Value = aValue;
	}
	public void setValue(String aValue)
	{
		if (aValue != null && !aValue.equals(""))
		{
			Double tDouble = new Double(aValue);
			double d = tDouble.doubleValue();
			Value = d;
		}
	}

	public double getCompareValue()
	{
		return CompareValue;
	}
	public void setCompareValue(double aCompareValue)
	{
		CompareValue = aCompareValue;
	}
	public void setCompareValue(String aCompareValue)
	{
		if (aCompareValue != null && !aCompareValue.equals(""))
		{
			Double tDouble = new Double(aCompareValue);
			double d = tDouble.doubleValue();
			CompareValue = d;
		}
	}


	/**
	* 使用另外一个 LMAccTriggerSchema 对象给 Schema 赋值
	* @param: aLMAccTriggerSchema LMAccTriggerSchema
	**/
	public void setSchema(LMAccTriggerSchema aLMAccTriggerSchema)
	{
		this.PayPlanCode = aLMAccTriggerSchema.getPayPlanCode();
		this.InsuAccNo = aLMAccTriggerSchema.getInsuAccNo();
		this.AccType = aLMAccTriggerSchema.getAccType();
		this.ActionCalMode = aLMAccTriggerSchema.getActionCalMode();
		this.ActionCalModeType = aLMAccTriggerSchema.getActionCalModeType();
		this.ActionCalCode = aLMAccTriggerSchema.getActionCalCode();
		this.Value = aLMAccTriggerSchema.getValue();
		this.CompareValue = aLMAccTriggerSchema.getCompareValue();
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
			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("ActionCalMode") == null )
				this.ActionCalMode = null;
			else
				this.ActionCalMode = rs.getString("ActionCalMode").trim();

			if( rs.getString("ActionCalModeType") == null )
				this.ActionCalModeType = null;
			else
				this.ActionCalModeType = rs.getString("ActionCalModeType").trim();

			if( rs.getString("ActionCalCode") == null )
				this.ActionCalCode = null;
			else
				this.ActionCalCode = rs.getString("ActionCalCode").trim();

			this.Value = rs.getDouble("Value");
			this.CompareValue = rs.getDouble("CompareValue");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMAccTrigger表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMAccTriggerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMAccTriggerSchema getSchema()
	{
		LMAccTriggerSchema aLMAccTriggerSchema = new LMAccTriggerSchema();
		aLMAccTriggerSchema.setSchema(this);
		return aLMAccTriggerSchema;
	}

	public LMAccTriggerDB getDB()
	{
		LMAccTriggerDB aDBOper = new LMAccTriggerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMAccTrigger描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCalModeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Value));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CompareValue));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMAccTrigger>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ActionCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ActionCalModeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ActionCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Value = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			CompareValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMAccTriggerSchema";
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("ActionCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCalMode));
		}
		if (FCode.equalsIgnoreCase("ActionCalModeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCalModeType));
		}
		if (FCode.equalsIgnoreCase("ActionCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCalCode));
		}
		if (FCode.equalsIgnoreCase("Value"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Value));
		}
		if (FCode.equalsIgnoreCase("CompareValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompareValue));
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
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ActionCalMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ActionCalModeType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ActionCalCode);
				break;
			case 6:
				strFieldValue = String.valueOf(Value);
				break;
			case 7:
				strFieldValue = String.valueOf(CompareValue);
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

		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("ActionCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCalMode = FValue.trim();
			}
			else
				ActionCalMode = null;
		}
		if (FCode.equalsIgnoreCase("ActionCalModeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCalModeType = FValue.trim();
			}
			else
				ActionCalModeType = null;
		}
		if (FCode.equalsIgnoreCase("ActionCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCalCode = FValue.trim();
			}
			else
				ActionCalCode = null;
		}
		if (FCode.equalsIgnoreCase("Value"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Value = d;
			}
		}
		if (FCode.equalsIgnoreCase("CompareValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CompareValue = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMAccTriggerSchema other = (LMAccTriggerSchema)otherObject;
		return
			PayPlanCode.equals(other.getPayPlanCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& AccType.equals(other.getAccType())
			&& ActionCalMode.equals(other.getActionCalMode())
			&& ActionCalModeType.equals(other.getActionCalModeType())
			&& ActionCalCode.equals(other.getActionCalCode())
			&& Value == other.getValue()
			&& CompareValue == other.getCompareValue();
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
		if( strFieldName.equals("PayPlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 1;
		}
		if( strFieldName.equals("AccType") ) {
			return 2;
		}
		if( strFieldName.equals("ActionCalMode") ) {
			return 3;
		}
		if( strFieldName.equals("ActionCalModeType") ) {
			return 4;
		}
		if( strFieldName.equals("ActionCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("Value") ) {
			return 6;
		}
		if( strFieldName.equals("CompareValue") ) {
			return 7;
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
				strFieldName = "PayPlanCode";
				break;
			case 1:
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "AccType";
				break;
			case 3:
				strFieldName = "ActionCalMode";
				break;
			case 4:
				strFieldName = "ActionCalModeType";
				break;
			case 5:
				strFieldName = "ActionCalCode";
				break;
			case 6:
				strFieldName = "Value";
				break;
			case 7:
				strFieldName = "CompareValue";
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
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCalModeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Value") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CompareValue") ) {
			return Schema.TYPE_DOUBLE;
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
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
