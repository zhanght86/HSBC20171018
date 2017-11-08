

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
import com.sinosoft.lis.db.RIFeeRateTableTraceDB;

/*
 * <p>ClassName: RIFeeRateTableTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIFeeRateTableTraceSchema implements Schema, Cloneable
{
	// @Field
	/** 费率表编号 */
	private String FeeTableNo;
	/** 费率表名称 */
	private String FeeTableName;
	/** 费率表批次 */
	private String BatchNo;
	/** 存储表名称 */
	private String SaveDataName;
	/** 费率表批次生效日期 */
	private Date InureDate;
	/** 费率表批次失效日期 */
	private Date LapseDate;
	/** 批次导入日期 */
	private Date MakeDate;
	/** 批次导入时间 */
	private String MakeTime;
	/** 操作人 */
	private String Operator;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIFeeRateTableTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "FeeTableNo";
		pk[1] = "BatchNo";

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
		RIFeeRateTableTraceSchema cloned = (RIFeeRateTableTraceSchema)super.clone();
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
	* G01-一般  S01-特殊01 S02-特殊02。。。。。。。
	*/
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getSaveDataName()
	{
		return SaveDataName;
	}
	public void setSaveDataName(String aSaveDataName)
	{
		SaveDataName = aSaveDataName;
	}
	public String getInureDate()
	{
		if( InureDate != null )
			return fDate.getString(InureDate);
		else
			return null;
	}
	public void setInureDate(Date aInureDate)
	{
		InureDate = aInureDate;
	}
	public void setInureDate(String aInureDate)
	{
		if (aInureDate != null && !aInureDate.equals("") )
		{
			InureDate = fDate.getDate( aInureDate );
		}
		else
			InureDate = null;
	}

	public String getLapseDate()
	{
		if( LapseDate != null )
			return fDate.getString(LapseDate);
		else
			return null;
	}
	public void setLapseDate(Date aLapseDate)
	{
		LapseDate = aLapseDate;
	}
	public void setLapseDate(String aLapseDate)
	{
		if (aLapseDate != null && !aLapseDate.equals("") )
		{
			LapseDate = fDate.getDate( aLapseDate );
		}
		else
			LapseDate = null;
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
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 RIFeeRateTableTraceSchema 对象给 Schema 赋值
	* @param: aRIFeeRateTableTraceSchema RIFeeRateTableTraceSchema
	**/
	public void setSchema(RIFeeRateTableTraceSchema aRIFeeRateTableTraceSchema)
	{
		this.FeeTableNo = aRIFeeRateTableTraceSchema.getFeeTableNo();
		this.FeeTableName = aRIFeeRateTableTraceSchema.getFeeTableName();
		this.BatchNo = aRIFeeRateTableTraceSchema.getBatchNo();
		this.SaveDataName = aRIFeeRateTableTraceSchema.getSaveDataName();
		this.InureDate = fDate.getDate( aRIFeeRateTableTraceSchema.getInureDate());
		this.LapseDate = fDate.getDate( aRIFeeRateTableTraceSchema.getLapseDate());
		this.MakeDate = fDate.getDate( aRIFeeRateTableTraceSchema.getMakeDate());
		this.MakeTime = aRIFeeRateTableTraceSchema.getMakeTime();
		this.Operator = aRIFeeRateTableTraceSchema.getOperator();
		this.State = aRIFeeRateTableTraceSchema.getState();
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

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("SaveDataName") == null )
				this.SaveDataName = null;
			else
				this.SaveDataName = rs.getString("SaveDataName").trim();

			this.InureDate = rs.getDate("InureDate");
			this.LapseDate = rs.getDate("LapseDate");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIFeeRateTableTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIFeeRateTableTraceSchema getSchema()
	{
		RIFeeRateTableTraceSchema aRIFeeRateTableTraceSchema = new RIFeeRateTableTraceSchema();
		aRIFeeRateTableTraceSchema.setSchema(this);
		return aRIFeeRateTableTraceSchema;
	}

	public RIFeeRateTableTraceDB getDB()
	{
		RIFeeRateTableTraceDB aDBOper = new RIFeeRateTableTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTableTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaveDataName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InureDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LapseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTableTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SaveDataName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InureDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			LapseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableTraceSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("SaveDataName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaveDataName));
		}
		if (FCode.equalsIgnoreCase("InureDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInureDate()));
		}
		if (FCode.equalsIgnoreCase("LapseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLapseDate()));
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
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SaveDataName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInureDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLapseDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(State);
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("SaveDataName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaveDataName = FValue.trim();
			}
			else
				SaveDataName = null;
		}
		if (FCode.equalsIgnoreCase("InureDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InureDate = fDate.getDate( FValue );
			}
			else
				InureDate = null;
		}
		if (FCode.equalsIgnoreCase("LapseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LapseDate = fDate.getDate( FValue );
			}
			else
				LapseDate = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIFeeRateTableTraceSchema other = (RIFeeRateTableTraceSchema)otherObject;
		return
			FeeTableNo.equals(other.getFeeTableNo())
			&& FeeTableName.equals(other.getFeeTableName())
			&& BatchNo.equals(other.getBatchNo())
			&& SaveDataName.equals(other.getSaveDataName())
			&& fDate.getString(InureDate).equals(other.getInureDate())
			&& fDate.getString(LapseDate).equals(other.getLapseDate())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Operator.equals(other.getOperator())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("BatchNo") ) {
			return 2;
		}
		if( strFieldName.equals("SaveDataName") ) {
			return 3;
		}
		if( strFieldName.equals("InureDate") ) {
			return 4;
		}
		if( strFieldName.equals("LapseDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("State") ) {
			return 9;
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
				strFieldName = "BatchNo";
				break;
			case 3:
				strFieldName = "SaveDataName";
				break;
			case 4:
				strFieldName = "InureDate";
				break;
			case 5:
				strFieldName = "LapseDate";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "State";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaveDataName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InureDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LapseDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
