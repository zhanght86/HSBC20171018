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
import com.sinosoft.lis.db.LDMaxNoDB;

/*
 * <p>ClassName: LDMaxNoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDMaxNoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMaxNoSchema.class);

	// @Field
	/** 号码类型 */
	private String NoType;
	/** 号码限制条件 */
	private String NoLimit;
	/** 当前最大值 */
	private int MaxNo;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMaxNoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "NoType";
		pk[1] = "NoLimit";

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
		LDMaxNoSchema cloned = (LDMaxNoSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getNoType()
	{
		return NoType;
	}
	public void setNoType(String aNoType)
	{
		NoType = aNoType;
	}
	public String getNoLimit()
	{
		return NoLimit;
	}
	public void setNoLimit(String aNoLimit)
	{
		NoLimit = aNoLimit;
	}
	public int getMaxNo()
	{
		return MaxNo;
	}
	public void setMaxNo(int aMaxNo)
	{
		MaxNo = aMaxNo;
	}
	public void setMaxNo(String aMaxNo)
	{
		if (aMaxNo != null && !aMaxNo.equals(""))
		{
			Integer tInteger = new Integer(aMaxNo);
			int i = tInteger.intValue();
			MaxNo = i;
		}
	}


	/**
	* 使用另外一个 LDMaxNoSchema 对象给 Schema 赋值
	* @param: aLDMaxNoSchema LDMaxNoSchema
	**/
	public void setSchema(LDMaxNoSchema aLDMaxNoSchema)
	{
		this.NoType = aLDMaxNoSchema.getNoType();
		this.NoLimit = aLDMaxNoSchema.getNoLimit();
		this.MaxNo = aLDMaxNoSchema.getMaxNo();
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
			if( rs.getString("NoType") == null )
				this.NoType = null;
			else
				this.NoType = rs.getString("NoType").trim();

			if( rs.getString("NoLimit") == null )
				this.NoLimit = null;
			else
				this.NoLimit = rs.getString("NoLimit").trim();

			this.MaxNo = rs.getInt("MaxNo");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMaxNo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMaxNoSchema getSchema()
	{
		LDMaxNoSchema aLDMaxNoSchema = new LDMaxNoSchema();
		aLDMaxNoSchema.setSchema(this);
		return aLDMaxNoSchema;
	}

	public LDMaxNoDB getDB()
	{
		LDMaxNoDB aDBOper = new LDMaxNoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(NoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoLimit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NoLimit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MaxNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoSchema";
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
		if (FCode.equalsIgnoreCase("NoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoType));
		}
		if (FCode.equalsIgnoreCase("NoLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoLimit));
		}
		if (FCode.equalsIgnoreCase("MaxNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxNo));
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
				strFieldValue = StrTool.GBKToUnicode(NoType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(NoLimit);
				break;
			case 2:
				strFieldValue = String.valueOf(MaxNo);
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

		if (FCode.equalsIgnoreCase("NoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoType = FValue.trim();
			}
			else
				NoType = null;
		}
		if (FCode.equalsIgnoreCase("NoLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoLimit = FValue.trim();
			}
			else
				NoLimit = null;
		}
		if (FCode.equalsIgnoreCase("MaxNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMaxNoSchema other = (LDMaxNoSchema)otherObject;
		return
			NoType.equals(other.getNoType())
			&& NoLimit.equals(other.getNoLimit())
			&& MaxNo == other.getMaxNo();
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
		if( strFieldName.equals("NoType") ) {
			return 0;
		}
		if( strFieldName.equals("NoLimit") ) {
			return 1;
		}
		if( strFieldName.equals("MaxNo") ) {
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
				strFieldName = "NoType";
				break;
			case 1:
				strFieldName = "NoLimit";
				break;
			case 2:
				strFieldName = "MaxNo";
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
		if( strFieldName.equals("NoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoLimit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxNo") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
