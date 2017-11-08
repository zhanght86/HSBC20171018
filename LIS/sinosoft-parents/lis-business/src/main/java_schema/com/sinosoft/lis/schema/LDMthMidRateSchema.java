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
import com.sinosoft.lis.db.LDMthMidRateDB;

/*
 * <p>ClassName: LDMthMidRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造基础表
 */
public class LDMthMidRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMthMidRateSchema.class);

	// @Field
	/** 外币币种代码 */
	private String CurrCode;
	/** 外币数额单位 */
	private int Per;
	/** 本币币种代码 */
	private String DestCode;
	/** 月平均中间价 */
	private double Average;
	/** 作用年度 */
	private String ValidYear;
	/** 作用月份 */
	private String ValidMonth;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMthMidRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "CurrCode";
		pk[1] = "Per";
		pk[2] = "DestCode";
		pk[3] = "ValidYear";
		pk[4] = "ValidMonth";

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
		LDMthMidRateSchema cloned = (LDMthMidRateSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCurrCode()
	{
		return CurrCode;
	}
	public void setCurrCode(String aCurrCode)
	{
		CurrCode = aCurrCode;
	}
	public int getPer()
	{
		return Per;
	}
	public void setPer(int aPer)
	{
		Per = aPer;
	}
	public void setPer(String aPer)
	{
		if (aPer != null && !aPer.equals(""))
		{
			Integer tInteger = new Integer(aPer);
			int i = tInteger.intValue();
			Per = i;
		}
	}

	public String getDestCode()
	{
		return DestCode;
	}
	public void setDestCode(String aDestCode)
	{
		DestCode = aDestCode;
	}
	public double getAverage()
	{
		return Average;
	}
	public void setAverage(double aAverage)
	{
		Average = aAverage;
	}
	public void setAverage(String aAverage)
	{
		if (aAverage != null && !aAverage.equals(""))
		{
			Double tDouble = new Double(aAverage);
			double d = tDouble.doubleValue();
			Average = d;
		}
	}

	public String getValidYear()
	{
		return ValidYear;
	}
	public void setValidYear(String aValidYear)
	{
		ValidYear = aValidYear;
	}
	public String getValidMonth()
	{
		return ValidMonth;
	}
	public void setValidMonth(String aValidMonth)
	{
		ValidMonth = aValidMonth;
	}

	/**
	* 使用另外一个 LDMthMidRateSchema 对象给 Schema 赋值
	* @param: aLDMthMidRateSchema LDMthMidRateSchema
	**/
	public void setSchema(LDMthMidRateSchema aLDMthMidRateSchema)
	{
		this.CurrCode = aLDMthMidRateSchema.getCurrCode();
		this.Per = aLDMthMidRateSchema.getPer();
		this.DestCode = aLDMthMidRateSchema.getDestCode();
		this.Average = aLDMthMidRateSchema.getAverage();
		this.ValidYear = aLDMthMidRateSchema.getValidYear();
		this.ValidMonth = aLDMthMidRateSchema.getValidMonth();
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
			if( rs.getString("CurrCode") == null )
				this.CurrCode = null;
			else
				this.CurrCode = rs.getString("CurrCode").trim();

			this.Per = rs.getInt("Per");
			if( rs.getString("DestCode") == null )
				this.DestCode = null;
			else
				this.DestCode = rs.getString("DestCode").trim();

			this.Average = rs.getDouble("Average");
			if( rs.getString("ValidYear") == null )
				this.ValidYear = null;
			else
				this.ValidYear = rs.getString("ValidYear").trim();

			if( rs.getString("ValidMonth") == null )
				this.ValidMonth = null;
			else
				this.ValidMonth = rs.getString("ValidMonth").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMthMidRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMthMidRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMthMidRateSchema getSchema()
	{
		LDMthMidRateSchema aLDMthMidRateSchema = new LDMthMidRateSchema();
		aLDMthMidRateSchema.setSchema(this);
		return aLDMthMidRateSchema;
	}

	public LDMthMidRateDB getDB()
	{
		LDMthMidRateDB aDBOper = new LDMthMidRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMthMidRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CurrCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Per));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Average));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidMonth));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMthMidRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CurrCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Per= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			DestCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Average = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ValidYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ValidMonth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMthMidRateSchema";
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
		if (FCode.equalsIgnoreCase("CurrCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrCode));
		}
		if (FCode.equalsIgnoreCase("Per"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Per));
		}
		if (FCode.equalsIgnoreCase("DestCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestCode));
		}
		if (FCode.equalsIgnoreCase("Average"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Average));
		}
		if (FCode.equalsIgnoreCase("ValidYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidYear));
		}
		if (FCode.equalsIgnoreCase("ValidMonth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidMonth));
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
				strFieldValue = StrTool.GBKToUnicode(CurrCode);
				break;
			case 1:
				strFieldValue = String.valueOf(Per);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DestCode);
				break;
			case 3:
				strFieldValue = String.valueOf(Average);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ValidYear);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ValidMonth);
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

		if (FCode.equalsIgnoreCase("CurrCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrCode = FValue.trim();
			}
			else
				CurrCode = null;
		}
		if (FCode.equalsIgnoreCase("Per"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Per = i;
			}
		}
		if (FCode.equalsIgnoreCase("DestCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestCode = FValue.trim();
			}
			else
				DestCode = null;
		}
		if (FCode.equalsIgnoreCase("Average"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Average = d;
			}
		}
		if (FCode.equalsIgnoreCase("ValidYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidYear = FValue.trim();
			}
			else
				ValidYear = null;
		}
		if (FCode.equalsIgnoreCase("ValidMonth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidMonth = FValue.trim();
			}
			else
				ValidMonth = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMthMidRateSchema other = (LDMthMidRateSchema)otherObject;
		return
			CurrCode.equals(other.getCurrCode())
			&& Per == other.getPer()
			&& DestCode.equals(other.getDestCode())
			&& Average == other.getAverage()
			&& ValidYear.equals(other.getValidYear())
			&& ValidMonth.equals(other.getValidMonth());
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
		if( strFieldName.equals("CurrCode") ) {
			return 0;
		}
		if( strFieldName.equals("Per") ) {
			return 1;
		}
		if( strFieldName.equals("DestCode") ) {
			return 2;
		}
		if( strFieldName.equals("Average") ) {
			return 3;
		}
		if( strFieldName.equals("ValidYear") ) {
			return 4;
		}
		if( strFieldName.equals("ValidMonth") ) {
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
				strFieldName = "CurrCode";
				break;
			case 1:
				strFieldName = "Per";
				break;
			case 2:
				strFieldName = "DestCode";
				break;
			case 3:
				strFieldName = "Average";
				break;
			case 4:
				strFieldName = "ValidYear";
				break;
			case 5:
				strFieldName = "ValidMonth";
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
		if( strFieldName.equals("CurrCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Per") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DestCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Average") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ValidYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidMonth") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
