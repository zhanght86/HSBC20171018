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
import com.sinosoft.lis.db.LPContPlanDutyParamDB;

/*
 * <p>ClassName: LPContPlanDutyParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPContPlanDutyParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPContPlanDutyParamSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体投保单号码 */
	private String ProposalGrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
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

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPContPlanDutyParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[9];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "GrpContNo";
		pk[3] = "MainRiskCode";
		pk[4] = "RiskCode";
		pk[5] = "ContPlanCode";
		pk[6] = "DutyCode";
		pk[7] = "CalFactor";
		pk[8] = "PlanType";

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
		LPContPlanDutyParamSchema cloned = (LPContPlanDutyParamSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		ProposalGrpContNo = aProposalGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
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
	* 3-险种默认值
	*/
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
	}

	/**
	* 使用另外一个 LPContPlanDutyParamSchema 对象给 Schema 赋值
	* @param: aLPContPlanDutyParamSchema LPContPlanDutyParamSchema
	**/
	public void setSchema(LPContPlanDutyParamSchema aLPContPlanDutyParamSchema)
	{
		this.EdorNo = aLPContPlanDutyParamSchema.getEdorNo();
		this.EdorType = aLPContPlanDutyParamSchema.getEdorType();
		this.GrpContNo = aLPContPlanDutyParamSchema.getGrpContNo();
		this.ProposalGrpContNo = aLPContPlanDutyParamSchema.getProposalGrpContNo();
		this.GrpPolNo = aLPContPlanDutyParamSchema.getGrpPolNo();
		this.MainRiskCode = aLPContPlanDutyParamSchema.getMainRiskCode();
		this.MainRiskVersion = aLPContPlanDutyParamSchema.getMainRiskVersion();
		this.RiskCode = aLPContPlanDutyParamSchema.getRiskCode();
		this.RiskVersion = aLPContPlanDutyParamSchema.getRiskVersion();
		this.ContPlanCode = aLPContPlanDutyParamSchema.getContPlanCode();
		this.ContPlanName = aLPContPlanDutyParamSchema.getContPlanName();
		this.DutyCode = aLPContPlanDutyParamSchema.getDutyCode();
		this.CalFactor = aLPContPlanDutyParamSchema.getCalFactor();
		this.CalFactorType = aLPContPlanDutyParamSchema.getCalFactorType();
		this.CalFactorValue = aLPContPlanDutyParamSchema.getCalFactorValue();
		this.Remark = aLPContPlanDutyParamSchema.getRemark();
		this.PlanType = aLPContPlanDutyParamSchema.getPlanType();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPContPlanDutyParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanDutyParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPContPlanDutyParamSchema getSchema()
	{
		LPContPlanDutyParamSchema aLPContPlanDutyParamSchema = new LPContPlanDutyParamSchema();
		aLPContPlanDutyParamSchema.setSchema(this);
		return aLPContPlanDutyParamSchema;
	}

	public LPContPlanDutyParamDB getDB()
	{
		LPContPlanDutyParamDB aDBOper = new LPContPlanDutyParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPContPlanDutyParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(PlanType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPContPlanDutyParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MainRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MainRiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ContPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CalFactor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CalFactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CalFactorValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanDutyParamSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MainRiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MainRiskVersion);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ContPlanName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CalFactor);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CalFactorType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CalFactorValue);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPContPlanDutyParamSchema other = (LPContPlanDutyParamSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& MainRiskCode.equals(other.getMainRiskCode())
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
			&& PlanType.equals(other.getPlanType());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("MainRiskVersion") ) {
			return 6;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 8;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 9;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return 10;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 11;
		}
		if( strFieldName.equals("CalFactor") ) {
			return 12;
		}
		if( strFieldName.equals("CalFactorType") ) {
			return 13;
		}
		if( strFieldName.equals("CalFactorValue") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
		}
		if( strFieldName.equals("PlanType") ) {
			return 16;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ProposalGrpContNo";
				break;
			case 4:
				strFieldName = "GrpPolNo";
				break;
			case 5:
				strFieldName = "MainRiskCode";
				break;
			case 6:
				strFieldName = "MainRiskVersion";
				break;
			case 7:
				strFieldName = "RiskCode";
				break;
			case 8:
				strFieldName = "RiskVersion";
				break;
			case 9:
				strFieldName = "ContPlanCode";
				break;
			case 10:
				strFieldName = "ContPlanName";
				break;
			case 11:
				strFieldName = "DutyCode";
				break;
			case 12:
				strFieldName = "CalFactor";
				break;
			case 13:
				strFieldName = "CalFactorType";
				break;
			case 14:
				strFieldName = "CalFactorValue";
				break;
			case 15:
				strFieldName = "Remark";
				break;
			case 16:
				strFieldName = "PlanType";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
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
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
