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
import com.sinosoft.lis.db.LLClaimUWDutyFeeDB;

/*
 * <p>ClassName: LLClaimUWDutyFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUWDutyFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUWDutyFeeSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 保单号 */
	private String PolNo;
	/** 核赔次数 */
	private String ClmUWNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任类型 */
	private String GetDutyType;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 费用类型 */
	private String DutyFeeType;
	/** 费用代码 */
	private String DutyFeeCode;
	/** 费用名称 */
	private String DutyFeeName;
	/** 序号 */
	private String DutyFeeStaNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 医院编号 */
	private String HosID;
	/** 医院名称 */
	private String HosName;
	/** 医院等级 */
	private String HosGrade;
	/** 开始时间 */
	private Date StartDate;
	/** 结束时间 */
	private Date EndDate;
	/** 天数 */
	private int DayCount;
	/** 伤残类型 */
	private String DefoType;
	/** 伤残代码 */
	private String DefoCode;
	/** 伤残级别名称 */
	private String DefoeName;
	/** 残疾给付比例 */
	private double DefoRate;
	/** 实际给付比例 */
	private double RealRate;
	/** 原始金额 */
	private double OriSum;
	/** 调整金额 */
	private double AdjSum;
	/** 理算金额 */
	private double CalSum;
	/** 调整原因 */
	private String AdjReason;
	/** 调整备注 */
	private String AdjRemark;
	/** 免赔额 */
	private double OutDutyAmnt;
	/** 免赔比例 */
	private double OutDutyRate;
	/** 免赔类型 */
	private String OutDutyType;
	/** 管理机构 */
	private String MngCom;
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
	/** 承保时保单号 */
	private String NBPolNo;
	/** 回退号 */
	private String BackNo;
	/** 集体合同号 */
	private String GrpContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 个单合同号 */
	private String ContNo;

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUWDutyFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[10];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "PolNo";
		pk[3] = "ClmUWNo";
		pk[4] = "DutyCode";
		pk[5] = "GetDutyType";
		pk[6] = "GetDutyCode";
		pk[7] = "DutyFeeType";
		pk[8] = "DutyFeeCode";
		pk[9] = "DutyFeeStaNo";

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
		LLClaimUWDutyFeeSchema cloned = (LLClaimUWDutyFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getClmUWNo()
	{
		return ClmUWNo;
	}
	public void setClmUWNo(String aClmUWNo)
	{
		ClmUWNo = aClmUWNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	/**
	* #100  意外医疗<p>
	* #101  意外伤残<p>
	* #102  意外死<p>
	* #103  意外医疗津贴<p>
	* #104  意外高残<p>
	* #200  疾病医疗<p>
	* #201  疾病伤残<p>
	* #202  疾病死亡<p>
	* #203  疾病医疗津贴<p>
	* #204  疾病高残
	*/
	public String getGetDutyType()
	{
		return GetDutyType;
	}
	public void setGetDutyType(String aGetDutyType)
	{
		GetDutyType = aGetDutyType;
	}
	/**
	* 保项编码
	*/
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	/**
	* 指门诊，住院，手术，特殊，其他等5钟
	*/
	public String getDutyFeeType()
	{
		return DutyFeeType;
	}
	public void setDutyFeeType(String aDutyFeeType)
	{
		DutyFeeType = aDutyFeeType;
	}
	/**
	* 住院费，X光费等费用明细
	*/
	public String getDutyFeeCode()
	{
		return DutyFeeCode;
	}
	public void setDutyFeeCode(String aDutyFeeCode)
	{
		DutyFeeCode = aDutyFeeCode;
	}
	/**
	* 住院费，X光费等费用明细
	*/
	public String getDutyFeeName()
	{
		return DutyFeeName;
	}
	public void setDutyFeeName(String aDutyFeeName)
	{
		DutyFeeName = aDutyFeeName;
	}
	/**
	* 对应医疗费用录入的序号
	*/
	public String getDutyFeeStaNo()
	{
		return DutyFeeStaNo;
	}
	public void setDutyFeeStaNo(String aDutyFeeStaNo)
	{
		DutyFeeStaNo = aDutyFeeStaNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
		PolMngCom = aPolMngCom;
	}
	public String getHosID()
	{
		return HosID;
	}
	public void setHosID(String aHosID)
	{
		HosID = aHosID;
	}
	public String getHosName()
	{
		return HosName;
	}
	public void setHosName(String aHosName)
	{
		HosName = aHosName;
	}
	public String getHosGrade()
	{
		return HosGrade;
	}
	public void setHosGrade(String aHosGrade)
	{
		HosGrade = aHosGrade;
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

	public int getDayCount()
	{
		return DayCount;
	}
	public void setDayCount(int aDayCount)
	{
		DayCount = aDayCount;
	}
	public void setDayCount(String aDayCount)
	{
		if (aDayCount != null && !aDayCount.equals(""))
		{
			Integer tInteger = new Integer(aDayCount);
			int i = tInteger.intValue();
			DayCount = i;
		}
	}

	/**
	* 1--普通伤残(使用7级34项残疾给付表)<p>
	* 2--普通伤残(使用老的残疾给付表)
	*/
	public String getDefoType()
	{
		return DefoType;
	}
	public void setDefoType(String aDefoType)
	{
		DefoType = aDefoType;
	}
	/**
	* 七级三十四项
	*/
	public String getDefoCode()
	{
		return DefoCode;
	}
	public void setDefoCode(String aDefoCode)
	{
		DefoCode = aDefoCode;
	}
	public String getDefoeName()
	{
		return DefoeName;
	}
	public void setDefoeName(String aDefoeName)
	{
		DefoeName = aDefoeName;
	}
	public double getDefoRate()
	{
		return DefoRate;
	}
	public void setDefoRate(double aDefoRate)
	{
		DefoRate = aDefoRate;
	}
	public void setDefoRate(String aDefoRate)
	{
		if (aDefoRate != null && !aDefoRate.equals(""))
		{
			Double tDouble = new Double(aDefoRate);
			double d = tDouble.doubleValue();
			DefoRate = d;
		}
	}

	public double getRealRate()
	{
		return RealRate;
	}
	public void setRealRate(double aRealRate)
	{
		RealRate = aRealRate;
	}
	public void setRealRate(String aRealRate)
	{
		if (aRealRate != null && !aRealRate.equals(""))
		{
			Double tDouble = new Double(aRealRate);
			double d = tDouble.doubleValue();
			RealRate = d;
		}
	}

	public double getOriSum()
	{
		return OriSum;
	}
	public void setOriSum(double aOriSum)
	{
		OriSum = aOriSum;
	}
	public void setOriSum(String aOriSum)
	{
		if (aOriSum != null && !aOriSum.equals(""))
		{
			Double tDouble = new Double(aOriSum);
			double d = tDouble.doubleValue();
			OriSum = d;
		}
	}

	public double getAdjSum()
	{
		return AdjSum;
	}
	public void setAdjSum(double aAdjSum)
	{
		AdjSum = aAdjSum;
	}
	public void setAdjSum(String aAdjSum)
	{
		if (aAdjSum != null && !aAdjSum.equals(""))
		{
			Double tDouble = new Double(aAdjSum);
			double d = tDouble.doubleValue();
			AdjSum = d;
		}
	}

	public double getCalSum()
	{
		return CalSum;
	}
	public void setCalSum(double aCalSum)
	{
		CalSum = aCalSum;
	}
	public void setCalSum(String aCalSum)
	{
		if (aCalSum != null && !aCalSum.equals(""))
		{
			Double tDouble = new Double(aCalSum);
			double d = tDouble.doubleValue();
			CalSum = d;
		}
	}

	public String getAdjReason()
	{
		return AdjReason;
	}
	public void setAdjReason(String aAdjReason)
	{
		AdjReason = aAdjReason;
	}
	public String getAdjRemark()
	{
		return AdjRemark;
	}
	public void setAdjRemark(String aAdjRemark)
	{
		AdjRemark = aAdjRemark;
	}
	public double getOutDutyAmnt()
	{
		return OutDutyAmnt;
	}
	public void setOutDutyAmnt(double aOutDutyAmnt)
	{
		OutDutyAmnt = aOutDutyAmnt;
	}
	public void setOutDutyAmnt(String aOutDutyAmnt)
	{
		if (aOutDutyAmnt != null && !aOutDutyAmnt.equals(""))
		{
			Double tDouble = new Double(aOutDutyAmnt);
			double d = tDouble.doubleValue();
			OutDutyAmnt = d;
		}
	}

	public double getOutDutyRate()
	{
		return OutDutyRate;
	}
	public void setOutDutyRate(double aOutDutyRate)
	{
		OutDutyRate = aOutDutyRate;
	}
	public void setOutDutyRate(String aOutDutyRate)
	{
		if (aOutDutyRate != null && !aOutDutyRate.equals(""))
		{
			Double tDouble = new Double(aOutDutyRate);
			double d = tDouble.doubleValue();
			OutDutyRate = d;
		}
	}

	public String getOutDutyType()
	{
		return OutDutyType;
	}
	public void setOutDutyType(String aOutDutyType)
	{
		OutDutyType = aOutDutyType;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
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
	public String getNBPolNo()
	{
		return NBPolNo;
	}
	public void setNBPolNo(String aNBPolNo)
	{
		NBPolNo = aNBPolNo;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		BackNo = aBackNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}

	/**
	* 使用另外一个 LLClaimUWDutyFeeSchema 对象给 Schema 赋值
	* @param: aLLClaimUWDutyFeeSchema LLClaimUWDutyFeeSchema
	**/
	public void setSchema(LLClaimUWDutyFeeSchema aLLClaimUWDutyFeeSchema)
	{
		this.ClmNo = aLLClaimUWDutyFeeSchema.getClmNo();
		this.CaseNo = aLLClaimUWDutyFeeSchema.getCaseNo();
		this.PolNo = aLLClaimUWDutyFeeSchema.getPolNo();
		this.ClmUWNo = aLLClaimUWDutyFeeSchema.getClmUWNo();
		this.DutyCode = aLLClaimUWDutyFeeSchema.getDutyCode();
		this.GetDutyType = aLLClaimUWDutyFeeSchema.getGetDutyType();
		this.GetDutyCode = aLLClaimUWDutyFeeSchema.getGetDutyCode();
		this.DutyFeeType = aLLClaimUWDutyFeeSchema.getDutyFeeType();
		this.DutyFeeCode = aLLClaimUWDutyFeeSchema.getDutyFeeCode();
		this.DutyFeeName = aLLClaimUWDutyFeeSchema.getDutyFeeName();
		this.DutyFeeStaNo = aLLClaimUWDutyFeeSchema.getDutyFeeStaNo();
		this.KindCode = aLLClaimUWDutyFeeSchema.getKindCode();
		this.RiskCode = aLLClaimUWDutyFeeSchema.getRiskCode();
		this.RiskVer = aLLClaimUWDutyFeeSchema.getRiskVer();
		this.PolMngCom = aLLClaimUWDutyFeeSchema.getPolMngCom();
		this.HosID = aLLClaimUWDutyFeeSchema.getHosID();
		this.HosName = aLLClaimUWDutyFeeSchema.getHosName();
		this.HosGrade = aLLClaimUWDutyFeeSchema.getHosGrade();
		this.StartDate = fDate.getDate( aLLClaimUWDutyFeeSchema.getStartDate());
		this.EndDate = fDate.getDate( aLLClaimUWDutyFeeSchema.getEndDate());
		this.DayCount = aLLClaimUWDutyFeeSchema.getDayCount();
		this.DefoType = aLLClaimUWDutyFeeSchema.getDefoType();
		this.DefoCode = aLLClaimUWDutyFeeSchema.getDefoCode();
		this.DefoeName = aLLClaimUWDutyFeeSchema.getDefoeName();
		this.DefoRate = aLLClaimUWDutyFeeSchema.getDefoRate();
		this.RealRate = aLLClaimUWDutyFeeSchema.getRealRate();
		this.OriSum = aLLClaimUWDutyFeeSchema.getOriSum();
		this.AdjSum = aLLClaimUWDutyFeeSchema.getAdjSum();
		this.CalSum = aLLClaimUWDutyFeeSchema.getCalSum();
		this.AdjReason = aLLClaimUWDutyFeeSchema.getAdjReason();
		this.AdjRemark = aLLClaimUWDutyFeeSchema.getAdjRemark();
		this.OutDutyAmnt = aLLClaimUWDutyFeeSchema.getOutDutyAmnt();
		this.OutDutyRate = aLLClaimUWDutyFeeSchema.getOutDutyRate();
		this.OutDutyType = aLLClaimUWDutyFeeSchema.getOutDutyType();
		this.MngCom = aLLClaimUWDutyFeeSchema.getMngCom();
		this.Operator = aLLClaimUWDutyFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLClaimUWDutyFeeSchema.getMakeDate());
		this.MakeTime = aLLClaimUWDutyFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimUWDutyFeeSchema.getModifyDate());
		this.ModifyTime = aLLClaimUWDutyFeeSchema.getModifyTime();
		this.NBPolNo = aLLClaimUWDutyFeeSchema.getNBPolNo();
		this.BackNo = aLLClaimUWDutyFeeSchema.getBackNo();
		this.GrpContNo = aLLClaimUWDutyFeeSchema.getGrpContNo();
		this.GrpPolNo = aLLClaimUWDutyFeeSchema.getGrpPolNo();
		this.ContNo = aLLClaimUWDutyFeeSchema.getContNo();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ClmUWNo") == null )
				this.ClmUWNo = null;
			else
				this.ClmUWNo = rs.getString("ClmUWNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyType") == null )
				this.GetDutyType = null;
			else
				this.GetDutyType = rs.getString("GetDutyType").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("DutyFeeType") == null )
				this.DutyFeeType = null;
			else
				this.DutyFeeType = rs.getString("DutyFeeType").trim();

			if( rs.getString("DutyFeeCode") == null )
				this.DutyFeeCode = null;
			else
				this.DutyFeeCode = rs.getString("DutyFeeCode").trim();

			if( rs.getString("DutyFeeName") == null )
				this.DutyFeeName = null;
			else
				this.DutyFeeName = rs.getString("DutyFeeName").trim();

			if( rs.getString("DutyFeeStaNo") == null )
				this.DutyFeeStaNo = null;
			else
				this.DutyFeeStaNo = rs.getString("DutyFeeStaNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("PolMngCom") == null )
				this.PolMngCom = null;
			else
				this.PolMngCom = rs.getString("PolMngCom").trim();

			if( rs.getString("HosID") == null )
				this.HosID = null;
			else
				this.HosID = rs.getString("HosID").trim();

			if( rs.getString("HosName") == null )
				this.HosName = null;
			else
				this.HosName = rs.getString("HosName").trim();

			if( rs.getString("HosGrade") == null )
				this.HosGrade = null;
			else
				this.HosGrade = rs.getString("HosGrade").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.DayCount = rs.getInt("DayCount");
			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("DefoCode") == null )
				this.DefoCode = null;
			else
				this.DefoCode = rs.getString("DefoCode").trim();

			if( rs.getString("DefoeName") == null )
				this.DefoeName = null;
			else
				this.DefoeName = rs.getString("DefoeName").trim();

			this.DefoRate = rs.getDouble("DefoRate");
			this.RealRate = rs.getDouble("RealRate");
			this.OriSum = rs.getDouble("OriSum");
			this.AdjSum = rs.getDouble("AdjSum");
			this.CalSum = rs.getDouble("CalSum");
			if( rs.getString("AdjReason") == null )
				this.AdjReason = null;
			else
				this.AdjReason = rs.getString("AdjReason").trim();

			if( rs.getString("AdjRemark") == null )
				this.AdjRemark = null;
			else
				this.AdjRemark = rs.getString("AdjRemark").trim();

			this.OutDutyAmnt = rs.getDouble("OutDutyAmnt");
			this.OutDutyRate = rs.getDouble("OutDutyRate");
			if( rs.getString("OutDutyType") == null )
				this.OutDutyType = null;
			else
				this.OutDutyType = rs.getString("OutDutyType").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimUWDutyFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWDutyFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUWDutyFeeSchema getSchema()
	{
		LLClaimUWDutyFeeSchema aLLClaimUWDutyFeeSchema = new LLClaimUWDutyFeeSchema();
		aLLClaimUWDutyFeeSchema.setSchema(this);
		return aLLClaimUWDutyFeeSchema;
	}

	public LLClaimUWDutyFeeDB getDB()
	{
		LLClaimUWDutyFeeDB aDBOper = new LLClaimUWDutyFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWDutyFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyFeeStaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefoRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OriSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutDutyAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutDutyRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutDutyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWDutyFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmUWNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DutyFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DutyFeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DutyFeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DutyFeeStaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			HosID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			HosName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			HosGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			DayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DefoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			DefoeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			DefoRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			RealRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			OriSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			AdjSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			CalSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			OutDutyAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWDutyFeeSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyType));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("DutyFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeType));
		}
		if (FCode.equalsIgnoreCase("DutyFeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeCode));
		}
		if (FCode.equalsIgnoreCase("DutyFeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeName));
		}
		if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyFeeStaNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMngCom));
		}
		if (FCode.equalsIgnoreCase("HosID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosID));
		}
		if (FCode.equalsIgnoreCase("HosName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosName));
		}
		if (FCode.equalsIgnoreCase("HosGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosGrade));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("DayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DayCount));
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoCode));
		}
		if (FCode.equalsIgnoreCase("DefoeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoeName));
		}
		if (FCode.equalsIgnoreCase("DefoRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoRate));
		}
		if (FCode.equalsIgnoreCase("RealRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealRate));
		}
		if (FCode.equalsIgnoreCase("OriSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriSum));
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjSum));
		}
		if (FCode.equalsIgnoreCase("CalSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSum));
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjReason));
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjRemark));
		}
		if (FCode.equalsIgnoreCase("OutDutyAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyAmnt));
		}
		if (FCode.equalsIgnoreCase("OutDutyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyRate));
		}
		if (FCode.equalsIgnoreCase("OutDutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyType));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClmUWNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DutyFeeStaNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(HosID);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(HosName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(HosGrade);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 20:
				strFieldValue = String.valueOf(DayCount);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(DefoCode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(DefoeName);
				break;
			case 24:
				strFieldValue = String.valueOf(DefoRate);
				break;
			case 25:
				strFieldValue = String.valueOf(RealRate);
				break;
			case 26:
				strFieldValue = String.valueOf(OriSum);
				break;
			case 27:
				strFieldValue = String.valueOf(AdjSum);
				break;
			case 28:
				strFieldValue = String.valueOf(CalSum);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 31:
				strFieldValue = String.valueOf(OutDutyAmnt);
				break;
			case 32:
				strFieldValue = String.valueOf(OutDutyRate);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(OutDutyType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWNo = FValue.trim();
			}
			else
				ClmUWNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyType = FValue.trim();
			}
			else
				GetDutyType = null;
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
		if (FCode.equalsIgnoreCase("DutyFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeType = FValue.trim();
			}
			else
				DutyFeeType = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeCode = FValue.trim();
			}
			else
				DutyFeeCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeName = FValue.trim();
			}
			else
				DutyFeeName = null;
		}
		if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyFeeStaNo = FValue.trim();
			}
			else
				DutyFeeStaNo = null;
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolMngCom = FValue.trim();
			}
			else
				PolMngCom = null;
		}
		if (FCode.equalsIgnoreCase("HosID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosID = FValue.trim();
			}
			else
				HosID = null;
		}
		if (FCode.equalsIgnoreCase("HosName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosName = FValue.trim();
			}
			else
				HosName = null;
		}
		if (FCode.equalsIgnoreCase("HosGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosGrade = FValue.trim();
			}
			else
				HosGrade = null;
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("DayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoType = FValue.trim();
			}
			else
				DefoType = null;
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoCode = FValue.trim();
			}
			else
				DefoCode = null;
		}
		if (FCode.equalsIgnoreCase("DefoeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoeName = FValue.trim();
			}
			else
				DefoeName = null;
		}
		if (FCode.equalsIgnoreCase("DefoRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefoRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OriSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OriSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CalSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjReason = FValue.trim();
			}
			else
				AdjReason = null;
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjRemark = FValue.trim();
			}
			else
				AdjRemark = null;
		}
		if (FCode.equalsIgnoreCase("OutDutyAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutDutyAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutDutyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutDutyRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutDutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutDutyType = FValue.trim();
			}
			else
				OutDutyType = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackNo = FValue.trim();
			}
			else
				BackNo = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLClaimUWDutyFeeSchema other = (LLClaimUWDutyFeeSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& PolNo.equals(other.getPolNo())
			&& ClmUWNo.equals(other.getClmUWNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyType.equals(other.getGetDutyType())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& DutyFeeType.equals(other.getDutyFeeType())
			&& DutyFeeCode.equals(other.getDutyFeeCode())
			&& DutyFeeName.equals(other.getDutyFeeName())
			&& DutyFeeStaNo.equals(other.getDutyFeeStaNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& HosID.equals(other.getHosID())
			&& HosName.equals(other.getHosName())
			&& HosGrade.equals(other.getHosGrade())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& DayCount == other.getDayCount()
			&& DefoType.equals(other.getDefoType())
			&& DefoCode.equals(other.getDefoCode())
			&& DefoeName.equals(other.getDefoeName())
			&& DefoRate == other.getDefoRate()
			&& RealRate == other.getRealRate()
			&& OriSum == other.getOriSum()
			&& AdjSum == other.getAdjSum()
			&& CalSum == other.getCalSum()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& OutDutyAmnt == other.getOutDutyAmnt()
			&& OutDutyRate == other.getOutDutyRate()
			&& OutDutyType.equals(other.getOutDutyType())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& NBPolNo.equals(other.getNBPolNo())
			&& BackNo.equals(other.getBackNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyType") ) {
			return 5;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 6;
		}
		if( strFieldName.equals("DutyFeeType") ) {
			return 7;
		}
		if( strFieldName.equals("DutyFeeCode") ) {
			return 8;
		}
		if( strFieldName.equals("DutyFeeName") ) {
			return 9;
		}
		if( strFieldName.equals("DutyFeeStaNo") ) {
			return 10;
		}
		if( strFieldName.equals("KindCode") ) {
			return 11;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 12;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 13;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 14;
		}
		if( strFieldName.equals("HosID") ) {
			return 15;
		}
		if( strFieldName.equals("HosName") ) {
			return 16;
		}
		if( strFieldName.equals("HosGrade") ) {
			return 17;
		}
		if( strFieldName.equals("StartDate") ) {
			return 18;
		}
		if( strFieldName.equals("EndDate") ) {
			return 19;
		}
		if( strFieldName.equals("DayCount") ) {
			return 20;
		}
		if( strFieldName.equals("DefoType") ) {
			return 21;
		}
		if( strFieldName.equals("DefoCode") ) {
			return 22;
		}
		if( strFieldName.equals("DefoeName") ) {
			return 23;
		}
		if( strFieldName.equals("DefoRate") ) {
			return 24;
		}
		if( strFieldName.equals("RealRate") ) {
			return 25;
		}
		if( strFieldName.equals("OriSum") ) {
			return 26;
		}
		if( strFieldName.equals("AdjSum") ) {
			return 27;
		}
		if( strFieldName.equals("CalSum") ) {
			return 28;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 29;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 30;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return 31;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return 32;
		}
		if( strFieldName.equals("OutDutyType") ) {
			return 33;
		}
		if( strFieldName.equals("MngCom") ) {
			return 34;
		}
		if( strFieldName.equals("Operator") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 39;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 40;
		}
		if( strFieldName.equals("BackNo") ) {
			return 41;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 42;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 43;
		}
		if( strFieldName.equals("ContNo") ) {
			return 44;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "ClmUWNo";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "GetDutyType";
				break;
			case 6:
				strFieldName = "GetDutyCode";
				break;
			case 7:
				strFieldName = "DutyFeeType";
				break;
			case 8:
				strFieldName = "DutyFeeCode";
				break;
			case 9:
				strFieldName = "DutyFeeName";
				break;
			case 10:
				strFieldName = "DutyFeeStaNo";
				break;
			case 11:
				strFieldName = "KindCode";
				break;
			case 12:
				strFieldName = "RiskCode";
				break;
			case 13:
				strFieldName = "RiskVer";
				break;
			case 14:
				strFieldName = "PolMngCom";
				break;
			case 15:
				strFieldName = "HosID";
				break;
			case 16:
				strFieldName = "HosName";
				break;
			case 17:
				strFieldName = "HosGrade";
				break;
			case 18:
				strFieldName = "StartDate";
				break;
			case 19:
				strFieldName = "EndDate";
				break;
			case 20:
				strFieldName = "DayCount";
				break;
			case 21:
				strFieldName = "DefoType";
				break;
			case 22:
				strFieldName = "DefoCode";
				break;
			case 23:
				strFieldName = "DefoeName";
				break;
			case 24:
				strFieldName = "DefoRate";
				break;
			case 25:
				strFieldName = "RealRate";
				break;
			case 26:
				strFieldName = "OriSum";
				break;
			case 27:
				strFieldName = "AdjSum";
				break;
			case 28:
				strFieldName = "CalSum";
				break;
			case 29:
				strFieldName = "AdjReason";
				break;
			case 30:
				strFieldName = "AdjRemark";
				break;
			case 31:
				strFieldName = "OutDutyAmnt";
				break;
			case 32:
				strFieldName = "OutDutyRate";
				break;
			case 33:
				strFieldName = "OutDutyType";
				break;
			case 34:
				strFieldName = "MngCom";
				break;
			case 35:
				strFieldName = "Operator";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyDate";
				break;
			case 39:
				strFieldName = "ModifyTime";
				break;
			case 40:
				strFieldName = "NBPolNo";
				break;
			case 41:
				strFieldName = "BackNo";
				break;
			case 42:
				strFieldName = "GrpContNo";
				break;
			case 43:
				strFieldName = "GrpPolNo";
				break;
			case 44:
				strFieldName = "ContNo";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyFeeStaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OriSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutDutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
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
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
