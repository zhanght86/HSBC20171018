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
import com.sinosoft.lis.db.LMEdorNetDB;

/*
 * <p>ClassName: LMEdorNetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LMEdorNetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMEdorNetSchema.class);
	// @Field
	/** Startyear */
	private int StartYear;
	/** Endyear */
	private int EndYear;
	/** Netrate */
	private int NetRate;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMEdorNetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "StartYear";
		pk[1] = "EndYear";

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
		LMEdorNetSchema cloned = (LMEdorNetSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getStartYear()
	{
		return StartYear;
	}
	public void setStartYear(int aStartYear)
	{
		StartYear = aStartYear;
	}
	public void setStartYear(String aStartYear)
	{
		if (aStartYear != null && !aStartYear.equals(""))
		{
			Integer tInteger = new Integer(aStartYear);
			int i = tInteger.intValue();
			StartYear = i;
		}
	}

	public int getEndYear()
	{
		return EndYear;
	}
	public void setEndYear(int aEndYear)
	{
		EndYear = aEndYear;
	}
	public void setEndYear(String aEndYear)
	{
		if (aEndYear != null && !aEndYear.equals(""))
		{
			Integer tInteger = new Integer(aEndYear);
			int i = tInteger.intValue();
			EndYear = i;
		}
	}

	public int getNetRate()
	{
		return NetRate;
	}
	public void setNetRate(int aNetRate)
	{
		NetRate = aNetRate;
	}
	public void setNetRate(String aNetRate)
	{
		if (aNetRate != null && !aNetRate.equals(""))
		{
			Integer tInteger = new Integer(aNetRate);
			int i = tInteger.intValue();
			NetRate = i;
		}
	}


	/**
	* 使用另外一个 LMEdorNetSchema 对象给 Schema 赋值
	* @param: aLMEdorNetSchema LMEdorNetSchema
	**/
	public void setSchema(LMEdorNetSchema aLMEdorNetSchema)
	{
		this.StartYear = aLMEdorNetSchema.getStartYear();
		this.EndYear = aLMEdorNetSchema.getEndYear();
		this.NetRate = aLMEdorNetSchema.getNetRate();
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
			this.StartYear = rs.getInt("StartYear");
			this.EndYear = rs.getInt("EndYear");
			this.NetRate = rs.getInt("NetRate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMEdorNet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorNetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMEdorNetSchema getSchema()
	{
		LMEdorNetSchema aLMEdorNetSchema = new LMEdorNetSchema();
		aLMEdorNetSchema.setSchema(this);
		return aLMEdorNetSchema;
	}

	public LMEdorNetDB getDB()
	{
		LMEdorNetDB aDBOper = new LMEdorNetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorNet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(StartYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorNet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			StartYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			EndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			NetRate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorNetSchema";
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
		if (FCode.equalsIgnoreCase("StartYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartYear));
		}
		if (FCode.equalsIgnoreCase("EndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndYear));
		}
		if (FCode.equalsIgnoreCase("NetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetRate));
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
				strFieldValue = String.valueOf(StartYear);
				break;
			case 1:
				strFieldValue = String.valueOf(EndYear);
				break;
			case 2:
				strFieldValue = String.valueOf(NetRate);
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

		if (FCode.equalsIgnoreCase("StartYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StartYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("EndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("NetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NetRate = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMEdorNetSchema other = (LMEdorNetSchema)otherObject;
		return
			StartYear == other.getStartYear()
			&& EndYear == other.getEndYear()
			&& NetRate == other.getNetRate();
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
		if( strFieldName.equals("StartYear") ) {
			return 0;
		}
		if( strFieldName.equals("EndYear") ) {
			return 1;
		}
		if( strFieldName.equals("NetRate") ) {
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
				strFieldName = "StartYear";
				break;
			case 1:
				strFieldName = "EndYear";
				break;
			case 2:
				strFieldName = "NetRate";
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
		if( strFieldName.equals("StartYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NetRate") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_INT;
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
