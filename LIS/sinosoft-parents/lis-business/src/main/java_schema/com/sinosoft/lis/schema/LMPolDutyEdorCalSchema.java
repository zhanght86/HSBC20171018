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
import com.sinosoft.lis.db.LMPolDutyEdorCalDB;

/*
 * <p>ClassName: LMPolDutyEdorCalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMPolDutyEdorCalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMPolDutyEdorCalSchema.class);

	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 保全项目 */
	private String EdorType;
	/** 计算方向 */
	private String CalMode;
	/** 补退保费计算代码 */
	private String ChgPremCalCode;
	/** 利息计算算法 */
	private String InterestCalCode;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMPolDutyEdorCalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "EdorType";

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
		LMPolDutyEdorCalSchema cloned = (LMPolDutyEdorCalSchema)super.clone();
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
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getCalMode()
	{
		return CalMode;
	}
	public void setCalMode(String aCalMode)
	{
		CalMode = aCalMode;
	}
	public String getChgPremCalCode()
	{
		return ChgPremCalCode;
	}
	public void setChgPremCalCode(String aChgPremCalCode)
	{
		ChgPremCalCode = aChgPremCalCode;
	}
	public String getInterestCalCode()
	{
		return InterestCalCode;
	}
	public void setInterestCalCode(String aInterestCalCode)
	{
		InterestCalCode = aInterestCalCode;
	}

	/**
	* 使用另外一个 LMPolDutyEdorCalSchema 对象给 Schema 赋值
	* @param: aLMPolDutyEdorCalSchema LMPolDutyEdorCalSchema
	**/
	public void setSchema(LMPolDutyEdorCalSchema aLMPolDutyEdorCalSchema)
	{
		this.RiskCode = aLMPolDutyEdorCalSchema.getRiskCode();
		this.DutyCode = aLMPolDutyEdorCalSchema.getDutyCode();
		this.EdorType = aLMPolDutyEdorCalSchema.getEdorType();
		this.CalMode = aLMPolDutyEdorCalSchema.getCalMode();
		this.ChgPremCalCode = aLMPolDutyEdorCalSchema.getChgPremCalCode();
		this.InterestCalCode = aLMPolDutyEdorCalSchema.getInterestCalCode();
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

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("CalMode") == null )
				this.CalMode = null;
			else
				this.CalMode = rs.getString("CalMode").trim();

			if( rs.getString("ChgPremCalCode") == null )
				this.ChgPremCalCode = null;
			else
				this.ChgPremCalCode = rs.getString("ChgPremCalCode").trim();

			if( rs.getString("InterestCalCode") == null )
				this.InterestCalCode = null;
			else
				this.InterestCalCode = rs.getString("InterestCalCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMPolDutyEdorCal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMPolDutyEdorCalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMPolDutyEdorCalSchema getSchema()
	{
		LMPolDutyEdorCalSchema aLMPolDutyEdorCalSchema = new LMPolDutyEdorCalSchema();
		aLMPolDutyEdorCalSchema.setSchema(this);
		return aLMPolDutyEdorCalSchema;
	}

	public LMPolDutyEdorCalDB getDB()
	{
		LMPolDutyEdorCalDB aDBOper = new LMPolDutyEdorCalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMPolDutyEdorCal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgPremCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestCalCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMPolDutyEdorCal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChgPremCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InterestCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMPolDutyEdorCalSchema";
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("CalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalMode));
		}
		if (FCode.equalsIgnoreCase("ChgPremCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgPremCalCode));
		}
		if (FCode.equalsIgnoreCase("InterestCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestCalCode));
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
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ChgPremCalCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InterestCalCode);
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("CalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalMode = FValue.trim();
			}
			else
				CalMode = null;
		}
		if (FCode.equalsIgnoreCase("ChgPremCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgPremCalCode = FValue.trim();
			}
			else
				ChgPremCalCode = null;
		}
		if (FCode.equalsIgnoreCase("InterestCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestCalCode = FValue.trim();
			}
			else
				InterestCalCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMPolDutyEdorCalSchema other = (LMPolDutyEdorCalSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& EdorType.equals(other.getEdorType())
			&& CalMode.equals(other.getCalMode())
			&& ChgPremCalCode.equals(other.getChgPremCalCode())
			&& InterestCalCode.equals(other.getInterestCalCode());
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
		if( strFieldName.equals("EdorType") ) {
			return 2;
		}
		if( strFieldName.equals("CalMode") ) {
			return 3;
		}
		if( strFieldName.equals("ChgPremCalCode") ) {
			return 4;
		}
		if( strFieldName.equals("InterestCalCode") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "EdorType";
				break;
			case 3:
				strFieldName = "CalMode";
				break;
			case 4:
				strFieldName = "ChgPremCalCode";
				break;
			case 5:
				strFieldName = "InterestCalCode";
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
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgPremCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestCalCode") ) {
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
