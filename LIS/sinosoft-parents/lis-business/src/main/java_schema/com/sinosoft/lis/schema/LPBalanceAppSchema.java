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
import com.sinosoft.lis.db.LPBalanceAppDB;

/*
 * <p>ClassName: LPBalanceAppSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPBalanceAppSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPBalanceAppSchema.class);
	// @Field
	/** 定期结算申请号 */
	private String BalanceAppNo;
	/** 定期结算汇总号码 */
	private String BalanceAllNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 申请日期 */
	private Date AppDate;
	/** 补退费金额 */
	private double GetMoney;
	/** 补退费利息 */
	private double GetInterest;
	/** 本次定期结算方式 */
	private String BalanceType;
	/** 定期结算关联收付类型 */
	private String BalanceRelaType;
	/** 状态 */
	private String State;
	/** 审批日期 */
	private Date ApproveDate;
	/** 审批时间 */
	private String ApproveTime;
	/** 审批结论 */
	private String ApproveFlag;
	/** 审批描述 */
	private String ApproveDesc;
	/** 业务确认日期 */
	private Date ConfDate;
	/** 业务确认时间 */
	private String ConfTime;
	/** 财务确认日期 */
	private Date FinaConfDate;
	/** 财务确认时间 */
	private String FinaConfTime;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
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
	/** 打印状态 */
	private String PrintState;
	/** 打印次数 */
	private String PrintCount;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPBalanceAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BalanceAppNo";

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
		LPBalanceAppSchema cloned = (LPBalanceAppSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBalanceAppNo()
	{
		return BalanceAppNo;
	}
	public void setBalanceAppNo(String aBalanceAppNo)
	{
		if(aBalanceAppNo!=null && aBalanceAppNo.length()>20)
			throw new IllegalArgumentException("定期结算申请号BalanceAppNo值"+aBalanceAppNo+"的长度"+aBalanceAppNo.length()+"大于最大值20");
		BalanceAppNo = aBalanceAppNo;
	}
	public String getBalanceAllNo()
	{
		return BalanceAllNo;
	}
	public void setBalanceAllNo(String aBalanceAllNo)
	{
		if(aBalanceAllNo!=null && aBalanceAllNo.length()>20)
			throw new IllegalArgumentException("定期结算汇总号码BalanceAllNo值"+aBalanceAllNo+"的长度"+aBalanceAllNo.length()+"大于最大值20");
		BalanceAllNo = aBalanceAllNo;
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

	public double getGetInterest()
	{
		return GetInterest;
	}
	public void setGetInterest(double aGetInterest)
	{
		GetInterest = aGetInterest;
	}
	public void setGetInterest(String aGetInterest)
	{
		if (aGetInterest != null && !aGetInterest.equals(""))
		{
			Double tDouble = new Double(aGetInterest);
			double d = tDouble.doubleValue();
			GetInterest = d;
		}
	}

	/**
	* 定期结算方式：<p>
	* -1 －未定期结算(在本表存储中无该方式)<p>
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
			throw new IllegalArgumentException("本次定期结算方式BalanceType值"+aBalanceType+"的长度"+aBalanceType.length()+"大于最大值2");
		BalanceType = aBalanceType;
	}
	/**
	* 0 －收付抵消<p>
	* 1 －收费<p>
	* 2 －付费
	*/
	public String getBalanceRelaType()
	{
		return BalanceRelaType;
	}
	public void setBalanceRelaType(String aBalanceRelaType)
	{
		if(aBalanceRelaType!=null && aBalanceRelaType.length()>2)
			throw new IllegalArgumentException("定期结算关联收付类型BalanceRelaType值"+aBalanceRelaType+"的长度"+aBalanceRelaType.length()+"大于最大值2");
		BalanceRelaType = aBalanceRelaType;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		if(aApproveTime!=null && aApproveTime.length()>8)
			throw new IllegalArgumentException("审批时间ApproveTime值"+aApproveTime+"的长度"+aApproveTime.length()+"大于最大值8");
		ApproveTime = aApproveTime;
	}
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		if(aApproveFlag!=null && aApproveFlag.length()>8)
			throw new IllegalArgumentException("审批结论ApproveFlag值"+aApproveFlag+"的长度"+aApproveFlag.length()+"大于最大值8");
		ApproveFlag = aApproveFlag;
	}
	public String getApproveDesc()
	{
		return ApproveDesc;
	}
	public void setApproveDesc(String aApproveDesc)
	{
		if(aApproveDesc!=null && aApproveDesc.length()>2000)
			throw new IllegalArgumentException("审批描述ApproveDesc值"+aApproveDesc+"的长度"+aApproveDesc.length()+"大于最大值2000");
		ApproveDesc = aApproveDesc;
	}
	public String getConfDate()
	{
		if( ConfDate != null )
			return fDate.getString(ConfDate);
		else
			return null;
	}
	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}
	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
	}

	public String getConfTime()
	{
		return ConfTime;
	}
	public void setConfTime(String aConfTime)
	{
		if(aConfTime!=null && aConfTime.length()>8)
			throw new IllegalArgumentException("业务确认时间ConfTime值"+aConfTime+"的长度"+aConfTime.length()+"大于最大值8");
		ConfTime = aConfTime;
	}
	public String getFinaConfDate()
	{
		if( FinaConfDate != null )
			return fDate.getString(FinaConfDate);
		else
			return null;
	}
	public void setFinaConfDate(Date aFinaConfDate)
	{
		FinaConfDate = aFinaConfDate;
	}
	public void setFinaConfDate(String aFinaConfDate)
	{
		if (aFinaConfDate != null && !aFinaConfDate.equals("") )
		{
			FinaConfDate = fDate.getDate( aFinaConfDate );
		}
		else
			FinaConfDate = null;
	}

	public String getFinaConfTime()
	{
		return FinaConfTime;
	}
	public void setFinaConfTime(String aFinaConfTime)
	{
		if(aFinaConfTime!=null && aFinaConfTime.length()>8)
			throw new IllegalArgumentException("财务确认时间FinaConfTime值"+aFinaConfTime+"的长度"+aFinaConfTime.length()+"大于最大值8");
		FinaConfTime = aFinaConfTime;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>200)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值200");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>1000)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值1000");
		Segment3 = aSegment3;
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
	public String getPrintState()
	{
		return PrintState;
	}
	public void setPrintState(String aPrintState)
	{
		if(aPrintState!=null && aPrintState.length()>2)
			throw new IllegalArgumentException("打印状态PrintState值"+aPrintState+"的长度"+aPrintState.length()+"大于最大值2");
		PrintState = aPrintState;
	}
	public String getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		if(aPrintCount!=null && aPrintCount.length()>8)
			throw new IllegalArgumentException("打印次数PrintCount值"+aPrintCount+"的长度"+aPrintCount.length()+"大于最大值8");
		PrintCount = aPrintCount;
	}

	/**
	* 使用另外一个 LPBalanceAppSchema 对象给 Schema 赋值
	* @param: aLPBalanceAppSchema LPBalanceAppSchema
	**/
	public void setSchema(LPBalanceAppSchema aLPBalanceAppSchema)
	{
		this.BalanceAppNo = aLPBalanceAppSchema.getBalanceAppNo();
		this.BalanceAllNo = aLPBalanceAppSchema.getBalanceAllNo();
		this.GrpContNo = aLPBalanceAppSchema.getGrpContNo();
		this.AppDate = fDate.getDate( aLPBalanceAppSchema.getAppDate());
		this.GetMoney = aLPBalanceAppSchema.getGetMoney();
		this.GetInterest = aLPBalanceAppSchema.getGetInterest();
		this.BalanceType = aLPBalanceAppSchema.getBalanceType();
		this.BalanceRelaType = aLPBalanceAppSchema.getBalanceRelaType();
		this.State = aLPBalanceAppSchema.getState();
		this.ApproveDate = fDate.getDate( aLPBalanceAppSchema.getApproveDate());
		this.ApproveTime = aLPBalanceAppSchema.getApproveTime();
		this.ApproveFlag = aLPBalanceAppSchema.getApproveFlag();
		this.ApproveDesc = aLPBalanceAppSchema.getApproveDesc();
		this.ConfDate = fDate.getDate( aLPBalanceAppSchema.getConfDate());
		this.ConfTime = aLPBalanceAppSchema.getConfTime();
		this.FinaConfDate = fDate.getDate( aLPBalanceAppSchema.getFinaConfDate());
		this.FinaConfTime = aLPBalanceAppSchema.getFinaConfTime();
		this.Segment1 = aLPBalanceAppSchema.getSegment1();
		this.Segment2 = aLPBalanceAppSchema.getSegment2();
		this.Segment3 = aLPBalanceAppSchema.getSegment3();
		this.ManageCom = aLPBalanceAppSchema.getManageCom();
		this.ComCode = aLPBalanceAppSchema.getComCode();
		this.MakeOperator = aLPBalanceAppSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPBalanceAppSchema.getMakeDate());
		this.MakeTime = aLPBalanceAppSchema.getMakeTime();
		this.ModifyOperator = aLPBalanceAppSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPBalanceAppSchema.getModifyDate());
		this.ModifyTime = aLPBalanceAppSchema.getModifyTime();
		this.PrintState = aLPBalanceAppSchema.getPrintState();
		this.PrintCount = aLPBalanceAppSchema.getPrintCount();
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
			if( rs.getString("BalanceAppNo") == null )
				this.BalanceAppNo = null;
			else
				this.BalanceAppNo = rs.getString("BalanceAppNo").trim();

			if( rs.getString("BalanceAllNo") == null )
				this.BalanceAllNo = null;
			else
				this.BalanceAllNo = rs.getString("BalanceAllNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			this.AppDate = rs.getDate("AppDate");
			this.GetMoney = rs.getDouble("GetMoney");
			this.GetInterest = rs.getDouble("GetInterest");
			if( rs.getString("BalanceType") == null )
				this.BalanceType = null;
			else
				this.BalanceType = rs.getString("BalanceType").trim();

			if( rs.getString("BalanceRelaType") == null )
				this.BalanceRelaType = null;
			else
				this.BalanceRelaType = rs.getString("BalanceRelaType").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveDesc") == null )
				this.ApproveDesc = null;
			else
				this.ApproveDesc = rs.getString("ApproveDesc").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ConfTime") == null )
				this.ConfTime = null;
			else
				this.ConfTime = rs.getString("ConfTime").trim();

			this.FinaConfDate = rs.getDate("FinaConfDate");
			if( rs.getString("FinaConfTime") == null )
				this.FinaConfTime = null;
			else
				this.FinaConfTime = rs.getString("FinaConfTime").trim();

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

			if( rs.getString("PrintState") == null )
				this.PrintState = null;
			else
				this.PrintState = rs.getString("PrintState").trim();

			if( rs.getString("PrintCount") == null )
				this.PrintCount = null;
			else
				this.PrintCount = rs.getString("PrintCount").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPBalanceApp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPBalanceAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPBalanceAppSchema getSchema()
	{
		LPBalanceAppSchema aLPBalanceAppSchema = new LPBalanceAppSchema();
		aLPBalanceAppSchema.setSchema(this);
		return aLPBalanceAppSchema;
	}

	public LPBalanceAppDB getDB()
	{
		LPBalanceAppDB aDBOper = new LPBalanceAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPBalanceApp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BalanceAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FinaConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinaConfTime)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintCount));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPBalanceApp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BalanceAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BalanceAllNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			GetInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			BalanceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BalanceRelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ApproveDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ConfTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			FinaConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			FinaConfTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PrintState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			PrintCount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPBalanceAppSchema";
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
		if (FCode.equalsIgnoreCase("BalanceAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAppNo));
		}
		if (FCode.equalsIgnoreCase("BalanceAllNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetInterest));
		}
		if (FCode.equalsIgnoreCase("BalanceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceType));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaType));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveDesc));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfTime));
		}
		if (FCode.equalsIgnoreCase("FinaConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFinaConfDate()));
		}
		if (FCode.equalsIgnoreCase("FinaConfTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinaConfTime));
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
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintState));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
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
				strFieldValue = StrTool.GBKToUnicode(BalanceAppNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 4:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 5:
				strFieldValue = String.valueOf(GetInterest);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BalanceType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ApproveDesc);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ConfTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFinaConfDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(FinaConfTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PrintState);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(PrintCount);
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

		if (FCode.equalsIgnoreCase("BalanceAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAppNo = FValue.trim();
			}
			else
				BalanceAppNo = null;
		}
		if (FCode.equalsIgnoreCase("BalanceAllNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAllNo = FValue.trim();
			}
			else
				BalanceAllNo = null;
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
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
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
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetInterest = d;
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
		if (FCode.equalsIgnoreCase("BalanceRelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaType = FValue.trim();
			}
			else
				BalanceRelaType = null;
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
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveDesc = FValue.trim();
			}
			else
				ApproveDesc = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfTime = FValue.trim();
			}
			else
				ConfTime = null;
		}
		if (FCode.equalsIgnoreCase("FinaConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FinaConfDate = fDate.getDate( FValue );
			}
			else
				FinaConfDate = null;
		}
		if (FCode.equalsIgnoreCase("FinaConfTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinaConfTime = FValue.trim();
			}
			else
				FinaConfTime = null;
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
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintState = FValue.trim();
			}
			else
				PrintState = null;
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintCount = FValue.trim();
			}
			else
				PrintCount = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPBalanceAppSchema other = (LPBalanceAppSchema)otherObject;
		return
			BalanceAppNo.equals(other.getBalanceAppNo())
			&& BalanceAllNo.equals(other.getBalanceAllNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& GetMoney == other.getGetMoney()
			&& GetInterest == other.getGetInterest()
			&& BalanceType.equals(other.getBalanceType())
			&& BalanceRelaType.equals(other.getBalanceRelaType())
			&& State.equals(other.getState())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveDesc.equals(other.getApproveDesc())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ConfTime.equals(other.getConfTime())
			&& fDate.getString(FinaConfDate).equals(other.getFinaConfDate())
			&& FinaConfTime.equals(other.getFinaConfTime())
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
			&& ModifyTime.equals(other.getModifyTime())
			&& PrintState.equals(other.getPrintState())
			&& PrintCount.equals(other.getPrintCount());
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
		if( strFieldName.equals("BalanceAppNo") ) {
			return 0;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("AppDate") ) {
			return 3;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 4;
		}
		if( strFieldName.equals("GetInterest") ) {
			return 5;
		}
		if( strFieldName.equals("BalanceType") ) {
			return 6;
		}
		if( strFieldName.equals("BalanceRelaType") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
			return 8;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 9;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 10;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 11;
		}
		if( strFieldName.equals("ApproveDesc") ) {
			return 12;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 13;
		}
		if( strFieldName.equals("ConfTime") ) {
			return 14;
		}
		if( strFieldName.equals("FinaConfDate") ) {
			return 15;
		}
		if( strFieldName.equals("FinaConfTime") ) {
			return 16;
		}
		if( strFieldName.equals("Segment1") ) {
			return 17;
		}
		if( strFieldName.equals("Segment2") ) {
			return 18;
		}
		if( strFieldName.equals("Segment3") ) {
			return 19;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 20;
		}
		if( strFieldName.equals("ComCode") ) {
			return 21;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
		}
		if( strFieldName.equals("PrintState") ) {
			return 28;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 29;
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
				strFieldName = "BalanceAppNo";
				break;
			case 1:
				strFieldName = "BalanceAllNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "AppDate";
				break;
			case 4:
				strFieldName = "GetMoney";
				break;
			case 5:
				strFieldName = "GetInterest";
				break;
			case 6:
				strFieldName = "BalanceType";
				break;
			case 7:
				strFieldName = "BalanceRelaType";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "ApproveDate";
				break;
			case 10:
				strFieldName = "ApproveTime";
				break;
			case 11:
				strFieldName = "ApproveFlag";
				break;
			case 12:
				strFieldName = "ApproveDesc";
				break;
			case 13:
				strFieldName = "ConfDate";
				break;
			case 14:
				strFieldName = "ConfTime";
				break;
			case 15:
				strFieldName = "FinaConfDate";
				break;
			case 16:
				strFieldName = "FinaConfTime";
				break;
			case 17:
				strFieldName = "Segment1";
				break;
			case 18:
				strFieldName = "Segment2";
				break;
			case 19:
				strFieldName = "Segment3";
				break;
			case 20:
				strFieldName = "ManageCom";
				break;
			case 21:
				strFieldName = "ComCode";
				break;
			case 22:
				strFieldName = "MakeOperator";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "ModifyOperator";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
				strFieldName = "ModifyTime";
				break;
			case 28:
				strFieldName = "PrintState";
				break;
			case 29:
				strFieldName = "PrintCount";
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
		if( strFieldName.equals("BalanceAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalanceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceRelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinaConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FinaConfTime") ) {
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
		if( strFieldName.equals("PrintState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
