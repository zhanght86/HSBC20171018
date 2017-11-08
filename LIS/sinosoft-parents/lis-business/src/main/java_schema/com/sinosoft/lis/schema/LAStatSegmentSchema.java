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
import com.sinosoft.lis.db.LAStatSegmentDB;

/*
 * <p>ClassName: LAStatSegmentSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAStatSegmentSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAStatSegmentSchema.class);

	// @Field
	/** 统计类型 */
	private String StatType;
	/** 统计间隔 */
	private int YearMonth;
	/** 起始时间 */
	private Date StartDate;
	/** 截止时间 */
	private Date EndDate;
	/** 扩展时间 */
	private Date ExtDate;
	/** 预留时间 */
	private Date PreDate;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAStatSegmentSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "StatType";
		pk[1] = "YearMonth";

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
		LAStatSegmentSchema cloned = (LAStatSegmentSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 0-非固定间隔统计;1-自然月度统计;2-考核季度统计;3-考核半年度统计;4-考核年度统计 5-承保月度统计;6-回单月度统计；7-扫描日期特殊定义;8-财务月度统计<p>
	* tattype=15:TM受理月定义stattype=16:TM承保月定义
	*/
	public String getStatType()
	{
		return StatType;
	}
	public void setStatType(String aStatType)
	{
		StatType = aStatType;
	}
	/**
	* YYYYMM
	*/
	public int getYearMonth()
	{
		return YearMonth;
	}
	public void setYearMonth(int aYearMonth)
	{
		YearMonth = aYearMonth;
	}
	public void setYearMonth(String aYearMonth)
	{
		if (aYearMonth != null && !aYearMonth.equals(""))
		{
			Integer tInteger = new Integer(aYearMonth);
			int i = tInteger.intValue();
			YearMonth = i;
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

	public String getExtDate()
	{
		if( ExtDate != null )
			return fDate.getString(ExtDate);
		else
			return null;
	}
	public void setExtDate(Date aExtDate)
	{
		ExtDate = aExtDate;
	}
	public void setExtDate(String aExtDate)
	{
		if (aExtDate != null && !aExtDate.equals("") )
		{
			ExtDate = fDate.getDate( aExtDate );
		}
		else
			ExtDate = null;
	}

	public String getPreDate()
	{
		if( PreDate != null )
			return fDate.getString(PreDate);
		else
			return null;
	}
	public void setPreDate(Date aPreDate)
	{
		PreDate = aPreDate;
	}
	public void setPreDate(String aPreDate)
	{
		if (aPreDate != null && !aPreDate.equals("") )
		{
			PreDate = fDate.getDate( aPreDate );
		}
		else
			PreDate = null;
	}


	/**
	* 使用另外一个 LAStatSegmentSchema 对象给 Schema 赋值
	* @param: aLAStatSegmentSchema LAStatSegmentSchema
	**/
	public void setSchema(LAStatSegmentSchema aLAStatSegmentSchema)
	{
		this.StatType = aLAStatSegmentSchema.getStatType();
		this.YearMonth = aLAStatSegmentSchema.getYearMonth();
		this.StartDate = fDate.getDate( aLAStatSegmentSchema.getStartDate());
		this.EndDate = fDate.getDate( aLAStatSegmentSchema.getEndDate());
		this.ExtDate = fDate.getDate( aLAStatSegmentSchema.getExtDate());
		this.PreDate = fDate.getDate( aLAStatSegmentSchema.getPreDate());
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
			if( rs.getString("StatType") == null )
				this.StatType = null;
			else
				this.StatType = rs.getString("StatType").trim();

			this.YearMonth = rs.getInt("YearMonth");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.ExtDate = rs.getDate("ExtDate");
			this.PreDate = rs.getDate("PreDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAStatSegment表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAStatSegmentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAStatSegmentSchema getSchema()
	{
		LAStatSegmentSchema aLAStatSegmentSchema = new LAStatSegmentSchema();
		aLAStatSegmentSchema.setSchema(this);
		return aLAStatSegmentSchema;
	}

	public LAStatSegmentDB getDB()
	{
		LAStatSegmentDB aDBOper = new LAStatSegmentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAStatSegment描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(StatType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(YearMonth));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExtDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PreDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAStatSegment>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			StatType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			YearMonth= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			ExtDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			PreDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAStatSegmentSchema";
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
		if (FCode.equalsIgnoreCase("StatType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatType));
		}
		if (FCode.equalsIgnoreCase("YearMonth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YearMonth));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("ExtDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getExtDate()));
		}
		if (FCode.equalsIgnoreCase("PreDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPreDate()));
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
				strFieldValue = StrTool.GBKToUnicode(StatType);
				break;
			case 1:
				strFieldValue = String.valueOf(YearMonth);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExtDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPreDate()));
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

		if (FCode.equalsIgnoreCase("StatType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StatType = FValue.trim();
			}
			else
				StatType = null;
		}
		if (FCode.equalsIgnoreCase("YearMonth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				YearMonth = i;
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
		if (FCode.equalsIgnoreCase("ExtDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ExtDate = fDate.getDate( FValue );
			}
			else
				ExtDate = null;
		}
		if (FCode.equalsIgnoreCase("PreDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PreDate = fDate.getDate( FValue );
			}
			else
				PreDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAStatSegmentSchema other = (LAStatSegmentSchema)otherObject;
		return
			StatType.equals(other.getStatType())
			&& YearMonth == other.getYearMonth()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(ExtDate).equals(other.getExtDate())
			&& fDate.getString(PreDate).equals(other.getPreDate());
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
		if( strFieldName.equals("StatType") ) {
			return 0;
		}
		if( strFieldName.equals("YearMonth") ) {
			return 1;
		}
		if( strFieldName.equals("StartDate") ) {
			return 2;
		}
		if( strFieldName.equals("EndDate") ) {
			return 3;
		}
		if( strFieldName.equals("ExtDate") ) {
			return 4;
		}
		if( strFieldName.equals("PreDate") ) {
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
				strFieldName = "StatType";
				break;
			case 1:
				strFieldName = "YearMonth";
				break;
			case 2:
				strFieldName = "StartDate";
				break;
			case 3:
				strFieldName = "EndDate";
				break;
			case 4:
				strFieldName = "ExtDate";
				break;
			case 5:
				strFieldName = "PreDate";
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
		if( strFieldName.equals("StatType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YearMonth") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ExtDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PreDate") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
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
