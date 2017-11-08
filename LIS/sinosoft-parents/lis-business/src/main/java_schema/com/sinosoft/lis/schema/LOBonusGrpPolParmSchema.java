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
import com.sinosoft.lis.db.LOBonusGrpPolParmDB;

/*
 * <p>ClassName: LOBonusGrpPolParmSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_13
 */
public class LOBonusGrpPolParmSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBonusGrpPolParmSchema.class);
	// @Field
	/** 红利分配会计年度 */
	private int FiscalYear;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 状态 */
	private String ComputeState;
	/** 实际投资收益率 */
	private double ActuRate;
	/** 保证年收益率 */
	private double EnsuRate;
	/** 默认保证年收益率 */
	private double EnsuRateDefault;
	/** 分红比率 */
	private double AssignRate;
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
	/** 红利应计算日期 */
	private Date SGetDate;
	/** 险种编码 */
	private String RiskCode;
	/** 红利金额 */
	private double SumBonus;
	/** 团单合同号 */
	private String GrpContNo;
	/** 利率类型 */
	private String RateIntv;
	/** 注释 */
	private String Note;
	/** 备用字段3 */
	private String StandbyFlag3;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;
	/** 终止日 */
	private Date EndDate;
	/** 发布日期 */
	private Date ARateDate;
	/** 结算/分红类型 */
	private String Flag;
	/** 起始日 */
	private Date StartDate;
	/** 利率 */
	private double Rate;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBonusGrpPolParmSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "FiscalYear";
		pk[1] = "GrpPolNo";

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
		LOBonusGrpPolParmSchema cloned = (LOBonusGrpPolParmSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 导入红利分配时的会计年度。（该字段在导入每年的可分配盈余时同时导入）
	*/
	public int getFiscalYear()
	{
		return FiscalYear;
	}
	public void setFiscalYear(int aFiscalYear)
	{
		FiscalYear = aFiscalYear;
	}
	public void setFiscalYear(String aFiscalYear)
	{
		if (aFiscalYear != null && !aFiscalYear.equals(""))
		{
			Integer tInteger = new Integer(aFiscalYear);
			int i = tInteger.intValue();
			FiscalYear = i;
		}
	}

	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	/**
	* 表示该红利是否已经完成结算。 0 待计算 1 红利计算结束 2 红利分配结束
	*/
	public String getComputeState()
	{
		return ComputeState;
	}
	public void setComputeState(String aComputeState)
	{
		if(aComputeState!=null && aComputeState.length()>1)
			throw new IllegalArgumentException("状态ComputeState值"+aComputeState+"的长度"+aComputeState.length()+"大于最大值1");
		ComputeState = aComputeState;
	}
	/**
	* 每会计年度计算时由精算提供
	*/
	public double getActuRate()
	{
		return ActuRate;
	}
	public void setActuRate(double aActuRate)
	{
		ActuRate = aActuRate;
	}
	public void setActuRate(String aActuRate)
	{
		if (aActuRate != null && !aActuRate.equals(""))
		{
			Double tDouble = new Double(aActuRate);
			double d = tDouble.doubleValue();
			ActuRate = d;
		}
	}

	/**
	* 每会计年度计算时由从团单提取
	*/
	public double getEnsuRate()
	{
		return EnsuRate;
	}
	public void setEnsuRate(double aEnsuRate)
	{
		EnsuRate = aEnsuRate;
	}
	public void setEnsuRate(String aEnsuRate)
	{
		if (aEnsuRate != null && !aEnsuRate.equals(""))
		{
			Double tDouble = new Double(aEnsuRate);
			double d = tDouble.doubleValue();
			EnsuRate = d;
		}
	}

	/**
	* 如果没有 保证年收益率 则取默认保证年收益率
	*/
	public double getEnsuRateDefault()
	{
		return EnsuRateDefault;
	}
	public void setEnsuRateDefault(double aEnsuRateDefault)
	{
		EnsuRateDefault = aEnsuRateDefault;
	}
	public void setEnsuRateDefault(String aEnsuRateDefault)
	{
		if (aEnsuRateDefault != null && !aEnsuRateDefault.equals(""))
		{
			Double tDouble = new Double(aEnsuRateDefault);
			double d = tDouble.doubleValue();
			EnsuRateDefault = d;
		}
	}

	/**
	* 精算提供
	*/
	public double getAssignRate()
	{
		return AssignRate;
	}
	public void setAssignRate(double aAssignRate)
	{
		AssignRate = aAssignRate;
	}
	public void setAssignRate(String aAssignRate)
	{
		if (aAssignRate != null && !aAssignRate.equals(""))
		{
			Double tDouble = new Double(aAssignRate);
			double d = tDouble.doubleValue();
			AssignRate = d;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getSGetDate()
	{
		if( SGetDate != null )
			return fDate.getString(SGetDate);
		else
			return null;
	}
	public void setSGetDate(Date aSGetDate)
	{
		SGetDate = aSGetDate;
	}
	public void setSGetDate(String aSGetDate)
	{
		if (aSGetDate != null && !aSGetDate.equals("") )
		{
			SGetDate = fDate.getDate( aSGetDate );
		}
		else
			SGetDate = null;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public double getSumBonus()
	{
		return SumBonus;
	}
	public void setSumBonus(double aSumBonus)
	{
		SumBonus = aSumBonus;
	}
	public void setSumBonus(String aSumBonus)
	{
		if (aSumBonus != null && !aSumBonus.equals(""))
		{
			Double tDouble = new Double(aSumBonus);
			double d = tDouble.doubleValue();
			SumBonus = d;
		}
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团单合同号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	/**
	* Y 年利率 M 月利率
	*/
	public String getRateIntv()
	{
		return RateIntv;
	}
	public void setRateIntv(String aRateIntv)
	{
		if(aRateIntv!=null && aRateIntv.length()>2)
			throw new IllegalArgumentException("利率类型RateIntv值"+aRateIntv+"的长度"+aRateIntv.length()+"大于最大值2");
		RateIntv = aRateIntv;
	}
	public String getNote()
	{
		return Note;
	}
	public void setNote(String aNote)
	{
		if(aNote!=null && aNote.length()>300)
			throw new IllegalArgumentException("注释Note值"+aNote+"的长度"+aNote.length()+"大于最大值300");
		Note = aNote;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		if(aStandbyFlag3!=null && aStandbyFlag3.length()>20)
			throw new IllegalArgumentException("备用字段3StandbyFlag3值"+aStandbyFlag3+"的长度"+aStandbyFlag3.length()+"大于最大值20");
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>20)
			throw new IllegalArgumentException("备用字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值20");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>20)
			throw new IllegalArgumentException("备用字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值20");
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getARateDate()
	{
		if( ARateDate != null )
			return fDate.getString(ARateDate);
		else
			return null;
	}
	public void setARateDate(Date aARateDate)
	{
		ARateDate = aARateDate;
	}
	public void setARateDate(String aARateDate)
	{
		if (aARateDate != null && !aARateDate.equals("") )
		{
			ARateDate = fDate.getDate( aARateDate );
		}
		else
			ARateDate = null;
	}

	/**
	* 0 分红利率 1 结算利率 2 保证利率
	*/
	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		if(aFlag!=null && aFlag.length()>2)
			throw new IllegalArgumentException("结算/分红类型Flag值"+aFlag+"的长度"+aFlag.length()+"大于最大值2");
		Flag = aFlag;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	/**
	* 精算提供
	*/
	public double getRate()
	{
		return Rate;
	}
	public void setRate(double aRate)
	{
		Rate = aRate;
	}
	public void setRate(String aRate)
	{
		if (aRate != null && !aRate.equals(""))
		{
			Double tDouble = new Double(aRate);
			double d = tDouble.doubleValue();
			Rate = d;
		}
	}


	/**
	* 使用另外一个 LOBonusGrpPolParmSchema 对象给 Schema 赋值
	* @param: aLOBonusGrpPolParmSchema LOBonusGrpPolParmSchema
	**/
	public void setSchema(LOBonusGrpPolParmSchema aLOBonusGrpPolParmSchema)
	{
		this.FiscalYear = aLOBonusGrpPolParmSchema.getFiscalYear();
		this.GrpPolNo = aLOBonusGrpPolParmSchema.getGrpPolNo();
		this.ComputeState = aLOBonusGrpPolParmSchema.getComputeState();
		this.ActuRate = aLOBonusGrpPolParmSchema.getActuRate();
		this.EnsuRate = aLOBonusGrpPolParmSchema.getEnsuRate();
		this.EnsuRateDefault = aLOBonusGrpPolParmSchema.getEnsuRateDefault();
		this.AssignRate = aLOBonusGrpPolParmSchema.getAssignRate();
		this.Operator = aLOBonusGrpPolParmSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOBonusGrpPolParmSchema.getMakeDate());
		this.MakeTime = aLOBonusGrpPolParmSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOBonusGrpPolParmSchema.getModifyDate());
		this.ModifyTime = aLOBonusGrpPolParmSchema.getModifyTime();
		this.SGetDate = fDate.getDate( aLOBonusGrpPolParmSchema.getSGetDate());
		this.RiskCode = aLOBonusGrpPolParmSchema.getRiskCode();
		this.SumBonus = aLOBonusGrpPolParmSchema.getSumBonus();
		this.GrpContNo = aLOBonusGrpPolParmSchema.getGrpContNo();
		this.RateIntv = aLOBonusGrpPolParmSchema.getRateIntv();
		this.Note = aLOBonusGrpPolParmSchema.getNote();
		this.StandbyFlag3 = aLOBonusGrpPolParmSchema.getStandbyFlag3();
		this.StandbyFlag1 = aLOBonusGrpPolParmSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLOBonusGrpPolParmSchema.getStandbyFlag2();
		this.EndDate = fDate.getDate( aLOBonusGrpPolParmSchema.getEndDate());
		this.ARateDate = fDate.getDate( aLOBonusGrpPolParmSchema.getARateDate());
		this.Flag = aLOBonusGrpPolParmSchema.getFlag();
		this.StartDate = fDate.getDate( aLOBonusGrpPolParmSchema.getStartDate());
		this.Rate = aLOBonusGrpPolParmSchema.getRate();
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
			this.FiscalYear = rs.getInt("FiscalYear");
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ComputeState") == null )
				this.ComputeState = null;
			else
				this.ComputeState = rs.getString("ComputeState").trim();

			this.ActuRate = rs.getDouble("ActuRate");
			this.EnsuRate = rs.getDouble("EnsuRate");
			this.EnsuRateDefault = rs.getDouble("EnsuRateDefault");
			this.AssignRate = rs.getDouble("AssignRate");
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

			this.SGetDate = rs.getDate("SGetDate");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.SumBonus = rs.getDouble("SumBonus");
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RateIntv") == null )
				this.RateIntv = null;
			else
				this.RateIntv = rs.getString("RateIntv").trim();

			if( rs.getString("Note") == null )
				this.Note = null;
			else
				this.Note = rs.getString("Note").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			this.EndDate = rs.getDate("EndDate");
			this.ARateDate = rs.getDate("ARateDate");
			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			this.StartDate = rs.getDate("StartDate");
			this.Rate = rs.getDouble("Rate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBonusGrpPolParm表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusGrpPolParmSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBonusGrpPolParmSchema getSchema()
	{
		LOBonusGrpPolParmSchema aLOBonusGrpPolParmSchema = new LOBonusGrpPolParmSchema();
		aLOBonusGrpPolParmSchema.setSchema(this);
		return aLOBonusGrpPolParmSchema;
	}

	public LOBonusGrpPolParmDB getDB()
	{
		LOBonusGrpPolParmDB aDBOper = new LOBonusGrpPolParmDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusGrpPolParm描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComputeState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ActuRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnsuRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnsuRateDefault));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssignRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Note)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ARateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusGrpPolParm>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ComputeState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ActuRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			EnsuRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			EnsuRateDefault = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AssignRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SumBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			RateIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Note = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ARateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusGrpPolParmSchema";
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
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ComputeState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComputeState));
		}
		if (FCode.equalsIgnoreCase("ActuRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActuRate));
		}
		if (FCode.equalsIgnoreCase("EnsuRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnsuRate));
		}
		if (FCode.equalsIgnoreCase("EnsuRateDefault"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnsuRateDefault));
		}
		if (FCode.equalsIgnoreCase("AssignRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssignRate));
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
		if (FCode.equalsIgnoreCase("SGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSGetDate()));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("SumBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumBonus));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RateIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateIntv));
		}
		if (FCode.equalsIgnoreCase("Note"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Note));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("ARateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getARateDate()));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
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
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ComputeState);
				break;
			case 3:
				strFieldValue = String.valueOf(ActuRate);
				break;
			case 4:
				strFieldValue = String.valueOf(EnsuRate);
				break;
			case 5:
				strFieldValue = String.valueOf(EnsuRateDefault);
				break;
			case 6:
				strFieldValue = String.valueOf(AssignRate);
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSGetDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 14:
				strFieldValue = String.valueOf(SumBonus);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(RateIntv);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Note);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getARateDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 25:
				strFieldValue = String.valueOf(Rate);
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

		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
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
		if (FCode.equalsIgnoreCase("ComputeState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComputeState = FValue.trim();
			}
			else
				ComputeState = null;
		}
		if (FCode.equalsIgnoreCase("ActuRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ActuRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnsuRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnsuRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnsuRateDefault"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnsuRateDefault = d;
			}
		}
		if (FCode.equalsIgnoreCase("AssignRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AssignRate = d;
			}
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
		if (FCode.equalsIgnoreCase("SGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SGetDate = fDate.getDate( FValue );
			}
			else
				SGetDate = null;
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
		if (FCode.equalsIgnoreCase("SumBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumBonus = d;
			}
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
		if (FCode.equalsIgnoreCase("RateIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateIntv = FValue.trim();
			}
			else
				RateIntv = null;
		}
		if (FCode.equalsIgnoreCase("Note"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Note = FValue.trim();
			}
			else
				Note = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("ARateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ARateDate = fDate.getDate( FValue );
			}
			else
				ARateDate = null;
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
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBonusGrpPolParmSchema other = (LOBonusGrpPolParmSchema)otherObject;
		return
			FiscalYear == other.getFiscalYear()
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ComputeState.equals(other.getComputeState())
			&& ActuRate == other.getActuRate()
			&& EnsuRate == other.getEnsuRate()
			&& EnsuRateDefault == other.getEnsuRateDefault()
			&& AssignRate == other.getAssignRate()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(SGetDate).equals(other.getSGetDate())
			&& RiskCode.equals(other.getRiskCode())
			&& SumBonus == other.getSumBonus()
			&& GrpContNo.equals(other.getGrpContNo())
			&& RateIntv.equals(other.getRateIntv())
			&& Note.equals(other.getNote())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(ARateDate).equals(other.getARateDate())
			&& Flag.equals(other.getFlag())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& Rate == other.getRate();
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
		if( strFieldName.equals("FiscalYear") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("ComputeState") ) {
			return 2;
		}
		if( strFieldName.equals("ActuRate") ) {
			return 3;
		}
		if( strFieldName.equals("EnsuRate") ) {
			return 4;
		}
		if( strFieldName.equals("EnsuRateDefault") ) {
			return 5;
		}
		if( strFieldName.equals("AssignRate") ) {
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
		if( strFieldName.equals("SGetDate") ) {
			return 12;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 13;
		}
		if( strFieldName.equals("SumBonus") ) {
			return 14;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 15;
		}
		if( strFieldName.equals("RateIntv") ) {
			return 16;
		}
		if( strFieldName.equals("Note") ) {
			return 17;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 18;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 19;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 20;
		}
		if( strFieldName.equals("EndDate") ) {
			return 21;
		}
		if( strFieldName.equals("ARateDate") ) {
			return 22;
		}
		if( strFieldName.equals("Flag") ) {
			return 23;
		}
		if( strFieldName.equals("StartDate") ) {
			return 24;
		}
		if( strFieldName.equals("Rate") ) {
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
				strFieldName = "FiscalYear";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "ComputeState";
				break;
			case 3:
				strFieldName = "ActuRate";
				break;
			case 4:
				strFieldName = "EnsuRate";
				break;
			case 5:
				strFieldName = "EnsuRateDefault";
				break;
			case 6:
				strFieldName = "AssignRate";
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
				strFieldName = "SGetDate";
				break;
			case 13:
				strFieldName = "RiskCode";
				break;
			case 14:
				strFieldName = "SumBonus";
				break;
			case 15:
				strFieldName = "GrpContNo";
				break;
			case 16:
				strFieldName = "RateIntv";
				break;
			case 17:
				strFieldName = "Note";
				break;
			case 18:
				strFieldName = "StandbyFlag3";
				break;
			case 19:
				strFieldName = "StandbyFlag1";
				break;
			case 20:
				strFieldName = "StandbyFlag2";
				break;
			case 21:
				strFieldName = "EndDate";
				break;
			case 22:
				strFieldName = "ARateDate";
				break;
			case 23:
				strFieldName = "Flag";
				break;
			case 24:
				strFieldName = "StartDate";
				break;
			case 25:
				strFieldName = "Rate";
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
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComputeState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActuRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnsuRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnsuRateDefault") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AssignRate") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("SGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Note") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ARateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
