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
import com.sinosoft.lis.db.LLDutyCtrlIndexDB;

/*
 * <p>ClassName: LLDutyCtrlIndexSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLDutyCtrlIndexSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLDutyCtrlIndexSchema.class);

	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 控制批次号 */
	private String CtrlBatchNo;
	/** 控制级别 */
	private int CtrlLevel;
	/** 职业类别 */
	private String OccupationType;
	/** 控制属性 */
	private String CtrlProp;
	/** 保险计划 */
	private String ContPlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
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
	/** 说明 */
	private String Remark;
	/** 观察期(日) */
	private String ObserveDateFlag;
	/** 免赔额控制标记 */
	private String ExemptFlag;
	/** 免赔天数控制标记 */
	private String ExemptDateFlag;
	/** 总赔付限额控制标记 */
	private String TotalLimitFlag;
	/** 赔付比率控制标记 */
	private String ClaimRateFlag;
	/** 起付线控制标记 */
	private String StartPayFlag;
	/** 封顶线控制标记 */
	private String EndPayFlag;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLDutyCtrlIndexSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[8];
		pk[0] = "GrpContNo";
		pk[1] = "CtrlBatchNo";
		pk[2] = "OccupationType";
		pk[3] = "CtrlProp";
		pk[4] = "ContPlanCode";
		pk[5] = "RiskCode";
		pk[6] = "DutyCode";
		pk[7] = "GetDutyCode";

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
		LLDutyCtrlIndexSchema cloned = (LLDutyCtrlIndexSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	/**
	* 如果存在同一个控制点在不同层级都有定义,记录为同一个控制批次号.便于理赔准备计算变量时使用
	*/
	public String getCtrlBatchNo()
	{
		return CtrlBatchNo;
	}
	public void setCtrlBatchNo(String aCtrlBatchNo)
	{
		CtrlBatchNo = aCtrlBatchNo;
	}
	/**
	* 1.	团体合同<p>
	* 2.	职业类别<p>
	* 3.	控制属性(0-共享控制,1-单一控制)<p>
	* 4.	保障计划<p>
	* 5.	险种<p>
	* 6.	责任<p>
	* 7.	给付责任
	*/
	public int getCtrlLevel()
	{
		return CtrlLevel;
	}
	public void setCtrlLevel(int aCtrlLevel)
	{
		CtrlLevel = aCtrlLevel;
	}
	public void setCtrlLevel(String aCtrlLevel)
	{
		if (aCtrlLevel != null && !aCtrlLevel.equals(""))
		{
			Integer tInteger = new Integer(aCtrlLevel);
			int i = tInteger.intValue();
			CtrlLevel = i;
		}
	}

	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
	}
	/**
	* 0-共享控制<p>
	* 1-单一控制
	*/
	public String getCtrlProp()
	{
		return CtrlProp;
	}
	public void setCtrlProp(String aCtrlProp)
	{
		CtrlProp = aCtrlProp;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
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
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getObserveDateFlag()
	{
		return ObserveDateFlag;
	}
	public void setObserveDateFlag(String aObserveDateFlag)
	{
		ObserveDateFlag = aObserveDateFlag;
	}
	public String getExemptFlag()
	{
		return ExemptFlag;
	}
	public void setExemptFlag(String aExemptFlag)
	{
		ExemptFlag = aExemptFlag;
	}
	public String getExemptDateFlag()
	{
		return ExemptDateFlag;
	}
	public void setExemptDateFlag(String aExemptDateFlag)
	{
		ExemptDateFlag = aExemptDateFlag;
	}
	public String getTotalLimitFlag()
	{
		return TotalLimitFlag;
	}
	public void setTotalLimitFlag(String aTotalLimitFlag)
	{
		TotalLimitFlag = aTotalLimitFlag;
	}
	public String getClaimRateFlag()
	{
		return ClaimRateFlag;
	}
	public void setClaimRateFlag(String aClaimRateFlag)
	{
		ClaimRateFlag = aClaimRateFlag;
	}
	/**
	* duty 级别
	*/
	public String getStartPayFlag()
	{
		return StartPayFlag;
	}
	public void setStartPayFlag(String aStartPayFlag)
	{
		StartPayFlag = aStartPayFlag;
	}
	/**
	* duty 级别
	*/
	public String getEndPayFlag()
	{
		return EndPayFlag;
	}
	public void setEndPayFlag(String aEndPayFlag)
	{
		EndPayFlag = aEndPayFlag;
	}

	/**
	* 使用另外一个 LLDutyCtrlIndexSchema 对象给 Schema 赋值
	* @param: aLLDutyCtrlIndexSchema LLDutyCtrlIndexSchema
	**/
	public void setSchema(LLDutyCtrlIndexSchema aLLDutyCtrlIndexSchema)
	{
		this.GrpContNo = aLLDutyCtrlIndexSchema.getGrpContNo();
		this.CtrlBatchNo = aLLDutyCtrlIndexSchema.getCtrlBatchNo();
		this.CtrlLevel = aLLDutyCtrlIndexSchema.getCtrlLevel();
		this.OccupationType = aLLDutyCtrlIndexSchema.getOccupationType();
		this.CtrlProp = aLLDutyCtrlIndexSchema.getCtrlProp();
		this.ContPlanCode = aLLDutyCtrlIndexSchema.getContPlanCode();
		this.RiskCode = aLLDutyCtrlIndexSchema.getRiskCode();
		this.DutyCode = aLLDutyCtrlIndexSchema.getDutyCode();
		this.GetDutyCode = aLLDutyCtrlIndexSchema.getGetDutyCode();
		this.Operator = aLLDutyCtrlIndexSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLDutyCtrlIndexSchema.getMakeDate());
		this.MakeTime = aLLDutyCtrlIndexSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLDutyCtrlIndexSchema.getModifyDate());
		this.ModifyTime = aLLDutyCtrlIndexSchema.getModifyTime();
		this.Remark = aLLDutyCtrlIndexSchema.getRemark();
		this.ObserveDateFlag = aLLDutyCtrlIndexSchema.getObserveDateFlag();
		this.ExemptFlag = aLLDutyCtrlIndexSchema.getExemptFlag();
		this.ExemptDateFlag = aLLDutyCtrlIndexSchema.getExemptDateFlag();
		this.TotalLimitFlag = aLLDutyCtrlIndexSchema.getTotalLimitFlag();
		this.ClaimRateFlag = aLLDutyCtrlIndexSchema.getClaimRateFlag();
		this.StartPayFlag = aLLDutyCtrlIndexSchema.getStartPayFlag();
		this.EndPayFlag = aLLDutyCtrlIndexSchema.getEndPayFlag();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("CtrlBatchNo") == null )
				this.CtrlBatchNo = null;
			else
				this.CtrlBatchNo = rs.getString("CtrlBatchNo").trim();

			this.CtrlLevel = rs.getInt("CtrlLevel");
			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("CtrlProp") == null )
				this.CtrlProp = null;
			else
				this.CtrlProp = rs.getString("CtrlProp").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ObserveDateFlag") == null )
				this.ObserveDateFlag = null;
			else
				this.ObserveDateFlag = rs.getString("ObserveDateFlag").trim();

			if( rs.getString("ExemptFlag") == null )
				this.ExemptFlag = null;
			else
				this.ExemptFlag = rs.getString("ExemptFlag").trim();

			if( rs.getString("ExemptDateFlag") == null )
				this.ExemptDateFlag = null;
			else
				this.ExemptDateFlag = rs.getString("ExemptDateFlag").trim();

			if( rs.getString("TotalLimitFlag") == null )
				this.TotalLimitFlag = null;
			else
				this.TotalLimitFlag = rs.getString("TotalLimitFlag").trim();

			if( rs.getString("ClaimRateFlag") == null )
				this.ClaimRateFlag = null;
			else
				this.ClaimRateFlag = rs.getString("ClaimRateFlag").trim();

			if( rs.getString("StartPayFlag") == null )
				this.StartPayFlag = null;
			else
				this.StartPayFlag = rs.getString("StartPayFlag").trim();

			if( rs.getString("EndPayFlag") == null )
				this.EndPayFlag = null;
			else
				this.EndPayFlag = rs.getString("EndPayFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLDutyCtrlIndex表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLDutyCtrlIndexSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLDutyCtrlIndexSchema getSchema()
	{
		LLDutyCtrlIndexSchema aLLDutyCtrlIndexSchema = new LLDutyCtrlIndexSchema();
		aLLDutyCtrlIndexSchema.setSchema(this);
		return aLLDutyCtrlIndexSchema;
	}

	public LLDutyCtrlIndexDB getDB()
	{
		LLDutyCtrlIndexDB aDBOper = new LLDutyCtrlIndexDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLDutyCtrlIndex描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlBatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlProp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ObserveDateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExemptFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExemptDateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TotalLimitFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimRateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndPayFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLDutyCtrlIndex>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CtrlBatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CtrlLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CtrlProp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ObserveDateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ExemptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ExemptDateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			TotalLimitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ClaimRateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StartPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			EndPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLDutyCtrlIndexSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("CtrlBatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlBatchNo));
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlLevel));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("CtrlProp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlProp));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ObserveDateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObserveDateFlag));
		}
		if (FCode.equalsIgnoreCase("ExemptFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExemptFlag));
		}
		if (FCode.equalsIgnoreCase("ExemptDateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExemptDateFlag));
		}
		if (FCode.equalsIgnoreCase("TotalLimitFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalLimitFlag));
		}
		if (FCode.equalsIgnoreCase("ClaimRateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimRateFlag));
		}
		if (FCode.equalsIgnoreCase("StartPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartPayFlag));
		}
		if (FCode.equalsIgnoreCase("EndPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndPayFlag));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CtrlBatchNo);
				break;
			case 2:
				strFieldValue = String.valueOf(CtrlLevel);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CtrlProp);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ObserveDateFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ExemptFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ExemptDateFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(TotalLimitFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ClaimRateFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StartPayFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(EndPayFlag);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("CtrlBatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlBatchNo = FValue.trim();
			}
			else
				CtrlBatchNo = null;
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
		}
		if (FCode.equalsIgnoreCase("CtrlProp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlProp = FValue.trim();
			}
			else
				CtrlProp = null;
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("ObserveDateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ObserveDateFlag = FValue.trim();
			}
			else
				ObserveDateFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExemptFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExemptFlag = FValue.trim();
			}
			else
				ExemptFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExemptDateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExemptDateFlag = FValue.trim();
			}
			else
				ExemptDateFlag = null;
		}
		if (FCode.equalsIgnoreCase("TotalLimitFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TotalLimitFlag = FValue.trim();
			}
			else
				TotalLimitFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClaimRateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimRateFlag = FValue.trim();
			}
			else
				ClaimRateFlag = null;
		}
		if (FCode.equalsIgnoreCase("StartPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartPayFlag = FValue.trim();
			}
			else
				StartPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("EndPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndPayFlag = FValue.trim();
			}
			else
				EndPayFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLDutyCtrlIndexSchema other = (LLDutyCtrlIndexSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& CtrlBatchNo.equals(other.getCtrlBatchNo())
			&& CtrlLevel == other.getCtrlLevel()
			&& OccupationType.equals(other.getOccupationType())
			&& CtrlProp.equals(other.getCtrlProp())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark())
			&& ObserveDateFlag.equals(other.getObserveDateFlag())
			&& ExemptFlag.equals(other.getExemptFlag())
			&& ExemptDateFlag.equals(other.getExemptDateFlag())
			&& TotalLimitFlag.equals(other.getTotalLimitFlag())
			&& ClaimRateFlag.equals(other.getClaimRateFlag())
			&& StartPayFlag.equals(other.getStartPayFlag())
			&& EndPayFlag.equals(other.getEndPayFlag());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("CtrlBatchNo") ) {
			return 1;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return 2;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 3;
		}
		if( strFieldName.equals("CtrlProp") ) {
			return 4;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 7;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("Remark") ) {
			return 14;
		}
		if( strFieldName.equals("ObserveDateFlag") ) {
			return 15;
		}
		if( strFieldName.equals("ExemptFlag") ) {
			return 16;
		}
		if( strFieldName.equals("ExemptDateFlag") ) {
			return 17;
		}
		if( strFieldName.equals("TotalLimitFlag") ) {
			return 18;
		}
		if( strFieldName.equals("ClaimRateFlag") ) {
			return 19;
		}
		if( strFieldName.equals("StartPayFlag") ) {
			return 20;
		}
		if( strFieldName.equals("EndPayFlag") ) {
			return 21;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "CtrlBatchNo";
				break;
			case 2:
				strFieldName = "CtrlLevel";
				break;
			case 3:
				strFieldName = "OccupationType";
				break;
			case 4:
				strFieldName = "CtrlProp";
				break;
			case 5:
				strFieldName = "ContPlanCode";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "DutyCode";
				break;
			case 8:
				strFieldName = "GetDutyCode";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "Remark";
				break;
			case 15:
				strFieldName = "ObserveDateFlag";
				break;
			case 16:
				strFieldName = "ExemptFlag";
				break;
			case 17:
				strFieldName = "ExemptDateFlag";
				break;
			case 18:
				strFieldName = "TotalLimitFlag";
				break;
			case 19:
				strFieldName = "ClaimRateFlag";
				break;
			case 20:
				strFieldName = "StartPayFlag";
				break;
			case 21:
				strFieldName = "EndPayFlag";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlBatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlProp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ObserveDateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExemptFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExemptDateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TotalLimitFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimRateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndPayFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
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
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
