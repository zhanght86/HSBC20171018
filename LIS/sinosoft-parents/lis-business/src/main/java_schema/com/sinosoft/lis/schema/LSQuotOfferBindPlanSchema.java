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
import com.sinosoft.lis.db.LSQuotOfferBindPlanDB;

/*
 * <p>ClassName: LSQuotOfferBindPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotOfferBindPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotOfferBindPlanSchema.class);
	// @Field
	/** 报价单号 */
	private String OfferListNo;
	/** 报价系统方案编码 */
	private String QSysPlanCode;
	/** 报价方案编码 */
	private String QPlanCode;
	/** 询价系统方案编码 */
	private String RSysPlanCode;
	/** 询价方案编码 */
	private String RPlanCode;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotOfferBindPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "OfferListNo";
		pk[1] = "QSysPlanCode";
		pk[2] = "QPlanCode";
		pk[3] = "RSysPlanCode";
		pk[4] = "RPlanCode";

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
		LSQuotOfferBindPlanSchema cloned = (LSQuotOfferBindPlanSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getOfferListNo()
	{
		return OfferListNo;
	}
	public void setOfferListNo(String aOfferListNo)
	{
		if(aOfferListNo!=null && aOfferListNo.length()>20)
			throw new IllegalArgumentException("报价单号OfferListNo值"+aOfferListNo+"的长度"+aOfferListNo.length()+"大于最大值20");
		OfferListNo = aOfferListNo;
	}
	public String getQSysPlanCode()
	{
		return QSysPlanCode;
	}
	public void setQSysPlanCode(String aQSysPlanCode)
	{
		if(aQSysPlanCode!=null && aQSysPlanCode.length()>10)
			throw new IllegalArgumentException("报价系统方案编码QSysPlanCode值"+aQSysPlanCode+"的长度"+aQSysPlanCode.length()+"大于最大值10");
		QSysPlanCode = aQSysPlanCode;
	}
	public String getQPlanCode()
	{
		return QPlanCode;
	}
	public void setQPlanCode(String aQPlanCode)
	{
		if(aQPlanCode!=null && aQPlanCode.length()>10)
			throw new IllegalArgumentException("报价方案编码QPlanCode值"+aQPlanCode+"的长度"+aQPlanCode.length()+"大于最大值10");
		QPlanCode = aQPlanCode;
	}
	public String getRSysPlanCode()
	{
		return RSysPlanCode;
	}
	public void setRSysPlanCode(String aRSysPlanCode)
	{
		if(aRSysPlanCode!=null && aRSysPlanCode.length()>10)
			throw new IllegalArgumentException("询价系统方案编码RSysPlanCode值"+aRSysPlanCode+"的长度"+aRSysPlanCode.length()+"大于最大值10");
		RSysPlanCode = aRSysPlanCode;
	}
	public String getRPlanCode()
	{
		return RPlanCode;
	}
	public void setRPlanCode(String aRPlanCode)
	{
		if(aRPlanCode!=null && aRPlanCode.length()>10)
			throw new IllegalArgumentException("询价方案编码RPlanCode值"+aRPlanCode+"的长度"+aRPlanCode.length()+"大于最大值10");
		RPlanCode = aRPlanCode;
	}

	/**
	* 使用另外一个 LSQuotOfferBindPlanSchema 对象给 Schema 赋值
	* @param: aLSQuotOfferBindPlanSchema LSQuotOfferBindPlanSchema
	**/
	public void setSchema(LSQuotOfferBindPlanSchema aLSQuotOfferBindPlanSchema)
	{
		this.OfferListNo = aLSQuotOfferBindPlanSchema.getOfferListNo();
		this.QSysPlanCode = aLSQuotOfferBindPlanSchema.getQSysPlanCode();
		this.QPlanCode = aLSQuotOfferBindPlanSchema.getQPlanCode();
		this.RSysPlanCode = aLSQuotOfferBindPlanSchema.getRSysPlanCode();
		this.RPlanCode = aLSQuotOfferBindPlanSchema.getRPlanCode();
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
			if( rs.getString("OfferListNo") == null )
				this.OfferListNo = null;
			else
				this.OfferListNo = rs.getString("OfferListNo").trim();

			if( rs.getString("QSysPlanCode") == null )
				this.QSysPlanCode = null;
			else
				this.QSysPlanCode = rs.getString("QSysPlanCode").trim();

			if( rs.getString("QPlanCode") == null )
				this.QPlanCode = null;
			else
				this.QPlanCode = rs.getString("QPlanCode").trim();

			if( rs.getString("RSysPlanCode") == null )
				this.RSysPlanCode = null;
			else
				this.RSysPlanCode = rs.getString("RSysPlanCode").trim();

			if( rs.getString("RPlanCode") == null )
				this.RPlanCode = null;
			else
				this.RPlanCode = rs.getString("RPlanCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LSQuotOfferBindPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotOfferBindPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotOfferBindPlanSchema getSchema()
	{
		LSQuotOfferBindPlanSchema aLSQuotOfferBindPlanSchema = new LSQuotOfferBindPlanSchema();
		aLSQuotOfferBindPlanSchema.setSchema(this);
		return aLSQuotOfferBindPlanSchema;
	}

	public LSQuotOfferBindPlanDB getDB()
	{
		LSQuotOfferBindPlanDB aDBOper = new LSQuotOfferBindPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotOfferBindPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OfferListNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QSysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RSysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RPlanCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotOfferBindPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OfferListNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QSysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			QPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RSysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotOfferBindPlanSchema";
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
		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferListNo));
		}
		if (FCode.equalsIgnoreCase("QSysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QSysPlanCode));
		}
		if (FCode.equalsIgnoreCase("QPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QPlanCode));
		}
		if (FCode.equalsIgnoreCase("RSysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RSysPlanCode));
		}
		if (FCode.equalsIgnoreCase("RPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RPlanCode));
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
				strFieldValue = StrTool.GBKToUnicode(OfferListNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(QSysPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(QPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RSysPlanCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RPlanCode);
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

		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OfferListNo = FValue.trim();
			}
			else
				OfferListNo = null;
		}
		if (FCode.equalsIgnoreCase("QSysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QSysPlanCode = FValue.trim();
			}
			else
				QSysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("QPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QPlanCode = FValue.trim();
			}
			else
				QPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("RSysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RSysPlanCode = FValue.trim();
			}
			else
				RSysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("RPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RPlanCode = FValue.trim();
			}
			else
				RPlanCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LSQuotOfferBindPlanSchema other = (LSQuotOfferBindPlanSchema)otherObject;
		return
			OfferListNo.equals(other.getOfferListNo())
			&& QSysPlanCode.equals(other.getQSysPlanCode())
			&& QPlanCode.equals(other.getQPlanCode())
			&& RSysPlanCode.equals(other.getRSysPlanCode())
			&& RPlanCode.equals(other.getRPlanCode());
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
		if( strFieldName.equals("OfferListNo") ) {
			return 0;
		}
		if( strFieldName.equals("QSysPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("QPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("RSysPlanCode") ) {
			return 3;
		}
		if( strFieldName.equals("RPlanCode") ) {
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
				strFieldName = "OfferListNo";
				break;
			case 1:
				strFieldName = "QSysPlanCode";
				break;
			case 2:
				strFieldName = "QPlanCode";
				break;
			case 3:
				strFieldName = "RSysPlanCode";
				break;
			case 4:
				strFieldName = "RPlanCode";
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
		if( strFieldName.equals("OfferListNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QSysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RSysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RPlanCode") ) {
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
