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
import com.sinosoft.lis.db.LPPBalanceOnDB;

/*
 * <p>ClassName: LPPBalanceOnSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPPBalanceOnSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPPBalanceOnSchema.class);
	// @Field
	/** 保全批单号 */
	private String EdorNo;
	/** 保全项目 */
	private String EdorType;
	/** 团体保单号 */
	private String GrpContNo;
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 定期结算业务开通日期 */
	private Date BalanceOnDate;
	/** 定期结算业务开通操作员 */
	private String BalanceOnOperator;
	/** 定期结算开通状态 */
	private String BalanceOnState;
	/** 定期结算周期 */
	private String BalancePeriod;
	/** 定期结算金额上限 */
	private double BalanceSumLimit;
	/** 当前定期结算金额合计 */
	private double BalanceSum;
	/** 最后一次定期结算方式 */
	private String BalanceType;
	/** 最后一次定期结算日期 */
	private Date LastBalanceDate;
	/** 最后一次定期结算时间 */
	private String LastBalanceTime;
	/** 下次定期结算周期日期 */
	private Date NextBalanceDate;
	/** 宽限期 */
	private String GracePeriod;
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

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPPBalanceOnSchema()
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
		LPPBalanceOnSchema cloned = (LPPBalanceOnSchema)super.clone();
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
	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("团体投保单号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
	}
	/**
	* 在开通定期结算业务时指定开通日期
	*/
	public String getBalanceOnDate()
	{
		if( BalanceOnDate != null )
			return fDate.getString(BalanceOnDate);
		else
			return null;
	}
	public void setBalanceOnDate(Date aBalanceOnDate)
	{
		BalanceOnDate = aBalanceOnDate;
	}
	public void setBalanceOnDate(String aBalanceOnDate)
	{
		if (aBalanceOnDate != null && !aBalanceOnDate.equals("") )
		{
			BalanceOnDate = fDate.getDate( aBalanceOnDate );
		}
		else
			BalanceOnDate = null;
	}

	public String getBalanceOnOperator()
	{
		return BalanceOnOperator;
	}
	public void setBalanceOnOperator(String aBalanceOnOperator)
	{
		if(aBalanceOnOperator!=null && aBalanceOnOperator.length()>30)
			throw new IllegalArgumentException("定期结算业务开通操作员BalanceOnOperator值"+aBalanceOnOperator+"的长度"+aBalanceOnOperator.length()+"大于最大值30");
		BalanceOnOperator = aBalanceOnOperator;
	}
	/**
	* 开通状态:<p>
	* 0 －开通状态;<p>
	* 1 －开通关闭状态;<p>
	* 2 －满期关闭状态;
	*/
	public String getBalanceOnState()
	{
		return BalanceOnState;
	}
	public void setBalanceOnState(String aBalanceOnState)
	{
		if(aBalanceOnState!=null && aBalanceOnState.length()>2)
			throw new IllegalArgumentException("定期结算开通状态BalanceOnState值"+aBalanceOnState+"的长度"+aBalanceOnState.length()+"大于最大值2");
		BalanceOnState = aBalanceOnState;
	}
	/**
	* 定期结算周期：1－12个月
	*/
	public String getBalancePeriod()
	{
		return BalancePeriod;
	}
	public void setBalancePeriod(String aBalancePeriod)
	{
		if(aBalancePeriod!=null && aBalancePeriod.length()>2)
			throw new IllegalArgumentException("定期结算周期BalancePeriod值"+aBalancePeriod+"的长度"+aBalancePeriod.length()+"大于最大值2");
		BalancePeriod = aBalancePeriod;
	}
	public double getBalanceSumLimit()
	{
		return BalanceSumLimit;
	}
	public void setBalanceSumLimit(double aBalanceSumLimit)
	{
		BalanceSumLimit = aBalanceSumLimit;
	}
	public void setBalanceSumLimit(String aBalanceSumLimit)
	{
		if (aBalanceSumLimit != null && !aBalanceSumLimit.equals(""))
		{
			Double tDouble = new Double(aBalanceSumLimit);
			double d = tDouble.doubleValue();
			BalanceSumLimit = d;
		}
	}

	/**
	* 在保全中对该金额进行合计处理
	*/
	public double getBalanceSum()
	{
		return BalanceSum;
	}
	public void setBalanceSum(double aBalanceSum)
	{
		BalanceSum = aBalanceSum;
	}
	public void setBalanceSum(String aBalanceSum)
	{
		if (aBalanceSum != null && !aBalanceSum.equals(""))
		{
			Double tDouble = new Double(aBalanceSum);
			double d = tDouble.doubleValue();
			BalanceSum = d;
		}
	}

	/**
	* 定期结算方式：<p>
	* -1 －未定期结算<p>
	* 0 －到达结算周期<p>
	* 1 －到达金额上限<p>
	* 2 －手动定期结算
	*/
	public String getBalanceType()
	{
		return BalanceType;
	}
	public void setBalanceType(String aBalanceType)
	{
		if(aBalanceType!=null && aBalanceType.length()>2)
			throw new IllegalArgumentException("最后一次定期结算方式BalanceType值"+aBalanceType+"的长度"+aBalanceType.length()+"大于最大值2");
		BalanceType = aBalanceType;
	}
	/**
	* 到达定期结算周期或到达金额上限或手动定期结算的处理日期
	*/
	public String getLastBalanceDate()
	{
		if( LastBalanceDate != null )
			return fDate.getString(LastBalanceDate);
		else
			return null;
	}
	public void setLastBalanceDate(Date aLastBalanceDate)
	{
		LastBalanceDate = aLastBalanceDate;
	}
	public void setLastBalanceDate(String aLastBalanceDate)
	{
		if (aLastBalanceDate != null && !aLastBalanceDate.equals("") )
		{
			LastBalanceDate = fDate.getDate( aLastBalanceDate );
		}
		else
			LastBalanceDate = null;
	}

	/**
	* 到达定期结算周期或到达金额上限或手动定期结算的处理时间
	*/
	public String getLastBalanceTime()
	{
		return LastBalanceTime;
	}
	public void setLastBalanceTime(String aLastBalanceTime)
	{
		if(aLastBalanceTime!=null && aLastBalanceTime.length()>8)
			throw new IllegalArgumentException("最后一次定期结算时间LastBalanceTime值"+aLastBalanceTime+"的长度"+aLastBalanceTime.length()+"大于最大值8");
		LastBalanceTime = aLastBalanceTime;
	}
	/**
	* 按照结算周期确定的结算日期
	*/
	public String getNextBalanceDate()
	{
		if( NextBalanceDate != null )
			return fDate.getString(NextBalanceDate);
		else
			return null;
	}
	public void setNextBalanceDate(Date aNextBalanceDate)
	{
		NextBalanceDate = aNextBalanceDate;
	}
	public void setNextBalanceDate(String aNextBalanceDate)
	{
		if (aNextBalanceDate != null && !aNextBalanceDate.equals("") )
		{
			NextBalanceDate = fDate.getDate( aNextBalanceDate );
		}
		else
			NextBalanceDate = null;
	}

	public String getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if(aGracePeriod!=null && aGracePeriod.length()>20)
			throw new IllegalArgumentException("宽限期GracePeriod值"+aGracePeriod+"的长度"+aGracePeriod.length()+"大于最大值20");
		GracePeriod = aGracePeriod;
	}
	/**
	* 承保管理机构
	*/
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
	* 承保公司代码
	*/
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
	* 使用另外一个 LPPBalanceOnSchema 对象给 Schema 赋值
	* @param: aLPPBalanceOnSchema LPPBalanceOnSchema
	**/
	public void setSchema(LPPBalanceOnSchema aLPPBalanceOnSchema)
	{
		this.EdorNo = aLPPBalanceOnSchema.getEdorNo();
		this.EdorType = aLPPBalanceOnSchema.getEdorType();
		this.GrpContNo = aLPPBalanceOnSchema.getGrpContNo();
		this.GrpPropNo = aLPPBalanceOnSchema.getGrpPropNo();
		this.BalanceOnDate = fDate.getDate( aLPPBalanceOnSchema.getBalanceOnDate());
		this.BalanceOnOperator = aLPPBalanceOnSchema.getBalanceOnOperator();
		this.BalanceOnState = aLPPBalanceOnSchema.getBalanceOnState();
		this.BalancePeriod = aLPPBalanceOnSchema.getBalancePeriod();
		this.BalanceSumLimit = aLPPBalanceOnSchema.getBalanceSumLimit();
		this.BalanceSum = aLPPBalanceOnSchema.getBalanceSum();
		this.BalanceType = aLPPBalanceOnSchema.getBalanceType();
		this.LastBalanceDate = fDate.getDate( aLPPBalanceOnSchema.getLastBalanceDate());
		this.LastBalanceTime = aLPPBalanceOnSchema.getLastBalanceTime();
		this.NextBalanceDate = fDate.getDate( aLPPBalanceOnSchema.getNextBalanceDate());
		this.GracePeriod = aLPPBalanceOnSchema.getGracePeriod();
		this.ManageCom = aLPPBalanceOnSchema.getManageCom();
		this.ComCode = aLPPBalanceOnSchema.getComCode();
		this.MakeOperator = aLPPBalanceOnSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPPBalanceOnSchema.getMakeDate());
		this.MakeTime = aLPPBalanceOnSchema.getMakeTime();
		this.ModifyOperator = aLPPBalanceOnSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPPBalanceOnSchema.getModifyDate());
		this.ModifyTime = aLPPBalanceOnSchema.getModifyTime();
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

			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			this.BalanceOnDate = rs.getDate("BalanceOnDate");
			if( rs.getString("BalanceOnOperator") == null )
				this.BalanceOnOperator = null;
			else
				this.BalanceOnOperator = rs.getString("BalanceOnOperator").trim();

			if( rs.getString("BalanceOnState") == null )
				this.BalanceOnState = null;
			else
				this.BalanceOnState = rs.getString("BalanceOnState").trim();

			if( rs.getString("BalancePeriod") == null )
				this.BalancePeriod = null;
			else
				this.BalancePeriod = rs.getString("BalancePeriod").trim();

			this.BalanceSumLimit = rs.getDouble("BalanceSumLimit");
			this.BalanceSum = rs.getDouble("BalanceSum");
			if( rs.getString("BalanceType") == null )
				this.BalanceType = null;
			else
				this.BalanceType = rs.getString("BalanceType").trim();

			this.LastBalanceDate = rs.getDate("LastBalanceDate");
			if( rs.getString("LastBalanceTime") == null )
				this.LastBalanceTime = null;
			else
				this.LastBalanceTime = rs.getString("LastBalanceTime").trim();

			this.NextBalanceDate = rs.getDate("NextBalanceDate");
			if( rs.getString("GracePeriod") == null )
				this.GracePeriod = null;
			else
				this.GracePeriod = rs.getString("GracePeriod").trim();

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
			logger.debug("数据库中的LPPBalanceOn表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPBalanceOnSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPPBalanceOnSchema getSchema()
	{
		LPPBalanceOnSchema aLPPBalanceOnSchema = new LPPBalanceOnSchema();
		aLPPBalanceOnSchema.setSchema(this);
		return aLPPBalanceOnSchema;
	}

	public LPPBalanceOnDB getDB()
	{
		LPPBalanceOnDB aDBOper = new LPPBalanceOnDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPPBalanceOn描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalanceOnDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceOnOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceOnState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalancePeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BalanceSumLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BalanceSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastBalanceDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastBalanceTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( NextBalanceDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GracePeriod)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPPBalanceOn>历史记账凭证主表信息</A>表字段
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
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BalanceOnDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			BalanceOnOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BalanceOnState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BalancePeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BalanceSumLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			BalanceSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			BalanceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			LastBalanceDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			LastBalanceTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			NextBalanceDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			GracePeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPBalanceOnSchema";
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
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("BalanceOnDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalanceOnDate()));
		}
		if (FCode.equalsIgnoreCase("BalanceOnOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceOnOperator));
		}
		if (FCode.equalsIgnoreCase("BalanceOnState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceOnState));
		}
		if (FCode.equalsIgnoreCase("BalancePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalancePeriod));
		}
		if (FCode.equalsIgnoreCase("BalanceSumLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceSumLimit));
		}
		if (FCode.equalsIgnoreCase("BalanceSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceSum));
		}
		if (FCode.equalsIgnoreCase("BalanceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceType));
		}
		if (FCode.equalsIgnoreCase("LastBalanceDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastBalanceDate()));
		}
		if (FCode.equalsIgnoreCase("LastBalanceTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastBalanceTime));
		}
		if (FCode.equalsIgnoreCase("NextBalanceDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getNextBalanceDate()));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalanceOnDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BalanceOnOperator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BalanceOnState);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BalancePeriod);
				break;
			case 8:
				strFieldValue = String.valueOf(BalanceSumLimit);
				break;
			case 9:
				strFieldValue = String.valueOf(BalanceSum);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BalanceType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastBalanceDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(LastBalanceTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getNextBalanceDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GracePeriod);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
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
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("BalanceOnDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalanceOnDate = fDate.getDate( FValue );
			}
			else
				BalanceOnDate = null;
		}
		if (FCode.equalsIgnoreCase("BalanceOnOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceOnOperator = FValue.trim();
			}
			else
				BalanceOnOperator = null;
		}
		if (FCode.equalsIgnoreCase("BalanceOnState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceOnState = FValue.trim();
			}
			else
				BalanceOnState = null;
		}
		if (FCode.equalsIgnoreCase("BalancePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalancePeriod = FValue.trim();
			}
			else
				BalancePeriod = null;
		}
		if (FCode.equalsIgnoreCase("BalanceSumLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BalanceSumLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("BalanceSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BalanceSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("BalanceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceType = FValue.trim();
			}
			else
				BalanceType = null;
		}
		if (FCode.equalsIgnoreCase("LastBalanceDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastBalanceDate = fDate.getDate( FValue );
			}
			else
				LastBalanceDate = null;
		}
		if (FCode.equalsIgnoreCase("LastBalanceTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastBalanceTime = FValue.trim();
			}
			else
				LastBalanceTime = null;
		}
		if (FCode.equalsIgnoreCase("NextBalanceDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				NextBalanceDate = fDate.getDate( FValue );
			}
			else
				NextBalanceDate = null;
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GracePeriod = FValue.trim();
			}
			else
				GracePeriod = null;
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
		LPPBalanceOnSchema other = (LPPBalanceOnSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& fDate.getString(BalanceOnDate).equals(other.getBalanceOnDate())
			&& BalanceOnOperator.equals(other.getBalanceOnOperator())
			&& BalanceOnState.equals(other.getBalanceOnState())
			&& BalancePeriod.equals(other.getBalancePeriod())
			&& BalanceSumLimit == other.getBalanceSumLimit()
			&& BalanceSum == other.getBalanceSum()
			&& BalanceType.equals(other.getBalanceType())
			&& fDate.getString(LastBalanceDate).equals(other.getLastBalanceDate())
			&& LastBalanceTime.equals(other.getLastBalanceTime())
			&& fDate.getString(NextBalanceDate).equals(other.getNextBalanceDate())
			&& GracePeriod.equals(other.getGracePeriod())
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
		if( strFieldName.equals("GrpPropNo") ) {
			return 3;
		}
		if( strFieldName.equals("BalanceOnDate") ) {
			return 4;
		}
		if( strFieldName.equals("BalanceOnOperator") ) {
			return 5;
		}
		if( strFieldName.equals("BalanceOnState") ) {
			return 6;
		}
		if( strFieldName.equals("BalancePeriod") ) {
			return 7;
		}
		if( strFieldName.equals("BalanceSumLimit") ) {
			return 8;
		}
		if( strFieldName.equals("BalanceSum") ) {
			return 9;
		}
		if( strFieldName.equals("BalanceType") ) {
			return 10;
		}
		if( strFieldName.equals("LastBalanceDate") ) {
			return 11;
		}
		if( strFieldName.equals("LastBalanceTime") ) {
			return 12;
		}
		if( strFieldName.equals("NextBalanceDate") ) {
			return 13;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 14;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 15;
		}
		if( strFieldName.equals("ComCode") ) {
			return 16;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
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
				strFieldName = "GrpPropNo";
				break;
			case 4:
				strFieldName = "BalanceOnDate";
				break;
			case 5:
				strFieldName = "BalanceOnOperator";
				break;
			case 6:
				strFieldName = "BalanceOnState";
				break;
			case 7:
				strFieldName = "BalancePeriod";
				break;
			case 8:
				strFieldName = "BalanceSumLimit";
				break;
			case 9:
				strFieldName = "BalanceSum";
				break;
			case 10:
				strFieldName = "BalanceType";
				break;
			case 11:
				strFieldName = "LastBalanceDate";
				break;
			case 12:
				strFieldName = "LastBalanceTime";
				break;
			case 13:
				strFieldName = "NextBalanceDate";
				break;
			case 14:
				strFieldName = "GracePeriod";
				break;
			case 15:
				strFieldName = "ManageCom";
				break;
			case 16:
				strFieldName = "ComCode";
				break;
			case 17:
				strFieldName = "MakeOperator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyOperator";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
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
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceOnDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalanceOnOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceOnState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalancePeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceSumLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalanceSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalanceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastBalanceDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastBalanceTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NextBalanceDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GracePeriod") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
