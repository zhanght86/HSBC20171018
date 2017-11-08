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
import com.sinosoft.lis.db.LDMaxNoRuleDB;

/*
 * <p>ClassName: LDMaxNoRuleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 号段管理
 */
public class LDMaxNoRuleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMaxNoRuleSchema.class);
	// @Field
	/** 号段编码 */
	private String NoCode;
	/** 顺序 */
	private int IDX;
	/** 要素编码 */
	private String Code;
	/** 实例编码 */
	private String RuleCode;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMaxNoRuleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "NoCode";
		pk[1] = "IDX";
		pk[2] = "Code";
		pk[3] = "RuleCode";

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
		LDMaxNoRuleSchema cloned = (LDMaxNoRuleSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getNoCode()
	{
		return NoCode;
	}
	public void setNoCode(String aNoCode)
	{
		if(aNoCode!=null && aNoCode.length()>100)
			throw new IllegalArgumentException("号段编码NoCode值"+aNoCode+"的长度"+aNoCode.length()+"大于最大值100");
		NoCode = aNoCode;
	}
	public int getIDX()
	{
		return IDX;
	}
	public void setIDX(int aIDX)
	{
		IDX = aIDX;
	}
	public void setIDX(String aIDX)
	{
		if (aIDX != null && !aIDX.equals(""))
		{
			Integer tInteger = new Integer(aIDX);
			int i = tInteger.intValue();
			IDX = i;
		}
	}

	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		if(aCode!=null && aCode.length()>100)
			throw new IllegalArgumentException("要素编码Code值"+aCode+"的长度"+aCode.length()+"大于最大值100");
		Code = aCode;
	}
	public String getRuleCode()
	{
		return RuleCode;
	}
	public void setRuleCode(String aRuleCode)
	{
		if(aRuleCode!=null && aRuleCode.length()>100)
			throw new IllegalArgumentException("实例编码RuleCode值"+aRuleCode+"的长度"+aRuleCode.length()+"大于最大值100");
		RuleCode = aRuleCode;
	}

	/**
	* 使用另外一个 LDMaxNoRuleSchema 对象给 Schema 赋值
	* @param: aLDMaxNoRuleSchema LDMaxNoRuleSchema
	**/
	public void setSchema(LDMaxNoRuleSchema aLDMaxNoRuleSchema)
	{
		this.NoCode = aLDMaxNoRuleSchema.getNoCode();
		this.IDX = aLDMaxNoRuleSchema.getIDX();
		this.Code = aLDMaxNoRuleSchema.getCode();
		this.RuleCode = aLDMaxNoRuleSchema.getRuleCode();
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
			if( rs.getString("NoCode") == null )
				this.NoCode = null;
			else
				this.NoCode = rs.getString("NoCode").trim();

			this.IDX = rs.getInt("IDX");
			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("RuleCode") == null )
				this.RuleCode = null;
			else
				this.RuleCode = rs.getString("RuleCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMaxNoRule表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoRuleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMaxNoRuleSchema getSchema()
	{
		LDMaxNoRuleSchema aLDMaxNoRuleSchema = new LDMaxNoRuleSchema();
		aLDMaxNoRuleSchema.setSchema(this);
		return aLDMaxNoRuleSchema;
	}

	public LDMaxNoRuleDB getDB()
	{
		LDMaxNoRuleDB aDBOper = new LDMaxNoRuleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNoRule描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(NoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(IDX));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNoRule>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			IDX= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoRuleSchema";
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
		if (FCode.equalsIgnoreCase("NoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoCode));
		}
		if (FCode.equalsIgnoreCase("IDX"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDX));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCode));
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
				strFieldValue = StrTool.GBKToUnicode(NoCode);
				break;
			case 1:
				strFieldValue = String.valueOf(IDX);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
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

		if (FCode.equalsIgnoreCase("NoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoCode = FValue.trim();
			}
			else
				NoCode = null;
		}
		if (FCode.equalsIgnoreCase("IDX"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				IDX = i;
			}
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
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCode = FValue.trim();
			}
			else
				RuleCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMaxNoRuleSchema other = (LDMaxNoRuleSchema)otherObject;
		return
			NoCode.equals(other.getNoCode())
			&& IDX == other.getIDX()
			&& Code.equals(other.getCode())
			&& RuleCode.equals(other.getRuleCode());
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
		if( strFieldName.equals("NoCode") ) {
			return 0;
		}
		if( strFieldName.equals("IDX") ) {
			return 1;
		}
		if( strFieldName.equals("Code") ) {
			return 2;
		}
		if( strFieldName.equals("RuleCode") ) {
			return 3;
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
				strFieldName = "NoCode";
				break;
			case 1:
				strFieldName = "IDX";
				break;
			case 2:
				strFieldName = "Code";
				break;
			case 3:
				strFieldName = "RuleCode";
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
		if( strFieldName.equals("NoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDX") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleCode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
