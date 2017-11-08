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
import com.sinosoft.lis.db.LDCodeModDB;

/*
 * <p>ClassName: LDCodeModSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDCodeModSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCodeModSchema.class);

	// @Field
	/** 代码类型 */
	private String CodeType;
	/** 编码 */
	private String Code;
	/** 编码名称 */
	private String CodeName;
	/** 内容 */
	private String Cont;
	/** 操作员 */
	private String Operator;
	/** 修改日期 */
	private Date ModifyDate;
	/** 是否计入问题件比例 */
	private String RecordQuest;
	/** 发送对象 */
	private String SendObj;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCodeModSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CodeType";
		pk[1] = "Code";

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
		LDCodeModSchema cloned = (LDCodeModSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	/**
	* 问题件类型
	*/
	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	public String getCodeName()
	{
		return CodeName;
	}
	public void setCodeName(String aCodeName)
	{
		CodeName = aCodeName;
	}
	public String getCont()
	{
		return Cont;
	}
	public void setCont(String aCont)
	{
		Cont = aCont;
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

	public String getRecordQuest()
	{
		return RecordQuest;
	}
	public void setRecordQuest(String aRecordQuest)
	{
		RecordQuest = aRecordQuest;
	}
	public String getSendObj()
	{
		return SendObj;
	}
	public void setSendObj(String aSendObj)
	{
		SendObj = aSendObj;
	}

	/**
	* 使用另外一个 LDCodeModSchema 对象给 Schema 赋值
	* @param: aLDCodeModSchema LDCodeModSchema
	**/
	public void setSchema(LDCodeModSchema aLDCodeModSchema)
	{
		this.CodeType = aLDCodeModSchema.getCodeType();
		this.Code = aLDCodeModSchema.getCode();
		this.CodeName = aLDCodeModSchema.getCodeName();
		this.Cont = aLDCodeModSchema.getCont();
		this.Operator = aLDCodeModSchema.getOperator();
		this.ModifyDate = fDate.getDate( aLDCodeModSchema.getModifyDate());
		this.RecordQuest = aLDCodeModSchema.getRecordQuest();
		this.SendObj = aLDCodeModSchema.getSendObj();
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
			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("CodeName") == null )
				this.CodeName = null;
			else
				this.CodeName = rs.getString("CodeName").trim();

			if( rs.getString("Cont") == null )
				this.Cont = null;
			else
				this.Cont = rs.getString("Cont").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("RecordQuest") == null )
				this.RecordQuest = null;
			else
				this.RecordQuest = rs.getString("RecordQuest").trim();

			if( rs.getString("SendObj") == null )
				this.SendObj = null;
			else
				this.SendObj = rs.getString("SendObj").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCodeMod表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeModSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCodeModSchema getSchema()
	{
		LDCodeModSchema aLDCodeModSchema = new LDCodeModSchema();
		aLDCodeModSchema.setSchema(this);
		return aLDCodeModSchema;
	}

	public LDCodeModDB getDB()
	{
		LDCodeModDB aDBOper = new LDCodeModDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCodeMod描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Cont)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RecordQuest)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendObj));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCodeMod>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Cont = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			RecordQuest = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SendObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeModSchema";
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
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName));
		}
		if (FCode.equalsIgnoreCase("Cont"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Cont));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("RecordQuest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecordQuest));
		}
		if (FCode.equalsIgnoreCase("SendObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendObj));
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
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CodeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Cont);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RecordQuest);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SendObj);
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

		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName = FValue.trim();
			}
			else
				CodeName = null;
		}
		if (FCode.equalsIgnoreCase("Cont"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Cont = FValue.trim();
			}
			else
				Cont = null;
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
		if (FCode.equalsIgnoreCase("RecordQuest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RecordQuest = FValue.trim();
			}
			else
				RecordQuest = null;
		}
		if (FCode.equalsIgnoreCase("SendObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendObj = FValue.trim();
			}
			else
				SendObj = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCodeModSchema other = (LDCodeModSchema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& Code.equals(other.getCode())
			&& CodeName.equals(other.getCodeName())
			&& Cont.equals(other.getCont())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& RecordQuest.equals(other.getRecordQuest())
			&& SendObj.equals(other.getSendObj());
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
		if( strFieldName.equals("CodeType") ) {
			return 0;
		}
		if( strFieldName.equals("Code") ) {
			return 1;
		}
		if( strFieldName.equals("CodeName") ) {
			return 2;
		}
		if( strFieldName.equals("Cont") ) {
			return 3;
		}
		if( strFieldName.equals("Operator") ) {
			return 4;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 5;
		}
		if( strFieldName.equals("RecordQuest") ) {
			return 6;
		}
		if( strFieldName.equals("SendObj") ) {
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
				strFieldName = "CodeType";
				break;
			case 1:
				strFieldName = "Code";
				break;
			case 2:
				strFieldName = "CodeName";
				break;
			case 3:
				strFieldName = "Cont";
				break;
			case 4:
				strFieldName = "Operator";
				break;
			case 5:
				strFieldName = "ModifyDate";
				break;
			case 6:
				strFieldName = "RecordQuest";
				break;
			case 7:
				strFieldName = "SendObj";
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
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Cont") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RecordQuest") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendObj") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
