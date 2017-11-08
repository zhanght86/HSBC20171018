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
import com.sinosoft.lis.db.LMFactoryModeDB;

/*
 * <p>ClassName: LMFactoryModeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LMFactoryModeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMFactoryModeSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 要素类型 */
	private String FactoryType;
	/** 计算编码 */
	private String FactoryCode;
	/** 计算名称 */
	private String FactoryName;
	/** 计算子编码 */
	private int FactorySubCode;
	/** 计算子名称 */
	private String FactorySubName;
	/** 计算sql */
	private String CalSql;
	/** 参数个数 */
	private int ParamsNum;
	/** 参数展现形式 */
	private String Params;
	/** 算法备注 */
	private String CalRemark;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMFactoryModeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "FactoryType";
		pk[2] = "FactoryCode";
		pk[3] = "FactorySubCode";

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
		LMFactoryModeSchema cloned = (LMFactoryModeSchema)super.clone();
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
	* 000002 保单给付要素<p>
	* 000003 保单账户要素<p>
	* 000004 理赔计算要素
	*/
	public String getFactoryType()
	{
		return FactoryType;
	}
	public void setFactoryType(String aFactoryType)
	{
		FactoryType = aFactoryType;
	}
	/**
	* 参照LDCode表中的codetype='healthfactory'的记录<p>
	* 的Code列
	*/
	public String getFactoryCode()
	{
		return FactoryCode;
	}
	public void setFactoryCode(String aFactoryCode)
	{
		FactoryCode = aFactoryCode;
	}
	public String getFactoryName()
	{
		return FactoryName;
	}
	public void setFactoryName(String aFactoryName)
	{
		FactoryName = aFactoryName;
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

	/**
	* 参照LDCode表中的codetype='healthfactory'的对应CalCode的记录的<p>
	* CodeName列
	*/
	public String getFactorySubName()
	{
		return FactorySubName;
	}
	public void setFactorySubName(String aFactorySubName)
	{
		FactorySubName = aFactorySubName;
	}
	/**
	* SQL模板
	*/
	public String getCalSql()
	{
		return CalSql;
	}
	public void setCalSql(String aCalSql)
	{
		CalSql = aCalSql;
	}
	/**
	* 参数个数
	*/
	public int getParamsNum()
	{
		return ParamsNum;
	}
	public void setParamsNum(int aParamsNum)
	{
		ParamsNum = aParamsNum;
	}
	public void setParamsNum(String aParamsNum)
	{
		if (aParamsNum != null && !aParamsNum.equals(""))
		{
			Integer tInteger = new Integer(aParamsNum);
			int i = tInteger.intValue();
			ParamsNum = i;
		}
	}

	public String getParams()
	{
		return Params;
	}
	public void setParams(String aParams)
	{
		Params = aParams;
	}
	/**
	* 参数个数
	*/
	public String getCalRemark()
	{
		return CalRemark;
	}
	public void setCalRemark(String aCalRemark)
	{
		CalRemark = aCalRemark;
	}

	/**
	* 使用另外一个 LMFactoryModeSchema 对象给 Schema 赋值
	* @param: aLMFactoryModeSchema LMFactoryModeSchema
	**/
	public void setSchema(LMFactoryModeSchema aLMFactoryModeSchema)
	{
		this.RiskCode = aLMFactoryModeSchema.getRiskCode();
		this.FactoryType = aLMFactoryModeSchema.getFactoryType();
		this.FactoryCode = aLMFactoryModeSchema.getFactoryCode();
		this.FactoryName = aLMFactoryModeSchema.getFactoryName();
		this.FactorySubCode = aLMFactoryModeSchema.getFactorySubCode();
		this.FactorySubName = aLMFactoryModeSchema.getFactorySubName();
		this.CalSql = aLMFactoryModeSchema.getCalSql();
		this.ParamsNum = aLMFactoryModeSchema.getParamsNum();
		this.Params = aLMFactoryModeSchema.getParams();
		this.CalRemark = aLMFactoryModeSchema.getCalRemark();
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

			if( rs.getString("FactoryName") == null )
				this.FactoryName = null;
			else
				this.FactoryName = rs.getString("FactoryName").trim();

			this.FactorySubCode = rs.getInt("FactorySubCode");
			if( rs.getString("FactorySubName") == null )
				this.FactorySubName = null;
			else
				this.FactorySubName = rs.getString("FactorySubName").trim();

			if( rs.getString("CalSql") == null )
				this.CalSql = null;
			else
				this.CalSql = rs.getString("CalSql").trim();

			this.ParamsNum = rs.getInt("ParamsNum");
			if( rs.getString("Params") == null )
				this.Params = null;
			else
				this.Params = rs.getString("Params").trim();

			if( rs.getString("CalRemark") == null )
				this.CalRemark = null;
			else
				this.CalRemark = rs.getString("CalRemark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMFactoryMode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactoryModeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMFactoryModeSchema getSchema()
	{
		LMFactoryModeSchema aLMFactoryModeSchema = new LMFactoryModeSchema();
		aLMFactoryModeSchema.setSchema(this);
		return aLMFactoryModeSchema;
	}

	public LMFactoryModeDB getDB()
	{
		LMFactoryModeDB aDBOper = new LMFactoryModeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFactoryMode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorySubCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorySubName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSql)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamsNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Params)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalRemark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFactoryMode>历史记账凭证主表信息</A>表字段
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
			FactoryName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FactorySubCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			FactorySubName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ParamsNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			Params = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactoryModeSchema";
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
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryName));
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubCode));
		}
		if (FCode.equalsIgnoreCase("FactorySubName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubName));
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSql));
		}
		if (FCode.equalsIgnoreCase("ParamsNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsNum));
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Params));
		}
		if (FCode.equalsIgnoreCase("CalRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalRemark));
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
				strFieldValue = StrTool.GBKToUnicode(FactoryName);
				break;
			case 4:
				strFieldValue = String.valueOf(FactorySubCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactorySubName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalSql);
				break;
			case 7:
				strFieldValue = String.valueOf(ParamsNum);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Params);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalRemark);
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
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryName = FValue.trim();
			}
			else
				FactoryName = null;
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
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSql = FValue.trim();
			}
			else
				CalSql = null;
		}
		if (FCode.equalsIgnoreCase("ParamsNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ParamsNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Params = FValue.trim();
			}
			else
				Params = null;
		}
		if (FCode.equalsIgnoreCase("CalRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalRemark = FValue.trim();
			}
			else
				CalRemark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMFactoryModeSchema other = (LMFactoryModeSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& FactoryType.equals(other.getFactoryType())
			&& FactoryCode.equals(other.getFactoryCode())
			&& FactoryName.equals(other.getFactoryName())
			&& FactorySubCode == other.getFactorySubCode()
			&& FactorySubName.equals(other.getFactorySubName())
			&& CalSql.equals(other.getCalSql())
			&& ParamsNum == other.getParamsNum()
			&& Params.equals(other.getParams())
			&& CalRemark.equals(other.getCalRemark());
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
		if( strFieldName.equals("FactoryName") ) {
			return 3;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return 4;
		}
		if( strFieldName.equals("FactorySubName") ) {
			return 5;
		}
		if( strFieldName.equals("CalSql") ) {
			return 6;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return 7;
		}
		if( strFieldName.equals("Params") ) {
			return 8;
		}
		if( strFieldName.equals("CalRemark") ) {
			return 9;
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
				strFieldName = "FactoryName";
				break;
			case 4:
				strFieldName = "FactorySubCode";
				break;
			case 5:
				strFieldName = "FactorySubName";
				break;
			case 6:
				strFieldName = "CalSql";
				break;
			case 7:
				strFieldName = "ParamsNum";
				break;
			case 8:
				strFieldName = "Params";
				break;
			case 9:
				strFieldName = "CalRemark";
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
		if( strFieldName.equals("FactoryName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FactorySubName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Params") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalRemark") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
