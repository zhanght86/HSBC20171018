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
import com.sinosoft.lis.db.LMGetCalModeDB;

/*
 * <p>ClassName: LMGetCalModeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMGetCalModeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMGetCalModeSchema.class);
	// @Field
	/** 序号 */
	private String SerialNo;
	/** 险种编码 */
	private String RiskCode;
	/** 账户类型 */
	private String AccType;
	/** 退费类型 */
	private String GetType;
	/** 费用比例值 */
	private String FeeValue;
	/** 计算公式 */
	private String FeeCalCode;
	/** 算法编码 */
	private String CalCode;
	/** 算法编码2 */
	private String CalCode2;
	/** 有效区间单位 */
	private String ValPeriod;
	/** 有效起期 */
	private String ValStartDate;
	/** 有效止期 */
	private String ValEndDate;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMGetCalModeSchema()
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
	public Object clone()
		throws CloneNotSupportedException
	{
		LMGetCalModeSchema cloned = (LMGetCalModeSchema)super.clone();
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
		if(aSerialNo!=null && aSerialNo.length()>10)
			throw new IllegalArgumentException("序号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值10");
		SerialNo = aSerialNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	/**
	* 预留
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		if(aAccType!=null && aAccType.length()>10)
			throw new IllegalArgumentException("账户类型AccType值"+aAccType+"的长度"+aAccType.length()+"大于最大值10");
		AccType = aAccType;
	}
	/**
	* ZT-减人退费<p>
	* CT-退保退费
	*/
	public String getGetType()
	{
		return GetType;
	}
	public void setGetType(String aGetType)
	{
		if(aGetType!=null && aGetType.length()>6)
			throw new IllegalArgumentException("退费类型GetType值"+aGetType+"的长度"+aGetType.length()+"大于最大值6");
		GetType = aGetType;
	}
	public String getFeeValue()
	{
		return FeeValue;
	}
	public void setFeeValue(String aFeeValue)
	{
		if(aFeeValue!=null && aFeeValue.length()>20)
			throw new IllegalArgumentException("费用比例值FeeValue值"+aFeeValue+"的长度"+aFeeValue.length()+"大于最大值20");
		FeeValue = aFeeValue;
	}
	public String getFeeCalCode()
	{
		return FeeCalCode;
	}
	public void setFeeCalCode(String aFeeCalCode)
	{
		if(aFeeCalCode!=null && aFeeCalCode.length()>6)
			throw new IllegalArgumentException("计算公式FeeCalCode值"+aFeeCalCode+"的长度"+aFeeCalCode.length()+"大于最大值6");
		FeeCalCode = aFeeCalCode;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		if(aCalCode!=null && aCalCode.length()>20)
			throw new IllegalArgumentException("算法编码CalCode值"+aCalCode+"的长度"+aCalCode.length()+"大于最大值20");
		CalCode = aCalCode;
	}
	/**
	* 预留
	*/
	public String getCalCode2()
	{
		return CalCode2;
	}
	public void setCalCode2(String aCalCode2)
	{
		if(aCalCode2!=null && aCalCode2.length()>20)
			throw new IllegalArgumentException("算法编码2CalCode2值"+aCalCode2+"的长度"+aCalCode2.length()+"大于最大值20");
		CalCode2 = aCalCode2;
	}
	/**
	* Y-年<p>
	* D-日
	*/
	public String getValPeriod()
	{
		return ValPeriod;
	}
	public void setValPeriod(String aValPeriod)
	{
		if(aValPeriod!=null && aValPeriod.length()>6)
			throw new IllegalArgumentException("有效区间单位ValPeriod值"+aValPeriod+"的长度"+aValPeriod.length()+"大于最大值6");
		ValPeriod = aValPeriod;
	}
	public String getValStartDate()
	{
		return ValStartDate;
	}
	public void setValStartDate(String aValStartDate)
	{
		if(aValStartDate!=null && aValStartDate.length()>6)
			throw new IllegalArgumentException("有效起期ValStartDate值"+aValStartDate+"的长度"+aValStartDate.length()+"大于最大值6");
		ValStartDate = aValStartDate;
	}
	public String getValEndDate()
	{
		return ValEndDate;
	}
	public void setValEndDate(String aValEndDate)
	{
		if(aValEndDate!=null && aValEndDate.length()>6)
			throw new IllegalArgumentException("有效止期ValEndDate值"+aValEndDate+"的长度"+aValEndDate.length()+"大于最大值6");
		ValEndDate = aValEndDate;
	}

	/**
	* 使用另外一个 LMGetCalModeSchema 对象给 Schema 赋值
	* @param: aLMGetCalModeSchema LMGetCalModeSchema
	**/
	public void setSchema(LMGetCalModeSchema aLMGetCalModeSchema)
	{
		this.SerialNo = aLMGetCalModeSchema.getSerialNo();
		this.RiskCode = aLMGetCalModeSchema.getRiskCode();
		this.AccType = aLMGetCalModeSchema.getAccType();
		this.GetType = aLMGetCalModeSchema.getGetType();
		this.FeeValue = aLMGetCalModeSchema.getFeeValue();
		this.FeeCalCode = aLMGetCalModeSchema.getFeeCalCode();
		this.CalCode = aLMGetCalModeSchema.getCalCode();
		this.CalCode2 = aLMGetCalModeSchema.getCalCode2();
		this.ValPeriod = aLMGetCalModeSchema.getValPeriod();
		this.ValStartDate = aLMGetCalModeSchema.getValStartDate();
		this.ValEndDate = aLMGetCalModeSchema.getValEndDate();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("GetType") == null )
				this.GetType = null;
			else
				this.GetType = rs.getString("GetType").trim();

			if( rs.getString("FeeValue") == null )
				this.FeeValue = null;
			else
				this.FeeValue = rs.getString("FeeValue").trim();

			if( rs.getString("FeeCalCode") == null )
				this.FeeCalCode = null;
			else
				this.FeeCalCode = rs.getString("FeeCalCode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalCode2") == null )
				this.CalCode2 = null;
			else
				this.CalCode2 = rs.getString("CalCode2").trim();

			if( rs.getString("ValPeriod") == null )
				this.ValPeriod = null;
			else
				this.ValPeriod = rs.getString("ValPeriod").trim();

			if( rs.getString("ValStartDate") == null )
				this.ValStartDate = null;
			else
				this.ValStartDate = rs.getString("ValStartDate").trim();

			if( rs.getString("ValEndDate") == null )
				this.ValEndDate = null;
			else
				this.ValEndDate = rs.getString("ValEndDate").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMGetCalMode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMGetCalModeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMGetCalModeSchema getSchema()
	{
		LMGetCalModeSchema aLMGetCalModeSchema = new LMGetCalModeSchema();
		aLMGetCalModeSchema.setSchema(this);
		return aLMGetCalModeSchema;
	}

	public LMGetCalModeDB getDB()
	{
		LMGetCalModeDB aDBOper = new LMGetCalModeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMGetCalMode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValStartDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValEndDate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMGetCalMode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ValPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ValStartDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ValEndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMGetCalModeSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("GetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetType));
		}
		if (FCode.equalsIgnoreCase("FeeValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeValue));
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalCode));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode2));
		}
		if (FCode.equalsIgnoreCase("ValPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValPeriod));
		}
		if (FCode.equalsIgnoreCase("ValStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValStartDate));
		}
		if (FCode.equalsIgnoreCase("ValEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValEndDate));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FeeValue);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FeeCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCode2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ValPeriod);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ValStartDate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ValEndDate);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("GetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetType = FValue.trim();
			}
			else
				GetType = null;
		}
		if (FCode.equalsIgnoreCase("FeeValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeValue = FValue.trim();
			}
			else
				FeeValue = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalCode = FValue.trim();
			}
			else
				FeeCalCode = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode2 = FValue.trim();
			}
			else
				CalCode2 = null;
		}
		if (FCode.equalsIgnoreCase("ValPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValPeriod = FValue.trim();
			}
			else
				ValPeriod = null;
		}
		if (FCode.equalsIgnoreCase("ValStartDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValStartDate = FValue.trim();
			}
			else
				ValStartDate = null;
		}
		if (FCode.equalsIgnoreCase("ValEndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValEndDate = FValue.trim();
			}
			else
				ValEndDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMGetCalModeSchema other = (LMGetCalModeSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& GetType.equals(other.getGetType())
			&& FeeValue.equals(other.getFeeValue())
			&& FeeCalCode.equals(other.getFeeCalCode())
			&& CalCode.equals(other.getCalCode())
			&& CalCode2.equals(other.getCalCode2())
			&& ValPeriod.equals(other.getValPeriod())
			&& ValStartDate.equals(other.getValStartDate())
			&& ValEndDate.equals(other.getValEndDate());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("AccType") ) {
			return 2;
		}
		if( strFieldName.equals("GetType") ) {
			return 3;
		}
		if( strFieldName.equals("FeeValue") ) {
			return 4;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("CalCode") ) {
			return 6;
		}
		if( strFieldName.equals("CalCode2") ) {
			return 7;
		}
		if( strFieldName.equals("ValPeriod") ) {
			return 8;
		}
		if( strFieldName.equals("ValStartDate") ) {
			return 9;
		}
		if( strFieldName.equals("ValEndDate") ) {
			return 10;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "AccType";
				break;
			case 3:
				strFieldName = "GetType";
				break;
			case 4:
				strFieldName = "FeeValue";
				break;
			case 5:
				strFieldName = "FeeCalCode";
				break;
			case 6:
				strFieldName = "CalCode";
				break;
			case 7:
				strFieldName = "CalCode2";
				break;
			case 8:
				strFieldName = "ValPeriod";
				break;
			case 9:
				strFieldName = "ValStartDate";
				break;
			case 10:
				strFieldName = "ValEndDate";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValStartDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValEndDate") ) {
			return Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
