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
import com.sinosoft.lis.db.LDCalendarTraceDB;

/*
 * <p>ClassName: LDCalendarTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDCalendarTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCalendarTraceSchema.class);

	// @Field
	/** 日期 */
	private Date CommonDate;
	/** 序号 */
	private String OrderNo;
	/** 修改前工作日标志 */
	private String LastFlag;
	/** 修改后工作日标志 */
	private String ThisFlag;
	/** 操作员 */
	private String Operator;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCalendarTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CommonDate";
		pk[1] = "OrderNo";

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
		LDCalendarTraceSchema cloned = (LDCalendarTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCommonDate()
	{
		if( CommonDate != null )
			return fDate.getString(CommonDate);
		else
			return null;
	}
	public void setCommonDate(Date aCommonDate)
	{
		CommonDate = aCommonDate;
	}
	public void setCommonDate(String aCommonDate)
	{
		if (aCommonDate != null && !aCommonDate.equals("") )
		{
			CommonDate = fDate.getDate( aCommonDate );
		}
		else
			CommonDate = null;
	}

	public String getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	/**
	* Y-工作日，N-非工作日
	*/
	public String getLastFlag()
	{
		return LastFlag;
	}
	public void setLastFlag(String aLastFlag)
	{
		LastFlag = aLastFlag;
	}
	/**
	* Y-工作日，N-非工作日
	*/
	public String getThisFlag()
	{
		return ThisFlag;
	}
	public void setThisFlag(String aThisFlag)
	{
		ThisFlag = aThisFlag;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDCalendarTraceSchema 对象给 Schema 赋值
	* @param: aLDCalendarTraceSchema LDCalendarTraceSchema
	**/
	public void setSchema(LDCalendarTraceSchema aLDCalendarTraceSchema)
	{
		this.CommonDate = fDate.getDate( aLDCalendarTraceSchema.getCommonDate());
		this.OrderNo = aLDCalendarTraceSchema.getOrderNo();
		this.LastFlag = aLDCalendarTraceSchema.getLastFlag();
		this.ThisFlag = aLDCalendarTraceSchema.getThisFlag();
		this.Operator = aLDCalendarTraceSchema.getOperator();
		this.ModifyDate = fDate.getDate( aLDCalendarTraceSchema.getModifyDate());
		this.ModifyTime = aLDCalendarTraceSchema.getModifyTime();
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
			this.CommonDate = rs.getDate("CommonDate");
			if( rs.getString("OrderNo") == null )
				this.OrderNo = null;
			else
				this.OrderNo = rs.getString("OrderNo").trim();

			if( rs.getString("LastFlag") == null )
				this.LastFlag = null;
			else
				this.LastFlag = rs.getString("LastFlag").trim();

			if( rs.getString("ThisFlag") == null )
				this.ThisFlag = null;
			else
				this.ThisFlag = rs.getString("ThisFlag").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCalendarTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCalendarTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCalendarTraceSchema getSchema()
	{
		LDCalendarTraceSchema aLDCalendarTraceSchema = new LDCalendarTraceSchema();
		aLDCalendarTraceSchema.setSchema(this);
		return aLDCalendarTraceSchema;
	}

	public LDCalendarTraceDB getDB()
	{
		LDCalendarTraceDB aDBOper = new LDCalendarTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCalendarTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(fDate.getString( CommonDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrderNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ThisFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCalendarTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CommonDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1,SysConst.PACKAGESPILTER));
			OrderNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LastFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ThisFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCalendarTraceSchema";
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
		if (FCode.equalsIgnoreCase("CommonDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCommonDate()));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equalsIgnoreCase("LastFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastFlag));
		}
		if (FCode.equalsIgnoreCase("ThisFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ThisFlag));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCommonDate()));
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OrderNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(LastFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ThisFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("CommonDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CommonDate = fDate.getDate( FValue );
			}
			else
				CommonDate = null;
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrderNo = FValue.trim();
			}
			else
				OrderNo = null;
		}
		if (FCode.equalsIgnoreCase("LastFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastFlag = FValue.trim();
			}
			else
				LastFlag = null;
		}
		if (FCode.equalsIgnoreCase("ThisFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ThisFlag = FValue.trim();
			}
			else
				ThisFlag = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCalendarTraceSchema other = (LDCalendarTraceSchema)otherObject;
		return
			fDate.getString(CommonDate).equals(other.getCommonDate())
			&& OrderNo.equals(other.getOrderNo())
			&& LastFlag.equals(other.getLastFlag())
			&& ThisFlag.equals(other.getThisFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("CommonDate") ) {
			return 0;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 1;
		}
		if( strFieldName.equals("LastFlag") ) {
			return 2;
		}
		if( strFieldName.equals("ThisFlag") ) {
			return 3;
		}
		if( strFieldName.equals("Operator") ) {
			return 4;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 5;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 6;
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
				strFieldName = "CommonDate";
				break;
			case 1:
				strFieldName = "OrderNo";
				break;
			case 2:
				strFieldName = "LastFlag";
				break;
			case 3:
				strFieldName = "ThisFlag";
				break;
			case 4:
				strFieldName = "Operator";
				break;
			case 5:
				strFieldName = "ModifyDate";
				break;
			case 6:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("CommonDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ThisFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
