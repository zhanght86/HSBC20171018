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
import com.sinosoft.lis.db.LMRiskParamsDefDB;

/*
 * <p>ClassName: LMRiskParamsDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskParamsDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskParamsDefSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 责任代码 */
	private String DutyCode;
	/** 其他编码 */
	private String OtherCode;
	/** 控制类型 */
	private String CtrlType;
	/** 险种参数名 */
	private String ParamsType;
	/** 险种参数值 */
	private String ParamsCode;
	/** 险种参数显示 */
	private String ParamsName;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskParamsDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "OtherCode";
		pk[3] = "ParamsType";
		pk[4] = "ParamsCode";

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
		LMRiskParamsDefSchema cloned = (LMRiskParamsDefSchema)super.clone();
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
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
	* (填入PayPlanCode 或GetDutyCode)*
	*/
	public String getOtherCode()
	{
		return OtherCode;
	}
	public void setOtherCode(String aOtherCode)
	{
		OtherCode = aOtherCode;
	}
	/**
	* P--保费项 G--领取项 D--责任 (分别对应上述的3个表）*
	*/
	public String getCtrlType()
	{
		return CtrlType;
	}
	public void setCtrlType(String aCtrlType)
	{
		CtrlType = aCtrlType;
	}
	/**
	* PayIntv 交费间隔 PayEndYear 缴费终止期间 PayEndYearFlag 缴费终止期间单位 Mult 份数 InsuYear 保险期间 InsuYearFlag 保险期间单位 GetDutyKind 给付方法 GetIntv 领取方式 GetYear 起领期间 GetYearFlag 起领期间单位
	*/
	public String getParamsType()
	{
		return ParamsType;
	}
	public void setParamsType(String aParamsType)
	{
		ParamsType = aParamsType;
	}
	public String getParamsCode()
	{
		return ParamsCode;
	}
	public void setParamsCode(String aParamsCode)
	{
		ParamsCode = aParamsCode;
	}
	public String getParamsName()
	{
		return ParamsName;
	}
	public void setParamsName(String aParamsName)
	{
		ParamsName = aParamsName;
	}

	/**
	* 使用另外一个 LMRiskParamsDefSchema 对象给 Schema 赋值
	* @param: aLMRiskParamsDefSchema LMRiskParamsDefSchema
	**/
	public void setSchema(LMRiskParamsDefSchema aLMRiskParamsDefSchema)
	{
		this.RiskCode = aLMRiskParamsDefSchema.getRiskCode();
		this.RiskVer = aLMRiskParamsDefSchema.getRiskVer();
		this.DutyCode = aLMRiskParamsDefSchema.getDutyCode();
		this.OtherCode = aLMRiskParamsDefSchema.getOtherCode();
		this.CtrlType = aLMRiskParamsDefSchema.getCtrlType();
		this.ParamsType = aLMRiskParamsDefSchema.getParamsType();
		this.ParamsCode = aLMRiskParamsDefSchema.getParamsCode();
		this.ParamsName = aLMRiskParamsDefSchema.getParamsName();
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("OtherCode") == null )
				this.OtherCode = null;
			else
				this.OtherCode = rs.getString("OtherCode").trim();

			if( rs.getString("CtrlType") == null )
				this.CtrlType = null;
			else
				this.CtrlType = rs.getString("CtrlType").trim();

			if( rs.getString("ParamsType") == null )
				this.ParamsType = null;
			else
				this.ParamsType = rs.getString("ParamsType").trim();

			if( rs.getString("ParamsCode") == null )
				this.ParamsCode = null;
			else
				this.ParamsCode = rs.getString("ParamsCode").trim();

			if( rs.getString("ParamsName") == null )
				this.ParamsName = null;
			else
				this.ParamsName = rs.getString("ParamsName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskParamsDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskParamsDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskParamsDefSchema getSchema()
	{
		LMRiskParamsDefSchema aLMRiskParamsDefSchema = new LMRiskParamsDefSchema();
		aLMRiskParamsDefSchema.setSchema(this);
		return aLMRiskParamsDefSchema;
	}

	public LMRiskParamsDefDB getDB()
	{
		LMRiskParamsDefDB aDBOper = new LMRiskParamsDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskParamsDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamsType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamsCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamsName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskParamsDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CtrlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ParamsType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ParamsCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ParamsName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskParamsDefSchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("OtherCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherCode));
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlType));
		}
		if (FCode.equalsIgnoreCase("ParamsType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsType));
		}
		if (FCode.equalsIgnoreCase("ParamsCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsCode));
		}
		if (FCode.equalsIgnoreCase("ParamsName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsName));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CtrlType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ParamsType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ParamsCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ParamsName);
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
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
		if (FCode.equalsIgnoreCase("OtherCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherCode = FValue.trim();
			}
			else
				OtherCode = null;
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlType = FValue.trim();
			}
			else
				CtrlType = null;
		}
		if (FCode.equalsIgnoreCase("ParamsType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsType = FValue.trim();
			}
			else
				ParamsType = null;
		}
		if (FCode.equalsIgnoreCase("ParamsCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamsCode = FValue.trim();
			}
			else
				ParamsCode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskParamsDefSchema other = (LMRiskParamsDefSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& DutyCode.equals(other.getDutyCode())
			&& OtherCode.equals(other.getOtherCode())
			&& CtrlType.equals(other.getCtrlType())
			&& ParamsType.equals(other.getParamsType())
			&& ParamsCode.equals(other.getParamsCode())
			&& ParamsName.equals(other.getParamsName());
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("OtherCode") ) {
			return 3;
		}
		if( strFieldName.equals("CtrlType") ) {
			return 4;
		}
		if( strFieldName.equals("ParamsType") ) {
			return 5;
		}
		if( strFieldName.equals("ParamsCode") ) {
			return 6;
		}
		if( strFieldName.equals("ParamsName") ) {
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
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "OtherCode";
				break;
			case 4:
				strFieldName = "CtrlType";
				break;
			case 5:
				strFieldName = "ParamsType";
				break;
			case 6:
				strFieldName = "ParamsCode";
				break;
			case 7:
				strFieldName = "ParamsName";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsName") ) {
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
