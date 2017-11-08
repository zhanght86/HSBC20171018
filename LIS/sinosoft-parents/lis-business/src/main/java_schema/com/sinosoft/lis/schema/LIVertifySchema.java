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
import com.sinosoft.lis.db.LIVertifyDB;

/*
 * <p>ClassName: LIVertifySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口
 */
public class LIVertifySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIVertifySchema.class);

	// @Field
	/** 科目名称 */
	private String SubjectName;
	/** 科目代码 */
	private String SubjectCode;
	/** 公司段 */
	private String SegComCode;
	/** 明细段 */
	private String SegAccountSub;
	/** 渠道段 */
	private String SegSaleChnl;
	/** 产品段 */
	private String SegRiskCode;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIVertifySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SubjectName";

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
		LIVertifySchema cloned = (LIVertifySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSubjectName()
	{
		return SubjectName;
	}
	public void setSubjectName(String aSubjectName)
	{
		SubjectName = aSubjectName;
	}
	public String getSubjectCode()
	{
		return SubjectCode;
	}
	public void setSubjectCode(String aSubjectCode)
	{
		SubjectCode = aSubjectCode;
	}
	public String getSegComCode()
	{
		return SegComCode;
	}
	public void setSegComCode(String aSegComCode)
	{
		SegComCode = aSegComCode;
	}
	public String getSegAccountSub()
	{
		return SegAccountSub;
	}
	public void setSegAccountSub(String aSegAccountSub)
	{
		SegAccountSub = aSegAccountSub;
	}
	public String getSegSaleChnl()
	{
		return SegSaleChnl;
	}
	public void setSegSaleChnl(String aSegSaleChnl)
	{
		SegSaleChnl = aSegSaleChnl;
	}
	public String getSegRiskCode()
	{
		return SegRiskCode;
	}
	public void setSegRiskCode(String aSegRiskCode)
	{
		SegRiskCode = aSegRiskCode;
	}

	/**
	* 使用另外一个 LIVertifySchema 对象给 Schema 赋值
	* @param: aLIVertifySchema LIVertifySchema
	**/
	public void setSchema(LIVertifySchema aLIVertifySchema)
	{
		this.SubjectName = aLIVertifySchema.getSubjectName();
		this.SubjectCode = aLIVertifySchema.getSubjectCode();
		this.SegComCode = aLIVertifySchema.getSegComCode();
		this.SegAccountSub = aLIVertifySchema.getSegAccountSub();
		this.SegSaleChnl = aLIVertifySchema.getSegSaleChnl();
		this.SegRiskCode = aLIVertifySchema.getSegRiskCode();
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
			if( rs.getString("SubjectName") == null )
				this.SubjectName = null;
			else
				this.SubjectName = rs.getString("SubjectName").trim();

			if( rs.getString("SubjectCode") == null )
				this.SubjectCode = null;
			else
				this.SubjectCode = rs.getString("SubjectCode").trim();

			if( rs.getString("SegComCode") == null )
				this.SegComCode = null;
			else
				this.SegComCode = rs.getString("SegComCode").trim();

			if( rs.getString("SegAccountSub") == null )
				this.SegAccountSub = null;
			else
				this.SegAccountSub = rs.getString("SegAccountSub").trim();

			if( rs.getString("SegSaleChnl") == null )
				this.SegSaleChnl = null;
			else
				this.SegSaleChnl = rs.getString("SegSaleChnl").trim();

			if( rs.getString("SegRiskCode") == null )
				this.SegRiskCode = null;
			else
				this.SegRiskCode = rs.getString("SegRiskCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIVertify表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIVertifySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIVertifySchema getSchema()
	{
		LIVertifySchema aLIVertifySchema = new LIVertifySchema();
		aLIVertifySchema.setSchema(this);
		return aLIVertifySchema;
	}

	public LIVertifyDB getDB()
	{
		LIVertifyDB aDBOper = new LIVertifyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIVertify描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubjectName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubjectCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SegComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SegAccountSub)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SegSaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SegRiskCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIVertify>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubjectName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubjectCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SegComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SegAccountSub = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SegSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SegRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIVertifySchema";
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
		if (FCode.equalsIgnoreCase("SubjectName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectName));
		}
		if (FCode.equalsIgnoreCase("SubjectCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectCode));
		}
		if (FCode.equalsIgnoreCase("SegComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SegComCode));
		}
		if (FCode.equalsIgnoreCase("SegAccountSub"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SegAccountSub));
		}
		if (FCode.equalsIgnoreCase("SegSaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SegSaleChnl));
		}
		if (FCode.equalsIgnoreCase("SegRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SegRiskCode));
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
				strFieldValue = StrTool.GBKToUnicode(SubjectName);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubjectCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SegComCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SegAccountSub);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SegSaleChnl);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SegRiskCode);
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

		if (FCode.equalsIgnoreCase("SubjectName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectName = FValue.trim();
			}
			else
				SubjectName = null;
		}
		if (FCode.equalsIgnoreCase("SubjectCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectCode = FValue.trim();
			}
			else
				SubjectCode = null;
		}
		if (FCode.equalsIgnoreCase("SegComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SegComCode = FValue.trim();
			}
			else
				SegComCode = null;
		}
		if (FCode.equalsIgnoreCase("SegAccountSub"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SegAccountSub = FValue.trim();
			}
			else
				SegAccountSub = null;
		}
		if (FCode.equalsIgnoreCase("SegSaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SegSaleChnl = FValue.trim();
			}
			else
				SegSaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("SegRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SegRiskCode = FValue.trim();
			}
			else
				SegRiskCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIVertifySchema other = (LIVertifySchema)otherObject;
		return
			SubjectName.equals(other.getSubjectName())
			&& SubjectCode.equals(other.getSubjectCode())
			&& SegComCode.equals(other.getSegComCode())
			&& SegAccountSub.equals(other.getSegAccountSub())
			&& SegSaleChnl.equals(other.getSegSaleChnl())
			&& SegRiskCode.equals(other.getSegRiskCode());
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
		if( strFieldName.equals("SubjectName") ) {
			return 0;
		}
		if( strFieldName.equals("SubjectCode") ) {
			return 1;
		}
		if( strFieldName.equals("SegComCode") ) {
			return 2;
		}
		if( strFieldName.equals("SegAccountSub") ) {
			return 3;
		}
		if( strFieldName.equals("SegSaleChnl") ) {
			return 4;
		}
		if( strFieldName.equals("SegRiskCode") ) {
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
				strFieldName = "SubjectName";
				break;
			case 1:
				strFieldName = "SubjectCode";
				break;
			case 2:
				strFieldName = "SegComCode";
				break;
			case 3:
				strFieldName = "SegAccountSub";
				break;
			case 4:
				strFieldName = "SegSaleChnl";
				break;
			case 5:
				strFieldName = "SegRiskCode";
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
		if( strFieldName.equals("SubjectName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubjectCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SegComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SegAccountSub") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SegSaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SegRiskCode") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
