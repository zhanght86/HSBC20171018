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
import com.sinosoft.lis.db.PD_LDPlanFeeRelaDB;

/*
 * <p>ClassName: PD_LDPlanFeeRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class PD_LDPlanFeeRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LDPlanFeeRelaSchema.class);
	// @Field
	/** 保障计划代码 */
	private String PlanCode;
	/** 产品代码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 给付责任代码 */
	private String GetDutyCode;
	/** 账单费用项代码 */
	private String FeeCode;
	/** 备用字段2 */
	private String StandFlag2;
	/** 费用类型 */
	private String FeeType;
	/** 是否预授权标识 */
	private String PreAuthFlag;
	/** 保单年度金额上限 */
	private String MoneyLimitAnnual;
	/** 保单年度次数上限 */
	private String CountLimitAnnual;
	/** 保单年度天数上限 */
	private String DaysLimitAnnual;
	/** 每次赔偿金额上限 */
	private String MoneyLimitEveryTime;
	/** 每次赔偿天数上限 */
	private String DaysLimitEveryTime;
	/** 每天赔付金额上限 */
	private String MoneyLimitEveryDay;
	/** 每天赔偿固定金额 */
	private String PayMoneyEveryDay;
	/** 等待期 */
	private String ObsPeriod;
	/** 等待期单位 */
	private String ObsPeriodUn;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 备用字段1 */
	private String StandFlag1;
	/** 备注1 */
	private String Remark1;
	/** 备注2 */
	private String Remark2;
	/** 管理机构 */
	private String ManageCom;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LDPlanFeeRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "PlanCode";
		pk[1] = "RiskCode";
		pk[2] = "DutyCode";
		pk[3] = "GetDutyCode";
		pk[4] = "FeeCode";

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
		PD_LDPlanFeeRelaSchema cloned = (PD_LDPlanFeeRelaSchema)super.clone();
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
	* PF001--Diamond Core<p>
	* PF002--Golden Core<p>
	* PF003--Silver Core<p>
	* PF004--Diamond Option1<p>
	* PF005--Diamond Option2<p>
	* PF006--Golden Option 1<p>
	* PF007--Golden Option 2<p>
	* PF008--Plus Beneits for all plan
	*/
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>15)
			throw new IllegalArgumentException("保障计划代码PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值15");
		PlanCode = aPlanCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>15)
			throw new IllegalArgumentException("产品代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值15");
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>15)
			throw new IllegalArgumentException("责任代码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值15");
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>15)
			throw new IllegalArgumentException("给付责任代码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值15");
		GetDutyCode = aGetDutyCode;
	}
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		if(aFeeCode!=null && aFeeCode.length()>20)
			throw new IllegalArgumentException("账单费用项代码FeeCode值"+aFeeCode+"的长度"+aFeeCode.length()+"大于最大值20");
		FeeCode = aFeeCode;
	}
	public String getStandFlag2()
	{
		return StandFlag2;
	}
	public void setStandFlag2(String aStandFlag2)
	{
		if(aStandFlag2!=null && aStandFlag2.length()>200)
			throw new IllegalArgumentException("备用字段2StandFlag2值"+aStandFlag2+"的长度"+aStandFlag2.length()+"大于最大值200");
		StandFlag2 = aStandFlag2;
	}
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		if(aFeeType!=null && aFeeType.length()>20)
			throw new IllegalArgumentException("费用类型FeeType值"+aFeeType+"的长度"+aFeeType.length()+"大于最大值20");
		FeeType = aFeeType;
	}
	public String getPreAuthFlag()
	{
		return PreAuthFlag;
	}
	public void setPreAuthFlag(String aPreAuthFlag)
	{
		if(aPreAuthFlag!=null && aPreAuthFlag.length()>6)
			throw new IllegalArgumentException("是否预授权标识PreAuthFlag值"+aPreAuthFlag+"的长度"+aPreAuthFlag.length()+"大于最大值6");
		PreAuthFlag = aPreAuthFlag;
	}
	public String getMoneyLimitAnnual()
	{
		return MoneyLimitAnnual;
	}
	public void setMoneyLimitAnnual(String aMoneyLimitAnnual)
	{
		if(aMoneyLimitAnnual!=null && aMoneyLimitAnnual.length()>20)
			throw new IllegalArgumentException("保单年度金额上限MoneyLimitAnnual值"+aMoneyLimitAnnual+"的长度"+aMoneyLimitAnnual.length()+"大于最大值20");
		MoneyLimitAnnual = aMoneyLimitAnnual;
	}
	public String getCountLimitAnnual()
	{
		return CountLimitAnnual;
	}
	public void setCountLimitAnnual(String aCountLimitAnnual)
	{
		if(aCountLimitAnnual!=null && aCountLimitAnnual.length()>20)
			throw new IllegalArgumentException("保单年度次数上限CountLimitAnnual值"+aCountLimitAnnual+"的长度"+aCountLimitAnnual.length()+"大于最大值20");
		CountLimitAnnual = aCountLimitAnnual;
	}
	public String getDaysLimitAnnual()
	{
		return DaysLimitAnnual;
	}
	public void setDaysLimitAnnual(String aDaysLimitAnnual)
	{
		if(aDaysLimitAnnual!=null && aDaysLimitAnnual.length()>20)
			throw new IllegalArgumentException("保单年度天数上限DaysLimitAnnual值"+aDaysLimitAnnual+"的长度"+aDaysLimitAnnual.length()+"大于最大值20");
		DaysLimitAnnual = aDaysLimitAnnual;
	}
	public String getMoneyLimitEveryTime()
	{
		return MoneyLimitEveryTime;
	}
	public void setMoneyLimitEveryTime(String aMoneyLimitEveryTime)
	{
		if(aMoneyLimitEveryTime!=null && aMoneyLimitEveryTime.length()>20)
			throw new IllegalArgumentException("每次赔偿金额上限MoneyLimitEveryTime值"+aMoneyLimitEveryTime+"的长度"+aMoneyLimitEveryTime.length()+"大于最大值20");
		MoneyLimitEveryTime = aMoneyLimitEveryTime;
	}
	public String getDaysLimitEveryTime()
	{
		return DaysLimitEveryTime;
	}
	public void setDaysLimitEveryTime(String aDaysLimitEveryTime)
	{
		if(aDaysLimitEveryTime!=null && aDaysLimitEveryTime.length()>20)
			throw new IllegalArgumentException("每次赔偿天数上限DaysLimitEveryTime值"+aDaysLimitEveryTime+"的长度"+aDaysLimitEveryTime.length()+"大于最大值20");
		DaysLimitEveryTime = aDaysLimitEveryTime;
	}
	public String getMoneyLimitEveryDay()
	{
		return MoneyLimitEveryDay;
	}
	public void setMoneyLimitEveryDay(String aMoneyLimitEveryDay)
	{
		if(aMoneyLimitEveryDay!=null && aMoneyLimitEveryDay.length()>20)
			throw new IllegalArgumentException("每天赔付金额上限MoneyLimitEveryDay值"+aMoneyLimitEveryDay+"的长度"+aMoneyLimitEveryDay.length()+"大于最大值20");
		MoneyLimitEveryDay = aMoneyLimitEveryDay;
	}
	public String getPayMoneyEveryDay()
	{
		return PayMoneyEveryDay;
	}
	public void setPayMoneyEveryDay(String aPayMoneyEveryDay)
	{
		if(aPayMoneyEveryDay!=null && aPayMoneyEveryDay.length()>20)
			throw new IllegalArgumentException("每天赔偿固定金额PayMoneyEveryDay值"+aPayMoneyEveryDay+"的长度"+aPayMoneyEveryDay.length()+"大于最大值20");
		PayMoneyEveryDay = aPayMoneyEveryDay;
	}
	public String getObsPeriod()
	{
		return ObsPeriod;
	}
	public void setObsPeriod(String aObsPeriod)
	{
		if(aObsPeriod!=null && aObsPeriod.length()>20)
			throw new IllegalArgumentException("等待期ObsPeriod值"+aObsPeriod+"的长度"+aObsPeriod.length()+"大于最大值20");
		ObsPeriod = aObsPeriod;
	}
	public String getObsPeriodUn()
	{
		return ObsPeriodUn;
	}
	public void setObsPeriodUn(String aObsPeriodUn)
	{
		if(aObsPeriodUn!=null && aObsPeriodUn.length()>1)
			throw new IllegalArgumentException("等待期单位ObsPeriodUn值"+aObsPeriodUn+"的长度"+aObsPeriodUn.length()+"大于最大值1");
		ObsPeriodUn = aObsPeriodUn;
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
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getStandFlag1()
	{
		return StandFlag1;
	}
	public void setStandFlag1(String aStandFlag1)
	{
		if(aStandFlag1!=null && aStandFlag1.length()>20)
			throw new IllegalArgumentException("备用字段1StandFlag1值"+aStandFlag1+"的长度"+aStandFlag1.length()+"大于最大值20");
		StandFlag1 = aStandFlag1;
	}
	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		if(aRemark1!=null && aRemark1.length()>100)
			throw new IllegalArgumentException("备注1Remark1值"+aRemark1+"的长度"+aRemark1.length()+"大于最大值100");
		Remark1 = aRemark1;
	}
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		if(aRemark2!=null && aRemark2.length()>100)
			throw new IllegalArgumentException("备注2Remark2值"+aRemark2+"的长度"+aRemark2.length()+"大于最大值100");
		Remark2 = aRemark2;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}

	/**
	* 使用另外一个 PD_LDPlanFeeRelaSchema 对象给 Schema 赋值
	* @param: aPD_LDPlanFeeRelaSchema PD_LDPlanFeeRelaSchema
	**/
	public void setSchema(PD_LDPlanFeeRelaSchema aPD_LDPlanFeeRelaSchema)
	{
		this.PlanCode = aPD_LDPlanFeeRelaSchema.getPlanCode();
		this.RiskCode = aPD_LDPlanFeeRelaSchema.getRiskCode();
		this.DutyCode = aPD_LDPlanFeeRelaSchema.getDutyCode();
		this.GetDutyCode = aPD_LDPlanFeeRelaSchema.getGetDutyCode();
		this.FeeCode = aPD_LDPlanFeeRelaSchema.getFeeCode();
		this.StandFlag2 = aPD_LDPlanFeeRelaSchema.getStandFlag2();
		this.FeeType = aPD_LDPlanFeeRelaSchema.getFeeType();
		this.PreAuthFlag = aPD_LDPlanFeeRelaSchema.getPreAuthFlag();
		this.MoneyLimitAnnual = aPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual();
		this.CountLimitAnnual = aPD_LDPlanFeeRelaSchema.getCountLimitAnnual();
		this.DaysLimitAnnual = aPD_LDPlanFeeRelaSchema.getDaysLimitAnnual();
		this.MoneyLimitEveryTime = aPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime();
		this.DaysLimitEveryTime = aPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime();
		this.MoneyLimitEveryDay = aPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay();
		this.PayMoneyEveryDay = aPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay();
		this.ObsPeriod = aPD_LDPlanFeeRelaSchema.getObsPeriod();
		this.ObsPeriodUn = aPD_LDPlanFeeRelaSchema.getObsPeriodUn();
		this.Operator = aPD_LDPlanFeeRelaSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LDPlanFeeRelaSchema.getMakeDate());
		this.MakeTime = aPD_LDPlanFeeRelaSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LDPlanFeeRelaSchema.getModifyDate());
		this.ModifyTime = aPD_LDPlanFeeRelaSchema.getModifyTime();
		this.StandFlag1 = aPD_LDPlanFeeRelaSchema.getStandFlag1();
		this.Remark1 = aPD_LDPlanFeeRelaSchema.getRemark1();
		this.Remark2 = aPD_LDPlanFeeRelaSchema.getRemark2();
		this.ManageCom = aPD_LDPlanFeeRelaSchema.getManageCom();
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
			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

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

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("StandFlag2") == null )
				this.StandFlag2 = null;
			else
				this.StandFlag2 = rs.getString("StandFlag2").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			if( rs.getString("PreAuthFlag") == null )
				this.PreAuthFlag = null;
			else
				this.PreAuthFlag = rs.getString("PreAuthFlag").trim();

			if( rs.getString("MoneyLimitAnnual") == null )
				this.MoneyLimitAnnual = null;
			else
				this.MoneyLimitAnnual = rs.getString("MoneyLimitAnnual").trim();

			if( rs.getString("CountLimitAnnual") == null )
				this.CountLimitAnnual = null;
			else
				this.CountLimitAnnual = rs.getString("CountLimitAnnual").trim();

			if( rs.getString("DaysLimitAnnual") == null )
				this.DaysLimitAnnual = null;
			else
				this.DaysLimitAnnual = rs.getString("DaysLimitAnnual").trim();

			if( rs.getString("MoneyLimitEveryTime") == null )
				this.MoneyLimitEveryTime = null;
			else
				this.MoneyLimitEveryTime = rs.getString("MoneyLimitEveryTime").trim();

			if( rs.getString("DaysLimitEveryTime") == null )
				this.DaysLimitEveryTime = null;
			else
				this.DaysLimitEveryTime = rs.getString("DaysLimitEveryTime").trim();

			if( rs.getString("MoneyLimitEveryDay") == null )
				this.MoneyLimitEveryDay = null;
			else
				this.MoneyLimitEveryDay = rs.getString("MoneyLimitEveryDay").trim();

			if( rs.getString("PayMoneyEveryDay") == null )
				this.PayMoneyEveryDay = null;
			else
				this.PayMoneyEveryDay = rs.getString("PayMoneyEveryDay").trim();

			if( rs.getString("ObsPeriod") == null )
				this.ObsPeriod = null;
			else
				this.ObsPeriod = rs.getString("ObsPeriod").trim();

			if( rs.getString("ObsPeriodUn") == null )
				this.ObsPeriodUn = null;
			else
				this.ObsPeriodUn = rs.getString("ObsPeriodUn").trim();

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

			if( rs.getString("StandFlag1") == null )
				this.StandFlag1 = null;
			else
				this.StandFlag1 = rs.getString("StandFlag1").trim();

			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LDPlanFeeRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LDPlanFeeRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LDPlanFeeRelaSchema getSchema()
	{
		PD_LDPlanFeeRelaSchema aPD_LDPlanFeeRelaSchema = new PD_LDPlanFeeRelaSchema();
		aPD_LDPlanFeeRelaSchema.setSchema(this);
		return aPD_LDPlanFeeRelaSchema;
	}

	public PD_LDPlanFeeRelaDB getDB()
	{
		PD_LDPlanFeeRelaDB aDBOper = new PD_LDPlanFeeRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LDPlanFeeRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreAuthFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoneyLimitAnnual)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CountLimitAnnual)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DaysLimitAnnual)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoneyLimitEveryTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DaysLimitEveryTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoneyLimitEveryDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMoneyEveryDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ObsPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ObsPeriodUn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LDPlanFeeRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StandFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PreAuthFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MoneyLimitAnnual = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CountLimitAnnual = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DaysLimitAnnual = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MoneyLimitEveryTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			DaysLimitEveryTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MoneyLimitEveryDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PayMoneyEveryDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ObsPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ObsPeriodUn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			StandFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LDPlanFeeRelaSchema";
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
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("StandFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag2));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("PreAuthFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreAuthFlag));
		}
		if (FCode.equalsIgnoreCase("MoneyLimitAnnual"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoneyLimitAnnual));
		}
		if (FCode.equalsIgnoreCase("CountLimitAnnual"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CountLimitAnnual));
		}
		if (FCode.equalsIgnoreCase("DaysLimitAnnual"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DaysLimitAnnual));
		}
		if (FCode.equalsIgnoreCase("MoneyLimitEveryTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoneyLimitEveryTime));
		}
		if (FCode.equalsIgnoreCase("DaysLimitEveryTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DaysLimitEveryTime));
		}
		if (FCode.equalsIgnoreCase("MoneyLimitEveryDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoneyLimitEveryDay));
		}
		if (FCode.equalsIgnoreCase("PayMoneyEveryDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoneyEveryDay));
		}
		if (FCode.equalsIgnoreCase("ObsPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObsPeriod));
		}
		if (FCode.equalsIgnoreCase("ObsPeriodUn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObsPeriodUn));
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
		if (FCode.equalsIgnoreCase("StandFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag1));
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(StandFlag2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PreAuthFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MoneyLimitAnnual);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CountLimitAnnual);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DaysLimitAnnual);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MoneyLimitEveryTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(DaysLimitEveryTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MoneyLimitEveryDay);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PayMoneyEveryDay);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ObsPeriod);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ObsPeriodUn);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(StandFlag1);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remark1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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

		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag2 = FValue.trim();
			}
			else
				StandFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		if (FCode.equalsIgnoreCase("PreAuthFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreAuthFlag = FValue.trim();
			}
			else
				PreAuthFlag = null;
		}
		if (FCode.equalsIgnoreCase("MoneyLimitAnnual"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoneyLimitAnnual = FValue.trim();
			}
			else
				MoneyLimitAnnual = null;
		}
		if (FCode.equalsIgnoreCase("CountLimitAnnual"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CountLimitAnnual = FValue.trim();
			}
			else
				CountLimitAnnual = null;
		}
		if (FCode.equalsIgnoreCase("DaysLimitAnnual"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DaysLimitAnnual = FValue.trim();
			}
			else
				DaysLimitAnnual = null;
		}
		if (FCode.equalsIgnoreCase("MoneyLimitEveryTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoneyLimitEveryTime = FValue.trim();
			}
			else
				MoneyLimitEveryTime = null;
		}
		if (FCode.equalsIgnoreCase("DaysLimitEveryTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DaysLimitEveryTime = FValue.trim();
			}
			else
				DaysLimitEveryTime = null;
		}
		if (FCode.equalsIgnoreCase("MoneyLimitEveryDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoneyLimitEveryDay = FValue.trim();
			}
			else
				MoneyLimitEveryDay = null;
		}
		if (FCode.equalsIgnoreCase("PayMoneyEveryDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMoneyEveryDay = FValue.trim();
			}
			else
				PayMoneyEveryDay = null;
		}
		if (FCode.equalsIgnoreCase("ObsPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ObsPeriod = FValue.trim();
			}
			else
				ObsPeriod = null;
		}
		if (FCode.equalsIgnoreCase("ObsPeriodUn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ObsPeriodUn = FValue.trim();
			}
			else
				ObsPeriodUn = null;
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
		if (FCode.equalsIgnoreCase("StandFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag1 = FValue.trim();
			}
			else
				StandFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark1 = FValue.trim();
			}
			else
				Remark1 = null;
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LDPlanFeeRelaSchema other = (PD_LDPlanFeeRelaSchema)otherObject;
		return
			PlanCode.equals(other.getPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& FeeCode.equals(other.getFeeCode())
			&& StandFlag2.equals(other.getStandFlag2())
			&& FeeType.equals(other.getFeeType())
			&& PreAuthFlag.equals(other.getPreAuthFlag())
			&& MoneyLimitAnnual.equals(other.getMoneyLimitAnnual())
			&& CountLimitAnnual.equals(other.getCountLimitAnnual())
			&& DaysLimitAnnual.equals(other.getDaysLimitAnnual())
			&& MoneyLimitEveryTime.equals(other.getMoneyLimitEveryTime())
			&& DaysLimitEveryTime.equals(other.getDaysLimitEveryTime())
			&& MoneyLimitEveryDay.equals(other.getMoneyLimitEveryDay())
			&& PayMoneyEveryDay.equals(other.getPayMoneyEveryDay())
			&& ObsPeriod.equals(other.getObsPeriod())
			&& ObsPeriodUn.equals(other.getObsPeriodUn())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StandFlag1.equals(other.getStandFlag1())
			&& Remark1.equals(other.getRemark1())
			&& Remark2.equals(other.getRemark2())
			&& ManageCom.equals(other.getManageCom());
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
		if( strFieldName.equals("PlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 3;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 4;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return 5;
		}
		if( strFieldName.equals("FeeType") ) {
			return 6;
		}
		if( strFieldName.equals("PreAuthFlag") ) {
			return 7;
		}
		if( strFieldName.equals("MoneyLimitAnnual") ) {
			return 8;
		}
		if( strFieldName.equals("CountLimitAnnual") ) {
			return 9;
		}
		if( strFieldName.equals("DaysLimitAnnual") ) {
			return 10;
		}
		if( strFieldName.equals("MoneyLimitEveryTime") ) {
			return 11;
		}
		if( strFieldName.equals("DaysLimitEveryTime") ) {
			return 12;
		}
		if( strFieldName.equals("MoneyLimitEveryDay") ) {
			return 13;
		}
		if( strFieldName.equals("PayMoneyEveryDay") ) {
			return 14;
		}
		if( strFieldName.equals("ObsPeriod") ) {
			return 15;
		}
		if( strFieldName.equals("ObsPeriodUn") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("StandFlag1") ) {
			return 22;
		}
		if( strFieldName.equals("Remark1") ) {
			return 23;
		}
		if( strFieldName.equals("Remark2") ) {
			return 24;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				strFieldName = "PlanCode";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "GetDutyCode";
				break;
			case 4:
				strFieldName = "FeeCode";
				break;
			case 5:
				strFieldName = "StandFlag2";
				break;
			case 6:
				strFieldName = "FeeType";
				break;
			case 7:
				strFieldName = "PreAuthFlag";
				break;
			case 8:
				strFieldName = "MoneyLimitAnnual";
				break;
			case 9:
				strFieldName = "CountLimitAnnual";
				break;
			case 10:
				strFieldName = "DaysLimitAnnual";
				break;
			case 11:
				strFieldName = "MoneyLimitEveryTime";
				break;
			case 12:
				strFieldName = "DaysLimitEveryTime";
				break;
			case 13:
				strFieldName = "MoneyLimitEveryDay";
				break;
			case 14:
				strFieldName = "PayMoneyEveryDay";
				break;
			case 15:
				strFieldName = "ObsPeriod";
				break;
			case 16:
				strFieldName = "ObsPeriodUn";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "StandFlag1";
				break;
			case 23:
				strFieldName = "Remark1";
				break;
			case 24:
				strFieldName = "Remark2";
				break;
			case 25:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("PlanCode") ) {
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
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreAuthFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MoneyLimitAnnual") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CountLimitAnnual") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DaysLimitAnnual") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MoneyLimitEveryTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DaysLimitEveryTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MoneyLimitEveryDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoneyEveryDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ObsPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ObsPeriodUn") ) {
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
		if( strFieldName.equals("StandFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
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
