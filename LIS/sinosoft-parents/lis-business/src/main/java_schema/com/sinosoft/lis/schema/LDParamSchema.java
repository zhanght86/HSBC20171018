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
import com.sinosoft.lis.db.LDParamDB;

/*
 * <p>ClassName: LDParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LDParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDParamSchema.class);

	// @Field
	/** 参数类型 */
	private String ParamType;
	/** 参数 */
	private String Param;
	/** 参数1 */
	private String param1;
	/** 注释 */
	private String Noti;
	/** 其它标志 */
	private String OtherSign;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ParamType";
		pk[1] = "Param";
		pk[2] = "param1";

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
		LDParamSchema cloned = (LDParamSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getParamType()
	{
		return ParamType;
	}
	public void setParamType(String aParamType)
	{
		ParamType = aParamType;
	}
	public String getParam()
	{
		return Param;
	}
	public void setParam(String aParam)
	{
		Param = aParam;
	}
	public String getparam1()
	{
		return param1;
	}
	public void setparam1(String aparam1)
	{
		param1 = aparam1;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
	}
	public String getOtherSign()
	{
		return OtherSign;
	}
	public void setOtherSign(String aOtherSign)
	{
		OtherSign = aOtherSign;
	}

	/**
	* 使用另外一个 LDParamSchema 对象给 Schema 赋值
	* @param: aLDParamSchema LDParamSchema
	**/
	public void setSchema(LDParamSchema aLDParamSchema)
	{
		this.ParamType = aLDParamSchema.getParamType();
		this.Param = aLDParamSchema.getParam();
		this.param1 = aLDParamSchema.getparam1();
		this.Noti = aLDParamSchema.getNoti();
		this.OtherSign = aLDParamSchema.getOtherSign();
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
			if( rs.getString("ParamType") == null )
				this.ParamType = null;
			else
				this.ParamType = rs.getString("ParamType").trim();

			if( rs.getString("Param") == null )
				this.Param = null;
			else
				this.Param = rs.getString("Param").trim();

			if( rs.getString("param1") == null )
				this.param1 = null;
			else
				this.param1 = rs.getString("param1").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

			if( rs.getString("OtherSign") == null )
				this.OtherSign = null;
			else
				this.OtherSign = rs.getString("OtherSign").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDParamSchema getSchema()
	{
		LDParamSchema aLDParamSchema = new LDParamSchema();
		aLDParamSchema.setSchema(this);
		return aLDParamSchema;
	}

	public LDParamDB getDB()
	{
		LDParamDB aDBOper = new LDParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ParamType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Param)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(param1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherSign));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ParamType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Param = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			param1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDParamSchema";
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
		if (FCode.equalsIgnoreCase("ParamType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamType));
		}
		if (FCode.equalsIgnoreCase("Param"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Param));
		}
		if (FCode.equalsIgnoreCase("param1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(param1));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
		}
		if (FCode.equalsIgnoreCase("OtherSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherSign));
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
				strFieldValue = StrTool.GBKToUnicode(ParamType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Param);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(param1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Noti);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherSign);
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

		if (FCode.equalsIgnoreCase("ParamType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamType = FValue.trim();
			}
			else
				ParamType = null;
		}
		if (FCode.equalsIgnoreCase("Param"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Param = FValue.trim();
			}
			else
				Param = null;
		}
		if (FCode.equalsIgnoreCase("param1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				param1 = FValue.trim();
			}
			else
				param1 = null;
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
		}
		if (FCode.equalsIgnoreCase("OtherSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherSign = FValue.trim();
			}
			else
				OtherSign = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDParamSchema other = (LDParamSchema)otherObject;
		return
			ParamType.equals(other.getParamType())
			&& Param.equals(other.getParam())
			&& param1.equals(other.getparam1())
			&& Noti.equals(other.getNoti())
			&& OtherSign.equals(other.getOtherSign());
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
		if( strFieldName.equals("ParamType") ) {
			return 0;
		}
		if( strFieldName.equals("Param") ) {
			return 1;
		}
		if( strFieldName.equals("param1") ) {
			return 2;
		}
		if( strFieldName.equals("Noti") ) {
			return 3;
		}
		if( strFieldName.equals("OtherSign") ) {
			return 4;
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
				strFieldName = "ParamType";
				break;
			case 1:
				strFieldName = "Param";
				break;
			case 2:
				strFieldName = "param1";
				break;
			case 3:
				strFieldName = "Noti";
				break;
			case 4:
				strFieldName = "OtherSign";
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
		if( strFieldName.equals("ParamType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Param") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("param1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherSign") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
