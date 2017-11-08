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
import com.sinosoft.lis.db.LMDutyPayAddFeeDB;

/*
 * <p>ClassName: LMDutyPayAddFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyPayAddFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyPayAddFeeSchema.class);

	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 加费类型 */
	private String AddFeeType;
	/** 加费对象 */
	private String AddFeeObject;
	/** 加费算法 */
	private String AddFeeCalCode;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyPayAddFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "AddFeeType";
		pk[3] = "AddFeeObject";

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
		LMDutyPayAddFeeSchema cloned = (LMDutyPayAddFeeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	/**
	* 00 默认 01 首期健康 02 首期职业 03 复效健康 04 复效职业*
	*/
	public String getAddFeeType()
	{
		return AddFeeType;
	}
	public void setAddFeeType(String aAddFeeType)
	{
		AddFeeType = aAddFeeType;
	}
	/**
	* 00 默认 01投保人 02单一被保险人 03多被保险人*
	*/
	public String getAddFeeObject()
	{
		return AddFeeObject;
	}
	public void setAddFeeObject(String aAddFeeObject)
	{
		AddFeeObject = aAddFeeObject;
	}
	/**
	* 描述加费公式的算法*
	*/
	public String getAddFeeCalCode()
	{
		return AddFeeCalCode;
	}
	public void setAddFeeCalCode(String aAddFeeCalCode)
	{
		AddFeeCalCode = aAddFeeCalCode;
	}

	/**
	* 使用另外一个 LMDutyPayAddFeeSchema 对象给 Schema 赋值
	* @param: aLMDutyPayAddFeeSchema LMDutyPayAddFeeSchema
	**/
	public void setSchema(LMDutyPayAddFeeSchema aLMDutyPayAddFeeSchema)
	{
		this.RiskCode = aLMDutyPayAddFeeSchema.getRiskCode();
		this.DutyCode = aLMDutyPayAddFeeSchema.getDutyCode();
		this.AddFeeType = aLMDutyPayAddFeeSchema.getAddFeeType();
		this.AddFeeObject = aLMDutyPayAddFeeSchema.getAddFeeObject();
		this.AddFeeCalCode = aLMDutyPayAddFeeSchema.getAddFeeCalCode();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("AddFeeType") == null )
				this.AddFeeType = null;
			else
				this.AddFeeType = rs.getString("AddFeeType").trim();

			if( rs.getString("AddFeeObject") == null )
				this.AddFeeObject = null;
			else
				this.AddFeeObject = rs.getString("AddFeeObject").trim();

			if( rs.getString("AddFeeCalCode") == null )
				this.AddFeeCalCode = null;
			else
				this.AddFeeCalCode = rs.getString("AddFeeCalCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyPayAddFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyPayAddFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyPayAddFeeSchema getSchema()
	{
		LMDutyPayAddFeeSchema aLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
		aLMDutyPayAddFeeSchema.setSchema(this);
		return aLMDutyPayAddFeeSchema;
	}

	public LMDutyPayAddFeeDB getDB()
	{
		LMDutyPayAddFeeDB aDBOper = new LMDutyPayAddFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyPayAddFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeObject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeCalCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyPayAddFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AddFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AddFeeObject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AddFeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyPayAddFeeSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("AddFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeType));
		}
		if (FCode.equalsIgnoreCase("AddFeeObject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeObject));
		}
		if (FCode.equalsIgnoreCase("AddFeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeCalCode));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AddFeeType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AddFeeObject);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AddFeeCalCode);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeType = FValue.trim();
			}
			else
				AddFeeType = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeObject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeObject = FValue.trim();
			}
			else
				AddFeeObject = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeCalCode = FValue.trim();
			}
			else
				AddFeeCalCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyPayAddFeeSchema other = (LMDutyPayAddFeeSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& AddFeeType.equals(other.getAddFeeType())
			&& AddFeeObject.equals(other.getAddFeeObject())
			&& AddFeeCalCode.equals(other.getAddFeeCalCode());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 1;
		}
		if( strFieldName.equals("AddFeeType") ) {
			return 2;
		}
		if( strFieldName.equals("AddFeeObject") ) {
			return 3;
		}
		if( strFieldName.equals("AddFeeCalCode") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "AddFeeType";
				break;
			case 3:
				strFieldName = "AddFeeObject";
				break;
			case 4:
				strFieldName = "AddFeeCalCode";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeObject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeCalCode") ) {
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
