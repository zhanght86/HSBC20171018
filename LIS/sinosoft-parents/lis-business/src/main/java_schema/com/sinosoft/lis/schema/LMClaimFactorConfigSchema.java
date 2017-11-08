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
import com.sinosoft.lis.db.LMClaimFactorConfigDB;

/*
 * <p>ClassName: LMClaimFactorConfigSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMClaimFactorConfigSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMClaimFactorConfigSchema.class);
	// @Field
	/** 要素类型编码 */
	private String FactorType;
	/** 要素类型名称 */
	private String FactorTypeName;
	/** 要素属性 */
	private String FactorAttribute;
	/** 要素控制 */
	private String FactorControl;
	/** 要素排序 */
	private int FactorOrder;
	/** 要素编码 */
	private String FactorCode;
	/** 要素名称 */
	private String FactorName;
	/** 要素明细 */
	private String CalRemark;
	/** 参数个数 */
	private int ParamsNum;
	/** 参数 */
	private String Params;
	/** 计算sql */
	private String CalSQL;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMClaimFactorConfigSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "FactorType";
		pk[1] = "FactorCode";

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
		LMClaimFactorConfigSchema cloned = (LMClaimFactorConfigSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFactorType()
	{
		return FactorType;
	}
	public void setFactorType(String aFactorType)
	{
		if(aFactorType!=null && aFactorType.length()>10)
			throw new IllegalArgumentException("要素类型编码FactorType值"+aFactorType+"的长度"+aFactorType.length()+"大于最大值10");
		FactorType = aFactorType;
	}
	public String getFactorTypeName()
	{
		return FactorTypeName;
	}
	public void setFactorTypeName(String aFactorTypeName)
	{
		if(aFactorTypeName!=null && aFactorTypeName.length()>100)
			throw new IllegalArgumentException("要素类型名称FactorTypeName值"+aFactorTypeName+"的长度"+aFactorTypeName.length()+"大于最大值100");
		FactorTypeName = aFactorTypeName;
	}
	/**
	* 0-全部，1-要素控制，2-共用控制
	*/
	public String getFactorAttribute()
	{
		return FactorAttribute;
	}
	public void setFactorAttribute(String aFactorAttribute)
	{
		if(aFactorAttribute!=null && aFactorAttribute.length()>2)
			throw new IllegalArgumentException("要素属性FactorAttribute值"+aFactorAttribute+"的长度"+aFactorAttribute.length()+"大于最大值2");
		FactorAttribute = aFactorAttribute;
	}
	/**
	* 0-一条，1-多条
	*/
	public String getFactorControl()
	{
		return FactorControl;
	}
	public void setFactorControl(String aFactorControl)
	{
		if(aFactorControl!=null && aFactorControl.length()>2)
			throw new IllegalArgumentException("要素控制FactorControl值"+aFactorControl+"的长度"+aFactorControl.length()+"大于最大值2");
		FactorControl = aFactorControl;
	}
	public int getFactorOrder()
	{
		return FactorOrder;
	}
	public void setFactorOrder(int aFactorOrder)
	{
		FactorOrder = aFactorOrder;
	}
	public void setFactorOrder(String aFactorOrder)
	{
		if (aFactorOrder != null && !aFactorOrder.equals(""))
		{
			Integer tInteger = new Integer(aFactorOrder);
			int i = tInteger.intValue();
			FactorOrder = i;
		}
	}

	public String getFactorCode()
	{
		return FactorCode;
	}
	public void setFactorCode(String aFactorCode)
	{
		if(aFactorCode!=null && aFactorCode.length()>50)
			throw new IllegalArgumentException("要素编码FactorCode值"+aFactorCode+"的长度"+aFactorCode.length()+"大于最大值50");
		FactorCode = aFactorCode;
	}
	public String getFactorName()
	{
		return FactorName;
	}
	public void setFactorName(String aFactorName)
	{
		if(aFactorName!=null && aFactorName.length()>100)
			throw new IllegalArgumentException("要素名称FactorName值"+aFactorName+"的长度"+aFactorName.length()+"大于最大值100");
		FactorName = aFactorName;
	}
	public String getCalRemark()
	{
		return CalRemark;
	}
	public void setCalRemark(String aCalRemark)
	{
		if(aCalRemark!=null && aCalRemark.length()>4000)
			throw new IllegalArgumentException("要素明细CalRemark值"+aCalRemark+"的长度"+aCalRemark.length()+"大于最大值4000");
		CalRemark = aCalRemark;
	}
	public int getParamsNum()
	{
		return ParamsNum;
	}
	public void setParamsNum(int aParamsNum)
	{
		ParamsNum = aParamsNum;
	}
	public void setParamsNum(String aParamsNum)
	{
		if (aParamsNum != null && !aParamsNum.equals(""))
		{
			Integer tInteger = new Integer(aParamsNum);
			int i = tInteger.intValue();
			ParamsNum = i;
		}
	}

	public String getParams()
	{
		return Params;
	}
	public void setParams(String aParams)
	{
		if(aParams!=null && aParams.length()>200)
			throw new IllegalArgumentException("参数Params值"+aParams+"的长度"+aParams.length()+"大于最大值200");
		Params = aParams;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		if(aCalSQL!=null && aCalSQL.length()>4000)
			throw new IllegalArgumentException("计算sqlCalSQL值"+aCalSQL+"的长度"+aCalSQL.length()+"大于最大值4000");
		CalSQL = aCalSQL;
	}

	/**
	* 使用另外一个 LMClaimFactorConfigSchema 对象给 Schema 赋值
	* @param: aLMClaimFactorConfigSchema LMClaimFactorConfigSchema
	**/
	public void setSchema(LMClaimFactorConfigSchema aLMClaimFactorConfigSchema)
	{
		this.FactorType = aLMClaimFactorConfigSchema.getFactorType();
		this.FactorTypeName = aLMClaimFactorConfigSchema.getFactorTypeName();
		this.FactorAttribute = aLMClaimFactorConfigSchema.getFactorAttribute();
		this.FactorControl = aLMClaimFactorConfigSchema.getFactorControl();
		this.FactorOrder = aLMClaimFactorConfigSchema.getFactorOrder();
		this.FactorCode = aLMClaimFactorConfigSchema.getFactorCode();
		this.FactorName = aLMClaimFactorConfigSchema.getFactorName();
		this.CalRemark = aLMClaimFactorConfigSchema.getCalRemark();
		this.ParamsNum = aLMClaimFactorConfigSchema.getParamsNum();
		this.Params = aLMClaimFactorConfigSchema.getParams();
		this.CalSQL = aLMClaimFactorConfigSchema.getCalSQL();
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
			if( rs.getString("FactorType") == null )
				this.FactorType = null;
			else
				this.FactorType = rs.getString("FactorType").trim();

			if( rs.getString("FactorTypeName") == null )
				this.FactorTypeName = null;
			else
				this.FactorTypeName = rs.getString("FactorTypeName").trim();

			if( rs.getString("FactorAttribute") == null )
				this.FactorAttribute = null;
			else
				this.FactorAttribute = rs.getString("FactorAttribute").trim();

			if( rs.getString("FactorControl") == null )
				this.FactorControl = null;
			else
				this.FactorControl = rs.getString("FactorControl").trim();

			this.FactorOrder = rs.getInt("FactorOrder");
			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("CalRemark") == null )
				this.CalRemark = null;
			else
				this.CalRemark = rs.getString("CalRemark").trim();

			this.ParamsNum = rs.getInt("ParamsNum");
			if( rs.getString("Params") == null )
				this.Params = null;
			else
				this.Params = rs.getString("Params").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMClaimFactorConfig表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimFactorConfigSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMClaimFactorConfigSchema getSchema()
	{
		LMClaimFactorConfigSchema aLMClaimFactorConfigSchema = new LMClaimFactorConfigSchema();
		aLMClaimFactorConfigSchema.setSchema(this);
		return aLMClaimFactorConfigSchema;
	}

	public LMClaimFactorConfigDB getDB()
	{
		LMClaimFactorConfigDB aDBOper = new LMClaimFactorConfigDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimFactorConfig描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorAttribute)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorControl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamsNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Params)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimFactorConfig>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactorTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactorAttribute = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorControl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FactorOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ParamsNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			Params = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimFactorConfigSchema";
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
		if (FCode.equalsIgnoreCase("FactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorType));
		}
		if (FCode.equalsIgnoreCase("FactorTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorTypeName));
		}
		if (FCode.equalsIgnoreCase("FactorAttribute"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorAttribute));
		}
		if (FCode.equalsIgnoreCase("FactorControl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorControl));
		}
		if (FCode.equalsIgnoreCase("FactorOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorOrder));
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equalsIgnoreCase("CalRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalRemark));
		}
		if (FCode.equalsIgnoreCase("ParamsNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsNum));
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Params));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
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
				strFieldValue = StrTool.GBKToUnicode(FactorType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FactorTypeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactorAttribute);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactorControl);
				break;
			case 4:
				strFieldValue = String.valueOf(FactorOrder);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalRemark);
				break;
			case 8:
				strFieldValue = String.valueOf(ParamsNum);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Params);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
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

		if (FCode.equalsIgnoreCase("FactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorType = FValue.trim();
			}
			else
				FactorType = null;
		}
		if (FCode.equalsIgnoreCase("FactorTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorTypeName = FValue.trim();
			}
			else
				FactorTypeName = null;
		}
		if (FCode.equalsIgnoreCase("FactorAttribute"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorAttribute = FValue.trim();
			}
			else
				FactorAttribute = null;
		}
		if (FCode.equalsIgnoreCase("FactorControl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorControl = FValue.trim();
			}
			else
				FactorControl = null;
		}
		if (FCode.equalsIgnoreCase("FactorOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FactorOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorCode = FValue.trim();
			}
			else
				FactorCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorName = FValue.trim();
			}
			else
				FactorName = null;
		}
		if (FCode.equalsIgnoreCase("CalRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalRemark = FValue.trim();
			}
			else
				CalRemark = null;
		}
		if (FCode.equalsIgnoreCase("ParamsNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ParamsNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Params = FValue.trim();
			}
			else
				Params = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMClaimFactorConfigSchema other = (LMClaimFactorConfigSchema)otherObject;
		return
			FactorType.equals(other.getFactorType())
			&& FactorTypeName.equals(other.getFactorTypeName())
			&& FactorAttribute.equals(other.getFactorAttribute())
			&& FactorControl.equals(other.getFactorControl())
			&& FactorOrder == other.getFactorOrder()
			&& FactorCode.equals(other.getFactorCode())
			&& FactorName.equals(other.getFactorName())
			&& CalRemark.equals(other.getCalRemark())
			&& ParamsNum == other.getParamsNum()
			&& Params.equals(other.getParams())
			&& CalSQL.equals(other.getCalSQL());
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
		if( strFieldName.equals("FactorType") ) {
			return 0;
		}
		if( strFieldName.equals("FactorTypeName") ) {
			return 1;
		}
		if( strFieldName.equals("FactorAttribute") ) {
			return 2;
		}
		if( strFieldName.equals("FactorControl") ) {
			return 3;
		}
		if( strFieldName.equals("FactorOrder") ) {
			return 4;
		}
		if( strFieldName.equals("FactorCode") ) {
			return 5;
		}
		if( strFieldName.equals("FactorName") ) {
			return 6;
		}
		if( strFieldName.equals("CalRemark") ) {
			return 7;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return 8;
		}
		if( strFieldName.equals("Params") ) {
			return 9;
		}
		if( strFieldName.equals("CalSQL") ) {
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
				strFieldName = "FactorType";
				break;
			case 1:
				strFieldName = "FactorTypeName";
				break;
			case 2:
				strFieldName = "FactorAttribute";
				break;
			case 3:
				strFieldName = "FactorControl";
				break;
			case 4:
				strFieldName = "FactorOrder";
				break;
			case 5:
				strFieldName = "FactorCode";
				break;
			case 6:
				strFieldName = "FactorName";
				break;
			case 7:
				strFieldName = "CalRemark";
				break;
			case 8:
				strFieldName = "ParamsNum";
				break;
			case 9:
				strFieldName = "Params";
				break;
			case 10:
				strFieldName = "CalSQL";
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
		if( strFieldName.equals("FactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorAttribute") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorControl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Params") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
