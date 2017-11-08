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
import com.sinosoft.lis.db.LICodeMappDB;

/*
 * <p>ClassName: LICodeMappSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LICodeMappSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LICodeMappSchema.class);

	// @Field
	/** 编码类别 */
	private String CodeType;
	/** 编码类别名 */
	private String CodeTypename;
	/** 系统编码 */
	private String SystemCode;
	/** 系统编码名称 */
	private String SCodeName;
	/** 财务编码 */
	private String AccCode;
	/** 财务编码名称 */
	private String ACodeName2;
	/** 备注1 */
	private String memo1;
	/** 备注2 */
	private String memo2;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LICodeMappSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "CodeType";
		pk[1] = "SystemCode";
		pk[2] = "AccCode";

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
		LICodeMappSchema cloned = (LICodeMappSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 1.公司代码<p>
	* 2.成本中心<p>
	* 3.代理机构<p>
	* 4.货币类型<p>
	* 5.分保公司<p>
	* 6.销售渠道<p>
	* 7.险种分类1<p>
	* 8.险种分类2
	*/
	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	public String getCodeTypename()
	{
		return CodeTypename;
	}
	public void setCodeTypename(String aCodeTypename)
	{
		CodeTypename = aCodeTypename;
	}
	public String getSystemCode()
	{
		return SystemCode;
	}
	public void setSystemCode(String aSystemCode)
	{
		SystemCode = aSystemCode;
	}
	public String getSCodeName()
	{
		return SCodeName;
	}
	public void setSCodeName(String aSCodeName)
	{
		SCodeName = aSCodeName;
	}
	public String getAccCode()
	{
		return AccCode;
	}
	public void setAccCode(String aAccCode)
	{
		AccCode = aAccCode;
	}
	public String getACodeName2()
	{
		return ACodeName2;
	}
	public void setACodeName2(String aACodeName2)
	{
		ACodeName2 = aACodeName2;
	}
	/**
	* 求险种性质1时:<p>
	* memo1:性质代码H(健康),L(寿险),A(意外)
	*/
	public String getmemo1()
	{
		return memo1;
	}
	public void setmemo1(String amemo1)
	{
		memo1 = amemo1;
	}
	/**
	* 求险种性质1时:<p>
	* memo2:指代码memo1的含义
	*/
	public String getmemo2()
	{
		return memo2;
	}
	public void setmemo2(String amemo2)
	{
		memo2 = amemo2;
	}

	/**
	* 使用另外一个 LICodeMappSchema 对象给 Schema 赋值
	* @param: aLICodeMappSchema LICodeMappSchema
	**/
	public void setSchema(LICodeMappSchema aLICodeMappSchema)
	{
		this.CodeType = aLICodeMappSchema.getCodeType();
		this.CodeTypename = aLICodeMappSchema.getCodeTypename();
		this.SystemCode = aLICodeMappSchema.getSystemCode();
		this.SCodeName = aLICodeMappSchema.getSCodeName();
		this.AccCode = aLICodeMappSchema.getAccCode();
		this.ACodeName2 = aLICodeMappSchema.getACodeName2();
		this.memo1 = aLICodeMappSchema.getmemo1();
		this.memo2 = aLICodeMappSchema.getmemo2();
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

			if( rs.getString("CodeTypename") == null )
				this.CodeTypename = null;
			else
				this.CodeTypename = rs.getString("CodeTypename").trim();

			if( rs.getString("SystemCode") == null )
				this.SystemCode = null;
			else
				this.SystemCode = rs.getString("SystemCode").trim();

			if( rs.getString("SCodeName") == null )
				this.SCodeName = null;
			else
				this.SCodeName = rs.getString("SCodeName").trim();

			if( rs.getString("AccCode") == null )
				this.AccCode = null;
			else
				this.AccCode = rs.getString("AccCode").trim();

			if( rs.getString("ACodeName2") == null )
				this.ACodeName2 = null;
			else
				this.ACodeName2 = rs.getString("ACodeName2").trim();

			if( rs.getString("memo1") == null )
				this.memo1 = null;
			else
				this.memo1 = rs.getString("memo1").trim();

			if( rs.getString("memo2") == null )
				this.memo2 = null;
			else
				this.memo2 = rs.getString("memo2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LICodeMapp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LICodeMappSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LICodeMappSchema getSchema()
	{
		LICodeMappSchema aLICodeMappSchema = new LICodeMappSchema();
		aLICodeMappSchema.setSchema(this);
		return aLICodeMappSchema;
	}

	public LICodeMappDB getDB()
	{
		LICodeMappDB aDBOper = new LICodeMappDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLICodeMapp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeTypename)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SystemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SCodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ACodeName2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(memo1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(memo2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLICodeMapp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CodeTypename = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SystemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SCodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ACodeName2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			memo1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			memo2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LICodeMappSchema";
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
		if (FCode.equalsIgnoreCase("CodeTypename"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeTypename));
		}
		if (FCode.equalsIgnoreCase("SystemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SystemCode));
		}
		if (FCode.equalsIgnoreCase("SCodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SCodeName));
		}
		if (FCode.equalsIgnoreCase("AccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCode));
		}
		if (FCode.equalsIgnoreCase("ACodeName2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ACodeName2));
		}
		if (FCode.equalsIgnoreCase("memo1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(memo1));
		}
		if (FCode.equalsIgnoreCase("memo2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(memo2));
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
				strFieldValue = StrTool.GBKToUnicode(CodeTypename);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SystemCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SCodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ACodeName2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(memo1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(memo2);
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
		if (FCode.equalsIgnoreCase("CodeTypename"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeTypename = FValue.trim();
			}
			else
				CodeTypename = null;
		}
		if (FCode.equalsIgnoreCase("SystemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SystemCode = FValue.trim();
			}
			else
				SystemCode = null;
		}
		if (FCode.equalsIgnoreCase("SCodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SCodeName = FValue.trim();
			}
			else
				SCodeName = null;
		}
		if (FCode.equalsIgnoreCase("AccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCode = FValue.trim();
			}
			else
				AccCode = null;
		}
		if (FCode.equalsIgnoreCase("ACodeName2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ACodeName2 = FValue.trim();
			}
			else
				ACodeName2 = null;
		}
		if (FCode.equalsIgnoreCase("memo1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				memo1 = FValue.trim();
			}
			else
				memo1 = null;
		}
		if (FCode.equalsIgnoreCase("memo2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				memo2 = FValue.trim();
			}
			else
				memo2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LICodeMappSchema other = (LICodeMappSchema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& CodeTypename.equals(other.getCodeTypename())
			&& SystemCode.equals(other.getSystemCode())
			&& SCodeName.equals(other.getSCodeName())
			&& AccCode.equals(other.getAccCode())
			&& ACodeName2.equals(other.getACodeName2())
			&& memo1.equals(other.getmemo1())
			&& memo2.equals(other.getmemo2());
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
		if( strFieldName.equals("CodeTypename") ) {
			return 1;
		}
		if( strFieldName.equals("SystemCode") ) {
			return 2;
		}
		if( strFieldName.equals("SCodeName") ) {
			return 3;
		}
		if( strFieldName.equals("AccCode") ) {
			return 4;
		}
		if( strFieldName.equals("ACodeName2") ) {
			return 5;
		}
		if( strFieldName.equals("memo1") ) {
			return 6;
		}
		if( strFieldName.equals("memo2") ) {
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
				strFieldName = "CodeTypename";
				break;
			case 2:
				strFieldName = "SystemCode";
				break;
			case 3:
				strFieldName = "SCodeName";
				break;
			case 4:
				strFieldName = "AccCode";
				break;
			case 5:
				strFieldName = "ACodeName2";
				break;
			case 6:
				strFieldName = "memo1";
				break;
			case 7:
				strFieldName = "memo2";
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
		if( strFieldName.equals("CodeTypename") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SystemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SCodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ACodeName2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("memo1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("memo2") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
