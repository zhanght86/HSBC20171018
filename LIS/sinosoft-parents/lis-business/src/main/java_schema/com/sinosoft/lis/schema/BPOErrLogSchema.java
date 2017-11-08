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
import com.sinosoft.lis.db.BPOErrLogDB;

/*
 * <p>ClassName: BPOErrLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 录入外包（lis6.0）
 */
public class BPOErrLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(BPOErrLogSchema.class);

	// @Field
	/** 业务号码 */
	private String BussNo;
	/** 业务号码类型 */
	private String BussNoType;
	/** 错误类别 */
	private String ErrVer;
	/** 错误编码 */
	private String ErrCode;
	/** 错误内容 */
	private String ErrorContent;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public BPOErrLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "BussNo";
		pk[1] = "BussNoType";
		pk[2] = "ErrVer";
		pk[3] = "ErrCode";

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
		BPOErrLogSchema cloned = (BPOErrLogSchema)super.clone();
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
	public String getErrVer()
	{
		return ErrVer;
	}
	public void setErrVer(String aErrVer)
	{
		ErrVer = aErrVer;
	}
	public String getErrCode()
	{
		return ErrCode;
	}
	public void setErrCode(String aErrCode)
	{
		ErrCode = aErrCode;
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
	* 使用另外一个 BPOErrLogSchema 对象给 Schema 赋值
	* @param: aBPOErrLogSchema BPOErrLogSchema
	**/
	public void setSchema(BPOErrLogSchema aBPOErrLogSchema)
	{
		this.BussNo = aBPOErrLogSchema.getBussNo();
		this.BussNoType = aBPOErrLogSchema.getBussNoType();
		this.ErrVer = aBPOErrLogSchema.getErrVer();
		this.ErrCode = aBPOErrLogSchema.getErrCode();
		this.ErrorContent = aBPOErrLogSchema.getErrorContent();
		this.Operator = aBPOErrLogSchema.getOperator();
		this.MakeDate = fDate.getDate( aBPOErrLogSchema.getMakeDate());
		this.MakeTime = aBPOErrLogSchema.getMakeTime();
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

			if( rs.getString("ErrVer") == null )
				this.ErrVer = null;
			else
				this.ErrVer = rs.getString("ErrVer").trim();

			if( rs.getString("ErrCode") == null )
				this.ErrCode = null;
			else
				this.ErrCode = rs.getString("ErrCode").trim();

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
			logger.debug("数据库中的BPOErrLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPOErrLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public BPOErrLogSchema getSchema()
	{
		BPOErrLogSchema aBPOErrLogSchema = new BPOErrLogSchema();
		aBPOErrLogSchema.setSchema(this);
		return aBPOErrLogSchema;
	}

	public BPOErrLogDB getDB()
	{
		BPOErrLogDB aDBOper = new BPOErrLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPOErrLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPOErrLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ErrVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ErrCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ErrorContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPOErrLogSchema";
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
		if (FCode.equalsIgnoreCase("ErrVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrVer));
		}
		if (FCode.equalsIgnoreCase("ErrCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrCode));
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
				strFieldValue = StrTool.GBKToUnicode(ErrVer);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ErrCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ErrorContent);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
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
		if (FCode.equalsIgnoreCase("ErrVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrVer = FValue.trim();
			}
			else
				ErrVer = null;
		}
		if (FCode.equalsIgnoreCase("ErrCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrCode = FValue.trim();
			}
			else
				ErrCode = null;
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
		BPOErrLogSchema other = (BPOErrLogSchema)otherObject;
		return
			BussNo.equals(other.getBussNo())
			&& BussNoType.equals(other.getBussNoType())
			&& ErrVer.equals(other.getErrVer())
			&& ErrCode.equals(other.getErrCode())
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
		if( strFieldName.equals("ErrVer") ) {
			return 2;
		}
		if( strFieldName.equals("ErrCode") ) {
			return 3;
		}
		if( strFieldName.equals("ErrorContent") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
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
				strFieldName = "ErrVer";
				break;
			case 3:
				strFieldName = "ErrCode";
				break;
			case 4:
				strFieldName = "ErrorContent";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
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
		if( strFieldName.equals("ErrVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrCode") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
