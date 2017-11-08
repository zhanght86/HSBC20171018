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
import com.sinosoft.lis.db.LPGrpEnginStateDB;

/*
 * <p>ClassName: LPGrpEnginStateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPGrpEnginStateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGrpEnginStateSchema.class);
	// @Field
	/** 保全批单号 */
	private String EdorNo;
	/** 保全项目 */
	private String EdorType;
	/** 团体保单号 */
	private String GrpContNo;
	/** 险种编码 */
	private String RiskCode;
	/** 变更后工程面积 */
	private double EnginArea;
	/** 变更后工程造价 */
	private double EnginCost;
	/** 中止日期 */
	private Date StopDate;
	/** 复效日期 */
	private Date ReStartDate;
	/** 满期延期日期 */
	private Date DeferDate;
	/** 工程延期天数 */
	private int DeferDays;
	/** 期初保额 */
	private double InitAmnt;
	/** 原因 */
	private String Reason;
	/** 描述 */
	private String ReasonDesc;
	/** 次数 */
	private int Times;
	/** 标准保费 */
	private double StandPrem;
	/** 实交保费 */
	private double GetMoney;
	/** 备注 */
	private String Remark;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGrpEnginStateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "GrpContNo";

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
		LPGrpEnginStateSchema cloned = (LPGrpEnginStateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
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
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("保全批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>10)
			throw new IllegalArgumentException("保全项目EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值10");
		EdorType = aEdorType;
	}
	/**
	* SGP+两位年份+4位省市行政代码(保监）+9位流水
	*/
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	public double getEnginArea()
	{
		return EnginArea;
	}
	public void setEnginArea(double aEnginArea)
	{
		EnginArea = aEnginArea;
	}
	public void setEnginArea(String aEnginArea)
	{
		if (aEnginArea != null && !aEnginArea.equals(""))
		{
			Double tDouble = new Double(aEnginArea);
			double d = tDouble.doubleValue();
			EnginArea = d;
		}
	}

	public double getEnginCost()
	{
		return EnginCost;
	}
	public void setEnginCost(double aEnginCost)
	{
		EnginCost = aEnginCost;
	}
	public void setEnginCost(String aEnginCost)
	{
		if (aEnginCost != null && !aEnginCost.equals(""))
		{
			Double tDouble = new Double(aEnginCost);
			double d = tDouble.doubleValue();
			EnginCost = d;
		}
	}

	public String getStopDate()
	{
		if( StopDate != null )
			return fDate.getString(StopDate);
		else
			return null;
	}
	public void setStopDate(Date aStopDate)
	{
		StopDate = aStopDate;
	}
	public void setStopDate(String aStopDate)
	{
		if (aStopDate != null && !aStopDate.equals("") )
		{
			StopDate = fDate.getDate( aStopDate );
		}
		else
			StopDate = null;
	}

	public String getReStartDate()
	{
		if( ReStartDate != null )
			return fDate.getString(ReStartDate);
		else
			return null;
	}
	public void setReStartDate(Date aReStartDate)
	{
		ReStartDate = aReStartDate;
	}
	public void setReStartDate(String aReStartDate)
	{
		if (aReStartDate != null && !aReStartDate.equals("") )
		{
			ReStartDate = fDate.getDate( aReStartDate );
		}
		else
			ReStartDate = null;
	}

	public String getDeferDate()
	{
		if( DeferDate != null )
			return fDate.getString(DeferDate);
		else
			return null;
	}
	public void setDeferDate(Date aDeferDate)
	{
		DeferDate = aDeferDate;
	}
	public void setDeferDate(String aDeferDate)
	{
		if (aDeferDate != null && !aDeferDate.equals("") )
		{
			DeferDate = fDate.getDate( aDeferDate );
		}
		else
			DeferDate = null;
	}

	public int getDeferDays()
	{
		return DeferDays;
	}
	public void setDeferDays(int aDeferDays)
	{
		DeferDays = aDeferDays;
	}
	public void setDeferDays(String aDeferDays)
	{
		if (aDeferDays != null && !aDeferDays.equals(""))
		{
			Integer tInteger = new Integer(aDeferDays);
			int i = tInteger.intValue();
			DeferDays = i;
		}
	}

	/**
	* 期初基本保额
	*/
	public double getInitAmnt()
	{
		return InitAmnt;
	}
	public void setInitAmnt(double aInitAmnt)
	{
		InitAmnt = aInitAmnt;
	}
	public void setInitAmnt(String aInitAmnt)
	{
		if (aInitAmnt != null && !aInitAmnt.equals(""))
		{
			Double tDouble = new Double(aInitAmnt);
			double d = tDouble.doubleValue();
			InitAmnt = d;
		}
	}

	/**
	* 00-施工总承包资质，01-专业承包资质，02-劳务分包资质
	*/
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>2)
			throw new IllegalArgumentException("原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值2");
		Reason = aReason;
	}
	public String getReasonDesc()
	{
		return ReasonDesc;
	}
	public void setReasonDesc(String aReasonDesc)
	{
		if(aReasonDesc!=null && aReasonDesc.length()>3000)
			throw new IllegalArgumentException("描述ReasonDesc值"+aReasonDesc+"的长度"+aReasonDesc.length()+"大于最大值3000");
		ReasonDesc = aReasonDesc;
	}
	/**
	* 00-特级资质标准，01-一级资质标准，02-二级资质标准，03-三级资质标准，04-其他
	*/
	public int getTimes()
	{
		return Times;
	}
	public void setTimes(int aTimes)
	{
		Times = aTimes;
	}
	public void setTimes(String aTimes)
	{
		if (aTimes != null && !aTimes.equals(""))
		{
			Integer tInteger = new Integer(aTimes);
			int i = tInteger.intValue();
			Times = i;
		}
	}

	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>3000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值3000");
		Remark = aRemark;
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
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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

	/**
	* 使用另外一个 LPGrpEnginStateSchema 对象给 Schema 赋值
	* @param: aLPGrpEnginStateSchema LPGrpEnginStateSchema
	**/
	public void setSchema(LPGrpEnginStateSchema aLPGrpEnginStateSchema)
	{
		this.EdorNo = aLPGrpEnginStateSchema.getEdorNo();
		this.EdorType = aLPGrpEnginStateSchema.getEdorType();
		this.GrpContNo = aLPGrpEnginStateSchema.getGrpContNo();
		this.RiskCode = aLPGrpEnginStateSchema.getRiskCode();
		this.EnginArea = aLPGrpEnginStateSchema.getEnginArea();
		this.EnginCost = aLPGrpEnginStateSchema.getEnginCost();
		this.StopDate = fDate.getDate( aLPGrpEnginStateSchema.getStopDate());
		this.ReStartDate = fDate.getDate( aLPGrpEnginStateSchema.getReStartDate());
		this.DeferDate = fDate.getDate( aLPGrpEnginStateSchema.getDeferDate());
		this.DeferDays = aLPGrpEnginStateSchema.getDeferDays();
		this.InitAmnt = aLPGrpEnginStateSchema.getInitAmnt();
		this.Reason = aLPGrpEnginStateSchema.getReason();
		this.ReasonDesc = aLPGrpEnginStateSchema.getReasonDesc();
		this.Times = aLPGrpEnginStateSchema.getTimes();
		this.StandPrem = aLPGrpEnginStateSchema.getStandPrem();
		this.GetMoney = aLPGrpEnginStateSchema.getGetMoney();
		this.Remark = aLPGrpEnginStateSchema.getRemark();
		this.ManageCom = aLPGrpEnginStateSchema.getManageCom();
		this.ComCode = aLPGrpEnginStateSchema.getComCode();
		this.MakeOperator = aLPGrpEnginStateSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPGrpEnginStateSchema.getMakeDate());
		this.MakeTime = aLPGrpEnginStateSchema.getMakeTime();
		this.ModifyOperator = aLPGrpEnginStateSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPGrpEnginStateSchema.getModifyDate());
		this.ModifyTime = aLPGrpEnginStateSchema.getModifyTime();
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

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.EnginArea = rs.getDouble("EnginArea");
			this.EnginCost = rs.getDouble("EnginCost");
			this.StopDate = rs.getDate("StopDate");
			this.ReStartDate = rs.getDate("ReStartDate");
			this.DeferDate = rs.getDate("DeferDate");
			this.DeferDays = rs.getInt("DeferDays");
			this.InitAmnt = rs.getDouble("InitAmnt");
			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("ReasonDesc") == null )
				this.ReasonDesc = null;
			else
				this.ReasonDesc = rs.getString("ReasonDesc").trim();

			this.Times = rs.getInt("Times");
			this.StandPrem = rs.getDouble("StandPrem");
			this.GetMoney = rs.getDouble("GetMoney");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPGrpEnginState表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpEnginStateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGrpEnginStateSchema getSchema()
	{
		LPGrpEnginStateSchema aLPGrpEnginStateSchema = new LPGrpEnginStateSchema();
		aLPGrpEnginStateSchema.setSchema(this);
		return aLPGrpEnginStateSchema;
	}

	public LPGrpEnginStateDB getDB()
	{
		LPGrpEnginStateDB aDBOper = new LPGrpEnginStateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpEnginState描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnginArea));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnginCost));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StopDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeferDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeferDays));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Times));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpEnginState>历史记账凭证主表信息</A>表字段
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
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EnginArea = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			EnginCost = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			StopDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ReStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			DeferDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			DeferDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			InitAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Times= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpEnginStateSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("EnginArea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginArea));
		}
		if (FCode.equalsIgnoreCase("EnginCost"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginCost));
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
		}
		if (FCode.equalsIgnoreCase("ReStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReStartDate()));
		}
		if (FCode.equalsIgnoreCase("DeferDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeferDate()));
		}
		if (FCode.equalsIgnoreCase("DeferDays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeferDays));
		}
		if (FCode.equalsIgnoreCase("InitAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitAmnt));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("ReasonDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonDesc));
		}
		if (FCode.equalsIgnoreCase("Times"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Times));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = String.valueOf(EnginArea);
				break;
			case 5:
				strFieldValue = String.valueOf(EnginCost);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReStartDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeferDate()));
				break;
			case 9:
				strFieldValue = String.valueOf(DeferDays);
				break;
			case 10:
				strFieldValue = String.valueOf(InitAmnt);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ReasonDesc);
				break;
			case 13:
				strFieldValue = String.valueOf(Times);
				break;
			case 14:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 15:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("EnginArea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnginArea = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnginCost"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnginCost = d;
			}
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StopDate = fDate.getDate( FValue );
			}
			else
				StopDate = null;
		}
		if (FCode.equalsIgnoreCase("ReStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReStartDate = fDate.getDate( FValue );
			}
			else
				ReStartDate = null;
		}
		if (FCode.equalsIgnoreCase("DeferDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeferDate = fDate.getDate( FValue );
			}
			else
				DeferDate = null;
		}
		if (FCode.equalsIgnoreCase("DeferDays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DeferDays = i;
			}
		}
		if (FCode.equalsIgnoreCase("InitAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("ReasonDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonDesc = FValue.trim();
			}
			else
				ReasonDesc = null;
		}
		if (FCode.equalsIgnoreCase("Times"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Times = i;
			}
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPGrpEnginStateSchema other = (LPGrpEnginStateSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& EnginArea == other.getEnginArea()
			&& EnginCost == other.getEnginCost()
			&& fDate.getString(StopDate).equals(other.getStopDate())
			&& fDate.getString(ReStartDate).equals(other.getReStartDate())
			&& fDate.getString(DeferDate).equals(other.getDeferDate())
			&& DeferDays == other.getDeferDays()
			&& InitAmnt == other.getInitAmnt()
			&& Reason.equals(other.getReason())
			&& ReasonDesc.equals(other.getReasonDesc())
			&& Times == other.getTimes()
			&& StandPrem == other.getStandPrem()
			&& GetMoney == other.getGetMoney()
			&& Remark.equals(other.getRemark())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("EnginArea") ) {
			return 4;
		}
		if( strFieldName.equals("EnginCost") ) {
			return 5;
		}
		if( strFieldName.equals("StopDate") ) {
			return 6;
		}
		if( strFieldName.equals("ReStartDate") ) {
			return 7;
		}
		if( strFieldName.equals("DeferDate") ) {
			return 8;
		}
		if( strFieldName.equals("DeferDays") ) {
			return 9;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return 10;
		}
		if( strFieldName.equals("Reason") ) {
			return 11;
		}
		if( strFieldName.equals("ReasonDesc") ) {
			return 12;
		}
		if( strFieldName.equals("Times") ) {
			return 13;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 14;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 15;
		}
		if( strFieldName.equals("Remark") ) {
			return 16;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 17;
		}
		if( strFieldName.equals("ComCode") ) {
			return 18;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
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
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "EnginArea";
				break;
			case 5:
				strFieldName = "EnginCost";
				break;
			case 6:
				strFieldName = "StopDate";
				break;
			case 7:
				strFieldName = "ReStartDate";
				break;
			case 8:
				strFieldName = "DeferDate";
				break;
			case 9:
				strFieldName = "DeferDays";
				break;
			case 10:
				strFieldName = "InitAmnt";
				break;
			case 11:
				strFieldName = "Reason";
				break;
			case 12:
				strFieldName = "ReasonDesc";
				break;
			case 13:
				strFieldName = "Times";
				break;
			case 14:
				strFieldName = "StandPrem";
				break;
			case 15:
				strFieldName = "GetMoney";
				break;
			case 16:
				strFieldName = "Remark";
				break;
			case 17:
				strFieldName = "ManageCom";
				break;
			case 18:
				strFieldName = "ComCode";
				break;
			case 19:
				strFieldName = "MakeOperator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyOperator";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginArea") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnginCost") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StopDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DeferDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DeferDays") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Times") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
