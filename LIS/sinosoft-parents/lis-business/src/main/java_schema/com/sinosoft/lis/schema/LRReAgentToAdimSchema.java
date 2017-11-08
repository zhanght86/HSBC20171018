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
import com.sinosoft.lis.db.LRReAgentToAdimDB;

/*
 * <p>ClassName: LRReAgentToAdimSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LRReAgentToAdimSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRReAgentToAdimSchema.class);

	// @Field
	/** 业务员原代码 */
	private String SAgentCode;
	/** 业务员新代码 */
	private String TAgentCode;
	/** 转任类型 */
	private String Type;
	/** 身份证号 */
	private String IDNo;
	/** 操作日期 */
	private Date MakeDate;
	/** 操作时间 */
	private String MakeTime;
	/** 操作员代码 */
	private String Operator;
	/** 业务员原渠道 */
	private String SBranchType;
	/** 业务员新渠道 */
	private String TBranchType;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRReAgentToAdimSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "SAgentCode";
		pk[1] = "TAgentCode";
		pk[2] = "Type";

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
		LRReAgentToAdimSchema cloned = (LRReAgentToAdimSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSAgentCode()
	{
		return SAgentCode;
	}
	public void setSAgentCode(String aSAgentCode)
	{
		SAgentCode = aSAgentCode;
	}
	public String getTAgentCode()
	{
		return TAgentCode;
	}
	public void setTAgentCode(String aTAgentCode)
	{
		TAgentCode = aTAgentCode;
	}
	/**
	* 1 续收员转任为续收督导<p>
	* 2 续收督导转任为续收员
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
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
	public String getSBranchType()
	{
		return SBranchType;
	}
	public void setSBranchType(String aSBranchType)
	{
		SBranchType = aSBranchType;
	}
	public String getTBranchType()
	{
		return TBranchType;
	}
	public void setTBranchType(String aTBranchType)
	{
		TBranchType = aTBranchType;
	}

	/**
	* 使用另外一个 LRReAgentToAdimSchema 对象给 Schema 赋值
	* @param: aLRReAgentToAdimSchema LRReAgentToAdimSchema
	**/
	public void setSchema(LRReAgentToAdimSchema aLRReAgentToAdimSchema)
	{
		this.SAgentCode = aLRReAgentToAdimSchema.getSAgentCode();
		this.TAgentCode = aLRReAgentToAdimSchema.getTAgentCode();
		this.Type = aLRReAgentToAdimSchema.getType();
		this.IDNo = aLRReAgentToAdimSchema.getIDNo();
		this.MakeDate = fDate.getDate( aLRReAgentToAdimSchema.getMakeDate());
		this.MakeTime = aLRReAgentToAdimSchema.getMakeTime();
		this.Operator = aLRReAgentToAdimSchema.getOperator();
		this.SBranchType = aLRReAgentToAdimSchema.getSBranchType();
		this.TBranchType = aLRReAgentToAdimSchema.getTBranchType();
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
			if( rs.getString("SAgentCode") == null )
				this.SAgentCode = null;
			else
				this.SAgentCode = rs.getString("SAgentCode").trim();

			if( rs.getString("TAgentCode") == null )
				this.TAgentCode = null;
			else
				this.TAgentCode = rs.getString("TAgentCode").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("SBranchType") == null )
				this.SBranchType = null;
			else
				this.SBranchType = rs.getString("SBranchType").trim();

			if( rs.getString("TBranchType") == null )
				this.TBranchType = null;
			else
				this.TBranchType = rs.getString("TBranchType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRReAgentToAdim表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRReAgentToAdimSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRReAgentToAdimSchema getSchema()
	{
		LRReAgentToAdimSchema aLRReAgentToAdimSchema = new LRReAgentToAdimSchema();
		aLRReAgentToAdimSchema.setSchema(this);
		return aLRReAgentToAdimSchema;
	}

	public LRReAgentToAdimDB getDB()
	{
		LRReAgentToAdimDB aDBOper = new LRReAgentToAdimDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRReAgentToAdim描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SAgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TAgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SBranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TBranchType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRReAgentToAdim>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SAgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TAgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SBranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TBranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRReAgentToAdimSchema";
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
		if (FCode.equalsIgnoreCase("SAgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SAgentCode));
		}
		if (FCode.equalsIgnoreCase("TAgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TAgentCode));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
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
		if (FCode.equalsIgnoreCase("SBranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SBranchType));
		}
		if (FCode.equalsIgnoreCase("TBranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TBranchType));
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
				strFieldValue = StrTool.GBKToUnicode(SAgentCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TAgentCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SBranchType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TBranchType);
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

		if (FCode.equalsIgnoreCase("SAgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SAgentCode = FValue.trim();
			}
			else
				SAgentCode = null;
		}
		if (FCode.equalsIgnoreCase("TAgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TAgentCode = FValue.trim();
			}
			else
				TAgentCode = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
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
		if (FCode.equalsIgnoreCase("SBranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SBranchType = FValue.trim();
			}
			else
				SBranchType = null;
		}
		if (FCode.equalsIgnoreCase("TBranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TBranchType = FValue.trim();
			}
			else
				TBranchType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRReAgentToAdimSchema other = (LRReAgentToAdimSchema)otherObject;
		return
			SAgentCode.equals(other.getSAgentCode())
			&& TAgentCode.equals(other.getTAgentCode())
			&& Type.equals(other.getType())
			&& IDNo.equals(other.getIDNo())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Operator.equals(other.getOperator())
			&& SBranchType.equals(other.getSBranchType())
			&& TBranchType.equals(other.getTBranchType());
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
		if( strFieldName.equals("SAgentCode") ) {
			return 0;
		}
		if( strFieldName.equals("TAgentCode") ) {
			return 1;
		}
		if( strFieldName.equals("Type") ) {
			return 2;
		}
		if( strFieldName.equals("IDNo") ) {
			return 3;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 4;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("SBranchType") ) {
			return 7;
		}
		if( strFieldName.equals("TBranchType") ) {
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
				strFieldName = "SAgentCode";
				break;
			case 1:
				strFieldName = "TAgentCode";
				break;
			case 2:
				strFieldName = "Type";
				break;
			case 3:
				strFieldName = "IDNo";
				break;
			case 4:
				strFieldName = "MakeDate";
				break;
			case 5:
				strFieldName = "MakeTime";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "SBranchType";
				break;
			case 8:
				strFieldName = "TBranchType";
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
		if( strFieldName.equals("SAgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TAgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
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
		if( strFieldName.equals("SBranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TBranchType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
