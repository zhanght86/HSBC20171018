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
import com.sinosoft.lis.db.BPOMissionDetailErrorDB;

/*
 * <p>ClassName: BPOMissionDetailErrorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 录入外包（lis6.0）
 */
public class BPOMissionDetailErrorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(BPOMissionDetailErrorSchema.class);

	// @Field
	/** 业务号码 */
	private String BussNo;
	/** 业务号码类型 */
	private String BussNoType;
	/** 错误数 */
	private int ErrorCount;
	/** 处理流水号 */
	private String SerialNo;
	/** 错误项名称 */
	private String ErrorTag;
	/** 操作错误代码 */
	private String ErrorCode;
	/** 操作错误内容 */
	private String ErrorContent;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public BPOMissionDetailErrorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "BussNo";
		pk[1] = "BussNoType";
		pk[2] = "ErrorCount";
		pk[3] = "SerialNo";

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
		BPOMissionDetailErrorSchema cloned = (BPOMissionDetailErrorSchema)super.clone();
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
	* 根据业务号码类型判断:新契约(TB)则对应印刷号理赔(LP)则对应赔案号保全(BQ)则对应保全受理号
	*/
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		BussNo = aBussNo;
	}
	/**
	* 对业务号码进行分类，如TB-新契约；LP-理赔；BQ-保全
	*/
	public String getBussNoType()
	{
		return BussNoType;
	}
	public void setBussNoType(String aBussNoType)
	{
		BussNoType = aBussNoType;
	}
	/**
	* 返回错误信息中的第几条如返回三条错误，则在该表中有三条记录，此字段值为1，2，3
	*/
	public int getErrorCount()
	{
		return ErrorCount;
	}
	public void setErrorCount(int aErrorCount)
	{
		ErrorCount = aErrorCount;
	}
	public void setErrorCount(String aErrorCount)
	{
		if (aErrorCount != null && !aErrorCount.equals(""))
		{
			Integer tInteger = new Integer(aErrorCount);
			int i = tInteger.intValue();
			ErrorCount = i;
		}
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getErrorTag()
	{
		return ErrorTag;
	}
	public void setErrorTag(String aErrorTag)
	{
		ErrorTag = aErrorTag;
	}
	public String getErrorCode()
	{
		return ErrorCode;
	}
	public void setErrorCode(String aErrorCode)
	{
		ErrorCode = aErrorCode;
	}
	public String getErrorContent()
	{
		return ErrorContent;
	}
	public void setErrorContent(String aErrorContent)
	{
		ErrorContent = aErrorContent;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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

	/**
	* 使用另外一个 BPOMissionDetailErrorSchema 对象给 Schema 赋值
	* @param: aBPOMissionDetailErrorSchema BPOMissionDetailErrorSchema
	**/
	public void setSchema(BPOMissionDetailErrorSchema aBPOMissionDetailErrorSchema)
	{
		this.BussNo = aBPOMissionDetailErrorSchema.getBussNo();
		this.BussNoType = aBPOMissionDetailErrorSchema.getBussNoType();
		this.ErrorCount = aBPOMissionDetailErrorSchema.getErrorCount();
		this.SerialNo = aBPOMissionDetailErrorSchema.getSerialNo();
		this.ErrorTag = aBPOMissionDetailErrorSchema.getErrorTag();
		this.ErrorCode = aBPOMissionDetailErrorSchema.getErrorCode();
		this.ErrorContent = aBPOMissionDetailErrorSchema.getErrorContent();
		this.Operator = aBPOMissionDetailErrorSchema.getOperator();
		this.MakeDate = fDate.getDate( aBPOMissionDetailErrorSchema.getMakeDate());
		this.MakeTime = aBPOMissionDetailErrorSchema.getMakeTime();
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
			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("BussNoType") == null )
				this.BussNoType = null;
			else
				this.BussNoType = rs.getString("BussNoType").trim();

			this.ErrorCount = rs.getInt("ErrorCount");
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("ErrorTag") == null )
				this.ErrorTag = null;
			else
				this.ErrorTag = rs.getString("ErrorTag").trim();

			if( rs.getString("ErrorCode") == null )
				this.ErrorCode = null;
			else
				this.ErrorCode = rs.getString("ErrorCode").trim();

			if( rs.getString("ErrorContent") == null )
				this.ErrorContent = null;
			else
				this.ErrorContent = rs.getString("ErrorContent").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的BPOMissionDetailError表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPOMissionDetailErrorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public BPOMissionDetailErrorSchema getSchema()
	{
		BPOMissionDetailErrorSchema aBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();
		aBPOMissionDetailErrorSchema.setSchema(this);
		return aBPOMissionDetailErrorSchema;
	}

	public BPOMissionDetailErrorDB getDB()
	{
		BPOMissionDetailErrorDB aDBOper = new BPOMissionDetailErrorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPOMissionDetailError描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ErrorCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorTag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPOMissionDetailError>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ErrorCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ErrorTag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ErrorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ErrorContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPOMissionDetailErrorSchema";
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
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNoType));
		}
		if (FCode.equalsIgnoreCase("ErrorCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorCount));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ErrorTag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorTag));
		}
		if (FCode.equalsIgnoreCase("ErrorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorCode));
		}
		if (FCode.equalsIgnoreCase("ErrorContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorContent));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BussNoType);
				break;
			case 2:
				strFieldValue = String.valueOf(ErrorCount);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ErrorTag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ErrorCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ErrorContent);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNoType = FValue.trim();
			}
			else
				BussNoType = null;
		}
		if (FCode.equalsIgnoreCase("ErrorCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ErrorCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("ErrorTag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorTag = FValue.trim();
			}
			else
				ErrorTag = null;
		}
		if (FCode.equalsIgnoreCase("ErrorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorCode = FValue.trim();
			}
			else
				ErrorCode = null;
		}
		if (FCode.equalsIgnoreCase("ErrorContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorContent = FValue.trim();
			}
			else
				ErrorContent = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		BPOMissionDetailErrorSchema other = (BPOMissionDetailErrorSchema)otherObject;
		return
			BussNo.equals(other.getBussNo())
			&& BussNoType.equals(other.getBussNoType())
			&& ErrorCount == other.getErrorCount()
			&& SerialNo.equals(other.getSerialNo())
			&& ErrorTag.equals(other.getErrorTag())
			&& ErrorCode.equals(other.getErrorCode())
			&& ErrorContent.equals(other.getErrorContent())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("BussNo") ) {
			return 0;
		}
		if( strFieldName.equals("BussNoType") ) {
			return 1;
		}
		if( strFieldName.equals("ErrorCount") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("ErrorTag") ) {
			return 4;
		}
		if( strFieldName.equals("ErrorCode") ) {
			return 5;
		}
		if( strFieldName.equals("ErrorContent") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "BussNo";
				break;
			case 1:
				strFieldName = "BussNoType";
				break;
			case 2:
				strFieldName = "ErrorCount";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "ErrorTag";
				break;
			case 5:
				strFieldName = "ErrorCode";
				break;
			case 6:
				strFieldName = "ErrorContent";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorTag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
