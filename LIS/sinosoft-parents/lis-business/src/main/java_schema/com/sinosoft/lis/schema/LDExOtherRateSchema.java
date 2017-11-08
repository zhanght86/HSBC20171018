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
import com.sinosoft.lis.db.LDExOtherRateDB;

/*
 * <p>ClassName: LDExOtherRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造基础表
 */
public class LDExOtherRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDExOtherRateSchema.class);

	// @Field
	/** 外汇币种代码 */
	private String CurrCode;
	/** 外币数额单位 */
	private int Per;
	/** 目标币种代码 */
	private String DestCurrCode;
	/** 对美元的折算率 */
	private double ExchRate;
	/** 启用日期 */
	private Date StartDate;
	/** 停用日期 */
	private Date EndDate;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDExOtherRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "CurrCode";
		pk[1] = "Per";
		pk[2] = "DestCurrCode";
		pk[3] = "StartDate";

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
		LDExOtherRateSchema cloned = (LDExOtherRateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
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

	public String getDestCurrCode()
	{
		return DestCurrCode;
	}
	public void setDestCurrCode(String aDestCurrCode)
	{
		DestCurrCode = aDestCurrCode;
	}
	public double getExchRate()
	{
		return ExchRate;
	}
	public void setExchRate(double aExchRate)
	{
		ExchRate = aExchRate;
	}
	public void setExchRate(String aExchRate)
	{
		if (aExchRate != null && !aExchRate.equals(""))
		{
			Double tDouble = new Double(aExchRate);
			double d = tDouble.doubleValue();
			ExchRate = d;
		}
	}

	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}


	/**
	* 使用另外一个 LDExOtherRateSchema 对象给 Schema 赋值
	* @param: aLDExOtherRateSchema LDExOtherRateSchema
	**/
	public void setSchema(LDExOtherRateSchema aLDExOtherRateSchema)
	{
		this.CurrCode = aLDExOtherRateSchema.getCurrCode();
		this.Per = aLDExOtherRateSchema.getPer();
		this.DestCurrCode = aLDExOtherRateSchema.getDestCurrCode();
		this.ExchRate = aLDExOtherRateSchema.getExchRate();
		this.StartDate = fDate.getDate( aLDExOtherRateSchema.getStartDate());
		this.EndDate = fDate.getDate( aLDExOtherRateSchema.getEndDate());
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
			if( rs.getString("DestCurrCode") == null )
				this.DestCurrCode = null;
			else
				this.DestCurrCode = rs.getString("DestCurrCode").trim();

			this.ExchRate = rs.getDouble("ExchRate");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDExOtherRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDExOtherRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDExOtherRateSchema getSchema()
	{
		LDExOtherRateSchema aLDExOtherRateSchema = new LDExOtherRateSchema();
		aLDExOtherRateSchema.setSchema(this);
		return aLDExOtherRateSchema;
	}

	public LDExOtherRateDB getDB()
	{
		LDExOtherRateDB aDBOper = new LDExOtherRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDExOtherRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CurrCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Per));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestCurrCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExchRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDExOtherRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CurrCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Per= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			DestCurrCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ExchRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDExOtherRateSchema";
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
		if (FCode.equalsIgnoreCase("DestCurrCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestCurrCode));
		}
		if (FCode.equalsIgnoreCase("ExchRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExchRate));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(DestCurrCode);
				break;
			case 3:
				strFieldValue = String.valueOf(ExchRate);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
		if (FCode.equalsIgnoreCase("DestCurrCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestCurrCode = FValue.trim();
			}
			else
				DestCurrCode = null;
		}
		if (FCode.equalsIgnoreCase("ExchRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExchRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDExOtherRateSchema other = (LDExOtherRateSchema)otherObject;
		return
			CurrCode.equals(other.getCurrCode())
			&& Per == other.getPer()
			&& DestCurrCode.equals(other.getDestCurrCode())
			&& ExchRate == other.getExchRate()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate());
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
		if( strFieldName.equals("DestCurrCode") ) {
			return 2;
		}
		if( strFieldName.equals("ExchRate") ) {
			return 3;
		}
		if( strFieldName.equals("StartDate") ) {
			return 4;
		}
		if( strFieldName.equals("EndDate") ) {
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
				strFieldName = "DestCurrCode";
				break;
			case 3:
				strFieldName = "ExchRate";
				break;
			case 4:
				strFieldName = "StartDate";
				break;
			case 5:
				strFieldName = "EndDate";
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
		if( strFieldName.equals("DestCurrCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExchRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
