

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIProLossCalDB;

/*
 * <p>ClassName: RIProLossCalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIProLossCalSchema implements Schema, Cloneable
{
	// @Field
	/** 纯益手续费编码 */
	private String RIProfitNo;
	/** 纯溢手续费比例 */
	private double RIProLoss;
	/** 计算类型 */
	private String ItemCalType;
	/** 固定数字值 */
	private double DoubleValue;
	/** 计算处理类 */
	private String CalClass;
	/** 计算sql算法 */
	private String CalSQL;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIProLossCalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RIProfitNo";

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
		RIProLossCalSchema cloned = (RIProLossCalSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIProfitNo()
	{
		return RIProfitNo;
	}
	public void setRIProfitNo(String aRIProfitNo)
	{
		RIProfitNo = aRIProfitNo;
	}
	public double getRIProLoss()
	{
		return RIProLoss;
	}
	public void setRIProLoss(double aRIProLoss)
	{
		RIProLoss = aRIProLoss;
	}
	public void setRIProLoss(String aRIProLoss)
	{
		if (aRIProLoss != null && !aRIProLoss.equals(""))
		{
			Double tDouble = new Double(aRIProLoss);
			double d = tDouble.doubleValue();
			RIProLoss = d;
		}
	}

	/**
	* 01-固定数值,02-Sql计算,03-类计算
	*/
	public String getItemCalType()
	{
		return ItemCalType;
	}
	public void setItemCalType(String aItemCalType)
	{
		ItemCalType = aItemCalType;
	}
	public double getDoubleValue()
	{
		return DoubleValue;
	}
	public void setDoubleValue(double aDoubleValue)
	{
		DoubleValue = aDoubleValue;
	}
	public void setDoubleValue(String aDoubleValue)
	{
		if (aDoubleValue != null && !aDoubleValue.equals(""))
		{
			Double tDouble = new Double(aDoubleValue);
			double d = tDouble.doubleValue();
			DoubleValue = d;
		}
	}

	public String getCalClass()
	{
		return CalClass;
	}
	public void setCalClass(String aCalClass)
	{
		CalClass = aCalClass;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}

	/**
	* 使用另外一个 RIProLossCalSchema 对象给 Schema 赋值
	* @param: aRIProLossCalSchema RIProLossCalSchema
	**/
	public void setSchema(RIProLossCalSchema aRIProLossCalSchema)
	{
		this.RIProfitNo = aRIProLossCalSchema.getRIProfitNo();
		this.RIProLoss = aRIProLossCalSchema.getRIProLoss();
		this.ItemCalType = aRIProLossCalSchema.getItemCalType();
		this.DoubleValue = aRIProLossCalSchema.getDoubleValue();
		this.CalClass = aRIProLossCalSchema.getCalClass();
		this.CalSQL = aRIProLossCalSchema.getCalSQL();
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
			if( rs.getString("RIProfitNo") == null )
				this.RIProfitNo = null;
			else
				this.RIProfitNo = rs.getString("RIProfitNo").trim();

			this.RIProLoss = rs.getDouble("RIProLoss");
			if( rs.getString("ItemCalType") == null )
				this.ItemCalType = null;
			else
				this.ItemCalType = rs.getString("ItemCalType").trim();

			this.DoubleValue = rs.getDouble("DoubleValue");
			if( rs.getString("CalClass") == null )
				this.CalClass = null;
			else
				this.CalClass = rs.getString("CalClass").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIProLossCal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProLossCalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIProLossCalSchema getSchema()
	{
		RIProLossCalSchema aRIProLossCalSchema = new RIProLossCalSchema();
		aRIProLossCalSchema.setSchema(this);
		return aRIProLossCalSchema;
	}

	public RIProLossCalDB getDB()
	{
		RIProLossCalDB aDBOper = new RIProLossCalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProLossCal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIProfitNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RIProLoss));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DoubleValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProLossCal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIProfitNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIProLoss = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).doubleValue();
			ItemCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DoubleValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			CalClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProLossCalSchema";
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
		if (FCode.equalsIgnoreCase("RIProfitNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitNo));
		}
		if (FCode.equalsIgnoreCase("RIProLoss"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProLoss));
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCalType));
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoubleValue));
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalClass));
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
				strFieldValue = StrTool.GBKToUnicode(RIProfitNo);
				break;
			case 1:
				strFieldValue = String.valueOf(RIProLoss);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ItemCalType);
				break;
			case 3:
				strFieldValue = String.valueOf(DoubleValue);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalClass);
				break;
			case 5:
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

		if (FCode.equalsIgnoreCase("RIProfitNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitNo = FValue.trim();
			}
			else
				RIProfitNo = null;
		}
		if (FCode.equalsIgnoreCase("RIProLoss"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RIProLoss = d;
			}
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCalType = FValue.trim();
			}
			else
				ItemCalType = null;
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DoubleValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalClass = FValue.trim();
			}
			else
				CalClass = null;
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
		RIProLossCalSchema other = (RIProLossCalSchema)otherObject;
		return
			RIProfitNo.equals(other.getRIProfitNo())
			&& RIProLoss == other.getRIProLoss()
			&& ItemCalType.equals(other.getItemCalType())
			&& DoubleValue == other.getDoubleValue()
			&& CalClass.equals(other.getCalClass())
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
		if( strFieldName.equals("RIProfitNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIProLoss") ) {
			return 1;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return 2;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return 3;
		}
		if( strFieldName.equals("CalClass") ) {
			return 4;
		}
		if( strFieldName.equals("CalSQL") ) {
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
				strFieldName = "RIProfitNo";
				break;
			case 1:
				strFieldName = "RIProLoss";
				break;
			case 2:
				strFieldName = "ItemCalType";
				break;
			case 3:
				strFieldName = "DoubleValue";
				break;
			case 4:
				strFieldName = "CalClass";
				break;
			case 5:
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
		if( strFieldName.equals("RIProfitNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIProLoss") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalClass") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
