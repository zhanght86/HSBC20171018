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
import com.sinosoft.lis.db.LDPlanDutyParamDB;

/*
 * <p>ClassName: LDPlanDutyParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDPlanDutyParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPlanDutyParamSchema.class);

	// @Field
	/** 主险险种编码 */
	private String MainRiskCode;
	/** 主险险种版本 */
	private String MainRiskVersion;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 保险计划名称 */
	private String ContPlanName;
	/** 责任编码 */
	private String DutyCode;
	/** 计划要素 */
	private String CalFactor;
	/** 计划要素类型 */
	private String CalFactorType;
	/** 计划要素值 */
	private String CalFactorValue;
	/** 备注 */
	private String Remark;
	/** 计划类别 */
	private String PlanType;
	/** 产品组合标记 */
	private String PortfolioFlag;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPlanDutyParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "MainRiskCode";
		pk[1] = "RiskCode";
		pk[2] = "ContPlanCode";
		pk[3] = "DutyCode";
		pk[4] = "CalFactor";
		pk[5] = "PlanType";

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
		LDPlanDutyParamSchema cloned = (LDPlanDutyParamSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMainRiskCode()
	{
		return MainRiskCode;
	}
	public void setMainRiskCode(String aMainRiskCode)
	{
		MainRiskCode = aMainRiskCode;
	}
	public String getMainRiskVersion()
	{
		return MainRiskVersion;
	}
	public void setMainRiskVersion(String aMainRiskVersion)
	{
		MainRiskVersion = aMainRiskVersion;
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
	/**
	* 00-默认值
	*/
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getContPlanName()
	{
		return ContPlanName;
	}
	public void setContPlanName(String aContPlanName)
	{
		ContPlanName = aContPlanName;
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
	/**
	* 直接值<p>
	* 算法计算结果值
	*/
	public String getCalFactorType()
	{
		return CalFactorType;
	}
	public void setCalFactorType(String aCalFactorType)
	{
		CalFactorType = aCalFactorType;
	}
	public String getCalFactorValue()
	{
		return CalFactorValue;
	}
	public void setCalFactorValue(String aCalFactorValue)
	{
		CalFactorValue = aCalFactorValue;
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
	* 0-固定计划；<p>
	* 1-非固定计划<p>
	* 3-险种默认值<p>
	* 4-套餐计划
	*/
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
	}
	public String getPortfolioFlag()
	{
		return PortfolioFlag;
	}
	public void setPortfolioFlag(String aPortfolioFlag)
	{
		PortfolioFlag = aPortfolioFlag;
	}

	/**
	* 使用另外一个 LDPlanDutyParamSchema 对象给 Schema 赋值
	* @param: aLDPlanDutyParamSchema LDPlanDutyParamSchema
	**/
	public void setSchema(LDPlanDutyParamSchema aLDPlanDutyParamSchema)
	{
		this.MainRiskCode = aLDPlanDutyParamSchema.getMainRiskCode();
		this.MainRiskVersion = aLDPlanDutyParamSchema.getMainRiskVersion();
		this.RiskCode = aLDPlanDutyParamSchema.getRiskCode();
		this.RiskVersion = aLDPlanDutyParamSchema.getRiskVersion();
		this.ContPlanCode = aLDPlanDutyParamSchema.getContPlanCode();
		this.ContPlanName = aLDPlanDutyParamSchema.getContPlanName();
		this.DutyCode = aLDPlanDutyParamSchema.getDutyCode();
		this.CalFactor = aLDPlanDutyParamSchema.getCalFactor();
		this.CalFactorType = aLDPlanDutyParamSchema.getCalFactorType();
		this.CalFactorValue = aLDPlanDutyParamSchema.getCalFactorValue();
		this.Remark = aLDPlanDutyParamSchema.getRemark();
		this.PlanType = aLDPlanDutyParamSchema.getPlanType();
		this.PortfolioFlag = aLDPlanDutyParamSchema.getPortfolioFlag();
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
			if( rs.getString("MainRiskCode") == null )
				this.MainRiskCode = null;
			else
				this.MainRiskCode = rs.getString("MainRiskCode").trim();

			if( rs.getString("MainRiskVersion") == null )
				this.MainRiskVersion = null;
			else
				this.MainRiskVersion = rs.getString("MainRiskVersion").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("ContPlanName") == null )
				this.ContPlanName = null;
			else
				this.ContPlanName = rs.getString("ContPlanName").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("CalFactor") == null )
				this.CalFactor = null;
			else
				this.CalFactor = rs.getString("CalFactor").trim();

			if( rs.getString("CalFactorType") == null )
				this.CalFactorType = null;
			else
				this.CalFactorType = rs.getString("CalFactorType").trim();

			if( rs.getString("CalFactorValue") == null )
				this.CalFactorValue = null;
			else
				this.CalFactorValue = rs.getString("CalFactorValue").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			if( rs.getString("PortfolioFlag") == null )
				this.PortfolioFlag = null;
			else
				this.PortfolioFlag = rs.getString("PortfolioFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDPlanDutyParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPlanDutyParamSchema getSchema()
	{
		LDPlanDutyParamSchema aLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
		aLDPlanDutyParamSchema.setSchema(this);
		return aLDPlanDutyParamSchema;
	}

	public LDPlanDutyParamDB getDB()
	{
		LDPlanDutyParamDB aDBOper = new LDPlanDutyParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlanDutyParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MainRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainRiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactorValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PortfolioFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlanDutyParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MainRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MainRiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ContPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalFactor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalFactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalFactorValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PortfolioFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamSchema";
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
		if (FCode.equalsIgnoreCase("MainRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskCode));
		}
		if (FCode.equalsIgnoreCase("MainRiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskVersion));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("ContPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanName));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("CalFactor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactor));
		}
		if (FCode.equalsIgnoreCase("CalFactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactorType));
		}
		if (FCode.equalsIgnoreCase("CalFactorValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactorValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equalsIgnoreCase("PortfolioFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PortfolioFlag));
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
				strFieldValue = StrTool.GBKToUnicode(MainRiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MainRiskVersion);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ContPlanName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalFactor);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalFactorType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalFactorValue);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PortfolioFlag);
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

		if (FCode.equalsIgnoreCase("MainRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskCode = FValue.trim();
			}
			else
				MainRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("MainRiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskVersion = FValue.trim();
			}
			else
				MainRiskVersion = null;
		}
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanName = FValue.trim();
			}
			else
				ContPlanName = null;
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
		if (FCode.equalsIgnoreCase("CalFactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFactorType = FValue.trim();
			}
			else
				CalFactorType = null;
		}
		if (FCode.equalsIgnoreCase("CalFactorValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFactorValue = FValue.trim();
			}
			else
				CalFactorValue = null;
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
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanType = FValue.trim();
			}
			else
				PlanType = null;
		}
		if (FCode.equalsIgnoreCase("PortfolioFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PortfolioFlag = FValue.trim();
			}
			else
				PortfolioFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDPlanDutyParamSchema other = (LDPlanDutyParamSchema)otherObject;
		return
			MainRiskCode.equals(other.getMainRiskCode())
			&& MainRiskVersion.equals(other.getMainRiskVersion())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& ContPlanName.equals(other.getContPlanName())
			&& DutyCode.equals(other.getDutyCode())
			&& CalFactor.equals(other.getCalFactor())
			&& CalFactorType.equals(other.getCalFactorType())
			&& CalFactorValue.equals(other.getCalFactorValue())
			&& Remark.equals(other.getRemark())
			&& PlanType.equals(other.getPlanType())
			&& PortfolioFlag.equals(other.getPortfolioFlag());
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
		if( strFieldName.equals("MainRiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("MainRiskVersion") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 3;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return 5;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 6;
		}
		if( strFieldName.equals("CalFactor") ) {
			return 7;
		}
		if( strFieldName.equals("CalFactorType") ) {
			return 8;
		}
		if( strFieldName.equals("CalFactorValue") ) {
			return 9;
		}
		if( strFieldName.equals("Remark") ) {
			return 10;
		}
		if( strFieldName.equals("PlanType") ) {
			return 11;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
			return 12;
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
				strFieldName = "MainRiskCode";
				break;
			case 1:
				strFieldName = "MainRiskVersion";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "RiskVersion";
				break;
			case 4:
				strFieldName = "ContPlanCode";
				break;
			case 5:
				strFieldName = "ContPlanName";
				break;
			case 6:
				strFieldName = "DutyCode";
				break;
			case 7:
				strFieldName = "CalFactor";
				break;
			case 8:
				strFieldName = "CalFactorType";
				break;
			case 9:
				strFieldName = "CalFactorValue";
				break;
			case 10:
				strFieldName = "Remark";
				break;
			case 11:
				strFieldName = "PlanType";
				break;
			case 12:
				strFieldName = "PortfolioFlag";
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
		if( strFieldName.equals("MainRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFactor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFactorValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
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
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
