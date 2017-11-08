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
import com.sinosoft.lis.db.LBPODataCheckDB;

/*
 * <p>ClassName: LBPODataCheckSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPODataCheckSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPODataCheckSchema.class);

	// @Field
	/** 业务号码类型 */
	private String BussNoType;
	/** 校验顺序 */
	private String CheckOrder;
	/** 校验编码 */
	private String CheckCode;
	/** 校验算法 */
	private String CalSql;
	/** 说明 */
	private String Noti;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPODataCheckSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BussNoType";
		pk[1] = "CheckOrder";
		pk[2] = "CheckCode";

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
		LBPODataCheckSchema cloned = (LBPODataCheckSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 对业务号码进行分类，如TB-新契约；LP-理赔；BQ-保全
	*/
	public String getBussNoType()
	{
		return BussNoType;
	}
	public void setBussNoType(String aBussNoType)
	{
		BussNoType = aBussNoType;
	}
	public String getCheckOrder()
	{
		return CheckOrder;
	}
	public void setCheckOrder(String aCheckOrder)
	{
		CheckOrder = aCheckOrder;
	}
	public String getCheckCode()
	{
		return CheckCode;
	}
	public void setCheckCode(String aCheckCode)
	{
		CheckCode = aCheckCode;
	}
	public String getCalSql()
	{
		return CalSql;
	}
	public void setCalSql(String aCalSql)
	{
		CalSql = aCalSql;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
	}

	/**
	* 使用另外一个 LBPODataCheckSchema 对象给 Schema 赋值
	* @param: aLBPODataCheckSchema LBPODataCheckSchema
	**/
	public void setSchema(LBPODataCheckSchema aLBPODataCheckSchema)
	{
		this.BussNoType = aLBPODataCheckSchema.getBussNoType();
		this.CheckOrder = aLBPODataCheckSchema.getCheckOrder();
		this.CheckCode = aLBPODataCheckSchema.getCheckCode();
		this.CalSql = aLBPODataCheckSchema.getCalSql();
		this.Noti = aLBPODataCheckSchema.getNoti();
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
			if( rs.getString("BussNoType") == null )
				this.BussNoType = null;
			else
				this.BussNoType = rs.getString("BussNoType").trim();

			if( rs.getString("CheckOrder") == null )
				this.CheckOrder = null;
			else
				this.CheckOrder = rs.getString("CheckOrder").trim();

			if( rs.getString("CheckCode") == null )
				this.CheckCode = null;
			else
				this.CheckCode = rs.getString("CheckCode").trim();

			if( rs.getString("CalSql") == null )
				this.CalSql = null;
			else
				this.CalSql = rs.getString("CalSql").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPODataCheck表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPODataCheckSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPODataCheckSchema getSchema()
	{
		LBPODataCheckSchema aLBPODataCheckSchema = new LBPODataCheckSchema();
		aLBPODataCheckSchema.setSchema(this);
		return aLBPODataCheckSchema;
	}

	public LBPODataCheckDB getDB()
	{
		LBPODataCheckDB aDBOper = new LBPODataCheckDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPODataCheck描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckOrder)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSql)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPODataCheck>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CheckOrder = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CheckCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPODataCheckSchema";
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
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNoType));
		}
		if (FCode.equalsIgnoreCase("CheckOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckOrder));
		}
		if (FCode.equalsIgnoreCase("CheckCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckCode));
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSql));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
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
				strFieldValue = StrTool.GBKToUnicode(BussNoType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CheckOrder);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CheckCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalSql);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Noti);
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

		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNoType = FValue.trim();
			}
			else
				BussNoType = null;
		}
		if (FCode.equalsIgnoreCase("CheckOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckOrder = FValue.trim();
			}
			else
				CheckOrder = null;
		}
		if (FCode.equalsIgnoreCase("CheckCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckCode = FValue.trim();
			}
			else
				CheckCode = null;
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSql = FValue.trim();
			}
			else
				CalSql = null;
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPODataCheckSchema other = (LBPODataCheckSchema)otherObject;
		return
			BussNoType.equals(other.getBussNoType())
			&& CheckOrder.equals(other.getCheckOrder())
			&& CheckCode.equals(other.getCheckCode())
			&& CalSql.equals(other.getCalSql())
			&& Noti.equals(other.getNoti());
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
		if( strFieldName.equals("BussNoType") ) {
			return 0;
		}
		if( strFieldName.equals("CheckOrder") ) {
			return 1;
		}
		if( strFieldName.equals("CheckCode") ) {
			return 2;
		}
		if( strFieldName.equals("CalSql") ) {
			return 3;
		}
		if( strFieldName.equals("Noti") ) {
			return 4;
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
				strFieldName = "BussNoType";
				break;
			case 1:
				strFieldName = "CheckOrder";
				break;
			case 2:
				strFieldName = "CheckCode";
				break;
			case 3:
				strFieldName = "CalSql";
				break;
			case 4:
				strFieldName = "Noti";
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
		if( strFieldName.equals("BussNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckOrder") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
