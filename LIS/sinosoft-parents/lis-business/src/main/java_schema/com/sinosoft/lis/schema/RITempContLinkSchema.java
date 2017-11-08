

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RITempContLinkDB;

/*
 * <p>ClassName: RITempContLinkSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RITempContLinkSchema implements Schema, Cloneable
{
	// @Field
	/** 再保合同号 */
	private String RIContNo;
	/** 临分方案号 */
	private String RIPreceptNo;
	/** 关联方式 */
	private String RelaType;
	/** 集体合同号码 */
	private String ProposalGrpContNo;
	/** 集体保单险种号码 */
	private String GrpProposalNo;
	/** 合同号码 */
	private String ProposalContNo;
	/** 保单险种号码 */
	private String ProposalNo;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 计算期间 */
	private String CalFeeTerm;
	/** 计算方式 */
	private String CalFeeType;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;
	/** 备用数字属性1 */
	private double StandbyDouble1;
	/** 备用数字属性2 */
	private double StandbyDouble2;
	/** 备用数字属性3 */
	private double StandbyDouble3;
	/** 备用日期1 */
	private Date StandbyDate1;
	/** 备用日期2 */
	private Date StandbyDate2;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RITempContLinkSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "RIContNo";
		pk[1] = "RIPreceptNo";
		pk[2] = "RelaType";
		pk[3] = "ProposalGrpContNo";
		pk[4] = "ProposalNo";
		pk[5] = "ContPlanCode";
		pk[6] = "DutyCode";

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
		RITempContLinkSchema cloned = (RITempContLinkSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	/**
	* 01:个人保单方式<p>
	* 02：保障计划方式<p>
	* <p>
	* 03：团单方式
	*/
	public String getRelaType()
	{
		return RelaType;
	}
	public void setRelaType(String aRelaType)
	{
		RelaType = aRelaType;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		ProposalGrpContNo = aProposalGrpContNo;
	}
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		GrpProposalNo = aGrpProposalNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/**
	* 此字段保存proposalgrpcontno
	*/
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
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
	/**
	* 填写责任编码，如果计算为险种级别，此字段填写000000
	*/
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	/**
	* 01：按月计算<p>
	* <p>
	* 02：按年计算
	*/
	public String getCalFeeTerm()
	{
		return CalFeeTerm;
	}
	public void setCalFeeTerm(String aCalFeeTerm)
	{
		CalFeeTerm = aCalFeeTerm;
	}
	/**
	* 01：按保费计算方式<p>
	* 02：按保额计算方式
	*/
	public String getCalFeeType()
	{
		return CalFeeType;
	}
	public void setCalFeeType(String aCalFeeType)
	{
		CalFeeType = aCalFeeType;
	}
	/**
	* 临分处理号
	*/
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	public double getStandbyDouble1()
	{
		return StandbyDouble1;
	}
	public void setStandbyDouble1(double aStandbyDouble1)
	{
		StandbyDouble1 = aStandbyDouble1;
	}
	public void setStandbyDouble1(String aStandbyDouble1)
	{
		if (aStandbyDouble1 != null && !aStandbyDouble1.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble1);
			double d = tDouble.doubleValue();
			StandbyDouble1 = d;
		}
	}

	public double getStandbyDouble2()
	{
		return StandbyDouble2;
	}
	public void setStandbyDouble2(double aStandbyDouble2)
	{
		StandbyDouble2 = aStandbyDouble2;
	}
	public void setStandbyDouble2(String aStandbyDouble2)
	{
		if (aStandbyDouble2 != null && !aStandbyDouble2.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble2);
			double d = tDouble.doubleValue();
			StandbyDouble2 = d;
		}
	}

	public double getStandbyDouble3()
	{
		return StandbyDouble3;
	}
	public void setStandbyDouble3(double aStandbyDouble3)
	{
		StandbyDouble3 = aStandbyDouble3;
	}
	public void setStandbyDouble3(String aStandbyDouble3)
	{
		if (aStandbyDouble3 != null && !aStandbyDouble3.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble3);
			double d = tDouble.doubleValue();
			StandbyDouble3 = d;
		}
	}

	/**
	* 确认日期
	*/
	public String getStandbyDate1()
	{
		if( StandbyDate1 != null )
			return fDate.getString(StandbyDate1);
		else
			return null;
	}
	public void setStandbyDate1(Date aStandbyDate1)
	{
		StandbyDate1 = aStandbyDate1;
	}
	public void setStandbyDate1(String aStandbyDate1)
	{
		if (aStandbyDate1 != null && !aStandbyDate1.equals("") )
		{
			StandbyDate1 = fDate.getDate( aStandbyDate1 );
		}
		else
			StandbyDate1 = null;
	}

	public String getStandbyDate2()
	{
		if( StandbyDate2 != null )
			return fDate.getString(StandbyDate2);
		else
			return null;
	}
	public void setStandbyDate2(Date aStandbyDate2)
	{
		StandbyDate2 = aStandbyDate2;
	}
	public void setStandbyDate2(String aStandbyDate2)
	{
		if (aStandbyDate2 != null && !aStandbyDate2.equals("") )
		{
			StandbyDate2 = fDate.getDate( aStandbyDate2 );
		}
		else
			StandbyDate2 = null;
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

	/**
	* 使用另外一个 RITempContLinkSchema 对象给 Schema 赋值
	* @param: aRITempContLinkSchema RITempContLinkSchema
	**/
	public void setSchema(RITempContLinkSchema aRITempContLinkSchema)
	{
		this.RIContNo = aRITempContLinkSchema.getRIContNo();
		this.RIPreceptNo = aRITempContLinkSchema.getRIPreceptNo();
		this.RelaType = aRITempContLinkSchema.getRelaType();
		this.ProposalGrpContNo = aRITempContLinkSchema.getProposalGrpContNo();
		this.GrpProposalNo = aRITempContLinkSchema.getGrpProposalNo();
		this.ProposalContNo = aRITempContLinkSchema.getProposalContNo();
		this.ProposalNo = aRITempContLinkSchema.getProposalNo();
		this.ContPlanCode = aRITempContLinkSchema.getContPlanCode();
		this.RiskCode = aRITempContLinkSchema.getRiskCode();
		this.DutyCode = aRITempContLinkSchema.getDutyCode();
		this.InsuredNo = aRITempContLinkSchema.getInsuredNo();
		this.CalFeeTerm = aRITempContLinkSchema.getCalFeeTerm();
		this.CalFeeType = aRITempContLinkSchema.getCalFeeType();
		this.StandbyString1 = aRITempContLinkSchema.getStandbyString1();
		this.StandbyString2 = aRITempContLinkSchema.getStandbyString2();
		this.StandbyString3 = aRITempContLinkSchema.getStandbyString3();
		this.StandbyDouble1 = aRITempContLinkSchema.getStandbyDouble1();
		this.StandbyDouble2 = aRITempContLinkSchema.getStandbyDouble2();
		this.StandbyDouble3 = aRITempContLinkSchema.getStandbyDouble3();
		this.StandbyDate1 = fDate.getDate( aRITempContLinkSchema.getStandbyDate1());
		this.StandbyDate2 = fDate.getDate( aRITempContLinkSchema.getStandbyDate2());
		this.Operator = aRITempContLinkSchema.getOperator();
		this.MakeDate = fDate.getDate( aRITempContLinkSchema.getMakeDate());
		this.MakeTime = aRITempContLinkSchema.getMakeTime();
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
			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("RelaType") == null )
				this.RelaType = null;
			else
				this.RelaType = rs.getString("RelaType").trim();

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

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

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("CalFeeTerm") == null )
				this.CalFeeTerm = null;
			else
				this.CalFeeTerm = rs.getString("CalFeeTerm").trim();

			if( rs.getString("CalFeeType") == null )
				this.CalFeeType = null;
			else
				this.CalFeeType = rs.getString("CalFeeType").trim();

			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			this.StandbyDouble1 = rs.getDouble("StandbyDouble1");
			this.StandbyDouble2 = rs.getDouble("StandbyDouble2");
			this.StandbyDouble3 = rs.getDouble("StandbyDouble3");
			this.StandbyDate1 = rs.getDate("StandbyDate1");
			this.StandbyDate2 = rs.getDate("StandbyDate2");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RITempContLink表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RITempContLinkSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RITempContLinkSchema getSchema()
	{
		RITempContLinkSchema aRITempContLinkSchema = new RITempContLinkSchema();
		aRITempContLinkSchema.setSchema(this);
		return aRITempContLinkSchema;
	}

	public RITempContLinkDB getDB()
	{
		RITempContLinkDB aDBOper = new RITempContLinkDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRITempContLink描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFeeTerm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRITempContLink>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalFeeTerm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CalFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandbyDouble1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			StandbyDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RITempContLinkSchema";
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
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaType));
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
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
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("CalFeeTerm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFeeTerm));
		}
		if (FCode.equalsIgnoreCase("CalFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFeeType));
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble1));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble2));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble3));
		}
		if (FCode.equalsIgnoreCase("StandbyDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate1()));
		}
		if (FCode.equalsIgnoreCase("StandbyDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate2()));
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
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RelaType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalFeeTerm);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CalFeeType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 16:
				strFieldValue = String.valueOf(StandbyDouble1);
				break;
			case 17:
				strFieldValue = String.valueOf(StandbyDouble2);
				break;
			case 18:
				strFieldValue = String.valueOf(StandbyDouble3);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate1()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate2()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaType = FValue.trim();
			}
			else
				RelaType = null;
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
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpProposalNo = FValue.trim();
			}
			else
				GrpProposalNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
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
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("CalFeeTerm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFeeTerm = FValue.trim();
			}
			else
				CalFeeTerm = null;
		}
		if (FCode.equalsIgnoreCase("CalFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFeeType = FValue.trim();
			}
			else
				CalFeeType = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDate1"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate1 = fDate.getDate( FValue );
			}
			else
				StandbyDate1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate2 = fDate.getDate( FValue );
			}
			else
				StandbyDate2 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RITempContLinkSchema other = (RITempContLinkSchema)otherObject;
		return
			RIContNo.equals(other.getRIContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& RelaType.equals(other.getRelaType())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& InsuredNo.equals(other.getInsuredNo())
			&& CalFeeTerm.equals(other.getCalFeeTerm())
			&& CalFeeType.equals(other.getCalFeeType())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& StandbyDouble1 == other.getStandbyDouble1()
			&& StandbyDouble2 == other.getStandbyDouble2()
			&& StandbyDouble3 == other.getStandbyDouble3()
			&& fDate.getString(StandbyDate1).equals(other.getStandbyDate1())
			&& fDate.getString(StandbyDate2).equals(other.getStandbyDate2())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("RIContNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 1;
		}
		if( strFieldName.equals("RelaType") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 4;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 5;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 6;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 7;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 8;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 9;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 10;
		}
		if( strFieldName.equals("CalFeeTerm") ) {
			return 11;
		}
		if( strFieldName.equals("CalFeeType") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 13;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 14;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 15;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return 16;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return 17;
		}
		if( strFieldName.equals("StandbyDouble3") ) {
			return 18;
		}
		if( strFieldName.equals("StandbyDate1") ) {
			return 19;
		}
		if( strFieldName.equals("StandbyDate2") ) {
			return 20;
		}
		if( strFieldName.equals("Operator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
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
				strFieldName = "RIContNo";
				break;
			case 1:
				strFieldName = "RIPreceptNo";
				break;
			case 2:
				strFieldName = "RelaType";
				break;
			case 3:
				strFieldName = "ProposalGrpContNo";
				break;
			case 4:
				strFieldName = "GrpProposalNo";
				break;
			case 5:
				strFieldName = "ProposalContNo";
				break;
			case 6:
				strFieldName = "ProposalNo";
				break;
			case 7:
				strFieldName = "ContPlanCode";
				break;
			case 8:
				strFieldName = "RiskCode";
				break;
			case 9:
				strFieldName = "DutyCode";
				break;
			case 10:
				strFieldName = "InsuredNo";
				break;
			case 11:
				strFieldName = "CalFeeTerm";
				break;
			case 12:
				strFieldName = "CalFeeType";
				break;
			case 13:
				strFieldName = "StandbyString1";
				break;
			case 14:
				strFieldName = "StandbyString2";
				break;
			case 15:
				strFieldName = "StandbyString3";
				break;
			case 16:
				strFieldName = "StandbyDouble1";
				break;
			case 17:
				strFieldName = "StandbyDouble2";
				break;
			case 18:
				strFieldName = "StandbyDouble3";
				break;
			case 19:
				strFieldName = "StandbyDate1";
				break;
			case 20:
				strFieldName = "StandbyDate2";
				break;
			case 21:
				strFieldName = "Operator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
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
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFeeTerm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDate1") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandbyDate2") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
