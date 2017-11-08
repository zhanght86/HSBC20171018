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
import com.sinosoft.lis.db.LLDutyCtrlDB;

/*
 * <p>ClassName: LLDutyCtrlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLDutyCtrlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLDutyCtrlSchema.class);

	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 保单类型 */
	private String ContType;
	/** 控制级别 */
	private int CtrlLevel;
	/** 控制批次号 */
	private String CtrlBatchNo;
	/** 职业类别 */
	private String OccupationType;
	/** 控制属性 */
	private String CtrlProp;
	/** 保险计划 */
	private String ContPlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 合同号码 */
	private String ContNo;
	/** 保单号 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 观察期(日) */
	private int ObserveDate;
	/** 免赔额 */
	private double Exempt;
	/** 免赔天数 */
	private int ExemptDate;
	/** 总赔付限额 */
	private double TotalLimit;
	/** 赔付比率 */
	private double ClaimRate;
	/** 起付线 */
	private double StartPay;
	/** 封顶线 */
	private double EndPay;
	/** 标志 */
	private String Flag;
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

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLDutyCtrlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "GrpContNo";
		pk[1] = "OccupationType";
		pk[2] = "CtrlProp";
		pk[3] = "ContPlanCode";
		pk[4] = "RiskCode";
		pk[5] = "DutyCode";
		pk[6] = "GetDutyCode";

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
		LLDutyCtrlSchema cloned = (LLDutyCtrlSchema)super.clone();
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
	* 1 个单<p>
	* 2 团单
	*/
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		ContType = aContType;
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

	/**
	* 每一个控制点和控制层次都产生唯一的批次号.
	*/
	public String getCtrlBatchNo()
	{
		return CtrlBatchNo;
	}
	public void setCtrlBatchNo(String aCtrlBatchNo)
	{
		CtrlBatchNo = aCtrlBatchNo;
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
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
	public int getObserveDate()
	{
		return ObserveDate;
	}
	public void setObserveDate(int aObserveDate)
	{
		ObserveDate = aObserveDate;
	}
	public void setObserveDate(String aObserveDate)
	{
		if (aObserveDate != null && !aObserveDate.equals(""))
		{
			Integer tInteger = new Integer(aObserveDate);
			int i = tInteger.intValue();
			ObserveDate = i;
		}
	}

	public double getExempt()
	{
		return Exempt;
	}
	public void setExempt(double aExempt)
	{
		Exempt = aExempt;
	}
	public void setExempt(String aExempt)
	{
		if (aExempt != null && !aExempt.equals(""))
		{
			Double tDouble = new Double(aExempt);
			double d = tDouble.doubleValue();
			Exempt = d;
		}
	}

	public int getExemptDate()
	{
		return ExemptDate;
	}
	public void setExemptDate(int aExemptDate)
	{
		ExemptDate = aExemptDate;
	}
	public void setExemptDate(String aExemptDate)
	{
		if (aExemptDate != null && !aExemptDate.equals(""))
		{
			Integer tInteger = new Integer(aExemptDate);
			int i = tInteger.intValue();
			ExemptDate = i;
		}
	}

	public double getTotalLimit()
	{
		return TotalLimit;
	}
	public void setTotalLimit(double aTotalLimit)
	{
		TotalLimit = aTotalLimit;
	}
	public void setTotalLimit(String aTotalLimit)
	{
		if (aTotalLimit != null && !aTotalLimit.equals(""))
		{
			Double tDouble = new Double(aTotalLimit);
			double d = tDouble.doubleValue();
			TotalLimit = d;
		}
	}

	public double getClaimRate()
	{
		return ClaimRate;
	}
	public void setClaimRate(double aClaimRate)
	{
		ClaimRate = aClaimRate;
	}
	public void setClaimRate(String aClaimRate)
	{
		if (aClaimRate != null && !aClaimRate.equals(""))
		{
			Double tDouble = new Double(aClaimRate);
			double d = tDouble.doubleValue();
			ClaimRate = d;
		}
	}

	/**
	* duty 级别
	*/
	public double getStartPay()
	{
		return StartPay;
	}
	public void setStartPay(double aStartPay)
	{
		StartPay = aStartPay;
	}
	public void setStartPay(String aStartPay)
	{
		if (aStartPay != null && !aStartPay.equals(""))
		{
			Double tDouble = new Double(aStartPay);
			double d = tDouble.doubleValue();
			StartPay = d;
		}
	}

	/**
	* duty 级别
	*/
	public double getEndPay()
	{
		return EndPay;
	}
	public void setEndPay(double aEndPay)
	{
		EndPay = aEndPay;
	}
	public void setEndPay(String aEndPay)
	{
		if (aEndPay != null && !aEndPay.equals(""))
		{
			Double tDouble = new Double(aEndPay);
			double d = tDouble.doubleValue();
			EndPay = d;
		}
	}

	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		Flag = aFlag;
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

	/**
	* 使用另外一个 LLDutyCtrlSchema 对象给 Schema 赋值
	* @param: aLLDutyCtrlSchema LLDutyCtrlSchema
	**/
	public void setSchema(LLDutyCtrlSchema aLLDutyCtrlSchema)
	{
		this.GrpContNo = aLLDutyCtrlSchema.getGrpContNo();
		this.ContType = aLLDutyCtrlSchema.getContType();
		this.CtrlLevel = aLLDutyCtrlSchema.getCtrlLevel();
		this.CtrlBatchNo = aLLDutyCtrlSchema.getCtrlBatchNo();
		this.OccupationType = aLLDutyCtrlSchema.getOccupationType();
		this.CtrlProp = aLLDutyCtrlSchema.getCtrlProp();
		this.ContPlanCode = aLLDutyCtrlSchema.getContPlanCode();
		this.RiskCode = aLLDutyCtrlSchema.getRiskCode();
		this.ContNo = aLLDutyCtrlSchema.getContNo();
		this.PolNo = aLLDutyCtrlSchema.getPolNo();
		this.DutyCode = aLLDutyCtrlSchema.getDutyCode();
		this.GetDutyCode = aLLDutyCtrlSchema.getGetDutyCode();
		this.ObserveDate = aLLDutyCtrlSchema.getObserveDate();
		this.Exempt = aLLDutyCtrlSchema.getExempt();
		this.ExemptDate = aLLDutyCtrlSchema.getExemptDate();
		this.TotalLimit = aLLDutyCtrlSchema.getTotalLimit();
		this.ClaimRate = aLLDutyCtrlSchema.getClaimRate();
		this.StartPay = aLLDutyCtrlSchema.getStartPay();
		this.EndPay = aLLDutyCtrlSchema.getEndPay();
		this.Flag = aLLDutyCtrlSchema.getFlag();
		this.Operator = aLLDutyCtrlSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLDutyCtrlSchema.getMakeDate());
		this.MakeTime = aLLDutyCtrlSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLDutyCtrlSchema.getModifyDate());
		this.ModifyTime = aLLDutyCtrlSchema.getModifyTime();
		this.Remark = aLLDutyCtrlSchema.getRemark();
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

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			this.CtrlLevel = rs.getInt("CtrlLevel");
			if( rs.getString("CtrlBatchNo") == null )
				this.CtrlBatchNo = null;
			else
				this.CtrlBatchNo = rs.getString("CtrlBatchNo").trim();

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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			this.ObserveDate = rs.getInt("ObserveDate");
			this.Exempt = rs.getDouble("Exempt");
			this.ExemptDate = rs.getInt("ExemptDate");
			this.TotalLimit = rs.getDouble("TotalLimit");
			this.ClaimRate = rs.getDouble("ClaimRate");
			this.StartPay = rs.getDouble("StartPay");
			this.EndPay = rs.getDouble("EndPay");
			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLDutyCtrl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLDutyCtrlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLDutyCtrlSchema getSchema()
	{
		LLDutyCtrlSchema aLLDutyCtrlSchema = new LLDutyCtrlSchema();
		aLLDutyCtrlSchema.setSchema(this);
		return aLLDutyCtrlSchema;
	}

	public LLDutyCtrlDB getDB()
	{
		LLDutyCtrlDB aDBOper = new LLDutyCtrlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLDutyCtrl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlBatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlProp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ObserveDate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Exempt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExemptDate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLDutyCtrl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CtrlLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			CtrlBatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CtrlProp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ObserveDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			Exempt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			ExemptDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			TotalLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			StartPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			EndPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLDutyCtrlSchema";
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
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlLevel));
		}
		if (FCode.equalsIgnoreCase("CtrlBatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlBatchNo));
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("ObserveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObserveDate));
		}
		if (FCode.equalsIgnoreCase("Exempt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Exempt));
		}
		if (FCode.equalsIgnoreCase("ExemptDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExemptDate));
		}
		if (FCode.equalsIgnoreCase("TotalLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalLimit));
		}
		if (FCode.equalsIgnoreCase("ClaimRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimRate));
		}
		if (FCode.equalsIgnoreCase("StartPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartPay));
		}
		if (FCode.equalsIgnoreCase("EndPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndPay));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
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
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 2:
				strFieldValue = String.valueOf(CtrlLevel);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CtrlBatchNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CtrlProp);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 12:
				strFieldValue = String.valueOf(ObserveDate);
				break;
			case 13:
				strFieldValue = String.valueOf(Exempt);
				break;
			case 14:
				strFieldValue = String.valueOf(ExemptDate);
				break;
			case 15:
				strFieldValue = String.valueOf(TotalLimit);
				break;
			case 16:
				strFieldValue = String.valueOf(ClaimRate);
				break;
			case 17:
				strFieldValue = String.valueOf(StartPay);
				break;
			case 18:
				strFieldValue = String.valueOf(EndPay);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 25:
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
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
		if (FCode.equalsIgnoreCase("CtrlBatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlBatchNo = FValue.trim();
			}
			else
				CtrlBatchNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("ObserveDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ObserveDate = i;
			}
		}
		if (FCode.equalsIgnoreCase("Exempt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Exempt = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExemptDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ExemptDate = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StartPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EndPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLDutyCtrlSchema other = (LLDutyCtrlSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContType.equals(other.getContType())
			&& CtrlLevel == other.getCtrlLevel()
			&& CtrlBatchNo.equals(other.getCtrlBatchNo())
			&& OccupationType.equals(other.getOccupationType())
			&& CtrlProp.equals(other.getCtrlProp())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& ObserveDate == other.getObserveDate()
			&& Exempt == other.getExempt()
			&& ExemptDate == other.getExemptDate()
			&& TotalLimit == other.getTotalLimit()
			&& ClaimRate == other.getClaimRate()
			&& StartPay == other.getStartPay()
			&& EndPay == other.getEndPay()
			&& Flag.equals(other.getFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContType") ) {
			return 1;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return 2;
		}
		if( strFieldName.equals("CtrlBatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 4;
		}
		if( strFieldName.equals("CtrlProp") ) {
			return 5;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 6;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("ContNo") ) {
			return 8;
		}
		if( strFieldName.equals("PolNo") ) {
			return 9;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 10;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 11;
		}
		if( strFieldName.equals("ObserveDate") ) {
			return 12;
		}
		if( strFieldName.equals("Exempt") ) {
			return 13;
		}
		if( strFieldName.equals("ExemptDate") ) {
			return 14;
		}
		if( strFieldName.equals("TotalLimit") ) {
			return 15;
		}
		if( strFieldName.equals("ClaimRate") ) {
			return 16;
		}
		if( strFieldName.equals("StartPay") ) {
			return 17;
		}
		if( strFieldName.equals("EndPay") ) {
			return 18;
		}
		if( strFieldName.equals("Flag") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
		}
		if( strFieldName.equals("Remark") ) {
			return 25;
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
				strFieldName = "ContType";
				break;
			case 2:
				strFieldName = "CtrlLevel";
				break;
			case 3:
				strFieldName = "CtrlBatchNo";
				break;
			case 4:
				strFieldName = "OccupationType";
				break;
			case 5:
				strFieldName = "CtrlProp";
				break;
			case 6:
				strFieldName = "ContPlanCode";
				break;
			case 7:
				strFieldName = "RiskCode";
				break;
			case 8:
				strFieldName = "ContNo";
				break;
			case 9:
				strFieldName = "PolNo";
				break;
			case 10:
				strFieldName = "DutyCode";
				break;
			case 11:
				strFieldName = "GetDutyCode";
				break;
			case 12:
				strFieldName = "ObserveDate";
				break;
			case 13:
				strFieldName = "Exempt";
				break;
			case 14:
				strFieldName = "ExemptDate";
				break;
			case 15:
				strFieldName = "TotalLimit";
				break;
			case 16:
				strFieldName = "ClaimRate";
				break;
			case 17:
				strFieldName = "StartPay";
				break;
			case 18:
				strFieldName = "EndPay";
				break;
			case 19:
				strFieldName = "Flag";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
				break;
			case 25:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CtrlBatchNo") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ObserveDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Exempt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExemptDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Flag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
