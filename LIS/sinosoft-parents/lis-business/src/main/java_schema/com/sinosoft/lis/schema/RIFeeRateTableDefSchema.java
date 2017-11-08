

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIFeeRateTableDefDB;

/*
 * <p>ClassName: RIFeeRateTableDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIFeeRateTableDefSchema implements Schema, Cloneable
{
	// @Field
	/** 费率表编号 */
	private String FeeTableNo;
	/** 费率表名称 */
	private String FeeTableName;
	/** 费率表类型 */
	private String FeeTableType;
	/** 适用方式 */
	private String MatchMode;
	/** 状态 */
	private String State;
	/** 建立日期 */
	private Date MakeDate;
	/** 建立时间 */
	private String MakeTime;
	/** 操作人 */
	private String Operator;
	/** 费率表描述 */
	private String ReMark;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIFeeRateTableDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "FeeTableNo";

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
		RIFeeRateTableDefSchema cloned = (RIFeeRateTableDefSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFeeTableNo()
	{
		return FeeTableNo;
	}
	public void setFeeTableNo(String aFeeTableNo)
	{
		FeeTableNo = aFeeTableNo;
	}
	public String getFeeTableName()
	{
		return FeeTableName;
	}
	public void setFeeTableName(String aFeeTableName)
	{
		FeeTableName = aFeeTableName;
	}
	/**
	* 分保保费<p>
	*   分保佣金
	*/
	public String getFeeTableType()
	{
		return FeeTableType;
	}
	public void setFeeTableType(String aFeeTableType)
	{
		FeeTableType = aFeeTableType;
	}
	/**
	* 01 -- 业务发生日<p>
	* <p>
	* 02 -- 保单生效日
	*/
	public String getMatchMode()
	{
		return MatchMode;
	}
	public void setMatchMode(String aMatchMode)
	{
		MatchMode = aMatchMode;
	}
	/**
	* 01--有效<p>
	* 02--未生效
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}

	/**
	* 使用另外一个 RIFeeRateTableDefSchema 对象给 Schema 赋值
	* @param: aRIFeeRateTableDefSchema RIFeeRateTableDefSchema
	**/
	public void setSchema(RIFeeRateTableDefSchema aRIFeeRateTableDefSchema)
	{
		this.FeeTableNo = aRIFeeRateTableDefSchema.getFeeTableNo();
		this.FeeTableName = aRIFeeRateTableDefSchema.getFeeTableName();
		this.FeeTableType = aRIFeeRateTableDefSchema.getFeeTableType();
		this.MatchMode = aRIFeeRateTableDefSchema.getMatchMode();
		this.State = aRIFeeRateTableDefSchema.getState();
		this.MakeDate = fDate.getDate( aRIFeeRateTableDefSchema.getMakeDate());
		this.MakeTime = aRIFeeRateTableDefSchema.getMakeTime();
		this.Operator = aRIFeeRateTableDefSchema.getOperator();
		this.ReMark = aRIFeeRateTableDefSchema.getReMark();
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
			if( rs.getString("FeeTableNo") == null )
				this.FeeTableNo = null;
			else
				this.FeeTableNo = rs.getString("FeeTableNo").trim();

			if( rs.getString("FeeTableName") == null )
				this.FeeTableName = null;
			else
				this.FeeTableName = rs.getString("FeeTableName").trim();

			if( rs.getString("FeeTableType") == null )
				this.FeeTableType = null;
			else
				this.FeeTableType = rs.getString("FeeTableType").trim();

			if( rs.getString("MatchMode") == null )
				this.MatchMode = null;
			else
				this.MatchMode = rs.getString("MatchMode").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIFeeRateTableDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIFeeRateTableDefSchema getSchema()
	{
		RIFeeRateTableDefSchema aRIFeeRateTableDefSchema = new RIFeeRateTableDefSchema();
		aRIFeeRateTableDefSchema.setSchema(this);
		return aRIFeeRateTableDefSchema;
	}

	public RIFeeRateTableDefDB getDB()
	{
		RIFeeRateTableDefDB aDBOper = new RIFeeRateTableDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTableDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTableType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTableDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FeeTableType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MatchMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableDefSchema";
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
		if (FCode.equalsIgnoreCase("FeeTableNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableNo));
		}
		if (FCode.equalsIgnoreCase("FeeTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableName));
		}
		if (FCode.equalsIgnoreCase("FeeTableType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableType));
		}
		if (FCode.equalsIgnoreCase("MatchMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchMode));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
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
				strFieldValue = StrTool.GBKToUnicode(FeeTableNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeTableName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FeeTableType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MatchMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
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

		if (FCode.equalsIgnoreCase("FeeTableNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableNo = FValue.trim();
			}
			else
				FeeTableNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableName = FValue.trim();
			}
			else
				FeeTableName = null;
		}
		if (FCode.equalsIgnoreCase("FeeTableType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableType = FValue.trim();
			}
			else
				FeeTableType = null;
		}
		if (FCode.equalsIgnoreCase("MatchMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchMode = FValue.trim();
			}
			else
				MatchMode = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIFeeRateTableDefSchema other = (RIFeeRateTableDefSchema)otherObject;
		return
			FeeTableNo.equals(other.getFeeTableNo())
			&& FeeTableName.equals(other.getFeeTableName())
			&& FeeTableType.equals(other.getFeeTableType())
			&& MatchMode.equals(other.getMatchMode())
			&& State.equals(other.getState())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Operator.equals(other.getOperator())
			&& ReMark.equals(other.getReMark());
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
		if( strFieldName.equals("FeeTableNo") ) {
			return 0;
		}
		if( strFieldName.equals("FeeTableName") ) {
			return 1;
		}
		if( strFieldName.equals("FeeTableType") ) {
			return 2;
		}
		if( strFieldName.equals("MatchMode") ) {
			return 3;
		}
		if( strFieldName.equals("State") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("ReMark") ) {
			return 8;
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
				strFieldName = "FeeTableNo";
				break;
			case 1:
				strFieldName = "FeeTableName";
				break;
			case 2:
				strFieldName = "FeeTableType";
				break;
			case 3:
				strFieldName = "MatchMode";
				break;
			case 4:
				strFieldName = "State";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "ReMark";
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
		if( strFieldName.equals("FeeTableNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeTableType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MatchMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
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
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
