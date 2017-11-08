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
import com.sinosoft.lis.db.LCErrorReportDB;

/*
 * <p>ClassName: LCErrorReportSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCErrorReportSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCErrorReportSchema.class);

	// @Field
	/** 其他号码类型 */
	private String othernotype;
	/** 其他号码 */
	private String otherno;
	/** 差错岗位 */
	private String ErrorPos;
	/** 差错人 */
	private String ErrorOperator;
	/** 流水号 */
	private String SerialNo;
	/** 操作岗位 */
	private String OperatorPos;
	/** 差错代码 */
	private String ErrorCode;
	/** 差错内容 */
	private String ErrorContent;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCErrorReportSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "othernotype";
		pk[1] = "otherno";
		pk[2] = "ErrorPos";
		pk[3] = "ErrorOperator";
		pk[4] = "SerialNo";
		pk[5] = "OperatorPos";

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
		LCErrorReportSchema cloned = (LCErrorReportSchema)super.clone();
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
	* 00 --- 个单合同号<p>
	* 01 --- 集体合同号<p>
	* 02 --- 合同号<p>
	* 03 --- 批单号<p>
	* 04 --- 实付收据号<p>
	* 05 -- 保单印刷号
	*/
	public String getothernotype()
	{
		return othernotype;
	}
	public void setothernotype(String aothernotype)
	{
		othernotype = aothernotype;
	}
	public String getotherno()
	{
		return otherno;
	}
	public void setotherno(String aotherno)
	{
		otherno = aotherno;
	}
	/**
	* 1-初审岗、2-录单岗、3-复核岗、4-成品保单
	*/
	public String getErrorPos()
	{
		return ErrorPos;
	}
	public void setErrorPos(String aErrorPos)
	{
		ErrorPos = aErrorPos;
	}
	public String getErrorOperator()
	{
		return ErrorOperator;
	}
	public void setErrorOperator(String aErrorOperator)
	{
		ErrorOperator = aErrorOperator;
	}
	/**
	* 对应与lcissuepol表中的SerialNo字段
	*/
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 01-录单、02-复核、03-核保、04-保全
	*/
	public String getOperatorPos()
	{
		return OperatorPos;
	}
	public void setOperatorPos(String aOperatorPos)
	{
		OperatorPos = aOperatorPos;
	}
	/**
	* 对应于LcIssuePol表中的ISSUETYPE字段
	*/
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
	* 使用另外一个 LCErrorReportSchema 对象给 Schema 赋值
	* @param: aLCErrorReportSchema LCErrorReportSchema
	**/
	public void setSchema(LCErrorReportSchema aLCErrorReportSchema)
	{
		this.othernotype = aLCErrorReportSchema.getothernotype();
		this.otherno = aLCErrorReportSchema.getotherno();
		this.ErrorPos = aLCErrorReportSchema.getErrorPos();
		this.ErrorOperator = aLCErrorReportSchema.getErrorOperator();
		this.SerialNo = aLCErrorReportSchema.getSerialNo();
		this.OperatorPos = aLCErrorReportSchema.getOperatorPos();
		this.ErrorCode = aLCErrorReportSchema.getErrorCode();
		this.ErrorContent = aLCErrorReportSchema.getErrorContent();
		this.Operator = aLCErrorReportSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCErrorReportSchema.getMakeDate());
		this.MakeTime = aLCErrorReportSchema.getMakeTime();
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
			if( rs.getString("othernotype") == null )
				this.othernotype = null;
			else
				this.othernotype = rs.getString("othernotype").trim();

			if( rs.getString("otherno") == null )
				this.otherno = null;
			else
				this.otherno = rs.getString("otherno").trim();

			if( rs.getString("ErrorPos") == null )
				this.ErrorPos = null;
			else
				this.ErrorPos = rs.getString("ErrorPos").trim();

			if( rs.getString("ErrorOperator") == null )
				this.ErrorOperator = null;
			else
				this.ErrorOperator = rs.getString("ErrorOperator").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("OperatorPos") == null )
				this.OperatorPos = null;
			else
				this.OperatorPos = rs.getString("OperatorPos").trim();

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
			logger.debug("数据库中的LCErrorReport表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCErrorReportSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCErrorReportSchema getSchema()
	{
		LCErrorReportSchema aLCErrorReportSchema = new LCErrorReportSchema();
		aLCErrorReportSchema.setSchema(this);
		return aLCErrorReportSchema;
	}

	public LCErrorReportDB getDB()
	{
		LCErrorReportDB aDBOper = new LCErrorReportDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCErrorReport描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(othernotype)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(otherno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorPos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperatorPos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCErrorReport>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			othernotype = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			otherno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ErrorPos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ErrorOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OperatorPos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ErrorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ErrorContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCErrorReportSchema";
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
		if (FCode.equalsIgnoreCase("othernotype"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(othernotype));
		}
		if (FCode.equalsIgnoreCase("otherno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(otherno));
		}
		if (FCode.equalsIgnoreCase("ErrorPos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorPos));
		}
		if (FCode.equalsIgnoreCase("ErrorOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorOperator));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("OperatorPos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperatorPos));
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
				strFieldValue = StrTool.GBKToUnicode(othernotype);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(otherno);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ErrorPos);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ErrorOperator);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OperatorPos);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ErrorCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ErrorContent);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
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

		if (FCode.equalsIgnoreCase("othernotype"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				othernotype = FValue.trim();
			}
			else
				othernotype = null;
		}
		if (FCode.equalsIgnoreCase("otherno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				otherno = FValue.trim();
			}
			else
				otherno = null;
		}
		if (FCode.equalsIgnoreCase("ErrorPos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorPos = FValue.trim();
			}
			else
				ErrorPos = null;
		}
		if (FCode.equalsIgnoreCase("ErrorOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorOperator = FValue.trim();
			}
			else
				ErrorOperator = null;
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
		if (FCode.equalsIgnoreCase("OperatorPos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperatorPos = FValue.trim();
			}
			else
				OperatorPos = null;
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
		LCErrorReportSchema other = (LCErrorReportSchema)otherObject;
		return
			othernotype.equals(other.getothernotype())
			&& otherno.equals(other.getotherno())
			&& ErrorPos.equals(other.getErrorPos())
			&& ErrorOperator.equals(other.getErrorOperator())
			&& SerialNo.equals(other.getSerialNo())
			&& OperatorPos.equals(other.getOperatorPos())
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
		if( strFieldName.equals("othernotype") ) {
			return 0;
		}
		if( strFieldName.equals("otherno") ) {
			return 1;
		}
		if( strFieldName.equals("ErrorPos") ) {
			return 2;
		}
		if( strFieldName.equals("ErrorOperator") ) {
			return 3;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 4;
		}
		if( strFieldName.equals("OperatorPos") ) {
			return 5;
		}
		if( strFieldName.equals("ErrorCode") ) {
			return 6;
		}
		if( strFieldName.equals("ErrorContent") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
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
				strFieldName = "othernotype";
				break;
			case 1:
				strFieldName = "otherno";
				break;
			case 2:
				strFieldName = "ErrorPos";
				break;
			case 3:
				strFieldName = "ErrorOperator";
				break;
			case 4:
				strFieldName = "SerialNo";
				break;
			case 5:
				strFieldName = "OperatorPos";
				break;
			case 6:
				strFieldName = "ErrorCode";
				break;
			case 7:
				strFieldName = "ErrorContent";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
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
		if( strFieldName.equals("othernotype") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("otherno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorPos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperatorPos") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
