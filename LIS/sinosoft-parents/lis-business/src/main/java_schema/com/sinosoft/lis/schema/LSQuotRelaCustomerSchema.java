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
import com.sinosoft.lis.db.LSQuotRelaCustomerDB;

/*
 * <p>ClassName: LSQuotRelaCustomerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotRelaCustomerSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotRelaCustomerSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 准客户号码 */
	private String PreCustomerNo;
	/** 准客户名称 */
	private String PreCustomerName;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotRelaCustomerSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";
		pk[2] = "PreCustomerNo";

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
		LSQuotRelaCustomerSchema cloned = (LSQuotRelaCustomerSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuotNo()
	{
		return QuotNo;
	}
	public void setQuotNo(String aQuotNo)
	{
		if(aQuotNo!=null && aQuotNo.length()>20)
			throw new IllegalArgumentException("询价号QuotNo值"+aQuotNo+"的长度"+aQuotNo.length()+"大于最大值20");
		QuotNo = aQuotNo;
	}
	public int getQuotBatNo()
	{
		return QuotBatNo;
	}
	public void setQuotBatNo(int aQuotBatNo)
	{
		QuotBatNo = aQuotBatNo;
	}
	public void setQuotBatNo(String aQuotBatNo)
	{
		if (aQuotBatNo != null && !aQuotBatNo.equals(""))
		{
			Integer tInteger = new Integer(aQuotBatNo);
			int i = tInteger.intValue();
			QuotBatNo = i;
		}
	}

	public String getPreCustomerNo()
	{
		return PreCustomerNo;
	}
	public void setPreCustomerNo(String aPreCustomerNo)
	{
		if(aPreCustomerNo!=null && aPreCustomerNo.length()>10)
			throw new IllegalArgumentException("准客户号码PreCustomerNo值"+aPreCustomerNo+"的长度"+aPreCustomerNo.length()+"大于最大值10");
		PreCustomerNo = aPreCustomerNo;
	}
	public String getPreCustomerName()
	{
		return PreCustomerName;
	}
	public void setPreCustomerName(String aPreCustomerName)
	{
		if(aPreCustomerName!=null && aPreCustomerName.length()>120)
			throw new IllegalArgumentException("准客户名称PreCustomerName值"+aPreCustomerName+"的长度"+aPreCustomerName.length()+"大于最大值120");
		PreCustomerName = aPreCustomerName;
	}

	/**
	* 使用另外一个 LSQuotRelaCustomerSchema 对象给 Schema 赋值
	* @param: aLSQuotRelaCustomerSchema LSQuotRelaCustomerSchema
	**/
	public void setSchema(LSQuotRelaCustomerSchema aLSQuotRelaCustomerSchema)
	{
		this.QuotNo = aLSQuotRelaCustomerSchema.getQuotNo();
		this.QuotBatNo = aLSQuotRelaCustomerSchema.getQuotBatNo();
		this.PreCustomerNo = aLSQuotRelaCustomerSchema.getPreCustomerNo();
		this.PreCustomerName = aLSQuotRelaCustomerSchema.getPreCustomerName();
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
			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("PreCustomerNo") == null )
				this.PreCustomerNo = null;
			else
				this.PreCustomerNo = rs.getString("PreCustomerNo").trim();

			if( rs.getString("PreCustomerName") == null )
				this.PreCustomerName = null;
			else
				this.PreCustomerName = rs.getString("PreCustomerName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LSQuotRelaCustomer表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotRelaCustomerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotRelaCustomerSchema getSchema()
	{
		LSQuotRelaCustomerSchema aLSQuotRelaCustomerSchema = new LSQuotRelaCustomerSchema();
		aLSQuotRelaCustomerSchema.setSchema(this);
		return aLSQuotRelaCustomerSchema;
	}

	public LSQuotRelaCustomerDB getDB()
	{
		LSQuotRelaCustomerDB aDBOper = new LSQuotRelaCustomerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotRelaCustomer描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotRelaCustomer>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PreCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PreCustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotRelaCustomerSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerNo));
		}
		if (FCode.equalsIgnoreCase("PreCustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerName));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 1:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerName);
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

		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotNo = FValue.trim();
			}
			else
				QuotNo = null;
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				QuotBatNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerNo = FValue.trim();
			}
			else
				PreCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("PreCustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerName = FValue.trim();
			}
			else
				PreCustomerName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LSQuotRelaCustomerSchema other = (LSQuotRelaCustomerSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& PreCustomerNo.equals(other.getPreCustomerNo())
			&& PreCustomerName.equals(other.getPreCustomerName());
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
		if( strFieldName.equals("QuotNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("PreCustomerName") ) {
			return 3;
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
				strFieldName = "QuotNo";
				break;
			case 1:
				strFieldName = "QuotBatNo";
				break;
			case 2:
				strFieldName = "PreCustomerNo";
				break;
			case 3:
				strFieldName = "PreCustomerName";
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreCustomerName") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
