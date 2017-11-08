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
import com.sinosoft.lis.db.LMRiskDutyFactorDB;

/*
 * <p>ClassName: LMRiskDutyFactorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskDutyFactorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskDutyFactorSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 计算要素 */
	private String CalFactor;
	/** 要素名称 */
	private String FactorName;
	/** 计划要素类型 */
	private String CalFactorType;
	/** 计算sql */
	private String CalSql;
	/** 可选属性 */
	private String ChooseFlag;
	/** 要素描述 */
	private String FactorNoti;
	/** 要素顺序 */
	private int FactorOrder;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskDutyFactorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "CalFactor";

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
		LMRiskDutyFactorSchema cloned = (LMRiskDutyFactorSchema)super.clone();
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
	public String getCalFactor()
	{
		return CalFactor;
	}
	public void setCalFactor(String aCalFactor)
	{
		CalFactor = aCalFactor;
	}
	public String getFactorName()
	{
		return FactorName;
	}
	public void setFactorName(String aFactorName)
	{
		FactorName = aFactorName;
	}
	/**
	* 1 直接值<p>
	* 2 算法计算结果值
	*/
	public String getCalFactorType()
	{
		return CalFactorType;
	}
	public void setCalFactorType(String aCalFactorType)
	{
		CalFactorType = aCalFactorType;
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
	* 0-保险计划<p>
	* 1-险种信息<p>
	* 2-二者都有<p>
	* 3-总单定价要素<p>
	* 4-险种定价要素<p>
	* 5-责任定价要素
	*/
	public String getChooseFlag()
	{
		return ChooseFlag;
	}
	public void setChooseFlag(String aChooseFlag)
	{
		ChooseFlag = aChooseFlag;
	}
	public String getFactorNoti()
	{
		return FactorNoti;
	}
	public void setFactorNoti(String aFactorNoti)
	{
		FactorNoti = aFactorNoti;
	}
	public int getFactorOrder()
	{
		return FactorOrder;
	}
	public void setFactorOrder(int aFactorOrder)
	{
		FactorOrder = aFactorOrder;
	}
	public void setFactorOrder(String aFactorOrder)
	{
		if (aFactorOrder != null && !aFactorOrder.equals(""))
		{
			Integer tInteger = new Integer(aFactorOrder);
			int i = tInteger.intValue();
			FactorOrder = i;
		}
	}


	/**
	* 使用另外一个 LMRiskDutyFactorSchema 对象给 Schema 赋值
	* @param: aLMRiskDutyFactorSchema LMRiskDutyFactorSchema
	**/
	public void setSchema(LMRiskDutyFactorSchema aLMRiskDutyFactorSchema)
	{
		this.RiskCode = aLMRiskDutyFactorSchema.getRiskCode();
		this.DutyCode = aLMRiskDutyFactorSchema.getDutyCode();
		this.CalFactor = aLMRiskDutyFactorSchema.getCalFactor();
		this.FactorName = aLMRiskDutyFactorSchema.getFactorName();
		this.CalFactorType = aLMRiskDutyFactorSchema.getCalFactorType();
		this.CalSql = aLMRiskDutyFactorSchema.getCalSql();
		this.ChooseFlag = aLMRiskDutyFactorSchema.getChooseFlag();
		this.FactorNoti = aLMRiskDutyFactorSchema.getFactorNoti();
		this.FactorOrder = aLMRiskDutyFactorSchema.getFactorOrder();
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

			if( rs.getString("CalFactor") == null )
				this.CalFactor = null;
			else
				this.CalFactor = rs.getString("CalFactor").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("CalFactorType") == null )
				this.CalFactorType = null;
			else
				this.CalFactorType = rs.getString("CalFactorType").trim();

			if( rs.getString("CalSql") == null )
				this.CalSql = null;
			else
				this.CalSql = rs.getString("CalSql").trim();

			if( rs.getString("ChooseFlag") == null )
				this.ChooseFlag = null;
			else
				this.ChooseFlag = rs.getString("ChooseFlag").trim();

			if( rs.getString("FactorNoti") == null )
				this.FactorNoti = null;
			else
				this.FactorNoti = rs.getString("FactorNoti").trim();

			this.FactorOrder = rs.getInt("FactorOrder");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskDutyFactor表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskDutyFactorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskDutyFactorSchema getSchema()
	{
		LMRiskDutyFactorSchema aLMRiskDutyFactorSchema = new LMRiskDutyFactorSchema();
		aLMRiskDutyFactorSchema.setSchema(this);
		return aLMRiskDutyFactorSchema;
	}

	public LMRiskDutyFactorDB getDB()
	{
		LMRiskDutyFactorDB aDBOper = new LMRiskDutyFactorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskDutyFactor描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSql)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChooseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorNoti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorOrder));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskDutyFactor>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CalFactor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalFactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ChooseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FactorNoti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FactorOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskDutyFactorSchema";
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
		if (FCode.equalsIgnoreCase("CalFactor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactor));
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equalsIgnoreCase("CalFactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactorType));
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSql));
		}
		if (FCode.equalsIgnoreCase("ChooseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChooseFlag));
		}
		if (FCode.equalsIgnoreCase("FactorNoti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorNoti));
		}
		if (FCode.equalsIgnoreCase("FactorOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorOrder));
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
				strFieldValue = StrTool.GBKToUnicode(CalFactor);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalFactorType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalSql);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ChooseFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FactorNoti);
				break;
			case 8:
				strFieldValue = String.valueOf(FactorOrder);
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
		if (FCode.equalsIgnoreCase("CalFactor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFactor = FValue.trim();
			}
			else
				CalFactor = null;
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorName = FValue.trim();
			}
			else
				FactorName = null;
		}
		if (FCode.equalsIgnoreCase("CalFactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFactorType = FValue.trim();
			}
			else
				CalFactorType = null;
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
		if (FCode.equalsIgnoreCase("ChooseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChooseFlag = FValue.trim();
			}
			else
				ChooseFlag = null;
		}
		if (FCode.equalsIgnoreCase("FactorNoti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorNoti = FValue.trim();
			}
			else
				FactorNoti = null;
		}
		if (FCode.equalsIgnoreCase("FactorOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FactorOrder = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskDutyFactorSchema other = (LMRiskDutyFactorSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& CalFactor.equals(other.getCalFactor())
			&& FactorName.equals(other.getFactorName())
			&& CalFactorType.equals(other.getCalFactorType())
			&& CalSql.equals(other.getCalSql())
			&& ChooseFlag.equals(other.getChooseFlag())
			&& FactorNoti.equals(other.getFactorNoti())
			&& FactorOrder == other.getFactorOrder();
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
		if( strFieldName.equals("CalFactor") ) {
			return 2;
		}
		if( strFieldName.equals("FactorName") ) {
			return 3;
		}
		if( strFieldName.equals("CalFactorType") ) {
			return 4;
		}
		if( strFieldName.equals("CalSql") ) {
			return 5;
		}
		if( strFieldName.equals("ChooseFlag") ) {
			return 6;
		}
		if( strFieldName.equals("FactorNoti") ) {
			return 7;
		}
		if( strFieldName.equals("FactorOrder") ) {
			return 8;
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
				strFieldName = "CalFactor";
				break;
			case 3:
				strFieldName = "FactorName";
				break;
			case 4:
				strFieldName = "CalFactorType";
				break;
			case 5:
				strFieldName = "CalSql";
				break;
			case 6:
				strFieldName = "ChooseFlag";
				break;
			case 7:
				strFieldName = "FactorNoti";
				break;
			case 8:
				strFieldName = "FactorOrder";
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
		if( strFieldName.equals("CalFactor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChooseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorNoti") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorOrder") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
