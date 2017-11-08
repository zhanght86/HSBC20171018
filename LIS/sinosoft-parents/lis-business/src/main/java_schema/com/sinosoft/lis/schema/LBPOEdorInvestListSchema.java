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
import com.sinosoft.lis.db.LBPOEdorInvestListDB;

/*
 * <p>ClassName: LBPOEdorInvestListSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LBPOEdorInvestListSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOEdorInvestListSchema.class);
	// @Field
	/** 流水号 */
	private int SerialNo;
	/** 保全受理号 */
	private String EdorAppNo;
	/** 操作节点 */
	private String ActivityID;
	/** 批次号 */
	private String BatchNo;
	/** 顺序号 */
	private int OrderNo;
	/** 责任缴费id */
	private String PayPlanID;
	/** 投资账户编码 */
	private String InsuAccNo;
	/** 投资账户名称 */
	private String InsuAccName;
	/** 投资金额 */
	private String InvestMoney;
	/** 投资分配比例 */
	private String InvestRate;
	/** 领取金额 */
	private String GetAmount;
	/** 领取比例 */
	private String GetRate;
	/** 状态 */
	private String State;
	/** 原因 */
	private String Reason;
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

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOEdorInvestListSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LBPOEdorInvestListSchema cloned = (LBPOEdorInvestListSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	public String getEdorAppNo()
	{
		return EdorAppNo;
	}
	public void setEdorAppNo(String aEdorAppNo)
	{
		if(aEdorAppNo!=null && aEdorAppNo.length()>20)
			throw new IllegalArgumentException("保全受理号EdorAppNo值"+aEdorAppNo+"的长度"+aEdorAppNo.length()+"大于最大值20");
		EdorAppNo = aEdorAppNo;
	}
	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("操作节点ActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		if(aBatchNo!=null && aBatchNo.length()>30)
			throw new IllegalArgumentException("批次号BatchNo值"+aBatchNo+"的长度"+aBatchNo.length()+"大于最大值30");
		BatchNo = aBatchNo;
	}
	public int getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(int aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		if (aOrderNo != null && !aOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aOrderNo);
			int i = tInteger.intValue();
			OrderNo = i;
		}
	}

	public String getPayPlanID()
	{
		return PayPlanID;
	}
	public void setPayPlanID(String aPayPlanID)
	{
		if(aPayPlanID!=null && aPayPlanID.length()>10)
			throw new IllegalArgumentException("责任缴费idPayPlanID值"+aPayPlanID+"的长度"+aPayPlanID.length()+"大于最大值10");
		PayPlanID = aPayPlanID;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		if(aInsuAccNo!=null && aInsuAccNo.length()>20)
			throw new IllegalArgumentException("投资账户编码InsuAccNo值"+aInsuAccNo+"的长度"+aInsuAccNo.length()+"大于最大值20");
		InsuAccNo = aInsuAccNo;
	}
	public String getInsuAccName()
	{
		return InsuAccName;
	}
	public void setInsuAccName(String aInsuAccName)
	{
		if(aInsuAccName!=null && aInsuAccName.length()>60)
			throw new IllegalArgumentException("投资账户名称InsuAccName值"+aInsuAccName+"的长度"+aInsuAccName.length()+"大于最大值60");
		InsuAccName = aInsuAccName;
	}
	public String getInvestMoney()
	{
		return InvestMoney;
	}
	public void setInvestMoney(String aInvestMoney)
	{
		if(aInvestMoney!=null && aInvestMoney.length()>20)
			throw new IllegalArgumentException("投资金额InvestMoney值"+aInvestMoney+"的长度"+aInvestMoney.length()+"大于最大值20");
		InvestMoney = aInvestMoney;
	}
	public String getInvestRate()
	{
		return InvestRate;
	}
	public void setInvestRate(String aInvestRate)
	{
		if(aInvestRate!=null && aInvestRate.length()>10)
			throw new IllegalArgumentException("投资分配比例InvestRate值"+aInvestRate+"的长度"+aInvestRate.length()+"大于最大值10");
		InvestRate = aInvestRate;
	}
	public String getGetAmount()
	{
		return GetAmount;
	}
	public void setGetAmount(String aGetAmount)
	{
		if(aGetAmount!=null && aGetAmount.length()>20)
			throw new IllegalArgumentException("领取金额GetAmount值"+aGetAmount+"的长度"+aGetAmount.length()+"大于最大值20");
		GetAmount = aGetAmount;
	}
	public String getGetRate()
	{
		return GetRate;
	}
	public void setGetRate(String aGetRate)
	{
		if(aGetRate!=null && aGetRate.length()>20)
			throw new IllegalArgumentException("领取比例GetRate值"+aGetRate+"的长度"+aGetRate.length()+"大于最大值20");
		GetRate = aGetRate;
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
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>3000)
			throw new IllegalArgumentException("原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值3000");
		Reason = aReason;
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
	* 使用另外一个 LBPOEdorInvestListSchema 对象给 Schema 赋值
	* @param: aLBPOEdorInvestListSchema LBPOEdorInvestListSchema
	**/
	public void setSchema(LBPOEdorInvestListSchema aLBPOEdorInvestListSchema)
	{
		this.SerialNo = aLBPOEdorInvestListSchema.getSerialNo();
		this.EdorAppNo = aLBPOEdorInvestListSchema.getEdorAppNo();
		this.ActivityID = aLBPOEdorInvestListSchema.getActivityID();
		this.BatchNo = aLBPOEdorInvestListSchema.getBatchNo();
		this.OrderNo = aLBPOEdorInvestListSchema.getOrderNo();
		this.PayPlanID = aLBPOEdorInvestListSchema.getPayPlanID();
		this.InsuAccNo = aLBPOEdorInvestListSchema.getInsuAccNo();
		this.InsuAccName = aLBPOEdorInvestListSchema.getInsuAccName();
		this.InvestMoney = aLBPOEdorInvestListSchema.getInvestMoney();
		this.InvestRate = aLBPOEdorInvestListSchema.getInvestRate();
		this.GetAmount = aLBPOEdorInvestListSchema.getGetAmount();
		this.GetRate = aLBPOEdorInvestListSchema.getGetRate();
		this.State = aLBPOEdorInvestListSchema.getState();
		this.Reason = aLBPOEdorInvestListSchema.getReason();
		this.MakeOperator = aLBPOEdorInvestListSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLBPOEdorInvestListSchema.getMakeDate());
		this.MakeTime = aLBPOEdorInvestListSchema.getMakeTime();
		this.ModifyOperator = aLBPOEdorInvestListSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLBPOEdorInvestListSchema.getModifyDate());
		this.ModifyTime = aLBPOEdorInvestListSchema.getModifyTime();
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
			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("EdorAppNo") == null )
				this.EdorAppNo = null;
			else
				this.EdorAppNo = rs.getString("EdorAppNo").trim();

			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			this.OrderNo = rs.getInt("OrderNo");
			if( rs.getString("PayPlanID") == null )
				this.PayPlanID = null;
			else
				this.PayPlanID = rs.getString("PayPlanID").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("InsuAccName") == null )
				this.InsuAccName = null;
			else
				this.InsuAccName = rs.getString("InsuAccName").trim();

			if( rs.getString("InvestMoney") == null )
				this.InvestMoney = null;
			else
				this.InvestMoney = rs.getString("InvestMoney").trim();

			if( rs.getString("InvestRate") == null )
				this.InvestRate = null;
			else
				this.InvestRate = rs.getString("InvestRate").trim();

			if( rs.getString("GetAmount") == null )
				this.GetAmount = null;
			else
				this.GetAmount = rs.getString("GetAmount").trim();

			if( rs.getString("GetRate") == null )
				this.GetRate = null;
			else
				this.GetRate = rs.getString("GetRate").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

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
			logger.debug("数据库中的LBPOEdorInvestList表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOEdorInvestListSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOEdorInvestListSchema getSchema()
	{
		LBPOEdorInvestListSchema aLBPOEdorInvestListSchema = new LBPOEdorInvestListSchema();
		aLBPOEdorInvestListSchema.setSchema(this);
		return aLBPOEdorInvestListSchema;
	}

	public LBPOEdorInvestListDB getDB()
	{
		LBPOEdorInvestListDB aDBOper = new LBPOEdorInvestListDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOEdorInvestList描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OrderNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestMoney)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetAmount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOEdorInvestList>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			EdorAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			PayPlanID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsuAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InvestMoney = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InvestRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GetAmount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GetRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOEdorInvestListSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAppNo));
		}
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equalsIgnoreCase("PayPlanID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanID));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccName));
		}
		if (FCode.equalsIgnoreCase("InvestMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMoney));
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRate));
		}
		if (FCode.equalsIgnoreCase("GetAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetAmount));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
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
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorAppNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 4:
				strFieldValue = String.valueOf(OrderNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PayPlanID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsuAccName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InvestMoney);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InvestRate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GetAmount);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GetRate);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAppNo = FValue.trim();
			}
			else
				EdorAppNo = null;
		}
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityID = FValue.trim();
			}
			else
				ActivityID = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayPlanID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanID = FValue.trim();
			}
			else
				PayPlanID = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccName = FValue.trim();
			}
			else
				InsuAccName = null;
		}
		if (FCode.equalsIgnoreCase("InvestMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestMoney = FValue.trim();
			}
			else
				InvestMoney = null;
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestRate = FValue.trim();
			}
			else
				InvestRate = null;
		}
		if (FCode.equalsIgnoreCase("GetAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetAmount = FValue.trim();
			}
			else
				GetAmount = null;
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetRate = FValue.trim();
			}
			else
				GetRate = null;
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
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
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
		LBPOEdorInvestListSchema other = (LBPOEdorInvestListSchema)otherObject;
		return
			SerialNo == other.getSerialNo()
			&& EdorAppNo.equals(other.getEdorAppNo())
			&& ActivityID.equals(other.getActivityID())
			&& BatchNo.equals(other.getBatchNo())
			&& OrderNo == other.getOrderNo()
			&& PayPlanID.equals(other.getPayPlanID())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& InsuAccName.equals(other.getInsuAccName())
			&& InvestMoney.equals(other.getInvestMoney())
			&& InvestRate.equals(other.getInvestRate())
			&& GetAmount.equals(other.getGetAmount())
			&& GetRate.equals(other.getGetRate())
			&& State.equals(other.getState())
			&& Reason.equals(other.getReason())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return 1;
		}
		if( strFieldName.equals("ActivityID") ) {
			return 2;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 4;
		}
		if( strFieldName.equals("PayPlanID") ) {
			return 5;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 6;
		}
		if( strFieldName.equals("InsuAccName") ) {
			return 7;
		}
		if( strFieldName.equals("InvestMoney") ) {
			return 8;
		}
		if( strFieldName.equals("InvestRate") ) {
			return 9;
		}
		if( strFieldName.equals("GetAmount") ) {
			return 10;
		}
		if( strFieldName.equals("GetRate") ) {
			return 11;
		}
		if( strFieldName.equals("State") ) {
			return 12;
		}
		if( strFieldName.equals("Reason") ) {
			return 13;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "EdorAppNo";
				break;
			case 2:
				strFieldName = "ActivityID";
				break;
			case 3:
				strFieldName = "BatchNo";
				break;
			case 4:
				strFieldName = "OrderNo";
				break;
			case 5:
				strFieldName = "PayPlanID";
				break;
			case 6:
				strFieldName = "InsuAccNo";
				break;
			case 7:
				strFieldName = "InsuAccName";
				break;
			case 8:
				strFieldName = "InvestMoney";
				break;
			case 9:
				strFieldName = "InvestRate";
				break;
			case 10:
				strFieldName = "GetAmount";
				break;
			case 11:
				strFieldName = "GetRate";
				break;
			case 12:
				strFieldName = "State";
				break;
			case 13:
				strFieldName = "Reason";
				break;
			case 14:
				strFieldName = "MakeOperator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyOperator";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayPlanID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestMoney") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetAmount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
