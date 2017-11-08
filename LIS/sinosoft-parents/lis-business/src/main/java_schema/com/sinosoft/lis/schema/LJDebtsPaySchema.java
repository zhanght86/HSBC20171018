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
import com.sinosoft.lis.db.LJDebtsPayDB;

/*
 * <p>ClassName: LJDebtsPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJDebtsPaySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJDebtsPaySchema.class);
	// @Field
	/** 坏账流水号 */
	private String DebtsNo;
	/** 坏账金额 */
	private double DebtsMoney;
	/** 实收金额 */
	private double ActualMoney;
	/** 坏账类型 */
	private String DebtsType;
	/** 记录状态 */
	private String State;
	/** 保单号 */
	private String GrpContNo;
	/** 业务类型 */
	private String BussType;
	/** 业务号码 */
	private String BussNo;
	/** 应收日期 */
	private Date ShouldDate;
	/** 投保人编码 */
	private String AppntNo;
	/** 关联号 */
	private String RelaNo;
	/** 申请人 */
	private String AppOperator;
	/** 申请日期 */
	private Date AppDate;
	/** 申请时间 */
	private String AppTime;
	/** 申请描述 */
	private String AppDesc;
	/** 审核人 */
	private String ConfirmOperator;
	/** 审核日期 */
	private Date ConfirmDate;
	/** 审核时间 */
	private String ConfirmTime;
	/** 审核意见 */
	private String ConfirmConclusion;
	/** 审核描述 */
	private String ConfirmDesc;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
	/** 管理机构 */
	private String ManageCom;
	/** 机构编码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改操作员 */
	private String ModifyOperator;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJDebtsPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "DebtsNo";

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
		LJDebtsPaySchema cloned = (LJDebtsPaySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDebtsNo()
	{
		return DebtsNo;
	}
	public void setDebtsNo(String aDebtsNo)
	{
		if(aDebtsNo!=null && aDebtsNo.length()>20)
			throw new IllegalArgumentException("坏账流水号DebtsNo值"+aDebtsNo+"的长度"+aDebtsNo.length()+"大于最大值20");
		DebtsNo = aDebtsNo;
	}
	public double getDebtsMoney()
	{
		return DebtsMoney;
	}
	public void setDebtsMoney(double aDebtsMoney)
	{
		DebtsMoney = aDebtsMoney;
	}
	public void setDebtsMoney(String aDebtsMoney)
	{
		if (aDebtsMoney != null && !aDebtsMoney.equals(""))
		{
			Double tDouble = new Double(aDebtsMoney);
			double d = tDouble.doubleValue();
			DebtsMoney = d;
		}
	}

	public double getActualMoney()
	{
		return ActualMoney;
	}
	public void setActualMoney(double aActualMoney)
	{
		ActualMoney = aActualMoney;
	}
	public void setActualMoney(String aActualMoney)
	{
		if (aActualMoney != null && !aActualMoney.equals(""))
		{
			Double tDouble = new Double(aActualMoney);
			double d = tDouble.doubleValue();
			ActualMoney = d;
		}
	}

	/**
	* 00-定结过宽，01-匹配欠费
	*/
	public String getDebtsType()
	{
		return DebtsType;
	}
	public void setDebtsType(String aDebtsType)
	{
		if(aDebtsType!=null && aDebtsType.length()>2)
			throw new IllegalArgumentException("坏账类型DebtsType值"+aDebtsType+"的长度"+aDebtsType.length()+"大于最大值2");
		DebtsType = aDebtsType;
	}
	/**
	* 00-待申请，01-待审核，02-审核通过，03-审核不通过
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("记录状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>2)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值2");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号码BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getShouldDate()
	{
		if( ShouldDate != null )
			return fDate.getString(ShouldDate);
		else
			return null;
	}
	public void setShouldDate(Date aShouldDate)
	{
		ShouldDate = aShouldDate;
	}
	public void setShouldDate(String aShouldDate)
	{
		if (aShouldDate != null && !aShouldDate.equals("") )
		{
			ShouldDate = fDate.getDate( aShouldDate );
		}
		else
			ShouldDate = null;
	}

	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人编码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public String getRelaNo()
	{
		return RelaNo;
	}
	public void setRelaNo(String aRelaNo)
	{
		if(aRelaNo!=null && aRelaNo.length()>20)
			throw new IllegalArgumentException("关联号RelaNo值"+aRelaNo+"的长度"+aRelaNo.length()+"大于最大值20");
		RelaNo = aRelaNo;
	}
	public String getAppOperator()
	{
		return AppOperator;
	}
	public void setAppOperator(String aAppOperator)
	{
		if(aAppOperator!=null && aAppOperator.length()>30)
			throw new IllegalArgumentException("申请人AppOperator值"+aAppOperator+"的长度"+aAppOperator.length()+"大于最大值30");
		AppOperator = aAppOperator;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		if(aAppTime!=null && aAppTime.length()>8)
			throw new IllegalArgumentException("申请时间AppTime值"+aAppTime+"的长度"+aAppTime.length()+"大于最大值8");
		AppTime = aAppTime;
	}
	public String getAppDesc()
	{
		return AppDesc;
	}
	public void setAppDesc(String aAppDesc)
	{
		if(aAppDesc!=null && aAppDesc.length()>300)
			throw new IllegalArgumentException("申请描述AppDesc值"+aAppDesc+"的长度"+aAppDesc.length()+"大于最大值300");
		AppDesc = aAppDesc;
	}
	public String getConfirmOperator()
	{
		return ConfirmOperator;
	}
	public void setConfirmOperator(String aConfirmOperator)
	{
		if(aConfirmOperator!=null && aConfirmOperator.length()>30)
			throw new IllegalArgumentException("审核人ConfirmOperator值"+aConfirmOperator+"的长度"+aConfirmOperator.length()+"大于最大值30");
		ConfirmOperator = aConfirmOperator;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	public String getConfirmTime()
	{
		return ConfirmTime;
	}
	public void setConfirmTime(String aConfirmTime)
	{
		if(aConfirmTime!=null && aConfirmTime.length()>8)
			throw new IllegalArgumentException("审核时间ConfirmTime值"+aConfirmTime+"的长度"+aConfirmTime.length()+"大于最大值8");
		ConfirmTime = aConfirmTime;
	}
	public String getConfirmConclusion()
	{
		return ConfirmConclusion;
	}
	public void setConfirmConclusion(String aConfirmConclusion)
	{
		if(aConfirmConclusion!=null && aConfirmConclusion.length()>2)
			throw new IllegalArgumentException("审核意见ConfirmConclusion值"+aConfirmConclusion+"的长度"+aConfirmConclusion.length()+"大于最大值2");
		ConfirmConclusion = aConfirmConclusion;
	}
	public String getConfirmDesc()
	{
		return ConfirmDesc;
	}
	public void setConfirmDesc(String aConfirmDesc)
	{
		if(aConfirmDesc!=null && aConfirmDesc.length()>300)
			throw new IllegalArgumentException("审核描述ConfirmDesc值"+aConfirmDesc+"的长度"+aConfirmDesc.length()+"大于最大值300");
		ConfirmDesc = aConfirmDesc;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>10)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值10");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>10)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值10");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>10)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值10");
		Segment3 = aSegment3;
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
			throw new IllegalArgumentException("机构编码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
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
			throw new IllegalArgumentException("修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
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
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LJDebtsPaySchema 对象给 Schema 赋值
	* @param: aLJDebtsPaySchema LJDebtsPaySchema
	**/
	public void setSchema(LJDebtsPaySchema aLJDebtsPaySchema)
	{
		this.DebtsNo = aLJDebtsPaySchema.getDebtsNo();
		this.DebtsMoney = aLJDebtsPaySchema.getDebtsMoney();
		this.ActualMoney = aLJDebtsPaySchema.getActualMoney();
		this.DebtsType = aLJDebtsPaySchema.getDebtsType();
		this.State = aLJDebtsPaySchema.getState();
		this.GrpContNo = aLJDebtsPaySchema.getGrpContNo();
		this.BussType = aLJDebtsPaySchema.getBussType();
		this.BussNo = aLJDebtsPaySchema.getBussNo();
		this.ShouldDate = fDate.getDate( aLJDebtsPaySchema.getShouldDate());
		this.AppntNo = aLJDebtsPaySchema.getAppntNo();
		this.RelaNo = aLJDebtsPaySchema.getRelaNo();
		this.AppOperator = aLJDebtsPaySchema.getAppOperator();
		this.AppDate = fDate.getDate( aLJDebtsPaySchema.getAppDate());
		this.AppTime = aLJDebtsPaySchema.getAppTime();
		this.AppDesc = aLJDebtsPaySchema.getAppDesc();
		this.ConfirmOperator = aLJDebtsPaySchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJDebtsPaySchema.getConfirmDate());
		this.ConfirmTime = aLJDebtsPaySchema.getConfirmTime();
		this.ConfirmConclusion = aLJDebtsPaySchema.getConfirmConclusion();
		this.ConfirmDesc = aLJDebtsPaySchema.getConfirmDesc();
		this.Segment1 = aLJDebtsPaySchema.getSegment1();
		this.Segment2 = aLJDebtsPaySchema.getSegment2();
		this.Segment3 = aLJDebtsPaySchema.getSegment3();
		this.ManageCom = aLJDebtsPaySchema.getManageCom();
		this.ComCode = aLJDebtsPaySchema.getComCode();
		this.MakeOperator = aLJDebtsPaySchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJDebtsPaySchema.getMakeDate());
		this.MakeTime = aLJDebtsPaySchema.getMakeTime();
		this.ModifyOperator = aLJDebtsPaySchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJDebtsPaySchema.getModifyDate());
		this.ModifyTime = aLJDebtsPaySchema.getModifyTime();
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
			if( rs.getString("DebtsNo") == null )
				this.DebtsNo = null;
			else
				this.DebtsNo = rs.getString("DebtsNo").trim();

			this.DebtsMoney = rs.getDouble("DebtsMoney");
			this.ActualMoney = rs.getDouble("ActualMoney");
			if( rs.getString("DebtsType") == null )
				this.DebtsType = null;
			else
				this.DebtsType = rs.getString("DebtsType").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			this.ShouldDate = rs.getDate("ShouldDate");
			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("RelaNo") == null )
				this.RelaNo = null;
			else
				this.RelaNo = rs.getString("RelaNo").trim();

			if( rs.getString("AppOperator") == null )
				this.AppOperator = null;
			else
				this.AppOperator = rs.getString("AppOperator").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("AppDesc") == null )
				this.AppDesc = null;
			else
				this.AppDesc = rs.getString("AppDesc").trim();

			if( rs.getString("ConfirmOperator") == null )
				this.ConfirmOperator = null;
			else
				this.ConfirmOperator = rs.getString("ConfirmOperator").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("ConfirmTime") == null )
				this.ConfirmTime = null;
			else
				this.ConfirmTime = rs.getString("ConfirmTime").trim();

			if( rs.getString("ConfirmConclusion") == null )
				this.ConfirmConclusion = null;
			else
				this.ConfirmConclusion = rs.getString("ConfirmConclusion").trim();

			if( rs.getString("ConfirmDesc") == null )
				this.ConfirmDesc = null;
			else
				this.ConfirmDesc = rs.getString("ConfirmDesc").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

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
			logger.debug("数据库中的LJDebtsPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJDebtsPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJDebtsPaySchema getSchema()
	{
		LJDebtsPaySchema aLJDebtsPaySchema = new LJDebtsPaySchema();
		aLJDebtsPaySchema.setSchema(this);
		return aLJDebtsPaySchema;
	}

	public LJDebtsPayDB getDB()
	{
		LJDebtsPayDB aDBOper = new LJDebtsPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJDebtsPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DebtsNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DebtsMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ActualMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DebtsType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ShouldDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJDebtsPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DebtsNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DebtsMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).doubleValue();
			ActualMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			DebtsType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ShouldDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AppOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJDebtsPaySchema";
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
		if (FCode.equalsIgnoreCase("DebtsNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsNo));
		}
		if (FCode.equalsIgnoreCase("DebtsMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsMoney));
		}
		if (FCode.equalsIgnoreCase("ActualMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualMoney));
		}
		if (FCode.equalsIgnoreCase("DebtsType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsType));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("RelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaNo));
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOperator));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("AppDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppDesc));
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmOperator));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmConclusion));
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmDesc));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
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
				strFieldValue = StrTool.GBKToUnicode(DebtsNo);
				break;
			case 1:
				strFieldValue = String.valueOf(DebtsMoney);
				break;
			case 2:
				strFieldValue = String.valueOf(ActualMoney);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DebtsType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RelaNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AppOperator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppDesc);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 30:
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

		if (FCode.equalsIgnoreCase("DebtsNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DebtsNo = FValue.trim();
			}
			else
				DebtsNo = null;
		}
		if (FCode.equalsIgnoreCase("DebtsMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DebtsMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ActualMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ActualMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("DebtsType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DebtsType = FValue.trim();
			}
			else
				DebtsType = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ShouldDate = fDate.getDate( FValue );
			}
			else
				ShouldDate = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("RelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaNo = FValue.trim();
			}
			else
				RelaNo = null;
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppOperator = FValue.trim();
			}
			else
				AppOperator = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("AppDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppDesc = FValue.trim();
			}
			else
				AppDesc = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmOperator = FValue.trim();
			}
			else
				ConfirmOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmTime = FValue.trim();
			}
			else
				ConfirmTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmConclusion = FValue.trim();
			}
			else
				ConfirmConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmDesc = FValue.trim();
			}
			else
				ConfirmDesc = null;
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
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
		LJDebtsPaySchema other = (LJDebtsPaySchema)otherObject;
		return
			DebtsNo.equals(other.getDebtsNo())
			&& DebtsMoney == other.getDebtsMoney()
			&& ActualMoney == other.getActualMoney()
			&& DebtsType.equals(other.getDebtsType())
			&& State.equals(other.getState())
			&& GrpContNo.equals(other.getGrpContNo())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& fDate.getString(ShouldDate).equals(other.getShouldDate())
			&& AppntNo.equals(other.getAppntNo())
			&& RelaNo.equals(other.getRelaNo())
			&& AppOperator.equals(other.getAppOperator())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& AppDesc.equals(other.getAppDesc())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmDesc.equals(other.getConfirmDesc())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
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
		if( strFieldName.equals("DebtsNo") ) {
			return 0;
		}
		if( strFieldName.equals("DebtsMoney") ) {
			return 1;
		}
		if( strFieldName.equals("ActualMoney") ) {
			return 2;
		}
		if( strFieldName.equals("DebtsType") ) {
			return 3;
		}
		if( strFieldName.equals("State") ) {
			return 4;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 5;
		}
		if( strFieldName.equals("BussType") ) {
			return 6;
		}
		if( strFieldName.equals("BussNo") ) {
			return 7;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return 8;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 9;
		}
		if( strFieldName.equals("RelaNo") ) {
			return 10;
		}
		if( strFieldName.equals("AppOperator") ) {
			return 11;
		}
		if( strFieldName.equals("AppDate") ) {
			return 12;
		}
		if( strFieldName.equals("AppTime") ) {
			return 13;
		}
		if( strFieldName.equals("AppDesc") ) {
			return 14;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 15;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 16;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 17;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 18;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 19;
		}
		if( strFieldName.equals("Segment1") ) {
			return 20;
		}
		if( strFieldName.equals("Segment2") ) {
			return 21;
		}
		if( strFieldName.equals("Segment3") ) {
			return 22;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 23;
		}
		if( strFieldName.equals("ComCode") ) {
			return 24;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 30;
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
				strFieldName = "DebtsNo";
				break;
			case 1:
				strFieldName = "DebtsMoney";
				break;
			case 2:
				strFieldName = "ActualMoney";
				break;
			case 3:
				strFieldName = "DebtsType";
				break;
			case 4:
				strFieldName = "State";
				break;
			case 5:
				strFieldName = "GrpContNo";
				break;
			case 6:
				strFieldName = "BussType";
				break;
			case 7:
				strFieldName = "BussNo";
				break;
			case 8:
				strFieldName = "ShouldDate";
				break;
			case 9:
				strFieldName = "AppntNo";
				break;
			case 10:
				strFieldName = "RelaNo";
				break;
			case 11:
				strFieldName = "AppOperator";
				break;
			case 12:
				strFieldName = "AppDate";
				break;
			case 13:
				strFieldName = "AppTime";
				break;
			case 14:
				strFieldName = "AppDesc";
				break;
			case 15:
				strFieldName = "ConfirmOperator";
				break;
			case 16:
				strFieldName = "ConfirmDate";
				break;
			case 17:
				strFieldName = "ConfirmTime";
				break;
			case 18:
				strFieldName = "ConfirmConclusion";
				break;
			case 19:
				strFieldName = "ConfirmDesc";
				break;
			case 20:
				strFieldName = "Segment1";
				break;
			case 21:
				strFieldName = "Segment2";
				break;
			case 22:
				strFieldName = "Segment3";
				break;
			case 23:
				strFieldName = "ManageCom";
				break;
			case 24:
				strFieldName = "ComCode";
				break;
			case 25:
				strFieldName = "MakeOperator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "ModifyOperator";
				break;
			case 29:
				strFieldName = "ModifyDate";
				break;
			case 30:
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
		if( strFieldName.equals("DebtsNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DebtsMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ActualMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DebtsType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 2:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
