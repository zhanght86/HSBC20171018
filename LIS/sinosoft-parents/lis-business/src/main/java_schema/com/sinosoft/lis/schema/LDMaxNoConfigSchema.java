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
import com.sinosoft.lis.db.LDMaxNoConfigDB;

/*
 * <p>ClassName: LDMaxNoConfigSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 号段管理
 */
public class LDMaxNoConfigSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMaxNoConfigSchema.class);
	// @Field
	/** 号段编码 */
	private String NoCode;
	/** 号段名称 */
	private String NoName;
	/** 规则预览 */
	private String ShowRule;
	/** 起期 */
	private Date StartDate;
	/** 止期 */
	private Date EndDate;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMaxNoConfigSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "NoCode";
		pk[1] = "StartDate";

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
		LDMaxNoConfigSchema cloned = (LDMaxNoConfigSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getNoCode()
	{
		return NoCode;
	}
	public void setNoCode(String aNoCode)
	{
		if(aNoCode!=null && aNoCode.length()>100)
			throw new IllegalArgumentException("号段编码NoCode值"+aNoCode+"的长度"+aNoCode.length()+"大于最大值100");
		NoCode = aNoCode;
	}
	public String getNoName()
	{
		return NoName;
	}
	public void setNoName(String aNoName)
	{
		if(aNoName!=null && aNoName.length()>100)
			throw new IllegalArgumentException("号段名称NoName值"+aNoName+"的长度"+aNoName.length()+"大于最大值100");
		NoName = aNoName;
	}
	public String getShowRule()
	{
		return ShowRule;
	}
	public void setShowRule(String aShowRule)
	{
		if(aShowRule!=null && aShowRule.length()>4000)
			throw new IllegalArgumentException("规则预览ShowRule值"+aShowRule+"的长度"+aShowRule.length()+"大于最大值4000");
		ShowRule = aShowRule;
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
	* 使用另外一个 LDMaxNoConfigSchema 对象给 Schema 赋值
	* @param: aLDMaxNoConfigSchema LDMaxNoConfigSchema
	**/
	public void setSchema(LDMaxNoConfigSchema aLDMaxNoConfigSchema)
	{
		this.NoCode = aLDMaxNoConfigSchema.getNoCode();
		this.NoName = aLDMaxNoConfigSchema.getNoName();
		this.ShowRule = aLDMaxNoConfigSchema.getShowRule();
		this.StartDate = fDate.getDate( aLDMaxNoConfigSchema.getStartDate());
		this.EndDate = fDate.getDate( aLDMaxNoConfigSchema.getEndDate());
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
			if( rs.getString("NoCode") == null )
				this.NoCode = null;
			else
				this.NoCode = rs.getString("NoCode").trim();

			if( rs.getString("NoName") == null )
				this.NoName = null;
			else
				this.NoName = rs.getString("NoName").trim();

			if( rs.getString("ShowRule") == null )
				this.ShowRule = null;
			else
				this.ShowRule = rs.getString("ShowRule").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMaxNoConfig表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoConfigSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMaxNoConfigSchema getSchema()
	{
		LDMaxNoConfigSchema aLDMaxNoConfigSchema = new LDMaxNoConfigSchema();
		aLDMaxNoConfigSchema.setSchema(this);
		return aLDMaxNoConfigSchema;
	}

	public LDMaxNoConfigDB getDB()
	{
		LDMaxNoConfigDB aDBOper = new LDMaxNoConfigDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNoConfig描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(NoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowRule)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNoConfig>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NoName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ShowRule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoConfigSchema";
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
		if (FCode.equalsIgnoreCase("NoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoCode));
		}
		if (FCode.equalsIgnoreCase("NoName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoName));
		}
		if (FCode.equalsIgnoreCase("ShowRule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowRule));
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
				strFieldValue = StrTool.GBKToUnicode(NoCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(NoName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ShowRule);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 4:
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

		if (FCode.equalsIgnoreCase("NoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoCode = FValue.trim();
			}
			else
				NoCode = null;
		}
		if (FCode.equalsIgnoreCase("NoName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoName = FValue.trim();
			}
			else
				NoName = null;
		}
		if (FCode.equalsIgnoreCase("ShowRule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowRule = FValue.trim();
			}
			else
				ShowRule = null;
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
		LDMaxNoConfigSchema other = (LDMaxNoConfigSchema)otherObject;
		return
			NoCode.equals(other.getNoCode())
			&& NoName.equals(other.getNoName())
			&& ShowRule.equals(other.getShowRule())
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
		if( strFieldName.equals("NoCode") ) {
			return 0;
		}
		if( strFieldName.equals("NoName") ) {
			return 1;
		}
		if( strFieldName.equals("ShowRule") ) {
			return 2;
		}
		if( strFieldName.equals("StartDate") ) {
			return 3;
		}
		if( strFieldName.equals("EndDate") ) {
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
				strFieldName = "NoCode";
				break;
			case 1:
				strFieldName = "NoName";
				break;
			case 2:
				strFieldName = "ShowRule";
				break;
			case 3:
				strFieldName = "StartDate";
				break;
			case 4:
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
		if( strFieldName.equals("NoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowRule") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
