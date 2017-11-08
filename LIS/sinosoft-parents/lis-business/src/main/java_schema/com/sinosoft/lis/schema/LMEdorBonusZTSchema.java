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
import com.sinosoft.lis.db.LMEdorBonusZTDB;

/*
 * <p>ClassName: LMEdorBonusZTSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMEdorBonusZTSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMEdorBonusZTSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 期缴时间间隔 */
	private String CycPayIntvType;
	/** 算法编码 */
	private String CycPayCalCode;
	/** 退保生存金计算类型 */
	private String LiveGetType;
	/** 计算方式参考 */
	private String CalCodeType;
	/** 退保年度计算类型 */
	private String ZTYearType;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMEdorBonusZTSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "CycPayIntvType";

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
		LMEdorBonusZTSchema cloned = (LMEdorBonusZTSchema)super.clone();
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
	public String getCycPayIntvType()
	{
		return CycPayIntvType;
	}
	public void setCycPayIntvType(String aCycPayIntvType)
	{
		CycPayIntvType = aCycPayIntvType;
	}
	public String getCycPayCalCode()
	{
		return CycPayCalCode;
	}
	public void setCycPayCalCode(String aCycPayCalCode)
	{
		CycPayCalCode = aCycPayCalCode;
	}
	public String getLiveGetType()
	{
		return LiveGetType;
	}
	public void setLiveGetType(String aLiveGetType)
	{
		LiveGetType = aLiveGetType;
	}
	public String getCalCodeType()
	{
		return CalCodeType;
	}
	public void setCalCodeType(String aCalCodeType)
	{
		CalCodeType = aCalCodeType;
	}
	public String getZTYearType()
	{
		return ZTYearType;
	}
	public void setZTYearType(String aZTYearType)
	{
		ZTYearType = aZTYearType;
	}

	/**
	* 使用另外一个 LMEdorBonusZTSchema 对象给 Schema 赋值
	* @param: aLMEdorBonusZTSchema LMEdorBonusZTSchema
	**/
	public void setSchema(LMEdorBonusZTSchema aLMEdorBonusZTSchema)
	{
		this.RiskCode = aLMEdorBonusZTSchema.getRiskCode();
		this.DutyCode = aLMEdorBonusZTSchema.getDutyCode();
		this.CycPayIntvType = aLMEdorBonusZTSchema.getCycPayIntvType();
		this.CycPayCalCode = aLMEdorBonusZTSchema.getCycPayCalCode();
		this.LiveGetType = aLMEdorBonusZTSchema.getLiveGetType();
		this.CalCodeType = aLMEdorBonusZTSchema.getCalCodeType();
		this.ZTYearType = aLMEdorBonusZTSchema.getZTYearType();
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

			if( rs.getString("CycPayIntvType") == null )
				this.CycPayIntvType = null;
			else
				this.CycPayIntvType = rs.getString("CycPayIntvType").trim();

			if( rs.getString("CycPayCalCode") == null )
				this.CycPayCalCode = null;
			else
				this.CycPayCalCode = rs.getString("CycPayCalCode").trim();

			if( rs.getString("LiveGetType") == null )
				this.LiveGetType = null;
			else
				this.LiveGetType = rs.getString("LiveGetType").trim();

			if( rs.getString("CalCodeType") == null )
				this.CalCodeType = null;
			else
				this.CalCodeType = rs.getString("CalCodeType").trim();

			if( rs.getString("ZTYearType") == null )
				this.ZTYearType = null;
			else
				this.ZTYearType = rs.getString("ZTYearType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMEdorBonusZT表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorBonusZTSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMEdorBonusZTSchema getSchema()
	{
		LMEdorBonusZTSchema aLMEdorBonusZTSchema = new LMEdorBonusZTSchema();
		aLMEdorBonusZTSchema.setSchema(this);
		return aLMEdorBonusZTSchema;
	}

	public LMEdorBonusZTDB getDB()
	{
		LMEdorBonusZTDB aDBOper = new LMEdorBonusZTDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorBonusZT描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayIntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZTYearType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorBonusZT>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CycPayIntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CycPayCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LiveGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ZTYearType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorBonusZTSchema";
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
		if (FCode.equalsIgnoreCase("CycPayIntvType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycPayIntvType));
		}
		if (FCode.equalsIgnoreCase("CycPayCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycPayCalCode));
		}
		if (FCode.equalsIgnoreCase("LiveGetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveGetType));
		}
		if (FCode.equalsIgnoreCase("CalCodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeType));
		}
		if (FCode.equalsIgnoreCase("ZTYearType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZTYearType));
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
				strFieldValue = StrTool.GBKToUnicode(CycPayIntvType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CycPayCalCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LiveGetType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCodeType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ZTYearType);
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
		if (FCode.equalsIgnoreCase("CycPayIntvType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycPayIntvType = FValue.trim();
			}
			else
				CycPayIntvType = null;
		}
		if (FCode.equalsIgnoreCase("CycPayCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycPayCalCode = FValue.trim();
			}
			else
				CycPayCalCode = null;
		}
		if (FCode.equalsIgnoreCase("LiveGetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveGetType = FValue.trim();
			}
			else
				LiveGetType = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeType = FValue.trim();
			}
			else
				CalCodeType = null;
		}
		if (FCode.equalsIgnoreCase("ZTYearType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZTYearType = FValue.trim();
			}
			else
				ZTYearType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMEdorBonusZTSchema other = (LMEdorBonusZTSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& CycPayIntvType.equals(other.getCycPayIntvType())
			&& CycPayCalCode.equals(other.getCycPayCalCode())
			&& LiveGetType.equals(other.getLiveGetType())
			&& CalCodeType.equals(other.getCalCodeType())
			&& ZTYearType.equals(other.getZTYearType());
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
		if( strFieldName.equals("CycPayIntvType") ) {
			return 2;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return 3;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return 4;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return 5;
		}
		if( strFieldName.equals("ZTYearType") ) {
			return 6;
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
				strFieldName = "CycPayIntvType";
				break;
			case 3:
				strFieldName = "CycPayCalCode";
				break;
			case 4:
				strFieldName = "LiveGetType";
				break;
			case 5:
				strFieldName = "CalCodeType";
				break;
			case 6:
				strFieldName = "ZTYearType";
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
		if( strFieldName.equals("CycPayIntvType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZTYearType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
