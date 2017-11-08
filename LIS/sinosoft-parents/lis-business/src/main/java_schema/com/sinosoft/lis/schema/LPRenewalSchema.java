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
import com.sinosoft.lis.db.LPRenewalDB;

/*
 * <p>ClassName: LPRenewalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPRenewalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPRenewalSchema.class);
	// @Field
	/** 批次号 */
	private String RenewalNo;
	/** 抽档类型 */
	private String RenewalType;
	/** 团体保单号 */
	private String GrpContNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 其它号码 */
	private String OtherNo;
	/** 第几次交费 */
	private int PayCount;
	/** 通知书号码 */
	private String GetNoticeNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 总应收金额 */
	private double SumDuePayMoney;
	/** 总实交金额 */
	private double SumActuPayMoney;
	/** 交费模式 */
	private String PayMode;
	/** 交费对象 */
	private String PayObj;
	/** 交费日期 */
	private Date PayDate;
	/** 宽限截止日期 */
	private Date PayStopDate;
	/** 交费间隔 */
	private int PayIntv;
	/** 状态 */
	private String State;
	/** 打印状态 */
	private String PrintState;
	/** 打印次数 */
	private int PrintCount;
	/** 打印人员 */
	private String PrintOperator;
	/** 抽档日期 */
	private Date OperDate;
	/** 核销日期 */
	private Date ConfDate;
	/** 核销时间 */
	private String ConfTime;
	/** 管理机构 */
	private String ManageCom;
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

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPRenewalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RenewalNo";

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
		LPRenewalSchema cloned = (LPRenewalSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRenewalNo()
	{
		return RenewalNo;
	}
	public void setRenewalNo(String aRenewalNo)
	{
		if(aRenewalNo!=null && aRenewalNo.length()>20)
			throw new IllegalArgumentException("批次号RenewalNo值"+aRenewalNo+"的长度"+aRenewalNo.length()+"大于最大值20");
		RenewalNo = aRenewalNo;
	}
	public String getRenewalType()
	{
		return RenewalType;
	}
	public void setRenewalType(String aRenewalType)
	{
		if(aRenewalType!=null && aRenewalType.length()>20)
			throw new IllegalArgumentException("抽档类型RenewalType值"+aRenewalType+"的长度"+aRenewalType.length()+"大于最大值20");
		RenewalType = aRenewalType;
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
	/**
	* 01-新契约，02-续期，03-保全，04-定期结算，05-理赔，06-溢交退费，07-暂交费退费
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>2)
			throw new IllegalArgumentException("其它号码类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值2");
		OtherNoType = aOtherNoType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其它号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public int getPayCount()
	{
		return PayCount;
	}
	public void setPayCount(int aPayCount)
	{
		PayCount = aPayCount;
	}
	public void setPayCount(String aPayCount)
	{
		if (aPayCount != null && !aPayCount.equals(""))
		{
			Integer tInteger = new Integer(aPayCount);
			int i = tInteger.intValue();
			PayCount = i;
		}
	}

	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		if(aGetNoticeNo!=null && aGetNoticeNo.length()>20)
			throw new IllegalArgumentException("通知书号码GetNoticeNo值"+aGetNoticeNo+"的长度"+aGetNoticeNo.length()+"大于最大值20");
		GetNoticeNo = aGetNoticeNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人客户号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public double getSumDuePayMoney()
	{
		return SumDuePayMoney;
	}
	public void setSumDuePayMoney(double aSumDuePayMoney)
	{
		SumDuePayMoney = aSumDuePayMoney;
	}
	public void setSumDuePayMoney(String aSumDuePayMoney)
	{
		if (aSumDuePayMoney != null && !aSumDuePayMoney.equals(""))
		{
			Double tDouble = new Double(aSumDuePayMoney);
			double d = tDouble.doubleValue();
			SumDuePayMoney = d;
		}
	}

	public double getSumActuPayMoney()
	{
		return SumActuPayMoney;
	}
	public void setSumActuPayMoney(double aSumActuPayMoney)
	{
		SumActuPayMoney = aSumActuPayMoney;
	}
	public void setSumActuPayMoney(String aSumActuPayMoney)
	{
		if (aSumActuPayMoney != null && !aSumActuPayMoney.equals(""))
		{
			Double tDouble = new Double(aSumActuPayMoney);
			double d = tDouble.doubleValue();
			SumActuPayMoney = d;
		}
	}

	/**
	* 00-匹配关联，01-代扣
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		if(aPayMode!=null && aPayMode.length()>2)
			throw new IllegalArgumentException("交费模式PayMode值"+aPayMode+"的长度"+aPayMode.length()+"大于最大值2");
		PayMode = aPayMode;
	}
	/**
	* 00-对公，01-对私
	*/
	public String getPayObj()
	{
		return PayObj;
	}
	public void setPayObj(String aPayObj)
	{
		if(aPayObj!=null && aPayObj.length()>2)
			throw new IllegalArgumentException("交费对象PayObj值"+aPayObj+"的长度"+aPayObj.length()+"大于最大值2");
		PayObj = aPayObj;
	}
	public String getPayDate()
	{
		if( PayDate != null )
			return fDate.getString(PayDate);
		else
			return null;
	}
	public void setPayDate(Date aPayDate)
	{
		PayDate = aPayDate;
	}
	public void setPayDate(String aPayDate)
	{
		if (aPayDate != null && !aPayDate.equals("") )
		{
			PayDate = fDate.getDate( aPayDate );
		}
		else
			PayDate = null;
	}

	public String getPayStopDate()
	{
		if( PayStopDate != null )
			return fDate.getString(PayStopDate);
		else
			return null;
	}
	public void setPayStopDate(Date aPayStopDate)
	{
		PayStopDate = aPayStopDate;
	}
	public void setPayStopDate(String aPayStopDate)
	{
		if (aPayStopDate != null && !aPayStopDate.equals("") )
		{
			PayStopDate = fDate.getDate( aPayStopDate );
		}
		else
			PayStopDate = null;
	}

	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* 00-核销<p>
	* 01-抽档<p>
	* 02-抽档回退
	*/
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
	public int getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(int aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		if (aPrintCount != null && !aPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aPrintCount);
			int i = tInteger.intValue();
			PrintCount = i;
		}
	}

	public String getPrintOperator()
	{
		return PrintOperator;
	}
	public void setPrintOperator(String aPrintOperator)
	{
		if(aPrintOperator!=null && aPrintOperator.length()>30)
			throw new IllegalArgumentException("打印人员PrintOperator值"+aPrintOperator+"的长度"+aPrintOperator.length()+"大于最大值30");
		PrintOperator = aPrintOperator;
	}
	public String getOperDate()
	{
		if( OperDate != null )
			return fDate.getString(OperDate);
		else
			return null;
	}
	public void setOperDate(Date aOperDate)
	{
		OperDate = aOperDate;
	}
	public void setOperDate(String aOperDate)
	{
		if (aOperDate != null && !aOperDate.equals("") )
		{
			OperDate = fDate.getDate( aOperDate );
		}
		else
			OperDate = null;
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
			throw new IllegalArgumentException("核销时间ConfTime值"+aConfTime+"的长度"+aConfTime.length()+"大于最大值8");
		ConfTime = aConfTime;
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
	* 使用另外一个 LPRenewalSchema 对象给 Schema 赋值
	* @param: aLPRenewalSchema LPRenewalSchema
	**/
	public void setSchema(LPRenewalSchema aLPRenewalSchema)
	{
		this.RenewalNo = aLPRenewalSchema.getRenewalNo();
		this.RenewalType = aLPRenewalSchema.getRenewalType();
		this.GrpContNo = aLPRenewalSchema.getGrpContNo();
		this.OtherNoType = aLPRenewalSchema.getOtherNoType();
		this.OtherNo = aLPRenewalSchema.getOtherNo();
		this.PayCount = aLPRenewalSchema.getPayCount();
		this.GetNoticeNo = aLPRenewalSchema.getGetNoticeNo();
		this.AppntNo = aLPRenewalSchema.getAppntNo();
		this.SumDuePayMoney = aLPRenewalSchema.getSumDuePayMoney();
		this.SumActuPayMoney = aLPRenewalSchema.getSumActuPayMoney();
		this.PayMode = aLPRenewalSchema.getPayMode();
		this.PayObj = aLPRenewalSchema.getPayObj();
		this.PayDate = fDate.getDate( aLPRenewalSchema.getPayDate());
		this.PayStopDate = fDate.getDate( aLPRenewalSchema.getPayStopDate());
		this.PayIntv = aLPRenewalSchema.getPayIntv();
		this.State = aLPRenewalSchema.getState();
		this.PrintState = aLPRenewalSchema.getPrintState();
		this.PrintCount = aLPRenewalSchema.getPrintCount();
		this.PrintOperator = aLPRenewalSchema.getPrintOperator();
		this.OperDate = fDate.getDate( aLPRenewalSchema.getOperDate());
		this.ConfDate = fDate.getDate( aLPRenewalSchema.getConfDate());
		this.ConfTime = aLPRenewalSchema.getConfTime();
		this.ManageCom = aLPRenewalSchema.getManageCom();
		this.MakeOperator = aLPRenewalSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPRenewalSchema.getMakeDate());
		this.MakeTime = aLPRenewalSchema.getMakeTime();
		this.ModifyOperator = aLPRenewalSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPRenewalSchema.getModifyDate());
		this.ModifyTime = aLPRenewalSchema.getModifyTime();
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
			if( rs.getString("RenewalNo") == null )
				this.RenewalNo = null;
			else
				this.RenewalNo = rs.getString("RenewalNo").trim();

			if( rs.getString("RenewalType") == null )
				this.RenewalType = null;
			else
				this.RenewalType = rs.getString("RenewalType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			this.PayCount = rs.getInt("PayCount");
			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			this.SumDuePayMoney = rs.getDouble("SumDuePayMoney");
			this.SumActuPayMoney = rs.getDouble("SumActuPayMoney");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayObj") == null )
				this.PayObj = null;
			else
				this.PayObj = rs.getString("PayObj").trim();

			this.PayDate = rs.getDate("PayDate");
			this.PayStopDate = rs.getDate("PayStopDate");
			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("PrintState") == null )
				this.PrintState = null;
			else
				this.PrintState = rs.getString("PrintState").trim();

			this.PrintCount = rs.getInt("PrintCount");
			if( rs.getString("PrintOperator") == null )
				this.PrintOperator = null;
			else
				this.PrintOperator = rs.getString("PrintOperator").trim();

			this.OperDate = rs.getDate("OperDate");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ConfTime") == null )
				this.ConfTime = null;
			else
				this.ConfTime = rs.getString("ConfTime").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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
			logger.debug("数据库中的LPRenewal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPRenewalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPRenewalSchema getSchema()
	{
		LPRenewalSchema aLPRenewalSchema = new LPRenewalSchema();
		aLPRenewalSchema.setSchema(this);
		return aLPRenewalSchema;
	}

	public LPRenewalDB getDB()
	{
		LPRenewalDB aDBOper = new LPRenewalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPRenewal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RenewalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumDuePayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumActuPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayStopDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OperDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPRenewal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RenewalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RenewalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SumDuePayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			SumActuPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PayObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			PayStopDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PrintState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			PrintOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			OperDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ConfTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPRenewalSchema";
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
		if (FCode.equalsIgnoreCase("RenewalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewalNo));
		}
		if (FCode.equalsIgnoreCase("RenewalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewalType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("SumDuePayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumDuePayMoney));
		}
		if (FCode.equalsIgnoreCase("SumActuPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumActuPayMoney));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PayObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayObj));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("PayStopDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayStopDate()));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintState));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("PrintOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintOperator));
		}
		if (FCode.equalsIgnoreCase("OperDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOperDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfTime));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(RenewalNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RenewalType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 5:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 8:
				strFieldValue = String.valueOf(SumDuePayMoney);
				break;
			case 9:
				strFieldValue = String.valueOf(SumActuPayMoney);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PayObj);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayStopDate()));
				break;
			case 14:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PrintState);
				break;
			case 17:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PrintOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOperDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ConfTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 28:
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

		if (FCode.equalsIgnoreCase("RenewalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewalNo = FValue.trim();
			}
			else
				RenewalNo = null;
		}
		if (FCode.equalsIgnoreCase("RenewalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewalType = FValue.trim();
			}
			else
				RenewalType = null;
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
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
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
		if (FCode.equalsIgnoreCase("SumDuePayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumDuePayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumActuPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumActuPayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equalsIgnoreCase("PayObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayObj = FValue.trim();
			}
			else
				PayObj = null;
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayDate = fDate.getDate( FValue );
			}
			else
				PayDate = null;
		}
		if (FCode.equalsIgnoreCase("PayStopDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayStopDate = fDate.getDate( FValue );
			}
			else
				PayStopDate = null;
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PrintOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintOperator = FValue.trim();
			}
			else
				PrintOperator = null;
		}
		if (FCode.equalsIgnoreCase("OperDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OperDate = fDate.getDate( FValue );
			}
			else
				OperDate = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		LPRenewalSchema other = (LPRenewalSchema)otherObject;
		return
			RenewalNo.equals(other.getRenewalNo())
			&& RenewalType.equals(other.getRenewalType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& OtherNo.equals(other.getOtherNo())
			&& PayCount == other.getPayCount()
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& AppntNo.equals(other.getAppntNo())
			&& SumDuePayMoney == other.getSumDuePayMoney()
			&& SumActuPayMoney == other.getSumActuPayMoney()
			&& PayMode.equals(other.getPayMode())
			&& PayObj.equals(other.getPayObj())
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& fDate.getString(PayStopDate).equals(other.getPayStopDate())
			&& PayIntv == other.getPayIntv()
			&& State.equals(other.getState())
			&& PrintState.equals(other.getPrintState())
			&& PrintCount == other.getPrintCount()
			&& PrintOperator.equals(other.getPrintOperator())
			&& fDate.getString(OperDate).equals(other.getOperDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ConfTime.equals(other.getConfTime())
			&& ManageCom.equals(other.getManageCom())
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
		if( strFieldName.equals("RenewalNo") ) {
			return 0;
		}
		if( strFieldName.equals("RenewalType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 4;
		}
		if( strFieldName.equals("PayCount") ) {
			return 5;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 6;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 7;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return 8;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return 9;
		}
		if( strFieldName.equals("PayMode") ) {
			return 10;
		}
		if( strFieldName.equals("PayObj") ) {
			return 11;
		}
		if( strFieldName.equals("PayDate") ) {
			return 12;
		}
		if( strFieldName.equals("PayStopDate") ) {
			return 13;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 14;
		}
		if( strFieldName.equals("State") ) {
			return 15;
		}
		if( strFieldName.equals("PrintState") ) {
			return 16;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 17;
		}
		if( strFieldName.equals("PrintOperator") ) {
			return 18;
		}
		if( strFieldName.equals("OperDate") ) {
			return 19;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 20;
		}
		if( strFieldName.equals("ConfTime") ) {
			return 21;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 22;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 28;
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
				strFieldName = "RenewalNo";
				break;
			case 1:
				strFieldName = "RenewalType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "OtherNoType";
				break;
			case 4:
				strFieldName = "OtherNo";
				break;
			case 5:
				strFieldName = "PayCount";
				break;
			case 6:
				strFieldName = "GetNoticeNo";
				break;
			case 7:
				strFieldName = "AppntNo";
				break;
			case 8:
				strFieldName = "SumDuePayMoney";
				break;
			case 9:
				strFieldName = "SumActuPayMoney";
				break;
			case 10:
				strFieldName = "PayMode";
				break;
			case 11:
				strFieldName = "PayObj";
				break;
			case 12:
				strFieldName = "PayDate";
				break;
			case 13:
				strFieldName = "PayStopDate";
				break;
			case 14:
				strFieldName = "PayIntv";
				break;
			case 15:
				strFieldName = "State";
				break;
			case 16:
				strFieldName = "PrintState";
				break;
			case 17:
				strFieldName = "PrintCount";
				break;
			case 18:
				strFieldName = "PrintOperator";
				break;
			case 19:
				strFieldName = "OperDate";
				break;
			case 20:
				strFieldName = "ConfDate";
				break;
			case 21:
				strFieldName = "ConfTime";
				break;
			case 22:
				strFieldName = "ManageCom";
				break;
			case 23:
				strFieldName = "MakeOperator";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyOperator";
				break;
			case 27:
				strFieldName = "ModifyDate";
				break;
			case 28:
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
		if( strFieldName.equals("RenewalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RenewalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayStopDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PrintOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
