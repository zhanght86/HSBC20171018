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
import com.sinosoft.lis.db.LMEdorWTDB;

/*
 * <p>ClassName: LMEdorWTSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LMEdorWTSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMEdorWTSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 犹豫期是否允许通融退保 */
	private String HesitateFlag;
	/** 犹豫期退保控制范围类型 */
	private String HesitateType;
	/** 犹豫期退保控制范围,起点 */
	private int HesitateStart;
	/** 犹豫期退保控制范围,终点 */
	private int HesitateEnd;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMEdorWTSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "RiskVersion";

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
		LMEdorWTSchema cloned = (LMEdorWTSchema)super.clone();
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
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	public String getHesitateFlag()
	{
		return HesitateFlag;
	}
	public void setHesitateFlag(String aHesitateFlag)
	{
		HesitateFlag = aHesitateFlag;
	}
	/**
	* year<p>
	* month<p>
	* day
	*/
	public String getHesitateType()
	{
		return HesitateType;
	}
	public void setHesitateType(String aHesitateType)
	{
		HesitateType = aHesitateType;
	}
	public int getHesitateStart()
	{
		return HesitateStart;
	}
	public void setHesitateStart(int aHesitateStart)
	{
		HesitateStart = aHesitateStart;
	}
	public void setHesitateStart(String aHesitateStart)
	{
		if (aHesitateStart != null && !aHesitateStart.equals(""))
		{
			Integer tInteger = new Integer(aHesitateStart);
			int i = tInteger.intValue();
			HesitateStart = i;
		}
	}

	public int getHesitateEnd()
	{
		return HesitateEnd;
	}
	public void setHesitateEnd(int aHesitateEnd)
	{
		HesitateEnd = aHesitateEnd;
	}
	public void setHesitateEnd(String aHesitateEnd)
	{
		if (aHesitateEnd != null && !aHesitateEnd.equals(""))
		{
			Integer tInteger = new Integer(aHesitateEnd);
			int i = tInteger.intValue();
			HesitateEnd = i;
		}
	}


	/**
	* 使用另外一个 LMEdorWTSchema 对象给 Schema 赋值
	* @param: aLMEdorWTSchema LMEdorWTSchema
	**/
	public void setSchema(LMEdorWTSchema aLMEdorWTSchema)
	{
		this.RiskCode = aLMEdorWTSchema.getRiskCode();
		this.RiskVersion = aLMEdorWTSchema.getRiskVersion();
		this.HesitateFlag = aLMEdorWTSchema.getHesitateFlag();
		this.HesitateType = aLMEdorWTSchema.getHesitateType();
		this.HesitateStart = aLMEdorWTSchema.getHesitateStart();
		this.HesitateEnd = aLMEdorWTSchema.getHesitateEnd();
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

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("HesitateFlag") == null )
				this.HesitateFlag = null;
			else
				this.HesitateFlag = rs.getString("HesitateFlag").trim();

			if( rs.getString("HesitateType") == null )
				this.HesitateType = null;
			else
				this.HesitateType = rs.getString("HesitateType").trim();

			this.HesitateStart = rs.getInt("HesitateStart");
			this.HesitateEnd = rs.getInt("HesitateEnd");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMEdorWT表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorWTSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMEdorWTSchema getSchema()
	{
		LMEdorWTSchema aLMEdorWTSchema = new LMEdorWTSchema();
		aLMEdorWTSchema.setSchema(this);
		return aLMEdorWTSchema;
	}

	public LMEdorWTDB getDB()
	{
		LMEdorWTDB aDBOper = new LMEdorWTDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorWT描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HesitateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HesitateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HesitateStart));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HesitateEnd));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorWT>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			HesitateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			HesitateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			HesitateStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			HesitateEnd= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorWTSchema";
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("HesitateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HesitateFlag));
		}
		if (FCode.equalsIgnoreCase("HesitateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HesitateType));
		}
		if (FCode.equalsIgnoreCase("HesitateStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HesitateStart));
		}
		if (FCode.equalsIgnoreCase("HesitateEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HesitateEnd));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(HesitateFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(HesitateType);
				break;
			case 4:
				strFieldValue = String.valueOf(HesitateStart);
				break;
			case 5:
				strFieldValue = String.valueOf(HesitateEnd);
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("HesitateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HesitateFlag = FValue.trim();
			}
			else
				HesitateFlag = null;
		}
		if (FCode.equalsIgnoreCase("HesitateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HesitateType = FValue.trim();
			}
			else
				HesitateType = null;
		}
		if (FCode.equalsIgnoreCase("HesitateStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				HesitateStart = i;
			}
		}
		if (FCode.equalsIgnoreCase("HesitateEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				HesitateEnd = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMEdorWTSchema other = (LMEdorWTSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& HesitateFlag.equals(other.getHesitateFlag())
			&& HesitateType.equals(other.getHesitateType())
			&& HesitateStart == other.getHesitateStart()
			&& HesitateEnd == other.getHesitateEnd();
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
		if( strFieldName.equals("RiskVersion") ) {
			return 1;
		}
		if( strFieldName.equals("HesitateFlag") ) {
			return 2;
		}
		if( strFieldName.equals("HesitateType") ) {
			return 3;
		}
		if( strFieldName.equals("HesitateStart") ) {
			return 4;
		}
		if( strFieldName.equals("HesitateEnd") ) {
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
				strFieldName = "RiskVersion";
				break;
			case 2:
				strFieldName = "HesitateFlag";
				break;
			case 3:
				strFieldName = "HesitateType";
				break;
			case 4:
				strFieldName = "HesitateStart";
				break;
			case 5:
				strFieldName = "HesitateEnd";
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
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HesitateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HesitateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HesitateStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("HesitateEnd") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
