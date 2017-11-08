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
import com.sinosoft.lis.db.LLAskRelaDB;

/*
 * <p>ClassName: LLAskRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLAskRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAskRelaSchema.class);
	// @Field
	/** 分报案号(事件号) */
	private String SubRptNo;
	/** 咨询号码 */
	private String ConsultNo;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAskRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SubRptNo";
		pk[1] = "ConsultNo";

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
		LLAskRelaSchema cloned = (LLAskRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSubRptNo()
	{
		return SubRptNo;
	}
	public void setSubRptNo(String aSubRptNo)
	{
		if(aSubRptNo!=null && aSubRptNo.length()>20)
			throw new IllegalArgumentException("分报案号(事件号)SubRptNo值"+aSubRptNo+"的长度"+aSubRptNo.length()+"大于最大值20");
		SubRptNo = aSubRptNo;
	}
	public String getConsultNo()
	{
		return ConsultNo;
	}
	public void setConsultNo(String aConsultNo)
	{
		if(aConsultNo!=null && aConsultNo.length()>20)
			throw new IllegalArgumentException("咨询号码ConsultNo值"+aConsultNo+"的长度"+aConsultNo.length()+"大于最大值20");
		ConsultNo = aConsultNo;
	}

	/**
	* 使用另外一个 LLAskRelaSchema 对象给 Schema 赋值
	* @param: aLLAskRelaSchema LLAskRelaSchema
	**/
	public void setSchema(LLAskRelaSchema aLLAskRelaSchema)
	{
		this.SubRptNo = aLLAskRelaSchema.getSubRptNo();
		this.ConsultNo = aLLAskRelaSchema.getConsultNo();
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
			if( rs.getString("SubRptNo") == null )
				this.SubRptNo = null;
			else
				this.SubRptNo = rs.getString("SubRptNo").trim();

			if( rs.getString("ConsultNo") == null )
				this.ConsultNo = null;
			else
				this.ConsultNo = rs.getString("ConsultNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAskRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAskRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAskRelaSchema getSchema()
	{
		LLAskRelaSchema aLLAskRelaSchema = new LLAskRelaSchema();
		aLLAskRelaSchema.setSchema(this);
		return aLLAskRelaSchema;
	}

	public LLAskRelaDB getDB()
	{
		LLAskRelaDB aDBOper = new LLAskRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAskRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubRptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConsultNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAskRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubRptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ConsultNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAskRelaSchema";
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
		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRptNo));
		}
		if (FCode.equalsIgnoreCase("ConsultNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConsultNo));
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
				strFieldValue = StrTool.GBKToUnicode(SubRptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ConsultNo);
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

		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRptNo = FValue.trim();
			}
			else
				SubRptNo = null;
		}
		if (FCode.equalsIgnoreCase("ConsultNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConsultNo = FValue.trim();
			}
			else
				ConsultNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAskRelaSchema other = (LLAskRelaSchema)otherObject;
		return
			SubRptNo.equals(other.getSubRptNo())
			&& ConsultNo.equals(other.getConsultNo());
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
		if( strFieldName.equals("SubRptNo") ) {
			return 0;
		}
		if( strFieldName.equals("ConsultNo") ) {
			return 1;
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
				strFieldName = "SubRptNo";
				break;
			case 1:
				strFieldName = "ConsultNo";
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
		if( strFieldName.equals("SubRptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConsultNo") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
