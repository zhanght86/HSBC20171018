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
import com.sinosoft.lis.db.LMRiskFeeParamDB;

/*
 * <p>ClassName: LMRiskFeeParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskFeeParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskFeeParamSchema.class);

	// @Field
	/** 管理费编码 */
	private String FeeCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 交费项编码 */
	private String PayPlanCode;
	/** 管理费计算方式 */
	private String FeeCalMode;
	/** Feeid */
	private int FeeID;
	/** 费用下限 */
	private double FeeMin;
	/** 费用上限 */
	private double FeeMax;
	/** 管理费比例 */
	private double FeeRate;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskFeeParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "FeeCode";
		pk[1] = "InsuAccNo";
		pk[2] = "PayPlanCode";
		pk[3] = "FeeCalMode";
		pk[4] = "FeeID";

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
		LMRiskFeeParamSchema cloned = (LMRiskFeeParamSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		FeeCode = aFeeCode;
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
	* 如果该编码前6位为全0，则表示加费。<p>
	* 第7,8位表示加费的次数。
	*/
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	/**
	* 01-固定值（内扣）<p>
	* 02-固定比例 （内扣）<p>
	* 03-固定值（外缴）<p>
	* 04-固定比例 （外缴）<p>
	* 05-Min(固定值与比例结合)<p>
	* 06-Max(固定值和比例结合）<p>
	* 07-分档计算<p>
	* 08-累计分档计算
	*/
	public String getFeeCalMode()
	{
		return FeeCalMode;
	}
	public void setFeeCalMode(String aFeeCalMode)
	{
		FeeCalMode = aFeeCalMode;
	}
	public int getFeeID()
	{
		return FeeID;
	}
	public void setFeeID(int aFeeID)
	{
		FeeID = aFeeID;
	}
	public void setFeeID(String aFeeID)
	{
		if (aFeeID != null && !aFeeID.equals(""))
		{
			Integer tInteger = new Integer(aFeeID);
			int i = tInteger.intValue();
			FeeID = i;
		}
	}

	public double getFeeMin()
	{
		return FeeMin;
	}
	public void setFeeMin(double aFeeMin)
	{
		FeeMin = aFeeMin;
	}
	public void setFeeMin(String aFeeMin)
	{
		if (aFeeMin != null && !aFeeMin.equals(""))
		{
			Double tDouble = new Double(aFeeMin);
			double d = tDouble.doubleValue();
			FeeMin = d;
		}
	}

	public double getFeeMax()
	{
		return FeeMax;
	}
	public void setFeeMax(double aFeeMax)
	{
		FeeMax = aFeeMax;
	}
	public void setFeeMax(String aFeeMax)
	{
		if (aFeeMax != null && !aFeeMax.equals(""))
		{
			Double tDouble = new Double(aFeeMax);
			double d = tDouble.doubleValue();
			FeeMax = d;
		}
	}

	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}


	/**
	* 使用另外一个 LMRiskFeeParamSchema 对象给 Schema 赋值
	* @param: aLMRiskFeeParamSchema LMRiskFeeParamSchema
	**/
	public void setSchema(LMRiskFeeParamSchema aLMRiskFeeParamSchema)
	{
		this.FeeCode = aLMRiskFeeParamSchema.getFeeCode();
		this.InsuAccNo = aLMRiskFeeParamSchema.getInsuAccNo();
		this.PayPlanCode = aLMRiskFeeParamSchema.getPayPlanCode();
		this.FeeCalMode = aLMRiskFeeParamSchema.getFeeCalMode();
		this.FeeID = aLMRiskFeeParamSchema.getFeeID();
		this.FeeMin = aLMRiskFeeParamSchema.getFeeMin();
		this.FeeMax = aLMRiskFeeParamSchema.getFeeMax();
		this.FeeRate = aLMRiskFeeParamSchema.getFeeRate();
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
			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("FeeCalMode") == null )
				this.FeeCalMode = null;
			else
				this.FeeCalMode = rs.getString("FeeCalMode").trim();

			this.FeeID = rs.getInt("FeeID");
			this.FeeMin = rs.getDouble("FeeMin");
			this.FeeMax = rs.getDouble("FeeMax");
			this.FeeRate = rs.getDouble("FeeRate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskFeeParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskFeeParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskFeeParamSchema getSchema()
	{
		LMRiskFeeParamSchema aLMRiskFeeParamSchema = new LMRiskFeeParamSchema();
		aLMRiskFeeParamSchema.setSchema(this);
		return aLMRiskFeeParamSchema;
	}

	public LMRiskFeeParamDB getDB()
	{
		LMRiskFeeParamDB aDBOper = new LMRiskFeeParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskFeeParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeMin));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeMax));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskFeeParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FeeCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			FeeMin = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			FeeMax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskFeeParamSchema";
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("FeeCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalMode));
		}
		if (FCode.equalsIgnoreCase("FeeID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeID));
		}
		if (FCode.equalsIgnoreCase("FeeMin"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMin));
		}
		if (FCode.equalsIgnoreCase("FeeMax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMax));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
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
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeCalMode);
				break;
			case 4:
				strFieldValue = String.valueOf(FeeID);
				break;
			case 5:
				strFieldValue = String.valueOf(FeeMin);
				break;
			case 6:
				strFieldValue = String.valueOf(FeeMax);
				break;
			case 7:
				strFieldValue = String.valueOf(FeeRate);
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

		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalMode = FValue.trim();
			}
			else
				FeeCalMode = null;
		}
		if (FCode.equalsIgnoreCase("FeeID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FeeID = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeMin"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeMin = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeMax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeMax = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskFeeParamSchema other = (LMRiskFeeParamSchema)otherObject;
		return
			FeeCode.equals(other.getFeeCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& FeeCalMode.equals(other.getFeeCalMode())
			&& FeeID == other.getFeeID()
			&& FeeMin == other.getFeeMin()
			&& FeeMax == other.getFeeMax()
			&& FeeRate == other.getFeeRate();
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
		if( strFieldName.equals("FeeCode") ) {
			return 0;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 1;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("FeeCalMode") ) {
			return 3;
		}
		if( strFieldName.equals("FeeID") ) {
			return 4;
		}
		if( strFieldName.equals("FeeMin") ) {
			return 5;
		}
		if( strFieldName.equals("FeeMax") ) {
			return 6;
		}
		if( strFieldName.equals("FeeRate") ) {
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
				strFieldName = "FeeCode";
				break;
			case 1:
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "PayPlanCode";
				break;
			case 3:
				strFieldName = "FeeCalMode";
				break;
			case 4:
				strFieldName = "FeeID";
				break;
			case 5:
				strFieldName = "FeeMin";
				break;
			case 6:
				strFieldName = "FeeMax";
				break;
			case 7:
				strFieldName = "FeeRate";
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
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeMin") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeMax") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeRate") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
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
