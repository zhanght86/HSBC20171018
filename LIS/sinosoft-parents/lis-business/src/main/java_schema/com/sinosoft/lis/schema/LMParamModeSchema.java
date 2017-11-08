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
import com.sinosoft.lis.db.LMParamModeDB;

/*
 * <p>ClassName: LMParamModeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LMParamModeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMParamModeSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 要素类型 */
	private String FactoryType;
	/** 计算编码 */
	private String FactoryCode;
	/** 计算子编码 */
	private int FactorySubCode;
	/** 计算子名称 */
	private String FactorySubName;
	/** 参数名 */
	private String ParamsName;
	/** 顺序号 */
	private int SequenceID;
	/** 参数说明 */
	private String ParamsDesc;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMParamModeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RiskCode";
		pk[1] = "FactoryType";
		pk[2] = "FactoryCode";
		pk[3] = "FactorySubCode";
		pk[4] = "ParamsName";

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
		LMParamModeSchema cloned = (LMParamModeSchema)super.clone();
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
	/**
	* ｓｑｌ模板类别<p>
	* 000000 保单要素<p>
	* 000001 保单责任要素<p>
	* 000002 保单给付要素
	*/
	public String getFactoryType()
	{
		return FactoryType;
	}
	public void setFactoryType(String aFactoryType)
	{
		FactoryType = aFactoryType;
	}
	public String getFactoryCode()
	{
		return FactoryCode;
	}
	public void setFactoryCode(String aFactoryCode)
	{
		FactoryCode = aFactoryCode;
	}
	public int getFactorySubCode()
	{
		return FactorySubCode;
	}
	public void setFactorySubCode(int aFactorySubCode)
	{
		FactorySubCode = aFactorySubCode;
	}
	public void setFactorySubCode(String aFactorySubCode)
	{
		if (aFactorySubCode != null && !aFactorySubCode.equals(""))
		{
			Integer tInteger = new Integer(aFactorySubCode);
			int i = tInteger.intValue();
			FactorySubCode = i;
		}
	}

	public String getFactorySubName()
	{
		return FactorySubName;
	}
	public void setFactorySubName(String aFactorySubName)
	{
		FactorySubName = aFactorySubName;
	}
	/**
	* 参数个数
	*/
	public String getParamsName()
	{
		return ParamsName;
	}
	public void setParamsName(String aParamsName)
	{
		ParamsName = aParamsName;
	}
	public int getSequenceID()
	{
		return SequenceID;
	}
	public void setSequenceID(int aSequenceID)
	{
		SequenceID = aSequenceID;
	}
	public void setSequenceID(String aSequenceID)
	{
		if (aSequenceID != null && !aSequenceID.equals(""))
		{
			Integer tInteger = new Integer(aSequenceID);
			int i = tInteger.intValue();
			SequenceID = i;
		}
	}

	public String getParamsDesc()
	{
		return ParamsDesc;
	}
	public void setParamsDesc(String aParamsDesc)
	{
		ParamsDesc = aParamsDesc;
	}

	/**
	* 使用另外一个 LMParamModeSchema 对象给 Schema 赋值
	* @param: aLMParamModeSchema LMParamModeSchema
	**/
	public void setSchema(LMParamModeSchema aLMParamModeSchema)
	{
		this.RiskCode = aLMParamModeSchema.getRiskCode();
		this.FactoryType = aLMParamModeSchema.getFactoryType();
		this.FactoryCode = aLMParamModeSchema.getFactoryCode();
		this.FactorySubCode = aLMParamModeSchema.getFactorySubCode();
		this.FactorySubName = aLMParamModeSchema.getFactorySubName();
		this.ParamsName = aLMParamModeSchema.getParamsName();
		this.SequenceID = aLMParamModeSchema.getSequenceID();
		this.ParamsDesc = aLMParamModeSchema.getParamsDesc();
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

			if( rs.getString("FactoryType") == null )
				this.FactoryType = null;
			else
				this.FactoryType = rs.getString("FactoryType").trim();

			if( rs.getString("FactoryCode") == null )
				this.FactoryCode = null;
			else
				this.FactoryCode = rs.getString("FactoryCode").trim();

			this.FactorySubCode = rs.getInt("FactorySubCode");
			if( rs.getString("FactorySubName") == null )
				this.FactorySubName = null;
			else
				this.FactorySubName = rs.getString("FactorySubName").trim();

			if( rs.getString("ParamsName") == null )
				this.ParamsName = null;
			else
				this.ParamsName = rs.getString("ParamsName").trim();

			this.SequenceID = rs.getInt("SequenceID");
			if( rs.getString("ParamsDesc") == null )
				this.ParamsDesc = null;
			else
				this.ParamsDesc = rs.getString("ParamsDesc").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMParamMode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMParamModeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMParamModeSchema getSchema()
	{
		LMParamModeSchema aLMParamModeSchema = new LMParamModeSchema();
		aLMParamModeSchema.setSchema(this);
		return aLMParamModeSchema;
	}

	public LMParamModeDB getDB()
	{
		LMParamModeDB aDBOper = new LMParamModeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMParamMode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorySubCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorySubName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamsName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SequenceID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamsDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMParamMode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactoryType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactoryCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorySubCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			FactorySubName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ParamsName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SequenceID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			ParamsDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMParamModeSchema";
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
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryType));
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryCode));
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubCode));
		}
		if (FCode.equalsIgnoreCase("FactorySubName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubName));
		}
		if (FCode.equalsIgnoreCase("ParamsName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsName));
		}
		if (FCode.equalsIgnoreCase("SequenceID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SequenceID));
		}
		if (FCode.equalsIgnoreCase("ParamsDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsDesc));
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
				strFieldValue = StrTool.GBKToUnicode(FactoryType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactoryCode);
				break;
			case 3:
				strFieldValue = String.valueOf(FactorySubCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FactorySubName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ParamsName);
				break;
			case 6:
				strFieldValue = String.valueOf(SequenceID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ParamsDesc);
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
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryType = FValue.trim();
			}
			else
				FactoryType = null;
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryCode = FValue.trim();
			}
			else
				FactoryCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FactorySubCode = i;
			}
		}
		if (FCode.equalsIgnoreCase("FactorySubName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorySubName = FValue.trim();
			}
			else
				FactorySubName = null;
		}
		if (FCode.equalsIgnoreCase("ParamsName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsName = FValue.trim();
			}
			else
				ParamsName = null;
		}
		if (FCode.equalsIgnoreCase("SequenceID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SequenceID = i;
			}
		}
		if (FCode.equalsIgnoreCase("ParamsDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsDesc = FValue.trim();
			}
			else
				ParamsDesc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMParamModeSchema other = (LMParamModeSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& FactoryType.equals(other.getFactoryType())
			&& FactoryCode.equals(other.getFactoryCode())
			&& FactorySubCode == other.getFactorySubCode()
			&& FactorySubName.equals(other.getFactorySubName())
			&& ParamsName.equals(other.getParamsName())
			&& SequenceID == other.getSequenceID()
			&& ParamsDesc.equals(other.getParamsDesc());
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
		if( strFieldName.equals("FactoryType") ) {
			return 1;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return 2;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return 3;
		}
		if( strFieldName.equals("FactorySubName") ) {
			return 4;
		}
		if( strFieldName.equals("ParamsName") ) {
			return 5;
		}
		if( strFieldName.equals("SequenceID") ) {
			return 6;
		}
		if( strFieldName.equals("ParamsDesc") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "FactoryType";
				break;
			case 2:
				strFieldName = "FactoryCode";
				break;
			case 3:
				strFieldName = "FactorySubCode";
				break;
			case 4:
				strFieldName = "FactorySubName";
				break;
			case 5:
				strFieldName = "ParamsName";
				break;
			case 6:
				strFieldName = "SequenceID";
				break;
			case 7:
				strFieldName = "ParamsDesc";
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
		if( strFieldName.equals("FactoryType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FactorySubName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SequenceID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ParamsDesc") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
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
