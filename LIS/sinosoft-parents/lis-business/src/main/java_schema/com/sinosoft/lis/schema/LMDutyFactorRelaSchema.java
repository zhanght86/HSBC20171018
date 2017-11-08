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
import com.sinosoft.lis.db.LMDutyFactorRelaDB;

/*
 * <p>ClassName: LMDutyFactorRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMDutyFactorRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyFactorRelaSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 要素编码 */
	private String FactorCode;
	/** 强制标志 */
	private String MandatoryFlag;
	/** 默认值 */
	private String DefaultValue;
	/** 要素顺序 */
	private int FactorOrder;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyFactorRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "FactorCode";

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
		LMDutyFactorRelaSchema cloned = (LMDutyFactorRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 责任拓展关联时：险种编码默认为000000
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>10)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值10");
		DutyCode = aDutyCode;
	}
	public String getFactorCode()
	{
		return FactorCode;
	}
	public void setFactorCode(String aFactorCode)
	{
		if(aFactorCode!=null && aFactorCode.length()>20)
			throw new IllegalArgumentException("要素编码FactorCode值"+aFactorCode+"的长度"+aFactorCode.length()+"大于最大值20");
		FactorCode = aFactorCode;
	}
	/**
	* 0-否.，1-是
	*/
	public String getMandatoryFlag()
	{
		return MandatoryFlag;
	}
	public void setMandatoryFlag(String aMandatoryFlag)
	{
		if(aMandatoryFlag!=null && aMandatoryFlag.length()>2)
			throw new IllegalArgumentException("强制标志MandatoryFlag值"+aMandatoryFlag+"的长度"+aMandatoryFlag.length()+"大于最大值2");
		MandatoryFlag = aMandatoryFlag;
	}
	public String getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		if(aDefaultValue!=null && aDefaultValue.length()>20)
			throw new IllegalArgumentException("默认值DefaultValue值"+aDefaultValue+"的长度"+aDefaultValue.length()+"大于最大值20");
		DefaultValue = aDefaultValue;
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


	/**
	* 使用另外一个 LMDutyFactorRelaSchema 对象给 Schema 赋值
	* @param: aLMDutyFactorRelaSchema LMDutyFactorRelaSchema
	**/
	public void setSchema(LMDutyFactorRelaSchema aLMDutyFactorRelaSchema)
	{
		this.RiskCode = aLMDutyFactorRelaSchema.getRiskCode();
		this.DutyCode = aLMDutyFactorRelaSchema.getDutyCode();
		this.FactorCode = aLMDutyFactorRelaSchema.getFactorCode();
		this.MandatoryFlag = aLMDutyFactorRelaSchema.getMandatoryFlag();
		this.DefaultValue = aLMDutyFactorRelaSchema.getDefaultValue();
		this.FactorOrder = aLMDutyFactorRelaSchema.getFactorOrder();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("MandatoryFlag") == null )
				this.MandatoryFlag = null;
			else
				this.MandatoryFlag = rs.getString("MandatoryFlag").trim();

			if( rs.getString("DefaultValue") == null )
				this.DefaultValue = null;
			else
				this.DefaultValue = rs.getString("DefaultValue").trim();

			this.FactorOrder = rs.getInt("FactorOrder");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyFactorRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyFactorRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyFactorRelaSchema getSchema()
	{
		LMDutyFactorRelaSchema aLMDutyFactorRelaSchema = new LMDutyFactorRelaSchema();
		aLMDutyFactorRelaSchema.setSchema(this);
		return aLMDutyFactorRelaSchema;
	}

	public LMDutyFactorRelaDB getDB()
	{
		LMDutyFactorRelaDB aDBOper = new LMDutyFactorRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyFactorRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MandatoryFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorOrder));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyFactorRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MandatoryFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DefaultValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FactorOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyFactorRelaSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equalsIgnoreCase("MandatoryFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MandatoryFlag));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("FactorOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorOrder));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MandatoryFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DefaultValue);
				break;
			case 5:
				strFieldValue = String.valueOf(FactorOrder);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("MandatoryFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MandatoryFlag = FValue.trim();
			}
			else
				MandatoryFlag = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultValue = FValue.trim();
			}
			else
				DefaultValue = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyFactorRelaSchema other = (LMDutyFactorRelaSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& FactorCode.equals(other.getFactorCode())
			&& MandatoryFlag.equals(other.getMandatoryFlag())
			&& DefaultValue.equals(other.getDefaultValue())
			&& FactorOrder == other.getFactorOrder();
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 1;
		}
		if( strFieldName.equals("FactorCode") ) {
			return 2;
		}
		if( strFieldName.equals("MandatoryFlag") ) {
			return 3;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 4;
		}
		if( strFieldName.equals("FactorOrder") ) {
			return 5;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "FactorCode";
				break;
			case 3:
				strFieldName = "MandatoryFlag";
				break;
			case 4:
				strFieldName = "DefaultValue";
				break;
			case 5:
				strFieldName = "FactorOrder";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MandatoryFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorOrder") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
