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
import com.sinosoft.lis.db.LLAccidentSubDB;

/*
 * <p>ClassName: LLAccidentSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLAccidentSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAccidentSubSchema.class);

	// @Field
	/** 事件号 */
	private String AccNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人状态 */
	private String CustomerState;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAccidentSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AccNo";
		pk[1] = "CustomerNo";

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
		LLAccidentSubSchema cloned = (LLAccidentSubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccNo()
	{
		return AccNo;
	}
	public void setAccNo(String aAccNo)
	{
		AccNo = aAccNo;
	}
	/**
	* 该次咨询所关联的客户号码<p>
	* 包括被保险人；投保人；连带被保险人；被保险人的配偶；被保险人的子女；被保险人的父母等等相关连的各种类型的人员。
	*/
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getCustomerState()
	{
		return CustomerState;
	}
	public void setCustomerState(String aCustomerState)
	{
		CustomerState = aCustomerState;
	}

	/**
	* 使用另外一个 LLAccidentSubSchema 对象给 Schema 赋值
	* @param: aLLAccidentSubSchema LLAccidentSubSchema
	**/
	public void setSchema(LLAccidentSubSchema aLLAccidentSubSchema)
	{
		this.AccNo = aLLAccidentSubSchema.getAccNo();
		this.CustomerNo = aLLAccidentSubSchema.getCustomerNo();
		this.CustomerState = aLLAccidentSubSchema.getCustomerState();
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
			if( rs.getString("AccNo") == null )
				this.AccNo = null;
			else
				this.AccNo = rs.getString("AccNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerState") == null )
				this.CustomerState = null;
			else
				this.CustomerState = rs.getString("CustomerState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAccidentSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAccidentSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAccidentSubSchema getSchema()
	{
		LLAccidentSubSchema aLLAccidentSubSchema = new LLAccidentSubSchema();
		aLLAccidentSubSchema.setSchema(this);
		return aLLAccidentSubSchema;
	}

	public LLAccidentSubDB getDB()
	{
		LLAccidentSubDB aDBOper = new LLAccidentSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAccidentSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAccidentSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAccidentSubSchema";
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
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerState));
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
				strFieldValue = StrTool.GBKToUnicode(AccNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustomerState);
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

		if (FCode.equalsIgnoreCase("AccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccNo = FValue.trim();
			}
			else
				AccNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerState = FValue.trim();
			}
			else
				CustomerState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAccidentSubSchema other = (LLAccidentSubSchema)otherObject;
		return
			AccNo.equals(other.getAccNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerState.equals(other.getCustomerState());
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
		if( strFieldName.equals("AccNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("CustomerState") ) {
			return 2;
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
				strFieldName = "AccNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "CustomerState";
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
		if( strFieldName.equals("AccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerState") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
