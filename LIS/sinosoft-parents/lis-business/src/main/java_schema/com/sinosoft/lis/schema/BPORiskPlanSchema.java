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
import com.sinosoft.lis.db.BPORiskPlanDB;

/*
 * <p>ClassName: BPORiskPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 录入外包（lis6.0）
 */
public class BPORiskPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(BPORiskPlanSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 计划名称 */
	private String PlanName;
	/** 计划要素 */
	private String PlanFactor;
	/** 计算类型 */
	private String CalType;
	/** 计算编码 */
	private String CalCode;
	/** 默认值 */
	private String DefaultValue;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public BPORiskPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "PlanName";
		pk[2] = "PlanFactor";

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
		BPORiskPlanSchema cloned = (BPORiskPlanSchema)super.clone();
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
	public String getPlanName()
	{
		return PlanName;
	}
	public void setPlanName(String aPlanName)
	{
		PlanName = aPlanName;
	}
	public String getPlanFactor()
	{
		return PlanFactor;
	}
	public void setPlanFactor(String aPlanFactor)
	{
		PlanFactor = aPlanFactor;
	}
	/**
	* 计算”计划要素“值的方式：<p>
	* 0-无需计算,直接取DefaultValue的值<p>
	* 1－需要计算，计算编码为CalCode
	*/
	public String getCalType()
	{
		return CalType;
	}
	public void setCalType(String aCalType)
	{
		CalType = aCalType;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 BPORiskPlanSchema 对象给 Schema 赋值
	* @param: aBPORiskPlanSchema BPORiskPlanSchema
	**/
	public void setSchema(BPORiskPlanSchema aBPORiskPlanSchema)
	{
		this.RiskCode = aBPORiskPlanSchema.getRiskCode();
		this.PlanName = aBPORiskPlanSchema.getPlanName();
		this.PlanFactor = aBPORiskPlanSchema.getPlanFactor();
		this.CalType = aBPORiskPlanSchema.getCalType();
		this.CalCode = aBPORiskPlanSchema.getCalCode();
		this.DefaultValue = aBPORiskPlanSchema.getDefaultValue();
		this.Remark = aBPORiskPlanSchema.getRemark();
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

			if( rs.getString("PlanName") == null )
				this.PlanName = null;
			else
				this.PlanName = rs.getString("PlanName").trim();

			if( rs.getString("PlanFactor") == null )
				this.PlanFactor = null;
			else
				this.PlanFactor = rs.getString("PlanFactor").trim();

			if( rs.getString("CalType") == null )
				this.CalType = null;
			else
				this.CalType = rs.getString("CalType").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("DefaultValue") == null )
				this.DefaultValue = null;
			else
				this.DefaultValue = rs.getString("DefaultValue").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的BPORiskPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPORiskPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public BPORiskPlanSchema getSchema()
	{
		BPORiskPlanSchema aBPORiskPlanSchema = new BPORiskPlanSchema();
		aBPORiskPlanSchema.setSchema(this);
		return aBPORiskPlanSchema;
	}

	public BPORiskPlanDB getDB()
	{
		BPORiskPlanDB aDBOper = new BPORiskPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPORiskPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanFactor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPORiskPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlanFactor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DefaultValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPORiskPlanSchema";
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
		if (FCode.equalsIgnoreCase("PlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanName));
		}
		if (FCode.equalsIgnoreCase("PlanFactor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanFactor));
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalType));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(PlanName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlanFactor);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DefaultValue);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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
		if (FCode.equalsIgnoreCase("PlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanName = FValue.trim();
			}
			else
				PlanName = null;
		}
		if (FCode.equalsIgnoreCase("PlanFactor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanFactor = FValue.trim();
			}
			else
				PlanFactor = null;
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalType = FValue.trim();
			}
			else
				CalType = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultValue = FValue.trim();
			}
			else
				DefaultValue = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		BPORiskPlanSchema other = (BPORiskPlanSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& PlanName.equals(other.getPlanName())
			&& PlanFactor.equals(other.getPlanFactor())
			&& CalType.equals(other.getCalType())
			&& CalCode.equals(other.getCalCode())
			&& DefaultValue.equals(other.getDefaultValue())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("PlanName") ) {
			return 1;
		}
		if( strFieldName.equals("PlanFactor") ) {
			return 2;
		}
		if( strFieldName.equals("CalType") ) {
			return 3;
		}
		if( strFieldName.equals("CalCode") ) {
			return 4;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 5;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "PlanName";
				break;
			case 2:
				strFieldName = "PlanFactor";
				break;
			case 3:
				strFieldName = "CalType";
				break;
			case 4:
				strFieldName = "CalCode";
				break;
			case 5:
				strFieldName = "DefaultValue";
				break;
			case 6:
				strFieldName = "Remark";
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
		if( strFieldName.equals("PlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanFactor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
