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
import com.sinosoft.lis.db.LMEdorZT1DB;

/*
 * <p>ClassName: LMEdorZT1Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMEdorZT1Schema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMEdorZT1Schema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 退保计算类型 */
	private String SurrCalType;
	/** 期缴时间间隔 */
	private String CycPayIntvType;
	/** 算法编码 */
	private String CycPayCalCode;
	/** 退保生存金计算类型 */
	private String LiveGetType;
	/** 死亡退保金计算类型 */
	private String DeadGetType;
	/** 计算方式参考 */
	private String CalCodeType;
	/** 退保年度计算类型 */
	private String ZTYearType;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMEdorZT1Schema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "PayPlanCode";
		pk[3] = "SurrCalType";
		pk[4] = "CycPayIntvType";

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
		LMEdorZT1Schema cloned = (LMEdorZT1Schema)super.clone();
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
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getSurrCalType()
	{
		return SurrCalType;
	}
	public void setSurrCalType(String aSurrCalType)
	{
		SurrCalType = aSurrCalType;
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
	public String getDeadGetType()
	{
		return DeadGetType;
	}
	public void setDeadGetType(String aDeadGetType)
	{
		DeadGetType = aDeadGetType;
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
	* 使用另外一个 LMEdorZT1Schema 对象给 Schema 赋值
	* @param: aLMEdorZT1Schema LMEdorZT1Schema
	**/
	public void setSchema(LMEdorZT1Schema aLMEdorZT1Schema)
	{
		this.RiskCode = aLMEdorZT1Schema.getRiskCode();
		this.DutyCode = aLMEdorZT1Schema.getDutyCode();
		this.PayPlanCode = aLMEdorZT1Schema.getPayPlanCode();
		this.SurrCalType = aLMEdorZT1Schema.getSurrCalType();
		this.CycPayIntvType = aLMEdorZT1Schema.getCycPayIntvType();
		this.CycPayCalCode = aLMEdorZT1Schema.getCycPayCalCode();
		this.LiveGetType = aLMEdorZT1Schema.getLiveGetType();
		this.DeadGetType = aLMEdorZT1Schema.getDeadGetType();
		this.CalCodeType = aLMEdorZT1Schema.getCalCodeType();
		this.ZTYearType = aLMEdorZT1Schema.getZTYearType();
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

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("SurrCalType") == null )
				this.SurrCalType = null;
			else
				this.SurrCalType = rs.getString("SurrCalType").trim();

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

			if( rs.getString("DeadGetType") == null )
				this.DeadGetType = null;
			else
				this.DeadGetType = rs.getString("DeadGetType").trim();

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
			logger.debug("数据库中的LMEdorZT1表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorZT1Schema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMEdorZT1Schema getSchema()
	{
		LMEdorZT1Schema aLMEdorZT1Schema = new LMEdorZT1Schema();
		aLMEdorZT1Schema.setSchema(this);
		return aLMEdorZT1Schema;
	}

	public LMEdorZT1DB getDB()
	{
		LMEdorZT1DB aDBOper = new LMEdorZT1DB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorZT1描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurrCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayIntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycPayCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadGetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZTYearType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorZT1>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SurrCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CycPayIntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CycPayCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LiveGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DeadGetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ZTYearType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorZT1Schema";
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("SurrCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurrCalType));
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
		if (FCode.equalsIgnoreCase("DeadGetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadGetType));
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
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SurrCalType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CycPayIntvType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CycPayCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(LiveGetType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DeadGetType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCodeType);
				break;
			case 9:
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("SurrCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurrCalType = FValue.trim();
			}
			else
				SurrCalType = null;
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
		if (FCode.equalsIgnoreCase("DeadGetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadGetType = FValue.trim();
			}
			else
				DeadGetType = null;
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
		LMEdorZT1Schema other = (LMEdorZT1Schema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& SurrCalType.equals(other.getSurrCalType())
			&& CycPayIntvType.equals(other.getCycPayIntvType())
			&& CycPayCalCode.equals(other.getCycPayCalCode())
			&& LiveGetType.equals(other.getLiveGetType())
			&& DeadGetType.equals(other.getDeadGetType())
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
		if( strFieldName.equals("PayPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("SurrCalType") ) {
			return 3;
		}
		if( strFieldName.equals("CycPayIntvType") ) {
			return 4;
		}
		if( strFieldName.equals("CycPayCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("LiveGetType") ) {
			return 6;
		}
		if( strFieldName.equals("DeadGetType") ) {
			return 7;
		}
		if( strFieldName.equals("CalCodeType") ) {
			return 8;
		}
		if( strFieldName.equals("ZTYearType") ) {
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
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "PayPlanCode";
				break;
			case 3:
				strFieldName = "SurrCalType";
				break;
			case 4:
				strFieldName = "CycPayIntvType";
				break;
			case 5:
				strFieldName = "CycPayCalCode";
				break;
			case 6:
				strFieldName = "LiveGetType";
				break;
			case 7:
				strFieldName = "DeadGetType";
				break;
			case 8:
				strFieldName = "CalCodeType";
				break;
			case 9:
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
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurrCalType") ) {
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
		if( strFieldName.equals("DeadGetType") ) {
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
			case 7:
				nFieldType = Schema.TYPE_STRING;
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
