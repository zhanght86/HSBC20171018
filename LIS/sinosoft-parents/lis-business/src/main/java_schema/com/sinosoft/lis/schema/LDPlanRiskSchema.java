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
import com.sinosoft.lis.db.LDPlanRiskDB;

/*
 * <p>ClassName: LDPlanRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDPlanRiskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPlanRiskSchema.class);

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
	/** 备注 */
	private String Remark;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 计划类别 */
	private String PlanType;
	/** 产品组合标记 */
	private String PortfolioFlag;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPlanRiskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "MainRiskCode";
		pk[1] = "RiskCode";
		pk[2] = "ContPlanCode";
		pk[3] = "PlanType";

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
		LDPlanRiskSchema cloned = (LDPlanRiskSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
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
	/**
	* 参数个数
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
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
	* 使用另外一个 LDPlanRiskSchema 对象给 Schema 赋值
	* @param: aLDPlanRiskSchema LDPlanRiskSchema
	**/
	public void setSchema(LDPlanRiskSchema aLDPlanRiskSchema)
	{
		this.MainRiskCode = aLDPlanRiskSchema.getMainRiskCode();
		this.MainRiskVersion = aLDPlanRiskSchema.getMainRiskVersion();
		this.RiskCode = aLDPlanRiskSchema.getRiskCode();
		this.RiskVersion = aLDPlanRiskSchema.getRiskVersion();
		this.ContPlanCode = aLDPlanRiskSchema.getContPlanCode();
		this.ContPlanName = aLDPlanRiskSchema.getContPlanName();
		this.Remark = aLDPlanRiskSchema.getRemark();
		this.Operator = aLDPlanRiskSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDPlanRiskSchema.getMakeDate());
		this.MakeTime = aLDPlanRiskSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDPlanRiskSchema.getModifyDate());
		this.ModifyTime = aLDPlanRiskSchema.getModifyTime();
		this.PlanType = aLDPlanRiskSchema.getPlanType();
		this.PortfolioFlag = aLDPlanRiskSchema.getPortfolioFlag();
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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

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
			logger.debug("数据库中的LDPlanRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPlanRiskSchema getSchema()
	{
		LDPlanRiskSchema aLDPlanRiskSchema = new LDPlanRiskSchema();
		aLDPlanRiskSchema.setSchema(this);
		return aLDPlanRiskSchema;
	}

	public LDPlanRiskDB getDB()
	{
		LDPlanRiskDB aDBOper = new LDPlanRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlanRisk描述/A>表字段
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
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PortfolioFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlanRisk>历史记账凭证主表信息</A>表字段
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
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PortfolioFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanRiskSchema";
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 13:
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		LDPlanRiskSchema other = (LDPlanRiskSchema)otherObject;
		return
			MainRiskCode.equals(other.getMainRiskCode())
			&& MainRiskVersion.equals(other.getMainRiskVersion())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& ContPlanName.equals(other.getContPlanName())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
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
		if( strFieldName.equals("Remark") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
		}
		if( strFieldName.equals("PlanType") ) {
			return 12;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
			return 13;
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
				strFieldName = "Remark";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
				break;
			case 12:
				strFieldName = "PlanType";
				break;
			case 13:
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
